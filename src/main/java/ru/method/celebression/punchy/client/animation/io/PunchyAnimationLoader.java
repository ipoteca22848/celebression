package ru.method.celebression.punchy.client.animation.io;

import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.class_2960;
import net.minecraft.class_310;
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

public class PunchyAnimationLoader {
    private static final Logger LOGGER;
    public static final String DEFAULT_PACK_OPTION_ID;
    private static final String BUILTIN_PACK_OPTION_ID;
    private static final List<class_2960> INTERNAL_ANIMATION_IDS;

    public static AnimationClip load(class_3300 class_33002, class_2960 class_29602, String string) {
        if (class_33002 != null && class_29602 != null && string != null && !string.isBlank()) {
            try {
                class_3298 class_32982 = class_33002.getResourceOrThrow(class_29602);
                Map<String, AnimationClip> map = PunchyAnimationLoader.readClipsFromResource(class_32982);
                return map.get(string.trim());
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    public static Map<String, AnimationClip> loadAll(class_3300 class_33002) {
        HashMap<String, AnimationClip> hashMap = new HashMap<String, AnimationClip>();
        Map<String, LinkedHashMap<String, AnimationClip>> map = PunchyAnimationLoader.loadAllWithPackVariants(class_33002);
        for (Map.Entry<String, LinkedHashMap<String, AnimationClip>> entry : map.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null || entry.getValue().isEmpty()) continue;
            AnimationClip animationClip = entry.getValue().get("punchy_default");
            if (animationClip == null) {
                animationClip = entry.getValue().values().iterator().next();
            }
            if (animationClip == null) continue;
            hashMap.put(entry.getKey(), AnimationClip.copyOf(animationClip));
        }
        return hashMap;
    }

    public static Map<String, LinkedHashMap<String, AnimationClip>> loadAllWithPackVariants(class_3300 class_33002) {
        HashMap<String, LinkedHashMap<String, AnimationClip>> hashMap = new HashMap<String, LinkedHashMap<String, AnimationClip>>();
        if (class_33002 == null) {
            return hashMap;
        }
        List<AnimationResourceEntry> list = PunchyAnimationLoader.collectAnimationResourceEntries(class_33002);
        HashMap<String, AnimationClip> hashMap2 = new HashMap<String, AnimationClip>();
        for (AnimationResourceEntry resourceEntry : list) {
            if (resourceEntry == null || resourceEntry.resource == null) continue;
            Map<String, AnimationClip> clips = PunchyAnimationLoader.readClipsFromResource(resourceEntry.resource);
            if (clips.isEmpty()) continue;
            String optionId = PunchyAnimationLoader.optionIdForAnimationEntry(resourceEntry);
            for (Map.Entry<String, AnimationClip> entry : clips.entrySet()) {
                String string2;
                if (entry == null || entry.getKey() == null || entry.getValue() == null || (string2 = entry.getKey().trim()).isBlank()) continue;
                LinkedHashMap<String, AnimationClip> variants = hashMap.computeIfAbsent(string2, string -> new LinkedHashMap<>());
                variants.putIfAbsent(optionId, AnimationClip.copyOf(entry.getValue()));
                hashMap2.putIfAbsent(string2, AnimationClip.copyOf(entry.getValue()));
            }
        }
        for (Map.Entry<String, AnimationClip> entry : hashMap2.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            LinkedHashMap<String, AnimationClip> variants = hashMap.computeIfAbsent(entry.getKey(), string -> new LinkedHashMap<>());
            LinkedHashMap<String, AnimationClip> orderedVariants = new LinkedHashMap<>();
            orderedVariants.put("punchy_default", AnimationClip.copyOf(entry.getValue()));
            for (Map.Entry<String, AnimationClip> entry2 : variants.entrySet()) {
                if (entry2 == null || entry2.getKey() == null || entry2.getValue() == null) continue;
                orderedVariants.putIfAbsent(entry2.getKey(), AnimationClip.copyOf(entry2.getValue()));
            }
            hashMap.put(entry.getKey(), orderedVariants);
        }
        return hashMap;
    }

    private static String optionIdForAnimationEntry(AnimationResourceEntry animationResourceEntry) {
        return animationResourceEntry != null && animationResourceEntry.id != null && "punchy".equals(animationResourceEntry.id.method_12836()) && animationResourceEntry.id.method_12832() != null && animationResourceEntry.id.method_12832().startsWith("animations/") ? "pack:punchy:punchy" : PunchyAnimationLoader.optionIdFromPackId(animationResourceEntry != null && animationResourceEntry.resource != null ? animationResourceEntry.resource.method_14480() : null);
    }

    private static List<AnimationResourceEntry> collectAnimationResourceEntries(class_3300 class_33002) {
        Map<class_2960, Boolean> map = PunchyAnimationLoader.discoverAnimationResourceIds(class_33002);
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, Integer> map2 = PunchyAnimationLoader.resolvePackPriorities();
        int n = map2.values().stream().mapToInt(Integer::intValue).min().orElse(0) - 1;
        int n2 = 0;
        ArrayList<AnimationResourceEntry> arrayList = new ArrayList<AnimationResourceEntry>();
        for (class_2960 class_29602 : map.keySet()) {
            int n3;
            List<class_3298> list = PunchyAnimationLoader.tryGetResourceStack(class_33002, class_29602);
            if (list != null && !list.isEmpty()) {
                int n4 = 0;
                for (n3 = list.size() - 1; n3 >= 0; --n3) {
                    class_3298 class_32982 = list.get(n3);
                    if (class_32982 == null) continue;
                    int n5 = map2.getOrDefault(class_32982.method_14480(), n--);
                    arrayList.add(new AnimationResourceEntry(class_29602, class_32982, n5, n2, n4++));
                }
                ++n2;
                continue;
            }
            try {
                class_3298 class_32983 = class_33002.getResourceOrThrow(class_29602);
                n3 = map2.getOrDefault(class_32983.method_14480(), n--);
                arrayList.add(new AnimationResourceEntry(class_29602, class_32983, n3, n2, 0));
            }
            catch (Exception exception) {
            }
            ++n2;
        }
        arrayList.sort(Comparator.comparingInt((AnimationResourceEntry entry) -> entry.packPriority).reversed()
                .thenComparingInt(entry -> entry.discoveryOrder)
                .thenComparingInt(entry -> entry.stackOrder)
                .thenComparing(entry -> entry.id.toString()));
        return arrayList;
    }

    private static Map<class_2960, Boolean> discoverAnimationResourceIds(class_3300 class_33002) {
        LinkedHashMap<class_2960, Boolean> linkedHashMap = new LinkedHashMap<class_2960, Boolean>();
        if (class_33002 == null) {
            return linkedHashMap;
        }
        PunchyAnimationLoader.collectAnimationResourceIds(class_33002, "animations", "punchy", "animations/", linkedHashMap);
        PunchyAnimationLoader.collectAnimationResourceIds(class_33002, "punchy/animations", "minecraft", "punchy/animations/", linkedHashMap);
        return linkedHashMap;
    }

    private static void collectAnimationResourceIds(class_3300 class_33002, String string, String string2, String string3, Map<class_2960, Boolean> map) {
        Map<class_2960, class_3298> map2 = class_33002.method_14488(string, class_29602 -> class_29602.method_12836().equals(string2) && class_29602.method_12832().startsWith(string3) && class_29602.method_12832().endsWith(".json"));
        for (class_2960 class_29603 : map2.keySet()) {
            map.putIfAbsent(class_29603, Boolean.TRUE);
        }
    }

    private static List<class_3298> tryGetResourceStack(class_3300 class_33002, class_2960 class_29602) {
        if (class_33002 != null && class_29602 != null) {
            try {
                List list = class_33002.method_14489(class_29602);
                return list != null && !list.isEmpty() ? new ArrayList(list) : null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static Map<String, Integer> resolvePackPriorities() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        List<String> list = PunchyAnimationLoader.getSelectedResourcePackIdsHighToLow();
        int n = list.size();
        for (String string : list) {
            if (string == null || string.isBlank()) continue;
            hashMap.putIfAbsent(string, n--);
        }
        return hashMap;
    }

    private static List<String> getSelectedResourcePackIdsHighToLow() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            var resourcePackManager = class_3102.method_1520();
            if (resourcePackManager != null) {
                for (String string : resourcePackManager.method_29210()) {
                    if (string == null || string.isBlank()) continue;
                    arrayList.add(string);
                }
            }
        }
        catch (Exception exception) {
        }
        Collections.reverse(arrayList);
        String builtInPackId = PunchyAnimationLoader.resolveActiveBuiltInPackId(class_3102);
        if (builtInPackId != null && !builtInPackId.isBlank() && !arrayList.contains(builtInPackId)) {
            arrayList.add(0, builtInPackId);
        }
        return arrayList;
    }

    private static String resolveActiveBuiltInPackId(class_310 class_3102) {
        if (class_3102 != null && class_3102.method_1478() != null) {
            try {
                class_2960 class_29602 = class_2960.method_60655((String)"minecraft", (String)"punchy/compat/default_tuning.json");
                return class_3102.method_1478().method_14486(class_29602).map(class_3298::method_14480).filter(string -> string != null && !string.isBlank()).orElse(null);
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static Map<String, AnimationClip> readClipsFromResource(class_3298 class_32982) {
        HashMap<String, AnimationClip> hashMap = new HashMap<String, AnimationClip>();
        if (class_32982 == null) {
            return hashMap;
        }
        try (InputStreamReader inputStreamReader = new InputStreamReader(class_32982.method_14482());){
            JsonObject jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
            JsonObject animations = jsonObject.getAsJsonObject("animations");
            if (animations != null) {
                for (Map.Entry<String, JsonElement> entry : animations.entrySet()) {
                    if (entry.getKey() == null || entry.getValue() == null || !entry.getValue().isJsonObject()) continue;
                    AnimationClip clip = PunchyAnimationLoader.buildClip(entry.getKey(), entry.getValue().getAsJsonObject());
                    if (clip != null) {
                        hashMap.put(entry.getKey(), clip);
                    }
                }
            }
            return hashMap;
        }
        catch (Exception exception) {
            return hashMap;
        }
    }

    private static BoneTrack parseBoneTrack(String string, JsonObject jsonObject, float f, boolean bl) {
        BoneTrack boneTrack = new BoneTrack(string);
        TreeMap<Float, KeyframeBuilder> treeMap = new TreeMap<Float, KeyframeBuilder>();
        if (jsonObject.has("rotation")) {
            JsonElement rotation = jsonObject.get("rotation");
            if (rotation.isJsonArray()) {
                KeyframeBuilder builder = PunchyAnimationLoader.getBuilder(treeMap, 0.0f);
                builder.rotationEuler = PunchyAnimationLoader.parseRotation(rotation);
                builder.rotation = PunchyAnimationLoader.parseEuler(rotation);
                Vector3f rotationV2 = PunchyAnimationLoader.parseRotationV2(rotation);
                builder.rotationPreEulerV2 = rotationV2 == null ? null : new Vector3f(rotationV2);
                builder.rotationPostEulerV2 = rotationV2 == null ? null : new Vector3f(rotationV2);
            } else if (rotation.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : rotation.getAsJsonObject().entrySet()) {
                    KeyframeBuilder builder = PunchyAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey()));
                    JsonElement value = entry.getValue();
                    builder.rotationEuler = PunchyAnimationLoader.parseRotation(value);
                    builder.rotation = PunchyAnimationLoader.parseEuler(value);
                    Vector3f pre = PunchyAnimationLoader.parseRotationEndpointV2(value, "pre");
                    Vector3f post = PunchyAnimationLoader.parseRotationEndpointV2(value, "post");
                    if (pre == null && post == null) {
                        Vector3f rotationV2 = PunchyAnimationLoader.parseRotationV2(value);
                        builder.rotationPreEulerV2 = rotationV2 == null ? null : new Vector3f(rotationV2);
                        builder.rotationPostEulerV2 = rotationV2 == null ? null : new Vector3f(rotationV2);
                    } else {
                        builder.rotationPreEulerV2 = pre != null ? new Vector3f(pre) : post != null ? new Vector3f(post) : null;
                        builder.rotationPostEulerV2 = post != null ? new Vector3f(post) : pre != null ? new Vector3f(pre) : null;
                    }
                    String interpolation = PunchyAnimationLoader.readInterpolation(value);
                    if (interpolation != null) {
                        builder.interpolation = interpolation;
                    }
                }
            }
        }
        if (jsonObject.has("position")) {
            JsonElement position = jsonObject.get("position");
            if (position.isJsonArray()) {
                KeyframeBuilder builder = PunchyAnimationLoader.getBuilder(treeMap, 0.0f);
                builder.position = PunchyAnimationLoader.parsePosition(position, string);
                builder.positionV2 = PunchyAnimationLoader.parsePositionV2(position);
            } else if (position.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : position.getAsJsonObject().entrySet()) {
                    KeyframeBuilder builder = PunchyAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey()));
                    builder.position = PunchyAnimationLoader.parsePosition(entry.getValue(), string);
                    builder.positionV2 = PunchyAnimationLoader.parsePositionV2(entry.getValue());
                    String interpolation = PunchyAnimationLoader.readInterpolation(entry.getValue());
                    if (interpolation != null) {
                        builder.interpolation = interpolation;
                    }
                }
            }
        }
        if (jsonObject.has("scale")) {
            JsonElement scale = jsonObject.get("scale");
            if (scale.isJsonArray()) {
                PunchyAnimationLoader.getBuilder(treeMap, 0.0f).scale = PunchyAnimationLoader.parseScale(scale, string);
            } else if (scale.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : scale.getAsJsonObject().entrySet()) {
                    KeyframeBuilder builder = PunchyAnimationLoader.getBuilder(treeMap, Float.parseFloat(entry.getKey()));
                    builder.scale = PunchyAnimationLoader.parseScale(entry.getValue(), string);
                    String interpolation = PunchyAnimationLoader.readInterpolation(entry.getValue());
                    if (interpolation != null) {
                        builder.interpolation = interpolation;
                    }
                }
            }
        }
        if (bl && f > 0.0f && !treeMap.isEmpty()) {
            Float lastTime = treeMap.lastKey();
            if (f - lastTime > 1.0E-4f && !treeMap.containsKey(f)) {
                KeyframeBuilder lastBuilder = treeMap.remove(lastTime);
                if (lastBuilder != null) {
                    treeMap.put(f, lastBuilder);
                }
            }
        }
        for (Map.Entry<Float, KeyframeBuilder> entry : treeMap.entrySet()) {
            KeyframeBuilder builder = entry.getValue();
            Vector3f position = builder.position == null ? null : new Vector3f(builder.position);
            Vector3f rotationEuler = builder.rotationEuler == null ? null : new Vector3f(builder.rotationEuler);
            Quaternionf rotation = builder.rotation == null ? null : new Quaternionf(builder.rotation);
            Vector3f scale = builder.scale == null ? null : new Vector3f(builder.scale);
            String interpolation = builder.interpolation != null ? builder.interpolation : "linear";
            Vector3f positionV2 = builder.positionV2 == null ? null : new Vector3f(builder.positionV2);
            Vector3f rotationPreV2 = builder.rotationPreEulerV2 == null ? null : new Vector3f(builder.rotationPreEulerV2);
            Vector3f rotationPostV2 = builder.rotationPostEulerV2 == null ? null : new Vector3f(builder.rotationPostEulerV2);
            boneTrack.addKeyframe(new Keyframe(entry.getKey(), position, rotationEuler, rotation, scale, interpolation, positionV2, rotationPreV2, rotationPostV2, null, null, null));
        }
        return boneTrack;
    }

    private static KeyframeBuilder getBuilder(Map<Float, KeyframeBuilder> map, float f2) {
        return map.computeIfAbsent(Float.valueOf(f2), f -> new KeyframeBuilder());
    }

    private static Vector3f parsePosition(JsonElement jsonElement, String string) {
        JsonElement jsonElement2 = PunchyAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return PunchyAnimationLoader.shouldFlipGripZ(string) ? new Vector3f(-f, -f3, f2) : new Vector3f(f, -f3, -f2);
        }
        return new Vector3f(0.0f);
    }

    private static Vector3f parsePositionV2(JsonElement jsonElement) {
        JsonElement jsonElement2 = PunchyAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return new Vector3f(f, -f2, f3);
        }
        return new Vector3f(0.0f);
    }

    private static Quaternionf parseEuler(JsonElement jsonElement) {
        Vector3f vector3f = PunchyAnimationLoader.parseRotation(jsonElement);
        float f = (float)Math.toRadians(vector3f.x);
        float f2 = (float)Math.toRadians(vector3f.y);
        float f3 = (float)Math.toRadians(vector3f.z);
        return new Quaternionf().rotationZYX(f3, f2, f);
    }

    private static Vector3f parseRotationEndpoint(JsonElement jsonElement, String string) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (!jsonElement.isJsonObject()) {
            return PunchyAnimationLoader.parseRotation(jsonElement);
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has(string)) {
            return null;
        }
        JsonElement jsonElement2 = jsonObject.get(string);
        return jsonElement2 != null && !jsonElement2.isJsonNull() ? PunchyAnimationLoader.parseRotation(jsonElement2) : null;
    }

    private static Vector3f parseRotationEndpointV2(JsonElement jsonElement, String string) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (!jsonElement.isJsonObject()) {
            return PunchyAnimationLoader.parseRotationV2(jsonElement);
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has(string)) {
            return null;
        }
        JsonElement jsonElement2 = jsonObject.get(string);
        return jsonElement2 != null && !jsonElement2.isJsonNull() ? PunchyAnimationLoader.parseRotationV2(jsonElement2) : null;
    }

    private static Vector3f parseRotation(JsonElement jsonElement) {
        JsonElement jsonElement2 = PunchyAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return new Vector3f(f, -f3, f2);
        }
        return new Vector3f(0.0f);
    }

    private static Vector3f parseRotationV2(JsonElement jsonElement) {
        JsonElement jsonElement2 = PunchyAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return new Vector3f(f, f2, f3);
        }
        return new Vector3f(0.0f);
    }

    private static Vector3f parseScale(JsonElement jsonElement, String string) {
        JsonElement jsonElement2 = PunchyAnimationLoader.unwrapKeyframeElement(jsonElement);
        if (jsonElement2 != null && jsonElement2.isJsonArray()) {
            float f = jsonElement2.getAsJsonArray().get(0).getAsFloat();
            float f2 = jsonElement2.getAsJsonArray().get(1).getAsFloat();
            float f3 = jsonElement2.getAsJsonArray().get(2).getAsFloat();
            return PunchyAnimationLoader.shouldSwapScaleAxes(string) ? new Vector3f(f, f3, f2) : new Vector3f(f, f2, f3);
        }
        return new Vector3f(1.0f, 1.0f, 1.0f);
    }

    private static String readInterpolation(JsonElement jsonElement) {
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("lerp_mode")) {
                return jsonObject.get("lerp_mode").getAsString().trim().toLowerCase(Locale.ROOT);
            }
            return jsonObject.has("easing") ? jsonObject.get("easing").getAsString().trim().toLowerCase(Locale.ROOT) : null;
        }
        return null;
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

    static void parseEffects(AnimationClip animationClip, JsonObject jsonObject) {
        if (animationClip != null && jsonObject != null) {
            if (jsonObject.has("effects")) {
                PunchyAnimationLoader.collectEffectStrings(animationClip, jsonObject.get("effects"));
            }
            if (jsonObject.has("effect")) {
                PunchyAnimationLoader.collectEffectStrings(animationClip, jsonObject.get("effect"));
            }
            if (jsonObject.has("events")) {
                PunchyAnimationLoader.collectEffectStrings(animationClip, jsonObject.get("events"));
            }
            if (jsonObject.has("event")) {
                PunchyAnimationLoader.collectEffectStrings(animationClip, jsonObject.get("event"));
            }
            if (jsonObject.has("sound_effects")) {
                PunchyAnimationLoader.collectSoundEffects(animationClip, jsonObject.get("sound_effects"));
            }
        }
    }

    static void parseTimelineEffects(AnimationClip animationClip, JsonObject jsonObject) {
        if (animationClip != null && jsonObject != null && jsonObject.has("timeline")) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("timeline");
            for (Map.Entry entry : jsonObject2.entrySet()) {
                float f = PunchyAnimationLoader.parseTime((String)entry.getKey());
                PunchyAnimationLoader.collectTimelineEffects(animationClip, f, (JsonElement)entry.getValue());
            }
        }
    }

    private static void collectEffectStrings(AnimationClip animationClip, JsonElement jsonElement) {
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
                    animationClip.addEffect(string3.toLowerCase(Locale.ROOT));
                }
                break block6;
            }
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    PunchyAnimationLoader.collectEffectStrings(animationClip, jsonElement2);
                }
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (Map.Entry entry : jsonObject.entrySet()) {
                    PunchyAnimationLoader.collectEffectStrings(animationClip, (JsonElement)entry.getValue());
                }
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
                    PunchyAnimationLoader.collectTimelineEffects(animationClip, f, jsonElement2);
                }
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (Map.Entry entry : jsonObject.entrySet()) {
                    PunchyAnimationLoader.collectTimelineEffects(animationClip, f, (JsonElement)entry.getValue());
                }
            }
        }
    }

    private static void collectSoundEffects(AnimationClip animationClip, JsonElement jsonElement) {
        if (animationClip != null && jsonElement != null && !jsonElement.isJsonNull() && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for (Map.Entry entry : jsonObject.entrySet()) {
                Object object;
                float f = PunchyAnimationLoader.parseTime((String)entry.getKey());
                JsonElement jsonElement2 = (JsonElement)entry.getValue();
                if (jsonElement2 != null && jsonElement2.isJsonObject()) {
                    String string;
                    JsonObject soundObject = jsonElement2.getAsJsonObject();
                    if (!soundObject.has("effect") || (string = soundObject.get("effect").getAsString()) == null || string.isBlank()) continue;
                    animationClip.addSoundEffect(f, string.trim());
                    continue;
                }
                if (jsonElement2 == null || !jsonElement2.isJsonPrimitive() || !jsonElement2.getAsJsonPrimitive().isString() || (object = jsonElement2.getAsString()) == null || ((String)object).isBlank()) continue;
                animationClip.addSoundEffect(f, ((String)object).trim());
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

    private static AnimationClip buildClip(String string, JsonObject jsonObject) {
        if (string != null && jsonObject != null) {
            float f = jsonObject.has("animation_length") ? jsonObject.get("animation_length").getAsFloat() : 0.0f;
            LoopInfo loopInfo = PunchyAnimationLoader.parseLoopInfo(jsonObject);
            AnimationClip animationClip = new AnimationClip(string, f, loopInfo.loop);
            animationClip.setHoldOnLastFrame(loopInfo.holdOnLastFrame);
            animationClip.setLengthDefined(jsonObject.has("animation_length"));
            animationClip.setLoopDefined(jsonObject.has("loop"));
            if (jsonObject.has("bones")) {
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("bones");
                for (String string2 : jsonObject2.keySet()) {
                    JsonObject jsonObject3 = jsonObject2.getAsJsonObject(string2);
                    animationClip.addTrack(PunchyAnimationLoader.parseBoneTrack(string2, jsonObject3, f, loopInfo.holdOnLastFrame));
                }
            }
            PunchyAnimationLoader.parseEffects(animationClip, jsonObject);
            PunchyAnimationLoader.parseTimelineEffects(animationClip, jsonObject);
            return animationClip;
        }
        return null;
    }

    public static String optionIdFromPackId(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            if (PunchyAnimationLoader.isPunchyBuiltInPackId(string2)) {
                return "pack:punchy:punchy";
            }
            if ("vanilla".equals(string2) || "mod_resources".equals(string2)) {
                return "punchy_default";
            }
            return string2.startsWith("pack:") ? string2 : "pack:" + string2;
        }
        return "punchy_default";
    }

    private static boolean isPunchyBuiltInPackId(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return "punchy".equals(string2) || string2.startsWith("punchy:") || string2.endsWith(":punchy") || string2.endsWith("/punchy") || string2.endsWith("\\punchy") || "builtin/punchy".equals(string2) || "builtin\\punchy".equals(string2);
        }
        return false;
    }

    static {
        DEFAULT_PACK_OPTION_ID = "punchy_default";
        BUILTIN_PACK_OPTION_ID = "pack:punchy:punchy";
        LOGGER = LoggerFactory.getLogger((String)"Punchy/AnimationLoader");
        INTERNAL_ANIMATION_IDS = List.of(class_2960.method_60655((String)"punchy", (String)"animations/player_3_p.animation.json"), class_2960.method_60655((String)"minecraft", (String)"punchy/animations/player.animation.json"));
    }

    static final class AnimationResourceEntry {
        final class_2960 id;
        final class_3298 resource;
        final int packPriority;
        final int discoveryOrder;
        final int stackOrder;

        AnimationResourceEntry(class_2960 class_29602, class_3298 class_32982, int n, int n2, int n3) {
            this.id = class_29602;
            this.resource = class_32982;
            this.packPriority = n;
            this.discoveryOrder = n2;
            this.stackOrder = n3;
        }

        @Override
        public final String toString() {
            return "AnimationResourceEntry[" + "id=" + this.id + ", " + "resource=" + this.resource + ", " + "packPriority=" + this.packPriority + ", " + "discoveryOrder=" + this.discoveryOrder + ", " + "stackOrder=" + this.stackOrder + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.id, this.resource, this.packPriority, this.discoveryOrder, this.stackOrder);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof AnimationResourceEntry other)) return false;
            return java.util.Objects.deepEquals(this.id, other.id)
                && java.util.Objects.deepEquals(this.resource, other.resource)
                && java.util.Objects.deepEquals(this.packPriority, other.packPriority)
                && java.util.Objects.deepEquals(this.discoveryOrder, other.discoveryOrder)
                && java.util.Objects.deepEquals(this.stackOrder, other.stackOrder);
        }

        public class_2960 id() {
            return this.id;
        }

        public class_3298 resource() {
            return this.resource;
        }

        public int packPriority() {
            return this.packPriority;
        }

        public int discoveryOrder() {
            return this.discoveryOrder;
        }

        public int stackOrder() {
            return this.stackOrder;
        }
    }

    static class KeyframeBuilder {
        Vector3f position;
        Vector3f positionV2;
        Vector3f rotationEuler;
        Quaternionf rotation;
        Vector3f scale;
        String interpolation;
        Vector3f rotationPreEulerV2;
        Vector3f rotationPostEulerV2;

        KeyframeBuilder() {
        }
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
}

