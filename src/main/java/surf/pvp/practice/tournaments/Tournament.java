package surf.pvp.practice.tournaments;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.tournament.global.TournamentEndEvent;
import surf.pvp.practice.listener.events.impl.tournament.global.TournamentStartEvent;
import surf.pvp.practice.listener.events.impl.tournament.player.PlayerJoinTournamentEvent;
import surf.pvp.practice.listener.events.impl.tournament.player.PlayerLeaveTournamentEvent;
import surf.pvp.practice.listener.events.impl.tournament.round.TournamentEndRoundEvent;
import surf.pvp.practice.match.impl.TournamentMatch;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Tournament {

    private final Kit kit;

    private final List<Player> playerList = new ArrayList<>();
    private List<Player> originalList = new ArrayList<>();

    private TournamentState tournamentState = TournamentState.WAITING;
    private int round = 1, countdown = 5;

    /**
     * Starts a tournament
     *
     * @param kit kit of tournament
     */

    public Tournament(Kit kit) {
        this.kit = kit;
    }

    /**
     * Adds a player to the tournament
     *
     * @param player player to add
     */

    public final void addPlayer(Player player) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        profile.setProfileState(ProfileState.IN_TOURNAMENT_LOBBY);

        profile.setTournament(this);

        playerList.add(player);
        Bukkit.getPluginManager().callEvent(new PlayerJoinTournamentEvent(this, player));
    }

    /**
     * Removes a player from the tournament
     *
     * @param player player to remove
     */

    public final void removePlayer(Player player) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        profile.setProfileState(ProfileState.LOBBY);

        profile.setTournament(null);

        playerList.remove(player);
        Bukkit.getPluginManager().callEvent(new PlayerLeaveTournamentEvent(this, player));
    }

    /**
     * Ends the tournament
     *
     * @param uuid uuid of winner
     */

    public final void end(UUID uuid) {

        if (playerList.size() > 2) {
            final List<Player> playersInMatch = playerList.stream().filter(player ->
                            SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId())
                                    .getMatch() == null).collect(Collectors.toList());

            if (playersInMatch.isEmpty()) {
                Bukkit.getPluginManager().callEvent(new TournamentEndRoundEvent(this));

                this.tournamentState = TournamentState.ENDED_ROUND;

                round++;

                this.tournamentState = TournamentState.STARTING_ROUND;
                this.countdown = 5;
            }

            return;
        }

        final Player playerOne = playerList.get(0);
        final Player playerTwo = playerList.get(1);

        this.tournamentState = TournamentState.ENDED;

        final Player winner = playerOne.getUniqueId().equals(uuid) ? playerOne : playerTwo;
        final TournamentEndEvent tournamentEndEvent = new TournamentEndEvent(this, winner);

        Bukkit.getPluginManager().callEvent(tournamentEndEvent);
        SurfPractice.getInstance().getTournamentHandler().getTournaments().remove(this);
    }

    /**
     * Starts matches between players
     */

    public final void startMatch() {
        if (playerList.isEmpty()) {
            return;
        }

        if (originalList.isEmpty()) {
            originalList = playerList;

            tournamentState = TournamentState.STARTED;
            Bukkit.getPluginManager().callEvent(new TournamentStartEvent(this));
        }

        final List<Player> playersToMove = playerList;

        for (int i = 0; i < playersToMove.size() / 2; i++) {
            final Player playerOne = playersToMove.get(0);
            final Player playerTwo = playersToMove.get(1);

            final Arena arena = SurfPractice.getInstance().getArenaHandler().getAvailableArena(kit);

            if (arena == null)
                return;

            playersToMove.remove(0);
            playersToMove.remove(1);

            new TournamentMatch(arena, kit, this, playerOne, playerTwo);
        }

    }

}
