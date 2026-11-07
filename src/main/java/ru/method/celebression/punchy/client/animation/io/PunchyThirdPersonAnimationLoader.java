package ru.method.celebression.punchy.client.animation.io;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.class_2960;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.animation.io.PunchyAnimationLoader;

public class PunchyThirdPersonAnimationLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Punchy/ThirdPersonAnimationLoader");

    public static Map<String, AnimationClip> loadAll(class_3300 class_33002) {
        HashMap<String, AnimationClip> hashMap = new HashMap<String, AnimationClip>();
        if (class_33002 == null) {
            return hashMap;
        }
        PunchyThirdPersonAnimationLoader.loadFromManager(class_33002, class_2960.method_60655((String)"punchy", (String)"animations/player_3_p.animation.json"), hashMap);
        if (hashMap.isEmpty()) {
            PunchyThirdPersonAnimationLoader.loadFromManager(class_33002, class_2960.method_60655((String)"minecraft", (String)"punchy/animations/player_3_p.animation.json"), hashMap);
        }
        return hashMap;
    }

    private static void loadFromManager(class_3300 class_33002, class_2960 class_29602, Map<String, AnimationClip> map) {
        if (class_33002 != null && class_29602 != null && map != null) {
            try {
                class_3298 class_32982 = class_33002.getResourceOrThrow(class_29602);
                if (class_32982 == null) {
                    return;
                }
                PunchyThirdPersonAnimationLoader.loadFromResource(class_32982, map);
            }
            catch (Exception exception) {
            }
        }
    }

    private static void loadFromResource(class_3298 class_32982, Map<String, AnimationClip> map) {
        if (class_32982 != null && map != null) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(class_32982.method_14482());){
                JsonObject jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("animations");
                if (jsonObject2 != null) {
                    for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                        AnimationClip animationClip;
                        if (entry == null || entry.getKey() == null || !entry.getValue().isJsonObject() || (animationClip = PunchyThirdPersonAnimationLoader.buildClip(entry.getKey(), entry.getValue().getAsJsonObject())) == null) continue;
                        map.put(entry.getKey(), animationClip);
                    }
                    return;
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static AnimationClip buildClip(String string, JsonObject jsonObject) {
        if (string != null && jsonObject != null) {
            float f = jsonObject.has("animation_length") ? jsonObject.get("animation_length").getAsFloat() : 0.0f;
            LoopInfo loopInfo = PunchyThirdPersonAnimationLoader.parseLoopInfo(jsonObject);
            AnimationClip animationClip = new AnimationClip(string, f, loopInfo.loop);
            animationClip.setHoldOnLastFrame(loopInfo.holdOnLastFrame);
            animationClip.setLengthDefined(jsonObject.has("animation_length"));
            animationClip.setLoopDefined(jsonObject.has("loop"));
            if (jsonObject.has("bones")) {
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("bones");
                for (String string2 : jsonObject2.keySet()) {
                    JsonObject jsonObject3 = jsonObject2.getAsJsonObject(string2);
                    animationClip.addTrack(PunchyThirdPersonAnimationLoader.parseBoneTrack(string2, jsonObject3));
                }
            }
            PunchyAnimationLoader.parseEffects(animationClip, jsonObject);
            PunchyAnimationLoader.parseTimelineEffects(animationClip, jsonObject);
            return animationClip;
        }
        return null;
    }

    private static LoopInfo parseLoopInfo(JsonObject jsonObject) {
        JsonElement jsonElement;
        boolean bl = false;
        boolean bl2 = false;
        if (jsonObject != null && jsonObject.has("loop") && (jsonElement = jsonObject.get("loop")).isJsonPrimitive()) {
            if (jsonElement.getAsJsonPrimitive().isBoolean()) {
                bl = jsonElement.getAsBoolean();
            } else if (jsonElement.getAsJsonPrimitive().isString()) {
                String string = jsonElement.getAsString().toLowerCase(Locale.ROOT).trim();
                if ("hold_on_last_frame".equals(string)) {
                    bl2 = true;
                } else if ("loop".equals(string) || "true".equals(string)) {
                    bl = true;
                }
            }
        }
        return new LoopInfo(bl, bl2);
    }

    private static BoneTrack parseBoneTrack(String string, JsonObject jsonObject) {
        BoneTrack boneTrack = new BoneTrack(string);
        TreeMap<Float, KeyframeBuilder> treeMap = new TreeMap<Float, KeyframeBuilder>();
        if (jsonObject.has("rotation")) {
            JsonElement rotation = jsonObject.get("rotation");
            if (rotation.isJsonArray()) {
                KeyframeBuilder builder = PunchyThirdPersonAnimationLoader.getBuilder(treeMap, 0.0f);
                builder.rotationEuler = PunchyThirdPersonAnimationLoader.parseRotation(rotation);
                builder.rotation = PunchyThirdPersonAnimationLoader.parseEuler(rotation);
            } else if (rotation.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : rotation.getAsJsonObject().entrySet()) {
                    KeyframeBuilder builder = PunchyThirdPersonAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey()));
                    builder.rotationEuler = PunchyThirdPersonAnimationLoader.parseRotation(entry.getValue());
                    builder.rotation = PunchyThirdPersonAnimationLoader.parseEuler(entry.getValue());
                }
            }
        }
        if (jsonObject.has("position")) {
            JsonElement position = jsonObject.get("position");
            if (position.isJsonArray()) {
                PunchyThirdPersonAnimationLoader.getBuilder(treeMap, 0.0f).position = PunchyThirdPersonAnimationLoader.parsePosition(position, string);
            } else if (position.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : position.getAsJsonObject().entrySet()) {
                    PunchyThirdPersonAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey())).position = PunchyThirdPersonAnimationLoader.parsePosition(entry.getValue(), string);
                }
            }
        }
        if (jsonObject.has("scale")) {
            JsonElement scale = jsonObject.get("scale");
            if (scale.isJsonArray()) {
                PunchyThirdPersonAnimationLoader.getBuilder(treeMap, 0.0f).scale = PunchyThirdPersonAnimationLoader.parseScale(scale, string);
            } else if (scale.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : scale.getAsJsonObject().entrySet()) {
                    PunchyThirdPersonAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey())).scale = PunchyThirdPersonAnimationLoader.parseScale(entry.getValue(), string);
                }
            }
        }
        for (Map.Entry<Float, KeyframeBuilder> entry : treeMap.entrySet()) {
            KeyframeBuilder builder = entry.getValue();
            Vector3f position = builder.position == null ? null : new Vector3f(builder.position);
            Vector3f rotationEuler = builder.rotationEuler == null ? null : new Vector3f(builder.rotationEuler);
            Quaternionf rotation = builder.rotation == null ? null : new Quaternionf(builder.rotation);
            Vector3f scale = builder.scale == null ? null : new Vector3f(builder.scale);
            boneTrack.addKeyframe(new Keyframe(entry.getKey(), position, rotationEuler, rotation, scale, "linear"));
        }
        return boneTrack;
    }

    private static KeyframeBuilder getBuilder(Map<Float, KeyframeBuilder> map, float f2) {
        return map.computeIfAbsent(Float.valueOf(f2), f -> new KeyframeBuilder());
    }

    private static Vector3f parsePosition(JsonElement jsonElement, String string) {
        JsonElement jsonElement2 = PunchyThirdPersonAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return PunchyThirdPersonAnimationLoader.shouldFlipGripZ(string) ? new Vector3f(-f, -f3, f2) : new Vector3f(f, -f3, -f2);
        }
        return new Vector3f(0.0f);
    }

    private static Quaternionf parseEuler(JsonElement jsonElement) {
        Vector3f vector3f = PunchyThirdPersonAnimationLoader.parseRotation(jsonElement);
        float f = (float)Math.toRadians(vector3f.x);
        float f2 = (float)Math.toRadians(vector3f.y);
        float f3 = (float)Math.toRadians(vector3f.z);
        return new Quaternionf().rotationZYX(f3, f2, f);
    }

    private static Vector3f parseRotation(JsonElement jsonElement) {
        JsonElement jsonElement2 = PunchyThirdPersonAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return new Vector3f(f, f2, f3);
        }
        return new Vector3f(0.0f);
    }

    private static Vector3f parseScale(JsonElement jsonElement, String string) {
        JsonElement jsonElement2 = PunchyThirdPersonAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return PunchyThirdPersonAnimationLoader.shouldSwapScaleAxes(string) ? new Vector3f(f, f3, f2) : new Vector3f(f, f2, f3);
        }
        return new Vector3f(1.0f, 1.0f, 1.0f);
    }

    private static boolean shouldSwapScaleAxes(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.contains("item");
    }

    private static boolean shouldFlipGripZ(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.contains("itemgrip");
    }

    private static JsonElement unwrapKeyframeElement(JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (!jsonElement.isJsonObject()) {
            return jsonElement;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("post")) {
            return jsonObject.get("post");
        }
        if (jsonObject.has("vector")) {
            return jsonObject.get("vector");
        }
        return jsonObject.has("pre") ? jsonObject.get("pre") : jsonElement;
    }

    private static void loadBundledDefaults(Map<String, AnimationClip> map) {
    }

    static final class LoopInfo {
        final boolean loop;
        final boolean holdOnLastFrame;

        LoopInfo(boolean bl, boolean bl2) {
            this.loop = bl;
            this.holdOnLastFrame = bl2;
        }

        @Override
        public final String toString() {
            return "LoopInfo[" + "loop=" + this.loop + ", " + "holdOnLastFrame=" + this.holdOnLastFrame + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.loop, this.holdOnLastFrame);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LoopInfo other)) return false;
            return java.util.Objects.deepEquals(this.loop, other.loop)
                && java.util.Objects.deepEquals(this.holdOnLastFrame, other.holdOnLastFrame);
        }

        public boolean loop() {
            return this.loop;
        }

        public boolean holdOnLastFrame() {
            return this.holdOnLastFrame;
        }
    }

    static class KeyframeBuilder {
        Vector3f position;
        Vector3f rotationEuler;
        Quaternionf rotation;
        Vector3f scale;

        KeyframeBuilder() {
        }
    }
}

