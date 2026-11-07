package unkw.visualtest.mixin.client;

import net.minecraft.class_276;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import unkw.visualtest.client.hud.HudEntityPreview;

@Mixin(value={class_310.class})
public abstract class MinecraftClientFramebufferMixin {
    @Inject(method={"getFramebuffer"}, at={@At(value="HEAD")}, cancellable=true)
    private void visualTestRedirectPreviewFramebuffer(CallbackInfoReturnable<class_276> callbackInfoReturnable) {
        class_276 class_2762 = HudEntityPreview.getFramebufferOverride();
        if (class_2762 != null) {
            callbackInfoReturnable.setReturnValue(class_2762);
        }
    }
}

