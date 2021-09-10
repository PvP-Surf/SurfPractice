package surf.pvp.practice.listener.events.impl.tournament.round;

import lombok.Data;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.tournaments.Tournament;

@Data
public class TournamentStartRoundEvent extends CustomEvent {

    private final Tournament tournament;

}
