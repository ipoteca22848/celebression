package ru.method.celebression.api.mixins;

import net.minecraft.class_10042;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.method.celebression.api.accessor.ItemEntityRenderStateAccessor;

@Mixin(value={class_10042.class})
public class LivingEntityRenderStateMixin
implements ItemEntityRenderStateAccessor {
    @Unique
    private boolean celebression$isPlayer;
    @Unique
    private float celebression$hitColorStrength;

    @Override
    public boolean celebression$isPlayer() {
        return this.celebression$isPlayer;
    }

    @Override
    public void celebression$setPlayer(boolean bl) {
        this.celebression$isPlayer = bl;
    }

    @Override
    public float celebression$getHitColorStrength() {
        return this.celebression$hitColorStrength;
    }

    @Override
    public void celebression$setHitColorStrength(float f) {
        this.celebression$hitColorStrength = f;
    }
}

