package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.render.CustomVitalsRenderer;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class CustomVitalsModule
extends Module {
    private final EnumSetting<CustomVitalsRenderer.Mode> zZ = new EnumSetting<CustomVitalsRenderer.Mode>("Style", CustomVitalsRenderer.Mode.fW);
    private final SliderSetting Aa = new SliderSetting("Segments", 8.0f, 3.0f, 10.0f, 1.0f, () -> this.zZ.jT() == CustomVitalsRenderer.Mode.fW);
    private final BooleanSetting Ab = new BooleanSetting("Armor row", true, () -> this.zZ.jT() == CustomVitalsRenderer.Mode.fW);

    public CustomVitalsModule() {
        super("CustomVitals", ModuleCategory.ql);
        this.getSettings().add(this.zZ);
        this.getSettings().add(this.Aa);
        this.getSettings().add(this.Ab);
    }

    public CustomVitalsRenderer.Mode iz() {
        return (CustomVitalsRenderer.Mode)this.zZ.jT();
    }

    public int iA() {
        return Math.round(((Float)this.Aa.jT()).floatValue());
    }

    public boolean iB() {
        return (Boolean)this.Ab.jT();
    }
}

