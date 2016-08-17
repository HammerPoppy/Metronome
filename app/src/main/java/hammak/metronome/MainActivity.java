package hammak.metronome;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import hammak.metronome.SetPeriodDialog.OnCompleteListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnCompleteListener {

    TextView tvPeriodText, tvPeriodValue;
    Button bStartMetronome, bSetPeriod;
    CheckBox cbSound, cbVibration;

    float periodSec = 1;
    boolean shouldTick;
    boolean shouldVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPeriodText = (TextView) findViewById(R.id.tvPeriodText);
        tvPeriodValue = (TextView) findViewById(R.id.tvPeriodValue);

        bStartMetronome = (Button) findViewById(R.id.bStartMetronome);
        bSetPeriod = (Button) findViewById(R.id.bSetPeriod);

        bStartMetronome.setOnClickListener(this);
        bSetPeriod.setOnClickListener(this);

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
                toMetronomeActivity.putExtra("periodSec", periodSec);
                startActivity(toMetronomeActivity);
                break;
            case R.id.bSetPeriod:
                DialogFragment setPeriodDialog = new SetPeriodDialog();
                setPeriodDialog.show(getFragmentManager(), setPeriodDialog.getClass().getName());
        }
    }

    @Override
    public void onComplete(Float periodSec) {
        tvPeriodValue.setText(Float.toString(periodSec));
        this.periodSec = periodSec;
    }
}
