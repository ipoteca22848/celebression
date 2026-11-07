package ru.method.celebression.modules.impl.render.cosmetic;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_1074;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCardRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCatalog;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEntityPreviewRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewPose;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.player.CosmeticsModule;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.modules.impl.render.cosmetic.LabyModCapeService;
import ru.method.celebression.modules.impl.render.cosmetic.CelebressionCapeRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.CapePreviewRenderer;
import ru.method.celebression.utils.render.CobwebUiShaderRenderer;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public class CosmeticsScreen
extends class_437
implements MinecraftClientAccess {
    private static final CosmeticSlot[] Oy = new CosmeticSlot[]{CosmeticSlot.dr, CosmeticSlot.ds, CosmeticSlot.dt};
    private static final int Oz = 2;
    private final CosmeticsModule OA;
    private final class_437 OB;
    private final Animation OC = new Animation(320L, 1.0, true, Easing.aiZ);
    private final CosmeticPreviewPose OD = new CosmeticPreviewPose();
    private final CosmeticCardRenderer OE = new CosmeticCardRenderer();
    private CosmeticSlot OF = CosmeticSlot.dr;
    private float scroll;
    private String OG;
    private boolean closing;
    private float OH;
    private float OI;
    private float OJ;
    private float OL;
    private boolean OM;
    private boolean ON;
    private double OO;
    private double OP;
    private String OQ = "";
    private boolean OR;
    private long OS;
    private String OT = "";
    private final float[] OU = new float[4];
    private final float[] OV = new float[4];

    public CosmeticsScreen(CosmeticsModule cosmeticsModule, class_437 class_4372) {
        super((class_2561)class_2561.method_43471((String)"cosmetics.title"));
        this.OA = cosmeticsModule;
        this.OB = class_4372;
    }

    protected void method_25426() {
        this.scroll = 0.0f;
        this.closing = false;
        this.OD.reset();
        this.j(this.OF);
        this.OC.reset();
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        this.method_25420(class_3322, n, n2, f);
        CosmeticEntry cosmeticEntry = this.ls();
        this.OC.i(!this.closing);
        float f2 = class_3532.method_15363((float)this.OC.rk(), (float)0.0f, (float)1.0f);
        if (this.closing && f2 <= 0.02f) {
            this.lp();
            return;
        }
        int n3 = Math.round(255.0f * f2);
        int n4 = Math.round(255.0f * f2);
        Render2D.drawRoundedRect(class_3322.method_51448(), 0.0f, 0.0f, cosmeticEntry.OX, cosmeticEntry.OY, 0.0f, new Color(0, 0, 0, Math.round(58.0f * f2)));
        float f3 = 0.93f + 0.07f * f2;
        float f4 = (float)cosmeticEntry.OZ + (float)cosmeticEntry.Pb / 2.0f;
        float f5 = (float)cosmeticEntry.Pa + (float)cosmeticEntry.Pc / 2.0f;
        this.OM = false;
        this.f(n, n2);
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(f4, f5, 0.0f);
        class_3322.method_51448().method_22905(f3, f3, 1.0f);
        class_3322.method_51448().method_46416(-f4, -f5, 0.0f);
        this.a(class_3322, cosmeticEntry, n3);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(17.0f), class_1074.method_4662((String)"cosmetics.title", (Object[])new Object[0]), (float)cosmeticEntry.OZ + 20.0f, (float)cosmeticEntry.Pa + 14.0f, CosmeticsScreen.al(n4));
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.6f), class_1074.method_4662((String)"cosmetics.subtitle", (Object[])new Object[0]), (float)cosmeticEntry.OZ + 20.0f, (float)cosmeticEntry.Pa + 35.0f, CosmeticsScreen.al(Math.round(165.0f * (float)n4)));
        this.a(class_3322, cosmeticEntry, n, n2, n4);
        this.a(cosmeticEntry, n, n2);
        this.b(class_3322, cosmeticEntry, n, n2, n4);
        this.c(class_3322, cosmeticEntry, n, n2, n4);
        if (this.OF == CosmeticSlot.dr) {
            this.d(class_3322, cosmeticEntry, n, n2, n4);
        }
        class_3322.method_51448().method_22909();
        this.a(class_3322, cosmeticEntry, f3, f2);
        if (CosmeticsScreen.k(this.OF) && this.OE.lN()) {
            Celebression.getInstance().getCosmeticsService().b(this.OF, this.OE.lL());
        }
    }

    private void a(class_332 class_3322, CosmeticEntry cosmeticEntry, float f, float f2) {
        if (!this.OM || f2 <= 0.02f) {
            return;
        }
        float f3 = (float)cosmeticEntry.OZ + (float)cosmeticEntry.Pb / 2.0f;
        float f4 = (float)cosmeticEntry.Pa + (float)cosmeticEntry.Pc / 2.0f;
        int n = Math.round(f3 + (this.OH - f3) * f);
        int n2 = Math.round(f4 + (this.OI - f4) * f);
        int n3 = Math.round(this.OJ * f);
        int n4 = Math.round(this.OL * f);
        this.OD.a(class_3322, n, n2, n3, n4, 1.0f);
    }

    private void f(double d, double d2) {
        if (!this.ON) {
            return;
        }
        if (aiG.method_22683() == null || GLFW.glfwGetMouseButton((long)aiG.method_22683().method_4490(), (int)0) != 1) {
            this.ON = false;
            return;
        }
        float f = (float)(d - this.OO);
        float f2 = (float)(d2 - this.OP);
        this.OO = d;
        this.OP = d2;
        CosmeticEntityPreviewRenderer.dY += f * 0.6f;
        CosmeticEntityPreviewRenderer.dZ = Math.max(-35.0f, Math.min(35.0f, CosmeticEntityPreviewRenderer.dZ + f2 * 0.5f));
    }

    private void j(CosmeticSlot cosmeticSlot) {
        this.ON = false;
        CosmeticEntityPreviewRenderer.dY = cosmeticSlot == CosmeticSlot.dr || cosmeticSlot == CosmeticSlot.dt ? 180.0f : 0.0f;
        CosmeticEntityPreviewRenderer.dZ = 0.0f;
    }

    private static boolean k(CosmeticSlot cosmeticSlot) {
        return cosmeticSlot == CosmeticSlot.dt || cosmeticSlot == CosmeticSlot.du || cosmeticSlot == CosmeticSlot.ds;
    }

    private static String l(CosmeticSlot cosmeticSlot) {
        return switch (cosmeticSlot) {
            case CosmeticSlot.ds -> "cosmetics.hat.color";
            case CosmeticSlot.du -> "cosmetics.effect.color";
            default -> "cosmetics.wings.color";
        };
    }

    private ScrollState a(CosmeticEntry cosmeticEntry) {
        int n = CosmeticsScreen.k(this.OF) ? Math.round(this.OE.lO()) + 6 : 0;
        return new ScrollState((float)cosmeticEntry.Ph + 8.0f, (float)cosmeticEntry.Pe + 28.0f, (float)cosmeticEntry.Pi - 16.0f, (float)cosmeticEntry.Pg - 52.0f - (float)n);
    }

    private void a(class_332 class_3322, CosmeticEntry cosmeticEntry, int n) {
        Render2D.drawBlurredRect(class_3322.method_51448(), cosmeticEntry.OZ, cosmeticEntry.Pa, cosmeticEntry.Pb, cosmeticEntry.Pc, 18.0f, 22.0f, new Color(15, 18, 24, Math.round(150.0f * ((float)n / 255.0f))));
        Render2D.drawRoundedRect(class_3322.method_51448(), cosmeticEntry.OZ, cosmeticEntry.Pa, cosmeticEntry.Pb, cosmeticEntry.Pc, 18.0f, new Color(20, 23, 30, n));
        this.a(class_3322, cosmeticEntry.OZ, cosmeticEntry.Pa, cosmeticEntry.Pb, cosmeticEntry.Pc, 18.0f, 0.34f * ((float)n / 255.0f));
        Render2D.drawRoundedRect(class_3322.method_51448(), cosmeticEntry.OZ, cosmeticEntry.Pa, cosmeticEntry.Pb, cosmeticEntry.Pc, 18.0f, new Color(12, 14, 20, Math.round(166.0f * ((float)n / 255.0f))));
        Render2D.drawBorder(class_3322.method_51448(), cosmeticEntry.OZ, cosmeticEntry.Pa, cosmeticEntry.Pb, cosmeticEntry.Pc, 18.0f, 0.75f, new ColorRGBA(ColorUtils.alpha(Color.WHITE, Math.round(38.0f * ((float)n / 255.0f)))));
    }

    private void a(class_332 class_3322, CosmeticEntry cosmeticEntry, int n, int n2, int n3) {
        int n4 = cosmeticEntry.Pd;
        int n5 = cosmeticEntry.Pe;
        int n6 = cosmeticEntry.Pf;
        int n7 = cosmeticEntry.Pg;
        Render2D.drawRoundedRect(class_3322.method_51448(), n4, n5, n6, n7, 12.0f, new Color(255, 255, 255, 18));
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(8.7f), class_1074.method_4662((String)"cosmetics.categories", (Object[])new Object[0]), (float)n4 + 10.0f, (float)n5 + 9.0f, CosmeticsScreen.al(n3));
        int n8 = n5 + 30;
        for (CosmeticSlot cosmeticSlot : Oy) {
            boolean bl = cosmeticSlot == this.OF;
            boolean bl2 = MathUtils.c(n4 + 6, n8, n6 - 12, 28.0f, n, n2);
            Color color = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 96) : (bl2 ? new Color(255, 255, 255, 32) : new Color(255, 255, 255, 12));
            Render2D.drawRoundedRect(class_3322.method_51448(), n4 + 6, n8, n6 - 12, 28.0f, 8.0f, color);
            Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.6f), class_1074.method_4662((String)CosmeticsScreen.m(cosmeticSlot), (Object[])new Object[0]), (float)n4 + 12.0f, (float)n8 + 8.0f, CosmeticsScreen.al(n3));
            n8 += 32;
        }
    }

    private void b(class_332 class_3322, CosmeticEntry cosmeticEntry, int n, int n2, int n3) {
        int n4;
        int n5 = cosmeticEntry.Ph;
        int n6 = cosmeticEntry.Pe;
        int n7 = cosmeticEntry.Pi;
        int n8 = cosmeticEntry.Pg;
        Render2D.drawRoundedRect(class_3322.method_51448(), n5, n6, n7, n8, 12.0f, new Color(255, 255, 255, 16));
        Render2D.drawBorder(class_3322.method_51448(), n5, n6, n7, n8, 12.0f, 0.55f, new ColorRGBA(ColorUtils.alpha(ColorUtils.getPrimaryColor(), Math.round(55.0f * ((float)n3 / 255.0f)))));
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        CosmeticLoadout cosmeticLoadout = cosmeticsService.aL();
        String string = this.OG != null ? this.OG : cosmeticLoadout.f(this.OF);
        CosmeticDefinition cosmeticDefinition = CosmeticCatalog.a(string, this.OF);
        Object object = string != null && string.startsWith("laby:") ? ((n4 = this.lo().indexOf(string.substring(5))) >= 0 ? "Custom Cape #" + (n4 + 1) : "Custom Cape") : class_1074.method_4662((String)cosmeticDefinition.aC(), (Object[])new Object[0]);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(8.8f), class_1074.method_4662((String)"cosmetics.preview", (Object[])new Object[0]), (float)n5 + 10.0f, (float)n6 + 10.0f, CosmeticsScreen.al(n3));
        ScrollState scrollState = this.a(cosmeticEntry);
        int n9 = Math.round(scrollState.Pl);
        int n10 = Math.round(scrollState.Pm);
        int n11 = Math.round(scrollState.Pn);
        int n12 = Math.max(48, Math.round(scrollState.Po));
        float f = (float)(n10 + n12) + 4.0f;
        if (CosmeticsScreen.k(this.OF)) {
            this.OE.b(n, n2, n9, f, n11);
        }
        Render2D.drawRoundedRect(class_3322.method_51448(), n9, n10, n11, n12, 10.0f, new Color(8, 10, 16, Math.round(140.0f * ((float)n3 / 255.0f))));
        this.OD.i(this.OF);
        CosmeticLoadout cosmeticLoadout2 = cosmeticLoadout.b(this.OF, string);
        if (CosmeticsScreen.k(this.OF)) {
            int n13;
            int n14 = n13 = this.OE.lM() ? this.OE.lL() : cosmeticLoadout.g(this.OF);
            if (!this.OE.lM()) {
                this.OE.am(n13);
            }
            cosmeticLoadout2 = cosmeticLoadout2.a(this.OF, n13);
        }
        this.OD.e(cosmeticLoadout2);
        this.OH = n9;
        this.OI = n10;
        this.OJ = n11;
        this.OL = n12;
        this.OM = true;
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.2f), class_1074.method_4662((String)"cosmetics.preview.hint", (Object[])new Object[0]), (float)n9 + 6.0f, (float)(n10 + n12) - 11.0f, CosmeticsScreen.al(Math.round(120.0f * ((float)n3 / 255.0f))));
        if (CosmeticsScreen.k(this.OF)) {
            float f2 = this.OE.lO();
            float f3 = (float)n3 / 255.0f;
            float f4 = n9;
            float f5 = f - 6.0f;
            float f6 = n11;
            float f7 = f2 + 12.0f;
            Render2D.drawRoundedRect(class_3322.method_51448(), f4, f5, f6, f7, 9.0f, new Color(22, 24, 31, Math.round(255.0f * f3)));
            Color color = ColorUtils.getPrimaryColor();
            Render2D.drawBorder(class_3322.method_51448(), f4, f5, f6, f7, 9.0f, 1.0f, new ColorRGBA(ColorUtils.alpha(color, Math.round(160.0f * f3))));
            this.OE.a(class_3322, n9, f, n11, n, n2, n3, CosmeticsScreen.l(this.OF));
            if (this.OE.lN()) {
                cosmeticsService.b(this.OF, this.OE.lL());
            }
        }
        float f8 = Fonts.MEDIUM.getWidth((String)object, 6.8f);
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.8f), (String)object, (float)n5 + ((float)n7 - f8) / 2.0f, (float)(n6 + n8) - 14.0f, CosmeticsScreen.al(Math.round(190.0f * ((float)n3 / 255.0f))));
    }

    private int b(CosmeticEntry cosmeticEntry) {
        int n = this.OF == CosmeticSlot.dr ? 128 : 42;
        return Math.max(60, cosmeticEntry.Pg - n);
    }

    private void c(class_332 class_3322, CosmeticEntry cosmeticEntry, int n, int n2, int n3) {
        int n4 = cosmeticEntry.Pj;
        int n5 = cosmeticEntry.Pe;
        int n6 = cosmeticEntry.Pk;
        int n7 = cosmeticEntry.Pg;
        Render2D.drawRoundedRect(class_3322.method_51448(), n4, n5, n6, n7, 12.0f, new Color(255, 255, 255, 16));
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(9.4f), class_1074.method_4662((String)CosmeticsScreen.m(this.OF), (Object[])new Object[0]), (float)n4 + 10.0f, (float)n5 + 10.0f, CosmeticsScreen.al(n3));
        CosmeticLoadout cosmeticLoadout = Celebression.getInstance().getCosmeticsService().aL();
        String string = cosmeticLoadout.f(this.OF);
        List<CosmeticDefinition> list = CosmeticCatalog.b(this.OF);
        List<String> list2 = this.lo();
        int n8 = list.size() + list2.size();
        int n9 = Math.max(88, (n6 - 28) / 2);
        int n10 = 86;
        int n11 = n5 + 34;
        int n12 = this.b(cosmeticEntry);
        int n13 = (n8 + 2 - 1) / 2;
        int n14 = Math.max(0, n13 * (n10 + 8) - n12);
        this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)n14);
        Render2D.startScissor(class_3322, n4, n11, n6, n12);
        int n15 = n11 - Math.round(this.scroll);
        for (int i = 0; i < n8; ++i) {
            Object object;
            int n16 = i % 2;
            int n17 = i / 2;
            float f = (float)n4 + 10.0f + (float)n16 * ((float)n9 + 8.0f);
            float f2 = (float)n15 + (float)n17 * ((float)n10 + 8.0f);
            boolean bl = i >= list.size();
            String string2 = bl ? "laby:" + list2.get(i - list.size()) : list.get(i).aA();
            boolean bl2 = string2.equals(string);
            boolean bl3 = MathUtils.c(f, f2, n9, n10, n, n2);
            Color color = bl2 ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 110) : (bl3 ? new Color(255, 255, 255, 34) : new Color(255, 255, 255, 14));
            Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, n9, n10, 10.0f, color);
            if (bl2) {
                Render2D.drawBorder(class_3322.method_51448(), f, f2, n9, n10, 10.0f, 0.65f, new ColorRGBA(ColorUtils.alpha(ColorUtils.getPrimaryColor(), 180)));
            }
            if (bl) {
                String capeId = list2.get(i - list.size());
                this.a(class_3322, capeId, f, f2, n9, n3);
                object = "Custom Cape #" + (i - list.size() + 1);
                float f3 = 15.0f;
                float f4 = f + (float)n9 - f3 - 3.0f;
                float f5 = f2 + 3.0f;
                boolean bl4 = MathUtils.c(f4, f5, f3, f3, n, n2);
                Render2D.drawRoundedRect(class_3322.method_51448(), f4, f5, f3, f3, f3 / 2.0f, new Color(bl4 ? 235 : 60, bl4 ? 70 : 66, bl4 ? 76 : 72, Math.round((float)(bl4 ? 255 : 170) * ((float)n3 / 255.0f))));
                float f6 = Fonts.ICONSNEW.getWidth("W", 7.5f);
                Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(7.5f), "W", f4 + (f3 - f6) / 2.0f, f5 + (f3 - Fonts.ICONSNEW.getHeight(7.5f)) / 2.0f, new Color(255, 235, 236, n3));
            } else {
                CosmeticDefinition definition = list.get(i);
                if ("celebression_signature".equals(definition.aA())) {
                    this.a(class_3322, CelebressionCapeRenderer.G(), LabyModCapeService.CapeProvider.cq, f, f2, n9, n3, 0.0f, CelebressionCapeRenderer.H());
                } else if (!definition.W()) {
                    int n18 = 46;
                    int n19 = Math.round(f + (float)(n9 - n18) * 0.5f);
                    int n20 = Math.round(f2 + 8.0f);
                    Render2D.drawRoundedRect(class_3322.method_51448(), (float)n19 - 2.0f, (float)n20 - 2.0f, (float)n18 + 4.0f, (float)n18 + 4.0f, 8.0f, new Color(8, 10, 16, Math.round(120.0f * ((float)n3 / 255.0f))));
                    this.OD.a(class_3322, n19, n20, n18, definition, this.OF, (float)n3 / 255.0f);
                }
                object = class_1074.method_4662((String)definition.aC(), (Object[])new Object[0]);
            }
            float f7 = Fonts.MEDIUM.getWidth((String)object, 6.8f);
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.8f), (String)object, f + ((float)n9 - f7) / 2.0f, f2 + (float)n10 - 16.0f, CosmeticsScreen.al(n3));
        }
        Render2D.stopScissor(class_3322);
    }

    private List<String> lo() {
        return this.OF == CosmeticSlot.dr ? LabyModCapeService.D() : Collections.emptyList();
    }

    private void a(class_332 class_3322, String string, float f, float f2, float f3, int n) {
        LabyModCapeService.CapeProvider capeProvider = LabyModCapeService.aj(string);
        this.a(class_3322, LabyModCapeService.an(string), capeProvider, f, f2, f3, n, 1.0f, false);
    }

    private void a(class_332 class_3322, class_2960 class_29602, LabyModCapeService.CapeProvider capeProvider, float f, float f2, float f3, int n, float f4, boolean bl) {
        class_4587 class_45872 = class_3322.method_51448();
        float f5 = (float)n / 255.0f;
        int n2 = 50;
        int n3 = Math.round((float)n2 * 10.0f / 16.0f);
        float f6 = Math.round(f + (f3 - (float)n3) / 2.0f);
        float f7 = f2 + 7.0f;
        Color color = ColorUtils.getPrimaryColor();
        Render2D.drawRoundedRect(class_45872, f6 - 6.0f, f7 - 4.0f, (float)n3 + 12.0f, (float)n2 + 8.0f, 8.0f, new Color(9, 11, 17, Math.round(165.0f * f5)));
        Render2D.drawRoundedRect(class_45872, f6 - 3.0f, f7 + (float)n2 * 0.18f, (float)n3 + 6.0f, (float)n2 * 0.7f, 7.0f, ColorUtils.alpha(color, Math.round(26.0f * f5)));
        Render2D.drawRoundedRect(class_45872, f6 + 1.0f, f7 + 3.0f, n3, n2, 4.0f, new Color(0, 0, 0, Math.round(110.0f * f5)));
        if (capeProvider == LabyModCapeService.CapeProvider.cq && class_29602 != null) {
            if (bl) {
                CapePreviewRenderer.render(class_45872, f6, f7, n3, n2, 4.0f, class_29602, f5, f4, 0.0f, 0.0f, 1.0f, 1.0f);
            } else {
                CapePreviewRenderer.render(class_45872, f6, f7, n3, n2, 4.0f, class_29602, f5, f4);
            }
        } else {
            this.a(class_45872, capeProvider, f6, f7, n3, n2, f5, color);
        }
        Render2D.drawBorder(class_45872, f6, f7, n3, n2, 4.0f, 0.7f, new ColorRGBA(ColorUtils.alpha(color, Math.round(90.0f * f5))));
    }

    private void a(class_4587 class_45872, LabyModCapeService.CapeProvider capeProvider, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 4.0f, new Color(18, 21, 28, Math.round(230.0f * f5)));
        if (capeProvider == LabyModCapeService.CapeProvider.cs) {
            Color color2 = new Color(232, 96, 96, Math.round(190.0f * f5));
            Instance instance = Fonts.ROUNDBOLD.getFont(9.0f);
            Render2D.drawFont(class_45872, instance, "!", f + (f3 - instance.getWidth("!")) / 2.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, color2);
            Render2D.drawRoundedRect(class_45872, f + 4.0f, f2 + f4 * 0.62f, f3 - 8.0f, 1.0f, 0.5f, ColorUtils.alpha(color2, Math.round(90.0f * f5)));
            return;
        }
        float f6 = (float)(System.currentTimeMillis() % 1200L) / 1200.0f;
        float f7 = f4 * 0.28f;
        float f8 = f2 - f7 + f6 * (f4 + f7);
        float f9 = Math.max(f2, f8);
        float f10 = Math.min(f2 + f4, f8 + f7);
        if (f10 > f9) {
            Render2D.drawRoundedRect(class_45872, f + 2.0f, f9, f3 - 4.0f, f10 - f9, 3.0f, ColorUtils.alpha(color, Math.round(55.0f * f5)));
        }
    }

    private void a(CosmeticEntry cosmeticEntry, int n, int n2) {
        this.OG = null;
        List<CosmeticDefinition> list = CosmeticCatalog.b(this.OF);
        int n3 = Math.max(88, (cosmeticEntry.Pk - 28) / 2);
        int n4 = 86;
        int n5 = cosmeticEntry.Pe + 34;
        int n6 = this.b(cosmeticEntry);
        if (!MathUtils.c(cosmeticEntry.Pj, n5, cosmeticEntry.Pk, n6, n, n2)) {
            return;
        }
        List<String> list2 = this.lo();
        int n7 = list.size() + list2.size();
        int n8 = n5 - Math.round(this.scroll);
        for (int i = 0; i < n7; ++i) {
            int n9 = i % 2;
            float f = (float)cosmeticEntry.Pj + 10.0f + (float)n9 * ((float)n3 + 8.0f);
            int n10 = i / 2;
            float f2 = (float)n8 + (float)n10 * ((float)n4 + 8.0f);
            if (!MathUtils.c(f, f2, n3, n4, n, n2)) continue;
            this.OG = i < list.size() ? list.get(i).aA() : "laby:" + list2.get(i - list.size());
            return;
        }
    }

    public boolean method_25402(double d, double d2, int n) {
        int n2;
        if (n != 0) {
            return super.method_25402(d, d2, n);
        }
        CosmeticEntry cosmeticEntry = this.ls();
        int n3 = cosmeticEntry.Pe + 30;
        for (CosmeticSlot cosmeticSlot : Oy) {
            if (MathUtils.c(cosmeticEntry.Pd + 6, n3, cosmeticEntry.Pf - 12, 28.0f, (float)d, (float)d2)) {
                this.OF = cosmeticSlot;
                this.j(cosmeticSlot);
                this.scroll = 0.0f;
                this.OG = null;
                this.OR = false;
                this.OD.i(cosmeticSlot);
                return true;
            }
            n3 += 32;
        }
        if (this.OF == CosmeticSlot.dr) {
            if (MathUtils.c(this.OV[0], this.OV[1], this.OV[2], this.OV[3], (float)d, (float)d2)) {
                this.lr();
                return true;
            }
            if (MathUtils.c(this.OU[0], this.OU[1], this.OU[2], this.OU[3], (float)d, (float)d2)) {
                this.OR = true;
                return true;
            }
            this.OR = false;
        }
        if (CosmeticsScreen.k(this.OF)) {
            ScrollState scrollState = this.a(cosmeticEntry);
            int n4 = Math.round(scrollState.Pl);
            int n5 = Math.round(scrollState.Pm);
            int n6 = Math.round(scrollState.Pn);
            n2 = Math.max(48, Math.round(scrollState.Po));
            float f = (float)(n5 + n2) + 4.0f;
            if (this.OE.a(n4, f, (float)n6, d, d2, n)) {
                return true;
            }
        }
        ScrollState scrollState = this.a(cosmeticEntry);
        if (MathUtils.c(scrollState.Pl, scrollState.Pm, scrollState.Pn, scrollState.Po, (float)d, (float)d2)) {
            this.ON = true;
            this.OO = d;
            this.OP = d2;
            return true;
        }
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        List<CosmeticDefinition> list = CosmeticCatalog.b(this.OF);
        List<String> list2 = this.lo();
        n2 = list.size() + list2.size();
        int n7 = Math.max(88, (cosmeticEntry.Pk - 28) / 2);
        int n8 = 86;
        int n9 = cosmeticEntry.Pe + 34 - Math.round(this.scroll);
        for (int i = 0; i < n2; ++i) {
            int n10 = i % 2;
            float f = (float)cosmeticEntry.Pj + 10.0f + (float)n10 * ((float)n7 + 8.0f);
            int n11 = i / 2;
            float f2 = (float)n9 + (float)n11 * ((float)n8 + 8.0f);
            if (!MathUtils.c(f, f2, n7, n8, (float)d, (float)d2)) continue;
            if (i < list.size()) {
                cosmeticsService.c(this.OF, list.get(i).aA());
            } else {
                String string = list2.get(i - list.size());
                float f3 = 15.0f;
                float f4 = f + (float)n7 - f3 - 3.0f;
                float f5 = f2 + 3.0f;
                if (MathUtils.c(f4, f5, f3, f3, (float)d, (float)d2)) {
                    LabyModCapeService.ai(string);
                    if (("laby:" + string).equals(cosmeticsService.aL().f(this.OF)) && !list.isEmpty()) {
                        cosmeticsService.c(this.OF, list.get(0).aA());
                    }
                } else {
                    cosmeticsService.au(string);
                }
            }
            return true;
        }
        return super.method_25402(d, d2, n);
    }

    public boolean method_25406(double d, double d2, int n) {
        if (n == 0) {
            this.ON = false;
        }
        if (n == 0 && this.OE.an(n)) {
            Celebression.getInstance().getCosmeticsService().b(this.OF, this.OE.lL());
            return true;
        }
        return super.method_25406(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        CosmeticEntry cosmeticEntry = this.ls();
        int n = cosmeticEntry.Pe + 34;
        int n2 = this.b(cosmeticEntry);
        if (MathUtils.c(cosmeticEntry.Pj, n, cosmeticEntry.Pk, n2, (float)d, (float)d2)) {
            this.scroll -= (float)d4 * 16.0f;
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.OR) {
            boolean bl;
            boolean bl2 = bl = (n3 & 2) != 0;
            if (n == 256) {
                this.OR = false;
                return true;
            }
            if (n == 257 || n == 335) {
                this.lr();
                return true;
            }
            if (n == 259) {
                if (!this.OQ.isEmpty()) {
                    this.OQ = this.OQ.substring(0, this.OQ.length() - 1);
                }
                return true;
            }
            if (bl && n == 86) {
                String string = CosmeticsScreen.aiG.field_1774.method_1460();
                if (string != null) {
                    this.OQ = (this.OQ + string.replace("\n", " ").replace("\r", "")).trim();
                }
                if (this.OQ.length() > 300) {
                    this.OQ = this.OQ.substring(0, 300);
                }
                return true;
            }
            if (bl && n == 65) {
                this.OQ = "";
                return true;
            }
            return true;
        }
        if (this.OE.ak(n)) {
            return true;
        }
        if (n == 256) {
            this.method_25419();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.OR && c >= ' ' && c != '\u007f') {
            if (this.OQ.length() < 300) {
                this.OQ = this.OQ + c;
            }
            return true;
        }
        return super.method_25400(c, n);
    }

    public void method_25419() {
        if (!this.closing) {
            this.closing = true;
        }
    }

    private void lp() {
        this.OA.hZ();
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.OB);
        }
    }

    public void lq() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.OB);
        }
    }

    private static Color al(int n) {
        return new Color(236, 239, 246, Math.max(0, Math.min(255, n)));
    }

    private void d(class_332 class_3322, CosmeticEntry cosmeticEntry, int n, int n2, int n3) {
        float f;
        class_4587 class_45872 = class_3322.method_51448();
        float f2 = (float)n3 / 255.0f;
        float f3 = 26.0f;
        float f4 = (float)(cosmeticEntry.Pe + cosmeticEntry.Pg) - 74.0f;
        float f5 = 66.0f;
        float f6 = (float)cosmeticEntry.Pj + 10.0f;
        float f7 = (float)cosmeticEntry.Pk - 20.0f - f5 - 8.0f;
        Render2D.drawFont(class_45872, Fonts.ROUNDBOLD.getFont(8.2f), "\u041f\u043b\u0430\u0449 \u0441 laby.net", f6, f4 - 15.0f, CosmeticsScreen.al(n3));
        Render2D.drawRoundedRect(class_45872, f6, f4, f7, f3, 7.0f, new Color(255, 255, 255, this.OR ? 26 : 15));
        Render2D.drawBorder(class_45872, f6, f4, f7, f3, 7.0f, 0.7f, new ColorRGBA(ColorUtils.alpha(ColorUtils.getPrimaryColor(), Math.round((float)(this.OR ? 155 : 55) * f2))));
        Instance instance = Fonts.MEDIUM.getFont(7.2f);
        boolean bl = this.OQ.isEmpty() && !this.OR;
        String string = bl ? "laby.net/capes/\u2026" : CosmeticsScreen.a(instance, this.OQ, f7 - 14.0f);
        Render2D.drawFont(class_45872, instance, string, f6 + 7.0f, f4 + f3 / 2.0f - instance.getHeight() / 2.0f, CosmeticsScreen.al(bl ? Math.round(110.0f * f2) : n3));
        if (this.OR) {
            f = f6 + 7.0f + instance.getWidth(string) + 1.0f;
            Render2D.drawRoundedRect(class_45872, f, f4 + 6.0f, 1.0f, f3 - 12.0f, 0.5f, CosmeticsScreen.al(n3));
        }
        this.OU[0] = f6;
        this.OU[1] = f4;
        this.OU[2] = f7;
        this.OU[3] = f3;
        f = f6 + f7 + 8.0f;
        boolean bl2 = MathUtils.c(f, f4, f5, f3, n, n2);
        Render2D.drawRoundedRect(class_45872, f, f4, f5, f3, 7.0f, ColorUtils.alpha(ColorUtils.getPrimaryColor(), bl2 ? 210 : 150));
        Instance instance2 = Fonts.ROUNDBOLD.getFont(7.4f);
        Render2D.drawFont(class_45872, instance2, "\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c", f + (f5 - instance2.getWidth("\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c")) / 2.0f, f4 + f3 / 2.0f - instance2.getHeight() / 2.0f, new Color(10, 17, 13));
        this.OV[0] = f;
        this.OV[1] = f4;
        this.OV[2] = f5;
        this.OV[3] = f3;
        String string2 = System.currentTimeMillis() < this.OS ? this.OT : "\u0412\u0441\u0442\u0430\u0432\u044c \u0441\u0441\u044b\u043b\u043a\u0443 \u0438\u043b\u0438 ID \u043f\u043b\u0430\u0449\u0430 \u0441 laby.net";
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(6.4f), string2, f6, f4 + f3 + 6.0f, CosmeticsScreen.al(Math.round(130.0f * f2)));
    }

    private void lr() {
        String string = LabyModCapeService.am(this.OQ);
        if (string == null) {
            this.OT = "\u041d\u0435 \u043f\u043e\u0445\u043e\u0436\u0435 \u043d\u0430 \u0441\u0441\u044b\u043b\u043a\u0443/ID laby.net";
            this.OS = System.currentTimeMillis() + 4000L;
            return;
        }
        Celebression.getInstance().getCosmeticsService().at(this.OQ);
        this.OT = "\u041f\u043b\u0430\u0449 \u043f\u0440\u0438\u043c\u0435\u043d\u0451\u043d, \u0433\u0440\u0443\u0437\u0438\u0442\u0441\u044f\u2026";
        this.OS = System.currentTimeMillis() + 4000L;
        this.OR = false;
    }

    private static String a(Instance instance, String string, float f) {
        if (instance.getWidth(string) <= f) {
            return string;
        }
        String string2 = "\u2026";
        String string3 = string;
        while (string3.length() > 1 && instance.getWidth(string2 + string3) > f) {
            string3 = string3.substring(1);
        }
        return string2 + string3;
    }

    private static String m(CosmeticSlot cosmeticSlot) {
        return switch (cosmeticSlot) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case CosmeticSlot.dr -> "cosmetics.slot.cape";
            case CosmeticSlot.ds -> "cosmetics.slot.hat";
            case CosmeticSlot.dt -> "cosmetics.slot.wings";
            case CosmeticSlot.du -> "cosmetics.slot.effect";
        };
    }

    private void a(class_332 class_3322, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.min(f5, Math.min(f3, f4) * 0.5f);
        Color color = ColorUtils.getPrimaryColor();
        if (f6 <= 0.0f) {
            return;
        }
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f + f7, f2, f3 - f7 * 2.0f, f7, color, f6);
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f, f2 + f7, f3, f4 - f7 * 2.0f, color, f6);
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f + f7, f2 + f4 - f7, f3 - f7 * 2.0f, f7, color, f6);
    }

    private CosmeticEntry ls() {
        int n;
        int n2 = Math.max(1, this.field_22789);
        int n3 = Math.max(1, this.field_22790);
        if ((n2 <= 1 || n3 <= 1) && aiG != null && aiG.method_22683() != null) {
            n2 = aiG.method_22683().method_4486();
            n3 = aiG.method_22683().method_4502();
        }
        int n4 = Math.min(Math.min(760, n2 - 16), Math.round((float)n2 * 0.9f));
        int n5 = Math.min(Math.min(460, n3 - 16), Math.round((float)n3 * 0.86f));
        int n6 = Math.round((float)(n2 - n4) * 0.5f);
        int n7 = Math.round((float)(n3 - n5) * 0.5f);
        int n8 = 56;
        int n9 = n7 + n8;
        int n10 = Math.max(140, n5 - n8 - 12);
        int n11 = 14;
        int n12 = 10;
        int n13 = Math.max(60, n4 - n11 * 2 - n12 * 2);
        int n14 = Math.min(138, Math.max(84, Math.round((float)n13 * 0.2f)));
        if (n14 + (n = Math.min(320, Math.max(120, Math.round((float)n13 * 0.42f)))) > n13 - 90) {
            n = Math.max(96, n13 - n14 - 90);
        }
        int n15 = Math.max(70, n13 - n14 - n);
        int n16 = n6 + n11;
        int n17 = n16 + n14 + n12;
        int n18 = n17 + n + n12;
        return new CosmeticEntry(n2, n3, n6, n7, n4, n5, n16, n9, n14, n10, n17, n, n18, n15);
    }

    static final class CosmeticEntry {
        final int OX;
        final int OY;
        final int OZ;
        final int Pa;
        final int Pb;
        final int Pc;
        final int Pd;
        final int Pe;
        final int Pf;
        final int Pg;
        final int Ph;
        final int Pi;
        final int Pj;
        final int Pk;

        CosmeticEntry(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, int n13, int n14) {
            this.OX = n;
            this.OY = n2;
            this.OZ = n3;
            this.Pa = n4;
            this.Pb = n5;
            this.Pc = n6;
            this.Pd = n7;
            this.Pe = n8;
            this.Pf = n9;
            this.Pg = n10;
            this.Ph = n11;
            this.Pi = n12;
            this.Pj = n13;
            this.Pk = n14;
        }

        @Override
        public final String toString() {
            return "CosmeticEntry[" + "screenW=" + this.OX + ", " + "screenH=" + this.OY + ", " + "x=" + this.OZ + ", " + "y=" + this.Pa + ", " + "w=" + this.Pb + ", " + "h=" + this.Pc + ", " + "sidebarX=" + this.Pd + ", " + "contentY=" + this.Pe + ", " + "sidebarW=" + this.Pf + ", " + "contentH=" + this.Pg + ", " + "previewX=" + this.Ph + ", " + "previewW=" + this.Pi + ", " + "gridX=" + this.Pj + ", " + "gridW=" + this.Pk + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.OX, this.OY, this.OZ, this.Pa, this.Pb, this.Pc, this.Pd, this.Pe, this.Pf, this.Pg, this.Ph, this.Pi, this.Pj, this.Pk);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof CosmeticEntry other)) return false;
            return java.util.Objects.deepEquals(this.OX, other.OX)
                && java.util.Objects.deepEquals(this.OY, other.OY)
                && java.util.Objects.deepEquals(this.OZ, other.OZ)
                && java.util.Objects.deepEquals(this.Pa, other.Pa)
                && java.util.Objects.deepEquals(this.Pb, other.Pb)
                && java.util.Objects.deepEquals(this.Pc, other.Pc)
                && java.util.Objects.deepEquals(this.Pd, other.Pd)
                && java.util.Objects.deepEquals(this.Pe, other.Pe)
                && java.util.Objects.deepEquals(this.Pf, other.Pf)
                && java.util.Objects.deepEquals(this.Pg, other.Pg)
                && java.util.Objects.deepEquals(this.Ph, other.Ph)
                && java.util.Objects.deepEquals(this.Pi, other.Pi)
                && java.util.Objects.deepEquals(this.Pj, other.Pj)
                && java.util.Objects.deepEquals(this.Pk, other.Pk);
        }

        public int kR() {
            return this.OX;
        }

        public int kS() {
            return this.OY;
        }

        public int x() {
            return this.OZ;
        }

        public int y() {
            return this.Pa;
        }

        public int kT() {
            return this.Pb;
        }

        public int kU() {
            return this.Pc;
        }

        public int lt() {
            return this.Pd;
        }

        public int lu() {
            return this.Pe;
        }

        public int lv() {
            return this.Pf;
        }

        public int lw() {
            return this.Pg;
        }

        public int lx() {
            return this.Ph;
        }

        public int ly() {
            return this.Pi;
        }

        public int lz() {
            return this.Pj;
        }

        public int lA() {
            return this.Pk;
        }
    }

    static final class ScrollState {
        final float Pl;
        final float Pm;
        final float Pn;
        final float Po;

        ScrollState(float f, float f2, float f3, float f4) {
            this.Pl = f;
            this.Pm = f2;
            this.Pn = f3;
            this.Po = f4;
        }

        @Override
        public final String toString() {
            return "ScrollState[" + "x=" + this.Pl + ", " + "y=" + this.Pm + ", " + "w=" + this.Pn + ", " + "h=" + this.Po + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Pl, this.Pm, this.Pn, this.Po);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ScrollState other)) return false;
            return java.util.Objects.deepEquals(this.Pl, other.Pl)
                && java.util.Objects.deepEquals(this.Pm, other.Pm)
                && java.util.Objects.deepEquals(this.Pn, other.Pn)
                && java.util.Objects.deepEquals(this.Po, other.Po);
        }

        public float x() {
            return this.Pl;
        }

        public float y() {
            return this.Pm;
        }

        public float lB() {
            return this.Pn;
        }

        public float lC() {
            return this.Po;
        }
    }
}
