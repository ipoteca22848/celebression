package ru.method.celebression.punchy.client.config;

import ru.method.celebression.animation.Animation;

import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.minecraft.class_1074;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_342;
import net.minecraft.class_3532;
import net.minecraft.class_357;
import net.minecraft.class_364;
import net.minecraft.class_410;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_5481;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.config.ConfigProfileManager;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.client.tuning.MixThePacksScreen;
import ru.method.celebression.punchy.client.tuning.TuningWorkbenchScreen;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.platform.Services;

public final class PunchyConfigScreen
extends class_437 {
    private static final int CONTENT_WIDTH = 320;
    private static final int SIDEBAR_WIDTH = 120;
    private static final int SIDEBAR_GAP = 16;
    private static final int MAIN_PANEL_GAP = 12;
    private static final int PROFILE_PANEL_WIDTH = 220;
    private static final int ROW_HEIGHT = 20;
    private static final int ROW_SPACING = 6;
    private static final int RESET_GAP = 10;
    private static final int RESET_WIDTH = 44;
    private static final int BLACKLIST_MODAL_ROW_HEIGHT = 22;
    private static final int BLACKLIST_MODAL_FILTER_ROWS = 2;
    private static final String BLACKLIST_BULK_ENABLE_KEY = "__bulk_enable__";
    private static final String BLACKLIST_BULK_DUAL_KEY = "__bulk_dual__";
    private final class_437 parent;
    private final PunchyConfig.Data data;
    private Tab tab = Tab.SUPPORT;
    private class_342 blacklistInput;
    private String blacklistDraft = "";
    private int dividerX;
    private int dividerTop;
    private int dividerBottom;
    private List<class_5481> blacklistHelpLines;
    private int blacklistHelpX;
    private int blacklistHelpY;
    private int blacklistHelpWidth;
    private int blacklistHelpHeight;
    private final List<class_4185> blacklistEntryButtons = new ArrayList<class_4185>();
    private final List<class_4185> blacklistDualButtons = new ArrayList<class_4185>();
    private final List<class_4185> blacklistRemoveButtons = new ArrayList<class_4185>();
    private int blacklistScrollOffset;
    private int blacklistListStartY;
    private int blacklistViewportHeight;
    private int blacklistEntryHeight;
    private int blacklistScrollbarReserve;
    private final List<class_339> animationWidgets = new ArrayList<class_339>();
    private final List<Integer> animationWidgetBaseY = new ArrayList<Integer>();
    private int animationScrollOffset;
    private int animationListStartY;
    private int animationViewportHeight;
    private int animationEntryHeight;
    private int animationRowCount;
    private boolean draggingBlacklistScrollbar;
    private boolean draggingAnimationScrollbar;
    private boolean draggingBlacklistModalScrollbar;
    private boolean draggingBlacklistNamespaceScrollbar;
    private boolean blacklistModalOpen;
    private class_342 blacklistSearchInput;
    private String blacklistSearchDraft = "";
    private int blacklistModalLeft;
    private int blacklistModalTop;
    private int blacklistModalRight;
    private int blacklistModalBottom;
    private int blacklistListLeft;
    private int blacklistListRight;
    private int blacklistListTop;
    private int blacklistListBottom;
    private int blacklistCloseButtonLeft;
    private int blacklistCloseButtonTop;
    private int blacklistCloseButtonRight;
    private int blacklistCloseButtonBottom;
    private int blacklistResetButtonLeft;
    private int blacklistResetButtonTop;
    private int blacklistResetButtonRight;
    private int blacklistResetButtonBottom;
    private int blacklistDoneButtonLeft;
    private int blacklistDoneButtonTop;
    private int blacklistDoneButtonRight;
    private int blacklistDoneButtonBottom;
    private int blacklistModalScrollOffset;
    private int blacklistModalMaxScroll;
    private int blacklistNamespaceScrollOffset;
    private int blacklistNamespaceMaxScroll;
    private int blacklistNamespaceAreaLeft;
    private int blacklistNamespaceAreaRight;
    private int blacklistNamespaceAreaTop;
    private int blacklistNamespaceAreaBottom;
    private final List<BlacklistCatalogEntry> blacklistCatalogEntries = new ArrayList<BlacklistCatalogEntry>();
    private final List<BlacklistCatalogEntry> blacklistFilteredEntries = new ArrayList<BlacklistCatalogEntry>();
    private final List<BlacklistModFilter> blacklistModFilters = new ArrayList<BlacklistModFilter>();
    private String blacklistSelectedNamespace = "";
    private final List<BlacklistHitbox> blacklistNamespaceHitboxes = new ArrayList<BlacklistHitbox>();
    private final List<BlacklistHitbox> blacklistEnableHitboxes = new ArrayList<BlacklistHitbox>();
    private final List<BlacklistHitbox> blacklistDualHitboxes = new ArrayList<BlacklistHitbox>();
    private final List<class_4185> profileRowButtons = new ArrayList<class_4185>();
    private List<String> profiles = List.of();
    private class_342 profileNameInput;
    private class_4185 profileSaveButton;
    private class_4185 profileLoadButton;
    private class_4185 profileDeleteButton;
    private class_4185 profileOpenFolderButton;
    private int selectedProfile = -1;
    private String activeProfileName = null;
    private int profileListScroll = 0;
    private int profilePanelX = 0;
    private int profilePanelY = 0;
    private int profileListX = 0;
    private int profileListY = 0;
    private int profileListW = 0;
    private int profileListH = 0;
    private int mainPanelLeft = 0;
    private int mainPanelTop = 0;
    private int mainPanelRight = 0;
    private int mainPanelBottom = 0;
    private int leftPanelLeft = 0;
    private int leftPanelTop = 0;
    private int leftPanelRight = 0;
    private int leftPanelBottom = 0;
    private int centerPanelLeft = 0;
    private int centerPanelTop = 0;
    private int centerPanelRight = 0;
    private int centerPanelBottom = 0;
    private int rightPanelLeft = 0;
    private int rightPanelTop = 0;
    private int rightPanelRight = 0;
    private int rightPanelBottom = 0;
    private int contentPanelWidth = 320;
    private int profilePanelWidth = 220;
    private Integer previousGuiScaleValue;
    private boolean forcedGuiScale;

    public PunchyConfigScreen(class_437 class_4372) {
        super((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.title"));
        this.parent = class_4372;
        this.data = PunchyConfig.snapshot();
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.blacklistModalOpen && n == 256) {
            this.closeBlacklistModal();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    protected void method_25426() {
        this.applySmallScreenGuiScaleOverride();
        this.syncGuiScaleOverrideDimensions();
        this.rebuild();
    }

    private void rebuild() {
        int n;
        class_4185 class_41853;
        int n2;
        Tab tab;
        int n3;
        this.method_37067();
        this.blacklistInput = null;
        this.blacklistHelpLines = null;
        this.blacklistHelpHeight = 0;
        this.blacklistEntryButtons.clear();
        this.blacklistDualButtons.clear();
        this.blacklistRemoveButtons.clear();
        this.blacklistScrollOffset = 0;
        this.blacklistListStartY = 0;
        this.blacklistViewportHeight = 0;
        this.blacklistEntryHeight = 26;
        this.blacklistScrollbarReserve = 0;
        this.animationWidgets.clear();
        this.animationWidgetBaseY.clear();
        this.animationScrollOffset = 0;
        this.animationListStartY = 0;
        this.animationViewportHeight = 0;
        this.animationEntryHeight = 26;
        this.animationRowCount = 0;
        this.blacklistSearchInput = null;
        this.blacklistNamespaceHitboxes.clear();
        this.blacklistEnableHitboxes.clear();
        this.blacklistDualHitboxes.clear();
        this.blacklistModalMaxScroll = 0;
        this.blacklistNamespaceScrollOffset = 0;
        this.blacklistNamespaceMaxScroll = 0;
        this.profileRowButtons.clear();
        this.profileNameInput = null;
        this.profileSaveButton = null;
        this.profileLoadButton = null;
        this.profileDeleteButton = null;
        this.profileOpenFolderButton = null;
        this.profileListScroll = 0;
        if (this.tab == Tab.BLACKLIST) {
            this.tab = Tab.SUPPORT;
        }
        int n4 = 20;
        int n5 = 6;
        int n6 = 2;
        Tab[] tabArray = Tab.values();
        int n7 = (tabArray.length + n6) * (n4 + n5) - n5;
        int n8 = Math.min(800, Math.max(680, this.field_22789 - 48));
        int n9 = Math.max(520, (int)Math.round((double)n8 * 0.65));
        n9 = Math.min(n9, Math.max(520, this.field_22789 - 16));
        this.profilePanelWidth = Math.max(170, (int)Math.round(143.0));
        this.contentPanelWidth = Math.max(170, n9 - 120 - 16 - 12 - this.profilePanelWidth);
        int n10 = Math.max(8, (this.field_22789 - n9) / 2);
        int n11 = Math.max(320, this.field_22790 - 24);
        int n12 = Math.min(320, n11);
        int n13 = Math.max(190, (int)Math.round((double)n12 * 0.6));
        n13 += n6 * (n4 + n5);
        n13 = Math.min(n13, n11);
        int n14 = Math.max(8, (this.field_22790 - n13) / 2);
        int n15 = n14 + n13;
        int n16 = n10;
        int n17 = n14 + 8;
        for (n3 = 0; n3 < tabArray.length; ++n3) {
            Tab currentTab = tabArray[n3];
            int n18 = n2 = n3 >= 2 ? n3 + n6 : n3;
            if (currentTab == Tab.BLACKLIST) {
                class_41853 = class_4185.method_46430((class_2561)class_2561.method_43471((String)currentTab.labelKey), class_41852 -> this.openBlacklistModal()).method_46433(n16, n17 + n2 * (n4 + n5)).method_46437(120, n4).method_46431();
                class_41853.field_22763 = !this.blacklistModalOpen;
            } else {
                class_41853 = class_4185.method_46430((class_2561)class_2561.method_43471((String)currentTab.labelKey), class_41852 -> {
                    this.tab = currentTab;
                    this.rebuild();
                }).method_46433(n16, n17 + n2 * (n4 + n5)).method_46437(120, n4).method_46431();
                class_41853.field_22763 = this.tab != currentTab;
            }
            this.method_37063(class_41853);
        }
        n3 = n17 + 2 * (n4 + n5);
        class_4185 handEditorButton = this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)"Hand Editor"), class_41852 -> {
            if (this.field_22787 != null) {
                this.field_22787.method_1507((class_437)new TuningWorkbenchScreen(this));
            }
        }).method_46433(n16, n3).method_46437(120, n4).method_46431());
        handEditorButton.field_22763 = !this.blacklistModalOpen;
        n2 = n17 + 3 * (n4 + n5);
        class_41853 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)"Mix Packs"), class_41852 -> {
            if (this.field_22787 != null) {
                this.field_22787.method_1507((class_437)new MixThePacksScreen(this));
            }
        }).method_46433(n16, n2).method_46437(120, n4).method_46431());
        class_41853.field_22763 = !this.blacklistModalOpen;
        int n19 = n10 + 120 + 16;
        int n20 = n19 + this.contentPanelWidth + 12;
        int n21 = 0;
        int n22 = this.contentPanelWidth - 44 - 10;
        this.dividerX = n19 - 8;
        if ((this.dividerX & 1) == 1) {
            --this.dividerX;
        }
        this.dividerTop = n17 - 4;
        this.dividerBottom = n17 + n7 + 4;
        if (this.tab == Tab.SUPPORT) {
        } else if (this.tab == Tab.BLACKLIST) {
            n = 50;
            int n23 = this.contentPanelWidth - n - 10;
            String string = class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.examples").getString();
            this.blacklistHelpX = n19;
            this.blacklistHelpY = n17 + this.rowOffset(n21);
            this.blacklistHelpWidth = this.contentPanelWidth;
            float f = 1.0f;
            int n24 = (int)Math.ceil(9.0f * f);
            int n25 = Math.max(10, (int)((float)this.blacklistHelpWidth / ((float)this.field_22793.method_1727("abcdefghijklmnopqrstuvwxyz") / 26.0f)));
            this.blacklistHelpLines = PunchyConfigScreen.splitHelpText(string, n25);
            this.blacklistHelpHeight = this.blacklistHelpLines.size() * n24 + 4;
            int n26 = n17 + this.blacklistHelpHeight;
            this.blacklistInput = new class_342(this.field_22793, n19, n26 + this.rowOffset(n21), n23, 20, (class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.input"));
            this.blacklistInput.method_1852(this.blacklistDraft);
            this.method_37063(this.blacklistInput);
            this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.add"), class_41852 -> this.handleAddBlacklist()).method_46433(n19 + n23 + 10, n26 + this.rowOffset(n21)).method_46437(n, 20).method_46431());
            ++n21;
            if (this.data.itemBlacklist != null) {
                int n27 = this.data.itemBlacklist.size() >= 5 ? 4 : 5;
                this.blacklistScrollbarReserve = this.data.itemBlacklist.size() >= 5 ? 7 : 0;
                this.blacklistListStartY = n26 + this.rowOffset(n21);
                this.blacklistViewportHeight = n27 * this.blacklistEntryHeight - 6;
                for (String string2 : this.data.itemBlacklist) {
                    if (string2 == null || string2.isBlank()) continue;
                    int n28 = 66;
                    int n29 = 20;
                    int n30 = this.contentPanelWidth - n28 - n29 - 20 - this.blacklistScrollbarReserve;
                    class_4185 class_41854 = class_4185.method_46430((class_2561)class_2561.method_43470((String)string2), class_41852 -> {}).method_46433(n19, this.blacklistListStartY + this.blacklistEntryButtons.size() * this.blacklistEntryHeight).method_46437(n30, 20).method_46431();
                    class_41854.field_22763 = false;
                    this.method_37063(class_41854);
                    class_4185 class_41855 = class_4185.method_46430((class_2561)this.blacklistDualLabel(this.isBlacklistDualHanded(string2)), class_41852 -> {
                        this.setBlacklistDualHanded(string2, !this.isBlacklistDualHanded(string2));
                        this.rebuild();
                    }).method_46433(n19 + n30 + 10, this.blacklistListStartY + this.blacklistDualButtons.size() * this.blacklistEntryHeight).method_46437(n28, 20).method_46431();
                    this.method_37063(class_41855);
                    class_4185 class_41856 = class_4185.method_46430((class_2561)class_2561.method_43470((String)"X"), class_41852 -> {
                        this.data.itemBlacklist.remove(string2);
                        if (this.data.blacklistApplyDualHanded != null) {
                            this.data.blacklistApplyDualHanded.remove(string2);
                        }
                        this.rebuild();
                    }).method_46433(n19 + n30 + 10 + n28 + 10, this.blacklistListStartY + this.blacklistRemoveButtons.size() * this.blacklistEntryHeight).method_46437(n29, 20).method_46431();
                    this.method_37063(class_41856);
                    this.blacklistEntryButtons.add(class_41854);
                    this.blacklistDualButtons.add(class_41855);
                    this.blacklistRemoveButtons.add(class_41856);
                    ++n21;
                }
                this.updateBlacklistScroll();
            }
        } else if (this.tab == Tab.RENDERING) {
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.render_armor_arms_fp", () -> this.data.renderArmorArmsFP, bl -> {
                this.data.renderArmorArmsFP = bl;
            }, false);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_enhanced_fire_arm_effects", () -> !this.data.disableEnhancedFireArmEffects, bl -> {
                this.data.disableEnhancedFireArmEffects = bl == false;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_blur_glow_at_dark", () -> this.data.enableBlurGlowAtDark, bl -> {
                this.data.enableBlurGlowAtDark = bl;
            }, true);
        } else if (this.tab == Tab.ANIMATION) {
            n = 24;
            int n31 = n15 - (n + 8);
            this.animationListStartY = n17;
            int n32 = 7;
            this.animationViewportHeight = Math.min(Math.max(0, n31 - 12 - this.animationListStartY), n32 * this.animationEntryHeight - 6);
            int n33 = this.addToggleWithResetScrollable(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_particles", () -> this.data.enableParticles, bl -> {
                this.data.enableParticles = bl;
            }, true);
            int n34 = this.addToggleWithResetScrollable(n33, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_model_parts_glow", () -> this.data.enableModelPartsGlow, bl -> {
                this.data.enableModelPartsGlow = bl;
            }, true);
            AnimationSpeedSlider animationSpeedSlider = new AnimationSpeedSlider(n19, n17 + this.rowOffset(n34++), n22, 20, this.data);
            this.registerAnimationWidget(animationSpeedSlider, animationSpeedSlider.method_46427());
            this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41852 -> {
                this.data.animationSpeed = 5.5f;
                animationSpeedSlider.setSpeed(this.data.animationSpeed);
                PunchyAnimationManager.setAnimationSpeed(this.data.animationSpeed);
            }).method_46433(n19 + n22 + 10, n17 + this.rowOffset(n34 - 1)).method_46437(44, 20).method_46431(), n17 + this.rowOffset(n34 - 1));
            int n35 = this.addToggleWithResetScrollable(n34, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_boat_fp_animations", () -> !this.data.disableBoatFirstPersonAnimations, bl -> {
                this.data.disableBoatFirstPersonAnimations = bl == false;
            }, true);
            int n36 = this.addToggleWithResetScrollable(n35, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_custom_walk", () -> this.data.enableCustomWalk, bl -> {
                this.data.enableCustomWalk = bl;
            }, true);
            int n37 = this.addToggleWithResetScrollable(n36, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_sprint_arm_swing", () -> this.data.enableSprintArmSwing, bl -> {
                this.data.enableSprintArmSwing = bl;
            }, true);
            int n38 = this.addToggleWithResetScrollable(n37, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_fall_arm_animation", () -> this.data.enableFallArmAnimation, bl -> {
                this.data.enableFallArmAnimation = bl;
            }, true);
            SprintArmSwingIntensitySlider sprintArmSwingIntensitySlider = new SprintArmSwingIntensitySlider(n19, n17 + this.rowOffset(n38++), n22, 20, this.data);
            this.registerAnimationWidget(sprintArmSwingIntensitySlider, sprintArmSwingIntensitySlider.method_46427());
            this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41852 -> {
                this.data.sprintArmSwingIntensity = 1.0f;
                sprintArmSwingIntensitySlider.setIntensity(this.data.sprintArmSwingIntensity);
            }).method_46433(n19 + n22 + 10, n17 + this.rowOffset(n38 - 1)).method_46437(44, 20).method_46431(), n17 + this.rowOffset(n38 - 1));
            SprintArmSwingSpeedSlider sprintArmSwingSpeedSlider = new SprintArmSwingSpeedSlider(n19, n17 + this.rowOffset(n38++), n22, 20, this.data);
            this.registerAnimationWidget(sprintArmSwingSpeedSlider, sprintArmSwingSpeedSlider.method_46427());
            this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41852 -> {
                this.data.sprintArmSwingSpeed = 0.0f;
                sprintArmSwingSpeedSlider.setSpeed(this.data.sprintArmSwingSpeed);
            }).method_46433(n19 + n22 + 10, n17 + this.rowOffset(n38 - 1)).method_46437(44, 20).method_46431(), n17 + this.rowOffset(n38 - 1));
            int n39 = this.addToggleWithResetScrollable(n38, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_crawl_animation", () -> this.data.enableCrawlAnimation, bl -> {
                this.data.enableCrawlAnimation = bl;
            }, true);
            int n40 = this.addToggleWithResetScrollable(n39, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_climb_animation", () -> this.data.enableClimbAnimation, bl -> {
                this.data.enableClimbAnimation = bl;
            }, true);
            int n41 = this.addToggleWithResetScrollable(n40, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_swim_animation", () -> this.data.enableSwimAnimation, bl -> {
                this.data.enableSwimAnimation = bl;
            }, true);
            int n42 = this.addToggleWithResetScrollable(n41, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_elytra_flight_animation", () -> this.data.enableElytraFlightAnimation, bl -> {
                this.data.enableElytraFlightAnimation = bl;
            }, true);
            int n43 = this.addToggleWithResetScrollable(n42, n19, n17, n22, "ru.method.celebression.punchy.config.option.freeze_shake", () -> this.data.enableFreezeShake, bl -> {
                this.data.enableFreezeShake = bl;
            }, true);
            int n44 = this.addToggleWithResetScrollable(n43, n19, n17, n22, "ru.method.celebression.punchy.config.option.freeze_arm_overlay", () -> this.data.enableFreezeArmOverlay, bl -> {
                this.data.enableFreezeArmOverlay = bl;
            }, true);
            int n45 = this.addToggleWithResetScrollable(n44, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_mud", () -> this.data.enableMud, bl -> {
                this.data.enableMud = bl;
            }, true);
            this.animationRowCount = n21 = this.addToggleWithResetScrollable(n45, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_burn", () -> this.data.enableBurn, bl -> {
                this.data.enableBurn = bl;
            }, true);
            this.updateAnimationScroll();
        } else if (this.tab == Tab.PHYSICS) {
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_arm_physics", () -> !this.data.disableArmPhysics, bl -> {
                this.data.disableArmPhysics = bl == false;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_native_item_physics", () -> !this.data.disableNativeItemPhysics, bl -> {
                this.data.disableNativeItemPhysics = bl == false;
            }, true);
        } else if (this.tab == Tab.MODELS) {
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_boat_minecart_models", () -> !this.data.disableBoatMinecartRaftModels, bl -> {
                this.data.disableBoatMinecartRaftModels = bl == false;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_piston_models", () -> !this.data.disablePistonModels, bl -> {
                this.data.disablePistonModels = bl == false;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_chest_models", () -> !this.data.disableChestModels, bl -> {
                this.data.disableChestModels = bl == false;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_enchanting_table_models", () -> !this.data.disableEnchantingTableModels, bl -> {
                this.data.disableEnchantingTableModels = bl == false;
            }, true);
        } else {
            n21 = this.addInteractiveStuffCompatToggle(n21, n19, n17, n22);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.firstperson_hide_enabled", () -> this.data.firstPersonModelHideEnabled, bl -> {
                this.data.firstPersonModelHideEnabled = bl;
            }, true);
            n = n17 + this.rowOffset(n21);
            FirstPersonHidePitchSlider firstPersonHidePitchSlider = new FirstPersonHidePitchSlider(n19, n, n22, 20, this.data);
            this.registerAnimationWidget(firstPersonHidePitchSlider, n);
            this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41852 -> {
                this.data.firstPersonModelHidePitch = 60.0f;
                firstPersonHidePitchSlider.setPitch(this.data.firstPersonModelHidePitch);
            }).method_46433(n19 + n22 + 10, n).method_46437(44, 20).method_46431(), n);
            ++n21;
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_mod", () -> this.data.enableMod, bl -> {
                this.data.enableMod = bl;
            }, true);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.enable_tuning", () -> this.data.enableTuning, bl -> {
                this.data.enableTuning = bl;
            }, false);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.bettercombat_compat", () -> this.data.bettercombatCompat, bl -> {
                this.data.bettercombatCompat = bl;
            }, false);
            n21 = this.addToggleWithReset(n21, n19, n17, n22, "ru.method.celebression.punchy.config.option.swordblocking_compat", () -> this.data.swordBlockingCompat, bl -> {
                this.data.swordBlockingCompat = bl;
            }, false);
        }
        n = 20;
        int n46 = n15 - (n + 8);
        this.configureCenterScroll(n21, n17, n46);
        this.initProfilesPanel(n20, n17, n46);
        this.refreshProfiles(true);
        this.updateProfileRowButtons();
        this.updateMainPanelBounds(n10, n14, n9, n15);
        if (this.blacklistModalOpen) {
            this.suppressBaseWidgetsForBlacklistModal();
            this.initBlacklistModalWidgets();
        }
    }

    private void applySmallScreenGuiScaleOverride() {
        if (!this.forcedGuiScale && this.field_22787 != null && this.field_22787.field_1690 != null && this.shouldForceSmallScreenGuiScale()) {
            this.previousGuiScaleValue = (Integer)this.field_22787.field_1690.method_42474().method_41753();
            this.field_22787.method_22683().method_15997(1.0);
            this.forcedGuiScale = true;
        }
    }

    private void syncGuiScaleOverrideDimensions() {
        if (this.forcedGuiScale && this.field_22787 != null && this.field_22787.method_22683() != null) {
            this.field_22789 = this.field_22787.method_22683().method_4486();
            this.field_22790 = this.field_22787.method_22683().method_4502();
        }
    }

    private void restoreGuiScaleOverride() {
        if (this.forcedGuiScale && this.field_22787 != null && this.field_22787.field_1690 != null && this.previousGuiScaleValue != null) {
            int n;
            this.field_22787.field_1690.method_42474().method_41748(this.previousGuiScaleValue);
            int n2 = n = this.previousGuiScaleValue > 0 ? this.previousGuiScaleValue.intValue() : this.field_22787.method_22683().method_4476(this.previousGuiScaleValue.intValue(), false);
            if (n < 1) {
                n = 1;
            }
            this.field_22787.method_22683().method_15997((double)n);
            this.forcedGuiScale = false;
            this.previousGuiScaleValue = null;
        } else {
            this.forcedGuiScale = false;
            this.previousGuiScaleValue = null;
        }
    }

    private boolean shouldForceSmallScreenGuiScale() {
        if (this.field_22787 != null && this.field_22787.method_22683() != null) {
            int n = this.field_22787.method_22683().method_4480();
            int n2 = this.field_22787.method_22683().method_4507();
            return n < 1280 || n2 < 720;
        }
        return false;
    }

    private int rowOffset(int n) {
        return n * 26;
    }

    private <T extends class_339> T registerAnimationWidget(T t, int n) {
        this.animationWidgets.add(t);
        this.animationWidgetBaseY.add(n);
        t.method_46419(n - this.animationScrollOffset);
        this.method_37063(t);
        return t;
    }

    private void configureCenterScroll(int n, int n2, int n3) {
        this.animationListStartY = n2;
        int n4 = this.centerPanelBottom - 8;
        this.animationViewportHeight = Math.max(0, n4 - this.animationListStartY);
        this.animationRowCount = Math.max(0, n);
        int n5 = this.animationRowCount * this.animationEntryHeight - 6;
        int n6 = Math.max(0, n5 - this.animationViewportHeight);
        this.animationScrollOffset = PunchyConfigScreen.clamp(this.animationScrollOffset, 0, n6);
        this.updateAnimationScroll();
    }

    private void initProfilesPanel(int n, int n2, int n3) {
        int n4;
        int n5;
        this.profilePanelX = n;
        this.profilePanelY = n2;
        this.profileListX = n;
        this.profileListW = this.profilePanelWidth;
        int n6 = 14;
        int n7 = 6;
        int n8 = 2;
        this.profileListY = n2;
        int n9 = 2;
        int n10 = 6;
        int n11 = 6;
        int n12 = 80 + n9 * 3;
        int n13 = n3 - n11 - 20 - n10 - n12;
        int n14 = Math.max(n8 * n6, n13 - this.profileListY);
        int n15 = class_3532.method_15340((int)(n14 / n6), (int)n8, (int)n7);
        this.profileListH = n15 * n6;
        int n16 = this.profileListY + this.profileListH + n10;
        int n17 = n16 + 20 + n9;
        int n18 = n17 + 20 + n9;
        int n19 = n18 + 20 + n9;
        for (n5 = 0; n5 < n15; ++n5) {
            int visibleRow = n5;
            class_4185 class_41853 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43473(), class_41852 -> this.selectProfileAtVisibleRow(visibleRow)).method_46433(this.profileListX, this.profileListY + n5 * n6).method_46437(this.profileListW, n6 - 1).method_46431());
            this.profileRowButtons.add(class_41853);
        }
        this.profileNameInput = (class_342)this.method_37063(new class_342(this.field_22793, n, n16, this.profilePanelWidth, 20, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.profile_name")));
        this.profileNameInput.method_1880(64);
        n5 = (this.profilePanelWidth - 6) / 2;
        this.profileSaveButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.save_profile"), class_41852 -> {
            boolean bl;
            String string = this.profileNameInput.method_1882();
            String string2 = ConfigProfileManager.sanitizeProfileName(string);
            if (string2.isBlank()) {
                string2 = "config_profile";
                this.profileNameInput.method_1852(string2);
            }
            if (bl = ConfigProfileManager.saveCurrentAs(string2, this.data)) {
                ConfigProfileManager.setActiveProfileName(string2);
                this.activeProfileName = string2;
                this.refreshProfiles(false);
                this.selectProfileByName(string2);
                this.updateProfileRowButtons();
            }
        }).method_46433(n, n17).method_46437(n5, 20).method_46431());
        this.profileLoadButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.load_profile"), class_41852 -> {
            PunchyConfig.Data data;
            String string = this.selectedProfileName();
            PunchyConfig.Data data2 = data = string == null ? null : ConfigProfileManager.load(string);
            if (data != null) {
                ConfigProfileManager.setActiveProfileName(string);
                this.activeProfileName = string;
                this.applyProfileDataIntoCurrent(data);
                this.rebuild();
            }
        }).method_46433(n + n5 + 6, n17).method_46437(this.profilePanelWidth - n5 - 6, 20).method_46431());
        this.profileDeleteButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.delete_profile"), class_41852 -> this.openDeleteProfileConfirm()).method_46433(n, n18).method_46437(this.profilePanelWidth, 20).method_46431());
        n4 = this.profilePanelWidth;
        int n20 = 20;
        this.profileOpenFolderButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.open_profiles_folder"), class_41852 -> this.openConfigProfilesFolder()).method_46433(n, n19).method_46437(n4, n20).method_46431());
        int n21 = (this.profilePanelWidth - 6) / 2;
        this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset"), class_41852 -> {
            this.resetAll();
            this.rebuild();
        }).method_46433(n, n3).method_46437(n21, 20).method_46431());
        this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.done"), class_41852 -> this.saveAndClose()).method_46433(n + n21 + 6, n3).method_46437(this.profilePanelWidth - n21 - 6, 20).method_46431());
    }

    private void updateMainPanelBounds(int n, int n2, int n3, int n4) {
        this.mainPanelLeft = Math.max(4, n - 10);
        this.mainPanelRight = Math.min(this.field_22789 - 4, n + n3 + 10);
        this.mainPanelTop = Math.max(4, n2 - 8);
        this.mainPanelBottom = Math.min(this.field_22790 - 4, n4 + 8);
        this.leftPanelLeft = Math.max(4, n - 4);
        this.leftPanelTop = Math.max(4, n2);
        this.leftPanelRight = Math.min(this.field_22789 - 4, n + 120 + 4);
        this.leftPanelBottom = Math.min(this.field_22790 - 4, n4);
        int n5 = n + 120 + 16;
        this.centerPanelLeft = Math.max(4, n5 - 4);
        this.centerPanelTop = Math.max(4, n2);
        this.centerPanelRight = Math.min(this.field_22789 - 4, n5 + this.contentPanelWidth + 4);
        this.centerPanelBottom = Math.min(this.field_22790 - 4, n4);
        this.rightPanelLeft = Math.max(4, this.profilePanelX - 4);
        this.rightPanelTop = Math.max(4, n2);
        this.rightPanelRight = Math.min(this.field_22789 - 4, this.profilePanelX + this.profilePanelWidth + 4);
        this.rightPanelBottom = Math.min(this.field_22790 - 4, n4);
    }

    private void refreshProfiles(boolean bl) {
        this.profiles = ConfigProfileManager.listProfiles();
        this.activeProfileName = ConfigProfileManager.getActiveProfileName();
        if (this.activeProfileName != null && !this.profiles.contains(this.activeProfileName)) {
            this.activeProfileName = null;
            ConfigProfileManager.setActiveProfileName(null);
        }
        int n = Math.max(0, this.profiles.size() - this.profileRowButtons.size());
        this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)n);
        if (this.profiles.isEmpty()) {
            this.selectedProfile = -1;
            if (this.profileNameInput != null) {
                this.profileNameInput.method_1852("");
            }
        } else {
            if (this.selectedProfile < 0 || this.selectedProfile >= this.profiles.size() || bl) {
                this.selectedProfile = this.activeProfileName != null ? this.profiles.indexOf(this.activeProfileName) : (bl ? -1 : 0);
                this.selectedProfile = class_3532.method_15340((int)this.selectedProfile, (int)-1, (int)(this.profiles.size() - 1));
            }
            this.ensureSelectedVisibleInProfileList();
            String string = this.selectedProfileName();
            if (this.profileNameInput != null) {
                this.profileNameInput.method_1852(string == null ? "" : string);
            }
        }
    }

    private void updateProfileRowButtons() {
        for (int i = 0; i < this.profileRowButtons.size(); ++i) {
            class_4185 class_41852 = this.profileRowButtons.get(i);
            int n = this.profileListScroll + i;
            if (n >= 0 && n < this.profiles.size()) {
                String string = this.profiles.get(n);
                boolean bl = string != null && string.equals(this.activeProfileName);
                class_41852.field_22764 = true;
                class_41852.field_22763 = true;
                class_41852.method_25355((class_2561)class_2561.method_43470((String)((bl ? "> " : "") + string)));
                continue;
            }
            class_41852.field_22764 = true;
            class_41852.field_22763 = false;
            class_41852.method_25355((class_2561)class_2561.method_43473());
        }
    }

    private void selectProfileAtVisibleRow(int n) {
        int n2 = this.profileListScroll + n;
        if (n2 >= 0 && n2 < this.profiles.size()) {
            this.selectedProfile = n2;
            String string = this.selectedProfileName();
            if (string != null && this.profileNameInput != null) {
                this.profileNameInput.method_1852(string);
            }
            this.ensureSelectedVisibleInProfileList();
            this.updateProfileRowButtons();
        }
    }

    private void ensureSelectedVisibleInProfileList() {
        if (this.selectedProfile >= 0) {
            int n = this.profileRowButtons.size();
            if (this.selectedProfile < this.profileListScroll) {
                this.profileListScroll = this.selectedProfile;
            } else if (this.selectedProfile >= this.profileListScroll + n) {
                this.profileListScroll = this.selectedProfile - n + 1;
            }
            this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)Math.max(0, this.profiles.size() - n));
        }
    }

    private String selectedProfileName() {
        return this.selectedProfile >= 0 && this.selectedProfile < this.profiles.size() ? this.profiles.get(this.selectedProfile) : null;
    }

    private void selectProfileByName(String string) {
        if (string != null && !string.isBlank()) {
            for (int i = 0; i < this.profiles.size(); ++i) {
                if (!string.equals(this.profiles.get(i))) continue;
                this.selectedProfile = i;
                this.ensureSelectedVisibleInProfileList();
                if (this.profileNameInput != null) {
                    this.profileNameInput.method_1852(string);
                }
                return;
            }
        }
    }

    private void openDeleteProfileConfirm() {
        String string = this.selectedProfileName();
        class_310 class_3102 = this.field_22787;
        if (string != null && class_3102 != null) {
            class_3102.method_1507((class_437)new class_410(bl -> {
                if (bl && ConfigProfileManager.deleteProfile(string)) {
                    this.refreshProfiles(true);
                    this.updateProfileRowButtons();
                }
                class_3102.method_1507((class_437)this);
            }, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_delete_profile.title"), (class_2561)class_2561.method_43469((String)"punchy.tuning.ui.confirm_delete_profile.message", (Object[])new Object[]{string}), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_delete_profile.confirm"), (class_2561)class_2561.method_43471((String)"gui.cancel")));
        }
    }

    private boolean isMouseOverProfileList(double d, double d2) {
        return d >= (double)this.profileListX && d < (double)(this.profileListX + this.profileListW) && d2 >= (double)this.profileListY && d2 < (double)(this.profileListY + this.profileListH);
    }

    private void applyProfileDataIntoCurrent(PunchyConfig.Data data) {
        if (data != null) {
            this.data.renderArmorArmsFP = data.renderArmorArmsFP;
            this.data.interactiveStuffCompat = data.interactiveStuffCompat;
            this.data.bettercombatCompat = data.bettercombatCompat;
            this.data.swordBlockingCompat = data.swordBlockingCompat;
            this.data.animationSpeed = data.animationSpeed;
            this.data.enableMod = data.enableMod;
            this.data.enableTuning = data.enableTuning;
            this.data.disableResourcePackModelParts = data.disableResourcePackModelParts;
            this.data.disableArmPhysics = data.disableArmPhysics;
            this.data.disableNativeItemPhysics = data.disableNativeItemPhysics;
            this.data.disableBoatMinecartRaftModels = data.disableBoatMinecartRaftModels;
            this.data.disablePistonModels = data.disablePistonModels;
            this.data.disableChestModels = data.disableChestModels;
            this.data.disableEnchantingTableModels = data.disableEnchantingTableModels;
            this.data.disableBoatFirstPersonAnimations = data.disableBoatFirstPersonAnimations;
            this.data.disableEnhancedFireArmEffects = data.disableEnhancedFireArmEffects;
            this.data.enableBlurGlowAtDark = data.enableBlurGlowAtDark;
            this.data.enableCrawlAnimation = data.enableCrawlAnimation;
            this.data.enableClimbAnimation = data.enableClimbAnimation;
            this.data.enableSwimAnimation = data.enableSwimAnimation;
            this.data.enableElytraFlightAnimation = data.enableElytraFlightAnimation;
            this.data.enableCustomWalk = data.enableCustomWalk;
            this.data.enableSprintArmSwing = data.enableSprintArmSwing;
            this.data.enableFallArmAnimation = data.enableFallArmAnimation;
            this.data.sprintArmSwingIntensity = data.sprintArmSwingIntensity;
            this.data.sprintArmSwingSpeed = data.sprintArmSwingSpeed;
            this.data.enableFreezeShake = data.enableFreezeShake;
            this.data.enableFreezeArmOverlay = data.enableFreezeArmOverlay;
            this.data.enableMud = data.enableMud;
            this.data.enableSweat = data.enableSweat;
            this.data.enableBurn = data.enableBurn;
            this.data.enableParticles = data.enableParticles;
            this.data.enableModelPartsGlow = data.enableModelPartsGlow;
            this.data.firstPersonModelHideEnabled = data.firstPersonModelHideEnabled;
            this.data.firstPersonModelHidePitch = data.firstPersonModelHidePitch;
            this.data.itemBlacklist = data.itemBlacklist == null ? new ArrayList<String>() : new ArrayList<String>(data.itemBlacklist);
            this.data.blacklistApplyDualHanded = data.blacklistApplyDualHanded == null ? new LinkedHashMap<String, Boolean>() : new LinkedHashMap<String, Boolean>(data.blacklistApplyDualHanded);
            PunchyAnimationManager.setAnimationSpeed(this.data.animationSpeed);
        }
    }

    private void openConfigProfilesFolder() {
        try {
            Path path = ConfigProfileManager.profilesDirectoryPath();
            Files.createDirectories(path, new FileAttribute[0]);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(path.toFile());
            }
        }
        catch (Exception exception) {
        }
    }

    private void suppressBaseWidgetsForBlacklistModal() {
        for (class_364 class_3642 : this.method_25396()) {
            if (!(class_3642 instanceof class_339)) continue;
            class_339 class_3392 = (class_339)class_3642;
            class_3392.field_22763 = false;
            class_3392.field_22764 = false;
        }
    }

    private int addToggleWithResetScrollable(int n, int n2, int n3, int n4, String string, BooleanSupplier booleanSupplier, Consumer<Boolean> consumer, boolean bl) {
        int n5 = n3 + this.rowOffset(n);
        class_4185 class_41852 = this.createToggleButton(n2, n5, n4, string, booleanSupplier, consumer);
        this.registerAnimationWidget(class_41852, n5);
        this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41853 -> {
            consumer.accept(bl);
            class_41852.method_25355(this.toggleLabel(string, bl));
        }).method_46433(n2 + n4 + 10, n5).method_46437(44, 20).method_46431(), n5);
        return n + 1;
    }

    private void handleAddBlacklist() {
        String string;
        String string2;
        if (this.blacklistInput != null && (string2 = this.blacklistInput.method_1882()) != null && !(string = string2.trim().toLowerCase(Locale.ROOT)).isEmpty()) {
            if (string.endsWith(":")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.indexOf(58) >= 0 ? !PunchyConfigScreen.isValidNamespacedBlacklistPattern(string) : !PunchyConfigScreen.isValidNamespace(string)) {
                return;
            }
            if (this.data.itemBlacklist == null) {
                this.data.itemBlacklist = new ArrayList<String>();
            }
            if (this.data.blacklistApplyDualHanded == null) {
                this.data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>();
            }
            if (!this.data.itemBlacklist.contains(string)) {
                this.data.itemBlacklist.add(string);
            }
            this.data.blacklistApplyDualHanded.putIfAbsent(string, false);
            this.blacklistDraft = "";
            this.blacklistInput.method_1852("");
            this.rebuild();
        }
    }

    private static boolean isValidNamespace(String string) {
        if (string != null && !string.isBlank()) {
            for (int i = 0; i < string.length(); ++i) {
                boolean bl;
                char c = string.charAt(i);
                boolean bl2 = bl = c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '.';
                if (bl) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isValidNamespacedBlacklistPattern(String string) {
        if (string != null && !string.isBlank()) {
            int n = string.indexOf(58);
            if (n > 0 && n == string.lastIndexOf(58)) {
                String string2 = string.substring(0, n);
                String string3 = string.substring(n + 1);
                if (PunchyConfigScreen.isValidNamespace(string2) && !string3.isBlank()) {
                    for (int i = 0; i < string3.length(); ++i) {
                        boolean bl;
                        char c = string3.charAt(i);
                        boolean bl2 = bl = c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '.' || c == '/' || c == '*' || c == '?';
                        if (bl) continue;
                        return false;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private void openBlacklistModal() {
        this.blacklistModalOpen = true;
        this.ensureBlacklistCatalog();
        this.refreshBlacklistFilteredEntries();
        this.blacklistModalScrollOffset = 0;
        this.blacklistNamespaceScrollOffset = 0;
        this.rebuild();
    }

    private void closeBlacklistModal() {
        if (this.blacklistModalOpen) {
            this.blacklistModalOpen = false;
            this.rebuild();
        }
    }

    private void initBlacklistModalWidgets() {
        this.ensureBlacklistCatalog();
        this.computeBlacklistModalLayout();
        int n = this.blacklistModalLeft + 14;
        int n2 = Math.max(140, this.blacklistModalRight - 14 - n);
        int n3 = this.blacklistModalTop + 30;
        this.blacklistSearchInput = new class_342(this.field_22793, n, n3, n2, 20, (class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.search"));
        this.blacklistSearchInput.method_1852(this.blacklistSearchDraft);
        this.blacklistSearchInput.method_47404((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.search"));
        this.blacklistSearchInput.method_1863(string -> {
            this.blacklistSearchDraft = string == null ? "" : string;
            this.blacklistModalScrollOffset = 0;
            this.refreshBlacklistFilteredEntries();
        });
        this.method_37063(this.blacklistSearchInput);
    }

    private void ensureBlacklistCatalog() {
        if (this.blacklistCatalogEntries.isEmpty()) {
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            for (class_1792 object : class_7923.field_41178) {
                String string;
                class_2960 class_29602;
                if (object == class_1802.field_8162 || (class_29602 = class_7923.field_41178.method_10221(object)) == null) continue;
                class_1799 class_17992 = class_1799.field_8037;
                String string2 = class_29602.toString();
                try {
                    class_17992 = new class_1799((class_1935)object);
                    string = class_17992.method_7964().getString();
                    if (string != null && !string.isBlank()) {
                        string2 = string;
                    }
                }
                catch (Throwable throwable) {
                }
                string = class_29602.toString().toLowerCase(Locale.ROOT);
                String string3 = class_29602.method_12836().toLowerCase(Locale.ROOT);
                this.blacklistCatalogEntries.add(new BlacklistCatalogEntry(string, string3, string2, string2.toLowerCase(Locale.ROOT), class_17992));
                linkedHashSet.add(string3);
            }
            this.blacklistCatalogEntries.sort((blacklistCatalogEntry, blacklistCatalogEntry2) -> {
                int n = blacklistCatalogEntry.displayName.compareToIgnoreCase(blacklistCatalogEntry2.displayName);
                return n != 0 ? n : blacklistCatalogEntry.id.compareTo(blacklistCatalogEntry2.id);
            });
            for (String string : linkedHashSet) {
                this.blacklistModFilters.add(new BlacklistModFilter(string, this.resolveModDisplayName(string)));
            }
            this.blacklistModFilters.sort((blacklistModFilter, blacklistModFilter2) -> blacklistModFilter.displayName.compareToIgnoreCase(blacklistModFilter2.displayName));
        }
    }

    private void refreshBlacklistFilteredEntries() {
        this.blacklistFilteredEntries.clear();
        String string = this.blacklistSearchDraft == null ? "" : this.blacklistSearchDraft.trim().toLowerCase(Locale.ROOT);
        for (BlacklistCatalogEntry blacklistCatalogEntry3 : this.blacklistCatalogEntries) {
            if (!this.blacklistSelectedNamespace.isBlank() && !this.blacklistSelectedNamespace.equals(blacklistCatalogEntry3.namespace) || !string.isEmpty() && !blacklistCatalogEntry3.id.contains(string) && !blacklistCatalogEntry3.searchName.contains(string)) continue;
            this.blacklistFilteredEntries.add(blacklistCatalogEntry3);
        }
        this.blacklistFilteredEntries.sort((blacklistCatalogEntry, blacklistCatalogEntry2) -> {
            boolean bl;
            boolean bl2 = this.isBlacklistEnabled(blacklistCatalogEntry.id);
            if (bl2 != (bl = this.isBlacklistEnabled(blacklistCatalogEntry2.id))) {
                return bl2 ? -1 : 1;
            }
            int n = blacklistCatalogEntry.displayName.compareToIgnoreCase(blacklistCatalogEntry2.displayName);
            return n != 0 ? n : blacklistCatalogEntry.id.compareTo(blacklistCatalogEntry2.id);
        });
        this.blacklistModalScrollOffset = PunchyConfigScreen.clamp(this.blacklistModalScrollOffset, 0, Math.max(0, this.blacklistModalMaxScroll));
    }

    private void computeBlacklistModalLayout() {
        int n = Math.min(620, Math.max(460, this.field_22789 - 80));
        int n2 = Math.min(302, Math.max(202, this.field_22790 - 96));
        this.blacklistModalLeft = (this.field_22789 - n) / 2;
        this.blacklistModalTop = (this.field_22790 - n2) / 2;
        this.blacklistModalRight = this.blacklistModalLeft + n;
        this.blacklistModalBottom = this.blacklistModalTop + n2;
        this.blacklistListLeft = this.blacklistModalLeft + 14;
        this.blacklistListRight = this.blacklistModalRight - 14;
        this.blacklistCloseButtonLeft = this.blacklistModalRight - 28;
        this.blacklistCloseButtonTop = this.blacklistModalTop + 8;
        this.blacklistCloseButtonRight = this.blacklistCloseButtonLeft + 20;
        this.blacklistCloseButtonBottom = this.blacklistCloseButtonTop + 20;
        int n3 = 98;
        int n4 = 18;
        int n5 = 8;
        int n6 = n3 * 2 + n5;
        int n7 = this.blacklistModalLeft + (n - n6) / 2;
        int n8 = this.blacklistModalBottom - 26;
        this.blacklistResetButtonLeft = n7;
        this.blacklistResetButtonTop = n8;
        this.blacklistResetButtonRight = n7 + n3;
        this.blacklistResetButtonBottom = n8 + n4;
        this.blacklistDoneButtonLeft = this.blacklistResetButtonRight + n5;
        this.blacklistDoneButtonTop = n8;
        this.blacklistDoneButtonRight = this.blacklistDoneButtonLeft + n3;
        this.blacklistDoneButtonBottom = n8 + n4;
    }

    private void ensureBlacklistDataStructures() {
        if (this.data.itemBlacklist == null) {
            this.data.itemBlacklist = new ArrayList<String>();
        }
        if (this.data.blacklistApplyDualHanded == null) {
            this.data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>();
        }
    }

    private boolean isBlacklistEnabled(String string) {
        return string != null && !string.isBlank() && this.data.itemBlacklist != null ? this.data.itemBlacklist.contains(string) : false;
    }

    private void moveBlacklistEntryToTop(String string) {
        this.ensureBlacklistDataStructures();
        this.data.itemBlacklist.removeIf(string2 -> string.equals(string2));
        this.data.itemBlacklist.add(0, string);
    }

    private void toggleBlacklistEnabled(String string) {
        if (string != null && !string.isBlank()) {
            this.ensureBlacklistDataStructures();
            if (this.isBlacklistEnabled(string)) {
                this.data.itemBlacklist.removeIf(string2 -> string.equals(string2));
                this.data.blacklistApplyDualHanded.remove(string);
            } else {
                this.moveBlacklistEntryToTop(string);
                this.data.blacklistApplyDualHanded.putIfAbsent(string, false);
                this.blacklistModalScrollOffset = 0;
            }
            this.refreshBlacklistFilteredEntries();
        }
    }

    private void toggleBlacklistDual(String string) {
        if (string != null && !string.isBlank()) {
            this.ensureBlacklistDataStructures();
            if (!this.isBlacklistEnabled(string)) {
                this.moveBlacklistEntryToTop(string);
                this.data.blacklistApplyDualHanded.put(string, true);
                this.blacklistModalScrollOffset = 0;
                this.refreshBlacklistFilteredEntries();
            } else {
                boolean bl = !Boolean.TRUE.equals(this.data.blacklistApplyDualHanded.get(string));
                this.data.blacklistApplyDualHanded.put(string, bl);
            }
        }
    }

    private void toggleBlacklistNamespace(String string) {
        if (PunchyConfigScreen.isValidNamespace(string)) {
            this.blacklistSelectedNamespace = string.equals(this.blacklistSelectedNamespace) ? "" : string;
            this.blacklistModalScrollOffset = 0;
            this.refreshBlacklistFilteredEntries();
        }
    }

    private String resolveModDisplayName(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        if ("minecraft".equals(string)) {
            return "Minecraft";
        }
        String string2 = Services.PLATFORM.getModDisplayName(string);
        return string2 != null && !string2.isBlank() && !string2.equalsIgnoreCase(string) ? string2 : PunchyConfigScreen.humanizeNamespace(string);
    }

    private static String humanizeNamespace(String string) {
        String string2 = string.replace('_', ' ').replace('-', ' ').trim();
        if (string2.isBlank()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        boolean bl = true;
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (c == ' ') {
                stringBuilder.append(c);
                bl = true;
                continue;
            }
            if (bl) {
                stringBuilder.append(Character.toUpperCase(c));
                bl = false;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private boolean allFilteredEnabled() {
        if (this.blacklistFilteredEntries.isEmpty()) {
            return false;
        }
        for (BlacklistCatalogEntry blacklistCatalogEntry : this.blacklistFilteredEntries) {
            if (this.isBlacklistEnabled(blacklistCatalogEntry.id)) continue;
            return false;
        }
        return true;
    }

    private boolean allFilteredDual() {
        if (this.blacklistFilteredEntries.isEmpty()) {
            return false;
        }
        for (BlacklistCatalogEntry blacklistCatalogEntry : this.blacklistFilteredEntries) {
            if (!this.isBlacklistEnabled(blacklistCatalogEntry.id)) {
                return false;
            }
            if (this.isBlacklistDualHanded(blacklistCatalogEntry.id)) continue;
            return false;
        }
        return true;
    }

    private void setFilteredEnableAll(boolean bl) {
        if (!this.blacklistFilteredEntries.isEmpty()) {
            this.ensureBlacklistDataStructures();
            if (bl) {
                for (int i = this.blacklistFilteredEntries.size() - 1; i >= 0; --i) {
                    BlacklistCatalogEntry blacklistCatalogEntry = this.blacklistFilteredEntries.get(i);
                    this.moveBlacklistEntryToTop(blacklistCatalogEntry.id);
                    this.data.blacklistApplyDualHanded.putIfAbsent(blacklistCatalogEntry.id, false);
                }
            } else {
                for (BlacklistCatalogEntry blacklistCatalogEntry : this.blacklistFilteredEntries) {
                    this.data.itemBlacklist.removeIf(string -> blacklistCatalogEntry.id.equals(string));
                    this.data.blacklistApplyDualHanded.remove(blacklistCatalogEntry.id);
                }
            }
            this.blacklistModalScrollOffset = 0;
            this.refreshBlacklistFilteredEntries();
        }
    }

    private void setFilteredDualAll(boolean bl) {
        if (!this.blacklistFilteredEntries.isEmpty()) {
            this.ensureBlacklistDataStructures();
            for (int i = this.blacklistFilteredEntries.size() - 1; i >= 0; --i) {
                BlacklistCatalogEntry blacklistCatalogEntry = this.blacklistFilteredEntries.get(i);
                if (!this.isBlacklistEnabled(blacklistCatalogEntry.id)) {
                    this.moveBlacklistEntryToTop(blacklistCatalogEntry.id);
                }
                this.data.blacklistApplyDualHanded.put(blacklistCatalogEntry.id, bl);
            }
            this.blacklistModalScrollOffset = 0;
            this.refreshBlacklistFilteredEntries();
        }
    }

    private void resetBlacklistOnly() {
        PunchyConfig.Data data = new PunchyConfig.Data();
        this.data.itemBlacklist = new ArrayList<String>(data.itemBlacklist);
        this.data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>(data.blacklistApplyDualHanded);
        this.blacklistModalScrollOffset = 0;
        this.refreshBlacklistFilteredEntries();
    }

    private boolean isMouseInside(double d, double d2, int n, int n2, int n3, int n4) {
        return d >= (double)n && d <= (double)n3 && d2 >= (double)n2 && d2 <= (double)n4;
    }

    private int addToggleWithReset(int n, int n2, int n3, int n4, String string, BooleanSupplier booleanSupplier, Consumer<Boolean> consumer, boolean bl) {
        int n5 = n3 + this.rowOffset(n);
        class_4185 class_41852 = this.createToggleButton(n2, n5, n4, string, booleanSupplier, consumer);
        this.registerAnimationWidget(class_41852, n5);
        this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41853 -> {
            consumer.accept(bl);
            class_41852.method_25355(this.toggleLabel(string, bl));
        }).method_46433(n2 + n4 + 10, n5).method_46437(44, 20).method_46431(), n5);
        return n + 1;
    }

    private int addInteractiveStuffCompatToggle(int n, int n2, int n3, int n4) {
        int n5 = n3 + this.rowOffset(n);
        class_4185 class_41854 = class_4185.method_46430((class_2561)this.interactiveStuffCompatLabel(this.data.interactiveStuffCompat), class_41852 -> {
            this.data.interactiveStuffCompat = !this.data.interactiveStuffCompat;
            class_41852.method_25355(this.interactiveStuffCompatLabel(this.data.interactiveStuffCompat));
        }).method_46433(n2, n5).method_46437(n4, 20).method_46431();
        this.registerAnimationWidget(class_41854, n5);
        this.registerAnimationWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.reset.option"), class_41853 -> {
            this.data.interactiveStuffCompat = false;
            class_41854.method_25355(this.interactiveStuffCompatLabel(false));
        }).method_46433(n2 + n4 + 10, n5).method_46437(44, 20).method_46431(), n5);
        return n + 1;
    }

    private class_4185 createToggleButton(int n, int n2, int n3, String string, BooleanSupplier booleanSupplier, Consumer<Boolean> consumer) {
        return class_4185.method_46430((class_2561)this.toggleLabel(string, booleanSupplier.getAsBoolean()), class_41852 -> {
            boolean bl = !booleanSupplier.getAsBoolean();
            consumer.accept(bl);
            class_41852.method_25355(this.toggleLabel(string, bl));
        }).method_46433(n, n2).method_46437(n3, 20).method_46431();
    }

    private class_2561 toggleLabel(String string, boolean bl) {
        return class_2561.method_43471((String)string).method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43471((String)(bl ? "punchy.toggle.on" : "punchy.toggle.off")));
    }

    private class_2561 interactiveStuffCompatLabel(boolean bl) {
        return class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.interactive_stuff_compat").method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43471((String)(bl ? "punchy.toggle.on" : "punchy.toggle.off")));
    }

    private void saveAndClose() {
        if (this.data.enableTuning && !this.data.enableMod) {
            this.data.enableMod = true;
        }
        PunchyConfig.Data data = PunchyConfig.snapshot();
        this.data.animationPackSelections = data.animationPackSelections == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(data.animationPackSelections);
        this.data.animationPackTypeSelections = data.animationPackTypeSelections == null ? new LinkedHashMap<String, String>() : new LinkedHashMap<String, String>(data.animationPackTypeSelections);
        this.data.mixSpecificForceOverride = data.mixSpecificForceOverride == null ? new LinkedHashMap<String, Boolean>() : new LinkedHashMap<String, Boolean>(data.mixSpecificForceOverride);
        this.restoreGuiScaleOverride();
        PunchyConfig.applyAndSave(this.data);
        PunchyDebug.setDebugPositionEnabled(this.data.enableTuning && this.data.enableMod);
        PunchyAnimationManager.setAnimationSpeed(PunchyConfig.getAnimationSpeed());
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.parent);
            this.restoreGuiScaleOverride();
        }
    }

    private void resetAll() {
        PunchyConfig.Data data = new PunchyConfig.Data();
        this.data.renderArmorArmsFP = data.renderArmorArmsFP;
        this.data.interactiveStuffCompat = data.interactiveStuffCompat;
        this.data.bettercombatCompat = data.bettercombatCompat;
        this.data.swordBlockingCompat = data.swordBlockingCompat;
        this.data.animationSpeed = data.animationSpeed;
        this.data.enableMod = data.enableMod;
        this.data.enableTuning = data.enableTuning;
        this.data.disableResourcePackModelParts = data.disableResourcePackModelParts;
        this.data.disableArmPhysics = data.disableArmPhysics;
        this.data.disableNativeItemPhysics = data.disableNativeItemPhysics;
        this.data.disableBoatMinecartRaftModels = data.disableBoatMinecartRaftModels;
        this.data.disablePistonModels = data.disablePistonModels;
        this.data.disableChestModels = data.disableChestModels;
        this.data.disableEnchantingTableModels = data.disableEnchantingTableModels;
        this.data.disableBoatFirstPersonAnimations = data.disableBoatFirstPersonAnimations;
        this.data.disableEnhancedFireArmEffects = data.disableEnhancedFireArmEffects;
        this.data.enableBlurGlowAtDark = data.enableBlurGlowAtDark;
        this.data.enableCrawlAnimation = data.enableCrawlAnimation;
        this.data.enableClimbAnimation = data.enableClimbAnimation;
        this.data.enableSwimAnimation = data.enableSwimAnimation;
        this.data.enableElytraFlightAnimation = data.enableElytraFlightAnimation;
        this.data.enableCustomWalk = data.enableCustomWalk;
        this.data.enableSprintArmSwing = data.enableSprintArmSwing;
        this.data.enableFallArmAnimation = data.enableFallArmAnimation;
        this.data.sprintArmSwingIntensity = data.sprintArmSwingIntensity;
        this.data.sprintArmSwingSpeed = data.sprintArmSwingSpeed;
        this.data.enableFreezeShake = data.enableFreezeShake;
        this.data.enableFreezeArmOverlay = data.enableFreezeArmOverlay;
        this.data.enableMud = data.enableMud;
        this.data.enableSweat = data.enableSweat;
        this.data.enableBurn = data.enableBurn;
        this.data.enableParticles = data.enableParticles;
        this.data.enableModelPartsGlow = data.enableModelPartsGlow;
        this.data.firstPersonModelHideEnabled = data.firstPersonModelHideEnabled;
        this.data.firstPersonModelHidePitch = data.firstPersonModelHidePitch;
        this.data.itemBlacklist = new ArrayList<String>(data.itemBlacklist);
        this.data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>(data.blacklistApplyDualHanded);
        PunchyAnimationManager.setAnimationSpeed(this.data.animationSpeed);
    }

    public void method_25419() {
        this.saveAndClose();
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        class_3322.method_25294(0, 0, this.field_22789, this.field_22790, -1442840576);
        if (this.blacklistModalOpen) {
            this.drawConfigPanels(class_3322);
            super.method_25394(class_3322, n, n2, f);
            this.drawBlacklistModalOverlay(class_3322, n, n2);
            if (this.blacklistSearchInput != null && this.blacklistSearchInput.field_22764) {
                this.blacklistSearchInput.method_25394(class_3322, n, n2, f);
            }
        } else {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            int n12;
            int n13;
            class_4185 class_41852;
            this.drawConfigPanels(class_3322);
            ArrayList<class_4185> arrayList = new ArrayList<class_4185>();
            ArrayList<class_2561> arrayList2 = new ArrayList<class_2561>();
            ArrayList<StyledConfigSlider> arrayList3 = new ArrayList<StyledConfigSlider>();
            ArrayList<class_2561> arrayList4 = new ArrayList<class_2561>();
            for (class_364 class_3642 : this.method_25396()) {
                if (class_3642 instanceof class_4185) {
                    class_41852 = (class_4185)class_3642;
                    if (class_41852.field_22764) {
                        arrayList.add(class_41852);
                        arrayList2.add(class_41852.method_25369());
                        class_41852.method_25355((class_2561)class_2561.method_43473());
                        continue;
                    }
                }
                if (!(class_3642 instanceof StyledConfigSlider)) continue;
                StyledConfigSlider styledConfigSlider = (StyledConfigSlider)class_3642;
                if (!styledConfigSlider.field_22764) continue;
                arrayList3.add(styledConfigSlider);
                arrayList4.add(styledConfigSlider.method_25369());
                styledConfigSlider.method_25355((class_2561)class_2561.method_43473());
            }
            super.method_25394(class_3322, n, n2, f);
            for (n13 = 0; n13 < arrayList.size(); ++n13) {
                ((class_4185)arrayList.get(n13)).method_25355((class_2561)arrayList2.get(n13));
            }
            for (n13 = 0; n13 < arrayList3.size(); ++n13) {
                ((StyledConfigSlider)((Object)arrayList3.get(n13))).method_25355((class_2561)arrayList4.get(n13));
            }
            this.renderStyledSliders(class_3322, n, n2);
            this.renderStyledButtons(class_3322, n, n2);
            this.drawProfileListPanel(class_3322);
            if (this.blacklistHelpLines != null && !this.blacklistHelpLines.isEmpty()) {
                n13 = (int)Math.ceil(9.0);
                for (int i = 0; i < this.blacklistHelpLines.size(); ++i) {
                    class_5481 helpLine = this.blacklistHelpLines.get(i);
                    int n14 = this.blacklistHelpX;
                    n12 = this.blacklistHelpY + i * n13;
                    class_3322.method_51430(this.field_22793, helpLine, n14, n12, -1, false);
                }
            }
            if (this.tab == Tab.BLACKLIST && this.blacklistViewportHeight > 0 && !this.blacklistEntryButtons.isEmpty() && (n11 = Math.max(0, (n13 = this.blacklistEntryButtons.size() * this.blacklistEntryHeight - 6) - this.blacklistViewportHeight)) > 0) {
                int n15 = 4;
                int n16 = this.blacklistHelpX + this.blacklistHelpWidth - n15;
                n12 = this.blacklistListStartY;
                n10 = this.blacklistListStartY + this.blacklistViewportHeight;
                class_3322.method_25294(n16, n12, n16 + n15, n10, -1442840576);
                n9 = Math.max(1, n10 - n12);
                n8 = Math.max(8, Math.round((float)n9 * ((float)this.blacklistViewportHeight / (float)n13)));
                n7 = n9 - n8;
                n6 = n11 == 0 ? 0 : Math.round((float)n7 * ((float)this.blacklistScrollOffset / (float)n11));
                n5 = n12 + n6;
                n4 = Math.min(n10, n5 + n8);
                class_3322.method_25294(n16, n5, n16 + n15, n4, -1);
            }
            if (this.animationViewportHeight > 0 && this.animationRowCount > 0 && (n3 = Math.max(0, (n13 = this.animationRowCount * this.animationEntryHeight - 6) - this.animationViewportHeight)) > 0) {
                int n17 = 4;
                int n18 = Math.max(this.centerPanelLeft + 2, this.centerPanelRight - 7);
                n12 = this.animationListStartY;
                n10 = this.animationListStartY + this.animationViewportHeight;
                class_3322.method_25294(n18, n12, n18 + n17, n10, -1442840576);
                n9 = Math.max(1, n10 - n12);
                n8 = Math.max(8, Math.round((float)n9 * ((float)this.animationViewportHeight / (float)n13)));
                n7 = n9 - n8;
                n6 = n3 == 0 ? 0 : Math.round((float)n7 * ((float)this.animationScrollOffset / (float)n3));
                n5 = n12 + n6;
                n4 = Math.min(n10, n5 + n8);
                class_3322.method_25294(n18, n5, n18 + n17, n4, -1);
            }
        }
    }

    private void renderStyledSliders(class_332 class_3322, int n, int n2) {
        for (class_364 class_3642 : this.method_25396()) {
            if (!(class_3642 instanceof StyledConfigSlider)) continue;
            StyledConfigSlider styledConfigSlider = (StyledConfigSlider)class_3642;
            if (!styledConfigSlider.field_22764) continue;
            styledConfigSlider.drawStyled(class_3322, n, n2);
        }
    }

    private void drawConfigPanels(class_332 class_3322) {
        int n = -267381478;
        int n2 = -11641751;
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelRight, this.mainPanelBottom, n);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelRight, this.mainPanelTop + 1, n2);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelBottom - 1, this.mainPanelRight, this.mainPanelBottom, n2);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelLeft + 1, this.mainPanelBottom, n2);
        class_3322.method_25294(this.mainPanelRight - 1, this.mainPanelTop, this.mainPanelRight, this.mainPanelBottom, n2);
        int n3 = 857944122;
        int n4 = 1430145621;
        this.drawSectionPanel(class_3322, this.leftPanelLeft, this.leftPanelTop, this.leftPanelRight, this.leftPanelBottom, n3, n4);
        this.drawSectionPanel(class_3322, this.centerPanelLeft, this.centerPanelTop, this.centerPanelRight, this.centerPanelBottom, n3, n4);
        this.drawSectionPanel(class_3322, this.rightPanelLeft, this.rightPanelTop, this.rightPanelRight, this.rightPanelBottom, n3, n4);
    }

    private void drawSectionPanel(class_332 class_3322, int n, int n2, int n3, int n4, int n5, int n6) {
        class_3322.method_25294(n, n2, n3, n4, n5);
        class_3322.method_25294(n, n2, n3, n2 + 1, n6);
        class_3322.method_25294(n, n4 - 1, n3, n4, n6);
        class_3322.method_25294(n, n2, n + 1, n4, n6);
        class_3322.method_25294(n3 - 1, n2, n3, n4, n6);
    }

    private void drawProfileListPanel(class_332 class_3322) {
        if (this.profileListW > 0 && this.profileListH > 0) {
            int n = this.profileListX;
            int n2 = this.profileListY;
            int n3 = this.profileListX + this.profileListW;
            int n4 = this.profileListY + this.profileListH;
            class_3322.method_25294(n, n2, n3, n4, 1141641495);
        }
    }

    private void renderStyledButtons(class_332 class_3322, int n, int n2) {
        for (class_364 class_3642 : this.method_25396()) {
            if (!(class_3642 instanceof class_4185)) continue;
            class_4185 class_41852 = (class_4185)class_3642;
            if (!class_41852.field_22764) continue;
            boolean bl = class_41852 == this.profileDeleteButton;
            this.drawStyledButton(class_3322, class_41852, n, n2, bl);
        }
    }

    private void drawStyledButton(class_332 class_3322, class_4185 class_41852, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6 = class_41852.method_46426();
        int n7 = class_41852.method_46427();
        int n8 = n6 + class_41852.method_25368();
        int n9 = n7 + class_41852.method_25364();
        boolean bl3 = this.isMouseInside(n, n2, n6, n7, n8, n9);
        boolean bl4 = class_41852.field_22763;
        if (!bl4) {
            n5 = -14801620;
            n4 = -12694955;
            n3 = -8483429;
        } else if (bl) {
            n5 = bl3 ? -7459282 : -9690590;
            n4 = -3048067;
            n3 = -1;
        } else if (bl3) {
            n5 = -12958126;
            n4 = -7485697;
            n3 = -1;
        } else {
            n5 = -13682107;
            n4 = -7429710;
            n3 = -1;
        }
        class_3322.method_25294(n6, n7, n8, n9, n5);
        class_3322.method_25294(n6, n7, n8, n7 + 1, n4);
        class_3322.method_25294(n6, n9 - 1, n8, n9, n4);
        class_3322.method_25294(n6, n7, n6 + 1, n9, n4);
        class_3322.method_25294(n8 - 1, n7, n8, n9, n4);
        String string = class_41852.method_25369() == null ? "" : class_41852.method_25369().getString();
        int n10 = Math.max(8, class_41852.method_25368() - 12);
        String string2 = this.resolveScrollableButtonLabel(string, n10, bl3);
        int n11 = n6 + (class_41852.method_25368() - this.field_22793.method_1727(string2)) / 2;
        int n12 = n7 + (class_41852.method_25364() - 8) / 2;
        class_3322.method_25303(this.field_22793, string2, n11, n12, n3);
    }

    private String resolveScrollableButtonLabel(String string, int n, boolean bl) {
        if (string != null && !string.isEmpty()) {
            if (this.field_22793.method_1727(string) <= n) {
                return string;
            }
            if (bl) {
                String string2 = string + "   ";
                int n2 = string2.length();
                if (n2 <= 1) {
                    return this.field_22793.method_27523(string, n);
                }
                long l = System.currentTimeMillis() / 150L;
                int n3 = (int)(l % (long)n2);
                String string3 = string2.substring(n3) + string2.substring(0, n3);
                String string4 = this.field_22793.method_27523(string3, n);
                if (string4.isBlank()) {
                    string4 = this.field_22793.method_27523(string, n);
                }
                return string4;
            }
            return n > this.field_22793.method_1727("...") ? this.field_22793.method_27523(string, n - this.field_22793.method_1727("...")) + "..." : this.field_22793.method_27523(string, n);
        }
        return "";
    }

    public boolean method_25402(double d, double d2, int n) {
        if (!this.blacklistModalOpen) {
            if (n == 0) {
                if (this.isMouseOverBlacklistScrollbar(d, d2)) {
                    this.draggingBlacklistScrollbar = true;
                    this.updateBlacklistScrollFromMouse(d2);
                    return true;
                }
                if (this.isMouseOverAnimationScrollbar(d, d2)) {
                    this.draggingAnimationScrollbar = true;
                    this.updateAnimationScrollFromMouse(d2);
                    return true;
                }
            }
            return super.method_25402(d, d2, n);
        }
        if (n != 0) {
            return true;
        }
        if (this.isMouseOverBlacklistModalScrollbar(d, d2)) {
            this.draggingBlacklistModalScrollbar = true;
            this.updateBlacklistModalScrollFromMouse(d2);
            return true;
        }
        if (super.method_25402(d, d2, n)) {
            return true;
        }
        if (this.isMouseOverBlacklistNamespaceScrollbar(d, d2)) {
            this.draggingBlacklistNamespaceScrollbar = true;
            this.updateBlacklistNamespaceScrollFromMouse(d2);
            return true;
        }
        if (this.isMouseInside(d, d2, this.blacklistResetButtonLeft, this.blacklistResetButtonTop, this.blacklistResetButtonRight, this.blacklistResetButtonBottom)) {
            this.resetBlacklistOnly();
            return true;
        }
        if (this.isMouseInside(d, d2, this.blacklistDoneButtonLeft, this.blacklistDoneButtonTop, this.blacklistDoneButtonRight, this.blacklistDoneButtonBottom)) {
            this.closeBlacklistModal();
            return true;
        }
        for (BlacklistHitbox blacklistHitbox : this.blacklistNamespaceHitboxes) {
            if (!this.isMouseInside(d, d2, blacklistHitbox.left, blacklistHitbox.top, blacklistHitbox.right, blacklistHitbox.bottom)) continue;
            this.toggleBlacklistNamespace(blacklistHitbox.key);
            return true;
        }
        for (BlacklistHitbox blacklistHitbox : this.blacklistEnableHitboxes) {
            if (!this.isMouseInside(d, d2, blacklistHitbox.left, blacklistHitbox.top, blacklistHitbox.right, blacklistHitbox.bottom)) continue;
            if ("__bulk_enable__".equals(blacklistHitbox.key)) {
                this.setFilteredEnableAll(!this.allFilteredEnabled());
            } else {
                this.toggleBlacklistEnabled(blacklistHitbox.key);
            }
            return true;
        }
        for (BlacklistHitbox blacklistHitbox : this.blacklistDualHitboxes) {
            if (!this.isMouseInside(d, d2, blacklistHitbox.left, blacklistHitbox.top, blacklistHitbox.right, blacklistHitbox.bottom)) continue;
            if ("__bulk_dual__".equals(blacklistHitbox.key)) {
                this.setFilteredDualAll(!this.allFilteredDual());
            } else {
                this.toggleBlacklistDual(blacklistHitbox.key);
            }
            return true;
        }
        return this.isMouseInside(d, d2, this.blacklistModalLeft, this.blacklistModalTop, this.blacklistModalRight, this.blacklistModalBottom) ? true : super.method_25402(d, d2, n);
    }

    public boolean method_25403(double d, double d2, int n, double d3, double d4) {
        if (n == 0) {
            if (this.draggingBlacklistModalScrollbar) {
                this.updateBlacklistModalScrollFromMouse(d2);
                return true;
            }
            if (this.draggingBlacklistNamespaceScrollbar) {
                this.updateBlacklistNamespaceScrollFromMouse(d2);
                return true;
            }
            if (!this.blacklistModalOpen && this.draggingBlacklistScrollbar) {
                this.updateBlacklistScrollFromMouse(d2);
                return true;
            }
            if (!this.blacklistModalOpen && this.draggingAnimationScrollbar) {
                this.updateAnimationScrollFromMouse(d2);
                return true;
            }
        }
        return super.method_25403(d, d2, n, d3, d4);
    }

    public boolean method_25406(double d, double d2, int n) {
        if (n == 0) {
            this.draggingBlacklistModalScrollbar = false;
            this.draggingBlacklistNamespaceScrollbar = false;
            this.draggingBlacklistScrollbar = false;
            this.draggingAnimationScrollbar = false;
        }
        return super.method_25406(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        int n;
        if (!this.blacklistModalOpen && this.isMouseOverProfileList(d, d2) && (n = Math.max(0, this.profiles.size() - this.profileRowButtons.size())) > 0) {
            this.profileListScroll = class_3532.method_15340((int)(this.profileListScroll - (int)Math.signum(d4)), (int)0, (int)n);
            this.updateProfileRowButtons();
            return true;
        }
        if (this.handleBlacklistNamespaceScroll(d, d2, d4)) {
            return true;
        }
        if (this.handleBlacklistModalScroll(d, d2, d4)) {
            return true;
        }
        if (this.handleBlacklistScroll(d, d2, d4)) {
            return true;
        }
        return this.handleAnimationScroll(d, d2, d4) ? true : super.method_25401(d, d2, d3, d4);
    }

    private boolean handleBlacklistModalScroll(double d, double d2, double d3) {
        if (!this.blacklistModalOpen) {
            return false;
        }
        if (this.blacklistModalMaxScroll <= 0) {
            return false;
        }
        if (!this.isMouseInside(d, d2, this.blacklistListLeft, this.blacklistListTop, this.blacklistListRight, this.blacklistListBottom)) {
            return false;
        }
        int n = (int)Math.round(d3 * 22.0);
        this.blacklistModalScrollOffset = PunchyConfigScreen.clamp(this.blacklistModalScrollOffset - n, 0, this.blacklistModalMaxScroll);
        return true;
    }

    private boolean handleBlacklistNamespaceScroll(double d, double d2, double d3) {
        if (!this.blacklistModalOpen) {
            return false;
        }
        if (this.blacklistNamespaceMaxScroll <= 0) {
            return false;
        }
        if (!this.isMouseInside(d, d2, this.blacklistNamespaceAreaLeft, this.blacklistNamespaceAreaTop, this.blacklistNamespaceAreaRight, this.blacklistNamespaceAreaBottom)) {
            return false;
        }
        int n = (int)Math.round(d3 * 20.0);
        this.blacklistNamespaceScrollOffset = PunchyConfigScreen.clamp(this.blacklistNamespaceScrollOffset - n, 0, this.blacklistNamespaceMaxScroll);
        return true;
    }

    private boolean isMouseOverBlacklistModalScrollbar(double d, double d2) {
        if (this.blacklistModalOpen && this.blacklistModalMaxScroll > 0) {
            int n = 4;
            int n2 = this.blacklistListRight - n;
            return this.isMouseInside(d, d2, n2, this.blacklistListTop, n2 + n, this.blacklistListBottom);
        }
        return false;
    }

    private boolean isMouseOverBlacklistNamespaceScrollbar(double d, double d2) {
        if (this.blacklistModalOpen && this.blacklistNamespaceMaxScroll > 0) {
            int n = 4;
            int n2 = this.blacklistNamespaceAreaRight - n;
            return this.isMouseInside(d, d2, n2, this.blacklistNamespaceAreaTop, n2 + n, this.blacklistNamespaceAreaBottom);
        }
        return false;
    }

    private void updateBlacklistModalScrollFromMouse(double d) {
        if (this.blacklistModalMaxScroll > 0) {
            int n = this.blacklistListTop;
            int n2 = this.blacklistListBottom;
            int n3 = Math.max(1, n2 - n);
            double d2 = (d - (double)n) / (double)n3;
            this.blacklistModalScrollOffset = PunchyConfigScreen.clamp((int)Math.round(d2 * (double)this.blacklistModalMaxScroll), 0, this.blacklistModalMaxScroll);
        }
    }

    private void updateBlacklistNamespaceScrollFromMouse(double d) {
        if (this.blacklistNamespaceMaxScroll > 0) {
            int n = this.blacklistNamespaceAreaTop;
            int n2 = this.blacklistNamespaceAreaBottom;
            int n3 = Math.max(1, n2 - n);
            double d2 = (d - (double)n) / (double)n3;
            this.blacklistNamespaceScrollOffset = PunchyConfigScreen.clamp((int)Math.round(d2 * (double)this.blacklistNamespaceMaxScroll), 0, this.blacklistNamespaceMaxScroll);
        }
    }

    private int computeBlacklistChipTotalHeight(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = 0;
        int n7 = n3;
        for (BlacklistModFilter blacklistModFilter : this.blacklistModFilters) {
            int n8 = Math.max(50, this.field_22793.method_1727(blacklistModFilter.displayName) + 18);
            if (n5 + n8 > n2) {
                n5 = n;
                n6 += n3 + n4;
            }
            n7 = Math.max(n7, n6 + n3);
            n5 += n8 + n4;
        }
        return n7;
    }

    private void drawBlacklistModalOverlay(class_332 class_3322, int n, int n2) {
        String string;
        String string2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        int n16;
        int n17;
        this.ensureBlacklistCatalog();
        this.computeBlacklistModalLayout();
        this.blacklistNamespaceHitboxes.clear();
        this.blacklistEnableHitboxes.clear();
        this.blacklistDualHitboxes.clear();
        class_3322.method_25294(this.blacklistModalLeft, this.blacklistModalTop, this.blacklistModalRight, this.blacklistModalBottom, -267381478);
        int n18 = -11641751;
        class_3322.method_25294(this.blacklistModalLeft, this.blacklistModalTop, this.blacklistModalRight, this.blacklistModalTop + 1, n18);
        class_3322.method_25294(this.blacklistModalLeft, this.blacklistModalBottom - 1, this.blacklistModalRight, this.blacklistModalBottom, n18);
        class_3322.method_25294(this.blacklistModalLeft, this.blacklistModalTop, this.blacklistModalLeft + 1, this.blacklistModalBottom, n18);
        class_3322.method_25294(this.blacklistModalRight - 1, this.blacklistModalTop, this.blacklistModalRight, this.blacklistModalBottom, n18);
        class_5250 class_52502 = class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.modal_title");
        String string3 = class_52502.getString();
        if (string3 == null || string3.isBlank() || string3.equals("ru.method.celebression.punchy.config.blacklist.modal_title")) {
            string3 = "Item Blacklist";
        }
        class_3322.method_25303(this.field_22793, string3, this.blacklistModalLeft + 14, this.blacklistModalTop + 12, -1);
        int n19 = this.blacklistModalTop + 58;
        String string4 = class_1074.method_4662((String)"ru.method.celebression.punchy.config.blacklist.global", (Object[])new Object[0]);
        if (string4 == null || string4.isBlank() || string4.equals("ru.method.celebression.punchy.config.blacklist.global")) {
            string4 = "Filtrar por mod";
        }
        class_3322.method_25303(this.field_22793, string4, this.blacklistModalLeft + 14, n19, -4799537);
        int n20 = this.blacklistModalLeft + 14;
        int n21 = this.blacklistModalRight - 18;
        int n22 = 16;
        int n23 = 4;
        int n24 = n19 + 12;
        int n25 = n22 * 2 + n23 * 1;
        int n26 = n24 + n25;
        int n27 = 4;
        int n28 = n21;
        int n29 = this.computeBlacklistChipTotalHeight(n20, n21, n22, n23);
        this.blacklistNamespaceMaxScroll = Math.max(0, n29 - n25);
        if (this.blacklistNamespaceMaxScroll > 0) {
            n28 = n21 - n27 - 2;
            n29 = this.computeBlacklistChipTotalHeight(n20, n28, n22, n23);
            this.blacklistNamespaceMaxScroll = Math.max(0, n29 - n25);
        }
        this.blacklistNamespaceScrollOffset = PunchyConfigScreen.clamp(this.blacklistNamespaceScrollOffset, 0, this.blacklistNamespaceMaxScroll);
        this.blacklistNamespaceAreaLeft = n20;
        this.blacklistNamespaceAreaRight = n21;
        this.blacklistNamespaceAreaTop = n24;
        this.blacklistNamespaceAreaBottom = n26;
        class_3322.method_25294(n20, n24, n21, n26, 705631516);
        class_3322.method_25294(n20, n24, n21, n24 + 1, 1430145621);
        class_3322.method_25294(n20, n26 - 1, n21, n26, 1430145621);
        int n30 = n20;
        int n31 = 0;
        int n32 = 0;
        int n33 = n22 + n23;
        int n34 = Math.max(0, this.blacklistNamespaceScrollOffset / Math.max(1, n33));
        int n35 = n34 + 2;
        for (BlacklistModFilter blacklistModFilter : this.blacklistModFilters) {
            String string5 = blacklistModFilter.displayName;
            n17 = Math.max(50, this.field_22793.method_1727(string5) + 18);
            if (n30 + n17 > n28) {
                n30 = n20;
                n31 += n22 + n23;
                ++n32;
            }
            if (n32 >= n34 && n32 < n35) {
                n16 = n24 + n31 - this.blacklistNamespaceScrollOffset;
                n15 = n16 + n22;
                if (n15 > n24 && n16 < n26) {
                    boolean selected = blacklistModFilter.namespace.equals(this.blacklistSelectedNamespace);
                    n13 = selected ? -13869680 : -14471110;
                    n12 = selected ? -7485697 : -9798515;
                    class_3322.method_25294(n30, n16, n30 + n17, n15, n13);
                    class_3322.method_25294(n30, n16, n30 + n17, n16 + 1, n12);
                    class_3322.method_25294(n30, n15 - 1, n30 + n17, n15, n12);
                    class_3322.method_25294(n30, n16, n30 + 1, n15, n12);
                    class_3322.method_25294(n30 + n17 - 1, n16, n30 + n17, n15, n12);
                    class_3322.method_25303(this.field_22793, string5, n30 + 8, n16 + 4, -1);
                    n11 = Math.max(n16, n24);
                    n10 = Math.min(n15, n26);
                    if (n10 > n11) {
                        this.blacklistNamespaceHitboxes.add(new BlacklistHitbox(n30, n11, n30 + n17, n10, blacklistModFilter.namespace));
                    }
                    n30 += n17 + n23;
                    continue;
                }
                n30 += n17 + n23;
                continue;
            }
            n30 += n17 + n23;
        }
        if (this.blacklistNamespaceMaxScroll > 0) {
            int n36 = n21 - n27;
            int n37 = Math.max(1, n26 - n24);
            int n38 = Math.max(10, Math.round((float)n37 * ((float)n25 / (float)n29)));
            n17 = n37 - n38;
            n16 = this.blacklistNamespaceMaxScroll == 0 ? 0 : Math.round((float)n17 * ((float)this.blacklistNamespaceScrollOffset / (float)this.blacklistNamespaceMaxScroll));
            class_3322.method_25294(n36, n24, n36 + n27, n26, -1442840576);
            class_3322.method_25294(n36, n24 + n16, n36 + n27, Math.min(n26, n24 + n16 + n38), -1);
        }
        this.blacklistListLeft = this.blacklistModalLeft + 14;
        this.blacklistListRight = this.blacklistModalRight - 14;
        this.blacklistListTop = n26 + 10;
        this.blacklistListBottom = this.blacklistResetButtonTop - 8;
        if (this.blacklistListBottom <= this.blacklistListTop) {
            this.blacklistListBottom = this.blacklistListTop + 22;
        }
        int n39 = this.blacklistListBottom - this.blacklistListTop;
        int n40 = Math.max(1, n39 / 22);
        int n41 = n40 * 22;
        this.blacklistListBottom = this.blacklistListTop + n41;
        n17 = (this.blacklistFilteredEntries.size() + 1) * 22;
        this.blacklistModalMaxScroll = Math.max(0, n17 - n41);
        this.blacklistModalScrollOffset = PunchyConfigScreen.clamp(this.blacklistModalScrollOffset, 0, this.blacklistModalMaxScroll);
        class_3322.method_25294(this.blacklistListLeft, this.blacklistListTop, this.blacklistListRight, this.blacklistListBottom, 1141641495);
        class_3322.method_25294(this.blacklistListLeft, this.blacklistListTop, this.blacklistListRight, this.blacklistListTop + 1, 1430145621);
        class_3322.method_25294(this.blacklistListLeft, this.blacklistListBottom - 1, this.blacklistListRight, this.blacklistListBottom, 1430145621);
        n16 = 66;
        n15 = 66;
        n14 = 6;
        n13 = this.blacklistListRight - 6;
        n12 = n13 - n15;
        n11 = n12 - n14 - n16;
        n10 = this.allFilteredEnabled() ? 1 : 0;
        boolean bl = this.allFilteredDual();
        int n42 = this.blacklistListTop - this.blacklistModalScrollOffset;
        int n43 = n42 + 22;
        if (n43 > this.blacklistListTop && n42 < this.blacklistListBottom) {
            class_3322.method_25294(this.blacklistListLeft + 1, n42, this.blacklistListRight - 1, n43, 859064927);
            String string6 = class_1074.method_4662((String)"ru.method.celebression.punchy.config.blacklist.bulk", (Object[])new Object[0]);
            if (string6 == null || string6.isBlank() || string6.equals("ru.method.celebression.punchy.config.blacklist.bulk")) {
                string6 = "Acoes em massa (filtro atual)";
            }
            class_3322.method_25303(this.field_22793, string6, this.blacklistListLeft + 8, n42 + 7, -1);
            this.drawBlacklistActionButton(class_3322, n11, n42 + 2, n11 + n16, n43 - 2, this.isMouseInside(n, n2, n11, n42 + 2, n11 + n16, n43 - 2), n10 != 0, this.blacklistFilteredEntries.isEmpty(), this.blacklistEnableLabel(n10 != 0));
            this.blacklistEnableHitboxes.add(new BlacklistHitbox(n11, n42 + 2, n11 + n16, n43 - 2, "__bulk_enable__"));
            this.drawBlacklistActionButton(class_3322, n12, n42 + 2, n12 + n15, n43 - 2, this.isMouseInside(n, n2, n12, n42 + 2, n12 + n15, n43 - 2), bl, this.blacklistFilteredEntries.isEmpty(), this.blacklistDualShortLabel(bl));
            this.blacklistDualHitboxes.add(new BlacklistHitbox(n12, n42 + 2, n12 + n15, n43 - 2, "__bulk_dual__"));
        }
        for (n9 = 0; n9 < this.blacklistFilteredEntries.size(); ++n9) {
            n8 = this.blacklistListTop + (n9 + 1) * 22 - this.blacklistModalScrollOffset;
            n7 = n8 + 22;
            if (n7 <= this.blacklistListTop || n8 >= this.blacklistListBottom) continue;
            BlacklistCatalogEntry blacklistCatalogEntry = this.blacklistFilteredEntries.get(n9);
            n6 = this.isBlacklistEnabled(blacklistCatalogEntry.id) ? 1 : 0;
            n5 = this.isBlacklistDualHanded(blacklistCatalogEntry.id) ? 1 : 0;
            n4 = (n9 & 1) == 0 ? 706553648 : 438118192;
            class_3322.method_25294(this.blacklistListLeft + 1, n8, this.blacklistListRight - 1, n7, n4);
            if (n6 != 0) {
                class_3322.method_25294(this.blacklistListLeft + 1, n8, this.blacklistListLeft + 3, n7, -9713270);
            }
            n3 = n8 + Math.max(0, 3);
            class_3322.method_51427(blacklistCatalogEntry.icon, this.blacklistListLeft + 5, n3);
            int n44 = this.blacklistListLeft + 25;
            class_3322.method_25303(this.field_22793, blacklistCatalogEntry.displayName, n44, n8 + 3, -1);
            class_3322.method_25303(this.field_22793, blacklistCatalogEntry.id, n44, n8 + 12, -7429710);
            this.drawBlacklistActionButton(class_3322, n11, n8 + 2, n11 + n16, n7 - 2, this.isMouseInside(n, n2, n11, n8 + 2, n11 + n16, n7 - 2), n6 != 0, false, this.blacklistEnableLabel(n6 != 0));
            this.blacklistEnableHitboxes.add(new BlacklistHitbox(n11, n8 + 2, n11 + n16, n7 - 2, blacklistCatalogEntry.id));
            this.drawBlacklistActionButton(class_3322, n12, n8 + 2, n12 + n15, n7 - 2, this.isMouseInside(n, n2, n12, n8 + 2, n12 + n15, n7 - 2), n5 != 0, false, this.blacklistDualShortLabel(n5 != 0));
            this.blacklistDualHitboxes.add(new BlacklistHitbox(n12, n8 + 2, n12 + n15, n7 - 2, blacklistCatalogEntry.id));
        }
        if (this.blacklistModalMaxScroll > 0) {
            n9 = 4;
            n8 = this.blacklistListRight - n9;
            n7 = this.blacklistListTop;
            int n45 = this.blacklistListBottom;
            n6 = Math.max(1, n45 - n7);
            n5 = Math.max(10, Math.round((float)n6 * ((float)n41 / (float)n17)));
            n4 = n6 - n5;
            n3 = this.blacklistModalMaxScroll == 0 ? 0 : Math.round((float)n4 * ((float)this.blacklistModalScrollOffset / (float)this.blacklistModalMaxScroll));
            class_3322.method_25294(n8, n7, n8 + n9, n45, -1442840576);
            class_3322.method_25294(n8, n7 + n3, n8 + n9, Math.min(n45, n7 + n3 + n5), -1);
        }
        if ((string2 = class_1074.method_4662((String)"ru.method.celebression.punchy.config.reset", (Object[])new Object[0])) == null || string2.isBlank() || string2.equals("ru.method.celebression.punchy.config.reset")) {
            string2 = "Reset";
        }
        if ((string = class_1074.method_4662((String)"gui.done", (Object[])new Object[0])) == null || string.isBlank() || string.equals("gui.done")) {
            string = "Done";
        }
        this.drawBlacklistFooterButton(class_3322, this.blacklistResetButtonLeft, this.blacklistResetButtonTop, this.blacklistResetButtonRight, this.blacklistResetButtonBottom, this.isMouseInside(n, n2, this.blacklistResetButtonLeft, this.blacklistResetButtonTop, this.blacklistResetButtonRight, this.blacklistResetButtonBottom), true, string2);
        this.drawBlacklistFooterButton(class_3322, this.blacklistDoneButtonLeft, this.blacklistDoneButtonTop, this.blacklistDoneButtonRight, this.blacklistDoneButtonBottom, this.isMouseInside(n, n2, this.blacklistDoneButtonLeft, this.blacklistDoneButtonTop, this.blacklistDoneButtonRight, this.blacklistDoneButtonBottom), false, string);
    }

    private void drawBlacklistActionButton(class_332 class_3322, int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3, String string) {
        int n5;
        int n6;
        int n7;
        if (bl3) {
            n7 = -14801620;
            n6 = -12694955;
            n5 = -8483429;
        } else if (bl2) {
            n7 = bl ? -13994943 : -14393546;
            n6 = -8662895;
            n5 = -1;
        } else {
            n7 = bl ? -12958126 : -13682107;
            n6 = -7429710;
            n5 = -1;
        }
        class_3322.method_25294(n, n2, n3, n4, n7);
        class_3322.method_25294(n, n2, n3, n2 + 1, n6);
        class_3322.method_25294(n, n4 - 1, n3, n4, n6);
        class_3322.method_25294(n, n2, n + 1, n4, n6);
        class_3322.method_25294(n3 - 1, n2, n3, n4, n6);
        int n8 = n + (n3 - n - this.field_22793.method_1727(string)) / 2;
        int n9 = n2 + (n4 - n2 - 8) / 2;
        class_3322.method_25303(this.field_22793, string, n8, n9, n5);
    }

    private void drawBlacklistFooterButton(class_332 class_3322, int n, int n2, int n3, int n4, boolean bl, boolean bl2, String string) {
        int n5 = bl2 ? (bl ? -7459282 : -9690590) : (bl ? -12958126 : -13682107);
        int n6 = bl2 ? -3048067 : -7429710;
        class_3322.method_25294(n, n2, n3, n4, n5);
        class_3322.method_25294(n, n2, n3, n2 + 1, n6);
        class_3322.method_25294(n, n4 - 1, n3, n4, n6);
        class_3322.method_25294(n, n2, n + 1, n4, n6);
        class_3322.method_25294(n3 - 1, n2, n3, n4, n6);
        int n7 = n + (n3 - n - this.field_22793.method_1727(string)) / 2;
        int n8 = n2 + (n4 - n2 - 8) / 2;
        class_3322.method_25303(this.field_22793, string, n7, n8, -1);
    }

    private boolean handleBlacklistScroll(double d, double d2, double d3) {
        if (this.tab != Tab.BLACKLIST) {
            return false;
        }
        if (this.blacklistEntryButtons.isEmpty()) {
            return false;
        }
        int n = this.blacklistListStartY;
        int n2 = this.blacklistListStartY + this.blacklistViewportHeight;
        if (d < (double)this.blacklistHelpX || d > (double)(this.blacklistHelpX + this.blacklistHelpWidth)) {
            return false;
        }
        if (!(d2 < (double)n) && !(d2 > (double)n2)) {
            int n3 = this.blacklistEntryButtons.size() * this.blacklistEntryHeight - 6;
            int n4 = Math.max(0, n3 - this.blacklistViewportHeight);
            if (n4 <= 0) {
                return false;
            }
            int n5 = (int)Math.round(d3 * (double)this.blacklistEntryHeight);
            this.blacklistScrollOffset = PunchyConfigScreen.clamp(this.blacklistScrollOffset - n5, 0, n4);
            this.updateBlacklistScroll();
            return true;
        }
        return false;
    }

    private void updateBlacklistScroll() {
        int n = this.blacklistListStartY;
        int n2 = this.blacklistListStartY + this.blacklistViewportHeight;
        for (int i = 0; i < this.blacklistEntryButtons.size(); ++i) {
            boolean bl;
            int n3 = this.blacklistListStartY + i * this.blacklistEntryHeight - this.blacklistScrollOffset;
            class_4185 class_41852 = this.blacklistEntryButtons.get(i);
            class_4185 class_41853 = this.blacklistDualButtons.get(i);
            class_4185 class_41854 = this.blacklistRemoveButtons.get(i);
            class_41852.method_46419(n3);
            class_41853.method_46419(n3);
            class_41854.method_46419(n3);
            class_41852.field_22764 = bl = n3 + 20 > n && n3 < n2;
            class_41853.field_22764 = bl;
            class_41854.field_22764 = bl;
            class_41852.field_22763 = bl;
            class_41853.field_22763 = bl;
            class_41854.field_22763 = bl;
        }
    }

    private boolean isMouseOverBlacklistScrollbar(double d, double d2) {
        int n = this.blacklistEntryButtons.size() * this.blacklistEntryHeight - 6;
        int n2 = Math.max(0, n - this.blacklistViewportHeight);
        if (this.tab == Tab.BLACKLIST && n2 > 0 && this.blacklistViewportHeight > 0) {
            int n3 = 4;
            int n4 = this.blacklistHelpX + this.blacklistHelpWidth - n3;
            return this.isMouseInside(d, d2, n4, this.blacklistListStartY, n4 + n3, this.blacklistListStartY + this.blacklistViewportHeight);
        }
        return false;
    }

    private void updateBlacklistScrollFromMouse(double d) {
        int n = this.blacklistEntryButtons.size() * this.blacklistEntryHeight - 6;
        int n2 = Math.max(0, n - this.blacklistViewportHeight);
        if (n2 > 0 && this.blacklistViewportHeight > 0) {
            int n3 = this.blacklistListStartY;
            int n4 = this.blacklistListStartY + this.blacklistViewportHeight;
            int n5 = Math.max(1, n4 - n3);
            double d2 = (d - (double)n3) / (double)n5;
            this.blacklistScrollOffset = PunchyConfigScreen.clamp((int)Math.round(d2 * (double)n2), 0, n2);
            this.updateBlacklistScroll();
        }
    }

    private boolean isBlacklistDualHanded(String string) {
        return string != null && !string.isBlank() && this.data.blacklistApplyDualHanded != null ? Boolean.TRUE.equals(this.data.blacklistApplyDualHanded.get(string)) : false;
    }

    private void setBlacklistDualHanded(String string, boolean bl) {
        if (string != null && !string.isBlank()) {
            if (this.data.blacklistApplyDualHanded == null) {
                this.data.blacklistApplyDualHanded = new LinkedHashMap<String, Boolean>();
            }
            this.data.blacklistApplyDualHanded.put(string, bl);
        }
    }

    private class_2561 blacklistDualLabel(boolean bl) {
        return class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.dual_handed.short").method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43471((String)(bl ? "punchy.toggle.on" : "punchy.toggle.off")));
    }

    private String blacklistEnableLabel(boolean bl) {
        String string;
        String string2 = class_1074.method_4662((String)"ru.method.celebression.punchy.config.blacklist.enable", (Object[])new Object[0]);
        if (string2 == null || string2.isBlank() || string2.equals("ru.method.celebression.punchy.config.blacklist.enable")) {
            string2 = "Enable";
        }
        if ((string = class_1074.method_4662((String)(bl ? "punchy.toggle.on" : "punchy.toggle.off"), (Object[])new Object[0])) == null || string.isBlank() || string.equals(bl ? "punchy.toggle.on" : "punchy.toggle.off")) {
            string = bl ? "On" : "Off";
        }
        return string2 + ": " + string;
    }

    private String blacklistDualShortLabel(boolean bl) {
        String string;
        String string2 = class_2561.method_43471((String)"ru.method.celebression.punchy.config.blacklist.dual_handed.short").getString();
        if (string2 == null || string2.isBlank() || string2.equals("ru.method.celebression.punchy.config.blacklist.dual_handed.short")) {
            string2 = "Dual";
        }
        if ((string = class_1074.method_4662((String)(bl ? "punchy.toggle.on" : "punchy.toggle.off"), (Object[])new Object[0])) == null || string.isBlank() || string.equals(bl ? "punchy.toggle.on" : "punchy.toggle.off")) {
            string = bl ? "On" : "Off";
        }
        return string2 + ": " + string;
    }

    private boolean handleAnimationScroll(double d, double d2, double d3) {
        if (this.animationRowCount > 0 && this.animationViewportHeight > 0) {
            int n = this.animationListStartY;
            int n2 = this.animationListStartY + this.animationViewportHeight;
            int n3 = this.dividerX + 16;
            int n4 = n3 + this.contentPanelWidth;
            if (d < (double)n3 || d > (double)n4) {
                return false;
            }
            if (!(d2 < (double)n) && !(d2 > (double)n2)) {
                int n5 = this.animationRowCount * this.animationEntryHeight - 6;
                int n6 = Math.max(0, n5 - this.animationViewportHeight);
                if (n6 <= 0) {
                    return false;
                }
                int n7 = (int)Math.round(d3 * (double)this.animationEntryHeight);
                this.animationScrollOffset = PunchyConfigScreen.clamp(this.animationScrollOffset - n7, 0, n6);
                this.updateAnimationScroll();
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isMouseOverAnimationScrollbar(double d, double d2) {
        int n = this.animationRowCount * this.animationEntryHeight - 6;
        int n2 = Math.max(0, n - this.animationViewportHeight);
        if (n2 > 0 && this.animationViewportHeight > 0) {
            int n3 = 4;
            int n4 = Math.max(this.centerPanelLeft + 2, this.centerPanelRight - 7);
            return this.isMouseInside(d, d2, n4, this.animationListStartY, n4 + n3, this.animationListStartY + this.animationViewportHeight);
        }
        return false;
    }

    private void updateAnimationScrollFromMouse(double d) {
        int n = this.animationRowCount * this.animationEntryHeight - 6;
        int n2 = Math.max(0, n - this.animationViewportHeight);
        if (n2 > 0 && this.animationViewportHeight > 0) {
            int n3 = this.animationListStartY;
            int n4 = this.animationListStartY + this.animationViewportHeight;
            int n5 = Math.max(1, n4 - n3);
            double d2 = (d - (double)n3) / (double)n5;
            this.animationScrollOffset = PunchyConfigScreen.clamp((int)Math.round(d2 * (double)n2), 0, n2);
            this.updateAnimationScroll();
        }
    }

    private void updateAnimationScroll() {
        int n = this.animationListStartY;
        int n2 = this.animationListStartY + this.animationViewportHeight;
        for (int i = 0; i < this.animationWidgets.size(); ++i) {
            boolean bl;
            class_339 class_3392 = this.animationWidgets.get(i);
            int n3 = this.animationWidgetBaseY.get(i);
            int n4 = n3 - this.animationScrollOffset;
            class_3392.method_46419(n4);
            int n5 = Math.max(20, class_3392.method_25364());
            class_3392.field_22764 = bl = n4 >= n && n4 < n2 && n4 + n5 <= n2;
            class_3392.field_22763 = bl;
        }
    }

    private static int clamp(int n, int n2, int n3) {
        return Math.max(n2, Math.min(n3, n));
    }

    private static List<class_5481> splitHelpText(String string, int n) {
        ArrayList<class_5481> arrayList = new ArrayList<class_5481>();
        if (string != null && !string.isBlank()) {
            String[] stringArray = string.split("\\s+");
            StringBuilder stringBuilder = new StringBuilder();
            for (String string2 : stringArray) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(string2);
                    continue;
                }
                if (stringBuilder.length() + 1 + string2.length() > n) {
                    arrayList.add(class_5481.method_30747((String)stringBuilder.toString(), (class_2583)class_2583.field_24360));
                    stringBuilder.setLength(0);
                    stringBuilder.append(string2);
                    continue;
                }
                stringBuilder.append(' ').append(string2);
            }
            if (stringBuilder.length() > 0) {
                arrayList.add(class_5481.method_30747((String)stringBuilder.toString(), (class_2583)class_2583.field_24360));
            }
            return arrayList;
        }
        return arrayList;
    }

    static enum Tab {
        SUPPORT("ru.method.celebression.punchy.config.tab.support"),
        BLACKLIST("ru.method.celebression.punchy.config.tab.blacklist"),
        RENDERING("ru.method.celebression.punchy.config.tab.rendering"),
        ANIMATION("ru.method.celebression.punchy.config.tab.animation"),
        PHYSICS("ru.method.celebression.punchy.config.tab.physics"),
        MODELS("ru.method.celebression.punchy.config.tab.models"),
        COMPAT("ru.method.celebression.punchy.config.tab.compat");

        final String labelKey;

        private Tab(String string2) {
            this.labelKey = string2;
        }
    }

    static final class AnimationSpeedSlider
    extends StyledConfigSlider {
        private final PunchyConfig.Data data;

        AnimationSpeedSlider(int n, int n2, int n3, int n4, PunchyConfig.Data data) {
            super(n, n2, n3, n4, (class_2561)class_2561.method_43473(), AnimationSpeedSlider.toSliderValue(data.animationSpeed));
            this.data = data;
            this.method_25346();
        }

        protected void method_25346() {
            float f = AnimationSpeedSlider.sliderToSpeed(this.field_22753);
            String string = String.format(Locale.ROOT, "%.1f", Float.valueOf(f));
            this.method_25355((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.animation_speed").method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43470((String)string)));
        }

        @Override
        protected String sliderLabel() {
            return class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.animation_speed").getString();
        }

        @Override
        protected String sliderValueText() {
            return String.format(Locale.ROOT, "%.1f", Float.valueOf(AnimationSpeedSlider.sliderToSpeed(this.field_22753)));
        }

        protected void method_25344() {
            float f = AnimationSpeedSlider.sliderToSpeed(this.field_22753);
            this.data.animationSpeed = f = (float)Math.round(f * 10.0f) / 10.0f;
            PunchyAnimationManager.setAnimationSpeed(f);
        }

        void setSpeed(float f) {
            this.field_22753 = AnimationSpeedSlider.toSliderValue(f);
            this.method_25346();
        }

        private static double toSliderValue(float f) {
            float f2 = 0.5f;
            float f3 = 7.0f;
            return (f - f2) / (f3 - f2);
        }

        private static float sliderToSpeed(double d) {
            float f = 0.5f;
            float f2 = 7.0f;
            float f3 = (float)Math.min(1.0, Math.max(0.0, d));
            return f + (f2 - f) * f3;
        }
    }

    static final class SprintArmSwingIntensitySlider
    extends StyledConfigSlider {
        private final PunchyConfig.Data data;

        SprintArmSwingIntensitySlider(int n, int n2, int n3, int n4, PunchyConfig.Data data) {
            super(n, n2, n3, n4, (class_2561)class_2561.method_43473(), SprintArmSwingIntensitySlider.toSliderValue(data.sprintArmSwingIntensity));
            this.data = data;
            this.method_25346();
        }

        protected void method_25346() {
            float f = SprintArmSwingIntensitySlider.sliderToIntensity(this.field_22753);
            int n = Math.round(f * 100.0f);
            this.method_25355((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.sprint_arm_swing_intensity").method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43470((String)(n + "%"))));
        }

        @Override
        protected String sliderLabel() {
            return class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.sprint_arm_swing_intensity").getString();
        }

        @Override
        protected String sliderValueText() {
            int n = Math.round(SprintArmSwingIntensitySlider.sliderToIntensity(this.field_22753) * 100.0f);
            return n + "%";
        }

        protected void method_25344() {
            float f = SprintArmSwingIntensitySlider.sliderToIntensity(this.field_22753);
            this.data.sprintArmSwingIntensity = f = (float)Math.round(f * 100.0f) / 100.0f;
            this.method_25346();
        }

        void setIntensity(float f) {
            this.field_22753 = SprintArmSwingIntensitySlider.toSliderValue(f);
            this.method_25346();
        }

        private static double toSliderValue(float f) {
            float f2 = -1.0f;
            float f3 = 1.0f;
            return (f - f2) / (f3 - f2);
        }

        private static float sliderToIntensity(double d) {
            float f = -1.0f;
            float f2 = 1.0f;
            float f3 = (float)Math.min(1.0, Math.max(0.0, d));
            return f + (f2 - f) * f3;
        }
    }

    static final class SprintArmSwingSpeedSlider
    extends StyledConfigSlider {
        private final PunchyConfig.Data data;

        SprintArmSwingSpeedSlider(int n, int n2, int n3, int n4, PunchyConfig.Data data) {
            super(n, n2, n3, n4, (class_2561)class_2561.method_43473(), SprintArmSwingSpeedSlider.toSliderValue(data.sprintArmSwingSpeed));
            this.data = data;
            this.method_25346();
        }

        protected void method_25346() {
            float f = SprintArmSwingSpeedSlider.sliderToSpeed(this.field_22753);
            int n = Math.round(f * 100.0f);
            class_5250 class_52502 = class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.sprint_arm_swing_speed");
            class_5250 class_52503 = class_2561.method_43470((String)(n + "%"));
            this.method_25355((class_2561)class_52502.method_27661().method_27693(": ").method_10852((class_2561)class_52503));
        }

        @Override
        protected String sliderLabel() {
            return class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.sprint_arm_swing_speed").getString();
        }

        @Override
        protected String sliderValueText() {
            int n = Math.round(SprintArmSwingSpeedSlider.sliderToSpeed(this.field_22753) * 100.0f);
            return n + "%";
        }

        protected void method_25344() {
            float f;
            this.data.sprintArmSwingSpeed = f = SprintArmSwingSpeedSlider.sliderToSpeed(this.field_22753);
        }

        void setSpeed(float f) {
            this.field_22753 = SprintArmSwingSpeedSlider.toSliderValue(f);
            this.method_25346();
        }

        private static double toSliderValue(float f) {
            return class_3532.method_15350((double)((f - -1.0f) / 2.0f), (double)0.0, (double)1.0);
        }

        private static float sliderToSpeed(double d) {
            float f = (float)d;
            return -1.0f + f * 2.0f;
        }
    }

    static final class FirstPersonHidePitchSlider
    extends StyledConfigSlider {
        private final PunchyConfig.Data data;

        FirstPersonHidePitchSlider(int n, int n2, int n3, int n4, PunchyConfig.Data data) {
            super(n, n2, n3, n4, (class_2561)class_2561.method_43473(), FirstPersonHidePitchSlider.toSliderValue(data.firstPersonModelHidePitch));
            this.data = data;
            this.method_25346();
        }

        protected void method_25346() {
            float f = FirstPersonHidePitchSlider.sliderToPitch(this.field_22753);
            String string = FirstPersonHidePitchSlider.valueText(f);
            this.method_25355((class_2561)class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.firstperson_hide_pitch").method_10852((class_2561)class_2561.method_43470((String)": ")).method_10852((class_2561)class_2561.method_43470((String)string)));
        }

        @Override
        protected String sliderLabel() {
            return class_2561.method_43471((String)"ru.method.celebression.punchy.config.option.firstperson_hide_pitch").getString();
        }

        @Override
        protected String sliderValueText() {
            return FirstPersonHidePitchSlider.valueText(FirstPersonHidePitchSlider.sliderToPitch(this.field_22753));
        }

        @Override
        protected String sliderReservedValueText() {
            return "never";
        }

        protected void method_25344() {
            float f = FirstPersonHidePitchSlider.sliderToPitch(this.field_22753);
            this.data.firstPersonModelHidePitch = f = (float)Math.round(f);
        }

        private static String valueText(float f) {
            return f > 85.0f ? "never" : String.format(Locale.ROOT, "%.0f", Float.valueOf(f));
        }

        void setPitch(float f) {
            this.field_22753 = FirstPersonHidePitchSlider.toSliderValue(f);
            this.method_25346();
        }

        private static double toSliderValue(float f) {
            float f2 = 0.0f;
            float f3 = 90.0f;
            return (f - f2) / (f3 - f2);
        }

        private static float sliderToPitch(double d) {
            float f = 0.0f;
            float f2 = 90.0f;
            float f3 = (float)Math.min(1.0, Math.max(0.0, d));
            return f + (f2 - f) * f3;
        }
    }

    static final class BlacklistCatalogEntry {
        final String id;
        final String namespace;
        final String displayName;
        final String searchName;
        final class_1799 icon;

        BlacklistCatalogEntry(String string, String string2, String string3, String string4, class_1799 class_17992) {
            this.id = string;
            this.namespace = string2;
            this.displayName = string3;
            this.searchName = string4;
            this.icon = class_17992;
        }
    }

    static final class BlacklistModFilter {
        final String namespace;
        final String displayName;

        BlacklistModFilter(String string, String string2) {
            this.namespace = string;
            this.displayName = string2;
        }
    }

    static abstract class StyledConfigSlider
    extends class_357 {
        protected StyledConfigSlider(int n, int n2, int n3, int n4, class_2561 class_25612, double d) {
            super(n, n2, n3, n4, class_25612, d);
        }

        protected abstract String sliderLabel();

        protected abstract String sliderValueText();

        protected String sliderReservedValueText() {
            return this.sliderValueText();
        }

        protected void drawStyled(class_332 class_3322, int n, int n2) {
            class_310 class_3102 = class_310.method_1551();
            if (this.field_22764 && class_3102 != null && class_3102.field_1772 != null) {
                int n3;
                int n4;
                int n5;
                int n6;
                String string;
                int n7;
                int n8 = this.method_46426();
                int n9 = this.method_46427();
                int n10 = n8 + this.method_25368();
                int n11 = n9 + this.method_25364();
                boolean bl = n >= n8 && n < n10 && n2 >= n9 && n2 < n11;
                boolean bl2 = this.method_25370();
                String string2 = this.sliderLabel();
                String string3 = this.sliderValueText();
                String string4 = this.sliderReservedValueText();
                int n12 = n9 + (this.method_25364() - 8) / 2;
                int n13 = n8 + 10;
                int n14 = n10 - 10 - class_3102.field_1772.method_1727(string3);
                int n15 = n10 - 10 - class_3102.field_1772.method_1727(string4);
                int n16 = n15 - 8;
                if (n16 <= (n7 = n13 + class_3102.field_1772.method_1727(string = class_3102.field_1772.method_27523(string2, n6 = Math.max(10, n15 - n13 - 16))) + 10) + 12) {
                    n7 = n8 + 8;
                    n16 = n10 - 8;
                }
                int n17 = Math.max(1, n16 - n7);
                if (!this.field_22763) {
                    n5 = -14801620;
                    n4 = -12694955;
                    n3 = -8483429;
                } else if (!bl2 && !bl) {
                    n5 = -13682107;
                    n4 = -7429710;
                    n3 = -1;
                } else {
                    n5 = -12958126;
                    n4 = -3048067;
                    n3 = -1;
                }
                class_3322.method_25294(n8, n9, n10, n11, n5);
                class_3322.method_25294(n8, n9, n10, n9 + 1, n4);
                class_3322.method_25294(n8, n11 - 1, n10, n11, n4);
                class_3322.method_25294(n8, n9, n8 + 1, n11, n4);
                class_3322.method_25294(n10 - 1, n9, n10, n11, n4);
                int n18 = n9 + this.method_25364() / 2 - 2;
                int n19 = n18 + 4;
                class_3322.method_25294(n7, n18, n16, n19, -1441719774);
                int n20 = 8;
                int n21 = Math.max(0, n17 - n20);
                int n22 = n7 + (int)Math.round(this.field_22753 * (double)n21);
                int n23 = n22 + n20;
                int n24 = n22 + n20 / 2;
                class_3322.method_25294(n7, n18, n24, n19, -7459282);
                int n25 = !this.field_22763 ? -11641751 : (!bl2 && !bl ? -7709604 : -3048067);
                class_3322.method_25294(n22, n9 + 2, n23, n11 - 2, n25);
                class_3322.method_25294(n22, n9 + 2, n23, n9 + 3, -4732456);
                class_3322.method_25294(n22, n11 - 3, n23, n11 - 2, -14404542);
                class_3322.method_25303(class_3102.field_1772, string, n13, n12, n3);
                class_3322.method_25303(class_3102.field_1772, string3, n14, n12, n3);
            }
        }
    }

    static final class BlacklistHitbox {
        final int left;
        final int top;
        final int right;
        final int bottom;
        final String key;

        BlacklistHitbox(int n, int n2, int n3, int n4, String string) {
            this.left = n;
            this.top = n2;
            this.right = n3;
            this.bottom = n4;
            this.key = string;
        }
    }
}
