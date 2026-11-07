package ru.method.celebression.api.mixins;

import net.minecraft.class_6367;
import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.FullbrightModule;

@Mixin(value={class_765.class})
public abstract class LightmapTextureManagerMixin {
    @Final
    @Shadow
    private class_6367 field_53101;

    @Inject(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gl/SimpleFramebuffer;endWrite()V", shift=At.Shift.BEFORE)})
    public void update(float f, CallbackInfo callbackInfo) {
        FullbrightModule fullbrightModule = Celebression.getInstance().getModuleManager().getModule(FullbrightModule.class);
        if (fullbrightModule != null && fullbrightModule.isToggled()) {
            this.field_53101.method_1230();
        }
    }
}

