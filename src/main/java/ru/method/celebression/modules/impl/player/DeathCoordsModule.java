package ru.method.celebression.modules.impl.player;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2338;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class DeathCoordsModule
extends Module {
    private boolean sQ;

    public DeathCoordsModule() {
        super("DeathCoords", ModuleCategory.qm);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        boolean bl;
        if (DeathCoordsModule.fullNullCheck()) {
            return;
        }
        boolean bl2 = bl = DeathCoordsModule.aiG.field_1724.method_29504() || DeathCoordsModule.aiG.field_1724.method_6032() <= 0.0f;
        if (!bl) {
            this.sQ = false;
            return;
        }
        if (this.sQ) {
            return;
        }
        class_2338 class_23382 = DeathCoordsModule.aiG.field_1724.method_24515();
        ChatOutput.cG("\u041a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b \u0441\u043c\u0435\u0440\u0442\u0438: " + class_23382.method_10263() + " " + class_23382.method_10264() + " " + class_23382.method_10260());
        this.sQ = true;
    }
}

