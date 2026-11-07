package ru.method.celebression.api.mixins;

import net.minecraft.class_332;
import net.minecraft.class_342;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.util.StreamerTextSanitizer;

@Mixin(value={class_342.class})
public abstract class TextFieldWidgetMixin {
    @Inject(method={"renderWidget"}, at={@At(value="HEAD")})
    private void celebression$beginTextFieldRender(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        StreamerTextSanitizer.sC();
    }

    @Inject(method={"renderWidget"}, at={@At(value="RETURN")})
    private void celebression$endTextFieldRender(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        StreamerTextSanitizer.sD();
    }
}

