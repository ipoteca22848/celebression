package ru.method.celebression.punchy.client.modelparts.util;

import net.minecraft.class_630;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import ru.method.celebression.punchy.client.access.TransformablePart;

public final class ModelPartMath {
    private ModelPartMath() {
    }

    public static Matrix4f getLocalMatrix(class_630 class_6302) {
        if (class_6302 == null) {
            return null;
        }
        if ((Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform != null) {
                return new Matrix4f((Matrix4fc)explicitTransform);
            }
        }
        return new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
    }
}
