package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_1297;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1297.class})
public interface IEntityMovementMultiplier {
    @Accessor(value="movementMultiplier")
    public class_243 celebression$getMovementMultiplier();
}

