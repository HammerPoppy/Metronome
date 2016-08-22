package hammak.metronome; /*
 *Created by Hammak on 20.08.2016 for Metronome.
*/

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SavePrefFragment extends Fragment implements View.OnClickListener {

    TextView tvPref;
    EditText etPref;
    Button bCancel, bAdd;

    public interface OnAddPrefListener{
        public void addPref(String prefName);
    }

    OnAddPrefListener addPrefListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addPrefListener = (OnAddPrefListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnAddPrefListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.save_pref_fragment, null);

        tvPref = (TextView) v.findViewById(R.id.tvPref);

        etPref = (EditText) v.findViewById(R.id.etPref);

        bCancel = (Button) v.findViewById(R.id.bCancel);
        bAdd = (Button) v.findViewById(R.id.bAdd);

        bCancel.setOnClickListener(this);
        bAdd.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bCancel:
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.bAdd:
                addPrefListener.addPref(String.valueOf(etPref.getText()));
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}
