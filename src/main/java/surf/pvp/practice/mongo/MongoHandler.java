package surf.pvp.practice.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;
import surf.pvp.practice.SurfPractice;

@Getter
public class MongoHandler {

    private final SurfPractice surfPractice;

    private MongoDatabase database;
    private MongoClient client;
    private MongoCollection<Document> profiles, arenas, kits, clans;

    /**
     * Mongo manager
     *
     * @param surfPractice instance of {@link SurfPractice}
     */

    public MongoHandler(SurfPractice surfPractice) {
        this.surfPractice = surfPractice;

        this.init();
    }

    public void init() {
        final FileConfiguration config = surfPractice.getConfig();

        if (config.getBoolean("mongo.uri-mode")) {
            this.client = MongoClients.create(config.getString("mongo.uri.connection-string"));
            this.database = client.getDatabase(config.getString("mongo.uri.database"));

            this.loadCollections();
            return;
        }

        boolean auth = config.getBoolean("mongo.normal.auth.enabled");
        String host = config.getString("mongo.normal.host");
        int port = config.getInt("mongo.normal.port");

        String uri = "mongodb://" + host + ":" + port;

        if (auth) {
            String username = config.getString("mongo.normal.auth.username");
            String password = config.getString("mongo.normal.auth.password");
            uri = "mongodb://" + username + ":" + password + "@" + host + ":" + port;
        }

        this.client = MongoClients.create(uri);
        this.database = client.getDatabase(config.getString("mongo.uri.database"));

        this.loadCollections();
    }

    public void loadCollections() {
        profiles = this.database.getCollection("profiles");
        clans = this.database.getCollection("clans");
        arenas = this.database.getCollection("arenas");
        kits = this.database.getCollection("kits");
    }

}
