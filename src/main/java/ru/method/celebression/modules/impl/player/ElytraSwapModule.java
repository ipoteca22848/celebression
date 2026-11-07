package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_490;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.inventory.InventoryHelper;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.MouseScrollEvent;
import ru.method.celebression.api.setting.NamedOption;

public class ElytraSwapModule
extends Module {
    private static final int tf = 6;
    private static final int tg = 30;
    private static final int th = 2;
    private static final int ti = 1;
    private static final int tj = 10;
    private final BindSetting tk = new BindSetting("settings.elytraswap.bind", new KeyBind(-1, false));
    private final BooleanSetting tl = new BooleanSetting("settings.elytraswap.fly_command", false);
    private int tm;
    private int tn;
    private int qM;
    private boolean qN;
    private SwapMode tp = SwapMode.tu;
    private int tq = -1;
    private int tr;
    private int ts;
    private boolean tt;

    public ElytraSwapModule() {
        super("ElytraSwap", ModuleCategory.qm);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.gU();
        this.qM = 0;
        this.qN = false;
        InventoryHelper.rN();
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (ElytraSwapModule.aiG.field_1755 != null) {
            return;
        }
        if (keyInputEvent.getAction() == 1 && ((KeyBind)this.tk.jT()).af(keyInputEvent.c())) {
            this.gP();
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (ElytraSwapModule.aiG.field_1755 != null) {
            return;
        }
        if (eventMouse.getAction() == 1 && ((KeyBind)this.tk.jT()).ag(eventMouse.getButton())) {
            eventMouse.consume();
            this.gP();
        }
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent mouseScrollEvent) {
        if (ElytraSwapModule.aiG.field_1755 != null) {
            return;
        }
        if (((KeyBind)this.tk.jT()).c(mouseScrollEvent.h())) {
            mouseScrollEvent.consume();
            this.gP();
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (InventoryHelper.rM()) {
            class_437 class_4372 = ElytraSwapModule.aiG.field_1755;
            if (class_4372 instanceof class_465) {
                class_465 class_4652 = (class_465)class_4372;
                InventoryHelper.d(class_4652);
            } else {
                InventoryHelper.rN();
            }
        }
        this.gR();
        this.ga();
        if (this.tm <= 0) {
            return;
        }
        if (ElytraSwapModule.fullNullCheck() || ElytraSwapModule.aiG.field_1724.field_3944 == null) {
            this.tm = 0;
            this.tn = 0;
            return;
        }
        if (ElytraSwapModule.aiG.field_1724.method_31548().method_7372(2).method_31574(class_1802.field_8833)) {
            if (this.tn > 0) {
                ElytraSwapModule.aiG.field_1724.field_3944.method_45730("fly");
                --this.tn;
            }
            this.tm = this.tn > 0 ? 30 : 0;
            return;
        }
        --this.tm;
        if (this.tm <= 0) {
            this.tn = 0;
        }
    }

    private void gP() {
        int n;
        if (ElytraSwapModule.fullNullCheck() || ElytraSwapModule.aiG.field_1761 == null) {
            return;
        }
        if (this.tp != SwapMode.tu) {
            return;
        }
        class_1799 class_17992 = ElytraSwapModule.aiG.field_1724.method_31548().method_7372(2);
        this.tt = !class_17992.method_31574(class_1802.field_8833);
        int n2 = n = class_17992.method_31574(class_1802.field_8833) ? InventoryUtils.ab(0, 35) : InventoryUtils.a(class_1802.field_8833, 0, 35);
        if (n == -1) {
            return;
        }
        this.tq = InventoryUtils.bk(n);
        this.fZ();
        if (this.qN) {
            this.tp = SwapMode.tv;
            this.ts = 10;
            this.tr = 0;
            return;
        }
        this.tp = SwapMode.tw;
        this.tr = 0;
    }

    private void gQ() {
        if (((Boolean)this.tl.jT()).booleanValue() && this.tt) {
            ++this.tn;
            this.tm = Math.max(this.tm, 30);
        }
    }

    private void gR() {
        if (this.tp == SwapMode.tu) {
            return;
        }
        if (this.tr > 0) {
            --this.tr;
            return;
        }
        if (this.tp == SwapMode.tv) {
            if (ElytraSwapModule.aiG.field_1755 instanceof class_465) {
                this.tp = SwapMode.tw;
                this.tr = 1;
                return;
            }
            if (--this.ts <= 0) {
                this.gT();
            }
            return;
        }
        if (ElytraSwapModule.fullNullCheck() || ElytraSwapModule.aiG.field_1761 == null) {
            this.gT();
            return;
        }
        int n = ElytraSwapModule.aiG.field_1724.field_7498.field_7763;
        switch (this.tp.ordinal()) {
            case 2: {
                this.i(n, this.tq);
                this.tp = SwapMode.tx;
                this.tr = 1;
                break;
            }
            case 3: {
                this.i(n, 6);
                this.tp = SwapMode.ty;
                this.tr = 1;
                break;
            }
            case 4: {
                this.i(n, this.tq);
                if (this.gc()) {
                    this.tp = SwapMode.tz;
                    this.tr = 1;
                    break;
                }
                this.gS();
                break;
            }
            case 5: {
                this.u(n);
                this.gS();
                break;
            }
            default: {
                this.gT();
            }
        }
    }

    private void i(int n, int n2) {
        class_437 class_4372 = ElytraSwapModule.aiG.field_1755;
        if (class_4372 instanceof class_465) {
            class_465 class_4652 = (class_465)class_4372;
            InventoryHelper.b(class_4652, n2);
        } else {
            InventoryUtils.bl(n2);
        }
        InventoryUtils.a(n, n2, 0, class_1713.field_7790);
    }

    private void u(int n) {
        if (!this.gc() || ElytraSwapModule.aiG.field_1724 == null) {
            return;
        }
        for (int i = 0; i < 36; ++i) {
            if (!ElytraSwapModule.aiG.field_1724.method_31548().method_5438(i).method_7960()) continue;
            this.i(n, InventoryUtils.bk(i));
            return;
        }
    }

    private void gS() {
        this.tp = SwapMode.tu;
        this.tq = -1;
        this.tr = 0;
        this.ts = 0;
        if (this.qN) {
            this.qM = 2;
        } else {
            InventoryHelper.rN();
        }
        this.gQ();
    }

    private void gT() {
        this.gU();
        InventoryHelper.rN();
    }

    private void gU() {
        this.tp = SwapMode.tu;
        this.tq = -1;
        this.tr = 0;
        this.ts = 0;
    }

    private void fZ() {
        this.qN = false;
        if (ElytraSwapModule.aiG.field_1724.method_7337()) {
            this.qM = 0;
            return;
        }
        if (ElytraSwapModule.aiG.field_1755 == null) {
            aiG.method_1507((class_437)new class_490((class_1657)ElytraSwapModule.aiG.field_1724));
            this.qN = true;
        }
    }

    private void ga() {
        if (this.qM <= 0) {
            return;
        }
        if (!(ElytraSwapModule.aiG.field_1755 instanceof class_490)) {
            this.qM = 0;
            this.qN = false;
            return;
        }
        if (this.gc() && ElytraSwapModule.aiG.field_1724 != null) {
            this.u(ElytraSwapModule.aiG.field_1724.field_7498.field_7763);
        }
        if (this.gc()) {
            return;
        }
        --this.qM;
        if (this.qM <= 0) {
            this.gd();
        }
    }

    private boolean gc() {
        return ElytraSwapModule.aiG.field_1724 != null && ElytraSwapModule.aiG.field_1724.field_7498 != null && !ElytraSwapModule.aiG.field_1724.field_7498.method_34255().method_7960();
    }

    private void gd() {
        InventoryHelper.rN();
        if (ElytraSwapModule.aiG.field_1724 != null) {
            ElytraSwapModule.aiG.field_1724.method_7346();
        }
        aiG.method_1507(null);
        this.qN = false;
    }

    static enum SwapMode {
        tu,
        tv,
        tw,
        tx,
        ty,
        tz;
}

    static enum SwapState
    implements NamedOption  {
        tB("settings.elytraswap.speed.normal");
        private final String tC;

@Override
        public String getName() {
            return this.tC;
        }

        @Generated
        private SwapState(String string2) {
            this.tC = string2;
        }

}
}

