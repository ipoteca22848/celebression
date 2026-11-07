package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_4604;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_761.class})
public interface IWorldRenderer {
    @Accessor(value="frustum")
    public class_4604 getFrustum();
}

