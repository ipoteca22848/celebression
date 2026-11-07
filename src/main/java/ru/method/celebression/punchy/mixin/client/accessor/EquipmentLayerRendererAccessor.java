package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10197;
import net.minecraft.class_10201;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_10197.class})
public interface EquipmentLayerRendererAccessor {
    @Accessor(value="equipmentModelLoader")
    public class_10201 punchy$getEquipmentAssets();
}

