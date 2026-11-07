package ru.method.celebression.integration.ui.screens.dropdown.impl;

import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.modules.setting.BooleanSetting;
import ru.method.celebression.integration.ui.screens.dropdown.SettingRenderContext;
import ru.method.celebression.integration.ui.screens.dropdown.SettingRenderer;
import net.minecraft.client.gui.DrawContext;

public final class BooleanSettingRenderer implements SettingRenderer<BooleanSetting> {

    public static final int HEIGHT = 12;

    @Override
    public void render(DrawContext ctx, BooleanSetting bs, int x, int y, int w, SettingRenderContext env) {
        FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), bs.getName(), x + 0.5f, y + 1, env.textPrimary());
        env.renderToggle(ctx, x + w - 16, y, bs.get(), bs);
    }

    @Override
    public boolean mouseClicked(BooleanSetting bs, int button, double mouseX, double mouseY, int x, int y, int w, SettingRenderContext env) {
        bs.set(!bs.get());
        return true;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
