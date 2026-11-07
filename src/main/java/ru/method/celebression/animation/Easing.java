package ru.method.celebression.animation;

import java.util.function.Function;
import lombok.Generated;

public enum Easing {
    aiQ(d -> d),
    aiR(d -> -(Math.cos(Math.PI * d) - 1.0) / 2.0),
    aiS(d -> -(Math.cos(Math.PI * d) - 1.0) / 2.0),
    aiT(d -> d == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * d)),
    aiU(d -> d < 0.5 ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * d, 2.0))) / 2.0 : (Math.sqrt(1.0 - Math.pow(-2.0 * d + 2.0, 2.0)) + 1.0) / 2.0),
    aiV(d -> d < 0.5 ? 4.0 * d * d * d : 1.0 - Math.pow(-2.0 * d + 2.0, 3.0) / 2.0),
    aiW(d -> d < 0.5 ? 8.0 * d * d * d * d : 1.0 - Math.pow(-2.0 * d + 2.0, 4.0) / 2.0),
    aiX(d -> 1.0 + 2.70158 * Math.pow(d - 1.0, 3.0) + 1.70158 * Math.pow(d - 1.0, 2.0)),
    aiY(d -> Math.sqrt(1.0 - Math.pow(d - 1.0, 2.0))),
    aiZ(d -> 1.0 - Math.pow(1.0 - d, 3.0)),
    aja(d -> -2.0 * Math.pow(d, 3.0) + 3.0 * Math.pow(d, 2.0)),
    ajb(d -> 3.0 * Math.pow(1.0 - d, 2.0) * d * -0.2 + 3.0 * (1.0 - d) * Math.pow(d, 2.0) * 1.5 + Math.pow(d, 3.0));
    private final Function<Double, Double> ajc;

public double aa(double d) {
        return this.ajc.apply(d);
    }

    @Generated
    private Easing(Function<Double, Double> function) {
        this.ajc = function;
    }

}

