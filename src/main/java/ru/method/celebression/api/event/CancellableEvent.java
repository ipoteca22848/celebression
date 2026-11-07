package ru.method.celebression.api.event;

import lombok.Generated;

public class CancellableEvent {
    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public void resume() {
        this.cancelled = false;
    }

    @Generated
    public boolean isCancelled() {
        return this.cancelled;
    }
}

