package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1074;
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

public class HelpCommand
extends ClientCommand {
    private static final Map<String, HelpEntry> ar = new LinkedHashMap<String, HelpEntry>();

    public HelpCommand() {
        super("help");
    }

    public static String e(String string) {
        HelpEntry helpEntry = ar.get(string);
        if (helpEntry == null) {
            return string == null ? "" : string;
        }
        return class_1074.method_4662((String)helpEntry.ad(), (Object[])new Object[0]);
    }

    public static String f(String string) {
        HelpEntry helpEntry = ar.get(string);
        if (helpEntry == null) {
            return string == null ? "" : string;
        }
        return helpEntry.title();
    }

    public static List<String> g(String string) {
        HelpEntry helpEntry = ar.get(string);
        if (helpEntry == null) {
            return string == null || string.isBlank() ? List.of() : List.of(string);
        }
        return helpEntry.ae();
    }

    public static void a(String string, int n) {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getCommandManager() == null) {
            return;
        }
        String string2 = celebression.getCommandManager().fj();
        ArrayList<ClientCommand> arrayList = new ArrayList<ClientCommand>(celebression.getCommandManager().fi());
        arrayList.sort((clientCommand, clientCommand2) -> clientCommand.t().compareToIgnoreCase(clientCommand2.t()));
        class_5250 class_52502 = class_2561.method_43470((String)"").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(9741240))));
        if (string != null && !string.isBlank()) {
            class_52502.method_10852((class_2561)HelpCommand.a(string, n, false));
            class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
        }
        class_52502.method_10852((class_2561)HelpCommand.a(class_1074.method_4662((String)"commands.help.subtitle", (Object[])new Object[]{string2 + "help <\u043a\u043e\u043c\u0430\u043d\u0434\u0430>"}), 9741240, false));
        for (ClientCommand clientCommand3 : arrayList) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
            class_52502.method_10852((class_2561)HelpCommand.a(string2, clientCommand3));
        }
        ChatOutput.b(class_52502);
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            HelpCommand.a(null, 16317180);
            return 1;
        })).then(this.a("command", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (ClientCommand clientCommand : Celebression.getInstance().getCommandManager().fi()) {
                suggestionsBuilder.suggest(clientCommand.t());
                for (String string : clientCommand.w()) {
                    if (string == null || string.isBlank()) continue;
                    suggestionsBuilder.suggest(string);
                }
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            this.h((String)commandContext.getArgument("command", String.class));
            return 1;
        }));
    }

    private void h(String string) {
        ClientCommand clientCommand = this.i(string);
        if (clientCommand == null) {
            ChatOutput.b(class_2561.method_43470((String)class_1074.method_4662((String)"commands.help.unknown", (Object[])new Object[]{string})).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(16281969)))));
            return;
        }
        ChatFormatting.a(HelpCommand.f(clientCommand.t()), HelpCommand.e(clientCommand.t()), clientCommand.w(), HelpCommand.g(clientCommand.t()));
    }

    private ClientCommand i(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getCommandManager() == null) {
            return null;
        }
        return celebression.getCommandManager().Z(string.trim().toLowerCase(Locale.ROOT));
    }

    private static class_5250 a(String string, ClientCommand clientCommand) {
        String string2 = string + clientCommand.t();
        String string3 = HelpCommand.a(clientCommand.w(), string);
        String string4 = HelpCommand.e(clientCommand.t());
        class_5250 class_52502 = class_2561.method_43470((String)"");
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"\u2022 ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(9741240)))));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)string2).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(8246268))).method_10958(new class_2558(class_2558.class_2559.field_11750, ChatFormatting.cJ(string + "help " + clientCommand.t()))).method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)class_1074.method_4662((String)"commands.help.click_hint", (Object[])new Object[0]))))));
        if (!string3.isEmpty()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)(" (" + string3 + ")")).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(16096779)))));
        }
        class_52502.method_10852((class_2561)class_2561.method_43470((String)("  " + string4)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bh(16317180)))));
        return class_52502;
    }

    private static String a(String[] stringArray, String string3) {
        if (stringArray == null || stringArray.length == 0) {
            return "";
        }
        return Arrays.stream(stringArray).filter(string -> string != null && !string.isBlank()).map(string2 -> string3 + string2).distinct().reduce((string, string2) -> string + ", " + string2).orElse("");
    }

    private static class_5250 a(String string, int n, boolean bl) {
        return ChatFormatting.a(string, n, bl);
    }

    static {
        ar.put("help", new HelpEntry("\u041f\u043e\u043c\u043e\u0449\u044c", "commands.help.desc.help", List.of("help", "help <\u043a\u043e\u043c\u0430\u043d\u0434\u0430>")));
        ar.put("friend", new HelpEntry("\u0414\u0440\u0443\u0437\u044c\u044f", "commands.help.desc.friend", List.of("friend add <\u0438\u0433\u0440\u043e\u043a>", "friend remove <\u0438\u0433\u0440\u043e\u043a>", "friend list", "friend clear")));
        ar.put("bind", new HelpEntry("\u0411\u0438\u043d\u0434\u044b", "commands.help.desc.bind", List.of("bind add <\u043c\u043e\u0434\u0443\u043b\u044c> <\u043a\u043b\u0430\u0432\u0438\u0448\u0430>", "bind remove <\u043c\u043e\u0434\u0443\u043b\u044c>", "bind list")));
        ar.put("config", new HelpEntry("\u041a\u043e\u043d\u0444\u0438\u0433\u0438", "commands.help.desc.config", List.of("config save <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config load <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "config list", "config dir")));
        ar.put("macro", new HelpEntry("\u041c\u0430\u043a\u0440\u043e\u0441\u044b", "commands.help.desc.macro", List.of("macro add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <\u043a\u043b\u0430\u0432\u0438\u0448\u0430> <\u043a\u043e\u043c\u0430\u043d\u0434\u0430>", "macro remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "macro list", "macro clear")));
        ar.put("gps", new HelpEntry("GPS", "commands.help.desc.gps", List.of("gps add <x> <z>", "gps add me", "gps remove <x> <z>", "gps clear")));
        ar.put("way", new HelpEntry("\u041c\u0435\u0442\u043a\u0438", "commands.help.desc.way", List.of("way add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <x> <y> <z>", "way add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> me", "way remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "way list", "way clear")));
        ar.put("prefix", new HelpEntry("\u041f\u0440\u0435\u0444\u0438\u043a\u0441", "commands.help.desc.prefix", List.of("prefix", "prefix <\u043d\u043e\u0432\u044b\u0439_\u043f\u0440\u0435\u0444\u0438\u043a\u0441>")));
        ar.put("theme", new HelpEntry("\u0422\u0435\u043c\u044b", "commands.help.desc.theme", List.of("theme save <name>", "theme load <name>", "theme remove <name>", "theme list", "theme dir")));
        ar.put("party", new HelpEntry("\u041f\u0430\u0442\u0438", "commands.help.desc.party", List.of("party create", "party join <\u043a\u043e\u0434>", "party leave")));
    }

    static final class HelpEntry {
        private final String as;
        private final String at;
        private final List<String> au;

        HelpEntry(String string, String string2, List<String> list) {
            this.as = string;
            this.at = string2;
            this.au = list;
        }

        @Override
        public final String toString() {
            return "HelpEntry[" + "title=" + this.as + ", " + "descriptionKey=" + this.at + ", " + "examples=" + this.au + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.as, this.at, this.au);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof HelpEntry other)) return false;
            return java.util.Objects.deepEquals(this.as, other.as)
                && java.util.Objects.deepEquals(this.at, other.at)
                && java.util.Objects.deepEquals(this.au, other.au);
        }

        public String title() {
            return this.as;
        }

        public String ad() {
            return this.at;
        }

        public List<String> ae() {
            return this.au;
        }
    }
}

