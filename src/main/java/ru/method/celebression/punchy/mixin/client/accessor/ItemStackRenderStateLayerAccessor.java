package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10444;
import net.minecraft.class_1921;
import net.minecraft.class_804;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_10444.class_10446.class})
public interface ItemStackRenderStateLayerAccessor {
    @Accessor(value="renderLayer")
    public class_1921 punchy$getRenderType();

    @Accessor(value="glint")
    public class_10444.class_10445 punchy$getFoilType();

    @Accessor(value="tints")
    public int[] punchy$getTintLayers();

    @Invoker(value="getTransformation")
    public class_804 punchy$getTransform();
}

