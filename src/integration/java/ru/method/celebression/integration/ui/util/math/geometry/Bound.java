package ru.method.celebression.integration.ui.util.math.geometry;

import lombok.AllArgsConstructor;

import ru.method.celebression.integration.ui.util.math.geometry.Side;

@AllArgsConstructor
public final class Bound
{

    public float x, y, width, height;

    public static Bound of(float x, float y, float width, float height)
    {
        return new Bound(x, y, width, height);
    }

    public static Bound relative(Bound parent, float relX, float relY, float width, float height)
    {
        return new Bound(parent.x + relX, parent.y + relY, width, height);
    }

    public static Bound fillWidth(Bound parent, float relY, float height, float paddingH)
    {
        return new Bound(parent.x + paddingH, parent.y + relY, parent.width - paddingH * 2, height);
    }

    public static Bound fill(Bound parent, float inset)
    {
        return new Bound(parent.x + inset, parent.y + inset,
                parent.width - inset * 2, parent.height - inset * 2);
    }

    public static Bound center(Bound parent, float width, float height)
    {
        return new Bound(
                parent.x + (parent.width  - width)  / 2f,
                parent.y + (parent.height - height) / 2f,
                width, height
        );
    }

    public static Bound centerX(Bound parent, float relY, float width, float height)
    {
        return new Bound(parent.x + (parent.width - width) / 2f, parent.y + relY, width, height);
    }

    public static Bound anchorRight(Bound parent, float relY, float width, float height, float marginRight)
    {
        return new Bound(parent.x + parent.width - width - marginRight, parent.y + relY, width, height);
    }

    public static Bound anchorBottom(Bound parent, float relX, float width, float height, float marginBottom)
    {
        return new Bound(parent.x + relX, parent.y + parent.height - height - marginBottom, width, height);
    }

    public float right()
    {
        return x + width;
    }

    public float bottom()
    {
        return y + height;
    }

    public float centerX()
    {
        return x + width  / 2f;
    }

    public float centerY()
    {
        return y + height / 2f;
    }

    public boolean contains(double px, double py)
    {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    public Bound copy()
    {
        return new Bound(x, y, width, height);
    }

    public Bound expand(Side side, float amount)
    {
        side.expand(this, amount);
        return this;
    }

    public Bound exp(Side side, float amount)
    {
        return expand(side, amount);
    }

    public Bound exp(float amount)
    {
        return expand(Side.ALL, amount);
    }

    public Bound set(float x, float y)
    {
        this.x = x; this.y = y;
        return this;
    }

    public Bound add(float x, float y)
    {
        this.x += x; this.y += y;
        return this;
    }

    public Bound sub(float x, float y)
    {
        this.x -= x; this.y -= y;
        return this;
    }

    public Bound resolve(Bound parent)
    {
        return new Bound(parent.x + x, parent.y + y, width, height);
    }

    public Bound snap(float scale)
    {
        float resolvedScale = Math.max(scale, 1.0f);
        float snappedX = Math.round(x * resolvedScale) / resolvedScale;
        float snappedY = Math.round(y * resolvedScale) / resolvedScale;
        float snappedRight = Math.round((x + width) * resolvedScale) / resolvedScale;
        float snappedBottom = Math.round((y + height) * resolvedScale) / resolvedScale;

        x = snappedX;
        y = snappedY;
        width = Math.max(0.0f, snappedRight - snappedX);
        height = Math.max(0.0f, snappedBottom - snappedY);
        return this;
    }

    public Bound snapped(float scale)
    {
        return copy().snap(scale);
    }
}
