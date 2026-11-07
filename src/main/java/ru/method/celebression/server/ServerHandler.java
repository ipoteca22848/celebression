package ru.method.celebression.server;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_310;
import net.minecraft.class_500;
import net.minecraft.class_641;
import net.minecraft.class_642;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.server.ServerListSnapshot;

public class ServerHandler {
    private boolean initialized = false;
    private class_641 abm;

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        class_310 class_3102 = class_310.method_1551();
        if (this.abm == null) {
            this.abm = new class_641(class_3102);
            this.abm.method_2981();
        }
        if (class_3102.field_1755 instanceof class_500 && !this.initialized) {
            this.sq();
            this.initialized = true;
        }
    }

    private void sq() {
        for (class_642 class_6422 : ServerListSnapshot.sr()) {
            boolean bl = false;
            for (int i = 0; i < this.abm.method_2984(); ++i) {
                if (!this.abm.method_2982((int)i).field_3761.equals(class_6422.field_3761)) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            this.abm.method_2988(new class_642(class_6422.field_3752, class_6422.field_3761, class_642.class_8678.field_45611), false);
        }
        this.abm.method_2987();
    }
}

