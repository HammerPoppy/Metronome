package hammak.metronome;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/*
*Created by Hammak on 14.08.2016 for Metronome.
*/
public class PeriodAlertDialog extends DialogFragment implements DialogInterface.OnClickListener {
     public Dialog onCreateDialog (Bundle savedInstanceState) {
         AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.period_alert_dialog_title)
                .setCancelable(true)
                .setMessage(R.string.period_alert_dialog_message)
                .setNeutralButton(R.string.period_alert_dialog_neutral, this);
         return adBuilder.create();
     }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
}
