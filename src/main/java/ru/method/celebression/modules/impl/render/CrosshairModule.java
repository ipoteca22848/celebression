package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.authlib.GameProfile;
import java.awt.Color;
import java.util.UUID;
import lombok.Generated;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1675;
import net.minecraft.class_1799;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_3966;
import net.minecraft.class_4587;
import net.minecraft.class_9779;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.Render2D;

public class CrosshairModule
extends Module {
    private final EnumSetting<CrosshairMode> zz = new EnumSetting<CrosshairMode>("settings.crosshair.style", CrosshairMode.zL);
    private final ColorSetting zA = new ColorSetting("settings.crosshair.color", Color.WHITE);
    private final ColorSetting zB = new ColorSetting("settings.crosshair.target_color", new Color(255, 70, 70, 255));
    private final SliderSetting zC = new SliderSetting("settings.crosshair.size", 6.0f, 2.0f, 18.0f, 0.5f);
    private final SliderSetting zD = new SliderSetting("settings.crosshair.gap", 2.0f, 0.0f, 10.0f, 0.5f, () -> this.zz.jT() == CrosshairMode.zL);
    private final SliderSetting zE = new SliderSetting("settings.crosshair.thickness", 1.5f, 0.5f, 5.0f, 0.25f);
    private final BooleanSetting zF = new BooleanSetting("settings.crosshair.dynamic_size", true);
    private final SliderSetting zG = new SliderSetting("settings.crosshair.dynamic_scale", 1.35f, 1.05f, 2.5f, 0.05f, this.zF::jT);
    private final SliderSetting zH = new SliderSetting("settings.crosshair.target_range", 6.0f, 3.0f, 12.0f, 0.5f);
    private final BooleanSetting zI = new BooleanSetting("settings.crosshair.hide_vanilla", true);
    private final Animation zJ = new Animation(180L, 1.0, false, Easing.aiR);
    private float zK = 0.0f;

    public CrosshairModule() {
        super("Crosshair", ModuleCategory.ql);
    }

    public boolean iw() {
        return this.isToggled() && (Boolean)this.zI.jT() != false;
    }

    public void b(class_332 class_3322, class_9779 class_97792) {
        if (!this.isToggled() || CrosshairModule.fullNullCheck() || CrosshairModule.aiG.field_1690.field_1842) {
            return;
        }
        if (!CrosshairModule.aiG.field_1690.method_31044().method_31034()) {
            return;
        }
        float f = class_97792.method_60637(false);
        class_1309 class_13092 = this.q(f);
        this.zJ.i(class_13092 != null);
        float f2 = (float)aiG.method_22683().method_4486() / 2.0f;
        float f3 = (float)aiG.method_22683().method_4502() / 2.0f;
        float f4 = 0.0f;
        if (((Boolean)this.zF.jT()).booleanValue()) {
            f4 = 1.0f - class_3532.method_15363((float)CrosshairModule.aiG.field_1724.method_7261(0.0f), (float)0.0f, (float)1.0f);
        }
        this.zK += (f4 - this.zK) * 0.1f;
        float f5 = 1.0f + this.zK * (((Float)this.zG.jT()).floatValue() - 1.0f);
        float f6 = ((Float)this.zC.jT()).floatValue() * f5;
        float f7 = ((Float)this.zD.jT()).floatValue() * f5;
        float f8 = ((Float)this.zE.jT()).floatValue();
        Color color = class_13092 != null ? (Color)this.zB.jT() : (Color)this.zA.jT();
        ColorRGBA colorRGBA = new ColorRGBA(color);
        class_4587 class_45872 = class_3322.method_51448();
        switch (((CrosshairMode)this.zz.jT()).ordinal()) {
            case 0: {
                this.a(class_45872, f2, f3, f6, f7, f8, colorRGBA);
                break;
            }
            case 1: {
                Render2D.drawRing(class_45872, f2, f3, f6, f8, color);
                break;
            }
            case 2: {
                float f9 = Math.max(1.0f, f6 * 0.3f);
                Render2D.drawRoundedRect(class_45872, f2 - f9, f3 - f9, f9 * 2.0f, f9 * 2.0f, f9, color);
            }
        }
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA) {
        this.b(class_45872, f, f2 - f4 - f3, f, f2 - f4, f5, colorRGBA);
        this.b(class_45872, f, f2 + f4, f, f2 + f4 + f3, f5, colorRGBA);
        this.b(class_45872, f - f4 - f3, f2, f - f4, f2, f5, colorRGBA);
        this.b(class_45872, f + f4, f2, f + f4 + f3, f2, f5, colorRGBA);
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, ColorRGBA colorRGBA) {
        Render2D.drawLine(class_45872, f, f2, f3, f4, f5, colorRGBA);
    }

    private class_1309 q(float f) {
        class_1309 class_13092 = this.a(CrosshairModule.aiG.field_1765);
        if (class_13092 != null) {
            return class_13092;
        }
        return this.r(f);
    }

    private class_1309 a(class_239 class_2392) {
        if (class_2392 == null || class_2392.method_17783() != class_239.class_240.field_1331) {
            return null;
        }
        class_1297 class_12972 = ((class_3966)class_2392).method_17782();
        return this.b(class_12972);
    }

    private class_1309 r(float f) {
        class_238 class_2382;
        class_243 class_2432;
        class_243 class_2433;
        if (CrosshairModule.fullNullCheck()) {
            return null;
        }
        double d = ((Float)this.zH.jT()).floatValue();
        class_243 class_2434 = CrosshairModule.aiG.field_1724.method_5836(f);
        class_3966 class_39662 = class_1675.method_18075((class_1297)CrosshairModule.aiG.field_1724, (class_243)class_2434, (class_243)(class_2433 = class_2434.method_1019((class_2432 = CrosshairModule.aiG.field_1724.method_5828(f)).method_1021(d))), (class_238)(class_2382 = CrosshairModule.aiG.field_1724.method_5829().method_18804(class_2432.method_1021(d)).method_1014(1.0)), this::a, (double)(d * d));
        if (class_39662 == null) {
            return null;
        }
        return this.b(class_39662.method_17782());
    }

    private boolean a(class_1297 class_12972) {
        return this.b(class_12972) != null;
    }

    private class_1309 b(class_1297 class_12972) {
        class_1309 class_13092;
        if (!(class_12972 instanceof class_1309) || (class_13092 = (class_1309)class_12972) == CrosshairModule.aiG.field_1724) {
            return null;
        }
        if (!class_13092.method_5805() || class_13092.method_31481()) {
            return null;
        }
        if (class_13092 instanceof class_1657) {
            class_1657 class_16572 = (class_1657)class_13092;
            if (this.p(class_16572)) {
                return null;
            }
            if (this.q(class_16572)) {
                return null;
            }
            if (this.n(class_16572)) {
                return null;
            }
        } else if (class_13092.method_5767() || class_13092.method_6059(class_1294.field_5905)) {
            return null;
        }
        if (CrosshairModule.aiG.field_1724.method_5739((class_1297)class_13092) > ((Float)this.zH.jT()).floatValue()) {
            return null;
        }
        return class_13092;
    }

    private boolean n(class_1657 class_16572) {
        if (class_16572 == null) {
            return true;
        }
        boolean bl = class_16572.method_5767() || class_16572.method_6059(class_1294.field_5905);
        return bl && !this.o(class_16572);
    }

    private boolean o(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        if (!class_16572.method_6047().method_7960() || !class_16572.method_6079().method_7960()) {
            return true;
        }
        for (class_1799 class_17992 : class_16572.method_5661()) {
            if (class_17992 == null || class_17992.method_7960()) continue;
            return true;
        }
        return false;
    }

    private boolean p(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getFriendManager() == null) {
            return false;
        }
        GameProfile gameProfile = class_16572.method_7334();
        if (gameProfile == null || gameProfile.getName() == null) {
            return false;
        }
        return celebression.getFriendManager().av(gameProfile.getName());
    }

    private boolean q(class_1657 class_16572) {
        if (class_16572 == null || class_16572 == CrosshairModule.aiG.field_1724 || aiG.method_1562() == null) {
            return false;
        }
        if (ServerDetector.t(class_16572) != 0) {
            return false;
        }
        UUID uUID = class_16572.method_7334() != null ? class_16572.method_7334().getId() : null;
        return this.d(uUID) || this.d(class_16572.method_5667());
    }

    private boolean d(UUID uUID) {
        return uUID != null && uUID.getMostSignificantBits() == 0L && uUID.getLeastSignificantBits() == 0L;
    }

    public static enum CrosshairMode
    implements NamedOption  {
        zL("settings.crosshair.style.cross"),
        zM("settings.crosshair.style.circle"),
        zN("settings.crosshair.style.dot");
        private final String zO;

@Override
        public String getName() {
            return this.zO;
        }

        @Generated
        private CrosshairMode(String string2) {
            this.zO = string2;
        }

}
}

