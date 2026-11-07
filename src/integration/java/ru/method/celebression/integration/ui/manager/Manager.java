package ru.method.celebression.integration.ui.manager;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import ru.method.celebression.Celebression;
import ru.method.celebression.integration.ui.modules.TypeList;
import ru.method.celebression.integration.ui.modules.api.Module;
import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.manager.themeManager.StyleManager;
import ru.method.celebression.integration.ui.screens.dropdown.ClickGUI;

/** Shared UI services backed by the visual client's modules and config directory. */
public final class Manager {
    public static final StyleManager STYLE_MANAGER = new StyleManager();
    public static final Functions FUNCTION_MANAGER = new Functions();
    public static final Accounts ACCOUNT_MANAGER = new Accounts();
    public static final Config CONFIG_MANAGER = new Config();
    private static boolean initialized;
    public static synchronized void initialize() {
        if (initialized) return;
        new FontUtils().init();
        STYLE_MANAGER.init();
        ACCOUNT_MANAGER.load();
        CONFIG_MANAGER.load();
        initialized = true;
    }
    private static Path directory() { return Celebression.getInstance().getGlobalsDir().toPath(); }
    public static final class Functions {
        private final Map<ru.method.celebression.module.Module, Module> views = new IdentityHashMap<>();
        public Module clickGUI;
        public List<Module> getFunctions() {
            var result = new ArrayList<Module>();
            for (var source : Celebression.getInstance().getModuleManager().getModules()) {
                var view = views.computeIfAbsent(source, Module::new);
                view.refresh();
                if (source instanceof ru.method.celebression.modules.impl.client.UI) clickGUI = view;
                result.add(view);
            }
            return result;
        }
        public List<Module> getFunctions(TypeList type) {
            return getFunctions().stream().filter(view -> view.category == type).toList();
        }
    }
    public static final class Accounts {
        private final List<String> accounts = new ArrayList<>();
        private String lastSelected = "";
        public List<String> getAccounts() { return accounts; }
        public void addAccount(String name) {
            if (name == null || name.isBlank()) return;
            if (!accounts.contains(name)) accounts.add(name);
            save();
        }
        public void removeAccount(String name) { accounts.remove(name); save(); }
        public void clearAll() { accounts.clear(); lastSelected = ""; save(); }
        public void setLastSelectedAccount(String name) { lastSelected = name; save(); }
        public String getLastSelectedAccount() { return lastSelected; }
        public void load() {
            Path file = directory().resolve("offline-accounts.json");
            if (!Files.isRegularFile(file)) return;
            try {
                var object = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
                accounts.clear();
                for (var value : object.getAsJsonArray("accounts")) accounts.add(value.getAsString());
                if (object.has("selected")) lastSelected = object.get("selected").getAsString();
            } catch (Exception e) { Celebression.LOGGER.warn("Cannot read offline account list", e); }
        }
        public void save() {
            try {
                Files.createDirectories(directory());
                var object = new JsonObject();
                var array = new JsonArray(); accounts.forEach(array::add);
                object.add("accounts", array); object.addProperty("selected", lastSelected);
                writeAtomically(directory().resolve("offline-accounts.json"), object);
            } catch (Exception e) { Celebression.LOGGER.warn("Cannot save offline account list", e); }
        }
    }
    public static final class Config {
        public void saveConfiguration(String ignoredName) {
            try {
                Files.createDirectories(directory());
                var root = new JsonObject();
                root.add("colors", STYLE_MANAGER.saveColors());
                root.addProperty("tab", ClickGUI.getPersistedTabName());
                root.add("scroll", ClickGUI.getPersistedScrollJson());
                root.addProperty("language", ClickGUI.getPersistedGuiLanguageName());
                writeAtomically(directory().resolve("ui.json"), root);
                Celebression.getInstance().getConfigManager().dU();
            } catch (Exception e) { Celebression.LOGGER.warn("Cannot save UI configuration", e); }
        }
        void load() {
            Path file = directory().resolve("ui.json");
            if (!Files.isRegularFile(file)) return;
            try {
                var root = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
                if (root.has("colors")) STYLE_MANAGER.loadColors(root.getAsJsonObject("colors"));
                if (root.has("tab")) ClickGUI.setPersistedTabFromName(root.get("tab").getAsString());
                if (root.has("scroll")) ClickGUI.loadPersistedScrollFromJson(root.getAsJsonObject("scroll"));
                if (root.has("language")) ru.method.celebression.integration.ui.screens.mainmenu.MenuLanguageState.load(root.get("language").getAsString());
            } catch (Exception e) { Celebression.LOGGER.warn("Cannot read UI configuration", e); }
        }
    }
    private static void writeAtomically(Path file, JsonObject object) throws java.io.IOException {
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        Files.writeString(temporary, new GsonBuilder().setPrettyPrinting().create().toJson(object));
        try { Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
        catch (AtomicMoveNotSupportedException e) { Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING); }
    }
}
