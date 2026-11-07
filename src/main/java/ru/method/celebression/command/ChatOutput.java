package ru.method.celebression.command;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.ColorUtils;

public final class ChatOutput
implements MinecraftClientAccess {
    private static final String ajj = "Celebression > ";

    public static void cG(String string) {
        if (string == null) {
            return;
        }
        aiG.execute(() -> {
            if (ChatOutput.aiG.field_1724 == null) {
                return;
            }
            class_5250 class_52502 = ChatOutput.rv();
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string).method_10862(class_2583.field_24360.method_10982(Boolean.valueOf(false)).method_27703(class_5251.method_27717((int)new Color(200, 200, 200).getRGB()))));
            ChatOutput.aiG.field_1724.method_7353((class_2561)class_52502, false);
        });
    }

    public static void b(class_5250 class_52502) {
        if (class_52502 == null) {
            return;
        }
        aiG.execute(() -> {
            if (ChatOutput.aiG.field_1724 == null) {
                return;
            }
            class_5250 class_52503 = ChatOutput.rv();
            class_52503.method_10852((class_2561)class_52502.method_27661());
            ChatOutput.aiG.field_1724.method_7353((class_2561)class_52503, false);
        });
    }

    public static boolean h(class_2561 class_25612) {
        return class_25612 != null && class_25612.getString().startsWith("Celebression > ");
    }

    private static class_5250 rv() {
        class_5250 class_52502 = class_2561.method_43470((String)"");
        String string = "Celebression";
        for (int i = 0; i < string.length(); ++i) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)("" + string.charAt(i))).method_10862(class_2583.field_24360.method_10982(Boolean.valueOf(true)).method_27703(class_5251.method_27717((int)ColorUtils.gradient((float)i / (float)string.length()).getRGB()))));
        }
        class_52502.method_10852((class_2561)class_2561.method_43470((String)" > ").method_10862(class_2583.field_24360.method_10982(Boolean.valueOf(false)).method_27703(class_5251.method_27717((int)new Color(200, 200, 200).getRGB()))));
        return class_52502;
    }

    @Generated
    private ChatOutput() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

