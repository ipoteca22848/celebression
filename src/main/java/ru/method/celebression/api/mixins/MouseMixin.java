package ru.method.celebression.api.mixins;

import net.minecraft.class_310;
import net.minecraft.class_312;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.modules.impl.render.FreeLookModule;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.event.MouseScrollEvent;

@Mixin(value={class_312.class})
public abstract class MouseMixin {
    @Shadow
    private class_310 field_1779;

    @Inject(method={"onMouseButton"}, at={@At(value="HEAD")}, cancellable=true)
    public void onMouseButton(long l, int n, int n2, int n3, CallbackInfo callbackInfo) {
        HudManager hudManager;
        EventMouse eventMouse = new EventMouse(n, n2);
        Celebression celebression = Celebression.getInstance();
        if (celebression != null) {
            hudManager = celebression.getHudManager();
            if (hudManager != null && hudManager.a(eventMouse)) {
                callbackInfo.cancel();
                return;
            }
            celebression.getEventHandler().post((Object)eventMouse);
        }
        FreeLookModule freeLookModule = celebression != null ? celebression.getModuleManager().getModule(FreeLookModule.class) : null;
        if (freeLookModule != null) {
            freeLookModule.k(n, n2);
        }
        if (eventMouse.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"onMouseScroll"}, at={@At(value="HEAD")}, cancellable=true)
    public void onMouseScroll(long l, double d, double d2, CallbackInfo callbackInfo) {
        MouseScrollEvent mouseScrollEvent = new MouseScrollEvent(d, d2);
        Celebression.getInstance().getEventHandler().post((Object)mouseScrollEvent);
        if (mouseScrollEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"updateMouse"}, at={@At(value="HEAD")})
    private void celebression$captureFreelookRotation(CallbackInfo callbackInfo) {
        FreeLookModule freeLookModule = Celebression.getInstance().getModuleManager().getModule(FreeLookModule.class);
        class_746 class_7462 = this.field_1779.field_1724;
        if (freeLookModule == null || class_7462 == null) {
            return;
        }
        freeLookModule.a(class_7462);
    }

    @Inject(method={"updateMouse"}, at={@At(value="RETURN")})
    private void celebression$restoreFreelookRotation(CallbackInfo callbackInfo) {
        FreeLookModule freeLookModule = Celebression.getInstance().getModuleManager().getModule(FreeLookModule.class);
        class_746 class_7462 = this.field_1779.field_1724;
        if (freeLookModule == null || class_7462 == null) {
            return;
        }
        freeLookModule.b(class_7462);
    }
}
