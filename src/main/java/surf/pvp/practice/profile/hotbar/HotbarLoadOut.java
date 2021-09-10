package surf.pvp.practice.profile.hotbar;

import org.bukkit.entity.Player;

public class HotbarLoadOut {

    private static HotbarLoadOut instance;

    /**
     * Initilizes/Create the instance if
     * null
     *
     * @return instance
     */

    public static HotbarLoadOut getInstance() {
        if (instance == null) {
            instance = new HotbarLoadOut();
            return instance;
        }

        return instance;
    }

    /**
     * Gives all the hotbar items to a player
     *
     * @param player player to give all the hotbar items to
     */

    public final void giveSpawnHotbar(Player player) {

    }

    /**
     * Gives all the queue items to a player
     *
     * @param player player to give all the queue items to
     */

    public final void giveQueueItems(Player player) {

    }

    /**
     * Gives all the party items to a player
     *
     * @param player player to give the items to
     */

    public final void givePartyItems(Player player) {

    }

}
