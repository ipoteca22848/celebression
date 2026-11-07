package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import ru.method.celebression.punchy.client.modelparts.ModelPartsSystem;
import ru.method.celebression.punchy.compat.iris.IrisItemLightProviderCompat;

@Mixin(value={class_1792.class})
@Implements(value={@Interface(iface=IrisItemLightProviderCompat.class, prefix="punchy$")})
public class ItemIrisLightMixin {
    private static final int MAX_MODEL_EMISSION = 15;
    private static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);

    public int punchy$getLightEmission(class_1657 class_16572, class_1799 class_17992) {
        int n = ItemIrisLightMixin.getVanillaEmission(class_17992);
        int n2 = ItemIrisLightMixin.getModelWorldEmission(class_16572, class_17992);
        return Math.max(n, ItemIrisLightMixin.scaleEmission(n2));
    }

    public Vector3f punchy$getLightColor(class_1657 class_16572, class_1799 class_17992) {
        return DEFAULT_COLOR;
    }

    private static int getVanillaEmission(class_1799 class_17992) {
        int n;
        if (class_17992 == null) {
            return 0;
        }
        class_1792 class_17922 = class_17992.method_7909();
        if (class_17922 instanceof class_1747) {
            class_1747 class_17472 = (class_1747)class_17922;
            n = class_17472.method_7711().method_9564().method_26213();
        } else {
            n = 0;
        }
        return n;
    }

    private static int getModelWorldEmission(class_1657 class_16572, class_1799 class_17992) {
        return class_16572 != null && class_17992 != null ? ModelPartsSystem.get().getModelWorldEmission(class_16572, class_17992) : 0;
    }

    private static int scaleEmission(int n) {
        if (n <= 0) {
            return 0;
        }
        int n2 = Math.min(n, 15);
        return Math.round((float)n2 * 1.0f);
    }
}

