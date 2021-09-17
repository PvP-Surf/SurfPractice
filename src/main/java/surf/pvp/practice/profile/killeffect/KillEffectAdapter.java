package surf.pvp.practice.profile.killeffect;

import org.bukkit.entity.Player;

public interface KillEffectAdapter {

    /**
     * Displays the effect to the player
     *
     * @param player player to display the effect to
     * @param killed player that was killed
     */

    void display(Player player, Player killed);

}
