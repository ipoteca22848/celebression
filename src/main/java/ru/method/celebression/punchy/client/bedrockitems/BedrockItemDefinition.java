package ru.method.celebression.punchy.client.bedrockitems;

import ru.method.celebression.animation.Animation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import net.minecraft.class_1011;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_3532;
import net.minecraft.class_9334;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationLoader;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemGeoLoader;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemGeoModel;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.util.ResourceLocationCompat;

final class BedrockItemDefinition {
    private static final Gson GSON = new GsonBuilder().create();
    private final class_2960 id;
    private final List<String> items;
    private final class_2960 geoId;
    private final class_2960 textureId;
    private final List<class_2960> animationFiles;
    private final List<PunchyTuningConfig.CustomAnimationEntry> modelPartsAnimation;
    private final List<String> definitionParticleEffects;
    private final Map<class_1306, DisplayTransform> displayTransforms;
    private final Map<String, Integer> boneEmission;
    private final Map<String, class_2960> boneTextures;
    private final Map<String, TextureAnimSpec> boneTextureAnim;
    private final List<DynamicBoneTextureSpec> dynamicBoneTextures;
    private final Map<String, BoneMirrorSpec> boneMirror;
    private final Map<String, BonePhysicsSpec> bonePhysics;
    private final Set<String> disablePhysicsBones;
    private final Set<String> cameraTransparentBones;
    private final boolean boneEmissionBlur;
    private final boolean boneEmissionClamp;
    private final int boneEmissionStrength;
    private final int textureAnimFrames;
    private final int textureAnimFrameTime;
    private final int textureAnimFrameHeight;
    private final boolean physicsFeedback;
    private final String customName;
    private BedrockItemGeoModel geoModel;
    private final Map<class_2960, BedrockItemGeoModel> geoModelCache = new HashMap<class_2960, BedrockItemGeoModel>();
    private final Map<String, AnimationClip> animations = new HashMap<String, AnimationClip>();
    private int texturePixelWidth;
    private int texturePixelHeight;
    private final Map<class_2960, int[]> textureSizeCache = new HashMap<class_2960, int[]>();

    BedrockItemDefinition(class_2960 class_29602, List<String> list, class_2960 class_29603, class_2960 class_29604, List<class_2960> list2, List<PunchyTuningConfig.CustomAnimationEntry> list3, List<String> list4, Map<String, Integer> map, Map<String, class_2960> map2, Map<String, TextureAnimSpec> map3, List<DynamicBoneTextureSpec> list5, Map<String, BoneMirrorSpec> map4, Map<String, BonePhysicsSpec> map5, Set<String> set, Set<String> set2, boolean bl, boolean bl2, int n, int n2, int n3, int n4, boolean bl3, Map<class_1306, DisplayTransform> map6, String string) {
        this.id = class_29602;
        this.items = list == null ? List.of() : list;
        this.geoId = class_29603;
        this.textureId = class_29604;
        this.animationFiles = list2 == null ? List.of() : list2;
        this.modelPartsAnimation = list3 == null ? List.of() : list3;
        this.definitionParticleEffects = list4 == null ? List.of() : new ArrayList<String>(list4);
        this.displayTransforms = map6 == null ? Map.of() : map6;
        this.boneEmission = map == null ? Map.of() : new HashMap<String, Integer>(map);
        this.boneTextures = map2 == null ? Map.of() : new HashMap<String, class_2960>(map2);
        this.boneTextureAnim = map3 == null ? Map.of() : new HashMap<String, TextureAnimSpec>(map3);
        this.dynamicBoneTextures = list5 == null ? List.of() : List.copyOf(list5);
        this.boneMirror = map4 == null ? Map.of() : new HashMap<String, BoneMirrorSpec>(map4);
        this.bonePhysics = map5 == null ? Map.of() : new HashMap<String, BonePhysicsSpec>(map5);
        this.disablePhysicsBones = set == null ? Set.of() : new HashSet<String>(set);
        this.cameraTransparentBones = set2 == null ? Set.of() : new HashSet<String>(set2);
        this.boneEmissionBlur = bl;
        this.boneEmissionClamp = bl2;
        this.boneEmissionStrength = class_3532.method_15340((int)n, (int)0, (int)100);
        this.textureAnimFrames = n2 < 1 ? 1 : n2;
        this.textureAnimFrameTime = n3 < 1 ? 1 : n3;
        this.textureAnimFrameHeight = n4 < 1 ? 0 : n4;
        this.physicsFeedback = bl3;
        this.customName = string;
    }

    static List<BedrockItemDefinition> loadAll(class_3300 class_33002) {
        if (class_33002 == null) {
            return List.of();
        }
        HashMap<class_2960, class_3298> hashMap = new HashMap<>();
        hashMap.putAll(class_33002.method_14488("punchy/model_parts_items/definitions", class_29602 -> class_29602.method_12832().startsWith("punchy/model_parts_items/definitions/") && class_29602.method_12832().endsWith(".json")));
        if (hashMap.isEmpty()) {
            return List.of();
        }
        ArrayList<class_2960> arrayList = new ArrayList<>(hashMap.keySet());
        arrayList.sort((class_29602, class_29603) -> class_29602.toString().compareToIgnoreCase(class_29603.toString()));
        ArrayList<BedrockItemDefinition> arrayList2 = new ArrayList<BedrockItemDefinition>();
        for (class_2960 class_29604 : arrayList) {
            BedrockItemDefinition bedrockItemDefinition;
            class_3298 class_32982 = hashMap.get(class_29604);
            if (class_32982 == null || (bedrockItemDefinition = BedrockItemDefinition.parse(class_29604, class_32982)) == null) continue;
            arrayList2.add(bedrockItemDefinition);
        }
        return arrayList2;
    }

