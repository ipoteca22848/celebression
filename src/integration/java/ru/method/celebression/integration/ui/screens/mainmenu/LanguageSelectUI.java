package ru.method.celebression.integration.ui.screens.mainmenu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.GameWindowPort;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.ColorValue;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuDrawBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.render.font.Fonts;
import ru.method.celebression.integration.ui.screens.mainmenu.render.PrimitiveUseCase;
import ru.method.celebression.integration.ui.screens.mainmenu.render.TextureUseCase;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftClientProvider;
import org.lwjgl.glfw.GLFW;
import org.jetbrains.annotations.Nullable;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuScissor;
import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.manager.fontManager.RenderFonts;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;
import ru.method.celebression.integration.ui.util.math.geometry.Radii;
import ru.method.celebression.integration.ui.util.render.RenderUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class LanguageSelectUI extends Screen
{
    private static final float PADDING = 28.0f;
    private static final float TOP_BAR_HEIGHT = 34.0f;
    private static final float QUIT_CHIP_WIDTH = 72.0f;
    private static final float QUIT_CHIP_HEIGHT = 26.0f;
    private static final float QUIT_CHIP_MARGIN_RIGHT = 8.0f;
    private static final float QUIT_CHIP_GAP = 8.0f;
    private static final float QUIT_CHIP_PADDING_X = 10.0f;
    private static final float QUIT_ICON_SIZE = 12.0f;
    private static final float QUIT_ICON_GAP = 6.0f;
    private static final float TOP_BAR_TITLE_SIZE = 13.0f;
    private static final float TOP_BAR_LOGO_SIZE = 22.0f;
    private static final float CHIP_STROKE_THICKNESS = 0.65f;
    private static final float CONTENT_BOTTOM_PADDING = 28.0f;
    private static final float SEARCH_WIDTH = 521.0f;
    private static final float SEARCH_HEIGHT = 41.0f;
    private static final float SEARCH_PADDING_X = 14.0f;
    private static final float SEARCH_ICON_SIZE = 12.0f;
    private static final float SEARCH_ICON_MARGIN = 14.0f;
    private static final float SEARCH_STROKE_THICKNESS = 0.65f;
    private static final float SEARCH_FONT_SIZE = 13.5f;
    private static final float SECTION_WIDTH = 968.0f;
    private static final float SECTION_HEADER_HEIGHT = 44.0f;
    private static final float SECTION_GAP = 10.0f;
    private static final float SECTION_ITEM_TOP_GAP = 14.0f;
    private static final float SECTION_EMPTY_HEIGHT = 34.0f;
    private static final float ITEM_WIDTH = 220.0f;
    private static final float ITEM_HEIGHT = 64.0f;
    private static final float ITEM_GAP_X = 20.0f;
    private static final float ITEM_GAP_Y = 16.0f;
    private static final float FLAG_HALO_SIZE = 50.0f;
    private static final float FLAG_SIZE = 40.0f;
    private static final float SECTION_TITLE_GAP = 22.0f;
    private static final float HERO_TITLE_SIZE = 31.0f;
    private static final float HERO_HINT_SIZE = 13.0f;
    private static final float HERO_TITLE_TO_HINT_GAP = 8.0f;
    private static final float HERO_HINT_TO_SEARCH_GAP = 18.0f;
    private static final float LANGUAGE_CONTENT_Y_OFFSET = -80.0f;
    private static final int MAX_QUERY_LENGTH = 40;
    private static final long INTRO_MS = 620L;
    private static final float ANIMATION_SPEED = 14.0f;
    private static final float EXPAND_ANIMATION_SPEED = 11.0f;
    private static final float SCROLL_ANIMATION_SPEED = 16.0f;

    private static final Radii SEARCH_RADII = Radii.of(12.0f);
    private static final Radii SOFT_RADII = Radii.of(14.0f);
    private static final Radii FLAG_RADII = Radii.zero();
    private static final float FLAG_STRIPE_OVERLAP = 3.0f;
    private static final float FLAG_EDGE_SMOOTHNESS = 1.0f;
    private static final float FLAG_STRIPE_SMOOTHNESS = 0.0f;
    private static final float FLAG_OUTLINE_THICKNESS = -0.4f;
    private static final float FLAG_OUTLINE_OUTSET = 1f;
    private static final ColorValue FLAG_OUTLINE_COLOR = ColorValue.rgb(48, 48, 52);

    private static final ColorValue MENU_BACKGROUND_DARK = ColorValue.rgb(15, 15, 17);
    private static final ColorValue MENU_BACKGROUND_LIGHT = ColorValue.rgb(245, 245, 245);
    private static final ColorValue MENU_OUTLINE_LIGHT = ColorValue.rgb(224, 225, 232);
    private static final ColorValue MENU_TEXT_LIGHT = ColorValue.rgb(33, 35, 41);
    private static final ColorValue MENU_CONTROL_FILL_LIGHT = ColorValue.rgb(250, 250, 250);
    private static final ColorValue MENU_CHIP_KNOB_LIGHT = ColorValue.rgb(236, 238, 242);
    private static final ColorValue MENU_QUIT_FILL_LIGHT = ColorValue.rgb(255, 239, 243);
    private static final ColorValue MENU_QUIT_TEXT_LIGHT = ColorValue.rgb(238, 98, 110);
    private static final ColorValue CONTROL_HOVER_DARK = ColorValue.rgb(30, 30, 32);
    private static final ColorValue CONTROL_HOVER_LIGHT = ColorValue.rgb(255, 255, 255);
    private static final ColorValue STROKE_HOVER_DARK = ColorValue.rgb(52, 52, 58);
    private static final ColorValue STROKE_HOVER_LIGHT = ColorValue.rgb(212, 215, 224);
    private static final ColorValue ALT_CONTROL_FILL = ColorValue.rgb(23, 23, 24);
    private static final ColorValue ALT_CONTROL_STROKE = ColorValue.rgb(34, 33, 36);
    private static final ColorValue ALT_TEXT_MUTED = ColorValue.rgb(124, 125, 134);
    private static final ColorValue ALT_TEXT_MAIN = ColorValue.rgb(224, 225, 232);
    private static final ColorValue FOOTER_HIGHLIGHT = ColorValue.rgb(224, 225, 232);
    private static final ColorValue QUIT_FILL_HOVER = ColorValue.rgba(255, 82, 96, 240);
    private static final ColorValue LIGHT_SURFACE_TINT = ColorValue.rgb(226, 228, 234);
    private static final ColorValue LIGHT_SURFACE_TINT_STRONG = ColorValue.rgb(214, 217, 224);
    private static final ColorValue LIGHT_FLAG_HALO = ColorValue.rgb(236, 237, 242);
    private static final ColorValue LIGHT_FLAG_OUTLINE = ColorValue.rgb(184, 187, 194);

    private static final List<RegionSection> DISPLAYED_REGIONS = List.of(
            RegionSection.EUROPE,
            RegionSection.ASIA
    );

    private static final List<LanguageOption> LANGUAGE_OPTIONS = List.of(
            new LanguageOption(RegionSection.ASIA, MenuLanguageState.Language.INDONESIAN_INDONESIA, FlagStyle.INDONESIA, "Indonesia", "indonesia", "indonesian", "\u0438\u043d\u0434\u043e\u043d\u0435\u0437\u0438\u044f", "\u0456\u043d\u0434\u043e\u043d\u0435\u0437\u0456\u044f"),
            new LanguageOption(RegionSection.ASIA, MenuLanguageState.Language.KAZAKH_KAZAKHSTAN, FlagStyle.KAZAKHSTAN, "Kazakhstan", "kazakhstan", "kazakh", "\u043a\u0430\u0437\u0430\u0445\u0441\u0442\u0430\u043d", "\u043a\u0430\u0437\u0430\u0445\u0441\u0442\u0430\u043d"),
            new LanguageOption(RegionSection.ASIA, MenuLanguageState.Language.ENGLISH_SINGAPORE, FlagStyle.SINGAPORE, "Singapore", "singapore", "english", "\u0441\u0438\u043d\u0433\u0430\u043f\u0443\u0440", "\u0441\u0456\u043d\u0433\u0430\u043f\u0443\u0440"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.ENGLISH_UNITED_KINGDOM, FlagStyle.UNITED_KINGDOM, "United Kingdom", "england", "britain", "uk", "english"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.FRENCH_FRANCE, FlagStyle.FRANCE, "France", "france", "french", "\u0444\u0440\u0430\u043d\u0446\u0438\u044f", "\u0444\u0440\u0430\u043d\u0446\u0456\u044f"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.GERMAN_GERMANY, FlagStyle.GERMANY, "Germany", "germany", "german", "\u0433\u0435\u0440\u043c\u0430\u043d\u0438\u044f", "\u043d\u0456\u043c\u0435\u0447\u0447\u0438\u043d\u0430"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.ITALIAN_ITALY, FlagStyle.ITALY, "Italy", "italy", "italian", "\u0438\u0442\u0430\u043b\u0438\u044f", "\u0456\u0442\u0430\u043b\u0456\u044f"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.DUTCH_NETHERLANDS, FlagStyle.NETHERLANDS, "Netherlands", "netherlands", "dutch", "\u043d\u0438\u0434\u0435\u0440\u043b\u0430\u043d\u0434\u044b", "\u043d\u0456\u0434\u0435\u0440\u043b\u0430\u043d\u0434\u0438"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.POLISH_POLAND, FlagStyle.POLAND, "Poland", "poland", "polish", "polska", "polski"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.PORTUGUESE_PORTUGAL, FlagStyle.PORTUGAL, "Portugal", "portugal", "portuguese", "\u043f\u043e\u0440\u0442\u0443\u0433\u0430\u043b\u0438\u044f", "\u043f\u043e\u0440\u0442\u0443\u0433\u0430\u043b\u0456\u044f"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.SPANISH_SPAIN, FlagStyle.SPAIN, "Spain", "spain", "spanish", "\u0438\u0441\u043f\u0430\u043d\u0438\u044f", "\u0456\u0441\u043f\u0430\u043d\u0456\u044f"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.UKRAINIAN_UKRAINE, FlagStyle.UKRAINE, "Ukraine", "ukraine", "ukrainian", "\u0443\u043a\u0440\u0430\u0438\u043d\u0430", "\u0443\u043a\u0440\u0430\u0457\u043d\u0430"),
            new LanguageOption(RegionSection.EUROPE, MenuLanguageState.Language.RUSSIAN_RUSSIA, FlagStyle.RUSSIA, "Russia", "russia", "russian", "\u0440\u043e\u0441\u0441\u0438\u044f", "\u0440\u043e\u0441\u0456\u044f")
    );

    private final Map<MenuLanguageState.Language, Float> optionHoverProgress = new EnumMap<>(MenuLanguageState.Language.class);
    private final Map<MenuLanguageState.Language, Bound> optionBounds = new EnumMap<>(MenuLanguageState.Language.class);
    private final Map<RegionSection, Float> sectionHoverProgress = new EnumMap<>(RegionSection.class);
    private final Map<RegionSection, Float> sectionExpandProgress = new EnumMap<>(RegionSection.class);
    private final Map<RegionSection, Bound> sectionBounds = new EnumMap<>(RegionSection.class);
    private final Map<RegionSection, Boolean> regionExpanded = new EnumMap<>(RegionSection.class);

    @Nullable
    private final Screen returnScreen;

    private boolean lightThemeEnabled;
    private boolean searchFocused;
    private String searchInput = "";
    private int searchCursorIndex;
    private long openedAt;
    private long lastAnimationFrameAt;
    private float switchVisualProgress;
    private float settingsHoverProgress;
    private float quitHoverProgress;
    private float searchHoverProgress;
    private float contentScroll;
    private float contentScrollTarget;
    private float contentMaxScroll;

    private Bound settingsChipBound;
    private Bound quitChipBound;
    private Bound searchBound;
    private Bound contentViewportBound;
    private List<SectionLayout> sectionLayouts = List.of();

    public LanguageSelectUI()
    {
        this(null);
    }

    public LanguageSelectUI(@Nullable Screen returnScreen)
    {
        super(Text.literal("Language Select"));
        this.returnScreen = returnScreen;
        long now = System.currentTimeMillis();
        openedAt = now;
        lastAnimationFrameAt = now;
        lightThemeEnabled = MenuUI.isSharedLightThemeEnabled();
        switchVisualProgress = lightThemeEnabled ? 1.0f : 0.0f;

        for (MenuLanguageState.Language language : MenuLanguageState.Language.values()) {
            optionHoverProgress.put(language, 0.0f);
        }
        for (RegionSection region : DISPLAYED_REGIONS)
        {
            sectionHoverProgress.put(region, 0.0f);
            regionExpanded.put(region, true);
            sectionExpandProgress.put(region, 1.0f);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        Bound root = rootBound();
        searchBound = searchLayout(root);
        contentViewportBound = contentViewportLayout(root);
        updateAnimations(root, mouseX, mouseY);
        sectionLayouts = buildSectionLayouts(root);
        if (clampContentScrollState()) {
            sectionLayouts = buildSectionLayouts(root);
        }

        MenuDrawBridge.begin(context);
        try {
            renderBackgroundFill(root);
            renderTopBar(root);
            renderContent(root);
        } finally {
            MenuDrawBridge.end();
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta)
    {
        MenuDrawBridge.begin(context);
        try {
            renderBackgroundFill(rootBound());
        } finally {
            MenuDrawBridge.end();
        }
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return returnScreen != null;
    }

    @Override
    public void close()
    {
        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        if (returnScreen != null && client != null) {
            client.setScreen(returnScreen);
            return;
        }
        super.close();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button != GLFW.GLFW_MOUSE_BUTTON_1) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        float x = toFramebufferX((float) mouseX);
        float y = toFramebufferY((float) mouseY);

        if (settingsChipBound != null && settingsChipBound.contains(x, y))
        {
            lightThemeEnabled = !lightThemeEnabled;
            MenuUI.setSharedLightThemeEnabled(lightThemeEnabled);
            return true;
        }

        if (quitChipBound != null && quitChipBound.contains(x, y))
        {
            MinecraftClient client = MinecraftClientProvider.getMinecraft();
            if (client != null) {
                client.scheduleStop();
            }
            return true;
        }

        if (searchBound != null && searchBound.contains(x, y))
        {
            searchFocused = true;
            searchCursorIndex = searchInput.length();
            return true;
        }
        searchFocused = false;

        String query = normalizeQuery(searchInput);
        if (contentViewportBound != null && !contentViewportBound.contains(x, y)) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        if (query.isEmpty())
        {
            for (SectionLayout section : sectionLayouts)
            {
                if (section.headerBound().contains(x, y))
                {
                    regionExpanded.put(section.region(), !regionExpanded.getOrDefault(section.region(), false));
                    return true;
                }
            }
        }

        for (SectionLayout section : sectionLayouts)
        {
            for (ItemLayout item : section.items())
            {
                if (item.bound().contains(x, y))
                {
                    openMenuForLanguage(item.option().language());
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (!searchFocused) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            searchFocused = false;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER)
        {
            openFirstMatch();
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE)
        {
            if (searchCursorIndex > 0 && !searchInput.isEmpty())
            {
                searchInput = searchInput.substring(0, searchCursorIndex - 1) + searchInput.substring(searchCursorIndex);
                searchCursorIndex = Math.max(0, searchCursorIndex - 1);
                resetContentScroll();
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_DELETE)
        {
            if (searchCursorIndex < searchInput.length())
            {
                searchInput = searchInput.substring(0, searchCursorIndex) + searchInput.substring(searchCursorIndex + 1);
                resetContentScroll();
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_LEFT)
        {
            searchCursorIndex = Math.max(0, searchCursorIndex - 1);
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_RIGHT)
        {
            searchCursorIndex = Math.min(searchInput.length(), searchCursorIndex + 1);
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_HOME)
        {
            searchCursorIndex = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_END)
        {
            searchCursorIndex = searchInput.length();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers)
    {
        if (!searchFocused) {
            return super.charTyped(chr, modifiers);
        }

        if (searchInput.length() >= MAX_QUERY_LENGTH) {
            return true;
        }

        if (Character.isISOControl(chr)) {
            return true;
        }

        int idx = MathHelper.clamp(searchCursorIndex, 0, searchInput.length());
        searchInput = searchInput.substring(0, idx) + chr + searchInput.substring(idx);
        searchCursorIndex = idx + 1;
        resetContentScroll();
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (contentViewportBound == null) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        float x = toFramebufferX((float) mouseX);
        float y = toFramebufferY((float) mouseY);
        if (!contentViewportBound.contains(x, y)) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        contentScrollTarget = MathHelper.clamp(
                contentScrollTarget - (float) scrollY * (ITEM_HEIGHT * 0.82f),
                0.0f,
                contentMaxScroll
        );
        return true;
    }

    private void renderTopBar(Bound root)
    {
        float logoY = topBarLogoY();
        float textY = topBarTitleY();
        float scaleX = Math.max(0.001f, MenuDrawBridge.scaleX());
        float scaleY = Math.max(0.001f, MenuDrawBridge.scaleY());
        int logoFontIndex = Math.max(1, Math.min(255, Math.round(TOP_BAR_LOGO_SIZE * scaleY / 0.5f)));
        RenderFonts logoFont = FontUtils.celebression[logoFontIndex];
        if (logoFont != null)
        {
            String logoGlyph = "a";
            float logoGlyphW = logoFont.getWidth(logoGlyph) / scaleX;
            float logoGlyphH = logoFont.getHeight() / scaleY;
            float logoX = PADDING + (TOP_BAR_LOGO_SIZE - logoGlyphW) / 2.0f - 10;
            float logoTextY = logoY + (TOP_BAR_LOGO_SIZE - logoGlyphH) / 2.0f;
            logoFont.drawLeftAligned(
                    MenuDrawBridge.matrices(),
                    logoGlyph,
                    MenuDrawBridge.toScreenX(logoX),
                    MenuDrawBridge.toScreenY(logoTextY),
                    logoTintColor().toRGBA()
            );
        }
        Fonts.ROBOTO_FLEX_REGULAR.draw("celebression", PADDING + TOP_BAR_LOGO_SIZE + 8.0f - 10, textY, TOP_BAR_TITLE_SIZE, textMainColor().toRGBA());
        renderSettingsChip(root);
        renderQuitChip(root);
    }

    private void renderContent(Bound root)
    {
        float heroCenterX = root.centerX();
        float titleY = contentTop(root);
        String title = MenuLanguageState.text("language.title");
        String hint = currentSelectionLine();
        float hintY = titleY + Fonts.ROBOTO_FLEX_REGULAR.getHeight(HERO_TITLE_SIZE) + HERO_TITLE_TO_HINT_GAP;

        Fonts.ROBOTO_FLEX_REGULAR.drawCentered(title, heroCenterX, titleY, HERO_TITLE_SIZE, textMainColor().toRGBA());
        Fonts.ROBOTO_FLEX_REGULAR.drawCentered(
                hint,
                heroCenterX,
                hintY,
                HERO_HINT_SIZE,
                withAlpha(textMutedColor(), uiElementAlpha() * 0.95f).toRGBA()
        );

        renderSearchField();

        if (sectionLayouts.isEmpty())
        {
            String emptyTitle = noResultsTitle();
            String emptyHint = noResultsSubtitle();
            float centerY = contentViewportBound == null ? searchBound.bottom() + 58.0f : contentViewportBound.y + 38.0f;

            Fonts.ROBOTO_FLEX_REGULAR.draw(
                    emptyTitle,
                    heroCenterX - Fonts.ROBOTO_FLEX_REGULAR.getWidth(emptyTitle, 18.0f) / 2.0f,
                    centerY,
                    18.0f,
                    textMainColor().toRGBA()
            );
            Fonts.ROBOTO_FLEX_REGULAR.draw(
                    emptyHint,
                    heroCenterX - Fonts.ROBOTO_FLEX_REGULAR.getWidth(emptyHint, 12.0f) / 2.0f,
                    centerY + 24.0f,
                    12.0f,
                    textMutedColor().toRGBA()
            );
            return;
        }

        if (contentViewportBound != null) {
            MenuScissor.enable(contentViewportBound);
        }
        try
        {
            for (SectionLayout section : sectionLayouts)
            {
                renderSectionHeader(section);

                if (section.expandProgress() <= 0.001f) {
                    continue;
                }

                renderSectionBody(section);
            }
        }
        finally
        {
            if (contentViewportBound != null) {
                MenuScissor.disable();
            }
        }

        renderScrollbar();
    }

    private void renderSearchField()
    {
        if (searchBound == null) {
            return;
        }

        float emphasis = Math.max(searchHoverProgress, searchFocused ? 0.85f : 0.0f);

        PrimitiveUseCase.rectangle(
                searchBound,
                SEARCH_RADII,
                searchFieldFillColor(emphasis)
        );
        PrimitiveUseCase.strokeInside(
                searchBound,
                SEARCH_RADII,
                SEARCH_STROKE_THICKNESS,
                searchFieldStrokeColor(emphasis)
        );

        float fontSize = SEARCH_FONT_SIZE;
        float textX = searchBound.x + SEARCH_PADDING_X;
        float textY = searchBound.y + (searchBound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f;
        float iconReserve = SEARCH_ICON_MARGIN + SEARCH_ICON_SIZE + 8.0f;
        float availableWidth = searchBound.width - SEARCH_PADDING_X - iconReserve;
        float caretTop = searchBound.y + 9.0f;
        float caretHeight = searchBound.height - 18.0f;

        if (searchInput.isEmpty())
        {
            String placeholder = searchPlaceholder();
            Fonts.ROBOTO_FLEX_REGULAR.draw(
                    placeholder,
                    textX,
                    textY,
                    fontSize,
                    withAlpha(textMutedColor(), 0.68f).toRGBA()
            );

            if (searchFocused && isCaretVisible())
            {
                PrimitiveUseCase.rectangle(
                        Bound.of(textX, caretTop, 1.2f, caretHeight),
                        Radii.of(999.0f),
                        textMainColor()
                );
            }
        }
        else
        {
            SearchViewport viewport = searchViewport(fontSize, availableWidth);
            Fonts.ROBOTO_FLEX_REGULAR.draw(viewport.text(), textX, textY, fontSize, textMainColor().toRGBA());

            if (searchFocused && isCaretVisible())
            {
                PrimitiveUseCase.rectangle(
                        Bound.of(textX + viewport.caretOffset(), caretTop, 1.2f, caretHeight),
                        Radii.of(999.0f),
                        textMainColor()
                );
            }
        }

        renderSearchIcon(Bound.of(
                searchBound.right() - SEARCH_ICON_MARGIN - SEARCH_ICON_SIZE,
                searchBound.centerY() - SEARCH_ICON_SIZE * 0.5f,
                SEARCH_ICON_SIZE,
                SEARCH_ICON_SIZE
        ));
    }

    private void renderSectionHeader(SectionLayout section)
    {
        Bound header = section.headerBound();
        float hover = sectionHoverProgress.getOrDefault(section.region(), 0.0f);
        String label = truncateToWidth(regionLabel(section.region()), header.width - 28.0f, 17.5f);
        String chevron = section.expandProgress() > 0.5f ? "^" : "v";

        if (hover > 0.01f)
        {
            Bound hoverBound = Bound.of(header.x, header.y + 4.0f, header.width, header.height - 8.0f);
            if (lightThemeEnabled)
            {
                PrimitiveUseCase.rectangle(
                        hoverBound,
                        SOFT_RADII,
                        withUiAlpha(withAlpha(lightThemeSurfaceFill(hover), 0.66f + hover * 0.22f))
                );
                PrimitiveUseCase.strokeInside(
                        hoverBound,
                        SOFT_RADII,
                        0.55f,
                        withAlpha(mixColor(controlStrokeColor(), STROKE_HOVER_LIGHT, hover * 0.45f), 0.18f + hover * 0.16f)
                );
            }
            else
            {
                PrimitiveUseCase.rectangle(
                        hoverBound,
                        SOFT_RADII,
                        withAlpha(controlHoverSurfaceColor(), 0.08f * hover)
                );
            }
        }

        PrimitiveUseCase.rectangle(
                Bound.of(header.x, header.bottom() - 1.0f, header.width, 1.0f),
                Radii.zero(),
                mixColor(controlStrokeColor(), footerHighlightColor(), 0.08f + hover * 0.14f)
        );

        Fonts.ROBOTO_FLEX_REGULAR.draw(
                label,
                header.x + 8.0f,
                header.y + (header.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(17.5f)) / 2.0f,
                17.5f,
                mixColor(textMainColor(), footerHighlightColor(), hover * 0.08f).toRGBA()
        );
        Fonts.ROBOTO_FLEX_REGULAR.draw(
                chevron,
                header.right() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(chevron, 13.0f) - 8.0f,
                header.y + (header.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(13.0f)) / 2.0f,
                13.0f,
                mixColor(textMutedColor(), textMainColor(), Math.max(section.expandProgress() * 0.24f, hover * 0.12f)).toRGBA()
        );
    }

    private void renderSectionBody(SectionLayout section)
    {
        Bound clip = section.contentClipBound();
        float progress = easedExpand(section.expandProgress());
        if (clip.height <= 0.5f || progress <= 0.001f) {
            return;
        }

        MenuScissor.enable(clip);
        try
        {
            if (section.showEmptyState())
            {
                float textY = section.headerBound().bottom() + 10.0f + (1.0f - progress) * 8.0f;
                Fonts.ROBOTO_FLEX_REGULAR.draw(
                        emptyRegionText(),
                        section.headerBound().x + 10.0f,
                        textY,
                        12.0f,
                        withAlpha(textMutedColor(), progress).toRGBA()
                );
                return;
            }

            for (ItemLayout item : section.items()) {
                renderLanguageOption(item.option(), item.bound(), progress);
            }
        }
        finally
        {
            MenuScissor.disable();
        }
    }

    private void renderLanguageOption(LanguageOption option, Bound bound, float revealProgress)
    {
        float hover = optionHoverProgress.getOrDefault(option.language(), 0.0f);
        boolean selected = MenuLanguageState.selected() == option.language();
        float emphasis = Math.max(hover, selected ? 1.0f : 0.0f);
        float alpha = MathHelper.clamp(revealProgress, 0.0f, 1.0f);
        float translateY = (1.0f - alpha) * 10.0f;
        Bound animatedBound = Bound.of(bound.x, bound.y + translateY, bound.width, bound.height);

        if (emphasis > 0.01f)
        {
            Bound highlight = Bound.of(animatedBound.x - 8.0f, animatedBound.y + 4.0f, animatedBound.width + 10.0f, animatedBound.height - 8.0f);
            ColorValue fill = languageOptionHighlightColor(emphasis, selected);
            float fillAlpha = languageOptionHighlightAlpha(emphasis, selected) * alpha;
            PrimitiveUseCase.rectangle(
                    highlight,
                    SOFT_RADII,
                    withAlpha(fill, fillAlpha)
            );

            if (lightThemeEnabled)
            {
                PrimitiveUseCase.strokeInside(
                        highlight,
                        SOFT_RADII,
                        0.55f,
                        withAlpha(
                                mixColor(controlStrokeColor(), STROKE_HOVER_LIGHT, emphasis * 0.42f),
                                (0.20f + emphasis * 0.16f) * alpha
                        )
                );
            }
        }

        Bound halo = Bound.of(animatedBound.x, animatedBound.centerY() - FLAG_HALO_SIZE * 0.5f, FLAG_HALO_SIZE, FLAG_HALO_SIZE);
        ColorValue haloFill = lightThemeEnabled
                ? withAlpha(mixColor(LIGHT_FLAG_HALO, LIGHT_SURFACE_TINT, 0.22f + emphasis * 0.34f), alpha)
                : withAlpha(mixColor(controlFillColor(), controlHoverSurfaceColor(), 0.18f + emphasis * 0.25f), alpha);
        PrimitiveUseCase.rectangle(halo, Radii.of(999.0f), haloFill);

        Bound flagBound = Bound.of(halo.centerX() - FLAG_SIZE * 0.5f, halo.centerY() - FLAG_SIZE * 0.5f, FLAG_SIZE, FLAG_SIZE);
        renderFlag(option.flagStyle(), flagBound, alpha);
        renderFlagCircleOutline(flagBound, alpha, emphasis);

        float titleSize = 15.5f;
        float subtitleSize = 12.0f;
        float titleX = halo.right() + 14.0f;
        float textMaxWidth = Math.max(40.0f, animatedBound.right() - titleX - 18.0f);
        float textBlockHeight = Fonts.ROBOTO_FLEX_REGULAR.getHeight(titleSize) + 4.0f + Fonts.ROBOTO_FLEX_REGULAR.getHeight(subtitleSize);
        float titleY = animatedBound.centerY() - textBlockHeight / 2.0f;
        float subtitleY = titleY + Fonts.ROBOTO_FLEX_REGULAR.getHeight(titleSize) + 4.0f;

        Fonts.ROBOTO_FLEX_REGULAR.draw(
                truncateToWidth(option.countryName(), textMaxWidth, titleSize),
                titleX,
                titleY,
                titleSize,
                withAlpha(mixColor(textMainColor(), footerHighlightColor(), emphasis * 0.06f), alpha).toRGBA()
        );
        Fonts.ROBOTO_FLEX_REGULAR.draw(
                truncateToWidth(option.language().displayNativeName(Fonts.ROBOTO_FLEX_REGULAR.atlas()), textMaxWidth, subtitleSize),
                titleX,
                subtitleY,
                subtitleSize,
                withAlpha(mixColor(textMutedColor(), textMainColor(), emphasis * 0.16f), alpha).toRGBA()
        );

        if (selected)
        {
            PrimitiveUseCase.rectangle(
                    Bound.of(halo.right() + 4.0f, animatedBound.centerY() - 3.0f, 6.0f, 6.0f),
                    Radii.of(999.0f),
                    withAlpha(footerHighlightColor(), alpha)
            );
        }
    }

    private void renderFlagCircleOutline(Bound flagBound, float alpha, float emphasis)
    {
        float outset = FLAG_OUTLINE_OUTSET;
        Bound outlineBound = Bound.of(
                flagBound.x - outset,
                flagBound.y - outset,
                flagBound.width + outset * 2.0f,
                flagBound.height + outset * 2.0f
        );
        ColorValue outline = lightThemeEnabled
                ? mixColor(flagOutlineColor(), STROKE_HOVER_LIGHT, emphasis * 0.22f)
                : mixColor(flagOutlineColor(), controlStrokeColor(), emphasis * 0.1f);
        float radius = MenuDrawBridge.toScreenRadius(flagCircleRadius(outlineBound));
        RenderUtil.drawRoundedBorder(
                MenuDrawBridge.matrices(),
                MenuDrawBridge.toScreenX(outlineBound.x),
                MenuDrawBridge.toScreenY(outlineBound.y),
                MenuDrawBridge.toScreenW(outlineBound.width),
                MenuDrawBridge.toScreenH(outlineBound.height),
                radius,
                FLAG_OUTLINE_THICKNESS,
                withAlpha(outline, alpha).toRGBA()
        );
    }

    private void fillCircleInClip(Bound flagBound, Bound clipBound, float alpha, ColorValue color)
    {
        MenuScissor.enable(clipBound);
        fillCircle(flagBound, alpha, color);
        MenuScissor.disable();
    }

    private void renderFlag(FlagStyle flagStyle, Bound bound, float alpha)
    {
        switch (flagStyle)
        {
            case EGYPT -> renderEgyptFlag(bound, alpha);
            case KENYA -> renderKenyaFlag(bound, alpha);
            case MOROCCO -> renderMoroccoFlag(bound, alpha);
            case SOUTH_AFRICA -> renderSouthAfricaFlag(bound, alpha);
            case CHINA -> renderChinaFlag(bound, alpha);
            case INDIA -> renderIndiaFlag(bound, alpha);
            case JAPAN -> renderJapanFlag(bound, alpha);
            case SOUTH_KOREA -> renderSouthKoreaFlag(bound, alpha);
            case THAILAND -> renderThailandFlag(bound, alpha);
            case VIETNAM -> renderVietnamFlag(bound, alpha);
            case INDONESIA -> renderIndonesiaFlag(bound, alpha);
            case KAZAKHSTAN -> renderKazakhstanFlag(bound, alpha);
            case SINGAPORE -> renderSingaporeFlag(bound, alpha);
            case AUSTRALIA -> renderAustraliaFlag(bound, alpha);
            case NEW_ZEALAND -> renderNewZealandFlag(bound, alpha);
            case UNITED_KINGDOM -> renderUnitedKingdomFlag(bound, alpha);
            case FRANCE -> renderVerticalTricolorFlag(bound, alpha, ColorValue.rgb(24, 85, 198), ColorValue.rgb(248, 248, 249), ColorValue.rgb(221, 62, 70));
            case GERMANY -> renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(25, 25, 27), ColorValue.rgb(198, 45, 47), ColorValue.rgb(239, 187, 53));
            case ITALY -> renderVerticalTricolorFlag(bound, alpha, ColorValue.rgb(39, 141, 82), ColorValue.rgb(248, 248, 249), ColorValue.rgb(221, 62, 70));
            case NETHERLANDS -> renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(196, 48, 70), ColorValue.rgb(248, 248, 249), ColorValue.rgb(37, 83, 180));
            case POLAND -> renderPolishFlag(bound, alpha);
            case PORTUGAL -> renderPortugalFlag(bound, alpha);
            case SPAIN -> renderSpainFlag(bound, alpha);
            case UKRAINE -> renderUkrainianFlag(bound, alpha);
            case RUSSIA -> renderRussianFlag(bound, alpha);
            case MEXICO -> renderVerticalTricolorFlag(bound, alpha, ColorValue.rgb(35, 131, 73), ColorValue.rgb(248, 248, 249), ColorValue.rgb(206, 58, 70));
            case BRAZIL -> renderBrazilFlag(bound, alpha);
            case ARGENTINA -> renderArgentinaFlag(bound, alpha);
            case CHILE -> renderChileFlag(bound, alpha);
            case SAUDI_ARABIA -> renderSaudiArabiaFlag(bound, alpha);
            case ISRAEL -> renderIsraelFlag(bound, alpha);
            case TURKEY -> renderTurkeyFlag(bound, alpha);
            case UAE -> renderUaeFlag(bound, alpha);
            case UNITED_STATES -> renderUnitedStatesFlag(bound, alpha);
            case CANADA -> renderCanadaFlag(bound, alpha);
        }
    }

    private float flagCircleRadius(Bound bound)
    {
        return bound.width * 0.45f;
    }

    private void fillCircle(Bound bound, float alpha, ColorValue color)
    {
        float radius = flagCircleRadius(bound);
        PrimitiveUseCase.rectangleCorners(bound, radius, radius, radius, radius, withAlpha(color, alpha), FLAG_EDGE_SMOOTHNESS);
    }

    private void renderHalfHorizontalFlag(Bound bound, float alpha, ColorValue top, ColorValue bottom)
    {
        float half = bound.height * 0.5f;
        float overlap = FLAG_STRIPE_OVERLAP;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, bound.width, half + overlap), alpha, top);
        fillCircleInClip(bound, Bound.of(bound.x, bound.centerY() - overlap * 0.5f, bound.width, half + overlap), alpha, bottom);
    }

    private void renderVerticalSplitFlag(Bound bound, float alpha, float splitRatio, ColorValue left, ColorValue right)
    {
        float split = bound.width * splitRatio;
        float overlap = FLAG_STRIPE_OVERLAP;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, split + overlap, bound.height), alpha, left);
        fillCircleInClip(bound, Bound.of(bound.x + split - overlap * 0.5f, bound.y, Math.max(0.0f, bound.width - split) + overlap, bound.height), alpha, right);
    }

    private void renderEgyptFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(201, 52, 56), ColorValue.rgb(248, 248, 249), ColorValue.rgb(28, 28, 31));
        renderDot(bound, alpha, ColorValue.rgb(216, 180, 69), 4.5f);
    }

    private void renderKenyaFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(25, 25, 27), ColorValue.rgb(178, 40, 52), ColorValue.rgb(34, 114, 57));
    }

    private void renderMoroccoFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(194, 30, 56));
        renderDot(bound, alpha, ColorValue.rgb(39, 118, 58), 5.0f);
    }

    private void renderSouthAfricaFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(
                bound,
                alpha,
                ColorValue.rgb(25, 94, 168),
                ColorValue.rgb(35, 126, 69),
                ColorValue.rgb(199, 42, 59)
        );
    }

    private void renderChinaFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(204, 36, 48));
        renderDotAt(bound, alpha, ColorValue.rgb(240, 201, 66), bound.x + 7.0f, bound.y + 10.0f, 4.5f);
    }

    private void renderIndiaFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(230, 141, 43), ColorValue.rgb(248, 248, 249), ColorValue.rgb(42, 133, 69));
        renderDot(bound, alpha, ColorValue.rgb(36, 71, 168), 4.5f);
    }

    private void renderJapanFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(248, 248, 249));
        renderDot(bound, alpha, ColorValue.rgb(210, 49, 59), 7.0f);
    }

    private void renderSouthKoreaFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(248, 248, 249));
        renderHalfDot(bound, alpha, ColorValue.rgb(198, 49, 66), ColorValue.rgb(43, 93, 181), 6.5f);
    }

    private void renderThailandFlag(Bound bound, float alpha)
    {
        float stripe = bound.height / 5.0f;
        ColorValue[] colors = {
                ColorValue.rgb(189, 42, 60),
                ColorValue.rgb(248, 248, 249),
                ColorValue.rgb(29, 55, 123),
                ColorValue.rgb(248, 248, 249),
                ColorValue.rgb(189, 42, 60)
        };
        for (int i = 0; i < colors.length; i++)
        {
            float y = bound.y + stripe * i;
            float h = i == colors.length - 1 ? bound.height - stripe * i : stripe + FLAG_STRIPE_OVERLAP;
            if (i > 0)
            {
                y -= FLAG_STRIPE_OVERLAP * 0.5f;
                h += FLAG_STRIPE_OVERLAP * 0.5f;
            }
            fillCircleInClip(bound, Bound.of(bound.x, y, bound.width, h), alpha, colors[i]);
        }
    }

    private void renderVietnamFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(193, 34, 47));
        renderDot(bound, alpha, ColorValue.rgb(239, 200, 62), 5.5f);
    }

    private void renderIndonesiaFlag(Bound bound, float alpha)
    {
        renderHalfHorizontalFlag(bound, alpha, ColorValue.rgb(214, 62, 74), ColorValue.rgb(248, 248, 249));
    }

    private void renderKazakhstanFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(0, 175, 202));
        float sunRadius = bound.width * 0.24f - 0.1f;
        renderDotAt(bound, alpha, ColorValue.rgb(255, 210, 0), bound.centerX(), bound.centerY(), sunRadius);
    }

    private void renderSingaporeFlag(Bound bound, float alpha)
    {
        renderHalfHorizontalFlag(bound, alpha, ColorValue.rgb(206, 46, 61), ColorValue.rgb(248, 248, 249));
        renderDotAt(bound, alpha, ColorValue.rgb(248, 248, 249), bound.x + 8.5f, bound.y + 11.0f, 4.0f);
    }

    private void renderAustraliaFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(34, 68, 143));
        renderDotAt(bound, alpha, ColorValue.rgb(248, 248, 249), bound.right() - 7.0f, bound.centerY(), 4.0f);
    }

    private void renderNewZealandFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(24, 61, 139));
        renderDotAt(bound, alpha, ColorValue.rgb(206, 49, 67), bound.right() - 7.5f, bound.centerY(), 4.0f);
    }

    private void renderUnitedKingdomFlag(Bound bound, float alpha)
    {
        float cw = bound.width * 0.22f;
        float cr = bound.width * 0.11f;
        float pi = bound.width * 0.07f;
        fillCircle(bound, alpha, ColorValue.rgb(36, 61, 133));
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.centerX() - cw * 0.5f, bound.y + pi, cw, Math.max(0.0f, bound.height - pi * 2)),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(246, 246, 248), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.x + pi, bound.centerY() - cw * 0.5f, Math.max(0.0f, bound.width - pi * 2), cw),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(246, 246, 248), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.centerX() - cr * 0.5f, bound.y + pi, cr, Math.max(0.0f, bound.height - pi * 2)),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(202, 42, 64), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.x + pi, bound.centerY() - cr * 0.5f, Math.max(0.0f, bound.width - pi * 2), cr),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(202, 42, 64), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
    }

    private void renderRussianFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(248, 248, 249), ColorValue.rgb(42, 95, 186), ColorValue.rgb(212, 58, 70));
    }

    private void renderUkrainianFlag(Bound bound, float alpha)
    {
        renderHalfHorizontalFlag(bound, alpha, ColorValue.rgb(35, 91, 196), ColorValue.rgb(242, 196, 41));
    }

    private void renderPolishFlag(Bound bound, float alpha)
    {
        renderHalfHorizontalFlag(bound, alpha, ColorValue.rgb(248, 248, 249), ColorValue.rgb(217, 74, 96));
    }

    private void renderPortugalFlag(Bound bound, float alpha)
    {
        renderVerticalSplitFlag(bound, alpha, 0.42f, ColorValue.rgb(37, 128, 66), ColorValue.rgb(198, 46, 57));
        renderDotAt(bound, alpha, ColorValue.rgb(229, 195, 72), bound.x + bound.width * 0.42f + 2.0f, bound.centerY(), 4.0f);
    }

    private void renderSpainFlag(Bound bound, float alpha)
    {
        float stripe = bound.height / 4.0f;
        float overlap = FLAG_STRIPE_OVERLAP;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, bound.width, stripe + overlap), alpha, ColorValue.rgb(194, 43, 58));
        fillCircleInClip(bound, Bound.of(bound.x, bound.y + stripe - overlap * 0.5f, bound.width, stripe * 2.0f + overlap), alpha, ColorValue.rgb(236, 198, 63));
        fillCircleInClip(
                bound,
                Bound.of(bound.x, bound.y + stripe * 3.0f - overlap, bound.width, Math.max(0.0f, bound.height - stripe * 3.0f + overlap)),
                alpha,
                ColorValue.rgb(194, 43, 58)
        );
    }

    private void renderBrazilFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(37, 143, 75));
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.centerX() - 7.5f, bound.centerY() - 5.0f, 15.0f, 10.0f),
                4.0f, 4.0f, 4.0f, 4.0f,
                withAlpha(ColorValue.rgb(235, 194, 67), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        renderDot(bound, alpha, ColorValue.rgb(44, 84, 170), 4.5f);
    }

    private void renderArgentinaFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(102, 192, 232), ColorValue.rgb(248, 248, 249), ColorValue.rgb(102, 192, 232));
        renderDot(bound, alpha, ColorValue.rgb(236, 192, 63), 4.5f);
    }

    private void renderChileFlag(Bound bound, float alpha)
    {
        renderHalfHorizontalFlag(bound, alpha, ColorValue.rgb(248, 248, 249), ColorValue.rgb(198, 43, 58));
        fillCircleInClip(
                bound,
                Bound.of(bound.x, bound.y, bound.width * 0.36f, bound.height * 0.5f),
                alpha,
                ColorValue.rgb(34, 78, 176)
        );
        renderDotAt(bound, alpha, ColorValue.rgb(248, 248, 249), bound.x + 7.0f, bound.y + 9.0f, 2.5f);
    }

    private void renderSaudiArabiaFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(34, 122, 66));
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.x + 6.0f, bound.centerY() + 3.5f, bound.width - 12.0f, 3.0f),
                1.5f, 1.5f, 1.5f, 1.5f,
                withAlpha(ColorValue.rgb(248, 248, 249), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
    }

    private void renderIsraelFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(248, 248, 249));
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.x, bound.y + 4.5f, bound.width, 3.5f),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(42, 96, 192), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        PrimitiveUseCase.rectangleCorners(
                Bound.of(bound.x, bound.bottom() - 8.0f, bound.width, 3.5f),
                0.0f, 0.0f, 0.0f, 0.0f,
                withAlpha(ColorValue.rgb(42, 96, 192), alpha),
                FLAG_STRIPE_SMOOTHNESS
        );
        renderDot(bound, alpha, ColorValue.rgb(42, 96, 192), 3.5f);
    }

    private void renderTurkeyFlag(Bound bound, float alpha)
    {
        fillCircle(bound, alpha, ColorValue.rgb(194, 43, 58));
        renderDotAt(bound, alpha, ColorValue.rgb(248, 248, 249), bound.centerX() - 2.0f, bound.centerY(), 5.0f);
    }

    private void renderUaeFlag(Bound bound, float alpha)
    {
        float split = bound.width * 0.24f;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, split, bound.height), alpha, ColorValue.rgb(199, 46, 63));
        Bound right = Bound.of(bound.x + split, bound.y, bound.width - split, bound.height);
        renderHorizontalTricolorFlag(right, alpha, ColorValue.rgb(34, 126, 69), ColorValue.rgb(248, 248, 249), ColorValue.rgb(28, 28, 31));
    }

    private void renderUnitedStatesFlag(Bound bound, float alpha)
    {
        renderHorizontalTricolorFlag(bound, alpha, ColorValue.rgb(198, 52, 69), ColorValue.rgb(248, 248, 249), ColorValue.rgb(198, 52, 69));
        fillCircleInClip(
                bound,
                Bound.of(bound.x, bound.y, bound.width * 0.42f, bound.height * 0.55f),
                alpha,
                ColorValue.rgb(37, 77, 159)
        );
    }

    private void renderCanadaFlag(Bound bound, float alpha)
    {
        float stripe = bound.width * 0.24f;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, stripe, bound.height), alpha, ColorValue.rgb(206, 52, 68));
        fillCircleInClip(
                bound,
                Bound.of(bound.x + stripe, bound.y, Math.max(0.0f, bound.width - stripe * 2.0f), bound.height),
                alpha,
                ColorValue.rgb(248, 248, 249)
        );
        fillCircleInClip(bound, Bound.of(bound.right() - stripe, bound.y, stripe, bound.height), alpha, ColorValue.rgb(206, 52, 68));
        renderDot(bound, alpha, ColorValue.rgb(206, 52, 68), 4.0f);
    }

    private void renderHorizontalTricolorFlag(Bound bound, float alpha, ColorValue top, ColorValue middle, ColorValue bottom)
    {
        float stripeHeight = bound.height / 3.0f;
        float overlap = FLAG_STRIPE_OVERLAP;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, bound.width, stripeHeight + overlap), alpha, top);
        fillCircleInClip(bound, Bound.of(bound.x, bound.y + stripeHeight - overlap * 0.5f, bound.width, stripeHeight + overlap), alpha, middle);
        fillCircleInClip(
                bound,
                Bound.of(bound.x, bound.y + stripeHeight * 2.0f - overlap, bound.width, Math.max(0.0f, bound.height - stripeHeight * 2.0f + overlap)),
                alpha,
                bottom
        );
    }

    private void renderVerticalTricolorFlag(Bound bound, float alpha, ColorValue left, ColorValue middle, ColorValue right)
    {
        float stripeWidth = bound.width / 3.0f;
        float overlap = FLAG_STRIPE_OVERLAP;
        fillCircleInClip(bound, Bound.of(bound.x, bound.y, stripeWidth + overlap, bound.height), alpha, left);
        fillCircleInClip(bound, Bound.of(bound.x + stripeWidth - overlap * 0.5f, bound.y, stripeWidth + overlap, bound.height), alpha, middle);
        fillCircleInClip(
                bound,
                Bound.of(bound.x + stripeWidth * 2.0f - overlap, bound.y, Math.max(0.0f, bound.width - stripeWidth * 2.0f + overlap), bound.height),
                alpha,
                right
        );
    }

    private void renderDot(Bound bound, float alpha, ColorValue color, float radius)
    {
        renderDotAt(bound, alpha, color, bound.centerX(), bound.centerY(), radius);
    }

    private void renderDotAt(Bound bound, float alpha, ColorValue color, float centerX, float centerY, float radius)
    {
        PrimitiveUseCase.rectangleCorners(
                Bound.of(centerX - radius, centerY - radius, radius * 2.0f, radius * 2.0f),
                radius, radius, radius, radius,
                withAlpha(color, alpha),
                FLAG_EDGE_SMOOTHNESS
        );
    }

    private void renderHalfDot(Bound bound, float alpha, ColorValue topColor, ColorValue bottomColor, float radius)
    {
        float centerX = bound.centerX();
        float centerY = bound.centerY();
        float overlap = FLAG_STRIPE_OVERLAP;
        PrimitiveUseCase.rectangleCorners(
                Bound.of(centerX - radius, centerY - radius, radius * 2.0f, radius + overlap),
                radius, 0.0f, 0.0f, radius,
                withAlpha(topColor, alpha),
                FLAG_EDGE_SMOOTHNESS
        );
        PrimitiveUseCase.rectangleCorners(
                Bound.of(centerX - radius, centerY - overlap * 0.5f, radius * 2.0f, radius + overlap),
                0.0f, radius, radius, 0.0f,
                withAlpha(bottomColor, alpha),
                FLAG_EDGE_SMOOTHNESS
        );
    }

    private void renderScrollbar()
    {
        if (contentViewportBound == null || contentMaxScroll <= 1.0f) {
            return;
        }

        float trackX = contentViewportBound.right() - 4.0f;
        float trackY = contentViewportBound.y + 6.0f;
        float trackHeight = Math.max(24.0f, contentViewportBound.height - 12.0f);

        PrimitiveUseCase.rectangle(
                Bound.of(trackX, trackY, 2.0f, trackHeight),
                Radii.of(999.0f),
                withAlpha(controlStrokeColor(), 0.34f)
        );

        float thumbHeight = Math.max(42.0f, trackHeight * (contentViewportBound.height / (contentViewportBound.height + contentMaxScroll)));
        float travel = Math.max(0.0f, trackHeight - thumbHeight);
        float ratio = contentMaxScroll <= 0.0f ? 0.0f : MathHelper.clamp(contentScroll / contentMaxScroll, 0.0f, 1.0f);
        float thumbY = trackY + travel * ratio;

        PrimitiveUseCase.rectangle(
                Bound.of(trackX - 0.5f, thumbY, 3.0f, thumbHeight),
                Radii.of(999.0f),
                withAlpha(mixColor(textMutedColor(), textMainColor(), 0.26f), 0.86f)
        );
    }

    private void renderSearchIcon(Bound bound)
    {
        float iconEmphasis = Math.max(searchHoverProgress, searchFocused ? 0.45f : 0.0f);
        ColorValue iconColor = mixColor(withAlpha(textMutedColor(), 0.66f), textMainColor(), iconEmphasis * 0.30f);
        float lensSize = bound.width * 0.72f;
        Bound lens = Bound.of(bound.x, bound.y + (bound.height - lensSize) * 0.5f, lensSize, lensSize);
        PrimitiveUseCase.strokeInside(lens, Radii.of(999.0f), 0.75f, iconColor);
        PrimitiveUseCase.rectangle(
                Bound.of(lens.right() - 0.6f, lens.bottom() - 0.2f, 1.6f, 4.2f),
                Radii.of(999.0f),
                iconColor
        );
    }

    private void renderSettingsChip(Bound root)
    {
        Bound bound = settingsChipLayout(root);
        settingsChipBound = bound;
        Radii radii = chipRadii(bound);

        ColorValue fill = lightThemeEnabled
                ? lightThemeSurfaceFill(0.36f + settingsHoverProgress * 0.34f)
                : mixColor(withUiAlpha(ColorValue.rgb(26, 26, 28)), controlHoverSurfaceColor(), settingsHoverProgress * 0.55f);
        ColorValue stroke = lightThemeEnabled
                ? mixColor(settingsChipStrokeColor(), STROKE_HOVER_LIGHT, settingsHoverProgress * 0.38f)
                : mixColor(settingsChipStrokeColor(), controlHoverStrokeColor(), settingsHoverProgress);

        PrimitiveUseCase.rectangle(bound, radii, fill);
        PrimitiveUseCase.strokeInside(
                bound,
                radii,
                CHIP_STROKE_THICKNESS,
                withAlpha(stroke, lightThemeEnabled ? 0.50f + settingsHoverProgress * 0.24f : 0.88f)
        );

        float knobSize = 24.0f;
        float knobX = settingsChipKnobLeft(bound, switchVisualProgress);
        PrimitiveUseCase.rectangle(Bound.of(knobX, bound.y + 1.0f, knobSize, knobSize), radii, settingsChipKnobColor());

        Bound whiteThemeIcon = Bound.of(bound.x + 9.0f, bound.centerY() - 6.27f, 12.54f, 12.54f);
        Bound darkThemeIcon = Bound.of(bound.right() - 19.0f, bound.centerY() - 5.04f, 10.08f, 10.08f);

        TextureUseCase.draw(Identifier.of("celebression_ui", "textures/ui/white.png"), whiteThemeIcon, Radii.zero(), themeIconColor(true));
        TextureUseCase.draw(Identifier.of("celebression_ui", "textures/ui/black.png"), darkThemeIcon, Radii.zero(), themeIconColor(false));
    }

    private void renderQuitChip(Bound root)
    {
        String label = MenuLanguageState.text("menu.quit");
        float fontSize = 12.0f;
        Bound bound = quitChipLayout(root);
        quitChipBound = bound;
        Radii radii = chipRadii(bound);

        PrimitiveUseCase.rectangle(bound, radii, quitFillColor(quitHoverProgress));
        PrimitiveUseCase.strokeInside(
                bound,
                radii,
                CHIP_STROKE_THICKNESS,
                withAlpha(quitStrokeColor(quitHoverProgress), lightThemeEnabled ? 0.72f : 0.92f)
        );

        ColorValue contentColor = quitTextColor(quitHoverProgress);
        Bound iconBound = Bound.of(
                bound.x + QUIT_CHIP_PADDING_X,
                bound.centerY() - QUIT_ICON_SIZE * 0.5f,
                QUIT_ICON_SIZE,
                QUIT_ICON_SIZE
        );
        renderQuitIcon(iconBound, contentColor);

        float textX = iconBound.right() + QUIT_ICON_GAP;
        float availableTextW = Math.max(0.0f, bound.right() - QUIT_CHIP_PADDING_X - textX);
        if (Fonts.ROBOTO_FLEX_REGULAR.getWidth(label, fontSize) > availableTextW && availableTextW > 0.0f) {
            fontSize = fontSize * (availableTextW / Fonts.ROBOTO_FLEX_REGULAR.getWidth(label, fontSize));
        }
        float textY = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f;
        Fonts.ROBOTO_FLEX_REGULAR.draw(label, textX, textY, fontSize, contentColor.toRGBA());
    }

    private void renderQuitIcon(Bound bound, ColorValue color)
    {
        Bound frame = Bound.of(bound.x + 0.5f, bound.y + 1.0f, bound.width * 0.48f, bound.height - 2.0f);
        PrimitiveUseCase.strokeInside(frame, Radii.of(2.0f), 0.85f, color);

        float arrowY = bound.centerY();
        float arrowStart = frame.right() + 1.2f;
        float arrowLen = Math.max(2.0f, bound.right() - arrowStart - 0.5f);
        PrimitiveUseCase.rectangle(Bound.of(arrowStart, arrowY - 0.65f, arrowLen, 1.25f), Radii.of(999.0f), color);
        PrimitiveUseCase.rectangle(Bound.of(bound.right() - 2.8f, arrowY - 1.8f, 1.4f, 1.2f), Radii.of(0.3f), color);
        PrimitiveUseCase.rectangle(Bound.of(bound.right() - 1.6f, arrowY - 0.65f, 1.6f, 1.25f), Radii.of(0.3f), color);
        PrimitiveUseCase.rectangle(Bound.of(bound.right() - 2.8f, arrowY + 0.6f, 1.4f, 1.2f), Radii.of(0.3f), color);
    }

    private void updateAnimations(Bound root, int mouseX, int mouseY)
    {
        long now = System.currentTimeMillis();
        float dt = Math.min(0.05f, Math.max(0.001f, (now - lastAnimationFrameAt) / 1000.0f));
        lastAnimationFrameAt = now;

        float fbX = toFramebufferX(mouseX);
        float fbY = toFramebufferY(mouseY);

        switchVisualProgress = animateTowards(switchVisualProgress, lightThemeEnabled ? 1.0f : 0.0f, dt, ANIMATION_SPEED);
        settingsHoverProgress = animateTowards(settingsHoverProgress, settingsChipLayout(root).contains(fbX, fbY) ? 1.0f : 0.0f, dt, ANIMATION_SPEED);
        quitHoverProgress = animateTowards(quitHoverProgress, quitChipLayout(root).contains(fbX, fbY) ? 1.0f : 0.0f, dt, ANIMATION_SPEED);
        searchHoverProgress = animateTowards(searchHoverProgress, searchFocused || (searchBound != null && searchBound.contains(fbX, fbY)) ? 1.0f : 0.0f, dt, ANIMATION_SPEED);
        contentScrollTarget = MathHelper.clamp(contentScrollTarget, 0.0f, contentMaxScroll);
        contentScroll = animateTowards(contentScroll, contentScrollTarget, dt, SCROLL_ANIMATION_SPEED);
        if (Math.abs(contentScroll - contentScrollTarget) < 0.2f) {
            contentScroll = contentScrollTarget;
        }

        String query = normalizeQuery(searchInput);
        boolean queryActive = !query.isEmpty();

        for (RegionSection region : DISPLAYED_REGIONS)
        {
            Bound bound = sectionBounds.get(region);
            boolean hover = bound != null && bound.contains(fbX, fbY);
            float current = sectionHoverProgress.getOrDefault(region, 0.0f);
            sectionHoverProgress.put(region, animateTowards(current, hover ? 1.0f : 0.0f, dt, ANIMATION_SPEED));

            boolean hasMatches = !filteredOptionsForRegion(region, query).isEmpty();
            boolean regionHit = queryActive && regionMatches(region, query);
            float target = (queryActive && (hasMatches || regionHit)) || (!queryActive && regionExpanded.getOrDefault(region, false)) ? 1.0f : 0.0f;
            float expand = sectionExpandProgress.getOrDefault(region, target);
            sectionExpandProgress.put(region, animateTowards(expand, target, dt, EXPAND_ANIMATION_SPEED));
        }

        for (MenuLanguageState.Language language : MenuLanguageState.Language.values())
        {
            Bound bound = optionBounds.get(language);
            boolean hover = bound != null && bound.contains(fbX, fbY);
            float current = optionHoverProgress.getOrDefault(language, 0.0f);
            optionHoverProgress.put(language, animateTowards(current, hover ? 1.0f : 0.0f, dt, ANIMATION_SPEED));
        }
    }

    private List<SectionLayout> buildSectionLayouts(Bound root)
    {
        optionBounds.clear();
        sectionBounds.clear();

        String query = normalizeQuery(searchInput);
        boolean queryActive = !query.isEmpty();
        float sectionWidth = Math.min(SECTION_WIDTH, root.width - PADDING * 2.0f - 50.0f);
        float startX = root.centerX() - sectionWidth * 0.5f;
        float startY = contentViewportBound == null ? searchLayout(root).bottom() + SECTION_TITLE_GAP : contentViewportBound.y - contentScroll;
        float y = startY;

        List<SectionLayout> layouts = new ArrayList<>();
        for (RegionSection region : DISPLAYED_REGIONS)
        {
            List<LanguageOption> items = filteredOptionsForRegion(region, query);
            boolean regionHit = queryActive && regionMatches(region, query);
            if (queryActive && items.isEmpty() && !regionHit) {
                continue;
            }

            Bound header = Bound.of(startX, y, sectionWidth, SECTION_HEADER_HEIGHT);
            sectionBounds.put(region, header);
            y += SECTION_HEADER_HEIGHT;

            boolean expanded = queryActive || regionExpanded.getOrDefault(region, false);
            float expandProgress = sectionExpandProgress.getOrDefault(region, expanded ? 1.0f : 0.0f);
            boolean emptyState = items.isEmpty();
            List<ItemLayout> itemLayouts = new ArrayList<>();
            float contentHeight = emptyState ? SECTION_EMPTY_HEIGHT : 0.0f;

            if (!items.isEmpty())
            {
                int columns = itemColumns(sectionWidth, items.size());
                float itemStartX = header.x + 8.0f;
                float itemStartY = y + SECTION_ITEM_TOP_GAP;

                for (int i = 0; i < items.size(); i++)
                {
                    int col = i % columns;
                    int row = i / columns;
                    Bound itemBound = Bound.of(
                            itemStartX + col * (ITEM_WIDTH + ITEM_GAP_X),
                            itemStartY + row * (ITEM_HEIGHT + ITEM_GAP_Y),
                            ITEM_WIDTH,
                            ITEM_HEIGHT
                    );
                    itemLayouts.add(new ItemLayout(items.get(i), itemBound));
                }

                int rows = (int) Math.ceil(items.size() / (float) columns);
                contentHeight = SECTION_ITEM_TOP_GAP + rows * ITEM_HEIGHT + Math.max(0, rows - 1) * ITEM_GAP_Y + 10.0f;
            }

            float easedProgress = easedExpand(expandProgress);
            float visibleHeight = contentHeight * easedProgress;
            Bound clipBound = Bound.of(header.x, header.bottom(), header.width, visibleHeight);
            if (visibleHeight > 6.0f)
            {
                for (ItemLayout itemLayout : itemLayouts) {
                    optionBounds.put(itemLayout.option().language(), itemLayout.bound());
                }
            }

            y += visibleHeight;
            y += SECTION_GAP;
            layouts.add(new SectionLayout(region, header, List.copyOf(itemLayouts), expanded, emptyState, expandProgress, clipBound));
        }

        if (contentViewportBound != null)
        {
            float totalHeight = Math.max(0.0f, y - startY);
            contentMaxScroll = Math.max(0.0f, totalHeight - contentViewportBound.height + 8.0f);
        }
        else
        {
            contentMaxScroll = 0.0f;
        }

        return List.copyOf(layouts);
    }

    private List<LanguageOption> filteredOptionsForRegion(RegionSection region, String query)
    {
        boolean queryActive = !query.isEmpty();
        boolean regionMatches = queryActive && regionMatches(region, query);
        List<LanguageOption> filtered = new ArrayList<>();

        for (LanguageOption option : LANGUAGE_OPTIONS)
        {
            if (option.region() != region) {
                continue;
            }

            if (!queryActive || regionMatches || option.matches(query)) {
                filtered.add(option);
            }
        }

        return filtered;
    }

    private void openFirstMatch()
    {
        String query = normalizeQuery(searchInput);
        if (query.isEmpty())
        {
            openMenuForLanguage(MenuLanguageState.selected());
            return;
        }

        for (SectionLayout section : sectionLayouts)
        {
            if (!section.items().isEmpty())
            {
                openMenuForLanguage(section.items().getFirst().option().language());
                return;
            }
        }
    }

    private void openMenuForLanguage(MenuLanguageState.Language language)
    {
        MenuLanguageState.select(language);
        MenuUI.setSharedLightThemeEnabled(lightThemeEnabled);

        MenuConfigBridge.saveAsync();

        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        if (client != null) {
            client.setScreen(returnScreen != null ? returnScreen : new MenuUI());
        }
    }

    private SearchViewport searchViewport(float fontSize, float maxWidth)
    {
        if (searchInput.isEmpty()) {
            return new SearchViewport("", 0.0f);
        }

        int cursor = MathHelper.clamp(searchCursorIndex, 0, searchInput.length());
        int start = 0;
        while (start < cursor && Fonts.ROBOTO_FLEX_REGULAR.getWidth(searchInput.substring(start, cursor), fontSize) > maxWidth) {
            start++;
        }

        int end = searchInput.length();
        while (start < end && Fonts.ROBOTO_FLEX_REGULAR.getWidth(searchInput.substring(start, end), fontSize) > maxWidth) {
            end--;
        }

        if (end < cursor) {
            end = cursor;
        }
        while (start < cursor && Fonts.ROBOTO_FLEX_REGULAR.getWidth(searchInput.substring(start, end), fontSize) > maxWidth) {
            start++;
        }

        String text = searchInput.substring(start, end);
        float caretOffset = Fonts.ROBOTO_FLEX_REGULAR.getWidth(searchInput.substring(start, cursor), fontSize);
        return new SearchViewport(text, caretOffset);
    }

    private Bound rootBound()
    {
        GameWindowPort window = MenuUI.fromWindow();
        return Bound.of(0.0f, 0.0f, window.getFramebufferWidth(), window.getFramebufferHeight());
    }

    private float toFramebufferX(float mouseX)
    {
        GameWindowPort window = MenuUI.fromWindow();
        float scaleX = window.getScaledWidth() <= 0 ? 1.0f : (float) window.getFramebufferWidth() / (float) window.getScaledWidth();
        return mouseX * scaleX;
    }

    private float toFramebufferY(float mouseY)
    {
        GameWindowPort window = MenuUI.fromWindow();
        float scaleY = window.getScaledHeight() <= 0 ? 1.0f : (float) window.getFramebufferHeight() / (float) window.getScaledHeight();
        return mouseY * scaleY;
    }

    private Bound settingsChipLayout(Bound root)
    {
        float chipH = 26.0f;
        float chipW = 53.0f;
        float rightOffset = QUIT_CHIP_MARGIN_RIGHT + quitChipWidth() + QUIT_CHIP_GAP + 16.0f;
        return Bound.anchorRight(root, topBarChipY(chipH), chipW, chipH, rightOffset);
    }

    private Bound quitChipLayout(Bound root)
    {
        float chipH = QUIT_CHIP_HEIGHT;
        return Bound.anchorRight(root, topBarChipY(chipH), quitChipWidth(), chipH, QUIT_CHIP_MARGIN_RIGHT + 8.0f);
    }

    private float quitChipWidth()
    {
        String label = MenuLanguageState.text("menu.quit");
        float fontSize = 12.0f;
        return Math.max(
                72.0f,
                QUIT_CHIP_PADDING_X * 2.0f + QUIT_ICON_SIZE + QUIT_ICON_GAP + Fonts.ROBOTO_FLEX_REGULAR.getWidth(label, fontSize) + 2.0f
        );
    }

    private Radii chipRadii(Bound bound)
    {
        return Radii.of(Math.min(bound.width, bound.height) * 0.5f);
    }

    private float topBarLogoY()
    {
        return PADDING + topBarIntroOffset() - 10.0f;
    }

    private float topBarTitleY()
    {
        return topBarLogoY() + (TOP_BAR_LOGO_SIZE - Fonts.ROBOTO_FLEX_REGULAR.getHeight(TOP_BAR_TITLE_SIZE)) / 2.0f;
    }

    private float topBarChipY(float chipHeight)
    {
        float titleHeight = Fonts.ROBOTO_FLEX_REGULAR.getHeight(TOP_BAR_TITLE_SIZE);
        return topBarTitleY() + (titleHeight - chipHeight) / 2.0f;
    }

    private Bound searchLayout(Bound root)
    {
        float width = Math.min(SEARCH_WIDTH, root.width - PADDING * 2.0f - 96.0f);
        float top = contentTop(root) + heroBlockHeight() + HERO_HINT_TO_SEARCH_GAP;
        return Bound.of(root.centerX() - width * 0.5f, top, width, SEARCH_HEIGHT);
    }

    private float heroBlockHeight()
    {
        return Fonts.ROBOTO_FLEX_REGULAR.getHeight(HERO_TITLE_SIZE)
                + HERO_TITLE_TO_HINT_GAP
                + Fonts.ROBOTO_FLEX_REGULAR.getHeight(HERO_HINT_SIZE);
    }

    private Bound contentViewportLayout(Bound root)
    {
        float top = searchLayout(root).bottom() + SECTION_TITLE_GAP;
        float height = Math.max(80.0f, root.bottom() - top - CONTENT_BOTTOM_PADDING);
        float width = Math.min(SECTION_WIDTH, root.width - PADDING * 2.0f - 50.0f) + 8.0f;
        return Bound.of(root.centerX() - width * 0.5f, top, width, height);
    }

    private float contentTop(Bound root)
    {
        return Math.max(PADDING + TOP_BAR_HEIGHT + 40.0f, root.centerY() - 178.0f) + introOffset(20.0f) + LANGUAGE_CONTENT_Y_OFFSET;
    }

    private float settingsChipKnobLeft(Bound chip, float lightProgress)
    {
        float knobSize = 24.0f;
        float darkLeft = chip.right() - knobSize - 1.0f;
        float lightLeft = chip.x + 1.0f;
        return darkLeft + (lightLeft - darkLeft) * MathHelper.clamp(lightProgress, 0.0f, 1.0f);
    }

    private float uiElementAlpha()
    {
        float t = MathHelper.clamp((System.currentTimeMillis() - openedAt) / (float) INTRO_MS, 0.0f, 1.0f);
        float inverse = 1.0f - t;
        return 1.0f - inverse * inverse * inverse;
    }

    private float topBarIntroOffset()
    {
        return (1.0f - uiElementAlpha()) * 14.0f;
    }

    private float introOffset(float pixels)
    {
        return (1.0f - uiElementAlpha()) * pixels;
    }

    private void resetContentScroll()
    {
        contentScroll = 0.0f;
        contentScrollTarget = 0.0f;
    }

    private boolean clampContentScrollState()
    {
        float clampedTarget = MathHelper.clamp(contentScrollTarget, 0.0f, contentMaxScroll);
        float clampedCurrent = MathHelper.clamp(contentScroll, 0.0f, contentMaxScroll);
        boolean changed = Math.abs(clampedTarget - contentScrollTarget) > 0.001f || Math.abs(clampedCurrent - contentScroll) > 0.001f;
        contentScrollTarget = clampedTarget;
        contentScroll = clampedCurrent;
        return changed;
    }

    private float easedExpand(float progress)
    {
        float clamped = MathHelper.clamp(progress, 0.0f, 1.0f);
        float inverse = 1.0f - clamped;
        return 1.0f - inverse * inverse * inverse;
    }

    private static String truncateToWidth(String text, float maxWidth, float fontSize)
    {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (Fonts.ROBOTO_FLEX_REGULAR.getWidth(text, fontSize) <= maxWidth) {
            return text;
        }

        String ellipsis = "...";
        float ellipsisWidth = Fonts.ROBOTO_FLEX_REGULAR.getWidth(ellipsis, fontSize);
        if (ellipsisWidth >= maxWidth) {
            return "";
        }

        int end = text.length();
        while (end > 0)
        {
            String sub = text.substring(0, end);
            if (Fonts.ROBOTO_FLEX_REGULAR.getWidth(sub, fontSize) + ellipsisWidth <= maxWidth) {
                return sub + ellipsis;
            }
            end--;
        }
        return "";
    }

    private boolean isCaretVisible()
    {
        return (System.currentTimeMillis() / 530L) % 2L == 0L;
    }

    private int itemColumns(float sectionWidth, int itemCount)
    {
        if (itemCount <= 1 || sectionWidth < 420.0f) {
            return 1;
        }
        if (sectionWidth < 690.0f) {
            return Math.min(2, itemCount);
        }
        if (sectionWidth < 910.0f) {
            return Math.min(3, itemCount);
        }
        return Math.min(4, itemCount);
    }

    private boolean regionMatches(RegionSection region, String query)
    {
        return switch (region)
        {
            case AFRICA -> containsAny(query, "africa", "\u0430\u0444\u0440\u0438\u043a\u0430", "\u0430\u0444\u0440\u0438\u043a\u0430", "afryka");
            case ASIA -> containsAny(query, "asia", "\u0430\u0437\u0438\u044f", "\u0430\u0437\u0456\u044f", "azja");
            case AUSTRALIA_OCEANIA -> containsAny(query, "australia", "oceania", "\u0430\u0432\u0441\u0442\u0440\u0430\u043b\u0438\u044f", "\u043e\u043a\u0435\u0430\u043d\u0438\u044f", "\u0430\u0432\u0441\u0442\u0440\u0430\u043b\u0456\u044f", "\u043e\u043a\u0435\u0430\u043d\u0456\u044f");
            case EUROPE -> containsAny(query, "europe", "\u0435\u0432\u0440\u043e\u043f\u0430", "\u0454\u0432\u0440\u043e\u043f\u0430", "europa");
            case LATIN_AMERICA -> containsAny(query, "latin", "america", "\u043b\u0430\u0442\u0438\u043d", "\u043b\u0430\u0442\u0438\u043d\u0441\u044c\u043a\u0430", "ameryka lacinska");
            case MIDDLE_EAST -> containsAny(query, "middle east", "\u0431\u043b\u0438\u0436\u043d\u0438\u0439 \u0432\u043e\u0441\u0442\u043e\u043a", "\u0431\u043b\u0438\u0437\u044c\u043a\u0438\u0439 \u0441\u0445\u0456\u0434", "bliski wschod");
            case NORTH_AMERICA -> containsAny(query, "north america", "\u0441\u0435\u0432\u0435\u0440\u043d\u0430\u044f \u0430\u043c\u0435\u0440\u0438\u043a\u0430", "\u043f\u0456\u0432\u043d\u0456\u0447\u043d\u0430 \u0430\u043c\u0435\u0440\u0438\u043a\u0430", "ameryka polnocna");
        };
    }

    private boolean containsAny(String query, String... values)
    {
        for (String value : values)
        {
            if (value.toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
        }
        return false;
    }

    private String currentSelectionLine()
    {
        return MenuLanguageState.format("language.current_selection", MenuLanguageState.selected().nativeName());
    }

    private String regionLabel(RegionSection region)
    {
        return MenuLanguageState.text(switch (region)
        {
            case AFRICA -> "region.africa";
            case ASIA -> "region.asia";
            case AUSTRALIA_OCEANIA -> "region.australia_oceania";
            case EUROPE -> "region.europe";
            case LATIN_AMERICA -> "region.latin_america";
            case MIDDLE_EAST -> "region.middle_east";
            case NORTH_AMERICA -> "region.north_america";
        });
    }

    private String searchPlaceholder()
    {
        return MenuLanguageState.text("language.search_placeholder");
    }

    private String emptyRegionText()
    {
        return MenuLanguageState.text("language.empty_region");
    }

    private String noResultsTitle()
    {
        return MenuLanguageState.text("language.no_results_title");
    }

    private String noResultsSubtitle()
    {
        return MenuLanguageState.text("language.no_results_subtitle");
    }

    private String normalizeQuery(String raw)
    {
        return raw == null ? "" : raw.trim().toLowerCase(Locale.ROOT);
    }

    private void renderBackgroundFill(Bound root)
    {
        PrimitiveUseCase.rectangle(root, Radii.zero(), backgroundColor());
    }

    private ColorValue backgroundColor()
    {
        return lightThemeEnabled ? MENU_BACKGROUND_LIGHT : MENU_BACKGROUND_DARK;
    }

    private ColorValue flagOutlineColor()
    {
        return lightThemeEnabled ? LIGHT_FLAG_OUTLINE : FLAG_OUTLINE_COLOR;
    }

    private ColorValue lightThemeSurfaceFill(float strength)
    {
        float clamped = MathHelper.clamp(strength, 0.0f, 1.0f);
        ColorValue fill = mixColor(backgroundColor(), LIGHT_SURFACE_TINT, 0.52f + clamped * 0.28f);
        return mixColor(fill, LIGHT_SURFACE_TINT_STRONG, clamped * 0.22f);
    }

    private ColorValue languageOptionHighlightColor(float emphasis, boolean selected)
    {
        if (!lightThemeEnabled) {
            return mixColor(backgroundColor(), controlHoverSurfaceColor(), 0.40f);
        }

        float strength = selected ? Math.max(emphasis, 0.72f) : emphasis;
        return lightThemeSurfaceFill(strength);
    }

    private float languageOptionHighlightAlpha(float emphasis, boolean selected)
    {
        if (!lightThemeEnabled) {
            float fillAlpha = 0.10f + emphasis * 0.06f;
            if (selected) {
                fillAlpha = Math.max(fillAlpha, 0.16f);
            }
            return fillAlpha;
        }

        float fillAlpha = 0.70f + emphasis * 0.18f;
        if (selected) {
            fillAlpha = Math.max(fillAlpha, 0.82f);
        }
        return fillAlpha;
    }

    private ColorValue searchFieldFillColor(float emphasis)
    {
        if (lightThemeEnabled) {
            float strength = 0.44f + MathHelper.clamp(emphasis, 0.0f, 1.0f) * 0.56f;
            return withUiAlpha(lightThemeSurfaceFill(strength));
        }

        float idleMix = 0.38f;
        ColorValue fill = mixColor(backgroundColor(), controlFillColor(), idleMix);
        if (emphasis > 0.001f)
        {
            ColorValue hoverFill = mixColor(backgroundColor(), controlHoverSurfaceColor(), 0.44f);
            fill = mixColor(fill, hoverFill, emphasis * 0.55f);
        }
        return withUiAlpha(fill);
    }

    private ColorValue searchFieldStrokeColor(float emphasis)
    {
        if (lightThemeEnabled) {
            float strength = 0.40f + MathHelper.clamp(emphasis, 0.0f, 1.0f) * 0.60f;
            ColorValue stroke = mixColor(backgroundColor(), STROKE_HOVER_LIGHT, 0.36f + strength * 0.34f);
            return withAlpha(withUiAlpha(stroke), 0.48f + strength * 0.22f);
        }

        float idleMix = 0.28f;
        ColorValue stroke = mixColor(backgroundColor(), controlStrokeColor(), idleMix);
        if (emphasis > 0.001f)
        {
            stroke = mixColor(stroke, controlHoverStrokeColor(), emphasis * 0.32f);
        }
        return withAlpha(withUiAlpha(stroke), 0.36f + emphasis * 0.18f);
    }

    private ColorValue controlFillColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_CONTROL_FILL_LIGHT : ALT_CONTROL_FILL);
    }

    private ColorValue controlStrokeColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_OUTLINE_LIGHT : ALT_CONTROL_STROKE);
    }

    private ColorValue textMainColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_TEXT_LIGHT : ALT_TEXT_MAIN);
    }

    private ColorValue textMutedColor()
    {
        return withUiAlpha(ALT_TEXT_MUTED);
    }

    private ColorValue footerHighlightColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_TEXT_LIGHT : FOOTER_HIGHLIGHT);
    }

    private ColorValue logoTintColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_TEXT_LIGHT : ColorValue.rgb(255, 255, 255));
    }

    private ColorValue settingsChipFillColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_CONTROL_FILL_LIGHT : ColorValue.rgb(23, 24, 24));
    }

    private ColorValue settingsChipStrokeColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_OUTLINE_LIGHT : ColorValue.rgb(34, 33, 36));
    }

    private ColorValue settingsChipKnobColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_CHIP_KNOB_LIGHT : ColorValue.rgb(34, 33, 36));
    }

    private ColorValue controlHoverSurfaceColor()
    {
        return withUiAlpha(lightThemeEnabled ? CONTROL_HOVER_LIGHT : CONTROL_HOVER_DARK);
    }

    private ColorValue controlHoverStrokeColor()
    {
        return withUiAlpha(lightThemeEnabled ? STROKE_HOVER_LIGHT : STROKE_HOVER_DARK);
    }

    private ColorValue activeThemeIconColor()
    {
        return withUiAlpha(lightThemeEnabled ? MENU_TEXT_LIGHT : ALT_TEXT_MAIN);
    }

    private ColorValue inactiveThemeIconColor()
    {
        return withUiAlpha(lightThemeEnabled ? ALT_TEXT_MUTED : ColorValue.rgb(89, 90, 97));
    }

    private ColorValue themeIconColor(boolean lightIcon)
    {
        float emphasis = lightIcon ? switchVisualProgress : 1.0f - switchVisualProgress;
        return mixColor(inactiveThemeIconColor(), activeThemeIconColor(), emphasis);
    }

    private ColorValue quitFillColor(float hoverProgress)
    {
        ColorValue base = lightThemeEnabled ? MENU_QUIT_FILL_LIGHT : ColorValue.rgb(104, 28, 28);
        ColorValue fill = mixColor(base, QUIT_FILL_HOVER, hoverProgress);
        return withUiAlpha(fill);
    }

    private ColorValue quitStrokeColor(float hoverProgress)
    {
        ColorValue base = lightThemeEnabled
                ? ColorValue.rgb(246, 178, 188)
                : ColorValue.rgb(176, 52, 64);
        ColorValue hover = lightThemeEnabled
                ? ColorValue.rgb(238, 98, 110)
                : ColorValue.rgb(214, 72, 84);
        return withUiAlpha(mixColor(base, hover, hoverProgress));
    }

    private ColorValue quitTextColor(float hoverProgress)
    {
        ColorValue base = withUiAlpha(lightThemeEnabled ? MENU_QUIT_TEXT_LIGHT : ColorValue.rgb(204, 64, 64));
        return mixColor(base, withUiAlpha(ColorValue.rgb(255, 255, 255)), hoverProgress * 0.18f);
    }

    private float animateTowards(float current, float target, float dt, float speed)
    {
        float factor = 1.0f - (float) Math.exp(-speed * dt);
        return current + (target - current) * factor;
    }

    private ColorValue withUiAlpha(ColorValue color)
    {
        return withAlpha(color, uiElementAlpha());
    }

    private ColorValue withAlpha(ColorValue color, float factor)
    {
        int rgba = color.toRGBA();
        int alpha = rgba >>> 24;
        return color.alphaRGBA(Math.round(alpha * MathHelper.clamp(factor, 0.0f, 1.0f)));
    }

    private ColorValue mixColor(ColorValue from, ColorValue to, float t)
    {
        float clamped = MathHelper.clamp(t, 0.0f, 1.0f);
        int a = from.toRGBA();
        int b = to.toRGBA();

        int ar = a >> 16 & 0xFF;
        int ag = a >> 8 & 0xFF;
        int ab = a & 0xFF;
        int aa = a >>> 24;

        int br = b >> 16 & 0xFF;
        int bg = b >> 8 & 0xFF;
        int bb = b & 0xFF;
        int ba = b >>> 24;

        return ColorValue.rgba(
                Math.round(ar + (br - ar) * clamped),
                Math.round(ag + (bg - ag) * clamped),
                Math.round(ab + (bb - ab) * clamped),
                Math.round(aa + (ba - aa) * clamped)
        );
    }

    private record SearchViewport(String text, float caretOffset)
    {
    }

    private enum RegionSection
    {
        AFRICA,
        ASIA,
        AUSTRALIA_OCEANIA,
        EUROPE,
        LATIN_AMERICA,
        MIDDLE_EAST,
        NORTH_AMERICA
    }

    private enum FlagStyle
    {
        EGYPT,
        KENYA,
        MOROCCO,
        SOUTH_AFRICA,
        CHINA,
        INDIA,
        JAPAN,
        SOUTH_KOREA,
        THAILAND,
        VIETNAM,
        INDONESIA,
        KAZAKHSTAN,
        SINGAPORE,
        AUSTRALIA,
        NEW_ZEALAND,
        UNITED_KINGDOM,
        FRANCE,
        GERMANY,
        ITALY,
        NETHERLANDS,
        POLAND,
        PORTUGAL,
        SPAIN,
        UKRAINE,
        RUSSIA,
        MEXICO,
        BRAZIL,
        ARGENTINA,
        CHILE,
        SAUDI_ARABIA,
        ISRAEL,
        TURKEY,
        UAE,
        UNITED_STATES,
        CANADA
    }

    private record LanguageOption(RegionSection region, MenuLanguageState.Language language, FlagStyle flagStyle, String countryName, String... aliases)
    {
        private boolean matches(String query)
        {
            if (query.isBlank()) {
                return true;
            }

            if (countryName.toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
            if (language.nativeName().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
            if (language.englishName().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
            if (language.shortCode().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }

            for (String alias : aliases)
            {
                if (alias.toLowerCase(Locale.ROOT).contains(query)) {
                    return true;
                }
            }
            return false;
        }
    }

    private record ItemLayout(LanguageOption option, Bound bound)
    {
    }

    private record SectionLayout(
            RegionSection region,
            Bound headerBound,
            List<ItemLayout> items,
            boolean expanded,
            boolean showEmptyState,
            float expandProgress,
            Bound contentClipBound
    )
    {
    }
}
