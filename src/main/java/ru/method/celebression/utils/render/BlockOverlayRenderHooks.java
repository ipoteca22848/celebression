package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_3726;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_9974;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.BlockOverlayModule;
import ru.method.celebression.module.Module;
import ru.method.celebression.utils.render.BlockOverlayShaderRenderer;
import ru.method.celebression.utils.render.ColorUtils;

public final class BlockOverlayRenderHooks {
    private static boolean registered = false;
    private static final long ANIM_EPOCH_NANOS = System.nanoTime();

    private BlockOverlayRenderHooks() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;
        WorldRenderEvents.BLOCK_OUTLINE.register(BlockOverlayRenderHooks::onBlockOutline);
    }

    private static boolean onBlockOutline(WorldRenderContext worldRenderContext, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
        class_238 class_23832;
        List<class_238> list;
        BlockOverlayModule blockOverlayModule;
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isPanic()) {
            return true;
        }
        BlockOverlayModule blockOverlayModule2 = blockOverlayModule = celebression.getModuleManager() == null ? null : celebression.getModuleManager().getModule(BlockOverlayModule.class);
        if (blockOverlayModule == null || !blockOverlayModule.isToggled() || Module.fullNullCheck()) {
            return true;
        }
        if (worldRenderContext.world() == null) {
            return true;
        }
        class_4597 class_45972 = worldRenderContext.consumers();
        if (class_45972 == null) {
            return true;
        }
        class_4587 class_45872 = worldRenderContext.matrixStack();
        if (class_45872 == null) {
            return true;
        }
        class_2338 class_23382 = blockOutlineContext.blockPos();
        class_2680 class_26802 = blockOutlineContext.blockState();
        if (class_23382 == null || class_26802 == null) {
            return true;
        }
        class_3726 class_37262 = blockOutlineContext.entity() != null ? class_3726.method_16195((class_1297)blockOutlineContext.entity()) : class_3726.method_16194();
        class_265 class_2652 = class_26802.method_26172((class_1922)worldRenderContext.world(), class_23382, class_37262);
        if (class_2652 == null) {
            class_2652 = class_259.method_1077();
        }
        if ((list = class_2652.method_1090()).isEmpty()) {
            return true;
        }
        if (list.size() > 1) {
            class_238 class_2384 = class_2652.method_1107();
            list = List.of(class_2384);
        }
        double d = blockOutlineContext.cameraX();
        double d2 = blockOutlineContext.cameraY();
        double d3 = blockOutlineContext.cameraZ();
        float f = 0.0f;
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)2884);
        boolean bl3 = GL11.glIsEnabled((int)2929);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((int)770, (int)771);
        int n = BlockOverlayRenderHooks.clampAlpha(((Float)blockOverlayModule.yT.jT()).floatValue());
        int n2 = BlockOverlayRenderHooks.clampAlpha(((Float)blockOverlayModule.yU.jT()).floatValue());
        float f2 = (float)((double)(System.nanoTime() - ANIM_EPOCH_NANOS) / 4.0E9) * ((Float)blockOverlayModule.yV.jT()).floatValue();
        BlockOverlayModule.OverlayShape overlayShape = (BlockOverlayModule.OverlayShape)blockOverlayModule.yS.jT();
        if (((Boolean)blockOverlayModule.yR.jT()).booleanValue() && BlockOverlayShaderRenderer.canRender(overlayShape) && Math.max(n, n2) > 0) {
            int n3 = Math.max(n, Math.round((float)n2 * 0.72f));
            for (class_238 outlineBox : list) {
                class_238 class_2385 = outlineBox.method_996(class_23382).method_1014(0.003).method_989(-d, -d2, -d3);
                BlockOverlayRenderHooks.renderStyledFill(class_45872, class_45972, class_2385, n3, f2, overlayShape, true);
            }
        } else if (((Boolean)blockOverlayModule.yQ.jT()).booleanValue() && n > 0) {
            for (class_238 class_2386 : list) {
                class_238 fillBox = class_2386.method_996(class_23382).method_1014((double)f).method_989(-d, -d2, -d3);
                BlockOverlayRenderHooks.renderStyledFill(class_45872, class_45972, fillBox, n, f2, overlayShape, false);
            }
        }
        if (n2 > 0) {
            for (class_238 class_2386 : list) {
                class_238 outlineBox = class_2386.method_996(class_23382).method_1014((double)f).method_989(-d, -d2, -d3);
                BlockOverlayRenderHooks.renderOutline(class_45872, class_45972, outlineBox, n2, f2, overlayShape);
            }
        }
        if (bl2) {
            RenderSystem.enableCull();
        }
        if (!bl) {
            RenderSystem.disableBlend();
        }
        if (bl3) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        return false;
    }

    private static void renderStyledFill(class_4587 class_45872, class_4597 class_45972, class_238 class_2383, int n, float f, BlockOverlayModule.OverlayShape overlayShape, boolean bl) {
        int n2;
        FillPalette fillPalette = FillPalette.of(overlayShape);
        if (bl && BlockOverlayShaderRenderer.canRender(overlayShape)) {
            Color color = fillPalette.sample(f + (float)((class_2383.field_1323 + class_2383.field_1322 + class_2383.field_1321) * (double)0.05f));
            BlockOverlayShaderRenderer.render(class_45872, class_2383, n, f, overlayShape, color);
            return;
        }
        if (overlayShape == BlockOverlayModule.OverlayShape.yY) {
            float f2 = f + (float)((class_2383.field_1323 + class_2383.field_1322 + class_2383.field_1321) * (double)0.04f);
            Color color = fillPalette.sample(f2);
            int n3 = Math.max(10, Math.round((float)n * 0.7f));
            BlockOverlayRenderHooks.drawFilledBox(class_45872, class_45972, class_2383, new Color(color.getRed(), color.getGreen(), color.getBlue(), n3));
            for (int i = 0; i < 3; ++i) {
                double d = 0.0018 * (double)(i + 1);
                float f3 = 1.0f - (float)i / 3.0f;
                int n4 = Math.round((float)n * (fillPalette.glowMinAlpha + fillPalette.glowAlphaRange * f3));
                BlockOverlayRenderHooks.drawFilledBox(class_45872, class_45972, class_2383.method_1014(d), new Color(color.getRed(), color.getGreen(), color.getBlue(), n4));
            }
            return;
        }
        double d = Math.max(0.001, class_2383.field_1325 - class_2383.field_1322);
        int n5 = Math.max(4, Math.min(8, (int)Math.ceil(d * 6.0)));
        for (n2 = 0; n2 < n5; ++n2) {
            float f4 = (float)n2 / (float)n5;
            double d2 = class_3532.method_16436((double)f4, (double)class_2383.field_1322, (double)class_2383.field_1325);
            double d3 = class_3532.method_16436((double)(((float)n2 + 1.0f) / (float)n5), (double)class_2383.field_1322, (double)class_2383.field_1325);
            Color color = fillPalette.sample(f + f4 * 0.45f + (float)((class_2383.field_1323 + class_2383.field_1321) * (double)0.035f));
            int n6 = Math.max(10, Math.round((float)n * (fillPalette.baseMinAlpha + fillPalette.baseAlphaRange * (1.0f - f4))));
            BlockOverlayRenderHooks.drawFilledBox(class_45872, class_45972, new class_238(class_2383.field_1323, d2, class_2383.field_1321, class_2383.field_1320, d3, class_2383.field_1324), new Color(color.getRed(), color.getGreen(), color.getBlue(), n6));
        }
        for (n2 = 0; n2 < 3; ++n2) {
            double d4 = 0.0018 * (double)(n2 + 1);
            float f5 = 1.0f - (float)n2 / 3.0f;
            Color color = fillPalette.sample(f + 0.16f * (float)n2 + (float)(class_2383.field_1322 * (double)0.05f));
            int n7 = Math.round((float)n * (fillPalette.glowMinAlpha + fillPalette.glowAlphaRange * f5));
            BlockOverlayRenderHooks.drawFilledBox(class_45872, class_45972, class_2383.method_1014(d4), new Color(color.getRed(), color.getGreen(), color.getBlue(), n7));
        }
    }

    private static void renderOutline(class_4587 class_45872, class_4597 class_45972, class_238 class_2383, int n, float f, BlockOverlayModule.OverlayShape overlayShape) {
        FillPalette fillPalette = FillPalette.of(overlayShape);
        Color color = fillPalette.sample(f + (float)((class_2383.field_1323 + class_2383.field_1321) * (double)0.05f));
        Color color2 = ColorUtils.mix(color, Color.WHITE, 0.35f);
        for (int i = 0; i < 2; ++i) {
            float f2 = 1.0f - (float)i * 0.45f;
            int n2 = Math.max(8, Math.round((float)n * (0.24f * f2)));
            class_238 class_2384 = class_2383.method_1014(0.0012 * (double)(i + 1));
            class_9974.method_62295((class_4587)class_45872, (class_4588)class_45972.getBuffer(class_1921.method_23594()), (class_238)class_2384, (float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)n2 / 255.0f));
        }
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        class_9974.method_62295((class_4587)class_45872, (class_4588)class_45972.getBuffer(class_1921.method_23594()), (class_238)class_2383, (float)((float)color3.getRed() / 255.0f), (float)((float)color3.getGreen() / 255.0f), (float)((float)color3.getBlue() / 255.0f), (float)((float)color3.getAlpha() / 255.0f));
    }

    private static void drawFilledBox(class_4587 class_45872, class_4597 class_45972, class_238 class_2383, Color color) {
        class_9974.method_62300((class_4587)class_45872, (class_4588)class_45972.getBuffer(class_1921.method_49047()), (double)class_2383.field_1323, (double)class_2383.field_1322, (double)class_2383.field_1321, (double)class_2383.field_1320, (double)class_2383.field_1325, (double)class_2383.field_1324, (float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    private static int clampAlpha(float f) {
        if (f <= 0.0f) {
            return 0;
        }
        if (f >= 255.0f) {
            return 255;
        }
        return Math.round(f);
    }

    static final class FillPalette {
        private final Color a;
        private final Color b;
        private final Color c;
        final float baseMinAlpha;
        final float baseAlphaRange;
        final float glowMinAlpha;
        final float glowAlphaRange;

        private FillPalette(Color color, Color color2, Color color3, float f, float f2, float f3, float f4) {
            this.a = color;
            this.b = color2;
            this.c = color3;
            this.baseMinAlpha = f;
            this.baseAlphaRange = f2;
            this.glowMinAlpha = f3;
            this.glowAlphaRange = f4;
        }

        Color sample(float f) {
            float f2 = f - (float)Math.floor(f);
            if (f2 < 0.5f) {
                return ColorUtils.mix(this.a, this.b, f2 * 2.0f);
            }
            return ColorUtils.mix(this.b, this.c, (f2 - 0.5f) * 2.0f);
        }

        static FillPalette of(BlockOverlayModule.OverlayShape overlayShape) {
            if (overlayShape == BlockOverlayModule.OverlayShape.yX) {
                return new FillPalette(new Color(55, 42, 180), new Color(224, 54, 255), new Color(54, 220, 255), 0.24f, 0.56f, 0.12f, 0.09f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.yY) {
                return new FillPalette(new Color(34, 52, 128), new Color(86, 114, 224), new Color(176, 210, 255), 0.2f, 0.52f, 0.1f, 0.08f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.yZ) {
                return new FillPalette(new Color(210, 222, 232), new Color(255, 255, 255), new Color(128, 150, 170), 0.18f, 0.45f, 0.08f, 0.06f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.za) {
                return new FillPalette(new Color(255, 72, 80), new Color(255, 220, 72), new Color(72, 172, 255), 0.26f, 0.58f, 0.14f, 0.12f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.zb) {
                return new FillPalette(new Color(24, 16, 72), new Color(106, 64, 220), new Color(255, 166, 232), 0.22f, 0.54f, 0.12f, 0.1f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.zc) {
                return new FillPalette(new Color(34, 8, 76), new Color(186, 44, 206), new Color(72, 186, 255), 0.22f, 0.56f, 0.12f, 0.1f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.zd) {
                return new FillPalette(new Color(8, 16, 44), new Color(44, 92, 164), new Color(235, 246, 255), 0.18f, 0.48f, 0.1f, 0.1f);
            }
            if (overlayShape == BlockOverlayModule.OverlayShape.ze) {
                return new FillPalette(new Color(36, 30, 72), new Color(255, 210, 92), new Color(255, 255, 220), 0.2f, 0.52f, 0.12f, 0.1f);
            }
            return new FillPalette(ColorUtils.getGlobalColor(0.05f), ColorUtils.getGlobalColor(0.45f), ColorUtils.getGlobalColor(0.85f), 0.22f, 0.55f, 0.12f, 0.07f);
        }
    }
}
