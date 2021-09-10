package surf.pvp.practice.profile;

public interface ProfileStorage {

    /**
     * Loads the profile
     *
     * @param profile profile to load
     */

    void load(Profile profile);

    /**
     * Saves the profile
     *
     * @param profile profile to save
     * @param async   if should be done async or not
     */

    void save(Profile profile, boolean async);

}
