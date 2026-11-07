package ru.method.celebression.integration.ui.screens.mainmenu;

import ru.method.celebression.integration.ui.manager.IMinecraft;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MenuConfigBridge implements IMinecraft {
    private static final Path LANGUAGE_FILE = ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath().resolve("menu-language.txt");

    private MenuConfigBridge() {}

    public static void load() {
        if (!Files.isRegularFile(LANGUAGE_FILE)) {
            return;
        }
        try {
            String code = Files.readString(LANGUAGE_FILE, StandardCharsets.UTF_8).trim();
            MenuLanguageState.load(code);
        } catch (IOException ignored) {
        }
    }

    public static void saveAsync() {
        try {
            Files.createDirectories(LANGUAGE_FILE.getParent());
            Files.writeString(LANGUAGE_FILE, MenuLanguageState.code(), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
        MenuAccountBridge.saveAsync();
    }

    public static void reload() {
        MenuAccountBridge.reload();
    }
}
