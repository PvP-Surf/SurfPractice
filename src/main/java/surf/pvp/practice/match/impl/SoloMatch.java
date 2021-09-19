package surf.pvp.practice.match.impl;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.essentials.Essentials;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.listener.events.impl.match.solo.MatchEndEvent;
import surf.pvp.practice.listener.events.impl.match.solo.MatchStartEvent;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.MatchType;
import surf.pvp.practice.match.task.MatchWaterCheckTask;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.util.PlayerUtil;

import java.util.UUID;

public class SoloMatch extends Match {

    private MatchWaterCheckTask waterCheckTask;

    public SoloMatch(Arena arena, Kit kit, Player... players) {
        super(MatchType.ONE, arena, kit, players);
    }

    @Override
    public void start() {
        final Player one = getPlayerOne();
        final Player two = getPlayerTwo();

        one.teleport(arena.getPositionOne());
        two.teleport(arena.getPositionTwo());

        switch (kit.getKitRule()) {
            case SUMO: {
                PlayerUtil.denyMovement(one);
                PlayerUtil.denyMovement(two);

                waterCheckTask = new MatchWaterCheckTask(this);
                waterCheckTask.runTaskTimer(SurfPractice.getInstance(), 20L, 20L);
            }
            case COMBO: {
                one.addPotionEffect(PotionEffectType.SPEED.createEffect(500000000, 1));
                two.addPotionEffect(PotionEffectType.SPEED.createEffect(500000000, 1));

                one.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(500000000, 0));
                two.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(500000000, 0));

                one.setNoDamageTicks(3);
                two.setNoDamageTicks(3);

                //TODO: Add Knockback implementation to change knockback here
            }
        }

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setCurrentQueue(null);
            profile.setMatch(this);
            profile.setProfileState(ProfileState.IN_MATCH);

            profile.giveBooks(player, profile.getLoudOut(kit));
        }

        this.callEvent(new MatchStartEvent(one, two, this));
        this.setMatchStatus(MatchStatus.START_COUNTDOWN);
    }

    @Override
    public void end(UUID uuid, boolean value) {
        Player winner;
        Player loser;

        if (value) {
            winner = getOpposingPlayer(uuid);
            loser = winner.getUniqueId().equals(getPlayerOne().getUniqueId()) ? getPlayerTwo() : getPlayerTwo();
        } else {
            winner = getPlayerOne().getUniqueId().equals(uuid) ? getPlayerOne() : getPlayerTwo();
            loser = winner.getUniqueId().equals(getPlayerOne().getUniqueId()) ? getPlayerOne() : getPlayerTwo();
        }

        if (waterCheckTask != null) waterCheckTask.cancel();

        PlayerUtil.reset(getPlayerTwo());
        PlayerUtil.reset(getPlayerTwo());

        this.setMatchStatus(MatchStatus.ENDED);
        this.callEvent(new MatchEndEvent(winner, loser, this));

        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(null);

            profile.setProfileState(ProfileState.LOBBY);
            player.teleport(Essentials.getInstance().getSpawnLocation());
        }
    }

    public final Player getOpposingPlayer(UUID uuid) {
        return getPlayerOne().getUniqueId().equals(uuid) ? getPlayerTwo() : getPlayerOne();
    }

    public final Player getPlayerOne() {
        return players[0];
    }

    public final Player getPlayerTwo() {
        return players[1];
    }

}
