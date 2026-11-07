package ru.method.celebression.api.mixins;

import java.awt.Color;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_408;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.modules.impl.client.SecretDupeModule;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.debug.ChatProfiler;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

@Mixin(value={class_408.class})
public abstract class ChatScreenHintsMixin {
    @Shadow
    protected class_342 field_2382;
    @Unique
    private Animation celebression$hintsAnimation;
    @Unique
    private static final float celebression$HINT_MARGIN_X = 6.0f;
    @Unique
    private static final float celebression$HINT_MARGIN_Y = 8.0f;
    @Unique
    private static final float celebression$HOTBAR_HEIGHT = 22.0f;
    @Unique
    private static final float celebression$HOTBAR_HINT_OFFSET = 40.0f;

    @Inject(method={"sendMessage"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$noteChatCommand(String string, boolean bl, CallbackInfo callbackInfo) {
        if (SecretDupeModule.aZ(string)) {
            callbackInfo.cancel();
        }
    }

    @Unique
    private Animation celebression$getHintsAnimation() {
        if (this.celebression$hintsAnimation == null) {
            this.celebression$hintsAnimation = new Animation(260L, 1.0, true, Easing.aiZ);
        }
        return this.celebression$hintsAnimation;
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void celebression$init(CallbackInfo callbackInfo) {
        Animation animation = this.celebression$getHintsAnimation();
        animation.reset();
        animation.ae(true);
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void celebression$beginChatProfile(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        ChatProfiler.beginFrame();
        Render2D.prepareHudFrame(class_3322);
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void celebression$renderHints(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        Color color;
        long l = ChatProfiler.startSection();
        float f2 = this.celebression$getHintsAnimation().rk();
        if (f2 <= 0.01f) {
            ChatProfiler.endSection("hintPanel", l);
            return;
        }
        float f3 = UI.getInterfaceScale();
        float f4 = 7.5f * f3;
        String string = "\u0421\u041a\u041c \u043f\u043e \u043d\u0438\u043a\u0443/\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0430\u043c \u2014 \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u0442\u044c";
        float f5 = 8.0f * f3;
        float f6 = 5.0f * f3;
        float f7 = Fonts.ROUNDBOLD.getHeight(f4);
        float f8 = Fonts.ROUNDBOLD.getWidth(string, f4);
        float f9 = f8 + f5 * 2.0f;
        float f10 = f6 * 2.0f + f7;
        float f11 = class_3322.method_51421();
        float f12 = class_3322.method_51443();
        float f13 = f12 - 22.0f;
        float f14 = f13 - f10 - 40.0f * f3;
        float f15 = 14.0f * f3;
        float f16 = f14 - (1.0f - f2) * f15;
        float f17 = f11 / 2.0f - f9 / 2.0f;
        f17 = Math.max(6.0f, Math.min(f17, f11 - f9 - 6.0f));
        f16 = Math.max(8.0f, Math.min(f16, f12 - f10 - 8.0f));
        int n3 = Math.max(0, Math.min(255, Math.round(255.0f * f2)));
        boolean bl = HudThemeRenderer.active();
        if (bl) {
            HudThemeRenderer.b(class_3322.method_51448(), f17, f16, f9, f10, HudThemeRenderer.f(f9, f10), f2);
        } else {
            color = new Color(245, 245, 245, Math.round(200.0f * f2));
            if (ColorUtils.isBlurEnabled()) {
                Render2D.drawLiquidRect(class_3322.method_51448(), f17, f16, f9, f10, new BorderRadius(20.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
            } else {
                Render2D.drawRoundedRect(class_3322.method_51448(), f17, f16, f9, f10, 20.0f, color);
            }
        }
        color = bl ? HudThemeRenderer.d(n3) : new Color(255, 255, 255, Math.round(200.0f * f2));
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(f4), string, f17 + f5, f16 + (f10 - f7) / 2.0f, color);
        ChatProfiler.endSection("hintPanel", l);
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void celebression$endChatProfile(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        ChatProfiler.endFrame();
    }
}

