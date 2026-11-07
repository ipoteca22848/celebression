package ru.method.celebression.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_1074;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_742;
import net.minecraft.class_746;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.client.AllSkinsModule;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.media.MediaPlayerWidget;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.ui.ModuleCard;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.ui.UiInteractionState;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.components.SearchBar;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.GifCache;
import ru.method.celebression.utils.render.GifRenderer;
import ru.method.celebression.utils.render.GifTexture;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class ModulesBrowser
implements MinecraftClientAccess {
    private static final float Zz = 16.0f;
    private static final float ZA = 10.0f;
    private static final float ZB = 26.0f;
    private static final long ZC = 340L;
    private static final class_2960 ZD = Celebression.id("textures/gui/confused_spoty.gif");
    private static final float ZE = 88.0f;
    private static final float ZF = 68.0f;
    private static final float ZG = 34.0f;
    private static final float ZH = 6.0f;
    private final UiInteractionState ZI;
    private final SearchBar ZJ;
    private final MediaPlayerWidget ZK = new MediaPlayerWidget();
    private final List<ModuleCard> ZL = new ArrayList<ModuleCard>();
    private final AnimatedComponent ZM = new AnimatedComponent(Easing.aiZ);
    private final Animation ZN = new Animation(320L, 1.0, false, Easing.aiZ);
    private final Animation ZO = new Animation(360L, 1.0, false, Easing.aiR);
    private final TimedProgress ZP = new TimedProgress(170L, Easing.aiZ, "");
    private final Animation ZQ = new Animation(220L, 1.0, false, Easing.aiZ);
    private final Animation ZR = new Animation(300L, 1.0, false, Easing.aiZ);
    private final Animation ZS = new Animation(200L, 1.0, false, Easing.aiZ);
    private final Animation ZT = new Animation(200L, 1.0, false, Easing.aiZ);
    private final Animation ZU = new Animation(200L, 1.0, false, Easing.aiZ);
    private final Animation ZV = new Animation(200L, 1.0, false, Easing.aiZ);
    private boolean ZW;
    private float x;
    private float y;
    private float width;
    private float height;
    private float Lm;
    private ModuleCategory ZX = ModuleCategory.qj;
    private String ZY = "";
    private long ZZ;
    private boolean aaa;
    private Runnable aab = () -> {};
    private Runnable aac = () -> {};
    private static String aad = "";

    public void a(Runnable runnable, Runnable runnable2) {
        this.aab = runnable;
        this.aac = runnable2;
    }

    public ModulesBrowser(UiInteractionState uiInteractionState) {
        this.ZI = uiInteractionState;
        this.ZJ = new SearchBar(0.0f, 0.0f, 280.0f, 28.0f);
        this.pt();
    }

    public void b(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.ZJ.setX(this.pq());
        this.ZJ.setY(f2 + 12.0f);
        this.ZJ.setWidth(this.pr());
        this.ZJ.setHeight(28.0f);
        this.ZK.b(f + 16.0f, f2 + f4 - 84.0f + 2.0f, f3 - 32.0f, 80.0f);
    }

    private float po() {
        return this.x + 88.0f;
    }

    private float pp() {
        return this.y + 12.0f;
    }

    private float pq() {
        return this.x + 88.0f + 34.0f + 6.0f;
    }

    private float pr() {
        return this.width - 68.0f - 88.0f - 34.0f - 6.0f;
    }

    public void init() {
        this.ZK.init();
        this.ZJ.setSearchText(aad);
        this.ps();
        this.ct(this.ZJ.getSearchText().toLowerCase(Locale.ROOT));
    }

    private void ps() {
        String string = this.ZJ.getSearchText().toLowerCase(Locale.ROOT);
        for (ModuleCard moduleCard : this.ZL) {
            moduleCard.aa(this.a(moduleCard, string));
        }
    }

    public void shutdown() {
        aad = this.ZJ.getSearchText();
        this.ZK.shutdown();
    }

    public void tick() {
        this.ZK.tick();
        this.ZN.i(this.ZJ.isFocused());
        this.ZO.i(this.ZJ.isFocused() || !this.ZJ.getSearchText().isEmpty());
        if (this.ZJ.isFocused() && !this.aaa) {
            this.ZZ = System.currentTimeMillis();
        }
        this.aaa = this.ZJ.isFocused();
        this.ZP.cF(this.ZJ.getSearchText());
        this.cq(this.ZJ.getSearchText().toLowerCase(Locale.ROOT));
    }

    public void a(class_332 class_3322, int n, int n2, float f, Runnable runnable) {
        LinkGuiBrandRenderer.g(class_3322.method_51448(), this.x, this.y, this.width, this.height, 12.0f);
        this.a(class_3322.method_51448(), class_3322, n, n2);
        float f2 = this.y + 52.0f + 2.0f;
        float f3 = this.y + this.height - 84.0f - 2.0f;
        float f4 = f3 - f2;
        String string = this.ZJ.getSearchText().toLowerCase(Locale.ROOT);
        this.cs(string);
        float f7 = Math.max(0.0f, this.cu(string) - f4);
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f7);
        float f8 = class_3532.method_15363((float)this.ZM.a(this.Lm, 340L), (float)0.0f, (float)f7);
        if (class_3322 != null) {
            Render2D.startScissor(class_3322, this.x + 16.0f, f2 - 4.0f, this.width - 32.0f, f4 + 6.0f);
        }
        float f9 = (this.width - 32.0f - 52.0f - 10.0f) / 2.0f;
        this.a(string, f8, f9, f2, (ModuleCard moduleCard, float f5, float f6) -> {
            moduleCard.setX(f5);
            moduleCard.setY(f6);
            moduleCard.setWidth(f9);
            moduleCard.ar(1.0f);
            if (f6 + moduleCard.getHeight() >= f2 - 8.0f && f6 <= f3 + 8.0f) {
                moduleCard.b(class_3322.method_51448(), n, n2, f);
            }
        });
        this.ZQ.i(this.cr(string));
        float f10 = this.ZQ.rk() * LinkGuiRenderContext.getFadeMultiplier();
        if (f10 > 0.01f) {
            this.j(class_3322.method_51448(), f2, f4, f10);
        }
        if (class_3322 != null) {
            Render2D.stopScissor(class_3322);
        }
        float f11 = Math.max(this.ZN.rk(), this.ZO.rk() * 0.55f);
        LinkGuiBrandRenderer.j(class_3322.method_51448(), this.pq(), this.y + 12.0f, this.pr(), 28.0f, f11);
        this.g(class_3322.method_51448());
        this.e(class_3322.method_51448(), n, n2);
        this.ZK.a(class_3322, n, n2);
    }

    private void e(class_4587 class_45872, int n, int n2) {
        this.ZR.i(this.ZW);
        float f = this.ZR.rk();
        float f2 = this.po();
        float f3 = this.pp();
        float f4 = 34.0f;
        float f5 = 28.0f;
        boolean bl = MathUtils.c(f2, f3, f4, f5, n, n2);
        this.ZV.i(bl);
        float f6 = this.ZV.rk();
        Color color = ThemeTransition.accent();
        float f7 = 9.0f;
        LinkGuiBrandRenderer.h(class_45872, f2, f3, f4, f5, f7, 0.5f + 0.35f * f6);
        if (f > 0.01f) {
            Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(235.0f * f));
            UiRenderUtils.drawRoundedRect(class_45872, f2, f3, f4, f5, f7, color2);
        }
        int n3 = Math.round(90.0f + 120.0f * Math.max(f, f6));
        UiRenderUtils.c(class_45872, f2, f3, f4, f5, f7, 1.2f, new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, n3)));
        Color color3 = InterpolationUtils.lerp(ColorUtils.getTextColor(220), new Color(15, 15, 18, 255), f);
        float f8 = 7.6f;
        float f9 = Fonts.ROUNDBOLD.getWidth("All", f8);
        float f10 = Fonts.ROUNDBOLD.getHeight(f8);
        Render2D.drawFont(class_45872, Fonts.ROUNDBOLD.getFont(f8), "All", f2 + (f4 - f9) / 2.0f, f3 + (f5 - f10) / 2.0f, color3);
    }

    private void g(class_4587 class_45872) {
        float f = this.pq();
        float f2 = this.y + 12.0f;
        float f3 = this.pr();
        float f4 = 28.0f;
        float f5 = 8.2f;
        float f6 = Math.max(this.ZN.rk(), this.ZO.rk());
        float f7 = f2 + f4 / 2.0f - Fonts.MEDIUM.getHeight(f5) / 2.0f;
        float f8 = f + 14.0f;
        String string = this.ZJ.getSearchText();
        boolean bl = string.isEmpty();
        if (bl && !this.ZJ.isFocused() && !this.ZP.rg()) {
            Color searchColor = ColorUtils.getTextColor((int)(120.0f + 35.0f * f6));
            Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f5), class_1074.method_4662((String)"clickgui.search.placeholder", (Object[])new Object[0]), f8, f7, searchColor);
        } else {
            Color searchColor = ColorUtils.getTextColor((int)(235.0f + 20.0f * this.ZN.rk()));
            this.r(class_45872, f8, f7, f5, searchColor);
        }
        if (this.ZJ.isFocused()) {
            float f9 = f8 + (bl ? 0.0f : Fonts.MEDIUM.getWidth(string, f5));
            long l = System.currentTimeMillis() - this.ZZ;
            double d = (double)(l % 1000L) / 1000.0;
            float f10 = (float)(0.35 + 0.65 * (0.5 + 0.5 * Math.sin(d * Math.PI * 2.0)));
            Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f5), "|", f9 + 1.0f, f7, ColorUtils.getTextColor((int)(255.0f * f10)));
        }
        Color color = ColorUtils.getTextColor((int)(140.0f + 80.0f * f6));
        UiRenderUtils.a(class_45872, Fonts.ICONSNEW, "F", f + f3 - 22.0f, f2 + 9.0f, color, 9.0f);
    }

    private void r(class_4587 class_45872, float f, float f2, float f3, Color color) {
        if (!this.ZP.rg()) {
            Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), this.ZP.rh(), f, f2, color);
            return;
        }
        float f4 = Math.max(0.0f, Math.min(1.0f, this.ZP.progress()));
        String string = this.ZP.ri();
        String string2 = this.ZP.rh();
        int n = EasingMath.w(string, string2);
        int n2 = EasingMath.d(string, string2, n);
        String string3 = EasingMath.a(string2, 0, n);
        String string4 = EasingMath.a(string, n, string.length() - n2);
        String string5 = EasingMath.a(string2, n, string2.length() - n2);
        String string6 = EasingMath.a(string2, string2.length() - n2, string2.length());
        float f5 = Fonts.MEDIUM.getWidth(string3, f3);
        float f6 = Fonts.MEDIUM.getWidth(string5, f3);
        float f7 = Fonts.MEDIUM.getHeight(f3) * 0.6f;
        int n3 = color.getAlpha();
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)n3 * (1.0f - f4)));
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)n3 * f4));
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), string3, f, f2, color);
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), string6, f + f5 + f6, f2, color);
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), string4, f + f5, f2 - f7 * f4, color2);
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), string5, f + f5, f2 + f7 * (1.0f - f4), color3);
    }

    private void a(class_4587 class_45872, class_332 class_3322, int n, int n2) {
        Color color;
        float f = this.x + 16.0f;
        float f2 = this.y + 6.0f;
        float f3 = 40.0f;
        LinkGuiBrandRenderer.e(class_45872, f, f2, f3, f3, 11.0f);
        float f4 = f + f3 / 2.0f;
        float f5 = f2 + f3 / 2.0f;
        LinkGuiBrandRenderer.e(class_45872, f4, f5, 37.0f);
        float f6 = 28.0f;
        float f7 = this.x + 16.0f + 42.0f;
        float f8 = this.y + 12.0f;
        boolean bl = MathUtils.c(f7, f8, f6, f6, n, n2);
        this.ZS.i(bl);
        float f9 = this.ZS.rk();
        LinkGuiBrandRenderer.h(class_45872, f7, f8, f6, f6, 7.0f);
        class_742 player = ModulesBrowser.aiG.field_1724;
        if (class_3322 != null && player != null) {
            int n3 = Math.max(0, Math.min(255, Math.round(255.0f * LinkGuiRenderContext.getFadeMultiplier())));
            class_2960 class_29602 = AllSkinsModule.it();
            if (class_29602 == null) {
                class_29602 = player.method_52814().comp_1626();
            }
            Render2D.drawTexture(class_45872, f7 + 1.0f, f8 + 1.0f, f6 - 2.0f, f6 - 2.0f, 6.0f, 0.125f, 0.125f, 0.125f, 0.125f, class_29602, new Color(255, 255, 255, n3));
        }
        if (f9 > 0.01f) {
            color = ThemeTransition.accent();
            UiRenderUtils.c(class_45872, f7, f8, f6, f6, 7.0f, 1.2f, new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(200.0f * f9)));
        }
        float f10 = this.x + this.width - 16.0f - 24.0f;
        float f11 = this.y + 14.0f;
        boolean bl2 = MathUtils.c(f10, f11, 24.0f, 24.0f, n, n2);
        this.ZT.i(bl2);
        LinkGuiBrandRenderer.h(class_45872, f10, f11, 24.0f, 24.0f, 6.0f, this.ZT.rk());
        UiRenderUtils.b(class_45872, Fonts.ICONSNEW, "L", f10 + 12.0f, f11 + 6.0f, ColorUtils.getTextColor(220), 9.0f);
        float f12 = f10 - 28.0f;
        boolean bl3 = MathUtils.c(f12, f11, 24.0f, 24.0f, n, n2);
        this.ZU.i(bl3);
        LinkGuiBrandRenderer.h(class_45872, f12, f11, 24.0f, 24.0f, 6.0f, this.ZU.rk());
        GuiIcons.draw(class_45872, GuiIcons.PALETTE, f12 + 12.0f, f11 + 12.0f, 16.0f, ColorUtils.getTextColor(235));
        float f13 = this.y + 52.0f - 3.0f;
        Color color2 = ThemeTransition.accent();
        float f14 = (this.width - 32.0f) / 2.0f;
        UiRenderUtils.c(class_45872, this.x + 16.0f, f13, f14, 1.0f, 0.0f, InterpolationUtils.e(color2, 0.0f), InterpolationUtils.e(color2, 0.45f));
        UiRenderUtils.c(class_45872, this.x + 16.0f + f14, f13, f14, 1.0f, 0.0f, InterpolationUtils.e(color2, 0.45f), InterpolationUtils.e(color2, 0.0f));
    }

    private void cq(String string) {
        for (ModuleCard moduleCard : this.ZL) {
            moduleCard.z(this.a(moduleCard, string));
        }
    }

    private boolean b(Module module, String string) {
        if (string.isEmpty()) {
            return true;
        }
        return module.getName().toLowerCase(Locale.ROOT).contains(string) || module.getDisplayName().toLowerCase(Locale.ROOT).contains(string);
    }

    private boolean a(ModuleCard moduleCard, String string) {
        if (string.isEmpty()) {
            return true;
        }
        return moduleCard.oq().contains(string) || moduleCard.or().contains(string);
    }

    private boolean cr(String string) {
        if (string.isEmpty()) {
            return false;
        }
        for (ModuleCard moduleCard : this.ZL) {
            if (!this.a(moduleCard) || !this.a(moduleCard, string)) continue;
            return false;
        }
        return true;
    }

    private void j(class_4587 class_45872, float f, float f2, float f3) {
        GifTexture gifTexture = GifCache.get(ZD);
        String string = class_1074.method_4662((String)"clickgui.search.empty", (Object[])new Object[0]);
        float f4 = 8.6f;
        float f5 = Fonts.MEDIUM.getWidth(string, f4);
        float f6 = Fonts.MEDIUM.getHeight(f4);
        float f7 = 90.0f;
        float f8 = 14.0f;
        float f9 = f7 + f8 + f5;
        float f10 = this.x + 16.0f + (this.width - 32.0f - f9) / 2.0f;
        float f11 = f + f2 * 0.4f - f7 / 2.0f;
        GifRenderer.drawGif(class_45872, gifTexture, f10, f11, f7, f7, f3);
        float f12 = f10 + f7 + f8;
        float f13 = f11 + (f7 - f6) / 2.0f;
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f4), string, f12, f13, ColorUtils.getTextColor((int)(210.0f * f3)));
    }

    public void pt() {
        this.ZL.clear();
        ArrayList<Module> arrayList = new ArrayList<Module>();
        for (Module module2 : Celebression.getInstance().getModuleManager().getModules()) {
            if (module2 instanceof UI) continue;
            arrayList.add(module2);
        }
        arrayList.sort(Comparator.comparingInt((Module module) -> module.getCategory().ordinal()).thenComparing(Module::getDisplayName, String.CASE_INSENSITIVE_ORDER));
        for (Module module2 : arrayList) {
            this.ZL.add(new ModuleCard(module2));
        }
    }

    public boolean a(double d, double d2, int n, Runnable runnable) {
        float f;
        float f2;
        float f3;
        float f6 = this.x + this.width - 16.0f - 24.0f;
        float f7 = this.y + 14.0f;
        if (MathUtils.c(f6, f7, 24.0f, 24.0f, (float)d, (float)d2) && n == 0) {
            runnable.run();
            return true;
        }
        if (n == 0) {
            f3 = this.x + 16.0f + 42.0f;
            f2 = this.y + 12.0f;
            float f8 = 28.0f;
            if (MathUtils.c(f3, f2, f8, f8, (float)d, (float)d2)) {
                this.aab.run();
                return true;
            }
            f = f6 - 28.0f;
            if (MathUtils.c(f, f7, 24.0f, 24.0f, (float)d, (float)d2)) {
                this.aac.run();
                return true;
            }
        }
        if (n == 0 && MathUtils.c(this.po(), this.pp(), 34.0f, 28.0f, (float)d, (float)d2)) {
            this.ZW = !this.ZW;
            this.ct(this.ZJ.getSearchText().toLowerCase(Locale.ROOT));
            return true;
        }
        if (this.ZJ.mouseClicked(d, d2, n)) {
            return true;
        }
        if (this.ZK.mouseClicked(d, d2, n)) {
            return true;
        }
        String string = this.ZJ.getSearchText().toLowerCase(Locale.ROOT);
        f3 = this.y + 52.0f + 2.0f;
        f2 = this.y + this.height - 84.0f - 2.0f;
        f = f2 - f3;
        if (!MathUtils.c(this.x + 16.0f, f3, this.width - 32.0f, f, (float)d, (float)d2)) {
            return false;
        }
        float f9 = Math.max(0.0f, this.cu(string) - f);
        float f10 = class_3532.method_15363((float)this.ZM.rk(), (float)0.0f, (float)f9);
        float f11 = (this.width - 32.0f - 52.0f - 10.0f) / 2.0f;
        float viewportTop = f3;
        float viewportBottom = f2;
        boolean[] blArray = new boolean[]{false};
        this.a(string, f10, f11, f3, (ModuleCard moduleCard, float f4, float f5) -> {
            if (blArray[0]) {
                return;
            }
            float cardHeight = moduleCard.getHeight();
            if (f5 + cardHeight < viewportTop || f5 > viewportBottom) {
                return;
            }
            moduleCard.setX(f4);
            moduleCard.setY(f5);
            moduleCard.setWidth(f11);
            if (moduleCard.mouseClicked(d, d2, n)) {
                blArray[0] = true;
            }
        });
        return blArray[0];
    }

    public void mouseReleased(double d, double d2, int n) {
        this.ZK.mouseReleased(d, d2, n);
    }

    public void mouseDragged(double d, double d2) {
        this.ZK.mouseDragged(d, d2);
    }

    public boolean g(double d, double d2, double d3) {
        if (ModuleCard.d(d3)) {
            return true;
        }
        float f = this.y + 52.0f + 2.0f;
        float f2 = this.y + this.height - 84.0f - 2.0f;
        float f3 = f2 - f;
        if (MathUtils.c(this.x + 16.0f, f, this.width - 32.0f, f3, (float)d, (float)d2)) {
            this.Lm -= (float)(d3 * 36.0);
            String string = this.ZJ.getSearchText().toLowerCase(Locale.ROOT);
            float f4 = Math.max(0.0f, this.cu(string) - f3);
            this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f4);
            return true;
        }
        return false;
    }

    private void cs(String string) {
        boolean bl;
        ModuleCategory moduleCategory = this.ZI.mG();
        boolean bl2 = bl = moduleCategory != this.ZX;
        if (bl || !string.equals(this.ZY)) {
            this.Lm = 0.0f;
            this.ZM.a(0.0f, 1L);
            if (bl) {
                this.ct(string);
            }
            this.ZX = moduleCategory;
            this.ZY = string;
        }
    }

    private boolean a(ModuleCard moduleCard) {
        return this.ZW || moduleCard.getModule().getCategory() == this.ZI.mG();
    }

    private void ct(String string) {
        int n = 0;
        for (ModuleCard moduleCard : this.ZL) {
            if (!this.a(moduleCard) || !this.a(moduleCard, string)) continue;
            moduleCard.av(n++);
        }
    }

    private float cu(String string) {
        float[] fArray = new float[]{0.0f, 0.0f};
        int n = 0;
        for (ModuleCard moduleCard : this.ZL) {
            if (!this.a(moduleCard) || !moduleCard.ot()) continue;
            int n2 = n;
            fArray[n2] = fArray[n2] + (moduleCard.getHeight() + 10.0f) * moduleCard.ou();
            n = 1 - n;
        }
        float f = Math.max(fArray[0], fArray[1]);
        return Math.max(0.0f, f - 10.0f);
    }

    private void a(String string, float f, float f2, float f3, ModuleFilter moduleFilter) {
        float[] fArray = new float[]{f3 - f, f3 - f};
        int n = 0;
        for (ModuleCard moduleCard : this.ZL) {
            if (!this.a(moduleCard) || !moduleCard.ot()) continue;
            float f4 = this.x + 16.0f + 26.0f + (float)n * (f2 + 10.0f);
            float f5 = fArray[n];
            moduleFilter.accept(moduleCard, f4, f5);
            int n2 = n;
            fArray[n2] = fArray[n2] + (moduleCard.getHeight() + 10.0f) * moduleCard.ou();
            n = 1 - n;
        }
    }

    public boolean charTyped(char c, int n) {
        if (ModuleCard.ov()) {
            return false;
        }
        return this.ZJ.charTyped(c, n);
    }

    public boolean keyPressed(int n, int n2, int n3) {
        if (ModuleCard.aw(n)) {
            return true;
        }
        return this.ZJ.keyPressed(n, n2, n3);
    }

    public SearchBar getSearchBar() {
        return this.ZJ;
    }

    @FunctionalInterface
    static interface ModuleFilter {
        public void accept(ModuleCard var1, float var2, float var3);
    }
}
