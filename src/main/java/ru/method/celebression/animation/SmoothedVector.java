package ru.method.celebression.animation;

import lombok.Generated;

public class SmoothedVector {
    private long startTime = System.currentTimeMillis();

    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

    public boolean x(long l) {
        return System.currentTimeMillis() - this.startTime > l;
    }

    public boolean y(long l) {
        return this.x(l);
    }

    public long rV() {
        return System.currentTimeMillis() - this.startTime;
    }

    @Generated
    public void z(long l) {
        this.startTime = l;
    }
}

