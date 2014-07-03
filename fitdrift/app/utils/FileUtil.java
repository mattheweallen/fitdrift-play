package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

//import org.primefaces.model.UploadedFile;
import models.Activity;
import models.GeoJSON.Feature;
import models.GeoJSON.Properties;
import models.GeoJSON.Geometry;
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
import java.sql.Timestamp;
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
    public static Feature buildFeatureFromGPX(File file) throws ParserConfigurationException, SAXException, IOException {
        FileInputStream in = new FileInputStream(file);
        GPXParser p = new GPXParser();
        GPX gpx = p.parseGPX(in);

        Feature feature = new Feature();

        Geometry geometry = new Geometry();


        List<Double[]> coordinates = new ArrayList<Double[]>();




        feature.type = "Feature";
        geometry.type = "LineString";

        Properties properties = new Properties();
        List<Date> time = new ArrayList<Date>();
        //Pproperties = properties;

        //a.setName(file.getFileName());
        //a.setUser(AthleticgisFacade.findUserByUsername(username));

        //List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
        for (Track t : gpx.getTracks()) {
            for (Waypoint wp : t.getTrackPoints()) {

                Double[] coord = new Double[3];
                coord[0] = new Double(wp.getLongitude());
                coord[1] = new Double(wp.getLatitude());
                coord[2] = new Double(wp.getElevation());

                coordinates.add(coord);

                time.add(wp.getTime());


                // System.out.println(wp.getLatitude() + "," +
                // wp.getLongitude());

//                ActivityPoint activityPoint = new ActivityPoint();
//                activityPoint.setLatitude(wp.getLatitude());
//                activityPoint.setLongitude(wp.getLongitude());
//                activityPoint.setTime(new Timestamp(wp.getTime().getTime()));
//                activityPoint.setElevation(wp.getElevation());
//                activityPoints.add(activityPoint);
                //System.out.println("Latitude: " + wp.getLatitude() + "Longitude: " + wp.getLongitude() + "Elevation: " + wp.getElevation());
            }
        }

        geometry.coordinates = coordinates;

        properties.time = time;

        feature.geometry = geometry;
        feature.properties = properties;

        //a.setActivitypoints(activityPoints);

        //distance TODO is elevation non null
        //GISCalculator gisc = new GISCalculator();

        //a.setDistance(gisc.computePathDistance(activityPoints) * 0.000621371); //0.000621371 convert meters to miles

        //user defined MyMap not used
        //a.setUseMyMap(false);

        //distance

        //AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);

        in.close();
        return feature;
    }
}



//Activity a = new Activity();
//a.feature = new Feature();
//        a.feature.geometry = new Geometry();
//
//
//        a.feature.geometry.coordinates = new ArrayList<Double[]>();
//
//
//        Double[] coord1 = new Double[3];
//        coord1[0] = new Double(23.5);
//        coord1[1] = new Double(24.5);
//        coord1[2] = new Double(-23.5);
//
//
//        a.feature.geometry.coordinates.add(coord2);
//
//
//
//        System.out.println(toJson(a.feature));