package ru.method.celebression.api.mixins;

import net.minecraft.class_2561;
import net.minecraft.class_266;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.util.StreamerTextSanitizer;

@Mixin(value={class_266.class})
public class ScoreboardObjectiveMixin {
    @Inject(method={"getDisplayName"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$sanitizeObjectiveName(CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        StreamerTextSanitizer.j((class_2561)callbackInfoReturnable.getReturnValue());
        callbackInfoReturnable.setReturnValue(StreamerTextSanitizer.l((class_2561)callbackInfoReturnable.getReturnValue()));
    }
}

