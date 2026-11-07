package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class EnumButtonComponent
extends Component {
    private final EnumSetting<?> setting;
    private final Animation hoverAnimation = new Animation(140L, 1.0, false, Easing.aiT);

    public EnumButtonComponent(EnumSetting<?> enumSetting) {
        super(enumSetting.getName());
        this.setting = enumSetting;
        this.visible = enumSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2 = 44.0f;
        float f3 = this.x + this.width - 48.0f;
        float f4 = this.y + 2.0f;
        float f5 = this.height - 4.0f;
        boolean bl = MathUtils.c(f3, f4, f2, f5, n, n2);
        this.hoverAnimation.i(bl);
        Color color = ColorUtils.getTextColor(255);
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f6 = Fonts.REGULAR.getWidth(string, 7.5f);
        float f7 = Math.max(0.0f, this.width - 54.0f);
        boolean bl2 = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f8 = this.getMarqueeOffset(f6, f7, bl2);
        float f9 = this.y + (this.height - Fonts.REGULAR.getHeight(7.5f)) / 2.0f + 1.0f;
        Render2D.startScissor(class_3322, this.x + 4.0f, this.y, f7, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f8, f9, color);
        if (f8 > 0.0f && f6 > f7) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f8 + f6 + 12.0f, f9, color);
        }
        Render2D.stopScissor(class_3322);
        float f10 = 5.0f;
        float f11 = 1.0f;
        float f12 = f3 + f11;
        float f13 = f4 + f11;
        float f14 = f2 - f11 * 2.0f;
        float f15 = f5 - f11 * 2.0f;
        float f16 = Math.max(0.0f, f10 - f11);
        Color color2 = ColorUtils.getGlassColor(255);
        Render2D.drawLiquidRectClickGuiChip(class_3322.method_51448(), f12, f13, f14, f15, new BorderRadius(f16), new ColorRGBA(color2), 2.5f, 18.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
        int n3 = (int)MathUtils.lerp(90.0f, 165.0f, this.hoverAnimation.rk());
        Render2D.drawRoundedRect(class_3322.method_51448(), f12, f13, f14, f15, f16, ColorUtils.getGlobalColor(n3));
        String string2 = class_1074.method_4662((String)((NamedOption)this.setting.jT()).getName(), (Object[])new Object[0]);
        string2 = this.fitText(string2, f2 - 8.0f);
        float f17 = Fonts.MEDIUM.getWidth(string2, 7.0f);
        float f18 = Fonts.MEDIUM.getHeight(7.0f);
        float f19 = f3 + (f2 - f17) / 2.0f;
        float f20 = f4 + (f5 - f18) / 2.0f + 1.0f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.0f), string2, f19, f20, color);
    }

    private String fitText(String string, float f) {
        if (Fonts.MEDIUM.getWidth(string, 7.0f) <= f) {
            return string;
        }
        String string2 = "...";
        float f2 = Fonts.MEDIUM.getWidth(string2, 7.0f);
        String string3 = string;
        while (string3.length() > 1 && Fonts.MEDIUM.getWidth(string3, 7.0f) + f2 > f) {
            string3 = string3.substring(0, string3.length() - 1);
        }
        return string3 + string2;
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n == 0 && MathUtils.c(this.x + this.width - 48.0f, this.y + 2.0f, 44.0f, this.height - 4.0f, (float)d, (float)d2)) {
            this.setting.jY();
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

