package surf.pvp.practice.profile.loadout;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.util.Serializer;

@Getter
public class CustomLoadOut {

    private final Kit kit;
    private final ItemStack[] contents;
    private final ItemStack[] armor;

    /**
     * Custom load out constructor
     *
     * @param kit      kit to set the custom load out of
     * @param contents contents of the custom load out
     * @param armor    armor of the custom load out
     */

    public CustomLoadOut(Kit kit, ItemStack[] contents, ItemStack[] armor) {
        this.kit = kit;

        this.contents = contents;
        this.armor = armor;
    }

    /**
     * De-serializes a custom
     * load out
     *
     * @param s string to de-serialize the load out of
     * @return {@link CustomLoadOut}
     */

    @SneakyThrows
    public static CustomLoadOut get(String s) {
        String[] args = s.split("///");
        return new CustomLoadOut(SurfPractice.getInstance().getKitHandler().getKit(args[0]), Serializer.itemStackArrayFromBase64(args[1]), Serializer.itemStackArrayFromBase64(args[2]));
    }

    /**
     * Serializes a custom load out
     *
     * @return {@link String}
     */

    public final String serialize() {
        return kit.getName() + "///" + Serializer.itemStackArrayToBase64(contents) + "///" + Serializer.itemStackArrayToBase64(armor);
    }

    /**
     * Sets the player's inventory
     *
     * @param player player to set the invenotry of
     */

    public final void update(Player player) {
        player.getInventory().setArmorContents(armor);
        player.getInventory().setContents(contents);
    }

}
