package surf.pvp.practice.profile;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class ProfileListener implements Listener {

    private final ProfileHandler profileHandler;

    /**
     * Profile Listener, handles all
     * profile related events
     */

    @EventHandler
    public final void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event) {
        profileHandler.createProfile(event.getUniqueId());
    }

    @EventHandler
    public final void onPlayerQuitEvent(PlayerQuitEvent event) {
        profileHandler.handleRemoval(profileHandler.getProfile(event.getPlayer().getUniqueId()), true);
    }

}
