package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1074;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_5251;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.modules.impl.combat.AutoSwapModule;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class EnumComponent
extends Component {
    private final EnumSetting<?> setting;
    private final Animation openAnimation = new Animation(180L, 1.0, false, Easing.aiT);
    private final Map<Enum<?>, Animation> pickAnimations = new HashMap();
    private boolean open;
    private final Animation hoverAnimation = new Animation(160L, 1.0, false, Easing.aiT);
    private final TimedProgress valueAnim;

    public EnumComponent(EnumSetting<?> enumSetting) {
        super(enumSetting.getName());
        this.setting = enumSetting;
        this.valueAnim = new TimedProgress(220L, Easing.aiZ, this.resolveDisplay((Enum)enumSetting.jT(), Color.WHITE).text());
        for (Enum enum_ : (Enum[])((Enum)enumSetting.jT()).getClass().getEnumConstants()) {
            Animation animation = new Animation(180L, 1.0, false, Easing.aiT);
            animation.ae(enum_ == enumSetting.jT());
            this.pickAnimations.put(enum_, animation);
        }
        this.openAnimation.ae(false);
        this.addHeight = () -> Float.valueOf(this.openAnimation.rk() > 0.0f ? (float)((Enum[])((Enum)enumSetting.jT()).getClass().getEnumConstants()).length * 14.0f * this.openAnimation.rk() : 0.0f);
        this.visible = enumSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        this.openAnimation.i(this.open);
        if (LinkGuiRenderContext.isActive()) {
            this.renderLink(class_3322, n, n2);
            return;
        }
        Color color = ColorUtils.getTextColor(255);
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]) + ": ";
        DisplayText displayText = this.resolveDisplay((Enum)this.setting.jT(), color);
        float f2 = Fonts.REGULAR.getWidth(string, 7.5f);
        float f3 = Fonts.REGULAR.getWidth(displayText.text(), 7.5f);
        float f4 = Math.max(0.0f, this.width - 10.0f - f3);
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f5 = this.getMarqueeOffset(f2, f4, bl);
        float f6 = this.x + 4.0f;
        Render2D.startScissor(class_3322, f6, this.y, f4, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f6 - f5, this.y + 3.0f, color);
        if (f5 > 0.0f && f2 > f4) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f6 - f5 + f2 + 12.0f, this.y + 3.0f, color);
        }
        Render2D.stopScissor(class_3322);
        float f7 = f6 + Math.min(f2, f4) + 1.5f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), displayText.text(), f7, this.y + 3.0f, displayText.color());
        if (this.openAnimation.rk() > 0.0f) {
            float f8 = (float)((Enum[])((Enum)this.setting.jT()).getClass().getEnumConstants()).length * 14.0f * this.openAnimation.rk();
            Render2D.startScissor(class_3322, this.x, this.y + this.height, this.width, f8);
            float f9 = this.height;
            for (Enum enum_ : (Enum[])((Enum)this.setting.jT()).getClass().getEnumConstants()) {
                Animation animation = this.pickAnimations.get(enum_);
                animation.i(enum_ == this.setting.jT());
                Color color2 = ColorUtils.getTextColor((int)(255.0f * animation.rk()));
                Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), "K", this.x + this.width - 14.0f, this.y + f9 + 2.0f, color2);
                DisplayText displayText2 = this.resolveDisplay(enum_, color);
                Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), displayText2.text(), this.x + 6.0f, this.y + f9 + 2.0f, displayText2.color());
                f9 += 14.0f;
            }
            Render2D.stopScissor(class_3322);
        }
    }

    private void renderLink(class_332 class_3322, int n, int n2) {
        Color color = ColorUtils.getTextColor(255);
        DisplayText displayText = this.resolveDisplay((Enum)this.setting.jT(), color);
        this.valueAnim.cF(displayText.text());
        float f = 7.6f;
        float f2 = 18.0f;
        float f3 = Math.max(Fonts.MEDIUM.getWidth(this.valueAnim.rh(), f), Fonts.MEDIUM.getWidth(this.valueAnim.ri(), f));
        float f4 = MathUtils.k(f3 + 14.0f, 38.0f, this.width * 0.62f);
        float f5 = this.x + this.width - f4 - 2.0f;
        float f6 = this.y + 3.0f;
        boolean bl = MathUtils.c(f5, f6, f4, f2, n, n2);
        this.hoverAnimation.i(bl);
        TextLayoutUtils.a(class_3322.method_51448(), class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]), this.x + 2.0f, this.y, Math.max(10.0f, this.width - f4 - 8.0f));
        LinkGuiBrandRenderer.h(class_3322.method_51448(), f5, f6, f4, f2, 5.0f, this.hoverAnimation.rk());
        Render2D.startScissor(class_3322, f5 + 1.0f, f6, f4 - 2.0f, f2);
        this.drawChipValue(class_3322, f5 + f4 / 2.0f, f6 + f2 / 2.0f, f, displayText.color());
        Render2D.stopScissor(class_3322);
    }

    private void drawChipValue(class_332 class_3322, float f, float f2, float f3, Color color) {
        float f4 = Fonts.MEDIUM.getHeight(f3);
        float f5 = f2 - f4 / 2.0f;
        if (!this.valueAnim.rg()) {
            String string = this.valueAnim.rh();
            float f6 = Fonts.MEDIUM.getWidth(string, f3);
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(f3), string, f - f6 / 2.0f, f5, color);
            return;
        }
        float f7 = Math.max(0.0f, Math.min(1.0f, this.valueAnim.progress()));
        float f8 = f4 * 1.1f;
        String string = this.valueAnim.ri();
        String string2 = this.valueAnim.rh();
        float f9 = Fonts.MEDIUM.getWidth(string, f3);
        float f10 = Fonts.MEDIUM.getWidth(string2, f3);
        Color color2 = EnumComponent.withAlpha(color, Math.round((float)color.getAlpha() * (1.0f - f7)));
        Color color3 = EnumComponent.withAlpha(color, Math.round((float)color.getAlpha() * f7));
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(f3), string, f - f9 / 2.0f, f5 - f8 * f7, color2);
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(f3), string2, f - f10 / 2.0f, f5 + f8 * (1.0f - f7), color3);
    }

    private static Color withAlpha(Color color, int n) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, n)));
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (LinkGuiRenderContext.isActive()) {
            if (n == 0 && MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
                this.setting.jY();
            }
            return;
        }
        if (MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
            if (n == 0) {
                this.setting.jY();
            } else if (n == 1) {
                boolean bl = this.open = !this.open;
            }
        }
        if (this.open && n == 0) {
            float f = this.height;
            if (MathUtils.c(this.x, this.y + this.height, this.width, ((Float)this.addHeight.get()).floatValue(), (float)d, (float)d2)) {
                for (Enum enum_ : (Enum[])((Enum)this.setting.jT()).getClass().getEnumConstants()) {
                    if (MathUtils.c(this.x, this.y + f, this.width, 14.0f, (float)d, (float)d2)) {
                        this.setting.bS(((NamedOption)((Object)enum_)).getName());
                        break;
                    }
                    f += 14.0f;
                }
            }
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
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

    private DisplayText resolveDisplay(Enum<?> enum_, Color color) {
        if (enum_ instanceof AutoSwapModule.SwapRule) {
            AutoSwapModule.SwapRule swapRule = (AutoSwapModule.SwapRule)enum_;
            class_2561 class_25612 = swapRule.ge();
            Color color2 = color;
            class_5251 class_52512 = class_25612.method_10866().method_10973();
            if (class_52512 != null) {
                color2 = new Color(class_52512.method_27716());
            }
            return new DisplayText(class_25612.getString(), color2);
        }
        return new DisplayText(class_1074.method_4662((String)((NamedOption)((Object)enum_)).getName(), (Object[])new Object[0]), color);
    }

    record DisplayText(String text, Color color) {
    }
}

