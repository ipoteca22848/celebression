package ru.method.celebression.api.mixins;

import net.minecraft.class_309;
import net.minecraft.class_310;
import net.minecraft.class_408;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.input.ClickGuiKeyHandler;
import ru.method.celebression.modules.impl.render.FreeLookModule;
import ru.method.celebression.api.event.KeyInputEvent;

@Mixin(value={class_309.class})
public abstract class KeyboardMixin {
    @Inject(method={"onKey"}, at={@At(value="HEAD")}, cancellable=true)
    public void onKey(long l, int n, int n2, int n3, int n4, CallbackInfo callbackInfo) {
        if (n == org.lwjgl.glfw.GLFW.GLFW_KEY_END && n3 == 1
                && !(class_310.method_1551().field_1755 instanceof class_408)) {
            ru.method.celebression.service.UnhookService.disable();
            callbackInfo.cancel();
            return;
        }
        if (ru.method.celebression.service.UnhookService.isActive()) return;
        ClickGuiKeyHandler.b(n, n2, n3);
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1755 instanceof class_408) {
            return;
        }
        KeyInputEvent keyInputEvent = new KeyInputEvent(n, n3, n4);
        Celebression.getInstance().getEventHandler().post((Object)keyInputEvent);
        FreeLookModule freeLookModule = Celebression.getInstance().getModuleManager().getModule(FreeLookModule.class);
        if (freeLookModule == null) {
            return;
        }
        freeLookModule.j(n, n3);
        if (n3 != 0 && freeLookModule.l(n, n2)) {
            callbackInfo.cancel();
        }
    }
}

