package ru.method.celebression.integration.ui.bridge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import ru.method.celebression.service.AppearanceMode;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.integration.ui.util.render.RenderUtil;

/** Compact outlined menu-switch button, also keyboard accessible. */
public final class ThemeSwitch extends ButtonWidget {
    private ThemeSwitch(int width) {
        super(Math.max(4, width - 42), 8, 32, 28, Text.literal("Оформление: " + (AppearanceMode.isCustom() ? "celebression" : "Minecraft")), button -> {
            boolean custom = !AppearanceMode.isCustom();
            if (AppearanceMode.setCustom(custom)) MinecraftClient.getInstance().setScreen(custom ? new CustomMainMenuScreen() : new TitleScreen());
        }, DEFAULT_NARRATION_SUPPLIER);
        setTooltip(net.minecraft.client.gui.tooltip.Tooltip.of(Text.literal("Оформление: " + (AppearanceMode.isCustom() ? "celebression → Minecraft" : "Minecraft → celebression"))));
    }
    public static ButtonWidget button(int width) { return new ThemeSwitch(width); }
    @Override public void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int x = getX(), y = getY();
        RenderUtil.drawRoundedRect(ctx.getMatrices(), x, y, width, height, 9, tint(0xEE142633));
        RenderUtil.drawRoundedBorder(ctx.getMatrices(), x, y, width, height, 9, 1, tint(isHovered() || isFocused() ? 0xFF8ED8FF : 0x8063B5E4));
        int accent = tint(0xFF8ED8FF);
        // Opposing arrows match the supplied compact menu-switch reference.
        ctx.fill(x + 8, y + 9, x + 24, y + 11, accent);
        ctx.fill(x + 21, y + 7, x + 23, y + 9, accent);
        ctx.fill(x + 21, y + 11, x + 23, y + 13, accent);
        ctx.fill(x + 8, y + 17, x + 24, y + 19, accent);
        ctx.fill(x + 9, y + 15, x + 11, y + 17, accent);
        ctx.fill(x + 9, y + 19, x + 11, y + 21, accent);

    }
    private int tint(int color) { return (color & 0xFFFFFF) | (Math.round((color >>> 24) * alpha) << 24); }
}
