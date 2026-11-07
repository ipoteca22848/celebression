package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_310;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.state.InterfaceDelayState;

@Mixin(value={class_310.class})
public class MinecraftScreenDelayMixin {
    @Inject(method={"setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$delayInterface(class_437 class_4372, CallbackInfo callbackInfo) {
        if (InterfaceDelayState.shouldDelay(class_4372)) {
            InterfaceDelayState.queue(class_4372);
            callbackInfo.cancel();
        }
    }
}

