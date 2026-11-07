package ru.method.celebression.api.mixins;

import net.minecraft.class_4267;
import net.minecraft.class_500;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.server.ServerEntryUtils;
import ru.method.celebression.server.ServerListSnapshot;

@Mixin(value={class_500.class})
public class MultiplayerScreenEditMixin {
    @Shadow
    protected class_4267 field_3043;

    @Inject(method={"editEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void onEdit(CallbackInfo callbackInfo) {
        if (this.isProtectedSelection()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"removeEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRemoveEntry(CallbackInfo callbackInfo) {
        if (this.isProtectedSelection()) {
            callbackInfo.cancel();
        }
    }

    private boolean isProtectedSelection() {
        class_4267.class_504 class_5042 = (class_4267.class_504)this.field_3043.method_25334();
        class_642 class_6422 = ServerEntryUtils.a(class_5042);
        return class_6422 != null && ServerListSnapshot.q(class_6422);
    }
}

