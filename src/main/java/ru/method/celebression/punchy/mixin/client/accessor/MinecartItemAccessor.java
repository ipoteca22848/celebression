package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_1299;
import net.minecraft.class_1688;
import net.minecraft.class_1808;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1808.class})
public interface MinecartItemAccessor {
    @Accessor(value="type")
    public class_1299<? extends class_1688> punchy$getType();
}

