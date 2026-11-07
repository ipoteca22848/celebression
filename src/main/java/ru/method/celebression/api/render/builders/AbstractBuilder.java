package ru.method.celebression.api.render.builders;

public abstract class AbstractBuilder<T> {
    public AbstractBuilder() {
        this.reset();
    }

    public final T build() {
        T t = this._build();
        this.reset();
        return t;
    }

    protected abstract void reset();

    protected abstract T _build();
}

