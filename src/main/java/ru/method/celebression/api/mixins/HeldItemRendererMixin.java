package ru.method.celebression.api.mixins;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1812;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import net.minecraft.class_9334;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.render.SwingAnimationModule;
import ru.method.celebression.modules.impl.render.ViewModelModule;

@Mixin(value={class_759.class})
public abstract class HeldItemRendererMixin
implements MinecraftClientAccess {
    @Shadow
    @Final
    private class_310 field_4050;
    @Shadow
    private class_1799 field_4047;
    @Shadow
    private float field_4043;
    @Shadow
    private float field_4053;
    @Shadow
    private float field_4051;
    @Shadow
    private float field_4052;
    @Shadow
    private class_1799 field_4048;

    @Shadow
    protected abstract void method_3228(class_742 var1, float var2, float var3, class_1268 var4, float var5, class_1799 var6, float var7, class_4587 var8, class_4597 var9, int var10);

    @Shadow
    protected abstract void method_65816(float var1, float var2, class_4587 var3, int var4, class_1306 var5);

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
    public void renderFirstPersonItem(class_742 class_7422, float f, float f2, class_1268 class_12682, float f3, class_1799 class_17992, float f4, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        ViewModelModule viewModelModule = Celebression.getInstance().getModuleManager().getModule(ViewModelModule.class);
        if (viewModelModule != null && viewModelModule.isToggled()) {
            boolean bl;
            boolean bl2 = this.field_4050.field_1724.method_6030().method_7909() == class_17992.method_7909();
            boolean bl3 = bl = class_17992.method_57826(class_9334.field_50075) || class_17992.method_7909() instanceof class_1812;
            if (class_12682 == class_1268.field_5808) {
                float f5;
                float f6 = bl && bl2 ? 0.0f : -((Float)viewModelModule.Ip.jT()).floatValue();
                float f7 = f5 = bl && bl2 ? 0.0f : ((Float)viewModelModule.Ir.jT()).floatValue();
                if (this.field_4050.field_1724.method_6068() == class_1306.field_6182) {
                    f6 = -f6;
                }
                class_45872.method_46416(f6, ((Float)viewModelModule.Iq.jT()).floatValue(), f5);
                class_45872.method_22905(((Float)viewModelModule.Is.jT()).floatValue(), ((Float)viewModelModule.It.jT()).floatValue(), ((Float)viewModelModule.Iu.jT()).floatValue());
            } else {
                float f8;
                float f9 = bl && bl2 ? 0.0f : ((Float)viewModelModule.Iv.jT()).floatValue();
                float f10 = f8 = bl && bl2 ? 0.0f : ((Float)viewModelModule.Ix.jT()).floatValue();
                if (this.field_4050.field_1724.method_6068() == class_1306.field_6182) {
                    f9 = -f9;
                }
                class_45872.method_46416(f9, ((Float)viewModelModule.Iw.jT()).floatValue(), f8);
                class_45872.method_22905(((Float)viewModelModule.Iy.jT()).floatValue(), ((Float)viewModelModule.Iz.jT()).floatValue(), ((Float)viewModelModule.IA.jT()).floatValue());
            }
        }
    }

    @Redirect(method={"renderFirstPersonItem"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FFLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V", ordinal=2))
    private void celebression$applySwingAnimation(class_759 class_7592, float f, float f2, class_4587 class_45872, int n, class_1306 class_13062) {
        class_1306 class_13063;
        SwingAnimationModule swingAnimationModule = Celebression.getInstance().getModuleManager().getModule(SwingAnimationModule.class);
        class_1306 class_13064 = class_13063 = this.field_4050.field_1724 != null ? this.field_4050.field_1724.method_6068() : class_1306.field_6183;
        if (swingAnimationModule != null && swingAnimationModule.isToggled() && class_13062 == class_13063) {
            swingAnimationModule.a(class_45872, f, f2, class_13062);
        } else {
            this.method_65816(f, f2, class_45872, n, class_13062);
        }
    }
}

