package ru.method.celebression.animation;

import lombok.Generated;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.SmoothedVector;

public class Animation {
    private static final long aiL = 120L;
    private final SmoothedVector aiM = new SmoothedVector();
    private long duration;
    private final Easing aiN;
    private boolean aiO;
    private double value;
    private long aiP;

    public Animation(long l, double d, boolean bl, Easing easing) {
        this.duration = Math.max(0L, l);
        this.value = d;
        this.aiO = bl;
        this.aiN = easing;
        this.aiP = Math.min(this.aiM.rV(), this.duration);
    }

    public void i(boolean bl) {
        if (this.aiO == bl) {
            return;
        }
        long l = Math.min(this.aiM.rV(), this.duration);
        long l2 = this.duration - l;
        this.aiM.z(System.currentTimeMillis() - l2);
        this.aiO = bl;
        this.aiP = Math.min(this.aiM.rV(), this.duration);
    }

    public boolean ad(boolean bl) {
        return this.aiM.x(this.duration) && bl == this.aiO;
    }

    public boolean rj() {
        return this.aiM.x(this.duration) && this.aiO;
    }

    public float rk() {
        if (this.duration <= 0L) {
            return this.aiO ? (float)this.value : 0.0f;
        }
        long l = this.rp();
        if (this.aiO) {
            if (l >= this.duration) {
                return (float)this.value;
            }
            return (float)(this.aiN.aa((double)l / (double)this.duration) * this.value);
        }
        if (l >= this.duration) {
            return 0.0f;
        }
        return (float)((1.0 - this.aiN.aa((double)l / (double)this.duration)) * this.value);
    }

    public float rl() {
        if (this.duration <= 0L) {
            return this.aiO ? (float)this.value : 0.0f;
        }
        long l = this.rp();
        if (this.aiO) {
            if (l >= this.duration) {
                return (float)this.value;
            }
            return (float)((double)l / (double)this.duration * this.value);
        }
        if (l >= this.duration) {
            return 0.0f;
        }
        return (float)((1.0 - (double)l / (double)this.duration) * this.value);
    }

    public float rm() {
        return 1.0f - this.rk();
    }

    public void reset() {
        this.aiM.reset();
        this.aiP = 0L;
    }

    public void update() {
        if (this.rj()) {
            this.i(false);
        } else if (this.ad(false)) {
            this.i(true);
        }
    }

    public boolean rn() {
        return this.aiO;
    }

    public void ae(boolean bl) {
        this.aiO = bl;
        if (bl) {
            this.aiM.z(System.currentTimeMillis());
            this.aiP = 0L;
        } else {
            this.aiM.z(System.currentTimeMillis() - this.duration);
            this.aiP = this.duration;
        }
    }

    public void ro() {
        this.aiO = true;
        if (this.duration <= 0L) {
            this.aiP = 0L;
            return;
        }
        this.aiP = this.duration;
        this.aiM.z(System.currentTimeMillis() - this.duration);
    }

    public void w(long l) {
        long l2 = Math.max(0L, l);
        double d = this.duration > 0L ? Math.min(1.0, (double)this.aiP / (double)this.duration) : (this.aiO ? 1.0 : 0.0);
        this.duration = l2;
        this.aiP = Math.round(d * (double)this.duration);
        if (!this.aiO && this.duration == 0L) {
            this.aiP = 0L;
        }
        this.aiM.z(System.currentTimeMillis() - this.aiP);
    }

    private long rp() {
        if (this.duration <= 0L) {
            return 0L;
        }
        long l = Math.min(this.aiM.rV(), this.duration);
        if (l < 0L) {
            this.aiM.reset();
            this.aiP = 0L;
            return 0L;
        }
        if (l < this.aiP) {
            this.aiP = l;
            return l;
        }
        long l2 = l - this.aiP;
        if (l2 > 120L) {
            l = this.aiP + 120L;
            this.aiM.z(System.currentTimeMillis() - l);
        }
        this.aiP = l;
        return l;
    }

    @Generated
    public void z(double d) {
        this.value = d;
    }
}

