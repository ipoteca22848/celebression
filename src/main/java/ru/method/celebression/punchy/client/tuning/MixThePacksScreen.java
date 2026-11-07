package ru.method.celebression.punchy.client.tuning;

import ru.method.celebression.animation.Animation;

import java.awt.Desktop;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_156;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_342;
import net.minecraft.class_3532;
import net.minecraft.class_364;
import net.minecraft.class_410;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.tuning.MixProfileManager;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class MixThePacksScreen
extends class_437 {
    private static final int ROW_HEIGHT = 20;
    private static final int ROW_SPACING = 4;
    private static final int ROW_STEP = 24;
    private static final int SCROLLBAR_WIDTH = 4;
    private static final int SCROLLBAR_GAP = 6;
    private static final int SCROLLBAR_RESERVE = 10;
    private static final int EXPANDED_CHILD_INSET = 10;
    private static final int MAX_PROFILE_ROWS = 6;
    private static final int PROFILE_ROW_HEIGHT = 14;
    private static final int PANEL_GAP = 12;
    private final class_437 parent;
    private final Set<String> expandedSlots = new HashSet<String>();
    private final List<RowEntry> rows = new ArrayList<RowEntry>();
    private final List<Integer> rowBaseY = new ArrayList<Integer>();
    private final List<class_339> rowWidgets = new ArrayList<class_339>();
    private final List<class_4185> profileRowButtons = new ArrayList<class_4185>();
    private List<String> profiles = List.of();
    private class_4185 doneButton;
    private class_4185 resetOrderButton;
    private class_342 filterBox;
    private class_342 profileNameBox;
    private class_4185 saveProfileButton;
    private class_4185 loadProfileButton;
    private class_4185 deleteProfileButton;
    private class_4185 openProfilesButton;
    private int selectedProfile = -1;
    private int profileListScroll = 0;
    private String statusMessage = "";
    private int scrollOffset = 0;
    private int maxScroll = 0;
    private int listLeft = 0;
    private int listRight = 0;
    private int listTop = 0;
    private int listBottom = 0;
    private int containerLeft = 0;
    private int containerTop = 0;
    private int containerRight = 0;
    private int containerBottom = 0;
    private int selectorWidth = 0;
    private int rowWidth = 0;
    private int profilePanelX = 0;
    private int profilePanelW = 0;
    private int listTitleX = 0;
    private int listTitleY = 0;
    private int profilePanelTop = 0;
    private int profilesTitleX = 0;
    private int profilesTitleY = 0;
    private int profileListX = 0;
    private int profileListY = 0;
    private int profileListW = 0;
    private int profileListH = 0;
    private int profileVisibleRows = 6;
    private int profilePanelScroll = 0;
    private int profilePanelMaxScroll = 0;
    private int footerRowY = 0;
    private boolean compactLayout = false;
    private String filterText = "";
    private boolean hasUnsavedProfileChanges = false;
    private PunchyConfig.Data mixSessionBaseline = null;
    private boolean initializedOnce = false;
    private final Map<String, String> stagedPackSelections = new LinkedHashMap<String, String>();
    private final Map<String, String> stagedPackTypeSelections = new LinkedHashMap<String, String>();
    private final Map<String, String> previewPackSelections = new LinkedHashMap<String, String>();
    private final Map<String, String> previewPackTypeSelections = new LinkedHashMap<String, String>();
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

    public MixThePacksScreen(class_437 class_4372) {
        super((class_2561)class_2561.method_43471((String)"punchy.mixpacks.title"));
        this.parent = class_4372;
    }

    protected void method_25426() {
        boolean bl;
        this.method_37067();
        this.rows.clear();
        this.rowBaseY.clear();
        this.rowWidgets.clear();
        this.profileRowButtons.clear();
        this.scrollOffset = 0;
        boolean bl2 = bl = this.initializedOnce && this.hasUnsavedProfileChanges;
        if (!bl) {
            this.mixSessionBaseline = PunchyConfig.snapshot();
            this.hasUnsavedProfileChanges = false;
            this.initializeStagedSelectionsFromCurrent();
        }
        this.initializedOnce = true;
        int n = 24;
        int n2 = 14;
        int n3 = Math.max(420, this.field_22789 - n * 2);
        int n4 = Math.max(300, this.field_22790 - 24);
        int n5 = Math.min(760, n3);
        int n6 = Math.min(266, n4);
        n5 = Math.min(n5, Math.max(1, this.field_22789 - 8));
        n6 = Math.min(n6, Math.max(1, this.field_22790 - 8));
        this.containerLeft = (this.field_22789 - n5) / 2;
        this.containerTop = (this.field_22790 - n6) / 2;
        this.containerRight = this.containerLeft + n5;
        this.containerBottom = this.containerTop + n6;
        this.footerRowY = this.containerBottom - n2 - 20;
        this.profilePanelW = class_3532.method_15340((int)(n5 / 3), (int)220, (int)300);
        int n7 = this.containerTop + 12;
        int n8 = this.footerRowY - 8;
        int n9 = 210;
        this.listLeft = this.containerLeft + n2;
        int n10 = n5 - this.profilePanelW - 12;
        boolean bl3 = this.compactLayout = n10 < 300 || n8 - n7 < n9;
        if (this.compactLayout) {
            this.profilePanelX = this.containerLeft + n2;
            this.profilePanelW = n5 - n2 * 2;
            this.listRight = this.containerRight - n2;
            this.listTop = n7;
            this.profilePanelTop = Math.max(n7 + 120, n8 - n9);
            this.listBottom = this.profilePanelTop - 8;
            if (this.listBottom < this.listTop + 80) {
                this.listBottom = this.listTop + 80;
                this.profilePanelTop = this.listBottom + 8;
            }
        } else {
            this.profilePanelX = this.containerRight - n2 - this.profilePanelW;
            this.profilePanelTop = n7;
            this.listRight = this.profilePanelX - 12;
            this.listTop = n7;
            this.listBottom = n8;
        }
        this.selectorWidth = Math.max(92, Math.min(150, Math.max(96, (this.listRight - this.listLeft) / 3)));
        this.rowWidth = Math.max(100, this.listRight - this.listLeft - this.selectorWidth - 6 - 10);
        this.listTitleX = this.listLeft;
        this.listTitleY = this.listTop - 14;
        this.filterBox = (class_342)this.method_37063(new class_342(this.field_22793, this.listLeft, this.listTop, this.rowWidth + 6 + this.selectorWidth, 20, (class_2561)class_2561.method_43471((String)"punchy.mixpacks.search")));
        this.filterBox.method_1880(80);
        this.filterBox.method_1852(this.filterText == null ? "" : this.filterText);
        this.filterBox.method_47404((class_2561)class_2561.method_43471((String)"punchy.mixpacks.search"));
        this.filterBox.method_1863(string -> {
            this.filterText = string == null ? "" : string;
            this.rebuildRows();
        });
        this.listTop += 26;
        this.resetOrderButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.mixpacks.reset_resourcepack_order"), class_41852 -> this.applyResetToResourcePackOrderAndUnloadProfile()).method_46433(0, 0).method_46437(220, 20).method_46431());
        this.doneButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.done"), class_41852 -> this.method_25419()).method_46433(0, 0).method_46437(this.profilePanelW, 20).method_46431());
        this.initProfilesPanel();
        if (this.compactLayout) {
            int n11 = (this.profilePanelW - 4) / 2;
            this.resetOrderButton.method_46421(this.profilePanelX);
            this.resetOrderButton.method_46419(this.footerRowY);
            this.resetOrderButton.method_25358(n11);
            this.doneButton.method_46421(this.profilePanelX + n11 + 4);
            this.doneButton.method_46419(this.footerRowY);
            this.doneButton.method_25358(this.profilePanelW - n11 - 4);
        } else {
            this.resetOrderButton.method_46421(this.listLeft);
            this.resetOrderButton.method_46419(this.footerRowY);
            this.resetOrderButton.method_25358(this.listRight - this.listLeft);
            this.doneButton.method_46421(this.profilePanelX);
            this.doneButton.method_46419(this.footerRowY);
            this.doneButton.method_25358(this.profilePanelW);
        }
        this.recalcProfilePanelScroll();
        this.refreshProfiles(true);
        this.rebuildRows();
        this.recalcDeleteConfirmLayout();
    }

    private void initProfilesPanel() {
        this.profilesTitleX = this.profilePanelX;
        this.profilesTitleY = this.profilePanelTop;
        this.profileListX = this.profilePanelX;
        this.profileListY = this.profilePanelTop;
        this.profileListW = this.profilePanelW;
        int n = this.footerRowY - 24;
        int n2 = n - 8;
        int n3 = (n2 - this.profilePanelTop - 66) / 14;
        this.profileVisibleRows = class_3532.method_15340((int)n3, (int)2, (int)6);
        this.profileListH = this.profileVisibleRows * 14;
        this.profileNameBox = (class_342)this.method_37063(new class_342(this.field_22793, this.profilePanelX, this.profileListY + this.profileListH + 6, this.profilePanelW, 20, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.profile_name")));
        this.profileNameBox.method_1880(64);
        int n4 = (this.profilePanelW - 4) / 2;
        this.saveProfileButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.save_profile"), class_41852 -> {
            String string = this.profileNameBox.method_1882();
            if (string == null || string.isBlank()) {
                string = "mix_profile";
                this.profileNameBox.method_1852(string);
            }
            PunchyConfig.Data data = PunchyConfig.snapshot();
            PunchyConfig.replaceAnimationPackSelections(this.stagedPackSelections, this.stagedPackTypeSelections, data.mixSpecificForceOverride);
            boolean bl = MixProfileManager.saveCurrentAs(string);
            if (!bl) {
                PunchyConfig.replaceAnimationPackSelections(data.animationPackSelections, data.animationPackTypeSelections, data.mixSpecificForceOverride);
            } else {
                PunchyAnimationManager.requestTuningReload();
            }
            String string2 = this.statusMessage = bl ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_saved", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_save_failed").getString();
            if (bl) {
                this.hasUnsavedProfileChanges = false;
                this.mixSessionBaseline = PunchyConfig.snapshot();
                this.initializeStagedSelectionsFromCurrent();
            }
            this.refreshProfiles(true);
            this.rebuildRows();
        }).method_46433(this.profilePanelX, this.profileNameBox.method_46427() + 24).method_46437(n4, 20).method_46431());
        this.loadProfileButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.load_profile"), class_41852 -> {
            String string = this.selectedProfileName();
            boolean bl = string != null && MixProfileManager.loadProfile(string);
            String string2 = this.statusMessage = bl ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_loaded", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_load_failed").getString();
            if (bl) {
                this.hasUnsavedProfileChanges = false;
                this.mixSessionBaseline = PunchyConfig.snapshot();
                this.initializeStagedSelectionsFromCurrent();
            }
            this.refreshProfiles(false);
            this.rebuildRows();
        }).method_46433(this.profilePanelX + n4 + 4, this.profileNameBox.method_46427() + 24).method_46437(this.profilePanelW - n4 - 4, 20).method_46431());
        this.deleteProfileButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.delete_profile"), class_41852 -> this.openDeleteProfileConfirm()).method_46433(this.profilePanelX, this.profileNameBox.method_46427() + 46).method_46437(this.profilePanelW, 20).method_46431());
        this.openProfilesButton = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43471((String)"punchy.tuning.ui.open_profiles_folder"), class_41852 -> {
            boolean bl = this.openProfilesFolder();
            this.statusMessage = bl ? class_2561.method_43471((String)"punchy.tuning.ui.status.profiles_folder_opened").getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profiles_folder_open_failed").getString();
        }).method_46433(this.profilePanelX, n).method_46437(this.profilePanelW, 20).method_46431());
        for (int i = 0; i < this.profileVisibleRows; ++i) {
            int n5 = i;
            class_4185 class_41853 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43473(), class_41852 -> this.selectProfileAtVisibleRow(n5)).method_46433(this.profileListX, this.profileListY + i * 14).method_46437(this.profileListW, 13).method_46431());
            this.profileRowButtons.add(class_41853);
        }
    }

    public boolean method_25421() {
        return false;
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        class_3322.method_25294(0, 0, this.field_22789, this.field_22790, -1610612736);
        class_3322.method_25294(this.containerLeft, this.containerTop, this.containerRight, this.containerBottom, -15657446);
        class_3322.method_25294(this.containerLeft, this.containerTop, this.containerRight, this.containerTop + 1, -14011848);
        class_3322.method_25294(this.containerLeft, this.containerBottom - 1, this.containerRight, this.containerBottom, -14011848);
        class_3322.method_25294(this.containerLeft, this.containerTop, this.containerLeft + 1, this.containerBottom, -14011848);
        class_3322.method_25294(this.containerRight - 1, this.containerTop, this.containerRight, this.containerBottom, -14011848);
        int n3 = 857944122;
        int n4 = 1430145621;
        int n5 = (this.filterBox != null ? this.filterBox.method_46427() : this.listTop) - 6;
        int n6 = this.listLeft - 4;
        int n7 = this.listRight + 4;
        int n8 = this.resetOrderButton.method_46427() + this.resetOrderButton.method_25364() + 6;
        class_3322.method_25294(n6, n5, n7, n8, n3);
        class_3322.method_25294(n6, n5, n7, n5 + 1, n4);
        class_3322.method_25294(n6, n8 - 1, n7, n8, n4);
        class_3322.method_25294(n6, n5, n6 + 1, n8, n4);
        class_3322.method_25294(n7 - 1, n5, n7, n8, n4);
        int n9 = this.profilePanelX - 4;
        int n10 = this.profilePanelX + this.profilePanelW + 4;
        int n11 = this.doneButton.method_46427() + this.doneButton.method_25364() + 6;
        class_3322.method_25294(n9, n5, n10, n11, n3);
        class_3322.method_25294(n9, n5, n10, n5 + 1, n4);
        class_3322.method_25294(n9, n11 - 1, n10, n11, n4);
        class_3322.method_25294(n9, n5, n9 + 1, n11, n4);
        class_3322.method_25294(n10 - 1, n5, n10, n11, n4);
        class_3322.method_25294(this.profileListX, this.profileListY, this.profileListX + this.profileListW, this.profileListY + this.profileListH, 1426985752);
        this.drawExpandedTypeGroups(class_3322);
        ArrayList<class_4185> arrayList = new ArrayList<class_4185>();
        ArrayList<class_2561> arrayList2 = new ArrayList<class_2561>();
        for (class_364 class_3642 : this.method_25396()) {
            if (!(class_3642 instanceof class_4185)) continue;
            class_4185 class_41852 = (class_4185)class_3642;
            if (!class_41852.field_22764) continue;
            arrayList.add(class_41852);
            arrayList2.add(class_41852.method_25369());
            class_41852.method_25355((class_2561)class_2561.method_43473());
        }
        super.method_25394(class_3322, n, n2, f);
        for (int i = 0; i < arrayList.size(); ++i) {
            ((class_4185)arrayList.get(i)).method_25355((class_2561)arrayList2.get(i));
        }
        class_3322.method_25303(this.field_22793, this.statusMessage == null ? "" : this.statusMessage, this.profilesTitleX, this.openProfilesButton.method_46427() - 12, 11075504);
        this.drawStyledButtons(class_3322, n, n2);
        this.drawRowIcons(class_3322);
        this.drawScrollbar(class_3322);
        this.drawDeleteProfileConfirmOverlay(class_3322, n, n2);
    }

    private void drawStyledButtons(class_332 class_3322, int n, int n2) {
        for (class_339 class_3392 : this.rowWidgets) {
            if (!(class_3392 instanceof class_4185)) continue;
            class_4185 class_41852 = (class_4185)class_3392;
            this.drawBlacklistButtonStyle(class_3322, class_41852, n, n2, false);
        }
        for (class_4185 class_41853 : this.profileRowButtons) {
            this.drawBlacklistButtonStyle(class_3322, class_41853, n, n2, false);
        }
        this.drawBlacklistButtonStyle(class_3322, this.resetOrderButton, n, n2, false);
        this.drawBlacklistButtonStyle(class_3322, this.doneButton, n, n2, false);
        this.drawBlacklistButtonStyle(class_3322, this.saveProfileButton, n, n2, false);
        this.drawBlacklistButtonStyle(class_3322, this.loadProfileButton, n, n2, false);
        this.drawBlacklistButtonStyle(class_3322, this.deleteProfileButton, n, n2, true);
        this.drawBlacklistButtonStyle(class_3322, this.openProfilesButton, n, n2, false);
    }

    private void drawBlacklistButtonStyle(class_332 class_3322, class_4185 class_41852, int n, int n2, boolean bl) {
        if (class_41852 != null && class_41852.field_22764) {
            int n3;
            int n4;
            int n5;
            int n6 = class_41852.method_46426();
            int n7 = class_41852.method_46427();
            int n8 = n6 + class_41852.method_25368();
            int n9 = n7 + class_41852.method_25364();
            boolean bl2 = MixThePacksScreen.isMouseInside(n, n2, n6, n7, n8, n9);
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

    private void drawExpandedTypeGroups(class_332 class_3322) {
        if (!this.rows.isEmpty()) {
            int n = this.listLeft;
            int n2 = this.listLeft + this.rowWidth + 6 + this.selectorWidth;
            int n3 = 0;
            while (n3 < this.rows.size()) {
                RowEntry rowEntry;
                RowEntry rowEntry2 = this.rows.get(n3);
                if (!rowEntry2.child()) {
                    ++n3;
                    continue;
                }
                String string = rowEntry2.slotId();
                int n4 = n3;
                while (n4 + 1 < this.rows.size() && (rowEntry = this.rows.get(n4 + 1)).child() && string.equals(rowEntry.slotId())) {
                    ++n4;
                }
                int n5 = this.rowBaseY.get(n3) - this.scrollOffset;
                int n6 = this.rowBaseY.get(n4) - this.scrollOffset + 20;
                int n7 = 0;
                int n8 = 3;
                int n9 = 3;
                int n10 = n - n7;
                int n11 = n2 + n7;
                int n12 = Math.max(this.listTop, n5 - n8);
                int n13 = Math.min(this.listBottom, n6 + n9);
                if (n13 > n12) {
                    int n14 = 856890146;
                    int n15 = -1439020458;
                    class_3322.method_25294(n10, n12, n11, n13, n14);
                    class_3322.method_25294(n10, n12, n11, n12 + 1, n15);
                    class_3322.method_25294(n10, n13 - 1, n11, n13, n15);
                    class_3322.method_25294(n10, n12, n10 + 1, n13, n15);
                    class_3322.method_25294(n11 - 1, n12, n11, n13, n15);
                }
                n3 = n4 + 1;
            }
        }
    }

    public boolean method_25402(double d, double d2, int n) {
        if (!this.deleteProfileConfirmOpen) {
            if (n == 0 && this.isMouseOverList(d, d2) && this.isMouseOverScrollBar(d, d2)) {
                this.updateScrollFromMouse(d2);
                return true;
            }
            return super.method_25402(d, d2, n);
        }
        this.recalcDeleteConfirmLayout();
        if (n == 0 || n == 0) {
            if (MixThePacksScreen.isMouseInside(d, d2, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom)) {
                this.confirmDeleteProfile();
                return true;
            }
            if (MixThePacksScreen.isMouseInside(d, d2, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom)) {
                this.cancelDeleteProfileConfirm();
                return true;
            }
            if (!MixThePacksScreen.isMouseInside(d, d2, this.deleteModalLeft, this.deleteModalTop, this.deleteModalRight, this.deleteModalBottom)) {
                this.cancelDeleteProfileConfirm();
                return true;
            }
        }
        return true;
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        if (this.deleteProfileConfirmOpen) {
            return true;
        }
        if (d4 == 0.0) {
            return super.method_25401(d, d2, d3, d4);
        }
        if (this.isMouseOverProfileList(d, d2)) {
            int n = Math.max(0, this.profiles.size() - this.profileVisibleRows);
            this.profileListScroll = class_3532.method_15340((int)(this.profileListScroll - (int)Math.signum(d4)), (int)0, (int)n);
            this.updateProfileRowButtons();
            return true;
        }
        if (this.isMouseOverProfilePanel(d, d2) && this.profilePanelMaxScroll > 0) {
            int n = (int)Math.round(d4 * 16.0);
            this.setProfilePanelScroll(class_3532.method_15340((int)(this.profilePanelScroll - n), (int)0, (int)this.profilePanelMaxScroll));
            return true;
        }
        if (this.isMouseOverList(d, d2) && this.maxScroll > 0) {
            int n = (int)Math.round(d4 * 24.0);
            this.scrollOffset = class_3532.method_15340((int)(this.scrollOffset - n), (int)0, (int)this.maxScroll);
            this.updateRowVisibility();
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
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
        if (n == 299) {
            this.method_25419();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            if (this.deleteProfileConfirmOpen) {
                this.cancelDeleteProfileConfirm();
            } else if (!this.hasUnsavedProfileChanges) {
                this.field_22787.method_1507(this.parent);
            } else {
                this.field_22787.method_1507((class_437)new class_410(bl -> {
                    if (this.field_22787 != null) {
                        if (!bl) {
                            this.field_22787.method_1507((class_437)this);
                        } else {
                            this.initializeStagedSelectionsFromCurrent();
                            this.hasUnsavedProfileChanges = false;
                            this.field_22787.method_1507(this.parent);
                        }
                    }
                }, (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.title"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.message"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.ignore_exit"), (class_2561)class_2561.method_43471((String)"punchy.tuning.ui.confirm_unsaved_profile.cancel")));
            }
        }
    }

    private void recalcScroll() {
        int n = this.rows.isEmpty() ? 0 : this.rows.size() * 24 - 4;
        int n2 = Math.max(0, this.listBottom - this.listTop);
        this.maxScroll = Math.max(0, n - n2);
        this.scrollOffset = class_3532.method_15340((int)this.scrollOffset, (int)0, (int)this.maxScroll);
    }

    private void rebuildRows() {
        if (!this.rows.isEmpty()) {
            for (class_339 object2 : this.rowWidgets) {
                this.method_37066(object2);
            }
        }
        this.rows.clear();
        this.rowBaseY.clear();
        this.rowWidgets.clear();
        List<String> list = PunchyAnimationManager.getMixPackAnimationNames();
        ArrayList<String> arrayList = list == null ? new ArrayList<String>() : new ArrayList(list);
        String string = "kind:empty_hand";
        if (arrayList.remove(string)) {
            arrayList.add(0, string);
        }
        for (String string2 : arrayList) {
            if (!this.matchesFilter(string2)) continue;
            this.addMainRow(string2);
        }
        this.recalcScroll();
        this.updateRowVisibility();
    }

    private void addMainRow(String string) {
        List<PunchyAnimationManager.PackOption> list = PunchyAnimationManager.getMixPackOptions(string);
        String string2 = this.stagedMainSelection(string);
        int n = MixThePacksScreen.selectedIndex(list, string2);
        int n2 = this.listTop + this.rows.size() * 24;
        String string3 = PunchyAnimationManager.getMixPackEntryLabel(string);
        if (string3 == null || string3.isBlank()) {
            string3 = string;
        }
        class_4185 class_41853 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)string3), class_41852 -> {}).method_46433(this.listLeft, n2).method_46437(this.rowWidth, 20).method_46431());
        class_4185 class_41854 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)MixThePacksScreen.optionLabel(list, n)), class_41852 -> {
            String currentSelection = this.stagedMainSelection(string);
            int currentIndex = MixThePacksScreen.selectedIndex(list, currentSelection);
            int nextIndex = list.isEmpty() ? -1 : (currentIndex + 1) % list.size();
            if (nextIndex >= 0) {
                PunchyAnimationManager.PackOption packOption = list.get(nextIndex);
                this.stageMainSelection(string, packOption.optionId());
                this.hasUnsavedProfileChanges = true;
                this.rebuildRows();
            }
        }).method_46433(this.listLeft + this.rowWidth + 6, n2).method_46437(this.selectorWidth, 20).method_46431());
        this.rows.add(new RowEntry(string, null, false, class_41853, class_41854));
        this.rowBaseY.add(n2);
        this.rowWidgets.add((class_339)class_41853);
        this.rowWidgets.add((class_339)class_41854);
    }

    private boolean matchesFilter(String string) {
        if (this.filterText != null && !this.filterText.isBlank()) {
            String string2 = this.filterText.toLowerCase().trim();
            String string3 = string == null ? "" : string.toLowerCase();
            String string4 = PunchyAnimationManager.getMixPackEntryLabel(string);
            String string5 = string4 == null ? "" : string4.toLowerCase();
            return string3.contains(string2) || string5.contains(string2);
        }
        return true;
    }

    private void drawRowIcons(class_332 class_3322) {
        for (RowEntry rowEntry : this.rows) {
            class_1799 class_17992;
            if (rowEntry == null || rowEntry.child() || !rowEntry.nameButton().field_22764 || (class_17992 = this.resolveDisplayStack(rowEntry.slotId())) == null || class_17992.method_7960()) continue;
            class_4185 class_41852 = rowEntry.nameButton();
            int n = class_41852.method_46426() + 5;
            int n2 = class_41852.method_46427() + Math.max(0, (class_41852.method_25364() - 16) / 2);
            class_3322.method_51427(class_17992, n, n2);
        }
    }

    private class_1799 resolveDisplayStack(String string) {
        if (string != null && !string.isBlank()) {
            Object object;
            Object object2;
            if (string.startsWith("specific:") && (object2 = class_2960.method_12829((String)string.substring("specific:".length()))) != null && (object = class_7923.field_41178.method_17966((class_2960)object2).orElse(class_1802.field_8162)) != class_1802.field_8162) {
                return new class_1799((class_1935)object);
            }
            if (string.startsWith("kind:")) {
                object2 = string.substring("kind:".length()).trim();
                try {
                    object = VanillaFirstPersonItemLayer.ToolKind.valueOf(((String)object2).toUpperCase(Locale.ROOT));
                    return this.representativeStack((VanillaFirstPersonItemLayer.ToolKind)((Object)object));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                }
            }
            return class_1799.field_8037;
        }
        return class_1799.field_8037;
    }

    private class_1799 representativeStack(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        class_1792 class_17922 = switch (toolKind) {
            case VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND -> class_1802.field_8162;
            case VanillaFirstPersonItemLayer.ToolKind.SWORD -> class_1802.field_8802;
            case VanillaFirstPersonItemLayer.ToolKind.AXE -> class_1802.field_8556;
            case VanillaFirstPersonItemLayer.ToolKind.PICKAXE -> class_1802.field_8377;
            case VanillaFirstPersonItemLayer.ToolKind.SHOVEL -> class_1802.field_8250;
            case VanillaFirstPersonItemLayer.ToolKind.HOE -> class_1802.field_8527;
            case VanillaFirstPersonItemLayer.ToolKind.SPEAR -> this.resolveSpearDisplayItem();
            case VanillaFirstPersonItemLayer.ToolKind.TRIDENT -> class_1802.field_8547;
            case VanillaFirstPersonItemLayer.ToolKind.FOOD -> class_1802.field_8176;
            case VanillaFirstPersonItemLayer.ToolKind.LANTERN -> class_1802.field_16539;
            case VanillaFirstPersonItemLayer.ToolKind.CREATURE_BUCKET -> class_1802.field_28354;
            case VanillaFirstPersonItemLayer.ToolKind.BUCKET -> class_1802.field_8705;
            case VanillaFirstPersonItemLayer.ToolKind.MINECART -> class_1802.field_8045;
            case VanillaFirstPersonItemLayer.ToolKind.BOAT -> class_1802.field_8533;
            case VanillaFirstPersonItemLayer.ToolKind.BOW -> class_1802.field_8102;
            case VanillaFirstPersonItemLayer.ToolKind.ARROW -> class_1802.field_8107;
            case VanillaFirstPersonItemLayer.ToolKind.CROSSBOW -> class_1802.field_8399;
            case VanillaFirstPersonItemLayer.ToolKind.SHIELD -> class_1802.field_8255;
            case VanillaFirstPersonItemLayer.ToolKind.FISHING_ROD -> class_1802.field_8378;
            case VanillaFirstPersonItemLayer.ToolKind.SPYGLASS -> class_1802.field_27070;
            case VanillaFirstPersonItemLayer.ToolKind.FLINT -> class_1802.field_8884;
            case VanillaFirstPersonItemLayer.ToolKind.MACE -> class_1802.field_49814;
            case VanillaFirstPersonItemLayer.ToolKind.TORCH -> class_1802.field_8810;
            case VanillaFirstPersonItemLayer.ToolKind.CHEST -> class_1802.field_8106;
            case VanillaFirstPersonItemLayer.ToolKind.BUTTON_PLATE -> class_1802.field_8667;
            case VanillaFirstPersonItemLayer.ToolKind.TRAPDOOR -> class_1802.field_8376;
            case VanillaFirstPersonItemLayer.ToolKind.CARPET -> class_1802.field_8482;
            case VanillaFirstPersonItemLayer.ToolKind.BED -> class_1802.field_8789;
            case VanillaFirstPersonItemLayer.ToolKind.FULL_BLOCK, VanillaFirstPersonItemLayer.ToolKind.BLOCK, VanillaFirstPersonItemLayer.ToolKind.BUILDING_BLOCKS -> class_1802.field_20391;
            case VanillaFirstPersonItemLayer.ToolKind.COLORED_BLOCKS -> class_1802.field_19058;
            case VanillaFirstPersonItemLayer.ToolKind.NATURAL_BLOCKS -> class_1802.field_8270;
            case VanillaFirstPersonItemLayer.ToolKind.FUNCTIONAL_BLOCKS -> class_1802.field_8465;
            case VanillaFirstPersonItemLayer.ToolKind.REDSTONE -> class_1802.field_8725;
            default -> class_1802.field_8407;
        };
        return class_17922 == class_1802.field_8162 ? class_1799.field_8037 : new class_1799((class_1935)class_17922);
    }

    private class_1792 resolveSpearDisplayItem() {
        class_1792 class_17922;
        class_2960 class_29603 = class_2960.method_12829((String)"minecraft:diamond_spear");
        if (class_29603 != null && (class_17922 = class_7923.field_41178.method_17966(class_29603).orElse(class_1802.field_8162)) != class_1802.field_8162) {
            return class_17922;
        }
        class_2960 class_29602 = class_2960.method_12829((String)"minecraft:spear");
        if (class_29602 != null && (class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(class_1802.field_8162)) != class_1802.field_8162) {
            return class_17922;
        }
        return class_1802.field_8547;
    }

    private void addTypeRows(String string) {
        for (String string2 : PunchyAnimationManager.getMixPackExpandableEntryTypes(string)) {
            List<PunchyAnimationManager.PackOption> list = this.buildTypeOptionsForDisplay(string, string2);
            String string3 = this.stagedTypeSelection(string, string2);
            int n = MixThePacksScreen.selectedIndex(list, string3);
            int n2 = this.listTop + this.rows.size() * 24;
            int n3 = Math.min(10, Math.max(4, this.rowWidth / 8));
            int n4 = this.listLeft + n3;
            int n5 = Math.max(80, this.rowWidth - n3 * 2);
            int n6 = this.listLeft + this.rowWidth + 6 - n3;
            String string4 = "  - " + MixThePacksScreen.prettyTypeLabel(string2);
            class_4185 class_41853 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)string4), class_41852 -> {}).method_46433(n4, n2).method_46437(n5, 20).method_46431());
            class_41853.field_22763 = false;
            class_4185 class_41854 = (class_4185)this.method_37063(class_4185.method_46430((class_2561)class_2561.method_43470((String)MixThePacksScreen.optionLabel(list, n)), class_41852 -> {
                String currentSelection = this.stagedTypeSelection(string, string2);
                int currentIndex = MixThePacksScreen.selectedIndex(list, currentSelection);
                int nextIndex = list.isEmpty() ? -1 : (currentIndex + 1) % list.size();
                if (nextIndex >= 0) {
                    PunchyAnimationManager.PackOption packOption = list.get(nextIndex);
                    this.stageTypeSelection(string, string2, packOption.optionId());
                    this.hasUnsavedProfileChanges = true;
                    this.rebuildRows();
                }
            }).method_46433(n6, n2).method_46437(this.selectorWidth, 20).method_46431());
            this.rows.add(new RowEntry(string, string2, true, class_41853, class_41854));
            this.rowBaseY.add(n2);
            this.rowWidgets.add((class_339)class_41853);
            this.rowWidgets.add((class_339)class_41854);
        }
    }

    private void updateRowVisibility() {
        for (int i = 0; i < this.rows.size(); ++i) {
            boolean bl;
            RowEntry rowEntry = this.rows.get(i);
            int n = this.rowBaseY.get(i);
            int n2 = n - this.scrollOffset;
            rowEntry.nameButton().method_46419(n2);
            rowEntry.selectorButton().method_46419(n2);
            rowEntry.nameButton().field_22764 = bl = n2 + 20 > this.listTop && n2 < this.listBottom;
            rowEntry.nameButton().field_22763 = bl && !rowEntry.child();
            rowEntry.selectorButton().field_22764 = bl;
            rowEntry.selectorButton().field_22763 = bl;
        }
    }

    private void drawScrollbar(class_332 class_3322) {
        if (this.maxScroll > 0) {
            int n = 4;
            int n2 = this.listRight - 4;
            int n3 = this.listTop;
            int n4 = this.listBottom;
            class_3322.method_25294(n2, n3, n2 + n, n4, 1427118368);
            int n5 = Math.max(1, n4 - n3);
            int n6 = this.rows.size() * 24 - 4;
            int n7 = Math.max(10, Math.round((float)n5 * ((float)(this.listBottom - this.listTop) / (float)n6)));
            int n8 = n5 - n7;
            int n9 = this.maxScroll == 0 ? 0 : Math.round((float)n8 * ((float)this.scrollOffset / (float)this.maxScroll));
            int n10 = n3 + n9;
            class_3322.method_25294(n2, n10, n2 + n, Math.min(n4, n10 + n7), -2827553);
        }
    }

    private boolean isMouseOverList(double d, double d2) {
        return d >= (double)this.listLeft && d <= (double)this.listRight && d2 >= (double)this.listTop && d2 <= (double)this.listBottom;
    }

    private boolean isMouseOverScrollBar(double d, double d2) {
        int n = this.listRight - 4;
        return d >= (double)n && d <= (double)this.listRight && d2 >= (double)this.listTop && d2 <= (double)this.listBottom;
    }

    private boolean isMouseOverProfileList(double d, double d2) {
        return d >= (double)this.profileListX && d <= (double)(this.profileListX + this.profileListW) && d2 >= (double)this.profileListY && d2 <= (double)(this.profileListY + this.profileListH);
    }

    private boolean isMouseOverProfilePanel(double d, double d2) {
        int n = this.profilesTitleY - 2;
        int n2 = this.doneButton.method_46427() - 6;
        return d >= (double)this.profilePanelX && d <= (double)(this.profilePanelX + this.profilePanelW) && d2 >= (double)n && d2 <= (double)n2;
    }

    private void updateScrollFromMouse(double d) {
        int n = this.listBottom - this.listTop;
        if (n > 0) {
            float f = (float)((d - (double)this.listTop) / (double)n);
            f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            this.scrollOffset = (int)(f * (float)this.maxScroll);
            this.updateRowVisibility();
        }
    }

    private void recalcProfilePanelScroll() {
        this.profilePanelScroll = 0;
        this.profilePanelMaxScroll = 0;
    }

    private void openDeleteProfileConfirm() {
        String string;
        String string2 = this.selectedProfileName();
        if ((string2 == null || string2.isBlank()) && this.profileNameBox != null && (string = this.profileNameBox.method_1882()) != null && !(string = string.trim()).isBlank() && this.profiles.contains(string)) {
            this.selectedProfile = this.profiles.indexOf(string);
            this.ensureSelectedVisibleInProfileList();
            this.updateProfileRowButtons();
            string2 = string;
        }
        if (string2 != null && !string2.isBlank()) {
            this.pendingDeleteProfileName = string2;
            this.recalcDeleteConfirmLayout();
            this.deleteProfileConfirmOpen = true;
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
        this.cancelDeleteProfileConfirm();
        boolean bl2 = bl = string != null && !string.isBlank() && MixProfileManager.deleteProfile(string);
        if (bl) {
            this.applyResetToResourcePackOrderAndUnloadProfile();
        }
        this.statusMessage = bl ? class_2561.method_43469((String)"punchy.tuning.ui.status.profile_deleted", (Object[])new Object[]{string}).getString() : class_2561.method_43471((String)"punchy.tuning.ui.status.profile_delete_failed").getString();
        this.refreshProfiles(true);
    }

    private void applyResetToResourcePackOrderAndUnloadProfile() {
        PunchyAnimationManager.resetMixPacksToResourcePackOrder();
        MixProfileManager.unloadActiveProfileToDefault();
        this.stagedPackSelections.clear();
        this.stagedPackTypeSelections.clear();
        this.setPreviewToResourcePackOrder();
        this.hasUnsavedProfileChanges = false;
        this.mixSessionBaseline = PunchyConfig.snapshot();
        this.initializeStagedSelectionsFromCurrent();
        this.refreshProfiles(true);
        this.rebuildRows();
    }

    private void recalcDeleteConfirmLayout() {
        int n = Math.min(380, Math.max(260, this.containerRight - this.containerLeft - 48));
        int n2 = 112;
        this.deleteModalLeft = this.containerLeft + (this.containerRight - this.containerLeft - n) / 2;
        this.deleteModalTop = this.containerTop + (this.containerBottom - this.containerTop - n2) / 2;
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
            class_3322.method_25294(0, 0, this.field_22789, this.field_22790, -805306368);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalRight, this.deleteModalBottom, -15657446);
            int n3 = -11641751;
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalRight, this.deleteModalTop + 1, n3);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalBottom - 1, this.deleteModalRight, this.deleteModalBottom, n3);
            class_3322.method_25294(this.deleteModalLeft, this.deleteModalTop, this.deleteModalLeft + 1, this.deleteModalBottom, n3);
            class_3322.method_25294(this.deleteModalRight - 1, this.deleteModalTop, this.deleteModalRight, this.deleteModalBottom, n3);
            class_2561 class_25612 = MixThePacksScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.title", "Voc\u00ea realmente quer fazer isso?", new Object[0]);
            String string = this.pendingDeleteProfileName == null ? "-" : this.pendingDeleteProfileName;
            class_2561 class_25613 = MixThePacksScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.message", "Delete profile \"" + string + "\"?", string);
            String string2 = class_25612.getString();
            String string3 = class_25613.getString();
            int n4 = this.deleteModalLeft + (this.deleteModalRight - this.deleteModalLeft - this.field_22793.method_1727(string2)) / 2;
            int n5 = this.deleteModalLeft + (this.deleteModalRight - this.deleteModalLeft - this.field_22793.method_1727(string3)) / 2;
            class_3322.method_25303(this.field_22793, string2, n4, this.deleteModalTop + 12, -1);
            class_3322.method_25303(this.field_22793, string3, n5, this.deleteModalTop + 34, -3747624);
            boolean bl = MixThePacksScreen.isMouseInside(n, n2, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom);
            boolean bl2 = MixThePacksScreen.isMouseInside(n, n2, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom);
            this.drawModalButton(class_3322, this.deleteCancelButtonLeft, this.deleteCancelButtonTop, this.deleteCancelButtonRight, this.deleteCancelButtonBottom, bl, false, MixThePacksScreen.nonBlankTranslatable("gui.cancel", "Cancel", new Object[0]));
            this.drawModalButton(class_3322, this.deleteConfirmButtonLeft, this.deleteConfirmButtonTop, this.deleteConfirmButtonRight, this.deleteConfirmButtonBottom, bl2, true, MixThePacksScreen.nonBlankTranslatable("punchy.tuning.ui.confirm_delete_profile.confirm", "Delete", new Object[0]));
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

    private void setProfilePanelScroll(int n) {
        int n2 = class_3532.method_15340((int)n, (int)0, (int)this.profilePanelMaxScroll);
        if (n2 != this.profilePanelScroll) {
            int n3 = n2 - this.profilePanelScroll;
            this.profilePanelScroll = n2;
            int n4 = -n3;
            this.profilesTitleY += n4;
            this.profileListY += n4;
            this.profileNameBox.method_46419(this.profileNameBox.method_46427() + n4);
            this.saveProfileButton.method_46419(this.saveProfileButton.method_46427() + n4);
            this.loadProfileButton.method_46419(this.loadProfileButton.method_46427() + n4);
            this.deleteProfileButton.method_46419(this.deleteProfileButton.method_46427() + n4);
            this.openProfilesButton.method_46419(this.openProfilesButton.method_46427() + n4);
            for (class_4185 class_41852 : this.profileRowButtons) {
                class_41852.method_46419(class_41852.method_46427() + n4);
            }
        }
    }

    private void refreshProfiles(boolean bl) {
        this.profiles = MixProfileManager.listProfiles();
        this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)Math.max(0, this.profiles.size() - this.profileVisibleRows));
        if (this.profiles.isEmpty()) {
            this.selectedProfile = -1;
            if (this.profileNameBox != null) {
                this.profileNameBox.method_1852("");
            }
            this.updateProfileRowButtons();
        } else {
            String string;
            if (this.selectedProfile < 0 || this.selectedProfile >= this.profiles.size() || bl) {
                string = MixProfileManager.getActiveProfileName();
                if (string != null) {
                    int n = this.profiles.indexOf(string);
                    if (n >= 0) {
                        this.selectedProfile = n;
                    }
                } else if (bl) {
                    this.selectedProfile = -1;
                }
                if (this.selectedProfile < 0 && !bl) {
                    this.selectedProfile = 0;
                } else if (this.selectedProfile >= 0) {
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

    private void ensureSelectedVisibleInProfileList() {
        if (this.selectedProfile >= 0) {
            int n = Math.max(0, this.profiles.size() - this.profileVisibleRows);
            if (this.selectedProfile < this.profileListScroll) {
                this.profileListScroll = this.selectedProfile;
            } else if (this.selectedProfile >= this.profileListScroll + this.profileVisibleRows) {
                this.profileListScroll = this.selectedProfile - this.profileVisibleRows + 1;
            }
            this.profileListScroll = class_3532.method_15340((int)this.profileListScroll, (int)0, (int)n);
        }
    }

    private void updateProfileRowButtons() {
        for (int i = 0; i < this.profileRowButtons.size(); ++i) {
            class_4185 class_41852 = this.profileRowButtons.get(i);
            int n = this.profileListScroll + i;
            if (n >= 0 && n < this.profiles.size()) {
                String string = this.profiles.get(n);
                boolean bl = n == this.selectedProfile;
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
            if (string != null && this.profileNameBox != null) {
                this.profileNameBox.method_1852(string);
            }
            this.ensureSelectedVisibleInProfileList();
            this.updateProfileRowButtons();
        }
    }

    private String selectedProfileName() {
        return this.selectedProfile >= 0 && this.selectedProfile < this.profiles.size() ? this.profiles.get(this.selectedProfile) : null;
    }

    private boolean openProfilesFolder() {
        try {
            Path path = MixProfileManager.profilesDirectoryPath();
            class_156.method_668().method_60932(path);
            return true;
        }
        catch (Exception exception) {
            try {
                Path path = MixProfileManager.profilesDirectoryPath();
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

    private void initializeStagedSelectionsFromCurrent() {
        PunchyConfig.Data data = PunchyConfig.snapshot();
        this.stagedPackSelections.clear();
        this.stagedPackTypeSelections.clear();
        this.previewPackSelections.clear();
        this.previewPackTypeSelections.clear();
        if (data != null) {
            if (data.animationPackSelections != null) {
                this.stagedPackSelections.putAll(data.animationPackSelections);
            }
            if (data.animationPackTypeSelections != null) {
                this.stagedPackTypeSelections.putAll(data.animationPackTypeSelections);
            }
        }
    }

    private String stagedMainSelection(String string) {
        if (this.stagedPackSelections.containsKey(string)) {
            String string2 = this.stagedPackSelections.get(string);
            return string2 != null && !string2.isBlank() ? string2 : "punchy_default";
        }
        String string3 = this.previewPackSelections.get(string);
        if (string3 != null && !string3.isBlank()) {
            return string3;
        }
        String string4 = this.stagedPackSelections.get(string);
        if (string4 != null && !string4.isBlank()) {
            return string4;
        }
        String string5 = PunchyAnimationManager.getSelectedMixPackOption(string);
        return string5 != null && !string5.isBlank() ? string5 : "punchy_default";
    }

    private String stagedTypeSelection(String string, String string2) {
        String string3 = string + "|" + (string2 == null ? "" : string2.trim().toLowerCase(Locale.ROOT));
        String string4 = this.stagedPackTypeSelections.get(string3);
        if (string4 != null && !string4.isBlank()) {
            return string4;
        }
        String string5 = this.previewPackTypeSelections.get(string3);
        if (string5 != null && !string5.isBlank()) {
            return string5;
        }
        String string6 = PunchyAnimationManager.getSelectedMixPackTypeOption(string, string2);
        return string6 != null && !string6.isBlank() ? string6 : this.stagedMainSelection(string);
    }

    private void stageMainSelection(String string, String string3) {
        if (string != null && !string.isBlank()) {
            String string4 = string + "|";
            this.stagedPackTypeSelections.keySet().removeIf(string2 -> string2 != null && string2.startsWith(string4));
            this.previewPackSelections.remove(string);
            this.previewPackTypeSelections.keySet().removeIf(string2 -> string2 != null && string2.startsWith(string4));
            if (string3 != null && !string3.isBlank()) {
                String string5 = string3.trim().toLowerCase(Locale.ROOT);
                if (string5.isBlank()) {
                    this.stagedPackSelections.remove(string);
                } else {
                    this.stagedPackSelections.put(string, string5);
                }
            } else {
                this.stagedPackSelections.remove(string);
            }
        }
    }

    private void stageTypeSelection(String string, String string2, String string3) {
        if (string != null && !string.isBlank() && string2 != null && !string2.isBlank()) {
            String string4 = string2.trim().toLowerCase(Locale.ROOT);
            String string5 = string + "|" + string4;
            this.previewPackTypeSelections.remove(string5);
            String string6 = string3 == null ? "" : string3.trim().toLowerCase(Locale.ROOT);
            String string7 = this.stagedMainSelection(string);
            if (!(string6.isBlank() || "punchy_default".equals(string6) || string6.equals(string7))) {
                this.stagedPackTypeSelections.put(string5, string6);
            } else {
                this.stagedPackTypeSelections.remove(string5);
            }
        }
    }

    private void setPreviewToResourcePackOrder() {
        this.previewPackSelections.clear();
        this.previewPackTypeSelections.clear();
        List<String> list = PunchyAnimationManager.getMixPackAnimationNames();
        if (list != null && !list.isEmpty()) {
            for (String string : list) {
                if (string == null || string.isBlank()) continue;
                String string2 = MixThePacksScreen.resolveResourceOrderMainOption(string);
                this.previewPackSelections.put(string, string2);
                List<String> list2 = PunchyAnimationManager.getMixPackEntryTypes(string);
                if (list2 == null || list2.isEmpty()) continue;
                for (String string3 : list2) {
                    if (string3 == null || string3.isBlank()) continue;
                    String string4 = string + "|" + string3.trim().toLowerCase(Locale.ROOT);
                    String string5 = MixThePacksScreen.resolveResourceOrderTypeOption(string, string3, string2);
                    if (string5 == null || string5.isBlank()) continue;
                    this.previewPackTypeSelections.put(string4, string5);
                }
            }
        }
    }

    private static String resolveResourceOrderMainOption(String string) {
        List<PunchyAnimationManager.PackOption> list = PunchyAnimationManager.getMixPackOptions(string);
        if (list != null && !list.isEmpty()) {
            for (PunchyAnimationManager.PackOption packOption : list) {
                if (packOption == null || packOption.optionId() == null || packOption.optionId().isBlank() || "punchy_default".equals(packOption.optionId())) continue;
                return packOption.optionId();
            }
            return "punchy_default";
        }
        return "punchy_default";
    }

    private static String resolveResourceOrderTypeOption(String string, String string2, String string3) {
        List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypeOptionIds(string, string2);
        if (list != null && !list.isEmpty()) {
            for (String string4 : list) {
                if (string4 == null || string4.isBlank() || "punchy_default".equals(string4)) continue;
                return string4;
            }
            return string3 != null && !string3.isBlank() ? string3 : "punchy_default";
        }
        return string3 != null && !string3.isBlank() ? string3 : "punchy_default";
    }

    private List<PunchyAnimationManager.PackOption> buildTypeOptionsForDisplay(String string, String string2) {
        List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypeOptionIds(string, string2);
        ArrayList<String> arrayList = new ArrayList<String>(list == null ? List.of() : list);
        String string3 = this.stagedMainSelection(string);
        if (string3 != null && !string3.isBlank() && !arrayList.contains(string3)) {
            arrayList.add(0, string3);
        }
        if (arrayList.isEmpty()) {
            return List.of();
        }
        ArrayList<PunchyAnimationManager.PackOption> arrayList2 = new ArrayList<PunchyAnimationManager.PackOption>(arrayList.size());
        for (String string4 : arrayList) {
            if ("punchy_default".equals(string4)) continue;
            arrayList2.add(new PunchyAnimationManager.PackOption(string4, this.displayPackOptionLabel(string4)));
        }
        return arrayList2;
    }

    private String displayPackOptionLabel(String string) {
        if (string == null || string.isBlank()) {
            return "Unknown";
        }
        if ("pack:punchy:punchy".equals(string)) {
            return "\u00a7bPunchy! \u00a7eDefault";
        }
        String string2 = string;
        if (string.startsWith("pack:")) {
            string2 = string.substring("pack:".length());
        }
        if (string2.startsWith("file/")) {
            string2 = string2.substring("file/".length());
        }
        if (string2.startsWith("file\\")) {
            string2 = string2.substring("file\\".length());
        }
        if (string2.endsWith(".zip")) {
            string2 = string2.substring(0, string2.length() - 4);
        }
        if (string2.endsWith(".jar")) {
            string2 = string2.substring(0, string2.length() - 4);
        }
        return (string2 = string2.replace('_', ' ').replace('-', ' ').trim()).isBlank() ? string : string2;
    }

    private static int selectedIndex(List<PunchyAnimationManager.PackOption> list, String string) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                if (!list.get(i).optionId().equals(string)) continue;
                return i;
            }
            return 0;
        }
        return -1;
    }

    private static String optionLabel(List<PunchyAnimationManager.PackOption> list, int n) {
        return list != null && !list.isEmpty() && n >= 0 && n < list.size() ? list.get(n).label() : "-";
    }

    private static String prettyTypeLabel(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().replace('_', ' ');
            string2 = string2.replaceAll("([a-z])([A-Z])", "$1 $2");
            String[] stringArray = string2.toLowerCase(Locale.ROOT).split("\\s+");
            StringBuilder stringBuilder = new StringBuilder();
            for (String string3 : stringArray) {
                if (string3.isBlank()) continue;
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(Character.toUpperCase(string3.charAt(0)));
                if (string3.length() <= 1) continue;
                stringBuilder.append(string3.substring(1));
            }
            return stringBuilder.toString();
        }
        return "Type";
    }

    record RowEntry(String slotId, String entryType, boolean child, class_4185 nameButton, class_4185 selectorButton) {
    }
}
