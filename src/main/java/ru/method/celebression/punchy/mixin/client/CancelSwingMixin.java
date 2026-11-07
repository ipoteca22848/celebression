package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public class CancelSwingMixin {
    @Inject(method={"getHandSwingProgress(F)F"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$freezeSwing(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
    }
}

