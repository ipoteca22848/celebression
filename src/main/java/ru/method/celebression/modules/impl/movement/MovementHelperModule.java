package ru.method.celebression.modules.impl.movement;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.movement.ClickGuiWalkModule;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.screen.clickgui.ClickGui;

public class MovementHelperModule
extends Module {
    private int xw = -1;
    private int xx = -1;

    public MovementHelperModule() {
        super("MovementHelper", ModuleCategory.qk);
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (MovementHelperModule.fullNullCheck() || MovementHelperModule.aiG.field_1755 != null || keyInputEvent.getAction() != 1) {
            return;
        }
        int n = keyInputEvent.c();
        int n2 = this.c(MovementHelperModule.aiG.field_1690.field_1894);
        int n3 = this.c(MovementHelperModule.aiG.field_1690.field_1881);
        int n4 = this.c(MovementHelperModule.aiG.field_1690.field_1913);
        int n5 = this.c(MovementHelperModule.aiG.field_1690.field_1849);
        if (n == n2 || n == n3) {
            this.xw = n;
        }
        if (n == n4 || n == n5) {
            this.xx = n;
        }
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        Object object;
        if (MovementHelperModule.fullNullCheck()) {
            return;
        }
        ClickGuiWalkModule clickGuiWalkModule = Celebression.getInstance().getModuleManager().getModule(ClickGuiWalkModule.class);
        if (clickGuiWalkModule != null && clickGuiWalkModule.isToggled() && MovementHelperModule.aiG.field_1755 instanceof ClickGui && !((ClickGui)(object = (ClickGui)MovementHelperModule.aiG.field_1755)).getSearchBar().isFocused()) {
            return;
        }
        if (MovementHelperModule.aiG.field_1755 != null) {
            return;
        }
        class_304 forwardKey = MovementHelperModule.aiG.field_1690.field_1894;
        class_304 class_3042 = MovementHelperModule.aiG.field_1690.field_1881;
        class_304 class_3043 = MovementHelperModule.aiG.field_1690.field_1913;
        class_304 class_3044 = MovementHelperModule.aiG.field_1690.field_1849;
        this.b(forwardKey);
        this.b(class_3042);
        this.b(class_3043);
        this.b(class_3044);
        int n = this.c(forwardKey);
        int n2 = this.c(class_3042);
        int n3 = this.c(class_3043);
        int n4 = this.c(class_3044);
        if (forwardKey.method_1434() && class_3042.method_1434()) {
            if (this.xw == n) {
                class_3042.method_23481(false);
            } else if (this.xw == n2) {
                forwardKey.method_23481(false);
            }
        }
        if (class_3043.method_1434() && class_3044.method_1434()) {
            if (this.xx == n3) {
                class_3044.method_23481(false);
            } else if (this.xx == n4) {
                class_3043.method_23481(false);
            }
        }
    }

    private void b(class_304 class_3042) {
        if (aiG.method_22683() == null || class_3042 == null) {
            return;
        }
        class_3675.class_306 class_3062 = class_3675.method_15981((String)class_3042.method_1428());
        int n = class_3062.method_1444();
        if (n < 0) {
            class_3042.method_23481(false);
            return;
        }
        long l = aiG.method_22683().method_4490();
        boolean bl = class_3062.method_1442() == class_3675.class_307.field_1672 ? GLFW.glfwGetMouseButton((long)l, (int)n) == 1 : class_3675.method_15987((long)l, (int)n);
        class_3042.method_23481(bl);
    }

    private int c(class_304 class_3042) {
        if (class_3042 == null) {
            return -1;
        }
        class_3675.class_306 class_3062 = class_3675.method_15981((String)class_3042.method_1428());
        if (class_3062.method_1442() == class_3675.class_307.field_1672) {
            return -1;
        }
        return class_3062.method_1444();
    }

    @Override
    public void onDisable() {
        this.xw = -1;
        this.xx = -1;
        if (MovementHelperModule.aiG.field_1690 != null) {
            this.b(MovementHelperModule.aiG.field_1690.field_1894);
            this.b(MovementHelperModule.aiG.field_1690.field_1881);
            this.b(MovementHelperModule.aiG.field_1690.field_1913);
            this.b(MovementHelperModule.aiG.field_1690.field_1849);
        }
        super.onDisable();
    }
}
