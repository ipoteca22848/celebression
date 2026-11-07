package ru.method.celebression.api.mixins;

import net.minecraft.class_4185;
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
public class MultiplayerScreenButtonMixin {
    @Shadow
    protected class_4267 field_3043;
    @Shadow
    private class_4185 field_3041;
    @Shadow
    private class_4185 field_3047;

    @Inject(method={"updateButtonActivationStates"}, at={@At(value="TAIL")})
    private void onUpdateButtonStates(CallbackInfo callbackInfo) {
        class_4267.class_504 class_5042 = (class_4267.class_504)this.field_3043.method_25334();
        class_642 class_6422 = ServerEntryUtils.a(class_5042);
        if (class_6422 != null && ServerListSnapshot.q(class_6422)) {
            this.field_3041.field_22763 = false;
            this.field_3047.field_22763 = false;
        }
    }
}

