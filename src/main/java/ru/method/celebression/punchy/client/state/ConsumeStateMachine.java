package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1839;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class ConsumeStateMachine {
    private static volatile boolean equipBlockedByConsumeAnimation;
    private static volatile boolean suppressUseAfterConsume;
    private static volatile class_1306 forcedRenderArm;
    private static volatile class_1799 forcedRenderStack;
    private boolean wasConsuming;
    private boolean finishingConsume;
    private boolean swapOnLastFramePending;
    private boolean swappedOnLastFrame;
    private AnimationClip activeClip;
    private class_1268 activeHand;
    private boolean skipBlendOut;
    private ConsumeV2Phase consumeV2Phase = ConsumeV2Phase.NONE;
    private AnimationClip consumeV2StartClip;
    private AnimationClip consumeV2LoopClip;
    private AnimationClip consumeV2EndClip;
    private PunchyTuningConfig.CustomAnimationEntry consumeV2Entry;
    private class_1799 lastConsumeV2Stack = class_1799.field_8037;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            Object object;
            boolean bl2;
            boolean bl3;
            boolean bl4 = bl3 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            if (!bl3) {
                suppressUseAfterConsume = false;
            }
            boolean bl5 = class_3102.field_1724.method_6115();
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl6 = bl2 = bl5 && bl3 && ConsumeStateMachine.isConsumeStack(class_17992);
            if (this.finishingConsume) {
                if (bl2) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.finishingConsume = false;
                    this.swapOnLastFramePending = false;
                    this.swappedOnLastFrame = false;
                    this.activeClip = null;
                    this.skipBlendOut = false;
                    this.resetConsumeV2();
                    PunchyAnimationManager.clearSourceHand();
                }
                if (this.shouldReleaseFinishingForEquip(class_3102)) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.finishingConsume = false;
                    this.swapOnLastFramePending = false;
                    this.swappedOnLastFrame = false;
                    this.activeClip = null;
                    this.skipBlendOut = false;
                    this.resetConsumeV2();
                    PunchyAnimationManager.clearSourceHand();
                    ConsumeStateMachine.clearForcedRenderStack();
                    equipBlockedByConsumeAnimation = false;
                }
                UseActionTracker.clear();
                object = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                boolean bl7 = bl = object != null && this.activeClip != null && object == this.activeClip && (PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.POSE_HANDLER.isBlendOutActive());
                if (bl) {
                    if (this.swapOnLastFramePending && !this.swappedOnLastFrame) {
                        float f = PunchyAnimationManager.POSE_HANDLER.getCurrentTime();
                        float f2 = this.activeClip.getLength();
                        if (f2 <= 0.0f || f >= f2 - 1.0E-4f) {
                            ConsumeStateMachine.clearForcedRenderStack();
                            HandEquipStateMachine.requestRenderedSync();
                            this.swappedOnLastFrame = true;
                        }
                    }
                    equipBlockedByConsumeAnimation = true;
                    return;
                }
                this.finishingConsume = false;
                this.swapOnLastFramePending = false;
                this.swappedOnLastFrame = false;
                this.activeClip = null;
                this.activeHand = null;
                this.skipBlendOut = false;
                this.resetConsumeV2();
                PunchyAnimationManager.clearSourceHand();
                ConsumeStateMachine.clearForcedRenderStack();
                suppressUseAfterConsume = true;
            }
            class_1268 consumingHand = class_3102.field_1724.method_6058();
            if (consumingHand == null) {
                consumingHand = class_1268.field_5808;
            }
            if (bl2 && this.wasConsuming && this.activeHand != null && consumingHand != this.activeHand) {
                this.interruptConsumeForHandSwap(class_3102, consumingHand);
            } else if (!bl2 || !HandEquipStateMachine.isEquipInProgress() || HandEquipStateMachine.abortEquipForAction(class_3102, consumingHand)) {
                if (bl2) {
                    UseActionTracker.clear();
                    suppressUseAfterConsume = true;
                    equipBlockedByConsumeAnimation = true;
                    if (PunchyAnimationManager.isHandBlacklisted(class_3102, consumingHand)) {
                        if (this.wasConsuming) {
                            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                            PunchyAnimationManager.clearSourceHand();
                        }
                        this.wasConsuming = false;
                        this.finishingConsume = false;
                        this.activeClip = null;
                        this.activeHand = null;
                        equipBlockedByConsumeAnimation = false;
                        this.resetConsumeV2();
                        ConsumeStateMachine.clearForcedRenderStack();
                    } else {
                        ConsumeSelection consumeSelection = this.resolveConsumeClip(class_17992);
                        if (consumeSelection.isV2()) {
                            this.tickConsumeV2(class_3102, consumingHand, class_17992, consumeSelection);
                        } else {
                            AnimationClip animationClip = consumeSelection.clip;
                            if (!(animationClip == null || this.wasConsuming && this.activeClip == animationClip && PunchyAnimationManager.POSE_HANDLER.isPlaying())) {
                                this.playConsume(class_3102, consumingHand, class_17992, animationClip, consumeSelection.entry);
                            }
                            if (this.consumeV2Phase != ConsumeV2Phase.NONE) {
                                this.resetConsumeV2();
                            }
                        }
                        this.wasConsuming = true;
                        this.finishingConsume = false;
                        this.swapOnLastFramePending = false;
                        this.swappedOnLastFrame = false;
                        this.activeHand = consumingHand;
                    }
                } else {
                    if (this.wasConsuming) {
                        boolean bl8;
                        bl = this.isFunctionalSwapDuringConsume(class_3102);
                        boolean bl9 = bl8 = !bl5 && bl3 && !bl;
                        if (this.consumeV2Phase != ConsumeV2Phase.NONE && this.consumeV2EndClip != null) {
                            this.transitionToConsumeV2End(this.activeHand);
                            this.finishingConsume = true;
                            this.swapOnLastFramePending = bl8;
                            this.swappedOnLastFrame = false;
                            equipBlockedByConsumeAnimation = true;
                            suppressUseAfterConsume = true;
                        } else if (bl8) {
                            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                            if (this.skipBlendOut) {
                                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
                            }
                            PunchyAnimationManager.POSE_HANDLER.releaseHold();
                            PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
                            this.finishingConsume = true;
                            this.swapOnLastFramePending = true;
                            this.swappedOnLastFrame = false;
                            equipBlockedByConsumeAnimation = true;
                            suppressUseAfterConsume = true;
                        } else {
                            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                            if (this.skipBlendOut) {
                                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
                            }
                            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                            this.finishingConsume = false;
                            this.swapOnLastFramePending = false;
                            this.swappedOnLastFrame = false;
                            equipBlockedByConsumeAnimation = false;
                            this.resetConsumeV2();
                            ConsumeStateMachine.clearForcedRenderStack();
                            if (bl) {
                                HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102.field_1724);
                                HandEquipStateMachine.requestRenderedSync();
                            }
                        }
                    }
                    this.wasConsuming = false;
                    if (!this.finishingConsume) {
                        this.swapOnLastFramePending = false;
                        this.swappedOnLastFrame = false;
                        equipBlockedByConsumeAnimation = false;
                        this.resetConsumeV2();
                        ConsumeStateMachine.clearForcedRenderStack();
                    }
                }
            }
        } else {
            equipBlockedByConsumeAnimation = false;
            ConsumeStateMachine.clearForcedRenderStack();
        }
    }

    public static boolean isEquipBlockedByConsumeAnimation() {
        return equipBlockedByConsumeAnimation;
    }

    public static boolean isUseSuppressedAfterConsume() {
        return suppressUseAfterConsume;
    }

    public static class_1799 getForcedRenderStack(class_1306 class_13062) {
        if (class_13062 == null) {
            return class_1799.field_8037;
        }
        return forcedRenderArm == class_13062 && forcedRenderStack != null && !forcedRenderStack.method_7960() ? forcedRenderStack : class_1799.field_8037;
    }

    public static String getForcedConsumeTypeForArm(class_1306 class_13062) {
        if (class_13062 == null) {
            return null;
        }
        if (forcedRenderArm == class_13062 && forcedRenderStack != null && !forcedRenderStack.method_7960()) {
            class_1839 class_18392 = forcedRenderStack.method_7976();
            if (class_18392 == class_1839.field_8946) {
                return "useDrink";
            }
            return class_18392 == class_1839.field_8950 ? "useEat" : null;
        }
        return null;
    }

    private void updateForcedRenderStack(class_1268 class_12682, class_1799 class_17992) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null && class_12682 != null) {
            class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            if (class_17992 != null && !class_17992.method_7960()) {
                forcedRenderArm = class_13062;
                forcedRenderStack = class_17992.method_7972();
            }
        }
    }

    private static void clearForcedRenderStack() {
        forcedRenderArm = null;
        forcedRenderStack = class_1799.field_8037;
    }

    private void playConsume(class_310 class_3102, class_1268 class_12682, class_1799 class_17992, AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        PunchyAnimationManager.setSourceHand(class_3102, class_12682);
        this.updateForcedRenderStack(class_12682, class_17992);
        class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
        PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
        boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose() || customAnimationEntry == null || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
        PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl, animationClip.isHoldOnLastFrame());
        this.activeClip = animationClip;
        this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry);
    }

    private void tickConsumeV2(class_310 class_3102, class_1268 class_12682, class_1799 class_17992, ConsumeSelection consumeSelection) {
        if (!consumeSelection.isV2Valid()) {
            this.resetConsumeV2();
        } else {
            boolean bl;
            boolean bl2 = bl = this.consumeV2Phase == ConsumeV2Phase.NONE || this.consumeV2Entry != consumeSelection.entry || this.hasConsumeStackChanged(this.lastConsumeV2Stack, class_17992);
            if (bl) {
                this.consumeV2Entry = consumeSelection.entry;
                this.consumeV2StartClip = consumeSelection.start;
                this.consumeV2LoopClip = consumeSelection.loop;
                this.consumeV2EndClip = consumeSelection.end;
                this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(consumeSelection.entry);
                this.startConsumeV2(class_3102, class_12682, class_17992, this.consumeV2StartClip, consumeSelection.entry);
                this.lastConsumeV2Stack = this.copyStack(class_17992);
            } else {
                this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(consumeSelection.entry);
                PoseHandlerWrapper poseHandlerWrapper = new PoseHandlerWrapper();
                switch (this.consumeV2Phase.ordinal()) {
                    case 1: {
                        if (poseHandlerWrapper.isPlaying(this.consumeV2StartClip)) break;
                        this.transitionToConsumeV2Loop(class_3102, class_12682, this.consumeV2LoopClip, consumeSelection.entry);
                        break;
                    }
                    case 2: {
                        if (!poseHandlerWrapper.isPlaying(this.consumeV2LoopClip)) {
                            this.transitionToConsumeV2Loop(class_3102, class_12682, this.consumeV2LoopClip, consumeSelection.entry);
                            break;
                        }
                        PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                        break;
                    }
                    case 3: {
                        if (poseHandlerWrapper.isPlaying(this.consumeV2EndClip) || poseHandlerWrapper.isCurrentOrBlending(this.consumeV2EndClip)) break;
                        this.resetConsumeV2();
                    }
                }
                this.lastConsumeV2Stack = this.copyStack(class_17992);
            }
        }
    }

    private void startConsumeV2(class_310 class_3102, class_1268 class_12682, class_1799 class_17992, AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 != null && animationClip != null) {
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            this.updateForcedRenderStack(class_12682, class_17992);
            class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose() || customAnimationEntry == null || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl, false);
            this.activeClip = animationClip;
            this.consumeV2Phase = ConsumeV2Phase.START;
        }
    }

    private void transitionToConsumeV2Loop(class_310 class_3102, class_1268 class_12682, AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 != null && animationClip != null) {
            class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(true);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose() || customAnimationEntry == null || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
            PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.06f);
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl, false);
            this.activeClip = animationClip;
            this.consumeV2Phase = ConsumeV2Phase.LOOP;
        }
    }

    private void transitionToConsumeV2End(class_1268 class_12682) {
        if (this.consumeV2EndClip != null) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null) {
                PunchyAnimationManager.setSourceHand(class_3102, class_12682 == null ? class_1268.field_5808 : class_12682);
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682 == null ? class_1268.field_5808 : class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(this.consumeV2EndClip);
            PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.0f);
            PunchyAnimationManager.POSE_HANDLER.play(this.consumeV2EndClip, f, true, false);
            this.activeClip = this.consumeV2EndClip;
            this.consumeV2Phase = ConsumeV2Phase.END;
        }
    }

    private boolean hasConsumeStackChanged(class_1799 class_17992, class_1799 class_17993) {
        class_1799 class_17994 = class_17992 == null ? class_1799.field_8037 : class_17992;
        class_1799 class_17995 = class_17993 == null ? class_1799.field_8037 : class_17993;
        return !class_1799.method_31577((class_1799)class_17994, (class_1799)class_17995);
    }

    private class_1799 copyStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7972() : class_1799.field_8037;
    }

    private void resetConsumeV2() {
        this.consumeV2Phase = ConsumeV2Phase.NONE;
        this.consumeV2StartClip = null;
        this.consumeV2LoopClip = null;
        this.consumeV2EndClip = null;
        this.consumeV2Entry = null;
        this.lastConsumeV2Stack = class_1799.field_8037;
    }

    public static boolean isConsumeStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (ConsumeStateMachine.isThrowableDrinkLikeStack(class_17992)) {
            return false;
        }
        class_1839 class_18392 = class_17992.method_7976();
        return class_18392 == class_1839.field_8950 || class_18392 == class_1839.field_8946;
    }

    private static boolean isThrowableDrinkLikeStack(class_1799 class_17992) {
        return class_17992.method_31574(class_1802.field_8436) || class_17992.method_31574(class_1802.field_8150) || class_17992.method_31574(class_1802.field_8287) || class_17992.method_31574(class_1802.field_8543) || class_17992.method_31574(class_1802.field_8803) || class_17992.method_31574(class_1802.field_8634) || class_17992.method_31574(class_1802.field_8449);
    }

    private ConsumeSelection resolveConsumeClip(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_1839 class_18392 = class_17992.method_7976();
            class_310 class_3102 = class_310.method_1551();
            class_1268 class_12682 = class_3102 != null && class_3102.field_1724 != null ? class_3102.field_1724.method_6058() : class_1268.field_5808;
            if (class_12682 == null) {
                class_12682 = class_1268.field_5808;
            }
            PunchyTuningConfig.CustomAnimationEntry v2Entry = this.findConsumeV2Entry(class_17992, class_18392, false);
            if (v2Entry != null) {
                ConsumeSelection selection = this.resolveConsumeV2Selection(class_3102, class_12682, class_17992, v2Entry);
                if (selection.isV2Valid()) return selection;
            }
            String typeName = class_18392 == class_1839.field_8950 ? "useEat" : "useDrink";
            PunchyTuningConfig.CustomAnimationEntry legacyEntry = this.findConsumeLegacyEntry(class_17992, class_18392, false);
            if (legacyEntry != null) {
                CustomAnimationResolver.ClipLookup lookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, typeName, legacyEntry, class_3102, class_12682);
                if (lookup.clip() != null) return ConsumeSelection.single(lookup.clip(), legacyEntry);
            }
            PunchyTuningConfig.CustomAnimationEntry globalLegacyEntry = this.findConsumeLegacyEntry(class_17992, class_18392, true);
            if (globalLegacyEntry != null) {
                CustomAnimationResolver.ClipLookup lookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, typeName, globalLegacyEntry, class_3102, class_12682);
                if (lookup.clip() != null) return ConsumeSelection.single(lookup.clip(), globalLegacyEntry);
            }
            PunchyTuningConfig.CustomAnimationEntry globalV2Entry = this.findConsumeV2Entry(class_17992, class_18392, true);
            if (globalV2Entry != null) {
                ConsumeSelection selection = this.resolveConsumeV2Selection(class_3102, class_12682, class_17992, globalV2Entry);
                if (selection.isV2Valid()) return selection;
            }
            if (class_18392 == class_1839.field_8950) {
                return ConsumeSelection.single(PunchyAnimationManager.resolveHandedClip("use_eat", class_3102, class_12682), null);
            }
            return class_18392 == class_1839.field_8946 ? ConsumeSelection.single(PunchyAnimationManager.resolveHandedClip("use_drink", class_3102, class_12682), null) : ConsumeSelection.single(null, null);
        }
        return ConsumeSelection.single(null, null);
    }

    private PunchyTuningConfig.CustomAnimationEntry findConsumeV2Entry(class_1799 class_17992, class_1839 class_18392, boolean bl) {
        if (class_18392 == class_1839.field_8950) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "eating", bl);
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "eating_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useitem_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useeat_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_eat_v2", bl);
            }
            return customAnimationEntry;
        }
        if (class_18392 == class_1839.field_8946) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "drinking", bl);
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "drinking_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useitem_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "usedrink_v2", bl);
            }
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_drink_v2", bl);
            }
            return customAnimationEntry;
        }
        return null;
    }

    private PunchyTuningConfig.CustomAnimationEntry findConsumeLegacyEntry(class_1799 class_17992, class_1839 class_18392, boolean bl) {
        if (class_18392 == class_1839.field_8950) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useEat", bl);
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_eat", bl);
            }
            return customAnimationEntry;
        }
        if (class_18392 == class_1839.field_8946) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useDrink", bl);
            if (customAnimationEntry == null) {
                customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_drink", bl);
            }
            return customAnimationEntry;
        }
        return null;
    }

    public boolean isActive() {
        return this.wasConsuming && this.activeClip != null;
    }

    private boolean shouldReleaseFinishingForEquip(class_310 class_3102) {
        if (this.finishingConsume && class_3102 != null && class_3102.field_1724 != null && this.activeHand != null) {
            class_1799 class_17992;
            class_1799 class_17993 = class_17992 = forcedRenderStack == null ? class_1799.field_8037 : forcedRenderStack;
            if (class_17992.method_7960()) {
                return false;
            }
            class_1799 class_17994 = class_3102.field_1724.method_5998(this.activeHand);
            class_1799 class_17995 = class_17994 == null ? class_1799.field_8037 : class_17994;
            return !class_1799.method_31577((class_1799)class_17992, (class_1799)class_17995);
        }
        return false;
    }

    private void interruptConsumeForHandSwap(class_310 class_3102, class_1268 class_12682) {
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
        PunchyAnimationManager.clearSourceHand();
        this.wasConsuming = false;
        this.finishingConsume = false;
        this.swapOnLastFramePending = false;
        this.swappedOnLastFrame = false;
        this.activeClip = null;
        this.activeHand = null;
        this.skipBlendOut = false;
        suppressUseAfterConsume = false;
        equipBlockedByConsumeAnimation = false;
        this.resetConsumeV2();
        ConsumeStateMachine.clearForcedRenderStack();
        HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102.field_1724);
        HandEquipStateMachine.requestRenderedSync();
        if (class_12682 == class_1268.field_5810) {
            HandEquipStateMachine.triggerPickupEquipCycle(class_3102, class_1268.field_5810);
        }
    }

    private boolean isFunctionalSwapDuringConsume(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && this.activeHand != null) {
            class_1799 class_17992;
            class_1799 class_17993 = class_17992 = forcedRenderStack == null ? class_1799.field_8037 : forcedRenderStack;
            if (class_17992.method_7960()) {
                return false;
            }
            class_1799 class_17994 = class_3102.field_1724.method_5998(this.activeHand);
            class_1268 class_12682 = this.activeHand == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
            class_1799 class_17995 = class_3102.field_1724.method_5998(class_12682);
            return !class_1799.method_31577((class_1799)class_17992, (class_1799)class_17994) && class_1799.method_31577((class_1799)class_17992, (class_1799)class_17995);
        }
        return false;
    }

    private ConsumeSelection resolveConsumeV2Selection(class_310 class_3102, class_1268 class_12682, class_1799 class_17992, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 != null && customAnimationEntry != null) {
            String string = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry);
            AnimationClip animationClip = this.resolveNamedClip(CustomAnimationResolver.resolveStartName(customAnimationEntry), class_3102, class_12682, string);
            AnimationClip animationClip2 = this.resolveNamedClip(CustomAnimationResolver.resolveLoopingName(customAnimationEntry), class_3102, class_12682, string);
            AnimationClip animationClip3 = this.resolveNamedClip(CustomAnimationResolver.resolveEndName(customAnimationEntry), class_3102, class_12682, string);
            return ConsumeSelection.v2(animationClip, animationClip2, animationClip3, customAnimationEntry);
        }
        return ConsumeSelection.single(null, null);
    }

    private AnimationClip resolveNamedClip(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        return string != null && !string.isBlank() ? PunchyAnimationManager.resolveNamedClip(string, class_3102, class_12682, string2) : null;
    }

    static {
        forcedRenderStack = class_1799.field_8037;
    }

    static enum ConsumeV2Phase {
        NONE,
        START,
        LOOP,
        END;

    }

    static final class ConsumeSelection {
        final AnimationClip clip;
        final PunchyTuningConfig.CustomAnimationEntry entry;
        final AnimationClip start;
        final AnimationClip loop;
        final AnimationClip end;

        private ConsumeSelection(AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, AnimationClip animationClip2, AnimationClip animationClip3, AnimationClip animationClip4) {
            this.clip = animationClip;
            this.entry = customAnimationEntry;
            this.start = animationClip2;
            this.loop = animationClip3;
            this.end = animationClip4;
        }

        static ConsumeSelection single(AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
            return new ConsumeSelection(animationClip, customAnimationEntry, null, null, null);
        }

        static ConsumeSelection v2(AnimationClip animationClip, AnimationClip animationClip2, AnimationClip animationClip3, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
            return new ConsumeSelection(animationClip, customAnimationEntry, animationClip, animationClip2, animationClip3);
        }

        boolean isV2() {
            return this.start != null || this.loop != null || this.end != null;
        }

        boolean isV2Valid() {
            return this.start != null && this.loop != null && this.end != null;
        }

        @Override
        public final String toString() {
            return "ConsumeSelection[" + "clip=" + this.clip + ", " + "entry=" + this.entry + ", " + "start=" + this.start + ", " + "loop=" + this.loop + ", " + "end=" + this.end + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.clip, this.entry, this.start, this.loop, this.end);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ConsumeSelection other)) return false;
            return java.util.Objects.deepEquals(this.clip, other.clip)
                && java.util.Objects.deepEquals(this.entry, other.entry)
                && java.util.Objects.deepEquals(this.start, other.start)
                && java.util.Objects.deepEquals(this.loop, other.loop)
                && java.util.Objects.deepEquals(this.end, other.end);
        }

        public AnimationClip clip() {
            return this.clip;
        }

        public PunchyTuningConfig.CustomAnimationEntry entry() {
            return this.entry;
        }

        public AnimationClip start() {
            return this.start;
        }

        public AnimationClip loop() {
            return this.loop;
        }

        public AnimationClip end() {
            return this.end;
        }
    }

    static final class PoseHandlerWrapper {
        PoseHandlerWrapper() {
        }

        boolean isPlaying(AnimationClip animationClip) {
            return animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
        }

        boolean isCurrentOrBlending(AnimationClip animationClip) {
            return animationClip != null && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
        }
    }
}

