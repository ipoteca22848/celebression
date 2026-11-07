package ru.method.celebression.api.mixins;

import net.minecraft.class_332;
import net.minecraft.class_434;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.render.ServerEntryRenderer;

@Mixin(value={class_434.class})
public abstract class DownloadingTerrainScreenMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$connectFlowSkin(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        class_437 class_4372 = (class_437)(Object)this;
        if (!ServerEntryRenderer.b(class_4372)) {
            return;
        }
        callbackInfo.cancel();
        ServerEntryRenderer.a(class_3322, n, n2, ServerEntryRenderer.c(class_4372), ServerEntryRenderer.qa(), ServerEntryRenderer.qb(), false);
    }
}

