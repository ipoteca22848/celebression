package ru.method.celebression.punchy.mixin.client.accessor;

import java.util.List;
import java.util.Map;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_630.class})
public interface ModelPartAccessor {
    @Accessor(value="cuboids")
    public List<class_630.class_628> punchy$getCubes();

    @Accessor(value="children")
    public Map<String, class_630> punchy$getChildren();
}

