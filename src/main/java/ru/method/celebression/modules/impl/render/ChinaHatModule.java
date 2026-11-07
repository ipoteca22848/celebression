package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.manager.FriendManager;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;

public class ChinaHatModule
extends Module {
    private static final int zh = 96;
    private static final float zi = 0.62f;
    private static final float zj = 0.35f;
    private static final float zk = 1.0f;
    private static final float zl = 0.7f;
    private static final float zm = 50.0f;
    private final BooleanSetting zn = new BooleanSetting("settings.chinahat.show_friends", true);
    private final BooleanSetting zo = new BooleanSetting("settings.chinahat.rainbow", false);
    private final EnumSetting<HatVertex> zp = new EnumSetting<HatVertex>("settings.chinahat.style", HatVertex.zq);

    public ChinaHatModule() {
        super("ChinaHat", ModuleCategory.ql);
    }

    @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (ChinaHatModule.fullNullCheck()) {
            return;
        }
        float f = matrixStackStage.k().method_60637(true);
        class_4587 class_45872 = matrixStackStage.l();
        FriendManager friendManager = Celebression.getInstance().getFriendManager();
        for (class_1657 class_16572 : ChinaHatModule.aiG.field_1687.method_18456()) {
            boolean bl;
            boolean bl2 = bl = class_16572 == ChinaHatModule.aiG.field_1724;
            if (bl && ChinaHatModule.aiG.field_1690.method_31044().method_31034() || !bl && (!((Boolean)this.zn.jT()).booleanValue() || friendManager == null || !friendManager.av(class_16572.method_7334().getName()))) continue;
            this.a(class_45872, class_16572, f);
        }
    }

        private void a(class_4587 class_45872, class_1657 class_16572, float f) {
        class_243 class_2432 = class_16572.method_30950(f);
        class_243 class_2433 = ChinaHatModule.aiG.field_1773.method_19418().method_19326();
        class_45872.method_22903();
        try {
            float f2;
            float f3;
            class_45872.method_22904(class_2432.field_1352 - class_2433.field_1352, class_2432.field_1351 - class_2433.field_1351, class_2432.field_1350 - class_2433.field_1350);
            float f4 = class_3532.method_16439((float)f, (float)class_16572.field_6220, (float)class_16572.field_6283);
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(-f4));
            if (class_16572 == ChinaHatModule.aiG.field_1724 && CosmeticPreviewTransformState.ei) {
                f3 = CosmeticPreviewTransformState.ej;
                f2 = CosmeticPreviewTransformState.ek;
                class_45872.method_22904(0.0, (double)f3, 0.0);
                class_45872.method_22907(class_7833.field_40714.rotation(f2));
                class_45872.method_22904(0.0, (double)(-f3), 0.0);
            }
            f3 = class_16572.method_18381(class_16572.method_18376());
            class_45872.method_22904(0.0, (double)(f3 - 0.15f), 0.0);
            f2 = class_3532.method_16439((float)f, (float)class_16572.field_6259, (float)class_16572.field_6241);
            float f5 = f2 - f4;
            float f6 = class_3532.method_16439((float)f, (float)class_16572.field_6004, (float)class_16572.method_36455());
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(-f5));
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(f6));
            if (class_16572 == ChinaHatModule.aiG.field_1724 && CosmeticPreviewTransformState.ei && Math.abs(CosmeticPreviewTransformState.el) > 1.0E-4f) {
                class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.el));
            }
            float f7 = (float)Math.toRadians(class_3532.method_15363((float)f6, (float)-90.0f, (float)90.0f));
            float f8 = class_3532.method_15363((float)((float)Math.cos(f7)), (float)0.0f, (float)1.0f);
            float f9 = 1.0f - f8;
            float f10 = f6 < 0.0f ? 1.6f : 1.0f;
            float f11 = 0.03f * f8 + (f6 < 0.0f ? 0.008f * f9 : 0.0f);
            float f12 = 0.04f + 0.08f * f9 * f10 - 0.05f;
            float f13 = -0.06f * f9 * Math.signum(f6) * f10;
            class_45872.method_22904(0.0, (double)((class_16572.method_18276() ? 0.305f : 0.265f) + f11 + f12), (double)f13);
            float f14 = (float)((double)(System.currentTimeMillis() - Celebression.getInstance().getInitTime()) / 1000.0 * 50.0);
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(f14));
            Color color = this.m(class_16572);
            HatVertex hatVertex = (HatVertex)this.zp.jT();
            float f15 = class_3532.method_15363((float)(0.62f * hatVertex.zu), (float)0.25f, (float)1.25f);
            float f16 = class_3532.method_15363((float)(0.35f * hatVertex.zv), (float)0.1f, (float)0.85f);
            int n = 96;
            float f17 = class_3532.method_15363((float)(1.0f * hatVertex.zw), (float)0.05f, (float)1.0f);
            float f18 = class_3532.method_15363((float)(0.7f * hatVertex.zx), (float)0.05f, (float)1.0f);
            switch (hatVertex.ordinal()) {
                case 0: {
                    this.a(class_45872, f15, f16, color, n, f17, f18);
                    return;
                }
                case 1: {
                    this.a(class_45872, f15, f16 * 0.92f, color, n, f17 * 0.95f, f18);
                    class_45872.method_22903();
                    class_45872.method_46416(0.0f, f16 * 0.32f, 0.0f);
                    this.a(class_45872, f15 * 0.62f, f16 * 0.68f, ColorUtils.fade(color, Color.WHITE, 0.24f), Math.max(24, n - 8), f17, f18 * 0.7f);
                    class_45872.method_22909();
                    return;
                }
                case 2: {
                    this.b(class_45872, f15 * 0.74f, f15, color, n, f18, f16 * 0.58f);
                    this.b(class_45872, f15 * 0.42f, f15 * 0.56f, ColorUtils.fade(color, Color.WHITE, 0.4f), n, f17, f16 * 0.58f);
                    return;
                }
            }
            return;
        }
        finally {
            class_45872.method_22909();
        }
    }

    private Color m(class_1657 class_16572) {
        if (((Boolean)this.zo.jT()).booleanValue()) {
            float f = (float)((System.currentTimeMillis() - Celebression.getInstance().getInitTime()) % 6000L) / 6000.0f;
            return ColorUtils.getGlobalColor(f += (float)(class_16572.method_5628() & 0x1F) / 31.0f);
        }
        return ColorUtils.getGlobalColor();
    }

    private void a(class_4587 class_45872, float f, float f2, Color color, int n, float f3, float f4) {
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GL11.glDisable((int)2884);
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_289 class_2892 = class_289.method_1348();
        float f5 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
        float f6 = class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f);
        float f7 = (float)color.getRed() / 255.0f;
        float f8 = (float)color.getGreen() / 255.0f;
        float f9 = (float)color.getBlue() / 255.0f;
        class_287 class_2872 = class_2892.method_60827(class_293.class_5596.field_27381, class_290.field_1576);
        class_2872.method_22918(matrix4f, 0.0f, 0.0f, 0.0f).method_22915(f7, f8, f9, f6);
        for (int i = 0; i <= n; ++i) {
            double d = (double)i / (double)n * Math.PI * 2.0;
            class_2872.method_22918(matrix4f, (float)(Math.cos(d) * (double)f), 0.0f, (float)(Math.sin(d) * (double)f)).method_22915(f7, f8, f9, f6);
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        class_287 class_2873 = class_2892.method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        for (int i = 0; i <= n; ++i) {
            double d = (double)i / (double)n * Math.PI * 2.0;
            float f10 = (float)(Math.cos(d) * (double)f);
            float f11 = (float)(Math.sin(d) * (double)f);
            class_2873.method_22918(matrix4f, f10, 0.0f, f11).method_22915(f7, f8, f9, f6);
            class_2873.method_22918(matrix4f, 0.0f, f2, 0.0f).method_22915(f7, f8, f9, f5);
        }
        class_286.method_43433((class_9801)class_2873.method_60800());
        RenderSystem.lineWidth((float)2.5f);
        class_287 class_2874 = class_2892.method_60827(class_293.class_5596.field_29345, class_290.field_1576);
        for (int i = 0; i <= n; ++i) {
            double d = (double)i / (double)n * Math.PI * 2.0;
            class_2874.method_22918(matrix4f, (float)(Math.cos(d) * (double)f), 0.0f, (float)(Math.sin(d) * (double)f)).method_22915(f7, f8, f9, 1.0f);
        }
        class_286.method_43433((class_9801)class_2874.method_60800());
        RenderSystem.lineWidth((float)1.0f);
        GL11.glEnable((int)2884);
    }

    private void b(class_4587 class_45872, float f, float f2, Color color, int n, float f3, float f4) {
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GL11.glDisable((int)2884);
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_289 class_2892 = class_289.method_1348();
        class_287 class_2872 = class_2892.method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        float f5 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
        float f6 = (float)color.getRed() / 255.0f;
        float f7 = (float)color.getGreen() / 255.0f;
        float f8 = (float)color.getBlue() / 255.0f;
        for (int i = 0; i <= n; ++i) {
            double d = (double)i / (double)n * Math.PI * 2.0;
            float f9 = (float)Math.cos(d);
            float f10 = (float)Math.sin(d);
            class_2872.method_22918(matrix4f, f9 * f2, f4, f10 * f2).method_22915(f6, f7, f8, f5 * 0.95f);
            class_2872.method_22918(matrix4f, f9 * f, f4, f10 * f).method_22915(f6, f7, f8, f5);
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        GL11.glEnable((int)2884);
    }

    static enum HatVertex
    implements NamedOption  {
        zq("settings.chinahat.style.classic", 1.0f, 1.0f, 1.0f, 1.0f),
        zr("settings.chinahat.style.layered", 1.0f, 1.0f, 1.0f, 1.0f),
        zs("settings.chinahat.style.halo", 1.0f, 1.0f, 1.0f, 0.95f);
        private final String zt;
        final float zu;
        final float zv;
        final float zw;
        final float zx;

private HatVertex(String string2, float f, float f2, float f3, float f4) {
            this.zt = string2;
            this.zu = f;
            this.zv = f2;
            this.zw = f3;
            this.zx = f4;
        }

        @Override
        public String getName() {
            return this.zt;
        }

}
}

