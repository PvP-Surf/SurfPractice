package surf.pvp.practice.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + (int) location.getYaw() + "," + (int) + location.getPitch();
    }

    public static Location stringToLocation(String string) {
        String[] args  = string.split(",");
        return new Location(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
    }

}
