package ru.method.celebression.screen.clickgui.redesign;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.class_4587;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.Render2D;

public final class ModuleIcons {
    private static float pixelScale = 1.0f;

    private ModuleIcons() {
    }

    public static void pixelScale(float f) {
        pixelScale = f > 0.01f ? f : 1.0f;
    }

    private static float stroke(float f) {
        return Math.max(1.15f / pixelScale, f * 0.075f);
    }

    private static float sx(float f, float f2, float f3) {
        return f - f2 / 2.0f + f3 / 24.0f * f2;
    }

    private static float sy(float f, float f2, float f3) {
        return f - f2 / 2.0f + f3 / 24.0f * f2;
    }

    private static void line(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
        Render2D.drawLine(class_45872, ModuleIcons.sx(f, f3, f4), ModuleIcons.sy(f2, f3, f5), ModuleIcons.sx(f, f3, f6), ModuleIcons.sy(f2, f3, f7), ModuleIcons.stroke(f3), new ColorRGBA(color));
    }

    private static void poly(class_4587 class_45872, float f, float f2, float f3, Color color, float ... fArray) {
        int n = 0;
        while (n + 3 < fArray.length) {
            ModuleIcons.line(class_45872, f, f2, f3, fArray[n], fArray[n + 1], fArray[n + 2], fArray[n + 3], color);
            n += 2;
        }
    }

    private static void dot(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Render2D.drawCircle(class_45872, ModuleIcons.sx(f, f3, f4), ModuleIcons.sy(f2, f3, f5), f6 / 24.0f * f3, color);
    }

    private static void ring(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Render2D.drawRing(class_45872, ModuleIcons.sx(f, f3, f4), ModuleIcons.sy(f2, f3, f5), f6 / 24.0f * f3, ModuleIcons.stroke(f3), color);
    }

    private static void arc(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        int n = Math.max(4, Math.round(Math.abs(f8 - f7) / 14.0f));
        float f9 = 0.0f;
        float f10 = 0.0f;
        for (int i = 0; i <= n; ++i) {
            float f11 = (float)i / (float)n;
            double d = Math.toRadians(f7 + (f8 - f7) * f11);
            float f12 = f4 + (float)Math.cos(d) * f6;
            float f13 = f5 + (float)Math.sin(d) * f6;
            if (i > 0) {
                ModuleIcons.line(class_45872, f, f2, f3, f9, f10, f12, f13, color);
            }
            f9 = f12;
            f10 = f13;
        }
    }

    public static boolean draw(class_4587 class_45872, String string, float f, float f2, float f3, Color color) {
        if (string == null) {
            return false;
        }
        switch (string.toLowerCase(Locale.ROOT)) {
            case "sprint": {
                ModuleIcons.sprint(class_45872, f, f2, f3, color);
                return true;
            }
            case "trajectories": {
                ModuleIcons.trajectories(class_45872, f, f2, f3, color);
                return true;
            }
            case "chinahat": {
                ModuleIcons.chinaHat(class_45872, f, f2, f3, color);
                return true;
            }
            case "movementhelper": {
                ModuleIcons.movementHelper(class_45872, f, f2, f3, color);
                return true;
            }
            case "discordrpc": {
                ModuleIcons.discord(class_45872, f, f2, f3, color);
                return true;
            }
            case "norender": {
                ModuleIcons.noRender(class_45872, f, f2, f3, color);
                return true;
            }
            case "itemscroller": {
                ModuleIcons.mouse(class_45872, f, f2, f3, color);
                return true;
            }
            case "targetesp": {
                ModuleIcons.target(class_45872, f, f2, f3, color);
                return true;
            }
            case "allskins": {
                ModuleIcons.users(class_45872, f, f2, f3, color);
                return true;
            }
            case "hitrange": {
                ModuleIcons.dotTarget(class_45872, f, f2, f3, color);
                return true;
            }
            case "zoom": {
                ModuleIcons.zoom(class_45872, f, f2, f3, color);
                return true;
            }
            case "fullbright": {
                ModuleIcons.sun(class_45872, f, f2, f3, color);
                return true;
            }
            case "crosshair": {
                ModuleIcons.crosshair(class_45872, f, f2, f3, color);
                return true;
            }
            case "freelook": {
                ModuleIcons.eye(class_45872, f, f2, f3, color);
                return true;
            }
            case "timechanger": {
                ModuleIcons.clock(class_45872, f, f2, f3, color);
                return true;
            }
            case "skychanger": {
                ModuleIcons.cloud(class_45872, f, f2, f3, color);
                return true;
            }
        }
        return false;
    }

    private static void sprint(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.line(class_45872, f, f2, f3, 2.0f, 8.0f, 10.0f, 8.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 10.0f, 6.2f, 1.9f, 60.0f, 320.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 2.0f, 16.0f, 13.0f, 16.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 13.0f, 17.8f, 1.9f, -60.0f, 210.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 2.0f, 12.0f, 18.0f, 12.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 18.0f, 9.9f, 2.1f, 40.0f, 300.0f, color);
    }

