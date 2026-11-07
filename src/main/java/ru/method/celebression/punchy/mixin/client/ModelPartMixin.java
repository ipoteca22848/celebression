package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_4587;
import net.minecraft.class_630;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.access.TransformablePart;

@Mixin(value={class_630.class})
public class ModelPartMixin
implements TransformablePart {
    @Unique
    private Matrix4f punchy$explicitMatrix;
    @Unique
    private boolean punchy$hasDirectPoseBase;
    @Unique
    private float punchy$directPoseBaseX;
    @Unique
    private float punchy$directPoseBaseY;
    @Unique
    private float punchy$directPoseBaseZ;
    @Unique
    private float punchy$directPoseBaseXRot;
    @Unique
    private float punchy$directPoseBaseYRot;
    @Unique
    private float punchy$directPoseBaseZRot;

    @Override
    public void punchy$setExplicitTransform(Matrix4f matrix4f) {
        this.punchy$explicitMatrix = matrix4f;
    }

    @Override
    public Matrix4f punchy$getExplicitTransform() {
        return this.punchy$explicitMatrix;
    }

    @Override
    public void punchy$setDirectPoseBase(float f, float f2, float f3, float f4, float f5, float f6) {
        this.punchy$hasDirectPoseBase = true;
        this.punchy$directPoseBaseX = f;
        this.punchy$directPoseBaseY = f2;
        this.punchy$directPoseBaseZ = f3;
        this.punchy$directPoseBaseXRot = f4;
        this.punchy$directPoseBaseYRot = f5;
        this.punchy$directPoseBaseZRot = f6;
    }

    @Override
    public boolean punchy$hasDirectPoseBase() {
        return this.punchy$hasDirectPoseBase;
    }

    @Override
    public float punchy$getDirectPoseBaseX() {
        return this.punchy$directPoseBaseX;
    }

    @Override
    public float punchy$getDirectPoseBaseY() {
        return this.punchy$directPoseBaseY;
    }

    @Override
    public float punchy$getDirectPoseBaseZ() {
        return this.punchy$directPoseBaseZ;
    }

    @Override
    public float punchy$getDirectPoseBaseXRot() {
        return this.punchy$directPoseBaseXRot;
    }

    @Override
    public float punchy$getDirectPoseBaseYRot() {
        return this.punchy$directPoseBaseYRot;
    }

    @Override
    public float punchy$getDirectPoseBaseZRot() {
        return this.punchy$directPoseBaseZRot;
    }

    @Override
    public void punchy$clearDirectPoseBase() {
        this.punchy$hasDirectPoseBase = false;
        this.punchy$directPoseBaseX = 0.0f;
        this.punchy$directPoseBaseY = 0.0f;
        this.punchy$directPoseBaseZ = 0.0f;
        this.punchy$directPoseBaseXRot = 0.0f;
        this.punchy$directPoseBaseYRot = 0.0f;
        this.punchy$directPoseBaseZRot = 0.0f;
    }

    @Inject(method={"rotate(Lnet/minecraft/client/util/math/MatrixStack;)V"}, at={@At(value="TAIL")})
    private void punchy$applyExplicitTransformAtTail(class_4587 class_45872, CallbackInfo callbackInfo) {
        if (this.punchy$explicitMatrix != null) {
            class_630 class_6302 = (class_630)(Object)this;
            Matrix4f matrix4f = new Matrix4f((Matrix4fc)this.punchy$explicitMatrix);
            Vector3f vector3f = matrix4f.getTranslation(new Vector3f());
            vector3f.div(16.0f);
            matrix4f.setTranslation((Vector3fc)vector3f);
            Matrix4f matrix4f2 = new Matrix4f().translate(class_6302.field_3657 / 16.0f, class_6302.field_3656 / 16.0f, class_6302.field_3655 / 16.0f).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
            Matrix4f matrix4f3 = matrix4f2.invert(new Matrix4f()).mul((Matrix4fc)matrix4f);
            class_45872.method_34425(matrix4f3);
        }
    }
}
