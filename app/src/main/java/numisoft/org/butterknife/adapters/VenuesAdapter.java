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
import java.util.List;
import java.util.Map;

import numisoft.org.butterknife.R;
import numisoft.org.butterknife.fragments.VenuesFragment.OnVenuesFragmentClickListener;
import numisoft.org.butterknife.models.Venue;


public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    private final List<Venue> mValues = new ArrayList<>();
    private final OnVenuesFragmentClickListener venuesClickListner;

    public VenuesAdapter(OnVenuesFragmentClickListener listener) {
//        mValues = items;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference venues = db.getReference("venues");

        venues.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Venue venue = dataSnapshot.getValue(Venue.class);
                mValues.add(venue);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Venue venue = dataSnapshot.getValue(Venue.class);
                for (Venue v : mValues) {
                    if (venue.equals(v)) {
                        mValues.remove(v);
                        notifyDataSetChanged();
                        return;
                    }
                }
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Venue venue = mValues.get(position);
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
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    venuesClickListner.OnVenuesFragmentClick(venue);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView eventsCount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.venue_id);
            mContentView = (TextView) view.findViewById(R.id.venue_content);
            eventsCount = (TextView) view.findViewById(R.id.venue_eventsCount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
