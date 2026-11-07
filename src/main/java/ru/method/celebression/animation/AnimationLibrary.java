package ru.method.celebression.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import ru.method.celebression.animation.AnimationClip;

public final class AnimationLibrary {
    private final Map<String, AnimationClip> aM;

    private AnimationLibrary(Map<String, AnimationClip> map) {
        this.aM = map;
    }

    public static AnimationLibrary f(JsonObject jsonObject) {
        if (jsonObject == null || !jsonObject.has("animations") || !jsonObject.get("animations").isJsonObject()) {
            return AnimationLibrary.bx();
        }
        HashMap<String, AnimationClip> hashMap = new HashMap<String, AnimationClip>();
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("animations");
        for (Map.Entry entry : jsonObject2.entrySet()) {
            AnimationClip animationClip;
            if (!((JsonElement)entry.getValue()).isJsonObject() || (animationClip = AnimationClip.e(((JsonElement)entry.getValue()).getAsJsonObject())) == null) continue;
            hashMap.put((String)entry.getKey(), animationClip);
        }
        return new AnimationLibrary(hashMap);
    }

    public static AnimationLibrary bx() {
        return new AnimationLibrary(Map.of());
    }

    public boolean isEmpty() {
        return this.aM.isEmpty();
    }

    public AnimationClip ad(String string) {
        return this.aM.get(string);
    }

    public AnimationClip a(boolean bl) {
        AnimationClip animationClip = this.aM.get(bl ? "moving" : "idle");
        if (animationClip != null) {
            return animationClip;
        }
        return this.aM.get("idle");
    }
}

