package ru.method.celebression.api.mixins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.class_4267;
import net.minecraft.class_437;
import net.minecraft.class_500;
import net.minecraft.class_641;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.api.mixins.accessors.IServerList;
import ru.method.celebression.server.ServerListSnapshot;

@Mixin(value={class_500.class})
public class MultiplayerScreenMixin {
    @Shadow
    protected class_4267 field_3043;
    @Shadow
    private class_641 field_3040;
    private static final int ORDER_CHECK_INTERVAL = 10;
    @Unique
    private List<class_642> celebression$protectedBuffer;
    @Unique
    private List<class_642> celebression$orderedProtected;
    @Unique
    private List<class_642> celebression$otherBuffer;
    private int celebression$ticksUntilReorderCheck = 0;
    private int lastServerListSize = 0;
    private int lastProtectedHash = 0;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void celebression$initServerBuffers(class_437 class_4372, CallbackInfo callbackInfo) {
        this.celebression$protectedBuffer = new ArrayList<class_642>();
        this.celebression$orderedProtected = new ArrayList<class_642>();
        this.celebression$otherBuffer = new ArrayList<class_642>();
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        this.refreshProtectedServers(true);
        this.celebression$ticksUntilReorderCheck = 10;
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    private void onTick(CallbackInfo callbackInfo) {
        if (this.celebression$ticksUntilReorderCheck > 0) {
            --this.celebression$ticksUntilReorderCheck;
            return;
        }
        this.celebression$ticksUntilReorderCheck = 10;
        this.refreshProtectedServers(false);
    }

    private void refreshProtectedServers(boolean bl) {
        boolean bl2;
        this.ensureProtectedServersExist();
        int n = this.field_3040.method_2984();
        int n2 = this.computeProtectedHeaderHash();
        if (!bl && n == this.lastServerListSize && n2 == this.lastProtectedHash) {
            return;
        }
        boolean bl3 = this.moveProtectedToTop();
        boolean bl4 = bl2 = bl || bl3 || n != this.lastServerListSize;
        if (bl2) {
            this.field_3043.method_20125(this.field_3040);
            this.field_3040.method_2987();
        }
        this.lastServerListSize = this.field_3040.method_2984();
        this.lastProtectedHash = this.computeProtectedHeaderHash();
    }

    private void ensureProtectedServersExist() {
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < this.field_3040.method_2984(); ++i) {
            class_642 class_6422 = this.field_3040.method_2982(i);
            if (class_6422 == null || class_6422.field_3761 == null) continue;
            hashSet.add(class_6422.field_3761.toLowerCase(Locale.ROOT));
        }
        for (class_642 class_6422 : ServerListSnapshot.sr()) {
            String string = class_6422.field_3761.toLowerCase(Locale.ROOT);
            if (!hashSet.add(string)) continue;
            this.field_3040.method_2988(class_6422, false);
        }
    }

    private boolean moveProtectedToTop() {
        List<class_642> list = this.getBackingServers();
        if (list == null || list.isEmpty()) {
            return false;
        }
        this.celebression$protectedBuffer.clear();
        this.celebression$otherBuffer.clear();
        for (class_642 class_6422 : list) {
            if (ServerListSnapshot.q(class_6422)) {
                this.celebression$protectedBuffer.add(class_6422);
                continue;
            }
            this.celebression$otherBuffer.add(class_6422);
        }
        if (this.celebression$protectedBuffer.isEmpty()) {
            return false;
        }
        List<class_642> list2 = this.orderProtectedServers();
        if (list2.isEmpty()) {
            return false;
        }
        boolean bl = true;
        for (int i = 0; i < list2.size(); ++i) {
            if (list.size() > i && list.get(i) == list2.get(i)) continue;
            bl = false;
            break;
        }
        if (bl) {
            return false;
        }
        list.clear();
        list.addAll((Collection<class_642>)list2);
        list.addAll(this.celebression$otherBuffer);
        return true;
    }

    private List<class_642> orderProtectedServers() {
        this.celebression$orderedProtected.clear();
        if (this.celebression$protectedBuffer.isEmpty()) {
            return this.celebression$orderedProtected;
        }
        List<class_642> list = ServerListSnapshot.sr();
        for (class_642 class_6422 : list) {
            class_642 class_6423 = this.findByAddress(class_6422.field_3761);
            if (class_6423 == null) continue;
            this.celebression$orderedProtected.add(class_6423);
        }
        for (class_642 class_6422 : this.celebression$protectedBuffer) {
            if (this.celebression$orderedProtected.contains(class_6422)) continue;
            this.celebression$orderedProtected.add(class_6422);
        }
        return this.celebression$orderedProtected;
    }

    private class_642 findByAddress(String string) {
        if (string == null) {
            return null;
        }
        for (class_642 class_6422 : this.celebression$protectedBuffer) {
            if (!string.equals(class_6422.field_3761)) continue;
            return class_6422;
        }
        return null;
    }

    private int computeProtectedHeaderHash() {
        int n = Math.min(ServerListSnapshot.ss(), this.field_3040.method_2984());
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            class_642 class_6422 = this.field_3040.method_2982(i);
            n2 = 31 * n2 + MultiplayerScreenMixin.hashServer(class_6422);
        }
        n2 = 31 * n2 + this.field_3040.method_2984();
        return n2;
    }

    private static int hashServer(class_642 class_6422) {
        return class_6422 == null ? 0 : Objects.hash(class_6422.field_3761, class_6422.field_3752);
    }

    private List<class_642> getBackingServers() {
        return ((IServerList)this.field_3040).celebression$getServers();
    }
}

