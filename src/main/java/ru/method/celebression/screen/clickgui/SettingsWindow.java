package ru.method.celebression.screen.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.screen.clickgui.components.impl.ActionButtonComponent;
import ru.method.celebression.screen.clickgui.components.impl.BindComponent;
import ru.method.celebression.screen.clickgui.components.impl.BooleanComponent;
import ru.method.celebression.screen.clickgui.components.impl.ColorComponent;
import ru.method.celebression.screen.clickgui.components.impl.DualActionButtonComponent;
import ru.method.celebression.screen.clickgui.components.impl.EnumComponent;
import ru.method.celebression.screen.clickgui.components.impl.ListComponent;
import ru.method.celebression.screen.clickgui.components.impl.SliderComponent;
import ru.method.celebression.screen.clickgui.components.impl.StringComponent;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class SettingsWindow
implements MinecraftClientAccess {
    private static final long SCROLL_ANIMATION_MS = 260L;
    private float x;
    private float y;
    private float width;
    private float height;
    private final Module module;
    private final List<Component> components = new ArrayList<Component>();
    private final List<Component> visibleComponents = new ArrayList<Component>();
    private float dragX;
    private float dragY;
    private boolean dragging;
    private float scroll;
    private final AnimatedComponent scrollAnimation = new AnimatedComponent(Easing.aiZ);
    private static final float WINDOW_HEIGHT = 150.0f;
    public static final float WINDOW_WIDTH = 120.0f;
    public static final float HEADER_HEIGHT = 20.0f;
    private final Animation openAnimation = new Animation(130L, 1.0, true, Easing.aiT);
    private final Animation toggleAnimation = new Animation(180L, 1.0, true, Easing.ajb);
    private boolean closing = false;

    public SettingsWindow(Module module, float f, float f2) {
        this.module = module;
        this.x = f;
        this.y = f2;
        this.width = 120.0f;
        this.height = 20.0f;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                this.components.add(new BooleanComponent((BooleanSetting)setting));
                continue;
            }
            if (setting instanceof SliderSetting) {
                this.components.add(new SliderComponent((SliderSetting)setting));
                continue;
            }
            if (setting instanceof EnumSetting) {
                this.components.add(new EnumComponent((EnumSetting)setting));
                continue;
            }
            if (setting instanceof StringSetting) {
                this.components.add(new StringComponent((StringSetting)setting));
                continue;
            }
            if (setting instanceof MultiSelectSetting) {
                this.components.add(new ListComponent((MultiSelectSetting)setting));
                continue;
            }
            if (setting instanceof BindSetting) {
                this.components.add(new BindComponent((BindSetting)setting));
                continue;
            }
            if (setting instanceof ColorSetting) {
                this.components.add(new ColorComponent((ColorSetting)setting));
                continue;
            }
            if (!(setting instanceof ButtonSetting)) continue;
            this.components.add(new ActionButtonComponent((ButtonSetting)setting));
        }
        if (module instanceof UI) {
            UI uI = (UI)module;
            this.components.add(new DualActionButtonComponent("settings.ui.apply", uI::applyScaleSetting, "settings.ui.default", uI::resetScaleSetting));
        }
        this.openAnimation.reset();
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        this.rebuildVisibleComponents();
        this.openAnimation.i(this.openAnimation.rn());
        this.toggleAnimation.i(this.module.isToggled());
        if (this.dragging) {
            this.x = (float)n - this.dragX;
            this.y = (float)n2 - this.dragY;
        }
        class_3322.method_51448().method_22903();
        float f2 = this.openAnimation.rk();
        class_3322.method_51448().method_46416(this.x + this.width / 2.0f, this.y + (this.height + 150.0f) / 2.0f, 0.0f);
        class_3322.method_51448().method_22905(f2, f2, 1.0f);
        class_3322.method_51448().method_46416(-(this.x + this.width / 2.0f), -(this.y + (this.height + 150.0f) / 2.0f), 0.0f);
        int n3 = (int)(255.0f * f2);
        Color color = ColorUtils.getGlassColor(255);
        Render2D.drawLiquidRectClickGuiSettings(class_3322.method_51448(), this.x, this.y, this.width, this.height + 150.0f, new BorderRadius(14.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        Color color2 = ColorUtils.getTextColor(n3);
        Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(9.0f), this.module.getDisplayName(), this.x + 6.0f, this.y + 5.0f, color2);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), "L", this.x + this.width - 15.0f, this.y + 5.0f, color2);
        float f3 = this.x + this.width - 38.0f;
        float f4 = this.y + (this.height - 8.0f) / 2.0f;
        float f5 = 16.0f;
        float f6 = 8.0f;
        Color color3 = ColorUtils.getGlobalColor();
        Color color4 = new Color(200, 206, 220, (int)(120.0f * ((float)n3 / 255.0f)));
        Color color5 = ColorUtils.getGlobalColor((int)((float)n3 * this.toggleAnimation.rl()));
        Render2D.drawRoundedRect(class_3322.method_51448(), f3, f4, f5, f6, 2.5f, color4);
        Render2D.drawRoundedRect(class_3322.method_51448(), f3, f4, f5 * this.toggleAnimation.rk(), f6, 2.5f, color5);
        float f7 = 7.0f;
        float f8 = f4 + (f6 - f7) / 2.0f;
        float f9 = f3 + (f6 - f7) / 2.0f;
        Color color6 = new Color(245, 247, 255, n3);
        Render2D.drawRoundedRect(class_3322.method_51448(), f9 + (f5 - f7) * this.toggleAnimation.rk(), f8, f7, f7, 2.5f, color6);
        Render2D.startScissor(class_3322, this.x, this.y + this.height, this.width, 150.0f);
        float f10 = Math.max(0.0f, this.getVisibleComponentsHeight() - 150.0f);
        this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)f10);
        float f11 = class_3532.method_15363((float)this.scrollAnimation.a(this.scroll, 260L), (float)0.0f, (float)f10);
        float f12 = this.y + this.height - f11;
        float f13 = this.y + this.height;
        float f14 = f13 + 150.0f;
        for (Component component : this.visibleComponents) {
            component.setX(this.x + 2.0f);
            component.setY(f12);
            component.setWidth(this.width - 4.0f);
            float f15 = component.getLayoutHeight();
            component.setHeight(f15);
            float f16 = f15 + component.getAddHeight().get().floatValue();
            if (this.intersectsVerticalRange(f12, f16, f13, f14)) {
                component.render(class_3322, n, n2, f);
            }
            f12 += f16;
        }
        Render2D.stopScissor(class_3322);
        class_3322.method_51448().method_22909();
        Render2D.clearScissor(class_3322);
        for (Component component : this.visibleComponents) {
            if (!component.hasActiveOverlay()) continue;
            component.renderOverlay(class_3322, n, n2, f);
        }
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (this.closing) {
            return false;
        }
        float[] fArray = this.toLocalMouse(d, d2);
        float f = fArray[0];
        float f2 = fArray[1];
        if (this.handleExtendedClick(d, d2, n)) {
            return true;
        }
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height + 150.0f, f, f2);
        if (!bl) {
            return false;
        }
        if (MathUtils.c(this.x + this.width - 18.0f, this.y, 18.0f, this.height, f, f2) && n == 0) {
            this.close();
        } else if (MathUtils.c(this.x + this.width - 38.0f, this.y, 20.0f, this.height, f, f2) && n == 0) {
            this.module.toggle();
        } else if (MathUtils.c(this.x, this.y, this.width - 38.0f, this.height, f, f2) && n == 0) {
            this.dragging = true;
            this.dragX = (float)d - this.x;
            this.dragY = (float)d2 - this.y;
        } else if (MathUtils.c(this.x, this.y + this.height, this.width, 150.0f, f, f2)) {
            for (Component component : this.visibleComponents) {
                component.mouseClicked(f, f2, n);
            }
        }
        return true;
    }

    public void mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            this.dragging = false;
        }
        if (this.closing) {
            return;
        }
        float[] fArray = this.toLocalMouse(d, d2);
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            component.mouseReleased(fArray[0], fArray[1], n);
        }
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        if (this.closing) {
            return;
        }
        float[] fArray = this.toLocalMouse(d, d2);
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            if (!component.mouseScrolled(fArray[0], fArray[1], d3, d4)) continue;
            return;
        }
        if (MathUtils.c(this.x, this.y + this.height, this.width, 150.0f, fArray[0], fArray[1])) {
            this.scroll -= (float)(d4 * 10.0);
            this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)Math.max(0.0f, this.getVisibleComponentsHeight() - 150.0f));
        }
    }

    public void keyPressed(int n, int n2, int n3) {
        if (this.closing) {
            return;
        }
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            component.keyPressed(n, n2, n3);
        }
    }

    public void charTyped(char c, int n) {
        if (this.closing) {
            return;
        }
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            component.charTyped(c, n);
        }
    }

    private void close() {
        if (!this.closing) {
            this.resetInputState();
            this.closing = true;
            this.openAnimation.i(false);
        }
    }

    public void resetInputState() {
        for (Component component : this.components) {
            component.onClose();
        }
    }

    public boolean isFinishedClosing() {
        return this.closing && (double)this.openAnimation.rk() <= 0.01;
    }

    public Module getModule() {
        return this.module;
    }

    public boolean hasActiveBinding() {
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            if (!component.isBinding()) continue;
            return true;
        }
        return false;
    }

    public static float getWindowHeight() {
        return 150.0f;
    }

    private boolean handleExtendedClick(double d, double d2, int n) {
        boolean bl = false;
        this.rebuildVisibleComponents();
        for (Component component : this.visibleComponents) {
            ColorComponent colorComponent;
            boolean bl2 = component.isExtendedHovered(d, d2);
            if (!bl2 && component instanceof ColorComponent && (colorComponent = (ColorComponent)component).isEditing()) {
                bl2 = true;
            }
            if (!bl2) continue;
            component.mouseClicked(d, d2, n);
            bl = true;
        }
        return bl;
    }

    private void rebuildVisibleComponents() {
        this.visibleComponents.clear();
        for (Component component : this.components) {
            if (!component.getVisible().get().booleanValue()) continue;
            this.visibleComponents.add(component);
        }
    }

    private float getVisibleComponentsHeight() {
        float f = 0.0f;
        for (Component component : this.visibleComponents) {
            float f2 = component.getLayoutHeight();
            f += f2 + component.getAddHeight().get().floatValue();
        }
        return f;
    }

    private boolean intersectsVerticalRange(float f, float f2, float f3, float f4) {
        return f + f2 >= f3 && f <= f4;
    }

    private float[] toLocalMouse(double d, double d2) {
        float f = this.openAnimation.rk();
        float f2 = this.x + this.width / 2.0f;
        float f3 = this.y + (this.height + 150.0f) / 2.0f;
        return new float[]{(float)((double)f2 + (d - (double)f2) / (double)f), (float)((double)f3 + (d2 - (double)f3) / (double)f)};
    }
}

