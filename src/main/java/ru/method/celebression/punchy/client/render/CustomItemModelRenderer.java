package ru.method.celebression.punchy.client.render;

import ru.method.celebression.animation.Animation;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1047;
import net.minecraft.class_1058;
import net.minecraft.class_1059;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2350;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_630;
import net.minecraft.class_746;
import net.minecraft.class_753;
import net.minecraft.class_765;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.model.ItemModelDefinition;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.modelparts.ModelPartsSystem;
import ru.method.celebression.punchy.client.modelparts.util.ModelPartMath;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.util.ResourceLocationCompat;

public final class CustomItemModelRenderer {
    private static final class_2960 ITEM_ATLAS_ID = ResourceLocationCompat.fromNamespaceAndPath("minecraft", "items");
    private static final class_2960 ITEM_ATLAS_TEXTURE = ResourceLocationCompat.fromNamespaceAndPath("minecraft", "textures/atlas/items.png");
    private static final float MODEL_UNIT = 0.0625f;
    private static final int MAX_MODEL_EMISSION = 15;

    private CustomItemModelRenderer() {
    }

    public static boolean tryRender(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f) {
        return CustomItemModelRenderer.tryRender(class_17992, class_7462, class_13062, class_45872, class_45972, n, f, true);
    }

    public static boolean tryRender(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f, boolean bl) {
        if (class_17992 != null && !class_17992.method_7960() && class_7462 != null && class_13062 != null) {
            class_2960 class_29602 = ModelPartsSystem.get().resolveItemModelId(class_17992, (class_1657)class_7462);
            if (class_29602 == null) {
                return false;
            }
            ItemModelDefinition itemModelDefinition = ModelPartsSystem.get().getItemModel(class_29602);
            if (itemModelDefinition != null && !itemModelDefinition.getElements().isEmpty()) {
                ItemModelDefinition.Pose pose = itemModelDefinition.getPose(class_13062);
                ItemAnimationManager.applyToParts(class_13062, pose.getParts(), f);
                class_45872.method_22903();
                class_45872.method_22905(0.0625f, 0.0625f, 0.0625f);
                if (bl) {
                    CustomItemModelRenderer.applyDisplayTransform(itemModelDefinition, class_13062, class_45872);
                }
                class_45872.method_46416(-0.5f, -0.5f, -0.5f);
                HashMap<String, Matrix4f> hashMap = new HashMap<String, Matrix4f>();
                Map<String, Matrix4f> map = CustomItemModelRenderer.collectRenderBoneTransforms(pose, hashMap, class_45872.method_23760().method_23761());
                ModelPartsParticleSystem.setRenderRoot(class_13062, class_45872.method_23760().method_23761());
                ModelPartsParticleSystem.emitDefinitionForRenderBones(class_310.method_1551(), class_13062, map, ToolTuning.getParticleEmitters(class_17992));
                ModelPartsParticleSystem.emitPendingForRenderBones(class_310.method_1551(), class_13062, map);
                ModelPartsParticleSystem.renderForArm(class_310.method_1551(), class_13062, class_45972, class_45872, n);
                CustomItemModelRenderer.renderElements(itemModelDefinition, pose, class_45872, class_45972, n);
                class_45872.method_22909();
                return true;
            }
            return false;
        }
        return false;
    }

