package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_2378;
import net.minecraft.class_310;
import net.minecraft.class_6880;
import net.minecraft.class_7924;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class TridentStateMachine {
    private static final int THROW_MIN_CHARGE_TICKS = 10;
    private static final int RECENT_ACTION_BLOCK_TICKS = 12;
    private static int lastRecentActionPlayerTick = -1;
    private boolean wasUsing;
    private class_1268 lastHand = class_1268.field_5808;
    private AnimationClip activeClip;
    private boolean lastWasRiptide;
    private boolean lastWasLoyalty;
    private int lastUseTicks;
    private class_1268 lastUsedHand;
    private boolean waitingLoyaltyReturn;
    private boolean loyaltyHandBecameEmpty;
    private int loyaltyThrowStartTick = -1;
    private int loyaltyThrowStartHotbarSlot = -1;
    private class_1268 loyaltyReturnHand = class_1268.field_5808;
    private String lastPreferredOption;
    private int chargedHotbarSlot = -1;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl = class_17992.method_7909() instanceof class_1835;
            boolean bl2 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            boolean bl3 = class_3102.field_1724.method_6115() && bl && bl2;
            class_1268 class_12682 = class_3102.field_1724.method_6058();
            if (bl3 && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                if (this.wasUsing) {
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                }
                this.wasUsing = false;
            } else if (!bl3 || !HandEquipStateMachine.isEquipInProgress() || HandEquipStateMachine.abortEquipForAction(class_3102, class_12682)) {
                if (bl3) {
                    lastRecentActionPlayerTick = class_3102.field_1724.field_6012;
                    this.lastUseTicks = class_17992.method_7935((class_1309)class_3102.field_1724) - (class_3102.field_1724.method_6014() + 1);
                    this.lastPreferredOption = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
                    try {
                        if (class_3102.field_1687 != null) {
                            class_2378 class_23782 = class_3102.field_1687.method_30349().method_30530(class_7924.field_41265);
                            class_6880.class_6883 class_68832 = class_23782.method_46747(class_1893.field_9104);
                            class_6880.class_6883 class_68833 = class_23782.method_46747(class_1893.field_9120);
                            this.lastWasRiptide = class_1890.method_8225((class_6880)class_68832, (class_1799)class_17992) > 0;
                            this.lastWasLoyalty = class_1890.method_8225((class_6880)class_68833, (class_1799)class_17992) > 0;
                        }
                    }
                    catch (Throwable throwable) {
                        this.lastWasRiptide = false;
                        this.lastWasLoyalty = false;
                    }
                    if (this.wasUsing && this.lastUsedHand != null && this.lastUsedHand != class_12682) {
                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                        this.activeClip = null;
                        PunchyAnimationManager.clearSourceHand();
                        HandEquipStateMachine.requestRenderedSync();
                    }
                    if (!this.wasUsing || this.lastUsedHand != class_12682) {
                        int n = this.chargedHotbarSlot = class_12682 == class_1268.field_5808 ? class_3102.field_1724.method_31548().field_7545 : -1;
                    }
                    if (!this.wasUsing || this.lastUsedHand != class_12682 || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.activeClip == null) {
                        this.lastHand = class_3102.field_1724.method_6058();
                        this.playUse(class_3102, this.lastHand);
                    }
                } else if (this.wasUsing) {
                    boolean bl4;
                    boolean bl5 = !bl2;
                    boolean bl6 = this.lastHand != class_1268.field_5808 || this.chargedHotbarSlot < 0 || class_3102.field_1724.method_31548().field_7545 == this.chargedHotbarSlot;
                    boolean bl7 = bl4 = this.lastHand == class_1268.field_5808 && this.chargedHotbarSlot >= 0 && class_3102.field_1724.method_31548().field_7545 != this.chargedHotbarSlot;
                    if (bl5 && bl6 && this.lastUseTicks >= 10) {
                        this.playThrow(class_3102, this.lastHand);
                        lastRecentActionPlayerTick = class_3102.field_1724.field_6012;
                    } else {
                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                        if (bl4) {
                            lastRecentActionPlayerTick = -1;
                            HandEquipStateMachine.triggerImmediateEquipCycle(class_3102, this.lastHand);
                        }
                    }
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                    this.lastUseTicks = 0;
                    this.lastUsedHand = null;
                    this.lastPreferredOption = null;
                    this.chargedHotbarSlot = -1;
                }
                if (this.waitingLoyaltyReturn) {
                    if (this.shouldCancelLoyaltyThrowOnEquipSwap(class_3102)) {
                        this.cancelLoyaltyThrowForEquipSwap();
                    }
                    this.monitorLoyaltyReturn(class_3102);
                }
                this.wasUsing = bl3;
                this.lastUsedHand = bl3 ? class_12682 : null;
            }
        }
    }

    private void playUse(class_310 class_3102, class_1268 class_12682) {
        AnimationClip animationClip = PunchyAnimationManager.resolveHandedClip("use_trident", class_3102, class_12682, this.lastPreferredOption);
        if (animationClip != null) {
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            boolean bl = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            }
            boolean bl2 = animationClip.isHoldOnLastFrame();
            PunchyAnimationManager.POSE_HANDLER.setLooping(!bl2);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            CustomAnimationResolver.playConfiguredClip(animationClip, null, f, true, bl2);
            this.activeClip = animationClip;
        }
    }

    private void playThrow(class_310 class_3102, class_1268 class_12682) {
        AnimationClip animationClip = PunchyAnimationManager.resolveHandedClip("use_trident_throw", class_3102, class_12682, this.lastPreferredOption);
        if (this.lastWasRiptide) {
            animationClip = PunchyAnimationManager.resolveHandedClip("use_trident_throw_riptide", class_3102, class_12682, this.lastPreferredOption);
        } else if (this.lastWasLoyalty) {
            animationClip = PunchyAnimationManager.resolveHandedClip("use_trident_throw_loyalty", class_3102, class_12682, this.lastPreferredOption);
        }
        if (animationClip == null) {
            animationClip = PunchyAnimationManager.resolveHandedClip("use_trident_throw", class_3102, class_12682, this.lastPreferredOption);
        }
        if (animationClip != null) {
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            boolean bl = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            CustomAnimationResolver.playConfiguredClip(animationClip, null, f, true, false);
            if (this.lastWasLoyalty && !this.lastWasRiptide) {
                this.waitingLoyaltyReturn = true;
                this.loyaltyHandBecameEmpty = false;
                this.loyaltyThrowStartTick = class_3102.field_1724 != null ? class_3102.field_1724.field_6012 : -1;
                this.loyaltyThrowStartHotbarSlot = class_3102.field_1724 != null ? class_3102.field_1724.method_31548().field_7545 : -1;
                this.loyaltyReturnHand = class_12682;
            } else {
                this.waitingLoyaltyReturn = false;
                this.loyaltyHandBecameEmpty = false;
                this.loyaltyThrowStartTick = -1;
                this.loyaltyThrowStartHotbarSlot = -1;
            }
        }
    }

    private void monitorLoyaltyReturn(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            this.clearLoyaltyReturnTracking();
        } else if (!class_3102.field_1724.method_6115()) {
            int n = class_3102.field_1724.field_6012;
            if (this.loyaltyThrowStartTick >= 0 && n - this.loyaltyThrowStartTick > 200) {
                this.clearLoyaltyReturnTracking();
            } else {
                boolean bl;
                class_1799 class_17992 = class_3102.field_1724.method_5998(this.loyaltyReturnHand);
                boolean bl2 = bl = class_17992 == null || class_17992.method_7960();
                if (!this.loyaltyHandBecameEmpty) {
                    if (bl) {
                        this.loyaltyHandBecameEmpty = true;
                    }
                } else if (!bl && class_17992.method_7909() instanceof class_1835) {
                    AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                    if (TridentStateMachine.isThrowClip(animationClip)) {
                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    }
                    PunchyAnimationManager.clearSourceHand();
                    this.activeClip = null;
                    lastRecentActionPlayerTick = -1;
                    HandEquipStateMachine.triggerPickupEquipCycle(class_3102, this.loyaltyReturnHand);
                    this.clearLoyaltyReturnTracking();
                }
            }
        }
    }

    private void clearLoyaltyReturnTracking() {
        this.waitingLoyaltyReturn = false;
        this.loyaltyHandBecameEmpty = false;
        this.loyaltyThrowStartTick = -1;
        this.loyaltyThrowStartHotbarSlot = -1;
    }

    private boolean shouldCancelLoyaltyThrowOnEquipSwap(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && this.waitingLoyaltyReturn) {
            int n;
            boolean bl;
            class_1799 class_17992 = class_3102.field_1724.method_5998(this.loyaltyReturnHand);
            boolean bl2 = bl = class_17992 == null || class_17992.method_7960();
            if (!this.loyaltyHandBecameEmpty) {
                return false;
            }
            if (!bl && !(class_17992.method_7909() instanceof class_1835)) {
                return true;
            }
            return this.loyaltyReturnHand == class_1268.field_5808 && this.loyaltyThrowStartHotbarSlot >= 0 && (n = class_3102.field_1724.method_31548().field_7545) != this.loyaltyThrowStartHotbarSlot;
        }
        return false;
    }

    private void cancelLoyaltyThrowForEquipSwap() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (TridentStateMachine.isThrowClip(animationClip)) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
        }
        PunchyAnimationManager.clearSourceHand();
        this.activeClip = null;
        lastRecentActionPlayerTick = -1;
        this.clearLoyaltyReturnTracking();
    }

    private static boolean isThrowClip(AnimationClip animationClip) {
        return PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide");
    }

    public static boolean isRecentActionBlockingEquip(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            int n = lastRecentActionPlayerTick;
            if (n < 0) {
                return false;
            }
            int n2 = class_3102.field_1724.field_6012;
            return n2 >= n && n2 - n <= 12;
        }
        return false;
    }
}

