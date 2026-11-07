package ru.method.celebression.api.mixins.accessors;

import net.minecraft.class_4184;
import net.minecraft.class_757;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_757.class})
public interface IGameRenderer {
    @Invoker(value="getFov")
    public float getFov$drug(class_4184 var1, float var2, boolean var3);
}

