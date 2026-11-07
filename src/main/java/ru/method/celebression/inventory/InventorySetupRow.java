package ru.method.celebression.inventory;

import java.awt.Color;
import java.util.List;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_490;
import ru.method.celebression.manager.InventorySetupManager;
import ru.method.celebression.screen.InventorySetupManagerScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class InventorySetupRow
implements MinecraftClientAccess {
    private static final int Se = 4;
    private static final int Sf = 22;
    private static final int Sg = 132;
    private static final int Sh = 6;
    private static final int Si = 18;
    private static final int Sj = 18;
    private static final int Sk = 16;
    private static final int Sl = 6;
    private static final int Sm = 8;
    private static final Font Sn = Fonts.SEMIBOLD;
    private static final float So = 8.0f;
    private static final float Sp = 7.0f;
    private static final Color Sq = new Color(18, 18, 20, 228);
    private static final Color Sr = new Color(38, 38, 44, 235);
    private static final Color Ss = new Color(255, 85, 85);
    private static final Color St = new Color(226, 226, 232);
    private static final Color Su = new Color(255, 130, 130);
    private static final Color Sv = new Color(255, 255, 255, 24);
    private static final Color Sw = new Color(255, 255, 255, 22);
    private static final Color Sx = new Color(255, 255, 255, 46);
    private static final Color Sy = new Color(255, 255, 255, 12);
    private static final Color Sz = new Color(235, 235, 240);
    private static final Color SA = new Color(150, 150, 160);

    private InventorySetupRow() {
    }

    private static int ao(int n) {
        return n - 22 - 4;
    }

    private static boolean a(class_490 class_4902, int n, int n2) {
        return InventorySetupRow.ao(n) >= 0 && n == (class_4902.field_22789 - n2) / 2;
    }

    private static boolean a(double d, double d2, int n, int n2, int n3, int n4) {
        return d >= (double)n && d < (double)(n + n3) && d2 >= (double)n2 && d2 < (double)(n2 + n4);
    }

    private static RowState a(class_490 class_4902, int n, int n2, int n3) {
        if (!InventorySetupManager.mm() || InventorySetupManager.mj() == null) {
            return null;
        }
        List<InventorySetupManager.SetupEntry> list = InventorySetupManager.mo();
        int n4 = InventorySetupRow.aiG.field_1724 == null || InventorySetupRow.aiG.field_1724.method_6026().isEmpty() || !class_4902.method_64507() ? 0 : (class_4902.field_22789 - (n + n3 + 2) >= 120 ? 124 : 36);
        int n5 = n + n3 + 6 + n4;
        if (n5 + 132 > class_4902.field_22789) {
            return null;
        }
        int n6 = class_4902.field_22790 - n2 - 18 - 12 - 16 - 10;
        int n7 = Math.max(1, Math.min(8, n6 / 18));
        int n8 = Math.min(list.size(), n7);
        int n9 = list.size() - n8;
        int n10 = 18 + n8 * 18 + (n9 > 0 ? 10 : 0) + 12 + 16;
        return new RowState(n5, n2, n10, n8, n9);
    }

    public static void a(class_332 class_3322, class_490 class_4902, int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        RowState rowState;
        boolean bl;
        if (InventorySetupRow.aiG.field_1724 == null) {
            return;
        }
        class_4587 class_45872 = class_3322.method_51448();
        if (InventorySetupRow.a(class_4902, n, n3)) {
            int n8 = InventorySetupRow.ao(n);
            bl = InventorySetupRow.a(n4, n5, n8, n2, 22, 22);
            Render2D.drawRoundedRect(class_45872, n8, n2, 22.0f, 22.0f, 5.0f, bl ? Sr : Sq);
            ClickGuiIcons.cube(class_45872, (float)n8 + 11.0f, (float)n2 + 11.0f, 12.0f, Sz);
        }
        if ((rowState = InventorySetupRow.a(class_4902, n, n2, n3)) == null) {
            return;
        }
        Render2D.drawRoundedRect(class_45872, rowState.x(), rowState.y(), 132.0f, rowState.kU(), 5.0f, Sq);
        bl = rowState.mg() == 0 && rowState.mh() == 0;
        Render2D.drawFont(class_45872, Sn.getFont(8.0f), bl ? "\u0412\u0441\u0451 \u043d\u0430 \u043c\u0435\u0441\u0442\u0435" : "\u041d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442:", (float)(rowState.x() + 6), (float)rowState.y() + (18.0f - Sn.getHeight(8.0f)) / 2.0f, bl ? MenuChrome.accent() : Ss);
        List<InventorySetupManager.SetupEntry> list = InventorySetupManager.mo();
        float f = (18.0f - Sn.getHeight(7.0f)) / 2.0f;
        for (n7 = 0; n7 < rowState.mg(); ++n7) {
            InventorySetupManager.SetupEntry setupEntry = list.get(n7);
            n6 = rowState.kW() + n7 * 18;
            if (InventorySetupRow.a(n4, n5, rowState.x() + 2, n6, 128, 18)) {
                Render2D.drawRoundedRect(class_45872, rowState.x() + 2, n6, 128.0f, 18.0f, 3.0f, Sv);
            }
            InventorySetupRow.a(class_3322, setupEntry.mx(), rowState.x() + 6, n6 + 1);
            String string = "\u00d7" + setupEntry.my();
            float f2 = Sn.getWidth(string, 7.0f);
            float f3 = rowState.x() + 6 + 20;
            float f4 = (float)(rowState.x() + 132 - 6) - f2 - 4.0f - f3;
            String string2 = InventorySetupRow.c(InventorySetupRow.v(setupEntry.mx()), 7.0f, f4);
            Render2D.drawFont(class_45872, Sn.getFont(7.0f), string2, f3, (float)n6 + f, St);
            Render2D.drawFont(class_45872, Sn.getFont(7.0f), string, (float)(rowState.x() + 132 - 6) - f2, (float)n6 + f, Su);
        }
        if (rowState.mh() > 0) {
            Render2D.drawFont(class_45872, Sn.getFont(7.0f), "\u0438 \u0435\u0449\u0451 " + rowState.mh(), (float)(rowState.x() + 6), (float)(rowState.kW() + rowState.mg() * 18) + 1.0f, SA);
        }
        n7 = rowState.mf();
        boolean bl2 = InventorySetupManager.mk();
        n6 = !bl2 && InventorySetupManager.ml() ? 1 : 0;
        boolean bl3 = !bl2 && InventorySetupRow.a(n4, n5, rowState.x() + 6, n7, 120, 16);
        Render2D.drawRoundedRect(class_45872, rowState.x() + 6, n7, 120.0f, 16.0f, 4.0f, n6 != 0 ? Sy : (bl3 ? Sx : Sw));
        String string = bl2 ? "\u0420\u0430\u0441\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u044e\u2026" : (n6 != 0 ? "\u0418\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c \u0440\u0430\u0437\u043b\u043e\u0436\u0435\u043d" : "\u0420\u0430\u0437\u043b\u043e\u0436\u0438\u0442\u044c \u0432\u0441\u0451");
        Render2D.drawFont(class_45872, Sn.getFont(7.0f), string, (float)rowState.x() + 66.0f - Sn.getWidth(string, 7.0f) / 2.0f, (float)n7 + (16.0f - Sn.getHeight(7.0f)) / 2.0f, n6 != 0 ? MenuChrome.accent() : St);
    }

    private static void a(class_332 class_3322, class_1799 class_17992, int n, int n2) {
        if (class_17992 == null || class_17992.method_7960()) {
            return;
        }
        class_3322.method_51427(class_17992, n, n2);
        class_3322.method_51739(class_1921.method_51787(), n, n2, n + 16, n2 + 16, 0x30FFFFFF);
    }

    public static boolean a(class_490 class_4902, int n, int n2, int n3, double d, double d2, int n4) {
        String string;
        if (n4 != 0 || InventorySetupRow.aiG.field_1724 == null) {
            return false;
        }
        if (InventorySetupRow.a(class_4902, n, n3) && InventorySetupRow.a(d, d2, InventorySetupRow.ao(n), n2, 22, 22)) {
            aiG.method_1507((class_437)new InventorySetupManagerScreen(InventorySetupRow.aiG.field_1755));
            return true;
        }
        RowState rowState = InventorySetupRow.a(class_4902, n, n2, n3);
        if (rowState == null || !InventorySetupRow.a(d, d2, rowState.x(), rowState.y(), 132, rowState.kU())) {
            return false;
        }
        if (InventorySetupRow.a(d, d2, rowState.x() + 6, rowState.mf(), 120, 16)) {
            InventorySetupManager.mp();
            return true;
        }
        int n5 = (int)((d2 - (double)rowState.kW()) / 18.0);
        List<InventorySetupManager.SetupEntry> list = InventorySetupManager.mo();
        if (d2 >= (double)rowState.kW() && n5 >= 0 && n5 < rowState.mg() && n5 < list.size() && !(string = InventorySetupRow.v(list.get(n5).mx()).replaceAll("[\\u00a7\\u0000-\\u001f\\u007f]", "").trim()).isEmpty() && InventorySetupRow.aiG.field_1724.field_3944 != null) {
            InventorySetupRow.aiG.field_1724.field_3944.method_45730("ah search " + string);
        }
        return true;
    }

    private static String v(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return "";
        }
        return Render2D.stripObfuscatedFormatting(class_17992.method_7909().method_63680().getString()).trim();
    }

    private static String c(String string, float f, float f2) {
        if (string.isEmpty() || Sn.getWidth(string, f) <= f2) {
            return string;
        }
        while (string.length() > 1 && Sn.getWidth(string + "..", f) > f2) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "..";
    }

    static final class RowState {
        private final int SB;
        private final int SC;
        private final int SD;
        private final int SE;
        private final int SF;

        RowState(int n, int n2, int n3, int n4, int n5) {
            this.SB = n;
            this.SC = n2;
            this.SD = n3;
            this.SE = n4;
            this.SF = n5;
        }

        int kW() {
            return this.SC + 18;
        }

        int mf() {
            return this.SC + this.SD - 6 - 16;
        }

        @Override
        public final String toString() {
            return "RowState[" + "x=" + this.SB + ", " + "y=" + this.SC + ", " + "h=" + this.SD + ", " + "rows=" + this.SE + ", " + "hidden=" + this.SF + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.SB, this.SC, this.SD, this.SE, this.SF);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof RowState other)) return false;
            return java.util.Objects.deepEquals(this.SB, other.SB)
                && java.util.Objects.deepEquals(this.SC, other.SC)
                && java.util.Objects.deepEquals(this.SD, other.SD)
                && java.util.Objects.deepEquals(this.SE, other.SE)
                && java.util.Objects.deepEquals(this.SF, other.SF);
        }

        public int x() {
            return this.SB;
        }

        public int y() {
            return this.SC;
        }

        public int kU() {
            return this.SD;
        }

        public int mg() {
            return this.SE;
        }

        public int mh() {
            return this.SF;
        }
    }
}

