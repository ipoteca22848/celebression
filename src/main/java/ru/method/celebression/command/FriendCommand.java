package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import net.minecraft.class_640;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;

public class FriendCommand
extends ClientCommand {
    private static final String an = "commands.help.desc.friend";
    private static final List<String> ao = List.of("friend add <\u0438\u0433\u0440\u043e\u043a>", "friend remove <\u0438\u0433\u0440\u043e\u043a>", "friend list", "friend clear");

    public FriendCommand() {
        super("friend", "friends");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.friend", (Object[])new Object[0]), this.w(), ao);
            return 1;
        })).then(this.a("add").then(this.a("player", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            for (class_640 class_6402 : aiG.method_1562().method_2880()) {
                String string = class_6402.method_2966().getName();
                if (!string.toLowerCase().startsWith(suggestionsBuilder.getRemaining().toLowerCase())) continue;
                suggestionsBuilder.suggest(string);
            }
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = (String)commandContext.getArgument("player", String.class);
            if (!Celebression.getInstance().getFriendManager().av(string)) {
                Celebression.getInstance().getFriendManager().bt(string);
                this.a("friend add " + string, class_1074.method_4662((String)"commands.friend.added", (Object[])new Object[]{string}), 16317180);
            } else {
                this.a("friend add " + string, class_1074.method_4662((String)"commands.friend.already", (Object[])new Object[]{string}), 16096779);
            }
            return 1;
        })))).then(this.a("remove").then(this.a("player", StringArgumentType.word()).suggests((commandContext, suggestionsBuilder) -> {
            Celebression.getInstance().getFriendManager().es().stream().sorted(String::compareTo).filter(string -> string.startsWith(suggestionsBuilder.getRemaining())).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
            return suggestionsBuilder.buildFuture();
        }).executes(commandContext -> {
            String string = (String)commandContext.getArgument("player", String.class);
            if (Celebression.getInstance().getFriendManager().av(string)) {
                Celebression.getInstance().getFriendManager().bu(string);
                this.a("friend remove " + string, class_1074.method_4662((String)"commands.friend.removed", (Object[])new Object[]{string}), 16317180);
            } else {
                this.a("friend remove " + string, class_1074.method_4662((String)"commands.friend.notfound", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("list").executes(commandContext -> {
            ArrayList<String> arrayList = new ArrayList<String>(Celebression.getInstance().getFriendManager().es());
            if (arrayList.isEmpty()) {
                this.a("friend list", class_1074.method_4662((String)"commands.friend.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            arrayList.sort(String::compareToIgnoreCase);
            String string = Celebression.getInstance().getCommandManager().fj();
            class_5250 class_52502 = ChatFormatting.x("friend list", class_1074.method_4662((String)"commands.help.desc.friend", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(arrayList.size())));
            for (String string2 : arrayList) {
                class_5250 class_52503 = ChatFormatting.y(string2, "");
                class_52503.method_10852((class_2561)ChatFormatting.cI(string + "friend remove " + string2));
                ChatFormatting.a(class_52502, class_52503);
            }
            ChatOutput.b(class_52502);
            return 1;
        }))).then(this.a("clear").executes(commandContext -> {
            if (!Celebression.getInstance().getFriendManager().isEmpty()) {
                Celebression.getInstance().getFriendManager().clear();
                this.a("friend clear", class_1074.method_4662((String)"commands.friend.cleared", (Object[])new Object[0]), 16317180);
            } else {
                this.a("friend clear", class_1074.method_4662((String)"commands.friend.empty", (Object[])new Object[0]), 9741240);
            }
            return 1;
        }));
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.friend", (Object[])new Object[0]), string2, n);
    }
}

