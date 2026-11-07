package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_1536;
import net.minecraft.class_1657;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1657.class})
public interface PlayerFishingAccessor {
    @Accessor(value="fishHook")
    public class_1536 punchy$getFishing();
}

