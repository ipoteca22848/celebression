package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.ColorUtils;

public class HitColorModule
extends Module {
    private static final Map<Integer, Long> Bw = new ConcurrentHashMap<Integer, Long>();
    private static final Map<Integer, Float> Bx = new ConcurrentHashMap<Integer, Float>();
    public final ColorSetting By = new ColorSetting("settings.hitcolor.color", new Color(ColorUtils.getGlobalColor().getRed(), ColorUtils.getGlobalColor().getGreen(), ColorUtils.getGlobalColor().getBlue(), 255));
    public final SliderSetting Bz = new SliderSetting("settings.hitcolor.opacity", 140.0f, 0.0f, 1000.0f, 1.0f);
    public final BooleanSetting BA = new BooleanSetting("settings.hitcolor.colorarmor", false);

    public static void ab(int n) {
        Bw.put(n, System.currentTimeMillis());
    }

    public static boolean a(int n, long l) {
        Long l2 = Bw.get(n);
        return l2 != null && System.currentTimeMillis() - l2 <= l;
    }

    public static float a(int n, boolean bl) {
        float f;
        float f2;
        Float f3 = Bx.get(n);
        if (!bl && f3 == null) {
            return 0.0f;
        }
        float f4 = f3 == null ? 0.0f : f3.floatValue();
        float f5 = f4 + ((f2 = bl ? 1.0f : 0.0f) - f4) * (f = f2 > f4 ? 0.85f : 0.18f);
        if (f5 <= 0.001f) {
            if (f3 != null) {
                Bx.remove(n);
            }
            return 0.0f;
        }
        Bx.put(n, Float.valueOf(f5));
        return f5;
    }

    @Override
    public void onDisable() {
        Bw.clear();
        Bx.clear();
        super.onDisable();
    }

    public HitColorModule() {
        super("HitColor", ModuleCategory.ql);
    }

    @EventHandler
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        class_1297 class_12972 = attackEntityEvent.b();
        if (class_12972 != null) {
            HitColorModule.ab(class_12972.method_5628());
        }
    }
}

