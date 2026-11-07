package ru.method.celebression.screen;

import java.awt.Color;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.modules.impl.player.CommandDelayModule;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.CobwebUiShaderRenderer;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class CommandDelayEditorScreen
extends class_437
implements MinecraftClientAccess {
    private static final int Mf = 1;
    private static final int Mg = 70;
    private static final int Mh = 30;
    private final CommandDelayModule Mi;
    private final class_437 Mj;
    private final Animation Mk = new Animation(420L, 1.0, true, Easing.aiZ);
    private final EditorState Ml = new EditorState("\u041a\u043e\u043c\u0430\u043d\u0434\u0430", "/spawn", 70);
    private final EditorState Mm = new EditorState("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0430\u0445", "60", 30);
    private int Mn = -1;
    private float scroll;

    public CommandDelayEditorScreen(CommandDelayModule commandDelayModule, class_437 class_4372) {
        super((class_2561)class_2561.method_43470((String)"CMDDelay"));
        this.Mi = commandDelayModule;
        this.Mj = class_4372;
    }

    protected void method_25426() {
        this.kO();
        this.Ml.lc();
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        this.method_25420(class_3322, n, n2, f);
        DelayEntry delayEntry = this.kQ();
        this.Mk.i(true);
        float f2 = class_3532.method_15363((float)this.Mk.rk(), (float)0.0f, (float)1.0f);
        float f3 = class_3532.method_15363((float)((f2 - 0.18f) / 0.82f), (float)0.0f, (float)1.0f);
        int n3 = Math.round(255.0f * f2);
        int n4 = Math.round(255.0f * f3);
        float f4 = class_3532.method_16439((float)f2, (float)0.86f, (float)1.0f);
        float f5 = (float)delayEntry.Mq + (float)delayEntry.Ms / 2.0f;
        float f6 = (float)delayEntry.Mr + (float)delayEntry.Mt / 2.0f;
        Render2D.drawRoundedRect(class_3322.method_51448(), 0.0f, 0.0f, delayEntry.Mo, delayEntry.Mp, 0.0f, new Color(0, 0, 0, Math.round(52.0f * f2)));
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(f5, f6 + (1.0f - f2) * 24.0f, 0.0f);
        class_3322.method_51448().method_22905(f4, f4, 1.0f);
        class_3322.method_51448().method_46416(-f5, -f6, 0.0f);
        Render2D.drawBlurredRect(class_3322.method_51448(), delayEntry.Mq, delayEntry.Mr, delayEntry.Ms, delayEntry.Mt, 18.0f, 22.0f, new Color(15, 18, 24, Math.round(150.0f * f2)));
        Render2D.drawRoundedRect(class_3322.method_51448(), delayEntry.Mq, delayEntry.Mr, delayEntry.Ms, delayEntry.Mt, 18.0f, new Color(20, 23, 30, n3));
        this.a(class_3322, delayEntry.Mq, delayEntry.Mr, delayEntry.Ms, delayEntry.Mt, 18.0f, 0.34f * f2);
        Render2D.drawRoundedRect(class_3322.method_51448(), delayEntry.Mq, delayEntry.Mr, delayEntry.Ms, delayEntry.Mt, 18.0f, new Color(12, 14, 20, Math.round(166.0f * f2)));
        Render2D.drawBorder(class_3322.method_51448(), delayEntry.Mq, delayEntry.Mr, delayEntry.Ms, delayEntry.Mt, 18.0f, 0.75f, new ColorRGBA(ColorUtils.alpha(Color.WHITE, Math.round(38.0f * f2))));
        float f7 = (1.0f - f3) * 10.0f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(16.0f), "Command Delay", (float)delayEntry.Mq + 18.0f, (float)delayEntry.Mr + 13.0f + f7, CommandDelayEditorScreen.aj(n4));
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.4f), "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0432\u0432\u043e\u0434 \u0443\u043a\u0430\u0437\u0430\u043d\u043d\u044b\u0445 \u043a\u043e\u043c\u0430\u043d\u0434 \u0441 \u043f\u0435\u0440\u0438\u043e\u0434\u0438\u0447\u043d\u043e\u0439 \u0446\u0438\u043a\u043b\u0438\u0447\u043d\u043e\u0441\u0442\u044c\u044e.", (float)delayEntry.Mq + 18.0f, (float)delayEntry.Mr + 34.0f + f7, CommandDelayEditorScreen.aj(Math.round(170.0f * f3)));
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(0.0f, f7 * 0.65f, 0.0f);
        this.a(class_3322, delayEntry, n, n2);
        this.b(class_3322, delayEntry, n, n2);
        class_3322.method_51448().method_22909();
        class_3322.method_51448().method_22909();
    }

    private void a(class_332 class_3322, DelayEntry delayEntry, int n, int n2) {
        int n3 = delayEntry.Mu;
        int n4 = delayEntry.Mv;
        int n5 = delayEntry.Mw;
        int n6 = delayEntry.Mx;
        Render2D.drawRoundedRect(class_3322.method_51448(), n3, n4, n5, n6, 12.0f, new Color(255, 255, 255, 18));
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(8.7f), "\u041a\u043e\u043c\u0430\u043d\u0434\u044b", (float)n3 + 10.0f, (float)n4 + 9.0f, CommandDelayEditorScreen.aj(235));
        if (this.Mi.gw().isEmpty()) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.2f), "\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0441\u0442", (float)n3 + 10.0f, (float)n4 + 32.0f, CommandDelayEditorScreen.aj(135));
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(6.8f), "\u0414\u043e\u0431\u0430\u0432\u044c \u043a\u043e\u043c\u0430\u043d\u0434\u0443 \u0441\u043f\u0440\u0430\u0432\u0430", (float)n3 + 10.0f, (float)n4 + 44.0f, CommandDelayEditorScreen.aj(105));
            return;
        }
        int n7 = n4 + 28;
        int n8 = n6 - 34;
        int n9 = 31;
        int n10 = Math.max(0, this.Mi.gw().size() * n9 - n8);
        this.scroll = class_3532.method_15363((float)this.scroll, (float)0.0f, (float)n10);
        Render2D.startScissor(class_3322, n3, n7, n5, n8);
        int n11 = n7 - Math.round(this.scroll);
        for (int i = 0; i < this.Mi.gw().size(); ++i) {
            CommandDelayModule.CommandDelayRule commandDelayRule = this.Mi.gw().get(i);
            boolean bl = i == this.Mn;
            boolean bl2 = MathUtils.c(n3 + 6, n11, n5 - 12, n9 - 5, n, n2);
            Color color = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 96) : (bl2 ? new Color(255, 255, 255, 32) : new Color(255, 255, 255, 12));
            Render2D.drawRoundedRect(class_3322.method_51448(), n3 + 6, n11, n5 - 12, n9 - 5, 8.0f, color);
            Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.5f), this.b("/" + commandDelayRule.t(), n5 - 28, 7.5f), (float)n3 + 12.0f, (float)n11 + 4.0f, CommandDelayEditorScreen.aj(235));
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.6f), commandDelayRule.gB() + " \u0441\u0435\u043a.", (float)n3 + 12.0f, (float)n11 + 15.0f, CommandDelayEditorScreen.aj(150));
            n11 += n9;
        }
        Render2D.stopScissor(class_3322);
    }

    private void b(class_332 class_3322, DelayEntry delayEntry, int n, int n2) {
        int n3 = delayEntry.My;
        int n4 = delayEntry.Mv;
        int n5 = delayEntry.Mz;
        int n6 = delayEntry.Mx;
        String string = this.Mn >= 0 ? "\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435" : "\u041d\u043e\u0432\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430";
        Render2D.drawRoundedRect(class_3322.method_51448(), n3, n4, n5, n6, 12.0f, new Color(255, 255, 255, 16));
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(9.4f), string, (float)n3 + 10.0f, (float)n4 + 10.0f, CommandDelayEditorScreen.aj(238));
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.8f), "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0432\u0432\u043e\u0434 \u0443\u043a\u0430\u0437\u0430\u043d\u043d\u044b\u0445 \u043a\u043e\u043c\u0430\u043d\u0434 \u0441 \u043f\u0435\u0440\u0438\u043e\u0434\u0438\u0447\u043d\u043e\u0439 \u0446\u0438\u043a\u043b\u0438\u0447\u043d\u043e\u0441\u0442\u044c\u044e.", (float)n3 + 10.0f, (float)n4 + 37.0f, CommandDelayEditorScreen.aj(116));
        this.Ml.a(class_3322, n3 + 10, n4 + 58, Math.max(120, n5 - 20), n, n2);
        this.Mm.a(class_3322, n3 + 10, n4 + 112, Math.min(130, Math.max(90, n5 - 20)), n, n2);
        int n7 = n4 + n6 - 38;
        int n8 = Math.min(92, Math.max(76, (n5 - 38) / 3));
        int n9 = Math.min(78, Math.max(62, (n5 - 38) / 3));
        int n10 = Math.min(78, Math.max(62, (n5 - 38) / 3));
        this.a(class_3322, n3 + 10, n7, n8, 23, this.Mn >= 0 ? "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c" : "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", n, n2, ColorUtils.getPrimaryColor());
        this.a(class_3322, n3 + 18 + n8, n7, n9, 23, "\u041d\u043e\u0432\u0430\u044f", n, n2, new Color(91, 126, 188));
        if (this.Mn >= 0) {
            this.a(class_3322, n3 + 26 + n8 + n9, n7, n10, 23, "\u0423\u0434\u0430\u043b\u0438\u0442\u044c", n, n2, new Color(225, 72, 72));
        }
    }

    private void a(class_332 class_3322, int n, int n2, int n3, int n4, String string, int n5, int n6, Color color) {
        boolean bl = MathUtils.c(n, n2, n3, n4, n5, n6);
        Render2D.drawRoundedRect(class_3322.method_51448(), n, n2, n3, n4, 8.0f, ColorUtils.alpha(color, bl ? 142 : 98));
        Render2D.drawBorder(class_3322.method_51448(), n, n2, n3, n4, 8.0f, 0.55f, new ColorRGBA(ColorUtils.alpha(Color.WHITE, bl ? 36 : 20)));
        float f = Fonts.ROUNDBOLD.getWidth(string, 7.25f);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(7.25f), string, (float)n + ((float)n3 - f) / 2.0f, (float)n2 + 6.8f, CommandDelayEditorScreen.aj(248));
    }

    private void a(class_332 class_3322, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = Math.min(f5, Math.min(f3, f4) * 0.5f);
        Color color = ColorUtils.getPrimaryColor();
        if (f6 <= 0.0f) {
            return;
        }
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f + f7, f2, f3 - f7 * 2.0f, f7, color, f6);
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f, f2 + f7, f3, f4 - f7 * 2.0f, color, f6);
        CobwebUiShaderRenderer.render(class_3322.method_51448(), f + f7, f2 + f4 - f7, f3 - f7 * 2.0f, f7, color, f6);
    }

    public boolean method_25402(double d, double d2, int n) {
        if (n == 0) {
            int n2;
            DelayEntry delayEntry = this.kQ();
            if (this.Ml.e(d, d2) || this.Mm.e(d, d2)) {
                return true;
            }
            int n3 = 31;
            int n4 = delayEntry.Mv + 28 - Math.round(this.scroll);
            for (n2 = 0; n2 < this.Mi.gw().size(); ++n2) {
                if (MathUtils.c(delayEntry.Mu + 6, n4, delayEntry.Mw - 12, n3 - 5, (float)d, (float)d2)) {
                    this.Mn = n2;
                    this.kO();
                    return true;
                }
                n4 += n3;
            }
            n2 = delayEntry.Mv + delayEntry.Mx - 38;
            int n5 = Math.min(92, Math.max(76, (delayEntry.Mz - 38) / 3));
            int n6 = Math.min(78, Math.max(62, (delayEntry.Mz - 38) / 3));
            int n7 = Math.min(78, Math.max(62, (delayEntry.Mz - 38) / 3));
            if (MathUtils.c(delayEntry.My + 10, n2, n5, 23.0f, (float)d, (float)d2)) {
                this.saveCurrent();
                return true;
            }
            if (MathUtils.c(delayEntry.My + 18 + n5, n2, n6, 23.0f, (float)d, (float)d2)) {
                this.kP();
                return true;
            }
            if (this.Mn >= 0 && MathUtils.c(delayEntry.My + 26 + n5 + n6, n2, n7, 23.0f, (float)d, (float)d2)) {
                this.Mi.s(this.Mn);
                this.ir();
                return true;
            }
        }
        this.Ml.lb();
        this.Mm.lb();
        return super.method_25402(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        DelayEntry delayEntry = this.kQ();
        if (MathUtils.c(delayEntry.Mu, delayEntry.Mv, delayEntry.Mw, delayEntry.Mx, (float)d, (float)d2)) {
            this.scroll -= (float)d4 * 14.0f;
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
    }

    public boolean method_25404(int n, int n2, int n3) {
        if ((n == 257 || n == 335) && (this.Ml.isFocused() || this.Mm.isFocused())) {
            this.saveCurrent();
            return true;
        }
        if (this.Ml.ak(n) || this.Mm.ak(n)) {
            return true;
        }
        if (n == 256) {
            this.method_25419();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.Ml.b(c) || this.Mm.b(c)) {
            return true;
        }
        return super.method_25400(c, n);
    }

    public void method_25419() {
        this.field_22787.method_1507(this.Mj);
    }

    private void kO() {
        if (this.Mn < 0 || this.Mn >= this.Mi.gw().size()) {
            this.ir();
            return;
        }
        CommandDelayModule.CommandDelayRule commandDelayRule = this.Mi.gw().get(this.Mn);
        this.Ml.cb("/" + commandDelayRule.t());
        this.Mm.cb(String.valueOf(commandDelayRule.gB()));
    }

    private void ir() {
        this.Mn = -1;
        this.Ml.cb("");
        this.Mm.cb("60");
        this.Ml.lc();
    }

    private void kP() {
        if (this.Mn < 0 && !this.Ml.ld().trim().isEmpty()) {
            this.saveCurrent();
        } else {
            this.ir();
        }
    }

    private void saveCurrent() {
        String string = this.Ml.ld();
        int n = this.ca(this.Mm.ld());
        if (string == null || string.trim().isEmpty()) {
            return;
        }
        if (this.Mn >= 0 && this.Mn < this.Mi.gw().size()) {
            this.Mi.a(this.Mn, string, n);
            this.kO();
        } else {
            this.Mi.c(string, n);
            this.ir();
        }
    }

    private int ca(String string) {
        try {
            String string2;
            String string3 = string2 = string == null ? "" : string.trim().replaceAll("[^0-9]", "");
            if (string2.isEmpty()) {
                return 60;
            }
            long l = Long.parseUnsignedLong(string2.length() > 18 ? string2.substring(0, 18) : string2);
            return (int)Math.min(Integer.MAX_VALUE, Math.max(1L, l));
        }
        catch (Exception exception) {
            return 60;
        }
    }

    private String b(String string, float f, float f2) {
        if (Fonts.SEMIBOLD.getWidth(string, f2) <= f) {
            return string;
        }
        String string2 = "...";
        String string3 = string;
        while (!string3.isEmpty() && Fonts.SEMIBOLD.getWidth(string3 + string2, f2) > f) {
            string3 = string3.substring(0, string3.length() - 1);
        }
        return string3 + string2;
    }

    static Color aj(int n) {
        return new Color(236, 239, 246, Math.max(0, Math.min(255, n)));
    }

    private DelayEntry kQ() {
        int n = this.field_22789;
        int n2 = this.field_22790;
        if ((n <= 1 || n2 <= 1) && aiG != null && aiG.method_22683() != null) {
            n = aiG.method_22683().method_4486();
            n2 = aiG.method_22683().method_4502();
        }
        n = Math.max(1, n);
        n2 = Math.max(1, n2);
        int n3 = Math.max(120, n - 16);
        int n4 = Math.max(120, n2 - 16);
        int n5 = Math.min(650, Math.max(120, Math.min(n3, Math.round((float)n * 0.88f))));
        int n6 = Math.min(405, Math.max(120, Math.min(n4, Math.round((float)n2 * 0.82f))));
        int n7 = Math.round((float)(n - n5) * 0.5f);
        int n8 = Math.round((float)(n2 - n6) * 0.5f);
        if (n - n5 >= 16) {
            n7 = class_3532.method_15340((int)n7, (int)8, (int)(n - n5 - 8));
        }
        if (n2 - n6 >= 16) {
            n8 = class_3532.method_15340((int)n8, (int)8, (int)(n2 - n6 - 8));
        }
        int n9 = n6 < 210 ? 42 : 56;
        int n10 = n8 + n9;
        int n11 = Math.max(80, n6 - n9 - 16);
        int n12 = Math.min(230, Math.max(78, (int)((float)n5 * 0.34f)));
        int n13 = n7 + 16 + n12 + 12;
        int n14 = Math.max(70, n5 - (n13 - n7) - 16);
        return new DelayEntry(n, n2, n7, n8, n5, n6, n7 + 16, n10, n12, n11, n13, n14);
    }

    static class EditorState
    implements MinecraftClientAccess {
        private static final float MA = 6.2f;
        private static final float MB = 14.0f;
        private static final float MC = 2.5f;
        private final String MD;
        private final String ME;
        private final int MF;
        private final Animation MG = new Animation(180L, 1.0, false, Easing.aiR);
        private float x;
        private float y;
        private float width;
        private float scrollOffset;
        private String text = "";
        private boolean focused;
        private boolean selected;

        EditorState(String string, String string2, int n) {
            this.MD = string;
            this.ME = string2;
            this.MF = n;
        }

        void a(class_332 class_3322, float f, float f2, float f3, int n, int n2) {
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.MG.i(this.focused);
            float f4 = f2 + 6.2f + 4.0f;
            float f5 = Math.max(1.0f, f3 - 8.0f);
            String string = this.text == null ? "" : this.text;
            boolean bl = !string.isEmpty();
            boolean bl2 = !bl && !this.focused;
            String string2 = bl2 ? this.ME : string;
            float f6 = Fonts.REGULAR.getWidth(string2, 7.5f);
            float f7 = bl ? Fonts.REGULAR.getWidth(string, 7.5f) : 0.0f;
            this.scrollOffset = bl && f7 > f5 ? Math.max(0.0f, f7 - f5) : 0.0f;
            Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(6.2f), this.MD, f, f2, new Color(230, 230, 230, 235));
            Render2D.drawRoundedRect(class_3322.method_51448(), f, f4, f3, 14.0f, 2.5f, new Color(23, 23, 23, 100));
            Render2D.drawRoundedRect(class_3322.method_51448(), f, f4, f3, 14.0f, 2.5f, ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), this.focused ? 200 : 130));
            float f8 = f + 4.0f;
            float f9 = f4 + 3.1f;
            Render2D.startScissor(class_3322, f + 1.0f, f4, f3 - 2.0f, 14.0f);
            if (this.selected && bl) {
                Render2D.drawRoundedRect(class_3322.method_51448(), f8, f9, Math.max(1.0f, f7), Math.max(1.0f, Fonts.REGULAR.getHeight(7.5f)), 0.0f, new Color(0, 0, 255, 150));
            }
            if (bl2) {
                Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.5f), this.ME, f8, f9, CommandDelayEditorScreen.aj((int)(200.0f * this.MG.rm())));
            } else if (bl) {
                Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(7.5f), string, f8 - this.scrollOffset, f9, CommandDelayEditorScreen.aj(255));
            }
            if (this.focused) {
                Color color = ColorUtils.pulse(CommandDelayEditorScreen.aj(255), 15L);
                float f10 = f8 - this.scrollOffset + (bl ? f7 : f6);
                Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), "|", f10, f9 - 0.25f, ColorUtils.alpha(color, (int)((float)color.getAlpha() * this.MG.rk())));
            }
            Render2D.stopScissor(class_3322);
        }

        boolean e(double d, double d2) {
            boolean bl;
            this.focused = bl = MathUtils.c(this.x, this.y + 6.2f + 4.0f, this.width, 14.0f, (float)d, (float)d2);
            this.selected = false;
            return bl;
        }

        boolean ak(int n) {
            if (!this.focused) {
                return false;
            }
            boolean bl = true;
            switch (n) {
                case 259: {
                    if (this.selected) {
                        this.text = "";
                        this.selected = false;
                        break;
                    }
                    if (this.text.isEmpty()) break;
                    this.text = this.text.substring(0, this.text.length() - 1);
                    break;
                }
                case 256: 
                case 257: {
                    this.lb();
                    break;
                }
                case 261: {
                    this.text = "";
                    this.selected = false;
                    break;
                }
                case 67: {
                    if (!class_437.method_25441() || !this.selected || this.text.isEmpty()) break;
                    GLFW.glfwSetClipboardString((long)aiG.method_22683().method_4490(), (CharSequence)this.text);
                    this.selected = false;
                    break;
                }
                case 86: {
                    String string = GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490());
                    if (!class_437.method_25441() || string == null) break;
                    if (this.selected) {
                        this.text = "";
                    }
                    this.text = this.text + string;
                    if (this.text.length() > this.MF) {
                        this.text = this.text.substring(0, this.MF);
                    }
                    this.selected = false;
                    break;
                }
                case 65: {
                    if (!class_437.method_25441() || this.text.isEmpty()) break;
                    this.selected = true;
                    break;
                }
                default: {
                    bl = false;
                }
            }
            return bl;
        }

        boolean b(char c) {
            if (!this.focused) {
                return false;
            }
            if (this.selected) {
                this.text = "";
                this.selected = false;
            }
            if (this.text.length() >= this.MF) {
                return true;
            }
            this.text = this.text + c;
            return true;
        }

        void lb() {
            this.focused = false;
            this.selected = false;
        }

        void lc() {
            this.focused = true;
            this.selected = false;
        }

        boolean isFocused() {
            return this.focused;
        }

        String ld() {
            return this.text == null ? "" : this.text;
        }

        void cb(String string) {
            String string2 = this.text = string == null ? "" : string;
            if (this.text.length() > this.MF) {
                this.text = this.text.substring(0, this.MF);
            }
            this.selected = false;
            this.scrollOffset = 0.0f;
        }
    }

    static final class DelayEntry {
        final int Mo;
        final int Mp;
        final int Mq;
        final int Mr;
        final int Ms;
        final int Mt;
        final int Mu;
        final int Mv;
        final int Mw;
        final int Mx;
        final int My;
        final int Mz;

        DelayEntry(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
            this.Mo = n;
            this.Mp = n2;
            this.Mq = n3;
            this.Mr = n4;
            this.Ms = n5;
            this.Mt = n6;
            this.Mu = n7;
            this.Mv = n8;
            this.Mw = n9;
            this.Mx = n10;
            this.My = n11;
            this.Mz = n12;
        }

        @Override
        public final String toString() {
            return "DelayEntry[" + "screenW=" + this.Mo + ", " + "screenH=" + this.Mp + ", " + "x=" + this.Mq + ", " + "y=" + this.Mr + ", " + "w=" + this.Ms + ", " + "h=" + this.Mt + ", " + "listX=" + this.Mu + ", " + "listY=" + this.Mv + ", " + "listW=" + this.Mw + ", " + "listH=" + this.Mx + ", " + "editorX=" + this.My + ", " + "editorW=" + this.Mz + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Mo, this.Mp, this.Mq, this.Mr, this.Ms, this.Mt, this.Mu, this.Mv, this.Mw, this.Mx, this.My, this.Mz);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof DelayEntry other)) return false;
            return java.util.Objects.deepEquals(this.Mo, other.Mo)
                && java.util.Objects.deepEquals(this.Mp, other.Mp)
                && java.util.Objects.deepEquals(this.Mq, other.Mq)
                && java.util.Objects.deepEquals(this.Mr, other.Mr)
                && java.util.Objects.deepEquals(this.Ms, other.Ms)
                && java.util.Objects.deepEquals(this.Mt, other.Mt)
                && java.util.Objects.deepEquals(this.Mu, other.Mu)
                && java.util.Objects.deepEquals(this.Mv, other.Mv)
                && java.util.Objects.deepEquals(this.Mw, other.Mw)
                && java.util.Objects.deepEquals(this.Mx, other.Mx)
                && java.util.Objects.deepEquals(this.My, other.My)
                && java.util.Objects.deepEquals(this.Mz, other.Mz);
        }

        public int kR() {
            return this.Mo;
        }

        public int kS() {
            return this.Mp;
        }

        public int x() {
            return this.Mq;
        }

        public int y() {
            return this.Mr;
        }

        public int kT() {
            return this.Ms;
        }

        public int kU() {
            return this.Mt;
        }

        public int kV() {
            return this.Mu;
        }

        public int kW() {
            return this.Mv;
        }

        public int kX() {
            return this.Mw;
        }

        public int kY() {
            return this.Mx;
        }

        public int kZ() {
            return this.My;
        }

        public int la() {
            return this.Mz;
        }
    }
}

