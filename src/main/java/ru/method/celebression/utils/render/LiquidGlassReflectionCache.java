package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_6367;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ru.method.celebression.screen.clickgui.ClickGui;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.RenderFrameTracker;

public final class LiquidGlassReflectionCache {
    private static final int MAIN_MENU_CAPTURE_DOWNSAMPLE = 1;
    private static final int GAMEPLAY_CAPTURE_DOWNSAMPLE = 2;
    private static final int GAMEPLAY_CAPTURE_DOWNSAMPLE_CLICKGUI = 4;
    private static class_6367 mainMenuFramebuffer;
    private static class_6367 gameplayFramebuffer;
    private static class_6367 clickGuiFramebuffer;
    private static boolean mainMenuCaptured;
    private static boolean gameplayCaptured;
    private static boolean clickGuiCaptured;
    private static volatile boolean mainMenuRequested;
    private static volatile boolean gameplayRequested;
    private static volatile long mainMenuCapturedFrameId;
    private static volatile long gameplayCapturedFrameId;
    private static volatile long clickGuiCapturedFrameId;
    private static volatile long gameplayUsedFrameId;

    private LiquidGlassReflectionCache() {
    }

    public static void captureMainMenu() {
        LiquidGlassReflectionCache.captureMainMenu(false);
    }

    public static void captureMainMenu(boolean bl) {
        RenderSystem.assertOnRenderThread();
        if (!bl && !LiquidGlassReflectionCache.needsCapture(ReflectionSource.MAIN_MENU)) {
            return;
        }
        if (LiquidGlassReflectionCache.alreadyCapturedThisFrame(ReflectionSource.MAIN_MENU)) {
            LiquidGlassReflectionCache.clearRequest(ReflectionSource.MAIN_MENU);
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        class_437 class_4372 = class_3102.field_1755;
        if (!(class_4372 instanceof CustomMainMenuScreen)) {
            mainMenuCaptured = false;
            mainMenuCapturedFrameId = -1L;
            if (mainMenuFramebuffer != null) {
                mainMenuFramebuffer.method_1238();
                mainMenuFramebuffer = null;
            }
            LiquidGlassReflectionCache.clearRequest(ReflectionSource.MAIN_MENU);
            return;
        }
        LiquidGlassReflectionCache.capture(class_3102.method_1522(), ReflectionSource.MAIN_MENU);
    }

    public static void captureGameplay() {
        LiquidGlassReflectionCache.captureGameplay(false);
    }

    public static void ensureGameplayCapturedForFrame() {
        if (LiquidGlassReflectionCache.isCapturedThisFrame(ReflectionSource.GAMEPLAY)) {
            return;
        }
        if (ColorUtils.blocksGameplayReflectionRecapture()) {
            return;
        }
        LiquidGlassReflectionCache.requestCapture(ReflectionSource.GAMEPLAY);
        LiquidGlassReflectionCache.captureGameplay(true);
    }

    public static void captureGameplayForHudFrame() {
        if (LiquidGlassReflectionCache.isCapturedThisFrame(ReflectionSource.GAMEPLAY)) {
            return;
        }
        if (RenderFrameTracker.getFrameId() - gameplayUsedFrameId > 3L) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.field_1687 == null) {
            return;
        }
        LiquidGlassReflectionCache.captureGameplay(true);
    }

    public static void markGameplayReflectionUsed() {
        gameplayUsedFrameId = RenderFrameTracker.getFrameId();
    }

    public static void ensureMainMenuCapturedForFrame() {
        LiquidGlassReflectionCache.requestCapture(ReflectionSource.MAIN_MENU);
        LiquidGlassReflectionCache.captureMainMenu(true);
    }

