package ru.method.celebression.integration.ui.bridge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import ru.method.celebression.service.AppearanceMode;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;

public final class ThemeSwitch {
    private ThemeSwitch() {}
    public static ButtonWidget button(int width) {
        return ButtonWidget.builder(Text.literal("Стиль: " + (AppearanceMode.isCustom() ? "celebression" : "Minecraft")), button -> {
            boolean custom = !AppearanceMode.isCustom();
            if (AppearanceMode.setCustom(custom)) {
                MinecraftClient.getInstance().setScreen(custom ? new CustomMainMenuScreen() : new TitleScreen());
            }
        }).dimensions(Math.max(4, width - 184), 6, 180, 20).build();
    }
}
