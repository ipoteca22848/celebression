package ru.method.celebression.screen;

import java.awt.Color;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_2561;
import net.minecraft.class_2926;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_412;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_639;
import net.minecraft.class_641;
import net.minecraft.class_642;
import net.minecraft.class_644;
import ru.method.celebression.Celebression;
import ru.method.celebression.screen.AccountManagerScreen;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.screen.DeleteServerDialog;
import ru.method.celebression.screen.DirectConnectDialog;
import ru.method.celebression.screen.EditServerDialog;
import ru.method.celebression.service.IasIntegration;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.manager.UserSyncManager;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class MultiplayerScreen
extends class_437
implements MinecraftClientAccess {
    private static final float aaT = 1000.0f;
    private static final float aaU = 168.0f;
    private static final float aaV = 12.0f;
    private static final float aaW = 22.0f;
    private static final float aaX = 68.0f;
    private static final float aaY = 64.0f;
    private static final float aaZ = 76.0f;
    private static final Color aba = MenuChrome.TEXT;
    private static final Color abb = MenuChrome.TEXT2;
    private static final Color abc = MenuChrome.MUTED;
    private static final Color abd = MenuChrome.MUTED2;
    private static final Color abe = MenuChrome.MUTED3;
    private static final Color abf = MenuChrome.MUTED4;
    private static final Color abg = MenuChrome.INK;
    private static final Color abh = MenuChrome.DANGER;
    private static final Color abi = new Color(224, 110, 110, 140);
    private static final AtomicInteger abj = new AtomicInteger();
    private static final ExecutorService abk = Executors.newFixedThreadPool(5, runnable -> {
        Thread thread = new Thread(runnable, "Celebression Server Pinger #" + abj.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    });
    private final class_437 abl;
    private class_641 abm;
    private final class_644 abn = new class_644();
    private final Map<String, class_1043> abo = new HashMap<String, class_1043>();
    private final Set<String> abp = new HashSet<String>();
    private final Set<class_642> abq = Collections.newSetFromMap(new IdentityHashMap());
    private final Map<class_642, Long> abr = new ConcurrentHashMap<class_642, Long>();
    private static final Map<String, ServerSelection> abs = new ConcurrentHashMap<String, ServerSelection>();
    private static final Map<String, String> abt = new ConcurrentHashMap<String, String>();
    private float uiScale = 1.0f;
    private int vw = 1;
    private int vh = 1;
    private static final String[][] abu = new String[0][];
    private final List<class_642> abv = new ArrayList<class_642>();
    private final List<class_642> abw = new ArrayList<class_642>();
    private final List<class_642> abx = new ArrayList<class_642>();
    private int aby = -1;
    private String Li = "";
    private boolean Lj;
    private float scroll;
    private float Lm;
    private long Lo;
    private int abz = -1;
    private class_642 abA;
    private final Animation abB = MenuChrome.anim();
    private final Animation abC = MenuChrome.anim();
    private final Animation abD = MenuChrome.anim();
    private final Animation abE = MenuChrome.anim();
    private final Animation abF = MenuChrome.anim();
    private final Animation abG = MenuChrome.anim();
    private final Animation abH = MenuChrome.anim();
    private final Animation abI = MenuChrome.anim();
    private final Animation abJ = MenuChrome.anim();
    private final Animation abK = MenuChrome.anim();
    private final Animation abL = MenuChrome.anim();
    private final float[] abM = new float[4];
    private final float[] abN = new float[4];
    private final float[] abO = new float[4];
    private final float[] abP = new float[4];
    private final float[] abQ = new float[4];
    private final float[] abR = new float[4];
    private final float[] abS = new float[4];
    private final float[] abT = new float[4];
    private final float[] abU = new float[4];
    private final float[] abV = new float[4];
    private final float[] abW = new float[4];
    private final List<float[]> abX = new ArrayList<float[]>();
    private final Map<class_642, ServerAction> abY = new IdentityHashMap<class_642, ServerAction>();
    private final Map<Integer, Animation> abZ = new HashMap<Integer, Animation>();
    private final Map<class_642, ServerStatus> aca = new IdentityHashMap<class_642, ServerStatus>();
    private final Map<class_642, Integer> acb = new IdentityHashMap<class_642, Integer>();
    private boolean acc;
    private long acd;
    private float parX;
    private float parY;
    private long Rf;
    private long Ln;
    private final Map<String, Long> ace = new HashMap<String, Long>();
    private final Animation acf = MenuChrome.anim();
    private static final Pattern acg = Pattern.compile("[\u00a7&][0-9A-Za-z]");
    private static final Pattern ach = Pattern.compile("(?i)\\s*[\\(\\[](?=[^\\)\\]]*(?:erid|\\d+\\+))[^\\)\\]]*[\\)\\]]");
    private static final Pattern aci = Pattern.compile("(?i)\\berid[:=]?\\S*");
    private static final Pattern acj = Pattern.compile("1\\.(\\d{1,2})(?:\\.\\d{1,2})?");

    private static Color b(Color color, float f) {
        if (f >= 0.999f) {
            return color;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * class_3532.method_15363((float)f, (float)0.0f, (float)1.0f)));
    }

    public MultiplayerScreen(class_437 class_4372) {
        super((class_2561)class_2561.method_43470((String)"\u0421\u0435\u0442\u0435\u0432\u0430\u044f \u0438\u0433\u0440\u0430"));
        this.abl = class_4372;
    }

    protected void method_25426() {
        if (this.Rf == 0L) {
            this.Rf = System.currentTimeMillis();
        }
        this.abm = new class_641(aiG);
        this.abm.method_2981();
        this.pE();
        this.refreshAll();
        this.pD();
    }

    private void pD() {
        this.d(List.of());
    }

    private void d(List<UserSyncManager.SyncPayload> list) {
        this.abw.clear();
        this.pE();
    }

    private boolean a(class_642 class_6422) {
        return class_6422 != null && this.abw.contains(class_6422);
    }

    private void cv(String string) {
        this.ace.put(string, System.currentTimeMillis());
    }

    private float cw(String string) {
        Long l = this.ace.get(string);
        if (l == null) {
            return 0.0f;
        }
        float f = (float)(System.currentTimeMillis() - l) / 150.0f;
        if (f >= 1.0f) {
            this.ace.remove(string);
            return 0.0f;
        }
        return 1.0f - f;
    }

    private void pE() {
        this.abx.clear();
        HashSet<String> hashSet = new HashSet<String>();
        for (class_642 object : this.abw) {
            this.abx.add(object);
            hashSet.add(object.field_3761 == null ? "" : object.field_3761.toLowerCase(Locale.ROOT));
        }
        for (int i = 0; i < this.abm.method_2984(); ++i) {
            class_642 class_6422 = this.abm.method_2982(i);
            if (class_6422 == null || hashSet.contains(class_6422.field_3761 == null ? "" : class_6422.field_3761.toLowerCase(Locale.ROOT))) continue;
            this.abx.add(class_6422);
        }
        this.abv.clear();
        String string = this.Li.trim().toLowerCase(Locale.ROOT);
        for (class_642 class_6423 : this.abx) {
            if (!this.a(class_6423) && !string.isEmpty()) {
                String string2 = class_6423.field_3752 == null ? "" : class_6423.field_3752.toLowerCase(Locale.ROOT);
                String string3 = class_6423.field_3761 == null ? "" : class_6423.field_3761.toLowerCase(Locale.ROOT);
                String string4 = abt.getOrDefault(MultiplayerScreen.d(class_6423), "");
                if (!string2.contains(string) && !string3.contains(string) && !string4.contains(string)) continue;
            }
            this.abv.add(class_6423);
        }
        if (this.aby >= this.abv.size()) {
            this.aby = this.abv.size() - 1;
        }
        this.acb.clear();
        for (int index = 0; index < this.abv.size(); ++index) {
            this.acb.put(this.abv.get(index), index);
        }
    }

    private void refreshAll() {
        for (class_642 class_6422 : this.abx) {
            if (class_6422 == null) continue;
            this.b(class_6422);
        }
    }

    private void b(class_642 class_6422) {
        String string = MultiplayerScreen.d(class_6422);
        if (!this.abq.add(class_6422)) {
            return;
        }
        class_6422.method_55824(class_642.class_9083.field_47881);
        this.abr.put(class_6422, System.currentTimeMillis());
        ServerSelection serverSelection = abs.get(string);
        if (serverSelection != null && (class_6422.field_3757 == null || class_6422.field_3757.getString().isBlank())) {
            class_6422.field_3757 = serverSelection.pR();
            class_6422.field_3753 = serverSelection.pS();
            class_6422.field_41861 = serverSelection.pT();
            class_6422.field_3758 = serverSelection.pU();
        }
        abk.submit(() -> {
            if (!abt.containsKey(string) && class_6422.field_3761 != null && !class_6422.field_3761.isBlank()) {
                try {
                    String string2 = class_639.method_2950((String)class_6422.field_3761).method_2952();
                    abt.put(string, InetAddress.getByName(string2).getHostAddress());
                }
                catch (Throwable throwable) {
                }
            }
            try {
                this.abn.method_3003(class_6422, () -> aiG.execute(() -> {
                    this.acc = true;
                    this.abp.remove(string);
                    if (class_6422.field_3757 != null && !class_6422.field_3757.getString().isBlank()) {
                        abs.put(string, new ServerSelection(class_6422.field_3757, class_6422.field_3753, class_6422.field_41861, class_6422.field_3758));
                    }
                }), () -> aiG.execute(() -> {
                    class_6422.method_55824(class_642.class_9083.field_47884);
                    this.abr.remove(class_6422);
                    abs.put(string, new ServerSelection(class_6422.field_3757, class_6422.field_3753, class_6422.field_41861, class_6422.field_3758));
                }));
            }
            catch (Throwable throwable) {
                this.c(class_6422);
            }
            finally {
                this.abq.remove(class_6422);
            }
        });
    }

    private void c(class_642 class_6422) {
        class_6422.method_55824(class_642.class_9083.field_47882);
        this.abr.remove(class_6422);
        if (class_6422.field_3757 == null || class_6422.field_3757.getString().isBlank()) {
            class_6422.field_3757 = class_2561.method_43470((String)"\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f");
        }
    }

    private void pF() {
        if (this.abr.isEmpty()) {
            return;
        }
        long l = System.currentTimeMillis();
        Iterator<Map.Entry<class_642, Long>> iterator = this.abr.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<class_642, Long> entry = iterator.next();
            class_642 class_6422 = entry.getKey();
            if (class_6422.method_55825() == class_642.class_9083.field_47884) {
                iterator.remove();
                continue;
            }
            if (class_6422.method_55825() != class_642.class_9083.field_47881 || l - entry.getValue() <= 7000L) continue;
            this.c(class_6422);
            iterator.remove();
        }
    }

    private static String d(class_642 class_6422) {
        return (class_6422.field_3752 == null ? "" : class_6422.field_3752) + "|" + (class_6422.field_3761 == null ? "" : class_6422.field_3761);
    }

    private class_1043 e(class_642 class_6422) {
        String string = MultiplayerScreen.d(class_6422);
        class_1043 class_10432 = this.abo.get(string);
        if (class_10432 != null) {
            return class_10432;
        }
        if (!this.abp.add(string)) {
            return null;
        }
        byte[] byArray = class_6422.method_49306();
        if (byArray == null) {
            return null;
        }
        try {
            class_1011 class_10112 = class_1011.method_49277((byte[])byArray);
            class_1043 class_10433 = new class_1043(class_10112);
            class_10433.method_4527(true, false);
            this.abo.put(string, class_10433);
            return class_10433;
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[Multiplayer] bad favicon for {}", (Object)class_6422.field_3761);
            return null;
        }
    }

    private void lY() {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        this.uiScale = Math.max(0.05f, (float)aiG.method_22683().method_4502() / 1080.0f);
        this.vw = Math.max(1, Math.round((float)aiG.method_22683().method_4486() / this.uiScale));
        this.vh = Math.max(1, Math.round((float)aiG.method_22683().method_4502() / this.uiScale));
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        this.lY();
        MenuChrome.tickMusic();
        this.abn.method_3000();
        this.pF();
        if (this.acc && System.currentTimeMillis() - this.acd > 2000L) {
            this.acd = System.currentTimeMillis();
            this.acc = false;
            this.abm.method_2987();
        }
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_22905(this.uiScale, this.uiScale, 1.0f);
        int n3 = Math.round((float)n / this.uiScale);
        int n4 = Math.round((float)n2 / this.uiScale);
        ClickGuiIcons.pixelScale(this.uiScale);
        float f2 = class_3532.method_15363((float)(((float)n3 - (float)this.vw / 2.0f) / ((float)this.vw / 2.0f)), (float)-1.0f, (float)1.0f);
        float f3 = class_3532.method_15363((float)(((float)n4 - (float)this.vh / 2.0f) / ((float)this.vh / 2.0f)), (float)-1.0f, (float)1.0f);
        this.parX += (f2 - this.parX) * 0.08f;
        this.parY += (f3 - this.parY) * 0.08f;
        MenuChrome.drawScreenBackground(class_45872, this.vw, this.vh, this.parX, this.parY);
        this.g(class_45872, n3, n4);
        MenuChrome.drawLogo(class_45872, this.vw);
        MenuChrome.drawClock(class_45872, this.vw);
        this.h(class_45872);
        if (MenuChrome.hasMusic()) {
            MenuChrome.drawMusic(class_45872, this.vw, this.vh, n3, n4, this.abD, this.abP);
        }
        float f4 = class_3532.method_15363((float)((float)(System.currentTimeMillis() - this.Rf) / 240.0f), (float)0.0f, (float)1.0f);
        float f5 = 1.0f - (1.0f - f4) * (1.0f - f4) * (1.0f - f4);
        class_45872.method_22903();
        class_45872.method_46416(0.0f, (1.0f - f5) * 18.0f, 0.0f);
        this.b(class_3322, class_45872, n3, n4);
        class_45872.method_22909();
        class_45872.method_22909();
        ClickGuiIcons.pixelScale(1.0f);
    }

    private void g(class_4587 class_45872, int n, int n2) {
        Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
        String string = "\u041d\u0430\u0437\u0430\u0434";
        float f = 38.0f;
        float f2 = 17.0f;
        float f3 = 12.0f + f2 + 9.0f + instance.getWidth(string) + 15.0f;
        float f4 = 56.0f;
        float f5 = 44.0f;
        boolean bl = MenuChrome.hit(n, n2, f4, f5, f3, f);
        float f6 = MenuChrome.upd(this.abB, bl);
        float f7 = f5 - f6;
        MenuChrome.drawSecondaryMaterial(class_45872, f4, f7, f3, f, 8.0f, f6, false);
        ClickGuiIcons.arrowLeft(class_45872, f4 + 12.0f + f2 / 2.0f, f7 + f / 2.0f, f2, abc);
        Render2D.drawFont(class_45872, instance, string, f4 + 12.0f + f2 + 9.0f, f7 + f / 2.0f - instance.getHeight() / 2.0f, abb);
        MenuChrome.set(this.abN, f4, f5, f3, f);
    }

    private void h(class_4587 class_45872, int n, int n2) {
        String string = MenuChrome.nick();
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        Instance instance2 = Fonts.GOLOS.getFont(11.0f);
        String string2 = "\u0441\u043c\u0435\u043d\u0438\u0442\u044c";
        float f = 34.0f;
        float f2 = 26.0f;
        float f3 = 5.0f + f2 + 10.0f + instance.getWidth(string) + 10.0f + 1.0f + 10.0f + instance2.getWidth(string2) + 7.0f;
        float f4 = 44.0f + Fonts.GOLOS_SEMI.getFont(23.0f).getHeight();
        float f5 = (float)this.vw / 2.0f - f3 / 2.0f;
        float f6 = f4 + 12.0f;
        MenuChrome.drawSecondaryMaterial(class_45872, f5, f6, f3, f, 17.0f, 0.0f, false);
        float f7 = f5 + 5.0f;
        float f8 = f6 + (f - f2) / 2.0f;
        Render2D.drawRoundedVerticalGradient(class_45872, f7, f8, f2, f2, f2 / 2.0f, new Color(42, 56, 48), new Color(16, 22, 19));
        Render2D.drawBorder(class_45872, f7, f8, f2, f2, f2 / 2.0f, 1.0f, 1.4f, MenuChrome.accentA(0.32f));
        Instance instance3 = Fonts.UNBOUNDED.getFont(9.0f);
        String string3 = string.isEmpty() ? "?" : string.substring(0, 1).toUpperCase(Locale.ROOT);
        Render2D.drawFont(class_45872, instance3, string3, f7 + (f2 - instance3.getWidth(string3)) / 2.0f, f8 + (f2 - instance3.getHeight()) / 2.0f, MenuChrome.accent());
        float f9 = f7 + f2 + 10.0f;
        Render2D.drawFont(class_45872, instance, string, f9, f6 + f / 2.0f - instance.getHeight() / 2.0f, aba);
        Render2D.drawRoundedRect(class_45872, f9 += instance.getWidth(string) + 10.0f, f6 + (f - 14.0f) / 2.0f, 1.0f, 14.0f, 0.0f, MenuChrome.white(0.12f));
        float f10 = instance2.getWidth(string2);
        float f11 = f6 + f / 2.0f - instance2.getHeight() / 2.0f - 1.0f;
        boolean bl = MenuChrome.hit(n, n2, f9 += 11.0f, f6, f10, f);
        float f12 = MenuChrome.upd(this.abC, bl);
        Render2D.drawFont(class_45872, instance2, string2, f9, f11, MenuChrome.lerp(abd, MenuChrome.accent(), f12));
        MenuChrome.set(this.abO, f9, f6, f10, f);
    }

    private void h(class_4587 class_45872) {
        // Promotional footer removed.
    }

    private float pG() {
        return (float)this.vw / 2.0f - 500.0f;
    }

    private int pH() {
        float f = (float)this.vh - 44.0f - 110.0f;
        float f2 = f - 168.0f - 68.0f - 76.0f;
        return Math.max(1, (int)Math.floor(f2 / 64.0f));
    }

    private float pI() {
        int n = Math.min(Math.max(this.abv.size(), 1), this.pH());
        return (float)n * 64.0f;
    }

    private float kw() {
        return 68.0f + this.pI() + 76.0f;
    }

    private void b(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = this.pG();
        float f2 = 168.0f;
        float f3 = 1000.0f;
        float f4 = this.kw();
        MenuPanelRenderer.n(class_45872, f, f2, f3, f4);
        this.a(class_45872, f, f2, f3, n, n2);
        float f5 = f2 + 68.0f;
        this.d(class_3322, class_45872, f, f5, f3, this.pI(), n, n2);
        this.b(class_45872, f, f2 + f4 - 76.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 34.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance, "\u0421\u0435\u0442\u0435\u0432\u0430\u044f \u0438\u0433\u0440\u0430", f + 22.0f, f4 - instance.getHeight() / 2.0f, aba);
        float f5 = 36.0f;
        float f6 = f + f3 - 22.0f - f5;
        float f7 = f4 - f5 / 2.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.abF, bl);
        float f9 = this.cw("refresh");
        MenuPanelRenderer.c(class_45872, f6, f7 + f9, f5, f5, f8, false);
        ClickGuiIcons.refresh(class_45872, f6 + f5 / 2.0f, f7 + f9 + f5 / 2.0f, 18.0f - 3.0f * f9, MenuChrome.lerp(abc, MenuChrome.accent(), Math.max(f8, f9)));
        MenuChrome.set(this.abR, f6, f7, f5, f5);
        float f10 = MenuChrome.upd(this.acf, this.Lj);
        float f11 = 320.0f + 24.0f * f10;
        float f12 = 36.0f;
        float f13 = f6 - 12.0f - f11;
        float f14 = f4 - f12 / 2.0f;
        boolean bl2 = MenuChrome.hit(n, n2, f13, f14, f11, f12);
        float f15 = MenuChrome.upd(this.abE, bl2 || this.Lj);
        MenuPanelRenderer.c(class_45872, f13, f14, f11, f12, Math.max(f15, f10), false);
        ClickGuiIcons.search(class_45872, f13 + 13.0f + 7.0f, f14 + f12 / 2.0f, 17.0f, MenuChrome.lerp(abe, MenuChrome.accent(), f10));
        Instance instance2 = Fonts.GOLOS.getFont(12.5f);
        float f16 = f13 + 13.0f + 14.0f + 10.0f;
        if (this.Li.isEmpty() && !this.Lj) {
            Render2D.drawFont(class_45872, instance2, "\u041f\u043e\u0438\u0441\u043a \u043f\u043e \u0441\u0435\u0440\u0432\u0435\u0440\u0430\u043c...", f16, f14 + f12 / 2.0f - instance2.getHeight() / 2.0f, abe);
        } else {
            Render2D.drawFont(class_45872, instance2, this.Li, f16, f14 + f12 / 2.0f - instance2.getHeight() / 2.0f, abb);
            if (this.Lj && System.currentTimeMillis() / 500L % 2L == 0L) {
                Render2D.drawRoundedRect(class_45872, f16 + instance2.getWidth(this.Li) + 1.0f, f14 + f12 / 2.0f - 6.0f, 1.0f, 12.0f, 0.5f, abb);
            }
        }
        MenuChrome.set(this.abQ, f13, f14, f11, f12);
        Render2D.drawRoundedRect(class_45872, f, f2 + 68.0f - 1.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.07f));
    }

    private void d(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        this.abX.clear();
        boolean bl = false;
        for (ServerAction object : this.abY.values()) {
            if (!(object.HW > 0.01f)) continue;
            bl = true;
            break;
        }
        if (this.abv.isEmpty() && !bl) {
            Instance instance = Fonts.GOLOS.getFont(13.0f);
            String string = this.Li.isEmpty() ? "\u0421\u0435\u0440\u0432\u0435\u0440\u043e\u0432 \u043f\u043e\u043a\u0430 \u043d\u0435\u0442" : "\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e";
            Render2D.drawFont(class_45872, instance, string, f + (f3 - instance.getWidth(string)) / 2.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f - 14.0f, abe);
            if (this.Li.isEmpty()) {
                Instance f10 = Fonts.UNBOUNDED.getFont(11.0f);
                String f11 = "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c";
                float i = 43.0f + f10.getWidth(f11) + 18.0f;
                float class_6423 = 44.0f;
                float serverAction = f + (f3 - i) / 2.0f;
                float n3 = f2 + f4 / 2.0f + 6.0f;
                boolean bl3 = MenuChrome.hit(n, n2, serverAction, n3, i, class_6423);
                float f13 = MenuChrome.upd(this.abL, bl3);
                MenuPanelRenderer.c(class_45872, serverAction, n3, i, class_6423, f13, false);
                ClickGuiIcons.plus(class_45872, serverAction + 18.0f + 7.5f, n3 + class_6423 / 2.0f, 18.0f, abc);
                Render2D.drawFont(class_45872, f10, f11, serverAction + 18.0f + 15.0f + 10.0f, n3 + class_6423 / 2.0f - f10.getHeight() / 2.0f, abb);
                MenuChrome.set(this.abM, serverAction, n3, i, class_6423);
            } else {
                MenuChrome.set(this.abM, 0.0f, 0.0f, 0.0f, 0.0f);
            }
            return;
        }
        MenuChrome.set(this.abM, 0.0f, 0.0f, 0.0f, 0.0f);
        long l = System.currentTimeMillis();
        float f5 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        float f6 = Math.max(0.0f, (float)this.abv.size() * 64.0f - f4);
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f6);
        this.scroll += (this.Lm - this.scroll) * (1.0f - (float)Math.exp(-13.0f * f5));
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        for (int i = 0; i < this.abv.size(); ++i) {
            this.abX.add(new float[4]);
        }
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        for (class_642 class_6423 : this.abx) {
            float f7;
            float f8;
            if (class_6423 == null) continue;
            ServerAction serverAction = this.abY.computeIfAbsent(class_6423, class_6422 -> new ServerAction());
            int n3 = this.acb.getOrDefault(class_6423, -1);
            boolean bl2 = n3 >= 0;
            float f9 = f8 = bl2 ? (float)n3 * 64.0f : serverAction.y;
            if (!serverAction.aco) {
                serverAction.y = f8;
                serverAction.HW = bl2 ? 1.0f : 0.0f;
                serverAction.aco = true;
            }
            serverAction.y += (f8 - serverAction.y) * 0.22f;
            if (Math.abs(f8 - serverAction.y) < 0.3f) {
                serverAction.y = f8;
            }
            serverAction.HW = serverAction.HW + ((bl2 ? 1.0f : 0.0f) - serverAction.HW) * 0.22f;
            float f10 = bl2 ? 1.0f : 0.0f;
            if (Math.abs(f10 - serverAction.HW) < 0.01f) {
                float f11 = serverAction.HW = bl2 ? 1.0f : 0.0f;
            }
            if (serverAction.HW <= 0.01f || (f7 = f2 + serverAction.y - this.scroll) + 64.0f < f2 - 64.0f || f7 > f2 + f4 + 64.0f) continue;
            float f12 = (1.0f - serverAction.HW) * -14.0f;
            this.a(class_45872, class_6423, n3, f + f12, f7, f3, n, n2, f2, f4, serverAction.HW);
        }
        Render2D.stopScissor(class_3322);
    }

    private ServerStatus f(class_642 class_6423) {
        ServerStatus serverStatus = this.aca.computeIfAbsent(class_6423, class_6422 -> new ServerStatus());
        if (serverStatus.acq == null || serverStatus.name != class_6423.field_3752 || serverStatus.ack != class_6423.field_3757 || serverStatus.acp != class_6423.field_3760) {
            serverStatus.name = class_6423.field_3752;
            serverStatus.ack = class_6423.field_3757;
            serverStatus.acp = class_6423.field_3760;
            serverStatus.acq = MultiplayerScreen.i(class_6423);
            serverStatus.acr = MultiplayerScreen.j(class_6423);
            serverStatus.acs = MultiplayerScreen.k(class_6423);
        }
        return serverStatus;
    }

    private void a(class_4587 class_45872, class_642 class_6422, int n2, float f, float f2, float f3, int n3, int n4, float f4, float f5, float f6) {
        Instance instance;
        String string;
        String string2;
        float f7;
        float f8;
        float f9;
        float f10;
        boolean bl;
        ServerStatus serverStatus = this.f(class_6422);
        boolean bl2 = n2 >= 0 && n2 == this.aby;
        boolean bl3 = bl = f6 > 0.9f && (float)n4 >= f4 && (float)n4 <= f4 + f5 && MenuChrome.hit(n3, n4, f, f2, f3, 64.0f);
        float f11 = n2 < 0 ? 0.0f : MenuChrome.upd(this.abZ.computeIfAbsent(n2, n -> MenuChrome.anim()), bl && !bl2);
        boolean bl4 = this.a(class_6422);
        if (bl4) {
            f10 = f + 2.0f;
            f9 = f2 + 3.0f;
            f8 = f3 - 4.0f;
            f7 = 58.0f;
            Render2D.drawRoundedRect(class_45872, f10, f9, f8, f7, 9.0f, MultiplayerScreen.b(MenuChrome.accentA(0.08f), f6));
            Render2D.drawBorder(class_45872, f10, f9, f8, f7, 9.0f, 1.0f, 3.4f, MultiplayerScreen.b(MenuChrome.accentA(0.18f), f6));
            Render2D.drawBorder(class_45872, f10, f9, f8, f7, 9.0f, 1.0f, 1.4f, MultiplayerScreen.b(MenuChrome.accentA(0.6f), f6));
        }
        if (bl2) {
            Render2D.drawRoundedRect(class_45872, f, f2, f3, 64.0f, 0.0f, MultiplayerScreen.b(MenuChrome.accentA(0.05f), f6));
            Render2D.drawRoundedRect(class_45872, f, f2, 2.0f, 64.0f, 0.0f, MultiplayerScreen.b(MenuChrome.accent(), f6));
        } else if (f11 > 0.001f) {
            Render2D.drawRoundedRect(class_45872, f, f2, f3, 64.0f, 0.0f, MultiplayerScreen.b(MenuChrome.white(0.035f * f11), f6));
        }
        Render2D.drawRoundedRect(class_45872, f, f2 + 64.0f - 1.0f, f3, 1.0f, 0.0f, MultiplayerScreen.b(MenuChrome.white(0.05f), f6));
        f10 = f + 22.0f;
        f9 = f2 + 32.0f;
        f8 = 38.0f;
        f7 = f10;
        float f12 = f9 - f8 / 2.0f;
        class_1043 class_10432 = this.e(class_6422);
        if (class_10432 != null) {
            float f13 = 32.0f;
            float f14 = (f8 - f13) / 2.0f;
            Render2D.drawTexture(class_45872, f7 + f14, f12 + f14, f13, f13, 6.0f, (class_1044)class_10432, MultiplayerScreen.b(Color.WHITE, f6));
            Render2D.drawBorder(class_45872, f7, f12, f8, f8, 8.0f, 1.0f, 1.4f, MultiplayerScreen.b(MenuChrome.white(0.1f), f6));
        } else {
            Color color = MultiplayerScreen.m(class_6422);
            Render2D.drawRoundedVerticalGradient(class_45872, f7, f12, f8, f8, 8.0f, MultiplayerScreen.b(color, f6), MultiplayerScreen.b(MultiplayerScreen.j(color), f6));
            Render2D.drawBorder(class_45872, f7, f12, f8, f8, 8.0f, 1.0f, 1.4f, MultiplayerScreen.b(bl2 ? MenuChrome.accentA(0.32f) : MenuChrome.white(0.1f), f6));
            Instance instance2 = Fonts.UNBOUNDED.getFont(12.0f);
            string2 = serverStatus.acq;
            string = (string2.isEmpty() ? "?" : string2.substring(0, 1)).toUpperCase(Locale.ROOT);
            Render2D.drawFont(class_45872, instance2, string, f7 + (f8 - instance2.getWidth(string)) / 2.0f, f12 + (f8 - instance2.getHeight()) / 2.0f, MultiplayerScreen.b(bl2 ? MenuChrome.accent() : MultiplayerScreen.n(class_6422), f6));
        }
        Instance instance3 = Fonts.PLEXMONO.getFont(11.0f);
        Instance instance4 = Fonts.PLEXMONO.getFont(10.5f);
        string2 = MultiplayerScreen.l(class_6422);
        string = MultiplayerScreen.h(class_6422);
        float f15 = 18.0f;
        float f16 = f15 + (string.isEmpty() ? 0.0f : 7.0f + instance4.getWidth(string));
        float f17 = Math.max(instance3.getWidth(string2), f16);
        float f18 = f + f3 - 22.0f - f17;
        Render2D.drawFont(class_45872, instance3, string2, f + f3 - 22.0f - instance3.getWidth(string2), f9 - 2.5f - instance3.getHeight(), MultiplayerScreen.b(abc, f6));
        float f19 = f + f3 - 22.0f - f16;
        float f20 = f9 + 2.5f + 14.0f;
        this.a(class_45872, f19, f20, class_6422, f6);
        if (!string.isEmpty()) {
            Render2D.drawFont(class_45872, instance4, string, f19 + f15 + 7.0f, f20 - 7.0f - instance4.getHeight() / 2.0f, MultiplayerScreen.b(bl2 ? MenuChrome.accent() : abc, f6));
        }
        float f21 = f7 + f8 + 14.0f;
        float f22 = f18 - 14.0f - f21;
        Instance instance5 = Fonts.UNBOUNDED.getFont(11.5f);
        String string3 = serverStatus.acq;
        String string4 = MultiplayerScreen.clip(instance5, string3, f22);
        float f23 = f9 - 2.0f - instance5.getHeight();
        Render2D.drawFont(class_45872, instance5, string4, f21, f23, MultiplayerScreen.b(aba, f6));
        String string5 = serverStatus.acs;
        if (!string5.isEmpty()) {
            instance = Fonts.PLEXMONO.getFont(9.0f);
            float f24 = f21 + instance5.getWidth(string4) + 8.0f;
            float f25 = instance.getWidth(string5) + 12.0f;
            float f26 = 16.0f;
            if (f24 + f25 <= f21 + f22) {
                float f27 = f23 + instance5.getHeight() / 2.0f - f26 / 2.0f;
                Render2D.drawBorder(class_45872, f24, f27, f25, f26, 4.0f, 1.0f, 1.4f, MultiplayerScreen.b(MenuChrome.white(0.12f), f6));
                Render2D.drawFont(class_45872, instance, string5, f24 + 6.0f, f27 + f26 / 2.0f - instance.getHeight() / 2.0f, MultiplayerScreen.b(abc, f6));
            }
        }
        instance = Fonts.GOLOS.getFont(12.0f);
        Render2D.drawFont(class_45872, instance, MultiplayerScreen.clip(instance, serverStatus.acr, f22), f21, f9 + 2.0f, MultiplayerScreen.b(abd, f6));
        if (n2 >= 0 && n2 < this.abX.size()) {
            MenuChrome.set(this.abX.get(n2), f, f2, f3, 64.0f);
        }
    }

    private void a(class_4587 class_45872, float f, float f2, class_642 class_6422, float f3) {
        int n = MultiplayerScreen.g(class_6422);
        float[] fArray = new float[]{5.0f, 8.0f, 11.0f, 14.0f};
        for (int i = 0; i < 4; ++i) {
            float f4 = f + (float)i * 5.0f;
            Color color = i < n ? MenuChrome.accent() : MenuChrome.white(0.16f);
            Render2D.drawRoundedRect(class_45872, f4, f2 - fArray[i], 3.0f, fArray[i], 1.0f, MultiplayerScreen.b(color, f3));
        }
    }

    private static int g(class_642 class_6422) {
        if (class_6422.method_55825() == class_642.class_9083.field_47882 || class_6422.method_55825() == class_642.class_9083.field_47883) {
            return 0;
        }
        if (class_6422.method_55825() != class_642.class_9083.field_47884) {
            return 0;
        }
        long l = class_6422.field_3758;
        if (l < 80L) {
            return 4;
        }
        if (l <= 120L) {
            return 3;
        }
        if (l <= 200L) {
            return 2;
        }
        return 1;
    }

    private static String h(class_642 class_6422) {
        return class_6422.method_55825() == class_642.class_9083.field_47884 ? class_6422.field_3758 + " \u043c\u0441" : "";
    }

    private static String aS(String string) {
        if (string == null) {
            return "";
        }
        return acg.matcher(string).replaceAll("").replaceAll("\\s+", " ").trim();
    }

    private static String i(class_642 class_6422) {
        String string = MultiplayerScreen.aS(class_6422.field_3752 == null ? "" : class_6422.field_3752);
        string = ru.method.celebression.server.ServerLabel.resolve(string, class_6422.field_3761, MenuChrome.nick());
        string = ach.matcher(string).replaceAll("");
        string = aci.matcher(string).replaceAll("").trim();
        if ((string = string.replaceAll("[\\s,;\u00b7|]+$", "").trim()).isEmpty()) {
            string = class_6422.field_3761 == null ? "" : class_6422.field_3761;
        }
        return string;
    }

    private static String j(class_642 class_6422) {
        return MultiplayerScreen.aS(class_6422.field_3757 == null ? "" : class_6422.field_3757.getString().replace('\n', ' '));
    }

    private static String k(class_642 class_6422) {
        String string = MultiplayerScreen.aS(class_6422.field_3760 == null ? "" : class_6422.field_3760.getString());
        if (string.isEmpty()) {
            return "";
        }
        Matcher matcher = acj.matcher(string);
        int n = Integer.MAX_VALUE;
        int n2 = Integer.MIN_VALUE;
        while (matcher.find()) {
            int n3 = Integer.parseInt(matcher.group(1));
            if (n3 < n) {
                n = n3;
            }
            if (n3 <= n2) continue;
            n2 = n3;
        }
        if (n2 < 0) {
            return string.length() > 12 ? string.substring(0, 12) : string;
        }
        return n == n2 ? "1." + n : "1." + n + "\u20131." + n2;
    }

    private static String l(class_642 class_6422) {
        if (class_6422.field_41861 == null) {
            return "\u2014";
        }
        return MultiplayerScreen.ax(class_6422.field_41861.comp_1280()) + " / " + MultiplayerScreen.ax(class_6422.field_41861.comp_1279());
    }

    private static String ax(int n) {
        String string = Integer.toString(Math.max(0, n));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            if (i > 0 && (string.length() - i) % 3 == 0) {
                stringBuilder.append('\u00a0');
            }
            stringBuilder.append(string.charAt(i));
        }
        return stringBuilder.toString();
    }

    private static Color m(class_642 class_6422) {
        String string = class_6422.field_3761 == null ? (class_6422.field_3752 == null ? "?" : class_6422.field_3752) : class_6422.field_3761;
        int n = Math.abs(string.hashCode());
        float f = (float)(n % 360) / 360.0f;
        Color color = Color.getHSBColor(f, 0.42f, 0.2f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }

    private static Color n(class_642 class_6422) {
        String string = class_6422.field_3761 == null ? (class_6422.field_3752 == null ? "?" : class_6422.field_3752) : class_6422.field_3761;
        int n = Math.abs(string.hashCode());
        Color color = Color.getHSBColor((float)(n % 360) / 360.0f, 0.45f, 0.8f);
        return color;
    }

    private static Color j(Color color) {
        return new Color(Math.round((float)color.getRed() * 0.62f), Math.round((float)color.getGreen() * 0.62f), Math.round((float)color.getBlue() * 0.62f), color.getAlpha());
    }

    private static String clip(Instance instance, String string, float f) {
        float f2;
        if (string == null) {
            return "";
        }
        if (f <= 0.0f) {
            return "";
        }
        if (instance.getWidth(string) <= f) {
            return string;
        }
        String string2 = "...";
        float f3 = instance.getWidth(string2);
        StringBuilder stringBuilder = new StringBuilder();
        float f4 = 0.0f;
        for (int i = 0; i < string.length() && !(f4 + (f2 = instance.getWidth(String.valueOf(string.charAt(i)))) + f3 > f); ++i) {
            stringBuilder.append(string.charAt(i));
            f4 += f2;
        }
        return String.valueOf(stringBuilder) + string2;
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 38.0f;
        boolean bl = this.aby >= 0 && this.aby < this.abv.size();
        boolean bl2 = bl && !this.a(this.abv.get(this.aby));
        float f5 = 38.0f;
        float f6 = f4 - f5 / 2.0f;
        float f7 = f + 22.0f;
        boolean bl3 = bl2 && MenuChrome.hit(n, n2, f7, f6, f5, f5);
        float f8 = MenuChrome.upd(this.abG, bl3);
        float f9 = this.cw("edit");
        MenuPanelRenderer.c(class_45872, f7, f6 + f9, f5, f5, f8, false);
        ClickGuiIcons.gear(class_45872, f7 + f5 / 2.0f, f6 + f9 + f5 / 2.0f, 17.0f - 2.0f * f9, bl2 ? MenuChrome.lerp(abc, abb, f8) : abf);
        MenuChrome.set(this.abS, f7, f6, f5, f5);
        float f10 = f7 + f5 + 9.0f;
        boolean bl4 = bl2 && MenuChrome.hit(n, n2, f10, f6, f5, f5);
        float f11 = MenuChrome.upd(this.abH, bl4);
        float f12 = this.cw("delete");
        MenuPanelRenderer.c(class_45872, f10, f6 + f12, f5, f5, f11, true);
        ClickGuiIcons.trash(class_45872, f10 + f5 / 2.0f, f6 + f12 + f5 / 2.0f, 17.0f - 2.0f * f12, bl2 ? MenuChrome.lerp(abc, abh, f11) : abf);
        MenuChrome.set(this.abT, f10, f6, f5, f5);
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        float f13 = 44.0f;
        float f14 = f4 - f13 / 2.0f;
        String string = "\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f";
        float f15 = 50.0f + instance.getWidth(string) + 24.0f;
        float f16 = f + f3 - 22.0f - f15;
        boolean bl5 = MenuChrome.hit(n, n2, f16, f14, f15, f13);
        float f17 = MenuChrome.upd(this.abK, bl5);
        this.b(class_45872, f16, f14 + 2.0f * this.cw("join"), f15, f13, f17, instance, string);
        MenuChrome.set(this.abW, f16, f14, f15, f13);
        String string2 = "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c";
        float f18 = 43.0f + instance.getWidth(string2) + 18.0f;
        float f19 = f16 - 9.0f - f18;
        boolean bl6 = MenuChrome.hit(n, n2, f19, f14, f18, f13);
        float f20 = MenuChrome.upd(this.abJ, bl6);
        this.a(class_45872, f19, f14 + 2.0f * this.cw("add"), f18, f13, f20, instance, string2, true);
        MenuChrome.set(this.abV, f19, f14, f18, f13);
        String string3 = "\u041f\u043e \u0430\u0434\u0440\u0435\u0441\u0443";
        float f21 = 20.0f + instance.getWidth(string3) + 20.0f;
        float f22 = f19 - 9.0f - f21;
        boolean bl7 = MenuChrome.hit(n, n2, f22, f14, f21, f13);
        float f23 = MenuChrome.upd(this.abI, bl7);
        this.a(class_45872, f22, f14 + 2.0f * this.cw("direct"), f21, f13, f23, instance, string3);
        MenuChrome.set(this.abU, f22, f14, f21, f13);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Instance instance, String string, boolean bl) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 8.0f, MenuChrome.white(0.04f));
        Render2D.drawBorder(class_45872, f, f2, f3, f4, 8.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.24f), f5));
        ClickGuiIcons.plus(class_45872, f + 18.0f + 7.5f, f2 + f4 / 2.0f, 18.0f, abc);
        Render2D.drawFont(class_45872, instance, string, f + 18.0f + 15.0f + 10.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, abb);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Instance instance, String string) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 8.0f, MenuChrome.white(0.04f));
        Render2D.drawBorder(class_45872, f, f2, f3, f4, 8.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.24f), f5));
        Render2D.drawFont(class_45872, instance, string, f + (f3 - instance.getWidth(string)) / 2.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, abb);
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Instance instance, String string) {
        float f6 = f2 - f5;
        Color color = MenuChrome.accent();
        int n = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f5)));
        int n2 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f5)));
        int n3 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f5)));
        Render2D.drawRoundedRect(class_45872, f, f6, f3, f4, 8.0f, new Color(n, n2, n3));
        Render2D.drawRoundedRect(class_45872, f + 8.0f, f6 + 1.0f, f3 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        ClickGuiIcons.arrowRight(class_45872, f + 24.0f + 7.5f, f6 + f4 / 2.0f, 18.0f, abg);
        Render2D.drawFont(class_45872, instance, string, f + 24.0f + 15.0f + 11.0f, f6 + f4 / 2.0f - instance.getHeight() / 2.0f, abg);
    }

    public boolean method_25402(double d, double d2, int n) {
        if (n != 0) {
            return super.method_25402(d, d2, n);
        }
        float f = (float)(d / (double)this.uiScale);
        float f2 = (float)(d2 / (double)this.uiScale);
        if (MenuChrome.in(this.abN, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.hasMusic() && MenuChrome.in(this.abP, f, f2)) {
            MenuChrome.toggleMusic();
            return true;
        }
        this.Lj = MenuChrome.in(this.abQ, f, f2);
        if (this.Lj) {
            return true;
        }
        if (this.abM[2] > 0.0f && MenuChrome.in(this.abM, f, f2)) {
            this.pN();
            return true;
        }
        if (MenuChrome.in(this.abR, f, f2)) {
            this.cv("refresh");
            this.refreshAll();
            return true;
        }
        if (MenuChrome.in(this.abW, f, f2)) {
            this.cv("join");
            this.pL();
            return true;
        }
        if (MenuChrome.in(this.abV, f, f2)) {
            this.cv("add");
            this.pN();
            return true;
        }
        if (MenuChrome.in(this.abU, f, f2)) {
            this.cv("direct");
            this.pO();
            return true;
        }
        if (MenuChrome.in(this.abS, f, f2)) {
            this.cv("edit");
            this.pM();
            return true;
        }
        if (MenuChrome.in(this.abT, f, f2)) {
            this.cv("delete");
            this.pP();
            return true;
        }
        for (int i = 0; i < this.abX.size(); ++i) {
            float[] fArray = this.abX.get(i);
            if (fArray[2] <= 0.0f || !MenuChrome.in(fArray, f, f2)) continue;
            long l = System.currentTimeMillis();
            boolean bl = i == this.abz && l - this.Lo < 300L;
            this.aby = i;
            this.abz = i;
            this.Lo = l;
            if (bl) {
                this.pL();
            }
            return true;
        }
        return super.method_25402(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        this.Lm = class_3532.method_15363((float)(this.Lm - (float)d4 * 64.0f * 0.8f), (float)0.0f, (float)Math.max(0.0f, (float)this.abv.size() * 64.0f - this.pI()));
        return true;
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (n == 256) {
            this.pJ();
            return true;
        }
        if (this.Lj) {
            if (n == 259) {
                if (!this.Li.isEmpty()) {
                    this.Li = this.Li.substring(0, this.Li.length() - 1);
                    this.pE();
                }
                return true;
            }
            if (n == 257 || n == 335) {
                this.Lj = false;
                return true;
            }
        }
        if (n == 257 || n == 335) {
            this.pL();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.Lj && c >= ' ') {
            this.Li = this.Li + c;
            this.pE();
            return true;
        }
        return super.method_25400(c, n);
    }

    private void pJ() {
        MenuChrome.keepMusicAcrossSwap();
        if (this.abl != null) {
            aiG.method_1507(this.abl);
        } else {
            aiG.method_1507((class_437)new CustomMainMenuScreen());
        }
    }

    private class_642 pK() {
        return this.aby >= 0 && this.aby < this.abv.size() ? this.abv.get(this.aby) : null;
    }

    private void pL() {
        class_642 class_6422 = this.pK();
        if (class_6422 == null) {
            return;
        }
        class_412.method_36877((class_437)this, (class_310)aiG, (class_639)class_639.method_2950((String)class_6422.field_3761), (class_642)class_6422, (boolean)false, null);
    }

    private void pM() {
        class_642 class_6422 = this.pK();
        if (class_6422 == null || this.a(class_6422)) {
            return;
        }
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new EditServerDialog(this, class_6422, false));
    }

    private void pN() {
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new EditServerDialog(this, new class_642("\u0421\u0435\u0440\u0432\u0435\u0440", "", class_642.class_8678.field_45611), true));
    }

    private void pO() {
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new DirectConnectDialog(this));
    }

    private void pP() {
        class_642 class_6422 = this.pK();
        if (class_6422 == null || this.a(class_6422)) {
            return;
        }
        this.abA = class_6422;
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new DeleteServerDialog(this, class_6422));
    }

    void a(class_642 class_6422, boolean bl) {
        if (bl) {
            this.abm.method_2988(class_6422, false);
        }
        this.abm.method_2987();
        this.pE();
        this.b(class_6422);
    }

    void pQ() {
        if (this.abA != null) {
            this.abm.method_2983(this.abA);
            this.abm.method_2987();
            this.abA = null;
            this.aby = -1;
            this.pE();
        }
    }

    void cx(String string) {
        class_642 class_6422 = new class_642("\u0421\u0435\u0440\u0432\u0435\u0440", string, class_642.class_8678.field_45611);
        class_412.method_36877((class_437)this, (class_310)aiG, (class_639)class_639.method_2950((String)string), (class_642)class_6422, (boolean)false, null);
    }

    private void openAccountSwitcher() {
        if (aiG == null || !IasIntegration.kD()) {
            return;
        }
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new AccountManagerScreen(this));
    }

    public void method_25432() {
        if (this.acc) {
            this.acc = false;
            this.abm.method_2987();
        }
        this.abn.method_3004();
        for (class_1043 class_10432 : this.abo.values()) {
            try {
                class_10432.close();
            }
            catch (Throwable throwable) {}
        }
        this.abo.clear();
        this.abp.clear();
        MenuChrome.onMenuScreenRemoved();
        super.method_25432();
    }

    public boolean method_25422() {
        return false;
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }

    static final class ServerSelection {
        private final class_2561 ack;
        private final class_2561 acl;
        private final class_2926.class_2927 acm;
        private final long acn;

        ServerSelection(class_2561 class_25612, class_2561 class_25613, class_2926.class_2927 class_29272, long l) {
            this.ack = class_25612;
            this.acl = class_25613;
            this.acm = class_29272;
            this.acn = l;
        }

        @Override
        public final String toString() {
            return "ServerSelection[" + "label=" + this.ack + ", " + "playerCountLabel=" + this.acl + ", " + "players=" + this.acm + ", " + "ping=" + this.acn + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ack, this.acl, this.acm, this.acn);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ServerSelection other)) return false;
            return java.util.Objects.deepEquals(this.ack, other.ack)
                && java.util.Objects.deepEquals(this.acl, other.acl)
                && java.util.Objects.deepEquals(this.acm, other.acm)
                && java.util.Objects.deepEquals(this.acn, other.acn);
        }

        public class_2561 pR() {
            return this.ack;
        }

        public class_2561 pS() {
            return this.acl;
        }

        public class_2926.class_2927 pT() {
            return this.acm;
        }

        public long pU() {
            return this.acn;
        }
    }

    static final class ServerAction {
        float HW;
        float y;
        boolean aco;

        ServerAction() {
        }
    }

    static final class ServerStatus {
        String name;
        class_2561 ack;
        class_2561 acp;
        String acq;
        String acr;
        String acs;

        ServerStatus() {
        }
    }
}
