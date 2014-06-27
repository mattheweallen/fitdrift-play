package models;

import java.lang.String;


/**
 * Created by meallen on 6/26/2014.
 */
public class Activity {
    public final String aid;
    public final String name;
    public final String description;
    public final String uid;

    private Activity(ActivityBuilder builder) {
        this.aid = builder.aid;
        this.name = builder.name;
        this.description = builder.description;
        this.uid = builder.uid;
    }

    public static class ActivityBuilder {
        private final String aid;
        private final String name;
        private String description;
        private final String uid;

        public ActivityBuilder(String aid, String name, String uid) {
            this.aid = aid;
            this.name = name;
            this.uid = uid;
        }

        public ActivityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Activity build() {
            return new Activity(this);
        }
    }

    public String toString() {
        return String.format("%s - %s", uid, name);
    }
}
