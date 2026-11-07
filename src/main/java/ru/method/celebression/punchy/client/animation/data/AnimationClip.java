package ru.method.celebression.punchy.client.animation.data;

import ru.method.celebression.animation.Animation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;

public class AnimationClip {
    private final String name;
    private final float length;
    private final boolean loop;
    private final Map<String, BoneTrack> tracks = new HashMap<String, BoneTrack>();
    private final Set<String> effects = new HashSet<String>();
    private boolean holdOnLastFrame;
    private final TreeMap<Float, Set<String>> timelineEffects = new TreeMap();
    private final TreeMap<Float, Set<String>> soundEffects = new TreeMap();
    private boolean lengthDefined;
    private boolean loopDefined;

    public AnimationClip(String string, float f, boolean bl) {
        this.name = string;
        this.length = f;
        this.loop = bl;
    }

    public void addTrack(BoneTrack boneTrack) {
        this.tracks.put(boneTrack.getBoneName(), boneTrack);
    }

    public BoneTrack getTrack(String string) {
        return this.tracks.get(string);
    }

    public Map<String, BoneTrack> getTracks() {
        return this.tracks;
    }

    public float getLength() {
        return this.length;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public String getName() {
        return this.name;
    }

    public void setHoldOnLastFrame(boolean bl) {
        this.holdOnLastFrame = bl;
    }

    public boolean isHoldOnLastFrame() {
        return this.holdOnLastFrame;
    }

    public void setLengthDefined(boolean bl) {
        this.lengthDefined = bl;
    }

    public boolean isLengthDefined() {
        return this.lengthDefined;
    }

    public void setLoopDefined(boolean bl) {
        this.loopDefined = bl;
    }

    public boolean isLoopDefined() {
        return this.loopDefined;
    }

    public void addEffect(String string) {
        if (string != null && !string.isEmpty()) {
            this.effects.add(string);
        }
    }

    public boolean hasEffect(String string) {
        return string != null && this.effects.contains(string);
    }

    public Set<String> getEffects() {
        return Collections.unmodifiableSet(this.effects);
    }

    public void addTimelineEffect(float f2, String string) {
        if (string != null && !string.isEmpty()) {
            this.timelineEffects.computeIfAbsent(Float.valueOf(f2), f -> new LinkedHashSet()).add(string);
        }
    }

    public void addSoundEffect(float f2, String string) {
        if (string != null && !string.isEmpty()) {
            this.soundEffects.computeIfAbsent(Float.valueOf(f2), f -> new HashSet()).add(string);
        }
    }

    public NavigableMap<Float, Set<String>> getSoundEffectsBetween(float f, float f2) {
        if (this.soundEffects.isEmpty()) {
            return Collections.emptyNavigableMap();
        }
        float f3 = Math.max(0.0f, f);
        float f4 = Math.max(0.0f, f2);
        return f4 < f3 ? Collections.emptyNavigableMap() : Collections.unmodifiableNavigableMap(this.soundEffects.subMap(Float.valueOf(f3), false, Float.valueOf(f4), true));
    }

    public Set<String> getSoundEffectsAt(float f) {
        if (this.soundEffects.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> set = this.soundEffects.get(Float.valueOf(f));
        return set != null && !set.isEmpty() ? Collections.unmodifiableSet(set) : Collections.emptySet();
    }

    public boolean hasEffectAny(String string) {
        if (string != null && !string.isEmpty()) {
            if (this.effects.contains(string)) {
                return true;
            }
            for (Set<String> set : this.timelineEffects.values()) {
                if (!set.contains(string)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isEffectActive(String string, float f) {
        if (string != null && !string.isEmpty()) {
            if (this.effects.contains(string)) {
                return true;
            }
            for (Map.Entry entry : this.timelineEffects.headMap(Float.valueOf(f), true).entrySet()) {
                if (!((Set)entry.getValue()).contains(string)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public Set<String> getActiveEffects(float f) {
        HashSet<String> hashSet = new HashSet<String>(this.effects);
        for (Set set : this.timelineEffects.headMap(Float.valueOf(f), true).values()) {
            hashSet.addAll(set);
        }
        return hashSet;
    }

    public Iterable<Map.Entry<Float, Set<String>>> getTimelineEffectsUpTo(float f) {
        NavigableMap<Float, Set<String>> navigableMap = this.timelineEffects.headMap(Float.valueOf(f), true);
        return Collections.unmodifiableNavigableMap(navigableMap).entrySet();
    }

    public NavigableMap<Float, Set<String>> getTimelineEffects() {
        return Collections.unmodifiableNavigableMap(this.timelineEffects);
    }

    public boolean hasEffectTriggeredBetween(String string, float f, float f2) {
        if (string != null && !string.isEmpty() && !this.timelineEffects.isEmpty()) {
            if (!(f2 < f)) {
                NavigableMap<Float, Set<String>> navigableMap = this.timelineEffects.subMap(Float.valueOf(f), false, Float.valueOf(f2), true);
                for (Set set : navigableMap.values()) {
                    if (!set.contains(string)) continue;
                    return true;
                }
                return false;
            }
            return this.hasEffectTriggeredBetween(string, f, Float.MAX_VALUE) || this.hasEffectTriggeredBetween(string, Float.NEGATIVE_INFINITY, f2);
        }
        return false;
    }

    public float getLastEffectTime(String string, float f) {
        if (string != null && !string.isEmpty() && !this.timelineEffects.isEmpty()) {
            float f2 = Float.NaN;
            for (Map.Entry entry : this.timelineEffects.headMap(Float.valueOf(f), true).entrySet()) {
                if (!((Set)entry.getValue()).contains(string)) continue;
                f2 = ((Float)entry.getKey()).floatValue();
            }
            return f2;
        }
        return Float.NaN;
    }

    public NavigableMap<Float, Set<String>> getSoundEffects() {
        return Collections.unmodifiableNavigableMap(this.soundEffects);
    }

    public static AnimationClip copyOf(AnimationClip animationClip) {
        if (animationClip == null) {
            return null;
        }
        AnimationClip animationClip2 = new AnimationClip(animationClip.getName(), animationClip.getLength(), animationClip.isLoop());
        animationClip2.setHoldOnLastFrame(animationClip.isHoldOnLastFrame());
        animationClip2.setLengthDefined(animationClip.isLengthDefined());
        animationClip2.setLoopDefined(animationClip.isLoopDefined());
        for (BoneTrack object : animationClip.getTracks().values()) {
            if (object == null) continue;
            animationClip2.addTrack(AnimationClip.copyTrack(object));
        }
        for (String string : animationClip.getEffects()) {
            animationClip2.addEffect(string);
        }
        for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffects().entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            for (String string : entry.getValue()) {
                animationClip2.addTimelineEffect(entry.getKey().floatValue(), string);
            }
        }
        for (Map.Entry<Float, Set<String>> entry : animationClip.getSoundEffects().entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            for (String string : entry.getValue()) {
                animationClip2.addSoundEffect(entry.getKey().floatValue(), string);
            }
        }
        return animationClip2;
    }

    public static AnimationClip merge(AnimationClip animationClip, AnimationClip animationClip2) {
        if (animationClip2 == null) {
            return AnimationClip.copyOf(animationClip);
        }
        if (animationClip == null) {
            return AnimationClip.copyOf(animationClip2);
        }
        String string = animationClip2.getName() != null && !animationClip2.getName().isBlank() ? animationClip2.getName() : animationClip.getName();
        float f = animationClip2.isLengthDefined() ? animationClip2.getLength() : animationClip.getLength();
        boolean bl = animationClip2.isLoopDefined() ? animationClip2.isLoop() : animationClip.isLoop();
        AnimationClip animationClip3 = new AnimationClip(string, f, bl);
        animationClip3.setLengthDefined(animationClip.isLengthDefined() || animationClip2.isLengthDefined());
        animationClip3.setLoopDefined(animationClip.isLoopDefined() || animationClip2.isLoopDefined());
        animationClip3.setHoldOnLastFrame(animationClip2.isLoopDefined() ? animationClip2.isHoldOnLastFrame() : animationClip.isHoldOnLastFrame());
        for (BoneTrack object : animationClip.getTracks().values()) {
            if (object == null) continue;
            animationClip3.addTrack(AnimationClip.copyTrack(object));
        }
        for (BoneTrack boneTrack : animationClip2.getTracks().values()) {
            if (boneTrack == null) continue;
            animationClip3.addTrack(AnimationClip.copyTrack(boneTrack));
        }
        for (String string2 : animationClip.getEffects()) {
            animationClip3.addEffect(string2);
        }
        for (String string3 : animationClip2.getEffects()) {
            animationClip3.addEffect(string3);
        }
        AnimationClip.mergeTimedEffectsInto(animationClip3, animationClip.getTimelineEffects(), false);
        AnimationClip.mergeTimedEffectsInto(animationClip3, animationClip2.getTimelineEffects(), false);
        AnimationClip.mergeTimedEffectsInto(animationClip3, animationClip.getSoundEffects(), true);
        AnimationClip.mergeTimedEffectsInto(animationClip3, animationClip2.getSoundEffects(), true);
        return animationClip3;
    }

    private static void mergeTimedEffectsInto(AnimationClip animationClip, NavigableMap<Float, Set<String>> navigableMap, boolean bl) {
        if (animationClip != null && navigableMap != null && !navigableMap.isEmpty()) {
            for (Map.Entry<Float, Set<String>> entry : navigableMap.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                for (String string : entry.getValue()) {
                    if (bl) {
                        animationClip.addSoundEffect(entry.getKey().floatValue(), string);
                        continue;
                    }
                    animationClip.addTimelineEffect(entry.getKey().floatValue(), string);
                }
            }
        }
    }

    private static BoneTrack copyTrack(BoneTrack boneTrack) {
        BoneTrack boneTrack2 = new BoneTrack(boneTrack.getBoneName());
        for (Keyframe keyframe : boneTrack.getKeyframes()) {
            boneTrack2.addKeyframe(AnimationClip.copyKeyframe(keyframe));
        }
        return boneTrack2;
    }

    private static Keyframe copyKeyframe(Keyframe keyframe) {
        Vector3f vector3f = keyframe.position() == null ? null : new Vector3f((Vector3fc)keyframe.position());
        Vector3f vector3f2 = keyframe.rotationEuler() == null ? null : new Vector3f((Vector3fc)keyframe.rotationEuler());
        Quaternionf quaternionf = keyframe.rotation() == null ? null : new Quaternionf((Quaternionfc)keyframe.rotation());
        Vector3f vector3f3 = keyframe.scale() == null ? null : new Vector3f((Vector3fc)keyframe.scale());
        return new Keyframe(keyframe.timeStamp(), vector3f, vector3f2, quaternionf, vector3f3, keyframe.interpolation(), keyframe.positionV2() == null ? null : new Vector3f((Vector3fc)keyframe.positionV2()), keyframe.rotationPreEulerV2() == null ? null : new Vector3f((Vector3fc)keyframe.rotationPreEulerV2()), keyframe.rotationPostEulerV2() == null ? null : new Vector3f((Vector3fc)keyframe.rotationPostEulerV2()), keyframe.positionExpression(), keyframe.rotationExpression(), keyframe.scaleExpression());
    }
}
