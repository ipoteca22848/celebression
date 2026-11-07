package ru.method.celebression.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import ru.method.celebression.animation.AnimationKeyframe;
import ru.method.celebression.modules.impl.render.cosmetic.BoneTransformSet;

public final class AnimationClip {
    private final float aG;
    private final boolean aH;
    private final Map<String, BoneAnimation> aI;

    private AnimationClip(float f, boolean bl, Map<String, BoneAnimation> map) {
        this.aG = Math.max(0.01f, f);
        this.aH = bl;
        this.aI = map;
    }

    public static AnimationClip e(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        float f = jsonObject.has("animation_length") ? jsonObject.get("animation_length").getAsFloat() : 1.0f;
        boolean bl = !jsonObject.has("loop") || jsonObject.get("loop").getAsBoolean();
        HashMap<String, BoneAnimation> hashMap = new HashMap<String, BoneAnimation>();
        if (jsonObject.has("bones") && jsonObject.get("bones").isJsonObject()) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("bones");
            for (Map.Entry entry : jsonObject2.entrySet()) {
                JsonObject jsonObject3;
                BoneAnimation boneAnimation;
                if (!((JsonElement)entry.getValue()).isJsonObject() || (boneAnimation = new BoneAnimation(AnimationKeyframe.g((jsonObject3 = ((JsonElement)entry.getValue()).getAsJsonObject()).has("rotation") ? jsonObject3.getAsJsonObject("rotation") : null), AnimationKeyframe.g(jsonObject3.has("position") ? jsonObject3.getAsJsonObject("position") : null), AnimationKeyframe.g(jsonObject3.has("scale") ? jsonObject3.getAsJsonObject("scale") : null))).isEmpty()) continue;
                hashMap.put((String)entry.getKey(), boneAnimation);
            }
        }
        return new AnimationClip(f, bl, hashMap);
    }

    public float bw() {
        return this.aG;
    }

    public boolean loop() {
        return this.aH;
    }

    public void a(BoneTransformSet boneTransformSet, float f) {
        if (boneTransformSet == null || this.aI.isEmpty()) {
            return;
        }
        float f2 = this.aH ? f : Math.min(Math.max(f, 0.0f), this.aG);
        for (Map.Entry<String, BoneAnimation> entry : this.aI.entrySet()) {
            entry.getValue().a(boneTransformSet, entry.getKey(), f2, this.aG, this.aH);
        }
    }

    static final class BoneAnimation {
        private final AnimationKeyframe aJ;
        private final AnimationKeyframe aK;
        private final AnimationKeyframe aL;

        BoneAnimation(AnimationKeyframe animationKeyframe, AnimationKeyframe animationKeyframe2, AnimationKeyframe animationKeyframe3) {
            this.aJ = animationKeyframe == null ? AnimationKeyframe.by() : animationKeyframe;
            this.aK = animationKeyframe2 == null ? AnimationKeyframe.by() : animationKeyframe2;
            this.aL = animationKeyframe3 == null ? AnimationKeyframe.by() : animationKeyframe3;
        }

        boolean isEmpty() {
            return this.aJ.isEmpty() && this.aK.isEmpty() && this.aL.isEmpty();
        }

        void a(BoneTransformSet boneTransformSet, String string, float f, float f2, boolean bl) {
            float[] fArray;
            if (!this.aJ.isEmpty()) {
                fArray = this.aJ.a(f, f2, bl);
                boneTransformSet.a(string, -fArray[0], -fArray[1], fArray[2]);
            }
            if (!this.aK.isEmpty()) {
                fArray = this.aK.a(f, f2, bl);
                boneTransformSet.b(string, fArray[0], fArray[1], fArray[2]);
            }
            if (!this.aL.isEmpty()) {
                fArray = this.aL.a(f, f2, bl);
                boneTransformSet.c(string, fArray[0], fArray[1], fArray[2]);
            }
        }
    }
}

