package ru.method.celebression.hud;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_2477;
import net.minecraft.class_2561;
import net.minecraft.class_266;
import net.minecraft.class_268;
import net.minecraft.class_269;
import net.minecraft.class_270;
import net.minecraft.class_5250;
import net.minecraft.class_5348;
import net.minecraft.class_5481;
import net.minecraft.class_8646;
import net.minecraft.class_9011;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.modules.impl.render.NoRenderModule;
import ru.method.celebression.modules.impl.client.SecretDupeModule;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public class ScoreboardHud
extends HudElement {
    private static final float kl = 7.0f;
    private static final float km = 2.5f;
    private static final int kn = 11;
    private static final int ko = 10;
    private static final float kp = 132.0f;
    private static final float kq = 10.0f;
    private final ColorSetting kr = new ColorSetting("\u0410\u043a\u0446\u0435\u043d\u0442", new Color(0, 0, 0, 0));
    private ScoreboardLayout ks;
    private float kt;
    private long cacheExpiresAtMs;

    public ScoreboardHud() {
        super("ScoreboardHud");
        this.getSettings().add(this.kr);
    }

    public float cO() {
        return UI.getScoreboardBackgroundColor().getAlpha();
    }

    public void j(float f) {
        UI.setScoreboardBackgroundAlpha(Math.round(Math.max(0.0f, Math.min(255.0f, f))));
    }

    public float cP() {
        return UI.getScoreboardRadius();
    }

    public void k(float f) {
        UI.setScoreboardRadius(f);
    }

    private Color accent() {
        Color color = (Color)this.kr.jT();
        if (color != null && color.getAlpha() > 8) {
            return color;
        }
        return ColorUtils.getPrimaryColor();
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        ScoreboardLayout scoreboardLayout;
        if (ScoreboardHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (!UI.isCustomScoreboardEnabled()) {
            return;
        }
        if (this.cQ()) {
            return;
        }
        long l = System.currentTimeMillis();
        if (this.ks == null || l >= this.cacheExpiresAtMs) {
            float f = (float)aiG.method_22683().method_4486() * 0.45f / this.bI();
            this.ks = this.l(f);
            this.kt = Math.min(this.a(this.ks, this.ks.cV()), f);
            this.cacheExpiresAtMs = l + 50L;
        }
        if ((scoreboardLayout = this.ks).cU().isEmpty()) {
            return;
        }
        boolean bl = scoreboardLayout.cV();
        int n = Math.round(this.getX());
        int n2 = Math.round(this.getY());
        float f = this.bI();
        int n3 = Math.round(Math.max(132.0f, this.kt));
        int n4 = Math.round(14.0f + (float)(11 * (scoreboardLayout.cU().size() + 1)) + 5.0f);
        int n5 = Math.round((float)n3 * f);
        int n6 = Math.round((float)n4 * f);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        int n7 = Math.round((float)n + fArray[0]);
        n7 = Math.min(n7, Math.max(0, aiG.method_22683().method_4486() - n5));
        int n8 = Math.round((float)n2 + fArray[1]);
        Color color = this.accent();
        Color color2 = UI.getScoreboardBackgroundColor();
        float f2 = Math.min(UI.getScoreboardRadius() * f, Math.max(0.0f, (float)Math.min(n5, n6)) * 0.5f);
        if (color2.getAlpha() < 240) {
            Render2D.drawBlurredRect(hudRenderEvent.j().method_51448(), n7, n8, n5, n6, f2, 10.0f, ColorUtils.alpha(color2, Math.round((float)color2.getAlpha() * 0.55f)));
        }
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), n7, n8, n5, n6, f2, color2);
        hudRenderEvent.j().method_51448().method_22903();
        hudRenderEvent.j().method_51448().method_46416((float)n7, (float)n8, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f, f, 1.0f);
        hudRenderEvent.j().method_51448().method_46416((float)(-n7), (float)(-n8), 0.0f);
        float f3 = f > 0.0f ? f2 / f : f2;
        float f4 = f3 - (float)Math.sqrt(Math.max(0.0f, 6.0f * f3 - 9.0f));
        float f5 = Math.max(4.0f, f4 + 1.5f);
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), (float)n7 + 3.0f, (float)n8 + f5, 2.5f, (float)n4 - f5 * 2.0f, 1.25f, ColorUtils.alpha(color, 235));
        float f6 = (float)n7 + 2.5f + 7.0f;
        float f7 = (float)(n7 + n3) - 7.0f;
        int n9 = ScoreboardHud.aiG.field_1772.method_30880(scoreboardLayout.cT());
        int n10 = Math.round(f6 + (f7 - f6 - (float)n9) / 2.0f);
        int n11 = n8 + Math.round(7.0f);
        hudRenderEvent.j().method_51430(ScoreboardHud.aiG.field_1772, scoreboardLayout.cT(), n10, n11, -1, false);
        int n12 = n11 + 11;
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f6, n12, f7 - f6, 0.8f, 0.4f, ColorUtils.alpha(color, 130));
        int n13 = 0xFF000000 | color.getRGB() & 0xFFFFFF;
        int n14 = n12 + 4;
        for (ScoreLine scoreLine : scoreboardLayout.cU()) {
            hudRenderEvent.j().method_51430(ScoreboardHud.aiG.field_1772, scoreLine.cR(), Math.round(f6), n14, -1513240, false);
            if (bl && scoreLine.cS() != null) {
                int n15 = ScoreboardHud.aiG.field_1772.method_27525((class_5348)scoreLine.cS());
                hudRenderEvent.j().method_51439(ScoreboardHud.aiG.field_1772, scoreLine.cS(), Math.round(f7 - (float)n15), n14, n13, false);
            }
            n14 += 11;
        }
        hudRenderEvent.j().method_51448().method_22909();
        hudRenderEvent.j().method_51448().method_22909();
        this.b(n, n2, n5, n6);
        super.onRender2D(hudRenderEvent);
    }

    private ScoreboardLayout l(float f) {
        if (ScoreboardHud.aiG.field_1687 == null) {
            return new ScoreboardLayout(class_5481.field_26385, List.of(), false);
        }
        class_269 class_2692 = ScoreboardHud.aiG.field_1687.method_8428();
        class_266 class_2662 = this.a(class_2692);
        if (class_2662 == null) {
            return new ScoreboardLayout(class_5481.field_26385, List.of(), false);
        }
        List<class_9011> list = class_2692.method_1184(class_2662).stream().filter(class_90112 -> !class_90112.method_55385()).sorted(Comparator.comparingInt(class_9011::comp_2128).reversed().thenComparing(class_9011::comp_2127, String.CASE_INSENSITIVE_ORDER)).limit(15L).toList();
        boolean bl = ScoreboardHud.b(list);
        float f2 = Math.max(40.0f, f - 26.5f);
        ArrayList<ScoreLine> arrayList = new ArrayList<ScoreLine>(list.size());
        for (class_9011 class_90113 : list) {
            class_5250 class_52502 = class_2561.method_43470((String)String.valueOf(class_90113.comp_2128()));
            float f3 = f2 - (bl ? (float)(10 + ScoreboardHud.aiG.field_1772.method_27525((class_5348)class_52502)) : 0.0f);
            class_2561 class_25612 = SecretDupeModule.d((class_2561)class_268.method_1142((class_270)class_2692.method_1164(class_90113.comp_2127()), (class_2561)class_90113.method_55387()));
            arrayList.add(new ScoreLine(this.a(class_25612, Math.round(f3)), (class_2561)class_52502));
        }
        return new ScoreboardLayout(this.a(class_2662.method_1114(), Math.round(f2)), arrayList, bl);
    }

    private class_5481 a(class_2561 class_25612, int n) {
        if (ScoreboardHud.aiG.field_1772.method_27525((class_5348)class_25612) <= n) {
            return class_25612.method_30937();
        }
        String string = "...";
        class_5348 class_53482 = ScoreboardHud.aiG.field_1772.method_1714((class_5348)class_25612, Math.max(0, n - ScoreboardHud.aiG.field_1772.method_1727(string)));
        return class_2477.method_10517().method_30934(class_5348.method_29433((class_5348[])new class_5348[]{class_53482, class_5348.method_29430((String)string)}));
    }

    private static boolean b(List<class_9011> list) {
        if (list.isEmpty()) {
            return false;
        }
        if (list.size() == 1) {
            return list.get(0).comp_2128() != 0;
        }
        boolean bl = true;
        boolean bl2 = true;
        for (int i = 1; i < list.size(); ++i) {
            int n = list.get(i - 1).comp_2128();
            int n2 = list.get(i).comp_2128();
            if (n2 != n - 1) {
                bl = false;
            }
            if (n2 == n) continue;
            bl2 = false;
        }
        return !bl && !bl2;
    }

    private class_266 a(class_269 class_2692) {
        class_266 class_2662;
        class_8646 class_86462;
        class_268 class_2682;
        if (ScoreboardHud.aiG.field_1724 != null && (class_2682 = class_2692.method_1164(ScoreboardHud.aiG.field_1724.method_5820())) != null && (class_86462 = class_8646.method_52622((class_124)class_2682.method_1202())) != null && (class_2662 = class_2692.method_1189(class_86462)) != null) {
            return class_2662;
        }
        return class_2692.method_1189(class_8646.field_45157);
    }

    private float a(ScoreboardLayout scoreboardLayout, boolean bl) {
        int n = ScoreboardHud.aiG.field_1772.method_30880(scoreboardLayout.cT());
        for (ScoreLine scoreLine : scoreboardLayout.cU()) {
            int n2 = ScoreboardHud.aiG.field_1772.method_30880(scoreLine.cR());
            if (bl && scoreLine.cS() != null) {
                n2 += 10 + ScoreboardHud.aiG.field_1772.method_27525((class_5348)scoreLine.cS());
            }
            n = Math.max(n, n2);
        }
        return 9.5f + (float)n + 7.0f + 10.0f;
    }

    private boolean cQ() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        NoRenderModule noRenderModule = celebression.getModuleManager().getModule(NoRenderModule.class);
        return noRenderModule != null && noRenderModule.isToggled() && (Boolean)noRenderModule.Fc.jT() != false;
    }

    static final class ScoreboardLayout {
        private final class_5481 kw;
        private final List<ScoreLine> kx;
        private final boolean ky;

        ScoreboardLayout(class_5481 class_54812, List<ScoreLine> list, boolean bl) {
            this.kw = class_54812;
            this.kx = list;
            this.ky = bl;
        }

        @Override
        public final String toString() {
            return "ScoreboardLayout[" + "title=" + this.kw + ", " + "entries=" + this.kx + ", " + "showScores=" + this.ky + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.kw, this.kx, this.ky);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ScoreboardLayout other)) return false;
            return java.util.Objects.deepEquals(this.kw, other.kw)
                && java.util.Objects.deepEquals(this.kx, other.kx)
                && java.util.Objects.deepEquals(this.ky, other.ky);
        }

        public class_5481 cT() {
            return this.kw;
        }

        public List<ScoreLine> cU() {
            return this.kx;
        }

        public boolean cV() {
            return this.ky;
        }
    }

    static final class ScoreLine {
        private final class_5481 ku;
        private final class_2561 kv;

        ScoreLine(class_5481 class_54812, class_2561 class_25612) {
            this.ku = class_54812;
            this.kv = class_25612;
        }

        @Override
        public final String toString() {
            return "ScoreLine[" + "name=" + this.ku + ", " + "score=" + this.kv + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ku, this.kv);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ScoreLine other)) return false;
            return java.util.Objects.deepEquals(this.ku, other.ku)
                && java.util.Objects.deepEquals(this.kv, other.kv);
        }

        public class_5481 cR() {
            return this.ku;
        }

        public class_2561 cS() {
            return this.kv;
        }
    }
}

