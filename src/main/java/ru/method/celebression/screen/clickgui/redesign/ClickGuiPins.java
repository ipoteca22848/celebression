package ru.method.celebression.screen.clickgui.redesign;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import ru.method.celebression.Celebression;

public final class ClickGuiPins {
    private static final Set<String> PINNED = new LinkedHashSet<String>();
    private static boolean loaded;

    private ClickGuiPins() {
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("celebression_clickgui_pins.txt");
    }

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            Path path = ClickGuiPins.file();
            if (Files.exists(path, new LinkOption[0])) {
                for (String string : Files.readAllLines(path)) {
                    String string2 = string.trim();
                    if (string2.isEmpty()) continue;
                    PINNED.add(string2);
                }
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[ClickGui] failed to load pins", (Throwable)exception);
        }
    }

    private static void save() {
        try {
            Files.write(ClickGuiPins.file(), new ArrayList<String>(PINNED), new OpenOption[0]);
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[ClickGui] failed to save pins", (Throwable)exception);
        }
    }

    public static boolean isPinned(String string) {
        ClickGuiPins.ensureLoaded();
        return string != null && PINNED.contains(string);
    }

    public static void toggle(String string) {
        if (string == null) {
            return;
        }
        ClickGuiPins.ensureLoaded();
        if (!PINNED.remove(string)) {
            PINNED.add(string);
        }
        ClickGuiPins.save();
    }

    public static void unpin(String string) {
        if (string == null) {
            return;
        }
        ClickGuiPins.ensureLoaded();
        if (PINNED.remove(string)) {
            ClickGuiPins.save();
        }
    }

    public static List<String> names() {
        ClickGuiPins.ensureLoaded();
        return new ArrayList<String>(PINNED);
    }
}

