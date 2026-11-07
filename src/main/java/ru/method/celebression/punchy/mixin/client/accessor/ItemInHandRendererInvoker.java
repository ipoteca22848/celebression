package ru.method.celebression.punchy.mixin.client.accessor;

import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_759.class})
public interface ItemInHandRendererInvoker {
    @Invoker(value="renderArmHoldingItem")
    public void punchy$renderPlayerArm(class_4587 var1, class_4597 var2, int var3, float var4, float var5, class_1306 var6);

    @Invoker(value="renderFirstPersonMap")
    public void punchy$renderMap(class_4587 var1, class_4597 var2, int var3, class_1799 var4);
}

