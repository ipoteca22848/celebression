package ru.method.celebression.punchy.client.tuning;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_310;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class TuningProfileManager {
    private static final String EXT = ".json";
    private static final String ACTIVE_PROFILE_FILE = ".active_profile";

    private TuningProfileManager() {
    }

    static List<String> listProfiles() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Path path2 = TuningProfileManager.profilesDir();
        try {
            Files.createDirectories(path2, new FileAttribute[0]);
            try (Stream<Path> stream = Files.list(path2);){
                stream.filter(path -> Files.isRegularFile(path, new LinkOption[0])).map(path -> path.getFileName().toString()).filter(string -> string.toLowerCase().endsWith(".json")).map(string -> string.substring(0, string.length() - ".json".length())).sorted(Comparator.naturalOrder()).forEach(arrayList::add);
            }
        }
        catch (IOException iOException) {
        }
        return arrayList;
    }

    static boolean saveCurrentAs(String string) {
        String string2 = TuningProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        Path path = TuningProfileManager.profilePath(string2);
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            boolean bl = PunchyTuningConfig.saveCurrentProfileDeltaToFile(path);
            return !bl ? false : TuningProfileManager.activateProfile(string2, true);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static boolean loadProfile(String string) {
        String string2 = TuningProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        Path path = TuningProfileManager.profilePath(string2);
        return Files.isRegularFile(path, new LinkOption[0]) && TuningProfileManager.activateProfile(string2, true);
    }

    static boolean deleteProfile(String string) {
        String string2 = TuningProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        try {
            boolean bl = Files.deleteIfExists(TuningProfileManager.profilePath(string2));
            if (bl) {
                Files.deleteIfExists(TuningProfileManager.activeProfilePath());
            }
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static boolean importProfile(Path path, String string) {
        if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
            String string2;
            String string3;
            String string4 = string3 = path.getFileName() == null ? "" : path.getFileName().toString();
            if (string3.toLowerCase().endsWith(".json")) {
                string3 = string3.substring(0, string3.length() - ".json".length());
            }
            if ((string2 = TuningProfileManager.sanitizeName(string != null && !string.isBlank() ? string : string3)).isEmpty()) {
                return false;
            }
            Path path2 = TuningProfileManager.profilePath(string2);
            try {
                Files.createDirectories(path2.getParent(), new FileAttribute[0]);
                Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING);
                return TuningProfileManager.activateProfile(string2, true);
            }
            catch (IOException iOException) {
                return false;
            }
        }
        return false;
    }

    public static String getActiveProfileName() {
        Path path = TuningProfileManager.activeProfilePath();
        try {
            if (!Files.isRegularFile(path, new LinkOption[0])) {
                return null;
            }
            String string = Files.readString(path).trim();
            String string2 = TuningProfileManager.sanitizeName(string);
            return string2.isEmpty() ? null : string2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static Path getActiveProfileFilePath() {
        String string = TuningProfileManager.getActiveProfileName();
        if (string != null && !string.isBlank()) {
            Path path = TuningProfileManager.profilePath(string);
            if (Files.isRegularFile(path, new LinkOption[0])) {
                return path;
            }
            try {
                Files.deleteIfExists(TuningProfileManager.activeProfilePath());
            }
            catch (IOException iOException) {
            }
            return null;
        }
        return null;
    }

    private static String sanitizeName(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim().replaceAll("[^a-zA-Z0-9._-]", "_");
        while (string2.startsWith(".")) {
            string2 = string2.substring(1);
        }
        return string2;
    }

    private static Path profilesDir() {
        return TuningProfileManager.punchyDir().resolve("profiles");
    }

    static Path profilesDirectoryPath() {
        Path path = TuningProfileManager.profilesDir();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (IOException iOException) {
        }
        return path;
    }

    static boolean unloadActiveProfileToDefault() {
        Path path = TuningProfileManager.activeProfilePath();
        try {
            Files.deleteIfExists(path);
            class_310 class_3102 = class_310.method_1551();
            PunchyTuningConfig.reloadActiveProfileFast(class_3102 != null ? class_3102.method_1478() : null);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    private static Path activeProfilePath() {
        return TuningProfileManager.profilesDir().resolve(".active_profile");
    }

    private static Path profilePath(String string) {
        return TuningProfileManager.profilesDir().resolve(string + ".json");
    }

    private static Path punchyDir() {
        class_310 class_3102 = class_310.method_1551();
        Path path = ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath();
        return path.resolve("config").resolve("punchy");
    }

    private static boolean activateProfile(String string, boolean bl) {
        Path path = TuningProfileManager.profilePath(string);
        try {
            if (path != null && Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(TuningProfileManager.activeProfilePath().getParent(), new FileAttribute[0]);
                Files.writeString(TuningProfileManager.activeProfilePath(), (CharSequence)TuningProfileManager.sanitizeName(string), new OpenOption[0]);
                if (bl) {
                    class_310 class_3102 = class_310.method_1551();
                    PunchyTuningConfig.reloadActiveProfileFast(class_3102 != null ? class_3102.method_1478() : null);
                }
                return true;
            }
            return false;
        }
        catch (IOException iOException) {
            return false;
        }
    }
}

