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
import numisoft.org.butterknife.fragments.EventsFragment;
import numisoft.org.butterknife.models.Event;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> mValues = new ArrayList<>();
    private final EventsFragment.OnEventsFragmentClickListener eventsListener;

    public EventsAdapter(EventsFragment.OnEventsFragmentClickListener listener) {
//        mValues = items;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference venues = db.getReference("events");

        venues.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                mValues.add(event);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                for (Event e : mValues) {
                    if (event.equals(e)) {
                        mValues.remove(e);
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

        eventsListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Event event = mValues.get(position);
        holder.mIdView.setText(event.getDate());

        String venueName = event.getEvent_venue().values().iterator().next();
        holder.mContentView.setText(venueName);

        String queensList = "";
        Map<String, String> queens = event.getEvent_queens();
        if (queens != null) {
            for (Map.Entry<String, String> queen : queens.entrySet()) {
                queensList += queen.getValue();
                queensList += ", ";
            }
            queensList = queensList.substring(0, queensList.length() - 2);
        }
        holder.eventsCount.setText(queensList);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != eventsListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    eventsListener.OnEventsFragmentClick(event);
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
//        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            eventsCount = (TextView) view.findViewById(R.id.eventsCount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
