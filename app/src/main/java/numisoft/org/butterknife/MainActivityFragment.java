package numisoft.org.butterknife;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.testDbSingle)
    TextView tvTestDbSingle;

    @BindView(R.id.testDb)
    TextView tvTestDb;

    @BindColor(R.color.colorAccent)
    int accent;

    @BindColor(R.color.colorPrimaryDark)
    int dark;

    @BindString(R.string.label)
    String label;

    @BindDrawable(R.drawable.coins)
    Drawable coins;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        textView.setTextColor(accent);
        textView.setText(label);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference events =
                fb.getReference().child("events");

        events.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countSingle = dataSnapshot.getChildrenCount();
                tvTestDbSingle.setText(Long.toString(countSingle));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                tvTestDb.setText(Long.toString(count));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        events.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                textView.setText(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @OnClick(R.id.button)
    public void onSubmit() {
        textView.setText(editText.getText());
        textView.setTextColor(dark);
//        imageView.setImageDrawable(coins);
    }

    @OnClick(R.id.buttonGo)
    public void goToTabs() {
        Intent intent = new Intent(getActivity(), TabActivity.class);
        startActivity(intent);
    }
}
