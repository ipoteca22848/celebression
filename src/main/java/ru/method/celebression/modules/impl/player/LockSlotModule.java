package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class LockSlotModule
extends Module {
    private final BooleanSetting vs = new BooleanSetting("modules.settings.lockslot.slot_1", false);
    private final BooleanSetting vt = new BooleanSetting("modules.settings.lockslot.slot_2", false);
    private final BooleanSetting vu = new BooleanSetting("modules.settings.lockslot.slot_3", false);
    private final BooleanSetting vv = new BooleanSetting("modules.settings.lockslot.slot_4", false);
    private final BooleanSetting vx = new BooleanSetting("modules.settings.lockslot.slot_5", false);
    private final BooleanSetting vz = new BooleanSetting("modules.settings.lockslot.slot_6", false);
    private final BooleanSetting vA = new BooleanSetting("modules.settings.lockslot.slot_7", false);
    private final BooleanSetting vB = new BooleanSetting("modules.settings.lockslot.slot_8", false);
    private final BooleanSetting vC = new BooleanSetting("modules.settings.lockslot.slot_9", false);

    public LockSlotModule() {
        super("LockSlot", ModuleCategory.qm);
    }

    public boolean y(int n) {
        return switch (n) {
            case 0 -> (Boolean)this.vs.jT();
            case 1 -> (Boolean)this.vt.jT();
            case 2 -> (Boolean)this.vu.jT();
            case 3 -> (Boolean)this.vv.jT();
            case 4 -> (Boolean)this.vx.jT();
            case 5 -> (Boolean)this.vz.jT();
            case 6 -> (Boolean)this.vA.jT();
            case 7 -> (Boolean)this.vB.jT();
            case 8 -> (Boolean)this.vC.jT();
            default -> false;
        };
    }
}

