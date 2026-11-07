package ru.method.celebression.utils.render;

import net.minecraft.class_10042;

public final class ArmorRenderStateHolder {
    private static final ThreadLocal<class_10042> ARMOR_STATE = new ThreadLocal();

    private ArmorRenderStateHolder() {
    }

    public static void set(class_10042 class_100422) {
        ARMOR_STATE.set(class_100422);
    }

    public static class_10042 get() {
        return ARMOR_STATE.get();
    }

    public static void clear() {
        ARMOR_STATE.remove();
    }
}

