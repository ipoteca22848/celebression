package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.class_2960;
import org.jetbrains.annotations.Nullable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCatalog;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticVisibility;
import ru.method.celebression.modules.impl.render.cosmetic.LabyModCapeService;
import ru.method.celebression.modules.impl.render.cosmetic.CelebressionCapeRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.render.cosmetic.PlayerCosmeticProfileStore;
import ru.method.celebression.manager.UserSyncManager;

public final class CosmeticsService
implements MinecraftClientAccess {
    private final PlayerCosmeticProfileStore ev = new PlayerCosmeticProfileStore();
    private volatile boolean enabled = true;
    private volatile CosmeticVisibility ew = CosmeticVisibility.dw;

    public PlayerCosmeticProfileStore bA() {
        return this.ev;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    public boolean bB() {
        return this.ew != CosmeticVisibility.dz;
    }

    public void a(CosmeticVisibility cosmeticVisibility) {
        this.ew = cosmeticVisibility == null ? CosmeticVisibility.dw : cosmeticVisibility;
    }

    public CosmeticLoadout aL() {
        return this.ev.aL();
    }

    public void c(CosmeticSlot cosmeticSlot, String string) {
        if (cosmeticSlot == null || !CosmeticCatalog.b(string, cosmeticSlot)) {
            return;
        }
        this.ev.d(this.ev.aL().b(cosmeticSlot, string));
        this.bC();
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && !celebression.isConfigLoading()) {
            celebression.getConfigManager().dU();
        }
    }

    public void at(String string) {
        this.au(LabyModCapeService.am(string));
    }

    public void au(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        this.c(CosmeticSlot.dr, "laby:" + string);
        LabyModCapeService.ah(string);
        LabyModCapeService.an(string);
    }

    public void b(int n) {
        this.b(CosmeticSlot.dt, n);
    }

    public void b(CosmeticSlot cosmeticSlot, int n) {
        this.ev.d(this.ev.aL().a(cosmeticSlot, n));
        this.bC();
        Celebression celebression = Celebression.getInstance();
        if (celebression != null && !celebression.isConfigLoading()) {
            celebression.getConfigManager().dU();
        }
    }

    public int a(CosmeticSlot cosmeticSlot, @Nullable UUID uUID, @Nullable String string) {
        return this.b(uUID, string).g(cosmeticSlot);
    }

    public CosmeticLoadout b(@Nullable UUID uUID, @Nullable String string) {
        CosmeticPreviewContext.PreviewState previewState = CosmeticPreviewContext.aO();
        if (previewState != null) {
            return previewState.aQ();
        }
        if (uUID != null && this.c(uUID)) {
            return this.ev.aL();
        }
        CosmeticLoadout cosmeticLoadout = this.ev.a(uUID, string);
        if (!cosmeticLoadout.isEmpty()) {
            return cosmeticLoadout;
        }
        return new CosmeticLoadout();
    }

    public CosmeticDefinition b(CosmeticSlot cosmeticSlot, @Nullable UUID uUID, @Nullable String string) {
        CosmeticLoadout cosmeticLoadout = this.b(uUID, string);
        return CosmeticCatalog.a(cosmeticLoadout.f(cosmeticSlot), cosmeticSlot);
    }

    @Nullable
    public class_2960 c(@Nullable UUID uUID, @Nullable String string) {
        if (!this.d(uUID, string)) {
            return null;
        }
        String string2 = this.b(uUID, string).f(CosmeticSlot.dr);
        if ("celebression_signature".equals(string2)) {
            return CelebressionCapeRenderer.texture();
        }
        if (string2 != null && string2.startsWith("laby:")) {
            return LabyModCapeService.an(string2.substring(5));
        }
        CosmeticDefinition cosmeticDefinition = CosmeticCatalog.a(string2, CosmeticSlot.dr);
        return cosmeticDefinition.W() ? null : cosmeticDefinition.texture();
    }

    public boolean d(@Nullable UUID uUID, @Nullable String string) {
        if (CosmeticPreviewContext.isActive()) {
            return true;
        }
        if (!this.enabled) {
            return false;
        }
        if (uUID != null && this.c(uUID)) {
            return true;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return false;
        }
        UserSyncManager userSyncManager = celebression.getUserSyncManager();
        if (userSyncManager == null || !userSyncManager.e(uUID, string)) {
            return false;
        }
        return switch (this.ew) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case CosmeticVisibility.dw -> true;
            case CosmeticVisibility.dx -> this.av(string);
            case CosmeticVisibility.dy -> userSyncManager.h(uUID, string);
            case CosmeticVisibility.dz -> false;
        };
    }

    private boolean av(@Nullable String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getFriendManager() == null) {
            return false;
        }
        return celebression.getFriendManager().av(string);
    }

    public boolean c(CosmeticSlot cosmeticSlot, @Nullable UUID uUID, @Nullable String string) {
        if (!this.d(uUID, string)) {
            return false;
        }
        return !this.b(cosmeticSlot, uUID, string).W();
    }

    public void aM() {
        this.ev.aM();
    }

    public void bC() {
        UserSyncManager userSyncManager = Celebression.getInstance().getUserSyncManager();
        if (userSyncManager != null) {
            userSyncManager.a((Consumer<Void>)null, (Consumer<String>)null);
        }
    }

    private boolean c(UUID uUID) {
        if (CosmeticsService.aiG.field_1724 == null || CosmeticsService.aiG.field_1724.method_7334() == null) {
            return false;
        }
        UUID uUID2 = CosmeticsService.aiG.field_1724.method_7334().getId();
        return uUID2 != null && uUID2.equals(uUID);
    }
}

