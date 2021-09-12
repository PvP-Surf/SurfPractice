package surf.pvp.practice.queue.impl;

import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueRule;
import surf.pvp.practice.queue.QueueType;

import java.util.UUID;

public class TeamKitQueue extends Queue<UUID> {

    /**
     * Queue Abstract Class
     *
     * @param kit kit to create the queue for
     */

    public TeamKitQueue(Kit kit) {
        super(QueueType.TEAM, QueueRule.NO_ELO, kit);
    }

    /**
     * Moves the queue
     */
    @Override
    public void move() {

    }

    /**
     * Removes an object from
     * The queue
     *
     * @param uuid object to remove
     */
    @Override
    public void remove(UUID uuid) {

    }

    /**
     * Adds an object to the queue
     *
     * @param uuid object to add
     */
    @Override
    public void add(UUID uuid) {

    }
}
