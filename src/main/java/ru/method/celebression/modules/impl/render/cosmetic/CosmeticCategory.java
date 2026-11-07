package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1074;
import net.minecraft.class_2960;
import org.jetbrains.annotations.Nullable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public enum CosmeticCategory {
    Pp(CosmeticSlot.dr, "cosmetics.slot.cape", CategoryInfo.PB, null),
    Pq(CosmeticSlot.ds, "cosmetics.slot.hat", CategoryInfo.PC, null),
    Pr(CosmeticSlot.dt, "cosmetics.slot.wings", CategoryInfo.PD, null),
    Ps(null, "cosmetics.slot.effect", CategoryInfo.PE, Celebression.id("cosmetics/icons/effect_halo.png")),
    Pt(null, "cosmetics.slot.pet", CategoryInfo.PF, null),
    Pu(null, "cosmetics.slot.animation", CategoryInfo.PG, null);
    private final CosmeticSlot Pv;
    private final String Pw;
    private final CategoryInfo Px;
    @Nullable
    private final class_2960 Py;

private CosmeticCategory(CosmeticSlot cosmeticSlot, @Nullable String string2, CategoryInfo categoryInfo, class_2960 class_29602) {
        this.Pv = cosmeticSlot;
        this.Pw = string2;
        this.Px = categoryInfo;
        this.Py = class_29602;
    }

    public CosmeticSlot lD() {
        return this.Pv;
    }

    public String lE() {
        return this.Pw;
    }

    public String label() {
        return class_1074.method_4662((String)this.Pw, (Object[])new Object[0]);
    }

    public CategoryInfo lF() {
        return this.Px;
    }

    @Nullable
    public class_2960 G() {
        return this.Py;
    }

    public boolean lG() {
        return this.Pv != null;
    }

public static enum CategoryInfo {
    PA,
    PB,
    PC,
    PD,
    PE,
    PF,
    PG,
    PH;
}
}

