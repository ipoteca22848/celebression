package ru.method.celebression.punchy.client.bedrockitems;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
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
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.AnimationExpression;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.animation.data.VectorExpression;

final class BedrockItemAnimationLoader {
    private BedrockItemAnimationLoader() {
    }

    static Map<String, AnimationClip> loadAll(class_3300 class_33002, class_2960 class_29602) {
        HashMap<String, AnimationClip> hashMap = new HashMap<String, AnimationClip>();
        if (class_33002 != null && class_29602 != null) {
            try {
                class_3298 class_32982 = class_33002.getResourceOrThrow(class_29602);
                JsonObject jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(class_32982.method_14482())).getAsJsonObject();
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("animations");
                if (jsonObject2 == null) {
                    return hashMap;
                }
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    String string = entry.getKey();
                    AnimationClip animationClip = entry.getValue().isJsonObject() ? BedrockItemAnimationLoader.buildClip(string, entry.getValue().getAsJsonObject()) : null;
                    if (animationClip == null) continue;
                    hashMap.put(string, animationClip);
                    hashMap.put(class_29602.method_12836() + ":" + string, animationClip);
                }
            }
            catch (Exception exception) {
            }
            return hashMap;
        }
        return hashMap;
    }

    private static AnimationClip buildClip(String string, JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        float f = jsonObject.has("animation_length") ? jsonObject.get("animation_length").getAsFloat() : 0.0f;
        boolean bl = false;
        boolean bl2 = false;
        JsonElement loopElement = jsonObject.get("loop");
        if (loopElement != null && loopElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = loopElement.getAsJsonPrimitive();
            if (jsonPrimitive.isBoolean()) {
                bl = jsonPrimitive.getAsBoolean();
            } else if (jsonPrimitive.isString()) {
                String string2 = jsonPrimitive.getAsString().trim().toLowerCase(Locale.ROOT);
                if ("hold_on_last_frame".equals(string2)) {
                    bl2 = true;
                    bl = false;
                } else {
                    bl = Boolean.parseBoolean(string2);
                }
            }
        }
        AnimationClip clip = new AnimationClip(string, f, bl);
        if (bl2) {
            clip.setHoldOnLastFrame(true);
        }
        JsonElement bonesElement = jsonObject.get("bones");
        if (bonesElement == null || !bonesElement.isJsonObject()) {
            return clip;
        }
        for (Map.Entry<String, JsonElement> entry : bonesElement.getAsJsonObject().entrySet()) {
            BoneTrack boneTrack = entry.getValue().isJsonObject() ? BedrockItemAnimationLoader.parseBoneTrack(entry.getKey(), entry.getValue().getAsJsonObject()) : null;
            if (boneTrack == null) continue;
            clip.addTrack(boneTrack);
        }
        BedrockItemAnimationLoader.parseTimelineEffects(clip, jsonObject);
        return clip;
    }

    private static BoneTrack parseBoneTrack(String string, JsonObject jsonObject) {
        if (string == null || jsonObject == null) {
            return null;
        }
        BoneTrack boneTrack = new BoneTrack(string);
        TreeMap<Float, KeyframeBuilder> keyframes = new TreeMap<>();

        JsonElement rotation = jsonObject.get("rotation");
        if (rotation != null) {
            if (rotation.isJsonArray()) {
                applyRotation(BedrockItemAnimationLoader.getBuilder(keyframes, 0.0f), BedrockItemAnimationLoader.parseRotation(rotation));
            } else if (rotation.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : rotation.getAsJsonObject().entrySet()) {
                    applyRotation(BedrockItemAnimationLoader.getBuilder(keyframes, BedrockItemAnimationLoader.parseTime(entry.getKey())), BedrockItemAnimationLoader.parseRotation(entry.getValue()));
                }
            }
        }

        JsonElement position = jsonObject.get("position");
        if (position != null) {
            if (position.isJsonArray()) {
                applyPosition(BedrockItemAnimationLoader.getBuilder(keyframes, 0.0f), BedrockItemAnimationLoader.parsePosition(position));
            } else if (position.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : position.getAsJsonObject().entrySet()) {
                    applyPosition(BedrockItemAnimationLoader.getBuilder(keyframes, BedrockItemAnimationLoader.parseTime(entry.getKey())), BedrockItemAnimationLoader.parsePosition(entry.getValue()));
                }
            }
        }

        JsonElement scale = jsonObject.get("scale");
        if (scale != null) {
            if (scale.isJsonArray()) {
                applyScale(BedrockItemAnimationLoader.getBuilder(keyframes, 0.0f), BedrockItemAnimationLoader.parseScale(scale));
            } else if (scale.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : scale.getAsJsonObject().entrySet()) {
                    applyScale(BedrockItemAnimationLoader.getBuilder(keyframes, BedrockItemAnimationLoader.parseTime(entry.getKey())), BedrockItemAnimationLoader.parseScale(entry.getValue()));
                }
            }
        }

        for (Map.Entry<Float, KeyframeBuilder> entry : keyframes.entrySet()) {
            KeyframeBuilder builder = entry.getValue();
            Vector3f positionValue = builder.position != null ? new Vector3f(builder.position) : null;
            Vector3f rotationValue = builder.rotationEuler != null ? new Vector3f(builder.rotationEuler) : null;
            Quaternionf quaternion = builder.rotation != null ? new Quaternionf(builder.rotation) : null;
            Vector3f scaleValue = builder.scale != null ? new Vector3f(builder.scale) : null;
            boneTrack.addKeyframe(new Keyframe(entry.getKey(), positionValue, rotationValue, quaternion, scaleValue, "linear", builder.positionExpression, builder.rotationExpression, builder.scaleExpression));
        }
        return boneTrack;
    }

    private static void applyRotation(KeyframeBuilder builder, ParsedVec3 value) {
        builder.rotationEuler = value.value;
        builder.rotationExpression = value.expression;
        builder.rotation = BedrockItemAnimationLoader.parseEuler(value.value);
    }

    private static void applyPosition(KeyframeBuilder builder, ParsedVec3 value) {
        builder.position = value.value;
        builder.positionExpression = value.expression;
    }

    private static void applyScale(KeyframeBuilder builder, ParsedVec3 value) {
        builder.scale = value.value;
        builder.scaleExpression = value.expression;
    }

    private static KeyframeBuilder getBuilder(Map<Float, KeyframeBuilder> map, float f2) {
        return map.computeIfAbsent(Float.valueOf(f2), f -> new KeyframeBuilder());
    }

    private static ParsedVec3 parsePosition(JsonElement jsonElement) {
        JsonElement jsonElement2 = BedrockItemAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            ValueOrExpr valueOrExpr = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(0));
            ValueOrExpr valueOrExpr2 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(1));
            ValueOrExpr valueOrExpr3 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(2));
            return new ParsedVec3(new Vector3f(-((float)valueOrExpr.value), (float)valueOrExpr2.value, (float)valueOrExpr3.value), new VectorExpression(BedrockItemAnimationLoader.negate(valueOrExpr.expr), valueOrExpr2.expr, valueOrExpr3.expr));
        }
        return new ParsedVec3(new Vector3f(0.0f), null);
    }

    private static Quaternionf parseEuler(Vector3f vector3f) {
        float f = (float)Math.toRadians(vector3f.x);
        float f2 = (float)Math.toRadians(vector3f.y);
        float f3 = (float)Math.toRadians(vector3f.z);
        return new Quaternionf().rotationXYZ(f, f2, f3);
    }

    private static ParsedVec3 parseRotation(JsonElement jsonElement) {
        JsonElement jsonElement2 = BedrockItemAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            ValueOrExpr valueOrExpr = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(0));
            ValueOrExpr valueOrExpr2 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(1));
            ValueOrExpr valueOrExpr3 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(2));
            return new ParsedVec3(new Vector3f(-((float)valueOrExpr.value), -((float)valueOrExpr2.value), (float)valueOrExpr3.value), new VectorExpression(BedrockItemAnimationLoader.negate(valueOrExpr.expr), BedrockItemAnimationLoader.negate(valueOrExpr2.expr), valueOrExpr3.expr));
        }
        return new ParsedVec3(new Vector3f(0.0f), null);
    }

    private static ParsedVec3 parseScale(JsonElement jsonElement) {
        JsonElement jsonElement2 = BedrockItemAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            ValueOrExpr valueOrExpr = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(0));
            ValueOrExpr valueOrExpr2 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(1));
            ValueOrExpr valueOrExpr3 = BedrockItemAnimationLoader.parseValue(jsonElement2.getAsJsonArray().get(2));
            return new ParsedVec3(new Vector3f((float)valueOrExpr.value, (float)valueOrExpr2.value, (float)valueOrExpr3.value), new VectorExpression(valueOrExpr.expr, valueOrExpr2.expr, valueOrExpr3.expr));
        }
        return new ParsedVec3(new Vector3f(1.0f, 1.0f, 1.0f), null);
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

    private static ValueOrExpr parseValue(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            try {
                if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
                    return new ValueOrExpr(jsonElement.getAsDouble(), null);
                }
                if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                    String string = jsonElement.getAsString();
                    if (string == null) {
                        return new ValueOrExpr(0.0, null);
                    }
                    AnimationExpression animationExpression = AnimationExpression.compile(string);
                    if (animationExpression != null) {
                        return new ValueOrExpr(0.0, animationExpression);
                    }
                    return new ValueOrExpr(Double.parseDouble(string), null);
                }
            }
            catch (Exception exception) {
            }
            return new ValueOrExpr(0.0, null);
        }
        return new ValueOrExpr(0.0, null);
    }

    private static AnimationExpression negate(AnimationExpression animationExpression) {
        return AnimationExpression.negated(animationExpression);
    }

    private static void parseTimelineEffects(AnimationClip animationClip, JsonObject jsonObject) {
        if (animationClip != null && jsonObject != null && jsonObject.has("timeline")) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("timeline");
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                float f = BedrockItemAnimationLoader.parseTime(entry.getKey());
                BedrockItemAnimationLoader.collectTimelineEffects(animationClip, f, entry.getValue());
            }
        }
    }

    private static void collectTimelineEffects(AnimationClip animationClip, float f, JsonElement jsonElement) {
        block6: {
            block7: {
                String[] stringArray;
                if (animationClip == null || jsonElement == null || jsonElement.isJsonNull()) break block6;
                if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isString()) break block7;
                String string = jsonElement.getAsString();
                if (string == null || string.isEmpty()) break block6;
                for (String string2 : stringArray = string.split(";")) {
                    String string3 = string2.trim();
                    if (string3.isEmpty()) continue;
                    animationClip.addTimelineEffect(f, string3.toLowerCase(Locale.ROOT));
                }
                break block6;
            }
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    BedrockItemAnimationLoader.collectTimelineEffects(animationClip, f, jsonElement2);
                }
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    BedrockItemAnimationLoader.collectTimelineEffects(animationClip, f, entry.getValue());
                }
            }
        }
    }

    private static float parseTime(String string) {
        if (string != null && !string.isEmpty()) {
            try {
                return Float.parseFloat(string);
            }
            catch (NumberFormatException numberFormatException) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    static class KeyframeBuilder {
        Vector3f position;
        Vector3f rotationEuler;
        Quaternionf rotation;
        Vector3f scale;
        VectorExpression positionExpression;
        VectorExpression rotationExpression;
        VectorExpression scaleExpression;

        KeyframeBuilder() {
        }
    }

    static final class ParsedVec3 {
        final Vector3f value;
        final VectorExpression expression;

        ParsedVec3(Vector3f vector3f, VectorExpression vectorExpression) {
            this.value = vector3f;
            this.expression = vectorExpression;
        }

        @Override
        public final String toString() {
            return "ParsedVec3[" + "value=" + this.value + ", " + "expression=" + this.expression + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.value, this.expression);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ParsedVec3 other)) return false;
            return java.util.Objects.deepEquals(this.value, other.value)
                && java.util.Objects.deepEquals(this.expression, other.expression);
        }

        public Vector3f value() {
            return this.value;
        }

        public VectorExpression expression() {
            return this.expression;
        }
    }

    static final class ValueOrExpr {
        final double value;
        final AnimationExpression expr;

        ValueOrExpr(double d, AnimationExpression animationExpression) {
            this.value = d;
            this.expr = animationExpression;
        }

        @Override
        public final String toString() {
            return "ValueOrExpr[" + "value=" + this.value + ", " + "expr=" + this.expr + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.value, this.expr);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ValueOrExpr other)) return false;
            return java.util.Objects.deepEquals(this.value, other.value)
                && java.util.Objects.deepEquals(this.expr, other.expr);
        }

        public double value() {
            return this.value;
        }

        public AnimationExpression expr() {
            return this.expr;
        }
    }
}

