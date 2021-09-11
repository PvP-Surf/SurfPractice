package surf.pvp.practice;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import surf.pvp.practice.arena.ArenaHandler;
import surf.pvp.practice.clan.ClanHandler;
import surf.pvp.practice.profile.joinitems.JoinItemHandler;
import surf.pvp.practice.kit.KitHandler;
import surf.pvp.practice.leaderboard.LeaderboardHandler;
import surf.pvp.practice.leaderboard.impl.LeaderboardElement;
import surf.pvp.practice.listener.listeners.PlayerDeathListener;
import surf.pvp.practice.mongo.MongoHandler;
import surf.pvp.practice.profile.ProfileHandler;
import surf.pvp.practice.tournaments.TournamentHandler;

@Getter
public class SurfPractice extends JavaPlugin {

    @Getter
    private static SurfPractice instance;

    private ClanHandler clanHandler;
    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private ArenaHandler arenaHandler;
    private KitHandler kitHandler;
    private TournamentHandler tournamentHandler;
    private JoinItemHandler joinItemHandler;

    /**
     * Loading logic of the plugin
     */

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
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
        this.joinItemHandler = new JoinItemHandler(this);

        this.registerPlugin();
        new LeaderboardHandler(this, new LeaderboardElement(), 300 * 20L);
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
    }

}
