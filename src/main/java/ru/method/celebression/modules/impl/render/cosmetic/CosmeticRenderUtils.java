package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_4587;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBlur;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLayout;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;

public final class CosmeticRenderUtils {
    private static final float MU = 0.75f;
    private static final Color MV = new Color(255, 255, 255, 8);
    private static final Color MW = new Color(119, 82, 214, 14);
    private static final Color MX = new Color(0, 0, 0, 0);

    public static void a(class_4587 class_45872, int n, int n2, float f) {
        if (f <= 0.001f) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        if (ColorUtils.isBlurEnabled()) {
            int n3 = Math.round(150.0f * f);
            int n4 = n3 << 24 | 0x80000 | 0x600 | 0x10;
            BuiltBlur.renderBackdrop(matrix4f, 0.0f, 0.0f, n, n2, 16.0f, n4);
        }
        Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, n, n2, 0.0f, new Color(8, 6, 16, Math.round(55.0f * f)));
        Render2D.drawVerticalGradient(class_45872, 0.0f, 0.0f, n, n2, new Color(8, 6, 16, Math.round(25.0f * f)), new Color(8, 6, 16, Math.round(120.0f * f)));
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        CosmeticRenderUtils.f(class_45872, f, f2, f3, f4, f5, f6);
        CosmeticRenderUtils.e(class_45872, f, f2, f3, f4, f5, f6);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, CosmeticColorUtils.c(new Color(26, 22, 37, 210), f6));
        CosmeticRenderUtils.f(class_45872, f, f2, f3, f4, f6);
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 0.75f, CosmeticColorUtils.c(new Color(119, 82, 214, 38), f6));
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, CosmeticColorUtils.c(CosmeticColors.NW, f6));
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 0.75f, CosmeticColorUtils.c(MW, f6));
        Render2D.drawHorizontalGradient(class_45872, f + 1.0f, f2 + 1.0f, f + f3 - 1.0f, f2 + 2.0f, new Color(255, 255, 255, Math.round(8.0f * f6)), new Color(255, 255, 255, Math.round(8.0f * f6)));
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        float f7 = f5 * 0.55f;
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f7, CosmeticColorUtils.c(CosmeticColors.NZ, f6));
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f7, 0.75f, CosmeticColorUtils.c(MV, f6));
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + 1.0f, f + f3 - 1.0f, f2 + 12.0f, new Color(0, 0, 0, Math.round(115.0f * f6)), MX);
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + f4 - 14.0f, f + f3 - 1.0f, f2 + f4 - 1.0f, MX, new Color(0, 0, 0, Math.round(115.0f * f6)));
    }

    public static void d(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = 3.6f;
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, 0.75f, CosmeticColorUtils.c(new Color(255, 255, 255, 8), f5));
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = f2 + f4 * 0.82f;
        float f7 = f3 * 0.48f;
        float f8 = f4 * 0.14f;
        float f9 = f + (f3 - f7) * 0.5f;
        UiRenderUtils.drawRoundedRect(class_45872, f9, f6 - f8 * 0.35f, f7, f8, f8 * 0.5f, CosmeticColorUtils.c(new Color(119, 82, 214, 31), f5));
        UiRenderUtils.drawRoundedRect(class_45872, f9 + f7 * 0.04f, f6 - f8 * 0.28f, f7 * 0.92f, f8 * 0.85f, f8 * 0.42f, CosmeticColorUtils.c(new Color(255, 255, 255, 13), f5));
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + 1.0f, f + f3 - 1.0f, f2 + f4 * 0.3f, new Color(0, 0, 0, Math.round(97.0f * f5)), MX);
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + f4 * 0.5f, f + f3 - 1.0f, f2 + f4 - 1.0f, MX, new Color(0, 0, 0, Math.round(128.0f * f5)));
    }

    public static void f(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = 4.4f;
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f6, CosmeticColorUtils.c(new Color(8, 6, 18, 184), f5));
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, 0.75f, CosmeticColorUtils.c(MV, f5));
    }

    public static void d(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        CosmeticRenderUtils.c(class_45872, f, f2, f3, f4, f5);
    }

    public static void d(class_4587 class_45872, float f, float f2, float f3) {
        float f4 = LinkGuiRenderContext.getFadeMultiplier();
        float f5 = f3 * 0.5f;
        UiRenderUtils.a(class_45872, f, f2, f3, f3, f5, CosmeticColorUtils.c(CosmeticColors.Oe, f4), CosmeticColorUtils.c(CosmeticColors.Of, f4));
        UiRenderUtils.c(class_45872, f - 0.5f, f2 - 0.5f, f3 + 1.0f, f3 + 1.0f, f5, 0.75f, CosmeticColorUtils.c(new Color(98, 70, 173, 82), f4));
        CosmeticLayout.f(class_45872, f + f3 * 0.5f, f2 + f3 * 0.5f, f3 * 0.4f, CosmeticColorUtils.c(Color.WHITE, f4));
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, boolean bl) {
        float f4 = LinkGuiRenderContext.getFadeMultiplier();
        float f5 = f3 * 0.5f;
        Color color = bl ? CosmeticColorUtils.c(new Color(119, 82, 214, 56), f4) : CosmeticColorUtils.c(new Color(14, 11, 24, 160), f4);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f3, f5, color);
        if (bl) {
            UiRenderUtils.c(class_45872, f, f2, f3, f3, f5, 0.75f, CosmeticColorUtils.c(new Color(119, 82, 214, 60), f4));
        }
        CosmeticLayout.m(class_45872, f + f3 * 0.5f, f2 + f3 * 0.5f, f3 * 0.48f, CosmeticColorUtils.c(new Color(255, 255, 255, 217), f4));
    }

    public static void g(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = f4 * 0.5f;
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, CosmeticColorUtils.c(CosmeticColors.NK, f5), CosmeticColorUtils.c(CosmeticColors.NN, f5));
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, 0.75f, CosmeticColorUtils.c(CosmeticColors.NP, f5));
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + 1.0f, f + f3 - 1.0f, f2 + Math.min(14.0f, f4 * 0.22f), new Color(255, 255, 255, Math.round(26.0f * f5)), MX);
        UiRenderUtils.c(class_45872, f + 1.0f, f2 + 1.0f, f3 - 2.0f, f4 - 2.0f, f6 - 1.0f, 0.75f, CosmeticColorUtils.c(new Color(188, 146, 255, 31), f5));
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl, boolean bl2) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        Color color = bl ? CosmeticColorUtils.c(CosmeticColors.NY, f6) : CosmeticColorUtils.c(CosmeticColors.NX, f6);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color);
        CosmeticRenderUtils.a(class_45872, f, f2, f3, f4, bl, f6);
        CosmeticRenderUtils.d(class_45872, f, f2, f3, f4, f5, f6);
        if (bl) {
            UiRenderUtils.c(class_45872, f - 4.0f, f2 - 4.0f, f3 + 8.0f, f4 + 8.0f, f5 + 4.0f, 1.0f, InterpolationUtils.e(CosmeticColors.NH, 0.28f * f6));
            UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 1.5f, CosmeticColorUtils.c(CosmeticColors.NH, f6));
        } else {
            UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 0.75f, CosmeticColorUtils.c(MV, f6));
            if (bl2) {
                UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 0.75f, CosmeticColorUtils.c(new Color(119, 82, 214, 41), f6));
            }
        }
    }

    public static void h(class_4587 class_45872, float f, float f2, float f3, float f4) {
        CosmeticRenderUtils.a(class_45872, f, f2, f3, f4, false);
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = f4 * 0.55f;
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f3, f6, CosmeticColorUtils.c(CosmeticColors.Ob, f5));
        Color color = bl ? CosmeticColorUtils.c(new Color(119, 82, 214, 36), f5) : CosmeticColorUtils.c(new Color(255, 255, 255, 10), f5);
        UiRenderUtils.c(class_45872, f, f2, f3, f3, f6, 0.75f, color);
        Render2D.drawVerticalGradient(class_45872, f + 2.0f, f2 + f3 * 0.55f, f + f3 - 2.0f, f2 + f3 - 2.0f, MX, new Color(0, 0, 0, Math.round(102.0f * f5)));
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        float f6 = f4 * 0.5f;
        if (bl) {
            UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, CosmeticColorUtils.c(CosmeticColors.NH, f5), CosmeticColorUtils.c(CosmeticColors.NN, f5));
            UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, 0.75f, CosmeticColorUtils.c(new Color(255, 255, 255, 26), f5));
        } else {
            UiRenderUtils.b(class_45872, f, f2, f3, f4, f6, CosmeticColorUtils.c(new Color(62, 56, 82, 245), f5), CosmeticColorUtils.c(new Color(42, 37, 56, 245), f5));
            UiRenderUtils.c(class_45872, f, f2, f3, f4, f6, 0.75f, CosmeticColorUtils.c(new Color(255, 255, 255, 15), f5));
        }
        float f7 = f4 - 4.0f;
        float f8 = bl ? f + f3 - f7 - 2.0f : f + 2.0f;
        UiRenderUtils.b(class_45872, f8, f2 + 2.0f, f7, f7, f7 * 0.5f, CosmeticColorUtils.c(Color.WHITE, f5), CosmeticColorUtils.c(new Color(229, 231, 235), f5));
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, CosmeticColorUtils.c(new Color(119, 82, 214, 46), f6));
    }

    private static void d(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        Render2D.drawVerticalGradient(class_45872, f + 1.0f, f2 + 1.0f, f + f3 - 1.0f, f2 + 3.0f, new Color(255, 255, 255, Math.round(10.0f * f6)), MX);
        UiRenderUtils.c(class_45872, f + 1.0f, f2 + 1.0f, f3 - 2.0f, f4 - 2.0f, f5 - 1.0f, 0.75f, CosmeticColorUtils.c(new Color(119, 82, 214, 10), f6));
    }

    private static void e(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f6 <= 0.01f) {
            return;
        }
        UiRenderUtils.c(class_45872, f - 1.0f, f2 - 1.0f, f3 + 2.0f, f4 + 2.0f, f5 + 1.0f, 1.0f, CosmeticColorUtils.c(new Color(119, 82, 214, 71), f6 * 0.55f));
    }

    private static void f(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f6 <= 0.01f) {
            return;
        }
        int n = 5;
        float f7 = 14.0f;
        float f8 = 0.12f * f6;
        for (int i = n; i >= 1; --i) {
            float f9 = (float)i / (float)n;
            float f10 = f7 * f9;
            float f11 = f8 * (1.0f - f9) * (1.0f - f9);
            int n2 = Math.round(f11 * 255.0f);
            if (n2 <= 0) continue;
            UiRenderUtils.drawRoundedRect(class_45872, f - f10, f2 - f10 + 3.0f, f3 + f10 * 2.0f, f4 + f10 * 2.0f, f5 + f10, new Color(0, 0, 0, n2));
        }
    }

    private static void f(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = Math.min(280.0f, Math.min(f3, f4) * 0.5f);
        CosmeticRenderUtils.i(class_45872, f, f2, f6, f5 * 0.9f);
        CosmeticRenderUtils.i(class_45872, f + f3 - f6, f2, f6, f5 * 0.7f);
    }

    private static void i(class_4587 class_45872, float f, float f2, float f3, float f4) {
        if (f4 <= 0.01f) {
            return;
        }
        Color color = new Color(119, 82, 214, Math.round(18.0f * f4));
        Render2D.drawHorizontalGradient(class_45872, f, f2, f + f3, f2 + 1.0f, color, MX);
        Render2D.drawVerticalGradient(class_45872, f, f2, f + 1.0f, f2 + f3, color, MX);
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, float f5) {
        Color color = bl ? new Color(188, 146, 255, Math.round(36.0f * f5)) : new Color(119, 82, 214, Math.round(15.0f * f5));
        Color color2 = bl ? new Color(119, 82, 214, Math.round(26.0f * f5)) : new Color(104, 77, 180, Math.round(13.0f * f5));
        Render2D.drawHorizontalGradient(class_45872, f + 2.0f, f2 + 2.0f, f + f3 * 0.45f, f2 + f4 * 0.35f, color, MX);
        Render2D.drawHorizontalGradient(class_45872, f + f3 * 0.55f, f2 + f4 * 0.65f, f + f3 - 2.0f, f2 + f4 - 2.0f, MX, color2);
    }

    @Generated
    private CosmeticRenderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

