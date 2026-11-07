package ru.method.celebression.api.mixins;

import net.minecraft.class_2535;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_412;
import net.minecraft.class_437;
import net.minecraft.class_639;
import net.minecraft.class_642;
import net.minecraft.class_9112;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.render.ServerEntryRenderer;
import ru.method.celebression.screen.mainmenu.MenuChrome;

@Mixin(value={class_412.class})
public abstract class ConnectScreenMixin
extends class_437 {
    @Shadow
    private class_2561 field_2413;
    @Shadow
    @Final
    class_437 field_2412;
    @Shadow
    volatile class_2535 field_2411;
    @Shadow
    volatile boolean field_2409;
    @Unique
    private static String celebression$name;
    @Unique
    private static String celebression$addr;

    protected ConnectScreenMixin(class_2561 class_25612) {
        super(class_25612);
    }

    @Inject(method={"connect(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;ZLnet/minecraft/client/network/CookieStorage;)V"}, at={@At(value="HEAD")})
    private static void celebression$captureTarget(class_437 class_4372, class_310 class_3102, class_639 class_6392, class_642 class_6422, boolean bl, class_9112 class_91122, CallbackInfo callbackInfo) {
        String string = celebression$name = class_6422 != null ? class_6422.field_3752 : null;
        celebression$addr = class_6392 != null ? class_6392.method_2952() + ":" + class_6392.method_2954() : (class_6422 != null ? class_6422.field_3761 : "");
        ServerEntryRenderer.u(celebression$name, celebression$addr);
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$render(class_332 class_3322, int n, int n2, float f, CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        ServerEntryRenderer.a(class_3322, n, n2, this.field_2413, celebression$name, celebression$addr);
    }

    public boolean method_25402(double d, double d2, int n) {
        float f;
        float f2;
        if (n == 0 && MenuChrome.in(ServerEntryRenderer.ada, f2 = (float)(d / (double)ServerEntryRenderer.uiScale), f = (float)(d2 / (double)ServerEntryRenderer.uiScale))) {
            this.celebression$cancel();
        }
        return true;
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (n == 256) {
            this.celebression$cancel();
            return true;
        }
        return false;
    }

    @Unique
    private void celebression$cancel() {
        this.field_2409 = true;
        if (this.field_2411 != null) {
            this.field_2411.method_10747(class_412.field_44970);
        }
        this.field_22787.method_1507(this.field_2412);
    }
}

