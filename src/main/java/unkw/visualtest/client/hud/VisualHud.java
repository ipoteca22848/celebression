package unkw.visualtest.client.hud;

import ru.method.celebression.animation.Animation;

import java.util.List;
import java.util.Locale;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.HudEditorScreen;
import unkw.visualtest.client.Visual_testClient;
import unkw.visualtest.client.hud.HudEntityPreview;
import unkw.visualtest.client.hud.HudModel;
import unkw.visualtest.client.hud.RoundFontRenderer;
import unkw.visualtest.client.hud.SmoothGuiRenderer;

public final class VisualHud {
    private static final class_2960 TARGET_BODY = class_2960.method_60655((String)"visual-test", (String)"textures/gui/target/target_body.png");
    private static final class_2960 TARGET_ROOM = class_2960.method_60655((String)"visual-test", (String)"textures/gui/target/target_room.png");
    private static final class_2960 TARGET_HEALTH = class_2960.method_60655((String)"visual-test", (String)"textures/gui/target/target_health.png");
    private static final class_2960 SURFACE = class_2960.method_60655((String)"visual-test", (String)"textures/gui/sprites/hud/surface.png");
    private static final class_2960 SURFACE_STRONG = class_2960.method_60655((String)"visual-test", (String)"textures/gui/sprites/hud/surface_strong.png");
    private static final class_2960 WATERMARK_SURFACE = class_2960.method_60655((String)"visual-test", (String)"textures/gui/sprites/hud/watermark.png");
    private static final class_2960 RECESS = class_2960.method_60655((String)"visual-test", (String)"textures/gui/sprites/hud/recess.png");
    private static final class_2960 SLOT = class_2960.method_60655((String)"visual-test", (String)"textures/gui/sprites/hud/slot.png");
    private static final float WATERMARK_TEXT_SIZE = 7.5f;
    private static final float HUD_TEXT_SIZE = 9.0f;
    private static final float TARGET_TEXT_SIZE = 7.5f;
    private static final int TEXT = -854798;
    private static final int MUTED = -6380126;
    private static final int ACCENT = -4076601;
    private static final int SEPARATOR = 1114535796;
    private static final int CRITICAL = -2317922;
    private static final int HARMFUL = -1937557;
    private static final AnimatedComponent TARGET_APPEAR = new AnimatedComponent(Easing.aiZ);
    private static final AnimatedComponent ARMOR_APPEAR = new AnimatedComponent(Easing.aiZ);
    private static final AnimatedComponent ALERT_APPEAR = new AnimatedComponent(Easing.aiZ);

    private static boolean hasAnyArmor(HudModel.Snapshot snapshot) {
        for (class_1799 class_17992 : snapshot.armor()) {
            if (class_17992.method_7960()) continue;
            return true;
        }
        return false;
    }

    private VisualHud() {
    }

    private static HudModel.Snapshot cardSnapshot(class_310 class_3102) {
        return HudModel.capture(class_3102, Visual_testClient.state());
    }

    public static int cardWatermark(class_332 class_3322, class_310 class_3102, int n, int n2) {
        RoundFontRenderer.beginFrame();
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        int n3 = VisualHud.watermarkWidth(snapshot);
        VisualHud.renderWatermark(class_3322, snapshot, n, n2, n3, 17);
        RoundFontRenderer.flush(class_3322);
        return n3;
    }

    public static boolean cardTarget(class_332 class_3322, class_310 class_3102, int n, int n2) {
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        float f = TARGET_APPEAR.a(snapshot.target() != null ? 1.0f : 0.0f, 200L);
        if (snapshot.target() == null || f <= 0.01f) {
            return false;
        }
        RoundFontRenderer.beginFrame();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_46416((float)n + 72.0f, (float)n2 + 22.4f, 0.0f);
        class_45872.method_22905(0.8f * f, 0.8f * f, 1.0f);
        class_45872.method_46416(-((float)n + 90.0f), -((float)n2 + 28.0f), 0.0f);
        VisualHud.renderTarget(class_3322, class_3102, snapshot.target(), n, n2, 180, 56);
        RoundFontRenderer.flush(class_3322);
        class_45872.method_22909();
        return true;
    }

