package controllers;

import models.Activity;
import models.ActivityDAO;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.activities.list;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

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

import java.net.UnknownHostException;

/**
 * Created by matt on 6/25/14.
 */
public class Activities extends Controller {

    public static Result list() throws UnknownHostException {
        //MongoClient mongoClient = new MongoClient( "localhost" );
        //DB db = mongoClient.getDB( "fitdrift" );
        //Set<String> colls = db.getCollectionNames();

        //for (String s : colls) {
        //    System.out.println(s);
        //}

        //List<Activity> activities = new ArrayList<Activity>();
        List<Activity> activities = ActivityDAO.findAllByUserId("matt");
        return ok(list.render(activities));
    }

    public static Result newActivity() {
        return TODO;
    }

    public static Result details(String aid) {
        return TODO;
    }

    public static Result save() {
        return TODO;
    }

    public static Result delete(String aid) {
        return TODO;
    }
}
