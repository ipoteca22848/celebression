package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1297;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_742;
import net.minecraft.class_757;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.HandRenderBobContext;

@Mixin(value={class_757.class})
public class GameRendererMixin {
    @Inject(method={"bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$disableViewBobDuringSprint(class_4587 class_45872, float f, CallbackInfo callbackInfo) {
        class_1297 class_12972;
        class_310 class_3102;
        if (!(f < 0.0f) && (class_3102 = class_310.method_1551()) != null && (class_12972 = class_3102.method_1560()) instanceof class_742) {
            class_742 class_7422 = (class_742)class_12972;
            float f2 = class_7422.field_53039 - class_7422.field_53038;
            float f3 = -(class_7422.field_53039 + f2 * f);
            float f4 = class_3532.method_16439((float)f, (float)class_7422.field_7505, (float)class_7422.field_7483);
            HandRenderBobContext.updateBobSample(f3, f4);
        }
    }
}

