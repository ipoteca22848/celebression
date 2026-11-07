package ru.method.celebression.modules.impl.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1313;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_745;
import ru.method.celebression.modules.impl.player.CrasherModule;

public final class CrasherMovementController {
    private static final double yE = 0.08;
    private static final double yF = 0.98;
    private static final double yG = -3.92;
    private static final double yH = 0.6;
    private static final int yI = 4;
    private final CrasherModule yJ;
    private final Map<UUID, MovementState> yK = new HashMap<UUID, MovementState>();

    public CrasherMovementController(CrasherModule crasherModule) {
        this.yJ = crasherModule;
    }

    public void clear() {
        this.yK.clear();
    }

    public void a(class_745 class_7452) {
        class_243 class_2432;
        class_1937 class_19372 = class_7452.method_37908();
        UUID uUID = class_7452.method_5667();
        MovementState movementState = this.yK.get(uUID);
        if (!CrasherMovementController.b(class_7452)) {
            this.yK.remove(uUID);
            return;
        }
        if (movementState == null) {
            if (this.yJ.ie() == 0) {
                return;
            }
            class_2432 = CrasherMovementController.a(class_7452, class_19372);
            if (class_2432 == null) {
                return;
            }
            movementState = new MovementState(class_2432);
            this.yK.put(uUID, movementState);
        }
        class_2432 = class_7452.method_19538();
        double d = class_3532.method_15350((double)(class_2432.field_1352 - movementState.pos.field_1352), (double)-0.6, (double)0.6);
        double d2 = class_3532.method_15350((double)(class_2432.field_1350 - movementState.pos.field_1350), (double)-0.6, (double)0.6);
        class_7452.method_33574(movementState.pos);
        class_7452.method_5784(class_1313.field_6308, new class_243(d, movementState.yL, d2));
        movementState.pos = class_7452.method_19538();
        movementState.yL = class_7452.field_5992 ? 0.0 : Math.max((movementState.yL - 0.08) * 0.98, -3.92);
        class_7452.method_18800(d, movementState.yL, d2);
        class_7452.method_24830(class_7452.field_5992 && movementState.yL <= 0.0);
    }

    private static boolean b(class_745 class_7452) {
        return !class_7452.method_31481() && !class_7452.method_5765() && !class_7452.method_7325() && !class_7452.method_6128() && !class_7452.method_5799() && !class_7452.method_6101();
    }

    private static class_243 a(class_745 class_7452, class_1937 class_19372) {
        class_238 class_2382 = class_7452.method_5829();
        if (!class_19372.method_8587((class_1297)class_7452, class_2382.method_35580(0.001, 0.001, 0.001))) {
            for (int i = 1; i <= 4; ++i) {
                class_238 class_2383 = class_2382.method_989(0.0, (double)i, 0.0);
                if (!class_19372.method_8587((class_1297)class_7452, class_2383.method_35580(0.001, 0.001, 0.001))) continue;
                return class_7452.method_19538().method_1031(0.0, (double)i, 0.0);
            }
            return null;
        }
        class_238 class_2384 = new class_238(class_2382.field_1323 + 0.001, class_2382.field_1322 - 0.12, class_2382.field_1321 + 0.001, class_2382.field_1320 - 0.001, class_2382.field_1322, class_2382.field_1324 - 0.001);
        if (class_7452.method_24828() && class_19372.method_8587((class_1297)class_7452, class_2384)) {
            return class_7452.method_19538();
        }
        return null;
    }

    static final class MovementState {
        class_243 pos;
        double yL;

        MovementState(class_243 class_2432) {
            this.pos = class_2432;
            this.yL = 0.0;
        }
    }
}

