package ru.method.celebression.manager;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_310;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.modules.impl.player.CosmeticsModule;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.manager.CrashLogTelegramManager;
import ru.method.celebression.modules.impl.render.DamageParticlesModule;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleDescriptor;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.modules.impl.render.ParticlesModule;
import ru.method.celebression.render.Point2f;
import ru.method.celebression.api.setting.PositionSetting;
import ru.method.celebression.server.ServerAddressUtils;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.ui.UiThemeController;
import ru.method.celebression.manager.UsageStatsManager;
import ru.method.celebression.manager.UserSyncManager;
import ru.method.celebression.modules.impl.render.ZoomModule;
import ru.method.celebression.modules.impl.client.UI;

public class ConfigManager {
    private static final int mB = 2;
    private static final long mC = 400L;
    private static final String mD = ".celebression";
    private static final String mE = ".prime";
    private static final String mF = ".theme";
    private String mG = "default";
    private final String mH;
    private boolean initialized = false;
    private int mI = 1;
    private final Object mJ = new Object();
    private final Object mK = new Object();
    private volatile long mL = 0L;
    private volatile boolean mM = false;
    private volatile boolean mN = false;
    private final Map<String, String> mO = new ConcurrentHashMap<String, String>();

    public ConfigManager() {
        this.mH = ".celebression";
        try {
            this.dR();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Celebression.LOGGER.info("[ConfigManager] Saving configs on shutdown...");
                this.dT();
            }));
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error during initialization:", (Throwable)exception);
        }
    }

    public void initialize() {
        if (!this.initialized) {
            Celebression.LOGGER.info("[ConfigManager] Loading configs...");
            for (HudElement object : Celebression.getInstance().getHudManager().fq()) {
                if (object.isToggled()) {
                    object.setToggled(false);
                }
                for (Setting<?> bl : object.getSettings()) {
                    bl.reset();
                }
            }
            try {
                boolean bl;
                this.ea();
                this.dY();
                String string = this.mG;
                File file = Celebression.getInstance().getConfigsDir();
                boolean bl2 = this.b(file, "default") != null;
                boolean bl3 = bl = this.b(file, string) != null;
                if (bl2) {
                    this.bi("default");
                    this.mG = string;
                } else if (bl) {
                    this.bi(string);
                } else {
                    this.dP();
                    this.eb();
                }
                Celebression.LOGGER.info("[ConfigManager] Configs loaded successfully");
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ConfigManager] Error loading configs:", (Throwable)exception);
            }
            this.initialized = true;
        }
    }

    public void dP() {
        Celebression.LOGGER.info("[ConfigManager] Initializing default HUD elements as disabled...");
        for (HudElement hudElement : Celebression.getInstance().getHudManager().fq()) {
            if (!hudElement.isToggled()) continue;
            hudElement.setToggled(false);
        }
        Celebression.LOGGER.info("[ConfigManager] Default HUD elements initialized (disabled)");
    }

    public String dQ() {
        return this.mG;
    }

    private void dR() {
        try {
            File file = Celebression.getInstance().getGlobalsDir();
            File file2 = Celebression.getInstance().getConfigsDir();
            File file3 = Celebression.getInstance().getThemesDir();
            if (!file.exists()) {
                file.mkdirs();
                Celebression.LOGGER.info("[ConfigManager] Created globals directory");
            }
            if (!file2.exists()) {
                file2.mkdirs();
                Celebression.LOGGER.info("[ConfigManager] Created configs directory");
            }
            if (!file3.exists()) {
                file3.mkdirs();
                Celebression.LOGGER.info("[ConfigManager] Created themes directory");
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error creating directories:", (Throwable)exception);
        }
    }

    private void dS() {
        try {
            this.ea();
            this.dY();
            this.bi(this.mG);
            Celebression.LOGGER.info("[ConfigManager] Configs loaded successfully");
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error loading configs:", (Throwable)exception);
        }
    }

    private void dT() {
        this.j(true);
    }

        private void j(boolean bl) {
        if (ru.method.celebression.service.UnhookService.isActive()) return;
        Object object = this.mK;
        synchronized (object) {
            try {
                this.l(bl);
                this.k(bl);
                this.eb();
                if (bl) {
                    Celebression.LOGGER.info("[ConfigManager] Session state saved (named config untouched \u2014 save explicitly)");
                }
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ConfigManager] Error saving configs:", (Throwable)exception);
            }
        }
    }

    public void dU() {
        if (ru.method.celebression.service.UnhookService.isActive()) return;
        this.ec();
    }

        public void dV() {
        Object object = this.mJ;
        synchronized (object) {
            this.mM = false;
            this.mL = System.currentTimeMillis();
        }
        this.j(true);
    }

    public void bh(String string) {
        this.a(string, true, true);
    }

    private void a(String string, boolean bl, boolean bl2) {
        String string2 = this.bl(string);
        try {
            this.a(string2, true, bl, bl2);
            if (!"default".equals(string2)) {
                this.eb();
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error saving config " + string2 + ":", (Throwable)exception);
            throw exception;
        }
    }

    public boolean bi(String string) {
        String string2 = this.bl(string);
        Celebression celebression = Celebression.getInstance();
        boolean bl = celebression != null && celebression.isSuppressToggleFeedback();
        boolean bl2 = celebression != null && celebression.isConfigLoading();
        boolean bl3 = false;
        if (celebression != null) {
            celebression.setSuppressToggleFeedback(true);
            celebression.setConfigLoading(true);
        }
        try {
            JsonObject jsonObject;
            String string3 = this.b(Celebression.getInstance().getConfigsDir(), string2);
            if (string3 == null) {
                Celebression.LOGGER.warn("[ConfigManager] Config file not found: " + string2 + ".celebression");
                boolean bl4 = false;
                return bl4;
            }
            Celebression.LOGGER.info("[ConfigManager] Loading config: " + string3);
            try (InputStream inputStream = Files.newInputStream(Paths.get(string3, new String[0]), new OpenOption[0]);
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
            }
            int n = this.mI = jsonObject.has("formatVersion") ? jsonObject.get("formatVersion").getAsInt() : 1;
            if (jsonObject.has("modules")) {
                this.h(jsonObject.get("modules").getAsJsonObject());
            }
            if (jsonObject.has("hudOrder") && jsonObject.get("hudOrder").isJsonArray()) {
                this.b(jsonObject.getAsJsonArray("hudOrder"));
            }
            if (jsonObject.has("linkGuiTheme")) {
                UiThemeController.co(jsonObject.get("linkGuiTheme").getAsString());
            }
            this.mG = string2;
            Celebression.LOGGER.info("[ConfigManager] Config loaded: " + string2);
            if (!"default".equals(string2)) {
                this.eb();
            }
            bl3 = true;
            boolean bl5 = true;
            return bl5;
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error loading config " + string2 + ":", (Throwable)exception);
            return false;
        }
        finally {
            if (celebression != null) {
                celebression.setSuppressToggleFeedback(bl);
                celebression.setConfigLoading(bl2);
            }
            if (bl3) {
                UiThemeController.pc();
                HudNotificationQueue.dG().bg("\u041a\u043e\u043d\u0444\u0438\u0433 \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d!");
            }
        }
    }

    public List<String> dW() {
        File[] fileArray;
        File file2 = Celebression.getInstance().getConfigsDir();
        File[] fileArray2 = fileArray = file2 == null ? null : file2.listFiles(file -> file.isFile() && (file.getName().toLowerCase(Locale.ROOT).endsWith(".celebression") || file.getName().toLowerCase(Locale.ROOT).endsWith(".prime")));
        if (fileArray == null || fileArray.length == 0) {
            return List.of();
        }
        TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (File file3 : fileArray) {
            String string = file3.getName();
            int n = string.lastIndexOf(46);
            if (n > 0) {
                string = string.substring(0, n);
            }
            if (string.isBlank()) continue;
            treeSet.add(string);
        }
        return new ArrayList<String>(treeSet);
    }

    public String bj(String string) {
        String string2;
        String string3;
        block15: {
            string3 = this.bl(string);
            String string4 = this.mO.get(string3);
            if (string4 != null) {
                return string4;
            }
            string2 = "";
            try {
                String string5 = this.b(Celebression.getInstance().getConfigsDir(), string3);
                if (string5 == null) break block15;
                try (InputStream inputStream = Files.newInputStream(Paths.get(string5, new String[0]), new OpenOption[0]);
                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                    JsonObject jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
                    if (jsonObject.has("author")) {
                        string2 = jsonObject.get("author").getAsString();
                    }
                }
            }
            catch (Exception exception) {
            }
        }
        this.mO.put(string3, string2);
        return string2;
    }

    private String dX() {
        class_310 class_3102 = class_310.method_1551();
        String string = "";
        if (class_3102 != null) {
            if (class_3102.field_1724 != null && class_3102.field_1724.method_7334() != null) {
                string = class_3102.field_1724.method_7334().getName();
            } else if (class_3102.method_1548() != null) {
                string = class_3102.method_1548().method_1676();
            }
        }
        if (string == null) {
            string = "";
        }
        return string.length() > 15 ? string.substring(0, 15) : string;
    }

    public boolean bk(String string) {
        String string2 = this.bl(string);
        if (string2.equalsIgnoreCase(this.mG) || "default".equalsIgnoreCase(string2)) {
            return false;
        }
        String string3 = this.b(Celebression.getInstance().getConfigsDir(), string2);
        if (string3 == null) {
            return false;
        }
        try {
            boolean bl = Files.deleteIfExists(Paths.get(string3, new String[0]));
            if (bl) {
                Celebression.LOGGER.info("[ConfigManager] Config removed: " + string2);
                this.mO.remove(string2);
            }
            return bl;
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error removing config " + string2 + ":", (Throwable)exception);
            return false;
        }
    }

    public String bl(String string) {
        String string2;
        String string3 = string2 = string == null ? "" : string.trim();
        if (string2.toLowerCase(Locale.ROOT).endsWith(".celebression")) {
            string2 = string2.substring(0, string2.length() - ".celebression".length());
        } else if (string2.toLowerCase(Locale.ROOT).endsWith(".prime")) {
            string2 = string2.substring(0, string2.length() - ".prime".length());
        }
        string2 = this.bn(string2);
        return string2.isBlank() ? this.mG : string2;
    }

    public String bm(String string) {
        String string2;
        String string3 = string2 = string == null ? "" : string.trim();
        if (string2.toLowerCase(Locale.ROOT).endsWith(".theme")) {
            string2 = string2.substring(0, string2.length() - ".theme".length());
        }
        return (string2 = this.bn(string2)).isBlank() ? "default" : string2;
    }

    private String bn(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (!(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '\u0410' && c <= '\u044f' || c == '\u0401' || c == '\u0451' || c >= '0' && c <= '9' || c == '_' || c == '-') && c != '.') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void af() {
        this.k(true);
    }

        private void k(boolean bl) {
        try {
            String string = String.valueOf(Celebression.getInstance().getGlobalsDir()) + "/macro.celebression";
            if (bl) {
                Celebression.LOGGER.info("[ConfigManager] Saving macros: " + string);
            }
            ServerAddressUtils.createEmptyFile(string);
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            Celebression.getInstance().getMacroManager().ev().forEach(moduleDescriptor -> {
                JsonObject macroJson = new JsonObject();
                macroJson.add("name", (JsonElement)new JsonPrimitive(moduleDescriptor.getName()));
                macroJson.add("command", (JsonElement)new JsonPrimitive(moduleDescriptor.t()));
                macroJson.add("key", (JsonElement)new JsonPrimitive((Number)moduleDescriptor.getBind().c()));
                macroJson.add("mouse", (JsonElement)new JsonPrimitive(Boolean.valueOf(moduleDescriptor.getBind().jW())));
                jsonArray.add((JsonElement)macroJson);
            });
            jsonObject.add("macros", (JsonElement)jsonArray);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(string), StandardCharsets.UTF_8);
            try {
                outputStreamWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)jsonObject.toString())));
                if (bl) {
                    Celebression.LOGGER.info("[ConfigManager] Macros saved successfully");
                }
            }
            finally {
                if (Collections.singletonList(outputStreamWriter).get(0) != null) {
                    outputStreamWriter.close();
                }
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error saving macros:", (Throwable)exception);
        }
    }

    public void dY() {
        try {
            JsonObject jsonObject;
            String string = this.b(Celebression.getInstance().getGlobalsDir(), "macro");
            if (string == null) {
                Celebression.LOGGER.warn("[ConfigManager] Macros file not found: macro.celebression");
                return;
            }
            Celebression.LOGGER.info("[ConfigManager] Loading macros: " + string);
            Celebression.getInstance().getMacroManager().clear();
            try (InputStream inputStream = Files.newInputStream(Paths.get(string, new String[0]), new OpenOption[0]);
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
            }
            if (jsonObject.has("macros")) {
                for (JsonElement macroElement : jsonObject.get("macros").getAsJsonArray()) {
                    JsonObject jsonObject2 = macroElement.getAsJsonObject();
                    String string2 = jsonObject2.get("name").getAsString();
                    String string3 = jsonObject2.get("command").getAsString();
                    int n = jsonObject2.get("key").getAsInt();
                    boolean bl = jsonObject2.has("mouse") ? jsonObject2.get("mouse").getAsBoolean() : false;
                    KeyBind keyBind = new KeyBind(n, bl);
                    Celebression.getInstance().getMacroManager().a(new ModuleDescriptor(string2, string3, keyBind));
                }
            }
            Celebression.LOGGER.info("[ConfigManager] Macros loaded successfully");
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error loading macros:", (Throwable)exception);
        }
    }

    public void dZ() {
        this.l(true);
    }

        private void l(boolean bl) {
        try {
            CosmeticsService cosmeticsService;
            UsageStatsManager usageStatsManager;
            CrashLogTelegramManager crashLogTelegramManager;
            String string = String.valueOf(Celebression.getInstance().getGlobalsDir()) + "/globals.celebression";
            if (bl) {
                Celebression.LOGGER.info("[ConfigManager] Saving globals: " + string);
            }
            ServerAddressUtils.createEmptyFile(string);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("config", (JsonElement)new JsonPrimitive(this.mG));
            JsonArray jsonArray = new JsonArray();
            Celebression.getInstance().getFriendManager().es().forEach(arg_0 -> ((JsonArray)jsonArray).add(arg_0));
            jsonObject.add("friends", (JsonElement)jsonArray);
            UserSyncManager userSyncManager = Celebression.getInstance().getUserSyncManager();
            if (userSyncManager != null) {
                JsonObject syncJson = new JsonObject();
                syncJson.addProperty("enabled", userSyncManager.isEnabled());
                syncJson.addProperty("url", userSyncManager.eB());
                syncJson.addProperty("apiKey", userSyncManager.eT());
                jsonObject.add("sync", syncJson);
            }
            if ((crashLogTelegramManager = Celebression.getInstance().getCrashLogTelegramManager()) != null) {
                JsonObject crashTelegramJson = new JsonObject();
                crashTelegramJson.addProperty("enabled", crashLogTelegramManager.isEnabled());
                crashTelegramJson.addProperty("botToken", crashLogTelegramManager.em());
                crashTelegramJson.addProperty("chatId", crashLogTelegramManager.en());
                jsonObject.add("crashTelegram", crashTelegramJson);
            }
            if ((usageStatsManager = Celebression.getInstance().getUsageStatsManager()) != null) {
                JsonObject usageStatsJson = new JsonObject();
                usageStatsJson.addProperty("enabled", usageStatsManager.isEnabled());
                usageStatsJson.addProperty("endpoint", usageStatsManager.eB());
                jsonObject.add("usageStats", usageStatsJson);
            }
            if ((cosmeticsService = Celebression.getInstance().getCosmeticsService()) != null) {
                jsonObject.add("cosmetics", (JsonElement)cosmeticsService.bA().aN());
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(string), StandardCharsets.UTF_8);
            try {
                outputStreamWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)jsonObject.toString())));
                if (bl) {
                    Celebression.LOGGER.info("[ConfigManager] Globals saved successfully");
                }
            }
            finally {
                if (Collections.singletonList(outputStreamWriter).get(0) != null) {
                    outputStreamWriter.close();
                }
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error saving globals:", (Throwable)exception);
        }
    }

    public void ea() {
        try {
            CosmeticsService cosmeticsService;
            UsageStatsManager usageStatsManager;
            JsonObject jsonObject;
            CrashLogTelegramManager crashLogTelegramManager;
            String string;
            String string2;
            JsonObject jsonObject2;
            InputStreamReader inputStreamReader;
            String string3 = this.b(Celebression.getInstance().getGlobalsDir(), "globals");
            if (string3 == null) {
                Celebression.LOGGER.warn("[ConfigManager] Globals file not found: globals.celebression");
                return;
            }
            Celebression.LOGGER.info("[ConfigManager] Loading globals: " + string3);
            try (InputStream inputStream = Files.newInputStream(Paths.get(string3, new String[0]), new OpenOption[0]);){
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                try {
                    jsonObject2 = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
                }
                finally {
                    inputStreamReader.close();
                }
            }
            if (jsonObject2.has("config")) {
                this.mG = jsonObject2.get("config").getAsString();
            }
            if (jsonObject2.has("friends")) {
                for (JsonElement friendElement : jsonObject2.getAsJsonArray("friends")) {
                    if (Celebression.getInstance().getFriendManager().av(friendElement.getAsString())) continue;
                    Celebression.getInstance().getFriendManager().bt(friendElement.getAsString());
                }
            }
            UserSyncManager userSyncManager = Celebression.getInstance().getUserSyncManager();
            if (userSyncManager != null) {
                boolean bl = userSyncManager.isEnabled();
                String string4 = userSyncManager.eB();
                string2 = userSyncManager.eT();
                if (jsonObject2.has("sync") && jsonObject2.get("sync").isJsonObject()) {
                    JsonObject syncJson = jsonObject2.getAsJsonObject("sync");
                    if (syncJson.has("enabled")) {
                        bl = syncJson.get("enabled").getAsBoolean();
                    }
                    if (syncJson.has("url")) {
                        string4 = syncJson.get("url").getAsString();
                    }
                    if (syncJson.has("apiKey")) {
                        string2 = syncJson.get("apiKey").getAsString();
                    }
                }
                userSyncManager.a(bl, string4, string2);
            }
            if ((crashLogTelegramManager = Celebression.getInstance().getCrashLogTelegramManager()) != null) {
                boolean bl = crashLogTelegramManager.isEnabled();
                string2 = crashLogTelegramManager.em();
                string = crashLogTelegramManager.en();
                if (jsonObject2.has("crashTelegram") && jsonObject2.get("crashTelegram").isJsonObject()) {
                    jsonObject = jsonObject2.getAsJsonObject("crashTelegram");
                    if (jsonObject.has("enabled")) {
                        bl = jsonObject.get("enabled").getAsBoolean();
                    }
                    if (jsonObject.has("botToken")) {
                        string2 = jsonObject.get("botToken").getAsString();
                    }
                    if (jsonObject.has("chatId")) {
                        string = jsonObject.get("chatId").getAsString();
                    }
                }
                crashLogTelegramManager.a(bl, string2, string);
            }
            if ((usageStatsManager = Celebression.getInstance().getUsageStatsManager()) != null) {
                boolean bl = usageStatsManager.isEnabled();
                string = usageStatsManager.eB();
                if (jsonObject2.has("usageStats") && jsonObject2.get("usageStats").isJsonObject()) {
                    jsonObject = jsonObject2.getAsJsonObject("usageStats");
                    if (jsonObject.has("enabled")) {
                        bl = jsonObject.get("enabled").getAsBoolean();
                    }
                    if (jsonObject.has("endpoint")) {
                        string = jsonObject.get("endpoint").getAsString();
                    }
                }
                usageStatsManager.a(bl, string);
            }
            if ((cosmeticsService = Celebression.getInstance().getCosmeticsService()) != null && jsonObject2.has("cosmetics")) {
                cosmeticsService.bA().c(jsonObject2.get("cosmetics"));
            }
            Celebression.LOGGER.info("[ConfigManager] Globals loaded successfully");
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error loading globals:", (Throwable)exception);
        }
    }

    public void bo(String string) {
        UI uI;
        String string2 = this.bm(string);
        File file = Celebression.getInstance().getThemesDir();
        if (!file.exists()) {
            file.mkdirs();
        }
        if ((uI = Celebression.getInstance().getModuleManager().getModule(UI.class)) == null) {
            throw new IllegalStateException("UI module is not available");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("theme", string2);
        jsonObject.addProperty("formatVersion", (Number)1);
        JsonObject jsonObject2 = new JsonObject();
        for (Setting<?> setting2 : uI.getSettings()) {
            JsonElement jsonElement = this.a(setting2);
            if (jsonElement == null) continue;
            jsonObject2.add(setting2.getName(), jsonElement);
        }
        jsonObject.add("settings", (JsonElement)jsonObject2);
        File file2 = new File(file, string2 + ".theme");
        ServerAddressUtils.createEmptyFile(file2.getAbsolutePath());
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file2), StandardCharsets.UTF_8)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonObject));
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error saving theme " + string2 + ":", exception);
        }
    }

    public boolean bp(String string) {
        JsonObject jsonObject;
        String string2 = this.bm(string);
        File file = this.br(string2);
        if (file == null || !file.exists()) {
            return false;
        }
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI == null) {
            throw new IllegalStateException("UI module is not available");
        }
        try (InputStream inputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error loading theme " + string2 + ":", exception);
            return false;
        }
        if (!jsonObject.has("settings") || !jsonObject.get("settings").isJsonObject()) {
            return false;
        }
        JsonObject settingsJson = jsonObject.getAsJsonObject("settings");
        for (Setting setting : uI.getSettings()) {
            if (!settingsJson.has(setting.getName())) continue;
            this.a(setting, settingsJson.get(setting.getName()));
        }
        this.dU();
        return true;
    }

    public boolean bq(String string) {
        String string2 = this.bm(string);
        File file = this.br(string2);
        if (file == null) {
            return false;
        }
        try {
            return Files.deleteIfExists(file.toPath());
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error deleting theme " + string2 + ":", exception);
            return false;
        }
    }

    public File[] ai() {
        File file2 = Celebression.getInstance().getThemesDir();
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.listFiles((file, string) -> string.toLowerCase(Locale.ROOT).endsWith(".theme"));
    }

    public File br(String string) {
        File file = Celebression.getInstance().getThemesDir();
        File file2 = new File(file, this.bm(string) + ".theme");
        return file2.exists() ? file2 : null;
    }

    private void eb() {
        try {
            this.a("default", false, false, false);
            Celebression.LOGGER.info("[ConfigManager] Synced default config with the last used config");
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Failed to sync default config:", (Throwable)exception);
        }
    }

        private void a(String string, boolean bl, boolean bl2, boolean bl3) {
        String string2 = String.valueOf(Celebression.getInstance().getConfigsDir()) + "/" + string + ".celebression";
        if (bl2) {
            Celebression.LOGGER.info("[ConfigManager] Saving config: " + string2);
        }
        String string3 = bl3 ? this.dX() : this.bj(string);
        ServerAddressUtils.createEmptyFile(string2);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("config", (JsonElement)new JsonPrimitive(string));
        jsonObject.add("formatVersion", (JsonElement)new JsonPrimitive((Number)2));
        if (string3 != null && !string3.isBlank()) {
            jsonObject.addProperty("author", string3);
        }
        jsonObject.addProperty("linkGuiTheme", UiThemeController.oX().getName());
        jsonObject.add("modules", (JsonElement)this.ee());
        jsonObject.add("hudOrder", (JsonElement)this.ef());
        this.mO.put(string, string3 == null ? "" : string3);
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(string2), StandardCharsets.UTF_8)) {
            outputStreamWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)jsonObject.toString())));
            if (bl) {
                this.mG = string;
            }
            if (bl2) {
                Celebression.LOGGER.info("[ConfigManager] Config saved: " + string);
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error saving config " + string + ":", exception);
        }
    }

    private String b(File file, String string) {
        String string2 = this.bl(string);
        String string3 = String.valueOf(file) + "/" + string2 + ".celebression";
        if (ServerAddressUtils.exists(string3)) {
            return string3;
        }
        String string4 = String.valueOf(file) + "/" + string2 + ".prime";
        if (ServerAddressUtils.exists(string4)) {
            return string4;
        }
        return null;
    }

        private void ec() {
        boolean bl = false;
        Object object = this.mJ;
        synchronized (object) {
            long l = System.currentTimeMillis();
            long l2 = l - this.mL;
            if (!this.mN && l2 >= 400L) {
                this.mL = l;
                bl = true;
            } else {
                this.mM = true;
                if (!this.mN) {
                    this.mN = true;
                    long l3 = Math.max(0L, 400L - l2);
                    this.d(l3);
                }
            }
        }
        if (bl) {
            this.ed();
        }
    }

    private void d(long l) {
        Thread thread = new Thread(() -> {
            long l2 = l;
            while (true) {
                if (l2 > 0L) {
                    try {
                        Thread.sleep(l2);
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        Object object = this.mJ;
                        synchronized (object) {
                            this.mN = false;
                        }
                        return;
                    }
                }
                Object object = this.mJ;
                synchronized (object) {
                    if (!this.mM) {
                        this.mN = false;
                        return;
                    }
                    this.mM = false;
                    this.mL = System.currentTimeMillis();
                }
                this.ed();
                object = this.mJ;
                synchronized (object) {
                    if (!this.mM) {
                        this.mN = false;
                        return;
                    }
                    long l3 = System.currentTimeMillis();
                    l2 = Math.max(0L, 400L - (l3 - this.mL));
                }
            }
        }, "Celebression-ConfigAutoSave");
        thread.setDaemon(true);
        thread.start();
    }

    private void ed() {
        try {
            this.j(false);
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[ConfigManager] Error during auto-save:", (Throwable)exception);
        }
    }

    private boolean a(Module module, Setting<?> setting) {
        return !(module instanceof UI) || !(setting instanceof ColorSetting);
    }

    private void b(Module module, Setting<?> setting) {
        if (this.a(module, setting)) {
            setting.reset();
        }
    }

    private JsonObject ee() {
        MultiSelectSetting multiSelectSetting;
        Setting setting;
        Iterator iterator;
        Color color;
        Setting setting2;
        Setting setting3;
        JsonObject jsonObject;
        JsonObject jsonObject2;
        JsonObject jsonObject3 = new JsonObject();
        for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
            jsonObject2 = new JsonObject();
            jsonObject2.add("toggled", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isToggled())));
            jsonObject2.add("bind", (JsonElement)new JsonPrimitive(module.getBind().c() + ", " + module.getBind().jW()));
            jsonObject = new JsonObject();
            for (Setting<?> setting4 : module.getSettings()) {
                if (!this.a(module, setting4)) continue;
                if (setting4 instanceof BooleanSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((Boolean)setting4.jT()));
                    continue;
                }
                if (setting4 instanceof SliderSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((Number)((Float)setting4.jT())));
                    continue;
                }
                if (setting4 instanceof StringSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((String)setting4.jT()));
                    continue;
                }
                if (setting4 instanceof EnumSetting) {
                    setting3 = (EnumSetting)setting4;
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(((NamedOption)setting3.jT()).getName()));
                    continue;
                }
                if (setting4 instanceof ColorSetting) {
                    setting2 = (ColorSetting)setting4;
                    color = (Color)setting2.jT();
                    String colorValue = color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha();
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(colorValue));
                    continue;
                }
                if (setting4 instanceof BindSetting) {
                    setting = (BindSetting)setting4;
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(((KeyBind)setting.jT()).c() + ", " + ((KeyBind)setting.jT()).jW()));
                    continue;
                }
                if (!(setting4 instanceof MultiSelectSetting)) continue;
                multiSelectSetting = (MultiSelectSetting)setting4;
                JsonObject multiSelectJson = new JsonObject();
                for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                    multiSelectJson.add(booleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)booleanSetting.jT()));
                }
                jsonObject.add(multiSelectSetting.getName(), multiSelectJson);
            }
            jsonObject2.add("settings", (JsonElement)jsonObject);
            jsonObject3.add(module.getName(), (JsonElement)jsonObject2);
        }
        for (HudElement hudElement : Celebression.getInstance().getHudManager().fq()) {
            jsonObject2 = new JsonObject();
            jsonObject2.add("toggled", (JsonElement)new JsonPrimitive(Boolean.valueOf(hudElement.isToggled())));
            jsonObject = new JsonObject();
            for (Setting<?> setting4 : hudElement.getSettings()) {
                if (setting4 instanceof BooleanSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((Boolean)setting4.jT()));
                    continue;
                }
                if (setting4 instanceof PositionSetting) {
                    setting3 = (PositionSetting)setting4;
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(((Point2f)setting3.jT()).getX() + ", " + ((Point2f)setting3.jT()).getY()));
                    continue;
                }
                if (setting4 instanceof SliderSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((Number)((Float)setting4.jT())));
                    continue;
                }
                if (setting4 instanceof StringSetting) {
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive((String)setting4.jT()));
                    continue;
                }
                if (setting4 instanceof EnumSetting) {
                    setting2 = (EnumSetting)setting4;
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(((NamedOption)setting2.jT()).getName()));
                    continue;
                }
                if (setting4 instanceof ColorSetting) {
                    setting = (ColorSetting)setting4;
                    color = (Color)setting.jT();
                    String colorValue = color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha();
                    jsonObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(colorValue));
                    continue;
                }
                if (!(setting4 instanceof MultiSelectSetting)) continue;
                multiSelectSetting = (MultiSelectSetting)setting4;
                JsonObject multiSelectJson = new JsonObject();
                for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                    multiSelectJson.add(booleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)booleanSetting.jT()));
                }
                jsonObject.add(multiSelectSetting.getName(), multiSelectJson);
            }
            jsonObject2.add("settings", (JsonElement)jsonObject);
            jsonObject3.add("hud_" + hudElement.getName(), (JsonElement)jsonObject2);
        }
        return jsonObject3;
    }

    private JsonArray ef() {
        JsonArray jsonArray = new JsonArray();
        for (HudElement hudElement : Celebression.getInstance().getHudManager().fq()) {
            jsonArray.add(hudElement.getName());
        }
        return jsonArray;
    }

    private void b(JsonArray jsonArray) {
        if (jsonArray == null || Celebression.getInstance().getHudManager() == null) {
            return;
        }
        List<HudElement> list = Celebression.getInstance().getHudManager().fq();
        ArrayList<HudElement> arrayList = new ArrayList<HudElement>();
        block0: for (JsonElement object : jsonArray) {
            String string = object.getAsString();
            for (HudElement hudElement : list) {
                if (!hudElement.getName().equalsIgnoreCase(string) || arrayList.contains(hudElement)) continue;
                arrayList.add(hudElement);
                continue block0;
            }
        }
        for (HudElement hudElement : list) {
            if (arrayList.contains(hudElement)) continue;
            arrayList.add(hudElement);
        }
        list.clear();
        list.addAll(arrayList);
    }

    private JsonElement a(Setting<?> setting) {
        if (setting instanceof BooleanSetting) {
            return new JsonPrimitive((Boolean)setting.jT());
        }
        if (setting instanceof SliderSetting) {
            return new JsonPrimitive((Number)((Float)setting.jT()));
        }
        if (setting instanceof StringSetting) {
            return new JsonPrimitive((String)setting.jT());
        }
        if (setting instanceof EnumSetting) {
            EnumSetting enumSetting = (EnumSetting)setting;
            return new JsonPrimitive(((NamedOption)enumSetting.jT()).getName());
        }
        if (setting instanceof ColorSetting) {
            ColorSetting colorSetting = (ColorSetting)setting;
            Color color = (Color)colorSetting.jT();
            return new JsonPrimitive(color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha());
        }
        if (setting instanceof BindSetting) {
            BindSetting bindSetting = (BindSetting)setting;
            return new JsonPrimitive(((KeyBind)bindSetting.jT()).c() + ", " + ((KeyBind)bindSetting.jT()).jW());
        }
        if (setting instanceof PositionSetting) {
            PositionSetting positionSetting = (PositionSetting)setting;
            return new JsonPrimitive(((Point2f)positionSetting.jT()).getX() + ", " + ((Point2f)positionSetting.jT()).getY());
        }
        if (setting instanceof MultiSelectSetting) {
            MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting;
            JsonObject jsonObject = new JsonObject();
            for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                jsonObject.add(booleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)booleanSetting.jT()));
            }
            return jsonObject;
        }
        return null;
    }

    private void a(Setting<?> setting, JsonElement jsonElement) {
        try {
            if (setting instanceof BooleanSetting) {
                BooleanSetting booleanSetting = (BooleanSetting)setting;
                booleanSetting.a(jsonElement.getAsBoolean());
            } else if (setting instanceof SliderSetting) {
                SliderSetting sliderSetting = (SliderSetting)setting;
                sliderSetting.a(Float.valueOf(jsonElement.getAsFloat()));
            } else if (setting instanceof StringSetting) {
                StringSetting stringSetting = (StringSetting)setting;
                stringSetting.bI(jsonElement.getAsString());
            } else if (setting instanceof EnumSetting) {
                EnumSetting enumSetting = (EnumSetting)setting;
                enumSetting.bS(jsonElement.getAsString());
            } else if (setting instanceof ColorSetting) {
                ColorSetting colorSetting = (ColorSetting)setting;
                String[] stringArray = jsonElement.getAsString().split(", ");
                if (stringArray.length >= 3) {
                    int n = Integer.parseInt(stringArray[0]);
                    int n2 = Integer.parseInt(stringArray[1]);
                    int n3 = Integer.parseInt(stringArray[2]);
                    int n4 = stringArray.length > 3 ? Integer.parseInt(stringArray[3]) : 255;
                    colorSetting.d(new Color(n, n2, n3, n4));
                }
            } else if (setting instanceof BindSetting) {
                BindSetting bindSetting = (BindSetting)setting;
                String[] stringArray = jsonElement.getAsString().split(", ");
                if (stringArray.length == 2) {
                    bindSetting.a(new KeyBind(Integer.parseInt(stringArray[0]), Boolean.parseBoolean(stringArray[1])));
                }
            } else if (setting instanceof PositionSetting) {
                PositionSetting positionSetting = (PositionSetting)setting;
                String[] stringArray = jsonElement.getAsString().split(", ");
                if (stringArray.length == 2) {
                    positionSetting.a(new Point2f(Float.parseFloat(stringArray[0]), Float.parseFloat(stringArray[1])));
                }
            } else if (setting instanceof MultiSelectSetting) {
                MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting;
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                    if (!jsonObject.has(booleanSetting.getName())) continue;
                    booleanSetting.a(jsonObject.get(booleanSetting.getName()).getAsBoolean());
                }
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[ConfigManager] Failed to load setting {}", (Object)setting.getName(), (Object)exception);
        }
    }

    private void h(JsonObject jsonObject) {
        for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
            JsonObject jsonObject2;
            boolean bl;
            JsonObject moduleObject = this.a(jsonObject, module);
            if (moduleObject == null) {
                if (!(module instanceof UI)) {
                    module.setToggled(module instanceof CosmeticsModule);
                } else {
                    ((UI)module).ensureDefaultBind();
                }
                for (Setting setting2 : module.getSettings()) {
                    this.b(module, setting2);
                }
                continue;
            }
            bl = moduleObject.has("toggled") ? moduleObject.get("toggled").getAsBoolean() : module instanceof CosmeticsModule;
            if (module instanceof DamageParticlesModule && this.j(jsonObject2 = this.d(jsonObject, "Particles"))) {
                bl = bl || jsonObject2.has("toggled") && jsonObject2.get("toggled").getAsBoolean();
            }
            if (!(module instanceof UI)) {
                module.setToggled(bl);
            }
            if (moduleObject.has("bind")) {
                try {
                    String[] stringArray2 = moduleObject.get("bind").getAsString().split("\\s*,\\s*");
                    if (stringArray2.length == 2) {
                        int n2 = Integer.parseInt(stringArray2[0]);
                        boolean bl4 = Boolean.parseBoolean(stringArray2[1]);
                        module.setBind(new KeyBind(n2, bl4));
                    }
                }
                catch (Exception exception) {
                    Celebression.LOGGER.warn("[ConfigManager] Failed to load bind for module {}", (Object)module.getName(), (Object)exception);
                }
            }
            if (module instanceof UI) {
                UI uI = (UI)module;
                uI.ensureDefaultBind();
            }
            JsonObject jsonObject3 = this.i(moduleObject);
            JsonObject legacySettings = this.b(jsonObject, module);
            if (jsonObject3 == null && legacySettings == null) {
                for (Setting<?> setting3 : module.getSettings()) {
                    this.b(module, setting3);
                }
                continue;
            }
            for (Setting<?> setting4 : module.getSettings()) {
                if (!this.a(module, setting4)) continue;
                JsonElement value = this.a(jsonObject3, legacySettings, setting4.getName());
                if (value == null) {
                    setting4.reset();
                    continue;
                }
                try {
                    if (setting4 instanceof BooleanSetting) {
                        ((BooleanSetting)setting4).a(value.getAsBoolean());
                        continue;
                    }
                    if (setting4 instanceof SliderSetting) {
                        SliderSetting sliderSetting = (SliderSetting)setting4;
                        float f = value.getAsFloat();
                        if (this.mI < 2 && module instanceof ZoomModule) {
                            if ("settings.zoom.multiplier".equals(setting4.getName())) {
                                f = this.clampFloat(1.05f - f, 0.05f, 1.0f);
                            } else if ("settings.zoom.smooth_speed".equals(setting4.getName())) {
                                f = this.clampFloat(1.01f - f, 0.01f, 1.0f);
                            }
                        }
                        sliderSetting.a(Float.valueOf(f));
                        continue;
                    }
                    if (setting4 instanceof EnumSetting) {
                        ((EnumSetting)setting4).bS(value.getAsString());
                        continue;
                    }
                    if (setting4 instanceof ColorSetting) {
                        ColorSetting colorSetting = (ColorSetting)setting4;
                        String[] stringArray3 = value.getAsString().split(", ");
                        if (stringArray3.length < 3) continue;
                        int n3 = Integer.parseInt(stringArray3[0]);
                        int n = Integer.parseInt(stringArray3[1]);
                        int n4 = Integer.parseInt(stringArray3[2]);
                        int n5 = stringArray3.length > 3 ? Integer.parseInt(stringArray3[3]) : 255;
                        colorSetting.d(new Color(n3, n, n4, n5));
                        continue;
                    }
                    if (setting4 instanceof StringSetting) {
                        ((StringSetting)setting4).bI(value.getAsString());
                        continue;
                    }
                    if (setting4 instanceof BindSetting) {
                        String[] stringArray4 = value.getAsString().split(", ");
                        if (stringArray4.length != 2) continue;
                        int n6 = Integer.parseInt(stringArray4[0]);
                        boolean mouse = Boolean.parseBoolean(stringArray4[1]);
                        ((BindSetting)setting4).a(new KeyBind(n6, mouse));
                        continue;
                    }
                    if (!(setting4 instanceof MultiSelectSetting)) continue;
                    MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting4;
                    JsonObject jsonObject4 = value.getAsJsonObject();
                    for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                        if (!jsonObject4.has(booleanSetting.getName())) continue;
                        booleanSetting.a(jsonObject4.get(booleanSetting.getName()).getAsBoolean());
                    }
                }
                catch (Exception exception) {
                    Celebression.LOGGER.warn("[ConfigManager] Failed to load setting {} for module {}", (Object)setting4.getName(), (Object)module.getName(), (Object)exception);
                    setting4.reset();
                }
            }
        }
        for (HudElement hudElement : Celebression.getInstance().getHudManager().fq()) {
            String hudKey = "hud_" + hudElement.getName();
            if (!jsonObject.has(hudKey)) {
                hudElement.setToggled(false);
                for (Setting setting5 : hudElement.getSettings()) {
                    setting5.reset();
                }
                continue;
            }
            JsonObject jsonObject5 = jsonObject.get(hudKey).getAsJsonObject();
            hudElement.setToggled(jsonObject5.has("toggled") && jsonObject5.get("toggled").getAsBoolean());
            if (!jsonObject5.has("settings")) {
                for (Setting<?> setting : hudElement.getSettings()) {
                    setting.reset();
                }
                continue;
            }
            JsonObject jsonObject6 = jsonObject5.get("settings").getAsJsonObject();
            for (Setting<?> setting6 : hudElement.getSettings()) {
                if (!jsonObject6.has(setting6.getName())) {
                    setting6.reset();
                    continue;
                }
                JsonElement jsonElement = jsonObject6.get(setting6.getName());
                this.a(setting6, jsonElement);
            }
        }
    }

    private JsonObject a(JsonObject jsonObject, Module module) {
        if (module instanceof DamageParticlesModule) {
            JsonObject jsonObject2 = this.d(jsonObject, "DamageParticles");
            if (jsonObject2 != null) {
                return jsonObject2;
            }
            JsonObject jsonObject3 = this.d(jsonObject, "Particles");
            return this.j(jsonObject3) ? jsonObject3 : null;
        }
        if (module instanceof ParticlesModule) {
            return this.d(jsonObject, "Particles");
        }
        return this.d(jsonObject, module.getName());
    }

    private JsonObject b(JsonObject jsonObject, Module module) {
        if (module instanceof DamageParticlesModule) {
            JsonObject jsonObject2 = this.d(jsonObject, "Particles");
            return this.j(jsonObject2) ? this.i(jsonObject2) : null;
        }
        if (module instanceof ParticlesModule) {
            return null;
        }
        return null;
    }

    private JsonObject d(JsonObject jsonObject, String string) {
        if (jsonObject == null || string == null || !jsonObject.has(string)) {
            return null;
        }
        return jsonObject.get(string).getAsJsonObject();
    }

    private JsonObject i(JsonObject jsonObject) {
        if (jsonObject == null || !jsonObject.has("settings")) {
            return null;
        }
        return jsonObject.get("settings").getAsJsonObject();
    }

    private JsonElement a(JsonObject jsonObject, JsonObject jsonObject2, String string) {
        if (jsonObject != null && jsonObject.has(string)) {
            return jsonObject.get(string);
        }
        if (jsonObject2 != null && jsonObject2.has(string)) {
            return jsonObject2.get(string);
        }
        return null;
    }

    private boolean j(JsonObject jsonObject) {
        JsonObject jsonObject2 = this.i(jsonObject);
        return jsonObject2 != null && jsonObject2.has("settings.damageparticles.count");
    }

    private float clampFloat(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }
}
