package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1735;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2371;
import net.minecraft.class_2480;
import net.minecraft.class_332;
import net.minecraft.class_9288;
import net.minecraft.class_9334;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public class ShulkerTooltipModule
extends Module {
    private static final float wu = 18.0f;
    private static final float wv = 6.0f;
    private static final int ww = 9;
    private static final int wx = 3;
    private final BooleanSetting wy = new BooleanSetting("settings.shulkertooltip.always_on", false);
    private final BindSetting wz = new BindSetting("settings.shulkertooltip.bind", new KeyBind(-1, false), () -> (Boolean)this.wy.jT() == false);
    private boolean wA;
    private boolean wB;

    public ShulkerTooltipModule() {
        super("ShulkerTooltip", ModuleCategory.qm);
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (((KeyBind)this.wz.jT()).af(keyInputEvent.c())) {
            this.wA = keyInputEvent.getAction() != 0;
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (((KeyBind)this.wz.jT()).ag(eventMouse.getButton())) {
            this.wB = eventMouse.getAction() != 0;
            eventMouse.consume();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.wA = false;
        this.wB = false;
    }

    public void a(class_332 class_3322, class_1735 class_17352, int n, int n2) {
        float f;
        if (!(this.isToggled() && class_17352 != null && class_17352.method_7681() && this.hF())) {
            return;
        }
        class_1799 class_17992 = class_17352.method_7677();
        if (!this.p(class_17992)) {
            return;
        }
        List<class_1799> list = this.q(class_17992);
        if (list.isEmpty()) {
            return;
        }
        float f2 = 174.0f;
        float f3 = 66.0f;
        float f4 = class_3322.method_51421();
        float f5 = class_3322.method_51443();
        float f6 = 4.0f;
        float f7 = (float)n - f2 - 12.0f;
        float f8 = (float)n + 12.0f;
        float f9 = f = f7 >= f6 ? f7 : f8;
        if (f + f2 > f4 - f6) {
            f = Math.max(f6, f4 - f2 - f6);
        }
        float f10 = Math.max(f6, Math.min((float)n2 + 12.0f, f5 - f3 - f6));
        Render2D.clearScissor(class_3322);
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(0.0f, 0.0f, 400.0f);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(class_3322.method_51448(), f, f10, f2, f3, HudThemeRenderer.f(f2, f3), 1.0f);
        } else {
            Render2D.drawLiquidRect(class_3322.method_51448(), f, f10, f2, f3, new BorderRadius(8.0f), new ColorRGBA(ColorUtils.getGlassColor(235)), 2.5f, 22.0f, 10.0f, 170.0f, true, 0.0f, 0.2f);
        }
        Color color = new Color(255, 255, 255, 30);
        for (int i = 0; i < 27; ++i) {
            float f11 = f + 6.0f + (float)(i % 9) * 18.0f;
            float f12 = f10 + 6.0f + (float)(i / 9) * 18.0f;
            Render2D.drawRoundedRect(class_3322.method_51448(), f11 + 1.0f, f12 + 1.0f, 16.0f, 16.0f, 4.0f, color);
            if (i >= list.size() || list.get(i).method_7960()) continue;
            int n3 = Math.round(f11 + 1.0f);
            int n4 = Math.round(f12 + 1.0f);
            class_3322.method_51427(list.get(i), n3, n4);
            class_3322.method_51431(ShulkerTooltipModule.aiG.field_1772, list.get(i), n3, n4);
        }
        class_3322.method_51448().method_22909();
    }

    private boolean hF() {
        if (((Boolean)this.wy.jT()).booleanValue()) {
            return true;
        }
        KeyBind keyBind = (KeyBind)this.wz.jT();
        if (keyBind == null || keyBind.isEmpty()) {
            return false;
        }
        return this.wA || this.wB;
    }

    private boolean p(class_1799 class_17992) {
        class_1747 class_17472;
        class_1792 class_17922 = class_17992.method_7909();
        return class_17922 instanceof class_1747 && (class_17472 = (class_1747)class_17922).method_7711() instanceof class_2480;
    }

    private List<class_1799> q(class_1799 class_17992) {
        ArrayList<class_1799> arrayList = new ArrayList<class_1799>();
        class_9288 class_92882 = (class_9288)class_17992.method_57824(class_9334.field_49622);
        if (class_92882 == null) {
            return arrayList;
        }
        class_2371 class_23712 = class_2371.method_10213((int)27, (Object)class_1799.field_8037);
        class_92882.method_57492(class_23712);
        arrayList.addAll((Collection<class_1799>)class_23712);
        return arrayList;
    }
}

