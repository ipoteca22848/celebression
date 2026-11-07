package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Locale;
import net.minecraft.class_10255;
import net.minecraft.class_1306;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.state.WalkOnWaterStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class SwimmingStateMachine {
    private static final int MIN_FALL_HEIGHT_BLOCKS = 5;
    private static final int MAX_WATER_SCAN_BLOCKS = 64;
    private static final float FALLING_IN_WATER_BLEND_IN_SECONDS = 0.64f;
    private static final float SWIM_NO_SHIFT_SPEED_MULTIPLIER = 0.6f;
    private boolean wasSwimming;
    private boolean wasFallingInWater;
    private boolean suppressSwimThisTick;
    private class_1306 lastSwimArm;
    private class_1306 suppressedMainArm;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            if (!PunchyConfig.enableSwimAnimation()) {
                this.stopAllSwimOverlays();
            } else if (class_3102.field_1724.method_5854() instanceof class_10255) {
                this.stopAllSwimOverlays();
            } else {
                this.suppressSwimArmForMainAnimation();
                this.suppressSwimThisTick = false;
                boolean bl = class_3102.field_1724.method_5869();
                boolean bl2 = !bl && this.isAboveWaterSwimming(class_3102);
                WalkOnWaterStateMachine walkOnWaterStateMachine = PunchyClient.getWalkOnWaterStateMachine();
                if (bl2 && walkOnWaterStateMachine != null && walkOnWaterStateMachine.isCurrentPoseOwnedByWalkOnWater()) {
                    bl2 = false;
                }
                boolean bl3 = class_3102.field_1724.method_5799();
                boolean bl4 = class_3102.field_1724.method_24828();
                boolean bl5 = this.hasSolidSupportAtFeet(class_3102);
                boolean bl6 = class_3102.field_1690 != null && class_3102.field_1690.field_1894.method_1434();
                boolean bl7 = class_3102.field_1690 != null && class_3102.field_1690.field_1913.method_1434();
                boolean bl8 = class_3102.field_1690 != null && class_3102.field_1690.field_1849.method_1434();
                float f = class_3102.field_1724.field_6212;
                if (Math.abs(f) > 0.001f) {
                    bl7 = f > 0.0f;
                    bl8 = f < 0.0f;
                }
                boolean bl9 = bl6 || bl7 || bl8;
                this.handleFallingInWater(class_3102, bl3, bl4);
                if (!bl && !bl2) {
                    if (this.wasSwimming) {
                        this.stopSwim();
                        this.wasSwimming = false;
                        this.lastSwimArm = null;
                    }
                } else {
                    boolean bl10 = class_3102.field_1724.method_5681();
                    if ((bl10 || !bl5) && bl9 && !this.suppressSwimThisTick) {
                        AnimationClip animationClip;
                        AnimationClip animationClip2 = animationClip = bl2 ? this.resolveAboveWaterClip() : PunchyAnimationManager.SWIMING_CLIP;
                        if (this.playOverlay(animationClip, bl6, bl7, bl8, false)) {
                            this.wasSwimming = true;
                        }
                    } else if (this.wasSwimming) {
                        this.stopSwim();
                        this.wasSwimming = false;
                        this.lastSwimArm = null;
                    }
                }
            }
        }
    }

    private boolean playOverlay(AnimationClip animationClip, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        class_1306 class_13062;
        boolean bl5;
        boolean bl6;
        boolean bl7;
        if (animationClip == null) {
            return false;
        }
        PunchyAnimationManager.SWIM_HANDLER.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
        class_1306 class_13063 = this.resolveMainArm();
        boolean bl8 = this.isArmBlockedBySwimFlag(class_310.method_1551(), class_1306.field_6182) || BedrockItemAnimationManager.isSwimSuppressed(class_1306.field_6182) || ItemAnimationManager.isSwimSuppressed(class_1306.field_6182);
        boolean bl9 = bl7 = this.isArmBlockedBySwimFlag(class_310.method_1551(), class_1306.field_6183) || BedrockItemAnimationManager.isSwimSuppressed(class_1306.field_6183) || ItemAnimationManager.isSwimSuppressed(class_1306.field_6183);
        if (bl8 && bl7) {
            return false;
        }
        boolean bl10 = bl6 = bl || bl2 && bl3;
        if (bl8 || bl7) {
            bl6 = false;
        }
        PunchyAnimationManager.SWIM_HANDLER.setIgnoreDualHanded(!bl6 || class_13063 != null);
        if (class_13063 == null && this.isMainDualHanded() && !this.isDualHandedEquipPoseActive()) {
            if (this.wasSwimming) {
                this.stopSwimWithoutClear();
                this.wasSwimming = false;
                this.lastSwimArm = null;
            }
            return false;
        }
        float f = 0.0f;
        if (PunchyAnimationManager.SWIM_HANDLER.isPlaying() && PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() == animationClip) {
            f = PunchyAnimationManager.SWIM_HANDLER.getCurrentTime();
        }
        boolean bl11 = bl5 = bl6 && PunchyAnimationManager.isDualHandedActive(class_310.method_1551(), animationClip, f);
        if (class_13063 != null) {
            bl5 = false;
            class_13062 = this.opposite(class_13063);
            if (class_13062 == class_1306.field_6182 && bl8 && !bl7) {
                class_13062 = class_1306.field_6183;
            } else if (class_13062 == class_1306.field_6183 && bl7 && !bl8) {
                class_13062 = class_1306.field_6182;
            } else if (class_13062 == class_1306.field_6182 && bl8 || class_13062 == class_1306.field_6183 && bl7) {
                return false;
            }
        } else {
            class_1306 class_13064 = class_13062 = bl5 ? null : this.resolveSwimArm(bl, bl2, bl3, class_13063);
            if (class_13062 == null && bl8 ^ bl7) {
                class_1306 class_13065 = class_13062 = bl8 ? class_1306.field_6183 : class_1306.field_6182;
            }
            if (!bl5) {
                if (this.lastSwimArm == null && class_13062 != null) {
                    class_1306 class_13066 = this.opposite(class_13062);
                    if (class_13066 == class_13063) {
                        PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_13066);
                    } else {
                        PunchyAnimationManager.SWIM_HANDLER.requestClearArm(class_13066);
                    }
                } else if (this.lastSwimArm != null && class_13062 != null && this.lastSwimArm != class_13062) {
                    if (this.lastSwimArm == class_13063) {
                        PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(this.lastSwimArm);
                    } else {
                        PunchyAnimationManager.SWIM_HANDLER.requestClearArm(this.lastSwimArm);
                    }
                }
            }
        }
        if (class_13062 == null) {
            if (bl8) {
                PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_1306.field_6182);
            }
            if (bl7) {
                PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_1306.field_6183);
            }
        } else if (class_13062 == class_1306.field_6182 && bl8) {
            class_13062 = bl7 ? null : class_1306.field_6183;
        } else if (class_13062 == class_1306.field_6183 && bl7) {
            class_1306 class_13067 = class_13062 = bl8 ? null : class_1306.field_6182;
        }
        if (class_13062 == null && bl8 && bl7) {
            return false;
        }
        this.lastSwimArm = class_13062;
        PunchyAnimationManager.SWIM_HANDLER.setActiveArm(class_13062);
        PunchyAnimationManager.SWIM_HANDLER.setLooping(true);
        PunchyAnimationManager.SWIM_HANDLER.setFrozen(bl4);
        PunchyAnimationManager.SWIM_HANDLER.setPaused(false);
        float f2 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
        float f3 = 1.0f;
        if (!(animationClip != PunchyAnimationManager.SWIMING_CLIP && animationClip != PunchyAnimationManager.SWIMING_ABOVE_CLIP || class_310.method_1551().field_1724 != null && class_310.method_1551().field_1724.method_5624())) {
            f3 = 0.6f;
        }
        PunchyAnimationManager.SWIM_HANDLER.setSpeedMultiplier(f2);
        PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(f3);
        if (!PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            if (animationClip == this.resolveAboveWaterClip() && this.canRebaseFromWalkOnWater()) {
                PunchyAnimationManager.SWIM_HANDLER.playAtTimeRebasedFromHandlerReturnToOrigin(PunchyAnimationManager.POSE_HANDLER, animationClip, f2, false, 0.0f);
            } else {
                PunchyAnimationManager.SWIM_HANDLER.play(animationClip, f2, false, false);
            }
        } else if (PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() != animationClip) {
            AnimationClip animationClip2 = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
            float f4 = this.remapSwimTransitionStartTime(animationClip2, animationClip, PunchyAnimationManager.SWIM_HANDLER.getCurrentTime());
            if (animationClip2 == this.resolveAboveWaterClip() && animationClip == PunchyAnimationManager.SWIMING_CLIP) {
                PunchyAnimationManager.SWIM_HANDLER.playWithBlendFromCurrentDynamic(animationClip, f2, false);
            } else {
                PunchyAnimationManager.SWIM_HANDLER.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f2, false, f4);
            }
        }
        return true;
    }

    private void handleFallingInWater(class_310 class_3102, boolean bl, boolean bl2) {
        AnimationClip animationClip = PunchyAnimationManager.FALLING_IN_WATER_CLIP;
        if (animationClip == null) {
            this.wasFallingInWater = false;
        } else if (this.isMainAnimationActive()) {
            if (this.wasFallingInWater) {
                this.stopFallingInWaterWithoutClear();
                this.wasFallingInWater = false;
                this.lastSwimArm = null;
            }
        } else if (this.wasFallingInWater) {
            if (bl || bl2 || !this.isActuallyFallingTowardWater(class_3102)) {
                this.stopFallingInWater();
                this.suppressSwimThisTick = true;
                this.wasFallingInWater = false;
                this.lastSwimArm = null;
            }
        } else if (!bl && this.shouldStartFallingInWater(class_3102)) {
            boolean bl3 = this.isArmBlockedBySwimFlag(class_3102, class_1306.field_6182);
            boolean bl4 = this.isArmBlockedBySwimFlag(class_3102, class_1306.field_6183);
            if (!(bl3 && bl4 || PunchyAnimationManager.SWIM_HANDLER.isPlaying() && PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() != animationClip)) {
                class_1306 class_13062 = null;
                if (bl3 && !bl4) {
                    class_13062 = class_1306.field_6183;
                } else if (bl4 && !bl3) {
                    class_13062 = class_1306.field_6182;
                }
                PunchyAnimationManager.SWIM_HANDLER.setIgnoreDualHanded(class_13062 != null);
                PunchyAnimationManager.SWIM_HANDLER.setActiveArm(class_13062);
                if (bl3) {
                    PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_1306.field_6182);
                }
                if (bl4) {
                    PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_1306.field_6183);
                }
                PunchyAnimationManager.SWIM_HANDLER.setLooping(true);
                PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                if (!PunchyAnimationManager.SWIM_HANDLER.isPlaying() || PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() != animationClip) {
                    PunchyAnimationManager.SWIM_HANDLER.setBlendInDurationNext(0.64f);
                    PunchyAnimationManager.SWIM_HANDLER.play(animationClip, f, false, false);
                }
                this.wasFallingInWater = true;
            }
        }
    }

    private void stopFallingInWater() {
        PunchyAnimationManager.SWIM_HANDLER.setLooping(false);
        PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
        PunchyAnimationManager.SWIM_HANDLER.stopImmediateWithBlendOut();
    }

    private void stopFallingInWaterWithoutClear() {
        PunchyAnimationManager.SWIM_HANDLER.setLooping(false);
        PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
        PunchyAnimationManager.SWIM_HANDLER.stopWithoutClear();
    }

    private boolean shouldStartFallingInWater(class_310 class_3102) {
        if (class_3102.field_1724 == null) {
            return false;
        }
        if (!this.isActuallyFallingTowardWater(class_3102)) {
            return false;
        }
        int n = this.findWaterDistanceBelow(class_3102.field_1724.method_37908(), class_3102.field_1724.method_24515());
        return n >= 5;
    }

    private boolean isActuallyFallingTowardWater(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return false;
        }
        if (class_3102.field_1724.method_24828() || class_3102.field_1724.method_5799() || class_3102.field_1724.method_6128()) {
            return false;
        }
        if (class_3102.field_1724.method_31549().field_7479) {
            return false;
        }
        return class_3102.field_1724.method_18798().field_1351 >= -0.08 ? false : (double)class_3102.field_1724.field_6017 > 1.5;
    }

    private int findWaterDistanceBelow(class_1937 class_19372, class_2338 class_23382) {
        if (class_19372 != null && class_23382 != null) {
            int n = class_23382.method_10264();
            class_2338.class_2339 class_23392 = new class_2338.class_2339(class_23382.method_10263(), n, class_23382.method_10260());
            for (int i = 0; i <= 64; ++i) {
                int n2 = n - i;
                class_23392.method_33098(n2);
                if (class_19372.method_8316((class_2338)class_23392).method_15767(class_3486.field_15517)) {
                    int n3 = n2 + 1;
                    return Math.max(0, n - n3);
                }
                if (!class_19372.method_8320((class_2338)class_23392).method_51366()) continue;
                return -1;
            }
            return -1;
        }
        return -1;
    }

    private void stopSwim() {
        PunchyAnimationManager.SWIM_HANDLER.setLooping(false);
        PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
        PunchyAnimationManager.SWIM_HANDLER.stopImmediateWithBlendOut();
    }

    private void stopSwimWithoutClear() {
        PunchyAnimationManager.SWIM_HANDLER.setLooping(false);
        PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
        PunchyAnimationManager.SWIM_HANDLER.stopWithoutClear();
    }

    private void stopAllSwimOverlays() {
        AnimationClip animationClip;
        if (PunchyAnimationManager.SWIM_HANDLER.isPlaying() && ((animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip()) == PunchyAnimationManager.SWIMING_CLIP || animationClip == PunchyAnimationManager.SWIMING_ABOVE_CLIP || animationClip == PunchyAnimationManager.FALLING_IN_WATER_CLIP)) {
            PunchyAnimationManager.SWIM_HANDLER.setLooping(false);
            PunchyAnimationManager.SWIM_HANDLER.setTimelineSpeedScale(1.0f);
            PunchyAnimationManager.SWIM_HANDLER.stopWithoutClear();
        }
        this.wasSwimming = false;
        this.wasFallingInWater = false;
        this.lastSwimArm = null;
    }

    private class_1306 resolveSwimArm(boolean bl, boolean bl2, boolean bl3, class_1306 class_13062) {
        if (class_13062 != null) {
            return this.opposite(class_13062);
        }
        if (!(bl || bl2 && bl3)) {
            if (bl2) {
                return class_1306.field_6183;
            }
            return bl3 ? class_1306.field_6182 : null;
        }
        return null;
    }

    private boolean isMainAnimationActive() {
        return PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() != null;
    }

    private boolean isMainDualHanded() {
        if (!this.isMainAnimationActive()) {
            return false;
        }
        if (PunchyAnimationManager.isDualHandedActive()) {
            return true;
        }
        if (PunchyAnimationManager.POSE_HANDLER.getActiveArm() != null) {
            return false;
        }
        return PunchyAnimationManager.getSourceArm() != null ? false : PunchyAnimationManager.POSE_HANDLER.getActiveArm() == null;
    }

    private boolean isDualHandedEquipPoseActive() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            String string = animationClip.getName();
            if (string != null && !string.isBlank()) {
                String string2 = string.toLowerCase(Locale.ROOT);
                if (!(string2.equals("hand_in") || string2.equals("hand_out") || string2.startsWith("hand_in_") || string2.startsWith("hand_out_"))) {
                    return false;
                }
                return PunchyAnimationManager.POSE_HANDLER.getActiveArm() != null ? false : PunchyAnimationManager.isDualHandedActive(animationClip, PunchyAnimationManager.POSE_HANDLER.getCurrentTime());
            }
            return false;
        }
        return false;
    }

    private class_1306 resolveMainArm() {
        if (!this.isMainAnimationActive()) {
            return null;
        }
        if (this.isMainDualHanded()) {
            return null;
        }
        class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
        return class_13062 != null ? class_13062 : PunchyAnimationManager.getSourceArm();
    }

    private void suppressSwimArmForMainAnimation() {
        if (!PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            this.suppressedMainArm = null;
        } else {
            AnimationClip animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
            if (animationClip != PunchyAnimationManager.SWIMING_CLIP && animationClip != PunchyAnimationManager.SWIMING_ABOVE_CLIP) {
                this.suppressedMainArm = null;
            } else {
                class_1306 class_13062 = this.resolveMainArm();
                if (class_13062 != null) {
                    PunchyAnimationManager.SWIM_HANDLER.requestSkipArm(class_13062);
                    this.suppressedMainArm = class_13062;
                } else if (this.suppressedMainArm != null) {
                    PunchyAnimationManager.SWIM_HANDLER.requestBlendInArm(this.suppressedMainArm);
                    this.suppressedMainArm = null;
                }
            }
        }
    }

    private class_1306 opposite(class_1306 class_13062) {
        return class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
    }

    private boolean isArmBlockedBySwimFlag(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            boolean bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
            class_1306 class_13063 = bl ? class_1306.field_6183 : class_1306.field_6182;
            return class_13062 == class_13063 ? PunchyTuningConfig.isSwimingDisabledForStack(class_3102.field_1724.method_6047()) : PunchyTuningConfig.isSwimingDisabledForStack(class_3102.field_1724.method_6079());
        }
        return false;
    }

    private boolean isDeepWaterForSwim(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_2338 class_23382;
            class_1937 class_19372 = class_3102.field_1724.method_37908();
            if (!class_19372.method_8316(class_23382 = class_3102.field_1724.method_24515()).method_15767(class_3486.field_15517)) {
                return false;
            }
            class_2338.class_2339 class_23392 = new class_2338.class_2339(class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260());
            int n = 0;
            for (int i = 0; i < 4; ++i) {
                class_23392.method_33098(class_23382.method_10264() + i);
                if (!class_19372.method_8316((class_2338)class_23392).method_15767(class_3486.field_15517)) break;
                ++n;
            }
            return n >= 2;
        }
        return false;
    }

    private boolean isAboveWaterSwimming(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return false;
        }
        if (class_3102.field_1724.method_5869() || class_3102.field_1724.method_5777(class_3486.field_15517)) {
            return false;
        }
        return this.hasSolidSupportAtFeet(class_3102) ? false : this.hasWaterContactAnyDepth(class_3102);
    }

    private AnimationClip resolveAboveWaterClip() {
        AnimationClip animationClip = PunchyAnimationManager.SWIMING_ABOVE_CLIP;
        return animationClip != null ? animationClip : PunchyAnimationManager.resolveCustomClip("swiming_above");
    }

    private boolean canRebaseFromWalkOnWater() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && poseHandler.getCurrentClip() != null && (poseHandler.isPlaying() || poseHandler.isBlendOutActive())) {
            WalkOnWaterStateMachine walkOnWaterStateMachine = PunchyClient.getWalkOnWaterStateMachine();
            return walkOnWaterStateMachine != null && walkOnWaterStateMachine.isCurrentPoseOwnedByWalkOnWater();
        }
        return false;
    }

    private float remapSwimTransitionStartTime(AnimationClip animationClip, AnimationClip animationClip2, float f) {
        if (animationClip != null && animationClip2 != null) {
            float f2 = animationClip.getLength();
            float f3 = animationClip2.getLength();
            if (!(f2 <= 0.0f) && !(f3 <= 0.0f)) {
                float f4 = f % f2;
                if (f4 < 0.0f) {
                    f4 += f2;
                }
                return f4 / f2 * f3;
            }
            return 0.0f;
        }
        return 0.0f;
    }

    private boolean isShallowWater(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1937 class_19372 = class_3102.field_1724.method_37908();
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double d = class_2383.field_1322 + 0.05;
            double d2 = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d3 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray = new double[]{class_2383.field_1323 + d2, class_2383.field_1320 - d2, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray2 = new double[]{class_2383.field_1321 + d3, class_2383.field_1324 - d3, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d4 : dArray) {
                for (double d5 : dArray2) {
                    class_2338 class_23382;
                    class_2338 class_23383 = class_2338.method_49637((double)d4, (double)d, (double)d5);
                    if (!class_19372.method_8316(class_23383).method_15767(class_3486.field_15517) || class_19372.method_8316(class_23382 = class_23383.method_10074()).method_15767(class_3486.field_15517) || !class_19372.method_8320(class_23382).method_51366()) continue;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean hasSolidSupportAtFeet(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1937 class_19372 = class_3102.field_1724.method_37908();
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double d = class_2383.field_1322 - 0.05;
            double d2 = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d3 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray = new double[]{class_2383.field_1323 + d2, class_2383.field_1320 - d2, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray2 = new double[]{class_2383.field_1321 + d3, class_2383.field_1324 - d3, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d4 : dArray) {
                for (double d5 : dArray2) {
                    class_2338 class_23382 = class_2338.method_49637((double)d4, (double)d, (double)d5);
                    if (!class_19372.method_8320(class_23382).method_51366()) continue;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean hasWaterContactAnyDepth(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1937 class_19372 = class_3102.field_1724.method_37908();
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double[] dArray = new double[]{class_2383.field_1322 + 0.01, class_2383.field_1322 + 0.08, class_2383.field_1322 + 0.16, Math.min(class_2383.field_1325 - 0.05, class_2383.field_1322 + 0.45)};
            double d = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d2 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray2 = new double[]{class_2383.field_1323 + d, class_2383.field_1320 - d, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray3 = new double[]{class_2383.field_1321 + d2, class_2383.field_1324 - d2, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d3 : dArray) {
                for (double d4 : dArray2) {
                    for (double d5 : dArray3) {
                        if (!class_19372.method_8316(class_2338.method_49637((double)d4, (double)d3, (double)d5)).method_15767(class_3486.field_15517)) continue;
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
}

