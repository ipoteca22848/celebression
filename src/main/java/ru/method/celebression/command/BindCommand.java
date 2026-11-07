package ru.method.celebression.command;

import ru.method.celebression.api.setting.Setting;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_5250;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.setting.KeyBindParser;
import ru.method.celebression.module.Module;

public class BindCommand
extends ClientCommand {
    private static final String ah = "commands.help.desc.bind";
    private static final List<String> ai = List.of("bind add <\u043c\u043e\u0434\u0443\u043b\u044c> <\u043a\u043b\u0430\u0432\u0438\u0448\u0430>", "bind remove <\u043c\u043e\u0434\u0443\u043b\u044c>", "bind list");

    public BindCommand() {
        super("bind");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.bind", (Object[])new Object[0]), this.w(), ai);
            return 1;
        })).then(this.a("add").then(this.a("module", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
                suggestionsBuilder.suggest(module.getName());
            }
            return suggestionsBuilder.buildFuture();
        }).then(this.a("bind", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (String string : KeyBindParser.cS(suggestionsBuilder.getRemaining())) {
                suggestionsBuilder.suggest(string);
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = (String)commandContext.getArgument("module", String.class);
            String string2 = (String)commandContext.getArgument("bind", String.class);
            String string3 = "bind add " + string + " " + string2;
            Module module = this.b(string);
            if (module == null) {
                this.a(string3, class_1074.method_4662((String)"commands.bind.notfound", (Object[])new Object[]{string}), 16281969);
                return 1;
            }
            KeyBind keyBind = KeyBindParser.cR(string2);
            if (keyBind == null) {
                this.a(string3, class_1074.method_4662((String)"commands.bind.unknown", (Object[])new Object[]{string2}), 16281969);
                return 1;
            }
            module.setBind(keyBind);
            this.a(string3, class_1074.method_4662((String)"commands.bind.set", (Object[])new Object[]{module.getName(), this.a(keyBind)}), 9741240);
            return 1;
        }))))).then(this.a("remove").then(this.a("module", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
                suggestionsBuilder.suggest(module.getName());
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = (String)commandContext.getArgument("module", String.class);
            String string2 = "bind remove " + string;
            Module module = this.b(string);
            if (module == null) {
                this.a(string2, class_1074.method_4662((String)"commands.bind.notfound", (Object[])new Object[]{string}), 16281969);
                return 1;
            }
            module.setBind(new KeyBind(-1, false));
            this.a(string2, class_1074.method_4662((String)"commands.bind.cleared", (Object[])new Object[]{module.getName()}), 9741240);
            return 1;
        })))).then(this.a("list").executes(commandContext -> {
            ArrayList<Module> arrayList = new ArrayList<Module>();
            for (Module object : Celebression.getInstance().getModuleManager().getModules()) {
                if (object.getBind() == null || object.getBind().isEmpty()) continue;
                arrayList.add(object);
            }
            if (arrayList.isEmpty()) {
                this.a("bind list", class_1074.method_4662((String)"commands.bind.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            arrayList.sort((module, module2) -> module.getName().compareToIgnoreCase(module2.getName()));
            class_5250 class_52502 = ChatFormatting.x("bind list", class_1074.method_4662((String)"commands.help.desc.bind", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(arrayList.size())));
            for (Module module3 : arrayList) {
                String string = this.a(module3.getBind());
                ChatFormatting.a(class_52502, ChatFormatting.y(module3.getName(), " [" + string + "]"));
            }
            ChatOutput.b(class_52502);
            return 1;
        }));
    }

    private Module b(String string) {
        if (string == null) {
            return null;
        }
        for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
            if (!module.getName().equalsIgnoreCase(string)) continue;
            return module;
        }
        return null;
    }

    private String a(KeyBind keyBind) {
        return KeyBindParser.d(keyBind);
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.bind", (Object[])new Object[0]), string2, n);
    }
}

