package surf.pvp.practice.listener.events.impl.tournament.global;

import lombok.Data;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.tournaments.Tournament;

@Data
public class TournamentStartEvent extends CustomEvent {

    private final Tournament tournament;

}
