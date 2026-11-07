package ru.method.celebression.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.manager.UserSyncManager;

public class PartyCommand
extends ClientCommand {
    private static final String ax = "commands.help.desc.party";
    private static final List<String> ay = List.of("party create", "party join <\u043a\u043e\u0434>", "party info", "party leave");

    public PartyCommand() {
        super("party");
    }

    private boolean ag() {
        if (aiG == null || aiG.method_1558() == null) {
            ChatOutput.cG(class_1074.method_4662((String)"commands.party.only_servers", (Object[])new Object[0]));
            return false;
        }
        return true;
    }

    private static String k(String string) {
        return switch (string == null ? "" : string) {
            case "request_failed" -> "commands.party.request_failed";
            case "bad_response", "bad_endpoint" -> "commands.party.bad_backend";
            default -> "commands.party.unavailable";
        };
    }

    private static class_5250 c(String string, String string2) {
        class_5250 class_52502 = class_2561.method_43470((String)string2).method_27694(class_25832 -> class_25832.method_27703(class_5251.method_27717((int)0xFFFFFF)).method_30938(Boolean.valueOf(true)).method_10958(new class_2558(class_2558.class_2559.field_21462, string2)).method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)class_1074.method_4662((String)"commands.party.copy_hint", (Object[])new Object[0])))));
        return class_2561.method_43469((String)string, (Object[])new Object[]{class_52502}).method_27694(class_25832 -> class_25832.method_27703(class_5251.method_27717((int)0xC8C8C8)));
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.party", (Object[])new Object[0]), this.w(), ay);
            return 1;
        })).then(this.a("create").executes(commandContext -> {
            if (!this.ag()) {
                return 1;
            }
            Celebression celebression = Celebression.getInstance();
            if (celebression == null || celebression.getUserSyncManager() == null) {
                ChatOutput.cG(class_1074.method_4662((String)"commands.party.unavailable", (Object[])new Object[0]));
                return 1;
            }
            celebression.getUserSyncManager().b(string -> ChatOutput.b(PartyCommand.c("commands.party.created", string)), string -> ChatOutput.cG(class_1074.method_4662((String)PartyCommand.k(string), (Object[])new Object[0])));
            return 1;
        }))).then(this.a("join").then(this.a("code", StringArgumentType.word()).executes(commandContext -> {
            if (!this.ag()) {
                return 1;
            }
            String string2 = (String)commandContext.getArgument("code", String.class);
            Celebression celebression = Celebression.getInstance();
            if (celebression == null || celebression.getUserSyncManager() == null) {
                ChatOutput.cG(class_1074.method_4662((String)"commands.party.unavailable", (Object[])new Object[0]));
                return 1;
            }
            celebression.getUserSyncManager().a(string2, string -> ChatOutput.cG(class_1074.method_4662((String)"commands.party.joined", (Object[])new Object[]{string})), string -> {
                String errorKey = switch (string == null ? "" : string) {
                    case "request_failed" -> "commands.party.request_failed";
                    case "bad_endpoint" -> "commands.party.bad_backend";
                    case "not_ready" -> "commands.party.unavailable";
                    default -> "commands.party.notfound";
                };
                ChatOutput.cG(class_1074.method_4662(errorKey, (Object[])new Object[0]));
            });
            return 1;
        })))).then(this.a("info").executes(commandContext -> {
            if (!this.ag()) {
                return 1;
            }
            Celebression celebression = Celebression.getInstance();
            if (celebression == null || celebression.getUserSyncManager() == null) {
                ChatOutput.cG(class_1074.method_4662((String)"commands.party.unavailable", (Object[])new Object[0]));
                return 1;
            }
            UserSyncManager userSyncManager = celebression.getUserSyncManager();
            if (!userSyncManager.eJ()) {
                ChatOutput.cG(class_1074.method_4662((String)"commands.party.not_in_party", (Object[])new Object[0]));
                return 1;
            }
            List<String> list = userSyncManager.eK();
            ChatOutput.b(PartyCommand.c("commands.party.info.code", userSyncManager.eU()));
            ChatOutput.cG(class_1074.method_4662((String)"commands.party.info.members", (Object[])new Object[]{list.size(), String.join((CharSequence)", ", list)}));
            return 1;
        }))).then(this.a("leave").executes(commandContext -> {
            if (!this.ag()) {
                return 1;
            }
            Celebression celebression = Celebression.getInstance();
            if (celebression == null || celebression.getUserSyncManager() == null) {
                ChatOutput.cG(class_1074.method_4662((String)"commands.party.unavailable", (Object[])new Object[0]));
                return 1;
            }
            celebression.getUserSyncManager().c(void_ -> ChatOutput.cG(class_1074.method_4662((String)"commands.party.left", (Object[])new Object[0])), string -> {
                String string2 = "not_in_party".equals(string) ? "commands.party.not_in_party" : PartyCommand.k(string);
                ChatOutput.cG(class_1074.method_4662((String)string2, (Object[])new Object[0]));
            });
            return 1;
        }));
    }
}
