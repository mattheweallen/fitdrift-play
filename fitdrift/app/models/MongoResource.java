package models;

import java.util.Properties;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import java.net.UnknownHostException;

/**
 * Created by meallen on 6/27/2014.
 */
public enum MongoResource {

    INSTANCE;
    private MongoClient mongoClient;
    private Properties properties;

    private MongoResource() {
        try {
            if (properties == null) {
                try {
                    properties = loadProperties();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mongoClient == null)
                mongoClient = getClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = MongoResource.class.getResourceAsStream("/mongodb.properties");
        if (inputStream == null) {
            throw new FileNotFoundException("not loaded!");
        } properties.load(inputStream);
        return properties;
    }

    @Nullable
    private MongoClient getClient() {
        try {
            return new MongoClient( properties.getProperty("host"), Integer.valueOf(properties.getProperty("port")));
        } catch (UnknownHostException uh) {
            uh.printStackTrace();
        }
        return null;
    }


}
