package ru.method.celebression.api.mixins;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.class_1074;
import net.minecraft.class_2172;
import net.minecraft.class_634;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.client.AuctionHelperModule;
import ru.method.celebression.util.ChatFormatting;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.CommandManager;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.modules.impl.player.PvpSaveModule;
import ru.method.celebression.modules.impl.client.SecretDupeModule;

@Mixin(value={class_634.class})
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method={"sendChatMessage"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendChatMessage(@NotNull String string, CallbackInfo callbackInfo) {
        if (string.startsWith("/") && SecretDupeModule.aZ(string)) {
            callbackInfo.cancel();
            return;
        }
        if (this.handleClientCommand(string, false)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"sendChatCommand"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendChatCommand(@NotNull String string, CallbackInfo callbackInfo) {
        AuctionHelperModule.aT(string);
        if (SecretDupeModule.aZ(string)) {
            callbackInfo.cancel();
            return;
        }
        PvpSaveModule pvpSaveModule = Celebression.getInstance().getModuleManager().getModule(PvpSaveModule.class);
        if (pvpSaveModule != null && pvpSaveModule.isToggled() && pvpSaveModule.bE(string)) {
            callbackInfo.cancel();
            return;
        }
        if (this.handleClientCommand(string, true)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"sendCommand"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendCommand(@NotNull String string, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        AuctionHelperModule.aT(string);
        if (SecretDupeModule.aZ(string)) {
            callbackInfoReturnable.setReturnValue(true);
            return;
        }
        PvpSaveModule pvpSaveModule = Celebression.getInstance().getModuleManager().getModule(PvpSaveModule.class);
        if (pvpSaveModule != null && pvpSaveModule.isToggled() && pvpSaveModule.bE(string)) {
            callbackInfoReturnable.setReturnValue(false);
            return;
        }
        if (this.handleClientCommand(string, true)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    private boolean handleClientCommand(String string, boolean bl) {
        CommandManager commandManager = Celebression.getInstance().getCommandManager();
        String string2 = commandManager.fj();
        if (!string.startsWith(string2)) {
            return false;
        }
        if (Celebression.getInstance().isPanic()) {
            return false;
        }
        String string3 = this.extractRawClientCommand(string, string2, bl);
        if (string3 == null) {
            return false;
        }
        if ((string3 = string3.stripLeading()).isBlank()) {
            return false;
        }
        String string4 = commandManager.aB(string3);
        CommandDispatcher<class_2172> commandDispatcher = commandManager.fg();
        class_2172 class_21722 = commandManager.fh();
        try {
            commandDispatcher.execute(string4, class_21722);
        }
        catch (CommandSyntaxException commandSyntaxException) {
            this.sendUsageMessage(string4, commandDispatcher, class_21722, commandSyntaxException);
        }
        return true;
    }

    private String extractRawClientCommand(String string, String string2, boolean bl) {
        String string3;
        String string4;
        boolean bl2;
        if (string == null || string2 == null) {
            return null;
        }
        String string5 = string;
        boolean bl3 = bl2 = bl && string5.startsWith("/");
        if (bl2) {
            string5 = string5.substring(1);
        }
        if ((string4 = this.tryStandaloneDotPrefixCommand(string, bl)) != null) {
            return string4;
        }
        String string6 = this.tryStripPrefix(string5, string2);
        if (string6 != null) {
            return string6;
        }
        if (bl && string2.startsWith("/")) {
            string3 = string2.substring(1);
            if (string3.isEmpty()) {
                return string5;
            }
            String string7 = this.tryStripPrefix(string5, string3);
            if (string7 != null) {
                return string7;
            }
        }
        if ((string3 = this.resolveKnownLocalCommandCandidate(string5, bl, bl2)) != null) {
            return string3;
        }
        return null;
    }

    private String tryStripPrefix(String string, String string2) {
        if (string == null || string2 == null) {
            return null;
        }
        if (string2.isEmpty()) {
            return string;
        }
        if (!string.startsWith(string2)) {
            return null;
        }
        return string.substring(string2.length());
    }

    private String tryStandaloneDotPrefixCommand(String string, boolean bl) {
        if (bl || string == null) {
            return null;
        }
        String string2 = string.stripLeading();
        return ".prefix".equalsIgnoreCase(string2.stripTrailing()) ? "prefix" : null;
    }

    private String resolveKnownLocalCommandCandidate(String string, boolean bl, boolean bl2) {
        if (string == null) {
            return null;
        }
        String string2 = string.stripLeading();
        if (string2.isEmpty()) {
            return null;
        }
        if ((bl || bl2) && "prefix".equalsIgnoreCase(this.firstToken(string2))) {
            return null;
        }
        return Celebression.getInstance().getCommandManager().Z(this.firstToken(string2)) != null ? string2 : null;
    }

    private String firstToken(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return "";
        }
        int n = string2.indexOf(32);
        return n >= 0 ? string2.substring(0, n) : string2;
    }

    private void sendUsageMessage(String string, CommandDispatcher<class_2172> commandDispatcher, class_2172 class_21722, CommandSyntaxException commandSyntaxException) {
        CommandManager commandManager = Celebression.getInstance().getCommandManager();
        String[] stringArray = string.trim().split("\\s+");
        String string3 = stringArray.length > 0 ? stringArray[0] : "";
        ArrayList<String> usageLines = new ArrayList<>();
        CommandNode<class_2172> commandNode = commandDispatcher.getRoot().getChild(string3);
        if (commandNode != null) {
            Collection<String> usages = commandDispatcher.getSmartUsage(commandNode, class_21722).values();
            if (!usages.isEmpty()) {
                usages.forEach(usage -> usageLines.add(string3 + " " + usage));
            } else {
                for (String string4 : commandDispatcher.getAllUsage(commandNode, class_21722, false)) {
                    usageLines.add(string3 + " " + string4);
                }
            }
        }
        if (usageLines.isEmpty()) {
            commandDispatcher.getSmartUsage(commandDispatcher.getRoot(), class_21722).values().forEach(usageLines::add);
        }
        ClientCommand exactCommand = commandManager.Z(string3);
        if (exactCommand != null) {
            ChatFormatting.a(HelpCommand.f(exactCommand.t()), HelpCommand.e(exactCommand.t()), exactCommand.w(), HelpCommand.g(exactCommand.t()), commandSyntaxException.getRawMessage().getString(), 16281969);
            return;
        }
        if (string3.isBlank()) {
            HelpCommand.a(null, 16317180);
            return;
        }
        ClientCommand clientCommand = commandManager.aC(string3);
        if (clientCommand != null) {
            ChatFormatting.a(HelpCommand.f(clientCommand.t()), HelpCommand.e(clientCommand.t()), clientCommand.w(), HelpCommand.g(clientCommand.t()), class_1074.method_4662((String)"commands.help.suggest", (Object[])new Object[]{commandManager.fj() + string3, commandManager.fj() + clientCommand.t()}), 16281969);
            return;
        }
        HelpCommand.a(class_1074.method_4662((String)"commands.help.unknown", (Object[])new Object[]{commandManager.fj() + string3}), 16281969);
    }
}
