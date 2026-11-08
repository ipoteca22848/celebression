package ru.method.celebression.integration.ui.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.service.AppearanceMode;
import ru.method.celebression.service.UnhookService;
import ru.method.celebression.integration.ui.bridge.ThemeSwitch;

@Mixin(TitleScreen.class)
public abstract class AppearanceTitleScreenMixin extends Screen {
    protected AppearanceTitleScreenMixin(Text title) { super(title); }
    @Inject(method = "init", at = @At("RETURN"))
    private void celebression$appearanceSwitch(CallbackInfo callback) {
        if (!AppearanceMode.isCustom() && !UnhookService.isActive()) addDrawableChild(ThemeSwitch.button(width));
    }
}
