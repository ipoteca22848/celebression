package ru.method.celebression.punchy.client.animation.data;

import ru.method.celebression.animation.Animation;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.animation.data.AnimationExpression;

public record VectorExpression(AnimationExpression x, AnimationExpression y, AnimationExpression z) {
    public boolean isPresent() {
        return this.x != null || this.y != null || this.z != null;
    }

    public Vector3f evaluate(Vector3f vector3f, float f) {
        Vector3f vector3f2 = vector3f != null ? new Vector3f((Vector3fc)vector3f) : new Vector3f();
        double d = f;
        if (this.x != null) {
            vector3f2.x = (float)this.x.evaluate(d);
        }
        if (this.y != null) {
            vector3f2.y = (float)this.y.evaluate(d);
        }
        if (this.z != null) {
            vector3f2.z = (float)this.z.evaluate(d);
        }
        return vector3f2;
    }
}

