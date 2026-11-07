package ru.method.celebression.api.setting;

import java.util.function.Supplier;
import ru.method.celebression.render.Point2f;
import ru.method.celebression.api.setting.Setting;

public class PositionSetting
extends Setting<Point2f> {
    public PositionSetting(String string, Point2f point2f) {
        super(string, point2f);
    }

    public PositionSetting(String string, Point2f point2f, Supplier<Boolean> supplier) {
        super(string, point2f, supplier);
    }
}

