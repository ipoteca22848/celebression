package ru.method.celebression.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_310;
import net.minecraft.class_437;

public final class IasIntegration {
    private static final boolean LG;
    private static Field LH;
    private static Method LI;
    private static Method LJ;
    private static Method LK;
    private static Method LL;
    private static Method LM;
    private static Method LN;
    private static Method LO;
    private static Method LP;
    private static Method LQ;
    private static Method LR;
    private static Method LS;
    private static Method LT;
    private static Constructor<?> LU;
    private static Constructor<?> LV;
    private static Constructor<?> LW;
    private static Constructor<?> LX;

    private IasIntegration() {
    }

    public static boolean kD() {
        return LG;
    }

    private static class_310 kE() {
        return class_310.method_1551();
    }

    public static boolean kF() {
        class_310 class_3102 = IasIntegration.kE();
        return class_3102 != null && (class_3102.field_1687 != null || class_3102.field_1724 != null);
    }

    public static UUID kG() {
        try {
            class_310 class_3102 = IasIntegration.kE();
            return class_3102 != null && class_3102.method_1548() != null ? class_3102.method_1548().method_44717() : null;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static String kH() {
        try {
            class_310 class_3102 = IasIntegration.kE();
            return class_3102 != null && class_3102.method_1548() != null ? class_3102.method_1548().method_1676() : null;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static List<IntegrationState> kI() {
        ArrayList<IntegrationState> arrayList = new ArrayList<IntegrationState>();
        if (!LG) {
            return arrayList;
        }
        try {
            List list = (List)LH.get(null);
            if (list == null) {
                return arrayList;
            }
            for (Object e : list) {
                if (e == null) continue;
                try {
                    arrayList.add(new IntegrationState(e, (UUID)LI.invoke(e, new Object[0]), (String)LJ.invoke(e, new Object[0]), (Boolean)LK.invoke(e, new Object[0]), (Boolean)LL.invoke(e, new Object[0]), (UUID)LM.invoke(e, new Object[0]), (String)LN.invoke(e, new Object[0])));
                }
                catch (Throwable throwable) {}
            }
        }
        catch (Throwable throwable) {
        }
        return arrayList;
    }

    public static void a(IntegrationState integrationState, class_437 class_4372) {
        if (!LG || integrationState == null || IasIntegration.kF()) {
            return;
        }
        if (!integrationState.kL()) {
            IasIntegration.a(integrationState.name(), class_4372);
            return;
        }
        try {
            class_310 class_3102 = IasIntegration.kE();
            if (class_3102 == null) {
                return;
            }
            Object obj = LW.newInstance(class_4372);
            class_3102.method_1507((class_437)obj);
            Executor executor = (Executor)LR.invoke(null, new Object[0]);
            executor.execute(() -> {
                try {
                    LO.invoke(integrationState.kJ(), obj, null);
                }
                catch (Throwable throwable) {
                }
            });
        }
        catch (Throwable throwable) {
            IasIntegration.a(class_4372);
        }
    }

    public static void a(String string, class_437 class_4372) {
        if (!LG || string == null || IasIntegration.kF()) {
            return;
        }
        try {
            class_310 class_3102 = IasIntegration.kE();
            if (class_3102 == null) {
                return;
            }
            UUID uUID = (UUID)LT.invoke(null, string);
            Object obj = LV.newInstance(string, uUID, "ias:offline", false);
            Object object = LS.invoke(null, class_3102, obj);
            if (object instanceof CompletableFuture) {
                CompletableFuture completableFuture = (CompletableFuture)object;
                completableFuture.thenRunAsync(() -> class_3102.method_1507(class_4372), (Executor)class_3102);
            } else {
                class_3102.execute(() -> class_3102.method_1507(class_4372));
            }
        }
        catch (Throwable throwable) {
        }
    }

    public static boolean bY(String string) {
        if (!LG || !IasIntegration.bZ(string)) {
            return false;
        }
        try {
            Object obj = LU.newInstance(string, null);
            List list = (List)LH.get(null);
            list.removeIf(Predicate.isEqual(obj));
            list.add(obj);
            LQ.invoke(null, new Object[0]);
            LP.invoke(null, new Object[0]);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static boolean a(IntegrationState integrationState, String string) {
        if (!LG || integrationState == null || integrationState.kL() || !IasIntegration.bZ(string)) {
            return false;
        }
        try {
            List list = (List)LH.get(null);
            list.remove(integrationState.kJ());
            Object obj = LU.newInstance(string, null);
            list.removeIf(Predicate.isEqual(obj));
            list.add(obj);
            LQ.invoke(null, new Object[0]);
            LP.invoke(null, new Object[0]);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static boolean c(IntegrationState integrationState) {
        if (!LG || integrationState == null) {
            return false;
        }
        try {
            List list = (List)LH.get(null);
            list.remove(integrationState.kJ());
            LQ.invoke(null, new Object[0]);
            LP.invoke(null, new Object[0]);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static void a(class_437 class_4372) {
        if (!LG) {
            return;
        }
        try {
            class_310 class_3102 = IasIntegration.kE();
            if (class_3102 == null) {
                return;
            }
            class_3102.method_1507((class_437)LX.newInstance(class_4372));
        }
        catch (Throwable throwable) {
        }
    }

    private static boolean bZ(String string) {
        if (string == null) {
            return false;
        }
        int n = string.length();
        if (n < 3 || n > 16) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            boolean bl;
            char c = string.charAt(i);
            boolean bl2 = bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_';
            if (bl) continue;
            return false;
        }
        return true;
    }

    static {
        boolean bl = false;
        try {
            if (FabricLoader.getInstance().isModLoaded("ias")) {
                ClassLoader classLoader = IasIntegration.class.getClassLoader();
                Class<?> clazz = Class.forName("ru.vidtu.ias.config.IASStorage", true, classLoader);
                Class<?> clazz2 = Class.forName("ru.vidtu.ias.account.Account", true, classLoader);
                Class<?> clazz3 = Class.forName("ru.vidtu.ias.account.OfflineAccount", true, classLoader);
                Class<?> clazz4 = Class.forName("ru.vidtu.ias.IAS", true, classLoader);
                Class<?> clazz5 = Class.forName("ru.vidtu.ias.IASMinecraft", true, classLoader);
                Class<?> clazz6 = Class.forName("ru.vidtu.ias.auth.LoginData", true, classLoader);
                Class<?> clazz7 = Class.forName("ru.vidtu.ias.auth.handlers.LoginHandler", true, classLoader);
                Class<?> clazz8 = Class.forName("ru.vidtu.ias.screen.LoginPopupScreen", true, classLoader);
                Class<?> clazz9 = Class.forName("ru.vidtu.ias.screen.AccountScreen", true, classLoader);
                LH = clazz.getField("ACCOUNTS");
                LI = clazz2.getMethod("uuid", new Class[0]);
                LJ = clazz2.getMethod("name", new Class[0]);
                LK = clazz2.getMethod("canLogin", new Class[0]);
                LL = clazz2.getMethod("insecure", new Class[0]);
                LM = clazz2.getMethod("skin", new Class[0]);
                LN = clazz2.getMethod("type", new Class[0]);
                LO = clazz2.getMethod("login", clazz7, Runnable.class);
                LP = clazz4.getMethod("saveStorage", new Class[0]);
                LQ = clazz4.getMethod("disclaimersStorage", new Class[0]);
                LR = clazz4.getMethod("executor", new Class[0]);
                LS = clazz5.getMethod("account", class_310.class, clazz6);
                LT = clazz3.getMethod("uuid", String.class);
                LU = clazz3.getConstructor(String.class, UUID.class);
                LV = clazz6.getConstructor(String.class, UUID.class, String.class, Boolean.TYPE);
                LW = clazz8.getDeclaredConstructor(class_437.class);
                LW.setAccessible(true);
                LX = clazz9.getConstructor(class_437.class);
                bl = true;
            }
        }
        catch (Throwable throwable) {
            bl = false;
        }
        LG = bl;
    }

    public static final class IntegrationState {
        private final Object LY;
        private final UUID LZ;
        private final String Ma;
        private final boolean Mb;
        private final boolean Mc;
        private final UUID Md;
        private final String Me;

        public IntegrationState(Object object, UUID uUID, String string, boolean bl, boolean bl2, UUID uUID2, String string2) {
            this.LY = object;
            this.LZ = uUID;
            this.Ma = string;
            this.Mb = bl;
            this.Mc = bl2;
            this.Md = uUID2;
            this.Me = string2;
        }

        @Override
        public final String toString() {
            return "IntegrationState[" + "handle=" + this.LY + ", " + "uuid=" + this.LZ + ", " + "name=" + this.Ma + ", " + "canLogin=" + this.Mb + ", " + "insecure=" + this.Mc + ", " + "skin=" + this.Md + ", " + "type=" + this.Me + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.LY, this.LZ, this.Ma, this.Mb, this.Mc, this.Md, this.Me);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof IntegrationState other)) return false;
            return java.util.Objects.deepEquals(this.LY, other.LY)
                && java.util.Objects.deepEquals(this.LZ, other.LZ)
                && java.util.Objects.deepEquals(this.Ma, other.Ma)
                && java.util.Objects.deepEquals(this.Mb, other.Mb)
                && java.util.Objects.deepEquals(this.Mc, other.Mc)
                && java.util.Objects.deepEquals(this.Md, other.Md)
                && java.util.Objects.deepEquals(this.Me, other.Me);
        }

        public Object kJ() {
            return this.LY;
        }

        public UUID kK() {
            return this.LZ;
        }

        public String name() {
            return this.Ma;
        }

        public boolean kL() {
            return this.Mb;
        }

        public boolean kM() {
            return this.Mc;
        }

        public UUID kN() {
            return this.Md;
        }

        public String type() {
            return this.Me;
        }
    }
}

