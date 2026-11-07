package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class MouseScrollEvent
extends CancellableEvent {
    private double j;
    private double k;

    public void consume() {
        this.cancel();
    }

    @Generated
    public MouseScrollEvent(double d, double d2) {
        this.j = d;
        this.k = d2;
    }

    @Generated
    public double g() {
        return this.j;
    }

    @Generated
    public double h() {
        return this.k;
    }
}

