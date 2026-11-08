package ru.method.celebression.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

public final class AppearancePreferences {
    private final Path file;
    private boolean custom = true;
    public AppearancePreferences(Path file) { this.file = file; }
    public static AppearancePreferences load(Path file) throws IOException {
        AppearancePreferences preferences = new AppearancePreferences(file);
        if (Files.isRegularFile(file)) {
            Properties values = new Properties();
            try (var input = Files.newInputStream(file)) { values.load(input); }
            preferences.custom = Boolean.parseBoolean(values.getProperty("customMenus", "true"));
        }
        return preferences;
    }
    public boolean isCustom() { return custom; }
    public void setCustom(boolean value) throws IOException {
        Properties values = new Properties();
        values.setProperty("customMenus", Boolean.toString(value));
        Files.createDirectories(file.getParent());
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        try (var output = Files.newOutputStream(temporary)) { values.store(output, "Menu appearance"); }
        try { Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
        catch (AtomicMoveNotSupportedException ignored) { Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING); }
        custom = value;
    }
}
