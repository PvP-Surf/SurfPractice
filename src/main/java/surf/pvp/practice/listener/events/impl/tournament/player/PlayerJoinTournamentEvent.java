package surf.pvp.practice.listener.events.impl.tournament.player;

import lombok.Data;
import org.bukkit.entity.Player;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.tournaments.Tournament;

@Data
public class PlayerJoinTournamentEvent extends CustomEvent {

    private final Tournament tournament;
    private final Player player;

}
