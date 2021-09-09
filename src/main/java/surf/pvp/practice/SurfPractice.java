package surf.pvp.practice;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import surf.pvp.practice.arena.ArenaHandler;
import surf.pvp.practice.kit.KitHandler;
import surf.pvp.practice.mongo.MongoHandler;
import surf.pvp.practice.profile.ProfileHandler;
import surf.pvp.practice.profile.ProfileStorage;
import surf.pvp.practice.profile.impl.ProfileMongoStorage;

@Getter
public class SurfPractice extends JavaPlugin {

    @Getter
    private static SurfPractice instance;

    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private ArenaHandler arenaHandler;
    private KitHandler kitHandler;

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.mongoHandler = new MongoHandler(this);

        this.kitHandler = new KitHandler(this);
        this.arenaHandler = new ArenaHandler(this);

        this.profileHandler = new ProfileHandler(this);
    }

}
