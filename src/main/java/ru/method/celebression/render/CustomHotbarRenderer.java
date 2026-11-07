package ru.method.celebression.render;

import java.awt.Color;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_9779;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public final class CustomHotbarRenderer {
    private static final float fj = 18.0f;
    private static final float fk = 2.0f;
    private static final float fl = 2.0f;
    private static float fm = 0.0f;
    private static float fn = 0.0f;
    private static int fo = -1;
    private static long fp = 0L;

    private CustomHotbarRenderer() {
    }

    public static void a(class_332 class_3322, class_9779 class_97792) {
        int n;
        class_310 class_3102 = class_310.method_1551();
        class_746 class_7462 = class_3102.field_1724;
        if (class_7462 == null) {
            return;
        }
        int n2 = class_3322.method_51421();
        int n3 = class_3322.method_51443();
        float f = 178.0f;
        float f2 = (float)n2 / 2.0f - f / 2.0f;
        float f3 = (float)n3 - 18.0f - 4.0f;
        class_4587 class_45872 = class_3322.method_51448();
        HudThemeRenderer.b(class_45872, f2 - 2.0f, f3 - 2.0f, f + 4.0f, 22.0f, HudThemeRenderer.f(f + 4.0f, 22.0f), 1.0f);
        Color color = HudThemeRenderer.f(150);
        for (int i = 0; i < 9; ++i) {
            float f4 = f2 + (float)i * 20.0f;
            Render2D.drawRoundedRect(class_45872, f4, f3, 18.0f, 18.0f, 4.0f, color);
        }
        long l = System.currentTimeMillis();
        float f5 = fp == 0L ? 0.016666668f : Math.min(0.08f, (float)(l - fp) / 1000.0f);
        fp = l;
        int n4 = class_7462.method_31548().field_7545;
        if (fo == -1) {
            fo = n4;
            fm = n4;
        } else if (n4 != fo) {
            fn = 1.0f;
            fo = n4;
        }
        float f6 = 1.0f - (float)Math.exp(-19.0f * f5);
        fm += ((float)n4 - fm) * f6;
        if (Math.abs((float)n4 - fm) < 0.001f) {
            fm = n4;
        }
        if ((fn *= (float)Math.exp(-7.0f * f5)) < 0.01f) {
            fn = 0.0f;
        }
        float f7 = fn * fn;
        float f8 = f2 + fm * 20.0f;
        Color color2 = ColorUtils.getPrimaryColor();
        Color color3 = ColorUtils.fade(color2, Color.WHITE, 0.28f);
        float f9 = f7 * 3.0f;
        float f10 = f8 - 1.0f - f9;
        float f11 = f3 - 1.0f - f9;
        float f12 = 20.0f + f9 * 2.0f;
        float f13 = 20.0f + f9 * 2.0f;
        Render2D.drawRoundedRect(class_45872, f10, f11, f12, f13, 5.0f + f9, ColorUtils.alpha(color3, Math.round(140.0f + 60.0f * f7)));
        Render2D.drawBorder(class_45872, f10, f11, f12, f13, 5.0f + f9, 2.0f + f7 * 0.8f, new ColorRGBA(ColorUtils.alpha(color3, 255)));
        for (int i = 0; i < 9; ++i) {
            boolean bl;
            class_1799 class_17992 = class_7462.method_31548().method_5438(i);
            if (class_17992.method_7960()) continue;
            float f14 = f2 + (float)i * 20.0f;
            n = Math.round(f14 + 1.0f);
            int n5 = Math.round(f3 + 1.0f);
            boolean bl2 = bl = i == n4;
            if (bl && f7 > 0.001f) {
                float f15 = 1.0f + f7 * 0.18f;
                float f16 = (float)n + 8.0f;
                float f17 = (float)n5 + 8.0f;
                class_45872.method_22903();
                class_45872.method_46416(f16, f17, 0.0f);
                class_45872.method_22905(f15, f15, 1.0f);
                class_45872.method_46416(-f16, -f17, 0.0f);
                class_3322.method_51423((class_1309)class_7462, class_17992, n, n5, n + n5 * n2);
                class_45872.method_22909();
            } else {
                class_3322.method_51423((class_1309)class_7462, class_17992, n, n5, n + n5 * n2);
            }
            class_3322.method_51431(class_3102.field_1772, class_17992, n, n5);
        }
        class_1799 class_17993 = class_7462.method_6079();
        if (!class_17993.method_7960()) {
            float f18 = f2 - 2.0f - 2.0f - 18.0f;
            Render2D.drawRoundedRect(class_45872, f18, f3, 18.0f, 18.0f, 4.0f, color);
            int n6 = Math.round(f18 + 1.0f);
            n = Math.round(f3 + 1.0f);
            class_3322.method_51423((class_1309)class_7462, class_17993, n6, n, 0);
            class_3322.method_51431(class_3102.field_1772, class_17993, n6, n);
        }
    }
}

