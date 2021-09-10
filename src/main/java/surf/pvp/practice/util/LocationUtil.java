package surf.pvp.practice.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getYaw() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location stringToLocation(String string) {
        String[] args  = string.split(",");
        return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }

}
