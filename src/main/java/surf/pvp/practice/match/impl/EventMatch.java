package surf.pvp.practice.match.impl;

import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.events.Event;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.MatchType;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.UUID;

public class EventMatch extends Match {

    private final Event event;

    /**
     * Constructs {@link Match}
     *
     * @param arena   arena
     * @param kit     kit
     * @param players players
     */

    public EventMatch(Event event, Arena arena, Kit kit, Player... players) {
        super(MatchType.EVENT, arena, kit, players);

        this.event = event;
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

            profile.setProfileState(ProfileState.EVENT_MATCH);
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
                profile.setProfileState(ProfileState.EVENT);
            } else {
                event.removePlayer(player);
            }

            winner.teleport(event.getArena().getEventLocation());
        }

        if (event.getPlayerList().size() == 1) {
            event.end(event.getPlayer(winner), event.getPlayer(loser), false);
        } else {
            event.end(event.getPlayer(winner), event.getPlayer(loser), true);
        }
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
