package ru.method.celebression.api.mixins;

import java.util.Queue;
import net.minecraft.class_3999;
import net.minecraft.class_4184;
import net.minecraft.class_4597;
import net.minecraft.class_702;
import net.minecraft.class_703;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.NoRenderModule;

@Mixin(value={class_702.class})
public abstract class ParticleManagerMixin {
    private static boolean shouldSkipParticles() {
        NoRenderModule noRenderModule = Celebression.getInstance().getModuleManager().getModule(NoRenderModule.class);
        return noRenderModule != null && noRenderModule.isToggled() && (Boolean)noRenderModule.Fe.jT() != false;
    }

    @Inject(method={"renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderParticles(CallbackInfo callbackInfo) {
        if (ParticleManagerMixin.shouldSkipParticles()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderCustomParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderCustomParticles(class_4184 class_41842, float f, class_4597.class_4598 class_45982, Queue<class_703> queue, CallbackInfo callbackInfo) {
        if (ParticleManagerMixin.shouldSkipParticles()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderParticles(class_4184 class_41842, float f, class_4597.class_4598 class_45982, class_3999 class_39992, Queue<class_703> queue, CallbackInfo callbackInfo) {
        if (ParticleManagerMixin.shouldSkipParticles()) {
            callbackInfo.cancel();
        }
    }
}

