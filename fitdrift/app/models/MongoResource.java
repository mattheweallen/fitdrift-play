package models;

import java.util.Properties;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.typesafe.config.ConfigFactory;

import javax.annotation.Nullable;
import java.net.UnknownHostException;

/**
 * Created by meallen on 6/27/2014.
 */
public enum MongoResource {

    INSTANCE;
    private MongoClient mongoClient;

    private MongoResource() {
        try {
            if (mongoClient == null)
                mongoClient = new MongoClient( "localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public DB getDB(String database) {
        DB db = mongoClient.getDB(database);
        return db;
    }
}
