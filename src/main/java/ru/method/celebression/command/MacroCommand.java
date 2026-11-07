package ru.method.celebression.command;

import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.module.Module;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.setting.KeyBindParser;
import ru.method.celebression.module.ModuleDescriptor;

public class MacroCommand
extends ClientCommand {
    private static final String av = "commands.help.desc.macro";
    private static final List<String> aw = List.of("macro add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <\u043a\u043b\u0430\u0432\u0438\u0448\u0430> <\u043a\u043e\u043c\u0430\u043d\u0434\u0430>", "macro remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "macro list", "macro clear");

    public MacroCommand() {
        super("macro");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.macro", (Object[])new Object[0]), this.w(), aw);
            return 1;
        })).then(this.a("add").then(this.a("name", StringArgumentType.word()).then(this.a("bind", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (String string : KeyBindParser.cS(suggestionsBuilder.getRemaining())) {
                suggestionsBuilder.suggest(string);
            }
            return suggestionsBuilder.buildFuture();
        }).then(this.a("command", StringArgumentType.greedyString()).executes(commandContext -> {
            String string = (String)commandContext.getArgument("name", String.class);
            String string2 = (String)commandContext.getArgument("bind", String.class);
            KeyBind keyBind = KeyBindParser.cR(string2);
            String string3 = (String)commandContext.getArgument("command", String.class);
            String string4 = "macro add " + string + " " + string2 + " " + string3;
            if (keyBind == null || keyBind.jW()) {
                this.a(string4, class_1074.method_4662((String)"commands.bind.unknown", (Object[])new Object[]{string2}), 16281969);
                return 1;
            }
            if (!Celebression.getInstance().getMacroManager().ew().contains(string)) {
                ModuleDescriptor moduleDescriptor = new ModuleDescriptor(string, string3, keyBind);
                Celebression.getInstance().getMacroManager().a(moduleDescriptor);
                this.af();
                this.a(string4, class_1074.method_4662((String)"commands.macro.added", (Object[])new Object[]{string}), 16317180);
            } else {
                this.a(string4, class_1074.method_4662((String)"commands.macro.already", (Object[])new Object[]{string}), 16096779);
            }
            return 1;
        })))))).then(this.a("remove").then(this.a("name", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            Celebression.getInstance().getMacroManager().ew().stream().filter(string -> string.startsWith(suggestionsBuilder.getRemaining())).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = (String)commandContext.getArgument("name", String.class);
            String string2 = "macro remove " + string;
            if (Celebression.getInstance().getMacroManager().ev().isEmpty() || Celebression.getInstance().getMacroManager().ew().isEmpty()) {
                return 1;
            }
            if (!Celebression.getInstance().getMacroManager().ew().contains(string)) {
                this.a(string2, class_1074.method_4662((String)"commands.macro.notfound", (Object[])new Object[]{string}), 16281969);
                return 1;
            }
            Celebression.getInstance().getMacroManager().b(Celebression.getInstance().getMacroManager().bw(string));
            this.af();
            this.a(string2, class_1074.method_4662((String)"commands.macro.removed", (Object[])new Object[]{string}), 16317180);
            return 1;
        })))).then(this.a("list").executes(commandContext -> {
            if (Celebression.getInstance().getMacroManager().ev().isEmpty()) {
                this.a("macro list", class_1074.method_4662((String)"commands.macro.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            ArrayList<ModuleDescriptor> arrayList = new ArrayList<ModuleDescriptor>(Celebression.getInstance().getMacroManager().ev());
            arrayList.sort(Comparator.comparing(ModuleDescriptor::getName, String::compareToIgnoreCase));
            class_5250 class_52502 = ChatFormatting.x("macro list", class_1074.method_4662((String)"commands.help.desc.macro", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(arrayList.size())));
            for (ModuleDescriptor moduleDescriptor : arrayList) {
                String string = this.a(moduleDescriptor.getBind());
                class_5250 class_52503 = ChatFormatting.y(moduleDescriptor.getName(), " [" + string + "] " + moduleDescriptor.t());
                class_52503.method_10852((class_2561)ChatFormatting.cI(Celebression.getInstance().getCommandManager().fj() + "macro remove " + moduleDescriptor.getName()));
                ChatFormatting.a(class_52502, class_52503);
            }
            ChatOutput.b(class_52502);
            return 1;
        }))).then(this.a("clear").executes(commandContext -> {
            if (!Celebression.getInstance().getMacroManager().isEmpty()) {
                Celebression.getInstance().getMacroManager().clear();
                this.af();
                this.a("macro clear", class_1074.method_4662((String)"commands.macro.cleared", (Object[])new Object[0]), 16317180);
            } else {
                this.a("macro clear", class_1074.method_4662((String)"commands.macro.empty", (Object[])new Object[0]), 9741240);
            }
            return 1;
        }));
    }

    private void af() {
        try {
            Celebression.getInstance().getConfigManager().af();
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[MacroCommand] Failed to save macros", (Throwable)exception);
        }
    }

    private String a(KeyBind keyBind) {
        return KeyBindParser.d(keyBind);
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.macro", (Object[])new Object[0]), string2, n);
    }
}

