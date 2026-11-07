package ru.method.celebression.api.mixins.accessors;

import java.util.Map;
import java.util.UUID;
import net.minecraft.class_634;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_634.class})
public interface IClientPlayNetworkHandler {
    @Accessor(value="playerListEntries")
    public Map<UUID, class_640> celebression$getPlayerListEntries();
}

