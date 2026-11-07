package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.api.setting.TimedBooleanSetting;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class BooleanComponent
extends Component {
    private final BooleanSetting setting;
    private final Animation toggleAnimation = new Animation(180L, 1.0, true, Easing.aiT);

    public BooleanComponent(BooleanSetting booleanSetting) {
        super(booleanSetting.getName());
        this.setting = booleanSetting;
        this.visible = booleanSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        Object object;
        this.toggleAnimation.i((Boolean)this.setting.jT());
        if (LinkGuiRenderContext.isActive()) {
            String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
            TextLayoutUtils.a(class_3322.method_51448(), string, this.x + 2.0f, this.y, this.width - 40.0f);
            TextLayoutUtils.i(class_3322.method_51448(), this.x + this.width - 34.0f, this.y + 4.5f, this.toggleAnimation.rk());
            return;
        }
        Color color = ColorUtils.getTextColor(255);
        Color color2 = new Color(23, 23, 23, 100);
        Color color3 = ColorUtils.getTextColor(255);
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f2 = 0.0f;
        BooleanSetting booleanSetting = this.setting;
        if (booleanSetting instanceof TimedBooleanSetting) {
            object = (TimedBooleanSetting)booleanSetting;
            f2 = ((TimedBooleanSetting)object).ke();
        }
        object = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f3 = this.x + 4.0f + f2;
        float f4 = Math.max(0.0f, this.width - 26.0f - f2);
        float f5 = Fonts.REGULAR.getWidth((String)object, 7.5f);
        float f6 = this.getMarqueeOffset(f5, f4, bl);
        Render2D.startScissor(class_3322, f3, this.y, f4, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), (String)object, f3 - f6, this.y + 3.0f, color);
        if (f6 > 0.0f && f5 > f4) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), (String)object, f3 - f6 + f5 + 12.0f, this.y + 3.0f, color);
        }
        Render2D.stopScissor(class_3322);
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 20.0f, this.y + 3.5f, 16.0f, 8.0f, 2.5f, color2);
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 20.0f, this.y + 3.5f, 16.0f * this.toggleAnimation.rk(), 8.0f, 2.5f, ColorUtils.getGlobalColor((int)(255.0f * this.toggleAnimation.rl())));
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 19.5f + 8.0f * this.toggleAnimation.rk(), this.y + 4.0f, 7.0f, 7.0f, 2.5f, color3);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (LinkGuiRenderContext.isActive()) {
            if (MathUtils.c(this.x + this.width - 34.0f, this.y + 4.5f, 30.0f, 15.0f, (float)d, (float)d2) && n == 0) {
                this.setting.a((Boolean)this.setting.jT() == false);
            }
            return;
        }
        if (MathUtils.c(this.x + this.width - 20.0f, this.y + 3.5f, 16.0f, 8.0f, (float)d, (float)d2) && n == 0) {
            this.setting.a((Boolean)this.setting.jT() == false);
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
}

