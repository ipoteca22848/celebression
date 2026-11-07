package ru.method.celebression.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1796;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2371;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_7923;

public final class CooldownEntry {
    private static final Field ajS = CooldownEntry.a(class_1796.class, "entries", "cooldowns", "field_8024");
    private static final Field ajT = CooldownEntry.a(class_1796.class, "tick", "field_8025");
    private static final Field ajU;
    private static final Field ajV;
    private static final Pattern ajW;
    private static final Pattern ajX;

    private CooldownEntry() {
    }

    public static List<CooldownCategory> a(class_310 class_3102, float f) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return Collections.emptyList();
        }
        class_1796 class_17962 = class_3102.field_1724.method_7357();
        Map<?, ?> map = CooldownEntry.a(class_17962);
        if (map == null || map.isEmpty()) {
            return CooldownEntry.a((class_1657)class_3102.field_1724, f);
        }
        int n = CooldownEntry.b(class_17962);
        ArrayList<CooldownCategory> arrayList = new ArrayList<CooldownCategory>(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            class_2960 class_29602;
            class_1792 class_17922;
            Object object;
            Object obj = entry.getKey();
            Object obj2 = entry.getValue();
            if (obj2 == null) continue;
            if (obj instanceof class_1792) {
                class_17922 = (class_1792)obj;
            } else if (!(obj instanceof class_2960) || (class_17922 = (class_1792)class_7923.field_41178.method_63535(class_29602 = (class_2960)obj)) == class_1802.field_8162 && !class_7923.field_41178.method_10250(class_29602)) continue;
            if (class_17922 == null || class_17922 == class_1802.field_8162 || (object = CooldownEntry.a((class_1657)class_3102.field_1724, class_17922, obj2, n, f)) == null) continue;
            arrayList.add((CooldownCategory)object);
        }
        if (arrayList.isEmpty()) {
            return CooldownEntry.a((class_1657)class_3102.field_1724, f);
        }
        arrayList.sort(Comparator.comparingDouble(CooldownCategory::rK).reversed());
        return arrayList;
    }

    private static List<CooldownCategory> a(class_1657 class_16572, float f) {
        if (class_16572 == null) {
            return Collections.emptyList();
        }
        ArrayList<CooldownCategory> arrayList = new ArrayList<CooldownCategory>();
        LinkedHashSet<class_1792> linkedHashSet = new LinkedHashSet<class_1792>();
        class_1796 class_17962 = class_16572.method_7357();
        class_2371<class_1799> class_23712 = class_16572.method_31548().field_7547;
        for (class_1799 class_17992 : class_23712) {
            CooldownEntry.a(arrayList, class_16572, class_17992, class_17962, f, linkedHashSet);
        }
        CooldownEntry.a(arrayList, class_16572, class_16572.method_6079(), class_17962, f, linkedHashSet);
        arrayList.sort(Comparator.comparingDouble(CooldownCategory::rK).reversed());
        return arrayList;
    }

    private static void a(List<CooldownCategory> list, class_1657 class_16572, class_1799 class_17992, class_1796 class_17962, float f, Set<class_1792> set) {
        if (class_17992.method_7960()) {
            return;
        }
        float f2 = class_17962.method_7905(class_17992, f);
        if (f2 <= 0.0f) {
            return;
        }
        class_1792 class_17922 = class_17992.method_7909();
        if (!set.add(class_17922)) {
            return;
        }
        float f3 = f2 * 20.0f;
        String string = CooldownEntry.a(class_17992, class_17922);
        list.add(new CooldownCategory(class_17992.method_7972(), string, f2, f3, 20.0f));
    }

    private static Map<?, ?> a(class_1796 class_17962) {
        if (ajS == null) {
            return null;
        }
        try {
            Object object = ajS.get(class_17962);
            if (object instanceof Map) {
                Map map = (Map)object;
                return map;
            }
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        return null;
    }

    private static int b(class_1796 class_17962) {
        if (ajT != null) {
            try {
                return ajT.getInt(class_17962);
            }
            catch (IllegalAccessException illegalAccessException) {
            }
        }
        return 0;
    }

    private static CooldownCategory a(class_1657 class_16572, class_1792 class_17922, Object object, int n, float f) {
        if (ajU == null || ajV == null) {
            return null;
        }
        try {
            int n2 = ajU.getInt(object);
            int n3 = ajV.getInt(object);
            float f2 = Math.max(1.0f, (float)(n3 - n2));
            float f3 = Math.max(0.0f, (float)n3 - ((float)n + f));
            float f4 = class_3532.method_15363((float)(f3 / f2), (float)0.0f, (float)1.0f);
            if (f4 <= 0.0f) {
                return null;
            }
            class_1799 class_17992 = CooldownEntry.a(class_16572, class_17922);
            String string = CooldownEntry.a(class_17992, class_17922);
            return new CooldownCategory(class_17992, string, f4, f3, f2);
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    private static class_1799 a(class_1657 class_16572, class_1792 class_17922) {
        if (class_16572 == null) {
            return class_17922.method_7854();
        }
        class_1799 class_17992 = class_16572.method_6047();
        if (class_17992.method_31574(class_17922)) {
            return class_17992.method_7972();
        }
        class_1799 class_17993 = class_16572.method_6079();
        if (class_17993.method_31574(class_17922)) {
            return class_17993.method_7972();
        }
        class_2371<class_1799> class_23712 = class_16572.method_31548().field_7547;
        for (class_1799 class_17994 : class_23712) {
            if (!class_17994.method_31574(class_17922)) continue;
            return class_17994.method_7972();
        }
        return class_17922.method_7854();
    }

    public static List<CooldownCategory> rG() {
        ArrayList<CooldownCategory> arrayList = new ArrayList<CooldownCategory>();
        arrayList.add(CooldownEntry.a(class_1802.field_8634, 320, 0.85f));
        arrayList.add(CooldownEntry.a(class_1802.field_8233, 400, 0.55f));
        arrayList.add(CooldownEntry.a(class_1802.field_8463, 80, 0.25f));
        return arrayList;
    }

    private static CooldownCategory a(class_1792 class_17922, int n, float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        float f3 = (float)n * f2;
        class_1799 class_17992 = new class_1799((class_1935)class_17922);
        String string = CooldownEntry.a(class_17992, class_17922);
        return new CooldownCategory(class_17992, string, f2, f3, n);
    }

    private static String a(class_1799 class_17992, class_1792 class_17922) {
        String string = class_17992 != null && !class_17992.method_7960() ? CooldownEntry.i(class_17992.method_7964()) : (class_17922 != null ? CooldownEntry.i(class_17922.method_63680()) : "");
        return CooldownEntry.dc(CooldownEntry.db(string));
    }

    private static String db(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= '\ue000' && c <= '\uf8ff') continue;
            stringBuilder.append(c);
        }
        String string2 = ajX.matcher(stringBuilder.toString()).replaceAll(" ").trim();
        Matcher matcher = ajW.matcher(string2);
        if (matcher.matches()) {
            string2 = matcher.group(2).trim();
        }
        return string2;
    }

    private static String i(class_2561 class_25612) {
        if (class_25612 == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        class_25612.method_27658((class_25832, string) -> {
            class_2960 class_29602 = class_25832.method_27708();
            if (class_29602 == null || class_2583.field_24359.equals((Object)class_29602)) {
                stringBuilder.append(string);
            }
            return Optional.empty();
        }, class_2583.field_24360);
        return ajX.matcher(stringBuilder.toString()).replaceAll(" ").trim();
    }

    private static String dc(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim();
        if (string2.length() >= 2 && string2.startsWith("[") && string2.endsWith("]")) {
            return string2.substring(1, string2.length() - 1).trim();
        }
        return string2.replace("[", "").replace("]", "");
    }

    private static Field a(Class<?> clazz, String ... stringArray) {
        for (String string : stringArray) {
            if (string == null || string.isEmpty()) continue;
            try {
                Field field = clazz.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
            }
        }
        return null;
    }

    static {
        Class<?> clazz = null;
        Field field = null;
        Field field2 = null;
        try {
            clazz = Class.forName("net.minecraft.entity.player.ItemCooldownManager$Entry");
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                clazz = Class.forName("net.minecraft.class_1796$class_1797");
            }
            catch (ClassNotFoundException classNotFoundException2) {
                clazz = null;
            }
        }
        if (clazz != null) {
            field = CooldownEntry.a(clazz, "startTick", "startTime", "comp_3083");
            field2 = CooldownEntry.a(clazz, "endTick", "endTime", "comp_3084");
        }
        ajU = field;
        ajV = field2;
        ajW = Pattern.compile("^(\\S{1,8})\\s+(.+?)\\s+\\1$");
        ajX = Pattern.compile("\\s{2,}");
    }

    public static final class CooldownCategory {
        private final class_1799 ajY;
        private final String ajZ;
        private final float aka;
        private final float akb;
        private final float akc;

        public CooldownCategory(class_1799 class_17992, String string, float f, float f2, float f3) {
            this.ajY = class_17992;
            this.ajZ = string;
            this.aka = f;
            this.akb = f2;
            this.akc = f3;
        }

        public String rH() {
            int n = (int)Math.ceil(Math.max(0.0f, this.akb) / 20.0f);
            int n2 = n / 60;
            int n3 = n % 60;
            return String.format("%d:%02d", n2, n3);
        }

        public int rI() {
            return Math.round(this.aka * 100.0f);
        }

        @Override
        public final String toString() {
            return "CooldownCategory[" + "stack=" + this.ajY + ", " + "displayName=" + this.ajZ + ", " + "progress=" + this.aka + ", " + "remainingTicks=" + this.akb + ", " + "totalTicks=" + this.akc + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ajY, this.ajZ, this.aka, this.akb, this.akc);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof CooldownCategory other)) return false;
            return java.util.Objects.deepEquals(this.ajY, other.ajY)
                && java.util.Objects.deepEquals(this.ajZ, other.ajZ)
                && java.util.Objects.deepEquals(this.aka, other.aka)
                && java.util.Objects.deepEquals(this.akb, other.akb)
                && java.util.Objects.deepEquals(this.akc, other.akc);
        }

        public class_1799 stack() {
            return this.ajY;
        }

        public String rJ() {
            return this.ajZ;
        }

        public float progress() {
            return this.aka;
        }

        public float rK() {
            return this.akb;
        }

        public float rL() {
            return this.akc;
        }
    }
}
