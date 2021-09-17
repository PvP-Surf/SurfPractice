package surf.pvp.practice.profile.killeffect.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.killeffect.KillEffectAdapter;
import surf.pvp.practice.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

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

        final List<ItemStack> stacks = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            double random = Math.random();

            if (random <= 0.25) {
                ItemStack stack = new ItemBuilder(Material.DIAMOND).name("staged").build();
                world.dropItemNaturally(location, stack);

                stacks.add(stack);
            } else if (random <= 0.5) {
                ItemStack stack = new ItemBuilder(Material.EMERALD).name("staged").build();
                world.dropItemNaturally(location, stack);

                stacks.add(stack);
            } else if (random <= 0.75) {
                ItemStack stack = new ItemBuilder(Material.GOLD_INGOT).name("staged").build();
                world.dropItemNaturally(location, stack);

                stacks.add(stack);
            } else if (random <= 1.00) {
                ItemStack stack = new ItemBuilder(Material.IRON_INGOT).name("staged").build();
                world.dropItemNaturally(location, stack);

                stacks.add(stack);
            }
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(SurfPractice.getInstance(), () -> stacks.forEach(itemStack -> itemStack.setType(Material.AIR)), 5 * 20L);
    }

}
