package surf.pvp.practice.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Method;

public class Properties {

    private static final Class<?> MinecraftServerClass;
    private static final Class<?> PropertyManagerClass;
    private static final Method getServerMethod;
    private static final Method getPropertyManagerMethod;
    private static final Method setPropertyMethod;
    private static final Method savePropertiesFileMethod;

    static {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            MinecraftServerClass = Class.forName("net.minecraft.server." + version + ".MinecraftServer");
            PropertyManagerClass = Class.forName("net.minecraft.server." + version + ".PropertyManager");

            getServerMethod = MinecraftServerClass.getDeclaredMethod("getServer");
            getPropertyManagerMethod = MinecraftServerClass.getDeclaredMethod("getPropertyManager");
            setPropertyMethod = PropertyManagerClass.getDeclaredMethod("setProperty", String.class, Object.class);
            savePropertiesFileMethod = PropertyManagerClass.getDeclaredMethod("savePropertiesFile");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePropertiesFile() throws Exception {
        savePropertiesFileMethod.invoke(getPropertyManager());
    }

    public static void setServerProperty(ServerProperty property, Object value) throws Exception {
        setPropertyMethod.invoke(getPropertyManager(), property.getPropertyName(), value);
    }

    public static Object getPropertyManager() throws Exception {
        return getPropertyManagerMethod.invoke(getServerMethod.invoke(null));
    }

    public enum ServerProperty {

        SPAWN_PROTECTION("spawn-protection"),
        SERVER_NAME("server-name"),
        FORCE_GAMEMODE("force-gamemode"),
        NETHER("allow-nether"),
        DEFAULT_GAMEMODE("gamemode"),
        QUERY("enable-query"),
        PLAYER_IDLE_TIMEOUT("player-idle-timeout"),
        DIFFICULTY("difficulty"),
        SPAWN_MONSTERS("spawn-monsters"),
        OP_PERMISSION_LEVEL("op-permission-level"),
        RESOURCE_PACK_HASH("resource-pack-hash"),
        RESOURCE_PACK("resource-pack"),
        ANNOUNCE_PLAYER_ACHIEVEMENTS("announce-player-achievements"),
        PVP("pvp"),
        SNOOPER("snooper-enabled"),
        LEVEL_NAME("level-name"),
        LEVEL_TYPE("level-type"),
        LEVEL_SEED("level-seed"),
        HARDCORE("hardcore"),
        COMMAND_BLOCKS("enable-command-blocks"),
        MAX_PLAYERS("max-players"),
        PACKET_COMPRESSION_LIMIT("network-compression-threshold"),
        MAX_WORLD_SIZE("max-world-size"),
        IP("server-ip"),
        PORT("server-port"),
        DEBUG_MODE("debug"),
        SPAWN_NPCS("spawn-npcs"),
        SPAWN_ANIMALS("spawn-animals"),
        FLIGHT("allow-flight"),
        VIEW_DISTANCE("view-distance"),
        WHITE_LIST("white-list"),
        GENERATE_STRUCTURES("generate-structures"),
        MAX_BUILD_HEIGHT("max-build-height"),
        MOTD("motd"),
        REMOTE_CONTROL("enable-rcon");

        private final String propertyName;

        ServerProperty(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyName() {
            return propertyName;
        }

    }
}