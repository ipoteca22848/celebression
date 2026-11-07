package ru.method.celebression.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_2338;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.render.MutablePoint;

public class GpsCommand
extends ClientCommand {
    private static final String ap = "commands.help.desc.gps";
    private static final List<String> aq = List.of("gps add <x> <z>", "gps add me", "gps remove <x> <z>", "gps clear");

    public GpsCommand() {
        super("gps");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.gps", (Object[])new Object[0]), this.w(), aq);
            return 1;
        })).then(this.a("add").then(this.a("X", IntegerArgumentType.integer()).then(this.a("Z", IntegerArgumentType.integer()).executes(commandContext -> {
            int n = (Integer)commandContext.getArgument("X", Integer.class);
            int n2 = (Integer)commandContext.getArgument("Z", Integer.class);
            MutablePoint mutablePoint = Celebression.getInstance().getGpsManager().et();
            if (mutablePoint != null && mutablePoint.getX() == n && mutablePoint.rE() == n2) {
                this.a("gps add " + n + " " + n2, class_1074.method_4662((String)"commands.gps.already", (Object[])new Object[]{n, n2}), 16096779);
            } else {
                Celebression.getInstance().getGpsManager().a(new MutablePoint(n, n2));
                this.a("gps add " + n + " " + n2, class_1074.method_4662((String)"commands.gps.added", (Object[])new Object[]{n, n2}), 9741240);
            }
            return 1;
        }))))).then(this.a("add").then(this.a("me").executes(commandContext -> this.ac())))).then(this.a("remove").then(this.a("X", IntegerArgumentType.integer()).then(this.a("Z", IntegerArgumentType.integer()).executes(commandContext -> {
            int n = (Integer)commandContext.getArgument("X", Integer.class);
            int n2 = (Integer)commandContext.getArgument("Z", Integer.class);
            if (Celebression.getInstance().getGpsManager().isEmpty()) {
                this.a("gps remove " + n + " " + n2, class_1074.method_4662((String)"commands.gps.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            MutablePoint mutablePoint = Celebression.getInstance().getGpsManager().et();
            if (mutablePoint != null && mutablePoint.getX() == n && mutablePoint.rE() == n2) {
                Celebression.getInstance().getGpsManager().clear();
                this.a("gps remove " + n + " " + n2, class_1074.method_4662((String)"commands.gps.removed", (Object[])new Object[]{n, n2}), 9741240);
            } else {
                this.a("gps remove " + n + " " + n2, class_1074.method_4662((String)"commands.gps.notfound", (Object[])new Object[]{n, n2}), 16281969);
            }
            return 1;
        }))))).then(this.a("clear").executes(commandContext -> {
            if (!Celebression.getInstance().getGpsManager().isEmpty()) {
                Celebression.getInstance().getGpsManager().clear();
                this.a("gps clear", class_1074.method_4662((String)"commands.gps.cleared", (Object[])new Object[0]), 9741240);
            } else {
                this.a("gps clear", class_1074.method_4662((String)"commands.gps.empty", (Object[])new Object[0]), 9741240);
            }
            return 1;
        }));
    }

    private int ac() {
        if (GpsCommand.aiG.field_1724 == null) {
            this.a("gps add me", class_1074.method_4662((String)"commands.gps.empty", (Object[])new Object[0]), 9741240);
            return 1;
        }
        class_2338 class_23382 = GpsCommand.aiG.field_1724.method_24515();
        int n = class_23382.method_10263();
        int n2 = class_23382.method_10260();
        MutablePoint mutablePoint = Celebression.getInstance().getGpsManager().et();
        if (mutablePoint != null && mutablePoint.getX() == n && mutablePoint.rE() == n2) {
            this.a("gps add me", class_1074.method_4662((String)"commands.gps.already", (Object[])new Object[]{n, n2}), 16096779);
            return 1;
        }
        Celebression.getInstance().getGpsManager().a(new MutablePoint(n, n2));
        this.a("gps add me", class_1074.method_4662((String)"commands.gps.added", (Object[])new Object[]{n, n2}), 9741240);
        return 1;
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.gps", (Object[])new Object[0]), string2, n);
    }
}

