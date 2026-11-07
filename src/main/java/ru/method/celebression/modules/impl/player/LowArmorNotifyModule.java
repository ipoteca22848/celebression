package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.hud.LowArmorNotificationsHud;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class LowArmorNotifyModule
extends Module {
    private final SliderSetting vD = new SliderSetting("modules.settings.low_armor_notify.durability_threshold", 50.0f, 1.0f, 70.0f, 1.0f);
    private final SliderSetting vE = new SliderSetting("modules.settings.low_armor_notify.interval", 15.0f, 1.0f, 120.0f, 1.0f);
    private final BooleanSetting vF = new BooleanSetting("modules.settings.low_armor_notify.show_once", false);
    private final BooleanSetting vG = new BooleanSetting("modules.settings.low_armor_notify.helmet", true);
    private final BooleanSetting vH = new BooleanSetting("modules.settings.low_armor_notify.chestplate", true);
    private final BooleanSetting vI = new BooleanSetting("modules.settings.low_armor_notify.leggings", true);
    private final BooleanSetting vJ = new BooleanSetting("modules.settings.low_armor_notify.boots", true);
    private final BooleanSetting vK = new BooleanSetting("modules.settings.low_armor_notify.shield", true);
    private final long[] vL = new long[5];
    private final String[] vM = new String[5];
    private final boolean[] vN = new boolean[5];
    private int tickCounter;

    public LowArmorNotifyModule() {
        super("LowArmorNotify", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.q(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.q(false);
    }

    private void q(boolean bl) {
        HudManager hudManager = Celebression.getInstance().getHudManager();
        if (hudManager == null) {
            return;
        }
        LowArmorNotificationsHud lowArmorNotificationsHud = hudManager.a(LowArmorNotificationsHud.class);
        if (lowArmorNotificationsHud == null) {
            return;
        }
        BooleanSetting booleanSetting = hudManager.b(lowArmorNotificationsHud);
        if (booleanSetting == null || (Boolean)booleanSetting.jT() == bl) {
            return;
        }
        booleanSetting.a(bl);
        hudManager.a(booleanSetting, bl, 0.0f, 0.0f);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (LowArmorNotifyModule.fullNullCheck() || ++this.tickCounter % 20 != 0) {
            return;
        }
        this.a(3, (Boolean)this.vG.jT(), "\u0448\u043b\u0435\u043c");
        this.a(2, (Boolean)this.vH.jT(), "\u043d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a");
        this.a(1, (Boolean)this.vI.jT(), "\u043f\u043e\u043d\u043e\u0436\u0438");
        this.a(0, (Boolean)this.vJ.jT(), "\u0431\u043e\u0442\u0438\u043d\u043a\u0438");
        this.hv();
    }

    private void a(int n, boolean bl, String string) {
        if (!bl) {
            return;
        }
        class_1799 class_17992 = (class_1799)LowArmorNotifyModule.aiG.field_1724.method_31548().field_7548.get(n);
        if (n == 2 && class_17992.method_31574(class_1802.field_8833)) {
            return;
        }
        this.a(class_17992, n, string);
    }

    private void hv() {
        if (!((Boolean)this.vK.jT()).booleanValue()) {
            return;
        }
        class_1799 class_17992 = LowArmorNotifyModule.aiG.field_1724.method_6047();
        class_1799 class_17993 = LowArmorNotifyModule.aiG.field_1724.method_6079();
        if (class_17992.method_31574(class_1802.field_8255)) {
            this.a(class_17992, 4, "\u0449\u0438\u0442");
        } else if (class_17993.method_31574(class_1802.field_8255)) {
            this.a(class_17993, 4, "\u0449\u0438\u0442");
        }
    }

    private void a(class_1799 class_17992, int n, String string) {
        if (class_17992.method_7960() || !class_17992.method_7963()) {
            return;
        }
        this.b(class_17992, n);
        int n2 = class_17992.method_7936();
        if (n2 <= 0) {
            return;
        }
        int n3 = Math.round((float)(n2 - class_17992.method_7919()) * 100.0f / (float)n2);
        if ((float)n3 > ((Float)this.vD.jT()).floatValue()) {
            return;
        }
        if (((Boolean)this.vF.jT()).booleanValue() && this.vN[n]) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = Math.max(1L, (long)Math.round(((Float)this.vE.jT()).floatValue())) * 1000L;
        if (!((Boolean)this.vF.jT()).booleanValue() && l - this.vL[n] < l2) {
            return;
        }
        this.vL[n] = l;
        this.vN[n] = true;
        LowArmorNotificationsHud.ay("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u0441\u043a\u043e\u0440\u043e \u0441\u043b\u043e\u043c\u0430\u0435\u0442\u0441\u044f: " + string + " (" + n3 + "%)");
    }

    private void b(class_1799 class_17992, int n) {
        String string = String.valueOf(class_17992.method_7909()) + "|" + class_17992.method_7936() + "|" + class_17992.method_7964().getString();
        if (!string.equals(this.vM[n])) {
            this.vM[n] = string;
            this.vL[n] = 0L;
            this.vN[n] = false;
        }
    }
}

