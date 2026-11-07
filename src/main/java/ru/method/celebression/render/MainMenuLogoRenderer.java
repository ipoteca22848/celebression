package ru.method.celebression.render;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.method.celebression.Celebression;

public final class MainMenuLogoRenderer {
    private static final Logger adq = LoggerFactory.getLogger((String)"Celebression/Splash");
    private static final Map<LogoAsset, LogoFrame> adr = new HashMap<LogoAsset, LogoFrame>();
    private static final Map<LogoAsset, Boolean> ads = new HashMap<LogoAsset, Boolean>();
    private static final Map<String, LogoState> adt = new LinkedHashMap<String, LogoState>();
    private static int adu;

    private MainMenuLogoRenderer() {
    }

    public static LogoState a(LogoAsset logoAsset, String string, int n, int n2, float f, int n3) {
        LogoState logoState;
        if (string == null || string.isEmpty() || n <= 0) {
            return null;
        }
        String string2 = logoAsset.name() + "|" + n + "|" + n2 + "|" + f + "|" + n3 + "|" + string;
        LogoState logoState2 = adt.get(string2);
        if (logoState2 != null) {
            return logoState2;
        }
        try {
            logoState = MainMenuLogoRenderer.b(logoAsset, string, n, n2, f, Math.max(1, n3));
        }
        catch (Throwable throwable) {
            MainMenuLogoRenderer.a(logoAsset, throwable);
            logoState = null;
        }
        if (logoState != null) {
            adt.put(string2, logoState);
        }
        return logoState;
    }

    public static LogoState h(int n, float f) {
        LogoState logoState;
        String string = "glow|" + n + "|" + f;
        LogoState logoState2 = adt.get(string);
        if (logoState2 != null) {
            return logoState2;
        }
        try {
            logoState = MainMenuLogoRenderer.i(n, f);
        }
        catch (Throwable throwable) {
            adq.warn("[splash] \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0441\u043e\u0437\u0434\u0430\u0442\u044c \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u0435", throwable);
            logoState = null;
        }
        if (logoState != null) {
            adt.put(string, logoState);
        }
        return logoState;
    }

    public static void qc() {
        class_310 class_3102 = class_310.method_1551();
        class_1060 textureManager = class_3102 == null ? null : class_3102.method_1531();
        if (textureManager != null) {
            for (LogoState logoState : adt.values()) {
                try {
                    textureManager.method_4615(logoState.id());
                }
                catch (Throwable throwable) {}
            }
        }
        adt.clear();
        for (LogoFrame logoFrame : adr.values()) {
            try {
                logoFrame.qd().close();
            }
            catch (Throwable throwable) {}
        }
        adr.clear();
        ads.clear();
    }

    private static LogoFrame a(LogoAsset logoAsset) {
        LogoFrame logoFrame = adr.get((Object)logoAsset);
        if (logoFrame != null) {
            return logoFrame;
        }
        if (ads.containsKey((Object)logoAsset)) {
            return null;
        }
        try {
            LogoFrame logoFrame2 = MainMenuLogoRenderer.b(logoAsset);
            adr.put(logoAsset, logoFrame2);
            return logoFrame2;
        }
        catch (Throwable throwable) {
            MainMenuLogoRenderer.a(logoAsset, throwable);
            return null;
        }
    }

