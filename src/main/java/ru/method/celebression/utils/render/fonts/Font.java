package ru.method.celebression.utils.render.fonts;

import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.utils.render.fonts.Instance;

public record Font(MsdfFont font) {
    public Instance getFont(float f) {
        return new Instance(this.font, f);
    }

    public float getWidth(String string, float f) {
        return this.font.getWidth(string, f);
    }

    public float getHeight(float f) {
        return this.font.getHeight(f);
    }
}

