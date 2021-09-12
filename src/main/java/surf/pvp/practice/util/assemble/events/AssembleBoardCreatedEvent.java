package surf.pvp.practice.util.assemble.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import surf.pvp.practice.util.assemble.AssembleBoard;

@Getter @Setter
public class AssembleBoardCreatedEvent extends Event {

    @Getter public static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;
    private final AssembleBoard board;

    /**
     * Assemble Board Created Event.
     *
     * @param board of player.
     */
    public AssembleBoardCreatedEvent(AssembleBoard board) {
        this.board = board;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
