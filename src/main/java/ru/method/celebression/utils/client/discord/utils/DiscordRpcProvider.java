package ru.method.celebression.utils.client.discord.utils;

import com.sun.jna.Native;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;
import java.util.zip.CRC32;
import ru.method.celebression.utils.client.discord.utils.DiscordRPC;

public final class DiscordRpcProvider {
    private static final String LIBRARY_FILE_NAME = "discord-rpc.dll";
    private static final Object LOCK = new Object();
    private static volatile DiscordRPC instance;
    private static volatile Throwable loadError;
    private static volatile Path extractedLibraryPath;

    private DiscordRpcProvider() {
    }

        public static DiscordRPC get() {
        DiscordRPC discordRPC = instance;
        if (discordRPC != null) {
            return discordRPC;
        }
        if (loadError != null) {
            return null;
        }
        Object object = LOCK;
        synchronized (object) {
            discordRPC = instance;
            if (discordRPC != null) {
                return discordRPC;
            }
            if (loadError != null) {
                return null;
            }
            try {
                Path path;
                extractedLibraryPath = path = DiscordRpcProvider.extractBundledLibrary();
                instance = (DiscordRPC)Native.load((String)path.toAbsolutePath().toString(), DiscordRPC.class);
                return instance;
            }
            catch (Throwable throwable) {
                loadError = throwable;
                return null;
            }
        }
    }

    public static Throwable getLoadError() {
        return loadError;
    }

    public static Path getExtractedLibraryPath() {
        return extractedLibraryPath;
    }

        public static void resetLoadError() {
        Object object = LOCK;
        synchronized (object) {
            loadError = null;
            instance = null;
        }
    }

    private static Path extractBundledLibrary() throws IOException {
        String resourcePath = DiscordRpcProvider.resolveResourcePath();
        if (resourcePath == null) {
            throw new IOException("Unsupported platform for bundled Discord RPC library");
        }

        byte[] libraryBytes;
        try (InputStream input = DiscordRpcProvider.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IOException("Bundled Discord RPC library not found: " + resourcePath);
            }
            libraryBytes = input.readAllBytes();
        }

        Path cacheDirectory = Path.of(System.getProperty("java.io.tmpdir"), "celebression", "discord-rpc");
        Files.createDirectories(cacheDirectory);
        Path libraryPath = cacheDirectory.resolve(DiscordRpcProvider.cacheFileName(DiscordRpcProvider.contentTag(libraryBytes)));
        if (!Files.isRegularFile(libraryPath) || !DiscordRpcProvider.sameSize(libraryPath, libraryBytes.length)) {
            Path temporaryFile = Files.createTempFile(cacheDirectory, "discord-rpc-", ".part");
            try {
                Files.write(temporaryFile, libraryBytes);
                try {
                    Files.move(temporaryFile, libraryPath, StandardCopyOption.ATOMIC_MOVE);
                }
                catch (IOException atomicMoveFailure) {
                    if (!Files.isRegularFile(libraryPath) || !DiscordRpcProvider.sameSize(libraryPath, libraryBytes.length)) {
                        Files.move(temporaryFile, libraryPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            finally {
                Files.deleteIfExists(temporaryFile);
            }
        }
        DiscordRpcProvider.cleanupStale(cacheDirectory, libraryPath.getFileName().toString());
        return libraryPath;
    }

    private static String cacheFileName(String string) {
        int n = "discord-rpc.dll".lastIndexOf(46);
        String string2 = n > 0 ? "discord-rpc.dll".substring(0, n) : "discord-rpc.dll";
        String string3 = n > 0 ? "discord-rpc.dll".substring(n) : "";
        return string2 + "-" + string + string3;
    }

    private static String contentTag(byte[] byArray) {
        CRC32 cRC32 = new CRC32();
        cRC32.update(byArray, 0, byArray.length);
        return Long.toHexString(cRC32.getValue());
    }

    private static boolean sameSize(Path path, long l) {
        try {
            return Files.size(path) == l;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    private static void cleanupStale(Path path, String string) {
        try {
            Files.deleteIfExists(path.resolve("discord-rpc.dll"));
        }
        catch (IOException iOException) {
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, "discord-rpc-*");){
            for (Path path2 : directoryStream) {
                String string2 = path2.getFileName().toString();
                if (string2.equals(string) || string2.endsWith(".part")) continue;
                try {
                    Files.deleteIfExists(path2);
                }
                catch (IOException iOException) {}
            }
        }
        catch (IOException iOException) {
        }
    }

    private static String resolveResourcePath() {
        String string = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (!string.contains("win")) {
            return null;
        }
        String string2 = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        if (string2.contains("64") || string2.contains("amd64") || string2.contains("x86_64")) {
            return "/win32-x86-64/discord-rpc.dll";
        }
        return "/assets/win32-x86/discord-rpc.dll";
    }
}
