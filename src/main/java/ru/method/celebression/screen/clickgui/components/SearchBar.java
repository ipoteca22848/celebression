package ru.method.celebression.screen.clickgui.components;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_1074;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class SearchBar
implements MinecraftClientAccess {
    private float x;
    private float y;
    private float width;
    private float height;
    private String searchText = "";
    private boolean focused = false;
    private boolean selected = false;
    private long startTime = 0L;
    private final Animation focusAnim = new Animation(160L, 1.0, false, Easing.aiZ);

    public SearchBar(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        Object object;
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x, this.y, this.width, this.height, Math.min(14.0f, this.height / 2.0f), new Color(255, 255, 255, 18));
        this.focusAnim.i(this.focused);
        float f2 = this.focusAnim.rk();
        if (f2 > 0.01f) {
            object = ColorUtils.getPrimaryColor();
            Render2D.drawBorder(class_3322.method_51448(), this.x, this.y, this.width, this.height, 14.0f, 1.2f, new ColorRGBA(new Color(((Color)object).getRed(), ((Color)object).getGreen(), ((Color)object).getBlue(), Math.max(0, Math.min(255, Math.round(180.0f * f2))))));
        }
        object = this.searchText.isEmpty() ? class_1074.method_4662((String)"clickgui.search.placeholder", (Object[])new Object[0]) : this.searchText;
        Color color = ColorUtils.getTextColor(255);
        Color color2 = new Color(150, 150, 160);
        Color color3 = this.searchText.isEmpty() ? SearchBar.lerpColor(color2, color, f2 * 0.5f) : color;
        float f3 = Fonts.MEDIUM.getWidth((String)object, 8.0f);
        float f4 = this.x + (this.width - f3) / 2.0f;
        float f5 = this.y + this.height / 2.0f - Fonts.MEDIUM.getHeight(8.0f) / 2.0f;
        Render2D.startScissor(class_3322, this.x + 5.0f, this.y, this.width - 10.0f, this.height);
        if (this.selected && !this.searchText.isEmpty()) {
            Render2D.drawRoundedRect(class_3322.method_51448(), f4, f5, f3, Fonts.MEDIUM.getHeight(8.0f), 0.0f, new Color(0, 120, 215, 150));
        }
        if (!((String)object).trim().isEmpty()) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(8.0f), (String)object, f4, f5, color3);
        }
        if (this.focused && !this.selected) {
            float f6;
            if (this.startTime == 0L) {
                this.startTime = System.currentTimeMillis();
            }
            if (this.searchText.isEmpty()) {
                f6 = f4;
            } else {
                float f7 = Fonts.MEDIUM.getWidth(this.searchText, 8.0f);
                f6 = this.x + (this.width - f7) / 2.0f + f7;
            }
            long l = System.currentTimeMillis() - this.startTime;
            double d = (double)(l % 1000L) / 1000.0;
            float f8 = (float)(0.5 + 0.5 * Math.sin(d * Math.PI * 2.0));
            int n3 = (int)(255.0f * f8);
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(8.0f), "|", f6, f5, ColorUtils.getTextColor(n3));
        } else if (!this.focused) {
            this.startTime = 0L;
        }
        Render2D.stopScissor(class_3322);
    }

    private static Color lerpColor(Color color, Color color2, float f) {
        f = Math.max(0.0f, Math.min(1.0f, f));
        return new Color(Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f), Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f), Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f), Math.round((float)color.getAlpha() + (float)(color2.getAlpha() - color.getAlpha()) * f));
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            boolean bl;
            this.focused = bl = MathUtils.c(this.x - 4.0f, this.y - 4.0f, this.width + 8.0f, this.height + 8.0f, (float)d, (float)d2);
            this.selected = false;
            if (this.focused) {
                this.startTime = System.currentTimeMillis();
            }
            return bl;
        }
        return false;
    }

    public boolean keyPressed(int n, int n2, int n3) {
        if (!this.focused) {
            return false;
        }
        switch (n) {
            case 259: 
            case 261: {
                if (this.selected) {
                    this.searchText = "";
                    this.selected = false;
                } else if (!this.searchText.isEmpty()) {
                    this.searchText = this.searchText.substring(0, this.searchText.length() - 1);
                }
                if (this.searchText.isEmpty()) {
                    this.startTime = System.currentTimeMillis();
                }
                return true;
            }
            case 256: {
                this.focused = false;
                this.selected = false;
                if (!this.searchText.isEmpty()) {
                    this.searchText = "";
                }
                this.startTime = 0L;
                return true;
            }
            case 86: {
                if (!class_437.method_25441()) break;
                String string = GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490());
                if (string != null) {
                    if (this.selected) {
                        this.searchText = string;
                        this.selected = false;
                    } else {
                        this.searchText = this.searchText + string;
                    }
                }
                return true;
            }
            case 65: {
                if (!class_437.method_25441() || this.searchText.isEmpty()) break;
                this.selected = true;
                return true;
            }
            case 67: {
                if (!class_437.method_25441() || !this.selected || this.searchText.isEmpty()) break;
                GLFW.glfwSetClipboardString((long)aiG.method_22683().method_4490(), (CharSequence)this.searchText);
                return true;
            }
            case 88: {
                if (!class_437.method_25441() || !this.selected || this.searchText.isEmpty()) break;
                GLFW.glfwSetClipboardString((long)aiG.method_22683().method_4490(), (CharSequence)this.searchText);
                this.searchText = "";
                this.selected = false;
                return true;
            }
        }
        return false;
    }

    public boolean charTyped(char c, int n) {
        if (!this.focused) {
            return false;
        }
        if (Character.isISOControl(c)) {
            return false;
        }
        if (this.selected) {
            this.searchText = String.valueOf(c);
            this.selected = false;
        } else {
            this.searchText = this.searchText + c;
        }
        this.playTypeSound();
        return true;
    }

    private void playTypeSound() {
        try {
            if (aiG.method_1483() == null) {
                return;
            }
            float f = 1.7f + (float)(Math.random() * 0.35);
            aiG.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)((class_3414)class_3417.field_15015.comp_349()), (float)f, (float)0.25f));
        }
        catch (Throwable throwable) {
        }
    }

    public boolean isEmpty() {
        return this.searchText.trim().isEmpty();
    }

    public void setFocused(boolean bl) {
        this.focused = bl;
        this.selected = false;
        if (bl) {
            this.startTime = System.currentTimeMillis();
        }
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void clear() {
        this.searchText = "";
        this.focused = false;
        this.selected = false;
        this.startTime = 0L;
    }

    public void reset() {
        this.searchText = "";
        this.focused = false;
        this.selected = false;
        this.startTime = 0L;
    }

    public void setSearchText(String string) {
        this.searchText = string == null ? "" : string;
        this.selected = false;
    }

    @Generated
    public void setX(float f) {
        this.x = f;
    }

    @Generated
    public void setY(float f) {
        this.y = f;
    }

    @Generated
    public void setWidth(float f) {
        this.width = f;
    }

    @Generated
    public void setHeight(float f) {
        this.height = f;
    }

    @Generated
    public String getSearchText() {
        return this.searchText;
    }
}

