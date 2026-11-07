package ru.method.celebression.integration.ui.screens.mainmenu.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Arithmetic
{

    public int clamp(int v)
    {
        return Math.max(0, Math.min(255, v));
    }
}