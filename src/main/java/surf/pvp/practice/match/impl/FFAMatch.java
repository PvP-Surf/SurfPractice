package surf.pvp.practice.match.impl;

import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.essentials.Essentials;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.MatchStatus;
import surf.pvp.practice.match.MatchType;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FFAMatch extends Match {

    public FFAMatch(Arena arena, Kit kit, Player... players) {
        super(MatchType.FFA, arena, kit, players);
    }

    @Override
    public void start() {
        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(this);
            profile.setProfileState(ProfileState.IN_MATCH);

            profile.giveBooks(player, profile.getLoudOut(kit));
            player.teleport(arena.getCenterPosition());
        }

        this.setMatchStatus(MatchStatus.START_COUNTDOWN);
    }

    @Override
    public void end(UUID uuid, boolean value) {
        for (Player player : players) {
            Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setMatch(null);
            profile.setProfileState(ProfileState.LOBBY);

            player.teleport(Essentials.getInstance().getSpawnLocation());
        }
    }

    public final void removePlayer(Player player) {
        Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setMatch(null);
        profile.setProfileState(ProfileState.LOBBY);

        player.teleport(Essentials.getInstance().getSpawnLocation());

        final List<Player> playerList = new ArrayList<>(Arrays.asList(players));
        playerList.remove(player);

        this.players = playerList.toArray(new Player[0]);
    }

}
