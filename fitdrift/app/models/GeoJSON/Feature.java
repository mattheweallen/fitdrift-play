package models.GeoJSON;

/**
 * Created by meallen on 7/3/2014.
 */
public class Feature {
    public String type;
    public Geometry geometry;
    public Properties properties;

    public Feature() {}

    private Feature(FeatureBuilder builder) {
        this.type = builder.type;
        this.geometry = builder.geometry;
        this.properties = builder.properties;
    }

    public static class FeatureBuilder {
        private String type;
        private Geometry geometry;
        private Properties properties;

        public FeatureBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FeatureBuilder geometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public FeatureBuilder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Feature build() {
            return new Feature(this);
        }
    }

    //public String toString() {
      //  return String.format("%s - %s", uid, name);
    //}
}
