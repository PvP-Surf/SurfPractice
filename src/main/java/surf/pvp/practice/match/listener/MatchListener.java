package surf.pvp.practice.match.listener;

import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.listener.events.impl.global.PracticeDeathEvent;
import surf.pvp.practice.listener.events.impl.match.global.MatchEndCountdownEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.remains.MatchRemains;
import surf.pvp.practice.util.PlayerUtil;

import java.util.Optional;

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
    public final void onPracticeDeathEvent(PracticeDeathEvent event) {
        Player player = event.getPlayer();
        Optional<Player> killer = event.getKiller();

        Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();

        if (match == null) {
            return;
        }

        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.setGameMode(GameMode.CREATIVE);

        if (killer.isPresent()) {
            match.end(killer.get().getUniqueId(), false);
        } else {
            match.end(player.getUniqueId(), true);
        }
    }

    @EventHandler
    public final void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl))
            return;

        EnderPearl enderPearl = (EnderPearl) event.getEntity();

        if (!(enderPearl.getShooter() instanceof Player))
            return;

        Player player = (Player) enderPearl.getShooter();
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (profile.getMatch() != null) {
            profile.getEnderPearlCooldown().put();
        }
    }

    @EventHandler
    public final void onMatchEndEvent(MatchEndEvent event) {
        final Player player = event.getWinner();
        final Player loser = event.getLoser();

        final MatchRemains playerProfile = surfPractice.getProfileHandler().getProfile(player.getUniqueId()).getMatchRemains().update(player);
        final MatchRemains loserProfile = surfPractice.getProfileHandler().getProfile(loser.getUniqueId()).getMatchRemains().update(loser);

    }

}
