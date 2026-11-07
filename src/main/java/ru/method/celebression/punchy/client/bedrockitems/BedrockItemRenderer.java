package ru.method.celebression.punchy.client.bedrockitems;

import com.google.gson.JsonElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1044;
import net.minecraft.class_10442;
import net.minecraft.class_10444;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2350;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5762;
import net.minecraft.class_630;
import net.minecraft.class_746;
import net.minecraft.class_7833;
import net.minecraft.class_804;
import net.minecraft.class_811;
import net.minecraft.class_9279;
import net.minecraft.class_9334;
import net.minecraft.class_9851;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.bedrockitems.BedrockAxolPhysicsStateMachine;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemDefinition;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemGeoModel;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;
import ru.method.celebression.punchy.client.bedrockitems.BedrockPufferPhysicsStateMachine;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.modelparts.util.ModelPartMath;
import ru.method.celebression.punchy.client.render.PendulumPhysics;
import ru.method.celebression.punchy.client.render.ResourcePackItemTransformResolver;
import ru.method.celebression.punchy.client.render.SlimeInHandPhysics;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateAccessor;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateLayerAccessor;

final class BedrockItemRenderer {
    private static final EnumMap<class_1306, class_2960> LAST_RENDER_DEF_ID = new EnumMap(class_1306.class);
    private static final float MODEL_UNIT = 0.0625f;
    private static final float EMISSIVE_BLUR_SCALE = 1.08f;
    private static final float EMISSIVE_BLUR_ALPHA = 0.4f;
    private static final float EMISSIVE_BLUR_COLOR = 0.6f;
    private static final float PUFFER_INCLINATION_SCALE = 1.19f;
    private static final float PUFFER_PITCH_BOOST = 1.3f;
    private static final Set<String> AXOL_PHYSICS_BONES = Set.of("fish", "head", "top_gills", "left_gills", "right_gills", "body", "legs_front", "leg1", "leg2", "leg4", "leg5");
    private static final Set<String> PUFFER_PHYSICS_BONES = Set.of("fish", "body", "right_fin", "left_fin", "fin_top", "tail", "eye", "fin_left", "fin_right", "spikes");
    private static final Map<EmissiveKey, class_1921> EMISSIVE_OVERLAY_TYPES = new HashMap<EmissiveKey, class_1921>();
    private static final Vector3f[] SCRATCH_CORNERS = new Vector3f[]{new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f()};
    private static final Vector3f[] SCRATCH_QUAD = new Vector3f[4];
    private static final float[] SCRATCH_UVS = new float[8];
    private static final Vector3f SCRATCH_NORMAL = new Vector3f();
    private static final Matrix4f SCRATCH_ROT_M4 = new Matrix4f();
    private static final Matrix3f SCRATCH_ROT_M3 = new Matrix3f();
    private static boolean irisResolved;
    private static boolean irisPresent;
    private static Object irisApi;
    private static Method irisIsShaderPackInUse;
    private static long irisPollStampMs;
    private static boolean irisShaderPackCached;

    private BedrockItemRenderer() {
    }

