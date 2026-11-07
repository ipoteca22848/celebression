package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_897.class})
public interface IEntityRendererShadow {
    @Accessor(value="shadowRadius")
    public float getShadowRadiusValue();

    @Accessor(value="shadowOpacity")
    public float getShadowOpacityValue();
}

