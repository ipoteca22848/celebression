package ru.method.celebression.integration.ui.screens.dropdown.impl;

import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.modules.setting.BindSetting;
import ru.method.celebression.integration.ui.screens.dropdown.SettingRenderContext;
import ru.method.celebression.integration.ui.screens.dropdown.SettingRenderer;
import net.minecraft.client.gui.DrawContext;

public final class BindSettingRenderer implements SettingRenderer<BindSetting> {

    public static final int HEIGHT = 12;

    @Override
    public void render(DrawContext ctx, BindSetting bs, int x, int y, int w, SettingRenderContext env) {
        FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), bs.getName(), x + 0.5f, y + 1, env.textPrimary());
        String keyName = bs.isBinding() ? "..." : BindSetting.formatBindKeyName(bs.getKey());
        FontUtils.roboto[13].drawLeftAligned(ctx.getMatrices(), keyName, x + w - (int) FontUtils.roboto[13].getWidth(keyName) - 1, y + 1, env.ao(env.accentRgb()));
    }

    @Override
    public boolean mouseClicked(BindSetting bs, int button, double mouseX, double mouseY, int x, int y, int w, SettingRenderContext env) {
        bs.setBinding(true);
        return true;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
