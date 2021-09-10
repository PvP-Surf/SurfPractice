package surf.pvp.practice.clan;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Clan {

    private final UUID uuid;
    private final String serverName;

    private final UUID leader;
    private final List<UUID> members;

    private final SurfPractice surfPractice = SurfPractice.getInstance();

    /**
     * Constructs a brand new
     * clan
     *
     * @param leader leader of the clan
     */

    public Clan(UUID leader, String serverName) {
        this.uuid = UUID.randomUUID();
        this.serverName = serverName;

        this.leader = leader;
        this.members = new ArrayList<>();
    }

    /**
     * Loads a clan from a mongo document
     *
     * @param document document to load the clan from
     */

    public Clan(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.serverName = document.getString("server");

        this.leader = UUID.fromString(document.getString("leader"));
        this.members = document.getList("members", String.class).stream().map(UUID::fromString).collect(Collectors.toList());
    }

    /**
     * Adds a member to the clan
     *
     * @param profile member to add
     */

    public final void addMember(Profile profile) {
        profile.getClanList().add(this);
        members.add(profile.getUuid());
    }

    /**
     * Removes a member from the clan
     *
     * @param profile member to remove
     */

    public final void removeMember(Profile profile) {
        profile.getClanList().remove(this);
        members.remove(profile.getUuid());
    }

    /**
     * Disbands the clan
     */

    public final void disband(boolean async) {

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> disband(false));
            return;
        }

        members.forEach(member -> surfPractice.getProfileHandler().getProfile(member).getClanList().remove(this));
        surfPractice.getProfileHandler().getProfile(leader).getClanList().remove(this);

        Document document = surfPractice.getMongoHandler().getClans().find(Filters.eq("_id", uuid.toString())).first();

        if (document == null)
            return;

        surfPractice.getMongoHandler().getClans().deleteOne(document);
    }

    /**
     * Collects all clan data
     * and inserts it into
     * mongo storage
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", uuid.toString())
                .append("leader", leader.toString())
                .append("server", serverName)
                .append("members", members.stream().map(UUID::toString).collect(Collectors.toList()));
    }

}
