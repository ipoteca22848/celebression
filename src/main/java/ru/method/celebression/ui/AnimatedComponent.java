package ru.method.celebression.ui;

import lombok.Generated;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;

public class AnimatedComponent {
    private float ajh;
    private float aji;
    private Easing aiI;
    private Animation jU = new Animation(0L, 0.0, false, Easing.aiQ);

    public AnimatedComponent(Easing easing) {
        this.aiI = easing;
    }

    public float a(float f, long l) {
        l = Math.max(1L, l);
        float f2 = this.aji;
        this.ajh = f2 - this.jU.rk();
        this.aji = f;
        if (f2 != f) {
            this.jU = new Animation(l, this.aji - this.ajh, false, this.aiI);
        }
        return this.ajh;
    }

    public boolean rj() {
        return this.ajh == this.aji || this.jU.rj() || this.jU.ad(false);
    }

    public float rk() {
        this.ajh = this.aji - this.jU.rk();
        return this.ajh;
    }

    @Generated
    public void a(Easing easing) {
        this.aiI = easing;
    }

    @Generated
    public Animation dN() {
        return this.jU;
    }
}

