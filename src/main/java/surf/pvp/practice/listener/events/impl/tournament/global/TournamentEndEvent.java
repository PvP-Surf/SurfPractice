package surf.pvp.practice.listener.events.impl.tournament.global;

import lombok.Data;
import org.bukkit.entity.Player;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.tournaments.Tournament;

@Data
public class TournamentEndEvent extends CustomEvent {

    private final Tournament tournament;
    private final Player winner;

}
