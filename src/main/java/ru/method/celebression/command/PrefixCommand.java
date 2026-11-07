package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_5250;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;

public class PrefixCommand
extends ClientCommand {
    private static final String az = "commands.help.desc.prefix";

    public PrefixCommand() {
        super("prefix");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            String string = Celebression.getInstance().getCommandManager().fj();
            class_5250 class_52502 = ChatFormatting.x("prefix", class_1074.method_4662((String)"commands.help.desc.prefix", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.prefix.current", (Object[])new Object[0]), string));
            ChatOutput.b(class_52502);
            return 1;
        })).then(this.a("newPrefix", StringArgumentType.greedyString()).executes(commandContext -> {
            String string = (String)commandContext.getArgument("newPrefix", String.class);
            String string2 = Celebression.getInstance().getCommandManager().fj();
            if (string.isEmpty()) {
                this.a("prefix <\u043d\u043e\u0432\u044b\u0439_\u043f\u0440\u0435\u0444\u0438\u043a\u0441>", class_1074.method_4662((String)"commands.prefix.empty", (Object[])new Object[0]), 16281969);
                return 1;
            }
            if (string.length() > 5) {
                this.a("prefix " + string, class_1074.method_4662((String)"commands.prefix.too_long", (Object[])new Object[0]), 16281969);
                return 1;
            }
            this.a("prefix " + string, class_1074.method_4662((String)"commands.prefix.changed", (Object[])new Object[]{string2, string}), 16317180);
            Celebression.getInstance().getCommandManager().aD(string);
            return 1;
        }));
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.prefix", (Object[])new Object[0]), string2, n);
    }
}

