package ru.method.celebression.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.class_1041;
import net.minecraft.class_1306;
import net.minecraft.class_1659;
import net.minecraft.class_1664;
import net.minecraft.class_310;
import net.minecraft.class_313;
import net.minecraft.class_315;
import net.minecraft.class_319;
import net.minecraft.class_3419;
import net.minecraft.class_4061;
import net.minecraft.class_4063;
import net.minecraft.class_4065;
import net.minecraft.class_437;
import net.minecraft.class_5365;
import net.minecraft.class_6597;
import net.minecraft.class_7172;
import ru.method.celebression.screen.ControlsSettingsScreen;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.util.SettingsUiUtils;

public final class PerformanceSettings {
    private PerformanceSettings() {
    }

    public static List<SettingsUiUtils> qN() {
        class_310 class_3102 = class_310.method_1551();
        class_315 class_3152 = class_3102.field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        arrayList.addAll(PerformanceSettings.qU());
        arrayList.add(PerformanceSettings.a("\u0413\u0440\u0430\u0444\u0438\u043a\u0430", class_3152.method_42534(), class_5365.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u043e\u0440\u0438\u0441\u043e\u0432\u043a\u0430", (class_7172<Integer>)class_3152.method_42503(), 2, 32, (int n) -> n + " \u0447\u0430\u043d\u043a\u043e\u0432"));
        arrayList.add(PerformanceSettings.a("\u0421\u0438\u043c\u0443\u043b\u044f\u0446\u0438\u044f", (class_7172<Integer>)class_3152.method_42510(), 5, 32, (int n) -> n + " \u0447\u0430\u043d\u043a\u043e\u0432"));
        arrayList.add(PerformanceSettings.a("\u0421\u0431\u043e\u0440\u043a\u0430 \u0447\u0430\u043d\u043a\u043e\u0432", class_3152.method_41798(), class_6597.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u0427\u0430\u0441\u0442\u043e\u0442\u0430 \u043a\u0430\u0434\u0440\u043e\u0432", (class_7172<Integer>)class_3152.method_42524(), 10, 260, (int n) -> n >= 260 ? "\u041c\u0430\u043a\u0441\u0438\u043c\u0443\u043c" : n + " \u043a/\u0441"));
        arrayList.add(PerformanceSettings.a("\u041c\u044f\u0433\u043a\u043e\u0435 \u043e\u0441\u0432\u0435\u0449\u0435\u043d\u0438\u0435", (class_7172<Boolean>)class_3152.method_41792()));
        arrayList.add(PerformanceSettings.a("V-Sync", (class_7172<Boolean>)class_3152.method_42433()));
        arrayList.add(PerformanceSettings.a("\u0418\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441", (class_7172<Integer>)class_3152.method_42474(), 0, 4, (int n) -> n == 0 ? "\u0410\u0432\u0442\u043e" : String.valueOf(n)));
        arrayList.add(PerformanceSettings.a("\u0418\u043d\u0434\u0438\u043a\u0430\u0442\u043e\u0440 \u0430\u0442\u0430\u043a\u0438", class_3152.method_42565(), class_4061.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u042f\u0440\u043a\u043e\u0441\u0442\u044c", (class_7172<Double>)class_3152.method_42473(), 0.0, 1.0, (double d) -> d <= 0.01 ? "\u041c\u0440\u0430\u0447\u043d\u043e" : (d >= 0.99 ? "\u042f\u0440\u043a\u043e" : Math.round(d * 100.0) + "%")));
        arrayList.add(PerformanceSettings.a("\u041e\u0431\u043b\u0430\u043a\u0430", class_3152.method_42528(), class_4063.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u041f\u043e\u043b\u043d\u043e\u044d\u043a\u0440\u0430\u043d\u043d\u044b\u0439 \u0440\u0435\u0436\u0438\u043c", (class_7172<Boolean>)class_3152.method_42447()));
        arrayList.add(PerformanceSettings.a("\u0427\u0430\u0441\u0442\u0438\u0446\u044b", class_3152.method_42475(), class_40662 -> switch (class_40662.name()) {
            case "ALL" -> "\u0412\u0441\u0435";
            case "DECREASED" -> "\u041c\u0435\u043d\u044c\u0448\u0435";
            default -> "\u041c\u0438\u043d\u0438\u043c\u0443\u043c";
        }));
        arrayList.add(PerformanceSettings.a("\u0422\u0435\u043d\u0438 \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439", (class_7172<Boolean>)class_3152.method_42435()));
        arrayList.add(PerformanceSettings.a("\u0414\u043e \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0438", (class_7172<Double>)class_3152.method_42517(), 0.5, 5.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u041c\u0435\u0440\u0446\u0430\u043d\u0438\u0435 \u0447\u0430\u0440", (class_7172<Double>)class_3152.method_48580(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0417\u0430\u043c\u0435\u0442\u043d\u043e\u0441\u0442\u044c \u0447\u0430\u0440", (class_7172<Double>)class_3152.method_48581(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0420\u0430\u0437\u043c\u044b\u0442\u0438\u0435 \u0444\u043e\u043d\u0430", (class_7172<Integer>)class_3152.method_57702(), 0, 10, String::valueOf));
        arrayList.add(PerformanceSettings.a("\u041f\u043e\u043a\u0430\u0447\u0438\u0432\u0430\u043d\u0438\u0435 \u043a\u0430\u043c\u0435\u0440\u044b", (class_7172<Boolean>)class_3152.method_42448()));
        return arrayList;
    }

    public static List<SettingsUiUtils> qO() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        for (class_1664 class_16642 : class_1664.values()) {
            arrayList.add(SettingsUiUtils.a(PerformanceSettings.a(class_16642), () -> class_3152.method_32594(class_16642), () -> {
                class_3152.method_1635(class_16642, !class_3152.method_32594(class_16642));
                class_3152.method_1643();
            }));
        }
        arrayList.add(PerformanceSettings.a("\u041e\u0441\u043d\u043e\u0432\u043d\u0430\u044f \u0440\u0443\u043a\u0430", class_3152.method_42552(), class_1306.values(), class_13062 -> class_13062 == class_1306.field_6182 ? "\u041b\u0435\u0432\u0430\u044f" : "\u041f\u0440\u0430\u0432\u0430\u044f"));
        return arrayList;
    }

    public static List<SettingsUiUtils> qP() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        for (class_3419 class_34192 : class_3419.values()) {
            arrayList.add(PerformanceSettings.a(PerformanceSettings.a(class_34192), (class_7172<Double>)class_3152.method_45578(class_34192), 0.0, 1.0, (double d) -> d <= 0.005 ? "\u0412\u044b\u043a\u043b" : Math.round(d * 100.0) + "%"));
        }
        arrayList.add(PerformanceSettings.a("\u041d\u0430\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u043d\u044b\u0439 \u0437\u0432\u0443\u043a", (class_7172<Boolean>)class_3152.method_42444()));
        arrayList.add(PerformanceSettings.a("\u0421\u0443\u0431\u0442\u0438\u0442\u0440\u044b", (class_7172<Boolean>)class_3152.method_42443()));
        arrayList.add(SettingsUiUtils.a("\u0423\u0441\u0442\u0440\u043e\u0439\u0441\u0442\u0432\u043e \u0432\u044b\u0432\u043e\u0434\u0430", () -> {
            String string = (String)class_3152.method_42477().method_41753();
            return string == null || string.isEmpty() ? "\u0421\u0438\u0441\u0442\u0435\u043c\u043d\u043e\u0435" : string;
        }));
        return arrayList;
    }

    public static List<SettingsUiUtils> qQ() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        arrayList.add(SettingsUiUtils.a("\u041a\u043b\u0430\u0432\u0438\u0448\u0438 \u0443\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f", () -> "\u041d\u0430\u0441\u0442\u0440\u043e\u0438\u0442\u044c", () -> {
            class_310 class_3102 = class_310.method_1551();
            class_437 class_4372 = class_3102.field_1755;
            if (class_4372 instanceof SettingsSubScreen) {
                SettingsSubScreen settingsSubScreen = (SettingsSubScreen)class_4372;
                settingsSubScreen.qK();
            } else {
                class_3102.method_1507((class_437)new ControlsSettingsScreen(class_3102.field_1755));
            }
        }));
        arrayList.add(PerformanceSettings.a("\u0427\u0443\u0432\u0441\u0442\u0432\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0441\u0442\u044c \u043c\u044b\u0448\u0438", (class_7172<Double>)class_3152.method_42495(), 0.0, 1.0, (double d) -> Math.round(d * 200.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0418\u043d\u0432\u0435\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043c\u044b\u0448\u044c", (class_7172<Boolean>)class_3152.method_42438()));
        arrayList.add(PerformanceSettings.a("\u0410\u0432\u0442\u043e\u043f\u0440\u044b\u0436\u043e\u043a", (class_7172<Boolean>)class_3152.method_42423()));
        arrayList.add(PerformanceSettings.a("\u041a\u0440\u0430\u0441\u0442\u044c\u0441\u044f \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435\u043c", (class_7172<Boolean>)class_3152.method_42449()));
        arrayList.add(PerformanceSettings.a("\u0411\u0435\u0436\u0430\u0442\u044c \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435\u043c", (class_7172<Boolean>)class_3152.method_42450()));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u044f\u043c\u043e\u0439 \u0432\u0432\u043e\u0434 \u043c\u044b\u0448\u0438", (class_7172<Boolean>)class_3152.method_41807()));
        arrayList.add(PerformanceSettings.a("\u0414\u0438\u0441\u043a\u0440\u0435\u0442\u043d\u0430\u044f \u043f\u0440\u043e\u043a\u0440\u0443\u0442\u043a\u0430", (class_7172<Boolean>)class_3152.method_42439()));
        arrayList.add(PerformanceSettings.a("\u0421\u0435\u043d\u0441\u043e\u0440\u043d\u044b\u0439 \u044d\u043a\u0440\u0430\u043d", (class_7172<Boolean>)class_3152.method_42446()));
        arrayList.add(PerformanceSettings.a("\u0412\u043a\u043b\u0430\u0434\u043a\u0430 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u043e\u043f\u0435\u0440\u0430\u0442\u043e\u0440\u0430", (class_7172<Boolean>)class_3152.method_47395()));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u043e\u043a\u0440\u0443\u0442\u043a\u0430", (class_7172<Double>)class_3152.method_41806(), 0.01, 10.0, (double d) -> String.format(Locale.ROOT, "%.2f", d)));
        return arrayList;
    }

    public static List<SettingsUiUtils> qR() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        arrayList.add(PerformanceSettings.a("\u0427\u0430\u0442", class_3152.method_42539(), class_1659.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u0426\u0432\u0435\u0442\u0430", (class_7172<Boolean>)class_3152.method_42427()));
        arrayList.add(PerformanceSettings.a("\u0421\u0441\u044b\u043b\u043a\u0438", (class_7172<Boolean>)class_3152.method_42429()));
        arrayList.add(PerformanceSettings.a("\u041f\u043e\u0434\u0442\u0432\u0435\u0440\u0436\u0434\u0435\u043d\u0438\u0435 \u0441\u0441\u044b\u043b\u043e\u043a", (class_7172<Boolean>)class_3152.method_42431()));
        arrayList.add(PerformanceSettings.a("\u041f\u043e\u0434\u0441\u043a\u0430\u0437\u043a\u0430 \u043a\u043e\u043c\u0430\u043d\u0434", (class_7172<Boolean>)class_3152.method_42425()));
        arrayList.add(PerformanceSettings.a("\u0421\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u0441\u043e\u0432\u043f\u0430\u0434\u0430\u044e\u0449\u0438\u0435 \u043d\u0438\u043a\u0438", (class_7172<Boolean>)class_3152.method_42451()));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c", (class_7172<Double>)class_3152.method_42542(), 0.0, 1.0, (double d) -> Math.round(d * 90.0 + 10.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0444\u043e\u043d\u0430", (class_7172<Double>)class_3152.method_42550(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u041c\u0430\u0441\u0448\u0442\u0430\u0431", (class_7172<Double>)class_3152.method_42554(), 0.0, 1.0, (double d) -> d <= 0.01 ? "\u0412\u044b\u043a\u043b" : Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0428\u0438\u0440\u0438\u043d\u0430", (class_7172<Double>)class_3152.method_42556(), 0.0, 1.0, (double d) -> Math.round(40.0 + d * 280.0) + " px"));
        arrayList.add(PerformanceSettings.a("\u0412\u044b\u0441\u043e\u0442\u0430 (\u0432 \u0444\u043e\u043a\u0443\u0441\u0435)", (class_7172<Double>)class_3152.method_41803(), 0.0, 1.0, (double d) -> Math.round(20.0 + d * 160.0) + " px"));
        arrayList.add(PerformanceSettings.a("\u0412\u044b\u0441\u043e\u0442\u0430 (\u0431\u0435\u0437 \u0444\u043e\u043a\u0443\u0441\u0430)", (class_7172<Double>)class_3152.method_41801(), 0.0, 1.0, (double d) -> Math.round(20.0 + d * 160.0) + " px"));
        arrayList.add(PerformanceSettings.a("\u041c\u0435\u0436\u0441\u0442\u0440\u043e\u0447\u043d\u044b\u0439 \u0438\u043d\u0442\u0435\u0440\u0432\u0430\u043b", (class_7172<Double>)class_3152.method_42546(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u0447\u0430\u0442\u0430", (class_7172<Double>)class_3152.method_42561(), 0.0, 6.0, (double d) -> d <= 0.01 ? "\u041d\u0435\u0442" : String.format(Locale.ROOT, "%.1f \u0441", d)));
        arrayList.add(PerformanceSettings.a("\u0414\u0438\u043a\u0442\u043e\u0440", class_3152.method_42476(), class_4065.values(), PerformanceSettings::a));
        return arrayList;
    }

    public static List<SettingsUiUtils> qS() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        arrayList.add(PerformanceSettings.a("\u0414\u0438\u043a\u0442\u043e\u0440", class_3152.method_42476(), class_4065.values(), PerformanceSettings::a));
        arrayList.add(PerformanceSettings.a("\u0421\u0443\u0431\u0442\u0438\u0442\u0440\u044b", (class_7172<Boolean>)class_3152.method_42443()));
        arrayList.add(PerformanceSettings.a("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0444\u043e\u043d\u0430", (class_7172<Double>)class_3152.method_42550(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(SettingsUiUtils.a("\u0424\u043e\u043d \u0442\u0435\u043a\u0441\u0442\u0430 \u0432\u0435\u0437\u0434\u0435", () -> !class_3152.method_42445().method_41753(), () -> class_3152.method_42445().method_41748(!class_3152.method_42445().method_41753())));
        arrayList.add(PerformanceSettings.a("\u0422\u0440\u044f\u0441\u043a\u0430 \u043e\u0442 \u0443\u0440\u043e\u043d\u0430", (class_7172<Double>)class_3152.method_48974(), 0.0, 1.0, (double d) -> d <= 0.001 ? "\u0412\u044b\u043a\u043b" : Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0418\u0441\u043a\u0430\u0436\u0435\u043d\u0438\u0435 \u043f\u0440\u0438 \u0438\u0441\u043a\u0440\u0438\u0432\u043b\u0435\u043d\u0438\u0438", (class_7172<Double>)class_3152.method_42453(), 0.0, 1.0, (double d) -> d <= 0.001 ? "\u0412\u044b\u043a\u043b" : Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u042d\u0444\u0444\u0435\u043a\u0442\u044b \u043f\u043e\u043b\u044f \u0437\u0440\u0435\u043d\u0438\u044f", (class_7172<Double>)class_3152.method_42454(), 0.0, 1.0, (double d) -> d <= 0.001 ? "\u0412\u044b\u043a\u043b" : Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u041c\u043e\u043d\u043e\u0445\u0440\u043e\u043c\u043d\u044b\u0439 \u043b\u043e\u0433\u043e\u0442\u0438\u043f", (class_7172<Boolean>)class_3152.method_41772()));
        arrayList.add(PerformanceSettings.a("\u0421\u043a\u0440\u044b\u0442\u044c \u0437\u0430\u0441\u0442\u0430\u0432\u043a\u0443", (class_7172<Boolean>)class_3152.method_54581()));
        arrayList.add(PerformanceSettings.a("\u0412\u0440\u0435\u043c\u044f \u0443\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u0439", (class_7172<Double>)class_3152.method_48191(), 0.5, 10.0, (double d) -> "x" + String.format(Locale.ROOT, "%.1f", d)));
        arrayList.add(PerformanceSettings.a("\u0420\u0430\u0437\u043c\u044b\u0442\u0438\u0435 \u0444\u043e\u043d\u0430 \u043c\u0435\u043d\u044e", (class_7172<Integer>)class_3152.method_57702(), 0, 10, (int n) -> n == 0 ? "\u0412\u044b\u043a\u043b" : String.valueOf(n)));
        arrayList.add(PerformanceSettings.a("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u0430\u043d\u043e\u0440\u0430\u043c\u044b", (class_7172<Double>)class_3152.method_45581(), 0.0, 1.0, (double d) -> Math.round(d * 100.0) + "%"));
        arrayList.add(PerformanceSettings.a("\u0412\u044b\u0441\u043e\u043a\u0430\u044f \u043a\u043e\u043d\u0442\u0440\u0430\u0441\u0442\u043d\u043e\u0441\u0442\u044c", (class_7172<Boolean>)class_3152.method_49600()));
        return arrayList;
    }

    public static List<SettingsUiUtils> qT() {
        class_315 class_3152 = class_310.method_1551().field_1690;
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>();
        arrayList.add(PerformanceSettings.a("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u044f Realms", (class_7172<Boolean>)class_3152.method_42440()));
        arrayList.add(PerformanceSettings.a("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u043c\u0435\u043d\u044f \u0432 \u0441\u043f\u0438\u0441\u043a\u0435", (class_7172<Boolean>)class_3152.method_42441()));
        arrayList.add(PerformanceSettings.a("\u0420\u0430\u0441\u0448\u0438\u0440\u0435\u043d\u043d\u0430\u044f \u0442\u0435\u043b\u0435\u043c\u0435\u0442\u0440\u0438\u044f", (class_7172<Boolean>)class_3152.method_47609()));
        return arrayList;
    }

    public static List<SettingsUiUtils> qU() {
        class_310 class_3102 = class_310.method_1551();
        class_1041 class_10412 = class_3102.method_22683();
        class_313 class_3132 = class_10412.method_20831();
        if (class_3132 == null) {
            return List.of(SettingsUiUtils.a("\u0420\u0430\u0437\u0440\u0435\u0448\u0435\u043d\u0438\u0435", () -> class_10412.method_4480() + " x " + class_10412.method_4507()));
        }
        Supplier<List<String>> supplier = () -> {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("\u0422\u0435\u043a\u0443\u0449\u0435\u0435");
            for (int i = 0; i < class_3132.method_1621(); ++i) {
                arrayList.add(PerformanceSettings.a(class_3132.method_1620(i)));
            }
            return arrayList;
        };
        return List.of(SettingsUiUtils.a("\u0420\u0430\u0437\u0440\u0435\u0448\u0435\u043d\u0438\u0435", () -> class_10412.method_4511().map(PerformanceSettings::a).orElse("\u0422\u0435\u043a\u0443\u0449\u0435\u0435"), supplier, (int n) -> {
            if (n < 0 || n > class_3132.method_1621()) {
                return;
            }
            Optional<class_319> optional = n == 0 ? Optional.empty() : Optional.of(class_3132.method_1620(n - 1));
            class_10412.method_4505(optional);
            class_3102.field_1690.field_1828 = optional.map(class_319::method_1670).orElse("");
            class_10412.method_4475();
        }));
    }

    private static SettingsUiUtils a(String string, class_7172<Boolean> class_71722) {
        return SettingsUiUtils.a(string, class_71722::method_41753, () -> class_71722.method_41748(!class_71722.method_41753()));
    }

    private static SettingsUiUtils a(String string, class_7172<Integer> class_71722, int n, int n2, IntFunction<String> intFunction) {
        return SettingsUiUtils.a(string, () -> intFunction.apply(class_71722.method_41753()), () -> (double)(class_71722.method_41753() - n) / (double)(n2 - n), (double d) -> class_71722.method_41748((int)Math.round((double)n + d * (double)(n2 - n))));
    }

    private static SettingsUiUtils a(String string, class_7172<Double> class_71722, double d, double d2, DoubleFunction<String> doubleFunction) {
        return SettingsUiUtils.a(string, () -> doubleFunction.apply(class_71722.method_41753()), () -> (class_71722.method_41753() - d) / (d2 - d), (double d3) -> class_71722.method_41748(d + d3 * (d2 - d)));
    }

    private static <T> SettingsUiUtils a(String string, class_7172<T> class_71722, T[] TArray, Function<T, String> function) {
        return SettingsUiUtils.a(string, () -> (String)function.apply(class_71722.method_41753()), () -> {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (T value : TArray) {
                arrayList.add(function.apply(value));
            }
            return arrayList;
        }, (int n) -> {
            if (n >= 0 && n < TArray.length) {
                class_71722.method_41748(TArray[n]);
            }
        });
    }

    private static <T extends Enum<T>> SettingsUiUtils a(String string, class_7172<T> class_71722, Function<T, String> function) {
        return SettingsUiUtils.a(string, () -> function.apply(class_71722.method_41753()), () -> {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (T value : class_71722.method_41753().getDeclaringClass().getEnumConstants()) {
                arrayList.add(function.apply(value));
            }
            return arrayList;
        }, (int n) -> {
            T[] enumArray = class_71722.method_41753().getDeclaringClass().getEnumConstants();
            if (n >= 0 && n < enumArray.length) {
                class_71722.method_41748(enumArray[n]);
            }
        });
    }

    private static String a(class_5365 class_53652) {
        return switch (class_53652) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_5365.field_25427 -> "\u0411\u044b\u0441\u0442\u0440\u043e";
            case class_5365.field_25428 -> "\u0414\u0435\u0442\u0430\u043b\u044c\u043d\u043e";
            case class_5365.field_25429 -> "\u0423\u043b\u0443\u0447\u0448\u0435\u043d\u043e";
        };
    }

    private static String a(class_6597 class_65972) {
        return switch (class_65972) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_6597.field_34788 -> "\u041d\u0435\u0442";
            case class_6597.field_34789 -> "\u041f\u043e\u0442\u043e\u043a\u043e\u0432\u0430\u044f";
            case class_6597.field_34790 -> "\u0411\u043b\u0438\u0436\u043d\u0438\u0435";
        };
    }

    private static String a(class_4061 class_40612) {
        return switch (class_40612) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_4061.field_18151 -> "\u0412\u044b\u043a\u043b";
            case class_4061.field_18152 -> "\u041f\u0440\u0438\u0446\u0435\u043b";
            case class_4061.field_18153 -> "\u0425\u043e\u0442\u0431\u0430\u0440";
        };
    }

    private static String a(class_4063 class_40632) {
        return switch (class_40632) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_4063.field_18162 -> "\u0412\u044b\u043a\u043b";
            case class_4063.field_18163 -> "\u0411\u044b\u0441\u0442\u0440\u044b\u0435";
            case class_4063.field_18164 -> "\u041e\u0431\u044a\u0451\u043c\u043d\u044b\u0435";
        };
    }

    private static String a(class_1664 class_16642) {
        return switch (class_16642) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_1664.field_7559 -> "\u041f\u043b\u0430\u0449";
            case class_1664.field_7564 -> "\u041a\u0443\u0440\u0442\u043a\u0430";
            case class_1664.field_7568 -> "\u041b\u0435\u0432\u044b\u0439 \u0440\u0443\u043a\u0430\u0432";
            case class_1664.field_7570 -> "\u041f\u0440\u0430\u0432\u044b\u0439 \u0440\u0443\u043a\u0430\u0432";
            case class_1664.field_7566 -> "\u041b\u0435\u0432\u0430\u044f \u0448\u0442\u0430\u043d\u0438\u043d\u0430";
            case class_1664.field_7565 -> "\u041f\u0440\u0430\u0432\u0430\u044f \u0448\u0442\u0430\u043d\u0438\u043d\u0430";
            case class_1664.field_7563 -> "\u0428\u043b\u044f\u043f\u0430";
        };
    }

    private static String a(class_3419 class_34192) {
        return switch (class_34192) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_3419.field_15250 -> "\u041e\u0431\u0449\u0430\u044f \u0433\u0440\u043e\u043c\u043a\u043e\u0441\u0442\u044c";
            case class_3419.field_15253 -> "\u041c\u0443\u0437\u044b\u043a\u0430";
            case class_3419.field_15247 -> "\u041f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0442\u0435\u043b\u044c";
            case class_3419.field_15252 -> "\u041f\u043e\u0433\u043e\u0434\u0430";
            case class_3419.field_15245 -> "\u0411\u043b\u043e\u043a\u0438";
            case class_3419.field_15251 -> "\u0412\u0440\u0430\u0436\u0434\u0435\u0431\u043d\u044b\u0435";
            case class_3419.field_15254 -> "\u0414\u0440\u0443\u0436\u0435\u043b\u044e\u0431\u043d\u044b\u0435";
            case class_3419.field_15248 -> "\u0418\u0433\u0440\u043e\u043a\u0438";
            case class_3419.field_15256 -> "\u041e\u043a\u0440\u0443\u0436\u0435\u043d\u0438\u0435";
            case class_3419.field_15246 -> "\u0413\u043e\u043b\u043e\u0441";
        };
    }

    private static String a(class_1659 class_16592) {
        return switch (class_16592) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_1659.field_7538 -> "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c";
            case class_1659.field_7539 -> "\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u043e\u043c\u0430\u043d\u0434\u044b";
            case class_1659.field_7536 -> "\u0421\u043a\u0440\u044b\u0442\u044c";
        };
    }

    private static String a(class_4065 class_40652) {
        return switch (class_40652) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_4065.field_18176 -> "\u0412\u044b\u043a\u043b";
            case class_4065.field_18177 -> "\u0412\u0441\u0451";
            case class_4065.field_18178 -> "\u0427\u0430\u0442";
            case class_4065.field_18179 -> "\u0421\u0438\u0441\u0442\u0435\u043c\u0430";
        };
    }

    private static String a(class_319 class_3192) {
        return class_3192.method_1668() + " x " + class_3192.method_1669() + " (" + class_3192.method_1671() + " \u0413\u0446)";
    }
}
