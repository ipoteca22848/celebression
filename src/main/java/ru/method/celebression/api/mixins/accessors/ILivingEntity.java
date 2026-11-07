package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1309.class})
public interface ILivingEntity {
    @Accessor(value="jumpingCooldown")
    public void setJumpingCooldown(int var1);
}

