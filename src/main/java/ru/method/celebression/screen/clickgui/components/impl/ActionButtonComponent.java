package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ActionButtonComponent
extends Component {
    private static final float BUTTON_HEIGHT = 15.0f;
    private final ButtonSetting setting;

    public ActionButtonComponent(ButtonSetting buttonSetting) {
        super(buttonSetting.getName());
        this.setting = buttonSetting;
        this.addHeight = () -> Float.valueOf(4.0f);
        this.visible = buttonSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2 = this.x + 4.0f;
        float f3 = this.y + 2.0f;
        float f4 = this.width - 8.0f;
        boolean bl = MathUtils.c(f2, f3, f4, 15.0f, n, n2);
        Color color = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 105) : new Color(255, 255, 255, 26);
        Color color2 = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 170) : ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 130);
        String string = class_1074.method_4662((String)this.setting.jX(), (Object[])new Object[0]);
        Render2D.drawRoundedRect(class_3322.method_51448(), f2, f3, f4, 15.0f, 5.0f, color);
        Render2D.drawBorder(class_3322.method_51448(), f2, f3, f4, 15.0f, 5.0f, 0.55f, new ColorRGBA(color2));
        float f5 = 7.2f;
        float f6 = Fonts.SEMIBOLD.getWidth(string, f5);
        float f7 = Fonts.SEMIBOLD.getHeight(f5);
        Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(f5), string, f2 + (f4 - f6) / 2.0f, f3 + (15.0f - f7) / 2.0f - 0.5f, ColorUtils.getTextColor(245));
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        if (MathUtils.c(this.x + 4.0f, this.y + 2.0f, this.width - 8.0f, 15.0f, (float)d, (float)d2)) {
            this.setting.run();
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

    @Override
    public float getLayoutHeight() {
        return 19.0f;
    }
}

