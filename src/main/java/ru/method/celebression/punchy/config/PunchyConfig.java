package ru.method.celebression.punchy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_7923;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.ViewModelModule;

public final class PunchyConfig {
    private static final Gson GSON;
    private static final String FILE_NAME;
    public static final float ANIMATION_SPEED_DEFAULT = 5.5f;
    public static final float ANIMATION_SPEED_MIN = 0.5f;
    public static final float ANIMATION_SPEED_MAX = 7.0f;
    public static final float FIRSTPERSON_HIDE_PITCH_DEFAULT = 60.0f;
    public static final float FIRSTPERSON_HIDE_PITCH_MIN = 0.0f;
    public static final float FIRSTPERSON_HIDE_PITCH_MAX = 90.0f;
    public static final float FIRSTPERSON_HIDE_PITCH_NEVER_THRESHOLD = 85.0f;
    public static final float SPRINT_ARM_SWING_INTENSITY_DEFAULT = 1.0f;
    public static final float SPRINT_ARM_SWING_INTENSITY_MIN = -1.0f;
    public static final float SPRINT_ARM_SWING_INTENSITY_MAX = 1.0f;
    public static final float SPRINT_ARM_SWING_SPEED_DEFAULT = 0.0f;
    public static final float SPRINT_ARM_SWING_SPEED_MIN = -1.0f;
    public static final float SPRINT_ARM_SWING_SPEED_MAX = 1.0f;
    private static Data DATA;

    private PunchyConfig() {
    }

    public static void load() {
        Data data;
        Path path;
        block8: {
            Path path2 = PunchyConfig.getConfigDirectory().resolve("punchy");
            path = path2.resolve("punchy_config.json");
            data = null;
            try {
                Files.createDirectories(path2, new FileAttribute[0]);
                if (!Files.exists(path, new LinkOption[0])) break block8;
                try (FileReader fileReader = new FileReader(path.toFile());){
                    data = (Data)GSON.fromJson((Reader)fileReader, Data.class);
                }
            }
            catch (Exception exception) {
            }
        }
        if (data == null) {
            data = new Data();
            PunchyConfig.write(path, data);
        }
        DATA = PunchyConfig.sanitize(data);
    }

    public static boolean renderArmorArmsFP() {
        return DATA != null && PunchyConfig.DATA.renderArmorArmsFP;
    }

    public static boolean isInteractiveStuffCompatEnabled() {
        return DATA != null && PunchyConfig.DATA.interactiveStuffCompat;
    }

    public static boolean isBetterCombatCompatEnabled() {
        return DATA != null && PunchyConfig.DATA.bettercombatCompat;
    }

    public static boolean isSwordBlockingCompatEnabled() {
        return DATA != null && PunchyConfig.DATA.swordBlockingCompat;
    }

    public static float getAnimationSpeed() {
        return DATA == null ? 5.5f : PunchyConfig.DATA.animationSpeed;
    }

    public static float getFirstPersonModelHidePitch() {
        return DATA == null ? 60.0f : PunchyConfig.DATA.firstPersonModelHidePitch;
    }

    public static boolean isFirstPersonModelHideNever() {
        return PunchyConfig.getFirstPersonModelHidePitch() > 85.0f;
    }

    public static boolean isFirstPersonModelHideEnabled() {
        return DATA == null || PunchyConfig.DATA.firstPersonModelHideEnabled;
    }

    public static boolean isModEnabled() {
        if (ru.method.celebression.service.UnhookService.isActive()) return false;
        if (!PunchyConfig.punchModeActive()) {
            return false;
        }
        return DATA == null || PunchyConfig.DATA.enableMod;
    }

