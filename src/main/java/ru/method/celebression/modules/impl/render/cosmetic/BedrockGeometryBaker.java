package ru.method.celebression.modules.impl.render.cosmetic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelCube;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelQuad;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelVertex;

public final class BedrockGeometryBaker {
    private BedrockGeometryBaker() {
    }

    public static List<BakedModelBone> a(JsonObject jsonObject, int n, int n2) {
        if (jsonObject == null || !jsonObject.has("bones") || !jsonObject.get("bones").isJsonArray()) {
            return List.of();
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray("bones");
        Map<String, BoneGeometry> geometryByName = new HashMap<>();
        Map<String, String> parentByName = new HashMap<>();
        List<String> boneOrder = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject()) continue;
            JsonObject boneJson = element.getAsJsonObject();
            String name = boneJson.has("name") ? boneJson.get("name").getAsString() : "";
            if (name.isBlank()) continue;
            float pivotX = BedrockGeometryBaker.a(boneJson, "pivot", 0, 0.0f);
            float pivotY = BedrockGeometryBaker.a(boneJson, "pivot", 1, 0.0f);
            float pivotZ = BedrockGeometryBaker.a(boneJson, "pivot", 2, 0.0f);
            float rotationX = BedrockGeometryBaker.a(boneJson, "rotation", 0, 0.0f);
            float rotationY = BedrockGeometryBaker.a(boneJson, "rotation", 1, 0.0f);
            float rotationZ = BedrockGeometryBaker.a(boneJson, "rotation", 2, 0.0f);
            Float inflate = boneJson.has("inflate") ? boneJson.get("inflate").getAsFloat() : null;
            Boolean mirror = boneJson.has("mirror") && boneJson.get("mirror").getAsBoolean() ? Boolean.TRUE : null;
            List<BakedModelCube> cubes = BedrockGeometryBaker.a(boneJson, n, n2, inflate, mirror);
            geometryByName.put(name, new BoneGeometry(name, pivotX, pivotY, pivotZ, rotationX, rotationY, rotationZ, cubes));
            boneOrder.add(name);
            if (boneJson.has("parent")) {
                parentByName.put(name, boneJson.get("parent").getAsString());
            }
        }
        Map<String, List<String>> childrenByParent = new HashMap<>();
        List<String> roots = new ArrayList<>();
        for (String name : boneOrder) {
            String parent = parentByName.get(name);
            if (parent == null || parent.isBlank() || !geometryByName.containsKey(parent)) {
                roots.add(name);
            } else {
                childrenByParent.computeIfAbsent(parent, ignored -> new ArrayList<>()).add(name);
            }
        }
        List<BakedModelBone> bakedRoots = new ArrayList<>();
        for (String root : roots) {
            BoneGeometry boneGeometry = geometryByName.get(root);
            if (boneGeometry == null) continue;
            bakedRoots.add(BedrockGeometryBaker.a(boneGeometry, childrenByParent, geometryByName));
        }
        return bakedRoots;
    }

    private static BakedModelBone a(BoneGeometry boneGeometry, Map<String, List<String>> map, Map<String, BoneGeometry> map2) {
        List<String> list = map.getOrDefault(boneGeometry.bQ, List.of());
        ArrayList<BakedModelBone> arrayList = new ArrayList<BakedModelBone>();
        for (String string : list) {
            BoneGeometry boneGeometry2 = map2.get(string);
            if (boneGeometry2 == null) continue;
            arrayList.add(BedrockGeometryBaker.a(boneGeometry2, map, map2));
        }
        float f = (float)Math.toRadians(-boneGeometry.bU);
        float f2 = (float)Math.toRadians(-boneGeometry.bV);
        float f3 = (float)Math.toRadians(boneGeometry.bW);
        return new BakedModelBone(boneGeometry.bQ, -boneGeometry.bR, boneGeometry.bS, boneGeometry.bT, f, f2, f3, boneGeometry.bX, arrayList);
    }

    private static List<BakedModelCube> a(JsonObject jsonObject, int n, int n2, Float f, Boolean bl) {
        if (!jsonObject.has("cubes") || !jsonObject.get("cubes").isJsonArray()) {
            return List.of();
        }
        ArrayList<BakedModelCube> arrayList = new ArrayList<BakedModelCube>();
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("cubes")) {
            BakedModelCube bakedModelCube;
            if (!jsonElement.isJsonObject() || (bakedModelCube = BedrockGeometryBaker.b(jsonElement.getAsJsonObject(), n, n2, f, bl)) == null) continue;
            arrayList.add(bakedModelCube);
        }
        return arrayList;
    }

    private static BakedModelCube b(JsonObject jsonObject, int n, int n2, Float f, Boolean bl) {
        boolean bl2;
        float f2 = BedrockGeometryBaker.a(jsonObject, "size", 0, 0.0f);
        float f3 = BedrockGeometryBaker.a(jsonObject, "size", 1, 0.0f);
        float f4 = BedrockGeometryBaker.a(jsonObject, "size", 2, 0.0f);
        float f5 = BedrockGeometryBaker.a(jsonObject, "origin", 0, 0.0f);
        float f6 = BedrockGeometryBaker.a(jsonObject, "origin", 1, 0.0f);
        float f7 = BedrockGeometryBaker.a(jsonObject, "origin", 2, 0.0f);
        double d = jsonObject.has("inflate") ? jsonObject.get("inflate").getAsDouble() / 16.0 : (f == null ? 0.0 : (double)f.floatValue() / 16.0);
        Boolean bl3 = jsonObject.has("mirror") && jsonObject.get("mirror").getAsBoolean() ? Boolean.TRUE : bl;
        float f8 = Math.max(1, n);
        float f9 = Math.max(1, n2);
        double d2 = (double)(-(f5 + f2)) / 16.0;
        double d3 = (double)f6 / 16.0;
        double d4 = (double)f7 / 16.0;
        double d5 = (double)f2 * 0.0625;
        double d6 = (double)f3 * 0.0625;
        double d7 = (double)f4 * 0.0625;
        float f10 = (float)Math.toRadians(-BedrockGeometryBaker.a(jsonObject, "rotation", 0, 0.0f));
        float f11 = (float)Math.toRadians(-BedrockGeometryBaker.a(jsonObject, "rotation", 1, 0.0f));
        float f12 = (float)Math.toRadians(BedrockGeometryBaker.a(jsonObject, "rotation", 2, 0.0f));
        float f13 = BedrockGeometryBaker.a(jsonObject, "pivot", 0, f5 + f2 * 0.5f) * -1.0f;
        float f14 = BedrockGeometryBaker.a(jsonObject, "pivot", 1, f6 + f3 * 0.5f);
        float f15 = BedrockGeometryBaker.a(jsonObject, "pivot", 2, f7 + f4 * 0.5f);
        BakedModelVertex bakedModelVertex = BedrockGeometryBaker.a(d2 - d, d3 - d, d4 - d);
        BakedModelVertex bakedModelVertex2 = BedrockGeometryBaker.a(d2 - d, d3 - d, d4 + d7 + d);
        BakedModelVertex bakedModelVertex3 = BedrockGeometryBaker.a(d2 - d, d3 + d6 + d, d4 - d);
        BakedModelVertex bakedModelVertex4 = BedrockGeometryBaker.a(d2 - d, d3 + d6 + d, d4 + d7 + d);
        BakedModelVertex bakedModelVertex5 = BedrockGeometryBaker.a(d2 + d5 + d, d3 - d, d4 - d);
        BakedModelVertex bakedModelVertex6 = BedrockGeometryBaker.a(d2 + d5 + d, d3 - d, d4 + d7 + d);
        BakedModelVertex bakedModelVertex7 = BedrockGeometryBaker.a(d2 + d5 + d, d3 + d6 + d, d4 - d);
        BakedModelVertex bakedModelVertex8 = BedrockGeometryBaker.a(d2 + d5 + d, d3 + d6 + d, d4 + d7 + d);
        ArrayList<BakedModelQuad> arrayList = new ArrayList<BakedModelQuad>(6);
        boolean bl4 = bl2 = jsonObject.has("uv") && jsonObject.get("uv").isJsonArray();
        if (bl2) {
            float f16 = BedrockGeometryBaker.a(jsonObject, "uv", 0, 0.0f);
            float f17 = BedrockGeometryBaker.a(jsonObject, "uv", 1, 0.0f);
            double d8 = Math.floor(f2);
            double d9 = Math.floor(f3);
            double d10 = Math.floor(f4);
            boolean bl5 = Boolean.TRUE.equals(bl3);
            if (bl5) {
                BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex7, bakedModelVertex8, bakedModelVertex6, bakedModelVertex5, (double)f16 + d10 + d8, (double)f17 + d10, d10, d9, f8, f9, bl3, -1.0f, 0.0f, 0.0f));
                BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex4, bakedModelVertex3, bakedModelVertex, bakedModelVertex2, f16, (double)f17 + d10, d10, d9, f8, f9, bl3, 1.0f, 0.0f, 0.0f));
            } else {
                BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex4, bakedModelVertex3, bakedModelVertex, bakedModelVertex2, (double)f16 + d10 + d8, (double)f17 + d10, d10, d9, f8, f9, bl3, -1.0f, 0.0f, 0.0f));
                BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex7, bakedModelVertex8, bakedModelVertex6, bakedModelVertex5, f16, (double)f17 + d10, d10, d9, f8, f9, bl3, 1.0f, 0.0f, 0.0f));
            }
            BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex3, bakedModelVertex7, bakedModelVertex5, bakedModelVertex, (double)f16 + d10, (double)f17 + d10, d8, d9, f8, f9, bl3, 0.0f, 0.0f, -1.0f));
            BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex8, bakedModelVertex4, bakedModelVertex2, bakedModelVertex6, (double)f16 + d10 + d8 + d10, (double)f17 + d10, d8, d9, f8, f9, bl3, 0.0f, 0.0f, 1.0f));
            BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex4, bakedModelVertex8, bakedModelVertex7, bakedModelVertex3, (double)f16 + d10, f17, d8, d10, f8, f9, bl3, 0.0f, 1.0f, 0.0f));
            BedrockGeometryBaker.a(arrayList, BedrockGeometryBaker.a(bakedModelVertex2, bakedModelVertex6, bakedModelVertex5, bakedModelVertex, (double)f16 + d10 + d8, f17, d8, d10, f8, f9, bl3, 0.0f, -1.0f, 0.0f));
        } else if (jsonObject.has("uv") && jsonObject.get("uv").isJsonObject()) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("uv");
            boolean bl6 = Boolean.TRUE.equals(bl3);
            if (bl6) {
                BedrockGeometryBaker.a(arrayList, jsonObject2, "west", bakedModelVertex7, bakedModelVertex8, bakedModelVertex6, bakedModelVertex5, f8, f9, bl3, -1.0f, 0.0f, 0.0f);
                BedrockGeometryBaker.a(arrayList, jsonObject2, "east", bakedModelVertex4, bakedModelVertex3, bakedModelVertex, bakedModelVertex2, f8, f9, bl3, 1.0f, 0.0f, 0.0f);
            } else {
                BedrockGeometryBaker.a(arrayList, jsonObject2, "west", bakedModelVertex4, bakedModelVertex3, bakedModelVertex, bakedModelVertex2, f8, f9, bl3, -1.0f, 0.0f, 0.0f);
                BedrockGeometryBaker.a(arrayList, jsonObject2, "east", bakedModelVertex7, bakedModelVertex8, bakedModelVertex6, bakedModelVertex5, f8, f9, bl3, 1.0f, 0.0f, 0.0f);
            }
            BedrockGeometryBaker.a(arrayList, jsonObject2, "north", bakedModelVertex3, bakedModelVertex7, bakedModelVertex5, bakedModelVertex, f8, f9, bl3, 0.0f, 0.0f, -1.0f);
            BedrockGeometryBaker.a(arrayList, jsonObject2, "south", bakedModelVertex8, bakedModelVertex4, bakedModelVertex2, bakedModelVertex6, f8, f9, bl3, 0.0f, 0.0f, 1.0f);
            BedrockGeometryBaker.a(arrayList, jsonObject2, "up", bakedModelVertex4, bakedModelVertex8, bakedModelVertex7, bakedModelVertex3, f8, f9, bl3, 0.0f, 1.0f, 0.0f);
            BedrockGeometryBaker.a(arrayList, jsonObject2, "down", bakedModelVertex, bakedModelVertex5, bakedModelVertex6, bakedModelVertex2, f8, f9, bl3, 0.0f, -1.0f, 0.0f);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new BakedModelCube(f13, f14, f15, f10, f11, f12, (float)d5, (float)d6, (float)d7, arrayList);
    }

    private static void a(List<BakedModelQuad> list, JsonObject jsonObject, String string, BakedModelVertex bakedModelVertex, BakedModelVertex bakedModelVertex2, BakedModelVertex bakedModelVertex3, BakedModelVertex bakedModelVertex4, float f, float f2, Boolean bl, float f3, float f4, float f5) {
        if (!jsonObject.has(string) || !jsonObject.get(string).isJsonObject()) {
            return;
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
        float f6 = BedrockGeometryBaker.a(jsonObject2, "uv", 0, 0.0f);
        float f7 = BedrockGeometryBaker.a(jsonObject2, "uv", 1, 0.0f);
        float f8 = BedrockGeometryBaker.a(jsonObject2, "uv_size", 0, 0.0f);
        float f9 = BedrockGeometryBaker.a(jsonObject2, "uv_size", 1, 0.0f);
        if (f8 == 0.0f || f9 == 0.0f) {
            return;
        }
        BedrockGeometryBaker.a(list, BedrockGeometryBaker.a(bakedModelVertex, bakedModelVertex2, bakedModelVertex3, bakedModelVertex4, f6, f7, f8, f9, f, f2, bl, f3, f4, f5));
    }

    private static void a(List<BakedModelQuad> list, BakedModelQuad bakedModelQuad) {
        if (bakedModelQuad != null) {
            list.add(bakedModelQuad);
        }
    }

    private static BakedModelQuad a(BakedModelVertex bakedModelVertex, BakedModelVertex bakedModelVertex2, BakedModelVertex bakedModelVertex3, BakedModelVertex bakedModelVertex4, double d, double d2, double d3, double d4, float f, float f2, Boolean bl, float f3, float f4, float f5) {
        float f6 = (float)d;
        float f7 = (float)d2;
        float f8 = (float)(d + d3);
        float f9 = (float)(d2 + d4);
        float f10 = f6 / f;
        float f11 = f8 / f;
        float f12 = f7 / f2;
        float f13 = f9 / f2;
        BakedModelVertex[] bakedModelVertexArray = new BakedModelVertex[]{BedrockGeometryBaker.a(bakedModelVertex), BedrockGeometryBaker.a(bakedModelVertex2), BedrockGeometryBaker.a(bakedModelVertex3), BedrockGeometryBaker.a(bakedModelVertex4)};
        if (bl != null && bl.booleanValue()) {
            bakedModelVertexArray[0].c(f10, f12);
            bakedModelVertexArray[1].c(f11, f12);
            bakedModelVertexArray[2].c(f11, f13);
            bakedModelVertexArray[3].c(f10, f13);
        } else {
            bakedModelVertexArray[0].c(f11, f12);
            bakedModelVertexArray[1].c(f10, f12);
            bakedModelVertexArray[2].c(f10, f13);
            bakedModelVertexArray[3].c(f11, f13);
        }
        return new BakedModelQuad(bakedModelVertexArray, f3, f4, f5);
    }

    private static BakedModelVertex a(double d, double d2, double d3) {
        return new BakedModelVertex((float)d, (float)d2, (float)d3);
    }

    private static BakedModelVertex a(BakedModelVertex bakedModelVertex) {
        return new BakedModelVertex(bakedModelVertex.cc, bakedModelVertex.cd, bakedModelVertex.ce);
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

    static final class BoneGeometry {
        final String bQ;
        final float bR;
        final float bS;
        final float bT;
        final float bU;
        final float bV;
        final float bW;
        final List<BakedModelCube> bX;

        BoneGeometry(String string, float f, float f2, float f3, float f4, float f5, float f6, List<BakedModelCube> list) {
            this.bQ = string;
            this.bR = f;
            this.bS = f2;
            this.bT = f3;
            this.bU = f4;
            this.bV = f5;
            this.bW = f6;
            this.bX = list;
        }

        @Override
        public final String toString() {
            return "BoneGeometry[" + "name=" + this.bQ + ", " + "pivotX=" + this.bR + ", " + "pivotY=" + this.bS + ", " + "pivotZ=" + this.bT + ", " + "rotationX=" + this.bU + ", " + "rotationY=" + this.bV + ", " + "rotationZ=" + this.bW + ", " + "cubes=" + this.bX + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.bQ, this.bR, this.bS, this.bT, this.bU, this.bV, this.bW, this.bX);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof BoneGeometry other)) return false;
            return java.util.Objects.deepEquals(this.bQ, other.bQ)
                && java.util.Objects.deepEquals(this.bR, other.bR)
                && java.util.Objects.deepEquals(this.bS, other.bS)
                && java.util.Objects.deepEquals(this.bT, other.bT)
                && java.util.Objects.deepEquals(this.bU, other.bU)
                && java.util.Objects.deepEquals(this.bV, other.bV)
                && java.util.Objects.deepEquals(this.bW, other.bW)
                && java.util.Objects.deepEquals(this.bX, other.bX);
        }

        public String name() {
            return this.bQ;
        }

        public float aj() {
            return this.bR;
        }

        public float ak() {
            return this.bS;
        }

        public float al() {
            return this.bT;
        }

        public float am() {
            return this.bU;
        }

        public float an() {
            return this.bV;
        }

        public float ao() {
            return this.bW;
        }

        public List<BakedModelCube> ap() {
            return this.bX;
        }
    }
}
