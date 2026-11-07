package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1297;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.FreeLookModule;

@Mixin(value={class_4184.class})
public abstract class CameraMixin {
    @WrapOperation(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getYaw(F)F")})
    private float celebression$useFreelookYaw(class_1297 class_12972, float f, Operation<Float> operation) {
        FreeLookModule freeLookModule = Celebression.getInstance().getModuleManager().getModule(FreeLookModule.class);
        if (freeLookModule != null && freeLookModule.c(class_12972)) {
            return freeLookModule.iN();
        }
        return ((Float)operation.call(new Object[]{class_12972, Float.valueOf(f)})).floatValue();
    }

    @WrapOperation(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getPitch(F)F")})
    private float celebression$useFreelookPitch(class_1297 class_12972, float f, Operation<Float> operation) {
        FreeLookModule freeLookModule = Celebression.getInstance().getModuleManager().getModule(FreeLookModule.class);
        if (freeLookModule != null && freeLookModule.c(class_12972)) {
            return freeLookModule.iO();
        }
        return ((Float)operation.call(new Object[]{class_12972, Float.valueOf(f)})).floatValue();
    }
}

