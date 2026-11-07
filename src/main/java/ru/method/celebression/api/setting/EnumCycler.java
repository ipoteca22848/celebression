package ru.method.celebression.api.setting;

import lombok.Generated;

public final class EnumCycler {
    public static <T extends Enum<T>> int a(T t) {
        Enum[] enumArray = (Enum[])t.getDeclaringClass().getEnumConstants();
        for (int i = 0; i < enumArray.length; ++i) {
            Enum enum_ = enumArray[i];
            if (!enum_.name().equalsIgnoreCase(t.name())) continue;
            return i;
        }
        return -1;
    }

    public static <T extends Enum<T>> T b(T t) {
        Enum[] enumArray = (Enum[])t.getDeclaringClass().getEnumConstants();
        int n = EnumCycler.a(t);
        for (int i = 0; i < enumArray.length; ++i) {
            Enum enum_ = enumArray[i];
            if (i != n + 1) continue;
            return (T)enum_;
        }
        return (T)enumArray[0];
    }

    @Generated
    private EnumCycler() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

