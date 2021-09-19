package surf.pvp.practice.match;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.Locale;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.essentials.Essentials;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.CustomEvent;
import surf.pvp.practice.listener.events.impl.match.spectator.LeaveSpectateMatchEvent;
import surf.pvp.practice.listener.events.impl.match.spectator.SpectateMatchEvent;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Match {

    protected final Arena arena;
    protected final Kit kit;
    protected final MatchType matchType;

    protected final List<Player> spectators = new ArrayList<>();
    protected Player[] players;

    private int countdown = Locale.MATCH_COUNTDOWN.getInteger();
    private int timeInside;
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
        this.start();
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
     * Adds a spectator to a match
     *
     * @param player player to add
     */

    public final void addSpectator(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.SPECTATING);
        player.teleport(arena.getCenterPosition());

        spectators.add(player);
        Bukkit.getPluginManager().callEvent(new SpectateMatchEvent(player, this));
    }

    /**
     * Removes a spectator from a match
     *
     * @param player player to remove
     */

    public final void removeSpectator(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.LOBBY);
        player.teleport(Essentials.getInstance().getSpawnLocation());

        spectators.remove(player);
        Bukkit.getPluginManager().callEvent(new LeaveSpectateMatchEvent(player, this));
    }

    public final List<Player> getAllPlayers() {
        final List<Player> specs = new ArrayList<>(spectators);
        specs.addAll(Arrays.asList(players));
        return specs;
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

