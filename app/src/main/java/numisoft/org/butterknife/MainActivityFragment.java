package numisoft.org.butterknife;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindColor;
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

    @BindColor(R.color.colorAccent)
    int accent;

    @BindColor(R.color.colorPrimaryDark)
    int dark;

    @BindString(R.string.label)
    String label;

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

    @OnClick(R.id.button)
    public void onSubmit() {
        textView.setText(editText.getText());
        textView.setTextColor(dark);
    }
}
