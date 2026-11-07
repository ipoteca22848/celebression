package ru.method.celebression.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Generated;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.manager.ClickRippleManager;
import ru.method.celebression.render.ColorPalette;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.render.UiRenderUtils;
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
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class ModuleSettingsPanel
implements MinecraftClientAccess {
    private static final long Xe = 280L;
    private static final float Xf = 24.0f;
    private static final float Xg = 300.0f;
    private static final float Xh = 10.0f;
    public static final float Xi = 258.0f;
    public static final float Xj = 32.0f;
    private float x;
    private float y;
    private final float Xk = 258.0f;
    private final float Xl = 32.0f;
    private final Module Xm;
    private int Xn;
    private final List<Component> Xo = new ArrayList<Component>();
    private final List<Component> Xp = new ArrayList<Component>();
    private float dragX;
    private float dragY;
    private boolean dragging;
    private boolean Xq;
    private final long Xr = Xs++;
    private static long Xs = 0L;
    private float scroll;
    private float Xt = 24.0f;
    private long Xu;
    private float Xv;
    private float Xw;
    private final AnimatedComponent Xx = new AnimatedComponent(Easing.aiZ);
    private final Animation Xy = new Animation(360L, 1.0, true, Easing.aiZ);
    private final Animation Xz = new Animation(300L, 1.0, false, Easing.aiZ);
    private final Animation XA = new Animation(520L, 1.0, false, Easing.aiZ);
    private final Animation XB = new Animation(180L, 1.0, false, Easing.aiZ);
    private final Animation XC = new Animation(180L, 1.0, false, Easing.aiZ);
    private boolean closing;

    private static float d(Animation animation) {
        if (animation.rn() && animation.rj()) {
            return 1.0f;
        }
        float f = animation.rl();
        if (animation.rn()) {
            float f2 = f * f * (3.0f - 2.0f * f);
            return f >= 0.985f ? 1.0f : f2;
        }
        return f;
    }

    public ModuleSettingsPanel(Module module, float f, float f2) {
        this(module, f, f2, 0);
    }

        public ModuleSettingsPanel(Module module, float f, float f2, int n) {
        this.Xm = module;
        this.x = f;
        this.y = f2;
        this.Xv = f;
        this.Xw = f2;
        this.Xn = n;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                BooleanSetting booleanSetting = (BooleanSetting)setting;
                this.Xo.add(new BooleanComponent(booleanSetting));
                continue;
            }
            if (setting instanceof SliderSetting) {
                SliderSetting sliderSetting = (SliderSetting)setting;
                this.Xo.add(new SliderComponent(sliderSetting));
                continue;
            }
            if (setting instanceof EnumSetting) {
                EnumSetting enumSetting = (EnumSetting)setting;
                this.Xo.add(new EnumComponent(enumSetting));
                continue;
            }
            if (setting instanceof StringSetting) {
                StringSetting stringSetting = (StringSetting)setting;
                this.Xo.add(new StringComponent(stringSetting));
                continue;
            }
            if (setting instanceof MultiSelectSetting) {
                MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting;
                this.Xo.add(new ListComponent(multiSelectSetting));
                continue;
            }
            if (setting instanceof BindSetting) {
                BindSetting bindSetting = (BindSetting)setting;
                this.Xo.add(new BindComponent(bindSetting));
                continue;
            }
            if (setting instanceof ColorSetting) {
                ColorSetting colorSetting = (ColorSetting)setting;
                this.Xo.add(new ColorComponent(colorSetting));
                continue;
            }
            if (!(setting instanceof ButtonSetting)) continue;
            ButtonSetting buttonSetting = (ButtonSetting)setting;
            this.Xo.add(new ActionButtonComponent(buttonSetting));
        }
        if (module instanceof UI) {
            UI ui = (UI)module;
            this.Xo.add(new DualActionButtonComponent("settings.ui.apply", ui::applyScaleSetting, "settings.ui.default", ui::resetScaleSetting));
        }
        for (Component component : this.Xo) {
            component.setHeight(24.0f);
        }
        this.rebuildVisibleComponents();
        LinkGuiRenderContext.begin();
        try {
            this.Xt = this.of();
        }
        finally {
            LinkGuiRenderContext.end();
        }
        this.Xy.reset();
        this.XB.ae(false);
        this.XC.ae(false);
    }

    private float of() {
        float f = Math.min(300.0f, ClickRippleManager.ao(32.0f));
        return class_3532.method_15363((float)(this.getVisibleComponentsHeight() + 16.0f), (float)24.0f, (float)f);
    }

    public float og() {
        return 32.0f + this.Xt;
    }

    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    public void v(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void w(float f, float f2) {
        this.Xv = f;
        this.Xw = f2;
    }

    public float oh() {
        return this.Xw;
    }

    public void oi() {
        this.x = this.Xv;
        this.y = this.Xw;
    }

    private static float ap(float f) {
        float f2 = Math.max(0.0f, Math.min(1.0f, f));
        return f2 * f2 * (3.0f - 2.0f * f2);
    }

        public void render(class_332 class_3322, int n, int n2, float f) {
        float f2;
        this.rebuildVisibleComponents();
        float f3 = this.of();
        long l = System.currentTimeMillis();
        float f4 = this.Xu == 0L ? 0.0f : Math.min(0.1f, (float)(l - this.Xu) / 1000.0f);
        this.Xu = l;
        this.Xt += (f3 - this.Xt) * (1.0f - (float)Math.exp(-14.0f * f4));
        if (Math.abs(f3 - this.Xt) < 0.5f) {
            this.Xt = f3;
        }
        if (!this.dragging && !this.Xq) {
            f2 = 1.0f - (float)Math.exp(-16.0f * f4);
            this.x += (this.Xv - this.x) * f2;
            this.y += (this.Xw - this.y) * f2;
            if (Math.abs(this.Xv - this.x) < 0.3f) {
                this.x = this.Xv;
            }
            if (Math.abs(this.Xw - this.y) < 0.3f) {
                this.y = this.Xw;
            }
        }
        this.Xy.i(this.Xy.rn());
        f2 = 32.0f + this.Xt;
        this.x = ClickRippleManager.t(this.x, 258.0f);
        this.y = ClickRippleManager.u(this.y, f2);
        float f5 = Math.max(0.0f, Math.min(1.0f, this.Xy.rk()));
        float f6 = 0.94f + 0.06f * f5;
        float f7 = this.x + 129.0f;
        float f8 = this.y + f2 / 2.0f;
        float f9 = LinkGuiRenderContext.getFadeMultiplier();
        LinkGuiRenderContext.setFade(f9 * f5);
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(f7, f8, 0.0f);
        class_3322.method_51448().method_22905(f6, f6, 1.0f);
        class_3322.method_51448().method_46416(-f7, -f8, 0.0f);
        LinkGuiBrandRenderer.g(class_3322.method_51448(), this.x, this.y, 258.0f, f2, 10.0f);
        float f10 = this.x + 10.0f;
        float f11 = this.y + 6.0f;
        float f12 = 20.0f;
        boolean bl = this.Xm.isToggled();
        this.Xz.i(bl);
        this.XA.i(bl);
        float f13 = Math.max(this.Xz.rk(), ModuleSettingsPanel.d(this.XA));
        Color color = ThemeTransition.accent();
        LinkGuiBrandRenderer.c(class_3322.method_51448(), f10, f11, f12, 5.0f, bl, f13);
        Color color2 = InterpolationUtils.lerp(InterpolationUtils.e(ColorPalette.aiu, 0.35f), color, f13);
        class_2960 class_29602 = GuiIcons.forCategory(this.Xm.getCategory());
        if (class_29602 != null) {
            GuiIcons.draw(class_3322.method_51448(), class_29602, f10 + f12 / 2.0f, f11 + f12 / 2.0f, 10.0f, color2);
        } else {
            LinkGuiBrandRenderer.a(class_3322.method_51448(), f10, f11, f12, this.Xm.getCategory().fK(), color2, 8.5f);
        }
        this.XC.i(MathUtils.c(f10, f11, f12, f12, n, n2));
        float f14 = this.XC.rk();
        if (f14 > 0.01f) {
            UiRenderUtils.c(class_3322.method_51448(), f10, f11, f12, f12, 5.0f, 1.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(160.0f * f14)));
        }
        float f15 = f10 + 26.0f;
        UiRenderUtils.a(class_3322.method_51448(), Fonts.UNBOUNDED, this.Xm.getDisplayName(), f15, this.y + 7.0f, ColorUtils.getTextColor(255), 9.2f);
        String string = this.Xm.getCategory().name();
        UiRenderUtils.a(class_3322.method_51448(), Fonts.GOLOS, string, f15, this.y + 18.0f, ColorUtils.getTextColor(120), 6.8f);
        float f16 = this.x + 258.0f - 22.0f;
        float f17 = this.y + 6.0f;
        boolean bl2 = MathUtils.c(f16, f17, 16.0f, 16.0f, n, n2);
        this.XB.i(bl2);
        LinkGuiBrandRenderer.h(class_3322.method_51448(), f16, f17, 16.0f, 16.0f, 5.0f, this.XB.rk());
        float f18 = Fonts.ICONSNEW.getHeight(7.0f);
        UiRenderUtils.b(class_3322.method_51448(), Fonts.ICONSNEW, "L", f16 + 8.0f, f17 + (16.0f - f18) / 2.0f, ColorUtils.getTextColor(220), 7.0f);
        LinkGuiBrandRenderer.g(class_3322.method_51448(), this.x + 10.0f, this.y + 32.0f - 1.0f, 238.0f);
        float f19 = this.x + 8.0f;
        float f20 = this.y + 32.0f + 2.0f;
        float f21 = 242.0f;
        LinkGuiBrandRenderer.h(class_3322.method_51448(), f19, f20, f21, this.Xt - 4.0f, 7.0f);
        LinkGuiRenderContext.begin();
        try {
            Render2D.startScissor(class_3322, f19 + 1.0f, f20 + 1.0f, f21 - 2.0f, this.Xt - 6.0f);
            float f22 = Math.max(0.0f, this.getVisibleComponentsHeight() - this.Xt + 12.0f);
            this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)f22);
            float f23 = class_3532.method_15363((float)this.Xx.a(this.scroll, 280L), (float)0.0f, (float)f22);
            float f24 = f20 + 4.0f - f23;
            float f25 = f20 + 2.0f;
            float f26 = f20 + this.Xt - 6.0f;
            for (int i = 0; i < this.Xp.size(); ++i) {
                Component component = this.Xp.get(i);
                component.setX(f19 + 6.0f);
                component.setY(f24);
                component.setWidth(f21 - 12.0f);
                component.setHeight(24.0f);
                float f27 = component.getLayoutHeight();
                float f28 = f27 + component.getAddHeight().get().floatValue();
                if (this.intersectsVerticalRange(f24, f28, f25, f26)) {
                    if (i > 0) {
                        LinkGuiBrandRenderer.g(class_3322.method_51448(), f19 + 5.0f, f24 - 1.0f, f21 - 10.0f);
                    }
                    component.render(class_3322, n, n2, f);
                }
                f24 += f28;
            }
            Render2D.stopScissor(class_3322);
        }
        finally {
            LinkGuiRenderContext.end();
        }
        class_3322.method_51448().method_22909();
        Render2D.clearScissor(class_3322);
        LinkGuiRenderContext.setFade(f9 * f5);
        LinkGuiRenderContext.begin();
        try {
            for (Component component : this.Xp) {
                if (!component.hasActiveOverlay()) continue;
                component.renderOverlay(class_3322, n, n2, f);
            }
        }
        finally {
            LinkGuiRenderContext.end();
        }
        LinkGuiRenderContext.setFade(f9);
    }

        public boolean mouseClicked(double d, double d2, int n) {
        if (this.closing) {
            return false;
        }
        LinkGuiRenderContext.begin();
        try {
            float[] fArray = this.toLocalMouse(d, d2);
            float f = fArray[0];
            float f2 = fArray[1];
            if (this.handleExtendedClick(d, d2, n)) {
                boolean bl = true;
                return bl;
            }
            float f3 = 32.0f + this.Xt;
            if (!MathUtils.c(this.x, this.y, 258.0f, f3, f, f2)) {
                boolean bl = false;
                return bl;
            }
            float f4 = this.x + 258.0f - 22.0f;
            float f5 = this.y + 6.0f;
            float f6 = this.x + 10.0f;
            float f7 = this.y + 6.0f;
            if (MathUtils.c(f4, f5, 16.0f, 16.0f, f, f2) && n == 0) {
                this.close();
            } else if (MathUtils.c(f6, f7, 20.0f, 20.0f, f, f2) && n == 0) {
                this.Xm.toggle();
            } else if (MathUtils.c(this.x, this.y, 230.0f, 32.0f, f, f2) && n == 0) {
                this.dragging = true;
                this.Xq = true;
                this.dragX = (float)d - this.x;
                this.dragY = (float)d2 - this.y;
            } else if (MathUtils.c(this.x + 8.0f, this.y + 32.0f, 242.0f, this.Xt, f, f2)) {
                for (Component component : this.Xp) {
                    component.mouseClicked(f, f2, n);
                }
            }
            boolean bl = true;
            return bl;
        }
        finally {
            LinkGuiRenderContext.end();
        }
    }

    public void a(double d, double d2, int n) {
        if (this.closing || !this.dragging || n != 0) {
            return;
        }
        this.x = (float)d - this.dragX;
        this.y = (float)d2 - this.dragY;
    }

        public void mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            this.dragging = false;
        }
        if (this.closing) {
            return;
        }
        LinkGuiRenderContext.begin();
        try {
            float[] fArray = this.toLocalMouse(d, d2);
            this.rebuildVisibleComponents();
            for (Component component : this.Xp) {
                component.mouseReleased(fArray[0], fArray[1], n);
            }
        }
        finally {
            LinkGuiRenderContext.end();
        }
    }

        public void mouseScrolled(double d, double d2, double d3, double d4) {
        if (this.closing) {
            return;
        }
        LinkGuiRenderContext.begin();
        try {
            float[] fArray = this.toLocalMouse(d, d2);
            this.rebuildVisibleComponents();
            for (Component component : this.Xp) {
                if (!component.mouseScrolled(fArray[0], fArray[1], d3, d4)) continue;
                return;
            }
            if (MathUtils.c(this.x + 8.0f, this.y + 32.0f, 242.0f, this.Xt, fArray[0], fArray[1])) {
                this.scroll -= (float)(d4 * 12.0);
                this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)Math.max(0.0f, this.getVisibleComponentsHeight() - this.Xt + 14.0f));
            }
        }
        finally {
            LinkGuiRenderContext.end();
        }
    }

        public void keyPressed(int n, int n2, int n3) {
        if (this.closing) {
            return;
        }
        LinkGuiRenderContext.begin();
        try {
            this.rebuildVisibleComponents();
            for (Component component : this.Xp) {
                component.keyPressed(n, n2, n3);
            }
        }
        finally {
            LinkGuiRenderContext.end();
        }
    }

        public void charTyped(char c, int n) {
        if (this.closing) {
            return;
        }
        LinkGuiRenderContext.begin();
        try {
            this.rebuildVisibleComponents();
            for (Component component : this.Xp) {
                component.charTyped(c, n);
            }
        }
        finally {
            LinkGuiRenderContext.end();
        }
    }

    public void resetInputState() {
        for (Component component : this.Xo) {
            component.onClose();
        }
    }

    public boolean isFinishedClosing() {
        return this.closing && this.Xy.rk() <= 0.01f;
    }

    public Module getModule() {
        return this.Xm;
    }

    public boolean hasActiveBinding() {
        this.rebuildVisibleComponents();
        for (Component component : this.Xp) {
            if (!component.isBinding()) continue;
            return true;
        }
        return false;
    }

    public static float oj() {
        return 300.0f;
    }

    public void requestClose() {
        this.close();
    }

    public boolean isClosing() {
        return this.closing;
    }

    private void close() {
        if (!this.closing) {
            this.resetInputState();
            this.closing = true;
            this.Xy.i(false);
        }
    }

    private boolean handleExtendedClick(double d, double d2, int n) {
        boolean bl = false;
        this.rebuildVisibleComponents();
        for (Component component : this.Xp) {
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
        this.Xp.clear();
        for (Component component : this.Xo) {
            if (!component.getVisible().get().booleanValue()) continue;
            this.Xp.add(component);
        }
    }

    private float getVisibleComponentsHeight() {
        float f = 0.0f;
        for (Component component : this.Xp) {
            f += component.getLayoutHeight() + component.getAddHeight().get().floatValue() + 1.0f;
        }
        return f;
    }

    private boolean intersectsVerticalRange(float f, float f2, float f3, float f4) {
        return f + f2 >= f3 && f <= f4;
    }

    private float[] toLocalMouse(double d, double d2) {
        return new float[]{(float)d, (float)d2};
    }

    @Generated
    public int ol() {
        return this.Xn;
    }

    @Generated
    public void au(int n) {
        this.Xn = n;
    }

    @Generated
    public boolean om() {
        return this.Xq;
    }

    @Generated
    public long on() {
        return this.Xr;
    }
}
