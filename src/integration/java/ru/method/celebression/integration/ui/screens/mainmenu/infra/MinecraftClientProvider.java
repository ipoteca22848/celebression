package ru.method.celebression.integration.ui.screens.mainmenu.infra;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.GameWindowPort;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftWindowAdapter;

@UtilityClass
public class MinecraftClientProvider {
    public MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    public Window getWindow() {
        return getMinecraft().getWindow();
    }

    public GameWindowPort getWindowPort() {
        return MinecraftWindowAdapter.from();
    }
}
