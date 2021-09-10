package surf.pvp.practice.tournaments.tasks;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import surf.pvp.practice.tournaments.Tournament;
import surf.pvp.practice.tournaments.TournamentHandler;
import surf.pvp.practice.tournaments.TournamentState;

@AllArgsConstructor
public class TournamentStartTask extends BukkitRunnable {

    private final TournamentHandler tournamentHandler;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    @Override
    public void run() {
        for (Tournament tournament : tournamentHandler.getTournaments()) {

            if (tournament.getTournamentState().equals(TournamentState.WAITING)) {
                if (tournament.getPlayerList().size() < tournament.getPlayersToPlayer())
                    return;

                tournament.startMatch(true);
                return;
            }

            if (!tournament.getTournamentState().equals(TournamentState.STARTING_ROUND))
                return;

            if (tournament.getCountdown() <= 0) {
                tournament.setTournamentState(TournamentState.IN_ROUND);
                tournament.startMatch(false);
            } else {
                tournament.setCountdown(tournament.getCountdown() - 1);
            }
        }
    }

}
