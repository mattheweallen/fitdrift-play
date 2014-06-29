package models;

import play.data.validation.Constraints;

import java.lang.String;

/**
 * Created by meallen on 6/26/2014.
 */
public class Activity {
    public String aid;
    @Constraints.Required
    public String name;
    public String description;
    public String uid;

    public Activity() {

    }

    private Activity(ActivityBuilder builder) {
        this.aid = builder.aid;
        this.name = builder.name;
        this.description = builder.description;
        this.uid = builder.uid;
    }

    public static class ActivityBuilder {
        private String aid;
        private String name;
        private String description;
        private String uid;

        public ActivityBuilder() {

        }

        public ActivityBuilder aid(String aid) {
            this.aid = aid;
            return this;
        }

        public ActivityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ActivityBuilder uid(String uid) {
            this.uid = uid;
            return this;
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
