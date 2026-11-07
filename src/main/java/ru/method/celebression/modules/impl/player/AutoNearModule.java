package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;

public class AutoNearModule
extends Module {
    private final SliderSetting rP = new SliderSetting("modules.settings.auto_near.delay", 60.0f, 1.0f, 180.0f, 1.0f);
    private final StringSetting rQ = new StringSetting("modules.settings.auto_near.amount", "", "100 / 500 / max");
    private long rR;

    public AutoNearModule() {
        super("AutoNear", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        this.rR = 0L;
        super.onEnable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (AutoNearModule.fullNullCheck() || AutoNearModule.aiG.field_1724.field_3944 == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l < this.rR) {
            return;
        }
        String string = this.gm();
        AutoNearModule.aiG.field_1724.field_3944.method_45730((String)(string.isBlank() ? "near" : "near " + string));
        this.rR = l + (long)(((Float)this.rP.jT()).floatValue() * 1000.0f);
    }

    private String gm() {
        String string;
        String string2 = string = this.rQ.kg() == null ? "" : this.rQ.kg().trim();
        if (string.isBlank()) {
            return "";
        }
        if (string.equalsIgnoreCase("max")) {
            return "max";
        }
        if (string.matches("\\d{1,7}")) {
            return string;
        }
        return "";
    }
}

