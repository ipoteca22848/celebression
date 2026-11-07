package ru.method.celebression.punchy.compat;

import ru.method.celebression.animation.Animation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.minecraft.class_1268;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.PunchyArmRenderer;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.platform.Services;

public final class FirstPersonModelCompat {
    private static final String MOD_ID = "firstperson";
    private static final String FPM_API_CLASS = "dev.tr7zw.firstperson.api.FirstPersonAPI";
    private static final String FPM_ACTIVATION_HANDLER = "dev.tr7zw.firstperson.api.ActivationHandler";
    private static boolean available;
    private static boolean checked;
    private static boolean active;
    private static boolean hidden;
    private static int transitionTicks;
    private static boolean pendingHide;
    private static boolean pendingShow;
    private static State state;
    private static boolean fpmLookupDone;
    private static Method fpmIsRenderingPlayer;
    private static boolean activationRegistered;
    private static boolean bodyRenderActive;

    private FirstPersonModelCompat() {
    }

    public static boolean isAvailable() {
        if (!checked) {
            checked = true;
            available = Services.PLATFORM != null && Services.PLATFORM.isModLoaded("firstperson");
        }
        return available;
    }

    public static void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            boolean bl2 = bl = FirstPersonModelCompat.isAvailable() && class_3102.field_1690.method_31044().method_31034();
            if (!bl) {
                FirstPersonModelCompat.deactivate();
            } else {
                FirstPersonModelCompat.ensureActivationHandlerRegistered();
                active = true;
                if (!PunchyConfig.isFirstPersonModelHideEnabled()) {
                    pendingHide = false;
                    pendingShow = false;
                    hidden = false;
                    transitionTicks = 0;
                    state = State.VISIBLE;
                    bodyRenderActive = false;
                    PunchyArmRenderer.setFirstPersonHidden(false);
                } else if (PunchyConfig.isFirstPersonModelHideNever()) {
                    pendingHide = false;
                    pendingShow = false;
                    hidden = false;
                    transitionTicks = 0;
                    state = State.VISIBLE;
                    bodyRenderActive = true;
                    PunchyArmRenderer.setFirstPersonHidden(false);
                } else {
                    float f;
                    boolean bl3;
                    float f2 = class_3102.field_1724.method_36455();
                    boolean bl4 = bl3 = f2 > (f = PunchyConfig.getFirstPersonModelHidePitch());
                    if (bl3) {
                        pendingHide = true;
                        pendingShow = false;
                        if ((state == State.VISIBLE || state == State.SHOWING) && FirstPersonModelCompat.beginHide(class_3102)) {
                            pendingHide = false;
                        }
                    } else {
                        pendingShow = true;
                        pendingHide = false;
                        if ((state == State.HIDDEN || state == State.HIDING) && FirstPersonModelCompat.beginShow(class_3102)) {
                            pendingShow = false;
                        }
                    }
                    FirstPersonModelCompat.advanceTransitions();
                    PunchyArmRenderer.setFirstPersonHidden(hidden);
                }
            }
        } else {
            FirstPersonModelCompat.deactivate();
        }
    }

    public static boolean shouldHideHands() {
        return hidden && active;
    }

    public static boolean shouldApplyThirdPersonAnimations(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1690 == null) {
            return false;
        }
        return FirstPersonModelCompat.isAvailable() && class_3102.field_1690.method_31044().method_31034() ? state == State.HIDDEN || state == State.HIDING : false;
    }

    public static boolean isRenderingPlayer() {
        Object object;
        if (!FirstPersonModelCompat.isAvailable()) {
            return false;
        }
        if (!fpmLookupDone) {
            fpmLookupDone = true;
            try {
                object = Class.forName("dev.tr7zw.firstperson.api.FirstPersonAPI");
                fpmIsRenderingPlayer = ((Class)object).getMethod("isRenderingPlayer", new Class[0]);
            }
            catch (Throwable throwable) {
                fpmIsRenderingPlayer = null;
            }
        }
        if (fpmIsRenderingPlayer == null) {
            return false;
        }
        try {
            object = fpmIsRenderingPlayer.invoke(null, new Object[0]);
            return object instanceof Boolean && (Boolean)object != false;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static void ensureActivationHandlerRegistered() {
        if (!activationRegistered && FirstPersonModelCompat.isAvailable()) {
            try {
                Class<?> clazz = Class.forName("dev.tr7zw.firstperson.api.FirstPersonAPI");
                Class<?> clazz2 = Class.forName("dev.tr7zw.firstperson.api.ActivationHandler");
                Object object2 = Proxy.newProxyInstance(FirstPersonModelCompat.class.getClassLoader(), new Class[]{clazz2}, (object, method, objectArray) -> "preventFirstperson".equals(method.getName()) ? Boolean.valueOf(!FirstPersonModelCompat.shouldAllowBodyRender(class_310.method_1551())) : null);
                clazz.getMethod("registerPlayerHandler", Object.class).invoke(null, object2);
                activationRegistered = true;
            }
            catch (Throwable throwable) {
                activationRegistered = false;
            }
        }
    }

    private static boolean shouldAllowBodyRender(class_310 class_3102) {
        float f;
        boolean bl;
        if (class_3102 == null || class_3102.field_1724 == null) {
            return false;
        }
        if (!PunchyConfig.isFirstPersonModelHideEnabled()) {
            bodyRenderActive = false;
            return false;
        }
        if (PunchyConfig.isFirstPersonModelHideNever()) {
            bodyRenderActive = true;
            return true;
        }
        float f2 = class_3102.field_1724.method_36455();
        bodyRenderActive = bl = f2 > (f = PunchyConfig.getFirstPersonModelHidePitch());
        return bl;
    }

    private static void deactivate() {
        if (active && state != State.VISIBLE) {
            ItemAnimationManager.forceTypeOnce(class_1268.field_5808, "hand_in");
            ItemAnimationManager.forceTypeOnce(class_1268.field_5810, "hand_in");
        }
        active = false;
        hidden = false;
        transitionTicks = 0;
        pendingHide = false;
        pendingShow = false;
        bodyRenderActive = false;
        state = State.VISIBLE;
        PunchyArmRenderer.setFirstPersonHidden(false);
    }

    private static boolean beginHide(class_310 class_3102) {
        hidden = true;
        state = State.HIDDEN;
        transitionTicks = 0;
        return true;
    }

    private static boolean beginShow(class_310 class_3102) {
        if (!FirstPersonModelCompat.canPlayEquipClip()) {
            hidden = false;
            state = State.VISIBLE;
            transitionTicks = 0;
            return true;
        }
        ItemAnimationManager.forceTypeOnce(class_1268.field_5808, "hand_in");
        ItemAnimationManager.forceTypeOnce(class_1268.field_5810, "hand_in");
        FirstPersonModelCompat.playEquipClip(class_3102, "hand_in");
        state = State.SHOWING;
        transitionTicks = 0;
        hidden = false;
        return true;
    }

    private static void advanceTransitions() {
        class_310 class_3102;
        if (state == State.SHOWING) {
            ++transitionTicks;
            if (FirstPersonModelCompat.isPoseClipPlaying("hand_in")) {
                return;
            }
            if (transitionTicks <= 2) {
                return;
            }
            if (transitionTicks > 30 || !FirstPersonModelCompat.isPoseClipPlaying("hand_in")) {
                hidden = false;
                state = State.VISIBLE;
                transitionTicks = 0;
            }
        }
        if (state == State.VISIBLE && pendingHide) {
            class_310 class_3103 = class_310.method_1551();
            if (FirstPersonModelCompat.beginHide(class_3103)) {
                pendingHide = false;
            }
        } else if (state == State.HIDDEN && pendingShow && FirstPersonModelCompat.beginShow(class_3102 = class_310.method_1551())) {
            pendingShow = false;
        }
    }

    private static boolean canPlayEquipClip() {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && poseHandler.isPlaying()) {
            AnimationClip animationClip = poseHandler.getCurrentClip();
            return animationClip == null ? true : PunchyAnimationManager.isClipNamed(animationClip, "hand_in") || PunchyAnimationManager.isClipNamed(animationClip, "hand_out");
        }
        return true;
    }

    private static void playEquipClip(class_310 class_3102, String string) {
        if (class_3102 != null && string != null && !string.isBlank()) {
            AnimationClip animationClip = PunchyAnimationManager.resolveHandedClip(string, class_3102, class_1268.field_5808);
            if (animationClip == null) {
                AnimationClip animationClip2 = animationClip = "hand_in".equals(string) ? PunchyAnimationManager.HAND_IN_CLIP : PunchyAnimationManager.HAND_OUT_CLIP;
            }
            if (animationClip != null) {
                PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
                poseHandler.setActiveArm(null);
                poseHandler.setLooping(false);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                poseHandler.play(animationClip, f, true, false);
            }
        }
    }

    private static boolean isPoseClipPlaying(String string) {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (poseHandler != null && poseHandler.isPlaying()) {
            AnimationClip animationClip = poseHandler.getCurrentClip();
            return PunchyAnimationManager.isClipNamed(animationClip, string);
        }
        return false;
    }

    static {
        state = State.VISIBLE;
    }

    static enum State {
        VISIBLE,
        HIDING,
        HIDDEN,
        SHOWING;

    }
}

