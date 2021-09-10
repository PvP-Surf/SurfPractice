package surf.pvp.practice.tournaments;

import lombok.Getter;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;

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
    }

    /**
     * Creates a tournament for a certain kit
     *
     * @param kit kit to create the tournament with
     */

    public final void createTournament(Kit kit) {
        tournaments.add(new Tournament(kit));
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
