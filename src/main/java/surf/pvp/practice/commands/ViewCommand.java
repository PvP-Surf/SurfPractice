package surf.pvp.practice.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.menu.view.ViewMenu;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;

import java.util.UUID;

public class ViewCommand {

    @Command(value = "view", description = "View Command", quoted = false)
    public final void view(@Sender Player player, @Name("uuid") UUID uuid) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(uuid);

        if (profile == null || !profile.hasMatchRemains()) {
            player.sendMessage(CC.translate("&cThis user did not have any recent fights!"));
            return;
        }

        new ViewMenu(player, profile.getPlayer().getDisplayName() + "'s Inventory", profile).updateMenu();
    }

}
