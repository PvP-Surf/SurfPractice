package surf.pvp.practice.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.menu.queue.SoloQueueMenu;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.queue.QueueType;
import surf.pvp.practice.util.CC;

public class QueueCommand {

    @Command(value = "unranked", description = "Unranked Command", async = false)
    public final void unranked(@Sender Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        final Queue<?> queue = profile.getCurrentQueue();

        if (queue != null) {
            player.sendMessage(CC.translate("&cYou are currently inside of a queue!"));
            return;
        }

        new SoloQueueMenu(player, QueueType.SOLO, false).updateMenu();
    }

    @Command(value = "ranked", description = "Ranked Command")
    public final void rankedCommand(@Sender Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        final Queue<?> queue = profile.getCurrentQueue();

        if (queue != null) {
            player.sendMessage(CC.translate("&cYou are currently inside of a queue!"));
            return;
        }

        new SoloQueueMenu(player, QueueType.SOLO, true).updateMenu();
    }

    @Command(value = "youtube", description = "Youtube Command")
    @Permission(value = "practice.queue.youtube")
    public final void youtubeComamnd(@Sender Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        final Queue<?> queue = profile.getCurrentQueue();

        if (queue != null) {
            player.sendMessage(CC.translate("&cYou are currently inside of a queue!"));
            return;
        }

        new SoloQueueMenu(player, QueueType.YOUTUBE, false).updateMenu();
    }

}
