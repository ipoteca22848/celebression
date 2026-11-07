package ru.method.celebression.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.method.celebression.service.NativeLibraryLoader;

public final class StringDecryptor {
    private static final Map C;

    static {
        NativeLibraryLoader.bootstrap();
        NativeLibraryLoader.call("ru/method/celebression/core/StringDecryptor");
        C = new ConcurrentHashMap();
    }

    public static native String d(String var0);
}

