package surf.pvp.practice.profile.settings;

import org.bson.Document;

import java.util.Map;

public interface Settings {

    /**
     * Loads settings from a document
     *
     * @param document document to load settings from
     */

    Settings load(Document document);

    /**
     * Sets the settings type to a boolean value
     *
     * @param type  type
     * @param value value
     */

    void set(SettingsType type, boolean value);

    /**
     * Turns the settings values into bson
     *
     * @return {@link Document}
     */

    default Document toBson(Map<SettingsType, Boolean> map) {
        Document document = new Document();
        map.entrySet().forEach(settingsTypeBooleanEntry -> document.append(
                settingsTypeBooleanEntry.getKey().name().toUpperCase(), settingsTypeBooleanEntry.getValue()));
        return document;
    }

    /**
     * Gets if the user has a settings type enabled
     *
     * @param settingsType type of settings to get
     * @return {@link Boolean}
     */

    boolean enabled(SettingsType settingsType);

}
