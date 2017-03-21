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

import numisoft.org.butterknife.R;
import numisoft.org.butterknife.fragments.EventsFragment.OnEventsFragmentClickListener;
import numisoft.org.butterknife.models.Event;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> events = new ArrayList<>();
    private final OnEventsFragmentClickListener eventsListener;

    public EventsAdapter(OnEventsFragmentClickListener listener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = db.getReference("events");

        eventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addEvent(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                removeEvent(dataSnapshot.getKey());
                addEvent(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeEvent(dataSnapshot.getKey());
                notifyDataSetChanged();
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



    private void addEvent(DataSnapshot dataSnapshot) {
        Event event = dataSnapshot.getValue(Event.class);
        event.setKey(dataSnapshot.getKey());
        events.add(event);
        Collections.sort(events);
    }

    private void removeEvent(String key) {
        for (Event e : events) {
            if (key.equals(e.getKey())) {
                events.remove(e);
                break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Event event = events.get(position);
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
                    eventsListener.OnEventsFragmentClick(event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView eventsCount;

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
