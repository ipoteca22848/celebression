package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_746;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.CameraAnimationState;

@Mixin(value={class_4184.class})
public abstract class CameraMixin {
    @Shadow
    @Final
    private static Vector3f field_52123;
    @Shadow
    @Final
    private static Vector3f field_52124;
    @Shadow
    @Final
    private static Vector3f field_52125;
    @Shadow
    private class_243 field_18712;
    @Shadow
    @Final
    private Quaternionf field_21518;
    @Shadow
    @Final
    private Vector3f field_18714;
    @Shadow
    @Final
    private Vector3f field_18715;
    @Shadow
    @Final
    private Vector3f field_18716;
    @Shadow
    private class_1297 field_18711;

    @Shadow
    protected abstract void method_19322(class_243 var1);

    @Inject(method={"update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"}, at={@At(value="TAIL")})
    private void punchy$applyCameraAnimation(class_1922 class_19222, class_1297 class_12972, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044().method_31034() && this.field_18711 instanceof class_746 && class_3102.field_1724 == this.field_18711 && CameraAnimationState.isActive()) {
            Vector3f vector3f = CameraAnimationState.offset();
            if (vector3f.lengthSquared() > 0.0f) {
                vector3f.rotate((Quaternionfc)this.field_21518);
                this.method_19322(this.field_18712.method_1031((double)vector3f.x, (double)vector3f.y, (double)vector3f.z));
            }
            Quaternionf quaternionf = CameraAnimationState.rotation();
            if (Math.abs(quaternionf.w - 1.0f) > 1.0E-4f || quaternionf.x != 0.0f || quaternionf.y != 0.0f || quaternionf.z != 0.0f) {
                Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)this.field_21518).mul((Quaternionfc)quaternionf);
                this.field_21518.set((Quaternionfc)quaternionf2);
                this.field_18714.set((Vector3fc)field_52123).rotate((Quaternionfc)this.field_21518);
                this.field_18715.set((Vector3fc)field_52124).rotate((Quaternionfc)this.field_21518);
                this.field_18716.set((Vector3fc)field_52125).rotate((Quaternionfc)this.field_21518);
            }
        }
    }
}

