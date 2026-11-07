package ru.method.celebression.service;

import ru.method.celebression.service.NativeLibraryLoader;

public final class NativeProtectionStatus {
    private static final byte[] akx;

    private NativeProtectionStatus() {
    }

    private static native String rW();

    public static native boolean enabled();

    public static native boolean ad(String var0, String var1);

    private static native String dd(String var0);

    private static native boolean ae(String var0, String var1);

    static {
        NativeLibraryLoader.bootstrap();
        NativeLibraryLoader.call("ru/method/celebression/core/NativeProtectionStatus");
        akx = new byte[]{57, 45, 10, 71, -15, -68, 31, 81, -32, 119, -106, 9, -88, -116, 123, -43, 110, 44, 86, 66, -94, -65, 30, 90, -26, 118, -61, 9, -91, -38, 43, -122, 62, 123, 82, 70, -96, -24, 75, 11, -31, 119, -109, 10, -9, -39, 121, -128, 56, 126, 6, 68, -10, -68, 74, 92, -29, 119, -98, 13, -92, -116, 124, -113};
    }
}

