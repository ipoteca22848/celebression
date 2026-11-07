package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1676;
import net.minecraft.class_2596;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_8143;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.modules.impl.render.KillStreakModule;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.manager.SoundManager;
import ru.method.celebression.api.event.TotemPopEvent;

public class SoundsModule
extends Module {
    private static final long wC = 4500L;
    private static final long wD = 9000L;
    private final SliderSetting wE = new SliderSetting("settings.sounds.volume", 130.0f, 0.0f, 200.0f, 5.0f);
    private final BooleanSetting wF = new BooleanSetting("settings.sounds.kills", true);
    private final BooleanSetting wG = new BooleanSetting("settings.sounds.streak", true);
    private final BooleanSetting wH = new BooleanSetting("settings.sounds.totem", true);
    private final BooleanSetting wI = new BooleanSetting("settings.sounds.death", true);
    private final BooleanSetting wJ = new BooleanSetting("settings.sounds.lowhealth", true);
    private final SliderSetting wK = new SliderSetting("settings.sounds.lowhealth_at", 6.0f, 1.0f, 19.0f, 1.0f, this.wJ::jT);
    private final Map<Integer, SoundType> wL = new ConcurrentHashMap<Integer, SoundType>();
    private final SoundManager.SoundEntry[] wM = new SoundManager.SoundEntry[]{SoundManager.SoundEntry.pS, SoundManager.SoundEntry.pT, SoundManager.SoundEntry.pU};
    private final Random rnd = new Random();
    private static final float wN = 6.0f;
    private int wO;
    private long wP;
    private boolean wQ;
    private boolean wR;

    public SoundsModule() {
        super("Sounds", ModuleCategory.qm);
    }

    @Override
    public void onDisable() {
        this.wL.clear();
        this.wO = 0;
        this.wQ = false;
        this.wR = false;
        super.onDisable();
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        class_1309 class_13092;
        if (SoundsModule.fullNullCheck() || attackEntityEvent.a() != SoundsModule.aiG.field_1724) {
            return;
        }
        class_1297 class_12972 = attackEntityEvent.b();
        if (!(class_12972 instanceof class_1309) || (class_13092 = (class_1309)class_12972) == SoundsModule.aiG.field_1724) {
            return;
        }
        if (class_13092.method_5628() < 0) {
            return;
        }
        this.wL.put(class_13092.method_5628(), new SoundType(System.currentTimeMillis(), class_13092 instanceof class_1308, class_13092 instanceof class_1657, class_13092.method_6032()));
    }

    @EventHandler
    private void onPacket(PacketEvent.Receive receive) {
        class_1309 class_13092;
        class_1297 class_12972;
        boolean bl;
        int n;
        class_8143 class_81432;
        block9: {
            block8: {
                class_2596<?> class_25962 = receive.i();
                if (!(class_25962 instanceof class_8143)) break block8;
                class_81432 = (class_8143)class_25962;
                if (!SoundsModule.fullNullCheck()) break block9;
            }
            return;
        }
        if (SoundsModule.aiG.field_1687 == null || SoundsModule.aiG.field_1724 == null) {
            return;
        }
        int n2 = class_81432.comp_1267();
        if (n2 == (n = SoundsModule.aiG.field_1724.method_5628())) {
            return;
        }
        boolean bl2 = bl = class_81432.comp_1269() == n || class_81432.comp_1270() == n;
        class_1297 ownerEntity = SoundsModule.aiG.field_1687.method_8469(class_81432.comp_1270());
        if (!bl && (!(ownerEntity instanceof class_1676) || !this.a((class_1676)ownerEntity))) {
            return;
        }
        class_12972 = SoundsModule.aiG.field_1687.method_8469(n2);
        if (class_12972 instanceof class_1309) {
            class_13092 = (class_1309)class_12972;
            if (class_12972 != SoundsModule.aiG.field_1724 && n2 >= 0) {
                this.wL.put(n2, new SoundType(System.currentTimeMillis(), class_12972 instanceof class_1308, class_12972 instanceof class_1657, class_13092.method_6032()));
            }
        }
    }

    @EventHandler
    private void onTotem(TotemPopEvent totemPopEvent) {
        if (SoundsModule.fullNullCheck() || !((Boolean)this.wH.jT()).booleanValue()) {
            return;
        }
        if (totemPopEvent.a() == SoundsModule.aiG.field_1724) {
            this.c(SoundManager.SoundEntry.pY);
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        boolean bl;
        if (SoundsModule.fullNullCheck()) {
            this.wL.clear();
            return;
        }
        long l = System.currentTimeMillis();
        if (this.wO > 0 && l - this.wP > 9000L) {
            this.wO = 0;
        }
        boolean bl2 = bl = SoundsModule.aiG.field_1724.method_29504() || SoundsModule.aiG.field_1724.method_6032() <= 0.0f;
        if (bl) {
            if (!this.wQ) {
                this.wQ = true;
                this.wO = 0;
                if (((Boolean)this.wI.jT()).booleanValue()) {
                    this.c(this.rnd.nextBoolean() ? SoundManager.SoundEntry.pW : SoundManager.SoundEntry.pX);
                }
            }
        } else {
            this.wQ = false;
        }
        float f = SoundsModule.aiG.field_1724.method_6032();
        float f2 = ((Float)this.wK.jT()).floatValue();
        if (!bl && f > 0.0f && f <= f2) {
            if (!this.wR) {
                this.wR = true;
                if (((Boolean)this.wJ.jT()).booleanValue()) {
                    this.c(SoundManager.SoundEntry.pR);
                }
            }
        } else if (f > f2 + 2.0f) {
            this.wR = false;
        }
        Iterator<Map.Entry<Integer, SoundType>> iterator = this.wL.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, SoundType> entry = iterator.next();
            if (l - entry.getValue().hr() > 4500L) {
                iterator.remove();
                continue;
            }
            class_1297 class_12972 = SoundsModule.aiG.field_1687.method_8469(entry.getKey().intValue());
            if (class_12972 instanceof class_1309) {
                class_1309 class_13092 = (class_1309)class_12972;
                if (class_13092.method_5805() && class_13092.method_6032() > 0.0f) {
                    SoundType soundType = entry.getValue();
                    entry.setValue(new SoundType(soundType.hr(), soundType.hH(), soundType.hI(), class_13092.method_6032()));
                    continue;
                }
                this.a(l, entry.getValue());
                iterator.remove();
                continue;
            }
            if (class_12972 != null) continue;
            if (entry.getValue().hJ() <= 6.0f) {
                this.a(l, entry.getValue());
            }
            iterator.remove();
        }
    }

    private void a(long l, SoundType soundType) {
        boolean bl;
        ++this.wO;
        this.wP = l;
        KillStreakModule killStreakModule = this.hG();
        boolean bl2 = killStreakModule != null && killStreakModule.isToggled() && (soundType.hH() ? killStreakModule.ji() : soundType.hI());
        boolean bl3 = bl = bl2 && killStreakModule.jj();
        if (((Boolean)this.wG.jT()).booleanValue() && this.wO >= 2 && !bl2) {
            this.c(SoundsModule.z(class_3532.method_15340((int)(this.wO - 2), (int)0, (int)4)));
        } else if (((Boolean)this.wF.jT()).booleanValue() && (!bl || this.wO < 2)) {
            this.c(this.wM[this.rnd.nextInt(this.wM.length)]);
        }
    }

    public static SoundManager.SoundEntry z(int n) {
        return switch (n) {
            case 0 -> SoundManager.SoundEntry.qc;
            case 1 -> SoundManager.SoundEntry.qd;
            case 2 -> SoundManager.SoundEntry.qe;
            case 3 -> SoundManager.SoundEntry.qf;
            default -> SoundManager.SoundEntry.qg;
        };
    }

    private KillStreakModule hG() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return null;
        }
        return celebression.getModuleManager().getModule(KillStreakModule.class);
    }

    private boolean a(class_1676 class_16762) {
        class_1657 class_16572;
        class_1297 class_12972 = class_16762.method_24921();
        return class_12972 instanceof class_1657 && (class_16572 = (class_1657)class_12972).method_5667().equals(SoundsModule.aiG.field_1724.method_5667());
    }

    private void c(SoundManager.SoundEntry soundEntry) {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getSoundManager() == null || aiG.method_1483() == null) {
            return;
        }
        SoundManager soundManager = celebression.getSoundManager();
        class_3414 class_34142 = soundManager.b(soundEntry);
        if (class_34142 == null) {
            return;
        }
        float f = Math.max(0.0f, ((Float)this.wE.jT()).floatValue() / 100.0f);
        if (f <= 0.0f) {
            return;
        }
        aiG.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)class_34142, (float)1.0f, (float)f));
    }

    static final class SoundType {
        private final long wS;
        private final boolean wT;
        private final boolean wU;
        private final float wV;

        SoundType(long l, boolean bl, boolean bl2, float f) {
            this.wS = l;
            this.wT = bl;
            this.wU = bl2;
            this.wV = f;
        }

        @Override
        public final String toString() {
            return "SoundType[" + "at=" + this.wS + ", " + "mob=" + this.wT + ", " + "player=" + this.wU + ", " + "lastHealth=" + this.wV + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.wS, this.wT, this.wU, this.wV);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof SoundType other)) return false;
            return java.util.Objects.deepEquals(this.wS, other.wS)
                && java.util.Objects.deepEquals(this.wT, other.wT)
                && java.util.Objects.deepEquals(this.wU, other.wU)
                && java.util.Objects.deepEquals(this.wV, other.wV);
        }

        public long hr() {
            return this.wS;
        }

        public boolean hH() {
            return this.wT;
        }

        public boolean hI() {
            return this.wU;
        }

        public float hJ() {
            return this.wV;
        }
    }
}
