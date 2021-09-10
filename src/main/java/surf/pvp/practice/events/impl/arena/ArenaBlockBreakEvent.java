package surf.pvp.practice.events.impl.arena;

import lombok.Data;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.events.CustomEvent;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.match.Match;

@Data
public class ArenaBlockBreakEvent extends CustomEvent implements Cancellable {

    private final Player player;
    private final Arena arena;
    private final Kit kit;
    private final Match match;
    private final Block block;

    private boolean cancel;

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }
}
