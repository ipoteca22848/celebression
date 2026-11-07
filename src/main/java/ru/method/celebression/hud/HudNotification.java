package ru.method.celebression.hud;

import java.awt.Color;
import lombok.Generated;

public class HudNotification {
    private final String lY;
    private final boolean lZ;
    private final long startTime;
    private final long ma;
    private final String mb;
    private final Color mc;

    public HudNotification(String string, boolean bl, long l) {
        this(string, bl, l, null, null);
    }

    public HudNotification(String string, boolean bl, long l, String string2) {
        this(string, bl, l, string2, null);
    }

    public HudNotification(String string, boolean bl, long l, String string2, Color color) {
        this.lY = string;
        this.lZ = bl;
        this.ma = l;
        this.mb = string2;
        this.mc = color;
        this.startTime = System.currentTimeMillis();
    }

    public boolean dz() {
        return System.currentTimeMillis() - this.startTime > this.ma;
    }

    public String dA() {
        return this.lZ ? "enabled" : "disabled";
    }

    public String dB() {
        if (this.mb != null && !this.mb.isEmpty()) {
            return this.mb;
        }
        return (String)(this.lY != null ? this.lY + " " : "") + this.dA();
    }

    @Generated
    public String dC() {
        return this.lY;
    }

    @Generated
    public boolean isEnabled() {
        return this.lZ;
    }

    @Generated
    public long dD() {
        return this.startTime;
    }

    @Generated
    public long getDuration() {
        return this.ma;
    }

    @Generated
    public String dE() {
        return this.mb;
    }

    @Generated
    public Color dF() {
        return this.mc;
    }
}

