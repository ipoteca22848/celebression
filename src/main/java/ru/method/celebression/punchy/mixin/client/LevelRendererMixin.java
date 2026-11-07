package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_9779;
import net.minecraft.class_9922;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.config.PunchyConfig;

@Mixin(value={class_761.class})
public class LevelRendererMixin {
    @Inject(method={"render(Lnet/minecraft/client/util/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V"}, at={@At(value="TAIL")})
    private void punchy$renderReleasedItemParticles(class_9922 class_99222, class_9779 class_97792, boolean bl, class_4184 class_41842, class_757 class_7572, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo callbackInfo) {
        class_310 class_3102;
        if (PunchyConfig.isModEnabled() && (class_3102 = class_310.method_1551()) != null && class_3102.field_1687 != null) {
            Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4f);
            matrix4f3.setTranslation(0.0f, 0.0f, 0.0f);
            class_4587 class_45872 = new class_4587();
            class_45872.method_23760().method_23761().set((Matrix4fc)matrix4f3);
            class_45872.method_23760().method_23762().set((Matrix3fc)new Matrix3f((Matrix4fc)matrix4f3));
            class_4597.class_4598 class_45982 = class_3102.method_22940().method_23000();
            ModelPartsParticleSystem.renderReleasedParticlesInWorld(class_3102, (class_4597)class_45982, class_45872);
            class_45982.method_22993();
        }
    }
}

