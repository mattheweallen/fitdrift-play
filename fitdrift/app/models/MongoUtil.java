package models;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import java.net.UnknownHostException;

/**
 * Created by meallen on 6/27/2014.
 */
public class MongoUtil {

    private static final String server = "localhost";
    private static final String database = "fitdrift";

    public static DB MongoDB() {
        DB db = null;
        try {
            //TODO review this make sure not opening too many connections
            MongoClient mongoClient = new MongoClient(server);
            db = mongoClient.getDB(database);
        } catch (UnknownHostException e) {

        }
        return db;
    }
}
