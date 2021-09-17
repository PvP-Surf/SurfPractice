package surf.pvp.practice.listener.events.impl.event;

import lombok.Data;
import surf.pvp.practice.events.Event;
import surf.pvp.practice.events.EventPlayer;
import surf.pvp.practice.listener.events.CustomEvent;

@Data
public class EventEndEvent extends CustomEvent {

    private final Event event;

    private final EventPlayer winner;
    private final EventPlayer loser;

    private final boolean round;

}
