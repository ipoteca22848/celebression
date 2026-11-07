package ru.method.celebression.api.render.builders;

import ru.method.celebression.api.render.builders.impl.BlurBuilder;
import ru.method.celebression.api.render.builders.impl.BorderBuilder;
import ru.method.celebression.api.render.builders.impl.LiquidGlassNewBuilder;
import ru.method.celebression.api.render.builders.impl.RectangleBuilder;
import ru.method.celebression.api.render.builders.impl.RoundedOutlineBuilder;
import ru.method.celebression.api.render.builders.impl.TextBuilder;
import ru.method.celebression.api.render.builders.impl.TextureBuilder;

public final class Builder {
    private static final ThreadLocal<RectangleBuilder> RECTANGLE_BUILDER = ThreadLocal.withInitial(RectangleBuilder::new);
    private static final ThreadLocal<BorderBuilder> BORDER_BUILDER = ThreadLocal.withInitial(BorderBuilder::new);
    private static final ThreadLocal<TextureBuilder> TEXTURE_BUILDER = ThreadLocal.withInitial(TextureBuilder::new);
    private static final ThreadLocal<TextBuilder> TEXT_BUILDER = ThreadLocal.withInitial(TextBuilder::new);
    private static final ThreadLocal<BlurBuilder> BLUR_BUILDER = ThreadLocal.withInitial(BlurBuilder::new);
    private static final ThreadLocal<LiquidGlassNewBuilder> LIQUID_GLASS_NEW_BUILDER = ThreadLocal.withInitial(LiquidGlassNewBuilder::new);
    private static final ThreadLocal<RoundedOutlineBuilder> ROUNDED_OUTLINE_BUILDER = ThreadLocal.withInitial(RoundedOutlineBuilder::new);

    public static RoundedOutlineBuilder roundedOutline() {
        return ROUNDED_OUTLINE_BUILDER.get();
    }

    public static RectangleBuilder rectangle() {
        return RECTANGLE_BUILDER.get();
    }

    public static BorderBuilder border() {
        return BORDER_BUILDER.get();
    }

    public static TextureBuilder texture() {
        return TEXTURE_BUILDER.get();
    }

    public static TextBuilder text() {
        return TEXT_BUILDER.get();
    }

    public static BlurBuilder blur() {
        return BLUR_BUILDER.get();
    }

    public static LiquidGlassNewBuilder liquidGlassNew() {
        return LIQUID_GLASS_NEW_BUILDER.get();
    }
}

