package ru.method.celebression.punchy.client.animation;

import ru.method.celebression.animation.Animation;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1306;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.io.PunchyThirdPersonAnimationLoader;

public class PunchyThirdPersonAnimationManager {
    public static final PoseHandler POSE_HANDLER = new PoseHandler();
    private static final Map<String, AnimationClip> ALL_CLIPS = new HashMap<String, AnimationClip>();
    private static String lastSourceClip;
    private static String manualClipName;

    public static void loadAnimations(class_3300 class_33002) {
        ALL_CLIPS.clear();
        POSE_HANDLER.stopImmediateWithBlendOut();
        ALL_CLIPS.putAll(PunchyThirdPersonAnimationLoader.loadAll(class_33002));
    }

    public static AnimationClip resolveClip(String string) {
        return string != null && !string.isBlank() ? ALL_CLIPS.get(string.trim()) : null;
    }

    public static void tick(class_310 class_3102) {
        if (class_3102 != null) {
            if (!PunchyThirdPersonAnimationManager.isManualPlaying()) {
                PunchyThirdPersonAnimationManager.syncWithFirstPerson(class_3102);
            }
            POSE_HANDLER.tick(0.05f, PunchyAnimationManager.ANIMATION_SPEED);
            if (manualClipName != null && !PunchyThirdPersonAnimationManager.isManualPlaying()) {
                manualClipName = null;
            }
        }
    }

    private static void syncWithFirstPerson(class_310 class_3102) {
        if (class_3102 != null) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            boolean bl = PunchyAnimationManager.POSE_HANDLER.isPlaying();
            if (bl && animationClip != null) {
                String string = animationClip.getName();
                AnimationClip animationClip2 = PunchyThirdPersonAnimationManager.resolveClip(string);
                if (animationClip2 == null) {
                    if (POSE_HANDLER.isPlaying()) {
                        POSE_HANDLER.stopImmediateWithBlendOut();
                    }
                    lastSourceClip = null;
                } else if (lastSourceClip == null || !lastSourceClip.equals(string) || POSE_HANDLER.getCurrentClip() != animationClip2 || !POSE_HANDLER.isPlaying()) {
                    class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
                    POSE_HANDLER.setActiveArm(class_13062);
                    POSE_HANDLER.setLooping(PunchyAnimationManager.POSE_HANDLER.isForceLooping());
                    float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                    boolean bl2 = animationClip2.isHoldOnLastFrame();
                    POSE_HANDLER.play(animationClip2, f, true, bl2);
                    lastSourceClip = string;
                }
            } else {
                if (POSE_HANDLER.isPlaying()) {
                    POSE_HANDLER.stopImmediateWithBlendOut();
                }
                lastSourceClip = null;
            }
        }
    }

    public static void playManual(String string) {
        AnimationClip animationClip = PunchyThirdPersonAnimationManager.resolveClip(string);
        if (animationClip != null) {
            manualClipName = animationClip.getName();
            POSE_HANDLER.setActiveArm(null);
            POSE_HANDLER.setLooping(animationClip.isLoop());
            POSE_HANDLER.play(animationClip, 1.0f, true, animationClip.isHoldOnLastFrame());
        }
    }

    private static boolean isManualPlaying() {
        if (manualClipName == null) {
            return false;
        }
        AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
        return POSE_HANDLER.isPlaying() && animationClip != null && manualClipName.equals(animationClip.getName());
    }
}

