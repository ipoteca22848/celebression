package ru.method.celebression.punchy.client.state;

import net.minecraft.class_1306;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.state.MiningActionTracker;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class MudStateMachine {
    private static final float MUD_GAIN_PER_TICK = 0.02f;
    private static float mudAmount = 0.0f;
    private static class_1306 mudArm = class_1306.field_6183;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && PunchyConfig.enableMud()) {
            boolean bl;
            boolean bl2 = class_3102.field_1724.method_5799() || class_3102.field_1724.method_5869();
            boolean bl3 = bl = MiningActionTracker.isMiningActive() && MiningActionTracker.isMiningMud() && !class_3102.field_1724.method_7325();
            if (bl) {
                mudArm = class_3102.field_1724.method_6068();
                mudAmount = Math.min(1.0f, mudAmount + 0.02f);
            } else if (mudAmount > 0.0f && bl2) {
                mudAmount = 0.0f;
            }
        }
    }

    public static int getStageForArm(class_1306 class_13062) {
        if (class_13062 == null || class_13062 != mudArm) {
            return 0;
        }
        if (mudAmount <= 0.0f) {
            return 0;
        }
        if (mudAmount < 0.34f) {
            return 1;
        }
        return mudAmount < 0.67f ? 2 : 3;
    }

    public static boolean hasMud() {
        return mudAmount > 0.0f;
    }

    public static float getMudAmount() {
        return mudAmount;
    }
}

