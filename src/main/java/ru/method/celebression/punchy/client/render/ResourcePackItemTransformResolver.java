package ru.method.celebression.punchy.client.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_7923;
import net.minecraft.class_804;
import net.minecraft.class_811;
import org.joml.Vector3f;

public final class ResourcePackItemTransformResolver {
    private static class_3300 cachedManager;
    private static final Map<class_2960, JsonObject> ITEM_DEFINITIONS;
    private static final Map<class_2960, JsonObject> MODEL_DEFINITIONS;

    private ResourcePackItemTransformResolver() {
    }

    public static class_804 resolve(class_1799 class_17992, class_811 class_8112) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && class_3102.method_1478() != null) {
                class_2960 class_29602;
                class_3300 class_33002 = class_3102.method_1478();
                if (class_33002 != cachedManager) {
                    cachedManager = class_33002;
                    ITEM_DEFINITIONS.clear();
                    MODEL_DEFINITIONS.clear();
                }
                if ((class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909())) == null) {
                    return null;
                }
                class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)("items/" + class_29602.method_12832() + ".json"));
                JsonObject jsonObject = ResourcePackItemTransformResolver.loadPreferredResourceJson(class_33002, class_29603, ITEM_DEFINITIONS);
                if (jsonObject != null && jsonObject.has("model") && jsonObject.get("model").isJsonObject()) {
                    class_2960 class_29604 = ResourcePackItemTransformResolver.resolveModelId(jsonObject.getAsJsonObject("model"), class_17992, class_8112);
                    return class_29604 == null ? null : ResourcePackItemTransformResolver.loadDisplayTransform(class_33002, class_29604, class_8112);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private static class_2960 resolveModelId(JsonObject jsonObject, class_1799 class_17992, class_811 class_8112) {
        if (jsonObject == null) {
            return null;
        }
        String string = ResourcePackItemTransformResolver.getString(jsonObject, "type");
        if ("minecraft:model".equals(string)) {
            String string2 = ResourcePackItemTransformResolver.getString(jsonObject, "model");
            return string2 == null ? null : class_2960.method_12829((String)string2);
        }
        if ("minecraft:condition".equals(string)) {
            String string3 = ResourcePackItemTransformResolver.getString(jsonObject, "property");
            if ("minecraft:using_item".equals(string3)) {
                class_2960 class_29602 = ResourcePackItemTransformResolver.resolveBranch(jsonObject, "on_false", class_17992, class_8112);
                return class_29602 != null ? class_29602 : ResourcePackItemTransformResolver.resolveBranch(jsonObject, "on_true", class_17992, class_8112);
            }
            class_2960 class_29603 = ResourcePackItemTransformResolver.resolveBranch(jsonObject, "on_false", class_17992, class_8112);
            return class_29603 != null ? class_29603 : ResourcePackItemTransformResolver.resolveBranch(jsonObject, "on_true", class_17992, class_8112);
        }
        if ("minecraft:range_dispatch".equals(string)) {
            JsonArray jsonArray = jsonObject.getAsJsonArray("entries");
            if (jsonArray != null) {
                for (JsonElement jsonElement : jsonArray) {
                    class_2960 class_29604;
                    JsonObject jsonObject2;
                    if (!jsonElement.isJsonObject() || !(jsonObject2 = jsonElement.getAsJsonObject()).has("model") || !jsonObject2.get("model").isJsonObject() || (class_29604 = ResourcePackItemTransformResolver.resolveModelId(jsonObject2.getAsJsonObject("model"), class_17992, class_8112)) == null) continue;
                    return class_29604;
                }
            }
            return ResourcePackItemTransformResolver.resolveFallback(jsonObject, class_17992, class_8112);
        }
        if ("minecraft:select".equals(string)) {
            String string4 = ResourcePackItemTransformResolver.getString(jsonObject, "property");
            if (string4 == null) {
                return ResourcePackItemTransformResolver.resolveFallback(jsonObject, class_17992, class_8112);
            }
            if ("minecraft:display_context".equals(string4)) {
                String string5 = ResourcePackItemTransformResolver.toDisplayContextName(class_8112);
                JsonArray jsonArray = jsonObject.getAsJsonArray("cases");
                if (jsonArray != null) {
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject jsonObject3;
                        if (!jsonElement.isJsonObject() || !ResourcePackItemTransformResolver.matchesDisplayContext((jsonObject3 = jsonElement.getAsJsonObject()).get("when"), string5) || !jsonObject3.has("model") || !jsonObject3.get("model").isJsonObject()) continue;
                        return ResourcePackItemTransformResolver.resolveModelId(jsonObject3.getAsJsonObject("model"), class_17992, class_8112);
                    }
                }
                return ResourcePackItemTransformResolver.resolveFallback(jsonObject, class_17992, class_8112);
            }
            if ("minecraft:component".equals(string4)) {
                return ResourcePackItemTransformResolver.resolveFallback(jsonObject, class_17992, class_8112);
            }
        }
        return ResourcePackItemTransformResolver.resolveFallback(jsonObject, class_17992, class_8112);
    }

    private static class_2960 resolveFallback(JsonObject jsonObject, class_1799 class_17992, class_811 class_8112) {
        return jsonObject != null && jsonObject.has("fallback") && jsonObject.get("fallback").isJsonObject() ? ResourcePackItemTransformResolver.resolveModelId(jsonObject.getAsJsonObject("fallback"), class_17992, class_8112) : null;
    }

    private static class_2960 resolveBranch(JsonObject jsonObject, String string, class_1799 class_17992, class_811 class_8112) {
        return jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string).isJsonObject() ? ResourcePackItemTransformResolver.resolveModelId(jsonObject.getAsJsonObject(string), class_17992, class_8112) : null;
    }

    private static boolean matchesDisplayContext(JsonElement jsonElement, String string) {
        if (jsonElement == null || string == null) {
            return false;
        }
        if (jsonElement.isJsonPrimitive()) {
            return string.equals(jsonElement.getAsString());
        }
        if (jsonElement.isJsonArray()) {
            for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                if (jsonElement2 == null || !jsonElement2.isJsonPrimitive() || !string.equals(jsonElement2.getAsString())) continue;
                return true;
            }
        }
        return false;
    }

    private static String toDisplayContextName(class_811 class_8112) {
        return switch (class_8112) {
            case class_811.field_4321 -> "firstperson_lefthand";
            case class_811.field_4322 -> "firstperson_righthand";
            case class_811.field_4323 -> "thirdperson_lefthand";
            case class_811.field_4320 -> "thirdperson_righthand";
            case class_811.field_4316 -> "head";
            case class_811.field_4317 -> "gui";
            case class_811.field_4318 -> "ground";
            case class_811.field_4319 -> "fixed";
            default -> class_8112.name().toLowerCase(Locale.ROOT);
        };
    }

    private static class_804 loadDisplayTransform(class_3300 class_33002, class_2960 class_29602, class_811 class_8112) {
        class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)("models/" + class_29602.method_12832() + ".json"));
        JsonObject jsonObject = ResourcePackItemTransformResolver.loadPreferredResourceJson(class_33002, class_29603, MODEL_DEFINITIONS);
        if (jsonObject == null) {
            return null;
        }
        class_804 class_8042 = ResourcePackItemTransformResolver.parseDisplayTransform(jsonObject, class_8112);
        if (class_8042 != null) {
            return class_8042;
        }
        String string = ResourcePackItemTransformResolver.getString(jsonObject, "parent");
        if (string != null && !string.isBlank()) {
            class_2960 class_29604 = class_2960.method_12829((String)string);
            return class_29604 != null && !class_29604.equals((Object)class_29602) ? ResourcePackItemTransformResolver.loadDisplayTransform(class_33002, class_29604, class_8112) : null;
        }
        return null;
    }

    private static class_804 parseDisplayTransform(JsonObject jsonObject, class_811 class_8112) {
        if (jsonObject != null && class_8112 != null && jsonObject.has("display") && jsonObject.get("display").isJsonObject()) {
            String string;
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("display");
            if (jsonObject2.has(string = ResourcePackItemTransformResolver.toDisplayContextName(class_8112)) && jsonObject2.get(string).isJsonObject()) {
                JsonObject jsonObject3 = jsonObject2.getAsJsonObject(string);
                Vector3f vector3f = ResourcePackItemTransformResolver.readVec3(jsonObject3, "rotation", new Vector3f());
                Vector3f vector3f2 = ResourcePackItemTransformResolver.readVec3(jsonObject3, "translation", new Vector3f()).mul(0.0625f);
                Vector3f vector3f3 = ResourcePackItemTransformResolver.readVec3(jsonObject3, "scale", new Vector3f(1.0f, 1.0f, 1.0f));
                return new class_804(vector3f, vector3f2, vector3f3);
            }
            return null;
        }
        return null;
    }

    private static JsonObject loadPreferredResourceJson(class_3300 class_33002, class_2960 class_29602, Map<class_2960, JsonObject> map) {
        if (class_33002 == null || class_29602 == null) {
            return null;
        }
        if (map.containsKey(class_29602)) {
            return map.get(class_29602);
        }
        try {
            List list = class_33002.method_14489(class_29602);
            if (list != null && !list.isEmpty()) {
                JsonObject jsonObject;
                class_3298 class_32982 = ResourcePackItemTransformResolver.pickPreferredResource(list);
                if (class_32982 == null) {
                    map.put(class_29602, null);
                    return null;
                }
                try (InputStreamReader inputStreamReader = new InputStreamReader(class_32982.method_14482());){
                    JsonObject jsonObject2 = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
                    map.put(class_29602, jsonObject2);
                    jsonObject = jsonObject2;
                }
                return jsonObject;
            }
            map.put(class_29602, null);
            return null;
        }
        catch (Exception exception) {
            map.put(class_29602, null);
            return null;
        }
    }

    private static class_3298 pickPreferredResource(List<class_3298> list) {
        if (list != null && !list.isEmpty()) {
            class_3298 class_32982 = null;
            for (int i = list.size() - 1; i >= 0; --i) {
                String string;
                class_3298 class_32983 = list.get(i);
                if (class_32983 == null) continue;
                if (class_32982 == null) {
                    class_32982 = class_32983;
                }
                if (ResourcePackItemTransformResolver.isFaPlayerPack(string = class_32983.method_14480())) continue;
                return class_32983;
            }
            return class_32982;
        }
        return null;
    }

    private static boolean isFaPlayerPack(String string) {
        return string != null && string.toLowerCase(Locale.ROOT).contains("faplayer");
    }

    private static Vector3f readVec3(JsonObject jsonObject, String string, Vector3f vector3f) {
        if (jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string).isJsonArray()) {
            JsonArray jsonArray = jsonObject.getAsJsonArray(string);
            return jsonArray.size() < 3 ? vector3f : new Vector3f(jsonArray.get(0).getAsFloat(), jsonArray.get(1).getAsFloat(), jsonArray.get(2).getAsFloat());
        }
        return vector3f;
    }

    private static String getString(JsonObject jsonObject, String string) {
        return jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string).isJsonPrimitive() ? jsonObject.get(string).getAsString() : null;
    }

    static {
        ITEM_DEFINITIONS = new HashMap<class_2960, JsonObject>();
        MODEL_DEFINITIONS = new HashMap<class_2960, JsonObject>();
    }
}
