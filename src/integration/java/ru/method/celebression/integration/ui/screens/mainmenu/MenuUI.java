package ru.method.celebression.integration.ui.screens.mainmenu;

import com.mojang.authlib.GameProfile;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.Account;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.GameWindowPort;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.ColorValue;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.TextValue;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuDrawBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.render.font.Fonts;
import ru.method.celebression.integration.ui.screens.mainmenu.render.PrimitiveUseCase;
import ru.method.celebression.integration.ui.screens.mainmenu.render.TextureUseCase;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftWindowAdapter;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.VanillaGuiPreviewRenderer;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.SkinProfileService;
import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftClientProvider;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SkinTextures;
import ru.method.celebression.integration.ui.screens.mainmenu.render.TextureRegion;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;
import ru.method.celebression.integration.ui.util.math.geometry.Radii;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class MenuUI extends Screen
{
    private static final float PADDING = 28.0f;
    private static final float TOP_BAR_HEIGHT = 34.0f;

    private static final float QUIT_CHIP_WIDTH = 58;
    private static final float QUIT_CHIP_HEIGHT = 26;
    private static final float QUIT_CHIP_MARGIN_RIGHT = 8.0f;
    private static final float QUIT_CHIP_GAP = 8.0f;
    private static final float QUIT_CHIP_PADDING_X = 8.0f;
    private static final float OPTIONS_CHIP_MIN_WIDTH = 92.0f;
    private static final float OPTIONS_CHIP_PADDING_X = 13.0f;

    private static final Radii CHIP_RADII = Radii.of(999.0f);

    private static final ColorValue QUIT_FILL_HOVER = ColorValue.rgba(255, 82, 96, 240);
    private static final ColorValue MENU_BACKGROUND_DARK = ColorValue.rgb(15, 15, 17);
    private static final ColorValue MENU_BACKGROUND_LIGHT = ColorValue.rgb(245, 245, 245);
    private static final ColorValue MENU_OUTLINE_LIGHT = ColorValue.rgb(224, 225, 232);
    private static final ColorValue MENU_TEXT_LIGHT = ColorValue.rgb(33, 35, 41);
    private static final ColorValue MENU_CONTROL_FILL_LIGHT = ColorValue.rgb(250, 250, 250);
    private static final ColorValue MENU_CHIP_KNOB_LIGHT = ColorValue.rgb(236, 238, 242);
    private static final ColorValue MENU_QUIT_FILL_LIGHT = ColorValue.rgb(255, 239, 243);
    private static final ColorValue MENU_QUIT_TEXT_LIGHT = ColorValue.rgb(238, 98, 110);
    private static final ColorValue MENU_PREVIEW_SHADOW_LIGHT = ColorValue.rgba(158, 164, 178, 84);
    private static final ColorValue CONTROL_HOVER_DARK = ColorValue.rgb(30, 30, 32);
    private static final ColorValue CONTROL_HOVER_LIGHT = ColorValue.rgb(255, 255, 255);
    private static final ColorValue STROKE_HOVER_DARK = ColorValue.rgb(52, 52, 58);
    private static final ColorValue STROKE_HOVER_LIGHT = ColorValue.rgb(212, 215, 224);
    private static final long UI_INTRO_MS = 620L;
    private static final float UI_ANIMATION_SPEED = 14.0f;

    private static final ColorValue FOOTER_BASE = ColorValue.rgb(124, 125, 134);
    private static final ColorValue FOOTER_HIGHLIGHT = ColorValue.rgb(224, 225, 232);

    private static final float FONT_FOOTER = 11.0f;

    private static final float PREVIEW_ROTATION_SPEED = 0.6f;
    private static final float PREVIEW_MODEL_Y_OFFSET = -15.0f;
    private static final float PREVIEW_SHADOW_BASE_WIDTH_FACTOR = 0.72f;
    private static final float PREVIEW_SHADOW_BASE_HEIGHT = 16.0f;
    private static final Identifier PREVIEW_SHADOW_TEXTURE = Identifier.of("minecraft", "textures/misc/shadow.png");

    private static final Radii ALT_PANEL_RADII = Radii.of(14.0f);
    private static final Radii ALT_CONTROL_RADII = Radii.of(10.0f);

    private static final ColorValue ALT_PANEL_STROKE = ColorValue.rgb(34, 33, 36);
    private static final ColorValue ALT_CONTROL_FILL = ColorValue.rgb(23, 23, 24);
    private static final ColorValue ALT_CONTROL_STROKE = ColorValue.rgb(34, 33, 36);
    private static final ColorValue ALT_TEXT_MUTED = ColorValue.rgb(124, 125, 134);
    private static final ColorValue ALT_TEXT_MAIN = ColorValue.rgb(224, 225, 232);

    private static final float ALT_PANEL_WIDTH = 412.0f;
    private static final float ALT_PANEL_HEIGHT = 312.0f;
    private static final float ALT_PANEL_MARGIN_LEFT = 16.0f;
    private static final float ALT_PANEL_MARGIN_BOTTOM = 28.0f;
    private static final float ALT_PANEL_INSET = 6.0f;

    private static final float ALT_TOP_ROW_HEIGHT = 34.0f;
    private static final float ALT_INPUT_WIDTH = 212.0f;
    private static final float ALT_SWAP_WIDTH = 43.0f;
    private static final float ALT_CREATE_WIDTH = 137.0f;
    private static final float ALT_GAP = 4.0f;

    private static final float ALT_BOTTOM_BUTTON_HEIGHT = 41.0f;
    private static final float ALT_BOTTOM_BUTTON_GAP = 5.0f;
    private static final float ALT_ROW_HEIGHT = 52.0f;

    private static final float PLAY_PANEL_WIDTH = 280.0f;
    private static final float PLAY_PANEL_MARGIN_RIGHT = 28.0f;
    private static final float PLAY_PANEL_MARGIN_BOTTOM = 28.0f;
    private static final float PLAY_PANEL_INSET = 6.0f;
    private static final float PLAY_BUTTON_HEIGHT = 46.0f;
    private static final float PLAY_BUTTON_GAP = 6.0f;

    private static final DateTimeFormatter CREATED_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private boolean previewDragging;
    private boolean previewMouseDown;
    private double previewLastMouseX;
    private float previewYRotation;

    private String usernameInput = "";
    private boolean usernameFocused;
    private int usernameCursorIndex;

    private float altScroll;
    private int activeAccountIndex = -1;

    private static boolean lightThemeEnabled;
    private boolean renderLightTheme;
    private long screenOpenedAt;
    private long lastAnimationFrameAt;
    private float switchVisualProgress;
    private float settingsHoverProgress;
    private float optionsHoverProgress;
    private float quitHoverProgress;
    private float swapHoverProgress;
    private float createHoverProgress;
    private float bottomHoverProgress;
    private float singleplayerHoverProgress;
    private float multiplayerHoverProgress;

    private Bound settingsChipBound;
    private Bound optionsChipBound;
    private Bound quitChipBound;
    private Bound altPanelBound;
    private Bound altInputBound;
    private Bound altSwapBound;
    private Bound altCreateBound;
    private Bound altListBound;
    private Bound altBottomSelectBound;
    private Bound singleplayerButtonBound;
    private Bound multiplayerButtonBound;

    public MenuUI()
    {
        super(Text.literal("celebression"));
        long now = System.currentTimeMillis();
        screenOpenedAt = now;
        lastAnimationFrameAt = now;
        renderLightTheme = lightThemeEnabled;
        switchVisualProgress = lightThemeEnabled ? 1.0f : 0.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        MenuDrawBridge.begin(context);
        try {
            Bound root = rootBound();
            Bound root2 = rootBound2();
            updateUiAnimations(root, mouseX, mouseY);
            renderBackgroundFill(root);
            renderThemedFrame(root, root2, mouseX, mouseY, lightThemeEnabled);
        } finally {
            MenuDrawBridge.end();
        }
        renderPlayerPreviewModel(context, rootBound2(), mouseX, mouseY);
    }

    public static GameWindowPort fromWindow()
    {
        return new MinecraftWindowAdapter(MinecraftClientProvider.getWindow());
    }

    static boolean isSharedLightThemeEnabled()
    {
        return lightThemeEnabled;
    }

    static void setSharedLightThemeEnabled(boolean enabled)
    {
        lightThemeEnabled = enabled;
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

    private void renderThemedFrame(Bound root, Bound root2, int mouseX, int mouseY, boolean lightTheme)
    {
        renderLightTheme = lightTheme;
        renderTopBar(root, mouseX, mouseY);
        renderPlayerPreviewShadow(playerPreviewBound(root2));
        renderFooter(root);
        renderPlayPanel(mouseX, mouseY);
        renderAltManager(mouseX, mouseY);
    }

    private void renderBackgroundFill(Bound root)
    {
        PrimitiveUseCase.rectangle(root, Radii.zero(), backgroundColor());
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return false;
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
            return true;
        }

        if (optionsChipBound != null && optionsChipBound.contains(x, y))
        {
            openOptionsScreen();
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

        if (singleplayerButtonBound != null && singleplayerButtonBound.contains(x, y))
        {
            openSingleplayerScreen();
            return true;
        }

        if (multiplayerButtonBound != null && multiplayerButtonBound.contains(x, y))
        {
            openMultiplayerScreen();
            return true;
        }

        if (altPanelBound == null) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        if (!altPanelBound.contains(x, y))
        {
            usernameFocused = false;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        if (altInputBound != null && altInputBound.contains(x, y))
        {
            usernameFocused = true;
            usernameCursorIndex = Math.min(usernameInput.length(), usernameCursorIndexForClick(x));
            return true;
        }

        usernameFocused = false;

        if (altSwapBound != null && altSwapBound.contains(x, y))
        {
            MenuConfigBridge.reload();
            return true;
        }

        if (altCreateBound != null && altCreateBound.contains(x, y))
        {
            createAccountFromInput();
            return true;
        }

        if (altBottomSelectBound != null && altBottomSelectBound.contains(x, y))
        {
            selectActiveAccount();
            return true;
        }

        if (altListBound != null && altListBound.contains(x, y))
        {
            int idx = accountIndexAt(y);
            if (idx >= 0)
            {
                Bound rowButton = selectButtonBoundAtIndex(idx);
                if (rowButton != null && rowButton.contains(x, y))
                {
                    selectAccountAtIndex(idx);
                }
                else
                {
                    activeAccountIndex = idx;
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (!usernameFocused) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            usernameFocused = false;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER)
        {
            createAccountFromInput();
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE)
        {
            if (usernameCursorIndex > 0 && !usernameInput.isEmpty())
            {
                usernameInput = usernameInput.substring(0, usernameCursorIndex - 1) + usernameInput.substring(usernameCursorIndex);
                usernameCursorIndex = Math.max(0, usernameCursorIndex - 1);
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_DELETE)
        {
            if (usernameCursorIndex < usernameInput.length())
            {
                usernameInput = usernameInput.substring(0, usernameCursorIndex) + usernameInput.substring(usernameCursorIndex + 1);
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_LEFT)
        {
            usernameCursorIndex = Math.max(0, usernameCursorIndex - 1);
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_RIGHT)
        {
            usernameCursorIndex = Math.min(usernameInput.length(), usernameCursorIndex + 1);
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_HOME)
        {
            usernameCursorIndex = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_END)
        {
            usernameCursorIndex = usernameInput.length();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers)
    {
        if (usernameFocused && chr >= 32 && chr != 127)
        {
            if (usernameInput.length() >= 32) {
                return true;
            }
            if (!isAllowedUsernameChar(chr)) {
                return true;
            }
            int idx = MathHelper.clamp(usernameCursorIndex, 0, usernameInput.length());
            usernameInput = usernameInput.substring(0, idx) + chr + usernameInput.substring(idx);
            usernameCursorIndex = idx + 1;
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (altListBound == null) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        float x = toFramebufferX((float) mouseX);
        float y = toFramebufferY((float) mouseY);
        if (!altListBound.contains(x, y)) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        altScroll = Math.max(0.0f, altScroll - (float) scrollY * ui(ALT_ROW_HEIGHT * 0.65f));
        clampAltScroll();
        return true;
    }

    @Override
    public void close()
    {
    }

    private Bound rootBound()
    {
        GameWindowPort window = fromWindow();
        return Bound.of(0, 0, window.getFramebufferWidth(), window.getFramebufferHeight());
    }

    private Bound rootBound2()
    {
        GameWindowPort window = fromWindow();

        int w = window.getScaledWidth();
        int h = window.getScaledHeight();

        return Bound.of(0, 0, w, h);
    }

    private void renderTopBar(Bound root, int mouseX, int mouseY)
    {
        float titleSize = ui(14.0f);
        float logoSize = ui(16.0f);
        float logoY = ui(16.0f) + topBarIntroOffset();
        float textY = logoY + (logoSize - Fonts.ROBOTO_FLEX_REGULAR.getHeight(titleSize)) / 2.0f;
        TextureUseCase.draw(
                Identifier.of("celebression_ui", "textures/ui/logo.png"),
                Bound.of(ui(16.0f), logoY, logoSize, logoSize),
                Radii.zero(),
                logoTintColor()
        );
        Fonts.ROBOTO_FLEX_REGULAR.draw("celebression", ui(16.0f) + logoSize + ui(8.0f), textY, titleSize, textMainColor().toRGBA());
        renderSettingsChip(root);
        renderOptionsChip(root);
        renderQuitChip(root, mouseX, mouseY);
    }

    private void renderPlayPanel(int mouseX, int mouseY)
    {
        float mx = toFramebufferX(mouseX);
        float my = toFramebufferY(mouseY);

        Bound panel = playPanelLayout();

        PrimitiveUseCase.strokeInside(panel, altPanelRadii(), ui(1.0f), panelStrokeColor());

        singleplayerButtonBound = singleplayerButtonLayout(panel);
        multiplayerButtonBound = multiplayerButtonLayout(singleplayerButtonBound);

        renderPlayButton(singleplayerButtonBound, MenuLanguageState.text("menu.singleplayer"), singleplayerHoverProgress, mx, my);
        renderPlayButton(multiplayerButtonBound, MenuLanguageState.text("menu.multiplayer"), multiplayerHoverProgress, mx, my);
    }

    private void renderPlayButton(Bound bound, String label, float hoverProgress, float mouseX, float mouseY)
    {
        PrimitiveUseCase.rectangle(bound, altControlRadii(), controlFillColor(hoverProgress));
        PrimitiveUseCase.strokeInside(bound, altControlRadii(), ui(1.0f), controlStrokeColor(hoverProgress));

        float fontSize = fitFontSize(label, ui(14.0f), ui(9.0f), bound.width - ui(24.0f));
        String display = truncateToWidth(label, bound.width - ui(24.0f), fontSize);
        int color = textColor(hoverProgress).toRGBA();
        Fonts.ROBOTO_FLEX_REGULAR.draw(
                display,
                bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(display, fontSize) / 2.0f,
                bound.centerY() - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize) / 2.0f + ui(1.0f),
                fontSize,
                color
        );
    }

    private void renderAltManager(int mouseX, int mouseY)
    {
        float mx = toFramebufferX(mouseX);
        float my = toFramebufferY(mouseY);

        Bound panel = altPanelLayout();
        altPanelBound = panel;

        PrimitiveUseCase.strokeInside(panel, altPanelRadii(), ui(1.0f), panelStrokeColor());

        altInputBound = altInputLayout(panel);
        altSwapBound = altSwapLayout(altInputBound);
        altCreateBound = altCreateLayout(altSwapBound);

        renderAltInput(altInputBound, mx, my);
        renderAltSwap(altSwapBound, mx, my);
        renderAltCreate(altCreateBound, mx, my);

        altBottomSelectBound = altBottomSelectLayout(panel);

        float listTop = altInputBound.bottom() + ui(10.0f);
        float listBottom = altBottomSelectBound.y - ui(10.0f);
        altListBound = Bound.of(panel.x + ui(ALT_PANEL_INSET), listTop, panel.width - ui(ALT_PANEL_INSET * 2.0f), listBottom - listTop);

        renderAltList(altListBound, mx, my);
        renderAltBottomButton(altBottomSelectBound, mx, my);
    }

    private void renderAltInput(Bound bound, float mouseX, float mouseY)
    {
        PrimitiveUseCase.rectangle(bound, altControlRadii(), controlFillColor());
        PrimitiveUseCase.strokeInside(bound, altControlRadii(), ui(1.0f), controlStrokeColor());

        String content = usernameInput == null ? "" : usernameInput;
        float fontSize = ui(13.0f);
        float padX = ui(12.0f);

        boolean showPlaceholder = !usernameFocused && content.isBlank();
        String text = showPlaceholder ? MenuLanguageState.text("menu.username_placeholder") : content;
        ColorValue color = showPlaceholder ? textMutedColor() : textMainColor();

        float available = Math.max(0.0f, bound.width - padX * 2.0f);
        String display = truncateToWidth(text, available, fontSize);
        float textY = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f + ui(1.0f);
        Fonts.ROBOTO_FLEX_REGULAR.draw(display, bound.x + padX, textY, fontSize, color.toRGBA());

        if (usernameFocused && (System.currentTimeMillis() / 500L) % 2L == 0L)
        {
            int cursor = MathHelper.clamp(usernameCursorIndex, 0, content.length());
            String before = content.substring(0, cursor);
            float cursorX = bound.x + padX + Math.min(Fonts.ROBOTO_FLEX_REGULAR.getWidth(before, fontSize), available);
            PrimitiveUseCase.rectangle(Bound.of(cursorX, bound.y + ui(8.0f), ui(1.2f), bound.height - ui(16.0f)), Radii.of(ui(1.0f)), textMainColor());
        }
    }

    private void renderAltSwap(Bound bound, float mouseX, float mouseY)
    {
        PrimitiveUseCase.rectangle(bound, altControlRadii(), controlFillColor(swapHoverProgress));
        PrimitiveUseCase.strokeInside(bound, altControlRadii(), ui(1.0f), controlStrokeColor(swapHoverProgress));
        float fontSize = ui(16.0f);
        String icon = "⟳";
        int color = textColor(swapHoverProgress).toRGBA();
        float x = bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(icon, fontSize) / 2.0f;
        float y = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f + ui(1.0f);
        Fonts.ROBOTO_FLEX_REGULAR.draw(icon, x, y, fontSize, color);
    }

    private void renderAltCreate(Bound bound, float mouseX, float mouseY)
    {
        PrimitiveUseCase.rectangle(bound, altControlRadii(), controlFillColor(createHoverProgress));
        PrimitiveUseCase.strokeInside(bound, altControlRadii(), ui(1.0f), controlStrokeColor(createHoverProgress));

        String label = MenuLanguageState.text("menu.create_account");
        float fontSize = fitFontSize(label, ui(13.0f), ui(9.5f), bound.width - ui(18.0f));
        String display = truncateToWidth(label, bound.width - ui(18.0f), fontSize);
        int color = textColor(createHoverProgress).toRGBA();
        float x = bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(display, fontSize) / 2.0f;
        float y = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f + ui(1.0f);
        Fonts.ROBOTO_FLEX_REGULAR.draw(display, x, y, fontSize, color);
    }

    private void renderAltList(Bound bound, float mouseX, float mouseY)
    {
        clampAltScroll();
        List<Account> accounts = accounts();

        float viewTop = bound.y;
        float viewBottom = bound.bottom();

        float y = viewTop - altScroll;
        for (int i = 0; i < accounts.size(); i++)
        {
            Account account = accounts.get(i);
            Bound row = Bound.of(bound.x, y, bound.width, ui(ALT_ROW_HEIGHT));
            if (row.bottom() >= viewTop && row.y <= viewBottom)
            {
                renderAltRow(i, account, row, mouseX, mouseY);
            }
            y += ui(ALT_ROW_HEIGHT);
        }
    }

    private void renderAltRow(int index, Account account, Bound row, float mouseX, float mouseY)
    {
        float avatarSize = ui(34.0f);
        Bound avatar = Bound.of(row.x + ui(6.0f), row.y + (row.height - avatarSize) / 2.0f, avatarSize, avatarSize);
        renderAccountHead(account.username(), avatar);

        boolean selected = isSelected(account.username());
        Bound button = selectButtonBound(row, selected);
        float nameX = avatar.right() + ui(5.0f);
        float nameSize = ui(14.0f);
        float nameMaxWidth = Math.max(ui(24.0f), button.x - nameX - ui(10.0f));
        Fonts.ROBOTO_FLEX_REGULAR.draw(truncateToWidth(account.username(), nameMaxWidth, nameSize), nameX, row.y + ui(10.0f), nameSize, textMainColor().toRGBA());

        String created = MenuLanguageState.text("menu.created_prefix") + " " + Instant.ofEpochMilli(account.createdAtMillis())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(CREATED_FORMAT);
        float createdSize = ui(10.0f);
        Fonts.ROBOTO_FLEX_REGULAR.draw(truncateToWidth(created, nameMaxWidth, createdSize), nameX, row.y + ui(27.0f), createdSize, textMutedColor().toRGBA());

        boolean hover = button.contains(mouseX, mouseY);

        PrimitiveUseCase.rectangle(button, Radii.of(999.0f), controlFillColor());
        PrimitiveUseCase.strokeInside(button, Radii.of(999.0f), ui(1.0f), controlStrokeColor());

        String label = selected ? MenuLanguageState.text("menu.selected_account") : MenuLanguageState.text("menu.select_account");
        float fontSize = fitFontSize(label, ui(10.0f), ui(7.0f), button.width - ui(12.0f));
        String display = truncateToWidth(label, button.width - ui(12.0f), fontSize);
        int color = (selected ? textMainColor() : (hover ? textMainColor() : textMutedColor())).toRGBA();

        Fonts.ROBOTO_FLEX_REGULAR.draw(
                display,
                button.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(display, fontSize) / 2.0f,
                button.centerY() - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize) / 2.0f + ui(1.0f),
                fontSize,
                color
        );
    }

    private void renderAltBottomButton(Bound bound, float mouseX, float mouseY)
    {
        PrimitiveUseCase.rectangle(bound, altControlRadii(), controlFillColor(bottomHoverProgress));
        PrimitiveUseCase.strokeInside(bound, altControlRadii(), ui(1.0f), controlStrokeColor(bottomHoverProgress));

        String label = MenuLanguageState.text("menu.select_account_bottom");
        float fontSize = fitFontSize(label, ui(14.0f), ui(8.5f), Math.max(0.0f, bound.width - ui(28.0f)));
        int color = textColor(bottomHoverProgress).toRGBA();
        Fonts.ROBOTO_FLEX_REGULAR.draw(
                label,
                bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(label, fontSize) / 2.0f,
                bound.centerY() - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize) / 2.0f + ui(1.0f),
                fontSize,
                color
        );
    }

    private float toFramebufferX(int mouseX)
    {
        return toFramebufferX((float) mouseX);
    }

    private float toFramebufferY(int mouseY)
    {
        return toFramebufferY((float) mouseY);
    }

    private float toFramebufferX(float mouseX)
    {
        GameWindowPort window = fromWindow();
        float scaleX = window.getScaledWidth() <= 0 ? 1.0f : (float) window.getFramebufferWidth() / (float) window.getScaledWidth();
        return mouseX * scaleX;
    }

    private float toFramebufferY(float mouseY)
    {
        GameWindowPort window = fromWindow();
        float scaleY = window.getScaledHeight() <= 0 ? 1.0f : (float) window.getFramebufferHeight() / (float) window.getScaledHeight();
        return mouseY * scaleY;
    }

    private int usernameCursorIndexForClick(float framebufferX)
    {
        if (altInputBound == null) {
            return usernameInput.length();
        }
        float fontSize = ui(13.0f);
        float padX = ui(12.0f);
        float x = framebufferX - (altInputBound.x + padX);
        if (x <= 0.0f) {
            return 0;
        }
        String content = usernameInput == null ? "" : usernameInput;
        int idx = 0;
        float w = 0.0f;
        while (idx < content.length())
        {
            float next = Fonts.ROBOTO_FLEX_REGULAR.getWidth(content.substring(0, idx + 1), fontSize);
            if (next >= x) {
                break;
            }
            w = next;
            idx++;
        }
        if (idx == content.length() && w < x) {
            return content.length();
        }
        return idx;
    }

    private void createAccountFromInput()
    {
        String raw = usernameInput;
        boolean added = MenuAccountBridge.add(raw);
        if (added)
        {
            MenuAccountBridge.select(raw);
            usernameInput = "";
            usernameCursorIndex = 0;
            usernameFocused = false;
            requestAltSave();
            clampAltScroll();
        }
    }

    private void selectActiveAccount()
    {
        int idx = activeAccountIndex;
        if (idx < 0) {
            return;
        }
        selectAccountAtIndex(idx);
    }

    private void selectAccountAtIndex(int idx)
    {
        List<Account> accounts = accounts();
        if (idx < 0 || idx >= accounts.size()) {
            return;
        }
        Account account = accounts.get(idx);
        MenuAccountBridge.select(account.username());
        requestAltSave();
    }

    private void renderAccountHead(String username, Bound bound)
    {
        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        GameProfile profile = SkinProfileService.getInstance().profile(username);
        SkinTextures skin = client.getSkinProvider().getSkinTextures(profile);
        Identifier texture = skin.texture();

        PrimitiveUseCase.rectangle(
                Bound.of(bound.x + ui(3.0f), bound.y + ui(2.0f), bound.width - ui(4.0f), bound.height - ui(4.0f)),
                Radii.of(999.0f),
                avatarBackdropColor()
        );

        TextureRegion headRegion = TextureRegion.of(
                8f / 64f,
                8f / 64f,
                16f / 64f,
                16f / 64f
        );
        TextureUseCase.draw(
                texture,
                Bound.of(bound.x + ui(3.0f), bound.y + ui(2.0f), bound.width - ui(4.0f), bound.height - ui(4.0f)),
                headRegion,
                Radii.of(ui(12.0f)),
                1.0f,
                ColorValue.rgb(255, 255, 255)
        );
    }

    private void clampAltScroll()
    {
        float contentH = accounts().size() * ui(ALT_ROW_HEIGHT);
        float viewH = altListBound == null ? 0.0f : altListBound.height;
        float max = Math.max(0.0f, contentH - viewH);
        altScroll = MathHelper.clamp(altScroll, 0.0f, max);
    }

    private List<Account> accounts()
    {
        return MenuAccountBridge.accounts();
    }

    private boolean isSelected(String username)
    {
        String selected = MenuAccountBridge.selectedUsername();
        return selected != null && selected.equalsIgnoreCase(username);
    }

    private int accountIndexAt(float framebufferY)
    {
        if (altListBound == null) {
            return -1;
        }
        float local = framebufferY - altListBound.y + altScroll;
        if (local < 0.0f) {
            return -1;
        }
        int idx = (int) (local / ui(ALT_ROW_HEIGHT));
        List<Account> accounts = accounts();
        return idx >= 0 && idx < accounts.size() ? idx : -1;
    }

    private Bound selectButtonBoundAtIndex(int idx)
    {
        if (altListBound == null) {
            return null;
        }
        float rowY = altListBound.y + idx * ui(ALT_ROW_HEIGHT) - altScroll;
        List<Account> accounts = accounts();
        boolean selected = idx < accounts.size() && isSelected(accounts.get(idx).username());
        return selectButtonBound(Bound.of(altListBound.x, rowY, altListBound.width, ui(ALT_ROW_HEIGHT)), selected);
    }

    private Bound selectButtonBound(Bound row)
    {
        return selectButtonBound(row, false);
    }

    private Bound selectButtonBound(Bound row, boolean selected)
    {
        float w = ui(selected ? 100.0f : 86.0f);
        float h = ui(23.0f);
        return Bound.of(row.right() - w - ui(12.0f), row.centerY() - h / 2.0f - ui(1.0f), w, h);
    }

    private static boolean isAllowedUsernameChar(char c)
    {
        return c == '_' || c == '-' || Character.isLetterOrDigit(c);
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
        float eW = Fonts.ROBOTO_FLEX_REGULAR.getWidth(ellipsis, fontSize);
        if (eW >= maxWidth) {
            return "";
        }

        int end = text.length();
        while (end > 0)
        {
            String sub = text.substring(0, end);
            float w = Fonts.ROBOTO_FLEX_REGULAR.getWidth(sub, fontSize);
            if (w + eW <= maxWidth) {
                return sub + ellipsis;
            }
            end--;
        }
        return "";
    }

    private void requestAltSave()
    {
        MenuConfigBridge.saveAsync();
    }

    private void openOptionsScreen()
    {
        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        if (client != null) {
            client.setScreen(new OptionsScreen(this, client.options));
        }
    }

    private void openSingleplayerScreen()
    {
        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        if (client != null) {
            client.setScreen(new SelectWorldScreen(this));
        }
    }

    private void openMultiplayerScreen()
    {
        MinecraftClient client = MinecraftClientProvider.getMinecraft();
        if (client != null) {
            client.setScreen(new MultiplayerScreen(this));
        }
    }

    private void updateUiAnimations(Bound root, int mouseX, int mouseY)
    {
        long now = System.currentTimeMillis();
        float dt = Math.min(0.05f, Math.max(0.001f, (now - lastAnimationFrameAt) / 1000.0f));
        lastAnimationFrameAt = now;

        float fbX = toFramebufferX(mouseX);
        float fbY = toFramebufferY(mouseY);

        switchVisualProgress = animateTowards(switchVisualProgress, lightThemeEnabled ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        settingsHoverProgress = animateTowards(settingsHoverProgress, settingsChipLayout(root).contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        optionsHoverProgress = animateTowards(optionsHoverProgress, optionsChipLayout(root).contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        quitHoverProgress = animateTowards(quitHoverProgress, quitChipLayout(root).contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);

        Bound panel = altPanelLayout();
        Bound input = altInputLayout(panel);
        Bound swap = altSwapLayout(input);
        Bound create = altCreateLayout(swap);
        Bound bottom = altBottomSelectLayout(panel);
        swapHoverProgress = animateTowards(swapHoverProgress, swap.contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        createHoverProgress = animateTowards(createHoverProgress, create.contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        bottomHoverProgress = animateTowards(bottomHoverProgress, bottom.contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);

        Bound playPanel = playPanelLayout();
        Bound singleplayer = singleplayerButtonLayout(playPanel);
        Bound multiplayer = multiplayerButtonLayout(singleplayer);
        singleplayerHoverProgress = animateTowards(singleplayerHoverProgress, singleplayer.contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
        multiplayerHoverProgress = animateTowards(multiplayerHoverProgress, multiplayer.contains(fbX, fbY) ? 1.0f : 0.0f, dt, UI_ANIMATION_SPEED);
    }

    private float uiIntroProgress()
    {
        float t = MathHelper.clamp((System.currentTimeMillis() - screenOpenedAt) / (float) UI_INTRO_MS, 0.0f, 1.0f);
        float inverse = 1.0f - t;
        return 1.0f - inverse * inverse * inverse;
    }

    private float uiElementAlpha()
    {
        return uiIntroProgress();
    }

    private float topBarIntroOffset()
    {
        return (1.0f - uiIntroProgress()) * ui(14.0f);
    }

    private float panelIntroOffset()
    {
        return (1.0f - uiIntroProgress()) * ui(22.0f);
    }

    private float footerIntroOffset()
    {
        return (1.0f - uiIntroProgress()) * ui(16.0f);
    }

    private float previewIntroOffset()
    {
        return (1.0f - uiIntroProgress()) * 20.0f;
    }

    private Bound settingsChipLayout(Bound root)
    {
        float chipH = ui(26.0f);
        float chipW = ui(53.0f);
        float rightOffset = ui(QUIT_CHIP_MARGIN_RIGHT + 8.0f + QUIT_CHIP_WIDTH + QUIT_CHIP_GAP) + optionsChipWidth() + ui(QUIT_CHIP_GAP);
        return Bound.anchorRight(root, ui(PADDING) + (ui(TOP_BAR_HEIGHT) - chipH) / 2.0f - ui(16.0f) + topBarIntroOffset(), chipW, chipH, rightOffset);
    }

    private Bound optionsChipLayout(Bound root)
    {
        float chipH = ui(QUIT_CHIP_HEIGHT);
        float rightOffset = ui(QUIT_CHIP_MARGIN_RIGHT + 8.0f + QUIT_CHIP_WIDTH + QUIT_CHIP_GAP);
        return Bound.anchorRight(root, ui(PADDING) + (ui(TOP_BAR_HEIGHT) - chipH) / 2.0f - ui(16.0f) + topBarIntroOffset(), optionsChipWidth(), chipH, rightOffset);
    }

    private Bound quitChipLayout(Bound root)
    {
        float chipH = ui(QUIT_CHIP_HEIGHT);
        return Bound.anchorRight(root, ui(PADDING) + (ui(TOP_BAR_HEIGHT) - chipH) / 2.0f - ui(16.0f) + topBarIntroOffset(), ui(QUIT_CHIP_WIDTH), chipH, ui(QUIT_CHIP_MARGIN_RIGHT + 8.0f));
    }

    private float optionsChipWidth()
    {
        String label = MenuLanguageState.text("menu.settings");
        return Math.max(ui(OPTIONS_CHIP_MIN_WIDTH), Fonts.ROBOTO_FLEX_REGULAR.getWidth(label, ui(12.0f)) + ui(OPTIONS_CHIP_PADDING_X * 2.0f));
    }

    private Bound altPanelLayout()
    {
        GameWindowPort window = fromWindow();
        int h = window.getFramebufferHeight();
        return Bound.of(
                ui(ALT_PANEL_MARGIN_LEFT),
                h - ui(ALT_PANEL_HEIGHT) - ui(ALT_PANEL_MARGIN_BOTTOM) + panelIntroOffset(),
                ui(ALT_PANEL_WIDTH),
                ui(ALT_PANEL_HEIGHT)
        );
    }

    private Bound altInputLayout(Bound panel)
    {
        return Bound.of(panel.x + ui(ALT_PANEL_INSET), panel.y + ui(ALT_PANEL_INSET), ui(ALT_INPUT_WIDTH), ui(ALT_TOP_ROW_HEIGHT));
    }

    private Bound altSwapLayout(Bound input)
    {
        return Bound.of(input.right() + ui(ALT_GAP), input.y, ui(ALT_SWAP_WIDTH), ui(ALT_TOP_ROW_HEIGHT));
    }

    private Bound altCreateLayout(Bound swap)
    {
        return Bound.of(swap.right() + ui(ALT_GAP), swap.y, ui(ALT_CREATE_WIDTH), ui(ALT_TOP_ROW_HEIGHT));
    }

    private Bound altBottomSelectLayout(Bound panel)
    {
        return Bound.of(
                panel.x + ui(ALT_PANEL_INSET),
                panel.bottom() - ui(ALT_BOTTOM_BUTTON_HEIGHT) - ui(ALT_BOTTOM_BUTTON_GAP),
                panel.width - ui(ALT_PANEL_INSET * 2.0f),
                ui(ALT_BOTTOM_BUTTON_HEIGHT)
        );
    }

    private Bound playPanelLayout()
    {
        GameWindowPort window = fromWindow();
        float panelW = ui(PLAY_PANEL_WIDTH);
        float panelH = ui(PLAY_PANEL_INSET * 2.0f + PLAY_BUTTON_HEIGHT * 2.0f + PLAY_BUTTON_GAP);
        float x = window.getFramebufferWidth() - ui(PLAY_PANEL_MARGIN_RIGHT) - panelW;
        float y = window.getFramebufferHeight() - ui(PLAY_PANEL_MARGIN_BOTTOM) - panelH + panelIntroOffset();
        return Bound.of(x, y, panelW, panelH);
    }

    private Bound singleplayerButtonLayout(Bound panel)
    {
        return Bound.of(
                panel.x + ui(PLAY_PANEL_INSET),
                panel.y + ui(PLAY_PANEL_INSET),
                panel.width - ui(PLAY_PANEL_INSET * 2.0f),
                ui(PLAY_BUTTON_HEIGHT)
        );
    }

    private Bound multiplayerButtonLayout(Bound singleplayer)
    {
        return Bound.of(
                singleplayer.x,
                singleplayer.bottom() + ui(PLAY_BUTTON_GAP),
                singleplayer.width,
                ui(PLAY_BUTTON_HEIGHT)
        );
    }

    private float settingsChipKnobSize()
    {
        return ui(24.0f);
    }

    private float settingsChipKnobLeft(Bound chip, boolean lightTheme)
    {
        return settingsChipKnobLeft(chip, lightTheme ? 1.0f : 0.0f);
    }

    private float settingsChipKnobLeft(Bound chip, float lightProgress)
    {
        float knobSize = settingsChipKnobSize();
        float darkLeft = chip.right() - knobSize - ui(1.0f);
        float lightLeft = chip.x + ui(1.0f);
        return darkLeft + (lightLeft - darkLeft) * MathHelper.clamp(lightProgress, 0.0f, 1.0f);
    }

    private float settingsChipKnobCenterX(Bound chip, boolean lightTheme)
    {
        return settingsChipKnobLeft(chip, lightTheme) + settingsChipKnobSize() * 0.5f;
    }

    private float settingsChipKnobCenterX(Bound chip, float lightProgress)
    {
        return settingsChipKnobLeft(chip, lightProgress) + settingsChipKnobSize() * 0.5f;
    }

    private float settingsChipKnobCenterY(Bound chip)
    {
        return chip.y + ui(1.0f) + settingsChipKnobSize() * 0.5f;
    }

    private void renderSettingsChip(Bound root)
    {
        Bound bound = settingsChipLayout(root);
        settingsChipBound = bound;

        PrimitiveUseCase.rectangle(bound, CHIP_RADII, mixColor(settingsChipFillColor(), controlHoverSurfaceColor(), settingsHoverProgress * 0.55f));
        PrimitiveUseCase.strokeInside(bound, CHIP_RADII, ui(1.0f), mixColor(settingsChipStrokeColor(), controlHoverStrokeColor(), settingsHoverProgress));

        float knobSize = settingsChipKnobSize();
        float knobX = settingsChipKnobLeft(bound, switchVisualProgress);
        PrimitiveUseCase.rectangle(Bound.of(knobX, bound.y + ui(1.0f), knobSize, knobSize), CHIP_RADII, settingsChipKnobColor());

        Bound whiteThemeIcon = Bound.of(bound.x + ui(9.0f), bound.centerY() - ui(6.27f), ui(12.54f), ui(12.54f));
        Bound darkThemeIcon = Bound.of(bound.right() - ui(19.0f), bound.centerY() - ui(5.04f), ui(10.08f), ui(10.08f));

        TextureUseCase.draw(
                Identifier.of("celebression_ui", "textures/ui/white.png"),
                whiteThemeIcon,
                Radii.zero(),
                themeIconColor(true)
        );
        TextureUseCase.draw(
                Identifier.of("celebression_ui", "textures/ui/black.png"),
                darkThemeIcon,
                Radii.zero(),
            themeIconColor(false)
        );
    }

    private void renderOptionsChip(Bound root)
    {
        String label = MenuLanguageState.text("menu.settings");
        Bound bound = optionsChipLayout(root);
        optionsChipBound = bound;

        float fontSize = fitFontSize(label, ui(12.0f), ui(8.0f), Math.max(0.0f, bound.width - ui(OPTIONS_CHIP_PADDING_X * 2.0f)));
        String display = truncateToWidth(label, bound.width - ui(OPTIONS_CHIP_PADDING_X * 2.0f), fontSize);

        PrimitiveUseCase.rectangle(bound, CHIP_RADII, controlFillColor(optionsHoverProgress));
        PrimitiveUseCase.strokeInside(bound, CHIP_RADII, ui(1.0f), controlStrokeColor(optionsHoverProgress));

        float textX = bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(display, fontSize) * 0.5f;
        float textY = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) * 0.5f;
        Fonts.ROBOTO_FLEX_REGULAR.draw(display, textX, textY, fontSize, textColor(optionsHoverProgress).toRGBA());
    }

    private void renderQuitChip(Bound root, int mouseX, int mouseY)
    {
        String label = MenuLanguageState.text("menu.quit");
        Bound bound = quitChipLayout(root);
        quitChipBound = bound;
        float fontSize = fitFontSize(label, ui(12.0f), ui(8.0f), Math.max(0.0f, bound.width - ui(QUIT_CHIP_PADDING_X * 2.0f)));
        String display = truncateToWidth(label, bound.width - ui(QUIT_CHIP_PADDING_X * 2.0f), fontSize);

        PrimitiveUseCase.rectangle(bound, CHIP_RADII, quitFillColor(quitHoverProgress));
        float textX = bound.centerX() - Fonts.ROBOTO_FLEX_REGULAR.getWidth(display, fontSize) * 0.5f;
        float textY = bound.y + (bound.height - Fonts.ROBOTO_FLEX_REGULAR.getHeight(fontSize)) / 2.0f;
        Fonts.ROBOTO_FLEX_REGULAR.draw(display, textX, textY, fontSize, quitTextColor(quitHoverProgress).toRGBA());
    }

    private void renderFooter(Bound root)
    {
        String line1 = MenuLanguageState.text("menu.footer_line1");
        String line2 = MenuLanguageState.text("menu.footer_line2");

        float footerSize = ui(FONT_FOOTER);
        float h = Fonts.ROBOTO_FLEX_REGULAR.getHeight(footerSize);
        float y2 = root.bottom() - ui(PADDING) - h + footerIntroOffset();
        float y1 = y2 - h - ui(8.0f);

        float footerMaxWidth = Math.max(ui(120.0f), root.width - ui(PADDING * 2.0f));
        footerSize = Math.min(
                fitFontSize(line1, footerSize, ui(8.0f), footerMaxWidth),
                fitFontSize(line2, footerSize, ui(8.0f), footerMaxWidth)
        );
        h = Fonts.ROBOTO_FLEX_REGULAR.getHeight(footerSize);
        y2 = root.bottom() - ui(PADDING) - h + footerIntroOffset();
        y1 = y2 - h - ui(8.0f);

        float w1 = Fonts.ROBOTO_FLEX_REGULAR.getWidth(line1, footerSize);
        float w2 = Fonts.ROBOTO_FLEX_REGULAR.getWidth(line2, footerSize);

        TextValue t1 = TextValue.of(line1, footerBaseColor());

        TextValue t2 = TextValue.of(line2, footerBaseColor());
        String privacy = MenuLanguageState.text("menu.privacy_policy");
        int privacyStart = line2.indexOf(privacy);
        t2.range(privacyStart, privacyStart + privacy.length() - 1, footerHighlightColor());

        String terms = MenuLanguageState.text("menu.terms_of_service");
        int termsStart = line2.indexOf(terms);
        t2.range(termsStart, termsStart + terms.length() - 1, footerHighlightColor());

        Fonts.ROBOTO_FLEX_REGULAR.drawColored(t1, root.centerX() - w1 / 2.0f, y1, footerSize);
        Fonts.ROBOTO_FLEX_REGULAR.drawColored(t2, root.centerX() - w2 / 2.0f, y2, footerSize);
    }

    private Bound playerPreviewBound(Bound root)
    {
        float w = 85.0f;
        float h = 135.0f;

        return Bound.of(
                root.centerX() - w / 2f,
                root.centerY() - h / 2f + 10.0f + previewIntroOffset(),
                w,
                h
        );
    }

    private void renderPlayerPreviewModel(DrawContext context, Bound root, int mouseX, int mouseY)
    {
        Bound preview = playerPreviewBound(root);
        Bound box = Bound.of(preview.x, preview.y + PREVIEW_MODEL_Y_OFFSET, preview.width, preview.height);

        updatePreviewRotation(box, mouseX, mouseY);

        float danceTime = (System.currentTimeMillis() - screenOpenedAt) / 1000.0f;

        VanillaGuiPreviewRenderer.drawPlayerPreviewRotated(
                context,
                (int) box.x,
                (int) box.y,
                (int) (box.x + box.width),
                (int) (box.y + box.height),
                0.0f,
                previewYRotation,
                danceTime
        );
    }

    private void renderPlayerPreviewShadow(Bound previewBound)
    {
        GameWindowPort window = fromWindow();
        float scaleX = window.getScaledWidth() <= 0 ? 1.0f : (float) window.getFramebufferWidth() / (float) window.getScaledWidth();
        float scaleY = window.getScaledHeight() <= 0 ? 1.0f : (float) window.getFramebufferHeight() / (float) window.getScaledHeight();

        float baseW = previewBound.width * PREVIEW_SHADOW_BASE_WIDTH_FACTOR;
        float baseH = PREVIEW_SHADOW_BASE_HEIGHT;
        float baseX = previewBound.centerX() - baseW * 0.5f;
        float baseY = previewBound.bottom() - baseH * 0.55f;

        Bound shadowBound = Bound.of(baseX, baseY + 4.0f, baseW, baseH);
        Bound shadowFramebufferBound = Bound.of(
                shadowBound.x * scaleX,
                shadowBound.y * scaleY,
                shadowBound.width * scaleX,
                shadowBound.height * scaleY
        );
        TextureUseCase.draw(Identifier.of("celebression_ui", "textures/ui/shadow.png"), shadowFramebufferBound, Radii.zero(), previewShadowColor());
    }

    private void updatePreviewRotation(Bound previewBound, int mouseX, int mouseY)
    {
        long handle = MinecraftClientProvider.getWindow().getHandle();
        boolean mouseDown = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;

        if (!previewMouseDown && mouseDown && previewBound.contains(mouseX, mouseY))
        {
            previewDragging = true;
            previewLastMouseX = mouseX;
        }

        if (previewDragging && mouseDown)
        {
            double deltaX = mouseX - previewLastMouseX;
            previewLastMouseX = mouseX;
            previewYRotation = wrapAngleDegrees(previewYRotation + (float) deltaX * PREVIEW_ROTATION_SPEED);
        }

        if (!mouseDown) {
            previewDragging = false;
        }

        previewMouseDown = mouseDown;
    }

    private static float wrapAngleDegrees(float value)
    {
        float wrapped = value % 360.0f;
        if (wrapped < 0.0f) {
            wrapped += 360.0f;
        }
        return wrapped;
    }

    private boolean isLightTheme()
    {
        return renderLightTheme;
    }

    private ColorValue backgroundColor()
    {
        return isLightTheme() ? MENU_BACKGROUND_LIGHT : MENU_BACKGROUND_DARK;
    }

    private ColorValue panelStrokeColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_OUTLINE_LIGHT : ALT_PANEL_STROKE);
    }

    private ColorValue controlFillColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_CONTROL_FILL_LIGHT : ALT_CONTROL_FILL);
    }

    private ColorValue controlFillColor(float emphasis)
    {
        return mixColor(controlFillColor(), controlHoverSurfaceColor(), emphasis);
    }

    private ColorValue controlStrokeColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_OUTLINE_LIGHT : ALT_CONTROL_STROKE);
    }

    private ColorValue controlStrokeColor(float emphasis)
    {
        return mixColor(controlStrokeColor(), controlHoverStrokeColor(), emphasis);
    }

    private ColorValue textMainColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_TEXT_LIGHT : ALT_TEXT_MAIN);
    }

    private ColorValue textMutedColor()
    {
        return withUiAlpha(ALT_TEXT_MUTED);
    }

    private ColorValue textColor(float emphasis)
    {
        return mixColor(textMutedColor(), textMainColor(), emphasis);
    }

    private ColorValue logoTintColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_TEXT_LIGHT : ColorValue.rgb(255, 255, 255));
    }

    private ColorValue settingsChipFillColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_CONTROL_FILL_LIGHT : ColorValue.rgb(23, 24, 24));
    }

    private ColorValue settingsChipStrokeColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_OUTLINE_LIGHT : ColorValue.rgb(34, 33, 36));
    }

    private ColorValue settingsChipKnobColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_CHIP_KNOB_LIGHT : ColorValue.rgb(34, 33, 36));
    }

    private ColorValue controlHoverSurfaceColor()
    {
        return withUiAlpha(isLightTheme() ? CONTROL_HOVER_LIGHT : CONTROL_HOVER_DARK);
    }

    private ColorValue controlHoverStrokeColor()
    {
        return withUiAlpha(isLightTheme() ? STROKE_HOVER_LIGHT : STROKE_HOVER_DARK);
    }

    private ColorValue activeThemeIconColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_TEXT_LIGHT : ALT_TEXT_MAIN);
    }

    private ColorValue inactiveThemeIconColor()
    {
        return withUiAlpha(isLightTheme() ? ALT_TEXT_MUTED : ColorValue.rgb(89, 90, 97));
    }

    private ColorValue themeIconColor(boolean lightIcon)
    {
        float emphasis = lightIcon ? switchVisualProgress : 1.0f - switchVisualProgress;
        return mixColor(inactiveThemeIconColor(), activeThemeIconColor(), emphasis);
    }

    private ColorValue quitFillColor(float hoverProgress)
    {
        ColorValue base = withUiAlpha(isLightTheme() ? MENU_QUIT_FILL_LIGHT : ColorValue.rgb(104, 28, 28));
        return mixColor(base, withUiAlpha(QUIT_FILL_HOVER), hoverProgress);
    }

    private ColorValue quitTextColor(float hoverProgress)
    {
        ColorValue base = withUiAlpha(isLightTheme() ? MENU_QUIT_TEXT_LIGHT : ColorValue.rgb(204, 64, 64));
        return mixColor(base, withUiAlpha(ColorValue.rgb(255, 255, 255)), hoverProgress * 0.18f);
    }

    private ColorValue footerBaseColor()
    {
        return withUiAlpha(FOOTER_BASE);
    }

    private ColorValue footerHighlightColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_TEXT_LIGHT : FOOTER_HIGHLIGHT);
    }

    private ColorValue previewShadowColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_PREVIEW_SHADOW_LIGHT : ColorValue.rgba(16, 16, 16, 170));
    }

    private ColorValue avatarBackdropColor()
    {
        return withUiAlpha(isLightTheme() ? MENU_CHIP_KNOB_LIGHT : ColorValue.rgb(34, 33, 36));
    }

    private float animateTowards(float current, float target, float dt, float speed)
    {
        float factor = 1.0f - (float) Math.exp(-speed * dt);
        return current + (target - current) * factor;
    }

    private float ui(float value)
    {
        return value * MenuUiMetrics.uiScale(fromWindow());
    }

    private Radii altPanelRadii()
    {
        return Radii.of(ui(14.0f));
    }

    private Radii altControlRadii()
    {
        return Radii.of(ui(10.0f));
    }

    private float fitFontSize(String text, float preferredSize, float minSize, float maxWidth)
    {
        return MenuUiMetrics.fitFontSize(Fonts.ROBOTO_FLEX_REGULAR, text, preferredSize, minSize, maxWidth);
    }

    private ColorValue withUiAlpha(ColorValue color)
    {
        return withAlpha(color, uiElementAlpha());
    }

    private ColorValue withAlpha(ColorValue color, float factor)
    {
        if (color == null) {
            return null;
        }
        int rgba = color.toRGBA();
        int alpha = rgba >>> 24;
        return color.alphaRGBA(Math.round(alpha * MathHelper.clamp(factor, 0.0f, 1.0f)));
    }

    private ColorValue mixColor(ColorValue from, ColorValue to, float t)
    {
        float clamped = MathHelper.clamp(t, 0.0f, 1.0f);
        int a = from == null ? 0 : from.toRGBA();
        int b = to == null ? 0 : to.toRGBA();

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
}
