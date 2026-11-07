package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ListComponent
extends Component {
    private final MultiSelectSetting setting;
    private final Animation openAnimation = new Animation(180L, 1.0, false, Easing.aiT);
    private final Map<BooleanSetting, Animation> pickAnimations = new HashMap<BooleanSetting, Animation>();
    private boolean open;

    public ListComponent(MultiSelectSetting multiSelectSetting) {
        super(multiSelectSetting.getName());
        this.setting = multiSelectSetting;
        for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
            Animation animation = new Animation(180L, 1.0, false, Easing.aiT);
            animation.ae((Boolean)booleanSetting.jT());
            this.pickAnimations.put(booleanSetting, animation);
        }
        this.openAnimation.ae(false);
        this.addHeight = () -> Float.valueOf(this.openAnimation.rk() > 0.0f ? (float)((List)multiSelectSetting.jT()).size() * 14.0f * this.openAnimation.rk() : 0.0f);
        this.visible = multiSelectSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        this.openAnimation.i(this.open);
        Color color = ColorUtils.getTextColor(255);
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        String string2 = "(" + this.setting.ka().size() + "/" + ((List)this.setting.jT()).size() + ")";
        float f2 = Fonts.REGULAR.getWidth(string2, 7.5f);
        float f3 = Math.max(0.0f, this.width - 12.0f - f2);
        float f4 = Fonts.REGULAR.getWidth(string, 7.5f);
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f5 = this.getMarqueeOffset(f4, f3, bl);
        float f6 = this.x + 5.0f;
        Render2D.startScissor(class_3322, f6, this.y, f3, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f6 - f5, this.y + 3.5f, color);
        if (f5 > 0.0f && f4 > f3) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f6 - f5 + f4 + 12.0f, this.y + 3.5f, color);
        }
        Render2D.stopScissor(class_3322);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string2, this.x + this.width - f2 - 5.0f, this.y + 3.5f, color);
        if (this.openAnimation.rk() > 0.0f) {
            float f7 = this.height;
            for (BooleanSetting booleanSetting : this.setting.jT()) {
                Animation animation = this.pickAnimations.get(booleanSetting);
                animation.i((Boolean)booleanSetting.jT());
                Color color2 = ColorUtils.getTextColor((int)(255.0f * animation.rk()));
                Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), "D", this.x + this.width - 14.0f, this.y + f7 + 3.5f, color2);
                Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), class_1074.method_4662((String)booleanSetting.getName(), (Object[])new Object[0]), this.x + 6.0f, this.y + f7 + 3.5f, color);
                f7 += 14.0f;
            }
        }
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2) && n == 1) {
            boolean bl = this.open = !this.open;
        }
        if (this.openAnimation.rk() > 0.0f && n == 0) {
            float f = this.height;
            for (BooleanSetting booleanSetting : this.setting.jT()) {
                if (MathUtils.c(this.x, this.y + f, this.width, 14.0f, (float)d, (float)d2)) {
                    booleanSetting.a((Boolean)booleanSetting.jT() == false);
                    break;
                }
                f += 14.0f;
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
}
