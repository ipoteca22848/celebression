package ru.method.celebression.api.mixins;

import net.minecraft.class_243;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.SkyChangerModule;

@Mixin(value={class_638.class})
public abstract class ClientWorldMixin {
    @Inject(method={"getSkyColor(Lnet/minecraft/util/math/Vec3d;F)I"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$skyChanger(class_243 class_2432, float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        SkyChangerModule skyChangerModule = Celebression.getInstance().getModuleManager().getModule(SkyChangerModule.class);
        if (skyChangerModule != null && skyChangerModule.isToggled()) {
            callbackInfoReturnable.setReturnValue(skyChangerModule.ae((Integer)callbackInfoReturnable.getReturnValue()));
        }
    }
}

