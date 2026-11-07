package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.class_2172;
import net.minecraft.class_2583;
import net.minecraft.class_342;
import net.minecraft.class_4717;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.StreamerTextSanitizer;

@Mixin(value={class_4717.class})
public abstract class ChatInputSuggestorMixin {
    @Final
    @Shadow
    class_342 field_21599;
    @Shadow
    boolean field_21614;
    @Shadow
    private ParseResults<class_2172> field_21610;
    @Shadow
    private CompletableFuture<Suggestions> field_21611;
    @Shadow
    private class_4717.class_464 field_21612;

    @Shadow
    public abstract void method_23920(boolean var1);

    @Inject(method={"refresh"}, at={@At(value="INVOKE", target="Lcom/mojang/brigadier/StringReader;canRead()Z", remap=false)}, cancellable=true)
    public void refresh(CallbackInfo callbackInfo, @Local StringReader stringReader) {
        String string = Celebression.getInstance().getCommandManager().fj();
        if (stringReader.canRead(string.length()) && stringReader.getString().startsWith(string, stringReader.getCursor()) && !Celebression.getInstance().isPanic()) {
            String string2 = this.field_21599.method_1882();
            int n = stringReader.getCursor() + string.length();
            String string3 = Celebression.getInstance().getCommandManager().aB(string2.substring(n));
            StringReader stringReader2 = new StringReader(string + string3);
            CommandDispatcher<class_2172> commandDispatcher = Celebression.getInstance().getCommandManager().fg();
            stringReader2.setCursor(n);
            this.field_21610 = commandDispatcher.parse(stringReader2, Celebression.getInstance().getCommandManager().fh());
            int n2 = this.field_21599.method_1881();
            if (!(n2 < n || this.field_21612 != null && this.field_21614)) {
                this.field_21611 = commandDispatcher.getCompletionSuggestions(this.field_21610, n2);
                this.field_21611.thenRun(() -> {
                    if (this.field_21611.isDone()) {
                        this.method_23920(false);
                    }
                });
            }
            callbackInfo.cancel();
        }
    }

    @Inject(method={"provideRenderText"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$maskSensitiveRenderText(String string, int n, CallbackInfoReturnable<class_5481> callbackInfoReturnable) {
        String string2 = StreamerTextSanitizer.b(this.field_21599.method_1882(), n, string == null ? 0 : string.length());
        if (string2 != null) {
            callbackInfoReturnable.setReturnValue(class_5481.method_30747((String)string2, (class_2583)class_2583.field_24360));
        }
    }

    @Inject(method={"show"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$hideSensitiveSuggestions(boolean bl, CallbackInfo callbackInfo) {
        if (StreamerTextSanitizer.dw(this.field_21599.method_1882())) {
            callbackInfo.cancel();
        }
    }
}

