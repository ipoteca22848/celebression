package ru.method.celebression.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_638;
import net.minecraft.class_8646;
import org.joml.Vector3f;
import ru.method.celebression.Celebression;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.util.NamedItem;
import ru.method.celebression.render.ProjectionUtils;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.DepthRenderScope;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class WaypointManager
implements MinecraftClientAccess {
    private static final Pattern oF = Pattern.compile("(?i)x\\s*[:=]?\\s*(-?\\d+)\\D+?y\\s*[:=]?\\s*(-?\\d+)\\D+?z\\s*[:=]?\\s*(-?\\d+)");
    private static final Pattern oG = Pattern.compile("(?i)x\\s*[:=]?\\s*(-?\\d+)\\D+?z\\s*[:=]?\\s*(-?\\d+)");
    private static final Pattern oH = Pattern.compile("(?iu)(?:coords?|coordinates?|\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\\p{L}*)\\D+?(-?\\d+)\\D+?(-?\\d+)(?:\\D+?(-?\\d+))?");
    private static final Pattern oI = Pattern.compile("(-?\\d+)\\s*[ ,;|/]\\s*(-?\\d+)\\s*[ ,;|/]\\s*(-?\\d+)");
    private static final Pattern oJ = Pattern.compile("-?\\d+");
    private static final Pattern oK = Pattern.compile("\\[([^\\]]+)]");
    private static final Pattern oL = Pattern.compile("\\|\\|\\|\\s*([^|]+?)\\s*\\|\\|\\|");
    private static final class_2960 oM = Celebression.id("client/waypoint/way.png");
    private static final long oN = 1200000L;
    private static final long oO = 15000L;
    private static final float oP = 4.0f;
    private static final float oQ = 24.0f;
    private static final float oR = 48.0f;
    private static final float oS = 150.0f;
    private static final String[] oT = new String[]{"coords", "coordinate", "coordinates", "\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442"};
    private static final String[] oU = new String[]{"event", "coords", "coordinate", "coordinates", "\u0438\u0432\u0435\u043d\u0442", "\u043c\u0430\u044f\u043a", "\u0441\u0443\u043d\u0434\u0443\u043a", "\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442"};
    private static final String[] oV = new String[]{"ft", "funtime", "fun time", "\u0444\u0442", "\u0444\u0430\u043d\u0442\u0430\u0439\u043c"};
    private final List<NamedItem> oW = new ArrayList<NamedItem>();
    private class_243 oX;
    private String oY;
    private long oZ;
    private boolean pa;
    private String pb;
    private long pc;
    private NamedItem pd;
    private long pe;
    private class_638 pf;
    private class_5321<class_1937> pg;

    public WaypointManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

    public void a(NamedItem namedItem) {
        this.oW.add(namedItem);
    }

    public void b(NamedItem namedItem) {
        this.oW.remove(namedItem);
    }

    public void clear() {
        if (!this.oW.isEmpty()) {
            this.oW.clear();
        }
    }

    public boolean isEmpty() {
        return this.oW.isEmpty();
    }

    public NamedItem O(String string) {
        for (NamedItem namedItem : this.oW) {
            if (!namedItem.getName().equalsIgnoreCase(string)) continue;
            return namedItem;
        }
        return null;
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        if (this.oW.isEmpty()) {
            return;
        }
        boolean bl = this.pg == null || WaypointManager.aiG.field_1687 != null && this.pg.equals((Object)WaypointManager.aiG.field_1687.method_27983());
        for (NamedItem namedItem : this.oW) {
            if (namedItem == this.pd && !bl) continue;
            class_243 class_2432 = new class_243(namedItem.ti(), namedItem.tj(), namedItem.tk());
            double d = WaypointManager.aiG.field_1724.method_19538().method_1022(class_2432);
            this.a(hudRenderEvent, class_2432, namedItem, d);
        }
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        class_638 class_6382 = WaypointManager.aiG.field_1687;
        if (class_6382 != this.pf) {
            this.pf = class_6382;
        }
        this.f(System.currentTimeMillis());
    }

    private void a(HudRenderEvent hudRenderEvent, class_243 class_2432, NamedItem namedItem, double d) {
        Vector3f vector3f;
        class_243 class_2433;
        class_243 class_2434 = WaypointManager.aiG.field_1773.method_19418().method_19326();
        class_243 class_2435 = class_2432.method_1020(class_2434).method_1029();
        double d2 = class_2435.method_1026(class_2433 = new class_243(vector3f = WaypointManager.aiG.field_1773.method_19418().method_19335()));
        if (d2 <= 0.1) {
            return;
        }
        class_243 class_2436 = ProjectionUtils.o(class_2432);
        if (class_2436.field_1350 <= 0.0) {
            return;
        }
        if (class_2436.method_10216() < -100.0 || class_2436.method_10216() > (double)(aiG.method_22683().method_4486() + 100)) {
            return;
        }
        if (class_2436.method_10214() < -100.0 || class_2436.method_10214() > (double)(aiG.method_22683().method_4502() + 100)) {
            return;
        }
        float f = (float)class_2436.method_10216();
        float f2 = (float)class_2436.method_10214();
        Color color = ColorUtils.getGlobalColor();
        float f3 = Math.max(0.4f, 1.0f - (float)(d / 3000.0));
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255.0f * f3));
        float f4 = 32.0f;
        try (DepthRenderScope depthRenderScope = DepthRenderScope.lequal();){
            hudRenderEvent.j().method_51448().method_22903();
            hudRenderEvent.j().method_51448().method_46416(f, f2, -100.0f);
            Render2D.drawTexture(hudRenderEvent.j().method_51448(), -f4 / 2.0f, -f4 / 2.0f, f4, f4, 0.0f, oM, color2);
            hudRenderEvent.j().method_51448().method_22909();
        }
        this.a(hudRenderEvent, new class_243((double)f, (double)f2, class_2436.field_1350), namedItem, d, f3);
    }

    private void a(HudRenderEvent hudRenderEvent, class_243 class_2432, NamedItem namedItem, double d, float f) {
        String string = namedItem.getName();
        String string2 = MathUtils.ax((float)d) + "m";
        Color color = new Color(255, 255, 255, (int)(255.0f * f));
        Color color2 = new Color(200, 200, 200, (int)(255.0f * f * 0.9f));
        float f2 = Fonts.ROUNDBOLD.getWidth(string, 8.0f);
        float f3 = Fonts.ROUNDBOLD.getWidth(string2, 7.0f);
        float f4 = Math.max(f2, f3);
        float f5 = f4 + 12.0f;
        float f6 = 30.0f;
        float f7 = (float)class_2432.method_10216() - f5 / 2.0f;
        float f8 = (float)class_2432.method_10214() + 20.0f;
        float[] fArray = this.c(f7, f8, f5, f6);
        f7 = fArray[0];
        f8 = fArray[1];
        Color color3 = new Color(0, 0, 0, (int)(140.0f * f));
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f7, f8, f5, f6, 3.0f, color3);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string, f7 + (f5 - f2) / 2.0f, f8 + 5.0f, color);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), string2, f7 + (f5 - f3) / 2.0f, f8 + 18.0f, color2);
    }

    private float[] c(float f, float f2, float f3, float f4) {
        float f5 = aiG.method_22683().method_4486();
        float f6 = aiG.method_22683().method_4502();
        float f7 = 4.0f;
        if (WaypointManager.aiG.field_1687 != null && WaypointManager.aiG.field_1687.method_8428() != null && WaypointManager.aiG.field_1687.method_8428().method_1189(class_8646.field_45157) != null) {
            f7 = 150.0f;
        }
        float f8 = 4.0f;
        float f9 = Math.max(f8, f5 - f7 - f3);
        float f10 = 24.0f;
        float f11 = Math.max(f10, f6 - 48.0f - f4);
        float f12 = Math.max(f8, Math.min(f, f9));
        float f13 = Math.max(f10, Math.min(f2, f11));
        return new float[]{f12, f13};
    }

    public void P(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        long l = System.currentTimeMillis();
        String string3 = this.h(string, string2);
        if (string3 != null) {
            this.pb = string3;
            this.pc = l;
            if (this.oX != null && l - this.oZ <= 1200000L) {
                this.oY = string3;
            }
        }
        if (!this.X(string2)) {
            return;
        }
        if (this.U(string2)) {
            return;
        }
        if (this.V(string2)) {
            return;
        }
        class_243 class_2432 = this.k(string, string2);
        if (class_2432 == null) {
            return;
        }
        if (this.oX != null && l - this.oZ <= 15000L && this.a(string, string2, class_2432)) {
            return;
        }
        this.oX = class_2432;
        this.oZ = l;
        this.pg = WaypointManager.aiG.field_1687 != null ? WaypointManager.aiG.field_1687.method_27983() : null;
        this.pa = this.Y(string2);
        String string4 = this.b(l, string3);
        if (string4 != null) {
            this.oY = string4;
            this.pb = null;
            this.pc = 0L;
        } else {
            this.oY = null;
        }
    }

    public boolean eW() {
        long l = System.currentTimeMillis();
        this.f(l);
        if (!this.g(l)) {
            ChatOutput.cG(class_1074.method_4662((String)"elements.event_marker.missing", (Object[])new Object[0]));
            return false;
        }
        if (this.pg != null && WaypointManager.aiG.field_1687 != null && !this.pg.equals((Object)WaypointManager.aiG.field_1687.method_27983())) {
            return false;
        }
        String string = this.fc();
        NamedItem namedItem = new NamedItem(string, this.oX.field_1352, this.oX.field_1351, this.oX.field_1350);
        this.fb();
        this.a(namedItem);
        this.pd = namedItem;
        this.pe = this.oZ + 1200000L;
        ChatOutput.cG(class_1074.method_4662((String)"elements.event_marker.placed", (Object[])new Object[]{string, (int)this.oX.field_1352, (int)this.oX.field_1351, (int)this.oX.field_1350}));
        return true;
    }

    public boolean eX() {
        long l = System.currentTimeMillis();
        this.f(l);
        return this.g(l);
    }

    public boolean eY() {
        long l = System.currentTimeMillis();
        this.f(l);
        return this.g(l) && this.pd == null;
    }

    public boolean eZ() {
        long l = System.currentTimeMillis();
        this.f(l);
        return this.g(l) && this.pa && this.pd == null;
    }

    private void f(long l) {
        if (this.pd != null && !this.oW.contains(this.pd)) {
            this.pd = null;
            this.pe = 0L;
        }
        if (this.pb != null && l - this.pc > 15000L) {
            this.pb = null;
            this.pc = 0L;
        }
        if (this.pe > 0L && l >= this.pe) {
            this.fa();
            return;
        }
        if (this.oX != null && this.oZ > 0L && l - this.oZ > 1200000L) {
            this.fa();
        }
    }

    private void fa() {
        this.fb();
        this.oX = null;
        this.oY = null;
        this.oZ = 0L;
        this.pg = null;
        this.pa = false;
        this.pb = null;
        this.pc = 0L;
    }

    private void fb() {
        if (this.pd != null && this.oW.contains(this.pd)) {
            this.b(this.pd);
        }
        this.pd = null;
        this.pe = 0L;
    }

    private boolean g(long l) {
        if (this.oX == null || this.oZ <= 0L) {
            return false;
        }
        return l - this.oZ <= 1200000L;
    }

    private String fc() {
        if (this.oY != null && !this.oY.isBlank()) {
            return this.oY.trim();
        }
        String string = class_1074.method_4662((String)"elements.event_marker.waypoint", (Object[])new Object[0]);
        if (string == null || string.isBlank()) {
            return "Event";
        }
        return string;
    }

    private String b(long l, String string) {
        if (string != null && !string.isBlank()) {
            return string.trim();
        }
        if (this.pb != null && !this.pb.isBlank() && l - this.pc <= 15000L) {
            return this.pb.trim();
        }
        return null;
    }

    private String h(String string, String string2) {
        String string3 = this.i(string, string2);
        if (string3 != null) {
            return string3;
        }
        return this.j(string, string2);
    }

    private String i(String string, String string2) {
        Matcher matcher = oK.matcher(string);
        while (matcher.find()) {
            String string3 = this.Q(matcher.group(1));
            if (string3.isEmpty() || !this.S(string3) || this.T(string3)) continue;
            String string4 = string3.toLowerCase(Locale.ROOT);
            if (!this.R(string) && !this.X(string2) && !this.X(string4)) continue;
            return string3;
        }
        return null;
    }

    private String j(String string, String string2) {
        Matcher matcher = oL.matcher(string);
        while (matcher.find()) {
            String string3 = this.Q(matcher.group(1));
            if (string3.isEmpty() || !this.S(string3) || this.T(string3)) continue;
            String string4 = string3.toLowerCase(Locale.ROOT);
            if (!this.R(string) && !this.X(string2) && !this.X(string4)) continue;
            return string3;
        }
        return null;
    }

    private String Q(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim();
        if (string2.startsWith("[") && string2.endsWith("]") && string2.length() > 2) {
            string2 = string2.substring(1, string2.length() - 1).trim();
        }
        return string2;
    }

    private boolean R(String string) {
        return string.contains("|||") || string.indexOf(9553) >= 0 || string.indexOf(9556) >= 0 || string.indexOf(9559) >= 0 || string.indexOf(9562) >= 0 || string.indexOf(9565) >= 0 || string.indexOf(9552) >= 0;
    }

    private boolean S(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isLetter(string.charAt(i))) continue;
            return true;
        }
        return false;
    }

    private boolean T(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (Character.isDigit(c) || Character.isWhitespace(c) || c == '-' || c == ',' || c == ';' || c == '|' || c == '/') continue;
            return false;
        }
        return true;
    }

    private class_243 k(String string, String string2) {
        class_243 class_2432;
        Matcher matcher2 = oF.matcher(string);
        if (matcher2.find()) {
            return this.b(matcher2.group(1), matcher2.group(2), matcher2.group(3));
        }
        Matcher matcher3 = oG.matcher(string);
        if (matcher3.find()) {
            return this.b(matcher3.group(1), this.fd(), matcher3.group(2));
        }
        Matcher matcher4 = oH.matcher(string);
        while (matcher4.find()) {
            class_243 candidate = matcher4.group(3) == null ? this.b(matcher4.group(1), this.fd(), matcher4.group(2)) : this.b(matcher4.group(1), matcher4.group(2), matcher4.group(3));
            if (!this.c(candidate)) continue;
            return candidate;
        }
        if (!this.l(string, string2)) {
            return null;
        }
        Matcher matcher = oI.matcher(string);
        while (matcher.find()) {
            class_2432 = this.b(matcher.group(1), matcher.group(2), matcher.group(3));
            if (!this.c(class_2432) || this.b(class_2432)) continue;
            return class_2432;
        }
        ArrayList<Integer> coordinates = new ArrayList<>();
        Matcher matcher5 = oJ.matcher(string);
        while (matcher5.find()) {
            try {
                coordinates.add(Integer.parseInt(matcher5.group()));
            }
            catch (NumberFormatException numberFormatException) {}
        }
        if (coordinates.size() >= 3) {
            for (int i = coordinates.size() - 3; i >= 0; --i) {
                class_243 class_2433 = new class_243(coordinates.get(i), coordinates.get(i + 1), coordinates.get(i + 2));
                if (!this.c(class_2433) || this.b(class_2433)) continue;
                return class_2433;
            }
        }
        if (coordinates.size() == 2) {
            class_243 class_2434 = new class_243(coordinates.get(0), this.fe(), coordinates.get(1));
            return this.c(class_2434) && !this.b(class_2434) ? class_2434 : null;
        }
        return null;
    }

    private boolean l(String string, String string2) {
        if (this.W(string2) || this.R(string)) {
            return true;
        }
        return oF.matcher(string).find() || oG.matcher(string).find();
    }

    private boolean a(String string, String string2, class_243 class_2432) {
        if (this.W(string2) || this.R(string)) {
            return false;
        }
        return this.b(class_2432);
    }

    private boolean U(String string) {
        return string.contains("\u0434\u043e \u0441\u043b\u0435\u0434\u0443\u044e\u0449") || string.contains("\u043c\u0438\u043d") || string.contains("\u0441\u0435\u043a") || string.contains(" minute") || string.contains(" min ") || string.contains(" sec") || string.contains(" second");
    }

    private boolean V(String string) {
        return string.contains("\u0432\u0430\u0448\u0438 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442") || string.contains("\u0442\u0432\u043e\u0438 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442") || string.contains("\u0442\u0435\u043a\u0443\u0449\u0438\u0435 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442") || string.contains("\u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d") || string.contains("\u0432\u044b \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0435\u0441\u044c") || string.contains("your coordinat") || string.contains("your coord") || string.contains("you are at") || string.contains("teleported");
    }

    private boolean b(class_243 class_2432) {
        if (class_2432 == null) {
            return true;
        }
        double d = Math.max(Math.abs(class_2432.field_1352), Math.max(Math.abs(class_2432.field_1351), Math.abs(class_2432.field_1350)));
        double d2 = Math.min(Math.abs(class_2432.field_1352), Math.min(Math.abs(class_2432.field_1351), Math.abs(class_2432.field_1350)));
        return d <= 120.0 && d2 <= 10.0;
    }

    private boolean W(String string) {
        for (String string2 : oT) {
            if (!string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    private String fd() {
        return String.valueOf((int)Math.round(this.fe()));
    }

    private double fe() {
        return WaypointManager.aiG.field_1724 != null ? WaypointManager.aiG.field_1724.method_23318() : 0.0;
    }

    private class_243 b(String string, String string2, String string3) {
        try {
            class_243 class_2432 = new class_243(Double.parseDouble(string), Double.parseDouble(string2), Double.parseDouble(string3));
            return this.c(class_2432) ? class_2432 : null;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private boolean c(class_243 class_2432) {
        if (class_2432 == null) {
            return false;
        }
        int n = WaypointManager.aiG.field_1687 != null ? WaypointManager.aiG.field_1687.method_31607() - 16 : -128;
        int n2 = WaypointManager.aiG.field_1687 != null ? WaypointManager.aiG.field_1687.method_31607() + WaypointManager.aiG.field_1687.method_31605() + 64 : 512;
        return class_2432.field_1351 >= (double)n && class_2432.field_1351 <= (double)n2 && Math.abs(class_2432.field_1352) <= 3.0E7 && Math.abs(class_2432.field_1350) <= 3.0E7;
    }

    private boolean X(String string) {
        for (String string2 : oU) {
            if (!string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    private boolean Y(String string) {
        for (String string2 : oV) {
            if (!string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    @Generated
    public List<NamedItem> ff() {
        return this.oW;
    }
}
