package ru.method.celebression.api.mixins;

import net.minecraft.class_419;
import net.minecraft.class_9812;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_419.class})
public interface DisconnectedScreenAccessor {
    @Accessor(value="info")
    public class_9812 celebression$getInfo();
}

