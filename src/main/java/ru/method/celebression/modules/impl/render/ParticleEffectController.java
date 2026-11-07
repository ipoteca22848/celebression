package ru.method.celebression.modules.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.minecraft.class_1297;
import net.minecraft.class_1667;
import net.minecraft.class_1684;
import net.minecraft.class_1685;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.BlockLineOfSightChecker;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public final class ParticleEffectController
implements MinecraftClientAccess {
    static final class_2960 IK = Celebression.id("client/xuynya/ghost_bloom.png");
    static final class_2960 IL = Celebression.id("client/particles/star.png");
    static final class_2960 IM = Celebression.id("client/particles/snow.png");
    static final class_2960 IN = Celebression.id("client/particles/firefly.png");
    static final class_2960 IO = Celebression.id("client/particles/dollar.png");
    private static final long IP = 2000L;
    private static final long IQ = 2000L;
    private static final long IR = 350L;
    private static final long IS = 650L;
    private static final long IT = 650L;
    private static final long IU = 1200L;
    private static final long IV = 450L;
    private static final int IW = 420;
    private static final int IX = 480;
    private static final int IY = 35;
    private static final int IZ = 4;
    private final BooleanSetting Ja = new BooleanSetting("settings.particles.spawn_on.attack", true);
    private final BooleanSetting Jb = new BooleanSetting("settings.particles.spawn_on.throw", true);
    private final EnumSetting<EffectType> Jc = new EnumSetting<EffectType>("settings.particles.mode", EffectType.Jo);
    private final SliderSetting Jd = new SliderSetting("settings.particles.size", 0.5f, 0.0f, 1.0f, 0.1f);
    private final List<EffectParticle> Je = new ArrayList<EffectParticle>();
    private final List<EffectParticle> Jf = new ArrayList<EffectParticle>();
    private final Supplier<Color> Jg;
    private long lastUpdateTime = System.nanoTime();

    public ParticleEffectController() {
        this(ColorUtils::getGlobalColor);
    }

    public ParticleEffectController(Supplier<Color> supplier) {
        this.Jg = supplier == null ? ColorUtils::getGlobalColor : supplier;
    }

    public List<Setting<?>> getSettings() {
        return List.of(this.Ja, this.Jb, this.Jc, this.Jd);
    }

    public void reset() {
        this.Je.clear();
        this.Jf.clear();
        this.lastUpdateTime = System.nanoTime();
    }

    public void d(class_1297 class_12972) {
        if (!((Boolean)this.Ja.jT()).booleanValue() || class_12972 == null) {
            return;
        }
        for (int i = 0; i < 35; ++i) {
            class_243 class_2432 = new class_243(class_12972.method_23317(), class_12972.method_23318() + ParticleEffectController.c(0.0, class_12972.method_17682()), class_12972.method_23321());
            class_243 class_2433 = new class_243(ParticleEffectController.c(-6.0, 6.0), ParticleEffectController.c(-6.0, 6.0), ParticleEffectController.c(-6.0, 6.0));
            this.a(this.Je, class_2432, class_2433);
        }
        this.a(this.Je, 420);
    }

    public void dI() {
        this.a(this.Je, 2000L);
        this.a(this.Jf, 2000L);
        if (!((Boolean)this.Jb.jT()).booleanValue() || ParticleEffectController.aiG.field_1687 == null) {
            return;
        }
        for (class_1297 class_12972 : ParticleEffectController.aiG.field_1687.method_18112()) {
            boolean bl;
            if (!this.e(class_12972)) continue;
            boolean bl2 = bl = class_12972.field_6014 != class_12972.method_23317() || class_12972.field_6036 != class_12972.method_23318() || class_12972.field_5969 != class_12972.method_23321();
            if (!bl) continue;
            class_243 class_2432 = class_12972.method_19538();
            for (int i = 0; i < 4; ++i) {
                class_243 class_2433 = new class_243(class_2432.field_1352 + ParticleEffectController.c(-0.2, 0.2), class_2432.field_1351 + ParticleEffectController.c(-0.2, 0.2), class_2432.field_1350 + ParticleEffectController.c(-0.2, 0.2));
                class_243 class_2434 = new class_243(ParticleEffectController.c(-1.0, 1.0), ParticleEffectController.c(-0.3, 0.3), ParticleEffectController.c(-1.0, 1.0));
                this.a(this.Jf, class_2433, class_2434);
            }
        }
        this.a(this.Jf, 480);
    }

    public void d(class_4587 class_45872) {
        if (ParticleEffectController.aiG.field_1687 == null || ParticleEffectController.aiG.field_1724 == null) {
            return;
        }
        long l = System.nanoTime();
        double d = (double)(l - this.lastUpdateTime) / 1.0E9;
        this.lastUpdateTime = l;
        d = !Double.isFinite(d) || d <= 0.0 ? 0.0 : Math.min(d, 0.2);
        class_243 class_2432 = ParticleEffectController.aiG.field_1773.method_19418().method_19326();
        this.a(class_45872, class_2432, this.Je, 350L, 650L, 2000L, d);
        this.a(class_45872, class_2432, this.Jf, 650L, 1200L, 2000L, d);
    }

    private void a(class_4587 class_45872, class_243 class_2432, List<EffectParticle> list, long l, long l2, long l3, double d) {
        if (list.isEmpty()) {
            return;
        }
        long l4 = System.currentTimeMillis();
        Iterator<EffectParticle> iterator = list.iterator();
        while (iterator.hasNext()) {
            Color color;
            float f;
            EffectParticle effectParticle = iterator.next();
            effectParticle.b(d);
            long l5 = l4 - effectParticle.Jm;
            if (l5 > l3) {
                iterator.remove();
                continue;
            }
            float f2 = this.a(l5, l, l2);
            if (f2 <= 0.0f) continue;
            if (l4 - effectParticle.Jn >= 100L) {
                effectParticle.FV = BlockLineOfSightChecker.a(class_2432, effectParticle.position);
                effectParticle.Jn = l4;
            }
            if (!effectParticle.FV) continue;
            int n = effectParticle.Jj & 0xFFFFFF;
            int n2 = class_3532.method_15340((int)Math.round(f2 * 255.0f), (int)0, (int)255);
            int n3 = n2 << 24 | n;
            if (effectParticle.Jh == EffectType.Jt) {
                f = Math.max(0.04f, effectParticle.Jk * 1.8f);
                color = new Color(n3, true);
                Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, n2 + 24));
                Render3D.renderCube(class_45872, effectParticle.position, f, true, color, true, color2);
                continue;
            }
            f = effectParticle.Jk;
            class_45872.method_22903();
            class_45872.method_22904(effectParticle.position.field_1352 - class_2432.field_1352, effectParticle.position.field_1351 - class_2432.field_1351, effectParticle.position.field_1350 - class_2432.field_1350);
            class_45872.method_22907(ParticleEffectController.aiG.method_1561().field_4686.method_23767());
            if (effectParticle.Jh.Jx) {
                class_45872.method_22907(class_7833.field_40718.rotationDegrees((float)effectParticle.Ji));
            }
            class_4587.class_4665 matrixEntry = class_45872.method_23760().method_56822();
            Render3D.drawTexture(matrixEntry, effectParticle.Jh.Jv, -f, -f, f * 2.0f, f * 2.0f, n3, true, effectParticle.Jh.Jw);
            if (effectParticle.Jh == EffectType.Jo || effectParticle.Jh == EffectType.Jr) {
                Render3D.drawTexture(matrixEntry, effectParticle.Jh.Jv, -f / 2.0f, -f / 2.0f, f, f, n3, true, true);
            }
            class_45872.method_22909();
        }
    }

    private void a(List<EffectParticle> list, class_243 class_2432, class_243 class_2433) {
        EffectType effectType = (EffectType)this.Jc.jT();
        float f = 0.05f + ((Float)this.Jd.jT()).floatValue() * 0.2f;
        int n = this.iC().getRGB();
        int n2 = Math.round((float)ParticleEffectController.c(0.0, 360.0) / 15.0f) * 15;
        list.add(new EffectParticle(this, effectType, class_2432.method_1031(0.0, (double)f, 0.0), class_2433, n2, n, f, 0.2));
    }

    private Color iC() {
        Color color = this.Jg.get();
        return color == null ? ColorUtils.getGlobalColor() : color;
    }

    private void a(List<EffectParticle> list, int n) {
        int n2 = list.size() - Math.max(0, n);
        if (n2 > 0) {
            list.subList(0, n2).clear();
        }
    }

    private void a(List<EffectParticle> list, long l) {
        long l2 = System.currentTimeMillis();
        list.removeIf(effectParticle -> l2 - effectParticle.Jm > l);
    }

    private boolean e(class_1297 class_12972) {
        if (class_12972 instanceof class_1685) {
            class_1685 class_16852 = (class_1685)class_12972;
            return !class_16852.method_24828();
        }
        return class_12972 instanceof class_1684 || class_12972 instanceof class_1667;
    }

    private float a(long l, long l2, long l3) {
        if (l < 0L) {
            return 0.0f;
        }
        if (l < l2) {
            return ParticleEffectController.ab((float)l / (float)l2);
        }
        if (l >= l3) {
            float f = (float)(l - l3) / 450.0f;
            return 1.0f - ParticleEffectController.ab(f);
        }
        return 1.0f;
    }

    private static float ab(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return f2 * (2.0f - f2);
    }

    private static double c(double d, double d2) {
        if (d >= d2) {
            return d;
        }
        return ThreadLocalRandom.current().nextDouble(d, d2);
    }

    static enum EffectType
    implements NamedOption  {
        Jo("settings.particles.mode.bloom", IK, true, true),
        Jp("settings.particles.mode.star", IL, false, true),
        Jq("settings.particles.mode.snow", IM, false, true),
        Jr("settings.particles.mode.firefly", IN, true, true),
        Js("settings.particles.mode.dollar", IO, false, true),
        Jt("settings.particles.mode.cube", null, false, false);
        private final String Ju;
        final class_2960 Jv;
        final boolean Jw;
        final boolean Jx;

private EffectType(String string2, class_2960 class_29602, boolean bl, boolean bl2) {
            this.Ju = string2;
            this.Jv = class_29602;
            this.Jw = bl;
            this.Jx = bl2;
        }

        @Override
        public String getName() {
            return this.Ju;
        }

}

    final class EffectParticle {
        final EffectType Jh;
        class_243 position;
        private class_243 velocity;
        final int Ji;
        final int Jj;
        final float Jk;
        private final double Jl;
        final long Jm;
        boolean FV;
        long Jn;
        private class_2338 FX;

        EffectParticle(ParticleEffectController particleEffectController, EffectType effectType, class_243 class_2432, class_243 class_2433, int n, int n2, float f, double d) {
            this.Jh = effectType;
            this.position = class_2432;
            this.velocity = class_2433.method_1021(0.05);
            this.Ji = n;
            this.Jj = n2;
            this.Jk = f;
            this.Jl = d;
            this.Jm = System.currentTimeMillis();
        }

        void b(double d) {
            double d2;
            if (MinecraftClientAccess.aiG.field_1687 != null) {
                class_2338 class_23382;
                d2 = this.velocity.method_1027();
                if (d2 > 1.0E-4 && !(class_23382 = class_2338.method_49637((double)(this.position.field_1352 + this.velocity.field_1352), (double)(this.position.field_1351 + this.velocity.field_1351), (double)(this.position.field_1350 + this.velocity.field_1350))).equals((Object)this.FX)) {
                    this.FX = class_23382;
                    if (this.f(this.position.field_1352, this.position.field_1351, this.position.field_1350 + this.velocity.field_1350)) {
                        this.velocity = new class_243(this.velocity.field_1352 * 1.35, this.velocity.field_1351 * 1.35, this.velocity.field_1350 * -1.1);
                    }
                    if (this.f(this.position.field_1352, this.position.field_1351 + this.velocity.field_1351, this.position.field_1350)) {
                        this.velocity = new class_243(this.velocity.field_1352 * 1.35, this.velocity.field_1351 * -1.1, this.velocity.field_1350 * 1.35);
                    }
                    if (this.f(this.position.field_1352 + this.velocity.field_1352, this.position.field_1351, this.position.field_1350)) {
                        this.velocity = new class_243(this.velocity.field_1352 * -1.1, this.velocity.field_1351 * 1.35, this.velocity.field_1350 * 1.35);
                    }
                }
                double d3 = Math.pow(0.999, d * 60.0);
                this.velocity = this.velocity.method_1021(d3).method_1023(0.0, 2.0E-5, 0.0);
            }
            d2 = d * 60.0 * this.Jl;
            this.position = this.position.method_1019(this.velocity.method_1021(d2));
        }

        private boolean f(double d, double d2, double d3) {
            class_2338 class_23382 = class_2338.method_49637((double)d, (double)d2, (double)d3);
            return MinecraftClientAccess.aiG.field_1687.method_8320(class_23382).method_26234((class_1922)MinecraftClientAccess.aiG.field_1687, class_23382);
        }
    }
}
