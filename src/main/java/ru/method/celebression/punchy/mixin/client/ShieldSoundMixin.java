package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1113;
import net.minecraft.class_1144;
import net.minecraft.class_1306;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.ShieldImpactPhysics;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;

@Mixin(value={class_1144.class})
public class ShieldSoundMixin {
    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at={@At(value="HEAD")})
    private void punchy$onPlaySound(class_1113 class_11132, CallbackInfo callbackInfo) {
        class_2960 class_29602;
        if (class_11132 != null && !PunchyConfig.disableArmPhysics() && !PunchyConfig.disableNativeItemPhysics() && (class_29602 = class_11132.method_4775()) != null && class_29602.method_12832().equals("item.shield.block")) {
            class_1306 class_13062;
            double d;
            class_310 class_3102 = class_310.method_1551();
            if (class_3102.field_1724 != null && (d = class_3102.field_1724.method_5649(class_11132.method_4784(), class_11132.method_4779(), class_11132.method_4778())) < 4.0 && (class_13062 = ShieldImpactPhysics.resolveImpactArm(class_3102.field_1724)) != null) {
                ShieldImpactPhysics.get().trigger(class_13062);
                MovementStateMachine.get().triggerShieldBlockImpact(class_13062);
            }
        }
    }
}

