package ru.method.celebression.api.mixins;

import net.minecraft.class_332;
import net.minecraft.class_337;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.modules.impl.render.NoRenderModule;

@Mixin(value={class_337.class})
public abstract class BossBarHudMixin {
    private static final float DYNAMIC_ISLAND_BOSSBAR_OFFSET = 19.0f;

    private static boolean celebression$islandPushesBossBar() {
        if (HudThemeRenderer.active()) {
            return false;
        }
        DynamicIslandHud dynamicIslandHud = Celebression.getInstance().getHudManager().a(DynamicIslandHud.class);
        return dynamicIslandHud != null && dynamicIslandHud.isToggled() && !dynamicIslandHud.bL();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    public void preRender(class_332 class_3322, CallbackInfo callbackInfo) {
        NoRenderModule noRenderModule = Celebression.getInstance().getModuleManager().getModule(NoRenderModule.class);
        if (noRenderModule != null && noRenderModule.isToggled() && ((Boolean)noRenderModule.Fd.jT()).booleanValue()) {
            callbackInfo.cancel();
            return;
        }
        if (BossBarHudMixin.celebression$islandPushesBossBar()) {
            class_3322.method_51448().method_22903();
            class_3322.method_51448().method_46416(0.0f, 19.0f, 0.0f);
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    public void postRender(class_332 class_3322, CallbackInfo callbackInfo) {
        if (BossBarHudMixin.celebression$islandPushesBossBar()) {
            class_3322.method_51448().method_22909();
        }
    }
}

