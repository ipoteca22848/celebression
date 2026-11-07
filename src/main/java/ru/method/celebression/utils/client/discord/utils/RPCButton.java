package ru.method.celebression.utils.client.discord.utils;

import java.io.Serializable;
import lombok.Generated;

public class RPCButton
implements Serializable {
    private final String url;
    private final String label;

    public static RPCButton create(String string, String string2) {
        String string3 = string != null ? string : "";
        string3 = string3.substring(0, Math.min(string3.length(), 31));
        return new RPCButton(string3, string2);
    }

    protected RPCButton(String string, String string2) {
        this.label = string;
        this.url = string2;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getLabel() {
        return this.label;
    }
}

