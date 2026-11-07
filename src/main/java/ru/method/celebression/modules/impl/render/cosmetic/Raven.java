package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.class_10017;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1420;
import net.minecraft.class_1452;
import net.minecraft.class_1453;
import net.minecraft.class_1454;
import net.minecraft.class_1621;
import net.minecraft.class_1634;
import net.minecraft.class_1657;
import net.minecraft.class_1921;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_4019;
import net.minecraft.class_4048;
import net.minecraft.class_4050;
import net.minecraft.class_4184;
import net.minecraft.class_4466;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_638;
import net.minecraft.class_7102;
import net.minecraft.class_7106;
import net.minecraft.class_7298;
import net.minecraft.class_746;
import net.minecraft.class_7833;
import net.minecraft.class_7924;
import net.minecraft.class_897;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IFoxEntity;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;
import ru.method.celebression.modules.impl.render.cosmetic.PetGroundSampler;
import ru.method.celebression.modules.impl.render.cosmetic.PetWanderController;

public final class Raven {
    static final class_2960 VORON_TEX = Celebression.id("cosmetic/textures/voron.png");
    static final class_2960 FEATHER_TEX = Celebression.id("cosmetic/textures/pero.png");
    static final class_2960 ITACHI_SOUND_MINECRAFT = Celebression.id("cosmetic/itachi");
    static final class_2960 ITACHI_SOUND_MRE = Celebression.id("cosmetic/itachi");
    static final long ULT_MS = 19000L;
    static final long ULT_ASCEND_MS = 2400L;
    static final long ULT_IN_MS = 2600L;
    static final long ULT_HOLD_MS = 9200L;
    static final long ULT_BREAK_MS = 2800L;
    static final long ULT_DISPERSE_MS = 3000L;
    static final long RETURN_MS = 850L;
    static final int ULT_RAVENS = 42;
    private final CosmeticEffects p;
    private class_1420 bat;
    private class_1453 parrot;
    private class_1453 raven;
    private class_7298 fairy;
    private class_4466 bee;
    private class_1634 vex;
    private class_4019 fox;
    private class_1452 pig;
    private class_7102 frog;
    private class_1454 pufferfish;
    private class_1621 slime;
    private double slimeJumpY = 0.0;
    private double slimeJumpVel = 0.0;
    private boolean slimeJumpAir = false;
    private long slimeLastHopMs = 0L;
    private int lastMode = -1;
    private long lastMs = 0L;
    private long lastBatTickMs = 0L;
    private long lastPetTickMs = 0L;
    private long lastFoxTickMs = 0L;
    private long lastPlayerGroundedMs = 0L;
    private Object lastWorld = null;
    private class_243 smoothPos = class_243.field_1353;
    private class_243 prevSmoothPos = class_243.field_1353;
    private float smoothYaw = 0.0f;
    private float smoothPitch = 0.0f;
    private float moveSp = 0.0f;
    private float wantHoldTime = 0.0f;
    private float wantOrbitTime = 0.0f;
    private boolean holdMode = false;
    private float orbitAng = 0.0f;
    private float orbitBlend = 1.0f;
    private float baseYawDeg = Float.NaN;
    private RenderCompat compat = new RenderCompat();
    private ParrotWingCompat parrotWing = new ParrotWingCompat();
    private BatRoostCompat batRoost = new BatRoostCompat();
    private FoxStateCompat foxState = new FoxStateCompat();
    private FoxLimbCompat foxLimb = new FoxLimbCompat();
    private WalkerLimbCompat walkerLimb = new WalkerLimbCompat();
    private PetWanderController walkerWander = new PetWanderController();
    private PetWanderController foxWander = new PetWanderController();
    private class_243 foxSmoothVel = class_243.field_1353;
    private double foxGroundY = Double.NaN;
    private double foxGroundTargetY = Double.NaN;
    private long foxGroundLastSampleMs = 0L;
    private class_243 foxAnimPrevPos = class_243.field_1353;
    private float foxSpeedSm = 0.0f;
    private class_243 walkerAnimPrevPos = class_243.field_1353;
    private float walkerSpeedSm = 0.0f;
    private PufferStateCompat pufferState = new PufferStateCompat();
    private boolean ultActive = false;
    private long ultStartMs = 0L;
    private long ultLastMs = 0L;
    private class_243 ultStartPos = class_243.field_1353;
    private float ultStartYaw = 0.0f;
    private class_1109 ultSound = null;
    private boolean returnActive = false;
    private long returnStartMs = 0L;
    private class_243 returnFrom = class_243.field_1353;
    private class_243 returnPrev = class_243.field_1353;
    private boolean returnHasFrom = false;
    private List<Illusion> illusions = new ArrayList<Illusion>();
    static long celebression$lastDbgMs = 0L;

    public Raven(CosmeticEffects cosmeticEffects) {
        this.p = cosmeticEffects;
    }

    public void deactivate() {
        this.stopUltSound();
        this.ultActive = false;
        this.ultStartMs = 0L;
        this.ultLastMs = 0L;
        this.ultStartPos = class_243.field_1353;
        this.ultStartYaw = 0.0f;
        this.illusions.clear();
        this.returnActive = false;
        this.returnStartMs = 0L;
        this.returnFrom = class_243.field_1353;
        this.returnPrev = class_243.field_1353;
        this.returnHasFrom = false;
        this.bat = null;
        this.parrot = null;
        this.raven = null;
        this.fairy = null;
        this.bee = null;
        this.vex = null;
        this.fox = null;
        this.pig = null;
        this.frog = null;
        this.pufferfish = null;
        this.slime = null;
        this.lastMode = -1;
        this.lastMs = 0L;
        this.lastBatTickMs = 0L;
        this.lastPetTickMs = 0L;
        this.lastFoxTickMs = 0L;
        this.resetMotion();
        this.slimeJumpY = 0.0;
        this.slimeJumpVel = 0.0;
        this.slimeJumpAir = false;
        this.slimeLastHopMs = 0L;
        this.compat.reset();
        this.parrotWing.reset();
        this.batRoost.reset();
        this.foxState.reset();
        this.foxLimb.reset();
        this.walkerLimb.reset();
        this.pufferState.reset();
    }

    public void triggerUltimate() {
        block10: {
            block9: {
                if (CosmeticEffects.aiG.field_1687 == null) break block9;
                if (CosmeticEffects.aiG.field_1724 != null) break block10;
            }
            return;
        }
        if (!this.p.petIsRaven()) {
            return;
        }
        long l = System.currentTimeMillis();
        if (this.ultActive) {
            return;
        }
        this.ensureRaven((class_1657)CosmeticEffects.aiG.field_1724);
        this.returnActive = false;
        this.returnStartMs = 0L;
        this.returnFrom = class_243.field_1353;
        this.returnPrev = class_243.field_1353;
        this.returnHasFrom = false;
        float f = CosmeticEffects.aiG.method_61966().method_60637(false);
        class_746 class_7462 = CosmeticEffects.aiG.field_1724;
        double d = class_3532.method_16436((double)f, (double)class_7462.field_6014, (double)class_7462.method_23317());
        double d2 = class_3532.method_16436((double)f, (double)class_7462.field_6036, (double)class_7462.method_23318());
        double d3 = class_3532.method_16436((double)f, (double)class_7462.field_5969, (double)class_7462.method_23321());
        class_243 class_2432 = new class_243(d, d2 + (double)class_7462.method_5751() * 0.82, d3);
        if (this.smoothPos == class_243.field_1353) {
            this.smoothPos = class_2432;
            this.prevSmoothPos = class_2432;
        }
        this.ultActive = true;
        this.ultStartMs = l;
        this.ultLastMs = l;
        this.ultStartPos = this.smoothPos;
        this.ultStartYaw = Float.isNaN(this.baseYawDeg) ? class_7462.field_6283 : this.baseYawDeg;
        this.illusions.clear();
        Random random = new Random(l ^ 0x9E3779B97F4A7C15L);
        for (int i = 0; i < 42; ++i) {
            class_1453 class_14532 = this.createParrot();
            Raven.setParrotVariantSafe(class_14532, 0);
            try {
                class_14532.method_5880(false);
                class_14532.method_5665(null);
            }
            catch (Throwable throwable) {
            }
            Illusion illusion = new Illusion();
            illusion.entity = class_14532;
            illusion.seedA = random.nextFloat() * 1000.0f;
            illusion.seedB = random.nextFloat() * 1000.0f;
            illusion.phase = random.nextFloat();
            illusion.spinMul = 0.85f + random.nextFloat() * 1.55f;
            illusion.u = (random.nextFloat() - 0.5f) * 1.0f;
            illusion.v = (random.nextFloat() - 0.5f) * 1.0f;
            illusion.fly = Raven.randomDir(random).method_1021(0.55 + random.nextDouble() * 1.35);
            illusion.pos = class_243.field_1353;
            illusion.prevPos = class_243.field_1353;
            illusion.vel = class_243.field_1353;
            illusion.hasPos = false;
            illusion.burst = false;
            illusion.burstMs = 0L;
            illusion.layer = Raven.frac((float)i * 0.618034f + illusion.seedA * 7.0E-4f);
            illusion.ang0 = (float)((double)i * 0.14959965017094254);
            illusion.lastBodyPoint = class_243.field_1353;
            this.illusions.add(illusion);
        }
        this.playUltSound();
    }

