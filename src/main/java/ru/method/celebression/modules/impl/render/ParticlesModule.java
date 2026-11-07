package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IWorldRenderer;
import ru.method.celebression.util.BlockLineOfSightChecker;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public class ParticlesModule
extends Module {
    static final class_2960 Fh = Celebression.id("client/xuynya/ghost_bloom.png");
    static final class_2960 Fi = Celebression.id("client/particles/star.png");
    static final class_2960 Fj = Celebression.id("client/particles/snow.png");
    static final class_2960 Fk = Celebression.id("client/particles/firefly.png");
    static final class_2960 Fl = Celebression.id("client/particles/dollar.png");
    private static final long Fm = 50L;
    private static final int Fn = 36;
    private static final int Fo = 12;
    private static final int Fp = 50;
    private static final int Fq = 150;
    private static final double Fr = 0.99;
    private static final float Fs = 42.0f;
    private static final double Ft = 18.0;
    private static final float Fu = 1.0f;
    private static final float Fv = 0.22f;
    private static final float Fw = 1.45f;
    private static final int Fx = 16;
    private static final float Fy = 2.0f;
    private final SliderSetting Fz = new SliderSetting("settings.particles.max", 600.0f, 0.0f, 15000.0f, 50.0f);
    private final SliderSetting FA = new SliderSetting("settings.particles.size", 0.25f, 0.04f, 2.5f, 0.01f);
    private final ColorSetting FB = new ColorSetting("settings.particles.color", ColorUtils.getGlobalColor());
    private final EnumSetting<ParticleType> FC = new EnumSetting<ParticleType>("settings.particles.mode", ParticleType.FY);
    private final BooleanSetting FD = new BooleanSetting("settings.particles.cube.glow", true, () -> this.FC.jT() == ParticleType.Gd);
    private final SliderSetting FE = new SliderSetting("settings.particles.cube.glow_size", 2.0f, 0.5f, 4.0f, 0.1f, () -> this.FC.jT() == ParticleType.Gd && (Boolean)this.FD.jT() != false);
    private final SliderSetting FF = new SliderSetting("settings.particles.cube.rotate_speed", 2.0f, 0.5f, 6.0f, 0.1f, () -> this.FC.jT() == ParticleType.Gd);
    private final List<Particle> FG = new ArrayList<Particle>();
    private final Object FH = new Object();
    final Random FI = new Random();
    private double FJ;
    private Color FK;
    private long lastColorUpdate;
    private int tickCounter;

    public ParticlesModule() {
        super("Particles", ModuleCategory.ql);
        this.getSettings().add(this.Fz);
        this.getSettings().add(this.FA);
        this.getSettings().add(this.FB);
        this.getSettings().add(this.FC);
        this.getSettings().add(this.FD);
        this.getSettings().add(this.FE);
        this.getSettings().add(this.FF);
    }

        @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (ParticlesModule.fullNullCheck()) {
            return;
        }
        ++this.tickCounter;
        this.FJ += 2.1;
        int n = (int)this.FJ;
        if (n > 0) {
            this.FJ -= (double)n;
        }
        int n2 = Math.max(0, Math.round(((Float)this.Fz.jT()).floatValue()));
        class_243 class_2432 = ParticlesModule.aiG.field_1724.method_19538();
        int n3 = this.jn();
        float f = Math.max(0.0f, ((Float)this.FF.jT()).floatValue());
        Object object = this.FH;
        synchronized (object) {
            if (this.FG.size() > n2) {
                this.FG.subList(0, this.FG.size() - n2).clear();
            }
            if (n > 0 && this.FG.size() < n2) {
                int n4 = n2 - this.FG.size();
                n = Math.min(n, Math.min(n4, 36));
                for (int i = 0; i < n; ++i) {
                    class_243 class_2433 = this.b(class_2432, 18.0);
                    this.FG.add(new Particle(this, class_2433, 1.0f, 0.22f, n3, f));
                }
            }
            double d = Math.max(60.0, 54.0);
            double d2 = d * d;
            Iterator<Particle> iterator = this.FG.iterator();
            while (iterator.hasNext()) {
                Particle particle = iterator.next();
                particle.tick();
                if (!particle.dz() && !(ParticlesModule.aiG.field_1724.method_19538().method_1025(particle.pos) > d2) && this.a(particle) && !this.l(particle.pos)) continue;
                iterator.remove();
            }
        }
    }

        @EventHandler
    private void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (ParticlesModule.fullNullCheck()) {
            return;
        }
        float f = matrixStackStage.k().method_60637(true);
        float f2 = Math.max(0.0f, ((Float)this.FA.jT()).floatValue());
        ParticleType particleType = (ParticleType)this.FC.jT();
        boolean bl = particleType == ParticleType.Gd;
        boolean bl2 = bl && (Boolean)this.FD.jT() != false;
        float f3 = bl ? Math.max(0.0f, ((Float)this.FE.jT()).floatValue()) : 0.0f;
        class_243 class_2432 = ParticlesModule.aiG.field_1773.method_19418().method_19326();
        class_4587 class_45872 = matrixStackStage.l();
        Object object = this.FH;
        synchronized (object) {
            for (Particle particle : this.FG) {
                int n;
                class_243 class_2433 = particle.v(f);
                if (!this.j(class_2433)) continue;
                if (this.tickCounter - particle.FW >= 2) {
                    particle.FV = BlockLineOfSightChecker.a(class_2432, class_2433);
                    particle.FW = this.tickCounter;
                }
                if (!particle.FV || (n = particle.t(f)) <= 3) continue;
                float f4 = particle.u(f);
                float f5 = Math.max(0.04f, f2 * particle.FM * (1.0f - f4 * 0.25f));
                if (bl) {
                    f5 *= 1.45f;
                }
                int n2 = n << 24 | particle.FL & 0xFFFFFF;
                if (bl) {
                    this.a(class_45872, particle, class_2433, f, f5, n2, bl2, f3);
                    continue;
                }
                Render3D.renderBillboardTexture(class_2433, f5, particleType.jo(), n2, true, true);
            }
        }
    }

        @Override
    public void onDisable() {
        Object object = this.FH;
        synchronized (object) {
            this.FG.clear();
        }
        this.FJ = 0.0;
        super.onDisable();
    }

    private boolean j(class_243 class_2432) {
        return ((IWorldRenderer)ParticlesModule.aiG.field_1769).getFrustum().method_23093(new class_238(class_2432.field_1352 - 0.5, class_2432.field_1351 - 0.5, class_2432.field_1350 - 0.5, class_2432.field_1352 + 0.5, class_2432.field_1351 + 0.5, class_2432.field_1350 + 0.5));
    }

    private Color jm() {
        long l = System.currentTimeMillis();
        if (this.FK == null || l - this.lastColorUpdate > 50L) {
            this.FK = ColorUtils.getGlobalColor();
            this.lastColorUpdate = l;
        }
        return this.FK;
    }

    private int jn() {
        Color color = (Color)this.FB.jT();
        if (color == null) {
            color = this.jm();
        }
        int n = Math.max(0, Math.min(255, color.getAlpha()));
        return n << 24 | color.getRGB() & 0xFFFFFF;
    }

    private class_243 b(class_243 class_2432, double d) {
        int n = ParticlesModule.aiG.field_1687.method_31607();
        int n2 = n + ParticlesModule.aiG.field_1687.method_31605();
        double d2 = d;
        double d3 = d * 0.5;
        for (int i = 0; i < 12; ++i) {
            double d4 = class_2432.field_1352 + (this.FI.nextDouble() - 0.5) * (d2 * 2.0);
            double d5 = class_2432.field_1351 + (this.FI.nextDouble() - 0.5) * (d3 * 2.0);
            double d6 = class_2432.field_1350 + (this.FI.nextDouble() - 0.5) * (d2 * 2.0);
            class_243 class_2433 = new class_243(d4, class_3532.method_15350((double)d5, (double)((double)n + 1.0), (double)((double)n2 - 2.0)), d6);
            if (!this.k(class_2433) || this.l(class_2433)) continue;
            return class_2433;
        }
        double d7 = class_3532.method_15350((double)(class_2432.field_1351 + 1.5), (double)((double)n + 1.0), (double)((double)n2 - 2.0));
        class_243 class_2434 = new class_243(class_2432.field_1352, d7, class_2432.field_1350);
        if (this.k(class_2434) && !this.l(class_2434)) {
            return class_2434;
        }
        return class_2432;
    }

    private boolean a(Particle particle) {
        class_2338 class_23382 = class_2338.method_49638((class_2374)particle.pos.method_1023(0.15, 0.15, 0.15));
        if (class_23382.equals((Object)particle.FX) && particle.FO % 10 != 0) {
            return true;
        }
        if (this.k(particle.pos)) {
            particle.FX = class_23382;
            return true;
        }
        return false;
    }

    private boolean k(class_243 class_2432) {
        if (ParticlesModule.aiG.field_1687 == null) {
            return false;
        }
        class_238 class_2382 = new class_238(class_2432.field_1352 - 0.15, class_2432.field_1351 - 0.15, class_2432.field_1350 - 0.15, class_2432.field_1352 + 0.15, class_2432.field_1351 + 0.15, class_2432.field_1350 + 0.15);
        class_2338 class_23382 = class_2338.method_49638((class_2374)class_2432);
        return ParticlesModule.aiG.field_1687.method_18026(class_2382) && ParticlesModule.aiG.field_1687.method_8320(class_23382).method_26215();
    }

    private boolean l(class_243 class_2432) {
        if (ParticlesModule.aiG.field_1724 == null) {
            return false;
        }
        return ParticlesModule.aiG.field_1724.method_5829().method_1014(0.05).method_1006(class_2432);
    }

    private void a(class_4587 class_45872, Particle particle, class_243 class_2432, float f, float f2, int n, boolean bl, float f3) {
        float f4 = (float)(n >>> 24 & 0xFF) / 255.0f;
        float f5 = Math.max(0.01f, f2 * 0.5f * f4);
        if (f5 <= 0.0f) {
            return;
        }
        Color color = new Color(n, true);
        if (bl) {
            this.a(class_45872, particle, class_2432, f, f5 * f3, color);
        }
        this.b(class_45872, particle, class_2432, f, f5, color);
    }

    private void a(class_4587 class_45872, Particle particle, class_243 class_2432, float f, float f2, Color color) {
        if (f2 <= 0.0f) {
            return;
        }
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), class_3532.method_15340((int)Math.round((float)color.getAlpha() * 0.32f), (int)0, (int)255));
        this.a(class_45872, particle, class_2432, f, f2, color2, 3.0f);
    }

    private void b(class_4587 class_45872, Particle particle, class_243 class_2432, float f, float f2, Color color) {
        this.a(class_45872, particle, class_2432, f, f2, color, 2.0f);
    }

    private void a(class_4587 class_45872, Particle particle, class_243 class_2432, float f, float f2, Color color, float f3) {
        float f4 = f2;
        Matrix4f matrix4f = new Matrix4f().rotateX((float)Math.toRadians(particle.w(f))).rotateY((float)Math.toRadians(particle.x(f))).rotateZ((float)Math.toRadians(particle.y(f)));
        class_243 class_2433 = this.a(matrix4f, class_2432, -f4, -f4, -f4);
        class_243 class_2434 = this.a(matrix4f, class_2432, f4, -f4, -f4);
        class_243 class_2435 = this.a(matrix4f, class_2432, f4, -f4, f4);
        class_243 class_2436 = this.a(matrix4f, class_2432, -f4, -f4, f4);
        class_243 class_2437 = this.a(matrix4f, class_2432, -f4, f4, -f4);
        class_243 class_2438 = this.a(matrix4f, class_2432, f4, f4, -f4);
        class_243 class_2439 = this.a(matrix4f, class_2432, f4, f4, f4);
        class_243 class_24310 = this.a(matrix4f, class_2432, -f4, f4, f4);
        Render3D.drawLine(class_45872, class_2433, class_2434, color, f3, true);
        Render3D.drawLine(class_45872, class_2434, class_2435, color, f3, true);
        Render3D.drawLine(class_45872, class_2435, class_2436, color, f3, true);
        Render3D.drawLine(class_45872, class_2436, class_2433, color, f3, true);
        Render3D.drawLine(class_45872, class_2437, class_2438, color, f3, true);
        Render3D.drawLine(class_45872, class_2438, class_2439, color, f3, true);
        Render3D.drawLine(class_45872, class_2439, class_24310, color, f3, true);
        Render3D.drawLine(class_45872, class_24310, class_2437, color, f3, true);
        Render3D.drawLine(class_45872, class_2433, class_2437, color, f3, true);
        Render3D.drawLine(class_45872, class_2434, class_2438, color, f3, true);
        Render3D.drawLine(class_45872, class_2435, class_2439, color, f3, true);
        Render3D.drawLine(class_45872, class_2436, class_24310, color, f3, true);
        int n = class_3532.method_15340((int)(color.getAlpha() / 2), (int)0, (int)255);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        Render3D.drawLine(class_45872, class_2433, class_2439, color2, f3, true);
        Render3D.drawLine(class_45872, class_2434, class_24310, color2, f3, true);
        Render3D.drawLine(class_45872, class_2436, class_2438, color2, f3, true);
        Render3D.drawLine(class_45872, class_2435, class_2437, color2, f3, true);
    }

    private class_243 a(Matrix4f matrix4f, class_243 class_2432, float f, float f2, float f3) {
        Vector3f vector3f = new Vector3f(f, f2, f3);
        matrix4f.transformPosition(vector3f);
        return class_2432.method_1031((double)vector3f.x, (double)vector3f.y, (double)vector3f.z);
    }

    static enum ParticleType
    implements NamedOption  {
        FY("settings.particles.mode.bloom", Fh),
        FZ("settings.particles.mode.star", Fi),
        Ga("settings.particles.mode.snow", Fj),
        Gb("settings.particles.mode.firefly", Fk),
        Gc("settings.particles.mode.dollar", Fl),
        Gd("settings.particles.mode.cube", Fh);
        private final String Ge;
        private final class_2960 Gf;

private ParticleType(String string2, class_2960 class_29602) {
            this.Ge = string2;
            this.Gf = class_29602;
        }

        @Override
        public String getName() {
            return this.Ge;
        }

        public class_2960 jo() {
            return this.Gf;
        }

}

    final class Particle {
        private class_243 prevPos;
        class_243 pos;
        private class_243 velocity;
        final int FL;
        final float FM;
        private final int FN;
        int FO;
        private float FP;
        private float FQ;
        private float FR;
        private float rotX;
        private float rotY;
        private float rotZ;
        private final float FS;
        private final float FT;
        private final float FU;
        boolean FV;
        int FW = -1073741824;
        class_2338 FX;

        Particle(ParticlesModule particlesModule, class_243 class_2432, float f, float f2, int n, float f3) {
            this.prevPos = class_2432;
            this.pos = class_2432;
            this.FL = n;
            this.FM = f2;
            this.FO = this.FN = 50 + particlesModule.FI.nextInt(Math.max(1, 101));
            double d = (particlesModule.FI.nextDouble() - 0.5) * 0.02 * (double)f;
            double d2 = (particlesModule.FI.nextDouble() * 0.03 + 0.01) * (double)f;
            double d3 = (particlesModule.FI.nextDouble() - 0.5) * 0.02 * (double)f;
            this.velocity = new class_243(d, d2, d3);
            this.rotX = particlesModule.FI.nextFloat() * 360.0f - 180.0f;
            this.rotY = particlesModule.FI.nextFloat() * 360.0f - 180.0f;
            this.rotZ = particlesModule.FI.nextFloat() * 360.0f - 180.0f;
            this.FP = this.rotX;
            this.FQ = this.rotY;
            this.FR = this.rotZ;
            float f4 = Math.max(0.0f, f3);
            this.FS = (particlesModule.FI.nextFloat() * 2.0f - 1.0f) * f4;
            this.FT = (particlesModule.FI.nextFloat() * 2.0f - 1.0f) * f4;
            this.FU = (particlesModule.FI.nextFloat() * 2.0f - 1.0f) * f4;
        }

        void tick() {
            this.prevPos = this.pos;
            this.pos = this.pos.method_1019(this.velocity);
            this.velocity = this.velocity.method_1021(0.99);
            --this.FO;
            this.FP = this.rotX;
            this.FQ = this.rotY;
            this.FR = this.rotZ;
            this.rotX += this.FS;
            this.rotY += this.FT;
            this.rotZ += this.FU;
        }

        boolean dz() {
            return this.FO <= 0;
        }

        int t(float f) {
            float f2 = class_3532.method_15363((float)(((float)this.FO - f) / (float)this.FN), (float)0.0f, (float)1.0f);
            int n = this.FL >>> 24 & 0xFF;
            return class_3532.method_15340((int)((int)Math.round((double)n * Math.sin((double)f2 * Math.PI))), (int)0, (int)255);
        }

        float u(float f) {
            return 1.0f - class_3532.method_15363((float)(((float)this.FO - f) / (float)this.FN), (float)0.0f, (float)1.0f);
        }

        class_243 v(float f) {
            double d = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            return this.prevPos.method_35590(this.pos, d);
        }

        float w(float f) {
            return class_3532.method_16439((float)f, (float)this.FP, (float)this.rotX);
        }

        float x(float f) {
            return class_3532.method_16439((float)f, (float)this.FQ, (float)this.rotY);
        }

        float y(float f) {
            return class_3532.method_16439((float)f, (float)this.FR, (float)this.rotZ);
        }
    }
}

