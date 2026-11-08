package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4071;
import net.minecraft.class_419;
import net.minecraft.class_425;
import net.minecraft.class_433;
import net.minecraft.class_437;
import net.minecraft.class_442;
import net.minecraft.class_500;
import net.minecraft.class_5250;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.DisconnectedScreenAccessor;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBlur;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.render.ColorMath;
import ru.method.celebression.screen.DisconnectedScreen;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.PauseMenuScreen;
import ru.method.celebression.render.ServerEntryRenderer;
import ru.method.celebression.render.SettingsEntryRenderer;
import ru.method.celebression.util.Stopwatch;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;
import ru.method.celebression.utils.render.RenderFrameTracker;
import ru.method.celebression.utils.render.WorldSnapshotCache;

@Mixin(value={class_310.class})
public abstract class MinecraftClientMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void celebression$beginFrame(boolean bl, CallbackInfo callbackInfo) {
        RenderFrameTracker.beginFrame();
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")})
    private void celebression$endJoinFlow(class_437 class_4372, CallbackInfo callbackInfo) {
        if (class_4372 == null || class_4372 instanceof class_442 || class_4372 instanceof class_419) {
            ServerEntryRenderer.pZ();
        }
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$redirectDisconnected(class_437 class_4372, CallbackInfo callbackInfo) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return;
        if (class_4372 instanceof class_419) {
            class_2561 class_25612;
            class_419 class_4192 = (class_419)class_4372;
            class_310 class_3102 = (class_310)(Object)this;
            try {
                class_25612 = ((DisconnectedScreenAccessor)class_4192).celebression$getInfo().comp_2853();
            }
            catch (Throwable throwable) {
                class_25612 = class_2561.method_43473();
            }
            boolean bl = class_3102.method_29043();
            class_642 class_6422 = class_3102.method_1558();
            callbackInfo.cancel();
            class_3102.method_1507((class_437)new DisconnectedScreen(class_4192.method_25440(), class_25612, bl, class_6422));
        }
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$redirectMultiplayer(class_437 class_4372, CallbackInfo callbackInfo) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return;
        if (class_4372 instanceof class_500) {
            callbackInfo.cancel();
            ((class_310)(Object)this).method_1507((class_437)new MultiplayerScreen(null));
        }
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$redirectPauseMenu(class_437 class_4372, CallbackInfo callbackInfo) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return;
        class_433 class_4332;
        if (class_4372 instanceof class_433 && (class_4332 = (class_433)class_4372).method_53558()) {
            callbackInfo.cancel();
            ((class_310)(Object)this).method_1507((class_437)new PauseMenuScreen());
        }
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$redirectOptions(class_437 class_4372, CallbackInfo callbackInfo) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return;
        class_437 class_4373 = SettingsEntryRenderer.e(class_4372);
        if (class_4373 == null) {
            return;
        }
        callbackInfo.cancel();
        ((class_310)(Object)this).method_1507(class_4373);
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    public void tick(CallbackInfo callbackInfo) {
        ClientTickEvent clientTickEvent = new ClientTickEvent();
        Celebression.getInstance().getEventHandler().post((Object)clientTickEvent);
        Stopwatch.rS();
    }

    @ModifyExpressionValue(method={"handleBlockBreaking"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z")})
    public boolean handleBlockBreaking(boolean bl) {
        return bl;
    }

    @ModifyExpressionValue(method={"doItemUse"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z")})
    public boolean doItemUse(boolean bl) {
        return bl;
    }

    @Inject(method={"setOverlay"}, at={@At(value="TAIL")})
    private void celebression$trackOverlay(class_4071 class_40712, CallbackInfo callbackInfo) {
        if (class_40712 instanceof class_425) {
            ColorMath.rx();
        }
    }

    @Inject(method={"close"}, at={@At(value="HEAD")})
    private void celebression$releaseRenderResources(CallbackInfo callbackInfo) {
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && celebression.getConfigManager() != null) {
            celebression.getConfigManager().dV();
        }
        if (celebression != null) {
            try {
                if (celebression.getUsageStatsManager() != null) {
                    celebression.getUsageStatsManager().shutdown();
                }
                if (celebression.getUserSyncManager() != null) {
                    celebression.getUserSyncManager().shutdown();
                }
                if (celebression.getCrashLogTelegramManager() != null) {
                    celebression.getCrashLogTelegramManager().shutdown();
                }
            }
            catch (Throwable throwable) {
            }
        }
        LiquidGlassReflectionCache.clear();
        BuiltBlur.releaseTempFramebuffer();
        WorldSnapshotCache.release();
    }
}
