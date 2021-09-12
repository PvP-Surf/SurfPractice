package surf.pvp.practice.commands;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.annotation.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.util.CC;

import java.util.Collection;

@AllArgsConstructor
public class ArenaCommand {

    private final SurfPractice surfPractice;

    @Command(value = "arena create", description = "Creates an arena", async = true, extraUsageData = "<name>")
    @Permission(value = "practice.arena")
    public final void arenaCreate(@Sender Player player, @Name("name") String name) {

        if (surfPractice.getArenaHandler().getArena(name) != null) {
            player.sendMessage(CC.translate("&cAn arena with that name already exists!"));
            return;
        }

        surfPractice.getArenaHandler().createArena(name);
        player.sendMessage(CC.translate("&fCreated an arena with the name &b" + name + "&f!"));
    }

    @Command(value = "arena delete", description = "Deletes an arena", async = true, extraUsageData = "<arena>")
    @Permission(value = "practice.arena")
    public final void arenaDelete(@Sender Player player, @Name("arena") Arena arena) {
        surfPractice.getArenaHandler().handleRemoval(arena, true);
        player.sendMessage(CC.translate("&fDeleted the arena &b" + arena.getName() + "&f!"));
    }

    @Command(value = "arena setposition", description = "Sets the position of an arena", extraUsageData = "<arena> <position>", async = true)
    @Permission(value = "practice.arena")
    public final void arenaSetPotistion(@Sender Player player, @Name("arena") Arena arena, @Name("position") @Range(min = 1, max = 2) int position) {
        switch (position) {
            case 1:
                arena.setPositionOne(player.getLocation());
                player.sendMessage(CC.translate("&fSet the position &b1 &fto your &blocation!"));
                break;
            case 2:
                arena.setPositionTwo(player.getLocation());
                player.sendMessage(CC.translate("&fSet the position &b2 &fto your &blocation!"));
                break;
            default:
                break;
        }
    }

    @Command(value = "arena setcenter", description = "Sets the center of an arena", extraUsageData = "<arena>", async = true)
    @Permission(value = "practice.arena")
    public final void arenaSetCenter(@Sender Player player, @Name("arena") Arena arena) {
        arena.setCenterPosition(player.getLocation());
        player.sendMessage(CC.translate("&fSet the &bcenter &fposition of &b" + arena.getName() + "&fto your &blocation&f!"));
    }

    @Command(value = "arena addkit", description = "Adds a kit to the arena", extraUsageData = "<arena> <kit>", async = true)
    @Permission(value = "practice.arena")
    public final void arenaAddKit(@Sender Player player, @Name("arena") Arena arena, @Name("kit") Kit kit) {

        if (arena.getKits().contains(kit)) {
            player.sendMessage(CC.translate("&cThe arena already contains the kit &e" + kit.getName() + "&c!"));
            return;
        }

        arena.getKits().add(kit);
        player.sendMessage(CC.translate("&fAdded the kit &b" + kit.getName() + " &fto the arena &b" + arena.getName() + "&f!"));
    }

    @Command(value = "arena removekit", description = "Removes a kit from the arena", extraUsageData = "<arena> <kit>", async = true)
    @Permission(value = "practice.arena")
    public final void arenaRemoveKit(@Sender Player player, @Name("arena") Arena arena, @Name("kit") Kit kit) {

        if (!arena.getKits().contains(kit)) {
            player.sendMessage(CC.translate("&cThe arena &e" + arena.getName() + " &cdoes not contain the kit &e" + kit.getName() + "&c!"));
            return;
        }

        arena.getKits().remove(kit);
        player.sendMessage(CC.translate("&fRemoved the kit &b" + kit.getName() + " &ffrom the arena &b" + arena.getName() + "&f!"));
    }

    @Command(value = "arena kitlist", description = "Lists all kits that an arena has", extraUsageData = "<arena>", async = true)
    @Permission(value = "practice.arena")
    public final void arenaListKit(@Sender Player player, @Name("arena") Arena arena) {

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&b&l" + arena.getName() + "&f's Kits"));
        player.sendMessage(CC.CHAT_BAR);

        arena.getKits().forEach(kit -> {
            TextComponent textComponent = new TextComponent(CC.translate("&f* &b" + kit.getName() + " &f- " + WordUtils.capitalizeFully(kit.getKitType().name().replace("_", " "))));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(CC.translate("&fClick to load the &bkit&f!")).create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit load " + kit.getName()));

            player.spigot().sendMessage(textComponent);
        });
    }

    @Command(value = "arena tp", description = "Tps to an arena", extraUsageData = "<arena>")
    @Permission(value = "practice.arena")
    public final void arenaTpCommand(@Sender Player player, @Name("arena") Arena arena) {
        final Location location = arena.getAvailableLocation();

        if (location == null) {
            player.sendMessage(CC.translate("&cThere are no available locations to teleport to on &e" + arena.getName() + "&c!"));
            return;
        }

        player.teleport(location);
        player.sendMessage(CC.translate("&fTeleported you to &b" + arena.getName() + "&f!"));
    }

    @Command(value = "arena list", description = "Lists all arenas", async = true)
    @Permission(value = "practice.arena")
    public final void arenaListCommand(@Sender Player player) {
        final Collection<Arena> arenas = surfPractice.getArenaHandler().getArenas();

        if (arenas.isEmpty()) {
            player.sendMessage(CC.translate("&cThere are no arenas"));
            return;
        }

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&b&lArena List"));
        player.sendMessage(CC.CHAT_BAR);

        arenas.forEach(arena -> {
            String color = arena.isSetup() ? "&a" : "&c";

            TextComponent textComponent = new TextComponent(CC.translate("&f*" + color + arena.getName()));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(CC.translate("&fClick here to teleport to &b" + arena.getName() + "&f!")).create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena tp " + arena.getName()));

            player.spigot().sendMessage(textComponent);
        });
    }

}
