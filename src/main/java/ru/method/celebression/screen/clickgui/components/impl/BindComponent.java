package ru.method.celebression.screen.clickgui.components.impl;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_332;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.modules.impl.combat.AutoSwapModule;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class BindComponent
extends Component {
    private final BindSetting setting;
    private boolean binding;
    private final Animation hoverAnimation = new Animation(140L, 1.0, false, Easing.aiT);

    public BindComponent(BindSetting bindSetting) {
        super(bindSetting.getName());
        this.setting = bindSetting;
        this.visible = bindSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2 = this.x + this.width - 44.0f;
        float f3 = this.y + 2.0f;
        float f4 = 40.0f;
        float f5 = this.height - 4.0f;
        boolean bl = MathUtils.c(f2, f3, f4, f5, n, n2);
        this.hoverAnimation.i(bl || this.binding);
        if (LinkGuiRenderContext.isActive()) {
            String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
            TextLayoutUtils.a(class_3322.method_51448(), string, this.x + 2.0f, this.y, this.width - 52.0f);
            String string2 = this.getBindText();
            string2 = this.fitText(string2, f4 - 8.0f);
            TextLayoutUtils.a(class_3322.method_51448(), string2, f2, this.y + 3.0f, f4, 18.0f, Math.max(this.hoverAnimation.rk(), this.binding ? 1.0f : 0.0f));
            return;
        }
        Color color = ColorUtils.getTextColor(255);
        String string = class_1074.method_4662((String)this.setting.getName(), (Object[])new Object[0]);
        float f6 = Fonts.REGULAR.getWidth(string, 7.5f);
        float f7 = Math.max(0.0f, this.width - 52.0f);
        boolean bl2 = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f8 = this.getMarqueeOffset(f6, f7, bl2);
        float f9 = this.y + (this.height - Fonts.REGULAR.getHeight(7.5f)) / 2.0f;
        Render2D.startScissor(class_3322, this.x + 4.0f, this.y, f7, this.height);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f8, f9, color);
        if (f8 > 0.0f && f6 > f7) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + 4.0f - f8 + f6 + 12.0f, f9, color);
        }
        Render2D.stopScissor(class_3322);
        float f10 = 5.0f;
        Color color2 = bl || this.binding ? ColorUtils.getGlobalColor(235) : ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 150);
        Render2D.drawRoundedRect(class_3322.method_51448(), f2, f3, f4, f5, f10, color2);
        float f11 = 1.0f;
        float f12 = f2 + f11;
        float f13 = f3 + f11;
        float f14 = f4 - f11 * 2.0f;
        float f15 = f5 - f11 * 2.0f;
        float f16 = Math.max(0.0f, f10 - f11);
        Color color3 = ColorUtils.getGlassColor(255);
        Render2D.drawLiquidRectClickGuiChip(class_3322.method_51448(), f12, f13, f14, f15, new BorderRadius(f16), new ColorRGBA(color3), 2.5f, 18.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
        int n3 = (int)MathUtils.lerp(90.0f, 165.0f, this.hoverAnimation.rk());
        if (this.binding) {
            n3 = 185;
        }
        Render2D.drawRoundedRect(class_3322.method_51448(), f12, f13, f14, f15, f16, ColorUtils.getGlobalColor(n3));
        Color color4 = this.binding ? ColorUtils.pulse(ColorUtils.getTextColor(255), 18L) : (bl ? ColorUtils.getTextColor(255) : ColorUtils.mix(ColorUtils.getTextColor(255), ColorUtils.getGlassBase(), 0.25f));
        String string3 = this.getBindText();
        string3 = this.fitText(string3, f4 - 8.0f);
        float f17 = Fonts.MEDIUM.getWidth(string3, 7.0f);
        float f18 = Fonts.MEDIUM.getHeight(7.0f);
        float f19 = f2 + (f4 - f17) / 2.0f;
        float f20 = f3 + (f5 - f18) / 2.0f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.0f), string3, f19, f20, color4);
    }

    private String getBindText() {
        int n;
        int n2;
        if (this.binding) {
            return "...";
        }
        if (!((KeyBind)this.setting.jT()).jW() && (n2 = ((KeyBind)this.setting.jT()).c()) <= -95 && n2 >= -120 && (n = n2 + 100) >= 0) {
            ((KeyBind)this.setting.jT()).ah(n);
            ((KeyBind)this.setting.jT()).u(true);
        }
        if ((n2 = ((KeyBind)this.setting.jT()).c()) == -1) {
            return "None";
        }
        if (((KeyBind)this.setting.jT()).jW()) {
            return switch (n2) {
                case 1000 -> "Wheel+";
                case 1001 -> "Wheel-";
                case 0 -> "LMB";
                case 1 -> "RMB";
                case 2 -> "MMB";
                default -> "MB" + (n2 + 1);
            };
        }
        return ((KeyBind)this.setting.jT()).toString();
    }

    private String fitText(String string, float f) {
        if (Fonts.MEDIUM.getWidth(string, 7.0f) <= f) {
            return string;
        }
        String string2 = "...";
        float f2 = Fonts.MEDIUM.getWidth(string2, 7.0f);
        String string3 = string;
        while (string3.length() > 1 && Fonts.MEDIUM.getWidth(string3, 7.0f) + f2 > f) {
            string3 = string3.substring(0, string3.length() - 1);
        }
        return string3 + string2;
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        float f;
        float f2 = 40.0f;
        float f3 = this.x + this.width - 44.0f;
        float f4 = LinkGuiRenderContext.isActive() ? this.y + 3.0f : this.y + 2.0f;
        float f5 = f = LinkGuiRenderContext.isActive() ? 18.0f : this.height - 4.0f;
        if (MathUtils.c(f3, f4, f2, f, (float)d, (float)d2) && n == 0) {
            this.binding = !this.binding;
        } else if (this.binding && n != 0) {
            if (this.isAutoSwapConflict(n, true)) {
                ChatOutput.cG(String.valueOf(class_124.field_1061) + "\u0417\u0430\u043d\u044f\u0442\u043e \u043a\u043e\u043b\u0435\u0441\u043e\u043c \u0430\u0432\u0442\u043e\u0441\u0432\u0430\u043f\u0430");
                this.binding = false;
                return;
            }
            ((KeyBind)this.setting.jT()).ah(n);
            ((KeyBind)this.setting.jT()).u(true);
            this.binding = false;
        } else if (this.binding) {
            this.binding = false;
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        int n;
        if (!this.binding || d4 == 0.0) {
            return false;
        }
        int n2 = n = d4 > 0.0 ? 1000 : 1001;
        if (this.isAutoSwapConflict(n, true)) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "\u0420\u2014\u0420\u00b0\u0420\u0405\u0421\u040f\u0421\u201a\u0420\u0455 \u0420\u0454\u0420\u0455\u0420\u00bb\u0420\u00b5\u0421\u0403\u0420\u0455\u0420\u0458 \u0420\u00b0\u0420\u0406\u0421\u201a\u0420\u0455\u0421\u0403\u0420\u0406\u0420\u00b0\u0420\u0457\u0420\u00b0");
            this.binding = false;
            return true;
        }
        ((KeyBind)this.setting.jT()).ah(n);
        ((KeyBind)this.setting.jT()).u(true);
        this.binding = false;
        return true;
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        if (this.binding) {
            if (n == 256 || n == 261) {
                ((KeyBind)this.setting.jT()).ah(-1);
                ((KeyBind)this.setting.jT()).u(false);
            } else {
                if (this.isAutoSwapConflict(n, false)) {
                    ChatOutput.cG(String.valueOf(class_124.field_1061) + "\u0417\u0430\u043d\u044f\u0442\u043e \u043a\u043e\u043b\u0435\u0441\u043e\u043c \u0430\u0432\u0442\u043e\u0441\u0432\u0430\u043f\u0430");
                    this.binding = false;
                    return;
                }
                ((KeyBind)this.setting.jT()).ah(n);
                ((KeyBind)this.setting.jT()).u(false);
            }
            this.binding = false;
        }
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }

    @Override
    public void onClose() {
        this.binding = false;
    }

    @Override
    public boolean isBinding() {
        return this.binding;
    }

    private boolean isAutoSwapConflict(int n, boolean bl) {
        if (!this.isHelperBindSetting(this.setting.getName())) {
            return false;
        }
        KeyBind keyBind = this.getAutoSwapBind();
        if (keyBind == null || keyBind.isEmpty()) {
            return false;
        }
        if (keyBind.jW() != bl) {
            return false;
        }
        return keyBind.c() == n;
    }

    private boolean isHelperBindSetting(String string) {
        return string.startsWith("settings.funtimehelper.") || string.startsWith("settings.hwhelper.") || string.startsWith("settings.rwhelper.");
    }

    private KeyBind getAutoSwapBind() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return null;
        }
        AutoSwapModule autoSwapModule = celebression.getModuleManager().getModule(AutoSwapModule.class);
        if (autoSwapModule == null) {
            return null;
        }
        for (Setting<?> setting : autoSwapModule.getSettings()) {
            BindSetting bindSetting;
            if (!(setting instanceof BindSetting) || !"settings.autoswap.key".equals((bindSetting = (BindSetting)setting).getName())) continue;
            return (KeyBind)bindSetting.jT();
        }
        return null;
    }
}

