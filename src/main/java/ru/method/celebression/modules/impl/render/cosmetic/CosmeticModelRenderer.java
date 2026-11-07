package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_7833;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelCube;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelQuad;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelVertex;
import ru.method.celebression.modules.impl.render.cosmetic.BoneTransformSet;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;

public final class CosmeticModelRenderer {
    private static final float W = 0.0625f;
    private static final Vector4f aA = new Vector4f();
    private static final Vector3f aB = new Vector3f();
    private static final Quaternionf aC = new Quaternionf();

    private CosmeticModelRenderer() {
    }

    public static void a(CosmeticModel cosmeticModel, class_4587 class_45872, class_4597 class_45972, int n, int n2, BoneTransformSet boneTransformSet) {
        if (cosmeticModel == null || cosmeticModel.isEmpty() || cosmeticModel.texture() == null) {
            return;
        }
        class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23578((class_2960)cosmeticModel.texture()));
        for (BakedModelBone bakedModelBone : cosmeticModel.bq()) {
            CosmeticModelRenderer.a(bakedModelBone, class_45872, class_45882, n, n2, boneTransformSet);
        }
    }

    private static void a(BakedModelBone bakedModelBone, class_4587 class_45872, class_4588 class_45882, int n, int n2, BoneTransformSet boneTransformSet) {
        class_45872.method_22903();
        if (boneTransformSet != null && boneTransformSet.v(bakedModelBone.aX)) {
            class_45872.method_46416(-boneTransformSet.w(bakedModelBone.aX) * 0.0625f, boneTransformSet.x(bakedModelBone.aX) * 0.0625f, boneTransformSet.y(bakedModelBone.aX) * 0.0625f);
        }
        CosmeticModelRenderer.a(class_45872, bakedModelBone.aY, bakedModelBone.aZ, bakedModelBone.bA);
        float f = bakedModelBone.bB;
        float f2 = bakedModelBone.bC;
        float f3 = bakedModelBone.bD;
        if (boneTransformSet != null) {
            f += (float)Math.toRadians(boneTransformSet.s(bakedModelBone.aX));
            f2 += (float)Math.toRadians(boneTransformSet.t(bakedModelBone.aX));
            f3 += (float)Math.toRadians(boneTransformSet.u(bakedModelBone.aX));
        }
        CosmeticModelRenderer.b(class_45872, f, f2, f3);
        if (boneTransformSet != null && boneTransformSet.z(bakedModelBone.aX)) {
            class_45872.method_22905(boneTransformSet.aa(bakedModelBone.aX), boneTransformSet.ab(bakedModelBone.aX), boneTransformSet.ac(bakedModelBone.aX));
        }
        CosmeticModelRenderer.a(class_45872, -bakedModelBone.aY, -bakedModelBone.aZ, -bakedModelBone.bA);
        for (BakedModelCube object : bakedModelBone.bE) {
            class_45872.method_22903();
            CosmeticModelRenderer.a(object, class_45872, class_45882, n, n2);
            class_45872.method_22909();
        }
        for (BakedModelBone bakedModelBone2 : bakedModelBone.bF) {
            CosmeticModelRenderer.a(bakedModelBone2, class_45872, class_45882, n, n2, boneTransformSet);
        }
        class_45872.method_22909();
    }

    private static void a(BakedModelCube bakedModelCube, class_4587 class_45872, class_4588 class_45882, int n, int n2) {
        CosmeticModelRenderer.a(class_45872, bakedModelCube.bG, bakedModelCube.bH, bakedModelCube.bI);
        if (bakedModelCube.bL != 0.0f || bakedModelCube.bK != 0.0f || bakedModelCube.bJ != 0.0f) {
            class_45872.method_22907(aC.rotationZYX(bakedModelCube.bL, bakedModelCube.bK, bakedModelCube.bJ));
        }
        CosmeticModelRenderer.a(class_45872, -bakedModelCube.bG, -bakedModelCube.bH, -bakedModelCube.bI);
        class_4587.class_4665 class_46652 = class_45872.method_23760();
        Matrix4f matrix4f = class_46652.method_23761();
        Matrix3f matrix3f = class_46652.method_23762();
        int n3 = n2 >> 24 & 0xFF;
        int n4 = n2 >> 16 & 0xFF;
        int n5 = n2 >> 8 & 0xFF;
        int n6 = n2 & 0xFF;
        for (BakedModelQuad bakedModelQuad : bakedModelCube.bP) {
            Vector3f vector3f = aB.set(bakedModelQuad.bZ, bakedModelQuad.ca, bakedModelQuad.cb);
            vector3f.mul((Matrix3fc)matrix3f);
            if ((bakedModelCube.bN == 0.0f || bakedModelCube.bO == 0.0f) && vector3f.x() < 0.0f) {
                vector3f.mul(-1.0f, 1.0f, 1.0f);
            }
            if ((bakedModelCube.bM == 0.0f || bakedModelCube.bO == 0.0f) && vector3f.y() < 0.0f) {
                vector3f.mul(1.0f, -1.0f, 1.0f);
            }
            if ((bakedModelCube.bM == 0.0f || bakedModelCube.bN == 0.0f) && vector3f.z() < 0.0f) {
                vector3f.mul(1.0f, 1.0f, -1.0f);
            }
            for (BakedModelVertex bakedModelVertex : bakedModelQuad.bY) {
                Vector4f vector4f = aA.set(bakedModelVertex.cc, bakedModelVertex.cd, bakedModelVertex.ce, 1.0f);
                vector4f.mul((Matrix4fc)matrix4f);
                class_45882.method_22912(vector4f.x(), vector4f.y(), vector4f.z()).method_1336(n4, n5, n6, n3).method_22913(bakedModelVertex.u, bakedModelVertex.v).method_22922(class_4608.field_21444).method_60803(n).method_22914(vector3f.x(), vector3f.y(), vector3f.z());
            }
        }
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3) {
        class_45872.method_46416(f * 0.0625f, f2 * 0.0625f, f3 * 0.0625f);
    }

    private static void b(class_4587 class_45872, float f, float f2, float f3) {
        if (f3 != 0.0f) {
            class_45872.method_22907(class_7833.field_40718.rotation(f3));
        }
        if (f2 != 0.0f) {
            class_45872.method_22907(class_7833.field_40716.rotation(f2));
        }
        if (f != 0.0f) {
            class_45872.method_22907(class_7833.field_40714.rotation(f));
        }
    }
}

