package ru.method.celebression.api.mixins;

import net.minecraft.class_2561;
import net.minecraft.class_437;
import net.minecraft.class_8671;
import org.spongepowered.asm.mixin.Mixin;
import ru.method.celebression.render.ServerEntryRenderer;

@Mixin(value={class_8671.class})
public abstract class ReconfiguringScreenMixin
extends class_437 {
    protected ReconfiguringScreenMixin(class_2561 class_25612) {
        super(class_25612);
    }

    public boolean method_25402(double d, double d2, int n) {
        if (ServerEntryRenderer.b(this)) {
            return true;
        }
        return super.method_25402(d, d2, n);
    }
}

