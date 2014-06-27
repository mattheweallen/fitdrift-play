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

import java.lang.String;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by meallen on 6/27/2014.
 */
public class ActivityDAO {

    public void insert(Activity entity) {

    }

    public Activity queryByKey(String aid) {
        return null;
    }

    public void remove(String aid) {

    }

    public static List<Activity> findAllByUserId(String uid) {
        List<Activity> activities = new ArrayList<Activity>();
        DBCollection activityColl;

        activityColl = MongoUtil.MongoDB().getCollection("activities");
        DBCursor cur = activityColl.find();
    //List<User> usersWithMatchEmail = new ArrayList<User>();

        while(cur.hasNext()) {
            // this is where I want to convert cur.next() into a <Activity> POJO
            DBObject dbObject = cur.next();
            Activity a = new Activity.ActivityBuilder(dbObject.get("_id").toString(), (String)dbObject.get("name"), (String)dbObject.get("uid")).build();
            activities.add(a);
        }


        //MongoUtil.MongoDB().
        return activities;
    }
}