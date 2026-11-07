package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1753;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class BowStateMachine {
    private boolean wasUsing;
    private AnimationClip activeClip;
    private class_1268 lastUsedHand;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl = class_17992.method_7909() instanceof class_1753;
            boolean bl2 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            boolean bl3 = class_3102.field_1724.method_6115() && bl && bl2;
            class_1268 class_12682 = class_3102.field_1724.method_6058();
            if (bl3 && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                if (this.wasUsing) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                }
                this.wasUsing = false;
            } else if (!bl3 || !HandEquipStateMachine.isEquipInProgress() || HandEquipStateMachine.abortEquipForAction(class_3102, class_12682)) {
                if (bl3) {
                    ItemAnimationManager.setPersistentType(class_12682, "useItem", true, true);
                    if (this.wasUsing && this.lastUsedHand != null && this.lastUsedHand != class_12682) {
                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                        this.activeClip = null;
                        PunchyAnimationManager.clearSourceHand();
                        HandEquipStateMachine.requestRenderedSync();
                    }
                    if (!this.wasUsing || this.lastUsedHand != class_12682 || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.activeClip == null) {
                        this.playBow(class_3102, class_12682, class_17992);
                    }
                } else if (this.wasUsing) {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                    this.activeClip = null;
                    PunchyAnimationManager.clearSourceHand();
                    this.lastUsedHand = null;
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "useItem");
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "useItem");
                }
                this.wasUsing = bl3;
                this.lastUsedHand = bl3 ? class_12682 : null;
            }
        }
    }

    private void playBow(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        AnimationClip animationClip = this.resolveBowClip(class_3102, class_12682, class_17992);
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
            boolean bl3 = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl3, bl2);
            this.activeClip = animationClip;
        }
    }

    private AnimationClip resolveBowClip(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "use_bow");
        CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "use_bow", customAnimationEntry, class_3102, class_12682);
        if (clipLookup.clip() != null) {
            return clipLookup.clip();
        }
        String string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
        return PunchyAnimationManager.resolveHandedClip("use_bow", class_3102, class_12682, string);
    }
}

