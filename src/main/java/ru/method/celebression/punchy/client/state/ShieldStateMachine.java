package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class ShieldStateMachine {
    private boolean wasUsing;
    private AnimationClip activeClip;
    private class_1268 lastShieldHand;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl2 = class_17992.method_7909() instanceof class_1819;
            class_1268 class_12682 = class_3102.field_1724.method_6115() ? class_3102.field_1724.method_6058() : this.lastShieldHand;
            boolean bl3 = false;
            boolean bl4 = bl = class_3102.field_1724.method_6115() && bl2;
            if (bl) {
                this.lastShieldHand = class_12682;
            } else if (this.wasUsing && this.shouldKeepShieldVisualHold(class_3102)) {
                class_12682 = this.lastShieldHand;
                bl = true;
                bl3 = true;
            }
            if (bl && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                if (this.wasUsing) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    PunchyAnimationManager.clearSourceHand();
                    this.activeClip = null;
                }
                this.wasUsing = false;
                this.lastShieldHand = null;
            } else if (!bl || bl3 || !HandEquipStateMachine.isEquipInProgress() || HandEquipStateMachine.isOppositeShieldEquipInProgress(class_12682) || HandEquipStateMachine.abortEquipForAction(class_3102, class_12682)) {
                if (bl) {
                    if (!this.wasUsing || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.activeClip == null) {
                        this.playShield(class_3102, class_12682);
                    }
                } else if (this.wasUsing) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    PunchyAnimationManager.clearSourceHand();
                    this.activeClip = null;
                    this.lastShieldHand = null;
                }
                this.wasUsing = bl;
            }
        }
    }

    private boolean shouldKeepShieldVisualHold(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1690 == null || this.lastShieldHand == null) {
            return false;
        }
        if (!class_3102.field_1690.field_1904.method_1434()) {
            return false;
        }
        class_1799 class_17992 = class_3102.field_1724.method_5998(this.lastShieldHand);
        return class_17992 != null && class_17992.method_7909() instanceof class_1819;
    }

    private void playShield(class_310 class_3102, class_1268 class_12682) {
        AnimationClip animationClip = this.resolveShieldClip(class_3102, class_12682);
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
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, true, bl2);
            this.activeClip = animationClip;
        }
    }

    private AnimationClip resolveShieldClip(class_310 class_3102, class_1268 class_12682) {
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry;
        class_1799 class_17992;
        CustomAnimationResolver.ClipLookup clipLookup;
        if (class_3102 != null && class_3102.field_1724 != null && (clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992 = class_3102.field_1724.method_5998(class_12682), "use_shield", customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_shield"), class_3102, class_12682)).clip() != null) {
            return clipLookup.clip();
        }
        return PunchyAnimationManager.resolveHandedClip("use_shield", class_3102, class_12682);
    }
}

