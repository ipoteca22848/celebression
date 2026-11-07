package ru.method.celebression.punchy.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_5603;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_630;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import ru.method.celebression.punchy.mixin.client.accessor.ModelPartAccessor;

public class SimpleBedrockGeoThirdPerson {
    public static final Map<String, class_630> loadedParts = new HashMap<>();
    public static final Map<String, Vector3f> bedrockLocalPivots = new HashMap<>();
    public static final Map<String, String> parentByName = new HashMap<>();
    public static final Map<String, Matrix4f> restWorldByName = new HashMap<>();

    public static void load(class_2960 modelId) {
        clearLoadedData();
        if (modelId == null) {
            return;
        }

        try {
            class_3298 resource = class_310.method_1551().method_1478().getResourceOrThrow(modelId);
            JsonObject rootJson = JsonParser.parseReader((Reader)new InputStreamReader(resource.method_14482())).getAsJsonObject();
            JsonArray geometries = rootJson.getAsJsonArray("minecraft:geometry");
            if (geometries == null || geometries.isEmpty() || !geometries.get(0).isJsonObject()) {
                return;
            }

            JsonObject geometry = geometries.get(0).getAsJsonObject();
            JsonArray bones = geometry.getAsJsonArray("bones");
            if (bones == null) {
                return;
            }

            JsonObject description = geometry.has("description") && geometry.get("description").isJsonObject()
                    ? geometry.getAsJsonObject("description")
                    : geometry;
            int textureWidth = description.has("texture_width") ? description.get("texture_width").getAsInt() : 64;
            int textureHeight = description.has("texture_height") ? description.get("texture_height").getAsInt() : 64;

            class_5609 modelData = new class_5609();
            class_5610 rootPartData = modelData.method_32111();
            Map<String, class_5610> partDataByName = new HashMap<>();
            Map<String, float[]> pivots = readBoneMetadata(bones);

            for (JsonElement boneElement : bones) {
                if (boneElement == null || !boneElement.isJsonObject()) {
                    continue;
                }
                JsonObject bone = boneElement.getAsJsonObject();
                String name = bone.has("name") ? bone.get("name").getAsString() : null;
                if (name == null || name.isBlank()) {
                    continue;
                }

                String parentName = parentByName.get(name);
                float[] pivot = pivots.getOrDefault(name, new float[3]);
                float pivotX = pivot[0];
                float pivotY = pivot[1];
                float pivotZ = pivot[2];
                JsonArray rotation = bone.getAsJsonArray("rotation");
                float rotationX = getFloat(rotation, 0);
                float rotationY = getFloat(rotation, 1);
                float rotationZ = getFloat(rotation, 2);

                float relativePivotX = pivotX;
                float relativePivotY = pivotY;
                float relativePivotZ = pivotZ;
                float[] parentPivot = parentName == null ? null : pivots.get(parentName);
                if (parentPivot != null) {
                    relativePivotX -= parentPivot[0];
                    relativePivotY -= parentPivot[1];
                    relativePivotZ -= parentPivot[2];
                }
                bedrockLocalPivots.put(name, new Vector3f(relativePivotX, relativePivotY, relativePivotZ));

                class_5606 cubeBuilder = class_5606.method_32108();
                addCubes(cubeBuilder, bone.getAsJsonArray("cubes"), pivotX, pivotY, pivotZ);

                class_5610 parentData = parentName == null ? rootPartData : partDataByName.get(parentName);
                if (parentData == null) {
                    parentData = rootPartData;
                }
                float modelX = relativePivotX;
                float modelY = parentPivot == null ? 28.0f - pivotY : -relativePivotY;
                float modelZ = -relativePivotZ;
                class_5603 transform = class_5603.method_32091(
                        modelX,
                        modelY,
                        modelZ,
                        (float)Math.toRadians(rotationX),
                        (float)Math.toRadians(rotationY),
                        (float)Math.toRadians(rotationZ));
                partDataByName.put(name, parentData.method_32117(name, cubeBuilder, transform));
            }

            class_630 rootPart = class_5607.method_32110(modelData, textureWidth, textureHeight).method_32109();
            collectParts(rootPart, loadedParts);
            for (String partName : loadedParts.keySet()) {
                Matrix4f restWorld = buildRestWorldMatrix(partName, new HashSet<>());
                if (restWorld != null) {
                    restWorldByName.put(partName, restWorld);
                }
            }
        }
        catch (Exception exception) {
            clearLoadedData();
        }
    }