    public static int cardEffects(class_332 class_3322, class_310 class_3102, int n, int n2) {
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        int n3 = snapshot.effects().size();
        if (n3 == 0) {
            return 0;
        }
        RoundFontRenderer.beginFrame();
        VisualHud.renderEffects(class_3322, class_3102, snapshot, n, n2, n3);
        RoundFontRenderer.flush(class_3322);
        return 20 + n3 * 21;
    }

    public static int cardCooldowns(class_332 class_3322, class_310 class_3102, int n, int n2) {
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        int n3 = Math.min(snapshot.cooldowns().size(), 3);
        if (n3 == 0) {
            return 0;
        }
        RoundFontRenderer.beginFrame();
        VisualHud.renderCooldowns(class_3322, class_3102, snapshot.cooldowns(), n, n2, n3);
        RoundFontRenderer.flush(class_3322);
        return 20 + n3 * 21;
    }

    public static boolean cardArmor(class_332 class_3322, class_310 class_3102, int n, int n2) {
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        boolean bl = VisualHud.hasAnyArmor(snapshot) || snapshot.preview() || HudEditorScreen.mb();
        float f = ARMOR_APPEAR.a(bl ? 1.0f : 0.0f, 200L);
        if (f <= 0.01f) {
            return false;
        }
        RoundFontRenderer.beginFrame();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_46416((float)n + 41.0f, (float)n2 + 12.5f, 0.0f);
        class_45872.method_22905(f, f, 1.0f);
        class_45872.method_46416(-((float)n + 41.0f), -((float)n2 + 12.5f), 0.0f);
        VisualHud.renderArmor(class_3322, class_3102, snapshot, n, n2);
        RoundFontRenderer.flush(class_3322);
        class_45872.method_22909();
        return true;
    }

    public static boolean cardDurability(class_332 class_3322, class_310 class_3102, int n, int n2) {
        HudModel.Snapshot snapshot = VisualHud.cardSnapshot(class_3102);
        float f = ALERT_APPEAR.a(snapshot.alert() != null ? 1.0f : 0.0f, 200L);
        if (snapshot.alert() == null || f <= 0.01f) {
            return false;
        }
        RoundFontRenderer.beginFrame();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_46416((float)n + 56.0f, (float)n2 + 13.3f, 0.0f);
        class_45872.method_22905(0.7f * f, 0.7f * f, 1.0f);
        class_45872.method_46416(-((float)n + 80.0f), -((float)n2 + 19.0f), 0.0f);
        VisualHud.renderDurability(class_3322, class_3102, snapshot.alert(), n, n2);
        RoundFontRenderer.flush(class_3322);
        class_45872.method_22909();
        return true;
    }

    private static void renderWatermark(class_332 class_3322, HudModel.Snapshot snapshot, int n, int n2, int n3, int n4) {
        SmoothGuiRenderer.drawNineSlice(class_3322, WATERMARK_SURFACE, n, n2, n3, n4, 80, 80, 28, 7);
        String[] stringArray = VisualHud.watermarkParts(snapshot);
        int n5 = n + 7;
        for (int i = 0; i < stringArray.length; ++i) {
            int n6 = i == 0 ? -4076601 : (i == 1 ? -854798 : -6380126);
            RoundFontRenderer.queue(stringArray[i], n5, n2 + 4, 7.5f, n6);
            n5 += VisualHud.watermarkTextWidth(stringArray[i]);
            if (i + 1 >= stringArray.length) continue;
            class_3322.method_25294(n5 += 3, n2 + 4, n5 + 1, n2 + 12, 1114535796);
            n5 += 4;
        }
    }

    private static int watermarkWidth(HudModel.Snapshot snapshot) {
        String[] stringArray = VisualHud.watermarkParts(snapshot);
        int n = 14 + (stringArray.length - 1) * 7;
        for (String string : stringArray) {
            n += VisualHud.watermarkTextWidth(string);
        }
        return n;
    }

