package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import net.minecraft.class_1074;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class AspectRatioModule
extends Module {
    public final SliderSetting yP = new SliderSetting(class_1074.method_4662((String)"settings.aspectratio.ratio", (Object[])new Object[0]), 1.75f, 0.1f, 5.0f, 0.05f);

    public AspectRatioModule() {
        super("AspectRatio", ModuleCategory.ql);
    }
}

