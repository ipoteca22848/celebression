package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2761;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_638;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public class WorldModule
extends Module {
    private static final class_2960 GU = Celebression.id("client/xuynya/ghost_bloom.png");
    private static final int GV = 120;
    private static final float GW = 0.07f;
    private static final float GX = 17.0f;
    private static final float GY = 2.0f;
    private static final float GZ = 5.0f;
    private static final long Ha = 400L;
    private final EnumSetting<TimeMode> Hb = new EnumSetting<TimeMode>("settings.timechanger.mode", TimeMode.Hy);
    private final EnumSetting<WeatherMode> Hc = new EnumSetting<WeatherMode>("settings.timechanger.weather", WeatherMode.HE);
    private final BooleanSetting Hd = new BooleanSetting("settings.world.glyphs", false);
    private final ColorSetting He = new ColorSetting("settings.world.glyphs.color", ColorUtils.getGlobalColor(), this.Hd::jT);
    private final SliderSetting Hf = new SliderSetting("settings.world.glyphs.volume", 120.0f, 20.0f, 400.0f, 10.0f, this.Hd::jT);
    private final SliderSetting Hg = new SliderSetting("settings.world.glyphs.thickness", 2.5f, 1.0f, 8.0f, 0.5f, this.Hd::jT);
    private final SliderSetting Hh = new SliderSetting("settings.world.glyphs.speed", 1.0f, 0.1f, 3.0f, 0.1f, this.Hd::jT);
    private final BooleanSetting Hi = new BooleanSetting("settings.world.glyphs.glow", false, this.Hd::jT);
    private final List<WorldMode> Hj = new ArrayList<WorldMode>();
    private final Random Hk = new Random();
    private volatile long Hl = -1L;
    private float Hm = -1.0f;
    private float Hn = -1.0f;

    public WorldModule() {
        super("World", ModuleCategory.ql);
        this.getSettings().add(this.Hb);
        this.getSettings().add(this.Hc);
        this.getSettings().add(this.Hd);
        this.getSettings().add(this.He);
        this.getSettings().add(this.Hf);
        this.getSettings().add(this.Hg);
        this.getSettings().add(this.Hh);
        this.getSettings().add(this.Hi);
    }

    private int jx() {
        return Math.max(1, Math.round(((Float)this.Hf.jT()).floatValue()));
    }

    @Override
    public void onEnable() {
        if (!WorldModule.fullNullCheck() && WorldModule.aiG.field_1687 != null) {
            this.Hl = WorldModule.aiG.field_1687.method_8532();
            this.Hm = WorldModule.aiG.field_1687.method_8430(1.0f);
            this.Hn = WorldModule.aiG.field_1687.method_8478(1.0f);
        }
        this.jz();
        super.onEnable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        class_638.class_5271 class_52712;
        if (WorldModule.fullNullCheck()) {
            return;
        }
        class_638 class_6382 = WorldModule.aiG.field_1687;
        if (class_6382 == null) {
            return;
        }
        long l = ((TimeMode)this.Hb.jT()).jD() % 24000L;
        if (l < 0L) {
            l += 24000L;
        }
        if ((class_52712 = class_6382.method_28104()) != null) {
            class_52712.method_165(l);
        }
        WeatherMode weatherMode = (WeatherMode)this.Hc.jT();
        class_6382.method_8519(weatherMode.jF());
        class_6382.method_8496(weatherMode.jG());
        this.jy();
    }

    private void jy() {
        int n;
        if (!((Boolean)this.Hd.jT()).booleanValue() || WorldModule.aiG.field_1724 == null) {
            return;
        }
        float f = 0.21000001f * Math.max(0.1f, ((Float)this.Hh.jT()).floatValue());
        int n2 = 0;
        Iterator<WorldMode> iterator = this.Hj.iterator();
        while (iterator.hasNext()) {
            WorldMode worldMode = iterator.next();
            if (worldMode.Hw) {
                if (!worldMode.iI()) continue;
                iterator.remove();
                continue;
            }
            worldMode.i(f, 5.0f, 17.0f);
            if (worldMode.m(WorldModule.aiG.field_1724.method_19538())) {
                worldMode.jC();
                continue;
            }
            ++n2;
        }
        int n3 = this.jx() - n2;
        for (n = 0; n < n3; ++n) {
            this.Hj.add(new WorldMode(this.Hk));
        }
        for (n = this.Hj.size() - 1; n >= 0 && n2 > this.jx(); --n) {
            WorldMode worldMode = this.Hj.get(n);
            if (worldMode.Hw) continue;
            worldMode.jC();
            --n2;
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        if (WorldModule.fullNullCheck()) {
            return;
        }
        class_2596<?> class_25962 = receive.i();
        if (class_25962 instanceof class_2761) {
            class_2761 class_27612 = (class_2761)class_25962;
            long l = class_27612.comp_3220() % 24000L;
            if (l < 0L) {
                l += 24000L;
            }
            this.Hl = l;
            receive.cancel();
        }
    }

    private void jz() {
        this.Hj.clear();
        if (WorldModule.fullNullCheck() || WorldModule.aiG.field_1724 == null) {
            return;
        }
        int n = this.jx();
        for (int i = 0; i < n; ++i) {
            this.Hj.add(new WorldMode(this.Hk));
        }
    }

    @EventHandler
    private void onRenderGlyphs(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (!((Boolean)this.Hd.jT()).booleanValue() || WorldModule.fullNullCheck() || WorldModule.aiG.field_1724 == null) {
            return;
        }
        this.c(matrixStackStage.l());
    }

    private void c(class_4587 class_45872) {
        if (this.Hj.isEmpty() || WorldModule.aiG.field_1773 == null) {
            return;
        }
        class_243 class_2432 = WorldModule.aiG.field_1773.method_19418().method_19326();
        Color color = this.He.jT() != null ? (Color)this.He.jT() : ColorUtils.getGlobalColor();
        int n = color.getRGB();
        int n2 = color.getAlpha();
        if (((Boolean)this.Hi.jT()).booleanValue()) {
            this.a(class_45872, class_2432, n, n2, 8.0f, 0.04f);
            this.a(class_45872, class_2432, n, n2, 5.0f, 0.08f);
            this.a(class_45872, class_2432, n, n2, 2.5f, 0.15f);
            this.a(class_45872, class_2432, n, n2, 1.35f, 0.22f);
        }
        this.a(class_45872, class_2432, n, n2, 1.0f, 1.0f);
    }

    private void a(class_4587 class_45872, class_243 class_2432, int n, int n2, float f, float f2) {
        float f3 = ((Float)this.Hg.jT()).floatValue() * f;
        for (WorldMode worldMode : this.Hj) {
            float f4;
            if (worldMode.Ho.size() < 2 || (f4 = worldMode.jB() * f2) <= 0.005f) continue;
            for (int i = 0; i < worldMode.Ho.size() - 1; ++i) {
                class_243 class_2433 = worldMode.Ho.get(i);
                class_243 class_2434 = worldMode.Ho.get(i + 1);
                if (class_2433.method_1022(class_2432) > 60.0 || class_2434.method_1022(class_2432) > 60.0) continue;
                float f5 = f4 * (float)(i + 1) / (float)worldMode.Ho.size();
                int n3 = WorldModule.o(n, Math.round(f5 * (float)n2));
                Render3D.drawLine(class_45872, class_2433, class_2434, n3, f3, true);
            }
        }
    }

    private static int o(int n, int n2) {
        return Math.max(0, Math.min(255, n2)) << 24 | n & 0xFFFFFF;
    }

    @Override
    public void onDisable() {
        class_638 class_6382;
        this.Hj.clear();
        if (!WorldModule.fullNullCheck() && (class_6382 = WorldModule.aiG.field_1687) != null && this.Hl >= 0L) {
            class_638.class_5271 class_52712;
            long l = this.Hl % 24000L;
            if (l < 0L) {
                l += 24000L;
            }
            if ((class_52712 = class_6382.method_28104()) != null) {
                class_52712.method_165(l);
            }
            if (this.Hm >= 0.0f) {
                class_6382.method_8519(this.Hm);
            }
            if (this.Hn >= 0.0f) {
                class_6382.method_8496(this.Hn);
            }
        }
        this.Hl = -1L;
        this.Hm = -1.0f;
        this.Hn = -1.0f;
        super.onDisable();
    }

    static enum TimeMode
    implements NamedOption  {
        Hx("settings.timechanger.mode.dawn", 23000L),
        Hy("settings.timechanger.mode.day", 1000L),
        Hz("settings.timechanger.mode.sunset", 13000L),
        HA("settings.timechanger.mode.night", 14000L);
        private final String HB;
        private final long HC;

private TimeMode(String string2, long l) {
            this.HB = string2;
            this.HC = l;
        }

        @Override
        public String getName() {
            return this.HB;
        }

        public long jD() {
            return this.HC;
        }

}

    static enum WeatherMode
    implements NamedOption  {
        HE("settings.timechanger.weather.sunny", 0.0f, 0.0f),
        HF("settings.timechanger.weather.rain", 1.0f, 0.0f);
        private final String HG;
        private final float HH;
        private final float HI;

private WeatherMode(String string2, float f, float f2) {
            this.HG = string2;
            this.HH = f;
            this.HI = f2;
        }

        @Override
        public String getName() {
            return this.HG;
        }

        public float jF() {
            return this.HH;
        }

        public float jG() {
            return this.HI;
        }

}

    static final class WorldMode {
        final List<class_243> Ho = new ArrayList<class_243>();
        final List<class_243> Hp = new ArrayList<class_243>();
        class_243 Hq;
        double Hr = 0.0;
        double Hs;
        double Ht = 0.0;
        final Random Hu;
        final Animation Hv = new Animation(400L, 1.0, true, Easing.aiR);
        boolean Hw = false;

        WorldMode(Random random) {
            this.Hu = random;
            class_310 class_3102 = class_310.method_1551();
            if (class_3102.field_1724 == null) {
                this.Ho.add(class_243.field_1353);
            } else {
                class_243 class_2432 = class_3102.field_1724.method_19538();
                float f = class_3102.field_1724.method_36454();
                float f2 = class_3102.field_1724.method_36455();
                double d = Math.toRadians(f);
                double d2 = Math.toRadians(f2);
                double d3 = 2.0 + random.nextDouble() * 6.0;
                double d4 = -Math.sin(d) * Math.cos(d2);
                double d5 = -Math.sin(d2);
                double d6 = Math.cos(d) * Math.cos(d2);
                class_243 class_2433 = new class_243(d4, d5, d6);
                class_243 class_2434 = class_2433.method_1036(new class_243(0.0, 1.0, 0.0)).method_1029();
                class_243 class_2435 = class_2434.method_1036(class_2433).method_1029();
                double d7 = (random.nextDouble() - 0.5) * 24.0;
                double d8 = (random.nextDouble() - 0.5) * 18.0;
                double d9 = class_2432.field_1352 + d4 * d3 + class_2434.field_1352 * d7 + class_2435.field_1352 * d8;
                double d10 = class_2432.field_1351 + d5 * d3 + class_2434.field_1351 * d7 + class_2435.field_1351 * d8;
                double d11 = class_2432.field_1350 + d6 * d3 + class_2434.field_1350 * d7 + class_2435.field_1350 * d8;
                this.Ho.add(new class_243(d9, d10, d11));
            }
            this.Hq = this.jA();
            this.Hs = 0.5 + random.nextDouble() * 1.5;
        }

        void i(float f, float f2, float f3) {
            if (this.Ho.isEmpty()) {
                return;
            }
            class_243 class_2432 = this.Ho.get(this.Ho.size() - 1);
            class_243 class_2433 = class_2432.method_1019(this.Hq.method_1021((double)f));
            this.Ho.add(class_2433);
            this.Ht += (double)f;
            this.Hr += (double)f;
            if (this.Hr >= this.Hs) {
                this.Hp.add(class_2433);
                this.Hq = this.jA();
                this.Hr = 0.0;
                this.Hs = 0.5 + this.Hu.nextDouble() * 1.5;
            }
            while (this.Ht > (double)f3 && this.Ho.size() > 2) {
                class_243 class_2434 = this.Ho.remove(0);
                this.Ht -= class_2434.method_1022(this.Ho.get(0));
                if (this.Hp.isEmpty() || !this.Hp.get(0).equals((Object)class_2434)) continue;
                this.Hp.remove(0);
            }
        }

        private class_243 jA() {
            return switch (this.Hu.nextInt(6)) {
                case 0 -> new class_243(1.0, 0.0, 0.0);
                case 1 -> new class_243(-1.0, 0.0, 0.0);
                case 2 -> new class_243(0.0, 1.0, 0.0);
                case 3 -> new class_243(0.0, -1.0, 0.0);
                case 4 -> new class_243(0.0, 0.0, 1.0);
                default -> new class_243(0.0, 0.0, -1.0);
            };
        }

        float jB() {
            return Math.max(0.0f, Math.min(1.0f, this.Hv.rk()));
        }

        void jC() {
            this.Hw = true;
            this.Hv.i(false);
        }

        boolean iI() {
            return this.Hw && this.Hv.rk() <= 0.01f;
        }

        boolean m(class_243 class_2432) {
            class_243 class_2433;
            class_4184 class_41842;
            class_243 class_2434;
            if (this.Ho.isEmpty()) {
                return true;
            }
            class_243 class_2435 = this.Ho.get(this.Ho.size() - 1);
            if (class_2435.method_1022(class_2432) > 18.0) {
                return true;
            }
            class_310 class_3102 = class_310.method_1551();
            return class_3102.field_1773 != null && (class_2434 = class_243.method_1030((float)(class_41842 = class_3102.field_1773.method_19418()).method_19329(), (float)class_41842.method_19330())).method_1026(class_2433 = class_2435.method_1020(class_41842.method_19326()).method_1029()) < 0.5;
        }
    }
}

