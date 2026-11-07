package ru.method.celebression.modules.impl.player;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_7439;
import ru.method.celebression.Celebression;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.manager.UserSyncManager;

public class AutoAcceptModule
extends Module {
    public AutoAcceptModule() {
        super("AutoAccept", ModuleCategory.qm);
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive receive) {
        class_7439 class_74392;
        if (AutoAcceptModule.fullNullCheck()) {
            return;
        }
        Object object = receive.i();
        if (object instanceof class_7439 && ((class_74392 = (class_7439)object).comp_763().getString().toLowerCase().contains("\u0445\u043e\u0447\u0435\u0442") || class_74392.comp_763().getString().toLowerCase().contains("\u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f") || class_74392.comp_763().getString().toLowerCase().contains("\u043a \u0432\u0430\u043c") || class_74392.comp_763().getString().toLowerCase().contains("wants") || class_74392.comp_763().getString().toLowerCase().contains("teleport") || class_74392.comp_763().getString().toLowerCase().contains("to you")) && this.aU((String)(object = class_74392.comp_763().getString()))) {
            aiG.method_1562().method_45730("tpaccept");
        }
    }

    private boolean aU(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        for (String string2 : Celebression.getInstance().getFriendManager().es()) {
            if (string2 == null || string2.isBlank() || !string.contains(string2)) continue;
            return true;
        }
        UserSyncManager userSyncManager = Celebression.getInstance().getUserSyncManager();
        return userSyncManager != null && userSyncManager.E(string);
    }
}