    private static LogoFrame b(LogoAsset logoAsset) throws IOException {
        JsonObject jsonObject;
        try (InputStream inputStream = MainMenuLogoRenderer.cA(logoAsset.adF + ".json");){
            jsonObject = JsonParser.parseReader((Reader)new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
        }
        class_1011 image;
        try (InputStream imageStream = MainMenuLogoRenderer.cA(logoAsset.adF + ".png");){
            image = class_1011.method_4309(imageStream);
        }
        JsonObject atlas = jsonObject.getAsJsonObject("atlas");
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("metrics");
        HashMap<Integer, LogoLayout> hashMap = new HashMap<Integer, LogoLayout>();
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("glyphs")) {
            JsonObject jsonObject3 = jsonElement.getAsJsonObject();
            int n = jsonObject3.get("unicode").getAsInt();
            float f = jsonObject3.get("advance").getAsFloat();
            if (!jsonObject3.has("planeBounds") || !jsonObject3.has("atlasBounds")) {
                hashMap.put(n, new LogoLayout(f, false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
                continue;
            }
            JsonObject jsonObject4 = jsonObject3.getAsJsonObject("planeBounds");
            JsonObject jsonObject5 = jsonObject3.getAsJsonObject("atlasBounds");
            hashMap.put(n, new LogoLayout(f, true, jsonObject4.get("left").getAsFloat(), jsonObject4.get("bottom").getAsFloat(), jsonObject4.get("right").getAsFloat(), jsonObject4.get("top").getAsFloat(), jsonObject5.get("left").getAsFloat(), jsonObject5.get("bottom").getAsFloat(), jsonObject5.get("right").getAsFloat(), jsonObject5.get("top").getAsFloat()));
        }
        return new LogoFrame(image, hashMap, atlas.get("size").getAsFloat(), atlas.get("distanceRange").getAsFloat(), jsonObject2.get("ascender").getAsFloat(), jsonObject2.get("descender").getAsFloat());
    }

    private static InputStream cA(String string) throws FileNotFoundException {
        InputStream inputStream = MainMenuLogoRenderer.class.getResourceAsStream("/assets/celebression/fonts/" + string);
        if (inputStream == null) {
            throw new FileNotFoundException("assets/celebression/fonts/" + string);
        }
        return inputStream;
    }

    private static LogoState b(LogoAsset logoAsset, String string, int n, int n2, float f, int n3) {
        int n4;
        LogoFrame logoFrame = MainMenuLogoRenderer.a(logoAsset);
        if (logoFrame == null) {
            return null;
        }
        float f2 = n;
        int n5 = string.length();
        float f3 = 0.0f;
        for (n4 = 0; n4 < n5; ++n4) {
            LogoLayout logoLayout = logoFrame.glyphs().get(string.charAt(n4));
            if (logoLayout == null) continue;
            f3 += logoLayout.advance() + (n4 < n5 - 1 ? f : 0.0f);
        }
        if (f3 <= 0.0f) {
            return null;
        }
        n4 = Math.max(2, Math.round(f2 * 0.08f));
        float f4 = f3 * f2;
        int n6 = MainMenuLogoRenderer.u(class_3532.method_15386((float)f4) + n4 * 2, n3);
        int n7 = MainMenuLogoRenderer.u(class_3532.method_15386((float)((logoFrame.ascender() - logoFrame.descender()) * f2)) + n4 * 2, n3);
        float f5 = (float)n4 + logoFrame.ascender() * f2;
        float f6 = Math.max(1.0f, logoFrame.qf() * (f2 / logoFrame.qe()));
        byte[] byArray = new byte[n6 * n7];
        float f7 = n4;
        for (int i = 0; i < n5; ++i) {
            LogoLayout logoLayout = logoFrame.glyphs().get(string.charAt(i));
            if (logoLayout == null) continue;
            if (logoLayout.qh()) {
                MainMenuLogoRenderer.a(logoFrame, logoLayout, byArray, n6, n7, f7, f5, f2, f6);
            }
            f7 += (logoLayout.advance() + (i < n5 - 1 ? f : 0.0f)) * f2;
        }
        class_1011 class_10112 = new class_1011(n6, n7, true);
        int n8 = n2 & 0xFFFFFF;
        for (int i = 0; i < n7; ++i) {
            int n9 = i * n6;
            for (int j = 0; j < n6; ++j) {
                class_10112.method_61941(j, i, (byArray[n9 + j] & 0xFF) << 24 | n8);
            }
        }
        return MainMenuLogoRenderer.a(class_10112, n4, f5, f4);
    }

    private static void a(LogoFrame logoFrame, LogoLayout logoLayout, byte[] byArray, int n, int n2, float f, float f2, float f3, float f4) {
        float f5 = f + logoLayout.qi() * f3;
        float f6 = f + logoLayout.qk() * f3;
        float f7 = f2 - logoLayout.ql() * f3;
        float f8 = f2 - logoLayout.qj() * f3;
        float f9 = f6 - f5;
        float f10 = f8 - f7;
        if (f9 <= 0.0f || f10 <= 0.0f) {
            return;
        }
        int n3 = Math.max(0, class_3532.method_15375((float)f5));
        int n4 = Math.min(n - 1, class_3532.method_15386((float)f6));
        int n5 = Math.max(0, class_3532.method_15375((float)f7));
        int n6 = Math.min(n2 - 1, class_3532.method_15386((float)f8));
        class_1011 class_10112 = logoFrame.qd();
        int n7 = class_10112.method_4323();
        for (int i = n5; i <= n6; ++i) {
            float f11 = class_3532.method_15363((float)(((float)i + 0.5f - f7) / f10), (float)0.0f, (float)1.0f);
            float f12 = logoLayout.qp() - f11 * (logoLayout.qp() - logoLayout.qn());
            float f13 = (float)n7 - f12 - 0.5f;
            for (int j = n3; j <= n4; ++j) {
                int n8;
                int n9;
                float f14 = class_3532.method_15363((float)(((float)j + 0.5f - f5) / f9), (float)0.0f, (float)1.0f);
                float f15 = logoLayout.qm() + f14 * (logoLayout.qo() - logoLayout.qm()) - 0.5f;
                float f16 = MainMenuLogoRenderer.a(class_10112, f15, f13);
                float f17 = class_3532.method_15363((float)((f16 - 0.5f) * f4 + 0.5f), (float)0.0f, (float)1.0f);
                if (f17 <= 0.002f || (byArray[n9 = i * n + j] & 0xFF) >= (n8 = Math.round(f17 * 255.0f))) continue;
                byArray[n9] = (byte)n8;
            }
        }
    }

    private static float a(class_1011 class_10112, float f, float f2) {
        int n = class_3532.method_15375((float)f);
        int n2 = class_3532.method_15375((float)f2);
        float f3 = f - (float)n;
        float f4 = f2 - (float)n2;
        int n3 = MainMenuLogoRenderer.a(class_10112, n, n2);
        int n4 = MainMenuLogoRenderer.a(class_10112, n + 1, n2);
        int n5 = MainMenuLogoRenderer.a(class_10112, n, n2 + 1);
        int n6 = MainMenuLogoRenderer.a(class_10112, n + 1, n2 + 1);
        float f5 = MainMenuLogoRenderer.a(f3, f4, n3 >> 16 & 0xFF, n4 >> 16 & 0xFF, n5 >> 16 & 0xFF, n6 >> 16 & 0xFF);
        float f6 = MainMenuLogoRenderer.a(f3, f4, n3 >> 8 & 0xFF, n4 >> 8 & 0xFF, n5 >> 8 & 0xFF, n6 >> 8 & 0xFF);
        float f7 = MainMenuLogoRenderer.a(f3, f4, n3 & 0xFF, n4 & 0xFF, n5 & 0xFF, n6 & 0xFF);
        return MainMenuLogoRenderer.j(f5, f6, f7) / 255.0f;
    }

    private static int a(class_1011 class_10112, int n, int n2) {
        int n3 = class_3532.method_15340((int)n, (int)0, (int)(class_10112.method_4307() - 1));
        int n4 = class_3532.method_15340((int)n2, (int)0, (int)(class_10112.method_4323() - 1));
        return class_10112.method_61940(n3, n4);
    }

    private static float a(float f, float f2, int n, int n2, int n3, int n4) {
        float f3 = (float)n + (float)(n2 - n) * f;
        float f4 = (float)n3 + (float)(n4 - n3) * f;
        return f3 + (f4 - f3) * f2;
    }

    private static float j(float f, float f2, float f3) {
        return Math.max(Math.min(f, f2), Math.min(Math.max(f, f2), f3));
    }

    private static LogoState i(int n, float f) {
        int n2 = 256;
        class_1011 class_10112 = new class_1011(n2, n2, true);
        int n3 = n & 0xFFFFFF;
        float f2 = f * 255.0f;
        for (int i = 0; i < n2; ++i) {
            float f3 = ((float)i + 0.5f) / (float)n2 * 2.0f - 1.0f;
            for (int j = 0; j < n2; ++j) {
                float f4 = ((float)j + 0.5f) / (float)n2 * 2.0f - 1.0f;
                float f5 = (float)Math.sqrt(f4 * f4 + f3 * f3);
                float f6 = class_3532.method_15363((float)(1.0f - f5), (float)0.0f, (float)1.0f);
                f6 = f6 * f6 * (3.0f - 2.0f * f6);
                float f7 = f6 * f2 + (MainMenuLogoRenderer.t(j, i) - 0.5f);
                int n4 = class_3532.method_15340((int)Math.round(f7), (int)0, (int)255);
                class_10112.method_61941(j, i, n4 << 24 | n3);
            }
        }
        return MainMenuLogoRenderer.a(class_10112, 0, 0.0f, n2);
    }

    private static float t(int n, int n2) {
        int n3 = n * 374761393 + n2 * 668265263;
        n3 = (n3 ^ n3 >> 13) * 1274126177;
        return (float)((n3 ^ n3 >> 16) & 0xFFFF) / 65535.0f;
    }

    private static LogoState a(class_1011 class_10112, int n, float f, float f2) {
        class_2960 class_29602 = Celebression.id("splash/run_" + adu++);
        class_310.method_1551().method_1531().method_4616(class_29602, (class_1044)new class_1043(class_10112));
        return new LogoState(class_29602, class_10112.method_4307(), class_10112.method_4323(), n, f, f2);
    }

    private static int u(int n, int n2) {
        if (n2 <= 1) {
            return n;
        }
        int n3 = n % n2;
        return n3 == 0 ? n : n + (n2 - n3);
    }

    private static void a(LogoAsset logoAsset, Throwable throwable) {
        if (ads.putIfAbsent(logoAsset, Boolean.TRUE) == null) {
            adq.warn("[splash] \u0448\u0440\u0438\u0444\u0442 {} \u043d\u0435\u0434\u043e\u0441\u0442\u0443\u043f\u0435\u043d, \u0442\u0435\u043a\u0441\u0442 \u043d\u0430 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0435 \u0431\u0443\u0434\u0435\u0442 \u043f\u0440\u043e\u043f\u0443\u0449\u0435\u043d", (Object)logoAsset.adF, (Object)throwable);
        }
    }

    public static enum LogoAsset {
        adB("golos"),
        adC("golos_semi"),
        adD("unbounded"),
        adE("plexmono");
        final String adF;

private LogoAsset(String string2) {
            this.adF = string2;
        }

}

    public static final class LogoState {
        private final class_2960 adR;
        private final int adS;
        private final int adT;
        private final int adU;
        private final float adV;
        private final float adW;

        public LogoState(class_2960 class_29602, int n, int n2, int n3, float f, float f2) {
            this.adR = class_29602;
            this.adS = n;
            this.adT = n2;
            this.adU = n3;
            this.adV = f;
            this.adW = f2;
        }

        @Override
        public final String toString() {
            return "LogoState[" + "id=" + this.adR + ", " + "width=" + this.adS + ", " + "height=" + this.adT + ", " + "pad=" + this.adU + ", " + "baseline=" + this.adV + ", " + "advance=" + this.adW + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.adR, this.adS, this.adT, this.adU, this.adV, this.adW);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LogoState other)) return false;
            return java.util.Objects.deepEquals(this.adR, other.adR)
                && java.util.Objects.deepEquals(this.adS, other.adS)
                && java.util.Objects.deepEquals(this.adT, other.adT)
                && java.util.Objects.deepEquals(this.adU, other.adU)
                && java.util.Objects.deepEquals(this.adV, other.adV)
                && java.util.Objects.deepEquals(this.adW, other.adW);
        }

