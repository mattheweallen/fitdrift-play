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
        //Feature feature = new Feature();
        //Geometry geometry = new Geometry();
        List<Double[]> coordinates = new ArrayList<Double[]>();
        //feature.type = "Feature";
        //geometry.type = "LineString";

        //Properties properties = new Properties();
        List<Date> time = new ArrayList<Date>();

        for (Track t : gpx.getTracks()) {
            for (Waypoint wp : t.getTrackPoints()) {
                Double[] coord = new Double[3];
                coord[0] = new Double(wp.getLongitude());
                coord[1] = new Double(wp.getLatitude());
                coord[2] = new Double(wp.getElevation());
                coordinates.add(coord);

                time.add(wp.getTime());
            }
        }
        //geometry.coordinates = coordinates;
        //properties.time = time;
        //feature.geometry = geometry;
        //feature.properties = properties;

        Feature feature = new Feature.FeatureBuilder()
                .geometry(new Geometry.GeometryBuilder().coordinates(coordinates).type("LineString").build())
                .properties(new Properties.PropertiesBuilder().time(time).build())
                .type("Feature")
                .build();


        in.close();
        return feature;
    }
}