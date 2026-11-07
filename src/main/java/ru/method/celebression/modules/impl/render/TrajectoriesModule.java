package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1044;
import net.minecraft.class_1074;
import net.minecraft.class_1268;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1776;
import net.minecraft.class_1779;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1803;
import net.minecraft.class_1828;
import net.minecraft.class_1835;
import net.minecraft.class_1893;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_6880;
import net.minecraft.class_9304;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public class TrajectoriesModule
extends Module {
    private static final class_2960 HX = Celebression.id("client/xuynya/hit.png");
    private static final float HY = 0.85f;
    private static final double HZ = 0.02;
    private static final double Ia = 4.0;
    private static final String[] Ib = new String[]{"\u0432\u0437\u0440\u044b\u0432", "\u0432\u0437\u0440\u044b\u0432\u043d", "\u0431\u043e\u043c\u0431", "\u0432\u0437\u0440\u044b\u0432\u0447\u0430\u0442", "boom", "blast", "explos", "tnt"};
    private final BooleanSetting Ic = new BooleanSetting("settings.trajectories.explosive_circle", true);
    private final Map<Float, TrajectoryPoint> Id = new HashMap<Float, TrajectoryPoint>();
    private int Ie = -1;
    private float If = Float.NaN;
    private float Ig = Float.NaN;
    private class_1792 Ih;
    private boolean Ii;
    private long Ij;
    private class_1792 Ik;

    public TrajectoriesModule() {
        super("Trajectories", ModuleCategory.ql);
    }

    private boolean b(class_1792 class_17922) {
        return class_17922 instanceof class_1776 || class_17922 instanceof class_1835 || class_17922 instanceof class_1764;
    }

    private float c(class_1792 class_17922) {
        return class_17922 instanceof class_1753 ? 1.0f : 0.4f;
    }

    private float d(class_1792 class_17922) {
        if (class_17922 instanceof class_1828 || class_17922 instanceof class_1803) {
            return 0.5f;
        }
        if (class_17922 instanceof class_1779) {
            return 0.59f;
        }
        if (class_17922 instanceof class_1835) {
            return 2.0f;
        }
        return 1.5f;
    }

    private int e(class_1792 class_17922) {
        if (class_17922 instanceof class_1828 || class_17922 instanceof class_1803 || class_17922 instanceof class_1779) {
            return 20;
        }
        return 0;
    }

    @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (TrajectoriesModule.fullNullCheck() || TrajectoriesModule.aiG.field_1690.field_1842) {
            return;
        }
        if (!TrajectoriesModule.aiG.field_1690.method_31044().method_31034()) {
            return;
        }
        class_1268 class_12682 = this.jK();
        if (class_12682 == null) {
            return;
        }
        boolean bl = (Boolean)TrajectoriesModule.aiG.field_1690.method_42448().method_41753();
        TrajectoriesModule.aiG.field_1690.method_42448().method_41748(Boolean.FALSE);
        class_1799 class_17992 = class_12682 == class_1268.field_5808 ? TrajectoriesModule.aiG.field_1724.method_6047() : TrajectoriesModule.aiG.field_1724.method_6079();
        class_1792 class_17922 = class_17992.method_7909();
        if (class_17922 instanceof class_1753) {
            if (TrajectoriesModule.aiG.field_1724.method_6115() && TrajectoriesModule.aiG.field_1724.method_6030().method_7909() == class_17922) {
                this.a(class_17992, class_17922, 0.0f, matrixStackStage);
            }
        } else if (class_17922 instanceof class_1764 && this.s(class_17992)) {
            this.a(class_17992, class_17922, -10.0f, matrixStackStage);
            this.a(class_17992, class_17922, 0.0f, matrixStackStage);
            this.a(class_17992, class_17922, 10.0f, matrixStackStage);
        } else {
            this.a(class_17992, class_17922, 0.0f, matrixStackStage);
        }
        TrajectoriesModule.aiG.field_1690.method_42448().method_41748(Boolean.valueOf(bl));
    }

    private class_1268 jK() {
        class_1799 class_17992 = TrajectoriesModule.aiG.field_1724.method_6047();
        class_1799 class_17993 = TrajectoriesModule.aiG.field_1724.method_6079();
        if (this.f(class_17992.method_7909())) {
            return class_1268.field_5808;
        }
        if (this.f(class_17993.method_7909())) {
            return class_1268.field_5810;
        }
        return null;
    }

    private boolean f(class_1792 class_17922) {
        return this.b(class_17922) || class_17922 instanceof class_1753;
    }

    private boolean s(class_1799 class_17992) {
        if (!(class_17992.method_7909() instanceof class_1764)) {
            return false;
        }
        try {
            class_9304 class_93042 = class_17992.method_58657();
            for (class_6880 class_68802 : class_93042.method_57534()) {
                if (!class_68802.method_40230().equals(Optional.of(class_1893.field_9108))) continue;
                return class_93042.method_57536(class_68802) > 0;
            }
            return false;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private void a(class_1799 class_17992, class_1792 class_17922, float f, WorldRenderStages.MatrixStackStage matrixStackStage) {
        TrajectoryPoint trajectoryPoint;
        boolean bl;
        float f2 = matrixStackStage.k().method_60637(true);
        float f3 = class_3532.method_17821((float)f2, (float)TrajectoriesModule.aiG.field_1724.field_5982, (float)TrajectoriesModule.aiG.field_1724.method_36454());
        float f4 = class_3532.method_16439((float)f2, (float)TrajectoriesModule.aiG.field_1724.field_6004, (float)TrajectoriesModule.aiG.field_1724.method_36455());
        boolean bl2 = bl = TrajectoriesModule.aiG.field_1724.field_6012 == this.Ie && class_17922 == this.Ih && Math.abs(class_3532.method_15393((float)(f3 - this.If))) <= 0.1f && Math.abs(f4 - this.Ig) <= 0.1f;
        if (!bl) {
            this.Id.clear();
            this.Ie = TrajectoriesModule.aiG.field_1724.field_6012;
            this.Ih = class_17922;
            this.If = f3;
            this.Ig = f4;
        }
        if ((trajectoryPoint = this.Id.get(Float.valueOf(f))) == null) {
            trajectoryPoint = this.a(class_17922, f3 + f, f4, f2);
            this.Id.put(Float.valueOf(f), trajectoryPoint);
        }
        if (trajectoryPoint.jL() == null || trajectoryPoint.jM() == null) {
            return;
        }
        Color color = ColorUtils.getGlobalColor();
        this.a(trajectoryPoint.jL(), trajectoryPoint.jM(), color);
        if (((Boolean)this.Ic.jT()).booleanValue() && this.t(class_17992)) {
            this.a(matrixStackStage, trajectoryPoint.jL(), trajectoryPoint.jM(), color);
        }
    }

    private TrajectoryPoint a(class_1792 class_17922, float f, float f2, float f3) {
        float f4;
        double d = class_3532.method_16436((double)f3, (double)TrajectoriesModule.aiG.field_1724.field_6014, (double)TrajectoriesModule.aiG.field_1724.method_23317());
        double d2 = class_3532.method_16436((double)f3, (double)TrajectoriesModule.aiG.field_1724.field_6036, (double)TrajectoriesModule.aiG.field_1724.method_23318());
        double d3 = class_3532.method_16436((double)f3, (double)TrajectoriesModule.aiG.field_1724.field_5969, (double)TrajectoriesModule.aiG.field_1724.method_23321());
        d2 += (double)TrajectoriesModule.aiG.field_1724.method_18381(TrajectoriesModule.aiG.field_1724.method_18376()) - 0.1;
        float f5 = this.c(class_17922);
        double d4 = -class_3532.method_15374((float)(f * ((float)Math.PI / 180))) * class_3532.method_15362((float)(f2 * ((float)Math.PI / 180))) * f5;
        double d5 = -class_3532.method_15374((float)((f2 - (float)this.e(class_17922)) * ((float)Math.PI / 180))) * f5;
        double d6 = class_3532.method_15362((float)(f * ((float)Math.PI / 180))) * class_3532.method_15362((float)(f2 * ((float)Math.PI / 180))) * f5;
        float f6 = 1.0f;
        if (class_17922 instanceof class_1753 && (f6 = ((f4 = (float)TrajectoriesModule.aiG.field_1724.method_6048() / 20.0f) * f4 + f4 * 2.0f) / 3.0f) > 1.0f) {
            f6 = 1.0f;
        }
        if ((f4 = class_3532.method_15355((float)((float)(d4 * d4 + d5 * d5 + d6 * d6)))) == 0.0f) {
            return new TrajectoryPoint(null, null);
        }
        d4 /= (double)f4;
        d5 /= (double)f4;
        d6 /= (double)f4;
        float f7 = (class_17922 instanceof class_1753 ? f6 * 2.0f : (class_17922 instanceof class_1764 ? 2.2f : 1.0f)) * this.d(class_17922);
        d4 *= (double)f7;
        d5 *= (double)f7;
        d6 *= (double)f7;
        class_243 class_2432 = TrajectoriesModule.aiG.field_1724.method_18798();
        d4 += class_2432.field_1352;
        d5 += class_2432.field_1351;
        d6 += class_2432.field_1350;
        class_243 class_2433 = null;
        class_2350 class_23502 = null;
        for (int i = 0; i < 300; ++i) {
            class_243 class_2434 = new class_243(d, d2, d3);
            if (TrajectoriesModule.aiG.field_1687.method_8320(new class_2338((int)(d += d4), (int)(d2 += d5), (int)(d3 += d6))).method_26204() == class_2246.field_10382) {
                d4 *= 0.8;
                d5 *= 0.8;
                d6 *= 0.8;
            } else {
                d4 *= 0.99;
                d5 *= 0.99;
                d6 *= 0.99;
            }
            d5 = class_17922 instanceof class_1753 || class_17922 instanceof class_1764 ? (d5 -= (double)0.05f) : (d5 -= (double)0.03f);
            class_243 class_2435 = new class_243(d, d2, d3);
            class_3965 class_39652 = TrajectoriesModule.aiG.field_1687.method_17742(new class_3959(class_2434, class_2435, class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)TrajectoriesModule.aiG.field_1724));
            if (class_39652 != null && class_39652.method_17783() == class_239.class_240.field_1332) {
                class_2433 = this.a(class_39652);
                class_23502 = class_39652.method_17780();
                break;
            }
            if (d2 <= -64.0) break;
        }
        return new TrajectoryPoint(class_2433, class_23502);
    }

    private boolean t(class_1799 class_17992) {
        long l = System.currentTimeMillis();
        if (class_17992.method_7909() != this.Ik || l >= this.Ij) {
            this.Ii = this.u(class_17992);
            this.Ik = class_17992.method_7909();
            this.Ij = l + 200L;
        }
        return this.Ii;
    }

    private boolean u(class_1799 class_17992) {
        if (TrajectoriesModule.fullNullCheck() || class_17992 == null) {
            return false;
        }
        String string = class_17992.method_7964().getString();
        if (this.bR(string)) {
            return true;
        }
        String string2 = ServerDetector.so();
        if (this.bR(string2)) {
            return true;
        }
        for (class_1293 class_12932 : TrajectoriesModule.aiG.field_1724.method_6026()) {
            String string3 = class_1074.method_4662((String)((class_1291)class_12932.method_5579().comp_349()).method_5567(), (Object[])new Object[0]);
            if (!this.bR(string3)) continue;
            return true;
        }
        return false;
    }

    private boolean bR(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        String string2 = string.toLowerCase();
        for (String string3 : Ib) {
            if (!string2.contains(string3)) continue;
            return true;
        }
        return false;
    }

    private void a(WorldRenderStages.MatrixStackStage matrixStackStage, class_243 class_2432, class_2350 class_23502, Color color) {
        float f = 120.0f;
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)f);
        double d = 4.0;
        double d2 = class_2432.field_1351 + (class_23502 == class_2350.field_11036 ? 0.02 : 0.01);
        class_243 class_2433 = new class_243(class_2432.field_1352, d2, class_2432.field_1350);
        Render3D.renderCircle(matrixStackStage.l(), class_2433, d, color2);
    }

    private class_243 a(class_3965 class_39652) {
        class_2350 class_23502 = class_39652.method_17780();
        class_243 class_2432 = new class_243((double)class_23502.method_10148(), (double)class_23502.method_10164(), (double)class_23502.method_10165());
        class_243 class_2433 = class_39652.method_17784();
        return class_2433.method_1019(class_2432.method_1021(0.02));
    }

    private void a(class_243 class_2432, class_2350 class_23502, Color color) {
        class_1044 class_10442 = aiG.method_1531().method_4619(HX);
        if (class_10442 != null) {
            class_10442.method_4527(true, false);
        }
        Render3D.renderOrientedTexture(class_2432, 0.85f, HX, color.getRGB(), class_23502, true);
    }

    static final class TrajectoryPoint {
        private final class_243 Il;
        private final class_2350 Im;

        TrajectoryPoint(class_243 class_2432, class_2350 class_23502) {
            this.Il = class_2432;
            this.Im = class_23502;
        }

        @Override
        public final String toString() {
            return "TrajectoryPoint[" + "point=" + this.Il + ", " + "side=" + this.Im + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Il, this.Im);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TrajectoryPoint other)) return false;
            return java.util.Objects.deepEquals(this.Il, other.Il)
                && java.util.Objects.deepEquals(this.Im, other.Im);
        }

        public class_243 jL() {
            return this.Il;
        }

        public class_2350 jM() {
            return this.Im;
        }
    }
}
