package surf.pvp.practice.kit;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueType;
import surf.pvp.practice.queue.impl.PartyKitQueue;
import surf.pvp.practice.queue.impl.SoloKitQueue;
import surf.pvp.practice.queue.impl.TeamKitQueue;
import surf.pvp.practice.util.ItemBuilder;
import surf.pvp.practice.util.Serializer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Kit {

    private final String name;
    private final boolean elo;

    private final List<Queue<?>> queues = new ArrayList<>();

    private int priority;
    private Material icon = Material.GOLD_SWORD;
    private String color = "&b&l";

    private KitType kitType = KitType.NO_BUILD;
    private ItemStack[] armorContents, inventoryContents;

    /**
     * Kit Constructor
     *
     * @param name name of kit
     * @param elo  if kit is elo or not
     */

    public Kit(String name, boolean elo) {
        this.name = name;
        this.elo = elo;

        this.queues.add(new SoloKitQueue(this));
        this.queues.add(new TeamKitQueue(this));
        this.queues.add(new PartyKitQueue(this));
    }

    /**
     * Loads a kit from a mongo document
     *
     * @param document document to load the kit from
     */

    @SneakyThrows
    public Kit(Document document) {
        this.name = document.getString("_id");
        this.elo = document.getBoolean("elo");

        this.kitType = KitType.valueOf(document.getString("type").toUpperCase());

        this.armorContents = Serializer.itemStackArrayFromBase64(document.getString("armor"));
        this.inventoryContents = Serializer.itemStackArrayFromBase64(document.getString("inventory"));
        this.priority = document.getInteger("priority");
        this.icon = Material.valueOf(document.getString("icon").toUpperCase());
        this.color = document.getString("color");

        this.queues.add(new SoloKitQueue(this));
        this.queues.add(new TeamKitQueue(this));
        this.queues.add(new PartyKitQueue(this));
    }

    /**
     * Gets a queue based on it's type
     *
     * @param queueType type of queue
     * @return {@link Queue}
     */

    public final Queue<?> getQueue(QueueType queueType) {
        return queues.stream().filter(queue -> queue.getQueueType().equals(queueType)).findFirst().orElse(null);
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
     * Saves the kit to the mongo
     * database
     *
     * @param surfPractice instance of {@link SurfPractice}
     * @param async        if task should be ran async or not
     */

    public final void save(SurfPractice surfPractice, boolean async) {

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> save(surfPractice, false));
            return;
        }

        final Document document = surfPractice.getMongoHandler().getKits().find(Filters.eq("_id", name)).first();

        if (document == null) {
            surfPractice.getMongoHandler().getKits().insertOne(toBson());
            return;
        }

        surfPractice.getMongoHandler().getKits().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    /**
     * Gets the stack of the kit
     *
     * @return {@link ItemStack}
     */

    public final ItemStack toStack() {
        return new ItemBuilder(icon).name(color + name).build();
    }

    /**
     * Turns data to mongo document
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", name)
                .append("elo", elo)
                .append("icon", icon.name().toUpperCase())
                .append("color", color)
                .append("priority", priority)
                .append("type", kitType.name().toUpperCase())
                .append("armor", Serializer.itemStackArrayToBase64(armorContents))
                .append("inventory", Serializer.itemStackArrayToBase64(inventoryContents));
    }

}
