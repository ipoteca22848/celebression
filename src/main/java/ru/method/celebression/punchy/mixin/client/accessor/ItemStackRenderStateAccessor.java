package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10444;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_10444.class})
public interface ItemStackRenderStateAccessor {
    @Accessor(value="layerCount")
    public int punchy$getActiveLayerCount();

    @Accessor(value="layers")
    public class_10444.class_10446[] punchy$getLayers();
}

