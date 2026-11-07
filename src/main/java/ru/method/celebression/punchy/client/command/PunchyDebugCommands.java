package ru.method.celebression.punchy.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2561;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class PunchyDebugCommands {
    private PunchyDebugCommands() {
    }

    public static void register(CommandDispatcher<class_2168> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"punchy").then(class_2170.method_9247((String)"debugposition").executes(commandContext -> {
            boolean bl = PunchyDebug.toggleDebugPosition();
            PunchyConfig.Data data = PunchyConfig.snapshot();
            if (bl) {
                data.enableTuning = true;
                data.enableMod = true;
                PunchyConfig.applyAndSave(data);
            } else if (data.enableTuning) {
                data.enableTuning = false;
                PunchyConfig.applyAndSave(data);
            }
            ((class_2168)commandContext.getSource()).method_9226(() -> class_2561.method_43469((String)"punchy.commands.debug_position", (Object[])new Object[]{bl ? class_2561.method_43471((String)"punchy.toggle.on") : class_2561.method_43471((String)"punchy.toggle.off")}), false);
            return 1;
        }))).then(class_2170.method_9247((String)"pos").then(class_2170.method_9244((String)"x", (ArgumentType)FloatArgumentType.floatArg()).then(class_2170.method_9244((String)"y", (ArgumentType)FloatArgumentType.floatArg()).then(class_2170.method_9244((String)"z", (ArgumentType)FloatArgumentType.floatArg()).executes(commandContext -> {
            PunchyDebug.POS_X = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"x");
            PunchyDebug.POS_Y = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"y");
            PunchyDebug.POS_Z = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"z");
            ((class_2168)commandContext.getSource()).method_9226(() -> class_2561.method_43469((String)"punchy.commands.pos_set", (Object[])new Object[]{Float.valueOf(PunchyDebug.POS_X), Float.valueOf(PunchyDebug.POS_Y), Float.valueOf(PunchyDebug.POS_Z)}), false);
            return 1;
        })))))).then(class_2170.method_9247((String)"rot").then(class_2170.method_9244((String)"x", (ArgumentType)FloatArgumentType.floatArg()).then(class_2170.method_9244((String)"y", (ArgumentType)FloatArgumentType.floatArg()).then(class_2170.method_9244((String)"z", (ArgumentType)FloatArgumentType.floatArg()).executes(commandContext -> {
            PunchyDebug.ROT_X = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"x");
            PunchyDebug.ROT_Y = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"y");
            PunchyDebug.ROT_Z = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"z");
            ((class_2168)commandContext.getSource()).method_9226(() -> class_2561.method_43469((String)"punchy.commands.rot_set", (Object[])new Object[]{Float.valueOf(PunchyDebug.ROT_X), Float.valueOf(PunchyDebug.ROT_Y), Float.valueOf(PunchyDebug.ROT_Z)}), false);
            return 1;
        })))))).then(class_2170.method_9247((String)"scale").then(class_2170.method_9244((String)"s", (ArgumentType)FloatArgumentType.floatArg()).executes(commandContext -> {
            PunchyDebug.SCALE = FloatArgumentType.getFloat((CommandContext)commandContext, (String)"s");
            ((class_2168)commandContext.getSource()).method_9226(() -> class_2561.method_43469((String)"punchy.commands.scale_set", (Object[])new Object[]{Float.valueOf(PunchyDebug.SCALE)}), false);
            return 1;
        }))));
    }
}

