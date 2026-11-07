package ru.method.celebression.modules.impl.movement;

import java.util.ArrayList;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.modules.impl.movement.SprintModule;
import ru.method.celebression.screen.clickgui.ClickGui;

public class ClickGuiWalkModule
extends Module {
    public ClickGuiWalkModule() {
        super("ClickGuiWalk", ModuleCategory.qk);
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (ClickGuiWalkModule.fullNullCheck()) {
            return;
        }
        class_437 class_4372 = ClickGuiWalkModule.aiG.field_1755;
        if (class_4372 instanceof ClickGui) {
            ClickGui clickGui = (ClickGui)class_4372;
            if (!clickGui.getSearchBar().isFocused()) {
                this.hW();
            }
        } else {
            LinkGuiScreen linkGuiScreen;
            class_4372 = ClickGuiWalkModule.aiG.field_1755;
            if (class_4372 instanceof LinkGuiScreen && !(linkGuiScreen = (LinkGuiScreen)class_4372).nK()) {
                this.hW();
            }
        }
    }

    private void hW() {
        SprintModule sprintModule = Celebression.getInstance().getModuleManager().getModule(SprintModule.class);
        boolean bl = sprintModule == null || !sprintModule.isToggled();
        ArrayList<class_304> arrayList = new ArrayList<class_304>();
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1894);
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1881);
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1913);
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1849);
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1903);
        arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1832);
        if (bl) {
            arrayList.add(ClickGuiWalkModule.aiG.field_1690.field_1867);
        }
        for (class_304 class_3042 : arrayList) {
            class_3042.method_23481(this.a(class_3042));
        }
    }

    private boolean a(class_304 class_3042) {
        if (aiG.method_22683() == null || class_3042 == null) {
            return false;
        }
        class_3675.class_306 class_3062 = class_3675.method_15981((String)class_3042.method_1428());
        int n = class_3062.method_1444();
        if (n < 0) {
            return false;
        }
        long l = aiG.method_22683().method_4490();
        if (class_3062.method_1442() == class_3675.class_307.field_1672) {
            return GLFW.glfwGetMouseButton((long)l, (int)n) == 1;
        }
        return class_3675.method_15987((long)l, (int)n);
    }
}

