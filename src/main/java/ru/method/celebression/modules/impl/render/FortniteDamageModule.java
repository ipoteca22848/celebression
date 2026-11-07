package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_5134;
import net.minecraft.class_7833;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.builders.Builder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltText;
import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.api.event.AttackEntityEvent;
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
import ru.method.celebression.utils.render.fonts.Fonts;

public class FortniteDamageModule
extends Module {
    private final EnumSetting<DamageType> As = new EnumSetting<DamageType>("\u0421\u0442\u0438\u043b\u044c", DamageType.AN);
    private final SliderSetting At = new SliderSetting("\u0420\u0430\u0437\u043c\u0435\u0440", 0.032f, 0.012f, 0.08f, 0.001f);
    private final SliderSetting Au = new SliderSetting("\u0412\u0440\u0435\u043c\u044f \u043f\u043e\u043a\u0430\u0437\u0430", 850.0f, 300.0f, 1800.0f, 50.0f);
    private final SliderSetting Av = new SliderSetting("\u041f\u043e\u0434\u044a\u0451\u043c", 0.42f, 0.0f, 1.5f, 0.05f);
    private final BooleanSetting Aw = new BooleanSetting("\u0426\u0432\u0435\u0442 \u0442\u0435\u043c\u044b", false);
    private final ColorSetting Ax = new ColorSetting("\u0426\u0432\u0435\u0442", new Color(255, 255, 255), () -> (Boolean)this.Aw.jT() == false);
    private final BooleanSetting Ay = new BooleanSetting("\u0416\u0451\u043b\u0442\u0430\u044f \u0446\u0438\u0444\u0440\u0430 \u043f\u0440\u0438 \u043a\u0440\u0438\u0442\u0435", true);
    private final List<AnimationState> Az = new ArrayList<AnimationState>();
    private final List<DamageEntry> AA = new ArrayList<DamageEntry>();

    public FortniteDamageModule() {
        super("FortniteDamage", ModuleCategory.ql);
        this.getSettings().add(this.As);
        this.getSettings().add(this.At);
        this.getSettings().add(this.Au);
        this.getSettings().add(this.Av);
        this.getSettings().add(this.Aw);
        this.getSettings().add(this.Ax);
        this.getSettings().add(this.Ay);
    }

        @Override
    public void onDisable() {
        super.onDisable();
        synchronized (this.Az) {
            this.Az.clear();
        }
        synchronized (this.AA) {
            this.AA.clear();
        }
    }

        @EventHandler
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        class_1309 class_13092;
        if (FortniteDamageModule.aiG.field_1724 == null || FortniteDamageModule.aiG.field_1687 == null) {
            return;
        }
        class_1297 class_12972 = attackEntityEvent.b();
        if (!(class_12972 instanceof class_1309) || (class_13092 = (class_1309)class_12972) == FortniteDamageModule.aiG.field_1724) {
            return;
        }
        List<AnimationState> list = this.Az;
        synchronized (list) {
            this.Az.add(new AnimationState(class_13092, this.a(class_13092), this.a((class_1297)FortniteDamageModule.aiG.field_1724, (class_1297)class_13092), this.a((class_1657)FortniteDamageModule.aiG.field_1724, (class_1297)class_13092), System.currentTimeMillis()));
            this.Az.removeIf(AnimationState::dz);
        }
    }

        @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (FortniteDamageModule.aiG.field_1724 == null || FortniteDamageModule.aiG.field_1687 == null) {
            return;
        }
        List<DamageEntry> newEntries = new ArrayList<>();
        HashSet<Integer> processedEntities = new HashSet<>();
        synchronized (this.Az) {
            this.Az.removeIf(animationState -> {
                if (animationState.dz()) {
                    return true;
                }
                if (animationState.AI.method_31481() || !animationState.AI.method_5805()) {
                    if (!processedEntities.add(animationState.AI.method_5628())) {
                        return true;
                    }
                    float f = this.t(animationState.AL);
                    float f2 = Math.max(0.0f, animationState.AJ);
                    if (f > 0.05f) {
                        f2 = Math.min(f2, f);
                    }
                    if (f2 > 0.05f) {
                        newEntries.add(this.a(animationState, f2));
                    }
                    return true;
                }
                float f = animationState.AJ - this.a(animationState.AI);
                if (f > 0.05f) {
                    newEntries.add(this.a(animationState, f));
                    return true;
                }
                return false;
            });
        }
        synchronized (this.AA) {
            if (!newEntries.isEmpty()) {
                this.AA.addAll(newEntries);
            }
            this.AA.removeIf(DamageEntry::iI);
        }
    }

        @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        ArrayList<DamageEntry> arrayList;
        if (FortniteDamageModule.aiG.field_1724 == null || FortniteDamageModule.aiG.field_1687 == null) {
            return;
        }
        synchronized (this.AA) {
            if (this.AA.isEmpty()) {
                return;
            }
            arrayList = new ArrayList<DamageEntry>(this.AA);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.disableCull();
        class_4184 class_41842 = FortniteDamageModule.aiG.field_1773.method_19418();
        class_243 class_2432 = class_41842.method_19326();
        class_4587 class_45872 = matrixStackStage.l();
        MsdfFont msdfFont = Fonts.FORT.font();
        float f = 10.8f;
        for (DamageEntry damageEntry : arrayList) {
            float f2 = damageEntry.iJ();
            if (f2 <= 0.01f) continue;
            class_243 class_2433 = damageEntry.iK();
            class_45872.method_22903();
            class_45872.method_22904(class_2433.field_1352 - class_2432.field_1352, class_2433.field_1351 - class_2432.field_1351, class_2433.field_1350 - class_2432.field_1350);
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(-class_41842.method_19330()));
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(class_41842.method_19329()));
            float f3 = ((Float)this.At.jT()).floatValue();
            if (this.As.jT() == DamageType.AN) {
                f3 *= damageEntry.getScale();
            }
            class_45872.method_22905(-f3, -f3, f3);
            String string = damageEntry.AB;
            boolean bl = damageEntry.AD && (Boolean)this.Ay.jT() != false;
            Color color = this.a(bl, f2);
            float f4 = msdfFont.getWidth(string, f);
            Color color2 = new Color(0, 0, 0, Math.round(f2 * 255.0f));
            float f5 = 0.18f;
            BuiltText builtText = (BuiltText)Builder.text().font(msdfFont).text(string).size(f).thickness(0.05f).color(color).outline(color2, f5).build();
            Matrix4f matrix4f = class_45872.method_23760().method_23761();
            try {
                builtText.render(matrix4f, -f4 / 2.0f, -f / 2.0f);
            }
            catch (Throwable throwable) {
            }
            class_45872.method_22909();
        }
        RenderSystem.enableCull();
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private DamageEntry a(AnimationState animationState, float f) {
        return new DamageEntry(animationState.AK, Math.round(f), f, animationState.AL, System.currentTimeMillis(), (long)((Float)this.Au.jT()).floatValue(), ((Float)this.Av.jT()).floatValue());
    }

    private float a(class_1309 class_13092) {
        return class_13092.method_6032() + class_13092.method_6067();
    }

    private float t(boolean bl) {
        if (FortniteDamageModule.aiG.field_1724 == null) {
            return 0.0f;
        }
        double d = FortniteDamageModule.aiG.field_1724.method_45325(class_5134.field_23721);
        if (bl) {
            d *= 1.5;
        }
        return (float)d;
    }

    private boolean a(class_1657 class_16572, class_1297 class_12972) {
        if (!(class_12972 instanceof class_1309)) {
            return false;
        }
        if (class_16572.method_24828() || class_16572.method_6101() || class_16572.method_5799() || class_16572.method_5765() || class_16572.method_5624()) {
            return false;
        }
        if (class_16572.method_6059(class_1294.field_5919)) {
            return false;
        }
        return class_16572.field_6017 > 0.0f || class_16572.method_18798().field_1351 < -0.06;
    }

    private Color a(boolean bl, float f) {
        int n = Math.round(f * 255.0f);
        if (bl) {
            return new Color(252, 254, 60, n);
        }
        if (((Boolean)this.Aw.jT()).booleanValue()) {
            Color color = ColorUtils.getPrimaryColor();
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        }
        Color color = (Color)this.Ax.jT();
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private class_243 a(class_1297 class_12972, class_1297 class_12973) {
        class_243 class_2432 = new class_243(class_12973.method_23317(), class_12973.method_23318() + (double)class_12973.method_17682() / 2.0, class_12973.method_23321());
        if (class_12972 == null) {
            return class_2432;
        }
        class_243 class_2433 = class_12972.method_5836(1.0f);
        class_243 class_2434 = class_12972.method_5828(1.0f);
        class_243 class_2435 = class_12973.method_5829().method_1005();
        double d = Math.max(class_2433.method_1022(class_2435) + 1.0, 6.0);
        class_243 class_2436 = class_2433.method_1019(class_2434.method_1021(d));
        Optional<class_243> optional = class_12973.method_5829().method_992(class_2433, class_2436);
        return optional.orElseGet(() -> class_2433.method_1019(class_2434.method_1021(class_2433.method_1022(class_2435))));
    }

    static float easeOutCubic(float f) {
        return 1.0f - (float)Math.pow(1.0f - f, 3.0);
    }

    static float easeOutBack(float f) {
        float f2 = 1.70158f;
        float f3 = f2 + 1.0f;
        return 1.0f + f3 * (float)Math.pow(f - 1.0f, 3.0) + f2 * (float)Math.pow(f - 1.0f, 2.0);
    }

    public static enum DamageType
    implements NamedOption  {
        AN("\u0424\u043e\u0440\u0442\u043d\u0430\u0439\u0442");
        private final String AO;

private DamageType(String string2) {
            this.AO = string2;
        }

        @Override
        public String getName() {
            return this.AO;
        }

}

    static final class AnimationState {
        private static final long AH = 750L;
        final class_1309 AI;
        final float AJ;
        final class_243 AK;
        final boolean AL;
        private final long AM;

        AnimationState(class_1309 class_13092, float f, class_243 class_2432, boolean bl, long l) {
            this.AI = class_13092;
            this.AJ = f;
            this.AK = class_2432;
            this.AL = bl;
            this.AM = l;
        }

        boolean dz() {
            return System.currentTimeMillis() - this.AM > 750L;
        }
    }

    static final class DamageEntry {
        private final class_243 position;
        final String AB;
        private final float AC;
        final boolean AD;
        private final long AE;
        private final long AF;
        private final float AG;

        DamageEntry(class_243 class_2432, int n, float f, boolean bl, long l, long l2, float f2) {
            this.position = class_2432;
            this.AB = String.valueOf(Math.max(1, n));
            this.AC = f;
            this.AD = bl;
            this.AE = l;
            this.AF = l2;
            this.AG = f2;
        }

        private boolean iI() {
            return System.currentTimeMillis() - this.AE >= this.AF;
        }

        private float progress() {
            return Math.min(1.0f, (float)(System.currentTimeMillis() - this.AE) / (float)this.AF);
        }

        float iJ() {
            float f = this.progress();
            if (f < 0.12f) {
                return FortniteDamageModule.easeOutCubic(f / 0.12f);
            }
            if (f > 0.72f) {
                return 1.0f - FortniteDamageModule.easeOutCubic((f - 0.72f) / 0.28f);
            }
            return 1.0f;
        }

        float getScale() {
            float f = this.progress();
            if (f < 0.18f) {
                return 0.45f + 0.55f * FortniteDamageModule.easeOutBack(f / 0.18f);
            }
            return 1.0f;
        }

        class_243 iK() {
            float f = this.progress();
            double d = this.AG * FortniteDamageModule.easeOutCubic(f);
            return this.position.method_1031(0.0, d, 0.0);
        }
    }
}
