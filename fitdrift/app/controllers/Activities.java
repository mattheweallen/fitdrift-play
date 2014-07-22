package controllers;

//import com.google.common.io.Files;
import models.Activity;

import models.GeoJSON.Feature;
//import models.GeoJSON.Geometry;
import models.GeoJSON.FeatureCollection;
import org.xml.sax.SAXException;
import play.mvc.With;
import views.html.activities.details;
import views.html.activities.list;
import views.html.activities.map;
import views.html.activities.data;
import views.html.activities.upload;

import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Set;
import play.mvc.With;

import utils.FileUtil;

import static play.mvc.Http.MultipartFormData;
import static play.libs.Json.toJson;
import models.Activity;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by matt on 6/25A/14.
 * This class is a play mvc Controller for Activities.
 */
@With(CatchAction.class)
public class Activities extends Controller {

    private static final Form<Activity> activityForm = Form.form(Activity.class);

    public static Result index() {
        return redirect(routes.Activities.list(1));
    }

    public static Result data()  {
        return ok(data.render());
    }

    public static Result list(Integer page)  {
        List<Activity> activities = Activity.findAllByUserId("matt");
        return ok(list.render(activities));
    }

    /**
     * Gives empty activityForm.
     * @return
     */
    public static Result newActivity() {
        return ok(details.render(activityForm));
    }

    /**
     * Gives filled activityForm for updating.
     * @param aid
     * @return
     */
    public static Result details(String aid) {
        final Activity activity = Activity.findById(aid);

        if (activity == null) {
            return notFound(String.format("Activity %s does not exist.", aid));
        }
        Form<Activity> filledForm = activityForm.fill(activity);
        return ok(details.render(filledForm));
    }

    /**
     * Saves activity and returns activity list.
     * @return
     */
    public static Result save() {
        MultipartFormData body = request().body().asMultipartFormData();

        Form<Activity> boundForm = activityForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Activity activity = boundForm.get();

        MultipartFormData.FilePart part = body.getFile("activityfile");


        //Get all files bound to the form when submitted
        //List<MultipartFormData.FilePart> plate_files = request().body().asMultipartFormData().getFiles();
//Get files from a specific name or id
        // FilePart myfile = request().body().asMultipartFormData().getFile("files");


        //Get all files bound to the form when submitted
        //List<MultipartFormData.FilePart> plate_files = request().body().asMultipartFormData().getFiles();

        if(part != null) {
        //if(plate_files != null) {
            File activityfile = part.getFile();




            try {

                //    Feature feature = FileUtil.buildFeatureFromGPX(activityfile);
                //    activity.feature = feature;

                //for(MultipartFormData.FilePart filepart : plate_files) {
                    Feature feature = FileUtil.buildFeatureFromGPX(activityfile);

                   // System.out.println(feature.geometry.type);
                    //System.out.println(feature.geometry.coordinates.get(0));

                   // activity.feature = feature;
//                }


                FeatureCollection featureCollection = new FeatureCollection();
                featureCollection.type = "FeatureCollection";
                List<Feature> features = new ArrayList<Feature>();
                features.add(feature);




                //for(MultipartFormData.FilePart filepart : plate_files) {
                //    Feature feature = FileUtil.buildFeatureFromGPX(filepart.getFile());
                //    features.add(feature);
                    //System.out.println(feature.geometry.type);
                    //System.out.println(feature.geometry.coordinates.get(0));

                    //activity.feature = feature;
                //}
                featureCollection.features = features;
                activity.featureCollection = featureCollection;





            } catch (IOException e) {
                return internalServerError("Error reading file upload");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return internalServerError("Error reading file upload");
            } catch (SAXException e) {
                e.printStackTrace();
                return internalServerError("Error reading file upload");
            }
        }


        Activity.insert(activity);
        flash("success",
                String.format("Successfully added activity %s", activity));
        return redirect(routes.Activities.list(1));
    }

    public static Result upload() {
        return ok(upload.render());
    }

    public static Result uploadFiles() {

        //List<Activity> activities = new ArrayList<Activity>();

        MultipartFormData body = request().body().asMultipartFormData();

//        Form<Activity> boundForm = activityForm.bindFromRequest();
//        if(boundForm.hasErrors()) {
//            flash("error", "Please correct the form below.");
//            return badRequest(details.render(boundForm));
//        }
//
//        Activity activity = boundForm.get();

        //MultipartFormData.FilePart part = body.getFile("activityfile");


        //Get all files bound to the form when submitted
        //List<MultipartFormData.FilePart> plate_files = request().body().asMultipartFormData().getFiles();
//Get files from a specific name or id
        // FilePart myfile = request().body().asMultipartFormData().getFile("files");


        //Get all files bound to the form when submitted
        List<MultipartFormData.FilePart> plate_files = request().body().asMultipartFormData().getFiles();


        List<Activity> activities = new ArrayList<Activity>();

        //if(part != null) {
        if(plate_files != null) {
            //File activityfile = part.getFile();




            try {

                //    Feature feature = FileUtil.buildFeatureFromGPX(activityfile);
                //    activity.feature = feature;




                for(MultipartFormData.FilePart filepart : plate_files) {
                    FeatureCollection featureCollection = new FeatureCollection();
                    featureCollection.type = "FeatureCollection";
                    List<Feature> features = new ArrayList<Feature>();


                    Activity activity = new Activity();
                    activity.name = "changeme";



                    Feature feature = FileUtil.buildFeatureFromGPX(filepart.getFile());
                    features.add(feature);
                    //System.out.println(feature.geometry.type);
                    //System.out.println(feature.geometry.coordinates.get(0));

                    //activity.feature = feature;
                    featureCollection.features = features;
                    activity.featureCollection = featureCollection;

                    activities.add(activity);
                }




            } catch (IOException e) {
                return internalServerError("Error reading file upload");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return internalServerError("Error reading file upload");
            } catch (SAXException e) {
                e.printStackTrace();
                return internalServerError("Error reading file upload");
            }
        }


        for(Activity a : activities) {
            Activity.insert(a);
        }

       // flash("success",
       //         String.format("Successfully added activity %s", activity));
        return redirect(routes.Activities.list(1));
    }

    /**
     * Delete activity given by aid, and return updated Activity list.
     * @param aid
     * @return
     */
    public static Result delete(String aid) {

        final Activity activity = Activity.findById(aid);
        if (activity == null) {
            return notFound(String.format("Activity %s does not exist.", aid));
        }

        Activity.remove(activity);
        return redirect(routes.Activities.list(1));
    }

    public static Result feature(String aid) {
        //final Activity activity = Activity.findById(aid);
        String featureStr = Activity.findActivityFeatureStringById(aid);

        System.out.println(featureStr);

        if(featureStr == null) return notFound();
        return ok(featureStr);
    }

    public static Result map(String aid) {
        //final Activity activity = Activity.findById(aid);

        //if (activity == null) {
        //    return notFound(String.format("Activity %s does not exist.", aid));
        //}
        //Form<Activity> filledForm = activityForm.fill(activity);
        return ok(map.render(aid));
    }
}
