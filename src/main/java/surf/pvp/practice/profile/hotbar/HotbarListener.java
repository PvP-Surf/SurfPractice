package surf.pvp.practice.profile.hotbar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HotbarListener implements Listener {

    @EventHandler
    public final void onPlayerInteractEvent(PlayerInteractEvent event) {
        ItemStack stack = event.getPlayer().getItemInHand();
        Player player = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR))
            return;

        if (stack == null || stack.getItemMeta() == null)
            return;

        for (HotbarItem hotbarItem : HotbarItem.values()) {
            ItemStack hotStack = hotbarItem.getItemStack();

            if (hotStack.isSimilar(stack)) {
                hotbarItem.getHotbarAdapter().handle(event);
                break;
            }
        }

    }

}
