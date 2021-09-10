package surf.pvp.practice.match.impl;

import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.enums.LocationEnum;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.MatchType;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.tournaments.Tournament;

import java.util.UUID;

public class TournamentMatch extends Match {

    private final Tournament tournament;

    /**
     * Constructs {@link Match}
     *
     * @param arena   arena
     * @param kit     kit
     * @param players players
     */
    public TournamentMatch(Arena arena, Kit kit, Tournament tournament, Player... players) {
        super(MatchType.TOURNAMENT, arena, kit, players);

        this.tournament = tournament;
    }

    /**
     * what to do on match start
     */

    @Override
    public void start() {
        final Player one = getPlayerOne();
        final Player two = getPlayerTwo();

        one.teleport(arena.getPositionOne());
        two.teleport(arena.getPositionTwo());

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setProfileState(ProfileState.IN_TOURNAMENT_MATCH);
            profile.setMatch(this);

            profile.giveBooks(player, profile.getLoudOut(kit));
        }

        this.callEvent(new MatchStartEvent(one, two, this));
        this.setMatchStatus(MatchStatus.START_COUNTDOWN);
    }

    /**
     * what to do on match end
     *
     * @param uuid     uuid of the winner
     * @param forceEnd if it was force ended
     */

    @Override
    public void end(UUID uuid, boolean forceEnd) {
        Player winner;
        Player loser;

        if (forceEnd) {
            winner = getOpposingPlayer(uuid);
            loser = winner.getUniqueId().equals(getPlayerOne().getUniqueId()) ? getPlayerTwo() : getPlayerTwo();
        } else {
            winner = getPlayerOne().getUniqueId().equals(uuid) ? getPlayerOne() : getPlayerTwo();
            loser = winner.getUniqueId().equals(getPlayerOne().getUniqueId()) ? getPlayerOne() : getPlayerTwo();
        }

        this.setMatchStatus(MatchStatus.ENDED);
        this.callEvent(new MatchEndEvent(winner, loser, this));

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(null);

            if (player.getUniqueId().equals(winner.getUniqueId())) {
                profile.setProfileState(ProfileState.IN_TOURNAMENT_LOBBY);
            } else {
                tournament.removePlayer(player);
            }

            player.teleport(LocationEnum.SPAWN.getLocation());
        }

        tournament.end(winner.getUniqueId());
    }

    public final Player getOpposingPlayer(UUID uuid) {
        return getPlayerOne().getUniqueId().equals(uuid) ? getPlayerTwo() : getPlayerOne();
    }

    public final Player getPlayerOne() {
        return players[0];
    }

    public final Player getPlayerTwo() {
        return players[1];
    }

}
