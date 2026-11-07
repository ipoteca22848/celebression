package ru.method.celebression.api.mixins;

import net.minecraft.class_10055;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.method.celebression.modules.impl.render.cosmetic.CapeSimulation;
import ru.method.celebression.api.accessor.CapeSimulationStateAccessor;

@Mixin(value={class_10055.class})
public class PlayerEntityRenderStateCapeMixin
implements CapeSimulationStateAccessor {
    @Unique
    private CapeSimulation celebression$capeSimulation;
    @Unique
    private float celebression$capeTickDelta;
    @Unique
    private boolean celebression$capeCrouching;

    @Override
    public CapeSimulation celebression$capeSimulation() {
        return this.celebression$capeSimulation;
    }

    @Override
    public void celebression$setCapeSimulation(CapeSimulation capeSimulation) {
        this.celebression$capeSimulation = capeSimulation;
    }

    @Override
    public float celebression$capeTickDelta() {
        return this.celebression$capeTickDelta;
    }

    @Override
    public void celebression$setCapeTickDelta(float f) {
        this.celebression$capeTickDelta = f;
    }

    @Override
    public boolean celebression$capeCrouching() {
        return this.celebression$capeCrouching;
    }

    @Override
    public void celebression$setCapeCrouching(boolean bl) {
        this.celebression$capeCrouching = bl;
    }
}

