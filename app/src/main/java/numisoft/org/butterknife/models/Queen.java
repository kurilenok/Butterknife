package numisoft.org.butterknife.models;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by kukolka on 3/21/2017.
 */

public class Queen implements Comparable<Queen> {

    private String key;
    private String name;
    private Map<String, Boolean> queen_events;

    public Queen() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getQueen_events() {
        return queen_events;
    }

    public void setQueen_events(Map<String, Boolean> queen_events) {
        this.queen_events = queen_events;
    }

    @Override
    public int compareTo(@NonNull Queen queen) {
        return this.getName().compareTo(queen.getName());
    }
}
