package ru.method.celebression.integration.ui.mixin;

import net.minecraft.client.option.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.server.ServerAds;

@Mixin(ServerList.class)
public abstract class ServerListAdvertisingMixin {
    @Inject(method = "loadFile", at = @At("RETURN"))
    private void celebression$removePromotions(CallbackInfo callback) {
        ServerList list = (ServerList)(Object)this;
        boolean changed = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            var server = list.get(i);
            if (ServerAds.isPromotion(server.name, server.address)) {
                list.remove(server);
                changed = true;
            }
        }
        if (changed) list.saveFile();
    }
}
