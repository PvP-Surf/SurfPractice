package surf.pvp.practice.party;

import lombok.Getter;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Party {

    private final UUID uuid;

    private final Player leader;
    private final List<Player> members = new ArrayList<>();

    private final int maxPartyCount = 10;

    /**
     * Party Constructor
     *
     * @param leader leader of the party
     */

    public Party(Player leader) {
        this.uuid = UUID.randomUUID();

        this.leader = leader;
        this.addMember(leader);

        members.remove(leader);
    }

    /**
     * Adds a member to the party
     *
     * @param player player to add
     */

    public final void addMember(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.IN_PARTY);
        profile.setParty(this);

        members.add(player);
    }

    /**
     * Removes a member from the party
     *
     * @param player player to remove
     */

    public final void removeMember(Player player) {
        final Profile profile = SurfPractice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        profile.setProfileState(ProfileState.LOBBY);
        profile.setParty(null);

        members.remove(player);
    }

    /**
     * Gets the name of the party
     *
     * @return {@link String}
     */

    public final String getName() {
        return leader.getName() + "'s Party";
    }

    /**
     * Disbands the party
     */

    public final void disband() {
        this.removeMember(leader);
        members.forEach(member -> this.removeMember(member));
    }

}
