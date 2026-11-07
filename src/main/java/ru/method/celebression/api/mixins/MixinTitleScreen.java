package ru.method.celebression.api.mixins;

import net.minecraft.class_437;
import net.minecraft.class_442;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;

@Mixin(value={class_442.class})
public abstract class MixinTitleScreen
implements MinecraftClientAccess {
    @Inject(method={"init"}, at={@At(value="RETURN")})
    private void celebressions$redirectToCustom(CallbackInfo callbackInfo) {
        if (aiG == null || ru.method.celebression.service.UnhookService.isActive()) {
            return;
        }
        if (MixinTitleScreen.aiG.field_1755 instanceof CustomMainMenuScreen) {
            return;
        }
        aiG.execute(() -> {
            if (ru.method.celebression.service.UnhookService.isActive()) return;
            class_437 class_4372 = MixinTitleScreen.aiG.field_1755;
            if (class_4372 instanceof class_442 && !(class_4372 instanceof CustomMainMenuScreen)) {
                aiG.method_1507((class_437)new CustomMainMenuScreen());
            }
        });
    }
}

