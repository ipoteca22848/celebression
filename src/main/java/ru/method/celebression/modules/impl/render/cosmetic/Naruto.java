package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;

public final class Naruto {
    private static volatile Naruto INSTANCE;
    private final CosmeticEffects parent;

    public Naruto(CosmeticEffects cosmeticEffects) {
        this.parent = cosmeticEffects;
        INSTANCE = this;
    }

    public void onDisable() {
    }

    public static boolean enabled() {
        Naruto naruto = INSTANCE;
        if (naruto == null) {
            return false;
        }
        return naruto.isEnabled();
    }

    private boolean isEnabled() {
        return this.parent != null && this.parent.narutoOn();
    }
}

