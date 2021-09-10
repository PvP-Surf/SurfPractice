package surf.pvp.practice.profile.hotbar;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.loadout.CustomLoadOut;
import surf.pvp.practice.util.ItemBuilder;

import java.util.List;
import java.util.function.Consumer;

@Getter
public enum HotbarItem {

    BOOK(new ItemBuilder(Material.BOOK).enchant(Enchantment.DURABILITY).name("LoudOut <number>").build(), event -> {
        Player player = event.getPlayer();
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        Match match = profile.getMatch();
        ItemStack stack = event.getItem();

        if (match == null)
            return;

        String[] args = stack.getItemMeta().getDisplayName().split("\\s+");

        List<CustomLoadOut> loadOuts = profile.getLoudOut(match.getKit());
        CustomLoadOut customLoadOut = loadOuts.get(Integer.parseInt(args[1]));

        if (customLoadOut == null) {
            customLoadOut = loadOuts.get(loadOuts.size() - 1);
        }

        customLoadOut.update(player);
    });

    private final ItemStack itemStack;
    private final Consumer<PlayerInteractEvent> playerInteractEventConsumer;

    HotbarItem(ItemStack itemStack, Consumer<PlayerInteractEvent> playerInteractEventConsumer) {
        this.itemStack = itemStack;
        this.playerInteractEventConsumer = playerInteractEventConsumer;
    }

    public final void giveItem(Player player) {
        player.getInventory().addItem(itemStack);
    }

    public final void setItem(Player player, int index) {
        player.getInventory().setItem(index, itemStack);
    }

}
