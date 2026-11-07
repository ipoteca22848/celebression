package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.List;
import java.util.Locale;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class FallingStateMachine {
    private static final float MIN_FALL_DISTANCE_BLOCKS = 0.5f;
    private static final float FALLING_ENTRY_EPSILON = 1.0E-4f;
    private static final double FALLING_GROUND_DISTANCE_TRIGGER = 2.0;
    private static final double GROUND_SCAN_DEPTH = 12.0;
    private static final double EARLY_TRIGGER_FALL_VELOCITY = -0.04;
    private static final float FALLING_SPEED_START_BOOST = 15.0f;
    private static final float FALLING_SPEED_END_BOOST = 8.0f;
    private static final long FALLING_SPEED_RAMP_NANOS = 1000000000L;
    private static String activeType;
    private AnimationClip activeClip;
    private PunchyTuningConfig.CustomAnimationEntry activeEntry;
    private long fallingStartNanos = -1L;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            if (HandEquipStateMachine.isEquipInProgress()) {
                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "falling");
                ItemAnimationManager.clearPersistentType(class_1268.field_5810, "falling");
            } else {
                boolean bl;
                boolean bl2 = class_3102.field_1724.method_24828();
                boolean bl3 = class_3102.field_1724.method_5799() || class_3102.field_1724.method_5869();
                class_746 class_7462 = class_3102.field_1724;
                double d = class_7462.method_18798().field_1351;
                boolean bl4 = d < 0.0 || (double)class_7462.field_6017 > 0.0;
                boolean bl5 = (double)class_3102.field_1724.field_6017 >= 0.5 || d <= -0.04;
                double d2 = FallingStateMachine.distanceToGround(class_7462);
                float f = FallingStateMachine.resolveTriggerFallBlocks(class_7462, d2);
                boolean bl6 = d2 >= 2.0;
                boolean bl7 = bl = !bl2 && !bl3 && (bl6 || bl4 && bl5);
                if (!bl) {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "falling");
                    ItemAnimationManager.clearPersistentType(class_1268.field_5810, "falling");
                    this.stopIfActive();
                } else {
                    ItemAnimationManager.setPersistentType(class_1268.field_5808, "falling", true, true);
                    ItemAnimationManager.setPersistentType(class_1268.field_5810, "falling", true, true);
                    activeType = "falling";
                    String string = "falling";
                    ClipSelection clipSelection = this.resolveCustomClip(class_3102, string, f);
                    if (clipSelection.clip == null) {
                        if (this.activeClip != null) {
                            this.stopIfActive();
                        }
                    } else {
                        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                        boolean bl8 = PunchyAnimationManager.POSE_HANDLER.isPlaying();
                        if (!bl8 || !FallingStateMachine.isAttackLikeClip(animationClip)) {
                            if (bl8 && animationClip != clipSelection.clip) {
                                this.stopIfActive();
                                bl8 = PunchyAnimationManager.POSE_HANDLER.isPlaying();
                                animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                            }
                            float f2 = PunchyAnimationManager.clipSpeedMultiplier(clipSelection.clip) * this.resolveFallingSpeedBoost();
                            if (animationClip == clipSelection.clip && bl8) {
                                PunchyAnimationManager.POSE_HANDLER.setSpeedMultiplier(f2);
                            } else {
                                class_1306 class_13062;
                                class_1306 class_13063 = class_13062 = clipSelection.hand == null ? null : PunchyAnimationManager.getUseArm(class_3102, clipSelection.hand);
                                if (PunchyAnimationManager.isDualHandedActive(class_3102, clipSelection.clip, 0.0f)) {
                                    class_13062 = null;
                                }
                                CustomAnimationResolver.applyLoopSpeed(clipSelection.entry);
                                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
                                PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                                boolean bl9 = true;
                                PunchyAnimationManager.POSE_HANDLER.play(clipSelection.clip, f2, bl9, false);
                                this.activeClip = clipSelection.clip;
                                this.activeEntry = clipSelection.entry;
                                activeType = string;
                            }
                        }
                    }
                }
            }
        }
    }

    private void stopIfActive() {
        AnimationClip animationClip;
        if (this.activeClip != null && (animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip()) == this.activeClip) {
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            if (CustomAnimationResolver.shouldSkipBlendOut(this.activeEntry)) {
                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
            }
            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
        }
        this.activeClip = null;
        activeType = null;
        this.activeEntry = null;
        this.fallingStartNanos = -1L;
    }

    public static String getActiveType() {
        return activeType;
    }

    private ClipSelection resolveCustomClip(class_310 class_3102, String string, float f) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            ClipSelection clipSelection = this.resolveFromStack(class_3102, class_17992, class_1268.field_5808, string, f);
            if (clipSelection.clip != null) {
                return clipSelection;
            }
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            return this.resolveFromStack(class_3102, class_17993, class_1268.field_5810, string, f);
        }
        return new ClipSelection(null, null, null);
    }

    private ClipSelection resolveFromStack(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, String string, float f) {
        if (class_17992 == null) {
            return new ClipSelection(null, null, null);
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = this.resolveBestEntryForFalling(class_17992, string, f);
        CustomAnimationResolver.ClipLookup clipLookup = CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, string, customAnimationEntry, class_3102, class_12682);
        return clipLookup.clip() == null ? new ClipSelection(null, null, null) : new ClipSelection(clipLookup.clip(), class_12682, customAnimationEntry);
    }

    private PunchyTuningConfig.CustomAnimationEntry resolveBestEntryForFalling(class_1799 class_17992, String string, float f) {
        List<PunchyTuningConfig.CustomAnimationEntry> list = CustomAnimationResolver.findEntries(class_17992, string);
        if (list != null && !list.isEmpty()) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = null;
            float f2 = -1.0f;
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2 : list) {
                float f3;
                if (customAnimationEntry2 == null || f + 1.0E-4f < (f3 = FallingStateMachine.resolveMinBlocks(customAnimationEntry2)) || customAnimationEntry != null && !(f3 > f2)) continue;
                customAnimationEntry = customAnimationEntry2;
                f2 = f3;
            }
            return customAnimationEntry;
        }
        return null;
    }

    private static float resolveMinBlocks(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && customAnimationEntry.min_blocks != null ? Math.max(0.0f, customAnimationEntry.min_blocks.floatValue()) : 0.0f;
    }

    private static float resolveTriggerFallBlocks(class_746 class_7462, double d) {
        if (class_7462 == null) {
            return 0.0f;
        }
        float f = (float)Math.max(0.0, (double)class_7462.field_6017);
        float f2 = (float)Math.max(0.0, d);
        return f + f2;
    }

    private static double distanceToGround(class_746 class_7462) {
        if (class_7462 != null && class_7462.method_37908() != null) {
            class_238 class_2383 = class_7462.method_5829();
            double d = class_2383.field_1322 + 0.01;
            double d2 = Math.max(0.01, (class_2383.field_1320 - class_2383.field_1323) * 0.35);
            double d3 = Math.max(0.01, (class_2383.field_1324 - class_2383.field_1321) * 0.35);
            double d4 = (class_2383.field_1323 + class_2383.field_1320) * 0.5;
            double d5 = (class_2383.field_1321 + class_2383.field_1324) * 0.5;
            double[] dArray = new double[]{d4, class_2383.field_1323 + d2, class_2383.field_1320 - d2, class_2383.field_1323 + d2, class_2383.field_1320 - d2};
            double[] dArray2 = new double[]{d5, class_2383.field_1321 + d3, class_2383.field_1321 + d3, class_2383.field_1324 - d3, class_2383.field_1324 - d3};
            double d6 = -1.0;
            for (int i = 0; i < dArray.length; ++i) {
                double d7;
                class_243 class_2432 = new class_243(dArray[i], d, dArray2[i]);
                class_243 class_2433 = class_2432.method_1023(0.0, 12.0, 0.0);
                class_3965 class_39652 = class_7462.method_37908().method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)class_7462));
                if (class_39652.method_17783() != class_239.class_240.field_1332 || !((d7 = Math.max(0.0, class_2432.field_1351 - class_39652.method_17784().field_1351)) > d6)) continue;
                d6 = d7;
            }
            return d6;
        }
        return -1.0;
    }

    private float resolveFallingSpeedBoost() {
        long l = System.nanoTime();
        if (this.fallingStartNanos < 0L) {
            this.fallingStartNanos = l;
            return 15.0f;
        }
        long l2 = Math.max(0L, l - this.fallingStartNanos);
        float f = Math.min(1.0f, (float)l2 / 1.0E9f);
        return class_3532.method_16439((float)f, (float)15.0f, (float)8.0f);
    }

    private static boolean isAttackLikeClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return string2.contains("attack") || string2.contains("critical");
        }
        return false;
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