    public void onWorldRender(WorldRenderStages.MatrixStackStage matrixStackStage) {
        float f;
        int n;
        block31: {
            block30: {
                if (CosmeticEffects.aiG.field_1687 == null) break block30;
                if (CosmeticEffects.aiG.field_1724 != null) break block31;
            }
            return;
        }
        if (this.p.pollRavenScene()) {
            this.triggerUltimate();
        }
        if (!this.ultActive && !CosmeticEffects.bool(this.p.petFirstPerson)) {
            if (Raven.isFirstPerson(CosmeticEffects.aiG)) {
                return;
            }
        }
        long l = System.currentTimeMillis();
        float f2 = this.lastMs == 0L ? 0.016f : class_3532.method_15363((float)((float)(l - this.lastMs) / 1000.0f), (float)0.0f, (float)0.06f);
        this.lastMs = l;
        float f3 = matrixStackStage.k().method_60637(true);
        class_746 class_7462 = CosmeticEffects.aiG.field_1724;
        if (this.lastWorld != CosmeticEffects.aiG.field_1687) {
            this.lastWorld = CosmeticEffects.aiG.field_1687;
            this.resetMotion();
        }
        if (this.lastPlayerGroundedMs == 0L) {
            this.lastPlayerGroundedMs = l;
        }
        if (class_7462.method_24828()) {
            this.lastPlayerGroundedMs = l;
        }
        if ((n = this.modeFromSetting()) != this.lastMode) {
            this.lastMode = n;
            this.resetMotion();
            this.lastBatTickMs = 0L;
            this.lastPetTickMs = 0L;
            this.lastFoxTickMs = 0L;
            if (!this.ultActive) {
                this.stopUltSound();
            }
            if (!this.ultActive) {
                this.ultStartMs = 0L;
                this.ultLastMs = 0L;
                this.ultStartPos = class_243.field_1353;
                this.ultStartYaw = 0.0f;
                this.illusions.clear();
                this.returnActive = false;
                this.returnStartMs = 0L;
                this.returnFrom = class_243.field_1353;
                this.returnPrev = class_243.field_1353;
                this.returnHasFrom = false;
            }
            this.foxState.reset();
            this.foxLimb.reset();
            this.walkerLimb.reset();
            this.pufferState.reset();
        }
        double d = class_3532.method_16436((double)f3, (double)class_7462.field_6014, (double)class_7462.method_23317());
        double d2 = class_3532.method_16436((double)f3, (double)class_7462.field_6036, (double)class_7462.method_23318());
        double d3 = class_3532.method_16436((double)f3, (double)class_7462.field_5969, (double)class_7462.method_23321());
        class_243 class_2432 = class_7462.method_18798();
        float f4 = (float)Math.sqrt(class_2432.field_1352 * class_2432.field_1352 + class_2432.field_1350 * class_2432.field_1350);
        float f5 = 1.0f - (float)Math.exp(-f2 / 0.1f);
        this.moveSp += (f4 - this.moveSp) * f5;
        float f6 = class_3532.method_17821((float)f3, (float)class_7462.field_6220, (float)class_7462.field_6283);
        if (Float.isNaN(this.baseYawDeg)) {
            this.baseYawDeg = f6;
        }
        float f7 = this.baseYawDeg;
        if (class_7462.method_5624() || this.moveSp > 0.11f) {
            f7 = f6;
            f = 1.0f - (float)Math.exp(-f2 / 0.14f);
        } else if (this.moveSp > 0.06f) {
            f7 = f6;
            f = 1.0f - (float)Math.exp(-f2 / 0.22f);
        } else {
            f = 0.0f;
        }
        if (f > 0.0f) {
            this.baseYawDeg = Raven.lerpAngle(this.baseYawDeg, f7, f);
        }
        double d4 = Math.toRadians(this.baseYawDeg);
        class_243 class_2433 = new class_243(-Math.sin(d4), 0.0, Math.cos(d4));
        class_243 class_2434 = new class_243(class_2433.field_1350, 0.0, -class_2433.field_1352);
        float f8 = class_3532.method_15363((float)((Float)this.p.petRadius.jT()).floatValue(), (float)0.15f, (float)2.5f);
        float f9 = class_3532.method_15363((float)((Float)this.p.petHeight.jT()).floatValue(), (float)-0.5f, (float)3.0f);
        float f10 = class_3532.method_15363((float)((Float)this.p.petSpeed.jT()).floatValue(), (float)0.05f, (float)5.0f);
        float f11 = class_3532.method_15363((float)((Float)this.p.petScale.jT()).floatValue(), (float)0.15f, (float)2.0f);
        class_4597.class_4598 class_45982 = CosmeticEffects.aiG.method_22940().method_23000();
        int n2 = 0xF000F0;
        if (n == 2 && this.returnActive && !this.ultActive) {
            this.ensureRaven((class_1657)class_7462);
            this.renderReturn(matrixStackStage, class_45982, n2, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, f11, l);
            class_45982.method_22993();
            return;
        }
        if (n == 2 && this.ultActive) {
            this.ensureRaven((class_1657)class_7462);
            this.renderUltimate(matrixStackStage, class_45982, n2, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, f8, f9, f10, f11, l);
            return;
        }
        if (n == 0) {
            this.ensureBat((class_1657)class_7462);
            this.renderBatWithParrotLogic(matrixStackStage, class_45982, n2, this.bat, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11, l);
            class_45982.method_22993();
            return;
        }
        if (n == 2) {
            this.ensureRaven((class_1657)class_7462);
            this.renderParrotLike(matrixStackStage, class_45982, n2, this.raven, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11, l, true);
            class_45982.method_22993();
            return;
        }
        if (n == 1) {
            this.ensureParrot((class_1657)class_7462);
            this.renderParrotLike(matrixStackStage, class_45982, n2, this.parrot, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11, l, false);
            class_45982.method_22993();
            return;
        }
        if (n == 3) {
            this.ensureFairy((class_1657)class_7462);
            this.renderGenericFlyer(matrixStackStage, class_45982, n2, (class_1297)this.fairy, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.92f, l, 0.9f, 0.55f);
            class_45982.method_22993();
            return;
        }
        if (n == 4) {
            this.ensureBee((class_1657)class_7462);
            this.renderGenericFlyer(matrixStackStage, class_45982, n2, (class_1297)this.bee, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.95f, l, 1.1f, 0.7f);
            class_45982.method_22993();
            return;
        }
        if (n == 5) {
            this.ensureVex((class_1657)class_7462);
            this.renderGenericFlyer(matrixStackStage, class_45982, n2, (class_1297)this.vex, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.98f, l, 1.25f, 0.75f);
            class_45982.method_22993();
            return;
        }
        if (n == 7) {
            this.ensurePig((class_1657)class_7462);
            this.renderGroundWalker(matrixStackStage, class_45982, n2, (class_1309)this.pig, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.98f, l);
            class_45982.method_22993();
            return;
        }
        if (n == 8) {
            this.ensureFrog((class_1657)class_7462);
            this.renderGroundWalker(matrixStackStage, class_45982, n2, (class_1309)this.frog, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.98f, l);
            class_45982.method_22993();
            return;
        }
        if (n == 9) {
            this.ensurePufferfish((class_1657)class_7462);
            this.renderPufferfishOrbit(matrixStackStage, class_45982, n2, this.pufferfish, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.92f, l);
            class_45982.method_22993();
            return;
        }
        if (n == 10) {
            this.ensureSlime((class_1657)class_7462);
            this.renderGroundWalker(matrixStackStage, class_45982, n2, (class_1309)this.slime, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.72f, l);
            class_45982.method_22993();
            return;
        }
        this.ensureFox((class_1657)class_7462);
        this.renderFoxAI(matrixStackStage, class_45982, n2, this.fox, (class_1657)class_7462, f3, f2, d, d2, d3, class_2433, class_2434, this.baseYawDeg, f8, f9, f10, f11 * 0.92f, l);
        class_45982.method_22993();
    }

    private void beginReturn(class_243 class_2432, class_243 class_2433, long l) {
        this.returnActive = true;
        this.returnStartMs = l;
        this.returnFrom = class_2432;
        this.returnPrev = class_2433;
        this.returnHasFrom = true;
    }

