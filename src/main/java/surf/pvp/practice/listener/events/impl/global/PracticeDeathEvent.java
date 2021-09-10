package surf.pvp.practice.listener.events.impl.global;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import surf.pvp.practice.listener.events.CustomEvent;

import java.util.Optional;

@Data
public class PracticeDeathEvent extends CustomEvent implements Cancellable {

    private final Player player;
    private final Optional<Player> killer;
    private double damage;

    private boolean cancel;

    public PracticeDeathEvent(Player player, Player killer, double damage) {
        this.player = player;
        this.killer = Optional.ofNullable(killer);
        this.damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }

}
