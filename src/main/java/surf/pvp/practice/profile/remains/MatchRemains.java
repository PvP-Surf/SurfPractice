package surf.pvp.practice.profile.remains;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchRemains {

    private ItemStack[] armor, contents;

    private List<PotionEffect> effectList;

    private double health;
    private double food;
    private int potionCount;

    public final MatchRemains update(Player player) {
        this.armor = player.getInventory().getArmorContents();
        this.contents = player.getInventory().getContents();

        this.health = player.getHealth();
        this.food = player.getFoodLevel();

        this.effectList = new ArrayList<>(player.getActivePotionEffects());
        this.potionCount = 0;

        for (ItemStack stack : contents) {
            if (stack != null && stack.getType().equals(Material.POTION)) {
                potionCount++;
            }
        }

        return this;
    }

}
