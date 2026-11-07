package ru.method.celebression.punchy.client.debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import ru.method.celebression.punchy.client.debug.PunchyDebug;

public class PunchyTuningIO {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void writeDefaultFromResources(class_3300 class_33002) {
        if (class_33002 != null) {
            class_2960 class_29602 = class_2960.method_60655((String)"punchy", (String)"tuning/default.json");
            try {
                byte[] byArray = class_33002.getResourceOrThrow(class_29602).method_14482().readAllBytes();
                Path path = PunchyTuningIO.getDefaultPath();
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                Files.write(path, byArray, new OpenOption[0]);
            }
            catch (IOException ignored) {
            }
        }
    }

    public static void saveSession() {
        try {
            PunchyTuningIO.writeJson(PunchyTuningIO.getSessionPath(), PunchyTuningIO.buildJson());
        }
        catch (IOException ignored) {
        }
    }

    public static void loadDefaultToDebug() {
        Path path = PunchyTuningIO.getDefaultPath();
        if (Files.exists(path, new LinkOption[0])) {
            try {
                String string = Files.readString(path, StandardCharsets.UTF_8);
                JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
                PunchyTuningIO.applyJson(jsonObject);
            }
            catch (Exception ignored) {
            }
        }
    }

    public static Path getSessionPath() {
        return PunchyTuningIO.getGameDir().resolve("config").resolve("punchy").resolve("punchy_tuning_session.json");
    }

    public static Path getDefaultPath() {
        return PunchyTuningIO.getGameDir().resolve("config").resolve("punchy").resolve("punchy_tuning_default.json");
    }

