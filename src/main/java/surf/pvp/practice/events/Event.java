package surf.pvp.practice.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.event.PlayerJoinEventEvent;
import surf.pvp.practice.listener.events.impl.event.PlayerLeaveEventEvent;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Event {

    private final List<EventPlayer> playerList = new ArrayList<>();
    private final List<Player> spectators = new ArrayList<>();

    private final Kit kit;
    private final Arena arena;

    private int round;

    public Event(Kit kit, Arena arena) {
        this.kit = kit;
        this.arena = arena;
    }

    /**
     * Adds a player to the event
     *
     * @param player player to add
     */

    public final void addPlayer(Player player) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.EVENT);
        profile.setEvent(this);

        playerList.add(new EventPlayer(player));
        Bukkit.getPluginManager().callEvent(new PlayerJoinEventEvent(player, this));
    }

    /**
     * Removes a player from the event
     *
     * @param player player to remove
     */

    public final void removePlayer(Player player) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.LOBBY);
        profile.setEvent(null);

        playerList.remove(getPlayer(player));
        Bukkit.getPluginManager().callEvent(new PlayerLeaveEventEvent(player, this));
    }

    /**
     * Gets all event players with zero
     * won rounds
     *
     * @return {@link EventPlayer}
     */

    public final List<EventPlayer> getPlayers() {
        return playerList.stream().filter(eventPlayer -> eventPlayer.getRoundWins() == 0).collect(Collectors.toList());
    }

    /**
     * Gets the event player of a player
     *
     * @param player player to get the event player of
     * @return {@link EventPlayer}
     */

    public final EventPlayer getPlayer(Player player) {
        return playerList.stream().filter(eventPlayer -> eventPlayer.getPlayer().equals(player)).findFirst().orElse(null);
    }

    /**
     * Starts the event
     */

    public final void start() {
        Collections.shuffle(playerList);
    }

}
