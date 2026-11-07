package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_10197;
import net.minecraft.class_572;
import net.minecraft.class_970;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_970.class})
public interface HumanoidArmorLayerAccessor {
    @Accessor(value="innerModel")
    public class_572<?> punchy$getInnerModel();

    @Accessor(value="outerModel")
    public class_572<?> punchy$getOuterModel();

    @Accessor(value="babyInnerModel")
    public class_572<?> punchy$getInnerModelBaby();

    @Accessor(value="babyOuterModel")
    public class_572<?> punchy$getOuterModelBaby();

    @Accessor(value="equipmentRenderer")
    public class_10197 punchy$getEquipmentRenderer();
}

