package surf.pvp.practice.kit;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.queue.QueueRule;
import surf.pvp.practice.queue.QueueType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KitHandler {

    private final Map<String, Kit> kitMap = new HashMap<>();
    private final SurfPractice surfPractice;

    /**
     * Kit Manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public KitHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        this.load();
    }

    /**
     * loads all kits
     */

    private final void load() {
        surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> {
            for (Document document : surfPractice.getMongoHandler().getKits().find()) {
                kitMap.put(document.getString("_id"), new Kit(document));
            }
        });
    }

    /**
     * Creates a kit
     *
     * @param name name of kit
     */

    public final Kit createKit(String name) {
        final Kit kit =  kitMap.put(name.toUpperCase(), new Kit(name));

        for (Profile profile : surfPractice.getProfileHandler().getProfiles()) {
            profile.getEloMap().put(kit, 1000);
        }

        return kit;
    }

    /**
     * Handles the removal of a kit
     *
     * @param kit   kit to remove
     * @param async if task should be ran async or not
     */

    public final void handleRemoval(Kit kit, boolean async) {
        kitMap.remove(kit.getName().toUpperCase());

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> handleRemoval(kit, false));
            return;
        }

        Document document = surfPractice.getMongoHandler().getKits().find(Filters.eq("_id", kit.getName())).first();

        if (document == null)
            return;

        surfPractice.getMongoHandler().getKits().deleteOne(document);
    }

    /**
     * Gets the kit based on
     * it's name
     *
     * @param name name of kit
     * @return {@link Kit}
     */

    public final Kit getKit(String name) {
        return kitMap.get(name.toUpperCase());
    }

    /**
     * Gets all kits with the certain queue type
     *
     * @param queueType queue type of kit
     * @return {@link java.util.List<Kit>}
     */

    public final List<Kit> getKits(QueueType queueType, boolean elo) {
        final QueueRule queueRule = elo ? QueueRule.ELO : QueueRule.NO_ELO;
        return kitMap.values().stream().filter(kit -> kit.getQueue(queueType, queueRule) != null).collect(Collectors.toList());
    }

    /**
     * Gets all kits registered
     *
     * @return {@link Collection<Kit>}
     */

    public final Collection<Kit> getKits() {
        return kitMap.values();
    }

}
