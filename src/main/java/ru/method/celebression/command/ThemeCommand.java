package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.io.File;
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
import ru.method.celebression.command.HelpCommand;

public class ThemeCommand
extends ClientCommand {
    private static final String ba = ".theme";
    private static final String bb = "commands.help.desc.theme";
    private static final List<String> bc = List.of("theme save <name>", "theme load <name>", "theme remove <name>", "theme list", "theme dir");

    public ThemeCommand() {
        super("theme");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.theme", (Object[])new Object[0]), this.w(), bc);
            return 1;
        })).then(this.a("save").then(this.a("theme", StringArgumentType.word()).executes(commandContext -> {
            String string = Celebression.getInstance().getConfigManager().bm((String)commandContext.getArgument("theme", String.class));
            try {
                Celebression.getInstance().getConfigManager().bo(string);
                this.a("theme save " + string, class_1074.method_4662((String)"commands.theme.save", (Object[])new Object[]{string}), 9741240);
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ThemeCommand] Failed to save theme {}", (Object)string, (Object)exception);
                this.a("theme save " + string, class_1074.method_4662((String)"commands.theme.save_failed", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("load").then(this.a("theme", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            File[] fileArray = ThemeCommand.ai();
            if (fileArray != null) {
                Arrays.stream(fileArray).map(File::getName).map(ThemeCommand::q).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = Celebression.getInstance().getConfigManager().bm((String)commandContext.getArgument("theme", String.class));
            try {
                boolean bl = Celebression.getInstance().getConfigManager().bp(string);
                this.a("theme load " + string, class_1074.method_4662((String)(bl ? "commands.theme.load" : "commands.theme.notfound"), (Object[])new Object[]{string}), bl ? 9741240 : 16281969);
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ThemeCommand] Failed to load theme {}", (Object)string, (Object)exception);
                this.a("theme load " + string, class_1074.method_4662((String)"commands.theme.notfound", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("remove").then(this.a("theme", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            File[] fileArray = ThemeCommand.ai();
            if (fileArray != null) {
                Arrays.stream(fileArray).map(File::getName).map(ThemeCommand::q).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = Celebression.getInstance().getConfigManager().bm((String)commandContext.getArgument("theme", String.class));
            try {
                boolean bl = Celebression.getInstance().getConfigManager().bq(string);
                this.a("theme remove " + string, class_1074.method_4662((String)(bl ? "commands.theme.removed" : "commands.theme.notfound"), (Object[])new Object[]{string}), bl ? 16317180 : 16281969);
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("[ThemeCommand] Failed to remove theme {}", (Object)string, (Object)exception);
                this.a("theme remove " + string, class_1074.method_4662((String)"commands.theme.remove_failed", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("dir").executes(commandContext -> {
            this.ah();
            return 1;
        }))).then(this.a("list").executes(commandContext -> {
            File[] fileArray = ThemeCommand.ai();
            if (fileArray == null || fileArray.length == 0) {
                this.a("theme list", class_1074.method_4662((String)"commands.theme.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            Arrays.sort(fileArray, (file, file2) -> file.getName().compareToIgnoreCase(file2.getName()));
            String string = Celebression.getInstance().getCommandManager().fj();
            class_5250 class_52502 = ChatFormatting.x("theme list", class_1074.method_4662((String)"commands.help.desc.theme", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(fileArray.length)));
            for (File file3 : fileArray) {
                String string2 = ThemeCommand.q(file3.getName());
                class_5250 class_52503 = ChatFormatting.y(string2, "");
                class_52503.method_10852((class_2561)ChatFormatting.cI(string + "theme remove " + string2));
                ChatFormatting.a(class_52502, class_52503);
            }
            ChatOutput.b(class_52502);
            return 1;
        }));
    }

    private void ah() {
        File file = Celebression.getInstance().getThemesDir();
        if (!file.exists()) {
            file.mkdirs();
        }
        class_156.method_668().method_672(file);
        class_5250 class_52502 = class_2561.method_43470((String)file.getAbsolutePath()).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(8246268))).method_10958(new class_2558(class_2558.class_2559.field_11746, file.getAbsolutePath())).method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)file.getAbsolutePath()))));
        class_5250 class_52503 = ChatFormatting.x("theme dir", class_1074.method_4662((String)"commands.help.desc.theme", (Object[])new Object[0]));
        class_5250 class_52504 = ChatFormatting.z(class_1074.method_4662((String)"commands.ui.folder", (Object[])new Object[0]), "");
        class_52504.method_10852((class_2561)class_52502);
        ChatFormatting.a(class_52503, class_52504);
        ChatOutput.b(class_52503);
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.theme", (Object[])new Object[0]), string2, n);
    }

    private static File[] ai() {
        return Celebression.getInstance().getConfigManager().ai();
    }

    private static String q(String string) {
        return string.toLowerCase().endsWith(".theme") ? string.substring(0, string.length() - ".theme".length()) : string;
    }
}

