package surf.pvp.practice.profile.joinitems;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;

import java.util.LinkedList;

public class JoinItemHandler implements Listener {
    private final LinkedList<JoinItem> joinItems = new LinkedList<>();

    public JoinItemHandler(SurfPractice plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        for(JoinItem jitem : this.joinItems){
            jitem.give(event.getPlayer());
        }
    }

}
