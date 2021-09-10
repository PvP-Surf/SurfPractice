package surf.pvp.practice.profile.settings.impl;

import org.bson.Document;
import surf.pvp.practice.profile.settings.Settings;
import surf.pvp.practice.profile.settings.SettingsType;

import java.util.HashMap;
import java.util.Map;

public class ProfileSettings implements Settings {

    private final Map<SettingsType, Boolean> settingsTypeBooleanMap = new HashMap<>();

    /**
     * Sets all settings type values fast
     */

    public ProfileSettings() {
        for (SettingsType type : SettingsType.values()) {
            settingsTypeBooleanMap.put(type, true);
        }
    }

    /**
     * Loads settings from a document
     *
     * @param document document to load settings from
     */

    @Override
    public Settings load(Document document) {
        Document settings = (Document) document.get("settings");

        settings.keySet().forEach(string -> {
            settingsTypeBooleanMap.put(SettingsType.valueOf(string.toUpperCase()), settings.getBoolean(string));
        });

        return this;
    }

    /**
     * Sets the settings type to a boolean value
     *
     * @param type  type
     * @param value value
     */

    @Override
    public void set(SettingsType type, boolean value) {
        settingsTypeBooleanMap.put(type, value);
    }

    /**
     * Gets if the user has a settings type enabled
     *
     * @param settingsType type of settings to get
     * @return {@link Boolean}
     */

    @Override
    public boolean enabled(SettingsType settingsType) {
        return settingsTypeBooleanMap.get(settingsType);
    }

    public Document toBson() {
        return this.toBson(settingsTypeBooleanMap);
    }

}
