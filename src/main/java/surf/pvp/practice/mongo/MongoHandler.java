package surf.pvp.practice.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;

@Getter
public class MongoHandler {

    private final SurfPractice surfPractice;

    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;
    private MongoCollection<Document> profiles, arenas, kits, clans;

    /**
     * Mongo manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public MongoHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;
    }

}
