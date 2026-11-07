package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_3532;
import ru.method.celebression.api.event.CancellableEvent;

public class MovementInputEvent
extends CancellableEvent {
    private float h;
    private float i;

    public void a(float f, float f2) {
        float f3 = this.e();
        float f4 = this.f();
        double d = class_3532.method_15338((double)Math.toDegrees(this.a(f2, f3, f4)));
        if (f3 == 0.0f && f4 == 0.0f) {
            return;
        }
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = Float.MAX_VALUE;
        for (float f8 = -1.0f; f8 <= 1.0f; f8 += 1.0f) {
            for (float f9 = -1.0f; f9 <= 1.0f; f9 += 1.0f) {
                double d2;
                double d3;
                if (f9 == 0.0f && f8 == 0.0f || !((d3 = Math.abs(d - (d2 = class_3532.method_15338((double)Math.toDegrees(this.a(f, f8, f9)))))) < (double)f7)) continue;
                f7 = (float)d3;
                f5 = f8;
                f6 = f9;
            }
        }
        this.a(f5);
        this.b(f6);
    }

    private double a(float f, double d, double d2) {
        if (d < 0.0) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (d < 0.0) {
            f2 = -0.5f;
        } else if (d > 0.0) {
            f2 = 0.5f;
        }
        if (d2 > 0.0) {
            f -= 90.0f * f2;
        }
        if (d2 < 0.0) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }

    @Generated
    public MovementInputEvent(float f, float f2) {
        this.h = f;
        this.i = f2;
    }

    @Generated
    public float e() {
        return this.h;
    }

    @Generated
    public float f() {
        return this.i;
    }

    @Generated
    public void a(float f) {
        this.h = f;
    }

    @Generated
    public void b(float f) {
        this.i = f;
    }
}