    static boolean render(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f, boolean bl) {
        if (bedrockItemDefinition != null && class_17992 != null && !class_17992.method_7960() && class_7462 != null && class_13062 != null) {
            class_2960 class_29602 = BedrockItemRenderer.resolveDynamicGeoId(class_17992, bedrockItemDefinition);
            BedrockItemGeoModel bedrockItemGeoModel = bedrockItemDefinition.getGeoModel(class_29602);
            if (bedrockItemGeoModel != null && bedrockItemGeoModel.getRoot() != null) {
                boolean bl2;
                class_2960 class_29603 = bedrockItemDefinition.getTextureId();
                if (class_29603 == null) {
                    return false;
                }
                class_2960 class_29604 = bedrockItemDefinition.getId();
                class_2960 class_29605 = LAST_RENDER_DEF_ID.get(class_13062);
                if (class_13062 != null) {
                    if (class_29605 != null && class_29604 != null && !class_29605.equals((Object)class_29604)) {
                        ModelPartsParticleSystem.resetArm(class_13062);
                    }
                    LAST_RENDER_DEF_ID.put(class_13062, class_29604);
                }
                bedrockItemGeoModel.resetPose();
                BedrockItemAnimationManager.applyToParts(class_13062, bedrockItemGeoModel.getParts(), f);
                class_45872.method_22903();
                Matrix4f matrix4f = new Matrix4f();
                class_45872.method_22905(0.0625f, 0.0625f, 0.0625f);
                matrix4f.scale(0.0625f, 0.0625f, 0.0625f);
                class_45872.method_46416(0.5f, 0.0f, 0.5f);
                matrix4f.translate(0.5f, 0.0f, 0.5f);
                if (bl) {
                    BedrockItemRenderer.applyDisplayTransform(bedrockItemDefinition, class_17992, class_7462, class_13062, class_45872);
                    BedrockItemRenderer.applyDisplayTransformMatrix(bedrockItemDefinition, class_17992, class_7462, class_13062, matrix4f);
                }
                class_1268 class_12682 = BedrockItemRenderer.resolveInteractionHand(class_7462, class_13062);
                boolean bl3 = BedrockItemAnimationManager.isAnimMirrorActive(class_13062);
                if (class_13062 == class_1306.field_6182) {
                    class_45872.method_22905(-1.0f, 1.0f, 1.0f);
                    matrix4f.scale(-1.0f, 1.0f, 1.0f);
                    class_45872.method_23760().method_23762().scale(-1.0f, 1.0f, 1.0f);
                }
                class_45872.method_46416(-0.5f, -0.5f, -0.5f);
                matrix4f.translate(-0.5f, -0.5f, -0.5f);
                Map<String, List<BedrockItemGeoModel.BedrockCube>> map = bedrockItemGeoModel.getCubesByBone();
                boolean bl4 = bl2 = class_13062 == class_1306.field_6182;
                if (map != null && !map.isEmpty()) {
                    float f2 = (float)class_7462.field_6012 + f;
                    BedrockItemRenderer.renderCubes(class_17992, bedrockItemGeoModel, class_45872, class_45972, n, class_29603, bedrockItemDefinition.getBoneEmission(), bedrockItemDefinition.getBoneTextures(), bedrockItemDefinition.getBoneTextureAnim(), bedrockItemDefinition.isBoneEmissionBlur(), bedrockItemDefinition.isBoneEmissionClamp(), bedrockItemDefinition.getBoneEmissionStrength(), bedrockItemDefinition.getTextureAnimFrames(), bedrockItemDefinition.getTextureAnimFrameTime(), bedrockItemDefinition.getTextureAnimFrameHeight(), bedrockItemDefinition.getTexturePixelWidth(), bedrockItemDefinition.getTexturePixelHeight(), f2, f, bedrockItemDefinition, class_13062, class_12682, matrix4f, bl2);
                } else {
                    class_630 class_6302 = bedrockItemGeoModel.getRoot();
                    class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29603));
                    class_6302.method_22698(class_45872, class_45882, n, class_4608.field_21444);
                }
                class_45872.method_22909();
                return true;
            }
            return false;
        }
        return false;
    }

    private static void applyDisplayTransform(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872) {
        BedrockItemDefinition.DisplayTransform displayTransform = bedrockItemDefinition.getDisplayTransform(class_13062);
        if (displayTransform == null) {
            BedrockItemRenderer.applyVanillaDisplayTransform(class_17992, class_7462, class_13062, class_45872);
        } else {
            float f = displayTransform.transX * 0.0625f;
            float f2 = displayTransform.transY * 0.0625f;
            float f3 = displayTransform.transZ * 0.0625f;
            float f4 = displayTransform.rotX;
            float f5 = displayTransform.rotY;
            float f6 = displayTransform.rotZ;
            if (class_13062 == class_1306.field_6182) {
                f = -f;
                f5 = -f5;
                f6 = -f6;
            }
            class_45872.method_46416(f, f2, f3);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(f4));
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(f5));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(f6));
            class_45872.method_22905(displayTransform.scaleX, displayTransform.scaleY, displayTransform.scaleZ);
        }
    }

    private static void applyDisplayTransformMatrix(BedrockItemDefinition bedrockItemDefinition, class_1799 class_17992, class_746 class_7462, class_1306 class_13062, Matrix4f matrix4f) {
        if (matrix4f != null) {
            BedrockItemDefinition.DisplayTransform displayTransform = bedrockItemDefinition.getDisplayTransform(class_13062);
            if (displayTransform == null) {
                class_811 class_8112 = class_13062 == class_1306.field_6182 ? class_811.field_4321 : class_811.field_4322;
                class_804 class_8042 = BedrockItemRenderer.resolveDisplayTransform(class_17992, class_8112, (class_1309)class_7462);
                if (class_8042 != null) {
                    Vector3f vector3f = class_8042.field_4287;
                    Vector3f vector3f2 = class_8042.field_4286;
                    Vector3f vector3f3 = class_8042.field_4285;
                    if (vector3f != null && vector3f2 != null && vector3f3 != null) {
                        matrix4f.translate(vector3f2.x() * 0.0625f, vector3f2.y() * 0.0625f, vector3f2.z() * 0.0625f);
                        matrix4f.rotateXYZ((float)Math.toRadians(vector3f.x()), (float)Math.toRadians(vector3f.y()), (float)Math.toRadians(vector3f.z()));
                        matrix4f.scale(vector3f3.x(), vector3f3.y(), vector3f3.z());
                    }
                }
            } else {
                float f = displayTransform.transX * 0.0625f;
                float f2 = displayTransform.transY * 0.0625f;
                float f3 = displayTransform.transZ * 0.0625f;
                float f4 = displayTransform.rotX;
                float f5 = displayTransform.rotY;
                float f6 = displayTransform.rotZ;
                if (class_13062 == class_1306.field_6182) {
                    f = -f;
                    f5 = -f5;
                    f6 = -f6;
                }
                matrix4f.translate(f, f2, f3);
                matrix4f.rotateXYZ((float)Math.toRadians(f4), (float)Math.toRadians(f5), (float)Math.toRadians(f6));
                matrix4f.scale(displayTransform.scaleX, displayTransform.scaleY, displayTransform.scaleZ);
            }
        }
    }

    private static void applyVanillaDisplayTransform(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872) {
        class_811 class_8112;
        class_804 class_8042;
        if (class_17992 != null && !class_17992.method_7960() && class_7462 != null && class_13062 != null && (class_8042 = BedrockItemRenderer.resolveDisplayTransform(class_17992, class_8112 = class_13062 == class_1306.field_6182 ? class_811.field_4321 : class_811.field_4322, (class_1309)class_7462)) != null) {
            Vector3f vector3f = class_8042.field_4287;
            Vector3f vector3f2 = class_8042.field_4286;
            Vector3f vector3f3 = class_8042.field_4285;
            if (vector3f != null && vector3f2 != null && vector3f3 != null) {
                class_45872.method_46416(vector3f2.x() * 0.0625f, vector3f2.y() * 0.0625f, vector3f2.z() * 0.0625f);
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(vector3f.x()));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(vector3f.y()));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(vector3f.z()));
                class_45872.method_22905(vector3f3.x(), vector3f3.y(), vector3f3.z());
            }
        }
    }

    private static class_804 resolveDisplayTransform(class_1799 class_17992, class_811 class_8112, class_1309 class_13092) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_804 class_8042 = ResourcePackItemTransformResolver.resolve(class_17992, class_8112);
            return class_8042 != null ? class_8042 : BedrockItemRenderer.resolveVanillaTransform(class_17992, class_8112, class_13092);
        }
        return null;
    }

    private static class_804 resolveVanillaTransform(class_1799 class_17992, class_811 class_8112, class_1309 class_13092) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null && class_13092 != null) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 == null) {
                return null;
            }
            class_10442 class_104422 = class_3102.method_65386();
            if (class_104422 == null) {
                return null;
            }
            class_10444 class_104442 = new class_10444();
            boolean bl = UseAnimationSuppressor.isForced();
            UseAnimationSuppressor.force(true);
            try {
                class_104422.method_65597(class_104442, class_17992, class_8112, class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323, class_13092);
                class_804 class_8042 = BedrockItemRenderer.resolveLayerTransform(class_104442);
                return class_8042;
            }
            catch (Throwable throwable) {
                return null;
            }
            finally {
                UseAnimationSuppressor.force(bl);
            }
        }
        return null;
    }

    private static class_804 resolveLayerTransform(class_10444 class_104442) {
        if (class_104442 == null) {
            return null;
        }
        ItemStackRenderStateAccessor itemStackRenderStateAccessor = (ItemStackRenderStateAccessor)class_104442;
        class_10444.class_10446[] class_10446Array = itemStackRenderStateAccessor.punchy$getLayers();
        int n = itemStackRenderStateAccessor.punchy$getActiveLayerCount();
        if (class_10446Array != null && n > 0) {
            for (int i = 0; i < n && i < class_10446Array.length; ++i) {
                class_804 class_8042;
                class_10444.class_10446 class_104462 = class_10446Array[i];
                if (class_104462 == null || (class_8042 = ((ItemStackRenderStateLayerAccessor)class_104462).punchy$getTransform()) == null) continue;
                return class_8042;
            }
            return null;
        }
        return null;
    }

    private static class_1268 resolveInteractionHand(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            if (class_13062 == class_1306.field_6183) {
                return bl ? class_1268.field_5808 : class_1268.field_5810;
            }
            return bl ? class_1268.field_5810 : class_1268.field_5808;
        }
        return class_1268.field_5808;
    }

    private static void renderCubes(class_1799 class_17992, BedrockItemGeoModel bedrockItemGeoModel, class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, Map<String, Integer> map, Map<String, class_2960> map2, Map<String, BedrockItemDefinition.TextureAnimSpec> map3, boolean bl, boolean bl2, int n2, int n3, int n4, int n5, int n6, int n7, float f, float f2, BedrockItemDefinition bedrockItemDefinition, class_1306 class_13062, class_1268 class_12682, Matrix4f matrix4f, boolean bl3) {
        Map<String, class_630> map4 = bedrockItemGeoModel.getParts();
        Map<String, String> map5 = bedrockItemGeoModel.getParentByBone();
        Map<String, List<BedrockItemGeoModel.BedrockCube>> map6 = bedrockItemGeoModel.getCubesByBone();
        boolean bl5 = BedrockItemRenderer.isPufferModel(map4);
        boolean bl6 = !bl5 && BedrockItemRenderer.isAxolModel(map4, map6);
        HashMap<String, Matrix4f> hashMap = new HashMap<String, Matrix4f>();
        Matrix4f matrix4f2 = bl6 ? BedrockItemRenderer.computeWorldMatrix("head", map4, map5, hashMap) : null;
        Matrix4f matrix4f3 = bl6 ? BedrockItemRenderer.computeWorldMatrix("body", map4, map5, hashMap) : null;
        Matrix4f matrix4f4 = bl5 ? BedrockItemRenderer.computeWorldMatrix("fish", map4, map5, hashMap) : null;
        int n8 = bedrockItemGeoModel.getTextureWidth();
        int n9 = bedrockItemGeoModel.getTextureHeight();
        Map<String, Integer> map7 = map == null ? Map.of() : map;
        float f3 = class_3532.method_15363((float)((float)n2 / 100.0f), (float)0.0f, (float)1.0f);
        Map<String, class_2960> map8 = BedrockItemRenderer.mergeDynamicBoneTextures(class_17992, bedrockItemDefinition, map2);
        HashMap<String, class_2960> hashMap2 = new HashMap<String, class_2960>();
        Map<String, BedrockItemDefinition.TextureAnimSpec> map9 = map3 == null ? Map.of() : map3;
        boolean bl7 = BedrockItemRenderer.isShaderPackInUse();
        Set<String> set = bedrockItemDefinition != null ? bedrockItemDefinition.getCameraTransparentBones() : Set.of();
        Map<String, BedrockItemDefinition.BonePhysicsSpec> map10 = BedrockItemRenderer.mergeBonePhysics(bedrockItemDefinition != null ? bedrockItemDefinition.getBonePhysics() : null, ToolTuning.getSpecificBonePhysics(class_17992));
        Set<String> set2 = null;
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = class_13062 != null ? BedrockItemAnimationManager.getActiveEntry(class_13062) : null;
        if (customAnimationEntry != null && customAnimationEntry.disable_physics_bones != null) {
            set2 = new HashSet<>();
            for (String object52 : customAnimationEntry.disable_physics_bones) {
                if (object52 == null || object52.isBlank()) continue;
                set2.add(object52.trim());
            }
        }
        if (set2 == null) {
            set2 = bedrockItemDefinition != null ? bedrockItemDefinition.getDisablePhysicsBones() : Set.of();
        }
        Matrix4f pendulumCancellation = null;
        if (set2 != null && !set2.isEmpty() && class_13062 != null) {
            Matrix4f currentPhysicsDelta = BedrockItemSystem.getCurrentPhysicsDelta(class_13062);
            if (currentPhysicsDelta != null) {
                pendulumCancellation = new Matrix4f(currentPhysicsDelta);
            } else {
                Matrix4f matrix4f5 = PendulumPhysics.get().getLastAppliedMatrix(class_13062);
                if (matrix4f5 != null) {
                    pendulumCancellation = new Matrix4f(matrix4f5).invert();
                }
            }
        }
        Matrix4f rootTransform = matrix4f != null ? new Matrix4f(matrix4f) : new Matrix4f();
        Matrix4f matrix4f6 = new Matrix4f(rootTransform).invert();
        Matrix4f matrix4f7 = null;
        if (pendulumCancellation != null) {
            matrix4f7 = new Matrix4f(matrix4f6).mul(pendulumCancellation).mul(rootTransform);
        }
        Matrix4f matrix4f8 = matrix4f7 == null ? null : new Matrix4f(matrix4f7);
        BedrockAxolPhysicsStateMachine.FeedbackPose feedbackPose = null;
        BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose2 = null;
        if (bedrockItemDefinition != null && bedrockItemDefinition.isPhysicsFeedbackEnabled() && bl6 && (feedbackPose = BedrockItemSystem.get().getAxolPhysicsPose(class_13062, f2)) != null && feedbackPose.isIdentity()) {
            feedbackPose = null;
        }
        if (bedrockItemDefinition != null && (bl5 || BedrockItemRenderer.hasCreatureBonePhysics(map10)) && (feedbackPose2 = BedrockItemSystem.get().getPufferPhysicsPose(class_13062, f2)) != null && feedbackPose2.isIdentity()) {
            feedbackPose2 = null;
        }
        ArrayList<Map.Entry<String, List<BedrockItemGeoModel.BedrockCube>>> arrayList = new ArrayList<Map.Entry<String, List<BedrockItemGeoModel.BedrockCube>>>(map6.entrySet());
        HashMap<String, Matrix4f> hashMap3 = new HashMap<String, Matrix4f>();
        HashMap<String, Boolean> hashMap4 = new HashMap<String, Boolean>();
        HashMap<String, Matrix4f> hashMap5 = new HashMap<String, Matrix4f>();
        Matrix4f matrix4f9 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(map4.keySet());
        linkedHashSet.addAll(map6.keySet());
        for (String string : linkedHashSet) {
            Matrix4f worldMatrix = string == null ? null : BedrockItemRenderer.computeWorldMatrix(string, map4, map5, hashMap);
            if (worldMatrix == null) continue;
            boolean bl8 = set2 != null && BedrockItemRenderer.isPendulumSuppressed(string, map5, set2);
            Matrix4f animatedWorldMatrix = worldMatrix;
            if (feedbackPose != null) {
                animatedWorldMatrix = BedrockItemRenderer.applyAxolPhysicsIfNeeded(animatedWorldMatrix, string, feedbackPose, map5, matrix4f2, matrix4f3);
            }
            if (feedbackPose2 != null) {
                animatedWorldMatrix = BedrockItemRenderer.applyPufferPhysicsIfNeeded(animatedWorldMatrix, string, feedbackPose2, map5, matrix4f4);
            }
            animatedWorldMatrix = BedrockItemRenderer.applyCustomBonePhysicsIfNeeded(animatedWorldMatrix, string, map10, matrix4f9, map4, map5, hashMap, feedbackPose2, class_13062, class_12682, f2);
            hashMap3.put(string, animatedWorldMatrix);
            hashMap4.put(string, bl8);
            Matrix4f renderMatrix = new Matrix4f(matrix4f9);
            if (matrix4f8 != null && bl8) {
                renderMatrix.mul(matrix4f8);
            }
            renderMatrix.mul(animatedWorldMatrix);
            hashMap5.put(string, renderMatrix);
        }
        List<String> list2 = ToolTuning.getParticleEmitters(class_17992);
        ModelPartsParticleSystem.setRenderRoot(class_13062, class_45872.method_23760().method_23761());
        List<String> list3 = BedrockItemRenderer.mergeParticleEffects(bedrockItemDefinition == null ? List.of() : bedrockItemDefinition.getDefinitionParticleEffects(), list2);
        ModelPartsParticleSystem.emitDefinitionForRenderBones(class_310.method_1551(), class_13062, hashMap5, list3);
        ModelPartsParticleSystem.emitPendingForRenderBones(class_310.method_1551(), class_13062, hashMap5);
        ModelPartsParticleSystem.renderForArm(class_310.method_1551(), class_13062, class_45972, class_45872, n);
        for (Map.Entry<String, List<BedrockItemGeoModel.BedrockCube>> entry : arrayList) {
            String boneName = entry.getKey();
            List<BedrockItemGeoModel.BedrockCube> cubes = entry.getValue();
            class_2960 boneTexture = boneName == null ? null : BedrockItemRenderer.resolveBoneTexture(boneName, map8, map5, hashMap2, class_29602);
            if (boneTexture == null || cubes == null || cubes.isEmpty()) continue;
            class_1921 renderLayer = class_1921.method_23580(boneTexture);
            TextureAnim textureAnim = BedrockItemRenderer.resolveAnimForBone(map9.get(boneName), n3, n4, n5, bedrockItemDefinition, boneTexture, n8, n9, f);
            Matrix4f boneMatrix = hashMap3.get(boneName);
            if (boneMatrix == null) continue;
            boolean bl9 = Boolean.TRUE.equals(hashMap4.get(boneName));
            class_45872.method_22903();
            if (matrix4f8 != null && bl9) {
                class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f8);
                class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f8));
            }
            class_45872.method_23760().method_23761().mul(boneMatrix);
            class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f(boneMatrix));
            boolean cameraTransparent = BedrockItemRenderer.findBonePhysicsSpec(boneName, map10, map5) == null && BedrockItemRenderer.isCameraTransparentBone(boneName, map5, set);
            class_4588 class_45882 = class_45972.getBuffer(renderLayer);
            class_4587.class_4665 class_46652 = class_45872.method_23760();
            for (BedrockItemGeoModel.BedrockCube bedrockCube : cubes) {
                BedrockItemRenderer.renderCubeFaces(class_46652, class_45882, bedrockCube, n, n8, n9, 1.0f, 1.0f, textureAnim, bl3, cameraTransparent);
            }
            class_45872.method_22909();
        }
        for (Map.Entry<String, List<BedrockItemGeoModel.BedrockCube>> entry : arrayList) {
            String boneName = entry.getKey();
            List<BedrockItemGeoModel.BedrockCube> cubes = entry.getValue();
            int emission = boneName == null ? 0 : BedrockItemRenderer.getBoneEmission(map7, boneName);
            class_2960 boneTexture = boneName == null ? null : BedrockItemRenderer.resolveBoneTexture(boneName, map8, map5, hashMap2, class_29602);
            if (boneTexture == null || cubes == null || cubes.isEmpty() || emission <= 0) continue;
            class_1921 emissiveLayer = BedrockItemRenderer.emissiveOverlay(boneTexture, bl, bl2, bl7);
            TextureAnim textureAnim = BedrockItemRenderer.resolveAnimForBone(map9.get(boneName), n3, n4, n5, bedrockItemDefinition, boneTexture, n8, n9, f);
            Matrix4f matrix4f10 = hashMap3.get(boneName);
            if (matrix4f10 == null) continue;
            boolean pendulumSuppressed = Boolean.TRUE.equals(hashMap4.get(boneName));
            float f4 = BedrockItemRenderer.emissionToBaseIntensity(emission) * f3;
            float f5 = BedrockItemRenderer.emissionToExtraGlow(emission) * f3;
            if (f4 <= 0.0f && f5 <= 0.0f) continue;
            float f6 = Math.min(1.0f, f4);
            float f7 = bl7 ? 1.0f : Math.min(1.0f, (bl ? 0.42f : 0.58f) + f5 * 0.18f);
            class_45872.method_22903();
            if (matrix4f8 != null && pendulumSuppressed) {
                class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f8);
                class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f8));
            }
            class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f10);
            class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f10));
            boolean bl10 = BedrockItemRenderer.findBonePhysicsSpec(boneName, map10, map5) == null && BedrockItemRenderer.isCameraTransparentBone(boneName, map5, set);
            class_4588 class_45883 = class_45972.getBuffer(emissiveLayer);
            class_4587.class_4665 class_46653 = class_45872.method_23760();
            for (BedrockItemGeoModel.BedrockCube bedrockCube : cubes) {
                BedrockItemRenderer.renderCubeFaces(class_46653, class_45883, bedrockCube, 0xF000F0, n8, n9, f6, f7, textureAnim, bl3, bl10);
            }
            class_45872.method_22909();
            if (!bl) continue;
            float f8 = 1.0f + f5 * 1.5f;
            float f9 = 1.08f + f5 * 0.08f;
            float f10 = Math.min(1.0f, f6 * (0.6f + f5 * 0.4f));
            float f11 = Math.min(1.0f, 0.4f * f8);
            if (!bl7) {
                f9 += 0.1f + f5 * 0.05f;
                f10 = Math.min(1.0f, f10 * 1.06f);
                f11 = Math.min(1.0f, f11 * 1.18f);
            }
            BedrockItemRenderer.renderEmissiveCubeHalo(class_45872, class_45972, emissiveLayer, cubes, matrix4f8, matrix4f10, pendulumSuppressed, n8, n9, f10, f11, f9, textureAnim, bl3, bl10, bl7);
        }
    }

    private static void renderEmissiveCubeHalo(class_4587 class_45872, class_4597 class_45972, class_1921 class_19212, List<BedrockItemGeoModel.BedrockCube> list, Matrix4f matrix4f, Matrix4f matrix4f2, boolean bl, int n, int n2, float f, float f2, float f3, TextureAnim textureAnim, boolean bl2, boolean bl3, boolean bl4) {
        if (class_45872 != null && class_45972 != null && class_19212 != null && list != null && !list.isEmpty()) {
            float[] fArray;
            float[] fArray2;
            if (bl4) {
                float[] fArray3 = new float[1];
                fArray2 = fArray3;
                fArray3[0] = 1.0f;
            } else {
                float[] fArray4 = new float[3];
                fArray4[0] = 0.52f;
                fArray4[1] = 0.31f;
                fArray2 = fArray4;
                fArray4[2] = 0.17f;
            }
            float[] fArray5 = fArray2;
            if (bl4) {
                float[] fArray6 = new float[1];
                fArray = fArray6;
                fArray6[0] = 0.0f;
            } else {
                float[] fArray7 = new float[3];
                fArray7[0] = 0.0f;
                fArray7[1] = 0.08f;
                fArray = fArray7;
                fArray7[2] = 0.18f;
            }
            float[] fArray8 = fArray;
            class_4588 class_45882 = class_45972.getBuffer(class_19212);
            for (int i = 0; i < fArray5.length; ++i) {
                float f4 = Math.min(1.0f, f2 * fArray5[i]);
                if (f4 <= 0.0f) continue;
                class_45872.method_22903();
                if (matrix4f != null && bl) {
                    class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f);
                    class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f));
                }
                class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f2);
                class_45872.method_23760().method_23762().mul((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f2));
                float f5 = f3 + fArray8[i];
                class_45872.method_22905(f5, f5, f5);
                float f6 = bl4 ? 1.0f : 1.0f - (float)i * 0.08f;
                class_4587.class_4665 class_46652 = class_45872.method_23760();
                for (BedrockItemGeoModel.BedrockCube bedrockCube : list) {
                    BedrockItemRenderer.renderCubeFaces(class_46652, class_45882, bedrockCube, 0xF000F0, n, n2, Math.min(1.0f, f * f6), f4, textureAnim, bl2, bl3);
                }
                class_45872.method_22909();
            }
        }
    }

    private static Matrix4f applyAxolPhysicsIfNeeded(Matrix4f matrix4f, String string, BedrockAxolPhysicsStateMachine.FeedbackPose feedbackPose, Map<String, String> map, Matrix4f matrix4f2, Matrix4f matrix4f3) {
        boolean bl;
        boolean bl2;
        if (matrix4f == null || feedbackPose == null || string == null) {
            return matrix4f;
        }
        if ("fish".equals(string)) {
            return matrix4f;
        }
        boolean bl3 = bl2 = matrix4f2 != null && ("head".equals(string) || BedrockItemRenderer.isBoneOrDescendantOf(string, "head", map));
        if (bl2) {
            Matrix4f matrix4f4 = BedrockItemRenderer.buildAxolPhysicsTransform("head", feedbackPose);
            Matrix4f matrix4f5 = new Matrix4f((Matrix4fc)matrix4f2).invert();
            return new Matrix4f((Matrix4fc)matrix4f2).mul((Matrix4fc)matrix4f4).mul((Matrix4fc)matrix4f5).mul((Matrix4fc)matrix4f);
        }
        boolean bl4 = bl = matrix4f3 != null && ("body".equals(string) || BedrockItemRenderer.isBoneOrDescendantOf(string, "body", map));
        if (bl) {
            Matrix4f matrix4f6 = BedrockItemRenderer.buildAxolPhysicsTransform("body", feedbackPose);
            Matrix4f matrix4f7 = new Matrix4f((Matrix4fc)matrix4f3).invert();
            Matrix4f matrix4f8 = new Matrix4f((Matrix4fc)matrix4f3).mul((Matrix4fc)matrix4f6).mul((Matrix4fc)matrix4f7).mul((Matrix4fc)matrix4f);
            if ("leg1".equals(string) || "leg2".equals(string) || "leg4".equals(string) || "leg5".equals(string)) {
                matrix4f8.mul((Matrix4fc)BedrockItemRenderer.buildAxolPhysicsTransform(string, feedbackPose));
            }
            return matrix4f8;
        }
        if (!AXOL_PHYSICS_BONES.contains(string)) {
            return matrix4f;
        }
        Matrix4f matrix4f9 = BedrockItemRenderer.buildAxolPhysicsTransform(string, feedbackPose);
        return new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f9);
    }

    private static Matrix4f buildAxolPhysicsTransform(String string, BedrockAxolPhysicsStateMachine.FeedbackPose feedbackPose) {
        float f;
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = 1.0f;
        float f5 = 1.0f;
        float f6 = 1.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = feedbackPose.impactWave();
        float f13 = feedbackPose.armRaiseGate();
        float f14 = feedbackPose.landingPunch();
        float f15 = feedbackPose.walkPitchDeg();
        float f16 = 0.5f;
        if ("head".equals(string)) {
            f2 = 1.0f;
            f3 = 1.0f;
            f4 = 0.975f;
            f5 = 1.6575f;
            f6 = 0.45f;
            f16 = -0.5f;
            f10 = -f15 * 0.035f;
            f9 += feedbackPose.translateXUnits() * 31.2f;
        } else if ("body".equals(string) || "legs_front".equals(string) || "legs_rear".equals(string)) {
            f2 = 0.65f;
            f3 = 0.6f;
            f4 = 0.4f;
            f5 = 0.55f;
            f6 = 0.35f;
            f16 = 0.3f;
            f9 += feedbackPose.translateXUnits() * 12.0f;
        } else if ("leg1".equals(string) || "leg2".equals(string) || "leg4".equals(string) || "leg5".equals(string)) {
            f2 = 0.25f;
            f3 = 0.2f;
            f4 = 0.25f;
            f5 = 0.3f;
            f6 = 0.2f;
            f = !"leg2".equals(string) && !"leg5".equals(string) ? 1.0f : -1.0f;
            f7 = 24.0f * f13;
            f8 = 18.0f * f13 * f;
            f9 = 12.0f * f12 * f13 * f;
            f11 = 0.08f * f13;
        } else if ("tail".equals(string)) {
            f2 = 0.35f;
            f3 = 0.3f;
            f4 = 0.45f;
            f5 = 0.55f;
            f6 = 0.25f;
        }
        f = feedbackPose.translateXUnits();
        float f17 = feedbackPose.translateYUnits() * f2 + feedbackPose.highFallLiftUnits();
        float f18 = feedbackPose.translateZUnits() * f3;
        float f19 = feedbackPose.pitchDeg();
        if (("body".equals(string) || "legs_front".equals(string) || "legs_rear".equals(string)) && f19 < 0.0f) {
            f19 *= 0.32f;
        }
        float f20 = f19 * f4 + f7;
        float f21 = feedbackPose.rollDeg() * f5 + f15 * f16 + f9;
        float f22 = 1.0f + (feedbackPose.scaleX() - 1.0f) * f6;
        float f23 = 1.0f + (feedbackPose.scaleY() - 1.0f) * f6;
        float f24 = 1.0f + (feedbackPose.scaleZ() - 1.0f) * f6;
        return new Matrix4f().translate(f10 + f, f17, f18 + f11).rotateXYZ((float)Math.toRadians(f20), (float)Math.toRadians(f8), (float)Math.toRadians(f21)).scale(f22, f23, f24);
    }

    private static Matrix4f applyPufferPhysicsIfNeeded(Matrix4f matrix4f, String string, BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose, Map<String, String> map, Matrix4f matrix4f2) {
        if (matrix4f != null && string != null && feedbackPose != null) {
            Object object;
            boolean bl = BedrockItemRenderer.isBoneOrDescendantOf(string, "fish", map);
            if (!bl) {
                return matrix4f;
            }
            Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f);
            if (matrix4f2 != null) {
                object = BedrockItemRenderer.buildPufferBaseTransform(feedbackPose);
                Matrix4f matrix4f4 = new Matrix4f((Matrix4fc)matrix4f2).invert();
                matrix4f3 = new Matrix4f((Matrix4fc)matrix4f2).mul((Matrix4fc)object).mul((Matrix4fc)matrix4f4).mul((Matrix4fc)matrix4f3);
            }
            if (BedrockItemRenderer.isPufferFinBone(string)) {
                matrix4f3.mul((Matrix4fc)BedrockItemRenderer.buildPufferInverseScaleTransform(feedbackPose));
                matrix4f3.mul((Matrix4fc)BedrockItemRenderer.buildPufferFinTransform(string, feedbackPose));
                return matrix4f3;
            }
            object = null;
            if (BedrockItemRenderer.isBoneOrDescendantOf(string, "right_fin", map)) {
                object = "right_fin";
            } else if (BedrockItemRenderer.isBoneOrDescendantOf(string, "fin_right", map)) {
                object = "fin_right";
            } else if (BedrockItemRenderer.isBoneOrDescendantOf(string, "left_fin", map)) {
                object = "left_fin";
            } else if (BedrockItemRenderer.isBoneOrDescendantOf(string, "fin_left", map)) {
                object = "fin_left";
            }
            if (object != null) {
                matrix4f3.mul((Matrix4fc)BedrockItemRenderer.buildPufferInverseScaleTransform(feedbackPose));
                matrix4f3.mul((Matrix4fc)BedrockItemRenderer.buildPufferFinTransform((String)object, feedbackPose));
            }
            return matrix4f3;
        }
        return matrix4f;
    }

    private static Matrix4f applyCustomBonePhysicsIfNeeded(Matrix4f matrix4f, String string, Map<String, BedrockItemDefinition.BonePhysicsSpec> map, Matrix4f matrix4f2, Map<String, class_630> map2, Map<String, String> map3, Map<String, Matrix4f> map4, BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose, class_1306 class_13062, class_1268 class_12682, float f) {
        if (matrix4f != null && string != null && map != null && !map.isEmpty()) {
            List<String> list = BedrockItemRenderer.collectBoneLineage(string, map3);
            if (list.isEmpty()) {
                return matrix4f;
            }
            Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f);
            Matrix4f matrix4f4 = new Matrix4f();
            for (String string2 : list) {
                Matrix4f matrix4f5;
                Matrix4f matrix4f6;
                Matrix4f matrix4f7;
                BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec = map.get(string2);
                if (bonePhysicsSpec == null || bonePhysicsSpec.isEmpty() || (matrix4f7 = BedrockItemRenderer.computeWorldMatrix(string2, map2, map3, map4)) == null || (matrix4f6 = BedrockItemRenderer.computeCustomBonePhysicsDelta(matrix4f5 = new Matrix4f((Matrix4fc)matrix4f4).mul((Matrix4fc)matrix4f7), matrix4f7, matrix4f2, bonePhysicsSpec, feedbackPose, class_13062, class_12682, f, string2)) == null) continue;
                matrix4f3 = new Matrix4f((Matrix4fc)matrix4f6).mul((Matrix4fc)matrix4f3);
                matrix4f4 = new Matrix4f((Matrix4fc)matrix4f6).mul((Matrix4fc)matrix4f4);
            }
            return matrix4f3;
        }
        return matrix4f;
    }

    private static Matrix4f computeCustomBonePhysicsDelta(Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec, BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose, class_1306 class_13062, class_1268 class_12682, float f, String string) {
        if (matrix4f != null && matrix4f2 != null && bonePhysicsSpec != null && !bonePhysicsSpec.isEmpty()) {
            float f2;
            float f3;
            Matrix4f matrix4f4 = new Matrix4f((Matrix4fc)matrix4f);
            if (bonePhysicsSpec.enableCreaturePhysics() && feedbackPose != null) {
                matrix4f4.mul((Matrix4fc)BedrockItemRenderer.buildPufferBaseTransform(feedbackPose, bonePhysicsSpec));
            }
            if (bonePhysicsSpec.enableSlimePhysics() && Math.abs(f3 = SlimeInHandPhysics.get().sampleSlimeBlockDeform(class_12682, f)) > 1.0E-4f) {
                if (f3 > 0.0f) {
                    f3 *= bonePhysicsSpec.slimeFallScale();
                } else if (f3 < 0.0f) {
                    f3 *= bonePhysicsSpec.slimeImpactScale();
                }
                float f4 = SlimeInHandPhysics.horizontalScaleForDeform(f3);
                f2 = SlimeInHandPhysics.verticalScaleForDeform(f3);
                matrix4f4.scale(f4, f2, f4);
            }
            if (bonePhysicsSpec.pendulumMode() != null && bonePhysicsSpec.pendulumMode() != ToolTuning.PhysicsMode.NONE && class_13062 != null) {
                Matrix4f matrix4f5 = new Matrix4f((Matrix4fc)matrix4f2);
                Matrix4f matrix4f6 = matrix4f3 == null ? matrix4f5 : new Matrix4f((Matrix4fc)matrix4f3).mul((Matrix4fc)matrix4f5);
                f2 = BedrockItemRenderer.extractInheritedPitchDeg(matrix4f, matrix4f2);
                ToolTuning.PhysicsSettings physicsSettings = BedrockItemRenderer.applyRemainingPendulumLimit(bonePhysicsSpec.pendulumSettings(), f2);
                Matrix4f matrix4f7 = PendulumPhysics.get().computeAppliedMatrix(matrix4f6, bonePhysicsSpec.pendulumMode(), new Vector3f(), physicsSettings, class_13062, f, string, f2);
                if (matrix4f7 != null) {
                    matrix4f4.mul((Matrix4fc)matrix4f7);
                }
            }
            return matrix4f4.mul((Matrix4fc)new Matrix4f((Matrix4fc)matrix4f).invert());
        }
        return null;
    }

    private static float extractInheritedPitchDeg(Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f != null && matrix4f2 != null) {
            Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)new Matrix4f((Matrix4fc)matrix4f2).invert());
            Quaternionf quaternionf = matrix4f3.getUnnormalizedRotation(new Quaternionf());
            if (quaternionf.lengthSquared() < 1.0E-8f) {
                return 0.0f;
            }
            quaternionf.normalize();
            Vector3f vector3f = quaternionf.transform(new Vector3f(0.0f, 0.0f, 1.0f));
            float f = (float)Math.sqrt(vector3f.x * vector3f.x + vector3f.z * vector3f.z);
            return (float)Math.toDegrees(Math.atan2(-vector3f.y, f));
        }
        return 0.0f;
    }

    private static ToolTuning.PhysicsSettings applyRemainingPendulumLimit(ToolTuning.PhysicsSettings physicsSettings, float f) {
        boolean bl;
        if (physicsSettings == null) {
            return null;
        }
        boolean bl2 = bl = physicsSettings.limitDeg != null || physicsSettings.limitDegUp != null || physicsSettings.limitDegDown != null;
        if (!bl) {
            return physicsSettings;
        }
        ToolTuning.PhysicsSettings physicsSettings2 = physicsSettings.copy();
        float f2 = Math.abs(f);
        if (physicsSettings2.limitDeg != null) {
            physicsSettings2.limitDeg = Float.valueOf(Math.max(0.0f, physicsSettings2.limitDeg.floatValue() - f2));
        }
        if (physicsSettings2.limitDegUp != null) {
            physicsSettings2.limitDegUp = Float.valueOf(Math.max(0.0f, physicsSettings2.limitDegUp.floatValue() - f2));
        }
        if (physicsSettings2.limitDegDown != null) {
            physicsSettings2.limitDegDown = Float.valueOf(Math.max(0.0f, physicsSettings2.limitDegDown.floatValue() - f2));
        }
        return physicsSettings2;
    }

    private static List<String> collectBoneLineage(String string, Map<String, String> map) {
        if (string == null) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        String string2 = string;
        for (int i = 0; string2 != null && i < 64; ++i) {
            arrayList.add(string2);
            string2 = map != null ? map.get(string2) : null;
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    private static BedrockItemDefinition.BonePhysicsSpec findBonePhysicsSpec(String string, Map<String, BedrockItemDefinition.BonePhysicsSpec> map, Map<String, String> map2) {
        if (string != null && map != null && !map.isEmpty()) {
            String string2 = string;
            for (int i = 0; string2 != null && i < 64; ++i) {
                BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec = map.get(string2);
                if (bonePhysicsSpec != null) {
                    return bonePhysicsSpec;
                }
                string2 = map2 != null ? map2.get(string2) : null;
            }
            return null;
        }
        return null;
    }

    private static Map<String, BedrockItemDefinition.BonePhysicsSpec> mergeBonePhysics(Map<String, BedrockItemDefinition.BonePhysicsSpec> map, JsonElement jsonElement) {
        Map<String, BedrockItemDefinition.BonePhysicsSpec> map2 = BedrockItemDefinition.parseBonePhysics(jsonElement);
        if ((map == null || map.isEmpty()) && map2.isEmpty()) {
            return Map.of();
        }
        HashMap<String, BedrockItemDefinition.BonePhysicsSpec> hashMap = new HashMap<String, BedrockItemDefinition.BonePhysicsSpec>();
        if (map != null && !map.isEmpty()) {
            hashMap.putAll(map);
        }
        for (Map.Entry<String, BedrockItemDefinition.BonePhysicsSpec> entry : map2.entrySet()) {
            String string = entry.getKey();
            BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec = entry.getValue();
            if (string == null || string.isBlank() || bonePhysicsSpec == null) continue;
            BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec2 = (BedrockItemDefinition.BonePhysicsSpec)hashMap.get(string);
            hashMap.put(string, bonePhysicsSpec2 == null ? bonePhysicsSpec : bonePhysicsSpec2.merge(bonePhysicsSpec));
        }
        return hashMap;
    }

    private static boolean hasCreatureBonePhysics(Map<String, BedrockItemDefinition.BonePhysicsSpec> map) {
        if (map != null && !map.isEmpty()) {
            for (BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec : map.values()) {
                if (bonePhysicsSpec == null || !bonePhysicsSpec.enableCreaturePhysics()) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static Matrix4f buildPufferBaseTransform(BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose, BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec) {
        float f = feedbackPose.pitchDeg() * 1.19f * 1.3f;
        float f2 = 1.0f;
        if (bonePhysicsSpec != null) {
            if (f > 0.0f) {
                f *= bonePhysicsSpec.impactInclinationScale();
                f2 = bonePhysicsSpec.impactTranslationScale();
            } else if (f < 0.0f) {
                f *= bonePhysicsSpec.fallInclinationScale();
                f2 = bonePhysicsSpec.fallTranslationScale();
            }
        }
        float f3 = feedbackPose.translateXUnits();
        float f4 = feedbackPose.translateYUnits() * f2;
        float f5 = feedbackPose.translateZUnits() * f2;
        float f6 = feedbackPose.rollDeg() * 1.19f;
        float f7 = feedbackPose.scaleX();
        float f8 = feedbackPose.scaleY();
        float f9 = feedbackPose.scaleZ();
        return new Matrix4f().translate(f3, f4, f5).rotateXYZ((float)Math.toRadians(f), 0.0f, (float)Math.toRadians(f6)).scale(f7, f8, f9);
    }

    private static Matrix4f buildPufferBaseTransform(BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose) {
        return BedrockItemRenderer.buildPufferBaseTransform(feedbackPose, new BedrockItemDefinition.BonePhysicsSpec(true, false, ToolTuning.PhysicsMode.NONE, null, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f));
    }

    private static Matrix4f buildPufferInverseScaleTransform(BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose) {
        float f = feedbackPose.scaleX();
        float f2 = feedbackPose.scaleY();
        float f3 = feedbackPose.scaleZ();
        float f4 = Math.abs(f) > 1.0E-4f ? 1.0f / f : 1.0f;
        float f5 = Math.abs(f2) > 1.0E-4f ? 1.0f / f2 : 1.0f;
        float f6 = Math.abs(f3) > 1.0E-4f ? 1.0f / f3 : 1.0f;
        return new Matrix4f().scale(f4, f5, f6);
    }

    private static Matrix4f buildPufferFinTransform(String string, BedrockPufferPhysicsStateMachine.FeedbackPose feedbackPose) {
        boolean bl = "right_fin".equals(string) || "fin_right".equals(string);
        float f = bl ? 1.0f : -1.0f;
        float f2 = class_3532.method_15363((float)feedbackPose.finLift(), (float)0.0f, (float)1.0f);
        float f3 = f2 * f2 * (3.0f - 2.0f * f2);
        float f4 = 32.0f * f3 * 1.19f;
        float f5 = 6.0f * f3 * 1.19f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = (f4 + f5) * f;
        return new Matrix4f().translate(0.0f, 0.06f * f3, 0.085f * f3).rotateXYZ((float)Math.toRadians(f7), (float)Math.toRadians(f6), (float)Math.toRadians(f8));
    }

    private static boolean isPufferFinBone(String string) {
        return "right_fin".equals(string) || "fin_right".equals(string) || "left_fin".equals(string) || "fin_left".equals(string);
    }

    private static boolean isPufferModel(Map<String, class_630> map) {
        return map != null && !map.isEmpty() ? map.containsKey("fish") && (map.containsKey("right_fin") || map.containsKey("fin_right")) && (map.containsKey("left_fin") || map.containsKey("fin_left")) : false;
    }

    private static boolean isAxolModel(Map<String, class_630> map, Map<String, List<BedrockItemGeoModel.BedrockCube>> map2) {
        boolean bl;
        boolean bl2 = map != null && map.containsKey("head");
        boolean bl3 = map != null && (map.containsKey("body") || map.containsKey("body_root"));
        boolean bl4 = bl = map != null && (map.containsKey("legs_front") || map.containsKey("leg1"));
        if (bl2 && bl3 && bl) {
            return true;
        }
        return map2 != null && !map2.isEmpty() ? map2.containsKey("head") && (map2.containsKey("body") || map2.containsKey("body_root")) && (map2.containsKey("legs_front") || map2.containsKey("leg1")) : false;
    }

    private static boolean isBoneOrDescendantOf(String string, String string2, Map<String, String> map) {
        if (string == null || string2 == null) {
            return false;
        }
        if (string2.equals(string)) {
            return true;
        }
        String string3 = string;
        for (int i = 0; string3 != null && i < 128; ++i) {
            String string4 = string3 = map != null ? map.get(string3) : null;
            if (!string2.equals(string3)) continue;
            return true;
        }
        return false;
    }

    private static Matrix4f computeWorldMatrix(String string, Map<String, class_630> map, Map<String, String> map2, Map<String, Matrix4f> map3) {
        Matrix4f matrix4f;
        Matrix4f matrix4f2 = map3.get(string);
        if (matrix4f2 != null) {
            return matrix4f2;
        }
        class_630 class_6302 = map.get(string);
        if (class_6302 == null) {
            return null;
        }
        Matrix4f matrix4f3 = ModelPartMath.getLocalMatrix(class_6302);
        String string2 = map2.get(string);
        Matrix4f matrix4f4 = string2 != null ? ((matrix4f = BedrockItemRenderer.computeWorldMatrix(string2, map, map2, map3)) != null ? new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f3) : matrix4f3) : matrix4f3;
        map3.put(string, matrix4f4);
        return matrix4f4;
    }

    private static Map<String, class_2960> mergeDynamicBoneTextures(class_1799 class_17992, BedrockItemDefinition bedrockItemDefinition, Map<String, class_2960> map) {
        Map<String, class_2960> map2 = map == null ? Map.of() : map;
        if (bedrockItemDefinition != null && !bedrockItemDefinition.getDynamicBoneTextures().isEmpty()) {
            HashMap<String, class_2960> hashMap = new HashMap<String, class_2960>(map2);
            DynamicValueCache dynamicValueCache = new DynamicValueCache();
            for (BedrockItemDefinition.DynamicBoneTextureSpec dynamicBoneTextureSpec : bedrockItemDefinition.getDynamicBoneTextures()) {
                class_2960 class_29602;
                if (dynamicBoneTextureSpec == null || !dynamicBoneTextureSpec.isTextureType() || dynamicBoneTextureSpec.bone() == null || dynamicBoneTextureSpec.bone().isBlank()) continue;
                String string = BedrockItemRenderer.resolveDynamicKey(class_17992, dynamicBoneTextureSpec, dynamicValueCache);
                class_2960 class_29603 = class_29602 = string == null ? null : dynamicBoneTextureSpec.resourceMap().get(string);
                if (class_29602 == null) {
                    class_29602 = dynamicBoneTextureSpec.fallbackResource();
                }
                if (class_29602 == null) continue;
                hashMap.put(dynamicBoneTextureSpec.bone().trim(), class_29602);
            }
            return hashMap;
        }
        return map2;
    }

    private static class_2960 resolveDynamicGeoId(class_1799 class_17992, BedrockItemDefinition bedrockItemDefinition) {
        if (bedrockItemDefinition != null && !bedrockItemDefinition.getDynamicBoneTextures().isEmpty()) {
            class_2960 class_29602 = null;
            DynamicValueCache dynamicValueCache = new DynamicValueCache();
            for (BedrockItemDefinition.DynamicBoneTextureSpec dynamicBoneTextureSpec : bedrockItemDefinition.getDynamicBoneTextures()) {
                class_2960 class_29603;
                if (dynamicBoneTextureSpec == null || !dynamicBoneTextureSpec.isGeoType()) continue;
                String string = BedrockItemRenderer.resolveDynamicKey(class_17992, dynamicBoneTextureSpec, dynamicValueCache);
                class_2960 class_29604 = class_29603 = string == null ? null : dynamicBoneTextureSpec.resourceMap().get(string);
                if (class_29603 == null) {
                    class_29603 = dynamicBoneTextureSpec.fallbackResource();
                }
                if (class_29603 == null) continue;
                class_29602 = class_29603;
            }
            return class_29602;
        }
        return null;
    }

    private static String resolveDynamicKey(class_1799 class_17992, BedrockItemDefinition.DynamicBoneTextureSpec dynamicBoneTextureSpec, DynamicValueCache dynamicValueCache) {
        if (dynamicBoneTextureSpec == null) {
            return null;
        }
        if (dynamicBoneTextureSpec.usesAxolVariants()) {
            if (!dynamicValueCache.axolResolved) {
                dynamicValueCache.axolResolved = true;
                dynamicValueCache.axolVariant = BedrockItemRenderer.resolveAxolotlVariant(class_17992);
            }
            return dynamicValueCache.axolVariant;
        }
        return null;
    }

    private static String resolveAxolotlVariant(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_5762.class_5767 class_57672 = null;
            class_9279 class_92792 = (class_9279)class_17992.method_57824(class_9334.field_49610);
            var entityData = class_92792 != null ? class_92792.method_57461() : null;
            if (entityData != null && entityData.method_10545("Variant")) {
                class_57672 = class_5762.class_5767.method_47928(entityData.method_10550("Variant"));
            }
            if (class_57672 == null) {
                return null;
            }
            String variantName = class_57672.method_15434();
            if (variantName != null && !variantName.isBlank()) {
                return variantName;
            }
            String string = class_57672.method_33238();
            return string != null && !string.isBlank() ? string : null;
        }
        return null;
    }

    private static class_2960 resolveBoneTexture(String string, Map<String, class_2960> map, Map<String, String> map2, Map<String, class_2960> map3, class_2960 class_29602) {
        if (string == null) {
            return class_29602;
        }
        class_2960 class_29603 = BedrockItemRenderer.findTextureOverride(map, string);
        if (class_29603 != null) {
            map3.put(string, class_29603);
            return class_29603;
        }
        class_2960 class_29604 = map3.get(string);
        if (class_29604 != null) {
            return class_29604;
        }
        String string2 = string;
        for (int i = 0; string2 != null && i < 64; ++i) {
            class_2960 class_29605 = BedrockItemRenderer.findTextureOverride(map, string2);
            if (class_29605 != null) {
                map3.put(string, class_29605);
                return class_29605;
            }
            string2 = map2 != null ? map2.get(string2) : null;
        }
        if (class_29602 != null) {
            map3.put(string, class_29602);
        }
        return class_29602;
    }

    private static class_2960 findTextureOverride(Map<String, class_2960> map, String string) {
        if (map != null && !map.isEmpty() && string != null) {
            class_2960 class_29602;
            class_2960 class_29603 = map.get(string);
            if (class_29603 != null) {
                return class_29603;
            }
            String string2 = string.trim();
            if (!string2.equals(string) && (class_29602 = map.get(string2)) != null) {
                return class_29602;
            }
            for (Map.Entry entry : map.entrySet()) {
                String string3 = (String)entry.getKey();
                if (string3 == null || !string3.trim().equalsIgnoreCase(string2)) continue;
                return (class_2960)entry.getValue();
            }
            return null;
        }
        return null;
    }

    private static void renderCubeFaces(class_4587.class_4665 class_46652, class_4588 class_45882, BedrockItemGeoModel.BedrockCube bedrockCube, int n, int n2, int n3, float f, float f2, TextureAnim textureAnim, boolean bl, boolean bl2) {
        Map<class_2350, BedrockItemGeoModel.BedrockFace> map = bedrockCube.faces;
        if (map != null && !map.isEmpty()) {
            Map<class_2350, Boolean> map2 = BedrockItemRenderer.computeThinFaceSkips(bedrockCube, map);
            for (Map.Entry<class_2350, BedrockItemGeoModel.BedrockFace> entry : map.entrySet()) {
                class_2350 class_23502 = entry.getKey();
                BedrockItemGeoModel.BedrockFace bedrockFace = entry.getValue();
                if (class_23502 == null || bedrockFace == null || map2 != null && Boolean.TRUE.equals(map2.get(class_23502))) continue;
                BedrockItemRenderer.emitFace(class_46652, class_45882, class_23502, bedrockCube, bedrockFace, n, n2, n3, f, f2, textureAnim, bl, bl2);
            }
        }
    }

    private static Map<class_2350, Boolean> computeThinFaceSkips(BedrockItemGeoModel.BedrockCube bedrockCube, Map<class_2350, BedrockItemGeoModel.BedrockFace> map) {
        if (bedrockCube != null && map != null && !map.isEmpty()) {
            boolean bl;
            boolean bl2;
            float f = Math.abs(bedrockCube.x2 - bedrockCube.x1);
            float f2 = Math.abs(bedrockCube.y2 - bedrockCube.y1);
            float f3 = Math.abs(bedrockCube.z2 - bedrockCube.z1);
            float f4 = 0.001f;
            if (f > f4 && f2 > f4 && f3 > f4) {
                return null;
            }
            HashMap<class_2350, Boolean> hashMap = new HashMap<class_2350, Boolean>();
            boolean bl3 = f <= f4;
            int n = (bl3 ? 1 : 0) + ((bl2 = f2 <= f4) ? 1 : 0) + ((bl = f3 <= f4) ? 1 : 0);
            if (n >= 2) {
                class_2350 class_23502 = BedrockItemRenderer.findLargestFace(map);
                for (class_2350 class_23503 : map.keySet()) {
                    if (class_23503 == class_23502) continue;
                    hashMap.put(class_23503, true);
                }
                return hashMap;
            }
            if (bl3) {
                hashMap.put(class_2350.field_11043, true);
                hashMap.put(class_2350.field_11035, true);
                hashMap.put(class_2350.field_11036, true);
                hashMap.put(class_2350.field_11033, true);
                return hashMap;
            }
            if (bl2) {
                hashMap.put(class_2350.field_11043, true);
                hashMap.put(class_2350.field_11035, true);
                hashMap.put(class_2350.field_11034, true);
                hashMap.put(class_2350.field_11039, true);
                return hashMap;
            }
            if (bl) {
                hashMap.put(class_2350.field_11034, true);
                hashMap.put(class_2350.field_11039, true);
                hashMap.put(class_2350.field_11036, true);
                hashMap.put(class_2350.field_11033, true);
                return hashMap;
            }
            return hashMap;
        }
        return null;
    }

    private static class_2350 findLargestFace(Map<class_2350, BedrockItemGeoModel.BedrockFace> map) {
        class_2350 class_23502 = null;
        float f = -1.0f;
        for (Map.Entry<class_2350, BedrockItemGeoModel.BedrockFace> entry : map.entrySet()) {
            float f2;
            BedrockItemGeoModel.BedrockFace bedrockFace = entry.getValue();
            if (bedrockFace == null || !((f2 = Math.abs(bedrockFace.uSize * bedrockFace.vSize)) > f)) continue;
            f = f2;
            class_23502 = entry.getKey();
        }
        return class_23502;
    }

    private static void emitFace(class_4587.class_4665 class_46652, class_4588 class_45882, class_2350 class_23502, BedrockItemGeoModel.BedrockCube bedrockCube, BedrockItemGeoModel.BedrockFace bedrockFace, int n, int n2, int n3, float f, float f2, TextureAnim textureAnim, boolean bl, boolean bl2) {
        if (bedrockCube != null) {
            float f3 = bedrockCube.x1;
            float f4 = bedrockCube.y1;
            float f5 = bedrockCube.z1;
            float f6 = bedrockCube.x2;
            float f7 = bedrockCube.y2;
            float f8 = bedrockCube.z2;
            float f9 = bedrockFace.u;
            float f10 = bedrockFace.v;
            float f11 = bedrockFace.uSize;
            float f12 = bedrockFace.vSize;
            int n4 = (bedrockFace.rotation % 360 + 360) % 360;
            int n5 = n4 / 90 % 4;
            float f13 = n2 > 0 ? (f9 + f11) / (float)n2 : 0.0f;
            float f14 = n3 > 0 ? (f10 + f12) / (float)n3 : 0.0f;
            float f15 = n2 > 0 ? f9 / (float)n2 : 0.0f;
            float f16 = n3 > 0 ? f10 / (float)n3 : 0.0f;
            Vector3f vector3f = SCRATCH_NORMAL.set((float)class_23502.method_10148(), (float)class_23502.method_10164(), (float)class_23502.method_10165());
            if (bl) {
                vector3f.x = -vector3f.x;
            }
            if (!bedrockCube.mirror) {
                float f17 = f13;
                f13 = f15;
                f15 = f17;
            } else {
                vector3f.mul(-1.0f, 1.0f, 1.0f);
            }
            float[] fArray = BedrockItemRenderer.rotateUvs(f15, f16, f13, f14, n5);
            Vector3f[] vector3fArray = BedrockItemRenderer.getQuadVertices(class_23502, f3, f4, f5, f6, f7, f8, bedrockCube.mirror);
            if (vector3fArray != null) {
                float f18;
                Vector3f vector3f2;
                if (bl) {
                    vector3f2 = vector3fArray[1];
                    vector3fArray[1] = vector3fArray[3];
                    vector3fArray[3] = vector3f2;
                    float f19 = fArray[2];
                    f18 = fArray[3];
                    fArray[2] = fArray[6];
                    fArray[3] = fArray[7];
                    fArray[6] = f19;
                    fArray[7] = f18;
                }
                if (bedrockCube.hasRotation) {
                    Matrix4f rotationMatrix = SCRATCH_ROT_M4.identity().translate(bedrockCube.pivotX, bedrockCube.pivotY, bedrockCube.pivotZ).rotateZ(bedrockCube.rotZ).rotateY(bedrockCube.rotY).rotateX(bedrockCube.rotX).translate(-bedrockCube.pivotX, -bedrockCube.pivotY, -bedrockCube.pivotZ);
                    Matrix3f matrix3f = SCRATCH_ROT_M3.identity().rotateZ(bedrockCube.rotZ).rotateY(bedrockCube.rotY).rotateX(bedrockCube.rotX);
                    for (Vector3f vector3f3 : vector3fArray) {
                        rotationMatrix.transformPosition(vector3f3);
                    }
                    matrix3f.transform(vector3f);
                }
                if (!bl2 || BedrockItemRenderer.isInsideVisibleFace(class_46652, vector3fArray, vector3f, bl)) {
                    for (int i = 0; i < 4; ++i) {
                        Vector3f vector3f4 = vector3fArray[i];
                        f18 = class_3532.method_15363((float)fArray[i * 2], (float)0.0f, (float)1.0f);
                        float f20 = class_3532.method_15363((float)fArray[i * 2 + 1], (float)0.0f, (float)1.0f);
                        if (textureAnim != null) {
                            f20 = (f20 + (float)textureAnim.frameIndex) * textureAnim.invFrames;
                        }
                        BedrockItemRenderer.addVertex(class_46652, class_45882, vector3f4.x, vector3f4.y, vector3f4.z, f18, f20, n, vector3f.x, vector3f.y, vector3f.z, f, f2);
                    }
                }
            }
        }
    }

    private static boolean isInsideVisibleFace(class_4587.class_4665 class_46652, Vector3f[] vector3fArray, Vector3f vector3f, boolean bl) {
        if (class_46652 != null && vector3fArray != null && vector3fArray.length >= 4) {
            Vector3f vector3f2;
            Vector3f vector3f3;
            Vector3f[] vector3fArray2 = new Vector3f[4];
            Vector3f vector3f4 = new Vector3f();
            int n = 0;
            for (int i = 0; i < 4; ++i) {
                vector3f3 = vector3fArray[i];
                if (vector3f3 == null) continue;
                vector3f2 = new Vector3f((Vector3fc)vector3f3);
                class_46652.method_23761().transformPosition(vector3f2);
                vector3fArray2[i] = vector3f2;
                vector3f4.add((Vector3fc)vector3f2);
                ++n;
            }
            if (n < 3) {
                return true;
            }
            vector3f4.mul(1.0f / (float)n);
            Vector3f vector3f5 = null;
            if (vector3fArray2[0] != null && vector3fArray2[1] != null && vector3fArray2[2] != null) {
                vector3f3 = new Vector3f((Vector3fc)vector3fArray2[1]).sub((Vector3fc)vector3fArray2[0]);
                vector3f2 = new Vector3f((Vector3fc)vector3fArray2[2]).sub((Vector3fc)vector3fArray2[0]);
                vector3f5 = vector3f3.cross((Vector3fc)vector3f2, new Vector3f());
            }
            if ((vector3f5 == null || vector3f5.lengthSquared() <= 1.0E-6f) && vector3f != null) {
                vector3f5 = new Vector3f((Vector3fc)vector3f);
                class_46652.method_23762().transform(vector3f5);
            }
            if (!(vector3f5.lengthSquared() <= 1.0E-6f) && !(vector3f4.lengthSquared() <= 1.0E-6f)) {
                vector3f5.normalize();
                vector3f3 = new Vector3f((Vector3fc)vector3f4).negate().normalize();
                float f = vector3f5.dot((Vector3fc)vector3f3);
                return f >= 0.0f;
            }
            return true;
        }
        return true;
    }

    private static Vector3f[] getQuadVertices(class_2350 class_23502, float f, float f2, float f3, float f4, float f5, float f6, boolean bl) {
        if (class_23502 == null) {
            return null;
        }
        Vector3f vector3f = SCRATCH_CORNERS[0].set(f, f2, f3);
        Vector3f vector3f2 = SCRATCH_CORNERS[1].set(f, f2, f6);
        Vector3f vector3f3 = SCRATCH_CORNERS[2].set(f, f5, f3);
        Vector3f vector3f4 = SCRATCH_CORNERS[3].set(f, f5, f6);
        Vector3f vector3f5 = SCRATCH_CORNERS[4].set(f4, f5, f3);
        Vector3f vector3f6 = SCRATCH_CORNERS[5].set(f4, f5, f6);
        Vector3f vector3f7 = SCRATCH_CORNERS[6].set(f4, f2, f3);
        Vector3f vector3f8 = SCRATCH_CORNERS[7].set(f4, f2, f6);
        Vector3f[] vector3fArray = SCRATCH_QUAD;
        switch (class_23502) {
            case field_11039: {
                if (bl) {
                    BedrockItemRenderer.setQuad(vector3fArray, vector3f5, vector3f6, vector3f8, vector3f7);
                    break;
                }
                BedrockItemRenderer.setQuad(vector3fArray, vector3f4, vector3f3, vector3f, vector3f2);
                break;
            }
            case field_11034: {
                if (bl) {
                    BedrockItemRenderer.setQuad(vector3fArray, vector3f4, vector3f3, vector3f, vector3f2);
                    break;
                }
                BedrockItemRenderer.setQuad(vector3fArray, vector3f5, vector3f6, vector3f8, vector3f7);
                break;
            }
            case field_11043: {
                BedrockItemRenderer.setQuad(vector3fArray, vector3f3, vector3f5, vector3f7, vector3f);
                break;
            }
            case field_11035: {
                BedrockItemRenderer.setQuad(vector3fArray, vector3f6, vector3f4, vector3f2, vector3f8);
                break;
            }
            case field_11036: {
                if (bl) {
                    BedrockItemRenderer.setQuad(vector3fArray, vector3f, vector3f7, vector3f8, vector3f2);
                    break;
                }
                BedrockItemRenderer.setQuad(vector3fArray, vector3f4, vector3f6, vector3f5, vector3f3);
                break;
            }
            case field_11033: {
                if (bl) {
                    BedrockItemRenderer.setQuad(vector3fArray, vector3f4, vector3f6, vector3f5, vector3f3);
                    break;
                }
                BedrockItemRenderer.setQuad(vector3fArray, vector3f, vector3f7, vector3f8, vector3f2);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected enum value");
            }
        }
        return vector3fArray;
    }

    private static void setQuad(Vector3f[] vector3fArray, Vector3f vector3f, Vector3f vector3f2, Vector3f vector3f3, Vector3f vector3f4) {
        vector3fArray[0] = vector3f;
        vector3fArray[1] = vector3f2;
        vector3fArray[2] = vector3f3;
        vector3fArray[3] = vector3f4;
    }

    private static float[] rotateUvs(float f, float f2, float f3, float f4, int n) {
        float[] fArray = SCRATCH_UVS;
        switch (n & 3) {
            case 0: {
                fArray[0] = f;
                fArray[1] = f2;
                fArray[2] = f3;
                fArray[3] = f2;
                fArray[4] = f3;
                fArray[5] = f4;
                fArray[6] = f;
                fArray[7] = f4;
                break;
            }
            case 1: {
                fArray[0] = f3;
                fArray[1] = f2;
                fArray[2] = f3;
                fArray[3] = f4;
                fArray[4] = f;
                fArray[5] = f4;
                fArray[6] = f;
                fArray[7] = f2;
                break;
            }
            case 2: {
                fArray[0] = f3;
                fArray[1] = f4;
                fArray[2] = f;
                fArray[3] = f4;
                fArray[4] = f;
                fArray[5] = f2;
                fArray[6] = f3;
                fArray[7] = f2;
                break;
            }
            default: {
                fArray[0] = f;
                fArray[1] = f4;
                fArray[2] = f;
                fArray[3] = f2;
                fArray[4] = f3;
                fArray[5] = f2;
                fArray[6] = f3;
                fArray[7] = f4;
            }
        }
        return fArray;
    }

    private static void addVertex(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, int n, float f6, float f7, float f8, float f9, float f10) {
        int n2 = BedrockItemRenderer.scaleToColor(f9);
        int n3 = BedrockItemRenderer.scaleToColor(f10);
        class_45882.method_22918(class_46652.method_23761(), f, f2, f3).method_1336(n2, n2, n2, n3).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, f6, f7, f8);
    }

    private static boolean isPendulumSuppressed(String string, Map<String, String> map, Set<String> set) {
        if (string != null && set != null && !set.isEmpty()) {
            String string2 = string;
            for (int i = 0; string2 != null && i < 64; ++i) {
                if (set.contains(string2)) {
                    return true;
                }
                string2 = map != null ? map.get(string2) : null;
            }
            return false;
        }
        return false;
    }

    private static boolean isCameraTransparentBone(String string, Map<String, String> map, Set<String> set) {
        if (string != null && set != null && !set.isEmpty()) {
            String string2 = string;
            for (int i = 0; string2 != null && i < 64; ++i) {
                if (set.contains(string2)) {
                    return true;
                }
                string2 = map != null ? map.get(string2) : null;
            }
            return false;
        }
        return false;
    }

    private static int getBoneEmission(Map<String, Integer> map, String string) {
        if (map != null && !map.isEmpty() && string != null) {
            Integer n = map.get(string);
            return n == null ? 0 : n;
        }
        return 0;
    }

    private static float emissionToBaseIntensity(int n) {
        if (n <= 0) {
            return 0.0f;
        }
        return n <= 15 ? (float)n / 15.0f : 1.0f;
    }

    private static float emissionToExtraGlow(int n) {
        return n <= 15 ? 0.0f : Math.min(1.0f, (float)(n - 15) / 85.0f);
    }

    private static int scaleToColor(float f) {
        if (f <= 0.0f) {
            return 0;
        }
        return f >= 1.0f ? 255 : Math.max(0, Math.min(255, Math.round(255.0f * f)));
    }

    private static class_1921 emissiveOverlay(class_2960 class_29602, boolean bl, boolean bl2, boolean bl3) {
        if (bl3) {
            return class_1921.method_42600((class_2960)class_29602);
        }
        EmissiveKey emissiveKey = new EmissiveKey(class_29602, bl, bl2);
        class_1921 class_19212 = EMISSIVE_OVERLAY_TYPES.get(emissiveKey);
        if (class_19212 != null) {
            return class_19212;
        }
        class_1921 class_19213 = BedrockItemRenderer.tryCreateEmissiveOverlay(class_29602, bl, bl2);
        if (class_19213 == null) {
            class_19213 = class_1921.method_42600((class_2960)class_29602);
        }
        EMISSIVE_OVERLAY_TYPES.put(emissiveKey, class_19213);
        return class_19213;
    }

    private static class_1921 tryCreateEmissiveOverlay(class_2960 class_29602, boolean bl, boolean bl2) {
        class_1044 class_10443;
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && (class_10443 = class_3102.method_1531().method_4619(class_29602)) != null) {
            class_10443.method_65923(bl ? class_9851.field_52394 : class_9851.field_52395, bl);
            class_10443.method_65924(bl2);
        }
        return bl ? class_1921.method_23026((class_2960)class_29602) : class_1921.method_42600((class_2960)class_29602);
    }

    private static boolean isShaderPackInUse() {
        long l = System.currentTimeMillis();
        if (l - irisPollStampMs >= 250L) {
            irisPollStampMs = l;
            irisShaderPackCached = BedrockItemRenderer.queryIrisShaderPackInUse();
        }
        return irisShaderPackCached;
    }

    private static boolean queryIrisShaderPackInUse() {
        if (!irisResolved) {
            BedrockItemRenderer.resolveIrisApi();
        }
        if (!irisPresent) {
            return false;
        }
        try {
            Boolean bl;
            Object object = irisIsShaderPackInUse.invoke(irisApi, new Object[0]);
            return object instanceof Boolean && (bl = (Boolean)object) != false;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static void resolveIrisApi() {
        irisResolved = true;
        for (String string : new String[]{"net.irisshaders.iris.api.v0.IrisApi", "net.coderbot.iris.api.v0.IrisApi"}) {
            try {
                Class<?> clazz = Class.forName(string);
                Object object = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                irisIsShaderPackInUse = clazz.getMethod("isShaderPackInUse", new Class[0]);
                irisApi = object;
                irisPresent = true;
                return;
            }
            catch (Throwable throwable) {
            }
        }
        irisPresent = false;
    }

    private static TextureAnim resolveTextureAnim(int n, int n2, int n3, int n4, int n5, int n6, int n7, float f) {
        if (n > 1 && n2 > 0 && n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0) {
            int n8 = n3 > 0 ? n3 : n7;
            int n9 = n8 * n;
            if (n5 != n9) {
                return null;
            }
            int n10 = (int)(f / (float)n2) % n;
            float f2 = 1.0f / (float)n;
            return new TextureAnim(n10, f2);
        }
        return null;
    }

    private static TextureAnim resolveAnimForBone(BedrockItemDefinition.TextureAnimSpec textureAnimSpec, int n, int n2, int n3, BedrockItemDefinition bedrockItemDefinition, class_2960 class_29602, int n4, int n5, float f) {
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        if (textureAnimSpec != null) {
            n6 = textureAnimSpec.frames;
            n7 = textureAnimSpec.frameTime;
            n8 = textureAnimSpec.frameHeight;
        }
        int[] nArray = bedrockItemDefinition != null ? bedrockItemDefinition.getTexturePixelSize(class_29602) : null;
        int n9 = nArray != null ? nArray[0] : 0;
        int n10 = nArray != null ? nArray[1] : 0;
        return BedrockItemRenderer.resolveTextureAnim(n6, n7, n8, n9, n10, n4, n5, f);
    }

    private static List<String> mergeParticleEffects(List<String> list, List<String> list2) {
        if (list != null && !list.isEmpty() || list2 != null && !list2.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            if (list != null && !list.isEmpty()) {
                arrayList.addAll(list);
            }
            if (list2 != null && !list2.isEmpty()) {
                arrayList.addAll(list2);
            }
            return arrayList;
        }
        return List.of();
    }

    static {
        irisPollStampMs = Long.MIN_VALUE;
    }

    static final class TextureAnim {
        final int frameIndex;
        final float invFrames;

        TextureAnim(int n, float f) {
            this.frameIndex = n;
            this.invFrames = f;
        }

        @Override
        public final String toString() {
            return "TextureAnim[" + "frameIndex=" + this.frameIndex + ", " + "invFrames=" + this.invFrames + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.frameIndex, this.invFrames);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TextureAnim other)) return false;
            return java.util.Objects.deepEquals(this.frameIndex, other.frameIndex)
                && java.util.Objects.deepEquals(this.invFrames, other.invFrames);
        }

        public int frameIndex() {
            return this.frameIndex;
        }

        public float invFrames() {
            return this.invFrames;
        }
    }

    static final class DynamicValueCache {
        boolean axolResolved;
        String axolVariant;

        DynamicValueCache() {
        }
    }

    record EmissiveKey(class_2960 texture, boolean blur, boolean clamp) {
    }
}
