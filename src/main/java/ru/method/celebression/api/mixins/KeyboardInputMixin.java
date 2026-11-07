package ru.method.celebression.api.mixins;

import net.minecraft.class_743;
import net.minecraft.class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.MovementInputEvent;

@Mixin(value={class_743.class})
public abstract class KeyboardInputMixin
extends class_744 {
    @Inject(method={"tick"}, at={@At(value="RETURN")})
    public void tick(CallbackInfo callbackInfo) {
        MovementInputEvent movementInputEvent = new MovementInputEvent(this.field_3905, this.field_3907);
        Celebression.getInstance().getEventHandler().post((Object)movementInputEvent);
        this.field_3905 = movementInputEvent.e();
        this.field_3907 = movementInputEvent.f();
    }
}

