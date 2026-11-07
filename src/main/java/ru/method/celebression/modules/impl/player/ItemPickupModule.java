package ru.method.celebression.modules.impl.player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2596;
import net.minecraft.class_2775;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;

public class ItemPickupModule
extends Module {
    private static final String[] uW = new String[]{"\u043a\u0440\u0443\u0448\u0438\u0442\u0435\u043b", "\u043c\u043e\u043b\u043e\u0442 \u0442\u043e\u0440\u0430", "\u0441\u0444\u0435\u0440\u0430", "\u0442\u0430\u043b\u0438\u0441\u043c\u0430\u043d", "\u043b\u043e\u0432\u0443\u0448\u043a\u0430", "\u0442\u0451\u043c\u043d\u044b\u0439 \u043f\u0443\u043b\u044c\u0441", "\u0442\u0435\u043c\u043d\u044b\u0439 \u043f\u0443\u043b\u044c\u0441"};
    private static final long uX = 750L;
    private static final long uY = 3000L;
    private final Map<Integer, Long> uZ = new HashMap<Integer, Long>();
    private final Map<Integer, PickupEntry> va = new ConcurrentHashMap<Integer, PickupEntry>();

    public ItemPickupModule() {
        super("ItemPickup", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.uZ.clear();
        this.va.clear();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (ItemPickupModule.fullNullCheck()) {
            return;
        }
        long l = System.currentTimeMillis();
        this.va.entrySet().removeIf(entry -> l - ((PickupEntry)entry.getValue()).hr() > 3000L);
        for (class_1297 class_12972 : ItemPickupModule.aiG.field_1687.method_18112()) {
            class_1542 class_15422;
            if (!(class_12972 instanceof class_1542) || (class_15422 = (class_1542)class_12972).method_5858((class_1297)ItemPickupModule.aiG.field_1724) > 64.0) continue;
            class_1799 class_17992 = class_15422.method_6983();
            PickupEntry pickupEntry = this.va.get(class_15422.method_5628());
            if (pickupEntry != null && class_1799.method_7973((class_1799)pickupEntry.stack(), (class_1799)class_17992)) {
                this.va.put(class_15422.method_5628(), new PickupEntry(pickupEntry.stack(), l));
                continue;
            }
            this.va.put(class_15422.method_5628(), new PickupEntry(class_17992.method_7972(), l));
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        class_2596<?> class_25962;
        if (ItemPickupModule.fullNullCheck() || !((class_25962 = receive.i()) instanceof class_2775)) {
            return;
        }
        class_2775 class_27752 = (class_2775)class_25962;
        if (class_27752.method_11912() != ItemPickupModule.aiG.field_1724.method_5628()) {
            return;
        }
        long l = System.currentTimeMillis();
        this.uZ.entrySet().removeIf(entry -> l - (Long)entry.getValue() > 750L);
        int n = class_27752.method_11915();
        if (this.uZ.containsKey(n)) {
            return;
        }
        this.uZ.put(n, l);
        class_1297 entity = ItemPickupModule.aiG.field_1687.method_8469(n);
        class_1799 stack = entity instanceof class_1542 itemEntity ? itemEntity.method_6983() : null;
        if (stack == null || stack.method_7960()) {
            PickupEntry pickupEntry = this.va.get(n);
            stack = pickupEntry == null ? null : pickupEntry.stack();
        }
        if (stack == null || stack.method_7960()) {
            return;
        }
        String itemName = this.bC(stack.method_7964().getString());
        if (!this.bB(itemName)) {
            return;
        }
        this.a(stack, itemName);
    }

    private boolean bB(String string) {
        String string2 = string.toLowerCase(Locale.ROOT);
        for (String string3 : uW) {
            if (!string2.contains(string3)) continue;
            return true;
        }
        return false;
    }

    private String bC(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.replaceAll("(?i)(\u00a7|&)[0-9A-FK-OR]", "").trim();
        string2 = string2.replaceAll("^(?:123\\s*)+", "").replaceAll("(?:\\s*123)+$", "").trim();
        return string2;
    }

    private void a(class_1799 class_17992, String string) {
        class_2561 class_25612 = class_17992.method_7964();
        class_2583 class_25832 = class_25612.method_10866() == null || class_25612.method_10866().method_10967() ? class_2583.field_24360.method_27703(class_5251.method_27718((class_124)class_124.field_1065)) : class_25612.method_10866();
        class_5250 class_52502 = class_2561.method_43470((String)"\u041f\u043e\u0434\u043e\u0431\u0440\u0430\u043d \u043f\u0440\u0435\u0434\u043c\u0435\u0442: ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27718((class_124)class_124.field_1068))).method_10852((class_2561)class_2561.method_43470((String)string).method_10862(class_25832));
        ChatOutput.b(class_52502);
    }

    static final class PickupEntry {
        private final class_1799 vb;
        private final long vc;

        PickupEntry(class_1799 class_17992, long l) {
            this.vb = class_17992;
            this.vc = l;
        }

        @Override
        public final String toString() {
            return "PickupEntry[" + "stack=" + this.vb + ", " + "at=" + this.vc + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.vb, this.vc);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PickupEntry other)) return false;
            return java.util.Objects.deepEquals(this.vb, other.vb)
                && java.util.Objects.deepEquals(this.vc, other.vc);
        }

        public class_1799 stack() {
            return this.vb;
        }

        public long hr() {
            return this.vc;
        }
    }
}
