package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_310;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;

public class FireStateMachine {
    private static final float FIRE_ANIM_SECONDS = 1.7713f;
    private static final int FIRE_ANIM_TICKS = Math.round(35.426f);
    private boolean wasOnFire;
    private boolean playedThisFire;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && !HandEquipStateMachine.isEquipInProgress()) {
            class_746 class_7462 = class_3102.field_1724;
            boolean bl = class_7462.method_5809();
            if (bl) {
                if (!this.wasOnFire) {
                    this.playedThisFire = false;
                }
            } else if (this.wasOnFire && !this.playedThisFire) {
                this.triggerPutOut();
                this.playedThisFire = true;
            }
            this.wasOnFire = bl;
        }
    }

    private void triggerPutOut() {
        AnimationClip animationClip = PunchyAnimationManager.PUT_OUT_FIRE_CLIP;
        if (!(animationClip == null || PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip && PunchyAnimationManager.POSE_HANDLER.isPlaying())) {
            float f = 1.0f / Math.max(0.01f, PunchyAnimationManager.ANIMATION_SPEED);
            class_310 class_3102 = class_310.method_1551();
            PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(PunchyAnimationManager.getUseArm(class_3102, class_1268.field_5808));
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl, false);
        }
    }
}

