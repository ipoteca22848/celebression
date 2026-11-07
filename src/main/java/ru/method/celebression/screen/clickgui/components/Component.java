package ru.method.celebression.screen.clickgui.components;

import java.util.function.Supplier;
import lombok.Generated;
import net.minecraft.class_332;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public abstract class Component
implements MinecraftClientAccess {
    private final String name;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Supplier<Float> addHeight = () -> Float.valueOf(0.0f);
    protected Supplier<Boolean> visible = () -> true;
    protected static final float MARQUEE_SPEED = 25.0f;
    protected static final float MARQUEE_GAP = 12.0f;
    protected static final long MARQUEE_DELAY_MS = 350L;
    private long marqueeHoverStartMs;

    public abstract void render(class_332 var1, int var2, int var3, float var4);

    public abstract void mouseClicked(double var1, double var3, int var5);

    public abstract void mouseReleased(double var1, double var3, int var5);

    public abstract void keyPressed(int var1, int var2, int var3);

    public abstract void keyReleased(int var1, int var2, int var3);

    public abstract void charTyped(char var1, int var2);

    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        return false;
    }

    public void renderOverlay(class_332 class_3322, int n, int n2, float f) {
    }

    public boolean isExtendedHovered(double d, double d2) {
        return false;
    }

    public boolean hasActiveOverlay() {
        return false;
    }

    public void onClose() {
    }

    public boolean isBinding() {
        return false;
    }

    public float getLayoutHeight() {
        return this.height > 0.0f ? this.height : 18.0f;
    }

    protected float getMarqueeOffset(float f, float f2, boolean bl) {
        if (!bl || f <= f2) {
            this.marqueeHoverStartMs = 0L;
            return 0.0f;
        }
        long l = System.currentTimeMillis();
        if (this.marqueeHoverStartMs == 0L) {
            this.marqueeHoverStartMs = l;
            return 0.0f;
        }
        long l2 = l - this.marqueeHoverStartMs;
        if (l2 < 350L) {
            return 0.0f;
        }
        float f3 = Math.max(1.0f, f + 12.0f);
        float f4 = (float)(l2 - 350L) / 1000.0f * 25.0f;
        return f4 % f3;
    }

    @Generated
    public Component(String string) {
        this.name = string;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public float getX() {
        return this.x;
    }

    @Generated
    public float getY() {
        return this.y;
    }

    @Generated
    public float getWidth() {
        return this.width;
    }

    @Generated
    public float getHeight() {
        return this.height;
    }

    @Generated
    public Supplier<Float> getAddHeight() {
        return this.addHeight;
    }

    @Generated
    public Supplier<Boolean> getVisible() {
        return this.visible;
    }

    @Generated
    public long getMarqueeHoverStartMs() {
        return this.marqueeHoverStartMs;
    }

    @Generated
    public void setX(float f) {
        this.x = f;
    }

    @Generated
    public void setY(float f) {
        this.y = f;
    }

    @Generated
    public void setWidth(float f) {
        this.width = f;
    }

    @Generated
    public void setHeight(float f) {
        this.height = f;
    }

    @Generated
    public void setAddHeight(Supplier<Float> supplier) {
        this.addHeight = supplier;
    }

    @Generated
    public void setVisible(Supplier<Boolean> supplier) {
        this.visible = supplier;
    }

    @Generated
    public void setMarqueeHoverStartMs(long l) {
        this.marqueeHoverStartMs = l;
    }
}

