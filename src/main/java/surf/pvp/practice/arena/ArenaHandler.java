package surf.pvp.practice.arena;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;

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
    }

    /**
     * loads all arenas
     */

    private final void load() {

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

}
