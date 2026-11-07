package ru.method.celebression.util;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public final class SettingsUiUtils {
    public final String ahg;
    public final SettingsOption ahh;
    public final Supplier<String> ahi;
    public final Supplier<List<String>> ahj;
    public final IntConsumer ahk;
    public final DoubleSupplier ahl;
    public final DoubleConsumer ahm;
    public final BooleanSupplier ahn;
    public final Runnable aho;

    private SettingsUiUtils(String string, SettingsOption settingsOption, Supplier<String> supplier, Supplier<List<String>> supplier2, IntConsumer intConsumer, DoubleSupplier doubleSupplier, DoubleConsumer doubleConsumer, BooleanSupplier booleanSupplier, Runnable runnable) {
        this.ahg = string;
        this.ahh = settingsOption;
        this.ahi = supplier;
        this.ahj = supplier2;
        this.ahk = intConsumer;
        this.ahl = doubleSupplier;
        this.ahm = doubleConsumer;
        this.ahn = booleanSupplier;
        this.aho = runnable;
    }

    public static SettingsUiUtils a(String string, Supplier<String> supplier, Supplier<List<String>> supplier2, IntConsumer intConsumer) {
        return new SettingsUiUtils(string, SettingsOption.ahp, supplier, supplier2, intConsumer, null, null, null, null);
    }

    public static SettingsUiUtils a(String string, Supplier<String> supplier) {
        return new SettingsUiUtils(string, SettingsOption.ahp, supplier, null, null, null, null, null, null);
    }

    public static SettingsUiUtils a(String string, Supplier<String> supplier, DoubleSupplier doubleSupplier, DoubleConsumer doubleConsumer) {
        return new SettingsUiUtils(string, SettingsOption.ahq, supplier, null, null, doubleSupplier, doubleConsumer, null, null);
    }

    public static SettingsUiUtils a(String string, BooleanSupplier booleanSupplier, Runnable runnable) {
        return new SettingsUiUtils(string, SettingsOption.ahr, null, null, null, null, null, booleanSupplier, runnable);
    }

    public static SettingsUiUtils a(String string, Supplier<String> supplier, Runnable runnable) {
        return new SettingsUiUtils(string, SettingsOption.ahs, supplier, null, null, null, null, null, runnable);
    }

    public static enum SettingsOption {
        ahp,
        ahq,
        ahr,
        ahs;
}
}

