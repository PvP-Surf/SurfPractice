package surf.pvp.practice.events.tasks;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.events.Event;
import surf.pvp.practice.events.EventStatus;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;

@AllArgsConstructor
public class EventCountdownTask extends BukkitRunnable {

    private final SurfPractice surfPractice;

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
        for (Profile profile : surfPractice.getProfileHandler().getProfiles()) {

            if (profile.getEvent() == null)
                return;

            final Event event = profile.getEvent();

            if (event.getEventStatus().equals(EventStatus.WAITING) && event.getPlayerList().size() >= event.getMinimumPlayers()) {
                event.setEventStatus(EventStatus.IS_STARTING);
            }

            this.tick(event);
        }
    }

    /**
     * Does the task
     *
     * @param event event to change
     */

    public final void tick(Event event) {

        if (!event.getEventStatus().equals(EventStatus.IS_STARTING)) {
            return;
        }

        if (event.getCountdown() > 0) {
            event.setCountdown(event.getCountdown() - 1);

            event.getPlayerList().forEach(eventPlayer -> {
                eventPlayer.getPlayer().sendMessage(CC.translate("&fRound &b" + event.getRound() + " &fstarting in &b" + event.getCountdown() + "&f!"));
            });

        } else {
            event.start();
        }

    }

}
