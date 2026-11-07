package ru.method.celebression.utils.render;

import java.util.UUID;
import net.minecraft.class_1068;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_742;
import net.minecraft.class_8685;
import ru.method.celebression.screen.clickgui.ClickGui;

public final class GuiSkinTextures {
    private static class_8685 cachedSkinTextures;
    private static UUID cachedSkinTexturesUuid;

    private GuiSkinTextures() {
    }

    public static boolean isClickGuiOpen() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.field_1755 instanceof ClickGui;
    }

    public static void cacheLocalSkinTextures(UUID uUID, class_8685 class_86852) {
        if (uUID == null || class_86852 == null) {
            return;
        }
        cachedSkinTexturesUuid = uUID;
        cachedSkinTextures = class_86852;
    }

    public static class_2960 resolveTexture(class_1657 class_16572) {
        if (class_16572 == null) {
            return class_1068.method_4649();
        }
        class_8685 class_86852 = GuiSkinTextures.getCachedSkinTextures(class_16572.method_5667());
        if (class_86852 != null) {
            return class_86852.comp_1626();
        }
        return class_1068.method_4648((UUID)class_16572.method_5667()).comp_1626();
    }

    public static class_8685 resolveLocalSkinTextures(class_742 class_7422) {
        if (class_7422 == null) {
            return class_1068.method_4648((UUID)UUID.randomUUID());
        }
        class_8685 class_86852 = GuiSkinTextures.getCachedSkinTextures(class_7422.method_5667());
        if (class_86852 != null) {
            return class_86852;
        }
        return class_1068.method_4648((UUID)class_7422.method_5667());
    }

    private static class_8685 getCachedSkinTextures(UUID uUID) {
        if (uUID == null || cachedSkinTextures == null || !uUID.equals(cachedSkinTexturesUuid)) {
            return null;
        }
        return cachedSkinTextures;
    }
}

