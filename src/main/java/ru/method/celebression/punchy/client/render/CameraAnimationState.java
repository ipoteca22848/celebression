package ru.method.celebression.punchy.client.render;

import net.minecraft.class_630;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.access.TransformablePart;

public final class CameraAnimationState {
    public static float CAMERA_POS_SCALE = 0.0625f;
    public static float CAMERA_ROT_SCALE = 1.0f;
    private static final float EPSILON = 1.0E-4f;
    private static final Quaternionf ROTATION = new Quaternionf();
    private static final Vector3f OFFSET = new Vector3f();
    private static boolean active;

    private CameraAnimationState() {
    }

    public static void clear() {
        ROTATION.identity();
        OFFSET.zero();
        active = false;
    }

    public static void update(class_630 class_6302) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform == null) {
                CameraAnimationState.clear();
            } else {
                Matrix4f matrix4f = new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
                Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f).invert();
                Matrix4f matrix4f3 = matrix4f2.mul((Matrix4fc)explicitTransform, new Matrix4f());
                Vector3f vector3f = matrix4f3.getTranslation(new Vector3f()).mul(CAMERA_POS_SCALE);
                vector3f.x = -vector3f.x;
                Quaternionf quaternionf = new Quaternionf();
                matrix4f3.getUnnormalizedRotation(quaternionf);
                quaternionf.normalize();
                Vector3f vector3f2 = quaternionf.getEulerAnglesZYX(new Vector3f());
                vector3f2.x = -vector3f2.x;
                float f = vector3f2.y;
                vector3f2.y = vector3f2.z;
                vector3f2.z = f;
                vector3f2.y = -vector3f2.y;
                vector3f2.z = -vector3f2.z;
                vector3f2.mul(CAMERA_ROT_SCALE);
                quaternionf.rotationZYX(vector3f2.z, vector3f2.y, vector3f2.x);
                ROTATION.set((Quaternionfc)quaternionf);
                OFFSET.set((Vector3fc)vector3f);
                active = vector3f.lengthSquared() > 1.0E-4f || Math.abs(CameraAnimationState.ROTATION.w - 1.0f) > 1.0E-4f;
            }
        } else {
            CameraAnimationState.clear();
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static Quaternionf rotation() {
        return new Quaternionf((Quaternionfc)ROTATION);
    }

    public static Vector3f offset() {
        return new Vector3f((Vector3fc)OFFSET);
    }
}
