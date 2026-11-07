package ru.method.celebression.punchy.mixin.client.accessor;

import java.util.List;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_922.class})
public interface LivingEntityRendererAccessor {
    @Accessor(value="features")
    public List<Object> punchy$getLayers();
}