    private static Map<String, Matrix4f> collectRenderBoneTransforms(ItemModelDefinition.Pose pose, Map<String, Matrix4f> map, Matrix4f matrix4f) {
        HashMap<String, Matrix4f> hashMap = new HashMap<String, Matrix4f>();
        if (pose != null && matrix4f != null) {
            for (String string : pose.getParts().keySet()) {
                Matrix4f matrix4f2 = CustomItemModelRenderer.computeWorldMatrix(string, pose, map);
                if (matrix4f2 == null) continue;
                Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f2);
                hashMap.put(string, matrix4f3);
            }
            return hashMap;
        }
        return hashMap;
    }

    private static void renderElements(ItemModelDefinition itemModelDefinition, ItemModelDefinition.Pose pose, class_4587 class_45872, class_4597 class_45972, int n) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null) {
            class_1059 class_10592;
            try {
                class_10592 = class_3102.method_1554().method_24153(ITEM_ATLAS_ID);
            }
            catch (NullPointerException nullPointerException) {
                return;
            }
            class_1058 class_10582 = class_10592.method_4608(class_1047.method_4539());
            HashMap<String, Matrix4f> hashMap = new HashMap<String, Matrix4f>();
            for (ItemModelDefinition.Element element : itemModelDefinition.getElements()) {
                EnumMap<class_2350, ItemModelDefinition.Face> enumMap;
                Matrix4f matrix4f = CustomItemModelRenderer.computeWorldMatrix(element.getBoneName(), pose, hashMap);
                if (matrix4f == null || (enumMap = element.getFaces()) == null || enumMap.isEmpty()) continue;
                class_45872.method_22903();
                class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f);
                class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f));
                int n2 = CustomItemModelRenderer.applyEmission(n, element.getLightEmission());
                for (Map.Entry<class_2350, ItemModelDefinition.Face> entry : enumMap.entrySet()) {
                    class_2960 class_29602;
                    class_1058 class_10583;
                    ItemModelDefinition.Face face = entry.getValue();
                    if (face == null || (class_10583 = (class_29602 = CustomItemModelRenderer.resolveTexture(itemModelDefinition, face.getTextureRef())) != null ? class_10592.method_4608(class_29602) : class_10582) == null || class_10583 == class_10582) continue;
                    class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)ITEM_ATLAS_TEXTURE));
                    CustomItemModelRenderer.emitFace(class_45872.method_23760(), class_45882, entry.getKey(), element.getFrom(), element.getTo(), face, class_10583, n2, itemModelDefinition.getTextureWidth(), itemModelDefinition.getTextureHeight());
                }
                class_45872.method_22909();
            }
        }
    }

    private static int applyEmission(int n, int n2) {
        if (n2 <= 0) {
            return n;
        }
        int n3 = Math.round((float)Math.min(n2, 15) * 1.0f);
        int n4 = class_765.method_24187((int)n);
        int n5 = class_765.method_24186((int)n);
        int n6 = Math.max(n4, n3);
        int n7 = Math.max(n5, n3);
        return class_765.method_23687((int)n7, (int)n6);
    }

    private static Matrix4f computeWorldMatrix(String string, ItemModelDefinition.Pose pose, Map<String, Matrix4f> map) {
        if (string != null && pose != null) {
            Matrix4f matrix4f;
            Matrix4f matrix4f2 = map.get(string);
            if (matrix4f2 != null) {
                return matrix4f2;
            }
            Map<String, class_630> map2 = pose.getParts();
            class_630 class_6302 = map2.get(string);
            if (class_6302 == null) {
                return null;
            }
            Matrix4f matrix4f3 = ModelPartMath.getLocalMatrix(class_6302);
            String string2 = pose.getParentByBone().get(string);
            Matrix4f matrix4f4 = string2 != null ? ((matrix4f = CustomItemModelRenderer.computeWorldMatrix(string2, pose, map)) == null ? matrix4f3 : new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f3)) : matrix4f3;
            map.put(string, matrix4f4);
            return matrix4f4;
        }
        return null;
    }

    private static void applyDisplayTransform(ItemModelDefinition itemModelDefinition, class_1306 class_13062, class_4587 class_45872) {
        ItemModelDefinition.DisplayTransform displayTransform = itemModelDefinition.getDisplayTransform(class_13062);
        if (displayTransform != null) {
            Vector3f vector3f = displayTransform.getTranslation();
            Vector3f vector3f2 = displayTransform.getRotation();
            Vector3f vector3f3 = displayTransform.getScale();
            float f = vector3f.x * 0.0625f;
            float f2 = vector3f.y * 0.0625f;
            float f3 = vector3f.z * 0.0625f;
            float f4 = vector3f2.x;
            float f5 = vector3f2.y;
            float f6 = vector3f2.z;
            if (class_13062 == class_1306.field_6182) {
                f = -f;
                f5 = -f5;
                f6 = -f6;
            }
            class_45872.method_46416(f, f2, f3);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(f4));
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(f5));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(f6));
            class_45872.method_22905(vector3f3.x, vector3f3.y, vector3f3.z);
        }
    }

    private static class_2960 resolveTargetId(class_1799 class_17992) {
        class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
        return class_29602 == null ? null : ResourceLocationCompat.fromNamespaceAndPath(class_29602.method_12836(), "item/" + class_29602.method_12832());
    }

    private static class_2960 resolveTexture(ItemModelDefinition itemModelDefinition, String string) {
        if (itemModelDefinition != null && string != null && !string.isBlank()) {
            String string2;
            String string3;
            String string4 = string;
            Map<String, String> map = itemModelDefinition.getTextures();
            for (int i = 0; string4.startsWith("#") && i < 6 && (string3 = map.get(string2 = string4.substring(1))) != null; ++i) {
                string4 = string3;
            }
            class_2960 class_29602 = class_2960.method_12829((String)string4);
            return class_29602 != null ? class_29602 : ResourceLocationCompat.fromNamespaceAndPath(itemModelDefinition.getNamespace(), string4);
        }
        return null;
    }

    private static void emitFace(class_4587.class_4665 class_46652, class_4588 class_45882, class_2350 class_23502, Vector3f vector3f, Vector3f vector3f2, ItemModelDefinition.Face face, class_1058 class_10582, int n, int n2, int n3) {
        float f = face.getU0();
        float f2 = face.getU1();
        float f3 = face.getV0();
        float f4 = face.getV1();
        float f5 = Math.max(Math.max(Math.abs(f), Math.abs(f2)), Math.max(Math.abs(f3), Math.abs(f4)));
        float f6 = f5 > 16.0f && n2 > 0 ? 16.0f / (float)n2 : 1.0f;
        float f7 = f5 > 16.0f && n3 > 0 ? 16.0f / (float)n3 : 1.0f;
        class_753 class_7532 = class_753.method_3163((class_2350)class_23502);
        float f8 = class_23502.method_10148();
        float f9 = class_23502.method_10164();
        float f10 = class_23502.method_10165();
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f3 = CustomItemModelRenderer.selectVertex(class_7532.method_3162(i), vector3f, vector3f2);
            float f11 = CustomItemModelRenderer.getRotatedU(i, f * f6, f2 * f6, face.getRotation()) / 16.0f;
            float f12 = CustomItemModelRenderer.getRotatedV(i, f3 * f7, f4 * f7, face.getRotation()) / 16.0f;
            CustomItemModelRenderer.addVertex(class_46652, class_45882, vector3f3.x, vector3f3.y, vector3f3.z, class_10582.method_4580(f11), class_10582.method_4570(f12), n, f8, f9, f10);
        }
    }

    private static float getRotatedU(int n, float f, float f2, int n2) {
        int n3 = CustomItemModelRenderer.rotateVertexIndex(n, n2);
        return n3 != 0 && n3 != 1 ? f2 : f;
    }

    private static float getRotatedV(int n, float f, float f2, int n2) {
        int n3 = CustomItemModelRenderer.rotateVertexIndex(n, n2);
        return n3 != 0 && n3 != 3 ? f2 : f;
    }

    private static int rotateVertexIndex(int n, int n2) {
        int n3 = n2 % 360;
        if (n3 < 0) {
            n3 += 360;
        }
        int n4 = n3 / 90 % 4;
        return (n + n4) % 4;
    }

    private static void addVertex(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, int n, float f6, float f7, float f8) {
        class_45882.method_22918(class_46652.method_23761(), f, f2, f3).method_1336(255, 255, 255, 255).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, f6, f7, f8);
    }

    private static Vector3f selectVertex(class_753.class_755 class_7552, Vector3f vector3f, Vector3f vector3f2) {
        float f = class_7552.field_3975 == class_753.class_754.field_3967 ? vector3f.x : vector3f2.x;
        float f2 = class_7552.field_3974 == class_753.class_754.field_3968 ? vector3f.y : vector3f2.y;
        float f3 = class_7552.field_3973 == class_753.class_754.field_3969 ? vector3f.z : vector3f2.z;
        return new Vector3f(f, f2, f3);
    }
}
