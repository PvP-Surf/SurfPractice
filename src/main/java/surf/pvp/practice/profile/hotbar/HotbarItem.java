package surf.pvp.practice.profile.hotbar;

import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.profile.hotbar.adapter.HotbarAdapter;
import surf.pvp.practice.profile.hotbar.adapter.impl.core.BookHotbarItem;
import surf.pvp.practice.profile.hotbar.adapter.impl.core.SettingsHotbarItem;
import surf.pvp.practice.profile.hotbar.adapter.impl.core.ViewLeaderboardsItem;
import surf.pvp.practice.profile.hotbar.adapter.impl.party.PartyCreateItem;
import surf.pvp.practice.profile.hotbar.adapter.impl.queue.*;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.ItemBuilder;

@Getter
public enum HotbarItem {

    BOOK(new ItemBuilder(Material.BOOK).enchant(Enchantment.DURABILITY).name("Loudout <number>").build(), new BookHotbarItem()),
    UNRANKED(new ItemBuilder(Material.STONE_SWORD).name("&b&lUnranked Queue").build(), new UnrankedQueueItem()),
    RANKED(new ItemBuilder(Material.DIAMOND_SWORD).name("&b&lRanked Queue").build(), new RankedQueueItem()),
    PREMIUM(new ItemBuilder(Material.GOLD_SWORD).name("&b&lPremium Queue").build(), new PremiumQueueItem()),
    YOUTUBE(new ItemBuilder(Material.WOOD_SWORD).name("&b&lYoutuber Queue").build(), new YoutuberQueueItem()),
    LEAVE_QUEUE(new ItemBuilder(Material.INK_SACK).data(DyeColor.RED.getDyeData()).build(), new LeaveQueueItem()),
    SETTINGS(new ItemBuilder(Material.PAPER).name("&b&lSettings").build(), new SettingsHotbarItem()),
    PARTY_CREATE(new ItemBuilder(Material.NAME_TAG).name("&b&lCreate Party").build(), new PartyCreateItem()),
    VIEW_LEADERBOARDS(new ItemBuilder(Material.SKULL_ITEM).name("&b&lView Leaderboards").build(), new ViewLeaderboardsItem());

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
