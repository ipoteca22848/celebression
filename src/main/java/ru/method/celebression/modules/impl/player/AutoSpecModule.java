package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class AutoSpecModule
extends Module {
    private final BindSetting sq = new BindSetting("modules.settings.autospec.bind", new KeyBind(-1, false));

    public AutoSpecModule() {
        super("AutoSpec", ModuleCategory.qm);
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (AutoSpecModule.fullNullCheck() || AutoSpecModule.aiG.field_1755 != null || keyInputEvent.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.sq.jT()).af(keyInputEvent.c())) {
            this.gv();
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (AutoSpecModule.fullNullCheck() || AutoSpecModule.aiG.field_1755 != null || eventMouse.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.sq.jT()).ag(eventMouse.getButton())) {
            this.gv();
        }
    }

    private void gv() {
        if (AutoSpecModule.aiG.field_1724.field_3944 == null) {
            return;
        }
        String string = AutoSpecModule.aiG.field_1724.method_7334().getName();
        AutoSpecModule.aiG.field_1724.field_3944.method_45729("!spec " + string);
    }
}

