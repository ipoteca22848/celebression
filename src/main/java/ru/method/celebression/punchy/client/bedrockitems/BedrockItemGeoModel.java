package ru.method.celebression.punchy.client.bedrockitems;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_2350;
import net.minecraft.class_630;

final class BedrockItemGeoModel {
    private final class_630 root;
    private final Map<String, class_630> parts;
    private int textureWidth;
    private int textureHeight;
    private final Map<String, String> parentByBone;
    private final Map<String, List<BedrockCube>> cubesByBone;

    BedrockItemGeoModel(class_630 class_6302, Map<String, class_630> map, int n, int n2, Map<String, String> map2, Map<String, List<BedrockCube>> map3) {
        this.root = class_6302;
        this.parts = map == null ? Map.of() : new HashMap<String, class_630>(map);
        this.textureWidth = n;
        this.textureHeight = n2;
        this.parentByBone = map2 == null ? Map.of() : new HashMap<String, String>(map2);
        this.cubesByBone = map3 == null ? Map.of() : new HashMap<String, List<BedrockCube>>(map3);
    }

    class_630 getRoot() {
        return this.root;
    }

    Map<String, class_630> getParts() {
        return Collections.unmodifiableMap(this.parts);
    }

    int getTextureWidth() {
        return this.textureWidth;
    }

    int getTextureHeight() {
        return this.textureHeight;
    }

    void setTextureSize(int n, int n2) {
        if (n > 0) {
            this.textureWidth = n;
        }
        if (n2 > 0) {
            this.textureHeight = n2;
        }
    }

    Map<String, String> getParentByBone() {
        return Collections.unmodifiableMap(this.parentByBone);
    }

    Map<String, List<BedrockCube>> getCubesByBone() {
        return Collections.unmodifiableMap(this.cubesByBone);
    }

    void resetPose() {
        if (this.root != null) {
            this.root.method_41923();
        }
        for (class_630 class_6302 : this.parts.values()) {
            if (class_6302 == null) continue;
            class_6302.method_41923();
        }
    }

    static final class BedrockFace {
        final float u;
        final float v;
        final float uSize;
        final float vSize;
        final int rotation;

        BedrockFace(float f, float f2, float f3, float f4, int n) {
            this.u = f;
            this.v = f2;
            this.uSize = f3;
            this.vSize = f4;
            this.rotation = n;
        }
    }

    static final class BedrockCube {
        final float x1;
        final float y1;
        final float z1;
        final float x2;
        final float y2;
        final float z2;
        final Map<class_2350, BedrockFace> faces;
        final boolean mirror;
        final float pivotX;
        final float pivotY;
        final float pivotZ;
        final float rotX;
        final float rotY;
        final float rotZ;
        final boolean hasRotation;

        BedrockCube(float f, float f2, float f3, float f4, float f5, float f6, Map<class_2350, BedrockFace> map, boolean bl, float f7, float f8, float f9, float f10, float f11, float f12) {
            this.x1 = f;
            this.y1 = f2;
            this.z1 = f3;
            this.x2 = f4;
            this.y2 = f5;
            this.z2 = f6;
            this.faces = map == null ? Map.of() : map;
            this.mirror = bl;
            this.pivotX = f7;
            this.pivotY = f8;
            this.pivotZ = f9;
            this.rotX = f10;
            this.rotY = f11;
            this.rotZ = f12;
            this.hasRotation = f10 != 0.0f || f11 != 0.0f || f12 != 0.0f;
        }
    }
}

