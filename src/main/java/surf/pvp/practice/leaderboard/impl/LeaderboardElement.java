package surf.pvp.practice.leaderboard.impl;

import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.leaderboard.LeaderboardAdapter;
import surf.pvp.practice.leaderboard.LeaderboardType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LeaderboardElement implements LeaderboardAdapter {

    private final Map<LeaderboardType, TreeMap<Integer, String>> leaderboard = new HashMap<>();

    /**
     * Updates that certain leaderboard type
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    @Override
    public void update(SurfPractice surfPractice) {
        final TreeMap<Integer, String> killProfile = new TreeMap<>(Comparator.comparingInt(o -> o)), wins = new TreeMap<>(Comparator.comparingInt(o -> o));

        for (Document document : surfPractice.getMongoHandler().getProfiles().find()) {
            String name = document.getString("name");

            int kills = document.getInteger("kills");
            int win = document.getInteger("win");

            wins.put(kills, name);
            killProfile.put(win, name);
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
    public Map<LeaderboardType, TreeMap<Integer, String>> getLeaderboard() {
        return leaderboard;
    }

}