    private static String[] watermarkParts(HudModel.Snapshot snapshot) {
        return new String[]{"celebression.", VisualHud.fit(snapshot.playerName(), 64, 7.5f), snapshot.fps() + " FPS", snapshot.ping()};
    }

    private static int watermarkTextWidth(String string) {
        return (int)Math.ceil(RoundFontRenderer.width(string, 7.5f));
    }

    private static void renderHint(class_332 class_3322, class_327 class_3272, HudModel.Snapshot snapshot, int n, int n2) {
        String string = snapshot.preview() ? "F8   \u0422\u0435\u0441\u0442\u043e\u0432\u044b\u0439 \u0440\u0435\u0436\u0438\u043c" : "F8   \u0422\u043e\u043b\u044c\u043a\u043e \u0440\u0435\u0430\u043b\u044c\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435";
        int n3 = VisualHud.textWidth(class_3272, string) + 20;
        VisualHud.surface(class_3322, n, n2, n3, 18, false);
        VisualHud.text(class_3322, class_3272, string, n + 10, n2 + 5, -6380126);
    }

    private static void renderArmor(class_332 class_3322, class_310 class_3102, HudModel.Snapshot snapshot, int n, int n2) {
        VisualHud.surface(class_3322, n, n2, 82, 25, false);
        int n3 = snapshot.armor().size();
        for (int i = 0; i < n3; ++i) {
            VisualHud.renderArmorSlot(class_3322, class_3102, snapshot.armor().get(i), n + 5 + i * 18, n2 + 4, 17);
        }
    }

    private static void renderEffects(class_332 class_3322, class_310 class_3102, HudModel.Snapshot snapshot, int n, int n2, int n3) {
        int n4 = Math.min(n3, snapshot.effects().size());
        int n5 = 20 + n4 * 21;
        VisualHud.surface(class_3322, n, n2, 150, n5, false);
        String string = snapshot.effects().size() > n4 ? "\u042d\u0444\u0444\u0435\u043a\u0442\u044b  +" + (snapshot.effects().size() - n4) : "\u042d\u0444\u0444\u0435\u043a\u0442\u044b";
        VisualHud.label(class_3322, class_3102.field_1772, string, n + 9, n2 + 6);
        for (int i = 0; i < n4; ++i) {
            HudModel.EffectRow effectRow = snapshot.effects().get(i);
            int n6 = n2 + 19 + i * 21;
            VisualHud.inset(class_3322, n + 5, n6, 18, 18);
            class_3322.method_52709(class_1921::method_62277, class_3102.method_18505().method_18663(effectRow.type()), n + 8, n6 + 3, 12, 12);
            String string2 = VisualHud.fit(class_3102.field_1772, effectRow.name() + (String)(effectRow.amplifier() > 1 ? " " + effectRow.amplifier() : ""), 72);
            VisualHud.text(class_3322, class_3102.field_1772, string2, n + 29, n6 + 5, effectRow.harmful() ? -1937557 : -854798);
            VisualHud.pillText(class_3322, class_3102.field_1772, effectRow.duration(), n + 109, n6 + 1, 35, 16, effectRow.seconds() <= 10 ? -2317922 : -6380126);
        }
    }

    private static void renderCooldowns(class_332 class_3322, class_310 class_3102, List<HudModel.CooldownRow> list, int n, int n2, int n3) {
        int n4 = Math.min(n3, list.size());
        int n5 = 20 + n4 * 21;
        VisualHud.surface(class_3322, n, n2, 150, n5, false);
        String string = list.size() > n4 ? "\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0438  +" + (list.size() - n4) : "\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0438";
        VisualHud.label(class_3322, class_3102.field_1772, string, n + 9, n2 + 6);
        for (int i = 0; i < n4; ++i) {
            HudModel.CooldownRow cooldownRow = list.get(i);
            int n6 = n2 + 19 + i * 21;
            VisualHud.inset(class_3322, n + 5, n6, 18, 18);
            class_3322.method_51427(cooldownRow.stack(), n + 6, n6 + 1);
            String string2 = VisualHud.fit(class_3102.field_1772, cooldownRow.name(), 72);
            VisualHud.text(class_3322, class_3102.field_1772, string2, n + 29, n6 + 5, -854798);
            VisualHud.pillText(class_3322, class_3102.field_1772, cooldownRow.value(), n + 109, n6 + 1, 35, 16, -6380126);
        }
    }

