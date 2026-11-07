package ru.method.celebression.integration.ui.screens.mainmenu.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextValue
{

    @Getter
    String text;

    ColorValue defaultColor;

    Map<Integer, ColorValue> overrides = new HashMap<>();

    public static TextValue of(String text, ColorValue defaultColor)
    {
        return new TextValue(text, defaultColor);
    }

    public static TextValue of(String text)
    {
        return new TextValue(text, ColorValue.rgb(255, 255, 255));
    }

    public TextValue index(int position, ColorValue color)
    {
        if (position >= 0 && position < text.length())
            overrides.put(position, color);
        return this;
    }

    public TextValue range(int from, int to, ColorValue color)
    {
        for (int i = from; i <= to && i < text.length(); i++)
            overrides.put(i, color);
        return this;
    }

    public int  length()
    {
        return text.length();
    }

    public int colorAt(int i)
    {
        return overrides.getOrDefault(i, defaultColor).toRGBA();
    }
}