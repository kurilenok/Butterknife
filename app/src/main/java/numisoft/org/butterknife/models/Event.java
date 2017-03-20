package numisoft.org.butterknife.models;

import java.util.Map;

/**
 * Created by kukolka on 3/20/2017.
 */

public class Event {

    private String date;
    private Map<String, String> event_venue;
    private Map<String, String> event_queens;

    public Event() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(Map<String, String> event_venue) {
        this.event_venue = event_venue;
    }

    public Map<String, String> getEvent_queens() {
        return event_queens;
    }

    public void setEvent_queens(Map<String, String> event_queens) {
        this.event_queens = event_queens;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Event)) return false;

        Event other = (Event) object;

        if (this.getDate().equalsIgnoreCase(other.getDate()) &&
                this.getEvent_queens().toString().equalsIgnoreCase(other.getEvent_queens().toString())
                && this.getEvent_venue().toString().equalsIgnoreCase(other.getEvent_venue().toString()))
            return true;
        else return false;

    }
}
