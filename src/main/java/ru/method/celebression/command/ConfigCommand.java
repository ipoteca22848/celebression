package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_156;
import net.minecraft.class_2172;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2583;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.manager.ConfigManager;
import ru.method.celebression.command.HelpCommand;

public class ConfigCommand
extends ClientCommand {
    private static final String aj = ".celebression";
    private static final String ak = ".prime";
    private static final String al = "commands.help.desc.config";
    private static final List<String> am = List.of("config save <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config load <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config list", "config dir");

    public ConfigCommand() {
        super("config", "cfg");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.config", (Object[])new Object[0]), this.w(), am);
            return 1;
        })).then(this.a("save").then(this.a("config", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            File[] fileArray;
            if (Celebression.getInstance().getConfigsDir().exists() && Celebression.getInstance().getConfigsDir().isDirectory() && (fileArray = ConfigCommand.ab()) != null) {
                Arrays.stream(fileArray).map(File::getName).map(ConfigCommand::c).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = Celebression.getInstance().getConfigManager().bl((String)commandContext.getArgument("config", String.class));
            try {
                Celebression.getInstance().getConfigManager().bh(string);
                this.a("config save " + string, class_1074.method_4662((String)"commands.config.save", (Object[])new Object[]{string}), 9741240);
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ConfigCommand] Failed to save config {}", (Object)string, (Object)exception);
                this.a("config save " + string, class_1074.method_4662((String)"commands.config.remove_failed", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("load").then(this.a("config", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            File[] fileArray;
            if (Celebression.getInstance().getConfigsDir().exists() && Celebression.getInstance().getConfigsDir().isDirectory() && (fileArray = ConfigCommand.ab()) != null) {
                Arrays.stream(fileArray).map(File::getName).map(ConfigCommand::c).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = Celebression.getInstance().getConfigManager().bl((String)commandContext.getArgument("config", String.class));
            try {
                boolean bl = Celebression.getInstance().getConfigManager().bi(string);
                if (bl) {
                    this.a("config load " + string, class_1074.method_4662((String)"commands.config.load", (Object[])new Object[]{string}), 9741240);
                } else {
                    this.a("config load " + string, class_1074.method_4662((String)"commands.config.notfound", (Object[])new Object[]{string}), 16281969);
                }
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ConfigCommand] Failed to load config {}", (Object)string, (Object)exception);
                this.a("config load " + string, class_1074.method_4662((String)"commands.config.notfound", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("remove").then(this.a("config", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            File[] fileArray;
            if (Celebression.getInstance().getConfigsDir().exists() && Celebression.getInstance().getConfigsDir().isDirectory() && (fileArray = ConfigCommand.ab()) != null) {
                Arrays.stream(fileArray).map(File::getName).map(ConfigCommand::c).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            File file;
            ConfigManager configManager = Celebression.getInstance().getConfigManager();
            String string = configManager.bl((String)commandContext.getArgument("config", String.class));
            if ("default".equalsIgnoreCase(string)) {
                this.a("config remove " + string, class_1074.method_4662((String)"commands.config.default_locked", (Object[])new Object[0]), 16096779);
                return 1;
            }
            String string2 = configManager.bl(configManager.dQ());
            if (string.equalsIgnoreCase(string2)) {
                try {
                    configManager.bh("default");
                }
                catch (Exception exception) {
                    Celebression.LOGGER.error("[ConfigCommand] Failed to switch current config before deleting {}", (Object)string, (Object)exception);
                    this.a("config remove " + string, class_1074.method_4662((String)"commands.config.remove_failed", (Object[])new Object[]{string}), 16281969);
                    return 1;
                }
            }
            if (!(file = ConfigCommand.d(string)).exists()) {
                this.a("config remove " + string, class_1074.method_4662((String)"commands.config.notfound", (Object[])new Object[]{string}), 16281969);
                return 1;
            }
            try {
                Files.deleteIfExists(file.toPath());
                this.a("config remove " + string, class_1074.method_4662((String)"commands.config.removed", (Object[])new Object[]{string}), 16317180);
            }
            catch (IOException iOException) {
                Celebression.LOGGER.error("[ConfigCommand] Failed to remove config {}", (Object)string, (Object)iOException);
                this.a("config remove " + string, class_1074.method_4662((String)"commands.config.remove_failed", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("dir").executes(commandContext -> {
            this.aa();
            return 1;
        }))).then(this.a("list").executes(commandContext -> {
            File[] fileArray = ConfigCommand.ab();
            String string = Celebression.getInstance().getCommandManager().fj();
            if (fileArray == null || fileArray.length == 0) {
                this.a("config list", class_1074.method_4662((String)"commands.config.empty", (Object[])new Object[0]), 9741240);
            } else {
                Arrays.sort(fileArray, (file, file2) -> file.getName().compareToIgnoreCase(file2.getName()));
                class_5250 class_52502 = ChatFormatting.x("config list", class_1074.method_4662((String)"commands.help.desc.config", (Object[])new Object[0]));
                ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(fileArray.length)));
                for (File file3 : fileArray) {
                    String string2 = ConfigCommand.c(file3.getName());
                    class_5250 class_52503 = ChatFormatting.y(string2, "");
                    if (!"default".equalsIgnoreCase(string2)) {
                        class_52503.method_10852((class_2561)ChatFormatting.cI(string + "config remove " + string2));
                    } else {
                        class_52503.method_10852((class_2561)ChatFormatting.a(class_1074.method_4662((String)"commands.ui.default", (Object[])new Object[0]), 9741240, null, null, class_1074.method_4662((String)"commands.config.default_locked", (Object[])new Object[0])));
                    }
                    ChatFormatting.a(class_52502, class_52503);
                }
                ChatOutput.b(class_52502);
            }
            return 1;
        }));
    }

    private void aa() {
        File file = Celebression.getInstance().getConfigsDir();
        if (!file.exists()) {
            file.mkdirs();
        }
        class_156.method_668().method_672(file);
        class_5250 class_52502 = class_2561.method_43470((String)file.getAbsolutePath()).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(8246268))).method_10958(new class_2558(class_2558.class_2559.field_11746, file.getAbsolutePath())).method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)file.getAbsolutePath()))));
        class_5250 class_52503 = ChatFormatting.x("config dir", class_1074.method_4662((String)"commands.help.desc.config", (Object[])new Object[0]));
        class_5250 class_52504 = ChatFormatting.z(class_1074.method_4662((String)"commands.ui.folder", (Object[])new Object[0]), "");
        class_52504.method_10852((class_2561)class_52502);
        ChatFormatting.a(class_52503, class_52504);
        ChatOutput.b(class_52503);
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.config", (Object[])new Object[0]), string2, n);
    }

    private static File[] ab() {
        return Celebression.getInstance().getConfigsDir().listFiles((file, string) -> {
            String string2 = string.toLowerCase();
            return string2.endsWith(".celebression") || string2.endsWith(".prime");
        });
    }

    private static String c(String string) {
        String string2 = string.toLowerCase();
        if (string2.endsWith(".celebression")) {
            return string.substring(0, string.length() - ".celebression".length());
        }
        if (string2.endsWith(".prime")) {
            return string.substring(0, string.length() - ".prime".length());
        }
        return string;
    }

    private static File d(String string) {
        File file = Celebression.getInstance().getConfigsDir();
        File file2 = new File(file, string + ".celebression");
        if (file2.exists()) {
            return file2;
        }
        return new File(file, string + ".prime");
    }
}

