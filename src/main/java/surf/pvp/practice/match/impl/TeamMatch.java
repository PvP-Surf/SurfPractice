package surf.pvp.practice.match.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.enums.LocationEnum;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.match.team.MatchTeamEndEvent;
import surf.pvp.practice.listener.events.impl.match.team.MatchTeamStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.MatchType;
import surf.pvp.practice.match.objects.MatchTeam;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.util.CC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class TeamMatch extends Match {

    private final MatchTeam matchTeamOne;
    private final MatchTeam matchTeamTwo;

    /**
     * Team Match
     *
     * @param arena arena
     * @param kit kit
     * @param players players
     */

    public TeamMatch(Arena arena, Kit kit, Player... players) {
        super(MatchType.TEAM, arena, kit, players);

        final List<List<Player>> splitList = CC.split(Arrays.asList(players));

        this.matchTeamOne = new MatchTeam(1, splitList.get(0));
        this.matchTeamTwo = new MatchTeam(2, splitList.get(1));
    }

    /**
     * start logic
     */

    @Override
    public void start() {
        matchTeamOne.getPlayers().forEach(player -> player.teleport(arena.getPositionOne()));
        matchTeamTwo.getPlayers().forEach(player -> player.teleport(arena.getPositionTwo()));

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(this);
            profile.setProfileState(ProfileState.IN_MATCH);

            profile.giveBooks(player, profile.getLoudOut(kit));
        }

        this.callEvent(new MatchTeamStartEvent(matchTeamOne, matchTeamTwo, this));
        this.setMatchStatus(MatchStatus.START_COUNTDOWN);
    }

    /**
     * ending logic
     *
     * @param uuid uuid of the winner
     * @param forced if forced
     */

    @Override
    public void end(UUID uuid, boolean forced) {
        if (uuid == null)
            return;

        MatchTeam winner;
        MatchTeam loser;

        if (forced) {
            winner = getOpposingMatchTeam(uuid);
            loser = winner.getId() == matchTeamOne.getId() ? matchTeamTwo : matchTeamOne;
        } else {
            winner = getMatchTeam(uuid).getId() == 1 ? matchTeamOne : matchTeamTwo;
            loser = winner.getId() == matchTeamOne.getId() ? matchTeamTwo : matchTeamOne;
        }

        this.setMatchStatus(MatchStatus.ENDED);
        this.callEvent(new MatchTeamEndEvent(winner, loser, this));

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(null);
            profile.setProfileState(ProfileState.LOBBY);

            player.teleport(LocationEnum.SPAWN.getLocation());
        }
    }

    /**
     * Gets the match team of a player
     *
     * @param player player to get the match team of
     * @return {@link MatchTeam}
     */

    public final MatchTeam getMatchTeam(Player player) {
        return matchTeamOne.getPlayers().contains(player) ? matchTeamOne : matchTeamTwo;
    }

    /**
     * Gets the opposing match team of a player/uuid
     *
     * @param uuid uuid to get the opposing match team off
     * @return {@link MatchTeam}
     */

    public final MatchTeam getOpposingMatchTeam(UUID uuid) {
        return getMatchTeam(uuid).getId() == matchTeamOne.getId() ? matchTeamTwo : matchTeamOne;
    }

    /**
     * Gets the match team of a player
     *
     * @param uuid player to get the match team of
     * @return {@link MatchTeam}
     */

    public final MatchTeam getMatchTeam(UUID uuid) {
        return matchTeamOne.getPlayers().stream().anyMatch(player -> player.getUniqueId().equals(uuid)) ? matchTeamOne : matchTeamTwo;
    }

}