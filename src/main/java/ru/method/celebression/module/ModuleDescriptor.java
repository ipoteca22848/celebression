package ru.method.celebression.module;

import lombok.Generated;
import ru.method.celebression.input.KeyBind;

public class ModuleDescriptor {
    private String name;
    private String af;
    private KeyBind bind;

    @Generated
    public ModuleDescriptor(String string, String string2, KeyBind keyBind) {
        this.name = string;
        this.af = string2;
        this.bind = keyBind;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String t() {
        return this.af;
    }

    @Generated
    public KeyBind getBind() {
        return this.bind;
    }
}

