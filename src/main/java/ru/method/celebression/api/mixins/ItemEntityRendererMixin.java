package ru.method.celebression.api.mixins;

import net.minecraft.class_10017;
import net.minecraft.class_10039;
import net.minecraft.class_10444;
import net.minecraft.class_1542;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5617;
import net.minecraft.class_5819;
import net.minecraft.class_7833;
import net.minecraft.class_897;
import net.minecraft.class_916;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IEntityRendererShadow;
import ru.method.celebression.modules.impl.render.ItemPhysicsModule;
import ru.method.celebression.api.accessor.LivingEntityRenderStateAccessor;

@Mixin(value={class_916.class})
public abstract class ItemEntityRendererMixin
extends class_897<class_1542, class_10039> {
    @Shadow
    @Final
    private class_5819 field_4725;

    protected ItemEntityRendererMixin(class_5617.class_5618 class_56182) {
        super(class_56182);
    }

    @Unique
    private ItemPhysicsModule celebression$getItemPhysics() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return null;
        }
        return celebression.getModuleManager().getModule(ItemPhysicsModule.class);
    }

    @Inject(method={"updateRenderState"}, at={@At(value="TAIL")})
    private void celebression$capturePhysicsState(class_1542 class_15422, class_10039 class_100392, float f, CallbackInfo callbackInfo) {
        LivingEntityRenderStateAccessor livingEntityRenderStateAccessor = (LivingEntityRenderStateAccessor)class_100392;
        ItemPhysicsModule itemPhysicsModule = this.celebression$getItemPhysics();
        livingEntityRenderStateAccessor.celebression$setOnGround(class_15422.method_24828());
        livingEntityRenderStateAccessor.celebression$setHorizontalSpeed((float)Math.hypot(class_15422.method_18798().field_1352, class_15422.method_18798().field_1350));
        livingEntityRenderStateAccessor.celebression$setVerticalSpeed((float)class_15422.method_18798().field_1351);
        livingEntityRenderStateAccessor.celebression$setYRotation((float)Math.toRadians(class_15422.method_36454()));
        livingEntityRenderStateAccessor.celebression$setAdditionalOffset(false);
        if (itemPhysicsModule != null && itemPhysicsModule.isToggled()) {
            itemPhysicsModule.a(class_15422, class_100392, livingEntityRenderStateAccessor);
        }
    }

    @Inject(method={"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$renderItemPhysic(class_10039 class_100392, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        ItemPhysicsModule itemPhysicsModule = this.celebression$getItemPhysics();
        if (itemPhysicsModule == null || !itemPhysicsModule.isToggled() || class_100392.field_55310.method_65606() || class_100392.field_53328 < 1.0f) {
            return;
        }
        this.celebression$renderItemPhysicStack(class_100392, class_45872, class_45972, n, (LivingEntityRenderStateAccessor)class_100392, itemPhysicsModule);
        super.method_3936(class_100392, class_45872, class_45972, n);
        callbackInfo.cancel();
    }

    @Unique
    private void celebression$renderItemPhysicStack(class_10039 class_100392, class_4587 class_45872, class_4597 class_45972, int n, LivingEntityRenderStateAccessor livingEntityRenderStateAccessor, ItemPhysicsModule itemPhysicsModule) {
        class_45872.method_22903();
        this.field_4725.method_43052((long)class_100392.field_55312);
        int n2 = class_100392.field_55311;
        class_10444 class_104442 = class_100392.field_55310;
        boolean bl = class_104442.method_65607();
        class_45872.method_22907(class_7833.field_40714.rotation(1.5707964f));
        class_45872.method_22907(class_7833.field_40718.rotation(livingEntityRenderStateAccessor.celebression$getYRotation()));
        if (class_100392.field_53328 != 0.0f) {
            class_45872.method_22904(0.0, (double)itemPhysicsModule.a(class_100392), (double)itemPhysicsModule.a(class_100392, livingEntityRenderStateAccessor));
            if (bl) {
                class_45872.method_22904(0.0, (double)0.2f, 0.0);
            }
            class_45872.method_22907(class_7833.field_40716.rotation(livingEntityRenderStateAccessor.celebression$getXRotation()));
            if (bl) {
                class_45872.method_22904(0.0, (double)-0.2f, 0.0);
            }
        }
        itemPhysicsModule.a(class_45872, class_100392, livingEntityRenderStateAccessor);
        float f = class_104442.method_65609().field_4285.z();
        if (!bl) {
            class_45872.method_46416(0.0f, 0.0f, -0.09375f * (float)(n2 - 1) * 0.5f * f);
        }
        for (int i = 0; i < n2; ++i) {
            class_45872.method_22903();
            if (i > 0 && bl) {
                float f2 = (this.field_4725.method_43057() * 2.0f - 1.0f) * 0.15f;
                float f3 = (this.field_4725.method_43057() * 2.0f - 1.0f) * 0.15f;
                float f4 = (this.field_4725.method_43057() * 2.0f - 1.0f) * 0.15f;
                class_45872.method_46416(f2, f3, f4);
            }
            class_104442.method_65604(class_45872, class_45972, n, class_4608.field_21444);
            class_45872.method_22909();
            if (bl) continue;
            class_45872.method_46416(0.0f, 0.0f, 0.09375f * f);
        }
        class_45872.method_22909();
    }

    @Override
    protected float method_55831(class_10039 class_100392) {
        ItemPhysicsModule itemPhysicsModule = this.celebression$getItemPhysics();
        float f = ((IEntityRendererShadow)((Object)this)).getShadowRadiusValue();
        if (itemPhysicsModule == null) {
            return f;
        }
        return itemPhysicsModule.o(f);
    }

    @Override
    protected float method_65247(class_10039 class_100392) {
        ItemPhysicsModule itemPhysicsModule = this.celebression$getItemPhysics();
        float f = ((IEntityRendererShadow)((Object)this)).getShadowOpacityValue();
        if (itemPhysicsModule == null) {
            return f;
        }
        return itemPhysicsModule.p(f);
    }
}
