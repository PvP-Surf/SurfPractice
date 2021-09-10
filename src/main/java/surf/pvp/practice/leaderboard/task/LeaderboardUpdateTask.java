package surf.pvp.practice.leaderboard.task;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import surf.pvp.practice.leaderboard.LeaderboardHandler;

@AllArgsConstructor
public class LeaderboardUpdateTask extends BukkitRunnable {

    private final LeaderboardHandler leaderboardHandler;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    @Override
    public void run() {
        leaderboardHandler.getLeaderboardAdapter().update(leaderboardHandler.getSurfPractice());
    }
}
