package ru.method.celebression.animation;

import java.util.Objects;
import lombok.Generated;
import ru.method.celebression.animation.Easing;

public class TimedProgress {
    private final long aiH;
    private final Easing aiI;
    private String aiJ;
    private String aiK;
    private long startTime;

    public TimedProgress(long l, Easing easing, String string) {
        this.aiH = Math.max(1L, l);
        this.aiI = easing;
        this.aiK = this.aiJ = string != null ? string : "";
        this.startTime = 0L;
    }

    public void cF(String string) {
        String string2;
        String string3 = string2 = string != null ? string : "";
        if (Objects.equals(string2, this.aiJ)) {
            if (!this.rg()) {
                this.aiK = this.aiJ;
            }
            return;
        }
        this.aiK = this.aiJ;
        this.aiJ = string2;
        this.startTime = System.currentTimeMillis();
    }

    public float progress() {
        if (this.aiH <= 0L || !this.rg()) {
            return 1.0f;
        }
        float f = (float)(System.currentTimeMillis() - this.startTime) / (float)this.aiH;
        if (f >= 1.0f) {
            this.startTime = 0L;
            this.aiK = this.aiJ;
            return 1.0f;
        }
        return (float)this.aiI.aa(Math.max(0.0f, Math.min(1.0f, f)));
    }

    public boolean rg() {
        return this.startTime > 0L;
    }

    @Generated
    public String rh() {
        return this.aiJ;
    }

    @Generated
    public String ri() {
        return this.aiK;
    }
}

