package ru.method.celebression.punchy.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1306;
import net.minecraft.class_2350;
import net.minecraft.class_5603;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_630;
import org.joml.Vector3f;

public final class ItemModelDefinition {
    private final String namespace;
    private final int textureWidth;
    private final int textureHeight;
    private final Map<String, String> textures;
    private final List<Element> elements;
    private final Map<String, BoneDef> bones;
    private final Map<String, String> parentByBone;
    private final Map<String, List<String>> bonePaths;
    private final EnumMap<class_1306, Pose> poses = new EnumMap(class_1306.class);
    private final EnumMap<class_1306, DisplayTransform> displayTransforms = new EnumMap(class_1306.class);

    public ItemModelDefinition(String string, int n, int n2, Map<String, String> map, List<Element> list, Map<String, BoneDef> map2, Map<String, String> map3, Map<String, List<String>> map4) {
        this.namespace = string;
        this.textureWidth = n;
        this.textureHeight = n2;
        this.textures = map;
        this.elements = list;
        this.bones = map2;
        this.parentByBone = map3;
        this.bonePaths = map4;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public Map<String, String> getTextures() {
        return this.textures;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public DisplayTransform getDisplayTransform(class_1306 class_13062) {
        return this.displayTransforms.get(class_13062);
    }

    public void setDisplayTransform(class_1306 class_13062, DisplayTransform displayTransform) {
        if (class_13062 != null && displayTransform != null) {
            this.displayTransforms.put(class_13062, displayTransform);
        }
    }

    public Pose getPose(class_1306 class_13062) {
        Pose pose = this.poses.get(class_13062);
        if (pose != null) {
            return pose;
        }
        Pose pose2 = this.buildPose();
        this.poses.put(class_13062, pose2);
        return pose2;
    }

    private Pose buildPose() {
        class_5609 class_56092 = new class_5609();
        class_5610 class_56102 = class_56092.method_32111();
        HashMap<String, class_5610> hashMap = new HashMap<>();
        LinkedHashMap<String, BoneDef> linkedHashMap = new LinkedHashMap<String, BoneDef>(this.bones);
        boolean bl;
        do {
            bl = false;
            var iterator = linkedHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                BoneDef bone = iterator.next().getValue();
                String string = bone.getParent();
                if (string != null && !hashMap.containsKey(string)) continue;
                class_5610 parentData = string == null ? class_56102 : hashMap.get(string);
                Vector3f pivot = bone.getPivot();
                Vector3f relativePivot = new Vector3f(pivot);
                BoneDef parent = string != null ? this.bones.get(string) : null;
                if (parent != null) {
                    relativePivot.sub(parent.getPivot());
                }
                Vector3f rotation = bone.getRotation();
                class_5603 transform = class_5603.method_32091(relativePivot.x, relativePivot.y, relativePivot.z, (float)Math.toRadians(rotation.x), (float)Math.toRadians(rotation.y), (float)Math.toRadians(rotation.z));
                hashMap.put(bone.getName(), parentData.method_32117(bone.getName(), class_5606.method_32108(), transform));
                iterator.remove();
                bl = true;
            }
        } while (bl && !linkedHashMap.isEmpty());
        class_630 rootPart = class_5607.method_32110(class_56092, this.textureWidth, this.textureHeight).method_32109();
        HashMap<String, class_630> parts = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : this.bonePaths.entrySet()) {
            class_630 part = rootPart;
            for (String pathElement : entry.getValue()) {
                if (part == null) break;
                part = part.method_32086(pathElement);
            }
            if (part == null) continue;
            parts.put(entry.getKey(), part);
        }
        return new Pose(parts, this.parentByBone);
    }

    public static Map<String, List<String>> buildPaths(Map<String, String> map) {
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        for (String string : map.keySet()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            String string2 = string;
            while (string2 != null) {
                arrayList.add(0, string2);
                string2 = map.get(string2);
            }
            hashMap.put(string, arrayList);
        }
        return hashMap;
    }

    public static Map<String, String> copyParentMap(Map<String, String> map) {
        return Collections.unmodifiableMap(new HashMap<String, String>(map));
    }

    public static final class DisplayTransform {
        private final Vector3f rotation;
        private final Vector3f translation;
        private final Vector3f scale;

        public DisplayTransform(Vector3f vector3f, Vector3f vector3f2, Vector3f vector3f3) {
            this.rotation = vector3f;
            this.translation = vector3f2;
            this.scale = vector3f3;
        }

        public Vector3f getRotation() {
            return this.rotation;
        }

        public Vector3f getTranslation() {
            return this.translation;
        }

        public Vector3f getScale() {
            return this.scale;
        }
    }

    public static final class Pose {
        private final Map<String, class_630> parts;
        private final Map<String, String> parentByBone;

        public Pose(Map<String, class_630> map, Map<String, String> map2) {
            this.parts = map;
            this.parentByBone = map2;
        }

        public Map<String, class_630> getParts() {
            return this.parts;
        }

        public Map<String, String> getParentByBone() {
            return this.parentByBone;
        }
    }

    public static final class BoneDef {
        private final String name;
        private final String parent;
        private final Vector3f pivot;
        private final Vector3f rotation;

        public BoneDef(String string, String string2, Vector3f vector3f, Vector3f vector3f2) {
            this.name = string;
            this.parent = string2;
            this.pivot = vector3f;
            this.rotation = vector3f2;
        }

        public String getName() {
            return this.name;
        }

        public String getParent() {
            return this.parent;
        }

        public Vector3f getPivot() {
            return this.pivot;
        }

        public Vector3f getRotation() {
            return this.rotation;
        }
    }

    public static final class Face {
        private final float u0;
        private final float v0;
        private final float u1;
        private final float v1;
        private final int rotation;
        private final String textureRef;

        public Face(float f, float f2, float f3, float f4, int n, String string) {
            this.u0 = f;
            this.v0 = f2;
            this.u1 = f3;
            this.v1 = f4;
            this.rotation = n;
            this.textureRef = string;
        }

        public float getU0() {
            return this.u0;
        }

        public float getV0() {
            return this.v0;
        }

        public float getU1() {
            return this.u1;
        }

        public float getV1() {
            return this.v1;
        }

        public int getRotation() {
            return this.rotation;
        }

        public String getTextureRef() {
            return this.textureRef;
        }
    }

    public static final class Element {
        private final String boneName;
        private final Vector3f from;
        private final Vector3f to;
        private final EnumMap<class_2350, Face> faces;
        private final int lightEmission;
        private final boolean worldLight;

        public Element(String string, Vector3f vector3f, Vector3f vector3f2, EnumMap<class_2350, Face> enumMap, int n, boolean bl) {
            this.boneName = string;
            this.from = vector3f;
            this.to = vector3f2;
            this.faces = enumMap;
            this.lightEmission = n;
            this.worldLight = bl;
        }

        public String getBoneName() {
            return this.boneName;
        }

        public Vector3f getFrom() {
            return this.from;
        }

        public Vector3f getTo() {
            return this.to;
        }

        public EnumMap<class_2350, Face> getFaces() {
            return this.faces;
        }

        public int getLightEmission() {
            return this.lightEmission;
        }

        public boolean hasWorldLight() {
            return this.worldLight;
        }
    }
}
