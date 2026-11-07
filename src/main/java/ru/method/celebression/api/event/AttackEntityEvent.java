package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import ru.method.celebression.api.event.CancellableEvent;

public class AttackEntityEvent
extends CancellableEvent {
    private final class_1657 d;
    private final class_1297 e;

    @Generated
    public AttackEntityEvent(class_1657 class_16572, class_1297 class_12972) {
        this.d = class_16572;
        this.e = class_12972;
    }

    @Generated
    public class_1657 a() {
        return this.d;
    }

    @Generated
    public class_1297 b() {
        return this.e;
    }
}

