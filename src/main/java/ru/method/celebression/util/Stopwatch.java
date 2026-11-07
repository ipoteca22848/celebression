package ru.method.celebression.util;

import lombok.Generated;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class Stopwatch
implements MinecraftClientAccess {
    private static int akt;

    public static void rS() {
        akt = aiG.method_47599();
    }

    @Generated
    private Stopwatch() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Generated
    public static int rT() {
        return akt;
    }
}

