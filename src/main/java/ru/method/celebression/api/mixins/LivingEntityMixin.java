package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.JumpYawEvent;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.event.TravelRotationEvent;

@Mixin(value={class_1309.class})
public abstract class LivingEntityMixin
extends class_1297
implements MinecraftClientAccess {
    public LivingEntityMixin(class_1299<?> class_12992, class_1937 class_19372) {
        super(class_12992, class_19372);
    }

    @WrapOperation(method={"jump"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;getYaw()F")})
    public float jump(class_1309 class_13092, Operation<Float> operation) {
        float f = ((Float)operation.call(new Object[]{class_13092})).floatValue();
        if (class_13092 == LivingEntityMixin.aiG.field_1724) {
            JumpYawEvent jumpYawEvent = new JumpYawEvent(f);
            Celebression.getInstance().getEventHandler().post((Object)jumpYawEvent);
            return jumpYawEvent.r();
        }
        return f;
    }

    @WrapOperation(method={"calcGlidingVelocity"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;")})
    public class_243 calcGlidingVelocity(class_1309 class_13092, Operation<class_243> operation) {
        if (class_13092 == LivingEntityMixin.aiG.field_1724) {
            TravelRotationEvent travelRotationEvent = new TravelRotationEvent(class_13092.method_36454(), class_13092.method_36455());
            Celebression.getInstance().getEventHandler().post((Object)travelRotationEvent);
            return this.method_5631(travelRotationEvent.s(), travelRotationEvent.r());
        }
        return (class_243)operation.call(new Object[]{class_13092});
    }
}

