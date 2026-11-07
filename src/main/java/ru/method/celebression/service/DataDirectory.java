package ru.method.celebression.service;

import java.io.IOException;
import java.nio.file.*;

/** Client-owned state stays inside the selected Minecraft profile. */
public final class DataDirectory {
    private DataDirectory() {}
    public static Path location(Path gameDirectory) { return gameDirectory.resolve(".cache").resolve(".ce"); }
    public static Path prepare(Path gameDirectory) throws IOException {
        Path target = location(gameDirectory);
        Files.createDirectories(target);
        copyMissing(gameDirectory.resolve("celebression"), target);
        copyMissing(gameDirectory.resolve("config").resolve("punchy"), target.resolve("config/punchy"));
        Path language = gameDirectory.resolve("files/menu_language.ew");
        if (Files.isRegularFile(language, LinkOption.NOFOLLOW_LINKS) && !Files.exists(target.resolve("menu-language.txt"))) {
            Files.copy(language, target.resolve("menu-language.txt"));
        }
        return target;
    }
    private static void copyMissing(Path source, Path target) throws IOException {
        if (!Files.isDirectory(source, LinkOption.NOFOLLOW_LINKS)) return;
        try (var paths = Files.walk(source)) {
            for (Path item : paths.toList()) {
                Path output = target.resolve(source.relativize(item));
                if (Files.isDirectory(item, LinkOption.NOFOLLOW_LINKS)) Files.createDirectories(output);
                else if (Files.isRegularFile(item, LinkOption.NOFOLLOW_LINKS) && !Files.exists(output)) {
                    Files.createDirectories(output.getParent());
                    Files.copy(item, output);
                }
            }
        }
    }
}
