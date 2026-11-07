package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCatalog;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCategory;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDescriptionRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffectType;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewPose;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewSetup;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticUiRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.render.Rect;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CosmeticEffectsPanel {
    private static final int QJ = 2;
    private final CosmeticDescriptionRenderer QK = new CosmeticDescriptionRenderer();

        public ViewState a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, CosmeticPreviewPose cosmeticPreviewPose, float f, float f2, float f3, CosmeticEffects cosmeticEffects, Consumer<String> consumer) {
        float f4 = 16.0f;
        CosmeticUiRenderer.a(class_45872, transform, cosmeticCategory, transform.No + transform.Nt, transform.lk() + transform.Nt, f4);
        Rect rect = CosmeticEffectsPanel.a(transform);
        float f5 = CosmeticEffectsPanel.b(transform);
        float f6 = transform.NB;
        Render2D.startMatrixScissor(class_3322, rect.x(), rect.y(), rect.lB(), rect.lC());
        try {
            ViewState viewState = switch (cosmeticCategory) {
                case CosmeticCategory.Pp, CosmeticCategory.Pq, CosmeticCategory.Pr -> this.a(class_3322, class_45872, transform, cosmeticCategory, cosmeticPreviewPose, f, f5, f6, f2, f3, consumer);
                case CosmeticCategory.Ps -> this.a(class_3322, class_45872, transform, f5, f6, cosmeticEffects, f2, f3);
                case CosmeticCategory.Pt -> this.a(class_3322, class_45872, transform, cosmeticEffects, f, f5, f6, f2, f3);
                default -> new ViewState(rect, f, 0.0f, null);
            };
            return viewState;
        }
        finally {
            Render2D.stopScissor(class_3322);
        }
    }

    private ViewState a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, CosmeticPreviewPose cosmeticPreviewPose, float f, float f2, float f3, float f4, float f5, Consumer<String> consumer) {
        CosmeticSlot cosmeticSlot = cosmeticCategory.lD();
        String string = Celebression.getInstance().getCosmeticsService().aL().f(cosmeticSlot);
        List<CosmeticDefinition> list = CosmeticCatalog.b(cosmeticSlot);
        float f6 = transform.lk() + transform.Nz;
        float f7 = CosmeticEffectsPanel.a(transform).lC();
        int n = (list.size() + 2 - 1) / 2;
        float f8 = Math.max(0.0f, (float)n * (f3 + transform.NA) - f7);
        f = class_3532.method_15363((float)f, (float)0.0f, (float)f8);
        String string2 = null;
        float f9 = f6 - f;
        for (int i = 0; i < list.size(); ++i) {
            CosmeticDefinition cosmeticDefinition = list.get(i);
            int n2 = i % 2;
            int n3 = i / 2;
            float f10 = transform.No + transform.Nt + (float)n2 * (f2 + transform.NA);
            float f11 = f9 + (float)n3 * (f3 + transform.NA);
            boolean bl = cosmeticDefinition.aA().equals(string);
            this.QK.a(class_3322, class_45872, transform, f10, f11, f2, f3, cosmeticDefinition, cosmeticSlot, bl, cosmeticPreviewPose, f4, f5);
            if (!transform.c(f10, f11, f2, f3, f4, f5)) continue;
            string2 = cosmeticDefinition.aA();
        }
        if (consumer != null) {
            consumer.accept(string2);
        }
        return new ViewState(CosmeticEffectsPanel.a(transform), f, f8, string2);
    }

    private ViewState a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, float f, float f2, CosmeticEffects cosmeticEffects, float f3, float f4) {
        boolean bl = CosmeticEffects.bool(cosmeticEffects.haloEnabledSetting());
        boolean bl2 = CosmeticEffects.bool(cosmeticEffects.spheresEnabledSetting());
        float f5 = transform.No + transform.Nt;
        float f6 = transform.lk() + transform.Nz;
        this.QK.a(class_3322, class_45872, transform, f5, f6, f, f2, "cosmetics.effect.none", "cosmetics.effect.none.desc", null, true, !bl && !bl2, f3, f4);
        this.QK.a(class_3322, class_45872, transform, f5 + f + transform.NA, f6, f, f2, "cosmetics.effect.halo", "cosmetics.effect.halo.desc", CosmeticEffectType.dk, false, bl, f3, f4);
        this.QK.a(class_3322, class_45872, transform, f5, f6 + f2 + transform.NA, f, f2, "cosmetics.effect.spheres", "cosmetics.effect.spheres.desc", CosmeticEffectType.dl, false, bl2, f3, f4);
        return new ViewState(CosmeticEffectsPanel.a(transform), 0.0f, 0.0f, null);
    }

    private ViewState a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticEffects cosmeticEffects, float f, float f2, float f3, float f4, float f5) {
        boolean bl = CosmeticEffects.bool(cosmeticEffects.petEnabledSetting());
        CosmeticEnums.PetSkin petSkin = (CosmeticEnums.PetSkin)cosmeticEffects.petSkinSetting().jT();
        CosmeticEnums.PetSkin[] petSkinArray = CosmeticEnums.PetSkin.values();
        float f6 = transform.lk() + transform.Nz;
        float f7 = CosmeticEffectsPanel.a(transform).lC();
        int n = (petSkinArray.length + 2 - 1) / 2;
        float f8 = Math.max(0.0f, (float)n * (f3 + transform.NA) - f7);
        f = class_3532.method_15363((float)f, (float)0.0f, (float)f8);
        float f9 = f6 - f;
        for (int i = 0; i < petSkinArray.length; ++i) {
            CosmeticEnums.PetSkin petSkin2 = petSkinArray[i];
            int n2 = i % 2;
            int n3 = i / 2;
            float f10 = transform.No + transform.Nt + (float)n2 * (f2 + transform.NA);
            float f11 = f9 + (float)n3 * (f3 + transform.NA);
            boolean bl2 = bl && petSkin2 == petSkin;
            boolean bl3 = transform.c(f10, f11, f2, f3, f4, f5);
            CosmeticUiRenderer.b(class_45872, f10, f11, f2, f3, transform.NC, bl2, bl3);
            float f12 = f10 + (f2 - transform.ND) * 0.5f;
            float f13 = f11 + 12.0f;
            CosmeticUiRenderer.a(class_45872, f12, f13, transform.ND, transform.NE, bl2);
            CosmeticPreviewSetup.a(class_3322, cosmeticEffects, petSkin2, Math.round(f12), Math.round(f13), Math.round(transform.ND));
            float f14 = f11 + transform.ND + transform.ac(20.0f);
            UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, petSkin2.getName(), f10 + transform.ac(12.0f), f14, bl2 ? CosmeticColorUtils.Qo : CosmeticColorUtils.Qr, transform.ad(12.0f));
            CosmeticUiRenderer.b(class_45872, f10, f11, f2, f3, bl2, 1.0f);
        }
        return new ViewState(CosmeticEffectsPanel.a(transform), f, f8, null);
    }

    public static Rect a(CosmeticMathUtils.Transform transform) {
        float f = transform.lk() + transform.Nz;
        return new Rect(transform.No, f, transform.Np, transform.Ns - transform.Nz - transform.Nt);
    }

    public static float b(CosmeticMathUtils.Transform transform) {
        return (transform.Np - transform.Nt * 2.0f - transform.NA) / 2.0f;
    }

    public String a(CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, float f, float f2, float f3) {
        if (!cosmeticCategory.lG()) {
            return null;
        }
        CosmeticSlot cosmeticSlot = cosmeticCategory.lD();
        List<CosmeticDefinition> list = CosmeticCatalog.b(cosmeticSlot);
        float f4 = CosmeticEffectsPanel.b(transform);
        float f5 = transform.NB;
        float f6 = transform.lk() + transform.Nz - f;
        for (int i = 0; i < list.size(); ++i) {
            int n = i % 2;
            int n2 = i / 2;
            float f7 = transform.No + transform.Nt + (float)n * (f4 + transform.NA);
            float f8 = f6 + (float)n2 * (f5 + transform.NA);
            if (!(f2 >= f7) || !(f2 <= f7 + f4) || !(f3 >= f8) || !(f3 <= f8 + f5)) continue;
            return list.get(i).aA();
        }
        return null;
    }

    public boolean b(CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, float f, float f2, float f3) {
        String string = this.a(transform, cosmeticCategory, f, f2, f3);
        if (string == null) {
            return false;
        }
        CosmeticSlot cosmeticSlot = cosmeticCategory.lD();
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        String string2 = cosmeticsService.aL().f(cosmeticSlot);
        if (string.equals(string2)) {
            cosmeticsService.c(cosmeticSlot, CosmeticCatalog.a(cosmeticSlot).aA());
        } else {
            cosmeticsService.c(cosmeticSlot, string);
        }
        return true;
    }

    public boolean a(CosmeticMathUtils.Transform transform, float f, float f2, CosmeticEffects cosmeticEffects) {
        float f3;
        float f4;
        float f5 = transform.No + transform.Nt;
        float f6 = transform.lk() + transform.Nz;
        if (transform.c(f5, f6, f4 = CosmeticEffectsPanel.b(transform), f3 = transform.NB, f, f2)) {
            cosmeticEffects.haloEnabledSetting().a(false);
            cosmeticEffects.spheresEnabledSetting().a(false);
            return true;
        }
        if (transform.c(f5 + f4 + transform.NA, f6, f4, f3, f, f2)) {
            cosmeticEffects.haloEnabledSetting().a(true);
            cosmeticEffects.spheresEnabledSetting().a(false);
            return true;
        }
        if (transform.c(f5, f6 + f3 + transform.NA, f4, f3, f, f2)) {
            cosmeticEffects.spheresEnabledSetting().a(true);
            cosmeticEffects.haloEnabledSetting().a(false);
            return true;
        }
        return false;
    }

    public boolean a(CosmeticMathUtils.Transform transform, float f, float f2, float f3, CosmeticEffects cosmeticEffects) {
        boolean bl = CosmeticEffects.bool(cosmeticEffects.petEnabledSetting());
        CosmeticEnums.PetSkin petSkin = (CosmeticEnums.PetSkin)cosmeticEffects.petSkinSetting().jT();
        CosmeticEnums.PetSkin[] petSkinArray = CosmeticEnums.PetSkin.values();
        float f4 = CosmeticEffectsPanel.b(transform);
        float f5 = transform.NB;
        float f6 = transform.lk() + transform.Nz - f;
        for (int i = 0; i < petSkinArray.length; ++i) {
            CosmeticEnums.PetSkin petSkin2 = petSkinArray[i];
            int n = i % 2;
            float f7 = transform.No + transform.Nt + (float)n * (f4 + transform.NA);
            int n2 = i / 2;
            float f8 = f6 + (float)n2 * (f5 + transform.NA);
            if (!transform.c(f7, f8, f4, f5, f2, f3)) continue;
            if (bl && petSkin2 == petSkin) {
                cosmeticEffects.petEnabledSetting().a(false);
            } else {
                cosmeticEffects.petSkinSetting().a(petSkin2);
                cosmeticEffects.petEnabledSetting().a(true);
            }
            return true;
        }
        return false;
    }

    public float a(CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, float f) {
        int n;
        float f2 = transform.NB;
        float f3 = CosmeticEffectsPanel.a(transform).lC();
        switch (cosmeticCategory) {
            case Pp: 
            case Pq: 
            case Pr: {
                n = CosmeticCatalog.b(cosmeticCategory.lD()).size();
                break;
            }
            case Pt: {
                n = CosmeticEnums.PetSkin.values().length;
                break;
            }
            default: {
                n = 0;
            }
        }
        if (n <= 0) {
            return 0.0f;
        }
        int n3 = (n + 2 - 1) / 2;
        float f4 = Math.max(0.0f, (float)n3 * (f2 + transform.NA) - f3);
        return class_3532.method_15363((float)f, (float)0.0f, (float)f4);
    }

    public static final class ViewState {
        private final Rect QM;
        private final float QN;
        private final float QO;
        private final String QP;

        public ViewState(Rect rect, float f, float f2, String string) {
            this.QM = rect;
            this.QN = f;
            this.QO = f2;
            this.QP = string;
        }

        @Override
        public final String toString() {
            return "ViewState[" + "viewport=" + this.QM + ", " + "scroll=" + this.QN + ", " + "maxScroll=" + this.QO + ", " + "hoveredId=" + this.QP + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.QM, this.QN, this.QO, this.QP);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ViewState other)) return false;
            return java.util.Objects.deepEquals(this.QM, other.QM)
                && java.util.Objects.deepEquals(this.QN, other.QN)
                && java.util.Objects.deepEquals(this.QO, other.QO)
                && java.util.Objects.deepEquals(this.QP, other.QP);
        }

        public Rect lR() {
            return this.QM;
        }

        public float lS() {
            return this.QN;
        }

        public float lT() {
            return this.QO;
        }

        public String lU() {
            return this.QP;
        }
    }
}
