package surf.pvp.practice.queue.impl;

import surf.pvp.practice.clan.Clan;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueType;

public class ClanKitQueue extends Queue<Clan> {

    /**
     * Queue Abstract Class
     *
     * @param kit kit to create the queue for
     */
    public ClanKitQueue(Kit kit) {
        super(QueueType.CLAN, kit);
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
     * @param clan object to remove
     */
    @Override
    public void remove(Clan clan) {

    }

    /**
     * Adds an object to the queue
     *
     * @param clan object to add
     */
    @Override
    public void add(Clan clan) {

    }
}
