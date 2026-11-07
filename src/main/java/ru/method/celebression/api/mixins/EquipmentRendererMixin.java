package ru.method.celebression.api.mixins;

import net.minecraft.class_10042;
import net.minecraft.class_10197;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ru.method.celebression.utils.render.ArmorRenderStateHolder;
import ru.method.celebression.utils.render.HitColorRenderHelper;

@Mixin(value={class_10197.class})
public abstract class EquipmentRendererMixin {
    @ModifyArg(method={"render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index=4)
    private int celebression$tintArmorHitColor(int n) {
        class_10042 class_100422 = ArmorRenderStateHolder.get();
        return HitColorRenderHelper.tint(class_100422, n, true);
    }
}

