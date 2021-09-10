package surf.pvp.practice.leaderboard;

import surf.pvp.practice.SurfPractice;

import java.util.List;
import java.util.Map;

public interface LeaderboardAdapter {

    /**
     * Updates that certain leaderboard type
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    void update(SurfPractice surfPractice);

    /**
     * Gets the leaderboard
     *
     * @return {@link Map}
     */

    Map<LeaderboardType, List<Integer>> getLeaderboard();

    /**
     * Gets the list of integers
     * of a leaderboard type
     *
     * @param leaderboardType type of leaderboard
     * @return {@link List<Integer>}
     */

    default List<Integer> getList(LeaderboardType leaderboardType) {
        return getLeaderboard().get(leaderboardType);
    }

}
