package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.math.BigDecimal;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class SliderComponent
extends Component {
    private final SliderSetting setting;
    private final AnimatedComponent animation = new AnimatedComponent(Easing.aiT);
    private final Animation dragScaleAnimation = new Animation(90L, 1.0, false, Easing.aiT);
    private float dragVisualProgress = 0.0f;
    private float displayedValue;
    private long lastDisplayUpdateNs;
    private boolean displayInitialized;
    private boolean drag;

    public SliderComponent(SliderSetting sliderSetting) {
        super(sliderSetting.getName());
        this.setting = sliderSetting;
        this.addHeight = () -> Float.valueOf(LinkGuiRenderContext.isActive() ? 6.0f : 3.0f);
        this.visible = sliderSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2;
        this.dragScaleAnimation.i(this.drag);
        float f3 = this.x + 4.0f;
        float f4 = this.width - 8.0f;
        float f5 = this.y + 13.0f;
        float f6 = 4.0f;
        if (LinkGuiRenderContext.isActive()) {
            f3 = this.x + 2.0f;
            f4 = this.width - 4.0f;
            f5 = this.y + 24.0f - 6.0f;
        }
        float f7 = f5 + f6 / 2.0f;
        if (this.drag) {
            float f8;
            float f9 = Math.max(1.0f, f4);
            this.dragVisualProgress = f8 = class_3532.method_15363((float)(((float)n - f3) / f9), (float)0.0f, (float)1.0f);
            f2 = class_3532.method_15363((float)MathUtils.y(f8 * (this.setting.kc() - this.setting.kb()) + this.setting.kb(), this.setting.kd()), (float)this.setting.kb(), (float)this.setting.kc());
            this.setting.a(Float.valueOf(f2));
        }
        Color color = ColorUtils.getTextColor(255);
        Color color2 = new Color(23, 23, 23, 100);
        f2 = this.getSettingProgress();
        float f10 = this.drag ? f4 * this.dragVisualProgress : f4 * f2;
        int n3 = this.drag ? 40 : 150;
        float f11 = this.animation.a(f10, n3);
        float f12 = MathUtils.k(f11, 0.0f, f4);
        float f13 = this.dragScaleAnimation.rk();
        float f14 = MathUtils.lerp(1.0f, 1.35f, f13);
        float f15 = MathUtils.k(f3 + f12, f3, f3 + f4);
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f16 = this.updateDisplayedValue();
        String string2 = this.formatDisplayValue(f16, this.setting.kd());
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        if (LinkGuiRenderContext.isActive()) {
            float f17 = Fonts.MEDIUM.getWidth(string2, 7.8f);
            float f18 = Math.max(this.width * 0.45f, this.width - f17 - 12.0f);
            TextLayoutUtils.a(class_3322.method_51448(), string, this.x + 2.0f, this.y - 2.0f, f18);
            UiRenderUtils.a(class_3322.method_51448(), Fonts.MEDIUM, string2, this.x + this.width - f17 - 2.0f, TextLayoutUtils.al(this.y) - 2.0f, ColorUtils.getTextColor(170), 7.8f);
            Render2D.drawRoundedRect(class_3322.method_51448(), f3, f5, f4, f6, 1.0f, new Color(255, 255, 255, 28));
            Render2D.drawRoundedRect(class_3322.method_51448(), f3, f5, f12, f6, 1.0f, ThemeTransition.accent());
            float f19 = 8.0f * f14;
            LinkGuiBrandRenderer.f(class_3322.method_51448(), f15, f7, f19);
            return;
        }
        float f20 = Fonts.REGULAR.getWidth(string2, 6.5f) + 8.0f;
        float f21 = Math.max(0.0f, this.width - 12.0f - f20);
        float f22 = Fonts.REGULAR.getWidth(string, 7.5f);
        float f23 = this.getMarqueeOffset(f22, f21, bl);
        Render2D.startScissor(class_3322, this.x + 4.0f, this.y, f21, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f23, this.y + 3.0f, color);
        if (f23 > 0.0f && f22 > f21) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f23 + f22 + 12.0f, this.y + 3.0f, color);
        }
        Render2D.stopScissor(class_3322);
        Render2D.drawRoundedRect(class_3322.method_51448(), f3, f5, f4, f6, 0.5f, color2);
        Render2D.drawRoundedRect(class_3322.method_51448(), f3, f5, f12, f6, 0.5f, ColorUtils.getGlobalColor());
        if (this.drag) {
            float f24 = 10.0f * f14;
            float f25 = 6.5f * f14;
            float f26 = f25 / 2.0f;
            float f27 = f15 - f24 / 2.0f;
            float f28 = f7 - f25 / 2.0f;
            Color color3 = new Color(220, 220, 220, 210);
            Render2D.drawRoundedRect(class_3322.method_51448(), f27, f28, f24, f25, f26, color3);
        } else {
            float f29 = 3.0f * f14;
            float f30 = 3.6f * f14;
            float f31 = f30 * 2.0f;
            float f32 = f29 * 2.0f;
            Color color4 = ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 170);
            Render2D.drawRoundedRect(class_3322.method_51448(), f15 - f30, f7 - f30, f31, f31, f30, color4);
            Render2D.drawRoundedRect(class_3322.method_51448(), f15 - f29, f7 - f29, f32, f32, f29, ColorUtils.getTextColor(255));
        }
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(6.0f), string2, this.x + this.width - Fonts.REGULAR.getWidth(string2, 6.5f) - 4.5f, this.y + 5.0f, color);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        float f = Math.max(this.height, 24.0f);
        if (!MathUtils.c(this.x, this.y, this.width, f, (float)d, (float)d2)) {
            return;
        }
        this.dragVisualProgress = this.getMouseProgress((float)d);
        this.drag = true;
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        this.drag = false;
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }

    private String formatValue(float f, float f2) {
        float f3 = MathUtils.y(f, f2);
        if (Math.abs(f3) < 1.0E-4f) {
            f3 = 0.0f;
        }
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f3)).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    private String formatDisplayValue(float f, float f2) {
        float f3 = f2 >= 1.0f ? 0.1f : Math.min(f2, 0.01f);
        float f4 = MathUtils.y(f, f3);
        if (Math.abs(f4) < 1.0E-4f) {
            f4 = 0.0f;
        }
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f4)).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    private float updateDisplayedValue() {
        float f = ((Float)this.setting.jT()).floatValue();
        long l = System.nanoTime();
        if (!this.displayInitialized) {
            this.displayedValue = f;
            this.lastDisplayUpdateNs = l;
            this.displayInitialized = true;
            return this.displayedValue;
        }
        float f2 = Math.max(0.0f, Math.min(0.08f, (float)(l - this.lastDisplayUpdateNs) / 1.0E9f));
        this.lastDisplayUpdateNs = l;
        float f3 = 1.0f - (float)Math.exp(-9.0f * f2);
        this.displayedValue = class_3532.method_16439((float)class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f), (float)this.displayedValue, (float)f);
        if (Math.abs(this.displayedValue - f) <= Math.max(1.0E-4f, this.setting.kd() * 0.003f)) {
            this.displayedValue = f;
        }
        return this.displayedValue;
    }

    private float getSettingProgress() {
        float f = this.setting.kc() - this.setting.kb();
        if (Math.abs(f) <= 1.0E-6f) {
            return 0.0f;
        }
        return class_3532.method_15363((float)((((Float)this.setting.jT()).floatValue() - this.setting.kb()) / f), (float)0.0f, (float)1.0f);
    }

    private static boolean isLightTheme() {
        Color color = ThemeTransition.pf();
        float f = (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f;
        return f > 0.6f;
    }

    private float getMouseProgress(float f) {
        float f2 = this.x + 4.0f;
        float f3 = Math.max(1.0f, this.width - 8.0f);
        return class_3532.method_15363((float)((f - f2) / f3), (float)0.0f, (float)1.0f);
    }
}

