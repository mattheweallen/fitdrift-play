package models;

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

import java.lang.String;

import java.util.List;
import java.util.ArrayList;

import static play.libs.Json.toJson;
import static play.libs.Json.stringify;

/**
 * Created by meallen on 6/27/2014.
 * This class is for getting data from MongoDB for collection activities
 */
public class ActivityDAO {

    public static void insert(Activity activity) {
        BasicDBObject doc = new BasicDBObject("uid", activity.uid)
                .append("name", activity.name)
                .append("description", activity.description)
                .append("feature",new BasicDBObject("type", activity.feature.type)
                        .append("geometry", new BasicDBObject("type",activity.feature.geometry.type)
                                .append("coordinates",activity.feature.geometry.coordinates))
                        .append("properties",new BasicDBObject("time", activity.feature.properties.time)));
                //.append();
        //BasicDBObject doc = new BasicDBObject("uid", toJson(activity));

                //.append("feature",activity.feature);
                //.append("info", new BasicDBObject("x", 203).append("y", 102));
        DB mongodb = MongoResource.INSTANCE.getDB("fitdrift");
        DBCollection activityColl;
        activityColl = mongodb.getCollection("activities");
        activityColl.insert(doc);
    }

    public static Activity queryByKey(String aid) {
        return null;
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
            a = new Activity.ActivityBuilder()
                    .aid(dbObject.get("_id").toString())
                    .name((String) dbObject.get("name"))
                    .uid((String) dbObject.get("uid")).build();
            //activities.add(a);
            //}
        }
        return a;
    }

}

//Example when have containing list in json
//List<Student> students = new ArrayList<Student>();
//
//BasicDBObject query = new BasicDBObject();
//query.put("user", username);
//        DBCursor cursor = theCollection.find(query);
//        while (cursor.hasNext()) {
//        DBObject theObj = cursor.next();
//        //How to get the DBObject value to ArrayList of Java Object?
//
//        BasicDBList studentsList = (BasicDBList) theObj.get("students");
//        for (int i = 0; i < studentsList.size(); i++) {
//        BasicDBObject studentObj = (BasicDBObject) studentsList.get(i);
//        String firstName = studentObj.getString("firstName");
//        String lastName = studentObj.getString("lastName");
//        String age = studentObj.getString("age");
//        String gender = studentObj.getString("gender");
//
//        Student student = new Student();
//        student.setFirstName(firstName);
//        student.setLastName(lastName);
//        student.setAge(age);
//        student.setGender(gender);
//
//        students.add(student);
//        }
//        }