package surf.pvp.practice.joinitems;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class JoinItemHandler implements Listener {
    private final LinkedList<JoinItem> joinItems = new LinkedList<>();

    public void registerJoinItem(JoinItem joinItem){
        this.joinItems.add(joinItem);
    }

    public JoinItem getJoinItem(String name){
        for(JoinItem joinItem : this.joinItems){
            if(joinItem.getName().equalsIgnoreCase(name)){
                return joinItem;
            }
        }

        return null;
    }

    @EventHandler
    public void onPlayerClickJoinItem(PlayerInteractEvent event){
        ItemStack item = event.getItem();

        if(item == null || item.getType() == Material.AIR){
            return;
        }

        NBTItem nbtItem = new NBTItem(item);
        if(nbtItem.hasKey("joinitem") && nbtItem.getBoolean("joinitem")){
            JoinItem jitem = this.getJoinItem(nbtItem.getString("joinitem_name"));

            if(jitem != null){
                jitem.handle(event);
            }
        }
    }
}
