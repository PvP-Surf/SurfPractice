package surf.pvp.practice.listener.events.impl.event;

import lombok.Data;
import org.bukkit.entity.Player;
import surf.pvp.practice.events.Event;
import surf.pvp.practice.listener.events.CustomEvent;

@Data
public class PlayerJoinEventEvent extends CustomEvent {

    private final Player player;
    private final Event event;

}
