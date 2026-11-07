package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_2960;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.AnimationLibrary;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.BedrockCubeDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.BedrockGeometryBaker;
import ru.method.celebression.modules.impl.render.cosmetic.BedrockModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelAnchor;
import ru.method.celebression.render.UvRegion;

public final class CosmeticModelParser {
    private CosmeticModelParser() {
    }

    public static CosmeticModel a(JsonObject jsonObject) {
        JsonArray jsonArray;
        if (jsonObject == null) {
            return CosmeticModelParser.bt();
        }
        class_2960 class_29602 = CosmeticModelParser.b(jsonObject, "texture");
        CosmeticModelAnchor cosmeticModelAnchor = CosmeticModelParser.d(jsonObject);
        float f = CosmeticModelParser.a(jsonObject, "offsetX", "x", 0.0f);
        float f2 = CosmeticModelParser.a(jsonObject, "offsetY", "y", 0.0f);
        float f3 = CosmeticModelParser.a(jsonObject, "offsetZ", "z", 0.0f);
        float f4 = CosmeticModelParser.a(jsonObject, "scale", 1.0f);
        float f5 = CosmeticModelParser.a(jsonObject, "yaw", 0.0f);
        float f6 = CosmeticModelParser.a(jsonObject, "pitch", 0.0f);
        float f7 = CosmeticModelParser.a(jsonObject, "roll", 0.0f);
        float f8 = CosmeticModelParser.a(jsonObject, "previewY", 0.5f);
        float f9 = CosmeticModelParser.a(jsonObject, "previewScale", 1.0f);
        AnimationLibrary animationLibrary = jsonObject.has("animation") && jsonObject.get("animation").isJsonObject() ? AnimationLibrary.f(jsonObject.getAsJsonObject("animation")) : AnimationLibrary.bx();
        JsonObject jsonObject2 = jsonObject.has("geometry") && jsonObject.get("geometry").isJsonObject() ? jsonObject.getAsJsonObject("geometry") : jsonObject;
        JsonArray jsonArray2 = jsonArray = jsonObject2.has("minecraft:geometry") ? jsonObject2.getAsJsonArray("minecraft:geometry") : null;
        if (jsonArray == null || jsonArray.isEmpty()) {
            return new CosmeticModel(class_29602, cosmeticModelAnchor, f, f2, f3, f4, f5, f6, f7, f8, f9, 64, 64, List.of(), List.of(), animationLibrary);
        }
        JsonObject jsonObject3 = jsonArray.get(0).getAsJsonObject();
        JsonObject jsonObject4 = jsonObject3.has("description") ? jsonObject3.getAsJsonObject("description") : new JsonObject();
        int n = CosmeticModelParser.a(jsonObject4, "texture_width", 64);
        int n2 = CosmeticModelParser.a(jsonObject4, "texture_height", 64);
        List<BedrockModelBone> list = CosmeticModelParser.b(jsonObject3);
        List<BakedModelBone> list2 = BedrockGeometryBaker.a(jsonObject3, n, n2);
        return new CosmeticModel(class_29602, cosmeticModelAnchor, f, f2, f3, f4, f5, f6, f7, f8, f9, n, n2, list, list2, animationLibrary);
    }

    private static float a(JsonObject jsonObject, String string, String string2, float f) {
        if (jsonObject.has(string)) {
            return CosmeticModelParser.a(jsonObject, string, f);
        }
        if (jsonObject.has(string2)) {
            return CosmeticModelParser.a(jsonObject, string2, f);
        }
        return f;
    }

