package surf.pvp.practice.match;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.events.CustomEvent;
import surf.pvp.practice.kit.Kit;

import java.util.UUID;

@Getter
@Setter
public abstract class Match {

    protected final Arena arena;
    protected final Kit kit;
    protected final MatchType matchType;

    protected final Player[] players;

    private int countdown = 5;
    private MatchStatus matchStatus = MatchStatus.WAITING;

    /**
     * Constructs {@link Match}
     *
     * @param matchType match type
     * @param arena arena
     * @param kit kit
     * @param players players
     */

    public Match(MatchType matchType, Arena arena, Kit kit, Player... players) {
        this.matchType = matchType;
        this.arena = arena;
        this.kit = kit;

        this.players = players;
    }

    /**
     * Calls a custom event
     *
     * @param customEvent {@link CustomEvent}
     */

    public final void callEvent(CustomEvent customEvent) {
        Bukkit.getPluginManager().callEvent(customEvent);
    }

    /**
     * what to do on match start
     */

    public abstract void start();

    /**
     * what to do on match end
     *
     * @param uuid uuid of the winner
     * @param forceEnd if it was force ended
     */

    public abstract void end(UUID uuid, boolean forceEnd);

}
