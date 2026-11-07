package ru.method.celebression.punchy.client.animation.data;

import ru.method.celebression.animation.Animation;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.animation.data.VectorExpression;

public record Keyframe(float timeStamp, Vector3f position, Vector3f rotationEuler, Quaternionf rotation, Vector3f scale, String interpolation, Vector3f positionV2, Vector3f rotationPreEulerV2, Vector3f rotationPostEulerV2, VectorExpression positionExpression, VectorExpression rotationExpression, VectorExpression scaleExpression) {
    public Keyframe(float f, Vector3f vector3f, Vector3f vector3f2, Quaternionf quaternionf, Vector3f vector3f3, String string) {
        this(f, vector3f, vector3f2, quaternionf, vector3f3, string, null, null, null, null, null, null);
    }

    public Keyframe(float f, Vector3f vector3f, Vector3f vector3f2, Quaternionf quaternionf, Vector3f vector3f3, String string, VectorExpression vectorExpression, VectorExpression vectorExpression2, VectorExpression vectorExpression3) {
        this(f, vector3f, vector3f2, quaternionf, vector3f3, string, null, null, null, vectorExpression, vectorExpression2, vectorExpression3);
    }

    public Vector3f sampledPosition(float f) {
        return Keyframe.sampled(this.position, this.positionExpression, f, new Vector3f(0.0f, 0.0f, 0.0f));
    }

    public Vector3f sampledPositionV2(float f) {
        return this.positionV2 != null ? new Vector3f((Vector3fc)this.positionV2) : this.sampledPosition(f);
    }

    public Vector3f sampledRotationEuler(float f) {
        return Keyframe.sampled(this.rotationEuler, this.rotationExpression, f, new Vector3f(0.0f, 0.0f, 0.0f));
    }

    public Vector3f sampledScale(float f) {
        return Keyframe.sampled(this.scale, this.scaleExpression, f, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public boolean hasRotationV2() {
        return this.rotationPreEulerV2 != null || this.rotationPostEulerV2 != null;
    }

    public Vector3f sampledRotationPreEulerV2(float f) {
        if (this.rotationPreEulerV2 != null) {
            return new Vector3f((Vector3fc)this.rotationPreEulerV2);
        }
        Vector3f vector3f = this.sampledRotationEuler(f);
        return vector3f == null ? null : new Vector3f((Vector3fc)vector3f);
    }

    public Vector3f sampledRotationPostEulerV2(float f) {
        if (this.rotationPostEulerV2 != null) {
            return new Vector3f((Vector3fc)this.rotationPostEulerV2);
        }
        Vector3f vector3f = this.sampledRotationEuler(f);
        return vector3f == null ? null : new Vector3f((Vector3fc)vector3f);
    }

    private static Vector3f sampled(Vector3f vector3f, VectorExpression vectorExpression, float f, Vector3f vector3f2) {
        Vector3f vector3f3 = vector3f != null ? vector3f : vector3f2;
        return vectorExpression != null && vectorExpression.isPresent() ? vectorExpression.evaluate(vector3f3, f) : new Vector3f((Vector3fc)vector3f3);
    }
}

