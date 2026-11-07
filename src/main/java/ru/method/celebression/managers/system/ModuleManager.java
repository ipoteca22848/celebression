package ru.method.celebression.managers.system;

import ru.method.celebression.api.setting.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.modules.impl.client.AllSkinsModule;
import ru.method.celebression.modules.impl.render.AspectRatioModule;
import ru.method.celebression.modules.impl.client.AuctionHelperModule;
import ru.method.celebression.modules.impl.player.AutoAcceptModule;
import ru.method.celebression.modules.impl.player.AutoNearModule;
import ru.method.celebression.modules.impl.player.AutoRelistModule;
import ru.method.celebression.modules.impl.player.AutoReplyModule;
import ru.method.celebression.modules.impl.player.AutoSpecModule;
import ru.method.celebression.modules.impl.combat.AutoSwapModule;
import ru.method.celebression.modules.impl.render.BlockOverlayModule;
import ru.method.celebression.modules.impl.player.ChatHelperModule;
import ru.method.celebression.modules.impl.render.ChinaHatModule;
import ru.method.celebression.input.ClickGuiKeyHandler;
import ru.method.celebression.modules.impl.movement.ClickGuiWalkModule;
import ru.method.celebression.modules.impl.player.ClickPearlModule;
import ru.method.celebression.modules.impl.player.CommandDelayModule;
import ru.method.celebression.modules.impl.player.CoordInviteModule;
import ru.method.celebression.modules.impl.player.CosmeticsModule;
import ru.method.celebression.modules.impl.player.CrasherModule;
import ru.method.celebression.modules.impl.render.CrosshairModule;
import ru.method.celebression.modules.impl.render.CustomHitBoxModule;
import ru.method.celebression.modules.impl.render.CustomVitalsModule;
import ru.method.celebression.modules.impl.render.DamageParticlesModule;
import ru.method.celebression.modules.impl.player.DeathCoordsModule;
import ru.method.celebression.modules.impl.client.DiscordRpcModule;
import ru.method.celebression.modules.impl.render.DrochkaModule;
import ru.method.celebression.modules.impl.client.ElementsModule;
import ru.method.celebression.modules.impl.player.ElytraSwapModule;
import ru.method.celebression.modules.impl.player.FakePlayerModule;
import ru.method.celebression.modules.impl.player.FastExpModule;
import ru.method.celebression.modules.impl.render.FogBlurModule;
import ru.method.celebression.modules.impl.render.FortniteDamageModule;
import ru.method.celebression.modules.impl.render.FreeLookModule;
import ru.method.celebression.modules.impl.combat.FriendHelperModule;
import ru.method.celebression.modules.impl.render.FullbrightModule;
import ru.method.celebression.modules.impl.player.FunTimeHelperModule;
import ru.method.celebression.modules.impl.render.HandsModule;
import ru.method.celebression.modules.impl.render.HitColorModule;
import ru.method.celebression.modules.impl.render.HitRangeModule;
import ru.method.celebression.modules.impl.player.HitSoundModule;
import ru.method.celebression.modules.impl.player.HolyWorldHelperModule;
import ru.method.celebression.modules.impl.player.ItemHelperModule;
import ru.method.celebression.modules.impl.render.ItemPhysicsModule;
import ru.method.celebression.modules.impl.player.ItemPickupModule;
import ru.method.celebression.modules.impl.render.ItemRadiusModule;
import ru.method.celebression.modules.impl.player.ItemScrollerModule;
import ru.method.celebression.modules.impl.render.JumpCirclesModule;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.modules.impl.render.KillEffectModule;
import ru.method.celebression.modules.impl.render.KillStreakModule;
import ru.method.celebression.modules.impl.player.LockSlotModule;
import ru.method.celebression.modules.impl.player.LowArmorNotifyModule;
import ru.method.celebression.modules.impl.player.MineHelperModule;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.modules.impl.render.MotionBlurModule;
import ru.method.celebression.api.event.MouseScrollEvent;
import ru.method.celebression.modules.impl.movement.MovementHelperModule;
import ru.method.celebression.modules.impl.render.NoRenderModule;
import ru.method.celebression.modules.impl.render.ParticlesModule;
import ru.method.celebression.modules.impl.client.PartyModule;
import ru.method.celebression.modules.impl.player.PlayerUtilsModule;
import ru.method.celebression.modules.impl.player.PotionTrackerModule;
import ru.method.celebression.modules.impl.player.PvpSaveModule;
import ru.method.celebression.modules.impl.player.ReallyWorldHelperModule;
import ru.method.celebression.modules.impl.player.ScoreboardHealthModule;
import ru.method.celebression.modules.impl.client.SecretDupeModule;
import ru.method.celebression.modules.impl.render.SelfNametagsModule;
import ru.method.celebression.api.setting.SettingPersistence;
import ru.method.celebression.modules.impl.combat.ShiftTapModule;
import ru.method.celebression.modules.impl.player.ShulkerTooltipModule;
import ru.method.celebression.modules.impl.render.SkyChangerModule;
import ru.method.celebression.modules.impl.player.SoundsModule;
import ru.method.celebression.modules.impl.movement.SprintModule;
import ru.method.celebression.modules.impl.client.StreamerModeModule;
import ru.method.celebression.modules.impl.render.SwingAnimationModule;
import ru.method.celebression.modules.impl.player.TapeMouseModule;
import ru.method.celebression.modules.impl.render.TargetEspModule;
import ru.method.celebression.modules.impl.player.TotemCounterModule;
import ru.method.celebression.modules.impl.player.TotemTrackerModule;
import ru.method.celebression.modules.impl.render.TrailsModule;
import ru.method.celebression.modules.impl.render.TrajectoriesModule;
import ru.method.celebression.modules.impl.player.UseTrackerModule;
import ru.method.celebression.modules.impl.render.ViewModelModule;
import ru.method.celebression.modules.impl.player.WaypointsModule;
import ru.method.celebression.modules.impl.render.WorldModule;
import ru.method.celebression.modules.impl.render.ZoomModule;
import ru.method.celebression.modules.impl.client.UI;

