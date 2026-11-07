package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_2596;
import ru.method.celebression.api.event.CancellableEvent;

public class PacketEvent
extends CancellableEvent {
    private final class_2596<?> l;

    @Generated
    public PacketEvent(class_2596<?> class_25962) {
        this.l = class_25962;
    }

    @Generated
    public class_2596<?> i() {
        return this.l;
    }

    public static class GenericPacket
    extends PacketEvent {
        public GenericPacket(class_2596<?> class_25962) {
            super(class_25962);
        }
    }

    public static class Send
    extends PacketEvent {
        public Send(class_2596<?> class_25962) {
            super(class_25962);
        }
    }

    public static class Receive
    extends PacketEvent {
        public Receive(class_2596<?> class_25962) {
            super(class_25962);
        }
    }
}

