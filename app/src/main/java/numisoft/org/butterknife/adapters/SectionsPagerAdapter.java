package numisoft.org.butterknife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import numisoft.org.butterknife.fragments.EventsFragment;
import numisoft.org.butterknife.fragments.QueensFragment;
import numisoft.org.butterknife.fragments.VenuesFragment;
import numisoft.org.butterknife.models.Sections;

/**
 * Created by kukolka on 3/19/2017.
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return EventsFragment.newInstance(1);
            case 1:
                return QueensFragment.newInstance(1);
            case 2:
                return VenuesFragment.newInstance(1);
        }

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return VenuesFragment.newInstance(1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Sections.EVENTS.getTitle();
            case 1:
                return Sections.QUEENS.getTitle();
            case 2:
                return Sections.VENUES.getTitle();
        }
        return null;


    }
}
