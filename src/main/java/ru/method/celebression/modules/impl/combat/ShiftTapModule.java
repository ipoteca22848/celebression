package ru.method.celebression.modules.impl.combat;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ShiftTapModule
extends Module {
    private final SliderSetting rz = new SliderSetting("settings.shifttap.hold_ticks", 3.0f, 1.0f, 8.0f, 1.0f);
    private int rA;
    private boolean rB;
    private long rC;
    private static final long rD = 100L;

    public ShiftTapModule() {
        super("ShiftTap", ModuleCategory.qj);
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        if (ShiftTapModule.fullNullCheck()) {
            return;
        }
        class_1657 class_16572 = attackEntityEvent.a();
        class_1297 class_12972 = attackEntityEvent.b();
        if (class_16572 == null || class_16572 != ShiftTapModule.aiG.field_1724) {
            return;
        }
        if (!(class_12972 instanceof class_1309)) {
            return;
        }
        if (this.rB || this.rA > 0) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.rC < 100L) {
            return;
        }
        if (this.j(class_16572)) {
            this.rA = Math.max(1, Math.round(((Float)this.rz.jT()).floatValue()));
            this.rC = l;
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (ShiftTapModule.fullNullCheck()) {
            this.gk();
            return;
        }
        if (this.rA > 0) {
            ShiftTapModule.aiG.field_1690.field_1832.method_23481(true);
            this.rB = true;
            --this.rA;
        } else if (this.rB) {
            ShiftTapModule.aiG.field_1690.field_1832.method_23481(false);
            this.rB = false;
        }
    }

    @Override
    public void onDisable() {
        this.gk();
        super.onDisable();
    }

    private boolean j(class_1657 class_16572) {
        if (class_16572.method_24828()) {
            return false;
        }
        if (class_16572.method_5869() || class_16572.method_5799()) {
            return false;
        }
        if (class_16572.method_6101()) {
            return false;
        }
        return !class_16572.method_5765();
    }

    private void gk() {
        this.rA = 0;
        if (this.rB && aiG != null && ShiftTapModule.aiG.field_1690 != null) {
            ShiftTapModule.aiG.field_1690.field_1832.method_23481(false);
        }
        this.rB = false;
    }
}

