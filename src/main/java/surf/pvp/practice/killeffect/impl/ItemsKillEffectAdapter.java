package surf.pvp.practice.killeffect.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.killeffect.KillEffectAdapter;
import surf.pvp.practice.util.ItemBuilder;

public class ItemsKillEffectAdapter implements KillEffectAdapter {

    /**
     * Displays the effect to the player
     *
     * @param player player to display the effect to
     * @param killed player that was killed
     */

    @Override
    public void display(Player player, Player killed) {
        final Location location = killed.getLocation();
        final World world = location.getWorld();

        for (int i = 0; i < 50; i++) {
            double random = Math.random();

            if (random <= 0.25) {
                ItemStack stack = new ItemBuilder(Material.DIAMOND).name("staged").build();
                world.dropItemNaturally(location, stack);
            } else if (random <= 0.5) {
                ItemStack stack = new ItemBuilder(Material.EMERALD).name("staged").build();
                world.dropItemNaturally(location, stack);
            } else if (random <= 0.75) {
                ItemStack stack = new ItemBuilder(Material.GOLD_INGOT).name("staged").build();
                world.dropItemNaturally(location, stack);
            } else if (random <= 1.00) {
                ItemStack stack = new ItemBuilder(Material.IRON_INGOT).name("staged").build();
                world.dropItemNaturally(location, stack);
            }
        }
    }

}
