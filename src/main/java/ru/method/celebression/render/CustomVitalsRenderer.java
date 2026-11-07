package ru.method.celebression.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CustomVitalsRenderer {
    private static final float fq = 182.0f;
    private static final float fr = 18.0f;
    private static final Color fs = new Color(224, 50, 74);
    private static final Color ft = new Color(124, 179, 66);
    private static final Color fu = new Color(110, 74, 87);
    private static final Color fv = new Color(217, 164, 65);
    private static final Color fw = new Color(139, 195, 74);
    private static final Color fx = new Color(77, 155, 255);
    private static final Color fy = new Color(201, 106, 133);
    private static final Color fz = new Color(200, 210, 220);
    private static float fA = -1.0f;
    private static long lastMs = 0L;
    private static float fB = 0.0f;

    private CustomVitalsRenderer() {
    }

    public static void a(class_332 class_3322, Layout layout) {
        int n = class_3322.method_51421();
        int n2 = class_3322.method_51443();
        class_4587 class_45872 = class_3322.method_51448();
        float f = (float)n / 2.0f - 91.0f;
        float f2 = (float)n2 - 18.0f - 4.0f - 2.0f - 1.0f;
        switch ((layout.fC == null ? Mode.fW : layout.fC).ordinal()) {
            case 1: {
                CustomVitalsRenderer.c(class_45872, f, f2, layout);
                break;
            }
            case 0: {
                CustomVitalsRenderer.b(class_45872, f, f2, layout);
                break;
            }
            default: {
                CustomVitalsRenderer.a(class_45872, f, f2, layout);
            }
        }
    }

    private static void a(class_4587 class_45872, float f, float f2, Layout layout) {
        CustomVitalsRenderer.a(layout);
        float f3 = 6.0f;
        float f4 = 6.0f;
        float f5 = 3.0f;
        float f6 = 3.0f;
        float f7 = 8.0f;
        int n = Math.max(3, Math.min(10, layout.fD));
        float f8 = 87.0f;
        float f9 = (f8 - (float)(n - 1) * 3.0f) / (float)n;
        float f10 = 1.0f;
        if (layout.fF > 0.0f && layout.fF < 0.3f) {
            float f11 = (float)(System.currentTimeMillis() % 1000L) / 1000.0f;
            f10 = 0.72f + 0.28f * (0.5f + 0.5f * (float)Math.sin((double)f11 * Math.PI * 2.0));
        }
        int n2 = Math.round(248.0f * f10);
        Color color = layout.fS ? fu : (layout.fR ? ft : fs);
        Color color2 = layout.fT ? fw : fv;
        float f12 = f2 - 6.0f;
        float f13 = f12 - 3.0f - 6.0f;
        boolean bl = layout.fE && layout.fL > 0;
        boolean bl2 = layout.fN;
        float f14 = f13 - 2.0f - 3.0f;
        if (bl) {
            CustomVitalsRenderer.a(class_45872, f, f14, f8, f9, 3.0f, 3.0f, n, (float)layout.fL / 20.0f, fz, false, 210, 0.0f);
        }
        if (bl2) {
            CustomVitalsRenderer.a(class_45872, f + 182.0f - f8, f14, f8, f9, 3.0f, 3.0f, n, layout.fM, fx, true, 235, 0.0f);
        }
        float f15 = Math.max(0.0f, fA - layout.fF);
        CustomVitalsRenderer.a(class_45872, f, f13, f8, f9, 6.0f, 3.0f, n, layout.fF, color, false, n2, f15);
        if (fB > 0.01f) {
            Render2D.drawRoundedRect(class_45872, f - 1.0f, f13 - 1.0f, f8 + 2.0f, 8.0f, 3.0f, ColorUtils.alpha(Color.WHITE, Math.round(70.0f * fB)));
        }
        if (layout.fO) {
            CustomVitalsRenderer.a(class_45872, f + 182.0f - f8, f13, f8, f9, 6.0f, 3.0f, n, layout.fP, fy, true, 245, 0.0f);
        } else {
            CustomVitalsRenderer.a(class_45872, f + 182.0f - f8, f13, f8, f9, 6.0f, 3.0f, n, layout.fG, color2, true, 245, 0.0f);
        }
        boolean bl3 = layout.fO && layout.fQ > 0.0f;
        float f16 = bl3 ? layout.fQ : layout.fH;
        Color color3 = bl3 ? new Color(242, 197, 114) : HudThemeRenderer.accent();
        Color color4 = bl3 ? new Color(217, 154, 46) : HudThemeRenderer.cf();
        Render2D.drawRoundedRect(class_45872, f, f12, 182.0f, 6.0f, 3.0f, HudThemeRenderer.f(225));
        Render2D.drawBorder(class_45872, f, f12, 182.0f, 6.0f, 3.0f, 1.0f, 1.0f, ColorUtils.alpha(HudThemeRenderer.accent(), 45));
        if (f16 > 0.0f) {
            float f17 = Math.max(6.0f, 182.0f * CustomVitalsRenderer.clamp(f16));
            Render2D.drawRoundedHorizontalGradient(class_45872, f, f12, f17, 6.0f, 3.0f, ColorUtils.alpha(color3, 255), ColorUtils.alpha(color4, 255));
        }
        if (!bl3 && layout.fK > 0) {
            CustomVitalsRenderer.a(class_45872, f + 91.0f, f12 + 3.0f, 6.0f, Integer.toString(layout.fK));
        }
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, int n, float f7, Color color, boolean bl, int n2, float f8) {
        Render2D.drawRoundedRect(class_45872, f - 1.0f, f2 - 1.0f, f3 + 2.0f, f5 + 2.0f, 3.0f, HudThemeRenderer.f(120));
        float f9 = CustomVitalsRenderer.clamp(f7) * (float)n;
        float f10 = CustomVitalsRenderer.clamp(f7 + f8) * (float)n;
        Color color2 = ColorUtils.alpha(HudThemeRenderer.isLightTheme() ? Color.BLACK : Color.WHITE, 55);
        for (int i = 0; i < n; ++i) {
            float f11;
            float f12;
            float f13 = bl ? f + (float)(n - 1 - i) * (f4 + f6) : f + (float)i * (f4 + f6);
            Render2D.drawRoundedRect(class_45872, f13, f2, f4, f5, 2.0f, HudThemeRenderer.f(165));
            Render2D.drawBorder(class_45872, f13, f2, f4, f5, 2.0f, 1.0f, 1.0f, color2);
            float f14 = CustomVitalsRenderer.clamp(f9 - (float)i);
            if (f14 > 0.0f) {
                f12 = Math.max(1.5f, f4 * f14);
                f11 = bl ? f13 + (f4 - f12) : f13;
                Render2D.drawRoundedRect(class_45872, f11, f2, f12, f5, 2.0f, ColorUtils.alpha(color, n2));
            }
            if (!((f12 = CustomVitalsRenderer.clamp(f10 - (float)i) - f14) > 0.02f)) continue;
            f11 = Math.max(1.5f, f4 * f12);
            float f15 = bl ? f13 + (f4 - CustomVitalsRenderer.clamp(f9 - (float)i) * f4) : f13 + CustomVitalsRenderer.clamp(f9 - (float)i) * f4;
            float f16 = bl ? f15 - f11 : f15;
            Render2D.drawRoundedRect(class_45872, f16, f2, f11, f5, 2.0f, ColorUtils.alpha(Color.WHITE, 150));
        }
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, String string) {
        float f4 = 6.0f;
        float f5 = Fonts.ROUNDBOLD.getWidth(string, f4);
        float f6 = f3 + 3.0f;
        float f7 = f5 + 8.0f;
        float f8 = f - f7 / 2.0f;
        float f9 = f2 - f6 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f8, f9, f7, f6, f6 / 2.0f, HudThemeRenderer.f(235));
        Render2D.drawBorder(class_45872, f8, f9, f7, f6, f6 / 2.0f, 1.0f, 1.0f, ColorUtils.alpha(HudThemeRenderer.accent(), 95));
        Render2D.drawFont(class_45872, Fonts.ROUNDBOLD.getFont(f4), string, f - f5 / 2.0f, f2 - f4 / 2.0f - 0.5f, ColorUtils.alpha(HudThemeRenderer.cf(), 255));
    }

    private static void a(Layout layout) {
        long l = System.currentTimeMillis();
        float f = lastMs == 0L ? 0.016666668f : Math.min(0.1f, (float)(l - lastMs) / 1000.0f);
        lastMs = l;
        if (fA < 0.0f) {
            fA = layout.fF;
        }
        if (layout.fF < fA) {
            fB = 1.0f;
            fA += (layout.fF - fA) * (1.0f - (float)Math.exp(-6.0f * f));
        } else {
            fA = layout.fF;
        }
        fB *= (float)Math.exp(-4.0f * f);
        if (fB < 0.01f) {
            fB = 0.0f;
        }
    }

    private static void b(class_4587 class_45872, float f, float f2, Layout layout) {
        float f3 = 3.0f;
        float f4 = 4.0f;
        float f5 = 3.0f;
        float f6 = f2 - f3;
        float f7 = f6 - f5 - f4;
        float f8 = 88.0f;
        CustomVitalsRenderer.a(class_45872, f, f7, f8, f4, 2.0f, layout.fF, fs);
        CustomVitalsRenderer.a(class_45872, f + 182.0f - f8, f7, f8, f4, 2.0f, layout.fG, fv);
        CustomVitalsRenderer.a(class_45872, f, f6, 182.0f, f3, 1.5f, layout.fH, HudThemeRenderer.accent());
        if (layout.fK > 0) {
            String string = Integer.toString(layout.fK);
            float f9 = 6.5f;
            float f10 = Fonts.ROUNDBOLD.getWidth(string, f9);
            Render2D.drawFont(class_45872, Fonts.ROUNDBOLD.getFont(f9), string, f + 91.0f - f10 / 2.0f, f7 + f4 / 2.0f - f9 / 2.0f, ColorUtils.alpha(HudThemeRenderer.cf(), 255));
        }
    }

    private static void c(class_4587 class_45872, float f, float f2, Layout layout) {
        float f3 = 3.0f;
        float f4 = 4.0f;
        float f5 = 3.0f;
        float f6 = f3 * 2.0f + f4 * 3.0f + f5 * 2.0f;
        float f7 = f2 - f6;
        HudThemeRenderer.b(class_45872, f, f7, 182.0f, f6, HudThemeRenderer.f(182.0f, f6), 1.0f);
        float f8 = 15.0f;
        float f9 = 182.0f - f3 * 2.0f - f8;
        float f10 = f + f3;
        float f11 = f7 + f3;
        CustomVitalsRenderer.a(class_45872, f10, f11, f9, f4, layout.fF, fs, Integer.toString(layout.fI), f + 182.0f - f3);
        CustomVitalsRenderer.a(class_45872, f10, f11 += f4 + f5, f9, f4, layout.fG, fv, Integer.toString(layout.fJ), f + 182.0f - f3);
        CustomVitalsRenderer.a(class_45872, f10, f11 += f4 + f5, f9, f4, layout.fH, HudThemeRenderer.accent(), Integer.toString(layout.fK), f + 182.0f - f3);
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, String string, float f6) {
        CustomVitalsRenderer.a(class_45872, f, f2, f3, f4, 2.0f, f5, color);
        float f7 = 6.0f;
        float f8 = Fonts.ROUNDBOLD.getWidth(string, f7);
        Render2D.drawFont(class_45872, Fonts.ROUNDBOLD.getFont(f7), string, f6 - f8, f2 + (f4 - f7) / 2.0f - 0.5f, ColorUtils.alpha(HudThemeRenderer.ce(), 235));
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, HudThemeRenderer.f(200));
        f6 = CustomVitalsRenderer.clamp(f6);
        if (f6 <= 0.0f) {
            return;
        }
        float f7 = Math.max(f4, f3 * f6);
        Render2D.drawRoundedRect(class_45872, f, f2, f7, f4, f5, ColorUtils.alpha(color, 245));
    }

    private static float clamp(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }

    public static final class Layout {
        public Mode fC = Mode.fW;
        public int fD = 8;
        public boolean fE = true;
        public float fF;
        public float fG;
        public float fH;
        public int fI;
        public int fJ;
        public int fK;
        public int fL;
        public float fM = 1.0f;
        public boolean fN;
        public boolean fO;
        public float fP;
        public float fQ;
        public boolean fR;
        public boolean fS;
        public boolean fT;
    }

    public static enum Mode
    implements NamedOption  {
        fU("Bars"),
        fV("Stack"),
        fW("Segments");
        private final String label;

private Mode(String string2) {
            this.label = string2;
        }

        @Override
        public String getName() {
            return this.label;
        }

}
}

