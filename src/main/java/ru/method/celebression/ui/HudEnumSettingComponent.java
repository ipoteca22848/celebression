package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class HudEnumSettingComponent
extends HudSettingComponent {
    private final EnumSetting<?> mx;

    public HudEnumSettingComponent(String string, EnumSetting<?> enumSetting) {
        super(string);
        this.mx = enumSetting;
        this.visible = enumSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        Color color = ColorUtils.getTextColor((int)(255.0f * this.jU.rk()));
        String string = class_1074.method_4662((String)this.getName(), (Object[])new Object[0]);
        String string2 = this.dO();
        float f2 = Fonts.ROUNDBOLD.getWidth(string2, 8.0f);
        float f3 = f2 + 16.0f;
        this.a(class_3322, string, 8.0f, this.x + 5.0f, this.y + 4.0f, Math.max(0.0f, this.width - f3 - 5.0f), this.height, color, n, n2, true);
        float f4 = this.x + this.width - f2 - 16.0f;
        Render2D.startScissor(class_3322, f4, this.y, f2 + 11.0f, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string2, f4, this.y + 4.0f, color);
        Render2D.stopScissor(class_3322);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), "J", this.x + this.width - 11.0f, this.y + 3.0f, color);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (!MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
            return;
        }
        if (n == 0 || n == 1) {
            this.mx.jY();
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

    private String dO() {
        Enum enum_ = (Enum)this.mx.jT();
        if (enum_ instanceof NamedOption) {
            NamedOption namedOption = (NamedOption)((Object)enum_);
            return class_1074.method_4662((String)namedOption.getName(), (Object[])new Object[0]);
        }
        return enum_.name();
    }
}

