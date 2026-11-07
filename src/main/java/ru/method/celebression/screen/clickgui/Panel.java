package ru.method.celebression.screen.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.modules.impl.client.ElementsModule;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.screen.clickgui.components.impl.ColorComponent;
import ru.method.celebression.screen.clickgui.components.impl.ModuleComponent;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class Panel
implements MinecraftClientAccess {
    private static final float CONTENT_VIEW_HEIGHT = 230.0f;
    private static final float SETTINGS_BOTTOM_GAP = 10.0f;
    private static final long SCROLL_ANIMATION_MS = 260L;
    private float x;
    private float y;
    private float width;
    private float height;
    private final ModuleCategory category;
    private final List<ModuleComponent> components = new ArrayList<ModuleComponent>();
    private ModuleComponent activeModule;
    private ModuleComponent lastSettingsModule;
    private final Animation openAnimation = new Animation(70L, 1.0, true, Easing.aiR);
    private final Animation pageAnimation = new Animation(240L, 1.0, false, Easing.aiT);
    private final Animation moduleHeaderAnimation = new Animation(130L, 1.0, false, Easing.aiT);
    private final AnimatedComponent scrollAnimation = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent settingsScrollAnimation = new AnimatedComponent(Easing.aiZ);
    private final List<ModuleComponent> filteredComponents = new ArrayList<ModuleComponent>();
    private final List<Component> visibleSettingsComponents = new ArrayList<Component>();
    private float scroll;
    private float settingsScroll;
    private boolean open = true;
    private String cachedSearchQuery = "";
    private float cachedFilteredTotalHeight = -1.0f;
    private ModuleComponent cachedVisibleSettingsModule;
    private float cachedVisibleSettingsHeight = -1.0f;
    private int cachedModuleCount = -1;

    public Panel(float f, float f2, float f3, float f4, ModuleCategory moduleCategory) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.category = moduleCategory;
        for (Module module : Panel.panelModules(moduleCategory)) {
            ModuleComponent moduleComponent = new ModuleComponent(module);
            moduleComponent.setParentPanel(this);
            this.components.add(moduleComponent);
        }
        this.components.sort(Comparator.comparing(Component::getName));
    }

    public void render(class_332 class_3322, int n, int n2, float f, String string) {
        boolean bl;
        float f2;
        float f3;
        float f4;
        float f5;
        this.syncModuleComponentsIfNeeded();
        this.ensureFilteredComponents(string);
        this.openAnimation.i(this.open);
        boolean bl2 = this.activeModule != null || this.lastSettingsModule != null && this.pageAnimation.rk() > 0.01f;
        this.pageAnimation.i(this.activeModule != null);
        ModuleComponent moduleComponent = this.activeModule != null ? this.activeModule : this.lastSettingsModule;
        this.ensureVisibleSettingsComponents(moduleComponent);
        boolean bl3 = this.activeModule != null || this.pageAnimation.rk() > 0.01f;
        this.moduleHeaderAnimation.i(bl3);
        boolean bl4 = moduleComponent != null && (this.pageAnimation.rk() > 0.01f || this.activeModule != null);
        Color color = ColorUtils.getGlassColor(255);
        Color color2 = ColorUtils.getTextColor(255);
        Color color3 = ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 70);
        float f6 = this.pageAnimation.rk();
        float f7 = this.moduleHeaderAnimation.rk();
        float f8 = 12.0f * f7;
        boolean bl5 = moduleComponent != null && f7 > 0.01f;
        float f9 = this.y + this.height + f8;
        float f10 = 250.0f * this.openAnimation.rk() + this.height * this.openAnimation.rm();
        float f11 = Math.max(0.0f, 230.0f * this.openAnimation.rk() - (f9 - (this.y + this.height)));
        float f12 = bl2 ? Math.max(0.0f, f11 - 10.0f) : f11;
        float f13 = Math.max(0.0f, f10 - (f9 - this.y) - 1.0f);
        f12 = Math.min(f12, f13);
        float f14 = Math.max(0.0f, this.cachedFilteredTotalHeight - f12);
        float f15 = Math.max(0.0f, this.cachedVisibleSettingsHeight - f12);
        if (!bl2) {
            this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)f14);
        } else {
            this.settingsScroll = class_3532.method_15363((float)this.settingsScroll, (float)0.0f, (float)f15);
        }
        f10 = 250.0f * this.openAnimation.rk() + this.height * this.openAnimation.rm();
        Render2D.drawLiquidRectClickGuiPanel(class_3322.method_51448(), this.x, this.y, this.width, f10, new BorderRadius(14.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        float f16 = 9.0f;
        float f17 = Fonts.SEMIBOLD.getWidth(this.category.name(), f16);
        float f18 = this.x + (this.width - f17) / 2.0f - 4.0f;
        float f19 = this.y + (this.height - Fonts.SEMIBOLD.getHeight(f16)) / 2.0f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(f16), this.category.name(), f18, f19, color2);
        float f20 = this.category == ModuleCategory.qn ? -2.0f : 0.0f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), this.category.fK(), this.x + this.width - 19.0f + f20, this.y + 5.0f, color2);
        if (bl5) {
            String string2 = moduleComponent.getModule().getDisplayName();
            f5 = Fonts.SEMIBOLD.getWidth("<", 7.5f);
            f4 = Fonts.SEMIBOLD.getWidth(string2, 8.0f);
            f3 = f5 + 4.0f + f4;
            f2 = this.y + this.height;
            bl = MathUtils.c(this.x + 6.0f, f2 - 2.0f, f3, 11.0f, n, n2);
            float f21 = (1.0f - f7) * 6.0f;
            Color object = bl ? ColorUtils.alpha(color2, (int)(255.0f * f7)) : ColorUtils.alpha(color2, (int)(220.0f * f7));
            Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(7.5f), "<", this.x + 6.0f + f21, f2, object);
            Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(8.0f), string2, this.x + 6.0f + f21 + f5 + 4.0f, f2, object);
        }
        Render2D.startScissor(class_3322, this.x, f9, this.width, f12);
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(-this.width * f6, 0.0f, 0.0f);
        float f22 = class_3532.method_15363((float)this.scrollAnimation.a(this.scroll, 260L), (float)0.0f, (float)f14);
        f5 = f9 - f22;
        f4 = f9 + f12;
        if (this.openAnimation.rk() > 0.0f) {
            for (int i = 0; i < this.filteredComponents.size(); ++i) {
                ModuleComponent moduleComponent2 = this.filteredComponents.get(i);
                moduleComponent2.setX(this.x);
                moduleComponent2.setY(f5);
                moduleComponent2.setWidth(this.width);
                moduleComponent2.setHeight(this.height);
                bl = this.intersectsVerticalRange(f5, moduleComponent2.getHeight(), f9, f4);
                if (bl) {
                    moduleComponent2.render(class_3322, n, n2, f);
                    if (i < this.filteredComponents.size() - 1 && f5 + moduleComponent2.getHeight() <= f4) {
                        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + 4.0f, f5 + moduleComponent2.getHeight() - 0.5f, this.width - 8.0f, 0.5f, 0.35f, color3);
                    }
                }
                f5 += moduleComponent2.getHeight();
            }
        }
        class_3322.method_51448().method_22909();
        if (bl4) {
            class_3322.method_51448().method_22903();
            class_3322.method_51448().method_46416(this.width * (1.0f - f6), 0.0f, 0.0f);
            f3 = class_3532.method_15363((float)this.settingsScrollAnimation.a(this.settingsScroll, 260L), (float)0.0f, (float)f15);
            f2 = f9 - f3;
            float f23 = f9 + f12;
            for (Component component : this.visibleSettingsComponents) {
                component.setX(this.x + 2.0f);
                component.setY(f2);
                component.setWidth(this.width - 4.0f);
                float f21 = component.getLayoutHeight();
                component.setHeight(f21);
                float f24 = f21 + component.getAddHeight().get().floatValue();
                if (this.intersectsVerticalRange(f2, f24, f9, f23)) {
                    component.render(class_3322, n, n2, f);
                }
                f2 += f24;
            }
            class_3322.method_51448().method_22909();
        }
        Render2D.stopScissor(class_3322);
        if (this.pageAnimation.rk() <= 0.01f && this.activeModule == null) {
            this.lastSettingsModule = null;
        }
    }

    public void renderOverlays(class_332 class_3322, int n, int n2, float f) {
        boolean bl;
        boolean bl2 = bl = this.cachedVisibleSettingsModule != null && (this.pageAnimation.rk() > 0.01f || this.activeModule != null);
        if (!bl) {
            return;
        }
        float f2 = this.pageAnimation.rk();
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(this.width * (1.0f - f2), 0.0f, 0.0f);
        for (Component component : this.visibleSettingsComponents) {
            if (!component.hasActiveOverlay()) continue;
            component.renderOverlay(class_3322, n, n2, f);
        }
        class_3322.method_51448().method_22909();
    }

    public boolean handleExtendedClick(double d, double d2, int n) {
        boolean bl;
        ModuleComponent moduleComponent = this.activeModule != null ? this.activeModule : this.lastSettingsModule;
        boolean bl2 = bl = moduleComponent != null && (this.pageAnimation.rk() > 0.01f || this.activeModule != null);
        if (!bl) {
            return false;
        }
        for (Component component : moduleComponent.getComponents()) {
            ColorComponent colorComponent;
            if (!component.getVisible().get().booleanValue()) continue;
            boolean bl3 = component.isExtendedHovered(d, d2);
            if (!bl3 && component instanceof ColorComponent && (colorComponent = (ColorComponent)component).isEditing()) {
                bl3 = true;
            }
            if (!bl3) continue;
            component.mouseClicked(d, d2, n);
            return true;
        }
        return false;
    }

    private float getTotalHeight(String string) {
        this.ensureFilteredComponents(string);
        return this.cachedFilteredTotalHeight;
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        this.render(class_3322, n, n2, f, "");
    }

    public void mouseClicked(double d, double d2, int n) {
        this.mouseClicked(d, d2, n, "");
    }

    public void mouseReleased(double d, double d2, int n) {
        this.mouseReleased(d, d2, n, "");
    }

    public void keyPressed(int n, int n2, int n3) {
        this.keyPressed(n, n2, n3, "");
    }

    public void keyReleased(int n, int n2, int n3) {
        this.keyReleased(n, n2, n3, "");
    }

    public void charTyped(char c, int n) {
        this.charTyped(c, n, "");
    }

    private float getTotalHeight() {
        return this.getTotalHeight("");
    }

    public void mouseClicked(double d, double d2, int n, String string) {
        float f;
        if (MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2) && n == 1) {
            return;
        }
        float f2 = this.moduleHeaderAnimation.rk();
        float f3 = this.y + this.height + 12.0f * f2;
        float f4 = Math.max(0.0f, 230.0f * this.openAnimation.rk() - (f3 - (this.y + this.height)));
        float f5 = f = this.isSettingsViewActive() ? Math.max(0.0f, f4 - 10.0f) : f4;
        if (this.activeModule != null) {
            String string2 = this.activeModule.getModule().getDisplayName();
            float f6 = Fonts.SEMIBOLD.getWidth("<", 7.5f);
            float f7 = Fonts.SEMIBOLD.getWidth(string2, 8.0f);
            float f8 = f6 + 4.0f + f7;
            float f9 = this.y + this.height;
            float f10 = (1.0f - this.moduleHeaderAnimation.rk()) * 6.0f;
            float f11 = this.x + 6.0f + f10;
            if (this.moduleHeaderAnimation.rk() > 0.01f && MathUtils.c(f11, f9 - 2.0f, f8, 11.0f, (float)d, (float)d2) && n == 0) {
                this.closeActiveModule();
                return;
            }
            if (this.open && MathUtils.c(this.x, f3, this.width, f, (float)d, (float)d2)) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue()) continue;
                    component.mouseClicked(d, d2, n);
                }
            }
            return;
        }
        if (this.open && MathUtils.c(this.x, f3, this.width, f, (float)d, (float)d2)) {
            this.ensureFilteredComponents(string);
            for (ModuleComponent moduleComponent : this.filteredComponents) {
                moduleComponent.mouseClicked(d, d2, n);
            }
        }
    }

    public void mouseReleased(double d, double d2, int n, String string) {
        if (this.open) {
            if (this.activeModule != null) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue()) continue;
                    component.mouseReleased(d, d2, n);
                }
                return;
            }
            this.ensureFilteredComponents(string);
            for (ModuleComponent moduleComponent : this.filteredComponents) {
                moduleComponent.mouseReleased(d, d2, n);
            }
        }
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        float f;
        float f2 = this.moduleHeaderAnimation.rk();
        float f3 = this.y + this.height + 12.0f * f2;
        float f4 = Math.max(0.0f, 230.0f * this.openAnimation.rk() - (f3 - (this.y + this.height)));
        float f5 = f = this.isSettingsViewActive() ? Math.max(0.0f, f4 - 10.0f) : f4;
        if (this.open && MathUtils.c(this.x, f3, this.width, f, (float)d, (float)d2)) {
            if (this.activeModule != null) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue() || !component.mouseScrolled(d, d2, d3, d4)) continue;
                    return;
                }
                this.settingsScroll -= (float)(d4 * 20.0);
                this.settingsScroll = class_3532.method_15363((float)this.settingsScroll, (float)0.0f, (float)Math.max(0.0f, this.getSettingsHeight(this.activeModule) - f));
            } else {
                this.scroll -= (float)(d4 * 20.0);
                float f6 = this.cachedFilteredTotalHeight >= 0.0f ? this.cachedFilteredTotalHeight : this.getTotalHeight();
                this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)Math.max(0.0f, f6 - f));
            }
        }
    }

    public void keyPressed(int n, int n2, int n3, String string) {
        if (this.open) {
            if (this.activeModule != null) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue()) continue;
                    component.keyPressed(n, n2, n3);
                }
                return;
            }
            this.ensureFilteredComponents(string);
            for (ModuleComponent moduleComponent : this.filteredComponents) {
                moduleComponent.keyPressed(n, n2, n3);
            }
        }
    }

    public void keyReleased(int n, int n2, int n3, String string) {
        if (this.open) {
            if (this.activeModule != null) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue()) continue;
                    component.keyReleased(n, n2, n3);
                }
                return;
            }
            this.ensureFilteredComponents(string);
            for (ModuleComponent moduleComponent : this.filteredComponents) {
                moduleComponent.keyReleased(n, n2, n3);
            }
        }
    }

    public void charTyped(char c, int n, String string) {
        if (this.open) {
            if (this.activeModule != null) {
                for (Component component : this.activeModule.getComponents()) {
                    if (!component.getVisible().get().booleanValue()) continue;
                    component.charTyped(c, n);
                }
                return;
            }
            this.ensureFilteredComponents(string);
            for (ModuleComponent moduleComponent : this.filteredComponents) {
                moduleComponent.charTyped(c, n);
            }
        }
    }

    public void openModuleSettings(ModuleComponent moduleComponent) {
        if (moduleComponent == null) {
            return;
        }
        this.activeModule = moduleComponent;
        this.lastSettingsModule = moduleComponent;
        this.settingsScroll = 0.0f;
        this.settingsScrollAnimation.a(this.settingsScroll, 260L);
        this.moduleHeaderAnimation.i(true);
        if (Celebression.getInstance().getSoundManager() != null) {
            Celebression.getInstance().getSoundManager().fH();
        }
    }

    public boolean isSettingsViewActive() {
        return this.activeModule != null || this.lastSettingsModule != null && this.pageAnimation.rk() > 0.01f;
    }

    public boolean isMouseOverContentArea(float f, float f2) {
        float f3 = this.moduleHeaderAnimation.rk();
        float f4 = this.y + this.height + 12.0f * f3;
        float f5 = Math.max(0.0f, 230.0f * this.openAnimation.rk() - (f4 - (this.y + this.height)));
        float f6 = this.isSettingsViewActive() ? Math.max(0.0f, f5 - 10.0f) : f5;
        return this.open && MathUtils.c(this.x, f4, this.width, f6, f, f2);
    }

    public boolean hasActiveBinding() {
        if (this.activeModule != null) {
            for (Component component : this.activeModule.getComponents()) {
                if (!component.isBinding()) continue;
                return true;
            }
            return this.activeModule.isBinding();
        }
        for (ModuleComponent moduleComponent : this.components) {
            if (!moduleComponent.isBinding()) continue;
            return true;
        }
        return false;
    }

    public void dismissOverlays() {
        this.closeActiveModule();
        for (ModuleComponent moduleComponent : this.components) {
            moduleComponent.onClose();
        }
    }

    private void closeActiveModule() {
        if (this.activeModule != null) {
            for (Component component : this.activeModule.getComponents()) {
                component.onClose();
            }
        }
        this.activeModule = null;
    }

    private float getSettingsHeight(ModuleComponent moduleComponent) {
        this.ensureVisibleSettingsComponents(moduleComponent);
        return this.cachedVisibleSettingsHeight;
    }

    private void ensureFilteredComponents(String string) {
        String string2 = this.normalizeSearchQuery(string);
        if (string2.equals(this.cachedSearchQuery) && this.cachedFilteredTotalHeight >= 0.0f) {
            return;
        }
        this.cachedSearchQuery = string2;
        this.cachedFilteredTotalHeight = 0.0f;
        this.filteredComponents.clear();
        for (ModuleComponent moduleComponent : this.components) {
            if (!moduleComponent.matchesSearch(string2)) continue;
            this.filteredComponents.add(moduleComponent);
            float f = moduleComponent.getHeight() > 0.0f ? moduleComponent.getHeight() : this.height;
            this.cachedFilteredTotalHeight += f;
        }
    }

    private static List<Module> panelModules(ModuleCategory moduleCategory) {
        List<Module> list = Celebression.getInstance().getModuleManager().getModules(moduleCategory);
        ArrayList<Module> arrayList = new ArrayList<Module>(list.size());
        for (Module module : list) {
            if (module instanceof ElementsModule) continue;
            arrayList.add(module);
        }
        return arrayList;
    }

    private void syncModuleComponentsIfNeeded() {
        int n = Panel.panelModules(this.category).size();
        if (n == this.cachedModuleCount && n == this.components.size()) {
            return;
        }
        this.cachedModuleCount = n;
        this.syncModuleComponents();
    }

    private void syncModuleComponents() {
        boolean bl = false;
        for (Module module : Panel.panelModules(this.category)) {
            boolean bl2 = false;
            for (ModuleComponent moduleComponent : this.components) {
                if (moduleComponent.getModule() != module) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            ModuleComponent moduleComponent = new ModuleComponent(module);
            moduleComponent.setParentPanel(this);
            this.components.add(moduleComponent);
            bl = true;
        }
        if (!bl) {
            return;
        }
        this.components.sort(Comparator.comparing(Component::getName));
        this.cachedSearchQuery = "";
        this.cachedFilteredTotalHeight = -1.0f;
    }

    private void ensureVisibleSettingsComponents(ModuleComponent moduleComponent) {
        if (moduleComponent == this.cachedVisibleSettingsModule && !this.visibleSettingsComponents.isEmpty() && !this.needsSettingsLayoutRefresh(moduleComponent)) {
            return;
        }
        this.cachedVisibleSettingsModule = moduleComponent;
        this.visibleSettingsComponents.clear();
        this.cachedVisibleSettingsHeight = 0.0f;
        if (moduleComponent == null) {
            return;
        }
        for (Component component : moduleComponent.getComponents()) {
            if (!component.getVisible().get().booleanValue()) continue;
            this.visibleSettingsComponents.add(component);
            float f = component.getLayoutHeight();
            this.cachedVisibleSettingsHeight += f + component.getAddHeight().get().floatValue();
        }
    }

    private boolean needsSettingsLayoutRefresh(ModuleComponent moduleComponent) {
        if (moduleComponent == null) {
            return this.cachedVisibleSettingsHeight > 0.0f;
        }
        float f = 0.0f;
        for (Component component : moduleComponent.getComponents()) {
            if (!component.getVisible().get().booleanValue()) continue;
            f += component.getLayoutHeight() + component.getAddHeight().get().floatValue();
        }
        return Math.abs(f - this.cachedVisibleSettingsHeight) > 0.01f;
    }

    private String normalizeSearchQuery(String string) {
        return string == null ? "" : string.trim().toLowerCase(Locale.ROOT);
    }

    private boolean intersectsVerticalRange(float f, float f2, float f3, float f4) {
        return f + f2 >= f3 && f <= f4;
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
    public List<ModuleComponent> getComponents() {
        return this.components;
    }
}

