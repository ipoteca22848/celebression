package ru.method.celebression.input;

import ru.method.celebression.api.setting.Setting;

import lombok.Generated;
import ru.method.celebression.api.setting.KeyBindParser;

public class KeyBind {
    public static final int Kg = 1000;
    public static final int Kh = 1001;
    private int f;
    private boolean Ki;

    private void jV() {
        int n;
        if (!this.Ki && this.f <= -95 && this.f >= -120 && (n = this.f + 100) >= 0) {
            this.f = n;
            this.Ki = true;
        }
    }

    public int c() {
        this.jV();
        return this.f;
    }

    public boolean jW() {
        this.jV();
        return this.Ki;
    }

    public boolean af(int n) {
        this.jV();
        if (this.isEmpty() || this.Ki || n < 0) {
            return false;
        }
        return this.f == n;
    }

    public boolean ag(int n) {
        this.jV();
        if (this.isEmpty() || !this.Ki || n < 0) {
            return false;
        }
        return this.f == n;
    }

    public boolean c(double d) {
        this.jV();
        if (this.isEmpty() || !this.Ki || d == 0.0) {
            return false;
        }
        return this.f == (d > 0.0 ? 1000 : 1001);
    }

    public String toString() {
        this.jV();
        return KeyBindParser.d(this);
    }

    public boolean isEmpty() {
        return this.f < 0;
    }

    @Generated
    public KeyBind(int n, boolean bl) {
        this.f = n;
        this.Ki = bl;
    }

    @Generated
    public void ah(int n) {
        this.f = n;
    }

    @Generated
    public void u(boolean bl) {
        this.Ki = bl;
    }
}

