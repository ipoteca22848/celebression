package ru.method.celebression.animation;

import net.minecraft.class_3532;

public final class MotionBlendState {
    private float aE;
    private float aF;

    public void a(boolean bl, float f, float f2, boolean bl2) {
        if (bl2) {
            this.aF = class_3532.method_16439((float)class_3532.method_15363((float)(f2 * 6.0f), (float)0.0f, (float)1.0f), (float)this.aF, (float)1.0f);
            return;
        }
        this.aE = class_3532.method_16439((float)class_3532.method_15363((float)(f2 * 8.0f), (float)0.0f, (float)1.0f), (float)this.aE, (float)f);
        boolean bl3 = bl || this.aE > 0.03f;
        float f3 = bl3 ? 1.0f : 0.0f;
        float f4 = f3 > this.aF ? 5.0f : 3.0f;
        this.aF = class_3532.method_16439((float)class_3532.method_15363((float)(f2 * f4), (float)0.0f, (float)1.0f), (float)this.aF, (float)f3);
    }

    public float bv() {
        return this.aF;
    }

    public void reset() {
        this.aE = 0.0f;
        this.aF = 0.0f;
    }
}