    private static void trajectories(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.poly(class_45872, f, f2, f3, color, 23.0f, 6.0f, 13.5f, 15.5f, 8.5f, 10.5f, 1.0f, 18.0f);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 17.0f, 6.0f, 23.0f, 6.0f, 23.0f, 12.0f);
    }

    private static void chinaHat(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.poly(class_45872, f, f2, f3, color, 12.0f, 4.0f, 22.0f, 18.0f, 2.0f, 18.0f, 12.0f, 4.0f);
        ModuleIcons.line(class_45872, f, f2, f3, 8.0f, 18.0f, 8.0f, 20.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 16.0f, 18.0f, 16.0f, 20.0f, color);
    }

    private static void movementHelper(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.line(class_45872, f, f2, f3, 2.0f, 12.0f, 22.0f, 12.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 2.0f, 12.0f, 22.0f, color);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 5.0f, 9.0f, 2.0f, 12.0f, 5.0f, 15.0f);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 9.0f, 5.0f, 12.0f, 2.0f, 15.0f, 5.0f);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 15.0f, 19.0f, 12.0f, 22.0f, 9.0f, 19.0f);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 19.0f, 9.0f, 22.0f, 12.0f, 19.0f, 15.0f);
    }

    private static void discord(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.arc(class_45872, f, f2, f3, 12.5f, 11.0f, 8.0f, 135.0f, 400.0f, color);
        ModuleIcons.poly(class_45872, f, f2, f3, color, 6.3f, 16.6f, 3.0f, 21.0f, 8.4f, 18.6f);
    }

    private static void noRender(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 20.0f, 9.2f, 235.0f, 305.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 4.0f, 9.2f, 55.0f, 125.0f, color);
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 2.7f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 2.0f, 2.0f, 22.0f, 22.0f, color);
    }

    private static void mouse(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.line(class_45872, f, f2, f3, 5.0f, 9.0f, 5.0f, 15.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 19.0f, 9.0f, 19.0f, 15.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 9.0f, 7.0f, 180.0f, 360.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 15.0f, 7.0f, 0.0f, 180.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 5.5f, 12.0f, 9.5f, color);
    }

    private static void target(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 10.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 22.0f, 12.0f, 18.0f, 12.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 6.0f, 12.0f, 2.0f, 12.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 6.0f, 12.0f, 2.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 22.0f, 12.0f, 18.0f, color);
    }

    private static void users(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 9.0f, 7.0f, 4.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 9.0f, 21.0f, 8.0f, 200.0f, 340.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 16.5f, 7.0f, 3.6f, 300.0f, 480.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 18.5f, 21.0f, 6.0f, 210.0f, 330.0f, color);
    }

    private static void dotTarget(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 9.0f, color);
        ModuleIcons.dot(class_45872, f, f2, f3, 12.0f, 12.0f, 2.2f, color);
    }

    private static void zoom(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 10.5f, 10.5f, 6.5f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 15.2f, 15.2f, 21.0f, 21.0f, color);
    }

    private static void sun(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 4.2f, color);
        for (int i = 0; i < 8; ++i) {
            double d = Math.toRadians(i * 45);
            ModuleIcons.line(class_45872, f, f2, f3, 12.0f + (float)Math.cos(d) * 7.2f, 12.0f + (float)Math.sin(d) * 7.2f, 12.0f + (float)Math.cos(d) * 9.6f, 12.0f + (float)Math.sin(d) * 9.6f, color);
        }
    }

    private static void crosshair(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 7.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 2.5f, 12.0f, 7.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 17.0f, 12.0f, 21.5f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 2.5f, 12.0f, 7.0f, 12.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 17.0f, 12.0f, 21.5f, 12.0f, color);
    }

    private static void eye(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 19.5f, 9.5f, 235.0f, 305.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 12.0f, 4.5f, 9.5f, 55.0f, 125.0f, color);
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 2.7f, color);
    }

    private static void clock(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.ring(class_45872, f, f2, f3, 12.0f, 12.0f, 9.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 12.0f, 12.0f, 7.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 12.0f, 12.0f, 15.5f, 13.5f, color);
    }

    private static void cloud(class_4587 class_45872, float f, float f2, float f3, Color color) {
        ModuleIcons.arc(class_45872, f, f2, f3, 9.0f, 12.0f, 4.0f, 90.0f, 270.0f, color);
        ModuleIcons.arc(class_45872, f, f2, f3, 14.0f, 10.5f, 5.2f, 250.0f, 380.0f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 9.0f, 8.0f, 15.5f, 6.7f, color);
        ModuleIcons.line(class_45872, f, f2, f3, 5.0f, 16.0f, 18.5f, 16.0f, color);
    }
}

