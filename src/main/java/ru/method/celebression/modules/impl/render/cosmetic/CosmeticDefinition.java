package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_2960;
import org.jetbrains.annotations.Nullable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffectType;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelAnchor;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticDefinition {
    private final String db;
    private final CosmeticSlot dc;
    private final String dd;
    @Nullable
    private final class_2960 de;
    @Nullable
    private final class_2960 df;
    @Nullable
    private final class_2960 dg;
    private final CosmeticModelAnchor dh;
    private final CosmeticEffectType di;

    public CosmeticDefinition(String string, CosmeticSlot cosmeticSlot, String string2, @Nullable class_2960 class_29602, @Nullable class_2960 class_29603, @Nullable class_2960 class_29604, CosmeticModelAnchor cosmeticModelAnchor, CosmeticEffectType cosmeticEffectType) {
        this.db = string;
        this.dc = cosmeticSlot;
        this.dd = string2;
        this.de = class_29602;
        this.df = class_29603;
        this.dg = class_29604;
        this.dh = cosmeticModelAnchor;
        this.di = cosmeticEffectType;
    }

    public static CosmeticDefinition a(CosmeticSlot cosmeticSlot) {
        return new CosmeticDefinition("none", cosmeticSlot, "cosmetics.none", null, null, null, CosmeticModelAnchor.cY, CosmeticEffectType.dj);
    }

    public static CosmeticDefinition a(String string, String string2, String string3) {
        class_2960 class_29602 = Celebression.id(string3);
        return new CosmeticDefinition(string, CosmeticSlot.dr, string2, class_29602, class_29602, null, CosmeticModelAnchor.cZ, CosmeticEffectType.dj);
    }

    public static CosmeticDefinition a(String string, CosmeticSlot cosmeticSlot, String string2, String string3, String string4, CosmeticModelAnchor cosmeticModelAnchor) {
        class_2960 class_29602 = Celebression.id(string3);
        class_2960 class_29603 = Celebression.id(string4);
        return new CosmeticDefinition(string, cosmeticSlot, string2, class_29602, class_29602, class_29603, cosmeticModelAnchor, CosmeticEffectType.dj);
    }

    public static CosmeticDefinition a(String string, String string2, String string3, CosmeticEffectType cosmeticEffectType) {
        class_2960 class_29602 = Celebression.id(string3);
        return new CosmeticDefinition(string, CosmeticSlot.du, string2, class_29602, class_29602, null, CosmeticModelAnchor.cZ, cosmeticEffectType);
    }

    public boolean W() {
        return "none".equals(this.db);
    }

    public boolean X() {
        return this.dg != null;
    }

    public boolean Y() {
        return this.di != null && this.di != CosmeticEffectType.dj;
    }

    public float bf() {
        return 0.0f;
    }

    public float bg() {
        return 0.0f;
    }

    public float bh() {
        return 0.0f;
    }

    public float Z() {
        return 1.0f;
    }

    @Override
    public final String toString() {
        return "CosmeticDefinition[" + "id=" + this.db + ", " + "slot=" + this.dc + ", " + "nameKey=" + this.dd + ", " + "texture=" + this.de + ", " + "previewTexture=" + this.df + ", " + "modelAsset=" + this.dg + ", " + "anchor=" + this.dh + ", " + "effectType=" + this.di + "]";
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.db, this.dc, this.dd, this.de, this.df, this.dg, this.dh, this.di);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CosmeticDefinition other)) return false;
        return java.util.Objects.deepEquals(this.db, other.db)
            && java.util.Objects.deepEquals(this.dc, other.dc)
            && java.util.Objects.deepEquals(this.dd, other.dd)
            && java.util.Objects.deepEquals(this.de, other.de)
            && java.util.Objects.deepEquals(this.df, other.df)
            && java.util.Objects.deepEquals(this.dg, other.dg)
            && java.util.Objects.deepEquals(this.dh, other.dh)
            && java.util.Objects.deepEquals(this.di, other.di);
    }

    public String aA() {
        return this.db;
    }

    public CosmeticSlot aB() {
        return this.dc;
    }

    public String aC() {
        return this.dd;
    }

    @Nullable
    public class_2960 texture() {
        return this.de;
    }

    @Nullable
    public class_2960 aD() {
        return this.df;
    }

    @Nullable
    public class_2960 aE() {
        return this.dg;
    }

    public CosmeticModelAnchor be() {
        return this.dh;
    }

    public CosmeticEffectType aF() {
        return this.di;
    }
}

