package ru.method.celebression.animation;

import net.minecraft.class_3532;
import ru.method.celebression.animation.Easing;

public final class TransitionAnimation {
    private float value;
    private float prevValue;
    private float animationSpeed;
    private float aje;
    private float ajf;
    private float ajg = 1.0f;

    public void rr() {
        this.value = 0.0f;
        this.prevValue = 0.0f;
        this.ajg = 0.0f;
    }

    public void rs() {
        this.value = 1.0f;
        this.prevValue = 1.0f;
        this.ajg = 1.0f;
    }

    public void i(boolean bl) {
        this.prevValue = this.value;
        this.value = class_3532.method_15363((float)(this.value + (bl ? this.animationSpeed : -this.animationSpeed)), (float)this.aje, (float)this.ajf);
    }

    public void j(float f, float f2, float f3, float f4) {
        this.animationSpeed = f3;
        this.aje = f;
        this.ajf = f2;
        float f5 = class_3532.method_16439((float)f4, (float)this.prevValue, (float)this.value);
        this.ajg = (float)Easing.aiX.aa(f5);
    }

    public float rk() {
        return this.value;
    }

    public float rt() {
        return this.prevValue;
    }

    public float ru() {
        return this.ajg;
    }

    public boolean isFinishedClosing() {
        return this.value <= 0.0f && this.prevValue <= 0.0f;
    }
}

