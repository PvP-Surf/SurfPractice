package surf.pvp.practice.listener.events.impl.match.spectator;

import lombok.Data;
import org.bukkit.entity.Player;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.match.Match;

@Data
public class LeaveSpectateMatchEvent extends CustomEvent {

    private final Player player;
    private final Match match;

}