    private static void renderDurability(class_332 class_3322, class_310 class_3102, HudModel.DurabilityAlert durabilityAlert, int n, int n2) {
        VisualHud.surface(class_3322, n, n2, 160, 38, true);
        VisualHud.inset(class_3322, n + 6, n2 + 8, 22, 22);
        class_3322.method_51427(durabilityAlert.stack(), n + 9, n2 + 11);
        VisualHud.strongText(class_3322, class_3102.field_1772, "\u041d\u0438\u0437\u043a\u0430\u044f \u043f\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u044c", n + 35, n2 + 7, -854798);
        VisualHud.strongText(class_3322, class_3102.field_1772, durabilityAlert.percent() + "%", n + 35, n2 + 21, VisualHud.durabilityColor(durabilityAlert.percent()));
    }

    private static void renderTarget(class_332 class_3322, class_310 class_3102, HudModel.Target target, int n, int n2, int n3, int n4) {
        VisualHud.drawAsset(class_3322, TARGET_BODY, n, n2, n3, n4, 720, 224);
        int n5 = 50;
        int n6 = n + 3;
        int n7 = n2 + 3;
        VisualHud.drawAsset(class_3322, TARGET_ROOM, n6, n7, n5, n5, 400, 400);
        HudEntityPreview.render(class_3322, class_3102, target.entity(), n6, n7, n5, n5);
        int n8 = n + 62;
        int n9 = n + n3 - 8;
        int n10 = Math.max(1, n9 - n8);
        String string = String.format(Locale.ROOT, "%.1f \u043c", Float.valueOf(target.distance()));
        String string2 = VisualHud.fitTargetStrong(class_3102.field_1772, target.name(), n10);
        VisualHud.targetStrongText(class_3322, class_3102.field_1772, string2, n8, n2 + 7, -854798);
        int n11 = VisualHud.targetTextWidth(class_3102.field_1772, string);
        String string3 = String.format(Locale.ROOT, "%.1f / %.1f \u0425\u041f", Float.valueOf(target.health()), Float.valueOf(target.maxHealth()));
        int n12 = Math.max(12, n10 - n11 - 7);
        VisualHud.targetStrongText(class_3322, class_3102.field_1772, VisualHud.fitTargetStrong(class_3102.field_1772, string3, n12), n8, n2 + 19, -854798);
        VisualHud.targetText(class_3322, class_3102.field_1772, string, n9 - n11, n2 + 19, -6380126);
        int n13 = 0;
        for (class_1304 class_13042 : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            class_1799 class_17992 = target.entity().method_6118(class_13042);
            if (class_17992.method_7960()) continue;
            class_3322.method_51427(class_17992, n8 + 17 * n13++, n2 + 29);
        }
        float f = target.maxHealth() <= 0.0f ? 0.0f : class_3532.method_15363((float)(target.health() / target.maxHealth()), (float)0.0f, (float)1.0f);
        int n14 = Math.round((float)n10 * f);
        if (n14 > 0) {
            class_3322.method_25302(SmoothGuiRenderer::getLayer, TARGET_HEALTH, n8, n2 + 48, 0.0f, 0.0f, n14, 2, n14 * 4, 8, 440, 8);
        }
    }

    private static void drawAsset(class_332 class_3322, class_2960 class_29602, int n, int n2, int n3, int n4, int n5, int n6) {
        SmoothGuiRenderer.draw(class_3322, class_29602, n, n2, n3, n4, n5, n6);
    }

    private static void renderArmorSlot(class_332 class_3322, class_310 class_3102, class_1799 class_17992, int n, int n2, int n3) {
        SmoothGuiRenderer.draw(class_3322, SLOT, n, n2, n3, n3, 72, 72);
        if (!class_17992.method_7960()) {
            int n4 = Math.max(0, (n3 - 15) / 2);
            class_3322.method_51427(class_17992, n + n4, n2 + n4);
            class_3322.method_51432(class_3102.field_1772, class_17992, n + n4, n2 + n4, "");
        }
    }

