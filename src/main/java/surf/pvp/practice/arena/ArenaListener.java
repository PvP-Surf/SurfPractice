package surf.pvp.practice.arena;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.arena.ArenaBlockBreakEvent;
import surf.pvp.practice.listener.events.impl.arena.ArenaBlockPlaceEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;

@AllArgsConstructor
public class ArenaListener implements Listener {

    private final SurfPractice surfPractice;

    @EventHandler
    public final void onPlayerBreakBlockEvent(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        if (profile.isBuild())
            return;

        final Match match = profile.getMatch();

        if (match == null) {
            event.setCancelled(true);
            return;
        }

        final Kit kit = match.getKit();
        final ArenaBlockBreakEvent arenaBlockBreakEvent = new ArenaBlockBreakEvent(player, match.getArena(), kit, match, event.getBlock());

        surfPractice.getServer().getPluginManager().callEvent(arenaBlockBreakEvent);

        if (arenaBlockBreakEvent.isCancelled())
            event.setCancelled(true);
    }

    @EventHandler
    public final void onPlayerBlockBuildEvent(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        if (profile.isBuild())
            return;

        final Match match = profile.getMatch();

        if (match == null) {
            event.setCancelled(true);
            return;
        }

        final Kit kit = match.getKit();
        final ArenaBlockPlaceEvent blockPlaceEvent = new ArenaBlockPlaceEvent(player, match.getArena(), kit, match, event.getBlock());

        surfPractice.getServer().getPluginManager().callEvent(blockPlaceEvent);

        if (blockPlaceEvent.isCancelled())
            return;
    }

}