    private static boolean punchModeActive() {
        try {
            Celebression celebression = Celebression.getInstance();
            if (celebression == null || celebression.getModuleManager() == null) {
                return false;
            }
            ViewModelModule viewModelModule = celebression.getModuleManager().getModule(ViewModelModule.class);
            if (viewModelModule == null || !viewModelModule.isToggled()) {
                return false;
            }
            return viewModelModule.jN();
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static boolean isTuningEnabled() {
        return DATA != null && PunchyConfig.DATA.enableTuning;
    }

    public static boolean isItemBlacklisted(class_1799 class_17992) {
        return DATA != null && PunchyConfig.DATA.itemBlacklist != null && class_17992 != null && !class_17992.method_7960() ? PunchyConfig.findBlacklistMatch(class_17992) != null : false;
    }

    public static boolean isItemBlacklistDualHanded(class_1799 class_17992) {
        if (DATA != null && PunchyConfig.DATA.blacklistApplyDualHanded != null && class_17992 != null && !class_17992.method_7960()) {
            String string = PunchyConfig.findBlacklistMatch(class_17992);
            return string == null ? false : Boolean.TRUE.equals(PunchyConfig.DATA.blacklistApplyDualHanded.get(string));
        }
        return false;
    }

    public static boolean isHandBlacklisted(class_1657 class_16572, class_1268 class_12682) {
        if (class_16572 == null) {
            return false;
        }
        class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
        class_1799 class_17992 = class_16572.method_5998(class_12683);
        if (PunchyConfig.isItemBlacklisted(class_17992)) {
            return true;
        }
        class_1268 class_12684 = class_12683 == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
        class_1799 class_17993 = class_16572.method_5998(class_12684);
        return PunchyConfig.isItemBlacklistDualHanded(class_17993);
    }

    private static boolean matchesBlacklistEntry(String string, String string2, String string3) {
        if (string != null && !string.isBlank()) {
            boolean bl;
            String string4 = string.trim().toLowerCase(Locale.ROOT);
            if (string4.endsWith(":")) {
                string4 = string4.substring(0, string4.length() - 1);
            }
            if (string4.isEmpty()) {
                return false;
            }
            boolean bl2 = string4.indexOf(42) >= 0 || string4.indexOf(63) >= 0;
            boolean bl3 = bl = string4.indexOf(58) >= 0;
            if (bl) {
                return !bl2 ? string2.equals(string4) : PunchyConfig.globMatch(string4, string2);
            }
            return !bl2 ? string3.equals(string4) : PunchyConfig.globMatch(string4, string3);
        }
        return false;
    }

    private static boolean globMatch(String string, String string2) {
        if (string != null && string2 != null) {
            StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
            block16: for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                switch (c) {
                    case '$': {
                        stringBuilder.append("\\$");
                        continue block16;
                    }
                    case '(': {
                        stringBuilder.append("\\(");
                        continue block16;
                    }
                    case ')': {
                        stringBuilder.append("\\)");
                        continue block16;
                    }
                    case '*': {
                        stringBuilder.append(".*");
                        continue block16;
                    }
                    case '+': {
                        stringBuilder.append("\\+");
                        continue block16;
                    }
                    case '.': {
                        stringBuilder.append("\\.");
                        continue block16;
                    }
                    case '?': {
                        stringBuilder.append('.');
                        continue block16;
                    }
                    case '[': {
                        stringBuilder.append("\\[");
                        continue block16;
                    }
                    case '\\': {
                        stringBuilder.append("\\\\");
                        continue block16;
                    }
                    case ']': {
                        stringBuilder.append("\\]");
                        continue block16;
                    }
                    case '^': {
                        stringBuilder.append("\\^");
                        continue block16;
                    }
                    case '{': {
                        stringBuilder.append("\\{");
                        continue block16;
                    }
                    case '|': {
                        stringBuilder.append("\\|");
                        continue block16;
                    }
                    case '}': {
                        stringBuilder.append("\\}");
                        continue block16;
                    }
                    default: {
                        stringBuilder.append(c);
                    }
                }
            }
            return string2.matches(stringBuilder.toString());
        }
        return false;
    }

    public static boolean disableResourcePackModelParts() {
        return DATA != null && PunchyConfig.DATA.disableResourcePackModelParts;
    }

    public static boolean disableArmPhysics() {
        return DATA != null && PunchyConfig.DATA.disableArmPhysics;
    }

    public static boolean disableNativeItemPhysics() {
        return DATA != null && PunchyConfig.DATA.disableNativeItemPhysics;
    }

    public static boolean disableBoatMinecartRaftModels() {
        return DATA != null && (PunchyConfig.DATA.disableBoatMinecartRaftModels || PunchyConfig.DATA.interactiveStuffCompat);
    }

    public static boolean disablePistonModels() {
        return DATA != null && (PunchyConfig.DATA.disablePistonModels || PunchyConfig.DATA.interactiveStuffCompat);
    }

    public static boolean disableChestModels() {
        return DATA != null && (PunchyConfig.DATA.disableChestModels || PunchyConfig.DATA.interactiveStuffCompat);
    }

    public static boolean disableEnchantingTableModels() {
        return DATA != null && (PunchyConfig.DATA.disableEnchantingTableModels || PunchyConfig.DATA.interactiveStuffCompat);
    }

    public static boolean disableBoatFirstPersonAnimations() {
        return DATA != null && PunchyConfig.DATA.disableBoatFirstPersonAnimations;
    }

    public static boolean disableEnhancedFireArmEffects() {
        return DATA != null && PunchyConfig.DATA.disableEnhancedFireArmEffects;
    }

    public static boolean enableCrawlAnimation() {
        return DATA != null && PunchyConfig.DATA.enableCrawlAnimation;
    }

    public static boolean enableClimbAnimation() {
        return DATA != null && PunchyConfig.DATA.enableClimbAnimation;
    }

    public static boolean enableSwimAnimation() {
        return DATA != null && PunchyConfig.DATA.enableSwimAnimation;
    }

    public static boolean enableElytraFlightAnimation() {
        return DATA == null || PunchyConfig.DATA.enableElytraFlightAnimation;
    }

    public static boolean enableCustomWalk() {
        return DATA == null || PunchyConfig.DATA.enableCustomWalk;
    }

    public static boolean enableSprintArmSwing() {
        return DATA == null || PunchyConfig.DATA.enableSprintArmSwing;
    }

    public static boolean enableFallArmAnimation() {
        return DATA == null || PunchyConfig.DATA.enableFallArmAnimation;
    }

    public static float getSprintArmSwingIntensity() {
        return DATA == null ? 1.0f : PunchyConfig.DATA.sprintArmSwingIntensity;
    }

    public static float getSprintArmSwingSpeed() {
        return DATA == null ? 0.0f : PunchyConfig.DATA.sprintArmSwingSpeed;
    }

    public static boolean enableFreezeShake() {
        return DATA == null || PunchyConfig.DATA.enableFreezeShake;
    }

    public static boolean enableFreezeArmOverlay() {
        return DATA == null || PunchyConfig.DATA.enableFreezeArmOverlay;
    }

    public static boolean enableMud() {
        return DATA == null || PunchyConfig.DATA.enableMud;
    }

    public static boolean enableSweat() {
        return false;
    }

    public static boolean enableBurn() {
        return DATA == null || PunchyConfig.DATA.enableBurn;
    }

    public static boolean enableParticles() {
        return DATA == null || PunchyConfig.DATA.enableParticles;
    }

    public static boolean enableModelPartsGlow() {
        return DATA != null && PunchyConfig.DATA.enableModelPartsGlow;
    }

    public static boolean enableBlurGlowAtDark() {
        return DATA != null && PunchyConfig.DATA.enableBlurGlowAtDark;
    }

    public static Data snapshot() {
        Data data = new Data();
        if (DATA != null) {
            data.renderArmorArmsFP = PunchyConfig.DATA.renderArmorArmsFP;
            data.interactiveStuffCompat = PunchyConfig.DATA.interactiveStuffCompat;
            data.bettercombatCompat = PunchyConfig.DATA.bettercombatCompat;
            data.swordBlockingCompat = PunchyConfig.DATA.swordBlockingCompat;
            data.animationSpeed = PunchyConfig.DATA.animationSpeed;
            data.enableMod = PunchyConfig.DATA.enableMod;
            data.enableTuning = PunchyConfig.DATA.enableTuning;
            data.disableResourcePackModelParts = PunchyConfig.DATA.disableResourcePackModelParts;
            data.disableArmPhysics = PunchyConfig.DATA.disableArmPhysics;
            data.disableNativeItemPhysics = PunchyConfig.DATA.disableNativeItemPhysics;
            data.disableBoatMinecartRaftModels = PunchyConfig.DATA.disableBoatMinecartRaftModels;
            data.disablePistonModels = PunchyConfig.DATA.disablePistonModels;
            data.disableChestModels = PunchyConfig.DATA.disableChestModels;
            data.disableEnchantingTableModels = PunchyConfig.DATA.disableEnchantingTableModels;
            data.disableBoatFirstPersonAnimations = PunchyConfig.DATA.disableBoatFirstPersonAnimations;
            data.disableEnhancedFireArmEffects = PunchyConfig.DATA.disableEnhancedFireArmEffects;
            data.enableCrawlAnimation = PunchyConfig.DATA.enableCrawlAnimation;
            data.enableClimbAnimation = PunchyConfig.DATA.enableClimbAnimation;
            data.enableSwimAnimation = PunchyConfig.DATA.enableSwimAnimation;
            data.enableElytraFlightAnimation = PunchyConfig.DATA.enableElytraFlightAnimation;
            data.enableCustomWalk = PunchyConfig.DATA.enableCustomWalk;
            data.enableSprintArmSwing = PunchyConfig.DATA.enableSprintArmSwing;
            data.enableFallArmAnimation = PunchyConfig.DATA.enableFallArmAnimation;
            data.sprintArmSwingIntensity = PunchyConfig.DATA.sprintArmSwingIntensity;
            data.sprintArmSwingSpeed = PunchyConfig.DATA.sprintArmSwingSpeed;
            data.enableFreezeShake = PunchyConfig.DATA.enableFreezeShake;
            data.enableFreezeArmOverlay = PunchyConfig.DATA.enableFreezeArmOverlay;
            data.enableMud = PunchyConfig.DATA.enableMud;
            data.enableSweat = PunchyConfig.DATA.enableSweat;
            data.enableBurn = PunchyConfig.DATA.enableBurn;
            data.enableParticles = PunchyConfig.DATA.enableParticles;
            data.enableModelPartsGlow = PunchyConfig.DATA.enableModelPartsGlow;
            data.enableBlurGlowAtDark = PunchyConfig.DATA.enableBlurGlowAtDark;
            data.firstPersonModelHideEnabled = PunchyConfig.DATA.firstPersonModelHideEnabled;
            data.firstPersonModelHidePitch = PunchyConfig.DATA.firstPersonModelHidePitch;
            data.itemBlacklist = PunchyConfig.DATA.itemBlacklist == null ? new ArrayList<String>() : new ArrayList<String>(PunchyConfig.DATA.itemBlacklist);
            data.blacklistApplyDualHanded = PunchyConfig.DATA.blacklistApplyDualHanded == null ? new LinkedHashMap<String, Boolean>() : new LinkedHashMap<String, Boolean>(PunchyConfig.DATA.blacklistApplyDualHanded);
            data.animationPackSelections = PunchyConfig.DATA.animationPackSelections == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(PunchyConfig.DATA.animationPackSelections);
            data.animationPackTypeSelections = PunchyConfig.DATA.animationPackTypeSelections == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(PunchyConfig.DATA.animationPackTypeSelections);
            data.mixSpecificForceOverride = PunchyConfig.DATA.mixSpecificForceOverride == null ? new LinkedHashMap<String, Boolean>() : new LinkedHashMap<String, Boolean>(PunchyConfig.DATA.mixSpecificForceOverride);
        }
        return data;
    }

    public static String getAnimationPackSelection(String string) {
        return string != null && !string.isBlank() && DATA != null && PunchyConfig.DATA.animationPackSelections != null ? PunchyConfig.DATA.animationPackSelections.get(string) : null;
    }

    public static void setAnimationPackSelection(String string, String string2) {
        if (string != null && !string.isBlank()) {
            if (DATA == null) {
                DATA = new Data();
            }
            if (PunchyConfig.DATA.animationPackSelections == null) {
                PunchyConfig.DATA.animationPackSelections = new LinkedHashMap<String, String>();
            }
            if (string2 != null && !string2.isBlank()) {
                PunchyConfig.DATA.animationPackSelections.put(string, string2.trim().toLowerCase(Locale.ROOT));
            } else {
                PunchyConfig.DATA.animationPackSelections.remove(string);
            }
            DATA = PunchyConfig.sanitize(DATA);
            PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
        }
    }

    public static String getAnimationPackTypeSelection(String string, String string2) {
        return string != null && !string.isBlank() && string2 != null && !string2.isBlank() && DATA != null && PunchyConfig.DATA.animationPackTypeSelections != null ? PunchyConfig.DATA.animationPackTypeSelections.get(string + "|" + string2.trim().toLowerCase(Locale.ROOT)) : null;
    }

    public static void setAnimationPackTypeSelection(String string, String string2, String string3) {
        if (string != null && !string.isBlank() && string2 != null && !string2.isBlank()) {
            if (DATA == null) {
                DATA = new Data();
            }
            if (PunchyConfig.DATA.animationPackTypeSelections == null) {
                PunchyConfig.DATA.animationPackTypeSelections = new LinkedHashMap<String, String>();
            }
            String string4 = string + "|" + string2.trim().toLowerCase(Locale.ROOT);
            if (string3 != null && !string3.isBlank()) {
                PunchyConfig.DATA.animationPackTypeSelections.put(string4, string3.trim().toLowerCase(Locale.ROOT));
            } else {
                PunchyConfig.DATA.animationPackTypeSelections.remove(string4);
            }
            DATA = PunchyConfig.sanitize(DATA);
            PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
        }
    }

    public static void clearAnimationPackTypeSelections(String string) {
        if (string != null && !string.isBlank() && DATA != null && PunchyConfig.DATA.animationPackTypeSelections != null) {
            String string2 = string + "|";
            PunchyConfig.DATA.animationPackTypeSelections.entrySet().removeIf(entry -> entry != null && entry.getKey() != null && ((String)entry.getKey()).startsWith(string2));
            DATA = PunchyConfig.sanitize(DATA);
            PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
        }
    }

    public static void clearAnimationPackSelections() {
        if (DATA == null) {
            DATA = new Data();
        }
        if (PunchyConfig.DATA.animationPackSelections == null) {
            PunchyConfig.DATA.animationPackSelections = new LinkedHashMap<String, String>();
        } else {
            PunchyConfig.DATA.animationPackSelections.clear();
        }
        if (PunchyConfig.DATA.animationPackTypeSelections == null) {
            PunchyConfig.DATA.animationPackTypeSelections = new LinkedHashMap<String, String>();
        } else {
            PunchyConfig.DATA.animationPackTypeSelections.clear();
        }
        if (PunchyConfig.DATA.mixSpecificForceOverride == null) {
            PunchyConfig.DATA.mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
        } else {
            PunchyConfig.DATA.mixSpecificForceOverride.clear();
        }
        DATA = PunchyConfig.sanitize(DATA);
        PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
    }

    public static void replaceAnimationPackSelections(Map<String, String> map, Map<String, String> map2, Map<String, Boolean> map3) {
        if (DATA == null) {
            DATA = new Data();
        }
        PunchyConfig.DATA.animationPackSelections = map == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(map);
        PunchyConfig.DATA.animationPackTypeSelections = map2 == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(map2);
        PunchyConfig.DATA.mixSpecificForceOverride = map3 == null ? new LinkedHashMap<String, Boolean>() : new LinkedHashMap<String, Boolean>(map3);
        DATA = PunchyConfig.sanitize(DATA);
        PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
    }

    public static boolean isMixSpecificForceOverride(String string) {
        return string != null && !string.isBlank() && DATA != null && PunchyConfig.DATA.mixSpecificForceOverride != null ? Boolean.TRUE.equals(PunchyConfig.DATA.mixSpecificForceOverride.get(string)) : false;
    }

    public static void setMixSpecificForceOverride(String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            if (DATA == null) {
                DATA = new Data();
            }
            if (PunchyConfig.DATA.mixSpecificForceOverride == null) {
                PunchyConfig.DATA.mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
            }
            if (bl) {
                PunchyConfig.DATA.mixSpecificForceOverride.put(string, true);
            } else {
                PunchyConfig.DATA.mixSpecificForceOverride.remove(string);
            }
            DATA = PunchyConfig.sanitize(DATA);
            PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
        }
    }

