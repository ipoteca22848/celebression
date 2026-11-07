package ru.method.celebression.integration.ui.screens.mainmenu.render.font;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Fonts {
    private static final Map<String, String> FONT_REGISTRY = new LinkedHashMap<>();

    public static final Font ROBOTO_FLEX_REGULAR = register("inter_display_semibold", "inter_display_semibold");

    private static Font register(String name, String path) {
        FONT_REGISTRY.put(name, path);
        return new Font(name);
    }

    public static Map<String, String> getRegistry() {
        return FONT_REGISTRY;
    }

    private Fonts() {}
}
