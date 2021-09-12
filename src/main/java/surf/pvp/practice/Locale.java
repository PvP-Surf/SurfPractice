package surf.pvp.practice;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public enum Locale {

    SERVER_NAME("server-name"),
    JOIN_MESSAGE("join-message");

    private final FileConfiguration fileConfiguration;
    private final String path;

    /**
     * Locale used to get messages
     *
     * @param fileConfiguration configuration file to get from
     * @param path              path to get from
     */

    Locale(FileConfiguration fileConfiguration, String path) {
        this.fileConfiguration = fileConfiguration;
        this.path = path;
    }

    /**
     * Locale used to get messages
     *
     * @param path path to get from
     */

    Locale(String path) {
        this.fileConfiguration = SurfPractice.getInstance().getConfig();
        this.path = path;
    }

    /**
     * Gets the string of that path
     *
     * @return {@link String}
     */

    public final String getString() {
        return fileConfiguration.getString(path);
    }

    /**
     * Gets the integer of that path
     *
     * @return {@link Integer}
     */

    public final int getInteger() {
        return fileConfiguration.getInt(path);
    }

    /**
     * Gets the boolean of that path
     *
     * @return {@link Boolean}
     */

    public final boolean getBoolean() {
        return fileConfiguration.getBoolean(path);
    }

    /**
     * Gets the string list
     *
     * @return {@link List<String>}
     */

    public final List<String> getStringList() {
        return fileConfiguration.getStringList(path);
    }

    /**
     * Sends a message to a sender
     *
     * @param sender sender to send the message to
     */

    public final void sendMessage(CommandSender sender) {
        sender.sendMessage(getString());
    }

    /**
     * Sends a message to a player
     *
     * @param player player to send the message to
     */

    public final void sendMessage(Player player) {
        player.sendMessage(getString());
    }
}
