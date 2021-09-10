package surf.pvp.practice.listener.events.impl.match.team;

import lombok.Getter;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.objects.MatchTeam;

@Getter
public class MatchTeamStartEvent extends CustomEvent {

    private final MatchTeam matchTeamOne;
    private final MatchTeam matchTeamTwo;

    private final Match match;

    public MatchTeamStartEvent(MatchTeam matchTeamOne, MatchTeam matchTeamTwo, Match match) {
        this.matchTeamOne = matchTeamOne;
        this.matchTeamTwo = matchTeamTwo;
        this.match = match;
    }

}
