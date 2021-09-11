package surf.pvp.practice.profile.hotbar.adapter.impl.party;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import surf.pvp.practice.profile.hotbar.adapter.HotbarAdapter;

public class PartyCreateItem implements HotbarAdapter {

    /**
     * Handles what the item will do
     * on the interaction of a player
     *
     * @param event instance of {@link PlayerInteractEvent}
     */

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        player.chat("/party create");
    }
}