    private static BedrockItemDefinition parse(class_2960 class_29602, class_3298 class_32982) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(class_32982.method_14482())) {
            JsonObject jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
            ArrayList<String> items = new ArrayList<>();
            JsonElement itemsElement = jsonObject.get("items");
            if (itemsElement != null && itemsElement.isJsonArray()) {
                for (JsonElement element : itemsElement.getAsJsonArray()) {
                    String item = element != null && element.isJsonPrimitive() ? element.getAsString() : null;
                    if (item != null && !item.isBlank()) {
                        items.add(item.trim());
                    }
                }
            } else {
                JsonElement itemElement = jsonObject.get("item");
                String item = itemElement != null && itemElement.isJsonPrimitive() ? itemElement.getAsString() : null;
                if (item != null && !item.isBlank()) {
                    items.add(item.trim());
                }
            }

            class_2960 geoId = BedrockItemDefinition.readIdentifier(jsonObject.get("geo"));
            class_2960 textureId = BedrockItemDefinition.readIdentifier(jsonObject.get("texture"));
            if (textureId != null) {
                textureId = BedrockItemDefinition.normalizeTextureId(textureId);
            }

            ArrayList<class_2960> animationFiles = new ArrayList<>();
            JsonElement animationsElement = jsonObject.get("animations");
            if (animationsElement != null && animationsElement.isJsonArray()) {
                for (JsonElement element : animationsElement.getAsJsonArray()) {
                    class_2960 animationId = BedrockItemDefinition.readIdentifier(element);
                    if (animationId != null) {
                        animationFiles.add(animationId);
                    }
                }
            } else {
                class_2960 animationId = BedrockItemDefinition.readIdentifier(jsonObject.get("animation"));
                if (animationId != null) {
                    animationFiles.add(animationId);
                }
            }

            ArrayList<PunchyTuningConfig.CustomAnimationEntry> modelPartsAnimation = new ArrayList<>();
            JsonElement modelPartsElement = jsonObject.get("modelPartsAnimation");
            if (modelPartsElement != null && modelPartsElement.isJsonArray()) {
                for (JsonElement element : modelPartsElement.getAsJsonArray()) {
                    PunchyTuningConfig.CustomAnimationEntry entry = element != null && element.isJsonObject() ? GSON.fromJson(element, PunchyTuningConfig.CustomAnimationEntry.class) : null;
                    if (entry != null) {
                        modelPartsAnimation.add(entry);
                    }
                }
            }

            HashMap<class_1306, DisplayTransform> displayTransforms = new HashMap<>();
            JsonElement displayElement = jsonObject.get("display");
            if (displayElement != null && displayElement.isJsonObject()) {
                JsonObject display = displayElement.getAsJsonObject();
                DisplayTransform right = BedrockItemDefinition.readDisplay(display, "thirdperson_righthand");
                if (right == null) {
                    right = BedrockItemDefinition.readDisplay(display, "firstperson_righthand");
                }
                if (right != null) {
                    displayTransforms.put(class_1306.field_6183, right);
                }
                DisplayTransform left = BedrockItemDefinition.readDisplay(display, "thirdperson_lefthand");
                if (left == null) {
                    left = BedrockItemDefinition.readDisplay(display, "firstperson_lefthand");
                }
                if (left != null) {
                    displayTransforms.put(class_1306.field_6182, left);
                }
            }

            String customName = BedrockItemDefinition.readNonBlankString(jsonObject.get("customName"));
            if (customName == null) {
                customName = BedrockItemDefinition.readNonBlankString(jsonObject.get("name"));
            }
            if (items.isEmpty() || geoId == null || textureId == null) {
                return null;
            }

            boolean physicsFeedback = BedrockItemDefinition.readBoolean(jsonObject, "physicsFeedback",
                    BedrockItemDefinition.readBoolean(jsonObject, "fallingFeedBack",
                            BedrockItemDefinition.readBoolean(jsonObject, "fallingFeedback", false)));
            Map<String, Integer> boneEmission = Map.of();
            boolean boneEmissionBlur = false;
            boolean boneEmissionClamp = false;
            int boneEmissionStrength = 100;
            int textureAnimFrames = 1;
            int textureAnimFrameTime = 1;
            int textureAnimFrameHeight = 0;
            Map<String, class_2960> boneTextures = Map.of();
            Map<String, TextureAnimSpec> boneTextureAnim = Map.of();
            List<DynamicBoneTextureSpec> dynamicBoneTextures = List.of();
            Map<String, BoneMirrorSpec> boneMirror = Map.of();

            JsonElement emissionElement = jsonObject.get("bone_emission");
            if (emissionElement != null && emissionElement.isJsonObject()) {
                HashMap<String, Integer> emission = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : emissionElement.getAsJsonObject().entrySet()) {
                    String key = entry.getKey();
                    JsonElement value = entry.getValue();
                    if (key == null || key.isBlank() || value == null || !value.isJsonPrimitive()) continue;
                    if ("blur".equalsIgnoreCase(key)) {
                        boneEmissionBlur = value.getAsBoolean();
                        continue;
                    }
                    if ("clamp".equalsIgnoreCase(key)) {
                        boneEmissionClamp = value.getAsBoolean();
                        continue;
                    }
                    if (!value.getAsJsonPrimitive().isNumber()) continue;
                    int amount = class_3532.method_15340(value.getAsInt(), 0, 100);
                    if ("strength".equalsIgnoreCase(key) || "intensity".equalsIgnoreCase(key) || "brightness".equalsIgnoreCase(key)) {
                        boneEmissionStrength = amount;
                    } else if (amount > 0) {
                        emission.put(key, amount);
                    }
                }
                if (!emission.isEmpty()) {
                    boneEmission = emission;
                }
            }

            JsonElement boneTexturesElement = jsonObject.get("bone_textures");
            if (boneTexturesElement != null) {
                HashMap<String, class_2960> textures = new HashMap<>();
                if (boneTexturesElement.isJsonObject()) {
                    for (Map.Entry<String, JsonElement> entry : boneTexturesElement.getAsJsonObject().entrySet()) {
                        class_2960 texture = BedrockItemDefinition.readIdentifier(entry.getValue());
                        if (entry.getKey() == null || entry.getKey().isBlank() || texture == null) continue;
                        textures.put(entry.getKey().trim(), BedrockItemDefinition.normalizeTextureId(texture));
                    }
                } else if (boneTexturesElement.isJsonArray()) {
                    for (JsonElement element : boneTexturesElement.getAsJsonArray()) {
                        if (element == null || !element.isJsonObject()) continue;
                        JsonObject definition = element.getAsJsonObject();
                        class_2960 texture = BedrockItemDefinition.readIdentifier(definition.get("texture"));
                        JsonElement bones = definition.get("bones");
                        if (texture == null || bones == null || !bones.isJsonArray()) continue;
                        texture = BedrockItemDefinition.normalizeTextureId(texture);
                        for (JsonElement boneElement : bones.getAsJsonArray()) {
                            String bone = BedrockItemDefinition.readNonBlankString(boneElement);
                            if (bone != null) {
                                textures.put(bone.trim(), texture);
                            }
                        }
                    }
                }
                if (!textures.isEmpty()) {
                    boneTextures = textures;
                }
            }

            JsonElement textureAnimElement = jsonObject.get("texture_anim");
            if (textureAnimElement != null && textureAnimElement.isJsonObject()) {
                JsonObject textureAnim = textureAnimElement.getAsJsonObject();
                textureAnimFrames = BedrockItemDefinition.readPositiveInt(textureAnim, "frames", 1);
                textureAnimFrameTime = BedrockItemDefinition.readPositiveInt(textureAnim, "frametime", 1);
                textureAnimFrameHeight = textureAnim.has("frame_height") ? Math.max(1, textureAnim.get("frame_height").getAsInt()) : 0;
            }

            JsonElement boneTextureAnimElement = jsonObject.get("bone_texture_anim");
            if (boneTextureAnimElement != null && boneTextureAnimElement.isJsonObject()) {
                HashMap<String, TextureAnimSpec> animations = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : boneTextureAnimElement.getAsJsonObject().entrySet()) {
                    TextureAnimSpec spec = entry.getValue() != null && entry.getValue().isJsonObject() ? TextureAnimSpec.fromJson(entry.getValue().getAsJsonObject()) : null;
                    if (entry.getKey() == null || entry.getKey().isBlank() || spec == null) continue;
                    animations.put(entry.getKey(), spec);
                }
                if (!animations.isEmpty()) {
                    boneTextureAnim = animations;
                }
            }
            if (jsonObject.has("dynamic_bone_textures")) {
                dynamicBoneTextures = BedrockItemDefinition.parseDynamicBoneTextures(jsonObject.get("dynamic_bone_textures"));
            } else if (jsonObject.has("dynamicBoneTextures")) {
                dynamicBoneTextures = BedrockItemDefinition.parseDynamicBoneTextures(jsonObject.get("dynamicBoneTextures"));
            }

            JsonElement boneMirrorElement = jsonObject.get("bone_mirror");
            if (boneMirrorElement != null && boneMirrorElement.isJsonObject()) {
                HashMap<String, BoneMirrorSpec> mirrors = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : boneMirrorElement.getAsJsonObject().entrySet()) {
                    BoneMirrorSpec spec = BoneMirrorSpec.fromJson(entry.getValue());
                    if (entry.getKey() == null || entry.getKey().isBlank() || spec == null) continue;
                    mirrors.put(entry.getKey().trim(), spec);
                }
                if (!mirrors.isEmpty()) {
                    boneMirror = mirrors;
                }
            }

            JsonElement bonePhysicsElement = jsonObject.has("bone_physics") ? jsonObject.get("bone_physics") : jsonObject.get("bonePhysics");
            Map<String, BonePhysicsSpec> bonePhysics = BedrockItemDefinition.parseBonePhysics(bonePhysicsElement);
            JsonElement disabledPhysicsElement = jsonObject.has("disable_physics_bones") ? jsonObject.get("disable_physics_bones") : jsonObject.get("disable_pendulum_bones");
            Set<String> disablePhysicsBones = BedrockItemDefinition.parseBoneNameSet(disabledPhysicsElement);
            JsonElement transparentElement = jsonObject.has("camera_transparent_bones") ? jsonObject.get("camera_transparent_bones") : jsonObject.get("inside_visible_bones");
            Set<String> cameraTransparentBones = BedrockItemDefinition.parseBoneNameSet(transparentElement);
            List<String> particleEffects = BedrockItemDefinition.readDefinitionParticleEffects(jsonObject);

            return new BedrockItemDefinition(class_29602, items, geoId, textureId, animationFiles, modelPartsAnimation, particleEffects, boneEmission, boneTextures, boneTextureAnim, dynamicBoneTextures, boneMirror, bonePhysics, disablePhysicsBones, cameraTransparentBones, boneEmissionBlur, boneEmissionClamp, boneEmissionStrength, textureAnimFrames, textureAnimFrameTime, textureAnimFrameHeight, physicsFeedback, displayTransforms, customName);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static class_2960 readIdentifier(JsonElement element) {
        String value = BedrockItemDefinition.readNonBlankString(element);
        return value == null ? null : class_2960.method_12829(value);
    }

    private static String readNonBlankString(JsonElement element) {
        if (element == null || !element.isJsonPrimitive()) {
            return null;
        }
        String value = element.getAsString();
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static boolean readBoolean(JsonObject object, String key, boolean fallback) {
        JsonElement element = object.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int readPositiveInt(JsonObject object, String key, int fallback) {
        JsonElement element = object.get(key);
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber() ? Math.max(1, element.getAsInt()) : fallback;
    }

    static Map<String, BonePhysicsSpec> parseBonePhysics(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            HashMap<String, BonePhysicsSpec> hashMap = new HashMap<String, BonePhysicsSpec>();
            if (jsonElement.isJsonObject()) {
                BedrockItemDefinition.appendBonePhysicsObject(hashMap, jsonElement.getAsJsonObject());
            } else if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    if (jsonElement2 == null || !jsonElement2.isJsonObject()) continue;
                    BedrockItemDefinition.appendBonePhysicsObject(hashMap, jsonElement2.getAsJsonObject());
                }
            }
            return hashMap.isEmpty() ? Map.of() : hashMap;
        }
        return Map.of();
    }

    private static List<DynamicBoneTextureSpec> parseDynamicBoneTextures(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            ArrayList<DynamicBoneTextureSpec> arrayList = new ArrayList<DynamicBoneTextureSpec>();
            if (jsonElement.isJsonObject()) {
                DynamicBoneTextureSpec dynamicBoneTextureSpec = DynamicBoneTextureSpec.fromJson(jsonElement.getAsJsonObject());
                if (dynamicBoneTextureSpec != null) {
                    arrayList.add(dynamicBoneTextureSpec);
                }
            } else if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    DynamicBoneTextureSpec dynamicBoneTextureSpec;
                    if (jsonElement2 == null || !jsonElement2.isJsonObject() || (dynamicBoneTextureSpec = DynamicBoneTextureSpec.fromJson(jsonElement2.getAsJsonObject())) == null) continue;
                    arrayList.add(dynamicBoneTextureSpec);
                }
            }
            return arrayList.isEmpty() ? List.of() : arrayList;
        }
        return List.of();
    }

    private static void appendBonePhysicsObject(Map<String, BonePhysicsSpec> map, JsonObject jsonObject) {
        if (jsonObject != null) {
            for (Map.Entry entry : jsonObject.entrySet()) {
                BonePhysicsSpec bonePhysicsSpec;
                String string = (String)entry.getKey();
                if (string == null || string.isBlank() || (bonePhysicsSpec = BonePhysicsSpec.fromJson((JsonElement)entry.getValue())) == null || bonePhysicsSpec.isEmpty()) continue;
                BonePhysicsSpec bonePhysicsSpec2 = map.get(string = string.trim());
                map.put(string, bonePhysicsSpec2 == null ? bonePhysicsSpec : bonePhysicsSpec2.merge(bonePhysicsSpec));
            }
        }
    }

    private static Set<String> parseBoneNameSet(JsonElement jsonElement) {
        if (jsonElement != null && jsonElement.isJsonArray()) {
            HashSet<String> hashSet = new HashSet<String>();
            for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                String string;
                if (jsonElement2 == null || !jsonElement2.isJsonPrimitive() || (string = jsonElement2.getAsString()) == null || string.isBlank()) continue;
                hashSet.add(string.trim());
            }
            return hashSet;
        }
        return Set.of();
    }

    private static List<String> readDefinitionParticleEffects(JsonObject jsonObject) {
        if (jsonObject == null) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        BedrockItemDefinition.appendDefinitionParticleEffects(arrayList, jsonObject.get("particle"));
        BedrockItemDefinition.appendDefinitionParticleEffects(arrayList, jsonObject.get("particles"));
        BedrockItemDefinition.appendDefinitionParticleEffects(arrayList, jsonObject.get("particle_effects"));
        BedrockItemDefinition.appendDefinitionParticleEffects(arrayList, jsonObject.get("particle_emission"));
        BedrockItemDefinition.appendDefinitionParticleEffects(arrayList, jsonObject.get("particleEmission"));
        return arrayList.isEmpty() ? List.of() : arrayList;
    }

    private static void appendDefinitionParticleEffects(List<String> list, JsonElement jsonElement) {
        if (list != null && jsonElement != null && !jsonElement.isJsonNull()) {
            String string;
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    BedrockItemDefinition.appendDefinitionParticleEffects(list, jsonElement2);
                }
            } else if (jsonElement.isJsonObject()) {
                String string2 = BedrockItemDefinition.definitionParticleObjectToScript(jsonElement.getAsJsonObject());
                if (string2 != null && !string2.isBlank()) {
                    list.add(string2);
                }
            } else if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString() && (string = BedrockItemDefinition.normalizeDefinitionParticleEffect(jsonElement.getAsString())) != null && !string.isBlank()) {
                list.add(string);
            }
        }
    }

    private static String normalizeDefinitionParticleEffect(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            if (string2.startsWith("particle(") && string2.endsWith(")")) {
                return string2;
            }
            if (string2.contains("=")) {
                return "particle(" + string2 + ")";
            }
            class_2960 class_29602 = class_2960.method_12829((String)string2);
            return class_29602 != null ? "particle(type=" + String.valueOf(class_29602) + ")" : null;
        }
        return null;
    }

    private static String definitionParticleObjectToScript(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        if (jsonObject.has("enabled") && jsonObject.get("enabled").isJsonPrimitive() && !jsonObject.get("enabled").getAsBoolean()) {
            return null;
        }
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Map.Entry entry : jsonObject.entrySet()) {
            String string = (String)entry.getKey();
            if (string == null || string.isBlank() || "enabled".equalsIgnoreCase(string) || BedrockItemDefinition.isIgnoredDefinitionParticleRenderHint(string)) continue;
            if ("triggers".equalsIgnoreCase(string) && entry.getValue() != null && ((JsonElement)entry.getValue()).isJsonObject()) {
                BedrockItemDefinition.appendDefinitionTriggerTokens(stringJoiner, ((JsonElement)entry.getValue()).getAsJsonObject());
                continue;
            }
            if ("on_animation".equalsIgnoreCase(string) && entry.getValue() != null && ((JsonElement)entry.getValue()).isJsonObject()) {
                BedrockItemDefinition.appendOnAnimationTokens(stringJoiner, ((JsonElement)entry.getValue()).getAsJsonObject());
                continue;
            }
            String string2 = BedrockItemDefinition.definitionParticleValueToToken((JsonElement)entry.getValue());
            if (string2 == null || string2.isBlank()) continue;
            stringJoiner.add(string.trim() + "=" + string2);
        }
        String string = stringJoiner.toString();
        return string.isBlank() ? null : "particle(" + (String)string + ")";
    }

    private static boolean isIgnoredDefinitionParticleRenderHint(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return "emissive_strength".equals(string2) || "emission_strength".equals(string2) || "emissive_blur".equals(string2) || "emission_blur".equals(string2) || "emissive_clamp".equals(string2) || "emission_clamp".equals(string2);
        }
        return false;
    }

    private static String definitionParticleValueToToken(JsonElement jsonElement) {
        Object object;
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        if (!jsonElement.isJsonArray()) {
            JsonObject jsonObject;
            if (jsonElement.isJsonObject() && (jsonObject = jsonElement.getAsJsonObject()).has("x") && jsonObject.has("y") && jsonObject.has("z") && jsonObject.get("x").isJsonPrimitive() && jsonObject.get("y").isJsonPrimitive() && jsonObject.get("z").isJsonPrimitive()) {
                return jsonObject.get("x").getAsString() + ":" + jsonObject.get("y").getAsString() + ":" + jsonObject.get("z").getAsString();
            }
            return null;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        boolean bl = true;
        for (JsonElement element : jsonArray) {
            if (element != null && element.isJsonPrimitive()) continue;
            bl = false;
            break;
        }
        if (!bl) {
            return null;
        }
        if (jsonArray.size() == 3) {
            object = new StringJoiner(":");
            for (JsonElement jsonElement2 : jsonArray) {
                ((StringJoiner)object).add(jsonElement2.getAsString());
            }
            return ((StringJoiner)object).toString();
        }
        object = new StringJoiner("|");
        for (JsonElement jsonElement3 : jsonArray) {
            ((StringJoiner)object).add(jsonElement3.getAsString());
        }
        return ((StringJoiner)object).toString();
    }

    private static void appendDefinitionTriggerTokens(StringJoiner stringJoiner, JsonObject jsonObject) {
        if (stringJoiner != null && jsonObject != null) {
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_jump", "jump", "triggerJump");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_landing", "landing", "triggerLanding");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_walking", "walking", "walkgin", "triggerWalking");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_sprinting", "sprinting", "triggerSprinting");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_swimming", "swimming", "swiming", "triggerSwimming");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_attacking", "attacking", "attack", "triggerAttacking");
            BedrockItemDefinition.appendTrueTrigger(stringJoiner, jsonObject, "trigger_mining", "mining", "triggerMining");
            JsonElement jsonElement2 = jsonObject.get("falling");
            if (jsonElement2 != null && !jsonElement2.isJsonNull()) {
                if (jsonElement2.isJsonPrimitive()) {
                    if (BedrockItemDefinition.readBoolean(jsonElement2, false)) {
                        stringJoiner.add("trigger_falling=true");
                    }
                } else if (jsonElement2.isJsonObject()) {
                    JsonObject falling = jsonElement2.getAsJsonObject();
                    stringJoiner.add("trigger_falling=true");
                    Float minimumBlocks = BedrockItemDefinition.readFallingMinBlocks(falling);
                    if (minimumBlocks != null) {
                        stringJoiner.add("trigger_falling_min_blocks=" + minimumBlocks);
                    }
                }
            }
            List<String> biomes = BedrockItemDefinition.readTriggerBiomeNames(jsonObject.get("biome"));
            if (!biomes.isEmpty()) {
                stringJoiner.add("trigger_biomes=" + String.join("|", biomes));
            }
        }
    }

    private static void appendOnAnimationTokens(StringJoiner stringJoiner, JsonObject jsonObject) {
        if (stringJoiner != null && jsonObject != null) {
            String string;
            JsonElement jsonElement = jsonObject.get("name");
            JsonElement jsonElement2 = jsonObject.get("timecode");
            if (jsonElement != null && jsonElement.isJsonPrimitive() && (string = jsonElement.getAsString()) != null && !string.isBlank()) {
                stringJoiner.add("on_animation_name=" + string.trim());
            }
            if (jsonElement2 != null && jsonElement2.isJsonPrimitive()) {
                stringJoiner.add("on_animation_timecode=" + jsonElement2.getAsString());
            }
        }
    }

    private static void appendTrueTrigger(StringJoiner stringJoiner, JsonObject jsonObject, String string, String ... stringArray) {
        if (stringJoiner != null && jsonObject != null && string != null && stringArray != null && stringArray.length != 0) {
            for (String string2 : stringArray) {
                JsonElement jsonElement;
                if (string2 == null || string2.isBlank() || !BedrockItemDefinition.readBoolean(jsonElement = jsonObject.get(string2), false)) continue;
                stringJoiner.add(string + "=true");
                return;
            }
        }
    }

    static boolean readBoolean(JsonElement jsonElement, boolean bl) {
        if (jsonElement != null && !jsonElement.isJsonNull() && jsonElement.isJsonPrimitive()) {
            try {
                if (jsonElement.getAsJsonPrimitive().isBoolean()) {
                    return jsonElement.getAsBoolean();
                }
                if (jsonElement.getAsJsonPrimitive().isNumber()) {
                    return jsonElement.getAsInt() != 0;
                }
                if (jsonElement.getAsJsonPrimitive().isString()) {
                    String string = jsonElement.getAsString();
                    if (string == null) {
                        return bl;
                    }
                    String string2 = string.trim().toLowerCase(Locale.ROOT);
                    if (!("true".equals(string2) || "1".equals(string2) || "yes".equals(string2))) {
                        if (!("false".equals(string2) || "0".equals(string2) || "no".equals(string2))) {
                            return bl;
                        }
                        return false;
                    }
                    return true;
                }
            }
            catch (Exception exception) {
            }
            return bl;
        }
        return bl;
    }

    private static Float readFallingMinBlocks(JsonObject jsonObject) {
        String[] stringArray;
        if (jsonObject == null) {
            return null;
        }
        for (String string : stringArray = new String[]{"min_blocks", "minBlocks", "min_height", "minHeight", "min"}) {
            JsonElement jsonElement = jsonObject.get(string);
            if (jsonElement == null || !jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isNumber()) continue;
            float f = jsonElement.getAsFloat();
            return Float.valueOf(f < 0.0f ? 0.0f : f);
        }
        return null;
    }

    private static List<String> readTriggerBiomeNames(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                String string = jsonElement.getAsString();
                if (string != null && !string.isBlank()) {
                    arrayList.add(string.trim());
                }
                return arrayList;
            }
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    String string;
                    if (jsonElement2 == null || !jsonElement2.isJsonPrimitive() || !jsonElement2.getAsJsonPrimitive().isString() || (string = jsonElement2.getAsString()) == null || string.isBlank()) continue;
                    arrayList.add(string.trim());
                }
                return arrayList;
            }
            if (!jsonElement.isJsonObject()) {
                return arrayList;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement3 = jsonObject.get("names");
            if (jsonElement3 == null) {
                jsonElement3 = jsonObject.get("biomes");
            }
            if (jsonElement3 != null) {
                arrayList.addAll(BedrockItemDefinition.readTriggerBiomeNames(jsonElement3));
                return arrayList;
            }
            JsonElement jsonElement4 = jsonObject.get("name");
            if (jsonElement4 == null) {
                jsonElement4 = jsonObject.get("biome");
            }
            if (jsonElement4 != null) {
                arrayList.addAll(BedrockItemDefinition.readTriggerBiomeNames(jsonElement4));
            }
            return arrayList;
        }
        return List.of();
    }

    private static DisplayTransform readDisplay(JsonObject jsonObject, String string) {
        if (jsonObject != null && string != null && jsonObject.has(string)) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
            return DisplayTransform.fromJson(jsonObject2);
        }
        return null;
    }

    class_2960 getId() {
        return this.id;
    }

    List<String> getItems() {
        return this.items;
    }

    class_2960 getGeoId() {
        return this.geoId;
    }

    class_2960 getTextureId() {
        return this.textureId;
    }

    List<class_2960> getAnimationFiles() {
        return this.animationFiles;
    }

    List<PunchyTuningConfig.CustomAnimationEntry> getModelPartsAnimation() {
        return this.modelPartsAnimation;
    }

    List<String> getDefinitionParticleEffects() {
        return this.definitionParticleEffects;
    }

    Map<String, Integer> getBoneEmission() {
        return this.boneEmission;
    }

    Map<String, class_2960> getBoneTextures() {
        return this.boneTextures;
    }

    Map<String, TextureAnimSpec> getBoneTextureAnim() {
        return this.boneTextureAnim;
    }

    List<DynamicBoneTextureSpec> getDynamicBoneTextures() {
        return this.dynamicBoneTextures;
    }

    Set<String> getDisablePhysicsBones() {
        return this.disablePhysicsBones;
    }

    Set<String> getCameraTransparentBones() {
        return this.cameraTransparentBones;
    }

    boolean isBoneEmissionBlur() {
        return this.boneEmissionBlur;
    }

    boolean isBoneEmissionClamp() {
        return this.boneEmissionClamp;
    }

    int getBoneEmissionStrength() {
        return this.boneEmissionStrength;
    }

    int getTextureAnimFrames() {
        return this.textureAnimFrames;
    }

    int getTextureAnimFrameTime() {
        return this.textureAnimFrameTime;
    }

    int getTextureAnimFrameHeight() {
        return this.textureAnimFrameHeight;
    }

    boolean isPhysicsFeedbackEnabled() {
        return this.physicsFeedback;
    }

    int getTexturePixelWidth() {
        return this.texturePixelWidth;
    }

    int getTexturePixelHeight() {
        return this.texturePixelHeight;
    }

    int[] getTexturePixelSize(class_2960 class_29602) {
        return class_29602 == null ? null : this.textureSizeCache.get(class_29602);
    }

    Map<String, BonePhysicsSpec> getBonePhysics() {
        return this.bonePhysics;
    }

    boolean hasCreatureBonePhysics() {
        if (this.bonePhysics != null && !this.bonePhysics.isEmpty()) {
            for (BonePhysicsSpec bonePhysicsSpec : this.bonePhysics.values()) {
                if (bonePhysicsSpec == null || !bonePhysicsSpec.enableCreaturePhysics()) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    boolean hasSlimeBonePhysics() {
        if (this.bonePhysics != null && !this.bonePhysics.isEmpty()) {
            for (BonePhysicsSpec bonePhysicsSpec : this.bonePhysics.values()) {
                if (bonePhysicsSpec == null || !bonePhysicsSpec.enableSlimePhysics()) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    boolean matchesCustomName(class_1799 class_17992) {
        if (this.customName == null || this.customName.isBlank()) {
            return true;
        }
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2561 class_25612 = (class_2561)class_17992.method_57824(class_9334.field_49631);
            if (class_25612 == null) {
                return false;
            }
            String string = class_25612.getString();
            return string == null ? false : string.trim().equalsIgnoreCase(this.customName.trim());
        }
        return false;
    }

    boolean hasCustomName() {
        return this.customName != null && !this.customName.isBlank();
    }

    DisplayTransform getDisplayTransform(class_1306 class_13062) {
        return this.displayTransforms.get(class_13062);
    }

    void loadResources(class_3300 class_33002) {
        this.geoModelCache.clear();
        this.geoModel = this.loadGeoModel(class_33002, this.geoId);
        this.animations.clear();
        if (class_33002 != null) {
            this.textureSizeCache.clear();
            if (this.textureId != null) {
                this.loadTextureSize(class_33002, this.textureId);
                int[] textureSize = this.textureSizeCache.get(this.textureId);
                if (textureSize != null) {
                    this.texturePixelWidth = textureSize[0];
                    this.texturePixelHeight = textureSize[1];
                }
            }
            for (class_2960 object : this.boneTextures.values()) {
                this.loadTextureSize(class_33002, object);
            }
            for (DynamicBoneTextureSpec dynamicBoneTextureSpec : this.dynamicBoneTextures) {
                if (dynamicBoneTextureSpec == null) continue;
                if (dynamicBoneTextureSpec.isTextureType()) {
                    this.loadTextureSize(class_33002, dynamicBoneTextureSpec.fallbackResource());
                    for (class_2960 class_29602 : dynamicBoneTextureSpec.resourceMap().values()) {
                        this.loadTextureSize(class_33002, class_29602);
                    }
                    continue;
                }
                if (dynamicBoneTextureSpec.fallbackResource() != null) {
                    this.loadGeoModel(class_33002, dynamicBoneTextureSpec.fallbackResource());
                }
                for (class_2960 class_29602 : dynamicBoneTextureSpec.resourceMap().values()) {
                    this.loadGeoModel(class_33002, class_29602);
                }
            }
            for (class_2960 class_29603 : this.animationFiles) {
                Map<String, AnimationClip> map = BedrockItemAnimationLoader.loadAll(class_33002, class_29603);
                if (map == null || map.isEmpty()) continue;
                this.animations.putAll(map);
            }
            if (!this.boneMirror.isEmpty()) {
                this.applyBoneMirrorOverrides();
            }
        }
    }

    BedrockItemGeoModel getGeoModel() {
        return this.geoModel;
    }

    BedrockItemGeoModel getGeoModel(class_2960 class_29602) {
        return class_29602 != null && !class_29602.equals((Object)this.geoId) ? this.geoModelCache.get(class_29602) : this.geoModel;
    }

    Map<String, AnimationClip> getAnimations() {
        return this.animations;
    }

    private void applyBoneMirrorOverrides() {
        for (Map.Entry<String, AnimationClip> entry : this.animations.entrySet()) {
            AnimationClip animationClip = entry.getValue();
            if (animationClip == null) continue;
            for (Map.Entry<String, BoneTrack> entry2 : animationClip.getTracks().entrySet()) {
                BoneTrack boneTrack;
                BoneMirrorSpec boneMirrorSpec;
                String string = entry2.getKey();
                if (string == null || (boneMirrorSpec = this.boneMirror.get(string)) == null || boneMirrorSpec.isDefault() || (boneTrack = entry2.getValue()) == null) continue;
                for (Keyframe keyframe : boneTrack.getKeyframes()) {
                    if (keyframe == null) continue;
                    if (boneMirrorSpec.mirrorPosition == Boolean.FALSE && keyframe.position() != null) {
                        keyframe.position().x = -keyframe.position().x;
                    }
                    if (boneMirrorSpec.mirrorRotation != Boolean.FALSE || keyframe.rotationEuler() == null) continue;
                    keyframe.rotationEuler().x = -keyframe.rotationEuler().x;
                    keyframe.rotationEuler().y = -keyframe.rotationEuler().y;
                    if (keyframe.rotation() == null) continue;
                    float f = (float)Math.toRadians(keyframe.rotationEuler().x);
                    float f2 = (float)Math.toRadians(keyframe.rotationEuler().y);
                    float f3 = (float)Math.toRadians(keyframe.rotationEuler().z);
                    keyframe.rotation().set((Quaternionfc)new Quaternionf().rotationXYZ(f, f2, f3));
                }
            }
        }
    }

    private BedrockItemGeoModel loadGeoModel(class_3300 class_33002, class_2960 class_29602) {
        if (class_33002 != null && class_29602 != null) {
            BedrockItemGeoModel bedrockItemGeoModel = this.geoModelCache.get(class_29602);
            if (bedrockItemGeoModel != null) {
                return bedrockItemGeoModel;
            }
            BedrockItemGeoModel bedrockItemGeoModel2 = BedrockItemGeoLoader.load(class_33002, class_29602);
            if (bedrockItemGeoModel2 != null) {
                this.geoModelCache.put(class_29602, bedrockItemGeoModel2);
            }
            return bedrockItemGeoModel2;
        }
        return null;
    }

    static class_2960 normalizeTextureId(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        String string = class_29602.method_12832();
        if (string != null && !string.isBlank()) {
            Object object = string;
            if (string.startsWith("punchy/model_parts_items/textures/")) {
                if (!string.endsWith(".png")) {
                    object = string + ".png";
                }
                return ResourceLocationCompat.fromNamespaceAndPath(class_29602.method_12836(), (String)object);
            }
            if (!string.startsWith("textures/")) {
                object = "textures/" + string;
            }
            if (!((String)object).endsWith(".png")) {
                object = (String)object + ".png";
            }
            return ResourceLocationCompat.fromNamespaceAndPath(class_29602.method_12836(), (String)object);
        }
        return class_29602;
    }

    static class_2960 normalizeGeoId(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        String string = class_29602.method_12832();
        if (string != null && !string.isBlank()) {
            Object object = string;
            if (string.startsWith("punchy/model_parts_items/geo/")) {
                if (!string.endsWith(".geo.json")) {
                    object = string.endsWith(".json") ? string.substring(0, string.length() - 5) + ".geo.json" : string + ".geo.json";
                }
                return ResourceLocationCompat.fromNamespaceAndPath(class_29602.method_12836(), (String)object);
            }
            if (!string.startsWith("geo/")) {
                object = "geo/" + string;
            }
            if (!((String)object).endsWith(".geo.json")) {
                object = ((String)object).endsWith(".json") ? ((String)object).substring(0, ((String)object).length() - 5) + ".geo.json" : (String)object + ".geo.json";
            }
            return ResourceLocationCompat.fromNamespaceAndPath(class_29602.method_12836(), (String)object);
        }
        return class_29602;
    }

    private void loadTextureSize(class_3300 class_33002, class_2960 class_29602) {
        if (class_33002 != null && class_29602 != null && !this.textureSizeCache.containsKey(class_29602)) {
            try {
                Optional optional = class_33002.method_14486(class_29602);
                if (optional.isEmpty()) {
                    return;
                }
                try (InputStream inputStream = ((class_3298)optional.get()).method_14482();){
                    class_1011 class_10112 = class_1011.method_4309((InputStream)inputStream);
                    if (class_10112 != null) {
                        this.textureSizeCache.put(class_29602, new int[]{class_10112.method_4307(), class_10112.method_4323()});
                        class_10112.close();
                    }
                }
            }
            catch (Exception exception) {
            }
        }
    }

    static final class DisplayTransform {
        final float rotX;
        final float rotY;
        final float rotZ;
        final float transX;
        final float transY;
        final float transZ;
        final float scaleX;
        final float scaleY;
        final float scaleZ;

        private DisplayTransform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            this.rotX = f;
            this.rotY = f2;
            this.rotZ = f3;
            this.transX = f4;
            this.transY = f5;
            this.transZ = f6;
            this.scaleX = f7;
            this.scaleY = f8;
            this.scaleZ = f9;
        }

        static DisplayTransform fromJson(JsonObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            float[] fArray = DisplayTransform.readVec3(jsonObject, "rotation", 0.0f, 0.0f, 0.0f);
            float[] fArray2 = DisplayTransform.readVec3(jsonObject, "translation", 0.0f, 0.0f, 0.0f);
            float[] fArray3 = DisplayTransform.readVec3(jsonObject, "scale", 1.0f, 1.0f, 1.0f);
            return new DisplayTransform(fArray[0], fArray[1], fArray[2], fArray2[0], fArray2[1], fArray2[2], fArray3[0], fArray3[1], fArray3[2]);
        }

        private static float[] readVec3(JsonObject jsonObject, String string, float f, float f2, float f3) {
            if (jsonObject != null && jsonObject.has(string)) {
                float[] fArray;
                JsonArray jsonArray = jsonObject.getAsJsonArray(string);
                if (jsonArray != null && jsonArray.size() >= 3) {
                    float[] fArray2 = new float[3];
                    fArray2[0] = jsonArray.get(0).getAsFloat();
                    fArray2[1] = jsonArray.get(1).getAsFloat();
                    fArray = fArray2;
                    fArray2[2] = jsonArray.get(2).getAsFloat();
                } else {
                    float[] fArray3 = new float[3];
                    fArray3[0] = f;
                    fArray3[1] = f2;
                    fArray = fArray3;
                    fArray3[2] = f3;
                }
                return fArray;
            }
            return new float[]{f, f2, f3};
        }
    }

    static final class TextureAnimSpec {
        final int frames;
        final int frameTime;
        final int frameHeight;

        private TextureAnimSpec(int n, int n2, int n3) {
            this.frames = n;
            this.frameTime = n2;
            this.frameHeight = n3;
        }

        static TextureAnimSpec fromJson(JsonObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            int n = jsonObject.has("frames") ? Math.max(1, jsonObject.get("frames").getAsInt()) : 1;
            int n2 = jsonObject.has("frametime") ? Math.max(1, jsonObject.get("frametime").getAsInt()) : 1;
            int n3 = jsonObject.has("frame_height") ? Math.max(1, jsonObject.get("frame_height").getAsInt()) : 0;
            return new TextureAnimSpec(n, n2, n3);
        }
    }

    static final class BoneMirrorSpec {
        final Boolean mirrorPosition;
        final Boolean mirrorRotation;

        private BoneMirrorSpec(Boolean bl, Boolean bl2) {
            this.mirrorPosition = bl;
            this.mirrorRotation = bl2;
        }

        boolean isDefault() {
            return this.mirrorPosition == null && this.mirrorRotation == null;
        }

        static BoneMirrorSpec fromJson(JsonElement jsonElement) {
            if (jsonElement == null || jsonElement.isJsonNull()) {
                return null;
            }
            if (jsonElement.isJsonPrimitive()) {
                boolean bl = jsonElement.getAsBoolean();
                return new BoneMirrorSpec(bl, bl);
            }
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Boolean bl = jsonObject.has("position") ? Boolean.valueOf(jsonObject.get("position").getAsBoolean()) : null;
                Boolean bl2 = jsonObject.has("rotation") ? Boolean.valueOf(jsonObject.get("rotation").getAsBoolean()) : null;
                return new BoneMirrorSpec(bl, bl2);
            }
            return null;
        }
    }

    record DynamicBoneTextureSpec(String type, String bone, String source, class_2960 fallbackResource, Map<String, class_2960> resourceMap) {
        static final String TYPE_TEXTURE = "texture";
        static final String TYPE_GEO = "geo";
        static final String SOURCE_AXOL_VARIANTS = "axol_variants";

        DynamicBoneTextureSpec {
            type = type != null && !type.isBlank() ? type.trim() : "texture";
            resourceMap = resourceMap == null ? Map.of() : Map.copyOf(resourceMap);
        }

        boolean isTextureType() {
            return !"geo".equalsIgnoreCase(this.type);
        }

        boolean isGeoType() {
            return "geo".equalsIgnoreCase(this.type);
        }

        boolean usesAxolVariants() {
            return "axol_variants".equalsIgnoreCase(this.source);
        }

        static DynamicBoneTextureSpec fromJson(JsonObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            String string = DynamicBoneTextureSpec.readString(jsonObject, "type");
            if (string == null || string.isBlank()) {
                string = "texture";
            }
            boolean bl = "geo".equalsIgnoreCase(string);
            String string2 = DynamicBoneTextureSpec.readString(jsonObject, "bone");
            if (bl || string2 != null && !string2.isBlank()) {
                JsonObject jsonObject2;
                String string3 = DynamicBoneTextureSpec.readString(jsonObject, "source");
                if (string3 != null) {
                    string3 = string3.trim();
                }
                class_2960 class_29602 = DynamicBoneTextureSpec.readResourceId(jsonObject, "fallback", bl);
                HashMap<String, class_2960> hashMap = new HashMap<String, class_2960>();
                JsonObject jsonObject3 = jsonObject2 = jsonObject.has("map") && jsonObject.get("map").isJsonObject() ? jsonObject.getAsJsonObject("map") : null;
                if (jsonObject2 != null) {
                    for (Map.Entry entry : jsonObject2.entrySet()) {
                        class_2960 class_29603;
                        String string4 = (String)entry.getKey();
                        if (string4 == null || string4.isBlank() || entry.getValue() == null || !((JsonElement)entry.getValue()).isJsonPrimitive() || (class_29603 = class_2960.method_12829((String)((JsonElement)entry.getValue()).getAsString())) == null) continue;
                        hashMap.put(string4.trim(), bl ? BedrockItemDefinition.normalizeGeoId(class_29603) : BedrockItemDefinition.normalizeTextureId(class_29603));
                    }
                }
                return class_29602 == null && hashMap.isEmpty() ? null : new DynamicBoneTextureSpec(string.trim(), string2 == null ? null : string2.trim(), string3, class_29602, hashMap);
            }
            return null;
        }

        private static String readString(JsonObject jsonObject, String string) {
            if (jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string) != null && jsonObject.get(string).isJsonPrimitive()) {
                String string2 = jsonObject.get(string).getAsString();
                return string2 != null && !string2.isBlank() ? string2.trim() : null;
            }
            return null;
        }

        private static class_2960 readResourceId(JsonObject jsonObject, String string, boolean bl) {
            String string2 = DynamicBoneTextureSpec.readString(jsonObject, string);
            if (string2 == null) {
                return null;
            }
            class_2960 class_29602 = class_2960.method_12829((String)string2);
            if (class_29602 == null) {
                return null;
            }
            return bl ? BedrockItemDefinition.normalizeGeoId(class_29602) : BedrockItemDefinition.normalizeTextureId(class_29602);
        }
    }

    record BonePhysicsSpec(boolean enableCreaturePhysics, boolean enableSlimePhysics, ToolTuning.PhysicsMode pendulumMode, ToolTuning.PhysicsSettings pendulumSettings, float impactInclinationPercent, float fallInclinationPercent, float impactTranslationPercent, float fallTranslationPercent, float slimeImpactPercent, float slimeFallPercent) {
        private static final float DEFAULT_INCLINATION_PERCENT = 100.0f;

        boolean isEmpty() {
            return !this.enableCreaturePhysics && !this.enableSlimePhysics && (this.pendulumMode == null || this.pendulumMode == ToolTuning.PhysicsMode.NONE);
        }

        float impactInclinationScale() {
            return this.impactInclinationPercent / 100.0f;
        }

        float fallInclinationScale() {
            return this.fallInclinationPercent / 100.0f;
        }

        float impactTranslationScale() {
            return this.impactTranslationPercent / 100.0f;
        }

        float fallTranslationScale() {
            return this.fallTranslationPercent / 100.0f;
        }

        float slimeImpactScale() {
            return this.slimeImpactPercent / 100.0f;
        }

        float slimeFallScale() {
            return this.slimeFallPercent / 100.0f;
        }

        BonePhysicsSpec merge(BonePhysicsSpec bonePhysicsSpec) {
            return bonePhysicsSpec == null ? this : new BonePhysicsSpec(this.enableCreaturePhysics || bonePhysicsSpec.enableCreaturePhysics, this.enableSlimePhysics || bonePhysicsSpec.enableSlimePhysics, BonePhysicsSpec.choosePendulumMode(this.pendulumMode, bonePhysicsSpec.pendulumMode), BonePhysicsSpec.mergePendulumSettings(this.pendulumSettings, bonePhysicsSpec.pendulumSettings), BonePhysicsSpec.choosePercent(this.impactInclinationPercent, bonePhysicsSpec.impactInclinationPercent), BonePhysicsSpec.choosePercent(this.fallInclinationPercent, bonePhysicsSpec.fallInclinationPercent), BonePhysicsSpec.choosePercent(this.impactTranslationPercent, bonePhysicsSpec.impactTranslationPercent), BonePhysicsSpec.choosePercent(this.fallTranslationPercent, bonePhysicsSpec.fallTranslationPercent), BonePhysicsSpec.choosePercent(this.slimeImpactPercent, bonePhysicsSpec.slimeImpactPercent), BonePhysicsSpec.choosePercent(this.slimeFallPercent, bonePhysicsSpec.slimeFallPercent));
        }

        static BonePhysicsSpec fromJson(JsonElement jsonElement) {
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                if (jsonElement.isJsonArray()) {
                    BonePhysicsSpec bonePhysicsSpec = null;
                    for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                        BonePhysicsSpec bonePhysicsSpec2 = BonePhysicsSpec.fromJson(jsonElement2);
                        if (bonePhysicsSpec2 == null) continue;
                        bonePhysicsSpec = bonePhysicsSpec == null ? bonePhysicsSpec2 : bonePhysicsSpec.merge(bonePhysicsSpec2);
                    }
                    return bonePhysicsSpec;
                }
                if (jsonElement.isJsonPrimitive()) {
                    boolean bl = BedrockItemDefinition.readBoolean(jsonElement, false);
                    return new BonePhysicsSpec(bl, bl, ToolTuning.PhysicsMode.NONE, null, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f);
                }
                if (!jsonElement.isJsonObject()) {
                    return null;
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                boolean bl = false;
                boolean bl2 = false;
                if (jsonObject.has("enableCreaturePhysics")) {
                    bl = BedrockItemDefinition.readBoolean(jsonObject.get("enableCreaturePhysics"), false);
                } else if (jsonObject.has("creature")) {
                    bl = BedrockItemDefinition.readBoolean(jsonObject.get("creature"), false);
                } else if (jsonObject.has("puffer")) {
                    bl = BedrockItemDefinition.readBoolean(jsonObject.get("puffer"), false);
                }
                if (jsonObject.has("enableSlimePhysics")) {
                    bl2 = BedrockItemDefinition.readBoolean(jsonObject.get("enableSlimePhysics"), false);
                } else if (jsonObject.has("slime")) {
                    bl2 = BedrockItemDefinition.readBoolean(jsonObject.get("slime"), false);
                }
                ToolTuning.PhysicsMode physicsMode = BonePhysicsSpec.readPendulumMode(jsonObject);
                ToolTuning.PhysicsSettings physicsSettings = BonePhysicsSpec.readPendulumSettings(jsonObject);
                float f = BonePhysicsSpec.readPercent(jsonObject, "impactInclinationPercent", "impact_inclination_percent", "impactInclination", "impact_inclination", "landingInclinationPercent", "landing_inclination_percent", "landingInclination", "landing_inclination");
                float f2 = BonePhysicsSpec.readPercent(jsonObject, "fallInclinationPercent", "fall_inclination_percent", "fallInclination", "fall_inclination", "upwardInclinationPercent", "upward_inclination_percent", "upwardInclination", "upward_inclination");
                float f3 = BonePhysicsSpec.readPercent(jsonObject, "impactTranslationPercent", "impact_translation_percent", "landingTranslationPercent", "landing_translation_percent", "impactTranslation", "impact_translation", "landingTranslation", "landing_translation");
                float f4 = BonePhysicsSpec.readPercent(jsonObject, "fallTranslationPercent", "fall_translation_percent", "upwardTranslationPercent", "upward_translation_percent", "fallTranslation", "fall_translation", "upwardTranslation", "upward_translation");
                float f5 = BonePhysicsSpec.readPercent(jsonObject, "slimeImpactPercent", "slime_impact_percent", "impactSlimePercent", "impact_slime_percent", "slimeImpact", "slime_impact");
                float f6 = BonePhysicsSpec.readPercent(jsonObject, "slimeFallPercent", "slime_fall_percent", "fallSlimePercent", "fall_slime_percent", "slimeFall", "slime_fall");
                return new BonePhysicsSpec(bl, bl2, physicsMode, physicsSettings, f, f2, f3, f4, f5, f6);
            }
            return null;
        }

        private static float choosePercent(float f, float f2) {
            return f2 != 100.0f ? f2 : f;
        }

        private static ToolTuning.PhysicsMode choosePendulumMode(ToolTuning.PhysicsMode physicsMode, ToolTuning.PhysicsMode physicsMode2) {
            return physicsMode2 != null && physicsMode2 != ToolTuning.PhysicsMode.NONE ? physicsMode2 : physicsMode;
        }

        private static ToolTuning.PhysicsSettings mergePendulumSettings(ToolTuning.PhysicsSettings physicsSettings, ToolTuning.PhysicsSettings physicsSettings2) {
            if (physicsSettings == null) {
                return physicsSettings2 == null ? null : physicsSettings2.copy();
            }
            if (physicsSettings2 == null) {
                return physicsSettings.copy();
            }
            ToolTuning.PhysicsSettings physicsSettings3 = physicsSettings.copy();
            if (physicsSettings2.limitDeg != null) {
                physicsSettings3.limitDeg = physicsSettings2.limitDeg;
            }
            if (physicsSettings2.limitDegUp != null) {
                physicsSettings3.limitDegUp = physicsSettings2.limitDegUp;
            }
            if (physicsSettings2.limitDegDown != null) {
                physicsSettings3.limitDegDown = physicsSettings2.limitDegDown;
            }
            if (physicsSettings2.force != null) {
                physicsSettings3.force = physicsSettings2.force;
            }
            if (physicsSettings2.speed != null) {
                physicsSettings3.speed = physicsSettings2.speed;
            }
            if (physicsSettings2.cameraSens != null) {
                physicsSettings3.cameraSens = physicsSettings2.cameraSens;
            }
            if (physicsSettings2.gravity != null) {
                physicsSettings3.gravity = physicsSettings2.gravity;
            }
            if (physicsSettings2.motionSens != null) {
                physicsSettings3.motionSens = physicsSettings2.motionSens;
            }
            if (physicsSettings2.invert != null) {
                physicsSettings3.invert = physicsSettings2.invert;
            }
            return physicsSettings3;
        }

        private static float readPercent(JsonObject jsonObject, String ... stringArray) {
            if (jsonObject != null && stringArray != null) {
                for (String string : stringArray) {
                    JsonElement jsonElement;
                    if (string == null || !jsonObject.has(string) || (jsonElement = jsonObject.get(string)) == null || jsonElement.isJsonNull()) continue;
                    try {
                        return class_3532.method_15363((float)jsonElement.getAsFloat(), (float)0.0f, (float)100.0f);
                    }
                    catch (RuntimeException runtimeException) {
                    }
                }
                return 100.0f;
            }
            return 100.0f;
        }

        private static ToolTuning.PhysicsMode readPendulumMode(JsonObject jsonObject) {
            if (jsonObject == null) {
                return ToolTuning.PhysicsMode.NONE;
            }
            ToolTuning.PhysicsMode physicsMode = BonePhysicsSpec.parsePendulumMode(jsonObject.get("pendulum_mode"));
            if (physicsMode != ToolTuning.PhysicsMode.NONE) {
                return physicsMode;
            }
            physicsMode = BonePhysicsSpec.parsePendulumMode(jsonObject.get("pendulumMode"));
            if (physicsMode != ToolTuning.PhysicsMode.NONE) {
                return physicsMode;
            }
            physicsMode = BonePhysicsSpec.parsePendulumMode(jsonObject.get("pendulum"));
            return physicsMode != ToolTuning.PhysicsMode.NONE ? physicsMode : BonePhysicsSpec.parsePendulumMode(jsonObject.get("physics_mode"));
        }

        private static ToolTuning.PhysicsMode parsePendulumMode(JsonElement jsonElement) {
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                try {
                    String string;
                    if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isBoolean()) {
                        return jsonElement.getAsBoolean() ? ToolTuning.PhysicsMode.FULL_PENDULUM : ToolTuning.PhysicsMode.NONE;
                    }
                    String string2 = jsonElement.getAsString();
                    if (string2 == null) {
                        return ToolTuning.PhysicsMode.NONE;
                    }
                    return switch (string = string2.trim().toLowerCase(Locale.ROOT).replace('-', '_').replace(' ', '_')) {
                        case "full", "pendulum_full", "full_pendulum" -> ToolTuning.PhysicsMode.FULL_PENDULUM;
                        case "forward", "foward", "pendulum_forward", "pendulum_foward", "forward_pendulum", "foward_pendulum" -> ToolTuning.PhysicsMode.FORWARD_PENDULUM;
                        default -> ToolTuning.PhysicsMode.NONE;
                    };
                }
                catch (RuntimeException runtimeException) {
                    return ToolTuning.PhysicsMode.NONE;
                }
            }
            return ToolTuning.PhysicsMode.NONE;
        }

        private static ToolTuning.PhysicsSettings readPendulumSettings(JsonObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            ToolTuning.PhysicsSettings physicsSettings = new ToolTuning.PhysicsSettings();
            physicsSettings.limitDeg = BonePhysicsSpec.readOptionalFloat(jsonObject, "limit_deg", "limitDeg", "pendulum_limit_deg", "pendulumLimitDeg");
            physicsSettings.limitDegUp = BonePhysicsSpec.readOptionalFloat(jsonObject, "limit_deg_up", "limitDegUp", "pendulum_limit_deg_up", "pendulumLimitDegUp");
            physicsSettings.limitDegDown = BonePhysicsSpec.readOptionalFloat(jsonObject, "limit_deg_down", "limitDegDown", "pendulum_limit_deg_down", "pendulumLimitDegDown");
            physicsSettings.force = BonePhysicsSpec.readOptionalFloat(jsonObject, "force", "pendulum_force", "pendulumForce");
            physicsSettings.speed = BonePhysicsSpec.readOptionalFloat(jsonObject, "speed", "pendulum_speed", "pendulumSpeed");
            physicsSettings.cameraSens = BonePhysicsSpec.readOptionalFloat(jsonObject, "camera_sens", "cameraSens", "pendulum_camera_sens", "pendulumCameraSens");
            physicsSettings.gravity = BonePhysicsSpec.readOptionalFloat(jsonObject, "gravity", "pendulum_gravity", "pendulumGravity");
            physicsSettings.motionSens = BonePhysicsSpec.readOptionalFloat(jsonObject, "motion_sens", "motionSens", "pendulum_motion_sens", "pendulumMotionSens");
            physicsSettings.invert = BonePhysicsSpec.readOptionalBoolean(jsonObject, "invert", "pendulum_invert", "pendulumInvert");
            return BonePhysicsSpec.hasPendulumSettings(physicsSettings) ? physicsSettings : null;
        }

        private static boolean hasPendulumSettings(ToolTuning.PhysicsSettings physicsSettings) {
            return physicsSettings != null && (physicsSettings.limitDeg != null || physicsSettings.limitDegUp != null || physicsSettings.limitDegDown != null || physicsSettings.force != null || physicsSettings.speed != null || physicsSettings.cameraSens != null || physicsSettings.gravity != null || physicsSettings.motionSens != null || physicsSettings.invert != null);
        }

        private static Float readOptionalFloat(JsonObject jsonObject, String ... stringArray) {
            if (jsonObject != null && stringArray != null) {
                for (String string : stringArray) {
                    JsonElement jsonElement;
                    if (string == null || !jsonObject.has(string) || (jsonElement = jsonObject.get(string)) == null || jsonElement.isJsonNull()) continue;
                    try {
                        return Float.valueOf(jsonElement.getAsFloat());
                    }
                    catch (RuntimeException runtimeException) {
                    }
                }
                return null;
            }
            return null;
        }

        private static Boolean readOptionalBoolean(JsonObject jsonObject, String ... stringArray) {
            if (jsonObject != null && stringArray != null) {
                for (String string : stringArray) {
                    JsonElement jsonElement;
                    if (string == null || !jsonObject.has(string) || (jsonElement = jsonObject.get(string)) == null || jsonElement.isJsonNull()) continue;
                    try {
                        return jsonElement.getAsBoolean();
                    }
                    catch (RuntimeException runtimeException) {
                    }
                }
                return null;
            }
            return null;
        }
    }
}

