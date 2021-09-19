package surf.pvp.practice.match.objects;

import org.bukkit.entity.Player;

import java.util.List;

public class MatchTeam {

    private final List<Player> players;
    private final int id;

    public MatchTeam(int id, List<Player> players) {
        this.id = id;
        this.players = players;
    }

    /**
     * Gets the players inside of the team
     *
     * @return {@link List<Player>}
     */

    public final List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the ID of the team
     *
     * @return {@link Integer}
     */

    public final int getId() {
        return id;
    }

}
