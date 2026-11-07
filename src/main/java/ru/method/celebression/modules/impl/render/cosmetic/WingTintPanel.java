package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_332;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCardRenderer;

public final class WingTintPanel {
    private static final String QH = "cosmetics.wings.tint";
    private final CosmeticCardRenderer QI;

    public WingTintPanel(CosmeticCardRenderer cosmeticCardRenderer) {
        this.QI = cosmeticCardRenderer;
    }

    public float ag(float f) {
        return this.QI.lO();
    }

    public void a(int n, int n2, float f, float f2, float f3, float f4) {
        this.QI.b(n, n2, f, f2, f3);
    }

    public void a(class_332 class_3322, float f, float f2, float f3, float f4, int n, int n2, int n3) {
        this.QI.a(class_3322, f, f2, f3, n, n2, n3, "cosmetics.wings.tint");
    }

    public boolean a(float f, float f2, float f3, float f4, double d, double d2, int n) {
        return this.QI.a(f, f2, f3, d, d2, n);
    }

    public boolean an(int n) {
        return this.QI.an(n);
    }

    public boolean lN() {
        return this.QI.lN();
    }

    public int lL() {
        return this.QI.lL();
    }

    public void am(int n) {
        this.QI.am(n);
    }

    public boolean lM() {
        return this.QI.lM();
    }
}

