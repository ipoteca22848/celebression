package ru.method.celebression.integration.ui.screens.mainmenu.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import ru.method.celebression.integration.ui.screens.mainmenu.domain.Arithmetic;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorValue
{
    private static final float INV_255 = 1.0f / 255.0f;

    int r, g, b, a;

    public ColorValue(int r, int g, int b, int a)
    {
        this.r = Arithmetic.clamp(r);
        this.g = Arithmetic.clamp(g);
        this.b = Arithmetic.clamp(b);
        this.a = Arithmetic.clamp(a);
    }

    public float[] toFloatArray()
    {
        return new float[] {redf(), greenf(), bluef(), alphaf()};
    }

    public float redf()
    {
        return r * INV_255;
    }

    public float greenf()
    {
        return g * INV_255;
    }

    public float bluef()
    {
        return b * INV_255;
    }

    public float alphaf()
    {
        return a * INV_255;
    }

    public static ColorValue rgb(int r, int g, int b)
    {
        return new ColorValue(r, g, b, 255);
    }

    public static ColorValue rgba(int r, int g, int b, int a)
    {
        return new ColorValue(r, g, b, a);
    }

    public static ColorValue fromRGBA(int rgba)
    {
        int a = rgba >>> 24 & 0xFF;
        int r = rgba >>> 16 & 0xFF;
        int g = rgba >>> 8 & 0xFF;
        int b = rgba & 0xFF;
        return new ColorValue(r, g, b, a);
    }

    public ColorValue alphaRGBA(int alpha)
    {
        return new ColorValue(r, g, b, alpha);
    }

    public ColorValue alpha(int percent)
    {
        percent = Math.max(0, Math.min(100, percent));
        int a = Math.round(percent / 100f * 255f);
        return new ColorValue(r, g, b, a);
    }

    public int toRGBA()
    {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
