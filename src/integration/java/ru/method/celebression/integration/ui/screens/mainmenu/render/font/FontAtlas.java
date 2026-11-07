package ru.method.celebression.integration.ui.screens.mainmenu.render.font;

public final class FontAtlas {
    public static final FontAtlas INSTANCE = new FontAtlas();

    private FontAtlas() {}

    public void ensureLoaded() {}

    public boolean hasGlyph(int codePoint) {
        return true;
    }
}
