package models;

import models.GeoJSON.Feature;
import models.GeoJSON.FeatureCollection;
import models.GeoJSON.Geometry;
import models.GeoJSON.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import org.bson.types.ObjectId;

import play.data.validation.Constraints;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;
import static play.libs.Json.fromJson;

/**
 * Created by meallen on 6/26/2014.
 */
public class Activity {
    public String aid;
    @Constraints.Required
    public String name;
    public String description;
    public String uid;
    //@TODO remove feature, replace with FeatureCollection
    //public Feature feature;

    public FeatureCollection featureCollection;

    public Activity() {}

    private Activity(ActivityBuilder builder) {
        this.aid = builder.aid;
        this.name = builder.name;
        this.description = builder.description;
        this.uid = builder.uid;
        this.featureCollection = builder.featureCollection;
    }

    public static class ActivityBuilder {
        private String aid;
        private String name;
        private String description;
        private String uid;
        private FeatureCollection featureCollection;

        public ActivityBuilder() {

        }

        public ActivityBuilder aid(String aid) {
            this.aid = aid;
            return this;
        }

        public ActivityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ActivityBuilder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public ActivityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ActivityBuilder featureCollection(FeatureCollection featureCollection) {
            this.featureCollection = featureCollection;
            return this;
        }

        public Activity build() {
            return new Activity(this);
        }
    }

    public String toString() {
        return String.format("%s - %s", uid, name);
    }

    public static void insert(Activity activity) {

        List<BasicDBObject> features = new ArrayList<BasicDBObject>();
        for(Feature feature : activity.featureCollection.features) {
            BasicDBObject featureDBObject = new BasicDBObject("type", feature.type)
                    .append("geometry", new BasicDBObject("type",feature.geometry.type)
                            .append("coordinates",feature.geometry.coordinates))
                    .append("properties",new BasicDBObject("time",feature.properties.time));
            features.add(featureDBObject);
        }

        BasicDBObject doc = new BasicDBObject("uid", activity.uid)
                .append("name", activity.name)
                .append("description", activity.description)

                .append("featureCollection", new BasicDBObject("type", activity.featureCollection.type).append("features", features));

             //   .append("feature",new BasicDBObject("type", activity.feature.type)
             //           .append("geometry", new BasicDBObject("type",activity.feature.geometry.type)
             //                   .append("coordinates",activity.feature.geometry.coordinates))
             //           .append("properties",new BasicDBObject("time", activity.feature.properties.time)));
        //.append();
        //BasicDBObject doc = new BasicDBObject("uid", toJson(activity));


        //.append("feature",activity.feature);
        //.append("info", new BasicDBObject("x", 203).append("y", 102));
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        DBCollection activityColl;
        activityColl = mongodb.getCollection("activities");
        activityColl.insert(doc);
    }

    public static void remove(Activity activity) {
        BasicDBObject doc = new BasicDBObject("_id", new ObjectId(activity.aid));
        //.append("info", new BasicDBObject("x", 203).append("y", 102));
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        DBCollection activityColl;
        activityColl = mongodb.getCollection("activities");
        activityColl.remove(doc);

    }

    public static List<Activity> findAll() {
        List<Activity> activities = new ArrayList<Activity>();
        DBCollection activityColl;

        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        activityColl = mongodb.getCollection("activities");
        DBCursor cur = activityColl.find();


        while(cur.hasNext()) {
            // this is where I want to convert cur.next() into a <Activity> POJO
            DBObject dbObject = cur.next();
            //Activity a = new Activity.ActivityBuilder(dbObject.get("_id").toString(), (String)dbObject.get("name"), (String)dbObject.get("uid")).build();
            Activity a = new Activity.ActivityBuilder()
                    .aid(dbObject.get("_id").toString())
                    .name((String)dbObject.get("name"))
                    .uid((String)dbObject.get("uid")).build();
            activities.add(a);
        }

        return activities;
    }

