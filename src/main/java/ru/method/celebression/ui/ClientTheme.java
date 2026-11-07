package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.utils.render.ColorUtils;

public enum ClientTheme
implements NamedOption  {
    YZ("Midnight", new Color(126, 223, 180), new Color(15, 18, 23), new Color(244, 247, 250)),
    Za("Graphite", new Color(154, 164, 173), new Color(18, 19, 23), new Color(240, 242, 247)),
    Zb("Amethyst", new Color(177, 138, 224), new Color(20, 16, 28), new Color(247, 242, 255)),
    Zc("Ocean", new Color(77, 163, 232), new Color(12, 18, 28), new Color(232, 246, 255)),
    Zd("Rose", new Color(224, 92, 116), new Color(26, 14, 22), new Color(255, 239, 246)),
    Ze("Emerald", new Color(62, 207, 122), new Color(12, 22, 18), new Color(236, 255, 242)),
    Zf("Light", new Color(77, 163, 232), new Color(236, 238, 243), new Color(28, 30, 38)),
    Zg("Purple", new Color(138, 99, 232), new Color(244, 246, 250), new Color(30, 31, 42));
    private final String Zh;
    private final Color Zi;
    private final Color Zj;
    private final Color Zk;

private ClientTheme(String string2, Color color, Color color2, Color color3) {
        this.Zh = string2;
        this.Zi = color;
        this.Zj = color2;
        this.Zk = color3;
    }

    @Override
    public String getName() {
        return this.Zh;
    }

    public Color pe() {
        return this.Zi;
    }

    public Color pf() {
        return this.Zj;
    }

    public Color pg() {
        return this.Zk;
    }

    public Color ph() {
        return new Color(Math.round((float)(this.Zi.getRed() + this.Zk.getRed()) * 0.5f), Math.round((float)(this.Zi.getGreen() + this.Zk.getGreen()) * 0.5f), Math.round((float)(this.Zi.getBlue() + this.Zk.getBlue()) * 0.5f));
    }

    public static ClientTheme cp(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        for (ClientTheme clientTheme : ClientTheme.values()) {
            if (!clientTheme.Zh.equalsIgnoreCase(string.trim())) continue;
            return clientTheme;
        }
        return null;
    }

    public Color pi() {
        Color color = new Color(220, 220, 220);
        Color color2 = ColorUtils.mix(color, this.Zi, 0.14f);
        return ColorUtils.mix(color2, this.Zj, 0.06f);
    }

    public void pj() {
        ColorUtils.setPrimaryColor(this.Zi);
        ColorUtils.setSecondaryColor(this.ph());
        ColorUtils.setBackgroundColor(this.pi());
        ColorUtils.setTextColor(this.Zk);
    }

    @Deprecated
    public void pk() {
        this.pj();
    }

    @Deprecated
    public void pl() {
        this.pj();
    }

}

