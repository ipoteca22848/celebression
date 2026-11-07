package ru.method.celebression.inventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import net.minecraft.class_1799;
import net.minecraft.class_6903;

public final class InventorySetupModel {
    public static final int SG = 41;
    public String name;
    public final class_1799[] SH = new class_1799[41];
    public int SI;

    public InventorySetupModel() {
        Arrays.fill(this.SH, class_1799.field_8037);
    }

    public InventorySetupModel(String string) {
        this();
        this.name = string;
    }

    public JsonObject a(class_6903<JsonElement> class_69032) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("count", (Number)this.SI);
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < 41; ++i) {
            JsonElement jsonElement;
            class_1799 class_17992 = this.SH[i];
            if (class_17992 == null || class_17992.method_7960() || (jsonElement = class_1799.field_24671.encodeStart(class_69032, class_17992).result().orElse(null)) == null) continue;
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("slot", (Number)i);
            jsonObject2.add("item", jsonElement);
            jsonArray.add((JsonElement)jsonObject2);
        }
        jsonObject.add("slots", (JsonElement)jsonArray);
        return jsonObject;
    }

    public static InventorySetupModel a(class_6903<JsonElement> class_69032, JsonObject jsonObject) {
        InventorySetupModel inventorySetupModel = new InventorySetupModel(jsonObject.has("name") ? jsonObject.get("name").getAsString() : "inv");
        int n = inventorySetupModel.SI = jsonObject.has("count") ? jsonObject.get("count").getAsInt() : 0;
        if (jsonObject.has("slots")) {
            for (JsonElement jsonElement : jsonObject.getAsJsonArray("slots")) {
                int n2;
                JsonObject jsonObject2;
                if (!jsonElement.isJsonObject() || !(jsonObject2 = jsonElement.getAsJsonObject()).has("slot") || !jsonObject2.has("item") || (n2 = jsonObject2.get("slot").getAsInt()) < 0 || n2 >= 41) continue;
                inventorySetupModel.SH[n2] = class_1799.field_24671.parse(class_69032, jsonObject2.get("item")).result().orElse(class_1799.field_8037);
            }
        }
        return inventorySetupModel;
    }
}
