package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Locale;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class IdlePoseStateMachine {
    private static final float IDLE_RETURN_BLEND_SECONDS = 0.27f;
    private String activeIdleClipName = null;
    private class_1306 activeIdleArm = null;

    public void tick(class_310 class_3102) {
        PoseHandler poseHandler = PunchyAnimationManager.POSE_HANDLER;
        if (class_3102 != null && class_3102.field_1724 != null) {
            String string = this.resolveBlockReason(poseHandler);
            if (string != null) {
                this.clearActiveIdle(poseHandler);
            } else {
                IdleSelection idleSelection = this.resolveDesiredIdle(class_3102, poseHandler);
                if (idleSelection != null && idleSelection.clip() != null && idleSelection.arm() != null) {
                    String string2;
                    if (this.isForeignSecondaryActive(poseHandler, idleSelection.arm())) {
                        if (!this.shouldIgnoreForeignSecondary(poseHandler)) {
                            this.activeIdleClipName = null;
                            this.activeIdleArm = null;
                            return;
                        }
                        poseHandler.clearSecondaryClip();
                    }
                    if (!this.isDesiredIdleAlreadyApplied(poseHandler, idleSelection, string2 = this.clipName(idleSelection.clip()))) {
                        float f = PunchyAnimationManager.clipSpeedMultiplier(idleSelection.clip());
                        poseHandler.setSecondaryClipAtTimeWithBlendFromCurrentAndFlags(idleSelection.clip(), idleSelection.arm(), f, 0.0f, true, 0.27f, idleSelection.flags());
                        poseHandler.setSecondaryCompatIdle(true);
                        this.activeIdleClipName = string2;
                        this.activeIdleArm = idleSelection.arm();
                    }
                } else {
                    this.clearActiveIdle(poseHandler);
                }
            }
        } else {
            this.clearActiveIdle(poseHandler);
        }
    }

    private IdleSelection resolveDesiredIdle(class_310 class_3102, PoseHandler poseHandler) {
        class_1306 class_13062 = class_3102.field_1724.method_6068();
        return this.resolveIdleForArm(class_3102, class_13062);
    }

    private IdleSelection resolveIdleForArm(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13062);
            if (class_12682 != null && !PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12682)) {
                class_1799 class_17992 = this.resolveStack(class_3102, class_13062);
                if (class_17992 != null && !class_17992.method_7960()) {
                    CustomAnimationResolver.ClipLookup clipLookup;
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "idle", false);
                    if (customAnimationEntry == null) {
                        customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "idle");
                    }
                    return (clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "idle", customAnimationEntry, class_3102, class_12682)) != null && clipLookup.clip() != null ? new IdleSelection(clipLookup.clip(), class_13062, customAnimationEntry != null ? customAnimationEntry.customFlags : null) : null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private void clearActiveIdle(PoseHandler poseHandler) {
        if (poseHandler == null) {
            this.activeIdleClipName = null;
            this.activeIdleArm = null;
        } else {
            if (poseHandler.isSecondaryCompatIdle()) {
                poseHandler.clearSecondaryClipWithBlendOut();
                poseHandler.setSecondaryCompatIdle(false);
            }
            this.activeIdleClipName = null;
            this.activeIdleArm = null;
        }
    }

    private boolean isForeignSecondaryActive(PoseHandler poseHandler, class_1306 class_13062) {
        if (poseHandler == null || class_13062 == null) {
            return false;
        }
        return poseHandler.isSecondaryCompatIdle() ? false : poseHandler.getSecondaryClip() != null && poseHandler.getSecondaryArm() != null && poseHandler.getSecondaryArm() != class_13062;
    }

    private boolean shouldIgnoreForeignSecondary(PoseHandler poseHandler) {
        if (poseHandler == null) {
            return false;
        }
        AnimationClip animationClip = poseHandler.getSecondaryClip();
        if (animationClip == null) {
            return false;
        }
        return this.isEquipClip(animationClip) ? false : poseHandler.getCurrentClip() == null && !poseHandler.isPlaying() && !poseHandler.isBlendOutActive();
    }

    private boolean isDesiredIdleAlreadyApplied(PoseHandler poseHandler, IdleSelection idleSelection, String string) {
        if (poseHandler != null && idleSelection != null && string != null) {
            if (!string.equals(this.activeIdleClipName) || idleSelection.arm() != this.activeIdleArm) {
                return false;
            }
            if (!poseHandler.isSecondaryCompatIdle()) {
                return false;
            }
            AnimationClip animationClip = poseHandler.getSecondaryClip();
            return animationClip != null && string.equals(this.clipName(animationClip)) && poseHandler.getSecondaryArm() == idleSelection.arm();
        }
        return false;
    }

    private class_1799 resolveStack(class_310 class_3102, class_1306 class_13062) {
        boolean bl;
        boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
        return class_13062 == class_1306.field_6183 ? (bl ? class_3102.field_1724.method_6047() : class_3102.field_1724.method_6079()) : (bl ? class_3102.field_1724.method_6079() : class_3102.field_1724.method_6047());
    }

    private String resolveBlockReason(PoseHandler poseHandler) {
        if (poseHandler == null) {
            return "no_handler";
        }
        AnimationClip animationClip = poseHandler.getCurrentClip();
        if (animationClip != null && !this.isEquipClip(animationClip) && poseHandler.isPlaying()) {
            return "pose_playing";
        }
        boolean bl = ItemAnimationManager.isTypeActive("walk_on_water_v2");
        boolean bl2 = poseHandler.isSecondaryCompatIdle();
        if (PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            return bl && bl2 ? null : "swim_playing";
        }
        if (PunchyAnimationManager.SWIM_HANDLER.isBlendOutActive()) {
            return bl && bl2 ? null : "swim_blend_out";
        }
        if (PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() != null) {
            return bl && bl2 ? null : "swim_clip_lingering";
        }
        if (bl) {
            return "item_walk_on_water";
        }
        if (ItemAnimationManager.isTypeActive("mining")) {
            return "item_mining";
        }
        return ItemAnimationManager.isTypeActive("useItem") ? "item_use" : null;
    }

    private boolean isEquipClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        if (string == null) {
            return false;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        return string2.equals("hand_in") || string2.equals("hand_out") || string2.startsWith("hand_in_") || string2.startsWith("hand_out_");
    }

    private String clipName(AnimationClip animationClip) {
        return animationClip == null ? "null" : animationClip.getName();
    }

    record IdleSelection(AnimationClip clip, class_1306 arm, PunchyTuningConfig.CustomFlags flags) {
    }
}

