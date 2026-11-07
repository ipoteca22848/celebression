package ru.method.celebression.ui;

import ru.method.celebression.animation.Animation;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Generated;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_243;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_332;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_6367;
import net.minecraft.class_7833;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class LinkGuiRenderState
implements MinecraftClientAccess {
    private static final LinkGuiRenderState XD = new LinkGuiRenderState();
    private static final long XE = 520L;
    private static final float XF = 2.6f;
    private class_6367 XG;
    private int XH;
    private int XI;
    private boolean active;
    private long XJ;
    private class_243 XK;
    private float ae;
    private float pitch;

    private LinkGuiRenderState() {
    }

    public boolean isActive() {
        return this.active;
    }

    public void cancel() {
        this.active = false;
    }

        public void a(class_332 class_3322, LinkGuiScreen linkGuiScreen) {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        int n = aiG.method_22683().method_4489();
        int n2 = aiG.method_22683().method_4506();
        if (n <= 0 || n2 <= 0) {
            return;
        }
        if (this.XG == null || this.XH != n || this.XI != n2) {
            if (this.XG != null) {
                this.XG.method_1238();
            }
            this.XG = new class_6367(n, n2, false);
            this.XH = n;
            this.XI = n2;
        }
        class_276 class_2762 = aiG.method_1522();
        this.XG.method_1236(0.0f, 0.0f, 0.0f, 0.0f);
        this.XG.method_1230();
        this.XG.method_1235(true);
        try {
            linkGuiScreen.b(class_3322);
            class_3322.method_51452();
        }
        finally {
            class_2762.method_1235(true);
        }
        class_4184 class_41842 = LinkGuiRenderState.aiG.field_1773.method_19418();
        class_243 class_2432 = class_41842.method_19326();
        double d = 4.0 * ((double)aiG.method_22683().method_4506() / 1080.0);
        class_243 class_2433 = class_243.method_1030((float)class_41842.method_19329(), (float)class_41842.method_19330()).method_1021(d);
        this.XK = class_2432.method_1019(class_2433);
        this.ae = class_41842.method_19330();
        this.pitch = class_41842.method_19329();
        this.XJ = System.currentTimeMillis();
        this.active = true;
    }

    public void f(class_4587 class_45872) {
        if (!this.active || this.XG == null || aiG == null) {
            return;
        }
        if (LinkGuiRenderState.aiG.field_1687 == null) {
            this.active = false;
            return;
        }
        long l = System.currentTimeMillis();
        float f = Math.min(1.0f, (float)(l - this.XJ) / 520.0f);
        if (f >= 1.0f) {
            this.active = false;
            return;
        }
        float f2 = 1.0f - (float)Easing.aiZ.aa(f);
        float f3 = 0.6f + 0.4f * f2;
        int n = Math.max(0, Math.min(255, Math.round(255.0f * f2)));
        if (n <= 2) {
            return;
        }
        class_243 class_2432 = LinkGuiRenderState.aiG.field_1773.method_19418().method_19326();
        class_45872.method_22903();
        class_45872.method_22904(this.XK.field_1352 - class_2432.field_1352, this.XK.field_1351 - class_2432.field_1351, this.XK.field_1350 - class_2432.field_1350);
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(-this.ae + 180.0f));
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(this.pitch));
        float f4 = 2.6f * f3;
        float f5 = f4 * ((float)this.XI / (float)this.XH);
        float f6 = f4 / 2.0f;
        float f7 = f5 / 2.0f;
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader((class_10156)class_10142.field_53880);
        RenderSystem.setShaderTexture((int)0, (int)this.XG.method_30277());
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        class_2872.method_22918(matrix4f, -f6, -f7, 0.0f).method_22913(0.0f, 1.0f).method_1336(255, 255, 255, n);
        class_2872.method_22918(matrix4f, f6, -f7, 0.0f).method_22913(1.0f, 1.0f).method_1336(255, 255, 255, n);
        class_2872.method_22918(matrix4f, f6, f7, 0.0f).method_22913(1.0f, 0.0f).method_1336(255, 255, 255, n);
        class_2872.method_22918(matrix4f, -f6, f7, 0.0f).method_22913(0.0f, 0.0f).method_1336(255, 255, 255, n);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        class_45872.method_22909();
    }

    @Generated
    public static LinkGuiRenderState oo() {
        return XD;
    }
}

