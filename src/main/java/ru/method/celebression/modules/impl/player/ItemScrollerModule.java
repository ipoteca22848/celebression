package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_465;
import ru.method.celebression.api.mixins.accessors.IHandledScreen;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ItemScrollerModule
extends Module {
    private static final long vd = 50L;
    private static final int ve = 3;
    private final SliderSetting vf = new SliderSetting("settings.itemscroller.delay", 0.0f, 0.0f, 250.0f, 5.0f);
    private final Set<Integer> vg = new HashSet<Integer>();
    private boolean dragging;
    private ScrollDirection vi = ScrollDirection.vn;
    private class_1799 vj = class_1799.field_8037;
    private double vk;
    private double vl;
    private long vm;

    public ItemScrollerModule() {
        super("ItemScroller", ModuleCategory.qm);
    }

    @Override
    public void onDisable() {
        this.stopDragging();
        super.onDisable();
    }

    public ScrollDirection a(int n, boolean bl, boolean bl2, boolean bl3) {
        if (n != 0) {
            return ScrollDirection.vn;
        }
        if (bl3 && bl) {
            return ScrollDirection.vo;
        }
        if (bl3) {
            return ScrollDirection.vq;
        }
        if (bl2) {
            return ScrollDirection.vp;
        }
        if (bl) {
            return ScrollDirection.vo;
        }
        return ScrollDirection.vn;
    }

    public boolean a(class_465<?> class_4652, class_1735 class_17352, double d, double d2, ScrollDirection scrollDirection) {
        if (scrollDirection == ScrollDirection.vn || !this.a(class_4652, class_17352, scrollDirection)) {
            return false;
        }
        this.dragging = true;
        this.vi = scrollDirection;
        this.vg.clear();
        this.vj = class_17352.method_7677().method_7972();
        this.vk = d;
        this.vl = d2;
        this.vm = 0L;
        this.d(class_4652, class_17352);
        this.hs();
        return true;
    }

    public void stopDragging() {
        this.dragging = false;
        this.vi = ScrollDirection.vn;
        this.vg.clear();
        this.vj = class_1799.field_8037;
        this.vm = 0L;
    }

    public boolean bV() {
        return this.dragging;
    }

    public boolean a(class_465<?> class_4652, double d, double d2, class_1735 class_17352) {
        if (!this.dragging || class_4652 == null) {
            return false;
        }
        long l = this.ht();
        int n = Math.min(3, this.j(l));
        if (n <= 0) {
            this.vk = d;
            this.vl = d2;
            return false;
        }
        boolean bl = false;
        int n2 = (int)Math.round(d);
        int n3 = (int)Math.round(d2);
        int n4 = (int)Math.round(this.vk);
        int n5 = (int)Math.round(this.vl);
        int n6 = n2 - n4;
        int n7 = n3 - n5;
        int n8 = Math.max(Math.abs(n6), Math.abs(n7));
        LinkedHashSet<class_1735> linkedHashSet = new LinkedHashSet<class_1735>();
        if (n8 <= 0) {
            if (class_17352 != null) {
                linkedHashSet.add(class_17352);
            }
        } else {
            for (int i = 0; i <= n8; ++i) {
                double d3 = (double)n4 + (double)n6 * ((double)i / (double)n8);
                double d4 = (double)n5 + (double)n7 * ((double)i / (double)n8);
                class_1735 class_17353 = this.a(class_4652, d3, d4);
                if (class_17353 == null) continue;
                linkedHashSet.add(class_17353);
            }
        }
        if (class_17352 != null) {
            linkedHashSet.add(class_17352);
        }
        for (class_1735 class_17354 : linkedHashSet) {
            if (n <= 0) break;
            if (!this.d(class_4652, class_17354)) continue;
            bl = true;
            --n;
            this.k(l);
        }
        this.vk = d;
        this.vl = d2;
        return bl;
    }

    public boolean a(class_465<?> class_4652, class_1735 class_17352, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5;
        if (!this.isToggled() || ItemScrollerModule.fullNullCheck() || ItemScrollerModule.aiG.field_1761 == null) {
            return false;
        }
        if (class_4652 == null || class_17352 == null) {
            return false;
        }
        if (!class_4652.method_17577().method_34255().method_7960()) {
            return false;
        }
        boolean bl6 = bl4 || bl3;
        boolean bl7 = bl5 = bl6 && bl2;
        if (bl5) {
            return this.a(class_4652, class_17352, bl, false, false);
        }
        if (bl6) {
            return this.a(class_4652, class_17352, bl, true, false);
        }
        if (bl2) {
            return this.a(class_4652, class_17352, bl, true, true);
        }
        if (bl) {
            if (!class_17352.method_7681() || !class_17352.method_7674((class_1657)ItemScrollerModule.aiG.field_1724)) {
                return false;
            }
            return this.a(class_4652, class_17352);
        }
        if (!class_17352.method_7681()) {
            return false;
        }
        if (class_17352.method_7677().method_7947() >= class_17352.method_7677().method_7914()) {
            return false;
        }
        return this.b(class_4652, class_17352);
    }

    private boolean a(class_465<?> class_4652, class_1735 class_17352, boolean bl, boolean bl2, boolean bl3) {
        class_1703 class_17032 = class_4652.method_17577();
        class_1799 class_17992 = class_17352.method_7677();
        if (bl2 && class_17992.method_7960()) {
            return false;
        }
        boolean bl4 = false;
        class_1735 class_17353 = null;
        for (int i = class_17032.field_7761.size() - 1; i >= 0; --i) {
            class_1735 class_17354 = (class_1735)class_17032.field_7761.get(i);
            if (!this.a(class_17354, class_17352, class_17992, bl, bl2)) continue;
            if (bl && class_17354.field_7874 == class_17352.field_7874 && bl2) {
                class_17353 = class_17354;
                continue;
            }
            if (!this.c(class_4652, class_17354)) continue;
            bl4 = true;
            if (!bl3) continue;
            return true;
        }
        if (class_17353 != null && this.c(class_4652, class_17353)) {
            bl4 = true;
        }
        return bl4;
    }

    private boolean a(class_1735 class_17352, class_1735 class_17353, class_1799 class_17992, boolean bl, boolean bl2) {
        if (class_17352 == null || !class_17352.method_7681()) {
            return false;
        }
        if (!class_17352.method_7674((class_1657)ItemScrollerModule.aiG.field_1724)) {
            return false;
        }
        boolean bl3 = this.a(class_17352, class_17353);
        if (bl != bl3) {
            return false;
        }
        if (!bl2) {
            return true;
        }
        return class_1799.method_31577((class_1799)class_17352.method_7677(), (class_1799)class_17992);
    }

    private boolean a(class_465<?> class_4652, class_1735 class_17352) {
        if (!class_17352.method_7681() || !class_17352.method_7674((class_1657)ItemScrollerModule.aiG.field_1724)) {
            return false;
        }
        if (!class_4652.method_17577().method_34255().method_7960()) {
            return false;
        }
        class_1799 class_17992 = class_17352.method_7677();
        if (class_17992.method_7947() > 1 && !class_17352.method_7680(class_17992)) {
            return false;
        }
        return this.a(class_4652, class_17352.field_7874);
    }

    private boolean b(class_465<?> class_4652, class_1735 class_17352) {
        if (!class_17352.method_7681()) {
            return false;
        }
        if (!class_17352.method_7680(class_17352.method_7677())) {
            return false;
        }
        class_1703 class_17032 = class_4652.method_17577();
        class_1799 class_17992 = class_17352.method_7677();
        for (int i = class_17032.field_7761.size() - 1; i >= 0; --i) {
            class_1799 class_17993;
            class_1735 class_17353 = (class_1735)class_17032.field_7761.get(i);
            if (class_17353.field_7874 == class_17352.field_7874 || this.a(class_17353, class_17352) || !class_17353.method_7681() || !class_17353.method_7674((class_1657)ItemScrollerModule.aiG.field_1724) || (class_17993 = class_17353.method_7677()).method_7947() > 1 && !class_17353.method_7680(class_17993) || !class_1799.method_31577((class_1799)class_17993, (class_1799)class_17992)) continue;
            return this.a(class_4652, class_17353.field_7874, class_17352.field_7874);
        }
        return false;
    }

    private boolean a(class_465<?> class_4652, int n) {
        class_1703 class_17032 = class_4652.method_17577();
        if (n < 0 || n >= class_17032.field_7761.size()) {
            return false;
        }
        class_1735 class_17352 = (class_1735)class_17032.field_7761.get(n);
        if (!class_17352.method_7681()) {
            return false;
        }
        class_1799 class_17992 = class_17352.method_7677();
        if (class_17992.method_7947() > 1) {
            this.a(class_4652, n, 0, class_1713.field_7790);
            if (class_17352.method_7681()) {
                this.a(class_4652, n, 0, class_1713.field_7790);
                return false;
            }
            this.a(class_4652, n, 1, class_1713.field_7790);
        }
        this.a(class_4652, n, 0, class_1713.field_7794);
        if (!class_17032.method_34255().method_7960()) {
            this.a(class_4652, n, 0, class_1713.field_7790);
        }
        return true;
    }

    private boolean a(class_465<?> class_4652, int n, int n2) {
        class_1703 class_17032 = class_4652.method_17577();
        if (n < 0 || n >= class_17032.field_7761.size()) {
            return false;
        }
        if (n2 < 0 || n2 >= class_17032.field_7761.size()) {
            return false;
        }
        class_1735 class_17352 = (class_1735)class_17032.field_7761.get(n);
        if (!class_17352.method_7681()) {
            return false;
        }
        class_1799 class_17992 = class_17352.method_7677();
        if (class_17992.method_7947() > 1) {
            this.a(class_4652, n, 1, class_1713.field_7790);
        } else {
            this.a(class_4652, n, 0, class_1713.field_7790);
        }
        this.a(class_4652, n2, 1, class_1713.field_7790);
        if (!class_17032.method_34255().method_7960()) {
            this.a(class_4652, n, 0, class_1713.field_7790);
        }
        return true;
    }

    private boolean c(class_465<?> class_4652, class_1735 class_17352) {
        if (class_17352 == null || !class_17352.method_7681() || !class_17352.method_7674((class_1657)ItemScrollerModule.aiG.field_1724)) {
            return false;
        }
        class_1799 class_17992 = class_17352.method_7677().method_7972();
        this.a(class_4652, class_17352.field_7874, 0, class_1713.field_7794);
        class_1799 class_17993 = class_17352.method_7677();
        return class_17993.method_7960() || !class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993) || class_17993.method_7947() != class_17992.method_7947();
    }

    private boolean a(class_465<?> class_4652, class_1735 class_17352, ScrollDirection scrollDirection) {
        if (!this.isToggled() || ItemScrollerModule.fullNullCheck() || ItemScrollerModule.aiG.field_1761 == null) {
            return false;
        }
        if (scrollDirection == ScrollDirection.vn) {
            return false;
        }
        if (class_4652 == null || class_17352 == null || !class_17352.method_7681()) {
            return false;
        }
        if (!class_4652.method_17577().method_34255().method_7960()) {
            return false;
        }
        if (!class_17352.method_7674((class_1657)ItemScrollerModule.aiG.field_1724)) {
            return false;
        }
        return scrollDirection != ScrollDirection.vp || class_17352.method_7677().method_7947() <= 1 || class_17352.method_7680(class_17352.method_7677());
    }

    private boolean d(class_465<?> class_4652, class_1735 class_17352) {
        boolean bl;
        if (!this.a(class_4652, class_17352, this.vi)) {
            return false;
        }
        if (this.vg.contains(class_17352.field_7874)) {
            return false;
        }
        switch (this.vi.ordinal()) {
            default: {
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1: {
                bl = this.c(class_4652, class_17352);
                break;
            }
            case 2: {
                bl = this.a(class_4652, class_17352);
                break;
            }
            case 3: {
                if (!this.vj.method_7960() && class_1799.method_31577((class_1799)class_17352.method_7677(), (class_1799)this.vj) && this.c(class_4652, class_17352)) {
                    bl = true;
                    break;
                }
                bl = false;
                break;
            }
            case 0: {
                bl = false;
                break;
            }
        }
        if (bl) {
            this.vg.add(class_17352.field_7874);
        }
        return bl;
    }

    private boolean a(class_1735 class_17352, class_1735 class_17353) {
        return class_17352.field_7871 == class_17353.field_7871;
    }

    private void a(class_465<?> class_4652, int n, int n2, class_1713 class_17132) {
        ItemScrollerModule.aiG.field_1761.method_2906(class_4652.method_17577().field_7763, n, n2, class_17132, (class_1657)ItemScrollerModule.aiG.field_1724);
    }

    private int j(long l) {
        long l2 = System.currentTimeMillis();
        if (l2 < this.vm) {
            return 0;
        }
        return 1 + (int)((l2 - this.vm) / l);
    }

    private void hs() {
        this.k(this.ht());
    }

    private long ht() {
        return Math.max(((Float)this.vf.jT()).longValue(), 50L);
    }

    private void k(long l) {
        if (l <= 0L) {
            this.vm = 0L;
            return;
        }
        long l2 = System.currentTimeMillis();
        if (this.vm <= l2) {
            this.vm = l2 + l;
            return;
        }
        this.vm += l;
    }

    private class_1735 a(class_465<?> class_4652, double d, double d2) {
        return ((IHandledScreen)class_4652).invokeGetSlotAt(d, d2);
    }

    public static enum ScrollDirection {
        vn,
        vo,
        vp,
        vq;
}
}
