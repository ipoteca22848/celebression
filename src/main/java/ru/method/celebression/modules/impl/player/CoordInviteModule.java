package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2338;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.StringSetting;

public class CoordInviteModule
extends Module {
    private final BindSetting sO = new BindSetting("modules.settings.coord_invite.bind", new KeyBind(-1, false));
    private final StringSetting sP = new StringSetting("modules.settings.coord_invite.player", "", "nickname");

    public CoordInviteModule() {
        super("CoordInvite", ModuleCategory.qm);
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (CoordInviteModule.fullNullCheck() || CoordInviteModule.aiG.field_1755 != null || keyInputEvent.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.sO.jT()).af(keyInputEvent.c())) {
            this.gL();
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (CoordInviteModule.fullNullCheck() || CoordInviteModule.aiG.field_1755 != null || eventMouse.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.sO.jT()).ag(eventMouse.getButton())) {
            this.gL();
        }
    }

    private void gL() {
        if (CoordInviteModule.aiG.field_1724.field_3944 == null) {
            return;
        }
        class_2338 class_23382 = CoordInviteModule.aiG.field_1724.method_24515();
        String string = "\u041a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b: " + class_23382.method_10263() + " " + class_23382.method_10264() + " " + class_23382.method_10260();
        String string2 = this.sP.kg().trim();
        if (string2.isEmpty()) {
            CoordInviteModule.aiG.field_1724.field_3944.method_45729(string);
        } else {
            CoordInviteModule.aiG.field_1724.field_3944.method_45730("msg " + string2 + " " + string);
        }
    }
}

