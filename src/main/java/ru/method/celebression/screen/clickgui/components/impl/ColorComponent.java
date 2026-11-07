package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.class_1044;
import net.minecraft.class_1074;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.screen.clickgui.ClickGui;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ColorComponent
extends Component {
    private static final class_2960 HUE_TEXTURE = Celebression.id("client/hue/hue.png");
    private static final class_2960 EMPTY_TEXTURE = Celebression.id("client/hue/empty.png");
    private static final class_2960 CLOSE_TEXTURE = Celebression.id("client/colorpicker/xmark.png");
    private static final int EMPTY_TEXTURE_WIDTH = 200;
    private static final int EMPTY_TEXTURE_HEIGHT = 24;
    private static final float CLOSE_BUTTON_SIZE = 14.0f;
    private static final float PICKER_PADDING = 10.0f;
    private static final float PICKER_TITLE_HEIGHT = 18.0f;
    private static final float SV_SIZE = 110.0f;
    private static final float HUE_BAR_WIDTH = 14.0f;
    private static final float HUE_BAR_GAP = 8.0f;
    private static final float OPACITY_SLIDER_HEIGHT = 11.0f;
    private static final float OPACITY_SECTION_GAP = 8.0f;
    private static final float OPACITY_LABEL_GAP = 10.0f;
    private static final float OPACITY_PREVIEW_WIDTH = 26.0f;
    private static final float OPACITY_PREVIEW_GAP = 6.0f;
    private static final float HEX_SECTION_GAP = 8.0f;
    private static final float HEX_LABEL_GAP = 9.0f;
    private static final float HEX_FIELD_HEIGHT = 11.0f;
    private static final float HEX_FIELD_RADIUS = 4.0f;
    private static final int HEX_MAX_LENGTH = 9;
    private final ColorSetting setting;
    private boolean hovering;
    private boolean editing;
    private float pickerX;
    private float pickerY;
    private float pickerWidth;
    private float pickerHeight;
    private boolean pickerManualPosition;
    private boolean draggingPicker;
    private float pickerDragOffsetX;
    private float pickerDragOffsetY;
    private boolean closing;
    private final Animation pickerAnimation = new Animation(140L, 1.0, true, Easing.aiT);
    private boolean draggingSv;
    private boolean draggingHue;
    private boolean draggingOpacity;
    private float hue;
    private float saturation;
    private float value;
    private float opacity;
    private boolean hsvInitialized;
    private boolean hexFocused;
    private boolean hexSelected;
    private String hexBuffer = "";
    private float hexFieldX;
    private float hexFieldY;
    private float hexFieldW;
    private float hexFieldH;
    private float previewButtonX;
    private float previewButtonY;
    private float previewButtonSize;

    public ColorComponent(ColorSetting colorSetting) {
        super(colorSetting.getName());
        this.setting = colorSetting;
        this.visible = colorSetting::jS;
        this.setAddHeight(() -> Float.valueOf(0.0f));
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2;
        this.hovering = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        this.previewButtonSize = this.height - 8.0f;
        this.previewButtonX = this.x + this.width - this.previewButtonSize - 4.0f;
        this.previewButtonY = this.y + (this.height - this.previewButtonSize) / 2.0f;
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f3 = this.x + 4.0f;
        float f4 = Math.max(0.0f, this.previewButtonX - f3 - 4.0f);
        if (LinkGuiRenderContext.isActive()) {
            TextLayoutUtils.a(class_3322.method_51448(), string, this.x + 2.0f, this.y, f4);
        } else {
            float f5 = Fonts.REGULAR.getWidth(string, 7.5f);
            f2 = this.getMarqueeOffset(f5, f4, this.hovering);
            Render2D.startScissor(class_3322, f3, this.y, f4, this.height);
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f3 - f2, this.y + 5.0f, ColorUtils.getTextColor(255));
            if (f2 > 0.0f && f5 > f4) {
                Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, f3 - f2 + f5 + 12.0f, this.y + 5.0f, ColorUtils.getTextColor(255));
            }
            Render2D.stopScissor(class_3322);
        }
        Color color = (Color)this.setting.jT();
        f2 = Math.min(5.0f, this.previewButtonSize * 0.5f);
        Render2D.drawRoundedRect(class_3322.method_51448(), this.previewButtonX, this.previewButtonY, this.previewButtonSize, this.previewButtonSize, f2, color);
        Render2D.drawBorder(class_3322.method_51448(), this.previewButtonX, this.previewButtonY, this.previewButtonSize, this.previewButtonSize, f2, 0.5f, new ColorRGBA(ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 170)));
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            if (this.editing) {
                this.editing = false;
                this.draggingSv = false;
                this.draggingHue = false;
                this.draggingOpacity = false;
            }
            return;
        }
        if (this.editing) {
            this.updatePickerBounds();
            this.updateHexFieldBounds();
            if (this.isInsideClose(d, d2)) {
                this.requestClose();
                this.draggingPicker = false;
                this.draggingSv = false;
                this.draggingHue = false;
                this.draggingOpacity = false;
                this.hexFocused = false;
                this.hexSelected = false;
                return;
            }
            if (this.isInsideTitleBar(d, d2) && !this.isInsideClose(d, d2)) {
                this.pickerManualPosition = true;
                this.draggingPicker = true;
                this.pickerDragOffsetX = (float)d - this.pickerX;
                this.pickerDragOffsetY = (float)d2 - this.pickerY;
                this.hexFocused = false;
                this.hexSelected = false;
                return;
            }
            if (this.isInsideSv(d, d2)) {
                this.draggingSv = true;
                this.hexFocused = false;
                this.hexSelected = false;
                this.updateSv(d, d2);
                return;
            }
            if (this.isInsideHueBar(d, d2)) {
                this.draggingHue = true;
                this.hexFocused = false;
                this.hexSelected = false;
                this.updateHue(d2);
                return;
            }
            if (this.isInsideOpacitySlider(d, d2)) {
                this.draggingOpacity = true;
                this.hexFocused = false;
                this.hexSelected = false;
                this.updateOpacity(d);
                return;
            }
            if (this.isInsideHexField(d, d2)) {
                this.focusHexInput();
                return;
            }
            this.hexFocused = false;
            this.hexSelected = false;
            if (this.hovering || !this.isInsideExtended(d, d2)) {
                this.requestClose();
                this.draggingPicker = false;
                this.draggingSv = false;
                this.draggingHue = false;
                this.draggingOpacity = false;
            }
            return;
        }
        if (MathUtils.c(this.previewButtonX, this.previewButtonY, this.previewButtonSize, this.previewButtonSize, (float)d, (float)d2)) {
            this.editing = true;
            this.pickerManualPosition = false;
            this.draggingPicker = false;
            this.draggingSv = false;
            this.draggingHue = false;
            this.draggingOpacity = false;
            this.hsvInitialized = false;
            this.closing = false;
            this.hexFocused = false;
            this.hexSelected = false;
            this.hexBuffer = "";
            this.pickerAnimation.ae(true);
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            this.draggingPicker = false;
            this.draggingSv = false;
            this.draggingHue = false;
            this.draggingOpacity = false;
        }
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        if (!this.editing || !this.hexFocused) {
            return;
        }
        switch (n) {
            case 256: {
                this.hexFocused = false;
                this.hexSelected = false;
                this.syncHexBuffer();
                break;
            }
            case 257: {
                this.applyHexBuffer();
                this.hexFocused = false;
                this.hexSelected = false;
                this.syncHexBuffer();
                break;
            }
            case 259: {
                if (this.hexSelected) {
                    this.hexBuffer = "";
                    this.hexSelected = false;
                    return;
                }
                if (this.hexBuffer == null || this.hexBuffer.isEmpty()) break;
                this.hexBuffer = this.hexBuffer.substring(0, this.hexBuffer.length() - 1);
                this.hexBuffer = this.normalizeHexInput(this.hexBuffer);
                this.applyHexBuffer();
                break;
            }
            case 261: {
                this.hexBuffer = "";
                this.hexSelected = false;
                break;
            }
            case 67: {
                if (!class_437.method_25441()) break;
                String string = this.formatHex((Color)this.setting.jT());
                GLFW.glfwSetClipboardString((long)aiG.method_22683().method_4490(), (CharSequence)string);
                this.hexSelected = false;
                break;
            }
            case 86: {
                String string;
                if (!class_437.method_25441() || (string = GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490())) == null || string.isBlank()) break;
                this.hexBuffer = this.normalizeHexInput(string);
                this.hexSelected = false;
                this.applyHexBuffer();
                break;
            }
            case 65: {
                if (!class_437.method_25441()) break;
                this.hexSelected = this.hexBuffer != null && !this.hexBuffer.isEmpty();
            }
        }
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
        if (!this.editing || !this.hexFocused) {
            return;
        }
        if (!this.isHexChar(c) && c != '#') {
            return;
        }
        if (this.hexSelected) {
            this.hexBuffer = "";
        }
        String string = (this.hexBuffer == null ? "" : this.hexBuffer) + c;
        this.hexBuffer = this.normalizeHexInput(string);
        this.hexSelected = false;
        this.applyHexBuffer();
    }

    @Override
    public void renderOverlay(class_332 class_3322, int n, int n2, float f) {
        Object object;
        if (!this.editing) {
            return;
        }
        if (this.hasActiveDrag() && !this.isLeftMouseDown()) {
            this.stopDragging();
        }
        this.pickerAnimation.i(!this.closing);
        float f2 = this.pickerAnimation.rk();
        if (this.closing && f2 <= 0.01f) {
            this.editing = false;
            this.closing = false;
            this.stopDragging();
            this.hexFocused = false;
            this.hexSelected = false;
            this.hexBuffer = "";
            return;
        }
        this.updatePickerBounds();
        this.syncHsvFromSettingIfNeeded();
        this.syncHexBuffer();
        if (this.draggingPicker) {
            this.pickerManualPosition = true;
            this.pickerX = (float)n - this.pickerDragOffsetX;
            this.pickerY = (float)n2 - this.pickerDragOffsetY;
            this.clampPickerToScreen();
        }
        if (this.draggingSv) {
            this.updateSv(n, n2);
        }
        if (this.draggingHue) {
            this.updateHue(n2);
        }
        if (this.draggingOpacity) {
            this.updateOpacity(n);
        }
        float f3 = LinkGuiRenderContext.isActive() ? LinkGuiRenderContext.getFadeMultiplier() : 1.0f;
        int n3 = Math.max(0, Math.min(255, Math.round(255.0f * f2 * f3)));
        class_3322.method_51448().method_22903();
        float f4 = this.pickerX + this.pickerWidth / 2.0f;
        float f5 = this.pickerY + this.pickerHeight / 2.0f;
        class_3322.method_51448().method_46416(f4, f5, 0.0f);
        class_3322.method_51448().method_22905(f2, f2, 1.0f);
        class_3322.method_51448().method_46416(-f4, -f5, 0.0f);
        if (LinkGuiRenderContext.isActive()) {
            object = LinkGuiBrandRenderer.mA();
            Render2D.drawRoundedRect(class_3322.method_51448(), this.pickerX, this.pickerY, this.pickerWidth, this.pickerHeight, 20.0f, new Color(((Color)object).getRed(), ((Color)object).getGreen(), ((Color)object).getBlue(), n3));
            Color accentColor = ThemeTransition.accent();
            Render2D.drawBorder(class_3322.method_51448(), this.pickerX, this.pickerY, this.pickerWidth, this.pickerHeight, 20.0f, 0.9f, new ColorRGBA(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), Math.round((float)n3 * 0.6f))));
        } else {
            object = ColorUtils.getGlassBase();
            Render2D.drawRoundedRect(class_3322.method_51448(), this.pickerX, this.pickerY, this.pickerWidth, this.pickerHeight, 20.0f, new Color(((Color)object).getRed(), ((Color)object).getGreen(), ((Color)object).getBlue(), n3));
            Color accentColor = ColorUtils.getPrimaryColor();
            Render2D.drawBorder(class_3322.method_51448(), this.pickerX, this.pickerY, this.pickerWidth, this.pickerHeight, 20.0f, 0.9f, new ColorRGBA(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), Math.round((float)n3 * 0.6f))));
        }
        object = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f6 = 8.5f;
        float f7 = Fonts.SEMIBOLD.getWidth((String)object, f6);
        float f8 = this.pickerX + (this.pickerWidth - f7) / 2.0f;
        float f9 = this.pickerY + 5.0f;
        float f10 = 14.0f;
        float f11 = this.getCloseX();
        float f12 = f11 - 2.0f - f7;
        f8 = MathUtils.k(f8, this.pickerX + 10.0f, Math.max(this.pickerX + 10.0f, f12));
        Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(f6), (String)object, f8, f9, ColorUtils.getTextColor(n3));
        float f13 = this.getCloseY();
        boolean bl = MathUtils.c(f11, f13, f10, f10, n, n2);
        Render2D.drawRoundedRect(class_3322.method_51448(), f11, f13, f10, f10, 4.0f, ColorUtils.getGlassColor(bl ? Math.min(255, n3) : Math.min(220, n3)));
        float f14 = 0.8f;
        this.drawSmoothTexture(class_3322, CLOSE_TEXTURE, f11 + f14, f13 + f14, f10 - f14 * 2.0f, f10 - f14 * 2.0f, 0.0f, ColorUtils.getTextColor(n3), true);
        float f15 = this.pickerX + 10.0f;
        float f16 = this.pickerY + 18.0f;
        float f17 = this.getContentWidth();
        this.drawSvSquare(class_3322, f15, f16, 110.0f);
        float f18 = f15 + 110.0f + 8.0f;
        float f19 = f16;
        this.drawHueBar(class_3322, f18, f19, 14.0f, 110.0f);
        float f20 = f16 + 110.0f + 8.0f;
        String string = class_1074.method_4662((String)"celebression.colorpicker.opacity", (Object[])new Object[0]);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), string, f15, f20, new Color(230, 232, 240, n3));
        String string2 = Math.round(this.opacity * 100.0f) + "%";
        float f21 = Fonts.ROUNDBOLD.getWidth(string2, 7.0f);
        float f22 = this.getOpacitySliderWidth();
        float f23 = this.getOpacitySliderX();
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), string2, f23 + f22 - f21, f20, new Color(230, 232, 240, n3));
        float f24 = f20 + 10.0f;
        this.drawOpacityPreview(class_3322, f15, f24, 26.0f, 11.0f);
        this.drawOpacitySlider(class_3322, f23, f24, f22, 11.0f);
        float f25 = f24 + 11.0f + 8.0f;
        float f26 = f25 + 9.0f;
        float f27 = f15;
        float f28 = f17;
        float f29 = 11.0f;
        this.hexFieldX = f27;
        this.hexFieldY = f26;
        this.hexFieldW = f28;
        this.hexFieldH = f29;
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), "HEX", f27, f25, new Color(230, 232, 240, n3));
        int n4 = Math.min(100, n3);
        int n5 = Math.min(this.hexFocused ? 200 : 130, n3);
        Color color = new Color(23, 23, 23, n4);
        Color color2 = ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), n5);
        Render2D.drawRoundedRect(class_3322.method_51448(), f27, f26, f28, f29, 4.0f, color);
        Render2D.drawRoundedRect(class_3322.method_51448(), f27, f26, f28, f29, 4.0f, color2);
        String string3 = this.getHexDisplayText();
        float f30 = 7.5f;
        float f31 = Fonts.REGULAR.getWidth(string3, f30);
        float f32 = f27 + 4.0f;
        float f33 = Fonts.REGULAR.getHeight(f30);
        float f34 = f26 + (f29 - f33) / 2.0f;
        Render2D.startScissor(class_3322, f27 + 1.0f, f26 + 1.0f, f28 - 2.0f, f29 - 2.0f);
        if (this.hexFocused && this.hexSelected && !string3.isEmpty()) {
            Render2D.drawRoundedRect(class_3322.method_51448(), f32, f34, Math.max(1.0f, f31), Math.max(1.0f, Fonts.REGULAR.getHeight(f30)), 0.0f, new Color(0, 120, 215, Math.min(150, n3)));
        }
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(f30), string3, f32, f34, ColorUtils.getTextColor(n3));
        if (this.hexFocused) {
            Color color3 = ColorUtils.pulse(ColorUtils.getTextColor(n3), 15L);
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(f30), "|", f32 + f31, f34, ColorUtils.alpha(color3, Math.min(n3, color3.getAlpha())));
        }
        Render2D.stopScissor(class_3322);
        class_3322.method_51448().method_22909();
    }

    private void updatePickerBounds() {
        float f = aiG.method_22683().method_4486();
        float f2 = aiG.method_22683().method_4502();
        float f3 = 6.0f;
        this.pickerWidth = this.getContentWidth() + 20.0f;
        this.pickerHeight = 195.0f;
        if (this.pickerManualPosition) {
            this.clampPickerToScreen();
            return;
        }
        float f4 = this.x + this.width + f3;
        ClickGui clickGui = Celebression.getInstance().getClickGui();
        float f5 = clickGui != null ? clickGui.getGuiLeft() : 0.0f;
        float f6 = clickGui != null ? clickGui.getGuiRight() : 0.0f;
        boolean bl = f6 > f5;
        float f7 = this.x + this.width;
        if (bl) {
            f4 = this.x >= f6 ? this.x + this.width + f3 : (f7 <= f5 ? this.x - f3 - this.pickerWidth : this.x + this.width + f3);
        }
        if (f4 + this.pickerWidth + f3 > f) {
            f4 = this.x - f3 - this.pickerWidth;
        }
        if (f4 < f3) {
            f4 = f3;
        }
        this.pickerX = f4;
        float f8 = this.y;
        if (f8 + this.pickerHeight + f3 > f2) {
            f8 = f2 - this.pickerHeight - f3;
        }
        if (f8 < f3) {
            f8 = f3;
        }
        this.pickerY = f8;
    }

    private void clampPickerToScreen() {
        float f = aiG.method_22683().method_4486();
        float f2 = aiG.method_22683().method_4502();
        float f3 = 6.0f;
        this.pickerX = MathUtils.k(this.pickerX, f3, Math.max(f3, f - this.pickerWidth - f3));
        this.pickerY = MathUtils.k(this.pickerY, f3, Math.max(f3, f2 - this.pickerHeight - f3));
    }

    private float getContentWidth() {
        return 132.0f;
    }

    private float getCloseX() {
        float f = 14.0f;
        return this.pickerX + this.pickerWidth - 10.0f - f;
    }

    private float getCloseY() {
        return this.pickerY + 3.0f;
    }

    private boolean isInsideClose(double d, double d2) {
        float f = 14.0f;
        float f2 = this.getCloseX();
        float f3 = this.getCloseY();
        return d >= (double)f2 && d <= (double)(f2 + f) && d2 >= (double)f3 && d2 <= (double)(f3 + f);
    }

    private boolean isInsideTitleBar(double d, double d2) {
        return d >= (double)this.pickerX && d <= (double)(this.pickerX + this.pickerWidth) && d2 >= (double)this.pickerY && d2 <= (double)(this.pickerY + 18.0f);
    }

    private boolean isInsideExtended(double d, double d2) {
        return d >= (double)this.pickerX && d <= (double)(this.pickerX + this.pickerWidth) && d2 >= (double)this.pickerY && d2 <= (double)(this.pickerY + this.pickerHeight);
    }

    private boolean isInsideSv(double d, double d2) {
        float f = this.pickerX + 10.0f;
        float f2 = this.pickerY + 18.0f;
        return d >= (double)f && d <= (double)(f + 110.0f) && d2 >= (double)f2 && d2 <= (double)(f2 + 110.0f);
    }

    private boolean isInsideHueBar(double d, double d2) {
        float f = this.pickerX + 10.0f;
        float f2 = this.pickerY + 18.0f;
        float f3 = f + 110.0f + 8.0f;
        float f4 = f2;
        return d >= (double)f3 && d <= (double)(f3 + 14.0f) && d2 >= (double)f4 && d2 <= (double)(f4 + 110.0f);
    }

    private boolean isInsideOpacitySlider(double d, double d2) {
        float f = this.pickerY + 18.0f;
        float f2 = f + 110.0f + 8.0f;
        float f3 = f2 + 10.0f;
        float f4 = this.getOpacitySliderX();
        float f5 = this.getOpacitySliderWidth();
        return d >= (double)f4 && d <= (double)(f4 + f5) && d2 >= (double)f3 && d2 <= (double)(f3 + 11.0f);
    }

    private void syncHsvFromSettingIfNeeded() {
        if (this.hsvInitialized) {
            return;
        }
        Color color = (Color)this.setting.jT();
        float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = fArray[0];
        this.saturation = fArray[1];
        this.value = fArray[2];
        this.opacity = (float)color.getAlpha() / 255.0f;
        this.hsvInitialized = true;
    }

    private void applyCurrentColor() {
        int n = Color.HSBtoRGB(this.hue, this.saturation, this.value);
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        int n5 = this.clampInt(Math.round(this.opacity * 255.0f), 0, 255);
        this.setting.d(new Color(n2, n3, n4, n5));
    }

    private void updateSv(double d, double d2) {
        float f = this.pickerX + 10.0f;
        float f2 = this.pickerY + 18.0f;
        float f3 = (float)((d - (double)f) / 110.0);
        float f4 = (float)((d2 - (double)f2) / 110.0);
        this.saturation = MathUtils.k(f3, 0.0f, 1.0f);
        this.value = 1.0f - MathUtils.k(f4, 0.0f, 1.0f);
        this.applyCurrentColor();
    }

    private void updateHue(double d) {
        float f = this.pickerY + 18.0f;
        float f2 = (float)((d - (double)f) / 110.0);
        this.hue = MathUtils.k(f2, 0.0f, 1.0f);
        this.applyCurrentColor();
    }

    private void updateOpacity(double d) {
        float f = this.getOpacitySliderX();
        float f2 = this.getOpacitySliderWidth();
        float f3 = (float)((d - (double)f) / (double)f2);
        this.opacity = MathUtils.k(f3, 0.0f, 1.0f);
        this.applyCurrentColor();
    }

    public boolean isEditing() {
        return this.editing && !this.closing;
    }

    @Override
    public void onClose() {
        this.stopDragging();
        this.hexFocused = false;
        this.hexSelected = false;
    }

    @Override
    public boolean hasActiveOverlay() {
        return this.editing;
    }

    @Override
    public boolean isExtendedHovered(double d, double d2) {
        return this.editing && this.isInsideExtended(d, d2);
    }

    private void drawSvSquare(class_332 class_3322, float f, float f2, float f3) {
        Color color = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        float f4 = Math.min(6.0f, f3 * 0.5f);
        Render2D.drawRoundedHorizontalGradient(class_3322.method_51448(), f, f2, f3, f3, f4, Color.WHITE, color);
        Render2D.drawRoundedVerticalGradient(class_3322.method_51448(), f, f2, f3, f3, f4, new Color(0, 0, 0, 0), new Color(0, 0, 0, 255));
        float f5 = f + this.saturation * f3;
        float f6 = f2 + (1.0f - this.value) * f3;
        float f7 = 8.0f;
        float f8 = f7 / 2.0f;
        f5 = MathUtils.k(f5, f + f8, f + f3 - f8);
        f6 = MathUtils.k(f6, f2 + f8, f2 + f3 - f8);
        f5 = Math.round(f5);
        f6 = Math.round(f6);
        float f9 = f7 * 0.5f;
        Render2D.drawRoundedRect(class_3322.method_51448(), f5 - f7 / 2.0f - 1.0f, f6 - f7 / 2.0f - 1.0f, f7 + 2.0f, f7 + 2.0f, f9 + 1.0f, new Color(0, 0, 0, 150));
        Render2D.drawRoundedRect(class_3322.method_51448(), f5 - f7 / 2.0f, f6 - f7 / 2.0f, f7, f7, f9, new Color(255, 255, 255, 225));
        Color color2 = this.currentRgb();
        float f10 = 6.2f;
        float f11 = f10 * 0.5f;
        Render2D.drawRoundedRect(class_3322.method_51448(), f5 - f10 / 2.0f, f6 - f10 / 2.0f, f10, f10, f11, new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), 255));
    }

    private void drawHueBar(class_332 class_3322, float f, float f2, float f3, float f4) {
        float f5 = Math.min(6.0f, Math.min(f3, f4) * 0.5f);
        Render2D.drawTexture(class_3322.method_51448(), f, f2, f3, f4, f5, HUE_TEXTURE, Color.WHITE);
        float f6 = f2 + this.hue * f4;
        f6 = MathUtils.k(f6, f2 + 2.0f, f2 + f4 - 2.0f);
        float f7 = Math.max(1.0f, f3 - 4.0f);
        float f8 = 1.6f;
        float f9 = f + 2.0f;
        float f10 = f6 - f8 / 2.0f;
        Render2D.drawRoundedRect(class_3322.method_51448(), f9 - 0.6f, f10 - 0.9f, f7 + 1.2f, f8 + 1.8f, (f8 + 1.8f) * 0.5f, new Color(0, 0, 0, 140));
        Render2D.drawRoundedRect(class_3322.method_51448(), f9, f10, f7, f8, f8 * 0.5f, new Color(255, 255, 255, 235));
    }

    private void drawOpacitySlider(class_332 class_3322, float f, float f2, float f3, float f4) {
        float f5 = Math.max(1.0f, (float)Math.round(f3));
        float f6 = Math.max(1.0f, (float)Math.round(f4));
        float f7 = Math.round(f);
        float f8 = Math.round(f2);
        float f9 = Math.min(6.0f, Math.min(f5, f6) * 0.5f);
        float f10 = f5;
        float f11 = f6;
        float f12 = f7;
        float f13 = f8;
        float f14 = f10;
        float f15 = f11;
        float f16 = f9;
        this.drawEmptyGrid(class_3322, f12, f13, f14, f15, f16);
        Color color = this.currentRgb();
        Render2D.drawRoundedHorizontalGradient(class_3322.method_51448(), f12, f13, f14, f15, f16, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0), new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
        float f17 = f7 + this.opacity * f10;
        f17 = MathUtils.k(f17, f7 + 2.0f, f7 + f10 - 2.0f);
        float f18 = f8 + f11 / 2.0f;
        float f19 = 1.6f;
        float f20 = Math.max(1.0f, f11 - 4.0f);
        float f21 = MathUtils.k(f17 - f19 / 2.0f, f7 + 2.0f, f7 + f10 - 2.0f - f19);
        float f22 = f8 + (f11 - f20) / 2.0f;
        Render2D.drawRoundedRect(class_3322.method_51448(), f21 - 0.9f, f22 - 0.6f, f19 + 1.8f, f20 + 1.2f, (f19 + 1.8f) * 0.5f, new Color(0, 0, 0, 140));
        Render2D.drawRoundedRect(class_3322.method_51448(), f21, f22, f19, f20, f19 * 0.5f, new Color(255, 255, 255, 235));
    }

    private void requestClose() {
        if (!this.editing) {
            return;
        }
        this.closing = true;
        this.hexFocused = false;
        this.hexSelected = false;
        this.stopDragging();
        this.pickerAnimation.i(false);
    }

    private boolean hasActiveDrag() {
        return this.draggingPicker || this.draggingSv || this.draggingHue || this.draggingOpacity;
    }

    private void stopDragging() {
        this.draggingPicker = false;
        this.draggingSv = false;
        this.draggingHue = false;
        this.draggingOpacity = false;
    }

    private boolean isLeftMouseDown() {
        if (aiG == null || aiG.method_22683() == null) {
            return false;
        }
        return GLFW.glfwGetMouseButton((long)aiG.method_22683().method_4490(), (int)0) == 1;
    }

    private void drawSmoothTexture(class_332 class_3322, class_2960 class_29602, float f, float f2, float f3, float f4, float f5, Color color, boolean bl) {
        if (class_3322 == null || class_29602 == null || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f6 = Math.max(1.0f, (float)Math.round(f3));
        float f7 = Math.max(1.0f, (float)Math.round(f4));
        float f8 = Math.round(f);
        float f9 = Math.round(f2);
        class_1044 class_10443 = aiG.method_1531().method_4619(class_29602);
        if (class_10443 != null) {
            class_10443.method_4527(bl, false);
        }
        Render2D.drawTexture(class_3322.method_51448(), f8, f9, f6, f7, f5, class_10443, color);
    }

    private void drawEmptyGrid(class_332 class_3322, float f, float f2, float f3, float f4, float f5) {
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        this.drawSmoothTexture(class_3322, EMPTY_TEXTURE, f, f2, f3, f4, f5, Color.WHITE, true);
    }

    private float getOpacitySliderWidth() {
        float f = this.getContentWidth();
        float f2 = Math.max(1.0f, f - 26.0f - 6.0f);
        float f3 = 0.45833334f;
        float f4 = Math.round(200.0f * f3);
        return Math.min(f2, Math.max(1.0f, f4));
    }

    private float getOpacitySliderX() {
        float f = this.getOpacitySliderWidth();
        float f2 = this.pickerX + 10.0f;
        return f2 + 26.0f + 6.0f;
    }

    private void drawOpacityPreview(class_332 class_3322, float f, float f2, float f3, float f4) {
        float f5 = Math.min(6.0f, Math.min(f3, f4) * 0.5f);
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, f4, f5, (Color)this.setting.jT());
    }

    private void updateHexFieldBounds() {
        float f = this.pickerX + 10.0f;
        float f2 = this.pickerY + 18.0f;
        float f3 = f2 + 110.0f + 8.0f;
        float f4 = f3 + 10.0f;
        float f5 = f4 + 11.0f + 8.0f;
        float f6 = f5 + 9.0f;
        this.hexFieldX = f;
        this.hexFieldY = f6;
        this.hexFieldW = this.getContentWidth();
        this.hexFieldH = 11.0f;
    }

    private boolean isInsideHexField(double d, double d2) {
        return d >= (double)this.hexFieldX && d <= (double)(this.hexFieldX + this.hexFieldW) && d2 >= (double)this.hexFieldY && d2 <= (double)(this.hexFieldY + this.hexFieldH);
    }

    private void focusHexInput() {
        this.hexFocused = true;
        this.hexSelected = true;
        if (this.hexBuffer == null || this.hexBuffer.isEmpty()) {
            this.hexBuffer = this.formatHex((Color)this.setting.jT());
        }
    }

    private void syncHexBuffer() {
        if (!this.hexFocused) {
            this.hexBuffer = this.formatHex((Color)this.setting.jT());
        }
    }

    private void applyHexBuffer() {
        Color color = this.parseHex(this.hexBuffer);
        if (color != null) {
            this.setColorFromHex(color);
        }
    }

    private String normalizeHexInput(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim().toUpperCase(Locale.ROOT);
        if (string2.startsWith("#")) {
            string2 = string2.substring(1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 8;
        for (int i = 0; i < string2.length() && stringBuilder.length() < n; ++i) {
            char c = string2.charAt(i);
            if (!this.isHexChar(c)) continue;
            stringBuilder.append(c);
        }
        if (stringBuilder.length() == 0) {
            return "";
        }
        return "#" + String.valueOf(stringBuilder);
    }

    private String getHexDisplayText() {
        if (this.hexBuffer == null || this.hexBuffer.isEmpty()) {
            return "#";
        }
        return this.hexBuffer;
    }

    private boolean isHexChar(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
    }

    private Color parseHex(String string) {
        int n;
        if (string == null) {
            return null;
        }
        String string2 = string.trim();
        if (string2.startsWith("#")) {
            string2 = string2.substring(1);
        }
        if (string2.length() != 6 && string2.length() != 8) {
            return null;
        }
        for (n = 0; n < string2.length(); ++n) {
            if (this.isHexChar(string2.charAt(n))) continue;
            return null;
        }
        try {
            n = Integer.parseInt(string2.substring(0, 2), 16);
            int n2 = Integer.parseInt(string2.substring(2, 4), 16);
            int n3 = Integer.parseInt(string2.substring(4, 6), 16);
            int n4 = string2.length() == 8 ? Integer.parseInt(string2.substring(6, 8), 16) : 255;
            return new Color(n, n2, n3, n4);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private String formatHex(Color color) {
        if (color == null) {
            return "#FFFFFF";
        }
        String string = String.format(Locale.ROOT, "%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        if (color.getAlpha() < 255) {
            return "#" + string + String.format(Locale.ROOT, "%02X", color.getAlpha());
        }
        return "#" + string;
    }

    private void setColorFromHex(Color color) {
        if (color == null) {
            return;
        }
        this.setting.d(color);
        float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = fArray[0];
        this.saturation = fArray[1];
        this.value = fArray[2];
        this.opacity = (float)color.getAlpha() / 255.0f;
        this.hsvInitialized = true;
    }

    private Color currentRgb() {
        int n = Color.HSBtoRGB(this.hue, this.saturation, this.value);
        return new Color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }

    private int clampInt(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        return Math.min(n, n3);
    }
}