    private static void surface(class_332 class_3322, int n, int n2, int n3, int n4, boolean bl) {
        SmoothGuiRenderer.drawNineSlice(class_3322, bl ? SURFACE_STRONG : SURFACE, n, n2, n3, n4, 80, 80, 28, 7);
    }

    private static void inset(class_332 class_3322, int n, int n2, int n3, int n4) {
        SmoothGuiRenderer.drawNineSlice(class_3322, RECESS, n, n2, n3, n4, 64, 64, 24, 6);
    }

    private static void pillText(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3, int n4, int n5) {
        VisualHud.inset(class_3322, n, n2, n3, n4);
        VisualHud.centeredText(class_3322, class_3272, string, n, n2, n3, n4, n5);
    }

    private static void label(class_332 class_3322, class_327 class_3272, String string, int n, int n2) {
        VisualHud.strongText(class_3322, class_3272, string, n, n2, -6380126);
    }

    private static void text(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3) {
        RoundFontRenderer.queue(string, n, n2, 9.0f, n3);
    }

    private static void strongText(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3) {
        RoundFontRenderer.queue(string, n, n2, 9.0f, n3);
    }

    private static void targetText(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3) {
        RoundFontRenderer.queue(string, n, n2, 7.5f, n3);
    }

    private static void targetStrongText(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3) {
        RoundFontRenderer.queue(string, n, n2, 7.5f, n3);
    }

    private static void centeredText(class_332 class_3322, class_327 class_3272, String string, int n, int n2, int n3, int n4, int n5) {
        int n6 = n + Math.max(0, Math.round(((float)n3 - RoundFontRenderer.width(string, 9.0f)) / 2.0f));
        int n7 = n2 + Math.max(0, (n4 - 9 + 1) / 2);
        VisualHud.text(class_3322, class_3272, string, n6, n7, n5);
    }

    private static int textWidth(class_327 class_3272, String string) {
        return (int)Math.ceil(RoundFontRenderer.width(string, 9.0f));
    }

    private static int targetTextWidth(class_327 class_3272, String string) {
        return (int)Math.ceil(RoundFontRenderer.width(string, 7.5f));
    }

    private static int durabilityColor(int n) {
        int n2;
        int n3;
        int n4;
        float f = (float)Math.max(0, Math.min(100, n)) / 100.0f;
        float f2 = f * 2.0f;
        if (f2 <= 1.0f) {
            n4 = VisualHud.blend(224, 222, f2);
            n3 = VisualHud.blend(92, 198, f2);
            n2 = VisualHud.blend(92, 86, f2);
        } else {
            float f3 = f2 - 1.0f;
            n4 = VisualHud.blend(222, 132, f3);
            n3 = VisualHud.blend(198, 220, f3);
            n2 = VisualHud.blend(86, 105, f3);
        }
        return 0xFF000000 | n4 << 16 | n3 << 8 | n2;
    }

    private static int blend(int n, int n2, float f) {
        return Math.round((float)n + (float)(n2 - n) * f);
    }

    private static String fit(class_327 class_3272, String string, int n) {
        return VisualHud.fit(string, n, 9.0f);
    }

    private static String fitTargetStrong(class_327 class_3272, String string, int n) {
        return VisualHud.fit(string, n, 7.5f);
    }

    private static String fit(String string, int n, float f) {
        if (RoundFontRenderer.width(string, f) <= (float)n) {
            return string;
        }
        String string2 = "...";
        if (RoundFontRenderer.width(string2, f) > (float)n) {
            return "";
        }
        int n2 = string.length();
        while (n2 > 0 && RoundFontRenderer.width(string.substring(0, n2) + string2, f) > (float)n) {
            n2 = string.offsetByCodePoints(n2, -1);
        }
        return string.substring(0, n2) + string2;
    }
}

