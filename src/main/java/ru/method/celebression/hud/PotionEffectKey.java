package ru.method.celebression.hud;

import java.util.Objects;
import net.minecraft.class_1291;
import net.minecraft.class_6880;

public final class PotionEffectKey {
    private final class_6880<class_1291> wg;
    private final int wh;
    private final int wi;

    public PotionEffectKey(class_6880<class_1291> class_68802, int n, int n2) {
        this.wg = class_68802;
        this.wh = n;
        this.wi = n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PotionEffectKey)) {
            return false;
        }
        PotionEffectKey potionEffectKey = (PotionEffectKey)object;
        return this.wh == potionEffectKey.wh && this.wi == potionEffectKey.wi && Objects.equals(this.wg, potionEffectKey.wg);
    }

    public int hashCode() {
        int n = this.wg != null ? this.wg.hashCode() : 0;
        n = 31 * n + this.wh;
        n = 31 * n + this.wi;
        return n;
    }
}
