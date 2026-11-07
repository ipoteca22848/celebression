package ru.method.celebression.modules.impl.movement;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2848;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.network.PacketSuppression;

public class SprintModule
extends Module {
    private final BooleanSetting xy = new BooleanSetting("settings.sprint.reset", true);

    public SprintModule() {
        super("Sprint", ModuleCategory.qk);
    }

    @Override
    public boolean isUnsafe() {
        return true;
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (SprintModule.fullNullCheck()) {
            return;
        }
        if (SprintModule.aiG.field_1724.method_3144()) {
            return;
        }
        SprintModule.aiG.field_1690.field_1867.method_23481(true);
    }

    @EventHandler
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        if (SprintModule.fullNullCheck() || !((Boolean)this.xy.jT()).booleanValue()) {
            return;
        }
        if (attackEntityEvent.a() != SprintModule.aiG.field_1724 || !(attackEntityEvent.b() instanceof class_1309)) {
            return;
        }
        if (aiG.method_1562() == null || SprintModule.aiG.field_1724.method_3144() || !SprintModule.aiG.field_1724.method_5624()) {
            return;
        }
        PacketSuppression.b(new class_2848((class_1297)SprintModule.aiG.field_1724, class_2848.class_2849.field_12985));
        PacketSuppression.b(new class_2848((class_1297)SprintModule.aiG.field_1724, class_2848.class_2849.field_12981));
    }

    @Override
    public void onDisable() {
        if (SprintModule.fullNullCheck()) {
            return;
        }
        SprintModule.aiG.field_1690.field_1867.method_23481(false);
        super.onDisable();
    }
}

