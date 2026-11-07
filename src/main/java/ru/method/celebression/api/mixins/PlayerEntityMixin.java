package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.util.PlayerNameSanitizer;
import ru.method.celebression.modules.impl.render.SelfNametagsModule;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.api.event.TravelRotationEvent;

@Mixin(value={class_1657.class})
public abstract class PlayerEntityMixin
extends class_1309
implements MinecraftClientAccess {
    protected PlayerEntityMixin(class_1299<? extends class_1309> class_12992, class_1937 class_19372) {
        super(class_12992, class_19372);
    }

    @WrapOperation(method={"travel"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;")})
    public class_243 travel(class_1657 class_16572, Operation<class_243> operation) {
        if (class_16572 == PlayerEntityMixin.aiG.field_1724) {
            TravelRotationEvent travelRotationEvent = new TravelRotationEvent(this.method_36454(), this.method_36455());
            Celebression.getInstance().getEventHandler().post((Object)travelRotationEvent);
            return this.method_5631(travelRotationEvent.s(), travelRotationEvent.r());
        }
        return (class_243)operation.call(new Object[]{class_16572});
    }

    @Inject(method={"getDisplayName"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$streamerDisplayName(CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        class_1657 class_16572 = (class_1657)(Object)this;
        class_310 class_3102 = class_310.method_1551();
        if (CosmeticPreviewContext.aP() && class_3102 != null && class_16572 == class_3102.field_1724) {
            callbackInfoReturnable.setReturnValue(class_2561.method_43473());
            return;
        }
        class_2561 class_25612 = PlayerNameSanitizer.a((class_2561)callbackInfoReturnable.getReturnValue(), class_16572.method_7334().getId(), class_16572.method_7334().getName());
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && class_3102 != null && class_16572 == class_3102.field_1724) {
            boolean bl;
            SelfNametagsModule selfNametagsModule = celebression.getModuleManager().getModule(SelfNametagsModule.class);
            boolean bl2 = bl = selfNametagsModule != null && selfNametagsModule.isToggle();
            if (bl) {
                class_25612 = StreamerTextSanitizer.n(class_25612);
            }
        }
        callbackInfoReturnable.setReturnValue(class_25612);
    }
}

