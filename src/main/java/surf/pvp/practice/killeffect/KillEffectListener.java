package surf.pvp.practice.killeffect;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.profile.Profile;

public class KillEffectListener implements Listener {

    @EventHandler
    public final void onItemPickUpEvent(PlayerPickupItemEvent event) {
        ItemStack stack = event.getItem().getItemStack();

        if (stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null)
            return;

        if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("staged")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public final void onMatchEndEvent(MatchEndEvent event) {
        final Player player = event.getWinner();
        final Player loser = event.getLoser();

        if (loser == null)
            return;

        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        final KillEffectType killEffectType = profile.getKillEffectType();

        if (killEffectType == null)
            return;

        killEffectType.getKillEffectAdapter().display(player, loser);
    }

}
