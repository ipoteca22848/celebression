package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_3417;
import net.minecraft.class_4050;
import net.minecraft.class_5134;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_745;
import net.minecraft.class_746;
import net.minecraft.class_8685;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;

public class FakePlayerModule
extends Module {
    private final BooleanSetting tX = new BooleanSetting("settings.fakeplayer.copy_equipment", true);
    private final BooleanSetting tY = new BooleanSetting("settings.fakeplayer.totem", true);
    private final StringSetting tZ = new StringSetting("settings.fakeplayer.name", "", "");
    private final SliderSetting ua = new SliderSetting("settings.fakeplayer.duration", 0.0f, 0.0f, 600.0f, 5.0f);
    private final BindSetting ub = new BindSetting("settings.fakeplayer.reanchor", new KeyBind(-1, false));
    private FakePlayerState uc;
    private class_638 ud;
    private double ue;
    private double uf;
    private double ug;
    private float uh;
    private float ui;
    private float uj;
    private float uk;
    private int ul;
    private int um = Integer.MIN_VALUE;

    public FakePlayerModule() {
        super("FakePlayer", ModuleCategory.qm);
        this.getSettings().add(this.tX);
        this.getSettings().add(this.tY);
        this.getSettings().add(this.tZ);
        this.getSettings().add(this.ua);
        this.getSettings().add(this.ub);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.hj();
        super.onDisable();
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (FakePlayerModule.fullNullCheck() || FakePlayerModule.aiG.field_1755 != null) {
            return;
        }
        if (keyInputEvent.getAction() == 0) {
            return;
        }
        if (((KeyBind)this.ub.jT()).af(keyInputEvent.c())) {
            this.hh();
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (FakePlayerModule.fullNullCheck() || FakePlayerModule.aiG.field_1755 != null) {
            return;
        }
        if (eventMouse.getAction() == 0) {
            return;
        }
        if (((KeyBind)this.ub.jT()).ag(eventMouse.getButton())) {
            this.hh();
        }
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        if (this.uc == null || attackEntityEvent.b() != this.uc) {
            return;
        }
        attackEntityEvent.cancel();
        this.hf();
    }

    private void hf() {
        if (this.uc == null || FakePlayerModule.aiG.field_1724 == null || FakePlayerModule.aiG.field_1687 == null) {
            return;
        }
        float f = (float)FakePlayerModule.aiG.field_1724.method_45325(class_5134.field_23721);
        if (f < 1.0f) {
            f = 1.0f;
        }
        this.uc.method_48922(this.uc.method_48923().method_48802((class_1657)FakePlayerModule.aiG.field_1724));
        float f2 = this.uc.method_6032() - f;
        if (f2 > 0.0f) {
            this.uc.method_6033(f2);
            return;
        }
        if (this.uc.method_6079().method_31574(class_1802.field_8288)) {
            this.hg();
        }
        this.uc.method_6033(this.uc.method_6063());
    }

    private void hg() {
        if (this.uc == null || FakePlayerModule.aiG.field_1687 == null) {
            return;
        }
        this.uc.method_5783(class_3417.field_14931, 1.0f, 1.0f);
        for (int i = 0; i < 30; ++i) {
            FakePlayerModule.aiG.field_1687.method_8406((class_2394)class_2398.field_11220, this.uc.method_23322(0.5), this.uc.method_23319(), this.uc.method_23325(0.5), FakePlayerModule.aiG.field_1687.field_9229.method_43059() * 0.15, FakePlayerModule.aiG.field_1687.field_9229.method_43058() * 0.3, FakePlayerModule.aiG.field_1687.field_9229.method_43059() * 0.15);
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (FakePlayerModule.fullNullCheck()) {
            this.hj();
            return;
        }
        if (this.uc == null) {
            return;
        }
        if (FakePlayerModule.aiG.field_1687 != this.ud || this.uc.method_31481()) {
            this.hj();
            return;
        }
        this.uc.method_24203(this.ue, this.uf, this.ug);
        this.uc.method_36456(this.uh);
        this.uc.method_5636(this.ui);
        this.uc.method_5847(this.uj);
        this.uc.method_36457(this.uk);
        int n = Math.round(((Float)this.ua.jT()).floatValue()) * 20;
        if (n > 0) {
            ++this.ul;
            if (this.ul >= n) {
                this.toggle();
            }
        }
    }

    private void hh() {
        if (FakePlayerModule.fullNullCheck()) {
            return;
        }
        this.hj();
        this.hi();
    }

    private void hi() {
        class_8685 class_86852;
        class_746 class_7462 = FakePlayerModule.aiG.field_1724;
        if (class_7462 == null || FakePlayerModule.aiG.field_1687 == null) {
            return;
        }
        this.hj();
        GameProfile gameProfile = class_7462.method_7334();
        String string = this.tZ.kg().isBlank() ? gameProfile.getName() : this.tZ.kg().trim();
        GameProfile gameProfile2 = new GameProfile(UUID.randomUUID(), string);
        gameProfile2.getProperties().putAll((Multimap)gameProfile.getProperties());
        class_86852 = class_7462.method_52814();
        class_8685 class_86853 = class_86852;
        FakePlayerState fakePlayer = new FakePlayerState(FakePlayerModule.aiG.field_1687, gameProfile2, class_86853);
        fakePlayer.method_5838(this.hk());
        fakePlayer.method_5826(gameProfile2.getId());
        fakePlayer.method_18380(class_4050.field_18076);
        fakePlayer.method_5880(false);
        this.ud = FakePlayerModule.aiG.field_1687;
        this.ue = class_7462.method_23317();
        this.uf = class_7462.method_23318();
        this.ug = class_7462.method_23321();
        this.uh = class_7462.method_36454();
        this.ui = class_7462.method_43078();
        this.uj = class_7462.method_5791();
        this.uk = class_7462.method_36455();
        fakePlayer.method_24203(this.ue, this.uf, this.ug);
        fakePlayer.method_36456(this.uh);
        fakePlayer.method_5636(this.ui);
        fakePlayer.method_5847(this.uj);
        fakePlayer.method_36457(this.uk);
        if (((Boolean)this.tX.jT()).booleanValue()) {
            for (class_1304 class_13042 : class_1304.values()) {
                class_1799 class_17992 = class_7462.method_6118(class_13042);
                if (class_17992 == null) continue;
                fakePlayer.method_5673(class_13042, class_17992.method_7972());
            }
        }
        if (((Boolean)this.tY.jT()).booleanValue()) {
            fakePlayer.method_5673(class_1304.field_6171, new class_1799((class_1935)class_1802.field_8288));
        }
        fakePlayer.method_6033(fakePlayer.method_6063());
        FakePlayerModule.aiG.field_1687.method_53875(fakePlayer);
        this.uc = fakePlayer;
        this.ul = 0;
        ChatOutput.cG("FakePlayer: \u043a\u043b\u043e\u043d \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d (\u0432\u0438\u0434\u0435\u043d \u0442\u043e\u043b\u044c\u043a\u043e \u0442\u0435\u0431\u0435).");
    }

    private void hj() {
        if (this.uc != null) {
            this.uc.method_31472();
            if (FakePlayerModule.aiG.field_1687 != null) {
                FakePlayerModule.aiG.field_1687.method_2945(this.uc.method_5628(), class_1297.class_5529.field_26999);
            }
        }
        this.uc = null;
        this.ud = null;
        this.ul = 0;
    }

    private int hk() {
        int n = this.um;
        this.um = this.um == Integer.MAX_VALUE ? Integer.MIN_VALUE : ++this.um;
        return n;
    }

    static final class FakePlayerState
    extends class_745 {
        private final class_8685 un;

        FakePlayerState(class_638 class_6382, GameProfile gameProfile, class_8685 class_86852) {
            super(class_6382, gameProfile);
            this.un = class_86852;
        }

        public class_8685 hl() {
            return this.un != null ? this.un : super.method_52814();
        }

        public boolean hm() {
            return false;
        }
    }
}
