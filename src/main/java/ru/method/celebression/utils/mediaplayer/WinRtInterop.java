package ru.method.celebression.utils.mediaplayer;

import com.sun.jna.Function;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinNT;

public final class WinRtInterop {
    private static final int RO_INIT_MULTITHREADED = 1;
    private static final int COINIT_MULTITHREADED = 0;
    private static final ThreadLocal<InitState> THREAD_STATE = ThreadLocal.withInitial(() -> InitState.NOT_TRIED);
    private static final ThreadLocal<InitKind> THREAD_KIND = ThreadLocal.withInitial(() -> InitKind.NONE);

    private WinRtInterop() {
    }

    public static boolean ensureInitialized() {
        if (!WinRtInterop.isWindows()) {
            return false;
        }
        InitState initState = THREAD_STATE.get();
        if (initState == InitState.OK) {
            return true;
        }
        if (initState == InitState.FAILED) {
            return false;
        }
        InitAttempt initAttempt = WinRtInterop.tryRoInitialize();
        if (!initAttempt.ok) {
            initAttempt = WinRtInterop.tryCoInitializeEx();
        }
        THREAD_STATE.set(initAttempt.ok ? InitState.OK : InitState.FAILED);
        THREAD_KIND.set(initAttempt.kind);
        return initAttempt.ok;
    }

    public static void uninitializeCurrentThread() {
        InitKind initKind = THREAD_KIND.get();
        if (initKind == InitKind.NONE) {
            return;
        }
        try {
            if (initKind == InitKind.RO) {
                NativeLibraryHelper.invokeVoid("combase", "RoUninitialize");
            } else if (initKind == InitKind.CO) {
                Ole32.INSTANCE.CoUninitialize();
            }
        }
        catch (Throwable throwable) {
        }
        finally {
            THREAD_KIND.set(InitKind.NONE);
            THREAD_STATE.set(InitState.NOT_TRIED);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private static InitAttempt tryRoInitialize() {
        try {
            int n = NativeLibraryHelper.invokeInt("combase", "RoInitialize", 1);
            if (n == -2147417850) {
                return new InitAttempt(true, InitKind.NONE);
            }
            return new InitAttempt(WinRtInterop.isHresultOk(n), InitKind.RO);
        }
        catch (Throwable throwable) {
            return new InitAttempt(false, InitKind.NONE);
        }
    }

    private static InitAttempt tryCoInitializeEx() {
        try {
            WinNT.HRESULT hRESULT = Ole32.INSTANCE.CoInitializeEx(null, 0);
            if (hRESULT.intValue() == -2147417850) {
                return new InitAttempt(true, InitKind.NONE);
            }
            return new InitAttempt(WinRtInterop.isHresultOk(hRESULT.intValue()), InitKind.CO);
        }
        catch (Throwable throwable) {
            return new InitAttempt(false, InitKind.NONE);
        }
    }

    private static boolean isHresultOk(int n) {
        return n >= 0 || n == -2147417850;
    }

    static enum InitState {
        NOT_TRIED,
        OK,
        FAILED;

    }

    static final class InitAttempt {
        final boolean ok;
        final InitKind kind;

        InitAttempt(boolean bl, InitKind initKind) {
            this.ok = bl;
            this.kind = initKind;
        }

        @Override
        public final String toString() {
            return "InitAttempt[" + "ok=" + this.ok + ", " + "kind=" + this.kind + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ok, this.kind);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof InitAttempt other)) return false;
            return java.util.Objects.deepEquals(this.ok, other.ok)
                && java.util.Objects.deepEquals(this.kind, other.kind);
        }

        public boolean ok() {
            return this.ok;
        }

        public InitKind kind() {
            return this.kind;
        }
    }

    static enum InitKind {
        NONE,
        RO,
        CO;

    }

    static final class NativeLibraryHelper {
        private NativeLibraryHelper() {
        }

        static int invokeInt(String string, String string2, int n) {
            Function function = NativeLibrary.getInstance((String)string).getFunction(string2);
            return function.invokeInt(new Object[]{n});
        }

        static void invokeVoid(String string, String string2) {
            Function function = NativeLibrary.getInstance((String)string).getFunction(string2);
            function.invokeVoid(new Object[0]);
        }
    }
}

