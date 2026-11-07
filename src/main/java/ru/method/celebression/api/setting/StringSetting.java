package ru.method.celebression.api.setting;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.Generated;
import ru.method.celebression.api.setting.Setting;

public class StringSetting
extends Setting<String> {
    private final String Km;
    private final int Kn;
    private String Ko;
    private String Kp;
    private final Validator Kq;

    public StringSetting(String string, String string2, String string3) {
        this(string, string2, string3, "", 64, null, null);
    }

    public StringSetting(String string, String string2, String string3, Supplier<Boolean> supplier) {
        this(string, string2, string3, "", 64, supplier, null);
    }

    public StringSetting(String string, String string2, String string3, String string4) {
        this(string, string2, string3, string4, 64, null, null);
    }

    public StringSetting(String string, String string2, String string3, String string4, Supplier<Boolean> supplier) {
        this(string, string2, string3, string4, 64, supplier, null);
    }

    public StringSetting(String string, String string2, String string3, String string4, int n, Validator validator) {
        this(string, string2, string3, string4, n, null, validator);
    }

    public StringSetting(String string, String string2, String string3, String string4, int n, Supplier<Boolean> supplier, Validator validator) {
        super(string);
        this.Kp = string3;
        this.Km = Objects.requireNonNullElse(string4, "");
        this.Kn = Math.max(1, n);
        this.Kq = validator;
        this.Ko = this.bU(string2);
        this.Kf = this.Ko;
        this.Ke = this.Ko;
        if (supplier != null) {
            this.setVisible(supplier);
        }
    }

    public void bI(String string) {
        String string2 = this.bU(string);
        if (string2.equals(this.Ko)) {
            return;
        }
        String string3 = this.Ko;
        this.Ko = string2;
        this.Ke = string2;
        if (this.Kq != null) {
            this.Kq.onChange(string3, string2);
        }
    }

    private String bU(String string) {
        String string2 = Objects.requireNonNullElse(string, "");
        StringBuilder stringBuilder = new StringBuilder(Math.min(string2.length(), this.Kn));
        for (int i = 0; i < string2.length() && stringBuilder.length() < this.Kn; ++i) {
            char c = string2.charAt(i);
            if (c == '\u00a7') {
                stringBuilder.append('&');
                continue;
            }
            if (!this.a(c)) continue;
            stringBuilder.append(c);
        }
        String string3 = stringBuilder.toString();
        if (string3.length() > this.Kn) {
            string3 = string3.substring(0, this.Kn);
        }
        return string3;
    }

    private boolean a(char c) {
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= '\u0410' && c <= '\u044f') {
            return true;
        }
        if (c == '\u0401' || c == '\u0451') {
            return true;
        }
        if (c >= '0' && c <= '9') {
            return true;
        }
        return c == ' ' || c == '/' || c == '\\' || c == '_' || c == '-' || c == '.' || c == ',' || c == ':' || c == ';' || c == '!' || c == '?' || c == '#' || c == '&' || c == '[' || c == ']' || c == '(' || c == ')' || c == '<' || c == '>' || c == '+' || c == '=' || c == '*' || c == '\'' || c == '\"';
    }

    @Override
    public void reset() {
        this.bI((String)this.Kf);
    }

    @Override
    public void p(JsonObject jsonObject) {
        jsonObject.addProperty(String.valueOf(this.Kd), this.Ko);
    }

    @Override
    public void q(JsonObject jsonObject) {
        if (!jsonObject.has(String.valueOf(this.Kd))) {
            return;
        }
        this.bI(jsonObject.get(String.valueOf(this.Kd)).getAsString());
    }

    @Generated
    public String getDescription() {
        return this.Km;
    }

    @Generated
    public int kf() {
        return this.Kn;
    }

    @Generated
    public String kg() {
        return this.Ko;
    }

    @Generated
    public String kh() {
        return this.Kp;
    }

    @Generated
    public Validator ki() {
        return this.Kq;
    }

    @Override
    @Generated
    public String jT() {
        return this.kg();
    }

    @Override
    public void a(String value) {
        this.bI(value);
    }

    public static interface Validator {
        public void onChange(String var1, String var2);
    }
}
