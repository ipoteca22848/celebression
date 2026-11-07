package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1268;
import net.minecraft.class_418;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;

public class PlayerUtilsModule
extends Module {
    private final BooleanSetting vU = new BooleanSetting("modules.settings.playerutils.anti_afk", true);
    private final BooleanSetting vV = new BooleanSetting("modules.settings.playerutils.auto_respawn", true);
    private final EnumSetting<PlayerAction> vW = new EnumSetting<PlayerAction>("modules.settings.playerutils.action", PlayerAction.wa, this.vU::jT);
    private final SliderSetting vX = new SliderSetting("modules.settings.playerutils.delay", 30.0f, 5.0f, 300.0f, 1.0f, this.vU::jT);
    private long vY = System.currentTimeMillis();
    private double lastX;
    private double vZ;
    private double lastZ;

    public PlayerUtilsModule() {
        super("PlayerUtils", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.hA();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (PlayerUtilsModule.fullNullCheck()) {
            return;
        }
        if (((Boolean)this.vV.jT()).booleanValue() && PlayerUtilsModule.aiG.field_1755 instanceof class_418) {
            PlayerUtilsModule.aiG.field_1724.method_7331();
            aiG.method_1507(null);
        }
        if (!((Boolean)this.vU.jT()).booleanValue()) {
            return;
        }
        if (this.hz()) {
            this.hA();
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.vY < (long)(((Float)this.vX.jT()).floatValue() * 1000.0f)) {
            return;
        }
        if (this.vW.jT() == PlayerAction.wa) {
            PlayerUtilsModule.aiG.field_1724.method_6043();
        } else {
            PlayerUtilsModule.aiG.field_1724.method_6104(class_1268.field_5808);
        }
        this.hA();
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        this.hA();
    }

    private boolean hz() {
        return PlayerUtilsModule.aiG.field_1724.method_23317() != this.lastX || PlayerUtilsModule.aiG.field_1724.method_23318() != this.vZ || PlayerUtilsModule.aiG.field_1724.method_23321() != this.lastZ || PlayerUtilsModule.aiG.field_1724.field_3913.field_3905 != 0.0f || PlayerUtilsModule.aiG.field_1724.field_3913.field_3907 != 0.0f;
    }

    private void hA() {
        this.vY = System.currentTimeMillis();
        if (PlayerUtilsModule.aiG.field_1724 != null) {
            this.lastX = PlayerUtilsModule.aiG.field_1724.method_23317();
            this.vZ = PlayerUtilsModule.aiG.field_1724.method_23318();
            this.lastZ = PlayerUtilsModule.aiG.field_1724.method_23321();
        }
    }

    static enum PlayerAction
    implements NamedOption  {
        wa("modules.settings.playerutils.action.jump"),
        wb("modules.settings.playerutils.action.swing_hand");
        private final String wc;

private PlayerAction(String string2) {
            this.wc = string2;
        }

        @Override
        public String getName() {
            return this.wc;
        }

}
}

