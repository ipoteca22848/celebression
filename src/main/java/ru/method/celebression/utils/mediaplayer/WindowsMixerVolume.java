package ru.method.celebression.utils.mediaplayer;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.util.Map;

final class WindowsMixerVolume {
    private static final int MMSYSERR_NOERROR = 0;
    private static final int MIXERCONTROL_CONTROLTYPE_VOLUME = 1342373889;
    private static final int MIXERLINE_COMPONENTTYPE_DST_SPEAKERS = 4;
    private static final int MIXER_GETLINEINFOF_COMPONENTTYPE = 3;
    private static final int MIXER_GETLINECONTROLSF_ONEBYTYPE = 2;
    private static final int MIXER_GETCONTROLDETAILSF_VALUE = 0;
    private static final int MIXER_SETCONTROLDETAILSF_VALUE = 0;
    private static volatile Integer cachedHandle;
    private static volatile int cachedControlId;
    private static volatile int cachedMin;
    private static volatile int cachedMax;

    private WindowsMixerVolume() {
    }

    static boolean isAvailable() {
        return WindowsMixerVolume.resolveControl() != null;
    }

    static float readScalar() {
        ControlHandle controlHandle = WindowsMixerVolume.resolveControl();
        if (controlHandle == null) {
            return -1.0f;
        }
        Memory memory = new Memory(4L);
        MIXERCONTROLDETAILS mIXERCONTROLDETAILS = new MIXERCONTROLDETAILS();
        mIXERCONTROLDETAILS.cbStruct = mIXERCONTROLDETAILS.size();
        mIXERCONTROLDETAILS.dwControlID = controlHandle.controlId();
        mIXERCONTROLDETAILS.cChannels = 1;
        mIXERCONTROLDETAILS.cMultipleItems = 0;
        mIXERCONTROLDETAILS.cbDetails = 4;
        mIXERCONTROLDETAILS.paDetails = memory;
        if (WinMM.INSTANCE.mixerGetControlDetails(controlHandle.mixer(), mIXERCONTROLDETAILS, 0) != 0) {
            WindowsMixerVolume.invalidate();
            return -1.0f;
        }
        int n = memory.getInt(0L);
        return WindowsMixerVolume.scalarFromRaw(n, controlHandle.min(), controlHandle.max());
    }

    static boolean writeScalar(float f) {
        ControlHandle controlHandle = WindowsMixerVolume.resolveControl();
        if (controlHandle == null) {
            return false;
        }
        int n = WindowsMixerVolume.rawFromScalar(f, controlHandle.min(), controlHandle.max());
        Memory memory = new Memory(4L);
        memory.setInt(0L, n);
        MIXERCONTROLDETAILS mIXERCONTROLDETAILS = new MIXERCONTROLDETAILS();
        mIXERCONTROLDETAILS.cbStruct = mIXERCONTROLDETAILS.size();
        mIXERCONTROLDETAILS.dwControlID = controlHandle.controlId();
        mIXERCONTROLDETAILS.cChannels = 1;
        mIXERCONTROLDETAILS.cMultipleItems = 0;
        mIXERCONTROLDETAILS.cbDetails = 4;
        mIXERCONTROLDETAILS.paDetails = memory;
        if (WinMM.INSTANCE.mixerSetControlDetails(controlHandle.mixer(), mIXERCONTROLDETAILS, 0) != 0) {
            WindowsMixerVolume.invalidate();
            return false;
        }
        return true;
    }

        private static ControlHandle resolveControl() {
        if (cachedControlId >= 0 && cachedHandle != null) {
            return new ControlHandle(cachedHandle, cachedControlId, cachedMin, cachedMax);
        }
        Class<WindowsMixerVolume> clazz = WindowsMixerVolume.class;
        synchronized (WindowsMixerVolume.class) {
            if (cachedControlId >= 0 && cachedHandle != null) {
                return new ControlHandle(cachedHandle, cachedControlId, cachedMin, cachedMax);
            }
            return WindowsMixerVolume.discoverControl();
        }
    }

    private static ControlHandle discoverControl() {
        IntByReference intByReference = new IntByReference();
        if (WinMM.INSTANCE.mixerOpen(intByReference, 0, null, null, 0) != 0) {
            return null;
        }
        int n = intByReference.getValue();
        try {
            MIXERLINE mIXERLINE = new MIXERLINE();
            mIXERLINE.cbStruct = mIXERLINE.size();
            mIXERLINE.dwComponentType = 4;
            if (WinMM.INSTANCE.mixerGetLineInfo(n, mIXERLINE, 3) != 0) {
                WinMM.INSTANCE.mixerClose(n);
                return null;
            }
            Memory memory = new Memory((long)new MIXERCONTROL().size());
            MIXERLINECONTROLS mIXERLINECONTROLS = new MIXERLINECONTROLS();
            mIXERLINECONTROLS.cbStruct = mIXERLINECONTROLS.size();
            mIXERLINECONTROLS.dwLineID = mIXERLINE.dwLineID;
            mIXERLINECONTROLS.dwControlID = 1342373889;
            mIXERLINECONTROLS.cControls = 1;
            mIXERLINECONTROLS.cbmxctrl = (int)memory.size();
            mIXERLINECONTROLS.pamxctrl = memory;
            if (WinMM.INSTANCE.mixerGetLineControls(n, mIXERLINECONTROLS, 2) != 0) {
                WinMM.INSTANCE.mixerClose(n);
                return null;
            }
            MIXERCONTROL mIXERCONTROL = new MIXERCONTROL((Pointer)memory);
            mIXERCONTROL.read();
            cachedHandle = n;
            cachedControlId = mIXERCONTROL.dwControlID;
            cachedMin = mIXERCONTROL.bounds.lMinimum;
            cachedMax = mIXERCONTROL.bounds.lMaximum;
            return new ControlHandle(n, cachedControlId, cachedMin, cachedMax);
        }
        catch (RuntimeException runtimeException) {
            WinMM.INSTANCE.mixerClose(n);
            return null;
        }
    }

