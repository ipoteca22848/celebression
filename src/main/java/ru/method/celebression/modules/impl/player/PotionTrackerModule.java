package ru.method.celebression.modules.impl.player;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1291;
import net.minecraft.class_2596;
import net.minecraft.class_2783;
import net.minecraft.class_4081;
import net.minecraft.class_6880;
import ru.method.celebression.Celebression;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.input.KeyConstants;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.render.NotificationRenderer;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.hud.PotionEffectKey;
import ru.method.celebression.util.TextFormattingStripper;

public class PotionTrackerModule
extends Module {
    private final Map<class_6880<class_1291>, Boolean> we = new HashMap<class_6880<class_1291>, Boolean>();
    private final Map<PotionEffectKey, Long> wf = new HashMap<PotionEffectKey, Long>();

    public PotionTrackerModule() {
        super("PotionTracker", ModuleCategory.qm);
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive receive) {
        if (PotionTrackerModule.fullNullCheck()) {
            return;
        }
        class_2596<?> class_25962 = receive.i();
        if (class_25962 instanceof class_2783) {
            class_2783 class_27832 = (class_2783)class_25962;
            if (PotionTrackerModule.aiG.field_1687.method_8469(class_27832.method_11943()) != PotionTrackerModule.aiG.field_1724) {
                return;
            }
            if (PotionTrackerModule.aiG.field_1724.method_6059(class_27832.method_11946())) {
                return;
            }
            long l = PotionTrackerModule.aiG.field_1687.method_8510();
            PotionEffectKey potionEffectKey = new PotionEffectKey((class_6880<class_1291>)class_27832.method_11946(), class_27832.method_11945(), class_27832.method_11944());
            Long l2 = this.wf.get(potionEffectKey);
            if (l2 != null && l - l2 <= 1L) {
                return;
            }
            this.wf.put(potionEffectKey, l);
            String string = TextFormattingStripper.da(class_1074.method_4662((String)((class_1291)class_27832.method_11946().comp_349()).method_5567(), (Object[])new Object[0]));
            String[] stringArray = new String[]{"", "I", "II", "III", "IV", "V"};
            String string2 = class_27832.method_11945() + 1 < stringArray.length ? stringArray[class_27832.method_11945() + 1] : String.valueOf(class_27832.method_11945() + 1);
            String string3 = String.format("%02d:%02d", class_27832.method_11944() / 20 / 60, class_27832.method_11944() / 20 % 60);
            ChatOutput.cG(class_1074.method_4662((String)"modules.potiontracker.message", (Object[])new Object[]{String.valueOf(class_124.field_1061) + "\u0412\u044b" + String.valueOf(class_124.field_1068), String.valueOf(class_124.field_1065) + string + " " + string2 + String.valueOf(class_124.field_1068), String.valueOf(class_124.field_1080) + string3 + String.valueOf(class_124.field_1068)}));
        }
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (PotionTrackerModule.fullNullCheck() || PotionTrackerModule.aiG.field_1724 == null) {
            return;
        }
        Map<class_6880<class_1291>, ?> map = PotionTrackerModule.aiG.field_1724.method_6088();
        for (class_6880<class_1291> class_68802 : new HashMap<class_6880<class_1291>, Boolean>(this.we).keySet()) {
            if (map.containsKey(class_68802)) continue;
            this.we.remove(class_68802);
            if (((class_1291)class_68802.comp_349()).method_18792() != class_4081.field_18271) continue;
            String string = TextFormattingStripper.da(class_1074.method_4662((String)((class_1291)class_68802.comp_349()).method_5567(), (Object[])new Object[0]));
            Celebression.getInstance().getNotifyManager().a(new NotificationRenderer(KeyConstants.akP, "\u0411\u0430\u0444\u0444 " + string + " \u0437\u0430\u043a\u043e\u043d\u0447\u0438\u043b\u0441\u044f", 2000L));
        }
        for (class_6880<class_1291> class_68802 : map.keySet()) {
            this.we.putIfAbsent(class_68802, true);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.wf.clear();
        if (PotionTrackerModule.aiG.field_1724 != null) {
            for (class_6880<class_1291> class_68802 : PotionTrackerModule.aiG.field_1724.method_6088().keySet()) {
                this.we.put(class_68802, true);
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.we.clear();
        this.wf.clear();
    }
}
