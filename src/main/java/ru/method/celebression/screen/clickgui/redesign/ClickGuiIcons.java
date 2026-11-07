package ru.method.celebression.screen.clickgui.redesign;

import java.awt.Color;
import net.minecraft.class_4587;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class ClickGuiIcons {
    private ClickGuiIcons() {
    }

    public static void pixelScale(float f) {
    }

    private static void glyph(class_4587 class_45872, Font font, String string, float f, float f2, float f3, Color color) {
        Instance instance = font.getFont(f3);
        float f4 = font == Fonts.ICONSFA ? f3 * 0.0625f : 0.0f;
        Render2D.drawFont(class_45872, instance, string, f - font.getWidth(string, f3) / 2.0f, f2 - font.getHeight(f3) / 2.0f - f4, color);
    }

    private static void fa(class_4587 class_45872, char c, float f, float f2, float f3, Color color) {
        ClickGuiIcons.glyph(class_45872, Fonts.ICONSFA, String.valueOf(c), f, f2, f3, color);
    }

    public static void search(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf002', f, f2, f3, color);
    }

    public static void sliders(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf1de', f, f2, f3, color);
    }

    public static void moreHorizontal(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf141', f, f2, f3, color);
    }

    public static void close(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf00d', f, f2, f3 * 0.92f, color);
    }

    public static void check(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf00c', f, f2, f3 * 0.92f, color);
    }

    public static void chevronDown(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf078', f, f2, f3 * 0.85f, color);
    }

    public static void chevronUp(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf077', f, f2, f3 * 0.85f, color);
    }

    public static void chevronLeft(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf053', f, f2, f3 * 0.85f, color);
    }

    public static void users(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf0c0', f, f2, f3, color);
    }

    public static void mapPin(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf3c5', f, f2, f3, color);
    }

    public static void arrowLeft(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf060', f, f2, f3 * 0.9f, color);
    }

    public static void arrowRight(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf061', f, f2, f3 * 0.9f, color);
    }

    public static void plus(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf067', f, f2, f3 * 0.9f, color);
    }

    public static void gear(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf013', f, f2, f3, color);
    }

    public static void trash(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf2ed', f, f2, f3 * 0.95f, color);
    }

    public static void link(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf0c1', f, f2, f3 * 0.95f, color);
    }

    public static void refresh(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf021', f, f2, f3 * 0.95f, color);
    }

    public static void list(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf03a', f, f2, f3 * 0.95f, color);
    }

    public static void wifi(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf1eb', f, f2, f3, color);
    }

    public static void droplet(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf043', f, f2, f3, color);
    }

    public static void user(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf007', f, f2, f3, color);
    }

    public static void musicNote(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf001', f, f2, f3, color);
    }

    public static void monitor(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf108', f, f2, f3, color);
    }

    public static void keyboard(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf11c', f, f2, f3, color);
    }

    public static void globe(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf0ac', f, f2, f3, color);
    }

    public static void chat(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf075', f, f2, f3, color);
    }

    public static void layers(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf5fd', f, f2, f3, color);
    }

    public static void accessibility(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf29a', f, f2, f3, color);
    }

    public static void database(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf1c0', f, f2, f3, color);
    }

    public static void info(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf05a', f, f2, f3, color);
    }

    public static void play(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf04b', f, f2, f3 * 0.9f, color);
    }

    public static void flag(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf024', f, f2, f3, color);
    }

    public static void chartBar(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf080', f, f2, f3, color);
    }

    public static void trophy(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf091', f, f2, f3, color);
    }

    public static void cube(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf1b2', f, f2, f3, color);
    }

    public static void signOut(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ClickGuiIcons.fa(class_45872, '\uf08b', f, f2, f3 * 0.95f, color);
    }
}

