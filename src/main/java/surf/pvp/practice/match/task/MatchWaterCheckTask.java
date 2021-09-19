package surf.pvp.practice.match.task;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.KitRule;
import surf.pvp.practice.kit.KitType;
import surf.pvp.practice.listener.events.impl.global.PracticeDeathEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.impl.SoloMatch;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/18/2021
 * Project: SurfPractice
 */

@RequiredArgsConstructor
public class MatchWaterCheckTask extends BukkitRunnable {

    private final Match match;
    private final List<UUID> caught = new ArrayList<>();

    @Override
    public void run() {
        if (match == null || match.getMatchStatus().equals(MatchStatus.ENDED) || (!(match instanceof SoloMatch))) {
            caught.clear();
            this.cancel();
            return;
        }

        for ( Player player : match.getPlayers()) {
            if (player == null) continue;
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
            if (!profile.getProfileState().equals(ProfileState.IN_MATCH)) continue;
            if (this.caught.contains(player.getUniqueId())) continue;

            if (match.getMatchStatus().equals(MatchStatus.ENDED)) {
                caught.clear();
                this.cancel();
                return;
            }

            Block body = player.getLocation().getBlock();
            Block head = body.getRelative(BlockFace.UP);

            SoloMatch soloMatch = (SoloMatch) match;

            if (body.getType() == Material.WATER || body.getType() == Material.STATIONARY_WATER || head.getType() == Material.WATER || head.getType() == Material.STATIONARY_WATER) {
                if (match.getKit().getKitRule().equals(KitRule.SUMO)) {
                    this.caught.add(player.getUniqueId());

                    match.callEvent(new PracticeDeathEvent(player, soloMatch.getOpposingPlayer(player.getUniqueId()), player.getMaxHealth()));
                }
            }
        }
    }
}