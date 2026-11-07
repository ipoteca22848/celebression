package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1934;
import net.minecraft.class_1935;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_2797;
import net.minecraft.class_2828;
import net.minecraft.class_2833;
import net.minecraft.class_2846;
import net.minecraft.class_2848;
import net.minecraft.class_2851;
import net.minecraft.class_2879;
import net.minecraft.class_2885;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_640;
import net.minecraft.class_745;
import net.minecraft.class_746;
import net.minecraft.class_7472;
import ru.method.celebression.api.mixins.accessors.IClientPlayNetworkHandler;
import ru.method.celebression.api.mixins.accessors.IEntityRemoval;
import ru.method.celebression.api.event.BlockBreakEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.command.CrasherChatOutput;
import ru.method.celebression.command.CrasherCommandProcessor;
import ru.method.celebression.modules.impl.player.CrasherMovementController;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.modules.impl.player.FakeChatSequence;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.event.PacketEvent;

public class CrasherModule
extends Module {
    private final BooleanSetting xD = new BooleanSetting("settings.crasher.freeze_packets", true);
    private final BooleanSetting xE = new BooleanSetting("settings.crasher.fake_commands", true);
    private final BooleanSetting xF = new BooleanSetting("settings.crasher.flight", true);
    private final EnumSetting<CrashMode> xG = new EnumSetting<CrashMode>("settings.crasher.gamemode", CrashMode.yb);
    private final BooleanSetting xH = new BooleanSetting("settings.crasher.hide_players", false);
    private final BooleanSetting xI = new BooleanSetting("settings.crasher.restore_blocks", true);
    private final BooleanSetting xJ = new BooleanSetting("settings.crasher.fake_physics", true);
    private final BooleanSetting xK = new BooleanSetting("settings.crasher.restore_position", true);
    private final BooleanSetting xL = new BooleanSetting("settings.crasher.fake_chat", true);
    private class_243 xM;
    private float uh;
    private float uk;
    private class_638 ud;
    private final Map<class_2338, class_2680> xN = new LinkedHashMap<class_2338, class_2680>();
    private final List<class_1297> xO = new ArrayList<class_1297>();
    private final Map<UUID, class_640> xP = new LinkedHashMap<UUID, class_640>();
    private class_1934 xQ;
    private boolean xR;
    private boolean xS;
    private boolean xT;
    private int xU = -1;
    private class_1799 xV = class_1799.field_8037;
    private final CrasherMovementController xW = new CrasherMovementController(this);
    private final CrasherCommandProcessor xX = new CrasherCommandProcessor(this);
    private final FakeChatSequence xY = new FakeChatSequence();

    public CrasherModule() {
        super("Crasher", ModuleCategory.qm);
        this.getSettings().add(this.xD);
        this.getSettings().add(this.xE);
        this.getSettings().add(this.xF);
        this.getSettings().add(this.xG);
        this.getSettings().add(this.xH);
        this.getSettings().add(this.xI);
        this.getSettings().add(this.xJ);
        this.getSettings().add(this.xK);
        this.getSettings().add(this.xL);
        AttackBlockCallback.EVENT.register((class_16572, class_19372, class_12682, class_23382, class_23502) -> {
            if (this.isToggle() && class_19372.method_8608() && this.ig()) {
                this.xX.b(class_23382);
                return class_1269.field_5812;
            }
            return class_1269.field_5811;
        });
        UseBlockCallback.EVENT.register((class_16572, class_19372, class_12682, class_39652) -> {
            if (this.isToggle() && class_19372.method_8608() && class_12682 == class_1268.field_5808 && this.ig()) {
                this.xX.c(class_39652.method_17777());
                return class_1269.field_5812;
            }
            return class_1269.field_5811;
        });
    }

    @Override
    public void onEnable() {
        class_1934 class_19342;
        super.onEnable();
        class_746 class_7462 = CrasherModule.aiG.field_1724;
        if (class_7462 == null || CrasherModule.aiG.field_1687 == null) {
            return;
        }
        this.ud = CrasherModule.aiG.field_1687;
        this.xM = class_7462.method_19538();
        this.uh = class_7462.method_36454();
        this.uk = class_7462.method_36455();
        this.xT = false;
        this.xQ = CrasherModule.aiG.field_1761 != null ? CrasherModule.aiG.field_1761.method_2920() : class_1934.field_9215;
        this.xR = class_7462.method_31549().field_7478;
        this.xS = class_7462.method_31549().field_7479;
        if (((Boolean)this.xF.jT()).booleanValue()) {
            class_7462.method_31549().field_7478 = true;
        }
        if ((class_19342 = CrasherModule.a((CrashMode)this.xG.jT())) != null && CrasherModule.aiG.field_1761 != null) {
            boolean bl;
            CrasherModule.aiG.field_1761.method_2907(class_19342);
            class_7462.method_31549().field_7478 = bl = class_19342 == class_1934.field_9220 || class_19342 == class_1934.field_9219 || (Boolean)this.xF.jT() != false;
            if (!bl) {
                class_7462.method_31549().field_7479 = false;
            }
        }
        if (((Boolean)this.xH.jT()).booleanValue()) {
            this.ij();
        }
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("[Crasher] ", class_124.field_1063).method_10852((class_2561)CrasherChatOutput.a("\u0420\u0435\u0436\u0438\u043c ", class_124.field_1080)).method_10852((class_2561)CrasherChatOutput.a("ON", class_124.field_1060, class_124.field_1067)).method_10852((class_2561)CrasherChatOutput.a(" \u2014 \u0441\u0435\u0440\u0432\u0435\u0440 \u0432\u0438\u0434\u0438\u0442 \u0442\u0435\u0431\u044f \u043d\u0430 \u043c\u0435\u0441\u0442\u0435.", class_124.field_1080)));
    }

    @Override
    public void onDisable() {
        this.r(true);
        super.onDisable();
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("[Crasher] ", class_124.field_1063).method_10852((class_2561)CrasherChatOutput.a("\u0420\u0435\u0436\u0438\u043c ", class_124.field_1080)).method_10852((class_2561)CrasherChatOutput.a("OFF", class_124.field_1061, class_124.field_1067)).method_10852((class_2561)CrasherChatOutput.a(" \u2014 \u0432\u0441\u0451 \u043e\u0442\u043a\u0430\u0447\u0435\u043d\u043e, \u0442\u044b \u043d\u0430 \u0438\u0441\u0445\u043e\u0434\u043d\u043e\u0439 \u0442\u043e\u0447\u043a\u0435.", class_124.field_1080)));
    }

    private void r(boolean bl) {
        boolean bl2;
        class_746 class_7462 = CrasherModule.aiG.field_1724;
        boolean bl3 = bl2 = CrasherModule.aiG.field_1687 != null && CrasherModule.aiG.field_1687 == this.ud;
        if (bl && bl2) {
            this.ib();
            this.ik();
            this.ih();
        } else {
            this.xN.clear();
            this.xO.clear();
            this.xP.clear();
            this.xU = -1;
            this.xV = class_1799.field_8037;
        }
        this.xW.clear();
        this.xY.clear();
        this.xX.ir();
        if (bl && class_7462 != null) {
            if (CrasherModule.aiG.field_1761 != null && this.xQ != null) {
                CrasherModule.aiG.field_1761.method_2907(this.xQ);
            }
            class_7462.method_31549().field_7478 = this.xR;
            class_7462.method_31549().field_7479 = this.xS;
            class_7462.method_7355();
            if (((Boolean)this.xK.jT()).booleanValue() && this.xM != null && bl2) {
                class_7462.method_18799(class_243.field_1353);
                class_7462.field_6017 = 0.0f;
                class_7462.method_5808(this.xM.field_1352, this.xM.field_1351, this.xM.field_1350, this.uh, this.uk);
                class_7462.method_36456(this.uh);
                class_7462.method_36457(this.uk);
                class_7462.field_6014 = this.xM.field_1352;
                class_7462.field_6036 = this.xM.field_1351;
                class_7462.field_5969 = this.xM.field_1350;
                class_7462.field_6038 = this.xM.field_1352;
                class_7462.field_5971 = this.xM.field_1351;
                class_7462.field_5989 = this.xM.field_1350;
            }
        }
        this.xM = null;
        this.ud = null;
        this.xQ = null;
        this.xT = false;
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send send) {
        class_2596<?> class_25962 = send.i();
        if (((Boolean)this.xE.jT()).booleanValue()) {
            class_2797 class_27972;
            String string = null;
            if (class_25962 instanceof class_7472) {
                class_7472 class_74722 = (class_7472)class_25962;
                string = class_74722.comp_808();
            } else if (class_25962 instanceof class_2797 && (class_27972 = (class_2797)class_25962).comp_945().startsWith("/")) {
                string = class_27972.comp_945().substring(1);
            }
            if (string != null && this.xX.bK(string)) {
                send.cancel();
                return;
            }
        }
        if (!((Boolean)this.xD.jT()).booleanValue()) {
            return;
        }
        if (class_25962 instanceof class_2828 || class_25962 instanceof class_2833 || class_25962 instanceof class_2851 || class_25962 instanceof class_2848 || class_25962 instanceof class_2879 || class_25962 instanceof class_2846 || class_25962 instanceof class_2885) {
            send.cancel();
        }
    }

    @EventHandler
    private void onStartBreakBlock(BlockBreakEvent blockBreakEvent) {
        if (!((Boolean)this.xI.jT()).booleanValue() || CrasherModule.fullNullCheck()) {
            return;
        }
        this.a(blockBreakEvent.p());
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (CrasherModule.fullNullCheck()) {
            this.r(false);
            return;
        }
        if (this.ud != null && CrasherModule.aiG.field_1687 != this.ud) {
            this.r(false);
            this.ud = CrasherModule.aiG.field_1687;
            this.xM = CrasherModule.aiG.field_1724.method_19538();
            this.uh = CrasherModule.aiG.field_1724.method_36454();
            this.uk = CrasherModule.aiG.field_1724.method_36455();
            return;
        }
        this.xY.tick();
        if (!((Boolean)this.xJ.jT()).booleanValue()) {
            this.xW.clear();
            return;
        }
        for (class_1657 class_16572 : new ArrayList<>(CrasherModule.aiG.field_1687.method_18456())) {
            if (!(class_16572 instanceof class_745)) continue;
            class_745 class_7452 = (class_745)class_16572;
            this.xW.a(class_7452);
        }
    }

    private void a(class_2338 class_23382) {
        class_638 class_6382 = CrasherModule.aiG.field_1687;
        if (class_6382 == null) {
            return;
        }
        class_2338 class_23383 = class_23382.method_10062();
        this.xN.putIfAbsent(class_23383, class_6382.method_8320(class_23383));
    }

    public void a(class_2338 class_23382, class_2680 class_26802) {
        class_638 class_6382 = CrasherModule.aiG.field_1687;
        if (class_6382 == null) {
            return;
        }
        class_2338 class_23383 = class_23382.method_10062();
        this.a(class_23383);
        class_6382.method_8652(class_23383, class_26802, 3);
    }

    private void ib() {
        class_638 class_6382 = CrasherModule.aiG.field_1687;
        if (class_6382 != null) {
            Iterator<Map.Entry<class_2338, class_2680>> iterator = this.xN.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<class_2338, class_2680> entry = iterator.next();
                class_2680 blockState = entry.getValue();
                class_6382.method_8652(entry.getKey(), blockState == null ? class_2246.field_10124.method_9564() : blockState, 3);
            }
        }
        this.xN.clear();
    }

    public void ic() {
        if (((Boolean)this.xL.jT()).booleanValue()) {
            this.xY.im();
        }
    }

    public int ie() {
        return this.xN.size();
    }

    public void equipSelectionWand() {
        class_746 class_7462 = CrasherModule.aiG.field_1724;
        if (!this.isToggle() || class_7462 == null) {
            return;
        }
        int n = class_7462.method_31548().field_7545;
        if (this.xU < 0) {
            this.xU = n;
            this.xV = class_7462.method_31548().method_5438(n).method_7972();
        }
        class_7462.method_31548().method_5447(n, new class_1799((class_1935)class_1802.field_8406));
    }

    public boolean ig() {
        return this.isToggle() && CrasherModule.aiG.field_1724 != null && CrasherModule.aiG.field_1724.method_6047().method_31574(class_1802.field_8406);
    }

    private void ih() {
        class_746 class_7462 = CrasherModule.aiG.field_1724;
        if (class_7462 != null && this.xU >= 0) {
            class_7462.method_31548().method_5447(this.xU, this.xV);
        }
        this.xU = -1;
        this.xV = class_1799.field_8037;
    }

    public void s(boolean bl) {
        this.xT = bl;
    }

    public boolean ii() {
        return this.isToggle() && this.xT;
    }

    public int ij() {
        class_638 class_6382 = CrasherModule.aiG.field_1687;
        class_634 class_6342 = aiG.method_1562();
        if (class_6382 == null || class_6342 == null || CrasherModule.aiG.field_1724 == null) {
            return 0;
        }
        Map<UUID, class_640> map = ((IClientPlayNetworkHandler)class_6342).celebression$getPlayerListEntries();
        for (class_1657 object : new ArrayList<>(class_6382.method_18456())) {
            if (object == CrasherModule.aiG.field_1724 || !map.containsKey(object.method_5667())) continue;
            this.xO.add((class_1297)object);
            class_6382.method_2945(object.method_5628(), class_1297.class_5529.field_26999);
        }
        for (UUID uUID : new ArrayList<UUID>(map.keySet())) {
            if (uUID.equals(CrasherModule.aiG.field_1724.method_5667())) continue;
            this.xP.put(uUID, map.remove(uUID));
        }
        return this.xP.size();
    }

    private void ik() {
        class_638 class_6382 = CrasherModule.aiG.field_1687;
        class_634 class_6342 = aiG.method_1562();
        if (class_6382 != null) {
            for (class_1297 class_12972 : this.xO) {
                ((IEntityRemoval)class_12972).celebression$unsetRemoved();
                class_6382.method_53875(class_12972);
            }
        }
        this.xO.clear();
        if (class_6342 != null) {
            ((IClientPlayNetworkHandler)class_6342).celebression$getPlayerListEntries().putAll(this.xP);
        }
        this.xP.clear();
    }

    private static class_1934 a(CrashMode crashMode) {
        return switch (crashMode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> null;
            case 1 -> class_1934.field_9215;
            case 2 -> class_1934.field_9220;
            case 3 -> class_1934.field_9216;
            case 4 -> class_1934.field_9219;
        };
    }

    @Override
    public boolean isUnsafe() {
        return true;
    }

    public static enum CrashMode
    implements NamedOption  {
        xZ("None"),
        ya("Survival"),
        yb("Creative"),
        yc("Adventure"),
        yd("Spectator");
        private final String ye;

private CrashMode(String string2) {
            this.ye = string2;
        }

        @Override
        public String getName() {
            return this.ye;
        }

}
}
