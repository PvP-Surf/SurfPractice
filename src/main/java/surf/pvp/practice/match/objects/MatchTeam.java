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

    public List<Player> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }
}
