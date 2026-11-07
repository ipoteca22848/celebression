package ru.method.celebression.punchy.client.animation;

import ru.method.celebression.animation.Animation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_630;
import net.minecraft.class_746;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.method.celebression.punchy.client.access.TransformablePart;
import ru.method.celebression.punchy.client.animation.Easing;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class PoseHandler {
    private static final Logger LOGGER;
    private static final boolean DEBUG_COMPAT_IDLE = false;
    private static final float BLEND_OUT_SECONDS = 0.28f;
    private static final float BLEND_IN_SECONDS = 0.2f;
    private static final float BLEND_OUT_SLOW = 1.8f;
    private static final float BLEND_IN_SLOW = 1.0f;
    private static final float ANIM_POS_X_OFFSET = 0.0f;
    private static final float ANIM_POS_Z_OFFSET = 0.0f;
    private static final String ANIM_SPEED_PREFIX;
    private static final Pattern ANIM_SPEED_VALUE_PATTERN;
    private static final float MIN_TIMELINE_SPEED = 0.5f;
    private static final float MAX_TIMELINE_SPEED = 5.5f;
    private static final float DEFAULT_LOOP_SPEED_MAX = 2.0f;
    private static final float DEFAULT_LOOP_SPEED_RAMP = 0.6f;
    private static final float LOOP_SPEED_MAX_LIMIT = 1.0f;
    private static final float LOOP_SPEED_RAMP_LIMIT = 1.0f;
    private static final float ARM_TUNING_SUPPRESS_IN_SECONDS = 0.18f;
    private static final float ARM_TUNING_SUPPRESS_OUT_SECONDS = 0.48f;
    private static final float REBASE_RETURN_SPEED_MULTIPLIER = 1.7f;
    private static final String HIDE_OFFHAND_IF_EMPTY_EFFECT;
    private static final String SHOW_OFFHAND_IF_EMPTY_EFFECT;
    private static final String START_WITH_OFFHAND_EFFECT;
    private static final String START_WITH_OFFHAND_EFFECT_ALIAS;
    private static final String STOP_WITH_OFFHAND_EFFECT;
    private AnimationClip currentClip;
    private float currentTime;
    private float prevTime;
    private float expressionTime;
    private float prevExpressionTime;
    private boolean isPlaying;
    private float blendRemaining;
    private float prevBlendRemaining;
    private float blendInRemaining;
    private float prevBlendInRemaining;
    private final Map<String, Sample> blendFrom = new HashMap<String, Sample>();
    private final Map<String, Sample> blendInFrom = new HashMap<String, Sample>();
    private final Map<String, Sample> rebaseFrom = new HashMap<String, Sample>();
    private final Map<String, Sample> rebaseOrigin = new HashMap<String, Sample>();
    private boolean rebaseActive;
    private boolean rebaseReturnToOrigin;
    private float rebaseStartTime;
    private float rebaseReturnSpeedMultiplier = 1.7f;
    private Float nextRebaseReturnSpeedMultiplier;
    private class_1306 nextRebaseSourceArm;
    private boolean nextRebaseUseBothArms;
    private boolean blendInFromActive;
    private boolean blendInDynamic;
    private AnimationClip blendInClip;
    private float blendInTime;
    private float blendInPrevTime;
    private float blendInSpeedMultiplier = 1.0f;
    private class_1306 activeArm;
    private class_1306 clipArm;
    private boolean clipArmFilter;
    private class_1306 blendArm;
    private boolean blendArmFilter;
    private float blendOutDuration = 0.28f;
    private float blendInDuration = 0.2f;
    private float blendOutSlow = 1.8f;
    private BlendInEasing blendInEasing = BlendInEasing.BACK;
    private Float nextBlendInDuration;
    private Float nextBlendOutDuration;
    private Float nextBlendOutSlow;
    private BlendInEasing nextBlendInEasing;
    private boolean stopAfterCurrent;
    private boolean forceLoop;
    private float speedMultiplier = 1.0f;
    private float timelineSpeedScale = 1.0f;
    private boolean preBlendInActive;
    private boolean holdOnLastFrame;
    private boolean holdReleasePending;
    private boolean hasLooped;
    private boolean skipClearOnStop;
    private class_1306 pendingClearArm;
    private int pendingClearArmTick = -1;
    private class_1306 pendingSkipArm;
    private int pendingSkipArmTick = -1;
    private class_1306 forcedSkipArm;
    private class_1306 nextForcedSkipArm;
    private boolean nextForcedSkipArmSet;
    private float leftArmBlendRemaining;
    private float leftArmBlendPrevRemaining;
    private float rightArmBlendRemaining;
    private float rightArmBlendPrevRemaining;
    private int tickCounter;
    private boolean frozen;
    private boolean swapTranslationYZ;
    private boolean javaBlock;
    private boolean mirrorX;
    private float loopSpeedBoost = 1.0f;
    private float loopSpeedMax = 2.0f;
    private float loopSpeedRamp = 0.6f;
    private boolean ignoreDualHanded;
    private boolean paused;
    private class_1306 pendingOverlapSourceArm;
    private AnimationClip overlapClip;
    private float overlapTime;
    private float overlapPrevTime;
    private float overlapSpeedMultiplier = 1.0f;
    private float overlapBlendRemaining;
    private float overlapPrevBlendRemaining;
    private class_1306 overlapArm;
    private boolean overlapPaused;
    private boolean overlapCompatIdle;
    private final Map<String, Sample> overlapBlendFrom = new HashMap<String, Sample>();
    private float overlapBlendInRemaining;
    private float overlapPrevBlendInRemaining;
    private float overlapBlendInDuration = 0.2f;
    private float armTuningSuppressAlpha;
    private float prevArmTuningSuppressAlpha;
    private boolean latchedKeepTriggerArmTuning;
    private PunchyTuningConfig.CustomFlags overlapCustomFlags;
    private class_1306 latchedTriggerArm;
    private String lastCompatIdleSnapshot = "";

    public PunchyTuningConfig.CustomFlags getOverlapCustomFlags() {
        return this.overlapCustomFlags;
    }

    public void setSecondaryClipAtTimeWithFlags(AnimationClip animationClip, class_1306 class_13062, float f, float f2, PunchyTuningConfig.CustomFlags customFlags) {
        this.setSecondaryClipAtTime(animationClip, class_13062, f, f2);
        this.overlapCustomFlags = customFlags;
    }

    public void setSecondaryClipAtTimeWithBlendFromCurrentAndFlags(AnimationClip animationClip, class_1306 class_13062, float f, float f2, boolean bl, float f3, PunchyTuningConfig.CustomFlags customFlags) {
        this.setSecondaryClipAtTimeWithBlendFromCurrent(animationClip, class_13062, f, f2, bl, f3);
        this.overlapCustomFlags = customFlags;
    }

    public void play(AnimationClip animationClip) {
        this.play(animationClip, 1.0f);
    }

    public void play(AnimationClip animationClip, float f) {
        this.play(animationClip, f, false);
    }

    public void play(AnimationClip animationClip, float f, boolean bl) {
        this.play(animationClip, f, bl, false);
    }

    public void play(AnimationClip animationClip, float f, boolean bl, boolean bl2) {
        this.playInternal(animationClip, f, bl, bl2, false);
    }

    public void playAtTime(AnimationClip animationClip, float f, boolean bl, boolean bl2, float f2) {
        this.playInternal(animationClip, f, bl, bl2, false);
        if (animationClip != null) {
            float f3;
            float f4 = animationClip.getLength();
            if (f4 > 0.0f) {
                if (animationClip.isLoop()) {
                    f3 = f2 % f4;
                    if (f3 < 0.0f) {
                        f3 += f4;
                    }
                } else {
                    f3 = class_3532.method_15363((float)f2, (float)0.0f, (float)f4);
                }
            } else {
                f3 = 0.0f;
            }
            this.currentTime = f3;
            this.prevTime = f3;
            this.expressionTime = f2;
            this.prevExpressionTime = f2;
            this.preBlendInActive = false;
        }
    }

    public void playAtTimeWithBlendFromCurrent(AnimationClip animationClip, float f, boolean bl, float f2) {
        this.playWithBlendFromCurrentDynamic(animationClip, f, bl);
        if (animationClip != null) {
            float f3;
            float f4 = animationClip.getLength();
            if (f4 > 0.0f) {
                if (animationClip.isLoop()) {
                    f3 = f2 % f4;
                    if (f3 < 0.0f) {
                        f3 += f4;
                    }
                } else {
                    f3 = class_3532.method_15363((float)f2, (float)0.0f, (float)f4);
                }
            } else {
                f3 = 0.0f;
            }
            this.currentTime = f3;
            this.prevTime = f3;
            this.expressionTime = f2;
            this.prevExpressionTime = f2;
            this.preBlendInActive = false;
        }
    }

    public void playAtTimeRebasedFromCurrent(AnimationClip animationClip, float f, boolean bl, float f2) {
        this.playAtTimeRebasedFromCurrentInternal(animationClip, f, bl, f2, false);
    }

    public void playAtTimeRebasedFromCurrentReturnToOrigin(AnimationClip animationClip, float f, boolean bl, float f2) {
        this.playAtTimeRebasedFromCurrentInternal(animationClip, f, bl, f2, true);
    }

    public void playAtTimeRebasedFromHandlerReturnToOrigin(PoseHandler poseHandler, AnimationClip animationClip, float f, boolean bl, float f2) {
        if (poseHandler != null && poseHandler != this) {
            AnimationClip animationClip2 = poseHandler.currentClip;
            HashMap<String, Sample> hashMap = new HashMap<String, Sample>(poseHandler.rebaseFrom);
            HashMap<String, Sample> hashMap2 = new HashMap<String, Sample>(poseHandler.rebaseOrigin);
            boolean bl2 = poseHandler.rebaseActive;
            boolean bl3 = poseHandler.rebaseReturnToOrigin;
            float f3 = poseHandler.rebaseStartTime;
            float f4 = poseHandler.rebaseReturnSpeedMultiplier;
            class_1306 class_13062 = poseHandler.clipArmFilter ? poseHandler.clipArm : poseHandler.activeArm;
            float f5 = poseHandler.currentTime;
            float f6 = poseHandler.expressionTime;
            boolean bl4 = animationClip2 != null && (poseHandler.isPlaying || poseHandler.blendRemaining > 0.0f);
            this.playAtTime(animationClip, f, true, bl, f2);
            this.rebaseFrom.clear();
            this.rebaseOrigin.clear();
            this.rebaseActive = false;
            this.rebaseReturnToOrigin = false;
            this.rebaseStartTime = f2;
            this.rebaseReturnSpeedMultiplier = this.nextRebaseReturnSpeedMultiplier != null ? Math.max(0.01f, this.nextRebaseReturnSpeedMultiplier.floatValue()) : 1.7f;
            this.nextRebaseReturnSpeedMultiplier = null;
            if (bl4 && animationClip != null) {
                for (Map.Entry<String, BoneTrack> entry : animationClip.getTracks().entrySet()) {
                    String string = entry.getKey();
                    BoneTrack boneTrack = entry.getValue();
                    if (boneTrack == null) continue;
                    class_1306 class_13063 = this.resolveBoneArm(string);
                    if (class_13062 != null && class_13063 != null && class_13063 != class_13062) continue;
                    Sample sample = poseHandler.sampleVisibleSourceForRebase(string, class_13063, animationClip2, f5, f6, hashMap, hashMap2, bl2, bl3, f3, f4);
                    Sample sample2 = this.sampleTrack(boneTrack, f2, f2, animationClip);
                    this.rebaseFrom.put(string, sample);
                    this.rebaseOrigin.put(string, sample2);
                }
                this.rebaseReturnToOrigin = this.rebaseActive = !this.rebaseFrom.isEmpty();
            }
        } else {
            this.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f, bl, f2);
        }
    }

    private void playAtTimeRebasedFromCurrentInternal(AnimationClip animationClip, float f, boolean bl, float f2, boolean bl2) {
        boolean bl3;
        AnimationClip animationClip2 = this.currentClip;
        HashMap<String, Sample> hashMap = new HashMap<String, Sample>(this.rebaseFrom);
        HashMap<String, Sample> hashMap2 = new HashMap<String, Sample>(this.rebaseOrigin);
        boolean bl4 = this.rebaseActive;
        boolean bl5 = this.rebaseReturnToOrigin;
        float f3 = this.rebaseStartTime;
        float f4 = this.rebaseReturnSpeedMultiplier;
        class_1306 class_13062 = this.nextRebaseSourceArm;
        boolean bl6 = this.nextRebaseUseBothArms;
        this.nextRebaseSourceArm = null;
        this.nextRebaseUseBothArms = false;
        if (bl6) {
            class_13062 = null;
        }
        if (class_13062 == null) {
            if (animationClip2 != null) {
                class_13062 = this.clipArmFilter ? this.clipArm : this.activeArm;
            } else {
                Object object = this.activeArm != null ? this.activeArm : (class_13062 = this.clipArmFilter ? this.clipArm : null);
            }
            if (bl6) {
                class_13062 = null;
            }
        }
        float f5 = this.currentTime;
        float f6 = this.expressionTime;
        boolean bl7 = bl3 = animationClip2 != null && (this.isPlaying || this.blendRemaining > 0.0f);
        if (!bl3 && this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null) {
            animationClip2 = this.overlapClip;
            class_13062 = this.overlapArm;
            f5 = this.overlapTime;
            f6 = this.overlapTime;
            hashMap.clear();
            hashMap2.clear();
            bl4 = false;
            bl5 = false;
            f3 = 0.0f;
            f4 = 1.7f;
            bl3 = true;
        }
        this.playAtTime(animationClip, f, true, bl, f2);
        this.rebaseFrom.clear();
        this.rebaseOrigin.clear();
        this.rebaseActive = false;
        this.rebaseReturnToOrigin = false;
        this.rebaseStartTime = f2;
        this.rebaseReturnSpeedMultiplier = this.nextRebaseReturnSpeedMultiplier != null ? Math.max(0.01f, this.nextRebaseReturnSpeedMultiplier.floatValue()) : 1.7f;
        this.nextRebaseReturnSpeedMultiplier = null;
        if (bl3 && animationClip != null && animationClip2 != animationClip) {
            for (Map.Entry<String, BoneTrack> entry : animationClip.getTracks().entrySet()) {
                String string = entry.getKey();
                BoneTrack boneTrack = entry.getValue();
                if (boneTrack == null) continue;
                class_1306 class_13063 = this.resolveBoneArm(string);
                if (class_13062 != null && class_13063 != null && class_13063 != class_13062) continue;
                Sample sample = this.sampleVisibleSourceForRebase(string, class_13063, animationClip2, f5, f6, hashMap, hashMap2, bl4, bl5, f3, f4);
                Sample sample2 = this.sampleTrack(boneTrack, f2, f2, animationClip);
                this.rebaseFrom.put(string, sample);
                this.rebaseOrigin.put(string, sample2);
            }
            this.rebaseActive = !this.rebaseFrom.isEmpty();
            this.rebaseReturnToOrigin = this.rebaseActive && bl2;
        }
    }

    private Sample sampleVisibleSourceForRebase(String string, class_1306 class_13062, AnimationClip animationClip, float f, float f2, Map<String, Sample> map, Map<String, Sample> map2, boolean bl, boolean bl2, float f3, float f4) {
        BoneTrack boneTrack;
        if (this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null && class_13062 != null && class_13062 == this.overlapArm && !this.isItemGripOrHeldItemBone(string) && (boneTrack = this.overlapClip.getTracks().get(string)) != null) {
            return this.sampleTrack(boneTrack, this.overlapTime, this.overlapTime, this.overlapClip);
        }
        if (animationClip == null) {
            return Sample.identity();
        }
        boneTrack = animationClip.getTracks().get(string);
        return boneTrack == null ? Sample.identity() : this.samplePreviousVisibleTrack(boneTrack, string, f, f2, animationClip, map, map2, bl, bl2, f3, f4);
    }

    private Sample samplePreviousVisibleTrack(BoneTrack boneTrack, String string, float f, float f2, AnimationClip animationClip, Map<String, Sample> map, Map<String, Sample> map2, boolean bl, boolean bl2, float f3, float f4) {
        Sample sample = this.sampleTrack(boneTrack, f, f2, animationClip);
        if (!bl) {
            return sample;
        }
        Sample sample2 = map.get(string);
        Sample sample3 = map2.get(string);
        if (sample2 != null && sample3 != null) {
            Sample sample4 = this.rebaseSample(sample2, sample3, sample);
            if (bl2 && animationClip != null) {
                float f5 = this.resolveCapturedRebaseReturnProgress(animationClip, f2, f3, f4);
                if (f5 <= 0.0f) {
                    return sample4;
                }
                return f5 >= 1.0f ? sample : this.blendBetweenSamples(sample4, sample, f5);
            }
            return sample4;
        }
        return sample;
    }

    private float resolveCapturedRebaseReturnProgress(AnimationClip animationClip, float f, float f2, float f3) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f4 = animationClip.getLength();
        if (f4 <= 0.0f) {
            return 1.0f;
        }
        float f5 = Math.max(0.0f, Math.min(f2, f4));
        if (f <= f5) {
            return 0.0f;
        }
        float f6 = Math.max(1.0E-4f, f4 - f5);
        float f7 = class_3532.method_15363((float)((f - f5) / f6 * Math.max(0.01f, f3)), (float)0.0f, (float)1.0f);
        return this.easeOutCubic(f7);
    }

    public void playWithBlendFromCurrentDynamic(AnimationClip animationClip, float f, boolean bl) {
        AnimationClip animationClip2 = this.currentClip;
        float f2 = this.currentTime;
        float f3 = this.prevTime;
        float f4 = this.speedMultiplier;
        boolean bl2 = animationClip2 != null && (this.isPlaying || this.blendRemaining > 0.0f);
        this.playInternal(animationClip, f, false, bl, true);
        this.blendInFrom.clear();
        this.blendInFromActive = false;
        if (bl2 && this.currentClip == animationClip) {
            this.blendInDynamic = true;
            this.blendInClip = animationClip2;
            this.blendInTime = f2;
            this.blendInPrevTime = f3;
            this.blendInSpeedMultiplier = f4;
            if (this.blendInRemaining <= 0.0f) {
                this.blendInDynamic = false;
                this.blendInClip = null;
                this.blendInTime = 0.0f;
                this.blendInPrevTime = 0.0f;
                this.blendInSpeedMultiplier = 1.0f;
            }
        } else {
            this.blendInDynamic = false;
            this.blendInClip = null;
            this.blendInTime = 0.0f;
            this.blendInPrevTime = 0.0f;
            this.blendInSpeedMultiplier = 1.0f;
        }
    }

    public void playWithBlendFromCurrent(AnimationClip animationClip, float f, boolean bl) {
        if (this.shouldForceBlendInFromVisibleSource()) {
            this.captureVisibleArmSamplesForBlendIn(this.resolveVisibleSourceArm());
        } else if (this.currentClip != null && (this.isPlaying || this.blendRemaining > 0.0f)) {
            this.blendInFrom.clear();
            for (Map.Entry<String, BoneTrack> entry : this.currentClip.getTracks().entrySet()) {
                Sample sample = this.sampleTrack(entry.getValue(), this.currentTime, this.currentClip);
                this.blendInFrom.put(entry.getKey(), sample);
            }
            this.blendInFromActive = !this.blendInFrom.isEmpty();
        } else {
            this.blendInFrom.clear();
            this.blendInFromActive = false;
        }
        this.playInternal(animationClip, f, false, bl, true);
    }

    private void playInternal(AnimationClip animationClip, float f, boolean bl, boolean bl2, boolean bl3) {
        if (!PunchyAnimationManager.shouldBlockClip(animationClip, this.activeArm)) {
            float f2;
            if (!bl3 && this.shouldForceBlendInFromVisibleSource()) {
                this.captureVisibleArmSamplesForBlendIn(this.resolveVisibleSourceArm());
                bl3 = this.blendInFromActive;
            }
            boolean bl4 = false;
            class_1306 class_13062 = null;
            if (!bl4) {
                boolean bl5;
                class_1306 class_13063 = this.pendingOverlapSourceArm != null ? this.pendingOverlapSourceArm : (this.clipArmFilter ? this.clipArm : this.activeArm);
                class_1306 class_13064 = this.activeArm;
                boolean bl6 = this.isPlaying && this.currentClip != null && this.isDualHandedActive(this.currentClip, this.currentTime);
                boolean bl7 = bl5 = animationClip != null && this.isDualHandedActive(animationClip, 0.0f);
                if (bl6 && !bl5 && class_13064 != null) {
                    bl4 = true;
                    class_1306 class_13065 = class_13062 = class_13064 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                }
                if (!bl4) {
                    boolean bl8 = bl4 = this.isPlaying && this.currentClip != null && class_13063 != null && class_13064 != null && animationClip != null && class_13063 != class_13064 && !this.isDualHandedActive(this.currentClip, this.currentTime);
                    if (bl4) {
                        class_13062 = class_13063;
                    }
                }
                if (bl4 && class_13062 != null) {
                    this.preserveOverlapClip(class_13062);
                }
            }
            this.pendingOverlapSourceArm = null;
            boolean bl9 = bl || !bl3 && (this.isPlaying || this.blendRemaining > 0.0f);
            this.currentClip = animationClip;
            if (this.nextForcedSkipArmSet) {
                this.forcedSkipArm = this.nextForcedSkipArm;
                this.nextForcedSkipArm = null;
                this.nextForcedSkipArmSet = false;
            } else {
                this.forcedSkipArm = null;
            }
            this.currentTime = f2 = this.resolveConditionalDualStartTime(animationClip);
            this.prevTime = f2;
            this.expressionTime = f2;
            this.prevExpressionTime = f2;
            this.isPlaying = true;
            this.hasLooped = false;
            this.blendRemaining = 0.0f;
            this.prevBlendRemaining = 0.0f;
            if (this.nextBlendInDuration != null) {
                this.blendInDuration = this.nextBlendInDuration.floatValue();
                this.nextBlendInDuration = null;
            } else {
                this.blendInDuration = 0.2f;
            }
            if (this.nextBlendInEasing != null) {
                this.blendInEasing = this.nextBlendInEasing;
                this.nextBlendInEasing = null;
            } else {
                this.blendInEasing = BlendInEasing.BACK;
            }
            this.blendInRemaining = bl9 ? 0.0f : this.blendInDuration;
            this.prevBlendInRemaining = bl9 ? 0.0f : this.blendInDuration;
            this.blendFrom.clear();
            this.blendArm = null;
            this.blendArmFilter = false;
            if (!bl3) {
                this.blendInFrom.clear();
                this.blendInFromActive = false;
            }
            this.rebaseFrom.clear();
            this.rebaseOrigin.clear();
            this.rebaseActive = false;
            this.rebaseReturnToOrigin = false;
            this.rebaseStartTime = 0.0f;
            this.blendInDynamic = false;
            this.blendInClip = null;
            this.blendInTime = 0.0f;
            this.blendInPrevTime = 0.0f;
            this.blendInSpeedMultiplier = 1.0f;
            this.stopAfterCurrent = false;
            this.forceLoop = false;
            this.speedMultiplier = Math.max(0.01f, f);
            this.preBlendInActive = !bl9 && this.blendInRemaining > 0.0f;
            this.holdOnLastFrame = bl2;
            this.holdReleasePending = false;
            this.paused = false;
            this.clipArm = this.activeArm;
            this.clipArmFilter = this.activeArm != null;
            this.latchCompatKeepTriggerArmState();
            this.clearArmBlend();
            if (animationClip != null && this.activeArm != null && !this.isDualHandedActive(animationClip, 0.0f) && !this.isEquipClip(animationClip)) {
                MovementStateMachine.get().triggerIdleBalance(this.activeArm == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182);
            }
            if (animationClip != null && this.activeArm != null && !this.isEquipClip(animationClip) && !this.isIdleLikeClip(animationClip)) {
                MovementStateMachine.get().triggerActionAnimation(this.activeArm);
            }
        }
    }

    public void setActiveArm(class_1306 class_13062) {
        if (this.isPlaying && this.currentClip != null && class_13062 != null && this.activeArm != null && class_13062 != this.activeArm && !this.isDualHandedActive(this.currentClip, this.currentTime)) {
            this.pendingOverlapSourceArm = this.clipArmFilter ? this.clipArm : this.activeArm;
        }
        this.activeArm = class_13062;
        if (!this.isPlaying) {
            this.clipArm = class_13062;
            this.clipArmFilter = class_13062 != null;
        }
    }

    public void prepareOverlapBlendOut(class_1306 class_13062, AnimationClip animationClip) {
        if (this.isPlaying && this.currentClip != null && class_13062 != null && animationClip != null && !this.isDualHandedActive(this.currentClip, this.currentTime)) {
            class_1306 class_13063;
            class_1306 class_13064 = class_13063 = this.clipArmFilter ? this.clipArm : this.activeArm;
            if (class_13063 != null && class_13063 != class_13062) {
                this.pendingOverlapSourceArm = class_13063;
            }
        }
    }

    public void requestClearArm(class_1306 class_13062) {
        this.pendingClearArm = class_13062;
        this.pendingClearArmTick = this.tickCounter;
    }

    public void requestSkipArm(class_1306 class_13062) {
        this.pendingSkipArm = class_13062;
        this.pendingSkipArmTick = this.tickCounter;
    }

    public void clearRequestedArmFilters() {
        this.pendingClearArm = null;
        this.pendingClearArmTick = -1;
        this.pendingSkipArm = null;
        this.pendingSkipArmTick = -1;
        this.forcedSkipArm = null;
    }

    public void setForcedSkipArmForNextPlay(class_1306 class_13062) {
        this.nextForcedSkipArm = class_13062;
        this.nextForcedSkipArmSet = true;
    }

    public void requestBlendInArm(class_1306 class_13062) {
        if (class_13062 != null && this.currentClip != null && this.isPlaying) {
            this.setArmBlendRemaining(class_13062, 0.2f);
            this.setArmBlendPrevRemaining(class_13062, 0.2f);
        }
    }

    public void setSecondaryClip(AnimationClip animationClip, class_1306 class_13062, float f) {
        this.setSecondaryClipAtTime(animationClip, class_13062, f, 0.0f);
    }

    public void setSecondaryClipAtTime(AnimationClip animationClip, class_1306 class_13062, float f, float f2) {
        if (animationClip != null && class_13062 != null) {
            float f3;
            this.overlapClip = animationClip;
            this.overlapArm = class_13062;
            this.overlapTime = f3 = class_3532.method_15363((float)f2, (float)0.0f, (float)animationClip.getLength());
            this.overlapPrevTime = f3;
            this.overlapSpeedMultiplier = Math.max(0.01f, f);
            this.overlapBlendRemaining = 0.0f;
            this.overlapPrevBlendRemaining = 0.0f;
            this.overlapPaused = false;
            this.overlapCompatIdle = false;
            this.overlapBlendFrom.clear();
            this.overlapBlendInRemaining = 0.0f;
            this.overlapPrevBlendInRemaining = 0.0f;
            this.overlapBlendInDuration = 0.2f;
            this.debugCompatIdle("setSecondary", "clip=" + this.clipName(animationClip) + " arm=" + String.valueOf(class_13062) + " start=" + f3 + " compat=" + this.overlapCompatIdle);
        } else {
            this.clearSecondaryClip();
        }
    }

    public void setSecondaryClipAtTimeWithBlendFromCurrent(AnimationClip animationClip, class_1306 class_13062, float f, float f2) {
        this.setSecondaryClipAtTimeWithBlendFromCurrent(animationClip, class_13062, f, f2, false, 0.2f);
    }

    public void setSecondaryClipAtTimeWithBlendFromCurrent(AnimationClip animationClip, class_1306 class_13062, float f, float f2, boolean bl) {
        this.setSecondaryClipAtTimeWithBlendFromCurrent(animationClip, class_13062, f, f2, bl, 0.2f);
    }

    public void setSecondaryClipAtTimeWithBlendFromCurrent(AnimationClip animationClip, class_1306 class_13062, float f, float f2, boolean bl, float f3) {
        this.setSecondaryClipAtTime(animationClip, class_13062, f, f2);
        if (animationClip != null && class_13062 != null) {
            this.captureVisibleSamplesForTargetClip(this.overlapBlendFrom, class_13062, animationClip, bl);
            if (!this.overlapBlendFrom.isEmpty()) {
                float f4;
                this.overlapBlendInDuration = f4 = Math.max(1.0E-4f, f3);
                this.overlapBlendInRemaining = f4;
                this.overlapPrevBlendInRemaining = f4;
            } else {
                this.overlapBlendInDuration = 0.2f;
            }
            this.debugCompatIdle("setSecondaryBlendFromCurrent", "clip=" + this.clipName(animationClip) + " arm=" + String.valueOf(class_13062) + " armOnly=" + bl + " blendFromSize=" + this.overlapBlendFrom.size());
        }
    }

    public void clearSecondaryClipWithBlendOut() {
        if (this.overlapClip != null && this.overlapArm != null) {
            this.overlapPaused = true;
            this.overlapPrevTime = this.overlapTime;
            this.overlapBlendFrom.clear();
            this.overlapBlendInRemaining = 0.0f;
            this.overlapPrevBlendInRemaining = 0.0f;
            this.overlapBlendInDuration = 0.2f;
            this.overlapPrevBlendRemaining = 0.28f;
            this.overlapBlendRemaining = 0.28f;
            this.debugCompatIdle("clearSecondaryBlendOut", "clip=" + this.clipName(this.overlapClip) + " arm=" + String.valueOf(this.overlapArm) + " compat=" + this.overlapCompatIdle);
        } else {
            this.clearSecondaryClip();
        }
    }

    public void setSecondaryPaused(boolean bl) {
        this.overlapPaused = bl;
    }

    public void setSecondaryCompatIdle(boolean bl) {
        this.overlapCompatIdle = bl && this.overlapClip != null && this.overlapArm != null;
        this.debugCompatIdle("setCompatIdle", "clip=" + this.clipName(this.overlapClip) + " arm=" + String.valueOf(this.overlapArm) + " compat=" + this.overlapCompatIdle);
    }

    public boolean isSecondaryCompatIdle() {
        return this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null;
    }

    public void clearSecondaryClip() {
        this.debugCompatIdle("clearSecondary", "clip=" + this.clipName(this.overlapClip) + " arm=" + String.valueOf(this.overlapArm) + " compat=" + this.overlapCompatIdle);
        this.overlapClip = null;
        this.overlapTime = 0.0f;
        this.overlapPrevTime = 0.0f;
        this.overlapSpeedMultiplier = 1.0f;
        this.overlapBlendRemaining = 0.0f;
        this.overlapPrevBlendRemaining = 0.0f;
        this.overlapArm = null;
        this.overlapPaused = false;
        this.overlapCompatIdle = false;
        this.overlapBlendFrom.clear();
        this.overlapBlendInRemaining = 0.0f;
        this.overlapPrevBlendInRemaining = 0.0f;
        this.overlapBlendInDuration = 0.2f;
        this.pendingOverlapSourceArm = null;
        this.latchedKeepTriggerArmTuning = false;
        this.latchedTriggerArm = null;
        this.overlapCustomFlags = null;
    }

    public void setFrozen(boolean bl) {
        this.frozen = bl;
        if (bl) {
            this.currentTime = 0.0f;
            this.prevTime = 0.0f;
            this.expressionTime = 0.0f;
            this.prevExpressionTime = 0.0f;
        }
    }

    public void setPaused(boolean bl) {
        this.paused = bl;
    }

    public boolean isBlendOutActive() {
        return this.blendRemaining > 0.0f;
    }

    public float getBlendOutProgress(float f) {
        if (this.blendRemaining <= 0.0f) {
            return 1.0f;
        }
        float f2 = class_3532.method_16439((float)f, (float)this.prevBlendRemaining, (float)this.blendRemaining);
        float f3 = 1.0f - f2 / Math.max(1.0E-4f, this.blendOutDuration);
        return class_3532.method_15363((float)Easing.easeOutBack(f3), (float)0.0f, (float)1.0f);
    }

    public void setRebaseSourceArmForNextPlay(class_1306 class_13062) {
        this.nextRebaseSourceArm = class_13062;
    }

    public void setRebaseUseBothArmsForNextPlay(boolean bl) {
        this.nextRebaseUseBothArms = bl;
    }

    public boolean isRebaseReturnToOriginActive() {
        return this.rebaseActive && this.rebaseReturnToOrigin;
    }

    public float getRebaseReturnToOriginProgress(float f) {
        if (this.rebaseActive && this.rebaseReturnToOrigin && this.currentClip != null) {
            float f2 = class_3532.method_16439((float)f, (float)this.prevExpressionTime, (float)this.expressionTime);
            return this.resolveRebaseReturnProgress(this.currentClip, f2);
        }
        return 0.0f;
    }

    public void resetImmediate() {
        this.stop();
        this.currentClip = null;
        this.blendRemaining = 0.0f;
        this.prevBlendRemaining = 0.0f;
        this.blendInRemaining = 0.0f;
        this.prevBlendInRemaining = 0.0f;
        this.blendFrom.clear();
        this.blendInFrom.clear();
        this.blendInFromActive = false;
        this.rebaseReturnToOrigin = false;
        this.rebaseStartTime = 0.0f;
        this.blendInDynamic = false;
        this.blendInClip = null;
        this.blendInTime = 0.0f;
        this.blendInPrevTime = 0.0f;
        this.blendInSpeedMultiplier = 1.0f;
        this.overlapClip = null;
        this.overlapTime = 0.0f;
        this.overlapPrevTime = 0.0f;
        this.overlapSpeedMultiplier = 1.0f;
        this.overlapBlendRemaining = 0.0f;
        this.overlapPrevBlendRemaining = 0.0f;
        this.overlapArm = null;
        this.overlapPaused = false;
        this.pendingOverlapSourceArm = null;
    }

    public void setSwapTranslationYZ(boolean bl) {
        this.swapTranslationYZ = bl;
    }

    public void setJavaBlock(boolean bl) {
        this.javaBlock = bl;
    }

    public void setMirrorX(boolean bl) {
        this.mirrorX = bl;
    }

    public void setLooping(boolean bl) {
        this.forceLoop = bl;
    }

    public void setSpeedMultiplier(float f) {
        this.speedMultiplier = Math.max(0.01f, f);
    }

    public void setTimelineSpeedScale(float f) {
        this.timelineSpeedScale = Math.max(0.01f, f);
    }

    public void setIgnoreDualHanded(boolean bl) {
        this.ignoreDualHanded = bl;
    }

    public boolean isCurrentDualHandedActive() {
        return this.isPlaying && this.currentClip != null ? this.isDualHandedActive(this.currentTime) : false;
    }

    public void setLoopSpeedParams(Float f, Float f2) {
        float f3 = f == null ? 2.0f : f.floatValue();
        float f4 = f2 == null ? 0.6f : f2.floatValue();
        this.loopSpeedMax = class_3532.method_15363((float)f3, (float)1.0f, (float)1.0f);
        this.loopSpeedRamp = class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f);
    }

    public void setBlendInDurationNext(float f) {
        this.nextBlendInDuration = Float.valueOf(Math.max(0.0f, f));
    }

    public void setBlendInEasingNext(BlendInEasing blendInEasing) {
        this.nextBlendInEasing = blendInEasing == null ? BlendInEasing.BACK : blendInEasing;
    }

    public void setBlendOutDurationNext(float f) {
        this.nextBlendOutDuration = Float.valueOf(Math.max(0.0f, f));
    }

    public void setBlendOutSlowNext(float f) {
        this.nextBlendOutSlow = Float.valueOf(Math.max(0.01f, f));
    }

    public void setRebaseReturnSpeedNext(float f) {
        this.nextRebaseReturnSpeedMultiplier = Float.valueOf(Math.max(0.01f, f));
    }

    public void resetLoopSpeedParams() {
        this.setLoopSpeedParams(null, null);
    }

    public void requestStopAfterCurrent() {
        this.stopAfterCurrent = true;
        this.forceLoop = false;
    }

    public boolean isLooping(AnimationClip animationClip) {
        return this.isPlaying && this.currentClip == animationClip;
    }

    public boolean isForceLooping() {
        return this.forceLoop;
    }

    public boolean hasLooped() {
        return this.hasLooped;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public AnimationClip getCurrentClip() {
        return this.currentClip;
    }

    public boolean isCurrentClipOwnedBy(AnimationClip animationClip, class_1306 class_13062) {
        class_1306 class_13063;
        if (animationClip == null || this.currentClip != animationClip) {
            return false;
        }
        if (class_13062 == null) {
            return true;
        }
        class_1306 class_13064 = class_13063 = this.clipArmFilter ? this.clipArm : this.activeArm;
        return class_13063 == null ? this.isDualHandedActive(this.currentClip, this.currentTime) : class_13063 == class_13062;
    }

    public float getCurrentTime() {
        return this.currentTime;
    }

    public AnimationClip getSecondaryClip() {
        return this.overlapClip;
    }

    public float getSecondaryTime() {
        return this.overlapTime;
    }

    public class_1306 getSecondaryArm() {
        return this.overlapArm;
    }

    public float getTuningFactor(float f, class_1306 class_13062) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        if (this.currentClip == null) {
            return 1.0f;
        }
        float f2 = class_3532.method_16439((float)f, (float)this.prevTime, (float)this.currentTime);
        float f3 = class_3532.method_16439((float)f, (float)this.prevExpressionTime, (float)this.expressionTime);
        if (this.preBlendInActive) {
            f2 = 0.0f;
        }
        if (bl3 = this.currentClip.isEffectActive("use_keyframes_v2", f2)) {
            return 0.0f;
        }
        boolean bl4 = bl2 = this.currentClip.hasEffectAny("disable_tuning") && this.isPlaying && this.currentClip.isEffectActive("disable_tuning", f2);
        if (bl2 && this.activeArm != null && class_13062 != null && class_13062 != this.activeArm && !(bl = this.isDualHandedActive(f2))) {
            bl2 = false;
        }
        if (bl2) {
            return 0.0f;
        }
        if (this.isPlaying) {
            return 1.0f;
        }
        return this.blendRemaining > 0.0f ? 1.0f : 1.0f;
    }

    public float getArmTuningFactor(float f, class_1306 class_13062) {
        boolean bl;
        boolean bl2;
        AnimationClip animationClip = this.currentClip;
        float f2 = class_3532.method_16439((float)f, (float)this.prevTime, (float)this.currentTime);
        class_1306 class_13063 = this.activeArm;
        if (animationClip == null && this.overlapCompatIdle && this.overlapClip != null) {
            animationClip = this.overlapClip;
            f2 = class_3532.method_16439((float)f, (float)this.overlapPrevTime, (float)this.overlapTime);
            class_13063 = this.overlapArm;
        }
        if (animationClip == null && this.armTuningSuppressAlpha <= 1.0E-4f && this.prevArmTuningSuppressAlpha <= 1.0E-4f) {
            return 1.0f;
        }
        if (animationClip == null) {
            return 1.0f;
        }
        float f3 = class_3532.method_16439((float)f, (float)this.prevExpressionTime, (float)this.expressionTime);
        if (this.preBlendInActive) {
            f2 = 0.0f;
        }
        boolean bl3 = this.isArmTuningKeepActive(f2);
        boolean bl4 = animationClip.isEffectActive("use_keyframes_v2", f2);
        boolean bl5 = this.isCompatKeepTriggerArmTuningActive(f2, class_13062);
        boolean bl6 = this.latchedKeepTriggerArmTuning && this.latchedTriggerArm != null && class_13062 != null && class_13062 == this.latchedTriggerArm && (this.blendRemaining > 0.0f || this.prevBlendRemaining > 0.0f);
        boolean bl7 = animationClip == this.currentClip && this.isPlaying;
        boolean bl8 = bl2 = animationClip.hasEffectAny("disable_arm_tuning") && (animationClip == this.overlapClip && this.overlapCompatIdle || bl7) && animationClip.isEffectActive("disable_arm_tuning", f2);
        if (this.isWalkOnWaterClip(animationClip)) {
            bl2 = false;
        }
        if (bl3 && !bl2) {
            bl2 = false;
        }
        if (bl2 && class_13063 != null && class_13062 != null && class_13062 != class_13063 && !(bl = this.isDualHandedActive(animationClip, f2))) {
            bl2 = false;
        }
        boolean bl9 = bl = bl7 && this.isEquipClip(animationClip);
        if (!(!bl4 || bl3 || bl2 && bl)) {
            bl2 = false;
        }
        float f4 = class_3532.method_16439((float)f, (float)this.prevArmTuningSuppressAlpha, (float)this.armTuningSuppressAlpha);
        f4 = class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f);
        boolean bl10 = this.isDualHandedActive(animationClip, f2);
        if (!bl10 && class_13063 != null && class_13062 != null && class_13062 != class_13063) {
            f4 = 0.0f;
        }
        if (bl5) {
            bl2 = false;
            f4 = 0.0f;
        }
        if (bl6) {
            bl2 = false;
            f4 = 0.0f;
        }
        float f5 = bl7 ? class_3532.method_15363((float)Easing.easeOutBack(f4), (float)0.0f, (float)1.0f) : 1.0f - class_3532.method_15363((float)Easing.easeOutBack(1.0f - f4), (float)0.0f, (float)1.0f);
        return bl2 ? 0.0f : 1.0f - f5;
    }

    public boolean isPendulumDisabled(float f, class_1306 class_13062) {
        if (this.currentClip != null && this.isPlaying) {
            boolean bl;
            boolean bl2;
            float f2 = class_3532.method_16439((float)f, (float)this.prevTime, (float)this.currentTime);
            if (this.preBlendInActive) {
                f2 = 0.0f;
            }
            if (this.currentClip.isEffectActive("use_keyframes_v2", f2)) {
                return true;
            }
            boolean bl3 = bl2 = this.currentClip.hasEffectAny("disable_pendulum") && this.currentClip.isEffectActive("disable_pendulum", f2) || this.currentClip.hasEffectAny("disable_pendulum_physics") && this.currentClip.isEffectActive("disable_pendulum_physics", f2);
            if (bl2 && this.activeArm != null && class_13062 != null && class_13062 != this.activeArm && !(bl = this.isDualHandedActive(f2))) {
                bl2 = false;
            }
            return bl2;
        }
        return false;
    }

    public void stop() {
        this.isPlaying = false;
        this.blendRemaining = 0.0f;
        this.prevBlendRemaining = 0.0f;
        this.blendInRemaining = 0.0f;
        this.prevBlendInRemaining = 0.0f;
        this.blendFrom.clear();
        this.blendInFrom.clear();
        this.blendInFromActive = false;
        this.blendInDynamic = false;
        this.blendInClip = null;
        this.blendInTime = 0.0f;
        this.blendInPrevTime = 0.0f;
        this.blendInSpeedMultiplier = 1.0f;
        this.blendOutSlow = 1.8f;
        this.activeArm = null;
        this.clipArm = null;
        this.clipArmFilter = false;
        this.blendArm = null;
        this.blendArmFilter = false;
        this.blendOutDuration = 0.28f;
        if (this.nextBlendInDuration != null) {
            this.blendInDuration = this.nextBlendInDuration.floatValue();
            this.nextBlendInDuration = null;
        } else {
            this.blendInDuration = 0.2f;
        }
        this.stopAfterCurrent = false;
        this.forceLoop = false;
        this.speedMultiplier = 1.0f;
        this.preBlendInActive = false;
        this.holdOnLastFrame = false;
        this.holdReleasePending = false;
        this.expressionTime = this.currentTime;
        this.prevExpressionTime = this.prevTime;
        this.skipClearOnStop = false;
        this.pendingClearArm = null;
        this.pendingClearArmTick = -1;
        this.pendingSkipArm = null;
        this.pendingSkipArmTick = -1;
        this.forcedSkipArm = null;
        this.nextForcedSkipArm = null;
        this.nextForcedSkipArmSet = false;
        this.clearArmBlend();
        this.frozen = false;
        this.paused = false;
        this.loopSpeedBoost = 1.0f;
        this.resetLoopSpeedParams();
        this.overlapClip = null;
        this.overlapTime = 0.0f;
        this.overlapPrevTime = 0.0f;
        this.overlapSpeedMultiplier = 1.0f;
        this.overlapBlendRemaining = 0.0f;
        this.overlapPrevBlendRemaining = 0.0f;
        this.overlapArm = null;
        this.overlapPaused = false;
        this.overlapCompatIdle = false;
        this.pendingOverlapSourceArm = null;
        this.overlapBlendInDuration = 0.2f;
        this.latchedKeepTriggerArmTuning = false;
        this.latchedTriggerArm = null;
        this.clearArmTuningSuppression();
    }

    public void stopWithBlendOut() {
        if (this.currentClip == null) {
            this.stop();
        } else {
            this.holdOnLastFrame = false;
            if (this.isPlaying) {
                this.requestStopAfterCurrent();
            } else {
                this.beginBlendOut();
            }
        }
    }

    public void stopImmediateWithBlendOut() {
        if (this.currentClip == null) {
            this.stop();
        } else {
            this.holdOnLastFrame = false;
            this.holdReleasePending = false;
            this.skipClearOnStop = false;
            this.pendingClearArm = null;
            this.pendingClearArmTick = -1;
            this.pendingSkipArm = null;
            this.pendingSkipArmTick = -1;
            this.clearArmBlend();
            this.frozen = false;
            this.paused = false;
            this.blendInFrom.clear();
            this.blendInFromActive = false;
            this.blendInDynamic = false;
            this.blendInClip = null;
            this.blendInTime = 0.0f;
            this.blendInPrevTime = 0.0f;
            this.blendInSpeedMultiplier = 1.0f;
            this.blendOutSlow = 1.8f;
            this.beginBlendOut();
            this.overlapClip = null;
            this.overlapTime = 0.0f;
            this.overlapPrevTime = 0.0f;
            this.overlapSpeedMultiplier = 1.0f;
            this.overlapBlendRemaining = 0.0f;
            this.overlapPrevBlendRemaining = 0.0f;
            this.overlapArm = null;
            this.overlapPaused = false;
        }
    }

    public void stopWithoutClear() {
        this.isPlaying = false;
        this.blendRemaining = 0.0f;
        this.prevBlendRemaining = 0.0f;
        this.blendInRemaining = 0.0f;
        this.prevBlendInRemaining = 0.0f;
        this.blendFrom.clear();
        this.blendInFrom.clear();
        this.blendInFromActive = false;
        this.blendInDynamic = false;
        this.blendInClip = null;
        this.blendInTime = 0.0f;
        this.blendInPrevTime = 0.0f;
        this.blendInSpeedMultiplier = 1.0f;
        this.blendOutSlow = 1.8f;
        this.stopAfterCurrent = false;
        this.forceLoop = false;
        this.speedMultiplier = 1.0f;
        this.preBlendInActive = false;
        this.holdOnLastFrame = false;
        this.holdReleasePending = false;
        this.expressionTime = this.currentTime;
        this.prevExpressionTime = this.prevTime;
        this.skipClearOnStop = true;
        this.pendingClearArm = null;
        this.pendingClearArmTick = -1;
        this.pendingSkipArm = null;
        this.pendingSkipArmTick = -1;
        this.clearArmBlend();
        this.frozen = false;
        this.paused = false;
        this.loopSpeedBoost = 1.0f;
        this.resetLoopSpeedParams();
        this.overlapClip = null;
        this.overlapTime = 0.0f;
        this.overlapPrevTime = 0.0f;
        this.overlapSpeedMultiplier = 1.0f;
        this.overlapBlendRemaining = 0.0f;
        this.overlapPrevBlendRemaining = 0.0f;
        this.overlapArm = null;
        this.overlapPaused = false;
        this.overlapCompatIdle = false;
        this.overlapBlendInDuration = 0.2f;
        this.latchedKeepTriggerArmTuning = false;
        this.latchedTriggerArm = null;
        this.clearArmTuningSuppression();
    }

    public void tick(float f, float f2) {
        float f3;
        ++this.tickCounter;
        this.tickArmTuningSuppression(f);
        float f4 = f2;
        float f5 = this.speedMultiplier;
        if (this.isEquipClip(this.currentClip)) {
            f4 = 1.0f;
            f5 = 1.0f;
        } else if (this.isConsumeClip(this.currentClip)) {
            f4 = 1.0f;
        } else if (this.isFixedSpeedClip(this.currentClip)) {
            f4 = 1.0f;
        }
        float f6 = this.resolveTimelineSpeed(this.currentClip, this.currentTime);
        if (!Float.isNaN(f6)) {
            f4 = f6 * this.timelineSpeedScale;
            f5 = 1.0f;
        }
        boolean bl = this.isPlaying && this.currentClip != null && (this.currentClip.isLoop() || this.forceLoop) && !this.stopAfterCurrent;
        this.loopSpeedBoost = 1.0f;
        float f7 = f * f4 * f5 * this.loopSpeedBoost;
        if (this.blendInDynamic && this.blendInClip != null) {
            f3 = this.computeAnimDelta(this.blendInClip, this.blendInTime, f, f2, this.blendInSpeedMultiplier);
            this.blendInPrevTime = this.blendInTime;
            this.blendInTime = this.advanceClipTime(this.blendInClip, this.blendInTime, f3);
        }
        if (this.overlapClip != null) {
            f3 = f2;
            float f8 = this.overlapSpeedMultiplier;
            if (this.isEquipClip(this.overlapClip)) {
                f3 = 1.0f;
                f8 = 1.0f;
            } else if (this.isConsumeClip(this.overlapClip)) {
                f3 = 1.0f;
            } else if (this.isFixedSpeedClip(this.overlapClip)) {
                f3 = 1.0f;
            }
            float f9 = this.resolveTimelineSpeed(this.overlapClip, this.overlapTime);
            if (!Float.isNaN(f9)) {
                f3 = f9 * this.timelineSpeedScale;
                f8 = 1.0f;
            }
            float f10 = this.overlapPaused ? 0.0f : f * f3 * f8;
            this.overlapPrevTime = this.overlapTime;
            this.overlapTime += f10;
            if (this.overlapTime >= this.overlapClip.getLength()) {
                if (this.overlapClip.isLoop() && this.overlapClip.getLength() > 0.0f) {
                    this.overlapTime %= this.overlapClip.getLength();
                    this.overlapPrevTime = this.overlapTime;
                } else {
                    this.overlapTime = this.overlapClip.getLength();
                    if (this.overlapBlendRemaining <= 0.0f) {
                        this.overlapPrevBlendRemaining = this.overlapBlendRemaining = 0.28f;
                    }
                }
            }
            if (this.overlapBlendRemaining > 0.0f) {
                this.overlapPrevBlendRemaining = this.overlapBlendRemaining;
                this.overlapBlendRemaining = Math.max(0.0f, this.overlapBlendRemaining - f / 1.8f);
                if (this.overlapBlendRemaining == 0.0f) {
                    this.overlapClip = null;
                    this.overlapArm = null;
                    this.overlapBlendFrom.clear();
                    this.overlapBlendInRemaining = 0.0f;
                    this.overlapPrevBlendInRemaining = 0.0f;
                }
            }
            if (this.overlapBlendInRemaining > 0.0f) {
                this.overlapPrevBlendInRemaining = this.overlapBlendInRemaining;
                this.overlapBlendInRemaining = Math.max(0.0f, this.overlapBlendInRemaining - f / 1.0f);
            }
        }
        if (this.isPlaying && this.currentClip != null) {
            if (this.blendInRemaining > 0.0f) {
                this.prevBlendInRemaining = this.blendInRemaining;
                this.blendInRemaining = Math.max(0.0f, this.blendInRemaining - f7 / 1.0f);
                if (this.blendInRemaining == 0.0f) {
                    this.preBlendInActive = false;
                    this.blendInFrom.clear();
                    this.blendInFromActive = false;
                    this.blendInDynamic = false;
                    this.blendInClip = null;
                    this.blendInTime = 0.0f;
                    this.blendInPrevTime = 0.0f;
                    this.blendInSpeedMultiplier = 1.0f;
                }
            }
            this.tickArmBlend(f7);
            if (!this.preBlendInActive) {
                if (this.frozen) {
                    this.prevTime = 0.0f;
                    this.currentTime = 0.0f;
                    this.prevExpressionTime = 0.0f;
                    this.expressionTime = 0.0f;
                } else {
                    f3 = this.paused ? 0.0f : f7;
                    this.prevTime = this.currentTime;
                    this.currentTime += f3;
                    this.prevExpressionTime = this.expressionTime;
                    this.expressionTime += f3;
                }
            }
            if (this.currentTime >= this.currentClip.getLength()) {
                if (this.holdOnLastFrame) {
                    if (!this.holdReleasePending) {
                        this.prevTime = this.currentTime = this.currentClip.getLength();
                        return;
                    }
                    this.holdOnLastFrame = false;
                    this.holdReleasePending = false;
                }
                if ((this.currentClip.isLoop() || this.forceLoop) && !this.stopAfterCurrent) {
                    this.currentTime %= this.currentClip.getLength();
                    this.prevTime = this.currentTime - f7;
                    this.hasLooped = true;
                } else {
                    this.beginBlendOut();
                    this.stopAfterCurrent = false;
                    this.forceLoop = false;
                    this.prevBlendRemaining = this.blendRemaining;
                    this.blendRemaining = Math.max(0.0f, this.blendRemaining - f / 1.8f);
                }
            }
            if (this.rebaseActive && this.rebaseReturnToOrigin && this.currentClip != null && this.resolveRebaseReturnProgress(this.currentClip, this.expressionTime) >= 1.0f) {
                this.rebaseFrom.clear();
                this.rebaseOrigin.clear();
                this.rebaseActive = false;
                this.rebaseReturnToOrigin = false;
                this.rebaseStartTime = 0.0f;
            }
            if (this.hasLooped && this.currentClip != null && this.activeArm != null) {
                this.hasLooped = false;
                if (!this.isEquipClip(this.currentClip) && !this.isDualHandedActive(0.0f)) {
                    MovementStateMachine.get().triggerIdleBalance(this.activeArm == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182);
                }
            }
        } else if (this.blendRemaining > 0.0f) {
            this.prevBlendRemaining = this.blendRemaining;
            this.blendRemaining = Math.max(0.0f, this.blendRemaining - f / this.blendOutSlow);
        }
    }

    public boolean isOffhandHiddenIfEmptyActive() {
        return this.currentClip == null ? false : this.isOffhandHiddenIfEmptyActiveAtTime(this.currentTime);
    }

    public boolean isOffhandHiddenIfEmptyActive(float f) {
        if (this.currentClip == null) {
            return false;
        }
        float f2 = class_3532.method_16439((float)f, (float)this.prevTime, (float)this.currentTime);
        if (this.preBlendInActive) {
            f2 = 0.0f;
        }
        return this.isOffhandHiddenIfEmptyActiveAtTime(f2);
    }

    private boolean isOffhandHiddenIfEmptyActiveAtTime(float f) {
        if (this.currentClip != null && this.isRealOffhandEmpty()) {
            float f2 = this.currentClip.getLastEffectTime("hide_offhand_if_empty", f);
            float f3 = this.currentClip.getLastEffectTime("show_offhand_if_empty", f);
            boolean bl = !Float.isNaN(f2);
            boolean bl2 = !Float.isNaN(f3);
            return bl && (!bl2 || f2 > f3);
        }
        return false;
    }

    private class_1306 resolveTimelineForcedSkipArm(float f, class_1306 class_13062) {
        if (!this.isOffhandHiddenIfEmptyActiveAtTime(f)) {
            return class_13062;
        }
        class_1306 class_13063 = this.resolvePlayerOffhandArm();
        return class_13063 == null ? class_13062 : class_13063;
    }

    private boolean isRealOffhandEmpty() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102 == null || class_3102.field_1724 == null || class_3102.field_1724.method_6079().method_7960();
    }

    private class_1306 resolvePlayerOffhandArm() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1306 class_13062 = class_3102.field_1724.method_6068();
            return class_13062 == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
        }
        return null;
    }

    private boolean isPlayerOffhandArm(class_1306 class_13062) {
        return class_13062 != null && class_13062 == this.resolvePlayerOffhandArm();
    }

    private float computeAnimDelta(AnimationClip animationClip, float f, float f2, float f3, float f4) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f5 = f3;
        float f6 = f4;
        if (this.isEquipClip(animationClip)) {
            f5 = 1.0f;
            f6 = 1.0f;
        } else if (this.isConsumeClip(animationClip)) {
            f5 = 1.0f;
        } else if (this.isFixedSpeedClip(animationClip)) {
            f5 = 1.0f;
        }
        float f7 = this.resolveTimelineSpeed(animationClip, f);
        if (!Float.isNaN(f7)) {
            f5 = f7;
            f6 = 1.0f;
        }
        return f2 * f5 * f6;
    }

    private float advanceClipTime(AnimationClip animationClip, float f, float f2) {
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

    private boolean isEquipClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return string != null && (string.equals("hand_in") || string.equals("hand_out") || string.startsWith("hand_in_") || string.startsWith("hand_out_"));
    }

    private boolean isWalkOnWaterClip(AnimationClip animationClip) {
        return animationClip != null && animationClip.getName() != null ? animationClip.getName().contains("walk_on_water") : false;
    }

    private boolean isConsumeClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return "use_eat".equals(string) || "use_drink".equals(string);
    }

    private boolean isIdleLikeClip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return string == null ? false : "idle".equals(string) || string.startsWith("idle_");
    }

    private boolean isFixedSpeedClip(AnimationClip animationClip) {
        return animationClip == null ? false : "falling_in_water".equals(animationClip.getName());
    }

    private float resolveTimelineSpeed(AnimationClip animationClip, float f) {
        if (animationClip == null) {
            return Float.NaN;
        }
        float f2 = Float.NaN;
        for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffectsUpTo(f)) {
            for (String string : entry.getValue()) {
                String string2;
                String string3;
                if (string == null || string.isBlank() || !(string3 = string.toLowerCase(Locale.ROOT).trim()).startsWith("anim_speed_") || (string2 = string3.substring("anim_speed_".length())).isEmpty()) continue;
                try {
                    String string4;
                    float f3;
                    Matcher matcher = ANIM_SPEED_VALUE_PATTERN.matcher(string2);
                    if (!matcher.find() || !Float.isFinite(f3 = Float.parseFloat(string4 = matcher.group().replace(',', '.')))) continue;
                    f2 = class_3532.method_15363((float)f3, (float)0.5f, (float)5.5f);
                }
                catch (NumberFormatException numberFormatException) {}
            }
        }
        return f2;
    }

    public void releaseHold() {
        if (this.holdOnLastFrame && this.currentClip != null) {
            if (this.currentTime >= this.currentClip.getLength()) {
                this.holdOnLastFrame = false;
            } else {
                this.holdReleasePending = true;
            }
        }
    }

    public void applyTransformations(Map<String, class_630> map, float f) {
        this.applyTransformations(map, f, true);
    }

    public void applyTransformations(Map<String, class_630> map, float f, boolean bl) {
        if (this.pendingClearArm != null && this.pendingClearArmTick != this.tickCounter) {
            this.pendingClearArm = null;
            this.pendingClearArmTick = -1;
        }
        if (this.pendingSkipArm != null && this.pendingSkipArmTick != this.tickCounter) {
            this.pendingSkipArm = null;
            this.pendingSkipArmTick = -1;
        }
        class_1306 class_13062 = this.pendingClearArm;
        class_1306 class_13063 = this.pendingSkipArm;
        class_1306 class_13064 = this.forcedSkipArm;
        if (this.currentClip != null || this.overlapCompatIdle && this.overlapClip != null) {
            String string;
            float f2;
            float f3;
            float f4;
            if (this.currentClip != null && !this.isPlaying && this.blendRemaining <= 0.0f) {
                boolean bl2;
                boolean bl3 = bl2 = this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null;
                if (!this.skipClearOnStop && !bl2) {
                    if (bl) {
                        this.clearTransforms(map);
                    } else {
                        this.clearTransformsForClipArm(map);
                    }
                }
                this.skipClearOnStop = false;
                this.currentClip = null;
                this.activeArm = null;
                this.clipArm = null;
                this.clipArmFilter = false;
                this.blendArm = null;
                this.blendArmFilter = false;
                if (!this.overlapCompatIdle || this.overlapClip == null) {
                    return;
                }
            }
            if (class_13062 != null) {
                this.clearTransformsForArm(map, class_13062);
            }
            float f5 = class_3532.method_16439((float)f, (float)this.prevTime, (float)this.currentTime);
            float f6 = class_3532.method_16439((float)f, (float)this.prevExpressionTime, (float)this.expressionTime);
            if (this.preBlendInActive) {
                f5 = 0.0f;
            }
            if ((class_13064 = this.resolveTimelineForcedSkipArm(f5, class_13064)) != null && class_13064 != class_13062) {
                this.clearTransformsForArm(map, class_13064);
            }
            float f7 = 0.0f;
            if (this.blendRemaining > 0.0f) {
                f4 = class_3532.method_16439((float)f, (float)this.prevBlendRemaining, (float)this.blendRemaining);
                f7 = 1.0f - f4 / Math.max(1.0E-4f, this.blendOutDuration);
                f7 = Easing.easeOutBack(f7);
            }
            f4 = 1.0f;
            if (this.isPlaying && this.blendInRemaining > 0.0f) {
                f3 = class_3532.method_16439((float)f, (float)this.prevBlendInRemaining, (float)this.blendInRemaining);
                f4 = 1.0f - f3 / Math.max(1.0E-4f, this.blendInDuration);
                f4 = this.applyBlendInEasing(f4);
            }
            f3 = class_3532.method_16439((float)f, (float)this.blendInPrevTime, (float)this.blendInTime);
            boolean bl4 = this.isDualHandedActive(f5);
            float f8 = class_3532.method_16439((float)f, (float)this.overlapPrevTime, (float)this.overlapTime);
            boolean bl5 = this.overlapClip != null && this.isDualHandedActive(this.overlapClip, f8);
            float f9 = 0.0f;
            if (this.overlapBlendRemaining > 0.0f) {
                f2 = class_3532.method_16439((float)f, (float)this.overlapPrevBlendRemaining, (float)this.overlapBlendRemaining);
                f9 = 1.0f - f2 / Math.max(1.0E-4f, 0.28f);
                f9 = Easing.easeOutBack(f9);
            }
            f2 = 1.0f;
            if (this.overlapBlendInRemaining > 0.0f) {
                float f10 = class_3532.method_16439((float)f, (float)this.overlapPrevBlendInRemaining, (float)this.overlapBlendInRemaining);
                f2 = 1.0f - f10 / Math.max(1.0E-4f, this.overlapBlendInDuration);
                f2 = this.applyBlendInEasing(f2);
            }
            Map<String, PartPoseSnapshot> map2 = this.capturePartSnapshots(map);
            if (this.currentClip != null) {
                for (Map.Entry<String, BoneTrack> entry : this.currentClip.getTracks().entrySet()) {
                    boolean bl6;
                    string = entry.getKey();
                    BoneTrack track = entry.getValue();
                    if (class_13062 != null && this.isBoneForArm(string, class_13062) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064)) continue;
                    boolean bl7 = bl6 = this.blendRemaining > 0.0f && this.blendArmFilter && this.isBoneForArm(string, this.blendArm) && (this.activeArm == null || !this.isBoneForArm(string, this.activeArm));
                    class_630 part = map.get(string);
                    if (!bl4 && this.isPlaying && this.activeArm != null && !this.isBoneForArm(string, this.activeArm) && !bl6 || !bl4 && !this.isPlaying && this.blendRemaining > 0.0f && this.blendArmFilter && !this.isBoneForArm(string, this.blendArm) || part == null) continue;
                    Sample sampled;
                    if (!(this.blendRemaining > 0.0f) || this.isPlaying && !bl6) {
                        sampled = this.sampleTrack(track, f5, f6, this.currentClip);
                        if (this.rebaseActive) {
                            Sample rebaseFromSample = this.rebaseFrom.get(string);
                            Sample rebaseOriginSample = this.rebaseOrigin.get(string);
                            if (rebaseFromSample != null && rebaseOriginSample != null) {
                                sampled = this.rebaseSampleForCurrentClip(string, rebaseFromSample, rebaseOriginSample, sampled, f6);
                            }
                        }
                        float f10 = f4;
                        class_1306 boneArm = this.resolveBoneArm(string);
                        if (this.isPlaying && boneArm != null) {
                            float f11 = this.resolveArmBlendInT(boneArm, f);
                            f10 = f4 * f11;
                        }
                        if (f10 < 1.0f) {
                            if (this.blendInDynamic && this.blendInClip != null) {
                                BoneTrack blendTrack = this.blendInClip.getTracks().get(string);
                                Sample blendSample = blendTrack != null ? this.sampleTrack(blendTrack, f3, f3, this.blendInClip) : null;
                                sampled = blendSample != null ? this.blendBetweenSamples(blendSample, sampled, f10) : this.blendFromIdentity(sampled, f10);
                            } else {
                                Sample blendFromSample = this.blendInFromActive ? this.blendInFrom.get(string) : null;
                                sampled = blendFromSample != null ? this.blendBetweenSamples(blendFromSample, sampled, f10) : this.blendFromIdentity(sampled, f10);
                            }
                        }
                    } else {
                        Sample blendSample = this.blendFrom.get(string);
                        if (blendSample == null) continue;
                        sampled = this.blendToIdentity(blendSample, f7);
                    }
                    boolean bl8 = this.currentClip.isEffectActive("use_keyframes_v2", f5);
                    if (bl8) {
                        this.applyV2ToPart(part, map2.get(string), sampled);
                        continue;
                    }
                    Object partObject = part;
                    if (!(partObject instanceof TransformablePart transformablePart)) continue;
                    Matrix4f sampleMatrix = this.matrixFromSample(sampled, false);
                    Matrix4f partMatrix = new Matrix4f().translate(part.field_3657, part.field_3656, part.field_3655)
                            .rotateZ(part.field_3674).rotateY(part.field_3675).rotateX(part.field_3654)
                            .scale(part.field_37938, part.field_37939, part.field_37940);
                    transformablePart.punchy$setExplicitTransform(partMatrix.mul(sampleMatrix));
                }
            }
            if (this.overlapClip != null && this.overlapArm != null) {
                for (Map.Entry<String, BoneTrack> entry : this.overlapClip.getTracks().entrySet()) {
                    boolean bl9;
                    float f12;
                    string = entry.getKey();
                    class_630 part = map.get(string);
                    if (!bl5 && !this.isBoneForArm(string, this.overlapArm) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064) || part == null) continue;
                    if (this.currentClip != null && this.currentClip.getTracks().containsKey(string)) {
                        boolean bl10 = false;
                        if (this.isPlaying) {
                            bl10 = bl4 || this.activeArm == null || this.isBoneForArm(string, this.activeArm);
                        } else if (!(this.blendRemaining > 0.0f)) {
                            bl10 = !this.isEquipClip(this.currentClip);
                        } else {
                            boolean bl11 = bl10 = !this.overlapCompatIdle && !this.isEquipClip(this.currentClip) && (!this.blendArmFilter || this.blendArm == null || this.isBoneForArm(string, this.blendArm));
                        }
                        if (bl10) continue;
                    }
                    Sample sample = this.sampleTrack(entry.getValue(), f8, f8, this.overlapClip);
                    Sample overlapBlendSample = this.overlapBlendFrom.get(string);
                    if (overlapBlendSample != null && f2 < 1.0f) {
                        sample = this.blendBetweenSamples(overlapBlendSample, sample, f2);
                    }
                    class_1306 resolvedArm = this.resolveBoneArm(string);
                    if ((f12 = this.resolveArmBlendInT(resolvedArm != null ? resolvedArm : this.overlapArm, f)) < 1.0f) {
                        sample = this.blendFromIdentity(sample, f12);
                    }
                    if (this.overlapBlendRemaining > 0.0f) {
                        sample = this.blendToIdentity(sample, f9);
                    }
                    boolean bl12 = bl9 = this.overlapClip != null && this.overlapClip.isEffectActive("use_keyframes_v2", f8);
                    if (bl9) {
                        this.applyV2ToPart(part, map2.get(string), sample);
                        continue;
                    }
                    Object partObject = part;
                    if (!(partObject instanceof TransformablePart transformablePart)) continue;
                    Matrix4f sampleMatrix = this.matrixFromSample(sample, false);
                    Matrix4f partMatrix = new Matrix4f().translate(part.field_3657, part.field_3656, part.field_3655)
                            .rotateZ(part.field_3674).rotateY(part.field_3675).rotateX(part.field_3654)
                            .scale(part.field_37938, part.field_37939, part.field_37940);
                    transformablePart.punchy$setExplicitTransform(partMatrix.mul(sampleMatrix));
                }
            }
            if (this.isPlaying && this.blendRemaining > 0.0f && this.blendArmFilter && this.blendArm != null) {
                for (Map.Entry<String, Sample> entry : this.blendFrom.entrySet()) {
                    boolean bl13;
                    string = entry.getKey();
                    class_630 part = map.get(string);
                    if (!this.isBoneForArm(string, this.blendArm) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064) || this.currentClip.getTracks().containsKey(string) || part == null) continue;
                    Sample sample = this.blendToIdentity(entry.getValue(), f7);
                    boolean bl14 = bl13 = this.currentClip != null && this.currentClip.isEffectActive("use_keyframes_v2", f5);
                    if (bl13) {
                        this.applyV2ToPart(part, map2.get(string), sample);
                        continue;
                    }
                    Object partObject = part;
                    if (!(partObject instanceof TransformablePart transformablePart)) continue;
                    Matrix4f sampleMatrix = this.matrixFromSample(sample, false);
                    Matrix4f partMatrix = new Matrix4f().translate(part.field_3657, part.field_3656, part.field_3655)
                            .rotateZ(part.field_3674).rotateY(part.field_3675).rotateX(part.field_3654)
                            .scale(part.field_37938, part.field_37939, part.field_37940);
                    transformablePart.punchy$setExplicitTransform(partMatrix.mul(sampleMatrix));
                }
            }
            if (this.isPlaying) {
                if (!bl) {
                    return;
                }
                for (Map.Entry<String, class_630> entry : map.entrySet()) {
                    string = entry.getKey();
                    if (string == null || this.overlapClip != null && this.overlapArm != null && (bl5 || this.isBoneForArm(string, this.overlapArm)) || class_13062 != null && this.isBoneForArm(string, class_13062) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064) || this.blendRemaining > 0.0f && this.blendArmFilter && this.isBoneForArm(string, this.blendArm)) continue;
                    if (!bl4 && this.activeArm != null && !this.isBoneForArm(string, this.activeArm)) {
                        this.clearPartTransform((class_630)entry.getValue());
                        continue;
                    }
                    if (this.currentClip.getTracks().containsKey(string)) continue;
                    this.clearPartTransform((class_630)entry.getValue());
                }
            } else if (this.blendRemaining > 0.0f) {
                if (!bl) {
                    return;
                }
                for (Map.Entry<String, class_630> entry : map.entrySet()) {
                    string = entry.getKey();
                    if (string == null || this.overlapClip != null && this.overlapArm != null && (bl5 || this.isBoneForArm(string, this.overlapArm)) || class_13062 != null && this.isBoneForArm(string, class_13062) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064)) continue;
                    if (!bl4 && this.blendArmFilter && !this.isBoneForArm(string, this.blendArm)) {
                        this.clearPartTransform((class_630)entry.getValue());
                        continue;
                    }
                    if (this.blendFrom.containsKey(string)) continue;
                    this.clearPartTransform((class_630)entry.getValue());
                }
            } else if (this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null) {
                if (!bl) {
                    return;
                }
                for (Map.Entry<String, class_630> entry : map.entrySet()) {
                    string = entry.getKey();
                    if (string == null || class_13062 != null && this.isBoneForArm(string, class_13062) || class_13063 != null && this.isBoneForArm(string, class_13063) || class_13064 != null && this.isBoneForArm(string, class_13064) || (bl5 || this.isBoneForArm(string, this.overlapArm)) && this.overlapClip.getTracks().containsKey(string)) continue;
                    this.clearPartTransform((class_630)entry.getValue());
                }
            }
        } else if (bl) {
            this.clearTransforms(map);
        }
    }

    private void beginBlendOut() {
        if (this.currentClip != null) {
            this.isPlaying = false;
            this.blendInRemaining = 0.0f;
            this.prevBlendInRemaining = 0.0f;
            if (this.nextBlendOutDuration != null) {
                this.blendOutDuration = this.nextBlendOutDuration.floatValue();
                this.nextBlendOutDuration = null;
            } else {
                this.blendOutDuration = 0.28f;
            }
            if (this.nextBlendOutSlow != null) {
                this.blendOutSlow = this.nextBlendOutSlow.floatValue();
                this.nextBlendOutSlow = null;
            } else {
                this.blendOutSlow = 1.8f;
            }
            this.prevBlendRemaining = this.blendOutDuration;
            this.blendRemaining = this.blendOutDuration;
            this.blendFrom.clear();
            class_1306 class_13062 = this.activeArm != null ? this.activeArm : (this.clipArmFilter ? this.clipArm : null);
            boolean bl = this.isDualHandedActive(this.currentTime);
            this.blendArm = class_13062;
            this.blendArmFilter = class_13062 != null && !bl;
            for (Map.Entry<String, BoneTrack> entry : this.currentClip.getTracks().entrySet()) {
                if (this.blendArmFilter && !this.isBoneForArm(entry.getKey(), this.blendArm)) continue;
                Sample sample = this.sampleTrack(entry.getValue(), this.currentTime, this.expressionTime, this.currentClip);
                if (this.rebaseActive) {
                    Sample sample2 = this.rebaseFrom.get(entry.getKey());
                    Sample sample3 = this.rebaseOrigin.get(entry.getKey());
                    if (sample2 != null && sample3 != null) {
                        sample = this.rebaseSampleForCurrentClip(entry.getKey(), sample2, sample3, sample, this.expressionTime);
                    }
                }
                this.blendFrom.put(entry.getKey(), sample);
            }
            this.speedMultiplier = 1.0f;
            this.timelineSpeedScale = 1.0f;
            this.preBlendInActive = false;
            this.holdOnLastFrame = false;
            this.holdReleasePending = false;
            this.clearArmBlend();
        }
    }

    private void beginOverlapBlendOut(class_1306 class_13062) {
        if (this.currentClip != null && class_13062 != null) {
            this.prevBlendRemaining = this.blendOutDuration = 0.28f;
            this.blendRemaining = this.blendOutDuration;
            this.blendFrom.clear();
            boolean bl = this.isDualHandedActive(this.currentTime);
            this.blendArm = class_13062;
            this.blendArmFilter = class_13062 != null && !bl;
            for (Map.Entry<String, BoneTrack> entry : this.currentClip.getTracks().entrySet()) {
                if (this.blendArmFilter && !this.isBoneForArm(entry.getKey(), this.blendArm)) continue;
                Sample sample = this.sampleTrack(entry.getValue(), this.currentTime, this.expressionTime, this.currentClip);
                if (this.rebaseActive) {
                    Sample sample2 = this.rebaseFrom.get(entry.getKey());
                    Sample sample3 = this.rebaseOrigin.get(entry.getKey());
                    if (sample2 != null && sample3 != null) {
                        sample = this.rebaseSampleForCurrentClip(entry.getKey(), sample2, sample3, sample, this.expressionTime);
                    }
                }
                this.blendFrom.put(entry.getKey(), sample);
            }
        }
    }

    private void preserveOverlapClip(class_1306 class_13062) {
        if (this.currentClip != null && class_13062 != null) {
            this.overlapClip = this.currentClip;
            this.overlapTime = this.currentTime;
            this.overlapPrevTime = this.prevTime;
            this.overlapSpeedMultiplier = this.speedMultiplier;
            this.overlapBlendRemaining = 0.0f;
            this.overlapPrevBlendRemaining = 0.0f;
            this.overlapArm = class_13062;
        }
    }

    private void captureCurrentArmSamplesInto(Map<String, Sample> map, class_1306 class_13062) {
        if (map != null) {
            map.clear();
            if (class_13062 != null) {
                AnimationClip animationClip = this.currentClip;
                float f = this.currentTime;
                float f2 = this.expressionTime;
                boolean bl = false;
                if (animationClip == null && this.overlapCompatIdle && this.overlapClip != null && this.overlapArm == class_13062) {
                    animationClip = this.overlapClip;
                    f = this.overlapTime;
                    f2 = this.overlapTime;
                    bl = true;
                }
                if (animationClip != null) {
                    for (Map.Entry<String, BoneTrack> entry : animationClip.getTracks().entrySet()) {
                        String string = entry.getKey();
                        if (!this.isBoneForArm(string, class_13062)) continue;
                        Sample sample = this.sampleTrack(entry.getValue(), f, f2, animationClip);
                        if (!bl && this.rebaseActive) {
                            Sample sample2 = this.rebaseFrom.get(string);
                            Sample sample3 = this.rebaseOrigin.get(string);
                            if (sample2 != null && sample3 != null) {
                                sample = this.rebaseSampleForCurrentClip(string, sample2, sample3, sample, this.expressionTime);
                            }
                        }
                        map.put(string, sample);
                    }
                }
            }
        }
    }

    private void captureVisibleArmSamplesForBlendIn(class_1306 class_13062) {
        this.blendInFrom.clear();
        this.blendInFromActive = false;
        if (class_13062 != null) {
            this.captureVisibleArmSamplesInto(this.blendInFrom, class_13062);
            this.blendInFromActive = !this.blendInFrom.isEmpty();
        }
    }

    private void captureVisibleArmSamplesInto(Map<String, Sample> map, class_1306 class_13062) {
        if (map != null) {
            map.clear();
            if (class_13062 != null) {
                if (this.overlapCompatIdle && this.overlapClip != null && this.overlapArm == class_13062) {
                    for (Map.Entry<String, BoneTrack> entry : this.overlapClip.getTracks().entrySet()) {
                        String string = entry.getKey();
                        if (!this.isBoneForArm(string, class_13062)) continue;
                        map.put(string, this.sampleTrack(entry.getValue(), this.overlapTime, this.overlapTime, this.overlapClip));
                    }
                    if (!map.isEmpty()) {
                        return;
                    }
                }
                this.captureCurrentArmSamplesInto(map, class_13062);
            }
        }
    }

    private void captureVisibleSamplesForTargetClip(Map<String, Sample> map, class_1306 class_13062, AnimationClip animationClip) {
        this.captureVisibleSamplesForTargetClip(map, class_13062, animationClip, false);
    }

    private void captureVisibleSamplesForTargetClip(Map<String, Sample> map, class_1306 class_13062, AnimationClip animationClip, boolean bl) {
        if (map != null) {
            map.clear();
            if (class_13062 != null && animationClip != null) {
                AnimationClip animationClip2 = this.currentClip;
                float f = this.currentTime;
                float f2 = this.expressionTime;
                Map<String, Sample> map2 = this.rebaseFrom;
                Map<String, Sample> map3 = this.rebaseOrigin;
                boolean bl2 = this.rebaseActive;
                boolean bl3 = this.rebaseReturnToOrigin;
                float f3 = this.rebaseStartTime;
                float f4 = this.rebaseReturnSpeedMultiplier;
                if (animationClip2 == null && this.overlapCompatIdle && this.overlapClip != null && this.overlapArm == class_13062) {
                    animationClip2 = this.overlapClip;
                    f = this.overlapTime;
                    f2 = this.overlapTime;
                    map2 = Map.of();
                    map3 = Map.of();
                    bl2 = false;
                    bl3 = false;
                    f3 = 0.0f;
                    f4 = 1.7f;
                }
                for (Map.Entry<String, BoneTrack> entry : animationClip.getTracks().entrySet()) {
                    class_1306 class_13063;
                    String string = entry.getKey();
                    if (bl && this.isItemGripOrHeldItemBone(string) || (class_13063 = this.resolveBoneArm(string)) != null && class_13063 != class_13062) continue;
                    Sample sample = this.sampleVisibleSourceForRebase(string, class_13063, animationClip2, f, f2, map2, map3, bl2, bl3, f3, f4);
                    map.put(string, sample);
                }
            }
        }
    }

    private boolean isItemGripOrHeldItemBone(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        return string2.startsWith("item_") || string2.endsWith("_item") || string2.startsWith("itemgrip_") || string2.endsWith("_itemgrip") || string2.contains("itemgrip");
    }

    private boolean shouldForceBlendInFromVisibleSource() {
        return !this.isPlaying && this.blendRemaining <= 0.0f && this.overlapCompatIdle && this.overlapClip != null && this.overlapArm != null;
    }

    private void debugCompatIdle(String string, String string2) {
    }

    private String clipName(AnimationClip animationClip) {
        return animationClip == null ? "null" : animationClip.getName();
    }

    private class_1306 resolveVisibleSourceArm() {
        if (this.activeArm != null) {
            return this.activeArm;
        }
        if (this.clipArmFilter && this.clipArm != null) {
            return this.clipArm;
        }
        return this.overlapCompatIdle && this.overlapArm != null ? this.overlapArm : null;
    }

    private Matrix4f calculateMatrix(BoneTrack boneTrack, float f) {
        Sample sample = this.sampleTrack(boneTrack, f, f, this.currentClip);
        return this.matrixFromSample(sample, this.currentClip != null && this.currentClip.isEffectActive("use_keyframes_v2", f));
    }

    private Matrix4f matrixFromKeyframe(Keyframe keyframe) {
        Vector3f vector3f = keyframe.sampledPosition(this.expressionTime);
        Vector3f vector3f2 = keyframe.sampledRotationEuler(this.expressionTime);
        Vector3f vector3f3 = keyframe.sampledScale(this.expressionTime);
        Quaternionf quaternionf = this.quatFromEuler(vector3f2, this.currentClip, this.currentTime);
        return new Matrix4f().translate((Vector3fc)vector3f).rotate((Quaternionfc)quaternionf).scale((Vector3fc)vector3f3);
    }

    private Sample sampleTrack(BoneTrack boneTrack, float f, AnimationClip animationClip) {
        return this.sampleTrack(boneTrack, f, f, animationClip);
    }

    private Sample sampleTrack(BoneTrack boneTrack, float f2, float f3, AnimationClip animationClip) {
        TreeMap<Float, Keyframe> treeMap = boneTrack.getTimeIndex();
        boolean bl = animationClip != null && animationClip.isEffectActive("use_keyframes_v2", f2);
        Keyframe keyframe2 = this.findPrevWithPosition(treeMap, f2);
        Keyframe keyframe3 = this.findNextWithPosition(treeMap, f2);
        Vector3f vector3f = this.sampleVectorChannel(keyframe2, keyframe3, f2, f3, new Vector3f(0.0f, 0.0f, 0.0f), bl ? (keyframe, f) -> keyframe.sampledPositionV2(f.floatValue()) : (keyframe, f) -> keyframe.sampledPosition(f.floatValue()));
        Keyframe keyframe4 = this.findPrevWithRotation(treeMap, f2);
        Keyframe keyframe5 = this.findNextWithRotation(treeMap, f2);
        Quaternionf quaternionf = this.sampleRotationChannel(treeMap, keyframe4, keyframe5, f2, f3, animationClip);
        Vector3f vector3f2 = bl ? this.sampleRotationEulerChannelV2(treeMap, keyframe4, keyframe5, f2, f3) : null;
        Keyframe keyframe6 = this.findPrevWithScale(treeMap, f2);
        Keyframe keyframe7 = this.findNextWithScale(treeMap, f2);
        Vector3f vector3f3 = this.sampleVectorChannel(keyframe6, keyframe7, f2, f3, new Vector3f(1.0f, 1.0f, 1.0f), (keyframe, f) -> keyframe.sampledScale(f.floatValue()));
        return new Sample(vector3f, quaternionf, vector3f3, vector3f2);
    }

    private static String fmt(Vector3f vector3f) {
        return vector3f == null ? "null" : String.format(Locale.ROOT, "[%.3f, %.3f, %.3f]", Float.valueOf(vector3f.x), Float.valueOf(vector3f.y), Float.valueOf(vector3f.z));
    }

    private static String fmt(Quaternionf quaternionf) {
        return quaternionf == null ? "null" : String.format(Locale.ROOT, "[%.5f, %.5f, %.5f, %.5f]", Float.valueOf(quaternionf.x), Float.valueOf(quaternionf.y), Float.valueOf(quaternionf.z), Float.valueOf(quaternionf.w));
    }

    private static String fmt(float f) {
        return String.format(Locale.ROOT, "%.3f", Float.valueOf(f));
    }

    private Keyframe findPrevWithPosition(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.floorKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.position() != null || keyframe.positionExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.lowerKey(f2);
        }
        return null;
    }

    private Keyframe findNextWithPosition(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.ceilingKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.position() != null || keyframe.positionExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.higherKey(f2);
        }
        return null;
    }

    private Keyframe findPrevWithRotation(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.floorKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.rotationEuler() != null || keyframe.rotationExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.lowerKey(f2);
        }
        return null;
    }

    private Keyframe findNextWithRotation(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.ceilingKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.rotationEuler() != null || keyframe.rotationExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.higherKey(f2);
        }
        return null;
    }

    private Keyframe findPrevWithScale(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.floorKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.scale() != null || keyframe.scaleExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.lowerKey(f2);
        }
        return null;
    }

    private Keyframe findNextWithScale(TreeMap<Float, Keyframe> treeMap, float f) {
        Float f2 = treeMap.ceilingKey(Float.valueOf(f));
        while (f2 != null) {
            Keyframe keyframe = treeMap.get(f2);
            if (keyframe != null && (keyframe.scale() != null || keyframe.scaleExpression() != null)) {
                return keyframe;
            }
            f2 = treeMap.higherKey(f2);
        }
        return null;
    }

    private Vector3f sampleVectorChannel(Keyframe keyframe, Keyframe keyframe2, float f, float f2, Vector3f vector3f, BiFunction<Keyframe, Float, Vector3f> biFunction) {
        if (keyframe == null && keyframe2 == null) {
            return new Vector3f((Vector3fc)vector3f);
        }
        if (keyframe == null) {
            Vector3f vector3f2 = biFunction.apply(keyframe2, Float.valueOf(f2));
            return vector3f2 == null ? new Vector3f((Vector3fc)vector3f) : new Vector3f((Vector3fc)vector3f2);
        }
        if (keyframe2 == null) {
            Vector3f vector3f3 = biFunction.apply(keyframe, Float.valueOf(f2));
            return vector3f3 == null ? new Vector3f((Vector3fc)vector3f) : new Vector3f((Vector3fc)vector3f3);
        }
        Vector3f vector3f4 = biFunction.apply(keyframe, Float.valueOf(f2));
        Vector3f vector3f5 = biFunction.apply(keyframe2, Float.valueOf(f2));
        if (vector3f4 == null && vector3f5 == null) {
            return new Vector3f((Vector3fc)vector3f);
        }
        if (vector3f4 == null) {
            return new Vector3f((Vector3fc)vector3f5);
        }
        if (vector3f5 == null) {
            return new Vector3f((Vector3fc)vector3f4);
        }
        float f3 = keyframe.timeStamp();
        float f4 = keyframe2.timeStamp();
        float f5 = f4 - f3;
        float f6 = f5 == 0.0f ? 0.0f : (f - f3) / f5;
        return new Vector3f((Vector3fc)vector3f4).lerp((Vector3fc)vector3f5, f6);
    }

    private Quaternionf sampleRotationChannel(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2, AnimationClip animationClip) {
        boolean bl;
        boolean bl2 = bl = animationClip != null && animationClip.isEffectActive("use_keyframes_v2", f);
        if (bl) {
            return this.sampleRotationChannelV2(treeMap, keyframe, keyframe2, f, f2);
        }
        if (keyframe == null && keyframe2 == null) {
            return new Quaternionf();
        }
        if (keyframe == null) {
            Vector3f vector3f = keyframe2.sampledRotationEuler(f2);
            return vector3f == null ? new Quaternionf() : this.quatFromEuler(vector3f, animationClip, f);
        }
        if (keyframe2 == null) {
            Vector3f vector3f = keyframe.sampledRotationEuler(f2);
            return vector3f == null ? new Quaternionf() : this.quatFromEuler(vector3f, animationClip, f);
        }
        Vector3f vector3f = keyframe.sampledRotationEuler(f2);
        Vector3f vector3f2 = keyframe2.sampledRotationEuler(f2);
        if (vector3f == null && vector3f2 == null) {
            return new Quaternionf();
        }
        if (vector3f == null) {
            return this.quatFromEuler(vector3f2, animationClip, f);
        }
        if (vector3f2 == null) {
            return this.quatFromEuler(vector3f, animationClip, f);
        }
        float f3 = keyframe.timeStamp();
        float f4 = keyframe2.timeStamp();
        float f5 = f4 - f3;
        float f6 = f5 == 0.0f ? 0.0f : (f - f3) / f5;
        f6 = class_3532.method_15363((float)f6, (float)0.0f, (float)1.0f);
        Vector3f vector3f3 = new Vector3f(PoseHandler.lerpAngleDegrees(vector3f.x, vector3f2.x, f6), PoseHandler.lerpAngleDegrees(vector3f.y, vector3f2.y, f6), PoseHandler.lerpAngleDegrees(vector3f.z, vector3f2.z, f6));
        return this.quatFromEuler(vector3f3, animationClip, f);
    }

    private Quaternionf sampleRotationChannelV2(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2) {
        Vector3f vector3f = this.sampleRotationEulerChannelV2(treeMap, keyframe, keyframe2, f, f2);
        return vector3f == null ? new Quaternionf() : PoseHandler.quatFromEulerV2(vector3f);
    }

    private Vector3f sampleRotationEulerChannelV2(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2) {
        if (keyframe == null && keyframe2 == null) {
            return null;
        }
        if (keyframe == null) {
            return keyframe2.sampledRotationPreEulerV2(f2);
        }
        if (keyframe2 == null) {
            return keyframe.sampledRotationPostEulerV2(f2);
        }
        if (keyframe != keyframe2 && !(Math.abs(keyframe.timeStamp() - keyframe2.timeStamp()) <= 1.0E-4f)) {
            float f3 = keyframe.timeStamp();
            float f4 = keyframe2.timeStamp();
            float f5 = f4 - f3;
            float f6 = f5 == 0.0f ? 0.0f : (f - f3) / f5;
            f6 = class_3532.method_15363((float)f6, (float)0.0f, (float)1.0f);
            String string = PoseHandler.normalizeInterpolation(keyframe.interpolation());
            String string2 = PoseHandler.normalizeInterpolation(keyframe2.interpolation());
            if ("spherical_squad".equals(string) || "spherical_squad".equals(string2)) {
                Quaternionf quaternionf = this.sampleRotationSphericalSquadV2(treeMap, keyframe, keyframe2, f2, f6);
                return quaternionf.getEulerAnglesXYZ(new Vector3f()).mul(57.29578f);
            }
            if ("spherical_linear".equals(string) || "spherical_linear".equals(string2)) {
                Quaternionf quaternionf = PoseHandler.quatFromEulerV2(keyframe.sampledRotationPostEulerV2(f2));
                Quaternionf quaternionf2 = PoseHandler.quatFromEulerV2(keyframe2.sampledRotationPreEulerV2(f2));
                return quaternionf.slerp((Quaternionfc)quaternionf2, f6).getEulerAnglesXYZ(new Vector3f()).mul(57.29578f);
            }
            if (!"catmullrom".equals(string) && !"catmullrom".equals(string2)) {
                Vector3f vector3f = keyframe.sampledRotationPostEulerV2(f2);
                Vector3f vector3f2 = keyframe2.sampledRotationPreEulerV2(f2);
                if (vector3f == null && vector3f2 == null) {
                    return null;
                }
                if (vector3f == null) {
                    return vector3f2;
                }
                return vector3f2 == null ? vector3f : new Vector3f((Vector3fc)vector3f).lerp((Vector3fc)vector3f2, f6);
            }
            return this.sampleRotationCatmullRomEulerV2(treeMap, keyframe, keyframe2, f2, f6);
        }
        return keyframe.sampledRotationPreEulerV2(f2);
    }

    private Quaternionf sampleRotationCatmullRomV2(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2) {
        Vector3f vector3f = this.sampleRotationCatmullRomEulerV2(treeMap, keyframe, keyframe2, f, f2);
        return vector3f == null ? new Quaternionf() : PoseHandler.quatFromEulerV2(vector3f);
    }

    private Vector3f sampleRotationCatmullRomEulerV2(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2) {
        Keyframe keyframe3 = this.findPrevWithRotation(treeMap, keyframe.timeStamp() - 1.0E-4f);
        Keyframe keyframe4 = this.findNextWithRotation(treeMap, keyframe2.timeStamp() + 1.0E-4f);
        Vector3f vector3f = this.getRotationValueForSpline(keyframe3 != null ? keyframe3 : keyframe, true, f);
        Vector3f vector3f2 = this.getRotationValueForSpline(keyframe, false, f);
        Vector3f vector3f3 = this.getRotationValueForSpline(keyframe2, false, f);
        Vector3f vector3f4 = this.getRotationValueForSpline(keyframe4 != null ? keyframe4 : keyframe2, false, f);
        return new Vector3f(PoseHandler.splineCurve(vector3f.x, vector3f2.x, vector3f3.x, vector3f4.x, f2), PoseHandler.splineCurve(vector3f.y, vector3f2.y, vector3f3.y, vector3f4.y, f2), PoseHandler.splineCurve(vector3f.z, vector3f2.z, vector3f3.z, vector3f4.z, f2));
    }

    private Quaternionf sampleRotationSphericalSquadV2(TreeMap<Float, Keyframe> treeMap, Keyframe keyframe, Keyframe keyframe2, float f, float f2) {
        Keyframe keyframe3 = this.findPrevWithRotation(treeMap, keyframe.timeStamp() - 1.0E-4f);
        Keyframe keyframe4 = this.findNextWithRotation(treeMap, keyframe2.timeStamp() + 1.0E-4f);
        Quaternionf quaternionf = PoseHandler.quatFromEulerV2(this.getRotationValueForSpline(keyframe3 != null ? keyframe3 : keyframe, true, f));
        Quaternionf quaternionf2 = PoseHandler.quatFromEulerV2(this.getRotationValueForSpline(keyframe, false, f));
        Quaternionf quaternionf3 = PoseHandler.quatFromEulerV2(this.getRotationValueForSpline(keyframe2, false, f));
        Quaternionf quaternionf4 = PoseHandler.quatFromEulerV2(this.getRotationValueForSpline(keyframe4 != null ? keyframe4 : keyframe2, false, f));
        return PoseHandler.squad(quaternionf, quaternionf2, quaternionf3, quaternionf4, f2);
    }

    private Vector3f getRotationValueForSpline(Keyframe keyframe, boolean bl, float f) {
        if (keyframe == null) {
            return new Vector3f();
        }
        Vector3f vector3f = bl ? keyframe.sampledRotationPostEulerV2(f) : keyframe.sampledRotationPreEulerV2(f);
        return vector3f == null ? new Vector3f() : vector3f;
    }

    private static String normalizeInterpolation(String string) {
        return string != null && !string.isBlank() ? string.trim().toLowerCase(Locale.ROOT) : "linear";
    }

    private static float splineCurve(float f, float f2, float f3, float f4, float f5) {
        float f6 = (f3 - f) * 0.5f;
        float f7 = (f4 - f2) * 0.5f;
        float f8 = f5 * f5;
        float f9 = f5 * f8;
        float f10 = 2.0f * f9 - 3.0f * f8 + 1.0f;
        float f11 = -2.0f * f9 + 3.0f * f8;
        float f12 = f9 - 2.0f * f8 + f5;
        float f13 = f9 - f8;
        return f10 * f2 + f11 * f3 + f12 * f6 + f13 * f7;
    }

    private static float lerpAngleDegrees(float f, float f2, float f3) {
        float f4 = f2 - f;
        float f5 = Math.abs(f4);
        if (f5 > 180.0f && f5 < 359.9999f) {
            f4 -= Math.signum(f4) * 360.0f;
        }
        return f + f4 * f3;
    }

    private Sample blendToIdentity(Sample sample, float f) {
        Vector3f vector3f = new Vector3f((Vector3fc)sample.pos).lerp((Vector3fc)new Vector3f(0.0f, 0.0f, 0.0f), f);
        Quaternionf quaternionf = new Quaternionf((Quaternionfc)sample.rot).slerp((Quaternionfc)new Quaternionf(), f);
        Vector3f vector3f2 = new Vector3f((Vector3fc)sample.scl).lerp((Vector3fc)new Vector3f(1.0f, 1.0f, 1.0f), f);
        Vector3f vector3f3 = sample.rotEulerV2 == null ? null : new Vector3f((Vector3fc)sample.rotEulerV2).lerp((Vector3fc)new Vector3f(), f);
        return new Sample(vector3f, quaternionf, vector3f2, vector3f3);
    }

    private Quaternionf quatFromEuler(Vector3f vector3f, AnimationClip animationClip, float f) {
        boolean bl;
        float f2 = (float)Math.toRadians(vector3f.x);
        float f3 = (float)Math.toRadians(vector3f.y);
        float f4 = (float)Math.toRadians(vector3f.z);
        if (this.javaBlock) {
            float f5 = (float)Math.toRadians(-vector3f.x);
            float f6 = (float)Math.toRadians(-vector3f.z);
            float f7 = (float)Math.toRadians(-vector3f.y);
            return new Quaternionf().rotationXYZ(f5, f6, f7);
        }
        boolean bl2 = animationClip != null && (animationClip.isEffectActive("rotation_xyz", f) || animationClip.isEffectActive("rot_xyz", f));
        boolean bl3 = animationClip != null && (animationClip.isEffectActive("rotation_zyx", f) || animationClip.isEffectActive("rot_zyx", f));
        String string = animationClip != null && animationClip.getName() != null ? animationClip.getName() : "";
        boolean bl4 = bl = animationClip != null && (string.contains("mace") || string.contains("trident") || string.contains("crossbow"));
        if (bl3) {
            bl = false;
        } else if (bl2) {
            bl = true;
        }
        return bl ? new Quaternionf().rotationXYZ(f2, f3, f4) : new Quaternionf().rotationZYX(f4, f3, f2);
    }

    private static Quaternionf quatFromEulerV2(Vector3f vector3f) {
        if (vector3f == null) {
            return new Quaternionf();
        }
        float f = (float)Math.toRadians(vector3f.x);
        float f2 = (float)Math.toRadians(vector3f.y);
        float f3 = (float)Math.toRadians(vector3f.z);
        return new Quaternionf().rotationXYZ(f, f2, f3);
    }

    private static Quaternionf squad(Quaternionf quaternionf, Quaternionf quaternionf2, Quaternionf quaternionf3, Quaternionf quaternionf4, float f) {
        Quaternionf quaternionf5 = PoseHandler.intermediate(quaternionf, quaternionf2, quaternionf3);
        Quaternionf quaternionf6 = PoseHandler.intermediate(quaternionf2, quaternionf3, quaternionf4);
        Quaternionf quaternionf7 = new Quaternionf((Quaternionfc)quaternionf2).slerp((Quaternionfc)quaternionf3, f);
        Quaternionf quaternionf8 = new Quaternionf((Quaternionfc)quaternionf5).slerp((Quaternionfc)quaternionf6, f);
        return quaternionf7.slerp((Quaternionfc)quaternionf8, 2.0f * f * (1.0f - f));
    }

    private static Quaternionf intermediate(Quaternionf quaternionf, Quaternionf quaternionf2, Quaternionf quaternionf3) {
        Quaternionf quaternionf4 = new Quaternionf((Quaternionfc)quaternionf);
        Quaternionf quaternionf5 = new Quaternionf((Quaternionfc)quaternionf3);
        if (quaternionf2.dot(quaternionf4) < 0.0f) {
            quaternionf4.mul(-1.0f);
        }
        if (quaternionf2.dot(quaternionf5) < 0.0f) {
            quaternionf5.mul(-1.0f);
        }
        Quaternionf quaternionf6 = new Quaternionf((Quaternionfc)quaternionf4).conjugate();
        Quaternionf quaternionf7 = new Quaternionf((Quaternionfc)quaternionf2).conjugate();
        Quaternionf quaternionf8 = quaternionf6.mul((Quaternionfc)new Quaternionf((Quaternionfc)quaternionf2));
        Quaternionf quaternionf9 = quaternionf7.mul((Quaternionfc)new Quaternionf((Quaternionfc)quaternionf5));
        Quaternionf quaternionf10 = PoseHandler.quaternionLog(quaternionf8);
        Quaternionf quaternionf11 = PoseHandler.quaternionLog(quaternionf9);
        Quaternionf quaternionf12 = new Quaternionf(
                quaternionf10.x - quaternionf11.x,
                quaternionf10.y - quaternionf11.y,
                quaternionf10.z - quaternionf11.z,
                quaternionf10.w - quaternionf11.w).mul(0.25f);
        Quaternionf quaternionf13 = PoseHandler.quaternionExp(quaternionf12);
        return new Quaternionf((Quaternionfc)quaternionf2).mul((Quaternionfc)quaternionf13);
    }

    private static Quaternionf quaternionLog(Quaternionf quaternionf) {
        double d = Math.sqrt(quaternionf.x * quaternionf.x + quaternionf.y * quaternionf.y + quaternionf.z * quaternionf.z);
        double d2 = Math.atan2(d, quaternionf.w);
        Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)quaternionf);
        if (d > 5.0E-4) {
            quaternionf2.mul((float)(d2 / d));
        }
        quaternionf2.w = 0.0f;
        return quaternionf2;
    }

    private static Quaternionf quaternionExp(Quaternionf quaternionf) {
        double d = Math.sqrt(quaternionf.x * quaternionf.x + quaternionf.y * quaternionf.y + quaternionf.z * quaternionf.z);
        double d2 = Math.cos(d);
        Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)quaternionf);
        if (d2 < 0.9995 && d > 1.0E-7) {
            quaternionf2.mul((float)(Math.sin(d) / d));
        }
        quaternionf2.w = (float)d2;
        return quaternionf2;
    }

    private Sample blendFromIdentity(Sample sample, float f) {
        Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f).lerp((Vector3fc)sample.pos, f);
        Quaternionf quaternionf = new Quaternionf().slerp((Quaternionfc)sample.rot, f);
        Vector3f vector3f2 = new Vector3f(1.0f, 1.0f, 1.0f).lerp((Vector3fc)sample.scl, f);
        Vector3f vector3f3 = sample.rotEulerV2 == null ? null : new Vector3f((Vector3fc)sample.rotEulerV2).mul(f);
        return new Sample(vector3f, quaternionf, vector3f2, vector3f3);
    }

    private Sample blendBetweenSamples(Sample sample, Sample sample2, float f) {
        Vector3f vector3f = new Vector3f((Vector3fc)sample.pos).lerp((Vector3fc)sample2.pos, f);
        Quaternionf quaternionf = new Quaternionf((Quaternionfc)sample.rot).slerp((Quaternionfc)sample2.rot, f);
        Vector3f vector3f2 = new Vector3f((Vector3fc)sample.scl).lerp((Vector3fc)sample2.scl, f);
        Vector3f vector3f3 = null;
        if (sample.rotEulerV2 != null || sample2.rotEulerV2 != null) {
            Vector3f vector3f4 = sample.rotEulerV2 != null ? new Vector3f((Vector3fc)sample.rotEulerV2) : new Vector3f();
            Vector3f vector3f5 = sample2.rotEulerV2 != null ? new Vector3f((Vector3fc)sample2.rotEulerV2) : new Vector3f();
            vector3f3 = vector3f4.lerp((Vector3fc)vector3f5, f);
        }
        return new Sample(vector3f, quaternionf, vector3f2, vector3f3);
    }

    private Sample rebaseSample(Sample sample, Sample sample2, Sample sample3) {
        Vector3f vector3f = new Vector3f((Vector3fc)sample3.pos).sub((Vector3fc)sample2.pos);
        Vector3f vector3f2 = new Vector3f((Vector3fc)sample.pos).add((Vector3fc)vector3f);
        Quaternionf quaternionf = new Quaternionf((Quaternionfc)sample2.rot).invert();
        Quaternionf quaternionf2 = quaternionf.mul((Quaternionfc)new Quaternionf((Quaternionfc)sample3.rot), new Quaternionf());
        Quaternionf quaternionf3 = new Quaternionf((Quaternionfc)sample.rot).mul((Quaternionfc)quaternionf2).normalize();
        Vector3f vector3f3 = new Vector3f((Vector3fc)sample3.scl);
        return new Sample(vector3f2, quaternionf3, vector3f3, sample3.rotEulerV2);
    }

    private Sample rebaseSampleForCurrentClip(String string, Sample sample, Sample sample2, Sample sample3, float f) {
        if (this.shouldBypassRebaseForBone(string)) {
            return sample3;
        }
        Sample sample4 = this.rebaseSample(sample, sample2, sample3);
        if (this.rebaseReturnToOrigin && this.currentClip != null) {
            float f2 = this.resolveRebaseReturnProgress(this.currentClip, f);
            if (f2 <= 0.0f) {
                return sample4;
            }
            return f2 >= 1.0f ? sample3 : this.blendBetweenSamples(sample4, sample3, f2);
        }
        return sample4;
    }

    private boolean shouldBypassRebaseForBone(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.toLowerCase(Locale.ROOT);
            return string2.contains("eyelid") || string2.contains("pupil") || string2.startsWith("eye_") || string2.endsWith("_eye") || string2.equals("eye") || string2.contains("fin_") || string2.endsWith("_fin");
        }
        return false;
    }

    private float resolveRebaseReturnProgress(AnimationClip animationClip, float f) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f2 = animationClip.getLength();
        if (f2 <= 0.0f) {
            return 1.0f;
        }
        float f3 = Math.max(0.0f, Math.min(this.rebaseStartTime, f2));
        if (f <= f3) {
            return 0.0f;
        }
        float f4 = Math.max(1.0E-4f, f2 - f3);
        float f5 = class_3532.method_15363((float)((f - f3) / f4 * this.rebaseReturnSpeedMultiplier), (float)0.0f, (float)1.0f);
        return this.easeOutCubic(f5);
    }

    private float easeOutCubic(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        float f3 = 1.0f - f2;
        return 1.0f - f3 * f3 * f3;
    }

    private Matrix4f matrixFromSample(Sample sample, boolean bl) {
        Vector3f vector3f = new Vector3f((Vector3fc)sample.pos);
        if (!bl && this.javaBlock) {
            vector3f.set(-vector3f.x, -vector3f.z, -vector3f.y);
        } else if (!bl && this.swapTranslationYZ) {
            vector3f.set(vector3f.x, vector3f.z, vector3f.y);
        }
        vector3f.x += 0.0f;
        vector3f.z += 0.0f;
        Matrix4f matrix4f = new Matrix4f().translate((Vector3fc)vector3f);
        if (bl && sample.rotEulerV2 != null) {
            float f = (float)Math.toRadians(sample.rotEulerV2.x);
            float f2 = (float)Math.toRadians(sample.rotEulerV2.y);
            float f3 = (float)Math.toRadians(sample.rotEulerV2.z);
            matrix4f.rotateXYZ(f, f2, f3);
        } else {
            matrix4f.rotate((Quaternionfc)sample.rot);
        }
        matrix4f.scale((Vector3fc)sample.scl);
        if (!bl && this.mirrorX) {
            Matrix4f matrix4f2 = new Matrix4f().scale(-1.0f, 1.0f, 1.0f);
            matrix4f = new Matrix4f((Matrix4fc)matrix4f2).mul((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f2);
        }
        return matrix4f;
    }

    private void clearTransforms(Map<String, class_630> map) {
        block4: {
            if (map == null || map.isEmpty()) break block4;
            if (this.currentClip == null) {
                for (class_630 class_6302 : map.values()) {
                    this.clearPartTransform(class_6302);
                }
            } else {
                for (String string : this.currentClip.getTracks().keySet()) {
                    if (this.isPlaying && this.activeArm != null && !this.isBoneForArm(string, this.activeArm)) continue;
                    class_630 class_6303 = map.get(string);
                    this.clearPartTransform(class_6303);
                }
            }
        }
    }

    private void clearTransformsForClipArm(Map<String, class_630> map) {
        if (this.currentClip != null) {
            for (String string : this.currentClip.getTracks().keySet()) {
                if (this.clipArmFilter && !this.isBoneForArm(string, this.clipArm)) continue;
                class_630 class_6302 = map.get(string);
                this.clearPartTransform(class_6302);
            }
        }
    }

    private void clearTransformsForArm(Map<String, class_630> map, class_1306 class_13062) {
        if (this.currentClip != null && class_13062 != null) {
            for (String string : this.currentClip.getTracks().keySet()) {
                if (!this.isBoneForArm(string, class_13062)) continue;
                class_630 class_6302 = map.get(string);
                this.clearPartTransform(class_6302);
            }
        }
    }

    private Map<String, PartPoseSnapshot> capturePartSnapshots(Map<String, class_630> map) {
        HashMap<String, PartPoseSnapshot> hashMap = new HashMap<String, PartPoseSnapshot>();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, class_630> entry : map.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                hashMap.put(entry.getKey(), PartPoseSnapshot.of(entry.getValue()));
            }
            return hashMap;
        }
        return hashMap;
    }

    private void applyV2ToPart(class_630 class_6302, PartPoseSnapshot partPoseSnapshot, Sample sample) {
        if (class_6302 != null && sample != null) {
            PartPoseSnapshot partPoseSnapshot2 = partPoseSnapshot != null ? partPoseSnapshot : PartPoseSnapshot.of(class_6302);
            Vector3f vector3f = sample.pos != null ? sample.pos : new Vector3f();
            Vector3f vector3f2 = sample.rotEulerV2 != null ? sample.rotEulerV2 : new Vector3f();
            class_6302.field_3657 = partPoseSnapshot2.x + vector3f.x;
            class_6302.field_3656 = partPoseSnapshot2.y + vector3f.y;
            class_6302.field_3655 = partPoseSnapshot2.z + vector3f.z;
            class_6302.field_3654 = partPoseSnapshot2.xRot + (float)Math.toRadians(vector3f2.x);
            class_6302.field_3675 = partPoseSnapshot2.yRot + (float)Math.toRadians(vector3f2.y);
            class_6302.field_3674 = partPoseSnapshot2.zRot + (float)Math.toRadians(vector3f2.z);
            class_6302.field_37938 = partPoseSnapshot2.xScale * sample.scl.x;
            class_6302.field_37939 = partPoseSnapshot2.yScale * sample.scl.y;
            class_6302.field_37940 = partPoseSnapshot2.zScale * sample.scl.z;
            this.clearPartTransform(class_6302);
            Object partObject = class_6302;
            if (partObject instanceof TransformablePart) {
                TransformablePart transformablePart = (TransformablePart)partObject;
                transformablePart.punchy$setDirectPoseBase(partPoseSnapshot2.x, partPoseSnapshot2.y, partPoseSnapshot2.z, partPoseSnapshot2.xRot, partPoseSnapshot2.yRot, partPoseSnapshot2.zRot);
            }
        }
    }

    public class_1306 getActiveArm() {
        return this.activeArm;
    }

    private void clearPartTransform(class_630 class_6302) {
        Object partObject = class_6302;
        if (partObject instanceof TransformablePart) {
            TransformablePart transformablePart = (TransformablePart)partObject;
            transformablePart.punchy$setExplicitTransform(null);
            transformablePart.punchy$clearDirectPoseBase();
        }
    }

    private void clearArmBlend() {
        this.leftArmBlendRemaining = 0.0f;
        this.leftArmBlendPrevRemaining = 0.0f;
        this.rightArmBlendRemaining = 0.0f;
        this.rightArmBlendPrevRemaining = 0.0f;
    }

    private void tickArmBlend(float f) {
        if (this.isPlaying && this.currentClip != null) {
            if (this.leftArmBlendRemaining > 0.0f) {
                this.leftArmBlendPrevRemaining = this.leftArmBlendRemaining;
                this.leftArmBlendRemaining = Math.max(0.0f, this.leftArmBlendRemaining - f / 1.0f);
            }
            if (this.rightArmBlendRemaining > 0.0f) {
                this.rightArmBlendPrevRemaining = this.rightArmBlendRemaining;
                this.rightArmBlendRemaining = Math.max(0.0f, this.rightArmBlendRemaining - f / 1.0f);
            }
        }
    }

    private float resolveArmBlendInT(class_1306 class_13062, float f) {
        float f2 = this.getArmBlendRemaining(class_13062);
        if (f2 <= 0.0f) {
            return 1.0f;
        }
        float f3 = this.getArmBlendPrevRemaining(class_13062);
        float f4 = class_3532.method_16439((float)f, (float)f3, (float)f2);
        float f5 = 1.0f - f4 / Math.max(1.0E-4f, 0.2f);
        return this.applyBlendInEasing(f5);
    }

    private float applyBlendInEasing(float f) {
        return switch (this.blendInEasing.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> Easing.easeInBack(f);
            case 1 -> Easing.smoothStep(f);
            case 2 -> Easing.easeOutBackStrong(f);
        };
    }

    private void tickArmTuningSuppression(float f) {
        boolean bl;
        boolean bl2;
        this.prevArmTuningSuppressAlpha = this.armTuningSuppressAlpha;
        boolean bl3 = this.isPlaying && this.isEquipClip(this.currentClip);
        boolean bl4 = bl2 = this.isPlaying && this.currentClip != null && this.currentClip.hasEffectAny("disable_arm_tuning") && this.currentClip.isEffectActive("disable_arm_tuning", this.currentTime);
        if (this.isWalkOnWaterClip(this.currentClip)) {
            bl2 = false;
        }
        if (bl3) {
            String string;
            String string2 = this.currentClip != null ? this.currentClip.getName() : "";
            String string3 = string = string2 == null ? "" : string2.toLowerCase(Locale.ROOT);
            if (string.equals("hand_in") || string.startsWith("hand_in_")) {
                if (bl2) {
                    this.armTuningSuppressAlpha = 1.0f;
                    this.prevArmTuningSuppressAlpha = 1.0f;
                } else {
                    this.clearArmTuningSuppression();
                }
                return;
            }
        }
        boolean bl5 = bl = this.overlapCompatIdle && this.overlapClip != null && this.overlapClip.hasEffectAny("disable_arm_tuning") && this.overlapClip.isEffectActive("disable_arm_tuning", this.overlapTime);
        if (this.isWalkOnWaterClip(this.overlapClip)) {
            bl = false;
        }
        bl2 = bl2 || bl;
        boolean bl6 = this.isArmTuningKeepActive(this.currentTime);
        if (bl6 && !bl2) {
            this.clearArmTuningSuppression();
        } else {
            boolean bl7 = this.isPlaying && this.currentClip != null && (!bl3 || bl2) || bl;
            float f2 = bl7 ? 1.0f : 0.0f;
            float f3 = bl7 ? 0.18f : 0.48f;
            float f4 = f3 <= 0.0f ? 1.0f : f / f3;
            f4 = class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f);
            this.armTuningSuppressAlpha = class_3532.method_16439((float)f4, (float)this.armTuningSuppressAlpha, (float)f2);
        }
    }

    private void clearArmTuningSuppression() {
        this.armTuningSuppressAlpha = 0.0f;
        this.prevArmTuningSuppressAlpha = 0.0f;
    }

    private boolean isArmTuningKeepActive(float f) {
        if (this.isPlaying && this.currentClip != null) {
            boolean bl;
            boolean bl2 = bl = this.currentClip.hasEffectAny("keep_arm_tuning") && this.currentClip.isEffectActive("keep_arm_tuning", f);
            if (bl) {
                return true;
            }
            return this.overlapCompatIdle && this.overlapClip != null && this.overlapClip.hasEffectAny("keep_arm_tuning") && this.overlapClip.isEffectActive("keep_arm_tuning", this.overlapTime) ? true : this.isCompatKeepArmTuningActive(f);
        }
        return this.overlapCompatIdle && this.overlapClip != null && this.overlapClip.hasEffectAny("keep_arm_tuning") && this.overlapClip.isEffectActive("keep_arm_tuning", this.overlapTime);
    }

    private boolean isCompatKeepArmTuningActive(float f) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_746 class_7462 = class_3102.field_1724;
            if (this.activeArm == null) {
                return this.hasCompatKeepArmTuning(class_7462, class_1306.field_6182) || this.hasCompatKeepArmTuning(class_7462, class_1306.field_6183);
            }
            if (this.hasCompatKeepArmTuning(class_7462, this.activeArm)) {
                return true;
            }
            if (this.isDualHandedActive(f)) {
                class_1306 class_13062 = this.activeArm == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                return this.hasCompatKeepArmTuning(class_7462, class_13062);
            }
            return false;
        }
        return false;
    }

    private boolean hasCompatKeepArmTuning(class_746 class_7462, class_1306 class_13062) {
        ToolTuning.Profile profile = this.resolveCompatProfileForArm(class_7462, class_13062);
        return profile != null && Boolean.TRUE.equals(profile.keepArmTuning);
    }

    private boolean isCompatKeepTriggerArmTuningActive(float f, class_1306 class_13062) {
        if (!this.isPlaying || this.currentClip == null || class_13062 == null) {
            return false;
        }
        if (this.latchedKeepTriggerArmTuning && this.latchedTriggerArm != null) {
            return class_13062 == this.latchedTriggerArm;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_746 class_7462 = class_3102.field_1724;
            ToolTuning.Profile profile = this.resolveCompatProfileForArm(class_7462, class_13062);
            class_1306 class_13063 = this.activeArm != null ? this.activeArm : PunchyAnimationManager.getSourceArm();
            return profile != null && Boolean.TRUE.equals(profile.keepTriggerArmTuning) && class_13063 != null && class_13062 == class_13063;
        }
        return false;
    }

    private void latchCompatKeepTriggerArmState() {
        class_310 class_3102;
        this.latchedKeepTriggerArmTuning = false;
        this.latchedTriggerArm = null;
        if (this.isPlaying && this.currentClip != null && (class_3102 = class_310.method_1551()) != null && class_3102.field_1724 != null) {
            ToolTuning.Profile profile;
            class_1306 class_13062;
            class_1306 class_13063 = class_13062 = this.activeArm != null ? this.activeArm : PunchyAnimationManager.getSourceArm();
            if (class_13062 != null && (profile = this.resolveCompatProfileForArm(class_3102.field_1724, class_13062)) != null && Boolean.TRUE.equals(profile.keepTriggerArmTuning)) {
                this.latchedKeepTriggerArmTuning = true;
                this.latchedTriggerArm = class_13062;
            }
        }
    }

    private ToolTuning.Profile resolveCompatProfileForArm(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            class_1799 class_17992 = class_13062 == class_1306.field_6183 ? (bl ? class_7462.method_6047() : class_7462.method_6079()) : (bl ? class_7462.method_6079() : class_7462.method_6047());
            class_1799 class_17993 = PunchyAnimationManager.resolveRenderStack((class_1657)class_7462, class_13062, class_17992);
            boolean bl3 = class_13062 == class_1306.field_6182;
            VanillaFirstPersonItemLayer.ToolKind toolKind = class_17993 != null && !class_17993.method_7960() ? VanillaFirstPersonItemLayer.ToolKind.INGREDIENT : VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND;
            VanillaFirstPersonItemLayer.ToolKind toolKind2 = ToolKindResolver.resolveItemKind(class_17993, toolKind);
            return ToolTuning.resolveProfile(class_17993, toolKind2, bl3);
        }
        return null;
    }

    private float getArmBlendRemaining(class_1306 class_13062) {
        return class_13062 == class_1306.field_6182 ? this.leftArmBlendRemaining : this.rightArmBlendRemaining;
    }

    private float getArmBlendPrevRemaining(class_1306 class_13062) {
        return class_13062 == class_1306.field_6182 ? this.leftArmBlendPrevRemaining : this.rightArmBlendPrevRemaining;
    }

    private void setArmBlendRemaining(class_1306 class_13062, float f) {
        if (class_13062 == class_1306.field_6182) {
            this.leftArmBlendRemaining = f;
        } else {
            this.rightArmBlendRemaining = f;
        }
    }

    private void setArmBlendPrevRemaining(class_1306 class_13062, float f) {
        if (class_13062 == class_1306.field_6182) {
            this.leftArmBlendPrevRemaining = f;
        } else {
            this.rightArmBlendPrevRemaining = f;
        }
    }

    private boolean isBoneForArm(String string, class_1306 class_13062) {
        if (string == null) {
            return true;
        }
        boolean bl = this.isLeftBone(string);
        boolean bl2 = this.isRightBone(string);
        if (!bl && !bl2) {
            return true;
        }
        return class_13062 == class_1306.field_6182 ? bl : bl2;
    }

    private class_1306 resolveBoneArm(String string) {
        if (string == null) {
            return null;
        }
        boolean bl = this.isLeftBone(string);
        boolean bl2 = this.isRightBone(string);
        if (bl && !bl2) {
            return class_1306.field_6182;
        }
        return bl2 && !bl ? class_1306.field_6183 : null;
    }

    private boolean isLeftBone(String string) {
        return string.startsWith("left_") || string.endsWith("_left");
    }

    private boolean isRightBone(String string) {
        return string.startsWith("right_") || string.endsWith("_right");
    }

    private String oppositeBoneName(String string) {
        if (string.startsWith("left_")) {
            return "right_" + string.substring("left_".length());
        }
        if (string.startsWith("right_")) {
            return "left_" + string.substring("right_".length());
        }
        if (string.endsWith("_left")) {
            return string.substring(0, string.length() - "_left".length()) + "_right";
        }
        return string.endsWith("_right") ? string.substring(0, string.length() - "_right".length()) + "_left" : string;
    }

    private boolean isDualHandedActive(float f) {
        return this.isDualHandedActive(this.currentClip, f);
    }

    private boolean isDualHandedActive(AnimationClip animationClip, float f) {
        if (this.ignoreDualHanded || animationClip == null) {
            return false;
        }
        return !PunchyAnimationManager.isDualHandedActive(animationClip, f) ? false : this.isConditionalOffhandWindowActive(animationClip, f);
    }

    private boolean isConditionalOffhandWindowActive(AnimationClip animationClip, float f) {
        boolean bl;
        if (animationClip == null) {
            return false;
        }
        if (this.isRealOffhandEmpty()) {
            return true;
        }
        float f2 = this.resolveLastOffhandStartTime(animationClip, f);
        float f3 = animationClip.getLastEffectTime("stop_if_offhand_item", f);
        boolean bl2 = !Float.isNaN(f2);
        boolean bl3 = bl = !Float.isNaN(f3);
        if (!bl2 && !bl) {
            return true;
        }
        return !bl2 ? !bl : !bl || f2 > f3;
    }

    private float resolveLastOffhandStartTime(AnimationClip animationClip, float f) {
        if (animationClip == null) {
            return Float.NaN;
        }
        float f2 = animationClip.getLastEffectTime("start_here_if_off_hand_item", f);
        float f3 = animationClip.getLastEffectTime("start_if_offhand_item", f);
        if (Float.isNaN(f2)) {
            return f3;
        }
        return Float.isNaN(f3) ? f2 : Math.max(f2, f3);
    }

    private float resolveFirstOffhandStartTime(AnimationClip animationClip) {
        if (animationClip == null) {
            return Float.NaN;
        }
        float f = Float.NaN;
        for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffects().entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            for (String string : entry.getValue()) {
                String string2;
                if (string == null || !"start_here_if_off_hand_item".equals(string2 = string.trim().toLowerCase(Locale.ROOT)) && !"start_if_offhand_item".equals(string2)) continue;
                return entry.getKey().floatValue();
            }
        }
        return f;
    }

    private float resolveConditionalDualStartTime(AnimationClip animationClip) {
        if (animationClip == null || this.isRealOffhandEmpty()) {
            return 0.0f;
        }
        if (!PunchyAnimationManager.hasAnyDualHandedEffect(animationClip)) {
            return 0.0f;
        }
        float f = this.resolveFirstOffhandStartTime(animationClip);
        if (!Float.isNaN(f) && !(f <= 0.0f)) {
            float f2 = animationClip.getLength();
            if (f2 <= 0.0f) {
                return 0.0f;
            }
            return animationClip.isLoop() ? f % f2 : Math.min(f, f2);
        }
        return 0.0f;
    }

    static {
        ANIM_SPEED_PREFIX = "anim_speed_";
        HIDE_OFFHAND_IF_EMPTY_EFFECT = "hide_offhand_if_empty";
        SHOW_OFFHAND_IF_EMPTY_EFFECT = "show_offhand_if_empty";
        START_WITH_OFFHAND_EFFECT = "start_here_if_off_hand_item";
        START_WITH_OFFHAND_EFFECT_ALIAS = "start_if_offhand_item";
        STOP_WITH_OFFHAND_EFFECT = "stop_if_offhand_item";
        LOGGER = LoggerFactory.getLogger((String)"Punchy/PoseHandler");
        ANIM_SPEED_VALUE_PATTERN = Pattern.compile("^[-+]?\\d+(?:[\\.,]\\d+)?");
    }

    public static enum BlendInEasing {
        BACK,
        SMOOTH,
        STRONG_OUT_BACK;

    }

    static class Sample {
        final Vector3f pos;
        final Quaternionf rot;
        final Vector3f scl;
        final Vector3f rotEulerV2;

        Sample(Vector3f vector3f, Quaternionf quaternionf, Vector3f vector3f2, Vector3f vector3f3) {
            this.pos = vector3f;
            this.rot = quaternionf;
            this.scl = vector3f2;
            this.rotEulerV2 = vector3f3 != null ? new Vector3f((Vector3fc)vector3f3) : null;
        }

        static Sample identity() {
            return new Sample(new Vector3f(), new Quaternionf(), new Vector3f(1.0f, 1.0f, 1.0f), null);
        }
    }

    static class PartPoseSnapshot {
        final float x;
        final float y;
        final float z;
        final float xRot;
        final float yRot;
        final float zRot;
        final float xScale;
        final float yScale;
        final float zScale;

        private PartPoseSnapshot(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            this.x = f;
            this.y = f2;
            this.z = f3;
            this.xRot = f4;
            this.yRot = f5;
            this.zRot = f6;
            this.xScale = f7;
            this.yScale = f8;
            this.zScale = f9;
        }

        static PartPoseSnapshot of(class_630 class_6302) {
            return new PartPoseSnapshot(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655, class_6302.field_3654, class_6302.field_3675, class_6302.field_3674, class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
        }
    }
}

