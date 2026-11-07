package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Locale;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1787;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1835;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_9362;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.InterfaceDelayState;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.compat.BetterCombatCompat;
import ru.method.celebression.punchy.compat.SwordBlockingMechanicsCompat;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.mixin.client.accessor.PlayerFishingAccessor;

public class UseItemStateMachine {
    private static final long FAST_TRIGGER_WINDOW_TICKS = 4L;
    private static final float STOP_IMMEDIATE_BLEND_OUT_SECONDS = 0.3f;
    private static class_1799 pendingThrowableMain = class_1799.field_8037;
    private static class_1799 pendingThrowableOff = class_1799.field_8037;
    private static class_1799 pendingDropMain = class_1799.field_8037;
    private static class_1799 pendingDropOff = class_1799.field_8037;
    private static class_1799 pendingBoneMealUseOnMain = class_1799.field_8037;
    private static class_1799 pendingBoneMealUseOnOff = class_1799.field_8037;
    private static class_1268 pendingSwordBlockingCompatHand;
    private static long visualUseItemEpoch;
    private boolean wasUseDown;
    private boolean wasUsing;
    private long lastTriggerTick = -1L;
    private boolean holdOnLastFrame;
    private AnimationClip activeClip;
    private boolean stopImmediate;
    private boolean skipBlendOut;
    private boolean swordBlockingSuppressed;
    private class_1268 lastSwordBlockingHand;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1761 != null) {
            if (ConsumeStateMachine.isUseSuppressedAfterConsume()) {
                UseItemStateMachine.clearPendingThrowableSnapshot(null);
                UseActionTracker.clear();
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                this.holdOnLastFrame = false;
                this.activeClip = null;
                this.stopImmediate = false;
                this.skipBlendOut = false;
                this.wasUsing = false;
                this.swordBlockingSuppressed = false;
                this.lastSwordBlockingHand = null;
                this.wasUseDown = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            } else if (ConsumeStateMachine.isEquipBlockedByConsumeAnimation()) {
                UseItemStateMachine.clearPendingThrowableSnapshot(null);
                UseActionTracker.clear();
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                this.holdOnLastFrame = false;
                this.activeClip = null;
                this.stopImmediate = false;
                this.skipBlendOut = false;
                this.wasUsing = false;
                this.swordBlockingSuppressed = false;
                this.lastSwordBlockingHand = null;
                this.wasUseDown = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            } else {
                class_1268 class_12682;
                if (class_3102.field_1724.method_6115() && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682 = class_3102.field_1724.method_6058())) {
                    this.resetState();
                    return;
                }
                class_1799 activeStack = class_3102.field_1724.method_6030();
                if (ConsumeStateMachine.isConsumeStack(activeStack)) {
                    this.resetState();
                } else if (this.isBowUseActive(class_3102) || this.isCrossbowUseActive(class_3102) || this.isSpearUseActive(class_3102)) {
                    this.resetPoseStateOnly();
                } else if (this.shouldStopSwordBlockingCompat(class_3102)) {
                    this.stopSwordBlockingCompat(class_3102);
                } else {
                    boolean bl = class_3102.field_1724.method_6115();
                    boolean bl2 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
                    boolean bl3 = bl2 && !this.wasUseDown;
                    if (!bl2) {
                        this.swordBlockingSuppressed = false;
                    }
                    bl3 = bl2 && this.activeClip != null && PunchyAnimationManager.isClipNamed(this.activeClip, "sword_blocking_compat");
                    class_1268 class_12683 = UseItemStateMachine.consumePendingSwordBlockingCompatHand();
                    if (this.swordBlockingSuppressed || class_12683 == null || !SwordBlockingMechanicsCompat.shouldUseBlockingAnimation(class_12683)) {
                        UseActionTracker.Trigger trigger = UseActionTracker.consumeLatestTrigger();
                        if (trigger != null && trigger.fallback() && !this.shouldUseFallbackTrigger(class_3102, trigger)) {
                            trigger = UseActionTracker.consumeTrigger();
                        }
                        class_1268 class_12684 = trigger == null ? null : trigger.hand();
                        UseActionTracker.TriggerType triggerType = trigger == null ? null : trigger.type();
                        class_3965 class_39652 = trigger == null ? null : trigger.blockHit();
                        class_2680 class_26802 = this.resolveBlockState(class_3102, class_39652);
                        if (this.swordBlockingSuppressed && bl2) {
                            this.clearUseItemState(false);
                        } else if (class_12684 != null && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12684)) {
                            this.resetState();
                            this.wasUseDown = bl2;
                        } else {
                            boolean bl4;
                            class_1799 class_17992;
                            if (class_12684 != null && ConsumeStateMachine.isConsumeStack(class_17992 = class_3102.field_1724.method_5998(class_12684)) && !this.isInteractionAwareTrigger(triggerType)) {
                                this.wasUsing = false;
                                return;
                            }
                            boolean bl5 = bl4 = class_12684 != null || bl && bl2 || bl3;
                            if (this.holdOnLastFrame && this.activeClip != null && bl2) {
                                bl4 = true;
                            }
                            class_1268 class_12685 = class_12684 != null ? class_12684 : (bl ? class_3102.field_1724.method_6058() : class_1268.field_5808);
                            if (bl4 && HandEquipStateMachine.isEquipInProgress() && this.shouldAbortEquipForHand(class_3102, class_12685) && !HandEquipStateMachine.abortEquipForAction(class_3102, class_12685)) {
                                if (trigger != null) {
                                    UseActionTracker.pushBack(trigger);
                                }
                            } else {
                                if (bl4) {
                                    class_1268 class_12687;
                                    class_1268 class_12688 = class_12687 = bl ? class_3102.field_1724.method_6058() : null;
                                    if (this.activeClip != null && PunchyAnimationManager.isClipNamed(this.activeClip, "sword_blocking_compat") && class_12687 != null && this.lastSwordBlockingHand != null && this.lastSwordBlockingHand != class_12687) {
                                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                                        PunchyAnimationManager.clearSourceHand();
                                        HandEquipStateMachine.requestRenderedSync();
                                        this.activeClip = null;
                                        this.holdOnLastFrame = false;
                                        this.stopImmediate = false;
                                    }
                                    if (PunchyAnimationManager.isHandBlacklisted(class_3102, class_12685)) {
                                        this.resetState();
                                        this.wasUseDown = bl2;
                                        return;
                                    }
                                    if (bl3) {
                                        ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                                        ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                                    } else {
                                        boolean bl6;
                                        boolean bl7 = bl6 = bl && bl2;
                                        Boolean bl8 = bl6 ? !this.holdOnLastFrame : Boolean.FALSE;
                                        ItemAnimationManager.setPersistentType(class_12685, "useItem", bl8, true);
                                    }
                                    if (class_12684 != null && !this.shouldIgnoreTrigger(class_3102, class_12684, triggerType, class_26802)) {
                                        UseActionTracker.discardOppositeOlderThan(class_12684, trigger.recordNano());
                                        this.playInteract(class_3102, class_12684, triggerType, class_26802, trigger.fallback());
                                    } else if (this.shouldForceSwordBlockingPlay(class_3102)) {
                                        this.playInteract(class_3102, class_12685, null, null, false);
                                    } else if (!PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.shouldPlayUseDuringOffhandEquip(class_3102, class_12685, bl, class_12687)) {
                                        this.playInteract(class_3102, bl ? class_3102.field_1724.method_6058() : class_1268.field_5808, null, null, false);
                                    }
                                } else if (this.wasUsing) {
                                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                                    if (this.skipBlendOut) {
                                        PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
                                    }
                                    if (this.holdOnLastFrame) {
                                        if (!bl2) {
                                            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                                        }
                                    } else if (this.stopImmediate) {
                                        PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.3f);
                                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                                    } else {
                                        PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
                                    }
                                    this.clearUseItemState(false);
                                } else {
                                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                                }
                                this.wasUseDown = bl2;
                                this.wasUsing = bl4;
                                this.lastSwordBlockingHand = bl4 && this.activeClip != null && PunchyAnimationManager.isClipNamed(this.activeClip, "sword_blocking_compat") ? (bl ? class_3102.field_1724.method_6058() : this.lastSwordBlockingHand) : null;
                            }
                        }
                    } else if (PunchyAnimationManager.isHandBlacklisted(class_3102, class_12683)) {
                        this.resetState();
                        this.wasUseDown = bl2;
                    } else if (HandEquipStateMachine.isEquipInProgress() && !HandEquipStateMachine.abortEquipForAction(class_3102, class_12683)) {
                        pendingSwordBlockingCompatHand = class_12683;
                        this.wasUseDown = bl2;
                    } else {
                        if (this.playPendingSwordBlockingCompat(class_3102, class_12683)) {
                            this.wasUsing = true;
                        }
                        this.wasUseDown = bl2;
                    }
                }
            }
        }
    }

    private void resetState() {
        if (this.wasUsing) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            if (this.stopImmediate) {
                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.3f);
                PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
            } else {
                PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
            }
        }
        this.clearUseItemState(true);
    }

    private void resetPoseStateOnly() {
        if (this.wasUsing) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            if (this.stopImmediate) {
                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.3f);
                PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
            } else {
                PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
            }
        }
        this.holdOnLastFrame = false;
        this.activeClip = null;
        this.stopImmediate = false;
        this.skipBlendOut = false;
        this.wasUsing = false;
        this.swordBlockingSuppressed = false;
        this.lastSwordBlockingHand = null;
    }

    private boolean shouldStopSwordBlockingCompat(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || this.activeClip == null) {
            return false;
        }
        if (!PunchyAnimationManager.isClipNamed(this.activeClip, "sword_blocking_compat")) {
            return false;
        }
        if (class_3102.field_1690 == null) {
            return true;
        }
        if (!class_3102.field_1690.field_1904.method_1434()) {
            return true;
        }
        class_1268 class_12682 = this.lastSwordBlockingHand != null ? this.lastSwordBlockingHand : (class_3102.field_1724.method_6115() ? class_3102.field_1724.method_6058() : class_1268.field_5808);
        return !SwordBlockingMechanicsCompat.shouldUseBlockingAnimation(class_12682);
    }

    private void stopSwordBlockingCompat(class_310 class_3102) {
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
        this.clearUseItemState(false);
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434()) {
            this.swordBlockingSuppressed = true;
        }
    }

    private void playInteract(class_310 class_3102, class_1268 class_12682, UseActionTracker.TriggerType triggerType, class_2680 class_26802, boolean bl) {
        Float interfaceDelay;
        UseSelection useSelection = this.resolveUseSelection(class_3102, class_12682, triggerType, class_26802, bl);
        if (triggerType == UseActionTracker.TriggerType.DROP_ITEM) {
            UseItemStateMachine.clearPendingDropSnapshot(class_12682);
        } else if (triggerType == UseActionTracker.TriggerType.USE_ITEM_ON) {
            UseItemStateMachine.clearPendingBoneMealUseOnSnapshot(class_12682);
        }
        AnimationClip animationClip = useSelection.clip;
        if (triggerType == UseActionTracker.TriggerType.USE_ITEM_ON && CustomAnimationResolver.isInteractionEntry(useSelection.entry) && (interfaceDelay = useSelection.entry.interfaceDelay) != null && interfaceDelay.floatValue() > 0.0f) {
            InterfaceDelayState.schedule(interfaceDelay.floatValue());
        }
        if (animationClip != null) {
            boolean bl2;
            boolean bl3;
            ++visualUseItemEpoch;
            CustomAnimationResolver.applyLoopSpeed(useSelection.entry);
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            class_1306 useArm = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            boolean bl4 = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl4 && class_12682 == class_1268.field_5808 && HandEquipStateMachine.isOffhandEquipTransitionActive()) {
                HandEquipStateMachine.cancelOffhandTransitionForPoseOverride(class_3102.field_1724);
            }
            boolean bl5 = bl3 = class_12682 == class_1268.field_5808 && HandEquipStateMachine.isOffhandEquipTransitionActive() && useArm != null;
            if (!bl3) {
                bl3 = bl4 && useArm != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getActiveArm() != null && PunchyAnimationManager.POSE_HANDLER.getActiveArm() != useArm;
            }
            class_1306 class_13062 = useArm == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
            PunchyAnimationManager.POSE_HANDLER.setForcedSkipArmForNextPlay((class_1306)(bl3 ? class_13062 : null));
            if (bl4 && !bl3) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                this.primeOppositeArmOverlapDuringEquip(class_3102, class_12682, useArm);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(useArm);
            }
            boolean bl6 = animationClip.isHoldOnLastFrame() || PunchyAnimationManager.isClipNamed(animationClip, "sword_blocking_compat");
            PunchyAnimationManager.POSE_HANDLER.setLooping(!bl6);
            float f2 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            boolean bl7 = bl2 = CustomAnimationResolver.shouldSkipBlendInOnIdlePose() || this.shouldSkipBlendIn(class_3102) || PunchyAnimationManager.isClipNamed(animationClip, "use_bow");
            if (CustomAnimationResolver.shouldSkipBlendIn(useSelection.entry)) {
                bl2 = true;
            }
            if (CustomAnimationResolver.shouldBlendFromCurrentOrigin(useSelection.entry, animationClip)) {
                CustomAnimationResolver.playConfiguredClip(animationClip, useSelection.entry, f2, bl2, bl6);
            } else if (this.shouldBlendFromAttackToBlocking(animationClip)) {
                if (bl2) {
                    PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.0f);
                }
                PunchyAnimationManager.POSE_HANDLER.playWithBlendFromCurrentDynamic(animationClip, f2, bl6);
            } else {
                PunchyAnimationManager.POSE_HANDLER.play(animationClip, f2, bl2, bl6);
            }
            this.lastTriggerTick = this.currentTick(class_3102);
            this.holdOnLastFrame = bl6;
            this.activeClip = animationClip;
            this.stopImmediate = CustomAnimationResolver.shouldStopImmediately(useSelection.entry);
            this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(useSelection.entry);
        }
    }

    private boolean playPendingSwordBlockingCompat(class_310 class_3102, class_1268 class_12682) {
        if (PunchyAnimationManager.resolveNamedClip("sword_blocking_compat", class_3102, class_12682) == null) {
            return false;
        }
        UseActionTracker.clear();
        ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
        ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
        this.playInteract(class_3102, class_12682, null, null, false);
        return this.activeClip != null && PunchyAnimationManager.isClipNamed(this.activeClip, "sword_blocking_compat");
    }

    public static long getVisualUseItemEpoch() {
        return visualUseItemEpoch;
    }

    private boolean shouldSkipBlendIn(class_310 class_3102) {
        long l = this.currentTick(class_3102);
        return this.lastTriggerTick > 0L && l - this.lastTriggerTick <= 4L;
    }

    private long currentTick(class_310 class_3102) {
        return class_3102.field_1687 == null ? 0L : class_3102.field_1687.method_8510();
    }

    private boolean shouldAbortEquipForHand(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 == null || class_12682 == null) {
            return true;
        }
        if (HandEquipStateMachine.isOppositeShieldEquipInProgress(class_12682)) {
            return false;
        }
        class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
        if (class_13062 == null) {
            return false;
        }
        class_1306 class_13063 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
        return class_13063 == null || class_13063 == class_13062;
    }

    private boolean shouldPlayUseDuringOffhandEquip(class_310 class_3102, class_1268 class_12682, boolean bl, class_1268 class_12683) {
        if (class_3102 == null || class_12682 != class_1268.field_5808) {
            return false;
        }
        return !HandEquipStateMachine.isOffhandEquipTransitionActive() ? false : !bl || class_12683 == class_1268.field_5808;
    }

    private void primeOppositeArmOverlapDuringEquip(class_310 class_3102, class_1268 class_12682, class_1306 class_13062) {
        if (class_3102 != null && class_12682 != null && class_13062 != null && HandEquipStateMachine.isEquipInProgress()) {
            if (class_12682 == class_1268.field_5808) {
                HandEquipStateMachine.prepareOffhandForMainhandAction(class_3102);
            }
            if (PunchyAnimationManager.POSE_HANDLER.getActiveArm() == null) {
                class_1306 class_13063 = class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13063);
            }
        }
    }

    private AnimationClip resolveUseClip(class_310 class_3102, class_1268 class_12682, UseActionTracker.TriggerType triggerType, class_2680 class_26802) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = this.resolveUseContextStack(class_3102.field_1724.method_5998(class_12682), class_12682, triggerType);
            if (this.isChargeItem(class_17992) && this.shouldBlockChargeForTrigger(triggerType)) {
                return null;
            }
            AnimationClip throwableClip = this.shouldResolveThrowableUse(triggerType) ? this.resolveThrowableUseClip(class_3102, class_12682, class_17992, triggerType) : null;
            if (throwableClip != null) {
                return throwableClip;
            }
            PunchyTuningConfig.CustomAnimationEntry interactionEntry = CustomAnimationResolver.findInteractionEntry(class_17992, class_26802);
            String interactionName = CustomAnimationResolver.resolveInteractionName(interactionEntry);
            if (this.isInteractionAwareTrigger(triggerType) && interactionName != null && !interactionName.isBlank()) {
                AnimationClip interactionClip = PunchyAnimationManager.resolveNamedClip(interactionName, class_3102, class_12682);
                if (interactionClip != null) return interactionClip;
            }
            if (this.isBoneMealStack(class_17992) && triggerType == UseActionTracker.TriggerType.USE_ITEM_ON) {
                return PunchyAnimationManager.resolveTypeFallbackClip("useItem", class_3102, class_12682, PunchyTuningConfig.getMixPreferredOptionForStack(class_17992));
            }
            PunchyTuningConfig.CustomAnimationEntry useEntry = CustomAnimationResolver.findEntry(class_17992, "useItem");
            if (useEntry != null) {
                CustomAnimationResolver.ClipLookup lookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "useItem", useEntry, class_3102, class_12682);
                if (lookup.clip() != null) return lookup.clip();
            }
            AnimationClip fishingClip = this.resolveFishingRodUseClip(class_3102, class_12682, class_17992);
            if (fishingClip != null) {
                return fishingClip;
            }
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            String string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
            if (toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE || toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE) {
                return PunchyAnimationManager.resolveHandedClip("mining_use_foward", class_3102, class_12682, string);
            }
            return toolKind == VanillaFirstPersonItemLayer.ToolKind.SHOVEL ? PunchyAnimationManager.resolveHandedClip("mining_use_digging", class_3102, class_12682, string) : PunchyAnimationManager.resolveHandedClip("interact", class_3102, class_12682, string);
        }
        return PunchyAnimationManager.INTERACT_CLIP;
    }

    private boolean shouldIgnoreTrigger(class_310 class_3102, class_1268 class_12682, UseActionTracker.TriggerType triggerType, class_2680 class_26802) {
        if (triggerType != null) {
            return false;
        }
        if (this.holdOnLastFrame && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            AnimationClip animationClip = this.resolveUseSelection((class_310)class_3102, (class_1268)class_12682, (UseActionTracker.TriggerType)triggerType, (class_2680)class_26802, (boolean)false).clip;
            return animationClip != null && animationClip == this.activeClip;
        }
        return false;
    }

    private boolean shouldUseFallbackTrigger(class_310 class_3102, UseActionTracker.Trigger trigger) {
        if (class_3102 != null && class_3102.field_1724 != null && trigger != null) {
            if (trigger.fallback() && trigger.type() == UseActionTracker.TriggerType.USE_ITEM_ON && trigger.blockHit() != null) {
                class_1268 class_12682 = trigger.hand();
                if (class_12682 != null && !PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                    class_1799 class_17992 = class_3102.field_1724.method_5998(class_12682);
                    if (class_17992 == null || ConsumeStateMachine.isConsumeStack(class_17992) || this.isChargeItem(class_17992)) {
                        return false;
                    }
                    if (!class_17992.method_7960()) {
                        return true;
                    }
                    class_2680 class_26802 = this.resolveBlockState(class_3102, trigger.blockHit());
                    if (this.isFallbackAttackCandidate(class_3102, class_12682, class_17992)) {
                        return true;
                    }
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findInteractionEntry(class_17992, class_26802);
                    if (customAnimationEntry == null && CustomAnimationResolver.findEntry(class_17992, "useItem") == null) {
                        VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
                        return toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE || toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.SHOVEL;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private UseSelection resolveUseSelection(class_310 class_3102, class_1268 class_12682, UseActionTracker.TriggerType triggerType, class_2680 class_26802) {
        return this.resolveUseSelection(class_3102, class_12682, triggerType, class_26802, false);
    }

    private UseSelection resolveUseSelection(class_310 class_3102, class_1268 class_12682, UseActionTracker.TriggerType triggerType, class_2680 class_26802, boolean bl) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = this.resolveUseContextStack(class_3102.field_1724.method_5998(class_12682), class_12682, triggerType);
            if (this.isChargeItem(class_17992) && this.shouldBlockChargeForTrigger(triggerType)) {
                return new UseSelection(null, null);
            }
            AnimationClip throwableClip = this.shouldResolveThrowableUse(triggerType) ? this.resolveThrowableUseClip(class_3102, class_12682, class_17992, triggerType) : null;
            if (throwableClip != null) {
                class_1799 class_17993 = this.resolveThrowableContextStack(class_17992, class_12682, triggerType);
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17993, "useItem");
                return new UseSelection(throwableClip, customAnimationEntry);
            }
            AnimationClip blockingClip = !this.swordBlockingSuppressed && SwordBlockingMechanicsCompat.shouldUseBlockingAnimation(class_12682) ? PunchyAnimationManager.resolveNamedClip("sword_blocking_compat", class_3102, class_12682) : null;
            if (blockingClip != null) {
                return new UseSelection(blockingClip, null);
            }
            if (bl && triggerType == UseActionTracker.TriggerType.USE_ITEM_ON && this.isFallbackAttackCandidate(class_3102, class_12682, class_17992)) {
                return new UseSelection(this.resolveFallbackAttackClip(class_3102, class_12682, class_17992), null);
            }
            PunchyTuningConfig.CustomAnimationEntry interactionEntry = CustomAnimationResolver.findInteractionEntry(class_17992, class_26802);
            String interactionName = CustomAnimationResolver.resolveInteractionName(interactionEntry);
            AnimationClip interactionClip = this.isInteractionAwareTrigger(triggerType) && interactionName != null && !interactionName.isBlank() ? PunchyAnimationManager.resolveNamedClip(interactionName, class_3102, class_12682) : null;
            if (interactionClip != null) {
                return new UseSelection(interactionClip, interactionEntry);
            }
            if (this.isBoneMealStack(class_17992) && triggerType == UseActionTracker.TriggerType.USE_ITEM_ON) {
                return new UseSelection(PunchyAnimationManager.resolveTypeFallbackClip("useItem", class_3102, class_12682, PunchyTuningConfig.getMixPreferredOptionForStack(class_17992)), null);
            }
            PunchyTuningConfig.CustomAnimationEntry useEntry = CustomAnimationResolver.findEntry(class_17992, "useItem");
            if (useEntry != null) {
                CustomAnimationResolver.ClipLookup lookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "useItem", useEntry, class_3102, class_12682);
                if (lookup.clip() != null) return new UseSelection(lookup.clip(), useEntry);
            }
            AnimationClip fishingClip = this.resolveFishingRodUseClip(class_3102, class_12682, class_17992);
            if (fishingClip != null) {
                return new UseSelection(fishingClip, CustomAnimationResolver.findEntry(class_17992, "fishingrod"));
            }
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            String string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
            if (toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE || toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE) {
                return new UseSelection(PunchyAnimationManager.resolveHandedClip("mining_use_foward", class_3102, class_12682, string), null);
            }
            return toolKind == VanillaFirstPersonItemLayer.ToolKind.SHOVEL ? new UseSelection(PunchyAnimationManager.resolveHandedClip("mining_use_digging", class_3102, class_12682, string), null) : new UseSelection(PunchyAnimationManager.resolveHandedClip("interact", class_3102, class_12682, string), null);
        }
        return new UseSelection(PunchyAnimationManager.INTERACT_CLIP, null);
    }

    private boolean isFallbackAttackCandidate(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        if (class_3102 == null || class_12682 == null) {
            return false;
        }
        if (class_17992 != null && !class_17992.method_7960()) {
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            return toolKind != VanillaFirstPersonItemLayer.ToolKind.SWORD && toolKind != VanillaFirstPersonItemLayer.ToolKind.MACE && toolKind != VanillaFirstPersonItemLayer.ToolKind.SPEAR && toolKind != VanillaFirstPersonItemLayer.ToolKind.TRIDENT ? BetterCombatCompat.isWeaponStack(class_17992) : true;
        }
        return class_12682 == class_1268.field_5808;
    }

    private AnimationClip resolveFallbackAttackClip(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        if (class_3102 == null) {
            return PunchyAnimationManager.ATTACK_CLIP;
        }
        if (class_17992 != null && !class_17992.method_7960()) {
            AnimationClip animationClip;
            AnimationClip specialClip;
            if (class_17992.method_7909() instanceof class_9362 && (specialClip = PunchyAnimationManager.resolveHandedClip("attack_mace", class_3102, class_12682)) != null) {
                return specialClip;
            }
            if (class_17992.method_7909() instanceof class_1835 && (specialClip = PunchyAnimationManager.resolveHandedClip("attack_trident", class_3102, class_12682)) != null) {
                return specialClip;
            }
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            if (toolKind == VanillaFirstPersonItemLayer.ToolKind.SPEAR && (animationClip = PunchyAnimationManager.resolveHandedClip("attack_spear", class_3102, class_12682)) != null) {
                return animationClip;
            }
            return PunchyAnimationManager.resolveHandedClip("attack", class_3102, class_12682);
        }
        AnimationClip animationClip = PunchyAnimationManager.resolveHandedClip("punch", class_3102, class_12682);
        return animationClip != null ? animationClip : PunchyAnimationManager.PUNCH_CLIP;
    }

    private AnimationClip resolveThrowableUseClip(class_310 class_3102, class_1268 class_12682, class_1799 class_17992, UseActionTracker.TriggerType triggerType) {
        class_1799 class_17993 = this.resolveThrowableContextStack(class_17992, class_12682, triggerType);
        if (class_3102 != null && class_12682 != null && this.isThrowableUseStack(class_17993)) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17993, "useItem");
            if (customAnimationEntry != null) {
                String string = null;
                string = customAnimationEntry.throw_animation != null && !customAnimationEntry.throw_animation.isBlank() ? customAnimationEntry.throw_animation.trim() : CustomAnimationResolver.resolveSingleName(customAnimationEntry);
                AnimationClip animationClip = PunchyAnimationManager.resolveNamedClip(string, class_3102, class_12682);
                if (animationClip != null) {
                    return animationClip;
                }
            }
            return PunchyAnimationManager.resolveHandedClip("throw_item", class_3102, class_12682, PunchyTuningConfig.getMixPreferredOptionForStack(class_17993));
        }
        return null;
    }

    private boolean isThrowableUseStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_31574(class_1802.field_8436) || class_17992.method_31574(class_1802.field_8150) || class_17992.method_31574(class_1802.field_8287) || class_17992.method_31574(class_1802.field_8543) || class_17992.method_31574(class_1802.field_8803) || class_17992.method_31574(class_1802.field_8634) || class_17992.method_31574(class_1802.field_8449) : false;
    }

    private class_1799 resolveThrowableContextStack(class_1799 class_17992, class_1268 class_12682, UseActionTracker.TriggerType triggerType) {
        if (class_17992 != null && !class_17992.method_7960()) {
            return class_17992;
        }
        if (triggerType == UseActionTracker.TriggerType.USE_ITEM && class_12682 != null) {
            class_1799 class_17993 = UseItemStateMachine.consumePendingThrowableSnapshot(class_12682);
            return this.isThrowableUseStack(class_17993) ? class_17993 : class_1799.field_8037;
        }
        return class_17992 == null ? class_1799.field_8037 : class_17992;
    }

    public static void capturePendingThrowableSnapshot(class_1268 class_12682, class_1799 class_17992) {
        if (class_12682 != null && UseItemStateMachine.isThrowableStatic(class_17992)) {
            class_1799 class_17993 = class_17992.method_7972();
            if (class_12682 == class_1268.field_5810) {
                pendingThrowableOff = class_17993;
            } else {
                pendingThrowableMain = class_17993;
            }
        } else {
            UseItemStateMachine.clearPendingThrowableSnapshot(class_12682);
        }
    }

    public static void capturePendingDropSnapshot(class_1268 class_12682, class_1799 class_17992) {
        if (class_12682 != null && class_17992 != null && !class_17992.method_7960()) {
            class_1799 class_17993 = class_17992.method_7972();
            if (class_12682 == class_1268.field_5810) {
                pendingDropOff = class_17993;
            } else {
                pendingDropMain = class_17993;
            }
        } else {
            UseItemStateMachine.clearPendingDropSnapshot(class_12682);
        }
    }

    public static void capturePendingBoneMealUseOnSnapshot(class_1268 class_12682, class_1799 class_17992) {
        if (class_12682 != null && UseItemStateMachine.isBoneMealStatic(class_17992)) {
            class_1799 class_17993 = class_17992.method_7972();
            if (class_12682 == class_1268.field_5810) {
                pendingBoneMealUseOnOff = class_17993;
            } else {
                pendingBoneMealUseOnMain = class_17993;
            }
        } else {
            UseItemStateMachine.clearPendingBoneMealUseOnSnapshot(class_12682);
        }
    }

    public static boolean hasPendingBoneMealUseOnSnapshot(class_1268 class_12682) {
        return UseItemStateMachine.isBoneMealStatic(UseItemStateMachine.peekPendingBoneMealUseOnSnapshot(class_12682));
    }

    public static void requestSwordBlockingCompat(class_1268 class_12682) {
        pendingSwordBlockingCompatHand = class_12682 == null ? class_1268.field_5808 : class_12682;
    }

    private static class_1268 consumePendingSwordBlockingCompatHand() {
        class_1268 class_12682 = pendingSwordBlockingCompatHand;
        pendingSwordBlockingCompatHand = null;
        return class_12682;
    }

    public static void clearPendingThrowableSnapshot(class_1268 class_12682) {
        if (class_12682 == null || class_12682 == class_1268.field_5808) {
            pendingThrowableMain = class_1799.field_8037;
        }
        if (class_12682 == null || class_12682 == class_1268.field_5810) {
            pendingThrowableOff = class_1799.field_8037;
        }
    }

    private static class_1799 consumePendingThrowableSnapshot(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            class_1799 class_17992 = pendingThrowableOff;
            pendingThrowableOff = class_1799.field_8037;
            return class_17992 == null ? class_1799.field_8037 : class_17992;
        }
        class_1799 class_17993 = pendingThrowableMain;
        pendingThrowableMain = class_1799.field_8037;
        return class_17993 == null ? class_1799.field_8037 : class_17993;
    }

    private static void clearPendingDropSnapshot(class_1268 class_12682) {
        if (class_12682 == null || class_12682 == class_1268.field_5808) {
            pendingDropMain = class_1799.field_8037;
        }
        if (class_12682 == null || class_12682 == class_1268.field_5810) {
            pendingDropOff = class_1799.field_8037;
        }
    }

    private static class_1799 peekPendingDropSnapshot(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            return pendingDropOff == null ? class_1799.field_8037 : pendingDropOff;
        }
        return pendingDropMain == null ? class_1799.field_8037 : pendingDropMain;
    }

    private class_1799 resolveUseContextStack(class_1799 class_17992, class_1268 class_12682, UseActionTracker.TriggerType triggerType) {
        class_1799 class_17993 = this.resolveDropContextStack(class_17992, class_12682, triggerType);
        if (triggerType == UseActionTracker.TriggerType.USE_ITEM_ON && class_12682 != null && !this.isBoneMealStack(class_17993)) {
            class_1799 class_17994 = UseItemStateMachine.peekPendingBoneMealUseOnSnapshot(class_12682);
            return this.isBoneMealStack(class_17994) ? class_17994 : class_17993;
        }
        return class_17993;
    }

    private class_1799 resolveDropContextStack(class_1799 class_17992, class_1268 class_12682, UseActionTracker.TriggerType triggerType) {
        if (triggerType == UseActionTracker.TriggerType.DROP_ITEM && class_12682 != null) {
            class_1799 class_17993 = UseItemStateMachine.peekPendingDropSnapshot(class_12682);
            return class_17993 != null && !class_17993.method_7960() ? class_17993 : (class_17992 == null ? class_1799.field_8037 : class_17992);
        }
        return class_17992 == null ? class_1799.field_8037 : class_17992;
    }

    private static void clearPendingBoneMealUseOnSnapshot(class_1268 class_12682) {
        if (class_12682 == null || class_12682 == class_1268.field_5808) {
            pendingBoneMealUseOnMain = class_1799.field_8037;
        }
        if (class_12682 == null || class_12682 == class_1268.field_5810) {
            pendingBoneMealUseOnOff = class_1799.field_8037;
        }
    }

    private static class_1799 peekPendingBoneMealUseOnSnapshot(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            return pendingBoneMealUseOnOff == null ? class_1799.field_8037 : pendingBoneMealUseOnOff;
        }
        return pendingBoneMealUseOnMain == null ? class_1799.field_8037 : pendingBoneMealUseOnMain;
    }

    private boolean shouldResolveThrowableUse(UseActionTracker.TriggerType triggerType) {
        return !this.isInteractionAwareTrigger(triggerType) && triggerType != UseActionTracker.TriggerType.DROP_ITEM;
    }

    private boolean isBoneMealStack(class_1799 class_17992) {
        return UseItemStateMachine.isBoneMealStatic(class_17992);
    }

    private static boolean isBoneMealStatic(class_1799 class_17992) {
        return class_17992 != null && class_17992.method_31574(class_1802.field_8324);
    }

    private static boolean isThrowableStatic(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_31574(class_1802.field_8436) || class_17992.method_31574(class_1802.field_8150) || class_17992.method_31574(class_1802.field_8287) || class_17992.method_31574(class_1802.field_8543) || class_17992.method_31574(class_1802.field_8803) || class_17992.method_31574(class_1802.field_8634) || class_17992.method_31574(class_1802.field_8449) : false;
    }

    private boolean shouldForceSwordBlockingPlay(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return false;
        }
        if (!SwordBlockingMechanicsCompat.shouldUseBlockingAnimation(class_3102.field_1724.method_6058())) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip == null ? false : this.isAttackClip(animationClip);
    }

    private boolean shouldBlendFromAttackToBlocking(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (!PunchyAnimationManager.isClipNamed(animationClip, "sword_blocking_compat")) {
            return false;
        }
        if (!PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            return false;
        }
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip2 == null ? false : this.isAttackClip(animationClip2);
    }

    private void clearUseItemState(boolean bl) {
        ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
        ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        boolean bl2 = PunchyAnimationManager.POSE_HANDLER.isPlaying() && animationClip != null && PunchyAnimationManager.hasAnyDualHandedEffect(animationClip);
        this.holdOnLastFrame = false;
        this.activeClip = null;
        this.stopImmediate = false;
        this.skipBlendOut = false;
        this.wasUsing = false;
        if (bl) {
            this.swordBlockingSuppressed = false;
        }
        if (!bl2) {
            PunchyAnimationManager.clearSourceHand();
        }
    }

    private boolean isChargeItem(class_1799 class_17992) {
        return class_17992 == null ? false : class_17992.method_7909() instanceof class_1753 || class_17992.method_7909() instanceof class_1764 || class_17992.method_7909() instanceof class_1835;
    }

    private boolean shouldBlockChargeForTrigger(UseActionTracker.TriggerType triggerType) {
        return triggerType == null || triggerType == UseActionTracker.TriggerType.USE_ITEM;
    }

    private boolean isInteractionAwareTrigger(UseActionTracker.TriggerType triggerType) {
        return triggerType == UseActionTracker.TriggerType.USE_ITEM_ON || triggerType == UseActionTracker.TriggerType.INTERACT || triggerType == UseActionTracker.TriggerType.INTERACT_AT;
    }

    private class_2680 resolveBlockState(class_310 class_3102, class_3965 class_39652) {
        return class_3102 != null && class_3102.field_1687 != null && class_39652 != null ? class_3102.field_1687.method_8320(class_39652.method_17777()) : null;
    }

    private boolean isAttackClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "attack")) {
            return true;
        }
        String string = animationClip.getName();
        return string != null && string.toLowerCase(Locale.ROOT).contains("attack");
    }

    private AnimationClip resolveFishingRodUseClip(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        String string;
        if (class_3102 == null || class_3102.field_1724 == null || class_17992 == null || class_17992.method_7960()) {
            return null;
        }
        if (!(class_17992.method_7909() instanceof class_1787)) {
            return null;
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "fishingrod");
        if (customAnimationEntry == null) {
            return null;
        }
        boolean bl = this.isFishingRodCastActive(class_3102);
        String string2 = string = bl ? customAnimationEntry.pull_animation : customAnimationEntry.throw_animation;
        if (string == null || string.isBlank()) {
            string = CustomAnimationResolver.resolveSingleName(customAnimationEntry);
        }
        return string != null && !string.isBlank() ? PunchyAnimationManager.resolveNamedClip(string.trim(), class_3102, class_12682) : null;
    }

    private boolean isFishingRodCastActive(class_310 class_3102) {
        return class_3102 != null && class_3102.field_1724 != null ? ((PlayerFishingAccessor)class_3102.field_1724).punchy$getFishing() != null : false;
    }

    private boolean isBowUseActive(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            return class_3102.field_1724.method_6115() && class_17992.method_7909() instanceof class_1753 && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
        }
        return false;
    }

    private boolean isSpearUseActive(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            return class_3102.field_1724.method_6115() && SpearStateMachine.isSpearStack(class_17992) && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
        }
        return false;
    }

    private boolean isCrossbowUseActive(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            return class_3102.field_1724.method_6115() && class_17992.method_7909() instanceof class_1764 && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
        }
        return false;
    }

    static final class UseSelection {
        final AnimationClip clip;
        final PunchyTuningConfig.CustomAnimationEntry entry;

        UseSelection(AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
            this.clip = animationClip;
            this.entry = customAnimationEntry;
        }

        @Override
        public final String toString() {
            return "UseSelection[" + "clip=" + this.clip + ", " + "entry=" + this.entry + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.clip, this.entry);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof UseSelection other)) return false;
            return java.util.Objects.deepEquals(this.clip, other.clip)
                && java.util.Objects.deepEquals(this.entry, other.entry);
        }

        public AnimationClip clip() {
            return this.clip;
        }

        public PunchyTuningConfig.CustomAnimationEntry entry() {
            return this.entry;
        }
    }
}

