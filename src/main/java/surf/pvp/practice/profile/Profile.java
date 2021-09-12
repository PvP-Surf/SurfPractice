package surf.pvp.practice.profile;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import surf.pvp.practice.Locale;
import surf.pvp.practice.clan.Clan;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.party.Party;
import surf.pvp.practice.profile.hotbar.HotbarItem;
import surf.pvp.practice.profile.hotbar.HotbarLoadOut;
import surf.pvp.practice.profile.killeffect.KillEffectType;
import surf.pvp.practice.profile.loadout.CustomLoadOut;
import surf.pvp.practice.profile.remains.MatchRemains;
import surf.pvp.practice.profile.settings.impl.ProfileSettings;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.tournaments.Tournament;
import surf.pvp.practice.util.cooldown.SimpleCooldown;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Profile {

    private final UUID uuid;

    private final List<CustomLoadOut> customLoadOutMap = new ArrayList<>();
    private final Map<Kit, Integer> eloMap = new HashMap<>();
    private final List<Clan> clanList = new ArrayList<>();

    private int win, loss, kills, deaths, xp, coins;
    private KillEffectType killEffectType;
    private ProfileSettings settings;

    private ProfileState profileState = ProfileState.LOBBY;
    private SimpleCooldown enderPearlCooldown = new SimpleCooldown(Locale.ENDERPEARL.getInteger());
    private MatchRemains matchRemains = new MatchRemains();

    private Match match;
    private Party party;
    private Tournament tournament;
    private Queue<?> currentQueue;

    private boolean build;

    /**
     * Contructs a profile
     *
     * @param uuid uuid of the profile
     */

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the elo of a kit
     *
     * @param kit kit to get the elo of
     * @return {@link Integer}
     */

    public final int getElo(Kit kit) {
        return eloMap.get(kit);
    }

    /**
     * Gets the player of the profile
     *
     * @return {@link Player}
     */

    public final Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Gets the clan on the server the player is on
     *
     * @return {@link Clan}
     */

    public final Clan getClan() {
        return this.getClan(Locale.SERVER_NAME.getString());
    }

    /**
     * Gets the clan on the
     * server specified
     *
     * @param server server to get the clan off
     * @return {@link Clan}
     */

    public final Clan getClan(String server) {
        return clanList.stream().filter(clan -> clan.getServerName().equalsIgnoreCase(server)).findFirst().orElse(null);
    }

    /**
     * Gets all the loud outs of a kit
     *
     * @param kit kit to get the load outs of
     * @return {@link List<CustomLoadOut>}
     */

    public final List<CustomLoadOut> getLoudOut(Kit kit) {
        final List<CustomLoadOut> customLoadOuts = customLoadOutMap.stream().filter(loadOut -> loadOut.getKit().getName().equalsIgnoreCase(kit.getName())).collect(Collectors.toList());

        if (!customLoadOuts.isEmpty()) {
            customLoadOuts.add(0, new CustomLoadOut(kit, kit.getInventoryContents(), kit.getArmorContents()));
            return customLoadOuts;
        }

        return Collections.singletonList(new CustomLoadOut(kit, kit.getInventoryContents(), kit.getArmorContents()));
    }

    /**
     * Gives all books to the player
     *
     * @param player         player
     * @param customLoadOuts custom load outs
     */

    public final void giveBooks(Player player, List<CustomLoadOut> customLoadOuts) {
        customLoadOuts.forEach(customLoadOut -> {
            ItemStack stack = HotbarItem.BOOK.getItemStack();
            ItemMeta meta = stack.getItemMeta();

            meta.setDisplayName(meta.getDisplayName().replace("<number>", String.valueOf(customLoadOuts.indexOf(customLoadOut))));

            stack.setItemMeta(meta);

            player.getInventory().addItem(stack);
        });
    }

    /**
     * Sets the state of the profile and sets it's hotbar
     *
     * @param profileState state of profile
     */

    public final void setProfileState(ProfileState profileState) {
        this.profileState = profileState;
        this.updateHotbar();
    }

    /**
     * Updates the hotbar
     */

    public final void updateHotbar() {
        final Player player = getPlayer();

        if (player == null)
            return;

        switch (profileState) {
            case LOBBY:
                HotbarLoadOut.giveSpawnHotbar(player);
                break;
            case QUEUE:
                HotbarLoadOut.giveQueueItems(player);
            case IN_PARTY:
                HotbarLoadOut.givePartyItems(player);
                break;
            default:
                break;
        }
    }

    /**
     * Has match remains
     *
     * @return {@link Boolean}
     */

    public final boolean hasMatchRemains() {
        return matchRemains.getArmor() != null && matchRemains.getContents() != null;
    }

    /**
     * Creates a bson object and
     * stores data in it
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", uuid.toString())
                .append("win", win)
                .append("loss", loss)
                .append("kills", kills)
                .append("deaths", deaths)
                .append("effect", killEffectType == null ? "null" : killEffectType.name().toUpperCase())
                .append("xp", xp)
                .append("coins", coins)
                .append("name", getPlayer().getName())
                .append("settings", settings.toBson())
                .append("clans", clanList.stream().map(clan -> clan.getUuid().toString()).collect(Collectors.toList()))
                .append("elo", eloMap.entrySet().stream().map(kitIntegerEntry -> kitIntegerEntry.getKey().getName() + ":" + kitIntegerEntry.getValue()).collect(Collectors.toList()))
                .append("loadout", customLoadOutMap.stream().map(CustomLoadOut::serialize).collect(Collectors.toList()));
    }

}
