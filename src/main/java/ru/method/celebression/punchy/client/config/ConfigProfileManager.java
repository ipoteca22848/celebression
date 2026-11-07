package ru.method.celebression.punchy.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_310;
import ru.method.celebression.punchy.config.PunchyConfig;

final class ConfigProfileManager {
    private static final Gson GSON;
    private static final String EXTENSION;
    private static final String ACTIVE_PROFILE_FILE;

    private ConfigProfileManager() {
    }

    static Path profilesDirectoryPath() {
        class_310 class_3102 = class_310.method_1551();
        Path path = ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath().resolve("config");
        return path.resolve("punchy").resolve("config_profiles");
    }

    static List<String> listProfiles() {
        Path path2 = ConfigProfileManager.profilesDirectoryPath();
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            Files.createDirectories(path2, new FileAttribute[0]);
            try (Stream<Path> stream = Files.list(path2);){
                stream.filter(path -> Files.isRegularFile(path, new LinkOption[0]) && path.getFileName().toString().endsWith(".json")).map(path -> ConfigProfileManager.stripExtension(path.getFileName().toString())).filter(string -> !string.isBlank()).sorted(Comparator.naturalOrder()).forEach(arrayList::add);
            }
        }
        catch (IOException iOException) {
        }
        return arrayList;
    }

    static boolean saveCurrentAs(String string, PunchyConfig.Data data) {
        String string2 = ConfigProfileManager.sanitizeProfileName(string);
        if (!string2.isBlank() && data != null) {
            Path path = ConfigProfileManager.profilePath(string2);
            try {
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                PunchyConfig.Data data2 = (PunchyConfig.Data)GSON.fromJson(GSON.toJson((Object)data), PunchyConfig.Data.class);
                if (data2 == null) {
                    return false;
                }
                data2.animationPackSelections = new LinkedHashMap<String, String>();
                data2.animationPackTypeSelections = new LinkedHashMap<String, String>();
                data2.mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                    GSON.toJson((Object)data2, (Appendable)bufferedWriter);
                }
                return true;
            }
            catch (IOException iOException) {
                return false;
            }
        }
        return false;
    }

    static PunchyConfig.Data load(String string) {
        String string2 = ConfigProfileManager.sanitizeProfileName(string);
        if (string2.isBlank()) {
            return null;
        }
        Path path = ConfigProfileManager.profilePath(string2);
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return null;
        }
        try {
            PunchyConfig.Data data;
            try (BufferedReader bufferedReader = Files.newBufferedReader(path);){
                data = (PunchyConfig.Data)GSON.fromJson((Reader)bufferedReader, PunchyConfig.Data.class);
            }
            return data;
        }
        catch (Exception exception) {
            return null;
        }
    }

    static boolean deleteProfile(String string) {
        String string2 = ConfigProfileManager.sanitizeProfileName(string);
        if (string2.isBlank()) {
            return false;
        }
        Path path = ConfigProfileManager.profilePath(string2);
        try {
            String string3;
            boolean bl = Files.deleteIfExists(path);
            if (bl && string2.equals(string3 = ConfigProfileManager.getActiveProfileName())) {
                ConfigProfileManager.setActiveProfileName(null);
            }
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static String getActiveProfileName() {
        Path path = ConfigProfileManager.activeProfilePath();
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return null;
        }
        try {
            String string = Files.readString(path);
            String string2 = ConfigProfileManager.sanitizeProfileName(string);
            return string2.isBlank() ? null : string2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    static void setActiveProfileName(String string) {
        Path path = ConfigProfileManager.activeProfilePath();
        String string2 = ConfigProfileManager.sanitizeProfileName(string);
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            if (string2.isBlank()) {
                Files.deleteIfExists(path);
                return;
            }
            Files.writeString(path, (CharSequence)string2, new OpenOption[0]);
        }
        catch (IOException iOException) {
        }
    }

    static String sanitizeProfileName(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim();
        if (string2.isBlank()) {
            return "";
        }
        string2 = string2.replaceAll("[\\\\/:*?\"<>|]", "_");
        if ((string2 = string2.replaceAll("\\s+", "_")).length() > 64) {
            string2 = string2.substring(0, 64);
        }
        return string2;
    }

    private static Path profilePath(String string) {
        return ConfigProfileManager.profilesDirectoryPath().resolve(string + ".json");
    }

    private static Path activeProfilePath() {
        return ConfigProfileManager.profilesDirectoryPath().resolve("active_profile.txt");
    }

    private static String stripExtension(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        return string.endsWith(".json") ? string.substring(0, string.length() - ".json".length()) : string;
    }

    static {
        EXTENSION = ".json";
        ACTIVE_PROFILE_FILE = "active_profile.txt";
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}

