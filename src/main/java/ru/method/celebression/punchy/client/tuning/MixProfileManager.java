package ru.method.celebression.punchy.client.tuning;

import ru.method.celebression.animation.Animation;

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
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.config.PunchyConfig;

final class MixProfileManager {
    private static final Gson GSON;
    private static final String EXT;
    private static final String ACTIVE_PROFILE_FILE;

    private MixProfileManager() {
    }

    static List<String> listProfiles() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Path path2 = MixProfileManager.profilesDir();
        try {
            Files.createDirectories(path2, new FileAttribute[0]);
            try (Stream<Path> stream = Files.list(path2);){
                stream.filter(path -> Files.isRegularFile(path, new LinkOption[0])).map(path -> path.getFileName().toString()).filter(string -> string.toLowerCase(Locale.ROOT).endsWith(".json")).map(string -> string.substring(0, string.length() - ".json".length())).sorted(Comparator.naturalOrder()).forEach(arrayList::add);
            }
        }
        catch (IOException iOException) {
        }
        return arrayList;
    }

    static boolean saveCurrentAs(String string) {
        String string2 = MixProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        Path path = MixProfileManager.profilePath(string2);
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            MixProfileData mixProfileData = MixProfileManager.readFromCurrentConfig();
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                GSON.toJson((Object)mixProfileData, (Appendable)bufferedWriter);
            }
            Files.createDirectories(MixProfileManager.activeProfilePath().getParent(), new FileAttribute[0]);
            Files.writeString(MixProfileManager.activeProfilePath(), (CharSequence)string2, new OpenOption[0]);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static boolean loadProfile(String string) {
        String string2 = MixProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        Path path = MixProfileManager.profilePath(string2);
        return MixProfileManager.loadProfileInternal(string2, path, true);
    }

    static boolean deleteProfile(String string) {
        String string2 = MixProfileManager.sanitizeName(string);
        if (string2.isEmpty()) {
            return false;
        }
        try {
            boolean bl = Files.deleteIfExists(MixProfileManager.profilePath(string2));
            String string3 = MixProfileManager.getActiveProfileName();
            if (string3 != null && string3.equals(string2)) {
                Files.deleteIfExists(MixProfileManager.activeProfilePath());
            }
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static String getActiveProfileName() {
        Path path = MixProfileManager.activeProfilePath();
        try {
            if (!Files.isRegularFile(path, new LinkOption[0])) {
                return null;
            }
            String string = Files.readString(path).trim();
            String string2 = MixProfileManager.sanitizeName(string);
            return string2.isEmpty() ? null : string2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    static Path profilesDirectoryPath() {
        Path path = MixProfileManager.profilesDir();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (IOException iOException) {
        }
        return path;
    }

    static boolean unloadActiveProfileToDefault() {
        Path path = MixProfileManager.activeProfilePath();
        try {
            Files.deleteIfExists(path);
            PunchyAnimationManager.requestTuningReload();
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    static boolean restoreActiveProfileForStartup() {
        String string = MixProfileManager.getActiveProfileName();
        if (string != null && !string.isBlank()) {
            Path path = MixProfileManager.profilePath(string);
            return MixProfileManager.loadProfileInternal(string, path, false);
        }
        return false;
    }

    private static boolean loadProfileInternal(String string, Path path, boolean bl) {
        if (string != null && !string.isBlank() && path != null && Files.isRegularFile(path, new LinkOption[0])) {
            try {
                boolean bl2;
                try (BufferedReader bufferedReader = Files.newBufferedReader(path);){
                    MixProfileData mixProfileData = (MixProfileData)GSON.fromJson((Reader)bufferedReader, MixProfileData.class);
                    MixProfileManager.applyToCurrentConfig(mixProfileData);
                    Files.createDirectories(MixProfileManager.activeProfilePath().getParent(), new FileAttribute[0]);
                    Files.writeString(MixProfileManager.activeProfilePath(), (CharSequence)string, new OpenOption[0]);
                    if (bl) {
                        PunchyAnimationManager.requestTuningReload();
                    }
                    bl2 = true;
                }
                return bl2;
            }
            catch (Exception exception) {
                return false;
            }
        }
        return false;
    }

    private static MixProfileData readFromCurrentConfig() {
        PunchyConfig.Data data = PunchyConfig.snapshot();
        MixProfileData mixProfileData = new MixProfileData();
        if (data.animationPackSelections != null) {
            mixProfileData.animationPackSelections.putAll(data.animationPackSelections);
        }
        if (data.animationPackTypeSelections != null) {
            mixProfileData.animationPackTypeSelections.putAll(data.animationPackTypeSelections);
        }
        if (data.mixSpecificForceOverride != null) {
            mixProfileData.mixSpecificForceOverride.putAll(data.mixSpecificForceOverride);
        }
        return mixProfileData;
    }

    private static void applyToCurrentConfig(MixProfileData mixProfileData) {
        PunchyConfig.Data data = PunchyConfig.snapshot();
        data.animationPackSelections = new LinkedHashMap<String, String>();
        data.animationPackTypeSelections = new LinkedHashMap<String, String>();
        data.mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
        if (mixProfileData != null) {
            if (mixProfileData.animationPackSelections != null) {
                data.animationPackSelections.putAll(mixProfileData.animationPackSelections);
            }
            if (mixProfileData.animationPackTypeSelections != null) {
                data.animationPackTypeSelections.putAll(mixProfileData.animationPackTypeSelections);
            }
            if (mixProfileData.mixSpecificForceOverride != null) {
                data.mixSpecificForceOverride.putAll(mixProfileData.mixSpecificForceOverride);
            }
        }
        PunchyConfig.applyAndSave(data);
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
        class_310 class_3102 = class_310.method_1551();
        Path path = ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath();
        return path.resolve("config").resolve("punchy").resolve("mix_profiles");
    }

    private static Path profilePath(String string) {
        return MixProfileManager.profilesDir().resolve(string + ".json");
    }

    private static Path activeProfilePath() {
        return MixProfileManager.profilesDir().resolve(".active_mix_profile");
    }

    static {
        EXT = ".json";
        ACTIVE_PROFILE_FILE = ".active_mix_profile";
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }

    static final class MixProfileData {
        LinkedHashMap<String, String> animationPackSelections = new LinkedHashMap();
        LinkedHashMap<String, String> animationPackTypeSelections = new LinkedHashMap();
        LinkedHashMap<String, Boolean> mixSpecificForceOverride = new LinkedHashMap();

        MixProfileData() {
        }
    }
}

