package ru.method.celebression.api.mixins;

import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.utils.debug.TabProfiler;

@Mixin(value={class_332.class})
public class DrawContextMixin {
    private static boolean celebression$skipSanitization() {
        return TabProfiler.isActive() || StreamerTextSanitizer.sy();
    }

    @ModifyVariable(method={"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_2561 celebression$sanitizeShadowText(class_2561 class_25612) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_25612;
        }
        return StreamerTextSanitizer.l(class_25612);
    }

    @ModifyVariable(method={"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_5481 celebression$sanitizeShadowOrdered(class_5481 class_54812) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_54812;
        }
        return StreamerTextSanitizer.b(class_54812);
    }

    @ModifyVariable(method={"drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_2561 celebression$sanitizeText(class_2561 class_25612) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_25612;
        }
        return StreamerTextSanitizer.l(class_25612);
    }

    @ModifyVariable(method={"drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;IIIZ)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_5481 celebression$sanitizeOrdered(class_5481 class_54812) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_54812;
        }
        return StreamerTextSanitizer.b(class_54812);
    }

    @ModifyVariable(method={"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private String celebression$sanitizeShadowString(String string) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return string;
        }
        return StreamerTextSanitizer.dq(string);
    }

    @ModifyVariable(method={"drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private String celebression$sanitizeString(String string) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return string;
        }
        return StreamerTextSanitizer.dq(string);
    }

    @ModifyVariable(method={"drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_2561 celebression$sanitizeCenteredText(class_2561 class_25612) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_25612;
        }
        return StreamerTextSanitizer.l(class_25612);
    }

    @ModifyVariable(method={"drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)V"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_5481 celebression$sanitizeCenteredOrdered(class_5481 class_54812) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_54812;
        }
        return StreamerTextSanitizer.b(class_54812);
    }

    @ModifyVariable(method={"drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private String celebression$sanitizeCenteredString(String string) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return string;
        }
        return StreamerTextSanitizer.dq(string);
    }

    @ModifyVariable(method={"drawTextWithBackground(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIII)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_2561 celebression$sanitizeBackgroundText(class_2561 class_25612) {
        if (DrawContextMixin.celebression$skipSanitization()) {
            return class_25612;
        }
        return StreamerTextSanitizer.l(class_25612);
    }
}

