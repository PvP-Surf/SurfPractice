package surf.pvp.practice.commands;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.kit.KitRule;
import surf.pvp.practice.kit.KitType;
import surf.pvp.practice.util.CC;

@AllArgsConstructor
public class KitCommand {

    private final SurfPractice surfPractice;

    @Command(value = "kit create", description = "Creates a kit", async = true, extraUsageData = "<name>")
    @Permission(value = "practice.kit")
    public final void kitCreateCommand(@Sender Player player, @Name("name") String name) {

        if (surfPractice.getKitHandler().getKit(name) != null) {
            player.sendMessage(CC.translate("&cA kit with the name &e" + name + " &calready exists!"));
            return;
        }

        Kit kit = surfPractice.getKitHandler().createKit(name);

        kit.setArmorContents(player.getInventory().getArmorContents());
        kit.setInventoryContents(player.getInventory().getContents());

        player.sendMessage(CC.translate("&fCreated a kit with the name &b" + name));
    }

    @Command(value = "kit delete", description = "Deletes a kit", async = true, extraUsageData = "<kit>")
    @Permission(value = "practice.kit")
    public final void kitDeleteCommand(@Sender Player player, @Name("kit") Kit kit) {
        surfPractice.getKitHandler().handleRemoval(kit, true);
        player.sendMessage(CC.translate("&fDeleted the kit &b" + kit.getName() + "&f!"));
    }

    @Command(value = "kit seticon", description = "Sets the icon of a kit", async = true, extraUsageData = "<kit> <icon>")
    @Permission(value = "practice.kit")
    public final void kitSetIconCommand(@Sender Player player, @Name("kit") Kit kit, @Name("icon") Material material) {
        kit.setIcon(material);
        player.sendMessage(CC.translate("&fSet the icon of &b" + kit.getName() + " &fto &b" + material.toString()));
    }

    @Command(value = "kit setcolor", description = "Sets the color of a kit", async = true, extraUsageData = "<kit> <color>")
    @Permission(value = "practice.kit")
    public final void kitSetColorCommand(@Sender Player player, @Name("kit") Kit kit, @Name("color") String color) {
        kit.setColor(color);
        player.sendMessage(CC.translate("&fSet the color of &b" + kit + " &fto " + color + " color!"));
    }

    @Command(value = "kit setbuild", description = "Sets if a kit is a build kit or not", async = true, extraUsageData = "<kit>")
    @Permission(value = "practice.kit")
    public final void kitSetBuild(@Sender Player player, @Name("kit") Kit kit) {
        kit.setKitType(KitType.oppositeOf(kit.getKitType()));
        player.sendMessage(CC.translate("&fSet &b" + kit.getName() + "&f's to &b" + WordUtils.capitalizeFully(kit.getKitType().name().replace("_", " "))));
    }

    @Command(value = "kit update", description = "Updates a kit to your inventory", async = true, extraUsageData = "<kit>")
    @Permission("practice.kit")
    public final void kitUpdateCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.setArmorContents(player.getInventory().getArmorContents());
        kit.setInventoryContents(player.getInventory().getContents());

        player.sendMessage(CC.translate("&fUpdated &b" + kit.getName() + "&f's contents to &byour &finventory contents!"));
    }

    @Command(value = "kit setrule", description = "Sets the kit rule", extraUsageData = "<kit> <rule>", async = true)
    @Permission(value = "practice.kit")
    public final void kitSetRuleCommand(@Sender Player player, @Name("kit") Kit kit, @Name("rule") KitRule kitRule) {
        kit.setKitRule(kitRule);
        player.sendMessage(CC.translate("&fSet the &bkit rule &fof &b" + kit.getName() + " &fto &b" + kitRule.name().toLowerCase() + "&f!"));
    }

    @Command(value = "kit load", description = "Loads a kit to your inventory", extraUsageData = "<kit>")
    @Permission(value = "practice.kit")
    public final void kitLoadCommand(@Sender Player player, @Name("kit") Kit kit) {
        player.getInventory().setArmorContents(kit.getArmorContents());
        player.getInventory().setContents(kit.getInventoryContents());

        player.sendMessage(CC.translate("&fLoaded the kit &b" + kit.getName() + "&f!"));
    }

    @Command(value = "kit event", description = "Allows to do events with this kit", async = true, extraUsageData = "<kit>")
    @Permission(value = "practice.kit")
    public final void kitEventCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.setAllowEvent(!kit.isAllowEvent());
        player.sendMessage(CC.translate("&fThe kit &b" + kit.getName() + " &fhas now the event status of &b" + kit.isAllowEvent() + "&f!"));
    }

    @Command(value = "kit list", description = "Lists all kits available", async = true)
    @Permission(value = "practice.kit")
    public final void kitListCommand(@Sender Player player) {
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&b&lKit List"));
        player.sendMessage(CC.CHAT_BAR);

        surfPractice.getKitHandler().getKits().forEach(kit -> {
            TextComponent textComponent = new TextComponent(CC.translate("&f* &b" + kit.getName() + " &f- " + WordUtils.capitalizeFully(kit.getKitType().name().replace("_", " "))));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(CC.translate("&fClick to load the &bkit&f!")).create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit load " + kit.getName()));

            player.spigot().sendMessage(textComponent);
        });
    }

}
