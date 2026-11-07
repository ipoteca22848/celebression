package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_746.class})
public interface IClientPlayerEntity {
    @Invoker(value="sendSprintingPacket")
    public void sendSprintingPacket$drug();
}

