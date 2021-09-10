package surf.pvp.practice.leaderboard;

import surf.pvp.practice.SurfPractice;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    Map<LeaderboardType, TreeMap<Integer, String>> getLeaderboard();

    /**
     * Gets the list of integers
     * of a leaderboard type
     *
     * @param leaderboardType type of leaderboard
     * @return {@link List<Integer>}
     */

    default TreeMap<Integer, String> getTreeMap(LeaderboardType leaderboardType) {
        return getLeaderboard().get(leaderboardType);
    }

}
