package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.class_10042;
import net.minecraft.class_10055;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3883;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5603;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_5617;
import net.minecraft.class_583;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_7833;
import net.minecraft.class_897;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.modules.impl.render.DrochkaModule;
import ru.method.celebression.modules.impl.render.HitColorModule;
import ru.method.celebression.api.accessor.ItemEntityRenderStateAccessor;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.render.SelfNametagsModule;
import ru.method.celebression.modules.impl.render.cosmetic.Naruto;
import ru.method.celebression.utils.render.HitColorRenderHelper;

@Mixin(value={class_922.class})
public abstract class LivingEntityRendererMixin<T extends class_1309, S extends class_10042, M extends class_583<? super S>>
extends class_897<T, S>
implements class_3883<S, M>,
MinecraftClientAccess {
    @Unique
    private static final ThreadLocal<class_10042> CELEBRESSION$CURRENT_RENDER_STATE = new ThreadLocal();
    @Unique
    private static class_630 celebression$breastModel;

    @Unique
    private static class_630 celebression$breast() {
        if (celebression$breastModel == null) {
            class_5609 class_56092 = new class_5609();
            class_5610 class_56102 = class_56092.method_32111();
            class_56102.method_32117("left", class_5606.method_32108().method_32101(18, 20).method_32097(-1.8f, -0.8f, -3.2f, 3.6f, 4.0f, 3.2f), class_5603.method_32091((float)-2.0f, (float)2.6f, (float)-2.0f, (float)0.35f, (float)0.15f, (float)-0.08f));
            class_56102.method_32117("right", class_5606.method_32108().method_32101(18, 20).method_32096().method_32097(-1.8f, -0.8f, -3.2f, 3.6f, 4.0f, 3.2f), class_5603.method_32091((float)2.0f, (float)2.6f, (float)-2.0f, (float)0.35f, (float)-0.15f, (float)0.08f));
            celebression$breastModel = class_5607.method_32110((class_5609)class_56092, (int)64, (int)64).method_32109();
        }
        return celebression$breastModel;
    }

    @Shadow
    public abstract class_2960 method_3885(S var1);

    protected LivingEntityRendererMixin(class_5617.class_5618 class_56182) {
        super(class_56182);
    }

    @Inject(method={"updateRenderState"}, at={@At(value="TAIL")})
    private void celebression$updateHitColorState(T t, S s, float f, CallbackInfo callbackInfo) {
        ItemEntityRenderStateAccessor itemEntityRenderStateAccessor;
        if (CosmeticPreviewContext.aP()) {
            ((class_10042)s).field_53337 = null;
            if (s instanceof class_10055) {
                class_10055 playerState = (class_10055)s;
                playerState.field_53525 = null;
                playerState.field_53529 = null;
            }
        }
        if (!(s instanceof ItemEntityRenderStateAccessor)) {
            return;
        }
        itemEntityRenderStateAccessor = (ItemEntityRenderStateAccessor)s;
        itemEntityRenderStateAccessor.celebression$setPlayer(t instanceof class_1657);
        boolean bl = HitColorModule.a(t.method_5628(), 250L);
        itemEntityRenderStateAccessor.celebression$setHitColorStrength(HitColorModule.a(t.method_5628(), bl));
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void celebression$captureRenderState(S s, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        CELEBRESSION$CURRENT_RENDER_STATE.set((class_10042)s);
        CosmeticPreviewTransformState.clear();
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void celebression$clearRenderState(S s, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        CELEBRESSION$CURRENT_RENDER_STATE.remove();
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index=3)
    private int celebression$hitColorOverlay(int n) {
        ItemEntityRenderStateAccessor itemEntityRenderStateAccessor;
        class_10042 class_100422 = CELEBRESSION$CURRENT_RENDER_STATE.get();
        if (class_100422 == null) {
            return n;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return n;
        }
        HitColorModule hitColorModule = celebression.getModuleManager().getModule(HitColorModule.class);
        if (hitColorModule == null || !hitColorModule.isToggled()) {
            return n;
        }
        if (!(class_100422 instanceof ItemEntityRenderStateAccessor) || !(itemEntityRenderStateAccessor = (ItemEntityRenderStateAccessor)class_100422).celebression$isPlayer()) {
            return n;
        }
        float f = class_3532.method_15363((float)itemEntityRenderStateAccessor.celebression$getHitColorStrength(), (float)0.0f, (float)1.0f);
        if (f <= 0.001f) {
            return n;
        }
        return class_4608.field_21444;
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index=4)
    private int celebression$hitColorTint(int n) {
        class_10042 class_100422 = CELEBRESSION$CURRENT_RENDER_STATE.get();
        if (class_100422 == null) {
            return n;
        }
        return HitColorRenderHelper.tint(class_100422, n, false);
    }

        @Redirect(method={"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void celebression$cosmeticPoseRedirect(class_583<?> class_5832, class_4587 class_45872, class_4588 class_45882, int n, int n2, int n3, @Local(ordinal=0, argsOnly=true) class_10042 class_100422) {
        CosmeticPreviewTransformState.clear();
        boolean bl = false;
        if (aiG != null && LivingEntityRendererMixin.aiG.field_1724 != null && class_5832 instanceof class_591) {
            class_591 class_5912 = (class_591)class_5832;
            if (class_100422 instanceof class_10055) {
                float f;
                float f2;
                float f3;
                class_10055 class_100552 = (class_10055)class_100422;
                if (class_100552.field_53528 == LivingEntityRendererMixin.aiG.field_1724.method_5628()) {
                    float f4;
                    CosmeticPreviewTransformState.aX();
                    if (Naruto.enabled() && (f3 = class_3532.method_15363((float)((f4 = (float)LivingEntityRendererMixin.aiG.field_1724.method_18798().method_37267()) * 10.0f), (float)0.0f, (float)1.0f)) >= 0.1f && !class_100552.field_53412 && !class_100552.field_53411 && !class_100552.field_53414 && !class_100552.field_53410) {
                        class_591 class_5913 = class_5912;
                        f2 = aiG.method_61966().method_60637(true);
                        f = ((float)LivingEntityRendererMixin.aiG.field_1724.field_6012 + f2) * 0.6f;
                        float f5 = class_3532.method_15362((float)f) * 0.2f * f3;
                        float f6 = 1.25f + 0.45f * f3 + f5;
                        float f7 = 0.55f * f3;
                        float f8 = -0.15f * f3;
                        float f9 = 0.45f * f3;
                        class_5913.field_3401.field_3654 = f6;
                        class_5913.field_27433.field_3654 = f6;
                        class_5913.field_3401.field_3674 = f7;
                        class_5913.field_27433.field_3674 = -f7;
                        class_5913.field_3401.field_3675 += 0.1f * f3;
                        class_5913.field_27433.field_3675 -= 0.1f * f3;
                        class_5913.field_3398.field_3654 += f8;
                        float f10 = 0.75f;
                        class_45872.method_22903();
                        bl = true;
                        class_45872.method_46416(0.0f, f10, 0.0f);
                        class_45872.method_22907(class_7833.field_40714.rotation(f9));
                        class_45872.method_46416(0.0f, -f10, 0.0f);
                        CosmeticPreviewTransformState.a(f10, f9, f8);
                        CosmeticPreviewTransformState.b(f10, f9, f8);
                    }
                }
                if (!(!DrochkaModule.active() || class_100552.field_53412 || class_100552.field_53411 || class_100552.field_53414 || class_100552.field_53410)) {
                    class_591 class_5914 = class_5912;
                    f3 = aiG.method_61966().method_60637(true);
                    float f11 = (float)LivingEntityRendererMixin.aiG.field_1724.field_6012 + f3;
                    f2 = (float)(class_100552.field_53528 % 23) * 0.27f;
                    f = class_3532.method_15374((float)(f11 * DrochkaModule.iD() + f2)) * DrochkaModule.iE();
                    class_5914.field_3401.field_3654 = -0.3f + f;
                    class_5914.field_3401.field_3675 = 0.0f;
                    class_5914.field_3401.field_3674 = -0.55f;
                    if (DrochkaModule.iF()) {
                        class_5914.field_27433.field_3654 = -0.3f - f;
                        class_5914.field_27433.field_3675 = 0.0f;
                        class_5914.field_27433.field_3674 = 0.55f;
                    }
                }
                if (DrochkaModule.iG()) {
                    class_45872.method_22903();
                    class_5912.field_3391.method_22703(class_45872);
                    LivingEntityRendererMixin.celebression$breast().method_22699(class_45872, class_45882, n, n2, n3);
                    class_45872.method_22909();
                }
            }
        }
        try {
            class_5832.method_62100(class_45872, class_45882, n, n2, n3);
        }
        finally {
            if (bl) {
                class_45872.method_22909();
            }
        }
    }

    @Inject(method={"hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void viewOwnLabel(class_1309 class_13092, double d, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (CosmeticPreviewContext.aP()) {
            callbackInfoReturnable.setReturnValue(false);
            return;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        SelfNametagsModule selfNametagsModule = celebression.getModuleManager().getModule(SelfNametagsModule.class);
        if (selfNametagsModule == null || !selfNametagsModule.isToggle()) {
            return;
        }
        if (class_13092 == LivingEntityRendererMixin.aiG.field_1724) {
            callbackInfoReturnable.setReturnValue(class_310.method_1498());
        }
    }
}
