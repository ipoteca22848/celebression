package ru.method.celebression.util;

import net.minecraft.class_1657;

public final class PlayerSpeedCalculator {
    private PlayerSpeedCalculator() {
    }

    public static double u(class_1657 class_16572) {
        if (class_16572 == null) {
            return 0.0;
        }
        double d = class_16572.method_23317() - class_16572.field_6014;
        double d2 = class_16572.method_23321() - class_16572.field_5969;
        return Math.hypot(d, d2) * 20.0;
    }
}

