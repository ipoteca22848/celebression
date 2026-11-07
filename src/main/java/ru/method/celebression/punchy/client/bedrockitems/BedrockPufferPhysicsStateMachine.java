package ru.method.celebression.punchy.client.bedrockitems;

import java.util.EnumMap;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemDefinition;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;

final class BedrockPufferPhysicsStateMachine {
    private static final float BASE_DT = 0.05f;
    private static final float TICK_TIME_SCALE = 2.16f;
    private static final float SPRING_INPUT = 0.32f;
    private static final float SPRING_GRAVITY = 0.055f;
    private static final float SPRING_DAMPING = 0.82f;
    private final EnumMap<class_1306, ArmState> states = new EnumMap(class_1306.class);

    BedrockPufferPhysicsStateMachine() {
    }

    void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            for (class_1306 class_13063 : class_1306.values()) {
                ArmState armState = this.states.computeIfAbsent(class_13063, class_13062 -> new ArmState());
                class_1799 class_17992 = BedrockPufferPhysicsStateMachine.resolveStack(class_3102, class_13063);
                boolean bl = armState.resolveEnabled(class_3102, class_17992);
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
        private float spring;
        private float springSpeed;
        private float wobbleTime;
        private float landingPunch;
        private float finLift;
        private float walkWeight;
        private float walkPhase;
        private float camLat;
        private float camLatSpeed;
        private float camVert;
        private float camVertSpeed;
        private float highFallTiltGate;
        private float highFallPitchDrive;
        private float prevYaw;
        private float prevPitch;
        private boolean cameraPrimed;
        private float prevWeight;
        private float prevSpring;
        private float prevWobbleTime;
        private float prevLandingPunch;
        private float prevLandingPitchVisual;
        private float prevFinLift;
        private float prevWalkWeight;
        private float prevWalkPhase;
        private float prevCamLat;
        private float prevCamVert;
        private float prevHighFallTiltGate;
        private float prevHighFallPitchDrive;
        private float jumpTiltCap;
        private float prevJumpTiltCap;
        private boolean prevHighFallMode;
        private boolean prevOnGround;
        private int airTicks;
        private float landingPitchVisual;
        private class_1799 lastResolvedStack = class_1799.field_8037;
        private boolean lastResolvedEnabled;

        ArmState() {
        }

        boolean resolveEnabled(class_310 class_3102, class_1799 class_17992) {
            if (class_17992 == null) {
                class_17992 = class_1799.field_8037;
            }
            if (!this.lastResolvedStack.method_7960() && class_1799.method_31577((class_1799)this.lastResolvedStack, (class_1799)class_17992)) {
                return this.lastResolvedEnabled;
            }
            BedrockItemDefinition bedrockItemDefinition = BedrockItemSystem.get().resolveDefinition(class_17992);
            this.lastResolvedEnabled = bedrockItemDefinition != null;
            this.lastResolvedStack = class_17992.method_7972();
            return this.lastResolvedEnabled;
        }

        void tick(class_310 class_3102, boolean bl) {
            this.prevWeight = this.weight;
            this.prevSpring = this.spring;
            this.prevWobbleTime = this.wobbleTime;
            this.prevLandingPunch = this.landingPunch;
            this.prevLandingPitchVisual = this.landingPitchVisual;
            this.prevFinLift = this.finLift;
            this.prevWalkWeight = this.walkWeight;
            this.prevWalkPhase = this.walkPhase;
            this.prevCamLat = this.camLat;
            this.prevCamVert = this.camVert;
            this.prevHighFallTiltGate = this.highFallTiltGate;
            this.prevHighFallPitchDrive = this.highFallPitchDrive;
            this.prevJumpTiltCap = this.jumpTiltCap;
            if (bl && class_3102 != null && class_3102.field_1724 != null) {
                float f;
                float f2;
                float f3;
                float f4;
                class_746 class_7462 = class_3102.field_1724;
                float f5 = (float)class_7462.method_18798().field_1351;
                float f6 = (float)class_7462.method_18798().field_1352;
                float f7 = (float)class_7462.method_18798().field_1350;
                float f8 = class_7462.method_36454();
                float f9 = class_7462.method_36455();
                boolean bl2 = class_7462.method_24828();
                float f10 = class_7462.field_6017;
                boolean bl3 = class_7462.method_31549().field_7479;
                boolean bl4 = -f5 > 0.08f;
                boolean bl5 = !bl2 && !bl3;
                boolean bl6 = bl5 && (bl4 || f10 > 1.25f);
                this.weight = class_3532.method_16439((float)ArmState.rate(0.48f), (float)this.weight, (float)1.0f);
                this.springSpeed += -f5 * 0.32f * 2.16f;
                this.springSpeed -= 0.055f * this.spring * 2.16f;
                this.springSpeed *= (float)Math.pow(0.82f, 2.16f);
                this.spring += this.springSpeed * 2.16f;
                this.spring = class_3532.method_15363((float)this.spring, (float)-2.2f, (float)2.8f);
                boolean bl7 = false;
                if (!this.prevOnGround && bl2) {
                    this.landingPunch = 1.0f;
                    this.spring *= 0.5f;
                    this.springSpeed *= 0.45f;
                    this.springSpeed -= 0.55f;
                    this.spring -= 0.28f;
                    bl7 = true;
                    this.airTicks = 0;
                } else {
                    this.airTicks = !bl2 && !bl3 ? Math.min(this.airTicks + 1, 200) : 0;
                }
                if (!bl7) {
                    this.landingPunch = class_3532.method_16439((float)ArmState.rate(0.22f), (float)this.landingPunch, (float)0.0f);
                }
                float f11 = ArmState.rate(0.07f);
                float f12 = ArmState.rate(0.11f);
                this.landingPitchVisual = class_3532.method_16439((float)(this.landingPunch > this.landingPitchVisual ? f11 : f12), (float)this.landingPitchVisual, (float)this.landingPunch);
                float f13 = bl6 ? 1.0f : 0.0f;
                this.highFallTiltGate = ArmState.moveTowards(this.highFallTiltGate, f13, 0.1f);
                if (bl6) {
                    f4 = class_3532.method_15363((float)((-f5 - 0.05f) / 0.45f), (float)0.0f, (float)1.0f);
                    f3 = class_3532.method_15363((float)((f10 - 5.0f) / 7.0f), (float)0.0f, (float)1.0f);
                    f2 = Math.max(f4, f3);
                    if (!this.prevHighFallMode) {
                        f = class_3532.method_15363((float)(Math.max(0.0f, this.spring) / 2.4f), (float)0.0f, (float)1.0f);
                        this.highFallPitchDrive = Math.max(this.highFallPitchDrive, f);
                    }
                    f = f2 >= this.highFallPitchDrive ? 0.03125f : 0.008333334f;
                    this.highFallPitchDrive = ArmState.moveTowards(this.highFallPitchDrive, f2, f);
                } else {
                    this.highFallPitchDrive = ArmState.moveTowards(this.highFallPitchDrive, 0.0f, 0.06666667f);
                }
                if (bl5) {
                    f4 = Math.abs(this.spring * 11.5f);
                    this.jumpTiltCap = Math.max(this.jumpTiltCap, f4);
                } else if (!bl7) {
                    this.jumpTiltCap = class_3532.method_16439((float)ArmState.rate(0.3f), (float)this.jumpTiltCap, (float)0.0f);
                }
                f4 = bl6 && this.airTicks >= 2 ? 1.0f : 0.0f;
                f3 = f4 > this.finLift ? 0.055555556f : 0.0625f;
                this.finLift = ArmState.moveTowards(this.finLift, f4, f3);
                f2 = class_3532.method_15355((float)(f6 * f6 + f7 * f7));
                f = bl2 && f2 > 0.02f ? class_3532.method_15363((float)((f2 - 0.02f) / 0.1f), (float)0.0f, (float)1.0f) : 0.0f;
                this.walkWeight = class_3532.method_16439((float)ArmState.rate(bl2 ? 0.24f : 0.36f), (float)this.walkWeight, (float)f);
                this.walkPhase += 0.108f * (1.2f + this.walkWeight * 3.0f);
                if (!this.cameraPrimed) {
                    this.prevYaw = f8;
                    this.prevPitch = f9;
                    this.cameraPrimed = true;
                }
                float f14 = class_3532.method_15393((float)(f8 - this.prevYaw));
                float f15 = f9 - this.prevPitch;
                this.prevYaw = f8;
                this.prevPitch = f9;
                float f16 = Math.abs(f14) > 0.9f ? f14 : 0.0f;
                float f17 = Math.abs(f15) > 0.9f ? f15 : 0.0f;
                float f18 = 0.019f;
                float f19 = 0.14f;
                float f20 = class_3532.method_15363((float)(-f16 * f18), (float)-1.1f, (float)1.1f);
                float f21 = class_3532.method_15363((float)(-f17 * f18), (float)-1.0f, (float)1.0f);
                this.camLatSpeed += f20 * f19 * 2.16f;
                this.camVertSpeed += f21 * f19 * 2.16f;
                this.camLatSpeed -= 0.07f * this.camLat * 2.16f;
                this.camVertSpeed -= 0.07f * this.camVert * 2.16f;
                this.camLatSpeed *= (float)Math.pow(0.84f, 2.16f);
                this.camVertSpeed *= (float)Math.pow(0.84f, 2.16f);
                this.camLat += this.camLatSpeed * 2.16f;
                this.camVert += this.camVertSpeed * 2.16f;
                this.camLat = class_3532.method_15363((float)this.camLat, (float)-1.1f, (float)1.1f);
                this.camVert = class_3532.method_15363((float)this.camVert, (float)-1.0f, (float)1.0f);
                this.wobbleTime += 0.108f * (1.1f + Math.abs(this.spring) * 0.7f);
                this.prevOnGround = bl2;
                this.prevHighFallMode = bl6;
            } else {
                this.tickDisabled();
            }
        }

        void tickDisabled() {
            this.prevWeight = this.weight;
            this.prevSpring = this.spring;
            this.prevWobbleTime = this.wobbleTime;
            this.prevLandingPunch = this.landingPunch;
            this.prevLandingPitchVisual = this.landingPitchVisual;
            this.prevFinLift = this.finLift;
            this.prevWalkWeight = this.walkWeight;
            this.prevWalkPhase = this.walkPhase;
            this.prevCamLat = this.camLat;
            this.prevCamVert = this.camVert;
            this.prevHighFallTiltGate = this.highFallTiltGate;
            this.prevHighFallPitchDrive = this.highFallPitchDrive;
            this.prevJumpTiltCap = this.jumpTiltCap;
            this.weight = class_3532.method_16439((float)ArmState.rate(0.28f), (float)this.weight, (float)0.0f);
            this.springSpeed = class_3532.method_16439((float)ArmState.rate(0.28f), (float)this.springSpeed, (float)0.0f);
            this.spring = class_3532.method_16439((float)ArmState.rate(0.28f), (float)this.spring, (float)0.0f);
            this.landingPunch = class_3532.method_16439((float)ArmState.rate(0.24f), (float)this.landingPunch, (float)0.0f);
            this.landingPitchVisual = class_3532.method_16439((float)ArmState.rate(0.12f), (float)this.landingPitchVisual, (float)this.landingPunch);
            this.finLift = class_3532.method_16439((float)ArmState.rate(0.4f), (float)this.finLift, (float)0.0f);
            this.walkWeight = class_3532.method_16439((float)ArmState.rate(0.36f), (float)this.walkWeight, (float)0.0f);
            this.walkPhase += 0.108f;
            this.camLatSpeed = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camLatSpeed, (float)0.0f);
            this.camVertSpeed = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camVertSpeed, (float)0.0f);
            this.camLat = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camLat, (float)0.0f);
            this.camVert = class_3532.method_16439((float)ArmState.rate(0.35f), (float)this.camVert, (float)0.0f);
            this.highFallTiltGate = ArmState.moveTowards(this.highFallTiltGate, 0.0f, 0.06666667f);
            this.highFallPitchDrive = ArmState.moveTowards(this.highFallPitchDrive, 0.0f, 0.06666667f);
            this.jumpTiltCap = class_3532.method_16439((float)ArmState.rate(0.32f), (float)this.jumpTiltCap, (float)0.0f);
            this.wobbleTime += 0.108f;
            this.airTicks = 0;
            this.prevOnGround = false;
            this.cameraPrimed = false;
            this.prevHighFallMode = false;
        }

        FeedbackPose sample(class_1306 class_13062, float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            float f3 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevWeight, (float)this.weight), (float)0.0f, (float)1.0f);
            float f4 = class_3532.method_16439((float)f2, (float)this.prevSpring, (float)this.spring);
            float f5 = class_3532.method_16439((float)f2, (float)this.prevWobbleTime, (float)this.wobbleTime);
            float f6 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevLandingPunch, (float)this.landingPunch), (float)0.0f, (float)1.0f);
            float f7 = ArmState.smoothStep01(class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevLandingPitchVisual, (float)this.landingPitchVisual), (float)0.0f, (float)1.0f));
            float f8 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevFinLift, (float)this.finLift), (float)0.0f, (float)1.0f);
            f8 = ArmState.smootherStep01(f8);
            float f9 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevWalkWeight, (float)this.walkWeight), (float)0.0f, (float)1.0f);
            float f10 = class_3532.method_16439((float)f2, (float)this.prevWalkPhase, (float)this.walkPhase);
            float f11 = class_3532.method_16439((float)f2, (float)this.prevCamLat, (float)this.camLat);
            float f12 = class_3532.method_16439((float)f2, (float)this.prevCamVert, (float)this.camVert);
            float f13 = (float)Math.sin(f5 * 8.2f) * 0.012f * f3;
            float f14 = (float)Math.sin(f10) * 0.5f * f9;
            float f15 = (float)Math.sin(f10 + 0.75f) * 3.0f * f9;
            float f16 = class_3532.method_15363((float)(f11 / 1.1f), (float)-1.0f, (float)1.0f);
            float f17 = ArmState.easeInOutSigned(f16) * 1.1f;
            float f18 = class_3532.method_15363((float)f12, (float)-1.0f, (float)1.0f);
            float f19 = ArmState.easeInOutSigned(f18);
            float f20 = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
            float f21 = f17 * 0.24f * f3 * f20;
            float f22 = (f4 * 0.62f - f6 * 0.92f) * f3 + f14 - f19 * 0.2f * f3;
            float f23 = f4 * 0.26f * f3;
            float f24 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevHighFallTiltGate, (float)this.highFallTiltGate), (float)0.0f, (float)1.0f);
            float f25 = class_3532.method_15363((float)class_3532.method_16439((float)f2, (float)this.prevHighFallPitchDrive, (float)this.highFallPitchDrive), (float)0.0f, (float)1.0f);
            float f26 = Math.max(0.0f, class_3532.method_16439((float)f2, (float)this.prevJumpTiltCap, (float)this.jumpTiltCap));
            float f27 = class_3532.method_16439((float)f24, (float)1.0f, (float)1.22f);
            float f28 = Math.max(0.0f, f4);
            float f29 = (f28 * 11.5f + f25 * 5.0f) * f27 * f3;
            float f30 = (f29 *= 1.0f - f7) - f7 * 30.0f * f3 - f19 * 8.0f * f3;
            float f31 = Math.max(2.0f, Math.max(8.0f + f7 * 15.0f, f26));
            float f32 = class_3532.method_15363((float)f30, (float)(-f31), (float)f31);
            float f33 = (float)Math.sin(f5 * 1.6f) * 1.8f * f3 + f15 + f17 * 5.0f * f3;
            return new FeedbackPose(f21, f22, f23, f32, f33, 1.0f, 1.0f, 1.0f, f8, Math.abs(f4) * f3);
        }

        private static float rate(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)0.999f);
            return 1.0f - (float)Math.pow(1.0f - f2, 2.16f);
        }

        private static float easeInOutSigned(float f) {
            float f2 = class_3532.method_15363((float)Math.abs(f), (float)0.0f, (float)1.0f);
            float f3 = 0.5f - 0.5f * (float)Math.cos(Math.PI * (double)f2);
            return Math.signum(f) * f3;
        }

        private static float moveTowards(float f, float f2, float f3) {
            float f4 = Math.abs(f3);
            return f < f2 ? Math.min(f + f4, f2) : Math.max(f - f4, f2);
        }

        private static float smoothStep01(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            return f2 * f2 * (3.0f - 2.0f * f2);
        }

        private static float smootherStep01(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            return f2 * f2 * f2 * (f2 * (f2 * 6.0f - 15.0f) + 10.0f);
        }
    }

    record FeedbackPose(float translateXUnits, float translateYUnits, float translateZUnits, float pitchDeg, float rollDeg, float scaleX, float scaleY, float scaleZ, float finLift, float impactWave) {
        boolean isIdentity() {
            return Math.abs(this.translateXUnits) < 1.0E-4f && Math.abs(this.translateYUnits) < 1.0E-4f && Math.abs(this.translateZUnits) < 1.0E-4f && Math.abs(this.pitchDeg) < 1.0E-4f && Math.abs(this.rollDeg) < 1.0E-4f && Math.abs(this.scaleX - 1.0f) < 1.0E-4f && Math.abs(this.scaleY - 1.0f) < 1.0E-4f && Math.abs(this.scaleZ - 1.0f) < 1.0E-4f && Math.abs(this.finLift) < 1.0E-4f && Math.abs(this.impactWave) < 1.0E-4f;
        }
    }
}

