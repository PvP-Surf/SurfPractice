package surf.pvp.practice;

import lombok.Getter;
import me.vaperion.blade.Blade;
import me.vaperion.blade.command.bindings.impl.BukkitBindings;
import me.vaperion.blade.command.bindings.impl.DefaultBindings;
import me.vaperion.blade.command.container.impl.BukkitCommandContainer;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.arena.ArenaHandler;
import surf.pvp.practice.clan.ClanHandler;
import surf.pvp.practice.commands.*;
import surf.pvp.practice.commands.service.providers.ArenaCommandProvider;
import surf.pvp.practice.commands.service.providers.KitCommandProvider;
import surf.pvp.practice.commands.service.providers.MaterialCommandProvider;
import surf.pvp.practice.events.object.EventRegistration;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.kit.KitHandler;
import surf.pvp.practice.leaderboard.LeaderboardHandler;
import surf.pvp.practice.leaderboard.impl.LeaderboardElement;
import surf.pvp.practice.listener.listeners.GeneralServerListener;
import surf.pvp.practice.listener.listeners.PlayerDeathListener;
import surf.pvp.practice.listener.listeners.PlayerJoinListener;
import surf.pvp.practice.match.objects.MatchRegistration;
import surf.pvp.practice.mongo.MongoHandler;
import surf.pvp.practice.profile.ProfileHandler;
import surf.pvp.practice.profile.hotbar.HotbarListener;
import surf.pvp.practice.scoreboard.Scoreboard;
import surf.pvp.practice.tournaments.TournamentHandler;
import surf.pvp.practice.util.ConfigFile;
import surf.pvp.practice.util.assemble.Assemble;
import surf.pvp.practice.util.assemble.AssembleStyle;

@Getter
public class SurfPractice extends JavaPlugin {

    @Getter
    private static SurfPractice instance;

    private ConfigFile tabFile;
    private ConfigFile scoreBoardFile;

    private ClanHandler clanHandler;
    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private ArenaHandler arenaHandler;
    private KitHandler kitHandler;
    private TournamentHandler tournamentHandler;

    /**
     * Loading logic of the plugin
     */

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();
        this.tabFile = new ConfigFile(getDataFolder(), "tab.yml");
        this.scoreBoardFile = new ConfigFile(getDataFolder(), "scoreboard.yml");
    }

    /**
     * Enabling logic of the plugin
     */

    @Override
    public void onEnable() {
        this.mongoHandler = new MongoHandler(this);

        this.kitHandler = new KitHandler(this);
        this.arenaHandler = new ArenaHandler(this);
        this.clanHandler = new ClanHandler(this);

        this.profileHandler = new ProfileHandler(this);
        this.tournamentHandler = new TournamentHandler(this);

        this.registerPlugin();
        new LeaderboardHandler(this, new LeaderboardElement(), 300 * 20L);
        new Assemble(this, new Scoreboard(this), AssembleStyle.KOHI, 2L);
    }

    /**
     * Disabling logic of the plugin
     */

    @Override
    public void onDisable() {
        arenaHandler.getArenas().forEach(arena -> arena.save(this, false));
        kitHandler.getKits().forEach(kit -> kit.save(this, false));
    }

    /**
     * Registration logic of the plugin
     */

    public final void registerPlugin() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new GeneralServerListener(this), this);
        pluginManager.registerEvents(new HotbarListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        new MatchRegistration(this);
        new EventRegistration(this);

        Blade.of().binding(new BukkitBindings()).binding(new DefaultBindings()).containerCreator(BukkitCommandContainer.CREATOR)
                .fallbackPrefix("pvpsurf").overrideCommands(true)
                .bind(Kit.class, new KitCommandProvider(this))
                .bind(Arena.class, new ArenaCommandProvider(this))
                .bind(Material.class, new MaterialCommandProvider()).build()
                .register(new ViewCommand()).register(new QueueCommand())
                .register(new ArenaCommand(this)).register(new KitCommand(this))
                .register(new EssentialCommands());
    }

}
