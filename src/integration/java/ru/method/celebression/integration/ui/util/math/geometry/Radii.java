package ru.method.celebression.integration.ui.util.math.geometry;

import java.util.Map;
import java.util.WeakHashMap;

public final class Radii {
    private static final Map<String, Radii> roundMap = new WeakHashMap<>();

    public float lb, lt, rb, rt;;

    public static Radii zero() {
        return of(0);
    }

    private Radii(float round) {
        this.lb = round;
        this.lt = round;
        this.rb = round;
        this.rt = round;
    }

    private Radii(float lb, float lt, float rb, float rt) {
        this.lb = lb;
        this.lt = lt;
        this.rb = rb;
        this.rt = rt;
    }

    public static Radii of(float LB, float LT, float RB, float RT) {
        String key = LB + "_" + LT + "_" + RB + "_" + RT;
        return roundMap.computeIfAbsent(key, k -> new Radii(LB, LT, RB, RT));
    }

    public static Radii of(float round) {
        return of(round, round, round, round);
    }

    public static Radii of(Radii radii) {
        return of(radii.lb, radii.lt, radii.rb, radii.rt);
    }

    public Radii sub(float lb, float lt, float rb, float rt) {
        return Radii.of(this.lb - lb, this.lt - lt, this.rb - rb, this.rt - rt);
    }

    public Radii add(float lb, float lt, float rb, float rt) {
        return Radii.of(this.lb + lb, this.lt + lt, this.rb + rb, this.rt + rt);
    }

    public Radii mul(float lb, float lt, float rb, float rt) {
        return Radii.of(this.lb * lb, this.lt * lt, this.rb * rb, this.rt * rt);
    }

    public Radii sub(float amount) {
        return sub(amount, amount, amount, amount);
    }

    public Radii add(float amount) {
        return add(amount, amount, amount, amount);
    }

    public Radii mul(float amount) {
        return mul(amount, amount, amount, amount);
    }
}