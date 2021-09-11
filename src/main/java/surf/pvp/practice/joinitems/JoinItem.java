package surf.pvp.practice.joinitems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class JoinItem {
    private final String name;

    public JoinItem(String name){
        this.name = name;
    }

    public abstract void give(Player player);

    public abstract void handle(PlayerInteractEvent event);

    public String getName(){
        return this.name;
    }
}
