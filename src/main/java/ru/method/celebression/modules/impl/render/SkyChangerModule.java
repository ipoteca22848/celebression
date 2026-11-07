package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class SkyChangerModule
extends Module {
    private final ColorSetting Gh = new ColorSetting("modules.settings.skychanger.color", new Color(120, 60, 200, 180));

    public SkyChangerModule() {
        super("SkyChanger", ModuleCategory.ql);
    }

    public int ae(int n) {
        Color color = (Color)this.Gh.jT();
        float f = (float)color.getAlpha() / 255.0f;
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        int n5 = SkyChangerModule.clamp(Math.round((float)n2 * (1.0f - f) + (float)color.getRed() * f));
        int n6 = SkyChangerModule.clamp(Math.round((float)n3 * (1.0f - f) + (float)color.getGreen() * f));
        int n7 = SkyChangerModule.clamp(Math.round((float)n4 * (1.0f - f) + (float)color.getBlue() * f));
        return n5 << 16 | n6 << 8 | n7;
    }

    private static int clamp(int n) {
        return Math.max(0, Math.min(255, n));
    }
}

