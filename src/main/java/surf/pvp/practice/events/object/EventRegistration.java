package surf.pvp.practice.events.object;

import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.events.listener.EventListener;
import surf.pvp.practice.events.tasks.EventCountdownTask;

public class EventRegistration {


    /**
     * Registers all event related objects
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public EventRegistration(SurfPractice surfPractice) {
        new EventCountdownTask(surfPractice).runTaskTimerAsynchronously(surfPractice, 20L, 20L);
        surfPractice.getServer().getPluginManager().registerEvents(new EventListener(surfPractice), surfPractice);
    }

}
