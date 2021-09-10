package surf.pvp.practice.leaderboard;

import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.leaderboard.task.LeaderboardUpdateTask;

public class LeaderboardHandler {

    private final SurfPractice surfPractice;
    private final LeaderboardAdapter leaderboardAdapter;

    /**
     * Leaderboard manager
     * that manages all the tasks
     * and keeps the adapter in-check
     *
     * @param surfPractice       instance of {@link SurfPractice}
     * @param leaderboardAdapter adapter
     * @param ticks              ticks to run the task with
     */

    public LeaderboardHandler(SurfPractice surfPractice, LeaderboardAdapter leaderboardAdapter, long ticks) {
        this.surfPractice = surfPractice;
        this.leaderboardAdapter = leaderboardAdapter;

        new LeaderboardUpdateTask(this).runTaskTimerAsynchronously(surfPractice, ticks, ticks);
    }

    /**
     * Gets the instance of {@link SurfPractice}
     *
     * @return {@link SurfPractice}
     */

    public final SurfPractice getSurfPractice() {
        return surfPractice;
    }

    /**
     * Gets the leaderboard adapter registered
     *
     * @return {@link LeaderboardAdapter}
     */

    public final LeaderboardAdapter getLeaderboardAdapter() {
        return leaderboardAdapter;
    }

}
