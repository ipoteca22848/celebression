package ru.method.celebression.service;

/** Branded API delegating to the unchanged native signature verifier. */
public final class NativeProtectionStatus {
    private NativeProtectionStatus() {}
    public static boolean enabled() { return ru.method.linkenvisuals.obf.ma.enabled(); }
    public static boolean ad(String body, String signature) {
        return ru.method.linkenvisuals.obf.ma.ad(body, signature);
    }
}
