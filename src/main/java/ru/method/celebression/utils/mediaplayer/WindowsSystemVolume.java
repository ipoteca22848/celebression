package ru.method.celebression.utils.mediaplayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import ru.method.celebression.utils.mediaplayer.WindowsCoreAudioVolume;
import ru.method.celebression.utils.mediaplayer.WindowsMixerVolume;

public final class WindowsSystemVolume {
    private static volatile float cachedVolume = 0.72f;
    private static volatile long lastReadMs;
    private static volatile FloatControl masterControl;

    private WindowsSystemVolume() {
    }

    public static float getVolume() {
        long l = System.currentTimeMillis();
        if (l - lastReadMs < 60L) {
            return cachedVolume;
        }
        if (WindowsSystemVolume.isWindows()) {
            float f = WindowsSystemVolume.safeRead(() -> WindowsCoreAudioVolume.readScalar());
            if (f >= 0.0f) {
                cachedVolume = f;
                lastReadMs = l;
                return cachedVolume;
            }
            float f2 = WindowsSystemVolume.safeRead(() -> WindowsMixerVolume.readScalar());
            if (f2 >= 0.0f) {
                cachedVolume = f2;
                lastReadMs = l;
                return cachedVolume;
            }
        }
        try {
            FloatControl floatControl = WindowsSystemVolume.resolveControl();
            if (floatControl != null) {
                cachedVolume = WindowsSystemVolume.scalarFromControl(floatControl);
                lastReadMs = l;
            }
        }
        catch (Throwable throwable) {
        }
        return cachedVolume;
    }

    public static void setVolume(float f) {
        cachedVolume = WindowsSystemVolume.clamp(f);
        lastReadMs = System.currentTimeMillis();
        if (WindowsSystemVolume.isWindows()) {
            if (WindowsSystemVolume.safeWrite(() -> WindowsCoreAudioVolume.writeScalar(cachedVolume))) {
                return;
            }
            if (WindowsSystemVolume.safeWrite(() -> WindowsMixerVolume.writeScalar(cachedVolume))) {
                return;
            }
        }
        try {
            FloatControl floatControl = WindowsSystemVolume.resolveControl();
            if (floatControl == null) {
                return;
            }
            floatControl.setValue(WindowsSystemVolume.scalarToControl(floatControl, cachedVolume));
        }
        catch (Throwable throwable) {
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private static float safeRead(FloatSupplier floatSupplier) {
        try {
            return floatSupplier.get();
        }
        catch (Throwable throwable) {
            return -1.0f;
        }
    }

    private static boolean safeWrite(BooleanSupplier booleanSupplier) {
        try {
            return booleanSupplier.get();
        }
        catch (Throwable throwable) {
            return false;
        }
    }

        private static FloatControl resolveControl() {
        FloatControl floatControl = masterControl;
        if (floatControl != null) {
            return floatControl;
        }
        Class<WindowsSystemVolume> clazz = WindowsSystemVolume.class;
        synchronized (WindowsSystemVolume.class) {
            if (masterControl != null) {
                return masterControl;
            }
            masterControl = WindowsSystemVolume.findMasterControl();
            return masterControl;
        }
    }

    private static FloatControl findMasterControl() {
        Mixer.Info[] infoArray;
        FloatControl floatControl = WindowsSystemVolume.findControlOnMixer(null);
        if (floatControl != null) {
            return floatControl;
        }
        for (Mixer.Info info : infoArray = AudioSystem.getMixerInfo()) {
            floatControl = WindowsSystemVolume.findControlOnMixer(info);
            if (floatControl == null) continue;
            return floatControl;
        }
        return null;
    }

    private static FloatControl findControlOnMixer(Mixer.Info info) {
        try {
            Mixer mixer;
            Mixer mixer2 = mixer = info == null ? AudioSystem.getMixer(null) : AudioSystem.getMixer(info);
            if (mixer == null) {
                return null;
            }
            if (mixer.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                return (FloatControl)mixer.getControl(FloatControl.Type.MASTER_GAIN);
            }
            if (mixer.isControlSupported(FloatControl.Type.VOLUME)) {
                return (FloatControl)mixer.getControl(FloatControl.Type.VOLUME);
            }
        }
        catch (Throwable throwable) {
        }
        return null;
    }

    private static float scalarFromControl(FloatControl floatControl) {
        float f = floatControl.getMinimum();
        float f2 = floatControl.getMaximum();
        if (f2 <= f) {
            return cachedVolume;
        }
        return WindowsSystemVolume.clamp((floatControl.getValue() - f) / (f2 - f));
    }

    private static float scalarToControl(FloatControl floatControl, float f) {
        float f2 = floatControl.getMinimum();
        float f3 = floatControl.getMaximum();
        return f2 + WindowsSystemVolume.clamp(f) * (f3 - f2);
    }

    private static float clamp(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }

    @FunctionalInterface
    static interface FloatSupplier {
        public float get();
    }

    @FunctionalInterface
    static interface BooleanSupplier {
        public boolean get();
    }
}

