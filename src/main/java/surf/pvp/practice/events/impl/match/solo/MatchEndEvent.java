package surf.pvp.practice.events.impl.match.solo;

import lombok.Getter;
import org.bukkit.entity.Player;
import surf.pvp.practice.events.CustomEvent;
import surf.pvp.practice.match.Match;

@Getter
public class MatchEndEvent extends CustomEvent {

    private final Player winner;
    private final Player loser;

    private final Match match;

    public MatchEndEvent(Player winner, Player loser, Match match) {
        this.winner = winner;
        this.loser = loser;
        this.match = match;
    }

}
