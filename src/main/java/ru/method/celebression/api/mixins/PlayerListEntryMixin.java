package ru.method.celebression.api.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.class_2561;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.util.PlayerNameSanitizer;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.utils.debug.TabProfiler;

@Mixin(value={class_640.class})
public abstract class PlayerListEntryMixin {
    @Shadow
    public abstract GameProfile method_2966();

    @Inject(method={"getDisplayName"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$sanitizeDisplayNameGet(CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        if (TabProfiler.isActive()) {
            return;
        }
        GameProfile gameProfile = this.method_2966();
        class_2561 class_25612 = StreamerTextSanitizer.a((class_2561)callbackInfoReturnable.getReturnValue(), gameProfile == null ? null : gameProfile.getName());
        callbackInfoReturnable.setReturnValue(PlayerNameSanitizer.b(class_25612, gameProfile == null ? null : gameProfile.getId(), gameProfile == null ? null : gameProfile.getName()));
    }
}

