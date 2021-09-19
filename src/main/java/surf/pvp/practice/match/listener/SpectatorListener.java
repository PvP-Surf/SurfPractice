package surf.pvp.practice.match.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.listener.events.impl.match.spectator.LeaveSpectateMatchEvent;
import surf.pvp.practice.listener.events.impl.match.spectator.SpectateMatchEvent;
import surf.pvp.practice.match.Match;

public class SpectatorListener implements Listener {

    private final SurfPractice surfPractice;

    public SpectatorListener(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;
    }

    @EventHandler
    public final void onSpectatorAddEvent(SpectateMatchEvent event) {
        final Match match = event.getMatch();
        final Player player = event.getPlayer();

        player.setGameMode(GameMode.CREATIVE);

        for (Player inMatch : match.getPlayers()) {
            inMatch.hidePlayer(player);
            player.showPlayer(inMatch);
        }

        for (Player spectator : match.getSpectators()) {
            spectator.hidePlayer(player);
            player.hidePlayer(spectator);
        }

    }

    @EventHandler
    public final void onSpectatorLeaveEvent(LeaveSpectateMatchEvent event) {
        final Match match = event.getMatch();
        final Player player = event.getPlayer();

        for (Player players : match.getPlayers()) {
            player.hidePlayer(players);
        }

        player.setGameMode(GameMode.SURVIVAL);
    }

}
