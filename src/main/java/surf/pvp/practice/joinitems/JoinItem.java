package surf.pvp.practice.joinitems;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class JoinItem {
    private final String name;
    protected final NBTItem nbtItem;

    public JoinItem(String name, ItemStack item){
        this.name = name;
        this.nbtItem = new NBTItem(item);

        nbtItem.setBoolean("joinitem",true);
        nbtItem.setString("joinitem_name",getName());
    }

    public abstract void give(Player player);

    public abstract void handle(PlayerInteractEvent event);

    public String getName(){
        return this.name;
    }
}
