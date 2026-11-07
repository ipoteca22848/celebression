package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_636.class})
public class MultiPlayerGameModeAttackMixin {
    @Inject(method={"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"}, at={@At(value="HEAD")})
    private void punchy$onAttack(class_1657 class_16572, class_1297 class_12972, CallbackInfo callbackInfo) {
    }
}

