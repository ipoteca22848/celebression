package ru.method.celebression.punchy.client.animation;

import ru.method.celebression.animation.Animation;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
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
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class ItemAnimationManager {
    private static final EnumMap<class_1306, PoseHandler> HANDLERS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, AnimationClip> ACTIVE_CLIPS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, String> ACTIVE_TYPES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, PunchyTuningConfig.CustomAnimationEntry> ACTIVE_ENTRIES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Boolean> SWIM_SUPPRESSION = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, AnimationClip> IDLE_CLIPS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Float> IDLE_TIMES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1268, ForcedAction> FORCED_TYPES = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, PersistentAction> PERSISTENT_ACTIONS = new EnumMap(class_1268.class);
    private static final float ITEM_BLEND_IN_SECONDS = 0.0f;
    private static final float ITEM_BLEND_TRANSITION_SECONDS = 0.4f;
    private static final float ITEM_BLEND_OUT_SECONDS = 3.0f;
    private static final float ITEM_BLEND_OUT_SLOW = 3.6f;
    private static final float ITEM_BASE_DELTA_SECONDS = 0.05f;

    private ItemAnimationManager() {
    }

    public static void forceTypeOnce(class_1268 class_12682, String string) {
        ItemAnimationManager.forceTypeOnce(class_12682, string, false);
    }

    public static void forceTypeOnce(class_1268 class_12682, String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            if (class_12682 == null) {
                FORCED_TYPES.put(class_1268.field_5808, new ForcedAction(string, bl));
                FORCED_TYPES.put(class_1268.field_5810, new ForcedAction(string, bl));
                BedrockItemAnimationManager.forceTypeOnce(class_1268.field_5808, string, bl);
                BedrockItemAnimationManager.forceTypeOnce(class_1268.field_5810, string, bl);
            } else {
                FORCED_TYPES.put(class_12682, new ForcedAction(string, bl));
                BedrockItemAnimationManager.forceTypeOnce(class_12682, string, bl);
            }
        }
    }

    public static void setPersistentType(class_1268 class_12682, String string, Boolean bl) {
        ItemAnimationManager.setPersistentType(class_12682, string, bl, false);
    }

    public static void setPersistentType(class_1268 class_12682, String string, Boolean bl, boolean bl2) {
        if (class_12682 != null && string != null && !string.isBlank()) {
            PERSISTENT_ACTIONS.put(class_12682, new PersistentAction(string, bl, bl2));
            BedrockItemAnimationManager.setPersistentType(class_12682, string, bl, bl2);
        }
    }

    public static void clearPersistentType(class_1268 class_12682, String string) {
        PersistentAction persistentAction;
        if (class_12682 != null && (persistentAction = PERSISTENT_ACTIONS.get(class_12682)) != null && (string == null || string.isBlank() || string.equals(persistentAction.type))) {
            PERSISTENT_ACTIONS.remove(class_12682);
            BedrockItemAnimationManager.clearPersistentType(class_12682, string);
        }
    }

    public static void clearAllPersistentTypes() {
        PERSISTENT_ACTIONS.clear();
        BedrockItemAnimationManager.clearAllPersistentTypes();
    }

    public static boolean isTypeActive(String string) {
        if (string != null && !string.isBlank()) {
            for (class_1306 class_13062 : class_1306.values()) {
                PoseHandler poseHandler;
                String string2 = ACTIVE_TYPES.get(class_13062);
                if (!string.equals(string2) || (poseHandler = HANDLERS.get(class_13062)) == null || !poseHandler.isPlaying()) continue;
                return true;
            }
            return false;
        }
        return false;
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
            float f = PunchyAnimationManager.ANIMATION_SPEED;
            for (class_1306 class_13063 : class_1306.values()) {
                boolean bl;
                SWIM_SUPPRESSION.put(class_13063, false);
                PoseHandler poseHandler = HANDLERS.computeIfAbsent(class_13063, class_13062 -> new PoseHandler());
                boolean bl2 = true;
                boolean bl3 = false;
                class_1799 class_17992 = ItemAnimationManager.resolveStack(class_3102, class_13063);
                class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13063);
                if (PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12682)) {
                    FORCED_TYPES.remove(class_12682);
                    PERSISTENT_ACTIONS.remove(class_12682);
                    IDLE_CLIPS.remove(class_13063);
                    IDLE_TIMES.remove(class_13063);
                    if (poseHandler.isPlaying()) {
                        ItemAnimationManager.logPlaybackOnce(class_13063, class_17992, ACTIVE_TYPES.get(class_13063), ACTIVE_CLIPS.get(class_13063), ACTIVE_ENTRIES.get(class_13063), null, false, false, "blacklist_stop");
                        poseHandler.setLooping(false);
                        poseHandler.stopImmediateWithBlendOut();
                    }
                    ACTIVE_CLIPS.put(class_13063, null);
                    ACTIVE_TYPES.remove(class_13063);
                    ACTIVE_ENTRIES.remove(class_13063);
                    ModelPartsParticleSystem.resetArm(class_13063);
                    continue;
                }
                if (ItemAnimationManager.hasItemSuppression(class_17992, true)) {
                    SWIM_SUPPRESSION.put(class_13063, true);
                }
                ActionState actionState = ItemAnimationManager.resolveDesiredState(class_3102, class_13063, class_17992);
                String string = actionState.type;
                AnimationClip animationClip = ItemAnimationManager.resolveDesiredClip(class_3102, class_13063, class_17992, string);
                AnimationClip animationClip2 = ItemAnimationManager.resolveDesiredClip(class_3102, class_13063, class_17992, "idle");
                float f2 = ItemAnimationManager.updateIdleTime(class_13063, poseHandler, animationClip2, 0.05f, f);
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = ItemAnimationManager.resolveEntry(class_17992, string, class_3102, class_13063);
                if (customAnimationEntry != null && customAnimationEntry.isJavaBlock != null) {
                    bl3 = customAnimationEntry.isJavaBlock;
                }
                if (customAnimationEntry != null) {
                    poseHandler.setLoopSpeedParams(customAnimationEntry.loop_speed_max, customAnimationEntry.loop_speed_ramp);
                } else {
                    poseHandler.resetLoopSpeedParams();
                }
                if (customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.disable_swim_animation)) {
                    SWIM_SUPPRESSION.put(class_13063, true);
                }
                poseHandler.setSwapTranslationYZ(bl2);
                poseHandler.setJavaBlock(bl3);
                AnimationClip animationClip3 = ACTIVE_CLIPS.get(class_13063);
                String string2 = ACTIVE_TYPES.get(class_13063);
                boolean bl4 = bl = animationClip3 != null && poseHandler.isPlaying() && poseHandler.getCurrentClip() == animationClip3 && !animationClip3.isLoop() && !animationClip3.isHoldOnLastFrame() && !poseHandler.isForceLooping() && (animationClip == null || "idle".equals(string)) && !"falling".equals(string2);
                if (bl) {
                    animationClip = animationClip3;
                }
                if (!class_17992.method_31574(class_1802.field_8378) && ItemAnimationManager.shouldSuppressMeshAnimation(animationClip)) {
                    if (animationClip3 != null) {
                        ItemAnimationManager.logPlaybackOnce(class_13063, class_17992, string2, animationClip3, ACTIVE_ENTRIES.get(class_13063), null, false, false, "mesh_suppressed");
                        poseHandler.setBlendOutDurationNext(3.0f);
                        poseHandler.setBlendOutSlowNext(3.6f);
                        poseHandler.setLooping(false);
                        poseHandler.stopImmediateWithBlendOut();
                        ACTIVE_CLIPS.put(class_13063, null);
                        ACTIVE_TYPES.remove(class_13063);
                    }
                    ModelPartsParticleSystem.clearArmAnimationState(class_13063);
                    continue;
                }
                if (animationClip != animationClip3) {
                    boolean bl5;
                    PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2 = ACTIVE_ENTRIES.get(class_13063);
                    boolean bl6 = bl5 = customAnimationEntry2 != null && Boolean.TRUE.equals(customAnimationEntry2.skip_blend_out);
                    if (animationClip != null) {
                        boolean bl7;
                        poseHandler.setActiveArm(null);
                        boolean shouldLoop = actionState.loopOverride != null ? actionState.loopOverride : ItemAnimationManager.shouldLoop(animationClip, string);
                        poseHandler.setLooping(shouldLoop);
                        float f3 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                        boolean bl8 = poseHandler.isPlaying() && poseHandler.getCurrentClip() != null;
                        boolean bl9 = animationClip == animationClip2;
                        boolean bl10 = bl7 = customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_blend_in);
                        if (bl5) {
                            bl7 = true;
                        }
                        if (bl7) {
                            poseHandler.setBlendInDurationNext(0.0f);
                        }
                        if (actionState.immediate) {
                            if (bl8) {
                                if (!bl7) {
                                    poseHandler.setBlendInDurationNext(0.4f);
                                }
                                if (bl9) {
                                    if (bl7) {
                                        poseHandler.playAtTime(animationClip, f3, true, animationClip.isHoldOnLastFrame(), f2);
                                    } else {
                                        poseHandler.playAtTimeWithBlendFromCurrent(animationClip, f3, animationClip.isHoldOnLastFrame(), f2);
                                    }
                                } else if (bl7) {
                                    poseHandler.play(animationClip, f3, true, animationClip.isHoldOnLastFrame());
                                } else {
                                    poseHandler.playWithBlendFromCurrentDynamic(animationClip, f3, animationClip.isHoldOnLastFrame());
                                }
                            } else {
                                if (!bl7) {
                                    poseHandler.setBlendInDurationNext(0.0f);
                                }
                                if (bl9) {
                                    poseHandler.playAtTime(animationClip, f3, true, animationClip.isHoldOnLastFrame(), f2);
                                } else {
                                    poseHandler.play(animationClip, f3, true, animationClip.isHoldOnLastFrame());
                                }
                            }
                        } else {
                            if (!bl7) {
                                poseHandler.setBlendInDurationNext(bl8 ? 0.4f : 0.0f);
                            }
                            if (bl9) {
                                if (bl7) {
                                    poseHandler.playAtTime(animationClip, f3, true, animationClip.isHoldOnLastFrame(), f2);
                                } else {
                                    poseHandler.playAtTimeWithBlendFromCurrent(animationClip, f3, animationClip.isHoldOnLastFrame(), f2);
                                }
                            } else if (bl7) {
                                poseHandler.play(animationClip, f3, true, animationClip.isHoldOnLastFrame());
                            } else {
                                poseHandler.playWithBlendFromCurrentDynamic(animationClip, f3, animationClip.isHoldOnLastFrame());
                            }
                        }
                        if (!(bl9 || string2 != null && string2.equals(string))) {
                            MovementStateMachine.get().triggerActionAnimation(class_13063);
                        }
                        ACTIVE_TYPES.put(class_13063, string);
                        ACTIVE_ENTRIES.put(class_13063, customAnimationEntry);
                        ItemAnimationManager.logPlaybackOnce(class_13063, class_17992, string, animationClip, customAnimationEntry, PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry), shouldLoop, actionState.immediate, bl8 ? "switch" : "start");
                    } else if (animationClip3 != null) {
                        boolean skipBlendOut = customAnimationEntry2 != null && Boolean.TRUE.equals(customAnimationEntry2.skip_blend_out);
                        ItemAnimationManager.logPlaybackOnce(class_13063, class_17992, string2, animationClip3, customAnimationEntry2, PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry2), false, false, skipBlendOut ? "stop_skip_blend" : "stop_blend_out");
                        if (skipBlendOut) {
                            poseHandler.setBlendOutDurationNext(0.0f);
                        } else {
                            poseHandler.setBlendOutDurationNext(3.0f);
                        }
                        poseHandler.setBlendOutSlowNext(3.6f);
                        poseHandler.setLooping(false);
                        poseHandler.stopImmediateWithBlendOut();
                        ACTIVE_TYPES.remove(class_13063);
                        ACTIVE_ENTRIES.remove(class_13063);
                    }
                    ACTIVE_CLIPS.put(class_13063, animationClip);
                }
                poseHandler.setMirrorX(class_13063 == class_1306.field_6182);
                ItemAnimationManager.syncIdleOverlay(poseHandler, class_13063, animationClip2, f2, ACTIVE_CLIPS.get(class_13063));
                poseHandler.tick(0.05f, f);
                ModelPartsParticleSystem.onHandlerTick(class_3102, class_13063, poseHandler);
            }
        }
    }

    public static void applyToParts(class_1306 class_13062, Map<String, class_630> map, float f) {
        PoseHandler poseHandler;
        if (class_13062 != null && map != null && !map.isEmpty() && (poseHandler = HANDLERS.get(class_13062)) != null) {
            poseHandler.setMirrorX(class_13062 == class_1306.field_6182);
            poseHandler.applyTransformations(map, f, true);
        }
    }

    private static AnimationClip resolveDesiredClip(class_310 class_3102, class_1306 class_13062, class_1799 class_17992, String string) {
        if (string == null) {
            return null;
        }
        if (class_17992 != null && !class_17992.method_7960()) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = ItemAnimationManager.resolveEntry(class_17992, string, class_3102, class_13062);
            String string2 = ItemAnimationManager.resolveEntryName(customAnimationEntry);
            class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13062);
            String string3 = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry);
            if (string2 != null && !string2.isBlank()) {
                Object object;
                PunchyAnimationManager.ClipResolution clipResolution;
                if (class_13062 != null && (clipResolution = PunchyAnimationManager.resolveNamedClipWithSource(string2 + (String)(object = class_13062 == class_1306.field_6182 ? "_left" : "_right"), class_3102, class_12682, string3)) != null && clipResolution.clip() != null) {
                    ItemAnimationManager.logResolutionOnce(class_13062, class_17992, string, string2 + (String)object, string3, customAnimationEntry, clipResolution);
                    return clipResolution.clip();
                }
                object = PunchyAnimationManager.resolveNamedClipWithSource(string2, class_3102, class_12682, string3);
                if (object != null && ((PunchyAnimationManager.ClipResolution)object).clip() != null) {
                    ItemAnimationManager.logResolutionOnce(class_13062, class_17992, string, string2, string3, customAnimationEntry, (PunchyAnimationManager.ClipResolution)object);
                    return ((PunchyAnimationManager.ClipResolution)object).clip();
                }
                return null;
            }
            PunchyAnimationManager.ClipResolution clipResolution = PunchyAnimationManager.resolveTypeFallbackClipWithSource(string, class_3102, class_12682, string3);
            if (clipResolution != null && clipResolution.clip() != null) {
                ItemAnimationManager.logResolutionOnce(class_13062, class_17992, string, string, string3, customAnimationEntry, clipResolution);
                return clipResolution.clip();
            }
            return null;
        }
        return null;
    }

    private static void logResolutionOnce(class_1306 class_13062, class_1799 class_17992, String string, String string2, String string3, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, PunchyAnimationManager.ClipResolution clipResolution) {
    }

    private static void logPlaybackOnce(class_1306 class_13062, class_1799 class_17992, String string, AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, String string2, boolean bl, boolean bl2, String string3) {
    }

    private static float updateIdleTime(class_1306 class_13062, PoseHandler poseHandler, AnimationClip animationClip, float f, float f2) {
        float f3;
        if (animationClip == null) {
            IDLE_CLIPS.remove(class_13062);
            IDLE_TIMES.remove(class_13062);
            return 0.0f;
        }
        AnimationClip animationClip2 = IDLE_CLIPS.put(class_13062, animationClip);
        Float f4 = IDLE_TIMES.get(class_13062);
        float f5 = f3 = f4 == null ? 0.0f : f4.floatValue();
        if (animationClip2 != animationClip) {
            f3 = 0.0f;
        }
        if (poseHandler != null && poseHandler.isPlaying() && poseHandler.getCurrentClip() == animationClip) {
            f3 = poseHandler.getCurrentTime();
        } else if (poseHandler != null && poseHandler.getSecondaryClip() == animationClip) {
            f3 = poseHandler.getSecondaryTime();
        } else {
            float f6 = f * f2 * PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            f3 = ItemAnimationManager.advanceLoopTime(animationClip, f3, f6);
        }
        IDLE_TIMES.put(class_13062, Float.valueOf(f3));
        return f3;
    }

    private static void syncIdleOverlay(PoseHandler poseHandler, class_1306 class_13062, AnimationClip animationClip, float f, AnimationClip animationClip2) {
        if (poseHandler != null) {
            if (animationClip != null && animationClip2 != null && animationClip2 != animationClip) {
                if (poseHandler.getSecondaryClip() != animationClip) {
                    poseHandler.setSecondaryClipAtTime(animationClip, class_13062, PunchyAnimationManager.clipSpeedMultiplier(animationClip), f);
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

    private static ActionState resolveDesiredState(class_310 class_3102, class_1306 class_13062, class_1799 class_17992) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return ActionState.EMPTY;
        }
        if (!ItemAnimationManager.shouldAnimateArm(class_13062)) {
            return ActionState.EMPTY;
        }
        ForcedAction forcedAction = ItemAnimationManager.consumeForcedTypeForArm(class_3102, class_13062);
        if (forcedAction != null) {
            return new ActionState(ItemAnimationManager.resolveTypeWithFallback(class_17992, forcedAction.type), null, forcedAction.immediate);
        }
        PersistentAction persistentAction = ItemAnimationManager.getPersistentForArm(class_3102, class_13062);
        return persistentAction != null ? new ActionState(ItemAnimationManager.resolveTypeWithFallback(class_17992, persistentAction.type), persistentAction.loopOverride, persistentAction.immediate) : new ActionState(ItemAnimationManager.resolveTypeWithFallback(class_17992, "idle"), null, false);
    }

    private static ForcedAction consumeForcedTypeForArm(class_310 class_3102, class_1306 class_13062) {
        class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13062);
        return FORCED_TYPES.remove(class_12682);
    }

    private static PersistentAction getPersistentForArm(class_310 class_3102, class_1306 class_13062) {
        class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13062);
        return PERSISTENT_ACTIONS.get(class_12682);
    }

    private static boolean shouldAnimateArm(class_1306 class_13062) {
        return class_13062 != null;
    }

    private static class_1799 resolveStack(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
            return class_13062 == class_1306.field_6183 ? (bl ? class_3102.field_1724.method_6047() : class_3102.field_1724.method_6079()) : (bl ? class_3102.field_1724.method_6079() : class_3102.field_1724.method_6047());
        }
        return class_1799.field_8037;
    }

    private static boolean shouldLoop(AnimationClip animationClip, String string) {
        if (animationClip == null) {
            return false;
        }
        return string == null ? false : "mining".equals(string) || "useItem".equals(string) || "idle".equals(string);
    }

    private static boolean shouldSuppressMeshAnimation(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (!(animationClip.hasEffectAny("suppress_mesh_animation") || animationClip.hasEffectAny("start_mesh_animation") || animationClip.hasEffectAny("loadstart") || animationClip.hasEffectAny("load_start"))) {
            return false;
        }
        AnimationClip animationClip2 = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip2 != null && animationClip2 == animationClip) {
            float f;
            float f2 = PunchyAnimationManager.POSE_HANDLER.getCurrentTime();
            float f3 = ItemAnimationManager.getLastEffectTime(animationClip, "suppress_mesh_animation", f2);
            return f3 > (f = Math.max(ItemAnimationManager.getLastEffectTime(animationClip, "start_mesh_animation", f2), Math.max(ItemAnimationManager.getLastEffectTime(animationClip, "loadstart", f2), ItemAnimationManager.getLastEffectTime(animationClip, "load_start", f2)))) ? true : f < 0.0f;
        }
        return false;
    }

    private static float getLastEffectTime(AnimationClip animationClip, String string, float f) {
        if (animationClip != null && string != null && !string.isEmpty()) {
            if (animationClip.hasEffect(string)) {
                return Float.POSITIVE_INFINITY;
            }
            float f2 = Float.NEGATIVE_INFINITY;
            for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffectsUpTo(f)) {
                if (!entry.getValue().contains(string)) continue;
                f2 = entry.getKey().floatValue();
            }
            return f2;
        }
        return Float.NEGATIVE_INFINITY;
    }

    private static String resolveTypeWithFallback(class_1799 class_17992, String string) {
        if (string != null && !string.isBlank()) {
            String string2 = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
            if (!ItemAnimationManager.hasEntry(class_17992, string) && !PunchyAnimationManager.hasTypeFallbackClip(string, string2)) {
                return "idle".equals(string) || !ItemAnimationManager.hasEntry(class_17992, "idle") && !PunchyAnimationManager.hasTypeFallbackClip("idle", string2) ? null : "idle";
            }
            return string;
        }
        return null;
    }

    private static PunchyTuningConfig.CustomAnimationEntry resolveEntry(class_1799 class_17992, String string, class_310 class_3102, class_1306 class_13062) {
        if (class_17992 != null && !class_17992.method_7960() && string != null && !string.isBlank()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomItemAnimation(class_17992);
            if (list != null && !list.isEmpty()) {
                String string2 = ItemAnimationManager.normalizeType(string);
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = null;
                int n = -1;
                PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2 = null;
                int n2 = -1;
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry3 : list) {
                    if (customAnimationEntry3 == null || customAnimationEntry3.type == null || !string2.equals(ItemAnimationManager.normalizeType(customAnimationEntry3.type)) || customAnimationEntry3.items != null && !customAnimationEntry3.items.isEmpty() && !ItemAnimationManager.matchesItemList(class_17992, customAnimationEntry3.items) || !ItemAnimationManager.matchesWorldTriggers(customAnimationEntry3, class_3102, class_13062)) continue;
                    int n3 = ItemAnimationManager.triggerSpecificityScore(customAnimationEntry3);
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
                PunchyTuningConfig.CustomAnimationEntry selectedEntry = customAnimationEntry != null ? customAnimationEntry : customAnimationEntry2;
                ItemAnimationManager.logEntrySelectionOnce(class_13062, class_17992, string, list, selectedEntry, customAnimationEntry != null ? "triggered" : "fallback");
                return selectedEntry;
            }
            ItemAnimationManager.logEntrySelectionOnce(class_13062, class_17992, string, list, null, "no_entries");
            return null;
        }
        return null;
    }

    private static void logEntrySelectionOnce(class_1306 class_13062, class_1799 class_17992, String string, List<PunchyTuningConfig.CustomAnimationEntry> list, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, String string2) {
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

    private static boolean hasEntry(class_1799 class_17992, String string) {
        if (class_17992 != null && !class_17992.method_7960() && string != null && !string.isBlank()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomItemAnimation(class_17992);
            if (list != null && !list.isEmpty()) {
                String string2 = ItemAnimationManager.normalizeType(string);
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    String string3;
                    if (customAnimationEntry == null || customAnimationEntry.type == null || !string2.equals(ItemAnimationManager.normalizeType(customAnimationEntry.type)) || (string3 = ItemAnimationManager.resolveEntryName(customAnimationEntry)) == null || string3.isBlank()) continue;
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static String normalizeType(String string) {
        return string == null ? "" : string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
    }

    public static boolean isSwimSuppressed(class_1306 class_13062) {
        return class_13062 == null ? false : Boolean.TRUE.equals(SWIM_SUPPRESSION.get(class_13062));
    }

    private static boolean hasItemSuppression(class_1799 class_17992, boolean bl) {
        if (class_17992 != null && !class_17992.method_7960()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomItemAnimation(class_17992);
            if (list != null && !list.isEmpty()) {
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    if (customAnimationEntry == null || customAnimationEntry.items != null && !customAnimationEntry.items.isEmpty() && !ItemAnimationManager.matchesItemList(class_17992, customAnimationEntry.items) || !bl || !Boolean.TRUE.equals(customAnimationEntry.disable_swim_animation)) continue;
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static int triggerSpecificityScore(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return 0;
        }
        int n = 0;
        if (customAnimationEntry.in_water != null) {
            ++n;
        }
        if (customAnimationEntry.inside_water != null) {
            ++n;
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
            if (customAnimationEntry.in_water != null) {
                bl = ItemAnimationManager.isInWaterContact((class_1937)class_6382, (class_1657)class_7462);
                if (customAnimationEntry.in_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.inside_water != null) {
                bl = class_7462.method_5777(class_3486.field_15517);
                if (customAnimationEntry.inside_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.weather_detection != null && !ItemAnimationManager.matchesWeather(customAnimationEntry.weather_detection, (class_1937)class_6382)) {
                return false;
            }
            if (customAnimationEntry.time_detection != null && !ItemAnimationManager.matchesTime(customAnimationEntry.time_detection, (class_1937)class_6382)) {
                return false;
            }
            return customAnimationEntry.block_detection != null && !ItemAnimationManager.matchesBlock(customAnimationEntry.block_detection, (class_1937)class_6382, class_7462.method_24515()) ? false : customAnimationEntry.biome_detection == null || ItemAnimationManager.matchesBiome(customAnimationEntry.biome_detection, (class_1937)class_6382, class_7462.method_24515());
        }
        return false;
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

    private static boolean matchesWeather(PunchyTuningConfig.WeatherDetection weatherDetection, class_1937 class_19372) {
        if (weatherDetection != null && class_19372 != null) {
            boolean bl = class_19372.method_8419();
            boolean bl2 = class_19372.method_8546();
            if (weatherDetection.raining != null && weatherDetection.raining != bl) {
                return false;
            }
            if (weatherDetection.thundering != null && weatherDetection.thundering != bl2) {
                return false;
            }
            if (weatherDetection.clear != null) {
                boolean bl3;
                boolean bl4 = bl3 = !bl && !bl2;
                if (weatherDetection.clear != bl3) {
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
                    return ItemAnimationManager.matchesBlockList(class_19372.method_8320(class_23383), list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!ItemAnimationManager.matchesBlockList(class_19372.method_8320((class_2338)class_23392), list)) continue;
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
                    return ItemAnimationManager.matchesBiomeList(class_19372, class_23383, list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!ItemAnimationManager.matchesBiomeList(class_19372, (class_2338)class_23392, list)) continue;
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
                class_2960 id = class_2960.method_12829(value.startsWith("#") ? value.substring(1) : value);
                if (id == null) continue;
                if (value.startsWith("#")) {
                    class_6862 tag = class_6862.method_40092(class_7924.field_41236, id);
                    if (class_68802.method_40220(tag)) {
                        return true;
                    }
                } else {
                    class_5321 key = class_5321.method_29179(class_7924.field_41236, id);
                    if (class_68802.method_40225(key)) {
                        return true;
                    }
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

    private static boolean matchesItemList(class_1799 class_17992, List<String> list) {
        if (class_17992 != null && !class_17992.method_7960() && list != null && !list.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.toString();
            for (String string2 : list) {
                class_6862 class_68622;
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim()).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_17992.method_31573(class_68622 = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return false;
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
}

