package controllers;

import com.google.common.io.Files;
import models.Activity;

import views.html.activities.details;
import views.html.activities.list;

import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import play.mvc.With;

import static play.mvc.Http.MultipartFormData;
import models.ActivityDAO;

/**
 * Created by matt on 6/25A/14.
 */
public class Activities extends Controller {

    private static final Form<Activity> activityForm = Form.form(Activity.class);

    public static Result list()  {
        List<Activity> activities = ActivityDAO.findAllByUserId("matt");
        return ok(list.render(activities));
    }

    public static Result newActivity() {

        return ok(details.render(activityForm));
    }

    public static Result details(String aid) {
        final Activity activity = ActivityDAO.findById(aid);
        if (activity == null) {
            return notFound(String.format("Activity %s does not exist.", aid));
        }

        Form<Activity> filledForm = activityForm.fill(activity);
        return ok(details.render(filledForm));
    }

    public static Result save() {
        MultipartFormData body = request().body().asMultipartFormData();

        Form<Activity> boundForm = activityForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Activity activity = boundForm.get();

        MultipartFormData.FilePart part = body.getFile("activityfile");
        if(part != null) {
            File activityfile = part.getFile();
            activityfile.

            try {
                //product.picture = Files.toByteArray(picture);
                Files.toByteArray(activityfile);
            } catch (IOException e) {
                return internalServerError("Error reading file upload");
            }
        }


        ActivityDAO.insert(activity);
        flash("success",
                String.format("Successfully added activity %s", activity));

        return redirect(routes.Activities.list());
    }

    public static Result delete(String aid) {

        final Activity activity = ActivityDAO.findById(aid);
        if (activity == null) {
            return notFound(String.format("Activity %s does not exist.", aid));
        }

        ActivityDAO.remove(activity);
        return redirect(routes.Activities.list());
    }
}
