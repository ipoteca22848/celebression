package ru.method.celebression.inventory;

import lombok.Generated;
import net.minecraft.class_465;
import ru.method.celebression.inventory.InventoryUtils;

public final class InventoryHelper {
    private static int akd = -1;

    public static boolean rM() {
        return akd != -1;
    }

    public static void b(class_465<?> class_4652, int n) {
        akd = n;
        InventoryHelper.d(class_4652);
    }

    public static void d(class_465<?> class_4652) {
        if (akd == -1 || class_4652 == null) {
            return;
        }
        InventoryUtils.c(class_4652, akd);
    }

    public static void rN() {
        akd = -1;
    }

    @Generated
    private InventoryHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

