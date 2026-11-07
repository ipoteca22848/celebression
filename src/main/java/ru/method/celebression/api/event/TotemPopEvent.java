package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_1657;
import ru.method.celebression.api.event.CancellableEvent;

public class TotemPopEvent
extends CancellableEvent {
    private final class_1657 m;

    @Generated
    public TotemPopEvent(class_1657 class_16572) {
        this.m = class_16572;
    }

    @Generated
    public class_1657 a() {
        return this.m;
    }
}

