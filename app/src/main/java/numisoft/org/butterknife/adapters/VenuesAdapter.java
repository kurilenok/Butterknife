package numisoft.org.butterknife.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import numisoft.org.butterknife.R;
import numisoft.org.butterknife.fragments.VenuesFragment.OnVenuesFragmentClickListener;
import numisoft.org.butterknife.models.Venue;


public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    private final List<Venue> venues = new ArrayList<>();
    private final OnVenuesFragmentClickListener venuesClickListner;

    public VenuesAdapter(OnVenuesFragmentClickListener listener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference venuesRef = db.getReference("venues");

        venuesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addVenue(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                removeVenue(dataSnapshot.getKey());
                addVenue(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeVenue(dataSnapshot.getKey());
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        venuesClickListner = listener;
    }

    private void removeVenue(String key) {
        for (Venue v : venues) {
            if (key.equals(v.getKey())) {
                venues.remove(v);
                break;
            }
        }
    }

    private void addVenue(DataSnapshot dataSnapshot) {
        Venue venue = dataSnapshot.getValue(Venue.class);
        venue.setKey(dataSnapshot.getKey());
        venues.add(venue);
        Collections.sort(venues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Venue venue = venues.get(position);
        holder.mIdView.setText(venue.getName());
        holder.mContentView.setText(venue.getLocation());

        String eventsList = "";
        Map<String, String> events = venue.getVenue_events();
        if (events != null) {
            for (Map.Entry<String, String> event : events.entrySet()) {
                eventsList += event.getValue();
                eventsList += ", ";
            }
            eventsList = eventsList.substring(0, eventsList.length() - 2);
        }
        holder.eventsCount.setText(eventsList);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != venuesClickListner) {
                    venuesClickListner.OnVenuesFragmentClick(venue);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.venue_id)
        TextView mIdView;

        @BindView(R.id.venue_content)
        TextView mContentView;

        @BindView(R.id.venue_eventsCount)
        TextView eventsCount;

        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public final TextView eventsCount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.venue_id);
//            mContentView = (TextView) view.findViewById(R.id.venue_content);
//            eventsCount = (TextView) view.findViewById(R.id.venue_eventsCount);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
