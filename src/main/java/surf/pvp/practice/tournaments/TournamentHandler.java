package surf.pvp.practice.tournaments;

import lombok.Getter;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.tournaments.tasks.TournamentStartTask;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TournamentHandler {

    private final List<Tournament> tournaments = new ArrayList<>();
    private final SurfPractice surfPractice;

    /**
     * Tournament Manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public TournamentHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        new TournamentStartTask(this).runTaskTimerAsynchronously(surfPractice, 20L, 20L);
    }

    /**
     * Creates a tournament for a certain kit
     *
     * @param kit kit to create the tournament with
     */

    public final void createTournament(Kit kit, int playersToPlay) {
        tournaments.add(new Tournament(kit, playersToPlay));
    }

    /**
     * Get the tournament of a kit
     *
     * @param kit kit to create a tournament with
     * @return {@link Tournament}
     */

    public final Tournament getTournament(Kit kit) {
        return tournaments.stream().filter(tournament -> tournament.getKit().getName().equalsIgnoreCase(kit.getName()))
                .findFirst().orElse(null);
    }

}
