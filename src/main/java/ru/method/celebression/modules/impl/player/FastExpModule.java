package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1802;
import ru.method.celebression.api.mixins.accessors.IMinecraftClient;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class FastExpModule
extends Module {
    private final SliderSetting uo = new SliderSetting("settings.fastexp.delay", 0.0f, 0.0f, 4.0f, 1.0f);

    public FastExpModule() {
        super("FastExp", ModuleCategory.qm);
        this.getSettings().add(this.uo);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (FastExpModule.fullNullCheck()) {
            return;
        }
        if (FastExpModule.aiG.field_1755 != null) {
            return;
        }
        if (!FastExpModule.aiG.field_1690.field_1904.method_1434()) {
            return;
        }
        if (!this.hn()) {
            return;
        }
        IMinecraftClient iMinecraftClient = (IMinecraftClient)aiG;
        int n = Math.round(((Float)this.uo.jT()).floatValue());
        if (iMinecraftClient.getItemUseCooldown() > n) {
            iMinecraftClient.setItemUseCooldown(n);
        }
    }

    private boolean hn() {
        return FastExpModule.aiG.field_1724.method_6047().method_31574(class_1802.field_8287) || FastExpModule.aiG.field_1724.method_6079().method_31574(class_1802.field_8287);
    }
}

