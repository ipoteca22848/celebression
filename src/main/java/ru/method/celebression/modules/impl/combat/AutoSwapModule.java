package ru.method.celebression.modules.impl.combat;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1809;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_481;
import net.minecraft.class_490;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import net.minecraft.class_7923;
import net.minecraft.class_9285;
import net.minecraft.class_9334;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.mixins.accessors.IBoundKey;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.service.ClientTaskScheduler;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.inventory.InventoryHelper;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class AutoSwapModule
extends Module {
    private final BindSetting qA = new BindSetting("settings.autoswap.key", new KeyBind(-1, false));
    private final EnumSetting<SwapState> qB = new EnumSetting<SwapState>("settings.autoswap.mode", SwapState.rn);
    private final EnumSetting<SwapRule> qC = new EnumSetting<SwapRule>("settings.autoswap.firstitem", SwapRule.rk, () -> this.qB.jT() == SwapState.rn);
    private final EnumSetting<SwapRule> qD = new EnumSetting<SwapRule>("settings.autoswap.seconditem", SwapRule.rj, () -> this.qB.jT() == SwapState.rn);
    private final EnumSetting<SwapTarget> qE = new EnumSetting<SwapTarget>("settings.autoswap.wheel_style", SwapTarget.rr, () -> this.qB.jT() == SwapState.ro);
    private final StringSetting[] qF = new StringSetting[5];
    private boolean qG;
    private boolean qH;
    private boolean qI;
    private int qJ = -1;
    private int qK;
    private boolean qL;
    private int qM;
    private boolean qN;
    private int qO;
    private int qP;
    private int qQ;
    private class_465<?> qR;
    private static final int qS = 5;
    private static final int qT = 1;
    private static final int qU = 2;
    private static final int qV = 2;
    private static final float qW = 52.0f;
    private static final float qX = 13.0f;
    private static final float qY = 10.0f;
    private static final float qZ = 8.1f;
    private static final String ra = ";";
    private static final class_2960 rb;
    private static final String rc;
    private static final String rd;
    private static final Pattern re;
    private static final Pattern rf;
    private final List<String> rg = new ArrayList<String>();
    private long rh = -1L;
    private boolean ri;

    public AutoSwapModule() {
        super("AutoSwap", ModuleCategory.qj);
        for (int i = 0; i < this.qF.length; ++i) {
            this.qF[i] = new StringSetting("settings.autoswap.slot" + (i + 1), "", "", "", 256, () -> false, null);
            this.getSettings().add(this.qF[i]);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.qG = false;
        this.qH = false;
        this.qI = false;
        this.qJ = -1;
        this.qO = 0;
        this.qL = false;
        this.qM = 0;
        if (this.qN) {
            this.gd();
        }
        this.qN = false;
        this.qP = 0;
        this.qQ = 0;
        this.qR = null;
        InventoryHelper.rN();
        if (aiG != null && AutoSwapModule.aiG.field_1729 != null && AutoSwapModule.aiG.field_1755 == null) {
            AutoSwapModule.aiG.field_1729.method_1612();
        }
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (AutoSwapModule.fullNullCheck()) {
            return;
        }
        if (((KeyBind)this.qA.jT()).af(keyInputEvent.c())) {
            this.m(keyInputEvent.getAction() != 0);
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (AutoSwapModule.fullNullCheck()) {
            return;
        }
        if (((KeyBind)this.qA.jT()).ag(eventMouse.getButton())) {
            this.m(eventMouse.getAction() != 0);
            return;
        }
        if (this.qB.jT() == SwapState.ro && this.qH && eventMouse.getAction() == 1) {
            this.p(eventMouse.getButton());
            eventMouse.consume();
        }
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (this.qB.jT() != SwapState.ro || !this.qH) {
            return;
        }
        if (this.fQ()) {
            return;
        }
        if (AutoSwapModule.aiG.field_1755 != null) {
            return;
        }
        this.d(hudRenderEvent);
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (AutoSwapModule.aiG.field_1724 == null) {
            return;
        }
        if (InventoryHelper.rM()) {
            if (this.qR != null && AutoSwapModule.aiG.field_1755 == this.qR) {
                InventoryHelper.d(this.qR);
            } else {
                InventoryHelper.rN();
            }
        }
        this.ga();
        this.fX();
        if (this.qB.jT() != SwapState.ro) {
            return;
        }
        if (this.qO > 0) {
            --this.qO;
        }
        if (this.qI && AutoSwapModule.aiG.field_1755 == null && this.qO <= 0) {
            this.qI = false;
            this.qJ = -1;
            AutoSwapModule.aiG.field_1729.method_1612();
        }
        if (this.qH && this.fQ()) {
            this.qH = false;
            this.qG = false;
            if (AutoSwapModule.aiG.field_1755 == null) {
                AutoSwapModule.aiG.field_1729.method_1612();
            }
        }
        if (this.qH && AutoSwapModule.aiG.field_1755 == null) {
            AutoSwapModule.aiG.field_1729.method_1610();
        }
        ++this.qK;
        if (this.qK < 10) {
            return;
        }
        this.qK = 0;
        for (StringSetting stringSetting : this.qF) {
            String string = stringSetting.kg();
            if (string.isEmpty() || this.aH(string)) continue;
            stringSetting.bI("");
        }
    }

    public boolean fO() {
        return this.qI;
    }

    public boolean d(class_1799 class_17992) {
        if (this.qB.jT() != SwapState.ro) {
            return false;
        }
        if (!this.qI || this.qJ < 0 || this.qJ >= this.qF.length) {
            return false;
        }
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        String string = this.e(class_17992);
        if (this.b(string, this.qJ)) {
            ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u042d\u0442\u043e\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u0443\u0436\u0435 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d").method_27692(class_124.field_1061));
            return false;
        }
        this.qF[this.qJ].bI(string);
        this.qI = false;
        this.qJ = -1;
        aiG.method_1507(null);
        return true;
    }

    private void m(boolean bl) {
        if (this.qB.jT() == SwapState.rn) {
            if (bl && !this.qL && AutoSwapModule.aiG.field_1755 == null && !this.fP()) {
                this.qL = true;
                this.fU();
            } else if (!bl) {
                this.qL = false;
            }
            return;
        }
        if (this.fQ()) {
            this.qH = false;
            this.qG = false;
            return;
        }
        if (bl) {
            if (this.qG) {
                return;
            }
            if (AutoSwapModule.aiG.field_1755 != null || this.qI) {
                return;
            }
            if (this.fP()) {
                return;
            }
            this.qG = true;
            this.qH = true;
            AutoSwapModule.aiG.field_1729.method_1610();
        } else {
            String string;
            int n;
            if (!this.qG) {
                return;
            }
            this.qG = false;
            if (!(this.qI || !this.qH || (n = this.fR()) == -1 || (string = this.qF[n].kg()).isEmpty() || this.aF(string) || this.fP())) {
                this.q(n);
            }
            if (!this.qI) {
                this.qH = false;
                if (AutoSwapModule.aiG.field_1755 == null) {
                    AutoSwapModule.aiG.field_1729.method_1612();
                }
            }
        }
    }

    private void p(int n) {
        if (this.fQ()) {
            return;
        }
        int n2 = this.fR();
        if (n2 < 0) {
            return;
        }
        if (n == 1) {
            if (!this.qF[n2].kg().isEmpty()) {
                this.qF[n2].bI("");
            }
            return;
        }
        if (n != 0 && n != 2) {
            return;
        }
        String string = this.qF[n2].kg();
        if (n == 2) {
            if (!(string.isEmpty() || this.aF(string) || this.fP())) {
                this.q(n2);
            }
            return;
        }
        if (string.isEmpty()) {
            this.qI = true;
            this.qJ = n2;
            this.qO = 5;
            this.qH = false;
            if (AutoSwapModule.aiG.field_1724 != null && AutoSwapModule.aiG.field_1724.method_7337()) {
                aiG.method_1507((class_437)new class_481(AutoSwapModule.aiG.field_1724, AutoSwapModule.aiG.field_1687.method_45162(), true));
            } else {
                aiG.method_1507((class_437)new class_490((class_1657)AutoSwapModule.aiG.field_1724));
            }
            return;
        }
        if (!this.aF(string) && !this.fP()) {
            this.q(n2);
        }
        if (!this.qG) {
            this.qH = false;
        }
    }

    private void d(HudRenderEvent hudRenderEvent) {
        float f;
        int n;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        Color color;
        float f7 = (float)aiG.method_22683().method_4486() / 2.0f;
        float f8 = (float)aiG.method_22683().method_4502() / 2.0f;
        float f9 = 71.0f;
        float f10 = Math.max(8.0f, 33.0f);
        Color color2 = ColorUtils.getGlobalColor();
        Color color3 = ColorUtils.mix(color2, Color.WHITE, 0.35f);
        boolean bl = this.qE.jT() == SwapTarget.rs;
        Color color4 = bl ? ColorUtils.getGlassColor(255) : ColorUtils.getGlassColor(170);
        Color color5 = bl ? ColorUtils.getGlassColor(255) : ColorUtils.getGlassColor(140);
        Color color6 = bl ? ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 150) : new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), 90);
        Color color7 = color = bl ? ColorUtils.alpha(ColorUtils.getGlassHighlightColor(), 90) : new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), 40);
        if (bl) {
            f6 = f9 * 2.0f;
            f5 = f7 - f9;
            float f11 = f8 - f9;
            Render2D.drawLiquidRect(hudRenderEvent.j().method_51448(), f5, f11, f6, f6, new BorderRadius(f9), new ColorRGBA(color4), 2.5f, 22.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
            float f12 = f10 * 2.0f;
            float f13 = f7 - f10;
            float f14 = f8 - f10;
            Render2D.drawLiquidRect(hudRenderEvent.j().method_51448(), f13, f14, f12, f12, new BorderRadius(f10), new ColorRGBA(color5), 2.5f, 22.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 - 0.6f, color6);
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f10 + 0.6f, ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 140));
        } else {
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9, color4);
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 - 1.4f, ColorUtils.getGlassColor(120));
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f10, color5);
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f10 - 1.0f, ColorUtils.getGlassColor(100));
        }
        Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 + 4.6f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
        Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 + 3.2f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
        Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 + 2.2f, color);
        Render2D.drawCircle(hudRenderEvent.j().method_51448(), f7, f8, f9 + 0.8f, color6);
        f6 = this.fS();
        f5 = this.fT();
        int n2 = this.d(f6, f5, f7, f8);
        class_1799 class_17992 = AutoSwapModule.aiG.field_1724 == null ? class_1799.field_8037 : AutoSwapModule.aiG.field_1724.method_6079();
        String string = class_17992.method_7960() ? "" : this.e(class_17992);
        int n3 = this.qF.length;
        long l = System.currentTimeMillis();
        for (int i = 0; i < n3; ++i) {
            float f15 = -1.5707964f + (float)Math.PI * 2 / (float)n3 * (float)i + (float)Math.PI * 2 / (float)n3 / 2.0f;
            f4 = f7 + (float)Math.cos(f15) * f10;
            float f16 = f8 + (float)Math.sin(f15) * f10;
            f3 = f7 + (float)Math.cos(f15) * (f9 - 3.0f);
            f2 = f8 + (float)Math.sin(f15) * (f9 - 3.0f);
            Render2D.drawLine(hudRenderEvent.j().method_51448(), f4, f16, f3, f2, 0.9f, new ColorRGBA(255, 255, 255, 28));
        }
        float f17 = f10 + (f9 - f10) * 0.55f;
        int n4 = 6;
        f4 = (float)(l % 8000L) / 8000.0f;
        for (n = 0; n < n4; ++n) {
            f3 = -1.5707964f + (float)Math.PI * 2 / (float)n4 * (float)n + f4 * ((float)Math.PI * 2);
            f2 = f7 + (float)Math.cos(f3) * f17;
            f = f8 + (float)Math.sin(f3) * f17;
            float f18 = 6.0f;
            Render2D.drawTexture(hudRenderEvent.j().method_51448(), f2 - f18 / 2.0f, f - f18 / 2.0f, f18, f18, 2.0f, rb, new Color(255, 255, 255, 200));
        }
        for (n = 0; n < n3; ++n) {
            String string2;
            boolean bl2;
            f3 = -1.5707964f + (float)Math.PI * 2 / (float)n3 * (float)n;
            f2 = f7 + (float)Math.cos(f3) * 52.0f;
            f = f8 + (float)Math.sin(f3) * 52.0f;
            boolean bl3 = n == n2;
            String string3 = this.qF[n].kg();
            boolean bl4 = bl2 = !string3.isEmpty() && string3.equals(string);
            Color color9 = bl ? ColorUtils.getGlassColor(bl3 ? 210 : 175) : (bl3 ? new Color(40, 40, 40, 170) : new Color(25, 25, 25, 150));
            if (bl) {
                float f19 = 26.0f;
                Render2D.drawLiquidRect(hudRenderEvent.j().method_51448(), f2 - 13.0f, f - 13.0f, f19, f19, new BorderRadius(13.0f), new ColorRGBA(color9), 2.5f, 18.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
                Render2D.drawCircle(hudRenderEvent.j().method_51448(), f2, f, 12.6f, ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 150));
            } else {
                Render2D.drawCircle(hudRenderEvent.j().method_51448(), f2, f, 13.0f, color9);
            }
            Color color10 = bl2 ? new Color(235, 70, 70, 210) : (bl3 ? new Color(235, 205, 85, 210) : (bl ? ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 150) : ColorUtils.getGlassColor(110)));
            Render2D.drawCircle(hudRenderEvent.j().method_51448(), f2, f, 11.8f, color10);
            if (string3.isEmpty()) {
                string2 = "+";
                float f20 = Fonts.SEMIBOLD.getWidth(string2, 10.0f);
                float f21 = Fonts.SEMIBOLD.getHeight(10.0f);
                Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(10.0f), string2, f2 - f20 / 2.0f, f - f21 / 2.0f, new Color(255, 255, 255, 220));
                continue;
            }
            class_1799 displayStack = this.aJ(string3);
            if (displayStack.method_7960()) continue;
            int n5 = Math.round(f2 - 8.0f);
            int n6 = Math.round(f - 8.0f);
            hudRenderEvent.j().method_51427(displayStack, n5, n6);
        }
        if (n2 != -1) {
            class_1799 class_17993;
            String string4 = this.qF[n2].kg();
            String string5 = "+";
            if (!string4.isEmpty() && !(class_17993 = this.aJ(string4)).method_7960()) {
                string5 = this.aG(class_17993.method_7964().getString());
            }
            string5 = Render2D.stripObfuscatedFormatting(string5);
            float f22 = 8.1f;
            f = Fonts.MEDIUM.getWidth(string5, f22);
            float f23 = Fonts.MEDIUM.getHeight(f22);
            float f24 = f + 10.0f;
            float f25 = f23 + 6.0f;
            float f26 = f7 - f24 / 2.0f;
            float f27 = f8 - f25 / 2.0f;
            if (bl) {
                Render2D.drawLiquidRect(hudRenderEvent.j().method_51448(), f26, f27, f24, f25, new BorderRadius(4.0f), new ColorRGBA(ColorUtils.getGlassColor(255)), 2.5f, 18.0f, 11.0f, 220.0f, true, 0.0f, 0.19f);
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f26, f27, f24, f25, 4.0f, ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 150));
            } else {
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f26, f27, f24, f25, 3.0f, new Color(0, 0, 0, 140));
            }
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.MEDIUM.getFont(f22), string5, f7 - f / 2.0f, f8 - f23 / 2.0f + 1.0f, new Color(255, 255, 255, 230));
        }
    }

    private boolean aF(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        if (AutoSwapModule.aiG.field_1724 == null) {
            return false;
        }
        class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_6079();
        return !class_17992.method_7960() && string.equals(this.e(class_17992));
    }

    private String aG(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        String string2 = string.replaceAll("\\[[^\\]]*\\]\\s*", "");
        string2 = string2.replace("[", "").replace("]", "");
        string2 = string2.replaceAll("^(?:123\\s*)+", "").replaceAll("(?:\\s*123)+$", "");
        return string2.trim();
    }

    private boolean fP() {
        return AutoSwapModule.aiG.field_1724 != null && AutoSwapModule.aiG.field_1724.method_7337();
    }

    private boolean fQ() {
        return AutoSwapModule.aiG.field_1724 != null && AutoSwapModule.aiG.field_1724.method_7325();
    }

    private boolean b(String string, int n) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.qF.length; ++i) {
            if (i == n || !string.equals(this.qF[i].kg())) continue;
            return true;
        }
        return false;
    }

    private boolean aH(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        if (this.aF(string)) {
            return true;
        }
        return this.aK(string) != -1;
    }

    private int fR() {
        float f = this.fS();
        float f2 = this.fT();
        float f3 = (float)aiG.method_22683().method_4486() / 2.0f;
        float f4 = (float)aiG.method_22683().method_4502() / 2.0f;
        return this.d(f, f2, f3, f4);
    }

    private int d(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float f7 = f5 * f5 + f6 * f6;
        float f8 = 18.199999f;
        if (f7 < f8 * f8) {
            return -1;
        }
        float f9 = (float)Math.toDegrees(Math.atan2(f6, f5));
        int n = -1;
        float f10 = Float.MAX_VALUE;
        for (int i = 0; i < this.qF.length; ++i) {
            float f11 = -90.0f + 360.0f / (float)this.qF.length * (float)i;
            float f12 = Math.abs(class_3532.method_15393((float)(f9 - f11)));
            if (!(f12 < f10)) continue;
            f10 = f12;
            n = i;
        }
        return n;
    }

    private float fS() {
        return (float)(AutoSwapModule.aiG.field_1729.method_1603() * (double)aiG.method_22683().method_4486() / (double)aiG.method_22683().method_4480());
    }

    private float fT() {
        return (float)(AutoSwapModule.aiG.field_1729.method_1604() * (double)aiG.method_22683().method_4502() / (double)aiG.method_22683().method_4507());
    }

    private void q(int n) {
        if (AutoSwapModule.fullNullCheck()) {
            return;
        }
        if (this.fP()) {
            return;
        }
        if (n < 0 || n >= this.qF.length) {
            return;
        }
        String string = this.qF[n].kg();
        int n2 = this.aK(string);
        if (n2 == -1) {
            ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d").method_27692(class_124.field_1061));
            return;
        }
        class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_6079();
        class_1799 class_17993 = AutoSwapModule.aiG.field_1724.method_31548().method_5438(n2);
        class_2561 class_25612 = this.a(class_17992, (class_2561)class_2561.method_43470((String)""));
        class_2561 class_25613 = this.a(class_17993, (class_2561)class_2561.method_43470((String)"\u043d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u043e"));
        float f = AutoSwapModule.aiG.field_1724.method_6029();
        AutoSwapModule.aiG.field_1724.method_6125(0.0f);
        this.r(n2);
        this.gb();
        AutoSwapModule.aiG.field_1724.method_6125(f);
        this.a(class_25612, class_25613);
    }

    private void a(class_2561 class_25612, class_2561 class_25613) {
        class_5250 class_52502 = class_2561.method_43470((String)"\u0421\u043c\u0435\u043d\u0438\u043b ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)15265527)));
        if (class_25612 != null && !class_25612.getString().trim().isEmpty()) {
            class_52502.method_10852((class_2561)class_25612.method_27661()).method_10852((class_2561)class_2561.method_43470((String)" \u043d\u0430 ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)0xFFFFFF))));
        } else {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)" \u043d\u0430 ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)0xFFFFFF))));
        }
        class_52502.method_10852((class_2561)(class_25613 == null ? class_2561.method_43470((String)"\u043d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u043e").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)0xFF7A7A))) : class_25613.method_27661()));
        this.a((class_2561)class_52502);
    }

    private void a(class_2561 class_25612) {
        Runnable runnable = () -> AutoSwapModule.aiG.field_1705.method_1758(class_25612, false);
        if (aiG.method_18854()) {
            runnable.run();
        } else {
            aiG.execute(runnable);
        }
    }

    private String e(class_1799 class_17992) {
        String string = class_7923.field_41178.method_10221(class_17992.method_7909()).toString();
        String string2 = class_17992.method_65130() != null ? class_17992.method_7964().getString() : "";
        return this.aN(string) + ";" + this.aN(string2);
    }

    private class_1799 aI(String string) {
        if (string == null || string.isEmpty()) {
            return class_1799.field_8037;
        }
        String[] stringArray = this.aL(string);
        if (stringArray.length == 0) {
            return class_1799.field_8037;
        }
        class_2960 class_29602 = class_2960.method_12829((String)this.aO(stringArray[0]));
        if (class_29602 == null) {
            return class_1799.field_8037;
        }
        class_1799 class_17992 = new class_1799((class_1935)class_7923.field_41178.method_63535(class_29602));
        if (class_17992.method_7960()) {
            return class_1799.field_8037;
        }
        if (stringArray.length > 1 && !stringArray[1].isEmpty()) {
            class_17992.method_57379(class_9334.field_49631, class_2561.method_43470((String)this.aO(stringArray[1])));
        }
        return class_17992;
    }

    private class_1799 aJ(String string) {
        if (string == null || string.isEmpty()) {
            return class_1799.field_8037;
        }
        if (AutoSwapModule.aiG.field_1724 != null) {
            class_1799 class_17992;
            class_1799 class_17993 = AutoSwapModule.aiG.field_1724.method_6079();
            if (!class_17993.method_7960() && string.equals(this.e(class_17993))) {
                return class_17993;
            }
            int n = this.aK(string);
            if (n != -1 && !(class_17992 = AutoSwapModule.aiG.field_1724.method_31548().method_5438(n)).method_7960()) {
                return class_17992;
            }
        }
        return this.aI(string);
    }

    private int aK(String string) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        String[] stringArray = this.aL(string);
        if (stringArray.length == 0) {
            return -1;
        }
        class_2960 class_29602 = class_2960.method_12829((String)this.aO(stringArray[0]));
        if (class_29602 == null) {
            return -1;
        }
        boolean bl = stringArray.length > 1 && !stringArray[1].isEmpty();
        String string2 = bl ? this.aO(stringArray[1]) : "";
        for (int i = 0; i < 36; ++i) {
            class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_31548().method_5438(i);
            if (class_17992.method_7960() || !class_7923.field_41178.method_10221(class_17992.method_7909()).equals(class_29602)) continue;
            if (!bl) {
                return i;
            }
            if (class_17992.method_65130() == null || !class_17992.method_7964().getString().equals(string2)) continue;
            return i;
        }
        return -1;
    }

    private String[] aL(String string) {
        ArrayList<String> arrayList = new ArrayList<String>(2);
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (bl) {
                stringBuilder.append(c);
                bl = false;
                continue;
            }
            if (c == '\\') {
                bl = true;
                continue;
            }
            if (c == ";".charAt(0)) {
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
                continue;
            }
            stringBuilder.append(c);
        }
        arrayList.add(stringBuilder.toString());
        return arrayList.toArray(new String[0]);
    }

    private String aM(String string) {
        return string.replace("\\", "\\\\").replace(";", "\\;");
    }

    private String aN(String string) {
        String string2 = string == null ? "" : string;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(string2.getBytes(StandardCharsets.UTF_8));
    }

    private String aO(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        try {
            return new String(Base64.getUrlDecoder().decode(string), StandardCharsets.UTF_8);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return string;
        }
    }

    private void fU() {
        int n;
        if (AutoSwapModule.fullNullCheck()) {
            return;
        }
        if (this.fP()) {
            return;
        }
        class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_6079();
        if (class_17992.method_7960()) {
            ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u0432 \u043e\u0444\u0444\u0445\u0435\u043d\u0434\u0435 \u043d\u0438\u0447\u0435\u0433\u043e \u043d\u0435\u0442").method_27692(class_124.field_1061));
            return;
        }
        SwapRule swapRule = null;
        SwapRule swapRule2 = null;
        if (this.a(class_17992, (SwapRule)this.qC.jT())) {
            swapRule = (SwapRule)this.qC.jT();
            swapRule2 = (SwapRule)this.qD.jT();
        } else if (this.a(class_17992, (SwapRule)this.qD.jT())) {
            swapRule = (SwapRule)this.qD.jT();
            swapRule2 = (SwapRule)this.qC.jT();
        }
        if (swapRule == null || swapRule2 == null) {
            ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041d\u0435\u0447\u0435\u0433\u043e \u0441\u0432\u0430\u043f\u0430\u0442\u044c").method_27692(class_124.field_1061));
            return;
        }
        if (this.qC.jT() == this.qD.jT() && (n = this.a(swapRule2)) == -1) {
            if (this.b(swapRule2)) {
                ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d").method_27692(class_124.field_1061));
            } else {
                ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041d\u0435\u0447\u0435\u0433\u043e \u0441\u0432\u0430\u043f\u0430\u0442\u044c").method_27692(class_124.field_1061));
            }
            return;
        }
        class_2561 class_25612 = this.a(class_17992, (class_2561)class_2561.method_43470((String)""));
        SwapRule swapRule3 = swapRule2;
        String string = this.c(swapRule2).getString();
        ClientTaskScheduler.a(2, () -> {
            if (AutoSwapModule.fullNullCheck()) {
                return;
            }
            int slot = this.a(swapRule3);
            if (slot != -1) {
                class_1799 replacementStack = AutoSwapModule.aiG.field_1724.method_31548().method_5438(slot);
                class_2561 class_25613 = this.a(replacementStack, (class_2561)class_2561.method_43470((String)string));
                float f = AutoSwapModule.aiG.field_1724.method_6029();
                AutoSwapModule.aiG.field_1724.method_6125(0.0f);
                this.r(slot);
                this.gb();
                AutoSwapModule.aiG.field_1724.method_6125(f);
                this.a(class_25612, class_25613);
                return;
            }
            if (this.b(swapRule3)) {
                ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d").method_27692(class_124.field_1061));
            } else {
                ChatOutput.b(class_2561.method_43470((String)"\u0410\u0432\u0442\u043e\u0441\u0432\u0430\u043f: \u041d\u0435\u0447\u0435\u0433\u043e \u0441\u0432\u0430\u043f\u0430\u0442\u044c").method_27692(class_124.field_1061));
            }
        });
    }

    private boolean a(class_1799 class_17992, SwapRule swapRule) {
        switch (swapRule.ordinal()) {
            case 1: {
                return this.f(class_17992);
            }
            case 0: {
                return this.g(class_17992);
            }
        }
        return false;
    }

    private int a(SwapRule swapRule) {
        for (int i = 0; i < 36; ++i) {
            class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_31548().method_5438(i);
            if (!this.a(class_17992, swapRule) || !this.j(class_17992)) continue;
            return i;
        }
        return -1;
    }

    private boolean b(SwapRule swapRule) {
        for (int i = 0; i < 36; ++i) {
            class_1799 class_17992 = AutoSwapModule.aiG.field_1724.method_31548().method_5438(i);
            if (!this.a(class_17992, swapRule)) continue;
            return true;
        }
        return false;
    }

    private class_2561 c(SwapRule swapRule) {
        return switch (swapRule.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 1 -> class_2561.method_43470((String)"\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d");
            case 0 -> class_2561.method_43470((String)"\u0421\u0444\u0435\u0440\u0430");
        };
    }

    private boolean f(class_1799 class_17992) {
        return !class_17992.method_7960() && class_17992.method_7909() == class_1802.field_8288 && this.h(class_17992);
    }

    private boolean g(class_1799 class_17992) {
        return !class_17992.method_7960() && class_17992.method_7909() instanceof class_1809 && this.h(class_17992) && !this.i(class_17992);
    }

    private boolean h(class_1799 class_17992) {
        class_9285 class_92852 = (class_9285)class_17992.method_57824(class_9334.field_49636);
        return class_92852 != null && !class_92852.comp_2393().isEmpty();
    }

    private boolean i(class_1799 class_17992) {
        String string;
        String string2 = class_17992.method_7964().getString();
        return string2.startsWith(string = "\u0413\u043e\u043b\u043e\u0432\u0430 \u0438\u0433\u0440\u043e\u043a\u0430") && string2.length() > string.length();
    }

    private boolean j(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (class_17992.method_65130() == null) {
            return true;
        }
        if (this.qB.jT() == SwapState.ro && (this.g(class_17992) || this.f(class_17992) || this.k(class_17992))) {
            return true;
        }
        return this.aP(class_17992.method_7964().getString());
    }

    private class_2561 a(class_1799 class_17992, class_2561 class_25612) {
        if (class_17992 == null || class_17992.method_7960()) {
            return class_25612;
        }
        class_5250 class_52502 = class_17992.method_7964().method_27661();
        if (class_52502.getString().trim().isEmpty()) {
            class_5250 class_52503 = class_17992.method_7909().method_63680().method_27661();
            if (!class_52503.getString().trim().isEmpty()) {
                return class_52503;
            }
            String string = class_7923.field_41178.method_10221(class_17992.method_7909()).toString();
            if (string != null && !string.trim().isEmpty()) {
                return class_2561.method_43470((String)string);
            }
            return class_25612;
        }
        return class_52502;
    }

    private boolean k(class_1799 class_17992) {
        return class_17992.method_7909() == class_1802.field_8288 || class_17992.method_7909() instanceof class_1809;
    }

    private boolean aP(String string) {
        if (this.qB.jT() == SwapState.rn) {
            return true;
        }
        String string2 = this.aR(string);
        if (string2.isEmpty()) {
            return false;
        }
        this.fV();
        if (this.rg.isEmpty()) {
            return true;
        }
        return this.rg.contains(string2);
    }

    private void fV() {
        File file = new File(AutoSwapModule.aiG.field_1697, "2.txt");
        if (!file.exists()) {
            file = new File(Celebression.getInstance().getGlobalsDir(), "2.txt");
        }
        if (file.exists()) {
            long l = file.lastModified();
            if (l == this.rh && !this.ri) {
                return;
            }
            this.rg.clear();
            this.rh = l;
            this.ri = false;
            try {
                List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                this.c(list);
                return;
            }
            catch (Exception exception) {
                this.rg.clear();
            }
        }
        if (this.ri) {
            return;
        }
        this.rg.clear();
        this.rh = -1L;
        try {
            List<String> list = this.fW();
            this.c(list);
            this.ri = !this.rg.isEmpty();
        }
        catch (Exception exception) {
            this.rg.clear();
        }
    }

    private List<String> fW() {
        if ("/celebression/2.txt" == null) {
            return List.of();
        }
        try (InputStream inputStream = AutoSwapModule.class.getResourceAsStream("/celebression/2.txt")) {
            ArrayList<String> arrayList;
            if (inputStream == null) {
                List<String> list = List.of();
                return list;
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));){
                String string;
                ArrayList<String> arrayList2 = new ArrayList<String>();
                while ((string = bufferedReader.readLine()) != null) {
                    arrayList2.add(string);
                }
                arrayList = arrayList2;
            }
            return arrayList;
        }
        catch (Exception exception) {
            return List.of();
        }
    }

    private void c(List<String> list) {
        Object object = null;
        for (String string : list) {
            String string2 = this.aS(string);
            if (string2.isEmpty()) continue;
            String string3 = string2.toLowerCase(Locale.ROOT);
            if (("\u0430\u0443\u0440\u0430".equals(string3) || "\u043f\u044b\u043b\u044c".equals(string3) || "\u0437\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0430".equals(string3)) && object != null) {
                object = object + " " + string2;
                continue;
            }
            if (object != null) {
                this.aQ((String)object);
            }
            object = string2;
        }
        if (object != null) {
            this.aQ((String)object);
        }
    }

    private void aQ(String string) {
        String string2;
        String string3 = this.aR(string);
        if (string3.isEmpty()) {
            return;
        }
        if (!this.rg.contains(string3)) {
            this.rg.add(string3);
        }
        if (string3.startsWith("[\u2605]") && !(string2 = this.aR(string3.replace("[\u2605]", "").trim())).isEmpty() && !this.rg.contains(string2)) {
            this.rg.add(string2);
        }
    }

    private String aR(String string) {
        String string2 = this.aS(string);
        if (string2.isEmpty()) {
            return "";
        }
        return string2.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }

    private String aS(String string) {
        if (string == null) {
            return "";
        }
        String string2 = re.matcher(string).replaceAll("");
        string2 = rf.matcher(string2).replaceAll("");
        return string2.trim();
    }

    private void r(int n) {
        if (n == -1) {
            return;
        }
        if (this.qP > 0 || this.qQ > 0) {
            return;
        }
        int n2 = InventoryUtils.bk(n);
        class_437 class_4372 = AutoSwapModule.aiG.field_1755;
        if (class_4372 instanceof class_490) {
            class_490 class_4902 = (class_490)class_4372;
            InventoryHelper.b(class_4902, n2);
            var boundKey = ((IBoundKey)AutoSwapModule.aiG.field_1690.field_1831).getBoundKey();
            class_4902.method_25404(boundKey.method_1444(), 0, 0);
            InventoryHelper.rN();
            return;
        }
        if (AutoSwapModule.aiG.field_1755 != null) {
            return;
        }
        if (AutoSwapModule.aiG.field_1724.method_7337()) {
            InventoryUtils.b(n2, 40, class_1713.field_7791);
            return;
        }
        aiG.method_1507((class_437)new class_490((class_1657)AutoSwapModule.aiG.field_1724));
        class_4372 = AutoSwapModule.aiG.field_1755;
        if (class_4372 instanceof class_465) {
            class_465 class_4652;
            this.qR = class_4652 = (class_465)class_4372;
            InventoryHelper.b(class_4652, n2);
            this.qN = true;
            this.qP = 2;
        } else {
            InventoryUtils.b(n2, 40, class_1713.field_7791);
        }
    }

    private void fX() {
        if (this.qP > 0) {
            class_437 class_4372;
            if (AutoSwapModule.aiG.field_1755 != this.qR || !((class_4372 = AutoSwapModule.aiG.field_1755) instanceof class_465)) {
                this.fY();
                return;
            }
            class_465 class_4652 = (class_465)class_4372;
            --this.qP;
            if (this.qP <= 0) {
                var boundKey = ((IBoundKey)AutoSwapModule.aiG.field_1690.field_1831).getBoundKey();
                class_4652.method_25404(boundKey.method_1444(), 0, 0);
                this.gb();
                this.qQ = 2;
            }
            return;
        }
        if (this.qQ > 0) {
            if (AutoSwapModule.aiG.field_1755 != this.qR) {
                this.fY();
                return;
            }
            --this.qQ;
            if (this.qQ <= 0) {
                this.gd();
            }
        }
    }

    private void fY() {
        this.qP = 0;
        this.qQ = 0;
        InventoryHelper.rN();
        this.qN = false;
        this.qR = null;
    }

    private void fZ() {
        this.qN = false;
        if (AutoSwapModule.aiG.field_1724.method_7337()) {
            this.qM = 0;
            return;
        }
        if (AutoSwapModule.aiG.field_1755 == null) {
            aiG.method_1507((class_437)new class_490((class_1657)AutoSwapModule.aiG.field_1724));
            this.qM = 1;
            this.qN = true;
        }
    }

    private void ga() {
        if (this.qM <= 0) {
            return;
        }
        if (!(AutoSwapModule.aiG.field_1755 instanceof class_490)) {
            this.qM = 0;
            this.qN = false;
            return;
        }
        this.gb();
        if (this.gc()) {
            return;
        }
        --this.qM;
        if (this.qM <= 0) {
            this.gd();
        }
    }

    private void gb() {
        if (AutoSwapModule.aiG.field_1724.field_7498.method_34255() != null && !AutoSwapModule.aiG.field_1724.field_7498.method_34255().method_7960()) {
            for (int i = 0; i < 36; ++i) {
                if (!AutoSwapModule.aiG.field_1724.method_31548().method_5438(i).method_7960()) continue;
                int n = InventoryUtils.bk(i);
                InventoryUtils.b(n, 0, class_1713.field_7790);
                break;
            }
        }
    }

    private boolean gc() {
        return AutoSwapModule.aiG.field_1724 != null && AutoSwapModule.aiG.field_1724.field_7498 != null && !AutoSwapModule.aiG.field_1724.field_7498.method_34255().method_7960();
    }

    private void gd() {
        InventoryHelper.rN();
        if (AutoSwapModule.aiG.field_1724 != null) {
            AutoSwapModule.aiG.field_1724.method_7346();
        }
        aiG.method_1507(null);
        this.qN = false;
        this.qR = null;
    }

    static {
        rc = "2.txt";
        rd = "/celebression/2.txt";
        rb = Celebression.id("client/particles/snow.png");
        re = Pattern.compile("\u00a7x(?:\u00a7[0-9a-fA-F]){6}");
        rf = Pattern.compile("\u00a7.");
    }

    public static enum SwapState
    implements NamedOption  {
        rn("settings.autoswap.mode.two"),
        ro("settings.autoswap.mode.multi");
        private final String rp;

@Override
        public String getName() {
            return this.rp;
        }

        @Generated
        private SwapState(String string2) {
            this.rp = string2;
        }

}

    public static enum SwapRule
    implements NamedOption  {
        rj("settings.autoswap.sphere"),
        rk("settings.autoswap.talik");
        private final String rl;

@Override
        public String getName() {
            return this.rl;
        }

        public class_2561 ge() {
            return switch (this.ordinal()) {
                default -> throw new IllegalStateException("Unexpected enum value");
                case 1 -> class_2561.method_43470((String)"\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d");
                case 0 -> class_2561.method_43470((String)"\u0421\u0444\u0435\u0440\u0430");
            };
        }

        @Generated
        private SwapRule(String string2) {
            this.rl = string2;
        }

}

    public static enum SwapTarget
    implements NamedOption  {
        rr("settings.autoswap.wheel_style.classic"),
        rs("settings.autoswap.wheel_style.glass");
        private final String rt;

@Override
        public String getName() {
            return this.rt;
        }

        @Generated
        private SwapTarget(String string2) {
            this.rt = string2;
        }

}
}
