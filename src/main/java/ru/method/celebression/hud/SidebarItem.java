package ru.method.celebression.hud;

import java.awt.Color;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.ui.ModuleTab;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.ui.UiInteractionState;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class SidebarItem {
    private static final long Zn = 520L;
    private final UiInteractionState Zo;
    private static final ModuleTab[] Zp = ModuleTab.values();
    private final Animation[] Zq = new Animation[Zp.length];
    private final Animation[] Zr = new Animation[Zp.length];
    private final AnimatedComponent Zs = new AnimatedComponent(Easing.aiR);
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean Zt;

    public SidebarItem(UiInteractionState uiInteractionState) {
        this.Zo = uiInteractionState;
        for (int i = 0; i < this.Zq.length; ++i) {
            this.Zq[i] = new Animation(420L, 1.0, false, Easing.aiR);
            this.Zr[i] = new Animation(360L, 1.0, false, Easing.aiR);
        }
    }

    public void b(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void tick() {
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        this.c(class_3322.method_51448(), n, n2);
    }

    private void c(class_4587 class_45872, int n, int n2) {
        LinkGuiBrandRenderer.g(class_45872, this.x, this.y, this.width, this.height, 12.0f);
        ModuleTab[] moduleTabArray = Zp;
        float f = 6.0f;
        float f2 = (this.width - f * 2.0f) / (float)moduleTabArray.length;
        float f3 = this.height - f * 2.0f;
        float f4 = this.y + f;
        int n3 = 0;
        for (int i = 0; i < moduleTabArray.length; ++i) {
            if (this.Zo.mG() != moduleTabArray[i].mM()) continue;
            n3 = i;
            break;
        }
        float f5 = this.x + f + (float)n3 * f2 + 2.0f;
        float f6 = f2 - 4.0f;
        if (!this.Zt) {
            this.Zs.a(f5, 1L);
            this.Zt = true;
        }
        float f7 = this.Zs.a(f5, 520L);
        LinkGuiBrandRenderer.h(class_45872, f7, f4, f6, f3, 9.0f, 0.92f);
        float f8 = this.x + f;
        for (int i = 0; i < moduleTabArray.length; ++i) {
            ModuleTab moduleTab = moduleTabArray[i];
            boolean bl = this.Zo.mG() == moduleTab.mM();
            boolean bl2 = MathUtils.c(f8 + 2.0f, f4, f2 - 4.0f, f3, n, n2);
            this.Zq[i].i(bl);
            this.Zr[i].i(bl2 && !bl);
            float f9 = this.Zq[i].rk();
            float f10 = this.Zr[i].rk();
            float f11 = f10 * 4.5f;
            class_45872.method_22903();
            class_45872.method_46416(0.0f, -f11, 0.0f);
            if (!bl) {
                LinkGuiBrandRenderer.h(class_45872, f8 + 2.0f, f4, f2 - 4.0f, f3, 9.0f, f10 * 0.45f);
            }
            Color color = InterpolationUtils.lerp(ThemeTransition.d(150), ThemeTransition.accent(), bl ? 1.0f : Math.max(f10 * 0.8f, f9 * 0.35f));
            float f12 = f4 + f3 / 2.0f - 6.5f;
            float f13 = f4 + 9.0f;
            float f14 = class_3532.method_16439((float)Math.max(f9, f10 * 0.65f), (float)f12, (float)f13);
            class_2960 class_29602 = GuiIcons.forCategory(moduleTab.mM());
            if (class_29602 != null) {
                GuiIcons.draw(class_45872, class_29602, f8 + f2 / 2.0f, f14 + 6.5f, 15.0f, color);
            } else {
                UiRenderUtils.b(class_45872, Fonts.ICONSNEW, moduleTab.mN(), f8 + f2 / 2.0f, f14, color, 12.5f);
            }
            float f15 = Math.max(f9, f10 * 0.75f);
            if (f15 > 0.06f) {
                Color color2 = InterpolationUtils.lerp(ThemeTransition.d(145), ThemeTransition.d(245), bl ? 1.0f : f15);
                float f16 = 6.8f;
                float f17 = UiRenderUtils.a(Fonts.MEDIUM, f16);
                float f18 = f8 + f2 / 2.0f;
                float f19 = f4 + f3 - f17 / 2.0f - 5.0f;
                UiRenderUtils.b(class_45872, Fonts.MEDIUM, moduleTab.label(), f18, f19 - f17 / 2.0f + 0.5f, color2, f16);
            }
            class_45872.method_22909();
            f8 += f2;
        }
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (!MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
            return false;
        }
        if (n == 0) {
            ModuleTab[] moduleTabArray = Zp;
            float f = 6.0f;
            float f2 = (this.width - f * 2.0f) / (float)moduleTabArray.length;
            float f3 = this.height - f * 2.0f;
            float f4 = this.y + f;
            float f5 = this.x + f;
            for (ModuleTab moduleTab : moduleTabArray) {
                if (MathUtils.c(f5 + 2.0f, f4, f2 - 4.0f, f3, (float)d, (float)d2)) {
                    this.Zo.a(moduleTab);
                    return true;
                }
                f5 += f2;
            }
        }
        return true;
    }
}

