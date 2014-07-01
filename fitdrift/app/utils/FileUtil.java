package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

//import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;

//mport com.fitdrift.domain.activity.Activity;
//import com.fitdrift.domain.activity.ActivityPoint;
//import com.fitdrift.model.AthleticgisFacade;
//import com.fitdrift.util.file.fit.FitDecoder;
import utils.gpx.GPXParser;
import utils.gpx.beans.GPX;
import utils.gpx.beans.Track;
import utils.gpx.beans.Waypoint;
//import com.fitdrift.util.gis.GISCalculator;



/**
 * Created by matt on 6/29/14.
 */
public class FileUtil {
    /**
     *
     * @param file
     * @param username
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void uploadAcitvityPointsFromGPX(File file, String username) throws ParserConfigurationException, SAXException, IOException {
        FileInputStream in = new FileInputStream(file);
        GPXParser p = new GPXParser();
        GPX gpx = p.parseGPX(in);
        //Activity a = new Activity();
        //a.setName(file.getFileName());
        //a.setUser(AthleticgisFacade.findUserByUsername(username));

        //List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
        for (Track t : gpx.getTracks()) {
            for (Waypoint wp : t.getTrackPoints()) {
                // System.out.println(wp.getLatitude() + "," +
                // wp.getLongitude());

//                ActivityPoint activityPoint = new ActivityPoint();
//                activityPoint.setLatitude(wp.getLatitude());
//                activityPoint.setLongitude(wp.getLongitude());
//                activityPoint.setTime(new Timestamp(wp.getTime().getTime()));
//                activityPoint.setElevation(wp.getElevation());
//                activityPoints.add(activityPoint);
                System.out.println("Latitude: " + wp.getLatitude() + "Longitude: " + wp.getLongitude() + "Elevation: " + wp.getElevation());
            }
        }
        //a.setActivitypoints(activityPoints);

        //distance TODO is elevation non null
        //GISCalculator gisc = new GISCalculator();

        //a.setDistance(gisc.computePathDistance(activityPoints) * 0.000621371); //0.000621371 convert meters to miles

        //user defined MyMap not used
        //a.setUseMyMap(false);

        //distance

        //AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);

        in.close();
    }
}
