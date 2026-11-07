package ru.method.celebression.api.mixins;

import com.mojang.brigadier.suggestion.Suggestion;
import java.util.List;
import net.minecraft.class_310;
import net.minecraft.class_4717;
import net.minecraft.class_768;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.util.StreamerTextSanitizer;

@Mixin(value={class_4717.class_464.class})
public abstract class ChatInputSuggestorWindowMixin {
    @Mutable
    @Final
    @Shadow
    private class_768 field_2771;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void celebression$resizeForSanitizedSuggestions(class_4717 class_47172, int n, int n2, int n3, List<Suggestion> list, boolean bl, CallbackInfo callbackInfo) {
        if (this.field_2771 == null || list == null || list.isEmpty()) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.field_1772 == null) {
            return;
        }
        int n4 = this.field_2771.method_3319();
        for (Suggestion suggestion : list) {
            String string;
            String string2;
            if (suggestion == null || (string2 = StreamerTextSanitizer.dq(string = suggestion.getText())) == null || string2.equals(string)) continue;
            n4 = Math.max(n4, class_3102.field_1772.method_1727(string2));
        }
        if (n4 > this.field_2771.method_3319()) {
            this.field_2771 = new class_768(this.field_2771.method_3321(), this.field_2771.method_3322(), n4, this.field_2771.method_3320());
        }
    }
}

