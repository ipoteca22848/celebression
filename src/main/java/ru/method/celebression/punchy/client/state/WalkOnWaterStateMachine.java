package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_3610;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class WalkOnWaterStateMachine {
    private static final boolean LOG_WALK_ON_WATER_DEBUG = true;
    private static final int IN_WATER_TRIGGER_GAP_TICKS = 20;
    private static final float REBASE_RETURN_SPEED_DEFAULT = 1.7f;
    private static final float REBASE_RETURN_SPEED_END = 4.5f;
    private static final float REBASE_RETURN_SPEED_RESUME = 1.15f;
    private static final String START_WITH_OFFHAND_EFFECT = "start_here_if_off_hand_item";
    private static final String START_WITH_OFFHAND_EFFECT_ALIAS = "start_if_offhand_item";
    private static final String END_STOP_WITH_OFFHAND_EFFECT = "stop_if_offhand_item";
    private Phase phase = Phase.NONE;
    private PunchyTuningConfig.CustomAnimationEntry activeEntry;
    private AnimationClip startClip;
    private AnimationClip loopClip;
    private AnimationClip endClip;
    private AnimationClip activeClip;
    private class_1799 lastStack = class_1799.field_8037;
    private Boolean lastTriggerActive;
    private String lastTriggerReason;
    private String lastResolvedSummary;
    private Phase lastLoggedPhase = Phase.NONE;
    private boolean suspendedByForeignClip;
    private Phase suspendedPhase = Phase.NONE;
    private float suspendedTime;
    private boolean pauseLoopAfterResumeBlend;
    private int inWaterTriggerGapTicks;
    private int unsupportedWaterGraceTicks;
    private AnimationClip handoffClip;

    public void tick(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            this.stopAndReset(false);
        } else if (!class_3102.field_1690.method_31044().method_31034()) {
            this.stopAndReset(false);
        } else {
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            if (this.isWalkOnWaterSuppressedByFishingRod(class_3102)) {
                this.forceDisableReset();
            } else {
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "walk_on_water_v2");
                WalkOnWaterSelection walkOnWaterSelection = customAnimationEntry != null ? this.resolveSelection(class_3102, class_17992, customAnimationEntry) : WalkOnWaterSelection.INVALID;
                TriggerState triggerState = this.applyUnsupportedWaterGrace(class_3102, this.applyInWaterTriggerGap(class_3102, this.evaluateTrigger(class_3102)));
                boolean bl = triggerState.active();
                if (bl) {
                    ItemAnimationManager.setPersistentType(class_1268.field_5808, "walk_on_water_v2", true, true);
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "walk_on_water_v2");
                }
                this.logTriggerState(class_3102, customAnimationEntry, walkOnWaterSelection, triggerState);
                this.captureOwnedPhaseTime();
                if (this.shouldYieldToForeignClip()) {
                    this.suspendForForeignClip();
                } else if (this.suspendedByForeignClip) {
                    if (HandEquipStateMachine.isEquipInProgress()) {
                        this.keepOppositeArmWalkingOnWater();
                    } else if (!walkOnWaterSelection.isValid()) {
                        this.stopAndReset(false);
                    } else {
                        if (this.activeEntry != customAnimationEntry || !this.matchesActiveSelection(walkOnWaterSelection)) {
                            this.activeEntry = customAnimationEntry;
                            this.startClip = walkOnWaterSelection.start();
                            this.loopClip = walkOnWaterSelection.loop();
                            this.endClip = walkOnWaterSelection.end();
                            this.logResolvedSelection(customAnimationEntry, walkOnWaterSelection);
                        }
                        this.resumeFromSuspendedState(bl);
                    }
                } else if (!walkOnWaterSelection.isValid()) {
                    if (this.phase != Phase.NONE) {
                        this.stopAndReset(true);
                    }
                } else {
                    boolean bl2;
                    boolean bl3 = bl2 = this.phase == Phase.NONE || this.activeEntry != customAnimationEntry || !this.matchesActiveSelection(walkOnWaterSelection);
                    if (bl2) {
                        this.activeEntry = customAnimationEntry;
                        this.startClip = walkOnWaterSelection.start();
                        this.loopClip = walkOnWaterSelection.loop();
                        this.endClip = walkOnWaterSelection.end();
                        this.lastStack = this.copyStack(class_17992);
                        this.logResolvedSelection(customAnimationEntry, walkOnWaterSelection);
                        if (bl) {
                            this.startStartPhase();
                        } else {
                            this.phase = Phase.NONE;
                            this.activeClip = null;
                        }
                    } else {
                        this.lastStack = this.copyStack(class_17992);
                        boolean bl4 = this.shouldPauseForInWaterGapWithoutSupport(class_3102, triggerState);
                        switch (this.phase.ordinal()) {
                            case 0: {
                                if (!bl) break;
                                this.startStartPhase();
                                break;
                            }
                            case 1: {
                                if (bl) {
                                    if (bl4) {
                                        PunchyAnimationManager.POSE_HANDLER.setPaused(true);
                                        break;
                                    }
                                    if (!this.isPlaying(this.startClip)) {
                                        this.startLoopPhase();
                                        break;
                                    }
                                    PunchyAnimationManager.POSE_HANDLER.setPaused(false);
                                    break;
                                }
                                if (this.shouldHandOffToSwimAbove(class_3102)) {
                                    this.stopAndReset(false);
                                    break;
                                }
                                this.startEndPhase();
                                break;
                            }
                            case 2: {
                                if (!bl) {
                                    if (this.shouldHandOffToSwimAbove(class_3102)) {
                                        this.stopAndReset(false);
                                        break;
                                    }
                                    this.startEndPhase();
                                    break;
                                }
                                if (!this.isPlaying(this.loopClip)) {
                                    this.startLoopPhase();
                                    break;
                                }
                                PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                                boolean bl5 = this.isMovementKeyActive(class_3102);
                                PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
                                if (bl4) {
                                    poseHandler.setPaused(true);
                                    poseHandler.setSecondaryPaused(true);
                                    break;
                                }
                                class_1306 class_13062 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
                                if (poseHandler.getSecondaryClip() == this.loopClip && class_13062 != null && poseHandler.getSecondaryArm() == class_13062) {
                                    poseHandler.setSecondaryPaused(!bl5);
                                }
                                if (this.pauseLoopAfterResumeBlend) {
                                    if (bl5) {
                                        this.pauseLoopAfterResumeBlend = false;
                                        poseHandler.setPaused(false);
                                        break;
                                    }
                                    if (!PunchyAnimationManager.POSE_HANDLER.isRebaseReturnToOriginActive()) {
                                        this.pauseLoopAfterResumeBlend = false;
                                        poseHandler.setPaused(true);
                                        break;
                                    }
                                    poseHandler.setPaused(false);
                                    break;
                                }
                                poseHandler.setPaused(!bl5);
                                break;
                            }
                            case 3: {
                                if (bl) {
                                    this.startStartPhase();
                                    break;
                                }
                                if (this.shouldStopEndClipEarly(class_3102)) {
                                    this.stopAndReset(false);
                                    break;
                                }
                                if (this.isCurrentOrBlending(this.endClip)) break;
                                this.resetState();
                            }
                        }
                    }
                }
            }
        }
    }

    private void startStartPhase() {
        if (this.startClip != null) {
            float f;
            class_310 class_3102 = class_310.method_1551();
            HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102 != null ? class_3102.field_1724 : null);
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
            poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
            poseHandler.setIgnoreDualHanded(this.isAnyWalkOnWaterArmBlocked(class_3102));
            poseHandler.setActiveArm(this.resolveActiveArmForClip(this.startClip));
            poseHandler.setLooping(false);
            poseHandler.setFrozen(false);
            poseHandler.setPaused(false);
            poseHandler.setBlendInDurationNext(0.0f);
            float f2 = PunchyAnimationManager.clipSpeedMultiplier(this.startClip);
            Float f3 = this.resolveStartClipTime(class_3102);
            float f4 = f = f3 != null ? f3.floatValue() : 0.0f;
            if (this.hasPoseToRebase(poseHandler)) {
                poseHandler.setRebaseReturnSpeedNext(1.7f);
                poseHandler.setBlendInDurationNext(0.0f);
                poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(this.startClip, f2, false, f);
            } else if (f > 0.0f) {
                poseHandler.playAtTime(this.startClip, f2, true, false, f);
            } else {
                poseHandler.play(this.startClip, f2, true, false);
            }
            this.phase = Phase.START;
            this.activeClip = this.startClip;
            this.logPhaseTransition("start", this.startClip, f3);
        }
    }

    private void startLoopPhase() {
        if (this.loopClip != null) {
            class_310 class_3102 = class_310.method_1551();
            HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102 != null ? class_3102.field_1724 : null);
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
            poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
            poseHandler.setIgnoreDualHanded(this.isAnyWalkOnWaterArmBlocked(class_3102));
            poseHandler.setActiveArm(this.resolveActiveArmForClip(this.loopClip));
            poseHandler.setLooping(true);
            poseHandler.setFrozen(false);
            poseHandler.setPaused(!this.isMovementKeyActive(class_3102));
            float f = PunchyAnimationManager.clipSpeedMultiplier(this.loopClip);
            if (this.hasPoseToRebase(poseHandler)) {
                poseHandler.setRebaseReturnSpeedNext(1.7f);
                poseHandler.setBlendInDurationNext(0.0f);
                poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(this.loopClip, f, false, 0.0f);
            } else {
                poseHandler.play(this.loopClip, f, true, false);
            }
            this.phase = Phase.LOOP;
            this.activeClip = this.loopClip;
            this.logPhaseTransition("loop", this.loopClip);
        }
    }

    private void startEndPhase() {
        if (this.endClip == null) {
            this.resetState();
        } else {
            PunchyAnimationManager.setSourceHand(class_310.method_1551(), class_1268.field_5808);
            PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
            AnimationClip animationClip = poseHandler.getSecondaryClip();
            class_1306 class_13062 = poseHandler.getSecondaryArm();
            poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
            poseHandler.setIgnoreDualHanded(this.isAnyWalkOnWaterArmBlocked(class_310.method_1551()));
            poseHandler.setActiveArm(this.resolveActiveArmForClip(this.endClip));
            poseHandler.setLooping(false);
            poseHandler.setFrozen(false);
            poseHandler.setPaused(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(this.endClip);
            if (this.hasPoseToRebase(poseHandler)) {
                poseHandler.setRebaseReturnSpeedNext(4.5f);
                poseHandler.setBlendInDurationNext(0.0f);
                poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(this.endClip, f, false, 0.0f);
            } else {
                poseHandler.play(this.endClip, f, true, false);
            }
            if (this.isWalkOnWaterPhaseClip(animationClip) && class_13062 != null) {
                if (!this.isArmBlockedByWalkOnWaterFlag(class_310.method_1551(), class_13062)) {
                    poseHandler.setSecondaryClipAtTime(this.endClip, class_13062, f, 0.0f);
                    poseHandler.setSecondaryPaused(false);
                } else {
                    poseHandler.clearSecondaryClip();
                }
            }
            this.phase = Phase.END;
            this.activeClip = this.endClip;
            this.logPhaseTransition("end", this.endClip);
        }
    }

    private void stopAndReset(boolean bl) {
        if (this.phase == Phase.NONE) {
            this.resetState();
        } else {
            this.handoffClip = this.activeClip;
            if (this.ownsCurrentClip()) {
                PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                if (bl) {
                    PunchyAnimationManager.POSE_HANDLER.stopWithoutClear();
                } else {
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                }
            }
            ItemAnimationManager.clearPersistentType(class_1268.field_5808, "walk_on_water_v2");
            PunchyAnimationManager.clearSourceHand();
            this.resetState();
        }
    }

    private void resetState() {
        PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        this.phase = Phase.NONE;
        this.activeEntry = null;
        this.startClip = null;
        this.loopClip = null;
        this.endClip = null;
        this.activeClip = null;
        this.lastStack = class_1799.field_8037;
        this.lastLoggedPhase = Phase.NONE;
        this.suspendedByForeignClip = false;
        this.suspendedPhase = Phase.NONE;
        this.suspendedTime = 0.0f;
        this.pauseLoopAfterResumeBlend = false;
        this.inWaterTriggerGapTicks = 0;
        this.unsupportedWaterGraceTicks = 0;
        this.handoffClip = null;
    }

    private void forceDisableReset() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null) {
            poseHandler.clearSecondaryClip();
            poseHandler.setLooping(false);
            if (this.ownsCurrentClip()) {
                poseHandler.stopWithoutClear();
            }
        }
        ItemAnimationManager.clearPersistentType(class_1268.field_5808, "walk_on_water_v2");
        PunchyAnimationManager.clearSourceHand();
        this.handoffClip = null;
        this.resetState();
    }

    private boolean isWalkOnWaterSuppressedByFishingRod(class_310 class_3102) {
        boolean bl;
        class_1306 class_13062 = this.resolveWalkOnWaterActiveArm(class_3102);
        boolean bl2 = bl = class_13062 == null;
        if (class_3102 != null && class_3102.field_1724 != null) {
            this.debugWalkOnWater("suppressCheck", "suppressed=" + bl + " main=" + this.stackId(class_3102.field_1724.method_6047()) + " off=" + this.stackId(class_3102.field_1724.method_6079()) + " mainDisabled=" + this.hasFishingRodWalkDisable(class_3102.field_1724.method_6047()) + " offDisabled=" + this.hasFishingRodWalkDisable(class_3102.field_1724.method_6079()) + " resolvedArm=" + String.valueOf(class_13062));
        }
        return bl;
    }

    private boolean hasFishingRodWalkDisable(class_1799 class_17992) {
        return PunchyTuningConfig.isWalkOnWaterDisabledForStack(class_17992);
    }

    private WalkOnWaterSelection resolveSelection(class_310 class_3102, class_1799 class_17992, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 != null && customAnimationEntry != null) {
            String string = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry);
            AnimationClip animationClip = this.resolveNamedClip(CustomAnimationResolver.resolveStartName(customAnimationEntry), class_3102, string);
            AnimationClip animationClip2 = this.resolveNamedClip(CustomAnimationResolver.resolveLoopingName(customAnimationEntry), class_3102, string);
            AnimationClip animationClip3 = this.resolveNamedClip(CustomAnimationResolver.resolveEndName(customAnimationEntry), class_3102, string);
            return animationClip != null && animationClip2 != null && animationClip3 != null ? new WalkOnWaterSelection(animationClip, animationClip2, animationClip3) : WalkOnWaterSelection.INVALID;
        }
        return WalkOnWaterSelection.INVALID;
    }

    private AnimationClip resolveNamedClip(String string, class_310 class_3102, String string2) {
        return string != null && !string.isBlank() ? PunchyAnimationManager.resolveNamedClip(string, class_3102, class_1268.field_5808, string2) : null;
    }

    private TriggerState evaluateTrigger(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            return new TriggerState(false, "client_or_player_missing");
        }
        if (class_3102.field_1724.method_7325() || !class_3102.field_1724.method_5805()) {
            return new TriggerState(false, "spectator_or_dead");
        }
        if (class_3102.field_1724.method_5681()) {
            return new TriggerState(false, "swimming_or_submerged");
        }
        if (class_3102.field_1724.method_31549().field_7479 || class_3102.field_1724.method_6128() || class_3102.field_1724.method_5854() != null) {
            return new TriggerState(false, "flying_or_vehicle");
        }
        if (this.shouldTriggerOnSolidUnderwaterFloor(class_3102)) {
            return new TriggerState(true, "supported_underwater_floor");
        }
        if (!class_3102.field_1724.method_5869() && !class_3102.field_1724.method_5777(class_3486.field_15517)) {
            class_638 class_6382 = class_3102.field_1687;
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double[] dArray = new double[]{class_2383.field_1322 + 0.01, class_2383.field_1322 + 0.08, class_2383.field_1322 + 0.16};
            double d = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d2 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray2 = new double[]{class_2383.field_1323 + d, class_2383.field_1320 - d, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray3 = new double[]{class_2383.field_1321 + d2, class_2383.field_1324 - d2, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d3 : dArray) {
                for (double d4 : dArray2) {
                    for (double d5 : dArray3) {
                        class_2338 class_23382 = class_2338.method_49637((double)d4, (double)d3, (double)d5);
                        class_3610 class_36102 = class_6382.method_8316(class_23382);
                        if (!class_36102.method_15767(class_3486.field_15517) || class_6382.method_8316(class_23382.method_10084()).method_15767(class_3486.field_15517) || class_6382.method_8316(class_23382.method_10074()).method_15767(class_3486.field_15517) || !class_6382.method_8320(class_23382.method_10074()).method_51366()) continue;
                        String string = "water@" + class_23382.method_10263() + "," + class_23382.method_10264() + "," + class_23382.method_10260() + " below=" + class_23382.method_10074().method_10263() + "," + class_23382.method_10074().method_10264() + "," + class_23382.method_10074().method_10260();
                        return new TriggerState(true, string);
                    }
                }
            }
            return new TriggerState(false, "no_shallow_water_sample");
        }
        return new TriggerState(false, "swimming_or_submerged");
    }

    private boolean shouldTriggerOnSolidUnderwaterFloor(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            return false;
        }
        if (!class_3102.field_1724.method_5869() && !class_3102.field_1724.method_5777(class_3486.field_15517)) {
            return false;
        }
        return !this.hasSolidSupportAtFeet(class_3102) ? false : this.hasWaterContactAnyDepth(class_3102);
    }

    private TriggerState applyInWaterTriggerGap(class_310 class_3102, TriggerState triggerState) {
        if (triggerState == null) {
            this.inWaterTriggerGapTicks = 0;
            return new TriggerState(false, "null_trigger_state");
        }
        if (triggerState.active()) {
            this.inWaterTriggerGapTicks = 0;
            return triggerState;
        }
        if (!"no_shallow_water_sample".equals(triggerState.reason())) {
            if (this.shouldHoldTriggerAfterLosingSupport(class_3102, triggerState) && this.inWaterTriggerGapTicks < 20) {
                ++this.inWaterTriggerGapTicks;
                return new TriggerState(true, "gap_in_water_" + this.inWaterTriggerGapTicks);
            }
            this.inWaterTriggerGapTicks = 0;
            return triggerState;
        }
        if (this.phase == Phase.NONE && !this.suspendedByForeignClip) {
            this.inWaterTriggerGapTicks = 0;
            return triggerState;
        }
        if (!this.hasWaterContact(class_3102)) {
            this.inWaterTriggerGapTicks = 0;
            return triggerState;
        }
        if (this.inWaterTriggerGapTicks < 20) {
            ++this.inWaterTriggerGapTicks;
            return new TriggerState(true, "gap_in_water_" + this.inWaterTriggerGapTicks);
        }
        return triggerState;
    }

    private TriggerState applyUnsupportedWaterGrace(class_310 class_3102, TriggerState triggerState) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            this.unsupportedWaterGraceTicks = 0;
            return triggerState == null ? new TriggerState(false, "null_trigger_state") : triggerState;
        }
        if (triggerState != null && triggerState.active()) {
            this.unsupportedWaterGraceTicks = 0;
            return triggerState;
        }
        if (this.phase == Phase.NONE && !this.suspendedByForeignClip) {
            this.unsupportedWaterGraceTicks = 0;
            return triggerState;
        }
        if (class_3102.field_1724.method_5681() || class_3102.field_1724.method_5869() || class_3102.field_1724.method_5777(class_3486.field_15517)) {
            this.unsupportedWaterGraceTicks = 0;
            return triggerState;
        }
        if (this.hasSolidSupportAtFeet(class_3102) || !this.hasWaterContactAnyDepth(class_3102)) {
            this.unsupportedWaterGraceTicks = 0;
            return triggerState;
        }
        if (this.unsupportedWaterGraceTicks < 20) {
            ++this.unsupportedWaterGraceTicks;
            return new TriggerState(true, "unsupported_water_grace_" + this.unsupportedWaterGraceTicks);
        }
        return triggerState;
    }

    private boolean shouldHoldTriggerAfterLosingSupport(class_310 class_3102, TriggerState triggerState) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            return false;
        }
        if (this.phase == Phase.NONE && !this.suspendedByForeignClip) {
            return false;
        }
        if (triggerState == null || triggerState.active()) {
            return false;
        }
        if (class_3102.field_1724.method_5681() || class_3102.field_1724.method_5869() || class_3102.field_1724.method_5777(class_3486.field_15517)) {
            return false;
        }
        return this.hasSolidSupportAtFeet(class_3102) ? false : this.hasWaterContactAnyDepth(class_3102);
    }

    private boolean shouldPauseForInWaterGapWithoutSupport(class_310 class_3102, TriggerState triggerState) {
        return triggerState != null && triggerState.active() && triggerState.reason() != null && triggerState.reason().startsWith("gap_in_water_") && !this.hasSolidSupportAtFeet(class_3102);
    }

    private boolean hasWaterContact(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            return false;
        }
        return !class_3102.field_1724.method_5681() && !class_3102.field_1724.method_5869() && !class_3102.field_1724.method_5777(class_3486.field_15517) ? this.hasWaterContactAnyDepth(class_3102) : false;
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
                        if (!class_6382.method_8316(class_2338.method_49637((double)d4, (double)d3, (double)d5)).method_15767(class_3486.field_15517)) continue;
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private boolean shouldHandOffToSwimAbove(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1687 == null) {
            return false;
        }
        if (this.phase == Phase.NONE) {
            return false;
        }
        if (class_3102.field_1724.method_5681() || class_3102.field_1724.method_5869() || class_3102.field_1724.method_5777(class_3486.field_15517)) {
            return false;
        }
        return this.hasSolidSupportAtFeet(class_3102) ? false : this.hasWaterContactAnyDepth(class_3102);
    }

    private boolean hasSolidSupportAtFeet(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1687 != null) {
            class_638 class_6382 = class_3102.field_1687;
            class_238 class_2383 = class_3102.field_1724.method_5829();
            double d = class_2383.field_1322 - 0.05;
            double d2 = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d3 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray = new double[]{class_2383.field_1323 + d2, class_2383.field_1320 - d2, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray2 = new double[]{class_2383.field_1321 + d3, class_2383.field_1324 - d3, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d4 : dArray) {
                for (double d5 : dArray2) {
                    class_2338 class_23382 = class_2338.method_49637((double)d4, (double)d, (double)d5);
                    if (!class_6382.method_8320(class_23382).method_51366()) continue;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean ownsCurrentClip() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip != null && (animationClip == this.activeClip || animationClip == this.startClip || animationClip == this.loopClip || animationClip == this.endClip);
    }

    private boolean isPlaying(AnimationClip animationClip) {
        return animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
    }

    private boolean isCurrentOrBlending(AnimationClip animationClip) {
        return animationClip != null && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
    }

    private boolean hasPoseToRebase(PoseHandler poseHandler) {
        return poseHandler != null && poseHandler.getCurrentClip() != null && (poseHandler.isPlaying() || poseHandler.isBlendOutActive());
    }

    private void captureOwnedPhaseTime() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && this.activeClip != null && poseHandler.getCurrentClip() == this.activeClip && (poseHandler.isPlaying() || poseHandler.isBlendOutActive())) {
            this.suspendedPhase = this.phase;
            this.suspendedTime = poseHandler.getCurrentTime();
        }
    }

    private boolean shouldYieldToForeignClip() {
        if (this.phase == Phase.NONE) {
            return false;
        }
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler == null) {
            return false;
        }
        AnimationClip animationClip = poseHandler.getCurrentClip();
        if (animationClip == null) {
            return false;
        }
        return animationClip != this.startClip && animationClip != this.loopClip && animationClip != this.endClip ? poseHandler.isPlaying() : false;
    }

    private void suspendForForeignClip() {
        AnimationClip animationClip;
        PoseHandler poseHandler;
        if (!this.suspendedByForeignClip) {
            this.suspendedByForeignClip = true;
            this.suspendedPhase = this.phase;
        }
        if ((poseHandler = PunchyAnimationManager.POSE_HANDLER) != null && poseHandler.getSecondaryClip() == this.activeClip && PunchyAnimationManager.hasAnyDualHandedEffect(animationClip = poseHandler.getCurrentClip())) {
            poseHandler.clearSecondaryClip();
            this.debugWalkOnWater("clearSecondaryForDualForeign", "foreign=" + WalkOnWaterStateMachine.clipName(animationClip) + " active=" + WalkOnWaterStateMachine.clipName(this.activeClip));
            return;
        }
        if (!HandEquipStateMachine.isOffhandEquipTransitionActive()) {
            this.keepOppositeArmWalkingOnWater();
        }
    }

    private void resumeFromSuspendedState(boolean bl) {
        if (!this.shouldYieldToForeignClip()) {
            boolean bl2;
            this.syncSuspendedTimeFromBackgroundClip();
            Phase phase = this.resolveResumePhaseFromBackgroundClip();
            this.debugWalkOnWater("resume", "trigger=" + bl + " suspendedPhase=" + String.valueOf((Object)this.suspendedPhase) + " resumePhase=" + String.valueOf((Object)phase) + " suspendedTime=" + this.suspendedTime + " active=" + WalkOnWaterStateMachine.clipName(this.activeClip) + " secondary=" + WalkOnWaterStateMachine.clipName(PunchyAnimationManager.POSE_HANDLER.getSecondaryClip()));
            boolean bl3 = bl2 = bl && (phase == Phase.START || phase == Phase.LOOP);
            if (bl2) {
                this.resumePhaseClip(phase, this.suspendedTime);
            } else if (!bl && this.phase != Phase.END) {
                this.startEndPhase();
            }
            this.suspendedByForeignClip = false;
            this.suspendedPhase = Phase.NONE;
            this.suspendedTime = 0.0f;
            if (!bl2) {
                this.pauseLoopAfterResumeBlend = false;
            }
        }
    }

    public void reattachMainhandAfterEquip(class_746 class_7462) {
        PoseHandler poseHandler;
        if (class_7462 != null && this.suspendedByForeignClip && (poseHandler = PunchyAnimationManager.POSE_HANDLER) != null) {
            Phase phase;
            boolean bl = false;
            class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
            if (class_13062 != null && poseHandler.getSecondaryArm() == class_13062 && poseHandler.getSecondaryClip() != null) {
                bl = true;
                this.syncSuspendedTimeFromBackgroundClip();
            }
            Phase phase2 = phase = bl ? this.resolveResumePhaseFromBackgroundClip() : this.suspendedPhase;
            if (this.triggerActiveNow() && (phase == Phase.START || phase == Phase.LOOP)) {
                if (!bl && class_13062 != null) {
                    this.resumePhaseClipWithExplicitOffhand(phase, this.suspendedTime, class_13062);
                } else {
                    this.resumePhaseClip(phase, this.suspendedTime);
                }
                this.suspendedByForeignClip = false;
                this.suspendedPhase = Phase.NONE;
                this.suspendedTime = 0.0f;
            }
        }
    }

    private void syncSuspendedTimeFromBackgroundClip() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && this.activeClip != null && poseHandler.getSecondaryClip() == this.activeClip) {
            this.suspendedTime = Math.max(0.0f, poseHandler.getSecondaryTime());
        }
    }

    private Phase resolveResumePhaseFromBackgroundClip() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler == null) {
            return this.suspendedPhase;
        }
        AnimationClip animationClip = poseHandler.getSecondaryClip();
        if (animationClip == null) {
            return this.suspendedPhase;
        }
        if (animationClip == this.loopClip) {
            return Phase.LOOP;
        }
        return animationClip == this.startClip ? Phase.START : this.suspendedPhase;
    }

    private void resumePhaseClip(Phase phase, float f) {
        AnimationClip animationClip = switch (phase) {
            case START -> this.startClip;
            case LOOP -> this.loopClip;
            default -> null;
        };
        if (animationClip == null) {
            if (this.triggerActiveNow()) {
                this.startStartPhase();
            } else {
                this.resetState();
            }
        } else {
            class_310 class_3102 = class_310.method_1551();
            HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102 != null ? class_3102.field_1724 : null);
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
            boolean bl = this.shouldSplitResumeFromOffhandBackground(poseHandler, animationClip, class_3102);
            float f2 = bl ? poseHandler.getSecondaryTime() : 0.0f;
            class_1306 class_13062 = bl ? poseHandler.getSecondaryArm() : null;
            poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
            poseHandler.setIgnoreDualHanded(bl || this.isAnyWalkOnWaterArmBlocked(class_3102));
            poseHandler.setActiveArm(bl ? this.resolveActiveArm() : this.resolveActiveArmForClip(animationClip));
            poseHandler.setLooping(phase == Phase.LOOP);
            poseHandler.setFrozen(false);
            boolean bl2 = phase == Phase.LOOP && !this.isMovementKeyActive(class_3102);
            poseHandler.setPaused(false);
            poseHandler.setBlendInDurationNext(0.0f);
            float f3 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            float f4 = Math.max(0.0f, f);
            poseHandler.setRebaseReturnSpeedNext(1.15f);
            poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f3, false, f4);
            if (bl && class_13062 != null) {
                if (poseHandler.getSecondaryClip() != animationClip || poseHandler.getSecondaryArm() != class_13062) {
                    poseHandler.setSecondaryClipAtTime(animationClip, class_13062, f3, f2);
                }
                poseHandler.setSecondaryPaused(phase == Phase.LOOP && !this.isMovementKeyActive(class_3102));
            }
            this.pauseLoopAfterResumeBlend = bl2;
            this.phase = phase;
            this.activeClip = animationClip;
            this.logPhaseTransition(phase == Phase.START ? "resume_start" : "resume_loop", animationClip, Float.valueOf(f4));
        }
    }

    private void resumePhaseClipWithExplicitOffhand(Phase phase, float f, class_1306 class_13062) {
        AnimationClip animationClip = switch (phase) {
            case START -> this.startClip;
            case LOOP -> this.loopClip;
            default -> null;
        };
        if (animationClip == null) {
            if (this.triggerActiveNow()) {
                this.startStartPhase();
            } else {
                this.resetState();
            }
        } else {
            boolean bl;
            class_310 class_3102 = class_310.method_1551();
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
            poseHandler.clearRequestedArmFilters();
            poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
            boolean bl2 = !this.isAnyWalkOnWaterArmBlocked(class_3102) && PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            this.debugWalkOnWater("resumeExplicitOffhand.begin", "phaseToResume=" + String.valueOf((Object)phase) + " time=" + f + " offArm=" + String.valueOf(class_13062) + " dualHanded=" + bl2 + " current=" + WalkOnWaterStateMachine.clipName(poseHandler.getCurrentClip()) + " secondary=" + WalkOnWaterStateMachine.clipName(poseHandler.getSecondaryClip()) + " activeArm=" + String.valueOf(poseHandler.getActiveArm()) + " secondaryArm=" + String.valueOf(poseHandler.getSecondaryArm()));
            poseHandler.setIgnoreDualHanded(false);
            poseHandler.setActiveArm(bl2 ? null : this.resolveActiveArm());
            poseHandler.setLooping(phase == Phase.LOOP);
            poseHandler.setFrozen(false);
            poseHandler.setPaused(false);
            poseHandler.setBlendInDurationNext(0.0f);
            float f2 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            float f3 = Math.max(0.0f, f);
            poseHandler.setRebaseReturnSpeedNext(1.15f);
            poseHandler.setRebaseUseBothArmsForNextPlay(bl2);
            poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f2, false, f3);
            this.debugWalkOnWater("resumeExplicitOffhand.afterPlay", "current=" + WalkOnWaterStateMachine.clipName(poseHandler.getCurrentClip()) + " currentTime=" + poseHandler.getCurrentTime() + " activeArm=" + String.valueOf(poseHandler.getActiveArm()) + " secondary=" + WalkOnWaterStateMachine.clipName(poseHandler.getSecondaryClip()) + " secondaryArm=" + String.valueOf(poseHandler.getSecondaryArm()));
            boolean bl3 = bl = phase == Phase.LOOP && !this.isMovementKeyActive(class_3102);
            if (!bl2) {
                float f4 = Math.max(0.0f, poseHandler.getCurrentTime());
                if (!this.isArmBlockedByWalkOnWaterFlag(class_3102, class_13062)) {
                    poseHandler.setSecondaryClipAtTimeWithBlendFromCurrent(animationClip, class_13062, f2, f4);
                    poseHandler.requestBlendInArm(class_13062);
                    poseHandler.setSecondaryPaused(bl);
                } else {
                    poseHandler.clearSecondaryClip();
                }
                this.debugWalkOnWater("resumeExplicitOffhand.secondary", "syncedTime=" + f4 + " secondary=" + WalkOnWaterStateMachine.clipName(poseHandler.getSecondaryClip()) + " secondaryArm=" + String.valueOf(poseHandler.getSecondaryArm()) + " secondaryTime=" + poseHandler.getSecondaryTime() + " paused=" + bl);
            } else {
                poseHandler.clearSecondaryClip();
            }
            HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102 != null ? class_3102.field_1724 : null);
            this.debugWalkOnWater("resumeExplicitOffhand.end", "current=" + WalkOnWaterStateMachine.clipName(poseHandler.getCurrentClip()) + " currentTime=" + poseHandler.getCurrentTime() + " activeArm=" + String.valueOf(poseHandler.getActiveArm()) + " secondary=" + WalkOnWaterStateMachine.clipName(poseHandler.getSecondaryClip()) + " secondaryArm=" + String.valueOf(poseHandler.getSecondaryArm()) + " secondaryTime=" + poseHandler.getSecondaryTime());
            this.pauseLoopAfterResumeBlend = bl;
            this.phase = phase;
            this.activeClip = animationClip;
            this.logPhaseTransition(phase == Phase.START ? "resume_start_offhand" : "resume_loop_offhand", animationClip, Float.valueOf(Math.max(0.0f, poseHandler.getCurrentTime())));
        }
    }

    private boolean shouldSplitResumeFromOffhandBackground(PoseHandler poseHandler, AnimationClip animationClip, class_310 class_3102) {
        if (poseHandler != null && animationClip != null && class_3102 != null && class_3102.field_1724 != null) {
            AnimationClip animationClip2 = poseHandler.getSecondaryClip();
            if (animationClip2 != animationClip) {
                return false;
            }
            class_1306 class_13062 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
            return class_13062 != null && poseHandler.getSecondaryArm() == class_13062;
        }
        return false;
    }

    private boolean triggerActiveNow() {
        class_310 class_3102 = class_310.method_1551();
        return this.evaluateTrigger(class_3102).active();
    }

    public boolean isTriggerActiveOrInGap(class_310 class_3102) {
        return class_3102 == null ? false : this.applyInWaterTriggerGap(class_3102, this.evaluateTrigger(class_3102)).active();
    }

    private void keepOppositeArmWalkingOnWater() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && this.activeClip != null && this.suspendedPhase != Phase.END && !this.isOffhandEquipCurrentlyVisible(poseHandler)) {
            boolean bl = this.isMovementKeyActive(class_310.method_1551());
            class_1306 class_13062 = poseHandler.getActiveArm();
            if (class_13062 != null) {
                class_1306 class_13063 = class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                class_1306 class_13064 = this.resolveActiveArmForClip(this.activeClip);
                if (!(class_13064 != null && class_13063 != class_13064 || this.isArmBlockedByWalkOnWaterFlag(class_310.method_1551(), class_13063))) {
                    AnimationClip animationClip = poseHandler.getSecondaryClip();
                    if (animationClip == this.activeClip && poseHandler.getSecondaryArm() == class_13063) {
                        poseHandler.setSecondaryPaused(!bl && this.activeClip == this.loopClip);
                    } else {
                        float f = this.activeClip.getLength();
                        float f2 = Math.max(0.0f, this.suspendedTime);
                        if (f > 0.0f) {
                            f2 = this.activeClip.isLoop() ? f2 % f : Math.min(f2, f);
                        }
                        poseHandler.setSecondaryClipAtTime(this.activeClip, class_13063, PunchyAnimationManager.clipSpeedMultiplier(this.activeClip), f2);
                        poseHandler.setSecondaryPaused(!bl && this.activeClip == this.loopClip);
                        this.debugWalkOnWater("keepOpposite", "active=" + WalkOnWaterStateMachine.clipName(this.activeClip) + " overlapArm=" + String.valueOf(class_13063) + " startAt=" + f2 + " movement=" + bl);
                    }
                }
            }
        }
    }

    private boolean isOffhandEquipCurrentlyVisible(PoseHandler poseHandler) {
        class_310 class_3102 = class_310.method_1551();
        if (poseHandler != null && class_3102 != null && class_3102.field_1724 != null) {
            class_1306 class_13062 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
            if (class_13062 == null) {
                return false;
            }
            AnimationClip animationClip = poseHandler.getCurrentClip();
            if (animationClip == null || !this.isEquipClip(animationClip) || poseHandler.getActiveArm() != null && poseHandler.getActiveArm() != class_13062) {
                AnimationClip animationClip2 = poseHandler.getSecondaryClip();
                return animationClip2 != null && this.isEquipClip(animationClip2) && poseHandler.getSecondaryArm() == class_13062;
            }
            return true;
        }
        return false;
    }

    private boolean isEquipClip(AnimationClip animationClip) {
        String string;
        if (animationClip == null) {
            return false;
        }
        String string2 = animationClip.getName();
        if (string2 != null && ((string = string2.trim().toLowerCase(Locale.ROOT)).equals("hand_in") || string.equals("hand_out") || string.startsWith("hand_in_") || string.startsWith("hand_out_"))) {
            return true;
        }
        return PunchyAnimationManager.isClipNamed(animationClip, "hand_in") || PunchyAnimationManager.isClipNamed(animationClip, "hand_out");
    }

    private void debugWalkOnWater(String string, String string2) {
    }

    private String stackId(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            return class_29602 == null ? "unknown" : class_29602.toString();
        }
        return "empty";
    }

    private boolean shouldStopEndClipEarly(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && this.endClip != null) {
            if (!PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.POSE_HANDLER.getCurrentClip() != this.endClip) {
                return false;
            }
            if (class_3102.field_1724.method_6079() != null && !class_3102.field_1724.method_6079().method_7960()) {
                Float f = this.findTimelineEffectTime(this.endClip, "stop_if_offhand_item");
                return f == null ? false : PunchyAnimationManager.POSE_HANDLER.getCurrentTime() >= f.floatValue();
            }
            return false;
        }
        return false;
    }

    private Float resolveStartClipTime(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || this.startClip == null) {
            return null;
        }
        if (class_3102.field_1724.method_6079() != null && !class_3102.field_1724.method_6079().method_7960()) {
            Float f = this.findTimelineEffectTime(this.startClip, "start_here_if_off_hand_item");
            return f != null ? f : this.findTimelineEffectTime(this.startClip, "start_if_offhand_item");
        }
        return null;
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

    private boolean isMovementKeyActive(class_310 class_3102) {
        return class_3102 != null && class_3102.field_1690 != null ? class_3102.field_1690.field_1894.method_1434() || class_3102.field_1690.field_1881.method_1434() || class_3102.field_1690.field_1913.method_1434() || class_3102.field_1690.field_1849.method_1434() : false;
    }

    private class_1306 resolveActiveArm() {
        return this.resolveWalkOnWaterActiveArm(class_310.method_1551());
    }

    private class_1306 resolveWalkOnWaterActiveArm(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1306 class_13062 = this.resolveArm(class_3102.field_1724, class_1268.field_5808);
            class_1306 class_13063 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
            boolean bl = this.hasFishingRodWalkDisable(class_3102.field_1724.method_6047());
            boolean bl2 = this.hasFishingRodWalkDisable(class_3102.field_1724.method_6079());
            if (bl && bl2) {
                this.debugWalkOnWater("resolveArm", "both_arms_blocked=true");
                return null;
            }
            if (bl) {
                return class_13063;
            }
            if (bl2) {
                return class_13062;
            }
            class_1306 class_13064 = PunchyAnimationManager.getUseArm(class_3102, class_1268.field_5808);
            this.debugWalkOnWater("resolveArm", "mainBlocked=" + bl + " offBlocked=" + bl2 + " resolved=" + String.valueOf(class_13064));
            return class_13064;
        }
        return null;
    }

    private boolean isArmBlockedByWalkOnWaterFlag(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            class_1306 class_13063 = this.resolveArm(class_3102.field_1724, class_1268.field_5808);
            class_1306 class_13064 = this.resolveArm(class_3102.field_1724, class_1268.field_5810);
            if (class_13062 == class_13063) {
                return this.hasFishingRodWalkDisable(class_3102.field_1724.method_6047());
            }
            return class_13062 == class_13064 ? this.hasFishingRodWalkDisable(class_3102.field_1724.method_6079()) : false;
        }
        return false;
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

    private class_1306 resolveActiveArmForClip(AnimationClip animationClip) {
        if (this.isAnyWalkOnWaterArmBlocked(class_310.method_1551())) {
            return this.resolveActiveArm();
        }
        return PunchyAnimationManager.isDualHandedActive(class_310.method_1551(), animationClip, 0.0f) ? null : this.resolveActiveArm();
    }

    private boolean isAnyWalkOnWaterArmBlocked(class_310 class_3102) {
        return class_3102 != null && class_3102.field_1724 != null ? this.hasFishingRodWalkDisable(class_3102.field_1724.method_6047()) || this.hasFishingRodWalkDisable(class_3102.field_1724.method_6079()) : false;
    }

    private boolean hasStackChanged(class_1799 class_17992, class_1799 class_17993) {
        class_1799 class_17994 = class_17992 == null ? class_1799.field_8037 : class_17992;
        class_1799 class_17995 = class_17993 == null ? class_1799.field_8037 : class_17993;
        return !class_1799.method_31577((class_1799)class_17994, (class_1799)class_17995);
    }

    private class_1799 copyStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7972() : class_1799.field_8037;
    }

    private boolean matchesActiveSelection(WalkOnWaterSelection walkOnWaterSelection) {
        return walkOnWaterSelection != null && walkOnWaterSelection.isValid() ? walkOnWaterSelection.start() == this.startClip && walkOnWaterSelection.loop() == this.loopClip && walkOnWaterSelection.end() == this.endClip : false;
    }

    private boolean isWalkOnWaterPhaseClip(AnimationClip animationClip) {
        return animationClip != null && (animationClip == this.startClip || animationClip == this.loopClip || animationClip == this.endClip);
    }

    public boolean isCurrentPoseOwnedByWalkOnWater() {
        if (this.phase != Phase.NONE && this.ownsCurrentClip()) {
            return true;
        }
        if (this.handoffClip == null) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != this.handoffClip || !PunchyAnimationManager.POSE_HANDLER.isPlaying() && !PunchyAnimationManager.POSE_HANDLER.isBlendOutActive()) {
            this.handoffClip = null;
            return false;
        }
        return true;
    }

    public boolean isActiveOrHandingOff() {
        if (this.phase != Phase.NONE || this.suspendedByForeignClip) {
            return true;
        }
        if (this.handoffClip == null) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != this.handoffClip || !PunchyAnimationManager.POSE_HANDLER.isPlaying() && !PunchyAnimationManager.POSE_HANDLER.isBlendOutActive()) {
            this.handoffClip = null;
            return false;
        }
        return true;
    }

    private void logTriggerState(class_310 class_3102, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, WalkOnWaterSelection walkOnWaterSelection, TriggerState triggerState) {
        this.lastTriggerActive = triggerState.active();
        this.lastTriggerReason = triggerState.reason();
    }

    private void logResolvedSelection(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, WalkOnWaterSelection walkOnWaterSelection) {
        String string = "entry=" + (customAnimationEntry == null ? "null" : customAnimationEntry.type) + " start=" + WalkOnWaterStateMachine.clipName(walkOnWaterSelection.start()) + " loop=" + WalkOnWaterStateMachine.clipName(walkOnWaterSelection.loop()) + " end=" + WalkOnWaterStateMachine.clipName(walkOnWaterSelection.end()) + " valid=" + walkOnWaterSelection.isValid();
        if (!WalkOnWaterStateMachine.safeEquals(this.lastResolvedSummary, string)) {
            this.lastResolvedSummary = string;
        }
    }

    private void logPhaseTransition(String string, AnimationClip animationClip) {
        this.logPhaseTransition(string, animationClip, null);
    }

    private void logPhaseTransition(String string, AnimationClip animationClip, Float f) {
        if (this.phase != this.lastLoggedPhase || this.activeClip != animationClip) {
            this.lastLoggedPhase = this.phase;
        }
    }

    private static String clipName(AnimationClip animationClip) {
        return animationClip == null ? "null" : animationClip.getName();
    }

    private static boolean safeEquals(String string, String string2) {
        return string == null ? string2 == null : string.equals(string2);
    }

    static enum Phase {
        NONE,
        START,
        LOOP,
        END;

    }

    record WalkOnWaterSelection(AnimationClip start, AnimationClip loop, AnimationClip end) {
        static final WalkOnWaterSelection INVALID = new WalkOnWaterSelection(null, null, null);

        boolean isValid() {
            return this.start != null && this.loop != null && this.end != null;
        }
    }

    record TriggerState(boolean active, String reason) {
    }
}

