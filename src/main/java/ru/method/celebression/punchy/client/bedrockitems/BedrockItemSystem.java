package ru.method.celebression.punchy.client.bedrockitems;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.bedrockitems.BedrockAxolPhysicsStateMachine;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemDefinition;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemRenderer;
import ru.method.celebression.punchy.client.bedrockitems.BedrockPufferPhysicsStateMachine;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class BedrockItemSystem {
    private static final BedrockItemSystem INSTANCE = new BedrockItemSystem();
    private static final EnumMap<class_1306, Matrix4f> CURRENT_PHYSICS_DELTA = new EnumMap(class_1306.class);
    private final List<BedrockItemDefinition> definitions = new ArrayList<BedrockItemDefinition>();
    private final BedrockAxolPhysicsStateMachine axolPhysicsState = new BedrockAxolPhysicsStateMachine();
    private final BedrockPufferPhysicsStateMachine pufferPhysicsState = new BedrockPufferPhysicsStateMachine();

    private BedrockItemSystem() {
    }

    public static BedrockItemSystem get() {
        return INSTANCE;
    }

    public static void setCurrentPhysicsDelta(class_1306 class_13062, Matrix4f matrix4f) {
        if (class_13062 != null) {
            if (matrix4f == null) {
                CURRENT_PHYSICS_DELTA.remove(class_13062);
            } else {
                CURRENT_PHYSICS_DELTA.put(class_13062, new Matrix4f((Matrix4fc)matrix4f));
            }
        }
    }

    public static Matrix4f getCurrentPhysicsDelta(class_1306 class_13062) {
        if (class_13062 == null) {
            return null;
        }
        Matrix4f matrix4f = CURRENT_PHYSICS_DELTA.get(class_13062);
        return matrix4f == null ? null : new Matrix4f((Matrix4fc)matrix4f);
    }

    public static void clearCurrentPhysicsDelta(class_1306 class_13062) {
        if (class_13062 != null) {
            CURRENT_PHYSICS_DELTA.remove(class_13062);
        }
    }

    public void onResourceReload(class_3300 class_33002) {
        if (!PunchyConfig.disableResourcePackModelParts()) {
            this.definitions.clear();
            this.definitions.addAll(BedrockItemDefinition.loadAll(class_33002));
            for (BedrockItemDefinition bedrockItemDefinition : this.definitions) {
                bedrockItemDefinition.loadResources(class_33002);
            }
        }
    }

    public void tick(class_310 class_3102) {
        if (!PunchyConfig.disableResourcePackModelParts()) {
            PunchyAnimationManager.updateItemOverrides(class_3102);
            BedrockItemAnimationManager.tick(class_3102);
            this.axolPhysicsState.tick(class_3102);
            this.pufferPhysicsState.tick(class_3102);
        }
    }

    BedrockAxolPhysicsStateMachine.FeedbackPose getAxolPhysicsPose(class_1306 class_13062, float f) {
        return this.axolPhysicsState.getPose(class_13062, f);
    }

    BedrockPufferPhysicsStateMachine.FeedbackPose getPufferPhysicsPose(class_1306 class_13062, float f) {
        return this.pufferPhysicsState.getPose(class_13062, f);
    }

    public boolean hasSlimeBonePhysics(class_1799 class_17992) {
        if (!PunchyConfig.disableResourcePackModelParts() && class_17992 != null && !class_17992.method_7960()) {
            BedrockItemDefinition bedrockItemDefinition = this.resolveDefinition(class_17992);
            if (bedrockItemDefinition != null && bedrockItemDefinition.hasSlimeBonePhysics()) {
                return true;
            }
            JsonElement jsonElement = ToolTuning.getSpecificBonePhysics(class_17992);
            if (jsonElement == null) {
                return false;
            }
            Map<String, BedrockItemDefinition.BonePhysicsSpec> map = BedrockItemDefinition.parseBonePhysics(jsonElement);
            for (BedrockItemDefinition.BonePhysicsSpec bonePhysicsSpec : map.values()) {
                if (bonePhysicsSpec == null || !bonePhysicsSpec.enableSlimePhysics()) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean tryRenderItem(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f, boolean bl) {
        if (PunchyConfig.disableResourcePackModelParts()) {
            return false;
        }
        if (class_17992 == null || class_17992.method_7960() || class_7462 == null || class_13062 == null) {
            return false;
        }
        if (this.isBlockedByCustomName(class_17992)) {
            return false;
        }
        BedrockItemDefinition bedrockItemDefinition = this.resolveDefinition(class_17992);
        return bedrockItemDefinition == null ? false : BedrockItemRenderer.render(bedrockItemDefinition, class_17992, class_7462, class_13062, class_45872, class_45972, n, f, bl);
    }

    BedrockItemDefinition resolveDefinition(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            BedrockItemDefinition bedrockItemDefinition = null;
            boolean bl = false;
            for (BedrockItemDefinition bedrockItemDefinition2 : this.definitions) {
                if (bedrockItemDefinition2 == null || !BedrockItemSystem.matchesItemList(class_17992, bedrockItemDefinition2.getItems())) continue;
                if (bedrockItemDefinition2.hasCustomName()) {
                    bl = true;
                    if (!bedrockItemDefinition2.matchesCustomName(class_17992)) continue;
                    return bedrockItemDefinition2;
                }
                if (bedrockItemDefinition != null) continue;
                bedrockItemDefinition = bedrockItemDefinition2;
            }
            return bl ? null : bedrockItemDefinition;
        }
        return null;
    }

    static boolean matchesItemList(class_1799 class_17992, List<String> list) {
        if (class_17992 != null && !class_17992.method_7960() && list != null && !list.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.toString();
            for (String string2 : list) {
                class_6862 class_68622;
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim()).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_17992.method_31573(class_68622 = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    boolean isBlockedByCustomName(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            boolean bl = false;
            for (BedrockItemDefinition bedrockItemDefinition : this.definitions) {
                if (bedrockItemDefinition == null || !BedrockItemSystem.matchesItemList(class_17992, bedrockItemDefinition.getItems()) || !bedrockItemDefinition.hasCustomName()) continue;
                bl = true;
                if (!bedrockItemDefinition.matchesCustomName(class_17992)) continue;
                return false;
            }
            return bl;
        }
        return false;
    }
}
