package surf.pvp.practice.arena;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import surf.pvp.practice.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Arena {

    private final String name;

    private final List<Integer> ratings = new ArrayList<>();

    private boolean busy;
    private Location positionOne, positionTwo, centerPosition;

    /**
     * Constructs an arena
     *
     * @param name name of arena
     */

    public Arena(String name) {
        this.name = name;
    }

    /**
     * Gets the average rating of an arena
     *
     * @return {@link Double}
     */

    public final double getAverageRating() {
        int i = ratings.stream().mapToInt(integer -> integer).sum();
        return (double) (i / ratings.size());
    }

    /**
     * Checks if the arena is setup or
     * not
     *
     * @return {@link Boolean}
     */

    public boolean isSetup() {
        return positionOne != null && positionTwo != null && centerPosition != null;
    }

    /**
     * Takes all data and inserts it
     * into a bson/mongo document
     *
     * @return {@link Document}
     */

    public Document toBson() {
        return new Document("_id", name)
                .append("posOne", LocationUtil.locationToString(positionOne))
                .append("posTwo", LocationUtil.locationToString(positionTwo))
                .append("center", LocationUtil.locationToString(centerPosition));
    }

}
