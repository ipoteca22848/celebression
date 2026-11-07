package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;

public class WakeUpStateMachine {
    private boolean wasSleeping;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl = class_3102.field_1724.method_6113();
            if (bl) {
                this.wasSleeping = true;
            } else if (this.wasSleeping) {
                this.wasSleeping = false;
                this.triggerWakeUp();
            }
        } else {
            this.wasSleeping = false;
        }
    }

    private void triggerWakeUp() {
        AnimationClip animationClip = PunchyAnimationManager.WAKE_UP_CLIP;
        if (animationClip != null) {
            AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (!PunchyAnimationManager.POSE_HANDLER.isPlaying() || animationClip2 == null || PunchyAnimationManager.isClipNamed(animationClip2, "wake_up")) {
                class_310 class_3102 = class_310.method_1551();
                PunchyAnimationManager.setSourceHand(class_3102, class_1268.field_5808);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(PunchyAnimationManager.getUseArm(class_3102, class_1268.field_5808));
                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
                PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl, false);
            }
        }
    }
}