        public class_2960 id() {
            return this.adR;
        }

        public int qq() {
            return this.adS;
        }

        public int qr() {
            return this.adT;
        }

        public int qs() {
            return this.adU;
        }

        public float qt() {
            return this.adV;
        }

        public float advance() {
            return this.adW;
        }
    }

    static final class LogoFrame {
        private final class_1011 adv;
        private final Map<Integer, LogoLayout> adw;
        private final float adx;
        private final float ady;
        private final float adz;
        private final float adA;

        LogoFrame(class_1011 class_10112, Map<Integer, LogoLayout> map, float f, float f2, float f3, float f4) {
            this.adv = class_10112;
            this.adw = map;
            this.adx = f;
            this.ady = f2;
            this.adz = f3;
            this.adA = f4;
        }

        @Override
        public final String toString() {
            return "LogoFrame[" + "image=" + this.adv + ", " + "glyphs=" + this.adw + ", " + "emPx=" + this.adx + ", " + "distanceRange=" + this.ady + ", " + "ascender=" + this.adz + ", " + "descender=" + this.adA + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.adv, this.adw, this.adx, this.ady, this.adz, this.adA);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LogoFrame other)) return false;
            return java.util.Objects.deepEquals(this.adv, other.adv)
                && java.util.Objects.deepEquals(this.adw, other.adw)
                && java.util.Objects.deepEquals(this.adx, other.adx)
                && java.util.Objects.deepEquals(this.ady, other.ady)
                && java.util.Objects.deepEquals(this.adz, other.adz)
                && java.util.Objects.deepEquals(this.adA, other.adA);
        }

        public class_1011 qd() {
            return this.adv;
        }

        public Map<Integer, LogoLayout> glyphs() {
            return this.adw;
        }

        public float qe() {
            return this.adx;
        }

        public float qf() {
            return this.ady;
        }

        public float ascender() {
            return this.adz;
        }

        public float descender() {
            return this.adA;
        }
    }

    static final class LogoLayout {
        private final float adH;
        private final boolean adI;
        private final float adJ;
        private final float adK;
        private final float adL;
        private final float adM;
        private final float adN;
        private final float adO;
        private final float adP;
        private final float adQ;

        LogoLayout(float f, boolean bl, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            this.adH = f;
            this.adI = bl;
            this.adJ = f2;
            this.adK = f3;
            this.adL = f4;
            this.adM = f5;
            this.adN = f6;
            this.adO = f7;
            this.adP = f8;
            this.adQ = f9;
        }

        @Override
        public final String toString() {
            return "LogoLayout[" + "advance=" + this.adH + ", " + "quad=" + this.adI + ", " + "planeLeft=" + this.adJ + ", " + "planeBottom=" + this.adK + ", " + "planeRight=" + this.adL + ", " + "planeTop=" + this.adM + ", " + "atlasLeft=" + this.adN + ", " + "atlasBottom=" + this.adO + ", " + "atlasRight=" + this.adP + ", " + "atlasTop=" + this.adQ + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.adH, this.adI, this.adJ, this.adK, this.adL, this.adM, this.adN, this.adO, this.adP, this.adQ);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LogoLayout other)) return false;
            return java.util.Objects.deepEquals(this.adH, other.adH)
                && java.util.Objects.deepEquals(this.adI, other.adI)
                && java.util.Objects.deepEquals(this.adJ, other.adJ)
                && java.util.Objects.deepEquals(this.adK, other.adK)
                && java.util.Objects.deepEquals(this.adL, other.adL)
                && java.util.Objects.deepEquals(this.adM, other.adM)
                && java.util.Objects.deepEquals(this.adN, other.adN)
                && java.util.Objects.deepEquals(this.adO, other.adO)
                && java.util.Objects.deepEquals(this.adP, other.adP)
                && java.util.Objects.deepEquals(this.adQ, other.adQ);
        }

        public float advance() {
            return this.adH;
        }

        public boolean qh() {
            return this.adI;
        }

        public float qi() {
            return this.adJ;
        }

        public float qj() {
            return this.adK;
        }

        public float qk() {
            return this.adL;
        }

        public float ql() {
            return this.adM;
        }

        public float qm() {
            return this.adN;
        }

        public float qn() {
            return this.adO;
        }

        public float qo() {
            return this.adP;
        }

        public float qp() {
            return this.adQ;
        }
    }
}
