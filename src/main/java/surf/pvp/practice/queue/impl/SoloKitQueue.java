package surf.pvp.practice.queue.impl;

import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.match.impl.SoloMatch;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueRule;
import surf.pvp.practice.queue.QueueType;

import java.util.UUID;

public class SoloKitQueue extends Queue<UUID> {

    /**
     * Queue Abstract Class
     *
     * @param kit kit to create the queue for
     */

    public SoloKitQueue(Kit kit, QueueRule queueRule) {
        super(QueueType.SOLO, queueRule, kit);
    }

    /**
     * Moves the queue
     */

    @Override
    public void move() {
        if (queue.isEmpty() || queue.size() < 2)
            return;

        if (queueRule.equals(QueueRule.ELO)) {
            this.moveElo();
            return;
        }

        final Arena arena = SurfPractice.getInstance().getArenaHandler().getAvailableArena(kit);

        if (arena == null) {
            return;
        }

        final Profile playerOneProfile = SurfPractice.getInstance().getProfileHandler().getProfile(queue.get(0));
        final Profile playerTwoProfile = SurfPractice.getInstance().getProfileHandler().getProfile(queue.get(1));

        queue.remove(0);
        queue.remove(1);

        new SoloMatch(arena, kit, playerOneProfile.getPlayer(), playerTwoProfile.getPlayer()).start();
    }

    /**
     * Removes an object from
     * The queue
     *
     * @param uuid object to remove
     */

    @Override
    public void remove(UUID uuid) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(uuid);

        profile.setProfileState(ProfileState.LOBBY);
        profile.setCurrentQueue(null);

        queue.remove(uuid);
    }

    /**
     * Adds an object to the queue
     *
     * @param uuid object to add
     */

    @Override
    public void add(UUID uuid) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(uuid);

        profile.setProfileState(ProfileState.QUEUE);
        profile.setCurrentQueue(this);

        queue.add(uuid);
    }

    /**
     * Moves queue if elo
     */

    @Override
    public void moveElo() {
        final Profile playerOneProfile = SurfPractice.getInstance().getProfileHandler().getProfile(queue.get(0));
        final Profile playerTwoProfile = SurfPractice.getInstance().getProfileHandler().getProfile(queue.get(1));

        int difference = Integer.compare(playerOneProfile.getElo(kit), playerTwoProfile.getElo(kit));

        //
    }

}
