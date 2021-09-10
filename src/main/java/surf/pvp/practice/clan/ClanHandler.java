package surf.pvp.practice.clan;

import org.bson.Document;
import surf.pvp.practice.Locale;
import surf.pvp.practice.SurfPractice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClanHandler {

    private final Map<UUID, Clan> clanMap = new HashMap<>();
    private final SurfPractice surfPractice;

    /**
     * Clan Manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public ClanHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        this.load();
    }

    /**
     * Loads all clans
     */

    private final void load() {
        surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> {
            for (Document document : surfPractice.getMongoHandler().getClans().find()) {
                clanMap.put(UUID.fromString(document.getString("_id")), new Clan(document));
            }
        });
    }

    public final void createClan(UUID leader) {
        Clan clan = new Clan(leader, Locale.SERVER_NAME.getString());
        clanMap.put(clan.getUuid(), clan);
    }

    /**
     * Gets all clans
     *
     * @param uuid uuid of clan to get
     * @return {@link Clan}
     */

    public final Clan getClan(UUID uuid) {
        return clanMap.get(uuid);
    }

    /**
     * Gets all clans
     *
     * @return {@link Collection<Clan>}
     */

    public final Collection<Clan> getClans() {
        return clanMap.values();
    }

}
