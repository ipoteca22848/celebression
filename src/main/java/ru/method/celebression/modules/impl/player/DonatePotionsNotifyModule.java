package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_6880;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class DonatePotionsNotifyModule
extends Module {
    private final SliderSetting sR = new SliderSetting("modules.settings.donate_potions_notify.notify_before", 10.0f, 1.0f, 60.0f, 1.0f);
    private final BooleanSetting sS = new BooleanSetting("modules.settings.donate_potions_notify.assassin", true);
    private final BooleanSetting sT = new BooleanSetting("modules.settings.donate_potions_notify.paladin", true);
    private final BooleanSetting sU = new BooleanSetting("modules.settings.donate_potions_notify.wrath", true);
    private final BooleanSetting sV = new BooleanSetting("modules.settings.donate_potions_notify.sleeping", true);
    private final BooleanSetting sW = new BooleanSetting("modules.settings.donate_potions_notify.radiation", true);
    private final BooleanSetting sX = new BooleanSetting("modules.settings.donate_potions_notify.firecracker", true);
    private final BooleanSetting sY = new BooleanSetting("modules.settings.donate_potions_notify.holy_water", true);
    private final Set<String> sZ = new HashSet<String>();
    private int tickCounter;

    public DonatePotionsNotifyModule() {
        super("DonatePotionsNotify", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.sZ.clear();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (DonatePotionsNotifyModule.fullNullCheck() || ++this.tickCounter % 10 != 0) {
            return;
        }
        Map map = DonatePotionsNotifyModule.aiG.field_1724.method_6088();
        this.a(new PotionState("\u0417\u0435\u043b\u044c\u0435 \u0410\u0441\u0441\u0430\u0441\u0438\u043d\u0430", (Boolean)this.sS.jT(), this.a((class_6880<class_1291>)class_1294.field_5910, 4), this.a((class_6880<class_1291>)class_1294.field_5904, 3), this.a((class_6880<class_1291>)class_1294.field_5917, 1)), map);
        this.a(new PotionState("\u0417\u0435\u043b\u044c\u0435 \u041f\u0430\u043b\u0430\u0434\u0438\u043d\u0430", (Boolean)this.sT.jT(), this.a((class_6880<class_1291>)class_1294.field_5907, 1), this.a((class_6880<class_1291>)class_1294.field_5918, 1), this.a((class_6880<class_1291>)class_1294.field_5905, 1), this.a((class_6880<class_1291>)class_1294.field_5914, 3)), map);
        this.a(new PotionState("\u0417\u0435\u043b\u044c\u0435 \u0413\u043d\u0435\u0432\u0430", (Boolean)this.sU.jT(), this.a((class_6880<class_1291>)class_1294.field_5910, 5), this.a((class_6880<class_1291>)class_1294.field_5909, 4)), map);
        this.a(new PotionState("\u0421\u043d\u043e\u0442\u0432\u043e\u0440\u043d\u043e\u0435", (Boolean)this.sV.jT(), this.a((class_6880<class_1291>)class_1294.field_5911, 2), this.a((class_6880<class_1291>)class_1294.field_5901, 2), this.a((class_6880<class_1291>)class_1294.field_5920, 3), this.a((class_6880<class_1291>)class_1294.field_5919, 1)), map);
        this.a(new PotionState("\u0417\u0435\u043b\u044c\u0435 \u0420\u0430\u0434\u0438\u0430\u0446\u0438\u0438", (Boolean)this.sW.jT(), this.a((class_6880<class_1291>)class_1294.field_5899, 2), this.a((class_6880<class_1291>)class_1294.field_5920, 2), this.a((class_6880<class_1291>)class_1294.field_5909, 3), this.a((class_6880<class_1291>)class_1294.field_5903, 5), this.a((class_6880<class_1291>)class_1294.field_5912, 1)), map);
        this.a(new PotionState("\u0425\u043b\u043e\u043f\u0443\u0448\u043a\u0430", (Boolean)this.sX.jT(), this.a((class_6880<class_1291>)class_1294.field_5909, 10), this.a((class_6880<class_1291>)class_1294.field_5904, 5), this.a((class_6880<class_1291>)class_1294.field_5919, 10), this.a((class_6880<class_1291>)class_1294.field_5912, 1)), map);
        this.a(new PotionState("\u0421\u0432\u044f\u0442\u0430\u044f \u0432\u043e\u0434\u0430", (Boolean)this.sY.jT(), this.a((class_6880<class_1291>)class_1294.field_5924, 3), this.a((class_6880<class_1291>)class_1294.field_5905, 2)), map);
    }

    private void a(PotionState potionState, Map<class_6880<class_1291>, class_1293> map) {
        if (!potionState.enabled()) {
            this.sZ.remove(potionState.name());
            return;
        }
        int n = Integer.MAX_VALUE;
        for (PotionRule potionRule : potionState.gO()) {
            class_1293 class_12932 = map.get(potionRule.gM());
            if (class_12932 == null || class_12932.method_5578() + 1 < potionRule.gN()) {
                this.sZ.remove(potionState.name());
                return;
            }
            n = Math.min(n, class_12932.method_5584());
        }
        int n2 = Math.round(((Float)this.sR.jT()).floatValue() * 20.0f);
        if (n > n2) {
            this.sZ.remove(potionState.name());
            return;
        }
        if (!this.sZ.add(potionState.name())) {
            return;
        }
        int n3 = Math.max(0, n / 20);
        ChatOutput.cG("\u0414\u043e\u043d\u0430\u0442\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435 \u0441\u043a\u043e\u0440\u043e \u0437\u0430\u043a\u043e\u043d\u0447\u0438\u0442\u0441\u044f: " + potionState.name() + " (" + n3 + "\u0441)");
    }

    private PotionRule a(class_6880<class_1291> class_68802, int n) {
        return new PotionRule(class_68802, n);
    }

    static final class PotionState {
        private final String tc;
        private final boolean td;
        private final PotionRule[] te;

        PotionState(String string, boolean bl, PotionRule ... potionRuleArray) {
            this.tc = string;
            this.td = bl;
            this.te = potionRuleArray;
        }

        @Override
        public final String toString() {
            return "PotionState[" + "name=" + this.tc + ", " + "enabled=" + this.td + ", " + "requirements=" + this.te + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.tc, this.td, this.te);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PotionState other)) return false;
            return java.util.Objects.deepEquals(this.tc, other.tc)
                && java.util.Objects.deepEquals(this.td, other.td)
                && java.util.Objects.deepEquals(this.te, other.te);
        }

        public String name() {
            return this.tc;
        }

        public boolean enabled() {
            return this.td;
        }

        public PotionRule[] gO() {
            return this.te;
        }
    }

    static final class PotionRule {
        private final class_6880<class_1291> ta;
        private final int tb;

        PotionRule(class_6880<class_1291> class_68802, int n) {
            this.ta = class_68802;
            this.tb = n;
        }

        @Override
        public final String toString() {
            return "PotionRule[" + "effect=" + this.ta + ", " + "minLevel=" + this.tb + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ta, this.tb);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PotionRule other)) return false;
            return java.util.Objects.deepEquals(this.ta, other.ta)
                && java.util.Objects.deepEquals(this.tb, other.tb);
        }

        public class_6880<class_1291> gM() {
            return this.ta;
        }

        public int gN() {
            return this.tb;
        }
    }
}

