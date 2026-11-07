package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.FogBlurRenderer;

public class FogBlurModule
extends Module {
    private final SliderSetting Ak = new SliderSetting("settings.fogblur.strength", 6.0f, 1.0f, 20.0f, 1.0f);
    private final SliderSetting Al = new SliderSetting("settings.fogblur.distance", 50.0f, 0.0f, 200.0f, 10.0f);
    private final BooleanSetting Am = new BooleanSetting("settings.fogblur.linear_sampling", true);
    private final BooleanSetting An = new BooleanSetting("settings.fogblur.rainbow", false);
    private final ColorSetting Ao = new ColorSetting("settings.fogblur.color", new Color(120, 170, 255, 65), () -> (Boolean)this.An.jT() == false);
    private final SliderSetting Ap = new SliderSetting("settings.fogblur.rainbow.opacity", 30.0f, 1.0f, 100.0f, 1.0f, this.An::jT);
    private final SliderSetting Aq = new SliderSetting("settings.fogblur.rainbow.saturation", 70.0f, 0.0f, 100.0f, 1.0f, this.An::jT);
    private final SliderSetting Ar = new SliderSetting("settings.fogblur.rainbow.brightness", 100.0f, 0.0f, 100.0f, 1.0f, this.An::jT);

    public FogBlurModule() {
        super("FogBlur", ModuleCategory.ql);
    }

    @Override
    public void onDisable() {
        FogBlurRenderer.release();
        super.onDisable();
    }

    public void iH() {
        FogBlurRenderer.render(((Float)this.Ak.jT()).floatValue(), ((Float)this.Al.jT()).floatValue(), (Boolean)this.Am.jT(), (Boolean)this.An.jT(), ((Float)this.Ap.jT()).floatValue() / 100.0f, ((Float)this.Aq.jT()).floatValue() / 100.0f, ((Float)this.Ar.jT()).floatValue() / 100.0f, (Color)this.Ao.jT());
    }
}

