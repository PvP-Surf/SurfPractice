package surf.pvp.practice.arena;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ArenaHandler {

    private final Map<String, Arena> arenaMap = new HashMap<>();
    private final SurfPractice surfPractice;

    /**
     * Arena Manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public ArenaHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        this.load();
        surfPractice.getServer().getPluginManager().registerEvents(new ArenaListener(surfPractice), surfPractice);
    }

    /**
     * loads all arenas
     */

    private final void load() {
        surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> {
            for (Document document : surfPractice.getMongoHandler().getArenas().find()) {
                arenaMap.put(document.getString("_id"), new Arena(document, surfPractice));
            }
        });
    }

    /**
     * Creates an arena
     *
     * @param name name of arena
     */

    public final void createArena(String name) {
        arenaMap.put(name.toUpperCase(), new Arena(name));
    }

    /**
     * Gets an arena based on
     * its name
     *
     * @param name name of arena
     * @return {@link Arena}
     */

    public final Arena getArena(String name) {
        return arenaMap.get(name.toUpperCase());
    }

    /**
     * Gets an available arena
     * For a kit
     *
     * @param kit kit to get the arena off
     * @return {@link Arena}
     */

    public final Arena getAvailableArena(Kit kit) {
        return arenaMap.values().stream().filter(arena -> !arena.isBusy() && arena.getKits().contains(kit)).findFirst().orElse(null);
    }

    public final Arena getAvailableEventArena(Kit kit) {
        return arenaMap.values().stream().filter(arena -> !arena.isBusy() && arena.getKits().contains(kit) && arena.isEvent()).findFirst().orElse(null);
    }

    /**
     * Handles the removal of an arena
     *
     * @param arena arena to remove
     * @param async if async or not
     */

    public final void handleRemoval(Arena arena, boolean async) {
        arenaMap.remove(arena.getName().toLowerCase());

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> handleRemoval(arena, false));
            return;
        }

        Document document = surfPractice.getMongoHandler().getArenas().find(Filters.eq("_id", arena.getName())).first();

        if (document == null)
            return;

        surfPractice.getMongoHandler().getArenas().deleteOne(document);
    }

    /**
     * Gets all arenas registered
     *
     * @return {@link Arena}
     */

    public final Collection<Arena> getArenas() {
        return arenaMap.values();
    }

}
