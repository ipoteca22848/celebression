package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.modules.impl.player.FunTimeHelperModule;
import ru.method.celebression.modules.impl.player.HolyWorldHelperModule;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.module.Module;
import ru.method.celebression.modules.impl.player.ReallyWorldHelperModule;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class HelperItemsHud
extends HudElement {
    private static final float jg = 18.0f;
    private static final float jh = 6.0f;
    private static final float ji = 0.875f;
    private static final float jj = 6.5f;
    private static final float jk = 6.5f;
    private static final float jl = 1.2f;
    private final AnimatedComponent jm = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jn = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jo = new AnimatedComponent(Easing.aiZ);
    private static final Set<Class<? extends Module>> jp = Set.of(FunTimeHelperModule.class, HolyWorldHelperModule.class, ReallyWorldHelperModule.class);
    private static final List<ItemEntry> jq = List.of(new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.bindsnowball", new class_1799((class_1935)class_1802.field_8543)), new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.binddesor", new class_1799((class_1935)class_1802.field_8449)), new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.bindbozhka", new class_1799((class_1935)class_1802.field_8614)), new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.bindyavka", new class_1799((class_1935)class_1802.field_8479)), new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.bindtrapka", new class_1799((class_1935)class_1802.field_22021)), new ItemEntry(FunTimeHelperModule.class, "settings.funtimehelper.bindplast", new class_1799((class_1935)class_1802.field_8551)), new ItemEntry(HolyWorldHelperModule.class, "settings.hwhelper.bindstan", new class_1799((class_1935)class_1802.field_8137)), new ItemEntry(HolyWorldHelperModule.class, "settings.hwhelper.bindtrapka", new class_1799((class_1935)class_1802.field_8882)), new ItemEntry(HolyWorldHelperModule.class, "settings.hwhelper.bindboomtrapka", new class_1799((class_1935)class_1802.field_8662)), new ItemEntry(HolyWorldHelperModule.class, "settings.hwhelper.bindboomthing", new class_1799((class_1935)class_1802.field_8814)), new ItemEntry(HolyWorldHelperModule.class, "settings.hwhelper.bindsnowball", new class_1799((class_1935)class_1802.field_8543)), new ItemEntry(ReallyWorldHelperModule.class, "settings.rwhelper.bindantifly", new class_1799((class_1935)class_1802.field_8450)), new ItemEntry(ReallyWorldHelperModule.class, "settings.rwhelper.bindexpscroll", new class_1799((class_1935)class_1802.field_8498)), new ItemEntry(ReallyWorldHelperModule.class, "settings.rwhelper.bindtrap", new class_1799((class_1935)class_1802.field_8207)), new ItemEntry(ReallyWorldHelperModule.class, "settings.rwhelper.binddarkpulse", new class_1799((class_1935)class_1802.field_8450)));
    private List<SlotLayout> jr;
    private long js;
    private boolean jt;

    public HelperItemsHud() {
        super("HelperItemsHud");
    }

    private List<SlotLayout> g(boolean bl) {
        Module module;
        ArrayList<SlotLayout> arrayList = new ArrayList<SlotLayout>();
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return arrayList;
        }
        HashSet<Class<? extends Module>> hashSet = new HashSet<Class<? extends Module>>();
        for (Class<? extends Module> object : jp) {
            module = celebression.getModuleManager().getModule(object);
            if (module == null || !module.isToggled()) continue;
            hashSet.add(object);
        }
        for (ItemEntry itemEntry : jq) {
            boolean bl2;
            BindSetting bindSetting;
            if (bl && !hashSet.isEmpty() && !hashSet.contains(itemEntry.cF()) || (module = celebression.getModuleManager().getModule(itemEntry.cF())) == null || !module.isToggled() && !bl || !module.isToggled() && bl || (bindSetting = this.a(module, itemEntry.cG())) == null) continue;
            KeyBind keyBind = (KeyBind)bindSetting.jT();
            boolean bl3 = bl2 = keyBind == null || keyBind.isEmpty();
            if (!bl && bl2 || !bl && InventoryUtils.h(itemEntry.stack().method_7909()) == -1) continue;
            String string = !bl2 ? HelperItemsHud.ax(keyBind.toString()) : "";
            arrayList.add(new SlotLayout(itemEntry.stack(), string));
        }
        if (bl && arrayList.isEmpty() && hashSet.isEmpty()) {
            arrayList.add(new SlotLayout(new class_1799((class_1935)class_1802.field_8543), "R"));
            arrayList.add(new SlotLayout(new class_1799((class_1935)class_1802.field_8557), "V"));
            arrayList.add(new SlotLayout(new class_1799((class_1935)class_1802.field_8137), "Z"));
            arrayList.add(new SlotLayout(new class_1799((class_1935)class_1802.field_8397), "X"));
        }
        return arrayList;
    }

    private BindSetting a(Module module, String string) {
        if (module == null || string == null) {
            return null;
        }
        for (Setting<?> setting : module.getSettings()) {
            if (!(setting instanceof BindSetting) || !string.equals(setting.getName())) continue;
            return (BindSetting)setting;
        }
        return null;
    }

    private boolean a(boolean bl, List<SlotLayout> list) {
        if (bl) {
            return true;
        }
        return !list.isEmpty();
    }

    private static String ax(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.replace("_", " ").trim();
        String string3 = string2.toUpperCase();
        if (string3.contains("LEFT BRACKET")) {
            return "[";
        }
        if (string3.contains("RIGHT BRACKET")) {
            return "]";
        }
        if (string3.contains("SEMICOLON")) {
            return ";";
        }
        if (string3.contains("APOSTROPHE")) {
            return "'";
        }
        if (string3.contains("COMMA")) {
            return ",";
        }
        if (string3.contains("PERIOD")) {
            return ".";
        }
        if (string3.contains("SLASH")) {
            return "/";
        }
        if (string3.contains("BACKSLASH")) {
            return "\\";
        }
        if (string3.contains("GRAVE")) {
            return "`";
        }
        if (string3.contains("MINUS")) {
            return "-";
        }
        if (string3.contains("EQUAL")) {
            return "=";
        }
        return string2;
    }

    private static String a(String string, float f) {
        if (string == null) {
            return "";
        }
        String label = string.trim();
        if (label.isEmpty()) {
            return "";
        }
        if (Fonts.MEDIUM.getWidth(label, 6.5f) <= f) {
            return label;
        }
        String string2 = label.toUpperCase();
        if (string2.contains("MOUSE") && string2.matches(".*\\d+.*")) {
            String digits = string2.replaceAll("\\D+", "");
            String mouseLabel = digits.isEmpty() ? "M" : "M" + digits;
            if (Fonts.MEDIUM.getWidth(mouseLabel, 6.5f) <= f) {
                return mouseLabel;
            }
        }
        String[] words = label.split("\\s+");
        if (words.length > 1) {
            StringBuilder initials = new StringBuilder(words.length);
            for (String word : words) {
                if (word.isEmpty()) continue;
                initials.append(Character.toUpperCase(word.charAt(0)));
            }
            String string3 = initials.toString();
            if (!string3.isEmpty() && Fonts.MEDIUM.getWidth(string3, 6.5f) <= f) {
                return string3;
            }
            label = string3.isEmpty() ? label : string3;
        }
        return HelperItemsHud.a(Fonts.MEDIUM, label, 6.5f, f);
    }

    private void a(HudRenderEvent hudRenderEvent, String string, float f, float f2, Color color) {
        if (string == null || string.isBlank()) {
            return;
        }
        float f3 = 15.6f;
        String string2 = HelperItemsHud.a(string, f3);
        if (string2.isEmpty()) {
            return;
        }
        float f4 = Fonts.MEDIUM.getWidth(string2, 6.5f);
        this.a(hudRenderEvent.j(), Fonts.MEDIUM, 6.5f, string2, f - f4 / 2.0f, f2, color);
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        Color color;
        List<SlotLayout> list;
        float f;
        if (HelperItemsHud.fullNullCheck() || this.bL()) {
            return;
        }
        boolean bl = HudEditorScreen.mb();
        long l = System.currentTimeMillis();
        if (this.jr == null || bl != this.jt || l >= this.js) {
            this.jr = this.g(bl);
            this.jt = bl;
            this.js = l + 100L;
        }
        if ((f = this.jo.a(this.a(bl, list = this.jr) ? 1.0f : 0.0f, 200L)) <= 0.0f) {
            return;
        }
        float f2 = this.getX();
        float f3 = this.getY();
        float f4 = 6.0f;
        float f5 = list.isEmpty() ? 0.0f : (float)list.size() * 18.0f + (float)(list.size() - 1) * 6.0f;
        float f6 = 24.5f;
        float f7 = f4 * 2.0f + f5;
        float f8 = f4 * 2.0f + f6;
        float f9 = bl ? f7 : this.jm.a(f7, 200L);
        float f10 = bl ? f8 : this.jn.a(f8, 200L);
        int n = Math.max(0, Math.min(255, Math.round(255.0f * f)));
        float f11 = this.b(hudRenderEvent.j().method_51448(), f2, f3);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f12 = this.gn.rk() * f * this.bJ();
        float f13 = f2 + f9 / 2.0f + fArray[0];
        float f14 = f3 + f10 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f13, f14, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f12, f12, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f13, -f14, 0.0f);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f2, f3, f9, f10, HudThemeRenderer.f(f9, f10), (float)n / 255.0f);
        } else {
            color = ColorUtils.getGlassColor(n);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f2, f3, f9, f10, new BorderRadius(10.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        color = ColorUtils.getTextColor(Math.min(230, n));
        float f15 = f2 + (f9 - f5) / 2.0f;
        float f16 = f3 + (f10 - f6) / 2.0f;
        for (int i = 0; i < list.size(); ++i) {
            SlotLayout slotLayout = list.get(i);
            float f17 = f15 + (float)i * 24.0f;
            float f18 = f16;
            if (HudThemeRenderer.active()) {
                HudThemeRenderer.c(hudRenderEvent.j().method_51448(), f17, f18, 18.0f, 18.0f, 5.0f, 1.0f);
            } else {
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f17, f18, 18.0f, 18.0f, 5.0f, new Color(255, 255, 255, 26));
            }
            float f19 = f17 + 9.0f;
            float f20 = f18 + 9.0f;
            int n2 = Math.round(f19 - 8.0f);
            int n3 = Math.round(f20 - 8.0f);
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            class_45872.method_22903();
            class_45872.method_46416(f19, f20, 0.0f);
            class_45872.method_22905(0.875f, 0.875f, 1.0f);
            class_45872.method_46416(-f19, -f20, 0.0f);
            try {
                RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                RenderSystem.enableDepthTest();
                hudRenderEvent.j().method_51423((class_1309)HelperItemsHud.aiG.field_1724, slotLayout.stack(), n2, n3, 0);
                RenderSystem.disableDepthTest();
                RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
            catch (Exception exception) {
            }
            class_45872.method_22909();
            this.a(hudRenderEvent, slotLayout.cl(), f17 + 9.0f, f18 + 18.0f + 1.25f, color);
        }
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f2, f3, f9, f10, f11);
        super.onRender2D(hudRenderEvent);
    }

    static final class ItemEntry {
        private final Class<? extends Module> ju;
        private final String jv;
        private final class_1799 jw;

        ItemEntry(Class<? extends Module> clazz, String string, class_1799 class_17992) {
            this.ju = clazz;
            this.jv = string;
            this.jw = class_17992;
        }

        @Override
        public final String toString() {
            return "ItemEntry[" + "moduleClass=" + this.ju + ", " + "settingName=" + this.jv + ", " + "stack=" + this.jw + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ju, this.jv, this.jw);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ItemEntry other)) return false;
            return java.util.Objects.deepEquals(this.ju, other.ju)
                && java.util.Objects.deepEquals(this.jv, other.jv)
                && java.util.Objects.deepEquals(this.jw, other.jw);
        }

        public Class<? extends Module> cF() {
            return this.ju;
        }

        public String cG() {
            return this.jv;
        }

        public class_1799 stack() {
            return this.jw;
        }
    }

    static final class SlotLayout {
        private final class_1799 jx;
        private final String jy;

        SlotLayout(class_1799 class_17992, String string) {
            this.jx = class_17992;
            this.jy = string;
        }

        @Override
        public final String toString() {
            return "SlotLayout[" + "stack=" + this.jx + ", " + "keyLabel=" + this.jy + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.jx, this.jy);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof SlotLayout other)) return false;
            return java.util.Objects.deepEquals(this.jx, other.jx)
                && java.util.Objects.deepEquals(this.jy, other.jy);
        }

        public class_1799 stack() {
            return this.jx;
        }

        public String cl() {
            return this.jy;
        }
    }
}
