package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class PlayerRotationEvent
extends CancellableEvent {
    private float ae;
    private float pitch;
    public static float lastYaw;
    public static float lastPitch;

    public void c(float f) {
        this.ae = f;
        lastYaw = f;
    }

    public void setPitch(float f) {
        this.pitch = f;
        lastPitch = f;
    }

    @Generated
    public PlayerRotationEvent(float f, float f2) {
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
}

