package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_3610;

public final class PetGroundSampler {
    private static final double WATER_SURFACE = 0.9;

    private PetGroundSampler() {
    }

    public static double sampleGroundY(class_1937 class_19372, double d, double d2, double d3) {
        if (class_19372 == null) {
            return Double.NaN;
        }
        int n = class_3532.method_15357((double)d);
        int n2 = class_3532.method_15357((double)d2);
        double d4 = d3 + 1.25;
        int n3 = PetGroundSampler.worldBottom(class_19372);
        int n4 = PetGroundSampler.startScanY(class_19372, d3, n3);
        double d5 = PetGroundSampler.column(class_19372, n, n2, n4, 90, n3, d4);
        if (!Double.isNaN(d5)) {
            return d5;
        }
        double d6 = PetGroundSampler.column(class_19372, n + 1, n2, n4, 90, n3, d4);
        double d7 = PetGroundSampler.column(class_19372, n, n2 + 1, n4, 90, n3, d4);
        double d8 = PetGroundSampler.column(class_19372, n + 1, n2 + 1, n4, 90, n3, d4);
        double d9 = Double.NaN;
        if (!Double.isNaN(d6)) {
            d9 = d6;
        }
        if (!Double.isNaN(d7)) {
            double d10 = d9 = Double.isNaN(d9) ? d7 : Math.max(d9, d7);
        }
        if (!Double.isNaN(d8)) {
            double d11 = d9 = Double.isNaN(d9) ? d8 : Math.max(d9, d8);
        }
        if (!Double.isNaN(d9)) {
            return d9;
        }
        return PetGroundSampler.column(class_19372, n, n2, n4, 170, n3, d4);
    }

    public static double sampleGroundYSmooth(class_1937 class_19372, double d, double d2, double d3) {
        return PetGroundSampler.sampleGroundY(class_19372, d, d2, d3);
    }

    private static double column(class_1937 class_19372, int n, int n2, int n3, int n4, int n5, double d) {
        if (class_19372 == null) {
            return Double.NaN;
        }
        int n6 = Math.max(n5 + 1, n3 - Math.max(12, n4));
        class_2338.class_2339 class_23392 = new class_2338.class_2339();
        double d2 = Double.NaN;
        for (int i = n3; i >= n6; --i) {
            class_3610 class_36102;
            class_2680 class_26802;
            class_23392.method_10103(n, i, n2);
            try {
                class_26802 = class_19372.method_8320((class_2338)class_23392);
            }
            catch (Throwable throwable) {
                return Double.NaN;
            }
            if (class_26802 == null) continue;
            if (Double.isNaN(d2)) {
                try {
                    class_36102 = class_26802.method_26227();
                    if (class_36102 != null && !class_36102.method_15769()) {
                        d2 = (double)i + 0.9;
                    }
                }
                catch (Throwable throwable) {
                }
            }
            if (class_26802.method_26215()) continue;
            class_265 collisionShape;
            try {
                collisionShape = class_26802.method_26220((class_1922)class_19372, (class_2338)class_23392);
            }
            catch (Throwable throwable) {
                collisionShape = null;
            }
            if (collisionShape == null || collisionShape.method_1110()) continue;
            double d3 = (double)i + collisionShape.method_1105(class_2350.class_2351.field_11052);
            double d4 = (double)i + collisionShape.method_1091(class_2350.class_2351.field_11052);
            if (d4 > d) continue;
            if (d3 > d) {
                return Double.NaN;
            }
            if (!Double.isNaN(d2) && d2 <= d && d2 >= d3) {
                return d2;
            }
            return d3 + 0.015;
        }
        if (!Double.isNaN(d2) && d2 <= d) {
            return d2;
        }
        return Double.NaN;
    }

    private static int worldBottom(class_1937 class_19372) {
        try {
            return class_19372.method_31607();
        }
        catch (Throwable throwable) {
            return -64;
        }
    }

    private static int startScanY(class_1937 class_19372, double d, int n) {
        int n2;
        try {
            n2 = n + class_19372.method_31605();
        }
        catch (Throwable throwable) {
            n2 = 320;
        }
        return class_3532.method_15340((int)(class_3532.method_15357((double)d) + 3), (int)(n + 2), (int)(n2 - 2));
    }
}
