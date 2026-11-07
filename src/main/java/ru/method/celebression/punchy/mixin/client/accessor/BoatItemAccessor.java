package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10255;
import net.minecraft.class_1299;
import net.minecraft.class_1749;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1749.class})
public interface BoatItemAccessor {
    @Accessor(value="boatEntityType")
    public class_1299<? extends class_10255> punchy$getEntityType();
}