        private static void invalidate() {
        Class<WindowsMixerVolume> clazz = WindowsMixerVolume.class;
        synchronized (WindowsMixerVolume.class) {
            if (cachedHandle != null) {
                WinMM.INSTANCE.mixerClose(cachedHandle);
            }
            cachedHandle = null;
            cachedControlId = -1;
            return;
        }
    }

    private static float scalarFromRaw(int n, int n2, int n3) {
        if (n3 <= n2) {
            return 0.0f;
        }
        return WindowsMixerVolume.clamp((float)(n - n2) / (float)(n3 - n2));
    }

    private static int rawFromScalar(float f, int n, int n2) {
        if (n2 <= n) {
            return n;
        }
        return n + Math.round(WindowsMixerVolume.clamp(f) * (float)(n2 - n));
    }

    private static float clamp(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }

    static {
        cachedControlId = -1;
    }

    record ControlHandle(int mixer, int controlId, int min, int max) {
    }

    @Structure.FieldOrder(value={"cbStruct", "dwControlID", "cChannels", "cMultipleItems", "cbDetails", "paDetails"})
    public static class MIXERCONTROLDETAILS
    extends Structure {
        public int cbStruct;
        public int dwControlID;
        public int cChannels;
        public int cMultipleItems;
        public int cbDetails;
        public Pointer paDetails;

        MIXERCONTROLDETAILS() {
        }
    }

    static interface WinMM
    extends StdCallLibrary {
        public static final WinMM INSTANCE = (WinMM)Native.load((String)"winmm", WinMM.class, (Map)W32APIOptions.DEFAULT_OPTIONS);

        public int mixerOpen(IntByReference var1, int var2, Pointer var3, Pointer var4, int var5);

        public int mixerClose(int var1);

        public int mixerGetLineInfo(int var1, MIXERLINE var2, int var3);

        public int mixerGetLineControls(int var1, MIXERLINECONTROLS var2, int var3);

        public int mixerGetControlDetails(int var1, MIXERCONTROLDETAILS var2, int var3);

        public int mixerSetControlDetails(int var1, MIXERCONTROLDETAILS var2, int var3);
    }

    @Structure.FieldOrder(value={"cbStruct", "dwDestination", "dwSource", "dwLineID", "fdwLine", "dwUser", "dwComponentType", "cChannels", "cConnections", "cControls", "szShortName", "szName", "dwType", "dwDeviceID", "wMid", "wPid", "vDriverVersion", "szPname"})
    public static class MIXERLINE
    extends Structure {
        public int cbStruct;
        public int dwDestination;
        public int dwSource;
        public int dwLineID;
        public int fdwLine;
        public Pointer dwUser;
        public int dwComponentType;
        public int cChannels;
        public int cConnections;
        public int cControls;
        public byte[] szShortName = new byte[16];
        public byte[] szName = new byte[64];
        public int dwType;
        public int dwDeviceID;
        public short wMid;
        public short wPid;
        public int vDriverVersion;
        public byte[] szPname = new byte[32];

        MIXERLINE() {
        }
    }

    @Structure.FieldOrder(value={"cbStruct", "dwControlID", "dwControlType", "fdwControl", "cMultipleItems", "szShortName", "szName", "bounds", "metrics"})
    public static class MIXERCONTROL
    extends Structure {
        public int cbStruct;
        public int dwControlID;
        public int dwControlType;
        public int fdwControl;
        public int cMultipleItems;
        public byte[] szShortName = new byte[16];
        public byte[] szName = new byte[64];
        public BoundsUnion bounds = new BoundsUnion();
        public MetricsUnion metrics = new MetricsUnion();

        MIXERCONTROL() {
        }

        MIXERCONTROL(Pointer pointer) {
            super(pointer);
        }

        @Structure.FieldOrder(value={"lMinimum", "lMaximum"})
        public static class BoundsUnion
        extends Structure {
            public int lMinimum;
            public int lMaximum;
        }

        @Structure.FieldOrder(value={"cSteps", "cbCustomData", "fdwReserved"})
        public static class MetricsUnion
        extends Structure {
            public int cSteps;
            public int cbCustomData;
            public int fdwReserved;
        }
    }

    @Structure.FieldOrder(value={"cbStruct", "dwLineID", "dwControlID", "cControls", "cbmxctrl", "pamxctrl"})
    public static class MIXERLINECONTROLS
    extends Structure {
        public int cbStruct;
        public int dwLineID;
        public int dwControlID;
        public int cControls;
        public int cbmxctrl;
        public Pointer pamxctrl;

        MIXERLINECONTROLS() {
        }
    }
}

