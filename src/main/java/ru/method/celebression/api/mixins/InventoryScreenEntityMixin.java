package ru.method.celebression.api.mixins;

import net.minecraft.class_1309;
import net.minecraft.class_332;
import net.minecraft.class_490;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;

@Mixin(value={class_490.class})
public abstract class InventoryScreenEntityMixin {
    @Inject(method={"drawEntity(Lnet/minecraft/client/gui/DrawContext;FFFLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/entity/LivingEntity;)V"}, at={@At(value="HEAD")})
    private static void celebression$stripGuiEntityName(class_332 class_3322, float f, float f2, float f3, Vector3f vector3f, Quaternionf quaternionf, Quaternionf quaternionf2, class_1309 class_13092, CallbackInfo callbackInfo) {
        if (CosmeticPreviewContext.isActive()) {
            class_13092.method_5880(false);
            class_13092.method_5665(null);
        }
    }

    @Inject(method={"drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V"}, at={@At(value="HEAD")})
    private static void celebression$trackGuiEntityDraw(class_332 class_3322, int n, int n2, int n3, int n4, int n5, float f, float f2, float f3, class_1309 class_13092, CallbackInfo callbackInfo) {
        if (CosmeticPreviewContext.isActive()) {
            class_13092.method_5880(false);
            class_13092.method_5665(null);
        }
    }
}

