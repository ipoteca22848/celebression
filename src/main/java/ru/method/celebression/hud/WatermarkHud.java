package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1657;
import net.minecraft.class_4587;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.util.Stopwatch;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class WatermarkHud
extends HudElement {
    private final BooleanSetting li = new BooleanSetting("settings.hud.fade", true);
    private final TimedProgress lj = new TimedProgress(120L, Easing.aiZ, "0");

    public WatermarkHud() {
        super("Watermark");
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        Object object;
        Color color;
        if (WatermarkHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz) {
            float f = this.getX();
            float f2 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f3 = this.b(class_45872, f, f2);
            float f4 = this.gn.rk() * this.bJ();
            class_45872.method_22903();
            class_45872.method_46416(f, f2, 0.0f);
            class_45872.method_22905(f4, f4, 1.0f);
            class_45872.method_46416(-f, -f2, 0.0f);
            int n = VisualHud.cardWatermark(hudRenderEvent.j(), aiG, Math.round(f), Math.round(f2));
            class_45872.method_22909();
            this.b(class_45872);
            this.b(f, f2, n, 17.0f, f3);
            super.onRender2D(hudRenderEvent);
            return;
        }
        float f = this.getX();
        float f5 = this.getY();
        float f6 = 5.0f;
        float f7 = 8.0f;
        float f8 = 9.0f;
        String string = "C";
        String string2 = "O";
        String string3 = "t.me/Celebression";
        Font font = Fonts.ROUNDBOLD;
        Font font2 = Fonts.ICONSNEW;
        float f9 = Fonts.ROUNDBOLD.getWidth(string3, f7);
        float f10 = 17.0f;
        float f11 = font2.getWidth(string, f8);
        float f12 = font2.getWidth(string2, f8);
        String string4 = String.valueOf(Stopwatch.rT());
        String string5 = "FPS";
        String string6 = WatermarkHud.n(ServerDetector.t((class_1657)WatermarkHud.aiG.field_1724));
        String string7 = "MS";
        float f13 = f7 - 1.5f;
        float f14 = font.getWidth(string4, f7);
        float f15 = Fonts.SEMIBOLD.getWidth(string5, f13);
        float f16 = font.getWidth(string6, f7);
        float f17 = Fonts.SEMIBOLD.getWidth(string7, f13);
        float f18 = 3.0f;
        float f19 = 1.5f;
        float f20 = 4.0f;
        float f21 = 1.5f;
        float f22 = f20 + f21 * 2.0f;
        float f23 = 2.0f;
        float f24 = 2.0f;
        float f25 = f6 + f9 + f23 + f22 + f23 + f11 + f18 + f14 + f19 + f15 + f23 + f22 + f23 + f12 + f18 + f16 + f19 + f17 + f6 + f24;
        float f26 = this.b(hudRenderEvent.j().method_51448(), f, f5);
        hudRenderEvent.j().method_51448().method_22903();
        float f27 = this.bJ();
        float[] fArray = this.bK();
        float f28 = this.gn.rk() * f27;
        hudRenderEvent.j().method_51448().method_46416(f + f25 / 2.0f + fArray[0], f5 + f10 / 2.0f + fArray[1], 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f28, f28, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-(f + f25 / 2.0f + fArray[0]), -(f5 + f10 / 2.0f + fArray[1]), 0.0f);
        boolean bl = HudThemeRenderer.active();
        float f29 = HudThemeRenderer.f(f25, f10);
        if (bl) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f, f5, f25, f10, f29, this.gn.rk());
        } else {
            color = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f, f5, f25, f10, new BorderRadius(10.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        color = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 80) : ColorUtils.alpha(Color.WHITE, 28);
        float f30 = bl ? f29 : 10.0f;
        Render2D.drawBorder(hudRenderEvent.j().method_51448(), f, f5, f25, f10, f30, 0.8f, new ColorRGBA(color));
        double d = (double)(System.currentTimeMillis() % 3000L) / 3000.0;
        float f31 = font.getHeight(f7);
        float f32 = font2.getHeight(f8);
        float f33 = f5 + f10 / 2.0f;
        float f34 = f33 - f31 / 2.0f;
        float f35 = f33 - f32 / 2.0f;
        float f36 = f + f6;
        for (int i = 0; i < string3.length(); ++i) {
            object = String.valueOf(string3.charAt(i));
            float f37 = (float)i / (float)(string3.length() - 1);
            float f38 = (f37 + (float)d * 2.0f) % 2.0f;
            float f39 = f38 <= 1.0f ? f38 : 2.0f - f38;
            f39 = (float)((Math.sin((double)(f39 - 0.5f) * Math.PI) + 1.0) / 2.0);
            Color color2 = bl ? (((Boolean)this.li.jT()).booleanValue() ? ColorUtils.gradient(f39) : ColorUtils.getPrimaryColor()) : ColorUtils.getTextColor(255);
            Render2D.drawFont(hudRenderEvent.j().method_51448(), font.getFont(f7), (String)object, f36, f34, color2);
            f36 += font.getWidth((String)object, f7);
        }
        Color color3 = bl ? HudThemeRenderer.d(255) : ColorUtils.getTextColor(255);
        Object object2 = object = bl ? HudThemeRenderer.e(210) : ColorUtils.getTextColor(200);
        Color color4 = bl ? (((Boolean)this.li.jT()).booleanValue() ? ColorUtils.getGlobalColor() : ColorUtils.getPrimaryColor()) : new Color(190, 198, 212, 200);
        Color color5 = bl ? HudThemeRenderer.e(150) : new Color(180, 188, 202, 150);
        Color color6 = (Boolean)this.li.jT() != false ? ColorUtils.getGlobalColor() : ColorUtils.getPrimaryColor();
        float f40 = 4.0f;
        float f41 = f + f6 + f9 + f23 + f21;
        float f42 = f5 + (f10 - f40) / 2.0f;
        if (bl) {
            for (int i = 3; i >= 1; --i) {
                float f43 = (float)i * 1.1f;
                int n = Math.round((0.12f + (float)(3 - i) * 0.05f) * 255.0f * this.gn.rk());
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f41 - f43, f42 - f43, f40 + 2.0f * f43, f40 + 2.0f * f43, (f40 + 2.0f * f43) / 2.0f, ColorUtils.alpha(color6, n));
            }
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f41, f42, f40, f40, f40 / 2.0f, color6);
        } else {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f41, f42, f40, f40, f40 / 2.0f, color5);
        }
        float f44 = f + f6 + f9 + f23 + f22 + f23;
        Render2D.drawFont(hudRenderEvent.j().method_51448(), font2.getFont(f8), string, f44, f35, color4);
        this.lj.cF(string4);
        WatermarkHud.a(hudRenderEvent.j().method_51448(), this.lj, font, f7, f44 += f11 + f18, f5 + 4.0f, color3);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f13), string5, f44 += f14 + f19, f5 + 5.0f, (Color)object);
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), (f44 += f15 + f23) + f21, f5 + (f10 - f20) / 2.0f, f20, f20, f20 / 2.0f, color5);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), font2.getFont(f8), string2, f44 += f22 + f23, f35, color4);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), font.getFont(f7), string6, f44 += f12 + f18, f5 + 4.0f, color3);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f13), string7, f44 += f16 + f19, f5 + 5.0f, (Color)object);
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f, f5, f25, f10, f26);
        super.onRender2D(hudRenderEvent);
    }

    private static String n(int n) {
        if (n < 0) {
            return "0";
        }
        if (n > 999) {
            return "999+";
        }
        return String.valueOf(n);
    }

    private static void a(class_4587 class_45872, TimedProgress timedProgress, Font font, float f, float f2, float f3, Color color) {
        if (!timedProgress.rg()) {
            Render2D.drawFont(class_45872, font.getFont(f), timedProgress.rh(), f2, f3, color);
            return;
        }
        float f4 = Math.max(0.0f, Math.min(1.0f, timedProgress.progress()));
        String string = timedProgress.ri();
        String string2 = timedProgress.rh();
        int n = EasingMath.w(string, string2);
        int n2 = EasingMath.d(string, string2, n);
        String string3 = EasingMath.a(string2, 0, n);
        String string4 = EasingMath.a(string, n, string.length() - n2);
        String string5 = EasingMath.a(string2, n, string2.length() - n2);
        String string6 = EasingMath.a(string2, string2.length() - n2, string2.length());
        float f5 = font.getWidth(string3, f);
        float f6 = font.getWidth(string5, f);
        float f7 = 6.0f;
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * (1.0f - f4)));
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * f4));
        Render2D.drawFont(class_45872, font.getFont(f), string3, f2, f3, color);
        Render2D.drawFont(class_45872, font.getFont(f), string6, f2 + f5 + f6, f3, color);
        Render2D.drawFont(class_45872, font.getFont(f), string4, f2 + f5, f3 - f7 * f4, color2);
        Render2D.drawFont(class_45872, font.getFont(f), string5, f2 + f5, f3 + f7 * (1.0f - f4), color3);
    }
}

