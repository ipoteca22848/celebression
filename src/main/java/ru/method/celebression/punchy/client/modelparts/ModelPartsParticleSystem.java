package ru.method.celebression.punchy.client.modelparts;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongConsumer;
import net.minecraft.class_1044;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1306;
import net.minecraft.class_1920;
import net.minecraft.class_1921;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_2394;
import net.minecraft.class_2396;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5321;
import net.minecraft.class_5819;
import net.minecraft.class_630;
import net.minecraft.class_638;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_761;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import net.minecraft.class_9851;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.modelparts.util.ModelPartMath;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.MiningActionTracker;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ModelPartAccessor;

public final class ModelPartsParticleSystem {
    private static final float EPS = 1.0E-4f;
    private static final float TWO_PI = (float)Math.PI * 2;
    private static final float EMISSIVE_BLUR_SCALE = 1.08f;
    private static final float EMISSIVE_BLUR_ALPHA = 0.4f;
    private static final float EMISSIVE_BLUR_COLOR = 0.6f;
    private static final long ITEM_PARTICLE_WORLD_RELEASE_DELAY_NANOS = 25000000L;
    private static final long BEDROCK_BONE_PARTICLE_WORLD_RELEASE_DELAY_NANOS = 0L;
    private static final float DEFAULT_WORLD_PREVIEW_BEHIND_LOCAL_BIAS = 0.0015f;
    private static final double BLOCK_FACE_SURFACE_EPSILON = 0.0015;
    private static final double PARTICLE_COLLISION_SURFACE_EPSILON = 0.0025;
    private static final double PARTICLE_COLLISION_REST_EPSILON = 0.0035;
    private static final Vector3f GHOST_ANCHOR_RENDER_OFFSET = new Vector3f(0.0f, 0.0f, 0.0f);
    private static final double PARTICLE_OCCLUSION_ENTRY_MARGIN = 0.22;
    private static final double PARTICLE_OCCLUSION_EPSILON = 0.01;
    private static final float PARTICLE_TEXTURE_ANIMATION_FPS = 12.0f;
    private static final int MAX_PARTICLES_PER_EVENT = 120;
    private static final EnumMap<class_1306, PlaybackState> PLAYBACK = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, List<PendingParticleEvent>> PENDING = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> PENDING_TICK = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> EMITTED_TICK = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, List<ActiveEmitter>> EMITTERS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_EMITTER_RENDER_NANOS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Map<String, PassiveEmitter>> DEFINITION_EMITTERS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_DEFINITION_EMITTER_NANOS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, TriggerRuntimeState> LAST_TRIGGER_STATE = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_USE_TRIGGER_EPOCH = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_ATTACK_TRIGGER_EPOCH = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_MINING_TRIGGER_EPOCH = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, List<RenderParticle>> ACTIVE_RENDER_PARTICLES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, AnimationTriggerWindow> ANIMATION_TRIGGER_WINDOWS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Matrix4f> LAST_SIM_ROOT = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> LAST_SIM_NANOS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, class_243> LAST_CAMERA_POS = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Quaternionf> LAST_CAMERA_ROT = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Quaternionf> LAST_CAMERA_TURN_DELTA_CAMERA_LOCAL = new EnumMap(class_1306.class);
    private static final Map<String, class_2960> PARTICLE_TEXTURE_CACHE = new HashMap<String, class_2960>();
    private static final Map<class_2960, Integer> PARTICLE_ANIM_FRAME_COUNT_CACHE = new HashMap<class_2960, Integer>();
    private static final EnumMap<class_1306, Matrix4f> RENDER_ROOT = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Matrix4f> RENDER_ROOT_INV = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Matrix4f> EXACT_INHAND_GHOST_ROOT = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, String> ACTIVE_DEFINITION_SOURCE = new EnumMap(class_1306.class);
    private static final class_2960 DEFAULT_PARTICLE_TEXTURE = class_2960.method_60655((String)"minecraft", (String)"textures/particle/flame.png");
    private static final Map<ParticleEmissiveKey, class_1921> PARTICLE_EMISSIVE_OVERLAY_TYPES = new HashMap<ParticleEmissiveKey, class_1921>();

    private ModelPartsParticleSystem() {
    }

    public static void resetArm(class_1306 class_13062) {
        if (class_13062 != null) {
            ModelPartsParticleSystem.clearArmControllerState(class_13062);
            ModelPartsParticleSystem.purgeArmParticles(class_13062);
        }
    }

    public static void clearArmAnimationState(class_1306 class_13062) {
        if (class_13062 != null) {
            PLAYBACK.remove(class_13062);
            PENDING.remove(class_13062);
            PENDING_TICK.remove(class_13062);
            EMITTED_TICK.remove(class_13062);
            EMITTERS.remove(class_13062);
            LAST_EMITTER_RENDER_NANOS.remove(class_13062);
            ANIMATION_TRIGGER_WINDOWS.remove(class_13062);
        }
    }

    public static void clearArmControllerState(class_1306 class_13062) {
        if (class_13062 != null) {
            ModelPartsParticleSystem.clearArmAnimationState(class_13062);
            DEFINITION_EMITTERS.remove(class_13062);
            LAST_DEFINITION_EMITTER_NANOS.remove(class_13062);
            ACTIVE_DEFINITION_SOURCE.remove(class_13062);
            LAST_TRIGGER_STATE.remove(class_13062);
            LAST_ATTACK_TRIGGER_EPOCH.remove(class_13062);
            LAST_MINING_TRIGGER_EPOCH.remove(class_13062);
        }
    }

    public static void resetArmPlayback(class_1306 class_13062) {
        ModelPartsParticleSystem.clearArmAnimationState(class_13062);
    }

    public static void purgeArmParticles(class_1306 class_13062) {
        if (class_13062 != null) {
            ACTIVE_RENDER_PARTICLES.remove(class_13062);
            LAST_SIM_ROOT.remove(class_13062);
            LAST_SIM_NANOS.remove(class_13062);
            LAST_CAMERA_POS.remove(class_13062);
            LAST_CAMERA_ROT.remove(class_13062);
            LAST_CAMERA_TURN_DELTA_CAMERA_LOCAL.remove(class_13062);
            RENDER_ROOT.remove(class_13062);
            RENDER_ROOT_INV.remove(class_13062);
            EXACT_INHAND_GHOST_ROOT.remove(class_13062);
        }
    }

    public static void resetAll() {
        for (class_1306 class_13062 : class_1306.values()) {
            ModelPartsParticleSystem.resetArm(class_13062);
        }
    }