    private static Map<String, float[]> readBoneMetadata(JsonArray bones) {
        Map<String, float[]> pivots = new HashMap<>();
        for (JsonElement boneElement : bones) {
            if (boneElement == null || !boneElement.isJsonObject()) {
                continue;
            }
            JsonObject bone = boneElement.getAsJsonObject();
            String name = bone.has("name") ? bone.get("name").getAsString() : null;
            if (name == null || name.isBlank()) {
                continue;
            }
            JsonArray pivot = bone.getAsJsonArray("pivot");
            pivots.put(name, new float[]{getFloat(pivot, 0), getFloat(pivot, 1), getFloat(pivot, 2)});
            if (bone.has("parent")) {
                String parentName = bone.get("parent").getAsString();
                if (!parentName.isBlank()) {
                    parentByName.put(name, parentName);
                }
            }
        }
        return pivots;
    }

    private static void addCubes(class_5606 builder, JsonArray cubes, float pivotX, float pivotY, float pivotZ) {
        if (cubes == null) {
            return;
        }
        for (JsonElement cubeElement : cubes) {
            if (cubeElement == null || !cubeElement.isJsonObject()) {
                continue;
            }
            JsonObject cube = cubeElement.getAsJsonObject();
            JsonArray origin = cube.getAsJsonArray("origin");
            JsonArray size = cube.getAsJsonArray("size");
            JsonElement uvElement = cube.get("uv");
            if (origin == null || size == null || uvElement == null || !uvElement.isJsonArray()) {
                continue;
            }
            JsonArray uv = uvElement.getAsJsonArray();
            if (uv.size() < 2) {
                continue;
            }
            float originX = getFloat(origin, 0);
            float originY = getFloat(origin, 1);
            float originZ = getFloat(origin, 2);
            float sizeX = getFloat(size, 0);
            float sizeY = getFloat(size, 1);
            float sizeZ = getFloat(size, 2);
            builder.method_32101(uv.get(0).getAsInt(), uv.get(1).getAsInt()).method_32097(
                    originX - pivotX,
                    pivotY - originY - sizeY,
                    originZ - pivotZ,
                    sizeX,
                    sizeY,
                    sizeZ);
        }
    }

    private static float getFloat(JsonArray values, int index) {
        return values != null && index >= 0 && index < values.size() ? values.get(index).getAsFloat() : 0.0f;
    }

    private static void collectParts(class_630 part, Map<String, class_630> destination) {
        if (part == null) {
            return;
        }
        Map<String, class_630> children = ((ModelPartAccessor)(Object)part).punchy$getChildren();
        if (children == null) {
            return;
        }
        for (Map.Entry<String, class_630> entry : children.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            destination.put(entry.getKey(), entry.getValue());
            collectParts(entry.getValue(), destination);
        }
    }

    private static Matrix4f buildRestWorldMatrix(String partName, Set<String> visiting) {
        if (partName == null || !visiting.add(partName)) {
            return null;
        }
        class_630 part = loadedParts.get(partName);
        if (part == null) {
            return null;
        }
        Matrix4f local = new Matrix4f()
                .translate(part.field_3657, part.field_3656, part.field_3655)
                .rotateZ(part.field_3674)
                .rotateY(part.field_3675)
                .rotateX(part.field_3654)
                .scale(part.field_37938, part.field_37939, part.field_37940);
        String parentName = parentByName.get(partName);
        if (parentName == null) {
            return local;
        }
        Matrix4f parentWorld = buildRestWorldMatrix(parentName, visiting);
        return parentWorld == null ? local : parentWorld.mul((Matrix4fc)local, new Matrix4f());
    }

    private static void clearLoadedData() {
        loadedParts.clear();
        bedrockLocalPivots.clear();
        parentByName.clear();
        restWorldByName.clear();
    }
}
