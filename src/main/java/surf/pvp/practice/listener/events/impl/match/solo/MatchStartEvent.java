package surf.pvp.practice.listener.events.impl.match.solo;

import lombok.Getter;
import org.bukkit.entity.Player;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.match.Match;

@Getter
public class MatchStartEvent extends CustomEvent {

    private final Player playerOne;
    private final Player playerTwo;

    private final Match match;

    public MatchStartEvent(Player playerOne, Player playerTwo, Match match) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.match = match;
    }

}
