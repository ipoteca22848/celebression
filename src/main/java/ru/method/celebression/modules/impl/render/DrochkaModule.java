package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class DrochkaModule
extends Module {
    private static DrochkaModule Af;
    private final SliderSetting Ag = new SliderSetting("modules.settings.drochka.speed", 0.9f, 0.3f, 4.0f, 0.1f);
    private final SliderSetting Ah = new SliderSetting("modules.settings.drochka.amplitude", 0.4f, 0.1f, 0.9f, 0.05f);
    private final BooleanSetting Ai = new BooleanSetting("modules.settings.drochka.both_hands", false);
    private final BooleanSetting Aj = new BooleanSetting("modules.settings.drochka.breast", false);

    public DrochkaModule() {
        super("Drochka", ModuleCategory.ql);
        Af = this;
    }

    public static boolean active() {
        DrochkaModule drochkaModule = Af;
        return drochkaModule != null && drochkaModule.isToggle();
    }

    public static float iD() {
        DrochkaModule drochkaModule = Af;
        return drochkaModule != null ? ((Float)drochkaModule.Ag.jT()).floatValue() : 0.9f;
    }

    public static float iE() {
        DrochkaModule drochkaModule = Af;
        return drochkaModule != null ? ((Float)drochkaModule.Ah.jT()).floatValue() : 0.4f;
    }

    public static boolean iF() {
        DrochkaModule drochkaModule = Af;
        return drochkaModule != null && (Boolean)drochkaModule.Ai.jT() != false;
    }

    public static boolean iG() {
        DrochkaModule drochkaModule = Af;
        return drochkaModule != null && drochkaModule.isToggle() && (Boolean)drochkaModule.Aj.jT() != false;
    }
}

