package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import ru.method.celebression.animation.AnimationClip;
import ru.method.celebression.animation.AnimationLibrary;
import ru.method.celebression.modules.impl.render.cosmetic.BoneTransformSet;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;

public final class CosmeticAnimationApplicator {
    private CosmeticAnimationApplicator() {
    }

    public static void a(BoneTransformSet boneTransformSet, CosmeticModel cosmeticModel, float f, float f2) {
        if (boneTransformSet == null || cosmeticModel == null) {
            return;
        }
        AnimationLibrary animationLibrary = cosmeticModel.br();
        if (animationLibrary == null || animationLibrary.isEmpty()) {
            return;
        }
        AnimationClip animationClip2 = animationLibrary.ad("idle");
        AnimationClip animationClip3 = animationLibrary.ad("moving");
        if (animationClip2 == null && animationClip3 == null) {
            return;
        }
        float f3 = Math.max(0.0f, Math.min(1.0f, f2));
        boolean moving = f3 >= 0.5f;
        AnimationClip selectedClip = moving
            ? (animationClip3 != null ? animationClip3 : animationClip2)
            : (animationClip2 != null ? animationClip2 : animationClip3);
        if (selectedClip != null) {
            selectedClip.a(boneTransformSet, f);
        }
    }
}