    private static Path getGameDir() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102.field_1697.toPath();
    }

    private static void writeJson(Path path, JsonObject jsonObject) throws IOException {
        Files.createDirectories(path.getParent(), new FileAttribute[0]);
        byte[] byArray = GSON.toJson((JsonElement)jsonObject).getBytes(StandardCharsets.UTF_8);
        Files.write(path, byArray, new OpenOption[0]);
    }

    private static void applyJson(JsonObject jsonObject) {
        if (jsonObject != null) {
            PunchyTuningIO.applySection(jsonObject.getAsJsonObject("global"), Section.GLOBAL);
            PunchyTuningIO.applySection(jsonObject.getAsJsonObject("arm_right"), Section.ARM_RIGHT);
            PunchyTuningIO.applySection(jsonObject.getAsJsonObject("arm_left"), Section.ARM_LEFT);
            PunchyTuningIO.applySection(jsonObject.getAsJsonObject("item_right"), Section.ITEM_RIGHT);
            PunchyTuningIO.applySection(jsonObject.getAsJsonObject("item_left"), Section.ITEM_LEFT);
        }
    }

    private static void applySection(JsonObject jsonObject, Section section) {
        if (jsonObject != null) {
            float f = PunchyTuningIO.getFloat(jsonObject, "pos_x", 0.0f);
            float f2 = PunchyTuningIO.getFloat(jsonObject, "pos_y", 0.0f);
            float f3 = PunchyTuningIO.getFloat(jsonObject, "pos_z", 0.0f);
            float f4 = PunchyTuningIO.getFloat(jsonObject, "rot_x", 0.0f);
            float f5 = PunchyTuningIO.getFloat(jsonObject, "rot_y", 0.0f);
            float f6 = PunchyTuningIO.getFloat(jsonObject, "rot_z", 0.0f);
            float f7 = PunchyTuningIO.getFloat(jsonObject, "scale", 1.0f);
            float f8 = PunchyTuningIO.getFloat(jsonObject, "pivot_x", 0.0f);
            float f9 = PunchyTuningIO.getFloat(jsonObject, "pivot_y", 0.0f);
            float f10 = PunchyTuningIO.getFloat(jsonObject, "pivot_z", 0.0f);
            switch (section.ordinal()) {
                case 0: {
                    PunchyDebug.POS_X = f;
                    PunchyDebug.POS_Y = f2;
                    PunchyDebug.POS_Z = f3;
                    PunchyDebug.ROT_X = f4;
                    PunchyDebug.ROT_Y = f5;
                    PunchyDebug.ROT_Z = f6;
                    PunchyDebug.SCALE = f7;
                    break;
                }
                case 1: {
                    PunchyDebug.ARM_R_POS_X = f;
                    PunchyDebug.ARM_R_POS_Y = f2;
                    PunchyDebug.ARM_R_POS_Z = f3;
                    PunchyDebug.ARM_R_ROT_X = f4;
                    PunchyDebug.ARM_R_ROT_Y = f5;
                    PunchyDebug.ARM_R_ROT_Z = f6;
                    PunchyDebug.ARM_R_SCALE_X = f7;
                    PunchyDebug.ARM_R_SCALE_Y = f7;
                    PunchyDebug.ARM_R_SCALE_Z = f7;
                    break;
                }
                case 2: {
                    PunchyDebug.ARM_L_POS_X = f;
                    PunchyDebug.ARM_L_POS_Y = f2;
                    PunchyDebug.ARM_L_POS_Z = f3;
                    PunchyDebug.ARM_L_ROT_X = f4;
                    PunchyDebug.ARM_L_ROT_Y = f5;
                    PunchyDebug.ARM_L_ROT_Z = f6;
                    PunchyDebug.ARM_L_SCALE_X = f7;
                    PunchyDebug.ARM_L_SCALE_Y = f7;
                    PunchyDebug.ARM_L_SCALE_Z = f7;
                    break;
                }
                case 3: {
                    PunchyDebug.ITEM_R_POS_X = f;
                    PunchyDebug.ITEM_R_POS_Y = f2;
                    PunchyDebug.ITEM_R_POS_Z = f3;
                    PunchyDebug.ITEM_R_ROT_X = f4;
                    PunchyDebug.ITEM_R_ROT_Y = f5;
                    PunchyDebug.ITEM_R_ROT_Z = f6;
                    PunchyDebug.ITEM_R_SCALE_X = f7;
                    PunchyDebug.ITEM_R_SCALE_Y = f7;
                    PunchyDebug.ITEM_R_SCALE_Z = f7;
                    PunchyDebug.ITEM_R_PIVOT_X = f8;
                    PunchyDebug.ITEM_R_PIVOT_Y = f9;
                    PunchyDebug.ITEM_R_PIVOT_Z = f10;
                    break;
                }
                case 4: {
                    PunchyDebug.ITEM_L_POS_X = f;
                    PunchyDebug.ITEM_L_POS_Y = f2;
                    PunchyDebug.ITEM_L_POS_Z = f3;
                    PunchyDebug.ITEM_L_ROT_X = f4;
                    PunchyDebug.ITEM_L_ROT_Y = f5;
                    PunchyDebug.ITEM_L_ROT_Z = f6;
                    PunchyDebug.ITEM_L_SCALE_X = f7;
                    PunchyDebug.ITEM_L_SCALE_Y = f7;
                    PunchyDebug.ITEM_L_SCALE_Z = f7;
                    PunchyDebug.ITEM_L_PIVOT_X = f8;
                    PunchyDebug.ITEM_L_PIVOT_Y = f9;
                    PunchyDebug.ITEM_L_PIVOT_Z = f10;
                }
            }
        }
    }

    private static float getFloat(JsonObject jsonObject, String string, float f) {
        try {
            if (jsonObject.has(string)) {
                return jsonObject.get(string).getAsFloat();
            }
        }
        catch (Exception exception) {
        }
        return f;
    }

    private static JsonObject buildJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("global", (JsonElement)PunchyTuningIO.globalSection());
        jsonObject.add("arm_right", (JsonElement)PunchyTuningIO.armSection(true));
        jsonObject.add("arm_left", (JsonElement)PunchyTuningIO.armSection(false));
        jsonObject.add("item_right", (JsonElement)PunchyTuningIO.itemSection(true));
        jsonObject.add("item_left", (JsonElement)PunchyTuningIO.itemSection(false));
        return jsonObject;
    }

    private static JsonObject globalSection() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pos_x", (Number)Float.valueOf(PunchyDebug.POS_X));
        jsonObject.addProperty("pos_y", (Number)Float.valueOf(PunchyDebug.POS_Y));
        jsonObject.addProperty("pos_z", (Number)Float.valueOf(PunchyDebug.POS_Z));
        jsonObject.addProperty("rot_x", (Number)Float.valueOf(PunchyDebug.ROT_X));
        jsonObject.addProperty("rot_y", (Number)Float.valueOf(PunchyDebug.ROT_Y));
        jsonObject.addProperty("rot_z", (Number)Float.valueOf(PunchyDebug.ROT_Z));
        jsonObject.addProperty("scale", (Number)Float.valueOf(PunchyDebug.SCALE));
        return jsonObject;
    }

    private static JsonObject armSection(boolean bl) {
        JsonObject jsonObject = new JsonObject();
        if (bl) {
            jsonObject.addProperty("pos_x", (Number)Float.valueOf(PunchyDebug.ARM_R_POS_X));
            jsonObject.addProperty("pos_y", (Number)Float.valueOf(PunchyDebug.ARM_R_POS_Y));
            jsonObject.addProperty("pos_z", (Number)Float.valueOf(PunchyDebug.ARM_R_POS_Z));
            jsonObject.addProperty("rot_x", (Number)Float.valueOf(PunchyDebug.ARM_R_ROT_X));
            jsonObject.addProperty("rot_y", (Number)Float.valueOf(PunchyDebug.ARM_R_ROT_Y));
            jsonObject.addProperty("rot_z", (Number)Float.valueOf(PunchyDebug.ARM_R_ROT_Z));
            jsonObject.addProperty("scale", (Number)Float.valueOf(PunchyDebug.ARM_R_SCALE_X));
        } else {
            jsonObject.addProperty("pos_x", (Number)Float.valueOf(PunchyDebug.ARM_L_POS_X));
            jsonObject.addProperty("pos_y", (Number)Float.valueOf(PunchyDebug.ARM_L_POS_Y));
            jsonObject.addProperty("pos_z", (Number)Float.valueOf(PunchyDebug.ARM_L_POS_Z));
            jsonObject.addProperty("rot_x", (Number)Float.valueOf(PunchyDebug.ARM_L_ROT_X));
            jsonObject.addProperty("rot_y", (Number)Float.valueOf(PunchyDebug.ARM_L_ROT_Y));
            jsonObject.addProperty("rot_z", (Number)Float.valueOf(PunchyDebug.ARM_L_ROT_Z));
            jsonObject.addProperty("scale", (Number)Float.valueOf(PunchyDebug.ARM_L_SCALE_X));
        }
        return jsonObject;
    }

    private static JsonObject itemSection(boolean bl) {
        JsonObject jsonObject = new JsonObject();
        if (bl) {
            jsonObject.addProperty("pos_x", (Number)Float.valueOf(PunchyDebug.ITEM_R_POS_X));
            jsonObject.addProperty("pos_y", (Number)Float.valueOf(PunchyDebug.ITEM_R_POS_Y));
            jsonObject.addProperty("pos_z", (Number)Float.valueOf(PunchyDebug.ITEM_R_POS_Z));
            jsonObject.addProperty("rot_x", (Number)Float.valueOf(PunchyDebug.ITEM_R_ROT_X));
            jsonObject.addProperty("rot_y", (Number)Float.valueOf(PunchyDebug.ITEM_R_ROT_Y));
            jsonObject.addProperty("rot_z", (Number)Float.valueOf(PunchyDebug.ITEM_R_ROT_Z));
            jsonObject.addProperty("scale", (Number)Float.valueOf(PunchyDebug.ITEM_R_SCALE_X));
            jsonObject.addProperty("pivot_x", (Number)Float.valueOf(PunchyDebug.ITEM_R_PIVOT_X));
            jsonObject.addProperty("pivot_y", (Number)Float.valueOf(PunchyDebug.ITEM_R_PIVOT_Y));
            jsonObject.addProperty("pivot_z", (Number)Float.valueOf(PunchyDebug.ITEM_R_PIVOT_Z));
        } else {
            jsonObject.addProperty("pos_x", (Number)Float.valueOf(PunchyDebug.ITEM_L_POS_X));
            jsonObject.addProperty("pos_y", (Number)Float.valueOf(PunchyDebug.ITEM_L_POS_Y));
            jsonObject.addProperty("pos_z", (Number)Float.valueOf(PunchyDebug.ITEM_L_POS_Z));
            jsonObject.addProperty("rot_x", (Number)Float.valueOf(PunchyDebug.ITEM_L_ROT_X));
            jsonObject.addProperty("rot_y", (Number)Float.valueOf(PunchyDebug.ITEM_L_ROT_Y));
            jsonObject.addProperty("rot_z", (Number)Float.valueOf(PunchyDebug.ITEM_L_ROT_Z));
            jsonObject.addProperty("scale", (Number)Float.valueOf(PunchyDebug.ITEM_L_SCALE_X));
            jsonObject.addProperty("pivot_x", (Number)Float.valueOf(PunchyDebug.ITEM_L_PIVOT_X));
            jsonObject.addProperty("pivot_y", (Number)Float.valueOf(PunchyDebug.ITEM_L_PIVOT_Y));
            jsonObject.addProperty("pivot_z", (Number)Float.valueOf(PunchyDebug.ITEM_L_PIVOT_Z));
        }
        return jsonObject;
    }

    static enum Section {
        GLOBAL,
        ARM_RIGHT,
        ARM_LEFT,
        ITEM_RIGHT,
        ITEM_LEFT;

    }
}
