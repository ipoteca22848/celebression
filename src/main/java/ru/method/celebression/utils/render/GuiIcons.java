package ru.method.celebression.utils.render;

import ru.method.celebression.module.Module;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.utils.render.Render2D;

public final class GuiIcons {
    public static final class_2960 PALETTE = Celebression.id("textures/gui/icons/icon_palette.png");
    public static final class_2960 AVATAR = Celebression.id("textures/gui/icons/icon_avatar.png");
    public static final class_2960 SOUND = Celebression.id("textures/gui/icons/icon_sound.png");
    public static final class_2960 MUTE = Celebression.id("textures/gui/icons/icon_mute.png");
    public static final class_2960 DOT = Celebression.id("textures/gui/icons/icon_dot.png");
    public static final class_2960 KEYBOARD = Celebression.id("textures/gui/icons/icon_keyboard.png");
    public static final class_2960 SWORD = Celebression.id("textures/gui/modules/sword.png");
    private static final Set<class_2960> FILTERED = new HashSet<class_2960>();

    private GuiIcons() {
    }

    public static class_2960 forCategory(ModuleCategory moduleCategory) {
        return moduleCategory == ModuleCategory.qm ? AVATAR : null;
    }

    public static void draw(class_4587 class_45872, class_2960 class_29602, float f, float f2, float f3, Color color) {
        class_1044 class_10443;
        class_310 class_3102;
        if (class_29602 == null || color == null || color.getAlpha() <= 0 || f3 <= 0.0f) {
            return;
        }
        if (!FILTERED.contains(class_29602) && (class_3102 = class_310.method_1551()) != null && (class_10443 = class_3102.method_1531().method_4619(class_29602)) != null) {
            class_10443.method_4527(true, false);
            FILTERED.add(class_29602);
        }
        Render2D.drawTexture(class_45872, f - f3 / 2.0f, f2 - f3 / 2.0f, f3, f3, 0.0f, class_29602, color);
    }
}

