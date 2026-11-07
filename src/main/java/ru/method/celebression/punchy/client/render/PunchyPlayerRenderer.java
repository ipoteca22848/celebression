package ru.method.celebression.punchy.client.render;

import ru.method.celebression.animation.Animation;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1068;
import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_742;
import net.minecraft.class_7833;
import net.minecraft.class_897;
import net.minecraft.class_922;
import ru.method.celebression.punchy.client.access.TransformablePart;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;

public class PunchyPlayerRenderer {
    public static void render(class_742 class_7422, float f, class_4587 class_45872, class_4597 class_45972, int n) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_7422 != null && class_45972 != null) {
            class_897 renderer = class_3102.method_1561().method_3953((class_1297)class_7422);
            if (!(renderer instanceof class_922 playerRenderer) || !(playerRenderer.method_4038() instanceof class_591 playerModel)) {
                return;
            }
            playerModel.field_3401.method_41923();
            playerModel.field_27433.method_41923();
            class_45872.method_22903();
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(180.0f));
            Map<String, class_630> armParts = new HashMap<>();
            armParts.put("RightArm", playerModel.field_3401);
            armParts.put("LeftArm", playerModel.field_27433);
            PunchyAnimationManager.POSE_HANDLER.applyTransformations(armParts, f);
            PunchyAnimationManager.SWIM_HANDLER.applyTransformations(armParts, f, false);
            PunchyPlayerRenderer.copyMatrixToSleeve(playerModel.field_3401, playerModel.field_3486);
            PunchyPlayerRenderer.copyMatrixToSleeve(playerModel.field_27433, playerModel.field_3484);
            class_2960 class_29602 = class_1068.method_4649();
            if (class_7422.method_52814() != null && class_7422.method_52814().comp_1626() != null) {
                class_29602 = class_7422.method_52814().comp_1626();
            }
            class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29602));
            playerModel.field_3401.method_22698(class_45872, class_45882, n, class_4608.field_21444);
            playerModel.field_27433.method_22698(class_45872, class_45882, n, class_4608.field_21444);
            playerModel.field_3486.method_22698(class_45872, class_45882, n, class_4608.field_21444);
            playerModel.field_3484.method_22698(class_45872, class_45882, n, class_4608.field_21444);
            class_45872.method_22909();
        }
    }

    private static void copyMatrixToSleeve(class_630 class_6302, class_630 class_6303) {
        if ((Object)class_6302 instanceof TransformablePart source && (Object)class_6303 instanceof TransformablePart target) {
            target.punchy$setExplicitTransform(source.punchy$getExplicitTransform());
        }
    }
}
