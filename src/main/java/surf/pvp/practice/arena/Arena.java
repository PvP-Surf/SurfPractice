package surf.pvp.practice.arena;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Arena {

    private final String name;

    private final List<Integer> ratings;
    private final List<Kit> kits;

    private boolean busy;
    private Location positionOne, positionTwo, centerPosition;

    /**
     * Constructs an arena
     *
     * @param name name of arena
     */

    public Arena(String name) {
        this.name = name;

        this.ratings = new ArrayList<>();
        this.kits = new ArrayList<>();
    }

    /**
     * Loads an arena from a mongo document
     *
     * @param document document to load the arena from
     * @param surfPractice instance of {@link SurfPractice}
     */

    public Arena(Document document, SurfPractice surfPractice) {
        this.name = document.getString("_id");

        this.ratings = document.getList("ratings", Integer.class);
        this.kits = document.getList("kits", String.class).stream().map(string -> surfPractice.getKitHandler().getKit(string)).collect(Collectors.toList());

        this.positionOne = LocationUtil.stringToLocation(document.getString("posOne"));
        this.positionTwo = LocationUtil.stringToLocation(document.getString("posTwo"));
        this.centerPosition = LocationUtil.stringToLocation(document.getString("center"));
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
                .append("ratings", ratings)
                .append("posOne", LocationUtil.locationToString(positionOne))
                .append("posTwo", LocationUtil.locationToString(positionTwo))
                .append("center", LocationUtil.locationToString(centerPosition))
                .append("kits", kits.stream().map(Kit::getName).collect(Collectors.toList()));
    }

}
