package surf.pvp.practice.profile.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.AllArgsConstructor;
import org.bson.Document;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.ProfileStorage;
import surf.pvp.practice.profile.loadout.CustomLoadOut;

@AllArgsConstructor
public class ProfileMongoStorage implements ProfileStorage {

    private final SurfPractice surfPractice;

    @Override
    public void load(Profile profile) {
        surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> {
            Document document = surfPractice.getMongoHandler().getProfiles().find(Filters.eq("_id", profile.getUuid().toString())).first();

            if (document == null) {
                this.save(profile, false);
                return;
            }

            profile.setCoins(document.getInteger("coins"));
            profile.setLoss(document.getInteger("loss"));
            profile.setWin(document.getInteger("win"));
            profile.setXp(document.getInteger("xp"));

            document.getList("elo", String.class).forEach(string -> {
                String[] args = string.split(":");
                profile.getEloMap().put(surfPractice.getKitHandler().getKit(args[0]), Integer.parseInt(args[1]));
            });

            document.getList("loadout", String.class).forEach(string -> profile.getCustomLoadOutMap().add(CustomLoadOut.get(string)));
        });
    }

    public void save(Profile profile, boolean async) {

        if (async) {
            surfPractice.getServer().getScheduler().runTaskAsynchronously(surfPractice, () -> save(profile, false));
            return;
        }

        Document document = surfPractice.getMongoHandler().getProfiles().find(Filters.eq("_id", profile.getUuid().toString())).first();

        if (document == null) {
            surfPractice.getMongoHandler().getProfiles().insertOne(profile.toBson());
            return;
        }

        surfPractice.getMongoHandler().getProfiles().replaceOne(document, profile.toBson(), new ReplaceOptions().upsert(true));
    }

}
