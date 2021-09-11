package surf.pvp.practice.profile.hotbar;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.profile.hotbar.adapter.HotbarAdapter;
import surf.pvp.practice.profile.hotbar.adapter.impl.BookHotbarItem;
import surf.pvp.practice.util.ItemBuilder;

@Getter
public enum HotbarItem {

    BOOK(new ItemBuilder(Material.BOOK).enchant(Enchantment.DURABILITY).name("LoudOut <number>").build(), new BookHotbarItem());

    private final ItemStack itemStack;
    private final HotbarAdapter hotbarAdapter;

    HotbarItem(ItemStack itemStack, HotbarAdapter hotbarAdapter) {
        this.itemStack = itemStack;
        this.hotbarAdapter = hotbarAdapter;
    }

    public final void giveItem(Player player) {
        player.getInventory().addItem(itemStack);
    }

    public final void setItem(Player player, int index) {
        player.getInventory().setItem(index, itemStack);
    }

}
