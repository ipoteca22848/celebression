package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.nio.ByteBuffer;
import lombok.Generated;
import net.minecraft.class_408;
import net.minecraft.class_437;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsScreen;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.clickgui.ClickGui;
import ru.method.celebression.utils.render.RenderFrameTracker;

public final class ColorUtils
implements MinecraftClientAccess {
    private static final ByteBuffer PICKER_BUFFER = ByteBuffer.allocateDirect(4);
    private static long pickerFrameId = -1L;
    private static float pickerCacheAX = Float.NaN;
    private static float pickerCacheAY = Float.NaN;
    private static Color pickerCacheAColor;
    private static float pickerCacheBX;
    private static float pickerCacheBY;
    private static Color pickerCacheBColor;
    private static Color primaryColor;
    private static Color secondaryColor;
    private static Color backgroundColor;
    private static Color textColor;
    private static boolean themeFadeEnabled;
    private static boolean blurEnabled;
    private static final boolean LIQUID_GLASS_SHADER_ENABLED = true;

    public static Color safeColor(int n, int n2, int n3, int n4) {
        return new Color(Math.max(0, Math.min(255, n)), Math.max(0, Math.min(255, n2)), Math.max(0, Math.min(255, n3)), Math.max(0, Math.min(255, n4)));
    }

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static void setPrimaryColor(Color color) {
        if (color == null) {
            return;
        }
        primaryColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color getSecondaryColor() {
        return secondaryColor;
    }

    public static void setSecondaryColor(Color color) {
        if (color == null) {
            return;
        }
        secondaryColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(Color color) {
        if (color == null) {
            return;
        }
        backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color getTextColor() {
        return textColor;
    }

    public static void setTextColor(Color color) {
        if (color == null) {
            return;
        }
        textColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static boolean isThemeFadeEnabled() {
        return themeFadeEnabled;
    }

    public static void setThemeFadeEnabled(boolean bl) {
        themeFadeEnabled = bl;
    }

    public static boolean isBlurEnabled() {
        return blurEnabled;
    }

    public static boolean isLiquidGlassActive() {
        return true;
    }

    public static boolean isLiquidGlassShaderActive() {
        return true;
    }

    public static boolean isClickGuiOpen() {
        class_437 class_4372 = aiG == null ? null : ColorUtils.aiG.field_1755;
        return class_4372 instanceof ClickGui;
    }

    public static boolean isHudEditorOpen() {
        class_437 class_4372 = aiG == null ? null : ColorUtils.aiG.field_1755;
        return class_4372 instanceof class_408;
    }

    public static boolean isOverlayGuiOpen() {
        return ColorUtils.isClickGuiOpen() || ColorUtils.isHudEditorOpen() || ColorUtils.isLinkGuiOpen();
    }

    public static boolean isLinkGuiOpen() {
        class_437 class_4372 = aiG == null ? null : ColorUtils.aiG.field_1755;
        return class_4372 instanceof LinkGuiScreen;
    }

    public static boolean blocksGameplayReflectionRecapture() {
        class_437 class_4372 = aiG == null ? null : ColorUtils.aiG.field_1755;
        return class_4372 instanceof LinkGuiScreen || class_4372 instanceof CosmeticsScreen;
    }

    public static void setBlurEnabled(boolean bl) {
        blurEnabled = bl;
    }

    public static Color picker(float f, float f2) {
        long l = RenderFrameTracker.getFrameId();
        float f3 = Math.round(f);
        float f4 = Math.round(f2);
        if (l == pickerFrameId) {
            if (f3 == pickerCacheAX && f4 == pickerCacheAY && pickerCacheAColor != null) {
                return pickerCacheAColor;
            }
            if (f3 == pickerCacheBX && f4 == pickerCacheBY && pickerCacheBColor != null) {
                return pickerCacheBColor;
            }
        } else {
            pickerFrameId = l;
            pickerCacheAX = Float.NaN;
            pickerCacheAY = Float.NaN;
            pickerCacheAColor = null;
            pickerCacheBX = Float.NaN;
            pickerCacheBY = Float.NaN;
            pickerCacheBColor = null;
        }
        Color color = ColorUtils.readPixelColor(f3, f4);
        if (pickerCacheAColor == null) {
            pickerCacheAX = f3;
            pickerCacheAY = f4;
            pickerCacheAColor = color;
        } else {
            pickerCacheBX = f3;
            pickerCacheBY = f4;
            pickerCacheBColor = color;
        }
        return color;
    }

    private static Color readPixelColor(float f, float f2) {
        RenderSystem.assertOnRenderThread();
        ByteBuffer byteBuffer = PICKER_BUFFER;
        byteBuffer.clear();
        double d = aiG.method_22683().method_4495();
        int n = (int)((double)f * d);
        int n2 = (int)((double)((float)aiG.method_22683().method_4502() - f2) * d);
        GL11.glReadPixels((int)n, (int)n2, (int)1, (int)1, (int)6408, (int)5121, (ByteBuffer)byteBuffer);
        byteBuffer.rewind();
        return new Color(byteBuffer.get() & 0xFF, byteBuffer.get() & 0xFF, byteBuffer.get() & 0xFF, byteBuffer.get() & 0xFF);
    }

    public static Color getGlobalColor() {
        return ColorUtils.getGlobalColor(0.0f, 255);
    }

    public static Color getGlobalColor(int n) {
        return ColorUtils.getGlobalColor(0.0f, n);
    }

    public static Color getGlobalColor(float f) {
        return ColorUtils.getGlobalColor(f, 255);
    }

    public static Color getGlobalColor(float f, int n) {
        Color color = ColorUtils.resolveThemeColor(f);
        return ColorUtils.applyAlpha(color, n);
    }

    private static Color resolveThemeColor(float f) {
        if (!themeFadeEnabled) {
            return primaryColor;
        }
        float f2 = f % 1.0f;
        if (f2 < 0.0f) {
            f2 += 1.0f;
        }
        float f3 = f2 <= 0.5f ? f2 * 2.0f : (1.0f - f2) * 2.0f;
        return ColorUtils.mix(primaryColor, secondaryColor, f3);
    }

    public static Color alpha(Color color, int n) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    public static Color fade(Color color, Color color2, float f) {
        int n = (int)((float)color.getRed() * (1.0f - f) + (float)color2.getRed() * f);
        int n2 = (int)((float)color.getGreen() * (1.0f - f) + (float)color2.getGreen() * f);
        int n3 = (int)((float)color.getBlue() * (1.0f - f) + (float)color2.getBlue() * f);
        int n4 = (int)((float)color.getAlpha() * (1.0f - f) + (float)color2.getAlpha() * f);
        return new Color(n, n2, n3, n4);
    }

    public static Color pulse(Color color, long l) {
        l = ColorUtils.clampLong(l, 0L, 30L);
        double d = Math.sin(Math.PI * 2 * (double)((float)l / 30.0f) * (double)((float)(System.currentTimeMillis() - Celebression.getInstance().getInitTime()) / 1000.0f));
        double d2 = (d + 1.0) / 2.0;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)((double)color.getAlpha() * d2));
    }

    public static Color offset(Color color, float f) {
        f = ColorUtils.clampFloat(f, 0.0f, 1.0f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * f));
    }

    public static Color gradient(float f) {
        return ColorUtils.gradient(0.0f, f);
    }

    public static Color gradient(float f, float f2) {
        Color color = ColorUtils.resolveThemeColor(f);
        Color color2 = ColorUtils.resolveThemeColor(f + 0.5f);
        return ColorUtils.gradient(color, color2, f2);
    }

    public static Color gradient(Color color, Color color2, float f) {
        f = ColorUtils.clampFloat(f, 0.0f, 1.0f);
        int n = (int)ColorUtils.lerp(f, color.getRed(), color2.getRed());
        int n2 = (int)ColorUtils.lerp(f, color.getGreen(), color2.getGreen());
        int n3 = (int)ColorUtils.lerp(f, color.getBlue(), color2.getBlue());
        int n4 = (int)ColorUtils.lerp(f, color.getAlpha(), color2.getAlpha());
        return new Color(n, n2, n3, n4);
    }

    private static Color applyAlpha(Color color, int n) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), ColorUtils.clampChannel(n));
    }

    private static int clampChannel(int n) {
        return (int)ColorUtils.clampLong(n, 0L, 255L);
    }

    public static Color mix(Color color, Color color2, float f) {
        f = ColorUtils.clampFloat(f, 0.0f, 1.0f);
        int n = (int)ColorUtils.lerp(f, color.getRed(), color2.getRed());
        int n2 = (int)ColorUtils.lerp(f, color.getGreen(), color2.getGreen());
        int n3 = (int)ColorUtils.lerp(f, color.getBlue(), color2.getBlue());
        int n4 = (int)ColorUtils.lerp(f, color.getAlpha(), color2.getAlpha());
        return new Color(n, n2, n3, n4);
    }

    public static Color getGlassBase() {
        Color color = backgroundColor;
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color getGlassColor(int n) {
        Color color = ColorUtils.getGlassBase();
        int n2 = ColorUtils.clampChannel(Math.round((float)n * 0.55f));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n2);
    }

    public static Color getGlassHighlightColor() {
        Color color = ColorUtils.mix(ColorUtils.getGlassBase(), Color.WHITE, 0.18f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color getGlassOutlineColor() {
        Color color = ColorUtils.mix(ColorUtils.getGlassBase(), Color.WHITE, 0.12f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color getTextColor(int n) {
        return ColorUtils.applyAlpha(textColor, n);
    }

    private static float clampFloat(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    private static long clampLong(long l, long l2, long l3) {
        if (l < l2) {
            return l2;
        }
        if (l > l3) {
            return l3;
        }
        return l;
    }

    private static float lerp(float f, float f2, float f3) {
        return f2 + (f3 - f2) * f;
    }

    @Generated
    private ColorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        pickerCacheBX = Float.NaN;
        pickerCacheBY = Float.NaN;
        primaryColor = new Color(142, 216, 255);
        secondaryColor = new Color(196, 235, 255);
        backgroundColor = new Color(220, 220, 220);
        textColor = new Color(244, 247, 250);
        themeFadeEnabled = true;
        blurEnabled = true;
    }
}

