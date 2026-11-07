package ru.method.celebression.utils.render.fonts;

import ru.method.celebression.api.render.msdf.MsdfFont;

public record Instance(MsdfFont font, float size) {
    public float getWidth(String string) {
        return this.font.getWidth(string, this.size);
    }

    public float getHeight() {
        return this.font.getHeight(this.size);
    }
}

