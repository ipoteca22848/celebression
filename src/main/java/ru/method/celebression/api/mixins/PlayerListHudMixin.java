package ru.method.celebression.api.mixins;

import java.awt.Color;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_2561;
import net.minecraft.class_266;
import net.minecraft.class_269;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_355;
import net.minecraft.class_4587;
import net.minecraft.class_5251;
import net.minecraft.class_5481;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.util.PlayerNameSanitizer;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.debug.TabProfiler;
import ru.method.celebression.utils.render.HudRenderFrame;
import ru.method.celebression.utils.render.Render2D;

@Mixin(value={class_355.class})
public abstract class PlayerListHudMixin {
    @Unique
    private static float lvTabMinX;
    @Unique
    private static float lvTabMinY;
    @Unique
    private static float lvTabMaxX;
    @Unique
    private static float lvTabMaxY;
    @Unique
    private static boolean lvTabHasBounds;
    @Unique
    private static float lvCapMinX;
    @Unique
    private static float lvCapMinY;
    @Unique
    private static float lvCapMaxX;
    @Unique
    private static float lvCapMaxY;
    @Unique
    private static boolean lvCapHas;
    @Unique
    private static long lvTabLastMs;
    @Unique
    private static final Animation lvTabOpen;
    @Unique
    private static boolean lvTabMatrixPushed;
    @Unique
    private static long lvTabBadgeLogMs;
    @Unique
    private static boolean lvRowsPathLogged;
    @Unique
    private static boolean lvRenderLogged;
    @Unique
    private static int lvDrawSampleCount;
    @Unique
    private static final Map<String, class_2561> lvRowCache;
    @Unique
    private static long lvRowCacheClearMs;
    @Unique
    private static int lvStyleDumpCount;

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void dv$renderBeforePlayerList(class_332 class_3322, int n, class_269 class_2692, class_266 class_2662, CallbackInfo callbackInfo) {
        TabProfiler.beginFrame();
        HudRenderFrame.tryDispatchBeforeOverlays(Celebression.getInstance().getEventHandler());
        if (!lvRenderLogged) {
            lvRenderLogged = true;
            int n2 = -1;
            try {
                class_310 class_3102 = class_310.method_1551();
                if (class_3102 != null && class_3102.method_1562() != null) {
                    n2 = class_3102.method_1562().method_45732().size();
                }
            }
            catch (Throwable throwable) {
            }
            Celebression.LOGGER.info("[TabBadge] tab render alive, listed entries={}", (Object)n2);
        }
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void lvCustomTabBackground(class_332 class_3322, int n, class_269 class_2692, class_266 class_2662, CallbackInfo callbackInfo) {
        long l = System.currentTimeMillis();
        if (l - lvTabLastMs > 200L) {
            lvTabOpen.ae(false);
        }
        lvTabLastMs = l;
        lvTabOpen.i(true);
        float f = Math.max(0.0f, Math.min(1.0f, lvTabOpen.rk()));
        lvCapHas = false;
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        lvTabMatrixPushed = true;
        float f2 = lvTabHasBounds ? lvTabMaxY + 8.0f : 90.0f;
        class_45872.method_46416(0.0f, -f2 * (1.0f - f), 0.0f);
        if (!UI.isCustomTabEnabled()) {
            return;
        }
        if (lvTabHasBounds) {
            float f3 = 2.0f;
            float f4 = lvTabMinX - f3;
            float f5 = lvTabMinY - f3;
            float f6 = lvTabMaxX + f3;
            float f7 = lvTabMaxY + f3;
            float f8 = 6.0f;
            Color color = UI.getCustomTabColor();
            int n2 = Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * f)));
            Render2D.drawBlurredRect(class_3322.method_51448(), f4, f5, f6 - f4, f7 - f5, f8, 12.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)n2 * 0.6f)));
            Render2D.drawRoundedRect(class_3322.method_51448(), f4, f5, f6 - f4, f7 - f5, f8, new Color(color.getRed(), color.getGreen(), color.getBlue(), n2));
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"), require=0)
    private void lvCaptureTabFill(class_332 class_3322, int n, int n2, int n3, int n4, int n5) {
        int n6 = Math.min(n, n3);
        int n7 = Math.min(n2, n4);
        int n8 = Math.max(n, n3);
        int n9 = Math.max(n2, n4);
        if (!lvCapHas) {
            lvCapMinX = n6;
            lvCapMinY = n7;
            lvCapMaxX = n8;
            lvCapMaxY = n9;
            lvCapHas = true;
        } else {
            lvCapMinX = Math.min(lvCapMinX, (float)n6);
            lvCapMinY = Math.min(lvCapMinY, (float)n7);
            lvCapMaxX = Math.max(lvCapMaxX, (float)n8);
            lvCapMaxY = Math.max(lvCapMaxY, (float)n9);
        }
        if (!UI.isCustomTabEnabled()) {
            class_3322.method_25294(n, n2, n3, n4, n5);
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void lvCommitTabBounds(class_332 class_3322, int n, class_269 class_2692, class_266 class_2662, CallbackInfo callbackInfo) {
        if (lvTabMatrixPushed) {
            class_3322.method_51448().method_22909();
            lvTabMatrixPushed = false;
        }
        if (lvCapHas) {
            lvTabMinX = lvCapMinX;
            lvTabMinY = lvCapMinY;
            lvTabMaxX = lvCapMaxX;
            lvTabMaxY = lvCapMaxY;
            lvTabHasBounds = true;
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void celebression$finishTabProfile(class_332 class_3322, int n, class_269 class_2692, class_266 class_2662, CallbackInfo callbackInfo) {
        TabProfiler.endFrame();
    }

    @Inject(method={"getPlayerName"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$sanitizeTabName(class_640 class_6402, CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        try {
            if (!lvRowsPathLogged) {
                lvRowsPathLogged = true;
                Celebression.LOGGER.info("[TabBadge] rows path alive (getPlayerName \u0432\u044b\u0437\u044b\u0432\u0430\u0435\u0442\u0441\u044f)");
            }
            String string = class_6402 == null || class_6402.method_2966() == null ? null : class_6402.method_2966().getName();
            UUID uUID = class_6402 == null || class_6402.method_2966() == null ? null : class_6402.method_2966().getId();
            class_2561 class_25612 = StreamerTextSanitizer.a((class_2561)callbackInfoReturnable.getReturnValue(), string);
            callbackInfoReturnable.setReturnValue(PlayerNameSanitizer.b(class_25612, uUID, string));
        }
        catch (Throwable throwable) {
        }
    }

    @Unique
    private static void lvSampleDraw(String string, String string2) {
        if (lvDrawSampleCount >= 6 || string2 == null) {
            return;
        }
        String string3 = string2.replace('\n', ' ').trim();
        if (string3.isEmpty()) {
            return;
        }
        ++lvDrawSampleCount;
        Celebression.LOGGER.info("[TabBadge] draw[{}] '{}'", (Object)string, (Object)string3.substring(0, Math.min(90, string3.length())));
    }

    @ModifyVariable(method={"setHeader"}, at=@At(value="HEAD"), argsOnly=true)
    private class_2561 celebression$sanitizeTabHeader(class_2561 class_25612) {
        StreamerTextSanitizer.k(class_25612);
        try {
            class_2561 class_25613 = PlayerNameSanitizer.y(class_25612);
            long l = System.currentTimeMillis();
            if (l - lvTabBadgeLogMs > 5000L) {
                lvTabBadgeLogMs = l;
                Celebression.LOGGER.info("[TabBadge] header {}", (Object)PlayerNameSanitizer.d(class_25612, class_25613));
            }
            return class_25613;
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[TabBadge] header insert threw", throwable);
            return StreamerTextSanitizer.l(class_25612);
        }
    }

    @ModifyVariable(method={"setFooter"}, at=@At(value="HEAD"), argsOnly=true)
    private class_2561 celebression$sanitizeTabFooter(class_2561 class_25612) {
        StreamerTextSanitizer.k(class_25612);
        try {
            return PlayerNameSanitizer.y(class_25612);
        }
        catch (Throwable throwable) {
            return StreamerTextSanitizer.l(class_25612);
        }
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"), index=1)
    private class_2561 celebression$sanitizeTabText(class_2561 class_25612) {
        try {
            if (lvDrawSampleCount < 6 && class_25612 != null) {
                PlayerListHudMixin.lvSampleDraw("Text", class_25612.getString());
            }
            return PlayerListHudMixin.lvProcessRowText(class_25612);
        }
        catch (Throwable throwable) {
            return class_25612;
        }
    }

    @Unique
    private static class_2561 lvProcessRowText(class_2561 class_25612) {
        class_2561 class_25613;
        class_2561 class_25614;
        if (class_25612 == null) {
            return null;
        }
        String string = class_25612.getString();
        if (string == null || string.isBlank()) {
            return class_25612;
        }
        long l = System.currentTimeMillis();
        if (l - lvRowCacheClearMs > 2000L) {
            lvRowCache.clear();
            lvRowCacheClearMs = l;
        }
        if ((class_25614 = lvRowCache.get(string)) != null) {
            return class_25614;
        }
        class_640 class_640Match = null;
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.method_1562() != null) {
            int n = 0;
            for (class_640 class_6402 : class_3102.method_1562().method_45732()) {
                String string2;
                if (class_6402 == null || class_6402.method_2966() == null || (string2 = class_6402.method_2966().getName()) == null || string2.length() < 3 || string2.length() <= n || !string.contains(string2)) continue;
                class_640Match = class_6402;
                n = string2.length();
            }
        }
        if (class_640Match != null) {
            class_2561 class_25616;
            String string3 = class_640Match.method_2966().getName();
            class_25616 = StreamerTextSanitizer.a(class_25612, string3);
            class_25613 = PlayerNameSanitizer.b(class_25616, class_640Match.method_2966().getId(), string3);
        } else {
            class_25613 = PlayerNameSanitizer.ac(class_25612);
        }
        if (class_25613 == null) {
            class_25613 = class_25612;
        }
        PlayerListHudMixin.lvDumpRowStyles(class_25612, class_25613);
        if (lvRowCache.size() < 256) {
            lvRowCache.put(string, class_25613);
        }
        return class_25613;
    }

    @Unique
    private static void lvDumpRowStyles(class_2561 class_25612, class_2561 class_25613) {
        if (lvStyleDumpCount >= 4 || class_25613 == class_25612) {
            return;
        }
        ++lvStyleDumpCount;
        StringBuilder stringBuilder = new StringBuilder();
        class_5251[] class_5251Array = new class_5251[]{null};
        boolean[] blArray = new boolean[]{true};
        class_25613.method_30937().accept((n, class_25832, n2) -> {
            class_5251 class_52512;
            class_5251 class_52513 = class_52512 = class_25832 == null ? null : class_25832.method_10973();
            if (blArray[0] || !Objects.equals(class_52512, class_5251Array[0])) {
                stringBuilder.append('[').append(class_52512 == null ? "def" : class_52512.toString()).append(']');
                class_5251Array[0] = class_52512;
                blArray[0] = false;
            }
            stringBuilder.appendCodePoint(n2);
            return stringBuilder.length() < 220;
        });
        Celebression.LOGGER.info("[TabBadge] rowStyled {}", (Object)stringBuilder);
    }

    @ModifyArg(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I"), index=1, require=0)
    private class_5481 celebression$sanitizeTabOrderedText(class_5481 class_54812) {
        try {
            if (lvDrawSampleCount < 6 && class_54812 != null) {
                PlayerListHudMixin.lvSampleDraw("Ordered", PlayerListHudMixin.lvPlainOf(class_54812));
            }
            return PlayerNameSanitizer.f(class_54812);
        }
        catch (Throwable throwable) {
            return class_54812;
        }
    }

    @Unique
    private static String lvPlainOf(class_5481 class_54812) {
        StringBuilder stringBuilder = new StringBuilder();
        class_54812.accept((n, class_25832, n2) -> {
            stringBuilder.appendCodePoint(n2);
            return stringBuilder.length() < 120;
        });
        return stringBuilder.toString();
    }

    static {
        lvTabOpen = new Animation(280L, 1.0, false, Easing.aiZ);
        lvRowCache = new ConcurrentHashMap<String, class_2561>();
    }
}
