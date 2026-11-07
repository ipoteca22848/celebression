package ru.method.celebression.integration.ui.screens.mainmenu.infra;

import ru.method.celebression.integration.ui.manager.IMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;

public final class VanillaGuiPreviewRenderer implements IMinecraft {
    private static final float ENTITY_VERTICAL_OFFSET = 0.0625f;
    private static final float ENTITY_SCALE_FACTOR = 0.62f;

    private VanillaGuiPreviewRenderer() {}

    public static void drawPlayerPreviewRotated(
            DrawContext drawContext,
            int left,
            int top,
            int right,
            int bottom,
            float xRotation,
            float yRotation,
            float animationTimeSeconds
    ) {
        if (drawContext == null || mc.player == null) {
            return;
        }

        float centerX = (left + right) * 0.5f;
        float centerY = (top + bottom) * 0.5f;
        float mouseX = centerX + yRotation * 8.0f;
        float mouseY = centerY + xRotation * 8.0f;
        int size = Math.max(1, Math.round(Math.min(right - left, bottom - top) * ENTITY_SCALE_FACTOR));

        InventoryScreen.drawEntity(
                drawContext,
                left,
                top,
                right,
                bottom,
                size,
                ENTITY_VERTICAL_OFFSET,
                mouseX,
                mouseY,
                mc.player
        );
    }

    public static void drawPlayerPreview(DrawContext drawContext, int left, int top, int right, int bottom, float mouseX, float mouseY) {
        float centerX = (left + right) * 0.5f;
        float centerY = (top + bottom) * 0.5f;
        float xRotation = (centerY - mouseY) * 0.12f;
        float yRotation = (centerX - mouseX) * 0.12f;
        drawPlayerPreviewRotated(drawContext, left, top, right, bottom, xRotation, yRotation, Float.NaN);
    }
}
