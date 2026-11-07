package ru.method.celebression.api.mixins;

import net.minecraft.class_1657;
import net.minecraft.class_742;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.modules.impl.render.cosmetic.CapeSimulation;
import ru.method.celebression.api.accessor.CapeSimulationAccessor;

@Mixin(value={class_1657.class})
public class PlayerEntityCapeMixin
implements CapeSimulationAccessor {
    @Unique
    private CapeSimulation celebression$capeSimulation;

    @Override
    public CapeSimulation celebression$capeSimulation() {
        if (this.celebression$capeSimulation == null) {
            this.celebression$capeSimulation = new CapeSimulation();
        }
        return this.celebression$capeSimulation;
    }

    @Inject(method={"updateCapeAngles"}, at={@At(value="HEAD")})
    private void celebression$tickCapeSimulation(CallbackInfo callbackInfo) {
        PlayerEntityCapeMixin playerEntityCapeMixin = this;
        if ((Object)playerEntityCapeMixin instanceof class_742) {
            class_742 class_7422 = (class_742)(Object)playerEntityCapeMixin;
            this.celebression$capeSimulation().c((class_1657)class_7422);
        }
    }
}
