package ru.method.celebression.punchy.client.state;

import java.util.Locale;
import net.minecraft.class_1937;
import net.minecraft.class_1959;
import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_3532;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_7924;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class SweatStateMachine {
    private static final class_6862<class_1959> DESERT_BIOME_TAG = class_6862.method_40092((class_5321)class_7924.field_41236, (class_2960)class_2960.method_60655((String)"minecraft", (String)"is_desert"));
    private static final int STAGE_COUNT = 4;
    private static final int STAGE_TICKS = 5;
    private static final int FADE_TICKS = 40;
    private static int stage = 0;
    private static float stageProgress = 0.0f;
    private static float fadeAlpha = 0.0f;
    private static boolean active = false;
    private static boolean fadingOut = false;
    private static boolean wetActive = false;
    private static boolean wetAnimate = false;
    private static int fadeTicks = 0;
    private static int fadeOutTicks = 0;
    private static int ticks;
    private static boolean suppressInWater;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            if (!PunchyConfig.enableSweat()) {
                SweatStateMachine.reset();
            } else {
                suppressInWater = class_3102.field_1724.method_5777(class_3486.field_15517);
                if (!wetActive) {
                    boolean bl = SweatStateMachine.isInDesert(class_3102);
                    if (!bl) {
                        if (active) {
                            if (!fadingOut) {
                                fadingOut = true;
                                fadeOutTicks = 40;
                            }
                            if (fadeOutTicks > 0) {
                                fadeAlpha = class_3532.method_15363((float)((float)(--fadeOutTicks) / 40.0f), (float)0.0f, (float)1.0f);
                            } else {
                                SweatStateMachine.reset();
                            }
                        }
                    } else {
                        if (fadingOut) {
                            fadingOut = false;
                            fadeTicks = class_3532.method_15340((int)((int)(fadeAlpha * 40.0f)), (int)0, (int)40);
                        }
                        if (!active) {
                            active = true;
                            fadeTicks = 0;
                        }
                        if (fadeTicks < 40) {
                            fadeAlpha = class_3532.method_15363((float)((float)(++fadeTicks) / 40.0f), (float)0.0f, (float)1.0f);
                            stage = 1;
                            stageProgress = 0.0f;
                        } else {
                            fadeAlpha = 1.0f;
                            int n = ++ticks / 5 % 4;
                            stage = n + 1;
                            stageProgress = (float)(ticks % 5) / 5.0f;
                        }
                    }
                } else if (fadeTicks < 40) {
                    fadeAlpha = class_3532.method_15363((float)((float)(++fadeTicks) / 40.0f), (float)0.0f, (float)1.0f);
                    stage = 1;
                    stageProgress = 0.0f;
                } else {
                    fadeAlpha = 1.0f;
                    if (wetAnimate) {
                        int n = ++ticks / 5 % 4;
                        stage = n + 1;
                        stageProgress = (float)(ticks % 5) / 5.0f;
                    } else {
                        stage = 1;
                        stageProgress = 0.0f;
                    }
                }
            }
        }
    }

    public static int getStage() {
        return stage;
    }

    public static float getStageProgress() {
        return stageProgress;
    }

    public static float getFadeAlpha() {
        return suppressInWater ? 0.0f : fadeAlpha;
    }

    public static void startWet(boolean bl) {
        if (wetActive) {
            wetAnimate = bl;
        } else {
            wetActive = true;
            wetAnimate = bl;
            active = true;
            fadingOut = false;
            fadeTicks = 0;
            fadeOutTicks = 0;
            fadeAlpha = 0.0f;
            ticks = 0;
            stage = 1;
            stageProgress = 0.0f;
        }
    }

    public static void startWet() {
        SweatStateMachine.startWet(true);
    }

    public static void stopWet() {
        if (wetActive) {
            wetActive = false;
            wetAnimate = false;
            SweatStateMachine.reset();
        }
    }

    private static void reset() {
        ticks = 0;
        stage = 0;
        stageProgress = 0.0f;
        fadeAlpha = 0.0f;
        active = false;
        fadeTicks = 0;
        fadingOut = false;
        fadeOutTicks = 0;
        wetActive = false;
        wetAnimate = false;
        suppressInWater = false;
    }

    private static boolean isInDesert(class_310 class_3102) {
        class_1937 class_19372 = class_3102.field_1724.method_37908();
        if (class_19372 == null) {
            return false;
        }
        class_2338 class_23382 = class_3102.field_1724.method_24515();
        class_6880 class_68802 = class_19372.method_23753(class_23382);
        if (class_68802.method_40220(DESERT_BIOME_TAG)) {
            return true;
        }
        String string = String.valueOf(class_68802).toLowerCase(Locale.ROOT);
        return string.contains("desert");
    }

    static {
        suppressInWater = false;
    }
}

