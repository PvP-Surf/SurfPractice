package surf.pvp.practice.profile.hotbar.adapter.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.hotbar.adapter.HotbarAdapter;
import surf.pvp.practice.profile.loadout.CustomLoadOut;

import java.util.List;

public class BookHotbarItem implements HotbarAdapter {

    /**
     * Handles what the item will do
     * on the interaction of a player
     *
     * @param event instance of {@link PlayerInteractEvent}
     */

    @Override
    public void handle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();
        ItemStack stack = event.getItem();

        if (match == null)
            return;

        String[] args = stack.getItemMeta().getDisplayName().split("\\s+");

        List<CustomLoadOut> loadOuts = profile.getLoudOut(match.getKit());
        CustomLoadOut customLoadOut = loadOuts.get(Integer.parseInt(args[1]));

        if (customLoadOut == null) {
            customLoadOut = loadOuts.get(loadOuts.size() - 1);
        }

        customLoadOut.update(player);
    }

}
