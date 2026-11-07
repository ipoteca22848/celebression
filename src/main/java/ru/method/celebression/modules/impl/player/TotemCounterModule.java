package ru.method.celebression.modules.impl.player;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2371;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class TotemCounterModule
extends Module {
    private int count;

    public TotemCounterModule() {
        super("TotemCounter", ModuleCategory.qm);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (TotemCounterModule.fullNullCheck()) {
            this.count = 0;
            return;
        }
        this.count = this.a((class_2371<class_1799>)TotemCounterModule.aiG.field_1724.method_31548().field_7547) + this.a((class_2371<class_1799>)TotemCounterModule.aiG.field_1724.method_31548().field_7544) + this.a((class_2371<class_1799>)TotemCounterModule.aiG.field_1724.method_31548().field_7548);
    }

    @EventHandler
    private void onRender2D(HudRenderEvent hudRenderEvent) {
        if (TotemCounterModule.fullNullCheck()) {
            return;
        }
        String string = Integer.toString(this.count);
        float f = 8.0f;
        float f2 = (float)aiG.method_22683().method_4486() / 2.0f;
        float f3 = (float)aiG.method_22683().method_4502() / 2.0f;
        float f4 = f2 + 10.0f;
        float f5 = f3 + 8.0f;
        float f6 = f4 + 18.0f;
        float f7 = f5 + 4.5f - Fonts.ROUNDBOLD.getHeight(f) / 2.0f;
        try {
            RenderSystem.enableDepthTest();
            hudRenderEvent.j().method_51423((class_1309)TotemCounterModule.aiG.field_1724, new class_1799((class_1935)class_1802.field_8288), (int)f4, (int)f5, 0);
            RenderSystem.disableDepthTest();
        }
        catch (Exception exception) {
        }
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(f), string, f6, f7, new Color(255, 255, 255, 255));
    }

    public int hS() {
        return this.count;
    }

    private int a(class_2371<class_1799> class_23712) {
        int n = 0;
        for (class_1799 class_17992 : class_23712) {
            if (!class_17992.method_31574(class_1802.field_8288)) continue;
            n += class_17992.method_7947();
        }
        return n;
    }
}

