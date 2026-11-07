package ru.method.celebression.integration.ui.screens.mainmenu.render;

public record TextureRegion(float u0, float v0, float u1, float v1) {
    private static final TextureRegion FULL = new TextureRegion(0.0f, 0.0f, 1.0f, 1.0f);

    public static TextureRegion full() {
        return FULL;
    }

    public static TextureRegion of(float u0, float v0, float u1, float v1) {
        return new TextureRegion(u0, v0, u1, v1);
    }
}
