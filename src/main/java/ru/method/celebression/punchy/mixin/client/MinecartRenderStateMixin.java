package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10045;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.method.celebression.punchy.client.render.MinecartLightState;

@Mixin(value={class_10045.class})
public class MinecartRenderStateMixin
implements MinecartLightState {
    @Unique
    private int punchy$light = -1;

    @Override
    public void punchy$setLight(int n) {
        this.punchy$light = n;
    }

    @Override
    public int punchy$getLight() {
        return this.punchy$light;
    }
}