    public static void captureIfRequested(ReflectionSource reflectionSource) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            LiquidGlassReflectionCache.captureMainMenu(false);
        } else if (reflectionSource == ReflectionSource.CLICK_GUI) {
            LiquidGlassReflectionCache.captureClickGuiBackdrop();
        } else {
            LiquidGlassReflectionCache.captureGameplay(false);
        }
    }

    public static void captureClickGuiBackdrop() {
        LiquidGlassReflectionCache.captureClickGuiBackdrop(false);
    }

    public static void captureClickGuiBackdrop(boolean bl) {
        RenderSystem.assertOnRenderThread();
        if (!bl && LiquidGlassReflectionCache.alreadyCapturedThisFrame(ReflectionSource.CLICK_GUI)) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || !(class_3102.field_1755 instanceof ClickGui)) {
            clickGuiCaptured = false;
            clickGuiCapturedFrameId = -1L;
            return;
        }
        LiquidGlassReflectionCache.capture(class_3102.method_1522(), ReflectionSource.CLICK_GUI);
    }

    public static void captureGameplay(boolean bl) {
        RenderSystem.assertOnRenderThread();
        class_310 class_3102 = class_310.method_1551();
        if (!bl && !LiquidGlassReflectionCache.needsCapture(ReflectionSource.GAMEPLAY)) {
            return;
        }
        if (LiquidGlassReflectionCache.alreadyCapturedThisFrame(ReflectionSource.GAMEPLAY)) {
            LiquidGlassReflectionCache.clearRequest(ReflectionSource.GAMEPLAY);
            return;
        }
        if (class_3102.field_1687 == null) {
            gameplayCaptured = false;
            gameplayCapturedFrameId = -1L;
            if (gameplayFramebuffer != null) {
                gameplayFramebuffer.method_1238();
                gameplayFramebuffer = null;
            }
            LiquidGlassReflectionCache.clearRequest(ReflectionSource.GAMEPLAY);
            return;
        }
        LiquidGlassReflectionCache.capture(class_3102.method_1522(), ReflectionSource.GAMEPLAY);
    }

    public static void invalidate(ReflectionSource reflectionSource) {
        LiquidGlassReflectionCache.setCaptured(reflectionSource, false);
        LiquidGlassReflectionCache.setCapturedFrameId(reflectionSource, -1L);
    }

    public static class_276 getFramebuffer(ReflectionSource reflectionSource, class_276 class_2762) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            if (mainMenuCaptured && mainMenuFramebuffer != null) {
                return mainMenuFramebuffer;
            }
            return class_2762;
        }
        if (reflectionSource == ReflectionSource.CLICK_GUI) {
            if (!clickGuiCaptured || clickGuiFramebuffer == null) {
                LiquidGlassReflectionCache.captureClickGuiBackdrop(true);
            }
            if (clickGuiCaptured && clickGuiFramebuffer != null) {
                return clickGuiFramebuffer;
            }
            if (gameplayCaptured && gameplayFramebuffer != null) {
                return gameplayFramebuffer;
            }
            return class_2762;
        }
        if (gameplayCaptured && gameplayFramebuffer != null) {
            return gameplayFramebuffer;
        }
        if (ColorUtils.blocksGameplayReflectionRecapture()) {
            if (gameplayFramebuffer != null) {
                return gameplayFramebuffer;
            }
            return class_2762;
        }
        return class_2762;
    }

    public static void requestCapture(ReflectionSource reflectionSource) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            mainMenuRequested = true;
        } else {
            gameplayRequested = true;
        }
    }

    public static boolean needsCapture(ReflectionSource reflectionSource) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            return mainMenuRequested;
        }
        return gameplayRequested;
    }

    public static boolean isCapturedThisFrame(ReflectionSource reflectionSource) {
        long l = RenderFrameTracker.getFrameId();
        return switch (reflectionSource.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                if (mainMenuCapturedFrameId == l) {
                    yield true;
                }
                yield false;
            }
            case 2 -> {
                if (clickGuiCapturedFrameId == l) {
                    yield true;
                }
                yield false;
            }
            case 1 -> gameplayCapturedFrameId == l;
        };
    }

    public static void clear() {
        if (mainMenuFramebuffer != null) {
            mainMenuFramebuffer.method_1238();
            mainMenuFramebuffer = null;
        }
        if (gameplayFramebuffer != null) {
            gameplayFramebuffer.method_1238();
            gameplayFramebuffer = null;
        }
        if (clickGuiFramebuffer != null) {
            clickGuiFramebuffer.method_1238();
            clickGuiFramebuffer = null;
        }
        mainMenuCaptured = false;
        gameplayCaptured = false;
        clickGuiCaptured = false;
        mainMenuRequested = false;
        gameplayRequested = false;
        mainMenuCapturedFrameId = -1L;
        gameplayCapturedFrameId = -1L;
        clickGuiCapturedFrameId = -1L;
    }

    public static class_276 getMainMenuFramebuffer(class_276 class_2762) {
        return LiquidGlassReflectionCache.getFramebuffer(ReflectionSource.MAIN_MENU, class_2762);
    }

    public static class_276 getGameplayFramebuffer(class_276 class_2762) {
        return LiquidGlassReflectionCache.getFramebuffer(ReflectionSource.GAMEPLAY, class_2762);
    }

    private static void capture(class_276 class_2762, ReflectionSource reflectionSource) {
        LiquidGlassReflectionCache.setCaptured(reflectionSource, false);
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        if (n <= 0 || n2 <= 0) {
            LiquidGlassReflectionCache.clearRequest(reflectionSource);
            return;
        }
        int n3 = LiquidGlassReflectionCache.resolveDownsample(reflectionSource);
        int n4 = Math.max(1, (n + n3 - 1) / n3);
        int n5 = Math.max(1, (n2 + n3 - 1) / n3);
        class_6367 class_63672 = LiquidGlassReflectionCache.getOrCreateFramebuffer(reflectionSource, n4, n5);
        LiquidGlassReflectionCache.blitColor(class_2762, (class_276)class_63672);
        LiquidGlassReflectionCache.setCaptured(reflectionSource, true);
        LiquidGlassReflectionCache.setCapturedFrameId(reflectionSource, RenderFrameTracker.getFrameId());
        LiquidGlassReflectionCache.clearRequest(reflectionSource);
    }

    public static void blitColor(class_276 class_2762, class_276 class_2763) {
        if (class_2762 == null || class_2763 == null) {
            return;
        }
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        int n3 = class_2763.field_1482;
        int n4 = class_2763.field_1481;
        if (n <= 0 || n2 <= 0 || n3 <= 0 || n4 <= 0) {
            return;
        }
        if (class_2762.field_1476 <= 0 || class_2763.field_1476 <= 0) {
            class_2763.method_1235(false);
            class_2762.method_1237(n3, n4);
            class_2762.method_1235(false);
            return;
        }
        int n5 = GL11.glGetInteger((int)36010);
        int n6 = GL11.glGetInteger((int)36006);
        GL30.glBindFramebuffer((int)36008, (int)class_2762.field_1476);
        GL30.glBindFramebuffer((int)36009, (int)class_2763.field_1476);
        GL30.glBlitFramebuffer((int)0, (int)0, (int)n, (int)n2, (int)0, (int)0, (int)n3, (int)n4, (int)16384, (int)9729);
        GL30.glBindFramebuffer((int)36008, (int)n5);
        GL30.glBindFramebuffer((int)36009, (int)n6);
        class_2762.method_1235(false);
    }

    private static int resolveDownsample(ReflectionSource reflectionSource) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            return 1;
        }
        if (reflectionSource == ReflectionSource.CLICK_GUI) {
            return 4;
        }
        return 2;
    }

    private static class_6367 getOrCreateFramebuffer(ReflectionSource reflectionSource, int n, int n2) {
        class_6367 class_63672 = switch (reflectionSource) {
            case MAIN_MENU -> mainMenuFramebuffer;
            case GAMEPLAY -> gameplayFramebuffer;
            case CLICK_GUI -> clickGuiFramebuffer;
        };
        if (class_63672 == null || class_63672.field_1482 != n || class_63672.field_1481 != n2) {
            if (class_63672 != null) {
                class_63672.method_1238();
            }
            class_63672 = new class_6367(n, n2, false);
            LiquidGlassReflectionCache.configureTextureFilter(class_63672);
            LiquidGlassReflectionCache.assignFramebuffer(reflectionSource, class_63672);
        }
        return class_63672;
    }

    private static void assignFramebuffer(ReflectionSource reflectionSource, class_6367 class_63672) {
        switch (reflectionSource.ordinal()) {
            case 0: {
                mainMenuFramebuffer = class_63672;
                break;
            }
            case 2: {
                clickGuiFramebuffer = class_63672;
                break;
            }
            case 1: {
                gameplayFramebuffer = class_63672;
            }
        }
    }

    private static void clearRequest(ReflectionSource reflectionSource) {
        if (reflectionSource == ReflectionSource.MAIN_MENU) {
            mainMenuRequested = false;
        } else {
            gameplayRequested = false;
        }
    }

    private static void setCaptured(ReflectionSource reflectionSource, boolean bl) {
        switch (reflectionSource.ordinal()) {
            case 0: {
                mainMenuCaptured = bl;
                break;
            }
            case 2: {
                clickGuiCaptured = bl;
                break;
            }
            case 1: {
                gameplayCaptured = bl;
            }
        }
    }

    private static boolean alreadyCapturedThisFrame(ReflectionSource reflectionSource) {
        long l = RenderFrameTracker.getFrameId();
        return LiquidGlassReflectionCache.getCapturedFrameId(reflectionSource) == l;
    }

    private static long getCapturedFrameId(ReflectionSource reflectionSource) {
        return switch (reflectionSource.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> mainMenuCapturedFrameId;
            case 2 -> clickGuiCapturedFrameId;
            case 1 -> gameplayCapturedFrameId;
        };
    }

    private static void setCapturedFrameId(ReflectionSource reflectionSource, long l) {
        switch (reflectionSource.ordinal()) {
            case 0: {
                mainMenuCapturedFrameId = l;
                break;
            }
            case 2: {
                clickGuiCapturedFrameId = l;
                break;
            }
            case 1: {
                gameplayCapturedFrameId = l;
            }
        }
    }

    private static void configureTextureFilter(class_6367 class_63672) {
        class_63672.method_58226(9729);
        RenderSystem.bindTexture((int)class_63672.method_30277());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        RenderSystem.bindTexture((int)0);
    }

    static {
        mainMenuCapturedFrameId = -1L;
        gameplayCapturedFrameId = -1L;
        clickGuiCapturedFrameId = -1L;
        gameplayUsedFrameId = -1L;
    }

    public static enum ReflectionSource {
        MAIN_MENU,
        GAMEPLAY,
        CLICK_GUI;

    }
}
