package controllers;

import models.Activity;
import models.ActivityDAO;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import views.html.activities.*;

import java.util.List;

/**
 * Created by matt on 6/25A/14.
 */
public class Activities extends Controller {

    private static final Form<Activity> activityForm = Form.form(Activity.class);

    public static Result list()  {
        List<Activity> activities = ActivityDAO.findAllByUserId("matt");
        return ok(views.html.activities.list.render(activities));
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

        Form<Activity> boundForm = activityForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Activity activity = boundForm.get();
        System.out.println(activity.uid);
        //product.save();
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
