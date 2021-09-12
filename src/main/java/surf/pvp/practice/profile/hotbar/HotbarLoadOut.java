package surf.pvp.practice.profile.hotbar;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class HotbarLoadOut {

    /**
     * Gives all the hotbar items to a player
     *
     * @param player player to give all the hotbar items to
     */

    public final void giveSpawnHotbar(Player player) {
        HotbarItem.UNRANKED.setItem(player, 0);
        HotbarItem.RANKED.setItem(player, 1);
        HotbarItem.PARTY_CREATE.setItem(player, 4);
        HotbarItem.VIEW_LEADERBOARDS.setItem(player, 7);
        HotbarItem.SETTINGS.setItem(player, 8);
    }

    /**
     * Gives all the queue items to a player
     *
     * @param player player to give all the queue items to
     */

    public final void giveQueueItems(Player player) {
        HotbarItem.LEAVE_QUEUE.setItem(player, 4);
    }

    /**
     * Gives all the party items to a player
     *
     * @param player player to give the items to
     */

    public final void givePartyItems(Player player) {

    }

}
