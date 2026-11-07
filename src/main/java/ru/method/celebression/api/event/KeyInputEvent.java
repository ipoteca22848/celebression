package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class KeyInputEvent
extends CancellableEvent {
    private int f;
    private int action;
    private int g;

    @Generated
    public KeyInputEvent(int n, int n2, int n3) {
        this.f = n;
        this.action = n2;
        this.g = n3;
    }

    @Generated
    public int c() {
        return this.f;
    }

    @Generated
    public int getAction() {
        return this.action;
    }

    @Generated
    public int d() {
        return this.g;
    }
}

