package ru.method.celebression.screen.clickgui;

import ru.method.celebression.module.Module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Generated;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.ClickGuiLayout;
import ru.method.celebression.screen.clickgui.Panel;
import ru.method.celebression.screen.clickgui.SettingsWindow;
import ru.method.celebression.screen.clickgui.components.SearchBar;
import ru.method.celebression.utils.render.ClickGuiRenderContext;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ClickGui
extends class_437
implements MinecraftClientAccess {
    private final List<Panel> panels = new ArrayList<Panel>();
    private static final long OPEN_ANIMATION_DURATION = 450L;
    private static final long CLOSE_FADE_DURATION = 220L;
    private static final float MIN_VISIBLE_FADE = 0.02f;
    private final Animation openAnimation = new Animation(450L, 1.0, true, Easing.aiT);
    private final Animation closeFadeAnimation = new Animation(220L, 1.0, true, Easing.aiR);
    private final Animation hoverAnimation = new Animation(90L, 1.0, true, Easing.aiQ);
    private final TimedProgress descriptionText = new TimedProgress(220L, Easing.aiZ, "");
    private SearchBar searchBar;
    private String description;
    private boolean close = false;
    private float closeRenderScale = 1.0f;
    private float guiLeft;
    private float guiRight;
    private final float panelWidth = 120.0f;
    private final float panelGap = 4.0f;
    private final float panelsYShift = 125.0f;
    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    public ClickGui() {
        super(class_2561.method_30163((String)"celebression-clickgui"));
        for (ModuleCategory moduleCategory : Celebression.getInstance().getModuleManager().getCategories()) {
            if (moduleCategory == ModuleCategory.qo) continue;
            this.panels.add(new Panel(-999.0f, -999.0f, 120.0f, 20.0f, moduleCategory));
        }
        this.searchBar = new SearchBar(0.0f, 0.0f, 150.0f, 18.0f);
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }

        public void method_25394(class_332 class_3322, int n, int n2, float f) {
        if (aiG == null || ClickGui.aiG.field_1755 != this) {
            return;
        }
        boolean bl = false;
        if (this.close) {
            this.closeFadeAnimation.i(false);
            if (this.closeFadeAnimation.rk() <= 0.02f) {
                bl = true;
            }
        } else {
            this.openAnimation.i(true);
        }
        float f2 = this.getUiFade();
        ClickGuiRenderContext.begin(f2, this.close);
        try {
            Render2D.prepareHudFrame(class_3322);
            LiquidGlassReflectionCache.captureClickGuiBackdrop(true);
            this.description = null;
            float[] fArray = this.transformMouse(n, n2);
            int n3 = Math.round(fArray[0]);
            int n4 = Math.round(fArray[1]);
            float f3 = this.getRenderScale();
            class_3322.method_51448().method_22903();
            class_3322.method_51448().method_46416((float)aiG.method_22683().method_4486() / 2.0f, (float)aiG.method_22683().method_4502() / 2.0f, 1.0f);
            class_3322.method_51448().method_22905(f3, f3, 1.0f);
            class_3322.method_51448().method_46416((float)(-aiG.method_22683().method_4486()) / 2.0f, (float)(-aiG.method_22683().method_4502()) / 2.0f, 0.0f);
            String string = this.searchBar.getSearchText();
            for (Panel object2 : this.panels) {
                object2.render(class_3322, n3, n4, f, string);
            }
            this.searchBar.render(class_3322, n3, n4, f);
            for (Panel panel : this.panels) {
                panel.renderOverlays(class_3322, n3, n4, f);
            }
            List<SettingsWindow> list = Celebression.getInstance().getSettingsWindows();
            list.removeIf(SettingsWindow::isFinishedClosing);
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                SettingsWindow settingsWindow = (SettingsWindow)iterator.next();
                settingsWindow.render(class_3322, n3, n4, f);
            }
            this.descriptionText.cF(this.description);
            this.hoverAnimation.i(!this.descriptionIsEmpty());
            if (!this.descriptionIsEmpty() || this.descriptionText.rg()) {
                float f4 = 9.0f;
                float f5 = this.descriptionText.rg() ? this.descriptionText.progress() : 1.0f;
                boolean bl2 = this.descriptionText.rg();
                float f6 = Fonts.ROUNDBOLD.getWidth(this.descriptionText.rh(), f4);
                float f7 = Fonts.ROUNDBOLD.getWidth(this.descriptionText.ri(), f4);
                float f8 = bl2 ? MathUtils.lerp(f7, f6, f5) : f6;
                float f9 = Fonts.ROUNDBOLD.getHeight(f4);
                float f10 = f8;
                float f11 = (float)aiG.method_22683().method_4486() / 2.0f - f10 / 2.0f;
                float f12 = (float)aiG.method_22683().method_4502() / 2.0f - 165.0f;
                Color color = ColorUtils.getTextColor((int)(255.0f * this.hoverAnimation.rk()));
                float f13 = f9 * 0.6f;
                this.drawAnimatedText(class_3322, this.descriptionText, f11, f12, color, f13, f4, Fonts.ROUNDBOLD, f5, bl2);
            }
            class_3322.method_51448().method_22909();
        }
        finally {
            ClickGuiRenderContext.end();
        }
        if (bl) {
            this.finishClosing();
        }
    }

    public boolean method_25402(double d, double d2, int n) {
        double d3;
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        float[] fArray = this.transformMouse(d, d2);
        double d4 = fArray[0];
        if (this.searchBar.mouseClicked(d4, d3 = (double)fArray[1], n)) {
            return true;
        }
        List<SettingsWindow> list = Celebression.getInstance().getSettingsWindows();
        for (int i = list.size() - 1; i >= 0; --i) {
            SettingsWindow minecraftClientAccess = list.get(i);
            if (!minecraftClientAccess.mouseClicked(d4, d3, n)) continue;
            if (list.contains(minecraftClientAccess)) {
                list.remove(minecraftClientAccess);
                list.add(minecraftClientAccess);
            }
            return true;
        }
        for (Panel panel : this.panels) {
            if (!panel.handleExtendedClick(d4, d3, n)) continue;
            return true;
        }
        for (Panel panel : this.panels) {
            panel.mouseClicked(d4, d3, n, this.searchBar.getSearchText());
        }
        return super.method_25402(d, d2, n);
    }

    public boolean method_25406(double d, double d2, int n) {
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        float[] fArray = this.transformMouse(d, d2);
        double d3 = fArray[0];
        double d4 = fArray[1];
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            minecraftClientAccess.mouseReleased(d3, d4, n);
        }
        for (Panel panel : this.panels) {
            panel.mouseReleased(d3, d4, n, this.searchBar.getSearchText());
        }
        return super.method_25406(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        float[] fArray = this.transformMouse(d, d2);
        double d5 = fArray[0];
        double d6 = fArray[1];
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            minecraftClientAccess.mouseScrolled(d5, d6, d3, d4);
        }
        for (Panel panel : this.panels) {
            panel.mouseScrolled(d5, d6, d3, d4);
        }
        return super.method_25401(d, d2, d3, d4);
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        boolean bl = this.hasActiveBinding();
        if (n == 70 && class_437.method_25441()) {
            this.searchBar.setFocused(true);
            return true;
        }
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            minecraftClientAccess.keyPressed(n, n2, n3);
        }
        if (!bl && this.searchBar.keyPressed(n, n2, n3)) {
            return true;
        }
        if (!this.searchBar.isFocused() || bl) {
            for (Panel panel : this.panels) {
                panel.keyPressed(n, n2, n3, this.searchBar.getSearchText());
            }
        }
        if (bl) {
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_16803(int n, int n2, int n3) {
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            minecraftClientAccess.keyPressed(n, n2, n3);
        }
        if (!this.searchBar.isFocused()) {
            for (Panel panel : this.panels) {
                panel.keyReleased(n, n2, n3, this.searchBar.getSearchText());
            }
        }
        return super.method_16803(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.close || ClickGui.aiG.field_1755 != this) {
            return false;
        }
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            minecraftClientAccess.charTyped(c, n);
        }
        if (this.searchBar.charTyped(c, n)) {
            return true;
        }
        if (this.searchBar.isFocused()) {
            return false;
        }
        for (Panel panel : this.panels) {
            panel.charTyped(c, n, this.searchBar.getSearchText());
        }
        return super.method_25400(c, n);
    }

    public void method_25426() {
        this.prepareForOpen();
        if (Celebression.getInstance().getSoundManager() != null) {
            Celebression.getInstance().getSoundManager().fH();
        }
    }

    public void method_25393() {
        this.updateLayout();
    }

    public void method_25419() {
        if (this.close) {
            return;
        }
        this.closeRenderScale = Math.max(0.01f, this.getLayoutScale(UI.peekInterfaceScale()));
        this.close = true;
        this.closeFadeAnimation.ro();
        this.closeFadeAnimation.i(false);
        for (Panel object2 : this.panels) {
            object2.dismissOverlays();
        }
        List<SettingsWindow> list = Celebression.getInstance().getSettingsWindows();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            SettingsWindow settingsWindow = (SettingsWindow)iterator.next();
            settingsWindow.resetInputState();
        }
        list.clear();
        this.searchBar.reset();
        LiquidGlassReflectionCache.invalidate(LiquidGlassReflectionCache.ReflectionSource.CLICK_GUI);
    }

    public void method_25432() {
        this.cancelCloseAnimation();
        this.resetOpenAnimation();
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI != null) {
            uI.syncClosedState();
        }
        super.method_25432();
    }

    public boolean isOpen() {
        return aiG != null && ClickGui.aiG.field_1755 == this && !this.close;
    }

    public boolean isDisplayed() {
        return aiG != null && ClickGui.aiG.field_1755 == this;
    }

    public boolean isClosing() {
        return this.close;
    }

    public void open() {
        if (!UI.isInPlayWorld() || aiG == null) {
            return;
        }
        this.prepareForOpen();
        if (ClickGui.aiG.field_1755 == this) {
            return;
        }
        aiG.method_1507((class_437)this);
    }

    public void beginClose() {
        if (aiG == null || ClickGui.aiG.field_1755 != this) {
            this.cancelCloseAnimation();
            return;
        }
        this.method_25419();
    }

    private void prepareForOpen() {
        this.cancelCloseAnimation();
        this.resetOpenAnimation();
        this.searchBar.reset();
        this.lastLayoutWidth = -1;
        this.lastLayoutHeight = -1;
        this.updateLayout();
    }

    private void resetOpenAnimation() {
        this.openAnimation.w(450L);
        this.openAnimation.ae(true);
        this.closeFadeAnimation.w(220L);
        this.closeFadeAnimation.ro();
    }

    private void cancelCloseAnimation() {
        this.close = false;
        this.closeRenderScale = 1.0f;
        this.closeFadeAnimation.ro();
    }

    private void finishClosing() {
        if (!this.close) {
            return;
        }
        this.close = false;
        this.closeRenderScale = 1.0f;
        this.closeFadeAnimation.ro();
        this.resetOpenAnimation();
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI != null) {
            uI.syncClosedState();
        }
        if (aiG != null && ClickGui.aiG.field_1755 == this) {
            super.method_25419();
        }
    }

    private void updateLayout() {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        int n = aiG.method_22683().method_4486();
        int n2 = aiG.method_22683().method_4502();
        if (n == this.lastLayoutWidth && n2 == this.lastLayoutHeight) {
            return;
        }
        this.lastLayoutWidth = n;
        this.lastLayoutHeight = n2;
        int n3 = this.panels.size();
        float f = (float)n3 * 120.0f + (float)(n3 - 1) * 4.0f;
        float f2 = (float)n / 2.0f - f / 2.0f;
        float f3 = (float)n2 / 2.0f - 125.0f;
        float f4 = f2;
        for (Panel panel : this.panels) {
            panel.setX(f4);
            panel.setY(f3);
            panel.setWidth(120.0f);
            panel.setHeight(20.0f);
            f4 += 124.0f;
        }
        this.guiLeft = f2;
        this.guiRight = f2 + f;
        float f5 = 150.0f;
        float f6 = (float)n / 2.0f - f5 / 2.0f;
        float f7 = (float)n2 / 2.0f + 140.0f;
        this.searchBar.setX(f6);
        this.searchBar.setY(f7);
        this.searchBar.setWidth(f5);
        this.searchBar.setHeight(18.0f);
    }

    public boolean method_25421() {
        return false;
    }

    private boolean descriptionIsEmpty() {
        return this.description == null || this.description.isEmpty();
    }

    private void drawAnimatedText(class_332 class_3322, TimedProgress timedProgress, float f, float f2, Color color, float f3, float f4, Font font, float f5, boolean bl) {
        float f6;
        if (timedProgress == null) {
            return;
        }
        String string = timedProgress.rh();
        if (!bl) {
            Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string, f, f2, color);
            return;
        }
        float f7 = Math.max(0.0f, Math.min(1.0f, f5));
        int n = color.getAlpha();
        int n2 = Math.round((float)n * (1.0f - f7));
        int n3 = Math.round((float)n * f7);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n2);
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n3);
        float f8 = f2 - f3 * f7;
        float f9 = f2 + f3 * (1.0f - f7);
        String string2 = timedProgress.ri();
        String string3 = timedProgress.rh();
        int n4 = EasingMath.w(string2, string3);
        int n5 = EasingMath.d(string2, string3, n4);
        String string4 = EasingMath.a(string3, 0, n4);
        String string5 = EasingMath.a(string2, n4, string2.length() - n5);
        String string6 = EasingMath.a(string3, n4, string3.length() - n5);
        String string7 = EasingMath.a(string3, string3.length() - n5, string3.length());
        float f10 = font.getWidth(string4, f4);
        float f11 = font.getWidth(string6, f4);
        float f12 = font.getWidth(string5, f4);
        float f13 = f6 = f + f10;
        float f14 = f6 + (f11 - f12) / 2.0f;
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string4, f, f2, color);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string7, f6 + f11, f2, color);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string5, f14, f8, color2);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string6, f13, f9, color3);
    }

    public boolean hasActiveBinding() {
        for (SettingsWindow minecraftClientAccess : Celebression.getInstance().getSettingsWindows()) {
            if (!minecraftClientAccess.hasActiveBinding()) continue;
            return true;
        }
        for (Panel panel : this.panels) {
            if (!panel.hasActiveBinding()) continue;
            return true;
        }
        return false;
    }

    private float getUiFade() {
        if (this.close) {
            return this.closeFadeAnimation.rk();
        }
        return Math.max(0.02f, this.openAnimation.rk());
    }

    private float getRenderScale() {
        if (this.close) {
            float f = Math.max(0.0f, Math.min(1.0f, this.closeFadeAnimation.rk()));
            return Math.max(0.01f, this.closeRenderScale * (0.92f + 0.08f * f));
        }
        float f = this.getLayoutScale(UI.getInterfaceScale());
        float f2 = Math.max(0.0f, Math.min(1.0f, this.openAnimation.rk()));
        float f3 = 0.9f + 0.1f * f2;
        return Math.max(0.01f, f * f3);
    }

    private float getLayoutScale(float f) {
        return Math.max(0.01f, f * this.getFitScale(f));
    }

    private float getFitScale(float f) {
        if (aiG == null || aiG.method_22683() == null) {
            return 1.0f;
        }
        return ClickGuiLayout.computeFitScale(aiG.method_22683().method_4486(), aiG.method_22683().method_4502(), this.panels.size());
    }

    private float[] transformMouse(double d, double d2) {
        float f = (float)aiG.method_22683().method_4486() / 2.0f;
        float f2 = (float)aiG.method_22683().method_4502() / 2.0f;
        float f3 = this.getRenderScale();
        return new float[]{(float)((double)f + (d - (double)f) / (double)f3), (float)((double)f2 + (d2 - (double)f2) / (double)f3)};
    }

    @Generated
    public SearchBar getSearchBar() {
        return this.searchBar;
    }

    @Generated
    public void setDescription(String string) {
        this.description = string;
    }

    @Generated
    public float getGuiLeft() {
        return this.guiLeft;
    }

    @Generated
    public float getGuiRight() {
        return this.guiRight;
    }
}

