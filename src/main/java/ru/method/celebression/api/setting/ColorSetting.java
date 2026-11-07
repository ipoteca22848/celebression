package ru.method.celebression.api.setting;

import java.awt.Color;
import java.util.function.Supplier;
import ru.method.celebression.api.setting.Setting;

public class ColorSetting
extends Setting<Color> {
    public ColorSetting(String string, Color color) {
        super(string, ColorSetting.f(color));
    }

    public ColorSetting(String string, Color color, Supplier<Boolean> supplier) {
        super(string, ColorSetting.f(color), supplier);
    }

    public void d(Color color) {
        super.a(ColorSetting.f(color));
    }

    private static Color f(Color color) {
        if (color == null) {
            return Color.WHITE;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void a(Color color) {
        this.d(color);
    }
}
