package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_4587;
import net.minecraft.class_9779;
import ru.method.celebression.api.event.CancellableEvent;

public class WorldRenderEvent
extends CancellableEvent {
    private final class_9779 w;
    private final class_4587 aa;

    @Generated
    public WorldRenderEvent(class_9779 class_97792, class_4587 class_45872) {
        this.w = class_97792;
        this.aa = class_45872;
    }

    @Generated
    public class_9779 k() {
        return this.w;
    }

    @Generated
    public class_4587 l() {
        return this.aa;
    }
}

