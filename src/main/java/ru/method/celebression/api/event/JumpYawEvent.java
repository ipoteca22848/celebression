package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class JumpYawEvent
extends CancellableEvent {
    private float ae;

    @Generated
    public JumpYawEvent(float f) {
        this.ae = f;
    }

    @Generated
    public float r() {
        return this.ae;
    }

    @Generated
    public void c(float f) {
        this.ae = f;
    }
}

