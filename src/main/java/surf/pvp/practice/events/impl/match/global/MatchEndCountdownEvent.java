package surf.pvp.practice.events.impl.match.global;

import lombok.Getter;
import surf.pvp.practice.events.CustomEvent;
import surf.pvp.practice.match.Match;

@Getter
public class MatchEndCountdownEvent extends CustomEvent {

    private final Match match;

    public MatchEndCountdownEvent(Match match) {
        this.match = match;
    }

}
