package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class RaycastRotationEvent
extends CancellableEvent {
    private float ae;
    private float pitch;

    @Generated
    public RaycastRotationEvent(float f, float f2) {
        this.ae = f;
        this.pitch = f2;
    }

    @Generated
    public float r() {
        return this.ae;
    }

    @Generated
    public float s() {
        return this.pitch;
    }

    @Generated
    public void c(float f) {
        this.ae = f;
    }

    @Generated
    public void setPitch(float f) {
        this.pitch = f;
    }
}

