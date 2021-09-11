package surf.pvp.practice.profile.killeffect.impl;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import surf.pvp.practice.profile.killeffect.KillEffectAdapter;

public class LightningKillEffectAdapter implements KillEffectAdapter {

    /**
     * Displays the effect to the player
     *
     * @param player player to display the effect to
     * @param killed player that was killed
     */

    @Override
    public void display(Player player, Player killed) {
        final Location location = killed.getLocation();
        final World world = location.getWorld();

        world.strikeLightning(location);
    }

}
