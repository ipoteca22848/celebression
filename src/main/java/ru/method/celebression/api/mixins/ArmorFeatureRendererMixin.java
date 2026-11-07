package ru.method.celebression.api.mixins;

import net.minecraft.class_10034;
import net.minecraft.class_10055;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_572;
import net.minecraft.class_7833;
import net.minecraft.class_970;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.modules.impl.render.NoRenderModule;
import ru.method.celebression.modules.impl.render.cosmetic.Naruto;
import ru.method.celebression.utils.render.ArmorRenderStateHolder;

@Mixin(value={class_970.class})
public abstract class ArmorFeatureRendererMixin<S extends class_10034, M extends class_572<S>, A extends class_572<S>>
extends class_3887<S, M> {
    @Unique
    private boolean celebression$narutoInvisPushed;

    public ArmorFeatureRendererMixin(class_3883<S, M> class_38832) {
        super(class_38832);
    }

    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V"}, at={@At(value="HEAD")})
    private void celebression$captureArmorState(class_4587 class_45872, class_4597 class_45972, int n, S s, float f, float f2, CallbackInfo callbackInfo) {
        ArmorRenderStateHolder.set(s);
        this.celebression$narutoInvisPushed = false;
        if (CosmeticPreviewTransformState.active) {
            class_45872.method_22903();
            class_45872.method_46416(0.0f, CosmeticPreviewTransformState.pivotY, 0.0f);
            class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.eg));
            class_45872.method_46416(0.0f, -CosmeticPreviewTransformState.pivotY, 0.0f);
        } else {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102.field_1724 != null && class_3102.field_1724.method_5767() && Naruto.enabled() && s instanceof class_10055) {
                float f3;
                class_10055 class_100552 = (class_10055)s;
                if (!(class_100552.field_53528 != class_3102.field_1724.method_5628() || class_100552.field_53412 || class_100552.field_53411 || class_100552.field_53414 || class_100552.field_53410 || !((f3 = class_3532.method_15363((float)((float)class_3102.field_1724.method_18798().method_37267() * 10.0f), (float)0.0f, (float)1.0f)) >= 0.1f))) {
                    class_572 class_5722 = (class_572)(Object)this.method_17165();
                    float f4 = class_3102.method_61966().method_60637(true);
                    float f5 = ((float)class_3102.field_1724.field_6012 + f4) * 0.6f;
                    float f6 = class_3532.method_15362((float)f5) * 0.2f * f3;
                    float f7 = 1.25f + 0.45f * f3 + f6;
                    float f8 = 0.55f * f3;
                    float f9 = -0.15f * f3;
                    class_5722.field_3401.field_3654 = f7;
                    class_5722.field_27433.field_3654 = f7;
                    class_5722.field_3401.field_3674 = f8;
                    class_5722.field_27433.field_3674 = -f8;
                    class_5722.field_3401.field_3675 += 0.1f * f3;
                    class_5722.field_27433.field_3675 -= 0.1f * f3;
                    class_5722.field_3398.field_3654 += f9;
                    float f10 = 0.75f;
                    float f11 = 0.45f * f3;
                    class_45872.method_22903();
                    class_45872.method_46416(0.0f, f10, 0.0f);
                    class_45872.method_22907(class_7833.field_40714.rotation(f11));
                    class_45872.method_46416(0.0f, -f10, 0.0f);
                    this.celebression$narutoInvisPushed = true;
                }
            }
        }
    }

    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V"}, at={@At(value="RETURN")})
    private void celebression$clearArmorState(class_4587 class_45872, class_4597 class_45972, int n, S s, float f, float f2, CallbackInfo callbackInfo) {
        if (CosmeticPreviewTransformState.active || this.celebression$narutoInvisPushed) {
            class_45872.method_22909();
        }
        this.celebression$narutoInvisPushed = false;
        ArmorRenderStateHolder.clear();
    }

    @Inject(method={"renderArmor"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderArmor(class_4587 class_45872, class_4597 class_45972, class_1799 class_17992, class_1304 class_13042, int n, A a, CallbackInfo callbackInfo) {
        NoRenderModule noRenderModule = Celebression.getInstance().getModuleManager().getModule(NoRenderModule.class);
        if (noRenderModule != null && noRenderModule.isToggled() && ((Boolean)noRenderModule.Ff.jT()).booleanValue()) {
            callbackInfo.cancel();
        }
    }
}

