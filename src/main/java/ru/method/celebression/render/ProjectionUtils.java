package ru.method.celebression.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Generated;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.api.mixins.accessors.IGameRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class ProjectionUtils
implements MinecraftClientAccess {
    public static final AtomicReference<Matrix4f> amr = new AtomicReference<Matrix4f>(new Matrix4f());
    public static final AtomicReference<Matrix4f> ams = new AtomicReference<Matrix4f>(new Matrix4f());
    public static final AtomicReference<Matrix4f> amt = new AtomicReference<Matrix4f>(new Matrix4f());
    private static final ThreadLocal<ProjectedPoint> amu = ThreadLocal.withInitial(ProjectedPoint::new);

    public static class_243 o(class_243 class_2432) {
        RenderSystem.assertOnRenderThread();
        class_4184 class_41842 = ProjectionUtils.aiG.method_1561().field_4686;
        ProjectedPoint projectedPoint = amu.get();
        int n = aiG.method_22683().method_4489();
        int n2 = aiG.method_22683().method_4506();
        if (projectedPoint.amz != n || projectedPoint.amA != n2) {
            GL11.glGetIntegerv((int)2978, (int[])projectedPoint.amv);
            projectedPoint.amz = n;
            projectedPoint.amA = n2;
        }
        double d = class_2432.field_1352 - class_41842.method_19326().field_1352;
        double d2 = class_2432.field_1351 - class_41842.method_19326().field_1351;
        double d3 = class_2432.field_1350 - class_41842.method_19326().field_1350;
        Matrix4f matrix4f = amr.get();
        Matrix4f matrix4f2 = ams.get();
        Matrix4f matrix4f3 = amt.get();
        projectedPoint.amx.set((float)d, (float)d2, (float)d3, 1.0f).mul((Matrix4fc)matrix4f);
        projectedPoint.amy.set((Matrix4fc)matrix4f2).mul((Matrix4fc)matrix4f3).project(projectedPoint.amx.x(), projectedPoint.amx.y(), projectedPoint.amx.z(), projectedPoint.amv, projectedPoint.amw);
        double d4 = aiG.method_22683().method_4495();
        return new class_243((double)projectedPoint.amw.x / d4, (double)((float)aiG.method_22683().method_4507() - projectedPoint.amw.y) / d4, (double)projectedPoint.amw.z);
    }

    public static double c(class_243 class_2432, double d) {
        class_243 class_2433 = ProjectionUtils.aiG.field_1773.method_19418().method_19326();
        double d2 = class_2433.method_1022(class_2432);
        double d3 = ((IGameRenderer)ProjectionUtils.aiG.field_1773).getFov$drug(ProjectionUtils.aiG.field_1773.method_19418(), aiG.method_61966().method_60637(true), true);
        return Math.max(10.0, 1000.0 / d2) * (d / 30.0) / (d3 == 70.0 ? 1.0 : d3 / 70.0);
    }

    @Generated
    private ProjectionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static final class ProjectedPoint {
        final int[] amv = new int[4];
        final Vector3f amw = new Vector3f();
        final Vector4f amx = new Vector4f();
        final Matrix4f amy = new Matrix4f();
        int amz = -1;
        int amA = -1;

        private ProjectedPoint() {
        }
    }
}

