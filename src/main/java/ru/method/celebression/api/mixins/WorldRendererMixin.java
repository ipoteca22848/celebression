package ru.method.celebression.api.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_4184;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_9779;
import net.minecraft.class_9922;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.Celebression;
import ru.method.celebression.render.ProjectionUtils;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ClickGuiRenderContext;

@Mixin(value={class_761.class})
public abstract class WorldRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    public void render(class_9922 class_99222, class_9779 class_97792, boolean bl, class_4184 class_41842, class_757 class_7572, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo callbackInfo) {
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        Matrix4f matrix4f3 = new Matrix4f((Matrix4fc)matrix4fStack);
        try {
            while (true) {
                matrix4fStack.popMatrix();
            }
        }
        catch (Exception exception) {
            matrix4fStack.set((Matrix4fc)matrix4f3);
            if (ClickGuiRenderContext.isClickGuiOpen()) {
                return;
            }
            WorldRenderStages.CameraStage cameraStage = new WorldRenderStages.CameraStage(class_41842, matrix4f, class_97792);
            Celebression.getInstance().getEventHandler().post((Object)cameraStage);
            ProjectionUtils.amr.get().set((Matrix4fc)matrix4f);
            ProjectionUtils.ams.get().set((Matrix4fc)RenderSystem.getProjectionMatrix());
            ProjectionUtils.amt.get().set((Matrix4fc)RenderSystem.getModelViewMatrix());
            return;
        }
    }
}

