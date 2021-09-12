package surf.pvp.practice.queue;

import lombok.Getter;
import surf.pvp.practice.kit.Kit;

import java.util.LinkedList;
import java.util.List;

@Getter
public abstract class Queue<T> {

    protected final List<T> queue;
    protected final Kit kit;

    protected final QueueType queueType;
    protected final QueueRule queueRule;

    /**
     * Queue Abstract Class
     *
     * @param kit kit to create the queue for
     */

    public Queue(QueueType queueType, QueueRule queueRule, Kit kit) {
        this.kit = kit;
        this.queueRule = queueRule;

        this.queueType = queueType;
        this.queue = new LinkedList<>();
    }

    /**
     * Moves queue if elo
     */

    public void moveElo() {

    }

    /**
     * Moves the queue
     */

    public abstract void move();

    /**
     * Removes an object from
     * The queue
     *
     * @param t object to remove
     */

    public abstract void remove(T t);

    /**
     * Adds an object to the queue
     *
     * @param t object to add
     */

    public abstract void add(T t);

}
