package ru.method.celebression.integration.ui.screens.mainmenu.domain;

public interface GameWindowPort
{

    int getScaledWidth();

    int getScaledHeight();

    int getFramebufferWidth();

    int getFramebufferHeight();

    long getHandle();

    int getWidth();

    int getHeight();

    int calculateScaleFactor(int guiScale, boolean forceUnicode);
}