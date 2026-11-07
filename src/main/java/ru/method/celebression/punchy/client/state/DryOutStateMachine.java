package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Map;
import java.util.Set;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_3610;
import net.minecraft.class_638;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.WalkOnWaterStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class DryOutStateMachine {
    private static final int MIN_WET_TICKS = 40;
    private static final float DRY_OUT_REBASE_SPEED = 3.4f;
    private static final String START_WITH_OFFHAND_EFFECT = "start_here_if_off_hand_item";
    private static final String START_WITH_OFFHAND_EFFECT_ALIAS = "start_if_offhand_item";
    private static final String STOP_WITH_OFFHAND_EFFECT = "stop_if_offhand_item";
    private int wetTicks;
    private boolean wasTouchingWater;
    private boolean pendingDryOut;
    private AnimationClip activeClip;

    public void tick(class_310 class_3102) {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null || poseHandler == null) {
            this.resetTracking();
        } else if (!class_3102.field_1690.method_31044().method_31034()) {
            this.resetTracking();
        } else if (class_3102.field_1724.method_7325() || !class_3102.field_1724.method_5805()) {
            this.stopIfOwned(poseHandler);
            this.resetTracking();
        } else if (this.resolveDryOutActiveArm(class_3102) == null) {
            this.logDryOutState("suppressed", class_3102, null);
            this.stopIfOwned(poseHandler);
            this.resetTracking();
        } else {
            AnimationClip animationClip = PunchyAnimationManager.resolveNamedClip("dry_out", class_3102, class_1268.field_5808);
            if (animationClip == null) {
                this.stopIfOwned(poseHandler);
                this.resetTracking();
            } else if (this.shouldStopForOffhand(poseHandler)) {
                this.stopIfOwned(poseHandler);
                this.pendingDryOut = false;
                this.wetTicks = 0;
            } else {
                boolean bl = this.hasWaterContactAnyDepth(class_3102);
                boolean bl2 = this.wasTouchingWater && !bl;
                this.wasTouchingWater = bl;
                if (bl) {
                    ++this.wetTicks;
                    this.pendingDryOut = false;
                    this.stopIfOwned(poseHandler);
                } else {
                    if (bl2) {
                        this.pendingDryOut = this.wetTicks >= 40;
                        this.wetTicks = 0;
                    } else if (!this.pendingDryOut) {
                        this.wetTicks = 0;
                    }
                    if (!this.pendingDryOut) {
                        this.clearFinishedClip(poseHandler);
                    } else if (!this.isReadyToDryOut(class_3102)) {
                        this.clearFinishedClip(poseHandler);
                    } else {
                        WalkOnWaterStateMachine walkOnWaterStateMachine = PunchyClient.getWalkOnWaterStateMachine();
                        if (walkOnWaterStateMachine != null && walkOnWaterStateMachine.isActiveOrHandingOff()) {
                            this.clearFinishedClip(poseHandler);
                        } else {
                            this.playDryOut(class_3102, poseHandler, animationClip);
                            this.pendingDryOut = false;
                            this.wetTicks = 0;
                        }
                    }
                }
            }
        }
    }

    private void playDryOut(class_310 class_3102, PoseHandler poseHandler, AnimationClip animationClip) {
        if (poseHandler.getCurrentClip() != animationClip || !poseHandler.isPlaying() && !poseHandler.isBlendOutActive()) {
            float f = this.resolveStartTime(class_3102, animationClip);
            HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102.field_1724);
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            poseHandler.setIgnoreDualHanded(this.isAnyDryOutArmBlocked(class_3102));
            poseHandler.setLooping(false);
            poseHandler.setFrozen(false);
            poseHandler.setPaused(false);
            class_1306 class_13062 = this.resolveActiveArm(class_3102, animationClip, f);
            poseHandler.setActiveArm(class_13062);
            poseHandler.setBlendInDurationNext(0.0f);
            poseHandler.setRebaseReturnSpeedNext(3.4f);
            poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, PunchyAnimationManager.clipSpeedMultiplier(animationClip), false, f);
            this.activeClip = animationClip;
            this.logDryOutState("play", class_3102, class_13062);
        } else {
            this.activeClip = animationClip;
        }
    }

    private class_1306 resolveActiveArm(class_310 class_3102, AnimationClip animationClip, float f) {
        class_1306 class_13062 = this.resolveDryOutActiveArm(class_3102);
        if (class_13062 == null) {
            return null;
        }
        if (this.isAnyDryOutArmBlocked(class_3102)) {
            return class_13062;
        }
        return PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, f) ? null : class_13062;
    }

    private boolean isAnyDryOutArmBlocked(class_310 class_3102) {
        return class_3102 != null && class_3102.field_1724 != null ? PunchyTuningConfig.isDryOutDisabledForStack(class_3102.field_1724.method_6047()) || PunchyTuningConfig.isDryOutDisabledForStack(class_3102.field_1724.method_6079()) : false;
    }

    private class_1306 resolveDryOutActiveArm(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl = PunchyTuningConfig.isDryOutDisabledForStack(class_3102.field_1724.method_6047());
            boolean bl2 = PunchyTuningConfig.isDryOutDisabledForStack(class_3102.field_1724.method_6079());
            class_1306 class_13062 = this.resolveArm(class_3102.field_1724, class_1268.field_5808);
            class_1306 class_13063 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
            if (bl && bl2) {
                this.logDryOutState("resolveArm", class_3102, null);
                return null;
            }
            if (bl) {
                return class_13063;
            }
            if (bl2) {
                return class_13062;
            }
            class_1306 class_13064 = PunchyAnimationManager.getUseArm(class_3102, class_1268.field_5808);
            this.logDryOutState("resolveArm", class_3102, class_13064);
            return class_13064;
        }
        return null;
    }

    private void logDryOutState(String string, class_310 class_3102, class_1306 class_13062) {
    }

    private class_1306 resolveArm(class_746 class_7462, class_1268 class_12682) {
        if (class_7462 != null && class_12682 != null) {
            boolean bl;
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            if (class_12682 == class_1268.field_5808) {
                return bl ? class_1306.field_6183 : class_1306.field_6182;
            }
            return bl ? class_1306.field_6182 : class_1306.field_6183;
        }
        return null;
    }

    private float resolveStartTime(class_310 class_3102, AnimationClip animationClip) {
        if (class_3102 == null || class_3102.field_1724 == null || animationClip == null) {
            return 0.0f;
        }
        if (class_3102.field_1724.method_6079() != null && !class_3102.field_1724.method_6079().method_7960()) {
            Float f = this.findTimelineEffectTime(animationClip, "start_here_if_off_hand_item");
            if (f != null) {
                return f.floatValue();
            }
            Float f2 = this.findTimelineEffectTime(animationClip, "start_if_offhand_item");
            return f2 == null ? 0.0f : f2.floatValue();
        }
        return 0.0f;
    }

    private boolean shouldStopForOffhand(PoseHandler poseHandler) {
        if (poseHandler != null && this.activeClip != null) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && class_3102.field_1724 != null) {
                if (!poseHandler.isPlaying() || poseHandler.getCurrentClip() != this.activeClip) {
                    return false;
                }
                if (class_3102.field_1724.method_6079() != null && !class_3102.field_1724.method_6079().method_7960()) {
                    Float f = this.findTimelineEffectTime(this.activeClip, "stop_if_offhand_item");
                    return f != null && poseHandler.getCurrentTime() >= f.floatValue();
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private Float findTimelineEffectTime(AnimationClip animationClip, String string) {
        if (animationClip != null && string != null && !string.isBlank()) {
            for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffects().entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                for (String string2 : entry.getValue()) {
                    if (string2 == null || !string.equalsIgnoreCase(string2.trim())) continue;
                    return entry.getKey();
                }
            }
            return null;
        }
        return null;
    }

    private boolean isReadyToDryOut(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return false;
        }
        return !class_3102.field_1724.method_31549().field_7479 && !class_3102.field_1724.method_6128() && class_3102.field_1724.method_5854() == null ? this.hasDryGroundSupport(class_3102) : false;
    }

    private boolean hasDryGroundSupport(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1687 != null) {
            class_638 class_6382 = class_3102.field_1687;
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double d = class_2383.field_1322 - 0.05;
            double d2 = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d3 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray = new double[]{class_2383.field_1323 + d2, class_2383.field_1320 - d2, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray2 = new double[]{class_2383.field_1321 + d3, class_2383.field_1324 - d3, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            boolean bl = false;
            for (double d4 : dArray) {
                for (double d5 : dArray2) {
                    class_2338 class_23382 = class_2338.method_49637((double)d4, (double)d, (double)d5);
                    if (class_6382.method_8316(class_23382).method_15767(class_3486.field_15517)) {
                        return false;
                    }
                    if (!class_6382.method_8320(class_23382).method_51366()) continue;
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    private boolean hasWaterContactAnyDepth(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1687 != null) {
            class_638 class_6382 = class_3102.field_1687;
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double[] dArray = new double[]{class_2383.field_1322 + 0.01, class_2383.field_1322 + 0.08, class_2383.field_1322 + 0.16, Math.min(class_2383.field_1325 - 0.05, class_2383.field_1322 + 0.45)};
            double d = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d2 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray2 = new double[]{class_2383.field_1323 + d, class_2383.field_1320 - d, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray3 = new double[]{class_2383.field_1321 + d2, class_2383.field_1324 - d2, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d3 : dArray) {
                for (double d4 : dArray2) {
                    for (double d5 : dArray3) {
                        class_3610 class_36102 = class_6382.method_8316(class_2338.method_49637((double)d4, (double)d3, (double)d5));
                        if (!class_36102.method_15767(class_3486.field_15517)) continue;
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void stopIfOwned(PoseHandler poseHandler) {
        if (poseHandler != null && this.activeClip != null) {
            if (poseHandler.getCurrentClip() == this.activeClip && (poseHandler.isPlaying() || poseHandler.isBlendOutActive())) {
                poseHandler.stopImmediateWithBlendOut();
            }
            this.activeClip = null;
        }
    }

    private void clearFinishedClip(PoseHandler poseHandler) {
        if (poseHandler != null && this.activeClip != null && (poseHandler.getCurrentClip() != this.activeClip || !poseHandler.isPlaying() && !poseHandler.isBlendOutActive())) {
            this.activeClip = null;
        }
    }

    private void resetTracking() {
        this.wetTicks = 0;
        this.wasTouchingWater = false;
        this.pendingDryOut = false;
        this.activeClip = null;
    }
}

