package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.screen.CommandDelayEditorScreen;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.StringSetting;

public class CommandDelayModule
extends Module {
    private static final Gson sr = new Gson();
    private final StringSetting ss = new StringSetting("modules.settings.cmddelay.entries", "[]", "", "", Short.MAX_VALUE, () -> false, (string, string2) -> this.gy());
    private final ButtonSetting st = new ButtonSetting("modules.settings.cmddelay.open", "modules.settings.cmddelay.open", this::gA);
    private final List<CommandDelayRule> su = new ArrayList<CommandDelayRule>();
    private int tickCounter;
    private boolean sv;

    public CommandDelayModule() {
        super("CMDDelay", ModuleCategory.qm);
        this.gy();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        long l = System.currentTimeMillis();
        for (CommandDelayRule commandDelayRule : this.su) {
            commandDelayRule.sx = l;
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (CommandDelayModule.fullNullCheck() || ++this.tickCounter % 10 != 0) {
            return;
        }
        long l = System.currentTimeMillis();
        for (CommandDelayRule commandDelayRule : this.su) {
            long l2;
            if (commandDelayRule.af.isBlank() || commandDelayRule.sw <= 0 || l - commandDelayRule.sx < (l2 = Math.max(1L, (long)commandDelayRule.sw) * 1000L)) continue;
            this.aX(commandDelayRule.af);
            commandDelayRule.sx = l;
        }
    }

    public List<CommandDelayRule> gw() {
        return this.su;
    }

    public void c(String string, int n) {
        this.su.add(new CommandDelayRule(this.aY(string), Math.max(1, n), System.currentTimeMillis()));
        this.gx();
    }

    public void a(int n, String string, int n2) {
        if (n < 0 || n >= this.su.size()) {
            return;
        }
        CommandDelayRule commandDelayRule = this.su.get(n);
        commandDelayRule.af = this.aY(string);
        commandDelayRule.sw = Math.max(1, n2);
        commandDelayRule.sx = System.currentTimeMillis();
        this.gx();
    }

    public void s(int n) {
        if (n < 0 || n >= this.su.size()) {
            return;
        }
        this.su.remove(n);
        this.gx();
    }

        public void gx() {
        JsonArray jsonArray = new JsonArray();
        for (CommandDelayRule object22 : this.su) {
            if (object22.af.isBlank()) continue;
            JsonObject ruleJson = new JsonObject();
            ruleJson.addProperty("command", object22.af);
            ruleJson.addProperty("delay", (Number)object22.sw);
            jsonArray.add(ruleJson);
        }
        String string = sr.toJson((JsonElement)jsonArray);
        String string2 = Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
        this.sv = true;
        try {
            this.ss.bI(string2);
        }
        finally {
            this.sv = false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && !celebression.isConfigLoading()) {
            celebression.getConfigManager().dU();
        }
    }

    private void gy() {
        if (this.sv) {
            return;
        }
        this.su.clear();
        try {
            JsonElement jsonElement = (JsonElement)sr.fromJson(this.gz(), JsonElement.class);
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                return;
            }
            for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                int n;
                if (!jsonElement2.isJsonObject()) continue;
                JsonObject jsonObject = jsonElement2.getAsJsonObject();
                String string = jsonObject.has("command") ? jsonObject.get("command").getAsString() : "";
                int n2 = n = jsonObject.has("delay") ? jsonObject.get("delay").getAsInt() : 60;
                if (string.isBlank()) continue;
                this.su.add(new CommandDelayRule(this.aY(string), Math.max(1, n), System.currentTimeMillis()));
            }
        }
        catch (Exception exception) {
            this.su.clear();
        }
    }

    private String gz() {
        String string = this.ss.kg();
        if (string == null || string.isBlank()) {
            return "[]";
        }
        String string2 = string.trim();
        if (string2.startsWith("[")) {
            return string2;
        }
        try {
            return new String(Base64.getDecoder().decode(string2), StandardCharsets.UTF_8);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return string2;
        }
    }

    private void gA() {
        if (aiG == null) {
            return;
        }
        aiG.method_1507((class_437)new CommandDelayEditorScreen(this, CommandDelayModule.aiG.field_1755));
    }

    private void aX(String string) {
        if (CommandDelayModule.aiG.field_1724 == null || CommandDelayModule.aiG.field_1724.field_3944 == null) {
            return;
        }
        String string2 = this.aY(string);
        if (string2.isBlank()) {
            return;
        }
        CommandDelayModule.aiG.field_1724.field_3944.method_45730(string2);
    }

    private String aY(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim();
        while (string2.startsWith("/")) {
            string2 = string2.substring(1).trim();
        }
        return string2;
    }

    public static class CommandDelayRule {
        String af;
        int sw;
        long sx;

        public CommandDelayRule(String string, int n, long l) {
            this.af = string;
            this.sw = n;
            this.sx = l;
        }

        public String t() {
            return this.af;
        }

        public int gB() {
            return this.sw;
        }
    }
}
