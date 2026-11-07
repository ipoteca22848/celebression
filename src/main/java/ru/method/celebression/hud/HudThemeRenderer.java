package ru.method.celebression.hud;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_4587;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.ui.UiThemeController;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public final class HudThemeRenderer {
    private HudThemeRenderer() {
    }

    public static ThemePalette cb() {
        return UI.getHudShape();
    }

    public static float f(float f, float f2) {
        float f3 = Math.max(0.0f, Math.min(f, f2)) * 0.5f;
        ThemePalette themePalette = HudThemeRenderer.cb();
        float f4 = switch ((themePalette == null ? ThemePalette.gu : themePalette).ordinal()) {
            case 2 -> 3.0f;
            case 1 -> 10.0f;
            default -> 5.0f;
        };
        return Math.min(f4, f3);
    }

    public static boolean active() {
        return UI.isLinkGuiStyle();
    }

    public static ThemeMetrics cc() {
        return UI.getHudVariant();
    }

    public static Color cd() {
        ClientTheme clientTheme = UiThemeController.oX();
        return clientTheme != null ? clientTheme.pf() : ColorUtils.getBackgroundColor();
    }

    public static Color ce() {
        ClientTheme clientTheme = UiThemeController.oX();
        return clientTheme != null ? clientTheme.pg() : ColorUtils.getTextColor();
    }

    public static Color accent() {
        return ColorUtils.getPrimaryColor();
    }

    public static Color cf() {
        return ColorUtils.getSecondaryColor();
    }

    public static boolean isLightTheme() {
        Color color = HudThemeRenderer.cd();
        return HudThemeRenderer.a(color) > 0.6f;
    }

    public static Color c(int n) {
        return ColorUtils.alpha(HudThemeRenderer.cd(), HudThemeRenderer.g(n));
    }

    public static Color d(int n) {
        return ColorUtils.alpha(HudThemeRenderer.ce(), HudThemeRenderer.g(n));
    }

    public static Color e(int n) {
        Color color = ColorUtils.mix(HudThemeRenderer.ce(), HudThemeRenderer.cd(), 0.45f);
        return ColorUtils.alpha(color, HudThemeRenderer.g(n));
    }

    public static Color accent(int n) {
        return ColorUtils.alpha(HudThemeRenderer.accent(), HudThemeRenderer.g(n));
    }

    private static Color a(float f, int n) {
        Color color = HudThemeRenderer.accent();
        Color color2 = HudThemeRenderer.cd();
        int n2 = Math.round((float)color.getRed() * f + (float)color2.getRed() * (1.0f - f));
        int n3 = Math.round((float)color.getGreen() * f + (float)color2.getGreen() * (1.0f - f));
        int n4 = Math.round((float)color.getBlue() * f + (float)color2.getBlue() * (1.0f - f));
        return new Color(HudThemeRenderer.clampChannel(n2), HudThemeRenderer.clampChannel(n3), HudThemeRenderer.clampChannel(n4), HudThemeRenderer.g(n));
    }

    private static int clampChannel(int n) {
        return Math.max(0, Math.min(255, n));
    }

    public static Color f(int n) {
        Color color = HudThemeRenderer.cd();
        Color color2 = ColorUtils.mix(color, HudThemeRenderer.isLightTheme() ? Color.BLACK : Color.WHITE, 0.16f);
        return ColorUtils.alpha(color2, HudThemeRenderer.g(n));
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        HudThemeRenderer.a(HudThemeRenderer.cc(), class_45872, f, f2, f3, f4, f5, f6);
    }

    public static void a(ThemeMetrics themeMetrics, class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.max(0.0f, Math.min(1.0f, f6));
        Color color = HudThemeRenderer.cd();
        Color color2 = HudThemeRenderer.accent();
        switch ((themeMetrics == null ? ThemeMetrics.gz : themeMetrics).ordinal()) {
            case 0: {
                Render2D.drawBorder(class_45872, f - 6.0f, f2 - 6.0f + 1.5f, f3 + 12.0f, f4 + 12.0f, f5 + 6.0f, 1.5f, 6.0f, ColorUtils.alpha(Color.BLACK, Math.round(46.0f * f7)));
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, HudThemeRenderer.a(0.05f, Math.round(238.0f * f7)), HudThemeRenderer.a(0.012f, Math.round(248.0f * f7)));
                Color color3 = ColorUtils.mix(color2, Color.WHITE, 0.22f);
                float f8 = Math.min(f4 * 0.5f, 15.0f);
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f8, f5, ColorUtils.alpha(color3, Math.round(30.0f * f7)), ColorUtils.alpha(color3, 0));
                float f9 = Math.min(f4 * 0.32f, 10.0f);
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2 + f4 - f9, f3, f9, f5, new Color(0, 0, 0, 0), ColorUtils.alpha(Color.BLACK, Math.round(58.0f * f7)));
                Color color4 = ColorUtils.mix(ColorUtils.mix(color, HudThemeRenderer.isLightTheme() ? Color.BLACK : Color.WHITE, 0.14f), color2, 0.35f);
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 1.6f, ColorUtils.alpha(color4, Math.round(120.0f * f7)));
                Render2D.drawRoundedRect(class_45872, f + f5, f2 + 1.0f, f3 - f5 * 2.0f, 1.0f, 0.5f, ColorUtils.alpha(Color.WHITE, Math.round(16.0f * f7)));
                break;
            }
            case 1: {
                float f10 = Math.min(f5, 4.0f);
                Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f10, HudThemeRenderer.a(0.02f, Math.round(120.0f * f7)));
                float f11 = 2.2f;
                float f12 = Math.min(f4 * 0.18f, f5 + 1.0f);
                Render2D.drawRoundedVerticalGradient(class_45872, f + 2.0f, f2 + f12, f11, f4 - f12 * 2.0f, f11 * 0.5f, ColorUtils.alpha(color2, Math.round(235.0f * f7)), ColorUtils.alpha(HudThemeRenderer.cf(), Math.round(235.0f * f7)));
                break;
            }
            case 2: {
                Render2D.drawBorder(class_45872, f - 5.0f, f2 - 5.0f, f3 + 10.0f, f4 + 10.0f, f5 + 5.0f, 1.5f, 5.0f, ColorUtils.alpha(color2, Math.round(72.0f * f7)));
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, HudThemeRenderer.a(0.02f, Math.round(236.0f * f7)), HudThemeRenderer.a(0.06f, Math.round(248.0f * f7)));
                Render2D.drawRoundedRect(class_45872, f + f5, f2 + 1.2f, f3 - f5 * 2.0f, 1.0f, 0.5f, ColorUtils.alpha(Color.WHITE, Math.round(22.0f * f7)));
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 2.0f, ColorUtils.alpha(color2, Math.round(220.0f * f7)));
                Render2D.drawBorder(class_45872, f + 1.2f, f2 + 1.2f, f3 - 2.4f, f4 - 2.4f, Math.max(0.0f, f5 - 1.2f), 1.0f, 1.2f, ColorUtils.alpha(color2, Math.round(60.0f * f7)));
                break;
            }
            case 3: {
                Color color5 = ColorUtils.alpha(ColorUtils.mix(color, Color.WHITE, 0.07f), Math.round(236.0f * f7));
                Color color6 = ColorUtils.alpha(ColorUtils.mix(color, Color.BLACK, 0.34f), Math.round(248.0f * f7));
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, color5, color6);
                float f13 = Math.min(f4 * 0.3f, 6.0f);
                Render2D.drawRoundedRect(class_45872, f + f5 * 0.6f, f2 + 0.8f, f3 - f5 * 1.2f, f13, f5 * 0.5f, ColorUtils.alpha(Color.WHITE, Math.round(26.0f * f7)));
                float f14 = 2.2f;
                Render2D.drawRoundedVerticalGradient(class_45872, f + 1.6f, f2 + f5 * 0.55f, f14, f4 - f5 * 1.1f, f14 * 0.5f, ColorUtils.alpha(color2, Math.round(240.0f * f7)), ColorUtils.alpha(HudThemeRenderer.cf(), Math.round(240.0f * f7)));
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.1f, 2.6f, ColorUtils.alpha(color2, Math.round(125.0f * f7)));
                break;
            }
            case 5: {
                float f15 = Math.min(f5, 2.2f);
                Color color7 = ColorUtils.alpha(ColorUtils.mix(color, Color.BLACK, 0.1f), Math.round(246.0f * f7));
                Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f15, color7);
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f15, 1.0f, 2.0f, ColorUtils.alpha(color2, Math.round(135.0f * f7)));
                break;
            }
            case 6: {
                Color color8 = ColorUtils.alpha(ColorUtils.mix(color, Color.WHITE, 0.06f), Math.round(236.0f * f7));
                Color color9 = ColorUtils.alpha(ColorUtils.mix(color, Color.BLACK, 0.3f), Math.round(248.0f * f7));
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, color8, color9);
                float f16 = Math.min(2.5f, f4 * 0.12f);
                Render2D.drawRoundedHorizontalGradient(class_45872, f + f5 * 0.5f, f2, f3 - f5, f16, f16 * 0.5f, ColorUtils.alpha(color2, Math.round(220.0f * f7)), ColorUtils.alpha(HudThemeRenderer.cf(), Math.round(220.0f * f7)));
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 2.0f, ColorUtils.alpha(color2, Math.round(95.0f * f7)));
                break;
            }
            case 7: {
                Color color10 = ColorUtils.alpha(ColorUtils.mix(color, Color.BLACK, 0.12f), Math.round(140.0f * f7));
                Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color10);
                Color color11 = ColorUtils.alpha(color2, Math.round(235.0f * f7));
                float f17 = Math.min(f3, f4) * 0.3f;
                float f18 = 1.4f;
                Render2D.drawRoundedRect(class_45872, f, f2, f17, f18, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f, f2, f18, f17, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f + f3 - f17, f2, f17, f18, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f + f3 - f18, f2, f18, f17, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f, f2 + f4 - f18, f17, f18, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f, f2 + f4 - f17, f18, f17, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f + f3 - f17, f2 + f4 - f18, f17, f18, f18 * 0.5f, color11);
                Render2D.drawRoundedRect(class_45872, f + f3 - f18, f2 + f4 - f17, f18, f17, f18 * 0.5f, color11);
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 2.0f, ColorUtils.alpha(color2, Math.round(80.0f * f7)));
                break;
            }
            default: {
                Color color12 = ColorUtils.alpha(ColorUtils.mix(color, Color.WHITE, 0.05f), Math.round(232.0f * f7));
                Color color13 = ColorUtils.alpha(ColorUtils.mix(color, Color.BLACK, 0.26f), Math.round(246.0f * f7));
                Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, color12, color13);
                Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 2.0f, ColorUtils.alpha(color2, Math.round(115.0f * f7)));
            }
        }
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.max(0.0f, Math.min(1.0f, f6));
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, HudThemeRenderer.f(Math.round(205.0f * f7)));
        ThemeMetrics themeMetrics = HudThemeRenderer.cc();
        if (themeMetrics == ThemeMetrics.gG || themeMetrics == ThemeMetrics.gF) {
            Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 1.6f, ColorUtils.alpha(HudThemeRenderer.accent(), Math.round(70.0f * f7)));
        } else if (themeMetrics == ThemeMetrics.gz || themeMetrics == ThemeMetrics.gB) {
            Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 1.4f, ColorUtils.alpha(HudThemeRenderer.isLightTheme() ? Color.BLACK : Color.WHITE, Math.round(34.0f * f7)));
        }
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = Math.max(0.0f, Math.min(1.0f, f7));
        float f9 = Math.max(0.0f, Math.min(1.0f, f6));
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, HudThemeRenderer.f(Math.round(200.0f * f8)));
        if (f9 <= 0.0f) {
            return;
        }
        float f10 = Math.max(f4, f3 * f9);
        Render2D.drawRoundedHorizontalGradient(class_45872, f, f2, f10, f4, f5, ColorUtils.alpha(HudThemeRenderer.accent(), Math.round(245.0f * f8)), ColorUtils.alpha(HudThemeRenderer.cf(), Math.round(245.0f * f8)));
    }

    private static float a(Color color) {
        return (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f;
    }

    private static int g(int n) {
        return Math.max(0, Math.min(255, n));
    }

    public static enum ThemePalette
    implements NamedOption  {
        gu("settings.ui.hud_shape.rounded"),
        gv("settings.ui.hud_shape.pill"),
        gw("settings.ui.hud_shape.sharp");
        private final String gx;

private ThemePalette(String string2) {
            this.gx = string2;
        }

        @Override
        public String getName() {
            return this.gx;
        }

}

    public static enum ThemeMetrics
    implements NamedOption  {
        gz("settings.ui.hud_style.card"),
        gA("settings.ui.hud_style.minimal"),
        gB("settings.ui.hud_style.neon"),
        gC("settings.ui.hud_style.aurora"),
        gD("settings.ui.hud_style.glass"),
        gE("settings.ui.hud_style.solid"),
        gF("settings.ui.hud_style.gradient"),
        gG("settings.ui.hud_style.outline");
        private final String gH;

private ThemeMetrics(String string2) {
            this.gH = string2;
        }

        @Override
        public String getName() {
            return this.gH;
        }

}
}

