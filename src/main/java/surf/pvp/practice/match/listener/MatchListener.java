package surf.pvp.practice.match.listener;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import surf.pvp.practice.Locale;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.listener.events.impl.global.PracticeDeathEvent;
import surf.pvp.practice.listener.events.impl.match.global.MatchEndCountdownEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.PlayerUtil;
import surf.pvp.practice.util.component.Component;

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
        Match match = event.getMatch();
        Player[] players = match.getPlayers();

        for (Player player : players) {
            PlayerUtil.allowMovement(player);

            Locale.MATCH_START.getStringList().forEach(string -> {
                player.sendMessage(CC.translate(string.replace("{kit}", match.getKit().getName()))
                        .replace("{arena}", match.getArena().getName()));
            });
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


        final Component loserComponent = new Component(loser.getName()).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + loser.getUniqueId()));
        final Component winnerComponent = new Component(player.getName()).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + player.getUniqueId()));

        final Match match = event.getMatch();

        Locale.MATCH_END.getStringList().forEach(string -> {
            match.getAllPlayers().forEach(player1 -> {
                player1.sendMessage(CC.translate(string.replace("{winner}",
                        winnerComponent.get().toLegacyText())
                        .replace("{loser}", loserComponent.get().toLegacyText())
                        .replace("{arena}", match.getArena().getName()))
                        .replace("{kit}", match.getKit().getName()));
            });
        });
    }

}
