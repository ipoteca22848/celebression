package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1041;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.TriggerableActionTracker;
import ru.method.celebression.punchy.client.state.TriggerableKeyResolver;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class TriggerableStateMachine {
    private static final Map<Integer, Boolean> LAST_KEY_STATE = new HashMap<Integer, Boolean>();

    public static void trigger(class_1268 class_12682) {
        TriggerableActionTracker.record(class_12682, -1);
    }

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            TriggerableActionTracker.Trigger trigger = TriggerableActionTracker.consumeTrigger();
            if (trigger == null) {
                trigger = this.pollKeyTrigger(class_3102);
            }
            if (trigger != null) {
                ClipSelection clipSelection;
                ItemAnimationManager.forceTypeOnce(trigger.hand(), "triggerable");
                if (trigger.hand() != null) {
                    class_1799 class_17992 = class_3102.field_1724.method_5998(trigger.hand());
                    clipSelection = this.resolveFromStack(class_3102, class_17992, trigger.hand(), "triggerable", trigger.keyCode());
                } else {
                    clipSelection = this.resolveCustomClip(class_3102, "triggerable", trigger.keyCode());
                }
                if (!(clipSelection.clip == null || HandEquipStateMachine.isEquipInProgress() && this.shouldAbortEquipForHand(class_3102, clipSelection.hand) && !HandEquipStateMachine.abortEquipForAction(class_3102, clipSelection.hand))) {
                    this.playClip(class_3102, clipSelection);
                }
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
            this.primeOppositeArmOverlapDuringEquip(class_3102, clipSelection.hand, class_13062);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            boolean bl = CustomAnimationResolver.shouldSkipBlendIn(clipSelection.entry);
            CustomAnimationResolver.playConfiguredClip(animationClip, clipSelection.entry, f, bl, false);
        }
    }

    private ClipSelection resolveCustomClip(class_310 class_3102, String string, int n) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            ClipSelection clipSelection = this.resolveFromStack(class_3102, class_17992, class_1268.field_5808, string, n);
            if (clipSelection.clip != null) {
                return clipSelection;
            }
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            return this.resolveFromStack(class_3102, class_17993, class_1268.field_5810, string, n);
        }
        return new ClipSelection(null, null, null);
    }

    private ClipSelection resolveFromStack(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, String string, int n) {
        if (class_17992 == null) {
            return new ClipSelection(null, null, null);
        }
        if (PunchyConfig.isItemBlacklisted(class_17992)) {
            return new ClipSelection(null, null, null);
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = this.resolveEntryForKey(CustomAnimationResolver.findEntries(class_17992, string), n);
        CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, string, customAnimationEntry, class_3102, class_12682);
        return clipLookup.clip() == null ? new ClipSelection(null, null, null) : new ClipSelection(clipLookup.clip(), class_12682, customAnimationEntry);
    }

    private PunchyTuningConfig.CustomAnimationEntry resolveEntryForKey(List<PunchyTuningConfig.CustomAnimationEntry> list, int n) {
        if (list != null && !list.isEmpty()) {
            if (n >= 0) {
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    if (customAnimationEntry == null || !TriggerableKeyResolver.matches(customAnimationEntry.key, n)) continue;
                    return customAnimationEntry;
                }
                return null;
            }
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry == null || customAnimationEntry.key != null && !customAnimationEntry.key.isBlank()) continue;
                return customAnimationEntry;
            }
            return list.get(0);
        }
        return null;
    }

    private TriggerableActionTracker.Trigger pollKeyTrigger(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1755 == null) {
            class_1041 class_10412 = class_3102.method_22683();
            if (class_10412 == null) {
                return null;
            }
            List<PunchyTuningConfig.CustomAnimationEntry> list = CustomAnimationResolver.findEntries(class_3102.field_1724.method_6047(), "triggerable");
            List<PunchyTuningConfig.CustomAnimationEntry> list2 = CustomAnimationResolver.findEntries(class_3102.field_1724.method_6079(), "triggerable");
            if (list.isEmpty() && list2.isEmpty()) {
                LAST_KEY_STATE.clear();
                return null;
            }
            ArrayList<KeyCandidate> arrayList = new ArrayList<KeyCandidate>();
            this.addCandidates(arrayList, list, class_1268.field_5808);
            this.addCandidates(arrayList, list2, class_1268.field_5810);
            if (arrayList.isEmpty()) {
                LAST_KEY_STATE.clear();
                return null;
            }
            HashSet<Integer> hashSet = new HashSet<Integer>();
            KeyCandidate keyCandidate = null;
            for (KeyCandidate keyCandidate2 : arrayList) {
                int n2 = keyCandidate2.keyCode;
                hashSet.add(n2);
                boolean bl = class_3675.method_15987((long)class_10412.method_4490(), (int)n2);
                boolean bl2 = LAST_KEY_STATE.getOrDefault(n2, false);
                if (bl && !bl2 && keyCandidate == null) {
                    keyCandidate = keyCandidate2;
                }
                LAST_KEY_STATE.put(n2, bl);
            }
            LAST_KEY_STATE.keySet().removeIf(n -> !hashSet.contains(n));
            return keyCandidate == null ? null : new TriggerableActionTracker.Trigger(keyCandidate.hand, keyCandidate.keyCode);
        }
        return null;
    }

    private void addCandidates(List<KeyCandidate> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, class_1268 class_12682) {
        if (list2 != null && !list2.isEmpty()) {
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list2) {
                int n;
                if (customAnimationEntry == null || (n = TriggerableKeyResolver.resolveKeyCode(customAnimationEntry.key)) < 0) continue;
                list.add(new KeyCandidate(class_12682, n));
            }
        }
    }

    private boolean shouldAbortEquipForHand(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_12682 != null) {
            class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            if (class_13062 == null) {
                return false;
            }
            class_1306 class_13063 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            return class_13063 == null || class_13063 == class_13062;
        }
        return false;
    }

    private void primeOppositeArmOverlapDuringEquip(class_310 class_3102, class_1268 class_12682, class_1306 class_13062) {
        if (class_3102 != null && class_12682 != null && class_13062 != null && HandEquipStateMachine.isEquipInProgress()) {
            if (class_12682 == class_1268.field_5808) {
                HandEquipStateMachine.prepareOffhandForMainhandAction(class_3102);
            }
            if (PunchyAnimationManager.POSE_HANDLER.getActiveArm() == null) {
                class_1306 class_13063 = class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13063);
            }
        }
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

    static final class KeyCandidate {
        final class_1268 hand;
        final int keyCode;

        KeyCandidate(class_1268 class_12682, int n) {
            this.hand = class_12682;
            this.keyCode = n;
        }

        @Override
        public final String toString() {
            return "KeyCandidate[" + "hand=" + this.hand + ", " + "keyCode=" + this.keyCode + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.hand, this.keyCode);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof KeyCandidate other)) return false;
            return java.util.Objects.deepEquals(this.hand, other.hand)
                && java.util.Objects.deepEquals(this.keyCode, other.keyCode);
        }

        public class_1268 hand() {
            return this.hand;
        }

        public int keyCode() {
            return this.keyCode;
        }
    }
}

