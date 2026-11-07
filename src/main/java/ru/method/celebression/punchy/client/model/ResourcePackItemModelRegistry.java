package ru.method.celebression.punchy.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1306;
import net.minecraft.class_2350;
import net.minecraft.class_2960;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.model.ItemModelDefinition;
import ru.method.celebression.punchy.client.model.ResourcePackGeoRegistry;

public final class ResourcePackItemModelRegistry {
    private static final Map<class_2960, ItemModelDefinition> MODELS = new HashMap<class_2960, ItemModelDefinition>();

    private ResourcePackItemModelRegistry() {
    }

    public static void load(class_3300 class_33002) {
        MODELS.clear();
        if (class_33002 != null) {
            for (class_2960 class_29602 : ResourcePackGeoRegistry.getMappedModelIds()) {
                if (class_29602 == null) continue;
                try {
                    class_3298 class_32982 = class_33002.getResourceOrThrow(class_29602);
                    JsonObject jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(class_32982.method_14482())).getAsJsonObject();
                    ItemModelDefinition itemModelDefinition = ResourcePackItemModelRegistry.parseModel(class_29602, jsonObject);
                    if (itemModelDefinition == null) continue;
                    MODELS.put(class_29602, itemModelDefinition);
                }
                catch (Exception exception) {}
            }
        }
    }

    public static ItemModelDefinition getModel(class_2960 class_29602) {
        return class_29602 == null ? null : MODELS.get(class_29602);
    }

    private static ItemModelDefinition parseModel(class_2960 class_29602, JsonObject jsonObject) {
        if (class_29602 != null && jsonObject != null && jsonObject.has("elements")) {
            int n = 16;
            int n2 = 16;
            JsonArray textureSize = jsonObject.getAsJsonArray("texture_size");
            if (textureSize != null && textureSize.size() >= 2) {
                n = textureSize.get(0).getAsInt();
                n2 = textureSize.get(1).getAsInt();
            }
            HashMap<String, String> textures = new HashMap<>();
            JsonObject texturesObject = jsonObject.getAsJsonObject("textures");
            if (texturesObject != null) {
                for (Map.Entry<String, JsonElement> entry : texturesObject.entrySet()) {
                    if (!entry.getValue().isJsonPrimitive()) continue;
                    textures.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
            HashMap<Integer, String> elementParents = new HashMap<>();
            HashMap<String, Vector3f> groupPivots = new HashMap<>();
            HashMap<String, Vector3f> groupRotations = new HashMap<>();
            HashMap<String, String> groupParents = new HashMap<>();
            LinkedHashMap<String, ItemModelDefinition.BoneDef> linkedHashMap = new LinkedHashMap<>();
            HashMap<String, String> hashMap3 = new HashMap<>();
            HashSet<String> hashSet = new HashSet<>();
            if (jsonObject.has("groups")) {
                ResourcePackItemModelRegistry.parseGroups(jsonObject.getAsJsonArray("groups"), null, groupPivots, groupRotations, groupParents, elementParents);
                for (Map.Entry<String, Vector3f> entry : groupPivots.entrySet()) {
                    String name = entry.getKey();
                    String parent = groupParents.get(name);
                    Vector3f rotation = groupRotations.getOrDefault(name, new Vector3f());
                    linkedHashMap.put(name, new ItemModelDefinition.BoneDef(name, parent, entry.getValue(), rotation));
                    hashMap3.put(name, parent);
                    hashSet.add(name);
                }
            }
            JsonArray jsonArray = jsonObject.getAsJsonArray("elements");
            ArrayList<ItemModelDefinition.Element> arrayList = new ArrayList<>();
            if (jsonArray != null) {
                int elementIndex = 0;
                for (JsonElement jsonElement : jsonArray) {
                    if (jsonElement == null || !jsonElement.isJsonObject()) continue;
                    JsonObject jsonObject2 = jsonElement.getAsJsonObject();
                    String string2 = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "cube_" + elementIndex;
                    String string3 = ResourcePackItemModelRegistry.uniqueName(string2, hashSet, elementIndex);
                    Vector3f vector3f = ResourcePackItemModelRegistry.readVec3(jsonObject2, "from", new Vector3f(0.0f, 0.0f, 0.0f));
                    Vector3f vector3f2 = ResourcePackItemModelRegistry.readVec3(jsonObject2, "to", new Vector3f(0.0f, 0.0f, 0.0f));
                    Vector3f vector3f3 = new Vector3f((vector3f.x + vector3f2.x) * 0.5f, (vector3f.y + vector3f2.y) * 0.5f, (vector3f.z + vector3f2.z) * 0.5f);
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
                            vector3f3 = ResourcePackItemModelRegistry.readVec3(rotation, "origin", vector3f3);
                        }
                    }
                    String parent = elementParents.get(elementIndex);
                    linkedHashMap.put(string3, new ItemModelDefinition.BoneDef(string3, parent, vector3f3, vector3f4));
                    hashMap3.put(string3, parent);
                    hashSet.add(string3);
                    Vector3f localFrom = new Vector3f(vector3f).sub(vector3f3);
                    Vector3f localTo = new Vector3f(vector3f2).sub(vector3f3);
                    EnumMap<class_2350, ItemModelDefinition.Face> enumMap = ResourcePackItemModelRegistry.parseFaces(jsonObject2.getAsJsonObject("faces"));
                    int n3 = 0;
                    if (jsonObject2.has("light_emission")) {
                        n3 = jsonObject2.get("light_emission").getAsInt();
                        if (n3 < 0) {
                            n3 = 0;
                        } else if (n3 > 15) {
                            n3 = 15;
                        }
                    }
                    boolean bl = jsonObject2.has("world_light") && jsonObject2.get("world_light").getAsBoolean();
                    arrayList.add(new ItemModelDefinition.Element(string3, localFrom, localTo, enumMap, n3, bl));
                    ++elementIndex;
                }
            }
            ItemModelDefinition itemModelDefinition = new ItemModelDefinition(class_29602.method_12836(), n, n2, textures, arrayList, linkedHashMap, hashMap3, ItemModelDefinition.buildPaths(hashMap3));
            ResourcePackItemModelRegistry.parseDisplay(jsonObject, itemModelDefinition);
            return itemModelDefinition;
        }
        return null;
    }

    private static void parseGroups(JsonArray jsonArray, String string, Map<String, Vector3f> map, Map<String, Vector3f> map2, Map<String, String> map3, Map<Integer, String> map4) {
        if (jsonArray != null) {
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject;
                if (!jsonElement.isJsonObject() || !(jsonObject = jsonElement.getAsJsonObject()).has("name")) continue;
                String string2 = jsonObject.get("name").getAsString();
                Vector3f vector3f = ResourcePackItemModelRegistry.readVec3(jsonObject, "origin", new Vector3f(0.0f, 0.0f, 0.0f));
                Vector3f vector3f2 = ResourcePackItemModelRegistry.readVec3(jsonObject, "rotation", new Vector3f(0.0f, 0.0f, 0.0f));
                map.put(string2, vector3f);
                map2.put(string2, vector3f2);
                if (string != null) {
                    map3.put(string2, string);
                }
                if (!jsonObject.has("children")) continue;
                for (JsonElement jsonElement2 : jsonObject.getAsJsonArray("children")) {
                    if (jsonElement2.isJsonPrimitive() && jsonElement2.getAsJsonPrimitive().isNumber()) {
                        map4.put(jsonElement2.getAsInt(), string2);
                        continue;
                    }
                    if (!jsonElement2.isJsonObject()) continue;
                    JsonArray jsonArray2 = new JsonArray();
                    jsonArray2.add(jsonElement2);
                    ResourcePackItemModelRegistry.parseGroups(jsonArray2, string2, map, map2, map3, map4);
                }
            }
        }
    }

    private static EnumMap<class_2350, ItemModelDefinition.Face> parseFaces(JsonObject jsonObject) {
        EnumMap<class_2350, ItemModelDefinition.Face> enumMap = new EnumMap<class_2350, ItemModelDefinition.Face>(class_2350.class);
        if (jsonObject == null) {
            return enumMap;
        }
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonArray jsonArray;
            JsonObject jsonObject2;
            class_2350 class_23502 = ResourcePackItemModelRegistry.parseDirection(entry.getKey());
            if (class_23502 == null || !entry.getValue().isJsonObject() || !(jsonObject2 = entry.getValue().getAsJsonObject()).has("uv") || !jsonObject2.has("texture") || (jsonArray = jsonObject2.getAsJsonArray("uv")) == null || jsonArray.size() < 4) continue;
            float f = jsonArray.get(0).getAsFloat();
            float f2 = jsonArray.get(1).getAsFloat();
            float f3 = jsonArray.get(2).getAsFloat();
            float f4 = jsonArray.get(3).getAsFloat();
            int n = jsonObject2.has("rotation") ? jsonObject2.get("rotation").getAsInt() : 0;
            String string = jsonObject2.get("texture").getAsString();
            enumMap.put(class_23502, new ItemModelDefinition.Face(f, f2, f3, f4, n, string));
        }
        return enumMap;
    }

    private static class_2350 parseDirection(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        return switch (string2 = string.toLowerCase()) {
            case "north" -> class_2350.field_11043;
            case "south" -> class_2350.field_11035;
            case "east" -> class_2350.field_11034;
            case "west" -> class_2350.field_11039;
            case "up" -> class_2350.field_11036;
            case "down" -> class_2350.field_11033;
            default -> null;
        };
    }

    private static Vector3f readVec3(JsonObject jsonObject, String string, Vector3f vector3f) {
        if (!jsonObject.has(string)) {
            return vector3f;
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray(string);
        return jsonArray != null && jsonArray.size() >= 3 ? new Vector3f(jsonArray.get(0).getAsFloat(), jsonArray.get(1).getAsFloat(), jsonArray.get(2).getAsFloat()) : vector3f;
    }

    private static void parseDisplay(JsonObject jsonObject, ItemModelDefinition itemModelDefinition) {
        if (jsonObject != null && itemModelDefinition != null && jsonObject.has("display")) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("display");
            boolean bl = ResourcePackItemModelRegistry.parseDisplayEntry(jsonObject2, "thirdperson_righthand", class_1306.field_6183, itemModelDefinition);
            boolean bl2 = ResourcePackItemModelRegistry.parseDisplayEntry(jsonObject2, "thirdperson_lefthand", class_1306.field_6182, itemModelDefinition);
            if (!bl) {
                ResourcePackItemModelRegistry.parseDisplayEntry(jsonObject2, "firstperson_righthand", class_1306.field_6183, itemModelDefinition);
            }
            if (!bl2) {
                ResourcePackItemModelRegistry.parseDisplayEntry(jsonObject2, "firstperson_lefthand", class_1306.field_6182, itemModelDefinition);
            }
        }
    }

    private static boolean parseDisplayEntry(JsonObject jsonObject, String string, class_1306 class_13062, ItemModelDefinition itemModelDefinition) {
        if (!jsonObject.has(string)) {
            return false;
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
        Vector3f vector3f = ResourcePackItemModelRegistry.readVec3(jsonObject2, "rotation", new Vector3f(0.0f, 0.0f, 0.0f));
        Vector3f vector3f2 = ResourcePackItemModelRegistry.readVec3(jsonObject2, "translation", new Vector3f(0.0f, 0.0f, 0.0f));
        Vector3f vector3f3 = ResourcePackItemModelRegistry.readVec3(jsonObject2, "scale", new Vector3f(1.0f, 1.0f, 1.0f));
        itemModelDefinition.setDisplayTransform(class_13062, new ItemModelDefinition.DisplayTransform(vector3f, vector3f2, vector3f3));
        return true;
    }

    private static String uniqueName(String object, Set<String> set, int n) {
        if (object == null || ((String)object).isBlank()) {
            object = "cube_" + n;
        }
        if (!set.contains(object)) {
            return object;
        }
        String string = (String)object + "_" + n;
        if (!set.contains(string)) {
            return string;
        }
        string = "cube_" + n;
        set.add(string);
        return string;
    }
}
