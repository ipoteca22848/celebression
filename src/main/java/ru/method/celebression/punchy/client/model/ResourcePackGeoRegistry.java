package ru.method.celebression.punchy.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.model.BedrockBone;
import ru.method.celebression.punchy.client.model.BedrockGeoModel;

public final class ResourcePackGeoRegistry {
    private static final Map<class_2960, BedrockGeoModel> GEO_MODELS = new HashMap<class_2960, BedrockGeoModel>();
    private static final Map<class_2960, BedrockGeoModel> MODEL_BONES = new HashMap<class_2960, BedrockGeoModel>();
    private static final Map<class_2960, class_2960> TARGET_TO_GEO = new HashMap<class_2960, class_2960>();
    private static final Map<class_2960, class_2960> TARGET_TO_MODEL = new HashMap<class_2960, class_2960>();
    private static final Map<class_2960, List<ModelCondition>> TARGET_TO_CONDITIONAL_MODEL = new HashMap<class_2960, List<ModelCondition>>();

    private ResourcePackGeoRegistry() {
    }

    public static void load(class_3300 class_33002) {
        GEO_MODELS.clear();
        MODEL_BONES.clear();
        TARGET_TO_GEO.clear();
        TARGET_TO_MODEL.clear();
        TARGET_TO_CONDITIONAL_MODEL.clear();
        if (class_33002 != null) {
            ResourcePackGeoRegistry.loadGeoModels(class_33002);
            ResourcePackGeoRegistry.loadModelBones(class_33002);
            ResourcePackGeoRegistry.loadMappings(class_33002);
        }
    }

    public static BedrockGeoModel getGeoModel(class_2960 class_29602) {
        return class_29602 != null ? GEO_MODELS.get(class_29602) : null;
    }

    public static class_2960 getGeoForTarget(class_2960 class_29602) {
        return class_29602 != null ? TARGET_TO_GEO.get(class_29602) : null;
    }

    public static class_2960 getModelForTarget(class_2960 class_29602) {
        return class_29602 != null ? TARGET_TO_MODEL.get(class_29602) : null;
    }

