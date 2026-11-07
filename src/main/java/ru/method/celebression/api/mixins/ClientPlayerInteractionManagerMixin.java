package ru.method.celebression.api.mixins;

import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.event.BlockBreakEvent;
import ru.method.celebression.modules.impl.client.SecretDupeModule;

@Mixin(value={class_636.class})
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method={"clickSlot"}, at={@At(value="HEAD")}, cancellable=true)
    public void celebression$fakeCosmeticClickSlot(int n, int n2, int n3, class_1713 class_17132, class_1657 class_16572, CallbackInfo callbackInfo) {
        if (SecretDupeModule.a(n2, n3, class_17132, class_16572)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"attackEntity"}, at={@At(value="HEAD")}, cancellable=true)
    public void attackEntity(class_1657 class_16572, class_1297 class_12972, CallbackInfo callbackInfo) {
        AttackEntityEvent attackEntityEvent = new AttackEntityEvent(class_16572, class_12972);
        Celebression.getInstance().getEventHandler().post((Object)attackEntityEvent);
        if (attackEntityEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"attackBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void attackBlock(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(class_23382, class_23502);
        Celebression.getInstance().getEventHandler().post((Object)blockBreakEvent);
        if (blockBreakEvent.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method={"updateBlockBreakingProgress"}, at={@At(value="HEAD")}, cancellable=true)
    public void updateBlockBreakingProgress(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(class_23382, class_23502);
        Celebression.getInstance().getEventHandler().post((Object)blockBreakEvent);
        if (blockBreakEvent.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}

