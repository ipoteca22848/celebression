package ru.method.celebression.modules.impl.client;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_1297;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.manager.UserSyncManager;

public class PartyModule
extends Module {
    private static final double vR = 2048.0;
    private final BindSetting vS = new BindSetting("settings.party.marker_bind", new KeyBind(-1, false));
    private final SliderSetting vT = new SliderSetting("settings.party.marker_lifetime", 60.0f, 30.0f, 120.0f, 1.0f);

    public PartyModule() {
        super("Party", ModuleCategory.qn);
    }

    public long hw() {
        return (long)(((Float)this.vT.jT()).floatValue() * 1000.0f);
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (PartyModule.fullNullCheck() || PartyModule.aiG.field_1755 != null || Celebression.getInstance().isPanic()) {
            return;
        }
        if (keyInputEvent.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.vS.jT()).af(keyInputEvent.c())) {
            this.hx();
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (PartyModule.fullNullCheck() || PartyModule.aiG.field_1755 != null || Celebression.getInstance().isPanic()) {
            return;
        }
        if (eventMouse.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.vS.jT()).ag(eventMouse.getButton())) {
            this.hx();
        }
    }

    private void hx() {
        UserSyncManager userSyncManager;
        Celebression celebression = Celebression.getInstance();
        UserSyncManager userSyncManager2 = userSyncManager = celebression == null ? null : celebression.getUserSyncManager();
        if (userSyncManager == null || !userSyncManager.isEnabled() || userSyncManager.eB().isBlank()) {
            ChatOutput.cG(class_1074.method_4662((String)"commands.party.unavailable", (Object[])new Object[0]));
            return;
        }
        if (!userSyncManager.eJ()) {
            ChatOutput.cG(class_1074.method_4662((String)"commands.party.not_in_party", (Object[])new Object[0]));
            return;
        }
        class_243 class_2432 = this.hy();
        if (class_2432 == null) {
            return;
        }
        userSyncManager.a(class_2432);
    }

    private class_243 hy() {
        class_243 class_2432;
        class_243 class_2433;
        if (PartyModule.aiG.field_1724 == null || PartyModule.aiG.field_1687 == null) {
            return null;
        }
        class_243 class_2434 = PartyModule.aiG.field_1724.method_33571();
        class_3959 class_39592 = new class_3959(class_2434, class_2433 = class_2434.method_1019((class_2432 = PartyModule.aiG.field_1724.method_5828(1.0f)).method_1021(2048.0)), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)PartyModule.aiG.field_1724);
        class_3965 class_39652 = PartyModule.aiG.field_1687.method_17742(class_39592);
        if (class_39652 == null || class_39652.method_17783() != class_239.class_240.field_1332) {
            return null;
        }
        return class_39652.method_17784();
    }
}

