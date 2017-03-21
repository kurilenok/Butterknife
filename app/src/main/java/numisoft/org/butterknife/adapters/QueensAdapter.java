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
import numisoft.org.butterknife.fragments.QueensFragment.OnQueensFragmentClickListener;
import numisoft.org.butterknife.models.Queen;


public class QueensAdapter extends RecyclerView.Adapter<QueensAdapter.ViewHolder> {

    private final List<Queen> queens = new ArrayList<>();
    private final OnQueensFragmentClickListener queensClickListner;

    public QueensAdapter(OnQueensFragmentClickListener listener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference queensRef = db.getReference("queens");

        queensRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addQueen(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                removeQueen(dataSnapshot.getKey());
                addQueen(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeQueen(dataSnapshot.getKey());
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        queensClickListner = listener;
    }

    private void removeQueen(String key) {
        for (Queen v : queens) {
            if (key.equals(v.getKey())) {
                queens.remove(v);
                break;
            }
        }
    }

    private void addQueen(DataSnapshot dataSnapshot) {
        Queen venue = dataSnapshot.getValue(Queen.class);
        venue.setKey(dataSnapshot.getKey());
        queens.add(venue);
        Collections.sort(queens);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Queen queen = queens.get(position);
        holder.mIdView.setText(queen.getName());

        int eventsCount = 0;
        Map<String, Boolean> events = queen.getQueen_events();

        if (events != null) {
            eventsCount = events.size();
        }

        holder.eventsCount.setText(Integer.toString(eventsCount));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != queensClickListner) {
                    queensClickListner.OnQueensFragmentClick(queen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return queens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView eventsCount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.queen_id);
            eventsCount = (TextView) view.findViewById(R.id.queen_eventsCount);
        }

    }
}
