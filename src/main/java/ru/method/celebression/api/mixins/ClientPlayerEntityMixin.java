package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.PlayerRotationEvent;
import ru.method.celebression.api.event.PlayerTickEvent;

@Mixin(value={class_746.class})
public abstract class ClientPlayerEntityMixin
extends class_742 {
    @Final
    @Shadow
    public class_634 field_3944;
    @Shadow
    private double field_3926;
    @Shadow
    private double field_3940;
    @Shadow
    private double field_3924;
    @Final
    @Shadow
    protected class_310 field_3937;
    @Shadow
    private float field_3941;
    @Shadow
    private float field_3925;
    @Shadow
    private boolean field_3920;
    @Shadow
    private boolean field_53040;
    @Shadow
    private boolean field_3927;
    @Shadow
    private int field_3923;
    @Unique
    private final PlayerRotationEvent event = new PlayerRotationEvent(0.0f, 0.0f);

    public ClientPlayerEntityMixin(class_638 class_6382, GameProfile gameProfile) {
        super(class_6382, gameProfile);
    }

    @Shadow
    protected boolean method_3134() {
        return false;
    }

    @Shadow
    private void method_46742() {
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    public void tick(CallbackInfo callbackInfo) {
        PlayerTickEvent playerTickEvent = new PlayerTickEvent();
        Celebression.getInstance().getEventHandler().post((Object)playerTickEvent);
    }

    @ModifyExpressionValue(method={"shouldStopSprinting"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z")})
    public boolean shouldStopSprinting(boolean bl) {
        return bl;
    }
}

