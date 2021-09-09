package surf.pvp.practice.match.task;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import surf.pvp.practice.events.impl.match.global.MatchEndCountdownEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileHandler;

@AllArgsConstructor
public class MatchCountDownTask extends BukkitRunnable {

    private final ProfileHandler profileHandler;

    /**
     * Counts down and sets the match to start
     */

    @Override
    public void run() {
        for (Profile profile : profileHandler.getProfiles()) {
            Match match = profile.getMatch();

            if (match == null)
                return;

            if (!match.getMatchStatus().equals(MatchStatus.START_COUNTDOWN))
                return;

            if (match.getCountdown() <= 0) {
                match.setMatchStatus(MatchStatus.STARTED);
                match.callEvent(new MatchEndCountdownEvent(match));
            } else {
                match.setCountdown(match.getCountdown() - 1);
            }
        }
    }

}
