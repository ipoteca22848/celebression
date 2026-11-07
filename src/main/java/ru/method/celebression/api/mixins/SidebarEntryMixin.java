package ru.method.celebression.api.mixins;

import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.util.PlayerNameSanitizer;

@Mixin(targets={"net.minecraft.client.gui.hud.InGameHud$SidebarEntry"})
public class SidebarEntryMixin {
    @Inject(method={"name"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$sanitizeSidebarName(CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(PlayerNameSanitizer.r((class_2561)callbackInfoReturnable.getReturnValue()));
    }

    @Inject(method={"score"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$sanitizeSidebarScore(CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(PlayerNameSanitizer.r((class_2561)callbackInfoReturnable.getReturnValue()));
    }
}

