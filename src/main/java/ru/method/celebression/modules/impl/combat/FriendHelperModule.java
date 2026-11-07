package ru.method.celebression.modules.impl.combat;

import ru.method.celebression.api.setting.Setting;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_9282;
import net.minecraft.class_9334;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class FriendHelperModule
extends Module {
    public final BooleanSetting rv = new BooleanSetting("settings.friendhelper.colorarmor", true);
    private final Map<class_1657, class_1799[]> rw = new HashMap<class_1657, class_1799[]>();
    private final class_1799[] rx = new class_1799[4];
    private static final int ry = 65280;

    public FriendHelperModule() {
        super("FriendHelper", ModuleCategory.qj);
        this.gi();
    }

    private void gi() {
        this.rx[0] = new class_1799((class_1935)class_1802.field_8370);
        this.rx[1] = new class_1799((class_1935)class_1802.field_8570);
        this.rx[2] = new class_1799((class_1935)class_1802.field_8577);
        this.rx[3] = new class_1799((class_1935)class_1802.field_8267);
        for (class_1799 class_17992 : this.rx) {
            if (class_17992 == null) continue;
            class_17992.method_57379(class_9334.field_49644, new class_9282(65280, false));
        }
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (FriendHelperModule.fullNullCheck()) {
            return;
        }
        if (!((Boolean)this.rv.jT()).booleanValue()) {
            this.gj();
            return;
        }
        for (class_1657 class_16572 : FriendHelperModule.aiG.field_1687.method_18456()) {
            if (class_16572 == FriendHelperModule.aiG.field_1724) continue;
            String string = class_16572.method_5477().getString();
            boolean bl = Celebression.getInstance().getFriendManager().av(string);
            if (bl) {
                this.g(class_16572);
                continue;
            }
            this.h(class_16572);
        }
        this.rw.entrySet().removeIf(entry -> {
            class_1657 class_16572 = (class_1657)entry.getKey();
            return class_16572 == null || class_16572.method_31481() || class_16572.method_37908() != FriendHelperModule.aiG.field_1687;
        });
    }

    private void g(class_1657 player) {
        class_1799[] originalArmor = this.rw.computeIfAbsent(player, ignored -> new class_1799[]{
            class_1799.field_8037,
            class_1799.field_8037,
            class_1799.field_8037,
            class_1799.field_8037
        });
        for (int slot = 0; slot < 4; ++slot) {
            class_1799 equippedItem = (class_1799)player.method_31548().field_7548.get(slot);
            if (this.a(equippedItem, slot)) {
                continue;
            }
            originalArmor[slot] = equippedItem.method_7960() ? class_1799.field_8037 : equippedItem.method_7972();
            player.method_31548().field_7548.set(slot, this.rx[slot].method_7972());
        }
    }

    private boolean a(class_1799 class_17992, int n) {
        if (class_17992.method_7960()) {
            return false;
        }
        if (!class_1799.method_7984((class_1799)class_17992, (class_1799)this.rx[n])) {
            return false;
        }
        class_9282 class_92822 = (class_9282)class_17992.method_57824(class_9334.field_49644);
        return class_92822 != null && class_92822.comp_2384() == 65280;
    }

    private void h(class_1657 class_16572) {
        if (this.rw.containsKey(class_16572)) {
            class_1799[] class_1799Array = this.rw.get(class_16572);
            for (int i = 0; i < 4; ++i) {
                class_16572.method_31548().field_7548.set(i, class_1799Array[i]);
            }
            this.rw.remove(class_16572);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.gj();
    }

    private void gj() {
        if (FriendHelperModule.fullNullCheck()) {
            return;
        }
        for (Map.Entry<class_1657, class_1799[]> entry : this.rw.entrySet()) {
            class_1657 class_16572 = entry.getKey();
            class_1799[] class_1799Array = entry.getValue();
            if (class_16572 == null || class_16572.method_31481()) continue;
            for (int i = 0; i < 4; ++i) {
                class_16572.method_31548().field_7548.set(i, class_1799Array[i]);
            }
        }
        this.rw.clear();
    }
}
