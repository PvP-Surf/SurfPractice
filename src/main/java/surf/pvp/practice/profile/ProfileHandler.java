package surf.pvp.practice.profile;

import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.impl.ProfileMongoStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileHandler {

    private final Map<UUID, Profile> profileMap = new HashMap<>();
    private final SurfPractice surfPractice;

    private final ProfileStorage profileStorage;

    /**
     * Profile Manager
     *
     * @param surfPractice
     */

    public ProfileHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        profileStorage = new ProfileMongoStorage(surfPractice);
        surfPractice.getServer().getPluginManager().registerEvents(new ProfileListener(this), surfPractice);
    }

    /**
     * Creates and loads
     * a profile with the
     * specified uuid
     *
     * @param uuid uuid to create/load the profile of
     */

    public final void createProfile(UUID uuid) {
        Profile profile = new Profile(uuid);

        profileStorage.load(profile);
        profileMap.put(uuid, profile);
    }

    /**
     * Handles the removal of a profile
     *
     * @param profile profile to handle removal off
     * @param async   if should be ran async or not
     */

    public final void handleRemoval(Profile profile, boolean async) {
        profileStorage.save(profile, async);
        profileMap.remove(profile.getUuid());
    }

    /**
     * Gets the profile of a uuid
     *
     * @param uuid uuid to get the profile of
     * @return {@link Profile}
     */

    public final Profile getProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    /**
     * Gets all profiles
     * registered
     *
     * @return {@link Collection<Profile>}
     */

    public final Collection<Profile> getProfiles() {
        return profileMap.values();
    }

    /**
     * Gets the number of matches
     *
     * @return {@link Integer}
     */

    public int getMatches() {
        return (int) getProfiles().stream().filter(profile -> profile.getProfileState().equals(ProfileState.IN_MATCH)).count();
    }

}
