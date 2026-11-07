package ru.method.celebression.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import lombok.Generated;
import net.minecraft.class_2172;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public abstract class ClientCommand
implements MinecraftClientAccess {
    private final String af;
    private final String[] ag;

    public ClientCommand(String string) {
        this(string, new String[0]);
    }

    public ClientCommand(String string, String ... stringArray) {
        this.af = string;
        this.ag = stringArray == null ? new String[]{} : stringArray;
    }

    public abstract void a(LiteralArgumentBuilder<class_2172> var1);

    public void register(CommandDispatcher<class_2172> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = LiteralArgumentBuilder.literal((String)this.af);
        this.a((LiteralArgumentBuilder<class_2172>)literalArgumentBuilder);
        LiteralCommandNode literalCommandNode = commandDispatcher.register(literalArgumentBuilder);
        for (String string : this.ag) {
            String string2;
            if (string == null || (string2 = string.trim()).isEmpty() || string2.equals(this.af)) continue;
            commandDispatcher.register((LiteralArgumentBuilder)LiteralArgumentBuilder.literal((String)string2).redirect((CommandNode)literalCommandNode));
        }
    }

    protected <T> RequiredArgumentBuilder<class_2172, T> a(String string, ArgumentType<T> argumentType) {
        return RequiredArgumentBuilder.argument((String)string, argumentType);
    }

    protected LiteralArgumentBuilder<class_2172> a(String string) {
        return LiteralArgumentBuilder.literal((String)string);
    }

    @Generated
    public String t() {
        return this.af;
    }

    @Generated
    public String[] w() {
        return this.ag;
    }
}

