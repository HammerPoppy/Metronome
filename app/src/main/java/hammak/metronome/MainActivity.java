package hammak.metronome;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SetPeriodDialog.OnCompleteListener, SetTPMDialog.OnCompleteListener {

    TextView tvPeriodText, tvPeriodValue, tvTPMText, tvTPMValue;
    Button bStartMetronome, bSetPeriod, bSetTPM;
    CheckBox cbSound, cbVibration;

    long periodMSec = 1000;
    boolean shouldTick;
    boolean shouldVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPeriodText = (TextView) findViewById(R.id.tvPeriodText);
        tvPeriodValue = (TextView) findViewById(R.id.tvPeriodValue);
        tvTPMText = (TextView) findViewById(R.id.tvTPMText);
        tvTPMValue = (TextView) findViewById(R.id.tvTPMValue);

        bStartMetronome = (Button) findViewById(R.id.bStartMetronome);
        bSetPeriod = (Button) findViewById(R.id.bSetPeriod);
        bSetTPM = (Button) findViewById(R.id.bSetTPM);

        bStartMetronome.setOnClickListener(this);
        bSetPeriod.setOnClickListener(this);
        bSetTPM.setOnClickListener(this);

        cbSound = (CheckBox) findViewById(R.id.cbSound);
        cbVibration = (CheckBox) findViewById(R.id.cbVibration);
        cbSound.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bStartMetronome:
                Intent toMetronomeActivity = new Intent(this, MetronomeActivity.class);
                shouldTick = cbSound.isChecked();
                shouldVibrate = cbVibration.isChecked();
                toMetronomeActivity.putExtra("shouldTick", shouldTick);
                toMetronomeActivity.putExtra("shouldVibrate", shouldVibrate);
                toMetronomeActivity.putExtra("periodMSec", periodMSec);
                startActivity(toMetronomeActivity);
                break;
            case R.id.bSetPeriod:
                DialogFragment setPeriodDialog = new SetPeriodDialog();
                setPeriodDialog.show(getFragmentManager(), setPeriodDialog.getClass().getName());
                break;
            case R.id.bSetTPM:
                DialogFragment setTPMDialog = new SetTPMDialog();
                setTPMDialog.show(getFragmentManager(), setTPMDialog.getClass().getName());
                break;
        }
    }

    @Override
    public void onComplete(long periodMSec) {
        setPeriodAndTPM(periodMSec);
    }

    @Override
    public void onComplete(int TPM) {
        long periodMSec = (long) 60000 / TPM;
        setPeriodAndTPM(periodMSec);
    }

    public void setPeriodAndTPM(long periodMSec){
        this.periodMSec = periodMSec;

        double doublePeriodSec = (double) periodMSec / 1000;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        tvPeriodValue.setText(df.format(doublePeriodSec));

        long tpm = 60000 / periodMSec;
        tvTPMValue.setText(Long.toString(tpm));
    }
}