    private static List<BedrockModelBone> b(JsonObject jsonObject) {
        if (!jsonObject.has("bones") || !jsonObject.get("bones").isJsonArray()) {
            return List.of();
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray("bones");
        Map<String, BedrockModelBone> bonesByName = new HashMap<>();
        Map<String, String> parentByName = new HashMap<>();
        List<String> boneOrder = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject()) continue;
            JsonObject boneJson = element.getAsJsonObject();
            String name = boneJson.has("name") ? boneJson.get("name").getAsString() : "";
            if (name.isBlank()) continue;
            float pivotX = CosmeticModelParser.a(boneJson, "pivot", 0, 0.0f);
            float pivotY = CosmeticModelParser.a(boneJson, "pivot", 1, 0.0f);
            float pivotZ = CosmeticModelParser.a(boneJson, "pivot", 2, 0.0f);
            float rotationX = CosmeticModelParser.a(boneJson, "rotation", 0, 0.0f);
            float rotationY = CosmeticModelParser.a(boneJson, "rotation", 1, 0.0f);
            float rotationZ = CosmeticModelParser.a(boneJson, "rotation", 2, 0.0f);
            List<BedrockCubeDefinition> cubes = CosmeticModelParser.c(boneJson);
            bonesByName.put(name, new BedrockModelBone(name, pivotX, pivotY, pivotZ, rotationX, rotationY, rotationZ, cubes));
            boneOrder.add(name);
            if (boneJson.has("parent")) {
                parentByName.put(name, boneJson.get("parent").getAsString());
            }
        }
        List<BedrockModelBone> roots = new ArrayList<>();
        for (String name : boneOrder) {
            BedrockModelBone bone = bonesByName.get(name);
            if (bone == null) continue;
            String parentName = parentByName.get(name);
            if (parentName == null || parentName.isBlank()) {
                roots.add(bone);
                continue;
            }
            BedrockModelBone parent = bonesByName.get(parentName);
            if (parent != null) {
                parent.a(bone);
            } else {
                roots.add(bone);
            }
        }
        return roots;
    }

    private static List<BedrockCubeDefinition> c(JsonObject jsonObject) {
        if (!jsonObject.has("cubes") || !jsonObject.get("cubes").isJsonArray()) {
            return List.of();
        }
        ArrayList<BedrockCubeDefinition> arrayList = new ArrayList<BedrockCubeDefinition>();
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("cubes")) {
            UvRegion uvRegion;
            UvRegion uvRegion2;
            UvRegion uvRegion3;
            UvRegion uvRegion4;
            UvRegion uvRegion5;
            UvRegion uvRegion6;
            JsonObject jsonObject2;
            if (!jsonElement.isJsonObject()) continue;
            JsonObject jsonObject3 = jsonElement.getAsJsonObject();
            float f = CosmeticModelParser.a(jsonObject3, "origin", 0, 0.0f);
            float f2 = CosmeticModelParser.a(jsonObject3, "origin", 1, 0.0f);
            float f3 = CosmeticModelParser.a(jsonObject3, "origin", 2, 0.0f);
            float f4 = CosmeticModelParser.a(jsonObject3, "size", 0, 0.0f);
            float f5 = CosmeticModelParser.a(jsonObject3, "size", 1, 0.0f);
            float f6 = CosmeticModelParser.a(jsonObject3, "size", 2, 0.0f);
            float f7 = CosmeticModelParser.a(jsonObject3, "pivot", 0, f + f4 * 0.5f);
            float f8 = CosmeticModelParser.a(jsonObject3, "pivot", 1, f2 + f5 * 0.5f);
            float f9 = CosmeticModelParser.a(jsonObject3, "pivot", 2, f3 + f6 * 0.5f);
            float f10 = CosmeticModelParser.a(jsonObject3, "rotation", 0, 0.0f);
            float f11 = CosmeticModelParser.a(jsonObject3, "rotation", 1, 0.0f);
            float f12 = CosmeticModelParser.a(jsonObject3, "rotation", 2, 0.0f);
            boolean bl = jsonObject3.has("mirror") && jsonObject3.get("mirror").getAsBoolean();
            float f13 = CosmeticModelParser.a(jsonObject3, "inflate", 0.0f);
            if (f13 != 0.0f) {
                f -= f13;
                f2 -= f13;
                f3 -= f13;
                f4 += f13 * 2.0f;
                f5 += f13 * 2.0f;
                f6 += f13 * 2.0f;
            }
            JsonObject jsonObject4 = jsonObject2 = jsonObject3.has("uv") && jsonObject3.get("uv").isJsonObject() ? jsonObject3.getAsJsonObject("uv") : null;
            if (jsonObject2 != null) {
                uvRegion6 = CosmeticModelParser.a(jsonObject2, "north");
                uvRegion5 = CosmeticModelParser.a(jsonObject2, "east");
                uvRegion4 = CosmeticModelParser.a(jsonObject2, "south");
                uvRegion3 = CosmeticModelParser.a(jsonObject2, "west");
                uvRegion2 = CosmeticModelParser.a(jsonObject2, "up");
                uvRegion = CosmeticModelParser.a(jsonObject2, "down");
            } else if (jsonObject3.has("uv") && jsonObject3.get("uv").isJsonArray()) {
                float f14 = CosmeticModelParser.a(jsonObject3, "uv", 0, 0.0f);
                float f15 = CosmeticModelParser.a(jsonObject3, "uv", 1, 0.0f);
                uvRegion6 = CosmeticModelParser.a(f14 + f6, f15 + f6, f4, f5);
                uvRegion5 = CosmeticModelParser.a(f14 + f6 + f4, f15 + f6, f6, f5);
                uvRegion4 = CosmeticModelParser.a(f14 + f6 + f4 + f6, f15 + f6, f4, f5);
                uvRegion3 = CosmeticModelParser.a(f14, f15 + f6, f6, f5);
                uvRegion2 = CosmeticModelParser.a(f14 + f6, f15, f4, f6);
                uvRegion = CosmeticModelParser.a(f14 + f6 + f4, f15, f4, f6);
            } else {
                uvRegion6 = UvRegion.L;
                uvRegion5 = UvRegion.L;
                uvRegion4 = UvRegion.L;
                uvRegion3 = UvRegion.L;
                uvRegion2 = UvRegion.L;
                uvRegion = UvRegion.L;
            }
            arrayList.add(new BedrockCubeDefinition(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, bl, uvRegion6, uvRegion5, uvRegion4, uvRegion3, uvRegion2, uvRegion));
        }
        return arrayList;
    }

    private static UvRegion a(float f, float f2, float f3, float f4) {
        if (f3 == 0.0f || f4 == 0.0f) {
            return UvRegion.L;
        }
        return new UvRegion(f, f2, f3, f4);
    }

    private static UvRegion a(JsonObject jsonObject, String string) {
        if (!jsonObject.has(string) || !jsonObject.get(string).isJsonObject()) {
            return UvRegion.L;
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
        float f = CosmeticModelParser.a(jsonObject2, "uv", 0, 0.0f);
        float f2 = CosmeticModelParser.a(jsonObject2, "uv", 1, 0.0f);
        float f3 = CosmeticModelParser.a(jsonObject2, "uv_size", 0, 0.0f);
        float f4 = CosmeticModelParser.a(jsonObject2, "uv_size", 1, 0.0f);
        return new UvRegion(f, f2, f3, f4);
    }

    private static CosmeticModelAnchor d(JsonObject jsonObject) {
        if (jsonObject.has("anchor")) {
            String string = jsonObject.get("anchor").getAsString();
            if (string != null && "body".equalsIgnoreCase(string.trim())) {
                return CosmeticModelAnchor.cZ;
            }
            if (string != null && "head".equalsIgnoreCase(string.trim())) {
                return CosmeticModelAnchor.cY;
            }
        }
        if (jsonObject.has("category")) {
            return jsonObject.get("category").getAsInt() == 5 ? CosmeticModelAnchor.cZ : CosmeticModelAnchor.cY;
        }
        return CosmeticModelAnchor.cY;
    }

    private static class_2960 b(JsonObject jsonObject, String string) {
        if (!jsonObject.has(string)) {
            return null;
        }
        String string2 = jsonObject.get(string).getAsString();
        class_2960 class_29602 = class_2960.method_12829((String)string2);
        if (class_29602 != null) {
            return CosmeticModelParser.a(class_29602);
        }
        return CosmeticModelParser.a(Celebression.id(string2));
    }

    private static class_2960 a(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        String string = class_29602.method_12832();
        if (string.startsWith("textures/")) {
            return class_2960.method_60655((String)class_29602.method_12836(), (String)string.substring("textures/".length()));
        }
        return class_29602;
    }

    private static float a(JsonObject jsonObject, String string, float f) {
        if (jsonObject == null || !jsonObject.has(string)) {
            return f;
        }
        try {
            return jsonObject.get(string).getAsFloat();
        }
        catch (Exception exception) {
            return f;
        }
    }

    private static float a(JsonObject jsonObject, String string, int n, float f) {
        if (jsonObject == null || !jsonObject.has(string) || !jsonObject.get(string).isJsonArray()) {
            return f;
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray(string);
        if (n < 0 || n >= jsonArray.size()) {
            return f;
        }
        try {
            return jsonArray.get(n).getAsFloat();
        }
        catch (Exception exception) {
            return f;
        }
    }

    private static int a(JsonObject jsonObject, String string, int n) {
        if (jsonObject == null || !jsonObject.has(string)) {
            return n;
        }
        try {
            return jsonObject.get(string).getAsInt();
        }
        catch (Exception exception) {
            return n;
        }
    }

    private static CosmeticModel bt() {
        return new CosmeticModel(null, CosmeticModelAnchor.cY, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 64, 64, List.of(), List.of(), AnimationLibrary.bx());
    }
}
