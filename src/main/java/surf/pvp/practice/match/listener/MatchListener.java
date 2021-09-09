package surf.pvp.practice.match.listener;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.events.impl.match.global.MatchEndCountdownEvent;
import surf.pvp.practice.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.PlayerUtil;

@AllArgsConstructor
public class MatchListener implements Listener {

    private final SurfPractice surfPractice;

    /**
     * Match Listener that listens
     * to all important events to make sure
     * the match is going well
     */

    @EventHandler
    public final void onMatchStartEvent(MatchStartEvent event) {
        Player[] players = event.getMatch().getPlayers();

        for (Player player : players) {
            PlayerUtil.denyMovement(player);
        }
    }

    @EventHandler
    public final void onMatchCountdownEndEvent(MatchEndCountdownEvent event) {
        Player[] players = event.getMatch().getPlayers();

        for (Player player : players) {
            PlayerUtil.allowMovement(player);
        }
    }

    @EventHandler
    public final void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();

        if (match == null)
            return;

        match.end(player.getUniqueId(), true);
    }

    @EventHandler
    public final void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        player.spigot().respawn();

        Match match = profile.getMatch();

        if (match == null) {
            return;
        }

        match.end(event.getEntity().getKiller().getUniqueId(), false);
    }

}
