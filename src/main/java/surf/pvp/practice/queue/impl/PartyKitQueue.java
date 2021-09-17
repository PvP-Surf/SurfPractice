package surf.pvp.practice.queue.impl;

import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.party.Party;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueRule;
import surf.pvp.practice.queue.QueueType;

public class PartyKitQueue extends Queue<Party> {

    /**
     * Queue Abstract Class
     *
     * @param kit kit to create the queue for
     */
    public PartyKitQueue(Kit kit) {
        super(QueueType.PARTY, QueueRule.NO_ELO, kit);
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
     * @param party object to remove
     */
    @Override
    public void remove(Party party) {

    }

    /**
     * Adds an object to the queue
     *
     * @param party object to add
     */
    @Override
    public void add(Party party) {

    }
}
