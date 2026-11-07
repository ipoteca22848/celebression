package ru.method.celebression.integration.ui.bridge;
import net.minecraft.client.MinecraftClient;
import ru.method.celebression.integration.ui.manager.Manager;
import ru.method.celebression.integration.ui.screens.dropdown.ClickGUI;
import ru.method.celebression.integration.ui.screens.dropdown.ClickGuiOpenHelper;
public final class ClientScreens {
    private ClientScreens() {}
    public static boolean isOpen() { return MinecraftClient.getInstance().currentScreen instanceof ClickGUI; }
    public static void setOpen(boolean open) {
        if (open && ru.method.celebression.service.UnhookService.isActive()) return;
        var client = MinecraftClient.getInstance();
        if (open) {
            if (isOpen()) return;
            Manager.initialize();
            Manager.FUNCTION_MANAGER.getFunctions();
            client.setScreen(ClickGuiOpenHelper.open(client));
        } else if (client.currentScreen instanceof ClickGUI screen) screen.close();
    }
    public static void toggle() { setOpen(!isOpen()); }
    public static boolean hasBinding() {
        return MinecraftClient.getInstance().currentScreen instanceof ClickGUI screen && screen.celebression$hasBinding();
    }
}
