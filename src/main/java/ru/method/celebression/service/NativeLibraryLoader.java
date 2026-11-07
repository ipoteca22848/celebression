package ru.method.celebression.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.security.CodeSource;
import java.util.EnumSet;
import java.util.Locale;
import java.util.zip.CRC32;

public final class NativeLibraryLoader {
    private static final Object LOAD_LOCK = new Object();
    private static volatile boolean loaded;
    private static volatile Throwable loadFailure;

    private NativeLibraryLoader() {
    }

    public static void bootstrap() {
        NativeLibraryLoader.ensureLoaded();
    }

    public static void call(String className) {
        NativeLibraryLoader.ensureLoaded();
        try {
            Class<?> target = NativeLibraryLoader.resolvePluginClass(className);
            NativeLibraryLoader.registerNatives(target);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot resolve class for native registration: " + className, e);
        }
    }

    private static String toBinaryClassName(String internalOrBinary) {
        if (internalOrBinary.indexOf(47) >= 0) {
            return internalOrBinary.replace('/', '.');
        }
        return internalOrBinary;
    }

    private static native void registerNatives(Class<?> var0);

        private static void ensureLoaded() {
        if (loaded) {
            return;
        }
        if (loadFailure != null) {
            throw new IllegalStateException("Native protection-library failed to load earlier", loadFailure);
        }
        Object object = LOAD_LOCK;
        synchronized (object) {
            if (loaded) {
                return;
            }
            if (loadFailure != null) {
                throw new IllegalStateException("Native protection-library failed to load earlier", loadFailure);
            }
            try {
                NativeLibraryLoader.loadFromResource();
                loaded = true;
            }
            catch (Throwable t) {
                loadFailure = t;
                throw new IllegalStateException("Failed to load native protection-library on " + NativeLibraryLoader.osArchSummary(), t);
            }
        }
    }

    private static ClassLoader resolveClassLoader() {
        ClassLoader plugin = NativeLibraryLoader.class.getClassLoader();
        if (plugin != null) {
            return plugin;
        }
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        if (context != null) {
            return context;
        }
        return ClassLoader.getSystemClassLoader();
    }

    private static Class<?> resolvePluginClass(String internalOrBinary) throws ClassNotFoundException {
        ClassLoader loader = NativeLibraryLoader.resolveClassLoader();
        String binary = NativeLibraryLoader.toBinaryClassName(internalOrBinary);
        try {
            return Class.forName(binary, false, loader);
        }
        catch (ClassNotFoundException first) {
            return loader.loadClass(binary);
        }
    }

    private static void loadFromResource() throws IOException {
        String[] candidates = NativeLibraryLoader.libraryResourceCandidates();
        IOException lastIo = null;
        Throwable lastLink = null;
        for (String string : candidates) {
            try {
                InputStream input;
                block16: {
                    input = NativeLibraryLoader.class.getResourceAsStream(string);
                    if (input != null) break block16;
                    if (input == null) continue;
                    input.close();
                    continue;
                }
                Path libFile = NativeLibraryLoader.extractLibrary(input, string);
                try {
                    System.load(libFile.toAbsolutePath().toString());
                    if (input == null) return;
                    input.close();
                    return;
                }
                catch (UnsatisfiedLinkError linkError) {
                    try {
                        if (NativeLibraryLoader.isAlreadyLoaded(linkError)) {
                            return;
                        }
                        lastLink = linkError;
                    }
                    finally {
                        if (input != null) {
                            input.close();
                        }
                    }
                }
            }
            catch (IOException e) {
                lastIo = e;
            }
        }
        if (lastLink != null) {
            throw new IllegalStateException("System.load failed on " + NativeLibraryLoader.osArchSummary() + ". Tried: " + String.join((CharSequence)", ", candidates) + ". " + lastLink.getMessage(), lastLink);
        }
        if (lastIo == null) throw new IllegalStateException("Native protection-library not found in plugin JAR (" + NativeLibraryLoader.osArchSummary() + "). Expected one of: " + String.join((CharSequence)", ", candidates));
        throw lastIo;
    }

