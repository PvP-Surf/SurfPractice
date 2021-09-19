package surf.pvp.practice.essentials;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.util.LocationUtil;

@Getter
@Setter
public class Essentials {

    private static Essentials instance;
    private Location spawnLocation;

    /**
     * Loads essentials
     */

    public final void load(SurfPractice surfPractice) {
        this.spawnLocation = LocationUtil.stringToLocation(surfPractice.getConfig().getString("locations.spawn"));
    }

    /**
     * Saves stuff
     *
     * @param surfPractice {@link SurfPractice}
     * @param async async
     */

    public final void save(SurfPractice surfPractice, boolean async) {

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> save(surfPractice, false));
            return;
        }

        final FileConfiguration fileConfiguration = surfPractice.getConfig();

        fileConfiguration.set("locations.spawn", LocationUtil.locationToString(spawnLocation));

        surfPractice.saveConfig();
    }

    /**
     * Gets the instance of {@link Essentials}
     *
     * @return {@link Essentials}
     */

    public static Essentials getInstance() {
        if (instance == null) {
            instance = new Essentials();
        }
        return instance;
    }

}