    public static List<Activity> findAllByUserId(String uid) {
        List<Activity> activities = new ArrayList<Activity>();
        DBCollection activityColl;

        BasicDBObject query = new BasicDBObject();

        query.put("uid", uid);
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        activityColl = mongodb.getCollection("activities");
        DBCursor cursor = activityColl.find(query);

        while(cursor.hasNext()) {
            // this is where I want to convert cur.next() into a <Activity> POJO
            DBObject dbObject = cursor.next();
            //Activity a = new Activity.ActivityBuilder(dbObject.get("_id").toString(), (String)dbObject.get("name"), (String)dbObject.get("uid")).build();
            Activity a = new Activity.ActivityBuilder()
                    .aid(dbObject.get("_id").toString())
                    .name((String)dbObject.get("name"))
                    .uid((String)dbObject.get("uid")).build();

            activities.add(a);
        }
        return activities;
    }

    public static Activity findById(String aid) {
        //List<Activity> activities = new ArrayList<Activity>();
        DBCollection activityColl;

        BasicDBObject query = new BasicDBObject();

        query.put("_id", new ObjectId(aid));
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        activityColl = mongodb.getCollection("activities");
        DBObject dbObject = activityColl.findOne(query);

        //while(cursor.hasNext()) {
        // this is where I want to convert cur.next() into a <Activity> POJO
        //  DBObject dbObject = cursor.next();
        //TODO fix builder
        //Activity a = new Activity.ActivityBuilder(dbObject.get("_id").toString(), (String)dbObject.get("name"), (String)dbObject.get("uid")).build();

        Activity a = null;
        if(dbObject != null) {
            Feature feature = new Feature();
            feature.type = ((BasicDBObject)(dbObject.get("feature"))).get("type").toString();



            FeatureCollection featureCollection = new FeatureCollection();
            featureCollection.type = ((BasicDBObject)(dbObject.get("featureCollection"))).get("type").toString();

            List<Feature> features = new ArrayList<Feature>();

           // for(BasicDBObject basicDBObject : (List<BasicDBObject>)(dbObject.get("featureCollection").get("features"))) {
                //TODO
           // }



            //System.out.println(feature.type);

            //(Feature)dbObject.get("feature")
            //System.out.println(dbObject.keySet().toString());
            //System.out.println(dbObject.get("feature").getClass());
            //Feature f = fromJson(toJson(dbObject.get("feature").toString()), Feature.class);
            //toJson(dbObject.get("feature").toString());


            a = new Activity.ActivityBuilder()
                    .aid(dbObject.get("_id").toString())
                    .name((String) dbObject.get("name"))
                    .uid((String) dbObject.get("uid"))
                    .featureCollection(featureCollection).build();

            //System.out.println(a.feature.type);
            //System.out.println(a.feature.geometry.coordinates.get(0));
            //activities.add(a);
            //}
        }
        return a;
    }

    public static String findActivityFeatureStringById(String aid) {
        //List<Activity> activities = new ArrayList<Activity>();
        DBCollection activityColl;

        BasicDBObject query = new BasicDBObject();

        query.put("_id", new ObjectId(aid));
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        activityColl = mongodb.getCollection("activities");
        DBObject dbObject = activityColl.findOne(query);

        //while(cursor.hasNext()) {
        // this is where I want to convert cur.next() into a <Activity> POJO
        //  DBObject dbObject = cursor.next();
        //TODO fix builder
        //Activity a = new Activity.ActivityBuilder(dbObject.get("_id").toString(), (String)dbObject.get("name"), (String)dbObject.get("uid")).build();

        Activity a = null;
        String featureStr = null;

        if(dbObject != null) {
            Feature feature = new Feature();
            //feature.type = ((BasicDBObject)(dbObject.get("feature"))).get("type").toString();
            featureStr = dbObject.get("feature").toString();

            //System.out.println(feature.type);

            //(Feature)dbObject.get("feature")
            //System.out.println(dbObject.keySet().toString());
            //System.out.println(dbObject.get("feature").getClass());
            //Feature f = fromJson(toJson(dbObject.get("feature").toString()), Feature.class);
            //toJson(dbObject.get("feature").toString());
//            a = new Activity.ActivityBuilder()
//                    .aid(dbObject.get("_id").toString())
//                    .name((String) dbObject.get("name"))
//                    .uid((String) dbObject.get("uid"))
//                    .feature(feature).build();

            //System.out.println(a.feature.type);
            //System.out.println(a.feature.geometry.coordinates.get(0));
            //activities.add(a);
            //}
        }
        return featureStr;
    }
}
