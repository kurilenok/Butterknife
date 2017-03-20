package numisoft.org.butterknife.models;

import java.util.Map;

/**
 * Created by kukolka on 3/19/2017.
 */

public class Venue {

    private String name;
    private String location;
    private Map<String, String> venue_events;

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getVenue_events() {
        return venue_events;
    }

    public void setVenue_events(Map<String, String> venue_events) {
        this.venue_events = venue_events;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Venue)) return false;

        Venue other = (Venue) object;

        if (this.getName().equalsIgnoreCase(other.getName()) &&
                this.getLocation().equalsIgnoreCase(other.getLocation())) return true;
        else return false;

    }
}
