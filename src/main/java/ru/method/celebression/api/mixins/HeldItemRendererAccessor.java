package ru.method.celebression.api.mixins;

import net.minecraft.class_1799;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.method.celebression.api.accessor.HeldItemRendererStateAccessor;

@Mixin(value={class_759.class})
public class HeldItemRendererAccessor
implements HeldItemRendererStateAccessor {
    @Shadow
    private class_1799 field_4047;
    @Shadow
    private class_1799 field_4048;
    @Shadow
    private float field_4043;
    @Shadow
    private float field_4052;

    @Override
    public float getEquippedProgressMainHand() {
        return this.field_4043;
    }

    @Override
    public void setEquippedProgressMainHand(float f) {
        this.field_4043 = f;
    }

    @Override
    public float getEquippedProgressOffHand() {
        return this.field_4052;
    }

    @Override
    public void setEquippedProgressOffHand(float f) {
        this.field_4052 = f;
    }

    @Override
    public void setItemStackMainHand(class_1799 class_17992) {
        this.field_4047 = class_17992;
    }

    @Override
    public void setItemStackOffHand(class_1799 class_17992) {
        this.field_4048 = class_17992;
    }
}

