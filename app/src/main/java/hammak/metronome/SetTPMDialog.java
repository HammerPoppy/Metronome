package hammak.metronome;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;


/*
*Created by Hammak on 14.08.2016 for Metronome.
*/

public class SetTPMDialog extends DialogFragment implements OnClickListener {

    NumberPicker npTPM;
    TextView tvTPM;

    long periodMSec;
    int TPM;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Set Ticks Per Minute");
        View v = inflater.inflate(R.layout.set_tpm_dialog, null);

        tvTPM = (TextView) v.findViewById(R.id.tvTPM);

        npTPM = (NumberPicker) v.findViewById(R.id.npTPM);;

        v.findViewById(R.id.bCancel).setOnClickListener(this);
        v.findViewById(R.id.bApply).setOnClickListener(this);

        return v;
     }

    public interface OnCompleteListener {
        void onComplete(int TPM);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bCancel:
                dismiss();
                break;
            case R.id.bApply:
                TPM = npTPM.getValue();
                mListener.onComplete(TPM);
                dismiss();
                break;
        }
    }

    private OnCompleteListener mListener;

    //Make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}