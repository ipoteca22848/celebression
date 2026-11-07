package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import ru.method.celebression.api.event.CancellableEvent;

public class BlockBreakEvent
extends CancellableEvent {
    private final class_2338 ac;
    private final class_2350 ad;

    @Generated
    public BlockBreakEvent(class_2338 class_23382, class_2350 class_23502) {
        this.ac = class_23382;
        this.ad = class_23502;
    }

    @Generated
    public class_2338 p() {
        return this.ac;
    }

    @Generated
    public class_2350 q() {
        return this.ad;
    }
}

