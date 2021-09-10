package surf.pvp.practice.leaderboard.impl;

import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.leaderboard.LeaderboardAdapter;
import surf.pvp.practice.leaderboard.LeaderboardType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardElement implements LeaderboardAdapter {

    private final Map<LeaderboardType, List<Integer>> leaderboard = new HashMap<>();

    /**
     * Updates that certain leaderboard type
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    @Override
    public void update(SurfPractice surfPractice) {
        final List<Integer> killProfile = new ArrayList<>(), wins = new ArrayList<>();

        for (Document document : surfPractice.getMongoHandler().getProfiles().find()) {
            int kills = document.getInteger("kills");
            int win = document.getInteger("win");

            wins.add(win);
            killProfile.add(kills);
        }

        leaderboard.put(LeaderboardType.KILLS, killProfile);
        leaderboard.put(LeaderboardType.WINS, wins);
    }

    /**
     * Gets the leaderboard
     *
     * @return {@link Map}
     */

    @Override
    public Map<LeaderboardType, List<Integer>> getLeaderboard() {
        return leaderboard;
    }

}
