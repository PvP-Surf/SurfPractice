package surf.pvp.practice.events;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class EventPlayer {

    private final Player player;

    private int roundWins;
    private int roundLoses;

}
