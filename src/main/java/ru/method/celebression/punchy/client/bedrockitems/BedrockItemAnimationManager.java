package ru.method.celebression.punchy.client.bedrockitems;

import ru.method.celebression.animation.Animation;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_1839;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_5321;
import net.minecraft.class_630;
import net.minecraft.class_638;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import net.minecraft.class_9334;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemDefinition;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.state.UseItemStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class BedrockItemAnimationManager {
    private static final EnumMap<class_1306, PoseHandler> HANDLERS;
    private static final EnumMap<class_1306, AnimationClip> ACTIVE_CLIPS;
    private static final EnumMap<class_1306, String> ACTIVE_TYPES;
    private static final EnumMap<class_1306, PunchyTuningConfig.CustomAnimationEntry> ACTIVE_ENTRIES;
    private static final EnumMap<class_1306, PunchyTuningConfig.CustomAnimationEntry> CLIP_ENTRIES;
    private static final EnumMap<class_1306, PunchyTuningConfig.CustomAnimationEntry> KEEP_LOOP_ACTIVE_ENTRY;
    private static final EnumMap<class_1306, String> KEEP_LOOP_ACTIVE_NAME;
    private static final EnumMap<class_1306, Boolean> LAST_PLAYING;
    private static final EnumMap<class_1306, Boolean> SWIM_SUPPRESSION;
    private static final EnumMap<class_1306, Integer> RAIN_GRACE_TICKS;
    private static final EnumMap<class_1306, Integer> INSIDE_WATER_GRACE_TICKS;
    private static final EnumMap<class_1306, Integer> KEEP_LOOP_FALSE_GRACE_TICKS_MAP;
    private static final EnumMap<class_1306, Boolean> ANIM_MIRROR;
    private static final EnumMap<class_1306, String> LAST_STACK_KEY;
    private static final EnumMap<class_1306, AnimationClip> IDLE_CLIPS;
    private static final EnumMap<class_1306, Float> IDLE_TIMES;
    private static final EnumMap<class_1306, Long> LAST_ATTACK_TRIGGER_EPOCH;
    private static final EnumMap<class_1306, Long> LAST_VISUAL_USE_ITEM_EPOCH;
    private static final EnumMap<class_1268, ForcedAction> FORCED_TYPES;
    private static final EnumMap<class_1268, PersistentAction> PERSISTENT_ACTIONS;
    private static final float ITEM_BLEND_IN_SECONDS = 0.0f;
    private static final float ITEM_BLEND_TRANSITION_SECONDS = 0.4f;
    private static final float ITEM_KEEP_LOOP_BLEND_SECONDS = 0.55f;
    private static final float ITEM_IDLE_BLEND_SECONDS = 0.6f;
    private static final float ITEM_TRIGGER_BLEND_OUT_SECONDS = 1.0f;
    private static final float ITEM_INSPECT_BLEND_IN_SECONDS = 0.0f;
    private static final float ITEM_INSPECT_BLEND_OUT_SECONDS = 0.18f;
    private static final float ITEM_INSPECT_BLEND_OUT_SLOW = 1.0f;
    private static final int RAIN_FALSE_GRACE_TICKS = 100;
    private static final float ITEM_BLEND_OUT_SECONDS = 3.0f;
    private static final float ITEM_BLEND_OUT_SLOW = 3.6f;
    private static final float ITEM_BASE_DELTA_SECONDS = 0.05f;
    private static final int INSIDE_WATER_FALSE_GRACE_TICKS = 4;
    private static final int KEEP_LOOP_FALSE_GRACE_TICKS = 6;
    private static final String TYPE_USE_DRINK;
    private static final String TYPE_USE_EAT;

    private BedrockItemAnimationManager() {
    }

    public static void forceTypeOnce(class_1268 class_12682, String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            if (class_12682 == null) {
                BedrockItemAnimationManager.putForcedType(class_1268.field_5808, string, bl);
                BedrockItemAnimationManager.putForcedType(class_1268.field_5810, string, bl);
            } else {
                BedrockItemAnimationManager.putForcedType(class_12682, string, bl);
            }
        }
    }

    public static void setPersistentType(class_1268 class_12682, String string, Boolean bl, boolean bl2) {
        if (class_12682 != null && string != null && !string.isBlank()) {
            PERSISTENT_ACTIONS.put(class_12682, new PersistentAction(string, bl, bl2));
        }
    }

    public static void clearPersistentType(class_1268 class_12682, String string) {
        PersistentAction persistentAction;
        if (class_12682 != null && (persistentAction = PERSISTENT_ACTIONS.get(class_12682)) != null && (string == null || string.isBlank() || string.equals(persistentAction.type))) {
            PERSISTENT_ACTIONS.remove(class_12682);
        }
    }

    public static void clearAllPersistentTypes() {
        PERSISTENT_ACTIONS.clear();
    }

    public static boolean isAnyDualHandedActive() {
        for (class_1306 class_13062 : class_1306.values()) {
            PoseHandler poseHandler = HANDLERS.get(class_13062);
            if (poseHandler == null) continue;
            if (poseHandler.isPlaying() && poseHandler.isCurrentDualHandedActive()) {
                return true;
            }
            AnimationClip animationClip = poseHandler.getSecondaryClip();
            if (animationClip == null || !PunchyAnimationManager.isDualHandedActive(animationClip, poseHandler.getSecondaryTime())) continue;
            return true;
        }
        return false;
    }

    public static void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            for (class_1306 class_13063 : class_1306.values()) {
                SWIM_SUPPRESSION.put(class_13063, false);
                RAIN_GRACE_TICKS.putIfAbsent(class_13063, 0);
                INSIDE_WATER_GRACE_TICKS.putIfAbsent(class_13063, 0);
                KEEP_LOOP_FALSE_GRACE_TICKS_MAP.putIfAbsent(class_13063, 0);
                ANIM_MIRROR.putIfAbsent(class_13063, false);
                PoseHandler poseHandler = HANDLERS.computeIfAbsent(class_13063, class_13062 -> new PoseHandler());
                boolean bl = false;
                boolean bl2 = false;
                poseHandler.setSwapTranslationYZ(bl);
                poseHandler.setJavaBlock(bl2);
                boolean bl3 = Boolean.TRUE.equals(LAST_PLAYING.get(class_13063));
                class_1799 class_17992 = BedrockItemAnimationManager.resolveStack(class_3102, class_13063);
                class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13063);
                if (!class_17992.method_7960() && !PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12682)) {
                    float f;
                    boolean bl4;
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry;
                    boolean bl5;
                    boolean bl6;
                    boolean bl7;
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2;
                    boolean bl8;
                    boolean bl9;
                    Object object;
                    Object object2;
                    Object object3;
                    if (BedrockItemSystem.get().isBlockedByCustomName(class_17992)) {
                        BedrockItemAnimationManager.stopHandler(poseHandler, class_13063);
                        CLIP_ENTRIES.remove(class_13063);
                        KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                        KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                        LAST_STACK_KEY.remove(class_13063);
                        LAST_ATTACK_TRIGGER_EPOCH.remove(class_13063);
                        ModelPartsParticleSystem.clearArmAnimationState(class_13063);
                        continue;
                    }
                    BedrockItemDefinition bedrockItemDefinition = BedrockItemSystem.get().resolveDefinition(class_17992);
                    String string = BedrockItemAnimationManager.getStackKey(class_17992, bedrockItemDefinition);
                    String string2 = LAST_STACK_KEY.get(class_13063);
                    if (string != null && !string.equals(string2)) {
                        BedrockItemAnimationManager.stopHandler(poseHandler, class_13063);
                        ACTIVE_CLIPS.put(class_13063, null);
                        ACTIVE_TYPES.remove(class_13063);
                        ACTIVE_ENTRIES.remove(class_13063);
                        CLIP_ENTRIES.remove(class_13063);
                        KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                        KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                        ModelPartsParticleSystem.resetArm(class_13063);
                    }
                    LAST_STACK_KEY.put(class_13063, string);
                    if (bedrockItemDefinition == null) {
                        BedrockItemAnimationManager.stopHandler(poseHandler, class_13063);
                        CLIP_ENTRIES.remove(class_13063);
                        KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                        KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                        ModelPartsParticleSystem.clearArmAnimationState(class_13063);
                        continue;
                    }
                    if (BedrockItemAnimationManager.hasItemSuppression(bedrockItemDefinition, class_17992, true)) {
                        SWIM_SUPPRESSION.put(class_13063, true);
                    }
                    ActionState actionState = BedrockItemAnimationManager.resolveDesiredState(class_3102, class_13063, class_17992);
                    String string3 = actionState.type;
                    Object object4 = BedrockItemAnimationManager.resolveEntry(bedrockItemDefinition, class_17992, string3, class_3102, class_13063);
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry3 = BedrockItemAnimationManager.resolveEntry(bedrockItemDefinition, class_17992, "idle", class_3102, class_13063);
                    AnimationClip animationClip = BedrockItemAnimationManager.resolveDesiredClip(bedrockItemDefinition, customAnimationEntry3, class_13063, null);
                    if (object4 == null && customAnimationEntry3 != null && !"idle".equals(string3)) {
                        object4 = customAnimationEntry3;
                        string3 = "idle";
                    }
                    String string4 = null;
                    if (object4 != null) {
                        object3 = KEEP_LOOP_ACTIVE_ENTRY.get(class_13063);
                        object2 = KEEP_LOOP_ACTIVE_NAME.get(class_13063);
                        if (object3 == object4 && object2 != null && !((String)object2).isBlank() && BedrockItemAnimationManager.matchesWorldTriggersStable((PunchyTuningConfig.CustomAnimationEntry)object4, class_3102, class_13063)) {
                            string4 = (String)object2;
                        } else {
                            KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                            KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                        }
                    } else {
                        KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                        KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                    }
                    object3 = BedrockItemAnimationManager.resolveDesiredClip(bedrockItemDefinition, (PunchyTuningConfig.CustomAnimationEntry)object4, class_13063, string4);
                    if (object3 == null && animationClip != null && !"idle".equals(string3)) {
                        object4 = customAnimationEntry3;
                        string3 = "idle";
                        object3 = animationClip;
                    }
                    if (object3 == null && object4 != null) {
                        if (animationClip != null) {
                            object4 = customAnimationEntry3;
                            string3 = "idle";
                            object3 = animationClip;
                        } else if (poseHandler.isPlaying() && poseHandler.getCurrentClip() != null && ACTIVE_TYPES.get(class_13063) != null && ACTIVE_TYPES.get(class_13063).equals(string3)) {
                            object3 = poseHandler.getCurrentClip();
                            string3 = ACTIVE_TYPES.getOrDefault(class_13063, string3);
                            object2 = CLIP_ENTRIES.get(class_13063);
                            if (object2 != null) {
                                object4 = object2;
                            }
                        }
                    }
                    boolean bl10 = false;
                    if (class_13063 == class_1306.field_6182 && object3 != null && (object = ((AnimationClip)object3).getName()) != null && ((String)object).endsWith("_left")) {
                        bl10 = true;
                    }
                    ANIM_MIRROR.put(class_13063, bl10);
                    object = ACTIVE_CLIPS.get(class_13063);
                    String string5 = ACTIVE_TYPES.get(class_13063);
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry4 = ACTIVE_ENTRIES.get(class_13063);
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry5 = CLIP_ENTRIES.get(class_13063);
                    if (object != null && customAnimationEntry5 != null && customAnimationEntry5.keep_looping != null && !customAnimationEntry5.keep_looping.isBlank() && !poseHandler.isPlaying() && BedrockItemAnimationManager.matchesWorldTriggersStable(customAnimationEntry5, class_3102, class_13063)) {
                        AnimationClip animationClip2 = BedrockItemAnimationManager.resolveDesiredClip(bedrockItemDefinition, customAnimationEntry5, class_13063, null);
                        boolean bl11 = bl9 = BedrockItemAnimationManager.isEndedClipForEntry(bedrockItemDefinition, (AnimationClip)object, customAnimationEntry5, class_13063) || animationClip2 != null && object == animationClip2;
                        if (bl9) {
                            String string6;
                            String string7 = string6 = string5 != null ? string5 : string3;
                            if (BedrockItemAnimationManager.startKeepLoopTransition(bedrockItemDefinition, poseHandler, class_13063, string6, customAnimationEntry5, customAnimationEntry5.keep_looping.trim(), poseHandler.getCurrentTime())) {
                                poseHandler.tick(0.05f, 1.0f);
                                ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
                                LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                                continue;
                            }
                        }
                    }
                    float f2 = BedrockItemAnimationManager.updateIdleTime(class_13063, poseHandler, animationClip, 0.05f);
                    boolean bl12 = bl9 = object != null && poseHandler.isPlaying() && poseHandler.getCurrentClip() == object && customAnimationEntry5 != null && !BedrockItemAnimationManager.matchesWorldTriggersStable(customAnimationEntry5, class_3102, class_13063);
                    if (bl9) {
                        KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                        KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                        if (animationClip == null) {
                            poseHandler.setLooping(false);
                            boolean bl13 = Boolean.TRUE.equals(customAnimationEntry5.skip_blend_out);
                            poseHandler.setBlendOutDurationNext(bl13 ? 0.0f : 1.0f);
                            poseHandler.stopImmediateWithBlendOut();
                            ACTIVE_CLIPS.put(class_13063, null);
                            ACTIVE_TYPES.remove(class_13063);
                            ACTIVE_ENTRIES.remove(class_13063);
                            CLIP_ENTRIES.remove(class_13063);
                            string5 = null;
                            ModelPartsParticleSystem.clearArmAnimationState(class_13063);
                            LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                            continue;
                        }
                        object3 = animationClip;
                        object4 = customAnimationEntry3;
                        string3 = "idle";
                    }
                    boolean bl14 = ConsumeStateMachine.isEquipBlockedByConsumeAnimation();
                    boolean bl15 = object4 != null && (Boolean.TRUE.equals(((PunchyTuningConfig.CustomAnimationEntry)object4).inside_water) || Boolean.TRUE.equals(((PunchyTuningConfig.CustomAnimationEntry)object4).in_water)) && object3 != null && object3 != object;
                    boolean bl16 = !(bl9 || object == null || !poseHandler.isPlaying() || poseHandler.getCurrentClip() != object || ((AnimationClip)object).isLoop() || ((AnimationClip)object).isHoldOnLastFrame() || poseHandler.isForceLooping() || object3 != null && !"idle".equals(string3) || "falling".equals(string5) || bl15 || BedrockItemAnimationManager.isConsumeType(string5) && !bl14);
                    boolean bl17 = bl8 = !bl9 && object != null && poseHandler.getCurrentClip() == object && ((AnimationClip)object).isHoldOnLastFrame() && "falling".equals(string3) && !"falling".equals(string5) && !bl15;
                    if (bl16) {
                        object3 = object;
                        if (string5 != null) {
                            string3 = string5;
                        }
                        if ((customAnimationEntry2 = CLIP_ENTRIES.get(class_13063)) != null) {
                            object4 = customAnimationEntry2;
                        }
                    }
                    if (bl8) {
                        object3 = object;
                        if (string5 != null) {
                            string3 = string5;
                        }
                        if ((customAnimationEntry2 = CLIP_ENTRIES.get(class_13063)) != null) {
                            object4 = customAnimationEntry2;
                        }
                    }
                    boolean bl18 = bl7 = object3 != null && object3 == object && BedrockItemAnimationManager.isConsumeType(string3) && !poseHandler.isPlaying() && !poseHandler.isBlendOutActive();
                    if (bl7) {
                        object = null;
                        ACTIVE_CLIPS.put(class_13063, null);
                    }
                    if (object == null && object3 != null && !actionState.immediate && poseHandler.isBlendOutActive() && poseHandler.getCurrentClip() != null) {
                        poseHandler.tick(0.05f, 1.0f);
                        ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
                        LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                        continue;
                    }
                    ACTIVE_ENTRIES.put(class_13063, (PunchyTuningConfig.CustomAnimationEntry)object4);
                    if (object4 != null) {
                        if (((PunchyTuningConfig.CustomAnimationEntry)object4).isJavaBlock != null) {
                            bl2 = ((PunchyTuningConfig.CustomAnimationEntry)object4).isJavaBlock;
                        }
                        poseHandler.setLoopSpeedParams(((PunchyTuningConfig.CustomAnimationEntry)object4).loop_speed_max, ((PunchyTuningConfig.CustomAnimationEntry)object4).loop_speed_ramp);
                    } else {
                        poseHandler.resetLoopSpeedParams();
                    }
                    poseHandler.setSwapTranslationYZ(bl);
                    poseHandler.setJavaBlock(bl2);
                    if (object4 != null && Boolean.TRUE.equals(((PunchyTuningConfig.CustomAnimationEntry)object4).disable_swim_animation)) {
                        SWIM_SUPPRESSION.put(class_13063, true);
                    }
                    if (bl6 = BedrockItemAnimationManager.shouldRestartVisualForcedUseItem(class_13063, string3, (AnimationClip)object3)) {
                        object = null;
                        ACTIVE_CLIPS.put(class_13063, null);
                    }
                    boolean bl19 = bl5 = !Objects.equals(string5, string3);
                    if (object3 == object && bl5) {
                        ACTIVE_TYPES.put(class_13063, string3);
                        CLIP_ENTRIES.put(class_13063, (PunchyTuningConfig.CustomAnimationEntry)object4);
                        ACTIVE_CLIPS.put(class_13063, (AnimationClip)object3);
                    } else if (object3 != object || bl5) {
                        boolean bl20;
                        customAnimationEntry = customAnimationEntry4 != null ? customAnimationEntry4 : CLIP_ENTRIES.get(class_13063);
                        boolean bl21 = customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_blend_out);
                        boolean bl22 = object4 != null && Boolean.TRUE.equals(((PunchyTuningConfig.CustomAnimationEntry)object4).skip_blend_in);
                        boolean bl23 = bl20 = bl21 || bl22;
                        if (object3 != null) {
                            boolean bl24;
                            boolean bl25;
                            boolean bl26 = "inspect".equals(string3);
                            if (bl20 && (poseHandler.isPlaying() || poseHandler.isBlendOutActive())) {
                                poseHandler.resetImmediate();
                            }
                            poseHandler.setActiveArm(null);
                            boolean bl27 = bl25 = actionState.loopOverride != null ? actionState.loopOverride : BedrockItemAnimationManager.shouldLoop(string3);
                            if (object4 != null && ((PunchyTuningConfig.CustomAnimationEntry)object4).keep_looping != null && !((PunchyTuningConfig.CustomAnimationEntry)object4).keep_looping.isBlank()) {
                                bl25 = false;
                            }
                            if (string4 != null) {
                                bl25 = true;
                            }
                            poseHandler.setLooping(bl25);
                            boolean bl28 = poseHandler.isPlaying() && poseHandler.getCurrentClip() != null;
                            bl4 = object3 == animationClip;
                            float f3 = 1.0f;
                            boolean bl29 = BedrockItemAnimationManager.shouldBlendFromCurrentOrigin((PunchyTuningConfig.CustomAnimationEntry)object4);
                            boolean bl30 = bl24 = object4 != null && Boolean.TRUE.equals(((PunchyTuningConfig.CustomAnimationEntry)object4).skip_blend_in);
                            if (bl21 || bl22) {
                                bl24 = true;
                            }
                            if (bl24) {
                                poseHandler.setBlendInDurationNext(0.0f);
                            }
                            if (actionState.immediate) {
                                if (!bl28) {
                                    if (!bl24) {
                                        float blendDuration = 0.0f;
                                        if (bl4 && bl3) {
                                            blendDuration = 0.6f;
                                        }
                                        poseHandler.setBlendInDurationNext(blendDuration);
                                    }
                                    if (bl4) {
                                        poseHandler.playAtTime((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                    } else if (bl29 && bl3) {
                                        poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                                        poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin((AnimationClip)object3, f3, ((AnimationClip)object3).isHoldOnLastFrame(), 0.0f);
                                    } else {
                                        poseHandler.play((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame());
                                    }
                                } else {
                                    if (!bl24) {
                                        poseHandler.setBlendInDurationNext(bl26 ? 0.0f : (bl4 && "idle".equals(string5) ? 0.6f : 0.4f));
                                    }
                                    if (bl4) {
                                        if (bl24) {
                                            poseHandler.playAtTime((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                        } else if (bl29) {
                                            poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                                            poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin((AnimationClip)object3, f3, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                        } else {
                                            poseHandler.playAtTime((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                        }
                                    } else if (bl24) {
                                        poseHandler.play((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame());
                                    } else if (bl29) {
                                        poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                                        poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin((AnimationClip)object3, f3, ((AnimationClip)object3).isHoldOnLastFrame(), 0.0f);
                                    } else {
                                        poseHandler.play((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame());
                                    }
                                }
                            } else {
                                if (!bl24) {
                                    float blendDuration = bl26 ? 0.0f : (bl28 ? 0.4f : 0.0f);
                                    if (bl28 && bl4 && "idle".equals(string5)) {
                                        blendDuration = 0.6f;
                                    } else if (!bl28 && bl4 && bl3) {
                                        blendDuration = 0.6f;
                                    }
                                    poseHandler.setBlendInDurationNext(blendDuration);
                                }
                                if (bl4) {
                                    if (bl24) {
                                        poseHandler.playAtTime((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                    } else if (bl29) {
                                        poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                                        poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin((AnimationClip)object3, f3, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                    } else {
                                        poseHandler.playAtTime((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame(), f2);
                                    }
                                } else if (bl24) {
                                    poseHandler.play((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame());
                                } else if (bl29) {
                                    poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                                    poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin((AnimationClip)object3, f3, ((AnimationClip)object3).isHoldOnLastFrame(), 0.0f);
                                } else {
                                    poseHandler.play((AnimationClip)object3, f3, true, ((AnimationClip)object3).isHoldOnLastFrame());
                                }
                            }
                            if (!Objects.equals(string3, string5)) {
                                MovementStateMachine.get().triggerActionAnimation(class_13063);
                            }
                            ACTIVE_TYPES.put(class_13063, string3);
                            CLIP_ENTRIES.put(class_13063, (PunchyTuningConfig.CustomAnimationEntry)object4);
                        } else if (object != null) {
                            boolean bl31;
                            boolean bl32 = bl31 = customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_blend_out);
                            if (bl31) {
                                poseHandler.resetImmediate();
                            } else {
                                boolean bl33 = "inspect".equals(string5);
                                poseHandler.setBlendOutDurationNext(bl33 ? 0.18f : 3.0f);
                                poseHandler.setBlendOutSlowNext(bl33 ? 1.0f : 3.6f);
                                poseHandler.setLooping(false);
                                poseHandler.stopImmediateWithBlendOut();
                            }
                            ACTIVE_TYPES.remove(class_13063);
                            ACTIVE_ENTRIES.remove(class_13063);
                            CLIP_ENTRIES.remove(class_13063);
                        }
                        ACTIVE_CLIPS.put(class_13063, (AnimationClip)object3);
                    }
                    BedrockItemAnimationManager.syncIdleOverlay(poseHandler, class_13063, animationClip, f2, ACTIVE_CLIPS.get(class_13063));
                    customAnimationEntry = CLIP_ENTRIES.get(class_13063);
                    String string8 = customAnimationEntry != null ? customAnimationEntry.keep_looping : null;
                    AnimationClip animationClip3 = ACTIVE_CLIPS.get(class_13063);
                    if (customAnimationEntry != null && string8 != null && !string8.isBlank() && animationClip3 != null && poseHandler.isPlaying() && poseHandler.getCurrentClip() == animationClip3 && !animationClip3.isLoop() && !animationClip3.isHoldOnLastFrame() && !poseHandler.isForceLooping() && BedrockItemAnimationManager.isEndedClipForEntry(bedrockItemDefinition, animationClip3, customAnimationEntry, class_13063) && BedrockItemAnimationManager.matchesWorldTriggersStable(customAnimationEntry, class_3102, class_13063) && (f = Math.max(0.0f, animationClip3.getLength() - poseHandler.getCurrentTime())) <= 0.3f && BedrockItemAnimationManager.startKeepLoopTransition(bedrockItemDefinition, poseHandler, class_13063, string3, customAnimationEntry, string8.trim(), poseHandler.getCurrentTime())) {
                        poseHandler.tick(0.05f, 1.0f);
                        ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
                        LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                        continue;
                    }
                    poseHandler.tick(0.05f, 1.0f);
                    ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
                    if (!poseHandler.isPlaying()) {
                        String string9;
                        customAnimationEntry5 = CLIP_ENTRIES.get(class_13063);
                        String string10 = string9 = customAnimationEntry5 != null ? customAnimationEntry5.keep_looping : null;
                        if (string9 != null && !string9.isBlank() && BedrockItemAnimationManager.matchesWorldTriggersStable(customAnimationEntry5, class_3102, class_13063)) {
                            AnimationClip animationClip4 = BedrockItemAnimationManager.resolveDesiredClip(bedrockItemDefinition, customAnimationEntry5, class_13063, null);
                            AnimationClip animationClip5 = poseHandler.getCurrentClip();
                            AnimationClip animationClip6 = ACTIVE_CLIPS.get(class_13063);
                            boolean bl34 = bl4 = BedrockItemAnimationManager.isEndedClipForEntry(bedrockItemDefinition, animationClip5, customAnimationEntry5, class_13063) || animationClip5 == null && animationClip4 != null && animationClip6 == animationClip4;
                            if (!bl4) {
                                ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
                                LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                                continue;
                            }
                            if (BedrockItemAnimationManager.startKeepLoopTransition(bedrockItemDefinition, poseHandler, class_13063, string3, customAnimationEntry5, string9.trim(), poseHandler.getCurrentTime())) {
                                // empty if block
                            }
                        }
                    }
                    LAST_PLAYING.put(class_13063, poseHandler.isPlaying());
                    continue;
                }
                BedrockItemAnimationManager.stopHandler(poseHandler, class_13063);
                CLIP_ENTRIES.remove(class_13063);
                KEEP_LOOP_ACTIVE_ENTRY.remove(class_13063);
                KEEP_LOOP_ACTIVE_NAME.remove(class_13063);
                LAST_STACK_KEY.remove(class_13063);
                LAST_ATTACK_TRIGGER_EPOCH.remove(class_13063);
                ModelPartsParticleSystem.resetArm(class_13063);
            }
        }
    }

    public static void applyToParts(class_1306 class_13062, Map<String, class_630> map, float f) {
        PoseHandler poseHandler;
        if (class_13062 != null && map != null && !map.isEmpty() && (poseHandler = HANDLERS.get(class_13062)) != null) {
            boolean bl = Boolean.TRUE.equals(ANIM_MIRROR.get(class_13062));
            poseHandler.setMirrorX(bl);
            poseHandler.applyTransformations(map, f, true);
        }
    }

    public static boolean isSwimSuppressed(class_1306 class_13062) {
        return class_13062 == null ? false : Boolean.TRUE.equals(SWIM_SUPPRESSION.get(class_13062));
    }

    public static boolean isAnimMirrorActive(class_1306 class_13062) {
        return class_13062 == null ? false : Boolean.TRUE.equals(ANIM_MIRROR.get(class_13062));
    }

    public static PunchyTuningConfig.CustomAnimationEntry getActiveEntry(class_1306 class_13062) {
        return class_13062 == null ? null : ACTIVE_ENTRIES.get(class_13062);
    }

    private static boolean hasItemSuppression(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, boolean bl) {
        if (bedrockItemDefinition != null && class_17992 != null && !class_17992.method_7960()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = bedrockItemDefinition.getModelPartsAnimation();
            if (list != null && !list.isEmpty()) {
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    if (customAnimationEntry == null || customAnimationEntry.items != null && !customAnimationEntry.items.isEmpty() && !BedrockItemSystem.matchesItemList(class_17992, customAnimationEntry.items) || !bl || !Boolean.TRUE.equals(customAnimationEntry.disable_swim_animation)) continue;
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static String getStackKey(class_1799 class_17992, BedrockItemDefinition bedrockItemDefinition) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return null;
            }
            int customModelData = -1;
            try {
                Object component = class_17992.method_57824(class_9334.field_49637);
                customModelData = BedrockItemAnimationManager.extractIntegerComponent(component);
            }
            catch (Throwable ignored) {
            }
            String customName = "";
            try {
                var name = class_17992.method_7964();
                if (name != null) {
                    String value = name.getString();
                    customName = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
                }
            }
            catch (Throwable ignored) {
            }
            String definitionId = bedrockItemDefinition != null && bedrockItemDefinition.getId() != null ? bedrockItemDefinition.getId().toString() : "null";
            return class_29602 + ":" + customModelData + "|name=" + customName + "|def=" + definitionId;
        }
        return null;
    }

    private static int extractIntegerComponent(Object component) {
        if (component instanceof Integer value) {
            return value;
        }
        if (component == null) {
            return -1;
        }
        for (String accessor : List.of("value", "getValue")) {
            try {
                Object value = component.getClass().getMethod(accessor).invoke(component);
                if (value instanceof Integer integer) {
                    return integer;
                }
                if (value instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Integer integer) {
                    return integer;
                }
            }
            catch (Throwable ignored) {
            }
        }
        return -1;
    }

    private static void stopHandler(PoseHandler poseHandler, class_1306 class_13062) {
        poseHandler.setLooping(false);
        poseHandler.resetImmediate();
        ACTIVE_CLIPS.put(class_13062, null);
        ACTIVE_TYPES.remove(class_13062);
        ACTIVE_ENTRIES.remove(class_13062);
    }

    private static ActionState resolveDesiredState(class_310 class_3102, class_1306 class_13062, class_1799 class_17992) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            ActionState actionState = BedrockItemAnimationManager.resolveVisualForcedUseItemState(class_3102, class_13062, class_17992);
            if (actionState != null) {
                return actionState;
            }
            ForcedAction forcedAction = BedrockItemAnimationManager.consumeForcedTypeForArm(class_3102, class_13062);
            if (forcedAction != null) {
                return new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, forcedAction.type), null, forcedAction.immediate);
            }
            PersistentAction persistentAction = BedrockItemAnimationManager.getPersistentForArm(class_3102, class_13062);
            if (persistentAction != null) {
                return new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, persistentAction.type), persistentAction.loopOverride, persistentAction.immediate);
            }
            String string = BedrockItemAnimationManager.resolveConsumeTypeForArmDuringFinish(class_3102, class_13062);
            if (string != null) {
                return new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, string), null, true);
            }
            if (BedrockItemAnimationManager.consumeAttackPulseForArm(class_3102, class_13062)) {
                return new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, "attack"), null, true);
            }
            String string2 = BedrockItemAnimationManager.resolveConsumeTypeForArm(class_3102, class_13062);
            if (string2 != null) {
                return new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, string2), null, true);
            }
            return BedrockItemAnimationManager.isChargeUseActiveForArm(class_3102, class_13062) ? new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, "useItem"), true, true) : new ActionState(BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, "idle"), null, false);
        }
        return ActionState.EMPTY;
    }

    private static ActionState resolveVisualForcedUseItemState(class_310 class_3102, class_1306 class_13062, class_1799 class_17992) {
        if (class_3102 == null || class_3102.field_1724 == null || class_13062 == null || class_17992 == null || class_17992.method_7960()) {
            return null;
        }
        if (!PunchyAnimationManager.isItemForceShown(class_13062)) {
            return null;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            String string = animationClip.getName();
            if (string == null || string.isBlank()) {
                return null;
            }
            if (!BedrockItemAnimationManager.isVisualUseItemPose(string)) {
                return null;
            }
            String string2 = BedrockItemAnimationManager.resolveTypeWithFallback(class_17992, "useItem");
            return string2 == null ? null : new ActionState(string2, false, true);
        }
        return null;
    }

    private static boolean isVisualUseItemPose(String string) {
        String string2 = BedrockItemAnimationManager.normalizeType(string);
        if (string2.isEmpty()) {
            return false;
        }
        if (string2.contains("strikefire")) {
            return true;
        }
        return !string2.contains("interact") && !string2.contains("interaction") ? string2.startsWith("use") || string2.contains("flintsteel") : true;
    }

    private static boolean shouldRestartVisualForcedUseItem(class_1306 class_13062, String string, AnimationClip animationClip) {
        if (class_13062 == null || animationClip == null || !"useItem".equals(string)) {
            return false;
        }
        if (!PunchyAnimationManager.isItemForceShown(class_13062)) {
            return false;
        }
        long l = UseItemStateMachine.getVisualUseItemEpoch();
        if (l <= 0L) {
            return false;
        }
        long l2 = LAST_VISUAL_USE_ITEM_EPOCH.getOrDefault(class_13062, 0L);
        LAST_VISUAL_USE_ITEM_EPOCH.put(class_13062, l);
        return l > l2;
    }

    private static String resolveConsumeTypeForArm(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 == null || class_3102.field_1724 == null || class_13062 == null) {
            return null;
        }
        if (class_3102.field_1724.method_6115() && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434()) {
            class_1268 class_12682;
            class_1268 class_12683 = class_3102.field_1724.method_6058();
            if (class_12683 != (class_12682 = BedrockItemAnimationManager.resolveHand(class_3102, class_13062))) {
                return null;
            }
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            if (class_17992 != null && !class_17992.method_7960()) {
                class_1839 class_18392 = class_17992.method_7976();
                if (class_18392 == class_1839.field_8946) {
                    return "useDrink";
                }
                return class_18392 == class_1839.field_8950 ? "useEat" : null;
            }
            return null;
        }
        return null;
    }

    private static String resolveConsumeTypeForArmDuringFinish(class_310 class_3102, class_1306 class_13062) {
        if (!ConsumeStateMachine.isEquipBlockedByConsumeAnimation()) {
            return null;
        }
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            String string = ConsumeStateMachine.getForcedConsumeTypeForArm(class_13062);
            if (string != null) {
                return string;
            }
            if (!PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                return null;
            }
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null) {
                return null;
            }
            class_1306 class_13063 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            if (class_13063 != null && class_13063 != class_13062) {
                return null;
            }
            if (PunchyAnimationManager.isClipNamed(animationClip, "use_drink")) {
                return "useDrink";
            }
            return PunchyAnimationManager.isClipNamed(animationClip, "use_eat") ? "useEat" : null;
        }
        return null;
    }

    private static boolean isChargeUseActiveForArm(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 == null || class_3102.field_1724 == null || class_13062 == null) {
            return false;
        }
        if (class_3102.field_1724.method_6115() && class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434()) {
            class_1268 class_12682;
            class_1268 class_12683 = class_3102.field_1724.method_6058();
            if (class_12683 != (class_12682 = BedrockItemAnimationManager.resolveHand(class_3102, class_13062))) {
                return false;
            }
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7909() instanceof class_1753 || class_17992.method_7909() instanceof class_1764 || class_17992.method_7909() instanceof class_1835 || SpearStateMachine.isSpearStack(class_17992) : false;
        }
        return false;
    }

    private static ForcedAction consumeForcedTypeForArm(class_310 class_3102, class_1306 class_13062) {
        class_1268 class_12682 = BedrockItemAnimationManager.resolveHand(class_3102, class_13062);
        return FORCED_TYPES.remove(class_12682);
    }

    private static PersistentAction getPersistentForArm(class_310 class_3102, class_1306 class_13062) {
        class_1268 class_12682 = BedrockItemAnimationManager.resolveHand(class_3102, class_13062);
        PersistentAction persistentAction = PERSISTENT_ACTIONS.get(class_12682);
        if (persistentAction != null) {
            return persistentAction;
        }
        if (class_3102 == null || class_3102.field_1724 == null || class_13062 == null) {
            return null;
        }
        if (!PunchyAnimationManager.isItemForceShown(class_13062)) {
            return null;
        }
        class_1306 class_13063 = PunchyAnimationManager.getSourceArm();
        if (class_13063 != null && class_13063 != class_13062) {
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                float f = PunchyAnimationManager.POSE_HANDLER.getCurrentTime();
                if (!PunchyAnimationManager.isDualHandedActive(animationClip, f)) {
                    return null;
                }
                class_1268 class_12683 = BedrockItemAnimationManager.resolveHand(class_3102, class_13063);
                return PERSISTENT_ACTIONS.get(class_12683);
            }
            return null;
        }
        return null;
    }

    private static boolean consumeAttackPulseForArm(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            long l;
            class_1268 class_12682 = BedrockItemAnimationManager.resolveHand(class_3102, class_13062);
            class_1268 class_12683 = AttackActionTracker.peekLastRecordedHand();
            if (class_12683 != class_12682) {
                return false;
            }
            long l2 = AttackActionTracker.peekEpoch();
            if (l2 <= (l = LAST_ATTACK_TRIGGER_EPOCH.getOrDefault(class_13062, 0L).longValue())) {
                return false;
            }
            LAST_ATTACK_TRIGGER_EPOCH.put(class_13062, l2);
            return true;
        }
        return false;
    }

    private static class_1799 resolveStack(class_310 class_3102, class_1306 class_13062) {
        class_1799 class_17992 = ConsumeStateMachine.getForcedRenderStack(class_13062);
        if (class_17992 != null && !class_17992.method_7960()) {
            return class_17992;
        }
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
            class_1268 class_12682 = class_13062 == class_1306.field_6183 ? (bl ? class_1268.field_5808 : class_1268.field_5810) : (bl ? class_1268.field_5810 : class_1268.field_5808);
            class_1799 class_17993 = class_12682 == class_1268.field_5810 ? HandEquipStateMachine.getVisualOffhandStack(class_3102.field_1724) : class_3102.field_1724.method_6047();
            return PunchyAnimationManager.resolveRenderStack((class_1657)class_3102.field_1724, class_13062, class_17993);
        }
        return class_1799.field_8037;
    }

    private static class_1268 resolveHand(class_310 class_3102, class_1306 class_13062) {
        boolean bl;
        boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
        if (class_13062 == class_1306.field_6183) {
            return bl ? class_1268.field_5808 : class_1268.field_5810;
        }
        return bl ? class_1268.field_5810 : class_1268.field_5808;
    }

    private static AnimationClip resolveDesiredClip(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, String string, class_310 class_3102, class_1306 class_13062) {
        if (bedrockItemDefinition != null && string != null && !string.isBlank()) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = BedrockItemAnimationManager.resolveEntry(bedrockItemDefinition, class_17992, string, class_3102, class_13062);
            return BedrockItemAnimationManager.resolveDesiredClip(bedrockItemDefinition, customAnimationEntry, class_13062, null);
        }
        return null;
    }

    private static AnimationClip resolveDesiredClip(BedrockItemDefinition bedrockItemDefinition, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_1306 class_13062, String string) {
        if (bedrockItemDefinition == null) {
            return null;
        }
        AnimationClip requestedClip = string != null && !string.isBlank() ? BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string.trim()) : null;
        if (requestedClip != null) {
            if (class_13062 != null) {
                String string2 = class_13062 == class_1306.field_6182 ? "_left" : "_right";
                AnimationClip animationClip = BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string.trim() + string2);
                if (animationClip != null) {
                    return animationClip;
                }
            }
            return requestedClip;
        }
        String entryName = BedrockItemAnimationManager.resolveEntryName(customAnimationEntry);
        if (entryName != null && !entryName.isBlank()) {
            String string3;
            AnimationClip animationClip;
            if (class_13062 != null && (animationClip = BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, entryName + (string3 = class_13062 == class_1306.field_6182 ? "_left" : "_right"))) != null) {
                return animationClip;
            }
            return BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, entryName);
        }
        return null;
    }

    private static AnimationClip resolveClipByName(BedrockItemDefinition bedrockItemDefinition, String string) {
        if (bedrockItemDefinition != null && string != null && !string.isBlank()) {
            AnimationClip animationClip = bedrockItemDefinition.getAnimations().get(string);
            return animationClip != null ? animationClip : bedrockItemDefinition.getAnimations().get(bedrockItemDefinition.getId().method_12836() + ":" + string);
        }
        return null;
    }

    private static AnimationClip resolveKeepLoopClip(BedrockItemDefinition bedrockItemDefinition, String string, class_1306 class_13062) {
        if (bedrockItemDefinition != null && string != null && !string.isBlank()) {
            String string2;
            AnimationClip animationClip;
            if (class_13062 != null && (animationClip = BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string + (string2 = class_13062 == class_1306.field_6182 ? "_left" : "_right"))) != null) {
                return animationClip;
            }
            return BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string);
        }
        return null;
    }

    private static boolean isEndedClipForEntry(BedrockItemDefinition bedrockItemDefinition, AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_1306 class_13062) {
        if (bedrockItemDefinition != null && animationClip != null && customAnimationEntry != null) {
            String string = BedrockItemAnimationManager.resolveEntryName(customAnimationEntry);
            if (string != null && !string.isBlank()) {
                AnimationClip animationClip2 = BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string);
                if (animationClip2 != null && animationClip == animationClip2) {
                    return true;
                }
                if (class_13062 != null) {
                    String string2 = class_13062 == class_1306.field_6182 ? "_left" : "_right";
                    AnimationClip animationClip3 = BedrockItemAnimationManager.resolveClipByName(bedrockItemDefinition, string + string2);
                    return animationClip == animationClip3;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static boolean startKeepLoopTransition(BedrockItemDefinition bedrockItemDefinition, PoseHandler poseHandler, class_1306 class_13062, String string, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, String string2, float f) {
        if (bedrockItemDefinition != null && poseHandler != null && customAnimationEntry != null && string2 != null && !string2.isBlank()) {
            AnimationClip animationClip = BedrockItemAnimationManager.resolveKeepLoopClip(bedrockItemDefinition, string2, class_13062);
            if (animationClip == null) {
                return false;
            }
            boolean bl = Boolean.TRUE.equals(customAnimationEntry.skip_blend_in);
            boolean bl2 = BedrockItemAnimationManager.shouldBlendFromCurrentOrigin(customAnimationEntry);
            boolean bl3 = !poseHandler.isPlaying() || poseHandler.getCurrentClip() == null;
            poseHandler.setLooping(true);
            if (!bl && !bl3) {
                poseHandler.setBlendInDurationNext(0.55f);
                if (bl2) {
                    poseHandler.setBlendInEasingNext(PoseHandler.BlendInEasing.STRONG_OUT_BACK);
                    poseHandler.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, 1.0f, false, f);
                } else {
                    poseHandler.playAtTime(animationClip, 1.0f, true, false, f);
                }
            } else {
                poseHandler.playAtTime(animationClip, 1.0f, true, false, f);
            }
            ACTIVE_CLIPS.put(class_13062, animationClip);
            ACTIVE_TYPES.put(class_13062, string);
            KEEP_LOOP_ACTIVE_ENTRY.put(class_13062, customAnimationEntry);
            KEEP_LOOP_ACTIVE_NAME.put(class_13062, string2);
            return true;
        }
        return false;
    }

    private static PunchyTuningConfig.CustomAnimationEntry resolveEntry(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, String string, class_310 class_3102, class_1306 class_13062) {
        if (bedrockItemDefinition == null) {
            return null;
        }
        List<PunchyTuningConfig.CustomAnimationEntry> list = bedrockItemDefinition.getModelPartsAnimation();
        if (list != null && !list.isEmpty()) {
            String string2 = BedrockItemAnimationManager.normalizeType(string);
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = null;
            int n = -1;
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2 = null;
            int n2 = -1;
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry3 : list) {
                if (customAnimationEntry3 == null || customAnimationEntry3.type == null || !BedrockItemAnimationManager.matchesRequestedType(string2, BedrockItemAnimationManager.normalizeType(customAnimationEntry3.type)) || customAnimationEntry3.items != null && !customAnimationEntry3.items.isEmpty() && !BedrockItemSystem.matchesItemList(class_17992, customAnimationEntry3.items) || !BedrockItemAnimationManager.matchesWorldTriggersStable(customAnimationEntry3, class_3102, class_13062)) continue;
                int n3 = BedrockItemAnimationManager.triggerSpecificityScore(customAnimationEntry3);
                if (n3 > 0) {
                    if (n3 <= n) continue;
                    customAnimationEntry = customAnimationEntry3;
                    n = n3;
                    continue;
                }
                if (n3 <= n2) continue;
                customAnimationEntry2 = customAnimationEntry3;
                n2 = n3;
            }
            return customAnimationEntry != null ? customAnimationEntry : customAnimationEntry2;
        }
        return null;
    }

    private static String resolveEntryName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        if (customAnimationEntry.animation != null && !customAnimationEntry.animation.isBlank()) {
            return customAnimationEntry.animation.trim();
        }
        if (customAnimationEntry.name != null && !customAnimationEntry.name.isBlank()) {
            return customAnimationEntry.name.trim();
        }
        return customAnimationEntry.var_1 != null && !customAnimationEntry.var_1.isBlank() ? customAnimationEntry.var_1.trim() : null;
    }

    private static float updateIdleTime(class_1306 class_13062, PoseHandler poseHandler, AnimationClip animationClip, float f) {
        float f2;
        if (animationClip == null) {
            IDLE_CLIPS.remove(class_13062);
            IDLE_TIMES.remove(class_13062);
            return 0.0f;
        }
        AnimationClip animationClip2 = IDLE_CLIPS.put(class_13062, animationClip);
        Float f3 = IDLE_TIMES.get(class_13062);
        float f4 = f2 = f3 == null ? 0.0f : f3.floatValue();
        if (animationClip2 != animationClip) {
            f2 = 0.0f;
        }
        f2 = poseHandler != null && poseHandler.isPlaying() && poseHandler.getCurrentClip() == animationClip ? poseHandler.getCurrentTime() : (poseHandler != null && poseHandler.getSecondaryClip() == animationClip ? poseHandler.getSecondaryTime() : BedrockItemAnimationManager.advanceLoopTime(animationClip, f2, f));
        IDLE_TIMES.put(class_13062, Float.valueOf(f2));
        return f2;
    }

    private static void syncIdleOverlay(PoseHandler poseHandler, class_1306 class_13062, AnimationClip animationClip, float f, AnimationClip animationClip2) {
        if (poseHandler != null) {
            if (animationClip != null && animationClip2 != null && animationClip2 != animationClip) {
                if (poseHandler.getSecondaryClip() != animationClip) {
                    poseHandler.setSecondaryClipAtTime(animationClip, class_13062, 1.0f, f);
                }
            } else if (poseHandler.getSecondaryClip() != null) {
                poseHandler.clearSecondaryClip();
            }
        }
    }

    private static float advanceLoopTime(AnimationClip animationClip, float f, float f2) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f3 = animationClip.getLength();
        if (f3 <= 0.0f) {
            return 0.0f;
        }
        float f4 = f + f2;
        if (animationClip.isLoop()) {
            if ((f4 %= f3) < 0.0f) {
                f4 += f3;
            }
            return f4;
        }
        return Math.min(f4, f3);
    }

    private static boolean shouldLoop(String string) {
        return string == null ? false : "mining".equals(string) || "useItem".equals(string) || "idle".equals(string);
    }

    private static boolean isConsumeType(String string) {
        return "useDrink".equals(string) || "useEat".equals(string);
    }

    private static boolean shouldBlendFromCurrentOrigin(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.blend_from_current_origin);
    }

    private static String resolveTypeWithFallback(class_1799 class_17992, String string) {
        return string;
    }

    private static String normalizeType(String string) {
        return string == null ? "" : string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
    }

    private static void putForcedType(class_1268 class_12682, String string, boolean bl) {
        if (class_12682 != null && string != null && !string.isBlank()) {
            ForcedAction forcedAction = new ForcedAction(string, bl);
            ForcedAction forcedAction2 = FORCED_TYPES.get(class_12682);
            if (forcedAction2 == null) {
                FORCED_TYPES.put(class_12682, forcedAction);
            } else {
                int n = BedrockItemAnimationManager.forcedTypePriority(forcedAction2.type);
                int n2 = BedrockItemAnimationManager.forcedTypePriority(forcedAction.type);
                if (n2 > n) {
                    FORCED_TYPES.put(class_12682, forcedAction);
                } else if (n2 == n) {
                    FORCED_TYPES.put(class_12682, forcedAction);
                }
            }
        }
    }

    private static int forcedTypePriority(String string) {
        String string2 = BedrockItemAnimationManager.normalizeType(string);
        if ("attack".equals(string2)) {
            return 100;
        }
        if ("triggerable".equals(string2)) {
            return 90;
        }
        if ("inspect".equals(string2)) {
            return 80;
        }
        if ("handout".equals(string2)) {
            return 60;
        }
        if ("handin".equals(string2)) {
            return 55;
        }
        if ("mining".equals(string2)) {
            return 50;
        }
        if ("useitem".equals(string2)) {
            return 45;
        }
        if ("usedrink".equals(string2) || "useeat".equals(string2)) {
            return 44;
        }
        return "idle".equals(string2) ? 0 : 40;
    }

    private static boolean matchesRequestedType(String string, String string2) {
        if (string == null || string2 == null) {
            return false;
        }
        return string.equals(string2) ? true : "idle".equals(string) && "inwater".equals(string2);
    }

    private static int triggerSpecificityScore(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return 0;
        }
        int n = 0;
        if (customAnimationEntry.in_water != null) {
            n += 2;
        }
        if (customAnimationEntry.inside_water != null) {
            n += 3;
        }
        if (customAnimationEntry.block_detection != null) {
            ++n;
        }
        if (customAnimationEntry.weather_detection != null) {
            ++n;
        }
        if (customAnimationEntry.time_detection != null) {
            ++n;
        }
        if (customAnimationEntry.biome_detection != null) {
            ++n;
        }
        return n;
    }

    private static boolean matchesWorldTriggers(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_310 class_3102, class_1306 class_13062) {
        if (customAnimationEntry == null) {
            return false;
        }
        if (customAnimationEntry.in_water == null && customAnimationEntry.inside_water == null && customAnimationEntry.block_detection == null && customAnimationEntry.weather_detection == null && customAnimationEntry.time_detection == null && customAnimationEntry.biome_detection == null) {
            return true;
        }
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            class_746 class_7462 = class_3102.field_1724;
            class_638 class_6382 = class_3102.field_1687;
            if (class_6382 == null) {
                return false;
            }
            String string = BedrockItemAnimationManager.normalizeType(customAnimationEntry.type);
            if ("inwater".equals(string) && customAnimationEntry.in_water == null && !BedrockItemAnimationManager.isInWaterContact((class_1937)class_6382, (class_1657)class_7462)) {
                return false;
            }
            if (customAnimationEntry.in_water != null) {
                bl = BedrockItemAnimationManager.isInWaterContact((class_1937)class_6382, (class_1657)class_7462);
                if (customAnimationEntry.in_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.inside_water != null) {
                bl = class_7462.method_5777(class_3486.field_15517);
                if (Boolean.TRUE.equals(customAnimationEntry.inside_water) && class_13062 != null) {
                    int n = INSIDE_WATER_GRACE_TICKS.getOrDefault(class_13062, 0);
                    if (bl) {
                        n = 4;
                    } else if (n > 0) {
                        --n;
                        bl = true;
                    }
                    INSIDE_WATER_GRACE_TICKS.put(class_13062, n);
                }
                if (customAnimationEntry.inside_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.weather_detection != null && !BedrockItemAnimationManager.matchesWeather(customAnimationEntry.weather_detection, (class_1937)class_6382, (class_1657)class_7462, class_13062)) {
                return false;
            }
            if (customAnimationEntry.time_detection != null && !BedrockItemAnimationManager.matchesTime(customAnimationEntry.time_detection, (class_1937)class_6382)) {
                return false;
            }
            return customAnimationEntry.block_detection != null && !BedrockItemAnimationManager.matchesBlock(customAnimationEntry.block_detection, (class_1937)class_6382, class_7462.method_24515()) ? false : customAnimationEntry.biome_detection == null || BedrockItemAnimationManager.matchesBiome(customAnimationEntry.biome_detection, (class_1937)class_6382, class_7462.method_24515());
        }
        return false;
    }

    private static boolean matchesWorldTriggersStable(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_310 class_3102, class_1306 class_13062) {
        boolean bl = BedrockItemAnimationManager.matchesWorldTriggers(customAnimationEntry, class_3102, class_13062);
        if (customAnimationEntry != null && customAnimationEntry.weather_detection != null) {
            return bl;
        }
        if (class_13062 != null && customAnimationEntry != null && customAnimationEntry.keep_looping != null && !customAnimationEntry.keep_looping.isBlank()) {
            int n = KEEP_LOOP_FALSE_GRACE_TICKS_MAP.getOrDefault(class_13062, 0);
            if (bl) {
                KEEP_LOOP_FALSE_GRACE_TICKS_MAP.put(class_13062, 6);
                return true;
            }
            if (n > 0) {
                KEEP_LOOP_FALSE_GRACE_TICKS_MAP.put(class_13062, n - 1);
                return true;
            }
            KEEP_LOOP_FALSE_GRACE_TICKS_MAP.put(class_13062, 0);
            return false;
        }
        return bl;
    }

    private static boolean isInWaterContact(class_1937 class_19372, class_1657 class_16572) {
        if (class_19372 != null && class_16572 != null) {
            if (!class_16572.method_5799() && !class_16572.method_5777(class_3486.field_15517)) {
                double d = class_16572.method_23317();
                double d2 = class_16572.method_23318();
                double d3 = class_16572.method_23321();
                double[] dArray = new double[]{d2 + 0.05, d2 + 0.6, d2 + 1.1};
                double[] dArray2 = new double[]{d, d - 0.3, d + 0.3};
                double[] dArray3 = new double[]{d3, d3 - 0.3, d3 + 0.3};
                for (double d4 : dArray) {
                    for (double d5 : dArray2) {
                        for (double d6 : dArray3) {
                            if (!class_19372.method_8316(class_2338.method_49637((double)d5, (double)d4, (double)d6)).method_15767(class_3486.field_15517)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean matchesWeather(PunchyTuningConfig.WeatherDetection weatherDetection, class_1937 class_19372, class_1657 class_16572, class_1306 class_13062) {
        if (weatherDetection != null && class_19372 != null) {
            boolean bl = class_19372.method_8419();
            boolean bl2 = class_19372.method_8546();
            if (class_16572 != null) {
                class_2338 class_23382 = class_2338.method_49637((double)class_16572.method_23317(), (double)(class_16572.method_23318() + 1.0), (double)class_16572.method_23321());
                bl = class_19372.method_8419() || class_19372.method_8520(class_23382) || class_19372.method_8520(class_23382.method_10084());
                boolean bl3 = bl2 = class_19372.method_8546() && bl;
            }
            if (weatherDetection.raining != null) {
                boolean bl4 = weatherDetection.raining;
                boolean bl5 = bl;
                if (bl4 && class_13062 != null) {
                    int n = RAIN_GRACE_TICKS.getOrDefault(class_13062, 0);
                    if (bl) {
                        n = 100;
                    } else if (n > 0) {
                        --n;
                        bl5 = true;
                    }
                    RAIN_GRACE_TICKS.put(class_13062, n);
                }
                if (bl4 != bl5) {
                    return false;
                }
            }
            if (weatherDetection.thundering != null && weatherDetection.thundering != bl2) {
                return false;
            }
            if (weatherDetection.clear != null) {
                boolean bl6;
                boolean bl7 = bl6 = !bl && !bl2;
                if (weatherDetection.clear != bl6) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private static boolean matchesTime(PunchyTuningConfig.TimeDetection timeDetection, class_1937 class_19372) {
        if (timeDetection != null && class_19372 != null) {
            int n;
            int n2 = timeDetection.min != null ? timeDetection.min : -1;
            int n3 = n = timeDetection.max != null ? timeDetection.max : -1;
            if (timeDetection.range != null && timeDetection.range.size() >= 2) {
                n2 = timeDetection.range.get(0);
                n = timeDetection.range.get(1);
            }
            if (n2 < 0 && n < 0) {
                return true;
            }
            int n4 = (int)(class_19372.method_8532() % 24000L);
            if (n2 < 0) {
                return n4 <= n;
            }
            if (n < 0) {
                return n4 >= n2;
            }
            return n2 <= n ? n4 >= n2 && n4 <= n : n4 >= n2 || n4 <= n;
        }
        return true;
    }

    private static boolean matchesBlock(PunchyTuningConfig.BlockDetection blockDetection, class_1937 class_19372, class_2338 class_23382) {
        if (blockDetection != null && class_19372 != null && class_23382 != null) {
            List<String> list = blockDetection.blocks;
            if ((list == null || list.isEmpty()) && blockDetection.block != null) {
                list = List.of(blockDetection.block);
            }
            if (list != null && !list.isEmpty()) {
                int n = blockDetection.radius != null ? Math.max(0, blockDetection.radius) : 0;
                int n2 = blockDetection.offsetX != null ? blockDetection.offsetX : 0;
                int n3 = blockDetection.offsetY != null ? blockDetection.offsetY : 0;
                int n4 = blockDetection.offsetZ != null ? blockDetection.offsetZ : 0;
                class_2338 class_23383 = class_23382.method_10069(n2, n3, n4);
                if (n == 0) {
                    return BedrockItemAnimationManager.matchesBlockList(class_19372.method_8320(class_23383), list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!BedrockItemAnimationManager.matchesBlockList(class_19372.method_8320((class_2338)class_23392), list)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean matchesBiome(PunchyTuningConfig.BiomeDetection biomeDetection, class_1937 class_19372, class_2338 class_23382) {
        if (biomeDetection != null && class_19372 != null && class_23382 != null) {
            List<String> list = biomeDetection.biomes;
            if ((list == null || list.isEmpty()) && biomeDetection.biome != null) {
                list = List.of(biomeDetection.biome);
            }
            if (list != null && !list.isEmpty()) {
                int n = biomeDetection.radius != null ? Math.max(0, biomeDetection.radius) : 0;
                int n2 = biomeDetection.offsetX != null ? biomeDetection.offsetX : 0;
                int n3 = biomeDetection.offsetY != null ? biomeDetection.offsetY : 0;
                int n4 = biomeDetection.offsetZ != null ? biomeDetection.offsetZ : 0;
                class_2338 class_23383 = class_23382.method_10069(n2, n3, n4);
                if (n == 0) {
                    return BedrockItemAnimationManager.matchesBiomeList(class_19372, class_23383, list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!BedrockItemAnimationManager.matchesBiomeList(class_19372, (class_2338)class_23392, list)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean matchesBiomeList(class_1937 class_19372, class_2338 class_23382, List<String> list) {
        if (class_19372 != null && class_23382 != null && list != null && !list.isEmpty()) {
            class_6880 class_68802 = class_19372.method_23753(class_23382);
            for (String string : list) {
                if (string == null || string.isBlank()) continue;
                String value = string.trim();
                if (value.startsWith("#")) {
                    class_2960 id = class_2960.method_12829(value.substring(1));
                    class_6862 tag = id == null ? null : class_6862.method_40092(class_7924.field_41236, id);
                    if (tag != null && class_68802.method_40220(tag)) return true;
                } else {
                    class_2960 id = class_2960.method_12829(value);
                    class_5321 key = id == null ? null : class_5321.method_29179(class_7924.field_41236, id);
                    if (key != null && class_68802.method_40225(key)) return true;
                }
            }
            return false;
        }
        return false;
    }

    private static boolean matchesBlockList(class_2680 class_26802, List<String> list) {
        if (class_26802 != null && list != null && !list.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41175.method_10221(class_26802.method_26204());
            String string = class_29602 == null ? null : class_29602.toString();
            for (String string2 : list) {
                class_6862 class_68622;
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim()).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_26802.method_26164(class_68622 = class_6862.method_40092((class_5321)class_7924.field_41254, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    static {
        TYPE_USE_DRINK = "useDrink";
        TYPE_USE_EAT = "useEat";
        HANDLERS = new EnumMap(class_1306.class);
        ACTIVE_CLIPS = new EnumMap(class_1306.class);
        ACTIVE_TYPES = new EnumMap(class_1306.class);
        ACTIVE_ENTRIES = new EnumMap(class_1306.class);
        CLIP_ENTRIES = new EnumMap(class_1306.class);
        KEEP_LOOP_ACTIVE_ENTRY = new EnumMap(class_1306.class);
        KEEP_LOOP_ACTIVE_NAME = new EnumMap(class_1306.class);
        LAST_PLAYING = new EnumMap(class_1306.class);
        SWIM_SUPPRESSION = new EnumMap(class_1306.class);
        RAIN_GRACE_TICKS = new EnumMap(class_1306.class);
        INSIDE_WATER_GRACE_TICKS = new EnumMap(class_1306.class);
        KEEP_LOOP_FALSE_GRACE_TICKS_MAP = new EnumMap(class_1306.class);
        ANIM_MIRROR = new EnumMap(class_1306.class);
        LAST_STACK_KEY = new EnumMap(class_1306.class);
        IDLE_CLIPS = new EnumMap(class_1306.class);
        IDLE_TIMES = new EnumMap(class_1306.class);
        LAST_ATTACK_TRIGGER_EPOCH = new EnumMap(class_1306.class);
        LAST_VISUAL_USE_ITEM_EPOCH = new EnumMap(class_1306.class);
        FORCED_TYPES = new EnumMap(class_1268.class);
        PERSISTENT_ACTIONS = new EnumMap(class_1268.class);
    }

    static final class PersistentAction {
        final String type;
        final Boolean loopOverride;
        final boolean immediate;

        PersistentAction(String string, Boolean bl, boolean bl2) {
            this.type = string;
            this.loopOverride = bl;
            this.immediate = bl2;
        }

        @Override
        public final String toString() {
            return "PersistentAction[" + "type=" + this.type + ", " + "loopOverride=" + this.loopOverride + ", " + "immediate=" + this.immediate + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.type, this.loopOverride, this.immediate);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PersistentAction other)) return false;
            return java.util.Objects.deepEquals(this.type, other.type)
                && java.util.Objects.deepEquals(this.loopOverride, other.loopOverride)
                && java.util.Objects.deepEquals(this.immediate, other.immediate);
        }

        public String type() {
            return this.type;
        }

        public Boolean loopOverride() {
            return this.loopOverride;
        }

        public boolean immediate() {
            return this.immediate;
        }
    }

    static final class ActionState {
        final String type;
        final Boolean loopOverride;
        final boolean immediate;
        static final ActionState EMPTY = new ActionState(null, null, false);

        ActionState(String string, Boolean bl, boolean bl2) {
            this.type = string;
            this.loopOverride = bl;
            this.immediate = bl2;
        }

        @Override
        public final String toString() {
            return "ActionState[" + "type=" + this.type + ", " + "loopOverride=" + this.loopOverride + ", " + "immediate=" + this.immediate + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.type, this.loopOverride, this.immediate);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ActionState other)) return false;
            return java.util.Objects.deepEquals(this.type, other.type)
                && java.util.Objects.deepEquals(this.loopOverride, other.loopOverride)
                && java.util.Objects.deepEquals(this.immediate, other.immediate);
        }

        public String type() {
            return this.type;
        }

        public Boolean loopOverride() {
            return this.loopOverride;
        }

        public boolean immediate() {
            return this.immediate;
        }
    }

    static final class ForcedAction {
        final String type;
        final boolean immediate;

        ForcedAction(String string, boolean bl) {
            this.type = string;
            this.immediate = bl;
        }

        @Override
        public final String toString() {
            return "ForcedAction[" + "type=" + this.type + ", " + "immediate=" + this.immediate + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.type, this.immediate);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ForcedAction other)) return false;
            return java.util.Objects.deepEquals(this.type, other.type)
                && java.util.Objects.deepEquals(this.immediate, other.immediate);
        }

        public String type() {
            return this.type;
        }

        public boolean immediate() {
            return this.immediate;
        }
    }
}

