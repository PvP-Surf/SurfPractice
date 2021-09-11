package surf.pvp.practice.menu.queue;

import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueType;
import surf.pvp.practice.util.menu.Menu;
import surf.pvp.practice.util.menu.buttons.Button;

import java.util.HashMap;
import java.util.Map;

public class QueueMenu extends Menu {

    private final QueueType queue;
    private final boolean elo;

    public QueueMenu(Player player, String title, int size, QueueType queue, boolean elo) {
        super(player, WordUtils.capitalizeFully(queue.name()) + " Queue", size);

        this.elo = elo;
        this.queue = queue;
    }

    /**
     * The method to get the buttons for the current inventory tick
     * <p>
     * a button to a slot.
     */

    @Override
    public Map<Integer, Button> getButtons() {
        final Map<Integer, Button> buttonMap = new HashMap<>();

        for (Kit kit : SurfPractice.getInstance().getKitHandler().getKits(queue, elo)) {
            buttonMap.put(kit.getPriority(), new Button(kit.toStack()).setClickAction(event -> {
                event.setCancelled(true);

                final Player player = getPlayer();
                player.chat("/unranked " + kit);
            }));
        }

        return null;
    }

}
