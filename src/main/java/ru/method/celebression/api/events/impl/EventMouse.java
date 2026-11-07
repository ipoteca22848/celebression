package ru.method.celebression.api.events.impl;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;

public class EventMouse
extends CancellableEvent {
    private int button;
    private int action;

    public void consume() {
        this.cancel();
    }

    @Generated
    public EventMouse(int n, int n2) {
        this.button = n;
        this.action = n2;
    }

    @Generated
    public int getButton() {
        return this.button;
    }

    @Generated
    public int getAction() {
        return this.action;
    }
}

