package ru.method.celebression.util;

import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2354;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2544;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class BlockLineOfSightChecker
implements MinecraftClientAccess {
    private static final double Ka = 1.0E-5;
    private static final double Kb = 0.98;
    private static final int Kc = 512;

    private BlockLineOfSightChecker() {
    }

    public static boolean a(class_243 class_2432, class_243 class_2433) {
        if (BlockLineOfSightChecker.aiG.field_1687 == null || class_2432 == null || class_2433 == null) {
            return true;
        }
        if (class_2432.method_1025(class_2433) <= 1.0E-5) {
            return true;
        }
        double d = class_2433.field_1352 - class_2432.field_1352;
        double d2 = class_2433.field_1351 - class_2432.field_1351;
        double d3 = class_2433.field_1350 - class_2432.field_1350;
        int n = class_3532.method_15357((double)class_2432.field_1352);
        int n2 = class_3532.method_15357((double)class_2432.field_1351);
        int n3 = class_3532.method_15357((double)class_2432.field_1350);
        int n4 = class_3532.method_15357((double)class_2433.field_1352);
        int n5 = class_3532.method_15357((double)class_2433.field_1351);
        int n6 = class_3532.method_15357((double)class_2433.field_1350);
        int n7 = Integer.compare(n4, n);
        int n8 = Integer.compare(n5, n2);
        int n9 = Integer.compare(n6, n3);
        double d4 = BlockLineOfSightChecker.d(class_2432.field_1352, d);
        double d5 = BlockLineOfSightChecker.d(class_2432.field_1351, d2);
        double d6 = BlockLineOfSightChecker.d(class_2432.field_1350, d3);
        double d7 = n7 == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / d);
        double d8 = n8 == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / d2);
        double d9 = n9 == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / d3);
        class_2338.class_2339 class_23392 = new class_2338.class_2339();
        boolean bl = true;
        for (int i = 0; i < 512; ++i) {
            class_23392.method_10103(n, n2, n3);
            if (!bl && BlockLineOfSightChecker.a((class_2338)class_23392, class_2432, class_2433)) {
                return false;
            }
            bl = false;
            if (n == n4 && n2 == n5 && n3 == n6) {
                return true;
            }
            if (d4 <= d5 && d4 <= d6) {
                n += n7;
                d4 += d7;
                continue;
            }
            if (d5 <= d6) {
                n2 += n8;
                d5 += d8;
                continue;
            }
            n3 += n9;
            d6 += d9;
        }
        return true;
    }

    private static boolean a(class_2338 class_23382, class_243 class_2432, class_243 class_2433) {
        class_2680 class_26802 = BlockLineOfSightChecker.aiG.field_1687.method_8320(class_23382);
        if (class_26802.method_26215()) {
            return false;
        }
        class_265 class_2652 = class_26802.method_26220((class_1922)BlockLineOfSightChecker.aiG.field_1687, class_23382);
        if (class_2652.method_1110()) {
            return false;
        }
        if (class_26802.method_26164(class_3481.field_15503)) {
            return BlockLineOfSightChecker.a(class_2652, class_23382, class_2432, class_2433);
        }
        if (class_26802.method_26204() instanceof class_2354 || class_26802.method_26204() instanceof class_2349 || class_26802.method_26204() instanceof class_2544) {
            return BlockLineOfSightChecker.a(class_2652, class_23382, class_2432, class_2433);
        }
        if (!class_26802.method_26216()) {
            return false;
        }
        class_238 class_2382 = class_2652.method_1107();
        boolean bl = class_2382.method_17939() >= 0.98 && class_2382.method_17940() >= 0.98 && class_2382.method_17941() >= 0.98;
        return bl && BlockLineOfSightChecker.a(class_2652, class_23382, class_2432, class_2433);
    }

    private static boolean a(class_265 class_2652, class_2338 class_23382, class_243 class_2432, class_243 class_2433) {
        if (class_2652.method_1110()) {
            return false;
        }
        class_243 class_2434 = class_2432.method_1023((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
        class_243 class_2435 = class_2433.method_1023((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
        class_243 class_2436 = class_2435.method_1020(class_2434);
        for (class_238 class_2382 : class_2652.method_1090()) {
            class_238 class_2383 = class_2382.method_1014(1.0E-4);
            if (!BlockLineOfSightChecker.a(class_2383, class_2434, class_2436)) continue;
            return true;
        }
        return false;
    }

    private static boolean a(class_238 class_2382, class_243 class_2432, class_243 class_2433) {
        double d = 0.0;
        double d2 = 1.0;
        double[] dArray = BlockLineOfSightChecker.a(class_2432.field_1352, class_2433.field_1352, class_2382.field_1323, class_2382.field_1320, d, d2);
        if (dArray == null) {
            return false;
        }
        d = dArray[0];
        d2 = dArray[1];
        double[] dArray2 = BlockLineOfSightChecker.a(class_2432.field_1351, class_2433.field_1351, class_2382.field_1322, class_2382.field_1325, d, d2);
        if (dArray2 == null) {
            return false;
        }
        d = dArray2[0];
        d2 = dArray2[1];
        double[] dArray3 = BlockLineOfSightChecker.a(class_2432.field_1350, class_2433.field_1350, class_2382.field_1321, class_2382.field_1324, d, d2);
        return dArray3 != null;
    }

    private static double[] a(double d, double d2, double d3, double d4, double d5, double d6) {
        double[] dArray;
        if (Math.abs(d2) < 1.0E-8) {
            double[] dArray2;
            if (d >= d3 && d <= d4) {
                double[] dArray3 = new double[2];
                dArray3[0] = d5;
                dArray2 = dArray3;
                dArray3[1] = d6;
            } else {
                dArray2 = null;
            }
            return dArray2;
        }
        double d7 = 1.0 / d2;
        double d8 = (d3 - d) * d7;
        double d9 = (d4 - d) * d7;
        if (d8 > d9) {
            double d10 = d8;
            d8 = d9;
            d9 = d10;
        }
        if ((d5 = Math.max(d5, d8)) <= (d6 = Math.min(d6, d9))) {
            double[] dArray4 = new double[2];
            dArray4[0] = d5;
            dArray = dArray4;
            dArray4[1] = d6;
        } else {
            dArray = null;
        }
        return dArray;
    }

    private static double d(double d, double d2) {
        if (d2 > 0.0) {
            return (Math.floor(d) + 1.0 - d) / d2;
        }
        if (d2 < 0.0) {
            return (d - Math.floor(d)) / -d2;
        }
        return Double.POSITIVE_INFINITY;
    }
}

