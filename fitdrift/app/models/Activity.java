package models;

import java.lang.String;




/**
 * Created by meallen on 6/26/2014.
 */
public class Activity {
    public String aid;
    public String name;
    public String description;

    public String toString() {
        return String.format("%s - %s", aid, name);
    }
}
