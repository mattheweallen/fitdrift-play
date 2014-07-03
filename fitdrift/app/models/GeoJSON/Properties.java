package models.GeoJSON;

import java.util.List;
import java.util.Date;

/**
 * Created by meallen on 7/3/2014.
 */
public class Properties {
    public List<Date> time;
    
    public Properties() {}

    private Properties(PropertiesBuilder builder) {
        this.time = builder.time;
    }

    public static class PropertiesBuilder {
        private List<Date> time;

        public PropertiesBuilder time(List<Date> time) {
            this.time = time;
            return this;
        }

        public Properties build() {
            return new Properties(this);
        }
    }
}