    public static void applyAndSave(Data data) {
        if (data != null) {
            DATA = PunchyConfig.sanitize(data);
            PunchyConfig.write(PunchyConfig.getConfigFile(), DATA);
        }
    }

    private static void write(Path path, Data data) {
        try (FileWriter fileWriter = new FileWriter(path.toFile());){
            GSON.toJson((Object)data, (Appendable)fileWriter);
        }
        catch (Exception exception) {
        }
    }

    private static Path getConfigFile() {
        return PunchyConfig.getConfigDirectory().resolve("punchy").resolve("punchy_config.json");
    }

    private static Path getConfigDirectory() {
        class_310 class_3102 = class_310.method_1551();
        return ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath().resolve("config");
    }

    private static Data sanitize(Data data) {
        if (data == null) {
            return new Data();
        }
        data.animationSpeed = class_3532.method_15363((float)data.animationSpeed, (float)0.5f, (float)7.0f);
        data.firstPersonModelHidePitch = class_3532.method_15363((float)data.firstPersonModelHidePitch, (float)0.0f, (float)90.0f);
        data.sprintArmSwingIntensity = class_3532.method_15363((float)data.sprintArmSwingIntensity, (float)-1.0f, (float)1.0f);
        data.sprintArmSwingSpeed = class_3532.method_15363((float)data.sprintArmSwingSpeed, (float)-1.0f, (float)1.0f);
        if (data.itemBlacklist == null) {
            data.itemBlacklist = new ArrayList<String>();
        }
        if (data.blacklistApplyDualHanded == null) {
            data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>();
        }
        if (data.animationPackSelections == null) {
            data.animationPackSelections = new LinkedHashMap<String, String>();
        }
        if (data.animationPackTypeSelections == null) {
            data.animationPackTypeSelections = new LinkedHashMap<String, String>();
        }
        if (data.mixSpecificForceOverride == null) {
            data.mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
        }
        LinkedHashSet<String> normalizedBlacklist = new LinkedHashSet<String>();
        for (String itemId : data.itemBlacklist) {
            if (itemId == null) continue;
            String normalizedItemId = itemId.trim().toLowerCase(Locale.ROOT);
            if (normalizedItemId.endsWith(":")) {
                normalizedItemId = normalizedItemId.substring(0, normalizedItemId.length() - 1);
            }
            if (normalizedItemId.isEmpty()) continue;
            normalizedBlacklist.add(normalizedItemId);
        }
        data.itemBlacklist = new ArrayList<String>(normalizedBlacklist);
        LinkedHashMap<String, Boolean> dualHandedByItem = new LinkedHashMap<String, Boolean>();
        for (Map.Entry<String, Boolean> entry : data.blacklistApplyDualHanded.entrySet()) {
            if (entry == null || entry.getKey() == null) continue;
            String normalizedItemId = entry.getKey().trim().toLowerCase(Locale.ROOT);
            if (normalizedItemId.endsWith(":")) {
                normalizedItemId = normalizedItemId.substring(0, normalizedItemId.length() - 1);
            }
            if (normalizedItemId.isEmpty()) continue;
            dualHandedByItem.put(normalizedItemId, Boolean.TRUE.equals(entry.getValue()));
        }
        LinkedHashMap<String, Boolean> normalizedDualHanded = new LinkedHashMap<String, Boolean>();
        for (String itemId : data.itemBlacklist) {
            normalizedDualHanded.put(itemId, Boolean.TRUE.equals(dualHandedByItem.get(itemId)));
        }
        data.blacklistApplyDualHanded = normalizedDualHanded;
        LinkedHashMap<String, String> normalizedPackSelections = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : data.animationPackSelections.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            String animationId = entry.getKey().trim();
            String packId = entry.getValue().trim().toLowerCase(Locale.ROOT);
            if (animationId.isEmpty() || packId.isEmpty()) continue;
            normalizedPackSelections.put(animationId, packId);
        }
        data.animationPackSelections = normalizedPackSelections;
        LinkedHashMap<String, String> normalizedTypeSelections = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : data.animationPackTypeSelections.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
            String selectionId = entry.getKey().trim().toLowerCase(Locale.ROOT);
            String packType = entry.getValue().trim().toLowerCase(Locale.ROOT);
            if (selectionId.isEmpty() || packType.isEmpty() || !selectionId.contains("|")) continue;
            normalizedTypeSelections.put(selectionId, packType);
        }
        data.animationPackTypeSelections = normalizedTypeSelections;
        LinkedHashMap<String, Boolean> normalizedOverrides = new LinkedHashMap<String, Boolean>();
        for (Map.Entry<String, Boolean> entry : data.mixSpecificForceOverride.entrySet()) {
            if (entry == null || entry.getKey() == null) continue;
            String overrideId = entry.getKey().trim();
            if (overrideId.isEmpty() || !Boolean.TRUE.equals(entry.getValue())) continue;
            normalizedOverrides.put(overrideId, true);
        }
        data.mixSpecificForceOverride = normalizedOverrides;
        return data;
    }

    private static String findBlacklistMatch(class_1799 class_17992) {
        if (DATA != null && PunchyConfig.DATA.itemBlacklist != null && class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return null;
            }
            String string = class_29602.toString().toLowerCase(Locale.ROOT);
            String string2 = class_29602.method_12836().toLowerCase(Locale.ROOT);
            for (String string3 : PunchyConfig.DATA.itemBlacklist) {
                if (!PunchyConfig.matchesBlacklistEntry(string3, string, string2)) continue;
                return string3;
            }
            return null;
        }
        return null;
    }

    static {
        FILE_NAME = "punchy_config.json";
        GSON = new GsonBuilder().setPrettyPrinting().create();
        DATA = new Data();
    }

    public static final class Data {
        public boolean renderArmorArmsFP = true;
        public boolean interactiveStuffCompat = false;
        @SerializedName(value="bettercombatCompat", alternate={"BetterCombatCompat", "betterCombatCompat"})
        public boolean bettercombatCompat = false;
        @SerializedName(value="swordBlockingCompat", alternate={"SwordBlockingCompat", "swordblockingCompat"})
        public boolean swordBlockingCompat = false;
        public float animationSpeed = 5.5f;
        public boolean enableMod = true;
        public boolean enableTuning = false;
        public boolean disableResourcePackModelParts = false;
        public boolean disableArmPhysics = false;
        public boolean disableNativeItemPhysics = false;
        public boolean disableBoatMinecartRaftModels = false;
        public boolean disablePistonModels = false;
        public boolean disableChestModels = false;
        public boolean disableEnchantingTableModels = false;
        public boolean disableBoatFirstPersonAnimations = false;
        public boolean disableEnhancedFireArmEffects = false;
        public boolean enableCrawlAnimation = true;
        public boolean enableClimbAnimation = true;
        public boolean enableSwimAnimation = true;
        public boolean enableElytraFlightAnimation = true;
        public boolean enableCustomWalk = true;
        public boolean enableSprintArmSwing = true;
        public boolean enableFallArmAnimation = true;
        public float sprintArmSwingIntensity = 1.0f;
        public float sprintArmSwingSpeed = 0.0f;
        @SerializedName(value="enableFreezeShake", alternate={"disableFreezeShake"})
        public boolean enableFreezeShake = true;
        @SerializedName(value="enableFreezeArmOverlay", alternate={"disableFreezeArmOverlay"})
        public boolean enableFreezeArmOverlay = true;
        public boolean enableMud = true;
        public boolean enableSweat = true;
        public boolean enableBurn = true;
        public boolean enableParticles = true;
        public boolean enableModelPartsGlow = false;
        public boolean enableBlurGlowAtDark = false;
        public boolean firstPersonModelHideEnabled = true;
        public float firstPersonModelHidePitch = 60.0f;
        public List<String> itemBlacklist = new ArrayList<String>();
        public Map<String, Boolean> blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>();
        public Map<String, String> animationPackSelections = new LinkedHashMap<String, String>();
        public Map<String, String> animationPackTypeSelections = new LinkedHashMap<String, String>();
        public Map<String, Boolean> mixSpecificForceOverride = new LinkedHashMap<String, Boolean>();
    }
}
