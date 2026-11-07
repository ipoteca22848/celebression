package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10485;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_10485.class})
public interface UseDurationAccessor {
    @Accessor(value="remaining")
    public boolean punchy$isRemaining();
}

