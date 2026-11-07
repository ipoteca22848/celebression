package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_1297;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_1297.class})
public interface IEntityRemoval {
    @Invoker(value="unsetRemoved")
    public void celebression$unsetRemoved();
}

