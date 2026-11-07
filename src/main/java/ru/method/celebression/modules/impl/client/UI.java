package ru.method.celebression.modules.impl.client;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.ui.GuiStyle;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.hud.ScoreboardHud;
import ru.method.celebression.ui.ScreenTransitionController;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.ui.UiScaleMode;
import ru.method.celebression.screen.clickgui.ClickGui;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.RenderFrameTracker;

public class UI
extends Module {
    private static final float MIN_SCALE = 0.75f;
    private static final float MAX_SCALE = 1.5f;
    private static final float SCALE_SMOOTH_SPEED = 10.0f;
    private static float appliedInterfaceScale = 1.0f;
    private static float currentInterfaceScale = 1.0f;
    static float guiTransparency = 1.0f;
    private static long lastScaleUpdateNs = System.nanoTime();
    private static long lastScaleAnimatedFrameId = -1L;
    private final EnumSetting<GuiStyle> guiStyle = new EnumSetting<GuiStyle>("settings.ui.gui", GuiStyle.aio){

        @Override
        public void a(GuiStyle guiStyle) {
            GuiStyle guiStyle2 = (GuiStyle)this.jT();
            super.a(guiStyle);
            if (guiStyle2 != guiStyle) {
                UI.this.closeOpenGuiOnStyleSwitch();
            }
        }
    };
    private final EnumSetting<HudThemeRenderer.ThemeMetrics> hudStyle = new EnumSetting<HudThemeRenderer.ThemeMetrics>("settings.ui.hud_style", HudThemeRenderer.ThemeMetrics.gz, this::usesLinkGui);
    private final EnumSetting<HudThemeRenderer.ThemePalette> hudShape = new EnumSetting<HudThemeRenderer.ThemePalette>("settings.ui.hud_shape", HudThemeRenderer.ThemePalette.gu, this::usesLinkGui);
    private final ColorSetting primaryColor = new ColorSetting("settings.ui.primary_color", ColorUtils.getPrimaryColor()){

        @Override
        public void d(Color color) {
            super.d(color);
            ColorUtils.setPrimaryColor((Color)this.jT());
        }
    };
    private final ColorSetting secondaryColor = new ColorSetting("settings.ui.secondary_color", ColorUtils.getSecondaryColor()){

        @Override
        public void d(Color color) {
            super.d(color);
            ColorUtils.setSecondaryColor((Color)this.jT());
        }
    };
    private final ColorSetting backgroundColor = new ColorSetting("settings.ui.background_color", ColorUtils.getBackgroundColor()){

        @Override
        public void d(Color color) {
            super.d(color);
            ColorUtils.setBackgroundColor((Color)this.jT());
        }
    };
    private final ColorSetting textColor = new ColorSetting("settings.ui.text_color", ColorUtils.getTextColor()){

        @Override
        public void d(Color color) {
            super.d(color);
            ColorUtils.setTextColor((Color)this.jT());
        }
    };
    private final BooleanSetting fade = new BooleanSetting("settings.ui.fade", true){

        @Override
        public void a(Boolean bl) {
            super.a(bl);
            ColorUtils.setThemeFadeEnabled((Boolean)this.jT());
        }
    };
    private final BooleanSetting blur = new BooleanSetting("settings.ui.blur", true){

        @Override
        public void a(Boolean bl) {
            super.a(bl);
            ColorUtils.setBlurEnabled((Boolean)this.jT());
        }
    };
    static UiScaleMode boostFpsMode = UiScaleMode.qr;
    private final EnumSetting<UiScaleMode> boostFpsUi = new EnumSetting<UiScaleMode>("settings.ui.boost_fps", UiScaleMode.qr, () -> false){

        @Override
        public void a(UiScaleMode uiScaleMode) {
            super.a(uiScaleMode);
            boostFpsMode = (UiScaleMode)this.jT();
        }
    };
    private final BooleanSetting customScoreboard = new BooleanSetting("settings.ui.scoreboard.custom", false){

        @Override
        public void a(Boolean bl) {
            super.a(bl);
            UI.syncCustomScoreboardHud((Boolean)this.jT());
        }
    };
    private final SliderSetting scoreboardRadius = new SliderSetting("settings.ui.scoreboard.radius", 9.0f, 0.0f, 18.0f, 0.5f, this.customScoreboard::jT);
    private final ColorSetting scoreboardBackground = new ColorSetting("settings.ui.scoreboard.background", new Color(18, 20, 26, 185), this.customScoreboard::jT);
    private final BooleanSetting customTab = new BooleanSetting("settings.ui.tab.custom", false);
    private final ColorSetting customTabColor = new ColorSetting("settings.ui.tab.color", new Color(15, 17, 22, 205), this.customTab::jT);
    private final SliderSetting transparency = new SliderSetting("settings.ui.transparency", 100.0f, 30.0f, 100.0f, 1.0f, () -> false){

        @Override
        public void a(Float f) {
            super.a(f);
            guiTransparency = class_3532.method_15363((float)(((Float)this.jT()).floatValue() / 100.0f), (float)0.3f, (float)1.0f);
        }
    };
    private final SliderSetting scale = new SliderSetting("settings.ui.scale", 1.0f, 0.75f, 1.5f, 0.01f){

        @Override
        public void a(Float f) {
            super.a(Float.valueOf(class_3532.method_15363((float)f.floatValue(), (float)0.75f, (float)1.5f)));
            Celebression celebression = Celebression.getInstance();
            if (celebression != null && celebression.isConfigLoading()) {
                UI.snapInterfaceScale(((Float)this.jT()).floatValue());
            }
        }

        @Override
        public void reset() {
            this.a((Float)this.jU());
        }
    };

    public UI() {
        super("UI", ModuleCategory.qn);
        this.setBind(new KeyBind(344, false));
        this.getSettings().add(this.guiStyle);
        this.getSettings().add(this.hudStyle);
        this.getSettings().add(this.hudShape);
        this.getSettings().add(this.primaryColor);
        this.getSettings().add(this.secondaryColor);
        this.getSettings().add(this.backgroundColor);
        this.getSettings().add(this.textColor);
        this.getSettings().add(this.fade);
        this.getSettings().add(this.blur);
        this.getSettings().add(this.boostFpsUi);
        this.getSettings().add(this.customScoreboard);
        this.getSettings().add(this.scoreboardRadius);
        this.getSettings().add(this.scoreboardBackground);
        this.getSettings().add(this.customTab);
        this.getSettings().add(this.customTabColor);
        this.getSettings().add(this.transparency);
        this.getSettings().add(this.scale);
        ColorUtils.setPrimaryColor((Color)this.primaryColor.jT());
        ColorUtils.setSecondaryColor((Color)this.secondaryColor.jT());
        ColorUtils.setBackgroundColor((Color)this.backgroundColor.jT());
        ColorUtils.setTextColor((Color)this.textColor.jT());
        ColorUtils.setThemeFadeEnabled((Boolean)this.fade.jT());
        ColorUtils.setBlurEnabled((Boolean)this.blur.jT());
        boostFpsMode = (UiScaleMode)this.boostFpsUi.jT();
        guiTransparency = class_3532.method_15363((float)(((Float)this.transparency.jT()).floatValue() / 100.0f), (float)0.3f, (float)1.0f);
        UI.snapInterfaceScale(((Float)this.scale.jT()).floatValue());
    }

    public void ensureDefaultBind() {
        this.setBind(new KeyBind(344, false));
    }

    public boolean usesLinkGui() {
        return this.guiStyle.jT() != GuiStyle.ain;
    }

    void closeOpenGuiOnStyleSwitch() {
        ClickGui clickGui;
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isConfigLoading()) {
            return;
        }
        ScreenTransitionController screenTransitionController = ScreenTransitionController.mK();
        if (screenTransitionController != null && screenTransitionController.isDisplayed()) {
            screenTransitionController.beginClose();
        }
        if ((clickGui = celebression.getClickGui()) != null && clickGui.isDisplayed()) {
            clickGui.beginClose();
        }
        this.toggled = false;
    }

    public static boolean isLinkGuiStyle() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI != null && uI.usesLinkGui();
    }

    public static HudThemeRenderer.ThemeMetrics getHudVariant() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return HudThemeRenderer.ThemeMetrics.gD;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        if (uI == null || !uI.usesLinkGui()) {
            return HudThemeRenderer.ThemeMetrics.gD;
        }
        return (HudThemeRenderer.ThemeMetrics)uI.hudStyle.jT();
    }

    public static HudThemeRenderer.ThemePalette getHudShape() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return HudThemeRenderer.ThemePalette.gu;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        if (uI == null || !uI.usesLinkGui()) {
            return HudThemeRenderer.ThemePalette.gu;
        }
        return (HudThemeRenderer.ThemePalette)uI.hudShape.jT();
    }

    public void migrateLegacyDefaultPalette() {
        if (this.primaryColor.jT().equals(new Color(95, 218, 139))
                && this.secondaryColor.jT().equals(new Color(170, 232, 195))) {
            this.primaryColor.d(new Color(142, 216, 255));
            this.secondaryColor.d(new Color(196, 235, 255));
        }
    }

    public void syncFromLinkTheme(ClientTheme clientTheme) {
        if (clientTheme == null) {
            return;
        }
        this.primaryColor.d(clientTheme.pe());
        this.secondaryColor.d(clientTheme.ph());
        this.backgroundColor.d(clientTheme.pi());
        this.textColor.d(clientTheme.pg());
    }

    private void openActiveGui(boolean bl) {
        ru.method.celebression.integration.ui.bridge.ClientScreens.setOpen(bl);
        this.toggled = ru.method.celebression.integration.ui.bridge.ClientScreens.isOpen();
    }

    @Override
    public void toggle() {
        if (!UI.isInPlayWorld()) return;
        ru.method.celebression.integration.ui.bridge.ClientScreens.toggle();
        this.toggled = ru.method.celebression.integration.ui.bridge.ClientScreens.isOpen();
    }

    @Override
    public void setToggled(boolean bl) {
        this.openActiveGui(bl);
    }

    public void syncClosedState() {
        this.toggled = false;
    }

    public static boolean isInPlayWorld() {
        return UI.aiG.field_1724 != null && UI.aiG.field_1687 != null;
    }

    public static void previewInterfaceScale(float f) {
        UI.setAppliedScale(f, false);
    }

    public static float getInterfaceScale() {
        UI.updateScaleAnimation();
        return currentInterfaceScale;
    }

    public static float peekInterfaceScale() {
        return currentInterfaceScale;
    }

    public static float getGuiTransparency() {
        return guiTransparency;
    }

    public static UiScaleMode getBoostFpsMode() {
        return UiScaleMode.qr;
    }

    public static boolean isCustomScoreboardEnabled() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI != null && (Boolean)uI.customScoreboard.jT() != false;
    }

    public static boolean isCustomTabEnabled() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI != null && (Boolean)uI.customTab.jT() != false;
    }

    public static Color getCustomTabColor() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return new Color(15, 17, 22, 205);
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI == null ? new Color(15, 17, 22, 205) : (Color)uI.customTabColor.jT();
    }

    public static float getScoreboardRadius() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return 9.0f;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI == null ? 9.0f : ((Float)uI.scoreboardRadius.jT()).floatValue();
    }

    public static void setScoreboardRadius(float f) {
        UI uI = UI.module();
        if (uI != null) {
            uI.scoreboardRadius.a(Float.valueOf(class_3532.method_15363((float)f, (float)0.0f, (float)18.0f)));
        }
    }

    public static Color getScoreboardBackgroundColor() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return new Color(18, 20, 26, 185);
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        return uI == null ? new Color(18, 20, 26, 185) : (Color)uI.scoreboardBackground.jT();
    }

    public static void setScoreboardBackgroundAlpha(int n) {
        UI uI = UI.module();
        if (uI == null) {
            return;
        }
        Color color = (Color)uI.scoreboardBackground.jT();
        uI.scoreboardBackground.d(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, n))));
    }

    private static UI module() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return null;
        }
        return celebression.getModuleManager().getModule(UI.class);
    }

    public void applyScaleSetting() {
        UI.setAppliedScale(((Float)this.scale.jT()).floatValue(), false);
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && !celebression.isConfigLoading()) {
            celebression.getConfigManager().dU();
        }
    }

    public void resetScaleSetting() {
        this.scale.reset();
        UI.setAppliedScale(((Float)this.scale.jT()).floatValue(), false);
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && !celebression.isConfigLoading()) {
            celebression.getConfigManager().dU();
        }
    }

    static void snapInterfaceScale(float f) {
        UI.setAppliedScale(f, true);
    }

    private static void setAppliedScale(float f, boolean bl) {
        float f2;
        appliedInterfaceScale = f2 = class_3532.method_15363((float)f, (float)0.75f, (float)1.5f);
        if (bl) {
            currentInterfaceScale = f2;
        }
        lastScaleUpdateNs = System.nanoTime();
    }

    private static void updateScaleAnimation() {
        long l = RenderFrameTracker.getFrameId();
        if (l == lastScaleAnimatedFrameId) {
            return;
        }
        lastScaleAnimatedFrameId = l;
        long l2 = System.nanoTime();
        double d = Math.max(0.0, (double)(l2 - lastScaleUpdateNs) / 1.0E9);
        lastScaleUpdateNs = l2;
        if (Math.abs(currentInterfaceScale - appliedInterfaceScale) <= 5.0E-4f) {
            currentInterfaceScale = appliedInterfaceScale;
            return;
        }
        float f = (float)(1.0 - Math.exp(-10.0 * d));
        currentInterfaceScale = class_3532.method_16439((float)class_3532.method_15363((float)f, (float)0.0f, (float)1.0f), (float)currentInterfaceScale, (float)appliedInterfaceScale);
    }

    static void syncCustomScoreboardHud(boolean bl) {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getHudManager() == null) {
            return;
        }
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        HudManager hudManager = celebression.getHudManager();
        ScoreboardHud scoreboardHud = hudManager.a(ScoreboardHud.class);
        if (scoreboardHud == null) {
            return;
        }
        if (scoreboardHud.isToggled() != bl) {
            hudManager.a(hudManager.b(scoreboardHud), bl, Math.max(0.0f, (float)aiG.method_22683().method_4486() - 150.0f), 40.0f);
        }
    }
}
