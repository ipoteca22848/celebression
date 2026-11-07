package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.server.ServerDetector;

public class HolyWorldHelperModule
extends Module {
    private final BindSetting uP = new BindSetting("settings.hwhelper.bindstan", new KeyBind(-1, false));
    private final BindSetting uQ = new BindSetting("settings.hwhelper.bindtrapka", new KeyBind(-1, false));
    private final BindSetting uR = new BindSetting("settings.hwhelper.bindboomtrapka", new KeyBind(-1, false));
    private final BindSetting uS = new BindSetting("settings.hwhelper.bindboomthing", new KeyBind(-1, false));

    public HolyWorldHelperModule() {
        super("HwHelper", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        if (HolyWorldHelperModule.fullNullCheck()) {
            this.toggled = false;
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "HwHelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime!");
            this.playCriticalSound();
            this.toggled = false;
            return;
        }
        super.onEnable();
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (HolyWorldHelperModule.fullNullCheck() || HolyWorldHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "hwhelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime!");
            this.playCriticalSound();
            this.toggle();
            return;
        }
        if (keyInputEvent.getAction() == 1) {
            if (((KeyBind)this.uP.jT()).af(keyInputEvent.c())) {
                this.a(class_1802.field_8137, "settings.hwhelper.bindstan");
            }
            if (((KeyBind)this.uQ.jT()).af(keyInputEvent.c())) {
                this.a(class_1802.field_8882, "settings.hwhelper.bindtrapka");
            }
            if (((KeyBind)this.uR.jT()).af(keyInputEvent.c())) {
                this.a(class_1802.field_8662, "settings.hwhelper.bindboomtrapka");
            }
            if (((KeyBind)this.uS.jT()).af(keyInputEvent.c())) {
                this.a(class_1802.field_8814, "settings.hwhelper.bindboomthing");
            }
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (HolyWorldHelperModule.fullNullCheck() || HolyWorldHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "HwHelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime!");
            this.playCriticalSound();
            this.toggle();
            return;
        }
        if (eventMouse.getAction() == 1) {
            if (((KeyBind)this.uP.jT()).ag(eventMouse.getButton())) {
                this.a(class_1802.field_8137, "settings.hwhelper.bindstan");
            }
            if (((KeyBind)this.uQ.jT()).ag(eventMouse.getButton())) {
                this.a(class_1802.field_8882, "settings.hwhelper.bindtrapka");
            }
            if (((KeyBind)this.uR.jT()).ag(eventMouse.getButton())) {
                this.a(class_1802.field_8662, "settings.hwhelper.bindboomtrapka");
            }
            if (((KeyBind)this.uS.jT()).ag(eventMouse.getButton())) {
                this.a(class_1802.field_8814, "settings.hwhelper.bindboomthing");
            }
        }
    }

    private void a(class_1792 class_17922, String string) {
        int n = InventoryUtils.h(class_17922);
        String string2 = class_1074.method_4662((String)string, (Object[])new Object[0]);
        if (n == -1) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1061) + string2 + String.valueOf(class_124.field_1068) + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
            return;
        }
        HolyWorldHelperModule.aiG.field_1724.method_31548().field_7545 = n;
        ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1060) + string2 + String.valueOf(class_124.field_1068) + " \u0441\u0432\u0430\u043f\u043d\u0443\u0442!");
    }
}

