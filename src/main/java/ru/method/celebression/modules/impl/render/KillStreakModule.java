package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1676;
import net.minecraft.class_2596;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_8143;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.render.builders.Builder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltText;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.modules.impl.player.SoundsModule;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public class KillStreakModule
extends Module {
    private static final long Dl = 3000L;
    private static final long Dm = 350L;
    private static final long Dn = 4500L;
    private static final long Do = 480L;
    private static final long Dp = 620L;
    private static final long Dq = 420L;
    private static final long Dr = 1100L;
    private static final long Ds = 250L;
    private static final long Dt = 700L;
    private static final long Du = 800L;
    private static final long Dv = 700L;
    private static final long Dw = 3000L;
    private static final long Dx = 2200L;
    private static final long Dy = 150L;
    private static final long Dz = 2000L;
    private static final long DA = 1600L;
    private static final long DB = 250L;
    private static final float DC = 0.5f;
    private static final float DD = 0.08f;
    private static final float DE = 8.0f;
    private static final float DF = 6.0f;
    private static final float DG = 7.0f;
    private static final float DH = 11.0f;
    private static final float DI = 8.0f;
    private static final float DJ = 7.0f;
    private static final float DK = 26.0f;
    private static final float DL = 7.0f;
    private static final float DM = 13.0f;
    private static final float DN = 5.0f;
    private static final float DO = 1.75f;
    private static final float DP = 5.0f;
    private static final float DQ = 11.5f;
    private static final float DR = 11.0f;
    private static final float DS = 6.5f;
    private static final float DT = 6.0f;
    private static final float DU = 20.0f;
    private static final float DV = 1.5f;
    private static final float DW = 0.46f;
    private static final float DX = 37.0f;
    private static final float DY = 43.0f;
    private static final float DZ = 9.0f;
    private static final float Ea = 11.5f;
    private static final float Eb = 17.0f;
    private static final float Ec = 2.5f;
    private static final float Ed = 7.0f;
    private static final float Ee = 2.0f;
    private static final float Ef = 1.5f;
    private static final float Eg = 3.5f;
    private static final float Eh = 7.0f;
    private static final float Ei = 1.5f;
    private static final float Ej = 2.5f;
    private static final float Ek = 75.0f;
    private static final float El = 1.0f;
    private static final float Em = 20.0f;
    private static final float En = 1.0f;
    private static final float Eo = 1.0f;
    private static final float Ep = 0.5f;
    private static final float Eq = 0.995f;
    private static final float Er = 0.84375f;
    private static final float Es = 1.025f;
    private static final float Et = 0.802f;
    private static final float Eu = 0.375f;
    private static final float Ev = 0.354f;
    private static final float Ew = 1.05f;
    private static final float Ex = 0.05f;
    private static final float Ey = 0.16f;
    private static final Color Ez = new Color(43, 232, 134);
    private static final Color EA = new Color(9, 14, 11, 240);
    private static final Color EB = new Color(247, 251, 248, 240);
    private static final Color EC = new Color(238, 255, 245);
    private static final Color ED = new Color(11, 20, 15);
    private static final Color EE = new Color(200, 240, 218, 128);
    private static final Color EF = new Color(38, 74, 58, 140);
    private static final Color EG = new Color(255, 255, 255, 36);
    private static final Color EH = new Color(0, 0, 0, 36);
    private static final Color EI = new Color(255, 255, 255, 15);
    private static final Color EJ = new Color(0, 0, 0, 20);
    private static final Color EK = new Color(255, 60, 120, 179);
    private static final Color EL = new Color(60, 200, 255, 179);
    private static final String[] EM = new String[]{"\u0414\u0412\u041e\u0419\u041d\u041e\u0415 \u0423\u0411\u0418\u0419\u0421\u0422\u0412\u041e", "\u0422\u0420\u041e\u0419\u041d\u041e\u0415 \u0423\u0411\u0418\u0419\u0421\u0422\u0412\u041e", "\u041c\u0415\u0413\u0410\u041a\u0418\u041b\u041b", "\u041c\u041e\u041d\u0421\u0422\u0420\u041a\u0418\u041b\u041b", "\u0411\u0415\u0417\u0423\u041c\u0418\u0415"};
    private final SliderSetting EN = new SliderSetting("settings.killstreak.window", 10.0f, 2.0f, 60.0f, 1.0f);
    private final BooleanSetting EO = new BooleanSetting("settings.killstreak.count_mobs", true);
    private final BooleanSetting EP = new BooleanSetting("settings.killstreak.combo_timer", true);
    private final BooleanSetting EQ = new BooleanSetting("settings.killstreak.theme_accent", true);
    private final BooleanSetting ER = new BooleanSetting("settings.killstreak.voice", true);
    private final SliderSetting ES = new SliderSetting("settings.killstreak.voice_volume", 130.0f, 0.0f, 200.0f, 5.0f, this.ER::jT);
    private final Map<Integer, Long> ET = new ConcurrentHashMap<Integer, Long>();
    private int wO;
    private int EU = -1;
    private int EV = -1;
    private long wP;
    private long EW;
    private long EX;
    private long EY;

    public KillStreakModule() {
        super("KillStreak", ModuleCategory.ql);
    }

    @Override
    public void onDisable() {
        this.ET.clear();
        this.jh();
        super.onDisable();
    }

    private void jh() {
        this.wO = 0;
        this.EU = -1;
        this.EV = -1;
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        class_1309 class_13092;
        if (KillStreakModule.fullNullCheck() || attackEntityEvent.a() != KillStreakModule.aiG.field_1724) {
            return;
        }
        class_1297 class_12972 = attackEntityEvent.b();
        if (!(class_12972 instanceof class_1309) || !this.b(class_13092 = (class_1309)class_12972)) {
            return;
        }
        if (!class_13092.method_5805() || class_13092.method_6032() <= 0.0f) {
            return;
        }
        this.ET.put(class_13092.method_5628(), System.currentTimeMillis());
    }

    @EventHandler
    private void onPacket(PacketEvent.Receive receive) {
        class_1309 class_13092;
        class_1297 class_12972;
        boolean bl;
        int n;
        class_8143 class_81432;
        block7: {
            block6: {
                class_2596<?> class_25962 = receive.i();
                if (!(class_25962 instanceof class_8143)) break block6;
                class_81432 = (class_8143)class_25962;
                if (!KillStreakModule.fullNullCheck()) break block7;
            }
            return;
        }
        int n2 = class_81432.comp_1267();
        if (n2 == (n = KillStreakModule.aiG.field_1724.method_5628())) {
            return;
        }
        boolean bl2 = bl = class_81432.comp_1269() == n || class_81432.comp_1270() == n;
        class_1297 ownerEntity = KillStreakModule.aiG.field_1687.method_8469(class_81432.comp_1270());
        if (!bl && (!(ownerEntity instanceof class_1676) || !this.a((class_1676)ownerEntity))) {
            return;
        }
        class_12972 = KillStreakModule.aiG.field_1687.method_8469(n2);
        if (class_12972 instanceof class_1309 && this.b(class_13092 = (class_1309)class_12972)) {
            this.ET.put(n2, System.currentTimeMillis());
        }
    }

    private boolean b(class_1309 class_13092) {
        if (class_13092.method_5628() < 0) {
            return false;
        }
        if (class_13092 instanceof class_1308) {
            return (Boolean)this.EO.jT();
        }
        return class_13092 instanceof class_1657 && class_13092 != KillStreakModule.aiG.field_1724;
    }

    public boolean ji() {
        return (Boolean)this.EO.jT();
    }

    private boolean a(class_1676 class_16762) {
        class_1657 class_16572;
        class_1297 class_12972 = class_16762.method_24921();
        return class_12972 instanceof class_1657 && (class_16572 = (class_1657)class_12972).method_5667().equals(KillStreakModule.aiG.field_1724.method_5667());
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (KillStreakModule.fullNullCheck()) {
            this.ET.clear();
            this.jh();
            return;
        }
        long l = System.currentTimeMillis();
        if (this.wO > 0 && l - this.wP > this.jk()) {
            this.jh();
        }
        Iterator<Map.Entry<Integer, Long>> iterator = this.ET.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Long> entry = iterator.next();
            if (l - entry.getValue() > 4500L) {
                iterator.remove();
                continue;
            }
            class_1297 class_12972 = KillStreakModule.aiG.field_1687.method_8469(entry.getKey().intValue());
            if (class_12972 instanceof class_1309) {
                class_1309 class_13092 = (class_1309)class_12972;
                if (class_13092.method_5805() && class_13092.method_6032() > 0.0f) continue;
                this.m(l);
                iterator.remove();
                continue;
            }
            if (class_12972 != null) continue;
            iterator.remove();
        }
    }

    private void m(long l) {
        boolean bl;
        if (this.wO > 0 && l - this.wP > this.jk()) {
            this.jh();
        }
        boolean bl2 = this.wO < 2 || l - this.wP > 3350L;
        ++this.wO;
        this.wP = l;
        this.EX = l;
        if (this.wO < 2) {
            return;
        }
        int n = KillStreakModule.ad(this.wO);
        boolean bl3 = bl = n != this.EU;
        if (bl || bl2) {
            this.EV = bl ? this.EU : n;
            this.EU = n;
            this.EW = l;
            if (this.EU >= 2) {
                this.EY = l;
            }
        }
        if (bl) {
            this.ac(n);
        }
    }

    private void ac(int n) {
        if (!((Boolean)this.ER.jT()).booleanValue()) {
            return;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getSoundManager() == null || aiG.method_1483() == null) {
            return;
        }
        class_3414 class_34142 = celebression.getSoundManager().b(SoundsModule.z(n));
        if (class_34142 == null) {
            return;
        }
        float f = Math.max(0.0f, ((Float)this.ES.jT()).floatValue() / 100.0f);
        if (f <= 0.0f) {
            return;
        }
        aiG.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)class_34142, (float)1.0f, (float)f));
    }

    public boolean jj() {
        return (Boolean)this.ER.jT();
    }

    private long jk() {
        return (long)(((Float)this.EN.jT()).floatValue() * 1000.0f);
    }

    private static int ad(int n) {
        return class_3532.method_15340((int)(n - 2), (int)0, (int)4);
    }

    @EventHandler
    private void onRender2D(HudRenderEvent hudRenderEvent) {
        float f;
        long l;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        long l2;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float animationProgress;
        String string;
        float f15;
        if (KillStreakModule.aiG.field_1724 == null || this.wO < 2 || this.EU < 0) {
            return;
        }
        long l3 = System.currentTimeMillis();
        long l4 = l3 - this.wP;
        if (l4 > 3350L) {
            return;
        }
        float f16 = f15 = l4 <= 3000L ? 1.0f : 1.0f - KillStreakModule.clamp01((float)(l4 - 3000L) / 350.0f);
        if (f15 <= 0.004f) {
            return;
        }
        class_332 class_3322 = hudRenderEvent.j();
        class_4587 class_45872 = class_3322.method_51448();
        Color color = this.accent();
        boolean bl = HudThemeRenderer.isLightTheme();
        Color color2 = bl ? EB : EA;
        Color color3 = bl ? ED : EC;
        Color color4 = bl ? EF : EE;
        Color color5 = bl ? EH : EG;
        Color color6 = bl ? EJ : EI;
        String string2 = EM[this.EU];
        String string3 = string = Integer.toString(this.wO);
        float f17 = KillStreakModule.a(Fonts.UNBOUNDED, string2, 11.5f, this.jl(), 0.0f);
        float f18 = KillStreakModule.a(Fonts.UNBOUNDED, "KILL STREAK", 5.0f, 0.05f, 1.75f);
        float f19 = this.n(l3);
        float f20 = Math.max(f18 + 5.0f + f19, f17);
        float f21 = 5.06f;
        float f22 = KillStreakModule.a(Fonts.PLEXMONO, string, 11.0f, 0.16f, 0.0f);
        float f23 = f21 + 1.5f + f22;
        float f24 = f23 + 13.0f;
        float f25 = 41.0f + f20 + 7.0f + f24 + 11.0f;
        float f26 = 38.0f;
        int n = aiG.method_22683().method_4486();
        int n2 = aiG.method_22683().method_4502();
        float f27 = ((float)n - f25) / 2.0f;
        float f28 = (float)n2 * 0.08f;
        float f29 = f27 + f25 / 2.0f;
        float f30 = f28 + f26 / 2.0f;
        float f31 = Math.min(1.0f, ((float)n - 8.0f) / f25);
        long l5 = l3 - this.EW;
        if (this.EU >= 2) {
            f14 = KillStreakModule.clamp01((float)l5 / 620.0f);
            f13 = KillStreakModule.g(f14, 0.6f, 1.12f, 1.0f);
            animationProgress = 1.0f - f14;
            f12 = (float)Math.sin((double)l5 * 0.062) * 1.5f * animationProgress;
            f11 = (float)Math.cos((double)l5 * 0.077) * 1.5f * animationProgress;
            f10 = (float)Math.sin((double)l5 * 0.055) * 0.6f * animationProgress;
            f9 = 0.0f;
            f8 = 0.0f;
        } else {
            f14 = KillStreakModule.clamp01((float)l5 / 480.0f);
            animationProgress = KillStreakModule.d(0.22f, 1.2f, 0.36f, 1.0f, f14);
            f13 = KillStreakModule.c(f14, 0.7f, 1.06f, 0.98f, 1.0f);
            f9 = -7.0f * (1.0f - animationProgress);
            f8 = 2.5f * (1.0f - f14);
            f10 = 0.0f;
            f11 = 0.0f;
            f12 = 0.0f;
        }
        if (this.EU >= 4) {
            f14 = (float)(l3 % 1600L) / 1600.0f;
            f13 *= 1.0f + 0.025f * (0.5f - 0.5f * (float)Math.cos((double)f14 * Math.PI * 2.0));
        }
        if (this.EU >= 2 && (l2 = l3 - this.EY) >= 0L && l2 < 700L) {
            f7 = KillStreakModule.clamp01((float)l2 / 700.0f);
            f6 = (this.EU >= 4 ? 0.16f : 0.1f) * (1.0f - f7) * f15;
            KillStreakModule.a(class_45872, (float)n / 2.0f, 0.0f, (float)n * 0.42f, color, f6);
        }
        class_45872.method_22903();
        class_45872.method_46416(f29 + f12, f30 + f9 + f11, 0.0f);
        class_45872.method_22905(f13 * f31, f13 * f31, 1.0f);
        if (f10 != 0.0f) {
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(f10));
        }
        class_45872.method_46416(-f29, -f30, 0.0f);
        float f32 = 0.3f + (float)this.EU * 0.08f;
        Color color7 = KillStreakModule.b(color, f32 * f15);
        if (this.EU >= 2 && l5 >= 0L && l5 < 700L) {
            f7 = (float)l5 / 700.0f;
            f6 = 1.0f - KillStreakModule.clamp01((float)(l5 - 450L) / 250.0f);
            f5 = 75.0f * f7;
            f4 = f30 - 0.5f;
            Color color8 = KillStreakModule.b(color, 0.0f);
            Color color9 = KillStreakModule.b(color, 0.85f * f15 * f6);
            Render2D.drawRoundedHorizontalGradient(class_45872, f27 - f5, f4, f5, 1.0f, 0.5f, color8, color9);
            Render2D.drawRoundedHorizontalGradient(class_45872, f27 + f25, f4, f5, 1.0f, 0.5f, color9, color8);
        }
        f7 = f27 + 7.0f;
        f6 = f28 + 6.0f;
        if (f8 > 0.3f) {
            for (int i = 0; i < 4; ++i) {
                double d = (double)i * 1.5707963267948966;
                Render2D.drawRoundedRect(class_45872, f27 + (float)(Math.cos(d) * (double)f8), f28 + (float)(Math.sin(d) * (double)f8), f25, f26, 8.0f, KillStreakModule.b(color2, 0.22f * f15));
            }
        }
        Render2D.drawRoundedRect(class_45872, f27, f28, f25, f26, 8.0f, KillStreakModule.b(color2, f15));
        f5 = KillStreakModule.a(Fonts.UNBOUNDED, string3, 37.0f, 0.16f, 0.0f);
        Render2D.startMatrixScissor(class_3322, f27, f28, f25, f26, 0);
        KillStreakModule.a(class_45872, Fonts.UNBOUNDED, string3, 37.0f, f27 + f25 - 43.0f - f5, KillStreakModule.f(f30, 37.0f, 0.995f, 0.375f), KillStreakModule.b(color, (0.05f + (float)this.EU * 0.005f) * f15), 0.16f, 0.0f);
        Render2D.stopScissor(class_3322);
        Render2D.drawRoundedRect(class_45872, f7, f6, 26.0f, 26.0f, 7.0f, KillStreakModule.b(color, (0.05f + (float)this.EU * 0.02f) * f15));
        Render2D.drawBorder(class_45872, f7, f6, 26.0f, 26.0f, 7.0f, 0.5f, 0.5f, color7);
        GuiIcons.draw(class_45872, GuiIcons.SWORD, f7 + 13.0f, f6 + 13.0f, 13.0f, KillStreakModule.b(color, f15));
        if (this.EU >= 2) {
            float f33 = f4 = this.EU >= 3 ? (float)(l5 % 800L) / 800.0f : KillStreakModule.clamp01((float)l5 / 800.0f);
            if (this.EU >= 3 || l5 < 800L) {
                float f34 = 0.5f + 1.6f * f4;
                float f35 = 26.0f * f34;
                f3 = f7 + 13.0f - f35 / 2.0f;
                f2 = f6 + 13.0f - f35 / 2.0f;
                Render2D.drawBorder(class_45872, f3, f2, f35, f35, 7.0f * f34, 0.5f, 0.5f, KillStreakModule.b(color, (1.0f - f4) * 0.85f * f15));
            }
        }
        f4 = f7 + 26.0f + 8.0f;
        KillStreakModule.a(class_45872, Fonts.UNBOUNDED, "KILL STREAK", 5.0f, f4, KillStreakModule.e(f28 + 9.0f, 5.0f, 0.995f, 0.84375f), KillStreakModule.b(color4, f15), 0.05f, 1.75f);
        this.a(class_45872, f4 + f18 + 5.0f, f28 + 11.5f, l3, color, color5, f15);
        this.a(class_45872, string2, f4, KillStreakModule.e(f28 + 17.0f, 11.5f, 0.995f, 0.84375f), l3, color, color3, bl, f15);
        float f36 = f27 + f25 - 11.0f - f24;
        float f37 = f30 - 10.0f;
        Render2D.drawRoundedRect(class_45872, f36, f37, f24, 20.0f, 6.0f, KillStreakModule.b(color, 0.06f * f15));
        Render2D.drawBorder(class_45872, f36, f37, f24, 20.0f, 6.0f, 0.5f, 0.5f, color7);
        f3 = 1.0f + 0.7f * (1.0f - KillStreakModule.clamp01((float)(l3 - this.EX) / 420.0f));
        f2 = f36 + f24 / 2.0f;
        class_45872.method_22903();
        class_45872.method_46416(f2, f30, 0.0f);
        class_45872.method_22905(f3, f3, 1.0f);
        class_45872.method_46416(-f2, -f30, 0.0f);
        float f38 = f36 + 6.5f;
        KillStreakModule.a(class_45872, f38 + f21 / 2.0f, f30, f21, 1.1f, KillStreakModule.b(color, f15));
        KillStreakModule.a(class_45872, Fonts.PLEXMONO, string, 11.0f, f38 + f21 + 1.5f, KillStreakModule.f(f30, 11.0f, 1.025f, 0.354f), KillStreakModule.b(color, f15), 0.16f, 0.0f);
        class_45872.method_22909();
        if (this.EU >= 1 && (l = l5 - 250L) >= 0L && l < 1100L) {
            f = (float)l / 1100.0f;
            float f39 = f27 - 40.0f;
            float f40 = f25 + 80.0f;
            Render2D.startMatrixScissor(class_3322, f27, f28, f25, f26, 0);
            KillStreakModule.b(class_45872, f39 + f * f40, f28, f28 + f26, 20.0f, KillStreakModule.b(color, (0.1f + (float)this.EU * 0.03f) * f15));
            Render2D.stopScissor(class_3322);
        }
        Render2D.drawBorder(class_45872, f27, f28, f25, f26, 8.0f, 0.5f, 0.5f, color7);
        if (this.EU >= 3) {
            float f41 = (float)(l3 % 3000L) / 3000.0f;
            KillStreakModule.a(class_45872, f27, f28, f25, f26, 8.0f, 1.0f, color, (float)((double)f41 * Math.PI * 2.0), f15);
        }
        if (((Boolean)this.EP.jT()).booleanValue()) {
            float f42 = 1.0f - KillStreakModule.clamp01((float)l4 / 3000.0f);
            float f43 = f28 + f26 + 3.5f;
            Render2D.drawRoundedRect(class_45872, f27, f43, f25, 1.5f, 0.75f, KillStreakModule.b(color6, f15));
            f = f25 * f42;
            if (f > 0.5f) {
                Render2D.drawRoundedRect(class_45872, f27, f43, f, 1.5f, Math.min(0.75f, f / 2.0f), KillStreakModule.b(color, f15));
            }
        }
        class_45872.method_22909();
    }

    private void a(class_4587 class_45872, String string, float f, float f2, long l, Color color, Color color2, boolean bl, float f3) {
        long l2;
        float f4 = this.jl();
        float f5 = 0.0f;
        if (this.EU >= 3 && (l2 = l % 2200L) < 150L) {
            int n = (int)(l2 / 50L);
            f5 = n % 2 == 0 ? 0.5f : -0.5f;
            KillStreakModule.a(class_45872, Fonts.UNBOUNDED, string, 11.5f, f + f5 + 1.0f, f2, KillStreakModule.b(EK, f3), f4, 0.0f);
            KillStreakModule.a(class_45872, Fonts.UNBOUNDED, string, 11.5f, f + f5 - 1.0f, f2, KillStreakModule.b(EL, f3), f4, 0.0f);
        }
        if (this.EU >= 4) {
            float f6 = (float)(l % 2000L) / 2000.0f;
            BuiltText builtText = (BuiltText)Builder.text().font(Fonts.UNBOUNDED.font()).text(string).size(11.5f).thickness(f4).color(KillStreakModule.b(color2, f3)).gradient(KillStreakModule.b(KillStreakModule.a(f6, color, color2, bl), f3), KillStreakModule.b(KillStreakModule.a(f6 + 0.5f, color, color2, bl), f3), 1.5707964f).build();
            try {
                builtText.render(class_45872.method_23760().method_23761(), f + f5, f2);
            }
            catch (Throwable throwable) {
            }
            return;
        }
        KillStreakModule.a(class_45872, Fonts.UNBOUNDED, string, 11.5f, f + f5, f2, KillStreakModule.b(color2, f3), f4, 0.0f);
    }

    private static Color a(float f, Color color, Color color2, boolean bl) {
        f -= (float)Math.floor(f);
        Color color3 = bl ? Color.BLACK : Color.WHITE;
        Color[] colorArray = new Color[]{color2, color, color3, color, color2};
        float f2 = f * 4.0f;
        int n = class_3532.method_15340((int)((int)f2), (int)0, (int)3);
        return KillStreakModule.mix(colorArray[n], colorArray[n + 1], f2 - (float)n);
    }

    private void a(class_4587 class_45872, float f, float f2, long l, Color color, Color color2, float f3) {
        float f4 = this.o(l);
        float f5 = f;
        for (int i = 0; i < 5; ++i) {
            float f6 = class_3532.method_16439((float)f4, (float)KillStreakModule.n(i, this.EV), (float)KillStreakModule.n(i, this.EU));
            Color color3 = KillStreakModule.mix(KillStreakModule.a(i, this.EV, color, color2), KillStreakModule.a(i, this.EU, color, color2), f4);
            Render2D.drawRoundedRect(class_45872, f5, f2 - 1.25f, f6, 2.5f, 1.25f, KillStreakModule.b(color3, f3));
            f5 += f6 + 2.0f;
        }
    }

    private float n(long l) {
        float f = this.o(l);
        float f2 = 8.0f;
        for (int i = 0; i < 5; ++i) {
            f2 += class_3532.method_16439((float)f, (float)KillStreakModule.n(i, this.EV), (float)KillStreakModule.n(i, this.EU));
        }
        return f2;
    }

    private float o(long l) {
        return KillStreakModule.clamp01((float)(l - this.EW) / 250.0f);
    }

    private static float n(int n, int n2) {
        return n == n2 ? 7.0f : 2.5f;
    }

    private static Color a(int n, int n2, Color color, Color color2) {
        return n <= n2 ? color : color2;
    }

    private float jl() {
        return this.EU >= 2 ? 0.16f : 0.05f;
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, Color color) {
        ColorRGBA colorRGBA = new ColorRGBA(color);
        float f5 = f3 / 2.0f;
        float f6 = Math.max(0.85f, f4);
        Render2D.drawLine(class_45872, f - f5, f2 - f5, f + f5, f2 + f5, f6, colorRGBA);
        Render2D.drawLine(class_45872, f + f5, f2 - f5, f - f5, f2 + f5, f6, colorRGBA);
    }

    private static void b(class_4587 class_45872, float f, float f2, float f3, float f4, Color color) {
        if (color.getAlpha() <= 0) {
            return;
        }
        float f5 = (f2 + f3) / 2.0f;
        float f6 = (float)Math.tan(Math.toRadians(20.0));
        float f7 = -(f2 - f5) * f6;
        float f8 = -(f3 - f5) * f6;
        int n = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0).getRGB();
        int n2 = color.getRGB();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        class_2872.method_22918(matrix4f, f - f4 + f7, f2, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f - f4 + f8, f3, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f + f8, f3, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f + f7, f2, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f + f7, f2, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f + f8, f3, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f + f4 + f8, f3, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f + f4 + f7, f2, 0.0f).method_39415(n);
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, Color color, float f4) {
        if (f4 <= 0.002f || f3 <= 0.0f) {
            return;
        }
        int n = KillStreakModule.b(color, f4).getRGB();
        int n2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0).getRGB();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27381, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(n);
        int n3 = 48;
        for (int i = 0; i <= n3; ++i) {
            double d = Math.PI * 2 * (double)i / (double)n3;
            class_2872.method_22918(matrix4f, f + (float)(Math.cos(d) * (double)f3), f2 + (float)(Math.sin(d) * (double)f3), 0.0f).method_39415(n2);
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, float f7, float f8) {
        float f9 = Math.min(f5, Math.min(f3, f4) / 2.0f);
        float f10 = f + f3 / 2.0f;
        float f11 = f2 + f4 / 2.0f;
        int n = 8;
        int n2 = 10;
        ArrayList<float[]> arrayList = new ArrayList<float[]>();
        KillStreakModule.a(arrayList, f + f3 - f9, f2 + f9, f9, -90.0f, 0.0f, n);
        KillStreakModule.a(arrayList, f + f3, f2 + f9, f + f3, f2 + f4 - f9, 1.0f, 0.0f, n2);
        KillStreakModule.a(arrayList, f + f3 - f9, f2 + f4 - f9, f9, 0.0f, 90.0f, n);
        KillStreakModule.a(arrayList, f + f3 - f9, f2 + f4, f + f9, f2 + f4, 0.0f, 1.0f, n2);
        KillStreakModule.a(arrayList, f + f9, f2 + f4 - f9, f9, 90.0f, 180.0f, n);
        KillStreakModule.a(arrayList, f, f2 + f4 - f9, f, f2 + f9, -1.0f, 0.0f, n2);
        KillStreakModule.a(arrayList, f + f9, f2 + f9, f9, 180.0f, 270.0f, n);
        KillStreakModule.a(arrayList, f + f9, f2, f + f3 - f9, f2, 0.0f, -1.0f, n2);
        if (arrayList.isEmpty()) {
            return;
        }
        arrayList.add((float[])arrayList.get(0));
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        float f12 = f6 / 2.0f;
        for (float[] fArray : arrayList) {
            double d = Math.atan2(fArray[1] - f11, fArray[0] - f10);
            double d2 = ((d - (double)f7) % (Math.PI * 2) + Math.PI * 2) % (Math.PI * 2);
            double d3 = d2 / Math.PI % 1.0;
            int n3 = KillStreakModule.b(color, (float)Math.pow(1.0 - d3, 3.0) * f8).getRGB();
            class_2872.method_22918(matrix4f, fArray[0] + fArray[2] * f12, fArray[1] + fArray[3] * f12, 0.0f).method_39415(n3);
            class_2872.method_22918(matrix4f, fArray[0] - fArray[2] * f12, fArray[1] - fArray[3] * f12, 0.0f).method_39415(n3);
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void a(List<float[]> list, float f, float f2, float f3, float f4, float f5, int n) {
        for (int i = 0; i <= n; ++i) {
            double d = Math.toRadians(class_3532.method_16439((float)((float)i / (float)n), (float)f4, (float)f5));
            float f6 = (float)Math.cos(d);
            float f7 = (float)Math.sin(d);
            list.add(new float[]{f + f6 * f3, f2 + f7 * f3, f6, f7});
        }
    }

    private static void a(List<float[]> list, float f, float f2, float f3, float f4, float f5, float f6, int n) {
        for (int i = 1; i < n; ++i) {
            float f7 = (float)i / (float)n;
            list.add(new float[]{class_3532.method_16439((float)f7, (float)f, (float)f3), class_3532.method_16439((float)f7, (float)f2, (float)f4), f5, f6});
        }
    }

    private static void a(class_4587 class_45872, Font font, String string, float f, float f2, float f3, Color color, float f4, float f5) {
        if (string == null || string.isEmpty() || color.getAlpha() <= 0) {
            return;
        }
        BuiltText builtText = (BuiltText)Builder.text().font(font.font()).text(string).size(f).thickness(f4).spacing(f5).color(color).build();
        try {
            builtText.render(class_45872.method_23760().method_23761(), f2, f3);
        }
        catch (Throwable throwable) {
        }
    }

    private static float e(float f, float f2, float f3, float f4) {
        return f + (f4 - f3) * f2;
    }

    private static float f(float f, float f2, float f3, float f4) {
        return f + (f4 - f3) * f2;
    }

    private static float a(Font font, String string, float f, float f2, float f3) {
        if (string == null || string.isEmpty()) {
            return 0.0f;
        }
        return font.getWidth(string, f) / 1.05f + (f2 * 0.5f * f + f3) * (float)Math.max(0, string.length() - 1);
    }

    private Color accent() {
        return (Boolean)this.EQ.jT() != false ? ColorUtils.getPrimaryColor() : Ez;
    }

    private static Color b(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), class_3532.method_15340((int)Math.round((float)color.getAlpha() * KillStreakModule.clamp01(f)), (int)0, (int)255));
    }

    private static Color mix(Color color, Color color2, float f) {
        f = KillStreakModule.clamp01(f);
        return new Color(Math.round(class_3532.method_48781((float)f, (int)color.getRed(), (int)color2.getRed())), Math.round(class_3532.method_48781((float)f, (int)color.getGreen(), (int)color2.getGreen())), Math.round(class_3532.method_48781((float)f, (int)color.getBlue(), (int)color2.getBlue())), Math.round(class_3532.method_48781((float)f, (int)color.getAlpha(), (int)color2.getAlpha())));
    }

    private static float clamp01(float f) {
        return f < 0.0f ? 0.0f : (f > 1.0f ? 1.0f : f);
    }

    private static float g(float f, float f2, float f3, float f4) {
        if (f < 0.5f) {
            return class_3532.method_16439((float)KillStreakModule.s(f / 0.5f), (float)f2, (float)f3);
        }
        return class_3532.method_16439((float)KillStreakModule.s((f - 0.5f) / 0.5f), (float)f3, (float)f4);
    }

    private static float c(float f, float f2, float f3, float f4, float f5) {
        if (f < 0.5f) {
            return class_3532.method_16439((float)KillStreakModule.s(f / 0.5f), (float)f2, (float)f3);
        }
        if (f < 0.75f) {
            return class_3532.method_16439((float)KillStreakModule.s((f - 0.5f) / 0.25f), (float)f3, (float)f4);
        }
        return class_3532.method_16439((float)KillStreakModule.s((f - 0.75f) / 0.25f), (float)f4, (float)f5);
    }

    private static float s(float f) {
        f = KillStreakModule.clamp01(f);
        return f * f * (3.0f - 2.0f * f);
    }

    private static float d(float f, float f2, float f3, float f4, float f5) {
        float f6;
        float f7;
        if (f5 <= 0.0f) {
            return 0.0f;
        }
        if (f5 >= 1.0f) {
            return 1.0f;
        }
        float f8 = f5;
        for (int i = 0; i < 8 && !(Math.abs(f7 = KillStreakModule.g(f8, f, f3) - f5) < 1.0E-4f) && !(Math.abs(f6 = KillStreakModule.h(f8, f, f3)) < 1.0E-6f); ++i) {
            f8 = KillStreakModule.clamp01(f8 - f7 / f6);
        }
        return KillStreakModule.g(f8, f2, f4);
    }

    private static float g(float f, float f2, float f3) {
        float f4 = 1.0f - f;
        return 3.0f * f4 * f4 * f * f2 + 3.0f * f4 * f * f * f3 + f * f * f;
    }

    private static float h(float f, float f2, float f3) {
        float f4 = 1.0f - f;
        return 3.0f * f4 * f4 * f2 + 6.0f * f4 * f * (f3 - f2) + 3.0f * f * f * (1.0f - f3);
    }
}
