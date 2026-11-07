package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_332;
import net.minecraft.class_9779;
import ru.method.celebression.api.event.CancellableEvent;

public class HudRenderEvent
extends CancellableEvent {
    private class_332 context;
    private class_9779 tickCounter;

    @Generated
    public HudRenderEvent(class_332 class_3322, class_9779 class_97792) {
        this.context = class_3322;
        this.tickCounter = class_97792;
    }

    @Generated
    public class_332 j() {
        return this.context;
    }

    @Generated
    public class_9779 k() {
        return this.tickCounter;
    }
}

