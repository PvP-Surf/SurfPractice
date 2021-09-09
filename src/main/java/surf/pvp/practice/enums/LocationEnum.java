package surf.pvp.practice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public enum LocationEnum {

    SPAWN(Bukkit.getServer().getWorld("world").getSpawnLocation());

    private final Location location;

}
