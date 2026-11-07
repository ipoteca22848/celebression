package ru.method.celebression.manager;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_437;
import net.minecraft.class_490;
import net.minecraft.class_634;
import net.minecraft.class_6903;
import ru.method.celebression.Celebression;
import ru.method.celebression.service.ClientTaskScheduler;
import ru.method.celebression.inventory.InventorySetupModel;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class InventorySetupManager
implements MinecraftClientAccess {
    private static final int SJ = 41;
    private static final int SK = 60;
    private static final int SL = 2;
    private static final int SM = 40;
    private static final List<InventorySetupModel> SN = new ArrayList<InventorySetupModel>();
    private static InventorySetupModel SO;
    private static boolean SP;
    private static boolean SQ;
    private static boolean SR;
    private static List<SetupEntry> SS;
    private static InventorySetupModel ST;
    private static int SU;
    private static boolean SV;
    private static int SW;
    private static long SX;
    private static final int[] SY;
    private static int SZ;
    private static int Ta;
    private static boolean Tb;
    private static boolean Tc;

    private InventorySetupManager() {
    }

    public static List<InventorySetupModel> mi() {
        InventorySetupManager.ensureLoaded();
        return SN;
    }

    public static InventorySetupModel mj() {
        return SO;
    }

    public static void h(InventorySetupModel inventorySetupModel) {
        SO = inventorySetupModel;
        ST = null;
        SX = 0L;
    }

    public static boolean mk() {
        return SV;
    }

    public static boolean ml() {
        return SX != 0L;
    }

    public static InventorySetupModel cc(String string) {
        if (InventorySetupManager.aiG.field_1724 == null) {
            return null;
        }
        InventorySetupManager.ensureLoaded();
        InventorySetupModel inventorySetupModel = null;
        for (InventorySetupModel inventorySetupModel2 : SN) {
            if (inventorySetupModel2.name == null || !inventorySetupModel2.name.equals(string)) continue;
            inventorySetupModel = inventorySetupModel2;
            break;
        }
        if (inventorySetupModel == null) {
            inventorySetupModel = new InventorySetupModel(string);
            SN.add(inventorySetupModel);
        }
        class_1661 class_16612 = InventorySetupManager.aiG.field_1724.method_31548();
        int n = 0;
        for (int i = 0; i < 41; ++i) {
            class_1799 class_17992;
            inventorySetupModel.SH[i] = class_17992 = i < class_16612.method_5439() ? class_16612.method_5438(i).method_7972() : class_1799.field_8037;
            if (class_17992.method_7960()) continue;
            ++n;
        }
        inventorySetupModel.SI = n;
        if (SO == inventorySetupModel) {
            ST = null;
        }
        InventorySetupManager.save();
        return inventorySetupModel;
    }

    public static void i(InventorySetupModel inventorySetupModel) {
        InventorySetupManager.ensureLoaded();
        SN.remove(inventorySetupModel);
        if (SO == inventorySetupModel) {
            SO = null;
            ST = null;
        }
        InventorySetupManager.save();
    }

    public static boolean mm() {
        return SQ;
    }

    public static void w(boolean bl) {
        if (SQ == bl) {
            return;
        }
        SQ = bl;
        InventorySetupManager.save();
    }

    public static boolean mn() {
        return SP;
    }

    public static void x(boolean bl) {
        if (SP == bl) {
            return;
        }
        SP = bl;
        InventorySetupManager.save();
    }

    public static List<SetupEntry> mo() {
        if (SO == null || InventorySetupManager.aiG.field_1724 == null) {
            return List.of();
        }
        int n2 = InventorySetupManager.aiG.field_1724.field_6012;
        if (SO == ST && Math.abs(n2 - SU) < 5) {
            return SS;
        }
        ST = SO;
        SU = n2;
        LinkedHashMap<class_1792, Integer> linkedHashMap = new LinkedHashMap<class_1792, Integer>();
        HashMap<class_1792, class_1799> hashMap = new HashMap<class_1792, class_1799>();
        for (class_1799 class_17992 : InventorySetupManager.SO.SH) {
            if (class_17992 == null || class_17992.method_7960()) continue;
            linkedHashMap.merge(class_17992.method_7909(), class_17992.method_7947(), Integer::sum);
            hashMap.putIfAbsent(class_17992.method_7909(), class_17992);
        }
        class_1661 class_16612 = InventorySetupManager.aiG.field_1724.method_31548();
        for (int i = 0; i < Math.min(class_16612.method_5439(), 41); ++i) {
            class_1799 class_17993 = class_16612.method_5438(i);
            if (class_17993.method_7960()) continue;
            linkedHashMap.computeIfPresent(class_17993.method_7909(), (class_17922, n) -> n - class_17993.method_7947());
        }
        ArrayList<SetupEntry> arrayList = new ArrayList<SetupEntry>();
        linkedHashMap.forEach((class_17922, n) -> {
            if (n > 0) {
                arrayList.add(new SetupEntry((class_1799)hashMap.get(class_17922), (int)n));
            }
        });
        arrayList.sort(Comparator.comparingInt(SetupEntry::my).reversed());
        SS = arrayList;
        return SS;
    }

    public static void mp() {
        if (SO == null || SV || InventorySetupManager.aiG.field_1724 == null) {
            return;
        }
        SV = true;
        SX = 0L;
        SZ = 3;
        Tc = false;
        SW = 60;
        InventorySetupManager.mr();
        ClientTaskScheduler.a(1, InventorySetupManager::mt);
    }

    public static void mq() {
        Tc = true;
        SV = false;
        SZ = 3;
        Ta = 0;
    }

    private static void mr() {
        Ta = 40;
        if (Tb) {
            return;
        }
        Tb = true;
        ClientTaskScheduler.a(1, InventorySetupManager::ms);
    }

    private static void ms() {
        if (Ta-- <= 0 || Tc) {
            Tb = false;
            return;
        }
        if (InventorySetupManager.aiG.field_1724 != null && InventorySetupManager.aiG.field_1755 == null) {
            aiG.method_1507((class_437)new class_490((class_1657)InventorySetupManager.aiG.field_1724));
        }
        ClientTaskScheduler.a(1, InventorySetupManager::ms);
    }

    private static void mt() {
        if (!SV) {
            return;
        }
        if (SO == null || InventorySetupManager.aiG.field_1724 == null || !(InventorySetupManager.aiG.field_1755 instanceof class_490)) {
            SV = false;
            SZ = 3;
            return;
        }
        Ta = 40;
        if (SZ < 3) {
            InventoryUtils.b(SY[SZ++], 0, class_1713.field_7790);
            ClientTaskScheduler.a(2, InventorySetupManager::mt);
            return;
        }
        if (!InventorySetupManager.aiG.field_1724.field_7498.method_34255().method_7960()) {
            SV = false;
            return;
        }
        if (SW-- <= 0) {
            SV = false;
            return;
        }
        if (!InventorySetupManager.mu()) {
            SV = false;
            SX = System.currentTimeMillis();
            return;
        }
        ClientTaskScheduler.a(2, InventorySetupManager::mt);
    }

    private static boolean mu() {
        class_1799 class_17992;
        int n;
        class_1661 class_16612 = InventorySetupManager.aiG.field_1724.method_31548();
        int n2 = Math.min(class_16612.method_5439(), 41);
        boolean[] blArray = new boolean[41];
        for (n = 0; n < n2; ++n) {
            class_17992 = InventorySetupManager.SO.SH[n];
            if (class_17992 == null || class_17992.method_7960() || class_16612.method_5438(n).method_7909() != class_17992.method_7909()) continue;
            blArray[n] = true;
        }
        for (n = 0; n < n2; ++n) {
            class_17992 = InventorySetupManager.SO.SH[n];
            if (class_17992 == null || class_17992.method_7960() || blArray[n] || n >= 36 && n <= 39 && !class_16612.method_5438(n).method_7960() || n >= 36 && n <= 39 && !InventorySetupManager.aiG.field_1724.field_7498.method_7611(InventorySetupManager.ap(n)).method_7680(class_17992)) continue;
            int n3 = -1;
            for (int i = 0; i < n2; ++i) {
                if (i == n || blArray[i] || i >= 36 && i <= 39 && !class_16612.method_5438(n).method_7960() || class_16612.method_5438(i).method_7909() != class_17992.method_7909()) continue;
                n3 = i;
                break;
            }
            if (n3 < 0) continue;
            InventorySetupManager.SY[0] = InventorySetupManager.ap(n3);
            InventorySetupManager.SY[1] = InventorySetupManager.ap(n);
            InventorySetupManager.SY[2] = SY[0];
            SZ = 0;
            return true;
        }
        return false;
    }

    static int ap(int n) {
        if (n <= 8) {
            return 36 + n;
        }
        if (n <= 35) {
            return n;
        }
        if (n <= 39) {
            return 8 - (n - 36);
        }
        return 45;
    }

    private static Path mv() {
        return Celebression.getInstance().getGlobalsDir().toPath().resolve("setups.celebression");
    }

    private static class_6903<JsonElement> mw() {
        class_634 class_6342 = aiG.method_1562();
        return class_6342 == null ? null : class_6342.method_29091().method_57093((DynamicOps)JsonOps.INSTANCE);
    }

    private static void ensureLoaded() {
        if (SR) {
            return;
        }
        class_6903<JsonElement> class_69032 = InventorySetupManager.mw();
        if (class_69032 == null) {
            return;
        }
        Path path = InventorySetupManager.mv();
        if (!Files.exists(path, new LinkOption[0])) {
            SR = true;
            return;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);){
            JsonObject jsonObject = JsonParser.parseReader((Reader)bufferedReader).getAsJsonObject();
            boolean bl = jsonObject.has("greeted") && jsonObject.get("greeted").getAsBoolean();
            boolean bl2 = !jsonObject.has("overlay") || jsonObject.get("overlay").getAsBoolean();
            ArrayList<InventorySetupModel> arrayList = new ArrayList<InventorySetupModel>();
            if (jsonObject.has("setups")) {
                for (JsonElement jsonElement : jsonObject.getAsJsonArray("setups")) {
                    try {
                        arrayList.add(InventorySetupModel.a(class_69032, jsonElement.getAsJsonObject()));
                    }
                    catch (Exception exception) {
                        Celebression.LOGGER.warn("[InventorySetupManager] Skipping broken setup entry", (Throwable)exception);
                    }
                }
            }
            SP = bl;
            SQ = bl2;
            SN.clear();
            SN.addAll(arrayList);
            SR = true;
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[InventorySetupManager] Error loading setups:", (Throwable)exception);
        }
    }

    private static void save() {
        class_6903<JsonElement> class_69032 = InventorySetupManager.mw();
        if (class_69032 == null) {
            return;
        }
        InventorySetupManager.ensureLoaded();
        if (!SR) {
            return;
        }
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("greeted", Boolean.valueOf(SP));
            jsonObject.addProperty("overlay", Boolean.valueOf(SQ));
            JsonArray jsonArray = new JsonArray();
            for (InventorySetupModel object2 : SN) {
                jsonArray.add((JsonElement)object2.a(class_69032));
            }
            jsonObject.add("setups", (JsonElement)jsonArray);
            String string = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonObject);
            Path path = InventorySetupManager.mv();
            Path path2 = path.getParent();
            if (path2 != null) {
                Files.createDirectories(path2, new FileAttribute[0]);
            }
            Path path3 = path.resolveSibling("setups.celebression.tmp");
            Files.writeString(path3, (CharSequence)string, StandardCharsets.UTF_8, new OpenOption[0]);
            try {
                Files.move(path3, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            }
            catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
                Files.move(path3, path, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[InventorySetupManager] Error saving setups:", (Throwable)exception);
        }
    }

    static {
        SQ = true;
        SS = List.of();
        SU = Integer.MIN_VALUE;
        SY = new int[3];
        SZ = 3;
    }

    public static final class SetupEntry {
        private final class_1799 Td;
        private final int Te;

        public SetupEntry(class_1799 class_17992, int n) {
            this.Td = class_17992;
            this.Te = n;
        }

        @Override
        public final String toString() {
            return "SetupEntry[" + "sample=" + this.Td + ", " + "count=" + this.Te + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Td, this.Te);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof SetupEntry other)) return false;
            return java.util.Objects.deepEquals(this.Td, other.Td)
                && java.util.Objects.deepEquals(this.Te, other.Te);
        }

        public class_1799 mx() {
            return this.Td;
        }

        public int my() {
            return this.Te;
        }
    }
}

