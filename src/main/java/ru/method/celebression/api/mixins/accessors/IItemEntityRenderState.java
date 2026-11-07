package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_10039;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_10039.class})
public interface IItemEntityRenderState {
    @Accessor(value="uniqueOffset")
    public float getUniqueOffset();
}

