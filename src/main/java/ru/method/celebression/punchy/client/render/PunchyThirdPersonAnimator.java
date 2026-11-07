package ru.method.celebression.punchy.client.render;

import ru.method.celebression.animation.Animation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_742;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import ru.method.celebression.punchy.client.access.TransformablePart;
import ru.method.celebression.punchy.client.animation.PunchyThirdPersonAnimationManager;
import ru.method.celebression.punchy.client.model.SimpleBedrockGeoThirdPerson;
import ru.method.celebression.punchy.compat.FirstPersonModelCompat;

public final class PunchyThirdPersonAnimator {
    private static final String GEO_PATH = "geo/player_3_per.geo.json";

    private PunchyThirdPersonAnimator() {
    }

    public static void apply(class_742 class_7422, class_591 class_5912, float f) {
        class_310 class_3102;
        if (class_7422 != null && class_5912 != null && (class_3102 = class_310.method_1551()) != null && class_3102.field_1690 != null) {
            if (class_3102.field_1690.method_31044().method_31034()) {
                if (FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_3102)) {
                    PunchyThirdPersonAnimator.clearModel(class_5912);
                } else {
                    PunchyThirdPersonAnimator.clearModel(class_5912);
                }
            } else if (class_3102.field_1724 != class_7422) {
                PunchyThirdPersonAnimator.clearModel(class_5912);
            } else {
                PunchyThirdPersonAnimator.ensureGeoLoaded();
                Map<String, class_630> map = SimpleBedrockGeoThirdPerson.loadedParts;
                if (map.isEmpty()) {
                    PunchyThirdPersonAnimator.clearModel(class_5912);
                } else {
                    PunchyThirdPersonAnimationManager.POSE_HANDLER.applyTransformations(map, f);
                    class_630 class_6302 = map.get("head");
                    class_630 class_6303 = map.get("body");
                    class_630 class_6304 = map.get("left_arm");
                    class_630 class_6305 = map.get("right_arm");
                    class_630 class_6306 = map.get("left_leg");
                    class_630 class_6307 = map.get("right_leg");
                    PunchyThirdPersonAnimator.applyWorldPart("head", class_5912.field_3398, class_6302, map);
                    PunchyThirdPersonAnimator.applyWorldPart("body", class_5912.field_3391, class_6303, map);
                    PunchyThirdPersonAnimator.applyWorldPart("left_arm", class_5912.field_27433, class_6304, map);
                    PunchyThirdPersonAnimator.applyWorldPart("right_arm", class_5912.field_3401, class_6305, map);
                    PunchyThirdPersonAnimator.applyWorldPart("left_leg", class_5912.field_3397, class_6306, map);
                    PunchyThirdPersonAnimator.applyWorldPart("right_leg", class_5912.field_3392, class_6307, map);
                    PunchyThirdPersonAnimator.applyLocalPart("hat", "head", class_5912.field_3394, map);
                    PunchyThirdPersonAnimator.applyLocalPart("jacket", "body", class_5912.field_3483, map);
                    PunchyThirdPersonAnimator.applyLocalPart("left_sleeve", "left_arm", class_5912.field_3484, map);
                    PunchyThirdPersonAnimator.applyLocalPart("right_sleeve", "right_arm", class_5912.field_3486, map);
                    PunchyThirdPersonAnimator.applyLocalPart("left_pants", "left_leg", class_5912.field_3482, map);
                    PunchyThirdPersonAnimator.applyLocalPart("right_pants", "right_leg", class_5912.field_3479, map);
                }
            }
        }
    }

    private static void ensureGeoLoaded() {
        if (SimpleBedrockGeoThirdPerson.loadedParts.isEmpty()) {
            class_2960 class_29602 = class_2960.method_60655((String)"punchy", (String)"geo/player_3_per.geo.json");
            SimpleBedrockGeoThirdPerson.load(class_29602);
        }
    }

    private static void applyWorldPart(String string, class_630 class_6302, class_630 class_6303, Map<String, class_630> map) {
        if (class_6302 != null) {
            boolean[] blArray = new boolean[1];
            Matrix4f matrix4f = PunchyThirdPersonAnimator.buildWorldMatrix(string, map, new HashSet<String>(), blArray);
            Matrix4f matrix4f2 = SimpleBedrockGeoThirdPerson.restWorldByName.get(string);
            if (matrix4f != null && matrix4f2 != null && blArray[0]) {
                Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f2).invert().mul((Matrix4fc)matrix4f);
                Matrix4f matrix4f4 = PunchyThirdPersonAnimator.getVanillaBaseMatrix(class_6302);
                Matrix4f matrix4f5 = matrix4f4.mul((Matrix4fc)matrix4f3);
                if ((Object)class_6302 instanceof TransformablePart transformablePart) {
                    transformablePart.punchy$setExplicitTransform(matrix4f5);
                }
            } else {
                PunchyThirdPersonAnimator.clearPart(class_6302);
            }
        }
    }

    private static void applyLocalPart(String string, String string2, class_630 class_6302, Map<String, class_630> map) {
        if (class_6302 != null) {
            boolean[] blArray = new boolean[1];
            Matrix4f matrix4f = PunchyThirdPersonAnimator.buildLocalDeltaMatrix(string, string2, map, blArray);
            if (matrix4f != null && blArray[0]) {
                Matrix4f matrix4f2 = PunchyThirdPersonAnimator.getVanillaBaseMatrix(class_6302);
                Matrix4f matrix4f3 = matrix4f2.mul((Matrix4fc)matrix4f);
                if ((Object)class_6302 instanceof TransformablePart transformablePart) {
                    transformablePart.punchy$setExplicitTransform(matrix4f3);
                }
            } else {
                PunchyThirdPersonAnimator.clearPart(class_6302);
            }
        }
    }

    private static Matrix4f getExplicitTransform(class_630 class_6302) {
        if (class_6302 == null) {
            return null;
        }
        if ((Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform != null) {
                return new Matrix4f((Matrix4fc)explicitTransform);
            }
        }
        return null;
    }

    private static Matrix4f buildWorldMatrix(String string, Map<String, class_630> map, Set<String> set, boolean[] blArray) {
        if (string != null && map != null && !set.contains(string)) {
            set.add(string);
            class_630 class_6302 = map.get(string);
            if (class_6302 == null) {
                return null;
            }
            Matrix4f matrix4f = PunchyThirdPersonAnimator.getLocalMatrix(class_6302, blArray);
            String string2 = SimpleBedrockGeoThirdPerson.parentByName.get(string);
            if (string2 == null) {
                return matrix4f;
            }
            Matrix4f matrix4f2 = PunchyThirdPersonAnimator.buildWorldMatrix(string2, map, set, blArray);
            return matrix4f2 == null ? matrix4f : matrix4f2.mul((Matrix4fc)matrix4f, new Matrix4f());
        }
        return null;
    }

    private static Matrix4f buildLocalDeltaMatrix(String string, String string2, Map<String, class_630> map, boolean[] blArray) {
        if (string != null && string2 != null && map != null) {
            boolean bl;
            boolean[] blArray2 = new boolean[1];
            boolean[] blArray3 = new boolean[1];
            Matrix4f matrix4f = PunchyThirdPersonAnimator.buildWorldMatrix(string, map, new HashSet<String>(), blArray2);
            Matrix4f matrix4f2 = PunchyThirdPersonAnimator.buildWorldMatrix(string2, map, new HashSet<String>(), blArray3);
            Matrix4f matrix4f3 = SimpleBedrockGeoThirdPerson.restWorldByName.get(string);
            Matrix4f matrix4f4 = SimpleBedrockGeoThirdPerson.restWorldByName.get(string2);
            boolean bl2 = bl = blArray2[0] || blArray3[0];
            if (blArray != null) {
                blArray[0] = bl;
            }
            if (matrix4f != null && matrix4f2 != null && matrix4f3 != null && matrix4f4 != null) {
                Matrix4f matrix4f5 = new Matrix4f((Matrix4fc)matrix4f2).invert().mul((Matrix4fc)matrix4f);
                Matrix4f matrix4f6 = new Matrix4f((Matrix4fc)matrix4f4).invert().mul((Matrix4fc)matrix4f3);
                return new Matrix4f((Matrix4fc)matrix4f6).invert().mul((Matrix4fc)matrix4f5);
            }
            return null;
        }
        return null;
    }

    private static Matrix4f getLocalMatrix(class_630 class_6302, boolean[] blArray) {
        Matrix4f matrix4f = PunchyThirdPersonAnimator.getExplicitTransform(class_6302);
        if (matrix4f != null) {
            if (blArray != null) {
                blArray[0] = true;
            }
            return new Matrix4f((Matrix4fc)matrix4f);
        }
        return new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
    }

    private static Matrix4f getVanillaBaseMatrix(class_630 class_6302) {
        return new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
    }

    private static void clearPart(class_630 class_6302) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            transformablePart.punchy$setExplicitTransform(null);
        }
    }

    private static void clearModel(class_591 class_5912) {
        if (class_5912 != null) {
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3398);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3391);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_27433);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3401);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3397);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3392);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3394);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3483);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3484);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3486);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3482);
            PunchyThirdPersonAnimator.clearPart(class_5912.field_3479);
        }
    }
}
