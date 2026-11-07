package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_636.class})
public interface IClientPlayerInteractionManager {
    @Invoker(value="syncSelectedSlot")
    public void syncSelectedSlot$drug();
}

