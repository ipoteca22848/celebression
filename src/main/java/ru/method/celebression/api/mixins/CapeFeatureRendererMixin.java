package ru.method.celebression.api.mixins;

import net.minecraft.class_10055;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_972;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CapeMeshRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CapeSimulation;
import ru.method.celebression.api.accessor.CapeSimulationStateAccessor;

@Mixin(value={class_972.class})
public class CapeFeatureRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$waveCape(class_4587 class_45872, class_4597 class_45972, int n, class_10055 class_100552, float f, float f2, CallbackInfo callbackInfo) {
        if (class_100552 == null || class_100552.field_53333 || !class_100552.field_53532 || class_100552.field_53520 == null) {
            return;
        }
        class_2960 class_29602 = class_100552.field_53520.comp_1627();
        if (class_29602 == null || !(class_100552 instanceof CapeSimulationStateAccessor)) {
            return;
        }
        CapeSimulationStateAccessor capeSimulationStateAccessor = (CapeSimulationStateAccessor)class_100552;
        CapeSimulation capeSimulation = capeSimulationStateAccessor.celebression$capeSimulation();
        if (capeSimulation == null || !capeSimulation.bD()) {
            return;
        }
        try {
            CapeMeshRenderer.a(class_45872, class_45972, n, class_29602, capeSimulation, capeSimulationStateAccessor.celebression$capeTickDelta(), capeSimulationStateAccessor.celebression$capeCrouching());
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[WaveCape] render failed: {}", (Object)throwable.toString());
            return;
        }
        callbackInfo.cancel();
    }
}

