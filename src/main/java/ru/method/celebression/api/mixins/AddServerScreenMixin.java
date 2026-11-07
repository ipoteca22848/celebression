package ru.method.celebression.api.mixins;

import net.minecraft.class_422;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.server.ServerListSnapshot;

@Mixin(value={class_422.class})
public class AddServerScreenMixin {
    @Shadow
    @Final
    private class_642 field_2469;

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        if (ServerListSnapshot.q(this.field_2469)) {
            class_422 class_4222 = (class_422)(Object)this;
            class_4222.method_25419();
        }
    }

    @Inject(method={"addAndClose"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddAndClose(CallbackInfo callbackInfo) {
        if (ServerListSnapshot.q(this.field_2469)) {
            callbackInfo.cancel();
        }
    }
}

