package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public class HudBooleanSettingComponent
extends HudSettingComponent {
    private final BooleanSetting mv;
    private final Animation mw = new Animation(300L, 1.0, false, Easing.ajb);

    public HudBooleanSettingComponent(String string, BooleanSetting booleanSetting) {
        super(string);
        this.mv = booleanSetting;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        this.mw.i((Boolean)this.mv.jT());
        String string = class_1074.method_4662((String)this.getName(), (Object[])new Object[0]);
        this.a(class_3322, string, 8.0f, this.x + 5.0f, this.y + 4.0f, Math.max(0.0f, this.width - 26.0f), this.height, ColorUtils.getTextColor((int)(255.0f * this.jU.rk())), n, n2, true);
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 20.0f, this.y + 4.5f, 16.0f * this.mw.rk(), 8.0f, 2.5f, ColorUtils.getGlobalColor((int)(255.0f * this.mw.rl())));
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 4.0f - 16.0f * this.mw.rm(), this.y + 4.5f, 16.0f * this.mw.rm(), 8.0f, 2.5f, new Color(23, 23, 23, 100));
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + this.width - 19.5f + 8.0f * this.mw.rk(), this.y + 5.0f, 7.0f, 7.0f, 2.5f, ColorUtils.getTextColor(255));
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (MathUtils.c(this.x + this.width - 20.0f, this.y + 3.5f, 16.0f, 8.0f, (float)d, (float)d2) && n == 0) {
            this.mv.a((Boolean)this.mv.jT() == false);
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

