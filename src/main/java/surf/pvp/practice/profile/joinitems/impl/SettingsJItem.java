package surf.pvp.practice.profile.joinitems.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import surf.pvp.practice.profile.joinitems.JoinItem;
import surf.pvp.practice.util.ItemBuilder;

public class SettingsJItem extends JoinItem {

    public SettingsJItem(){
        super("settings",new ItemBuilder(Material.NETHER_STAR)
                .name("&eSettings")
                .lore("&eSettings customizer")
                .build());
    }

    @Override
    public void give(Player player) {
        player.getInventory().setItem(5,nbtItem.getItem());
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        // Check for right click and open the settings gui
    }
}
