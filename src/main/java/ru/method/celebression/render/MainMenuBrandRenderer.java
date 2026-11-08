package ru.method.celebression.render;

import net.minecraft.class_1921;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_9848;
import ru.method.celebression.render.MainMenuLogoRenderer;

public final class MainMenuBrandRenderer {
    private static final int ade = 658956;
    private static final int adf = 395528;
    private static final int adg = 329478;
    private static final int adh = 0x8ED8FF;
    private static final int adi = 0xEEF2EF;
    private static final int adj = 6121570;
    private static final int adk = 7897981;
    private static final int adl = 0xC3CCC7;
    private static final int adm = 5002833;
    private static final int adn = 4147780;
    private static final String adp = "\u041d\u0435 \u0441\u0432\u044f\u0437\u0430\u043d \u0441 Mojang \u0438 Microsoft";

    private static long titleStartedNanos;

    private MainMenuBrandRenderer() {
    }

        public static void a(class_332 class_3322, float f, float f2, float f3) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.method_22683() == null) {
            return;
        }
        float f4 = class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
        int n = Math.round(f4 * 255.0f);
        if (n <= 1) {
            return;
        }
        int n2 = class_3322.method_51421();
        int n3 = class_3322.method_51443();
        if (n2 <= 0 || n3 <= 0) {
            return;
        }
        float f5 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
        if (f5 >= 1.0f) {
            return;
        }
        if (f5 > 0.0f) {
            float f6 = f5 * f5 * (3.0f - 2.0f * f5);
            class_3322.method_51448().method_22903();
            class_3322.method_51448().method_46416(0.0f, -f6 * (float)n3, 0.0f);
        }
        try {
            MainMenuBrandRenderer.a(class_3322, n2, n3, class_3102, f, f4, n);
        }
        finally {
            if (f5 > 0.0f) {
                class_3322.method_51448().method_22909();
            }
        }
    }

    private static void a(class_332 class_3322, int n, int n2, class_310 class_3102, float f, float f2, int n3) {
        float f3 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        int n4 = class_9848.method_61317((float)f2);
        double d = class_3102.method_22683().method_4495();
        if (d <= 0.0) {
            d = 1.0;
        }
        int n5 = Math.max(1, (int)Math.round(d));
        float f4 = (float)((double)n * d);
        float f5 = (float)((double)n2 * d);
        float f6 = Math.min(f4 / 1920.0f, f5 / 1080.0f);
        if (f6 <= 0.0f) {
            return;
        }
        MainMenuBrandRenderer.a(class_3322, n, n2, n3);
        MainMenuBrandRenderer.b(class_3322, n, n2, n4);
        MainMenuBrandRenderer.a(class_3322, n, n2, f6, d, n3);
        MainMenuBrandRenderer.a(class_3322, f4, f5, f6, d, n5, n4);
        MainMenuBrandRenderer.a(class_3322, f4, f5, f6, d, n5, n4, f2, f3);
        MainMenuBrandRenderer.b(class_3322, f4, f5, f6, d, n5, n4);
    }

    public static void qc() {
        titleStartedNanos = 0L;
        MainMenuLogoRenderer.qc();
    }

    private static void a(class_332 class_3322, int n, int n2, int n3) {
        int n4 = Math.round((float)n2 * 0.55f);
        class_3322.method_25296(0, 0, n, n4, MainMenuBrandRenderer.s(n3, 658956), MainMenuBrandRenderer.s(n3, 395528));
        class_3322.method_25296(0, n4, n, n2, MainMenuBrandRenderer.s(n3, 395528), MainMenuBrandRenderer.s(n3, 329478));
    }

    private static void b(class_332 class_3322, int n, int n2, int n3) {
        MainMenuLogoRenderer.LogoState logoState = MainMenuLogoRenderer.h(0x8ED8FF, 0.06f);
        if (logoState == null) {
            return;
        }
        int n4 = Math.round((float)n * 0.92f);
        int n5 = Math.round((float)n2 * 0.76f);
        int n6 = (n - n4) / 2;
        int n7 = Math.round((float)n2 * 0.42f) - n5 / 2;
        class_3322.method_25293(class_1921::method_62277, logoState.id(), n6, n7, 0.0f, 0.0f, n4, n5, logoState.qq(), logoState.qr(), logoState.qq(), logoState.qr(), n3);
    }

    private static void a(class_332 class_3322, int n, int n2, float f, double d, int n3) {
        int n4 = MainMenuBrandRenderer.a(24.0f * f, d);
        int n5 = Math.max(2, MainMenuBrandRenderer.a(18.0f * f, d));
        int n6 = Math.max(1, MainMenuBrandRenderer.a(f, d));
        int n7 = MainMenuBrandRenderer.s(Math.round((float)n3 * 0.35f), 0x8ED8FF);
        class_3322.method_25294(n4, n4, n4 + n5, n4 + n6, n7);
        class_3322.method_25294(n4, n4, n4 + n6, n4 + n5, n7);
        class_3322.method_25294(n - n4 - n5, n2 - n4 - n6, n - n4, n2 - n4, n7);
        class_3322.method_25294(n - n4 - n6, n2 - n4 - n5, n - n4, n2 - n4, n7);
    }

    private static void a(class_332 class_3322, float f, float f2, float f3, double d, int n, int n2) {
        if (titleStartedNanos == 0L) titleStartedNanos = System.nanoTime();
        float appear = ru.method.celebression.service.IntroTransition.progress(titleStartedNanos, System.nanoTime(), 850_000_000L);
        n2 = (n2 & 0x00FFFFFF) | (Math.round((n2 >>> 24) * appear) << 24);
        f2 += (1f - appear) * 18f * f3;

        int n3 = Math.max(8, Math.round(72.0f * f3));
        int n4 = Math.max(5, Math.round(12.0f * f3));
        MainMenuLogoRenderer.LogoState logoState = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adC, "celebression", n3, 0x8ED8FF, -0.04f, n);
        MainMenuLogoRenderer.LogoState logoState2 = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adC, ".", n3, 0x8ED8FF, 0.0f, n);
        MainMenuLogoRenderer.LogoState logoState3 = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adD, "VISUALS", n4, 6121570, 0.42f, n);
        if (logoState == null) {
            return;
        }
        float f4 = (float)logoState.qr() - (float)logoState.qs() * 2.0f;
        float f5 = logoState3 == null ? 0.0f : (float)logoState3.qr() - (float)logoState3.qs() * 2.0f;
        float f6 = logoState3 == null ? 0.0f : 10.0f * f3;
        float f7 = f4 + f6 + f5;
        float f8 = f2 * 0.44f - f7 / 2.0f;
        float f9 = logoState.advance() + (logoState2 == null ? 0.0f : logoState2.advance());
        float f10 = f / 2.0f - f9 / 2.0f;
        float f11 = f8 + (logoState.qt() - (float)logoState.qs());
        MainMenuBrandRenderer.a(class_3322, logoState, f10, f11, d, n2);
        if (logoState2 != null) {
            MainMenuBrandRenderer.a(class_3322, logoState2, f10 + logoState.advance(), f11, d, n2);
        }
        if (logoState3 != null) {
            float f12 = f8 + f4 + f6;
            MainMenuBrandRenderer.a(class_3322, logoState3, f / 2.0f - logoState3.advance() / 2.0f, f12 + (logoState3.qt() - (float)logoState3.qs()), d, n2);
        }
    }

    private static void a(class_332 class_3322, float f, float f2, float f3, double d, int n, int n2, float f4, float f5) {
        float f6;
        float f7;
        int n3 = Math.max(5, Math.round(12.5f * f3));
        int n4 = Math.max(5, Math.round(11.5f * f3));
        MainMenuLogoRenderer.LogoState logoState = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adB, MainMenuBrandRenderer.au(f5), n3, 7897981, 0.0f, n);
        MainMenuLogoRenderer.LogoState logoState2 = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adE, Math.round(f5 * 100.0f) + "%", n4, 0xC3CCC7, 0.0f, n);
        float f8 = 460.0f * f3;
        float f9 = f / 2.0f - f8 / 2.0f;
        float f10 = f7 = f2 - 200.0f * f3;
        float f11 = f7;
        if (logoState != null) {
            f6 = (float)(logoState.qr() - logoState.qs()) - logoState.qt();
            f10 = f7 - f6;
            f11 = f10 - (logoState.qt() - (float)logoState.qs());
        }
        float f12 = f11 - 16.0f * f3;
        int left = a(f9, d), right = a(f9 + f8, d);
        int bottom = a(f12, d), top = bottom - Math.max(10, a(24f * f3, d));
        int opacity = Math.round(f4 * 255f);
        b(class_3322, left - 1, top - 1, right + 1, bottom + 1, s(Math.round(opacity * .65f), 0x8ED8FF));
        b(class_3322, left, top, right, bottom, s(opacity, 0x102536));
        double phase = System.nanoTime() / 700_000_000d;
        int innerLeft = left + 2, innerRight = right - 2;
        int innerTop = top + 2, innerBottom = bottom - 2;
        int span = Math.max(0, innerRight - innerLeft);
        // Each row touches the preceding row; the moving water stays inside the tank.
        for (int row = innerTop; row < innerBottom; row++) {
            double wave = Math.sin((row - innerTop) * .55 + phase * 2.8) * 2.2
                        + Math.sin((row - innerTop) * .24 - phase * 1.7) * 1.1;
            float edgeStrength = (float)Math.sin(Math.PI * f5);
            int edge = Math.max(innerLeft, Math.min(innerRight, innerLeft + Math.round(span * f5 + (float)wave * edgeStrength)));
            float depth = (float)(row - innerTop) / Math.max(1, innerBottom - innerTop);
            int r = Math.round(120 - depth * 88), g = Math.round(218 - depth * 64), blue = Math.round(255 - depth * 18);
            if (edge > innerLeft) {
                class_3322.method_25294(innerLeft, row, edge, row + 1, s(opacity, (r << 16) | (g << 8) | blue));
                class_3322.method_25294(Math.max(innerLeft, edge - 1), row, edge, row + 1, s(opacity, 0xC7F5FF));
            }
        }
        if (logoState != null) {
            MainMenuBrandRenderer.a(class_3322, logoState, f9, f10, d, n2);
        }
        if (logoState2 != null) {
            MainMenuBrandRenderer.a(class_3322, logoState2, f9 + f8 - logoState2.advance(), f10, d, n2);
        }
    }

    private static void b(class_332 class_3322, float f, float f2, float f3, double d, int n, int n2) {
        int size = Math.max(5, Math.round(10.5f * f3));
        MainMenuLogoRenderer.LogoState note = MainMenuLogoRenderer.a(MainMenuLogoRenderer.LogoAsset.adB,
                "Не связан с Mojang и Microsoft", size, 4147780, 0.0f, n);
        if (note != null) {
            float y = f2 - 44f * f3 - (note.qr() - note.qs() * 2f);
            MainMenuBrandRenderer.a(class_3322, note, f / 2f - note.advance() / 2f, y + note.qt() - note.qs(), d, n2);
        }
    }

    private static void a(class_332 class_3322, MainMenuLogoRenderer.LogoState logoState, float f, float f2, double d, int n) {
        int n2 = MainMenuBrandRenderer.a(f - (float)logoState.qs(), d);
        int n3 = MainMenuBrandRenderer.a(f2 - logoState.qt(), d);
        int n4 = Math.max(1, (int)Math.round((double)logoState.qq() / d));
        int n5 = Math.max(1, (int)Math.round((double)logoState.qr() / d));
        class_3322.method_25293(class_1921::method_62277, logoState.id(), n2, n3, 0.0f, 0.0f, n4, n5, logoState.qq(), logoState.qr(), logoState.qq(), logoState.qr(), n);
    }

    private static int a(float f, double d) {
        return (int)Math.round((double)f / d);
    }

    private static void b(class_332 class_3322, int n, int n2, int n3, int n4, int n5) {
        int n6 = n3 - n;
        int n7 = n4 - n2;
        if (n6 <= 2 || n7 <= 2) {
            class_3322.method_25294(n, n2, n3, n4, n5);
            return;
        }
        int n8 = n5 & 0xFFFFFF | Math.round((float)(n5 >>> 24 & 0xFF) * 0.45f) << 24;
        class_3322.method_25294(n + 1, n2, n3 - 1, n4, n5);
        class_3322.method_25294(n, n2 + 1, n + 1, n4 - 1, n5);
        class_3322.method_25294(n3 - 1, n2 + 1, n3, n4 - 1, n5);
        class_3322.method_25294(n, n2, n + 1, n2 + 1, n8);
        class_3322.method_25294(n, n4 - 1, n + 1, n4, n8);
        class_3322.method_25294(n3 - 1, n2, n3, n2 + 1, n8);
        class_3322.method_25294(n3 - 1, n4 - 1, n3, n4, n8);
    }

    private static int s(int n, int n2) {
        return class_3532.method_15340((int)n, (int)0, (int)255) << 24 | n2 & 0xFFFFFF;
    }

    private static String au(float f) {
        if (f < 0.33f) {
            return "\u0417\u0430\u0433\u0440\u0443\u0436\u0430\u0435\u043c \u0440\u0435\u0441\u0443\u0440\u0441\u043f\u0430\u043a\u0438";
        }
        if (f < 0.66f) {
            return "\u0417\u0430\u0433\u0440\u0443\u0436\u0430\u0435\u043c \u0448\u0435\u0439\u0434\u0435\u0440\u044b";
        }
        if (f < 0.999f) {
            return "\u0417\u0430\u0433\u0440\u0443\u0436\u0430\u0435\u043c \u0442\u0435\u043a\u0441\u0442\u0443\u0440\u044b";
        }
        return "\u0413\u043e\u0442\u043e\u0432\u043e";
    }
}

