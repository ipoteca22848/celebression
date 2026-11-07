package ru.method.celebression.modules.impl.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.TotemPopEvent;

public class TotemTrackerModule
extends Module {
    private final Map<UUID, Boolean> xi = new HashMap<UUID, Boolean>();
    private final Map<UUID, Long> xj = new HashMap<UUID, Long>();

    public TotemTrackerModule() {
        super("TotemTracker", ModuleCategory.qm);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.xi.clear();
        this.xj.clear();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (TotemTrackerModule.fullNullCheck()) {
            return;
        }
        for (class_1657 class_16572 : TotemTrackerModule.aiG.field_1687.method_18456()) {
            class_1799 class_17992 = this.l(class_16572);
            if (class_17992 == null) continue;
            this.xi.put(class_16572.method_5667(), class_17992.method_7958());
        }
    }

    @EventHandler
    private void onTotem(TotemPopEvent totemPopEvent) {
        if (TotemTrackerModule.fullNullCheck()) {
            return;
        }
        class_1657 class_16572 = totemPopEvent.a();
        if (class_16572 == null) {
            return;
        }
        long l = TotemTrackerModule.aiG.field_1687.method_8510();
        Long l2 = this.xj.get(class_16572.method_5667());
        if (l2 != null && l - l2 <= 1L) {
            return;
        }
        this.xj.put(class_16572.method_5667(), l);
        boolean bl = this.k(class_16572);
        String string = (bl ? String.valueOf(class_124.field_1060) + "\u2714" : String.valueOf(class_124.field_1061) + "\u2718") + String.valueOf(class_124.field_1068);
        ChatOutput.cG(class_1074.method_4662((String)"modules.totemtracker.message", (Object[])new Object[]{String.valueOf(class_124.field_1065) + class_16572.method_5477().getString() + String.valueOf(class_124.field_1068), string}));
    }

    private boolean k(class_1657 class_16572) {
        class_1799 class_17992 = this.l(class_16572);
        if (class_17992 != null) {
            return class_17992.method_7958();
        }
        Boolean bl = this.xi.get(class_16572.method_5667());
        return bl != null && bl != false;
    }

    private class_1799 l(class_1657 class_16572) {
        class_1799 class_17992 = class_16572.method_6079();
        if (class_17992.method_31574(class_1802.field_8288)) {
            return class_17992;
        }
        class_1799 class_17993 = class_16572.method_6047();
        if (class_17993.method_31574(class_1802.field_8288)) {
            return class_17993;
        }
        return null;
    }
}

