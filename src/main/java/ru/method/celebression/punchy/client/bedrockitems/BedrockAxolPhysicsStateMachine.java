package ru.method.celebression.punchy.client.bedrockitems;

import java.util.EnumMap;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemDefinition;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;

final class BedrockAxolPhysicsStateMachine {
    private static final float BASE_DT = 0.05f;
    private static final float TICK_TIME_SCALE = 2.16f;
    private static final float FALL_INTENSITY = 0.24f;
    private static final float FALL_GRAVITY = 0.04f;
    private static final float FALL_DAMPING = 0.85f;
    private final EnumMap<class_1306, ArmState> states = new EnumMap(class_1306.class);

    BedrockAxolPhysicsStateMachine() {
    }

    void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            for (class_1306 class_13063 : class_1306.values()) {
                ArmState armState = this.states.computeIfAbsent(class_13063, class_13062 -> new ArmState());
                class_1799 class_17992 = BedrockAxolPhysicsStateMachine.resolveStack(class_3102, class_13063);
                boolean bl = armState.resolveEnabled(class_17992);
                armState.tick(class_3102, bl);
            }
        } else {
            for (class_1306 class_13064 : class_1306.values()) {
                this.states.computeIfAbsent(class_13064, class_13062 -> new ArmState()).tickDisabled();
            }
        }
    }

    FeedbackPose getPose(class_1306 class_13062, float f) {
        if (class_13062 == null) {
            return null;
        }
        ArmState armState = this.states.get(class_13062);
        return armState == null ? null : armState.sample(class_13062, f);
    }

    private static class_1799 resolveStack(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
            if (class_13062 == class_1306.field_6183) {
                return bl ? class_3102.field_1724.method_6047() : class_3102.field_1724.method_6079();
            }
            return bl ? class_3102.field_1724.method_6079() : class_3102.field_1724.method_6047();
        }
        return class_1799.field_8037;
    }

    static final class ArmState {
        private float weight;
        private float fall;
        private float fallSpeed;
        private float fallFastGate;
        private float wobbleTime;
        private float prevWeight;
        private float prevFall;
        private float prevFallSpeed;
        private float prevFallFastGate;
        private float prevArmRaiseGate;
        private float prevLandingPunch;
        private float prevWobbleTime;
        private float prevWalkWeight;
        private float prevWalkPhase;
        private float prevCamLat;
        private float prevCamVert;
        private float prevPitchVisual;
        private float lastVy;
        private float armRaiseGate;
        private float landingPunch;
        private float walkWeight;
        private float walkPhase;
        private float camLat;
        private float camLatSpeed;
        private float camVert;
        private float camVertSpeed;
        private float pitchVisual;
        private float prevYaw;
        private float prevPitch;
        private boolean cameraPrimed;
        private int airTicks;
        private boolean prevOnGround;
        private class_1799 lastResolvedStack = class_1799.field_8037;
        private boolean lastResolvedEnabled;

        ArmState() {
        }

        boolean resolveEnabled(class_1799 class_17992) {
            if (class_17992 == null) {
                class_17992 = class_1799.field_8037;
            }
            if (!this.lastResolvedStack.method_7960() && class_1799.method_31577((class_1799)this.lastResolvedStack, (class_1799)class_17992)) {
                return this.lastResolvedEnabled;
            }
            BedrockItemDefinition bedrockItemDefinition = BedrockItemSystem.get().resolveDefinition(class_17992);
            this.lastResolvedEnabled = bedrockItemDefinition != null && bedrockItemDefinition.isPhysicsFeedbackEnabled();
            this.lastResolvedStack = class_17992.method_7972();
            return this.lastResolvedEnabled;
        }

        void tick(class_310 class_3102, boolean bl) {
            this.prevWeight = this.weight;
            this.prevFall = this.fall;
            this.prevFallSpeed = this.fallSpeed;
            this.prevFallFastGate = this.fallFastGate;
            this.prevArmRaiseGate = this.armRaiseGate;
            this.prevLandingPunch = this.landingPunch;
            this.prevWobbleTime = this.wobbleTime;
            this.prevWalkWeight = this.walkWeight;
            this.prevWalkPhase = this.walkPhase;
            this.prevCamLat = this.camLat;
            this.prevCamVert = this.camVert;
            this.prevPitchVisual = this.pitchVisual;
            class_746 class_7462 = class_3102.field_1724;
            float f = (float)class_7462.method_18798().field_1351;
            float f2 = (float)class_7462.method_18798().field_1352;
            float f3 = (float)class_7462.method_18798().field_1350;
            float f4 = class_7462.method_36454();
            float f5 = class_7462.method_36455();
            boolean bl2 = class_7462.method_24828();
            float f6 = class_7462.field_6017;
            if (!bl) {
                this.tickDisabled();
            } else {
                float f7;
                float f8;
                float f9;
                float f10;
                this.weight = class_3532.method_16439((float)ArmState.rate(0.42f), (float)this.weight, (float)1.0f);
                this.fallSpeed += -f * 0.24f * 2.16f;
                this.fallSpeed -= 0.04f * this.fall * 2.16f;
                this.fallSpeed *= (float)Math.pow(0.85f, 2.16f);
                this.fall += this.fallSpeed * 2.16f;
                this.fall = class_3532.method_15363((float)this.fall, (float)-1.8f, (float)2.4f);
                this.fallFastGate = !bl2 && -f > 0.08f ? (this.fallFastGate += 0.2592f) : (this.fallFastGate -= 0.216f);
                this.fallFastGate = class_3532.method_15363((float)this.fallFastGate, (float)0.0f, (float)1.0f);
                float f11 = !bl2 && f6 >= 5.0f ? 1.0f : 0.0f;
                this.armRaiseGate = ArmState.moveTowards(this.armRaiseGate, f11, 0.06666667f);
                if (!bl2) {
                    this.airTicks = Math.min(this.airTicks + 1, 200);
                }
                float f12 = class_3532.method_15355((float)(f2 * f2 + f3 * f3));
                float f13 = bl2 && f12 > 0.02f ? class_3532.method_15363((float)((f12 - 0.02f) / 0.1f), (float)0.0f, (float)1.0f) : 0.0f;
                this.walkWeight = class_3532.method_16439((float)ArmState.rate(bl2 ? 0.25f : 0.4f), (float)this.walkWeight, (float)f13);
                this.walkPhase += 0.108f * (1.2f + this.walkWeight * 3.2f);
                if (!this.cameraPrimed) {
                    this.prevYaw = f4;
                    this.prevPitch = f5;
                    this.cameraPrimed = true;
                }
                float f14 = class_3532.method_15393((float)(f4 - this.prevYaw));
                float f15 = f5 - this.prevPitch;
                this.prevYaw = f4;
                this.prevPitch = f5;
                float f16 = Math.abs(f14);
                float f17 = Math.abs(f15);
                float f18 = f16 > 0.9f ? f14 : 0.0f;
                float f19 = f17 > 0.9f ? f15 : 0.0f;
                float f20 = 0.0224f;
                float f21 = 0.108f;
                float f22 = class_3532.method_15363((float)(-f18 * f20), (float)-1.25f, (float)1.25f);
                float f23 = class_3532.method_15363((float)(-f19 * f20), (float)-1.0f, (float)1.0f);
                this.camLatSpeed += f22 * f21 * 2.16f;
                this.camVertSpeed += f23 * f21 * 2.16f;
                this.camLatSpeed -= 0.06f * this.camLat * 2.16f;
                this.camVertSpeed -= 0.06f * this.camVert * 2.16f;
                this.camLatSpeed *= (float)Math.pow(0.85f, 2.16f);
                this.camVertSpeed *= (float)Math.pow(0.85f, 2.16f);
                this.camLat += this.camLatSpeed * 2.16f;
                this.camVert += this.camVertSpeed * 2.16f;
                this.camLat = class_3532.method_15363((float)this.camLat, (float)-1.25f, (float)1.25f);
                this.camVert = class_3532.method_15363((float)this.camVert, (float)-1.0f, (float)1.0f);
                boolean bl3 = false;
                if (!this.prevOnGround && bl2) {
                    f10 = 0.95f;
                    f9 = class_3532.method_15363((float)(((float)this.airTicks - 3.0f) / 12.0f), (float)0.0f, (float)1.0f);
                    f8 = 0.5f + 0.5f * f9;
                    f7 = f10 * f8;
                    this.fallSpeed -= f7 * 1.65f;
                    this.fall -= f7 * 0.45f;
                    this.fallSpeed *= 0.9f;
                    this.landingPunch = 1.0f;
                    bl3 = true;
                    this.airTicks = 0;
                } else if (bl2) {
                    this.airTicks = 0;
                }
                if (!bl3) {
                    this.landingPunch = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.landingPunch, (float)0.0f);
                }
                f10 = class_3532.method_15363((float)(this.fall * 0.4f), (float)0.0f, (float)2.0f) * (this.fallFastGate * this.fallFastGate);
                this.wobbleTime += 0.108f * (1.0f + f10 * 1.8f);
                f9 = class_3532.method_15363((float)this.camVert, (float)-1.0f, (float)1.0f);
                f8 = ArmState.easeInOutSigned(f9);
                f7 = class_3532.method_15363((float)(this.fall - f8 * 0.9f), (float)-2.4f, (float)2.4f);
                float f24 = class_3532.method_15363((float)(16.8f * f7 * (1.0f - this.landingPunch * 1.25f) - 4.14f * this.landingPunch), (float)-44.0f, (float)44.0f) * this.weight;
                float f25 = !bl2 && f6 >= 5.0f ? ArmState.rate(0.07f) : ArmState.rate(0.16f);
                this.pitchVisual = class_3532.method_16439((float)f25, (float)this.pitchVisual, (float)f24);
                this.prevOnGround = bl2;
                this.lastVy = f;
            }
        }

        void tickDisabled() {
            this.prevWeight = this.weight;
            this.prevFall = this.fall;
            this.prevFallSpeed = this.fallSpeed;
            this.prevFallFastGate = this.fallFastGate;
            this.prevArmRaiseGate = this.armRaiseGate;
            this.prevLandingPunch = this.landingPunch;
            this.prevWobbleTime = this.wobbleTime;
            this.prevWalkWeight = this.walkWeight;
            this.prevWalkPhase = this.walkPhase;
            this.prevCamLat = this.camLat;
            this.prevCamVert = this.camVert;
            this.prevPitchVisual = this.pitchVisual;
            this.weight = class_3532.method_16439((float)ArmState.rate(0.25f), (float)this.weight, (float)0.0f);
            this.fallSpeed = class_3532.method_16439((float)ArmState.rate(0.25f), (float)this.fallSpeed, (float)0.0f);
            this.fall = class_3532.method_16439((float)ArmState.rate(0.25f), (float)this.fall, (float)0.0f);
            this.fallFastGate = class_3532.method_16439((float)ArmState.rate(0.2f), (float)this.fallFastGate, (float)0.0f);
            this.armRaiseGate = ArmState.moveTowards(this.armRaiseGate, 0.0f, 0.06666667f);
            this.landingPunch = class_3532.method_16439((float)ArmState.rate(0.45f), (float)this.landingPunch, (float)0.0f);
            this.walkWeight = class_3532.method_16439((float)ArmState.rate(0.4f), (float)this.walkWeight, (float)0.0f);
            this.walkPhase += 0.108f;
            this.wobbleTime += 0.108f;
            this.camLatSpeed = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camLatSpeed, (float)0.0f);
            this.camLat = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camLat, (float)0.0f);
            this.camVertSpeed = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camVertSpeed, (float)0.0f);
            this.camVert = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camVert, (float)0.0f);
            this.pitchVisual = class_3532.method_16439((float)ArmState.rate(0.2f), (float)this.pitchVisual, (float)0.0f);
            this.airTicks = 0;
            this.cameraPrimed = false;
        }

        FeedbackPose sample(class_1306 class_13062, float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            float f3 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevWeight, (float)this.weight), (float)0.0f, (float)1.0f);
            float f4 = class_3532.method_16439((float)f2, (float)this.prevFall, (float)this.fall);
            float f5 = class_3532.method_16439((float)f2, (float)this.prevFallFastGate, (float)this.fallFastGate);
            float f6 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevArmRaiseGate, (float)this.armRaiseGate), (float)0.0f, (float)1.0f);
            float f7 = ArmState.smoothStep01(f6);
            float f8 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevLandingPunch, (float)this.landingPunch), (float)0.0f, (float)1.0f);
            float f9 = class_3532.method_16439((float)f2, (float)this.prevWobbleTime, (float)this.wobbleTime);
            float f10 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevWalkWeight, (float)this.walkWeight), (float)0.0f, (float)1.0f);
            float f11 = class_3532.method_16439((float)f2, (float)this.prevWalkPhase, (float)this.walkPhase);
            float f12 = class_3532.method_16439((float)f2, (float)this.prevCamLat, (float)this.camLat);
            float f13 = class_3532.method_16439((float)f2, (float)this.prevCamVert, (float)this.camVert);
            float f14 = class_3532.method_16439((float)f2, (float)this.prevPitchVisual, (float)this.pitchVisual);
            float f15 = class_3532.method_15363((float)(f4 * 0.4f), (float)0.0f, (float)2.0f) * (f5 * f5 * f5);
            float f16 = (float)Math.sin(f11) * 0.70686f * f10;
            float f17 = (float)Math.sin(f11 + 0.65f) * 7.084f * f10;
            float f18 = class_3532.method_15363((float)(f12 / 1.25f), (float)-1.0f, (float)1.0f);
            float f19 = ArmState.easeInOutSigned(f18) * 1.25f;
            float f20 = class_3532.method_15363((float)f13, (float)-1.0f, (float)1.0f);
            float f21 = ArmState.easeInOutSigned(f20);
            float f22 = class_3532.method_15363((float)(f4 - f21 * 0.9f), (float)-2.4f, (float)2.4f);
            float f23 = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
            float f24 = f19 * 0.42f * f3 * f23;
            float f25 = (f22 * 0.38f - f8 * 0.85f) * f3 + f16 - f21 * 0.34f * f3;
            float f26 = f22 * 0.44f * f3;
            float f27 = class_13062 == class_1306.field_6182 ? 1.0f : -1.0f;
            float f28 = f27 * (f15 * 3.0f) * f3;
            float f29 = (float)Math.sin(f9 * 7.2f) * f15 * 0.004f * f3;
            float f30 = 1.0f + 0.02f * f22 * f3 + f29;
            float f31 = 1.0f - 0.02f * f22 * f3 - f29 * 0.25f;
            f30 = class_3532.method_15363((float)f30, (float)0.97f, (float)1.05f);
            f31 = class_3532.method_15363((float)f31, (float)0.95f, (float)1.03f);
            return new FeedbackPose(f24, f25, f26, 0.0f, f7, f8 * f3, f17, f14, f28, f31, f30, f31, f15 * f3);
        }

        private static float rate(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)0.999f);
            return 1.0f - (float)Math.pow(1.0f - f2, 2.16f);
        }

        private static float easeOutBack(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f) - 1.0f;
            float f3 = 2.6f;
            float f4 = f3 + 1.0f;
            return 1.0f + f4 * f2 * f2 * f2 + f3 * f2 * f2;
        }

        private static float smoothStep01(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            return f2 * f2 * (3.0f - 2.0f * f2);
        }

        private static float moveTowards(float f, float f2, float f3) {
            float f4 = Math.abs(f3);
            return f < f2 ? Math.min(f + f4, f2) : Math.max(f - f4, f2);
        }

        private static float easeInOutSigned(float f) {
            float f2 = class_3532.method_15363((float)Math.abs(f), (float)0.0f, (float)1.0f);
            float f3 = 0.5f - 0.5f * (float)Math.cos(Math.PI * (double)f2);
            return Math.signum(f) * f3;
        }
    }

    record FeedbackPose(float translateXUnits, float translateYUnits, float translateZUnits, float highFallLiftUnits, float armRaiseGate, float landingPunch, float walkPitchDeg, float pitchDeg, float rollDeg, float scaleX, float scaleY, float scaleZ, float impactWave) {
        boolean isIdentity() {
            return Math.abs(this.translateXUnits) < 1.0E-4f && Math.abs(this.translateYUnits) < 1.0E-4f && Math.abs(this.translateZUnits) < 1.0E-4f && Math.abs(this.highFallLiftUnits) < 1.0E-4f && Math.abs(this.armRaiseGate) < 1.0E-4f && Math.abs(this.landingPunch) < 1.0E-4f && Math.abs(this.walkPitchDeg) < 1.0E-4f && Math.abs(this.pitchDeg) < 1.0E-4f && Math.abs(this.rollDeg) < 1.0E-4f && Math.abs(this.scaleX - 1.0f) < 1.0E-4f && Math.abs(this.scaleY - 1.0f) < 1.0E-4f && Math.abs(this.scaleZ - 1.0f) < 1.0E-4f && Math.abs(this.impactWave) < 1.0E-4f;
        }
    }
}

