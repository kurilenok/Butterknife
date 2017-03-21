package numisoft.org.butterknife.models;

import numisoft.org.butterknife.fragments.EventsFragment;
import numisoft.org.butterknife.fragments.QueensFragment;
import numisoft.org.butterknife.fragments.SectionFragment;
import numisoft.org.butterknife.fragments.VenuesFragment;

/**
 * Created by kukolka on 3/22/2017.
 */

public enum Sections {

    EVENTS(0, "Events", new EventsFragment()),
    QUEENS(1, "Queens", new QueensFragment()),
    VENUES(2, "Venues", new VenuesFragment());

    private final int count;
    private final String title;
    private final SectionFragment fragment;

    Sections(int count, String title, SectionFragment fragment) {
        this.count = count;
        this.title = title;
        this.fragment = fragment;
    }

    public int getCount() {
        return count;
    }

    public String getTitle() {
        return title;
    }

    public SectionFragment getFragment() {
        return fragment;
    }
}
