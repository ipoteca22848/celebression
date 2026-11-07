package ru.method.celebression.render;

import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.ui.MainMenuLottiePlayer;

public final class ColorMath {
    private static boolean ajw;
    private static boolean ajx;
    private static boolean ajy;
    private static boolean ajz;
    private static long ajA;
    private static MainMenuLottiePlayer ajB;

    private ColorMath() {
    }

    public static void rx() {
        ajx = !ajw;
        ajy = false;
        ajz = false;
        ajA = 0L;
        ColorMath.rB();
    }

    public static void ry() {
        if (!ajx) {
            return;
        }
        ajx = false;
        if (!ajw) {
            ajy = true;
        }
    }

    public static boolean rz() {
        return ajy || ajz;
    }

    public static void d(class_332 class_3322, int n, int n2) {
        if (ajw || class_3322 == null) {
            return;
        }
        if (!ajy && !ajz) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        if (ajB == null) {
            ajB = new MainMenuLottiePlayer();
        }
        if (ajy) {
            if (!ajB.a(class_3102)) {
                ajy = false;
                ColorMath.rA();
                return;
            }
            ajy = false;
            ajz = true;
            ajA = System.nanoTime();
        }
        if (!ajz) {
            return;
        }
        float f = ajB.rC();
        if (f <= 0.0f) {
            ColorMath.rA();
            return;
        }
        float f2 = (float)(System.nanoTime() - ajA) / 1.0E9f;
        float f3 = class_3532.method_15363((float)(f2 / f), (float)0.0f, (float)1.0f);
        if (f3 >= 1.0f) {
            ColorMath.rA();
            return;
        }
        ajB.render(class_3322, n, n2, f3);
    }

    private static void play() {
        if (ajw) {
            return;
        }
        ajw = true;
    }

    private static void rA() {
        ajz = false;
        ajy = false;
        ColorMath.rB();
        ColorMath.play();
    }

    private static void rB() {
        if (ajB != null) {
            ajB.close();
            ajB = null;
        }
    }
}

