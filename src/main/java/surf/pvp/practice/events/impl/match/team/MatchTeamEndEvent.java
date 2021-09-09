package surf.pvp.practice.events.impl.match.team;

import lombok.Getter;
import surf.pvp.practice.events.CustomEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.objects.MatchTeam;

@Getter
public class MatchTeamEndEvent extends CustomEvent {

    private final MatchTeam winnerMatchTeam;
    private final MatchTeam loserMatchTeam;

    private final Match match;

    public MatchTeamEndEvent(MatchTeam winnerMatchTeam, MatchTeam loserMatchTeam, Match match) {
        this.winnerMatchTeam = winnerMatchTeam;
        this.loserMatchTeam = loserMatchTeam;
        this.match = match;
    }

}
