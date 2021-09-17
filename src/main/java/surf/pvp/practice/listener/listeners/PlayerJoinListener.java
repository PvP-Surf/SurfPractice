package surf.pvp.practice.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import surf.pvp.practice.Locale;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public final void onPlayerJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.updateHotbar();
        CC.translate(Locale.JOIN_MESSAGE.getStringList()).forEach(player::sendMessage);
    }

}
