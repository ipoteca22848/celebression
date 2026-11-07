package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2902;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.builders.Builder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltText;
import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.Render3D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ItemRadiusModule
extends Module {
    private final BooleanSetting BK = new BooleanSetting("modules.settings.itemradius.ender_eye", true);
    private final ColorSetting BL = new ColorSetting("modules.settings.itemradius.ender_eye_color", Color.WHITE, this.BK::jT);
    private final BooleanSetting BM = new BooleanSetting("modules.settings.itemradius.sugar", true);
    private final ColorSetting BN = new ColorSetting("modules.settings.itemradius.sugar_color", Color.WHITE, this.BM::jT);
    private final BooleanSetting BO = new BooleanSetting("modules.settings.itemradius.netherite_scrap", true);
    private final ColorSetting BP = new ColorSetting("modules.settings.itemradius.netherite_scrap_color", Color.WHITE, this.BO::jT);
    private final BooleanSetting BQ = new BooleanSetting("modules.settings.itemradius.dragon_trap", false, this.BO::jT);
    private final BooleanSetting BR = new BooleanSetting("modules.settings.itemradius.dried_kelp", true);
    private final ColorSetting BS = new ColorSetting("modules.settings.itemradius.dried_kelp_color", Color.WHITE, this.BR::jT);
    private final BooleanSetting BT = new BooleanSetting("modules.settings.itemradius.snowball", true);
    private final ColorSetting BU = new ColorSetting("modules.settings.itemradius.snowball_color", Color.WHITE, this.BT::jT);
    private final BooleanSetting BV = new BooleanSetting("modules.settings.itemradius.explosive_potion", true);
    private final SliderSetting BW = new SliderSetting("modules.settings.itemradius.explosive_potion_radius", 4.0f, 1.0f, 30.0f, 0.5f, this.BV::jT);
    private final ColorSetting BX = new ColorSetting("modules.settings.itemradius.explosive_potion_color", Color.WHITE, this.BV::jT);
    private final BooleanSetting BY = new BooleanSetting("modules.settings.itemradius.proximity_color", true);
    private final ColorSetting BZ = new ColorSetting("modules.settings.itemradius.proximity_color_setting", new Color(255, 0, 255, 255), this.BY::jT);
    private class_243 Ca;
    private int Cb = -1;
    private float Cc = Float.NaN;
    private float Cd = Float.NaN;

    public ItemRadiusModule() {
        super("ItemRadius", ModuleCategory.ql);
    }

    @EventHandler
    private void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        class_243 class_2432;
        if (ItemRadiusModule.fullNullCheck()) {
            return;
        }
        class_243 class_2433 = ItemRadiusModule.aiG.field_1724.method_19538().method_1031(0.0, -0.1, 0.0);
        if (((Boolean)this.BK.jT()).booleanValue() && this.a(class_1802.field_8449)) {
            this.a(matrixStackStage, class_2433, 14.0, (Color)this.BL.jT());
        }
        if (((Boolean)this.BM.jT()).booleanValue() && this.a(class_1802.field_8479)) {
            this.a(matrixStackStage, class_2433, 14.0, (Color)this.BN.jT());
        }
        if (((Boolean)this.BO.jT()).booleanValue() && this.a(class_1802.field_22021)) {
            double d = (Boolean)this.BQ.jT() != false ? 3.5 : 2.5;
            class_243 class_2434 = this.d(ItemRadiusModule.aiG.field_1724.method_19538()).method_1031(0.0, d - 0.5, 0.0);
            this.a(matrixStackStage, class_2434, d, d, d, (Color)this.BP.jT(), 2.5);
        }
        if (((Boolean)this.BR.jT()).booleanValue() && this.a(class_1802.field_8551)) {
            this.a(matrixStackStage, (Color)this.BS.jT());
        }
        if (((Boolean)this.BT.jT()).booleanValue() && this.a(class_1802.field_8543)) {
            class_243 class_2435 = this.iY();
            this.a(matrixStackStage, class_2435, 12.0, (Color)this.BU.jT());
        }
        if (((Boolean)this.BV.jT()).booleanValue() && this.a(class_1802.field_8436) && (class_2432 = this.iX()) != null) {
            double d = ((Float)this.BW.jT()).floatValue();
            this.a(matrixStackStage, class_2432, d, (Color)this.BX.jT());
            this.a(matrixStackStage, class_2432, d);
        }
    }

    private class_243 iX() {
        float f = ItemRadiusModule.aiG.field_1724.method_36454();
        float f2 = ItemRadiusModule.aiG.field_1724.method_36455();
        if (this.Ca != null && ItemRadiusModule.aiG.field_1724.field_6012 == this.Cb && Math.abs(class_3532.method_15393((float)(f - this.Cc))) <= 0.5f && Math.abs(f2 - this.Cd) <= 0.5f) {
            return this.Ca;
        }
        this.Cb = ItemRadiusModule.aiG.field_1724.field_6012;
        this.Cc = f;
        this.Cd = f2;
        this.Ca = this.l(f, f2);
        return this.Ca;
    }

    private class_243 l(float f, float f2) {
        double d;
        double d2;
        if (ItemRadiusModule.aiG.field_1687 == null) {
            return null;
        }
        double d3 = Math.PI / 180;
        double d4 = -Math.sin((double)f * (Math.PI / 180)) * Math.cos((double)f2 * (Math.PI / 180));
        double d5 = Math.sqrt(d4 * d4 + (d2 = -Math.sin(((double)f2 - 20.0) * (Math.PI / 180))) * d2 + (d = Math.cos((double)f * (Math.PI / 180)) * Math.cos((double)f2 * (Math.PI / 180))) * d);
        if (d5 == 0.0) {
            return null;
        }
        double d6 = 0.5;
        d4 = d4 / d5 * d6;
        d2 = d2 / d5 * d6;
        d = d / d5 * d6;
        class_243 class_2432 = ItemRadiusModule.aiG.field_1724.method_18798();
        d4 += class_2432.field_1352;
        d += class_2432.field_1350;
        if (!ItemRadiusModule.aiG.field_1724.method_24828()) {
            d2 += class_2432.field_1351;
        }
        double d7 = ItemRadiusModule.aiG.field_1724.method_23317();
        double d8 = ItemRadiusModule.aiG.field_1724.method_23320() - 0.1;
        double d9 = ItemRadiusModule.aiG.field_1724.method_23321();
        for (int i = 0; i < 200; ++i) {
            d2 -= 0.03;
            class_243 class_2433 = new class_243(d7, d8, d9);
            boolean bl = ItemRadiusModule.aiG.field_1687.method_8320(class_2338.method_49637((double)d7, (double)d8, (double)d9)).method_27852(class_2246.field_10382);
            double d10 = bl ? 0.8 : 0.99;
            class_243 class_2434 = class_2433.method_1031(d4 *= d10, d2 *= d10, d *= d10);
            class_3965 class_39652 = ItemRadiusModule.aiG.field_1687.method_17742(new class_3959(class_2433, class_2434, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)ItemRadiusModule.aiG.field_1724));
            if (class_39652 != null && class_39652.method_17783() == class_239.class_240.field_1332) {
                return class_39652.method_17784();
            }
            d7 = class_2434.field_1352;
            d8 = class_2434.field_1351;
            d9 = class_2434.field_1350;
            if (!(d8 < (double)ItemRadiusModule.aiG.field_1687.method_31607())) continue;
            return null;
        }
        return null;
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, double d) {
        if (d <= 0.0) {
            return;
        }
        class_4184 class_41842 = ItemRadiusModule.aiG.field_1773.method_19418();
        class_243 class_2433 = class_41842.method_19326();
        class_4587 class_45872 = matrixStackStage.l();
        MsdfFont msdfFont = Fonts.FORT.font();
        float f = 10.8f;
        boolean bl = false;
        for (class_1657 class_16572 : ItemRadiusModule.aiG.field_1687.method_18456()) {
            int n;
            double d2;
            if (class_16572 == ItemRadiusModule.aiG.field_1724 || (d2 = Math.sqrt(class_16572.method_5707(class_2432))) > d || (n = (int)Math.round(Math.max(0.0, 1.0 - d2 / d) * 100.0)) <= 0) continue;
            if (!bl) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask((boolean)false);
                RenderSystem.disableCull();
                bl = true;
            }
            class_243 class_2434 = new class_243(class_16572.method_23317(), class_16572.method_23318() + (double)class_16572.method_17682() + 0.35, class_16572.method_23321());
            class_45872.method_22903();
            class_45872.method_22904(class_2434.field_1352 - class_2433.field_1352, class_2434.field_1351 - class_2433.field_1351, class_2434.field_1350 - class_2433.field_1350);
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(-class_41842.method_19330()));
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(class_41842.method_19329()));
            class_45872.method_22905(-0.025f, -0.025f, 0.025f);
            String string = n + "%";
            float f2 = msdfFont.getWidth(string, f);
            BuiltText builtText = (BuiltText)Builder.text().font(msdfFont).text(string).size(f).thickness(0.05f).color(new Color(255, 255, 255, 255)).outline(new Color(0, 0, 0, 255), 0.18f).build();
            Matrix4f matrix4f = class_45872.method_23760().method_23761();
            try {
                builtText.render(matrix4f, -f2 / 2.0f, -f / 2.0f);
            }
            catch (Throwable throwable) {
            }
            class_45872.method_22909();
        }
        if (bl) {
            RenderSystem.enableCull();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
        }
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, Color color) {
        float f = (float)Math.round(ItemRadiusModule.aiG.field_1724.method_5705(1.0f) / 45.0f) * 45.0f;
        double d = -Math.sin(Math.toRadians(f));
        double d2 = Math.cos(Math.toRadians(f));
        float f2 = ItemRadiusModule.aiG.field_1724.method_36455();
        class_243 class_2432 = this.d(ItemRadiusModule.aiG.field_1724.method_19538());
        if (f2 > 45.0f) {
            this.a(matrixStackStage, class_2432.method_1031(0.0, -1.5, 0.0), 2.5, 1.0, 2.5, color, 2.5);
            return;
        }
        if (f2 < -45.0f) {
            this.a(matrixStackStage, class_2432.method_1031(0.0, 2.5, 0.0), 2.5, 1.0, 2.5, color, 2.5);
            return;
        }
        double d3 = d2;
        double d4 = -d;
        class_243 class_2433 = class_2432.method_1031(d * 3.0, 0.0, d2 * 3.0);
        this.a(matrixStackStage, class_2433, d3, d4, 2.5, 4.0, color);
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, double d, double d2, double d3, double d4, Color color) {
        int n;
        int n2;
        int n3;
        Color color2 = this.a(class_2432, d3, color);
        double d5 = Math.floor(ItemRadiusModule.aiG.field_1724.method_23318());
        double d6 = d5 + d4;
        HashSet<Long> hashSet = new HashSet<Long>();
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MIN_VALUE;
        int n6 = Integer.MAX_VALUE;
        int n7 = Integer.MIN_VALUE;
        for (double d7 = -d3; d7 <= d3 + 1.0E-6; d7 += 0.05) {
            n3 = (int)Math.floor(class_2432.field_1352 + d * d7);
            n2 = (int)Math.floor(class_2432.field_1350 + d2 * d7);
            hashSet.add(ItemRadiusModule.m(n3, n2));
            n4 = Math.min(n4, n3);
            n5 = Math.max(n5, n3);
            n6 = Math.min(n6, n2);
            n7 = Math.max(n7, n2);
        }
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            long l = (Long)iterator.next();
            n2 = (int)(l >> 32);
            if (!hashSet.contains(ItemRadiusModule.m(n2 - 1, n = (int)(l & 0xFFFFFFFFL)))) {
                this.a(matrixStackStage, color2, n2, n, n2, n + 1, d5);
                this.a(matrixStackStage, color2, n2, n, n2, n + 1, d6);
            }
            if (!hashSet.contains(ItemRadiusModule.m(n2 + 1, n))) {
                this.a(matrixStackStage, color2, n2 + 1, n, n2 + 1, n + 1, d5);
                this.a(matrixStackStage, color2, n2 + 1, n, n2 + 1, n + 1, d6);
            }
            if (!hashSet.contains(ItemRadiusModule.m(n2, n - 1))) {
                this.a(matrixStackStage, color2, n2, n, n2 + 1, n, d5);
                this.a(matrixStackStage, color2, n2, n, n2 + 1, n, d6);
            }
            if (hashSet.contains(ItemRadiusModule.m(n2, n + 1))) continue;
            this.a(matrixStackStage, color2, n2, n + 1, n2 + 1, n + 1, d5);
            this.a(matrixStackStage, color2, n2, n + 1, n2 + 1, n + 1, d6);
        }
        for (int i = n4; i <= n5 + 1; ++i) {
            for (int j = n6; j <= n7 + 1; ++j) {
                boolean bl;
                n3 = hashSet.contains(ItemRadiusModule.m(i - 1, j - 1)) ? 1 : 0;
                int n8 = (n3 != 0 ? 1 : 0) + ((n2 = (int)(hashSet.contains(ItemRadiusModule.m(i, j - 1)) ? 1 : 0)) != 0 ? 1 : 0) + ((n = (int)(hashSet.contains(ItemRadiusModule.m(i - 1, j)) ? 1 : 0)) != 0 ? 1 : 0) + ((bl = hashSet.contains(ItemRadiusModule.m(i, j))) ? 1 : 0);
                if (n8 != 1 && n8 != 3 && (n8 != 2 || (n3 == 0 || !bl) && (n2 == 0 || n == 0))) continue;
                Render3D.drawLine(matrixStackStage.l(), new class_243((double)i, d5, (double)j), new class_243((double)i, d6, (double)j), color2, 8.0f, false);
            }
        }
    }

    private static long m(int n, int n2) {
        return (long)n << 32 ^ (long)n2 & 0xFFFFFFFFL;
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, Color color, int n, int n2, int n3, int n4, double d) {
        Render3D.drawLine(matrixStackStage.l(), new class_243((double)n, d, (double)n2), new class_243((double)n3, d, (double)n4), color, 8.0f, false);
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, double d, Color color) {
        Color color2 = this.a(class_2432, d, color);
        this.a(matrixStackStage, class_2432, d, color2, 4.0f);
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, double d, Color color, float f) {
        int n = 96;
        for (int i = 0; i < n; ++i) {
            double d2 = Math.PI * 2 * (double)i / (double)n;
            double d3 = Math.PI * 2 * (double)(i + 1) / (double)n;
            class_243 class_2433 = new class_243(class_2432.field_1352 + d * Math.cos(d2), class_2432.field_1351, class_2432.field_1350 + d * Math.sin(d2));
            class_243 class_2434 = new class_243(class_2432.field_1352 + d * Math.cos(d3), class_2432.field_1351, class_2432.field_1350 + d * Math.sin(d3));
            Render3D.drawLine(matrixStackStage.l(), class_2433, class_2434, color, f, false);
        }
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, double d, double d2, double d3, Color color, double d4) {
        Color color2 = this.a(class_2432, d4, color);
        class_238 class_2382 = new class_238(class_2432.field_1352 - d, class_2432.field_1351 - d2, class_2432.field_1350 - d3, class_2432.field_1352 + d, class_2432.field_1351 + d2, class_2432.field_1350 + d3);
        Render3D.drawBox(matrixStackStage.l(), class_2382, color2, 8.0f, true, false, false);
    }

    private class_243 iY() {
        class_243 class_2432 = ItemRadiusModule.aiG.field_1724.method_33571();
        class_243 class_2433 = ItemRadiusModule.aiG.field_1724.method_5828(1.0f);
        float f = ItemRadiusModule.aiG.field_1724.method_5695(1.0f);
        double d = Math.toRadians(f);
        double d2 = 1.0 + Math.abs(Math.sin(d));
        double d3 = Math.max(10.0, Math.min(50.0, 20.0 * d2));
        class_243 class_2434 = class_2432.method_1019(class_2433.method_1021(d3));
        double d4 = ItemRadiusModule.aiG.field_1724.method_23318();
        if (ItemRadiusModule.aiG.field_1687 != null) {
            d4 = ItemRadiusModule.aiG.field_1687.method_8624(class_2902.class_2903.field_13197, (int)class_2434.field_1352, (int)class_2434.field_1350);
        }
        return new class_243(class_2434.field_1352, d4, class_2434.field_1350);
    }

    private class_243 d(class_243 class_2432) {
        return new class_243(Math.floor(class_2432.field_1352) + 0.5, Math.floor(class_2432.field_1351) + 0.5, Math.floor(class_2432.field_1350) + 0.5);
    }

    private boolean a(class_1792 class_17922) {
        return ItemRadiusModule.aiG.field_1724.method_6047().method_31574(class_17922) || ItemRadiusModule.aiG.field_1724.method_6079().method_31574(class_17922);
    }

    private Color a(class_243 class_2432, double d, Color color) {
        if (((Boolean)this.BY.jT()).booleanValue() && this.a(class_2432, d)) {
            return this.e((Color)this.BZ.jT());
        }
        return this.e(color);
    }

    private boolean a(class_243 class_2432, double d) {
        double d2 = d * d;
        for (class_1657 class_16572 : ItemRadiusModule.aiG.field_1687.method_18456()) {
            if (class_16572 == ItemRadiusModule.aiG.field_1724 || !(class_16572.method_5707(class_2432) <= d2)) continue;
            return true;
        }
        return false;
    }

    private Color e(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(color.getAlpha(), 210));
    }
}

