package ru.method.celebression.integration.ui.mixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(MinecraftClient.class)
public interface SessionAccessor {
    @Mutable @Accessor("session") void celebression$setSession(Session session);
}
