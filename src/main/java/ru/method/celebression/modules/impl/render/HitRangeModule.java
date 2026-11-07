package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4587;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;

public class HitRangeModule
extends Module {
    private static final int BB = 72;
    private static final float BC = 3.32f;
    private static final float BD = 0.02f;
    private final ColorSetting BE = new ColorSetting("settings.hitrange.color", new Color(80, 180, 255, 200));
    private final SliderSetting BF = new SliderSetting("settings.hitrange.thickness", 2.0f, 0.5f, 8.0f, 0.1f);
    private final SliderSetting BH = new SliderSetting("settings.hitrange.rotspeed", 0.4f, 0.05f, 2.0f, 0.05f);
    private final BooleanSetting BI = new BooleanSetting("settings.hitrange.depth", true);
    private float BJ = 0.0f;

    public HitRangeModule() {
        super("HitRange", ModuleCategory.ql);
        this.getSettings().add(this.BE);
        this.getSettings().add(this.BF);
        this.getSettings().add(this.BH);
        this.getSettings().add(this.BI);
    }

    @EventHandler
    private void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (HitRangeModule.fullNullCheck()) {
            return;
        }
        boolean bl = false;
        for (class_1657 player : HitRangeModule.aiG.field_1687.method_18456()) {
            if (player == HitRangeModule.aiG.field_1724 || player.method_5767() || player.method_7325() || !(player.method_5739((class_1297)HitRangeModule.aiG.field_1724) <= 3.32f)) continue;
            bl = true;
            break;
        }
        this.BJ = (this.BJ + ((Float)this.BH.jT()).floatValue()) % 360.0f;
        float f = matrixStackStage.k().method_60637(true);
        class_243 playerPosition = HitRangeModule.aiG.field_1724.method_30950(f);
        class_243 class_2432 = HitRangeModule.aiG.field_1773.method_19418().method_19326();
        float f2 = (float)(playerPosition.field_1352 - class_2432.field_1352);
        float f3 = (float)(playerPosition.field_1351 + (double)0.02f - class_2432.field_1351);
        float f4 = (float)(playerPosition.field_1350 - class_2432.field_1350);
        Color color = bl ? new Color(255, 45, 45, 220) : (Color)this.BE.jT();
        float f5 = ((Float)this.BF.jT()).floatValue() * 0.045f;
        boolean bl2 = (Boolean)this.BI.jT();
        class_4587 class_45872 = matrixStackStage.l();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        if (bl2) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc((int)515);
        } else {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.depthMask((boolean)false);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.round((float)color.getAlpha() * 0.22f)));
        this.a(matrix4f, f2, f3, f4, 3.32f, f5 * 2.6f, color2);
        this.a(matrix4f, f2, f3, f4, 3.32f, f5, color);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        if (!bl2) {
            RenderSystem.enableDepthTest();
        }
    }

    private void a(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, Color color) {
        double d = Math.toRadians(this.BJ);
        int n = color.getRGB();
        float f6 = f4 + f5;
        float f7 = Math.max(0.01f, f4 - f5);
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        for (int i = 0; i <= 72; ++i) {
            double d2 = d + Math.PI * 2 * (double)i / 72.0;
            float f8 = (float)Math.cos(d2);
            float f9 = (float)Math.sin(d2);
            class_2872.method_22918(matrix4f, f + f8 * f6, f2, f3 + f9 * f6).method_39415(n);
            class_2872.method_22918(matrix4f, f + f8 * f7, f2, f3 + f9 * f7).method_39415(n);
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
    }
}
