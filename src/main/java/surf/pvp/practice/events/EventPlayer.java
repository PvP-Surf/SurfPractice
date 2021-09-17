package surf.pvp.practice.events;

import lombok.Data;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;

@Data
public class EventPlayer {

    private final Player player;
    private final Profile profile;

    private int roundWins;
    private int roundLoses;

    public EventPlayer(Player player) {
        this.player = player;
        this.profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
    }

}
