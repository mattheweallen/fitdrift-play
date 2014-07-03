package models.GeoJSON;

import java.util.List;

/**
 * Created by meallen on 7/3/2014.
 */
public class Geometry {
    public String type;
    public List<Double[]> coordinates;

    public Geometry() {}

    private Geometry(GeometryBuilder builder) {
        this.type = builder.type;
        this.coordinates = builder.coordinates;
    }

    public static class GeometryBuilder {
        private String type;
        private List<Double[]> coordinates;

        public GeometryBuilder type(String type) {
            this.type = type;
            return this;
        }

        public GeometryBuilder coordinates(List<Double[]> coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Geometry build() {
            return new Geometry(this);
        }
    }
}
