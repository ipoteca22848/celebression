package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1819;
import net.minecraft.class_1835;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_9331;
import net.minecraft.class_9334;
import net.minecraft.class_9336;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.MiningActionTracker;
import ru.method.celebression.punchy.client.state.TridentStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.platform.Services;

public class HandEquipStateMachine {
    private static final boolean LOG_OFFHAND_DETACH_DEBUG = false;
    private static final boolean LOG_WALK_ON_WATER_EQUIP_DEBUG = true;
    private static final long OFFHAND_USE_UNSUPPRESS_WINDOW_NANOS = 1800000000L;
    private static HandEquipStateMachine instance;
    private static volatile boolean equipActive;
    private static volatile String activeType;
    private static volatile int handOutGraceTicks;
    private static volatile boolean syncRequested;
    private static volatile int vanillaHandOutMainTicks;
    private static volatile int vanillaHandOutOffTicks;
    private static volatile int lastEquipTransitionPlayerTick;
    private static final String EFFECT_SWAP_END;
    private static final String EFFECT_SWAP;
    private static final String EFFECT_SWAP_ITEM;
    private static final int VANILLA_HAND_OUT_TICKS = 4;
    private static final int HOTBAR_RAPID_WINDOW_TICKS = 4;
    private static final int HOTBAR_RAPID_MIN_STREAK = 2;
    private static final int F_SWAP_BURST_WINDOW_TICKS = 10;
    private static final int F_SWAP_BURST_MIN_COUNT = 2;
    private static final float HOTBAR_HAND_OUT_SPEED_BOOST = 2.6f;
    private static final float HAND_OUT_ACTION_BLEND_SECONDS = 0.1f;
    private static final float HAND_OUT_TO_HAND_IN_EARLY_TRANSITION_SECONDS = 0.05f;
    private static final float WALK_ON_WATER_MIN_REBASE_TIME = 5.0E-4f;
    private Phase phase = Phase.IDLE;
    private boolean initialized;
    private int lastHotbarSlot = -1;
    private int hotbarRapidWindowTicks;
    private int hotbarChangeStreak;
    private final Deque<Integer> recentFunctionalSwapTicks = new ArrayDeque<Integer>();
    private class_1799 renderedMain = class_1799.field_8037;
    private class_1799 renderedOff = class_1799.field_8037;
    private class_1799 pendingMain = class_1799.field_8037;
    private class_1799 pendingOff = class_1799.field_8037;
    private boolean pendingMainChange;
    private boolean pendingOffChange;
    private boolean pickupRevealMainOnRaise;
    private boolean pickupRevealOffOnRaise;
    private boolean deferSwapWithoutEquip;
    private boolean skipNextEquipMain;
    private boolean skipNextEquipOff;
    private boolean swapTriggered;
    private boolean cycleMain;
    private boolean cycleOff;
    private boolean pausedByUseClip;
    private boolean suppressOffhandRender;
    private boolean hideOffhandTridentThrowCache;
    private boolean offhandRevealPending;
    private boolean offhandAwaitingHandIn;
    private boolean pendingBlacklistHandInMain;
    private boolean pendingBlacklistHandInOff;
    private boolean blacklistTransitionHandIn;
    private boolean lastOffhandEmpty = true;
    private boolean pendingMainActionClear;
    private boolean pendingOffActionClear;
    private AnimationClip pendingMainActionClearClip;
    private AnimationClip pendingOffActionClearClip;
    private float pendingMainActionClearTime;
    private float pendingOffActionClearTime;
    private AnimationClip activeHandInClip;
    private AnimationClip activeHandOutClip;
    private class_1306 activeHandInArm;
    private boolean detachedOffhandEquip;
    private boolean shieldOppositeEquip;
    private String lastOffhandDebugSnapshot = "";

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_746 class_7462 = class_3102.field_1724;
            if (!this.initialized) {
                this.initialize(class_7462);
            }
            if (syncRequested && !this.shouldDeferRenderedSyncDuringShieldOppositeEquip()) {
                syncRequested = false;
                this.syncRenderedItems(class_7462);
            }
            class_1799 class_17992 = class_7462.method_6047();
            class_1799 class_17993 = class_7462.method_6079();
            this.updateSkipEquipLatches(class_17992, class_17993);
            boolean bl = this.updateHotbarSlot(class_7462);
            if (bl) {
                this.hotbarChangeStreak = Math.min(8, this.hotbarChangeStreak + 1);
                this.hotbarRapidWindowTicks = 4;
            } else if (this.hotbarRapidWindowTicks > 0) {
                --this.hotbarRapidWindowTicks;
            } else {
                this.hotbarChangeStreak = 0;
            }
            boolean bl2 = this.isBlockingEquipClipActive(class_17992);
            if (bl2) {
                this.pausedByUseClip = true;
                if (class_17992.method_7960() && !this.renderedMain.method_7960()) {
                    this.renderedMain = class_1799.field_8037;
                }
            } else if (this.pausedByUseClip && this.phase != Phase.IDLE) {
                this.resumeEquipClip(class_7462);
                this.pausedByUseClip = false;
            } else if (this.pausedByUseClip) {
                this.pausedByUseClip = false;
            }
            boolean bl3 = this.updatePendingActionClearMain(class_17992, class_17993);
            boolean bl4 = this.updatePendingActionClearOff(class_17993, class_17992);
            boolean bl5 = !bl3 && (this.hasItemChanged(this.renderedMain, class_17992) || bl && this.shouldAnimateHotbarChange(this.renderedMain, class_17992));
            boolean bl6 = !bl4 && this.hasItemChanged(this.renderedOff, class_17993);
            this.updateOffhandRevealPending(class_7462, class_17993, bl6);
            this.updateOffhandAwaitingHandIn(class_7462, class_17993);
            this.suppressOffhandRender = this.shouldSuppressOffhandRender(class_17993);
            switch (this.phase.ordinal()) {
                case 0: {
                    this.handleIdle(class_3102, class_7462, class_17992, class_17993, bl5, bl6);
                    break;
                }
                case 1: {
                    this.handleLowering(class_3102, class_7462, class_17992, class_17993, bl);
                    break;
                }
                case 2: {
                    this.handleRaising(class_3102, class_7462, class_17992, class_17993, bl);
                }
            }
            equipActive = this.phase != Phase.IDLE;
            switch (this.phase.ordinal()) {
                default: {
                    throw new IllegalStateException("Unexpected enum value");
                }
                case 0: {
                    String string = null;
                    break;
                }
                case 1: {
                    String string = "hand_out";
                    break;
                }
                case 2: {
                    String string = activeType = "hand_in";
                }
            }
            if ("hand_out".equals(activeType) && this.cycleOff) {
                handOutGraceTicks = 2;
            } else if (handOutGraceTicks > 0) {
                --handOutGraceTicks;
            }
            if (this.phase == Phase.LOWERING) {
                if (this.cycleMain) {
                    ItemAnimationManager.setPersistentType(class_1268.field_5808, "hand_out", false);
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_out");
                }
                if (this.cycleOff && !this.shouldSuppressOffhandHandOut(class_7462, class_17993)) {
                    ItemAnimationManager.setPersistentType(class_1268.field_5810, "hand_out", false);
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_out");
                }
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_in");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_in");
            } else if (this.phase == Phase.RAISING) {
                if (this.cycleMain) {
                    ItemAnimationManager.setPersistentType(class_1268.field_5808, "hand_in", false);
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_in");
                }
                if (this.cycleOff) {
                    ItemAnimationManager.setPersistentType(class_1268.field_5810, "hand_in", false);
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_in");
                }
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_out");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_out");
            } else {
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_out");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_out");
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "hand_in");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "hand_in");
            }
            this.logOffhandState(class_7462, class_17993);
        }
    }

    public HandEquipStateMachine() {
        instance = this;
    }

    public static void requestRenderedSync() {
        syncRequested = true;
    }

    public static void triggerPickupEquipCycle(class_310 class_3102, class_1268 class_12682) {
        class_746 class_7462;
        if (instance != null && class_3102 != null && (class_7462 = class_3102.field_1724) instanceof class_746) {
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            instance.forcePickupEquipCycle(class_7462, class_12683);
            return;
        }
    }

    public static void triggerImmediateEquipCycle(class_310 class_3102, class_1268 class_12682) {
        class_746 class_7462;
        if (instance != null && class_3102 != null && (class_7462 = class_3102.field_1724) instanceof class_746) {
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            instance.forceImmediateEquipCycle(class_7462, class_12683);
            return;
        }
    }

    private void handleIdle(class_310 class_3102, class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = this.isWalkOnWaterPoseActive() || CustomAnimationResolver.shouldAutoBlendFromWalkOnWater();
        boolean bl5 = this.isFunctionalOffhandSwap(this.renderedMain, this.renderedOff, class_17992, class_17993, bl, bl2, false);
        boolean bl6 = bl3 = bl4 && (bl && !bl2 || bl5);
        if (!this.handleBlacklistToPunchyTransition(class_7462, class_17992, class_17993, bl, bl2)) {
            if (this.deferSwapWithoutEquip) {
                if (bl || bl2) {
                    this.queuePending(class_7462, class_17992, class_17993, bl, bl2, false);
                }
                if (!this.isSkipHandEquipWindowActive()) {
                    this.applyPending(class_7462);
                    this.deferSwapWithoutEquip = false;
                    this.skipNextEquipMain = false;
                    this.skipNextEquipOff = false;
                } else {
                    this.applyEquipOverrides(class_7462);
                }
            } else if (!bl && !bl2 || !this.shouldPreserveCurrentClipForHandOut() || !ItemAnimationManager.isTypeActive("mining") && !MiningActionTracker.wasRecentMining(250000000L)) {
                if (this.isLoopingActionClip() && !bl3) {
                    if (bl) {
                        this.renderedMain = this.copyStack(class_17992);
                    }
                    if (bl2) {
                        this.renderedOff = this.copyStack(class_17993);
                    }
                    this.pendingMainChange = false;
                    this.pendingOffChange = false;
                    this.applyEquipOverrides(class_7462);
                } else {
                    boolean bl7 = this.shouldBlockEquip(class_3102, class_17992, bl, bl2);
                    if (bl3 || !bl7 || !bl && !bl2) {
                        if ((bl3 || !bl7) && (bl || bl2)) {
                            if (this.shouldSkipHandEquipOnSwap(class_17992, class_17993, bl, bl2)) {
                                this.queuePending(class_7462, class_17992, class_17993, bl, bl2, false);
                                this.deferSwapWithoutEquip = true;
                                this.applyEquipOverrides(class_7462);
                            } else {
                                this.queuePending(class_7462, class_17992, class_17993, bl, bl2, false);
                                this.hideOffhandTridentThrowCache = this.shouldHideOffhandTridentThrowCache(class_17993, bl2);
                                this.startLowering(class_7462);
                            }
                        } else {
                            this.renderedMain = this.copyStack(class_17992);
                            this.renderedOff = this.copyStack(class_17993);
                            this.hideOffhandTridentThrowCache = false;
                            this.applyEquipOverrides(class_7462);
                        }
                    } else {
                        this.clearRenderedEmptyHandsWhileUseBlocksEquip(class_7462, class_17992, class_17993, bl, bl2);
                        this.queuePending(class_7462, class_17992, class_17993, bl, bl2, false);
                        this.applyEquipOverrides(class_7462);
                    }
                }
            } else {
                this.queuePending(class_7462, class_17992, class_17993, bl, bl2, false);
                this.startLowering(class_7462);
            }
        }
    }

    private void forcePickupEquipCycle(class_746 class_7462, class_1268 class_12682) {
        if (class_7462 != null) {
            class_1799 class_17992 = class_7462.method_6047();
            class_1799 class_17993 = class_7462.method_6079();
            if (class_12682 == class_1268.field_5810) {
                this.pendingOffChange = true;
                this.pendingOff = this.copyStack(class_17993);
                this.renderedOff = class_1799.field_8037;
                this.pendingMainChange = false;
                this.pickupRevealOffOnRaise = true;
                this.pickupRevealMainOnRaise = false;
            } else {
                this.pendingMainChange = true;
                this.pendingMain = this.copyStack(class_17992);
                this.renderedMain = class_1799.field_8037;
                this.pendingOffChange = false;
                this.pickupRevealMainOnRaise = true;
                this.pickupRevealOffOnRaise = false;
            }
            this.deferSwapWithoutEquip = false;
            this.skipNextEquipMain = false;
            this.skipNextEquipOff = false;
            this.pausedByUseClip = false;
            if (this.phase == Phase.IDLE) {
                this.startLowering(class_7462);
            } else if (this.phase == Phase.RAISING) {
                this.activeHandInClip = null;
                this.activeHandInArm = null;
                this.phase = Phase.IDLE;
                this.startLowering(class_7462);
            } else {
                this.applyEquipOverrides(class_7462);
            }
        }
    }

    private void forceImmediateEquipCycle(class_746 class_7462, class_1268 class_12682) {
        if (class_7462 != null) {
            class_1799 class_17992 = class_7462.method_6047();
            class_1799 class_17993 = class_7462.method_6079();
            if (class_12682 == class_1268.field_5810) {
                this.pendingOffChange = true;
                this.pendingOff = this.copyStack(class_17993);
                this.pendingMainChange = false;
            } else {
                this.pendingMainChange = true;
                this.pendingMain = this.copyStack(class_17992);
                this.pendingOffChange = false;
            }
            this.pickupRevealMainOnRaise = false;
            this.pickupRevealOffOnRaise = false;
            this.deferSwapWithoutEquip = false;
            this.skipNextEquipMain = false;
            this.skipNextEquipOff = false;
            this.pausedByUseClip = false;
            if (this.phase == Phase.RAISING) {
                this.activeHandInClip = null;
                this.activeHandInArm = null;
                this.phase = Phase.IDLE;
            }
            if (this.phase == Phase.IDLE) {
                this.startLowering(class_7462);
            } else {
                this.applyEquipOverrides(class_7462);
            }
        }
    }

    private boolean handleBlacklistToPunchyTransition(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2) {
        boolean bl3;
        if (class_7462 == null) {
            return false;
        }
        boolean bl4 = false;
        if (!this.pendingBlacklistHandInMain && bl && this.wasItemBlacklisted(this.renderedMain) && !this.isHandBlacklisted(class_7462, class_1268.field_5808)) {
            this.pendingBlacklistHandInMain = true;
            this.pendingMainChange = true;
            this.pendingMain = this.copyStack(class_17992);
            vanillaHandOutMainTicks = 4;
            bl4 = true;
        }
        if (!this.pendingBlacklistHandInOff && bl2 && this.wasItemBlacklisted(this.renderedOff) && !this.isHandBlacklisted(class_7462, class_1268.field_5810)) {
            this.pendingBlacklistHandInOff = true;
            this.pendingOffChange = true;
            this.pendingOff = this.copyStack(class_17993);
            vanillaHandOutOffTicks = 4;
            bl4 = true;
        }
        if (!(bl4 || this.pendingBlacklistHandInMain || this.pendingBlacklistHandInOff)) {
            return false;
        }
        if (this.pendingBlacklistHandInMain && vanillaHandOutMainTicks > 0) {
            --vanillaHandOutMainTicks;
        }
        if (this.pendingBlacklistHandInOff && vanillaHandOutOffTicks > 0) {
            --vanillaHandOutOffTicks;
        }
        boolean bl5 = this.pendingBlacklistHandInMain && vanillaHandOutMainTicks <= 0;
        boolean bl6 = bl3 = this.pendingBlacklistHandInOff && vanillaHandOutOffTicks <= 0;
        if (!bl5 && !bl3) {
            this.applyEquipOverrides(class_7462);
            return true;
        }
        if (bl5) {
            this.pendingBlacklistHandInMain = false;
            this.pendingMainChange = true;
            this.pendingMain = this.copyStack(class_17992);
        }
        if (bl3) {
            this.pendingBlacklistHandInOff = false;
            this.pendingOffChange = true;
            this.pendingOff = this.copyStack(class_17993);
        }
        this.cycleMain = bl5;
        this.cycleOff = bl3;
        this.blacklistTransitionHandIn = true;
        this.applyPending(class_7462);
        this.startRaising(class_7462);
        return true;
    }

    private void handleLowering(class_310 class_3102, class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl) {
        if (!this.shieldOppositeEquip && this.isLoopingActionClip()) {
            this.resetToCurrent(class_7462);
        } else if (!this.shieldOppositeEquip && this.isBlockingEquipClipActive(class_17992)) {
            this.pausedByUseClip = true;
        } else if (this.isInterruptedByOtherClip()) {
            if (!this.tryDetachOffhandDuringAction(class_7462)) {
                this.exitEquipPreservingSecondary(class_7462);
            }
        } else {
            boolean bl2 = this.updatePendingIfChanged(class_7462, class_17992, class_17993, bl);
            if (bl2) {
                if (this.isRapidHotbarScrollActive() && this.pendingMainChange && !this.pendingOffChange) {
                    this.swapTriggered = true;
                    this.applyPending(class_7462);
                    this.startRaising(class_7462);
                } else if (this.shouldForceDirectHandInOnRapidRepeat(class_7462)) {
                    this.forceDirectHandInFromPending(class_7462);
                } else {
                    this.startLowering(class_7462);
                }
            } else {
                boolean bl3;
                boolean bl4 = bl3 = this.shouldSwapNow() || this.isHandOutInEarlyTransitionWindow();
                if (!this.swapTriggered && bl3) {
                    this.swapTriggered = true;
                    boolean bl5 = this.shouldSkipRaiseForOffhandRemovalBefore();
                    this.applyPending(class_7462);
                    if (!bl5 && !this.shouldSkipRaiseForOffhandRemovalAfter(class_7462)) {
                        this.startRaising(class_7462);
                    } else {
                        this.finishLoweringWithoutRaise(class_7462);
                    }
                } else if (!this.isClipPlaying(this.activeHandOutClip)) {
                    boolean bl6 = this.shouldSkipRaiseForOffhandRemovalBefore();
                    this.applyPending(class_7462);
                    if (!bl6 && !this.shouldSkipRaiseForOffhandRemovalAfter(class_7462)) {
                        this.startRaising(class_7462);
                    } else {
                        this.finishLoweringWithoutRaise(class_7462);
                    }
                }
            }
        }
    }

    private void handleRaising(class_310 class_3102, class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl) {
        this.revealPickupStackOnRaise(class_7462);
        if (!this.shieldOppositeEquip && this.isLoopingActionClip()) {
            this.resetToCurrent(class_7462);
        } else if (!this.shieldOppositeEquip && this.isBlockingEquipClipActive(class_17992)) {
            this.pausedByUseClip = true;
        } else if (this.isInterruptedByOtherClip()) {
            if (!this.tryDetachOffhandDuringAction(class_7462)) {
                this.exitEquipPreservingSecondary(class_7462);
            }
        } else {
            boolean bl2 = this.updatePendingIfChanged(class_7462, class_17992, class_17993, bl);
            if (bl2) {
                if (this.shouldForceDirectHandInOnRapidRepeat(class_7462)) {
                    this.forceDirectHandInFromPending(class_7462);
                } else {
                    this.startLowering(class_7462);
                }
            } else if (!this.isClipPlaying(this.activeHandInClip)) {
                this.finishRaise(class_7462);
            }
        }
    }

    private boolean shouldForceDirectHandInOnRapidRepeat(class_746 class_7462) {
        return this.wasFunctionalSwapRecordedThisTick(class_7462) || this.isFunctionalSwapBurstActive(class_7462);
    }

    private void initialize(class_746 class_7462) {
        this.renderedMain = this.copyStack(class_7462.method_6047());
        this.renderedOff = this.copyStack(class_7462.method_6079());
        this.lastHotbarSlot = this.resolveSelectedSlot(class_7462);
        this.lastOffhandEmpty = this.renderedOff == null || this.renderedOff.method_7960();
        this.offhandAwaitingHandIn = false;
        this.offhandRevealPending = false;
        this.suppressOffhandRender = false;
        this.activeHandInClip = null;
        this.activeHandOutClip = null;
        this.activeHandInArm = null;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        this.pendingBlacklistHandInMain = false;
        this.pendingBlacklistHandInOff = false;
        this.blacklistTransitionHandIn = false;
        this.pickupRevealMainOnRaise = false;
        this.pickupRevealOffOnRaise = false;
        vanillaHandOutMainTicks = 0;
        vanillaHandOutOffTicks = 0;
        this.hotbarRapidWindowTicks = 0;
        this.hotbarChangeStreak = 0;
        this.recentFunctionalSwapTicks.clear();
        this.clearPendingActionClears();
        PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        this.initialized = true;
    }

    private void resetToCurrent(class_746 class_7462) {
        this.resetEquipState(class_7462, true);
        this.phase = Phase.IDLE;
        this.markEquipTransitionTick(class_7462);
        this.applyEquipOverrides(class_7462);
    }

    private void resetEquipState(class_746 class_7462, boolean bl) {
        this.renderedMain = this.copyStack(class_7462.method_6047());
        this.renderedOff = this.copyStack(class_7462.method_6079());
        this.pendingMainChange = false;
        this.pendingOffChange = false;
        this.deferSwapWithoutEquip = false;
        this.skipNextEquipMain = false;
        this.skipNextEquipOff = false;
        this.swapTriggered = false;
        this.cycleMain = false;
        this.cycleOff = false;
        this.suppressOffhandRender = false;
        this.offhandRevealPending = false;
        this.offhandAwaitingHandIn = false;
        this.lastOffhandEmpty = this.renderedOff == null || this.renderedOff.method_7960();
        this.activeHandInClip = null;
        this.activeHandOutClip = null;
        this.activeHandInArm = null;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        this.pendingBlacklistHandInMain = false;
        this.pendingBlacklistHandInOff = false;
        this.blacklistTransitionHandIn = false;
        vanillaHandOutMainTicks = 0;
        vanillaHandOutOffTicks = 0;
        this.hotbarRapidWindowTicks = 0;
        this.hotbarChangeStreak = 0;
        this.clearPendingActionClears();
        PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(false);
        if (bl) {
            PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        }
    }

    private void exitEquipPreservingSecondary(class_746 class_7462) {
        this.resetEquipState(class_7462, false);
        this.phase = Phase.IDLE;
        this.markEquipTransitionTick(class_7462);
        this.applyEquipOverrides(class_7462);
    }

    private void syncRenderedItems(class_746 class_7462) {
        this.renderedMain = this.copyStack(class_7462.method_6047());
        this.renderedOff = this.copyStack(class_7462.method_6079());
        this.pendingMainChange = false;
        this.pendingOffChange = false;
        this.deferSwapWithoutEquip = false;
        this.skipNextEquipMain = false;
        this.skipNextEquipOff = false;
        this.suppressOffhandRender = false;
        this.offhandRevealPending = false;
        this.offhandAwaitingHandIn = false;
        this.lastOffhandEmpty = this.renderedOff == null || this.renderedOff.method_7960();
        this.pendingBlacklistHandInMain = false;
        this.pendingBlacklistHandInOff = false;
        this.blacklistTransitionHandIn = false;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        vanillaHandOutMainTicks = 0;
        vanillaHandOutOffTicks = 0;
        this.hotbarRapidWindowTicks = 0;
        this.hotbarChangeStreak = 0;
        this.recentFunctionalSwapTicks.clear();
        this.clearPendingActionClears();
        PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(false);
        if (!this.shouldPreserveWalkOnWaterOffhandSecondary()) {
            PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        }
        this.applyEquipOverrides(class_7462);
    }

    private void finishRaise(class_746 class_7462) {
        this.phase = Phase.IDLE;
        this.cycleMain = false;
        this.cycleOff = false;
        this.deferSwapWithoutEquip = false;
        this.skipNextEquipMain = false;
        this.skipNextEquipOff = false;
        this.suppressOffhandRender = false;
        this.offhandRevealPending = false;
        this.offhandAwaitingHandIn = false;
        this.activeHandInClip = null;
        this.activeHandInArm = null;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        this.pendingBlacklistHandInMain = false;
        this.pendingBlacklistHandInOff = false;
        this.blacklistTransitionHandIn = false;
        vanillaHandOutMainTicks = 0;
        vanillaHandOutOffTicks = 0;
        boolean bl = this.shouldPreserveWalkOnWaterOffhandSecondary();
        this.debugWalkOnWaterEquip("finishRaise", bl);
        PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(false);
        if (!bl) {
            PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        }
        this.clearPendingActionClears();
        this.markEquipTransitionTick(class_7462);
        this.applyEquipOverrides(class_7462);
        if (class_7462 != null) {
            PunchyClient.getWalkOnWaterStateMachine().reattachMainhandAfterEquip(class_7462);
        }
        if (this.pendingMainChange || this.pendingOffChange) {
            this.restartRaisingFromPending(class_7462);
        }
    }

    private void restartRaisingFromPending(class_746 class_7462) {
        if (this.pendingMainChange || this.pendingOffChange) {
            this.cycleMain = this.pendingMainChange;
            this.cycleOff = this.pendingOffChange;
            this.applyPending(class_7462);
            this.startRaising(class_7462);
        }
    }

    private void forceDirectHandInFromPending(class_746 class_7462) {
        if (this.pendingMainChange || this.pendingOffChange) {
            this.clearSecondaryClipUnlessPreservingWalkOnWater("forceDirectHandInFromPending");
            this.activeHandOutClip = null;
            this.activeHandInClip = null;
            this.activeHandInArm = null;
            this.detachedOffhandEquip = false;
            this.shieldOppositeEquip = false;
            this.swapTriggered = true;
            this.cycleMain = this.pendingMainChange;
            this.cycleOff = this.pendingOffChange;
            this.applyPending(class_7462);
            this.startRaising(class_7462);
        }
    }

    private void queuePending(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2, boolean bl3) {
        this.recordFunctionalSwapIfNeeded(class_7462, class_17992, class_17993, bl, bl2, bl3);
        this.pendingMainChange = bl;
        this.pendingOffChange = bl2;
        if (bl) {
            this.pendingMain = this.copyStack(class_17992);
        }
        if (bl2) {
            this.pendingOff = this.copyStack(class_17993);
        }
    }

    private void startLowering(class_746 class_7462) {
        boolean bl;
        this.deferSwapWithoutEquip = false;
        this.cycleMain = this.pendingMainChange;
        this.cycleOff = this.pendingOffChange;
        if (!(this.cycleOff && this.pendingOff != null && this.pendingOff.method_7960() && this.renderedOff != null && this.renderedOff.method_7909() instanceof class_1835)) {
            this.hideOffhandTridentThrowCache = false;
        }
        boolean bl2 = bl = this.isWalkOnWaterPoseActive() || CustomAnimationResolver.shouldAutoBlendFromWalkOnWater();
        if (!bl || !this.shouldDelayWalkOnWaterHandOut()) {
            AnimationClip animationClip;
            class_1306 class_13062;
            AnimationClip animationClip2;
            class_1268 class_12682;
            class_310 class_3102;
            boolean bl3 = this.cycleMain && this.isRapidHotbarScrollActive();
            this.shieldOppositeEquip = this.shouldRunOppositeHandEquipOverShield(class_7462);
            if (!this.shieldOppositeEquip && !this.shouldPreserveCurrentClipForHandOut()) {
                this.stopConflictingClip();
            }
            EquipSelection equipSelection = this.resolveHandOutClip();
            AnimationClip animationClip3 = equipSelection.clip;
            boolean bl4 = this.cycleMain && this.cycleOff && equipSelection.explicitCustom && equipSelection.hand != null;
            boolean bl5 = false;
            class_1268 secondaryHand = null;
            AnimationClip animationClip4 = null;
            if (bl4) {
                class_3102 = class_310.method_1551();
                class_12682 = equipSelection.hand == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
                animationClip2 = PunchyAnimationManager.resolveHandedClip("hand_out", class_3102, class_12682);
                class_1306 class_13063 = class_13062 = class_7462 == null ? null : this.resolveArm(class_7462, class_12682);
                if (animationClip2 != null && !this.clipHasTracksForArm(equipSelection.clip, class_13062)) {
                    secondaryHand = class_12682;
                    animationClip4 = animationClip2;
                    animationClip3 = equipSelection.clip;
                    CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
                } else {
                    bl4 = false;
                    CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
                }
            }
            if (animationClip3 == null) {
                CustomAnimationResolver.applyLoopSpeed(null);
                class_3102 = class_310.method_1551();
                class_12682 = this.cycleOff && !this.cycleMain ? class_1268.field_5810 : class_1268.field_5808;
                animationClip3 = PunchyAnimationManager.resolveHandedClip("hand_out", class_3102, class_12682);
            } else if (!bl4) {
                CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
            }
            class_1306 currentArm = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            if (!bl4 && this.cycleMain && this.cycleOff && !bl && this.shouldBlendHandOutFromCurrent() && class_7462 != null && currentArm != null) {
                class_1268 primaryHand = this.resolveHandForArm(class_7462, currentArm);
                class_1268 oppositeHand = primaryHand == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
                class_310 client = class_310.method_1551();
                AnimationClip primaryClip = PunchyAnimationManager.resolveHandedClip("hand_out", client, primaryHand);
                AnimationClip oppositeClip = PunchyAnimationManager.resolveHandedClip("hand_out", client, oppositeHand);
                if (primaryClip != null && oppositeClip != null) {
                    animationClip3 = primaryClip;
                    secondaryHand = oppositeHand;
                    animationClip4 = oppositeClip;
                    bl5 = true;
                }
            }
            if (animationClip3 == null) {
                if (this.cycleMain) {
                    ItemAnimationManager.forceTypeOnce(class_1268.field_5808, "hand_out");
                }
                if (this.cycleOff && !this.shouldSuppressOffhandHandOut(class_7462, class_7462.method_6079())) {
                    ItemAnimationManager.forceTypeOnce(class_1268.field_5810, "hand_out");
                }
                this.clearSecondaryClipUnlessPreservingWalkOnWater("startLowering:noClip");
                this.applyPending(class_7462);
                this.startRaising(class_7462);
            } else {
                boolean bl6;
                this.activeHandOutClip = animationClip3;
                this.swapTriggered = false;
                this.applyEquipOverrides(class_7462);
                class_1306 activeArm = this.resolveActiveArm(class_7462);
                if (bl4) {
                    activeArm = this.resolveArm(class_7462, equipSelection.hand);
                } else if (bl5 && class_7462 != null) {
                    activeArm = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
                }
                if (bl && this.cycleMain && this.cycleOff) {
                    activeArm = null;
                }
                boolean bl7 = this.cycleMain ^ this.cycleOff;
                boolean bl8 = equipSelection.explicitCustom && PunchyAnimationManager.hasAnyDualHandedEffect(animationClip3);
                boolean bl9 = bl6 = bl || bl7 && !bl8;
                if (!bl6 && PunchyAnimationManager.isDualHandedActive(class_310.method_1551(), animationClip3, 0.0f)) {
                    activeArm = null;
                }
                float f = this.resolveEquipSpeedMultiplier();
                if (bl3) {
                    f *= 2.6f;
                }
                if (this.shieldOppositeEquip) {
                    float f2 = this.resolveEquipBlendStartTime(animationClip3);
                    PunchyAnimationManager.POSE_HANDLER.setSecondaryClipAtTime(animationClip3, activeArm, f, f2);
                    this.phase = Phase.LOWERING;
                } else {
                    boolean bl10;
                    PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(bl6);
                    PunchyAnimationManager.POSE_HANDLER.setActiveArm(activeArm);
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    this.preserveWalkOnWaterOffhandDuringMainhandEquip(class_7462, bl);
                    if ((bl4 || bl5) && animationClip4 != null && secondaryHand != null) {
                        class_1306 secondaryArm = this.resolveArm(class_7462, secondaryHand);
                        PunchyAnimationManager.POSE_HANDLER.setSecondaryClip(animationClip4, secondaryArm, f);
                    }
                    boolean bl11 = bl10 = bl3 || equipSelection.entry == null || CustomAnimationResolver.shouldSkipBlendIn(equipSelection.entry);
                    if (bl) {
                        float f3 = this.resolveEquipBlendStartTime(animationClip3);
                        if (this.cycleMain && this.cycleOff) {
                            PunchyAnimationManager.setSourceHand(class_310.method_1551(), class_1268.field_5810);
                            PunchyAnimationManager.POSE_HANDLER.setRebaseUseBothArmsForNextPlay(true);
                        } else {
                            PunchyAnimationManager.setSourceHand(class_310.method_1551(), this.resolveHandForArm(class_7462, activeArm));
                            if (activeArm != null) {
                                PunchyAnimationManager.POSE_HANDLER.setRebaseSourceArmForNextPlay(activeArm);
                            }
                        }
                        PunchyAnimationManager.POSE_HANDLER.setRebaseReturnSpeedNext(0.95f);
                        PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.0f);
                        PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromCurrent(animationClip3, f, false, f3);
                    } else if (this.shouldBlendHandOutFromSwim()) {
                        float f4 = this.resolveEquipBlendStartTime(animationClip3);
                        PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromHandlerReturnToOrigin(PunchyAnimationManager.SWIM_HANDLER, animationClip3, f, false, f4);
                    } else if (CustomAnimationResolver.shouldBlendFromCurrentOrigin(equipSelection.entry, animationClip3)) {
                        float f5 = this.resolveEquipBlendStartTime(animationClip3);
                        PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip3, f, false, f5);
                    } else if (this.shouldBlendHandOutFromCurrent()) {
                        float f6 = this.resolveEquipBlendStartTime(animationClip3);
                        PunchyAnimationManager.POSE_HANDLER.playAtTimeWithBlendFromCurrent(animationClip3, f, false, f6);
                    } else {
                        PunchyAnimationManager.POSE_HANDLER.play(animationClip3, f, bl10, false);
                    }
                    this.phase = Phase.LOWERING;
                }
            }
        }
    }

    private void startRaising(class_746 class_7462) {
        if (this.detachedOffhandEquip && this.cycleOff && !this.cycleMain) {
            this.startDetachedOffhandRaising(class_7462);
        } else {
            class_1268 class_12682;
            class_1799 class_17992;
            EquipSelection equipSelection;
            this.suppressOffhandRender = false;
            boolean bl = CustomAnimationResolver.shouldAutoBlendFromWalkOnWater();
            if (!this.shieldOppositeEquip) {
                this.stopConflictingClip();
            }
            class_1799 currentOffhand = class_7462 == null ? class_1799.field_8037 : class_7462.method_6079();
            if (class_7462 != null && this.cycleOff && (currentOffhand == null || currentOffhand.method_7960())) {
                if (!this.cycleMain) {
                    this.finishLoweringWithoutRaise(class_7462);
                    return;
                }
                this.cycleOff = false;
            }
            equipSelection = this.resolveHandInClip(class_7462);
            AnimationClip animationClip = equipSelection.clip;
            boolean bl2 = false;
            EquipSelection equipSelection2 = null;
            EquipSelection equipSelection3 = null;
            if (this.cycleMain && this.cycleOff && class_7462 != null) {
                class_1799 class_17993 = this.resolveRaiseTargetStack(class_1268.field_5808, this.pendingMain, this.renderedMain, class_7462);
                class_17992 = this.resolveRaiseTargetStack(class_1268.field_5810, this.pendingOff, this.renderedOff, class_7462);
                equipSelection2 = this.resolveCustomEquipClip(class_17993, class_1268.field_5808, "hand_in");
                equipSelection3 = this.resolveCustomEquipClip(class_17992, class_1268.field_5810, "hand_in");
                String mainClipName = equipSelection2.clip == null ? null : this.clipName(equipSelection2.clip);
                String offClipName = equipSelection3.clip == null ? null : this.clipName(equipSelection3.clip);
                if (equipSelection2.clip != null && equipSelection3.clip != null && !Objects.equals(mainClipName, offClipName)) {
                    bl2 = true;
                    equipSelection = equipSelection2;
                    animationClip = equipSelection2.clip;
                }
            }
            boolean bl3 = !bl2 && this.cycleMain && this.cycleOff && equipSelection.explicitCustom && equipSelection.hand != null;
            class_1268 secondaryHand = null;
            AnimationClip secondaryClip = null;
            if (bl3) {
                class_1306 class_13062;
                class_310 client = class_310.method_1551();
                class_12682 = equipSelection.hand == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
                AnimationClip animationClip2 = PunchyAnimationManager.resolveHandedClip("hand_in", client, class_12682);
                class_1306 class_13063 = class_13062 = class_7462 == null ? null : this.resolveArm(class_7462, class_12682);
                if (animationClip2 != null && !this.clipHasTracksForArm(equipSelection.clip, class_13062)) {
                    secondaryHand = class_12682;
                    secondaryClip = animationClip2;
                    animationClip = equipSelection.clip;
                    CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
                } else {
                    bl3 = false;
                    CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
                }
            }
            if (animationClip == null) {
                CustomAnimationResolver.applyLoopSpeed(null);
                class_310 client = class_310.method_1551();
                class_12682 = this.cycleOff && !this.cycleMain ? class_1268.field_5810 : class_1268.field_5808;
                animationClip = PunchyAnimationManager.resolveHandedClip("hand_in", client, class_12682);
            } else if (!bl3) {
                CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
            }
            if (animationClip == null) {
                if (this.cycleMain) {
                    ItemAnimationManager.forceTypeOnce(class_1268.field_5808, "hand_in");
                }
                if (this.cycleOff) {
                    ItemAnimationManager.forceTypeOnce(class_1268.field_5810, "hand_in");
                }
                PunchyAnimationManager.clearAllEquipOverrides();
                this.clearSecondaryClipUnlessPreservingWalkOnWater("startRaising:noClip");
                this.phase = Phase.IDLE;
                this.cycleMain = false;
                this.cycleOff = false;
                this.blacklistTransitionHandIn = false;
                this.pickupRevealMainOnRaise = false;
                this.pickupRevealOffOnRaise = false;
            } else {
                boolean bl4;
                this.activeHandInClip = animationClip;
                this.applyEquipOverrides(class_7462);
                if (!(this.shieldOppositeEquip || bl && this.cycleMain && !this.cycleOff)) {
                    this.clearSecondaryClipUnlessPreservingWalkOnWater("startRaising");
                }
                class_1306 activeArm = this.resolveActiveArm(class_7462);
                if (bl3) {
                    activeArm = this.resolveArm(class_7462, equipSelection.hand);
                }
                boolean bl5 = this.cycleMain ^ this.cycleOff;
                boolean bl6 = equipSelection.explicitCustom && PunchyAnimationManager.hasAnyDualHandedEffect(animationClip);
                boolean bl7 = bl4 = bl2 || bl || bl5 && !bl6;
                if (bl2) {
                    activeArm = this.resolveArm(class_7462, class_1268.field_5808);
                }
                if (!bl4 && PunchyAnimationManager.isDualHandedActive(class_310.method_1551(), animationClip, 0.0f)) {
                    activeArm = null;
                }
                this.activeHandInArm = bl3 ? null : activeArm;
                float f = this.resolveEquipSpeedMultiplier();
                if (this.shieldOppositeEquip) {
                    PunchyAnimationManager.POSE_HANDLER.setSecondaryClipAtTime(animationClip, activeArm, f, 0.0f);
                    this.blacklistTransitionHandIn = false;
                    this.phase = Phase.RAISING;
                } else {
                    boolean bl8;
                    class_1306 class_13064;
                    PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(bl4);
                    PunchyAnimationManager.POSE_HANDLER.setActiveArm(activeArm);
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    this.preserveWalkOnWaterOffhandDuringMainhandEquip(class_7462, bl);
                    if (bl2 && equipSelection3 != null && equipSelection3.clip != null && class_7462 != null) {
                        class_13064 = this.resolveArm(class_7462, class_1268.field_5810);
                        PunchyAnimationManager.POSE_HANDLER.requestClearArm(class_13064);
                        PunchyAnimationManager.POSE_HANDLER.setSecondaryClip(equipSelection3.clip, class_13064, f);
                    }
                    if (bl3 && secondaryClip != null && secondaryHand != null) {
                        class_13064 = this.resolveArm(class_7462, secondaryHand);
                        PunchyAnimationManager.POSE_HANDLER.setSecondaryClip(secondaryClip, class_13064, f);
                    }
                    boolean bl9 = bl8 = this.blacklistTransitionHandIn || equipSelection.entry == null || CustomAnimationResolver.shouldSkipBlendIn(equipSelection.entry);
                    if (!(bl || CustomAnimationResolver.shouldBlendFromCurrentOrigin(equipSelection.entry, animationClip) || this.shouldBlendHandInFromCurrentDuringSwim())) {
                        PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl8, false);
                    } else {
                        if (bl) {
                            PunchyAnimationManager.POSE_HANDLER.setRebaseReturnSpeedNext(0.95f);
                        }
                        if (activeArm == null) {
                            PunchyAnimationManager.POSE_HANDLER.setRebaseUseBothArmsForNextPlay(true);
                        }
                        PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f, false, 0.0f);
                    }
                    this.blacklistTransitionHandIn = false;
                    this.phase = Phase.RAISING;
                }
            }
        }
    }

    private void applyPending(class_746 class_7462) {
        boolean bl;
        boolean bl2 = this.pickupRevealMainOnRaise && this.phase == Phase.LOWERING;
        boolean bl3 = bl = this.pickupRevealOffOnRaise && this.phase == Phase.LOWERING;
        if (this.pendingMainChange && !bl2) {
            this.renderedMain = this.copyStack(this.pendingMain);
            this.pendingMainChange = false;
            this.pickupRevealMainOnRaise = false;
        }
        if (this.pendingOffChange && !bl) {
            this.renderedOff = this.copyStack(this.pendingOff);
            this.pendingOffChange = false;
            this.pickupRevealOffOnRaise = false;
        }
        this.applyEquipOverrides(class_7462);
    }

    private void revealPickupStackOnRaise(class_746 class_7462) {
        boolean bl = false;
        if (this.phase == Phase.RAISING) {
            if (this.pickupRevealMainOnRaise && this.pendingMainChange) {
                this.renderedMain = this.copyStack(this.pendingMain);
                this.pendingMainChange = false;
                this.pickupRevealMainOnRaise = false;
                bl = true;
            }
            if (this.pickupRevealOffOnRaise && this.pendingOffChange) {
                this.renderedOff = this.copyStack(this.pendingOff);
                this.pendingOffChange = false;
                this.pickupRevealOffOnRaise = false;
                bl = true;
            }
            if (bl) {
                this.applyEquipOverrides(class_7462);
            }
        }
    }

    private void applyEquipOverrides(class_746 class_7462) {
        class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5808);
        class_1306 class_13063 = this.resolveArm(class_7462, class_1268.field_5810);
        PunchyAnimationManager.setEquipOverride(class_13062, this.renderedMain);
        if (!this.cycleOff && this.renderedOff.method_7960()) {
            PunchyAnimationManager.clearEquipOverride(class_13063);
        } else {
            if (this.suppressOffhandRender && (this.renderedOff == null || this.renderedOff.method_7960())) {
                PunchyAnimationManager.clearEquipOverride(class_13063);
                return;
            }
            PunchyAnimationManager.setEquipOverride(class_13063, this.renderedOff);
        }
        if (this.detachedOffhandEquip && this.cycleOff && !this.cycleMain) {
            PunchyAnimationManager.POSE_HANDLER.requestClearArm(class_13063);
        }
    }

    private boolean updatePendingIfChanged(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl) {
        class_1799 class_17994;
        boolean bl2 = false;
        boolean bl3 = false;
        class_1799 class_17995 = this.pendingMainChange ? this.pendingMain : this.renderedMain;
        class_1799 class_17996 = class_17994 = this.pendingOffChange ? this.pendingOff : this.renderedOff;
        bl2 = this.pendingMainChange ? this.hasItemChanged(this.pendingMain, class_17992) : this.hasItemChanged(this.renderedMain, class_17992) || bl && this.shouldAnimateHotbarChange(this.renderedMain, class_17992);
        bl3 = this.pendingOffChange ? this.hasItemChanged(this.pendingOff, class_17993) : this.hasItemChanged(this.renderedOff, class_17993);
        this.recordFunctionalSwapIfNeeded(class_7462, class_17995, class_17994, class_17992, class_17993, bl2, bl3, bl);
        if (bl2) {
            this.pendingMainChange = true;
            this.pendingMain = this.copyStack(class_17992);
        }
        if (bl3) {
            this.pendingOffChange = true;
            this.pendingOff = this.copyStack(class_17993);
        }
        return bl2 || bl3;
    }

    private boolean shouldBlockEquip(class_310 class_3102, class_1799 class_17992, boolean bl, boolean bl2) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return true;
        }
        if (TridentStateMachine.isRecentActionBlockingEquip(class_3102)) {
            return true;
        }
        if (this.isLoopingActionClip()) {
            return true;
        }
        if (ConsumeStateMachine.isEquipBlockedByConsumeAnimation()) {
            return true;
        }
        return class_3102.field_1724.method_6115() && !this.isUsingStaleSwappedItem(class_3102.field_1724) && !this.isOppositeHandSwapDuringShieldUse(class_3102.field_1724, bl, bl2) ? true : this.isBlockingEquipClipActive(class_17992);
    }

    private boolean isUsingStaleSwappedItem(class_746 class_7462) {
        if (class_7462 != null && class_7462.method_6115()) {
            class_1799 class_17992 = class_7462.method_6030();
            if (class_17992 != null && !class_17992.method_7960()) {
                class_1268 class_12682 = class_7462.method_6058();
                if (class_12682 == null) {
                    return false;
                }
                class_1799 class_17993 = class_7462.method_5998(class_12682);
                return class_17993 == null || class_17993.method_7960() || !class_1799.method_31577((class_1799)class_17993, (class_1799)class_17992);
            }
            return false;
        }
        return false;
    }

    private boolean isOppositeHandSwapDuringShieldUse(class_746 class_7462, boolean bl, boolean bl2) {
        if (class_7462 == null) {
            return false;
        }
        class_1268 class_12682 = this.resolveActiveShieldHand(class_7462);
        return class_12682 == null ? false : class_12682 == class_1268.field_5810 && bl && !bl2 || class_12682 == class_1268.field_5808 && bl2 && !bl;
    }

    private boolean shouldRunOppositeHandEquipOverShield(class_746 class_7462) {
        return this.isOppositeHandSwapDuringShieldUse(class_7462, this.cycleMain, this.cycleOff);
    }

    private boolean shouldDeferRenderedSyncDuringShieldOppositeEquip() {
        return this.shieldOppositeEquip && this.phase != Phase.IDLE && (this.cycleMain && !this.cycleOff || this.cycleOff && !this.cycleMain);
    }

    private class_1268 resolveActiveShieldHand(class_746 class_7462) {
        if (class_7462 == null) {
            return null;
        }
        class_1799 activeStack = class_7462.method_6030();
        if (class_7462.method_6115() && activeStack != null && activeStack.method_7909() instanceof class_1819) {
            return class_7462.method_6058();
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434()) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (!PunchyAnimationManager.POSE_HANDLER.isPlaying() || !PunchyAnimationManager.isClipNamed(animationClip, "use_shield")) {
                return null;
            }
            if (class_7462.method_6079().method_7909() instanceof class_1819) {
                return class_1268.field_5810;
            }
            return class_7462.method_6047().method_7909() instanceof class_1819 ? class_1268.field_5808 : null;
        }
        return null;
    }

    private boolean isInterruptedByOtherClip() {
        AnimationClip animationClip;
        if (this.detachedOffhandEquip && this.cycleOff && !this.cycleMain && (animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip()) != null && this.isEquipClip(animationClip)) {
            return false;
        }
        if (this.shieldOppositeEquip && (animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip()) != null && this.isEquipClip(animationClip)) {
            return false;
        }
        animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return this.isBlockingEquipClip(animationClip) ? false : PunchyAnimationManager.POSE_HANDLER.isPlaying() && !this.isEquipClip(animationClip);
    }

    private void stopConflictingClip() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != null && !this.isEquipClip(animationClip) && !this.isBlockingEquipClip(animationClip)) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
            PunchyAnimationManager.clearSourceHand();
        }
    }

    public static boolean isEquipInProgress() {
        return equipActive;
    }

    public static boolean isOppositeShieldEquipInProgress(class_1268 class_12682) {
        return instance != null && class_12682 != null && HandEquipStateMachine.instance.shieldOppositeEquip ? class_12682 == class_1268.field_5810 && HandEquipStateMachine.instance.cycleMain && !HandEquipStateMachine.instance.cycleOff || class_12682 == class_1268.field_5808 && HandEquipStateMachine.instance.cycleOff && !HandEquipStateMachine.instance.cycleMain : false;
    }

    public static String getActiveType() {
        return activeType;
    }

    public static boolean shouldTreatHandOutActive() {
        return "hand_out".equals(activeType) ? true : handOutGraceTicks > 0;
    }

    public static boolean shouldAllowVanillaHandOut(class_1268 class_12682) {
        class_1268 class_12683;
        if (HandEquipStateMachine.isDualHandedPoseBlockingVanillaHandOut()) {
            return false;
        }
        class_1268 class_12684 = class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
        return class_12683 == class_1268.field_5810 ? vanillaHandOutOffTicks > 0 : vanillaHandOutMainTicks > 0;
    }

    public static boolean shouldKeepOffhandVisibleUntilHandOutEnds() {
        return instance == null ? false : HandEquipStateMachine.instance.phase == Phase.LOWERING && HandEquipStateMachine.instance.cycleOff && HandEquipStateMachine.instance.pendingOffChange && (HandEquipStateMachine.instance.pendingOff == null || HandEquipStateMachine.instance.pendingOff.method_7960());
    }

    public static boolean isOffhandEquipTransitionActive() {
        if (instance == null) {
            return false;
        }
        return HandEquipStateMachine.instance.phase == Phase.IDLE ? false : HandEquipStateMachine.instance.cycleOff || HandEquipStateMachine.instance.pendingOffChange || HandEquipStateMachine.instance.offhandRevealPending || HandEquipStateMachine.instance.offhandAwaitingHandIn;
    }

    public static boolean cancelOffhandTransitionForPoseOverride(class_746 class_7462) {
        return instance != null && class_7462 != null ? instance.cancelOffhandTransitionForPoseOverrideInternal(class_7462) : false;
    }

    public static boolean shouldHideIncomingOffhandUntilHandInStarts(class_746 class_7462) {
        if (instance != null && class_7462 != null) {
            class_1799 class_17992 = class_7462.method_6079();
            if (class_17992 != null && !class_17992.method_7960()) {
                boolean bl;
                if (instance.isHandInActive()) {
                    class_1306 class_13062 = instance.resolveArm(class_7462, class_1268.field_5810);
                    boolean bl2 = bl = HandEquipStateMachine.instance.cycleOff || HandEquipStateMachine.instance.activeHandInArm == null || HandEquipStateMachine.instance.activeHandInArm == class_13062;
                    if (bl) {
                        return false;
                    }
                }
                boolean bl3 = HandEquipStateMachine.instance.renderedOff == null || HandEquipStateMachine.instance.renderedOff.method_7960();
                bl = HandEquipStateMachine.instance.pendingOff != null && !HandEquipStateMachine.instance.pendingOff.method_7960();
                return HandEquipStateMachine.instance.offhandRevealPending || HandEquipStateMachine.instance.offhandAwaitingHandIn && !instance.isHandInActive() || bl3 && !bl;
            }
            return false;
        }
        return false;
    }

    public static class_1799 getVisualOffhandStack(class_746 class_7462) {
        class_1799 class_17992;
        boolean bl;
        if (class_7462 == null) {
            return class_1799.field_8037;
        }
        if (instance == null) {
            class_1799 class_17993 = class_7462.method_6079();
            return class_17993 == null ? class_1799.field_8037 : class_17993.method_7972();
        }
        boolean bl2 = bl = HandEquipStateMachine.instance.cycleOff || HandEquipStateMachine.instance.pendingOffChange || HandEquipStateMachine.instance.detachedOffhandEquip || HandEquipStateMachine.instance.offhandRevealPending || HandEquipStateMachine.instance.offhandAwaitingHandIn;
        if (bl) {
            if (HandEquipStateMachine.instance.renderedOff != null && !HandEquipStateMachine.instance.renderedOff.method_7960()) {
                return HandEquipStateMachine.instance.renderedOff.method_7972();
            }
            if (HandEquipStateMachine.instance.pendingOff != null && !HandEquipStateMachine.instance.pendingOff.method_7960()) {
                return HandEquipStateMachine.instance.pendingOff.method_7972();
            }
        }
        return (class_17992 = class_7462.method_6079()) == null ? class_1799.field_8037 : class_17992.method_7972();
    }

    private static boolean isDualHandedPoseBlockingVanillaHandOut() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.hasAnyDualHandedEffect(animationClip);
    }

    public static boolean abortEquipForAction(class_310 class_3102) {
        return HandEquipStateMachine.abortEquipForAction(class_3102, null);
    }

    public static boolean abortEquipForAction(class_310 class_3102, class_1268 class_12682) {
        return instance != null && class_3102 != null && class_3102.field_1724 != null ? instance.abortEquipForActionInternal(class_3102.field_1724, class_12682) : false;
    }

    public static boolean prepareOffhandForMainhandAction(class_310 class_3102) {
        return instance != null && class_3102 != null && class_3102.field_1724 != null ? instance.prepareDetachedOffhandForMainhandAction(class_3102.field_1724) : false;
    }

    public static void requestFlightOffhandLandingHide(class_746 class_7462) {
        if (instance != null && class_7462 != null) {
            instance.requestFlightOffhandLandingHideInternal(class_7462);
        }
    }

    private boolean abortEquipForActionInternal(class_746 class_7462, class_1268 class_12682) {
        class_1306 class_13062;
        if (!equipActive) {
            return false;
        }
        if (HandEquipStateMachine.isOppositeShieldEquipInProgress(class_12682)) {
            return true;
        }
        if (class_12682 == class_1268.field_5808 && this.prepareDetachedOffhandForMainhandAction(class_7462)) {
            return true;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        class_1306 class_13063 = class_13062 = class_12682 == null ? null : this.resolveArm(class_7462, class_12682);
        if (animationClip != null && this.isEquipClip(animationClip) && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            class_1306 class_13064 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            if (class_13062 != null && class_13064 != null && class_13064 != class_13062) {
                return true;
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
            PunchyAnimationManager.clearSourceHand();
        }
        this.resetToCurrent(class_7462);
        return true;
    }

    private boolean prepareDetachedOffhandForMainhandAction(class_746 class_7462) {
        if (class_7462 != null && !this.detachedOffhandEquip && this.cycleOff) {
            class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
            PoseHandlerState poseHandlerState = this.captureOffhandEquipState(class_13062);
            if (poseHandlerState == null) {
                return false;
            }
            this.renderedMain = this.copyStack(class_7462.method_6047());
            this.pendingMain = this.copyStack(class_7462.method_6047());
            this.pendingMainChange = false;
            this.cycleMain = false;
            this.skipNextEquipMain = false;
            this.pendingBlacklistHandInMain = false;
            vanillaHandOutMainTicks = 0;
            this.clearPendingMainActionClear();
            this.detachedOffhandEquip = true;
            if (this.phase == Phase.LOWERING) {
                this.activeHandOutClip = poseHandlerState.clip();
                this.swapTriggered = true;
                this.applyPending(class_7462);
                this.startDetachedOffhandRaising(class_7462);
                this.debugOffhandDetach("prepareDetachedOffhandForMainhandAction.forceRaise", class_7462, class_7462.method_6079());
                return true;
            }
            if (this.phase == Phase.RAISING) {
                this.activeHandInClip = poseHandlerState.clip();
                this.activeHandInArm = class_13062;
            }
            PunchyAnimationManager.POSE_HANDLER.setSecondaryClipAtTime(poseHandlerState.clip(), class_13062, this.resolveEquipSpeedMultiplier(), poseHandlerState.time());
            this.debugOffhandDetach("prepareDetachedOffhandForMainhandAction", class_7462, class_7462.method_6079());
            this.applyEquipOverrides(class_7462);
            return true;
        }
        return false;
    }

    private void requestFlightOffhandLandingHideInternal(class_746 class_7462) {
        class_1799 class_17992;
        if (!(class_7462 == null || this.phase != Phase.IDLE || (class_17992 = class_7462.method_6079()) != null && !class_17992.method_7960() || class_7462.method_6128() || class_7462.method_31549().field_7479 || !class_7462.method_24828() || this.pendingMainChange || this.pendingOffChange || this.cycleMain || this.cycleOff)) {
            this.pendingMainChange = false;
            this.pendingOffChange = true;
            this.pendingOff = class_1799.field_8037;
            this.suppressOffhandRender = false;
            this.offhandRevealPending = false;
            this.offhandAwaitingHandIn = false;
            this.startLowering(class_7462);
        }
    }

    private PoseHandlerState captureOffhandEquipState(class_1306 class_13062) {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
        if (animationClip != null && this.isEquipClip(animationClip) && PunchyAnimationManager.POSE_HANDLER.getSecondaryArm() == class_13062) {
            return new PoseHandlerState(animationClip, PunchyAnimationManager.POSE_HANDLER.getSecondaryTime());
        }
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip2 != null && this.isEquipClip(animationClip2)) {
            class_1306 class_13063 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            return class_13063 != null && class_13063 != class_13062 ? null : new PoseHandlerState(animationClip2, PunchyAnimationManager.POSE_HANDLER.getCurrentTime());
        }
        return null;
    }

    private boolean isEquipClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (animationClip != this.activeHandInClip && animationClip != this.activeHandOutClip) {
            String string = animationClip.getName();
            return string == null || !string.equals("hand_in") && !string.equals("hand_out") && !string.startsWith("hand_in_") && !string.startsWith("hand_out_") ? PunchyAnimationManager.isClipNamed(animationClip, "hand_in") || PunchyAnimationManager.isClipNamed(animationClip, "hand_out") : true;
        }
        return true;
    }

    private boolean isLoopingActionClip() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            return false;
        }
        if (this.isEquipClip(animationClip) || this.isBlockingEquipClip(animationClip)) {
            return false;
        }
        return this.isInspectLikeClip(animationClip) ? false : animationClip.isLoop() || PunchyAnimationManager.POSE_HANDLER.isForceLooping();
    }

    private boolean isActionClipActive() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying() ? false : !this.isEquipClip(animationClip) && !this.isBlockingEquipClip(animationClip);
    }

    private boolean updatePendingActionClearMain(class_1799 class_17992, class_1799 class_17993) {
        if (class_17992 != null && !class_17992.method_7960()) {
            this.clearPendingMainActionClear();
            return false;
        }
        if (this.pendingMainActionClear) {
            if (this.shouldFinishPendingActionClear(this.pendingMainActionClearClip, this.pendingMainActionClearTime)) {
                this.renderedMain = class_1799.field_8037;
                this.pendingMainChange = false;
                this.clearPendingMainActionClear();
                return false;
            }
            return true;
        }
        if (!this.shouldDelayActionClear(class_17992, class_17993, this.renderedMain, this.renderedOff, this.pendingMainChange, this.cycleMain)) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        this.pendingMainActionClear = true;
        this.pendingMainActionClearClip = animationClip;
        this.pendingMainActionClearTime = this.resolveActionClearTime(animationClip);
        return true;
    }

    private boolean updatePendingActionClearOff(class_1799 class_17992, class_1799 class_17993) {
        if (class_17992 != null && !class_17992.method_7960()) {
            this.clearPendingOffActionClear();
            return false;
        }
        if (this.pendingOffActionClear) {
            if (this.shouldFinishPendingActionClear(this.pendingOffActionClearClip, this.pendingOffActionClearTime)) {
                this.renderedOff = class_1799.field_8037;
                this.pendingOffChange = false;
                this.clearPendingOffActionClear();
                return false;
            }
            return true;
        }
        if (!this.shouldDelayActionClear(class_17992, class_17993, this.renderedOff, this.renderedMain, this.pendingOffChange, this.cycleOff)) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        this.pendingOffActionClear = true;
        this.pendingOffActionClearClip = animationClip;
        this.pendingOffActionClearTime = this.resolveActionClearTime(animationClip);
        return true;
    }

    private boolean shouldDelayActionClear(class_1799 class_17992, class_1799 class_17993, class_1799 class_17994, class_1799 class_17995, boolean bl, boolean bl2) {
        if (!this.isActionClipActive()) {
            return false;
        }
        if (this.isFunctionalOffhandSwap(class_17994, class_17995, class_17992, class_17993, true, true, false)) {
            return false;
        }
        if (this.shouldPreserveCurrentClipForHandOut() && !this.shouldFinishUseClipBeforeClearing(class_17992, class_17994)) {
            return false;
        }
        return this.phase == Phase.IDLE && !bl && !bl2 ? class_17992 != null && class_17992.method_7960() && class_17994 != null && !class_17994.method_7960() : false;
    }

    private boolean shouldFinishUseClipBeforeClearing(class_1799 class_17992, class_1799 class_17993) {
        if (class_17992 != null && class_17992.method_7960() && class_17993 != null && !class_17993.method_7960()) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            return animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() ? ItemAnimationManager.isTypeActive("useItem") || PunchyAnimationManager.isClipNamed(animationClip, "interact") : false;
        }
        return false;
    }

    private float resolveActionClearTime(AnimationClip animationClip) {
        return animationClip == null ? 0.0f : Math.max(0.0f, animationClip.getLength() * 0.5f);
    }

    private boolean shouldFinishPendingActionClear(AnimationClip animationClip, float f) {
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip == null || animationClip2 != animationClip) {
            return true;
        }
        return !PunchyAnimationManager.POSE_HANDLER.isPlaying() ? true : PunchyAnimationManager.POSE_HANDLER.getCurrentTime() >= f;
    }

    private void clearPendingActionClears() {
        this.clearPendingMainActionClear();
        this.clearPendingOffActionClear();
    }

    private void clearPendingMainActionClear() {
        this.pendingMainActionClear = false;
        this.pendingMainActionClearClip = null;
        this.pendingMainActionClearTime = 0.0f;
    }

    private void clearPendingOffActionClear() {
        this.pendingOffActionClear = false;
        this.pendingOffActionClearClip = null;
        this.pendingOffActionClearTime = 0.0f;
    }

    private boolean shouldBlendHandOutFromCurrent() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != null && (PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.POSE_HANDLER.isBlendOutActive())) {
            if (this.isBlockingEquipClip(animationClip)) {
                return false;
            }
            return animationClip == this.activeHandInClip || PunchyAnimationManager.isClipNamed(animationClip, "hand_in") ? true : !this.isEquipClip(animationClip);
        }
        return false;
    }

    private boolean shouldBlendHandInFromCurrentDuringSwim() {
        if (!PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
        if (animationClip != PunchyAnimationManager.SWIMING_CLIP && animationClip != PunchyAnimationManager.SWIMING_ABOVE_CLIP) {
            return false;
        }
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip2 != null && (PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.POSE_HANDLER.isBlendOutActive())) {
            return this.isBlockingEquipClip(animationClip2) ? false : animationClip2 == this.activeHandOutClip || PunchyAnimationManager.isClipNamed(animationClip2, "hand_out");
        }
        return false;
    }

    private boolean shouldBlendHandOutFromSwim() {
        if (!PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
        if (animationClip != PunchyAnimationManager.SWIMING_CLIP && animationClip != PunchyAnimationManager.SWIMING_ABOVE_CLIP) {
            return false;
        }
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip2 == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying() && !PunchyAnimationManager.POSE_HANDLER.isBlendOutActive();
    }

    private boolean isInspectLikeClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return string != null && !string.isBlank() ? string.trim().toLowerCase(Locale.ROOT).contains("inspect") : false;
    }

    private boolean isWalkOnWaterPoseActive() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip == null) {
            return false;
        }
        if (!PunchyAnimationManager.POSE_HANDLER.isPlaying() && !PunchyAnimationManager.POSE_HANDLER.isBlendOutActive()) {
            return false;
        }
        String string = animationClip.getName();
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return string2.contains("walk_on_water_start") || string2.contains("walk_on_water_loop") || string2.contains("walk_on_water_end");
        }
        return false;
    }

    private void preserveWalkOnWaterOffhandDuringMainhandEquip(class_746 class_7462, boolean bl) {
        if (bl && class_7462 != null && this.cycleMain && !this.cycleOff) {
            class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
            if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.getSecondaryArm() == class_13062) {
                this.syncWalkOnWaterSecondaryPause(animationClip);
            } else {
                AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                if (animationClip2 != null && this.containsWalkOnWaterName(animationClip2)) {
                    PunchyAnimationManager.POSE_HANDLER.setSecondaryClipAtTime(animationClip2, class_13062, PunchyAnimationManager.clipSpeedMultiplier(animationClip2), PunchyAnimationManager.POSE_HANDLER.getCurrentTime());
                    this.syncWalkOnWaterSecondaryPause(animationClip2);
                }
            }
        }
    }

    private void syncWalkOnWaterSecondaryPause(AnimationClip animationClip) {
        if (animationClip != null && this.containsWalkOnWaterLoopName(animationClip)) {
            class_310 class_3102 = class_310.method_1551();
            boolean bl = class_3102 != null && class_3102.field_1690 != null && (class_3102.field_1690.field_1894.method_1434() || class_3102.field_1690.field_1881.method_1434() || class_3102.field_1690.field_1913.method_1434() || class_3102.field_1690.field_1849.method_1434());
            PunchyAnimationManager.POSE_HANDLER.setSecondaryPaused(!bl);
        } else {
            PunchyAnimationManager.POSE_HANDLER.setSecondaryPaused(false);
        }
    }

    private boolean shouldPreserveWalkOnWaterOffhandSecondary() {
        if (!(this.cycleOff || this.pendingOffChange || this.detachedOffhandEquip || this.offhandRevealPending || this.offhandAwaitingHandIn)) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
            class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getSecondaryArm();
            if (animationClip != null && class_13062 != null && this.containsWalkOnWaterName(animationClip)) {
                class_746 class_7462;
                class_310 class_3102 = class_310.method_1551();
                class_746 class_7463 = class_7462 = class_3102 == null ? null : class_3102.field_1724;
                return class_7462 == null ? false : class_13062 == this.resolveArm(class_7462, class_1268.field_5810);
            }
            return false;
        }
        return false;
    }

    private void debugWalkOnWaterEquip(String string, boolean bl) {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
    }

    private void clearSecondaryClipUnlessPreservingWalkOnWater(String string) {
        boolean bl = this.shouldPreserveWalkOnWaterOffhandSecondary();
        this.debugWalkOnWaterEquip(string, bl);
        if (!bl) {
            PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        }
    }

    private static String debugClipName(AnimationClip animationClip) {
        return animationClip == null ? "null" : String.valueOf(animationClip.getName());
    }

    private boolean shouldDelayWalkOnWaterHandOut() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (!this.containsWalkOnWaterStartName(animationClip)) {
            return false;
        }
        return !PunchyAnimationManager.POSE_HANDLER.isPlaying() ? false : PunchyAnimationManager.POSE_HANDLER.getCurrentTime() <= 5.0E-4f;
    }

    private boolean containsWalkOnWaterStartName(AnimationClip animationClip) {
        return this.containsWalkOnWaterNamedPhase(animationClip, "walk_on_water_start");
    }

    private boolean containsWalkOnWaterLoopName(AnimationClip animationClip) {
        return this.containsWalkOnWaterNamedPhase(animationClip, "walk_on_water_loop");
    }

    private boolean containsWalkOnWaterName(AnimationClip animationClip) {
        return this.containsWalkOnWaterNamedPhase(animationClip, "walk_on_water_");
    }

    private boolean containsWalkOnWaterNamedPhase(AnimationClip animationClip, String string) {
        if (animationClip != null && animationClip.getName() != null) {
            String string2 = animationClip.getName().trim().toLowerCase(Locale.ROOT);
            return string2.contains(string);
        }
        return false;
    }

    private boolean shouldPreserveCurrentClipForHandOut() {
        return this.shouldBlendHandOutFromCurrent();
    }

    private float resolveEquipBlendStartTime(AnimationClip animationClip) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f = Float.POSITIVE_INFINITY;
        block0: for (BoneTrack boneTrack : animationClip.getTracks().values()) {
            if (boneTrack == null) continue;
            for (Keyframe keyframe : boneTrack.getKeyframes()) {
                float f2;
                if (keyframe == null || (f2 = keyframe.timeStamp()) <= 0.0f || this.isNeutralKeyframe(keyframe)) continue;
                f = Math.min(f, f2);
                continue block0;
            }
        }
        return !Float.isFinite(f) ? 0.0f : Math.max(0.0f, Math.min(f, animationClip.getLength()));
    }

    private boolean isNeutralKeyframe(Keyframe keyframe) {
        return this.isZeroVector(keyframe.position()) && this.isZeroVector(keyframe.rotationEuler()) && this.isIdentityScale(keyframe.scale());
    }

    private boolean isZeroVector(Vector3f vector3f) {
        return vector3f == null || Math.abs(vector3f.x) < 1.0E-4f && Math.abs(vector3f.y) < 1.0E-4f && Math.abs(vector3f.z) < 1.0E-4f;
    }

    private boolean clipHasTracksForArm(AnimationClip animationClip, class_1306 class_13062) {
        if (animationClip != null && class_13062 != null && !animationClip.getTracks().isEmpty()) {
            for (String string : animationClip.getTracks().keySet()) {
                if (!this.isBoneForArm(string, class_13062)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isBoneForArm(String string, class_1306 class_13062) {
        if (string != null && class_13062 != null) {
            String string2 = string.toLowerCase(Locale.ROOT);
            return class_13062 == class_1306.field_6182 ? string2.contains("left") : string2.contains("right");
        }
        return false;
    }

    private boolean isIdentityScale(Vector3f vector3f) {
        return vector3f == null || Math.abs(vector3f.x - 1.0f) < 1.0E-4f && Math.abs(vector3f.y - 1.0f) < 1.0E-4f && Math.abs(vector3f.z - 1.0f) < 1.0E-4f;
    }

    private boolean isBlockingEquipClip(AnimationClip animationClip) {
        return PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide") || PunchyAnimationManager.isClipNamed(animationClip, "use_crossbow_shot");
    }

    private boolean isBlockingEquipClipActive(class_1799 class_17992) {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return PunchyAnimationManager.POSE_HANDLER.isPlaying() && this.isBlockingEquipClip(animationClip);
    }

    private void clearRenderedEmptyHandsWhileUseBlocksEquip(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2) {
        if (class_7462 != null && class_7462.method_6115()) {
            class_1268 class_12682 = class_7462.method_6058();
            if (bl && class_12682 != class_1268.field_5808 && (class_17992 == null || class_17992.method_7960())) {
                this.renderedMain = class_1799.field_8037;
            }
            if (bl2 && class_12682 != class_1268.field_5810 && (class_17993 == null || class_17993.method_7960())) {
                this.renderedOff = class_1799.field_8037;
            }
        }
    }

    private void resumeEquipClip(class_746 class_7462) {
        if (class_7462 != null) {
            if (this.phase == Phase.LOWERING && !this.isClipPlaying(this.activeHandOutClip)) {
                this.startLowering(class_7462);
            } else if (this.phase == Phase.RAISING && !this.isClipPlaying(this.activeHandInClip)) {
                this.startRaising(class_7462);
            }
        }
    }

    private boolean shouldSkipRaiseForOffhandRemovalBefore() {
        if (this.cycleMain || !this.cycleOff) {
            return false;
        }
        return !this.pendingOffChange ? false : this.pendingOff == null || this.pendingOff.method_7960();
    }

    private boolean shouldSkipRaiseForOffhandRemovalAfter(class_746 class_7462) {
        if (this.cycleMain || !this.cycleOff) {
            return false;
        }
        if (this.renderedOff != null && !this.renderedOff.method_7960()) {
            return false;
        }
        class_1799 class_17992 = class_7462 == null ? class_1799.field_8037 : class_7462.method_6079();
        return class_17992 == null || class_17992.method_7960();
    }

    private void finishLoweringWithoutRaise(class_746 class_7462) {
        this.phase = Phase.IDLE;
        this.cycleMain = false;
        this.cycleOff = false;
        this.swapTriggered = false;
        this.suppressOffhandRender = false;
        this.offhandRevealPending = false;
        this.offhandAwaitingHandIn = false;
        this.activeHandOutClip = null;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        this.pendingBlacklistHandInMain = false;
        this.pendingBlacklistHandInOff = false;
        this.blacklistTransitionHandIn = false;
        vanillaHandOutMainTicks = 0;
        vanillaHandOutOffTicks = 0;
        boolean bl = this.shouldPreserveWalkOnWaterOffhandSecondary();
        this.debugWalkOnWaterEquip("finishLoweringWithoutRaise", bl);
        PunchyAnimationManager.POSE_HANDLER.setIgnoreDualHanded(false);
        if (!bl) {
            PunchyAnimationManager.POSE_HANDLER.clearSecondaryClip();
        }
        this.markEquipTransitionTick(class_7462);
        this.applyEquipOverrides(class_7462);
        if (class_7462 != null) {
            PunchyClient.getWalkOnWaterStateMachine().reattachMainhandAfterEquip(class_7462);
        }
        if (this.pendingMainChange || this.pendingOffChange) {
            this.startLowering(class_7462);
        }
    }

    private void markEquipTransitionTick(class_746 class_7462) {
        if (class_7462 != null) {
            lastEquipTransitionPlayerTick = class_7462.field_6012;
        }
    }

    public static boolean wasEquipTransitionRecent(class_310 class_3102, int n) {
        if (class_3102 != null && class_3102.field_1724 != null && n >= 0) {
            int n2 = lastEquipTransitionPlayerTick;
            if (n2 < 0) {
                return false;
            }
            int n3 = class_3102.field_1724.field_6012;
            return n3 >= n2 && n3 - n2 <= n;
        }
        return false;
    }

    private boolean shouldBypassTridentThrowBlock(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            return !PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") && !PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") && !PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide") ? false : this.hasItemChanged(this.renderedMain, class_17992);
        }
        return false;
    }

    public boolean isOffhandRenderSuppressed() {
        return UseActionTracker.wasRecentUseForHand(class_1268.field_5810, 1800000000L) ? false : this.suppressOffhandRender || this.offhandAwaitingHandIn && !this.isHandInActive();
    }

    public static boolean shouldHideOffhandTridentThrowCacheRender() {
        HandEquipStateMachine handEquipStateMachine = instance;
        return handEquipStateMachine != null && handEquipStateMachine.hideOffhandTridentThrowCache && handEquipStateMachine.phase == Phase.LOWERING && handEquipStateMachine.cycleOff;
    }

    public boolean isHandInActiveForOffhand() {
        return this.isHandInActive();
    }

    private boolean shouldSuppressOffhandHandOut(class_746 class_7462, class_1799 class_17992) {
        return !HandEquipStateMachine.isFabricPlatform() ? false : class_17992 == null || class_17992.method_7960();
    }

    private static boolean isFabricPlatform() {
        try {
            return Services.PLATFORM != null && "fabric".equalsIgnoreCase(Services.PLATFORM.getPlatformName());
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public boolean shouldLowerOffhand(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        if (UseActionTracker.wasRecentUseForHand(class_1268.field_5810, 1800000000L)) {
            return false;
        }
        if (this.suppressOffhandRender || this.offhandRevealPending) {
            return true;
        }
        if (!this.offhandAwaitingHandIn) {
            return false;
        }
        if (!this.isHandInActive()) {
            return true;
        }
        class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
        return this.activeHandInArm != null && this.activeHandInArm != class_13062;
    }

    private boolean shouldSuppressOffhandRender(class_1799 class_17992) {
        return this.offhandRevealPending ? true : this.phase == Phase.LOWERING && this.cycleOff && (this.renderedOff == null || this.renderedOff.method_7960());
    }

    private void updateOffhandRevealPending(class_746 class_7462, class_1799 class_17992, boolean bl) {
        if (this.isHandInActive() && this.shouldClearOffhandReveal(class_7462)) {
            this.offhandRevealPending = false;
        } else {
            boolean bl2;
            boolean bl3 = this.renderedOff == null || this.renderedOff.method_7960();
            boolean bl4 = bl2 = class_17992 != null && !class_17992.method_7960();
            if (!bl3 || !bl2 || !bl && !this.pendingOffChange) {
                if (!(bl2 || this.pendingOff != null && !this.pendingOff.method_7960() || this.pendingOffChange)) {
                    this.offhandRevealPending = false;
                }
            } else {
                this.offhandRevealPending = true;
            }
        }
    }

    private void updateOffhandAwaitingHandIn(class_746 class_7462, class_1799 class_17992) {
        boolean bl = this.isHandInActive();
        if (bl && this.shouldClearOffhandReveal(class_7462)) {
            this.offhandAwaitingHandIn = false;
            this.lastOffhandEmpty = class_17992 == null || class_17992.method_7960();
        } else {
            boolean bl2;
            boolean bl3 = bl2 = class_17992 == null || class_17992.method_7960();
            if (this.lastOffhandEmpty && !bl2) {
                this.offhandAwaitingHandIn = true;
            } else if (bl2) {
                this.offhandAwaitingHandIn = false;
            }
            this.lastOffhandEmpty = bl2;
        }
    }

    private boolean isHandInActive() {
        boolean bl = this.detachedOffhandEquip && this.cycleOff && !this.cycleMain;
        AnimationClip animationClip = bl ? PunchyAnimationManager.POSE_HANDLER.getSecondaryClip() : PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return this.phase == Phase.RAISING && animationClip != null && animationClip == this.activeHandInClip && (bl || PunchyAnimationManager.POSE_HANDLER.isPlaying());
    }

    private boolean shouldClearOffhandReveal(class_746 class_7462) {
        if (class_7462 == null) {
            return true;
        }
        if (this.cycleOff) {
            return true;
        }
        class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
        return this.activeHandInArm == null ? false : this.activeHandInArm == class_13062;
    }

    private boolean cancelOffhandTransitionForPoseOverrideInternal(class_746 class_7462) {
        boolean bl;
        if (class_7462 == null) {
            return false;
        }
        class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
        boolean bl2 = animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && this.isEquipClip(animationClip) && PunchyAnimationManager.POSE_HANDLER.getActiveArm() == class_13062;
        boolean bl3 = animationClip2 != null && this.isEquipClip(animationClip2);
        boolean bl4 = bl = this.cycleOff || this.pendingOffChange || this.offhandRevealPending || this.offhandAwaitingHandIn || bl2 || bl3;
        if (!bl) {
            return false;
        }
        this.pendingOff = this.copyStack(class_7462.method_6079());
        this.renderedOff = this.copyStack(class_7462.method_6079());
        this.pendingOffChange = false;
        this.cycleOff = false;
        this.suppressOffhandRender = false;
        this.offhandRevealPending = false;
        this.offhandAwaitingHandIn = false;
        this.detachedOffhandEquip = false;
        this.shieldOppositeEquip = false;
        this.pendingBlacklistHandInOff = false;
        vanillaHandOutOffTicks = 0;
        if (bl2) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
            PunchyAnimationManager.clearSourceHand();
        }
        if (bl3) {
            this.clearSecondaryClipUnlessPreservingWalkOnWater("finishPrimaryOffhandEquip");
        }
        if (!this.cycleMain) {
            this.phase = Phase.IDLE;
            this.activeHandInClip = null;
            this.activeHandOutClip = null;
            this.activeHandInArm = null;
        }
        this.applyEquipOverrides(class_7462);
        return true;
    }

    private void startDetachedOffhandRaising(class_746 class_7462) {
        this.suppressOffhandRender = false;
        if (class_7462 != null) {
            class_1799 targetStack = this.pendingOff != null && !this.pendingOff.method_7960() ? this.pendingOff : class_7462.method_6079();
            if (targetStack == null || targetStack.method_7960()) {
                this.finishLoweringWithoutRaise(class_7462);
                return;
            }
            this.renderedOff = this.copyStack(targetStack);
            this.pendingOff = this.copyStack(targetStack);
            this.pendingOffChange = false;
            this.debugOffhandDetach("startDetachedOffhandRaising.sync", class_7462, targetStack);
        }
        EquipSelection equipSelection = this.resolveHandInClip(class_7462);
        AnimationClip animationClip = equipSelection.clip;
        if (animationClip == null) {
            CustomAnimationResolver.applyLoopSpeed(null);
            animationClip = PunchyAnimationManager.resolveHandedClip("hand_in", class_310.method_1551(), class_1268.field_5810);
        } else {
            CustomAnimationResolver.applyLoopSpeed(equipSelection.entry);
        }
        if (animationClip == null) {
            ItemAnimationManager.forceTypeOnce(class_1268.field_5810, "hand_in");
            this.phase = Phase.IDLE;
            this.cycleOff = false;
            this.detachedOffhandEquip = false;
            this.shieldOppositeEquip = false;
            this.blacklistTransitionHandIn = false;
        } else {
            this.activeHandInClip = animationClip;
            this.activeHandInArm = class_7462 == null ? null : this.resolveArm(class_7462, class_1268.field_5810);
            this.applyEquipOverrides(class_7462);
            if (this.activeHandInArm != null) {
                PunchyAnimationManager.POSE_HANDLER.requestClearArm(this.activeHandInArm);
            }
            PunchyAnimationManager.POSE_HANDLER.setSecondaryClipAtTime(animationClip, this.activeHandInArm, this.resolveEquipSpeedMultiplier(), 0.0f);
            this.debugOffhandDetach("startDetachedOffhandRaising.play", class_7462, class_7462 != null ? class_7462.method_6079() : class_1799.field_8037);
            this.blacklistTransitionHandIn = false;
            this.phase = Phase.RAISING;
        }
    }

    private void debugOffhandTransition(String string, class_746 class_7462) {
    }

    private boolean tryDetachOffhandDuringAction(class_746 class_7462) {
        if (class_7462 != null && !this.detachedOffhandEquip && this.cycleOff) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
            class_1306 class_13062 = this.resolveArm(class_7462, class_1268.field_5810);
            if (animationClip != null && this.isEquipClip(animationClip) && PunchyAnimationManager.POSE_HANDLER.getSecondaryArm() == class_13062) {
                this.renderedMain = this.copyStack(class_7462.method_6047());
                this.pendingMain = this.copyStack(class_7462.method_6047());
                this.pendingMainChange = false;
                this.cycleMain = false;
                this.skipNextEquipMain = false;
                this.pendingBlacklistHandInMain = false;
                vanillaHandOutMainTicks = 0;
                this.clearPendingMainActionClear();
                this.detachedOffhandEquip = true;
                if (this.phase == Phase.RAISING) {
                    this.activeHandInArm = class_13062;
                }
                this.applyEquipOverrides(class_7462);
                this.debugOffhandDetach("tryDetachOffhandDuringAction.detach", class_7462, class_7462.method_6079());
                return true;
            }
            return false;
        }
        return false;
    }

    private void logOffhandState(class_746 class_7462, class_1799 class_17992) {
    }

    private boolean shouldSwapNow() {
        if (!(this.detachedOffhandEquip && this.cycleOff && !this.cycleMain || this.shieldOppositeEquip)) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null) {
                return true;
            }
            float f = PunchyAnimationManager.POSE_HANDLER.getCurrentTime();
            return animationClip.isEffectActive("swap_end", f) || animationClip.isEffectActive("swap_items", f) || animationClip.isEffectActive("swap_item", f);
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
        if (animationClip == null) {
            return true;
        }
        float f = PunchyAnimationManager.POSE_HANDLER.getSecondaryTime();
        return animationClip.isEffectActive("swap_end", f) || animationClip.isEffectActive("swap_items", f) || animationClip.isEffectActive("swap_item", f);
    }

    private boolean isClipPlaying(AnimationClip animationClip) {
        return !(this.detachedOffhandEquip && this.cycleOff && !this.cycleMain || this.shieldOppositeEquip) ? animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip : animationClip != null && PunchyAnimationManager.POSE_HANDLER.getSecondaryClip() == animationClip;
    }

    private boolean isHandOutInEarlyTransitionWindow() {
        if (!(this.detachedOffhandEquip && this.cycleOff && !this.cycleMain || this.shieldOppositeEquip)) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip != null && animationClip == this.activeHandOutClip && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                float f = animationClip.getLength();
                return f <= 0.0f ? true : PunchyAnimationManager.POSE_HANDLER.getCurrentTime() >= Math.max(0.0f, f - 0.05f);
            }
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getSecondaryClip();
        if (animationClip != null && animationClip == this.activeHandOutClip) {
            float f = animationClip.getLength();
            if (f <= 0.0f) {
                return true;
            }
            float f2 = PunchyAnimationManager.POSE_HANDLER.getSecondaryTime();
            return f2 > 0.05f && f2 >= Math.max(0.0f, f - 0.05f);
        }
        return false;
    }

    private boolean updateHotbarSlot(class_746 class_7462) {
        int n = this.resolveSelectedSlot(class_7462);
        if (n < 0) {
            return false;
        }
        boolean bl = n != this.lastHotbarSlot;
        this.lastHotbarSlot = n;
        return bl;
    }

    private boolean shouldAnimateHotbarChange(class_1799 class_17992, class_1799 class_17993) {
        return !class_17992.method_7960() || !class_17993.method_7960();
    }

    private boolean isRapidHotbarScrollActive() {
        return this.hotbarRapidWindowTicks > 0 && this.hotbarChangeStreak >= 2;
    }

    private void recordFunctionalSwapIfNeeded(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2, boolean bl3) {
        class_1799 class_17994 = this.pendingMainChange ? this.pendingMain : this.renderedMain;
        class_1799 class_17995 = this.pendingOffChange ? this.pendingOff : this.renderedOff;
        this.recordFunctionalSwapIfNeeded(class_7462, class_17994, class_17995, class_17992, class_17993, bl, bl2, bl3);
    }

    private void recordFunctionalSwapIfNeeded(class_746 class_7462, class_1799 class_17992, class_1799 class_17993, class_1799 class_17994, class_1799 class_17995, boolean bl, boolean bl2, boolean bl3) {
        if (class_7462 != null && this.isFunctionalOffhandSwap(class_17992, class_17993, class_17994, class_17995, bl, bl2, bl3)) {
            this.trimFunctionalSwapTicks(class_7462.field_6012);
            this.recentFunctionalSwapTicks.addLast(class_7462.field_6012);
        }
    }

    private boolean isFunctionalSwapBurstActive(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        this.trimFunctionalSwapTicks(class_7462.field_6012);
        return this.recentFunctionalSwapTicks.size() >= 2;
    }

    private boolean wasFunctionalSwapRecordedThisTick(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        this.trimFunctionalSwapTicks(class_7462.field_6012);
        Integer n = this.recentFunctionalSwapTicks.peekLast();
        return n != null && n == class_7462.field_6012;
    }

    private void trimFunctionalSwapTicks(int n) {
        int n2;
        while (!this.recentFunctionalSwapTicks.isEmpty() && n - (n2 = this.recentFunctionalSwapTicks.peekFirst().intValue()) > 10) {
            this.recentFunctionalSwapTicks.removeFirst();
        }
    }

    private boolean isFunctionalOffhandSwap(class_1799 class_17992, class_1799 class_17993, class_1799 class_17994, class_1799 class_17995, boolean bl, boolean bl2, boolean bl3) {
        return !bl3 && bl && bl2 ? this.areEquivalentStacks(class_17992, class_17995) && this.areEquivalentStacks(class_17993, class_17994) : false;
    }

    private boolean areEquivalentStacks(class_1799 class_17992, class_1799 class_17993) {
        return !this.hasItemChanged(class_17992, class_17993);
    }

    private class_1306 resolveActiveArm(class_746 class_7462) {
        if (this.cycleMain && this.cycleOff) {
            return null;
        }
        if (this.cycleMain) {
            return this.resolveArm(class_7462, class_1268.field_5808);
        }
        return this.cycleOff ? this.resolveArm(class_7462, class_1268.field_5810) : null;
    }

    private class_1306 resolveArm(class_746 class_7462, class_1268 class_12682) {
        boolean bl;
        boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
        if (class_12682 == class_1268.field_5808) {
            return bl ? class_1306.field_6183 : class_1306.field_6182;
        }
        return bl ? class_1306.field_6182 : class_1306.field_6183;
    }

    private class_1268 resolveHandForArm(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            return this.resolveArm(class_7462, class_1268.field_5808) == class_13062 ? class_1268.field_5808 : class_1268.field_5810;
        }
        return class_1268.field_5808;
    }

    private EquipSelection resolveHandOutClip() {
        EquipSelection equipSelection = this.cycleMain ? this.resolveCustomEquipClip(this.renderedMain, class_1268.field_5808, "hand_out") : new EquipSelection(null, null, class_1268.field_5808, false);
        EquipSelection equipSelection2 = this.cycleOff ? this.resolveCustomEquipClip(this.renderedOff, class_1268.field_5810, "hand_out") : new EquipSelection(null, null, class_1268.field_5810, false);
        return this.chooseEquipSelection(equipSelection, equipSelection2);
    }

    private EquipSelection resolveHandInClip(class_746 class_7462) {
        class_1799 class_17992 = this.resolveRaiseTargetStack(class_1268.field_5808, this.pendingMain, this.renderedMain, class_7462);
        class_1799 class_17993 = this.resolveRaiseTargetStack(class_1268.field_5810, this.pendingOff, this.renderedOff, class_7462);
        EquipSelection equipSelection = this.cycleMain ? this.resolveCustomEquipClip(class_17992, class_1268.field_5808, "hand_in") : new EquipSelection(null, null, class_1268.field_5808, false);
        EquipSelection equipSelection2 = this.cycleOff ? this.resolveCustomEquipClip(class_17993, class_1268.field_5810, "hand_in") : new EquipSelection(null, null, class_1268.field_5810, false);
        return this.chooseEquipSelection(equipSelection, equipSelection2);
    }

    private class_1799 resolveRaiseTargetStack(class_1268 class_12682, class_1799 class_17992, class_1799 class_17993, class_746 class_7462) {
        if (class_17992 != null && !class_17992.method_7960()) {
            return class_17992;
        }
        class_1799 class_17994 = class_1799.field_8037;
        if (class_7462 != null) {
            class_1799 class_17995 = class_17994 = class_12682 == class_1268.field_5810 ? class_7462.method_6079() : class_7462.method_6047();
        }
        if (class_17994 != null && !class_17994.method_7960()) {
            return class_17994;
        }
        return class_17993 != null && !class_17993.method_7960() ? class_17993 : class_1799.field_8037;
    }

    private EquipSelection resolveCustomEquipClip(class_1799 class_17992, class_1268 class_12682, String string) {
        if (class_17992 != null && string != null) {
            class_310 class_3102;
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, string);
            CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, string, customAnimationEntry, class_3102 = class_310.method_1551(), class_12682);
            if (clipLookup.clip() == null) {
                return new EquipSelection(null, customAnimationEntry, class_12682, false);
            }
            boolean bl = !clipLookup.usedTypeFallback() && clipLookup.resolvedName() != null && !clipLookup.resolvedName().isBlank();
            return new EquipSelection(clipLookup.clip(), customAnimationEntry, class_12682, bl);
        }
        return new EquipSelection(null, null, class_12682, false);
    }

    private EquipSelection chooseEquipSelection(EquipSelection equipSelection, EquipSelection equipSelection2) {
        if (equipSelection.clip == null) {
            return equipSelection2;
        }
        if (equipSelection2.clip == null) {
            return equipSelection;
        }
        if (this.isWalkOnWaterPoseActive() && this.cycleOff && !this.cycleMain) {
            return equipSelection2;
        }
        if (equipSelection.explicitCustom != equipSelection2.explicitCustom) {
            return equipSelection.explicitCustom ? equipSelection : equipSelection2;
        }
        return equipSelection;
    }

    private float resolveEquipSpeedMultiplier() {
        return 1.0f / Math.max(0.01f, PunchyAnimationManager.ANIMATION_SPEED);
    }

    private class_1799 copyStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7972() : class_1799.field_8037;
    }

    private String debugStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? String.valueOf(class_7923.field_41178.method_10221(class_17992.method_7909())) : "empty";
    }

    private String clipName(AnimationClip animationClip) {
        if (animationClip == null) {
            return "null";
        }
        String string = animationClip.getName();
        return string != null && !string.isBlank() ? string : "<unnamed>";
    }

    private String formatDebugTime(float f) {
        return String.format(Locale.ROOT, "%.3f", Float.valueOf(f));
    }

    private void debugOffhandDetach(String string, class_746 class_7462, class_1799 class_17992) {
    }

    private boolean wasItemBlacklisted(class_1799 class_17992) {
        return PunchyConfig.isItemBlacklisted(class_17992);
    }

    private boolean isHandBlacklisted(class_746 class_7462, class_1268 class_12682) {
        return PunchyConfig.isHandBlacklisted((class_1657)class_7462, class_12682);
    }

    private boolean hasItemChanged(class_1799 class_17992, class_1799 class_17993) {
        class_1799 class_17994;
        class_1799 class_17995 = class_17992 == null ? class_1799.field_8037 : class_17992;
        class_1799 class_17996 = class_17994 = class_17993 == null ? class_1799.field_8037 : class_17993;
        if (!class_17995.method_31574(class_17994.method_7909())) {
            return true;
        }
        if (class_17995.method_7909() instanceof class_1764 && class_17994.method_7909() instanceof class_1764) {
            return false;
        }
        for (class_9336 class_93362 : class_17994.method_57353()) {
            if (this.shouldIgnoreComponentForEquipDiff(class_93362.comp_2443())) continue;
            if (!class_17995.method_57353().method_57832(class_93362.comp_2443())) {
                return true;
            }
            if (Objects.equals(class_17995.method_57824(class_93362.comp_2443()), class_93362.comp_2444())) continue;
            return true;
        }
        for (class_9336 class_93362 : class_17995.method_57353()) {
            if (this.shouldIgnoreComponentForEquipDiff(class_93362.comp_2443()) || class_17994.method_57353().method_57832(class_93362.comp_2443())) continue;
            return true;
        }
        return false;
    }

    private boolean shouldIgnoreComponentForEquipDiff(class_9331<?> class_93312) {
        if (class_93312 == null) {
            return false;
        }
        if (class_93312 == class_9334.field_49629) {
            return true;
        }
        class_2960 class_29602 = class_7923.field_49658.method_10221(class_93312);
        return class_29602 != null && !"minecraft".equals(class_29602.method_12836());
    }

    private boolean shouldSkipHandEquipOnSwap(class_1799 class_17992, class_1799 class_17993, boolean bl, boolean bl2) {
        if (!bl && !bl2) {
            return false;
        }
        if (bl && this.shouldIgnoreMainhandTridentSwap(class_17992)) {
            this.skipNextEquipMain = false;
            return true;
        }
        if (!bl || !this.hasSkipHandEquipFlag(this.renderedMain, class_17992) && !this.skipNextEquipMain) {
            if (!bl2 || !this.hasSkipHandEquipFlag(this.renderedOff, class_17993) && !this.skipNextEquipOff) {
                return false;
            }
            this.skipNextEquipOff = false;
            return true;
        }
        this.skipNextEquipMain = false;
        return true;
    }

    private boolean shouldIgnoreMainhandTridentSwap(class_1799 class_17992) {
        boolean bl;
        class_1799 class_17993 = this.renderedMain == null ? class_1799.field_8037 : this.renderedMain;
        class_1799 class_17994 = class_17992 == null ? class_1799.field_8037 : class_17992;
        boolean bl2 = bl = class_17993.method_7909() instanceof class_1835 || class_17994.method_7909() instanceof class_1835;
        if (!bl) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        if (TridentStateMachine.isRecentActionBlockingEquip(class_3102)) {
            return true;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return !(PunchyAnimationManager.isClipNamed(animationClip, "use_trident") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide")) ? PunchyAnimationManager.POSE_HANDLER.isBlendOutActive() && (PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide")) : true;
    }

    private boolean shouldHideOffhandTridentThrowCache(class_1799 class_17992, boolean bl) {
        if (bl && class_17992 != null && class_17992.method_7960()) {
            class_1799 class_17993 = this.renderedOff == null ? class_1799.field_8037 : this.renderedOff;
            return class_17993.method_7909() instanceof class_1835;
        }
        return false;
    }

    private void updateSkipEquipLatches(class_1799 class_17992, class_1799 class_17993) {
        if (this.isSkipHandEquipWindowActive()) {
            if (this.hasSkipHandEquipFlagForStack(class_17992)) {
                this.skipNextEquipMain = true;
            }
            if (this.hasSkipHandEquipFlagForStack(class_17993)) {
                this.skipNextEquipOff = true;
            }
        }
    }

    private boolean hasSkipHandEquipFlag(class_1799 class_17992, class_1799 class_17993) {
        return this.isThrowableConsumedToEmpty(class_17992, class_17993) ? true : this.hasSkipHandEquipFlagForStack(class_17992) || this.hasSkipHandEquipFlagForStack(class_17993);
    }

    private boolean isThrowableConsumedToEmpty(class_1799 class_17992, class_1799 class_17993) {
        if (!this.isThrowableSkipEquipStack(class_17992)) {
            return false;
        }
        if (class_17993 != null && !class_17993.method_7960()) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip == null) {
            return false;
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "useItem");
        return customAnimationEntry != null && this.isEntryActiveForCurrentUseClip(customAnimationEntry, "useItem", animationClip) ? true : PunchyAnimationManager.isClipNamed(animationClip, "throw_item");
    }

    private boolean isThrowableSkipEquipStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_31574(class_1802.field_8436) || class_17992.method_31574(class_1802.field_8150) || class_17992.method_31574(class_1802.field_8287) || class_17992.method_31574(class_1802.field_8543) || class_17992.method_31574(class_1802.field_8803) || class_17992.method_31574(class_1802.field_8634) || class_17992.method_31574(class_1802.field_8449) : false;
    }

    private boolean hasSkipHandEquipFlagForStack(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            String[] stringArray;
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null) {
                return false;
            }
            for (String string : stringArray = new String[]{"useDrink", "useEat", "useItem"}) {
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, string);
                if (!CustomAnimationResolver.shouldSkipHandEquip(customAnimationEntry) || !this.isEntryActiveForCurrentUseClip(customAnimationEntry, string, animationClip)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isSkipHandEquipWindowActive() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        return animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying() ? false : !this.isEquipClip(animationClip) && !this.isBlockingEquipClip(animationClip);
    }

    private boolean isEntryActiveForCurrentUseClip(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, String string, AnimationClip animationClip) {
        if (animationClip != null && string != null) {
            String string2 = CustomAnimationResolver.resolveSingleName(customAnimationEntry);
            if (string2 != null && !string2.isBlank()) {
                return PunchyAnimationManager.isClipNamed(animationClip, string2);
            }
            String string3 = string.trim().toLowerCase(Locale.ROOT).replace("_", "");
            if ("usedrink".equals(string3)) {
                return PunchyAnimationManager.isClipNamed(animationClip, "use_drink");
            }
            if ("useeat".equals(string3)) {
                return PunchyAnimationManager.isClipNamed(animationClip, "use_eat");
            }
            return !"useitem".equals(string3) ? false : PunchyAnimationManager.isClipNamed(animationClip, "interact") || PunchyAnimationManager.isClipNamed(animationClip, "mining_use_foward") || PunchyAnimationManager.isClipNamed(animationClip, "mining_use_digging");
        }
        return false;
    }

    private int resolveSelectedSlot(class_746 class_7462) {
        if (class_7462 == null) {
            return -1;
        }
        class_1661 class_16612 = class_7462.method_31548();
        if (class_16612 == null) {
            return -1;
        }
        try {
            return class_16612.field_7545;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    static {
        EFFECT_SWAP_END = "swap_end";
        EFFECT_SWAP = "swap_items";
        EFFECT_SWAP_ITEM = "swap_item";
        lastEquipTransitionPlayerTick = -1;
    }

    static enum Phase {
        IDLE,
        LOWERING,
        RAISING;

    }

    static final class EquipSelection {
        final AnimationClip clip;
        final PunchyTuningConfig.CustomAnimationEntry entry;
        final class_1268 hand;
        final boolean explicitCustom;

        EquipSelection(AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_1268 class_12682, boolean bl) {
            this.clip = animationClip;
            this.entry = customAnimationEntry;
            this.hand = class_12682;
            this.explicitCustom = bl;
        }

        @Override
        public final String toString() {
            return "EquipSelection[" + "clip=" + this.clip + ", " + "entry=" + this.entry + ", " + "hand=" + this.hand + ", " + "explicitCustom=" + this.explicitCustom + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.clip, this.entry, this.hand, this.explicitCustom);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof EquipSelection other)) return false;
            return java.util.Objects.deepEquals(this.clip, other.clip)
                && java.util.Objects.deepEquals(this.entry, other.entry)
                && java.util.Objects.deepEquals(this.hand, other.hand)
                && java.util.Objects.deepEquals(this.explicitCustom, other.explicitCustom);
        }

        public AnimationClip clip() {
            return this.clip;
        }

        public PunchyTuningConfig.CustomAnimationEntry entry() {
            return this.entry;
        }

        public class_1268 hand() {
            return this.hand;
        }

        public boolean explicitCustom() {
            return this.explicitCustom;
        }
    }

    record PoseHandlerState(AnimationClip clip, float time) {
    }
}

