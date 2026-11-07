package unkw.visualtest.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10017;
import net.minecraft.class_10034;
import net.minecraft.class_10042;
import net.minecraft.class_10055;
import net.minecraft.class_10069;
import net.minecraft.class_10366;
import net.minecraft.class_1044;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_156;
import net.minecraft.class_1921;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_276;
import net.minecraft.class_2960;
import net.minecraft.class_308;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4048;
import net.minecraft.class_4050;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_572;
import net.minecraft.class_6367;
import net.minecraft.class_897;
import net.minecraft.class_898;
import net.minecraft.class_9851;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public final class HudEntityPreview {
    private static final class_2960 PREVIEW_TEXTURE = class_2960.method_60655((String)"visual-test", (String)"runtime/entity_preview");
    private static final int PREVIEW_SIZE = 160;
    private static class_6367 previewFramebuffer;
    private static boolean rendering;
    private static class_276 framebufferOverride;
    private static final Matrix4f ORTHO_PROJECTION;
    private static final Matrix4f OLD_PROJECTION;
    private static final Quaternionf ROTATION;
    private static final Quaternionf PREVIOUS_ROTATION;
    private static final Vector3f TRANSLATION;
    private static final Quaternionf CAMERA_INVERSE;

    private HudEntityPreview() {
    }

    public static class_276 getFramebufferOverride() {
        return framebufferOverride;
    }

        public static void render(class_332 class_3322, class_310 class_3102, class_1309 class_13092, int n, int n2, int n3, int n4) {
        if (rendering || class_13092 == null || class_13092.method_31481() || n3 < 4 || n4 < 4) {
            return;
        }
        class_3322.method_51452();
        rendering = true;
        try {
            HudEntityPreview.ensureFramebuffer(class_3102);
            HudEntityPreview.renderOffscreen(class_3102, class_13092);
            class_3322.method_25302(class_1921::method_62277, PREVIEW_TEXTURE, n, n2, 0.0f, 160.0f, n3, n4, 160, -160, 160, 160);
            class_3322.method_51452();
        }
        finally {
            rendering = false;
        }
    }

    private static void ensureFramebuffer(class_310 class_3102) {
        if (previewFramebuffer != null) {
            return;
        }
        previewFramebuffer = new class_6367(160, 160, true);
        previewFramebuffer.method_1236(0.0f, 0.0f, 0.0f, 0.0f);
        previewFramebuffer.method_58226(9729);
        class_3102.method_1531().method_4616(PREVIEW_TEXTURE, (class_1044)new FramebufferTexture(previewFramebuffer));
    }

        private static void renderOffscreen(class_310 class_3102, class_1309 class_13092) {
        class_276 class_2762 = class_3102.method_1522();
        Matrix4f matrix4f = OLD_PROJECTION.set((Matrix4fc)RenderSystem.getProjectionMatrix());
        class_10366 class_103662 = RenderSystem.getProjectionType();
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        boolean bl = GL11.glIsEnabled((int)2929);
        boolean bl2 = GL11.glGetBoolean((int)2930);
        boolean bl3 = false;
        class_332 class_3322 = null;
        try {
            RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            previewFramebuffer.method_1230();
            previewFramebuffer.method_1235(true);
            framebufferOverride = previewFramebuffer;
            RenderSystem.setProjectionMatrix((Matrix4f)ORTHO_PROJECTION, (class_10366)class_10366.field_54954);
            matrix4fStack.pushMatrix();
            bl3 = true;
            matrix4fStack.translation(0.0f, 0.0f, -11000.0f);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask((boolean)true);
            class_3322 = new class_332(class_3102, class_3102.method_22940().method_23000());
            HudEntityPreview.renderModel(class_3322, class_3102, class_13092, 0, 0, 160, 160);
        }
        finally {
            try {
                if (class_3322 != null) {
                    class_3322.method_51452();
                }
            }
            finally {
                framebufferOverride = null;
                if (bl3) {
                    matrix4fStack.popMatrix();
                }
                previewFramebuffer.method_1240();
                class_2762.method_1235(true);
                RenderSystem.setProjectionMatrix((Matrix4f)matrix4f, (class_10366)class_103662);
                if (bl) {
                    RenderSystem.enableDepthTest();
                } else {
                    RenderSystem.disableDepthTest();
                }
                RenderSystem.depthMask((boolean)bl2);
                class_308.method_24211();
            }
        }
    }

        private static void renderModel(class_332 class_3322, class_310 class_3102, class_1309 class_13092, int n, int n2, int n3, int n4) {
        class_4048 class_40482 = class_13092.method_18377(class_4050.field_18076);
        float f = Math.max(0.2f, class_40482.comp_2186());
        float f2 = Math.max(0.2f, class_40482.comp_2185());
        float f3 = (float)Math.min(n3, n4) / 50.0f;
        float f4 = Math.max(f2, f * 0.6f);
        float f5 = Math.min(36.0f * f3 / f, 30.0f * f3 / f4);
        float f6 = Math.max(0.1f, Math.min(24.0f * f3, f5));
        float f7 = (float)n2 + 41.0f * f3;
        float f8 = f7 - f * f6 * 0.5f;
        float f9 = -0.41887903f;
        float f10 = (float)(class_156.method_658() % 30000L) / 30000.0f * ((float)Math.PI * 2);
        float f11 = 1.5707964f + f10;
        Quaternionf quaternionf = ROTATION.identity().rotateZ((float)Math.PI).rotateX(f9).rotateY(f11);
        Vector3f vector3f = TRANSLATION.set(0.0f, f / 2.0f, 0.0f);
        class_4587 class_45872 = class_3322.method_51448();
        class_898 class_8982 = class_3102.method_1561();
        Quaternionf quaternionf2 = PREVIOUS_ROTATION.set((Quaternionfc)class_8982.method_24197());
        class_45872.method_22903();
        try {
            class_45872.method_46416((float)n + (float)n3 / 2.0f, f8, 50.0f);
            class_45872.method_22905(f6, f6, -f6);
            class_45872.method_46416(vector3f.x, vector3f.y, vector3f.z);
            class_45872.method_22907(quaternionf);
            class_3322.method_51452();
            class_308.method_34742();
            class_8982.method_24196(CAMERA_INVERSE);
            class_3322.method_64039(class_45972 -> HudEntityPreview.renderClean(class_8982.method_3953((class_1297)class_13092), class_13092, class_45872, class_45972));
            class_3322.method_51452();
        }
        finally {
            class_8982.method_24196(quaternionf2);
            class_45872.method_22909();
            class_308.method_24211();
        }
    }

        private static <E extends class_1297, S extends class_10017> void renderClean(class_897<? super E, S> class_8972, E e, class_4587 class_45872, class_4597 class_45972) {
        S class_100172 = class_8972.method_62425(e, 1.0f);
        boolean bl = class_100172.field_53333;
        boolean bl2 = class_100172.field_53335;
        boolean bl3 = class_100172.field_53334;
        float f = class_100172.field_53328;
        class_2561 class_25612 = class_100172.field_53337;
        class_243 class_2432 = class_100172.field_53338;
        class_10017.class_10018 class_100182 = class_100172.field_53324;
        class_100172.field_53333 = false;
        class_100172.field_53335 = false;
        class_100172.field_53334 = false;
        class_100172.field_53328 = 0.0f;
        class_100172.field_53337 = null;
        class_100172.field_53338 = null;
        class_100172.field_53324 = null;
        class_45872.method_22903();
        try {
            if (class_100172 instanceof class_10042) {
                class_10042 class_100422 = (class_10042)class_100172;
                HudEntityPreview.renderLiving(class_8972, class_100172, class_45872, class_45972, class_100422);
            } else {
                class_8972.method_3936(class_100172, class_45872, class_45972, 0xF000F0);
            }
        }
        finally {
            class_45872.method_22909();
            class_100172.field_53333 = bl;
            class_100172.field_53335 = bl2;
            class_100172.field_53334 = bl3;
            class_100172.field_53328 = f;
            class_100172.field_53337 = class_25612;
            class_100172.field_53338 = class_2432;
            class_100172.field_53324 = class_100182;
        }
    }

        private static <E extends class_1297, S extends class_10017> void renderLiving(class_897<? super E, S> class_8972, S s, class_4587 class_45872, class_4597 class_45972, class_10042 class_100422) {
        boolean bl = class_100422.field_53460;
        boolean bl2 = class_100422.field_53462;
        boolean bl3 = class_100422.field_53461;
        boolean bl4 = class_100422.field_53456;
        float f = class_100422.field_53449;
        float f2 = class_100422.field_53446;
        float f3 = class_100422.field_53447;
        float f4 = class_100422.field_53448;
        float f5 = class_100422.field_53450;
        float f6 = class_100422.field_53451;
        float f7 = class_100422.field_53452;
        boolean bl5 = class_100422.field_53459;
        boolean bl6 = class_100422.field_53455;
        class_2350 class_23502 = class_100422.field_53463;
        class_4050 class_40502 = class_100422.field_53465;
        class_100422.field_53460 = false;
        class_100422.field_53462 = false;
        class_100422.field_53461 = false;
        class_100422.field_53456 = false;
        class_100422.field_53449 = 0.0f;
        class_100422.field_53446 = 180.0f;
        class_100422.field_53447 = 0.0f;
        class_100422.field_53448 = 0.0f;
        class_100422.field_53450 = 0.0f;
        class_100422.field_53451 = 0.0f;
        class_100422.field_53452 = 0.0f;
        class_100422.field_53459 = false;
        class_100422.field_53455 = false;
        class_100422.field_53463 = null;
        class_100422.field_53465 = class_4050.field_18076;
        try {
            if (class_100422 instanceof class_10055) {
                class_10055 class_100552 = (class_10055)class_100422;
                HudEntityPreview.renderPlayer(class_8972, s, class_45872, class_45972, class_100552);
            } else if (class_100422 instanceof class_10034) {
                class_10034 class_100342 = (class_10034)class_100422;
                HudEntityPreview.renderBiped(class_8972, s, class_45872, class_45972, class_100342);
            } else if (class_100422 instanceof class_10069) {
                class_10069 class_100692 = (class_10069)class_100422;
                HudEntityPreview.renderSquid(class_8972, s, class_45872, class_45972, class_100692);
            } else {
                class_8972.method_3936(s, class_45872, class_45972, 0xF000F0);
            }
        }
        finally {
            class_100422.field_53460 = bl;
            class_100422.field_53462 = bl2;
            class_100422.field_53461 = bl3;
            class_100422.field_53456 = bl4;
            class_100422.field_53449 = f;
            class_100422.field_53446 = f2;
            class_100422.field_53447 = f3;
            class_100422.field_53448 = f4;
            class_100422.field_53450 = f5;
            class_100422.field_53451 = f6;
            class_100422.field_53452 = f7;
            class_100422.field_53459 = bl5;
            class_100422.field_53455 = bl6;
            class_100422.field_53463 = class_23502;
            class_100422.field_53465 = class_40502;
        }
    }

        private static <E extends class_1297, S extends class_10017> void renderPlayer(class_897<? super E, S> class_8972, S s, class_4587 class_45872, class_4597 class_45972, class_10055 class_100552) {
        float f = class_100552.field_53536;
        float f2 = class_100552.field_53537;
        float f3 = class_100552.field_53538;
        int n = class_100552.field_53541;
        float f4 = class_100552.field_53534;
        boolean bl = class_100552.field_53535;
        float f5 = class_100552.field_53521;
        boolean bl2 = class_100552.field_53522;
        int n2 = class_100552.field_53539;
        class_100552.field_53536 = 0.0f;
        class_100552.field_53537 = 0.0f;
        class_100552.field_53538 = 0.0f;
        class_100552.field_53541 = 0;
        class_100552.field_53534 = 0.0f;
        class_100552.field_53535 = false;
        class_100552.field_53521 = 0.0f;
        class_100552.field_53522 = false;
        class_100552.field_53539 = 0;
        try {
            HudEntityPreview.renderBiped(class_8972, s, class_45872, class_45972, (class_10034)class_100552);
        }
        finally {
            class_100552.field_53536 = f;
            class_100552.field_53537 = f2;
            class_100552.field_53538 = f3;
            class_100552.field_53541 = n;
            class_100552.field_53534 = f4;
            class_100552.field_53535 = bl;
            class_100552.field_53521 = f5;
            class_100552.field_53522 = bl2;
            class_100552.field_53539 = n2;
        }
    }

        private static <E extends class_1297, S extends class_10017> void renderBiped(class_897<? super E, S> class_8972, S s, class_4587 class_45872, class_4597 class_45972, class_10034 class_100342) {
        float f = class_100342.field_53403;
        float f2 = class_100342.field_53404;
        float f3 = class_100342.field_53405;
        float f4 = class_100342.field_53406;
        int n = class_100342.field_53407;
        boolean bl = class_100342.field_53410;
        boolean bl2 = class_100342.field_53411;
        boolean bl3 = class_100342.field_53412;
        boolean bl4 = class_100342.field_53413;
        boolean bl5 = class_100342.field_53414;
        float f5 = class_100342.field_53415;
        float f6 = class_100342.field_53416;
        float f7 = class_100342.field_53417;
        class_572.class_573 class_5732 = class_100342.field_55304;
        class_572.class_573 class_5733 = class_100342.field_55306;
        class_100342.field_53403 = 0.0f;
        class_100342.field_53404 = 0.0f;
        class_100342.field_53405 = 1.0f;
        class_100342.field_53406 = 0.0f;
        class_100342.field_53407 = 0;
        class_100342.field_53410 = false;
        class_100342.field_53411 = false;
        class_100342.field_53412 = false;
        class_100342.field_53413 = false;
        class_100342.field_53414 = false;
        class_100342.field_53415 = 0.2617994f;
        class_100342.field_53416 = 0.0f;
        class_100342.field_53417 = -0.2617994f;
        class_100342.field_55304 = class_100342.field_55305.method_65606() ? class_572.class_573.field_3409 : class_572.class_573.field_3410;
        class_100342.field_55306 = class_100342.field_55307.method_65606() ? class_572.class_573.field_3409 : class_572.class_573.field_3410;
        try {
            class_8972.method_3936(s, class_45872, class_45972, 0xF000F0);
        }
        finally {
            class_100342.field_53403 = f;
            class_100342.field_53404 = f2;
            class_100342.field_53405 = f3;
            class_100342.field_53406 = f4;
            class_100342.field_53407 = n;
            class_100342.field_53410 = bl;
            class_100342.field_53411 = bl2;
            class_100342.field_53412 = bl3;
            class_100342.field_53413 = bl4;
            class_100342.field_53414 = bl5;
            class_100342.field_53415 = f5;
            class_100342.field_53416 = f6;
            class_100342.field_53417 = f7;
            class_100342.field_55304 = class_5732;
            class_100342.field_55306 = class_5733;
        }
    }

        private static <E extends class_1297, S extends class_10017> void renderSquid(class_897<? super E, S> class_8972, S s, class_4587 class_45872, class_4597 class_45972, class_10069 class_100692) {
        float f = class_100692.field_53582;
        float f2 = class_100692.field_53583;
        float f3 = class_100692.field_53584;
        class_100692.field_53582 = 0.0f;
        class_100692.field_53583 = 0.0f;
        class_100692.field_53584 = 0.0f;
        try {
            class_8972.method_3936(s, class_45872, class_45972, 0xF000F0);
        }
        finally {
            class_100692.field_53582 = f;
            class_100692.field_53583 = f2;
            class_100692.field_53584 = f3;
        }
    }

    static {
        ORTHO_PROJECTION = new Matrix4f().setOrtho(0.0f, 160.0f, 160.0f, 0.0f, 1000.0f, 21000.0f);
        OLD_PROJECTION = new Matrix4f();
        ROTATION = new Quaternionf();
        PREVIOUS_ROTATION = new Quaternionf();
        TRANSLATION = new Vector3f();
        CAMERA_INVERSE = new Quaternionf().rotateX(-0.41887903f).conjugate().rotateY((float)Math.PI);
    }

    static final class FramebufferTexture
    extends class_1044 {
        private final class_6367 framebuffer;

        FramebufferTexture(class_6367 class_63672) {
            this.framebuffer = class_63672;
        }

        public int method_4624() {
            return this.framebuffer.method_30277();
        }

        public void method_65923(class_9851 class_98512, boolean bl) {
        }

        public void method_4527(boolean bl, boolean bl2) {
        }

        public void method_4528() {
        }

        public void close() {
        }
    }
}
