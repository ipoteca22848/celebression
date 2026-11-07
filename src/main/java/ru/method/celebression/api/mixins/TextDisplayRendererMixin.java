package ru.method.celebression.api.mixins;

import net.minecraft.class_10071;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_8113;
import net.minecraft.class_8138;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.utils.render.HologramLateRenderer;

@Mixin(value={class_8138.class_8141.class})
public abstract class TextDisplayRendererMixin {
    @Inject(method={"render(Lnet/minecraft/client/render/entity/state/TextDisplayEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    private void celebression$deferHologram(class_10071 class_100712, class_4587 class_45872, class_4597 class_45972, int n, float f, CallbackInfo callbackInfo) {
        if (HologramLateRenderer.replaying) {
            return;
        }
        HologramLateRenderer.queue((class_8138.class_8141)(Object)this, class_100712, class_45872, n, f);
        callbackInfo.cancel();
    }

    @Redirect(method={"render*"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/decoration/DisplayEntity$TextDisplayEntity$Data;flags()B"), require=0)
    private byte celebression$forceSeeThrough(class_8113.class_8123.class_8230 class_82302) {
        return (byte)(class_82302.comp_1338() | 2);
    }
}
