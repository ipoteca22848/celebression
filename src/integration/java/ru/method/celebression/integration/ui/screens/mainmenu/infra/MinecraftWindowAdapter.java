package ru.method.celebression.integration.ui.screens.mainmenu.infra;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import net.minecraft.client.util.Window;

import java.util.Objects;

import ru.method.celebression.integration.ui.screens.mainmenu.domain.GameWindowPort;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftClientProvider;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class MinecraftWindowAdapter implements GameWindowPort
{

    Window window;

    public MinecraftWindowAdapter(Window window)
    {
        this.window = Objects.requireNonNull(window, "window");
    }

    public static GameWindowPort from()
    {
        return new MinecraftWindowAdapter(MinecraftClientProvider.getWindow());
    }

    @Override
    public int getScaledWidth()
    {
        return window.getScaledWidth();
    }

    @Override
    public int getScaledHeight()
    {
        return window.getScaledHeight();
    }

    @Override
    public int getFramebufferWidth()
    {
        return window.getFramebufferWidth();
    }

    @Override
    public int getFramebufferHeight()
    {
        return window.getFramebufferHeight();
    }

    @Override
    public int getWidth()
    {
        return window.getWidth();
    }

    @Override
    public int getHeight()
    {
        return window.getHeight();
    }

    @Override
    public long getHandle()
    {
        return window.getHandle();
    }


    @Override
    public int calculateScaleFactor(int guiScale, boolean forceUnicode)
    {
        return window.calculateScaleFactor(guiScale, forceUnicode);
    }
}