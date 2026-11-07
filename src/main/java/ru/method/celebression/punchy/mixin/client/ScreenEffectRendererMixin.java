package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4603;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.method.celebression.punchy.config.PunchyConfig;

@Mixin(value={class_4603.class})
public class ScreenEffectRendererMixin {
    @Invoker(value="renderFireOverlay")
    public static void punchy$renderFire(class_4587 class_45872, class_4597 class_45972) {
        throw new AssertionError();
    }

    @Redirect(method={"renderOverlays(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/InGameOverlayRenderer;renderFireOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"))
    private static void punchy$skipFireOverlay(class_4587 class_45872, class_4597 class_45972) {
        if (!PunchyConfig.isModEnabled() || PunchyConfig.disableEnhancedFireArmEffects()) {
            ScreenEffectRendererMixin.punchy$renderFire(class_45872, class_45972);
        }
    }
}

