package ru.method.celebression.hud;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public class InventoryHud
extends HudElement {
    private static final float jz = 18.0f;
    private static final float jA = 16.0f;
    private static final float jB = 0.9f;
    private static final float jC = 2.0f;
    private static final float jD = 7.0f;
    private static final float jE = 7.0f;
    private static final float jF = 1.0f;
    private static final int jG = 9;
    private final BooleanSetting jH = new BooleanSetting("settings.inventoryhud.hotbar", false);

    public InventoryHud() {
        super("InventoryHud");
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (InventoryHud.fullNullCheck() || this.bL()) {
            return;
        }
        boolean bl = HudEditorScreen.mb();
        boolean bl2 = (Boolean)this.jH.jT();
        int n = bl2 ? 4 : 3;
        float f = 192.0f;
        float f2 = 14.0f + (float)n * 18.0f + (float)(n - 1) * 2.0f + (bl2 ? 7.0f : 0.0f);
        float f3 = this.getX();
        float f4 = this.getY();
        float f5 = this.b(hudRenderEvent.j().method_51448(), f3, f4);
        hudRenderEvent.j().method_51448().method_22903();
        float f6 = this.gn.rk() * this.bJ();
        float f7 = f3 + f / 2.0f;
        float f8 = f4 + f2 / 2.0f;
        hudRenderEvent.j().method_51448().method_46416(f7, f8, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f6, f6, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f7, -f8, 0.0f);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f3, f4, f, f2, HudThemeRenderer.f(f, f2), bl ? 1.0f : 0.92f);
        } else {
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f3, f4, f, f2, new BorderRadius(10.0f), new ColorRGBA(ColorUtils.getGlassColor(bl ? 235 : 210)), 2.8f, 24.0f, 12.0f, 180.0f, true, 0.0f, 0.22f);
        }
        if (bl2) {
            float f9 = f4 + 7.0f + 54.0f + 4.0f + 3.5f;
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f3 + 7.0f + 3.0f, f9, f - 14.0f - 6.0f, 1.0f, 1.0f, new Color(255, 255, 255, bl ? 85 : 60));
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < 9; ++j) {
                int n2 = i < 3 ? 9 + i * 9 + j : j;
                float f10 = this.i(f3 + 7.0f + (float)j * 20.0f);
                float f11 = this.i(f4 + 7.0f + (float)i * 20.0f + (bl2 && i == 3 ? 7.0f : 0.0f));
                float f12 = f10 + 1.0f;
                float f13 = f11 + 1.0f;
                if (HudThemeRenderer.active()) {
                    HudThemeRenderer.c(hudRenderEvent.j().method_51448(), f10, f11, 18.0f, 18.0f, 5.0f, 1.0f);
                } else {
                    Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f12, f13, 16.0f, 16.0f, 4.0f, new Color(255, 255, 255, 24));
                }
                class_1799 class_17992 = InventoryHud.aiG.field_1724.method_31548().method_5438(n2);
                if (class_17992.method_7960()) continue;
                this.b(hudRenderEvent, class_17992, f12, f13);
            }
        }
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f3, f4, f, f2, f5);
        super.onRender2D(hudRenderEvent);
    }

    private void b(HudRenderEvent hudRenderEvent, class_1799 class_17992, float f, float f2) {
        try {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.enableDepthTest();
            int n = Math.round(f);
            int n2 = Math.round(f2);
            hudRenderEvent.j().method_51448().method_22903();
            float f3 = 0.8000002f;
            hudRenderEvent.j().method_51448().method_46416((float)n + f3, (float)n2 + f3, 0.0f);
            hudRenderEvent.j().method_51448().method_22905(0.9f, 0.9f, 1.0f);
            hudRenderEvent.j().method_51423((class_1309)InventoryHud.aiG.field_1724, class_17992, 0, 0, 0);
            hudRenderEvent.j().method_51431(InventoryHud.aiG.field_1772, class_17992, 0, 0);
            hudRenderEvent.j().method_51448().method_22909();
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        catch (Exception exception) {
        }
    }

    private float i(float f) {
        return Math.round(f);
    }
}

