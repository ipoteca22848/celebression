package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Generated;
import net.minecraft.class_1011;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.method.celebression.api.render.builders.Builder;
import ru.method.celebression.api.render.builders.impl.LiquidGlassNewBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBlur;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBorder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltLiquidGlassNew;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltRectangle;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltRoundedOutline;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltText;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltTexture;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;
import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ClickGuiRenderContext;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;
import ru.method.celebression.utils.render.LiquidPanelPresets;
import ru.method.celebression.utils.render.fonts.Instance;

public final class Render2D
implements MinecraftClientAccess {
    public static final float DEFAULT_CORNER_RADIUS = 20.0f;
    private static final float DISTORT_REFERENCE_SIZE = 220.0f;
    private static final float LIQUID_SIZE_QUANTUM = 2.0f;
    private static final int RECTANGLE_CACHE_CAPACITY = 512;
    private static final int BORDER_CACHE_CAPACITY = 256;
    private static final int ROUNDED_OUTLINE_CACHE_CAPACITY = 128;
    private static final int BLUR_CACHE_CAPACITY = 128;
    private static final int TEXTURE_CACHE_CAPACITY = 512;
    private static final int LIQUID_CACHE_CAPACITY = 256;
    private static final int TEXT_CACHE_CAPACITY = 768;
    private static final Map<RectangleKey, BuiltRectangle> RECTANGLE_CACHE = Render2D.createLruCache(512);
    private static final Map<BorderKey, BuiltBorder> BORDER_CACHE = Render2D.createLruCache(256);
    private static final Map<RoundedOutlineKey, BuiltRoundedOutline> ROUNDED_OUTLINE_CACHE = Render2D.createLruCache(128);
    private static final Map<BlurKey, BuiltBlur> BLUR_CACHE = Render2D.createLruCache(128);
    private static final Map<TextureKey, BuiltTexture> TEXTURE_CACHE = Render2D.createLruCache(512);
    private static final Map<LiquidKey, BuiltLiquidGlassNew> LIQUID_CACHE = Render2D.createLruCache(256);
    private static final Map<TextKey, BuiltText> TEXT_CACHE = Render2D.createLruCache(768);
    private static final float PRESET_SIZE_EPSILON = 0.35f;
    private static final float LIQUID_PRESET_MIN_SCALE = 0.05f;
    private static volatile boolean LIQUID_HUD_MODE = false;

    public static void drawRoundedRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0) {
            return;
        }
        float f6 = f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f3, f4, f6, f6, f6, f6, color2.getRGB(), color2.getRGB(), color2.getRGB(), color2.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f6)).color(new QuadColorState(color2)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawCircle(class_4587 class_45872, float f, float f2, float f3, Color color) {
        Render2D.drawCircle(class_45872, f, f2, f3, 48, color);
    }

    public static void drawRing(class_4587 class_45872, float f, float f2, float f3, float f4, Color color) {
        color = Render2D.withClickGuiFade(color);
        if (f3 <= 0.0f || f4 <= 0.0f || color == null || color.getAlpha() <= 0) {
            return;
        }
        int n = 72;
        float f5 = Math.max(0.05f, f3 - f4 * 0.5f);
        float f6 = f3 + f4 * 0.5f;
        int n2 = color.getRGB();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        for (int i = 0; i <= n; ++i) {
            double d = Math.PI * 2 * (double)i / (double)n;
            float f7 = (float)Math.cos(d);
            float f8 = (float)Math.sin(d);
            class_2872.method_22918(matrix4f, f + f7 * f6, f2 + f8 * f6, 0.0f).method_39415(n2);
            class_2872.method_22918(matrix4f, f + f7 * f5, f2 + f8 * f5, 0.0f).method_39415(n2);
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void drawCircle(class_4587 class_45872, float f, float f2, float f3, int n, Color color) {
        color = Render2D.withClickGuiFade(color);
        if (f3 <= 0.0f || n < 3 || color == null || color.getAlpha() <= 0) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27381, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(color.getRGB());
        for (int i = 0; i <= n; ++i) {
            double d = Math.PI * 2 * (double)i / (double)n;
            float f4 = f + (float)(Math.cos(d) * (double)f3);
            float f5 = f2 + (float)(Math.sin(d) * (double)f3);
            class_2872.method_22918(matrix4f, f4, f5, 0.0f).method_39415(color.getRGB());
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void drawDotGrid(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0 || f5 <= 0.0f) {
            return;
        }
        if (f3 <= f6 || f4 <= f6) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        int n = color2.getRGB();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        for (float f7 = f2 + f6; f7 < f2 + f4; f7 += f5) {
            for (float f8 = f + f6; f8 < f + f3; f8 += f5) {
                class_2872.method_22918(matrix4f, f8 - 1.0f, f7 - 1.0f, 0.0f).method_39415(n);
                class_2872.method_22918(matrix4f, f8 - 1.0f, f7 + 1.0f, 0.0f).method_39415(n);
                class_2872.method_22918(matrix4f, f8 + 1.0f, f7 + 1.0f, 0.0f).method_39415(n);
                class_2872.method_22918(matrix4f, f8 + 1.0f, f7 - 1.0f, 0.0f).method_39415(n);
            }
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void drawHatch(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0 || f5 <= 0.0f || f6 <= 0.0f) {
            return;
        }
        if (f3 <= 0.0f || f4 <= 0.0f || f5 >= f3 + f4) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        int n = color2.getRGB();
        float f7 = f6 / 2.0f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        for (float f8 = f5; f8 < f3 + f4; f8 += f5) {
            float f9;
            float f10;
            float f11 = f + Math.min(f8, f3);
            float f12 = f2 + Math.max(0.0f, f8 - f3);
            float f13 = f + Math.max(0.0f, f8 - f4);
            float f14 = f13 - f11;
            float f15 = (float)Math.hypot(f14, f10 = (f9 = f2 + Math.min(f8, f4)) - f12);
            if (f15 <= 0.001f) continue;
            float f16 = -f10 / f15 * f7;
            float f17 = f14 / f15 * f7;
            class_2872.method_22918(matrix4f, f11 + f16, f12 + f17, 0.0f).method_39415(n);
            class_2872.method_22918(matrix4f, f11 - f16, f12 - f17, 0.0f).method_39415(n);
            class_2872.method_22918(matrix4f, f13 - f16, f9 - f17, 0.0f).method_39415(n);
            class_2872.method_22918(matrix4f, f13 + f16, f9 + f17, 0.0f).method_39415(n);
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void drawRoundedHorizontalGradient(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Color color2) {
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f6 = f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f3, f4, f6, f6, f6, f6, color.getRGB(), color.getRGB(), color2.getRGB(), color2.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f6)).color(new QuadColorState(color, color, color2, color2)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawRoundedVerticalGradient(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Color color2) {
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f6 = f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f3, f4, f6, f6, f6, f6, color.getRGB(), color2.getRGB(), color2.getRGB(), color.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f6)).color(new QuadColorState(color, color2, color2, color)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawBlurredRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Render2D.drawBlurredRect(class_45872, f, f2, f3, f4, f5, f6, color, ColorUtils.isBlurEnabled());
    }

    public static void drawBlurredRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, boolean bl) {
        f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        if (!bl) {
            Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color);
            return;
        }
        float f7 = f5;
        BuiltBlur builtBlur = Render2D.getOrCreateBlur(new BlurKey(f3, f4, f7, f7, f7, f7, color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f, f6), () -> (BuiltBlur)Builder.blur().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f7)).blurRadius(f6).color(new QuadColorState(color)).build());
        builtBlur.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawBorder(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0) {
            return;
        }
        float f8 = f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        BuiltBorder builtBorder = Render2D.getOrCreateBorder(new BorderKey(f3, f4, f8, f8, f8, f8, color2.getRGB(), color2.getRGB(), color2.getRGB(), color2.getRGB(), 1.0f, f6, f7), () -> (BuiltBorder)Builder.border().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f8)).smoothness(f6, f7).color(new QuadColorState(color2)).build());
        builtBorder.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawDrainwalkOutline(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        Render2D.drawDrainwalkOutline(class_45872, f, f2, f3, f4, f5, f6, 0.0f, 0.34f, color);
    }

    public static void drawDrainwalkOutline(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0 || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f9 = f5 = Render2D.clampSimpleRadius(f5, f3, f4);
        BuiltRoundedOutline builtRoundedOutline = Render2D.getOrCreateRoundedOutline(new RoundedOutlineKey(f3, f4, f9, f9, f9, f9, color2.getRGB(), color2.getRGB(), color2.getRGB(), color2.getRGB(), f6, f7, f8), () -> (BuiltRoundedOutline)Builder.roundedOutline().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f9)).thickness(f6).smoothness(f7, f8).color(new QuadColorState(color2)).build());
        builtRoundedOutline.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawHorizontalGradient(class_4587 class_45872, float f, float f2, float f3, float f4, Color color, Color color2) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f5, f6, 0.0f, 0.0f, 0.0f, 0.0f, color.getRGB(), color.getRGB(), color2.getRGB(), color2.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f5, f6)).color(new QuadColorState(color, color, color2, color2)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawVerticalGradient(class_4587 class_45872, float f, float f2, float f3, float f4, Color color, Color color2) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f5, f6, 0.0f, 0.0f, 0.0f, 0.0f, color.getRGB(), color2.getRGB(), color2.getRGB(), color.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f5, f6)).color(new QuadColorState(color, color2, color2, color)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawStyledRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, int n) {
        Render2D.drawStyledRect(class_45872, f, f2, f3, f4, f5, color, n, null);
    }

    public static void drawStyledRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, int n, Consumer<LiquidGlassNewBuilder> consumer) {
        if (!ColorUtils.isLiquidGlassShaderActive()) {
            if (f3 <= 0.0f || f4 <= 0.0f) {
                return;
            }
            Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, Render2D.solidColor(color));
            return;
        }
        if (f3 <= 2.0f || f4 <= 2.0f) {
            Color color2 = ColorUtils.getGlassColor(Render2D.clampChannel(color.getAlpha()));
            Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color2);
            return;
        }
        float f6 = Render2D.clampFloat((float)color.getAlpha() / 255.0f, 0.0f, 1.0f);
        float f7 = Math.max(f3, f4);
        int n2 = Render2D.clampChannel(Math.round(50.0f + f6 * 80.0f + (float)n * 0.3f));
        int n3 = Render2D.clampChannel(Math.round(80.0f + f6 * 120.0f + (float)n * 0.25f));
        Color color3 = ColorUtils.getGlassBase();
        Color color4 = ColorUtils.alpha(ColorUtils.mix(color3, Color.WHITE, 0.45f), n2);
        Color color5 = ColorUtils.getGlassColor(n3);
        Color color6 = ColorUtils.getGlassHighlightColor();
        Color color7 = ColorUtils.getGlassOutlineColor();
        float f8 = Render2D.clampFloat(f7 * 0.18f, 24.0f, 44.0f);
        if (ColorUtils.isBlurEnabled()) {
            Render2D.drawBlurredRect(class_45872, f + 0.2f, f2 + 0.2f, f3 - 0.4f, f4 - 0.4f, Math.max(0.0f, f5 - 0.2f), f8, color4);
        }
        if (!ColorUtils.isLiquidGlassShaderActive()) {
            return;
        }
        float f9 = Render2D.clampFloat(1.15f + f5 * 0.035f, 0.95f, 2.2f);
        float f10 = Render2D.clampFloat(2.35f + f5 * 0.06f, 2.05f, 3.05f);
        float f11 = 0.88f;
        float f12 = 0.94f;
        float f13 = 0.22f;
        float f14 = 0.57f;
        float f15 = 0.68f;
        float f16 = 0.24f;
        float f17 = Render2D.clampFloat(0.0036f + f6 * 4.0E-4f, 0.0034f, 0.0044f);
        Render2D.drawLiquidGlassRect(class_45872, f, f2, f3, f4, f5, color5, liquidGlassNewBuilder -> {
            liquidGlassNewBuilder.smoothness(f9);
            liquidGlassNewBuilder.cornerSmoothness(f10);
            liquidGlassNewBuilder.globalAlpha(0.96f);
            liquidGlassNewBuilder.baseAlpha(f13);
            liquidGlassNewBuilder.fresnelAlpha(f12);
            liquidGlassNewBuilder.fresnelMix(f11);
            liquidGlassNewBuilder.distortStrength(f17);
            liquidGlassNewBuilder.fresnelPower(2.7f);
            liquidGlassNewBuilder.fresnelInvert(true);
            liquidGlassNewBuilder.fresnelColor(color6);
            if (consumer != null) {
                consumer.accept((LiquidGlassNewBuilder)liquidGlassNewBuilder);
            }
        });
        int n4 = Render2D.clampChannel(Math.round(f14 * 255.0f));
        if (n4 > 0) {
            Color color8 = new Color(color7.getRed(), color7.getGreen(), color7.getBlue(), n4);
            float f18 = Math.max(0.0f, f15 * 0.5f);
            Render2D.drawBorder(class_45872, f - f18, f2 - f18, f3 + f18 * 2.0f, f4 + f18 * 2.0f, f5 + f15, f9, f9 + f16, color8);
        }
    }

    public static void drawLiquidGlassRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawLiquidGlassRectOther(class_45872, f, f2, f3, f4, f5, color, null);
    }

    public static void drawLiquidGlassRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Consumer<LiquidGlassNewBuilder> consumer) {
        Render2D.drawLiquidGlassRectOther(class_45872, f, f2, f3, f4, f5, color, consumer);
    }

    public static void drawLiquidGlassRectOther(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawLiquidGlassRectOther(class_45872, f, f2, f3, f4, f5, color, null);
    }

    public static void drawLiquidGlassRectOther(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Consumer<LiquidGlassNewBuilder> consumer) {
        Render2D.drawLiquidGlassRectInternal(class_45872, f, f2, f3, f4, f5, color, LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY, consumer);
    }

    public static void drawLiquidGlassRectMainMenu(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawLiquidGlassRectMainMenu(class_45872, f, f2, f3, f4, f5, color, null);
    }

    public static void drawLiquidGlassRectMainMenu(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Consumer<LiquidGlassNewBuilder> consumer) {
        Render2D.drawLiquidGlassRectInternal(class_45872, f, f2, f3, f4, f5, color, LiquidGlassReflectionCache.ReflectionSource.MAIN_MENU, consumer);
    }

    public static void drawLiquidGlassRectNew(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawLiquidGlassRectNew(class_45872, f, f2, f3, f4, f5, color, null);
    }

    public static void drawLiquidGlassRectNew(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Consumer<LiquidGlassNewBuilder> consumer) {
        Render2D.drawLiquidGlassRectOther(class_45872, f, f2, f3, f4, f5, color, consumer);
    }

    private static void drawLiquidGlassRectInternal(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, LiquidGlassReflectionCache.ReflectionSource reflectionSource, Consumer<LiquidGlassNewBuilder> consumer) {
        if (!ColorUtils.isLiquidGlassShaderActive()) {
            if (f3 <= 0.0f || f4 <= 0.0f) {
                return;
            }
            Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, Render2D.solidColor(color));
            return;
        }
        float f6 = Render2D.quantizeLiquidDimension(f3);
        float f7 = Render2D.quantizeLiquidDimension(f4);
        QuadRadiusState quadRadiusState = Render2D.clampRadius(new QuadRadiusState(f5), f6, f7);
        float f8 = Render2D.computeBlurRadius(f6, f7);
        if (consumer == null) {
            Render2D.getOrCreateLiquid(new LiquidKey(f6, f7, quadRadiusState.radius1(), quadRadiusState.radius2(), quadRadiusState.radius3(), quadRadiusState.radius4(), color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f, 2.0f, 1.0f, 2.0f, Color.WHITE.getRGB(), 0.8f, 0.2f, false, 1.0f, 0.03f, f8, reflectionSource.ordinal()), () -> (BuiltLiquidGlassNew)Builder.liquidGlassNew().size(new SizeState(f6, f7)).radius(quadRadiusState).color(new QuadColorState(color)).blurRadius(f8).reflectionSource(reflectionSource).build()).render(class_45872.method_23760().method_23761(), f, f2);
            return;
        }
        LiquidGlassNewBuilder liquidGlassNewBuilder = Builder.liquidGlassNew().size(new SizeState(f3, f4)).radius(quadRadiusState).color(new QuadColorState(color)).blurRadius(f8).reflectionSource(reflectionSource);
        consumer.accept(liquidGlassNewBuilder);
        ((BuiltLiquidGlassNew)liquidGlassNewBuilder.build()).render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawLiquidRect(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Objects.requireNonNull(class_45872, "matrices");
        Objects.requireNonNull(borderRadius, "borderRadius");
        Objects.requireNonNull(colorRGBA, "color");
        if (!ColorUtils.isLiquidGlassShaderActive()) {
            float f11 = Math.max(0.0f, Math.min(f3, f4) * 0.5f);
            float f12 = Render2D.clampFloat(borderRadius.topLeftRadius(), 0.0f, f11);
            float f13 = Render2D.clampFloat(borderRadius.bottomLeftRadius(), 0.0f, f11);
            float f14 = Render2D.clampFloat(borderRadius.bottomRightRadius(), 0.0f, f11);
            float f15 = Render2D.clampFloat(borderRadius.topRightRadius(), 0.0f, f11);
            Render2D.drawRect(class_45872, f, f2, f3, f4, f12, f13, f14, f15, Render2D.solidColor(colorRGBA));
            return;
        }
        Render2D.drawLiquidRectAtExactSize(class_45872, f, f2, Render2D.quantizeLiquidDimension(f3), Render2D.quantizeLiquidDimension(f4), borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
    }

    public static void drawLiquidRectPreset(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, BorderRadius borderRadius, ColorRGBA colorRGBA, float f7, float f8, float f9, float f10, boolean bl, float f11, float f12) {
        boolean bl2;
        Objects.requireNonNull(class_45872, "matrices");
        Objects.requireNonNull(borderRadius, "borderRadius");
        Objects.requireNonNull(colorRGBA, "color");
        if (f3 <= 0.0f || f4 <= 0.0f || f5 <= 0.0f || f6 <= 0.0f) {
            return;
        }
        if (!ColorUtils.isLiquidGlassShaderActive()) {
            float f13 = Math.max(0.0f, Math.min(f3, f4) * 0.5f);
            float f14 = Render2D.clampFloat(borderRadius.topLeftRadius(), 0.0f, f13);
            float f15 = Render2D.clampFloat(borderRadius.bottomLeftRadius(), 0.0f, f13);
            float f16 = Render2D.clampFloat(borderRadius.bottomRightRadius(), 0.0f, f13);
            float f17 = Render2D.clampFloat(borderRadius.topRightRadius(), 0.0f, f13);
            Render2D.drawRect(class_45872, f, f2, f3, f4, f14, f15, f16, f17, Render2D.solidColor(colorRGBA));
            return;
        }
        float f18 = f3 / f5;
        float f19 = f4 / f6;
        boolean bl3 = bl2 = Math.abs(f18 - 1.0f) <= 0.35f && Math.abs(f19 - 1.0f) <= 0.35f;
        if (bl2) {
            Render2D.drawLiquidRectAtExactSize(class_45872, f, f2, f5, f6, borderRadius, colorRGBA, f7, f8, f9, f10, bl, f11, f12);
            return;
        }
        if (f18 < 0.05f || f19 < 0.05f) {
            Render2D.drawLiquidRectAtExactSize(class_45872, f, f2, f5, f6, borderRadius, colorRGBA, f7, f8, f9, f10, bl, f11, f12);
            return;
        }
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22905(f18, f19, 1.0f);
        Render2D.drawLiquidRectAtExactSize(class_45872, 0.0f, 0.0f, f5, f6, borderRadius, colorRGBA, f7, f8, f9, f10, bl, f11, f12, f, f2, f3, f4);
        class_45872.method_22909();
    }

    public static boolean isLiquidHudMode() {
        return LIQUID_HUD_MODE;
    }

        public static void drawLiquidRectHud(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        boolean bl2 = Render2D.isLiquidHudMode();
        LIQUID_HUD_MODE = true;
        try {
            Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, LiquidPanelPresets.snapWidth(f3), LiquidPanelPresets.snapHeight(f4), borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
        }
        finally {
            LIQUID_HUD_MODE = bl2;
        }
    }

    public static void drawLiquidRectClickGuiPanel(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        if (f4 <= 26.0f) {
            Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 120.0f, 20.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
        } else {
            Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 120.0f, 250.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
        }
    }

    public static void drawLiquidRectClickGuiSearch(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 150.0f, 18.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
    }

    public static void drawLiquidRectClickGuiSettings(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 120.0f, 170.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
    }

    public static void drawLiquidRectClickGuiChip(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 42.0f, 12.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
    }

    public static void drawLiquidRectClickGuiColorPicker(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Render2D.drawLiquidRectPreset(class_45872, f, f2, f3, f4, 152.0f, 195.0f, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10);
    }

    public static void beginClickGuiLiquidBatch() {
        BuiltLiquidGlassNew.beginBatch();
    }

    public static void flushClickGuiLiquidBatch() {
        BuiltLiquidGlassNew.flushBatch();
    }

    private static void drawLiquidRectAtExactSize(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10) {
        Render2D.drawLiquidRectAtExactSize(class_45872, f, f2, f3, f4, borderRadius, colorRGBA, f5, f6, f7, f8, bl, f9, f10, f, f2, f3, f4);
    }

    private static void drawLiquidRectAtExactSize(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, ColorRGBA colorRGBA, float f5, float f6, float f7, float f8, boolean bl, float f9, float f10, float f11, float f12, float f13, float f14) {
        Color color = colorRGBA.toAwtColor();
        QuadColorState quadColorState = new QuadColorState(color);
        QuadRadiusState quadRadiusState = Render2D.clampRadius(new QuadRadiusState(borderRadius.topLeftRadius(), borderRadius.topRightRadius(), borderRadius.bottomRightRadius(), borderRadius.bottomLeftRadius()), f3, f4);
        float f15 = Render2D.adjustDistortStrength(f10, f3, f4);
        float f16 = Render2D.computeBlurRadius(f3, f4);
        boolean bl2 = Render2D.isLiquidHudMode();
        LiquidGlassReflectionCache.ReflectionSource reflectionSource = !bl2 && ClickGuiRenderContext.isClickGuiOpen() ? LiquidGlassReflectionCache.ReflectionSource.CLICK_GUI : LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY;
        float f17 = !bl2 && ClickGuiRenderContext.isClickGuiOpen() ? ClickGuiRenderContext.getFadeMultiplier() : 1.0f;
        Render2D.getOrCreateLiquid(new LiquidKey(f3, f4, quadRadiusState.radius1(), quadRadiusState.radius2(), quadRadiusState.radius3(), quadRadiusState.radius4(), color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f, f5, f17, f6, Color.WHITE.getRGB(), f7, f8, bl, f9, f15, f16, reflectionSource.ordinal()), () -> (BuiltLiquidGlassNew)Builder.liquidGlassNew().size(new SizeState(f3, f4)).radius(quadRadiusState).color(quadColorState).smoothness(1.0f).cornerSmoothness(f5).globalAlpha(f17).baseAlpha(f8).fresnelAlpha(f7).fresnelMix(f9).fresnelPower(f6).fresnelInvert(bl).distortStrength(f15).blurRadius(f16).reflectionSource(reflectionSource).build()).render(class_45872.method_23760().method_23761(), f, f2, 0.0f, f11, f12, f13, f14);
    }

    public static void drawFont(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (instance == null || string == null || string.isEmpty() || color2 == null || color2.getAlpha() <= 0) {
            return;
        }
        if ((string = Render2D.stripObfuscatedFormatting(string)).isEmpty()) {
            return;
        }
        String string2 = string;
        BuiltText builtText = Render2D.getOrCreateText(new TextKey(instance.font(), instance.size(), string2, color2.getRGB(), 0.05f), () -> (BuiltText)Builder.text().size(instance.size()).font(instance.font()).text(string2).thickness(0.05f).color(color2).build());
        try {
            builtText.render(class_45872.method_23760().method_23761(), f, f2);
        }
        catch (Throwable throwable) {
        }
    }

    public static void drawFontRaw(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (instance == null || string == null || string.isEmpty() || color2 == null || color2.getAlpha() <= 0) {
            return;
        }
        if (instance.getWidth(string) <= 0.0f) {
            return;
        }
        BuiltText builtText = (BuiltText)Builder.text().size(instance.size()).font(instance.font()).text(string).thickness(0.05f).color(color2).build();
        try {
            builtText.render(class_45872.method_23760().method_23761(), f, f2);
        }
        catch (Throwable throwable) {
        }
    }

    public static void drawFont(class_4587 class_45872, Instance instance, String string, float f, float f2, ColorRGBA colorRGBA) {
        if (colorRGBA == null) {
            return;
        }
        Render2D.drawFont(class_45872, instance, string, f, f2, colorRGBA.toAwtColor());
    }

    public static void drawFontGlow(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color, Color color2, float f3) {
        if (instance == null || string == null || string.isEmpty() || color.getAlpha() <= 0) {
            return;
        }
        if ((string = Render2D.stripObfuscatedFormatting(string)).isEmpty()) {
            return;
        }
        if (instance.getWidth(string) <= 0.0f) {
            return;
        }
        BuiltText builtText = (BuiltText)Builder.text().size(instance.size()).font(instance.font()).text(string).thickness(0.05f).color(color).glow(color2, f3).build();
        try {
            builtText.render(class_45872.method_23760().method_23761(), f, f2);
        }
        catch (Throwable throwable) {
        }
    }

    public static void drawFontGradient(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color, Color color2, Color color3, float f3) {
        if (instance == null || string == null || string.isEmpty() || color.getAlpha() <= 0) {
            return;
        }
        if ((string = Render2D.stripObfuscatedFormatting(string)).isEmpty()) {
            return;
        }
        if (instance.getWidth(string) <= 0.0f) {
            return;
        }
        BuiltText builtText = (BuiltText)Builder.text().size(instance.size()).font(instance.font()).text(string).thickness(0.05f).color(color).gradient(color2, color3, f3).build();
        try {
            builtText.render(class_45872.method_23760().method_23761(), f, f2);
        }
        catch (Throwable throwable) {
        }
    }

    public static void drawRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA) {
        if (colorRGBA == null) {
            return;
        }
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, colorRGBA.toAwtColor());
    }

    public static void drawRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, ColorRGBA colorRGBA) {
        if (colorRGBA == null || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        QuadRadiusState quadRadiusState = Render2D.clampRadius(new QuadRadiusState(f5, f8, f7, f6), f3, f4);
        Color color = colorRGBA.toAwtColor();
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f3, f4, quadRadiusState.radius1(), quadRadiusState.radius2(), quadRadiusState.radius3(), quadRadiusState.radius4(), color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(quadRadiusState).color(new QuadColorState(color)).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA, ColorRGBA colorRGBA2, ColorRGBA colorRGBA3, ColorRGBA colorRGBA4) {
        if (colorRGBA == null || colorRGBA2 == null || colorRGBA3 == null || colorRGBA4 == null || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        BuiltRectangle builtRectangle = Render2D.getOrCreateRectangle(new RectangleKey(f3, f4, f5, f5, f5, f5, colorRGBA.toAwtColor().getRGB(), colorRGBA2.toAwtColor().getRGB(), colorRGBA3.toAwtColor().getRGB(), colorRGBA4.toAwtColor().getRGB(), 1.0f), () -> (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f5)).color(new QuadColorState(colorRGBA.toAwtColor(), colorRGBA2.toAwtColor(), colorRGBA3.toAwtColor(), colorRGBA4.toAwtColor())).build());
        builtRectangle.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawBlur(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, ColorRGBA colorRGBA) {
        if (colorRGBA == null) {
            return;
        }
        Render2D.drawBlurredRect(class_45872, f, f2, f3, f4, f5, f6, colorRGBA.toAwtColor());
    }

    public static void drawBorder(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, ColorRGBA colorRGBA) {
        if (colorRGBA == null || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        ColorRGBA colorRGBA2 = Render2D.withClickGuiFade(colorRGBA);
        if (colorRGBA2.getA() <= 0) {
            return;
        }
        Color color = colorRGBA2.toAwtColor();
        BuiltBorder builtBorder = Render2D.getOrCreateBorder(new BorderKey(f3, f4, f5, f5, f5, f5, color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), f6, 0.4f, 0.4f), () -> (BuiltBorder)Builder.border().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f5)).thickness(f6).smoothness(0.4f, 0.4f).color(new QuadColorState(color)).build());
        builtBorder.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawLine(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA) {
        Render2D.drawGlowingLine(class_45872, f, f2, f3, f4, f5, colorRGBA, null, 0.0f);
    }

    public static void drawGlowingLine(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA, ColorRGBA colorRGBA2, float f6) {
        if (class_45872 == null || colorRGBA == null) {
            return;
        }
        float f7 = f3 - f;
        float f8 = f4 - f2;
        float f9 = (float)Math.hypot(f7, f8);
        if (f9 <= 0.001f) {
            return;
        }
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotation((float)Math.atan2(f8, f7)));
        float f10 = f5 / 2.0f;
        float f11 = -f10;
        if (colorRGBA2 != null && f6 > 0.0f) {
            Render2D.drawBlur(class_45872, 0.0f, f11, f9, f5, f10, f6, colorRGBA2);
        }
        Render2D.drawRect(class_45872, 0.0f, f11, f9, f5, f10, colorRGBA);
        class_45872.method_22909();
    }

    public static void drawTexture(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, class_2960 class_29602, Color color) {
        Render2D.drawTexture(class_45872, f, f2, f3, f4, f5, aiG.method_1531().method_4619(class_29602), color);
    }

    public static void drawTexture(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, class_1044 class_10443, Color color) {
        Color color2 = Render2D.withClickGuiFade(color);
        if (color2 == null || color2.getAlpha() <= 0) {
            return;
        }
        if (class_10443 instanceof class_1043) {
            class_10443.method_4527(true, false);
        }
        int n = class_10443.method_4624();
        BuiltTexture builtTexture = Render2D.getOrCreateTexture(new TextureKey(f3, f4, f5, f5, f5, f5, color2.getRGB(), color2.getRGB(), color2.getRGB(), color2.getRGB(), 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, n), () -> (BuiltTexture)Builder.texture().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f5)).texture(0.0f, 0.0f, 1.0f, 1.0f, n).color(new QuadColorState(color2)).build());
        builtTexture.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawTexture(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, class_2960 class_29602, Color color) {
        int n = aiG.method_1531().method_4619(class_29602).method_4624();
        BuiltTexture builtTexture = Render2D.getOrCreateTexture(new TextureKey(f3, f4, f5, f5, f5, f5, color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f, f6, f7, f8, f9, n), () -> (BuiltTexture)Builder.texture().size(new SizeState(f3, f4)).radius(new QuadRadiusState(f5)).texture(f6, f7, f8, f9, n).color(new QuadColorState(color)).build());
        builtTexture.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawTexture(class_4587 class_45872, float f, float f2, float f3, float f4, BorderRadius borderRadius, class_2960 class_29602, ColorRGBA colorRGBA) {
        if (class_45872 == null || borderRadius == null || class_29602 == null || colorRGBA == null) {
            return;
        }
        Render2D.drawTexture(class_45872, f, f2, f3, f4, borderRadius.topLeftRadius(), borderRadius.topRightRadius(), borderRadius.bottomRightRadius(), borderRadius.bottomLeftRadius(), 0.0f, 0.0f, 1.0f, 1.0f, class_29602, colorRGBA);
    }

    public static void drawTexture(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, class_2960 class_29602, ColorRGBA colorRGBA) {
        if (class_45872 == null || class_29602 == null || colorRGBA == null) {
            return;
        }
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        ColorRGBA colorRGBA2 = Render2D.withClickGuiFade(colorRGBA);
        if (colorRGBA2.getA() <= 0) {
            return;
        }
        QuadRadiusState quadRadiusState = Render2D.clampRadius(new QuadRadiusState(f5, f8, f7, f6), f3, f4);
        Color color = colorRGBA2.toAwtColor();
        int n = aiG.method_1531().method_4619(class_29602).method_4624();
        BuiltTexture builtTexture = Render2D.getOrCreateTexture(new TextureKey(f3, f4, quadRadiusState.radius1(), quadRadiusState.radius2(), quadRadiusState.radius3(), quadRadiusState.radius4(), color.getRGB(), color.getRGB(), color.getRGB(), color.getRGB(), 1.0f, f9, f10, f11, f12, n), () -> (BuiltTexture)Builder.texture().size(new SizeState(f3, f4)).radius(quadRadiusState).texture(f9, f10, f11, f12, n).color(new QuadColorState(color)).build());
        builtTexture.render(class_45872.method_23760().method_23761(), f, f2);
    }

    public static void drawTexture(class_4587 class_45872, class_2960 class_29602, float f, float f2, float f3, float f4, int n, int n2, int n3, ColorRGBA colorRGBA) {
        Render2D.drawTexture(class_45872, class_29602, f, f2, f3, f4, n, n2, n3, colorRGBA, null);
    }

    public static void drawTexture(class_4587 class_45872, class_2960 class_29602, float f, float f2, float f3, float f4, int n, int n2, int n3, ColorRGBA colorRGBA, ColorRGBA colorRGBA2) {
        ColorRGBA colorRGBA3;
        if (class_45872 == null || class_29602 == null) {
            return;
        }
        if (f3 <= 0.0f) {
            return;
        }
        if (colorRGBA != null && colorRGBA.alpha() > 0) {
            Render2D.drawRect(class_45872, f, f2, f3, f3, f4, colorRGBA);
        }
        ColorRGBA colorRGBA4 = colorRGBA3 = colorRGBA2 != null ? colorRGBA2 : new ColorRGBA(255, 255, 255, 255);
        if (n2 > 0 && n3 > 0 && (n != 0 || n2 != n3)) {
            float f5 = Math.max(1, n3);
            float f6 = Math.max(0, n2);
            float f7 = Math.max(0, n);
            float f8 = f7 / f5;
            float f9 = f7 / f5;
            float f10 = f6 / f5;
            float f11 = f6 / f5;
            Render2D.drawTexture(class_45872, f, f2, f3, f3, f4, f8, f9, f10, f11, class_29602, colorRGBA3.toAwtColor());
        } else {
            Render2D.drawTexture(class_45872, f, f2, f3, f3, new BorderRadius(f4), class_29602, colorRGBA3);
        }
    }

    public static void drawTexture(class_332 class_3322, class_2960 class_29602, float f, float f2, float f3, float f4, int n, int n2, int n3, ColorRGBA colorRGBA) {
        if (class_3322 == null) {
            return;
        }
        Render2D.drawTexture(class_3322.method_51448(), class_29602, f, f2, f3, f4, n, n2, n3, colorRGBA, null);
    }

    public static void drawTexture(class_332 class_3322, class_2960 class_29602, float f, float f2, float f3, float f4, int n, int n2, int n3, ColorRGBA colorRGBA, ColorRGBA colorRGBA2) {
        if (class_3322 == null) {
            return;
        }
        Render2D.drawTexture(class_3322.method_51448(), class_29602, f, f2, f3, f4, n, n2, n3, colorRGBA, colorRGBA2);
    }

    public static void startScissor(class_332 class_3322, float f, float f2, float f3, float f4) {
        if (class_3322 == null) {
            return;
        }
        class_3322.method_44379((int)f, (int)f2, (int)(f + f3), (int)(f2 + f4));
    }

    public static void startMatrixScissor(class_332 class_3322, float f, float f2, float f3, float f4) {
        Render2D.startMatrixScissor(class_3322, f, f2, f3, f4, 2);
    }

    public static void startMatrixScissor(class_332 class_3322, float f, float f2, float f3, float f4, int n) {
        if (class_3322 == null) {
            return;
        }
        if (f3 <= 0.0f || f4 <= 0.0f) {
            Render2D.startScissor(class_3322, 0.0f, 0.0f, 0.0f, 0.0f);
            return;
        }
        Matrix4f matrix4f = class_3322.method_51448().method_23760().method_23761();
        Vector3f vector3f = matrix4f.transformPosition(f, f2, 0.0f, new Vector3f());
        Vector3f vector3f2 = matrix4f.transformPosition(f + f3, f2, 0.0f, new Vector3f());
        Vector3f vector3f3 = matrix4f.transformPosition(f, f2 + f4, 0.0f, new Vector3f());
        Vector3f vector3f4 = matrix4f.transformPosition(f + f3, f2 + f4, 0.0f, new Vector3f());
        float f5 = Math.min(Math.min(vector3f.x, vector3f2.x), Math.min(vector3f3.x, vector3f4.x));
        float f6 = Math.min(Math.min(vector3f.y, vector3f2.y), Math.min(vector3f3.y, vector3f4.y));
        float f7 = Math.max(Math.max(vector3f.x, vector3f2.x), Math.max(vector3f3.x, vector3f4.x));
        float f8 = Math.max(Math.max(vector3f.y, vector3f2.y), Math.max(vector3f3.y, vector3f4.y));
        int n2 = Math.max(0, n);
        int n3 = (int)Math.floor(f5) - n2;
        int n4 = (int)Math.floor(f6) - n2;
        int n5 = (int)Math.ceil(f7) + n2;
        int n6 = (int)Math.ceil(f8) + n2;
        class_3322.method_44379(n3, n4, Math.max(n3, n5), Math.max(n4, n6));
    }

    public static void stopScissor(class_332 class_3322) {
        try {
            class_3322.method_44380();
        }
        catch (IllegalStateException illegalStateException) {
        }
    }

    public static void clearScissor(class_332 class_3322) {
        try {
            while (true) {
                class_3322.method_44380();
            }
        }
        catch (IllegalStateException illegalStateException) {
            return;
        }
    }

    public static void prepareHudFrame(class_332 class_3322) {
        if (class_3322 != null) {
            Render2D.clearScissor(class_3322);
        }
    }

    private static float quantizeLiquidDimension(float f) {
        if (f <= 0.0f) {
            return f;
        }
        return Math.max(2.0f, (float)Math.round(f / 2.0f) * 2.0f);
    }

    public static String stripObfuscatedFormatting(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (string.indexOf(167) < 0 && string.indexOf(38) < 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ((c == '\u00a7' || c == '&') && i + 1 < string.length()) {
                char c2;
                if ((c2 = Character.toLowerCase(string.charAt(++i))) == 'k') {
                    bl = true;
                    continue;
                }
                if (Render2D.isMinecraftFormattingCode(c2)) {
                    bl = false;
                    continue;
                }
                if (bl) continue;
                stringBuilder.append(c).append(c2);
                continue;
            }
            if (bl) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static boolean isMinecraftFormattingCode(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'r';
    }

    private static <K, V> V getOrCreate(Map<K, V> map, K k, Supplier<V> supplier) {
        V v = map.get(k);
        if (v != null) {
            return v;
        }
        V v2 = supplier.get();
        map.put(k, v2);
        return v2;
    }

    private static BuiltRectangle getOrCreateRectangle(RectangleKey rectangleKey, Supplier<BuiltRectangle> supplier) {
        return Render2D.getOrCreate(RECTANGLE_CACHE, rectangleKey, supplier);
    }

    private static BuiltBorder getOrCreateBorder(BorderKey borderKey, Supplier<BuiltBorder> supplier) {
        return Render2D.getOrCreate(BORDER_CACHE, borderKey, supplier);
    }

    private static BuiltRoundedOutline getOrCreateRoundedOutline(RoundedOutlineKey roundedOutlineKey, Supplier<BuiltRoundedOutline> supplier) {
        return Render2D.getOrCreate(ROUNDED_OUTLINE_CACHE, roundedOutlineKey, supplier);
    }

    private static BuiltBlur getOrCreateBlur(BlurKey blurKey, Supplier<BuiltBlur> supplier) {
        return Render2D.getOrCreate(BLUR_CACHE, blurKey, supplier);
    }

    private static BuiltTexture getOrCreateTexture(TextureKey textureKey, Supplier<BuiltTexture> supplier) {
        return Render2D.getOrCreate(TEXTURE_CACHE, textureKey, supplier);
    }

    private static BuiltLiquidGlassNew getOrCreateLiquid(LiquidKey liquidKey, Supplier<BuiltLiquidGlassNew> supplier) {
        return Render2D.getOrCreate(LIQUID_CACHE, liquidKey, supplier);
    }

    private static BuiltText getOrCreateText(TextKey textKey, Supplier<BuiltText> supplier) {
        return Render2D.getOrCreate(TEXT_CACHE, textKey, supplier);
    }

    private static <K, V> Map<K, V> createLruCache(final int n) {
        return new LinkedHashMap<K, V>(n, 0.75f, true){

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
                return this.size() > n;
            }
        };
    }

    public static class_1044 convert(BufferedImage bufferedImage) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        class_1011 class_10112 = new class_1011(n, n2, false);
        try {
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < n; ++j) {
                    class_10112.method_61941(j, i, bufferedImage.getRGB(j, i));
                }
            }
            class_1043 class_10432 = new class_1043(class_10112);
            return class_10432;
        }
        catch (Exception exception) {
            class_10112.close();
            throw exception;
        }
    }

    private static int clampChannel(int n) {
        return Math.max(0, Math.min(255, n));
    }

    private static float clampFloat(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }

    private static float clampSimpleRadius(float f, float f2, float f3) {
        float f4 = Math.max(0.0f, Math.min(f2, f3) * 0.5f);
        return Render2D.clampFloat(f, 0.0f, f4);
    }

    private static QuadRadiusState clampRadius(QuadRadiusState quadRadiusState, float f, float f2) {
        float f3 = Math.max(0.0f, f);
        float f4 = Math.max(0.0f, f2);
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return QuadRadiusState.NO_ROUND;
        }
        float f5 = Math.min(f3, f4) * 0.5f;
        float f6 = Render2D.clampFloat(quadRadiusState.radius1(), 0.0f, f5);
        float f7 = Render2D.clampFloat(quadRadiusState.radius2(), 0.0f, f5);
        float f8 = Render2D.clampFloat(quadRadiusState.radius3(), 0.0f, f5);
        float f9 = Render2D.clampFloat(quadRadiusState.radius4(), 0.0f, f5);
        float f10 = 1.0f;
        float f11 = f6 + f7;
        float f12 = f9 + f8;
        float f13 = f6 + f9;
        float f14 = f7 + f8;
        if (f11 > 0.0f) {
            f10 = Math.min(f10, f3 / f11);
        }
        if (f12 > 0.0f) {
            f10 = Math.min(f10, f3 / f12);
        }
        if (f13 > 0.0f) {
            f10 = Math.min(f10, f4 / f13);
        }
        if (f14 > 0.0f) {
            f10 = Math.min(f10, f4 / f14);
        }
        if (f10 < 1.0f) {
            f6 *= f10;
            f7 *= f10;
            f8 *= f10;
            f9 *= f10;
        }
        return new QuadRadiusState(f6, f7, f8, f9);
    }

    private static Color solidColor(Color color) {
        if (color == null) {
            return null;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }

    private static ColorRGBA solidColor(ColorRGBA colorRGBA) {
        if (colorRGBA == null) {
            return null;
        }
        return colorRGBA.withAlpha(255);
    }

    private static float resolveGuiFade() {
        float f;
        float f2 = 1.0f;
        if (ClickGuiRenderContext.isActive() && ClickGuiRenderContext.isClosing()) {
            f2 = ClickGuiRenderContext.getFadeMultiplier();
        }
        if ((f = LinkGuiRenderContext.getFadeMultiplier()) < f2) {
            f2 = f;
        }
        return (float)Math.round(f2 * 64.0f) / 64.0f;
    }

    private static Color withClickGuiFade(Color color) {
        if (color == null) {
            return null;
        }
        float f = Render2D.resolveGuiFade();
        if (f >= 1.0f) {
            return color;
        }
        int n = Math.round((float)color.getAlpha() * f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, n)));
    }

    private static ColorRGBA withClickGuiFade(ColorRGBA colorRGBA) {
        if (colorRGBA == null) {
            return null;
        }
        float f = Render2D.resolveGuiFade();
        if (f >= 1.0f) {
            return colorRGBA;
        }
        return colorRGBA.withAlpha(Math.round((float)colorRGBA.getA() * f));
    }

    private static float adjustDistortStrength(float f, float f2, float f3) {
        float f4 = Math.max(f2, f3);
        if (f4 <= 0.0f) {
            return f;
        }
        float f5 = 220.0f / Math.max(220.0f, f4);
        float f6 = f * f5;
        return Math.max(0.0f, f6);
    }

    private static float computeBlurRadius(float f, float f2) {
        float f3 = Math.max(f, f2);
        if (f3 <= 0.0f) {
            return 0.0f;
        }
        float f4 = f3 * 0.095f;
        return Render2D.clampFloat(f4, 8.0f, 22.0f);
    }

    @Generated
    private Render2D() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    record RectangleKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float smoothness) {
    }

    record BlurKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float smoothness, float blurRadius) {
    }

    record BorderKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float thickness, float internalSmoothness, float externalSmoothness) {
    }

    record RoundedOutlineKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float thickness, float internalSmoothness, float externalSmoothness) {
    }

    record LiquidKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float smoothness, float cornerSmoothness, float globalAlpha, float fresnelPower, int fresnelColor, float fresnelAlpha, float baseAlpha, boolean fresnelInvert, float fresnelMix, float distortStrength, float blurRadius, int reflectionSource) {
    }

    record TextKey(MsdfFont font, float size, String text, int color, float thickness) {
    }

    record TextureKey(float width, float height, float radius1, float radius2, float radius3, float radius4, int color1, int color2, int color3, int color4, float smoothness, float u, float v, float textureWidth, float textureHeight, int textureId) {
    }
}

