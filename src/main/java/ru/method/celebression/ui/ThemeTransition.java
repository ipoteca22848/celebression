package ru.method.celebression.ui;

import java.awt.Color;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.ui.UiThemeController;
import ru.method.celebression.utils.render.ColorUtils;

public final class ThemeTransition {
    private static final ThreadLocal<ClientTheme> Zm = new ThreadLocal();

    private ThemeTransition() {
    }

    public static void h(ClientTheme clientTheme) {
        Zm.set(clientTheme == null ? ClientTheme.YZ : clientTheme);
    }

    public static void end() {
        Zm.remove();
    }

    public static Color accent() {
        ClientTheme clientTheme = Zm.get();
        if (clientTheme == null) {
            return ColorUtils.getPrimaryColor();
        }
        return ThemeTransition.a(UiThemeController.oY().pe(), clientTheme.pe());
    }

    public static Color pg() {
        ClientTheme clientTheme = Zm.get();
        if (clientTheme == null) {
            return ColorUtils.getTextColor();
        }
        return ThemeTransition.a(UiThemeController.oY().pg(), clientTheme.pg());
    }

    public static Color d(int n) {
        Color color = ThemeTransition.pg();
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, n)));
    }

    public static Color pf() {
        ClientTheme clientTheme = Zm.get();
        if (clientTheme == null) {
            return ColorUtils.getBackgroundColor();
        }
        return ThemeTransition.a(UiThemeController.oY().pf(), clientTheme.pf());
    }

    private static Color a(Color color, Color color2) {
        float f = UiThemeController.oZ();
        if (color == null || f >= 1.0f) {
            return color2;
        }
        int n = Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f);
        int n2 = Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f);
        int n3 = Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f);
        return new Color(n, n2, n3);
    }
}

