package surf.pvp.practice.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;

public class EssentialCommands {

    @Command(value = "build", async = true, description = "Build Command")
    @Permission(value = "practice.build")
    public final void build(@Sender Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setBuild(!profile.isBuild());
        player.sendMessage(CC.translate("&fYour build mode has been set to &b" + profile.isBuild() + "&f!"));
    }

}
