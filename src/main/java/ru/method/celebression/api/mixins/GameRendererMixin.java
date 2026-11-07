package ru.method.celebression.api.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_1297;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.AspectRatioModule;
import ru.method.celebression.modules.impl.render.FogBlurModule;
import ru.method.celebression.ui.LinkGuiRenderState;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.modules.impl.render.cosmetic.CelebressionCapeRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.modules.impl.render.MotionBlurModule;
import ru.method.celebression.modules.impl.render.NoRenderModule;
import ru.method.celebression.api.event.RaycastRotationEvent;
import ru.method.celebression.ui.UiScaleMode;
import ru.method.celebression.api.event.WorldRenderEvent;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.ZoomModule;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.ClickGuiRenderContext;
import ru.method.celebression.utils.render.HandsRenderer;
import ru.method.celebression.utils.render.Render3D;
import ru.method.celebression.utils.render.WorldSnapshotCache;

@Mixin(value={class_757.class})
public abstract class GameRendererMixin
implements MinecraftClientAccess {
    @Shadow
    private float field_4005;
    @Shadow
    private float field_3988;
    @Shadow
    private float field_4004;
    @Shadow
    private float field_4025;
    @Unique
    private Matrix4f celebression$projectionMatrix;
    @Unique
    private Matrix4f celebression$perspectiveMatrix;
    @Unique
    private static int celebression$modelViewDepth = 0;

    @Shadow
    protected abstract void method_3172(class_4184 var1, float var2, Matrix4f var3);

    @Unique
    private static void celebression$cleanupModelViewStack() {
        if (celebression$modelViewDepth <= 0) {
            return;
        }
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        int n = celebression$modelViewDepth;
        celebression$modelViewDepth = 0;
        for (int i = 0; i < n; ++i) {
            try {
                matrix4fStack.popMatrix();
                continue;
            }
            catch (Exception exception) {
                break;
            }
        }
    }

    @Inject(method={"renderWorld"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderWorld(class_9779 class_97792, CallbackInfo callbackInfo) {
        CelebressionCapeRenderer.I();
        RenderSystem.activeTexture((int)33984);
        int n = class_310.method_1551().method_1522().method_30277();
        RenderSystem.bindTexture((int)n);
        RenderSystem.bindTexture((int)0);
        RenderSystem.setShaderTexture((int)0, (int)0);
        class_310 class_3102 = class_310.method_1551();
        boolean bl = class_3102.field_1755 instanceof LinkGuiScreen;
        if (bl) {
            UiScaleMode uiScaleMode = UI.getBoostFpsMode();
            if (uiScaleMode == UiScaleMode.qs) {
                if (WorldSnapshotCache.isValid()) {
                    WorldSnapshotCache.blitTo(class_3102.method_1522());
                    callbackInfo.cancel();
                    return;
                }
            } else {
                WorldSnapshotCache.invalidate();
            }
        } else {
            WorldSnapshotCache.invalidate();
        }
        GameRendererMixin.celebression$cleanupModelViewStack();
        Render3D.prepare();
    }

    @Inject(method={"renderWorld"}, at={@At(value="TAIL")})
    private void celebression$motionBlur(class_9779 class_97792, CallbackInfo callbackInfo) {
        MotionBlurModule motionBlurModule = Celebression.getInstance().getModuleManager().getModule(MotionBlurModule.class);
        if (motionBlurModule != null && motionBlurModule.isToggled()) {
            motionBlurModule.iH();
        }
    }

    @Inject(method={"renderWorld"}, at={@At(value="TAIL")})
    private void celebression$captureWorldSnapshot(class_9779 class_97792, CallbackInfo callbackInfo) {
        class_310 class_3102 = class_310.method_1551();
        if (UI.getBoostFpsMode() == UiScaleMode.qs && class_3102.field_1755 instanceof LinkGuiScreen && !WorldSnapshotCache.isValid()) {
            WorldSnapshotCache.capture(class_3102.method_1522());
        }
    }

    @Redirect(method={"renderWorld"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/GameRenderer;renderHand(Lnet/minecraft/client/render/Camera;FLorg/joml/Matrix4f;)V"))
    private void celebression$renderShaderHands(class_757 class_7572, class_4184 class_41842, float f, Matrix4f matrix4f) {
        if (!HandsRenderer.isActive()) {
            this.method_3172(class_41842, f, matrix4f);
            return;
        }
        RenderSystem.colorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        class_310.method_1551().method_1522().method_1235(false);
        RenderSystem.depthMask((boolean)true);
        GL11.glClear((int)256);
        this.method_3172(class_41842, f, matrix4f);
        RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        HandsRenderer.render(this.field_4025 * 4.0f);
    }

        @Inject(at={@At(value="FIELD", target="Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode=180, ordinal=0)}, method={"renderWorld"})
    public void onRenderWorld(class_9779 class_97792, CallbackInfo callbackInfo, @Local(ordinal=2) Matrix4f matrix4f, @Local(ordinal=1) float f, @Local class_4587 class_45872) {
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        boolean bl = false;
        try {
            boolean bl2;
            Object object;
            matrix4fStack.pushMatrix();
            bl = true;
            ++celebression$modelViewDepth;
            matrix4fStack.mul((Matrix4fc)matrix4f);
            boolean bl3 = ClickGuiRenderContext.isClickGuiOpen();
            Render3D.setLastProjectionMatrix(matrix4f);
            Render3D.setLastWorldSpaceMatrix(class_45872.method_23760().method_56822());
            if (!bl3) {
                object = new WorldRenderStages.MatrixStackStage(class_97792, class_45872);
                Celebression.getInstance().getEventHandler().post(object);
                LinkGuiRenderState.oo().f(class_45872);
            }
            boolean bl4 = bl2 = (object = Celebression.getInstance().getModuleManager().getModule(FogBlurModule.class)) != null && ((Module)object).isToggled() && GameRendererMixin.aiG.field_1724 != null && !GameRendererMixin.aiG.field_1773.method_35765();
            if (bl2) {
                if (!bl3) {
                    Render3D.drawDepthTexturesPreFog();
                    Render3D.drawDepth(Render3D.DEPTH_QUADS, Render3D.DEPTH_DEBUG_LINES, false);
                    Render3D.drawLightning();
                }
                ((FogBlurModule)object).iH();
            }
            if (bl3) {
                return;
            }
            if (!bl2) {
                Render3D.drawDepth(Render3D.DEPTH_QUADS, Render3D.DEPTH_DEBUG_LINES, false);
                Render3D.drawLightning();
            }
            Render3D.draw(Render3D.QUADS, Render3D.DEBUG_LINES, false);
            Render3D.drawVariableWidthLines(true);
            Render3D.drawVariableWidthLines(false);
            Render3D.drawTextures();
            Render3D.drawDepth(Render3D.DEPTH_SHINE_QUADS, Render3D.DEPTH_SHINE_DEBUG_LINES, true);
            Render3D.draw(Render3D.SHINE_QUADS, Render3D.SHINE_DEBUG_LINES, true);
            try {
                Celebression.getInstance().getEventHandler().post((Object)new WorldRenderEvent(class_97792, class_45872));
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("Error in EventRenderWorld:", (Throwable)exception);
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("Error in onRenderWorld:", (Throwable)exception);
        }
        finally {
            try {
                if (bl) {
                    matrix4fStack.popMatrix();
                    celebression$modelViewDepth = Math.max(0, celebression$modelViewDepth - 1);
                }
            }
            catch (Exception exception) {
                Celebression.LOGGER.error("Error popping matrix:", (Throwable)exception);
                GameRendererMixin.celebression$cleanupModelViewStack();
            }
        }
    }

    @Inject(method={"showFloatingItem"}, at={@At(value="HEAD")}, cancellable=true)
    public void showFloatingItem(class_1799 class_17992, CallbackInfo callbackInfo) {
        NoRenderModule noRenderModule = Celebression.getInstance().getModuleManager().getModule(NoRenderModule.class);
        if (noRenderModule != null && noRenderModule.isToggled() && ((Boolean)noRenderModule.wH.jT()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"tiltViewWhenHurt"}, at={@At(value="HEAD")}, cancellable=true)
    public void tiltViewWhenHurt(CallbackInfo callbackInfo) {
        NoRenderModule noRenderModule = Celebression.getInstance().getModuleManager().getModule(NoRenderModule.class);
        if (noRenderModule != null && noRenderModule.isToggled() && ((Boolean)noRenderModule.Fa.jT()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"getBasicProjectionMatrix"}, at={@At(value="TAIL")}, cancellable=true)
    public void getBasicProjectionMatrixHook(float f, CallbackInfoReturnable<Matrix4f> callbackInfoReturnable) {
        AspectRatioModule aspectRatioModule = Celebression.getInstance().getModuleManager().getModule(AspectRatioModule.class);
        if (aspectRatioModule != null && aspectRatioModule.isToggled()) {
            Matrix4f matrix4f;
            Matrix4f matrix4f2 = this.celebression$projectionMatrix;
            if (matrix4f2 == null) {
                this.celebression$projectionMatrix = matrix4f2 = new Matrix4f();
            }
            matrix4f2.identity();
            if (this.field_4005 != 1.0f) {
                matrix4f2.translate(this.field_3988, -this.field_4004, 0.0f);
                matrix4f2.scale(this.field_4005, this.field_4005, 1.0f);
            }
            if ((matrix4f = this.celebression$perspectiveMatrix) == null) {
                this.celebression$perspectiveMatrix = matrix4f = new Matrix4f();
            }
            matrix4f.setPerspective((float)((double)f * 0.01745329238474369), ((Float)aspectRatioModule.yP.jT()).floatValue(), 0.05f, this.field_4025 * 4.0f);
            matrix4f2.mul((Matrix4fc)matrix4f);
            callbackInfoReturnable.setReturnValue(new Matrix4f((Matrix4fc)matrix4f2));
        }
    }

    @Inject(method={"getFov"}, at={@At(value="RETURN")}, cancellable=true)
    private void celebression$getFov(class_4184 class_41842, float f, boolean bl, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        ZoomModule zoomModule = Celebression.getInstance().getModuleManager().getModule(ZoomModule.class);
        if (zoomModule == null || !zoomModule.isToggled()) {
            return;
        }
        float f2 = zoomModule.aa(f);
        if (f2 == 1.0f) {
            return;
        }
        callbackInfoReturnable.setReturnValue(Float.valueOf(callbackInfoReturnable.getReturnValueF() * f2));
    }

    @WrapOperation(method={"findCrosshairTarget"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;")})
    public class_243 findCrosshairTarget(class_1297 class_12972, float f, Operation<class_243> operation) {
        if (class_12972 == GameRendererMixin.aiG.field_1724) {
            RaycastRotationEvent raycastRotationEvent = new RaycastRotationEvent(class_12972.method_36454(), class_12972.method_36455());
            Celebression.getInstance().getEventHandler().post((Object)raycastRotationEvent);
            if (raycastRotationEvent.isCancelled()) {
                return class_12972.method_5631(raycastRotationEvent.s(), raycastRotationEvent.r());
            }
        }
        return (class_243)operation.call(new Object[]{class_12972, Float.valueOf(f)});
    }
}

