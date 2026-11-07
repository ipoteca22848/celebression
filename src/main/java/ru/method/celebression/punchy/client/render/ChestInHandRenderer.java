package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2336;
import net.minecraft.class_2338;
import net.minecraft.class_2343;
import net.minecraft.class_2531;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2611;
import net.minecraft.class_2646;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_4739;
import net.minecraft.class_638;
import net.minecraft.class_824;
import net.minecraft.class_827;
import ru.method.celebression.punchy.client.render.ChestInHandContext;
import ru.method.celebression.punchy.client.render.InHandLidPhysics;
import ru.method.celebression.punchy.compat.EmfEtfCemCompat;

public final class ChestInHandRenderer {
    private static final class_2338 ORIGIN = class_2338.field_10980;
    private static final EnumMap<class_1268, Float> LID_OPEN = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, Long> LID_LAST_UPDATE = new EnumMap(class_1268.class);

    private ChestInHandRenderer() {
    }

    public static boolean isChestStack(class_1799 class_17992) {
        return ChestInHandRenderer.isChest(class_17992);
    }

        public static boolean tryRender(class_1799 class_17992, class_1268 class_12682, class_4587 class_45872, class_4597 class_45972, int n) {
        float f;
        boolean bl;
        class_824 class_8242;
        if (!ChestInHandRenderer.isChest(class_17992)) {
            return false;
        }
        class_2680 class_26802 = ChestInHandRenderer.chestState(class_17992);
        if (class_26802 == null) {
            return false;
        }
        class_2586 class_25862 = ChestInHandRenderer.createBlockEntity(class_26802);
        if (class_25862 == null) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return false;
        }
        class_638 class_6382 = class_3102.field_1687;
        if (class_6382 != null) {
            class_25862.method_31662((class_1937)class_6382);
        }
        if ((class_8242 = class_3102.method_31975()) == null) {
            return false;
        }
        class_827 class_8272 = class_8242.method_3550(class_25862);
        if (class_8272 == null) {
            return false;
        }
        EmfEtfCemCompat.ProxyPhysicsTarget proxyPhysicsTarget = EmfEtfCemCompat.resolveChestTarget(class_26802.method_26204());
        boolean bl2 = bl = proxyPhysicsTarget != null && EmfEtfCemCompat.disableProxyPhysics(proxyPhysicsTarget);
        if (bl) {
            f = 0.0f;
        } else {
            float f2 = InHandLidPhysics.computeFallTarget((class_1657)class_3102.field_1724);
            f = InHandLidPhysics.updateLid(LID_OPEN, LID_LAST_UPDATE, class_12682, f2);
        }
        if (class_25862 instanceof PunchyChestBlockEntity) {
            PunchyChestBlockEntity punchyChestBlockEntity = (PunchyChestBlockEntity)class_25862;
            punchyChestBlockEntity.punchy$setOpen(f);
        } else if (class_25862 instanceof PunchyTrappedChestBlockEntity) {
            PunchyTrappedChestBlockEntity punchyTrappedChestBlockEntity = (PunchyTrappedChestBlockEntity)class_25862;
            punchyTrappedChestBlockEntity.punchy$setOpen(f);
        } else if (class_25862 instanceof PunchyEnderChestBlockEntity) {
            PunchyEnderChestBlockEntity punchyEnderChestBlockEntity = (PunchyEnderChestBlockEntity)class_25862;
            punchyEnderChestBlockEntity.punchy$setOpen(f);
        }
        ChestInHandContext.push();
        try {
            class_8272.method_3569(class_25862, 0.0f, class_45872, class_45972, n, class_4608.field_21444);
        }
        finally {
            ChestInHandContext.pop();
        }
        return true;
    }

    private static boolean isChest(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_1792 class_17922 = class_17992.method_7909();
            if (!(class_17922 instanceof class_1747)) {
                return false;
            }
            class_1747 class_17472 = (class_1747)class_17922;
            class_2248 block = class_17472.method_7711();
            return block instanceof class_4739 || block instanceof class_2336 || block instanceof class_2531;
        }
        return false;
    }

    private static class_2680 chestState(class_1799 class_17992) {
        class_1792 class_17922 = class_17992.method_7909();
        if (class_17922 instanceof class_1747) {
            class_1747 class_17472 = (class_1747)class_17922;
            class_2248 block = class_17472.method_7711();
            return block.method_9564();
        }
        return null;
    }

    private static class_2586 createBlockEntity(class_2680 class_26802) {
        class_2586 class_25862;
        class_2248 class_22482 = class_26802.method_26204();
        if (class_22482 == class_2246.field_10443) {
            return new PunchyEnderChestBlockEntity(ORIGIN, class_26802);
        }
        if (class_22482 == class_2246.field_10380) {
            return new PunchyTrappedChestBlockEntity(ORIGIN, class_26802);
        }
        if (class_22482 == class_2246.field_10034) {
            return new PunchyChestBlockEntity(ORIGIN, class_26802);
        }
        if (class_22482 instanceof class_2343) {
            class_2343 class_23432 = (class_2343)class_22482;
            class_25862 = class_23432.method_10123(ORIGIN, class_26802);
        } else {
            class_25862 = null;
        }
        return class_25862;
    }

    static final class PunchyChestBlockEntity
    extends class_2595 {
        private float punchy$open;

        PunchyChestBlockEntity(class_2338 class_23382, class_2680 class_26802) {
            super(class_23382, class_26802);
        }

        void punchy$setOpen(float f) {
            this.punchy$open = f;
        }

        public float method_11274(float f) {
            return this.punchy$open;
        }
    }

    static final class PunchyTrappedChestBlockEntity
    extends class_2646 {
        private float punchy$open;

        PunchyTrappedChestBlockEntity(class_2338 class_23382, class_2680 class_26802) {
            super(class_23382, class_26802);
        }

        void punchy$setOpen(float f) {
            this.punchy$open = f;
        }

        public float method_11274(float f) {
            return this.punchy$open;
        }
    }

    static final class PunchyEnderChestBlockEntity
    extends class_2611 {
        private float punchy$open;

        PunchyEnderChestBlockEntity(class_2338 class_23382, class_2680 class_26802) {
            super(class_23382, class_26802);
        }

        void punchy$setOpen(float f) {
            this.punchy$open = f;
        }

        public float method_11274(float f) {
            return this.punchy$open;
        }
    }
}
