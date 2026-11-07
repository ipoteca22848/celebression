package ru.method.celebression.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.util.NamedItem;

public class WaypointCommand
extends ClientCommand {
    private static final String bd = "commands.help.desc.way";
    private static final List<String> be = List.of("way add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <x> <y> <z>", "way add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> me", "way remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "way list", "way clear");

    public WaypointCommand() {
        super("way", "waypoint");
    }

    @Override
    public void a(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
            ChatFormatting.a(HelpCommand.f(this.t()), class_1074.method_4662((String)"commands.help.desc.way", (Object[])new Object[0]), this.w(), be);
            return 1;
        })).then(this.a("add").then(this.a("name", StringArgumentType.string()).then(this.a("X", IntegerArgumentType.integer()).then(this.a("Y", IntegerArgumentType.integer()).then(this.a("Z", IntegerArgumentType.integer()).executes(commandContext -> {
            String string = (String)commandContext.getArgument("name", String.class);
            int n = (Integer)commandContext.getArgument("X", Integer.class);
            int n2 = (Integer)commandContext.getArgument("Y", Integer.class);
            int n3 = (Integer)commandContext.getArgument("Z", Integer.class);
            if (Celebression.getInstance().getWaypointManager().O(string) == null) {
                Celebression.getInstance().getWaypointManager().a(new NamedItem(string, n, n2, n3));
                this.a("way add " + string + " " + n + " " + n2 + " " + n3, class_1074.method_4662((String)"commands.waypoint.added", (Object[])new Object[]{string, n, n2, n3}), 9741240);
            } else {
                this.a("way add " + string + " " + n + " " + n2 + " " + n3, class_1074.method_4662((String)"commands.waypoint.already", (Object[])new Object[]{string}), 16096779);
            }
            return 1;
        }))))))).then(this.a("add").then(this.a("name", StringArgumentType.string()).then(this.a("me").executes(commandContext -> this.r((String)commandContext.getArgument("name", String.class))))))).then(this.a("remove").then(this.a("name", StringArgumentType.greedyString()).executes(commandContext -> {
            if (Celebression.getInstance().getWaypointManager().isEmpty()) {
                this.a("way remove " + (String)commandContext.getArgument("name", String.class), class_1074.method_4662((String)"commands.waypoint.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            String string = (String)commandContext.getArgument("name", String.class);
            NamedItem namedItem = Celebression.getInstance().getWaypointManager().O(string);
            if (namedItem != null) {
                Celebression.getInstance().getWaypointManager().b(namedItem);
                this.a("way remove " + string, class_1074.method_4662((String)"commands.waypoint.removed", (Object[])new Object[]{string}), 9741240);
            } else {
                this.a("way remove " + string, class_1074.method_4662((String)"commands.waypoint.notfound", (Object[])new Object[]{string}), 16281969);
            }
            return 1;
        })))).then(this.a("list").executes(commandContext -> {
            if (Celebression.getInstance().getWaypointManager().isEmpty()) {
                this.a("way list", class_1074.method_4662((String)"commands.waypoint.empty", (Object[])new Object[0]), 9741240);
                return 1;
            }
            ArrayList<NamedItem> arrayList = new ArrayList<NamedItem>(Celebression.getInstance().getWaypointManager().ff());
            arrayList.sort(Comparator.comparing(NamedItem::getName, String::compareToIgnoreCase));
            String string = Celebression.getInstance().getCommandManager().fj();
            class_5250 class_52502 = ChatFormatting.x("way list", class_1074.method_4662((String)"commands.help.desc.way", (Object[])new Object[0]));
            ChatFormatting.a(class_52502, ChatFormatting.z(class_1074.method_4662((String)"commands.ui.entries", (Object[])new Object[0]), String.valueOf(arrayList.size())));
            for (NamedItem namedItem : arrayList) {
                String string2 = String.format("  X: %d, Y: %d, Z: %d", (int)namedItem.ti(), (int)namedItem.tj(), (int)namedItem.tk());
                class_5250 class_52503 = ChatFormatting.y(namedItem.getName(), string2);
                class_52503.method_10852((class_2561)ChatFormatting.cI(string + "way remove " + namedItem.getName()));
                ChatFormatting.a(class_52502, class_52503);
            }
            ChatOutput.b(class_52502);
            return 1;
        }))).then(this.a("clear").executes(commandContext -> {
            if (!Celebression.getInstance().getWaypointManager().isEmpty()) {
                Celebression.getInstance().getWaypointManager().clear();
                this.a("way clear", class_1074.method_4662((String)"commands.waypoint.cleared", (Object[])new Object[0]), 9741240);
            } else {
                this.a("way clear", class_1074.method_4662((String)"commands.waypoint.empty", (Object[])new Object[0]), 9741240);
            }
            return 1;
        }));
    }

    private int r(String string) {
        if (WaypointCommand.aiG.field_1724 == null) {
            this.a("way add " + string + " me", class_1074.method_4662((String)"commands.waypoint.empty", (Object[])new Object[0]), 9741240);
            return 1;
        }
        class_2338 class_23382 = WaypointCommand.aiG.field_1724.method_24515();
        int n = class_23382.method_10263();
        int n2 = class_23382.method_10264();
        int n3 = class_23382.method_10260();
        if (Celebression.getInstance().getWaypointManager().O(string) == null) {
            Celebression.getInstance().getWaypointManager().a(new NamedItem(string, n, n2, n3));
            this.a("way add " + string + " me", class_1074.method_4662((String)"commands.waypoint.added", (Object[])new Object[]{string, n, n2, n3}), 9741240);
        } else {
            this.a("way add " + string + " me", class_1074.method_4662((String)"commands.waypoint.already", (Object[])new Object[]{string}), 16096779);
        }
        return 1;
    }

    private void a(String string, String string2, int n) {
        ChatFormatting.b(string, class_1074.method_4662((String)"commands.help.desc.way", (Object[])new Object[0]), string2, n);
    }
}

