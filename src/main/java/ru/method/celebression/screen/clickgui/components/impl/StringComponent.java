package ru.method.celebression.screen.clickgui.components.impl;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class StringComponent
extends Component {
    private static final float H_PADDING = 5.0f;
    private static final float LABEL_TOP_PADDING = 2.0f;
    private static final float LABEL_BOTTOM_PADDING = 4.0f;
    private static final float LABEL_FONT_SIZE = 7.5f;
    private static final float FIELD_HEIGHT = 14.0f;
    private static final float FIELD_RADIUS = 2.5f;
    private final StringSetting setting;
    private boolean focused;
    private boolean selected;
    private final Animation animation = new Animation(180L, 1.0, false, Easing.aiR);
    private float scrollOffset = 0.0f;
    private String buffer = "";

    public StringComponent(StringSetting stringSetting) {
        super(stringSetting.getName());
        this.setting = stringSetting;
        this.visible = stringSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        this.height = this.getPreferredHeight();
        float f2 = this.x + 5.0f;
        float f3 = this.y + 2.0f;
        float f4 = this.x + 5.0f;
        float f5 = this.y + 2.0f + 7.5f + 4.0f;
        float f6 = Math.max(1.0f, this.width - 10.0f);
        float f7 = Math.max(1.0f, f6 - 8.0f);
        this.animation.i(this.focused);
        if (!this.focused) {
            this.buffer = this.setting.kg() == null ? "" : this.setting.kg();
        } else {
            this.setting.bI(this.buffer);
            this.buffer = this.setting.kg() == null ? "" : this.setting.kg();
        }
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        String string2 = this.setting.kh();
        String string3 = string2 == null || string2.isEmpty() ? string : class_1074.method_4662((String)string2, (Object[])new Object[0]);
        String string4 = this.buffer == null ? "" : this.buffer;
        boolean bl = !string4.isEmpty();
        boolean bl2 = !bl && !this.focused;
        String string5 = bl2 ? string3 : string4;
        float f8 = bl ? Fonts.ROUNDBOLD.getWidth(string4, 7.5f) : 0.0f;
        float f9 = Fonts.ROUNDBOLD.getWidth(string5, 7.5f);
        if (bl && f8 > f7) {
            if (f8 - this.scrollOffset > f7) {
                this.scrollOffset = f8 - f7;
            }
            if (f8 - this.scrollOffset < 0.0f) {
                this.scrollOffset = f8;
            }
        } else {
            this.scrollOffset = 0.0f;
        }
        this.ctxDrawLabel(class_3322, f2, f3);
        this.drawFieldBackground(class_3322, f4, f5, f6);
        float f10 = f4 + 4.0f;
        float f11 = f5 + 3.1f;
        Render2D.startScissor(class_3322, f4 + 1.0f, f5, f6 - 2.0f, 14.0f);
        if (this.selected && bl) {
            Render2D.drawRoundedRect(class_3322.method_51448(), f10, f11, Math.max(1.0f, f8), Math.max(1.0f, Fonts.ROUNDBOLD.getHeight(7.5f)), 0.0f, new Color(0, 0, 255, 150));
        }
        Color color = ColorUtils.getTextColor(255);
        if (bl2) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.5f), string3, f10, f11, ColorUtils.getTextColor((int)(200.0f * this.animation.rm())));
        } else if (bl) {
            Render2D.drawFontRaw(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.5f), string4, f10 - this.scrollOffset, f11, color);
        }
        if (this.focused) {
            Color color2 = ColorUtils.pulse(color, 15L);
            Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.5f), "|", f10 - this.scrollOffset + f8, f11 - 0.25f, ColorUtils.alpha(color2, (int)((float)color2.getAlpha() * this.animation.rk())));
        }
        Render2D.stopScissor(class_3322);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        float f = this.x + 5.0f;
        float f2 = this.y + 2.0f + 7.5f + 4.0f;
        float f3 = Math.max(1.0f, this.width - 10.0f);
        boolean bl = MathUtils.c(f, f2, f3, 14.0f, (float)d, (float)d2);
        if (this.focused && !bl) {
            this.commitBuffer();
        }
        this.focused = bl;
        this.selected = false;
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        switch (n) {
            case 259: {
                if (this.selected) {
                    this.buffer = "";
                    this.selected = false;
                    if (this.focused) {
                        this.commitBuffer();
                    }
                }
                if (!this.focused || this.buffer == null || this.buffer.isEmpty()) break;
                this.buffer = this.buffer.substring(0, this.buffer.length() - 1);
                this.commitBuffer();
                break;
            }
            case 256: 
            case 257: {
                if (!this.focused) break;
                this.commitBuffer();
                this.focused = false;
                this.selected = false;
                break;
            }
            case 261: {
                if (!this.focused) break;
                this.buffer = "";
                this.selected = false;
                this.commitBuffer();
                break;
            }
            case 67: {
                if (!class_437.method_25441() || !this.focused || !this.selected || this.buffer == null || this.buffer.isEmpty()) break;
                GLFW.glfwSetClipboardString((long)aiG.method_22683().method_4490(), (CharSequence)this.buffer);
                this.selected = false;
                break;
            }
            case 86: {
                if (!class_437.method_25441() || !this.focused || GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490()) == null) break;
                this.selected = false;
                this.buffer = (this.buffer == null ? "" : this.buffer) + GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490());
                this.commitBuffer();
                break;
            }
            case 65: {
                if (!class_437.method_25441() || !this.focused || this.buffer == null || this.buffer.isEmpty()) break;
                this.selected = true;
            }
        }
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
        if (!this.focused) {
            return;
        }
        if (this.selected) {
            this.buffer = "";
        }
        if (this.buffer != null && this.buffer.length() >= this.setting.kf()) {
            return;
        }
        this.buffer = (this.buffer == null ? "" : this.buffer) + c;
        this.selected = false;
        this.commitBuffer();
    }

    @Override
    public void onClose() {
        this.commitBuffer();
        this.focused = false;
        this.selected = false;
        this.scrollOffset = 0.0f;
        this.buffer = "";
    }

    @Override
    public float getLayoutHeight() {
        return this.getPreferredHeight();
    }

    private void commitBuffer() {
        this.setting.bI(this.buffer == null ? "" : this.buffer);
        this.buffer = this.setting.kg() == null ? "" : this.setting.kg();
    }

    private float getPreferredHeight() {
        return 31.5f;
    }

    private void ctxDrawLabel(class_332 class_3322, float f, float f2) {
        Color color = new Color(230, 230, 230, 235);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.5f), class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]), f, f2, color);
    }

    private void drawFieldBackground(class_332 class_3322, float f, float f2, float f3) {
        Color color = new Color(23, 23, 23, 100);
        Color color2 = ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), this.focused ? 200 : 130);
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, 14.0f, 2.5f, color);
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, 14.0f, 2.5f, color2);
    }
}

