package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1303;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4587;
import ru.method.celebression.api.mixins.accessors.IWorldRenderer;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public class CustomHitBoxModule
extends Module {
    private static final double zQ = 16384.0;
    private static final long zR = 120L;
    private final Map<Integer, long[]> zS = new HashMap<Integer, long[]>();
    private final BooleanSetting zT = new BooleanSetting("settings.customhitbox.fill", true);
    private final SliderSetting zU = new SliderSetting("settings.customhitbox.fill_alpha", 90.0f, 0.0f, 255.0f, 1.0f, () -> (Boolean)this.zT.jT());
    private final SliderSetting zV = new SliderSetting("settings.customhitbox.line_width", 1.5f, 1.0f, 8.0f, 0.5f);
    private final ColorSetting zW = new ColorSetting("settings.customhitbox.color", ColorUtils.getGlobalColor());
    private final BooleanSetting zX = new BooleanSetting("settings.customhitbox.self", false);
    private final BooleanSetting zY = new BooleanSetting("settings.customhitbox.items", false);

    public CustomHitBoxModule() {
        super("CustomHitBox", ModuleCategory.ql);
        this.getSettings().add(this.zT);
        this.getSettings().add(this.zU);
        this.getSettings().add(this.zV);
        this.getSettings().add(this.zW);
        this.getSettings().add(this.zX);
        this.getSettings().add(this.zY);
    }

    public boolean iy() {
        return (Boolean)this.zX.jT();
    }

    @EventHandler
    private void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (CustomHitBoxModule.fullNullCheck()) {
            return;
        }
        float f = matrixStackStage.k().method_60637(true);
        class_4587 class_45872 = new class_4587();
        this.a(class_45872, f);
    }

    private void a(class_4587 class_45872, float f) {
        Color color = (Color)this.zW.jT();
        for (class_1297 class_12972 : CustomHitBoxModule.aiG.field_1687.method_18112()) {
            Color color2;
            class_1309 class_13092;
            boolean bl = CustomHitBoxModule.aiG.field_1724 != null && class_12972.method_5667().equals(CustomHitBoxModule.aiG.field_1724.method_5667());
            boolean bl2 = CustomHitBoxModule.aiG.field_1690.method_31044().method_31034();
            if (bl && (!((Boolean)this.zX.jT()).booleanValue() || bl2) || !((Boolean)this.zY.jT()).booleanValue() && class_12972 instanceof class_1542 || class_12972 instanceof class_1303 || !class_12972.method_5805() || class_12972.method_31481() || class_12972.method_5767() || class_12972 instanceof class_1309 && (class_13092 = (class_1309)class_12972).method_6059(class_1294.field_5905) || CustomHitBoxModule.aiG.field_1724 != null && CustomHitBoxModule.aiG.field_1724.method_5858(class_12972) > 16384.0) continue;
            class_238 boundingBox = this.a(class_12972, f);
            if (!((IWorldRenderer)CustomHitBoxModule.aiG.field_1769).getFrustum().method_23093(boundingBox) || !this.a(class_12972, boundingBox, f)) continue;
            int n = class_3532.method_15340((int)Math.round(((Float)this.zU.jT()).floatValue()), (int)0, (int)255);
            float f2 = (float)color.getAlpha() / 255.0f;
            int n2 = class_3532.method_15340((int)Math.round((float)n * f2), (int)0, (int)255);
            if (((Boolean)this.zT.jT()).booleanValue() && n2 > 0) {
                color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n2);
                this.a(class_45872, boundingBox, color2);
            }
            color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            this.a(class_45872, boundingBox, color2, ((Float)this.zV.jT()).floatValue());
        }
    }

    private class_238 a(class_1297 class_12972, float f) {
        class_243 class_2432 = class_12972.method_30950(f);
        class_243 class_2433 = class_12972.method_19538();
        class_243 class_2434 = class_2432.method_1020(class_2433);
        return class_12972.method_5829().method_997(class_2434);
    }

    private boolean a(class_1297 class_12972, class_238 class_2382, float f) {
        if (CustomHitBoxModule.aiG.field_1724 == null || CustomHitBoxModule.aiG.field_1687 == null) {
            return false;
        }
        long l = System.currentTimeMillis();
        long[] lArray = this.zS.get(class_12972.method_5628());
        if (lArray != null && l < lArray[0]) {
            return lArray[1] != 0L;
        }
        class_243 class_2432 = CustomHitBoxModule.aiG.field_1724.method_5836(f);
        boolean bl = this.a(class_2432, class_2382);
        if (this.zS.size() > 512) {
            this.zS.clear();
        }
        this.zS.put(class_12972.method_5628(), new long[]{l + 120L, bl ? 1L : 0L});
        return bl;
    }

    private boolean a(class_243 class_2432, class_238 class_2382) {
        if (this.a(class_2432, class_2382.method_1005())) {
            return true;
        }
        for (class_243 class_2433 : this.a(class_2382)) {
            if (!this.a(class_2432, class_2433)) continue;
            return true;
        }
        return false;
    }

    private class_243[] a(class_238 class_2382) {
        double d = (class_2382.field_1323 + class_2382.field_1320) * 0.5;
        double d2 = (class_2382.field_1322 + class_2382.field_1325) * 0.5;
        double d3 = (class_2382.field_1321 + class_2382.field_1324) * 0.5;
        double d4 = Math.max(0.0, (class_2382.field_1320 - class_2382.field_1323) * 0.08);
        double d5 = Math.max(0.0, (class_2382.field_1324 - class_2382.field_1321) * 0.08);
        double d6 = class_2382.field_1323 + d4;
        double d7 = class_2382.field_1320 - d4;
        double d8 = class_2382.field_1321 + d5;
        double d9 = class_2382.field_1324 - d5;
        double d10 = class_2382.field_1322 + 0.05;
        double d11 = class_2382.field_1325 - 0.05;
        return new class_243[]{new class_243(d, d11, d3), new class_243(d, d10, d3), new class_243(d6, d11, d8), new class_243(d6, d11, d9), new class_243(d7, d11, d8), new class_243(d7, d11, d9), new class_243(d6, d2, d3), new class_243(d7, d2, d3), new class_243(d, d2, d8), new class_243(d, d2, d9)};
    }

    private boolean a(class_243 class_2432, class_243 class_2433) {
        class_3959 class_39592 = new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)CustomHitBoxModule.aiG.field_1724);
        class_3965 class_39652 = CustomHitBoxModule.aiG.field_1687.method_17742(class_39592);
        if (class_39652 == null) {
            return true;
        }
        if (class_39652.method_17783() == class_239.class_240.field_1333) {
            return true;
        }
        if (class_39652.method_17783() == class_239.class_240.field_1332) {
            double d;
            double d2 = class_39652.method_17784().method_1025(class_2432);
            return d2 >= (d = class_2433.method_1025(class_2432)) - 0.001;
        }
        return true;
    }

    private void a(class_4587 class_45872, class_238 class_2382, Color color) {
        class_243 class_2432 = new class_243(class_2382.field_1323, class_2382.field_1322, class_2382.field_1321);
        class_243 class_2433 = new class_243(class_2382.field_1320, class_2382.field_1325, class_2382.field_1324);
        class_243 class_2434 = new class_243(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        class_243 class_2435 = new class_243(class_2432.field_1352, class_2432.field_1351, class_2433.field_1350);
        class_243 class_2436 = new class_243(class_2432.field_1352, class_2433.field_1351, class_2432.field_1350);
        class_243 class_2437 = new class_243(class_2432.field_1352, class_2433.field_1351, class_2433.field_1350);
        class_243 class_2438 = new class_243(class_2433.field_1352, class_2432.field_1351, class_2432.field_1350);
        class_243 class_2439 = new class_243(class_2433.field_1352, class_2432.field_1351, class_2433.field_1350);
        class_243 class_24310 = new class_243(class_2433.field_1352, class_2433.field_1351, class_2432.field_1350);
        class_243 class_24311 = new class_243(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350);
        Render3D.renderTriangle(class_45872, class_2436, class_2437, class_24311, color, true);
        Render3D.renderTriangle(class_45872, class_2436, class_24311, class_24310, color, true);
        Render3D.renderTriangle(class_45872, class_2438, class_2439, class_2435, color, true);
        Render3D.renderTriangle(class_45872, class_2438, class_2435, class_2434, color, true);
        Render3D.renderTriangle(class_45872, class_2434, class_2435, class_2437, color, true);
        Render3D.renderTriangle(class_45872, class_2434, class_2437, class_2436, color, true);
        Render3D.renderTriangle(class_45872, class_24310, class_24311, class_2439, color, true);
        Render3D.renderTriangle(class_45872, class_24310, class_2439, class_2438, color, true);
        Render3D.renderTriangle(class_45872, class_2438, class_24310, class_2436, color, true);
        Render3D.renderTriangle(class_45872, class_2438, class_2436, class_2434, color, true);
        Render3D.renderTriangle(class_45872, class_2435, class_2437, class_24311, color, true);
        Render3D.renderTriangle(class_45872, class_2435, class_24311, class_2439, color, true);
    }

    private void a(class_4587 class_45872, class_238 class_2382, Color color, float f) {
        class_243 class_2432 = new class_243(class_2382.field_1323, class_2382.field_1322, class_2382.field_1321);
        class_243 class_2433 = new class_243(class_2382.field_1320, class_2382.field_1325, class_2382.field_1324);
        class_243 class_2434 = new class_243(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        class_243 class_2435 = new class_243(class_2432.field_1352, class_2432.field_1351, class_2433.field_1350);
        class_243 class_2436 = new class_243(class_2432.field_1352, class_2433.field_1351, class_2432.field_1350);
        class_243 class_2437 = new class_243(class_2432.field_1352, class_2433.field_1351, class_2433.field_1350);
        class_243 class_2438 = new class_243(class_2433.field_1352, class_2432.field_1351, class_2432.field_1350);
        class_243 class_2439 = new class_243(class_2433.field_1352, class_2432.field_1351, class_2433.field_1350);
        class_243 class_24310 = new class_243(class_2433.field_1352, class_2433.field_1351, class_2432.field_1350);
        class_243 class_24311 = new class_243(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350);
        double d = Math.max(0.0035, (double)f * 0.006);
        this.a(class_45872, class_2434, class_2435, d, color);
        this.a(class_45872, class_2435, class_2437, d, color);
        this.a(class_45872, class_2437, class_2436, d, color);
        this.a(class_45872, class_2436, class_2434, d, color);
        this.a(class_45872, class_2438, class_2439, d, color);
        this.a(class_45872, class_2439, class_24311, d, color);
        this.a(class_45872, class_24311, class_24310, d, color);
        this.a(class_45872, class_24310, class_2438, d, color);
        this.a(class_45872, class_2434, class_2438, d, color);
        this.a(class_45872, class_2435, class_2439, d, color);
        this.a(class_45872, class_2437, class_24311, d, color);
        this.a(class_45872, class_2436, class_24310, d, color);
    }

    private void a(class_4587 class_45872, class_243 class_2432, class_243 class_2433, double d, Color color) {
        class_243 class_2434;
        class_243 class_2435;
        double d2 = d * 0.5;
        if (Math.abs(class_2433.field_1352 - class_2432.field_1352) > 1.0E-6) {
            class_2435 = new class_243(0.0, d2, 0.0);
            class_2434 = new class_243(0.0, 0.0, d2);
        } else if (Math.abs(class_2433.field_1351 - class_2432.field_1351) > 1.0E-6) {
            class_2435 = new class_243(d2, 0.0, 0.0);
            class_2434 = new class_243(0.0, 0.0, d2);
        } else {
            class_2435 = new class_243(d2, 0.0, 0.0);
            class_2434 = new class_243(0.0, d2, 0.0);
        }
        this.a(class_45872, class_2432, class_2433, class_2435, color);
        this.a(class_45872, class_2432, class_2433, class_2434, color);
    }

    private void a(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, Color color) {
        class_243 class_2435 = class_2432.method_1020(class_2434);
        class_243 class_2436 = class_2433.method_1020(class_2434);
        class_243 class_2437 = class_2433.method_1019(class_2434);
        class_243 class_2438 = class_2432.method_1019(class_2434);
        Render3D.renderTriangle(class_45872, class_2435, class_2436, class_2437, color, true);
        Render3D.renderTriangle(class_45872, class_2435, class_2437, class_2438, color, true);
    }
}
