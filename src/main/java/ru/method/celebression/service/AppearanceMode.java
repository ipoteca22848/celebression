package ru.method.celebression.service;

import java.nio.file.Path;
import java.io.IOException;
import ru.method.celebression.Celebression;

/** Controls menu styling; gameplay modules have independent settings. */
public final class AppearanceMode {
    private static AppearancePreferences preferences;
    private AppearanceMode() {}
    public static void initialize(Path directory) {
        Path file = directory.resolve("appearance.properties");
        try { preferences = AppearancePreferences.load(file); }
        catch (IOException error) {
            preferences = new AppearancePreferences(file);
            Celebression.LOGGER.warn("Cannot read menu appearance", error);
        }
    }
    public static boolean isCustom() {
        return !UnhookService.isActive() && (preferences == null || preferences.isCustom());
    }
    public static boolean setCustom(boolean enabled) {
        if (UnhookService.isActive()) return false;
        if (preferences == null) initialize(Celebression.getInstance().getGlobalsDir().toPath());
        try { preferences.setCustom(enabled); return true; }
        catch (IOException error) {
            Celebression.LOGGER.warn("Cannot save menu appearance", error);
            return false;
        }
    }
}
