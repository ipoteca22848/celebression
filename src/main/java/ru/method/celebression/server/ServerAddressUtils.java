package ru.method.celebression.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import lombok.Generated;

public final class ServerAddressUtils {
    public static void createEmptyFile(String pathString) {
        Path path = Paths.get(pathString, new String[0]);
        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent, new FileAttribute[0]);
            }
            Files.deleteIfExists(path);
            Files.createFile(path, new FileAttribute[0]);
        }
        catch (IOException exception) {
            throw new UncheckedIOException("Unable to create file: " + path, exception);
        }
    }

    public static boolean exists(String pathString) {
        return Files.exists(Paths.get(pathString, new String[0]), new LinkOption[0]);
    }

    @Generated
    private ServerAddressUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
