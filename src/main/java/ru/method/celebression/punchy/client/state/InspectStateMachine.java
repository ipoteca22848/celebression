package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.InspectActionTracker;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class InspectStateMachine {
    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && !HandEquipStateMachine.isEquipInProgress() && InspectActionTracker.consume()) {
            ItemAnimationManager.forceTypeOnce(class_1268.field_5808, "inspect");
            ClipSelection clipSelection = this.resolveCustomClip(class_3102, "inspect");
            if (clipSelection.clip != null) {
                this.playClip(class_3102, clipSelection);
            }
        }
    }

    private void playClip(class_310 class_3102, ClipSelection clipSelection) {
        AnimationClip animationClip = clipSelection.clip;
        if (animationClip != null) {
            class_1306 class_13062;
            CustomAnimationResolver.applyLoopSpeed(clipSelection.entry);
            class_1306 class_13063 = class_13062 = clipSelection.hand == null ? null : PunchyAnimationManager.getUseArm(class_3102, clipSelection.hand);
            if (PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f)) {
                class_13062 = null;
            }
            PunchyAnimationManager.setSourceHand(class_3102, clipSelection.hand == null ? class_1268.field_5808 : clipSelection.hand);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, false);
        }
    }

    private ClipSelection resolveCustomClip(class_310 class_3102, String string) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            ClipSelection clipSelection = this.resolveFromStack(class_3102, class_17992, class_1268.field_5808, string);
            if (clipSelection.clip != null) {
                return clipSelection;
            }
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            return this.resolveFromStack(class_3102, class_17993, class_1268.field_5810, string);
        }
        return new ClipSelection(null, null, null);
    }

    private ClipSelection resolveFromStack(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, String string) {
        if (class_17992 == null) {
            return new ClipSelection(null, null, null);
        }
        if (PunchyConfig.isItemBlacklisted(class_17992)) {
            return new ClipSelection(null, null, null);
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, string);
        CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, string, customAnimationEntry, class_3102, class_12682);
        return clipLookup.clip() == null ? new ClipSelection(null, null, null) : new ClipSelection(clipLookup.clip(), class_12682, customAnimationEntry);
    }

    static final class ClipSelection {
        final AnimationClip clip;
        final class_1268 hand;
        final PunchyTuningConfig.CustomAnimationEntry entry;

        ClipSelection(AnimationClip animationClip, class_1268 class_12682, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
            this.clip = animationClip;
            this.hand = class_12682;
            this.entry = customAnimationEntry;
        }

        @Override
        public final String toString() {
            return "ClipSelection[" + "clip=" + this.clip + ", " + "hand=" + this.hand + ", " + "entry=" + this.entry + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.clip, this.hand, this.entry);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ClipSelection other)) return false;
            return java.util.Objects.deepEquals(this.clip, other.clip)
                && java.util.Objects.deepEquals(this.hand, other.hand)
                && java.util.Objects.deepEquals(this.entry, other.entry);
        }

        public AnimationClip clip() {
            return this.clip;
        }

        public class_1268 hand() {
            return this.hand;
        }

        public PunchyTuningConfig.CustomAnimationEntry entry() {
            return this.entry;
        }
    }
}

