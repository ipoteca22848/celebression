package ru.method.celebression.punchy.client.tuning;

import java.awt.Desktop;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_156;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_342;
import net.minecraft.class_3532;
import net.minecraft.class_357;
import net.minecraft.class_364;
import net.minecraft.class_4068;
import net.minecraft.class_410;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_6379;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.render.FishingLineProxyTuning;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.ArmToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.PlayerArmModelLayer;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.tuning.TuningProfileManager;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class TuningWorkbenchScreen
extends class_437 {
    private Scope scope = Scope.SPECIFIC;
    private boolean leftHand = false;
    private VanillaFirstPersonItemLayer.ToolKind selectedKind = VanillaFirstPersonItemLayer.ToolKind.AXE;
    private class_4185 scopeButton;
    private class_4185 handButton;
    private class_4185 targetButton;
    private class_4185 kindButton;
    private class_4185 resetButton;
    private class_4185 resetCurrentButton;
    private class_4185 mirrorButton;
    private class_4185 reloadTuningButton;
    private class_4185 saveProfileButton;
    private class_4185 loadProfileButton;
    private class_4185 deleteProfileButton;
    private class_4185 importProfileButton;
    private class_4185 doneButton;
    private FloatSlider offX;
    private FloatSlider offY;
    private FloatSlider offZ;
    private FloatSlider rotX;
    private FloatSlider rotY;
    private FloatSlider rotZ;
    private FloatSlider scale;
    private FloatSlider pivotX;
    private FloatSlider pivotY;
    private FloatSlider pivotZ;
    private final List<FloatSlider> sliders = new ArrayList<FloatSlider>();
    private final List<class_339> uiWidgets = new ArrayList<class_339>();
    private FloatSlider activeSlider = null;
    private class_342 profileNameBox;
    private List<String> profiles = List.of();
    private int selectedProfile = -1;
    private String statusMessage = "";
    private boolean syncing = false;
    private boolean compactLayout = false;
    private int scrollOffset = 0;
    private int maxScroll = 0;
    private int profilesTitleX = 0;
    private int profilesTitleY = 0;
    private int profileListX = 0;
    private int profileListY = 0;
    private int profileListW = 0;
    private int profileListH = 0;
    private int profileListScroll = 0;
    private boolean draggingMainScrollbar = false;
    private boolean draggingProfileScrollbar = false;
    private final List<class_4185> profileRowButtons = new ArrayList<class_4185>();
    private int containerLeft = 0;
    private int containerRight = 0;
    private int mainScrollX = 0;
    private int mainScrollTop = 0;
    private int mainScrollBottom = 0;
    private int profileScrollX = 0;
    private int profileScrollTop = 0;
    private int profileScrollBottom = 0;
    private int mainPanelLeft = 0;
    private int mainPanelTop = 0;
    private int mainPanelRight = 0;
    private int mainPanelBottom = 0;
    private int leftPanelLeft = 0;
    private int leftPanelTop = 0;
    private int leftPanelRight = 0;
    private int leftPanelBottom = 0;
    private int rightPanelLeft = 0;
    private int rightPanelTop = 0;
    private int rightPanelRight = 0;
    private int rightPanelBottom = 0;
    private EditorTarget editorTarget = EditorTarget.ITEM;
    private boolean hasUnsavedProfileChanges = false;
    private boolean deleteProfileConfirmOpen = false;
    private String pendingDeleteProfileName = null;
    private int deleteModalLeft = 0;
    private int deleteModalTop = 0;
    private int deleteModalRight = 0;
    private int deleteModalBottom = 0;
    private int deleteConfirmButtonLeft = 0;
    private int deleteConfirmButtonTop = 0;
    private int deleteConfirmButtonRight = 0;
    private int deleteConfirmButtonBottom = 0;
    private int deleteCancelButtonLeft = 0;
    private int deleteCancelButtonTop = 0;
    private int deleteCancelButtonRight = 0;
    private int deleteCancelButtonBottom = 0;
    private final class_437 parentScreen;

    public TuningWorkbenchScreen() {
        this(null);
    }

    public TuningWorkbenchScreen(class_437 class_4372) {
        super((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.title"));
        this.parentScreen = class_4372;
    }

    public static boolean shouldRenderPivotMarker() {
        boolean bl;
        class_437 class_4372;
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && (class_4372 = class_3102.field_1755) instanceof TuningWorkbenchScreen) {
            TuningWorkbenchScreen tuningWorkbenchScreen = (TuningWorkbenchScreen)class_4372;
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    protected void method_25426() {
        this.method_37067();
        this.sliders.clear();
        this.uiWidgets.clear();
        this.profileRowButtons.clear();
        int n = 200;
        int n2 = 20;
        this.scopeButton = this.addUiWidget(class_4185.method_46430((class_2561)this.scopeLabel(), class_41852 -> {
            if (this.isArmEditor()) {
                this.scope = switch (this.scope.ordinal()) {
                    default -> throw new IllegalStateException("Unexpected enum value");
                    case 0 -> Scope.SPECIFIC;
                    case 1 -> Scope.GLOBAL;
                };
                this.refreshHeaderButtons();
                this.loadFromCurrentProfile();
                this.layoutWidgets();
            }
        }).method_46433(0, 0).method_46437(110, 20).method_46431());
        this.handButton = this.addUiWidget(class_4185.method_46430((class_2561)this.handLabel(), class_41852 -> {
            this.leftHand = !this.leftHand;
            this.autoKindFromHeldItem();
            this.refreshHeaderButtons();
            this.loadFromCurrentProfile();
            this.layoutWidgets();
        }).method_46433(0, 0).method_46437(90, 20).method_46431());
        this.targetButton = this.addUiWidget(class_4185.method_46430((class_2561)this.targetLabel(), class_41852 -> {
            this.cycleEditorTarget();
            this.normalizeScopeForTarget();
            this.activeSlider = null;
            this.refreshHeaderButtons();
            this.loadFromCurrentProfile();
            this.layoutWidgets();
        }).method_46433(0, 0).method_46437(92, 20).method_46431());
        this.kindButton = this.addUiWidget(class_4185.method_46430((class_2561)this.kindLabel(), class_41852 -> {}).method_46433(0, 0).method_46437(160, 20).method_46431());
        this.kindButton.field_22763 = false;
        this.offX = this.addSlider(0, 0, n, n2, "offX", -2.0f, 2.0f);
        this.offY = this.addSlider(0, 0, n, n2, "offY", -2.0f, 2.0f);
        this.offZ = this.addSlider(0, 0, n, n2, "offZ", -2.0f, 2.0f);
        this.rotX = this.addSlider(0, 0, n, n2, "rotX", -180.0f, 180.0f);
        this.rotY = this.addSlider(0, 0, n, n2, "rotY", -180.0f, 180.0f);
        this.rotZ = this.addSlider(0, 0, n, n2, "rotZ", -180.0f, 180.0f);
        this.scale = this.addSlider(0, 0, n, n2, "scale", 0.1f, 3.0f);
        this.pivotX = this.addSlider(0, 0, n, n2, "pivotX", -1.0f, 1.0f);
        this.pivotY = this.addSlider(0, 0, n, n2, "pivotY", -1.0f, 1.0f);
        this.pivotZ = this.addSlider(0, 0, n, n2, "pivotZ", -1.0f, 1.0f);
        this.resetButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.reset_to_default"), class_41852 -> {
            boolean bl = TuningProfileManager.unloadActiveProfileToDefault();
            String string = this.statusMessage = bl ? class_2561.method_43471((String)"punchy.tuning.ui.status.reset_to_default_done").getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.reset_to_default_failed").getString();
            if (bl) {
                this.hasUnsavedProfileChanges = false;
            }
            this.refreshProfiles(true);
            this.loadFromCurrentProfile();
        }).method_46433(0, 0).method_46437(120, 20).method_46431());
        this.resetCurrentButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.reset_current"), class_41852 -> {
            this.resetCurrentProfile();
            this.loadFromCurrentProfile();
        }).method_46433(0, 0).method_46437(120, 20).method_46431());
        this.mirrorButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.mirror_hand"), class_41852 -> {
            this.mirrorToOtherHand();
            this.loadFromCurrentProfile();
        }).method_46433(0, 0).method_46437(120, 20).method_46431());
        this.reloadTuningButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.export_tuning_helper_dev"), class_41852 -> {
            boolean bl = PunchyTuningConfig.exportTuningHelperDevAndOpen();
            this.statusMessage = bl ? class_2561.method_43471((String)"punchy.tuning.ui.status.export_tuning_helper_dev_done").getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.export_tuning_helper_dev_failed").getString();
        }).method_46433(0, 0).method_46437(120, 20).method_46431());
        this.profileNameBox = new class_342(this.field_22793, 0, 0, 220, 20, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.profile_name"));
        this.profileNameBox.method_1880(64);
        this.addUiWidget(this.profileNameBox);
        this.saveProfileButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.save_profile"), class_41852 -> {
            boolean bl;
            String string = this.profileNameBox.method_1882();
            if (string == null || string.isBlank()) {
                string = this.defaultProfileName();
                this.profileNameBox.method_1852(string);
            }
            String string2 = this.statusMessage = (bl = TuningProfileManager.saveCurrentAs(string)) ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_saved", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_save_failed").getString();
            if (bl) {
                this.hasUnsavedProfileChanges = false;
            }
            this.refreshProfiles(true);
        }).method_46433(0, 0).method_46437(106, 20).method_46431());
        this.loadProfileButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.load_profile"), class_41852 -> {
            String string = this.selectedProfileName();
            boolean bl = string != null && TuningProfileManager.loadProfile(string);
            String string2 = this.statusMessage = bl ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_loaded", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_load_failed").getString();
            if (bl) {
                this.hasUnsavedProfileChanges = false;
            }
            this.loadFromCurrentProfile();
            this.refreshProfiles(false);
        }).method_46433(0, 0).method_46437(106, 20).method_46431());
        this.deleteProfileButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.delete_profile"), class_41852 -> this.openDeleteProfileConfirm()).method_46433(0, 0).method_46437(220, 20).method_46431());
        this.importProfileButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.open_profiles_folder"), class_41852 -> {
            boolean bl = this.openProfilesFolder();
            this.statusMessage = bl ? class_2561.method_43471((String)"punchy.tuning.ui.status.profiles_folder_opened").getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profiles_folder_open_failed").getString();
        }).method_46433(0, 0).method_46437(220, 20).method_46431());
        this.doneButton = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.done"), class_41852 -> this.method_25419()).method_46433(0, 0).method_46437(220, 20).method_46431());
        int n3 = 0;
        while (n3 < 6) {
            int n4 = n3++;
            class_4185 class_41853 = this.addUiWidget(class_4185.method_46430((class_2561)class_2561.method_43473(), class_41852 -> this.selectProfileAtVisibleRow(n4)).method_46433(0, 0).method_46437(220, 14).method_46431());
            this.profileRowButtons.add(class_41853);
        }
        this.normalizeScopeForTarget();
        this.hasUnsavedProfileChanges = false;
        this.refreshProfiles(true);
        this.autoKindFromHeldItem();
        this.refreshHeaderButtons();
        this.loadFromCurrentProfile();
        this.layoutWidgets();
    }

    public boolean method_25421() {
        return false;
    }

    public void method_25419() {
        if (this.hasUnsavedProfileChanges) {
            this.confirmDiscardUnsavedProfileChanges();
        } else {
            this.discardUnsavedChangesAndClose();
        }
    }

    private void discardUnsavedChangesAndClose() {
        class_310 class_3102 = this.field_22787;
        if (class_3102 != null) {
            PunchyTuningConfig.reloadActiveProfileFast(class_3102.method_1478());
            if (this.parentScreen != null) {
                class_3102.method_1507(this.parentScreen);
                return;
            }
        }
        super.method_25419();
    }

    private void confirmDiscardUnsavedProfileChanges() {
        class_310 class_3102 = this.field_22787;
        if (class_3102 == null) {
            this.discardUnsavedChangesAndClose();
        } else {
            class_3102.method_1507((class_437)new class_410(bl -> {
                if (bl) {
                    this.hasUnsavedProfileChanges = false;
                    this.discardUnsavedChangesAndClose();
                } else {
                    class_3102.method_1507((class_437)this);
                }
            }, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.title"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.message"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.ignore_exit"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.cancel")));
        }
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        this.applyFocusVisibility();
        if (this.activeSlider != null) {
            this.activeSlider.drawStyled(class_3322, n, n2);
        } else {
            int n3;
            class_3322.method_25294(0, 0, this.field_22789, this.field_22790, -2013265920);
            this.drawWorkbenchPanels(class_3322);
            ArrayList<class_4185> arrayList = new ArrayList<class_4185>();
            ArrayList<class_2561> arrayList2 = new ArrayList<class_2561>();
            ArrayList<FloatSlider> arrayList3 = new ArrayList<FloatSlider>();
            ArrayList<class_2561> arrayList4 = new ArrayList<class_2561>();
            for (class_339 class_3392 : this.uiWidgets) {
                if (class_3392 instanceof class_4185) {
                    class_4185 class_41852 = (class_4185)class_3392;
                    if (class_41852.field_22764) {
                        arrayList.add(class_41852);
                        arrayList2.add(class_41852.method_25369());
                        class_41852.method_25355((class_2561)class_2561.method_43473());
                        continue;
                    }
                }
                if (!(class_3392 instanceof FloatSlider)) continue;
                FloatSlider floatSlider = (FloatSlider)class_3392;
                if (!floatSlider.field_22764) continue;
                arrayList3.add(floatSlider);
                arrayList4.add(floatSlider.method_25369());
                floatSlider.method_25355((class_2561)class_2561.method_43473());
            }
            super.method_25394(class_3322, n, n2, f);
            for (n3 = 0; n3 < arrayList.size(); ++n3) {
                ((class_4185)arrayList.get(n3)).method_25355((class_2561)arrayList2.get(n3));
            }
            for (n3 = 0; n3 < arrayList3.size(); ++n3) {
                ((FloatSlider)((Object)arrayList3.get(n3))).method_25355((class_2561)arrayList4.get(n3));
            }
            this.renderStyledSliders(class_3322, n, n2);
            this.renderStyledButtons(class_3322, n, n2);
            class_3322.method_27535(this.field_22793, this.field_22785, 14, 4, 0xFFFFFF);
            class_3322.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"punchy.tuning.ui.held_item", (Object[])new Object[]{this.currentHeldItemString()}), 14, this.field_22790 - 28, 0xE0E0E0);
            this.renderKindButtonContent(class_3322);
            this.renderProfileList(class_3322);
            this.renderProfileScrollbar(class_3322);
            this.renderMainScrollbar(class_3322);
            class_3322.method_25303(this.field_22793, this.statusMessage == null ? "" : this.statusMessage, this.profilesTitleX, this.field_22790 - 14, 11075504);
            this.drawDeleteProfileConfirmOverlay(class_3322, n, n2);
        }
    }

    private void renderStyledSliders(class_332 class_3322, int n, int n2) {
        for (FloatSlider floatSlider : this.sliders) {
            if (!floatSlider.field_22764) continue;
            floatSlider.drawStyled(class_3322, n, n2);
        }
    }

    private void renderStyledButtons(class_332 class_3322, int n, int n2) {
        for (class_339 class_3392 : this.uiWidgets) {
            if (!(class_3392 instanceof class_4185)) continue;
            class_4185 class_41852 = (class_4185)class_3392;
            if (!class_41852.field_22764) continue;
            boolean bl = class_41852 == this.deleteProfileButton;
            this.drawBlacklistButtonStyle(class_3322, class_41852, n, n2, bl);
        }
    }

    private void drawBlacklistButtonStyle(class_332 class_3322, class_4185 class_41852, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6 = class_41852.method_46426();
        int n7 = class_41852.method_46427();
        int n8 = n6 + class_41852.method_25368();
        int n9 = n7 + class_41852.method_25364();
        boolean bl2 = n >= n6 && n < n8 && n2 >= n7 && n2 < n9;
        boolean bl3 = class_41852.field_22763;
        if (!bl3) {
            n5 = -14801620;
            n4 = -12694955;
            n3 = -8483429;
        } else if (bl) {
            n5 = bl2 ? -7459282 : -9690590;
            n4 = -3048067;
            n3 = -1;
        } else if (bl2) {
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
        String string2 = this.resolveScrollableButtonLabel(string, n10, bl2);
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

    private void drawWorkbenchPanels(class_332 class_3322) {
        int n = -267381478;
        int n2 = -11641751;
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelRight, this.mainPanelBottom, n);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelRight, this.mainPanelTop + 1, n2);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelBottom - 1, this.mainPanelRight, this.mainPanelBottom, n2);
        class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelLeft + 1, this.mainPanelBottom, n2);
        class_3322.method_25294(this.mainPanelRight - 1, this.mainPanelTop, this.mainPanelRight, this.mainPanelBottom, n2);
        int n3 = 857944122;
        int n4 = 1430145621;
        class_3322.method_25294(this.leftPanelLeft, this.leftPanelTop, this.leftPanelRight, this.leftPanelBottom, n3);
        class_3322.method_25294(this.leftPanelLeft, this.leftPanelTop, this.leftPanelRight, this.leftPanelTop + 1, n4);
        class_3322.method_25294(this.leftPanelLeft, this.leftPanelBottom - 1, this.leftPanelRight, this.leftPanelBottom, n4);
        class_3322.method_25294(this.leftPanelLeft, this.leftPanelTop, this.leftPanelLeft + 1, this.leftPanelBottom, n4);
        class_3322.method_25294(this.leftPanelRight - 1, this.leftPanelTop, this.leftPanelRight, this.leftPanelBottom, n4);
        class_3322.method_25294(this.rightPanelLeft, this.rightPanelTop, this.rightPanelRight, this.rightPanelBottom, n3);
        class_3322.method_25294(this.rightPanelLeft, this.rightPanelTop, this.rightPanelRight, this.rightPanelTop + 1, n4);
        class_3322.method_25294(this.rightPanelLeft, this.rightPanelBottom - 1, this.rightPanelRight, this.rightPanelBottom, n4);
        class_3322.method_25294(this.rightPanelLeft, this.rightPanelTop, this.rightPanelLeft + 1, this.rightPanelBottom, n4);
        class_3322.method_25294(this.rightPanelRight - 1, this.rightPanelTop, this.rightPanelRight, this.rightPanelBottom, n4);
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
        if (this.activeSlider == null) {
            super.method_25420(class_3322, n, n2, f);
        }
    }

    public void method_52752(class_332 class_3322) {
        if (this.activeSlider == null) {
            super.method_52752(class_3322);
        }
    }

    public void method_25393() {
        super.method_25393();
        this.autoKindFromHeldItem();
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.deleteProfileConfirmOpen) {
            if (n == 256) {
                this.cancelDeleteProfileConfirm();
                return true;
            }
            if (n != 257 && n != 335) {
                return true;
            }
            this.confirmDeleteProfile();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25402(double d, double d2, int n) {
        if (this.deleteProfileConfirmOpen) {
            if (n == 0) {
                if (TuningWorkbenchScreen.isMouseInside(d, d2, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom)) {
                    this.confirmDeleteProfile();
                    return true;
                }
                if (TuningWorkbenchScreen.isMouseInside(d, d2, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom)) {
                    this.cancelDeleteProfileConfirm();
                    return true;
                }
            }
            return true;
        }
        if (n == 0 && this.activeSlider == null) {
            if (this.maxScroll > 0 && this.isMouseOverMainScrollbar(d, d2)) {
                this.draggingMainScrollbar = true;
                this.updateMainScrollFromMouse(d2);
                return true;
            }
            if (this.maxProfileScroll() > 0 && this.isMouseOverProfileScrollbar(d, d2)) {
                this.draggingProfileScrollbar = true;
                this.updateProfileScrollFromMouse(d2);
                return true;
            }
        }
        boolean bl = super.method_25402(d, d2, n);
        if (n == 0) {
            this.activeSlider = null;
            for (FloatSlider floatSlider : this.sliders) {
                if (!floatSlider.field_22764 || !floatSlider.field_22763 || !floatSlider.method_25405(d, d2)) continue;
                this.activeSlider = floatSlider;
                break;
            }
            if (this.activeSlider == null && this.trySelectProfileFromList(d, d2)) {
                bl = true;
            }
            this.applyFocusVisibility();
        }
        return bl;
    }

    public boolean method_25406(double d, double d2, int n) {
        if (this.deleteProfileConfirmOpen) {
            return true;
        }
        if (n == 0) {
            this.draggingMainScrollbar = false;
            this.draggingProfileScrollbar = false;
        }
        boolean bl = super.method_25406(d, d2, n);
        if (n == 0 && this.activeSlider != null) {
            this.activeSlider = null;
            this.applyFocusVisibility();
        }
        return bl;
    }

    public boolean method_25403(double d, double d2, int n, double d3, double d4) {
        if (this.deleteProfileConfirmOpen) {
            return true;
        }
        if (n == 0) {
            if (this.draggingMainScrollbar) {
                this.updateMainScrollFromMouse(d2);
                return true;
            }
            if (this.draggingProfileScrollbar) {
                this.updateProfileScrollFromMouse(d2);
                return true;
            }
        }
        return super.method_25403(d, d2, n, d3, d4);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        if (this.deleteProfileConfirmOpen) {
            return true;
        }
        if (this.activeSlider == null && d4 != 0.0 && this.isMouseOverProfileList(d, d2)) {
            int n = this.visibleProfileRows();
            int n2 = Math.max(0, this.profiles.size() - n);
            if (n2 > 0) {
                this.profileListScroll = class_3532.method_15340((int)(this.profileListScroll - (int)Math.signum(d4)), (int)0, (int)n2);
                this.updateProfileRowButtons();
                return true;
            }
        }
        if (this.activeSlider == null && this.maxScroll > 0 && d4 != 0.0) {
            this.scrollOffset = class_3532.method_15340((int)(this.scrollOffset - (int)Math.round(d4 * 24.0)), (int)0, (int)this.maxScroll);
            this.layoutWidgets();
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
    }

    private void autoKindFromHeldItem() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_5998(this.leftHand ? class_1268.field_5810 : class_1268.field_5808);
            class_1799 oppositeStack = class_3102.field_1724.method_5998(this.leftHand ? class_1268.field_5808 : class_1268.field_5810);
            if ((class_17992 == null || class_17992.method_7960()) && oppositeStack != null && !oppositeStack.method_7960()) {
                this.leftHand = !this.leftHand;
                class_17992 = oppositeStack;
            }
            if (class_17992 != null && !class_17992.method_7960()) {
                VanillaFirstPersonItemLayer.ToolKind resolvedKind = this.resolveTuningKind(class_17992);
                if (this.isFishingLineEditor() && !this.isFishingRodStack(class_17992)) {
                    this.editorTarget = EditorTarget.ITEM;
                    this.normalizeScopeForTarget();
                    this.loadFromCurrentProfile();
                }
                if (resolvedKind != this.selectedKind) {
                    this.selectedKind = resolvedKind;
                    this.refreshHeaderButtons();
                    this.loadFromCurrentProfile();
                } else {
                    this.refreshHeaderButtons();
                }
            } else {
                this.refreshHeaderButtons();
            }
        }
    }

    private String currentHeldItemString() {
        if (this.isFishingLineEditor()) {
            return "fishing line proxy: rod";
        }
        if (this.isArmSpecificEmptyHandTarget()) {
            return class_2561.method_43471((String)"punchy.tuning.selection.empty_hand").getString();
        }
        class_2960 class_29602 = this.heldItemId();
        return class_29602 == null ? class_2561.method_43471((String)"punchy.tuning.selection.none_item").getString() : class_29602.toString();
    }

    private class_1799 heldItemStack() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_5998(this.leftHand ? class_1268.field_5810 : class_1268.field_5808);
            return class_17992 == null ? class_1799.field_8037 : class_17992;
        }
        return class_1799.field_8037;
    }

    private VanillaFirstPersonItemLayer.ToolKind resolveTuningKind(class_1799 class_17992) {
        return SpearStateMachine.isSpearStack(class_17992) ? VanillaFirstPersonItemLayer.ToolKind.SPEAR : ToolKindResolver.resolveItemKind(class_17992, this.selectedKind);
    }

    private FloatSlider addSlider(int n, int n2, int n3, int n4, String string, float f2, float f3) {
        FloatSlider floatSlider = new FloatSlider(n, n2, n3, n4, string, f2, f3, f -> {
            if (!this.syncing) {
                this.applyToCurrentProfile();
            }
        });
        this.sliders.add(floatSlider);
        this.addUiWidget(floatSlider);
        return floatSlider;
    }

    private void loadFromCurrentProfile() {
        this.configureSliderRangesForCurrentTarget();
        ToolTuning.Profile profile = this.isItemEditor() ? this.getCurrentProfile() : null;
        ArmToolTuning.Profile profile2 = this.isArmEditor() ? this.getCurrentArmProfile() : null;
        FishingLineProxyTuning.Profile profile3 = this.isFishingLineEditor() ? this.getCurrentFishingLineProfile() : null;
        boolean bl = profile != null || profile2 != null || profile3 != null;
        boolean bl2 = this.shouldHideArmGlobalRotationControls();
        this.syncing = true;
        if (profile != null) {
            this.offX.setFloat(profile.offX);
            this.offY.setFloat(profile.offY);
            this.offZ.setFloat(profile.offZ);
            this.rotX.setFloat(profile.rotX);
            this.rotY.setFloat(profile.rotY);
            this.rotZ.setFloat(profile.rotZ);
            this.scale.setFloat(profile.scale);
            this.pivotX.setFloat(profile.pivotX);
            this.pivotY.setFloat(profile.pivotY);
            this.pivotZ.setFloat(profile.pivotZ);
        } else if (profile2 != null) {
            ArmToolTuning.Profile profile4 = this.armEditorBaseProfile();
            this.offX.setFloat(profile2.offX - profile4.offX);
            this.offY.setFloat(profile2.offY - profile4.offY);
            this.offZ.setFloat(profile2.offZ - profile4.offZ);
            this.rotX.setFloat(profile2.rotX - profile4.rotX);
            this.rotY.setFloat(profile2.rotY - profile4.rotY);
            this.rotZ.setFloat(profile2.rotZ - profile4.rotZ);
            this.scale.setFloat(profile2.scale);
            this.pivotX.setFloat(0.0f);
            this.pivotY.setFloat(0.0f);
            this.pivotZ.setFloat(0.0f);
        } else if (profile3 != null) {
            this.offX.setFloat(profile3.offX);
            this.offY.setFloat(profile3.offY);
            this.offZ.setFloat(profile3.offZ);
            this.rotX.setFloat(profile3.rotX);
            this.rotY.setFloat(profile3.rotY);
            this.rotZ.setFloat(profile3.rotZ);
            this.scale.setFloat(profile3.scale);
            this.pivotX.setFloat(0.0f);
            this.pivotY.setFloat(0.0f);
            this.pivotZ.setFloat(0.0f);
        }
        this.syncing = false;
        for (FloatSlider floatSlider : this.sliders) {
            floatSlider.field_22763 = bl;
        }
        if (this.isArmEditor()) {
            this.rotX.field_22763 = !bl2 && bl;
            this.rotY.field_22763 = !bl2 && bl;
            this.rotZ.field_22763 = !bl2 && bl;
            this.rotX.field_22764 = !bl2;
            this.rotY.field_22764 = !bl2;
            this.rotZ.field_22764 = !bl2;
            this.pivotX.field_22763 = false;
            this.pivotY.field_22763 = false;
            this.pivotZ.field_22763 = false;
            this.pivotX.field_22764 = false;
            this.pivotY.field_22764 = false;
            this.pivotZ.field_22764 = false;
            if (this.activeSlider != null && (this.activeSlider.isPivotSlider() || bl2 && this.activeSlider.isRotationSlider())) {
                this.activeSlider = null;
            }
        } else if (this.isFishingLineEditor()) {
            this.pivotX.field_22763 = false;
            this.pivotY.field_22763 = false;
            this.pivotZ.field_22763 = false;
            this.pivotX.field_22764 = false;
            this.pivotY.field_22764 = false;
            this.pivotZ.field_22764 = false;
            if (this.activeSlider != null && this.activeSlider.isPivotSlider()) {
                this.activeSlider = null;
            }
        }
    }

    private void configureSliderRangesForCurrentTarget() {
        if (this.isFishingLineEditor()) {
            this.offX.setRange(-32.0f, 32.0f);
            this.offY.setRange(-32.0f, 32.0f);
            this.offZ.setRange(-32.0f, 32.0f);
            this.rotX.setRange(-720.0f, 720.0f);
            this.rotY.setRange(-720.0f, 720.0f);
            this.rotZ.setRange(-720.0f, 720.0f);
            this.scale.setRange(0.01f, 10.0f);
            this.pivotX.setRange(-1.0f, 1.0f);
            this.pivotY.setRange(-1.0f, 1.0f);
            this.pivotZ.setRange(-1.0f, 1.0f);
        } else {
            this.offX.setRange(-2.0f, 2.0f);
            this.offY.setRange(-2.0f, 2.0f);
            this.offZ.setRange(-2.0f, 2.0f);
            this.rotX.setRange(-180.0f, 180.0f);
            this.rotY.setRange(-180.0f, 180.0f);
            this.rotZ.setRange(-180.0f, 180.0f);
            this.scale.setRange(0.1f, 3.0f);
            this.pivotX.setRange(-1.0f, 1.0f);
            this.pivotY.setRange(-1.0f, 1.0f);
            this.pivotZ.setRange(-1.0f, 1.0f);
        }
    }

    private ToolTuning.Profile getCurrentProfile() {
        boolean bl = this.selectedHandTargetsLeftArm();
        return switch (this.scope.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> ToolTuning.getGlobal(bl);
            case 1 -> {
                class_1799 heldStack = this.heldItemStack();
                if (heldStack != null && !heldStack.method_7960()) {
                    yield ToolTuning.getSpecificProfile(heldStack, this.selectedKind, bl);
                }
                yield null;
            }
        };
    }

    private void applyToCurrentProfile() {
        if (this.isFishingLineEditor()) {
            FishingLineProxyTuning.Profile profile = this.getCurrentFishingLineProfile();
            if (profile == null) {
                this.statusMessage = "Hold a fishing rod to edit fishing line proxy";
            } else {
                profile.offX = this.offX.getFloat();
                profile.offY = this.offY.getFloat();
                profile.offZ = this.offZ.getFloat();
                profile.rotX = this.rotX.getFloat();
                profile.rotY = this.rotY.getFloat();
                profile.rotZ = this.rotZ.getFloat();
                profile.scale = this.scale.getFloat();
                FishingLineProxyTuning.apply(FishingLineProxyTuning.Kind.ROD, this.selectedHandTargetsLeftArm(), profile);
                this.hasUnsavedProfileChanges = true;
            }
        } else if (this.isArmEditor()) {
            ArmToolTuning.Profile profile = this.getCurrentArmProfile();
            if (profile == null) {
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.toast.hold_item_edit_specific").getString();
            } else {
                ArmToolTuning.Profile profile2 = this.armEditorBaseProfile();
                profile.offX = profile2.offX + this.offX.getFloat();
                profile.offY = profile2.offY + this.offY.getFloat();
                profile.offZ = profile2.offZ + this.offZ.getFloat();
                profile.rotX = profile2.rotX + this.rotX.getFloat();
                profile.rotY = profile2.rotY + this.rotY.getFloat();
                profile.rotZ = profile2.rotZ + this.rotZ.getFloat();
                profile.scale = this.scale.getFloat();
                PlayerArmModelLayer.ToolKind toolKind = this.currentArmKind();
                boolean bl = this.selectedHandTargetsLeftArm();
                switch (this.scope.ordinal()) {
                    case 0: {
                        ArmToolTuning.commitGlobal(bl);
                        break;
                    }
                    case 1: {
                        class_2960 class_29602 = this.heldItemId();
                        if (class_29602 == null) {
                            ArmToolTuning.commitKind(PlayerArmModelLayer.ToolKind.EMPTY_HAND, bl);
                            break;
                        }
                        ArmToolTuning.commitSpecific(class_29602, bl);
                    }
                }
                this.hasUnsavedProfileChanges = true;
            }
        } else {
            ToolTuning.Profile profile = this.getCurrentProfile();
            if (profile == null) {
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.toast.hold_item_edit_specific").getString();
            } else {
                profile.offX = this.offX.getFloat();
                profile.offY = this.offY.getFloat();
                profile.offZ = this.offZ.getFloat();
                profile.rotX = this.rotX.getFloat();
                profile.rotY = this.rotY.getFloat();
                profile.rotZ = this.rotZ.getFloat();
                profile.scale = this.scale.getFloat();
                profile.pivotX = this.pivotX.getFloat();
                profile.pivotY = this.pivotY.getFloat();
                profile.pivotZ = this.pivotZ.getFloat();
                profile.recomputeRadians();
                boolean bl = this.selectedHandTargetsLeftArm();
                switch (this.scope.ordinal()) {
                    case 0: {
                        ToolTuning.commitGlobal(bl);
                        break;
                    }
                    case 1: {
                        class_2960 class_29603 = this.heldItemId();
                        if (class_29603 == null) {
                            return;
                        }
                        class_1799 class_17992 = this.heldItemStack();
                        Integer n = null;
                        String string = null;
                        if (class_17992 != null && !class_17992.method_7960()) {
                            n = ToolTuning.resolveCustomModelDataForDebug(class_17992);
                            string = ToolTuning.resolveStackRenamedKeyForDebug(class_17992);
                        }
                        ToolTuning.Profile profile3 = class_17992 != null && !class_17992.method_7960() ? ToolTuning.getSpecificProfile(class_17992, this.selectedKind, false) : ToolTuning.getSpecificProfile(class_29603, this.selectedKind, false);
                        ToolTuning.Profile profile4 = class_17992 != null && !class_17992.method_7960() ? ToolTuning.getSpecificProfile(class_17992, this.selectedKind, true) : ToolTuning.getSpecificProfile(class_29603, this.selectedKind, true);
                        ToolTuning.applySpecific(class_29603, n, this.selectedKind, string, null, null, null, null, null, profile3, profile4);
                    }
                }
                this.hasUnsavedProfileChanges = true;
            }
        }
    }

    private void resetCurrentProfile() {
        if (this.isFishingLineEditor()) {
            FishingLineProxyTuning.reset(FishingLineProxyTuning.Kind.ROD, this.selectedHandTargetsLeftArm());
            this.loadFromCurrentProfile();
            this.hasUnsavedProfileChanges = true;
            this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.current_reset").getString();
        } else if (this.isArmEditor()) {
            ArmToolTuning.Profile profile = this.getCurrentArmProfile();
            if (profile != null) {
                this.syncing = true;
                this.offX.setFloat(0.0f);
                this.offY.setFloat(0.0f);
                this.offZ.setFloat(0.0f);
                this.rotX.setFloat(0.0f);
                this.rotY.setFloat(0.0f);
                this.rotZ.setFloat(0.0f);
                this.scale.setFloat(this.armEditorBaseProfile().scale);
                this.syncing = false;
                this.applyToCurrentProfile();
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.current_reset").getString();
            }
        } else {
            ToolTuning.Profile profile = this.getCurrentProfile();
            if (profile != null) {
                this.syncing = true;
                this.offX.setFloat(0.0f);
                this.offY.setFloat(0.0f);
                this.offZ.setFloat(0.0f);
                this.rotX.setFloat(0.0f);
                this.rotY.setFloat(0.0f);
                this.rotZ.setFloat(0.0f);
                this.scale.setFloat(1.0f);
                this.pivotX.setFloat(0.0f);
                this.pivotY.setFloat(0.0f);
                this.pivotZ.setFloat(0.0f);
                this.syncing = false;
                this.applyToCurrentProfile();
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.current_reset").getString();
            }
        }
    }

    private void mirrorToOtherHand() {
        if (this.isFishingLineEditor()) {
            FishingLineProxyTuning.Profile profile = this.getCurrentFishingLineProfile();
            if (profile != null) {
                FishingLineProxyTuning.apply(FishingLineProxyTuning.Kind.ROD, !this.selectedHandTargetsLeftArm(), FishingLineProxyTuning.mirror(profile));
                this.hasUnsavedProfileChanges = true;
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.mirrored").getString();
            }
        } else if (this.isArmEditor()) {
            ArmToolTuning.Profile profile = this.getCurrentArmProfile();
            if (profile != null) {
                ArmToolTuning.Profile profile2 = profile.mirror();
                boolean bl = !this.selectedHandTargetsLeftArm();
                PlayerArmModelLayer.ToolKind toolKind = this.currentArmKind();
                switch (this.scope.ordinal()) {
                    case 0: {
                        ArmToolTuning.applyGlobal(bl, profile2);
                        ArmToolTuning.commitGlobal(bl);
                        break;
                    }
                    case 1: {
                        class_2960 class_29602 = this.heldItemId();
                        if (class_29602 == null) {
                            ArmToolTuning.apply(PlayerArmModelLayer.ToolKind.EMPTY_HAND, bl, profile2);
                            ArmToolTuning.commitKind(PlayerArmModelLayer.ToolKind.EMPTY_HAND, bl);
                            break;
                        }
                        ArmToolTuning.Profile profile3 = bl ? ArmToolTuning.getSpecificProfile(class_29602, toolKind, false) : profile2;
                        ArmToolTuning.Profile profile4 = bl ? profile2 : ArmToolTuning.getSpecificProfile(class_29602, toolKind, true);
                        ArmToolTuning.applySpecific(class_29602, toolKind, profile3, profile4);
                    }
                }
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.mirrored").getString();
            }
        } else {
            ToolTuning.Profile profile = this.getCurrentProfile();
            if (profile != null) {
                ToolTuning.Profile profile5 = profile.mirror();
                boolean bl = !this.selectedHandTargetsLeftArm();
                switch (this.scope.ordinal()) {
                    case 0: {
                        ToolTuning.applyGlobal(bl, profile5);
                        ToolTuning.commitGlobal(bl);
                        break;
                    }
                    case 1: {
                        class_2960 class_29603 = this.heldItemId();
                        if (class_29603 == null) {
                            return;
                        }
                        class_1799 class_17992 = this.heldItemStack();
                        Integer n = null;
                        String string = null;
                        if (class_17992 != null && !class_17992.method_7960()) {
                            n = ToolTuning.resolveCustomModelDataForDebug(class_17992);
                            string = ToolTuning.resolveStackRenamedKeyForDebug(class_17992);
                        }
                        ToolTuning.Profile profile7 = bl ? (class_17992 != null && !class_17992.method_7960() ? ToolTuning.getSpecificProfile(class_17992, this.selectedKind, false) : ToolTuning.getSpecificProfile(class_29603, this.selectedKind, false)) : profile5;
                        ToolTuning.Profile profile8 = bl ? profile5 : (class_17992 != null && !class_17992.method_7960() ? ToolTuning.getSpecificProfile(class_17992, this.selectedKind, true) : ToolTuning.getSpecificProfile(class_29603, this.selectedKind, true));
                        ToolTuning.applySpecific(class_29603, n, this.selectedKind, string, null, null, null, null, null, profile7, profile8);
                    }
                }
                this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.mirrored").getString();
            }
        }
    }

    private class_2561 scopeLabel() {
        return class_2561.method_43471((String)this.scope.key);
    }

    private class_2561 handLabel() {
        return this.leftHand ? class_2561.method_43471((String)"punchy.tuning.ui.hand.off") : class_2561.method_43471((String)"punchy.tuning.ui.hand.main");
    }

    private class_2561 kindLabel() {
        return this.isFishingLineEditor() ? class_2561.method_43470((String)"Editing fishing line proxy: rod") : class_2561.method_43469((String)"punchy.tuning.ui.editing_item", (Object[])new Object[]{this.currentHeldItemString()});
    }

    private void renderKindButtonContent(class_332 class_3322) {
        if (this.kindButton != null && this.kindButton.field_22764) {
            int n = this.kindButton.method_46426();
            int n2 = this.kindButton.method_46427();
            int n3 = this.kindButton.method_25364();
            class_1799 class_17992 = this.heldItemStack();
            if (class_17992 != null && !class_17992.method_7960()) {
                int n4 = n + 5;
                int n5 = n2 + Math.max(0, (n3 - 16) / 2);
                this.renderItemIcon(class_3322, class_17992, n4, n5);
            }
        }
    }

    private void renderItemIcon(class_332 class_3322, class_1799 class_17992, int n, int n2) {
        if (class_17992 != null && !class_17992.method_7960() && class_3322 != null) {
            class_3322.method_51427(class_17992, n, n2);
        }
    }

    private class_2561 targetLabel() {
        return switch (this.editorTarget.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.ui.editor.item");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.ui.editor.arm");
            case 2 -> class_2561.method_43470((String)"Line");
        };
    }

    private class_2960 heldItemId() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_5998(this.leftHand ? class_1268.field_5810 : class_1268.field_5808);
            return class_17992 != null && !class_17992.method_7960() ? class_7923.field_41178.method_10221(class_17992.method_7909()) : null;
        }
        return null;
    }

    private void refreshProfiles(boolean bl) {
        this.profiles = TuningProfileManager.listProfiles();
        this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)Math.max(0, this.profiles.size() - this.visibleProfileRows()));
        if (this.profiles.isEmpty()) {
            this.selectedProfile = -1;
            if (this.profileNameBox != null) {
                this.profileNameBox.method_1852("");
            }
            this.updateProfileRowButtons();
        } else {
            String string;
            if (this.selectedProfile < 0 || this.selectedProfile >= this.profiles.size() || bl) {
                string = TuningProfileManager.getActiveProfileName();
                if (string != null) {
                    int n = this.profiles.indexOf(string);
                    if (n >= 0) {
                        this.selectedProfile = n;
                    }
                } else if (bl) {
                    this.selectedProfile = -1;
                }
                if (this.selectedProfile < 0) {
                    if (!bl) {
                        this.selectedProfile = 0;
                    }
                } else {
                    this.selectedProfile = class_3532.method_15340((int)this.selectedProfile, (int)0, (int)(this.profiles.size() - 1));
                }
            }
            this.ensureSelectedVisibleInProfileList();
            string = this.selectedProfileName();
            if (this.profileNameBox != null) {
                if (string != null && !string.isBlank()) {
                    this.profileNameBox.method_1852(string);
                } else {
                    this.profileNameBox.method_1852("");
                }
            }
            this.updateProfileRowButtons();
        }
    }

    private String selectedProfileName() {
        return this.selectedProfile >= 0 && this.selectedProfile < this.profiles.size() ? this.profiles.get(this.selectedProfile) : null;
    }

    private void refreshHeaderButtons() {
        if (this.scopeButton != null) {
            this.scopeButton.method_25355(this.scopeLabel());
            this.scopeButton.field_22763 = this.isArmEditor();
        }
        if (this.handButton != null) {
            this.handButton.method_25355(this.handLabel());
        }
        if (this.targetButton != null) {
            this.targetButton.method_25355(this.targetLabel());
        }
        if (this.kindButton != null) {
            this.kindButton.method_25355(this.kindButtonLabel());
            this.kindButton.field_22763 = false;
        }
    }

    private class_2561 kindButtonLabel() {
        String string = this.kindLabel().getString();
        int n = this.kindButton != null ? this.kindButton.method_25368() : 160;
        int n2 = Math.max(40, n - 34);
        String string2 = this.field_22793.method_27523(string, n2);
        return class_2561.method_43470((String)("    " + string2));
    }

    private String defaultProfileName() {
        class_2960 class_29602 = this.heldItemId();
        String string = this.isFishingLineEditor() ? "fishing_line_proxy" : (this.isArmSpecificEmptyHandTarget() ? "empty_hand" : (class_29602 == null ? "tuning" : class_29602.method_12832()));
        String string2 = this.leftHand ? "off" : "main";
        String string3 = this.scope.name().toLowerCase(Locale.ROOT);
        return string + "_" + string2 + "_" + string3;
    }

    private void layoutWidgets() {
        if (this.scopeButton != null) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8 = 24;
            int n9 = 800;
            int n10 = 6;
            int n11 = 20;
            int n12 = 12;
            int n13 = Math.max(260, this.field_22789 - n8 * 2);
            int n14 = Math.min(n9, n13);
            int n15 = n14 - (n7 = class_3532.method_15340((int)(n14 / 3), (int)220, (int)300)) - n12;
            boolean bl = this.compactLayout = n15 < 280;
            if (this.compactLayout) {
                n15 = n14;
                n7 = n14;
            }
            int n16 = this.computeContainerTop(this.compactLayout, n11, n10);
            this.containerLeft = n6 = (this.field_22789 - n14) / 2;
            this.containerRight = n6 + n14;
            int n17 = n5 = this.compactLayout ? n6 : n6 + n15 + n12;
            if (this.compactLayout) {
                n4 = (n15 - n10 * 3) / 4;
                this.place((class_339)this.scopeButton, n6, n16, n4);
                this.place((class_339)this.handButton, n6 + n4 + n10, n16, n4);
                this.place((class_339)this.targetButton, n6 + (n4 + n10) * 2, n16, n4);
                this.place((class_339)this.kindButton, n6 + (n4 + n10) * 3, n16, n15 - (n4 + n10) * 3);
            } else {
                n4 = Math.min(120, Math.max(96, n15 / 4));
                n3 = Math.min(95, Math.max(80, n15 / 5));
                n2 = Math.min(110, Math.max(92, n15 / 6));
                n = n15 - n4 - n3 - n2 - n10 * 3;
                this.place((class_339)this.scopeButton, n6, n16, n4);
                this.place((class_339)this.handButton, n6 + n4 + n10, n16, n3);
                this.place((class_339)this.targetButton, n6 + n4 + n10 + n3 + n10, n16, n2);
                this.place((class_339)this.kindButton, n6 + n4 + n10 + n3 + n10 + n2 + n10, n16, n);
            }
            n4 = n16 + n11 + 2;
            this.place((class_339)this.offX, n6, n4, n15);
            this.place((class_339)this.offY, n6, n4 += n11 + 2, n15);
            this.place((class_339)this.offZ, n6, n4 += n11 + 2, n15);
            n4 += n11 + 2;
            if (!this.shouldHideArmGlobalRotationControls()) {
                this.place((class_339)this.rotX, n6, n4, n15);
                this.place((class_339)this.rotY, n6, n4 += n11 + 2, n15);
                this.place((class_339)this.rotZ, n6, n4 += n11 + 2, n15);
                n4 += n11 + 2;
            }
            this.place((class_339)this.scale, n6, n4, n15);
            this.place((class_339)this.pivotX, n6, n4 += n11 + 2, n15);
            this.place((class_339)this.pivotY, n6, n4 += n11 + 2, n15);
            this.place((class_339)this.pivotZ, n6, n4 += n11 + 2, n15);
            n3 = this.compactLayout ? (n4 += n11 + n10) + n11 + n10 + 12 : n16;
            n2 = 6;
            n = 14;
            int n18 = 4;
            int n19 = n2 * n + n18 * 2;
            this.profileListX = n5;
            this.profileListY = n3 - this.scrollOffset;
            this.profileListW = n7;
            this.profileListH = n19;
            this.layoutProfileRowButtons();
            this.place((class_339)this.profileNameBox, n5, n3 += n19 + 6, n7);
            int n20 = (n7 - n10) / 2;
            this.place((class_339)this.saveProfileButton, n5, n3 += n11 + 4, n20);
            this.place((class_339)this.loadProfileButton, n5 + n20 + n10, n3, n7 - n20 - n10);
            this.place((class_339)this.deleteProfileButton, n5, n3 += n11 + 2, n7);
            int n21 = (n3 += n11 + 14) + n11 + 2;
            int n22 = Math.max(n3, n21 - (n11 + 8));
            this.place((class_339)this.importProfileButton, n5, n22, n7);
            this.place((class_339)this.doneButton, n5, n21, n7);
            n3 = n21 + n11 + n10;
            int n23 = Math.max(n4, n21);
            int n24 = (n15 - n10 * 3) / 4;
            int n25 = n15 - n24 * 3 - n10 * 3;
            this.place((class_339)this.resetButton, n6, n23, n24);
            this.place((class_339)this.resetCurrentButton, n6 + n24 + n10, n23, n24);
            this.place((class_339)this.mirrorButton, n6 + (n24 + n10) * 2, n23, n24);
            this.place((class_339)this.reloadTuningButton, n6 + (n24 + n10) * 3, n23, n25);
            int n26 = n23 + n11 + n10;
            int n27 = Math.max(n26, n3) + 8;
            this.maxScroll = Math.max(0, n27 - (this.field_22790 - 8));
            this.scrollOffset = class_3532.method_15340((int)this.scrollOffset, (int)0, (int)this.maxScroll);
            if (this.maxScroll > 0) {
                this.reflowWithScroll(n14, n6, n15, n5, n7, n16, n10, n11);
            } else {
                this.profilesTitleX = n5;
                this.profilesTitleY = this.profileTitleY(n16, this.compactLayout, n26);
            }
            this.updateWorkbenchPanelRects(n6, n15, n5, n7);
            this.updateMainScrollbarRects();
            this.updateProfileScrollbarRects();
            if (this.kindButton != null) {
                this.kindButton.method_25355(this.kindButtonLabel());
            }
        }
    }

    private int computeContainerTop(boolean bl, int n, int n2) {
        int n3 = n + 2;
        int n4 = (n + 2) * 9 + n + n2;
        int n5 = n + n2;
        int n6 = n3 + n4 + n5;
        int n7 = 6;
        int n8 = 14;
        int n9 = 4;
        int n10 = n7 * n8 + n9 * 2;
        int n11 = n10 + 6;
        n11 += n + 4;
        n11 += n + 2;
        n11 += n + 14;
        n11 += n + 2;
        int n12 = bl ? n6 + 12 + n11 : Math.max(n6, n11 += n + n2);
        return Math.max(12, (this.field_22790 - n12) / 2);
    }

    private void reflowWithScroll(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int n10;
        int n11;
        int n12;
        if (this.compactLayout) {
            n12 = (n3 - n7 * 3) / 4;
            this.place((class_339)this.scopeButton, n2, n6, n12);
            this.place((class_339)this.handButton, n2 + n12 + n7, n6, n12);
            this.place((class_339)this.targetButton, n2 + (n12 + n7) * 2, n6, n12);
            this.place((class_339)this.kindButton, n2 + (n12 + n7) * 3, n6, n3 - (n12 + n7) * 3);
        } else {
            n12 = Math.min(120, Math.max(96, n3 / 4));
            n11 = Math.min(95, Math.max(80, n3 / 5));
            n10 = Math.min(110, Math.max(92, n3 / 6));
            n9 = n3 - n12 - n11 - n10 - n7 * 3;
            this.place((class_339)this.scopeButton, n2, n6, n12);
            this.place((class_339)this.handButton, n2 + n12 + n7, n6, n11);
            this.place((class_339)this.targetButton, n2 + n12 + n7 + n11 + n7, n6, n10);
            this.place((class_339)this.kindButton, n2 + n12 + n7 + n11 + n7 + n10 + n7, n6, n9);
        }
        n12 = n6 + n8 + 2;
        this.place((class_339)this.offX, n2, n12, n3);
        this.place((class_339)this.offY, n2, n12 += n8 + 2, n3);
        this.place((class_339)this.offZ, n2, n12 += n8 + 2, n3);
        n12 += n8 + 2;
        if (!this.shouldHideArmGlobalRotationControls()) {
            this.place((class_339)this.rotX, n2, n12, n3);
            this.place((class_339)this.rotY, n2, n12 += n8 + 2, n3);
            this.place((class_339)this.rotZ, n2, n12 += n8 + 2, n3);
            n12 += n8 + 2;
        }
        this.place((class_339)this.scale, n2, n12, n3);
        this.place((class_339)this.pivotX, n2, n12 += n8 + 2, n3);
        this.place((class_339)this.pivotY, n2, n12 += n8 + 2, n3);
        this.place((class_339)this.pivotZ, n2, n12 += n8 + 2, n3);
        n11 = this.compactLayout ? (n12 += n8 + n7) + n8 + n7 + 12 : n6;
        n10 = 6;
        n9 = 14;
        int n13 = 4;
        int n14 = n10 * n9 + n13 * 2;
        this.profileListX = n4;
        this.profileListY = n11 - this.scrollOffset;
        this.profileListW = n5;
        this.profileListH = n14;
        this.layoutProfileRowButtons();
        this.place((class_339)this.profileNameBox, n4, n11 += n14 + 6, n5);
        int n15 = (n5 - n7) / 2;
        this.place((class_339)this.saveProfileButton, n4, n11 += n8 + 4, n15);
        this.place((class_339)this.loadProfileButton, n4 + n15 + n7, n11, n5 - n15 - n7);
        this.place((class_339)this.deleteProfileButton, n4, n11 += n8 + 2, n5);
        int n16 = (n11 += n8 + 14) + n8 + 2;
        int n17 = Math.max(n11, n16 - (n8 + 8));
        this.place((class_339)this.importProfileButton, n4, n17, n5);
        this.place((class_339)this.doneButton, n4, n16, n5);
        int n18 = Math.max(n12, n16);
        int n19 = (n3 - n7 * 3) / 4;
        int n20 = n3 - n19 * 3 - n7 * 3;
        this.place((class_339)this.resetButton, n2, n18, n19);
        this.place((class_339)this.resetCurrentButton, n2 + n19 + n7, n18, n19);
        this.place((class_339)this.mirrorButton, n2 + (n19 + n7) * 2, n18, n19);
        this.place((class_339)this.reloadTuningButton, n2 + (n19 + n7) * 3, n18, n20);
        int n21 = n18 + n8 + n7;
        this.profilesTitleX = n4;
        this.profilesTitleY = this.profileTitleY(n6, this.compactLayout, n21);
        if (this.kindButton != null) {
            this.kindButton.method_25355(this.kindButtonLabel());
        }
        this.recalcDeleteConfirmLayout();
    }

    private int profileTitleY(int n, boolean bl, int n2) {
        int n3 = bl ? n2 + 12 : n;
        return n3 - 12 - this.scrollOffset;
    }

    private void renderProfileList(class_332 class_3322) {
        if (this.profileListW > 0 && this.profileListH > 0) {
            int n = this.profileListX;
            int n2 = this.profileListY;
            int n3 = this.profileListX + this.profileListW;
            int n4 = this.profileListY + this.profileListH;
            class_3322.method_25294(n, n2, n3, n4, 1141641495);
            if (this.profiles.isEmpty()) {
                class_3322.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"punchy.tuning.selection.none_item"), n + 6, n2 + 6, 0xA0A0A0);
            }
        }
    }

    private void renderMainScrollbar(class_332 class_3322) {
        if (this.maxScroll > 0) {
            int n = 4;
            class_3322.method_25294(this.mainScrollX, this.mainScrollTop, this.mainScrollX + n, this.mainScrollBottom, -1442840576);
            int n2 = Math.max(1, this.mainScrollBottom - this.mainScrollTop);
            int n3 = Math.max(1, this.field_22790 - 16);
            int n4 = n3 + this.maxScroll;
            int n5 = Math.max(10, Math.round((float)n2 * ((float)n3 / (float)n4)));
            int n6 = Math.max(0, n2 - n5);
            int n7 = this.maxScroll == 0 ? 0 : Math.round((float)n6 * ((float)this.scrollOffset / (float)this.maxScroll));
            int n8 = this.mainScrollTop + n7;
            class_3322.method_25294(this.mainScrollX, n8, this.mainScrollX + n, Math.min(this.mainScrollBottom, n8 + n5), -1);
        }
    }

    private void renderProfileScrollbar(class_332 class_3322) {
        int n = this.maxProfileScroll();
        if (n > 0 && this.profileListH > 0) {
            int n2 = 4;
            class_3322.method_25294(this.profileScrollX, this.profileScrollTop, this.profileScrollX + n2, this.profileScrollBottom, -1442840576);
            int n3 = Math.max(1, this.profileScrollBottom - this.profileScrollTop);
            int n4 = Math.max(1, this.visibleProfileRows());
            int n5 = Math.max(n4, this.profiles.size());
            int n6 = Math.max(10, Math.round((float)n3 * ((float)n4 / (float)n5)));
            int n7 = Math.max(0, n3 - n6);
            int n8 = n == 0 ? 0 : Math.round((float)n7 * ((float)this.profileListScroll / (float)n));
            int n9 = this.profileScrollTop + n8;
            class_3322.method_25294(this.profileScrollX, n9, this.profileScrollX + n2, Math.min(this.profileScrollBottom, n9 + n6), -1);
        }
    }

    private int visibleProfileRows() {
        if (this.profileListH <= 0) {
            return 1;
        }
        int n = (this.profileListH - 8) / 14;
        return Math.max(1, n);
    }

    private boolean isMouseOverProfileList(double d, double d2) {
        return d >= (double)this.profileListX && d < (double)(this.profileListX + this.profileListW) && d2 >= (double)this.profileListY && d2 < (double)(this.profileListY + this.profileListH);
    }

    private boolean isMouseOverMainScrollbar(double d, double d2) {
        return d >= (double)this.mainScrollX && d < (double)(this.mainScrollX + 4) && d2 >= (double)this.mainScrollTop && d2 < (double)this.mainScrollBottom;
    }

    private boolean isMouseOverProfileScrollbar(double d, double d2) {
        return d >= (double)this.profileScrollX && d < (double)(this.profileScrollX + 4) && d2 >= (double)this.profileScrollTop && d2 < (double)this.profileScrollBottom;
    }

    private boolean trySelectProfileFromList(double d, double d2) {
        if (this.isMouseOverProfileList(d, d2) && !this.profiles.isEmpty()) {
            int n = (int)d2 - (this.profileListY + 4);
            int n2 = n / 14;
            if (n2 < 0) {
                return false;
            }
            int n3 = this.profileListScroll + n2;
            if (n3 >= 0 && n3 < this.profiles.size()) {
                this.selectedProfile = n3;
                String string = this.selectedProfileName();
                if (string != null) {
                    this.profileNameBox.method_1852(string);
                }
                this.updateProfileRowButtons();
                return true;
            }
            return false;
        }
        return false;
    }

    private void ensureSelectedVisibleInProfileList() {
        if (this.selectedProfile >= 0) {
            int n = this.visibleProfileRows();
            if (this.selectedProfile < this.profileListScroll) {
                this.profileListScroll = this.selectedProfile;
            } else if (this.selectedProfile >= this.profileListScroll + n) {
                this.profileListScroll = this.selectedProfile - n + 1;
            }
            int n2 = Math.max(0, this.profiles.size() - n);
            this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)n2);
        }
    }

    private void updateMainScrollbarRects() {
        this.mainScrollX = Math.max(6, this.containerRight + 6);
        if (this.mainScrollX > this.field_22789 - 8) {
            this.mainScrollX = this.field_22789 - 8;
        }
        this.mainScrollTop = 18;
        this.mainScrollBottom = Math.max(this.mainScrollTop + 12, this.field_22790 - 18);
    }

    private void updateProfileScrollbarRects() {
        this.profileScrollX = Math.max(this.profileListX + 2, this.profileListX + this.profileListW - 6);
        this.profileScrollTop = this.profileListY + 2;
        this.profileScrollBottom = this.profileListY + Math.max(6, this.profileListH - 2);
    }

    private void updateMainScrollFromMouse(double d) {
        int n = Math.max(1, this.mainScrollBottom - this.mainScrollTop);
        int n2 = class_3532.method_15340((int)((int)Math.round(d) - this.mainScrollTop), (int)0, (int)n);
        int n3 = (int)Math.round((double)n2 / (double)n * (double)this.maxScroll);
        this.scrollOffset = class_3532.method_15340((int)n3, (int)0, (int)this.maxScroll);
        this.layoutWidgets();
    }

    private void updateProfileScrollFromMouse(double d) {
        int n = this.maxProfileScroll();
        int n2 = Math.max(1, this.profileScrollBottom - this.profileScrollTop);
        int n3 = class_3532.method_15340((int)((int)Math.round(d) - this.profileScrollTop), (int)0, (int)n2);
        int n4 = (int)Math.round((double)n3 / (double)n2 * (double)n);
        this.profileListScroll = class_3532.method_15340((int)n4, (int)0, (int)n);
        this.updateProfileRowButtons();
    }

    private int maxProfileScroll() {
        return Math.max(0, this.profiles.size() - this.visibleProfileRows());
    }

    private void updateWorkbenchPanelRects(int n, int n2, int n3, int n4) {
        int n5 = Math.min(n, n3) - 10;
        int n6 = Math.max(n + n2, n3 + n4) + 10;
        int n7 = Math.min(this.scopeButton.method_46427(), this.profileListY) - 12;
        int n8 = Math.max(this.reloadTuningButton.method_46427() + this.reloadTuningButton.method_25364(), this.doneButton.method_46427() + this.doneButton.method_25364()) + 10;
        this.mainPanelLeft = class_3532.method_15340((int)n5, (int)4, (int)(this.field_22789 - 4));
        this.mainPanelRight = class_3532.method_15340((int)n6, (int)4, (int)(this.field_22789 - 4));
        this.mainPanelTop = class_3532.method_15340((int)n7, (int)4, (int)(this.field_22790 - 4));
        this.mainPanelBottom = class_3532.method_15340((int)n8, (int)4, (int)(this.field_22790 - 4));
        if (this.mainPanelBottom <= this.mainPanelTop + 2) {
            this.mainPanelBottom = Math.min(this.field_22790 - 4, this.mainPanelTop + 3);
        }
        this.leftPanelLeft = class_3532.method_15340((int)(n - 4), (int)4, (int)(this.field_22789 - 4));
        this.leftPanelRight = class_3532.method_15340((int)(n + n2 + 4), (int)4, (int)(this.field_22789 - 4));
        this.leftPanelTop = class_3532.method_15340((int)(this.scopeButton.method_46427() - 6), (int)4, (int)(this.field_22790 - 4));
        this.leftPanelBottom = class_3532.method_15340((int)(this.reloadTuningButton.method_46427() + this.reloadTuningButton.method_25364() + 6), (int)4, (int)(this.field_22790 - 4));
        if (this.leftPanelBottom <= this.leftPanelTop + 2) {
            this.leftPanelBottom = Math.min(this.field_22790 - 4, this.leftPanelTop + 3);
        }
        this.rightPanelLeft = class_3532.method_15340((int)(n3 - 4), (int)4, (int)(this.field_22789 - 4));
        this.rightPanelRight = class_3532.method_15340((int)(n3 + n4 + 4), (int)4, (int)(this.field_22789 - 4));
        this.rightPanelTop = class_3532.method_15340((int)(this.profileListY - 6), (int)4, (int)(this.field_22790 - 4));
        this.rightPanelBottom = class_3532.method_15340((int)(this.doneButton.method_46427() + this.doneButton.method_25364() + 6), (int)4, (int)(this.field_22790 - 4));
        if (this.rightPanelBottom <= this.rightPanelTop + 2) {
            this.rightPanelBottom = Math.min(this.field_22790 - 4, this.rightPanelTop + 3);
        }
        this.recalcDeleteConfirmLayout();
    }

    private void openDeleteProfileConfirm() {
        String string = this.selectedProfileName();
        if (string != null && !string.isBlank()) {
            this.pendingDeleteProfileName = string;
            this.deleteProfileConfirmOpen = true;
            this.activeSlider = null;
            this.applyFocusVisibility();
            this.recalcDeleteConfirmLayout();
        } else {
            this.statusMessage = class_2561.method_43471((String)"punchy.tuning.ui.status.profile_delete_failed").getString();
        }
    }

    private void cancelDeleteProfileConfirm() {
        this.deleteProfileConfirmOpen = false;
        this.pendingDeleteProfileName = null;
    }

    private void confirmDeleteProfile() {
        boolean bl;
        String string = this.pendingDeleteProfileName;
        boolean bl2 = bl = string != null && !string.isBlank() && TuningProfileManager.deleteProfile(string);
        if (bl && (bl = TuningProfileManager.unloadActiveProfileToDefault())) {
            this.hasUnsavedProfileChanges = false;
            this.loadFromCurrentProfile();
        }
        this.statusMessage = bl ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_deleted", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_delete_failed").getString();
        this.cancelDeleteProfileConfirm();
        this.refreshProfiles(true);
    }

    private void recalcDeleteConfirmLayout() {
        int n = Math.min(380, Math.max(260, this.mainPanelRight - this.mainPanelLeft - 48));
        int n2 = 112;
        this.deleteModalLeft = this.mainPanelLeft + (this.mainPanelRight - this.mainPanelLeft - n) / 2;
        this.deleteModalTop = this.mainPanelTop + (this.mainPanelBottom - this.mainPanelTop - n2) / 2;
        this.deleteModalRight = this.deleteModalLeft + n;
        this.deleteModalBottom = this.deleteModalTop + n2;
        int n3 = 20;
        int n4 = 8;
        int n5 = (n - 32 - n4) / 2;
        this.deleteCancelButtonLeft = this.deleteModalLeft + 16;
        this.deleteCancelButtonTop = this.deleteModalBottom - 30;
        this.deleteCancelButtonRight = this.deleteCancelButtonLeft + n5;
        this.deleteCancelButtonBottom = this.deleteCancelButtonTop + n3;
        this.deleteConfirmButtonLeft = this.deleteCancelButtonRight + n4;
        this.deleteConfirmButtonTop = this.deleteCancelButtonTop;
        this.deleteConfirmButtonRight = this.deleteConfirmButtonLeft + n5;
        this.deleteConfirmButtonBottom = this.deleteConfirmButtonTop + n3;
    }

    private void drawDeleteProfileConfirmOverlay(class_332 class_3322, int n, int n2) {
        if (this.deleteProfileConfirmOpen) {
            class_3322.method_51448().method_22903();
            class_3322.method_51448().method_46416(0.0f, 0.0f, 500.0f);
            class_3322.method_25294(this.mainPanelLeft, this.mainPanelTop, this.mainPanelRight, this.mainPanelBottom, -1610612736);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalRight, this.deleteModalBottom, -15657446);
            int n3 = -11641751;
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalRight, this.deleteModalTop + 1, n3);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalBottom - 1, this.deleteModalRight, this.deleteModalBottom, n3);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalLeft + 1, this.deleteModalBottom, n3);
            class_3322.method_25294(this.deleteModalRight - 1, this.deleteModalTop, this.deleteModalRight, this.deleteModalBottom, n3);
            class_2561 class_25612 = TuningWorkbenchScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.title", "Voce realmente quer fazer isso?", new Object[0]);
            String string = this.pendingDeleteProfileName == null ? "-" : this.pendingDeleteProfileName;
            class_2561 class_25613 = TuningWorkbenchScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.message", "Delete profile \"" + string + "\"?", string);
            String string2 = class_25612.getString();
            String string3 = class_25613.getString();
            int n4 = this.deleteModalLeft + (this.deleteModalRight - this.deleteModalLeft - this.field_22793.method_1727(string2)) / 2;
            int n5 = this.deleteModalLeft + (this.deleteModalRight - this.deleteModalLeft - this.field_22793.method_1727(string3)) / 2;
            class_3322.method_25303(this.field_22793, string2, n4, this.deleteModalTop + 12, -1);
            class_3322.method_25303(this.field_22793, string3, n5, this.deleteModalTop + 34, -3747624);
            boolean bl = TuningWorkbenchScreen.isMouseInside(n, n2, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom);
            boolean bl2 = TuningWorkbenchScreen.isMouseInside(n, n2, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom);
            this.drawModalButton(class_3322, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom, bl, false, TuningWorkbenchScreen.nonBlankTranslatable("gui.cancel", "Cancel", new Object[0]));
            this.drawModalButton(class_3322, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom, bl2, true, TuningWorkbenchScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.confirm", "Delete", new Object[0]));
            class_3322.method_51448().method_22909();
        }
    }

    private void drawModalButton(class_332 class_3322, int n, int n2, int n3, int n4, boolean bl, boolean bl2, class_2561 class_25612) {
        int n5 = bl2 ? (bl ? -7459282 : -9690590) : (bl ? -12958126 : -13682107);
        int n6 = bl2 ? -3048067 : -7429710;
        class_3322.method_25294(n, n2, n3, n4, n5);
        class_3322.method_25294(n, n2, n3, n2 + 1, n6);
        class_3322.method_25294(n, n4 - 1, n3, n4, n6);
        class_3322.method_25294(n, n2, n + 1, n4, n6);
        class_3322.method_25294(n3 - 1, n2, n3, n4, n6);
        String string = class_25612.getString();
        int n7 = n + (n3 - n - this.field_22793.method_1727(string)) / 2;
        int n8 = n2 + (n4 - n2 - 8) / 2;
        class_3322.method_25303(this.field_22793, string, n7, n8, -1);
    }

    private static boolean isMouseInside(double d, double d2, int n, int n2, int n3, int n4) {
        return d >= (double)n && d <= (double)n3 && d2 >= (double)n2 && d2 <= (double)n4;
    }

    private static class_2561 nonBlankTranslatable(String string, String string2, Object ... objectArray) {
        class_5250 class_52502 = class_2561.method_43469((String)string, (Object[])objectArray);
        String string3 = class_52502.getString();
        return string3 != null && !string3.isBlank() && !string3.equals(string) ? class_52502 : class_2561.method_43470((String)string2);
    }

    private void layoutProfileRowButtons() {
        int n = 14;
        int n2 = n - 1;
        int n3 = this.profileListY + 4;
        int n4 = Math.max(10, this.profileListW);
        for (int i = 0; i < this.profileRowButtons.size(); ++i) {
            class_4185 class_41852 = this.profileRowButtons.get(i);
            class_41852.method_46421(this.profileListX);
            class_41852.method_46419(n3 + i * n);
            class_41852.method_25358(n4);
            class_41852.method_53533(n2);
        }
        this.updateProfileRowButtons();
    }

    private void updateProfileRowButtons() {
        int n = this.visibleProfileRows();
        int n2 = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)Math.max(0, this.profiles.size() - n));
        for (int i = 0; i < this.profileRowButtons.size(); ++i) {
            class_4185 class_41852 = this.profileRowButtons.get(i);
            int n3 = n2 + i;
            if (n3 < this.profiles.size() && i < n) {
                String string = this.profiles.get(n3);
                boolean bl = n3 == this.selectedProfile;
                class_41852.field_22764 = true;
                class_41852.field_22763 = true;
                class_41852.method_25355((class_2561)class_2561.method_43470((String)((bl ? "> " : "") + string)));
                continue;
            }
            class_41852.field_22764 = false;
            class_41852.field_22763 = false;
            class_41852.method_25355((class_2561)class_2561.method_43473());
        }
    }

    private void selectProfileAtVisibleRow(int n) {
        int n2;
        int n3 = this.visibleProfileRows();
        if (n >= 0 && n < n3 && (n2 = this.profileListScroll + n) >= 0 && n2 < this.profiles.size()) {
            this.selectedProfile = n2;
            String string = this.selectedProfileName();
            if (string != null && this.profileNameBox != null) {
                this.profileNameBox.method_1852(string);
            }
            this.ensureSelectedVisibleInProfileList();
            this.updateProfileRowButtons();
        }
    }

    private boolean openProfilesFolder() {
        try {
            Path path = TuningProfileManager.profilesDirectoryPath();
            class_156.method_668().method_60932(path);
            return true;
        }
        catch (Exception exception) {
            try {
                Path path = TuningProfileManager.profilesDirectoryPath();
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(path.toFile());
                    return true;
                }
            }
            catch (Exception exception2) {
            }
            return false;
        }
    }

    private void place(class_339 class_3392, int n, int n2, int n3) {
        class_3392.method_46421(n);
        class_3392.method_46419(n2 - this.scrollOffset);
        class_3392.method_25358(n3);
    }

    private void applyFocusVisibility() {
        boolean bl;
        boolean bl2 = this.shouldHideArmGlobalRotationControls();
        boolean bl3 = bl = this.isArmEditor() || this.isFishingLineEditor();
        if (this.activeSlider != null) {
            for (class_339 class_3392 : this.uiWidgets) {
                boolean bl4;
                class_3392.field_22764 = bl4 = class_3392 == this.activeSlider;
                class_3392.field_22763 = bl4;
            }
        } else {
            FishingLineProxyTuning.Profile profile;
            for (class_339 widget : this.uiWidgets) {
                widget.field_22764 = true;
                widget.field_22763 = true;
            }
            ToolTuning.Profile profile2 = this.isItemEditor() ? this.getCurrentProfile() : null;
            ArmToolTuning.Profile armProfile = this.isArmEditor() ? this.getCurrentArmProfile() : null;
            FishingLineProxyTuning.Profile profile3 = profile = this.isFishingLineEditor() ? this.getCurrentFishingLineProfile() : null;
            boolean bl5 = this.isArmEditor() ? armProfile != null : (this.isFishingLineEditor() ? profile != null : profile2 != null);
            for (FloatSlider floatSlider : this.sliders) {
                floatSlider.field_22763 = bl5;
                if (bl && floatSlider.isPivotSlider()) {
                    floatSlider.field_22764 = false;
                    floatSlider.field_22763 = false;
                    continue;
                }
                if (bl2 && floatSlider.isRotationSlider()) {
                    floatSlider.field_22764 = false;
                    floatSlider.field_22763 = false;
                    continue;
                }
                floatSlider.field_22764 = true;
            }
            if (this.kindButton != null) {
                this.kindButton.field_22763 = false;
            }
        }
    }

    private <T extends class_339 & class_4068 & class_6379> T addUiWidget(T widget) {
        T addedWidget = this.method_37063(widget);
        this.uiWidgets.add(addedWidget);
        return addedWidget;
    }

    private void normalizeScopeForTarget() {
        if (!this.isArmEditor()) {
            this.scope = Scope.SPECIFIC;
        }
        if (this.isFishingLineEditor() && !this.isHeldFishingRod()) {
            this.editorTarget = EditorTarget.ITEM;
        }
    }

    private void cycleEditorTarget() {
        this.editorTarget = this.editorTarget == EditorTarget.ITEM ? EditorTarget.ARM : (this.editorTarget == EditorTarget.ARM ? (this.isHeldFishingRod() ? EditorTarget.FISHING_LINE : EditorTarget.ITEM) : EditorTarget.ITEM);
    }

    private boolean isItemEditor() {
        return this.editorTarget == EditorTarget.ITEM;
    }

    private boolean isArmEditor() {
        return this.editorTarget == EditorTarget.ARM;
    }

    private boolean isFishingLineEditor() {
        return this.editorTarget == EditorTarget.FISHING_LINE;
    }

    private FishingLineProxyTuning.Profile getCurrentFishingLineProfile() {
        return !this.isHeldFishingRod() ? null : FishingLineProxyTuning.get(FishingLineProxyTuning.Kind.ROD, this.selectedHandTargetsLeftArm());
    }

    private boolean isHeldFishingRod() {
        return this.isFishingRodStack(this.heldItemStack());
    }

    private boolean isFishingRodStack(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            return class_29602 != null && "minecraft".equals(class_29602.method_12836()) && "fishing_rod".equals(class_29602.method_12832());
        }
        return false;
    }

    private static FishingLineProxyTuning.Profile copyFishingLineProfile(FishingLineProxyTuning.Profile profile) {
        return profile == null ? null : new FishingLineProxyTuning.Profile(profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ, profile.scale);
    }

    private boolean isArmSpecificEmptyHandTarget() {
        return this.isArmEditor() && this.scope == Scope.SPECIFIC && this.heldItemId() == null;
    }

    private boolean shouldHideArmGlobalRotationControls() {
        return this.isArmEditor() && this.scope == Scope.GLOBAL;
    }

    private PlayerArmModelLayer.ToolKind currentArmKind() {
        PlayerArmModelLayer.ToolKind toolKind = this.armKindForSelectedItemKind();
        if (toolKind != null) {
            return toolKind;
        }
        class_310 class_3102 = class_310.method_1551();
        class_1799 class_17992 = class_1799.field_8037;
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_17992 = class_3102.field_1724.method_5998(this.leftHand ? class_1268.field_5810 : class_1268.field_5808);
        }
        return ToolKindResolver.resolveArmKind(class_17992, PlayerArmModelLayer.ToolKind.AXE);
    }

    private PlayerArmModelLayer.ToolKind armKindForSelectedItemKind() {
        try {
            return PlayerArmModelLayer.ToolKind.valueOf(this.selectedKind.name());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private ArmToolTuning.Profile getCurrentArmProfile() {
        PlayerArmModelLayer.ToolKind toolKind = this.currentArmKind();
        boolean bl = this.selectedHandTargetsLeftArm();
        return switch (this.scope.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> ArmToolTuning.getGlobal(bl);
            case 1 -> {
                class_2960 heldItemId = this.heldItemId();
                if (heldItemId == null) {
                    yield ArmToolTuning.get(PlayerArmModelLayer.ToolKind.EMPTY_HAND, bl);
                }
                yield ArmToolTuning.getSpecificProfile(heldItemId, toolKind, bl);
            }
        };
    }

    private ArmToolTuning.Profile armEditorBaseProfile() {
        if (!this.isArmEditor()) {
            return new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        boolean bl = this.selectedHandTargetsLeftArm();
        return switch (this.scope.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyTuningConfig.getDefaultGlobalArmBase(bl);
            case 1 -> new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        };
    }

    private boolean selectedHandTargetsLeftArm() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6182;
            return this.leftHand ? !bl : bl;
        }
        return this.leftHand;
    }

    private void applyArmGlobalToAllSpecifics(boolean bl, ArmToolTuning.Profile profile) {
        if (profile != null) {
            Map<class_2960, ArmToolTuning.SpecificSnapshot> map = ArmToolTuning.snapshotSpecifics();
            for (Map.Entry<class_2960, ArmToolTuning.SpecificSnapshot> entry : map.entrySet()) {
                ArmToolTuning.Profile profile2;
                class_2960 class_29602 = entry.getKey();
                ArmToolTuning.SpecificSnapshot specificSnapshot = entry.getValue();
                if (class_29602 == null || specificSnapshot == null) continue;
                ArmToolTuning.Profile profile3 = specificSnapshot.right();
                ArmToolTuning.Profile profile4 = specificSnapshot.left();
                ArmToolTuning.Profile profile5 = profile2 = bl ? profile4 : profile3;
                if (profile2 != null) {
                    profile2.offX = profile.offX;
                    profile2.offY = profile.offY;
                    profile2.offZ = profile.offZ;
                    profile2.rotX = profile.rotX;
                    profile2.rotY = profile.rotY;
                    profile2.rotZ = profile.rotZ;
                    profile2.scale = profile.scale;
                }
                ArmToolTuning.applySpecific(class_29602, specificSnapshot.kind(), profile3, profile4);
            }
        }
    }

    static enum Scope {
        GLOBAL("punchy.tuning.ui.scope.global"),
        SPECIFIC("punchy.tuning.ui.scope.specific");

        final String key;

        private Scope(String string2) {
            this.key = string2;
        }
    }

    static final class FloatSlider
    extends class_357 {
        private final String key;
        private float min;
        private float max;
        private final Consumer<Float> onChanged;
        private boolean draggingRelative;
        private double dragStartMouseX;
        private double dragStartValue;

        FloatSlider(int n, int n2, int n3, int n4, String string, float f, float f2, Consumer<Float> consumer) {
            super(n, n2, n3, n4, (class_2561)class_2561.method_43473(), 0.0);
            this.key = string;
            this.min = f;
            this.max = f2;
            this.onChanged = consumer;
            this.setFloat(0.0f);
        }

        float getFloat() {
            return class_3532.method_16439((float)((float)this.field_22753), (float)this.min, (float)this.max);
        }

        void setFloat(float f) {
            float f2 = class_3532.method_15363((float)f, (float)this.min, (float)this.max);
            this.field_22753 = (f2 - this.min) / (this.max - this.min);
            this.method_25346();
        }

        void setRange(float f, float f2) {
            if (!(f2 <= f)) {
                float f3 = this.getFloat();
                this.min = f;
                this.max = f2;
                this.setFloat(f3);
            }
        }

        public boolean method_25402(double d, double d2, int n) {
            if (this.field_22764 && this.field_22763 && n == 0 && this.method_25405(d, d2)) {
                this.draggingRelative = true;
                this.dragStartMouseX = d;
                this.dragStartValue = this.field_22753;
                this.method_25365(true);
                return true;
            }
            return false;
        }

        public boolean method_25403(double d, double d2, int n, double d3, double d4) {
            if (this.draggingRelative && n == 0) {
                int n2 = Math.max(1, this.method_25368() - 8);
                double d5 = (d - this.dragStartMouseX) / (double)n2;
                this.field_22753 = class_3532.method_15350((double)(this.dragStartValue + d5), (double)0.0, (double)1.0);
                this.method_25346();
                this.method_25344();
                return true;
            }
            return false;
        }

        public boolean method_25406(double d, double d2, int n) {
            if (n == 0 && this.draggingRelative) {
                this.draggingRelative = false;
                return true;
            }
            return false;
        }

        void drawStyled(class_332 class_3322, int n, int n2) {
            class_310 class_3102;
            if (this.field_22764 && (class_3102 = class_310.method_1551()) != null && class_3102.field_1772 != null) {
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
                String string2 = FloatSlider.displayKey(this.key);
                String string3 = String.format(Locale.ROOT, "%.3f", Float.valueOf(this.getFloat()));
                int n12 = n9 + (this.method_25364() - 8) / 2;
                int n13 = n8 + 10;
                int n14 = n10 - 10 - class_3102.field_1772.method_1727(string3);
                int n15 = n14 - 8;
                if (n15 <= (n7 = n13 + class_3102.field_1772.method_1727(string = class_3102.field_1772.method_27523(string2, n6 = Math.max(10, n14 - n13 - 16))) + 10) + 12) {
                    n7 = n8 + 8;
                    n15 = n10 - 8;
                }
                int n16 = Math.max(1, n15 - n7);
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
                    n4 = -7485697;
                    n3 = -1;
                }
                class_3322.method_25294(n8, n9, n10, n11, n5);
                class_3322.method_25294(n8, n9, n10, n9 + 1, n4);
                class_3322.method_25294(n8, n11 - 1, n10, n11, n4);
                class_3322.method_25294(n8, n9, n8 + 1, n11, n4);
                class_3322.method_25294(n10 - 1, n9, n10, n11, n4);
                int n17 = n9 + this.method_25364() / 2 - 2;
                int n18 = n17 + 4;
                class_3322.method_25294(n7, n17, n15, n18, -1441719774);
                int n19 = 8;
                int n20 = Math.max(0, n16 - n19);
                int n21 = n7 + (int)Math.round(this.field_22753 * (double)n20);
                int n22 = n21 + n19;
                int n23 = n21 + n19 / 2;
                class_3322.method_25294(n7, n17, n23, n18, -13869680);
                int n24 = !this.field_22763 ? -11641751 : (!bl2 && !bl ? -9798515 : -7485697);
                class_3322.method_25294(n21, n9 + 2, n22, n11 - 2, n24);
                class_3322.method_25294(n21, n9 + 2, n22, n9 + 3, -4732456);
                class_3322.method_25294(n21, n11 - 3, n22, n11 - 2, -14404542);
                class_3322.method_25303(class_3102.field_1772, string, n13, n12, n3);
                class_3322.method_25303(class_3102.field_1772, string3, n14, n12, n3);
            }
        }

        protected void method_25346() {
            String string = String.format(Locale.ROOT, "%.3f", Float.valueOf(this.getFloat()));
            this.method_25355((class_2561)class_2561.method_43470((String)(this.key + ": " + string)));
        }

        protected void method_25344() {
            if (this.onChanged != null) {
                this.onChanged.accept(Float.valueOf(this.getFloat()));
            }
        }

        boolean isPivotSlider() {
            return "pivotX".equals(this.key) || "pivotY".equals(this.key) || "pivotZ".equals(this.key);
        }

        boolean isRotationSlider() {
            return "rotX".equals(this.key) || "rotY".equals(this.key) || "rotZ".equals(this.key);
        }

        private static String displayKey(String string) {
            if (string != null && !string.isBlank()) {
                return switch (string) {
                    case "offX" -> "Offset X";
                    case "offY" -> "Offset Y";
                    case "offZ" -> "Offset Z";
                    case "rotX" -> "Rotation X";
                    case "rotY" -> "Rotation Y";
                    case "rotZ" -> "Rotation Z";
                    case "pivotX" -> "Pivot X";
                    case "pivotY" -> "Pivot Y";
                    case "pivotZ" -> "Pivot Z";
                    case "scale" -> "Scale";
                    default -> string;
                };
            }
            return "";
        }
    }

    static enum EditorTarget {
        ITEM,
        ARM,
        FISHING_LINE;

    }
}
