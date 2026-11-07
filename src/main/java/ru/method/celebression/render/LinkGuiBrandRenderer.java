package ru.method.celebression.render;

import ru.method.celebression.animation.Animation;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.render.ColorPalette;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class LinkGuiBrandRenderer {
    private static final float Ti = 1.0f;
    private static final class_2960 Tj = Celebression.id("textures/gui/logo.png");
    private static boolean Tk;

    public static void a(class_4587 class_45872, int n, int n2, float f) {
        if (f <= 0.001f) {
            return;
        }
        Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, n, n2, 0.0f, new Color(0, 0, 0, Math.round(76.0f * f)));
    }

    public static void j(class_4587 class_45872, float f, float f2, float f3, float f4) {
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, 8.0f, LinkGuiBrandRenderer.n(0.03f, 0.82f));
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, 8.0f, 0.42f);
    }

    public static void g(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        LinkGuiBrandRenderer.k(class_45872, f, f2, f3, f4, f5);
        LinkGuiBrandRenderer.l(class_45872, f, f2, f3, f4, f5);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, LinkGuiBrandRenderer.n(0.05f, 0.5f));
        Color color = LinkGuiBrandRenderer.mB();
        Color color2 = LinkGuiBrandRenderer.mC();
        UiRenderUtils.a(class_45872, f, f2, f3, f4, f5, color, color2);
        float f6 = Math.min(f4 * 0.45f, 200.0f);
        Color color3 = InterpolationUtils.lerp(LinkGuiBrandRenderer.accent(), Color.WHITE, 0.22f);
        UiRenderUtils.b(class_45872, f, f2, f3, f6, f5, InterpolationUtils.e(color3, 0.14f), InterpolationUtils.e(color3, 0.0f));
        float f7 = Math.min(f4 * 0.28f, 110.0f);
        UiRenderUtils.b(class_45872, f, f2 + f4 - f7, f3, f7, f5, new Color(0, 0, 0, 0), new Color(0, 0, 0, 64));
        Color color4 = LinkGuiBrandRenderer.mD();
        UiRenderUtils.d(class_45872, f, f2, f3, f4, f5, 1.0f, InterpolationUtils.e(color4, 0.9f), InterpolationUtils.e(color4, 0.25f));
        LinkGuiBrandRenderer.m(class_45872, f, f2, f3, f4, f5);
        UiRenderUtils.drawRoundedRect(class_45872, f + f5, f2 + 1.0f, f3 - f5 * 2.0f, 1.0f, 0.5f, new Color(255, 255, 255, 16));
        UiRenderUtils.b(class_45872, f + f5, f2 + 2.0f, f3 - f5 * 2.0f, 14.0f, 0.0f, new Color(255, 255, 255, 9), new Color(255, 255, 255, 0));
    }

    private static Color g(Color color) {
        float f = LinkGuiRenderContext.getFadeMultiplier();
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * f))));
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        Color color = LinkGuiBrandRenderer.accent();
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, InterpolationUtils.e(color, 0.22f));
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, f5, 1.0f);
    }

    public static void h(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, LinkGuiBrandRenderer.n(0.06f, 0.76f));
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, f5, 0.35f);
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl, float f6) {
        float f7 = bl ? Math.max(f6, 0.85f) : f6;
        Color color = LinkGuiBrandRenderer.n(0.025f, 0.68f);
        Color color2 = LinkGuiBrandRenderer.n(0.15f, 0.78f * f7);
        UiRenderUtils.b(class_45872, f, f2, f3, f4, f5, color, InterpolationUtils.e(color, 0.0f));
        UiRenderUtils.b(class_45872, f, f2, f3, f4, f5, color2, InterpolationUtils.e(color2, 0.0f));
        Color color3 = LinkGuiBrandRenderer.mD();
        float f8 = Math.max(0.25f, f7 * 0.85f);
        Color color4 = InterpolationUtils.lerp(InterpolationUtils.e(color3, 0.12f), color3, f8);
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 1.0f, InterpolationUtils.e(color4, 0.5f));
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl) {
        float f6 = f3 / 2.0f;
        Color color = LinkGuiBrandRenderer.g(ThemeTransition.d(48));
        Color color2 = LinkGuiBrandRenderer.g(ThemeTransition.d(22));
        Color color3 = LinkGuiBrandRenderer.accent();
        Color color4 = LinkGuiBrandRenderer.g(InterpolationUtils.e(color3, 0.95f));
        Color color5 = LinkGuiBrandRenderer.g(InterpolationUtils.e(color3, 0.5f));
        if (bl) {
            UiRenderUtils.b(class_45872, f, f2, f3, f4, f6, color4, color5);
            return;
        }
        UiRenderUtils.b(class_45872, f, f2, f3, f4, f6, color, color2);
        if (f5 <= 0.002f) {
            return;
        }
        float f7 = f4 * Math.max(0.0f, Math.min(1.0f, f5));
        if (f7 <= 0.5f) {
            return;
        }
        float f8 = f2 + f4 - f7;
        float f9 = f7 >= f4 - 0.01f ? f6 : Math.min(f6, f7 / 2.0f);
        UiRenderUtils.b(class_45872, f, f8, f3, f7, f9, color4, color5);
    }

    public static void g(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = (float)Math.round(Math.max(0.0f, Math.min(1.0f, f6)) * 16.0f) / 16.0f;
        float f8 = LinkGuiRenderContext.getFadeMultiplier();
        if (f7 <= 0.02f || f8 <= 0.01f) {
            return;
        }
        float f9 = 7.0f * f7;
        float f10 = 2.0f + 3.0f * f7;
        int n = Math.max(0, Math.min(255, Math.round(0.12f * f7 * f8 * 255.0f)));
        if (n <= 0) {
            return;
        }
        Render2D.drawBorder(class_45872, f - f9, f2 - f9 + f10, f3 + 2.0f * f9, f4 + 2.0f * f9, f5 + f9, 1.5f, f9, new Color(0, 0, 0, n));
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, float f5) {
        Color color = LinkGuiBrandRenderer.accent();
        float f6 = bl ? Math.max(f5, 0.85f) : f5 * 0.35f;
        Color color2 = bl ? InterpolationUtils.e(color, 0.2f + 0.12f * f5) : LinkGuiBrandRenderer.n(0.07f, 0.72f);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f3, f4, color2);
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f3, f4, Math.max(0.3f, f6));
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, String string, Color color, float f4) {
        float f5 = f + f3 / 2.0f;
        float f6 = f2 + f3 / 2.0f;
        float f7 = UiRenderUtils.a(Fonts.ICONSNEW, f4);
        UiRenderUtils.b(class_45872, Fonts.ICONSNEW, string, f5, f6 - f7 / 2.0f, color, f4);
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3) {
        class_1044 class_10443;
        class_310 client = class_310.method_1551();
        if (!Tk && client != null && (class_10443 = client.method_1531().method_4619(Tj)) != null) {
            class_10443.method_4527(true, false);
            Tk = true;
        }
        Color accentColor = LinkGuiBrandRenderer.accent();
        float f4 = f3 / 2.0f;
        for (int i = 4; i >= 1; --i) {
            float f5 = (float)i * 1.5f;
            float f6 = 0.1f + (float)(4 - i) * 0.06f;
            Render2D.drawTexture(class_45872, f - f4 - f5, f2 - f4 - f5, f3 + 2.0f * f5, f3 + 2.0f * f5, 0.0f, Tj, InterpolationUtils.e(accentColor, f6));
        }
        Render2D.drawTexture(class_45872, f - f4, f2 - f4, f3, f3, 0.0f, Tj, accentColor);
    }

    public static void a(class_4587 class_45872, String string, String string2, float f, float f2) {
        LinkGuiBrandRenderer.e(class_45872, f, f2, 16.0f, 16.0f, 5.0f);
        LinkGuiBrandRenderer.a(class_45872, f, f2, 16.0f, string2, LinkGuiBrandRenderer.accent(), 8.0f);
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, string, f + 22.0f, f2 + 3.0f, LinkGuiBrandRenderer.accent(), 8.2f);
    }

    public static void i(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6;
        float f7;
        float f8 = Math.max(0.0f, Math.min(1.0f, f5));
        float f9 = f4 / 2.0f;
        Color color = LinkGuiBrandRenderer.accent();
        if (f8 > 0.04f) {
            for (int i = 2; i >= 1; --i) {
                f7 = (float)i * 1.6f;
                f6 = (i == 1 ? 0.12f : 0.06f) * f8;
                UiRenderUtils.drawRoundedRect(class_45872, f - f7, f2 - f7, f3 + 2.0f * f7, f4 + 2.0f * f7, f9 + f7, InterpolationUtils.e(color, f6));
            }
        }
        Color color2 = InterpolationUtils.lerp(LinkGuiBrandRenderer.n(0.07f, 0.72f), InterpolationUtils.e(color, 0.32f), f8);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f9, color2);
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, f9, Math.max(0.3f, f8));
        f7 = f4 - 4.0f;
        f6 = f + 2.0f + (f3 - 4.0f - f7) * f8;
        float f10 = f2 + 2.0f;
        Color color3 = InterpolationUtils.lerp(new Color(150, 150, 158), Color.WHITE, f8);
        UiRenderUtils.drawRoundedRect(class_45872, f6, f10, f7, f7, f7 / 2.0f, color3);
        if (f8 > 0.04f) {
            LinkGuiBrandRenderer.j(class_45872, f6, f10, f7, f7, f7 / 2.0f, f8);
        }
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl) {
        LinkGuiBrandRenderer.h(class_45872, f, f2, f3, f4, f5, bl ? 1.0f : 0.0f);
    }

    public static void h(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.max(0.0f, Math.min(1.0f, f6));
        Color color = LinkGuiBrandRenderer.accent();
        Color color2 = InterpolationUtils.lerp(LinkGuiBrandRenderer.n(0.07f, 0.72f), InterpolationUtils.e(color, 0.18f), f7);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color2);
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, f5, Math.max(0.35f, f7));
    }

    public static void f(class_4587 class_45872, float f, float f2, float f3) {
        boolean bl = LinkGuiBrandRenderer.mz();
        Color color = bl ? new Color(0, 0, 0) : new Color(255, 255, 255);
        for (int i = 3; i >= 1; --i) {
            float f4 = (float)i * 1.425f;
            float f5 = (bl ? 0.2f : 0.29f) * (0.4f + 0.22f * (float)(3 - i));
            float f6 = f3 + 2.0f * f4;
            UiRenderUtils.drawRoundedRect(class_45872, f - f6 / 2.0f, f2 - f6 / 2.0f, f6, f6, f6 / 2.0f, InterpolationUtils.e(color, f5));
        }
        UiRenderUtils.drawRoundedRect(class_45872, f - f3 / 2.0f, f2 - f3 / 2.0f, f3, f3, f3 / 2.0f, Color.WHITE);
    }

    private static boolean mz() {
        Color color = ThemeTransition.pf();
        float f = (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f;
        return f > 0.6f;
    }

    public static void i(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.max(0.0f, Math.min(1.0f, f5));
        float f8 = f4 / 2.0f;
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f8, ThemeTransition.d(28));
        if (f7 > 0.001f) {
            Color color = InterpolationUtils.lerp(InterpolationUtils.e(LinkGuiBrandRenderer.accent(), 0.55f), LinkGuiBrandRenderer.accent(), Math.max(0.35f, f6));
            float f9 = f3 * f7;
            float f10 = Math.min(f8, f9 / 2.0f);
            UiRenderUtils.drawRoundedRect(class_45872, f, f2, f9, f4, f10, color);
            float f11 = Math.max(f4 + 2.0f, 5.0f);
            float f12 = Math.max(f + f8, Math.min(f + f3 - f8, f + f9));
            LinkGuiBrandRenderer.f(class_45872, f12, f2 + f4 / 2.0f, f11);
        }
    }

    public static void g(class_4587 class_45872, float f, float f2, float f3) {
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, 1.0f, 0.5f, ThemeTransition.d(20));
    }

    public static void k(class_4587 class_45872, float f, float f2, float f3, float f4) {
        LinkGuiBrandRenderer.j(class_45872, f, f2, f3, f4, 0.0f);
    }

    public static void j(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = Math.max(0.0f, Math.min(1.0f, f5));
        Color color = LinkGuiBrandRenderer.n(0.04f + f6 * 0.02f, 0.74f + f6 * 0.08f);
        UiRenderUtils.drawRoundedRect(class_45872, f, f2, f3, f4, f4 / 2.0f, color);
        Color color2 = LinkGuiBrandRenderer.mD();
        float f7 = 0.35f + f6 * 0.65f;
        Color color3 = InterpolationUtils.lerp(InterpolationUtils.e(color2, 0.12f), color2, f7);
        UiRenderUtils.f(class_45872, f, f2, f3, f4, f4 / 2.0f, 1.0f, color3, InterpolationUtils.e(color3, 0.0f));
    }

    private static void j(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        Color color = LinkGuiBrandRenderer.accent();
        Color color2 = InterpolationUtils.lerp(InterpolationUtils.e(color, 0.12f), color, Math.max(0.0f, Math.min(1.0f, f6)));
        UiRenderUtils.d(class_45872, f, f2, f3, f4, f5, 1.0f, color2, InterpolationUtils.e(color2, 0.0f));
    }

    private static void k(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        if (f6 <= 0.01f) {
            return;
        }
        float f7 = 16.0f;
        int n = Math.max(0, Math.min(255, Math.round(0.22f * f6 * 255.0f)));
        if (n <= 0) {
            return;
        }
        Render2D.drawBorder(class_45872, f - f7, f2 - f7 + 2.0f, f3 + 2.0f * f7, f4 + 2.0f * f7, f5 + f7, 1.5f, f7, new Color(0, 0, 0, n));
    }

    private static void l(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        if (f6 <= 0.05f) {
            return;
        }
        Color color = LinkGuiBrandRenderer.accent();
        float f7 = 22.0f;
        float f8 = 0.18f * f6;
        Color color2 = InterpolationUtils.lerp(Color.WHITE, color, 0.78f);
        Render2D.drawBorder(class_45872, f - f7, f2 - f7, f3 + 2.0f * f7, f4 + 2.0f * f7, f5 + f7, 1.5f, f7, new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), Math.max(0, Math.min(255, Math.round(f8 * 255.0f)))));
    }

    private static void m(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        float f6 = LinkGuiRenderContext.getFadeMultiplier();
        if (f6 <= 0.01f) {
            return;
        }
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f5, 1.0f, InterpolationUtils.e(LinkGuiBrandRenderer.accent(), 0.22f));
    }

    private static Color accent() {
        return ThemeTransition.accent();
    }

    public static Color mA() {
        return LinkGuiBrandRenderer.n(0.03f, 1.0f);
    }

    private static Color mB() {
        return LinkGuiBrandRenderer.n(0.045f, 0.85f);
    }

    private static Color mC() {
        return LinkGuiBrandRenderer.n(0.012f, 0.87f);
    }

    private static Color mD() {
        return InterpolationUtils.lerp(ColorPalette.aiy, LinkGuiBrandRenderer.accent(), 0.35f);
    }

    private static Color n(float f, float f2) {
        Color color = LinkGuiBrandRenderer.accent();
        Color color2 = ThemeTransition.pf();
        int n = Math.round((float)color.getRed() * f + (float)color2.getRed() * (1.0f - f));
        int n2 = Math.round((float)color.getGreen() * f + (float)color2.getGreen() * (1.0f - f));
        int n3 = Math.round((float)color.getBlue() * f + (float)color2.getBlue() * (1.0f - f));
        float f3 = LinkGuiRenderContext.getFadeMultiplier();
        int n4 = Math.round(Math.max(0.0f, Math.min(1.0f, f2)) * 255.0f * f3);
        return new Color(Math.min(255, n), Math.min(255, n2), Math.min(255, n3), n4);
    }

    private static Color a(Color color, float f, float f2) {
        int n = Math.round((float)color.getRed() * f + 18.0f * (1.0f - f));
        int n2 = Math.round((float)color.getGreen() * f + 19.0f * (1.0f - f));
        int n3 = Math.round((float)color.getBlue() * f + 24.0f * (1.0f - f));
        int n4 = Math.round(Math.max(0.0f, Math.min(1.0f, f2)) * 255.0f);
        return new Color(Math.min(255, n), Math.min(255, n2), Math.min(255, n3), n4);
    }

    @Generated
    private LinkGuiBrandRenderer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