public class ModuleManager
implements MinecraftClientAccess {
    private final List<Module> modules = new ArrayList<Module>();
    private final Map<Class<? extends Module>, Module> moduleLookup = new HashMap<Class<? extends Module>, Module>();
    private final Map<ModuleCategory, List<Module>> modulesByCategory = new LinkedHashMap<ModuleCategory, List<Module>>();
    private final List<ModuleCategory> categories = List.of(ModuleCategory.values());

    public ModuleManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            this.modulesByCategory.put(moduleCategory, new ArrayList());
        }
        this.addModules(new SprintModule(), new TrajectoriesModule(), new ChinaHatModule(), new MovementHelperModule(), new UI(), new DiscordRpcModule(), new NoRenderModule(), new ItemScrollerModule(), new TargetEspModule(), new AllSkinsModule(), new HitRangeModule(), new SelfNametagsModule(), new AutoSwapModule(), new ShiftTapModule(), new AspectRatioModule(), new CustomHitBoxModule(), new BlockOverlayModule(), new HandsModule(), new ItemHelperModule(), new ElytraSwapModule(), new DamageParticlesModule(), new ParticlesModule(), new FogBlurModule(), new MotionBlurModule(), new TrailsModule(), new WorldModule(), new SwingAnimationModule(), new FullbrightModule(), new ZoomModule(), new FreeLookModule(), new JumpCirclesModule(), new ItemPhysicsModule(), new FunTimeHelperModule(), new FriendHelperModule(), new ReallyWorldHelperModule(), new AutoNearModule(), new AutoRelistModule(), new AuctionHelperModule(), new AutoSpecModule(), new CoordInviteModule(), new DeathCoordsModule(), new ChatHelperModule(), new ClickPearlModule(), new CommandDelayModule(), new ItemPickupModule(), new LowArmorNotifyModule(), new LockSlotModule(), new MineHelperModule(), new PlayerUtilsModule(), new PvpSaveModule(), new TapeMouseModule(), new WaypointsModule(), new PartyModule(), new AutoAcceptModule(), new HolyWorldHelperModule(), new PotionTrackerModule(), new UseTrackerModule(), new HitSoundModule(), new SoundsModule(), new KillEffectModule(), new KillStreakModule(), new ViewModelModule(), new ScoreboardHealthModule(), new StreamerModeModule(), new ClickGuiWalkModule(), new TotemTrackerModule(), new TotemCounterModule(), new ShulkerTooltipModule(), new ItemRadiusModule(), new DrochkaModule(), new SkyChangerModule(), new HitColorModule(), new CrosshairModule(), new FortniteDamageModule(), new CustomVitalsModule(), new FastExpModule(), new AutoReplyModule(), new FakePlayerModule(), new SecretDupeModule(), new ElementsModule(), new CosmeticsModule(), new CrasherModule());
        for (Module module : this.modules) {
            SettingPersistence.a(module, module.getSettings(), module.getName());
        }
    }

    private void addModules(Module ... moduleArray) {
        for (Module module : moduleArray) {
            this.modules.add(module);
            this.moduleLookup.put(module.getClass(), module);
            this.modulesByCategory.computeIfAbsent(module.getCategory(), moduleCategory -> new ArrayList()).add(module);
        }
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (Celebression.getInstance().isPanic()) {
            return;
        }
        if (keyInputEvent.getAction() == 1) {
            Object object;
            int n = keyInputEvent.c();
            if (n < 0) {
                return;
            }
            UI uI = this.getModule(UI.class);
            if (uI != null && (object = uI.getBind()) != null && (ClickGuiKeyHandler.a(uI, n) || ((KeyBind)object).af(n))) {
                return;
            }
            if (Module.fullNullCheck() || ModuleManager.aiG.field_1755 != null) {
                return;
            }
            for (Module module : this.modules) {
                KeyBind keyBind;
                if (module instanceof UI || (keyBind = module.getBind()).isEmpty() || keyBind.jW() || keyBind.c() != n) continue;
                module.toggle();
            }
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (Module.fullNullCheck() || ModuleManager.aiG.field_1755 != null || Celebression.getInstance().isPanic()) {
            return;
        }
        if (eventMouse.getAction() == 1) {
            int n = eventMouse.getButton();
            if (n < 0) {
                return;
            }
            for (Module module : this.modules) {
                KeyBind keyBind = module.getBind();
                if (keyBind.isEmpty() || !keyBind.jW() || keyBind.c() != n) continue;
                module.toggle();
            }
        }
    }

    @EventHandler
    public void onScroll(MouseScrollEvent mouseScrollEvent) {
        if (Module.fullNullCheck() || ModuleManager.aiG.field_1755 != null || Celebression.getInstance().isPanic()) {
            return;
        }
        double d = mouseScrollEvent.h();
        if (d == 0.0) {
            return;
        }
        for (Module module : this.modules) {
            if (module instanceof UI || !module.getBind().c(d)) continue;
            module.toggle();
        }
    }

    public List<Module> getModules(ModuleCategory moduleCategory) {
        return this.modulesByCategory.getOrDefault((Object)moduleCategory, List.of());
    }

    public List<ModuleCategory> getCategories() {
        return this.categories;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return (T)this.moduleLookup.get(clazz);
    }

    @Generated
    public List<Module> getModules() {
        return this.modules;
    }

    @Generated
    public Map<Class<? extends Module>, Module> getModuleLookup() {
        return this.moduleLookup;
    }

    @Generated
    public Map<ModuleCategory, List<Module>> getModulesByCategory() {
        return this.modulesByCategory;
    }
}

