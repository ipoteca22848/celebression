package ru.method.celebression.integration.ui.screens.dropdown;

import ru.method.celebression.integration.ui.manager.IMinecraft;
import ru.method.celebression.integration.ui.manager.Manager;
import ru.method.celebression.integration.ui.manager.themeManager.GuiThemeColor;
import ru.method.celebression.integration.ui.manager.themeManager.StyleManager;
import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.modules.TypeList;
import ru.method.celebression.integration.ui.modules.api.Module;
import net.minecraft.util.math.RotationAxis;
import ru.method.celebression.integration.ui.modules.setting.*;
import ru.method.celebression.integration.ui.screens.dropdown.impl.BindBooleanSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.BindSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.BooleanSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.ModeSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.MultiSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.SliderSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.ColorSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.impl.TextSettingRenderer;
import ru.method.celebression.integration.ui.screens.dropdown.search.SearchState;
import ru.method.celebression.integration.ui.screens.mainmenu.LanguageSelectUI;
import ru.method.celebression.integration.ui.screens.mainmenu.MenuConfigBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.MenuLanguageState;
import ru.method.celebression.integration.ui.util.animations.motion.Animation;
import ru.method.celebression.integration.ui.util.animations.motion.Easing;
import ru.method.celebression.integration.ui.util.math.MathUtil;
import ru.method.celebression.integration.ui.util.player.AudioUtil;
import ru.method.celebression.integration.ui.util.render.RenderAddon;
import ru.method.celebression.integration.ui.util.render.RenderUtil;
import ru.method.celebression.integration.ui.util.render.KawaseBlur;
import ru.method.celebression.integration.ui.util.render.Scissor;
import ru.method.celebression.integration.ui.util.color.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickGUI extends Screen implements IMinecraft {

    @Nullable
    private final Screen backgroundScreen;

    @Nullable
    public Screen getBackgroundScreen() {
        return backgroundScreen;
    }

    private static Tab persistedTab = Tab.COMBAT;

    public static String getPersistedTabName() {
        return persistedTab.name();
    }

    public static void setPersistedTabFromName(String name) {
        if (name == null || name.isEmpty()) return;
        if ("VISUAL".equals(name)) name = "RENDER";
        if ("OTHER".equals(name)) name = "MISC";
        if ("ACCOUNT".equals(name)) name = "COMBAT";
        try {
            persistedTab = Tab.valueOf(name);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private static final EnumMap<Tab, Float> persistedScrollByTab = new EnumMap<>(Tab.class);

    static {
        for (Tab t : Tab.values()) {
            persistedScrollByTab.put(t, 0f);
        }
    }

    public static JsonObject getPersistedScrollJson() {
        JsonObject o = new JsonObject();
        for (Tab t : Tab.values()) {
            o.addProperty(t.name(), persistedScrollByTab.getOrDefault(t, 0f));
        }
        return o;
    }

    public static void loadPersistedScrollFromJson(JsonObject o) {
        if (o == null) return;
        for (String key : o.keySet()) {
            try {
                Tab tab = Tab.valueOf(key);
                if (!o.get(key).isJsonPrimitive()) continue;
                float v = o.get(key).getAsFloat();
                persistedScrollByTab.put(tab, Math.max(0f, Math.round(v)));
            } catch (IllegalArgumentException | ClassCastException | UnsupportedOperationException ignored) {
            }
        }
    }

    public static String getPersistedGuiLanguageName() {
        return MenuLanguageState.code();
    }

    public static void setPersistedGuiLanguageFromName(String name) {
        if (name == null || name.isEmpty()) return;
        MenuLanguageState.load(name);
        MenuConfigBridge.saveAsync();
    }

    public static String getPersistedGuiStyleName() {
        return "custom";
    }

    public static void setPersistedGuiStyleFromName(String name) {
    }

    private static final int LANG_BAR_FLAG_W = 18;
    private static final float TEXT_F_SECONDARY = 130f / 255f;
    private static final float TEXT_F_MUTED = 100f / 255f;
    private static final float TEXT_F_ICON = 146f / 255f;
    private static final float TEXT_F_GROUP = 115f / 255f;
    private static final float TEXT_F_SOFT = 88f / 255f;
    private static final float TEXT_F_TAB = 150f / 255f;
    private static final float TEXT_F_TAB_HOVER = 225f / 255f;
    private static final float TEXT_F_INACTIVE = 138f / 255f;
    private static final float TEXT_F_HOVER = 210f / 255f;
    private static final float TEXT_F_LIGHT = 178f / 255f;
    private static final float TEXT_F_LABEL = 150f / 255f;

    private final Animation langSwapAnim = new Animation(260, 1f, Easing.SMOOTH_STEP);
    private MenuLanguageState.Language langSwapPrevious;
    private boolean langSwapWentForward = true;
    private float langPillHoverAnim;
    private float langFlagHoverAnim;
    private float searchHoverAnim;
    private float langBarSwapEased = 1f;

    private int langBarPillX;
    private int langBarPillW;
    private int langBarFlagX;
    private int langBarSearchY;
    private int langBarSearchH;

    private final Animation openAnimation = new Animation(250, Easing.SMOOTH_STEP);
    private final Animation tabSwitchAnimation = new Animation(350, Easing.SMOOTH_STEP);
    private float animation;
    private boolean isClose;

    private final Map<Tab, Float> tabSelectionAnims = new HashMap<>();
    private final Map<Tab, Float> tabHoverAnims = new HashMap<>();

    private final SearchState searchState;

    private Tab currentTab;
    private Tab previousTab;
    private final Map<Tab, Float> scrollOffsets = new HashMap<>();
    private final Map<Tab, Float> scrollTargets = new HashMap<>();

    private final Map<Object, Animation> toggleAnims = new HashMap<>();

    private final Map<Module, Animation> expandAnims = new HashMap<>();
    private final int WINDOW_WIDTH = 485;
    private final int WINDOW_HEIGHT = 300;
    private final int SIDEBAR_WIDTH = 110;
    private final int TOPBAR_HEIGHT = 45;

    private static final BooleanSettingRenderer SETTING_BOOLEAN = new BooleanSettingRenderer();
    private static final BindBooleanSettingRenderer SETTING_BIND_BOOLEAN = new BindBooleanSettingRenderer();
    private static final SliderSettingRenderer SETTING_SLIDER = new SliderSettingRenderer();
    private static final ModeSettingRenderer SETTING_MODE = new ModeSettingRenderer();
    private static final MultiSettingRenderer SETTING_MULTI = new MultiSettingRenderer();
    private static final BindSettingRenderer SETTING_BIND = new BindSettingRenderer();
    private static final TextSettingRenderer SETTING_TEXT = new TextSettingRenderer();
    private static final ColorSettingRenderer SETTING_COLOR = new ColorSettingRenderer();

    private float currentTransitionAlpha = 1.0f;

    private final SettingRenderContext settingEnv = new SettingRenderContext() {
        @Override
        public int ao(int color) {
            return RenderUtil.applyOpacity(ClickGUI.this.ao(color), currentTransitionAlpha);
        }

        @Override
        public int accentRgb() {
            return getAccentRgb();
        }

        @Override
        public int textPrimary() {
            return ao(guiTextRgb());
        }

        @Override
        public int textSecondary() {
            return ao(ColorUtil.scaleRgb(guiTextRgb(), TEXT_F_SECONDARY));
        }

        @Override
        public int textMuted() {
            return ao(ColorUtil.scaleRgb(guiTextRgb(), TEXT_F_MUTED));
        }

        @Override
        public int iconMuted() {
            return ao(ColorUtil.scaleRgb(guiTextRgb(), TEXT_F_ICON));
        }

        @Override
        public int fieldFill() {
            return ao(guiCa("fonColor", new Color(13, 13, 13, 155).getRGB()));
        }

        @Override
        public int panelBorder() {
            return ao(guiCa("scrollColor", new Color(55, 55, 55, 125).getRGB()));
        }

        @Override
        public int sliderTrack() {
            return new Color(40, 40, 40).getRGB();
        }

        @Override
        public void drawPanel(DrawContext ctx, float x, float y, float w, float h, float rounding, float alpha) {
            RenderUtil.drawRoundedRect(ctx.getMatrices(), x, y, w, h, rounding, RenderUtil.applyOpacity(fieldFill(), alpha));
        }

        @Override
        public void drawField(DrawContext ctx, float x, float y, float w, float h, float rounding, float alpha) {
            RenderUtil.drawRoundedRect(ctx.getMatrices(), x, y, w, h, rounding, RenderUtil.applyOpacity(fieldFill(), alpha));
            RenderUtil.drawRoundedBorder(ctx.getMatrices(), x, y, w, h, rounding, -0.5f, RenderUtil.applyOpacity(panelBorder(), alpha));
        }

        @Override
        public void drawPanelBorder(DrawContext ctx, float x, float y, float w, float h, float rounding, int borderRgb, float alpha) {
            RenderUtil.drawRoundedBorder(ctx.getMatrices(), x, y, w, h, rounding, -0.5f, RenderUtil.applyOpacity(ao(borderRgb), alpha));
        }

        @Override
        public void renderToggle(DrawContext ctx, int x, int y, boolean state, Object id) {
            ClickGUI.this.renderToggle(ctx, x, y, state, currentTransitionAlpha, id);
        }

        @Override
        public void beginSliderDrag(SliderSetting setting, int x, int w) {
            ClickGUI.this.draggedSlider = setting;
            ClickGUI.this.draggedSliderX = x;
            ClickGUI.this.draggedSliderW = w;
            setting.dragging = true;
        }
    };

    private static final float MODULE_DESC_MAX_LINE_WIDTH = 160f;
    private static final String MODULE_BIND_HINT = "Нажмите клавишу или кнопку";
    private static final int MODULE_HEADER_BASE = 27;
    private static final int MODULE_DESC_LINE_STEP = 10;
    private static final float MODULE_ROW_GAP_RENDER = 4.5f;
    private static final float MODULE_LIST_BOTTOM_PAD = 8f;
    private static final float MODULE_SCROLL_EXP = 12f;

    private static final int SETTINGS_LIST_TOP_OFFSET = 36;
    private static final float SETTINGS_PRESET_CIRCLE = 10f;
    private static final float SETTINGS_PRESET_GAP = 8f;

    private float moduleListMaxScroll = 0f;

    private SliderSetting draggedSlider;
    private int draggedSliderX;
    private int draggedSliderW;
    private ColorSetting draggedColorSetting;
    private int draggedColorX;
    private int draggedColorY;
    private int draggedColorW;

    @Nullable
    private Module moduleBindTarget;
    private int moduleBindPreviousKey;

    @Nullable
    private String moduleBindTooltipKey;
    private int moduleBindTooltipX;
    private int moduleBindTooltipY;

    public enum Tab {
        SETTINGS("Settings", "k", null, "System"),
        UPDATES("Updates", "l", null, "System"),
        CONFIGS("Configs", "m", null, "System"),

        COMBAT("Combat", "o", TypeList.Combat, "Combat"),
        MOVEMENT("Movement", "u", TypeList.Movement, "Combat"),

        RENDER("Render", "p", TypeList.Render, "Visual"),
        DISPLAY("Display", "q", TypeList.Display, "Visual"),

        PLAYER("Player", "r", TypeList.Player, "Visual"),
        MISC("Misc", "s", TypeList.Misc, "Other");

        public final String name;
        public final String icon;
        public final TypeList typeList;
        public final String group;

        Tab(String name, String icon, TypeList typeList, String group) {
            this.name = name;
            this.icon = icon;
            this.typeList = typeList;
            this.group = group;
        }
    }

    public ClickGUI() {
        this(null);
    }

    public ClickGUI(@Nullable Screen backgroundScreen) {
        super(Text.literal("ClickGUI"));
        this.backgroundScreen = backgroundScreen;
        this.currentTab = persistedTab;
        this.searchState = new SearchState();
        for (Tab tab : Tab.values()) {
            float scroll = persistedScrollByTab.getOrDefault(tab, 0f);
            scroll = Math.round(Math.max(0f, scroll));
            scrollOffsets.put(tab, scroll);
            scrollTargets.put(tab, scroll);
            tabSelectionAnims.put(tab, 0f);
            tabHoverAnims.put(tab, 0f);
        }
    }

    private final ru.method.celebression.integration.ui.util.player.TimerUtil openTimer = new ru.method.celebression.integration.ui.util.player.TimerUtil();

    private boolean firstGuiInit = true;

    @Override
    public void init() {
        super.init();
        if (firstGuiInit) {
            firstGuiInit = false;
            isClose = false;
            openAnimation.setValue(0);
            tabSwitchAnimation.setValue(1);
            AudioUtil.playSound("open.wav", 0.4f);
            openTimer.reset();
            for (Tab tab : Tab.values()) {
                tabSelectionAnims.put(tab, tab == currentTab ? 1f : 0f);
                tabHoverAnims.put(tab, 0f);
            }
        }
        resizeBackground();
        syncModuleListScrollBounds();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        resizeBackground();
        syncModuleListScrollBounds();
    }

    private void resizeBackground() {
        if (backgroundScreen != null) {
            backgroundScreen.resize(this.client, this.width, this.height);
        }
    }

    private record SettingsColorSlot(GuiThemeColor entry, float x, float y, int w, int h) {
    }

    private int computeSettingsThemeListMaxScrollPx() {
        if (Manager.STYLE_MANAGER == null) return 0;
        int h = this.height > 0 ? this.height : MinecraftClient.getInstance().getWindow().getScaledHeight();
        int startY = (h - WINDOW_HEIGHT) / 2;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int listY = contentY + SETTINGS_LIST_TOP_OFFSET;
        float totalH = settingsColorListContentHeight();
        if (totalH <= 0f) return 0;
        float viewportH = (startY + WINDOW_HEIGHT) - listY - MODULE_LIST_BOTTOM_PAD;
        float overflow = totalH - viewportH;
        if (overflow <= 0f) return 0;
        return Math.max(0, Math.round((float) Math.ceil(overflow) - 1.5f) - 5);
    }

    private float settingsColorListContentHeight() {
        List<GuiThemeColor> colors = Manager.STYLE_MANAGER.getColors();
        if (colors.isEmpty()) return 0f;
        int[] heights = settingsColorEntryHeights(colors);
        int[] col = packBalancedTwoColumns(settingsColorCollapsedHeights(colors));
        float stackL = 0f;
        float stackR = 0f;
        for (int i = 0; i < colors.size(); i++) {
            if (col[i] == 0) {
                stackL += heights[i] + MODULE_ROW_GAP_RENDER;
            } else {
                stackR += heights[i] + MODULE_ROW_GAP_RENDER;
            }
        }
        return Math.max(stackL, stackR);
    }

    private int[] settingsColorEntryHeights(List<GuiThemeColor> colors) {
        int[] heights = new int[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            heights[i] = Math.round(GuiColorPicker.rowHeight(colors.get(i)));
        }
        return heights;
    }

    private int[] settingsColorCollapsedHeights(List<GuiThemeColor> colors) {
        int[] heights = new int[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            heights[i] = GuiColorPicker.headerHeight(colors.get(i));
        }
        return heights;
    }

    private List<SettingsColorSlot> layoutSettingsColorSlots(int startX, int startY, float scroll) {
        int contentX = startX + SIDEBAR_WIDTH + 7;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;
        int columnWidth = contentW / 2;
        int x1 = contentX;
        int x2 = contentX + columnWidth + 3;
        int listY = contentY + SETTINGS_LIST_TOP_OFFSET;

        List<GuiThemeColor> colors = Manager.STYLE_MANAGER.getColors();
        int[] heights = settingsColorEntryHeights(colors);
        int[] col = packBalancedTwoColumns(settingsColorCollapsedHeights(colors));

        float y1 = listY - scroll;
        float y2 = listY - scroll;
        List<SettingsColorSlot> slots = new ArrayList<>(colors.size());
        for (int i = 0; i < colors.size(); i++) {
            boolean left = col[i] == 0;
            float x = left ? x1 : x2;
            float y = left ? y1 : y2;
            int w = columnWidth;
            slots.add(new SettingsColorSlot(colors.get(i), x, y, w, heights[i]));
            if (left) {
                y1 += heights[i] + MODULE_ROW_GAP_RENDER;
            } else {
                y2 += heights[i] + MODULE_ROW_GAP_RENDER;
            }
        }
        return slots;
    }

    private int computeModuleListMaxScrollPx(Tab tab) {
        if (tab == null) return 0;
        if (tab == Tab.SETTINGS) {
            return computeSettingsThemeListMaxScrollPx();
        }
        if (tab.typeList == null) return 0;
        int h = this.height > 0 ? this.height : MinecraftClient.getInstance().getWindow().getScaledHeight();
        int startY = (h - WINDOW_HEIGHT) / 2;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        List<Module> funcs = Manager.FUNCTION_MANAGER.getFunctions(tab.typeList);
        if (!searchState.text.isEmpty()) {
            funcs = funcs.stream().filter(f -> f.name.toLowerCase().contains(searchState.text.toLowerCase())).toList();
        }
        int n = funcs.size();
        if (n == 0) return 0;
        int[] heights = moduleCardHeightsForList(funcs);
        int[] col = packBalancedTwoColumns(moduleCardCollapsedHeightsForColumnLayout(funcs));
        float stackL = contentY;
        float stackR = contentY;
        for (int i = 0; i < n; i++) {
            int cardH = heights[i];
            if (col[i] == 0) {
                stackL += cardH + MODULE_ROW_GAP_RENDER;
            } else {
                stackR += cardH + MODULE_ROW_GAP_RENDER;
            }
        }
        float totalContentH = Math.max(stackL, stackR) - contentY;
        float viewportH = (startY + WINDOW_HEIGHT) - contentY - MODULE_LIST_BOTTOM_PAD;
        float overflow = totalContentH - viewportH;
        if (overflow <= 0f) return 0;
        return Math.max(0, Math.round((float) Math.ceil((double) overflow) - 1.5f) - 5);
    }

    private void syncModuleListScrollBounds() {
        if (currentTab == null) {
            moduleListMaxScroll = 0f;
            return;
        }
        if (currentTab == Tab.SETTINGS) {
            int maxPx = computeSettingsThemeListMaxScrollPx();
            moduleListMaxScroll = (float) maxPx;
            float st = Math.min(maxPx, Math.max(0f, scrollTargets.getOrDefault(currentTab, 0f)));
            float so = Math.min(maxPx, Math.max(0f, scrollOffsets.getOrDefault(currentTab, 0f)));
            float next = approachScrollOffset(so, st, (float) maxPx);
            float snapped = Math.round(MathHelper.clamp(next, 0f, (float) maxPx));
            scrollTargets.put(currentTab, snapped);
            scrollOffsets.put(currentTab, snapped);
            return;
        }
        if (currentTab.typeList == null) {
            moduleListMaxScroll = 0f;
            return;
        }
        int maxPx = computeModuleListMaxScrollPx(currentTab);
        moduleListMaxScroll = (float) maxPx;
        float st = Math.min(maxPx, Math.max(0f, scrollTargets.getOrDefault(currentTab, 0f)));
        float so = Math.min(maxPx, Math.max(0f, scrollOffsets.getOrDefault(currentTab, 0f)));
        float next = approachScrollOffset(so, st, (float) maxPx);
        float snapped = Math.round(MathHelper.clamp(next, 0f, (float) maxPx));
        scrollTargets.put(currentTab, snapped);
        scrollOffsets.put(currentTab, snapped);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        if (!isClose) {
            openAnimation.update(true);
        } else {
            openAnimation.update();
        }

        animation = openAnimation.getValue();
        if (isClose && openAnimation.isDone()) {
            mc.setScreen(backgroundScreen);
            return;
        }

        if (backgroundScreen != null) {
            backgroundScreen.render(ctx, mouseX, mouseY, delta);
        }
        KawaseBlur.blur.updateBlur(3f, 3);

        int startX = (width - WINDOW_WIDTH) / 2;
        int startY = (height - WINDOW_HEIGHT) / 2;

        ctx.getMatrices().push();

        float scale = getGuiScale();
        RenderAddon.sizeAnimation(ctx.getMatrices(), width / 2.0f, height / 2.0f, scale);

        double[] guiMouseBuf = new double[2];
        screenToGui(mouseX, mouseY, guiMouseBuf);
        int guiMouseX = (int) Math.round(guiMouseBuf[0]);
        int guiMouseY = (int) Math.round(guiMouseBuf[1]);

        if (animation > 0.01f) {
            for (int i = 1; i <= 5; i++) {
                RenderUtil.drawRoundedRect(ctx.getMatrices(), startX - i, startY - i, WINDOW_WIDTH + i * 2, WINDOW_HEIGHT + i * 2, 10 + i, RenderUtil.applyOpacity(0, animation * (0.05f / i)));
            }
        }

        int lineColor = guiCa("scrollColor", new Color(39, 38, 38).getRGB());
        int fonColor = ColorUtil.setAlpha(guiCa("fonColor", new Color(13, 13, 13).getRGB()), 245);
        int lineColorAlpha = RenderUtil.applyOpacity(lineColor, animation * 0.8f);
        float lineThickness = -0.4f;
        RenderUtil.drawBlurredRoundedRectangle(ctx.getMatrices(), startX, startY, WINDOW_WIDTH, WINDOW_HEIGHT, 10, fonColor, animation);
        RenderUtil.drawRoundedBorder(ctx.getMatrices(), startX, startY, WINDOW_WIDTH, WINDOW_HEIGHT, 10, lineThickness, lineColorAlpha);

        RenderUtil.drawRoundedBorder(ctx.getMatrices(), startX, startY + 29, WINDOW_WIDTH, 1.25f, 0, lineThickness, lineColorAlpha);
        RenderUtil.drawRoundedBorder(ctx.getMatrices(), startX + 110, startY + 29, 1.25f, WINDOW_HEIGHT - 29f, 0, lineThickness, lineColorAlpha);
        RenderUtil.drawRoundedBorder(ctx.getMatrices(), startX, sidebarSystemDividerY(startY), 111, 1.25f, 0, lineThickness, lineColorAlpha);

        renderTopBar(ctx, startX, startY, guiMouseX, guiMouseY);
        renderSidebar(ctx, startX - 20, startY, guiMouseX, guiMouseY);

        moduleBindTooltipKey = null;
        renderContent(ctx, startX, startY, guiMouseX, guiMouseY);

        if (moduleBindTooltipKey != null) {
            renderModuleBindTooltip(ctx, moduleBindTooltipX, moduleBindTooltipY, moduleBindTooltipKey, animation);
        }

        ctx.getMatrices().pop();
    }

    private int guiC(String key) {
        if (Manager.STYLE_MANAGER == null) return -1;
        return Manager.STYLE_MANAGER.getColorByName(key);
    }

    private int guiCa(String key, int fallback) {
        int c = guiC(key);
        return c == -1 ? fallback : c;
    }

    private int guiTextRgb() {
        return guiCa("textColor", -1);
    }

    private int guiTextPrimary() {
        return ao(guiTextRgb());
    }

    private int guiTextDim(float factor) {
        return ao(ColorUtil.scaleRgb(guiTextRgb(), factor));
    }

    private int ao(int color) {
        return RenderUtil.applyOpacity(color, animation);
    }

    private int topBarFillColor() {
        return ao(new Color(0, 0, 0, 81).getRGB());
    }

    private float getGuiScale() {
        if (!isClose) {
            return 0.75f + animation * 0.25f;
        }
        float closeProgress = 1.0f - animation;
        return 1.0f + closeProgress * 0.05f;
    }

    private void screenToGui(double screenX, double screenY, double[] out) {
        float s = getGuiScale();
        float px = this.width / 2.0f;
        float py = this.height / 2.0f;
        out[0] = px + (screenX - px) / s;
        out[1] = py + (screenY - py) / s;
    }

    private static final float TOP_BAR_ROW_GAP = 4f;
    private static final float TOP_BAR_ROW_PAD = 5f;

    private float topBarTextY(int barY, int barH) {
        return barY + (barH - FontUtils.inter[13].getHeight()) / 2f;
    }

    private float topBarIconY(int barY, int barH) {
        return barY + (barH - FontUtils.celebression[14].getHeight()) / 2f;
    }

    private float topBarIconTextBlockWidth(String icon, String text) {
        return FontUtils.celebression[14].getWidth(icon) + TOP_BAR_ROW_GAP + FontUtils.inter[13].getWidth(text);
    }

    private float topBarIconTextBlockLeft(int barX, int barW, String icon, String text) {
        return barX + (barW - topBarIconTextBlockWidth(icon, text)) / 2f;
    }

    private void drawTopBarIconTextRow(DrawContext ctx, String icon, String text, int barX, int barY, int barW, int barH, int color) {
        float iconW = FontUtils.celebression[14].getWidth(icon);
        float blockLeft = topBarIconTextBlockLeft(barX, barW, icon, text);
        FontUtils.celebression[14].drawLeftAligned(ctx.getMatrices(), icon, blockLeft, topBarIconY(barY, barH), color);
        FontUtils.inter[13].drawLeftAligned(ctx.getMatrices(), text, blockLeft + iconW + TOP_BAR_ROW_GAP, topBarTextY(barY, barH), color);
    }

    private void drawTopBarCenteredIcon(DrawContext ctx, String icon, int barX, int barY, int barW, int barH, int color) {
        float iconW = FontUtils.celebression[14].getWidth(icon);
        FontUtils.celebression[14].drawLeftAligned(ctx.getMatrices(), icon, barX + (barW - iconW) / 2f, topBarIconY(barY, barH), color);
    }

    private int langPillWidth(MenuLanguageState.Language lang) {
        float inner = topBarIconTextBlockWidth("e", lang.nativeName());
        return Math.max(43, Math.round(inner + TOP_BAR_ROW_PAD * 2));
    }

    private void computeLangBarFrame(int startX, int startY, int mouseX, int mouseY, boolean tickAnim) {
        float swapT = tickAnim ? langSwapAnim.update() : MathHelper.clamp(langSwapAnim.getValue(), 0f, 1f);
        if (tickAnim && langSwapAnim.isDone() && langSwapPrevious != null) {
            langSwapPrevious = null;
        }
        langBarSwapEased = Easing.QUARTIC_OUT.ease(MathHelper.clamp(swapT, 0f, 1f), 0, 1, 1);
        MenuLanguageState.Language currentLang = MenuLanguageState.selected();
        int wNew = langPillWidth(currentLang);
        int wOld = langSwapPrevious != null ? langPillWidth(langSwapPrevious) : wNew;
        langBarPillW = Math.max(43, Math.round(wOld + (wNew - wOld) * langBarSwapEased));
        langBarFlagX = startX + WINDOW_WIDTH - 25;
        langBarPillX = langBarFlagX - 5 - langBarPillW;
        langBarSearchY = startY + 7;
        langBarSearchH = 18;
        boolean pillHovered = mouseX >= langBarPillX && mouseX <= langBarPillX + langBarPillW
                && mouseY >= langBarSearchY && mouseY <= langBarSearchY + langBarSearchH;
        boolean flagHovered = mouseX >= langBarFlagX && mouseX <= langBarFlagX + LANG_BAR_FLAG_W
                && mouseY >= langBarSearchY && mouseY <= langBarSearchY + langBarSearchH;
        langPillHoverAnim = MathUtil.lerp(langPillHoverAnim, pillHovered ? 1f : 0f, 18f);
        langFlagHoverAnim = MathUtil.lerp(langFlagHoverAnim, flagHovered ? 1f : 0f, 18f);
    }

    private void renderTopBar(DrawContext ctx, int startX, int startY, int mouseX, int mouseY) {
        int topBarFill = topBarFillColor();

        RenderUtil.drawRoundedRect(ctx.getMatrices(), startX + 8.5f, startY + 7f, 18, 18, 5, topBarFill);
        FontUtils.celebression[22].drawLeftAligned(ctx.getMatrices(), "a", startX + 12f, startY + 10f, guiTextPrimary());
        FontUtils.roboto[15].drawLeftAligned(ctx.getMatrices(), "celebression", startX + 31.5f, startY + 11.5f, guiTextPrimary());

        int searchW = 130;
        int searchH = 18;
        int searchX = startX + (WINDOW_WIDTH - searchW) / 2;
        int searchY = startY + 7;

        boolean searchHovered = mouseX >= searchX && mouseX <= searchX + searchW
                && mouseY >= searchY && mouseY <= searchY + searchH;
        searchHoverAnim = MathUtil.lerp(searchHoverAnim, searchHovered ? 1f : 0f, 18f);

        int iconMuted = guiTextDim(TEXT_F_ICON);
        int iconLight = guiTextDim(TEXT_F_LIGHT);
        int searchTextColor = ColorUtil.interpolateColor(iconMuted, iconLight, searchHoverAnim * 0.38f);
        float searchCx = searchX + searchW / 2f;
        float searchCy = searchY + searchH / 2f;
        float searchScale = 1f + searchHoverAnim * 0.034f;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(searchCx, searchCy, 0);
        ctx.getMatrices().scale(searchScale, searchScale, 1f);
        ctx.getMatrices().translate(-searchCx, -searchCy, 0);

        RenderUtil.drawRoundedRect(ctx.getMatrices(), searchX, searchY, searchW, searchH, 3.5f, topBarFill);

        String displaySearch = searchState.text.isEmpty() ? "Search" : searchState.text;
        if (searchState.focused && (System.currentTimeMillis() / 500) % 2 == 0) displaySearch += "|";
        drawTopBarIconTextRow(ctx, "d", displaySearch, searchX, searchY, searchW, searchH, searchTextColor);

        ctx.getMatrices().pop();

        computeLangBarFrame(startX, startY, mouseX, mouseY, true);

        int langPillX = langBarPillX;
        int langPillW = langBarPillW;
        int langFlagBtnX = langBarFlagX;
        int flagBtnW = LANG_BAR_FLAG_W;

        float swapPulse = (float) Math.sin(langBarSwapEased * Math.PI) * 0.022f;
        float pillScale = (1f + langPillHoverAnim * 0.034f) * (1f + swapPulse);

        int pillFill = topBarFill;
        int iconPillHi = ColorUtil.interpolateColor(iconMuted, iconLight, langPillHoverAnim * 0.38f);
        int iconFlagHi = ColorUtil.interpolateColor(iconMuted, iconLight, langFlagHoverAnim * 0.38f);

        float pillCx = langPillX + langPillW / 2f;
        float pillCy = searchY + searchH / 2f;
        float e = langBarSwapEased;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(pillCx, pillCy, 0);
        ctx.getMatrices().scale(pillScale, pillScale, 1f);
        ctx.getMatrices().translate(-pillCx, -pillCy, 0);

        RenderUtil.drawRoundedRect(ctx.getMatrices(), langPillX, searchY, langPillW, searchH, 3.5f, pillFill);

        MenuLanguageState.Language currentLang = MenuLanguageState.selected();
        String langLabel = currentLang.nativeName();
        float langIconW = FontUtils.celebression[14].getWidth("e");
        float langTextW = FontUtils.inter[13].getWidth(langLabel);
        if (langSwapPrevious != null && e < 0.998f) {
            float oldTextW = FontUtils.inter[13].getWidth(langSwapPrevious.nativeName());
            langTextW = oldTextW + (langTextW - oldTextW) * e;
        }
        float langBlockW = langIconW + TOP_BAR_ROW_GAP + langTextW;
        float langBlockLeft = langPillX + (langPillW - langBlockW) / 2f;
        float langTextX = langBlockLeft + langIconW + TOP_BAR_ROW_GAP;
        float langIconY = topBarIconY(searchY, searchH);
        FontUtils.celebression[14].drawLeftAligned(ctx.getMatrices(), "e", langBlockLeft, langIconY, iconPillHi);

        ctx.getMatrices().pop();

        float slide = 7f;
        int textBase = guiTextDim(TEXT_F_ICON);
        float langTextY = topBarTextY(searchY, searchH);
        int clipX = Math.max(langPillX, (int) langTextX);
        int clipW = Math.max(0, langPillX + langPillW - clipX - 2);
        Scissor.push();
        Scissor.setFromComponentCoordinates(clipX, searchY, clipW, searchH);
        if (langSwapPrevious != null && e < 0.998f) {
            float outX;
            float inX;
            if (langSwapWentForward) {
                outX = langTextX - e * slide;
                inX = langTextX + (1f - e) * slide;
            } else {
                outX = langTextX + e * slide;
                inX = langTextX - (1f - e) * slide;
            }
            FontUtils.inter[13].drawLeftAligned(ctx.getMatrices(), langSwapPrevious.nativeName(), outX, langTextY, RenderUtil.applyOpacity(textBase, 1f - e));
            FontUtils.inter[13].drawLeftAligned(ctx.getMatrices(), langLabel, inX, langTextY, RenderUtil.applyOpacity(textBase, e));
        } else {
            FontUtils.inter[13].drawLeftAligned(ctx.getMatrices(), langLabel, langTextX, langTextY, textBase);
        }
        Scissor.pop();

        float flagCx = langFlagBtnX + flagBtnW / 2f;
        float flagCy = searchY + searchH / 2f;
        float flagScale = 1f + langFlagHoverAnim * 0.034f;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(flagCx, flagCy, 0);
        ctx.getMatrices().scale(flagScale, flagScale, 1f);
        ctx.getMatrices().translate(-flagCx, -flagCy, 0);

        RenderUtil.drawRoundedRect(ctx.getMatrices(), langFlagBtnX, searchY, flagBtnW, searchH, 3.5f, pillFill);

        drawTopBarCenteredIcon(ctx, "f", langFlagBtnX, searchY, flagBtnW, searchH, iconFlagHi);

        ctx.getMatrices().pop();
    }

    private float sidebarSystemDividerY(int startY) {
        int systemTabCount = 0;
        for (Tab tab : Tab.values()) {
            if (tab.group.equals("System")) {
                systemTabCount++;
            }
        }
        int systemTabsEndY = startY + TOPBAR_HEIGHT - 14 + systemTabCount * 17;
        return systemTabsEndY + 4.5f;
    }

    private void renderSidebar(DrawContext ctx, int startX, int startY, int mouseX, int mouseY) {
        int currentY = startY + TOPBAR_HEIGHT - 14;

        for (Tab tab : Tab.values()) {
            if (tab.group.equals("System")) {
                renderTab(ctx, startX, currentY, tab, mouseX, mouseY, false);
                currentY += 17;
            }
        }

        currentY += 12;

        for (String group : new String[]{"Combat", "Visual", "Other"}) {
            FontUtils.inter[11].drawLeftAligned(ctx.getMatrices(), group, startX + 32, currentY, guiTextDim(TEXT_F_GROUP));
            FontUtils.celebression[11].drawLeftAligned(ctx.getMatrices(), "j", startX + 34.5f + FontUtils.inter[11].getWidth(group), currentY + 0.5f, guiTextDim(TEXT_F_GROUP));

            currentY += 11;

            for (Tab tab : Tab.values()) {
                if (tab.group.equals(group)) {
                    renderTab(ctx, startX, currentY, tab, mouseX, mouseY, true);
                    currentY += 17;
                }
            }
            currentY += 4;
        }

        int profileY = startY + WINDOW_HEIGHT - 24;

        RenderUtil.drawRoundedRect(ctx.getMatrices(), startX + 30, profileY - 2, 16, 16, 7, ao(new Color(30, 30, 30).getRGB()));
        FontUtils.icomoon[10].drawLeftAligned(ctx.getMatrices(), "A", startX + 17 + 12, profileY + 2, guiTextPrimary());
        FontUtils.inter[12].drawLeftAligned(ctx.getMatrices(), "t.me/soezproject", startX + 38 + 12, profileY - 0.5f, guiTextPrimary());
        FontUtils.roboto[11].drawLeftAligned(ctx.getMatrices(), "Admin", startX + 38 + 12, profileY + 6.5f, ao(new Color(209, 44, 44).getRGB()));
    }

    private void renderTab(DrawContext ctx, int startX, int y, Tab tab, int mouseX, int mouseY, boolean sub) {
        int x = startX + 18;
        int tabW = SIDEBAR_WIDTH - 20;
        boolean selected = currentTab == tab;
        boolean hovered = mouseX >= x && mouseX <= x + tabW && mouseY >= y && mouseY <= y + 22;

        float selectAnim = tabSelectionAnims.getOrDefault(tab, 0f);
        selectAnim = MathUtil.lerp(selectAnim, selected ? 1f : 0f, 15f);
        tabSelectionAnims.put(tab, selectAnim);

        float hoverAnim = tabHoverAnims.getOrDefault(tab, 0f);
        hoverAnim = MathUtil.lerp(hoverAnim, hovered ? 1f : 0f, 15f);
        tabHoverAnims.put(tab, hoverAnim);

        if (sub && selectAnim > 0.001f) {
            RenderUtil.drawRoundedRect(ctx.getMatrices(), x + 12, y + 1.5f, tabW, 15, 2, ao(RenderUtil.applyOpacity(new Color(0, 0, 0).getRGB(), selectAnim * 0.2f)));
        }

        int accent = getAccentRgb();
        int accentR = (accent >> 16) & 0xFF;
        int accentG = (accent >> 8) & 0xFF;
        int accentB = accent & 0xFF;

        int textRgb = guiTextRgb();
        int baseR = MathHelper.clamp(Math.round(ColorUtil.getRed(textRgb) * TEXT_F_TAB), 0, 255);
        int baseG = MathHelper.clamp(Math.round(ColorUtil.getGreen(textRgb) * TEXT_F_TAB), 0, 255);
        int baseB = MathHelper.clamp(Math.round(ColorUtil.getBlue(textRgb) * TEXT_F_TAB), 0, 255);
        int hoverR = MathHelper.clamp(Math.round(ColorUtil.getRed(textRgb) * TEXT_F_TAB_HOVER), 0, 255);
        int hoverG = MathHelper.clamp(Math.round(ColorUtil.getGreen(textRgb) * TEXT_F_TAB_HOVER), 0, 255);
        int hoverB = MathHelper.clamp(Math.round(ColorUtil.getBlue(textRgb) * TEXT_F_TAB_HOVER), 0, 255);

        int r = MathHelper.clamp((int) (baseR + (accentR - baseR) * selectAnim + (hoverR - baseR) * hoverAnim * (1 - selectAnim)), 0, 255);
        int g = MathHelper.clamp((int) (baseG + (accentG - baseG) * selectAnim + (hoverG - baseG) * hoverAnim * (1 - selectAnim)), 0, 255);
        int b = MathHelper.clamp((int) (baseB + (accentB - baseB) * selectAnim + (hoverB - baseB) * hoverAnim * (1 - selectAnim)), 0, 255);

        int textColor = ao(ColorUtil.rgba(r, g, b, ColorUtil.getAlpha(textRgb) == 0 ? 255 : ColorUtil.getAlpha(textRgb)));

        int iconX = x + (sub ? 16 : 9);
        int textX = iconX + 11;

        FontUtils.celebression[14].drawLeftAligned(ctx.getMatrices(), tab.icon, iconX + 1, y + 5, textColor);
        FontUtils.inter[12].drawLeftAligned(ctx.getMatrices(), tab.name, textX, y + 5, textColor);
    }

    private void contentClipBounds(int startX, int startY, float[] out) {
        float currentScale;
        if (!isClose) {
            currentScale = 0.85f + animation * 0.15f;
        } else {
            float closeProgress = 1.0f - animation;
            currentScale = 1.0f + closeProgress * 0.15f;
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        float pivotX = mc.getWindow().getScaledWidth() / 2.0f;
        float pivotY = mc.getWindow().getScaledHeight() / 2.0f;
        float sx = startX + SIDEBAR_WIDTH + 1;
        float sy = startY + TOPBAR_HEIGHT + 1;
        float sw = WINDOW_WIDTH - SIDEBAR_WIDTH - 2;
        float sh = WINDOW_HEIGHT - TOPBAR_HEIGHT - 2;
        out[0] = pivotX + (sx - pivotX) * currentScale;
        out[1] = pivotY + (sy - pivotY) - 16 * currentScale;
        out[2] = sw * currentScale;
        out[3] = sh + 16 * currentScale;
    }

    private boolean pointInContentClip(double gx, double gy, int startX, int startY) {
        float[] clip = new float[4];
        contentClipBounds(startX, startY, clip);
        return gx >= clip[0] && gx <= clip[0] + clip[2] && gy >= clip[1] && gy <= clip[1] + clip[3];
    }

    private void renderContent(DrawContext ctx, int startX, int startY, int mouseX, int mouseY) {
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;

        float[] clip = new float[4];
        contentClipBounds(startX, startY, clip);

        float transitionProgress = tabSwitchAnimation.update();

        ctx.getMatrices().push();

        Scissor.push();
        Scissor.setFromComponentCoordinates(clip[0], clip[1], clip[2], clip[3]);

        if (currentTab != null && currentTab.typeList != null) {
            List<Module> funcs = Manager.FUNCTION_MANAGER.getFunctions(currentTab.typeList);
            if (!searchState.text.isEmpty()) {
                funcs = funcs.stream().filter(f -> f.name.toLowerCase().contains(searchState.text.toLowerCase())).toList();
            }
            for (Module f : funcs) {
                updateExpandAnimation(f);
            }
            syncModuleListScrollBounds();
        } else if (currentTab == Tab.SETTINGS) {
            syncModuleListScrollBounds();
        } else {
            moduleListMaxScroll = 0f;
        }

        if (transitionProgress < 1.0f && previousTab != null) {
            float eased = Easing.QUARTIC_OUT.ease(transitionProgress, 0, 1, 1);
            float xOffsetOld = -eased * (contentW + 20);
            float xOffsetNew = (1.0f - eased) * (contentW + 20);

            renderTabContent(ctx, previousTab, startX, startY, mouseX, mouseY, 1.0f - eased, xOffsetOld);
            renderTabContent(ctx, currentTab, startX, startY, mouseX, mouseY, eased, xOffsetNew);
        } else {
            renderTabContent(ctx, currentTab, startX, startY, mouseX, mouseY, 1.0f, 0f);
        }

        Scissor.pop();
        ctx.getMatrices().pop();
    }

    private void renderTabContent(DrawContext ctx, Tab tab, int startX, int startY, int mouseX, int mouseY, float alpha, float xOffset) {
        if (tab == Tab.SETTINGS) {
            renderSettingsContent(ctx, startX, startY, mouseX, mouseY, alpha, xOffset);
            return;
        }
        renderModuleList(ctx, tab, startX, startY, mouseX, mouseY, alpha, xOffset);
    }

    private void renderSettingsContent(DrawContext ctx, int startX, int startY, int mouseX, int mouseY, float alpha, float xOffset) {
        if (Manager.STYLE_MANAGER == null) return;

        float tabAlpha = Math.max(alpha, 0.001f);
        int contentX = startX + SIDEBAR_WIDTH + 5;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 10;
        int panelY = contentY + 6;
        int listY = contentY + SETTINGS_LIST_TOP_OFFSET;
        float scroll = scrollOffsets.getOrDefault(Tab.SETTINGS, 0f);
        float viewportH = (startY + WINDOW_HEIGHT) - listY - MODULE_LIST_BOTTOM_PAD;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(xOffset, 0, 0);

        int titleColor = RenderUtil.applyOpacity(guiTextPrimary(), tabAlpha);
        int muted = RenderUtil.applyOpacity(
                ao(ColorUtil.interpolateColor(ColorUtil.scaleRgb(guiTextRgb(), TEXT_F_SOFT), guiTextRgb(), 0.15f)),
                tabAlpha
        );

        FontUtils.celebression[16].drawLeftAligned(ctx.getMatrices(), "k", contentX + 4, panelY - 3, titleColor);
        FontUtils.roboto[15].drawLeftAligned(ctx.getMatrices(), "Settings", contentX + 17, panelY - 3, titleColor);
        FontUtils.inter[13].drawLeftAligned(ctx.getMatrices(), "Цвета интерфейса · ПКМ — пикер", contentX + 4, panelY + 11, muted);

        renderSettingsPresets(ctx, contentX, contentY, contentW, panelY, mouseX, mouseY, tabAlpha);

        Scissor.push();
        Scissor.setFromComponentCoordinates(contentX, listY, contentW, viewportH);

        List<SettingsColorSlot> slots = layoutSettingsColorSlots(startX, startY, scroll);
        for (SettingsColorSlot slot : slots) {
            if (slot.y + slot.h >= listY && slot.y <= listY + viewportH) {
                GuiColorPicker.render(
                        ctx,
                        Math.round(slot.x),
                        slot.y,
                        slot.w,
                        slot.entry,
                        mouseX,
                        mouseY,
                        tabAlpha,
                        this::ao
                );
            }
        }

        Scissor.pop();
        ctx.getMatrices().pop();
    }

    private void renderSettingsPresets(DrawContext ctx, int contentX, int contentY, int contentW, int panelY, int mouseX, int mouseY, float tabAlpha) {
        var presets = StyleManager.PRESETS;
        int selected = Manager.STYLE_MANAGER.getSelectedPreset();
        float circle = SETTINGS_PRESET_CIRCLE;
        float gap = SETTINGS_PRESET_GAP;
        float totalW = presets.length * circle + (presets.length - 1) * gap;
        float startX = contentX + contentW - totalW - 4;
        float centerY = panelY + 4;

        for (int i = 0; i < presets.length; i++) {
            float cx = startX + i * (circle + gap) + circle / 2f;
            float cy = centerY;
            boolean hovered = Math.hypot(mouseX - cx, mouseY - cy) <= circle / 2f + 2f;
            int preview = RenderUtil.applyOpacity(ao(presets[i].previewColor()), tabAlpha);
            RenderUtil.drawCircle(ctx.getMatrices(), cx, cy, circle, preview);

            if (i == selected) {
                int ring = RenderUtil.applyOpacity(ao(getAccentRgb()), tabAlpha);
                RenderUtil.drawCircleBorder(ctx.getMatrices(), cx, cy, circle + 3f, -0.55f, ring);
            } else if (hovered) {
                int ring = RenderUtil.applyOpacity(ao(ColorUtil.scaleRgb(guiTextRgb(), TEXT_F_HOVER)), tabAlpha * 0.7f);
                RenderUtil.drawCircleBorder(ctx.getMatrices(), cx, cy, circle + 2f, -0.4f, ring);
            }
        }
    }

    private boolean clickSettingsPresets(double gx, double gy, int startX, int startY) {
        int contentX = startX + SIDEBAR_WIDTH + 5;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 10;
        int panelY = contentY + 6;

        var presets = StyleManager.PRESETS;
        float circle = SETTINGS_PRESET_CIRCLE;
        float gap = SETTINGS_PRESET_GAP;
        float totalW = presets.length * circle + (presets.length - 1) * gap;
        float presetStartX = contentX + contentW - totalW - 4;
        float centerY = panelY + 4;

        for (int i = 0; i < presets.length; i++) {
            float cx = presetStartX + i * (circle + gap) + circle / 2f;
            if (Math.hypot(gx - cx, gy - centerY) <= circle / 2f + 2f) {
                Manager.STYLE_MANAGER.applyPreset(i);
                return true;
            }
        }
        return false;
    }

    private boolean clickSettingsColors(double gx, double gy, int startX, int startY, int button) {
        if (Manager.STYLE_MANAGER == null) return false;

        int contentX = startX + SIDEBAR_WIDTH + 5;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;
        int listY = contentY + SETTINGS_LIST_TOP_OFFSET;
        float viewportH = (startY + WINDOW_HEIGHT) - listY - MODULE_LIST_BOTTOM_PAD;
        float scroll = scrollOffsets.getOrDefault(Tab.SETTINGS, 0f);

        if (button == 0 && clickSettingsPresets(gx, gy, startX, startY)) {
            return true;
        }

        if (gx < contentX || gx > contentX + contentW || gy < listY || gy > listY + viewportH) {
            if (button == 0 || button == 1) {
                Manager.STYLE_MANAGER.closeAllPickers();
            }
            return false;
        }
        if (!pointInContentClip(gx, gy, startX, startY)) {
            return false;
        }

        for (SettingsColorSlot slot : layoutSettingsColorSlots(startX, startY, scroll)) {
            if (slot.y + slot.h < listY || slot.y > listY + viewportH) {
                continue;
            }
            if (GuiColorPicker.mouseClicked(gx, gy, button, Math.round(slot.x), slot.y, slot.w, slot.entry)) {
                return true;
            }
        }
        return false;
    }

    private void dragSettingsColors(int mouseX, int mouseY, int startX, int startY) {
        if (Manager.STYLE_MANAGER == null) return;
        float scroll = scrollOffsets.getOrDefault(Tab.SETTINGS, 0f);
        for (SettingsColorSlot slot : layoutSettingsColorSlots(startX, startY, scroll)) {
            GuiColorPicker.applyDrag(mouseX, mouseY, Math.round(slot.x), slot.y, slot.w, slot.entry);
        }
    }

    private boolean anySettingsColorDragging() {
        if (Manager.STYLE_MANAGER == null) return false;
        for (GuiThemeColor entry : Manager.STYLE_MANAGER.getColors()) {
            if (GuiColorPicker.isDragging(entry)) return true;
        }
        return false;
    }

    private void renderModuleList(DrawContext ctx, Tab tab, int startX, int startY, int mouseX, int mouseY, float alpha, float xOffset) {
        if (tab == null || tab.typeList == null) return;

        int contentX = startX + SIDEBAR_WIDTH + 5;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;

        List<Module> funcs = Manager.FUNCTION_MANAGER.getFunctions(tab.typeList);
        if (!searchState.text.isEmpty()) {
            funcs = funcs.stream().filter(f -> f.name.toLowerCase().contains(searchState.text.toLowerCase())).toList();
        }

        int n = funcs.size();
        int[] cardHeights = moduleCardHeightsForList(funcs);
        int[] col = packBalancedTwoColumns(moduleCardCollapsedHeightsForColumnLayout(funcs));

        float so = scrollOffsets.getOrDefault(tab, 0f);
        int columnWidth = contentW / 2;
        int x1 = contentX;
        int x2 = contentX + columnWidth + 3;

        float y1 = contentY - so;
        float y2 = contentY - so;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(xOffset, 0, 0);

        for (int i = 0; i < n; i++) {
            Module f = funcs.get(i);
            int cardH = cardHeights[i];
            if (col[i] == 0) {
                renderModuleCard(ctx, x1, Math.round(y1), columnWidth, f, mouseX, mouseY, i, alpha, xOffset);
                y1 += cardH + MODULE_ROW_GAP_RENDER;
            } else {
                renderModuleCard(ctx, x2, Math.round(y2), columnWidth, f, mouseX, mouseY, i, alpha, xOffset);
                y2 += cardH + MODULE_ROW_GAP_RENDER;
            }
        }

        ctx.getMatrices().pop();
    }



    private void renderModuleCard(DrawContext ctx, int x, int y, int width, Module f, int mouseX, int mouseY, int index, float alpha, float tabSlideX) {
        this.currentTransitionAlpha = alpha;
        Animation anim = expandAnims.get(f);
        float progRaw = anim != null ? anim.getValue() : (f.expanded ? 1f : 0f);
        float prog = MathHelper.clamp(progRaw, 0f, 1f);
        int settingsH = (int) (computeSettingsHeight(f, width - 16) * prog);
        int headerH = moduleCardHeaderHeight(f);
        int totalH = headerH + settingsH;

        float tabAlpha = Math.max(alpha, 0.001f);

        ctx.getMatrices().push();

        int bgColor = RenderUtil.applyOpacity(topBarFillColor(), tabAlpha);

        RenderUtil.drawRoundedRect(ctx.getMatrices(), x, y, width, totalH, 2.5f, bgColor);

        boolean isBindingModule = f == moduleBindTarget;

        int fontColor = RenderUtil.applyOpacity(guiTextPrimary(), tabAlpha);
        int secondaryColor = RenderUtil.applyOpacity(guiTextDim(TEXT_F_SECONDARY), tabAlpha);

        FontUtils.inter[16].drawLeftAligned(ctx.getMatrices(), f.name, x + 8.5f, y + 8.5f, fontColor);

        if (!isBindingModule && f.getBindCode() != 0) {
            float iconX = x + 12f + FontUtils.inter[16].getWidth(f.name);
            float iconY = y + 10f;
            float iconW = FontUtils.celebression[16].getWidth("h");
            float iconH = FontUtils.celebression[16].getHeight();
            FontUtils.celebression[16].drawLeftAligned(ctx.getMatrices(), "h", iconX, iconY, secondaryColor);

            if (mouseX >= iconX - 2 && mouseX <= iconX + iconW + 2
                    && mouseY >= iconY - 2 && mouseY <= iconY + iconH + 2) {
                moduleBindTooltipKey = BindSetting.formatBindKeyName(f.getBindCode());
                moduleBindTooltipX = mouseX;
                moduleBindTooltipY = mouseY;
            }
        }

        if (isBindingModule) {
            List<String> bindLines = FontUtils.roboto[13].splitTextToLines(MODULE_BIND_HINT, MODULE_DESC_MAX_LINE_WIDTH);
            for (int i = 0; i < bindLines.size(); i++) {
                FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), bindLines.get(i), x + 8, y + 22 + i * MODULE_DESC_LINE_STEP, secondaryColor);
            }
        } else if (f.desc != null && !f.desc.isEmpty()) {
            List<String> descLines = FontUtils.roboto[13].splitTextToLines(f.desc, MODULE_DESC_MAX_LINE_WIDTH);
            for (int i = 0; i < descLines.size(); i++) {
                FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), descLines.get(i), x + 8, y + 22 + i * MODULE_DESC_LINE_STEP, secondaryColor);
            }
        }

        int toggleX = x + width - 42;
        int toggleY = y + 9;
        boolean expandable = hasExpandableSettings(f);
        int toggleDrawX = expandable ? toggleX : toggleX + 19;
        renderToggle(ctx, toggleDrawX, toggleY, f.state, tabAlpha, f);

        if (expandable) {
            RenderUtil.drawRoundedRect(ctx.getMatrices(), toggleX + 22, toggleY, 13, 10.5f, 2f, RenderUtil.applyOpacity(topBarFillColor(), tabAlpha));

            float arrowX = toggleX + 25.5f;
            float arrowY = toggleY + 2;
            float iconW = FontUtils.celebression[12].getWidth("j");
            float iconH = FontUtils.celebression[12].getHeight();

            ctx.getMatrices().push();
            ctx.getMatrices().translate(arrowX + iconW / 2f, arrowY + iconH / 2f, 0);
            ctx.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(prog * 180f));
            ctx.getMatrices().translate(-(arrowX + iconW / 2f), -(arrowY + iconH / 2f), 0);
            FontUtils.celebression[12].drawLeftAligned(ctx.getMatrices(), "j", arrowX, arrowY, RenderUtil.applyOpacity(guiTextDim(TEXT_F_MUTED), tabAlpha));
            ctx.getMatrices().pop();
        }

        if (progRaw > 0.001f) {
            int curY = y + headerH;

            ctx.getMatrices().push();
            float settingsScale = 0.95f + prog * 0.05f;
            RenderAddon.sizeAnimation(ctx.getMatrices(), x + width / 2f, y + headerH + settingsH / 2f, settingsScale);

            float settingsAlpha = tabAlpha * MathHelper.clamp(progRaw, 0f, 1f);
            this.currentTransitionAlpha = settingsAlpha;

            Scissor.push();
            Scissor.setFromComponentCoordinates(x + tabSlideX, y + headerH, width, settingsH);

            int sIndex = 0;
            for (Setting s : f.getSettings()) {
                if (!s.isVisible()) continue;
                int sH = getSettingHeight(s, width - 16);

                float staggerProg = MathHelper.clamp(progRaw * 1.6f - (sIndex * 0.08f), 0f, 1f);
                float easedStagger = Easing.SMOOTH_STEP.ease(staggerProg, 0, 1, 1);
                float staggerX = (1.0f - easedStagger) * -8f;
                float staggerY = (1.0f - easedStagger) * 4f;

                ctx.getMatrices().push();
                ctx.getMatrices().translate(staggerX, staggerY, 0);
                renderSetting(ctx, x + 8, curY, width - 16, s, mouseX, mouseY, settingsAlpha * easedStagger);
                ctx.getMatrices().pop();

                curY += sH + 5;
                sIndex++;
            }

            Scissor.pop();
            ctx.getMatrices().pop();

            this.currentTransitionAlpha = tabAlpha;
        }

        ctx.getMatrices().pop();
    }

    private void renderModuleBindTooltip(DrawContext ctx, int mouseX, int mouseY, String keyName, float alpha) {
        float padX = 5f;
        float padY = 3f;
        float textW = FontUtils.roboto[12].getWidth(keyName);
        float textH = FontUtils.roboto[12].getHeight();
        float w = textW + padX * 2;
        float h = textH + padY * 2;

        float tipX = mouseX + 3f;
        float tipY = mouseY - h - 2f;

        float startX = (this.width - WINDOW_WIDTH) / 2f;
        float startY = (this.height - WINDOW_HEIGHT) / 2f;
        tipX = MathHelper.clamp(tipX, startX + 4f, startX + WINDOW_WIDTH - w - 4f);
        tipY = MathHelper.clamp(tipY, startY + 4f, startY + WINDOW_HEIGHT - h - 4f);

        float tipAlpha = Math.max(alpha, 0.001f);
        int fonColor = ColorUtil.setAlpha(guiCa("fonColor", new Color(13, 13, 13).getRGB()), 245);
        int border = RenderUtil.applyOpacity(guiCa("scrollColor", new Color(55, 55, 55, 125).getRGB()), tipAlpha);
        int text = RenderUtil.applyOpacity(guiTextPrimary(), tipAlpha);

        RenderUtil.drawBlurredRoundedRectangle(ctx.getMatrices(), tipX, tipY, w, h, 2.5f, fonColor, tipAlpha);
        RenderUtil.drawRoundedBorder(ctx.getMatrices(), tipX, tipY, w, h, 2.5f, -0.45f, border);
        FontUtils.roboto[12].drawLeftAligned(ctx.getMatrices(), keyName, tipX + padX, tipY + padY, text);
    }

    private void cancelAllBindingStates() {
        moduleBindTarget = null;
        moduleBindPreviousKey = 0;
        for (Tab tab : Tab.values()) {
            if (tab.typeList == null) continue;
            for (Module mod : Manager.FUNCTION_MANAGER.getFunctions(tab.typeList)) {
                for (Setting s : mod.getSettings()) {
                    if (s instanceof BindSetting bs) bs.setBinding(false);
                    if (s instanceof BindBooleanSetting bbs) bbs.setListeningForBind(false);
                }
            }
        }
    }

    private void beginModuleBinding(Module module) {
        cancelAllBindingStates();
        moduleBindTarget = module;
        moduleBindPreviousKey = module.getBindCode();
    }

    private boolean handleModuleBindMouseWhileBinding(int button) {
        if (moduleBindTarget == null) return false;
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            return true;
        }
        moduleBindTarget.setBindCode(-(button + 2));
        moduleBindTarget = null;
        moduleBindPreviousKey = 0;
        return true;
    }

    private int moduleCardHeaderHeight(Module f) {
        if (f == moduleBindTarget) {
            List<String> lines = FontUtils.roboto[13].splitTextToLines(MODULE_BIND_HINT, MODULE_DESC_MAX_LINE_WIDTH);
            if (lines.isEmpty()) {
                return MODULE_HEADER_BASE;
            }
            return MODULE_HEADER_BASE + lines.size() * MODULE_DESC_LINE_STEP;
        }
        if (f.desc == null || f.desc.isEmpty()) {
            return MODULE_HEADER_BASE;
        }
        List<String> lines = FontUtils.roboto[13].splitTextToLines(f.desc, MODULE_DESC_MAX_LINE_WIDTH);
        if (lines.isEmpty()) {
            return MODULE_HEADER_BASE;
        }
        return MODULE_HEADER_BASE + lines.size() * MODULE_DESC_LINE_STEP;
    }

    private void renderToggle(DrawContext ctx, int x, int y, boolean state, float tabAlpha, Object id) {
        Animation anim = toggleAnims.computeIfAbsent(id, k -> new Animation(150, state ? 1f : 0f, Easing.SMOOTH_STEP));
        float progress = anim.update(state ? 1f : 0f);

        int w = 16;
        int h = 10;

        int offColor = new Color(40, 40, 40).getRGB();
        int onColor = guiCa("yesColor", getAccentRgb());
        int currentColor = ColorUtil.interpolateColor(offColor, onColor, progress);

        RenderUtil.drawRoundedRect(ctx.getMatrices(), x, y, w, h, 4f, RenderUtil.applyOpacity(ao(currentColor), tabAlpha));

        int offCircleColor = new Color(170, 170, 170).getRGB();
        int onCircleColor = -1;
        int currentCircleColor = ColorUtil.interpolateColor(offCircleColor, onCircleColor, progress);

        float circleX = x + 4.5f + (7f * progress);
        RenderUtil.drawCircle(ctx.getMatrices(), circleX, y + 5f, 6.5f, RenderUtil.applyOpacity(ao(currentCircleColor), tabAlpha));
    }

    private void renderSetting(DrawContext ctx, int x, int y, int w, Setting s, int mouseX, int mouseY, float tabAlpha) {
        if (s instanceof ru.method.celebression.integration.ui.bridge.SettingAdapters.ActionSetting action) {
            FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), action.getName(), x + 0.5f, y + 1, settingEnv.textPrimary());
            return;
        }

        if (s instanceof BooleanSetting bs) {
            SETTING_BOOLEAN.render(ctx, bs, x, y, w, settingEnv);
        } else if (s instanceof BindBooleanSetting bbs) {
            SETTING_BIND_BOOLEAN.render(ctx, bbs, x, y, w, settingEnv);
        } else if (s instanceof SliderSetting ss) {
            SETTING_SLIDER.render(ctx, ss, x, y, w, settingEnv);
        } else if (s instanceof ModeSetting ms) {
            SETTING_MODE.render(ctx, ms, x, y, w, settingEnv);
        } else if (s instanceof MultiSetting multi) {
            SETTING_MULTI.render(ctx, multi, x, y, w, settingEnv);
        } else if (s instanceof BindSetting bs) {
            SETTING_BIND.render(ctx, bs, x, y, w, settingEnv);
        } else if (s instanceof TextSetting ts) {
            SETTING_TEXT.render(ctx, ts, x, y, w, settingEnv);
        } else if (s instanceof ColorSetting cs) {
            SETTING_COLOR.render(ctx, cs, x, y, w, settingEnv);
        }
    }

    private int getAccentRgb() {
        if (Manager.STYLE_MANAGER != null) {
            return Manager.STYLE_MANAGER.getFirstColor();
        }
        return -1;
    }

    private int getSettingHeight(Setting s, int width) {
        if (s instanceof BooleanSetting) return SETTING_BOOLEAN.getHeight();
        if (s instanceof BindBooleanSetting) return SETTING_BIND_BOOLEAN.getHeight();
        if (s instanceof SliderSetting ss) return SliderSettingRenderer.getHeight(ss);
        if (s instanceof ModeSetting ms) return ModeSettingRenderer.getHeight(ms, width);
        if (s instanceof MultiSetting multi) return MultiSettingRenderer.getHeight(multi, width);
        if (s instanceof BindSetting) return SETTING_BIND.getHeight();
        if (s instanceof TextSetting) return SETTING_TEXT.getHeight();
        if (s instanceof ColorSetting cs) return ColorSettingRenderer.getHeight(cs);
        return 14;
    }

    private void clickSetting(Setting s, int button, double gx, double gy, int x, int y, int w) {
        if (s instanceof ru.method.celebression.integration.ui.bridge.SettingAdapters.ActionSetting action) {
            if (button == 0) action.run();
            return;
        }

        if (s instanceof BooleanSetting bs) {
            SETTING_BOOLEAN.mouseClicked(bs, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof BindBooleanSetting bbs) {
            SETTING_BIND_BOOLEAN.mouseClicked(bbs, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof SliderSetting ss) {
            SETTING_SLIDER.mouseClicked(ss, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof ModeSetting ms) {
            SETTING_MODE.mouseClicked(ms, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof MultiSetting multi) {
            SETTING_MULTI.mouseClicked(multi, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof BindSetting bs) {
            SETTING_BIND.mouseClicked(bs, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof TextSetting ts) {
            SETTING_TEXT.mouseClicked(ts, button, gx, gy, x, y, w, settingEnv);
        } else if (s instanceof ColorSetting cs) {
            if (SETTING_COLOR.mouseClicked(cs, button, gx, gy, x, y, w, settingEnv)) {
                draggedColorSetting = cs;
                draggedColorX = x;
                draggedColorY = y;
                draggedColorW = w;
            }
        }
    }

    private int moduleColumnInnerWidth() {
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;
        return contentW / 2 - 16;
    }

    private int computeSettingsHeight(Module f, int settingsInnerWidth) {
        int h = 0;
        for (Setting s : f.getSettings()) {
            if (s.isVisible()) h += getSettingHeight(s, settingsInnerWidth) + 5;
        }
        return h;
    }

    private boolean hasExpandableSettings(Module f) {
        for (Setting s : f.getSettings()) {
            if (s.isVisible()) return true;
        }
        return false;
    }

    private void collapseAllModuleCards() {
        for (Module fn : Manager.FUNCTION_MANAGER.getFunctions()) {
            fn.expanded = false;
        }
    }

    private float updateExpandAnimation(Module f) {
        Animation anim = expandAnims.computeIfAbsent(f, k -> new Animation(250, f.expanded ? 1f : 0f, Easing.SMOOTH_STEP));
        return anim.update(f.expanded ? 1f : 0f);
    }

    private int[] moduleCardHeightsForList(List<Module> funcs) {
        int n = funcs.size();
        int[] h = new int[n];
        int innerW = moduleColumnInnerWidth();
        for (int i = 0; i < n; i++) {
            Module f = funcs.get(i);
            Animation anim = expandAnims.get(f);
            float prog = anim != null ? anim.getValue() : (f.expanded ? 1f : 0f);
            h[i] = moduleCardHeaderHeight(f) + (int) (computeSettingsHeight(f, innerW) * MathHelper.clamp(prog, 0f, 1f));
        }
        return h;
    }

    private int[] moduleCardCollapsedHeightsForColumnLayout(List<Module> funcs) {
        int n = funcs.size();
        int[] h = new int[n];
        for (int i = 0; i < n; i++) {
            h[i] = moduleCardHeaderHeight(funcs.get(i));
        }
        return h;
    }

    private int[] packBalancedTwoColumns(int[] cardHeights) {
        int n = cardHeights.length;
        int[] col = new int[n];
        float accL = 0f;
        float accR = 0f;
        for (int i = 0; i < n; i++) {
            if (accL <= accR) {
                col[i] = 0;
                accL += cardHeights[i] + MODULE_ROW_GAP_RENDER;
            } else {
                col[i] = 1;
                accR += cardHeights[i] + MODULE_ROW_GAP_RENDER;
            }
        }
        return col;
    }

    private float approachScrollOffset(float from, float to, float maxScroll) {
        float d = to - from;
        if (Math.abs(d) < 0.35f) {
            return MathHelper.clamp(to, 0f, maxScroll);
        }
        MinecraftClient c = MinecraftClient.getInstance();
        int fps = c.getCurrentFps();
        float dt = 1f / (float) MathHelper.clamp(fps, 1, 240);
        double z = (double) MODULE_SCROLL_EXP * (double) dt;
        float t = 1f - (float) Math.exp(-z);
        t = MathHelper.clamp(t, 0.04f, 0.5f);
        from = from + d * t;
        if (Math.abs(to - from) < 0.3f) {
            from = to;
        }
        return MathHelper.clamp(from, 0f, maxScroll);
    }

    private boolean handleBindSettingMouseWhileBinding(int button) {
        for (Tab tab : Tab.values()) {
            if (tab.typeList == null) continue;
            for (Module f : Manager.FUNCTION_MANAGER.getFunctions(tab.typeList)) {
                for (Setting s : f.getSettings()) {
                    if (s instanceof BindSetting bs && bs.isBinding()) {
                        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                            return true;
                        }
                        bs.setKey(-(button + 2));
                        bs.setBinding(false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double[] g = new double[2];
        screenToGui(mouseX, mouseY, g);
        double gx = g[0];
        double gy = g[1];

        if (handleModuleBindMouseWhileBinding(button)) {
            return true;
        }

        if (handleBindSettingMouseWhileBinding(button)) {
            return true;
        }

        int startX = (this.width - WINDOW_WIDTH) / 2;
        int startY = (this.height - WINDOW_HEIGHT) / 2;

        int sidebarStartX = startX - 20;
        int tabLeft = sidebarStartX + 18;
        int tabW = SIDEBAR_WIDTH - 20;
        int tabRowH = 18;

        int currentY = startY + TOPBAR_HEIGHT - 14;
        for (Tab tab : Tab.values()) {
            if (tab.group.equals("System")) {
                if (gx >= tabLeft && gx <= tabLeft + tabW && gy >= currentY && gy <= currentY + tabRowH) {
                    if (currentTab != tab) {
                        collapseAllModuleCards();
                        previousTab = currentTab;
                        currentTab = tab;
                        tabSwitchAnimation.reset(0.0f);
                        tabSwitchAnimation.animateTo(1.0f);
                    }
                    return true;
                }
                currentY += 17;
            }
        }
        currentY += 12;
        for (String group : new String[]{"Combat", "Visual", "Other"}) {
            currentY += 11;
            for (Tab tab : Tab.values()) {
                if (tab.group.equals(group)) {
                if (gx >= tabLeft && gx <= tabLeft + tabW && gy >= currentY && gy <= currentY + tabRowH) {
                    if (currentTab != tab) {
                        collapseAllModuleCards();
                        previousTab = currentTab;
                        currentTab = tab;
                        tabSwitchAnimation.reset(0.0f);
                        tabSwitchAnimation.animateTo(1.0f);
                    }
                    return true;
                }
                    currentY += 17;
                }
            }
            currentY += 4;
        }

        int contentX = startX + SIDEBAR_WIDTH + 5;
        int contentY = startY + TOPBAR_HEIGHT - 8;
        int contentW = WINDOW_WIDTH - SIDEBAR_WIDTH - 15;

        if (currentTab != null && currentTab.typeList != null) {
            syncModuleListScrollBounds();
            if (pointInContentClip(gx, gy, startX, startY)) {
            List<Module> funcs = Manager.FUNCTION_MANAGER.getFunctions(currentTab.typeList);
            if (!searchState.text.isEmpty()) {
                funcs = funcs.stream().filter(f -> f.name.toLowerCase().contains(searchState.text.toLowerCase())).toList();
            }
            int columnWidth = contentW / 2;
            int colLeftX = contentX;
            int colRightX = contentX + columnWidth + 3;
            float offset = scrollOffsets.getOrDefault(currentTab, 0f);
            float y1 = contentY - offset;
            float y2 = contentY - offset;
            int[] cardHeights = moduleCardHeightsForList(funcs);
            int[] col = packBalancedTwoColumns(moduleCardCollapsedHeightsForColumnLayout(funcs));

            for (int i = 0; i < funcs.size(); i++) {
                Module f = funcs.get(i);
                int headerH = moduleCardHeaderHeight(f);
                int cardH = cardHeights[i];

                boolean isLeft = col[i] == 0;
                int cx = isLeft ? colLeftX : colRightX;
                float cy = isLeft ? y1 : y2;

                if (gx < cx || gx > cx + columnWidth || gy < cy || gy > cy + cardH) {
                    if (isLeft) {
                        y1 += cardH + MODULE_ROW_GAP_RENDER;
                    } else {
                        y2 += cardH + MODULE_ROW_GAP_RENDER;
                    }
                    continue;
                }

                float moduleToggleX = cx + columnWidth - 42;
                float moduleToggleY = cy + 9;
                boolean expandable = hasExpandableSettings(f);
                float moduleToggleDrawX = expandable ? moduleToggleX : moduleToggleX + 22;
                boolean onModuleStateToggle = gx >= moduleToggleDrawX && gx <= moduleToggleDrawX + 16 && gy >= moduleToggleY && gy <= moduleToggleY + 10;

                if (onModuleStateToggle) {
                    if (button == 0) {
                        f.toggle();
                    }
                    return true;
                }

                if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && gy <= cy + headerH) {
                    float expandBtnX = moduleToggleX + 22;
                    boolean onExpandBtn = expandable
                            && gx >= expandBtnX && gx <= expandBtnX + 13
                            && gy >= moduleToggleY && gy <= moduleToggleY + 10.5;
                    if (!onExpandBtn) {
                        beginModuleBinding(f);
                        return true;
                    }
                }

                if (gy > cy + headerH && gy <= cy + cardH) {
                    float curSY = cy + headerH;
                    final int settingX = cx + 8;
                    final int settingW = columnWidth - 16;
                    for (Setting s : f.getSettings()) {
                        if (!s.isVisible()) continue;
                        int sH = getSettingHeight(s, columnWidth - 16);
                        if (gy >= curSY && gy <= curSY + (double) sH) {
                            int settingY = (int) curSY;
                            clickSetting(s, button, gx, gy, settingX, settingY, settingW);
                            return true;
                        }
                        curSY += sH + 5;
                    }
                }

                if (expandable && button == 1) {
                    if (f.expanded) {
                        f.expanded = false;
                    } else {
                        collapseAllModuleCards();
                        f.expanded = true;
                    }
                }
                return true;
            }
            }
        } else if (currentTab == Tab.SETTINGS) {
            syncModuleListScrollBounds();
            if (clickSettingsColors(gx, gy, startX, startY, button)) {
                return true;
            }
        }

        int searchW = 130;
        int searchH = 18;
        int searchX = startX + (WINDOW_WIDTH - searchW) / 2;
        int searchY = startY + 7;
        computeLangBarFrame(startX, startY, (int) gx, (int) gy, false);
        boolean onLangPill = gx >= langBarPillX && gx <= langBarPillX + langBarPillW
                && gy >= langBarSearchY && gy <= langBarSearchY + langBarSearchH;
        boolean onLangFlag = gx >= langBarFlagX && gx <= langBarFlagX + LANG_BAR_FLAG_W
                && gy >= langBarSearchY && gy <= langBarSearchY + langBarSearchH;
        if ((button == 0 || button == 1) && onLangPill) {
            langSwapWentForward = button == 0;
            MenuLanguageState.Language current = MenuLanguageState.selected();
            langSwapPrevious = current;
            MenuLanguageState.select(button == 0 ? current.nextSelectable() : current.previousSelectable());
            MenuConfigBridge.saveAsync();
            langSwapAnim.reset(0f);
            langSwapAnim.animateTo(1f);
            searchState.focused = false;
            return true;
        }
        if (button == 0 && onLangFlag) {
            searchState.focused = false;
            mc.setScreen(new LanguageSelectUI(this));
            return true;
        }

        searchState.focused = gx >= searchX && gx <= searchX + searchW && gy >= searchY && gy <= searchY + searchH;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && draggedSlider != null) {
            double[] g = new double[2];
            screenToGui(mouseX, mouseY, g);
            SliderSettingRenderer.applyValueFromGuiX(draggedSlider, g[0], draggedSliderX, draggedSliderW);
            return true;
        }
        if (button == 0 && draggedColorSetting != null) {
            double[] g = new double[2];
            screenToGui(mouseX, mouseY, g);
            ColorSettingRenderer.applyDrag(draggedColorSetting, g[0], g[1], draggedColorX, draggedColorY, draggedColorW);
            return true;
        }
        if (button == 0 && currentTab == Tab.SETTINGS && anySettingsColorDragging()) {
            double[] g = new double[2];
            screenToGui(mouseX, mouseY, g);
            int startX = (this.width - WINDOW_WIDTH) / 2;
            int startY = (this.height - WINDOW_HEIGHT) / 2;
            dragSettingsColors((int) Math.round(g[0]), (int) Math.round(g[1]), startX, startY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && draggedSlider != null) {
            draggedSlider.dragging = false;
            draggedSlider = null;
            return true;
        }
        if (button == 0 && draggedColorSetting != null) {
            ColorSettingRenderer.releaseAll(draggedColorSetting);
            draggedColorSetting = null;
            return true;
        }
        if (button == 0) {
            GuiColorPicker.releaseAll();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (currentTab == null) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }
        if (currentTab.typeList != null || currentTab == Tab.SETTINGS) {
            syncModuleListScrollBounds();
            float max = moduleListMaxScroll;
            float cur = scrollOffsets.getOrDefault(currentTab, scrollTargets.getOrDefault(currentTab, 0f));
            float next = cur - (float) scrollY * 30f;
            float snapped = Math.round(MathHelper.clamp(next, 0f, max));
            scrollTargets.put(currentTab, snapped);
            scrollOffsets.put(currentTab, snapped);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean charTyped(char c, int keyCode) {
        if (searchState.focused) {
            searchState.text += c;
            searchState.cursorPosition++;
            return true;
        }
        for (Tab tab : Tab.values()) {
            if (tab.typeList != null) {
                for (Module f : Manager.FUNCTION_MANAGER.getFunctions(tab.typeList)) {
                    for (Setting s : f.getSettings()) {
                        if (s instanceof TextSetting ts && ts.isFocused()) {
                            ts.setValue(ts.getValue() + c);
                            return true;
                        }
                    }
                }
            }
        }
        return super.charTyped(c, keyCode);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (isClose) return true;

        if (moduleBindTarget != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                moduleBindTarget.setBindCode(moduleBindPreviousKey);
            } else if (keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                moduleBindTarget.setBindCode(0);
            } else {
                moduleBindTarget.setBindCode(keyCode);
            }
            moduleBindTarget = null;
            moduleBindPreviousKey = 0;
            return true;
        }

        for (Tab tab : Tab.values()) {
            if (tab.typeList != null) {
                for (Module f : Manager.FUNCTION_MANAGER.getFunctions(tab.typeList)) {
                    for (Setting s : f.getSettings()) {
                        if (s instanceof BindBooleanSetting bbs && bbs.isListeningForBind()) {
                            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) bbs.setKey(0);
                            else bbs.setKey(keyCode);
                            bbs.setListeningForBind(false);
                            return true;
                        }
                        if (s instanceof BindSetting bs && bs.isBinding()) {
                            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                                bs.setKey(-1);
                            } else {
                                bs.setKey(keyCode);
                            }
                            bs.setBinding(false);
                            return true;
                        }
                        if (s instanceof TextSetting ts && ts.isFocused()) {
                            if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !ts.getValue().isEmpty()) {
                                ts.setValue(ts.getValue().substring(0, ts.getValue().length() - 1));
                            }
                            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) ts.setFocused(false);
                            return true;
                        }
                    }
                }
            }
        }

        if (searchState.focused) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !searchState.text.isEmpty()) {
                searchState.text = searchState.text.substring(0, searchState.text.length() - 1);
                searchState.cursorPosition--;
            }
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) searchState.focused = false;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == Manager.FUNCTION_MANAGER.clickGUI.getBindCode()) {
            if (openTimer.hasTimeElapsed(200)) {
                close();
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        if (isClose) return;
        isClose = true;
        AudioUtil.playSound("close.wav", 0.4f);
        openAnimation.setDuration(170);
        openAnimation.animateTo(0);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void removed() {
        moduleBindTarget = null;
        moduleBindPreviousKey = 0;
        persistedTab = currentTab;
        for (Tab t : Tab.values()) {
            int maxPx = computeModuleListMaxScrollPx(t);
            float v = Math.round(MathHelper.clamp(scrollOffsets.getOrDefault(t, scrollTargets.getOrDefault(t, 0f)), 0f, (float) maxPx));
            scrollOffsets.put(t, v);
            scrollTargets.put(t, v);
            persistedScrollByTab.put(t, v);
        }
        Manager.CONFIG_MANAGER.saveConfiguration("autocfg");
        var visuals = ru.method.celebression.Celebression.getInstance();
        if (visuals != null && visuals.getModuleManager() != null) {
            var ui = visuals.getModuleManager().getModule(ru.method.celebression.modules.impl.client.UI.class);
            if (ui != null) ui.syncClosedState();
        }
        super.removed();
    }

    public boolean celebression$hasBinding() {
        if (moduleBindTarget != null) return true;
        for (Module module : Manager.FUNCTION_MANAGER.getFunctions()) {
            for (Setting setting : module.getSettings()) {
                if (setting instanceof BindSetting bind && bind.isBinding()) return true;
                if (setting instanceof BindBooleanSetting bind && bind.isListeningForBind()) return true;
            }
        }
        return false;
    }
}