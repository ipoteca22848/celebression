package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1074;
import ru.method.celebression.Celebression;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.module.Module;
import ru.method.celebression.managers.system.ModuleManager;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public class KeyBindsHud
extends HudElement {
    private final AnimatedComponent jI = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jJ = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jK = new AnimatedComponent(Easing.aiZ);
    private List<KeyBindEntry> jL;
    private long cacheExpiresAtMs;
    private boolean jt;

    public KeyBindsHud() {
        super("KeyBindsHud");
    }

    private List<KeyBindEntry> h(boolean bl) {
        ArrayList<KeyBindEntry> arrayList = new ArrayList<KeyBindEntry>();
        ModuleManager moduleManager = Celebression.getInstance().getModuleManager();
        if (moduleManager != null) {
            for (Module module : moduleManager.getModules()) {
                KeyBind keyBind = module.getBind();
                if (module instanceof UI || keyBind == null || keyBind.isEmpty() || !module.isToggled()) continue;
                arrayList.add(new KeyBindEntry(module.getDisplayName(), keyBind.toString(), true));
            }
        }
        if (bl && arrayList.isEmpty()) {
            arrayList.add(new KeyBindEntry("Sprint", "R", true));
            arrayList.add(new KeyBindEntry("Fullbright", "F", true));
            arrayList.add(new KeyBindEntry("DiscordRPC", "G", true));
        }
        return arrayList;
    }

    private boolean a(List<KeyBindEntry> list) {
        if (HudEditorScreen.mb()) {
            return true;
        }
        return !list.isEmpty();
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        float f;
        float f2;
        float f3;
        List<KeyBindEntry> list;
        boolean bl;
        float f4;
        if (KeyBindsHud.fullNullCheck() || this.bL()) {
            return;
        }
        boolean bl2 = HudEditorScreen.mb();
        long l = System.currentTimeMillis();
        if (this.jL == null || bl2 != this.jt || l >= this.cacheExpiresAtMs) {
            this.jL = this.h(bl2);
            this.jt = bl2;
            this.cacheExpiresAtMs = l + 200L;
        }
        if ((f4 = this.jK.a((bl = this.a(list = this.jL)) ? 1.0f : 0.0f, 200L)) <= 0.0f) {
            return;
        }
        float f5 = this.getX();
        float f6 = this.getY();
        float f7 = 6.0f;
        float f8 = 7.0f;
        float f9 = 13.0f;
        float f10 = 7.5f;
        float f11 = 80.0f;
        float f12 = 6.5f;
        float f13 = 4.0f;
        for (KeyBindEntry object2 : list) {
            float font;
            f3 = Fonts.MEDIUM.getWidth(object2.name(), f8);
            f = f3 + (f2 = (font = Fonts.MEDIUM.getWidth(object2.cH(), f12)) + f13 * 2.0f) + f7 * 3.0f;
            if (!(f > f11)) continue;
            f11 = f;
        }
        String string = class_1074.method_4662((String)"elements.settings.elements.keybindshud", (Object[])new Object[0]);
        String string2 = "S";
        f3 = 8.0f;
        Font font = Fonts.ICONSNEW;
        f2 = font.getWidth(string2, f3);
        f = 4.0f;
        float f14 = Fonts.SEMIBOLD.getWidth(string, f10);
        f11 = Math.max(f11, f14 + f + f2 + f7 * 2.0f);
        float f15 = f11 + f7 * 2.0f;
        float f16 = 16.0f + (float)list.size() * f9 + (f7 - 1.0f);
        float f17 = this.jI.a(f15, 200L);
        float f18 = this.jJ.a(f16, 200L);
        if (bl2) {
            f17 = f15;
            f18 = f16;
        }
        float f19 = this.bI();
        float f20 = aiG.method_22683().method_4486();
        float f21 = aiG.method_22683().method_4502();
        f5 = Math.max(0.0f, Math.min(f5, Math.max(0.0f, f20 - f17 * f19)));
        f6 = Math.max(0.0f, Math.min(f6, Math.max(0.0f, f21 - f18 * f19)));
        int n = Math.max(0, Math.min(255, Math.round(255.0f * f4)));
        this.b(hudRenderEvent.j().method_51448(), f5, f6);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f22 = this.gn.rk() * f4 * this.bJ();
        float f23 = f5 + f17 / 2.0f + fArray[0];
        float f24 = f6 + f18 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f23, f24, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f22, f22, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f23, -f24, 0.0f);
        boolean bl3 = HudThemeRenderer.active();
        float f25 = HudThemeRenderer.f(f17 - 2.0f, f18 - 2.0f);
        Color color = ColorUtils.getPrimaryColor();
        if (bl3) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f5, f6, f17 - 2.0f, f18 - 2.0f, f25, f4);
        } else {
            Color f27 = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f5, f6, f17 - 2.0f, f18 - 2.0f, new BorderRadius(10.0f), new ColorRGBA(f27), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        float f26 = f5 + f7;
        float f27 = f5 + f7;
        float f28 = font.getHeight(f3);
        float f29 = f6 + (18.0f - f28) / 2.0f;
        Color color2 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Color color3 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        try {
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f10), string, f27, f6 + f7 - 1.0f, color3);
        }
        catch (Exception f31) {
        }
        float f30 = f5 + f17 - f7 - f2;
        this.a(hudRenderEvent.j(), Fonts.ICONSNEW, f3, string2, f30, f29, color2);
        float f31 = f6 + 18.0f - 2.0f;
        if (bl3) {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f26, f31, f5 + f17 - f7 - f26, 0.8f, 0.4f, ColorUtils.alpha(color, Math.round(110.0f * f4)));
        }
        float f32 = 18.0f;
        Color color4 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Color color5 = ColorUtils.alpha(ColorUtils.mix(ColorUtils.getTextColor(255), ColorUtils.getGlassBase(), 0.35f), n);
        for (KeyBindEntry keyBindEntry : list) {
            float f33 = f6 + f32 + (f9 - f8) / 2.0f - 2.0f;
            String string3 = keyBindEntry.cH();
            float f34 = Fonts.MEDIUM.getWidth(string3, f12);
            if (bl3) {
                float badgeHeight = f9 - 3.0f;
                float badgeWidth = Math.max(badgeHeight, f34 + f13 * 2.0f);
                float f35 = f5 + f17 - f7 - badgeWidth - 1.0f;
                float f36 = f6 + f32 + (f9 - badgeHeight) / 2.0f;
                float f37 = Math.min(HudThemeRenderer.f(badgeWidth, badgeHeight), badgeHeight / 2.0f);
                this.a(hudRenderEvent.j(), Fonts.MEDIUM, f8, KeyBindsHud.a(Fonts.MEDIUM, keyBindEntry.name(), f8, f35 - f26 - 4.0f), f26, f33, color4);
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f35, f36, badgeWidth, badgeHeight, f37, ColorUtils.alpha(color, Math.round(38.0f * f4)));
                Render2D.drawBorder(hudRenderEvent.j().method_51448(), f35, f36, badgeWidth, badgeHeight, f37, 0.8f, new ColorRGBA(ColorUtils.alpha(color, Math.round(90.0f * f4))));
                float f38 = f35 + (badgeWidth - f34) / 2.0f;
                float f39 = f36 + (badgeHeight - Fonts.MEDIUM.getHeight(f12)) / 2.0f;
                try {
                    Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.MEDIUM.getFont(f12), string3, f38, f39, ColorUtils.alpha(color, n));
                }
                catch (Exception exception2) {}
            } else {
                float keyX = f5 + f17 - f7 - f34 - 2.0f;
                this.a(hudRenderEvent.j(), Fonts.MEDIUM, f8, KeyBindsHud.a(Fonts.MEDIUM, keyBindEntry.name(), f8, keyX - f26 - 4.0f), f26, f33, color4);
                try {
                    Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.MEDIUM.getFont(f12), string3, keyX, f33, color5);
                }
                catch (Exception exception) {
                }
            }
            f32 += f9;
        }
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f5, f6, f17, f18, f19);
        super.onRender2D(hudRenderEvent);
    }

    static final class KeyBindEntry {
        private final String jM;
        private final String key;
        private final boolean toggled;

        KeyBindEntry(String string, String string2, boolean bl) {
            this.jM = string;
            this.key = string2;
            this.toggled = bl;
        }

        @Override
        public final String toString() {
            return "KeyBindEntry[" + "name=" + this.jM + ", " + "key=" + this.key + ", " + "toggled=" + this.toggled + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.jM, this.key, this.toggled);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof KeyBindEntry other)) return false;
            return java.util.Objects.deepEquals(this.jM, other.jM)
                && java.util.Objects.deepEquals(this.key, other.key)
                && java.util.Objects.deepEquals(this.toggled, other.toggled);
        }

        public String name() {
            return this.jM;
        }

        public String cH() {
            return this.key;
        }

        public boolean cI() {
            return this.toggled;
        }
    }
}
