package ru.method.celebression.api.mixins;

import java.awt.Color;
import net.minecraft.class_1297;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_898;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.CustomHitBoxModule;
import ru.method.celebression.managers.system.ModuleManager;
import ru.method.celebression.utils.render.ColorUtils;

@Mixin(value={class_898.class})
public class EntityRenderDispatcherMixin {
    @Unique
    private static Color cachedColor = null;
    @Unique
    private static long lastColorUpdate = 0L;
    @Unique
    private static final long COLOR_CACHE_TIME = 16L;

    @Unique
    private static boolean isCustomHitBoxActive() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return false;
        }
        ModuleManager moduleManager = celebression.getModuleManager();
        if (moduleManager == null) {
            return false;
        }
        CustomHitBoxModule customHitBoxModule = moduleManager.getModule(CustomHitBoxModule.class);
        return customHitBoxModule != null && customHitBoxModule.isToggled();
    }

    @Unique
    private static Color getHitboxColor() {
        long l = System.currentTimeMillis();
        if (cachedColor == null || l - lastColorUpdate > 16L) {
            cachedColor = ColorUtils.getGlobalColor();
            lastColorUpdate = l;
        }
        return cachedColor;
    }

    @Inject(method={"renderHitbox"}, at={@At(value="HEAD")}, cancellable=true)
    private static void cancelSelfHitbox(class_4587 class_45872, class_4588 class_45882, class_1297 class_12972, float f, float f2, float f3, float f4, CallbackInfo callbackInfo) {
        Celebression celebression;
        block12: {
            block11: {
                block10: {
                    block9: {
                        celebression = Celebression.getInstance();
                        if (celebression == null || class_12972 == null) break block9;
                        if (Celebression.aiG.field_1724 != null) break block10;
                    }
                    return;
                }
                if (Celebression.aiG.field_1724 == null) break block11;
                if (class_12972.method_5667().equals(Celebression.aiG.field_1724.method_5667())) break block12;
            }
            return;
        }
        ModuleManager moduleManager = celebression.getModuleManager();
        if (moduleManager == null) {
            return;
        }
        CustomHitBoxModule customHitBoxModule = moduleManager.getModule(CustomHitBoxModule.class);
        if (customHitBoxModule != null) {
            boolean bl = Celebression.aiG.field_1690.method_31044().method_31034();
            if (!customHitBoxModule.iy() || bl) {
                callbackInfo.cancel();
            }
        }
    }

    @ModifyVariable(method={"renderHitbox"}, at=@At(value="HEAD"), ordinal=1, argsOnly=true)
    private static float modifyRed(float f) {
        if (!EntityRenderDispatcherMixin.isCustomHitBoxActive()) {
            cachedColor = null;
            return f;
        }
        return (float)EntityRenderDispatcherMixin.getHitboxColor().getRed() / 255.0f;
    }

    @ModifyVariable(method={"renderHitbox"}, at=@At(value="HEAD"), ordinal=2, argsOnly=true)
    private static float modifyGreen(float f) {
        if (!EntityRenderDispatcherMixin.isCustomHitBoxActive()) {
            return f;
        }
        return (float)EntityRenderDispatcherMixin.getHitboxColor().getGreen() / 255.0f;
    }

    @ModifyVariable(method={"renderHitbox"}, at=@At(value="HEAD"), ordinal=3, argsOnly=true)
    private static float modifyBlue(float f) {
        if (!EntityRenderDispatcherMixin.isCustomHitBoxActive()) {
            return f;
        }
        return (float)EntityRenderDispatcherMixin.getHitboxColor().getBlue() / 255.0f;
    }
}

