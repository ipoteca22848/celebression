package ru.method.celebression.utils.mediaplayer;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import ru.method.celebression.Celebression;

public final class SystemVolumeController {
    private static final String CLSID_MM_DEVICE_ENUMERATOR = "{BCDE0395-E52F-467C-8E3D-C4579291692E}";
    private static final String IID_IMM_DEVICE_ENUMERATOR = "{A95664D2-9614-4F35-A746-DE8DB63617E6}";
    private static final String IID_IAUDIO_ENDPOINT_VOLUME = "{5CDF2C82-841E-4546-9722-0CF74078229A}";
    private static final int CLSCTX_ALL = 23;
    private static final int COINIT_MULTITHREADED = 0;
    private static final int E_RENDER = 0;
    private static final int E_CONSOLE = 0;
    private static final int VT_SET_MASTER_SCALAR = 7;
    private static final int VT_GET_MASTER_SCALAR = 9;
    private static final int VT_GET_DEFAULT_ENDPOINT = 4;
    private static final int VT_ACTIVATE = 3;
    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "LV-Volume");
        thread.setDaemon(true);
        return thread;
    });
    private static final AtomicReference<Float> volume = new AtomicReference<>(null);
    private static volatile boolean initialized;
    private static volatile boolean unavailable;
    private static Pointer endpointVolume;

    private SystemVolumeController() {
    }

    public static void ensureInit() {
        if (initialized || unavailable || !SystemVolumeController.isWindows()) {
            if (!SystemVolumeController.isWindows()) {
                unavailable = true;
            }
            return;
        }
        EXEC.submit(() -> {
            if (!initialized && !unavailable) {
                SystemVolumeController.initOnComThread();
            }
        });
    }

    public static Float currentVolume() {
        return volume.get();
    }

    public static boolean isUnavailable() {
        return unavailable;
    }

    public static void setVolume(float f) {
        if (unavailable || !SystemVolumeController.isWindows()) {
            return;
        }
        float f2 = SystemVolumeController.clamp01(f);
        volume.set(Float.valueOf(f2));
        EXEC.submit(() -> {
            if (!initialized) {
                SystemVolumeController.initOnComThread();
            }
            SystemVolumeController.setVolumeInternal(f2);
        });
    }

    public static void refresh() {
        if (unavailable || !SystemVolumeController.isWindows()) {
            return;
        }
        EXEC.submit(() -> {
            if (!initialized) {
                SystemVolumeController.initOnComThread();
            }
            SystemVolumeController.readVolumeInternal();
        });
    }

    private static void initOnComThread() {
        try {
            Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, 0);
            Guid.GUID gUID = new Guid.GUID("{BCDE0395-E52F-467C-8E3D-C4579291692E}");
            Guid.GUID gUID2 = new Guid.GUID("{A95664D2-9614-4F35-A746-DE8DB63617E6}");
            PointerByReference pointerByReference = new PointerByReference();
            WinNT.HRESULT hRESULT = Ole32.INSTANCE.CoCreateInstance(gUID, Pointer.NULL, 23, gUID2, pointerByReference);
            if (SystemVolumeController.failed(hRESULT) || pointerByReference.getValue() == null) {
                SystemVolumeController.markUnavailable("CoCreateInstance(MMDeviceEnumerator)", hRESULT == null ? 0 : hRESULT.intValue());
                return;
            }
            Pointer pointer = pointerByReference.getValue();
            PointerByReference pointerByReference2 = new PointerByReference();
            int n = SystemVolumeController.invoke(pointer, 4, 0, 0, pointerByReference2);
            if (n < 0 || pointerByReference2.getValue() == null) {
                SystemVolumeController.release(pointer);
                SystemVolumeController.markUnavailable("GetDefaultAudioEndpoint", n);
                return;
            }
            Pointer pointer2 = pointerByReference2.getValue();
            Guid.GUID gUID3 = new Guid.GUID("{5CDF2C82-841E-4546-9722-0CF74078229A}");
            gUID3.write();
            PointerByReference pointerByReference3 = new PointerByReference();
            int n2 = SystemVolumeController.invoke(pointer2, 3, gUID3.getPointer(), 23, Pointer.NULL, pointerByReference3);
            if (n2 < 0 || pointerByReference3.getValue() == null) {
                SystemVolumeController.release(pointer2);
                SystemVolumeController.release(pointer);
                SystemVolumeController.markUnavailable("IMMDevice::Activate(IAudioEndpointVolume)", n2);
                return;
            }
            endpointVolume = pointerByReference3.getValue();
            SystemVolumeController.release(pointer2);
            SystemVolumeController.release(pointer);
            initialized = true;
            SystemVolumeController.readVolumeInternal();
        }
        catch (Throwable throwable) {
            unavailable = true;
            Celebression.LOGGER.debug("[Volume] Core Audio init failed", throwable);
        }
    }

    private static void setVolumeInternal(float f) {
        if (!initialized || endpointVolume == null) {
            return;
        }
        try {
            int n = SystemVolumeController.invoke(endpointVolume, 7, Float.valueOf(SystemVolumeController.clamp01(f)), Pointer.NULL);
            if (n >= 0) {
                volume.set(Float.valueOf(SystemVolumeController.clamp01(f)));
            }
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.debug("[Volume] SetMasterVolumeLevelScalar failed", throwable);
        }
    }

    private static void readVolumeInternal() {
        if (!initialized || endpointVolume == null) {
            return;
        }
        try {
            Memory memory = new Memory(4L);
            int n = SystemVolumeController.invoke(endpointVolume, 9, memory);
            if (n >= 0) {
                volume.set(Float.valueOf(SystemVolumeController.clamp01(memory.getFloat(0L))));
            }
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.debug("[Volume] GetMasterVolumeLevelScalar failed", throwable);
        }
    }

    private static int invoke(Pointer pointer, int n, Object ... objectArray) {
        Pointer pointer2 = pointer.getPointer(0L);
        Pointer pointer3 = pointer2.getPointer((long)n * (long)Native.POINTER_SIZE);
        Function function = Function.getFunction((Pointer)pointer3);
        Object[] objectArray2 = new Object[objectArray.length + 1];
        objectArray2[0] = pointer;
        System.arraycopy(objectArray, 0, objectArray2, 1, objectArray.length);
        return function.invokeInt(objectArray2);
    }

    private static void release(Pointer pointer) {
        if (pointer == null) {
            return;
        }
        try {
            SystemVolumeController.invoke(pointer, 2, new Object[0]);
        }
        catch (Throwable throwable) {
        }
    }

    private static void markUnavailable(String string, int n) {
        unavailable = true;
        Celebression.LOGGER.debug("[Volume] {} failed: 0x{}", (Object)string, (Object)Integer.toHexString(n));
    }

    private static boolean failed(WinNT.HRESULT hRESULT) {
        return hRESULT == null || hRESULT.intValue() < 0;
    }

    private static float clamp01(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}
