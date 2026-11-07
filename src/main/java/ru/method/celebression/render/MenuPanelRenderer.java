package ru.method.celebression.render;

import java.awt.Color;
import net.minecraft.class_4587;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.Render2D;

public final class MenuPanelRenderer {
    public static final float acY = 12.0f;
    private static final Color acZ = new Color(224, 110, 110, 140);

    private MenuPanelRenderer() {
    }

    public static void n(class_4587 class_45872, float f, float f2, float f3, float f4) {
        for (int i = 6; i >= 1; --i) {
            float f5 = (float)i * 2.6f;
            Render2D.drawRoundedRect(class_45872, f - f5, f2 - f5 + 10.0f, f3 + f5 * 2.0f, f4 + f5 * 2.0f, 12.0f + f5, new Color(0, 0, 0, Math.round(10.0f - (float)i * 0.9f)));
        }
        MenuChrome.glow(class_45872, f, f2, f3, f4, 12.0f, MenuChrome.accentA(0.16f), 0.0f, 6, 2.6f);
        Render2D.drawBlurredRect(class_45872, f, f2, f3, f4, 12.0f, 14.0f, new Color(4, 7, 6, 26), true);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, 12.0f, new Color(17, 26, 21, 230), new Color(5, 8, 7, 230));
        float f6 = Math.min(240.0f, f4);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f6, 12.0f, MenuChrome.accentA(0.07f), MenuChrome.accentA(0.0f));
        MenuPanelRenderer.o(class_45872, f, f2, f3, f4);
        Render2D.drawRoundedRect(class_45872, f + 12.0f, f2 + 1.0f, f3 - 24.0f, 1.0f, 0.5f, MenuChrome.white(0.05f));
        Render2D.drawBorder(class_45872, f, f2, f3, f4, 12.0f, 1.0f, 1.4f, MenuChrome.white(0.09f));
    }

    private static void o(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5;
        ColorRGBA colorRGBA = new ColorRGBA(MenuChrome.white(0.014f));
        for (float f6 = f5 = 14.141999f; f6 < f3 + f4; f6 += f5) {
            float f7 = f + Math.max(0.0f, f6 - f4);
            float f8 = f2 + Math.min(f6, f4);
            float f9 = f + Math.min(f6, f3);
            float f10 = f2 + Math.max(0.0f, f6 - f3);
            Render2D.drawLine(class_45872, f7, f8, f9, f10, 1.0f, colorRGBA);
        }
    }

    public static void p(class_4587 class_45872, float f, float f2, float f3, float f4) {
        Color color = MenuChrome.accentA(0.93f);
        float f5 = 20.0f;
        float f6 = 2.0f;
        float f7 = 14.0f;
        float f8 = f + f7;
        float f9 = f2 + f7;
        Render2D.drawRoundedRect(class_45872, f8, f9, f5, f6, f6 / 2.0f, color);
        Render2D.drawRoundedRect(class_45872, f8, f9, f6, f5, f6 / 2.0f, color);
        float f10 = f + f3 - f7 + 2.0f;
        float f11 = f2 + f4 - f7 + 2.0f;
        Render2D.drawRoundedRect(class_45872, f10 - f5, f11 - f6, f5, f6, f6 / 2.0f, color);
        Render2D.drawRoundedRect(class_45872, f10 - f6, f11 - f5, f6, f5, f6 / 2.0f, color);
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 7.0f, MenuChrome.white(0.04f));
        Color color = bl ? MenuChrome.lerp(MenuChrome.white(0.08f), acZ, f5) : MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.24f), f5);
        Render2D.drawBorder(class_45872, f, f2, f3, f4, 7.0f, 1.0f, 1.4f, color);
    }
}