    private void renderReturn(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, long l) {
        if (this.raven == null) {
            return;
        }
        if (!this.returnHasFrom) {
            this.returnActive = false;
            this.returnStartMs = 0L;
            this.returnFrom = class_243.field_1353;
            this.returnPrev = class_243.field_1353;
            this.returnHasFrom = false;
            return;
        }
        double d4 = (double)l / 1000.0;
        class_243 class_2434 = this.computeLeftShoulder(d, d2, d3, class_16572, class_2432, class_2433, d4);
        float f4 = (float)class_3532.method_15350((double)((float)(l - this.returnStartMs) / 850.0f), (double)0.0, (double)1.0);
        float f5 = Raven.easeInOut(f4);
        class_243 class_2435 = Raven.lerpVec(this.returnFrom, class_2434, f5);
        float f6 = (float)Math.sin((double)f5 * Math.PI) * 0.14f;
        class_2435 = new class_243(class_2435.field_1352, class_2435.field_1351 + (double)f6, class_2435.field_1350);
        class_243 class_2436 = class_2435.method_1020(this.returnPrev);
        if (class_2436.method_1027() < 1.0E-8) {
            class_2436 = class_2434.method_1020(class_2435);
        }
        if (class_2436.method_1027() < 1.0E-8) {
            class_2436 = new class_243(0.0, 0.0, 1.0);
        }
        float f7 = Raven.yawFromDir(class_2436);
        float f8 = Raven.pitchFromDir(class_2436);
        this.animateParrotWingsConstant(this.raven, d4, 2.35f);
        this.setEntityPoseAngles((class_1297)this.raven, f7, f8, class_16572.field_6012);
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)this.raven);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(class_2435.field_1352, class_2435.field_1351, class_2435.field_1350);
        class_45872.method_22905(f3 * 1.28f, f3 * 1.28f, f3 * 1.28f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        TextureSwapVcp textureSwapVcp = new TextureSwapVcp((class_4597)class_45982, VORON_TEX);
        this.renderCompat(class_8972, (class_1297)this.raven, f7, f, class_45872, textureSwapVcp, n);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
        class_45872.method_22909();
        this.returnPrev = class_2435;
        if (f4 >= 0.9995f) {
            this.returnActive = false;
            this.returnStartMs = 0L;
            this.returnFrom = class_243.field_1353;
            this.returnPrev = class_243.field_1353;
            this.returnHasFrom = false;
            this.smoothPos = class_2434;
            this.prevSmoothPos = class_2434;
            this.smoothYaw = f7;
            this.smoothPitch = f8;
        }
    }

    private void renderUltimate(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, long l) {
        long l2 = l - this.ultStartMs;
        if (l2 >= 19000L) {
            class_243 class_2434;
            this.stopUltSound();
            this.ultActive = false;
            float f7 = class_16572.method_17682();
            float f8 = (float)(d2 + (double)f7) + 0.1f;
            class_243 class_2435 = class_2434 = new class_243(d, (double)f8 + 1.65, d3).method_1019(class_2432.method_1021(0.35));
            class_243 class_2436 = class_2434.method_1020(new class_243(0.0, 0.0, 0.05));
            this.beginReturn(class_2435, class_2436, l);
            this.ultStartMs = 0L;
            this.ultLastMs = 0L;
            this.ultStartPos = class_243.field_1353;
            this.ultStartYaw = 0.0f;
            this.illusions.clear();
            return;
        }
        float f9 = this.ultLastMs == 0L ? f2 : class_3532.method_15363((float)((float)(l - this.ultLastMs) / 1000.0f), (float)0.0f, (float)0.06f);
        this.ultLastMs = l;
        double d4 = (double)l / 1000.0;
        class_4184 class_41842 = CosmeticEffects.aiG.field_1773.method_19418();
        class_243 class_2437 = class_41842.method_19326();
        float f10 = class_16572.method_17682();
        float f11 = (float)d2 - 0.02f;
        float f12 = (float)(d2 + (double)f10) + 0.1f;
        class_243 class_2438 = new class_243(d, d2 + (double)f10 * 0.52, d3);
        float f13 = class_3532.method_15363((float)((float)l2 / 2400.0f), (float)0.0f, (float)1.0f);
        float f14 = class_3532.method_15363((float)((float)(l2 - 2400L) / 2600.0f), (float)0.0f, (float)1.0f);
        float f15 = class_3532.method_15363((float)((float)(l2 - 5000L) / 9200.0f), (float)0.0f, (float)1.0f);
        float f16 = class_3532.method_15363((float)((float)(l2 - 14200L) / 2800.0f), (float)0.0f, (float)1.0f);
        float f17 = class_3532.method_15363((float)((float)(l2 - 17000L) / 3000.0f), (float)0.0f, (float)1.0f);
        float f18 = Raven.easeInOut(f13);
        float f19 = Raven.easeInOut(f14);
        float f20 = Raven.easeInOut(f15);
        float f21 = Raven.easeInOut(f16);
        float f22 = Raven.easeInOut(f17);
        float f23 = class_3532.method_15363((float)(f19 * 1.1f + f20), (float)0.0f, (float)1.0f);
        float f24 = class_3532.method_15363((float)(f21 * 0.95f + f22), (float)0.0f, (float)1.0f);
        float f25 = 0.1f;
        float f26 = f11 + f25;
        float f27 = f12 - f25;
        float f28 = 0.34f + 0.18f * f23;
        float f29 = class_3532.method_15363((float)(1.05f + f3 * 0.55f), (float)1.05f, (float)2.4f);
        float f30 = class_3532.method_15363((float)(0.24f + f3 * 0.12f), (float)0.24f, (float)0.62f);
        float f31 = Raven.lerp(f29, f30, Raven.easeInOut(f23));
        float f32 = class_3532.method_15363((float)(f23 * (1.0f - f24)), (float)0.0f, (float)1.0f);
        f32 *= f32;
        int n2 = ((Color)this.p.ravenColor.jT()).getRGB();
        float f33 = class_3532.method_15363((float)(((Float)this.p.ravenGlow.jT()).floatValue() / 100.0f * 0.85f), (float)0.0f, (float)1.0f);
        float f34 = class_3532.method_15363((float)(((Float)this.p.ravenAlpha.jT()).floatValue() / 255.0f * (0.85f + 0.25f * f23) * (1.0f - 0.8f * f24)), (float)0.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.depthMask((boolean)true);
        TextureSwapVcp textureSwapVcp = new TextureSwapVcp((class_4597)class_45982, VORON_TEX);
        this.renderMainAscendRaven(class_45872, textureSwapVcp, n, class_16572, f, class_2432, class_2433, d, d2, d3, f12, f18, f23, f24, (float)d4, f6, l2);
        long l3 = 520L;
        float f35 = 14.0f;
        TextureSwapVcp textureSwapVcp2 = new TextureSwapVcp((class_4597)class_45982, VORON_TEX);
        for (int i = 0; i < this.illusions.size(); ++i) {
            class_243 class_2439;
            float f36;
            Illusion illusion = this.illusions.get(i);
            float f37 = Raven.easeInOut(class_3532.method_15363((float)((float)(l2 - l3 - (long)((float)i * f35)) / 860.0f), (float)0.0f, (float)1.0f));
            float f38 = f37 * (f36 = 1.0f - Raven.easeInOut(class_3532.method_15363((float)(f22 * 1.12f + (float)i * 0.0085f), (float)0.0f, (float)1.0f))) * f34;
            if (f38 <= 0.0035f) continue;
            float f39 = (float)Math.sin((double)illusion.layer * Math.PI);
            f39 = 0.66f + 0.62f * f39;
            float f40 = Raven.lerp(f31, f28, Raven.easeInOut(f23));
            float f41 = f40 * f39;
            double d5 = (double)illusion.ang0 + d4 * (2.35 + (double)f5 * 0.85) * (double)illusion.spinMul + Math.sin(d4 * 0.95 + (double)illusion.seedA * 0.01) * 0.24;
            float f42 = Raven.lerp(f26, f27, illusion.layer);
            float f43 = (float)(Math.sin(d4 * 3.35 + (double)illusion.seedB * 0.01) * (0.08 + 0.12 * (double)(1.0f - f24)));
            float f44 = (float)((0.04 + 0.085 * (double)(1.0f - f23)) * (double)(1.0f - 0.55f * f24));
            float f45 = (float)(Math.sin(d4 * 6.4 + (double)illusion.seedA) * (double)f44);
            float f46 = (float)(Math.cos(d4 * 6.1 + (double)illusion.seedB) * (double)f44);
            class_243 class_24310 = new class_243(class_2438.field_1352 + Math.cos(d5) * (double)f28, (double)f42, class_2438.field_1350 + Math.sin(d5) * (double)f28);
            class_243 class_24311 = new class_243(class_2438.field_1352 + Math.cos(d5) * (double)f41 + (double)f45, (double)(f42 + f43), class_2438.field_1350 + Math.sin(d5) * (double)f41 + (double)f46);
            illusion.lastBodyPoint = class_24310;
            if (f24 > 0.0f && !illusion.burst) {
                class_243 class_24312;
                illusion.burst = true;
                illusion.burstMs = l;
                class_2439 = class_24311.method_1020(class_2438);
                double d6 = class_2439.method_1033();
                class_2439 = d6 > 1.0E-6 ? class_2439.method_1021(1.0 / d6) : new class_243(0.0, 1.0, 0.0);
                illusion.vel = class_24312 = class_2439.method_1021(2.8 + 7.2 * (double)f24).method_1019(illusion.fly.method_1021(2.4 + 6.4 * (double)f24)).method_1031(0.0, 0.65 + 1.05 * (double)f24, 0.0);
                illusion.prevPos = illusion.pos = class_24311;
                illusion.hasPos = true;
            }
            if (!illusion.hasPos) {
                illusion.pos = class_24311;
                illusion.prevPos = class_24311;
                illusion.vel = class_243.field_1353;
                illusion.hasPos = true;
            }
            illusion.prevPos = illusion.pos;
            if (!illusion.burst) {
                class_2439 = class_24311.method_1020(illusion.pos);
                class_243 class_24313 = class_2439.method_1021(0.58 + 0.34 * (double)f23);
                illusion.vel = illusion.vel.method_1019(class_24313.method_1021((double)f9 * 18.0));
                illusion.vel = illusion.vel.method_1021(0.84 + 0.1 * (double)f23);
                illusion.pos = illusion.pos.method_1019(illusion.vel.method_1021((double)f9 * 18.0));
            } else {
                illusion.vel = illusion.vel.method_1021(0.986);
                illusion.vel = illusion.vel.method_1031(0.0, -0.012 * (double)f9 * 60.0, 0.0);
                illusion.vel = illusion.vel.method_1031(Math.sin(d4 * 1.35 + (double)illusion.seedA * 0.02) * 0.01 * (double)f9 * 60.0, Math.sin(d4 * 1.55 + (double)illusion.seedB * 0.02) * 0.006 * (double)f9 * 60.0, Math.cos(d4 * 1.3 + (double)illusion.seedB * 0.02) * 0.01 * (double)f9 * 60.0);
                illusion.pos = illusion.pos.method_1019(illusion.vel.method_1021((double)f9 * (19.5 + 10.5 * (double)f24)));
            }
            class_2439 = illusion.pos.method_1020(illusion.prevPos);
            if (class_2439.method_1027() < 1.0E-8) {
                class_2439 = class_24311.method_1020(class_2438);
            }
            float f47 = Raven.yawFromDir(class_2439);
            float f48 = Raven.pitchFromDir(class_2439);
            this.animateParrotWingsConstant(illusion.entity, d4 + (double)illusion.seedA * 0.004, 1.25f + 0.65f * f23);
            class_45872.method_22903();
            class_45872.method_22904(illusion.pos.field_1352, illusion.pos.field_1351, illusion.pos.field_1350);
            class_45872.method_22905(f6 * 1.22f, f6 * 1.22f, f6 * 1.22f);
            this.setEntityPoseAngles((class_1297)illusion.entity, f47, f48, class_16572.field_6012);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)f38);
            this.renderCompat(CosmeticEffects.aiG.method_1561().method_3953((class_1297)illusion.entity), (class_1297)illusion.entity, f47, f, class_45872, textureSwapVcp2, n);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            class_45872.method_22909();
        }
        class_45982.method_22993();
        if (f32 > 0.01f && f24 < 0.985f && f23 > 0.18f && l2 > 2620L) {
            this.renderBirdFog(class_45872, class_41842, class_2437, (float)d4, f23, f24, f32, n2, f33, class_2438, f26, f27, f28);
        }
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private void renderMainAscendRaven(class_4587 class_45872, class_4597 class_45972, int n, class_1657 class_16572, float f, class_243 class_2432, class_243 class_2433, double d, double d2, double d3, float f2, float f3, float f4, float f5, float f6, float f7, long l) {
        if (this.raven == null) {
            return;
        }
        class_243 class_2434 = this.computeLeftShoulder(d, d2, d3, class_16572, class_2432, class_2433, f6);
        class_243 class_2435 = new class_243(d, (double)f2 + 1.65, d3).method_1019(class_2432.method_1021(0.35));
        class_243 class_2436 = Raven.lerpVec(class_2434, class_2435, f3);
        float f8 = class_3532.method_15363((float)((float)(l - 2400L) / 220.0f), (float)0.0f, (float)1.0f);
        float f9 = Raven.easeInOut(f8) * (1.0f - class_3532.method_15363((float)(f5 * 1.1f), (float)0.0f, (float)1.0f));
        float f10 = 0.06f + 0.05f * f4;
        float f11 = (float)(Math.cos(f6 * 2.2f) * (double)(0.12f + 0.1f * f9));
        float f12 = (float)(Math.sin(f6 * 2.2f) * (double)(0.12f + 0.1f * f9));
        float f13 = (float)(Math.sin(f6 * 3.4f) * (double)(f10 + 0.03f * f9));
        class_2436 = new class_243(class_2436.field_1352 + (double)f11, class_2436.field_1351 + (double)f13, class_2436.field_1350 + (double)f12);
        class_243 class_2437 = new class_243(-Math.sin(f6 * 2.2f), 0.0, Math.cos(f6 * 2.2f));
        float f14 = Raven.yawFromDir(class_2437);
        float f15 = -8.0f + (float)Math.sin(f6 * 2.8f) * 2.8f;
        float f16 = class_3532.method_15363((float)(f4 * (1.0f - 0.7f * f5)), (float)0.0f, (float)1.0f);
        this.animateParrotWingsConstant(this.raven, f6, 1.75f + 0.85f * f16);
        this.setEntityPoseAngles((class_1297)this.raven, f14, f15, class_16572.field_6012);
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)this.raven);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        float f17 = 1.0f - class_3532.method_15363((float)(f5 * 1.1f), (float)0.0f, (float)1.0f);
        if (f17 <= 0.01f) {
            return;
        }
        class_45872.method_22903();
        class_45872.method_22904(class_2436.field_1352, class_2436.field_1351, class_2436.field_1350);
        class_45872.method_22905(f7 * 1.35f, f7 * 1.35f, f7 * 1.35f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)f17);
        this.renderCompat(class_8972, (class_1297)this.raven, f14, f, class_45872, class_45972, n);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_45872.method_22909();
    }

    private void renderBirdFog(class_4587 class_45872, class_4184 class_41842, class_243 class_2432, float f, float f2, float f3, float f4, int n, float f5, class_243 class_2433, float f6, float f7, float f8) {
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        class_243 class_2434;
        if (f4 <= 0.0035f) {
            return;
        }
        RenderSystem.setShader((class_10156)class_10142.field_53880);
        RenderSystem.setShaderTexture((int)0, (class_2960)FEATHER_TEX);
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        float f14 = 1.05f + 1.55f * f2;
        float f15 = class_3532.method_15363((float)(f4 * f14), (float)0.0f, (float)1.0f);
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        int n2 = 7;
        float f16 = 0.1f + 0.18f * f2;
        for (int i = 0; i < this.illusions.size(); ++i) {
            class_243 class_2435;
            Illusion illusion = this.illusions.get(i);
            if (!illusion.hasPos || (class_2435 = illusion.pos) == null || class_2435 == class_243.field_1353) continue;
            class_2434 = illusion.lastBodyPoint == null ? class_2433 : illusion.lastBodyPoint;
            f13 = 0.72f + 0.28f * (float)Math.sin(f * 1.9f + illusion.seedA * 0.01f);
            float f17 = class_3532.method_15363((float)(f15 * f13), (float)0.0f, (float)1.0f);
            if (f17 <= 0.002f) continue;
            for (int j = 0; j < n2; ++j) {
                float f18 = n2 <= 1 ? 0.0f : (float)j / (float)(n2 - 1);
                f12 = 1.0f - f18;
                f12 *= f12;
                f11 = (float)Math.sin(f * (3.4f + (float)j * 0.35f) + illusion.seedA + (float)j * 1.7f) * f16;
                f10 = (float)Math.sin(f * (2.7f + (float)j * 0.28f) + illusion.seedB + (float)j * 2.3f) * (f16 * 0.55f);
                f9 = (float)Math.cos(f * (3.2f + (float)j * 0.31f) + illusion.seedB + (float)j * 1.1f) * f16;
                class_243 class_2436 = Raven.lerpVec(class_2435, class_2434, class_3532.method_15363((float)(0.08f + 0.86f * f18), (float)0.0f, (float)1.0f));
                class_2436 = new class_243(class_2436.field_1352 + (double)f11, class_2436.field_1351 + (double)f10, class_2436.field_1350 + (double)f9);
                float f19 = (0.7f + 1.25f * f2) * (1.12f - 0.1f * f18);
                float f20 = 0.9f + 0.2f * (float)Math.sin(f * 2.1f + illusion.seedB * 0.01f + (float)j * 0.9f);
                float f21 = f19 * f20;
                f21 = Math.min(f21, 2.1f + 1.05f * f2);
                float f22 = class_3532.method_15363((float)(f17 * (0.72f * f12)), (float)0.0f, (float)1.0f);
                float f23 = f * (85.0f - 35.0f * f18) + (illusion.seedA + illusion.seedB) * 0.18f + (float)j * 11.0f;
                this.pushBillboard(class_2872, class_45872, class_41842, class_2436.field_1352, class_2436.field_1351, class_2436.field_1350, f21, f23, 0.0f, 0.0f, 0.0f, f22);
            }
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        if (f5 > 0.002f && f4 > 0.01f && f3 < 0.985f) {
            RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
            float f24 = (float)(n >>> 16 & 0xFF) / 255.0f;
            float f25 = (float)(n >>> 8 & 0xFF) / 255.0f;
            float f26 = (float)(n & 0xFF) / 255.0f;
            class_287 glowBuffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
            f13 = class_3532.method_15363((float)(f5 * f4 * (1.1f + 0.85f * f2)), (float)0.0f, (float)1.0f);
            for (int i = 0; i < this.illusions.size(); ++i) {
                class_243 class_2437;
                Illusion illusion = this.illusions.get(i);
                if (!illusion.hasPos || (class_2437 = illusion.pos) == null || class_2437 == class_243.field_1353 || (f11 = class_3532.method_15363((float)(f13 * (f12 = 0.6f + 0.4f * (float)Math.sin(f * 2.05f + illusion.seedA * 0.01f))), (float)0.0f, (float)1.0f)) <= 0.002f) continue;
                f10 = (0.72f + 1.0f * f2) * (0.9f + 0.18f * (float)Math.sin(f * 1.25f + illusion.seedB * 0.01f));
                f10 = Math.min(f10, 1.85f + 0.95f * f2);
                f9 = f * 80.0f + illusion.seedB * 0.2f;
                this.pushBillboard(glowBuffer, class_45872, class_41842, class_2437.field_1352, class_2437.field_1351, class_2437.field_1350, f10, f9, f24, f25, f26, f11);
            }
            class_286.method_43433(glowBuffer.method_60800());
        }
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private void pushBillboard(class_287 class_2872, class_4587 class_45872, class_4184 class_41842, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f6 <= 0.001f || f <= 0.001f) {
            return;
        }
        class_45872.method_22903();
        class_45872.method_22904(d, d2, d3);
        class_45872.method_22907(class_41842.method_23767());
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(f2));
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        float f7 = f * 0.5f;
        class_2872.method_22918(matrix4f, -f7, f7, 0.0f).method_22913(0.0f, 1.0f).method_22915(f3, f4, f5, f6);
        class_2872.method_22918(matrix4f, f7, f7, 0.0f).method_22913(1.0f, 1.0f).method_22915(f3, f4, f5, f6);
        class_2872.method_22918(matrix4f, f7, -f7, 0.0f).method_22913(1.0f, 0.0f).method_22915(f3, f4, f5, f6);
        class_2872.method_22918(matrix4f, -f7, -f7, 0.0f).method_22913(0.0f, 0.0f).method_22915(f3, f4, f5, f6);
        class_45872.method_22909();
    }

    private void playUltSound() {
        class_1109 class_11092;
        block10: {
            block9: {
                this.stopUltSound();
                if (CosmeticEffects.aiG.field_1687 == null) break block9;
                if (CosmeticEffects.aiG.field_1724 != null) break block10;
            }
            return;
        }
        class_1109 class_11093 = this.tryCreateMaster(ITACHI_SOUND_MINECRAFT);
        if (class_11093 != null) {
            this.ultSound = class_11093;
            try {
                CosmeticEffects.aiG.method_1483().method_4873((class_1113)this.ultSound);
                return;
            }
            catch (Throwable throwable) {
                this.ultSound = null;
            }
        }
        if ((class_11092 = this.tryCreateMaster(ITACHI_SOUND_MRE)) != null) {
            this.ultSound = class_11092;
            try {
                CosmeticEffects.aiG.method_1483().method_4873((class_1113)this.ultSound);
            }
            catch (Throwable throwable) {
                this.ultSound = null;
            }
        }
    }

    private class_1109 tryCreateMaster(class_2960 class_29602) {
        try {
            class_3414 class_34142 = class_3414.method_47908((class_2960)class_29602);
            return class_1109.method_4758((class_3414)class_34142, (float)1.0f);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private void stopUltSound() {
        if (this.ultSound == null) {
            return;
        }
        try {
            CosmeticEffects.aiG.method_1483().method_4870((class_1113)this.ultSound);
        }
        catch (Throwable throwable) {
        }
        this.ultSound = null;
    }

    private static class_243 randomDir(Random random) {
        double d;
        double d2;
        double d3 = (random.nextDouble() - 0.5) * 2.0;
        class_243 class_2432 = new class_243(d3, d2 = (random.nextDouble() - 0.5) * 2.0, d = (random.nextDouble() - 0.5) * 2.0);
        double d4 = class_2432.method_1033();
        if (d4 < 1.0E-6) {
            return new class_243(0.0, 1.0, 0.0);
        }
        return class_2432.method_1021(1.0 / d4);
    }

    private void setEntityPoseAngles(class_1297 class_12972, float f, float f2, int n) {
        class_12972.field_6014 = class_12972.method_23317();
        class_12972.field_6036 = class_12972.method_23318();
        class_12972.field_5969 = class_12972.method_23321();
        class_12972.field_5982 = class_12972.method_36454();
        class_12972.method_36456(f);
        if (class_12972 instanceof class_1453) {
            class_1453 class_14532 = (class_1453)class_12972;
            class_14532.field_6283 = f;
            class_14532.field_6241 = f;
            class_14532.field_6220 = f;
            class_14532.field_6259 = f;
            class_14532.field_6004 = class_14532.method_36455();
            class_14532.method_36457(f2);
            class_14532.method_24830(false);
            class_14532.method_18380(class_4050.field_18076);
            class_14532.field_6012 = n;
            return;
        }
        if (class_12972 instanceof class_1420) {
            class_1420 class_14202 = (class_1420)class_12972;
            class_14202.field_6283 = f;
            class_14202.field_6241 = f;
            class_14202.field_6220 = f;
            class_14202.field_6259 = f;
            class_14202.field_6004 = class_14202.method_36455();
            class_14202.method_36457(f2);
            class_14202.method_24830(false);
            class_14202.method_18380(class_4050.field_18076);
            class_14202.field_6012 = n;
            return;
        }
        if (class_12972 instanceof class_1309) {
            class_1309 class_13092 = (class_1309)class_12972;
            try {
                class_13092.field_6283 = f;
                class_13092.field_6241 = f;
                class_13092.field_6220 = f;
                class_13092.field_6259 = f;
            }
            catch (Throwable throwable) {
            }
            class_13092.field_6004 = class_13092.method_36455();
            class_13092.method_36457(f2);
            class_13092.method_18380(class_4050.field_18076);
            class_13092.field_6012 = n;
            try {
                class_13092.method_24830(false);
            }
            catch (Throwable throwable) {
            }
        }
    }

    private void setEntityPoseAngles(class_1297 class_12972, float f, float f2, int n, boolean bl, class_4050 class_40502) {
        class_12972.field_6014 = class_12972.method_23317();
        class_12972.field_6036 = class_12972.method_23318();
        class_12972.field_5969 = class_12972.method_23321();
        class_12972.field_5982 = class_12972.method_36454();
        class_12972.method_36456(f);
        if (class_12972 instanceof class_1309) {
            class_1309 class_13092 = (class_1309)class_12972;
            try {
                class_13092.field_6283 = f;
                class_13092.field_6241 = f;
                class_13092.field_6220 = f;
                class_13092.field_6259 = f;
            }
            catch (Throwable throwable) {
            }
            class_13092.field_6004 = class_13092.method_36455();
            class_13092.method_36457(f2);
            class_13092.method_18380(class_40502);
            class_13092.field_6012 = n;
            try {
                class_13092.method_24830(bl);
            }
            catch (Throwable throwable) {}
        } else {
            class_12972.method_18380(class_40502);
        }
    }

    private int modeFromSetting() {
        if (this.p.petIsBat()) {
            return 0;
        }
        if (this.p.petIsParrot()) {
            return 1;
        }
        if (this.p.petIsRaven()) {
            return 2;
        }
        if (this.p.petIsFairy()) {
            return 3;
        }
        if (this.p.petIsBee()) {
            return 4;
        }
        if (this.p.petIsVex()) {
            return 5;
        }
        if (this.p.petIsFox()) {
            return 6;
        }
        if (this.p.petIsPig()) {
            return 7;
        }
        if (this.p.petIsFrog()) {
            return 8;
        }
        if (this.p.petIsPufferfish()) {
            return 9;
        }
        if (this.p.petIsSlime()) {
            return 10;
        }
        return 1;
    }

    public class_1309 getPreviewPetEntity() {
        class_746 class_7462 = CosmeticEffects.aiG.field_1724;
        if (class_7462 == null) {
            return null;
        }
        return this.getPreviewPetEntity((CosmeticEnums.PetSkin)this.p.petSkin.jT());
    }

    public class_1309 getPreviewPetEntity(CosmeticEnums.PetSkin petSkin) {
        class_746 class_7462 = CosmeticEffects.aiG.field_1724;
        if (class_7462 == null || petSkin == null) {
            return null;
        }
        switch (petSkin) {
            case BAT: {
                this.ensureBat((class_1657)class_7462);
                return this.bat;
            }
            case PARROT: {
                this.ensureParrot((class_1657)class_7462);
                return this.parrot;
            }
            case RAVEN: {
                this.ensureRaven((class_1657)class_7462);
                return this.raven;
            }
            case FAIRY: {
                this.ensureFairy((class_1657)class_7462);
                return this.fairy;
            }
            case BEE: {
                this.ensureBee((class_1657)class_7462);
                return this.bee;
            }
            case VEX: {
                this.ensureVex((class_1657)class_7462);
                return this.vex;
            }
            case FOX: {
                this.ensureFox((class_1657)class_7462);
                return this.fox;
            }
            case PIG: {
                this.ensurePig((class_1657)class_7462);
                return this.pig;
            }
            case FROG: {
                this.ensureFrog((class_1657)class_7462);
                return this.frog;
            }
            case PUFFERFISH: {
                this.ensurePufferfish((class_1657)class_7462);
                return this.pufferfish;
            }
            case SLIME: {
                this.ensureSlime((class_1657)class_7462);
                return this.slime;
            }
        }
        return null;
    }

    private void resetMotion() {
        this.smoothPos = class_243.field_1353;
        this.prevSmoothPos = class_243.field_1353;
        this.smoothYaw = 0.0f;
        this.smoothPitch = 0.0f;
        this.moveSp = 0.0f;
        this.wantHoldTime = 0.0f;
        this.wantOrbitTime = 0.0f;
        this.holdMode = false;
        this.orbitAng = 0.0f;
        this.orbitBlend = 1.0f;
        this.baseYawDeg = Float.NaN;
        this.foxSmoothVel = class_243.field_1353;
        this.foxGroundY = Double.NaN;
        this.foxGroundTargetY = Double.NaN;
        this.foxGroundLastSampleMs = 0L;
        this.foxAnimPrevPos = class_243.field_1353;
        this.foxSpeedSm = 0.0f;
        this.walkerAnimPrevPos = class_243.field_1353;
        this.walkerSpeedSm = 0.0f;
        this.walkerWander.reset();
        this.foxWander.reset();
    }

    private void ensureBat(class_1657 class_16572) {
        block3: {
            block2: {
                if (this.bat == null) break block2;
                if (this.bat.method_37908() == CosmeticEffects.aiG.field_1687) break block3;
            }
            this.bat = this.createBat();
            this.lastBatTickMs = 0L;
        }
        this.bat.field_6012 = class_16572.field_6012;
    }

    private void ensureParrot(class_1657 class_16572) {
        block3: {
            block2: {
                if (this.parrot == null) break block2;
                if (this.parrot.method_37908() == CosmeticEffects.aiG.field_1687) break block3;
            }
            this.parrot = this.createParrot();
            Raven.setParrotVariantSafe(this.parrot, 0);
        }
        this.parrot.field_6012 = class_16572.field_6012;
    }

    private void ensureRaven(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.raven == null) break block4;
                if (this.raven.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.raven = this.createParrot();
            Raven.setParrotVariantSafe(this.raven, 0);
            try {
                this.raven.method_5880(false);
                this.raven.method_5665(null);
            }
            catch (Throwable throwable) {
            }
        }
        this.raven.field_6012 = class_16572.field_6012;
    }

    private void ensureFairy(class_1657 class_16572) {
        block3: {
            block2: {
                if (this.fairy == null) break block2;
                if (this.fairy.method_37908() == CosmeticEffects.aiG.field_1687) break block3;
            }
            this.fairy = this.createAllay();
        }
        this.fairy.field_6012 = class_16572.field_6012;
    }

    private void ensureBee(class_1657 class_16572) {
        block3: {
            block2: {
                if (this.bee == null) break block2;
                if (this.bee.method_37908() == CosmeticEffects.aiG.field_1687) break block3;
            }
            this.bee = this.createBee();
        }
        this.bee.field_6012 = class_16572.field_6012;
    }

    private void ensureVex(class_1657 class_16572) {
        block3: {
            block2: {
                if (this.vex == null) break block2;
                if (this.vex.method_37908() == CosmeticEffects.aiG.field_1687) break block3;
            }
            this.vex = this.createVex();
        }
        this.vex.field_6012 = class_16572.field_6012;
    }

    private void ensureFox(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.fox == null) break block4;
                if (this.fox.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.fox = this.createFox();
            this.foxState.reset();
            this.foxLimb.reset();
            this.foxSmoothVel = class_243.field_1353;
            this.foxGroundY = Double.NaN;
            this.foxGroundTargetY = Double.NaN;
            this.foxGroundLastSampleMs = 0L;
            this.foxAnimPrevPos = class_243.field_1353;
            this.foxSpeedSm = 0.0f;
            Raven.setFoxSnowVariantSafe(this.fox);
        }
        this.fox.field_6012 = class_16572.field_6012;
        try {
            this.fox.method_5880(false);
            this.fox.method_5665(null);
        }
        catch (Throwable throwable) {
        }
    }

    private void ensurePig(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.pig == null) break block4;
                if (this.pig.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.pig = this.createPig();
            this.walkerAnimPrevPos = class_243.field_1353;
            this.walkerSpeedSm = 0.0f;
        }
        this.pig.field_6012 = class_16572.field_6012;
        try {
            this.pig.method_5880(false);
            this.pig.method_5665(null);
        }
        catch (Throwable throwable) {
        }
    }

    private void ensureFrog(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.frog == null) break block4;
                if (this.frog.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.frog = this.createFrog();
            this.walkerAnimPrevPos = class_243.field_1353;
            this.walkerSpeedSm = 0.0f;
            Raven.setFrogColdVariantSafe(this.frog);
        }
        this.frog.field_6012 = class_16572.field_6012;
        try {
            this.frog.method_5880(false);
            this.frog.method_5665(null);
        }
        catch (Throwable throwable) {
        }
    }

    private void ensurePufferfish(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.pufferfish == null) break block4;
                if (this.pufferfish.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.pufferfish = this.createPufferfish();
            this.pufferState.reset();
        }
        this.pufferfish.field_6012 = class_16572.field_6012;
        try {
            this.pufferfish.method_5880(false);
            this.pufferfish.method_5665(null);
        }
        catch (Throwable throwable) {
        }
    }

    private void ensureSlime(class_1657 class_16572) {
        block5: {
            block4: {
                if (this.slime == null) break block4;
                if (this.slime.method_37908() == CosmeticEffects.aiG.field_1687) break block5;
            }
            this.slimeJumpY = 0.0;
            this.slimeJumpVel = 0.0;
            this.slimeJumpAir = false;
            this.slimeLastHopMs = 0L;
            this.slime = this.createSlime();
            this.walkerAnimPrevPos = class_243.field_1353;
            this.walkerSpeedSm = 0.0f;
            Raven.setSlimeSizeSafe(this.slime, 1);
        }
        this.slime.field_6012 = class_16572.field_6012;
        try {
            this.slime.method_5880(false);
            this.slime.method_5665(null);
        }
        catch (Throwable throwable) {
        }
    }

    private static void setSlimeSizeSafe(class_1621 class_16212, int n) {
        if (class_16212 == null) {
            return;
        }
        try {
            class_16212.method_7161(class_3532.method_15340((int)n, (int)1, (int)2), false);
        }
        catch (Throwable throwable) {
        }
    }

    private void renderBatWithParrotLogic(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1420 class_14202, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l) {
        double d4 = (double)l / 1000.0;
        this.updateShoulderOrbit(class_16572, f2, d, d2, d3, class_2432, class_2433, f3, f4, f5, f6, d4);
        class_14202.field_6014 = class_14202.method_23317();
        class_14202.field_6036 = class_14202.method_23318();
        class_14202.field_5969 = class_14202.method_23321();
        class_14202.method_23327(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_14202.field_5982 = class_14202.method_36454();
        class_14202.method_36456(this.smoothYaw);
        class_14202.field_6283 = this.smoothYaw;
        class_14202.field_6241 = this.smoothYaw;
        class_14202.field_6220 = this.smoothYaw;
        class_14202.field_6259 = this.smoothYaw;
        class_14202.field_6004 = class_14202.method_36455();
        class_14202.method_36457(this.smoothPitch);
        class_14202.method_24830(false);
        class_14202.method_18380(class_4050.field_18076);
        this.batRoost.forceFlying(class_14202);
        this.tickBatForAnim(class_14202, l);
        class_14202.field_6012 = class_16572.field_6012;
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)class_14202);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_45872.method_22905(f7, f7, f7);
        this.renderCompat(class_8973, (class_1297)class_14202, this.smoothYaw, f, class_45872, class_45982, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private void renderParrotLike(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1453 class_14532, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l, boolean bl) {
        double d4 = (double)l / 1000.0;
        this.updateShoulderOrbit(class_16572, f2, d, d2, d3, class_2432, class_2433, f3, f4, f5, f6, d4);
        class_14532.field_6014 = class_14532.method_23317();
        class_14532.field_6036 = class_14532.method_23318();
        class_14532.field_5969 = class_14532.method_23321();
        class_14532.method_23327(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_14532.field_5982 = class_14532.method_36454();
        class_14532.method_36456(this.smoothYaw);
        class_14532.field_6283 = this.smoothYaw;
        class_14532.field_6241 = this.smoothYaw;
        class_14532.field_6220 = this.smoothYaw;
        class_14532.field_6259 = this.smoothYaw;
        class_14532.field_6004 = class_14532.method_36455();
        class_14532.method_36457(this.smoothPitch);
        class_14532.method_24830(false);
        class_14532.method_18380(class_4050.field_18076);
        float f8 = bl ? 1.15f : 5.25f;
        this.animateParrotWingsConstant(class_14532, d4, f8);
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)class_14532);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float f9 = bl ? f7 * 1.28f : f7;
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_45872.method_22905(f9, f9, f9);
        Object object = bl ? new TextureSwapVcp((class_4597)class_45982, VORON_TEX) : class_45982;
        this.renderCompat(class_8973, (class_1297)class_14532, this.smoothYaw, f, class_45872, object, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private void renderGenericFlyer(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1297 class_12972, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l, float f8, float f9) {
        if (class_12972 == null) {
            return;
        }
        double d4 = (double)l / 1000.0;
        this.updateShoulderOrbit(class_16572, f2, d, d2, d3, class_2432, class_2433, f3, f4, f5, f6, d4);
        class_12972.field_6014 = class_12972.method_23317();
        class_12972.field_6036 = class_12972.method_23318();
        class_12972.field_5969 = class_12972.method_23321();
        class_12972.method_23327(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_243 class_2434 = this.smoothPos.method_1020(this.prevSmoothPos);
        double d5 = (double)f2 > 1.0E-4 ? 1.0 / (double)f2 : 60.0;
        class_2434 = class_2434.method_1021(d5);
        double d6 = 0.22 + 0.25 * (double)f6;
        double d7 = Math.sqrt(class_2434.field_1352 * class_2434.field_1352 + class_2434.field_1350 * class_2434.field_1350 + class_2434.field_1351 * class_2434.field_1351);
        if (d7 > d6 && d7 > 1.0E-8) {
            class_2434 = class_2434.method_1021(d6 / d7);
        }
        try {
            class_12972.method_18800(class_2434.field_1352, class_2434.field_1351 * 0.45, class_2434.field_1350);
        }
        catch (Throwable throwable) {
        }
        float f10 = this.smoothPitch * f9;
        this.setEntityPoseAngles(class_12972, this.smoothYaw, f10, class_16572.field_6012);
        this.tickEntityForAnim(class_12972, l, f8);
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953(class_12972);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_45872.method_22905(f7, f7, f7);
        this.renderCompat(class_8973, class_12972, this.smoothYaw, f, class_45872, class_45982, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private void renderGroundWalker(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1309 class_13092, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l) {
        if (class_13092 == null) {
            return;
        }
        double d4 = class_3532.method_15350((double)((double)f4 + 0.85), (double)1.2, (double)1.6);
        int n2 = this.walkerWander.frame((class_1937)CosmeticEffects.aiG.field_1687, class_16572.method_23317(), class_16572.method_23318(), class_16572.method_23321(), f6, d4, class_16572.field_6012);
        class_243 class_2434 = this.walkerWander.posLerp(f);
        if (class_2434 == null) {
            return;
        }
        double d5 = 0.02;
        if (class_13092 instanceof class_7102) {
            d5 = 0.0;
        }
        if (class_13092 instanceof class_1621) {
            d5 = 0.01;
        }
        if (class_13092 instanceof class_1621) {
            double d6;
            long l2 = (long)class_3532.method_15350((double)(520.0 - (double)f6 * 55.0), (double)340.0, (double)520.0);
            if (!this.slimeJumpAir) {
                if (this.slimeLastHopMs == 0L) {
                    this.slimeLastHopMs = l;
                }
                if (l - this.slimeLastHopMs >= l2) {
                    this.slimeLastHopMs = l;
                    this.slimeJumpVel = d6 = 0.34 + 0.08 * (double)class_3532.method_15363((float)f6, (float)0.05f, (float)5.0f);
                    this.slimeJumpAir = true;
                }
            }
            if (this.slimeJumpAir) {
                d6 = -2.1;
                this.slimeJumpVel += d6 * (double)f2;
                this.slimeJumpY += this.slimeJumpVel * (double)f2;
                if (this.slimeJumpY <= 0.0) {
                    this.slimeJumpY = 0.0;
                    this.slimeJumpVel = 0.0;
                    this.slimeJumpAir = false;
                }
            }
        }
        class_243 class_2435 = new class_243(class_2434.field_1352, class_2434.field_1351 + d5 + (class_13092 instanceof class_1621 ? this.slimeJumpY : 0.0), class_2434.field_1350);
        float f8 = this.walkerWander.bodyYawLerp(f);
        float f9 = this.walkerWander.headYawLerp(f);
        boolean bl = !(class_13092 instanceof class_1621) || !this.slimeJumpAir;
        class_13092.field_6014 = class_2435.field_1352;
        class_13092.field_6036 = class_2435.field_1351;
        class_13092.field_5969 = class_2435.field_1350;
        class_13092.method_23327(class_2435.field_1352, class_2435.field_1351, class_2435.field_1350);
        Raven.applyYaws(class_13092, f8, f9);
        class_13092.field_6004 = 0.0f;
        class_13092.method_36457(0.0f);
        class_13092.method_18380(class_4050.field_18076);
        class_13092.field_6012 = class_16572.field_6012;
        try {
            class_13092.method_5875(true);
            class_13092.method_24830(bl);
        }
        catch (Throwable throwable) {
        }
        this.tickEntityForAnim((class_1297)class_13092, l, 1.0f);
        class_13092.method_23327(class_2435.field_1352, class_2435.field_1351, class_2435.field_1350);
        Raven.applyYaws(class_13092, f8, f9);
        for (int i = 0; i < n2; ++i) {
            Raven.driveLimbs(class_13092, this.walkerWander.limbPosDelta());
        }
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)class_13092);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(class_2435.field_1352, class_2435.field_1351, class_2435.field_1350);
        class_45872.method_22905(f7, f7, f7);
        this.renderCompat(class_8973, (class_1297)class_13092, f8, f, class_45872, class_45982, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private static void applyYaws(class_1309 class_13092, float f, float f2) {
        class_13092.field_5982 = class_13092.method_36454();
        class_13092.method_36456(f);
        try {
            class_13092.field_6283 = f;
            class_13092.field_6220 = f;
            class_13092.field_6241 = f2;
            class_13092.field_6259 = f2;
        }
        catch (Throwable throwable) {
        }
    }

    private static void driveLimbs(class_1309 class_13092, float f) {
        try {
            float f2 = Math.min(f * 4.0f, 1.0f);
            class_13092.field_42108.method_48568(f2, 0.4f, 1.0f);
        }
        catch (Throwable throwable) {
        }
    }

    private void renderPufferfishOrbit(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_1454 class_14542, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l) {
        if (class_14542 == null) {
            return;
        }
        double d4 = (double)l / 1000.0;
        this.updateShoulderOrbit(class_16572, f2, d, d2, d3, class_2432, class_2433, f3, f4, f5, f6, d4);
        class_14542.field_6014 = class_14542.method_23317();
        class_14542.field_6036 = class_14542.method_23318();
        class_14542.field_5969 = class_14542.method_23321();
        class_14542.method_23327(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_243 class_2434 = this.smoothPos.method_1020(this.prevSmoothPos);
        double d5 = (double)f2 > 1.0E-4 ? 1.0 / (double)f2 : 60.0;
        class_2434 = class_2434.method_1021(d5);
        double d6 = 0.2 + 0.22 * (double)f6;
        double d7 = Math.sqrt(class_2434.field_1352 * class_2434.field_1352 + class_2434.field_1350 * class_2434.field_1350 + class_2434.field_1351 * class_2434.field_1351);
        if (d7 > d6 && d7 > 1.0E-8) {
            class_2434 = class_2434.method_1021(d6 / d7);
        }
        try {
            class_14542.method_5875(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14542.method_24830(false);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14542.method_18800(class_2434.field_1352, class_2434.field_1351 * 0.4, class_2434.field_1350);
        }
        catch (Throwable throwable) {
        }
        float f8 = (float)((Math.sin(d4 * 1.35) + 1.0) * 0.5);
        int n2 = f8 < 0.33f ? 0 : (f8 < 0.66f ? 1 : 2);
        this.pufferState.apply(class_14542, n2);
        this.setEntityPoseAngles((class_1297)class_14542, this.smoothYaw, 0.0f, class_16572.field_6012, false, class_4050.field_18076);
        this.tickEntityForAnim((class_1297)class_14542, l, 1.0f);
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)class_14542);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(this.smoothPos.field_1352, this.smoothPos.field_1351, this.smoothPos.field_1350);
        class_45872.method_22905(f7, f7, f7);
        this.renderCompat(class_8973, (class_1297)class_14542, this.smoothYaw, f, class_45872, class_45982, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private static float dirYaw(class_2350 class_23502) {
        if (class_23502 == null) {
            return 0.0f;
        }
        try {
            return class_23502.method_10144();
        }
        catch (Throwable throwable) {
            Object object;
            Method method;
            try {
                method = class_23502.getClass().getMethod("asRotation", new Class[0]);
                object = method.invoke((Object)class_23502, new Object[0]);
                if (object instanceof Number) {
                    Number number = (Number)object;
                    return number.floatValue();
                }
            }
            catch (Throwable throwable2) {
            }
            try {
                method = class_23502.getClass().getMethod("getHorizontal", new Class[0]);
                object = method.invoke((Object)class_23502, new Object[0]);
                if (object instanceof Number) {
                    Number number = (Number)object;
                    return number.floatValue() * 90.0f;
                }
            }
            catch (Throwable throwable3) {
            }
            switch (class_23502) {
                case field_11035: {
                    return 0.0f;
                }
                case field_11039: {
                    return 90.0f;
                }
                case field_11043: {
                    return 180.0f;
                }
                case field_11034: {
                    return 270.0f;
                }
            }
            return 0.0f;
        }
    }

    private static float dimHeight(Object object) {
        Object object2;
        AccessibleObject accessibleObject;
        if (object == null) {
            return 0.0f;
        }
        try {
            accessibleObject = object.getClass().getDeclaredField("height");
            try {
                ((Field)accessibleObject).setAccessible(true);
            }
            catch (Throwable throwable) {
            }
            object2 = ((Field)accessibleObject).get(object);
            if (object2 instanceof Number) {
                Number number = (Number)object2;
                return number.floatValue();
            }
        }
        catch (Throwable throwable) {
        }
        try {
            accessibleObject = object.getClass().getMethod("height", new Class[0]);
            object2 = ((Method)accessibleObject).invoke(object, new Object[0]);
            if (object2 instanceof Number) {
                Number number = (Number)object2;
                return number.floatValue();
            }
        }
        catch (Throwable throwable) {
        }
        try {
            accessibleObject = object.getClass().getMethod("getHeight", new Class[0]);
            object2 = ((Method)accessibleObject).invoke(object, new Object[0]);
            if (object2 instanceof Number) {
                Number number = (Number)object2;
                return number.floatValue();
            }
        }
        catch (Throwable throwable) {
        }
        return 0.0f;
    }

    private void renderFoxAI(WorldRenderStages.MatrixStackStage matrixStackStage, class_4597.class_4598 class_45982, int n, class_4019 class_40192, class_1657 class_16572, float f, float f2, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f3, float f4, float f5, float f6, float f7, long l) {
        float f8;
        float f9;
        double d4;
        class_243 class_2434;
        if (class_40192 == null) {
            return;
        }
        double d5 = (double)l / 1000.0;
        boolean bl = class_16572.method_5624() || this.moveSp > 0.07f;
        this.foxState.update(class_16572, bl, l);
        int n2 = this.foxState.mode;
        double d6 = class_3532.method_15350((double)((double)f4 + 0.85), (double)1.2, (double)1.6);
        int n3 = 0;
        if (n2 == 0 || !this.foxWander.hasPos()) {
            n3 = this.foxWander.frame((class_1937)CosmeticEffects.aiG.field_1687, d, d2, d3, f6, d6, class_16572.field_6012);
        }
        class_243 class_2435 = class_2434 = n2 == 0 ? this.foxWander.posLerp(f) : this.foxWander.pos();
        if (class_2434 == null) {
            return;
        }
        double d7 = class_2434.field_1351;
        if (n2 == 1 || n2 == 2) {
            d4 = PetGroundSampler.sampleGroundY((class_1937)CosmeticEffects.aiG.field_1687, class_2434.field_1352, class_2434.field_1350, d2);
            if (!Double.isNaN(d4)) {
                d7 = d4;
            }
        }
        d4 = 0.06;
        if (n2 == 2) {
            double d8 = -0.3;
            f9 = 0.75f;
            float f10 = 0.25f;
            try {
                class_4048 class_40482 = class_40192.method_18377(class_4050.field_18076);
                class_4048 class_40483 = class_40192.method_18377(class_4050.field_18078);
                f9 = Raven.dimHeight(class_40482);
                f10 = Raven.dimHeight(class_40483);
            }
            catch (Throwable throwable) {
            }
            double d9 = Math.max(0.0, (double)(f9 - f10) * 0.5);
            d4 = 0.06 + d9 + d8;
        }
        class_243 class_2436 = new class_243(class_2434.field_1352, d7 + d4, class_2434.field_1350);
        float f11 = n2 == 0 ? this.foxWander.bodyYawLerp(f) : this.foxWander.bodyYaw();
        f9 = n2 == 0 ? this.foxWander.headYawLerp(f) : this.foxWander.headYaw();
        int n4 = class_3532.method_15375((float)(f11 * 4.0f / 360.0f + 0.5f)) & 3;
        class_2350 class_23502 = switch (n4) {
            case 0 -> class_2350.field_11035;
            case 1 -> class_2350.field_11039;
            case 2 -> class_2350.field_11043;
            default -> class_2350.field_11034;
        };
        boolean bl2 = true;
        class_40192.field_6014 = class_2436.field_1352;
        class_40192.field_6036 = class_2436.field_1351;
        class_40192.field_5969 = class_2436.field_1350;
        class_40192.method_23327(class_2436.field_1352, class_2436.field_1351, class_2436.field_1350);
        class_40192.field_6004 = 0.0f;
        class_40192.method_36457(0.0f);
        class_40192.field_6012 = class_16572.field_6012;
        if (n2 == 2) {
            float f12;
            f8 = f12 = Raven.dirYaw(class_23502);
            Raven.applyYaws((class_1309)class_40192, f12, f12);
            this.foxState.forceSleep(class_40192, class_23502);
            this.setEntityPoseAngles((class_1297)class_40192, f12, 0.0f, class_16572.field_6012, bl2, class_4050.field_18076);
            try {
                class_40192.method_5875(true);
                class_40192.method_18800(0.0, 0.0, 0.0);
            }
            catch (Throwable throwable) {
            }
            this.tickFoxForAnim(class_40192, l);
            Raven.applyYaws((class_1309)class_40192, f12, f12);
            Raven.driveLimbs((class_1309)class_40192, 0.0f);
        } else if (n2 == 1) {
            f8 = f11;
            Raven.applyYaws((class_1309)class_40192, f11, f9);
            this.foxState.applySittingFlag(class_40192);
            this.setEntityPoseAngles((class_1297)class_40192, f11, 0.0f, class_16572.field_6012, bl2, class_4050.field_40118);
            try {
                class_40192.method_5875(true);
                class_40192.method_18800(0.0, 0.0, 0.0);
            }
            catch (Throwable throwable) {
            }
            this.tickFoxForAnim(class_40192, l);
            Raven.applyYaws((class_1309)class_40192, f11, f9);
            Raven.driveLimbs((class_1309)class_40192, 0.0f);
        } else {
            f8 = f11;
            Raven.applyYaws((class_1309)class_40192, f11, f9);
            this.foxState.applyMovingFlags(class_40192);
            this.setEntityPoseAngles((class_1297)class_40192, f11, 0.0f, class_16572.field_6012, bl2, class_4050.field_18076);
            try {
                class_40192.method_5875(true);
            }
            catch (Throwable throwable) {
            }
            this.tickFoxForAnim(class_40192, l);
            class_40192.method_23327(class_2436.field_1352, class_2436.field_1351, class_2436.field_1350);
            Raven.applyYaws((class_1309)class_40192, f11, f9);
            for (int i = 0; i < n3; ++i) {
                Raven.driveLimbs((class_1309)class_40192, this.foxWander.limbPosDelta());
            }
        }
        class_897 class_8972 = CosmeticEffects.aiG.method_1561().method_3953((class_1297)class_40192);
        if (!(class_8972 instanceof class_897)) {
            return;
        }
        class_897 class_8973 = class_8972;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4587 class_45872 = matrixStackStage.l();
        class_45872.method_22903();
        class_45872.method_22904(class_2436.field_1352, class_2436.field_1351, class_2436.field_1350);
        class_45872.method_22905(f7, f7, f7);
        this.renderCompat(class_8973, (class_1297)class_40192, f8, f, class_45872, class_45982, n);
        class_45872.method_22909();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private boolean playerAirborne(class_1657 class_16572, long l) {
        try {
            if (class_16572.method_31549().field_7479) {
                return true;
            }
        }
        catch (Throwable throwable) {
        }
        try {
            if (class_16572.method_6128()) {
                return true;
            }
        }
        catch (Throwable throwable) {
        }
        return l - this.lastPlayerGroundedMs > 450L;
    }

    private class_243 resolveGroundPos(class_1657 class_16572, class_243 class_2432, double d, double d2, double d3, boolean bl) {
        class_638 class_6382 = CosmeticEffects.aiG.field_1687;
        double d4 = PetGroundSampler.sampleGroundYSmooth((class_1937)class_6382, class_2432.field_1352, class_2432.field_1350, d2);
        if (bl) {
            double d5;
            if (Double.isNaN(d4)) {
                d5 = d2;
            } else {
                double d6 = d2 - d4;
                double d7 = class_3532.method_15350((double)((d6 - 1.5) / 2.0), (double)0.0, (double)1.0);
                d5 = d4 + d6 * d7;
            }
            return new class_243(class_2432.field_1352, d5, class_2432.field_1350);
        }
        if (!Double.isNaN(d4) && Math.abs(d4 - d2) <= 1.5) {
            return new class_243(class_2432.field_1352, d4, class_2432.field_1350);
        }
        for (int i = 1; i <= 5; ++i) {
            double d8 = (double)i / 6.0;
            double d9 = class_2432.field_1352 + (d - class_2432.field_1352) * d8;
            double d10 = class_2432.field_1350 + (d3 - class_2432.field_1350) * d8;
            double d11 = PetGroundSampler.sampleGroundYSmooth((class_1937)class_6382, d9, d10, d2);
            if (Double.isNaN(d11) || !(Math.abs(d11 - d2) <= 1.5)) continue;
            return new class_243(d9, d11, d10);
        }
        double d12 = PetGroundSampler.sampleGroundYSmooth((class_1937)class_6382, d, d3, d2);
        double d13 = !Double.isNaN(d12) && Math.abs(d12 - d2) <= 1.5 ? d12 : d2;
        return new class_243(d, d13, d3);
    }

    private void updateShoulderOrbit(class_1657 class_16572, float f, double d, double d2, double d3, class_243 class_2432, class_243 class_2433, float f2, float f3, float f4, float f5, double d4) {
        boolean bl;
        boolean bl2 = class_16572.method_5624() || this.moveSp > 0.12f;
        boolean bl3 = bl = !class_16572.method_5624() && this.moveSp < 0.07f;
        if (bl2) {
            this.wantHoldTime += f;
            this.wantOrbitTime = 0.0f;
        } else if (bl) {
            this.wantOrbitTime += f;
            this.wantHoldTime = 0.0f;
        } else {
            this.wantHoldTime = Math.max(0.0f, this.wantHoldTime - f * 0.75f);
            this.wantOrbitTime = Math.max(0.0f, this.wantOrbitTime - f * 0.75f);
        }
        if (!this.holdMode && this.wantHoldTime >= 0.16f) {
            this.holdMode = true;
        }
        if (this.holdMode && this.wantOrbitTime >= 0.2f) {
            this.holdMode = false;
        }
        float f6 = this.holdMode ? 0.3f : 0.36f;
        float f7 = 1.0f - (float)Math.exp(-f / Math.max(0.06f, f6));
        float f8 = this.holdMode ? 0.0f : 1.0f;
        this.orbitBlend += (f8 - this.orbitBlend) * f7;
        float f9 = Raven.easeInOut(class_3532.method_15363((float)this.orbitBlend, (float)0.0f, (float)1.0f));
        float f10 = f5 * 1.25f;
        this.orbitAng = Raven.wrapRad(this.orbitAng + f10 * f * (0.26f + 0.26f * (1.0f - f9)));
        double d5 = Math.cos(this.orbitAng) * (double)f3;
        double d6 = Math.sin(this.orbitAng) * (double)f3;
        double d7 = d2 + (double)class_16572.method_5751() * 0.35 + (double)f4 + Math.sin(d4 * 1.6) * 0.055;
        class_243 class_2434 = new class_243(d + class_2433.field_1352 * d5 + class_2432.field_1352 * d6, d7, d3 + class_2433.field_1350 * d5 + class_2432.field_1350 * d6);
        class_243 class_2435 = this.computeLeftShoulder(d, d2, d3, class_16572, class_2432, class_2433, d4);
        class_243 class_2436 = Raven.lerpVec(class_2435, class_2434, f9);
        float f11 = 0.09f + 0.08f * (1.0f - f9);
        float f12 = 1.0f - (float)Math.exp(-f / Math.max(0.05f, f11));
        if (this.smoothPos == class_243.field_1353) {
            this.prevSmoothPos = class_2436;
            this.smoothPos = class_2436;
        } else if (this.smoothPos.method_1025(class_2436) > 256.0) {
            this.prevSmoothPos = class_2436;
            this.smoothPos = class_2436;
        } else {
            this.prevSmoothPos = this.smoothPos;
            this.smoothPos = this.smoothPos.method_1019(class_2436.method_1020(this.smoothPos).method_1021((double)f12));
        }
        class_243 class_2437 = this.smoothPos.method_1020(this.prevSmoothPos);
        double d8 = (double)f > 1.0E-4 ? 1.0 / (double)f : 60.0;
        class_243 class_2438 = class_2437.method_1021(d8);
        float f13 = class_2438.field_1352 * class_2438.field_1352 + class_2438.field_1350 * class_2438.field_1350 > 1.0E-8 ? (float)(class_3532.method_15349((double)class_2438.field_1350, (double)class_2438.field_1352) * 57.29577951308232) - 90.0f : this.smoothYaw;
        float f14 = f2;
        float f15 = Raven.lerpAngle(f14, f13, f9);
        float f16 = 1.0f - (float)Math.exp(-f / 0.16f);
        this.smoothYaw = Raven.lerpAngle(this.smoothYaw, f15, f16);
        float f17 = (float)class_3532.method_15350((double)(-class_2438.field_1351 * 7.5), (double)-16.0, (double)16.0);
        float f18 = (float)Math.sin(d4 * 1.45) * 2.0f;
        float f19 = f18 + (f17 - f18) * f9;
        float f20 = 1.0f - (float)Math.exp(-f / 0.2f);
        this.smoothPitch += (f19 - this.smoothPitch) * f20;
    }

    private void tickBatForAnim(class_1420 class_14202, long l) {
        long l2;
        long l3;
        if (class_14202 == null) {
            return;
        }
        if (this.lastBatTickMs == 0L) {
            this.lastBatTickMs = l;
        }
        if ((l3 = l - this.lastBatTickMs) < (l2 = 50L)) {
            return;
        }
        long l4 = Math.min(3L, l3 / l2);
        for (int i = 0; i < (int)l4; ++i) {
            try {
                class_14202.field_5960 = true;
                class_14202.method_5773();
                continue;
            }
            catch (Throwable throwable) {
            }
        }
        this.lastBatTickMs += l4 * l2;
    }

    private void tickFoxForAnim(class_4019 class_40192, long l) {
        long l2;
        long l3;
        if (class_40192 == null) {
            return;
        }
        if (this.lastFoxTickMs == 0L) {
            this.lastFoxTickMs = l;
        }
        if ((l3 = l - this.lastFoxTickMs) < (l2 = 50L)) {
            return;
        }
        long l4 = Math.min(2L, l3 / l2);
        for (int i = 0; i < (int)l4; ++i) {
            try {
                class_40192.field_5960 = true;
                class_40192.method_5773();
                continue;
            }
            catch (Throwable throwable) {
            }
        }
        this.lastFoxTickMs += l4 * l2;
    }

    private void tickEntityForAnim(class_1297 class_12972, long l, float f) {
        long l2;
        long l3;
        if (class_12972 == null) {
            return;
        }
        if (this.lastPetTickMs == 0L) {
            this.lastPetTickMs = l;
        }
        if ((l3 = l - this.lastPetTickMs) < (l2 = 50L)) {
            return;
        }
        long l4 = Math.min(6L, l3 / l2);
        int n = Math.max(1, Math.min(10, (int)Math.floor((float)l4 * class_3532.method_15363((float)f, (float)0.35f, (float)2.35f))));
        for (int i = 0; i < n; ++i) {
            try {
                class_12972.field_5960 = true;
                class_12972.method_5773();
                continue;
            }
            catch (Throwable throwable) {
            }
        }
        this.lastPetTickMs += l4 * l2;
    }

    private class_243 computeLeftShoulder(double d, double d2, double d3, class_1657 class_16572, class_243 class_2432, class_243 class_2433, double d4) {
        double d5 = d2 + (double)class_16572.method_5751() * 0.74 + 0.08;
        double d6 = -0.34;
        double d7 = 0.12;
        double d8 = Math.sin(d4 * 1.25) * 0.016;
        class_243 class_2434 = class_2432.method_1021(-1.0);
        return new class_243(d + class_2433.field_1352 * d6 + class_2434.field_1352 * d7, (d5 += Math.sin(d4 * 2.9) * 0.03) + d8, d3 + class_2433.field_1350 * d6 + class_2434.field_1350 * d7);
    }

    private class_1420 createBat() {
        class_1420 class_14202 = new class_1420(class_1299.field_6108, (class_1937)CosmeticEffects.aiG.field_1687);
        class_14202.method_5875(true);
        class_14202.method_5803(true);
        class_14202.method_5648(false);
        class_14202.method_24830(false);
        class_14202.method_18380(class_4050.field_18076);
        class_14202.method_5880(false);
        class_14202.method_5665(null);
        try {
            class_14202.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14202.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_14202;
    }

    private class_1453 createParrot() {
        class_1453 class_14532 = new class_1453(class_1299.field_6104, (class_1937)CosmeticEffects.aiG.field_1687);
        class_14532.method_5875(true);
        class_14532.method_5803(true);
        class_14532.method_5648(false);
        class_14532.method_24830(false);
        class_14532.method_18380(class_4050.field_18076);
        class_14532.method_5880(false);
        class_14532.method_5665(null);
        try {
            class_14532.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14532.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_14532;
    }

    private class_7298 createAllay() {
        class_7298 class_72982 = new class_7298(class_1299.field_38384, (class_1937)CosmeticEffects.aiG.field_1687);
        class_72982.method_5875(true);
        class_72982.method_5803(true);
        class_72982.method_5648(false);
        class_72982.method_24830(false);
        class_72982.method_18380(class_4050.field_18076);
        class_72982.method_5880(false);
        class_72982.method_5665(null);
        try {
            class_72982.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_72982.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_72982;
    }

    private class_4466 createBee() {
        class_4466 class_44662 = new class_4466(class_1299.field_20346, (class_1937)CosmeticEffects.aiG.field_1687);
        class_44662.method_5875(true);
        class_44662.method_5803(true);
        class_44662.method_5648(false);
        class_44662.method_24830(false);
        class_44662.method_18380(class_4050.field_18076);
        class_44662.method_5880(false);
        class_44662.method_5665(null);
        try {
            class_44662.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_44662.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_44662;
    }

    private class_1634 createVex() {
        class_1634 class_16342 = new class_1634(class_1299.field_6059, (class_1937)CosmeticEffects.aiG.field_1687);
        class_16342.method_5875(true);
        class_16342.method_5803(true);
        class_16342.method_5648(false);
        class_16342.method_24830(false);
        class_16342.method_18380(class_4050.field_18076);
        class_16342.method_5880(false);
        class_16342.method_5665(null);
        try {
            class_16342.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_16342.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_16342;
    }

    private class_4019 createFox() {
        class_4019 class_40192 = new class_4019(class_1299.field_17943, (class_1937)CosmeticEffects.aiG.field_1687);
        class_40192.method_5875(true);
        class_40192.method_5803(true);
        class_40192.method_5648(false);
        class_40192.method_24830(true);
        class_40192.method_18380(class_4050.field_18076);
        class_40192.method_5880(false);
        class_40192.method_5665(null);
        try {
            class_40192.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_40192.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        Raven.setFoxSnowVariantSafe(class_40192);
        return class_40192;
    }

    private class_1452 createPig() {
        class_1452 class_14522 = new class_1452(class_1299.field_6093, (class_1937)CosmeticEffects.aiG.field_1687);
        class_14522.method_5875(true);
        class_14522.method_5803(true);
        class_14522.method_5648(false);
        class_14522.method_24830(true);
        class_14522.method_18380(class_4050.field_18076);
        class_14522.method_5880(false);
        class_14522.method_5665(null);
        try {
            class_14522.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14522.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_14522;
    }

    private class_7102 createFrog() {
        class_7102 class_71022 = new class_7102(class_1299.field_37419, (class_1937)CosmeticEffects.aiG.field_1687);
        class_71022.method_5875(true);
        class_71022.method_5803(true);
        class_71022.method_5648(false);
        class_71022.method_24830(true);
        class_71022.method_18380(class_4050.field_18076);
        class_71022.method_5880(false);
        class_71022.method_5665(null);
        try {
            class_71022.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_71022.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        Raven.setFrogColdVariantSafe(class_71022);
        return class_71022;
    }

    private class_1454 createPufferfish() {
        class_1454 class_14542 = new class_1454(class_1299.field_6062, (class_1937)CosmeticEffects.aiG.field_1687);
        class_14542.method_5875(true);
        class_14542.method_5803(true);
        class_14542.method_5648(false);
        class_14542.method_24830(false);
        class_14542.method_18380(class_4050.field_18076);
        class_14542.method_5880(false);
        class_14542.method_5665(null);
        try {
            class_14542.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_14542.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_14542;
    }

    private class_1621 createSlime() {
        class_1621 class_16212 = new class_1621(class_1299.field_6069, (class_1937)CosmeticEffects.aiG.field_1687);
        class_16212.method_5875(true);
        class_16212.method_5803(true);
        class_16212.method_5648(false);
        class_16212.method_24830(true);
        class_16212.method_18380(class_4050.field_18076);
        class_16212.method_5880(false);
        class_16212.method_5665(null);
        try {
            class_16212.method_5977(true);
        }
        catch (Throwable throwable) {
        }
        try {
            class_16212.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        Raven.setSlimeSizeSafe(class_16212, 1);
        return class_16212;
    }

    private void animateParrotWingsConstant(class_1453 class_14532, double d, float f) {
        float f2 = (float)(Math.sin(d * (double)f * 6.2) * 0.5 + 0.5);
        class_14532.field_6827 = class_14532.field_6819;
        class_14532.field_6819 = 0.1f + f2 * 0.24f;
        class_14532.field_6829 = class_14532.field_6818;
        class_14532.field_6818 = (float)(d * (double)f * 3.6);
    }

    private void initParrotWingCompat(Class<?> clazz) {
        this.parrotWing.maxWingDeviation = Raven.findFloatField(clazz, "maxWingDeviation");
        this.parrotWing.prevMaxWingDeviation = Raven.findFloatField(clazz, "prevMaxWingDeviation");
        this.parrotWing.flapAngle = Raven.findFloatField(clazz, "flapAngle");
        this.parrotWing.prevFlapAngle = Raven.findFloatField(clazz, "prevFlapAngle");
        this.parrotWing.flapProgress = Raven.findFloatField(clazz, "flapProgress");
        this.parrotWing.prevFlapProgress = Raven.findFloatField(clazz, "prevFlapProgress");
        for (Method method : clazz.getMethods()) {
            String string;
            if (method.getParameterCount() != 0 || method.getReturnType() != Void.TYPE || !(string = method.getName().toLowerCase()).contains("update") || !string.contains("limb") && !string.contains("anim")) continue;
            this.parrotWing.updateLimbs = method;
            break;
        }
        this.parrotWing.inited = true;
    }

    private void renderCompat(class_897 class_8972, class_1297 class_12972, float f, float f2, Object object, Object object2, int n) {
        try {
            class_10017 class_100172 = class_8972.method_62425(class_12972, f2);
            if (class_100172 == null) {
                return;
            }
            class_8972.method_3936(class_100172, (class_4587)object, (class_4597)object2, n);
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable.getCause() != null ? throwable.getCause() : throwable;
            Raven.celebression$debugRender("[PET_DBG] renderCompat \u0423\u041f\u0410\u041b (" + class_8972.getClass().getName() + "): " + throwable2.getClass().getName() + ": " + throwable2.getMessage());
        }
    }

    private static void celebression$debugRender(String string) {
        long l = System.currentTimeMillis();
        if (l - celebression$lastDbgMs < 1500L) {
            return;
        }
        celebression$lastDbgMs = l;
        Celebression.LOGGER.warn(string);
    }

    private void initCompat(Class<?> clazz) {
        Class<?>[] classArray;
        Method method = null;
        Method method2 = null;
        Method method3 = null;
        for (Method method4 : clazz.getMethods()) {
            if (!method4.getName().equals("render")) continue;
            classArray = method4.getParameterTypes();
            if (classArray.length == 6 && class_1297.class.isAssignableFrom(classArray[0]) && classArray[1] == Float.TYPE && classArray[2] == Float.TYPE && classArray[3].getName().endsWith("MatrixStack") && classArray[4].getName().endsWith("VertexConsumerProvider") && classArray[5] == Integer.TYPE) {
                method = method4;
                break;
            }
            if (classArray.length != 4 || !classArray[1].getName().endsWith("MatrixStack") || !classArray[2].getName().endsWith("VertexConsumerProvider") || classArray[3] != Integer.TYPE || !classArray[0].getName().toLowerCase().contains("renderstate")) continue;
            method2 = method4;
        }
        if (method != null) {
            this.compat.render = method;
            this.compat.mode = 6;
            this.compat.inited = true;
            Celebression.LOGGER.warn("[PET_DBG] initCompat: \u0440\u0435\u0436\u0438\u043c 6 (\u0441\u0442\u0430\u0440\u0430\u044f \u0441\u0438\u0433\u043d\u0430\u0442\u0443\u0440\u0430) \u0434\u043b\u044f " + clazz.getName());
            return;
        }
        if (method2 != null) {
            for (Method method4 : clazz.getMethods()) {
                String string;
                classArray = method4.getParameterTypes();
                if (classArray.length != 2 || !class_1297.class.isAssignableFrom(classArray[0]) || classArray[1] != Float.TYPE || method4.getReturnType() == Void.TYPE || !(string = method4.getReturnType().getName().toLowerCase()).contains("renderstate")) continue;
                method3 = method4;
                break;
            }
            if (method3 != null) {
                this.compat.render = method2;
                this.compat.getState = method3;
                this.compat.mode = 4;
                this.compat.inited = true;
                Celebression.LOGGER.warn("[PET_DBG] initCompat: \u0440\u0435\u0436\u0438\u043c 4 (render-state) \u0434\u043b\u044f " + clazz.getName() + " | render=" + method2.getName() + " getState=" + method3.getName());
                return;
            }
            Celebression.LOGGER.warn("[PET_DBG] initCompat: render4 \u043d\u0430\u0439\u0434\u0435\u043d, \u043d\u043e getState \u041d\u0415 \u043d\u0430\u0439\u0434\u0435\u043d \u0434\u043b\u044f " + clazz.getName());
            return;
        }
        Celebression.LOGGER.warn("[PET_DBG] initCompat: \u041d\u0418 render6 \u041d\u0418 render4 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b \u0434\u043b\u044f " + clazz.getName());
    }

    private static void setParrotVariantSafe(class_1453 class_14532, int n) {
        if (class_14532 == null) {
            return;
        }
        try {
            class_14532.method_47848(class_1453.class_7989.method_47850((int)n));
        }
        catch (Throwable throwable) {
        }
    }

    private static void setFrogColdVariantSafe(class_7102 class_71022) {
        if (class_71022 == null) {
            return;
        }
        try {
            class_71022.method_37908().method_30349().method_46759(class_7924.field_41272).flatMap(class_23782 -> class_23782.method_10223(class_7106.field_37463.method_29177())).ifPresent(arg_0 -> ((class_7102)class_71022).method_41353(arg_0));
        }
        catch (Throwable throwable) {
        }
    }

    private static void setFoxSnowVariantSafe(class_4019 class_40192) {
        if (class_40192 == null) {
            return;
        }
        try {
            class_40192.method_47844(class_4019.class_4039.field_17997);
        }
        catch (Throwable throwable) {
        }
    }

    private static Object findEnumConst(Class<?> clazz, String string) {
        if (clazz == null || !clazz.isEnum() || string == null) {
            return null;
        }
        try {
            Object[] objArray = clazz.getEnumConstants();
            if (objArray == null) {
                return null;
            }
            for (Object obj : objArray) {
                Enum enum_;
                if (obj == null) continue;
                if (obj.toString().equalsIgnoreCase(string)) {
                    return obj;
                }
                if (!(obj instanceof Enum) || !(enum_ = (Enum)obj).name().equalsIgnoreCase(string)) continue;
                return obj;
            }
        }
        catch (Throwable throwable) {
        }
        return null;
    }

    private static float getFloat(Object object, Field field) {
        if (object == null || field == null) {
            return 0.0f;
        }
        try {
            return field.getFloat(object);
        }
        catch (Throwable throwable) {
            try {
                Object object2 = field.get(object);
                if (object2 instanceof Number) {
                    Number number = (Number)object2;
                    return number.floatValue();
                }
            }
            catch (Throwable throwable2) {
            }
            return 0.0f;
        }
    }

    private static void trySet(Object object, Field field, float f) {
        if (object == null || field == null) {
            return;
        }
        try {
            field.setFloat(object, f);
            return;
        }
        catch (Throwable throwable) {
            try {
                field.set(object, Float.valueOf(f));
            }
            catch (Throwable throwable2) {
            }
            return;
        }
    }

    private static Field findFloatField(Class<?> clazz, String string) {
        if (clazz == null || string == null) {
            return null;
        }
        for (Class<?> clazz2 = clazz; clazz2 != null && clazz2 != Object.class; clazz2 = clazz2.getSuperclass()) {
            try {
                Field field = clazz2.getDeclaredField(string);
                try {
                    field.setAccessible(true);
                }
                catch (Throwable throwable) {
                }
                if (field.getType() != Float.TYPE && field.getType() != Float.class) continue;
                return field;
            }
            catch (Throwable throwable) {
            }
        }
        return null;
    }

    private static void tryInvokeNoArg(Object object, Method method) {
        if (object == null || method == null) {
            return;
        }
        try {
            method.invoke(object, new Object[0]);
        }
        catch (Throwable throwable) {
        }
    }

    private static boolean celebression$isUpdateLimbsMethod(String string) {
        if (string == null) {
            return false;
        }
        if (string.equals("method_48565")) {
            return true;
        }
        String string2 = string.toLowerCase();
        return string2.contains("update") && string2.contains("limb");
    }

    private static float lerp(float f, float f2, float f3) {
        return f + (f2 - f) * f3;
    }

    private static float easeInOut(float f) {
        f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return f * f * (3.0f - 2.0f * f);
    }

    private static class_243 lerpVec(class_243 class_2432, class_243 class_2433, float f) {
        return new class_243(class_2432.field_1352 + (class_2433.field_1352 - class_2432.field_1352) * (double)f, class_2432.field_1351 + (class_2433.field_1351 - class_2432.field_1351) * (double)f, class_2432.field_1350 + (class_2433.field_1350 - class_2432.field_1350) * (double)f);
    }

    private static float lerpAngle(float f, float f2, float f3) {
        float f4 = class_3532.method_15393((float)(f2 - f));
        return f + f4 * f3;
    }

    private static float yawFromDir(class_243 class_2432) {
        double d = class_2432.field_1352;
        double d2 = class_2432.field_1350;
        if (Math.abs(d) < 1.0E-9 && Math.abs(d2) < 1.0E-9) {
            return 0.0f;
        }
        return (float)(class_3532.method_15349((double)d2, (double)d) * 57.29577951308232) - 90.0f;
    }

    private static float pitchFromDir(class_243 class_2432) {
        double d = class_2432.field_1352;
        double d2 = class_2432.field_1351;
        double d3 = class_2432.field_1350;
        double d4 = Math.sqrt(d * d + d3 * d3);
        if (d4 < 1.0E-9) {
            return d2 > 0.0 ? -90.0f : 90.0f;
        }
        return (float)(-(class_3532.method_15349((double)d2, (double)d4) * 57.29577951308232));
    }

    private static float wrapRad(float f) {
        float f2 = (float)Math.PI * 2;
        if ((f %= f2) < 0.0f) {
            f += f2;
        }
        return f;
    }

    private static float frac(float f) {
        return f - (float)Math.floor(f);
    }

    private static boolean isFirstPerson(class_310 class_3102) {
        if (class_3102 == null) {
            return false;
        }
        try {
            return class_3102.field_1690.method_31044().method_31034();
        }
        catch (Throwable throwable) {
            block8: {
                try {
                    Object object = class_3102.field_1690.getClass().getMethod("getPerspective", new Class[0]).invoke((Object)class_3102.field_1690, new Object[0]);
                    if (object == null) break block8;
                    try {
                        Method method = object.getClass().getMethod("isFirstPerson", new Class[0]);
                        Object object2 = method.invoke(object, new Object[0]);
                        if (object2 instanceof Boolean) {
                            Boolean bl = (Boolean)object2;
                            return bl;
                        }
                    }
                    catch (Throwable throwable2) {}
                }
                catch (Throwable throwable3) {
                }
            }
            return false;
        }
    }

    static final class RenderCompat {
        boolean inited = false;
        int mode = 0;
        private Class<?> rendererClass = null;
        Method render = null;
        Method getState = null;

        RenderCompat() {
        }

        void reset() {
            this.inited = false;
            this.mode = 0;
            this.rendererClass = null;
            this.render = null;
            this.getState = null;
        }
    }

    static final class ParrotWingCompat {
        boolean inited = false;
        private Class<?> entityClass = null;
        Field maxWingDeviation = null;
        Field prevMaxWingDeviation = null;
        Field flapAngle = null;
        Field prevFlapAngle = null;
        Field flapProgress = null;
        Field prevFlapProgress = null;
        Method updateLimbs = null;

        ParrotWingCompat() {
        }

        void reset() {
            this.inited = false;
            this.entityClass = null;
            this.maxWingDeviation = null;
            this.prevMaxWingDeviation = null;
            this.flapAngle = null;
            this.prevFlapAngle = null;
            this.flapProgress = null;
            this.prevFlapProgress = null;
            this.updateLimbs = null;
        }
    }

    static final class BatRoostCompat {
        private boolean inited = false;
        private Class<?> entityClass = null;
        private Method setRoosting = null;
        private Method setHanging = null;
        private Field roosting = null;
        private Field hanging = null;

        BatRoostCompat() {
        }

        void reset() {
            this.inited = false;
            this.entityClass = null;
            this.setRoosting = null;
            this.setHanging = null;
            this.roosting = null;
            this.hanging = null;
        }

        void forceFlying(class_1420 class_14202) {
            if (class_14202 == null) {
                return;
            }
            try {
                class_14202.method_6449(false);
            }
            catch (Throwable throwable) {
            }
        }
    }

    static final class FoxStateCompat {
        int mode = 0;
        private long modeSinceMs = 0L;
        private long lastMoveMs = 0L;
        private long lastIdleMs = 0L;
        private class_243 wanderTarget = null;
        private long nextWanderMs = 0L;
        private Random rnd = new Random(12648430L);

        FoxStateCompat() {
        }

        void forceSleep(class_4019 class_40192, class_2350 class_23502) {
            if (class_40192 == null) {
                return;
            }
            try {
                class_40192.method_18294(false);
                FoxStateCompat.setSleeping(class_40192, true);
                class_40192.method_18297(false);
                class_40192.method_18299(false);
            }
            catch (Throwable throwable) {
            }
        }

        private static void setSleeping(class_4019 class_40192, boolean bl) {
            try {
                ((IFoxEntity)class_40192).celebression$setSleeping(bl);
            }
            catch (Throwable throwable) {
            }
        }

        void reset() {
            this.mode = 0;
            this.modeSinceMs = 0L;
            this.lastMoveMs = 0L;
            this.lastIdleMs = 0L;
            this.wanderTarget = null;
            this.nextWanderMs = 0L;
            this.rnd = new Random(0xC0FFEEL ^ System.nanoTime());
        }

        void update(class_1657 class_16572, boolean bl, long l) {
            if (this.lastMoveMs == 0L) {
                this.lastMoveMs = l;
            }
            if (this.lastIdleMs == 0L) {
                this.lastIdleMs = l;
            }
            if (bl) {
                this.lastMoveMs = l;
            } else {
                this.lastIdleMs = l;
            }
            long l2 = l - this.lastMoveMs;
            long l3 = l - this.lastIdleMs;
            int n = l2 > 18000L ? 2 : (l2 > 4500L ? 1 : 0);
            if (this.mode == 2 && bl && l3 > 650L) {
                n = 0;
            }
            if (this.mode == 1 && bl && l3 > 450L) {
                n = 0;
            }
            if (this.modeSinceMs == 0L) {
                this.modeSinceMs = l;
            }
            long l4 = l - this.modeSinceMs;
            if (n != this.mode) {
                long l5;
                long l6 = l5 = this.mode == 2 ? 900L : 650L;
                if (l4 > l5) {
                    this.mode = n;
                    this.modeSinceMs = l;
                    this.wanderTarget = null;
                    this.nextWanderMs = 0L;
                }
            }
            if (this.mode == 1) {
                if (this.nextWanderMs == 0L) {
                    this.nextWanderMs = l + 12000L + (long)(this.rnd.nextDouble() * 9000.0);
                }
                if (this.wanderTarget == null && l > this.nextWanderMs) {
                    this.nextWanderMs = l;
                }
            }
        }

        void applySittingFlag(class_4019 class_40192) {
            if (class_40192 == null) {
                return;
            }
            try {
                class_40192.method_18294(true);
                class_40192.method_18297(false);
                class_40192.method_18299(false);
                long l = System.currentTimeMillis() - this.lastMoveMs;
                FoxStateCompat.setSleeping(class_40192, l > 38000L);
            }
            catch (Throwable throwable) {
            }
        }

        void applyMovingFlags(class_4019 class_40192) {
            if (class_40192 == null) {
                return;
            }
            try {
                long l;
                class_40192.method_18294(false);
                FoxStateCompat.setSleeping(class_40192, false);
                boolean bl = false;
                if (this.mode == 1 && (l = System.currentTimeMillis() - this.modeSinceMs) > 8000L && l % 24000L < 1300L) {
                    bl = this.rnd.nextDouble() < 0.35;
                }
                class_40192.method_18297(bl);
                class_40192.method_18299(false);
            }
            catch (Throwable throwable) {
            }
        }
    }

    static final class FoxLimbCompat {
        FoxLimbCompat() {
        }

        void reset() {
        }

        void apply(class_4019 class_40192, class_243 class_2432, class_243 class_2433, float f, boolean bl, boolean bl2, float f2) {
            if (class_40192 == null) {
                return;
            }
            try {
                class_40192.field_6014 = class_2432.field_1352;
                class_40192.field_6036 = class_2432.field_1351;
                class_40192.field_5969 = class_2432.field_1350;
            }
            catch (Throwable throwable) {
            }
            try {
                class_40192.method_24830(bl);
            }
            catch (Throwable throwable) {
            }
            try {
                class_40192.method_29242(bl2);
            }
            catch (Throwable throwable) {
            }
        }
    }

    static final class WalkerLimbCompat {
        WalkerLimbCompat() {
        }

        void reset() {
        }

        void apply(class_1309 class_13092, boolean bl, float f) {
            if (class_13092 == null) {
                return;
            }
            try {
                class_13092.method_29242(bl);
            }
            catch (Throwable throwable) {
            }
        }
    }

    static final class PufferStateCompat {
        PufferStateCompat() {
        }

        void reset() {
        }

        void apply(class_1454 class_14542, int n) {
            if (class_14542 == null) {
                return;
            }
            try {
                class_14542.method_6596(class_3532.method_15340((int)n, (int)0, (int)2));
            }
            catch (Throwable throwable) {
            }
        }
    }

    static final class Illusion {
        class_1453 entity;
        float seedA;
        float seedB;
        float phase;
        float spinMul;
        float u;
        float v;
        class_243 fly;
        class_243 pos;
        class_243 prevPos;
        class_243 vel;
        boolean hasPos;
        boolean burst;
        long burstMs;
        float layer;
        float ang0;
        class_243 lastBodyPoint;

        Illusion() {
        }
    }

    static final class TextureSwapVcp
    implements class_4597 {
        final class_4597 parent;
        final class_2960 tex;
        final Map<class_1921, class_1921> cache = new IdentityHashMap<class_1921, class_1921>();

        TextureSwapVcp(class_4597 class_45972, class_2960 class_29602) {
            this.parent = class_45972;
            this.tex = class_29602;
        }

        public class_4588 getBuffer(class_1921 class_19212) {
            if (class_19212 == null) {
                return this.parent.getBuffer(null);
            }
            class_1921 class_19213 = this.cache.get(class_19212);
            if (class_19213 == null) {
                class_19213 = TextureSwapVcp.trySwap(class_19212, this.tex);
                this.cache.put(class_19212, class_19213);
            }
            return this.parent.getBuffer(class_19213);
        }

        private static class_1921 trySwap(class_1921 class_19212, class_2960 class_29602) {
            boolean bl;
            String string;
            if (class_19212 == null || class_29602 == null) {
                return class_19212;
            }
            try {
                string = class_19212.toString().toLowerCase();
            }
            catch (Throwable throwable) {
                string = "";
            }
            boolean bl2 = bl = string.contains("entity") || string.contains("cutout") || string.contains("translucent") || string.contains("no_cull") || string.contains("nocull");
            if (!bl) {
                return class_19212;
            }
            try {
                return class_1921.method_23578((class_2960)class_29602);
            }
            catch (Throwable throwable) {
                try {
                    return class_1921.method_23576((class_2960)class_29602);
                }
                catch (Throwable throwable2) {
                    try {
                        return class_1921.method_23580((class_2960)class_29602);
                    }
                    catch (Throwable throwable3) {
                        return class_19212;
                    }
                }
            }
        }
    }
}
