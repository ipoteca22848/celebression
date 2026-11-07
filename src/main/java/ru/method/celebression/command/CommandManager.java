package ru.method.celebression.command;

import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import net.minecraft.class_2172;
import net.minecraft.class_637;
import ru.method.celebression.command.BindCommand;
import ru.method.celebression.command.ClientCommand;
import ru.method.celebression.command.ConfigCommand;
import ru.method.celebression.command.FriendCommand;
import ru.method.celebression.command.GpsCommand;
import ru.method.celebression.command.HelpCommand;
import ru.method.celebression.command.MacroCommand;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.command.PartyCommand;
import ru.method.celebression.command.PrefixCommand;
import ru.method.celebression.command.ThemeCommand;
import ru.method.celebression.command.WaypointCommand;

public class CommandManager
implements MinecraftClientAccess {
    private final CommandDispatcher<class_2172> ph = new CommandDispatcher();
    private final class_2172 pi = new class_637(null, aiG);
    private final List<ClientCommand> pj = new ArrayList<ClientCommand>();
    private String pk = ".";

    public CommandManager() {
        this.a(new HelpCommand(), new FriendCommand(), new BindCommand(), new ConfigCommand(), new ThemeCommand(), new MacroCommand(), new GpsCommand(), new WaypointCommand(), new PrefixCommand(), new PartyCommand());
    }

    private void a(ClientCommand ... clientCommandArray) {
        for (ClientCommand clientCommand : clientCommandArray) {
            clientCommand.register(this.ph);
            this.pj.add(clientCommand);
        }
    }

    public ClientCommand Z(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        for (ClientCommand clientCommand : this.pj) {
            if (clientCommand.t().equalsIgnoreCase(string)) {
                return clientCommand;
            }
            for (String string2 : clientCommand.w()) {
                if (string2 == null || !string2.equalsIgnoreCase(string)) continue;
                return clientCommand;
            }
        }
        return null;
    }

    public String aA(String string) {
        if (string == null || string.isBlank()) {
            return string;
        }
        for (ClientCommand clientCommand : this.pj) {
            if (clientCommand.t().equalsIgnoreCase(string)) {
                return clientCommand.t();
            }
            for (String string2 : clientCommand.w()) {
                if (string2 == null || !string2.equalsIgnoreCase(string)) continue;
                return string2;
            }
        }
        return string;
    }

    public String aB(String string) {
        int n;
        int n2;
        if (string == null || string.isBlank()) {
            return string;
        }
        for (n2 = 0; n2 < string.length() && Character.isWhitespace(string.charAt(n2)); ++n2) {
        }
        if (n2 >= string.length()) {
            return string;
        }
        for (n = n2; n < string.length() && !Character.isWhitespace(string.charAt(n)); ++n) {
        }
        String string2 = this.aA(string.substring(n2, n));
        if (string2 == null || string2.isBlank()) {
            return string;
        }
        return string.substring(0, n2) + string2 + string.substring(n);
    }

    public ClientCommand aC(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        if (string2.length() < 3) {
            return null;
        }
        ClientCommand clientCommand = null;
        int n = Integer.MAX_VALUE;
        for (ClientCommand clientCommand2 : this.pj) {
            int n2 = this.m(string2, clientCommand2.t());
            if (n2 < n) {
                n = n2;
                clientCommand = clientCommand2;
            }
            for (String string3 : clientCommand2.w()) {
                int n3;
                if (string3 == null || string3.isBlank() || (n3 = this.m(string2, string3)) >= n) continue;
                n = n3;
                clientCommand = clientCommand2;
            }
        }
        if (clientCommand == null) {
            return null;
        }
        if (n <= 1) {
            return clientCommand;
        }
        if (n == 2 && string2.length() >= 4) {
            return clientCommand;
        }
        return null;
    }

    private int m(String string, String string2) {
        if (string2 == null || string2.isBlank()) {
            return Integer.MAX_VALUE;
        }
        String string3 = string2.toLowerCase(Locale.ROOT);
        if (string3.equals(string)) {
            return 0;
        }
        if (string3.startsWith(string) || string.startsWith(string3)) {
            return Math.abs(string3.length() - string.length());
        }
        return this.n(string, string3);
    }

    private int n(String string, String string2) {
        int n;
        int[] nArray = new int[string2.length() + 1];
        int[] nArray2 = new int[string2.length() + 1];
        for (n = 0; n <= string2.length(); ++n) {
            nArray[n] = n;
        }
        for (n = 1; n <= string.length(); ++n) {
            nArray2[0] = n;
            for (int i = 1; i <= string2.length(); ++i) {
                int n2 = string.charAt(n - 1) == string2.charAt(i - 1) ? 0 : 1;
                nArray2[i] = Math.min(Math.min(nArray2[i - 1] + 1, nArray[i] + 1), nArray[i - 1] + n2);
            }
            int[] nArray3 = nArray;
            nArray = nArray2;
            nArray2 = nArray3;
        }
        return nArray[string2.length()];
    }

    @Generated
    public CommandDispatcher<class_2172> fg() {
        return this.ph;
    }

    @Generated
    public class_2172 fh() {
        return this.pi;
    }

    @Generated
    public List<ClientCommand> fi() {
        return this.pj;
    }

    @Generated
    public String fj() {
        return this.pk;
    }

    @Generated
    public void aD(String string) {
        this.pk = string;
    }
}

