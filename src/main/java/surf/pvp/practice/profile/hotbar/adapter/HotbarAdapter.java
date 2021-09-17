package surf.pvp.practice.profile.hotbar.adapter;

import org.bukkit.event.player.PlayerInteractEvent;

public interface HotbarAdapter {

    /**
     * Handles what the item will do
     * on the interaction of a player
     *
     * @param event instance of {@link PlayerInteractEvent}
     */

    void handle(PlayerInteractEvent event);

}
