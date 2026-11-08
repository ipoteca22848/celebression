package ru.method.celebression.api.mixins;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.class_156;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4011;
import net.minecraft.class_4071;
import net.minecraft.class_425;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.render.ColorMath;
import ru.method.celebression.render.MainMenuBrandRenderer;

@Mixin(value={class_425.class})
public abstract class SplashOverlayMixin
extends class_4071 {
    @Unique
    private static final Logger celebression$LOGGER = LoggerFactory.getLogger((String)"Celebression/Splash");
    @Unique
    private static final float celebression$HOLD = 0.12f;
    @Unique
    private static final float celebression$SLIDE = 0.45f;
    @Unique
    private static boolean celebression$broken;
    @Unique
    private long celebression$lastFrameMs;
    @Shadow
    @Final
    private class_310 field_18217;
    @Shadow
    @Final
    private class_4011 field_17767;
    @Shadow
    @Final
    private Consumer<Optional<Throwable>> field_18218;
    @Shadow
    @Final
    private boolean field_18219;
    @Shadow
    private float field_17770;
    @Shadow
    private long field_17771;
    @Shadow
    private long field_18220;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$customSplash(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return;
        if (celebression$broken) {
            return;
        }
        try {
            long l = class_156.method_658();
            if (this.field_18219 && this.field_18220 == -1L) {
                this.field_18220 = l;
            }
            float f2 = this.field_17771 > -1L ? (float)(l - this.field_17771) / 1000.0f : -1.0f;
            float f3 = this.field_18220 > -1L ? (float)(l - this.field_18220) / 500.0f : -1.0f;
            float f4 = this.celebression$lastFrameMs == 0L ? 0.016f : Math.min(0.1f, (float)(l - this.celebression$lastFrameMs) / 1000.0f);
            this.celebression$lastFrameMs = l;
            float f5 = this.field_17771 > -1L || this.field_17767.method_18787() ? 10.0f : 2.2f;
            float f6 = 1.0f - (float)Math.exp(-f5 * f4);
            this.field_17770 = class_3532.method_15363((float)(this.field_17770 + (this.field_17767.method_18229() - this.field_17770) * f6), (float)0.0f, (float)1.0f);
            if (this.field_17771 == -1L && this.field_17767.method_18787() && (!this.field_18219 || f3 >= 2.0f)) {
                try {
                    this.field_17767.method_18849();
                    this.field_18218.accept(Optional.empty());
                }
                catch (Throwable throwable) {
                    this.field_18218.accept(Optional.of(throwable));
                }
                this.field_17771 = class_156.method_658();
                ColorMath.ry();
                if (this.field_18217.field_1755 != null) {
                    this.field_18217.field_1755.method_25423(this.field_18217, class_3322.method_51421(), class_3322.method_51443());
                }
                f2 = 0.0f;
            }
            float f7 = 1.0f;
            float f8 = 0.0f;
            if (f2 >= 0.0f) {
                f8 = class_3532.method_15363((float)((f2 - 0.12f) / 0.45f), (float)0.0f, (float)1.0f);
                if (this.field_18217.field_1755 != null) {
                    this.field_18217.field_1755.method_25394(class_3322, n, n2, f);
                }
            } else if (this.field_18219) {
                f7 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
                if (this.field_18217.field_1755 != null && f3 < 1.0f) {
                    this.field_18217.field_1755.method_25394(class_3322, n, n2, f);
                }
            }
            MainMenuBrandRenderer.a(class_3322, this.field_17770, f7, f8);
            if (f8 >= 1.0f) {
                MainMenuBrandRenderer.qc();
                this.field_18217.method_18502(null);
            }
            callbackInfo.cancel();
        }
        catch (Throwable throwable) {
            celebression$broken = true;
            celebression$LOGGER.error("[splash] \u043a\u0430\u0441\u0442\u043e\u043c\u043d\u044b\u0439 \u0437\u0430\u0433\u0440\u0443\u0437\u043e\u0447\u043d\u044b\u0439 \u044d\u043a\u0440\u0430\u043d \u043e\u0442\u043a\u043b\u044e\u0447\u0451\u043d \u043f\u043e\u0441\u043b\u0435 \u0441\u0431\u043e\u044f", throwable);
            try {
                MainMenuBrandRenderer.qc();
            }
            catch (Throwable throwable2) {
            }
        }
    }
}