    private static String[] libraryResourceCandidates() {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (os.contains("win")) {
            return new String[]{"/natives/windows/library.dll"};
        }
        if (os.contains("mac") || os.contains("darwin")) {
            if (NativeLibraryLoader.isAarch64()) {
                return new String[]{"/natives/macos-arm64/library.dylib", "/natives/macos/library.dylib"};
            }
            return new String[]{"/natives/macos/library.dylib"};
        }
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            if (NativeLibraryLoader.isAarch64()) {
                return new String[]{"/natives/linux-arm64/library.so", "/natives/linux/library.so"};
            }
            return new String[]{"/natives/linux/library.so"};
        }
        throw new UnsupportedOperationException("Unsupported OS: " + System.getProperty("os.name"));
    }

        private static Path extractLibrary(InputStream input, String resourcePath) throws IOException {
        Path libFile;
        String fileName;
        Path cacheDir;
        block13: {
            cacheDir = NativeLibraryLoader.resolveCacheDir();
            Files.createDirectories(cacheDir, new FileAttribute[0]);
            String base = resourcePath.substring(resourcePath.lastIndexOf(47) + 1);
            byte[] bytes = input.readAllBytes();
            fileName = NativeLibraryLoader.cacheFileName(base, NativeLibraryLoader.contentTag(bytes));
            libFile = cacheDir.resolve(fileName);
            if (Files.isRegularFile(libFile, new LinkOption[0]) && NativeLibraryLoader.sameSize(libFile, bytes.length)) {
                NativeLibraryLoader.cleanupStale(cacheDir, fileName);
                return libFile;
            }
            Path tmp = Files.createTempFile(cacheDir, "lib-", ".part", new FileAttribute[0]);
            try {
                Files.write(tmp, bytes, new OpenOption[0]);
                NativeLibraryLoader.makeExecutable(tmp);
                try {
                    Files.move(tmp, libFile, StandardCopyOption.ATOMIC_MOVE);
                }
                catch (IOException moveErr) {
                    if (Files.isRegularFile(libFile, new LinkOption[0]) && NativeLibraryLoader.sameSize(libFile, bytes.length)) break block13;
                    try {
                        Files.move(tmp, libFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                    catch (IOException replaceErr) {
                        if (!Files.isRegularFile(libFile, new LinkOption[0])) {
                            throw replaceErr;
                        }
                    }
                }
            }
            finally {
                try {
                    Files.deleteIfExists(tmp);
                }
                catch (IOException iOException) {}
            }
        }
        NativeLibraryLoader.cleanupStale(cacheDir, fileName);
        return libFile;
    }

    private static String cacheFileName(String resourceName, String tag) {
        String arch = System.getProperty("os.arch", "unknown").replaceAll("[^A-Za-z0-9_.-]", "_");
        int dot = resourceName.lastIndexOf(46);
        if (dot <= 0 || dot == resourceName.length() - 1) {
            return resourceName + "-" + arch + "-" + tag;
        }
        String stem = resourceName.substring(0, dot);
        String ext = resourceName.substring(dot);
        return stem + "-" + arch + "-" + tag + ext;
    }

    private static String contentTag(byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes, 0, bytes.length);
        return Long.toHexString(crc.getValue());
    }

    private static boolean sameSize(Path file, long expected) {
        try {
            return Files.size(file) == expected;
        }
        catch (IOException e) {
            return false;
        }
    }

    private static void cleanupStale(Path cacheDir, String keepName) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(cacheDir, "library-*");){
            for (Path p : stream) {
                String n = p.getFileName().toString();
                if (n.equals(keepName) || n.endsWith(".part")) continue;
                try {
                    Files.deleteIfExists(p);
                }
                catch (IOException iOException) {}
            }
        }
        catch (IOException iOException) {
        }
    }

    private static Path resolveCacheDir() throws IOException {
        URL location;
        CodeSource source = NativeLibraryLoader.class.getProtectionDomain().getCodeSource();
        if (source != null && (location = source.getLocation()) != null) {
            try {
                Path path = Path.of(location.toURI());
                if (Files.isRegularFile(path, new LinkOption[0])) {
                    return path.getParent().resolve(".java2cpp-native");
                }
                if (Files.isDirectory(path, new LinkOption[0])) {
                    return path.resolve(".java2cpp-native");
                }
            }
            catch (IllegalArgumentException | URISyntaxException exception) {
            }
        }
        String home = System.getProperty("user.home", ".");
        return Path.of(home, ".java2cpp", "native-cache");
    }

    private static void makeExecutable(Path libFile) throws IOException {
        if (NativeLibraryLoader.isWindows()) {
            return;
        }
        try {
            EnumSet<PosixFilePermission> perms = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_READ, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_READ, PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(libFile, perms);
        }
        catch (UnsupportedOperationException ignored) {
            libFile.toFile().setExecutable(true);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
    }

    private static boolean isAlreadyLoaded(UnsatisfiedLinkError error) {
        String message = error.getMessage();
        return message != null && message.toLowerCase(Locale.ROOT).contains("already loaded");
    }

    private static boolean isAarch64() {
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        return arch.contains("aarch64") || arch.contains("arm64");
    }

    private static String osArchSummary() {
        return System.getProperty("os.name", "unknown") + " / " + System.getProperty("os.arch", "unknown");
    }
}
