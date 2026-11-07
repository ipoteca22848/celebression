package ru.method.celebression.api.mixins.accessors;

import java.util.List;
import net.minecraft.class_641;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_641.class})
public interface IServerList {
    @Accessor(value="servers")
    public List<class_642> celebression$getServers();
}

