package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_1735;
import net.minecraft.class_465;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_465.class})
public interface IHandledScreen {
    @Accessor(value="focusedSlot")
    public class_1735 getFocusedSlot();

    @Accessor(value="focusedSlot")
    @Mutable
    public void setFocusedSlot(class_1735 var1);

    @Accessor(value="x")
    public int getX();

    @Accessor(value="y")
    public int getY();

    @Invoker(value="getSlotAt")
    public class_1735 invokeGetSlotAt(double var1, double var3);
}

