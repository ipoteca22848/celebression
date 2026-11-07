package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_2561;
import net.minecraft.class_355;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_355.class})
public interface IPlayerListHud {
    @Accessor(value="header")
    public class_2561 getHeader();
}

