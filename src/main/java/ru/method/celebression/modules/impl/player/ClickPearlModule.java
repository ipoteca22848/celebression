package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_437;
import net.minecraft.class_490;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.mixins.accessors.IClientPlayerInteractionManager;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ClickPearlModule
extends Module {
    private static final int sD = 3;
    private final BindSetting sE = new BindSetting("modules.settings.clickpearl.bind", new KeyBind(-1, false));
    private final SliderSetting sF = new SliderSetting("modules.settings.clickpearl.slot", 9.0f, 1.0f, 9.0f, 1.0f);
    private boolean qG;
    private int sG = -1;
    private int sH = -1;
    private int sI;
    private int sJ;
    private int sK;
    private boolean qN;
    private int sL;
    private int sM = -1;
    private int sN = -1;

    public ClickPearlModule() {
        super("ClickPearl", ModuleCategory.qm);
        this.getSettings().add(this.sE);
        this.getSettings().add(this.sF);
    }

    @Override
    public void onDisable() {
        this.resetState();
        super.onDisable();
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (ClickPearlModule.fullNullCheck()) {
            return;
        }
        if (!((KeyBind)this.sE.jT()).af(keyInputEvent.c())) {
            return;
        }
        this.n(keyInputEvent.getAction() != 0);
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (ClickPearlModule.fullNullCheck()) {
            return;
        }
        if (!((KeyBind)this.sE.jT()).ag(eventMouse.getButton())) {
            return;
        }
        if (ClickPearlModule.aiG.field_1755 != null) {
            return;
        }
        eventMouse.consume();
        this.n(eventMouse.getAction() != 0);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (ClickPearlModule.fullNullCheck()) {
            this.resetState();
            return;
        }
        if (this.sK > 0) {
            --this.sK;
            if (this.sK <= 0) {
                this.gd();
            }
            return;
        }
        if (this.sJ > 0) {
            --this.sJ;
            if (this.sJ <= 0) {
                this.gJ();
            }
            return;
        }
        if (this.sL > 0) {
            --this.sL;
            if (this.sL <= 0) {
                this.gH();
                this.gd();
                this.sI = 1;
            }
            return;
        }
        if (this.sI > 0) {
            --this.sI;
            if (this.sI <= 0) {
                this.gI();
            }
        }
    }

    private void n(boolean bl) {
        if (bl) {
            if (this.qG || ClickPearlModule.aiG.field_1755 != null || ClickPearlModule.aiG.field_1761 == null) {
                return;
            }
            if (this.gF()) {
                return;
            }
            this.qG = true;
            this.gG();
        } else {
            this.qG = false;
        }
    }

    private boolean gF() {
        return this.sL > 0 || this.sK > 0 || this.sI > 0 || this.sJ > 0;
    }

    private void gG() {
        int n;
        int n2;
        int n3 = InventoryUtils.h(class_1802.field_8634);
        int n4 = n2 = n3 != -1 ? n3 : InventoryUtils.i(class_1802.field_8634);
        if (n2 == -1) {
            ChatOutput.b(class_2561.method_43470((String)"ClickPearl: \u043d\u0435\u0442 \u044d\u043d\u0434\u0435\u0440 \u043f\u0435\u0440\u043b\u043e\u0432").method_27692(class_124.field_1061));
            return;
        }
        this.sG = ClickPearlModule.aiG.field_1724.method_31548().field_7545;
        if (n2 <= 8) {
            this.sH = n2;
            this.sI = 1;
            return;
        }
        this.sH = n = this.gK();
        this.sM = n2;
        this.sN = n;
        this.fZ();
        if (this.qN) {
            this.sL = 1;
        } else {
            this.gH();
            this.sI = 1;
        }
    }

    private void gH() {
        if (this.sM < 0) {
            return;
        }
        int n = InventoryUtils.bk(this.sM);
        InventoryUtils.b(n, this.sN, class_1713.field_7791);
        this.sM = -1;
        this.sN = -1;
    }

    private void gI() {
        if (this.sH < 0 || this.sH > 8 || !ClickPearlModule.aiG.field_1724.method_31548().method_5438(this.sH).method_31574(class_1802.field_8634)) {
            ChatOutput.b(class_2561.method_43470((String)"ClickPearl: \u043d\u0435\u0442 \u044d\u043d\u0434\u0435\u0440 \u043f\u0435\u0440\u043b\u043e\u0432").method_27692(class_124.field_1061));
            this.resetState();
            return;
        }
        this.t(this.sH);
        ClickPearlModule.aiG.field_1761.method_2919((class_1657)ClickPearlModule.aiG.field_1724, class_1268.field_5808);
        ClickPearlModule.aiG.field_1724.method_6104(class_1268.field_5808);
        this.sJ = 2;
    }

    private void gJ() {
        if (this.sG >= 0 && this.sG <= 8) {
            this.t(this.sG);
        }
        this.resetState();
    }

    private int gK() {
        return Math.max(0, Math.min(8, Math.round(((Float)this.sF.jT()).floatValue()) - 1));
    }

    private void t(int n) {
        ClickPearlModule.aiG.field_1724.method_31548().field_7545 = n;
        ((IClientPlayerInteractionManager)ClickPearlModule.aiG.field_1761).syncSelectedSlot$drug();
    }

    private void fZ() {
        this.qN = false;
        if (ClickPearlModule.aiG.field_1724.method_7337()) {
            return;
        }
        if (ClickPearlModule.aiG.field_1755 == null) {
            aiG.method_1507((class_437)new class_490((class_1657)ClickPearlModule.aiG.field_1724));
            this.qN = true;
        }
    }

    private void gd() {
        if (ClickPearlModule.aiG.field_1755 instanceof class_490 && ClickPearlModule.aiG.field_1724 != null) {
            ClickPearlModule.aiG.field_1724.method_7346();
            aiG.method_1507(null);
        }
        this.qN = false;
    }

    private void resetState() {
        if (this.qN) {
            this.gd();
        }
        this.sI = 0;
        this.sJ = 0;
        this.sK = 0;
        this.sL = 0;
        this.sM = -1;
        this.sN = -1;
        this.sG = -1;
        this.sH = -1;
        this.qN = false;
        this.qG = false;
    }
}

