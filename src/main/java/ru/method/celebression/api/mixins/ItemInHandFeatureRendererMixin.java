package ru.method.celebression.api.mixins;

import net.minecraft.class_10426;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_989;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;

@Mixin(value={class_989.class})
public class ItemInHandFeatureRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void lvNarutoLeanPush(class_4587 class_45872, class_4597 class_45972, int n, class_10426 class_104262, float f, float f2, CallbackInfo callbackInfo) {
        if (!CosmeticPreviewTransformState.active) {
            return;
        }
        class_45872.method_22903();
        class_45872.method_46416(0.0f, CosmeticPreviewTransformState.pivotY, 0.0f);
        class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.eg));
        class_45872.method_46416(0.0f, -CosmeticPreviewTransformState.pivotY, 0.0f);
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void lvNarutoLeanPop(class_4587 class_45872, class_4597 class_45972, int n, class_10426 class_104262, float f, float f2, CallbackInfo callbackInfo) {
        if (!CosmeticPreviewTransformState.active) {
            return;
        }
        class_45872.method_22909();
    }
}

