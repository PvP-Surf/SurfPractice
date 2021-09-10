package surf.pvp.practice.match;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.enums.LocationEnum;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.listener.events.impl.match.spectator.LeaveSpectateMatchEvent;
import surf.pvp.practice.listener.events.impl.match.spectator.SpectateMatchEvent;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Match {

    protected final Arena arena;
    protected final Kit kit;
    protected final MatchType matchType;

    protected final Player[] players;
    protected final List<Player> spectators = new ArrayList<>();

    private int countdown = 5;
    private MatchStatus matchStatus = MatchStatus.WAITING;

    /**
     * Constructs {@link Match}
     *
     * @param matchType match type
     * @param arena     arena
     * @param kit       kit
     * @param players   players
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

    public final void addSpectator(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.SPECTATING);
        player.teleport(arena.getCenterPosition());

        spectators.add(player);
        Bukkit.getPluginManager().callEvent(new SpectateMatchEvent(player, this));
    }

    public final void removeSpectator(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.LOBBY);
        player.teleport(LocationEnum.SPAWN.getLocation());

        spectators.remove(player);
        Bukkit.getPluginManager().callEvent(new LeaveSpectateMatchEvent(player, this));
    }

    /**
     * what to do on match start
     */

    public abstract void start();

    /**
     * what to do on match end
     *
     * @param uuid     uuid of the winner
     * @param forceEnd if it was force ended
     */

    public abstract void end(UUID uuid, boolean forceEnd);

}

