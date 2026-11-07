package ru.method.celebression.modules.impl.player;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_1812;
import net.minecraft.class_1839;
import net.minecraft.class_1937;
import net.minecraft.class_2663;
import net.minecraft.class_9334;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;

public class UseTrackerModule
extends Module {
    private static final int xk = 4;
    private final Map<class_1268, class_1799> xl = new EnumMap<class_1268, class_1799>(class_1268.class);
    private final Map<class_1268, Integer> xm = new EnumMap<class_1268, Integer>(class_1268.class);
    private final Map<class_1268, Long> xn = new EnumMap<class_1268, Long>(class_1268.class);
    private long xo = -1L;
    private String xp = "";

    public UseTrackerModule() {
        super("UseTracker", ModuleCategory.qm);
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (UseTrackerModule.fullNullCheck()) {
            return;
        }
        this.a(class_1268.field_5808);
        this.a(class_1268.field_5810);
        this.hT();
    }

    private void a(class_1268 class_12682) {
        boolean bl = UseTrackerModule.aiG.field_1724.method_6115() && UseTrackerModule.aiG.field_1724.method_6058() == class_12682;
        class_1799 class_17992 = UseTrackerModule.aiG.field_1724.method_5998(class_12682);
        if (bl && !class_17992.method_7960()) {
            this.xn.remove(class_12682);
            if (!this.xl.containsKey(class_12682) || !class_1799.method_7984((class_1799)this.xl.get(class_12682), (class_1799)class_17992)) {
                this.xl.put(class_12682, class_17992.method_7972());
                this.xm.put(class_12682, UseTrackerModule.aiG.field_1724.method_6014());
            }
            return;
        }
        if (this.xl.containsKey(class_12682) && !this.xn.containsKey(class_12682)) {
            this.xn.put(class_12682, UseTrackerModule.aiG.field_1687.method_8510() + 4L);
        }
    }

    private void hT() {
        if (this.xn.isEmpty()) {
            return;
        }
        long l = UseTrackerModule.aiG.field_1687.method_8510();
        this.xn.entrySet().removeIf(entry -> {
            if (l < (Long)entry.getValue()) {
                return false;
            }
            class_1268 class_12682 = (class_1268)entry.getKey();
            this.xl.remove(class_12682);
            this.xm.remove(class_12682);
            return true;
        });
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive receive) {
        if (UseTrackerModule.fullNullCheck()) {
            return;
        }
        if (receive.i() instanceof class_2663) {
            class_2663 class_26632 = (class_2663)receive.i();
            if (class_26632.method_11469((class_1937)UseTrackerModule.aiG.field_1687) != UseTrackerModule.aiG.field_1724 || class_26632.method_11470() != 9) {
                return;
            }
            class_1799 usedStack = null;
            Iterator<Map.Entry<class_1268, class_1799>> iterator = this.xl.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<class_1268, class_1799> entry = iterator.next();
                class_1799 trackedStack = entry.getValue();
                if (trackedStack == null || trackedStack.method_7960() || !this.r(trackedStack)) continue;
                usedStack = trackedStack;
                break;
            }
            class_1799 activeStack = UseTrackerModule.aiG.field_1724.method_6030();
            if (usedStack == null && UseTrackerModule.aiG.field_1724.method_6115() && !activeStack.method_7960() && this.r(activeStack)) {
                usedStack = activeStack;
            }
            class_1799 mainHandStack = UseTrackerModule.aiG.field_1724.method_6047();
            if (usedStack == null && !mainHandStack.method_7960() && this.r(mainHandStack)) {
                usedStack = mainHandStack;
            }
            if (usedStack != null && !usedStack.method_7960()) {
                String itemName = usedStack.method_7909().method_63680().getString();
                boolean bl = usedStack.method_7909() instanceof class_1812;
                long l = UseTrackerModule.aiG.field_1687.method_8510();
                String string = itemName + ":" + bl;
                boolean bl2 = l - this.xo <= 1L && string.equals(this.xp);
                this.xo = l;
                this.xp = string;
                if (!bl2) {
                    ChatOutput.cG(bl ? class_1074.method_4662((String)"modules.usetracker.message2", (Object[])new Object[]{String.valueOf(class_124.field_1061) + "\u0412\u044b" + String.valueOf(class_124.field_1068), String.valueOf(class_124.field_1065) + itemName + String.valueOf(class_124.field_1068)}) : class_1074.method_4662((String)"modules.usetracker.message", (Object[])new Object[]{String.valueOf(class_124.field_1061) + "\u0412\u044b" + String.valueOf(class_124.field_1068), String.valueOf(class_124.field_1065) + itemName + String.valueOf(class_124.field_1068)}));
                }
            }
            this.xl.clear();
            this.xm.clear();
            this.xn.clear();
        }
    }

    private boolean r(class_1799 class_17992) {
        class_1839 class_18392 = class_17992.method_7976();
        return class_17992.method_57824(class_9334.field_50075) != null || class_17992.method_7909() instanceof class_1812 || class_18392 == class_1839.field_8950 || class_18392 == class_1839.field_8946;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.xl.clear();
        this.xm.clear();
        this.xn.clear();
        this.xo = -1L;
        this.xp = "";
    }
}
