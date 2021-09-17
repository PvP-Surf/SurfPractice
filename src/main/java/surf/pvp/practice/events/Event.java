package surf.pvp.practice.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.event.EventEndEvent;
import surf.pvp.practice.listener.events.impl.event.PlayerJoinEventEvent;
import surf.pvp.practice.listener.events.impl.event.PlayerLeaveEventEvent;
import surf.pvp.practice.match.impl.EventMatch;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.util.CC;

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

    private int countdown;
    private int round;
    private int minimumPlayers;

    private EventStatus eventStatus = EventStatus.WAITING;

    public Event(Kit kit, Arena arena, int minimumPlayers) {
        this.kit = kit;

        this.minimumPlayers = minimumPlayers;
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
     * Ends the event
     *
     * @param winner winner of event
     * @param loser loser of event
     * @param round if round or not
     */

    public final void end(EventPlayer winner, EventPlayer loser, boolean round) {

        if (round) {
            this.removePlayer(loser.getPlayer());

            for (EventPlayer player : playerList) {
                player.getPlayer().sendMessage(CC.translate("&b" +
                        winner.getPlayer().getName() + " &fhas won round &b" + this.round + "&f!"));
            }

            this.round++;

            winner.setRoundWins(winner.getRoundWins() + 1);

            this.countdown = 5;
            this.eventStatus = EventStatus.IS_STARTING;

            Bukkit.getPluginManager().callEvent(new EventEndEvent(this, winner, loser, true));
            return;
        }

        this.removePlayer(loser.getPlayer());
        Bukkit.getPluginManager().callEvent(new EventEndEvent(this, winner, loser, false));
    }

    /**
     * Starts the event
     */

    public final void start() {
        Collections.shuffle(playerList);
        List<EventPlayer> playersNoPlay = playerList.stream().filter(eventPlayer -> eventPlayer.getRoundWins() == 0).collect(Collectors.toList());

        if (playersNoPlay.isEmpty()) {
            playersNoPlay = playerList;
        }

        final EventPlayer one = playersNoPlay.get(0);
        final EventPlayer two = playersNoPlay.get(1);

        final Player playerOne = one.getPlayer();
        final Player playerTwo = two.getPlayer();

        this.eventStatus = EventStatus.STARTED;

        this.runMatch(playerOne, playerTwo);
    }

    /**
     * Runs a match sync
     */

    public final synchronized void runMatch(Player playerOne, Player playerTwo) {
        new EventMatch(this, arena, kit, playerOne, playerTwo);
    }

}
