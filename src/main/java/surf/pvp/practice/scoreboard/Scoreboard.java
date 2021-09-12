package surf.pvp.practice.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.match.Match;
import surf.pvp.practice.match.impl.SoloMatch;
import surf.pvp.practice.party.Party;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;
import surf.pvp.practice.queue.Queue;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.PlayerUtil;
import surf.pvp.practice.util.assemble.AssembleAdapter;
import surf.pvp.practice.util.cooldown.DurationFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class Scoreboard implements AssembleAdapter {

    private final SurfPractice surfPractice;

    private final String title;
    private final List<String> linesLobby, linesParty, linesMatch, linesEnderpearlCooldown, queueLines;

    public Scoreboard(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        this.title = surfPractice.getScoreBoardFile().getString("title");

        this.linesLobby = CC.translate(surfPractice.getScoreBoardFile().getStringList("lines.lobby"));
        this.linesParty = CC.translate(surfPractice.getScoreBoardFile().getStringList("lines.party"));
        this.linesMatch = CC.translate(surfPractice.getScoreBoardFile().getStringList("lines.match.normal"));
        this.queueLines = CC.translate(surfPractice.getScoreBoardFile().getStringList("lines.queue"));

        this.linesEnderpearlCooldown = CC.translate(surfPractice.getScoreBoardFile().getStringList("lines.match.enderpearl"));
    }

    /**
     * Get's the scoreboard title.
     *
     * @param player who's title is being displayed.
     * @return title.
     */

    @Override
    public String getTitle(Player player) {
        return CC.translate(title);
    }

    /**
     * Get's the scoreboard lines.
     *
     * @param player who's lines are being displayed.
     * @return lines.
     */

    @Override
    public List<String> getLines(Player player) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());
        final ProfileState profileState = profile.getProfileState();

        if (profileState.equals(ProfileState.LOBBY) || profileState.equals(ProfileState.IN_TOURNAMENT_LOBBY) || profileState.equals(ProfileState.SPECTATING)) {
            return getLinesLobby(player, profile);
        }

        if (profileState.equals(ProfileState.QUEUE)) {
            return getQueueLines(player, profile);
        }

        if (profile.getMatch() != null) {
            return getLinesMatch(profile, player, profile.getMatch(), profile.getEnderPearlCooldown().hasExpired());
        }

        return null;
    }

    /**
     * Gets replaced lines of the queue
     *
     * @param player  player to get the lines of
     * @param profile profile of the player
     * @return {@link List<String>}
     */


    public List<String> getQueueLines(Player player, Profile profile) {
        Queue<?> queue = profile.getCurrentQueue();

        return PlaceholderAPI.setPlaceholders(player, queueLines.stream().map(string ->
                string.replace("<size>", String.valueOf(queue.getQueue().size()))
                        .replace("<kit>", queue.getKit().getName())
                        .replace("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("<playing>", String.valueOf(surfPractice.getProfileHandler().getMatches()))
        ).collect(Collectors.toList()));
    }

    /**
     * Gets replaced lines of the lobby
     *
     * @param player  player to get the lines of
     * @param profile profile of the player
     * @return {@link List<String>}
     */

    public final List<String> getLinesLobby(Player player, Profile profile) {
        return PlaceholderAPI.setPlaceholders(player, linesLobby.stream().map(string ->
                string.replace("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("<playing>", String.valueOf(surfPractice.getProfileHandler().getMatches()))
        ).collect(Collectors.toList()));
    }

    /**
     * Gets replaced lines of the party
     *
     * @param player  player to get the lines of
     * @param profile profile of the player
     * @return {@link List<String>}
     */

    public final List<String> getLinesParty(Player player, Profile profile) {
        final Party party = profile.getParty();

        return PlaceholderAPI.setPlaceholders(player, linesParty.stream().map(string ->
                string.replace("<leader>", party.getLeader().getName())
                        .replace("<size>", String.valueOf((party.getMembers().size() + 1)))
                        .replace("<max>", String.valueOf(party.getMaxPartyCount()))
                        .replace("<name>", party.getName())
        ).collect(Collectors.toList()));
    }

    /**
     * Gets replaced lines of the match
     *
     * @param player  player to get the lines of
     * @param profile profile of the player
     * @return {@link List<String>}
     */

    public final List<String> getLinesMatch(Profile profile, Player player, Match match, boolean enderPearl) {
        Player opponenet = ((SoloMatch) match).getOpposingPlayer(player.getUniqueId());

        if (enderPearl)
            return PlaceholderAPI.setPlaceholders(player, linesEnderpearlCooldown.stream().map(string ->
                    string.replace("<cooldown>", profile.getEnderPearlCooldown().getTimeLeft())
                            .replace("<opponent>", opponenet.getName())
                            .replace("<name>", player.getName())
                            .replace("<time>", DurationFormatter.getRemaining(match.getTimeInside() * 1000L, true))
                            .replace("<opponenet_ping>", String.valueOf(PlayerUtil.getPing(opponenet)))
                            .replace("<ping>", String.valueOf(PlayerUtil.getPing(player)))).collect(Collectors.toList()));

        return PlaceholderAPI.setPlaceholders(player, linesMatch.stream().map(string ->
                string.replace("<cooldown>", profile.getEnderPearlCooldown().getTimeLeft())
                        .replace("<opponent>", opponenet.getName())
                        .replace("<name>", player.getName())
                        .replace("<time>", DurationFormatter.getRemaining(match.getTimeInside() * 1000L, true))
                        .replace("<opponenet_ping>", String.valueOf(PlayerUtil.getPing(opponenet)))
                        .replace("<ping>", String.valueOf(PlayerUtil.getPing(player)))).collect(Collectors.toList()));
    }

}
