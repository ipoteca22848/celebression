package ru.method.celebression.inventory;

import ru.method.celebression.api.setting.Setting;

import lombok.Generated;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2868;
import net.minecraft.class_3489;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_9282;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IClientPlayerEntity;
import ru.method.celebression.api.mixins.accessors.IClientPlayerInteractionManager;
import ru.method.celebression.api.mixins.accessors.IHandledScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.network.PacketSuppression;

public final class InventoryUtils
implements MinecraftClientAccess {
    public static int bk(int n) {
        if (n >= 0 && n <= 8) {
            return 36 + n;
        }
        return n;
    }

    public static void a(InventorySearchResult inventorySearchResult, int n, int n2) {
        if (n == -1 || n2 == -1 || n == Celebression.getInstance().getServerManager().fu()) {
            return;
        }
        switch (inventorySearchResult.ordinal()) {
            case 0: {
                InventoryUtils.aiG.field_1724.method_31548().field_7545 = n;
                ((IClientPlayerInteractionManager)InventoryUtils.aiG.field_1761).syncSelectedSlot$drug();
                break;
            }
            case 1: {
                PacketSuppression.b(new class_2868(n));
                break;
            }
            case 2: {
                InventoryUtils.a(InventorySlot.akf, n, n2);
            }
        }
    }

    public static void x(int n, int n2) {
        InventoryUtils.a(n, n2, class_1713.field_7791);
    }

    public static void y(int n, int n2) {
        InventoryUtils.aiG.field_1761.method_2906(0, n, n2, class_1713.field_7790, (class_1657)InventoryUtils.aiG.field_1724);
    }

    public static void a(InventorySlot inventorySlot, int n, int n2) {
        if (n == -1 || n2 == -1) {
            return;
        }
        switch (inventorySlot.ordinal()) {
            case 0: {
                InventoryUtils.a(InventoryUtils.bk(n), 0, class_1713.field_7790);
                InventoryUtils.a(InventoryUtils.bk(n2), 0, class_1713.field_7790);
                InventoryUtils.a(InventoryUtils.bk(n), 0, class_1713.field_7790);
                break;
            }
            case 1: {
                InventoryUtils.a(n, n2, class_1713.field_7791);
            }
        }
    }

    public static void z(int n, int n2) {
        if (n == -1 || n2 == -1) {
            return;
        }
        InventoryUtils.aa(n, n2);
    }

    public static void aa(int n, int n2) {
        if (n == -1 || n2 == -1) {
            return;
        }
        InventoryUtils.b(InventoryUtils.bk(n), 0, class_1713.field_7790);
        InventoryUtils.b(n2, 0, class_1713.field_7790);
        InventoryUtils.b(InventoryUtils.bk(n), 0, class_1713.field_7790);
    }

    public static void a(int n, int n2, class_1713 class_17132) {
        if (InventoryUtils.aiG.field_1724 == null || InventoryUtils.aiG.field_1761 == null || InventoryUtils.aiG.field_1724.field_7512 == null) {
            return;
        }
        InventoryUtils.a(InventoryUtils.aiG.field_1724.field_7512.field_7763, n, n2, class_17132);
    }

    public static void b(int n, int n2, class_1713 class_17132) {
        if (InventoryUtils.aiG.field_1724 == null || InventoryUtils.aiG.field_1761 == null || InventoryUtils.aiG.field_1724.field_7498 == null) {
            return;
        }
        InventoryUtils.a(InventoryUtils.aiG.field_1724.field_7498.field_7763, n, n2, class_17132);
    }

    public static void a(int n, int n2, int n3, class_1713 class_17132) {
        if (InventoryUtils.aiG.field_1724 == null || InventoryUtils.aiG.field_1761 == null) {
            return;
        }
        InventoryUtils.rO();
        InventoryUtils.bl(n2);
        InventoryUtils.aiG.field_1761.method_2906(n, n2, n3, class_17132, (class_1657)InventoryUtils.aiG.field_1724);
        InventoryUtils.bl(n2);
    }

    public static void rO() {
        if (InventoryUtils.aiG.field_1724 == null || InventoryUtils.aiG.field_1724.field_3944 == null) {
            return;
        }
        if (!InventoryUtils.aiG.field_1724.method_5624()) {
            return;
        }
        InventoryUtils.aiG.field_1724.method_5728(false);
        ((IClientPlayerEntity)InventoryUtils.aiG.field_1724).sendSprintingPacket$drug();
    }

    public static void bl(int n) {
        class_437 class_4372 = InventoryUtils.aiG.field_1755;
        if (!(class_4372 instanceof class_465)) {
            return;
        }
        class_465 class_4652 = (class_465)class_4372;
        InventoryUtils.c(class_4652, n);
    }

    public static double[] c(class_465<?> class_4652, int n) {
        if (aiG == null || aiG.method_22683() == null || class_4652 == null) {
            return null;
        }
        class_1735 class_17352 = InventoryUtils.d(class_4652, n);
        if (class_17352 == null) {
            return null;
        }
        IHandledScreen iHandledScreen = (IHandledScreen)class_4652;
        double d = aiG.method_22683().method_4495();
        double d2 = (double)(iHandledScreen.getX() + class_17352.field_7873) + 8.5;
        double d3 = (double)(iHandledScreen.getY() + class_17352.field_7872) + 8.5;
        GLFW.glfwSetCursorPos((long)aiG.method_22683().method_4490(), (double)(d2 * d), (double)(d3 * d));
        iHandledScreen.setFocusedSlot(class_17352);
        return new double[]{d2 * d, d3 * d};
    }

    public static class_1735 d(class_465<?> class_4652, int n) {
        for (class_1735 class_17352 : class_4652.method_17577().field_7761) {
            if (class_17352 == null || class_17352.field_7874 != n) continue;
            return class_17352;
        }
        if (n >= 0 && n < class_4652.method_17577().field_7761.size()) {
            return (class_1735)class_4652.method_17577().field_7761.get(n);
        }
        return null;
    }

    public static int ab(int n, int n2) {
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        int n8 = -1;
        for (int i = n2; i >= n; --i) {
            class_1799 class_17992 = InventoryUtils.aiG.field_1724.method_31548().method_5438(i);
            if (class_17992.method_7909() == class_1802.field_8577) {
                n3 = i;
                continue;
            }
            if (class_17992.method_7909() == class_1802.field_8873) {
                n4 = i;
                continue;
            }
            if (class_17992.method_7909() == class_1802.field_8523) {
                n5 = i;
                continue;
            }
            if (class_17992.method_7909() == class_1802.field_8678) {
                n6 = i;
                continue;
            }
            if (class_17992.method_7909() == class_1802.field_8058) {
                n7 = i;
                continue;
            }
            if (class_17992.method_7909() != class_1802.field_22028) continue;
            n8 = i;
        }
        if (n4 != -1) {
            return n4;
        }
        if (n5 != -1) {
            return n5;
        }
        if (n6 != -1) {
            return n6;
        }
        if (n7 != -1) {
            return n7;
        }
        if (n8 != -1) {
            return n8;
        }
        return n3;
    }

    public static int b(class_1657 class_16572, int n) {
        class_1799 class_17992 = class_16572.method_31548().method_7372(n);
        if (class_17992.method_31573(class_3489.field_48803)) {
            return class_9282.method_57470((class_1799)class_17992, (int)-6265536);
        }
        return -1;
    }

    public static int g(class_1792 class_17922) {
        return InventoryUtils.a(class_17922, 0, 35);
    }

    public static int h(class_1792 class_17922) {
        return InventoryUtils.a(class_17922, 0, 8);
    }

    public static int i(class_1792 class_17922) {
        return InventoryUtils.a(class_17922, 9, 35);
    }

    public static int b(Class<? extends class_1792> clazz) {
        return InventoryUtils.a(clazz, 0, 35);
    }

    public static int c(Class<? extends class_1792> clazz) {
        return InventoryUtils.a(clazz, 0, 8);
    }

    public static int d(Class<? extends class_1792> clazz) {
        return InventoryUtils.a(clazz, 9, 35);
    }

    public static int a(class_1792 class_17922, int n, int n2) {
        for (int i = n2; i >= n; --i) {
            if (InventoryUtils.aiG.field_1724.method_31548().method_5438(i).method_7909() != class_17922) continue;
            return i;
        }
        return -1;
    }

    public static int a(Class<? extends class_1792> clazz, int n, int n2) {
        for (int i = n2; i >= n; --i) {
            class_1799 class_17992 = InventoryUtils.aiG.field_1724.method_31548().method_5438(i);
            if (!class_17992.method_7909().getClass().isAssignableFrom(clazz)) continue;
            return i;
        }
        return -1;
    }

    public static int ac(int n, int n2) {
        for (int i = n2; i >= n; --i) {
            if (!InventoryUtils.aiG.field_1724.method_31548().method_5438(i).method_7960()) continue;
            return i;
        }
        return -1;
    }

    @Generated
    private InventoryUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static enum InventorySearchResult
    implements NamedOption  {
        akn("settings.normal"),
        ako("settings.switch.silent"),
        akp("settings.switch.alternative"),
        akq("settings.none");
        private final String akr;

@Override
        public String getName() {
            return this.akr;
        }

        @Generated
        private InventorySearchResult(String string2) {
            this.akr = string2;
        }

}

    public static enum InventorySlot {
        ake,
        akf;
}

    public static enum SlotRange
    implements NamedOption  {
        akh("settings.swing.mainhand"),
        aki("settings.swing.offhand"),
        akj("settings.packet"),
        akk("settings.none");
        private final String akl;

@Override
        public String getName() {
            return this.akl;
        }

        @Generated
        private SlotRange(String string2) {
            this.akl = string2;
        }

}
}

