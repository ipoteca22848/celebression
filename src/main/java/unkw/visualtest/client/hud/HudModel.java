package unkw.visualtest.client.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4081;
import net.minecraft.class_640;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import ru.method.celebression.ui.CooldownEntry;
import ru.method.celebression.screen.HudEditorScreen;
import unkw.visualtest.client.hud.HudState;

public final class HudModel {
    private static final class_1304[] ARMOR_SLOTS = new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166};
    private static float fpsSmooth;
    private static Snapshot cached;
    private static long cacheExpiresAtMs;
    private static final double AIM_HITBOX_MARGIN = 0.75;
    private static final double AIM_MAX_RANGE = 3.5;

    private HudModel() {
    }

    public static Snapshot capture(class_310 class_3102, HudState hudState) {
        class_640 class_6402;
        long l = System.currentTimeMillis();
        if (cached != null && l < cacheExpiresAtMs) {
            return cached;
        }
        class_746 class_7462 = class_3102.field_1724;
        class_1661 class_16612 = class_7462.method_31548();
        boolean bl = HudEditorScreen.mb();
        String string = String.format(Locale.ROOT, "X %d  Y %d  Z %d", class_7462.method_31477(), class_7462.method_31478(), class_7462.method_31479());
        String string2 = String.format(Locale.ROOT, "%.1f BPS", hudState.speedBps());
        class_640 class_6403 = class_6402 = class_3102.method_1562() == null ? null : class_3102.method_1562().method_2871(class_7462.method_5667());
        Object object = class_6402 != null ? class_6402.method_2959() + " \u041c\u0421" : (bl ? "48 \u041c\u0421" : "\u041b\u041e\u041a\u0410\u041b\u042c\u041d\u041e");
        Collection collection = class_7462.method_6026();
        hudState.trackEffects(collection);
        List<EffectRow> list = HudModel.collectEffects(collection, bl, hudState);
        List<CooldownRow> list2 = HudModel.collectCooldowns(class_3102, bl);
        List<class_1799> list3 = HudModel.collectArmor((class_1309)class_7462, bl);
        DurabilityAlert durabilityAlert = HudModel.collectDurabilityAlert((class_1309)class_7462, bl);
        Target target = HudModel.collectTarget(class_3102, bl);
        int n = class_3102.method_47599();
        fpsSmooth = fpsSmooth <= 0.0f ? (float)n : fpsSmooth + ((float)n - fpsSmooth) * 0.3f;
        cached = new Snapshot(Math.round(fpsSmooth), class_7462.method_5477().getString(), (String)object, string, string2, list, list2, list3, durabilityAlert, target, bl);
        cacheExpiresAtMs = l + 50L;
        return cached;
    }

    private static List<EffectRow> collectEffects(Iterable<class_1293> iterable, boolean bl, HudState hudState) {
        ArrayList<EffectRow> arrayList = new ArrayList<EffectRow>();
        for (class_1293 class_12932 : iterable) {
            int n = class_12932.method_48559() ? Integer.MAX_VALUE : Math.max(0, class_12932.method_5584() / 20);
            class_6880 class_68802 = class_12932.method_5579();
            arrayList.add(new EffectRow((class_6880<class_1291>)class_68802, class_2561.method_43471((String)class_12932.method_5586()).getString(), class_12932.method_5578() + 1, n == Integer.MAX_VALUE ? "\u221e" : HudModel.formatDuration(n), n, ((class_1291)class_68802.comp_349()).method_18792() == class_4081.field_18272));
        }
        arrayList.sort(Comparator.comparingLong((EffectRow effectRow) -> hudState.effectOrder(effectRow.type())).reversed());
        if (arrayList.isEmpty() && bl) {
            int n = (int)(System.currentTimeMillis() / 1000L % 20L);
            arrayList.add(new EffectRow((class_6880<class_1291>)class_1294.field_5904, "\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 2, HudModel.formatDuration(84 - n), 84 - n, false));
            arrayList.add(new EffectRow((class_6880<class_1291>)class_1294.field_5907, "\u0421\u043e\u043f\u0440\u043e\u0442\u0438\u0432\u043b\u0435\u043d\u0438\u0435", 1, HudModel.formatDuration(38 - n / 2), 38 - n / 2, false));
        }
        return arrayList;
    }

    private static List<CooldownRow> collectCooldowns(class_310 class_3102, boolean bl) {
        ArrayList<CooldownRow> arrayList = new ArrayList<CooldownRow>();
        for (CooldownEntry.CooldownCategory cooldownCategory : CooldownEntry.a(class_3102, 0.0f)) {
            int n = Math.max(0, Math.round(cooldownCategory.rK() / 20.0f));
            arrayList.add(new CooldownRow(cooldownCategory.stack(), cooldownCategory.rJ(), HudModel.formatDuration(n)));
            if (arrayList.size() != 3) continue;
            break;
        }
        if (arrayList.isEmpty() && bl) {
            arrayList.add(new CooldownRow(new class_1799((class_1935)class_1802.field_8634), "\u042d\u043d\u0434\u0435\u0440-\u0436\u0435\u043c\u0447\u0443\u0433", HudModel.formatDuration(12)));
            arrayList.add(new CooldownRow(new class_1799((class_1935)class_1802.field_8233), "\u041f\u043b\u043e\u0434 \u0445\u043e\u0440\u0443\u0441\u0430", HudModel.formatDuration(3)));
        }
        return arrayList;
    }

    private static List<class_1799> collectArmor(class_1309 class_13092, boolean bl) {
        ArrayList<class_1799> arrayList = new ArrayList<class_1799>(ARMOR_SLOTS.length);
        for (class_1304 class_13042 : ARMOR_SLOTS) {
            arrayList.add(class_13092.method_6118(class_13042));
        }
        return arrayList;
    }

    private static DurabilityAlert collectDurabilityAlert(class_1309 class_13092, boolean bl) {
        class_1799 class_17992 = class_1799.field_8037;
        int n = Integer.MAX_VALUE;
        int n2 = 100;
        ArrayList<class_1799> arrayList = new ArrayList<class_1799>();
        for (class_1304 class_13042 : ARMOR_SLOTS) {
            arrayList.add(class_13092.method_6118(class_13042));
        }
        arrayList.add(class_13092.method_6047());
        arrayList.add(class_13092.method_6079());
        for (class_1799 class_17993 : arrayList) {
            int n3;
            int n4;
            if (class_17993.method_7960() || !class_17993.method_7963() || class_17993.method_7936() <= 0 || (n4 = Math.round((float)(n3 = class_17993.method_7936() - class_17993.method_7919()) * 100.0f / (float)class_17993.method_7936())) > 15 && n3 > 30 || n4 >= n2 && (n4 != n2 || n3 >= n)) continue;
            class_17992 = class_17993;
            n = n3;
            n2 = n4;
        }
        if (!class_17992.method_7960()) {
            return new DurabilityAlert(class_17992, n2);
        }
        if (bl) {
            class_1799 class_17994 = new class_1799((class_1935)class_1802.field_8377);
            class_17994.method_7974(Math.max(0, class_17994.method_7936() - 74));
            return new DurabilityAlert(class_17994, Math.round(7400.0f / (float)class_17994.method_7936()));
        }
        return null;
    }

    private static Target collectTarget(class_310 class_3102, boolean bl) {
        class_1309 class_13092;
        class_1297 class_12972;
        class_1309 class_13093 = HudModel.playerNearCrosshair(class_3102);
        if (class_13093 == null && (class_12972 = class_3102.field_1692) instanceof class_1309 && (class_13092 = (class_1309)class_12972) != class_3102.field_1724 && !class_13092.method_5767() && !class_13092.method_6059(class_1294.field_5905)) {
            class_13093 = class_13092;
        }
        if (class_13093 != null) {
            return new Target(class_13093, class_13093.method_5477().getString(), class_13093.method_6032(), class_13093.method_6063(), class_3102.field_1724.method_5739((class_1297)class_13093));
        }
        return bl ? new Target((class_1309)class_3102.field_1724, class_3102.field_1724.method_5477().getString(), 13.5f, 20.0f, 4.2f) : null;
    }

    private static class_1309 playerNearCrosshair(class_310 class_3102) {
        if (class_3102.field_1687 == null || class_3102.field_1724 == null) {
            return null;
        }
        class_243 class_2432 = class_3102.field_1724.method_33571();
        class_243 class_2433 = class_3102.field_1724.method_5828(1.0f);
        class_243 class_2434 = class_2432.method_1019(class_2433.method_1021(3.5));
        class_1657 class_16572 = null;
        double d = Double.MAX_VALUE;
        for (class_1657 class_16573 : class_3102.field_1687.method_18456()) {
            double d2;
            Optional optional;
            if (class_16573 == class_3102.field_1724 || class_16573.method_5767() || class_16573.method_6059(class_1294.field_5905) || (double)class_3102.field_1724.method_5739((class_1297)class_16573) > 3.5 || (optional = class_16573.method_5829().method_1014(0.75).method_992(class_2432, class_2434)).isEmpty() || !((d2 = ((class_243)optional.get()).method_1025(class_2432)) < d)) continue;
            d = d2;
            class_16572 = class_16573;
        }
        return class_16572;
    }

    private static String formatDuration(int n) {
        return String.format(Locale.ROOT, "%d:%02d", n / 60, n % 60);
    }

    private static class_1799 damaged(class_1792 class_17922, float f) {
        class_1799 class_17992 = new class_1799((class_1935)class_17922);
        if (class_17992.method_7963()) {
            class_17992.method_7974(Math.round((float)class_17992.method_7936() * HudModel.clamp(f)));
        }
        return class_17992;
    }

    private static float clamp(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }

    public record Snapshot(int fps, String playerName, String ping, String coordinates, String movement, List<EffectRow> effects, List<CooldownRow> cooldowns, List<class_1799> armor, DurabilityAlert alert, Target target, boolean preview) {
    }

    public record DurabilityAlert(class_1799 stack, int percent) {
    }

    public record Target(class_1309 entity, String name, float health, float maxHealth, float distance) {
    }

    public record EffectRow(class_6880<class_1291> type, String name, int amplifier, String duration, int seconds, boolean harmful) {
    }

    public record CooldownRow(class_1799 stack, String name, String value) {
    }
}
