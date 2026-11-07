package ru.method.celebression.punchy.client.bedrockitems;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.class_2350;
import net.minecraft.class_2960;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_5603;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_630;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemGeoModel;
import ru.method.celebression.punchy.mixin.client.accessor.ModelPartAccessor;

final class BedrockItemGeoLoader {
    private BedrockItemGeoLoader() {
    }

    static BedrockItemGeoModel load(class_3300 class_33002, class_2960 class_29602) {
        if (class_33002 == null || class_29602 == null) {
            return null;
        }
        try {
            class_3298 resource = class_33002.getResourceOrThrow(class_29602);
            JsonObject rootJson = JsonParser.parseReader((Reader)new InputStreamReader(resource.method_14482())).getAsJsonObject();
            JsonArray geometries = rootJson.getAsJsonArray("minecraft:geometry");
            if (geometries == null || geometries.isEmpty() || !geometries.get(0).isJsonObject()) {
                return null;
            }
            JsonObject geometry = geometries.get(0).getAsJsonObject();
            JsonArray bones = geometry.getAsJsonArray("bones");
            if (bones == null) {
                return null;
            }

            JsonObject description = geometry.has("description") && geometry.get("description").isJsonObject() ? geometry.getAsJsonObject("description") : geometry;
            int textureWidth = description.has("texture_width") ? description.get("texture_width").getAsInt() : 64;
            int textureHeight = description.has("texture_height") ? description.get("texture_height").getAsInt() : 64;

            class_5609 modelData = new class_5609();
            class_5610 rootPartData = modelData.method_32111();
            HashMap<String, class_5610> partDataByName = new HashMap<>();
            HashMap<String, float[]> pivots = new HashMap<>();
            HashMap<String, String> parents = new HashMap<>();
            HashMap<String, List<BedrockItemGeoModel.BedrockCube>> customCubes = new HashMap<>();

            for (JsonElement boneElement : bones) {
                if (boneElement == null || !boneElement.isJsonObject()) continue;
                JsonObject bone = boneElement.getAsJsonObject();
                String name = bone.has("name") ? bone.get("name").getAsString() : null;
                if (name == null || name.isBlank()) continue;
                JsonArray pivot = bone.getAsJsonArray("pivot");
                pivots.put(name, new float[]{getFloat(pivot, 0), getFloat(pivot, 1), getFloat(pivot, 2)});
            }

            for (JsonElement boneElement : bones) {
                if (boneElement == null || !boneElement.isJsonObject()) continue;
                JsonObject bone = boneElement.getAsJsonObject();
                String name = bone.has("name") ? bone.get("name").getAsString() : null;
                if (name == null || name.isBlank()) continue;
                String parentName = bone.has("parent") ? bone.get("parent").getAsString() : null;
                if (parentName != null && !parentName.isBlank()) {
                    parents.put(name, parentName);
                }

                JsonArray pivot = bone.getAsJsonArray("pivot");
                float pivotX = getFloat(pivot, 0);
                float pivotY = getFloat(pivot, 1);
                float pivotZ = getFloat(pivot, 2);
                JsonArray rotation = bone.getAsJsonArray("rotation");
                float rotX = -getFloat(rotation, 0);
                float rotY = -getFloat(rotation, 1);
                float rotZ = getFloat(rotation, 2);
                class_5606 builder = class_5606.method_32108();

                JsonArray cubes = bone.getAsJsonArray("cubes");
                if (cubes != null) {
                    for (JsonElement cubeElement : cubes) {
                        if (cubeElement == null || !cubeElement.isJsonObject()) continue;
                        JsonObject cube = cubeElement.getAsJsonObject();
                        JsonArray origin = cube.getAsJsonArray("origin");
                        JsonArray size = cube.getAsJsonArray("size");
                        if (origin == null || size == null) continue;

                        float originX = getFloat(origin, 0);
                        float originY = getFloat(origin, 1);
                        float originZ = getFloat(origin, 2);
                        float sizeX = getFloat(size, 0);
                        float sizeY = getFloat(size, 1);
                        float sizeZ = getFloat(size, 2);
                        float inflate = cube.has("inflate") ? Math.max(0.0f, cube.get("inflate").getAsFloat()) : 0.0f;
                        boolean mirror = cube.has("mirror") && cube.get("mirror").getAsBoolean();

                        JsonArray cubePivot = cube.getAsJsonArray("pivot");
                        float cubePivotX = cubePivot != null && cubePivot.size() >= 3 ? getFloat(cubePivot, 0) : originX;
                        float cubePivotY = cubePivot != null && cubePivot.size() >= 3 ? getFloat(cubePivot, 1) : originY;
                        float cubePivotZ = cubePivot != null && cubePivot.size() >= 3 ? getFloat(cubePivot, 2) : originZ;
                        JsonArray cubeRotation = cube.getAsJsonArray("rotation");
                        float cubeRotX = cubeRotation != null && cubeRotation.size() >= 3 ? -getFloat(cubeRotation, 0) : 0.0f;
                        float cubeRotY = cubeRotation != null && cubeRotation.size() >= 3 ? -getFloat(cubeRotation, 1) : 0.0f;
                        float cubeRotZ = cubeRotation != null && cubeRotation.size() >= 3 ? getFloat(cubeRotation, 2) : 0.0f;

                        if (inflate > 0.0f) {
                            originX -= inflate;
                            originY -= inflate;
                            originZ -= inflate;
                            sizeX += inflate * 2.0f;
                            sizeY += inflate * 2.0f;
                            sizeZ += inflate * 2.0f;
                        }

                        float localX = originX - pivotX;
                        float localY = originY - pivotY;
                        float localZ = originZ - pivotZ;
                        float minX = toModelX(localX + sizeX);
                        float minY = toModelY(localY);
                        float minZ = toModelZ(localZ);
                        float maxX = minX + sizeX;
                        float maxY = minY + sizeY;
                        float maxZ = minZ + sizeZ;
                        float modelPivotX = toModelX(cubePivotX - pivotX);
                        float modelPivotY = toModelY(cubePivotY - pivotY);
                        float modelPivotZ = toModelZ(cubePivotZ - pivotZ);

                        JsonElement uvElement = cube.get("uv");
                        if (uvElement != null && uvElement.isJsonObject()) {
                            Map<class_2350, BedrockItemGeoModel.BedrockFace> faces = parseFaces(uvElement.getAsJsonObject());
                            if (!faces.isEmpty()) {
                                customCubes.computeIfAbsent(name, key -> new ArrayList<>()).add(new BedrockItemGeoModel.BedrockCube(minX, minY, minZ, maxX, maxY, maxZ, faces, mirror, modelPivotX, modelPivotY, modelPivotZ, (float)Math.toRadians(cubeRotX), (float)Math.toRadians(cubeRotY), (float)Math.toRadians(cubeRotZ)));
                            }
                        } else if (uvElement != null && uvElement.isJsonArray() && uvElement.getAsJsonArray().size() >= 2) {
                            JsonArray uv = uvElement.getAsJsonArray();
                            builder.method_32101(uv.get(0).getAsInt(), uv.get(1).getAsInt()).method_32097(minX, minY, minZ, sizeX, sizeY, sizeZ);
                        }
                    }
                }

                class_5610 parentData = parentName != null ? partDataByName.get(parentName) : rootPartData;
                if (parentData == null) {
                    parentData = rootPartData;
                }
                float relativeX = pivotX;
                float relativeY = pivotY;
                float relativeZ = pivotZ;
                float[] parentPivot = parentName != null ? pivots.get(parentName) : null;
                if (parentPivot != null) {
                    relativeX -= parentPivot[0];
                    relativeY -= parentPivot[1];
                    relativeZ -= parentPivot[2];
                }
                class_5603 transform = class_5603.method_32091(toModelX(relativeX), toModelY(relativeY), toModelZ(relativeZ), (float)Math.toRadians(rotX), (float)Math.toRadians(rotY), (float)Math.toRadians(rotZ));
                partDataByName.put(name, parentData.method_32117(name, builder, transform));
            }

            class_630 rootPart = class_5607.method_32110(modelData, textureWidth, textureHeight).method_32109();
            HashMap<String, class_630> parts = new HashMap<>();
            BedrockItemGeoLoader.collectParts(rootPart, parts);
            return new BedrockItemGeoModel(rootPart, parts, textureWidth, textureHeight, parents, customCubes);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static float getFloat(JsonArray jsonArray, int n) {
        return jsonArray != null && n >= 0 && n < jsonArray.size() ? jsonArray.get(n).getAsFloat() : 0.0f;
    }

    private static float toModelX(float f) {
        return -f;
    }

    private static float toModelY(float f) {
        return f;
    }

    private static float toModelZ(float f) {
        return f;
    }

    private static void collectParts(class_630 class_6302, Map<String, class_630> map) {
        if (class_6302 != null) {
            ModelPartAccessor modelPartAccessor = (ModelPartAccessor)(Object)class_6302;
            Map<String, class_630> children = modelPartAccessor.punchy$getChildren();
            if (children == null) return;
            for (Map.Entry<String, class_630> entry : children.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) continue;
                map.put(entry.getKey(), entry.getValue());
                BedrockItemGeoLoader.collectParts(entry.getValue(), map);
            }
        }
    }

    private static Map<class_2350, BedrockItemGeoModel.BedrockFace> parseFaces(JsonObject jsonObject) {
        HashMap<class_2350, BedrockItemGeoModel.BedrockFace> hashMap = new HashMap<class_2350, BedrockItemGeoModel.BedrockFace>();
        if (jsonObject == null) {
            return hashMap;
        }
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonObject jsonObject2;
            class_2350 class_23502 = BedrockItemGeoLoader.parseDirection(entry.getKey());
            if (class_23502 == null || !entry.getValue().isJsonObject() || !(jsonObject2 = entry.getValue().getAsJsonObject()).has("uv") || !jsonObject2.has("uv_size")) continue;
            JsonArray jsonArray = jsonObject2.getAsJsonArray("uv");
            JsonArray jsonArray2 = jsonObject2.getAsJsonArray("uv_size");
            if (jsonArray == null || jsonArray2 == null || jsonArray.size() < 2 || jsonArray2.size() < 2) continue;
            float f = jsonArray.get(0).getAsFloat();
            float f2 = jsonArray.get(1).getAsFloat();
            float f3 = jsonArray2.get(0).getAsFloat();
            float f4 = jsonArray2.get(1).getAsFloat();
            int n = 0;
            if (jsonObject2.has("uv_rotation")) {
                n = jsonObject2.get("uv_rotation").getAsInt();
            } else if (jsonObject2.has("rotation")) {
                n = jsonObject2.get("rotation").getAsInt();
            }
            hashMap.put(class_23502, new BedrockItemGeoModel.BedrockFace(f, f2, f3, f4, n));
        }
        return hashMap;
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
}
