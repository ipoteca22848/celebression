package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_310.class})
public interface IMinecraftClient {
    @Accessor(value="itemUseCooldown")
    public void setItemUseCooldown(int var1);

    @Accessor(value="itemUseCooldown")
    public int getItemUseCooldown();
}

