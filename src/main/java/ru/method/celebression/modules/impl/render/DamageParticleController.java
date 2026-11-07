package ru.method.celebression.modules.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IWorldRenderer;
import ru.method.celebression.util.BlockLineOfSightChecker;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public final class DamageParticleController
implements MinecraftClientAccess {
    static final class_2960 Jz = Celebression.id("client/particles/star.png");
    static final class_2960 JA = Celebression.id("client/particles/firefly.png");
    private static final long JB = 50L;
    private static final double JC = 10000.0;
    private static final long JD = 2800L;
    private static final double JE = 0.04;
    private static final double JF = 0.92;
    private static final double JG = 0.65;
    private static final double JH = 0.55;
    private static final double JI = 0.45;
    private static final double JJ = 0.005;
    private static final double JK = 0.12;
    private final SliderSetting JL = new SliderSetting("settings.damageparticles.count", 30.0f, 10.0f, 60.0f, 1.0f);
    private final SliderSetting JM = new SliderSetting("settings.damageparticles.size", 30.0f, 10.0f, 55.0f, 1.0f);
    private final List<DamageParticle> JN = new ArrayList<DamageParticle>();
    private final Object JO = new Object();
    private final Supplier<Color> JP;
    private Color FK;
    private long lastColorUpdate;

    public DamageParticleController() {
        this(ColorUtils::getGlobalColor);
    }

    public DamageParticleController(Supplier<Color> supplier) {
        this.JP = supplier == null ? ColorUtils::getGlobalColor : supplier;
    }

    public List<Setting<?>> getSettings() {
        return List.of(this.JL, this.JM);
    }

        public void clear() {
        Object object = this.JO;
        synchronized (object) {
            this.JN.clear();
        }
    }

        public void f(class_1297 class_12972) {
        if (!(class_12972 instanceof class_1309)) {
            return;
        }
        class_1309 class_13092 = (class_1309)class_12972;
        if (DamageParticleController.aiG.field_1724 == null || class_13092 == DamageParticleController.aiG.field_1724 || !class_13092.method_5805()) {
            return;
        }
        class_243 class_2432 = class_13092.method_19538().method_1031(0.0, (double)(class_13092.method_17682() * 0.5f), 0.0);
        Object object = this.JO;
        synchronized (object) {
            int n = ((Float)this.JL.jT()).intValue();
            for (int i = 0; i < n; ++i) {
                this.JN.add(new DamageParticle(this, class_2432));
            }
        }
    }

        public void jQ() {
        if (DamageParticleController.aiG.field_1724 == null || DamageParticleController.aiG.field_1687 == null) {
            return;
        }
        Object object = this.JO;
        synchronized (object) {
            Iterator<DamageParticle> iterator = this.JN.iterator();
            Color color = this.iC();
            long l = System.currentTimeMillis();
            while (iterator.hasNext()) {
                DamageParticle damageParticle = iterator.next();
                if (damageParticle.d(l, 2800L) || DamageParticleController.aiG.field_1724.method_19538().method_1025(damageParticle.pos) > 10000.0) {
                    iterator.remove();
                    continue;
                }
                damageParticle.r(l);
                if (!this.j(damageParticle.pos)) continue;
                if (l - damageParticle.Jn >= 100L) {
                    damageParticle.FV = BlockLineOfSightChecker.a(DamageParticleController.aiG.field_1773.method_19418().method_19326(), damageParticle.pos);
                    damageParticle.Jn = l;
                }
                if (!damageParticle.FV) continue;
                float f = damageParticle.b(l, 2800L);
                float f2 = damageParticle.c(l, 2800L);
                if (f2 <= 0.01f) {
                    iterator.remove();
                    continue;
                }
                this.a(damageParticle, color, f2, f);
            }
        }
    }

    private boolean j(class_243 class_2432) {
        return ((IWorldRenderer)DamageParticleController.aiG.field_1769).getFrustum().method_23093(new class_238(class_2432.method_1023(0.5, 0.5, 0.5), class_2432.method_1031(0.5, 0.5, 0.5)));
    }

    private Color jm() {
        long l = System.currentTimeMillis();
        if (this.FK == null || l - this.lastColorUpdate > 50L) {
            this.FK = ColorUtils.getGlobalColor();
            this.lastColorUpdate = l;
        }
        return this.FK;
    }

    private Color iC() {
        Color color = this.JP.get();
        return color == null ? this.jm() : color;
    }

    private void a(DamageParticle damageParticle, Color color, float f, float f2) {
        int n = class_3532.method_15340((int)((int)(f * 255.0f)), (int)0, (int)255);
        if (n <= 3) {
            return;
        }
        float f3 = class_3532.method_15363((float)(((Float)this.JM.jT()).floatValue() / 55.0f), (float)0.15f, (float)1.2f);
        float f4 = Math.max(0.04f, f3 * damageParticle.JV * (1.0f - f2 * 0.58f));
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        Color color3 = ColorUtils.fade(color2, new Color(255, 255, 255, n), 0.2f);
        Color color4 = ColorUtils.fade(color2, new Color(255, 255, 255, n), 0.48f);
        int n2 = class_3532.method_15340((int)((int)((float)n * (0.55f + damageParticle.JZ * 0.35f))), (int)0, (int)255);
        Color color5 = new Color(255, 255, 255, n2);
        Render3D.renderBillboardTexture(damageParticle.pos, f4 * (1.85f + damageParticle.JZ * 0.35f), JA, color3.getRGB(), true, true);
        Render3D.renderBillboardTexture(damageParticle.pos, f4 * (1.02f + damageParticle.JZ * 0.08f), damageParticle.JR, color4.getRGB(), true, true);
        Render3D.renderBillboardTexture(damageParticle.pos, f4 * 0.52f, Jz, color5.getRGB(), true, true);
    }

    final class DamageParticle {
        class_243 pos;
        private class_243 velocity;
        private final long JQ;
        private long lastUpdateTime;
        final class_2960 JR;
        private final float JS;
        private float JT = 1.0f;
        private boolean JU;
        final float JV;
        private final long JW;
        private final class_243 JX;
        private final double JY;
        final float JZ;
        boolean FV;
        long Jn;

        DamageParticle(DamageParticleController damageParticleController, class_243 class_2432) {
            this.JX = class_2432;
            double d = MathUtils.x(0.0f, (float)Math.PI * 2);
            double d2 = MathUtils.x(0.55f, 0.85f);
            double d3 = MathUtils.x(0.41f, 0.58f);
            double d4 = MathUtils.x(-0.2f, 0.2f);
            double d5 = MathUtils.x(-0.12f, 0.16f);
            double d6 = Math.cos(d + d4) * d2;
            double d7 = Math.sin(d + d4) * d2;
            double d8 = d3 + d5;
            this.pos = class_2432;
            this.velocity = new class_243(d6, d8, d7).method_1031((double)MathUtils.x(-0.04f, 0.04f), 0.0, (double)MathUtils.x(-0.04f, 0.04f));
            this.lastUpdateTime = this.JQ = System.currentTimeMillis();
            this.JR = this.jR();
            this.JS = MathUtils.x(0.75f, 1.0f);
            this.JV = MathUtils.x(0.85f, 1.15f);
            this.JW = MathUtils.ad(2100, 2800);
            this.JY = MathUtils.x(2.2f, 3.15f);
            this.JZ = MathUtils.x(0.0f, 1.0f);
        }

        void r(long l) {
            double d = Math.max(0.01, (double)(l - this.lastUpdateTime) / 50.0);
            this.lastUpdateTime = l;
            if (!this.JU || this.velocity.field_1351 > 0.0) {
                this.velocity = this.velocity.method_1031(0.0, -0.04 * d, 0.0);
            }
            class_243 class_2432 = this.velocity.method_1021(d);
            class_243 class_2433 = this.pos.method_1019(class_2432);
            this.pos = this.n(class_2433);
            double d2 = this.JU ? 0.65 : 0.92;
            this.velocity = this.velocity.method_18805(d2, d2, d2);
            if (!this.JU) {
                class_243 class_2434 = this.pos.method_1020(this.JX);
                class_243 class_2435 = new class_243(class_2434.field_1352, 0.0, class_2434.field_1350);
                double d3 = class_2435.method_1033();
                if (d3 > 0.001) {
                    double d4 = Math.min(this.JY, d3 + 0.01);
                    double d5 = class_3532.method_15350((double)((d4 - d3) * 0.08), (double)-0.06, (double)0.06);
                    class_243 class_2436 = class_2435.method_1029().method_1021(d5).method_1031((double)MathUtils.x(-0.005f, 0.005f), 0.0, (double)MathUtils.x(-0.005f, 0.005f));
                    this.velocity = this.velocity.method_1019(class_2436);
                } else {
                    this.velocity = this.velocity.method_1031((double)MathUtils.x(-0.02f, 0.02f), 0.0, (double)MathUtils.x(-0.02f, 0.02f));
                }
            } else {
                this.velocity = this.velocity.method_1031((double)MathUtils.x(-0.004f, 0.004f), 0.0, (double)MathUtils.x(-0.004f, 0.004f));
            }
            if (this.JU && this.velocity.method_1027() < 2.5E-5) {
                this.velocity = class_243.field_1353;
                this.JT *= 0.92f;
            } else if (!this.JU) {
                this.JT = Math.min(1.0f, this.JT + 0.01f);
            }
        }

        private class_243 n(class_243 class_2432) {
            class_2338 class_23382;
            class_243 class_2433 = class_2432;
            class_2338 class_23383 = class_2338.method_49637((double)class_2433.field_1352, (double)(class_2433.field_1351 - 0.12), (double)class_2433.field_1350);
            class_2680 class_26802 = MinecraftClientAccess.aiG.field_1687.method_8320(class_23383);
            if (!class_26802.method_26215()) {
                class_2433 = new class_243(class_2433.field_1352, (double)class_23383.method_10264() + 1.0 + 0.12, class_2433.field_1350);
                this.velocity = new class_243(this.velocity.field_1352 * 0.65, -this.velocity.field_1351 * 0.55, this.velocity.field_1350 * 0.65);
                this.JU = true;
            } else {
                this.JU = false;
            }
            if (Math.abs(this.velocity.field_1352) > 0.005 && !MinecraftClientAccess.aiG.field_1687.method_8320(class_23382 = class_2338.method_49637((double)(class_2433.field_1352 + Math.signum(this.velocity.field_1352) * 0.12), (double)class_2433.field_1351, (double)class_2433.field_1350)).method_26215()) {
                class_2433 = new class_243(this.pos.field_1352, class_2433.field_1351, class_2433.field_1350);
                this.velocity = new class_243(-this.velocity.field_1352 * 0.45, this.velocity.field_1351, this.velocity.field_1350);
            }
            if (Math.abs(this.velocity.field_1350) > 0.005 && !MinecraftClientAccess.aiG.field_1687.method_8320(class_23382 = class_2338.method_49637((double)class_2433.field_1352, (double)class_2433.field_1351, (double)(class_2433.field_1350 + Math.signum(this.velocity.field_1350) * 0.12))).method_26215()) {
                class_2433 = new class_243(class_2433.field_1352, class_2433.field_1351, this.pos.field_1350);
                this.velocity = new class_243(this.velocity.field_1352, this.velocity.field_1351, -this.velocity.field_1350 * 0.45);
            }
            return class_2433;
        }

        float b(long l, long l2) {
            long l3 = Math.min(l2, this.JW);
            return class_3532.method_15363((float)((float)(l - this.JQ) / (float)l3), (float)0.0f, (float)1.0f);
        }

        float c(long l, long l2) {
            float f = this.b(l, l2);
            float f2 = 1.0f - f;
            if (this.JU) {
                f2 *= 0.85f;
            }
            return class_3532.method_15363((float)(f2 * this.JS * this.JT), (float)0.0f, (float)1.0f);
        }

        boolean d(long l, long l2) {
            return l - this.JQ > Math.min(l2, this.JW) || this.JT <= 0.02f;
        }

        private class_2960 jR() {
            return MathUtils.ad(0, 1) == 0 ? Jz : JA;
        }
    }
}

