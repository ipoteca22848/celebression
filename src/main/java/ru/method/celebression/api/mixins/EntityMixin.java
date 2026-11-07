package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.event.RaycastRotationEvent;
import ru.method.celebression.api.event.TravelRotationEvent;

@Mixin(value={class_1297.class})
public abstract class EntityMixin
implements MinecraftClientAccess {
    @WrapOperation(method={"raycast"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;")})
    public class_243 raycast(class_1297 class_12972, float f, Operation<class_243> operation) {
        if (class_12972 == EntityMixin.aiG.field_1724) {
            RaycastRotationEvent raycastRotationEvent = new RaycastRotationEvent(class_12972.method_36454(), class_12972.method_36455());
            Celebression.getInstance().getEventHandler().post((Object)raycastRotationEvent);
            if (raycastRotationEvent.isCancelled()) {
                return class_12972.method_5631(class_12972.method_36455(), class_12972.method_36454());
            }
        }
        return (class_243)operation.call(new Object[]{class_12972, Float.valueOf(f)});
    }

    @WrapOperation(method={"updateVelocity"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getYaw()F")})
    public float updateVelocity(class_1297 class_12972, Operation<Float> operation) {
        if (class_12972 == EntityMixin.aiG.field_1724) {
            TravelRotationEvent travelRotationEvent = new TravelRotationEvent(class_12972.method_36454(), class_12972.method_36455());
            Celebression.getInstance().getEventHandler().post((Object)travelRotationEvent);
            return travelRotationEvent.r();
        }
        return ((Float)operation.call(new Object[]{class_12972})).floatValue();
    }
}