    public static void setRenderRoot(class_1306 class_13062, Matrix4f matrix4f) {
        if (class_13062 != null && matrix4f != null) {
            Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f);
            RENDER_ROOT.put(class_13062, matrix4f2);
            RENDER_ROOT_INV.put(class_13062, new Matrix4f((Matrix4fc)matrix4f2).invert());
        }
    }

    public static void setExactInHandGhostRoot(class_1306 class_13062, Matrix4f matrix4f) {
        if (class_13062 != null && matrix4f != null) {
            EXACT_INHAND_GHOST_ROOT.put(class_13062, new Matrix4f((Matrix4fc)matrix4f));
        }
    }

    public static void onHandlerTick(class_310 class_3102, class_1306 class_13063, PoseHandler poseHandler) {
        if (!PunchyConfig.enableParticles()) {
            if (class_13063 != null) {
                ModelPartsParticleSystem.resetArm(class_13063);
            }
        } else if (class_3102 != null && class_3102.field_1687 != null && class_13063 != null && poseHandler != null) {
            long l = class_3102.field_1687.method_8510();
            PlaybackState playbackState = PLAYBACK.computeIfAbsent(class_13063, class_13062 -> new PlaybackState());
            if (playbackState.lastProcessedTick != l) {
                boolean bl;
                playbackState.lastProcessedTick = l;
                AnimationClip animationClip = poseHandler.getCurrentClip();
                boolean bl2 = bl = poseHandler.isPlaying() && animationClip != null;
                if (!bl) {
                    playbackState.lastClip = null;
                    playbackState.lastTime = 0.0f;
                    PENDING.remove(class_13063);
                    PENDING_TICK.remove(class_13063);
                    EMITTERS.remove(class_13063);
                    LAST_EMITTER_RENDER_NANOS.remove(class_13063);
                    ANIMATION_TRIGGER_WINDOWS.remove(class_13063);
                } else {
                    float f = poseHandler.getCurrentTime();
                    ModelPartsParticleSystem.updateAnimationTriggerWindow(class_13063, playbackState.lastClip, playbackState.lastTime, animationClip, f);
                    PENDING.remove(class_13063);
                    PENDING_TICK.remove(class_13063);
                    EMITTERS.remove(class_13063);
                    LAST_EMITTER_RENDER_NANOS.remove(class_13063);
                    playbackState.lastClip = animationClip;
                    playbackState.lastTime = f;
                }
            }
        }
    }

    public static void emitPendingForParts(class_310 class_3102, class_1306 class_13062, Map<String, class_630> map) {
        Long l;
        if (!PunchyConfig.enableParticles()) {
            if (class_13062 != null) {
                ModelPartsParticleSystem.resetArm(class_13062);
            }
        } else if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1724 != null && class_13062 != null && map != null && !map.isEmpty() && (l = PENDING_TICK.get(class_13062)) != null && l.longValue() == class_3102.field_1687.method_8510() && EMITTED_TICK.getOrDefault(class_13062, Long.MIN_VALUE) != l) {
            List<PendingParticleEvent> list = PENDING.get(class_13062);
            if (list != null && !list.isEmpty()) {
                class_746 class_7462 = class_3102.field_1724;
                for (PendingParticleEvent pendingParticleEvent : list) {
                    ModelPartsParticleSystem.spawnEvent(class_3102, class_7462, class_13062, map, pendingParticleEvent);
                }
                list.clear();
                EMITTED_TICK.put(class_13062, l);
            } else {
                EMITTED_TICK.put(class_13062, l);
            }
        }
    }

    public static void emitPendingForRenderBones(class_310 class_3102, class_1306 class_13062, Map<String, Matrix4f> map) {
        if (!PunchyConfig.enableParticles()) {
            if (class_13062 != null) {
                ModelPartsParticleSystem.resetArm(class_13062);
            }
        } else if (ModelPartsParticleSystem.isParticleSuppressedByActiveItemAnimation(class_13062)) {
            ModelPartsParticleSystem.resetArm(class_13062);
        } else if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1724 != null && class_13062 != null && map != null && !map.isEmpty()) {
            class_746 class_7462 = class_3102.field_1724;
            ModelPartsParticleSystem.emitFromActiveEmittersRender(class_3102, class_7462, class_13062, map);
            Long l = PENDING_TICK.get(class_13062);
            if (l != null && l.longValue() == class_3102.field_1687.method_8510() && EMITTED_TICK.getOrDefault(class_13062, Long.MIN_VALUE) != l) {
                List<PendingParticleEvent> list = PENDING.get(class_13062);
                if (list != null && !list.isEmpty()) {
                    for (PendingParticleEvent pendingParticleEvent : list) {
                        ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13062, map, pendingParticleEvent);
                    }
                    list.clear();
                    EMITTED_TICK.put(class_13062, l);
                } else {
                    EMITTED_TICK.put(class_13062, l);
                }
            }
        }
    }

    public static void emitDefinitionForRenderBones(class_310 class_3102, class_1306 class_13063, Map<String, Matrix4f> map, List<String> list) {
        if (!PunchyConfig.enableParticles()) {
            if (class_13063 != null) {
                ModelPartsParticleSystem.resetArm(class_13063);
            }
        } else if (ModelPartsParticleSystem.isParticleSuppressedByActiveItemAnimation(class_13063)) {
            ModelPartsParticleSystem.resetArm(class_13063);
        } else if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1724 != null && class_13063 != null && map != null && !map.isEmpty()) {
            ModelPartsParticleSystem.resetDefinitionEmittersIfSourceChanged(class_13063, "render_bones");
            if (list != null && !list.isEmpty()) {
                long l = System.nanoTime();
                long l2 = LAST_DEFINITION_EMITTER_NANOS.getOrDefault(class_13063, 0L);
                LAST_DEFINITION_EMITTER_NANOS.put(class_13063, l);
                float f = l2 <= 0L ? 0.016666668f : (float)((double)(l - l2) / 1.0E9);
                f = class_3532.method_15363((float)f, (float)0.0f, (float)0.1f);
                Map<String, PassiveEmitter> map2 = DEFINITION_EMITTERS.computeIfAbsent(class_13063, class_13062 -> new HashMap<>());
                HashSet<String> hashSet = new HashSet<String>();
                class_746 class_7462 = class_3102.field_1724;
                long l3 = class_3102.field_1687.method_8510();
                TriggerContext triggerContext = ModelPartsParticleSystem.resolveTriggerContext(class_3102, class_13063);
                Iterator<String> iterator = list.iterator();
                block0: while (true) {
                    if (!iterator.hasNext()) {
                        if (!map2.isEmpty()) {
                            map2.entrySet().removeIf(entry -> !hashSet.contains(entry.getKey()));
                            if (map2.isEmpty()) {
                                DEFINITION_EMITTERS.remove(class_13063);
                            }
                        }
                        return;
                    }
                    String string = iterator.next();
                    if (string == null || string.isBlank()) continue;
                    String string2 = string.trim();
                    hashSet.add(string2);
                    PassiveEmitter passiveEmitter = map2.get(string2);
                    if (passiveEmitter == null) {
                        ParticleScriptSpec particleScriptSpec = ModelPartsParticleSystem.parseParticleSpec(string2);
                        if (particleScriptSpec == null) continue;
                        passiveEmitter = new PassiveEmitter(particleScriptSpec);
                        map2.put(string2, passiveEmitter);
                    }
                    passiveEmitter.ageSeconds += f;
                    if (passiveEmitter.spec.emitForSeconds > 0.0f && passiveEmitter.ageSeconds > passiveEmitter.spec.emitForSeconds + 1.0E-4f || !ModelPartsParticleSystem.matchesDefinitionTriggers(class_3102, passiveEmitter.spec, triggerContext)) continue;
                    boolean hasPulseTriggers = ModelPartsParticleSystem.hasPulseTriggers(passiveEmitter.spec);
                    boolean hasAnyPulseTriggers = ModelPartsParticleSystem.hasAnyPulseTriggers(passiveEmitter.spec);
                    if (hasAnyPulseTriggers) {
                        if (ModelPartsParticleSystem.consumePulseEvents(passiveEmitter, class_13063, triggerContext)) {
                            ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13063, map, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                        }
                        if (hasPulseTriggers) continue;
                    }
                    if (passiveEmitter.spec.emitIntervalSeconds > 0.0f) {
                        if (!ModelPartsParticleSystem.canEmitByInterval(passiveEmitter.spec, passiveEmitter, l)) continue;
                        ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13063, map, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                        continue;
                    }
                    if (passiveEmitter.spec.ratePerSecond > 0.0f) {
                        passiveEmitter.accumulator += (double)(passiveEmitter.spec.ratePerSecond * f);
                        int emissionCount = class_3532.method_15340((int)Math.floor(passiveEmitter.accumulator), 0, 24);
                        if (emissionCount <= 0) continue;
                        passiveEmitter.accumulator -= emissionCount;
                        int emissionIndex = 0;
                        while (true) {
                            if (emissionIndex >= emissionCount) continue block0;
                            ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13063, map, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                            ++emissionIndex;
                        }
                    }
                    if (passiveEmitter.lastTick == l3) continue;
                    passiveEmitter.lastTick = l3;
                    if (!ModelPartsParticleSystem.canEmitByInterval(passiveEmitter.spec, passiveEmitter, l)) continue;
                    ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13063, map, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                }
            }
            DEFINITION_EMITTERS.remove(class_13063);
            LAST_DEFINITION_EMITTER_NANOS.remove(class_13063);
        }
    }

    public static void emitDefinitionForItemRender(class_310 class_3102, class_1306 class_13063, Matrix4f matrix4f, List<String> list) {
        if (!PunchyConfig.enableParticles()) {
            if (class_13063 != null) {
                ModelPartsParticleSystem.resetArm(class_13063);
            }
        } else if (ModelPartsParticleSystem.isParticleSuppressedByActiveItemAnimation(class_13063)) {
            ModelPartsParticleSystem.resetArm(class_13063);
        } else if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1724 != null && class_13063 != null && matrix4f != null) {
            ModelPartsParticleSystem.resetDefinitionEmittersIfSourceChanged(class_13063, "item_render");
            if (list != null && !list.isEmpty()) {
                long l = System.nanoTime();
                long l2 = LAST_DEFINITION_EMITTER_NANOS.getOrDefault(class_13063, 0L);
                LAST_DEFINITION_EMITTER_NANOS.put(class_13063, l);
                float f = l2 <= 0L ? 0.016666668f : (float)((double)(l - l2) / 1.0E9);
                f = class_3532.method_15363((float)f, (float)0.0f, (float)0.1f);
                Map<String, PassiveEmitter> map = DEFINITION_EMITTERS.computeIfAbsent(class_13063, class_13062 -> new HashMap<>());
                HashSet<String> hashSet = new HashSet<String>();
                class_746 class_7462 = class_3102.field_1724;
                long l3 = class_3102.field_1687.method_8510();
                TriggerContext triggerContext = ModelPartsParticleSystem.resolveTriggerContext(class_3102, class_13063);
                Iterator<String> iterator = list.iterator();
                block0: while (true) {
                    if (!iterator.hasNext()) {
                        if (!map.isEmpty()) {
                            map.entrySet().removeIf(entry -> !hashSet.contains(entry.getKey()));
                            if (map.isEmpty()) {
                                DEFINITION_EMITTERS.remove(class_13063);
                            }
                        }
                        return;
                    }
                    String string = iterator.next();
                    if (string == null || string.isBlank()) continue;
                    String string2 = string.trim();
                    hashSet.add(string2);
                    PassiveEmitter passiveEmitter = map.get(string2);
                    if (passiveEmitter == null) {
                        ParticleScriptSpec particleScriptSpec = ModelPartsParticleSystem.parseParticleSpec(string2);
                        if (particleScriptSpec == null) continue;
                        passiveEmitter = new PassiveEmitter(particleScriptSpec);
                        map.put(string2, passiveEmitter);
                    }
                    passiveEmitter.ageSeconds += f;
                    if (passiveEmitter.spec.emitForSeconds > 0.0f && passiveEmitter.ageSeconds > passiveEmitter.spec.emitForSeconds + 1.0E-4f || !ModelPartsParticleSystem.matchesDefinitionTriggers(class_3102, passiveEmitter.spec, triggerContext)) continue;
                    boolean hasPulseTriggers = ModelPartsParticleSystem.hasPulseTriggers(passiveEmitter.spec);
                    boolean hasAnyPulseTriggers = ModelPartsParticleSystem.hasAnyPulseTriggers(passiveEmitter.spec);
                    if (hasAnyPulseTriggers) {
                        if (ModelPartsParticleSystem.consumePulseEvents(passiveEmitter, class_13063, triggerContext)) {
                            ModelPartsParticleSystem.spawnEventForItemRender(class_3102, class_7462, class_13063, matrix4f, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                        }
                        if (hasPulseTriggers) continue;
                    }
                    if (passiveEmitter.spec.emitIntervalSeconds > 0.0f) {
                        if (!ModelPartsParticleSystem.canEmitByInterval(passiveEmitter.spec, passiveEmitter, l)) continue;
                        ModelPartsParticleSystem.spawnEventForItemRender(class_3102, class_7462, class_13063, matrix4f, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                        continue;
                    }
                    if (passiveEmitter.spec.ratePerSecond > 0.0f) {
                        passiveEmitter.accumulator += (double)(passiveEmitter.spec.ratePerSecond * f);
                        int emissionCount = class_3532.method_15340((int)Math.floor(passiveEmitter.accumulator), 0, 24);
                        if (emissionCount <= 0) continue;
                        passiveEmitter.accumulator -= emissionCount;
                        int emissionIndex = 0;
                        while (true) {
                            if (emissionIndex >= emissionCount) continue block0;
                            ModelPartsParticleSystem.spawnEventForItemRender(class_3102, class_7462, class_13063, matrix4f, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                            ++emissionIndex;
                        }
                    }
                    if (passiveEmitter.lastTick == l3) continue;
                    passiveEmitter.lastTick = l3;
                    if (!ModelPartsParticleSystem.canEmitByInterval(passiveEmitter.spec, passiveEmitter, l)) continue;
                    ModelPartsParticleSystem.spawnEventForItemRender(class_3102, class_7462, class_13063, matrix4f, new PendingParticleEvent(passiveEmitter.spec, -1.0f, 1.0f));
                }
            }
            DEFINITION_EMITTERS.remove(class_13063);
            LAST_DEFINITION_EMITTER_NANOS.remove(class_13063);
        }
    }

    private static void resetDefinitionEmittersIfSourceChanged(class_1306 class_13062, String string) {
        if (class_13062 != null && string != null) {
            String string2 = ACTIVE_DEFINITION_SOURCE.get(class_13062);
            if (string2 != null && !string2.equals(string)) {
                DEFINITION_EMITTERS.remove(class_13062);
                LAST_DEFINITION_EMITTER_NANOS.remove(class_13062);
                ModelPartsParticleSystem.purgeArmParticles(class_13062);
            }
            ACTIVE_DEFINITION_SOURCE.put(class_13062, string);
        }
    }

    private static TriggerContext resolveTriggerContext(class_310 class_3102, class_1306 class_13063) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1687 != null && class_13063 != null) {
            long l;
            long l2;
            class_746 class_7462 = class_3102.field_1724;
            TriggerRuntimeState triggerRuntimeState = LAST_TRIGGER_STATE.computeIfAbsent(class_13063, class_13062 -> new TriggerRuntimeState());
            boolean bl = class_7462.method_24828();
            class_243 class_2432 = class_7462.method_18798();
            double d = class_2432.field_1352 * class_2432.field_1352 + class_2432.field_1350 * class_2432.field_1350;
            boolean bl2 = d > 1.0E-4;
            boolean bl3 = class_3102.field_1690 != null && (class_3102.field_1690.field_1894.method_1434() || class_3102.field_1690.field_1881.method_1434() || class_3102.field_1690.field_1913.method_1434() || class_3102.field_1690.field_1849.method_1434());
            boolean bl4 = class_7462.method_5681() || class_7462.method_5799();
            boolean bl5 = class_7462.method_5624() && (bl2 || bl3);
            boolean bl6 = (bl2 || bl3) && bl && !bl5 && !bl4;
            float f = Math.max(0.0f, class_7462.field_6017);
            boolean bl7 = !bl && class_2432.field_1351 < -0.03 && f > 0.0f;
            boolean bl8 = class_3102.field_1690 != null && class_3102.field_1690.field_1886.method_1434();
            boolean bl9 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            class_239 class_2392 = class_3102.field_1765;
            boolean bl10 = class_2392 != null && class_2392.method_17783() == class_239.class_240.field_1332;
            boolean bl11 = class_13063 == class_7462.method_6068();
            class_1268 class_12682 = ModelPartsParticleSystem.resolveHand(class_7462, class_13063);
            boolean bl12 = class_7462.method_6115() && bl9;
            class_1268 class_12683 = bl12 ? class_7462.method_6058() : null;
            boolean bl13 = bl12 && class_12683 == class_12682;
            UseActionTracker.Trigger trigger = UseActionTracker.peekTrigger();
            long l3 = UseActionTracker.peekEpoch();
            class_1268 class_12684 = trigger != null ? trigger.hand() : UseActionTracker.peekLastHand();
            boolean bl14 = class_12684 == class_12682;
            class_1268 class_12685 = AttackActionTracker.peekHand();
            long l4 = AttackActionTracker.peekEpoch();
            long l5 = MiningActionTracker.peekEpoch();
            boolean bl15 = class_12685 == null ? bl11 : class_12685 == class_12682;
            boolean bl16 = bl11 && MiningActionTracker.isMiningActive() && bl10;
            boolean bl17 = false;
            boolean bl18 = false;
            boolean bl19 = false;
            boolean bl20 = false;
            boolean bl21 = false;
            boolean bl22 = false;
            if (bl15 && l4 > (l2 = LAST_ATTACK_TRIGGER_EPOCH.getOrDefault(class_13063, 0L).longValue())) {
                bl22 = true;
                LAST_ATTACK_TRIGGER_EPOCH.put(class_13063, l4);
            }
            boolean bl23 = false;
            if (bl11 && l5 > (l = LAST_MINING_TRIGGER_EPOCH.getOrDefault(class_13063, 0L).longValue())) {
                bl23 = true;
                LAST_MINING_TRIGGER_EPOCH.put(class_13063, l5);
            }
            if (triggerRuntimeState.initialized) {
                long l6;
                boolean bl24;
                boolean bl25;
                boolean bl26;
                boolean bl27 = bl26 = !bl && triggerRuntimeState.lastOnGround && (class_2432.field_1351 > 0.04 || class_3102.field_1690 != null && class_3102.field_1690.field_1903.method_1434());
                if (bl26) {
                    ++triggerRuntimeState.jumpEventId;
                    triggerRuntimeState.jumpGraceTicks = 2;
                }
                if (triggerRuntimeState.jumpGraceTicks > 0) {
                    bl17 = true;
                    --triggerRuntimeState.jumpGraceTicks;
                }
                boolean bl28 = bl25 = bl && !triggerRuntimeState.lastOnGround;
                if (bl25) {
                    ++triggerRuntimeState.landingEventId;
                    triggerRuntimeState.landingGraceTicks = 2;
                }
                if (bl && triggerRuntimeState.landingGraceTicks > 0) {
                    bl18 = true;
                    --triggerRuntimeState.landingGraceTicks;
                } else if (!bl) {
                    triggerRuntimeState.landingGraceTicks = 0;
                }
                boolean bl29 = bl24 = bl15 && (class_7462.field_6252 && !triggerRuntimeState.lastSwinging || bl8 && !triggerRuntimeState.lastAttackKeyDown);
                if (bl24 || bl22) {
                    ++triggerRuntimeState.attackEventId;
                    triggerRuntimeState.attackGraceTicks = 2;
                }
                if (triggerRuntimeState.attackGraceTicks > 0) {
                    bl19 = true;
                    --triggerRuntimeState.attackGraceTicks;
                }
                if (bl23) {
                    ++triggerRuntimeState.miningEventId;
                    triggerRuntimeState.miningGraceTicks = 2;
                }
                if (triggerRuntimeState.miningGraceTicks > 0) {
                    bl20 = true;
                    --triggerRuntimeState.miningGraceTicks;
                }
                if (bl14 && l3 > (l6 = LAST_USE_TRIGGER_EPOCH.getOrDefault(class_13063, 0L).longValue())) {
                    bl21 = true;
                    LAST_USE_TRIGGER_EPOCH.put(class_13063, l3);
                }
            } else {
                triggerRuntimeState.initialized = true;
                if (bl22) {
                    ++triggerRuntimeState.attackEventId;
                    triggerRuntimeState.attackGraceTicks = 1;
                    bl19 = true;
                }
                if (bl23) {
                    ++triggerRuntimeState.miningEventId;
                    triggerRuntimeState.miningGraceTicks = 1;
                    bl20 = true;
                }
                if (bl14 && l3 > (l = LAST_USE_TRIGGER_EPOCH.getOrDefault(class_13063, 0L).longValue())) {
                    bl21 = true;
                    LAST_USE_TRIGGER_EPOCH.put(class_13063, l3);
                }
            }
            triggerRuntimeState.lastOnGround = bl;
            triggerRuntimeState.lastAttackKeyDown = bl8;
            triggerRuntimeState.lastSwinging = class_7462.field_6252;
            bl16 = bl16 || bl20;
            bl13 = bl13 || bl21;
            long l7 = bl21 ? l3 : 0L;
            return new TriggerContext(bl17, bl18, bl6, bl5, bl4, bl7, f, bl19, bl13, bl16, triggerRuntimeState.jumpEventId, triggerRuntimeState.landingEventId, triggerRuntimeState.attackEventId, triggerRuntimeState.miningEventId, l7);
        }
        return TriggerContext.EMPTY;
    }

    private static boolean isDamageableAttackTarget(class_239 class_2392) {
        if (class_2392 instanceof class_3966) {
            class_3966 class_39662 = (class_3966)class_2392;
            class_1297 class_12972 = class_39662.method_17782();
            if (class_12972 == null) {
                return false;
            }
            if (!class_12972.method_5805() || class_12972.method_7325()) {
                return false;
            }
            return !class_12972.method_5732() ? false : !class_12972.method_5655();
        }
        return false;
    }

    private static class_1268 resolveHand(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            if (class_13062 == class_1306.field_6183) {
                return bl ? class_1268.field_5808 : class_1268.field_5810;
            }
            return bl ? class_1268.field_5810 : class_1268.field_5808;
        }
        return class_1268.field_5808;
    }

    private static boolean isParticleSuppressedByActiveItemAnimation(class_1306 class_13062) {
        if (class_13062 == null) {
            return false;
        }
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = BedrockItemAnimationManager.getActiveEntry(class_13062);
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.disable_all_particles);
    }

    private static boolean matchesDefinitionTriggers(class_310 class_3102, ParticleScriptSpec particleScriptSpec, TriggerContext triggerContext) {
        if (particleScriptSpec != null && triggerContext != null && ModelPartsParticleSystem.hasDefinitionTriggers(particleScriptSpec)) {
            MiningActionTracker.Target target = ModelPartsParticleSystem.resolveMiningTarget(class_3102, particleScriptSpec);
            if (particleScriptSpec.triggerModeAny) {
                boolean bl = false;
                boolean bl2 = false;
                if (particleScriptSpec.triggerJump) {
                    bl = true;
                    bl2 |= triggerContext.jump;
                }
                if (particleScriptSpec.triggerLanding) {
                    bl = true;
                    bl2 |= triggerContext.landing;
                }
                if (particleScriptSpec.triggerWalking) {
                    bl = true;
                    bl2 |= triggerContext.walking;
                }
                if (particleScriptSpec.triggerSprinting) {
                    bl = true;
                    bl2 |= triggerContext.sprinting;
                }
                if (particleScriptSpec.triggerSwimming) {
                    bl = true;
                    bl2 |= triggerContext.swimming;
                }
                if (particleScriptSpec.triggerFalling) {
                    bl = true;
                    bl2 |= triggerContext.falling && triggerContext.fallDistance + 1.0E-4f >= particleScriptSpec.triggerFallingMinBlocks;
                }
                if (particleScriptSpec.triggerAttacking) {
                    bl = true;
                    bl2 |= triggerContext.attacking;
                }
                if (particleScriptSpec.triggerUseItem) {
                    bl = true;
                    bl2 |= triggerContext.useItem;
                }
                if (particleScriptSpec.triggerMining) {
                    bl = true;
                    bl2 |= triggerContext.mining;
                }
                if (particleScriptSpec.triggerBiomeNames != null && !particleScriptSpec.triggerBiomeNames.isEmpty()) {
                    bl = true;
                    bl2 |= ModelPartsParticleSystem.matchesBiomeNames(class_3102, particleScriptSpec.triggerBiomeNames);
                }
                if (ModelPartsParticleSystem.hasMiningTargetFilter(particleScriptSpec)) {
                    if (!bl) {
                        return ModelPartsParticleSystem.matchesMiningTarget(particleScriptSpec, target);
                    }
                    if (!ModelPartsParticleSystem.matchesMiningTarget(particleScriptSpec, target)) {
                        return false;
                    }
                }
                return !bl || bl2;
            }
            if (particleScriptSpec.triggerJump && !triggerContext.jump) {
                return false;
            }
            if (particleScriptSpec.triggerLanding && !triggerContext.landing) {
                return false;
            }
            if (particleScriptSpec.triggerWalking && !triggerContext.walking) {
                return false;
            }
            if (particleScriptSpec.triggerSprinting && !triggerContext.sprinting) {
                return false;
            }
            if (particleScriptSpec.triggerSwimming && !triggerContext.swimming) {
                return false;
            }
            if (particleScriptSpec.triggerFalling) {
                if (!triggerContext.falling) {
                    return false;
                }
                if (triggerContext.fallDistance + 1.0E-4f < particleScriptSpec.triggerFallingMinBlocks) {
                    return false;
                }
            }
            if (particleScriptSpec.triggerAttacking && !triggerContext.attacking) {
                return false;
            }
            if (particleScriptSpec.triggerUseItem && !triggerContext.useItem) {
                return false;
            }
            if (particleScriptSpec.triggerMining && !triggerContext.mining) {
                return false;
            }
            return particleScriptSpec.triggerBiomeNames != null && !particleScriptSpec.triggerBiomeNames.isEmpty() && !ModelPartsParticleSystem.matchesBiomeNames(class_3102, particleScriptSpec.triggerBiomeNames) ? false : !ModelPartsParticleSystem.hasMiningTargetFilter(particleScriptSpec) || ModelPartsParticleSystem.matchesMiningTarget(particleScriptSpec, target);
        }
        return true;
    }

    private static boolean matchesBiomeNames(class_310 class_3102, Set<String> set) {
        if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1724 != null && set != null && !set.isEmpty()) {
            class_638 class_6382 = class_3102.field_1687;
            class_2338 class_23382 = class_3102.field_1724.method_24515();
            class_6880 class_68802 = class_6382.method_23753(class_23382);
            for (String string : set) {
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

    private static MiningActionTracker.Target resolveMiningTarget(class_310 class_3102, ParticleScriptSpec particleScriptSpec) {
        if (!ModelPartsParticleSystem.hasMiningTargetFilter(particleScriptSpec)) {
            return null;
        }
        MiningActionTracker.Target target = MiningActionTracker.peekTarget();
        if (target != null) {
            return target;
        }
        target = MiningActionTracker.peekRecentTarget(250000000L);
        if (target != null) {
            return target;
        }
        if (ModelPartsParticleSystem.isAnyBlockFaceTarget(particleScriptSpec) && class_3102 != null && class_3102.field_1687 != null && class_3102.field_1765 instanceof class_3965 blockHit) {
            class_2338 blockPos = blockHit.method_17777();
            class_2680 blockState = class_3102.field_1687.method_8320(blockPos);
            return blockState != null && !blockState.method_26215() ? new MiningActionTracker.Target(blockPos, blockHit.method_17780(), blockState, blockHit.method_17784()) : null;
        }
        return null;
    }

    private static boolean hasMiningTargetFilter(ParticleScriptSpec particleScriptSpec) {
        return particleScriptSpec == null ? false : ModelPartsParticleSystem.isAnyBlockFaceTarget(particleScriptSpec) || particleScriptSpec.triggerBlockIds != null && !particleScriptSpec.triggerBlockIds.isEmpty() || particleScriptSpec.triggerBlockState != null && !particleScriptSpec.triggerBlockState.isEmpty();
    }

    private static boolean matchesMiningTarget(ParticleScriptSpec particleScriptSpec, MiningActionTracker.Target target) {
        if (!ModelPartsParticleSystem.hasMiningTargetFilter(particleScriptSpec)) {
            return true;
        }
        if (target != null && target.state() != null && !target.state().method_26215()) {
            return particleScriptSpec.triggerBlockIds != null && !particleScriptSpec.triggerBlockIds.isEmpty() && !ModelPartsParticleSystem.matchesBlockSelectors(target.state(), particleScriptSpec.triggerBlockIds) ? false : particleScriptSpec.triggerBlockState == null || particleScriptSpec.triggerBlockState.isEmpty() || ModelPartsParticleSystem.matchesRequiredBlockState(target.state(), particleScriptSpec.triggerBlockState);
        }
        return false;
    }

    private static boolean matchesBlockSelectors(class_2680 class_26802, Set<String> set) {
        if (class_26802 != null && set != null && !set.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41175.method_10221(class_26802.method_26204());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.toString();
            for (String string2 : set) {
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim().toLowerCase(Locale.ROOT)).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_26802.method_26164(class_6862.method_40092((class_5321)class_7924.field_41254, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean matchesRequiredBlockState(class_2680 class_26802, Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            if (class_26802 == null) {
                return false;
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String string = entry.getKey();
                String string2 = entry.getValue();
                if (string == null || string.isBlank() || string2 == null || string2.isBlank()) continue;
                class_2769<?> class_27692 = ModelPartsParticleSystem.findBlockProperty(class_26802, string.trim());
                if (class_27692 == null) {
                    return false;
                }
                String string3 = String.valueOf(class_26802.method_11654(class_27692));
                if (string2.trim().equalsIgnoreCase(string3)) continue;
                return false;
            }
            return true;
        }
        return true;
    }

    private static class_2769<?> findBlockProperty(class_2680 class_26802, String string) {
        if (class_26802 != null && string != null && !string.isBlank()) {
            for (class_2769 class_27692 : class_26802.method_28501()) {
                if (!class_27692.method_11899().equalsIgnoreCase(string)) continue;
                return class_27692;
            }
            return null;
        }
        return null;
    }

    private static boolean hasDefinitionTriggers(ParticleScriptSpec particleScriptSpec) {
        return particleScriptSpec == null ? false : particleScriptSpec.triggerJump || particleScriptSpec.triggerLanding || particleScriptSpec.triggerWalking || particleScriptSpec.triggerSprinting || particleScriptSpec.triggerSwimming || particleScriptSpec.triggerFalling || particleScriptSpec.triggerAttacking || particleScriptSpec.triggerUseItem || particleScriptSpec.triggerMining || particleScriptSpec.hasAnimationTrigger() || particleScriptSpec.triggerBiomeNames != null && !particleScriptSpec.triggerBiomeNames.isEmpty() || ModelPartsParticleSystem.hasMiningTargetFilter(particleScriptSpec);
    }

    private static boolean hasPulseTriggers(ParticleScriptSpec particleScriptSpec) {
        if (particleScriptSpec == null) {
            return false;
        }
        boolean bl = ModelPartsParticleSystem.hasAnyPulseTriggers(particleScriptSpec);
        if (!bl) {
            return false;
        }
        boolean bl2 = particleScriptSpec.triggerWalking || particleScriptSpec.triggerSprinting || particleScriptSpec.triggerSwimming || particleScriptSpec.triggerFalling || particleScriptSpec.triggerMining || particleScriptSpec.triggerBiomeNames != null && !particleScriptSpec.triggerBiomeNames.isEmpty();
        return !bl2;
    }

    private static boolean hasAnyPulseTriggers(ParticleScriptSpec particleScriptSpec) {
        return particleScriptSpec == null ? false : particleScriptSpec.triggerJump || particleScriptSpec.triggerLanding || particleScriptSpec.triggerAttacking || particleScriptSpec.triggerUseItem || particleScriptSpec.triggerMining || particleScriptSpec.hasAnimationTrigger();
    }

    private static boolean consumePulseEvents(PassiveEmitter passiveEmitter, class_1306 class_13062, TriggerContext triggerContext) {
        if (passiveEmitter != null && triggerContext != null && passiveEmitter.spec != null) {
            boolean bl = false;
            if (passiveEmitter.spec.triggerJump && triggerContext.jump && triggerContext.jumpEventId > passiveEmitter.lastJumpEventId) {
                passiveEmitter.lastJumpEventId = triggerContext.jumpEventId;
                bl = true;
            }
            if (passiveEmitter.spec.triggerLanding && triggerContext.landing && triggerContext.landingEventId > passiveEmitter.lastLandingEventId) {
                passiveEmitter.lastLandingEventId = triggerContext.landingEventId;
                bl = true;
            }
            if (passiveEmitter.spec.triggerAttacking && triggerContext.attacking && triggerContext.attackEventId > passiveEmitter.lastAttackEventId) {
                passiveEmitter.lastAttackEventId = triggerContext.attackEventId;
                bl = true;
            }
            if (passiveEmitter.spec.triggerMining && triggerContext.mining && triggerContext.miningEventId > passiveEmitter.lastMiningEventId) {
                passiveEmitter.lastMiningEventId = triggerContext.miningEventId;
                bl = true;
            }
            if (passiveEmitter.spec.triggerUseItem && triggerContext.useItemEventId > passiveEmitter.lastUseItemEventId) {
                passiveEmitter.lastUseItemEventId = triggerContext.useItemEventId;
                bl = true;
            }
            if (ModelPartsParticleSystem.consumeAnimationPulse(passiveEmitter, class_13062)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private static boolean consumeAnimationPulse(PassiveEmitter passiveEmitter, class_1306 class_13062) {
        if (passiveEmitter != null && passiveEmitter.spec != null && class_13062 != null && passiveEmitter.spec.hasAnimationTrigger()) {
            String string;
            AnimationTriggerWindow animationTriggerWindow = ANIMATION_TRIGGER_WINDOWS.get(class_13062);
            if (animationTriggerWindow == null || !animationTriggerWindow.active || animationTriggerWindow.serial <= 0L) {
                return false;
            }
            if (passiveEmitter.lastAnimationWindowSerial == animationTriggerWindow.serial) {
                return false;
            }
            passiveEmitter.lastAnimationWindowSerial = animationTriggerWindow.serial;
            String string2 = passiveEmitter.spec.triggerAnimationName == null ? "" : passiveEmitter.spec.triggerAnimationName.trim();
            String string3 = string = animationTriggerWindow.currentClipName == null ? "" : animationTriggerWindow.currentClipName;
            if (!string2.isBlank() && !string.isBlank() && string2.equalsIgnoreCase(string)) {
                float f = class_3532.method_15363((float)passiveEmitter.spec.triggerAnimationTimecode, (float)0.0f, (float)Math.max(0.0f, animationTriggerWindow.clipLengthSeconds));
                float f2 = animationTriggerWindow.previousTimeSeconds;
                float f3 = animationTriggerWindow.currentTimeSeconds;
                if (animationTriggerWindow.clipChanged) {
                    f2 = -1.0E-4f;
                }
                if (animationTriggerWindow.looping && f3 + 1.0E-4f < f2 && animationTriggerWindow.clipLengthSeconds > 1.0E-4f) {
                    boolean bl = f > f2 + 1.0E-4f && f <= animationTriggerWindow.clipLengthSeconds + 1.0E-4f;
                    boolean bl2 = f >= -1.0E-4f && f <= f3 + 1.0E-4f;
                    return bl || bl2;
                }
                return f > f2 + 1.0E-4f && f <= f3 + 1.0E-4f;
            }
            return false;
        }
        return false;
    }

    private static void updateAnimationTriggerWindow(class_1306 class_13063, AnimationClip animationClip, float f, AnimationClip animationClip2, float f2) {
        if (class_13063 != null && animationClip2 != null) {
            AnimationTriggerWindow animationTriggerWindow = ANIMATION_TRIGGER_WINDOWS.computeIfAbsent(class_13063, class_13062 -> new AnimationTriggerWindow());
            ++animationTriggerWindow.serial;
            animationTriggerWindow.active = true;
            animationTriggerWindow.currentClipName = animationClip2.getName();
            animationTriggerWindow.currentTimeSeconds = f2;
            animationTriggerWindow.previousTimeSeconds = animationClip != animationClip2 ? -1.0E-4f : f;
            animationTriggerWindow.clipLengthSeconds = Math.max(0.0f, animationClip2.getLength());
            animationTriggerWindow.looping = animationClip2.isLoop();
            animationTriggerWindow.clipChanged = animationClip != animationClip2;
        } else {
            ANIMATION_TRIGGER_WINDOWS.remove(class_13063);
        }
    }

    private static boolean canEmitByInterval(ParticleScriptSpec particleScriptSpec, PassiveEmitter passiveEmitter, long l2) {
        return particleScriptSpec != null && passiveEmitter != null ? ModelPartsParticleSystem.canEmitByInterval(particleScriptSpec.emitIntervalSeconds, l2, passiveEmitter.lastEmitNanos, l -> {
            passiveEmitter.lastEmitNanos = l;
        }) : false;
    }

    private static boolean canEmitByInterval(ParticleScriptSpec particleScriptSpec, ActiveEmitter activeEmitter, long l2) {
        return particleScriptSpec != null && activeEmitter != null ? ModelPartsParticleSystem.canEmitByInterval(particleScriptSpec.emitIntervalSeconds, l2, activeEmitter.lastEmitNanos, l -> {
            activeEmitter.lastEmitNanos = l;
        }) : false;
    }

    private static boolean canEmitByInterval(float f, long l, long l2, LongConsumer longConsumer) {
        if (f <= 0.0f) {
            if (longConsumer != null) {
                longConsumer.accept(l);
            }
            return true;
        }
        long l3 = (long)Math.max(1.0f, f * 1.0E9f);
        if (l2 > 0L && l - l2 < l3) {
            return false;
        }
        if (longConsumer != null) {
            longConsumer.accept(l);
        }
        return true;
    }

    private static void emitFromActiveEmittersRender(class_310 class_3102, class_746 class_7462, class_1306 class_13062, Map<String, Matrix4f> map) {
        List<ActiveEmitter> list = EMITTERS.get(class_13062);
        if (list != null && !list.isEmpty() && class_3102 != null && map != null && !map.isEmpty()) {
            long l = System.nanoTime();
            long l2 = LAST_EMITTER_RENDER_NANOS.getOrDefault(class_13062, 0L);
            LAST_EMITTER_RENDER_NANOS.put(class_13062, l);
            float f = l2 <= 0L ? 0.016666668f : (float)((double)(l - l2) / 1.0E9);
            f = class_3532.method_15363((float)f, (float)0.0f, (float)0.1f);
            for (int i = list.size() - 1; i >= 0; --i) {
                ActiveEmitter activeEmitter = list.get(i);
                if (activeEmitter != null && activeEmitter.spec != null) {
                    float f2;
                    activeEmitter.ageSeconds += f;
                    float f3 = Math.max(0.05f, (float)activeEmitter.totalTicks / 20.0f);
                    if (activeEmitter.ageSeconds > f3 + 1.0E-4f) {
                        list.remove(i);
                        continue;
                    }
                    float f4 = class_3532.method_15363((float)(activeEmitter.ageSeconds / f3), (float)0.0f, (float)1.0f);
                    float f5 = f2 = activeEmitter.spec.emitFullAnimation ? 1.0f : ModelPartsParticleSystem.applyEase(activeEmitter.spec.emitEase, f4);
                    if (activeEmitter.spec.emitIntervalSeconds > 0.0f) {
                        if (!ModelPartsParticleSystem.canEmitByInterval(activeEmitter.spec, activeEmitter, l)) continue;
                        ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13062, map, new PendingParticleEvent(activeEmitter.spec, -1.0f, f2));
                        continue;
                    }
                    activeEmitter.accumulator += (double)(activeEmitter.spec.ratePerSecond * f2 * f);
                    int n = (int)Math.floor(activeEmitter.accumulator);
                    if (n <= 0) continue;
                    activeEmitter.accumulator -= (double)n;
                    for (int j = 0; j < n; ++j) {
                        ModelPartsParticleSystem.spawnEventFromRender(class_3102, class_7462, class_13062, map, new PendingParticleEvent(activeEmitter.spec, -1.0f, f2));
                    }
                    continue;
                }
                list.remove(i);
            }
        }
    }

    public static void renderForArm(class_310 class_3102, class_1306 class_13062, class_4597 class_45972, class_4587 class_45872, int n) {
        if (!PunchyConfig.enableParticles()) {
            if (class_13062 != null) {
                ModelPartsParticleSystem.resetArm(class_13062);
            }
        } else if (ModelPartsParticleSystem.isParticleSuppressedByActiveItemAnimation(class_13062)) {
            ModelPartsParticleSystem.resetArm(class_13062);
        } else if (class_3102 != null && class_3102.field_1687 != null && class_13062 != null && class_45972 != null && class_45872 != null) {
            ModelPartsParticleSystem.tickRenderParticles(class_3102, class_13062);
            List<RenderParticle> list = ACTIVE_RENDER_PARTICLES.get(class_13062);
            if (list != null && !list.isEmpty()) {
                class_4184 class_41842;
                class_4184 class_41843 = class_41842 = class_3102.field_1773 == null ? null : class_3102.field_1773.method_19418();
                if (class_41842 != null && class_41842.method_19326() != null && class_41842.method_23767() != null) {
                    Quaternionf quaternionf = new Quaternionf((Quaternionfc)class_41842.method_23767());
                    class_243 class_2432 = class_41842.method_19326();
                    boolean bl = ModelPartsParticleSystem.isShaderPackInUse();
                    Matrix4f matrix4f = RENDER_ROOT.get(class_13062);
                    if (matrix4f == null) {
                        matrix4f = class_45872.method_23760().method_23761();
                    }
                    int n2 = 0;
                    int n3 = 0;
                    int n4 = 0;
                    int n5 = 0;
                    int n6 = 0;
                    int n7 = 0;
                    for (RenderParticle renderParticle : list) {
                        if (renderParticle == null || (renderParticle.brightAtDark || renderParticle.glowTargetMenu) && !PunchyConfig.enableBlurGlowAtDark() || renderParticle.renderInWorldPass && !renderParticle.localSpace) continue;
                        if (renderParticle.localSpace) {
                            if (renderParticle.pendingWorldRelease) {
                                ++n5;
                            } else {
                                ++n6;
                            }
                        } else {
                            ++n7;
                        }
                        float f = renderParticle.totalTicks <= 1 ? 1.0f : class_3532.method_15363((float)(renderParticle.ageTicks / (float)renderParticle.totalTicks), (float)0.0f, (float)1.0f);
                        float f2 = ModelPartsParticleSystem.applyEase(renderParticle.ease, f);
                        float f3 = class_3532.method_16439((float)f2, (float)renderParticle.sizeStart, (float)renderParticle.sizeEnd);
                        float f4 = class_3532.method_16439((float)f2, (float)renderParticle.alphaStart, (float)renderParticle.alphaEnd);
                        if (!(f3 <= 1.0E-4f) && !(f4 <= 0.001f)) {
                            boolean bl2;
                            boolean bl3;
                            Vector3f vector3f;
                            class_4587 class_45873 = new class_4587();
                            if (renderParticle.localSpace) {
                                boolean bl4;
                                vector3f = renderParticle.ignoreItemRotationForLayer ? ModelPartsParticleSystem.renderPointIgnoringPoseRotation(matrix4f, renderParticle.position, quaternionf, bl) : ModelPartsParticleSystem.transformRenderPoint(matrix4f, renderParticle.position);
                                class_45873.method_46416(vector3f.x, vector3f.y, vector3f.z);
                                if (!bl || renderParticle.pendingWorldRelease) {
                                    class_45873.method_22907(quaternionf);
                                }
                                ModelPartsParticleSystem.tryReleaseParticleFromRenderedPose(class_3102, class_13062, renderParticle, matrix4f, class_2432, quaternionf, vector3f, bl);
                                boolean bl5 = bl4 = renderParticle.pendingWorldRelease && (bl || renderParticle.worldReleaseDelayNanos <= 0L) && renderParticle.renderInWorldPass;
                                if (bl4) {
                                    continue;
                                }
                            } else {
                                if (ModelPartsParticleSystem.isParticleOccludedByWorld(class_3102, class_2432, renderParticle.position)) continue;
                                class_243 class_2433 = renderParticle.position.method_1020(class_2432);
                                vector3f = new Vector3f((float)class_2433.field_1352, (float)class_2433.field_1351, (float)class_2433.field_1350);
                                class_45873.method_46416(vector3f.x, vector3f.y, vector3f.z);
                                if (!bl) {
                                    class_45873.method_22907(quaternionf);
                                }
                            }
                            if (!ModelPartsParticleSystem.isFinite(vector3f)) {
                                ++n4;
                                continue;
                            }
                            class_2960 class_29602 = renderParticle.texture != null ? renderParticle.texture : DEFAULT_PARTICLE_TEXTURE;
                            class_2960 class_29603 = ModelPartsParticleSystem.resolveAnimatedParticleTexture(class_3102, renderParticle, class_29602);
                            int n8 = ModelPartsParticleSystem.resolveParticleWorldLight(class_3102, renderParticle, class_13062, class_2432, vector3f, n);
                            float f5 = ModelPartsParticleSystem.resolveBrightAtDarkGlowFactor(class_3102, renderParticle, n8);
                            if ((f4 = ModelPartsParticleSystem.resolveParticleRenderAlpha(class_3102, renderParticle, n8, f4)) <= 0.001f) {
                                ++n4;
                                continue;
                            }
                            int n9 = ModelPartsParticleSystem.resolveParticleLight(class_3102, renderParticle, n8);
                            float f6 = class_3532.method_15363((float)renderParticle.red, (float)0.0f, (float)1.0f);
                            float f7 = class_3532.method_15363((float)renderParticle.green, (float)0.0f, (float)1.0f);
                            float f8 = class_3532.method_15363((float)renderParticle.blue, (float)0.0f, (float)1.0f);
                            boolean bl6 = bl3 = ModelPartsParticleSystem.isParticleEmissiveActive(class_3102, renderParticle) || f5 > 0.001f;
                            if (renderParticle.lockInModel && bl3) {
                                class_1921 class_19212 = ModelPartsParticleSystem.emissiveOverlayRenderType(class_29603, bl, false, renderParticle.emissiveClamp);
                                class_4588 class_45882 = class_45972.getBuffer(class_19212);
                                float f9 = renderParticle.brightAtDark ? Math.min(1.0f, f4 * f5) : f4;
                                ModelPartsParticleSystem.addBillboardQuad(class_45873.method_23760(), class_45882, new Vector3f(0.0f, 0.0f, 0.0f), f3, f9, 0xF000F0, f6, f7, f8);
                                ++n2;
                                continue;
                            }
                            boolean bl7 = bl3 && (renderParticle.lightPercent < 0.0f || renderParticle.lightPercent >= 99.999f) && !renderParticle.lockInModel;
                            boolean bl8 = bl2 = bl7 && !renderParticle.emissiveBlur && renderParticle.emissiveStrength >= 100;
                            if (!bl2) {
                                class_4588 class_45883 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29603));
                                ModelPartsParticleSystem.addBillboardQuad(class_45873.method_23760(), class_45883, new Vector3f(0.0f, 0.0f, 0.0f), f3, f4, n9, f6, f7, f8);
                            }
                            if (bl7) {
                                float f10;
                                float f11;
                                float f12;
                                float f13;
                                float f14;
                                float f15 = ModelPartsParticleSystem.emissionToBaseIntensity(renderParticle.emissiveStrength);
                                float f16 = ModelPartsParticleSystem.emissionToExtraGlow(renderParticle.emissiveStrength);
                                if (f15 > 0.0f || f16 > 0.0f) {
                                    f14 = Math.min(1.0f, f15);
                                    f13 = class_3532.method_15363((float)(f6 * f14), (float)0.0f, (float)1.0f);
                                    f12 = class_3532.method_15363((float)(f7 * f14), (float)0.0f, (float)1.0f);
                                    f11 = class_3532.method_15363((float)(f8 * f14), (float)0.0f, (float)1.0f);
                                    float f17 = f10 = bl2 ? f4 : Math.min(1.0f, f4 * 0.55f);
                                    if (renderParticle.brightAtDark) {
                                        f10 *= f5;
                                    }
                                    class_1921 class_19213 = ModelPartsParticleSystem.emissiveOverlayRenderType(class_29603, bl, renderParticle.emissiveBlur, renderParticle.emissiveClamp);
                                    class_4588 class_45884 = class_45972.getBuffer(class_19213);
                                    ModelPartsParticleSystem.addBillboardQuad(class_45873.method_23760(), class_45884, new Vector3f(0.0f, 0.0f, 0.0f), f3, f10, 0xF000F0, f13, f12, f11);
                                }
                                if (renderParticle.emissiveBlur && f16 > 0.0f) {
                                    f14 = class_3532.method_15363((float)(f6 * Math.min(1.0f, f15)), (float)0.0f, (float)1.0f);
                                    f13 = class_3532.method_15363((float)(f7 * Math.min(1.0f, f15)), (float)0.0f, (float)1.0f);
                                    f12 = class_3532.method_15363((float)(f8 * Math.min(1.0f, f15)), (float)0.0f, (float)1.0f);
                                    f11 = 1.0f + f16 * 1.5f;
                                    f10 = 1.08f + f16 * 0.08f;
                                    float f18 = Math.min(1.0f, 0.6f + f16 * 0.4f);
                                    float f19 = Math.min(1.0f, f4 * 0.4f * f11);
                                    if (renderParticle.brightAtDark) {
                                        f19 *= f5;
                                    }
                                    ModelPartsParticleSystem.addEmissiveBlurHalo(class_45873.method_23760(), class_45972, class_29603, bl, renderParticle.emissiveClamp, f3, f10, f19, class_3532.method_15363((float)(f14 * f18), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f13 * f18), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f12 * f18), (float)0.0f, (float)1.0f));
                                }
                            }
                            ++n2;
                            continue;
                        }
                        ++n3;
                    }
                    if (n2 != 0 || !list.isEmpty()) {
                        // empty if block
                    }
                }
            }
        }
    }

    public static void renderReleasedParticlesInWorld(class_310 class_3102, class_4597 class_45972, class_4587 class_45872) {
        if (class_3102 != null && class_3102.field_1687 != null && class_45972 != null && class_45872 != null) {
            class_4184 class_41842;
            class_4184 class_41843 = class_41842 = class_3102.field_1773 == null ? null : class_3102.field_1773.method_19418();
            if (class_41842 != null && class_41842.method_19326() != null && class_41842.method_23767() != null) {
                Quaternionf quaternionf = new Quaternionf((Quaternionfc)class_41842.method_23767());
                class_243 class_2432 = class_41842.method_19326();
                boolean bl = ModelPartsParticleSystem.isShaderPackInUse();
                int n = 0;
                for (class_1306 class_13062 : class_1306.values()) {
                    List<RenderParticle> list = ACTIVE_RENDER_PARTICLES.get(class_13062);
                    if (list == null || list.isEmpty()) continue;
                    for (RenderParticle renderParticle : list) {
                        float f;
                        float f2;
                        float f3;
                        float f4;
                        float f5;
                        boolean bl2;
                        class_243 class_2433;
                        boolean bl3;
                        if (renderParticle == null || !renderParticle.renderInWorldPass || (renderParticle.brightAtDark || renderParticle.glowTargetMenu) && !PunchyConfig.enableBlurGlowAtDark() || renderParticle.lockInModel) continue;
                        boolean bl4 = bl3 = renderParticle.localSpace && renderParticle.pendingWorldRelease && renderParticle.pendingReleaseSnapshotReady && renderParticle.worldPreviewBehindLocal;
                        if (renderParticle.localSpace && !bl3) continue;
                        float f6 = renderParticle.totalTicks <= 1 ? 1.0f : class_3532.method_15363((float)(renderParticle.ageTicks / (float)renderParticle.totalTicks), (float)0.0f, (float)1.0f);
                        float f7 = ModelPartsParticleSystem.applyEase(renderParticle.ease, f6);
                        float f8 = class_3532.method_16439((float)f7, (float)renderParticle.sizeStart, (float)renderParticle.sizeEnd);
                        float f9 = class_3532.method_16439((float)f7, (float)renderParticle.alphaStart, (float)renderParticle.alphaEnd);
                        if (f8 <= 1.0E-4f || f9 <= 0.001f) continue;
                        class_243 class_2434 = class_2433 = bl3 && renderParticle.pendingReleaseWorldPos != null ? renderParticle.pendingReleaseWorldPos : renderParticle.position;
                        if (bl3) {
                            class_2433 = ModelPartsParticleSystem.pushBehindLocalLayer(class_2432, class_2433, renderParticle.worldPreviewBehindLocalBias, quaternionf);
                        }
                        if (ModelPartsParticleSystem.isParticleOccludedByWorld(class_3102, class_2432, class_2433)) continue;
                        class_243 class_2435 = class_2433.method_1020(class_2432);
                        Vector3f vector3f = new Vector3f((float)class_2435.field_1352, (float)class_2435.field_1351, (float)class_2435.field_1350);
                        if (!ModelPartsParticleSystem.isFinite(vector3f)) continue;
                        class_4587 class_45873 = new class_4587();
                        class_45873.method_23760().method_23761().set((Matrix4fc)class_45872.method_23760().method_23761());
                        class_45873.method_23760().method_23762().set((Matrix3fc)class_45872.method_23760().method_23762());
                        class_45873.method_46416(vector3f.x, vector3f.y, vector3f.z);
                        class_45873.method_22907(quaternionf);
                        class_2960 class_29602 = renderParticle.texture != null ? renderParticle.texture : DEFAULT_PARTICLE_TEXTURE;
                        class_2960 class_29603 = ModelPartsParticleSystem.resolveAnimatedParticleTexture(class_3102, renderParticle, class_29602);
                        int n2 = ModelPartsParticleSystem.resolveParticleWorldLight(class_3102, renderParticle, class_13062, class_2432, vector3f, 0);
                        float f10 = ModelPartsParticleSystem.resolveBrightAtDarkGlowFactor(class_3102, renderParticle, n2);
                        if ((f9 = ModelPartsParticleSystem.resolveParticleRenderAlpha(class_3102, renderParticle, n2, f9)) <= 0.001f) continue;
                        int n3 = ModelPartsParticleSystem.resolveParticleLight(class_3102, renderParticle, n2);
                        float f11 = class_3532.method_15363((float)renderParticle.red, (float)0.0f, (float)1.0f);
                        float f12 = class_3532.method_15363((float)renderParticle.green, (float)0.0f, (float)1.0f);
                        float f13 = class_3532.method_15363((float)renderParticle.blue, (float)0.0f, (float)1.0f);
                        boolean bl5 = (ModelPartsParticleSystem.isParticleEmissiveActive(class_3102, renderParticle) || f10 > 0.001f) && (renderParticle.lightPercent < 0.0f || renderParticle.lightPercent >= 99.999f);
                        boolean bl6 = bl2 = bl5 && !renderParticle.emissiveBlur && renderParticle.emissiveStrength >= 100;
                        if (!bl2) {
                            class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29603));
                            ModelPartsParticleSystem.addBillboardQuad(class_45873.method_23760(), class_45882, new Vector3f(0.0f, 0.0f, 0.0f), f8, f9, n3, f11, f12, f13);
                            ++n;
                        }
                        if (!bl5) continue;
                        float f14 = ModelPartsParticleSystem.emissionToBaseIntensity(renderParticle.emissiveStrength);
                        float f15 = ModelPartsParticleSystem.emissionToExtraGlow(renderParticle.emissiveStrength);
                        if (f14 > 0.0f || f15 > 0.0f) {
                            f5 = Math.min(1.0f, f14);
                            f4 = class_3532.method_15363((float)(f11 * f5), (float)0.0f, (float)1.0f);
                            f3 = class_3532.method_15363((float)(f12 * f5), (float)0.0f, (float)1.0f);
                            f2 = class_3532.method_15363((float)(f13 * f5), (float)0.0f, (float)1.0f);
                            float f16 = f = bl2 ? f9 : Math.min(1.0f, f9 * 0.55f);
                            if (renderParticle.brightAtDark) {
                                f *= f10;
                            }
                            class_1921 class_19212 = ModelPartsParticleSystem.emissiveOverlayRenderType(class_29603, bl, renderParticle.emissiveBlur, renderParticle.emissiveClamp);
                            class_4588 class_45883 = class_45972.getBuffer(class_19212);
                            ModelPartsParticleSystem.addBillboardQuad(class_45873.method_23760(), class_45883, new Vector3f(0.0f, 0.0f, 0.0f), f8, f, 0xF000F0, f4, f3, f2);
                        }
                        if (!renderParticle.emissiveBlur || !(f15 > 0.0f)) continue;
                        f5 = class_3532.method_15363((float)(f11 * Math.min(1.0f, f14)), (float)0.0f, (float)1.0f);
                        f4 = class_3532.method_15363((float)(f12 * Math.min(1.0f, f14)), (float)0.0f, (float)1.0f);
                        f3 = class_3532.method_15363((float)(f13 * Math.min(1.0f, f14)), (float)0.0f, (float)1.0f);
                        f2 = 1.0f + f15 * 1.5f;
                        f = 1.08f + f15 * 0.08f;
                        float f17 = Math.min(1.0f, 0.6f + f15 * 0.4f);
                        float f18 = Math.min(1.0f, f9 * 0.4f * f2);
                        if (renderParticle.brightAtDark) {
                            f18 *= f10;
                        }
                        ModelPartsParticleSystem.addEmissiveBlurHalo(class_45873.method_23760(), class_45972, class_29603, bl, renderParticle.emissiveClamp, f8, f, f18, class_3532.method_15363((float)(f5 * f17), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f4 * f17), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f3 * f17), (float)0.0f, (float)1.0f));
                    }
                }
            }
        }
    }

    private static void collectFromRange(AnimationClip animationClip, float f, float f2, List<PendingParticleEvent> list) {
        if (animationClip != null && list != null && !(f2 + 1.0E-4f < f)) {
            for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffectsUpTo(f2)) {
                float f3 = entry.getKey().floatValue();
                if (f3 < f + 1.0E-4f || f3 > f2 + 1.0E-4f) continue;
                for (String string : entry.getValue()) {
                    ParticleScriptSpec particleScriptSpec = ModelPartsParticleSystem.parseParticleSpec(string);
                    if (particleScriptSpec == null) continue;
                    list.add(new PendingParticleEvent(particleScriptSpec, f3, 1.0f));
                }
            }
        }
    }

    private static void spawnEvent(class_310 class_3102, class_746 class_7462, class_1306 class_13062, Map<String, class_630> map, PendingParticleEvent pendingParticleEvent) {
        float f;
        int n;
        ParticleScriptSpec particleScriptSpec = pendingParticleEvent.spec;
        if (!(particleScriptSpec == null || (n = class_3532.method_15340((int)Math.round((float)particleScriptSpec.count * (f = ModelPartsParticleSystem.applyEase(particleScriptSpec.ease, particleScriptSpec.t) * class_3532.method_15363((float)pendingParticleEvent.scale, (float)0.0f, (float)1.0f))), (int)0, (int)120)) <= 0 || particleScriptSpec.chance < 1.0f && class_3102.field_1687.field_9229.method_43057() > particleScriptSpec.chance)) {
            class_2394 class_23942 = ModelPartsParticleSystem.resolveParticle(particleScriptSpec.typeId);
            class_243 class_2432 = ModelPartsParticleSystem.resolveBoneBase(class_7462, class_13062, map, particleScriptSpec.boneName);
            class_243 class_2433 = ModelPartsParticleSystem.applyBasis(class_7462, class_13062, ModelPartsParticleSystem.effectiveParticleOffset(particleScriptSpec, class_13062, "bone"));
            class_243 class_2434 = ModelPartsParticleSystem.applyBasis(class_7462, class_13062, new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f));
            class_243 class_2435 = new class_243((double)particleScriptSpec.spread.x, (double)particleScriptSpec.spread.y, (double)particleScriptSpec.spread.z);
            for (int i = 0; i < n; ++i) {
                double d = class_2432.field_1352 + class_2433.field_1352 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1352);
                double d2 = class_2432.field_1351 + class_2433.field_1351 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1351);
                double d3 = class_2432.field_1350 + class_2433.field_1350 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1350);
                double d4 = class_2434.field_1352 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1352 * 0.25);
                double d5 = class_2434.field_1351 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1351 * 0.25);
                double d6 = class_2434.field_1350 + ModelPartsParticleSystem.randomSpread(class_3102, class_2435.field_1350 * 0.25);
                class_3102.field_1687.method_8406(class_23942, d, d2, d3, d4, d5, d6);
            }
        }
    }

    private static void spawnEventFromRender(class_310 class_3102, class_746 class_7462, class_1306 class_13063, Map<String, Matrix4f> map, PendingParticleEvent pendingParticleEvent) {
        float f;
        int n;
        ParticleScriptSpec particleScriptSpec = pendingParticleEvent.spec;
        if (particleScriptSpec != null && (n = class_3532.method_15340((int)Math.round((float)particleScriptSpec.count * (f = ModelPartsParticleSystem.applyEase(particleScriptSpec.ease, particleScriptSpec.t) * class_3532.method_15363((float)pendingParticleEvent.scale, (float)0.0f, (float)1.0f))), (int)0, (int)120)) > 0) {
            float f2;
            if (particleScriptSpec.chance < 1.0f && (f2 = class_3102.field_1687.field_9229.method_43057()) > particleScriptSpec.chance) {
                return;
            }
            if (ModelPartsParticleSystem.isWorldBlockFaceTarget(particleScriptSpec)) {
                ModelPartsParticleSystem.spawnEventFromBlockFace(class_3102, class_7462, class_13063, pendingParticleEvent, f, n);
            } else {
                String string = ModelPartsParticleSystem.resolveRenderBoneName(map, particleScriptSpec.boneName, class_13063);
                Matrix4f matrix4f = string == null ? null : map.get(string);
                Matrix4f matrix4f2 = RENDER_ROOT_INV.get(class_13063);
                Matrix4f matrix4f3 = ModelPartsParticleSystem.toLocalBoneTransform(matrix4f, matrix4f2);
                class_243 class_2432 = ModelPartsParticleSystem.resolveHandFallback(class_3102, class_7462, class_13063);
                class_243 class_2433 = ModelPartsParticleSystem.resolveRenderBoneBase(class_3102, class_7462, class_2432, matrix4f, matrix4f3, class_13063, particleScriptSpec.projectionMode);
                if (class_2433 != null) {
                    boolean bl;
                    if (!ModelPartsParticleSystem.isFinite(class_2433)) {
                        class_2433 = class_2432;
                    } else if (class_2432 != null && class_2433.method_1022(class_2432) > 8.0) {
                        class_2433 = class_2432;
                    }
                    Vector3f vector3f = ModelPartsParticleSystem.effectiveParticleOffset(particleScriptSpec, class_13063, particleScriptSpec.offsetSpace);
                    class_243 class_2434 = ModelPartsParticleSystem.applyRenderBasis(class_3102, matrix4f, matrix4f3, vector3f, class_7462, class_13063, particleScriptSpec.offsetSpace, false, class_2432, particleScriptSpec.projectionMode, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                    class_243 class_2435 = ModelPartsParticleSystem.applyRenderBasis(class_3102, matrix4f, matrix4f3, new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f), class_7462, class_13063, particleScriptSpec.velocitySpace, true, class_2432, particleScriptSpec.projectionMode, false);
                    if (class_2434 == null || !ModelPartsParticleSystem.isFinite(class_2434)) {
                        class_2434 = class_243.field_1353;
                    }
                    if (class_2435 == null || !ModelPartsParticleSystem.isFinite(class_2435)) {
                        class_2435 = class_243.field_1353;
                    }
                    class_243 class_2436 = new class_243((double)particleScriptSpec.spread.x, (double)particleScriptSpec.spread.y, (double)particleScriptSpec.spread.z);
                    class_2394 class_23942 = ModelPartsParticleSystem.resolveParticle(particleScriptSpec.typeId);
                    class_2960 class_29602 = ModelPartsParticleSystem.resolveCustomParticleTexture(particleScriptSpec);
                    Vector3f vector3f2 = ModelPartsParticleSystem.resolveCustomParticleTint(particleScriptSpec);
                    int n2 = particleScriptSpec.lockInModel ? Integer.MAX_VALUE : Math.max(1, Math.round(particleScriptSpec.lifetimeSeconds * 20.0f));
                    float f3 = particleScriptSpec.lockInModel ? particleScriptSpec.sizeStart : particleScriptSpec.sizeEnd;
                    float f4 = particleScriptSpec.lockInModel ? particleScriptSpec.alphaStart : particleScriptSpec.alphaEnd;
                    float f5 = particleScriptSpec.lockInModel ? 1.0f : particleScriptSpec.drag;
                    float f6 = particleScriptSpec.lockInModel ? 0.0f : particleScriptSpec.gravity;
                    String string2 = particleScriptSpec.lockInModel ? "linear" : ModelPartsParticleSystem.parseMotionMode(particleScriptSpec.motionMode, "linear");
                    boolean bl2 = !particleScriptSpec.lockInModel && "orbit".equals(string2);
                    float f7 = Math.max(0.0f, particleScriptSpec.orbitRadius);
                    float f8 = f7 * 16.0f;
                    float f9 = particleScriptSpec.orbitSpeed * ((float)Math.PI * 2) / 20.0f;
                    float f10 = (float)Math.toRadians(particleScriptSpec.orbitPhaseDegrees);
                    List list = ACTIVE_RENDER_PARTICLES.computeIfAbsent(class_13063, class_13062 -> new ArrayList());
                    boolean bl3 = bl = matrix4f3 != null;
                    if (!particleScriptSpec.lockInModel || bl) {
                        Vector3f vector3f3 = bl ? matrix4f3.getTranslation(new Vector3f()) : null;
                        Vector3f vector3f4 = null;
                        Vector3f vector3f5 = null;
                        Vector3f vector3f6 = null;
                        Vector3f vector3f7 = null;
                        Vector3f vector3f8 = null;
                        class_243 class_2437 = null;
                        class_243 class_2438 = null;
                        if (bl) {
                            vector3f4 = ModelPartsParticleSystem.applyRenderBasisLocal(matrix4f3, new Vector3f((Vector3fc)vector3f).mul(16.0f), particleScriptSpec.offsetSpace, false, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                            vector3f5 = ModelPartsParticleSystem.applyRenderBasisLocal(matrix4f3, new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f * 16.0f), particleScriptSpec.velocitySpace, true, false);
                            vector3f6 = new Vector3f((Vector3fc)particleScriptSpec.spread).mul(16.0f);
                            vector3f7 = ModelPartsParticleSystem.applyRenderBasisLocal(matrix4f3, new Vector3f((Vector3fc)particleScriptSpec.orbitCenterOffset).mul(16.0f), particleScriptSpec.motionSpace, false, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                            vector3f8 = ModelPartsParticleSystem.applyRenderBasisLocal(matrix4f3, new Vector3f((Vector3fc)particleScriptSpec.orbitAxis), particleScriptSpec.motionSpace, false, false);
                        } else {
                            class_2437 = ModelPartsParticleSystem.applyRenderBasis(class_3102, matrix4f, matrix4f3, particleScriptSpec.orbitCenterOffset, class_7462, class_13063, particleScriptSpec.motionSpace, false, class_2432, particleScriptSpec.projectionMode, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                            class_2438 = ModelPartsParticleSystem.applyRenderBasis(class_3102, matrix4f, matrix4f3, particleScriptSpec.orbitAxis, class_7462, class_13063, particleScriptSpec.motionSpace, false, class_2432, particleScriptSpec.projectionMode, false);
                        }
                        if (class_2437 == null || !ModelPartsParticleSystem.isFinite(class_2437)) {
                            class_2437 = class_243.field_1353;
                        }
                        if (class_2438 == null || !ModelPartsParticleSystem.isFinite(class_2438)) {
                            class_2438 = new class_243(0.0, 1.0, 0.0);
                        }
                        class_243 class_2439 = ModelPartsParticleSystem.normalizeAxis(class_2438);
                        class_243 class_24310 = vector3f8 == null ? new class_243(0.0, 1.0, 0.0) : ModelPartsParticleSystem.normalizeAxis(new class_243((double)vector3f8.x, (double)vector3f8.y, (double)vector3f8.z));
                        String string3 = particleScriptSpec.lockInModel ? particleScriptSpec.lockStableKey : null;
                        for (int i = 0; i < n; ++i) {
                            RenderParticle renderParticle;
                            double d;
                            double d2;
                            double d3;
                            class_243 class_24311;
                            class_243 class_24312;
                            class_243 class_24313;
                            double d4;
                            double d5;
                            double d6;
                            String string4 = string3 == null ? null : string3 + ":" + i;
                            float f11 = f10 + (n > 1 ? (float)Math.PI * 2 * ((float)i / (float)n) : 0.0f);
                            if (bl) {
                                d6 = (double)(vector3f3.x + vector3f4.x + vector3f7.x) + ModelPartsParticleSystem.randomSpread(class_3102, vector3f6.x);
                                d5 = (double)(vector3f3.y + vector3f4.y + vector3f7.y) + ModelPartsParticleSystem.randomSpread(class_3102, vector3f6.y);
                                d4 = (double)(vector3f3.z + vector3f4.z + vector3f7.z) + ModelPartsParticleSystem.randomSpread(class_3102, vector3f6.z);
                                class_24313 = new class_243(d6, d5, d4);
                                class_24312 = bl2 ? ModelPartsParticleSystem.orbitOffset(class_24310, f8, f11) : class_243.field_1353;
                                class_24311 = class_24313.method_1019(class_24312);
                                d3 = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f5.x + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f6.x * 0.25);
                                d2 = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f5.y + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f6.y * 0.25);
                                d = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f5.z + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f6.z * 0.25);
                                renderParticle = new RenderParticle(class_24311, new class_243(d3, d2, d), n2, particleScriptSpec.ease, particleScriptSpec.sizeStart, f3, particleScriptSpec.alphaStart, f4, f5, f6, particleScriptSpec.fullBright, particleScriptSpec.fullDayLight, particleScriptSpec.brightAtDark, particleScriptSpec.glowTargetMenu, particleScriptSpec.lightPercent, particleScriptSpec.emissiveStrength, particleScriptSpec.emissiveBlur, particleScriptSpec.emissiveClamp, class_29602, vector3f2.x, vector3f2.y, vector3f2.z, particleScriptSpec.debug, particleScriptSpec.lockInModel, string4, true, string2, class_24313, class_24310, f8, f11, f9);
                                ModelPartsParticleSystem.addInHandPipelineParticle(list, renderParticle, false, 0L, particleScriptSpec.worldPreviewBehindLocal, particleScriptSpec.worldPreviewBehindLocalBias, particleScriptSpec.ignoreModelRotationForEmissionPoint, particleScriptSpec.syntheticWorldLocal);
                                continue;
                            }
                            d6 = class_2433.field_1352 + class_2434.field_1352 + class_2437.field_1352 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1352);
                            d5 = class_2433.field_1351 + class_2434.field_1351 + class_2437.field_1351 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1351);
                            d4 = class_2433.field_1350 + class_2434.field_1350 + class_2437.field_1350 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1350);
                            class_24313 = new class_243(d6, d5, d4);
                            class_24312 = bl2 ? ModelPartsParticleSystem.orbitOffset(class_2439, f7, f11) : class_243.field_1353;
                            class_24311 = class_24313.method_1019(class_24312);
                            d3 = particleScriptSpec.lockInModel ? 0.0 : class_2435.field_1352 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1352 * 0.25);
                            d2 = particleScriptSpec.lockInModel ? 0.0 : class_2435.field_1351 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1351 * 0.25);
                            d = particleScriptSpec.lockInModel ? 0.0 : class_2435.field_1350 + ModelPartsParticleSystem.randomSpread(class_3102, class_2436.field_1350 * 0.25);
                            renderParticle = new RenderParticle(class_24311, new class_243(d3, d2, d), n2, particleScriptSpec.ease, particleScriptSpec.sizeStart, f3, particleScriptSpec.alphaStart, f4, f5, f6, particleScriptSpec.fullBright, particleScriptSpec.fullDayLight, particleScriptSpec.brightAtDark, particleScriptSpec.glowTargetMenu, particleScriptSpec.lightPercent, particleScriptSpec.emissiveStrength, particleScriptSpec.emissiveBlur, particleScriptSpec.emissiveClamp, class_29602, vector3f2.x, vector3f2.y, vector3f2.z, false, particleScriptSpec.lockInModel, string4, false, string2, class_24313, class_2439, f7, f11, f9);
                            ModelPartsParticleSystem.addInHandPipelineParticle(list, renderParticle, false, 0L, particleScriptSpec.worldPreviewBehindLocal, particleScriptSpec.worldPreviewBehindLocalBias, particleScriptSpec.ignoreModelRotationForEmissionPoint, particleScriptSpec.syntheticWorldLocal);
                        }
                    }
                }
            }
        }
    }

    private static void spawnEventForItemRender(class_310 class_3102, class_746 class_7462, class_1306 class_13063, Matrix4f matrix4f, PendingParticleEvent pendingParticleEvent) {
        float f;
        int n;
        ParticleScriptSpec particleScriptSpec = pendingParticleEvent.spec;
        if (!(particleScriptSpec == null || class_3102 == null || class_13063 == null || matrix4f == null || (n = class_3532.method_15340((int)Math.round((float)particleScriptSpec.count * (f = ModelPartsParticleSystem.applyEase(particleScriptSpec.ease, particleScriptSpec.t) * class_3532.method_15363((float)pendingParticleEvent.scale, (float)0.0f, (float)1.0f))), (int)0, (int)120)) <= 0 || particleScriptSpec.chance < 1.0f && class_3102.field_1687 != null && class_3102.field_1687.field_9229.method_43057() > particleScriptSpec.chance)) {
            if (ModelPartsParticleSystem.isWorldBlockFaceTarget(particleScriptSpec)) {
                ModelPartsParticleSystem.spawnEventFromBlockFace(class_3102, class_7462, class_13063, pendingParticleEvent, f, n);
            } else if (ModelPartsParticleSystem.isHeldBlockFaceTarget(particleScriptSpec)) {
                ModelPartsParticleSystem.spawnEventForHeldItemBlockFace(class_3102, class_7462, class_13063, matrix4f, pendingParticleEvent, f, n);
            } else {
                class_243 class_2432 = ModelPartsParticleSystem.resolveItemTargetLocalBase(particleScriptSpec);
                if (class_2432 == null) {
                    class_2432 = class_243.field_1353;
                }
                Vector3f vector3f = ModelPartsParticleSystem.resolveItemLocalVector(ModelPartsParticleSystem.effectiveParticleOffset(particleScriptSpec, class_13063, particleScriptSpec.offsetSpace), particleScriptSpec.offsetSpace, matrix4f, class_7462, class_13063, false, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                Vector3f vector3f2 = ModelPartsParticleSystem.resolveItemLocalVector(new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f), particleScriptSpec.velocitySpace, matrix4f, class_7462, class_13063, true, false);
                Vector3f vector3f3 = new Vector3f((Vector3fc)particleScriptSpec.spread);
                class_2960 class_29602 = ModelPartsParticleSystem.resolveCustomParticleTexture(particleScriptSpec);
                Vector3f vector3f4 = ModelPartsParticleSystem.resolveCustomParticleTint(particleScriptSpec);
                int n2 = particleScriptSpec.lockInModel ? Integer.MAX_VALUE : Math.max(1, Math.round(particleScriptSpec.lifetimeSeconds * 20.0f));
                float f2 = particleScriptSpec.lockInModel ? particleScriptSpec.sizeStart : particleScriptSpec.sizeEnd;
                float f3 = particleScriptSpec.lockInModel ? particleScriptSpec.alphaStart : particleScriptSpec.alphaEnd;
                float f4 = particleScriptSpec.lockInModel ? 1.0f : particleScriptSpec.drag;
                float f5 = particleScriptSpec.lockInModel ? 0.0f : particleScriptSpec.gravity;
                String string = particleScriptSpec.lockInModel ? "linear" : ModelPartsParticleSystem.parseMotionMode(particleScriptSpec.motionMode, "linear");
                boolean bl = !particleScriptSpec.lockInModel && "orbit".equals(string);
                float f6 = Math.max(0.0f, particleScriptSpec.orbitRadius);
                float f7 = particleScriptSpec.orbitSpeed * ((float)Math.PI * 2) / 20.0f;
                float f8 = (float)Math.toRadians(particleScriptSpec.orbitPhaseDegrees);
                Vector3f vector3f5 = ModelPartsParticleSystem.resolveItemLocalVector(particleScriptSpec.orbitCenterOffset, particleScriptSpec.motionSpace, matrix4f, class_7462, class_13063, false, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                Vector3f vector3f6 = ModelPartsParticleSystem.resolveItemLocalVector(particleScriptSpec.orbitAxis, particleScriptSpec.motionSpace, matrix4f, class_7462, class_13063, false, false);
                class_243 class_2433 = ModelPartsParticleSystem.normalizeAxis(new class_243((double)vector3f6.x, (double)vector3f6.y, (double)vector3f6.z));
                List list = ACTIVE_RENDER_PARTICLES.computeIfAbsent(class_13063, class_13062 -> new ArrayList());
                String string2 = particleScriptSpec.lockInModel ? particleScriptSpec.lockStableKey : null;
                for (int i = 0; i < n; ++i) {
                    String string3 = string2 == null ? null : string2 + ":" + i;
                    float f9 = f8 + (n > 1 ? (float)Math.PI * 2 * ((float)i / (float)n) : 0.0f);
                    double d = class_2432.field_1352 + (double)vector3f.x + (double)vector3f5.x + ModelPartsParticleSystem.randomSpread(class_3102, vector3f3.x);
                    double d2 = class_2432.field_1351 + (double)vector3f.y + (double)vector3f5.y + ModelPartsParticleSystem.randomSpread(class_3102, vector3f3.y);
                    double d3 = class_2432.field_1350 + (double)vector3f.z + (double)vector3f5.z + ModelPartsParticleSystem.randomSpread(class_3102, vector3f3.z);
                    class_243 class_2434 = new class_243(d, d2, d3);
                    class_243 class_2435 = bl ? ModelPartsParticleSystem.orbitOffset(class_2433, f6, f9) : class_243.field_1353;
                    class_243 class_2436 = class_2434.method_1019(class_2435);
                    double d4 = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f2.x + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f3.x * 0.25);
                    double d5 = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f2.y + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f3.y * 0.25);
                    double d6 = particleScriptSpec.lockInModel ? 0.0 : (double)vector3f2.z + ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f3.z * 0.25);
                    RenderParticle renderParticle = new RenderParticle(class_2436, new class_243(d4, d5, d6), n2, particleScriptSpec.ease, particleScriptSpec.sizeStart, f2, particleScriptSpec.alphaStart, f3, f4, f5, particleScriptSpec.fullBright, particleScriptSpec.fullDayLight, particleScriptSpec.brightAtDark, particleScriptSpec.glowTargetMenu, particleScriptSpec.lightPercent, particleScriptSpec.emissiveStrength, particleScriptSpec.emissiveBlur, particleScriptSpec.emissiveClamp, class_29602, vector3f4.x, vector3f4.y, vector3f4.z, particleScriptSpec.debug, particleScriptSpec.lockInModel, string3, true, string, class_2434, class_2433, f6, f9, f7);
                    ModelPartsParticleSystem.addInHandPipelineParticle(list, renderParticle, false, 0L, particleScriptSpec.worldPreviewBehindLocal, particleScriptSpec.worldPreviewBehindLocalBias, particleScriptSpec.ignoreModelRotationForEmissionPoint, particleScriptSpec.syntheticWorldLocal);
                }
            }
        }
    }

    private static void spawnEventFromBlockFace(class_310 class_3102, class_746 class_7462, class_1306 class_13063, PendingParticleEvent pendingParticleEvent, float f, int n) {
        MiningActionTracker.Target target;
        ParticleScriptSpec particleScriptSpec = pendingParticleEvent.spec;
        if (particleScriptSpec != null && class_3102 != null && class_3102.field_1687 != null && class_13063 != null && ModelPartsParticleSystem.matchesMiningTarget(particleScriptSpec, target = ModelPartsParticleSystem.resolveMiningTarget(class_3102, particleScriptSpec)) && target != null && target.pos() != null) {
            class_2350 class_23502 = ModelPartsParticleSystem.resolveBlockFaceSelector(particleScriptSpec.blockFaceSelector, target.face());
            FaceFrame faceFrame = ModelPartsParticleSystem.createFaceFrame(target.pos(), class_23502);
            if (faceFrame != null) {
                class_243 class_2432 = ModelPartsParticleSystem.resolveBlockFaceVector(ModelPartsParticleSystem.effectiveParticleOffset(particleScriptSpec, class_13063, particleScriptSpec.offsetSpace), particleScriptSpec.offsetSpace, faceFrame, class_7462, class_13063);
                class_243 class_2433 = ModelPartsParticleSystem.resolveBlockFaceVector(new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f), particleScriptSpec.velocitySpace, faceFrame, class_7462, class_13063);
                class_243 class_2434 = ModelPartsParticleSystem.resolveBlockFaceVector(particleScriptSpec.orbitCenterOffset, particleScriptSpec.motionSpace, faceFrame, class_7462, class_13063);
                class_243 class_2435 = ModelPartsParticleSystem.resolveBlockFaceVector(particleScriptSpec.orbitAxis, particleScriptSpec.motionSpace, faceFrame, class_7462, class_13063);
                if (class_2432 == null || !ModelPartsParticleSystem.isFinite(class_2432)) {
                    class_2432 = class_243.field_1353;
                }
                if (class_2433 == null || !ModelPartsParticleSystem.isFinite(class_2433)) {
                    class_2433 = class_243.field_1353;
                }
                if (class_2434 == null || !ModelPartsParticleSystem.isFinite(class_2434)) {
                    class_2434 = class_243.field_1353;
                }
                if (class_2435 == null || !ModelPartsParticleSystem.isFinite(class_2435)) {
                    class_2435 = new class_243(0.0, 1.0, 0.0);
                }
                class_2960 class_29602 = ModelPartsParticleSystem.resolveCustomParticleTexture(particleScriptSpec);
                Vector3f vector3f = ModelPartsParticleSystem.resolveCustomParticleTint(particleScriptSpec);
                int n2 = Math.max(1, Math.round(particleScriptSpec.lifetimeSeconds * 20.0f));
                float f2 = particleScriptSpec.sizeEnd;
                float f3 = particleScriptSpec.alphaEnd;
                float f4 = particleScriptSpec.drag;
                float f5 = particleScriptSpec.gravity;
                String string = ModelPartsParticleSystem.parseMotionMode(particleScriptSpec.motionMode, "linear");
                boolean bl = "orbit".equals(string);
                float f6 = Math.max(0.0f, particleScriptSpec.orbitRadius);
                float f7 = particleScriptSpec.orbitSpeed * ((float)Math.PI * 2) / 20.0f;
                float f8 = (float)Math.toRadians(particleScriptSpec.orbitPhaseDegrees);
                class_243 class_2436 = ModelPartsParticleSystem.normalizeAxis(class_2435);
                List list = ACTIVE_RENDER_PARTICLES.computeIfAbsent(class_13063, class_13062 -> new ArrayList());
                FaceUv faceUv = ModelPartsParticleSystem.resolveMiningImpactFaceUv(target);
                for (int i = 0; i < n; ++i) {
                    double d = faceUv != null && ModelPartsParticleSystem.shouldUseImpactFacePoint(particleScriptSpec) ? faceUv.u() : class_3532.method_16436((double)class_3102.field_1687.field_9229.method_43058(), (double)particleScriptSpec.faceMinU, (double)particleScriptSpec.faceMaxU);
                    double d2 = faceUv != null && ModelPartsParticleSystem.shouldUseImpactFacePoint(particleScriptSpec) ? faceUv.v() : class_3532.method_16436((double)class_3102.field_1687.field_9229.method_43058(), (double)particleScriptSpec.faceMinV, (double)particleScriptSpec.faceMaxV);
                    class_243 class_2437 = faceFrame.corner().method_1019(faceFrame.axisU().method_1021(d)).method_1019(faceFrame.axisV().method_1021(d2)).method_1019(faceFrame.normal().method_1021(0.0015));
                    class_243 class_2438 = ModelPartsParticleSystem.randomBlockFaceJitter(class_3102, particleScriptSpec.spread, faceFrame, 1.0);
                    class_243 class_2439 = class_2437.method_1019(class_2432).method_1019(class_2434).method_1019(class_2438);
                    float f9 = f8 + (n > 1 ? (float)Math.PI * 2 * ((float)i / (float)n) : 0.0f);
                    class_243 class_24310 = bl ? ModelPartsParticleSystem.orbitOffset(class_2436, f6, f9) : class_243.field_1353;
                    class_243 class_24311 = class_2439.method_1019(class_24310);
                    class_243 class_24312 = ModelPartsParticleSystem.randomBlockFaceJitter(class_3102, particleScriptSpec.spread, faceFrame, 0.25);
                    RenderParticle renderParticle = new RenderParticle(class_24311, new class_243(class_2433.field_1352 + class_24312.field_1352, class_2433.field_1351 + class_24312.field_1351, class_2433.field_1350 + class_24312.field_1350), n2, particleScriptSpec.ease, particleScriptSpec.sizeStart, f2, particleScriptSpec.alphaStart, f3, f4, f5, particleScriptSpec.fullBright, particleScriptSpec.fullDayLight, particleScriptSpec.brightAtDark, particleScriptSpec.glowTargetMenu, particleScriptSpec.lightPercent, particleScriptSpec.emissiveStrength, particleScriptSpec.emissiveBlur, particleScriptSpec.emissiveClamp, class_29602, vector3f.x, vector3f.y, vector3f.z, false, false, null, false, string, class_2439, class_2436, f6, f9, f7);
                    ModelPartsParticleSystem.addInHandPipelineParticle(list, renderParticle, false, 0L, particleScriptSpec.worldPreviewBehindLocal, particleScriptSpec.worldPreviewBehindLocalBias, false, false);
                }
            }
        }
    }

    private static void spawnEventForHeldItemBlockFace(class_310 class_3102, class_746 class_7462, class_1306 class_13063, Matrix4f matrix4f, PendingParticleEvent pendingParticleEvent, float f, int n) {
        class_2350 class_23502;
        FaceFrame faceFrame;
        MiningActionTracker.Target target;
        ParticleScriptSpec particleScriptSpec = pendingParticleEvent.spec;
        if (particleScriptSpec != null && class_3102 != null && class_13063 != null && matrix4f != null && ModelPartsParticleSystem.matchesMiningTarget(particleScriptSpec, target = ModelPartsParticleSystem.resolveMiningTarget(class_3102, particleScriptSpec)) && (faceFrame = ModelPartsParticleSystem.createUnitCubeFaceFrame(class_23502 = ModelPartsParticleSystem.resolveBlockFaceSelector(particleScriptSpec.blockFaceSelector, target == null ? null : target.face()))) != null) {
            Vector3f vector3f = ModelPartsParticleSystem.resolveHeldItemBlockFaceVector(ModelPartsParticleSystem.effectiveParticleOffset(particleScriptSpec, class_13063, particleScriptSpec.offsetSpace), particleScriptSpec.offsetSpace, faceFrame, matrix4f, class_7462, class_13063, false);
            Vector3f vector3f2 = ModelPartsParticleSystem.resolveHeldItemBlockFaceVector(new Vector3f((Vector3fc)particleScriptSpec.velocity).mul(f), particleScriptSpec.velocitySpace, faceFrame, matrix4f, class_7462, class_13063, true);
            Vector3f vector3f3 = ModelPartsParticleSystem.resolveHeldItemBlockFaceVector(particleScriptSpec.orbitCenterOffset, particleScriptSpec.motionSpace, faceFrame, matrix4f, class_7462, class_13063, false);
            Vector3f vector3f4 = ModelPartsParticleSystem.resolveHeldItemBlockFaceVector(particleScriptSpec.orbitAxis, particleScriptSpec.motionSpace, faceFrame, matrix4f, class_7462, class_13063, false);
            if (vector3f == null) {
                vector3f = new Vector3f();
            }
            if (vector3f2 == null) {
                vector3f2 = new Vector3f();
            }
            if (vector3f3 == null) {
                vector3f3 = new Vector3f();
            }
            if (vector3f4 == null) {
                vector3f4 = new Vector3f(0.0f, 1.0f, 0.0f);
            }
            class_2960 class_29602 = ModelPartsParticleSystem.resolveCustomParticleTexture(particleScriptSpec);
            Vector3f vector3f5 = ModelPartsParticleSystem.resolveCustomParticleTint(particleScriptSpec);
            int n2 = particleScriptSpec.lockInModel ? Integer.MAX_VALUE : Math.max(1, Math.round(particleScriptSpec.lifetimeSeconds * 20.0f));
            float f2 = particleScriptSpec.lockInModel ? particleScriptSpec.sizeStart : particleScriptSpec.sizeEnd;
            float f3 = particleScriptSpec.lockInModel ? particleScriptSpec.alphaStart : particleScriptSpec.alphaEnd;
            float f4 = particleScriptSpec.lockInModel ? 1.0f : particleScriptSpec.drag;
            float f5 = particleScriptSpec.lockInModel ? 0.0f : particleScriptSpec.gravity;
            String string = particleScriptSpec.lockInModel ? "linear" : ModelPartsParticleSystem.parseMotionMode(particleScriptSpec.motionMode, "linear");
            boolean bl = !particleScriptSpec.lockInModel && "orbit".equals(string);
            float f6 = Math.max(0.0f, particleScriptSpec.orbitRadius);
            float f7 = particleScriptSpec.orbitSpeed * ((float)Math.PI * 2) / 20.0f;
            float f8 = (float)Math.toRadians(particleScriptSpec.orbitPhaseDegrees);
            class_243 class_2432 = ModelPartsParticleSystem.normalizeAxis(new class_243((double)vector3f4.x, (double)vector3f4.y, (double)vector3f4.z));
            List list = ACTIVE_RENDER_PARTICLES.computeIfAbsent(class_13063, class_13062 -> new ArrayList());
            String string2 = particleScriptSpec.lockInModel ? particleScriptSpec.lockStableKey : null;
            FaceUv faceUv = ModelPartsParticleSystem.resolveMiningImpactFaceUv(target);
            for (int i = 0; i < n; ++i) {
                String string3 = string2 == null ? null : string2 + ":" + i;
                double d = faceUv != null && ModelPartsParticleSystem.shouldUseImpactFacePoint(particleScriptSpec) ? faceUv.u() : class_3532.method_16436((double)class_3102.field_1687.field_9229.method_43058(), (double)particleScriptSpec.faceMinU, (double)particleScriptSpec.faceMaxU);
                double d2 = faceUv != null && ModelPartsParticleSystem.shouldUseImpactFacePoint(particleScriptSpec) ? faceUv.v() : class_3532.method_16436((double)class_3102.field_1687.field_9229.method_43058(), (double)particleScriptSpec.faceMinV, (double)particleScriptSpec.faceMaxV);
                class_243 class_2433 = faceFrame.corner().method_1019(faceFrame.axisU().method_1021(d)).method_1019(faceFrame.axisV().method_1021(d2)).method_1019(faceFrame.normal().method_1021(0.0015));
                Vector3f vector3f6 = ModelPartsParticleSystem.randomHeldItemBlockFaceJitter(class_3102, particleScriptSpec.spread, faceFrame, 1.0f);
                double d3 = class_2433.field_1352 + (double)vector3f.x + (double)vector3f3.x + (double)vector3f6.x;
                double d4 = class_2433.field_1351 + (double)vector3f.y + (double)vector3f3.y + (double)vector3f6.y;
                double d5 = class_2433.field_1350 + (double)vector3f.z + (double)vector3f3.z + (double)vector3f6.z;
                class_243 class_2434 = new class_243(d3, d4, d5);
                float f9 = f8 + (n > 1 ? (float)Math.PI * 2 * ((float)i / (float)n) : 0.0f);
                class_243 class_2435 = bl ? ModelPartsParticleSystem.orbitOffset(class_2432, f6, f9) : class_243.field_1353;
                class_243 class_2436 = class_2434.method_1019(class_2435);
                Vector3f vector3f7 = ModelPartsParticleSystem.randomHeldItemBlockFaceJitter(class_3102, particleScriptSpec.spread, faceFrame, 0.25f);
                RenderParticle renderParticle = new RenderParticle(class_2436, new class_243(particleScriptSpec.lockInModel ? 0.0 : (double)(vector3f2.x + vector3f7.x), particleScriptSpec.lockInModel ? 0.0 : (double)(vector3f2.y + vector3f7.y), particleScriptSpec.lockInModel ? 0.0 : (double)(vector3f2.z + vector3f7.z)), n2, particleScriptSpec.ease, particleScriptSpec.sizeStart, f2, particleScriptSpec.alphaStart, f3, f4, f5, particleScriptSpec.fullBright, particleScriptSpec.fullDayLight, particleScriptSpec.brightAtDark, particleScriptSpec.glowTargetMenu, particleScriptSpec.lightPercent, particleScriptSpec.emissiveStrength, particleScriptSpec.emissiveBlur, particleScriptSpec.emissiveClamp, class_29602, vector3f5.x, vector3f5.y, vector3f5.z, particleScriptSpec.debug, particleScriptSpec.lockInModel, string3, true, string, class_2434, class_2432, f6, f9, f7);
                ModelPartsParticleSystem.addInHandPipelineParticle(list, renderParticle, false, 0L, particleScriptSpec.worldPreviewBehindLocal, particleScriptSpec.worldPreviewBehindLocalBias, particleScriptSpec.ignoreModelRotationForEmissionPoint, particleScriptSpec.syntheticWorldLocal);
            }
        }
    }

    private static void addInHandPipelineParticle(List<RenderParticle> list, RenderParticle renderParticle, boolean bl, long l, boolean bl2, float f, boolean bl3, boolean bl4) {
        if (list != null && renderParticle != null) {
            boolean bl5 = renderParticle.renderInWorldPass = !renderParticle.localSpace;
            if (renderParticle.localSpace && bl4) {
                renderParticle.enableSyntheticWorldLocal();
            }
            if (renderParticle.lockInModel) {
                renderParticle.localSpace = true;
                renderParticle.renderInWorldPass = false;
                renderParticle.pendingWorldRelease = false;
                renderParticle.pendingReleaseSnapshotReady = false;
                renderParticle.worldPreviewBehindLocal = false;
            }
            if (renderParticle.lockInModel && renderParticle.lockKey != null && !renderParticle.lockKey.isBlank()) {
                list.removeIf(renderParticle2 -> renderParticle2 != null && renderParticle2.lockInModel && renderParticle.lockKey.equals(renderParticle2.lockKey));
            }
            list.add(renderParticle);
        }
    }

    private static class_243 resolveItemTargetLocalBase(ParticleScriptSpec particleScriptSpec) {
        if (particleScriptSpec == null) {
            return class_243.field_1353;
        }
        String string = ModelPartsParticleSystem.normalizeTargetType(particleScriptSpec.targetType, "item_center");
        if ("item_center".equals(string)) {
            return new class_243(0.5, 0.5, 0.5);
        }
        return "bone".equals(string) ? new class_243(0.5, 0.5, 0.5) : new class_243(0.5, 0.5, 0.5);
    }

    private static Vector3f resolveHeldItemBlockFaceVector(Vector3f vector3f, String string, FaceFrame faceFrame, Matrix4f matrix4f, class_746 class_7462, class_1306 class_13062, boolean bl) {
        Vector3f vector3f2 = vector3f == null ? new Vector3f() : new Vector3f((Vector3fc)vector3f);
        String string2 = ModelPartsParticleSystem.normalizeSpace(string, bl);
        if ("block_face".equals(string2)) {
            class_243 class_2432 = faceFrame.axisU().method_1021((double)vector3f2.x).method_1019(faceFrame.axisV().method_1021((double)vector3f2.y)).method_1019(faceFrame.normal().method_1021((double)vector3f2.z));
            return new Vector3f((float)class_2432.field_1352, (float)class_2432.field_1351, (float)class_2432.field_1350);
        }
        return !"block".equals(string2) && !"bone".equals(string2) ? ModelPartsParticleSystem.resolveItemLocalVector(vector3f2, string2, matrix4f, class_7462, class_13062, bl, false) : vector3f2;
    }

    private static Vector3f randomHeldItemBlockFaceJitter(class_310 class_3102, Vector3f vector3f, FaceFrame faceFrame, float f) {
        return class_3102 != null && vector3f != null && faceFrame != null && !(f <= 0.0f) ? ModelPartsParticleSystem.resolveHeldItemBlockFaceVector(new Vector3f((float)ModelPartsParticleSystem.randomSpread(class_3102, vector3f.x * f), (float)ModelPartsParticleSystem.randomSpread(class_3102, vector3f.y * f), (float)ModelPartsParticleSystem.randomSpread(class_3102, vector3f.z * f)), "block_face", faceFrame, null, null, null, false) : new Vector3f();
    }

    private static Vector3f resolveItemLocalVector(Vector3f vector3f, String string, Matrix4f matrix4f, class_746 class_7462, class_1306 class_13062, boolean bl, boolean bl2) {
        Vector3f vector3f2 = vector3f == null ? new Vector3f() : new Vector3f((Vector3fc)vector3f);
        String string2 = ModelPartsParticleSystem.normalizeSpace(string, bl);
        if (class_13062 == class_1306.field_6182 && !"world".equals(string2) && !"camera".equals(string2)) {
            vector3f2.x = -vector3f2.x;
        }
        if (!"bone".equals(string2) && !bl2) {
            class_243 class_2432 = ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f2);
            Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f).invert();
            Vector4f vector4f = new Vector4f((float)class_2432.field_1352, (float)class_2432.field_1351, (float)class_2432.field_1350, 0.0f);
            matrix4f2.transform(vector4f);
            return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
        }
        return vector3f2;
    }

    private static boolean isHeldBlockFaceTarget(ParticleScriptSpec particleScriptSpec) {
        return particleScriptSpec != null && "block_face".equals(ModelPartsParticleSystem.normalizeTargetType(particleScriptSpec.targetType, particleScriptSpec.targetType));
    }

    private static boolean isWorldBlockFaceTarget(ParticleScriptSpec particleScriptSpec) {
        return particleScriptSpec != null && "world_block_face".equals(ModelPartsParticleSystem.normalizeTargetType(particleScriptSpec.targetType, particleScriptSpec.targetType));
    }

    private static boolean isAnyBlockFaceTarget(ParticleScriptSpec particleScriptSpec) {
        return ModelPartsParticleSystem.isHeldBlockFaceTarget(particleScriptSpec) || ModelPartsParticleSystem.isWorldBlockFaceTarget(particleScriptSpec);
    }

    private static boolean shouldUseImpactFacePoint(ParticleScriptSpec particleScriptSpec) {
        if (particleScriptSpec == null) {
            return false;
        }
        return particleScriptSpec.useImpactFacePoint ? true : particleScriptSpec.faceMinU <= 1.0E-4f && particleScriptSpec.faceMinV <= 1.0E-4f && particleScriptSpec.faceMaxU >= 0.9999f && particleScriptSpec.faceMaxV >= 0.9999f;
    }

    private static class_243 resolveBlockFaceVector(Vector3f vector3f, String string, FaceFrame faceFrame, class_746 class_7462, class_1306 class_13062) {
        if (vector3f == null) {
            return class_243.field_1353;
        }
        String string2 = ModelPartsParticleSystem.normalizeSpace(string, false);
        if ("world".equals(string2)) {
            return new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
        }
        if ("camera".equals(string2)) {
            class_243 class_2432 = ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f);
            return class_2432 == null ? class_243.field_1353 : class_2432;
        }
        if ("block".equals(string2)) {
            return new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
        }
        return faceFrame == null ? class_243.field_1353 : faceFrame.axisU().method_1021((double)vector3f.x).method_1019(faceFrame.axisV().method_1021((double)vector3f.y)).method_1019(faceFrame.normal().method_1021((double)vector3f.z));
    }

    private static class_243 randomBlockFaceJitter(class_310 class_3102, Vector3f vector3f, FaceFrame faceFrame, double d) {
        if (class_3102 != null && vector3f != null && faceFrame != null && !(d <= 0.0)) {
            Vector3f vector3f2 = new Vector3f((float)ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f.x * d), (float)ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f.y * d), (float)ModelPartsParticleSystem.randomSpread(class_3102, (double)vector3f.z * d));
            return ModelPartsParticleSystem.resolveBlockFaceVector(vector3f2, "block_face", faceFrame, null, null);
        }
        return class_243.field_1353;
    }

    private static FaceUv resolveMiningImpactFaceUv(MiningActionTracker.Target target) {
        if (target != null && target.pos() != null && target.face() != null && target.hitLocation() != null) {
            FaceFrame faceFrame = ModelPartsParticleSystem.createFaceFrame(target.pos(), target.face());
            if (faceFrame == null) {
                return null;
            }
            class_243 class_2432 = target.hitLocation().method_1020(faceFrame.corner());
            double d = class_3532.method_15350((double)class_2432.method_1026(faceFrame.axisU()), (double)0.0, (double)1.0);
            double d2 = class_3532.method_15350((double)class_2432.method_1026(faceFrame.axisV()), (double)0.0, (double)1.0);
            return new FaceUv(d, d2);
        }
        return null;
    }

    private static class_2350 resolveBlockFaceSelector(String string, class_2350 class_23502) {
        String string2;
        return switch (string2 = ModelPartsParticleSystem.normalizeBlockFaceSelector(string, "hit")) {
            case "down" -> class_2350.field_11033;
            case "up" -> class_2350.field_11036;
            case "north" -> class_2350.field_11043;
            case "south" -> class_2350.field_11035;
            case "west" -> class_2350.field_11039;
            case "east" -> class_2350.field_11034;
            case "opposite" -> {
                if (class_23502 == null) {
                    yield null;
                }
                yield class_23502.method_10153();
            }
            case "random" -> class_2350.method_10162((class_5819)class_5819.method_43047());
            case "hit" -> class_23502;
            default -> class_23502;
        };
    }

    private static FaceFrame createFaceFrame(class_2338 class_23382, class_2350 class_23502) {
        if (class_23382 != null && class_23502 != null) {
            class_243 class_2432 = new class_243((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
            return switch (class_23502) {
                case class_2350.field_11043 -> new FaceFrame(class_2432, new class_243(1.0, 0.0, 0.0), new class_243(0.0, 1.0, 0.0), new class_243(0.0, 0.0, -1.0));
                case class_2350.field_11035 -> new FaceFrame(class_2432.method_1031(1.0, 0.0, 1.0), new class_243(-1.0, 0.0, 0.0), new class_243(0.0, 1.0, 0.0), new class_243(0.0, 0.0, 1.0));
                case class_2350.field_11039 -> new FaceFrame(class_2432.method_1031(0.0, 0.0, 1.0), new class_243(0.0, 0.0, -1.0), new class_243(0.0, 1.0, 0.0), new class_243(-1.0, 0.0, 0.0));
                case class_2350.field_11034 -> new FaceFrame(class_2432.method_1031(1.0, 0.0, 0.0), new class_243(0.0, 0.0, 1.0), new class_243(0.0, 1.0, 0.0), new class_243(1.0, 0.0, 0.0));
                case class_2350.field_11033 -> new FaceFrame(class_2432.method_1031(0.0, 0.0, 1.0), new class_243(1.0, 0.0, 0.0), new class_243(0.0, 0.0, -1.0), new class_243(0.0, -1.0, 0.0));
                case class_2350.field_11036 -> new FaceFrame(class_2432.method_1031(0.0, 1.0, 0.0), new class_243(1.0, 0.0, 0.0), new class_243(0.0, 0.0, 1.0), new class_243(0.0, 1.0, 0.0));
                default -> throw new IllegalStateException("Unexpected enum value");
            };
        }
        return null;
    }

    private static FaceFrame createUnitCubeFaceFrame(class_2350 class_23502) {
        if (class_23502 == null) {
            return null;
        }
        return switch (class_23502) {
            case class_2350.field_11043 -> new FaceFrame(new class_243(0.0, 0.0, 0.0), new class_243(1.0, 0.0, 0.0), new class_243(0.0, 1.0, 0.0), new class_243(0.0, 0.0, -1.0));
            case class_2350.field_11035 -> new FaceFrame(new class_243(1.0, 0.0, 1.0), new class_243(-1.0, 0.0, 0.0), new class_243(0.0, 1.0, 0.0), new class_243(0.0, 0.0, 1.0));
            case class_2350.field_11039 -> new FaceFrame(new class_243(0.0, 0.0, 1.0), new class_243(0.0, 0.0, -1.0), new class_243(0.0, 1.0, 0.0), new class_243(-1.0, 0.0, 0.0));
            case class_2350.field_11034 -> new FaceFrame(new class_243(1.0, 0.0, 0.0), new class_243(0.0, 0.0, 1.0), new class_243(0.0, 1.0, 0.0), new class_243(1.0, 0.0, 0.0));
            case class_2350.field_11033 -> new FaceFrame(new class_243(0.0, 0.0, 1.0), new class_243(1.0, 0.0, 0.0), new class_243(0.0, 0.0, -1.0), new class_243(0.0, -1.0, 0.0));
            case class_2350.field_11036 -> new FaceFrame(new class_243(0.0, 1.0, 0.0), new class_243(1.0, 0.0, 0.0), new class_243(0.0, 0.0, 1.0), new class_243(0.0, 1.0, 0.0));
            default -> throw new IllegalStateException("Unexpected enum value");
        };
    }

    private static void tickRenderParticles(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1687 != null && class_13062 != null) {
            RenderParticle renderParticle;
            long l = System.nanoTime();
            long l2 = LAST_SIM_NANOS.getOrDefault(class_13062, 0L);
            LAST_SIM_NANOS.put(class_13062, l);
            float f = l2 <= 0L ? 0.016666668f : (float)((double)(l - l2) / 1.0E9);
            f = class_3532.method_15363((float)f, (float)0.0f, (float)0.1f);
            float f2 = f * 20.0f;
            Matrix4f matrix4f2 = RENDER_ROOT.get(class_13062);
            Matrix4f matrix4f3 = LAST_SIM_ROOT.get(class_13062);
            Matrix4f matrix4f4 = null;
            if (matrix4f2 != null && matrix4f3 != null) {
                matrix4f4 = new Matrix4f((Matrix4fc)matrix4f2).invert().mul((Matrix4fc)matrix4f3);
            }
            if (matrix4f2 != null) {
                LAST_SIM_ROOT.put(class_13062, new Matrix4f((Matrix4fc)matrix4f2));
            }
            class_243 class_2432 = ModelPartsParticleSystem.resolveCameraMotionPixels(class_3102, class_13062);
            class_243 class_2433 = ModelPartsParticleSystem.resolveCameraTurnPixels(class_3102, class_13062, f2);
            boolean bl = ModelPartsParticleSystem.isShaderPackInUse();
            Matrix4f matrix4f5 = null;
            if (bl && matrix4f2 != null) {
                Matrix4f rootInverse = RENDER_ROOT_INV.get(class_13062);
                Quaternionf quaternionf = LAST_CAMERA_TURN_DELTA_CAMERA_LOCAL.get(class_13062);
                if (rootInverse != null && quaternionf != null) {
                    Matrix4f rotationMatrix = new Matrix4f().rotation((Quaternionfc)new Quaternionf((Quaternionfc)quaternionf));
                    matrix4f5 = new Matrix4f((Matrix4fc)rootInverse).mul((Matrix4fc)rotationMatrix).mul((Matrix4fc)matrix4f2);
                }
            }
            List<RenderParticle> renderParticles = ACTIVE_RENDER_PARTICLES.get(class_13062);
            if (renderParticles != null && !renderParticles.isEmpty()) {
                for (int i = renderParticles.size() - 1; i >= 0; --i) {
                    double d;
                    class_243 class_2434;
                    Vector4f vector4f;
                    Vector3f vector3f;
                    renderParticle = renderParticles.get(i);
                    if (renderParticle == null) {
                        renderParticles.remove(i);
                        continue;
                    }
                    renderParticle.ageTicks += f2;
                    if (renderParticle.ageTicks > (float)renderParticle.totalTicks) {
                        renderParticles.remove(i);
                        continue;
                    }
                    if (renderParticle.localSpace && renderParticle.pendingWorldRelease && renderParticle.pendingReleaseSnapshotReady && ModelPartsParticleSystem.applyPendingWorldReleaseSnapshot(renderParticle) || renderParticle.localSpace && renderParticle.pendingWorldRelease || renderParticle.lockInModel) continue;
                    if (renderParticle.localSpace && matrix4f4 != null) {
                        if (renderParticle.ignoreItemRotationForLayer) {
                            vector3f = matrix4f4.getTranslation(new Vector3f());
                            renderParticle.position = renderParticle.position.method_1031((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
                        } else {
                            vector4f = new Vector4f((float)renderParticle.position.field_1352, (float)renderParticle.position.field_1351, (float)renderParticle.position.field_1350, 1.0f);
                            matrix4f4.transform(vector4f);
                            renderParticle.position = new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                            vector4f = new Vector4f((float)renderParticle.velocity.field_1352, (float)renderParticle.velocity.field_1351, (float)renderParticle.velocity.field_1350, 0.0f);
                            matrix4f4.transform(vector4f);
                            renderParticle.velocity = new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                        }
                        if (renderParticle.orbit()) {
                            if (renderParticle.ignoreItemRotationForLayer) {
                                vector3f = matrix4f4.getTranslation(new Vector3f());
                                renderParticle.orbitCenter = renderParticle.orbitCenter.method_1031((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
                            } else {
                                vector4f = new Vector4f((float)renderParticle.orbitCenter.field_1352, (float)renderParticle.orbitCenter.field_1351, (float)renderParticle.orbitCenter.field_1350, 1.0f);
                                matrix4f4.transform(vector4f);
                                renderParticle.orbitCenter = new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                                vector4f = new Vector4f((float)renderParticle.orbitAxis.field_1352, (float)renderParticle.orbitAxis.field_1351, (float)renderParticle.orbitAxis.field_1350, 0.0f);
                                matrix4f4.transform(vector4f);
                                renderParticle.orbitAxis = ModelPartsParticleSystem.normalizeAxis(new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z));
                            }
                        }
                    }
                    if (renderParticle.localSpace && class_2432 != null && !renderParticle.ignoreItemRotationForLayer) {
                        renderParticle.position = renderParticle.position.method_1020(class_2432);
                        if (renderParticle.orbit()) {
                            renderParticle.orbitCenter = renderParticle.orbitCenter.method_1020(class_2432);
                        }
                    }
                    if (renderParticle.localSpace && renderParticle.syntheticWorldLocal && matrix4f5 != null && !renderParticle.ignoreItemRotationForLayer) {
                        vector4f = new Vector4f((float)renderParticle.position.field_1352, (float)renderParticle.position.field_1351, (float)renderParticle.position.field_1350, 1.0f);
                        matrix4f5.transform(vector4f);
                        renderParticle.position = new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                        vector4f = new Vector4f((float)renderParticle.velocity.field_1352, (float)renderParticle.velocity.field_1351, (float)renderParticle.velocity.field_1350, 0.0f);
                        matrix4f5.transform(vector4f);
                        renderParticle.velocity = new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                        if (renderParticle.orbit()) {
                            Vector4f vector4f2 = new Vector4f((float)renderParticle.orbitCenter.field_1352, (float)renderParticle.orbitCenter.field_1351, (float)renderParticle.orbitCenter.field_1350, 1.0f);
                            matrix4f5.transform(vector4f2);
                            renderParticle.orbitCenter = new class_243((double)vector4f2.x, (double)vector4f2.y, (double)vector4f2.z);
                            Vector4f orbitAxisVector = new Vector4f((float)renderParticle.orbitAxis.field_1352, (float)renderParticle.orbitAxis.field_1351, (float)renderParticle.orbitAxis.field_1350, 0.0f);
                            matrix4f5.transform(orbitAxisVector);
                            renderParticle.orbitAxis = ModelPartsParticleSystem.normalizeAxis(new class_243((double)orbitAxisVector.x, (double)orbitAxisVector.y, (double)orbitAxisVector.z));
                        }
                    }
                    if (renderParticle.localSpace && class_2433 != null && !renderParticle.syntheticWorldLocal && !renderParticle.ignoreItemRotationForLayer) {
                        renderParticle.position = renderParticle.position.method_1019(class_2433);
                        if (renderParticle.orbit()) {
                            renderParticle.orbitCenter = renderParticle.orbitCenter.method_1019(class_2433);
                        }
                    }
                    if (renderParticle.orbit()) {
                        renderParticle.orbitAngle += renderParticle.orbitAngularVelocity * f2;
                        class_2434 = renderParticle.orbitCenter.method_1019(ModelPartsParticleSystem.orbitOffset(renderParticle.orbitAxis, renderParticle.orbitRadius, renderParticle.orbitAngle));
                        d = 1.0 / (double)Math.max(0.001f, f2);
                        renderParticle.velocity = class_2434.method_1020(renderParticle.position).method_1021(d);
                        renderParticle.position = class_2434;
                        continue;
                    }
                    float f3 = class_3532.method_15363((float)renderParticle.drag, (float)0.0f, (float)1.2f);
                    d = Math.pow(f3, f2);
                    renderParticle.velocity = renderParticle.velocity.method_1021(d);
                    renderParticle.velocity = renderParticle.velocity.method_1031(0.0, (double)(renderParticle.gravity / 20.0f * f2), 0.0);
                    class_2434 = renderParticle.velocity.method_1021((double)f2);
                    if (renderParticle.localSpace) {
                        renderParticle.position = renderParticle.position.method_1019(class_2434);
                        continue;
                    }
                    ModelPartsParticleSystem.applyWorldCollisionStep(class_3102, renderParticle, class_2434);
                }
            }
        }
    }

    private static void applyWorldCollisionStep(class_310 class_3102, RenderParticle renderParticle, class_243 class_2432) {
        if (renderParticle != null && class_2432 != null) {
            class_243 class_2433 = renderParticle.position;
            class_243 class_2434 = class_2433.method_1019(class_2432);
            if (class_3102 != null && class_3102.field_1687 != null && ModelPartsParticleSystem.isFinite(class_2433) && ModelPartsParticleSystem.isFinite(class_2434) && !(class_2432.method_1027() <= 1.0E-8)) {
                class_3965 class_39652;
                class_3965 class_39653 = class_3102.field_1687.method_17742(new class_3959(class_2433, class_2434, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)class_3102.field_1724));
                if (class_39653 instanceof class_3965 && (class_39652 = class_39653).method_17783() == class_239.class_240.field_1332 && class_39652.method_17784() != null) {
                    class_2350 class_23502 = class_39652.method_17780();
                    class_243 class_2435 = new class_243((double)class_23502.method_10148(), (double)class_23502.method_10164(), (double)class_23502.method_10165());
                    renderParticle.position = class_39652.method_17784().method_1019(class_2435.method_1021(0.0025));
                    double d = renderParticle.velocity.method_1026(class_2435);
                    class_243 class_2436 = renderParticle.velocity;
                    if (d < 0.0) {
                        class_2436 = class_2436.method_1020(class_2435.method_1021(d));
                    }
                    double d2 = class_23502.method_10166().method_10178() ? 0.68 : 0.55;
                    class_2436 = class_2436.method_1021(d2);
                    if (class_23502 == class_2350.field_11036 && class_2436.field_1351 < 0.0035) {
                        class_2436 = new class_243(class_2436.field_1352, 0.0, class_2436.field_1350);
                    } else if (class_23502 == class_2350.field_11033 && class_2436.field_1351 > -0.0035) {
                        class_2436 = new class_243(class_2436.field_1352, 0.0, class_2436.field_1350);
                    } else if (class_23502.method_10166().method_10179()) {
                        class_2436 = new class_243(Math.abs(class_2436.field_1352) < 0.0035 ? 0.0 : class_2436.field_1352, class_2436.field_1351, Math.abs(class_2436.field_1350) < 0.0035 ? 0.0 : class_2436.field_1350);
                    }
                    renderParticle.velocity = class_2436;
                } else {
                    renderParticle.position = class_2434;
                }
            } else {
                renderParticle.position = class_2434;
            }
        }
    }

    private static boolean tryReleaseParticleFromRenderedPose(class_310 class_3102, class_1306 class_13062, RenderParticle renderParticle, Matrix4f matrix4f, class_243 class_2432, Quaternionf quaternionf, Vector3f vector3f, boolean bl) {
        if (renderParticle == null || !renderParticle.localSpace || !renderParticle.pendingWorldRelease || matrix4f == null || class_2432 == null) {
            return false;
        }
        long releaseDelay = bl ? 0L : renderParticle.worldReleaseDelayNanos;
        if (System.nanoTime() - renderParticle.spawnNanos < releaseDelay || vector3f == null || !ModelPartsParticleSystem.isFinite(vector3f)) {
            return false;
        }

        class_243 handFallback = class_3102 != null && class_3102.field_1724 != null && class_13062 != null
                ? ModelPartsParticleSystem.resolveHandFallback(class_3102, class_3102.field_1724, class_13062)
                : null;
        class_243 worldPosition;
        if (renderParticle.ignoreItemRotationForLayer) {
            Vector3f releaseOffset = ModelPartsParticleSystem.ghostReleaseOffsetForRenderPos(class_13062, quaternionf, bl);
            Vector3f adjustedPosition = new Vector3f((Vector3fc)vector3f).add((Vector3fc)releaseOffset);
            class_243 rootBase = ModelPartsParticleSystem.resolveRenderRootBase(class_3102, class_13062, handFallback);
            Vector3f renderDelta = ModelPartsParticleSystem.resolveRenderRootRenderDelta(class_13062);
            if (rootBase != null && renderDelta != null) {
                Vector3f rootRelative = new Vector3f((Vector3fc)adjustedPosition).sub((Vector3fc)renderDelta);
                class_243 worldOffset = bl
                        ? ModelPartsParticleSystem.releaseVectorFromRenderSpace(new Vector4f(rootRelative.x, rootRelative.y, rootRelative.z, 0.0f), quaternionf, true)
                        : new class_243(rootRelative.x, rootRelative.y, rootRelative.z);
                worldPosition = rootBase.method_1019(worldOffset);
            } else if (bl) {
                worldPosition = ModelPartsParticleSystem.cameraSpaceToWorld(class_3102, adjustedPosition, class_13062, handFallback);
                if (worldPosition == null || !ModelPartsParticleSystem.isFinite(worldPosition)) {
                    worldPosition = ModelPartsParticleSystem.releasePointFromRenderSpace(class_2432, adjustedPosition, quaternionf, true);
                }
            } else {
                worldPosition = ModelPartsParticleSystem.releasePointFromRenderSpace(class_2432, adjustedPosition, quaternionf, false);
            }
        } else {
            worldPosition = ModelPartsParticleSystem.releasePointFromRenderSpace(class_2432, vector3f, quaternionf, bl);
        }

        Vector4f velocityVector = new Vector4f((float)renderParticle.velocity.field_1352, (float)renderParticle.velocity.field_1351, (float)renderParticle.velocity.field_1350, 0.0f);
        transformReleaseVector(matrix4f, velocityVector, renderParticle.ignoreItemRotationForLayer);
        class_243 worldVelocity = toWorldDirection(class_3102, class_13062, handFallback, velocityVector, quaternionf, bl, renderParticle.ignoreItemRotationForLayer);

        class_243 worldCenter = worldPosition;
        if (renderParticle.orbitCenter != null) {
            Vector4f centerOffset = new Vector4f(
                    (float)(renderParticle.orbitCenter.field_1352 - renderParticle.position.field_1352),
                    (float)(renderParticle.orbitCenter.field_1351 - renderParticle.position.field_1351),
                    (float)(renderParticle.orbitCenter.field_1350 - renderParticle.position.field_1350),
                    0.0f);
            transformReleaseVector(matrix4f, centerOffset, renderParticle.ignoreItemRotationForLayer);
            worldCenter = worldPosition.method_1019(toWorldDirection(class_3102, class_13062, handFallback, centerOffset, quaternionf, bl, renderParticle.ignoreItemRotationForLayer));
        }

        class_243 localAxis = renderParticle.orbitAxis == null ? new class_243(0.0, 1.0, 0.0) : renderParticle.orbitAxis;
        Vector4f axisVector = new Vector4f((float)localAxis.field_1352, (float)localAxis.field_1351, (float)localAxis.field_1350, 0.0f);
        transformReleaseVector(matrix4f, axisVector, renderParticle.ignoreItemRotationForLayer);
        class_243 worldAxis = ModelPartsParticleSystem.normalizeAxis(toWorldDirection(class_3102, class_13062, handFallback, axisVector, quaternionf, bl, renderParticle.ignoreItemRotationForLayer));

        if (ModelPartsParticleSystem.isFinite(worldPosition) && ModelPartsParticleSystem.isFinite(worldVelocity) && ModelPartsParticleSystem.isFinite(worldCenter) && ModelPartsParticleSystem.isFinite(worldAxis)) {
            renderParticle.pendingReleaseWorldPos = worldPosition;
            renderParticle.pendingReleaseWorldVel = worldVelocity;
            renderParticle.pendingReleaseWorldCenter = worldCenter;
            renderParticle.pendingReleaseWorldAxis = worldAxis;
            renderParticle.pendingReleaseSnapshotReady = true;
        }
        return false;
    }

    private static void transformReleaseVector(Matrix4f transform, Vector4f vector, boolean ignorePoseRotation) {
        if (ignorePoseRotation) {
            Vector3f transformed = ModelPartsParticleSystem.transformVectorIgnoringPoseRotation(transform, new Vector3f(vector.x, vector.y, vector.z));
            vector.set(transformed.x, transformed.y, transformed.z, 0.0f);
        } else {
            transform.transform(vector);
        }
    }

    private static class_243 toWorldDirection(class_310 client, class_1306 arm, class_243 handFallback, Vector4f direction, Quaternionf cameraRotation, boolean shaderPack, boolean ignorePoseRotation) {
        if (ignorePoseRotation) {
            class_243 cameraDirection = ModelPartsParticleSystem.cameraSpaceDirectionToWorld(client, new Vector3f(direction.x, direction.y, direction.z), arm, handFallback);
            if (cameraDirection != null) {
                return cameraDirection;
            }
            return ModelPartsParticleSystem.releaseVectorFromRenderSpace(direction, cameraRotation, true);
        }
        return ModelPartsParticleSystem.releaseVectorFromRenderSpace(direction, cameraRotation, shaderPack);
    }

    private static boolean applyPendingWorldReleaseSnapshot(RenderParticle renderParticle) {
        if (!(renderParticle != null && renderParticle.localSpace && renderParticle.pendingWorldRelease && renderParticle.pendingReleaseSnapshotReady)) {
            return false;
        }
        if (ModelPartsParticleSystem.isFinite(renderParticle.pendingReleaseWorldPos) && ModelPartsParticleSystem.isFinite(renderParticle.pendingReleaseWorldVel) && ModelPartsParticleSystem.isFinite(renderParticle.pendingReleaseWorldCenter) && ModelPartsParticleSystem.isFinite(renderParticle.pendingReleaseWorldAxis)) {
            renderParticle.position = renderParticle.pendingReleaseWorldPos;
            renderParticle.velocity = renderParticle.pendingReleaseWorldVel;
            renderParticle.orbitCenter = renderParticle.pendingReleaseWorldCenter;
            renderParticle.orbitAxis = renderParticle.pendingReleaseWorldAxis;
            renderParticle.localSpace = false;
            renderParticle.pendingWorldRelease = false;
            renderParticle.pendingReleaseSnapshotReady = false;
            return true;
        }
        renderParticle.pendingReleaseSnapshotReady = false;
        return false;
    }

    private static class_243 releasePointFromRenderSpace(class_243 class_2432, Vector3f vector3f, Quaternionf quaternionf, boolean bl) {
        if (class_2432 == null || vector3f == null) {
            return class_2432 == null ? class_243.field_1353 : class_2432;
        }
        if (bl && quaternionf != null) {
            Vector3f vector3f2 = new Vector3f((Vector3fc)vector3f).rotate((Quaternionfc)quaternionf);
            return class_2432.method_1031((double)vector3f2.x, (double)vector3f2.y, (double)vector3f2.z);
        }
        return class_2432.method_1031((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
    }

    private static Vector3f transformRenderPoint(Matrix4f matrix4f, class_243 class_2432) {
        if (class_2432 == null) {
            return new Vector3f();
        }
        Vector4f vector4f = new Vector4f((float)class_2432.field_1352, (float)class_2432.field_1351, (float)class_2432.field_1350, 1.0f);
        if (matrix4f != null) {
            matrix4f.transform(vector4f);
        }
        return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
    }

    private static Vector3f renderPointIgnoringPoseRotation(Matrix4f matrix4f, class_243 class_2432, Quaternionf quaternionf, boolean bl) {
        if (class_2432 == null) {
            return new Vector3f();
        }
        if (matrix4f == null) {
            return new Vector3f((float)class_2432.field_1352, (float)class_2432.field_1351, (float)class_2432.field_1350);
        }
        Vector3f vector3f = matrix4f.getTranslation(new Vector3f());
        float f = (float)Math.sqrt(matrix4f.m00() * matrix4f.m00() + matrix4f.m10() * matrix4f.m10() + matrix4f.m20() * matrix4f.m20());
        float f2 = (float)Math.sqrt(matrix4f.m01() * matrix4f.m01() + matrix4f.m11() * matrix4f.m11() + matrix4f.m21() * matrix4f.m21());
        float f3 = (float)Math.sqrt(matrix4f.m02() * matrix4f.m02() + matrix4f.m12() * matrix4f.m12() + matrix4f.m22() * matrix4f.m22());
        if (!Float.isFinite(f) || f <= 0.0f) {
            f = 1.0f;
        }
        if (!Float.isFinite(f2) || f2 <= 0.0f) {
            f2 = 1.0f;
        }
        if (!Float.isFinite(f3) || f3 <= 0.0f) {
            f3 = 1.0f;
        }
        Vector3f vector3f2 = new Vector3f((float)class_2432.field_1352 * f, (float)class_2432.field_1351 * f2, (float)class_2432.field_1350 * f3);
        if (!bl && quaternionf != null) {
            vector3f2.rotate((Quaternionfc)quaternionf);
        }
        return new Vector3f(vector3f.x + vector3f2.x, vector3f.y + vector3f2.y, vector3f.z + vector3f2.z);
    }

    private static Vector3f transformVectorIgnoringPoseRotation(Matrix4f matrix4f, Vector3f vector3f) {
        if (vector3f == null) {
            return new Vector3f();
        }
        if (matrix4f == null) {
            return new Vector3f((Vector3fc)vector3f);
        }
        float f = (float)Math.sqrt(matrix4f.m00() * matrix4f.m00() + matrix4f.m10() * matrix4f.m10() + matrix4f.m20() * matrix4f.m20());
        float f2 = (float)Math.sqrt(matrix4f.m01() * matrix4f.m01() + matrix4f.m11() * matrix4f.m11() + matrix4f.m21() * matrix4f.m21());
        float f3 = (float)Math.sqrt(matrix4f.m02() * matrix4f.m02() + matrix4f.m12() * matrix4f.m12() + matrix4f.m22() * matrix4f.m22());
        if (!Float.isFinite(f) || f <= 0.0f) {
            f = 1.0f;
        }
        if (!Float.isFinite(f2) || f2 <= 0.0f) {
            f2 = 1.0f;
        }
        if (!Float.isFinite(f3) || f3 <= 0.0f) {
            f3 = 1.0f;
        }
        return new Vector3f(vector3f.x * f, vector3f.y * f2, vector3f.z * f3);
    }

    private static class_243 releaseVectorFromRenderSpace(Vector4f vector4f, Quaternionf quaternionf, boolean bl) {
        if (vector4f == null) {
            return class_243.field_1353;
        }
        Vector3f vector3f = new Vector3f(vector4f.x, vector4f.y, vector4f.z);
        if (bl && quaternionf != null) {
            vector3f.rotate((Quaternionfc)quaternionf);
        }
        return new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
    }

    private static class_243 pushBehindLocalLayer(class_243 class_2432, class_243 class_2433, float f, Quaternionf quaternionf) {
        if (class_2433 != null && ModelPartsParticleSystem.isFinite(class_2433)) {
            double d = class_2432 == null ? 0.0 : class_2433.method_1025(class_2432);
            double d2 = Math.max(1.0E-4, (double)f);
            if (class_2432 != null && d > 1.0E-10) {
                class_243 class_2434 = class_2433.method_1020(class_2432).method_1029();
                return class_2433.method_1019(class_2434.method_1021(d2));
            }
            if (quaternionf != null) {
                Vector3f vector3f = new Vector3f(0.0f, 0.0f, 1.0f).rotate((Quaternionfc)quaternionf);
                return class_2433.method_1031((double)vector3f.x * d2, (double)vector3f.y * d2, (double)vector3f.z * d2);
            }
            return class_2433.method_1031(0.0, 0.0, d2);
        }
        return class_2433;
    }

    private static boolean isParticleOccludedByWorld(class_310 class_3102, class_243 class_2432, class_243 class_2433) {
        if (class_3102 == null || class_3102.field_1687 == null || class_2432 == null || class_2433 == null) {
            return false;
        }
        if (ModelPartsParticleSystem.isFinite(class_2432) && ModelPartsParticleSystem.isFinite(class_2433)) {
            class_3965 class_39652 = class_3102.field_1687.method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)class_3102.field_1724));
            if (class_39652 != null && class_39652.method_17783() == class_239.class_240.field_1332 && class_39652.method_17784() != null) {
                double d = Math.sqrt(class_39652.method_17784().method_1025(class_2432));
                double d2 = Math.sqrt(class_2433.method_1025(class_2432));
                return Double.isFinite(d) && Double.isFinite(d2) ? d + 0.22 < d2 - 0.01 : false;
            }
            return false;
        }
        return false;
    }

    private static class_243 resolveCameraMotionPixels(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1773 != null && class_13062 != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 != null && class_41842.method_19326() != null && class_41842.method_23767() != null) {
                class_243 class_2432 = class_41842.method_19326();
                class_243 class_2433 = LAST_CAMERA_POS.put(class_13062, class_2432);
                if (class_2433 == null) {
                    return class_243.field_1353;
                }
                class_243 class_2434 = class_2432.method_1020(class_2433);
                if (ModelPartsParticleSystem.isFinite(class_2434) && !(class_2434.method_1027() > 4.0)) {
                    float f;
                    Quaternionf quaternionf = new Quaternionf((Quaternionfc)class_41842.method_23767()).invert();
                    Vector3f vector3f = new Vector3f((float)class_2434.field_1352, (float)class_2434.field_1351, (float)class_2434.field_1350).rotate((Quaternionfc)quaternionf);
                    Matrix4f matrix4f = RENDER_ROOT_INV.get(class_13062);
                    if (matrix4f != null) {
                        boolean bl = ModelPartsParticleSystem.isShaderPackInUse();
                        Vector3f vector3f2 = bl ? vector3f : new Vector3f((float)class_2434.field_1352, (float)class_2434.field_1351, (float)class_2434.field_1350);
                        Vector4f vector4f = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 0.0f);
                        matrix4f.transform(vector4f);
                        if (Float.isFinite(vector4f.x) && Float.isFinite(vector4f.y) && Float.isFinite(vector4f.z)) {
                            return new class_243((double)vector4f.x, (double)vector4f.y, (double)vector4f.z);
                        }
                    }
                    Vector3f vector3f3 = vector3f.mul(16.0f);
                    float f2 = vector3f3.x;
                    vector3f3.x = f = vector3f3.z;
                    vector3f3.z = -f2;
                    return new class_243((double)vector3f3.x, (double)vector3f3.y, (double)vector3f3.z);
                }
                return class_243.field_1353;
            }
            return class_243.field_1353;
        }
        return class_243.field_1353;
    }

    private static class_243 resolveCameraTurnPixels(class_310 class_3102, class_1306 class_13062, float f) {
        if (class_3102 != null && class_3102.field_1773 != null && class_13062 != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 != null && class_41842.method_23767() != null) {
                float f2;
                float f3;
                float f4;
                Quaternionf quaternionf = new Quaternionf((Quaternionfc)class_41842.method_23767());
                Quaternionf quaternionf2 = LAST_CAMERA_ROT.put(class_13062, new Quaternionf((Quaternionfc)quaternionf));
                if (quaternionf2 == null) {
                    LAST_CAMERA_TURN_DELTA_CAMERA_LOCAL.remove(class_13062);
                    return class_243.field_1353;
                }
                Vector3f vector3f = new Vector3f(0.0f, 0.0f, 1.0f).rotate((Quaternionfc)quaternionf2);
                Vector3f vector3f2 = new Vector3f(0.0f, 0.0f, 1.0f).rotate((Quaternionfc)quaternionf);
                Vector3f vector3f3 = vector3f2.sub((Vector3fc)vector3f);
                Quaternionf quaternionf3 = new Quaternionf((Quaternionfc)quaternionf).invert();
                Vector3f vector3f4 = vector3f3.rotate((Quaternionfc)quaternionf3);
                Quaternionf quaternionf4 = new Quaternionf((Quaternionfc)quaternionf3).mul((Quaternionfc)quaternionf2).normalize();
                LAST_CAMERA_TURN_DELTA_CAMERA_LOCAL.put(class_13062, quaternionf4);
                Matrix4f matrix4f = RENDER_ROOT_INV.get(class_13062);
                if (matrix4f != null) {
                    boolean bl = ModelPartsParticleSystem.isShaderPackInUse();
                    Vector3f vector3f5 = bl ? vector3f4 : vector3f3;
                    Vector4f vector4f = new Vector4f(vector3f5.x, vector3f5.y, vector3f5.z, 0.0f);
                    matrix4f.transform(vector4f);
                    if (Float.isFinite(vector4f.x) && Float.isFinite(vector4f.y) && Float.isFinite(vector4f.z)) {
                        f4 = vector4f.x;
                        f3 = vector4f.y;
                        f2 = vector4f.z;
                    } else {
                        float f5;
                        float f6 = vector3f4.x;
                        float f7 = vector3f4.y;
                        f4 = f5 = vector3f4.z;
                        f3 = f7;
                        f2 = -f6;
                    }
                } else {
                    float f8;
                    float f9 = vector3f4.x;
                    float f10 = vector3f4.y;
                    f4 = f8 = vector3f4.z;
                    f3 = f10;
                    f2 = -f9;
                }
                float f11 = 12.8f * class_3532.method_15363((float)f, (float)0.0f, (float)3.0f);
                return new class_243((double)(f4 * f11), (double)(f3 * f11), (double)(f2 * f11));
            }
            return class_243.field_1353;
        }
        return class_243.field_1353;
    }

    private static class_243 orbitOffset(class_243 class_2432, float f, float f2) {
        if (f <= 1.0E-4f) {
            return class_243.field_1353;
        }
        class_243 class_2433 = ModelPartsParticleSystem.normalizeAxis(class_2432);
        class_243 class_2434 = ModelPartsParticleSystem.perpendicularUnit(class_2433);
        Quaternionf quaternionf = new Quaternionf().fromAxisAngleRad((float)class_2433.field_1352, (float)class_2433.field_1351, (float)class_2433.field_1350, f2);
        Vector3f vector3f = new Vector3f((float)class_2434.field_1352, (float)class_2434.field_1351, (float)class_2434.field_1350).rotate((Quaternionfc)quaternionf).mul(f);
        return new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
    }

    static class_243 normalizeAxis(class_243 class_2432) {
        return ModelPartsParticleSystem.isFinite(class_2432) && !(class_2432.method_1027() <= 1.0E-8) ? class_2432.method_1029() : new class_243(0.0, 1.0, 0.0);
    }

    private static class_243 perpendicularUnit(class_243 class_2432) {
        class_243 class_2433;
        class_243 class_2434 = ModelPartsParticleSystem.normalizeAxis(class_2432);
        class_243 class_2435 = class_2434.method_1036(class_2433 = Math.abs(class_2434.field_1351) > 0.98 ? new class_243(1.0, 0.0, 0.0) : new class_243(0.0, 1.0, 0.0));
        if (class_2435.method_1027() <= 1.0E-8) {
            class_2435 = class_2434.method_1036(new class_243(0.0, 0.0, 1.0));
        }
        return class_2435.method_1027() <= 1.0E-8 ? new class_243(1.0, 0.0, 0.0) : class_2435.method_1029();
    }

    private static int resolveParticleLight(class_310 class_3102, RenderParticle renderParticle, int n) {
        if (renderParticle == null) {
            return n;
        }
        if (PunchyConfig.enableModelPartsGlow() && renderParticle.fullBright && renderParticle.lightPercent < 0.0f) {
            return 0xF000F0;
        }
        float f = renderParticle.lightPercent < 0.0f ? (PunchyConfig.enableModelPartsGlow() && renderParticle.fullBright ? 1.0f : 0.0f) : class_3532.method_15363((float)(renderParticle.lightPercent / 100.0f), (float)0.0f, (float)1.0f);
        int n2 = n;
        if (f > 0.0f) {
            n2 = f >= 1.0f ? 0xF000F0 : ModelPartsParticleSystem.lerpPackedLight(n, 0xF000F0, f);
        }
        if (PunchyConfig.enableModelPartsGlow() && ModelPartsParticleSystem.isFullDayLightActive(class_3102, renderParticle)) {
            n2 = 0xF000F0;
        }
        return n2;
    }

    private static boolean isParticleEmissiveActive(class_310 class_3102, RenderParticle renderParticle) {
        return renderParticle != null && PunchyConfig.enableModelPartsGlow() ? renderParticle.fullBright || ModelPartsParticleSystem.isFullDayLightActive(class_3102, renderParticle) : false;
    }

    private static boolean isFullDayLightActive(class_310 class_3102, RenderParticle renderParticle) {
        if (renderParticle == null || !renderParticle.fullDayLight || class_3102 == null || class_3102.field_1687 == null) {
            return false;
        }
        if (!class_3102.field_1687.method_8597().comp_642()) {
            return false;
        }
        long l = class_3102.field_1687.method_8532() % 24000L;
        return l >= 0L && l < 12000L;
    }

    private static float resolvePackedLightBrightness(class_310 class_3102, int n) {
        int n2 = class_3532.method_15340((int)(n >> 4 & 0xFFFF), (int)0, (int)15);
        int n3 = class_3532.method_15340((int)(n >> 20 & 0xFFFF), (int)0, (int)15);
        float f = (float)n2 / 15.0f;
        float f2 = (float)n3 / 15.0f;
        if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1687.method_8597().comp_642()) {
            float f3 = class_3532.method_15363((float)((float)class_3102.field_1687.method_8594() / 15.0f), (float)0.0f, (float)1.0f);
            f2 *= 1.0f - f3;
        }
        return class_3532.method_15363((float)Math.max(f, f2), (float)0.0f, (float)1.0f);
    }

    private static float smoothBrightAtDark(float f, float f2) {
        if (!Float.isFinite(f)) {
            return f2;
        }
        float f3 = f2 > f ? 0.18f : 0.05f;
        return class_3532.method_16439((float)f3, (float)f, (float)f2);
    }

    private static float resolveParticleRenderAlpha(class_310 class_3102, RenderParticle renderParticle, int n, float f) {
        return renderParticle != null && renderParticle.brightAtDark ? ModelPartsParticleSystem.resolveBrightAtDarkVisibilityAlpha(class_3102, renderParticle, n, f) : f;
    }

    private static float resolveBrightAtDarkVisibilityAlpha(class_310 class_3102, RenderParticle renderParticle, int n, float f) {
        if (renderParticle != null && renderParticle.brightAtDark) {
            float f2 = ModelPartsParticleSystem.resolvePackedLightBrightness(class_3102, n);
            float f3 = 1.0f - f2;
            float f4 = class_3532.method_15363((float)f, (float)0.1f, (float)1.0f);
            float f5 = class_3532.method_16439((float)f3, (float)0.1f, (float)f4);
            renderParticle.brightAtDarkVisibilityFactor = ModelPartsParticleSystem.smoothBrightAtDark(renderParticle.brightAtDarkVisibilityFactor, f5);
            return renderParticle.brightAtDarkVisibilityFactor;
        }
        return f;
    }

    private static float resolveBrightAtDarkGlowFactor(class_310 class_3102, RenderParticle renderParticle, int n) {
        if (renderParticle != null && renderParticle.brightAtDark) {
            float f = ModelPartsParticleSystem.resolvePackedLightBrightness(class_3102, n);
            float f2 = 1.0f - f;
            float f3 = class_3532.method_16439((float)f2, (float)0.2f, (float)1.0f);
            renderParticle.brightAtDarkGlowFactor = ModelPartsParticleSystem.smoothBrightAtDark(renderParticle.brightAtDarkGlowFactor, f3);
            return renderParticle.brightAtDarkGlowFactor;
        }
        return 0.0f;
    }

    private static int lerpPackedLight(int n, int n2, float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        int n3 = n >> 4 & 0xFFFF;
        int n4 = n >> 20 & 0xFFFF;
        int n5 = n2 >> 4 & 0xFFFF;
        int n6 = n2 >> 20 & 0xFFFF;
        int n7 = class_3532.method_15340((int)Math.round(class_3532.method_48781((float)f2, (int)n3, (int)n5)), (int)0, (int)65535);
        int n8 = class_3532.method_15340((int)Math.round(class_3532.method_48781((float)f2, (int)n4, (int)n6)), (int)0, (int)65535);
        return n7 << 4 | n8 << 20;
    }

    private static class_2960 resolveAnimatedParticleTexture(class_310 class_3102, RenderParticle renderParticle, class_2960 class_29602) {
        if (class_3102 == null || renderParticle == null || class_29602 == null) {
            return class_29602;
        }
        if (renderParticle.lockInModel) {
            return class_29602;
        }
        String string = class_29602.method_12832();
        if (string != null && string.endsWith(".png")) {
            int n = string.length() - 4;
            int n2 = string.lastIndexOf(95, n - 1);
            if (n2 >= 0 && n2 < n - 1) {
                String string2 = string.substring(n2 + 1, n);
                if (!"0".equals(string2)) {
                    return class_29602;
                }
                int n3 = ModelPartsParticleSystem.getParticleAnimationFrameCount(class_3102, class_29602);
                if (n3 <= 1) {
                    return class_29602;
                }
                float f = Math.max(1.0f, 12.0f);
                int n4 = (int)Math.floor(Math.max(0.0f, renderParticle.ageTicks) * (f / 20.0f)) % n3;
                if (n4 <= 0) {
                    return class_29602;
                }
                String string3 = string.substring(0, n2 + 1) + n4 + ".png";
                return class_2960.method_60655((String)class_29602.method_12836(), (String)string3);
            }
            return class_29602;
        }
        return class_29602;
    }

    private static int getParticleAnimationFrameCount(class_310 class_3102, class_2960 class_29602) {
        Integer n = PARTICLE_ANIM_FRAME_COUNT_CACHE.get(class_29602);
        if (n != null) {
            return n;
        }
        int n2 = ModelPartsParticleSystem.detectParticleAnimationFrameCount(class_3102, class_29602);
        PARTICLE_ANIM_FRAME_COUNT_CACHE.put(class_29602, n2);
        return n2;
    }

    private static int detectParticleAnimationFrameCount(class_310 class_3102, class_2960 class_29602) {
        try {
            if (class_3102 != null && class_3102.method_1478() != null && class_29602 != null) {
                String string = class_29602.method_12832();
                if (string != null && string.endsWith(".png")) {
                    int n = string.length() - 4;
                    int n2 = string.lastIndexOf(95, n - 1);
                    if (n2 >= 0 && n2 < n - 1) {
                        if (!"0".equals(string.substring(n2 + 1, n))) {
                            return 1;
                        }
                        int n3 = 1;
                        for (int i = 1; i < 64; ++i) {
                            class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)(string.substring(0, n2 + 1) + i + ".png"));
                            Optional optional = class_3102.method_1478().method_14486(class_29603);
                            if (!optional.isPresent()) break;
                            ++n3;
                        }
                        return n3;
                    }
                    return 1;
                }
                return 1;
            }
            return 1;
        }
        catch (Exception exception) {
            return 1;
        }
    }

    private static int resolveParticleWorldLight(class_310 class_3102, RenderParticle renderParticle, class_1306 class_13062, class_243 class_2432, Vector3f vector3f, int n) {
        if (class_3102 != null && class_3102.field_1687 != null && renderParticle != null) {
            try {
                class_243 class_2433;
                if (renderParticle.localSpace) {
                    if (class_3102.field_1724 == null) {
                        return n;
                    }
                    class_2433 = ModelPartsParticleSystem.resolveParticleLightSampleWorld(class_3102, class_3102.field_1724, class_13062, null, true);
                    if (class_2433 == null && class_2432 != null) {
                        class_2433 = class_2432;
                    }
                } else {
                    class_2433 = renderParticle.position;
                }
                if (class_2433 != null && ModelPartsParticleSystem.isFinite(class_2433)) {
                    class_2338 class_23382 = class_2338.method_49637((double)class_2433.field_1352, (double)class_2433.field_1351, (double)class_2433.field_1350);
                    return class_761.method_23794((class_1920)class_3102.field_1687, (class_2338)class_23382);
                }
                return n;
            }
            catch (Throwable throwable) {
                return n;
            }
        }
        return n;
    }

    private static class_243 resolveParticleLightSampleWorld(class_310 class_3102, class_746 class_7462, class_1306 class_13062, MiningActionTracker.Target target, boolean bl) {
        if (target != null) {
            if (target.hitLocation() != null && ModelPartsParticleSystem.isFinite(target.hitLocation())) {
                return target.hitLocation();
            }
            if (target.pos() != null) {
                return class_243.method_24953((class_2382)target.pos());
            }
        }
        if (class_7462 != null) {
            class_243 class_2432 = ModelPartsParticleSystem.resolveHandFallback(class_3102, class_7462, class_13062);
            if (class_2432 != null && ModelPartsParticleSystem.isFinite(class_2432)) {
                return class_2432;
            }
            class_243 class_2433 = class_7462.method_5836(1.0f);
            if (class_2433 != null && ModelPartsParticleSystem.isFinite(class_2433)) {
                return class_2433;
            }
        }
        return !bl && class_3102 != null && class_3102.field_1765 != null && class_3102.field_1765.method_17784() != null && ModelPartsParticleSystem.isFinite(class_3102.field_1765.method_17784()) ? class_3102.field_1765.method_17784() : null;
    }

    private static void addBillboardQuad(class_4587.class_4665 class_46652, class_4588 class_45882, Vector3f vector3f, float f, float f2, int n, float f3, float f4, float f5) {
        if (class_46652 != null && class_45882 != null && vector3f != null) {
            int n2 = ModelPartsParticleSystem.scaleToByte(f3);
            int n3 = ModelPartsParticleSystem.scaleToByte(f4);
            int n4 = ModelPartsParticleSystem.scaleToByte(f5);
            int n5 = ModelPartsParticleSystem.scaleToByte(f2);
            if (n5 > 0) {
                float f6 = vector3f.x - f;
                float f7 = vector3f.x + f;
                float f8 = vector3f.y - f;
                float f9 = vector3f.y + f;
                float f10 = vector3f.z;
                class_45882.method_22918(class_46652.method_23761(), f6, f8, f10).method_1336(n2, n3, n4, n5).method_22913(0.0f, 1.0f).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
                class_45882.method_22918(class_46652.method_23761(), f7, f8, f10).method_1336(n2, n3, n4, n5).method_22913(1.0f, 1.0f).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
                class_45882.method_22918(class_46652.method_23761(), f7, f9, f10).method_1336(n2, n3, n4, n5).method_22913(1.0f, 0.0f).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
                class_45882.method_22918(class_46652.method_23761(), f6, f9, f10).method_1336(n2, n3, n4, n5).method_22913(0.0f, 0.0f).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    private static void addEmissiveBlurHalo(class_4587.class_4665 class_46652, class_4597 class_45972, class_2960 class_29602, boolean bl, boolean bl2, float f, float f2, float f3, float f4, float f5, float f6) {
        if (class_46652 != null && class_45972 != null && !(f3 <= 0.0f)) {
            class_1921 class_19212 = ModelPartsParticleSystem.emissiveOverlayRenderType(class_29602, bl, true, bl2);
            class_4588 class_45882 = class_45972.getBuffer(class_19212);
            float[] fArray = new float[]{0.52f, 0.31f, 0.17f};
            float[] fArray2 = new float[]{0.0f, 0.08f, 0.18f};
            for (int i = 0; i < fArray.length; ++i) {
                float f7 = Math.min(1.0f, f3 * fArray[i]);
                if (f7 <= 0.0f) continue;
                class_4587 class_45872 = new class_4587();
                class_45872.method_23760().method_23761().set((Matrix4fc)class_46652.method_23761());
                class_45872.method_23760().method_23762().set((Matrix3fc)class_46652.method_23762());
                float f8 = f2 + fArray2[i];
                class_45872.method_22905(f8, f8, f8);
                float f9 = 1.0f - (float)i * 0.08f;
                ModelPartsParticleSystem.addBillboardQuad(class_45872.method_23760(), class_45882, new Vector3f(0.0f, 0.0f, 0.0f), f, f7, 0xF000F0, class_3532.method_15363((float)(f4 * f9), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f5 * f9), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(f6 * f9), (float)0.0f, (float)1.0f));
            }
        }
    }

    private static class_2960 resolveCustomParticleTexture(ParticleScriptSpec particleScriptSpec) {
        class_2960 class_29602;
        if (particleScriptSpec != null && particleScriptSpec.textureId != null && !particleScriptSpec.textureId.isBlank() && (class_29602 = class_2960.method_12829((String)particleScriptSpec.textureId.trim())) != null) {
            class_2960 class_29603 = ModelPartsParticleSystem.resolveExplicitParticleTexture(class_29602);
            if (class_29603 != null) {
                return class_29603;
            }
            Object object = class_29602.method_12832().endsWith(".png") ? class_29602.method_12832() : class_29602.method_12832() + ".png";
            return class_2960.method_60655((String)class_29602.method_12836(), (String)object);
        }
        if (particleScriptSpec != null && particleScriptSpec.typeId != null && !particleScriptSpec.typeId.isBlank()) {
            class_29602 = ModelPartsParticleSystem.resolveTextureFromParticleDefinition(particleScriptSpec.typeId);
            if (class_29602 != null) {
                return class_29602;
            }
            class_2960 class_29604 = ModelPartsParticleSystem.resolveTextureFromType(particleScriptSpec.typeId);
            if (class_29604 != null) {
                return class_29604;
            }
            String string = particleScriptSpec.typeId.toLowerCase(Locale.ROOT);
            if (string.contains("soul")) {
                return class_2960.method_60655((String)"minecraft", (String)"textures/particle/soul_0.png");
            }
            if (string.contains("smoke")) {
                return class_2960.method_60655((String)"minecraft", (String)"textures/particle/generic_0.png");
            }
            return !string.contains("campfire") && !string.contains("ash") ? DEFAULT_PARTICLE_TEXTURE : class_2960.method_60655((String)"minecraft", (String)"textures/particle/ash.png");
        }
        return DEFAULT_PARTICLE_TEXTURE;
    }

    private static class_2960 resolveExplicitParticleTexture(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        String string = class_29602.method_12832();
        if (string != null && !string.isBlank()) {
            Object object;
            LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<Object>();
            Object object2 = string.endsWith(".png") ? string : string + ".png";
            linkedHashSet.add(object2);
            if (!string.startsWith("textures/")) {
                object = "textures/" + string;
                linkedHashSet.add(((String)object).endsWith(".png") ? object : (String)object + ".png");
                Object object3 = "textures/particle/" + string;
                linkedHashSet.add(((String)object3).endsWith(".png") ? object3 : (String)object3 + ".png");
            }
            if (string.startsWith("particle/")) {
                object = "textures/" + string;
                linkedHashSet.add(((String)object).endsWith(".png") ? object : (String)object + ".png");
            }
            for (Object object3 : linkedHashSet) {
                class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)object3);
                if (!ModelPartsParticleSystem.textureExists(class_29603)) continue;
                return class_29603;
            }
            return null;
        }
        return null;
    }

    private static class_2960 resolveTextureFromParticleDefinition(String string) {
        if (string == null) return null;
        if (string.isBlank()) return null;
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        if (PARTICLE_TEXTURE_CACHE.containsKey(string2)) {
            return PARTICLE_TEXTURE_CACHE.get(string2);
        }
        class_2960 class_29602 = class_2960.method_12829((String)string2);
        if (class_29602 == null) {
            PARTICLE_TEXTURE_CACHE.put(string2, null);
            return null;
        }
        class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)("particles/" + class_29602.method_12832() + ".json"));
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.method_1478() != null) {
            block22: {
                Optional optional = class_3102.method_1478().method_14486(class_29603);
                if (optional.isEmpty()) {
                    PARTICLE_TEXTURE_CACHE.put(string2, null);
                    return null;
                }
                try (BufferedReader bufferedReader = ((class_3298)optional.get()).method_43039();){
                    JsonObject jsonObject = JsonParser.parseReader((Reader)bufferedReader).getAsJsonObject();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("textures");
                    if (jsonArray != null && !jsonArray.isEmpty()) {
                        JsonElement jsonElement = jsonArray.get(0);
                        if (jsonElement != null && jsonElement.isJsonPrimitive()) {
                            class_2960 class_29604;
                            class_2960 class_29605 = class_2960.method_12829((String)jsonElement.getAsString());
                            if (class_29605 == null) {
                                PARTICLE_TEXTURE_CACHE.put(string2, null);
                                class_2960 class_29606 = null;
                                return class_29606;
                            }
                            String string3 = class_29605.method_12832();
                            if (string3.startsWith("particle/")) {
                                string3 = string3.substring("particle/".length());
                            }
                            if (ModelPartsParticleSystem.textureExists(class_29604 = class_2960.method_60655((String)class_29605.method_12836(), (String)("textures/particle/" + string3 + ".png")))) {
                                PARTICLE_TEXTURE_CACHE.put(string2, class_29604);
                                class_2960 class_29607 = class_29604;
                                return class_29607;
                            }
                            break block22;
                        } else {
                            PARTICLE_TEXTURE_CACHE.put(string2, null);
                            class_2960 class_29608 = null;
                            return class_29608;
                        }
                    }
                    PARTICLE_TEXTURE_CACHE.put(string2, null);
                    return null;
                }
                catch (Exception exception) {
                }
            }
            PARTICLE_TEXTURE_CACHE.put(string2, null);
            return null;
        }
        PARTICLE_TEXTURE_CACHE.put(string2, null);
        return null;
    }

    private static class_2960 resolveTextureFromType(String string) {
        class_2960 class_29602 = class_2960.method_12829((String)string.trim());
        if (class_29602 == null) {
            return null;
        }
        String string2 = class_29602.method_12836();
        String string3 = class_29602.method_12832();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string3);
        if (string3.endsWith("_particle")) {
            arrayList.add(string3.substring(0, string3.length() - "_particle".length()));
        }
        if (string3.startsWith("particle/")) {
            arrayList.add(string3.substring("particle/".length()));
        }
        for (String string4 : arrayList) {
            if (string4 == null || string4.isBlank()) continue;
            class_2960 class_29603 = class_2960.method_60655((String)string2, (String)("textures/particle/" + string4 + ".png"));
            if (ModelPartsParticleSystem.textureExists(class_29603)) {
                return class_29603;
            }
            class_2960 class_29604 = class_2960.method_60655((String)string2, (String)("textures/particle/" + string4 + "_0.png"));
            if (!ModelPartsParticleSystem.textureExists(class_29604)) continue;
            return class_29604;
        }
        return null;
    }

    private static boolean textureExists(class_2960 class_29602) {
        if (class_29602 == null) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.method_1478() != null ? class_3102.method_1478().method_14486(class_29602).isPresent() : false;
    }

    private static Vector3f resolveCustomParticleTint(ParticleScriptSpec particleScriptSpec) {
        String string;
        if (particleScriptSpec != null && particleScriptSpec.tint != null) {
            return new Vector3f(class_3532.method_15363((float)particleScriptSpec.tint.x, (float)0.0f, (float)1.0f), class_3532.method_15363((float)particleScriptSpec.tint.y, (float)0.0f, (float)1.0f), class_3532.method_15363((float)particleScriptSpec.tint.z, (float)0.0f, (float)1.0f));
        }
        String string2 = string = particleScriptSpec == null ? null : particleScriptSpec.typeId;
        if (string != null && !string.isBlank()) {
            String string3 = string.trim().toLowerCase(Locale.ROOT);
            return !string3.contains("falling_lava") && !string3.contains("landing_lava") && !string3.contains("dripping_lava") && !string3.contains("dripstone_lava") ? new Vector3f(1.0f, 1.0f, 1.0f) : new Vector3f(1.0f, 0.2857143f, 0.083333336f);
        }
        return new Vector3f(1.0f, 1.0f, 1.0f);
    }

    private static int scaleToByte(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return Math.max(0, Math.min(255, Math.round(f2 * 255.0f)));
    }

    private static double randomSpread(class_310 class_3102, double d) {
        return d <= 0.0 ? 0.0 : (class_3102.field_1687.field_9229.method_43058() * 2.0 - 1.0) * d;
    }

    private static class_243 resolveBoneBase(class_746 class_7462, class_1306 class_13062, Map<String, class_630> map, String string) {
        class_243 class_2432 = class_7462.method_5836(1.0f);
        class_243 class_2433 = new class_243(0.0, 1.0, 0.0);
        class_243 class_2434 = class_7462.method_5828(1.0f).method_1029();
        class_243 class_2435 = class_2433.method_1036(class_2434);
        class_2435 = class_2435.method_1027() < 1.0E-6 ? new class_243(class_13062 == class_1306.field_6183 ? 1.0 : -1.0, 0.0, 0.0) : class_2435.method_1029();
        class_243 class_2436 = class_2434.method_1036(class_2435).method_1029();
        class_243 class_2437 = class_2432.method_1019(class_2434.method_1021(0.45)).method_1019(class_2436.method_1021(-0.25)).method_1019(class_2435.method_1021(class_13062 == class_1306.field_6183 ? 0.22 : -0.22));
        String string2 = ModelPartsParticleSystem.resolveBoneName(map, string, class_13062);
        if (string2 == null) {
            return class_2437;
        }
        Matrix4f matrix4f = ModelPartsParticleSystem.computeBoneWorld(map, string2);
        if (matrix4f == null) {
            return class_2437;
        }
        Vector3f vector3f = matrix4f.getTranslation(new Vector3f());
        class_243 class_2438 = new class_243((double)vector3f.x / 16.0, (double)vector3f.y / 16.0, (double)vector3f.z / 16.0);
        return class_2437.method_1019(class_2435.method_1021(class_2438.field_1352)).method_1019(class_2436.method_1021(class_2438.field_1351)).method_1019(class_2434.method_1021(class_2438.field_1350));
    }

    private static class_243 applyBasis(class_746 class_7462, class_1306 class_13062, Vector3f vector3f) {
        class_243 class_2432 = new class_243(0.0, 1.0, 0.0);
        class_243 class_2433 = class_7462.method_5828(1.0f).method_1029();
        class_243 class_2434 = class_2432.method_1036(class_2433);
        class_2434 = class_2434.method_1027() < 1.0E-6 ? new class_243(class_13062 == class_1306.field_6183 ? 1.0 : -1.0, 0.0, 0.0) : class_2434.method_1029();
        class_243 class_2435 = class_2433.method_1036(class_2434).method_1029();
        return class_2434.method_1021((double)vector3f.x).method_1019(class_2435.method_1021((double)vector3f.y)).method_1019(class_2433.method_1021((double)vector3f.z));
    }

    private static class_243 resolveRenderBoneBase(class_310 class_3102, class_746 class_7462, class_243 class_2432, Matrix4f matrix4f, Matrix4f matrix4f2, class_1306 class_13062, String string) {
        if (class_2432 == null) {
            return class_243.field_1353;
        }
        if (matrix4f == null) {
            return class_2432;
        }
        Vector3f vector3f = matrix4f.getTranslation(new Vector3f());
        class_243 class_2433 = ModelPartsParticleSystem.cameraSpaceToWorld(class_3102, vector3f, class_13062, class_2432);
        if (class_2433 != null && ModelPartsParticleSystem.isFinite(class_2433)) {
            return class_2433;
        }
        if ("raw".equals(string)) {
            if (matrix4f2 == null) {
                return class_2432;
            }
            Vector3f vector3f2 = matrix4f2.getTranslation(new Vector3f()).mul(0.0625f);
            class_243 class_2434 = ModelPartsParticleSystem.applyCameraBasis(class_3102, vector3f2);
            if (class_2434 == null) {
                class_2434 = ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f2);
            }
            return class_2432.method_1019(class_2434);
        }
        return class_2432;
    }

    private static class_243 applyRenderBasis(class_310 class_3102, Matrix4f matrix4f, Matrix4f matrix4f2, Vector3f vector3f, class_746 class_7462, class_1306 class_13062, String string, boolean bl, class_243 class_2432, String string2, boolean bl2) {
        class_243 class_2433;
        String string3 = ModelPartsParticleSystem.normalizeSpace(string, bl);
        if ("world".equals(string3)) {
            return new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
        }
        if ("raw".equals(string2)) {
            class_243 class_2434;
            Matrix4f matrix4f4;
            if ("camera".equals(string3)) {
                class_243 class_2435 = ModelPartsParticleSystem.applyCameraBasis(class_3102, vector3f);
                return class_2435 != null ? class_2435 : ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f);
            }
            Vector3f vector3f2 = new Vector3f((Vector3fc)vector3f);
            matrix4f4 = bl2 ? null : (matrix4f2 != null ? matrix4f2 : matrix4f);
            if (matrix4f4 != null) {
                ModelPartsParticleSystem.transformVectorByMatrixBasis(matrix4f4, vector3f2);
            }
            return (class_2434 = ModelPartsParticleSystem.applyCameraBasis(class_3102, vector3f2)) != null ? class_2434 : ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f2);
        }
        if ("camera".equals(string3)) {
            class_243 class_2436 = ModelPartsParticleSystem.cameraSpaceDirectionToWorld(class_3102, vector3f, class_13062, class_2432);
            return class_2436 != null ? class_2436 : ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f);
        }
        Vector3f vector3f3 = new Vector3f((Vector3fc)vector3f);
        Matrix4f matrix4f3 = bl2 ? null : (matrix4f2 != null ? matrix4f2 : matrix4f);
        if (matrix4f3 != null) {
            ModelPartsParticleSystem.transformVectorByMatrixBasis(matrix4f3, vector3f3);
        }
        return (class_2433 = ModelPartsParticleSystem.cameraSpaceDirectionToWorld(class_3102, vector3f3, class_13062, class_2432)) != null ? class_2433 : ModelPartsParticleSystem.applyBasis(class_7462, class_13062, vector3f3);
    }

    private static Vector3f applyRenderBasisLocal(Matrix4f matrix4f, Vector3f vector3f, String string, boolean bl, boolean bl2) {
        String string2 = ModelPartsParticleSystem.normalizeSpace(string, bl);
        Vector3f vector3f2 = new Vector3f((Vector3fc)vector3f);
        if (!bl2 && "bone".equals(string2) && matrix4f != null) {
            ModelPartsParticleSystem.transformVectorByMatrixBasis(matrix4f, vector3f2);
        }
        return vector3f2;
    }

    private static void transformVectorByMatrixBasis(Matrix4f matrix4f, Vector3f vector3f) {
        if (matrix4f != null && vector3f != null) {
            Vector3f vector3f2 = new Vector3f(matrix4f.m00(), matrix4f.m10(), matrix4f.m20());
            Vector3f vector3f3 = new Vector3f(matrix4f.m01(), matrix4f.m11(), matrix4f.m21());
            Vector3f vector3f4 = new Vector3f(matrix4f.m02(), matrix4f.m12(), matrix4f.m22());
            Vector3f vector3f5 = ModelPartsParticleSystem.normalizeOrFallback(vector3f2, new Vector3f(1.0f, 0.0f, 0.0f));
            Vector3f vector3f6 = new Vector3f((Vector3fc)vector3f3);
            vector3f6.sub((Vector3fc)new Vector3f((Vector3fc)vector3f5).mul(vector3f6.dot((Vector3fc)vector3f5)));
            vector3f6 = ModelPartsParticleSystem.normalizeOrFallback(vector3f6, ModelPartsParticleSystem.pickPerpendicular(vector3f5));
            Vector3f vector3f7 = new Vector3f((Vector3fc)vector3f5).cross((Vector3fc)vector3f6);
            vector3f7 = ModelPartsParticleSystem.normalizeOrFallback(vector3f7, ModelPartsParticleSystem.normalizeOrFallback(vector3f4, new Vector3f(0.0f, 0.0f, 1.0f)));
            float f = vector3f2.cross((Vector3fc)new Vector3f((Vector3fc)vector3f3)).dot((Vector3fc)vector3f4);
            if (Float.isFinite(f) && f < 0.0f) {
                vector3f7.negate();
            }
            float f2 = vector3f.x;
            float f3 = vector3f.y;
            float f4 = vector3f.z;
            vector3f.set(vector3f5.x * f2 + vector3f6.x * f3 + vector3f7.x * f4, vector3f5.y * f2 + vector3f6.y * f3 + vector3f7.y * f4, vector3f5.z * f2 + vector3f6.z * f3 + vector3f7.z * f4);
        }
    }

    private static Vector3f normalizeOrFallback(Vector3f vector3f, Vector3f vector3f2) {
        Vector3f vector3f3;
        Vector3f vector3f4 = vector3f3 = vector3f == null ? new Vector3f() : new Vector3f((Vector3fc)vector3f);
        if (vector3f3.lengthSquared() > 1.0E-12f && Float.isFinite(vector3f3.x) && Float.isFinite(vector3f3.y) && Float.isFinite(vector3f3.z)) {
            vector3f3.normalize();
            return vector3f3;
        }
        return vector3f2 != null && vector3f2.lengthSquared() > 1.0E-12f ? new Vector3f((Vector3fc)vector3f2).normalize() : new Vector3f(1.0f, 0.0f, 0.0f);
    }

    private static Vector3f pickPerpendicular(Vector3f vector3f) {
        if (vector3f == null) {
            return new Vector3f(0.0f, 1.0f, 0.0f);
        }
        Vector3f vector3f2 = Math.abs(vector3f.y) < 0.9f ? new Vector3f(0.0f, 1.0f, 0.0f) : new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f vector3f3 = new Vector3f((Vector3fc)vector3f2).sub((Vector3fc)new Vector3f((Vector3fc)vector3f).mul(vector3f2.dot((Vector3fc)vector3f)));
        return vector3f3.lengthSquared() <= 1.0E-12f ? new Vector3f(0.0f, 0.0f, 1.0f) : vector3f3.normalize();
    }

    private static Matrix4f toLocalBoneTransform(Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f == null) {
            return null;
        }
        return matrix4f2 == null ? new Matrix4f((Matrix4fc)matrix4f) : new Matrix4f((Matrix4fc)matrix4f2).mul((Matrix4fc)matrix4f);
    }

    private static String normalizeSpace(String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            if (!("world".equals(string2) || "camera".equals(string2) || "bone".equals(string2))) {
                return bl ? "world" : "bone";
            }
            return string2;
        }
        return bl ? "world" : "bone";
    }

    private static class_243 resolveHandFallback(class_310 class_3102, class_746 class_7462, class_1306 class_13062) {
        class_243 class_2432 = ModelPartsParticleSystem.resolveCameraHandFallback(class_3102, class_13062);
        return class_2432 != null ? class_2432 : ModelPartsParticleSystem.resolvePlayerHandFallback(class_7462, class_13062);
    }

    private static class_243 resolvePlayerHandFallback(class_746 class_7462, class_1306 class_13062) {
        class_243 class_2432 = class_7462.method_5836(1.0f);
        class_243 class_2433 = new class_243(0.0, 1.0, 0.0);
        class_243 class_2434 = class_7462.method_5828(1.0f).method_1029();
        class_243 class_2435 = class_2433.method_1036(class_2434);
        class_2435 = class_2435.method_1027() < 1.0E-6 ? new class_243(class_13062 == class_1306.field_6183 ? 1.0 : -1.0, 0.0, 0.0) : class_2435.method_1029();
        class_243 class_2436 = class_2434.method_1036(class_2435).method_1029();
        return class_2432.method_1019(class_2434.method_1021(0.45)).method_1019(class_2436.method_1021(-0.25)).method_1019(class_2435.method_1021(class_13062 == class_1306.field_6183 ? 0.22 : -0.22));
    }

    private static class_243 resolveCameraHandFallback(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1773 != null && class_13062 != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 != null && class_41842.method_19326() != null && class_41842.method_23767() != null) {
                class_243 class_2432 = class_41842.method_19326();
                Quaternionf quaternionf = class_41842.method_23767();
                Vector3f vector3f = new Vector3f(1.0f, 0.0f, 0.0f).rotate((Quaternionfc)quaternionf);
                Vector3f vector3f2 = new Vector3f(0.0f, 1.0f, 0.0f).rotate((Quaternionfc)quaternionf);
                Vector3f vector3f3 = new Vector3f(0.0f, 0.0f, 1.0f).rotate((Quaternionfc)quaternionf);
                class_243 class_2433 = new class_243((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
                class_243 class_2434 = new class_243((double)vector3f2.x, (double)vector3f2.y, (double)vector3f2.z);
                class_243 class_2435 = new class_243((double)vector3f3.x, (double)vector3f3.y, (double)vector3f3.z);
                return class_2432.method_1019(class_2435.method_1021(0.45)).method_1019(class_2434.method_1021(-0.25)).method_1019(class_2433.method_1021(class_13062 == class_1306.field_6183 ? 0.22 : -0.22));
            }
            return null;
        }
        return null;
    }

    private static class_243 applyCameraBasis(class_310 class_3102, Vector3f vector3f) {
        if (class_3102 != null && class_3102.field_1773 != null && vector3f != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 != null && class_41842.method_23767() != null) {
                Quaternionf quaternionf = class_41842.method_23767();
                Vector3f vector3f2 = new Vector3f(1.0f, 0.0f, 0.0f).rotate((Quaternionfc)quaternionf);
                Vector3f vector3f3 = new Vector3f(0.0f, 1.0f, 0.0f).rotate((Quaternionfc)quaternionf);
                Vector3f vector3f4 = new Vector3f(0.0f, 0.0f, 1.0f).rotate((Quaternionfc)quaternionf);
                class_243 class_2432 = new class_243((double)vector3f2.x, (double)vector3f2.y, (double)vector3f2.z);
                class_243 class_2433 = new class_243((double)vector3f3.x, (double)vector3f3.y, (double)vector3f3.z);
                class_243 class_2434 = new class_243((double)vector3f4.x, (double)vector3f4.y, (double)vector3f4.z);
                return class_2432.method_1021((double)vector3f.x).method_1019(class_2433.method_1021((double)vector3f.y)).method_1019(class_2434.method_1021((double)vector3f.z));
            }
            return null;
        }
        return null;
    }

    private static class_243 cameraSpaceToWorld(class_310 class_3102, Vector3f vector3f, class_1306 class_13062, class_243 class_2432) {
        if (class_3102 != null && class_3102.field_1773 != null && vector3f != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 == null) {
                return null;
            }
            class_243 class_2433 = class_41842.method_19326();
            Quaternionf quaternionf = class_41842.method_23767();
            if (class_2433 != null && quaternionf != null) {
                Vector3f vector3f2 = ModelPartsParticleSystem.transformByCalibration(vector3f, class_41842, class_13062, class_2432);
                return class_2433.method_1031((double)vector3f2.x, (double)vector3f2.y, (double)vector3f2.z);
            }
            return null;
        }
        return null;
    }

    private static class_243 resolveRenderRootBase(class_310 class_3102, class_1306 class_13062, class_243 class_2432) {
        boolean bl;
        Quaternionf quaternionf;
        Vector3f vector3f;
        if (class_13062 == null) {
            return class_2432 == null ? class_243.field_1353 : class_2432;
        }
        Matrix4f matrix4f = EXACT_INHAND_GHOST_ROOT.get(class_13062);
        if (matrix4f == null) {
            matrix4f = RENDER_ROOT.get(class_13062);
        }
        if (matrix4f == null) {
            return class_2432 == null ? class_243.field_1353 : class_2432;
        }
        class_4184 class_41842 = class_3102 != null && class_3102.field_1773 != null ? class_3102.field_1773.method_19418() : null;
        class_243 class_2433 = class_41842 == null ? null : class_41842.method_19326();
        class_243 class_2434 = ModelPartsParticleSystem.releasePointFromRenderSpace(class_2433, vector3f = matrix4f.getTranslation(new Vector3f()), quaternionf = class_41842 == null ? null : class_41842.method_23767(), bl = ModelPartsParticleSystem.isShaderPackInUse());
        if (class_2434 == null || !ModelPartsParticleSystem.isFinite(class_2434)) {
            class_2434 = ModelPartsParticleSystem.cameraSpaceToWorld(class_3102, vector3f, class_13062, class_2432);
        }
        if (class_2434 != null && ModelPartsParticleSystem.isFinite(class_2434)) {
            return class_2434;
        }
        return class_2432 == null ? class_243.field_1353 : class_2432;
    }

    private static Vector3f ghostAnchorRenderOffsetForArm(class_1306 class_13062) {
        if (GHOST_ANCHOR_RENDER_OFFSET == null) {
            return new Vector3f();
        }
        Vector3f vector3f = new Vector3f((Vector3fc)GHOST_ANCHOR_RENDER_OFFSET);
        if (class_13062 == class_1306.field_6182) {
            vector3f.x = -vector3f.x;
        }
        return vector3f;
    }

    private static Vector3f ghostReleaseOffsetForRenderPos(class_1306 class_13062, Quaternionf quaternionf, boolean bl) {
        Vector3f vector3f = ModelPartsParticleSystem.ghostAnchorRenderOffsetForArm(class_13062);
        if (vector3f == null) {
            return new Vector3f();
        }
        Vector3f vector3f2 = new Vector3f((Vector3fc)vector3f);
        if (!bl && quaternionf != null) {
            vector3f2.rotate((Quaternionfc)quaternionf);
        }
        return vector3f2;
    }

    private static Vector3f resolveRenderRootRenderDelta(class_1306 class_13062) {
        if (class_13062 == null) {
            return null;
        }
        Matrix4f matrix4f = EXACT_INHAND_GHOST_ROOT.get(class_13062);
        if (matrix4f == null) {
            matrix4f = RENDER_ROOT.get(class_13062);
        }
        if (matrix4f == null) {
            return null;
        }
        Vector3f vector3f = matrix4f.getTranslation(new Vector3f());
        return Float.isFinite(vector3f.x) && Float.isFinite(vector3f.y) && Float.isFinite(vector3f.z) ? vector3f : null;
    }

    private static class_243 cameraSpaceDirectionToWorld(class_310 class_3102, Vector3f vector3f, class_1306 class_13062, class_243 class_2432) {
        if (class_3102 != null && class_3102.field_1773 != null && vector3f != null) {
            class_4184 class_41842 = class_3102.field_1773.method_19418();
            if (class_41842 == null) {
                return null;
            }
            Quaternionf quaternionf = class_41842.method_23767();
            if (quaternionf == null) {
                return null;
            }
            Vector3f vector3f2 = ModelPartsParticleSystem.transformByCalibration(vector3f, class_41842, class_13062, class_2432);
            return new class_243((double)vector3f2.x, (double)vector3f2.y, (double)vector3f2.z);
        }
        return null;
    }

    private static Vector3f transformByCalibration(Vector3f vector3f, class_4184 class_41842, class_1306 class_13062, class_243 class_2432) {
        if (vector3f == null) {
            return new Vector3f();
        }
        Quaternionf quaternionf = class_41842 == null ? null : class_41842.method_23767();
        return class_41842 != null && quaternionf != null ? new Vector3f((Vector3fc)vector3f).rotate((Quaternionfc)quaternionf) : new Vector3f((Vector3fc)vector3f);
    }

    private static boolean isFinite(class_243 class_2432) {
        return class_2432 == null ? false : Double.isFinite(class_2432.field_1352) && Double.isFinite(class_2432.field_1351) && Double.isFinite(class_2432.field_1350);
    }

    private static boolean isFinite(Vector3f vector3f) {
        return vector3f == null ? false : Float.isFinite(vector3f.x) && Float.isFinite(vector3f.y) && Float.isFinite(vector3f.z);
    }

    private static boolean isShaderPackInUse() {
        try {
            Boolean bl;
            Class<?> clazz = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Object object = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            Object object2 = clazz.getMethod("isShaderPackInUse", new Class[0]).invoke(object, new Object[0]);
            return object2 instanceof Boolean && (bl = (Boolean)object2) != false;
        }
        catch (Throwable throwable) {
            try {
                Boolean bl;
                Class<?> clazz = Class.forName("net.coderbot.iris.api.v0.IrisApi");
                Object object = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                Object object3 = clazz.getMethod("isShaderPackInUse", new Class[0]).invoke(object, new Object[0]);
                return object3 instanceof Boolean && (bl = (Boolean)object3) != false;
            }
            catch (Throwable throwable2) {
                return false;
            }
        }
    }

    private static class_1921 emissiveOverlayRenderType(class_2960 class_29602, boolean bl, boolean bl2, boolean bl3) {
        return bl ? class_1921.method_42600((class_2960)class_29602) : ModelPartsParticleSystem.particleEmissiveOverlay(class_29602, bl2, bl3);
    }

    private static class_1921 particleEmissiveOverlay(class_2960 class_29602, boolean bl, boolean bl2) {
        ParticleEmissiveKey particleEmissiveKey = new ParticleEmissiveKey(class_29602, bl, bl2);
        class_1921 class_19212 = PARTICLE_EMISSIVE_OVERLAY_TYPES.get(particleEmissiveKey);
        if (class_19212 != null) {
            return class_19212;
        }
        class_1921 class_19213 = ModelPartsParticleSystem.tryCreateParticleEmissiveOverlay(class_29602, bl, bl2);
        if (class_19213 == null) {
            class_19213 = class_1921.method_42600((class_2960)class_29602);
        }
        PARTICLE_EMISSIVE_OVERLAY_TYPES.put(particleEmissiveKey, class_19213);
        return class_19213;
    }

    private static class_1921 tryCreateParticleEmissiveOverlay(class_2960 class_29602, boolean bl, boolean bl2) {
        class_1044 class_10443;
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && (class_10443 = class_3102.method_1531().method_4619(class_29602)) != null) {
            class_10443.method_65923(bl ? class_9851.field_52394 : class_9851.field_52395, bl);
            class_10443.method_65924(bl2);
        }
        return bl ? class_1921.method_23026((class_2960)class_29602) : class_1921.method_42600((class_2960)class_29602);
    }

    private static float emissionToBaseIntensity(int n) {
        if (n <= 0) {
            return 0.0f;
        }
        return n <= 15 ? (float)n / 15.0f : 1.0f;
    }

    private static float emissionToExtraGlow(int n) {
        return n <= 15 ? 0.0f : Math.min(1.0f, (float)(n - 15) / 85.0f);
    }

    private static float estimateUniformScale(Matrix4f matrix4f) {
        if (matrix4f == null) {
            return 1.0f;
        }
        Vector3f vector3f = new Vector3f(matrix4f.m00(), matrix4f.m01(), matrix4f.m02());
        Vector3f vector3f2 = new Vector3f(matrix4f.m10(), matrix4f.m11(), matrix4f.m12());
        Vector3f vector3f3 = new Vector3f(matrix4f.m20(), matrix4f.m21(), matrix4f.m22());
        float f = vector3f.length();
        float f2 = vector3f2.length();
        float f3 = vector3f3.length();
        return Math.max(1.0E-4f, (f + f2 + f3) / 3.0f);
    }

    private static String resolveRenderBoneName(Map<String, Matrix4f> map, String string, class_1306 class_13062) {
        if (map != null && !map.isEmpty()) {
            if (string != null && !string.isBlank()) {
                String string2;
                Object object;
                if (map.containsKey(string)) {
                    return string;
                }
                String string3 = string.toLowerCase(Locale.ROOT);
                for (String object2 : map.keySet()) {
                    if (object2 == null || !object2.toLowerCase(Locale.ROOT).equals(string3)) continue;
                    return object2;
                }
                if (string3.contains("grip")) {
                    object = class_13062 == class_1306.field_6183 ? "right" : "left";
                    for (String string4 : map.keySet()) {
                        string2 = string4 == null ? "" : string4.toLowerCase(Locale.ROOT);
                        if (!string2.contains("grip") || !string2.contains((CharSequence)object)) continue;
                        return string4;
                    }
                }
                if (string3.contains("item")) {
                    object = class_13062 == class_1306.field_6183 ? "right" : "left";
                    for (String string4 : map.keySet()) {
                        string2 = string4 == null ? "" : string4.toLowerCase(Locale.ROOT);
                        if (!string2.contains("item") || !string2.contains((CharSequence)object)) continue;
                        return string4;
                    }
                }
            }
            return null;
        }
        return null;
    }

    private static String resolveBoneName(Map<String, class_630> map, String string, class_1306 class_13062) {
        if (string != null && !string.isBlank()) {
            String string2;
            Object object;
            if (map.containsKey(string)) {
                return string;
            }
            String string3 = string.toLowerCase(Locale.ROOT);
            for (String object2 : map.keySet()) {
                if (object2 == null || !object2.toLowerCase(Locale.ROOT).equals(string3)) continue;
                return object2;
            }
            if (string3.contains("grip")) {
                object = class_13062 == class_1306.field_6183 ? "right" : "left";
                for (String string4 : map.keySet()) {
                    string2 = string4 == null ? "" : string4.toLowerCase(Locale.ROOT);
                    if (!string2.contains("grip") || !string2.contains((CharSequence)object)) continue;
                    return string4;
                }
            }
            if (string3.contains("item")) {
                object = class_13062 == class_1306.field_6183 ? "right" : "left";
                for (String string4 : map.keySet()) {
                    string2 = string4 == null ? "" : string4.toLowerCase(Locale.ROOT);
                    if (!string2.contains("item") || !string2.contains((CharSequence)object)) continue;
                    return string4;
                }
            }
        }
        return null;
    }

    private static Matrix4f computeBoneWorld(Map<String, class_630> map, String string) {
        if (map != null && string != null) {
            IdentityHashMap<class_630, String> identityHashMap = new IdentityHashMap<class_630, String>();
            for (Map.Entry<String, class_630> object2 : map.entrySet()) {
                if (object2.getValue() == null) continue;
                identityHashMap.put(object2.getValue(), object2.getKey());
            }
            HashMap<String, String> hashMap = new HashMap<>();
            for (Map.Entry<String, class_630> entry : map.entrySet()) {
                String string2 = entry.getKey();
                class_630 class_6302 = entry.getValue();
                if (class_6302 == null) continue;
                ModelPartAccessor modelPartAccessor = (ModelPartAccessor)(Object)class_6302;
                for (Map.Entry<String, class_630> entry2 : modelPartAccessor.punchy$getChildren().entrySet()) {
                    String string4 = entry2.getKey();
                    String mappedName = identityHashMap.get(entry2.getValue());
                    if (!map.containsKey(string4) && mappedName != null) {
                        string4 = mappedName;
                    }
                    if (!map.containsKey(string4)) continue;
                    hashMap.put(string4, string2);
                }
            }
            HashMap<String, Matrix4f> hashMap2 = new HashMap<String, Matrix4f>();
            return ModelPartsParticleSystem.computeBoneWorldRecursive(map, hashMap, string, hashMap2);
        }
        return null;
    }

    private static Matrix4f computeBoneWorldRecursive(Map<String, class_630> map, Map<String, String> map2, String string, Map<String, Matrix4f> map3) {
        Matrix4f matrix4f;
        Matrix4f matrix4f2 = map3.get(string);
        if (matrix4f2 != null) {
            return new Matrix4f((Matrix4fc)matrix4f2);
        }
        class_630 class_6302 = map.get(string);
        if (class_6302 == null) {
            return null;
        }
        Matrix4f matrix4f3 = ModelPartMath.getLocalMatrix(class_6302);
        String string2 = map2.get(string);
        Matrix4f matrix4f4 = string2 != null && !string2.equals(string) ? ((matrix4f = ModelPartsParticleSystem.computeBoneWorldRecursive(map, map2, string2, map3)) != null ? new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f3) : matrix4f3) : matrix4f3;
        map3.put(string, new Matrix4f((Matrix4fc)matrix4f4));
        return matrix4f4;
    }

    private static float resolveEmitDurationSeconds(ParticleScriptSpec particleScriptSpec, AnimationClip animationClip, float f) {
        if (particleScriptSpec != null && !(particleScriptSpec.ratePerSecond <= 0.0f)) {
            if (particleScriptSpec.emitFullAnimation && animationClip != null && animationClip.getLength() > 1.0E-4f) {
                return animationClip.getLength();
            }
            if (particleScriptSpec.emitForSeconds > 0.0f) {
                return particleScriptSpec.emitForSeconds;
            }
            if (animationClip == null) {
                return 0.0f;
            }
            float f2 = animationClip.getLength();
            if (f2 <= 1.0E-4f) {
                return 0.0f;
            }
            float f3 = class_3532.method_15363((float)f, (float)0.0f, (float)f2);
            float f4 = f2 - f3;
            if (f4 <= 1.0E-4f && animationClip.isLoop()) {
                f4 = f2;
            }
            return Math.max(0.0f, f4);
        }
        return 0.0f;
    }

    private static class_2394 resolveParticle(String string) {
        class_2396 class_23962;
        class_2960 class_29602;
        if (string != null && !string.isBlank() && (class_29602 = class_2960.method_12829((String)string.trim())) != null && (class_23962 = (class_2396)class_7923.field_41180.method_17966(class_29602).orElse(null)) instanceof class_2394) {
            class_2394 class_23942 = (class_2394)class_23962;
            return class_23942;
        }
        return class_2398.field_11205;
    }

    private static ParticleScriptSpec parseParticleSpec(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            if (string2.startsWith("particle(") && string2.endsWith(")")) {
                String[] stringArray;
                String string3 = string2.substring("particle(".length(), string2.length() - 1).trim();
                if (string3.isEmpty()) {
                    return null;
                }
                ParticleScriptSpec particleScriptSpec = new ParticleScriptSpec();
                block243: for (String string4 : stringArray = string3.split(",")) {
                    int n;
                    String string5 = string4.trim();
                    if (string5.isEmpty() || (n = string5.indexOf(61)) <= 0 || n >= string5.length() - 1) continue;
                    String string6 = string5.substring(0, n).trim().toLowerCase(Locale.ROOT);
                    String string7 = string5.substring(n + 1).trim();
                    switch (string6) {
                        case "type": {
                            particleScriptSpec.typeId = string7;
                            continue block243;
                        }
                        case "bone": {
                            particleScriptSpec.boneName = string7;
                            continue block243;
                        }
                        case "target": 
                        case "emit_target": {
                            ModelPartsParticleSystem.applyTargetToken(particleScriptSpec, string7);
                            continue block243;
                        }
                        case "target_type": 
                        case "emit_target_type": {
                            particleScriptSpec.targetType = ModelPartsParticleSystem.normalizeTargetType(string7, particleScriptSpec.targetType);
                            continue block243;
                        }
                        case "count": 
                        case "burst": {
                            particleScriptSpec.count = ModelPartsParticleSystem.clampInt(ModelPartsParticleSystem.parseInt(string7, particleScriptSpec.count), 0, 120);
                            continue block243;
                        }
                        case "chance": {
                            particleScriptSpec.chance = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.chance), (float)0.0f, (float)1.0f);
                            continue block243;
                        }
                        case "offset": {
                            particleScriptSpec.offset = ModelPartsParticleSystem.parseVec3(string7, particleScriptSpec.offset);
                            continue block243;
                        }
                        case "spread": {
                            particleScriptSpec.spread = ModelPartsParticleSystem.parseVec3(string7, particleScriptSpec.spread);
                            continue block243;
                        }
                        case "velocity": {
                            particleScriptSpec.velocity = ModelPartsParticleSystem.parseVec3(string7, particleScriptSpec.velocity);
                            continue block243;
                        }
                        case "ease": {
                            particleScriptSpec.ease = string7.toLowerCase(Locale.ROOT);
                            continue block243;
                        }
                        case "t": {
                            particleScriptSpec.t = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.t), (float)0.0f, (float)1.0f);
                            continue block243;
                        }
                        case "emit_for": 
                        case "duration": {
                            particleScriptSpec.emitForSeconds = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.emitForSeconds), (float)0.0f, (float)30.0f);
                            continue block243;
                        }
                        case "rate": 
                        case "rate_per_sec": 
                        case "pps": {
                            particleScriptSpec.ratePerSecond = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.ratePerSecond), (float)0.0f, (float)120.0f);
                            continue block243;
                        }
                        case "emit_interval": 
                        case "emit_every_seconds": 
                        case "interval_seconds": 
                        case "spawn_every_seconds": {
                            particleScriptSpec.emitIntervalSeconds = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.emitIntervalSeconds), (float)0.0f, (float)60.0f);
                            continue block243;
                        }
                        case "emit_ease": {
                            particleScriptSpec.emitEase = string7.toLowerCase(Locale.ROOT);
                            continue block243;
                        }
                        case "emit_full_anim": 
                        case "emit_full_animation": 
                        case "full_anim": 
                        case "full_animation": {
                            particleScriptSpec.emitFullAnimation = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.emitFullAnimation);
                            continue block243;
                        }
                        case "life": 
                        case "lifetime": {
                            particleScriptSpec.lifetimeSeconds = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.lifetimeSeconds), (float)0.05f, (float)60.0f);
                            continue block243;
                        }
                        case "size": {
                            particleScriptSpec.sizeStart = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.sizeStart), (float)0.001f, (float)2.0f);
                            continue block243;
                        }
                        case "size_end": {
                            particleScriptSpec.sizeEnd = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.sizeEnd), (float)0.0f, (float)2.0f);
                            continue block243;
                        }
                        case "alpha": {
                            particleScriptSpec.alphaStart = ModelPartsParticleSystem.parseNormalizedAlpha(string7, particleScriptSpec.alphaStart);
                            continue block243;
                        }
                        case "alpha_end": {
                            particleScriptSpec.alphaEnd = ModelPartsParticleSystem.parseNormalizedAlpha(string7, particleScriptSpec.alphaEnd);
                            continue block243;
                        }
                        case "drag": {
                            particleScriptSpec.drag = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.drag), (float)0.0f, (float)1.2f);
                            continue block243;
                        }
                        case "gravity": {
                            particleScriptSpec.gravity = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.gravity), (float)-2.0f, (float)2.0f);
                            continue block243;
                        }
                        case "fullbright": 
                        case "full_bright": {
                            particleScriptSpec.fullBright = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.fullBright);
                            particleScriptSpec.fullBrightExplicit = true;
                            continue block243;
                        }
                        case "full_day_light": 
                        case "fulldaylight": 
                        case "day_fullbright": 
                        case "daylight_fullbright": {
                            particleScriptSpec.fullDayLight = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.fullDayLight);
                            continue block243;
                        }
                        case "blur_glow_at_dark": 
                        case "bright_at_dark": 
                        case "dark_bright": 
                        case "bright_in_dark": 
                        case "bright_at_night": 
                        case "night_bright": 
                        case "full_night_light": 
                        case "night_fullbright": {
                            particleScriptSpec.brightAtDark = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.brightAtDark);
                            if (!particleScriptSpec.brightAtDark || particleScriptSpec.fullBrightExplicit) continue block243;
                            particleScriptSpec.fullBright = false;
                            continue block243;
                        }
                        case "glow_target_menu": {
                            particleScriptSpec.glowTargetMenu = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.glowTargetMenu);
                            continue block243;
                        }
                        case "light_percent": 
                        case "light_pct": 
                        case "light": 
                        case "brightness_percent": {
                            particleScriptSpec.lightPercent = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.lightPercent), (float)0.0f, (float)100.0f);
                            particleScriptSpec.lightPercentExplicit = true;
                            continue block243;
                        }
                        case "emissive_strength": 
                        case "emission_strength": 
                        case "emissive": 
                        case "strength": {
                            particleScriptSpec.emissiveStrength = ModelPartsParticleSystem.clampInt(ModelPartsParticleSystem.parseInt(string7, particleScriptSpec.emissiveStrength), 0, 100);
                            particleScriptSpec.emissiveStrengthExplicit = true;
                            continue block243;
                        }
                        case "emissive_blur": 
                        case "emission_blur": 
                        case "blur": {
                            particleScriptSpec.emissiveBlur = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.emissiveBlur);
                            continue block243;
                        }
                        case "emissive_clamp": 
                        case "emission_clamp": 
                        case "clamp": {
                            particleScriptSpec.emissiveClamp = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.emissiveClamp);
                            continue block243;
                        }
                        case "tint": 
                        case "color": 
                        case "rgb": {
                            particleScriptSpec.tint = ModelPartsParticleSystem.parseColorVec3(string7, particleScriptSpec.tint);
                            continue block243;
                        }
                        case "texture": {
                            particleScriptSpec.textureId = string7;
                            continue block243;
                        }
                        case "offset_space": 
                        case "space_offset": {
                            particleScriptSpec.offsetSpace = string7;
                            continue block243;
                        }
                        case "velocity_space": 
                        case "space_velocity": 
                        case "vel_space": {
                            particleScriptSpec.velocitySpace = string7;
                            continue block243;
                        }
                        case "ignore_model_rotation_for_emit": 
                        case "ignore_model_rotation": 
                        case "emit_ignore_model_rotation": 
                        case "no_model_rot_emit": {
                            particleScriptSpec.ignoreModelRotationForEmissionPoint = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.ignoreModelRotationForEmissionPoint);
                            continue block243;
                        }
                        case "synthetic_world_local": 
                        case "fake_world_local": 
                        case "pseudo_world_local": {
                            particleScriptSpec.syntheticWorldLocal = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.syntheticWorldLocal);
                            continue block243;
                        }
                        case "world_preview_behind_local": 
                        case "world_layer_behind_local": 
                        case "world_behind_local": 
                        case "release_preview_layer": {
                            particleScriptSpec.worldPreviewBehindLocal = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.worldPreviewBehindLocal);
                            continue block243;
                        }
                        case "world_preview_bias": 
                        case "world_layer_bias": 
                        case "behind_local_bias": 
                        case "release_preview_bias": {
                            particleScriptSpec.worldPreviewBehindLocalBias = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.worldPreviewBehindLocalBias), (float)0.0f, (float)0.05f);
                            continue block243;
                        }
                        case "lock_in_model": 
                        case "lock_model": 
                        case "locked_in_model": 
                        case "pin_in_model": 
                        case "pinned_in_model": {
                            particleScriptSpec.lockInModel = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.lockInModel);
                            continue block243;
                        }
                        case "motion": 
                        case "movement": 
                        case "motion_mode": {
                            particleScriptSpec.motionMode = ModelPartsParticleSystem.parseMotionMode(string7, particleScriptSpec.motionMode);
                            continue block243;
                        }
                        case "motion_space": 
                        case "orbit_space": {
                            particleScriptSpec.motionSpace = ModelPartsParticleSystem.normalizeSpace(string7, false);
                            continue block243;
                        }
                        case "orbit_radius": 
                        case "radius": {
                            particleScriptSpec.orbitRadius = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.orbitRadius), (float)0.0f, (float)4.0f);
                            continue block243;
                        }
                        case "orbit_speed": 
                        case "angular_speed": {
                            particleScriptSpec.orbitSpeed = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.orbitSpeed), (float)-20.0f, (float)20.0f);
                            continue block243;
                        }
                        case "orbit_axis": 
                        case "axis": {
                            particleScriptSpec.orbitAxis = ModelPartsParticleSystem.parseVec3(string7, particleScriptSpec.orbitAxis);
                            continue block243;
                        }
                        case "orbit_phase": 
                        case "phase": {
                            particleScriptSpec.orbitPhaseDegrees = ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.orbitPhaseDegrees);
                            continue block243;
                        }
                        case "orbit_center": 
                        case "center_offset": 
                        case "motion_center": {
                            particleScriptSpec.orbitCenterOffset = ModelPartsParticleSystem.parseVec3(string7, particleScriptSpec.orbitCenterOffset);
                            continue block243;
                        }
                        case "block_face": 
                        case "emit_face": 
                        case "mining_face": 
                        case "face": {
                            particleScriptSpec.blockFaceSelector = ModelPartsParticleSystem.normalizeBlockFaceSelector(string7, particleScriptSpec.blockFaceSelector);
                            continue block243;
                        }
                        case "trigger_block": 
                        case "trigger_blocks": 
                        case "block": 
                        case "blocks": 
                        case "mining_block": 
                        case "mining_blocks": {
                            particleScriptSpec.triggerBlockIds = ModelPartsParticleSystem.parseNameSet(string7);
                            continue block243;
                        }
                        case "trigger_block_state": 
                        case "block_state": 
                        case "mining_block_state": {
                            particleScriptSpec.triggerBlockState = ModelPartsParticleSystem.parseBlockStateRequirements(string7);
                            continue block243;
                        }
                        case "face_region": 
                        case "face_bounds": 
                        case "surface": 
                        case "surface_region": 
                        case "surface_bounds": {
                            ModelPartsParticleSystem.applyFaceRegion(particleScriptSpec, string7);
                            continue block243;
                        }
                        case "trigger_jump": 
                        case "jump": {
                            particleScriptSpec.triggerJump = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerJump);
                            continue block243;
                        }
                        case "trigger_landing": 
                        case "landing": {
                            particleScriptSpec.triggerLanding = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerLanding);
                            continue block243;
                        }
                        case "trigger_walking": 
                        case "walking": 
                        case "walkgin": {
                            particleScriptSpec.triggerWalking = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerWalking);
                            continue block243;
                        }
                        case "trigger_sprinting": 
                        case "sprinting": {
                            particleScriptSpec.triggerSprinting = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerSprinting);
                            continue block243;
                        }
                        case "trigger_swimming": 
                        case "swimming": 
                        case "swiming": {
                            particleScriptSpec.triggerSwimming = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerSwimming);
                            continue block243;
                        }
                        case "trigger_falling": 
                        case "falling": {
                            particleScriptSpec.triggerFalling = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerFalling);
                            continue block243;
                        }
                        case "trigger_falling_min_blocks": 
                        case "falling_min_blocks": 
                        case "min_fall_blocks": 
                        case "min_blocks": {
                            particleScriptSpec.triggerFallingMinBlocks = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.triggerFallingMinBlocks), (float)0.0f, (float)512.0f);
                            continue block243;
                        }
                        case "trigger_attacking": 
                        case "attacking": 
                        case "attack": {
                            particleScriptSpec.triggerAttacking = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerAttacking);
                            continue block243;
                        }
                        case "trigger_use_item": 
                        case "trigger_useitem": 
                        case "use_item": 
                        case "useitem": {
                            particleScriptSpec.triggerUseItem = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerUseItem);
                            continue block243;
                        }
                        case "trigger_mining": 
                        case "mining": {
                            particleScriptSpec.triggerMining = ModelPartsParticleSystem.parseBoolean(string7, particleScriptSpec.triggerMining);
                            continue block243;
                        }
                        case "on_animation_name": 
                        case "trigger_animation_name": 
                        case "animation_name": {
                            particleScriptSpec.triggerAnimationName = string7;
                            continue block243;
                        }
                        case "on_animation_timecode": 
                        case "trigger_animation_timecode": 
                        case "animation_timecode": 
                        case "timecode": {
                            particleScriptSpec.triggerAnimationTimecode = Math.max(0.0f, ModelPartsParticleSystem.parseFloat(string7, particleScriptSpec.triggerAnimationTimecode));
                            continue block243;
                        }
                        case "on_animation": 
                        case "trigger_on_animation": {
                            ModelPartsParticleSystem.applyOnAnimationToken(particleScriptSpec, string7);
                            continue block243;
                        }
                        case "trigger_biomes": 
                        case "biome_names": 
                        case "biomes": 
                        case "trigger_biome": 
                        case "biome": {
                            particleScriptSpec.triggerBiomeNames = ModelPartsParticleSystem.parseNameSet(string7);
                            continue block243;
                        }
                        case "trigger_mode": 
                        case "trigger_logic": 
                        case "triggers_mode": 
                        case "triggers_logic": {
                            particleScriptSpec.triggerModeAny = "any".equalsIgnoreCase(string7) || "or".equalsIgnoreCase(string7);
                        }
                    }
                }
                particleScriptSpec.projectionMode = "raw";
                particleScriptSpec.renderMode = "custom";
                particleScriptSpec.debug = false;
                ModelPartsParticleSystem.applyParticleVisualDefaults(particleScriptSpec);
                particleScriptSpec.lockStableKey = ModelPartsParticleSystem.buildLockStableKey(particleScriptSpec);
                return particleScriptSpec;
            }
            return null;
        }
        return null;
    }

    private static String buildLockStableKey(ParticleScriptSpec particleScriptSpec) {
        if (particleScriptSpec == null) {
            return "lock";
        }
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("type=").append(particleScriptSpec.typeId == null ? "" : particleScriptSpec.typeId.trim().toLowerCase(Locale.ROOT));
        stringBuilder.append("|bone=").append(particleScriptSpec.boneName == null ? "" : particleScriptSpec.boneName.trim().toLowerCase(Locale.ROOT));
        stringBuilder.append("|target=").append(particleScriptSpec.targetType == null ? "" : particleScriptSpec.targetType.trim().toLowerCase(Locale.ROOT));
        stringBuilder.append("|texture=").append(particleScriptSpec.textureId == null ? "" : particleScriptSpec.textureId.trim().toLowerCase(Locale.ROOT));
        stringBuilder.append("|count=").append(particleScriptSpec.count);
        stringBuilder.append("|lock=").append(particleScriptSpec.lockInModel);
        return Integer.toHexString(stringBuilder.toString().hashCode());
    }

    private static float applyEase(String string, float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        if (string != null && !string.isBlank() && !"linear".equals(string)) {
            return switch (string) {
                case "ease_in_quad" -> f2 * f2;
                case "ease_out_quad" -> 1.0f - (1.0f - f2) * (1.0f - f2);
                case "ease_in_out_cubic" -> {
                    if (f2 < 0.5f) {
                        yield 4.0f * f2 * f2 * f2;
                    }
                    yield 1.0f - (float)Math.pow(-2.0f * f2 + 2.0f, 3.0) * 0.5f;
                }
                default -> f2;
            };
        }
        return f2;
    }

    private static Vector3f effectiveParticleOffset(ParticleScriptSpec particleScriptSpec, class_1306 class_13062, String string) {
        return particleScriptSpec != null && particleScriptSpec.offset != null ? new Vector3f((Vector3fc)particleScriptSpec.offset) : new Vector3f();
    }

    private static int parseInt(String string, int n) {
        try {
            return Integer.parseInt(string);
        }
        catch (Exception exception) {
            return n;
        }
    }

    private static float parseNormalizedAlpha(String string, float f) {
        float f2 = ModelPartsParticleSystem.parseFloat(string, f);
        if (!Float.isFinite(f2)) {
            return class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        }
        if (f2 > 1.0f) {
            f2 /= 255.0f;
        }
        return class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
    }

    private static int clampInt(int n, int n2, int n3) {
        return Math.max(n2, Math.min(n3, n));
    }

    private static float parseFloat(String string, float f) {
        try {
            return Float.parseFloat(string);
        }
        catch (Exception exception) {
            return f;
        }
    }

    private static boolean parseBoolean(String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            if ("true".equals(string2) || "1".equals(string2) || "yes".equals(string2)) {
                return true;
            }
            return !"false".equals(string2) && !"0".equals(string2) && !"no".equals(string2) ? bl : false;
        }
        return bl;
    }

    private static String parseProjectionMode(String string, String string2) {
        if (string != null && !string.isBlank()) {
            String string3 = string.trim().toLowerCase(Locale.ROOT);
            if ("rotation".equals(string3) || "rotate".equals(string3)) {
                string3 = "rotated";
            }
            return !"raw".equals(string3) && !"rotated".equals(string3) ? string2 : string3;
        }
        return string2;
    }

    private static String parseRenderMode(String string, String string2) {
        if (string != null && !string.isBlank()) {
            String string3 = string.trim().toLowerCase(Locale.ROOT);
            if ("vanilla".equals(string3)) {
                return "world";
            }
            return !"world".equals(string3) && !"custom".equals(string3) && !"hand".equals(string3) ? string2 : string3;
        }
        return string2;
    }

    static String parseMotionMode(String string, String string2) {
        if (string != null && !string.isBlank()) {
            String string3 = string.trim().toLowerCase(Locale.ROOT);
            if ("linear".equals(string3) || "default".equals(string3) || "drag".equals(string3)) {
                return "linear";
            }
            return !"orbit".equals(string3) && !"orbital".equals(string3) && !"circle".equals(string3) ? string2 : "orbit";
        }
        return string2;
    }

    private static Vector3f parseVec3(String string, Vector3f vector3f) {
        if (string != null && !string.isBlank()) {
            String[] stringArray = string.trim().split(":");
            return stringArray.length != 3 ? new Vector3f((Vector3fc)vector3f) : new Vector3f(ModelPartsParticleSystem.parseFloat(stringArray[0], vector3f.x), ModelPartsParticleSystem.parseFloat(stringArray[1], vector3f.y), ModelPartsParticleSystem.parseFloat(stringArray[2], vector3f.z));
        }
        return new Vector3f((Vector3fc)vector3f);
    }

    private static Vector3f parseColorVec3(String string, Vector3f vector3f) {
        Vector3f vector3f2 = vector3f == null ? new Vector3f(1.0f, 1.0f, 1.0f) : new Vector3f((Vector3fc)vector3f);
        Vector3f vector3f3 = ModelPartsParticleSystem.parseVec3(string, vector3f2);
        if (vector3f3.x > 1.0f || vector3f3.y > 1.0f || vector3f3.z > 1.0f) {
            vector3f3.x /= 255.0f;
            vector3f3.y /= 255.0f;
            vector3f3.z /= 255.0f;
        }
        vector3f3.x = class_3532.method_15363((float)vector3f3.x, (float)0.0f, (float)1.0f);
        vector3f3.y = class_3532.method_15363((float)vector3f3.y, (float)0.0f, (float)1.0f);
        vector3f3.z = class_3532.method_15363((float)vector3f3.z, (float)0.0f, (float)1.0f);
        return vector3f3;
    }

    private static void applyParticleVisualDefaults(ParticleScriptSpec particleScriptSpec) {
        if (particleScriptSpec != null) {
            if (particleScriptSpec.brightAtDark && !particleScriptSpec.fullBrightExplicit) {
                particleScriptSpec.fullBright = false;
            }
            if (ModelPartsParticleSystem.isLeafLikeParticleType(particleScriptSpec.typeId)) {
                if (!particleScriptSpec.fullBrightExplicit) {
                    particleScriptSpec.fullBright = false;
                }
                if (!particleScriptSpec.emissiveStrengthExplicit) {
                    particleScriptSpec.emissiveStrength = 0;
                }
            }
        }
    }

    private static boolean isLeafLikeParticleType(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return string2.contains("leaves") || string2.contains("leaf");
        }
        return false;
    }

    private static Set<String> parseNameSet(String string) {
        if (string != null && !string.isBlank()) {
            String[] stringArray = string.split("[|;]");
            HashSet<String> hashSet = new HashSet<String>();
            for (String string2 : stringArray) {
                String string3;
                if (string2 == null || (string3 = string2.trim().toLowerCase(Locale.ROOT)).isBlank()) continue;
                hashSet.add(string3);
            }
            return hashSet.isEmpty() ? Set.of() : hashSet;
        }
        return Set.of();
    }

    private static Map<String, String> parseBlockStateRequirements(String string) {
        if (string != null && !string.isBlank()) {
            String[] stringArray = string.split("[|;]");
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (String string2 : stringArray) {
                if (string2 == null || string2.isBlank()) continue;
                int n = string2.indexOf(58);
                if (n <= 0 || n >= string2.length() - 1) {
                    n = string2.indexOf(61);
                }
                if (n <= 0 || n >= string2.length() - 1) continue;
                String string3 = string2.substring(0, n).trim();
                String string4 = string2.substring(n + 1).trim();
                if (string3.isBlank() || string4.isBlank()) continue;
                hashMap.put(string3, string4);
            }
            return hashMap.isEmpty() ? Map.of() : hashMap;
        }
        return Map.of();
    }

    private static void applyFaceRegion(ParticleScriptSpec particleScriptSpec, String string) {
        if (particleScriptSpec != null && string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            if (!("hit".equals(string2) || "impact".equals(string2) || "exact".equals(string2))) {
                String[] stringArray = string.trim().split(":");
                if (stringArray.length == 4) {
                    particleScriptSpec.useImpactFacePoint = false;
                    float f = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(stringArray[0], particleScriptSpec.faceMinU), (float)0.0f, (float)1.0f);
                    float f2 = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(stringArray[1], particleScriptSpec.faceMinV), (float)0.0f, (float)1.0f);
                    float f3 = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(stringArray[2], particleScriptSpec.faceMaxU), (float)0.0f, (float)1.0f);
                    float f4 = class_3532.method_15363((float)ModelPartsParticleSystem.parseFloat(stringArray[3], particleScriptSpec.faceMaxV), (float)0.0f, (float)1.0f);
                    particleScriptSpec.faceMinU = Math.min(f, f3);
                    particleScriptSpec.faceMinV = Math.min(f2, f4);
                    particleScriptSpec.faceMaxU = Math.max(f, f3);
                    particleScriptSpec.faceMaxV = Math.max(f2, f4);
                }
            } else {
                particleScriptSpec.useImpactFacePoint = true;
                particleScriptSpec.faceMinU = 0.0f;
                particleScriptSpec.faceMinV = 0.0f;
                particleScriptSpec.faceMaxU = 1.0f;
                particleScriptSpec.faceMaxV = 1.0f;
            }
        }
    }

    private static String normalizeBlockFaceSelector(String string, String string2) {
        if (string != null && !string.isBlank()) {
            String string3;
            return switch (string3 = string.trim().toLowerCase(Locale.ROOT)) {
                case "hit", "impact", "clicked" -> "hit";
                case "random", "rand" -> "random";
                case "opposite", "back", "reverse" -> "opposite";
                case "up", "down", "north", "south", "west", "east" -> string3;
                default -> string2;
            };
        }
        return string2;
    }

    private static void applyTargetToken(ParticleScriptSpec particleScriptSpec, String string) {
        if (particleScriptSpec != null && string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            particleScriptSpec.targetType = ModelPartsParticleSystem.normalizeTargetType(string2, particleScriptSpec.targetType);
        }
    }

    private static void applyOnAnimationToken(ParticleScriptSpec particleScriptSpec, String string) {
        if (particleScriptSpec != null && string != null && !string.isBlank()) {
            String string2 = string.trim();
            int n = string2.lastIndexOf(64);
            if (n > 0 && n < string2.length() - 1) {
                particleScriptSpec.triggerAnimationName = string2.substring(0, n).trim();
                particleScriptSpec.triggerAnimationTimecode = Math.max(0.0f, ModelPartsParticleSystem.parseFloat(string2.substring(n + 1).trim(), particleScriptSpec.triggerAnimationTimecode));
            } else {
                particleScriptSpec.triggerAnimationName = string2;
            }
        }
    }

    private static String normalizeTargetType(String string, String string2) {
        if (string != null && !string.isBlank()) {
            String string3;
            return switch (string3 = string.trim().toLowerCase(Locale.ROOT)) {
                case "bone", "model_part", "modelpart" -> "bone";
                case "item_center", "center_item", "center", "item" -> "item_center";
                case "block_face", "blockface", "face", "mining_face", "hit_face" -> "block_face";
                case "world_block_face", "worldblockface", "world_face", "world_hit_face", "target_block_face" -> "world_block_face";
                default -> string2;
            };
        }
        return string2;
    }

    static final class PlaybackState {
        AnimationClip lastClip;
        float lastTime;
        long lastProcessedTick = Long.MIN_VALUE;

        PlaybackState() {
        }
    }

    static final class PendingParticleEvent {
        final ParticleScriptSpec spec;
        private final float timelineTime;
        final float scale;

        PendingParticleEvent(ParticleScriptSpec particleScriptSpec, float f, float f2) {
            this.spec = particleScriptSpec;
            this.timelineTime = f;
            this.scale = f2;
        }

        @Override
        public final String toString() {
            return "PendingParticleEvent[" + "spec=" + this.spec + ", " + "timelineTime=" + this.timelineTime + ", " + "scale=" + this.scale + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.spec, this.timelineTime, this.scale);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PendingParticleEvent other)) return false;
            return java.util.Objects.deepEquals(this.spec, other.spec)
                && java.util.Objects.deepEquals(this.timelineTime, other.timelineTime)
                && java.util.Objects.deepEquals(this.scale, other.scale);
        }

        public ParticleScriptSpec spec() {
            return this.spec;
        }

        public float timelineTime() {
            return this.timelineTime;
        }

        public float scale() {
            return this.scale;
        }
    }

    static final class TriggerContext {
        final boolean jump;
        final boolean landing;
        final boolean walking;
        final boolean sprinting;
        final boolean swimming;
        final boolean falling;
        final float fallDistance;
        final boolean attacking;
        final boolean useItem;
        final boolean mining;
        final long jumpEventId;
        final long landingEventId;
        final long attackEventId;
        final long miningEventId;
        final long useItemEventId;
        static final TriggerContext EMPTY = new TriggerContext(false, false, false, false, false, false, 0.0f, false, false, false, 0L, 0L, 0L, 0L, 0L);

        TriggerContext(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, float f, boolean bl7, boolean bl8, boolean bl9, long l, long l2, long l3, long l4, long l5) {
            this.jump = bl;
            this.landing = bl2;
            this.walking = bl3;
            this.sprinting = bl4;
            this.swimming = bl5;
            this.falling = bl6;
            this.fallDistance = f;
            this.attacking = bl7;
            this.useItem = bl8;
            this.mining = bl9;
            this.jumpEventId = l;
            this.landingEventId = l2;
            this.attackEventId = l3;
            this.miningEventId = l4;
            this.useItemEventId = l5;
        }

        @Override
        public final String toString() {
            return "TriggerContext[" + "jump=" + this.jump + ", " + "landing=" + this.landing + ", " + "walking=" + this.walking + ", " + "sprinting=" + this.sprinting + ", " + "swimming=" + this.swimming + ", " + "falling=" + this.falling + ", " + "fallDistance=" + this.fallDistance + ", " + "attacking=" + this.attacking + ", " + "useItem=" + this.useItem + ", " + "mining=" + this.mining + ", " + "jumpEventId=" + this.jumpEventId + ", " + "landingEventId=" + this.landingEventId + ", " + "attackEventId=" + this.attackEventId + ", " + "miningEventId=" + this.miningEventId + ", " + "useItemEventId=" + this.useItemEventId + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.jump, this.landing, this.walking, this.sprinting, this.swimming, this.falling, this.fallDistance, this.attacking, this.useItem, this.mining, this.jumpEventId, this.landingEventId, this.attackEventId, this.miningEventId, this.useItemEventId);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TriggerContext other)) return false;
            return java.util.Objects.deepEquals(this.jump, other.jump)
                && java.util.Objects.deepEquals(this.landing, other.landing)
                && java.util.Objects.deepEquals(this.walking, other.walking)
                && java.util.Objects.deepEquals(this.sprinting, other.sprinting)
                && java.util.Objects.deepEquals(this.swimming, other.swimming)
                && java.util.Objects.deepEquals(this.falling, other.falling)
                && java.util.Objects.deepEquals(this.fallDistance, other.fallDistance)
                && java.util.Objects.deepEquals(this.attacking, other.attacking)
                && java.util.Objects.deepEquals(this.useItem, other.useItem)
                && java.util.Objects.deepEquals(this.mining, other.mining)
                && java.util.Objects.deepEquals(this.jumpEventId, other.jumpEventId)
                && java.util.Objects.deepEquals(this.landingEventId, other.landingEventId)
                && java.util.Objects.deepEquals(this.attackEventId, other.attackEventId)
                && java.util.Objects.deepEquals(this.miningEventId, other.miningEventId)
                && java.util.Objects.deepEquals(this.useItemEventId, other.useItemEventId);
        }

        public boolean jump() {
            return this.jump;
        }

        public boolean landing() {
            return this.landing;
        }

        public boolean walking() {
            return this.walking;
        }

        public boolean sprinting() {
            return this.sprinting;
        }

        public boolean swimming() {
            return this.swimming;
        }

        public boolean falling() {
            return this.falling;
        }

        public float fallDistance() {
            return this.fallDistance;
        }

        public boolean attacking() {
            return this.attacking;
        }

        public boolean useItem() {
            return this.useItem;
        }

        public boolean mining() {
            return this.mining;
        }

        public long jumpEventId() {
            return this.jumpEventId;
        }

        public long landingEventId() {
            return this.landingEventId;
        }

        public long attackEventId() {
            return this.attackEventId;
        }

        public long miningEventId() {
            return this.miningEventId;
        }

        public long useItemEventId() {
            return this.useItemEventId;
        }
    }

    static final class PassiveEmitter {
        final ParticleScriptSpec spec;
        float ageSeconds;
        double accumulator;
        long lastTick;
        long lastJumpEventId;
        long lastLandingEventId;
        long lastAttackEventId;
        long lastMiningEventId;
        long lastUseItemEventId;
        long lastAnimationWindowSerial;
        long lastEmitNanos;

        PassiveEmitter(ParticleScriptSpec particleScriptSpec) {
            this.spec = particleScriptSpec;
            this.ageSeconds = 0.0f;
            this.accumulator = 0.0;
            this.lastTick = Long.MIN_VALUE;
            this.lastJumpEventId = 0L;
            this.lastLandingEventId = 0L;
            this.lastAttackEventId = 0L;
            this.lastMiningEventId = 0L;
            this.lastUseItemEventId = 0L;
            this.lastAnimationWindowSerial = Long.MIN_VALUE;
            this.lastEmitNanos = 0L;
        }
    }

    static final class ParticleScriptSpec {
        String typeId = "minecraft:crit";
        String boneName = "";
        String targetType = "bone";
        int count = 1;
        float chance = 1.0f;
        Vector3f offset = new Vector3f();
        Vector3f spread = new Vector3f();
        Vector3f velocity = new Vector3f();
        String ease = "linear";
        float t = 1.0f;
        float emitForSeconds = 0.0f;
        float ratePerSecond = 0.0f;
        String emitEase = "linear";
        boolean emitFullAnimation = false;
        float emitIntervalSeconds = 0.0f;
        float lifetimeSeconds = 1.1f;
        float sizeStart = 0.028f;
        float sizeEnd = 0.006f;
        float alphaStart = 0.95f;
        float alphaEnd = 0.0f;
        float drag = 0.93f;
        float gravity = 0.0f;
        boolean fullBright = true;
        boolean fullDayLight = false;
        boolean brightAtDark = false;
        boolean glowTargetMenu = false;
        float lightPercent = -1.0f;
        int emissiveStrength = 100;
        boolean emissiveBlur = false;
        boolean emissiveClamp = true;
        boolean fullBrightExplicit = false;
        boolean lightPercentExplicit = false;
        boolean emissiveStrengthExplicit = false;
        Vector3f tint = null;
        String textureId = "";
        String offsetSpace = "bone";
        String velocitySpace = "world";
        boolean ignoreModelRotationForEmissionPoint = true;
        boolean syntheticWorldLocal = true;
        boolean worldPreviewBehindLocal = true;
        float worldPreviewBehindLocalBias = 0.0015f;
        boolean lockInModel = false;
        String motionMode = "linear";
        String motionSpace = "bone";
        float orbitRadius = 0.0f;
        float orbitSpeed = 0.0f;
        Vector3f orbitAxis = new Vector3f(0.0f, 1.0f, 0.0f);
        float orbitPhaseDegrees = 0.0f;
        Vector3f orbitCenterOffset = new Vector3f();
        String blockFaceSelector = "hit";
        float faceMinU = 0.0f;
        float faceMinV = 0.0f;
        float faceMaxU = 1.0f;
        float faceMaxV = 1.0f;
        boolean useImpactFacePoint = false;
        boolean triggerJump = false;
        boolean triggerLanding = false;
        boolean triggerWalking = false;
        boolean triggerSprinting = false;
        boolean triggerSwimming = false;
        boolean triggerFalling = false;
        float triggerFallingMinBlocks = 0.0f;
        boolean triggerAttacking = false;
        boolean triggerUseItem = false;
        boolean triggerMining = false;
        Set<String> triggerBlockIds = Set.of();
        Map<String, String> triggerBlockState = Map.of();
        String triggerAnimationName = "";
        float triggerAnimationTimecode = -1.0f;
        Set<String> triggerBiomeNames = Set.of();
        boolean triggerModeAny = false;
        String projectionMode = "raw";
        boolean debug = false;
        String renderMode = "custom";
        String lockStableKey = "lock";

        ParticleScriptSpec() {
        }

        boolean hasAnimationTrigger() {
            return this.triggerAnimationName != null && !this.triggerAnimationName.isBlank() && this.triggerAnimationTimecode >= 0.0f;
        }
    }

    static final class TriggerRuntimeState {
        boolean initialized = false;
        boolean lastOnGround = true;
        boolean lastAttackKeyDown = false;
        boolean lastSwinging = false;
        int jumpGraceTicks = 0;
        int landingGraceTicks = 0;
        int attackGraceTicks = 0;
        int miningGraceTicks = 0;
        long jumpEventId = 0L;
        long landingEventId = 0L;
        long attackEventId = 0L;
        long miningEventId = 0L;

        TriggerRuntimeState() {
        }
    }

    static final class AnimationTriggerWindow {
        long serial;
        boolean active;
        boolean clipChanged;
        boolean looping;
        String currentClipName = "";
        float previousTimeSeconds;
        float currentTimeSeconds;
        float clipLengthSeconds;

        AnimationTriggerWindow() {
        }
    }

    static final class ActiveEmitter {
        final ParticleScriptSpec spec;
        final int totalTicks;
        float ageSeconds;
        double accumulator;
        long lastEmitNanos;

        private ActiveEmitter(ParticleScriptSpec particleScriptSpec, int n) {
            this.spec = particleScriptSpec;
            this.totalTicks = Math.max(1, n);
            this.ageSeconds = 0.0f;
            this.accumulator = 0.0;
            this.lastEmitNanos = 0L;
        }
    }

    static final class RenderParticle {
        class_243 position;
        class_243 velocity;
        final int totalTicks;
        float ageTicks;
        final String ease;
        final float sizeStart;
        final float sizeEnd;
        final float alphaStart;
        final float alphaEnd;
        final float drag;
        final float gravity;
        final boolean fullBright;
        final boolean fullDayLight;
        final boolean brightAtDark;
        final boolean glowTargetMenu;
        final float lightPercent;
        final int emissiveStrength;
        final boolean emissiveBlur;
        final boolean emissiveClamp;
        final class_2960 texture;
        final float red;
        final float green;
        final float blue;
        private final boolean debug;
        final boolean lockInModel;
        final String lockKey;
        boolean localSpace;
        boolean pendingWorldRelease;
        long spawnNanos;
        long worldReleaseDelayNanos;
        boolean renderInWorldPass;
        boolean worldPreviewBehindLocal;
        float worldPreviewBehindLocalBias;
        boolean ignoreItemRotationForLayer;
        boolean syntheticWorldLocal;
        boolean pendingReleaseSnapshotReady;
        class_243 pendingReleaseWorldPos;
        class_243 pendingReleaseWorldVel;
        class_243 pendingReleaseWorldCenter;
        class_243 pendingReleaseWorldAxis;
        float brightAtDarkVisibilityFactor;
        float brightAtDarkGlowFactor;
        private final String motionMode;
        class_243 orbitCenter;
        class_243 orbitAxis;
        final float orbitRadius;
        float orbitAngle;
        final float orbitAngularVelocity;

        RenderParticle(class_243 class_2432, class_243 class_2433, int n, String string, float f, float f2, float f3, float f4, float f5, float f6, boolean bl, boolean bl2, boolean bl3, boolean bl4, float f7, int n2, boolean bl5, boolean bl6, class_2960 class_29602, float f8, float f9, float f10, boolean bl7, boolean bl8, String string2, boolean bl9, String string3, class_243 class_2434, class_243 class_2435, float f11, float f12, float f13) {
            this.position = class_2432;
            this.velocity = class_2433;
            this.totalTicks = Math.max(1, n);
            this.ageTicks = 0.0f;
            this.ease = string != null && !string.isBlank() ? string : "linear";
            this.sizeStart = f;
            this.sizeEnd = f2;
            this.alphaStart = f3;
            this.alphaEnd = f4;
            this.drag = f5;
            this.gravity = f6;
            this.fullBright = bl;
            this.fullDayLight = bl2;
            this.brightAtDark = bl3;
            this.glowTargetMenu = bl4;
            this.lightPercent = Float.isFinite(f7) ? f7 : -1.0f;
            this.emissiveStrength = class_3532.method_15340((int)n2, (int)0, (int)100);
            this.emissiveBlur = bl5;
            this.emissiveClamp = bl6;
            this.texture = class_29602;
            this.red = f8;
            this.green = f9;
            this.blue = f10;
            this.debug = bl7;
            this.lockInModel = bl8;
            this.lockKey = string2;
            this.localSpace = bl9;
            this.pendingWorldRelease = false;
            this.spawnNanos = System.nanoTime();
            this.worldReleaseDelayNanos = 0L;
            this.renderInWorldPass = false;
            this.worldPreviewBehindLocal = false;
            this.worldPreviewBehindLocalBias = 0.0015f;
            this.ignoreItemRotationForLayer = false;
            this.syntheticWorldLocal = false;
            this.pendingReleaseSnapshotReady = false;
            this.pendingReleaseWorldPos = null;
            this.pendingReleaseWorldVel = null;
            this.pendingReleaseWorldCenter = null;
            this.pendingReleaseWorldAxis = null;
            this.brightAtDarkVisibilityFactor = Float.NaN;
            this.brightAtDarkGlowFactor = Float.NaN;
            this.motionMode = ModelPartsParticleSystem.parseMotionMode(string3, "linear");
            this.orbitCenter = class_2434 == null ? class_2432 : class_2434;
            this.orbitAxis = ModelPartsParticleSystem.normalizeAxis(class_2435);
            this.orbitRadius = Math.max(0.0f, f11);
            this.orbitAngle = f12;
            this.orbitAngularVelocity = f13;
        }

        boolean orbit() {
            return "orbit".equals(this.motionMode) && this.orbitRadius > 1.0E-4f;
        }

        private void enableDelayedWorldRelease(long l) {
            if (this.localSpace) {
                this.pendingWorldRelease = true;
                this.spawnNanos = System.nanoTime();
                this.worldReleaseDelayNanos = Math.max(0L, l);
                this.pendingReleaseSnapshotReady = false;
                this.pendingReleaseWorldPos = null;
                this.pendingReleaseWorldVel = null;
                this.pendingReleaseWorldCenter = null;
                this.pendingReleaseWorldAxis = null;
            }
        }

        private void enableWorldPreviewBehindLocal(float f) {
            this.worldPreviewBehindLocal = true;
            this.worldPreviewBehindLocalBias = class_3532.method_15363((float)f, (float)0.0f, (float)0.05f);
        }

        private void enableIgnoreItemRotationForLayer() {
            this.ignoreItemRotationForLayer = true;
        }

        void enableSyntheticWorldLocal() {
            this.syntheticWorldLocal = true;
        }
    }

    record FaceFrame(class_243 corner, class_243 axisU, class_243 axisV, class_243 normal) {
    }

    record FaceUv(double u, double v) {
    }

    record ParticleEmissiveKey(class_2960 texture, boolean blur, boolean clamp) {
    }
}

