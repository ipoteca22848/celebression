package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class CrossbowStateMachine {
    private boolean wasUsing;
    private AnimationClip activeClip;
    private boolean lastChargedMain;
    private boolean lastChargedOff;
    private boolean lastUseDown;
    private boolean suppressChargeLoop;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && !HandEquipStateMachine.isEquipInProgress()) {
            int n;
            int n2;
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl = class_17992.method_7909() instanceof class_1764;
            boolean bl2 = bl && class_1764.method_7781((class_1799)class_17992);
            boolean bl3 = false;
            if (bl && class_3102.field_1724.method_6115() && (n2 = class_1764.method_7775((class_1799)class_17992, (class_1309)class_3102.field_1724)) > 0 && (n = class_17992.method_7935((class_1309)class_3102.field_1724) - class_3102.field_1724.method_6014()) >= n2) {
                bl2 = true;
                bl3 = true;
                this.suppressChargeLoop = true;
            }
            n2 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434() ? 1 : 0;
            n = class_3102.field_1724.method_6115() && bl && n2 != 0 && !bl2 ? 1 : 0;
            class_1268 class_12682 = class_3102.field_1724.method_6058();
            if (n != 0 && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                if (this.wasUsing && !bl3) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                }
                this.suppressChargeLoop = false;
                this.wasUsing = false;
                this.lastUseDown = n2 != 0;
            } else if (n == 0 || !HandEquipStateMachine.isEquipInProgress() || HandEquipStateMachine.abortEquipForAction(class_3102, class_12682)) {
                boolean bl4;
                if (n2 == 0 || !bl) {
                    this.suppressChargeLoop = false;
                }
                boolean bl5 = bl4 = n2 != 0 && !this.lastUseDown;
                if (this.suppressChargeLoop) {
                    n = 0;
                }
                if (n != 0) {
                    ItemAnimationManager.setPersistentType(class_3102.field_1724.method_6058(), "useItem", true, true);
                    if (!this.wasUsing || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.activeClip == null) {
                        this.playCrossbow(class_3102, class_3102.field_1724.method_6058());
                    }
                } else if (this.wasUsing && !bl3) {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                }
                if (bl4) {
                    this.handleImmediateShot(class_3102);
                }
                this.handleShot(class_3102, n2 != 0 || this.lastUseDown);
                this.wasUsing = n != 0 && !bl3;
                this.lastUseDown = n2 != 0;
            }
        }
    }

    private void playCrossbow(class_310 class_3102, class_1268 class_12682) {
        AnimationClip animationClip = this.resolveCrossbowClip(class_3102, class_12682);
        if (animationClip != null) {
            int n;
            class_1799 class_17992;
            boolean bl;
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            boolean bl2 = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl2) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            }
            boolean bl3 = animationClip.isHoldOnLastFrame();
            boolean bl4 = bl = !bl3;
            if (class_3102.field_1724 != null && (class_17992 = class_3102.field_1724.method_6030()).method_7909() instanceof class_1764 && (n = class_1764.method_7775((class_1799)class_17992, (class_1309)class_3102.field_1724)) > 0) {
                float f = (float)n / 20.0f;
                bl = animationClip.getLength() < f;
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(bl);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            boolean skipBlendIn = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, skipBlendIn, bl3);
            this.activeClip = animationClip;
        }
    }

    private AnimationClip resolveCrossbowClip(class_310 class_3102, class_1268 class_12682) {
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry;
        class_1799 class_17992;
        CustomAnimationResolver.ClipLookup clipLookup;
        if (class_3102 != null && class_3102.field_1724 != null && (clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992 = class_3102.field_1724.method_5998(class_12682), "use_crossbow", customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_crossbow"), class_3102, class_12682)).clip() != null) {
            return clipLookup.clip();
        }
        return PunchyAnimationManager.resolveHandedClip("use_crossbow", class_3102, class_12682);
    }

    private void handleShot(class_310 class_3102, boolean bl) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl2;
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            boolean bl3 = class_17992.method_7909() instanceof class_1764 && class_1764.method_7781((class_1799)class_17992);
            boolean bl4 = bl2 = class_17993.method_7909() instanceof class_1764 && class_1764.method_7781((class_1799)class_17993);
            if (bl && this.lastChargedMain && !bl3) {
                this.playShot(class_3102, class_1268.field_5808);
            }
            if (bl && this.lastChargedOff && !bl2) {
                this.playShot(class_3102, class_1268.field_5810);
            }
            this.lastChargedMain = bl3;
            this.lastChargedOff = bl2;
        }
    }

    private void handleImmediateShot(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            boolean bl2 = class_17992.method_7909() instanceof class_1764 && class_1764.method_7781((class_1799)class_17992);
            boolean bl3 = bl = class_17993.method_7909() instanceof class_1764 && class_1764.method_7781((class_1799)class_17993);
            if (bl2) {
                this.playShot(class_3102, class_1268.field_5808);
                this.lastChargedMain = false;
            } else if (bl) {
                this.playShot(class_3102, class_1268.field_5810);
                this.lastChargedOff = false;
            }
        }
    }

    private void playShot(class_310 class_3102, class_1268 class_12682) {
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry;
        AnimationClip animationClip = null;
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_5998(class_12682);
            customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_crossbow_shot");
            CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "use_crossbow_shot", customAnimationEntry, class_3102, class_12682);
            animationClip = clipLookup.clip();
        }
        if (animationClip == null) {
            animationClip = PunchyAnimationManager.resolveHandedClip("use_crossbow_shot", class_3102, class_12682);
        }
        if (!(animationClip == null || PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip && PunchyAnimationManager.POSE_HANDLER.isPlaying())) {
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            boolean bl = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                class_1306 useArm = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(useArm);
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, true, false);
        }
    }
}

