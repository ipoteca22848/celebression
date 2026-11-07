package ru.method.linkenvisuals.obf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.method.celebression.service.NativeLibraryLoader;

public final class zz {
    private static final Map C;

    static {
        NativeLibraryLoader.bootstrap();
        NativeLibraryLoader.call(zz.class.getName());
        C = new ConcurrentHashMap();
    }

    public static native String d(String var0);
}

