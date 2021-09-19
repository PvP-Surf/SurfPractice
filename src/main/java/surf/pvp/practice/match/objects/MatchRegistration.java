package surf.pvp.practice.match.objects;

import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.match.listener.MatchListener;
import surf.pvp.practice.match.listener.SpectatorListener;
import surf.pvp.practice.match.task.MatchCountDownTask;

public class MatchRegistration {

    /**
     * Registers all match related stuff
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public MatchRegistration(SurfPractice surfPractice) {
        new MatchCountDownTask(surfPractice.getProfileHandler()).runTaskTimerAsynchronously(surfPractice, 20L, 20L);

        surfPractice.getServer().getPluginManager().registerEvents(new MatchListener(surfPractice), surfPractice);
        surfPractice.getServer().getPluginManager().registerEvents(new SpectatorListener(surfPractice), surfPractice);
    }

}
