package surf.pvp.practice.kit;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.util.Serializer;

@Getter
@Setter
public class Kit {

    private final String name;
    private final boolean elo;

    private KitType kitType = KitType.NO_BUILD;
    private ItemStack[] armorContents, inventoryContents;

    /**
     * Kit Constructor
     *
     * @param name name of kit
     * @param elo if kit is elo or not
     */

    public Kit(String name, boolean elo) {
        this.name = name;
        this.elo = elo;
    }

    /**
     * Applies the kit to a player
     *
     * @param player player to apply kit to
     */

    public void apply(Player player) {
        player.getInventory().setArmorContents(armorContents);
        player.getInventory().setContents(inventoryContents);
    }

    /**
     * Turns data to mongo document
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", name)
                .append("elo", elo)
                .append("type", kitType.name().toUpperCase())
                .append("armor", Serializer.itemStackArrayToBase64(armorContents))
                .append("inventory", Serializer.itemStackArrayToBase64(inventoryContents));
    }

}