    public static class_2960 getModelForTarget(class_1799 class_17992, class_1657 class_16572) {
        if (class_17992 != null && !class_17992.method_7960() && class_16572 != null) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return null;
            }
            class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)("item/" + class_29602.method_12832()));
            List<ModelCondition> list = TARGET_TO_CONDITIONAL_MODEL.get(class_29603);
            if (list != null && !list.isEmpty()) {
                for (ModelCondition modelCondition : list) {
                    if (modelCondition == null || modelCondition.modelId == null || modelCondition.requiredEnchant != null && !ResourcePackGeoRegistry.hasEnchantment(class_16572, class_17992, modelCondition.requiredEnchant)) continue;
                    return modelCondition.modelId;
                }
            }
            return TARGET_TO_MODEL.get(class_29603);
        }
        return null;
    }

    public static Collection<class_2960> getMappedModelIds() {
        ArrayList<class_2960> arrayList = new ArrayList<class_2960>(TARGET_TO_MODEL.values());
        for (List<ModelCondition> list : TARGET_TO_CONDITIONAL_MODEL.values()) {
            if (list == null) continue;
            for (ModelCondition modelCondition : list) {
                if (modelCondition == null || modelCondition.modelId == null) continue;
                arrayList.add(modelCondition.modelId);
            }
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public static BedrockGeoModel getBonesForTarget(class_2960 class_29602) {
        class_2960 class_29603 = ResourcePackGeoRegistry.getGeoForTarget(class_29602);
        BedrockGeoModel bedrockGeoModel = class_29603 != null ? ResourcePackGeoRegistry.getGeoModel(class_29603) : null;
        if (bedrockGeoModel != null) {
            return bedrockGeoModel;
        }
        class_2960 modelId = ResourcePackGeoRegistry.getModelForTarget(class_29602);
        return modelId != null ? MODEL_BONES.get(modelId) : null;
    }

    private static void loadGeoModels(class_3300 class_33002) {
        Map<class_2960, class_3298> map = class_33002.method_14488("geo", class_29602 -> class_29602.method_12832().startsWith("geo/") && class_29602.method_12832().endsWith(".geo.json"));
        for (Map.Entry<class_2960, class_3298> entry : map.entrySet()) {
            class_2960 class_29603 = entry.getKey();
            try {
                JsonObject jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(entry.getValue().method_14482())).getAsJsonObject();
                BedrockGeoModel bedrockGeoModel = ResourcePackGeoRegistry.parseGeo(jsonObject);
                if (bedrockGeoModel == null) continue;
                GEO_MODELS.put(class_29603, bedrockGeoModel);
            }
            catch (Exception exception) {}
        }
    }

    private static void loadModelBones(class_3300 class_33002) {
        Map<class_2960, class_3298> map = class_33002.method_14488("models", class_29602 -> class_29602.method_12832().startsWith("models/") && class_29602.method_12832().endsWith(".json"));
        for (Map.Entry<class_2960, class_3298> entry : map.entrySet()) {
            class_2960 class_29603 = entry.getKey();
            try {
                JsonObject jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(entry.getValue().method_14482())).getAsJsonObject();
                BedrockGeoModel bedrockGeoModel = ResourcePackGeoRegistry.parseModelAsBones(jsonObject);
                if (bedrockGeoModel == null) continue;
                MODEL_BONES.put(class_29603, bedrockGeoModel);
            }
            catch (Exception exception) {}
        }
    }

    private static BedrockGeoModel parseGeo(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray("minecraft:geometry");
        if (jsonArray != null && !jsonArray.isEmpty()) {
            JsonObject geometry = jsonArray.get(0).getAsJsonObject();
            JsonArray bones = geometry.getAsJsonArray("bones");
            if (bones == null) {
                return null;
            }
            HashMap<String, Vector3f> pivots = new HashMap<>();
            for (JsonElement element : bones) {
                if (element == null || !element.isJsonObject()) continue;
                JsonObject bone = element.getAsJsonObject();
                String name = bone.has("name") ? bone.get("name").getAsString() : null;
                if (name == null || name.isBlank()) continue;
                pivots.put(name, ResourcePackGeoRegistry.readVec3(bone, "pivot", new Vector3f()));
            }
            BedrockGeoModel bedrockGeoModel = new BedrockGeoModel();
            for (JsonElement element : bones) {
                if (element == null || !element.isJsonObject()) continue;
                JsonObject bone = element.getAsJsonObject();
                String name = bone.has("name") ? bone.get("name").getAsString() : null;
                if (name == null || name.isBlank()) continue;
                String parentName = bone.has("parent") ? bone.get("parent").getAsString() : null;
                Vector3f pivot = pivots.getOrDefault(name, new Vector3f());
                Vector3f rotation = ResourcePackGeoRegistry.readVec3(bone, "rotation", new Vector3f());
                Vector3f relativePivot = new Vector3f(pivot);
                Vector3f parentPivot = parentName != null ? pivots.get(parentName) : null;
                if (parentPivot != null) {
                    relativePivot.sub(parentPivot);
                }
                bedrockGeoModel.addBone(new BedrockBone(name, parentName, new Vector3f(pivot), rotation, relativePivot));
            }
            return bedrockGeoModel;
        }
        return null;
    }

    private static BedrockGeoModel parseModelAsBones(JsonObject jsonObject) {
        if (jsonObject != null && (jsonObject.has("elements") || jsonObject.has("groups"))) {
            JsonArray jsonArray = jsonObject.getAsJsonArray("elements");
            JsonArray jsonArray2 = jsonObject.getAsJsonArray("groups");
            HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
            HashMap<String, Vector3f> hashMap2 = new HashMap<String, Vector3f>();
            HashMap<String, String> hashMap3 = new HashMap<String, String>();
            BedrockGeoModel bedrockGeoModel = new BedrockGeoModel();
            if (jsonArray2 != null) {
                ResourcePackGeoRegistry.parseGroups(jsonArray2, null, hashMap2, hashMap3, hashMap, bedrockGeoModel);
            }
            int n = 0;
            if (jsonArray == null) {
                return bedrockGeoModel;
            }
            for (JsonElement jsonElement : jsonArray) {
                Vector3f vector3f;
                JsonObject jsonObject2 = jsonElement.getAsJsonObject();
                String string = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "cube_" + n;
                Vector3f vector3f2 = ResourcePackGeoRegistry.readVec3(jsonObject2, "from", new Vector3f(0.0f, 0.0f, 0.0f));
                Vector3f vector3f3 = ResourcePackGeoRegistry.readVec3(jsonObject2, "to", new Vector3f(0.0f, 0.0f, 0.0f));
                Vector3f vector3f4 = new Vector3f(0.0f, 0.0f, 0.0f);
                if (jsonObject2.has("rotation")) {
                    JsonObject rotation = jsonObject2.getAsJsonObject("rotation");
                    float f = rotation.has("angle") ? rotation.get("angle").getAsFloat() : 0.0f;
                    String axis = rotation.has("axis") ? rotation.get("axis").getAsString() : "y";
                    if ("x".equals(axis)) {
                        vector3f4.x = f;
                    }
                    if ("y".equals(axis)) {
                        vector3f4.y = f;
                    }
                    if ("z".equals(axis)) {
                        vector3f4.z = f;
                    }
                    if (rotation.has("origin")) {
                        JsonArray jsonArray3 = rotation.getAsJsonArray("origin");
                        vector3f = new Vector3f(jsonArray3.get(0).getAsFloat(), jsonArray3.get(1).getAsFloat(), jsonArray3.get(2).getAsFloat());
                    } else {
                        vector3f = new Vector3f((vector3f2.x + vector3f3.x) * 0.5f, (vector3f2.y + vector3f3.y) * 0.5f, (vector3f2.z + vector3f3.z) * 0.5f);
                    }
                } else {
                    vector3f = new Vector3f((vector3f2.x + vector3f3.x) * 0.5f, (vector3f2.y + vector3f3.y) * 0.5f, (vector3f2.z + vector3f3.z) * 0.5f);
                }
                String parentName = hashMap.get(n);
                Vector3f vector3f5 = vector3f;
                Vector3f parentPivot = parentName != null ? hashMap2.get(parentName) : null;
                if (parentPivot != null) {
                    vector3f5 = new Vector3f(vector3f).sub(parentPivot);
                }
                bedrockGeoModel.addBone(new BedrockBone(string, parentName, vector3f, vector3f4, vector3f5));
                ++n;
            }
            return bedrockGeoModel;
        }
        return null;
    }

    private static void parseGroups(JsonArray jsonArray, String string, Map<String, Vector3f> map, Map<String, String> map2, Map<Integer, String> map3, BedrockGeoModel bedrockGeoModel) {
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject;
            if (!jsonElement.isJsonObject() || !(jsonObject = jsonElement.getAsJsonObject()).has("name")) continue;
            String string2 = jsonObject.get("name").getAsString();
            Vector3f vector3f = ResourcePackGeoRegistry.readVec3(jsonObject, "origin", new Vector3f(0.0f, 0.0f, 0.0f));
            Vector3f vector3f2 = ResourcePackGeoRegistry.readVec3(jsonObject, "rotation", new Vector3f(0.0f, 0.0f, 0.0f));
            Vector3f vector3f3 = vector3f;
            Vector3f parentPivot = string != null ? map.get(string) : null;
            if (parentPivot != null) {
                vector3f3 = new Vector3f(vector3f).sub(parentPivot);
            }
            map.put(string2, vector3f);
            if (string != null) {
                map2.put(string2, string);
            }
            bedrockGeoModel.addBone(new BedrockBone(string2, string, vector3f, vector3f2, vector3f3));
            if (!jsonObject.has("children")) continue;
            for (JsonElement jsonElement2 : jsonObject.getAsJsonArray("children")) {
                if (jsonElement2.isJsonPrimitive() && jsonElement2.getAsJsonPrimitive().isNumber()) {
                    map3.put(jsonElement2.getAsInt(), string2);
                    continue;
                }
                if (!jsonElement2.isJsonObject()) continue;
                JsonArray jsonArray2 = new JsonArray();
                jsonArray2.add(jsonElement2);
                ResourcePackGeoRegistry.parseGroups(jsonArray2, string2, map, map2, map3, bedrockGeoModel);
            }
        }
    }

    private static Vector3f readVec3(JsonObject jsonObject, String string, Vector3f vector3f) {
        if (!jsonObject.has(string)) {
            return vector3f;
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray(string);
        return jsonArray != null && jsonArray.size() >= 3 ? new Vector3f(jsonArray.get(0).getAsFloat(), jsonArray.get(1).getAsFloat(), jsonArray.get(2).getAsFloat()) : vector3f;
    }

    private static void loadMappings(class_3300 class_33002) {
        Map<class_2960, class_3298> map = class_33002.method_14488("punchy", class_29602 -> class_29602.method_12832().startsWith("punchy/") && class_29602.method_12832().endsWith("rp_geo_map.json"));
        for (Map.Entry<class_2960, class_3298> entry : map.entrySet()) {
            try {
                JsonObject jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(entry.getValue().method_14482())).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("mappings");
                if (jsonArray == null) continue;
                for (JsonElement jsonElement : jsonArray) {
                    class_2960 class_29603;
                    class_2960 class_29604;
                    JsonObject jsonObject2 = jsonElement.getAsJsonObject();
                    if (!jsonObject2.has("target") || (class_29604 = class_2960.method_12829((String)jsonObject2.get("target").getAsString())) == null) continue;
                    if (jsonObject2.has("geo") && (class_29603 = class_2960.method_12829((String)jsonObject2.get("geo").getAsString())) != null) {
                        TARGET_TO_GEO.put(class_29604, class_29603);
                    }
                    if (!jsonObject2.has("model") || (class_29603 = ResourcePackGeoRegistry.resolveModelId(jsonObject2.get("model").getAsString())) == null) continue;
                    class_2960 class_29605 = null;
                    if (jsonObject2.has("require_enchant")) {
                        class_29605 = class_2960.method_12829((String)jsonObject2.get("require_enchant").getAsString());
                    }
                    if (class_29605 != null) {
                        TARGET_TO_CONDITIONAL_MODEL.computeIfAbsent(class_29604, class_29602 -> new ArrayList()).add(new ModelCondition(class_29603, class_29605));
                        continue;
                    }
                    TARGET_TO_MODEL.put(class_29604, class_29603);
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static class_2960 resolveModelId(String string) {
        class_2960 class_29602 = class_2960.method_12829((String)string);
        if (class_29602 == null) {
            return null;
        }
        String path = class_29602.method_12832();
        if (!path.startsWith("models/")) {
            path = "models/" + path;
        }
        if (!path.endsWith(".json")) {
            path += ".json";
        }
        return class_2960.method_60655(class_29602.method_12836(), path);
    }

    private static boolean hasEnchantment(class_1657 class_16572, class_1799 class_17992, class_2960 class_29602) {
        try {
            if (class_16572 != null && class_17992 != null && !class_17992.method_7960() && class_29602 != null) {
                class_5321 class_53212;
                class_2378 class_23782 = class_16572.method_37908().method_30349().method_30530(class_7924.field_41265);
                class_6880.class_6883 class_68832 = class_23782.method_46747(class_53212 = class_5321.method_29179((class_5321)class_7924.field_41265, (class_2960)class_29602));
                return class_1890.method_8225((class_6880)class_68832, (class_1799)class_17992) > 0;
            }
            return false;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    static final class ModelCondition {
        final class_2960 modelId;
        final class_2960 requiredEnchant;

        ModelCondition(class_2960 class_29602, class_2960 class_29603) {
            this.modelId = class_29602;
            this.requiredEnchant = class_29603;
        }
    }
}
