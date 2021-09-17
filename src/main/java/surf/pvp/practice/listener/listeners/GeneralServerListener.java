package surf.pvp.practice.listener.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

@AllArgsConstructor
public class GeneralServerListener implements Listener {

    private final SurfPractice surfPractice;

    @EventHandler
    public final void onEntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();

        if (match != null || profile.getProfileState().equals(ProfileState.EVENT_MATCH)
        || profile.getProfileState().equals(ProfileState.IN_TOURNAMENT_MATCH)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public final void onItemDropEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();

        if (match != null || profile.getProfileState().equals(ProfileState.EVENT)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public final void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !(event.getClickedInventory() instanceof PlayerInventory))
            return;

        Player player = (Player) event.getWhoClicked();
        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();

        if (match != null || profile.getProfileState().equals(ProfileState.EVENT)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public final void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public final void onEntitySpawnEvent(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public final void onBlockDamageEvent(BlockDamageEvent event) {
        event.setCancelled(true);
    }

}
